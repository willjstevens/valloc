/*
 * Property of Will Stevens
 * All rights reserved.
 */
package com.valloc.object.domain;

import java.util.Date;


/**
 *
 *
 * 
 * @author wstevens
 */
public interface LinkNote
{
	public Link getLink();
	public void setLink(Link link);
    public User getUser();
    public void setUser(User user);
    public String getNote();
	public void setNote(String note);
    public Date getPostTimestamp();
    public void setPostTimestamp(Date postTimestamp);


}
