/*
 * Property of Will Stevens
 * All rights reserved.
 */
package com.valloc.object.pojo;

import com.valloc.object.domain.Page;
import com.valloc.object.domain.PageGuest;
import com.valloc.object.domain.User;

/**
 *
 *
 * 
 * @author wstevens
 */
public class PageGuestPojo implements PageGuest 
{
	private User ownerUser;
	private User guestUser;
	private Page page;
	private boolean canModify;
	
	public User getOwnerUser() {
		return ownerUser;
	}
	public void setOwnerUser(User ownerUser) {
		this.ownerUser = ownerUser;
	}

	public User getGuestUser() {
		return guestUser;
	}
	public void setGuestUser(User guestUser) {
		this.guestUser = guestUser;
	}

	public Page getPage() {
		return page;
	}
	public void setPage(Page page) {
		this.page = page;
	}

	public boolean canModify() {
		return canModify;
	}
	public void setCanModify(boolean canModify) {
		this.canModify = canModify;
	}
}
