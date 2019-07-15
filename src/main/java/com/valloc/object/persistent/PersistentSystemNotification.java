/*
 * Property of Will Stevens
 * All rights reserved.
 */
package com.valloc.object.persistent;

import com.valloc.Category;
import com.valloc.SpecConstants;
import org.hibernate.annotations.IndexColumn;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 *
 * 
 * @author wstevens
 */
@Entity
@Table(name="system_notification")
@SequenceGenerator(
	    name="id_seq",
	    allocationSize=1,
	    sequenceName="system_notification_id_seq"
	)
public class PersistentSystemNotification extends DomainObject
{

	private Category category;
	private String action;
	private String notificationData;
	private boolean isConfigChange;
	private List<PersistentSystemNotificationHistory> systemNotificationHistories = new ArrayList<>();
	
	@Column(name="category", nullable=false)
	@Enumerated(EnumType.STRING) 
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}

	@Column(name="action", nullable=false)
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}

	@Column(name="system_notification_data", nullable=false, length=SpecConstants.SYSTEM_NOTIF_DATA_MAX_SIZE_VALUE)
	public String getNotificationData() {
		return notificationData;
	}
	public void setNotificationData(String notificationData) {
		this.notificationData = notificationData;
	}

	@Column(name="is_config_change", nullable=false)
	public boolean isConfigChange() {
		return isConfigChange;
	}
	public void setConfigChange(boolean isConfigChange) {
		this.isConfigChange = isConfigChange;
	}
	
	@OneToMany(cascade={CascadeType.ALL}, orphanRemoval=true)
    @JoinColumn(name="system_notification_id", nullable=false)
    @IndexColumn(name="history_order")
	public List<PersistentSystemNotificationHistory> getSystemNotificationHistories() {
		return systemNotificationHistories;
	}
	
	public void setSystemNotificationHistories(List<PersistentSystemNotificationHistory> systemNotificationHistories) {
		this.systemNotificationHistories = systemNotificationHistories;
		for (PersistentSystemNotificationHistory history : systemNotificationHistories) {
			history.setSystemNotification(this);
		}
	}

	public void addMessageHistory(PersistentSystemNotificationHistory history) {
		systemNotificationHistories.add(history);
		history.setSystemNotification(this);
	}
	public void removeMessageHistory(PersistentSystemNotificationHistory history) {
		systemNotificationHistories.remove(history);
		history.setSystemNotification(null);
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((action == null) ? 0 : action.hashCode());
		result = prime * result
				+ ((category == null) ? 0 : category.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PersistentSystemNotification other = (PersistentSystemNotification) obj;
		if (action == null) {
			if (other.action != null)
				return false;
		} else if (!action.equals(other.action))
			return false;
		if (category != other.category)
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "SystemNotification [category=" + category + ", action=" + action
				+ ", isConfigChange=" + isConfigChange + ", getId()=" + getId()
				+ "]";
	}
}
