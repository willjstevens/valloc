/*
 * Property of Will Stevens
 * All rights reserved.
 */
package com.valloc.service;

import com.valloc.Category;
import com.valloc.Constants;
import com.valloc.Util;
import com.valloc.dao.ApplicationDao;
import com.valloc.dao.SystemDao;
import com.valloc.framework.ServiceResult;
import com.valloc.log.LogManager;
import com.valloc.log.Logger;
import com.valloc.object.ObjectConverter;
import com.valloc.object.domain.*;
import com.valloc.object.dto.ClientDataDto;
import com.valloc.object.dto.ConfigDto;
import com.valloc.object.dto.UserMessageDto;
import com.valloc.object.persistent.PersistentConfig;
import com.valloc.object.persistent.PersistentUserMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import static com.valloc.Exposure.*;

/**
 * 
 *
 * @author wstevens
 */
@Service
public class ApplicationServiceImpl extends AbstractServiceImpl implements ApplicationService
{
	private static final Logger logger = LogManager.manager().newLogger(ApplicationServiceImpl.class, Category.SERVICE_APPLICATION);
	private final DateFormat clientDataDateFormatter = new SimpleDateFormat("yyyyMMdd-HH:mm:ss:SSS");
    public static final int APP_REQ_STATS_QUEUE_CAPACITY = 2;
    public static final int LINK_SERVE_QUEUE_CAPACITY = 2;
    private static final int paddedAppReqQueueSize = APP_REQ_STATS_QUEUE_CAPACITY + 10;
    private static final int paddedLinkServeQueueSize = LINK_SERVE_QUEUE_CAPACITY + 10;
	@Autowired private ApplicationDao applicationDao;
	@Autowired private SystemDao systemDao;
	@Autowired private ConfigurationService configurationService;
    @Autowired private ObjectConverter objectConverter;
    @Autowired private UtilityService utilityService;
	private ClientDataDto clientDataDto;
    private Map<String, UserMessage> userMessagesLookup;
	private Date mostRecentClientDataPubDate;
	private PersistentUserMessage serverErrorUserMessage;
    private final BlockingQueue<ApplicationRequest> applicationRequestsQueue = new ArrayBlockingQueue<ApplicationRequest>(paddedAppReqQueueSize, true);
    private final BlockingQueue<LinkServe> linkServeQueue = new ArrayBlockingQueue<LinkServe>(paddedLinkServeQueueSize, true);
	
	@Override
	public void init() {
		loadApplicationData();
	}

	@Override
	public void loadApplicationData() {
		loadUserMessages();
		loadClientData();
	}
	
	private void loadUserMessages() {
		// Load user messages for full, server-side usage only
		List<PersistentUserMessage> userMessages = applicationDao.findUserMessagesByExposure(SERVER, ALL);
		Map<String, UserMessage> newUserMessagesLookup = new HashMap<String, UserMessage>((userMessages.size()*2)); // just double instead of 1.3
		for (PersistentUserMessage userMessage : userMessages) {
			final String key = userMessage.getKey();
			newUserMessagesLookup.put(key, userMessage);
			if (key.equals("system.error")) {
				serverErrorUserMessage = userMessage;
			}
		}
		if (userMessagesLookup != null) {
			userMessagesLookup.clear();
		}
		userMessagesLookup = newUserMessagesLookup;
		
		if (logger.isFine()) {
			logger.fine("Done loading %d user messages.", userMessages.size());
		}
	}
	
	private void loadClientData() {
		clientDataDto = new ClientDataDto();
		
		// Load all the user messages for DTO only
		List<PersistentUserMessage> userMessages = applicationDao.findUserMessagesByExposure(CLIENT, ALL);
		for (PersistentUserMessage userMessage : userMessages) {
			UserMessageDto dto = UserMessageDto.toDto(userMessage);
			clientDataDto.userMessages.add(dto);
			if (logger.isFiner()) {
				logger.finer("Loading client data user message: %s.", dto);
			}
		}
		// Load all configs
		List<PersistentConfig> configs = systemDao.findConfigsByExposure(CLIENT, ALL);
		for (PersistentConfig config : configs) {
			ConfigDto dto = ConfigDto.toDto(config);
			clientDataDto.configs.add(dto);
			if (logger.isFiner()) {
				logger.finer("Loading client data config: %s.", dto);
			}
		}
		if (logger.isFine()) {
			logger.fine("Loaded %d user messages, %d configs and all SpecData for the client.", userMessages.size(), configs.size());
		}
		
		mostRecentClientDataPubDate = configurationService.getAppDataPublishDate();
		clientDataDto.publishDate = clientDataDateFormatter.format(mostRecentClientDataPubDate);
		if (logger.isFiner()) {
			logger.finer("Most recent client publish date was loaded as %s.", mostRecentClientDataPubDate);
		}
	}

	@Override
	public UserMessage getUserMessage(String key) {
		UserMessage userMessage = userMessagesLookup.get(key);
		if (logger.isFiner()) {
			logger.finer("User message located for key %s is: %s.", key, userMessage);
		}
		return userMessage;
	}

    @Override
    public Map<String, UserMessage> getAllUserMessages() {
        return userMessagesLookup;
    }

    @Override
	public UserMessage getCustomizedUserMessage(String key, Object... args) {
        final int argsLength = args.length;
		if (argsLength < 1 || argsLength > 4) {
			throw new IllegalArgumentException("Too few or too many arguments.");
		}
		UserMessage userMessage = getUserMessage(key);
		String message = userMessage.getMessage();
		for (int i = 0; i < argsLength; i++) {
			String replacement = (String) args[i];
			switch (i) {
			case 0: message = message.replaceAll("\\{0\\}", replacement);	break;
			case 1: message = message.replaceAll("\\{1\\}", replacement);	break;
			case 2: message = message.replaceAll("\\{2\\}", replacement);	break;
			case 3: message = message.replaceAll("\\{3\\}", replacement);	break;
            case 4: message = message.replaceAll("\\{4\\}", replacement);	break;
			default: throw new IndexOutOfBoundsException("Exceeded max allowed arguments.");
			}
		}
        return userMessage.toCustomizedInstance(message);
	}

	@Override
	public ClientDataDto getAppDataDto(String publishDateString) {
		ClientDataDto retval = null;
		
		try {
			if (publishDateString == null || publishDateString.equals("UNINITIALIZED")) {
				retval = clientDataDto;
			} else {
				Date clientDataPubDate = clientDataDateFormatter.parse(publishDateString);
				if (clientDataPubDate.compareTo(mostRecentClientDataPubDate) < 0) {
					retval = clientDataDto;
				}
			}
		} catch (ParseException e) {
			logger.error("Problem when attempting to convert date string \"%s\" and return client data.", e, publishDateString);
		}
						
		return retval;
	}
	
	@Override
	public PersistentUserMessage getServerErrorUserMessage() {
		return serverErrorUserMessage;
	}

	@Override
	public void updateClientDataPubDate() {
		configurationService.update(Category.CLIENT, "clientDataLastPublishDate", Util.now().toString());
	}

	@Override
	public boolean isOverSsl(HttpServletRequest request) {	
		// first check right off request for local dev environments
		boolean isSecure = request.getScheme().toLowerCase().startsWith(Constants.SECURE_SCHEME);
		// if still not secure, check for provider environments like Heroku which forward indicator in request header 
		if (!isSecure) { 
			if (logger.isFiner()) {
				logger.finer("Request isSecure found to be %b with scheme of %s. Now attempting provider's settings.", request.isSecure(), request.getScheme());
			}
			// Give final shot at pulling it from Heroku headers
			String providerForwardHeader = request.getHeader(configurationService.getProviderSecureRequestHeaderName());
			if (providerForwardHeader != null && providerForwardHeader.equalsIgnoreCase(Constants.SECURE_SCHEME)) {
				isSecure = true;
			}
		}
		if (logger.isFiner()) {
			logger.finer("Returning isSecure value of %b for URI %s.", isSecure, request.getRequestURI());
		}
		return isSecure;
	}

	@Override
	public Cookie newSessionCookie(String cookieValue) {
		Cookie cookie = new Cookie(Constants.COOKIE_SESSION_KEY, cookieValue);
		cookie.setSecure(true);
		cookie.setMaxAge(configurationService.getCookieExpirySeconds());
        cookie.setPath(Constants.FORWARD_SLASH_STR);
		return cookie;
	}
	
	@Override
	public Cookie findSessionCookie(Cookie[] cookies) {
		Cookie cookie = null;
		if (cookies != null) { // could be null if user cleared all cookies
			for (Cookie candidateCookie : cookies) {
				if (candidateCookie.getName().equals(Constants.COOKIE_SESSION_KEY)) {
					cookie = candidateCookie;
					break;
				}
			}
		}
		return cookie;
	}

    @Transactional
    @Override
    public void addLinkServe(LinkServe linkServe) {
        try {
            linkServe = objectConverter.toPersistentLinkServe(linkServe);
            linkServeQueue.offer(linkServe);
            int queueSize = linkServeQueue.size();
            if (queueSize >= LINK_SERVE_QUEUE_CAPACITY) {
                Collection<LinkServe> linksServed = new ArrayList<LinkServe>(LINK_SERVE_QUEUE_CAPACITY);
                int drainedCnt = linkServeQueue.drainTo(linksServed, LINK_SERVE_QUEUE_CAPACITY);
                if (logger.isFine()) {
                    logger.fine("Links served queue had size %d and we just drained %d elements. Batch insert about to occur.", queueSize, drainedCnt);
                }
                applicationDao.saveOrUpdateBatch(linksServed, LINK_SERVE_QUEUE_CAPACITY);
            }
        } catch (Exception e) {
            logger.error("Error occurred when adding a link serve: %s.", e, e);
        }
    }

    @Transactional
    @Override
    public void addApplicationRequestStats(ApplicationRequest applicationRequest) {
        try {
            applicationRequest = objectConverter.toPersistentApplicationRequest(applicationRequest);
            applicationRequestsQueue.offer(applicationRequest);
            int queueSize = applicationRequestsQueue.size();
            if (queueSize >= APP_REQ_STATS_QUEUE_CAPACITY) {
                Collection<ApplicationRequest> requestStats = new ArrayList<ApplicationRequest>(APP_REQ_STATS_QUEUE_CAPACITY);
                int drainedCnt = applicationRequestsQueue.drainTo(requestStats, APP_REQ_STATS_QUEUE_CAPACITY);
                if (logger.isFine()) {
                    logger.fine("Application request stats queue had size %d and we just drained %d elements. Batch insert about to occur.", queueSize, drainedCnt);
                }
                applicationDao.saveOrUpdateBatch(requestStats, APP_REQ_STATS_QUEUE_CAPACITY);
            }
        } catch (Exception e) {
            logger.error("Error occurred when adding application request stats: %s.", e, e);
        }
    }

    @Transactional
    @Override
    public void addApplicationErrorStats(ApplicationError applicationError) {
        try {
            applicationError = objectConverter.toPersistentApplicationError(applicationError);
            applicationDao.saveOrUpdate(applicationError);
        } catch (Exception e) {
            logger.error("Error occurred when adding application error stats: %s.", e, e);
        }
    }

    @Transactional
    @Override
    public ServiceResult<UserMessage> addFeedback(Feedback feedback) {
        ServiceResult<UserMessage> result = new ServiceResult<>();

        try {
            feedback = objectConverter.toPersistentFeedback(feedback);
            applicationDao.saveOrUpdate(feedback);

            // send email to owner
            StringBuilder bodyBuilder = new StringBuilder();
            bodyBuilder.append("Name: <b>").append(feedback.getName()).append("</b><br />");
            bodyBuilder.append("Email: <b>").append(feedback.getEmail()).append("</b><br />");
            bodyBuilder.append("Comment: <b>").append(feedback.getComment()).append("</b>");
            String body = bodyBuilder.toString();
            String subject = "Valloc feedback request from " + feedback.getName();
            utilityService.sendHtmlEmail(Constants.OWNER_EMAIL, subject, body);

            result.setSuccess();
            result.setUserMessage(userMessagesLookup.get("feedback_saveSuccess"));
        } catch (Exception e) {
            logger.error("Error occurred when adding feedback: %s.", e, e);
            result.setUserMessage(userMessagesLookup.get("feedback_saveFailure"));
        }

        return result;
    }
}
