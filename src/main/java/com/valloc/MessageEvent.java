/*
 * Property of Will Stevens
 * All rights reserved.
 */
package com.valloc;

/**
 * 
 *
 * @author wstevens
 */
public class MessageEvent
{
	private Category category;
	private String action;
	private String eventData;
	
	public MessageEvent(Category category, String action, String eventData) {
		this.category = category;
		this.action = action;
		this.eventData = eventData;
	}
	
	public Category getCategory() {
		return category;
	}
	
	public String getAction() {
		return action;
	}
	
	public String getEventData() {
		return eventData;
	}
}
