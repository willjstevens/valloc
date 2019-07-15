/*
 * Property of Will Stevens
 * All rights reserved.
 */
package com.valloc.object.pojo;

import com.valloc.Category;
import com.valloc.Exposure;
import com.valloc.object.domain.Config;

/**
 * 
 *
 * 
 * @author wstevens
 */
public class ConfigPojo implements Config
{
	private Category category;
	private String key;
	private String value;
	private String description;
	private Exposure exposure;
	
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Exposure getExposure() {
		return exposure;
	}
	public void setExposure(Exposure exposure) {
		this.exposure = exposure;
	}
}
