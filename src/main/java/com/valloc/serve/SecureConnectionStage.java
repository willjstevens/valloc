/*
 * Property of Will Stevens
 * All rights reserved.
 */
package com.valloc.serve;

import com.valloc.Category;
import com.valloc.UrlBuilder;
import com.valloc.log.LogManager;
import com.valloc.log.Logger;

/**
 *
 *
 * 
 * @author wstevens
 */
class SecureConnectionStage extends AbstractServeStage
{
	private static final Logger logger = LogManager.manager().newLogger(SecureConnectionStage.class, Category.SERVICE_PAGE_SERVE);
	public SecureConnectionStage(PageServeService pageServeService) {
		super(pageServeService);
	}

	@Override
	public boolean execute() {
		if (!isOverSsl()) {
			nextServeStage = this; // reuse same object
			// build redirect URL
			UrlBuilder urlBuilder = new UrlBuilder(pageServeService.getConfigurationService());
			urlBuilder.setDomainPrefixSecure(); // set to prefix "https://"
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
		} 
		// if this far then ready for next stage and invoke
		setNextStage(new SessionStage(pageServeService));
		if (logger.isFine()) {
			logger.fine("Graduating to session stage for page key: %s.", pageKey);
		}
		return true;
	}
}
