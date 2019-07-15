/*
 * Property of Will Stevens
 * All rights reserved.
 */
package com.valloc.serve;

import com.valloc.Category;
import com.valloc.framework.ServiceResult;
import com.valloc.framework.Session;
import com.valloc.framework.SessionManager;
import com.valloc.log.LogManager;
import com.valloc.log.Logger;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpSession;

/**
 *
 *
 * 
 * @author wstevens
 */
class SessionStage extends AbstractServeStage
{
	private static final Logger logger = LogManager.manager().newLogger(SessionStage.class, Category.SERVICE_PAGE_SERVE);
	
	public SessionStage(PageServeService pageServeService) {
		super(pageServeService);
	}

	@Override
	public boolean execute() {
		HttpSession httpSession = request.getSession();
		SessionManager sessionManager = pageServeService.getSessionManager();
		//	1. First check for vsession object; this should be done by passing the HttpSession object to the session manager,
		//		which should return the Valloc Session object.  Is it necessary to return it or just a boolean indicating presence?
		//		Doing this indicates the user already has an active Session created on the server so we are logged in.
		boolean isLoggedIn = sessionManager.isSessionPresent(httpSession);
		if (isLoggedIn) {
			// if this far then ready for next stage and invoke
			setNextStage(new AuthorizeStage(pageServeService));
			if (logger.isFine()) {
				logger.fine("The user is logged in with a session. Graduating to authorize stage for page key: %s.", pageKey);
			}
			return true;
		}
		
		//	2. If there is no Session object, then we check for the cookie. This could be a valid scenario as the user is hitting the 
		//		system after sometime, when the server passivated the Session or was restarted.  In that case we need to reestablish 
		//		a valid Session by logging in by Cookie value.
		Cookie cookie = pageServeService.getApplicationService().findSessionCookie(request.getCookies());
		if (cookie != null) {
			ServiceResult<Session> serviceResult = pageServeService.getAccountService().loginByCookie(cookie.getValue());
			if (serviceResult.isSuccess()) { // only not null if the user was found
				Session session = serviceResult.getObject();
				sessionManager.commitSession(session, httpSession);
				setNextStage(new AuthorizeStage(pageServeService));
				if (logger.isFine()) {
					logger.fine("The user logged in via cookie and created a session. Graduating to authorize stage for page key: %s.", pageKey);
				}
				return true;
			}
		}
		
		setNextStage(new LoginStage(pageServeService)); // no cookie set so redirect to login stage
		if (logger.isFine()) {
			logger.fine("Routing to login stage for page key: %s.", pageKey);
		}
		return true;
	}

}
