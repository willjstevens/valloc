/*
 * Property of Will Stevens
 * All rights reserved.
 */
package com.valloc.service;

import com.valloc.Category;
import com.valloc.Environment;
import com.valloc.MessageEvent;
import com.valloc.MessageListener;
import com.valloc.dao.SystemDao;
import com.valloc.log.LogManager;
import com.valloc.log.Logger;
import com.valloc.object.persistent.PersistentSystemNotification;
import com.valloc.object.persistent.PersistentSystemNotificationHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.*;

/**
 * 
 *
 * @author wstevens
 */
@Service
class SystemServiceImpl implements SystemService
{
	private static final Logger logger = LogManager.manager().newLogger(SystemServiceImpl.class, Category.SERVICE_SYSTEM);
	private Map<Category, List<MessageListener>> messageListeners = new HashMap<>();
	private ScheduledExecutorFactory scheduledExecutorFactory = new ScheduledExecutorFactory();
	private ScheduledExecutorService scheduledExecutorsService = Executors.newScheduledThreadPool(2, scheduledExecutorFactory);
	private MessagePoller messagePoller = new MessagePoller();
	private StatsSubmitter statsSubmitter = new StatsSubmitter();
	private static final int SCHEDULED_EXECUTOR_INIT_DELAY = 5; // seconds
	private long scheduledJobDelay;
	
	private final BlockingQueue<Object> statsQueue = new SynchronousQueue<>(true);
	
	@Autowired private ConfigurationService configurationService;	
	@Autowired private Environment environment;	
	@Autowired private SystemDao systemDao;
	
	@Override
	public void init() {
		messagePoller.systemService = this;
		scheduledJobDelay = configurationService.getSystemServiceScheduledExecutorDelay();
		
		startPollingMessages();
	}

	@Override
	public void startPollingMessages() {
		startScheduledExecutors();
	}

	@Override
	public void stopPollingMessages() {
		scheduledExecutorsService.shutdown();
	}

	@Override
	public void addMessageListener(MessageListener messageListener) {
		final Category category = messageListener.getCategory();
		List<MessageListener> categoryMessageListeners = messageListeners.get(category);
		if (categoryMessageListeners == null) {
			categoryMessageListeners = new ArrayList<>();
			messageListeners.put(category, categoryMessageListeners);
		}
		categoryMessageListeners.add(messageListener);
	}

	@Override
	public void publishMessage(Category category, String action, String messageData) {
		publishMessage(category, action, messageData, false);
	}

	@Override
	@Transactional
	public void publishMessage(Category category, String action, String notificationData, boolean isConfigChange) {
		PersistentSystemNotification systemNotification = new PersistentSystemNotification();
		systemNotification.setCategory(category);
		systemNotification.setAction(action);
		systemNotification.setNotificationData(notificationData);
		systemNotification.setConfigChange(isConfigChange);
		
		systemDao.saveOrUpdate(systemNotification);
		if (logger.isFiner()) {
			logger.finer("Successfully saved message for category %s and action %s.", category, action);
		}
		if (logger.isFinest()) {
			logger.finest("MessagePojo data is: \"%s\".", notificationData);
		}
	}
	
	@Override
	public void onMessageEvent(MessageEvent event) {
		switch (event.getAction()) {
		case "change-delay":
			scheduledJobDelay = configurationService.getSystemServiceScheduledExecutorDelay();
			shutdownScheduledExecutorService();
			startScheduledExecutors();
			break;
		}
	}

	@Override
	public Category getCategory() {
		return Category.SERVICE_SYSTEM;
	}

	private static class MessagePoller implements Runnable {
		SystemServiceImpl systemService;
		@Override 
		@Transactional
		public void run() {
			List<PersistentSystemNotification> systemNotifications = systemService.systemDao.findUnprocessedMessages(systemService.environment.getInstanceId());
			for (PersistentSystemNotification systemNotification : systemNotifications) {
				Category category = systemNotification.getCategory();
				List<MessageListener> categoryMessageListeners = systemService.messageListeners.get(category);
				if (categoryMessageListeners == null) {
					logger.warn("Found a message with no registered listeners for category %s.", category);
					continue;
				}
				
				if (systemNotification.isConfigChange()) {
					systemService.configurationService.load();
				}
				
				String action = systemNotification.getAction(); 
				String messageData = systemNotification.getNotificationData();
				if (logger.isFiner()) {
					logger.finer("About to invoke message listener for category %s and action %s.", category, action);
				}
				if (logger.isFinest()) {
					logger.finest("MessagePojo data is: \"%s\".", messageData);
				}
				MessageEvent event = new MessageEvent(category, action, messageData);
				for (MessageListener listener : categoryMessageListeners) {
					// stage history object before dispatching event
					PersistentSystemNotificationHistory history = new PersistentSystemNotificationHistory();
					history.setSystemNotification(systemNotification);
					history.setInstanceId(systemService.environment.getInstanceId());
					try {
						listener.onMessageEvent(event);
						
						// MessagePojo was successfully serviced so log a history record
						String successMessage = String.format("Successfully retrieved and dispatched message to category %s with action %s.", category, action);
						history.setResultCode(0);
						history.setResultMessage(successMessage);
						if (logger.isFine()) {
							logger.fine(successMessage);
						}
					} catch (Exception e) {
						logger.error("Exception occured during message dispatch for category %s and action %s.", e, category, action);
						history.setResultCode(1);
						history.setResultMessage(String.format("Failed dispatching message for category %s with action %s.", category, action));
					}
					
					systemService.systemDao.saveOrUpdate(history);
				}
			}
		}
	}

	private static class StatsSubmitter implements Runnable {
		SystemServiceImpl systemService;
		@Override 
		@Transactional
		public void run() {
			Collection<Object> statsToDrain = new ArrayList<>();
			systemService.statsQueue.drainTo(statsToDrain);
			systemService.systemDao.saveOrUpdateBatch(statsToDrain, 20);
			if (logger.isFine()) {
				logger.fine("Completed inserting %d stats objects.", statsToDrain.size());
			}
		}
	}

	@Override
	public void submitStatsObject(Object persistentStatsObject) {
		statsQueue.add(persistentStatsObject);
		if (logger.isFiner()) {
			logger.finer("Added the following stats object to queue: %s.", persistentStatsObject);
		}
	}
	
	private void startScheduledExecutors() {
		if (logger.isFine()) {
			logger.fine("About to start the system service message poller thread.");
		}
		scheduledExecutorFactory.threadName = "valloc.system.message-poller";
		scheduledExecutorsService.scheduleWithFixedDelay(messagePoller, SCHEDULED_EXECUTOR_INIT_DELAY, scheduledJobDelay, TimeUnit.SECONDS);
		if (logger.isInfo()) {
			logger.info("Started the system service message poller thread.");
		}
		
		if (logger.isFine()) {
			logger.fine("About to shutdown the system service stats submitter thread.");
		}
		scheduledExecutorFactory.threadName = "valloc.system.stats-submitter";
		scheduledExecutorsService.scheduleWithFixedDelay(statsSubmitter, SCHEDULED_EXECUTOR_INIT_DELAY, scheduledJobDelay, TimeUnit.SECONDS);
		if (logger.isInfo()) {
			logger.info("Started the system service stats submitter thread.");
		}
	}
	
	private void shutdownScheduledExecutorService() {
		if (logger.isFine()) {
			logger.fine("About to shutdown the system service executor service.");
		}
		scheduledExecutorsService.shutdown();
		if (logger.isInfo()) {
			logger.info("Shutdown the system service executor service.");
		}
	}
	
	private static class ScheduledExecutorFactory implements ThreadFactory {
		String threadName;
		@Override
		public Thread newThread(Runnable runnable) {
			if (threadName == null) {
				throw new NullPointerException("Need to supply a thread name.");
			}
			Thread thread = new Thread(runnable, threadName);
			threadName = null;
			return thread;
		}
	}
}
