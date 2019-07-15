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
public class DashboardUserPagesDto
{	
    @JsonProperty(value="pages")
	public List<PageDto> pages = new ArrayList<>();
    
    @JsonProperty(value="guestPages") 
    public List<DashboardListingPageDto> guestPages = new ArrayList<>(); 
}
