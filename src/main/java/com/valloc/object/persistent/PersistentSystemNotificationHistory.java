/*
 * Property of Will Stevens
 * All rights reserved.
 */
package com.valloc.object.persistent;

import javax.persistence.*;


/**
 *
 *
 * 
 * @author wstevens
 */
@Entity
@Table(name="system_notification_history")
@SequenceGenerator(
	    name="id_seq",
	    allocationSize=1,
	    sequenceName="system_notification_history_id_seq"
	)
public class PersistentSystemNotificationHistory extends DomainObject
{

	private PersistentSystemNotification systemNotification;
	private String instanceId;
	private int resultCode;
	private String resultMessage;

    @ManyToOne
    @JoinColumn(name="system_notification_id", insertable=false, updatable=false, nullable=false)
	public PersistentSystemNotification getSystemNotification() {
		return systemNotification;
	}
	public void setSystemNotification(PersistentSystemNotification systemNotification) {
		this.systemNotification = systemNotification;
	}
	
	@Column(name="instance_id", nullable=false)
	public String getInstanceId() {
		return instanceId;
	}
	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}
	
	@Column(name="result_code", nullable=false)
	public int getResultCode() {
		return resultCode;
	}
	public void setResultCode(int resultCode) {
		this.resultCode = resultCode;
	}
	
	@Column(name="result_message")
	public String getResultMessage() {
		return resultMessage;
	}
	public void setResultMessage(String resultMessage) {
		this.resultMessage = resultMessage;
	}
}