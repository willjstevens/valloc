/*
 * Property of Will Stevens
 * All rights reserved.
 */
package com.valloc.serve;

import com.valloc.Category;
import com.valloc.framework.*;
import com.valloc.log.LogManager;
import com.valloc.log.Logger;
import com.valloc.service.ApplicationService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 *
 * 
 * @author wstevens
 */
abstract class AbstractServeStage implements ServeStage
{
	private static final Logger logger = LogManager.manager().newLogger(AbstractServeStage.class, Category.SERVICE_PAGE_SERVE);
	PageServeService pageServeService;
	PageKey pageKey;
	AbstractServeStage nextServeStage;
	HttpServletRequest request;
	HttpServletResponse response;
	PageCache pageCache;
	
	public AbstractServeStage(PageServeService pageServeService) {
		this.pageServeService = pageServeService;
	}

	@Override
	public ServeStage nextStage() {
		return nextServeStage;
	}
	
	@Override
	public void setResources(PageKey pageKey, HttpServletRequest request, HttpServletResponse response) {
		this.pageKey = pageKey;
		this.request = request;
		this.response = response;
	}

    @Override
    public void setPageCache(PageCache pageCache) {
        this.pageCache = pageCache;
    }

    @Override
	public void setNextStage(ServeStage nextServeStage) {
		this.nextServeStage = (AbstractServeStage) nextServeStage;
		nextServeStage.setResources(pageKey, request, response);
        nextServeStage.setPageCache(pageCache);
		pageServeService.addPageServeStage(sessionId(), pageKey, nextServeStage);
	}
	
	void redirect(String redirectUrl) {
		String encodedRedirectUrl = response.encodeRedirectURL(redirectUrl);
		if (logger.isFiner()) {
			logger.finer("Encoded original redirect URL (%s) into: %s.", redirectUrl, encodedRedirectUrl);
		}
		response.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
		try {
			response.sendRedirect(encodedRedirectUrl);
			if (logger.isFiner()) {
				logger.finer("Successfully redirected to: %s.", encodedRedirectUrl);
			}
		} catch (Exception e) {
			logger.error("Error occured when redirecting to URL %s.", e, encodedRedirectUrl);
		}
	}

    void sendError(int httpErrorCode) throws ResultException {
        String message = null;
        ApplicationService applicationService = pageServeService.getApplicationService();
        ResultException e = null;
        switch (httpErrorCode) {
            case HttpServletResponse.SC_BAD_REQUEST:
                e = new BadRequestPageResultException();    break;
            case HttpServletResponse.SC_FORBIDDEN:
                e = new ForbiddenPageResultException();     break;
            case HttpServletResponse.SC_NOT_FOUND:
                e = new PageNotFoundPageResultException();  break;
            case HttpServletResponse.SC_INTERNAL_SERVER_ERROR:
                e = new ServerErrorPageResultException();   break;
            default:
                throw new IllegalArgumentException("Unknown http error code, please use another or implement handling. Error code: " + httpErrorCode);
        }
		logger.warn("Error occured when sending error in response with HTTP error code: %d.", httpErrorCode);

        throw e;
    }

	boolean isOverSsl() {
		return pageServeService.getApplicationService().isOverSsl(request);
	}
	
	public void cleanupPageStage() {
		pageServeService.removePageServeStage(sessionId(), pageKey);
	}
	
	@Override
	public void releaseForResponse() {
		request = null;
		response = null;
	}

	private String sessionId() {
		return request.getSession().getId();
	}
}
