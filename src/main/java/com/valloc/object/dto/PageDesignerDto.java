/*
 * Property of Will Stevens
 * All rights reserved.
 */
package com.valloc.object.dto;

//import org.codehaus.jackson.annotate.JsonIgnore;
//import org.codehaus.jackson.annotate.JsonIgnoreProperties;
//import org.codehaus.jackson.annotate.JsonProperty;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;
import java.util.Map;

/**
 *
 *
 * 
 * @author wstevens
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class PageDesignerDto
{
	private String originalPath;
    public UserMessageDto relayMessage;
    public Map<String, Object> attributes = new HashMap<>();
    @JsonIgnore
    private boolean isEdit;
    @JsonIgnore
    private boolean isGuestEdit;

	public PageDesignerDto() {}

    @JsonProperty(value="isNewPage")
    private boolean isNewPage() {
        return !isEdit; // if not edit than a new page
    }
    @JsonProperty(value="isEditPage")
    private boolean isEditPage() {
        return isEdit;
    }
    @JsonProperty(value="isOwnerEdit")
    public boolean isOwnerEdit() {
        return !isGuestEdit; // if not guest edit then owner
    }
    @JsonProperty(value="isGuestEdit")
    public boolean isGuestEdit() {
        return isGuestEdit;
    }

    @JsonProperty("pageData")
    private PageDto pageDto;

    public void setIsEdit(boolean isEdit) {
        this.isEdit = isEdit;
    }
    public void setGuestEdit(boolean guestEdit) {
        isGuestEdit = guestEdit;
    }
    public String getOriginalPath() {
		return originalPath;
	}
	public void setOriginalPath(String originalPath) {
		this.originalPath = originalPath;
	}
    public PageDto getPageDto() {
        return pageDto;
    }
    public void setPageDto(PageDto pageDto) {
        this.pageDto = pageDto;
    }
    public UserMessageDto getRelayMessage() {
        return relayMessage;
    }
    public void setRelayMessage(UserMessageDto relayMessage) {
        this.relayMessage = relayMessage;
    }

    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public void addAllAttributes(Map<String, Object> attributes) {
        this.attributes.putAll(attributes);
    }
}
