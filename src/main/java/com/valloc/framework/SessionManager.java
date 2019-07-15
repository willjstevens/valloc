/*
 * Property of Will Stevens
 * All rights reserved.
 */
package com.valloc.framework;

import com.valloc.Category;
import com.valloc.Constants;
import com.valloc.log.LogManager;
import com.valloc.log.Logger;
import com.valloc.object.domain.User;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 *
 *
 * 
 * @author wstevens
 */
public class SessionManager
{
	private static final Logger logger = LogManager.manager().newLogger(SessionManager.class, Category.SESSION);
	private final Map<String, Session> usernameToSessionLookup = new HashMap<String, Session>();
	private final Map<String, Session> cookieToSessionLookup = new HashMap<String, Session>();
	private final Map<String, Session> httpSessionIdLookup = new HashMap<String, Session>();
	
	public void createStubSession(String username, HttpSession httpSession) {
		Session session = new Session();
		session.setHttpSession(httpSession);
		httpSessionIdLookup.put(httpSession.getId(), session);
	}
	
	public void updateUser(User user) {
		Session session = usernameToSessionLookup.get(user.getUsername());
		session.setUser(user);
	}
	
	public Session createAndLoadPreliminarySession(User user) {
		Session session = new Session(user);
		cookieToSessionLookup.put(user.getCookieValue(), session);
		usernameToSessionLookup.put(user.getUsername(), session);
		if (logger.isFine()) {
			logger.fine("Created and loaded new session for user %s.", user);
		}
		return session;
	}
		
	public void commitSession(Session session, HttpSession httpSession) {
		session.setHttpSession(httpSession);
		httpSessionIdLookup.put(httpSession.getId(), session);
		httpSession.setAttribute(Constants.VSESSION_KEY, session);
//		String targetUrl = (String) httpSession.getAttribute(Constants.TARGET_URL);
//		if (targetUrl != null) {
//			session.setTargetUrl(targetUrl);
//			httpSession.removeAttribute(Constants.TARGET_URL);
//			if (logger.isFine()) {
//				logger.fine("Set target URI to be \"%s\" for user %s.", targetUrl, session.getUser());
//			}
//		}
		if (logger.isFine()) {
			logger.fine("Commited session for user %s.", session.getUser());
		}
	}
	
	public Session getSessionByHttpSession(HttpSession httpSession) {
		return httpSessionIdLookup.get(httpSession.getId());
	}
	
	public boolean isSessionPresent(HttpSession httpSession) {
		return getSessionByHttpSession(httpSession) != null;
	}
	
	public User getSessionUser(String username) {
		return getSessionByUsername(username).getUser();
	}
	
	public Session getSessionByUsername(String username) {
		return usernameToSessionLookup.get(username);
	}
	
	public Session getSessionByUser(User user) {
		return usernameToSessionLookup.get(user.getUsername());
	}
	
//	public Session getSessionByCookieValue(String cookieValue) {
//		return cookieToSessionLookup.get(cookieValue);
//	}
	
	public boolean removeSession(HttpSession httpSession) {
		boolean didLogout = false;
		Session session = httpSessionIdLookup.get(httpSession.getId());
		didLogout = removeSession(session);			 
		return didLogout;
	}
	
	public boolean removeSession(Session session) {
		boolean didLogout = false;
		// Could be null if user already logged out and just issued another /logout GET.
		if (session != null) { 
			User user = session.getUser();
//			pageService.cleanupUserResources(user);
			HttpSession httpSession = session.getHttpSession();
			
			usernameToSessionLookup.remove(user.getUsername());
			cookieToSessionLookup.remove(user.getCookieValue());
			httpSessionIdLookup.remove(httpSession.getId());			
			httpSession.removeAttribute(Constants.VSESSION_KEY);
			httpSession.invalidate();
			if (logger.isFine()) {
				logger.fine("Removed session for user %s.", user);
			}
			didLogout = true;
		}
		return didLogout;
	}	
}
