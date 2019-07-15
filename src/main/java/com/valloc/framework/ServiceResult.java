/*
 * Property of Will Stevens
 * All rights reserved.
 */
package com.valloc.framework;

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
public final class ServiceResult<T> 
{
	private boolean isSuccess;
	private UserMessage userMessage;
	private List<UserMessage> supportingUserMessages = new ArrayList<>();
	private T object;
	private String viewName;
    private UserMessage relayMessage;
    private Map<String, Object> attributes = new HashMap<>();
	
	public ServiceResult() {}
	
	public boolean isSuccess() {
		return isSuccess;
	}

	public void setSuccess() {
		this.isSuccess = true;
	}

	public void setUserMessage(UserMessage userMessage) {
		this.userMessage = userMessage;
	}
	
	public UserMessage getUserMessage() {
		return userMessage;
	}

	public void addSupportingUserMessage(UserMessage userMessage) {
		supportingUserMessages.add(userMessage);
	}
	
	public List<UserMessage> getSupportingUserMessages() {
		return supportingUserMessages;
	}
	
	public boolean hasSupportingUserMessages() {
		return !supportingUserMessages.isEmpty();
	}

	public T getObject() {
		return object;
	}

	public void setObject(T object) {
		this.object = object;
	}

	public String getViewName() {
		return viewName;
	}

	public void setViewName(String viewName) {
		this.viewName = viewName;
	}

    public void addAttribute(String key, Object value) {
        attributes.put(key, value);
    }

    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public UserMessage getRelayMessage() {
        return relayMessage;
    }

    public void setRelayMessage(UserMessage relayMessage) {
        this.relayMessage = relayMessage;
    }
}
