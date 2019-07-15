/*
 * Property of Will Stevens
 * All rights reserved.
 */
package com.valloc.web;

import com.valloc.Category;
import com.valloc.UrlBuilder;
import com.valloc.framework.SessionManager;
import com.valloc.log.LogManager;
import com.valloc.log.Logger;
import com.valloc.service.AccountService;
import com.valloc.service.ApplicationService;
import com.valloc.service.ConfigurationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 *
 * 
 * @author wstevens
 */
public class RegistrationPageInterceptor extends HandlerInterceptorAdapter //implements ApplicationContextAware
{
	private static final Logger logger = LogManager.manager().newLogger(RegistrationPageInterceptor.class, Category.INTERCEPTOR_SECURE_RESERVED);
	@Autowired private AccountService accountService;
	@Autowired private ApplicationService applicationService;
	@Autowired private ConfigurationService configurationService;
	@Autowired private SessionManager sessionManager;

	public RegistrationPageInterceptor(AccountService accountService,
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
		String targetLocation = request.getRequestURI(); // default to relative URI
		// determine if the request for a secure page is over SSL, if not then rebuild URL for HTTPS 
		boolean isOverSsl = applicationService.isOverSsl(request);
		if (isOverSsl) {
            doPassRequest = true;
        } else {
			// build and set target URL for over SSL
			UrlBuilder urlBuilder = new UrlBuilder(configurationService);
			urlBuilder.setDomainPrefixSecure();
			urlBuilder.appendActionPathPart(request.getRequestURI());
			targetLocation = urlBuilder.buildUrl();

            // if not over SSL then issue redirect to target location, with permanent 301 redirect since it's always over SSL
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
