/*
 * Property of Will Stevens
 * All rights reserved.
 */
package com.valloc.object.dto;

import com.valloc.framework.ServiceResult;
import com.valloc.object.domain.UserMessage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 *
 * 
 * @author wstevens
 */
public class ResponseDto
{
	public boolean isSuccess;
	public UserMessageDto userMessage;
    public UserMessageDto relayMessage;
    public List<UserMessageDto> supportingUserMessages = new ArrayList<>();
    public Object object;
    public String targetUrl;
    public Map<String, Object> attributes = new HashMap<>();

    public ResponseDto() {}
    
    public ResponseDto(ServiceResult<?> serviceResult) {
    	isSuccess = serviceResult.isSuccess();
        object = serviceResult.getObject();
        attributes = serviceResult.getAttributes();
    	targetUrl = serviceResult.getViewName();
        // main user message
    	UserMessage summaryUserMessage = serviceResult.getUserMessage();
    	if (summaryUserMessage != null) {
    		userMessage = UserMessageDto.toDto(summaryUserMessage);
    	}
        // relay message
        UserMessage serviceRelayMessage = serviceResult.getRelayMessage();
        if (serviceRelayMessage != null) {
            relayMessage = UserMessageDto.toDto(serviceRelayMessage);
        }
        // supporting messages
    	if (serviceResult.hasSupportingUserMessages()) {
    		for (UserMessage supportingUserMessage : serviceResult.getSupportingUserMessages()) {
    			supportingUserMessages.add(UserMessageDto.toDto(supportingUserMessage));
    		}
    	}
    }
}
