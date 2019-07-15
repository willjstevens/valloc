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
public interface MessageListener
{
	public void onMessageEvent(MessageEvent event);
	public Category getCategory();
}
