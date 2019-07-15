/*
 * Property of Will Stevens
 * All rights reserved.
 */
package com.valloc.framework;

import com.valloc.object.domain.User;
import com.valloc.object.domain.UserMessage;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 *
 *
 * 
 * @author wstevens
 */
public class Session
{
	private HttpSession httpSession;
	private User user;
    private UserMessage relayMessage;
    private Map<String, Object> attributes = new HashMap<>();
	
	Session() { /* for stub usage */ }
	
	Session(User user) {
		this.user = user;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getUsername() {
		return user.getUsername();
	}
	public HttpSession getHttpSession() {
		return httpSession;
	}
	public void setHttpSession(HttpSession httpSession) {
		this.httpSession = httpSession;
	}
    public Map<String, Object> getAttributes() {
        return attributes;
    }
    public void addAllAttributes(Map<String, Object> attributes) {
        this.attributes.putAll(attributes);
    }

    public void addAttribute(String key, Object object) {
        attributes.put(key, object);
    }

    public void removeAttribute(String key) {
        attributes.remove(key);
    }

    public void clearAttributes() {
        attributes.clear();
    }

    public boolean hasAttributes() {
        return !hasNoAttributes();
    }

    public boolean hasNoAttributes() {
        return attributes.isEmpty();
    }

    public UserMessage getRelayMessage() {
        return relayMessage;
    }

    public void setRelayMessage(UserMessage relayMessage) {
        this.relayMessage = relayMessage;
    }

	@Override
	public String toString() {
		return "Session for [username=" + user.getUsername() + "]";
	}
}
