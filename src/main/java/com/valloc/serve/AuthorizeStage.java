/*
 * Property of Will Stevens
 * All rights reserved.
 */
package com.valloc.serve;

import com.valloc.Category;
import com.valloc.framework.Session;
import com.valloc.framework.SessionManager;
import com.valloc.log.LogManager;
import com.valloc.log.Logger;

import javax.servlet.http.HttpServletResponse;

/**
 *
 *
 * 
 * @author wstevens
 */
class AuthorizeStage extends AbstractServeStage
{
	private static final Logger logger = LogManager.manager().newLogger(LoginStage.class, Category.SERVICE_PAGE_SERVE);

	/**
	 * @param pageServeService
	 */
	public AuthorizeStage(PageServeService pageServeService) {
		super(pageServeService);
	}

	@Override
	public boolean execute() {
		// first get the username of the user who owns the page
		String ownerUsername = pageKey.getUsername();
		// then retrieve the username of the user currently with a session in the system, requesting the page:
		SessionManager sessionManager = pageServeService.getSessionManager();
		Session session = sessionManager.getSessionByHttpSession(request.getSession());
		String requesterUsername = session.getUser().getUsername();
        requesterUsername = requesterUsername.toLowerCase(); // conditionalize
		// authorized if the requester is the page owner or a page guest
		final boolean isAuthorized = requesterUsername.equals(ownerUsername) || pageCache.userHasAccess(requesterUsername); 
		if (isAuthorized) {
			setNextStage(new EtagStage(pageServeService));
			if (logger.isFine()) {
				logger.fine("Requester with username %s is granted access to the following page. Graduating to ETAG stage for page key %s.", requesterUsername, pageKey);
			}
			return true;
		} else {
			if (logger.isInfo()) {
				logger.info("Illegal access by %s to private page for user %s for page key: %s.", requesterUsername, ownerUsername, pageKey);
			}
			cleanupPageStage();
			sendError(HttpServletResponse.SC_FORBIDDEN);
		}
		return false;
	}

}
