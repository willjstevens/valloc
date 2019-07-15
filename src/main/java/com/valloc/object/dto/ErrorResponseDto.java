/*
 * Property of Will Stevens
 * All rights reserved.
 */
package com.valloc.object.dto;

//import org.codehaus.jackson.annotate.JsonProperty;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *
 *
 * 
 * @author wstevens
 */
public class ErrorResponseDto
{
	@JsonProperty(value="code")
	public int code;
	
    @JsonProperty(value="title")
	public String title;

    @JsonProperty(value="message")
    public String message;
    
    public ErrorResponseDto() {}

	public ErrorResponseDto(int code, String title, String message) {
		this.code = code;
		this.title = title;
		this.message = message;
	}
}
