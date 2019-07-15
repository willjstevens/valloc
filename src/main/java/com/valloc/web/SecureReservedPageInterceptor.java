/*
 * Property of Will Stevens
 * All rights reserved.
 */
package com.valloc.web;

import com.valloc.Category;
import com.valloc.UrlBuilder;
import com.valloc.UrlConstants;
import com.valloc.framework.ServiceResult;
import com.valloc.framework.Session;
import com.valloc.framework.SessionManager;
import com.valloc.log.LogManager;
import com.valloc.log.Logger;
import com.valloc.service.AccountService;
import com.valloc.service.ApplicationService;
import com.valloc.service.ConfigurationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static com.valloc.Constants.LOGIN_PROCESS_FLAG;
import static com.valloc.Constants.TARGET_URL;
import static com.valloc.UrlConstants.LOGIN;

/**
 * Intercepts access to select secured resources.
 *
 * 
 * @author wstevens
 */
public class SecureReservedPageInterceptor extends HandlerInterceptorAdapter
{
	private static final Logger logger = LogManager.manager().newLogger(SecureReservedPageInterceptor.class, Category.INTERCEPTOR_SECURE_RESERVED);
	@Autowired private AccountService accountService;
	@Autowired private ApplicationService applicationService;
	@Autowired private ConfigurationService configurationService;
	@Autowired private SessionManager sessionManager;

	public SecureReservedPageInterceptor(AccountService accountService,
			ApplicationService applicationService,
			ConfigurationService configurationService,
			SessionManager sessionManager) {
		super();
		this.accountService = accountService;
		this.applicationService = applicationService;
		this.configurationService = configurationService;
		this.sessionManager = sessionManager;
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		boolean doPassRequest = false;		
		HttpSession httpSession = request.getSession();

		// First check if we are in some login phase for session establishment.
		boolean isWithinLoginProcess =
				httpSession.getAttribute(LOGIN_PROCESS_FLAG) != null &&
				httpSession.getAttribute(TARGET_URL) == null;
		if (isWithinLoginProcess) {
			// If so implicitly pass all login requests on for validation and login.
			return super.preHandle(request, response, handler);
		}
	
		//	first check if session is present for user on server, then he is already logged in
		boolean isLoggedIn = sessionManager.isSessionPresent(httpSession);
		if (!isLoggedIn) {
			// otherwise if not logged in, try and sign-in via cookie
			Cookie cookie = applicationService.findSessionCookie(request.getCookies());
			if (cookie != null) {
				if (logger.isFiner()) {
					logger.finer("CookieTracing: cookie found for cookie value \"%s\" with path of %s.", cookie.getValue(), cookie.getPath());
				}
				// cookie is present, so try and sign in via session cookie
				ServiceResult<Session> serviceResult = accountService.loginByCookie(cookie.getValue());
				if (serviceResult.isSuccess()) {
					Session session = serviceResult.getObject();
					sessionManager.commitSession(session, httpSession);
					isLoggedIn = true;
					if (logger.isFiner()) {
						logger.finer("CookieTracing: Logged in user %s by cookie with path %s.", session.getUsername(), cookie.getPath());
					}
				}
			}
		}

        // build the full path and query string to default to relative URI
		String targetLocation = request.getRequestURI();
        String queryString = request.getQueryString();
        if (queryString != null) {
            targetLocation += UrlConstants.QUERY_STRING_SEPARATOR + queryString;
        }

		// determine if the request for a secure page is over SSL, if not then rebuild URL for HTTPS 
		boolean isOverSsl = applicationService.isOverSsl(request);

		// if logged in then allow to proceed
        if (isOverSsl) {
            if (isLoggedIn) {
                // proceed into controllers
                doPassRequest = super.preHandle(request, response, handler);
            } else if (!targetLocation.endsWith(LOGIN)) {
                // if user is still not logged in (via session or cookie) then redirect to login screen
                UrlBuilder urlBuilder = new UrlBuilder(configurationService);
                urlBuilder.setDomainPrefixSecure();
                urlBuilder.appendActionPathPart(LOGIN);
                String loginRedirectUrl = urlBuilder.buildUrl();
                // save off target URL and mark for login process
                httpSession.setAttribute(TARGET_URL, targetLocation);
                httpSession.setAttribute(LOGIN_PROCESS_FLAG, new Object());
                // issue redirect to login URL, but with 302 found redirect since we are temporarily shifting to login before returning to target URL
                redirect(response, loginRedirectUrl, HttpServletResponse.SC_FOUND);

            } else if (targetLocation.endsWith(LOGIN)) {
                // pass it to controller to serve up GET
                doPassRequest = super.preHandle(request, response, handler);
            }
        } else {
            // if not over SSL, the redirect to target location under SSL
            // build and set target URL for after logging in
            UrlBuilder urlBuilder = new UrlBuilder(configurationService);
            urlBuilder.setDomainPrefixSecure();
            urlBuilder.appendActionPathPart(request.getRequestURI());
            targetLocation = urlBuilder.buildUrl();
            redirect(response, targetLocation, HttpServletResponse.SC_MOVED_PERMANENTLY);
        }
		
		return doPassRequest;
	}
		
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		super.postHandle(request, response, handler, modelAndView);
	}

	private void redirect(HttpServletResponse response, String redirectUrl, int statusCode) {
		String encodedRedirectUrl = response.encodeRedirectURL(redirectUrl);
		if (logger.isFiner()) {
			logger.finer("Encoded original redirect URL (%s) into: %s.", redirectUrl, encodedRedirectUrl);
		}
		response.setStatus(statusCode); // found since URL is here but being temporarily redirected
		try {
			response.sendRedirect(encodedRedirectUrl);
			if (logger.isFine()) {
				logger.fine("Successfully redirected with %d to: %s.", statusCode, encodedRedirectUrl);
			}
		} catch (Exception e) {
			logger.error("Error occured when redirecting to URL %s.", e, encodedRedirectUrl);
		}
	}
}
