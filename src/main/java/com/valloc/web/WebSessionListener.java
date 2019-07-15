/*
 * Property of Will Stevens
 * All rights reserved.
 */
package com.valloc.web;

import com.valloc.Category;
import com.valloc.framework.SessionManager;
import com.valloc.log.LogManager;
import com.valloc.log.Logger;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 *
 *
 * 
 * @author wstevens
 */
public class WebSessionListener implements HttpSessionListener
{

    private static final Logger logger = LogManager.manager().newLogger(WebSessionListener.class, Category.SESSION);
	private static final int DEFAULT_SESSION_TIMEOUT_SECONDS = 60 * 60 * 6; // 6 hours is plenty before we cleanup resources 
	private int sessionTimeoutSeconds = DEFAULT_SESSION_TIMEOUT_SECONDS;
	private SessionManager sessionManager;
    private ApplicationContext applicationContext;

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpSessionListener#sessionCreated(javax.servlet.http.HttpSessionEvent)
	 */
	@Override
	public void sessionCreated(HttpSessionEvent event) {
        if (sessionManager == null) {
            sessionManager = (SessionManager) applicationContext.getBean("sessionManager");
        }

		HttpSession httpSession = event.getSession();
		httpSession.setMaxInactiveInterval(sessionTimeoutSeconds);
        if (logger.isFiner()) {
            logger.finer("HttpSession with ID \"%s\" was created with session timeout set to %d seconds.", httpSession.getId(), sessionTimeoutSeconds);
        }
	}

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpSessionListener#sessionDestroyed(javax.servlet.http.HttpSessionEvent)
	 */
	@Override
	public void sessionDestroyed(HttpSessionEvent event) {
		HttpSession httpSession = event.getSession();
		sessionManager.removeSession(httpSession);
        if (logger.isFiner()) {
            logger.finer("HttpSession with ID \"%s\" was destroyed and removed from the session manager.", httpSession.getId());
        }
	}

	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
	}

	public void setSessionTimeoutSeconds(int sessionTimeoutSeconds) {
		this.sessionTimeoutSeconds = sessionTimeoutSeconds;
	}
	
}
