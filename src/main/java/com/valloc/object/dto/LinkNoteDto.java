/*
 * Property of Will Stevens
 * All rights reserved.
 */
package com.valloc.object.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.valloc.object.domain.LinkNote;

import java.util.Date;

//import org.codehaus.jackson.annotate.JsonIgnore;
//import org.codehaus.jackson.annotate.JsonProperty;
//import org.codehaus.jackson.map.annotate.JsonDeserialize;
//import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 *
 *
 * 
 * @author wstevens
 */
public class LinkNoteDto // does NOT implement LinkNote
{
    @JsonIgnore
    private LinkNote linkNoteBackReference;

    private String username;
    private String firstName;
    private String lastName;
	private String note;
    private Date postTimestamp;


    public LinkNote getLinkNoteBackReference() {
        return linkNoteBackReference;
    }

    public void setLinkNoteBackReference(LinkNote linkNoteBackReference) {
        this.linkNoteBackReference = linkNoteBackReference;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @JsonSerialize(using=JsonPrintedDateSerializer.class)
    public Date getPostTimestamp() {
        return postTimestamp;
    }

    @JsonDeserialize(using=JsonPrintedDateDeserializer.class)
    public void setPostTimestamp(Date postTimestamp) {
        this.postTimestamp = postTimestamp;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }


    @JsonSerialize(using=JsonConversationDateSerializer.class)
    public Date getPostTimestampDisplay() {
        return postTimestamp;
    }
    public void setPostTimestampDisplay(String doNothing) {}
}
