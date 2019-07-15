/*
 * Property of Will Stevens
 * All rights reserved.
 */
package com.valloc.object.pojo;

import com.valloc.Category;
import com.valloc.object.domain.SystemNotification;
import com.valloc.object.domain.SystemNotificationHistory;

import java.util.ArrayList;
import java.util.List;

/**
 *
 *
 * 
 * @author wstevens
 */
public class SystemNotificationPojo implements SystemNotification
{

	private Category category;
	private String action;
	private String notificationData;
	private boolean isConfigChange;
	private List<SystemNotificationHistory> systemNotificationHistories = new ArrayList<>();
	
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getNotificationData() {
		return notificationData;
	}
	public void setNotificationData(String notificationData) {
		this.notificationData = notificationData;
	}
	public boolean isConfigChange() {
		return isConfigChange;
	}
	public void setConfigChange(boolean isConfigChange) {
		this.isConfigChange = isConfigChange;
	}
	public List<SystemNotificationHistory> getSystemNotificationHistories() {
		return systemNotificationHistories;
	}
	public void setSystemNotificationHistories(List<SystemNotificationHistory> systemNotificationHistories) {
		this.systemNotificationHistories = systemNotificationHistories;
	}
}
