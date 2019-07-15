/*
 * Property of Will Stevens
 * All rights reserved.
 */
package com.valloc.object.dto;

//import org.codehaus.jackson.annotate.JsonProperty;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.valloc.object.persistent.PersistentConfig;

/**
 *
 *
 * 
 * @author wstevens
 */
public class ConfigDto
{
    @JsonProperty(value="category")
	public String category;
    
    @JsonProperty(value="key")
    public String key;

    @JsonProperty(value="value")
    public String value;

    @JsonProperty(value="description")
    public String description;
	
    @JsonProperty(value="hasDescription")
    public boolean hasDescription() {
    	return description != null;
    }
    
    public static ConfigDto toDto(PersistentConfig config) {
    	ConfigDto dto = new ConfigDto();
    	dto.category = config.getCategory().id();
    	dto.key = config.getKey();
    	dto.value = config.getValue();
    	return dto;
    }

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ConfigDto [category=");
		builder.append(category);
		builder.append(", key=");
		builder.append(key);
		builder.append(", value=");
		builder.append(value);
		builder.append(", description=");
		builder.append(description);
		builder.append("]");
		return builder.toString();
	}


}
