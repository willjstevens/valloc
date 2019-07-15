/*
 * Property of Will Stevens
 * All rights reserved.
 */
package com.valloc.object.pojo;

import com.valloc.object.domain.SystemNotification;
import com.valloc.object.domain.SystemNotificationHistory;



/**
 *
 *
 * 
 * @author wstevens
 */
public class SystemNotificationHistoryPojo implements SystemNotificationHistory
{

	private SystemNotification systemNotification;
	private String instanceId;
	private int resultCode;
	private String resultMessage;
	
	public SystemNotification getSystemNotification() {
		return systemNotification;
	}
	public void setSystemNotification(SystemNotification systemNotification) {
		this.systemNotification = systemNotification;
	}
	public String getInstanceId() {
		return instanceId;
	}
	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}
	public int getResultCode() {
		return resultCode;
	}
	public void setResultCode(int resultCode) {
		this.resultCode = resultCode;
	}
	public String getResultMessage() {
		return resultMessage;
	}
	public void setResultMessage(String resultMessage) {
		this.resultMessage = resultMessage;
	}

 
}