package com.valloc;

import com.valloc.object.persistent.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Mock object generator which also produces meaningful loaded data.
 *
 * @author wstevens
 */
public class MockFactory
{

	public static PersistentConfig newConfig() {
		PersistentConfig config = new PersistentConfig();
		config.setCategory(Category.SERVICE_SYSTEM);
		config.setKey("message-frequency");
		config.setValue("10");
		config.setDescription("How frequent in seconds the system polls the message table looking for new messages.");
		return config;
	}
	
	public static PersistentSystemNotification newMessage() {
		PersistentSystemNotification systemNotification = new PersistentSystemNotification();
		systemNotification.setCategory(Category.DATABASE);
		systemNotification.setAction(Action.ADD.id());
		systemNotification.setNotificationData("{batchSize: 50, showSql: true}");
		systemNotification.setConfigChange(true);
		return systemNotification;
	}


	public static PersistentSystemNotificationHistory newMessageHistory() {
		return newMessageHistory(newMessage());
	}
	
	public static PersistentSystemNotificationHistory newMessageHistory(PersistentSystemNotification systemNotification) {
		PersistentSystemNotificationHistory systemNotificationHistory = new PersistentSystemNotificationHistory();
		systemNotificationHistory.setSystemNotification(systemNotification);
		systemNotificationHistory.setInstanceId(UUID.randomUUID().toString());
		systemNotificationHistory.setResultCode(200);
		systemNotificationHistory.setResultMessage("Successfully received and updated message.");
		return systemNotificationHistory;
	}

	public static List<PersistentSystemNotificationHistory> newMessageHistories(int count) {
		PersistentSystemNotification systemNotification = newMessage();
		return newSystemNotificationHistories(count, systemNotification);
	}

	public static List<PersistentSystemNotificationHistory> newSystemNotificationHistories(int count, PersistentSystemNotification systemNotification) {
		List<PersistentSystemNotificationHistory> systemNotificationHistories = new ArrayList<PersistentSystemNotificationHistory>();
		for (int i = 0; i < count; i++) {
			systemNotificationHistories.add(newMessageHistory(systemNotification));
		}
		systemNotification.setSystemNotificationHistories(systemNotificationHistories);
		return systemNotificationHistories;
	}
	
	public static List<PersistentSystemNotificationHistory> newSystemNotificationHistory(int count, String instanceId) {
		List<PersistentSystemNotificationHistory> systemNotificationHistories = newMessageHistories(count);
		for (PersistentSystemNotificationHistory history : systemNotificationHistories) {
			history.setInstanceId(instanceId);
		}
		return systemNotificationHistories;
	}
	
	public static PersistentUser newUser() {
		PersistentUser user = new PersistentUser();
		user.setUsername("will");
		user.setFirstName("Will");
		user.setLastName("Stevens");
		user.setEmail("willjstevens@gmail.com");
		user.setBirthdate(new Date());
		user.setGender("male");
		user.setLocale("US");
		user.setPassword("password");
		user.setPasswordQuestion("Favorite company");
		user.setPasswordAnswer("Valloc, LLC");
		user.setVerificationCode("123456");
		user.setVerificationCodeIssuedTimestamp(new Date());
		user.setEnabled(true);
		user.setDeleted(false);
		user.setAdmin(false);
		return user;
	}
	
	public static PersistentPage newPage() {
		PersistentPage page = new PersistentPage();
		page.setName("OpTier");
		page.setPath("path");
		page.setSharedPrivately(true);
		page.setVisibility(Visibility.PRIVATE);
		page.setDescription("Links used for OpTier");
		page.setHome(false);
		return page;
	}
	
	public static PersistentColumn newColumn() {
		PersistentColumn column = new PersistentColumn();
		return column;
	}
	
	public static PersistentSection newSection() {
		PersistentSection section = new PersistentSection();
		section.setName("Awesome Stuff");
		section.setNote("My favorite section of all");
		return section;
	}
	
	public static PersistentLink newLink() {
		PersistentLink link = new PersistentLink();
		link.setName("Valloc.com");
		link.setUrl("http://www.valloc.com");
		link.setNote("Please so and so - check this out!");
		return link;
	}
}
