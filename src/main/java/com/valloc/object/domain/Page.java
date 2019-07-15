/*
 * Property of Will Stevens
 * All rights reserved.
 */
package com.valloc.object.domain;

import com.valloc.Visibility;

import java.util.List;




/**
 * 
 *
 * 
 * @author wstevens
 */
public interface Page
{
	public User getUser();
	public void setUser(User user);
	public Visibility getVisibility();
	public void setVisibility(Visibility visibility);
	public String getName();
	public void setName(String name);
	public String getPath();
	public void setPath(String path);
	public boolean isHome();
	public void setHome(boolean isHome);
	public boolean isSharedPrivately();
	public void setSharedPrivately(boolean isSharedPrivately);
	public String getDescription();
	public void setDescription(String description);	
	public void addColumn(Column column);
	public void removeColumn(Column column);
	public List<? extends Column> getColumns();

    public List<? extends PageGuest> getPageGuests();
    public void addPageGuest(PageGuest pageGuest);
    public void removePageGuest(PageGuest pageGuest);
    public void addPageGuests(List<? extends PageGuest> pageGuests);
}