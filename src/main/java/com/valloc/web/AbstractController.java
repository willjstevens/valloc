package com.valloc.web;

import com.valloc.Category;
import com.valloc.Constants;
import com.valloc.SpecConstants;
import com.valloc.framework.ServiceResult;
import com.valloc.log.LogManager;
import com.valloc.log.Logger;
import com.valloc.object.domain.UserMessage;
import com.valloc.object.dto.ResponseDto;
import com.valloc.service.ApplicationService;
import com.valloc.service.ConfigurationService;
import com.valloc.service.UtilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import static com.valloc.Constants.*;
import static com.valloc.SpecConstants.*;

/**
 * Handles requests for the application home page.
 */
abstract class AbstractController 
{
	private static final Logger logger = LogManager.manager().newLogger(AbstractController.class, Category.CONTROLLER);
    private Map<String, String> allServerMessages = new HashMap<>();
	@Autowired private ConfigurationService configurationService;
	@Autowired private ApplicationService applicationService;
    @Autowired private UtilityService utilityService;

	void loadCommonModelData(ModelAndView model) {
		
		// Load common JavaScript and CSS file environment files
        final String scriptsFile = "/r/env-prod.html"; // for now always use prod scripts file.
//		final String scriptsFile =
//			configurationService.isProdEnvironment() ?
//				"/r/env-prod.html" :
//				"/r/env-dev.html";

		model.addObject("scriptsFile", scriptsFile);
        // Load spec object for all to use
        model.addObject("specs", SpecConstants.class);
        model.addObject("siteDomainPrefix", configurationService.getSecureSiteDomainHttpsString());

		// Load common page messages
		UserMessage enableJavaScriptMessage = applicationService.getUserMessage("client_enable-javascript");
		model.addObject("clientEnableJavascript", enableJavaScriptMessage.getMessage());
	}

    void loadModel(ModelAndView model) {
        loadCommonModelData(model);
        if (allServerMessages.isEmpty()) {
            Map<String, UserMessage> userMessages = applicationService.getAllUserMessages();
            // load all messages
            for (String key : userMessages.keySet()) {
                String value = userMessages.get(key).getMessage();
                allServerMessages.put(key, value);
            }
            /*
             * next load all parameterized messages
             */
            // designer related
            loadGenericParameterizedMessage("designer_pageName_validate_length", PAGE_NAME_MIN_SIZE_VALUE, PAGE_NAME_MAX_SIZE_VALUE);
            loadGenericParameterizedMessage("designer_pagePath_validate_length", PAGE_PATH_MIN_SIZE_VALUE, PAGE_PATH_MAX_SIZE_VALUE);
            loadGenericParameterizedMessage("designer_pageDescription_validate_length", PAGE_DESCRIPTION_MIN_SIZE_VALUE, PAGE_DESCRIPTION_MAX_SIZE_VALUE);
            loadGenericParameterizedMessage("designer_sectionName_validate_length", SECTION_NAME_MIN_SIZE_VALUE, SECTION_NAME_MAX_SIZE_VALUE);
            loadGenericParameterizedMessage("designer_sectionNote_validate_length", SECTION_NOTE_MIN_SIZE_VALUE, SECTION_NOTE_MAX_SIZE_VALUE);
            loadGenericParameterizedMessage("designer_linkName_validate_length", LINK_NAME_MIN_SIZE_VALUE, LINK_NAME_MAX_SIZE_VALUE);
            loadGenericParameterizedMessage("designer_linkUrl_validate_length", LINK_URL_MIN_SIZE_VALUE, LINK_URL_MAX_SIZE_VALUE);
            loadGenericParameterizedMessage("designer_linkNote_validate_length", LINK_NOTE_MIN_SIZE_VALUE, LINK_NOTE_MAX_SIZE_VALUE);
            // account related
            loadGenericParameterizedMessage("account_username_validate_length", USERNAME_MIN_SIZE_VALUE, USERNAME_MAX_SIZE_VALUE);
            loadGenericParameterizedMessage("account_email_validate_length", EMAIL_MIN_SIZE_VALUE, EMAIL_MAX_SIZE_VALUE);
            loadGenericParameterizedMessage("account_firstName_validate_length", FIRST_NAME_MIN_SIZE_VALUE, FIRST_NAME_MAX_SIZE_VALUE);
            loadGenericParameterizedMessage("account_lastName_validate_length", LAST_NAME_MIN_SIZE_VALUE, LAST_NAME_MAX_SIZE_VALUE);
            loadGenericParameterizedMessage("account_passwordQuestion_validate_length", PASSWORD_QUESTION_MIN_SIZE_VALUE, PASSWORD_QUESTION_MAX_SIZE_VALUE);
            loadGenericParameterizedMessage("account_passwordAnswer_validate_length", PASSWORD_ANSWER_MIN_SIZE_VALUE, PASSWORD_ANSWER_MAX_SIZE_VALUE);
            loadGenericParameterizedMessage("account_password_validate_length", PASSWORD_MIN_SIZE_VALUE, PASSWORD_MAX_SIZE_VALUE);

            /*
             * literal values
             */
            // account related
            addServerMessagesValue(USERNAME_MIN_SIZE_KEY, USERNAME_MIN_SIZE_VALUE);
            addServerMessagesValue(USERNAME_MAX_SIZE_KEY, USERNAME_MAX_SIZE_VALUE);
            addServerMessagesValue(EMAIL_MIN_SIZE_KEY, EMAIL_MIN_SIZE_VALUE);
            addServerMessagesValue(EMAIL_MAX_SIZE_KEY, EMAIL_MAX_SIZE_VALUE);
            addServerMessagesValue(FIRST_NAME_MIN_SIZE_KEY, FIRST_NAME_MIN_SIZE_VALUE);
            addServerMessagesValue(FIRST_NAME_MAX_SIZE_KEY, FIRST_NAME_MAX_SIZE_VALUE);
            addServerMessagesValue(LAST_NAME_MIN_SIZE_KEY, LAST_NAME_MIN_SIZE_VALUE);
            addServerMessagesValue(LAST_NAME_MAX_SIZE_KEY, LAST_NAME_MAX_SIZE_VALUE);
            addServerMessagesValue(PASSWORD_MIN_SIZE_KEY, PASSWORD_MIN_SIZE_VALUE);
            addServerMessagesValue(PASSWORD_MAX_SIZE_KEY, PASSWORD_MAX_SIZE_VALUE);
            addServerMessagesValue(PASSWORD_QUESTION_MIN_SIZE_KEY, PASSWORD_QUESTION_MIN_SIZE_VALUE);
            addServerMessagesValue(PASSWORD_QUESTION_MAX_SIZE_KEY, PASSWORD_QUESTION_MAX_SIZE_VALUE);
            addServerMessagesValue(PASSWORD_ANSWER_MIN_SIZE_KEY, PASSWORD_ANSWER_MIN_SIZE_VALUE);
            addServerMessagesValue(PASSWORD_ANSWER_MAX_SIZE_KEY, PASSWORD_ANSWER_MAX_SIZE_VALUE);
            // designer related
            addServerMessagesValue(PAGE_NAME_MIN_SIZE_KEY, PAGE_NAME_MIN_SIZE_VALUE);
            addServerMessagesValue(PAGE_NAME_MAX_SIZE_KEY, PAGE_NAME_MAX_SIZE_VALUE);
            addServerMessagesValue(PAGE_PATH_MIN_SIZE_KEY, PAGE_PATH_MIN_SIZE_VALUE);
            addServerMessagesValue(PAGE_PATH_MAX_SIZE_KEY, PAGE_PATH_MAX_SIZE_VALUE);
            addServerMessagesValue(PAGE_DESCRIPTION_MIN_SIZE_KEY, PAGE_DESCRIPTION_MIN_SIZE_VALUE);
            addServerMessagesValue(PAGE_DESCRIPTION_MAX_SIZE_KEY, PAGE_DESCRIPTION_MAX_SIZE_VALUE);
            addServerMessagesValue(SECTION_NAME_MIN_SIZE_KEY, SECTION_NAME_MIN_SIZE_VALUE);
            addServerMessagesValue(SECTION_NAME_MAX_SIZE_KEY, SECTION_NAME_MAX_SIZE_VALUE);
            addServerMessagesValue(SECTION_NOTE_MIN_SIZE_KEY, SECTION_NOTE_MIN_SIZE_VALUE);
            addServerMessagesValue(SECTION_NOTE_MAX_SIZE_KEY, SECTION_NOTE_MAX_SIZE_VALUE);
            addServerMessagesValue(LINK_NAME_MIN_SIZE_KEY, LINK_NAME_MIN_SIZE_VALUE);
            addServerMessagesValue(LINK_NAME_MAX_SIZE_KEY, LINK_NAME_MAX_SIZE_VALUE);
            addServerMessagesValue(LINK_URL_MIN_SIZE_KEY, LINK_URL_MIN_SIZE_VALUE);
            addServerMessagesValue(LINK_URL_MAX_SIZE_KEY, LINK_URL_MAX_SIZE_VALUE);
            addServerMessagesValue(LINK_NOTE_MIN_SIZE_KEY, LINK_NOTE_MIN_SIZE_VALUE);
            addServerMessagesValue(LINK_NOTE_MAX_SIZE_KEY, LINK_NOTE_MAX_SIZE_VALUE);
            // feedback related
            addServerMessagesValue(FEEDBACK_NAME_MAX_SIZE_KEY, FEEDBACK_NAME_MAX_SIZE_VALUE);
            addServerMessagesValue(FEEDBACK_EMAIL_MAX_SIZE_KEY, FEEDBACK_EMAIL_MAX_SIZE_VALUE);
            addServerMessagesValue(FEEDBACK_COMMENT_MAX_SIZE_KEY, FEEDBACK_COMMENT_MAX_SIZE_VALUE);

            // misc
            addValue(model, "account_maxlength_offset", 5); // 5 characters

            if (logger.isFiner()) {
                TreeMap<String, String> sortedValues = new TreeMap<>();
                sortedValues.putAll(allServerMessages);
                for (String key : sortedValues.navigableKeySet()) {
                    logger.finer("Loaded controller server model message key %s with value %s.", key, allServerMessages.get(key));
                }
            }
            if (logger.isInfo()) {
                logger.info("Loaded messages into controller message cache.");
            }
        }
        model.addAllObjects(allServerMessages);
    }

	ResponseDto toResponseDto(ServiceResult<?> serviceResult) {
		ResponseDto responseDto = new ResponseDto(serviceResult);
		if (logger.isFine()) {
			logger.fine("Transformed service result into a response DTO with overall success status as: %b.", responseDto.isSuccess);
		}		
		return responseDto;
	}

    UserMessage userMessage(String key) {
		return applicationService.getUserMessage(key);
	}
	
	String message(String key) {
		return applicationService.getUserMessage(key).getMessage();
	}

    void addMessage(ModelAndView model, String key) {
        model.addObject(key, message(key));
    }

    void addServerMessagesValue(String key, Object value) {
        String stringValue = value.toString();
        if (value instanceof Integer) {
            stringValue = ((Integer)value).toString();
        }
        allServerMessages.put(key, stringValue);
    }

    void loadGenericParameterizedMessage(String key, Object... paramVals) {
        String msg = parameterizeMessage(key, paramVals);
        allServerMessages.put(key, msg);
    }

	void loadCustomMessage(ModelAndView model, String key, Object... paramVals) {
		String msg = parameterizeMessage(key, paramVals);
		model.addObject(key, msg);
	}

    private String parameterizeMessage(String key, Object... paramVals) {
        String msg = message(key);
        if (paramVals.length > 4) {
            throw new IllegalArgumentException("Not capable of handling more than 4 parameters in a parameterized string.");
        }
        for (int i = 0; i < paramVals.length; i++) {
            String val = paramVals[i].toString();
            switch (i) {
                case 0: msg = msg.replace(REPLACEMENT_TOKEN_0, val);	break;
                case 1: msg = msg.replace(REPLACEMENT_TOKEN_1, val);	break;
                case 2: msg = msg.replace(REPLACEMENT_TOKEN_2, val);	break;
                case 3: msg = msg.replace(REPLACEMENT_TOKEN_3, val);	break;
                default: throw new IllegalArgumentException();
            }
        }
        return msg;
    }

	void addMessageReplacement(ModelAndView model, String userMessageKey, String jspKey) {
		String userMessageValue = message(userMessageKey);
		model.addObject(jspKey, userMessageValue);
	}
	
	void addValue(ModelAndView model, String key, Object value) {
		model.addObject(key, value);
	}
	
	void transferToCache(ModelAndView model, Map<String, String> cache) {
		Map<String, Object> modelAttributes = model.getModel();
		for (String key : modelAttributes.keySet()) {
			Object obj = modelAttributes.get(key);
			if (obj instanceof String) {
				cache.put(key, ((String) obj));
			}
		}
	}

    void loadPageLoadData(Object object, ModelAndView model) {
        String json = utilityService.jsonStringify(object);
        if (logger.isFinest()) {
            logger.finest("Page load data is: %s", json);
        }
        model.addObject(Constants.PAGE_LOAD_DATA, json);
    }
}