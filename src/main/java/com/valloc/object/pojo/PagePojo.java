/*
 * Property of Will Stevens
 * All rights reserved.
 */
package com.valloc.object.pojo;

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
public class PagePojo implements Page
{
	private User user;
	private Visibility visibility;
	private String name;
	private String path;
	private boolean isHome;
	private boolean isSharedPrivately;
	private String description;
	private List<Column> columns = new ArrayList<>();
	private List<PageGuest> pageGuests = new ArrayList<>();

	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Visibility getVisibility() {
		return visibility;
	}
	public void setVisibility(Visibility visibility) {
		this.visibility = visibility;
	}
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
	public void addColumn(Column column) {
		columns.add(column);
		column.setPage(this);
	}
	public void removeColumn(Column column) {
		columns.remove(column);
		column.setPage(null);
	}
	public List<? extends Column> getColumns() {
		return columns;
	}

    @Override
    public List<? extends PageGuest> getPageGuests() {
        return pageGuests;
    }

    @Override
    public void addPageGuest(PageGuest pageGuest) {
        pageGuests.add(pageGuest);
        pageGuest.setPage(this);
    }

    @Override
    public void removePageGuest(PageGuest pageGuest) {
        pageGuests.remove(pageGuest);
        pageGuest.setPage(null);
    }

    @Override
    public void addPageGuests(List<? extends PageGuest> pageGuests) {
        for (PageGuest pageGuest : pageGuests) {
            addPageGuest(pageGuest);
        }
    }

    @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((path == null) ? 0 : path.hashCode());
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
		PagePojo other = (PagePojo) obj;
		if (path == null) {
			if (other.path != null)
				return false;
		} else if (!path.equals(other.path))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return String.format("PagePojo [name=%s, path=%s]", name, path);
	}
    
    
}
