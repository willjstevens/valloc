/*
 * Property of Will Stevens
 * All rights reserved.
 */
package com.valloc.object.domain;



/**
 *
 *
 * 
 * @author wstevens
 */
public interface SystemNotificationHistory
{
	public SystemNotification getSystemNotification();
	public void setSystemNotification(SystemNotification systemNotification);
	public String getInstanceId();
	public void setInstanceId(String instanceId);
	public int getResultCode();
	public void setResultCode(int resultCode);
	public String getResultMessage();
	public void setResultMessage(String resultMessage);
}