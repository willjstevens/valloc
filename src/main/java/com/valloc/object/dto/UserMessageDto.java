/*
 * Property of Will Stevens
 * All rights reserved.
 */
package com.valloc.object.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.valloc.object.domain.UserMessage;

/**
 *
 *
 * 
 * @author wstevens
 */
public class UserMessageDto
{

    @JsonProperty(value="key")
	public String key;

    @JsonProperty(value="message")
	public String message;

    @JsonProperty(value="type")
	public String type;

    @JsonProperty(value="code")
	public int code;

    public static UserMessageDto toDto(UserMessage userMessage) {
    	UserMessageDto dto = new UserMessageDto();
    	dto.key = userMessage.getKey();
    	dto.message = userMessage.getMessage();
    	if (userMessage.getCode() != null) {
    		dto.code = userMessage.getCode();
    	}
    	return dto;
    }

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UserMessageDto [key=");
		builder.append(key);
		builder.append(", message=");
		builder.append(message);
		builder.append(", type=");
		builder.append(type);
		builder.append(", code=");
		builder.append(code);
		builder.append("]");
		return builder.toString();
	}
    
}
