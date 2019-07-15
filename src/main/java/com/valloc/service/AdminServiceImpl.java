/*
 * Property of Will Stevens
 * All rights reserved.
 */
package com.valloc.service;

import com.valloc.Action;
import com.valloc.Category;
import com.valloc.MessageEvent;
import com.valloc.log.Level;
import com.valloc.log.LogManager;
import com.valloc.log.Logger;
import com.valloc.object.dto.LoggerChangeDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 
 *
 * @author wstevens
 */
@Service
public class AdminServiceImpl implements AdminService
{
	private static final Logger logger = LogManager.manager().newLogger(AdminServiceImpl.class, Category.SERVICE_ADMIN);
	@Autowired private SystemService systemService;
	@Autowired private UtilityService utilityService;

	@Override
	public void init() {
		systemService.addMessageListener(this);
	}
	
	@Override
	public void setLoggerLevel(Level level) {
		LoggerChangeDto dto = new LoggerChangeDto();
		dto.level = level.toString();
		dto.action = Action.CHANGE.id();
		
		setLoggerLevel(dto);
	}
	
	@Override
	public void setLoggerLevel(Level level, Category... categories) {
		LoggerChangeDto dto = new LoggerChangeDto();
		dto.level = level.toString();
		dto.action = Action.CHANGE.id();
		dto.hasCategories = true;
		for (Category category : categories) {
			dto.categories.add(category.id());
		}
		
		setLoggerLevel(dto);
	}

	@Override
	public void setLoggerLevel(LoggerChangeDto loggerChangeDto) {
		String jsonMessageData = utilityService.jsonStringify(loggerChangeDto);
		systemService.publishMessage(Category.LOG, Action.CHANGE.id(), jsonMessageData, true);
		if (logger.isFine()) {
			logger.fine("Successfully published log level change to %s.", loggerChangeDto.level);
		}
	}

	@Override
	public void onMessageEvent(MessageEvent event) {
		if (event.getCategory() == Category.LOG) {
			if (event.getAction() == Action.CHANGE.id()) {
				changeLogger(event.getEventData());
			}
		}
	}

	@Override
	public Category getCategory() {
		return Category.SERVICE_ADMIN;
	}
	
	private void changeLogger(String jsonMessageData) {
		LoggerChangeDto loggerChangeDto = utilityService.jsonParse(jsonMessageData, LoggerChangeDto.class);
		Level level = Level.toLogLevel(loggerChangeDto.level);
		LogManager logManager = LogManager.manager();
		boolean doSetAllCategories = !loggerChangeDto.hasCategories; 
		if (doSetAllCategories) {
			logManager.setLevel(level);
		} else {
			List<String> dtoCategories = loggerChangeDto.categories;
			final int catLen = dtoCategories.size();
			Category[] categories = new Category[catLen];
			for (int i = 0; i < catLen; i++) {
				categories[i] = Category.toCategory(dtoCategories.get(i));
			}
			logManager.setLevel(level, categories);
		}
	}
}
