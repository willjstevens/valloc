/*
 * Property of Will Stevens
 * All rights reserved.
 */
package com.valloc.object.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

//import org.codehaus.jackson.annotate.JsonProperty;

/**
 *
 *
 * 
 * @author wstevens
 */
public class ClientDataDto
{
    @JsonProperty(value="publishDate")
	public String publishDate;
    
    @JsonProperty(value="configs")
    public List<ConfigDto> configs = new ArrayList<>();
    
    @JsonProperty(value="userMessages")
    public List<UserMessageDto> userMessages = new ArrayList<>();

    @JsonProperty(value="specs")
    public SpecDto specDta = new SpecDto();
    
}
