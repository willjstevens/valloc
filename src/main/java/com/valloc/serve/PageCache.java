/*
 * Property of Will Stevens
 * All rights reserved.
 */
package com.valloc.serve;


import com.valloc.Visibility;
import com.valloc.object.domain.Page;
import com.valloc.object.domain.PageGuest;

import java.util.HashSet;
import java.util.Set;

/**
 *
 *
 * 
 * @author wstevens
 */
class PageCache
{
	private String name;
	private Visibility visibility;
	private String path;
	private boolean isHome;
	private boolean isSharedPrivately;
	private String description;
	private Set<String> pageGuestUsernames = new HashSet<>();
	private byte[] pageContents;
	// added at request time
	private String etagValue;
	

	PageCache(Page page) {
		name = page.getName();
		path = page.getPath();
		visibility = page.getVisibility();
		isHome = page.isHome();
		isSharedPrivately = page.isSharedPrivately();
		description = page.getDescription();
		for (PageGuest guest : page.getPageGuests()) {
			String guestUsername = guest.getGuestUser().getUsername();
			pageGuestUsernames.add(guestUsername);
		}		
	}
	
	boolean hasPageContents() {
		return pageContents != null;
	}
	
	boolean isPublic() {
		return visibility == Visibility.PUBLIC;
	}
	
	boolean isPrivate() {
		return visibility == Visibility.PRIVATE;
	}
	
	boolean userHasAccess(String username) {
		return pageGuestUsernames.contains(username);
	}
	
	String getEtagValue() {
		return etagValue;
	}

	void setEtagValue(String etagValue) {
		this.etagValue = etagValue;
	}

	String getName() {
		return name;
	}

	Visibility getVisibility() {
		return visibility;
	}

	String getPath() {
		return path;
	}

	boolean isHome() {
		return isHome;
	}

	boolean isSharedPrivately() {
		return isSharedPrivately;
	}

	String getDescription() {
		return description;
	}
	
	byte[] getPageContents() {
		return pageContents;
	}

	void setPageContents(byte[] pageContents) {
		this.pageContents = pageContents;
	}

	void setHome(boolean isHome) {
		this.isHome = isHome;
	}

	void setVisibility(Visibility visibility) {
		this.visibility = visibility;
	}
	
	
}
