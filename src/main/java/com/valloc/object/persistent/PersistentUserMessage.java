/*
 * Property of Will Stevens
 * All rights reserved.
 */
package com.valloc.object.persistent;

import com.valloc.Exposure;
import com.valloc.Locale;
import com.valloc.MessageType;
import com.valloc.SpecConstants;
import com.valloc.object.domain.UserMessage;

import javax.persistence.*;

/**
 * 
 *
 * 
 * @author wstevens
 */
@Entity
@Table(name="user_message")
@SequenceGenerator(
	    name="id_seq",
	    allocationSize=1,
	    sequenceName="user_message_id_seq"
	)
public class PersistentUserMessage extends UpdatableDomainObject implements UserMessage
{
	private String key;
	private String message;
	private Locale locale;
	private MessageType type;
	private Integer code;
	private Exposure exposure;
	
	@Column(name="key", nullable=false)
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	
	@Column(name="locale", nullable=false)
	@Enumerated(EnumType.STRING)
	public Locale getLocale() {
		return locale;
	}
	public void setLocale(Locale locale) {
		this.locale = locale;
	}

	@Column(name="message", nullable=false, length=SpecConstants.USER_MESSAGE_MESSAGE_MAX_SIZE_VALUE)
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

	@Column(name="type")
	@Enumerated(EnumType.STRING)
	public MessageType getType() {
		return type;
	}
	public void setType(MessageType type) {
		this.type = type;
	}

	@Column(name="code")
	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}

	@Column(name="exposure", nullable=false)
	@Enumerated(EnumType.STRING)
	public Exposure getExposure() {
		return exposure;
	}
	public void setExposure(Exposure exposure) {
		this.exposure = exposure;
	}
	
	public UserMessage toCustomizedInstance(String customizedMessage) {
		PersistentUserMessage retval = new PersistentUserMessage();
		retval.key = this.key;
		retval.message = customizedMessage;
		retval.locale = this.locale;
		retval.code = this.code;
		retval.exposure = this.exposure;
		return retval;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((locale == null) ? 0 : locale.hashCode());
		result = prime * result + ((key == null) ? 0 : key.hashCode());
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
		PersistentUserMessage other = (PersistentUserMessage) obj;
		if (locale != other.getLocale())
			return false;
		if (key == null) {
			if (other.getKey() != null)
				return false;
		} else if (!key.equals(other.getKey()))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "PersistentUserMessage [locale=" + locale + ", key=" + key + ", message="
				+ message + ", ID=" + getId() + "]";
	}

	
}
