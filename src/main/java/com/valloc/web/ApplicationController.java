package com.valloc.web;

import com.valloc.Category;
import com.valloc.Constants;
import com.valloc.Util;
import com.valloc.framework.ServiceResult;
import com.valloc.framework.SessionManager;
import com.valloc.io.IoUtil;
import com.valloc.io.JspWriterAdapter;
import com.valloc.log.LogManager;
import com.valloc.log.Logger;
import com.valloc.object.domain.Feedback;
import com.valloc.object.domain.LinkServe;
import com.valloc.object.domain.User;
import com.valloc.object.domain.UserMessage;
import com.valloc.object.dto.ClientDataDto;
import com.valloc.object.dto.PageDto;
import com.valloc.object.dto.PinLinkPageDto;
import com.valloc.object.dto.ResponseDto;
import com.valloc.object.pojo.FeedbackPojo;
import com.valloc.object.pojo.LinkServePojo;
import com.valloc.service.ApplicationService;
import com.valloc.service.DashboardService;
import com.valloc.service.PageService;
import com.valloc.service.UtilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

import static com.valloc.Constants.REPLACEMENT_TOKEN_0;
import static com.valloc.UrlConstants.*;

/**
 * Handles requests for the application home page.
 */
@Controller
public class ApplicationController extends AbstractController
{
	private static final Logger logger = LogManager.manager().newLogger(ApplicationController.class, Category.CONTROLLER);
	@Autowired private ApplicationService applicationService;
    @Autowired private UtilityService utilityService;
    @Autowired private DashboardService dashboardService;
    @Autowired private PageService pageService;
    @Autowired private SessionManager sessionManager;
    @Autowired private IoUtil ioUtil;

    @RequestMapping(value=HOME_PAGE)
    public ModelAndView home() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("home-1.2");
        loadModel(mav);
        return mav;
    }

    @RequestMapping(value="/home-1.1")
    public ModelAndView archivedHome2() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("archive/home-1.1");
        loadModel(mav);
        return mav;
    }

    @RequestMapping(value="/home-1.0")
    public ModelAndView archivedHome1() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("archive/home-1.0");
        loadModel(mav);
        return mav;
    }

    @RequestMapping(value=INVALID_BROWSER)
    public ModelAndView invalidBrowser() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("error/invalid-browser");
        loadModel(mav);
        return mav;
    }

    @RequestMapping(value=QUICK_START)
    public ModelAndView quickStart() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("quick-start");
        loadModel(mav);
        mav.addObject("tagline", "Jump start your first Valloc Page with a quick import.");
        return mav;
    }

    @RequestMapping(value=SERVE_PAGE)
    public void serve(@RequestParam String url, @RequestParam int uid, @RequestParam int pid, HttpServletRequest request, HttpServletResponse response) {
        String decodedUrl = url;
        decodedUrl = utilityService.urlDecode(url);
        LinkServe linkServe = new LinkServePojo();
        linkServe.setRequestUrl(decodedUrl);
        linkServe.setOwnerUserId(uid);
        linkServe.setOwnerPageId(pid);
        linkServe.setServeTimestamp(Util.now());
        applicationService.addLinkServe(linkServe);

        // should this status be HttpServletResponse.SC_MOVED_TEMPORARY ?
        response.setStatus(HttpServletResponse.SC_FOUND);
        response.setHeader("Referer", Constants.VALLOC_COM);
        try {
            response.sendRedirect(decodedUrl);
        } catch (IOException e) {
           logger.error("Error when attempting to redirect serve request to %s for user %d.", e, decodedUrl, uid);
        }
    }

	@RequestMapping(value="/error/{errorCode}") 
	public ModelAndView error(@PathVariable String errorCode) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("error/" + errorCode);

		int error = 500;
		try {
			error = Integer.parseInt(errorCode);
		} catch (NumberFormatException e) {
			logger.warn("Someone tried to put bogus error code (%s) into URL.", errorCode); 
		}

        UserMessage errorTitle = applicationService.getUserMessage("error_title");
		String title = Util.replaceTokenWithInt(errorTitle.getMessage(), REPLACEMENT_TOKEN_0, error);

		String message = "Unitialized."; 
		switch (error) {
        case 400: message = "error_400_message";	break;
		case 403: message = "error_403_message";	break;
        case 404: message = "error_404_message";	break;
		case 500: message = "error_500_message";	break;
		default: throw new IllegalArgumentException("Unrecognized error code: " + error);
		}
		message = applicationService.getUserMessage(message).getMessage();
		mav.addObject("error_title", title);
		mav.addObject("error_message", message);

        loadModel(mav);

		return mav;
	}
	
	@RequestMapping(value=LOAD_APP_DATA)
	@ResponseBody 
	public ClientDataDto loadAppData(@RequestParam String publishDate, HttpServletRequest request, HttpServletResponse response) {
		ClientDataDto clientDataDto = null;
		if (logger.isInfo()) {
			logger.info("Client publishDate is \"%s\"", publishDate);
		}
		clientDataDto = applicationService.getAppDataDto(publishDate);
		return clientDataDto;
	}

    @RequestMapping(value=PRIVACY_POLICY)
    public ModelAndView privacyPolicy() {
        ModelAndView model = new ModelAndView();
        model.setViewName("privacy-policy");
        loadModel(model);
        return model;
    }

    @RequestMapping(value=TERMS_OF_SERVICE)
    public ModelAndView termsOfService() {
        ModelAndView model = new ModelAndView();
        model.setViewName("terms-of-service");
        loadModel(model);
        return model;
    }

    @RequestMapping(value=HOW_IT_WORKS)
    public ModelAndView howItWorks() {
        ModelAndView model = new ModelAndView();
        model.setViewName("how-it-works");
        loadModel(model);
        model.addObject("tagline", "Learn how no-clutter bookmarking works. Everything from page illustrations, to the visual page designer, the pin-it button and more.");
        return model;
    }

    @RequestMapping(value=FEEDBACK)
    public ModelAndView feedbackGet() {
        ModelAndView model = new ModelAndView();
        model.setViewName("feedback");
        loadModel(model);
        return model;
    }

    @RequestMapping(value=FEEDBACK_POST, method=RequestMethod.POST)
    @ResponseBody
    public ResponseDto feedbackPost(@RequestParam String name, @RequestParam String email, @RequestParam String comment) {
        ResponseDto responseDto = null;

        Feedback feedback = new FeedbackPojo(name, email, "uncategorized", comment);
        ServiceResult<UserMessage> serviceResult = applicationService.addFeedback(feedback);

        responseDto = toResponseDto(serviceResult);
        return responseDto;
    }

    @RequestMapping(value=CONTACT_US_POST, method=RequestMethod.POST)
    @ResponseBody
    public ResponseDto contactUsPost(@RequestParam String name, @RequestParam String email, @RequestParam String comment) {
        ResponseDto responseDto = null;

        Feedback feedback = new FeedbackPojo(name, email, "uncategorized", comment);
        ServiceResult<UserMessage> serviceResult = applicationService.addFeedback(feedback);

        responseDto = toResponseDto(serviceResult);
        return responseDto;
    }

    @RequestMapping(value=PIN_LINK)
    public ModelAndView pinGet(@RequestParam("t") String title, @RequestParam("u") String url, HttpSession httpSession) {
        ModelAndView model = new ModelAndView();
        // retrieve pages and sections for user
        User user = sessionManager.getSessionByHttpSession(httpSession).getUser();
        ServiceResult<List<PageDto>> serviceResult = dashboardService.getUserPagesAndSections(user);
        // load dto
        PinLinkPageDto pinLinkPageDto = new PinLinkPageDto();
        pinLinkPageDto.pages = serviceResult.getObject();
        pinLinkPageDto.linkTitle = title;
        pinLinkPageDto.linkUrl = url;
        loadPageLoadData(pinLinkPageDto, model);
        // load the model for rendering
        model.setViewName("pin-link");
        loadModel(model);
        addValue(model, "linkTitle", utilityService.urlDecode(title));
        addValue(model, "linkUrl", utilityService.urlDecode(url));
        return model;
    }

    @RequestMapping(value= PIN_LINK_POST, method=RequestMethod.POST)
    @ResponseBody
    public ResponseDto pinPost(@RequestParam String pagePath,
                               @RequestParam String sectionName,
                               @RequestParam String linkTitle,
                               @RequestParam String linkUrl,
                               @RequestParam(required=false) String linkNote,
                               HttpSession httpSession,
                               HttpServletRequest request,
                               HttpServletResponse response) {
        ResponseDto responseDto = null;

        User user = sessionManager.getSessionByHttpSession(httpSession).getUser();
        JspWriterAdapter jspWriterAdapter =
                ioUtil.newUserPageFileWriterResponseAdapter(request,
                        response,
                        PAGE_BUILDER,
                        user.getUsername(),
                        pagePath);
        ModelAndView modelStore = new ModelAndView();
        loadModel(modelStore);
        jspWriterAdapter.setModelStore(modelStore);

        ServiceResult<?> serviceResult = pageService.pinLink(user, pagePath, sectionName, linkTitle, linkUrl, linkNote, jspWriterAdapter);

        responseDto = toResponseDto(serviceResult);
        return responseDto;
    }
}

