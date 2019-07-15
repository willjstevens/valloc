/*
 * Property of Will Stevens
 * All rights reserved.
 */
package com.valloc.object.domain;

/**
 *
 *
 * 
 * @author wstevens
 */
public interface PageGuest 
{
	public User getOwnerUser();
	public void setOwnerUser(User ownerUser);
	public User getGuestUser();
	public void setGuestUser(User guestUser);
	public Page getPage();
	public void setPage(Page page);
	public boolean canModify();
	public void setCanModify(boolean canModify);
}
