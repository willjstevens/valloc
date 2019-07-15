/*
 * Property of Will Stevens
 * All rights reserved.
 */
package com.valloc.service;


import java.util.Date;

/**
 * 
 *
 * @author wstevens
 */
public interface UtilityService extends VallocService
{
	void init();
	
	void initEmailSending();
	void sendTextEmail(String recipient, String subject, String body);
	void sendHtmlEmail(String recipient, String subject, String body);
	String jsonStringify(Object object);
	<T> T jsonParse(String json, Class<T> type);
	String urlEncode(String url);
	String urlDecode(String url);
	String newUuidString();
    String toConversationFormat(Date date);
}
