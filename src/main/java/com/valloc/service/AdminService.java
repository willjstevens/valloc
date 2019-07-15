/*
 * Property of Will Stevens
 * All rights reserved.
 */
package com.valloc.service;

import com.valloc.Category;
import com.valloc.MessageListener;
import com.valloc.log.Level;
import com.valloc.object.dto.LoggerChangeDto;

/**
 * 
 *
 * @author wstevens
 */
public interface AdminService extends VallocService, MessageListener
{
	public void init();
	public void setLoggerLevel(Level level);
	public void setLoggerLevel(Level level, Category... categories);
	public void setLoggerLevel(LoggerChangeDto loggerChangeDto);
}
