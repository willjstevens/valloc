/*
 * Property of Will Stevens
 * All rights reserved.
 */
package com.valloc.serve;

import com.valloc.Category;
import com.valloc.UrlBuilder;
import com.valloc.UrlConstants;
import com.valloc.log.LogManager;
import com.valloc.log.Logger;

import javax.servlet.http.HttpSession;

import static com.valloc.Constants.LOGIN_PROCESS_FLAG;
import static com.valloc.Constants.TARGET_URL;

/**
 *
 *
 * 
 * @author wstevens
 */
class LoginStage extends AbstractServeStage
{
	private static final Logger logger = LogManager.manager().newLogger(LoginStage.class, Category.SERVICE_PAGE_SERVE);

	public LoginStage(PageServeService pageServeService) {
		super(pageServeService);
	}

	@Override
	public boolean execute() {
		// Transfer target URI directly into HttpSession since we have no application session created
		//		yet.  It will then be redirected to and removed once login is successful.
		HttpSession httpSession = request.getSession();
		httpSession.setAttribute(TARGET_URL, request.getRequestURI());
		httpSession.setAttribute(LOGIN_PROCESS_FLAG, new Object());
		
		// Setup and issue redirect.
//		cancelContinuance();
		// build /login redirect URL
		UrlBuilder urlBuilder = new UrlBuilder(pageServeService.getConfigurationService());
		urlBuilder.setDomainPrefixSecure(); // SSL for logging in at /login
		urlBuilder.appendActionPathPart(UrlConstants.LOGIN);
		String loginRedirectUrl = urlBuilder.buildUrl();
		redirect(loginRedirectUrl);
		if (logger.isFine()) {
			logger.fine("Login redirect sent to user, and pre-graduating to ETAG stage for when user re-enters, for page key: %s.", pageKey);
		}
				
		// Let AccountController and Spring do login handling, but eventually on successful 
		//		login the target URI will be reset and user will need to come back in here for user page
		//		handling with ETag and custom response writes.  Therefore set ETag as next successor for requests.
		setNextStage(new EtagStage(pageServeService));
		return false;
	}

}
