/*
 * Property of Will Stevens
 * All rights reserved.
 */
package com.valloc.serve;

import com.valloc.Category;
import com.valloc.log.LogManager;
import com.valloc.log.Logger;

import javax.servlet.http.HttpServletResponse;

/**
 *
 *
 * 
 * @author wstevens
 */
class EtagStage extends AbstractServeStage
{
	private static final Logger logger = LogManager.manager().newLogger(LoginStage.class, Category.SERVICE_PAGE_SERVE);
	private static final String HEADER_IF_NONE_MATCH 	= "If-None-Match";
	private static final String HEADER_ETAG 			= "ETag";
	private static final String HEADER_CACHE_CONTROL	= "Cache-Control";
	
	public EtagStage(PageServeService pageServeService) {
		super(pageServeService);
	}

	@Override
	public boolean execute() {
		String ifNoneMatchHeader = request.getHeader(HEADER_IF_NONE_MATCH);
		if (ifNoneMatchHeader != null) {
			String etagVal = pageCache.getEtagValue();
			if (etagVal != null && etagVal.equals(ifNoneMatchHeader)) {
				// cache hit
				response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
				cleanupPageStage();
//				cancelContinuance();
				if (logger.isFine()) {
					logger.fine("Etag cache hit that matches the if-none-match header so sending a 304 for page key %s.", pageKey);
				}
				return false;
			}
		}
		
		// cache miss
		if (pageCache.getEtagValue() == null) { 
			// Some other request for a public page might have generated the etag. 
			String etag = pageServeService.getUtilityService().newUuidString();
			pageCache.setEtagValue(etag);
			if (logger.isFine()) {
				logger.fine("Generated and setting an ETag value for page key %s.", pageKey);
			}
		}
		
		response.setHeader(HEADER_ETAG, pageCache.getEtagValue());
		// This header should state to most browsers (now FireFox and IE) to cache secure (HTTPS) content
		//		so it will respect a 304 and not re-fetch everytime. However Chrome does not respect this 
		//		(probably more respecting the RFC more) and will always require fresh content.
		response.setHeader(HEADER_CACHE_CONTROL, "public");
		setNextStage(new WriteContentsStage(pageServeService));
		if (logger.isFine()) {
			logger.fine("Etag cache miss so adding response headers for Etag header and value.  Now graduating to memory stage for page key %s.", pageKey);
		}
		return true;
	}

}
