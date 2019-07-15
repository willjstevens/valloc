package com.valloc.web;

import com.valloc.Category;
import com.valloc.Constants;
import com.valloc.UrlBuilder;
import com.valloc.framework.ServiceResult;
import com.valloc.framework.Session;
import com.valloc.framework.SessionManager;
import com.valloc.io.IoUtil;
import com.valloc.io.JspWriterAdapter;
import com.valloc.log.LogManager;
import com.valloc.log.Logger;
import com.valloc.object.domain.User;
import com.valloc.object.dto.AccountDto;
import com.valloc.object.dto.ResponseDto;
import com.valloc.object.persistent.PersistentUser;
import com.valloc.service.AccountService;
import com.valloc.service.ApplicationService;
import com.valloc.service.ConfigurationService;
import com.valloc.service.UtilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

import static com.valloc.Constants.LOGIN_PROCESS_FLAG;
import static com.valloc.Constants.TARGET_URL;
import static com.valloc.UrlConstants.*;

/**
 * Handles requests for the application home page.
 */
@Controller
public class AccountController extends AbstractController
{
	private static final Logger logger = LogManager.manager().newLogger(AccountController.class, Category.CONTROLLER);
	@Autowired private ConfigurationService configurationService;
	@Autowired private AccountService accountService;
	@Autowired private UtilityService utilityService;
	@Autowired private ApplicationService applicationService;
	@Autowired private SessionManager sessionManager;
    @Autowired private IoUtil ioFactory;
	private Map<String, String> accountMessageCache = new HashMap<>();
	private Map<String, String> verificationMessageCache = new HashMap<>();

	@RequestMapping(value=ACCOUNT_CREATE)	 
	public ModelAndView createGet() {
		ModelAndView model = new ModelAndView();
		model.setViewName("account");
		loadModel(model);
		addMessageReplacement(model, "account_pageTitle_create", "account_pageTitle");
		return model;
	}

	@RequestMapping(value=ACCOUNT_CREATE_POST, method=RequestMethod.POST)
	@ResponseBody
	public ResponseDto createPost(HttpServletRequest request,
									HttpServletResponse response,
									@RequestParam String username,
									@RequestParam String email,
									@RequestParam String firstName,
									@RequestParam String lastName,
									@RequestParam String password,
									@RequestParam boolean inAgreement)
	{
		ResponseDto responseDto = null;
		AccountForm accountForm =
                new AccountForm(username, email, firstName, lastName, password, inAgreement);

		ServiceResult<PersistentUser> serviceResult = accountService.addUser(accountForm);

		if (serviceResult.isSuccess()) {
			// success so now stage send the email
			PersistentUser user = serviceResult.getObject();
			String encodedVerificationCode = utilityService.urlEncode(user.getVerificationCode());
			String accountVerificationLink = buildVerificationLink(user.getEmail(), encodedVerificationCode);
			// write JSP contents to string writer and get email body
			JspWriterAdapter emailWriter = ioFactory.newStringWriterJspAdapter(request, response, EMAIL_ACCOUNT_VERIFICATION);
			request.setAttribute("accountVerificationLink", accountVerificationLink);
			request.setAttribute("logoBannerUrl", buildLogoBannerUrl());
			emailWriter.write();
			String emailBody = emailWriter.getContents();
			// send email
			String emailSubject = applicationService.getUserMessage("account_emailVerification_subject").getMessage();
			utilityService.sendHtmlEmail(user.getEmail(), emailSubject, emailBody);
            // now that we are done log the user out so he cannot go directly to dashboard before verifying
            processLogout(request, response);
		}

		responseDto = toResponseDto(serviceResult);
		return responseDto;
	}
	
	@RequestMapping(value=ACCOUNT_CREATE_COMPLETE)
	public ModelAndView createComplete() {
		ModelAndView model = new ModelAndView();
		model.setViewName("verification");
        loadModel(model);
		model.addObject("viewPart",  "createComplete");
		return model;
	}

	@RequestMapping(value=ACCOUNT_VERIFY)
	public String verifyAccount(@RequestParam String email, @RequestParam String vc) {
		String redirect = "redirect:";
		ServiceResult<?> serviceResult = accountService.verifyAccount(email, vc);
		switch (serviceResult.getViewName()) {
		case "success": 		redirect += ACCOUNT_VERIFY_SUCCESS;				break;
		case "badVerification":	redirect += ACCOUNT_VERIFY_BAD_VERIFICATION;	break;
		case "expiredCode": 	redirect += ACCOUNT_VERIFY_EXPIRED_CODE;		break;
		case "verifyCompleted": redirect += ACCOUNT_VERIFY_COMPLETED;			break;
		default: throw new IllegalArgumentException("Unknown viewname: " + serviceResult.getViewName());
		}
		return redirect;
	}

	@RequestMapping(value=ACCOUNT_VERIFY_SUCCESS)
	public ModelAndView verificationCompleteSuccess() {
		ModelAndView model = new ModelAndView();
		model.setViewName("verification");
        loadModel(model);
		model.addObject("viewPart", "success");
		return model;
	}

	@RequestMapping(value=ACCOUNT_VERIFY_BAD_VERIFICATION)
	public ModelAndView verificationCompleteBadVerification() {
		ModelAndView model = new ModelAndView();
		model.setViewName("verification");
        loadModel(model);
		model.addObject("viewPart", "badVerification");
		return model;
	}
	
	@RequestMapping(value=ACCOUNT_VERIFY_EXPIRED_CODE)
	public ModelAndView verificationCompleteExpiredCode() {
		ModelAndView model = new ModelAndView();
		model.setViewName("verification");
        loadModel(model);
		model.addObject("viewPart", "expiredCode");
		return model;
	}
	
	@RequestMapping(value=ACCOUNT_VERIFY_COMPLETED)
	public ModelAndView verificationAlreadyCompleted() {
		ModelAndView model = new ModelAndView();
		model.setViewName("verification");
        loadModel(model);
		model.addObject("viewPart", "verifyCompleted");
		return model;
	}
	
	@RequestMapping(value=ACCOUNT_EDIT)	 
	public ModelAndView editGet(HttpSession httpSession) {
		ModelAndView model = new ModelAndView();
		model.setViewName("account");
        loadModel(model);
		addMessageReplacement(model, "account_pageTitle_edit", "account_pageTitle");
        String username = sessionManager.getSessionByHttpSession(httpSession).getUsername();
        ServiceResult<PersistentUser> serviceResult = accountService.findUserByUsername(username);
        User user = serviceResult.getObject();
        AccountDto accountDto = AccountDto.userToAccountDto(user);
        accountDto.setBirthdate(accountService.formatBirtdate(user.getBirthdate()));
        String json = utilityService.jsonStringify(accountDto);
        if (logger.isFiner()) {
            logger.finer("Account edit page JSON data:\n%s", json);
        }
        model.addObject("pld", json);
		return model;
	}

	@RequestMapping(value=ACCOUNT_EDIT_POST, method=RequestMethod.POST)
	@ResponseBody 
	public ResponseDto editPost(@RequestParam String password,									
									@RequestParam String passwordConfirmation,									
									@RequestParam String passwordQuestion,
									@RequestParam String passwordAnswer,
									HttpSession httpSession) {
		ResponseDto responseDto = null;
		String username = sessionManager.getSessionByHttpSession(httpSession).getUsername();
		AccountForm accountForm = new AccountForm(username, password, passwordConfirmation, passwordQuestion, passwordAnswer);
		ServiceResult<?> serviceResult = accountService.editUser(accountForm);
		responseDto = toResponseDto(serviceResult);
		return responseDto;
	}

    @RequestMapping(value=LOGIN)
    public ModelAndView loginGet() {
        ModelAndView model = new ModelAndView();
        model.setViewName("login");
        loadModel(model);
        return model;
    }

    @RequestMapping(value=LOGIN_POST, method=RequestMethod.POST)
    @ResponseBody
    public ResponseDto loginPost(@RequestParam String username, @RequestParam String password, HttpServletRequest request, HttpServletResponse response) {
        ResponseDto responseDto = null;

        ServiceResult<Session> serviceResult = accountService.loginByUsernamePassword(username, password);
        if (serviceResult.isSuccess()) {
            Session session = serviceResult.getObject();
            HttpSession httpSession = request.getSession();
            sessionManager.commitSession(session, httpSession);
            // ensure cookie is response for future hits
            String cookieValue = session.getUser().getCookieValue();
            Cookie sessionCookie = applicationService.findSessionCookie(request.getCookies());
            if (sessionCookie != null) { // either not created yet or cleared from logout
            	if (logger.isFiner()) {
            		logger.finer("CookieTracing: sessionCookie found for user %s with path of %s.", username, sessionCookie.getPath());
				}
                // if the old cookie value does not equal what is sitting on the user object, then reset with
                //      a whole new cookie (including expirey); this could happen if a user logged out
                if (!cookieValue.equals(sessionCookie.getValue())) {
                    sessionCookie = applicationService.newSessionCookie(cookieValue);
					if (logger.isFiner()) {
						logger.finer("CookieTracing: sessionCookie found for user %s but the cookie needed to be recreated.", username);
					}
                }
            } else {
                sessionCookie = applicationService.newSessionCookie(cookieValue); // create a new cookie with that
				if (logger.isFiner()) {
					logger.finer("CookieTracing: No sessionCookie found for user %s so the cookie needed to be created new.", username);
				}
            }
            if (sessionCookie != null) {
                String cookiePath = sessionCookie.getPath();
            	if (!Constants.FORWARD_SLASH_STR.equals(cookiePath)) {
            		String message = String.format("CookieBug: Made attempted resolve for vsk cookie with non-root path value of: \"%s\" for username: %s.", sessionCookie.getPath(), username);
					logger.warn(message);
					utilityService.sendTextEmail("willjstevens@gmail.com", "COOKIE BUG", message);

					if (cookiePath == null || cookiePath.equals("/a")) {
						// first capture old cookie value and set it to delete
						Cookie deleteMeCookie = sessionCookie;
						deleteMeCookie.setMaxAge(0);
						response.addCookie(deleteMeCookie); // add this cookie to delete it
						logger.warn("SessionCookieBug: setting DELETE cookie with path \"%s\" with a new one for user: \"%s\"", cookiePath, username);

						// second re-create a good, new cookie to overwrite the old one
						// 	it will be different cookie, distinguished by a different path
						//	see: https://humanwhocodes.com/blog/2009/05/05/http-cookies-explained/
                        sessionCookie = applicationService.newSessionCookie(cookieValue);

                        logger.warn("SessionCookieBug: Overwrote null or /a cookie with new one for user: " + username);
                    }
				}
			} else {
				logger.warn("SessionCookieBug: Found sessionCookie NULL. But why?");
				utilityService.sendTextEmail("willjstevens@gmail.com", "COOKIE BUG", "Found SessionCookieBug bug of sessionCookie to be null for user: " + username);
			}
            response.addCookie(sessionCookie);
            // clear flag for login process since now successful
            httpSession.removeAttribute(LOGIN_PROCESS_FLAG);

            // Now determine and return view name.
            String successfulLoginTargetUrl = DASHBOARD; // default to /dashboard
            String targetSecureUrl = (String) httpSession.getAttribute(TARGET_URL);
            if (targetSecureUrl != null && !targetSecureUrl.equals(LOGIN)) {
                // If targetSecureUri is set, then the user originally desired to hit some other secured URL, like /dashboard or /account.
                successfulLoginTargetUrl = targetSecureUrl;
            }
            serviceResult.setViewName(successfulLoginTargetUrl);
            if (logger.isFine()) {
                logger.fine("Successfully logged in user %s.", username);
            }

            // now we are done so remove session object from service result before converting to DTO
            serviceResult.setObject(null);
        }

        responseDto = toResponseDto(serviceResult);
        return responseDto;
    }

    @RequestMapping(value=LOGOUT)
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        processLogout(request, response);

        // redirect to non-secure homepage
        UrlBuilder homePageBuilder = new UrlBuilder(configurationService);
        homePageBuilder.setDomainPrefixPlain();
        String homePage = homePageBuilder.buildUrl();
        if (logger.isFine()) {
            logger.fine("After logout, redirecting to %s.", homePage);
        }
        return "redirect:" + homePage;
    }

    private void processLogout(HttpServletRequest request, HttpServletResponse response) {
        HttpSession httpSession = request.getSession();
        Session session = sessionManager.getSessionByHttpSession(httpSession);
        if (session != null) { // we have a logged-in session
            String username = session.getUsername();
            accountService.logout(username);
            Cookie deleteThis = applicationService.findSessionCookie(request.getCookies());
            if (deleteThis != null) {
                deleteThis.setMaxAge(0);
                response.addCookie(deleteThis); // Will be deleted when flushed out to the browser.
            }

            sessionManager.removeSession(httpSession);
            if (logger.isFine()) {
                logger.fine("Successfully logged out user %s.", username);
            }
        }
    }

    private String buildVerificationLink(String email, String encodedVerificationCode) {
		UrlBuilder accountVerificationLinkBuilder = new UrlBuilder(configurationService);
		accountVerificationLinkBuilder.setDomainPrefixSecure();
		accountVerificationLinkBuilder.appendActionPathPart(ACCOUNT_VERIFY);
		accountVerificationLinkBuilder.addRequestParameterPair("email", email);
		accountVerificationLinkBuilder.addRequestParameterPair("vc", encodedVerificationCode);
		return accountVerificationLinkBuilder.buildUrl();
	}

	private String buildLogoBannerUrl() {
		UrlBuilder logoUrlBuilder = new UrlBuilder(configurationService);
		logoUrlBuilder.setDomainPrefixSecure();
		logoUrlBuilder.appendActionPathPart(LOGO_BANNER);
		return logoUrlBuilder.buildUrl();
	}

}
