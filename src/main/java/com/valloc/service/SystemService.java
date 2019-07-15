/*
 * Property of Will Stevens
 * All rights reserved.
 */
package com.valloc.service;

import com.valloc.Category;
import com.valloc.MessageListener;

/**
 * 
 *
 * @author wstevens
 */
public interface SystemService extends VallocService, MessageListener
{
	public void init();
	public void publishMessage(Category category, String action, String messageData);
	public void publishMessage(Category category, String action, String messageData, boolean isConfigChange);
	public void startPollingMessages();
	public void stopPollingMessages();
	public void addMessageListener(MessageListener messageListener);
	public void submitStatsObject(Object persistentStatsObject);
	
}
