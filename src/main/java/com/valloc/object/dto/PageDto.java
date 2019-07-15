/*
 * Property of Will Stevens
 * All rights reserved.
 */
package com.valloc.object.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.valloc.Visibility;
import com.valloc.object.domain.Column;
import com.valloc.object.domain.Page;
import com.valloc.object.domain.PageGuest;
import com.valloc.object.domain.User;

import java.util.ArrayList;
import java.util.List;

/**
 *
 *
 * 
 * @author wstevens
 */
public class PageDto implements Page
{


    private String username;
    private String name;
    private String path;
    private boolean isHome;
    private Visibility visibility;
    private boolean isSharedPrivately;
    private String description;
    private List<ColumnDto> columns = new ArrayList<>();
    private List<PageGuestDto> pageGuests = new ArrayList<>();


	public PageDto() {}
	
	// used by subclass
    public PageDto(String username, String name, String path, Visibility visibility, String description) {
    	this.username = username;
		this.name = name;
		this.path = path;
		this.visibility = visibility;
		this.description = description;
	}
    
    public PageDto(String username, String name, String path, Visibility visibility, boolean isHome, boolean isSharedPrivately, String description) {
    	this(username, name, path, visibility, description);
		this.isHome = isHome;
		this.isSharedPrivately = isSharedPrivately;
	}

	@JsonIgnore
	public User getUser() {
//		Util.throwNpe("No bi-directional relationships supported.");
		return null;
	}
	@JsonIgnore
	public void setUser(User user) {
//		Util.throwNpe("No bi-directional relationships supported.");
	}
	
	@JsonIgnore
    @Override
    public List<? extends PageGuest> getPageGuests() {
        return null;
    }

	@JsonIgnore
    @Override
    public void addPageGuest(PageGuest pageGuest) {}

	@JsonIgnore
    @Override
    public void removePageGuest(PageGuest pageGuest) {}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public boolean isHome() {
		return isHome;
	}
	public void setHome(boolean isHome) {
		this.isHome = isHome;
	}
	public boolean isSharedPrivately() {
		return isSharedPrivately;
	}
	public void setSharedPrivately(boolean isSharedPrivately) {
		this.isSharedPrivately = isSharedPrivately;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public Visibility getVisibility() {
		return visibility;
	}
	public void setVisibility(Visibility visibility) {
		this.visibility = visibility;
	}
	
	@Override
	public void addColumn(Column column) {
        columns.add((ColumnDto) column);
		column.setPage(this);
	}
	@Override
	public void removeColumn(Column column) {
		columns.remove(column);
		column.setPage(null);
	}
	@Override
	public List<? extends Column> getColumns() {
		return columns;
	}

	public void addPageGuest(PageGuestDto pageGuest) {
		pageGuests.add(pageGuest);
	}

    @Override
    public void addPageGuests(List<? extends PageGuest> pageGuests) {
        for (PageGuest pageGuest : pageGuests) {
            addPageGuest(pageGuest);
        }
    }

    @JsonProperty("pageGuests")
	public List<PageGuestDto> getPageGuestDtos() {
        return pageGuests;
    }

    public void clearPageGuestDtos() {
        pageGuests.clear();
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((path == null) ? 0 : path.hashCode());
		result = prime * result
				+ ((username == null) ? 0 : username.hashCode());
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
		PageDto other = (PageDto) obj;
		if (path == null) {
			if (other.path != null)
				return false;
		} else if (!path.equals(other.path))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}

	
}
