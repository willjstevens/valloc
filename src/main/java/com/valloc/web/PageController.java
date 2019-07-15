package com.valloc.web;

import com.valloc.Category;
import com.valloc.UrlBuilder;
import com.valloc.UrlConstants;
import com.valloc.framework.*;
import com.valloc.io.IoUtil;
import com.valloc.io.JspWriterAdapter;
import com.valloc.log.LogManager;
import com.valloc.log.Logger;
import com.valloc.object.domain.Page;
import com.valloc.object.domain.PageGuest;
import com.valloc.object.domain.User;
import com.valloc.object.domain.UserMessage;
import com.valloc.object.dto.*;
import com.valloc.service.ConfigurationService;
import com.valloc.service.PageService;
import com.valloc.service.UtilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;

import static com.valloc.UrlConstants.*;

/**
 * Handles requests for the application home page.
 */
@Controller
public class PageController extends AbstractController
{
	private static final Logger logger = LogManager.manager().newLogger(PageController.class, Category.CONTROLLER);
	@Autowired private SessionManager sessionManager;
    @Autowired private PageService pageService;
	@Autowired private UtilityService utilityService;
    @Autowired private ConfigurationService configurationService;
	@Autowired private IoUtil ioUtil;

	@RequestMapping(value=PAGE_NEW)
	public ModelAndView pageNewGet() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("designer");
        loadModel(mav);

        ServiceResult<PageDesignerDto> serviceResult = pageService.loadPageNewPage();
        PageDesignerDto dto = serviceResult.getObject();
        loadPageLoadData(dto, mav);

		return mav;
	}

    @RequestMapping(value=PAGE_NEW_POST, method=RequestMethod.POST)
    @ResponseBody
    public ResponseDto pageNewPost(@RequestBody PageDesignerDto pageDesignerDto, HttpServletRequest request, HttpServletResponse response) {
    	Session session = sessionManager.getSessionByHttpSession(request.getSession());
        User user = session.getUser();
		JspWriterAdapter jspWriterAdapter = 
				ioUtil.newUserPageFileWriterResponseAdapter(request,
                        response,
                        PAGE_BUILDER,
                        user.getUsername(),
                        pageDesignerDto.getPageDto().getPath());
        ModelAndView modelStore = new ModelAndView();
        loadModel(modelStore);
        jspWriterAdapter.setModelStore(modelStore);
        loadTagline(modelStore, pageDesignerDto.getPageDto(), user);

        ServiceResult<PageDesignerDto> serviceResult = pageService.newPage(pageDesignerDto, user, jspWriterAdapter);
        if (logger.isFine()) {
			logger.fine("Returning response for new page post for username %s and page path %s.", user.getUsername(), pageDesignerDto.getPageDto().getPath());
		}

        ResponseDto responseDto = toResponseDto(serviceResult);
        if (serviceResult.isSuccess()) {
            session.setRelayMessage(serviceResult.getRelayMessage());
            session.addAllAttributes(serviceResult.getAttributes());
            responseDto.targetUrl = UrlConstants.PAGE_EDIT_GET.replaceAll("\\{path\\}", pageDesignerDto.getPageDto().getPath());
        }
        return responseDto;
    }

    @RequestMapping(value=PAGE_EDIT_GET)
    public ModelAndView pageEditGet(@PathVariable String path, HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("designer");
        loadModel(mav);
        Session session = sessionManager.getSessionByHttpSession(request.getSession());
        User user = session.getUser();
        ServiceResult<PageDesignerDto> serviceResult = pageService.loadPageEditPage(user, path);

        if (serviceResult.isSuccess()) { // page was found
            PageDesignerDto pageDesignerDto = serviceResult.getObject();

            UserMessage relayMessage = session.getRelayMessage();
            if (relayMessage != null) {
                pageDesignerDto.setRelayMessage(UserMessageDto.toDto(relayMessage));
                session.setRelayMessage(null);
            }
            if (session.hasAttributes()) {
                pageDesignerDto.addAllAttributes(session.getAttributes());
                session.clearAttributes();
            }
            loadPageLoadData(pageDesignerDto, mav);
        } else { // page not found
            throw new PageNotFoundPageResultException();
        }
        return mav;
    }

    @RequestMapping(value=PAGE_EDIT_POST, method=RequestMethod.POST)
    @ResponseBody
    public ResponseDto pageEditPost(@RequestBody PageDesignerDto pageDesignerDto, HttpServletRequest request, HttpServletResponse response) {
        Session session = sessionManager.getSessionByHttpSession(request.getSession());
        User user = session.getUser();
        PageDto pageDto = pageDesignerDto.getPageDto();

		JspWriterAdapter jspWriterAdapter =
				ioUtil.newUserPageFileWriterResponseAdapter(request,
                        response,
                        PAGE_BUILDER,
                        user.getUsername(),
                        pageDto.getPath());
        ModelAndView modelStore = new ModelAndView();
        loadModel(modelStore);
        jspWriterAdapter.setModelStore(modelStore);
        loadTagline(modelStore, pageDto, user);

        ServiceResult<PageDesignerDto> serviceResult = pageService.editPage(pageDesignerDto, user, jspWriterAdapter);
        if (logger.isFine()) {
			logger.fine("Returning response for edit page post for username %s and page path %s.", user.getUsername(), pageDto.getPath());
		}
        ResponseDto responseDto = toResponseDto(serviceResult);

        boolean pathDidChange = !pageDesignerDto.getOriginalPath().equals(pageDto.getPath());
        if (pathDidChange && serviceResult.isSuccess()) {
            session.setRelayMessage(serviceResult.getRelayMessage());
            session.addAllAttributes(serviceResult.getAttributes());
            responseDto.targetUrl = UrlConstants.PAGE_EDIT_GET.replaceAll("\\{path\\}", pageDesignerDto.getPageDto().getPath());
        }

        return responseDto;
    }

    @RequestMapping(value=IMPORT, method=RequestMethod.POST)
    public String importBmFile(@RequestParam("bmFile") Part bmFile, HttpServletRequest request, HttpServletResponse response) {
        Session session = sessionManager.getSessionByHttpSession(request.getSession());
        User user = session.getUser();

        // read bytes into file
        InputStream fileInputStream = null;
        try {
            fileInputStream = bmFile.getInputStream();
        } catch (IOException e) {
            logger.error("Problem when fetching input stream of import file for user %s.", e, user.getUsername());
        }

        ServiceResult<PageDesignerDto> serviceResult = pageService.importPage(user, fileInputStream);
        if (logger.isFine()) {
            logger.fine("Returning response to import for edit page post for username %s.", user.getUsername());
        }

        if (serviceResult.isSuccess()) {
            session.setRelayMessage(serviceResult.getRelayMessage());
        }

        PageDesignerDto pageDesignerDto = serviceResult.getObject();
        String redirectUrl = UrlConstants.PAGE_EDIT_GET.replaceAll("\\{path\\}", pageDesignerDto.getPageDto().getPath());
        if (logger.isFine()) {
        	logger.fine("Redirecting successful import to %s.", redirectUrl);
        }
        return "redirect:" + redirectUrl;
    }

    @RequestMapping(value=PAGE_GUEST_SEARCH)
    @ResponseBody
    public ResponseDto searchForPageGuest(@RequestBody PageGuestDto pageGuestDto) {
        ServiceResult<PageGuestDto> resultPageGuestDto = pageService.searchForPageGuest(pageGuestDto);
        ResponseDto responseDto = toResponseDto(resultPageGuestDto);
        return responseDto;
    }


    @RequestMapping(value=PAGE_GUEST_EDIT_GET)
    public ModelAndView pageGuestEditGet(@PathVariable String username, @PathVariable String path, HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("designer");
        User guestUser = sessionManager.getSessionByHttpSession(request.getSession()).getUser();
        ServiceResult<PageDesignerDto> serviceResult = pageService.loadPageGuestEditPage(guestUser, username, path);
        if (serviceResult.isSuccess()) { // page found
            loadPageLoadData(serviceResult.getObject(), mav);
            loadModel(mav);
        } else { // page not found
            throw new PageNotFoundPageResultException();
        }
        return mav;
    }

    @RequestMapping(value=PAGE_GUEST_EDIT_POST, method=RequestMethod.POST)
    @ResponseBody
    public ResponseDto pageGuestEditPost(@RequestBody PageDesignerDto pageDesignerDto, HttpServletRequest request, HttpServletResponse response) {
        User guestUser = sessionManager.getSessionByHttpSession(request.getSession()).getUser();
        PageDto pageDto = pageDesignerDto.getPageDto();
        JspWriterAdapter jspWriterAdapter =
                ioUtil.newUserPageFileWriterResponseAdapter(request,
                        response,
                        PAGE_BUILDER,
                        pageDto.getUsername(), // save it to the owner's directory
                        pageDto.getPath());
        ModelAndView modelStore = new ModelAndView();
        loadModel(modelStore);
        jspWriterAdapter.setModelStore(modelStore);

        ServiceResult<PageDesignerDto> editPageDto = pageService.savePageGuestEdit(pageDto, guestUser, jspWriterAdapter);
        if (logger.isFine()) {
            logger.fine("Returning response for guest edit post for username %s and page path %s.", guestUser.getUsername(), pageDto.getPath());
        }
        ResponseDto responseDto = toResponseDto(editPageDto);
        return responseDto;
    }

    @RequestMapping(value=PAGE_REMOVE_POST, method=RequestMethod.POST)
    @ResponseBody
    public ResponseDto pageRemovePost(@PathVariable String path, HttpSession httpSession) {
    	User user = sessionManager.getSessionByHttpSession(httpSession).getUser();
        ServiceResult<?> serviceResult = pageService.removePage(user, path);

        ResponseDto responseDto = toResponseDto(serviceResult);
        if (serviceResult.isSuccess()) {
            responseDto.targetUrl = UrlConstants.DASHBOARD;
        }
		return responseDto;
    }

    @RequestMapping(value=PAGE_GUESTS_POPUP_GET)
    public ModelAndView pageGuestsPopupGet(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("guests");
        loadModel(mav);
        return mav;
    }

    @RequestMapping(value=PAGE_GUESTS_POPUP_EDIT, method=RequestMethod.POST)
    @ResponseBody
    public ResponseDto pageGuestsPopupPost(@RequestBody PageDesignerDto pageDesignerDto, HttpServletRequest request, HttpServletResponse response) {
        User user = sessionManager.getSessionByHttpSession(request.getSession()).getUser();
        PageDto pageDto = pageDesignerDto.getPageDto();
        JspWriterAdapter jspWriterAdapter =
                ioUtil.newUserPageFileWriterResponseAdapter(request,
                        response,
                        PAGE_BUILDER,
                        user.getUsername(),
                        pageDto.getPath());
        ServiceResult<?> editPageDto = pageService.editPage(pageDesignerDto, user, jspWriterAdapter);
        if (logger.isFine()) {
            logger.fine("Returning response for edit page guest post for username %s and page path %s.", user.getUsername(), pageDto.getPath());
        }
        ResponseDto responseDto = toResponseDto(editPageDto);
        return responseDto;
    }

    @RequestMapping(value=PAGE_DIRECT_EDIT_GET)
    public String pageDirectEditGet(@PathVariable String username, @PathVariable String path, HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("designer");
        User user = sessionManager.getSessionByHttpSession(request.getSession()).getUser();

        // determine if this edit request is being made by the page owner
        String pageOwnerUsername = username.toLowerCase();
        String requestUsername = user.getUsername().toLowerCase();
        boolean isPageOwner = pageOwnerUsername.equals(requestUsername);

        // build URLs for different scenario for redirect
        UrlBuilder urlBuilder = new UrlBuilder(configurationService);
        urlBuilder.setDomainPrefixSecure();
        if (isPageOwner) {
            // build URL for page editing
            urlBuilder.appendEditPage(path);
        } else {
            // determine if guest edit priviledges, then build url for page guest editing
            ServiceResult<Page> serviceResult = pageService.findGuestModifiablePage(user, pageOwnerUsername, path);
            boolean isPageGuestEditor = false;
            if (serviceResult.isSuccess()) {
                Page page = serviceResult.getObject();
                for (PageGuest pageGuest : page.getPageGuests()) {
                    boolean isIdentifiedUser = pageGuest.getGuestUser().getUsername().equals(requestUsername);
                    boolean canModify = pageGuest.canModify();
                    if (isIdentifiedUser && canModify) {
                        isPageGuestEditor = true;
                        break;
                    }
                }
            }
            // is a page guest with modify rights
            if (isPageGuestEditor) {
                urlBuilder.appendGuestEditPage(pageOwnerUsername, path);
            } else {
                // otherwise issue 403
                throw new ForbiddenPageResultException();
            }
        }
        final String redirectUrl = urlBuilder.buildUrl();
        if (logger.isFine()) {
        	logger.fine("About to redirect page direct edit request to URL: %s", redirectUrl);
        }
        return "redirect:" + redirectUrl;
    }

    private void loadTagline(ModelAndView mav, PageDto pageDto, User user) {
	    String formattedString = String.format("%s by %s %s", pageDto.getName(), user.getFirstName(), user.getLastName());
        mav.addObject("tagline", "Jump start your first Valloc Page with a quick import.");
    }
}

