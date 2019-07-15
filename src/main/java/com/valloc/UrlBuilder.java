/*
 * Property of Will Stevens
 * All rights reserved.
 */
package com.valloc;

import com.valloc.log.LogManager;
import com.valloc.log.Logger;
import com.valloc.service.ConfigurationService;

import java.util.ArrayList;
import java.util.List;

import static com.valloc.UrlConstants.*;

/**
 *
 *
 * 
 * @author wstevens
 */
public final class UrlBuilder 
{
	private static final Logger logger = LogManager.manager().newLogger(UrlBuilder.class, Category.UTILITY);
	private ConfigurationService configurationService;
	
	private String domainPrefix;
	private List<String> actionParts = new ArrayList<>();
	private List<RequestParamPair> requestParams = new ArrayList<>();
    private boolean makeAbsolute;
	
	public UrlBuilder(ConfigurationService configurationService) {
		this.configurationService = configurationService;
	}

	private class RequestParamPair {
		String key;
		String value;
		public RequestParamPair(String key, String value) {
			this.key = key;
			this.value = value;
		}
	}

	public UrlBuilder setDomainPrefixPlain() {
		domainPrefix = configurationService.getSiteDomainHttpString();
		return this;
	}

	public UrlBuilder setDomainPrefixSecure() {
		domainPrefix = configurationService.getSecureSiteDomainHttpsString();
		return this;
	} 

    public void makeAbsolute() {
        makeAbsolute = true;
    }

	public UrlBuilder addRequestParameterPair(String key, String value) {
		RequestParamPair pair = new RequestParamPair(key, value);
		requestParams.add(pair);
		return this;
	}
	
	public UrlBuilder appendActionPathPart(String actionPart) {
		actionParts.add(actionPart);
		return this;
	}
	
	public UrlBuilder appendUserHomePage(String username) {
		StringBuilder userPagePathBuilder = new StringBuilder();
		userPagePathBuilder.append(URL_PATH_SEPARATOR_CHAR);
		userPagePathBuilder.append(username);
		String path = userPagePathBuilder.toString();
		if (logger.isFiner()) {
			logger.finer("Assembled user home page as: %s", path);
		}
		appendActionPathPart(path);
		return this;
	}
	
	public UrlBuilder appendUserPage(String username, String pagePath) {
		StringBuilder userPagePathBuilder = new StringBuilder();
		userPagePathBuilder.append(URL_PATH_SEPARATOR_CHAR);
		userPagePathBuilder.append(username);
		userPagePathBuilder.append(URL_PATH_SEPARATOR_CHAR);
		userPagePathBuilder.append(pagePath);
		String path = userPagePathBuilder.toString();
		if (logger.isFiner()) {
			logger.finer("Assembled user home page as: %s", path);
		}
		appendActionPathPart(path);
		return this;
	}

    public UrlBuilder appendEditPage(String pagePath) {
        StringBuilder userPagePathBuilder = new StringBuilder();
        userPagePathBuilder.append(PAGE_EDIT);
        userPagePathBuilder.append(URL_PATH_SEPARATOR_CHAR);
        userPagePathBuilder.append(pagePath);
        String path = userPagePathBuilder.toString();
        if (logger.isFiner()) {
            logger.finer("Assembled page edit as: %s", path);
        }
        appendActionPathPart(path);
        return this;
    }

    public UrlBuilder appendGuestEditPage(String ownerUsername, String pagePath) {
        StringBuilder userPagePathBuilder = new StringBuilder();
        userPagePathBuilder.append(PAGE_GUEST_EDIT);
        userPagePathBuilder.append(URL_PATH_SEPARATOR_CHAR);
        userPagePathBuilder.append(ownerUsername);
        userPagePathBuilder.append(URL_PATH_SEPARATOR_CHAR);
        userPagePathBuilder.append(pagePath);
        String path = userPagePathBuilder.toString();
        if (logger.isFiner()) {
            logger.finer("Assembled page guest edit as: %s", path);
        }
        appendActionPathPart(path);
        return this;
    }

	public String buildActionParts() {
		StringBuilder builder = new StringBuilder();
		for (String actionPart : actionParts) {
			builder.append(actionPart);
		}
		return builder.toString();
	}
	
	public void clear() {
		domainPrefix = null;
		actionParts.clear();
		requestParams.clear();
	}
	
	public String buildUrl() {
		StringBuilder builder = new StringBuilder();

        // if no domain prefix is set but make absolute is, then default to NON-secure
        if (makeAbsolute && domainPrefix == null) {
            setDomainPrefixPlain();
        }
        // now if domain prefix is set then attach
		if (domainPrefix != null) {
			builder.append(domainPrefix);			
		}

		for (String actionPart : actionParts) {
            if (!actionPart.startsWith(URL_PATH_SEPARATOR)) {
                builder.append(URL_PATH_SEPARATOR_CHAR);
            }
			builder.append(actionPart);
		}
		if (!requestParams.isEmpty()) {
			builder.append(QUERY_STRING_SEPARATOR);
			for (int i = 0, size = requestParams.size(); i < size; i++) {
				RequestParamPair pair = requestParams.get(i);
				builder.append(pair.key).append(QUERY_STRING_ASSIGNMENT).append(pair.value);
				if (i != size-1) {
					builder.append(QUERY_STRING_AND);
				}
			}
		}
		
		String absoluteUrl = builder.toString();
		if (logger.isFine()) {
			logger.fine("Assembled URL is: ", absoluteUrl);
		}
		
		return absoluteUrl;
	}
	
	 
}
