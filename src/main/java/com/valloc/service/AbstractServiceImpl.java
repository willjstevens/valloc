package com.valloc.service;

import com.valloc.framework.*;
import com.valloc.object.domain.UserMessage;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Standard implementation for account operations.
 *
 * @author wstevens
 */
public abstract class AbstractServiceImpl
{
	@Autowired private ApplicationService applicationService;
	
	private ForbiddenPageResultException forbiddenPageResultException; 
	private ForbiddenAjaxResultException forbiddenAjaxResultException;
	private ServerErrorAjaxResultException serverErrorAjaxResultException;
	private ServerErrorPageResultException serverErrorPageResultException;
	private PageNotFoundPageResultException pageNotFoundPageResultException;
	private PageNotFoundAjaxResultException pageNotFoundAjaxResultException;

    void setUserMessage(ServiceResult<?> serviceResult, String key) {
        UserMessage userMessage = applicationService.getUserMessage(key);
        serviceResult.setUserMessage(userMessage);
    }

    void addSupportingUserMessage(ServiceResult<?> serviceResult, String key) {
        UserMessage userMessage = applicationService.getUserMessage(key);
        serviceResult.addSupportingUserMessage(userMessage);
    }

	ServerErrorPageResultException getServerErrorPageResultException() {
		if (serverErrorPageResultException == null) {	
			serverErrorPageResultException = new ServerErrorPageResultException();	
		}
		return serverErrorPageResultException;
	}
	
	ServerErrorAjaxResultException getServerErrorAjaxResultException() {
		if (serverErrorAjaxResultException == null) {
			serverErrorAjaxResultException = new ServerErrorAjaxResultException();	
		}
		return serverErrorAjaxResultException;
	}
	
	ForbiddenPageResultException getForbiddenPageResultException() {
		if (forbiddenPageResultException == null) {	
			forbiddenPageResultException = new ForbiddenPageResultException();	
		}
		return forbiddenPageResultException;
	}

	ForbiddenAjaxResultException getForbiddenAjaxResultException() {
		if (forbiddenAjaxResultException == null) {		
			forbiddenAjaxResultException = new ForbiddenAjaxResultException();	
		}
		return forbiddenAjaxResultException;
	}
	
	PageNotFoundPageResultException getPageNotFoundPageResultException() {
		if (pageNotFoundPageResultException == null) {		
			pageNotFoundPageResultException = new PageNotFoundPageResultException();	
		}
		return pageNotFoundPageResultException;
	}

	PageNotFoundAjaxResultException PageNotFoundAjaxResultException() {
		if (pageNotFoundAjaxResultException == null) {		
			pageNotFoundAjaxResultException = new PageNotFoundAjaxResultException();	
		}
		return pageNotFoundAjaxResultException;
	}	
	

}
