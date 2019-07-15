/*
 * Property of Will Stevens
 * All rights reserved.
 */
package com.valloc.object.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;


/**
 *
 *
 * 
 * @author wstevens
 */
public class LoggerChangeDto
{
    @JsonProperty(value="action")
	public String action;
    
    @JsonProperty(value="level")
    public String level;
    
    @JsonProperty(value="hasCategories")
	public boolean hasCategories;
    
    @JsonProperty(value="categories")
	public List<String> categories = new ArrayList<>();
}
