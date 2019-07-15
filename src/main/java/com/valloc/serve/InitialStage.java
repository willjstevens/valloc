/*
 * Property of Will Stevens
 * All rights reserved.
 */
package com.valloc.serve;

import com.valloc.Category;
import com.valloc.UrlBuilder;
import com.valloc.log.LogManager;
import com.valloc.log.Logger;

import javax.servlet.http.HttpServletResponse;

/**
 *
 *
 * 
 * @author wstevens
 */
class InitialStage extends AbstractServeStage
{
	private static final Logger logger = LogManager.manager().newLogger(InitialStage.class, Category.SERVICE_PAGE_SERVE);
	public InitialStage(PageServeService pageServeService) {
		super(pageServeService);
	}

	@Override
	public boolean execute() {
		pageCache = pageServeService.getPageCache(pageKey);
		// if cache miss, then this is a 404 since it could not be loaded
		if (pageCache == null) { 
//			cancelContinuance();
			cleanupPageStage();
			if (logger.isFine()) {
				logger.fine("No page found for the following key so returning a 404: %s.", pageKey);
			}
			sendError(HttpServletResponse.SC_NOT_FOUND);
			return false;
		}
		
		if (pageCache.isPublic()) {
			// if the page is public but user requested it with SSL, kick them back to standard HTTP
			if (isOverSsl()) {
//				cancelContinuance();
				// build redirect URL
				UrlBuilder urlBuilder = new UrlBuilder(pageServeService.getConfigurationService());
				urlBuilder.setDomainPrefixPlain(); // set to prefix "http://"
				if (pageKey.isHomePage()) {
					urlBuilder.appendUserHomePage(pageKey.getUsername()); 
				} else {
					urlBuilder.appendUserPage(pageKey.getUsername(), pageKey.getPath());
				}
				String redirectUrl = urlBuilder.buildUrl();
				if (logger.isFine()) {
					logger.fine("HTTPS or SSL request made for a public page so redirecting same URL as standard HTTP for page key: %s.", pageKey);
				}
				redirect(redirectUrl);
				return false;
			} else { // else graduate to next stage
				setNextStage(new EtagStage(pageServeService));
				if (logger.isFine()) {
					logger.fine("Graduating to ETAG stage for page key %s.", pageKey);
				}
			}
		} else if (pageCache.isPrivate()) { 
			// this page is private so graduate to check secure connection
			setNextStage(new SecureConnectionStage(pageServeService));
			if (logger.isFine()) {
				logger.fine("Graduating to check secure connection stage for page key: %s.", pageKey);
			}
		}
		return true;
	}
}
