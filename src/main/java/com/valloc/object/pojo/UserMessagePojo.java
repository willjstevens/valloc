/*
 * Property of Will Stevens
 * All rights reserved.
 */
package com.valloc.object.pojo;

import com.valloc.Exposure;
import com.valloc.Locale;
import com.valloc.MessageType;
import com.valloc.object.domain.UserMessage;

/**
 * 
 *
 * 
 * @author wstevens
 */
public class UserMessagePojo implements UserMessage
{
	private String key;
	private String message;
	private Locale locale;
	private MessageType type;
	private Integer code;
	private Exposure exposure;
	
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Locale getLocale() {
		return locale;
	}
	public void setLocale(Locale locale) {
		this.locale = locale;
	}
	public MessageType getType() {
		return type;
	}
	public void setType(MessageType type) {
		this.type = type;
	}
	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	public Exposure getExposure() {
		return exposure;
	}
	public void setExposure(Exposure exposure) {
		this.exposure = exposure;
	}

    @Override
    public UserMessage toCustomizedInstance(String customizedMessage) {
        UserMessagePojo retval = new UserMessagePojo();
        retval.key = this.key;
        retval.message = customizedMessage;
        retval.locale = this.locale;
        retval.code = this.code;
        retval.exposure = this.exposure;
        return retval;
    }
}
