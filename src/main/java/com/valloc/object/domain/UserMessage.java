/*
 * Property of Will Stevens
 * All rights reserved.
 */
package com.valloc.object.domain;

import com.valloc.Exposure;
import com.valloc.Locale;
import com.valloc.MessageType;

/**
 * 
 *
 * 
 * @author wstevens
 */
public interface UserMessage
{
	public String getKey();
	public void setKey(String key);
	public String getMessage();
	public void setMessage(String message);
	public Locale getLocale();
	public void setLocale(Locale locale);
	public MessageType getType();
	public void setType(MessageType type);
	public Integer getCode();
	public void setCode(Integer code);
	public Exposure getExposure();
	public void setExposure(Exposure exposure);

    public UserMessage toCustomizedInstance(String customizedMessage);
}
