/*
 * Property of Will Stevens
 * All rights reserved.
 */
package com.valloc.service;

import com.valloc.framework.ServiceResult;
import com.valloc.object.domain.*;
import com.valloc.object.dto.ClientDataDto;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 
 *
 * @author wstevens
 */
public interface ApplicationService extends VallocService
{
	public void init();
	public void loadApplicationData();
	public void updateClientDataPubDate();
	public boolean isOverSsl(HttpServletRequest request);
	public ClientDataDto getAppDataDto(String publishDateString);
	public UserMessage getUserMessage(String key);
    public Map<String, UserMessage> getAllUserMessages();
	public UserMessage getCustomizedUserMessage(String key, Object... args);
	public UserMessage getServerErrorUserMessage();
	public Cookie newSessionCookie(String cookieValue);
	public Cookie findSessionCookie(Cookie[] cookies);
    public void addLinkServe(LinkServe linkServe);
    public void addApplicationRequestStats(ApplicationRequest applicationRequest);
    public void addApplicationErrorStats(ApplicationError applicationError);
    public ServiceResult<UserMessage> addFeedback(Feedback feedback);
	
	
}
