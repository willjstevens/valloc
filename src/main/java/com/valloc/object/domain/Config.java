/*
 * Property of Will Stevens
 * All rights reserved.
 */
package com.valloc.object.domain;

import com.valloc.Category;
import com.valloc.Exposure;

/**
 * 
 *
 * 
 * @author wstevens
 */
public interface Config
{
	public Category getCategory();
	public void setCategory(Category category);
	public String getKey();
	public void setKey(String key);
	public String getValue();
	public void setValue(String value);
	public String getDescription();
	public void setDescription(String description);
	public Exposure getExposure();
	public void setExposure(Exposure exposure);		
}
