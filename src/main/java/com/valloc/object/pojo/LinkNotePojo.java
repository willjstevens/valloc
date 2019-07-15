/*
 * Property of Will Stevens
 * All rights reserved.
 */
package com.valloc.object.pojo;

import com.valloc.object.domain.Link;
import com.valloc.object.domain.LinkNote;
import com.valloc.object.domain.User;

import java.util.Date;

/**
 *
 *
 * 
 * @author wstevens
 */
public class LinkNotePojo implements LinkNote
{
	private Link link;
	private User user;
	private String note;
    private Date postTimestamp;

    public Link getLink() {
        return link;
    }

    public void setLink(Link link) {
        this.link = link;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Date getPostTimestamp() {
        return postTimestamp;
    }

    public void setPostTimestamp(Date postTimestamp) {
        this.postTimestamp = postTimestamp;
    }
}
