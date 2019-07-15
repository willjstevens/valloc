/*
 * Property of Will Stevens
 * All rights reserved.
 */
package com.valloc.object.domain;

import com.valloc.Category;

import java.util.List;

/**
 *
 *
 * 
 * @author wstevens
 */
public interface SystemNotification
{
	public Category getCategory();
	public void setCategory(Category category);
	public String getAction();
	public void setAction(String action);
	public String getNotificationData();
	public void setNotificationData(String notificationData);
	public boolean isConfigChange();
	public void setConfigChange(boolean isConfigChange);
	public List<SystemNotificationHistory> getSystemNotificationHistories();
	public void setSystemNotificationHistories(List<SystemNotificationHistory> systemNotificationHistories);	
}
