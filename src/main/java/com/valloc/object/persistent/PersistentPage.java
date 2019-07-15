/*
 * Property of Will Stevens
 * All rights reserved.
 */
package com.valloc.object.persistent;

import com.valloc.Visibility;
import com.valloc.object.domain.Page;
import com.valloc.object.domain.PageGuest;
import com.valloc.object.domain.User;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 *
 * 
 * @author wstevens
 */
@Entity
@Table(name="page")
@SequenceGenerator(
	    name="id_seq",
	    allocationSize=1,
	    sequenceName="page_id_seq"
	)
public class PersistentPage extends UpdatableDomainObject implements Page
{
	private PersistentUser user;
	private Visibility visibility;
	private String name;
	private String path;
	private boolean isHome;
	private boolean isSharedPrivately;
	private String description;	
	private List<PersistentColumn> columns = new ArrayList<>();

    private List<PersistentPageGuest> pageGuests = new ArrayList<>();
		
	@ManyToOne(cascade={CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name="user_id")
	@Override
	public PersistentUser getUser() {
		return user;
	}
	@Override
	public void setUser(User user) {
		this.user = (PersistentUser) user;
	}

    @Column(name="visibility", nullable=false)
	@Enumerated(EnumType.STRING)
	@Override
	public Visibility getVisibility() {
		return visibility;
	}
	@Override
	public void setVisibility(Visibility visibility) {
		this.visibility = visibility;
	}
	
	@Column(name="name", nullable=false)
	@Override
	public String getName() {
		return name;
	}
	@Override
	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name="path", nullable=false)
	@Override
	public String getPath() {
		return path;
	}
	@Override
	public void setPath(String path) {
		this.path = path;
	}
	
	@Column(name="is_home", nullable=false)
	@Override
	public boolean isHome() {
		return isHome;
	}
	@Override
	public void setHome(boolean isHome) {
		this.isHome = isHome;
	}
	
	@Column(name="is_shared_privately", nullable=false)
	@Override
	public boolean isSharedPrivately() {
		return isSharedPrivately;
	}
	@Override
	public void setSharedPrivately(boolean isSharedPrivately) {
		this.isSharedPrivately = isSharedPrivately;
	}
	
	@Column(name="description")
	@Override
	public String getDescription() {
		return description;
	}
	@Override
	public void setDescription(String description) {
		this.description = description;
	}

    @OneToMany(cascade={CascadeType.ALL})
    @OrderColumn(name="column_order")
    @JoinColumn(name="page_id", nullable=false)
	@Override
	public List<PersistentColumn> getColumns() {
		return columns;
	}
	public void setColumns(List<PersistentColumn> columns) {
		this.columns = columns;
		for (PersistentColumn column : columns) {
			if (column.getPage() == null) {
				column.setPage(this);
			}
		}
	}
	@Override
	public void addColumn(com.valloc.object.domain.Column column) {
		PersistentColumn persistentColumn = (PersistentColumn) column;
		columns.add(persistentColumn);
		persistentColumn.setPage(this);
	}
    @Override
	public void removeColumn(com.valloc.object.domain.Column column) {
		PersistentColumn persistentColumn = (PersistentColumn) column;
		columns.remove(persistentColumn);
		persistentColumn.setPage(null);
	}

    @OneToMany(mappedBy="page", cascade={CascadeType.ALL})
    @Override
    public List<PersistentPageGuest> getPageGuests() {
        return pageGuests;
    }
    public void setPageGuests(List<PersistentPageGuest> pageGuests) {
        this.pageGuests = pageGuests;
        for (PersistentPageGuest pageGuest : pageGuests) {
            if (pageGuest.getPage() == null) {
                pageGuest.setPage(this);
            }
        }
    }
    @Override
    public void addPageGuest(PageGuest pageGuest) {
        PersistentPageGuest persistentPageGuest = (PersistentPageGuest) pageGuest;
        pageGuests.add(persistentPageGuest);
        pageGuest.setPage(this);
    }
    @Override
    public void removePageGuest(PageGuest pageGuest) {
        PersistentPageGuest persistentPageGuest = (PersistentPageGuest) pageGuest;
        pageGuests.remove(persistentPageGuest);
        persistentPageGuest.setPage(null);
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
		int result = super.hashCode();
		result = prime * result + ((path == null) ? 0 : path.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		PersistentPage other = (PersistentPage) obj;
		if (path == null) {
			if (other.path != null)
				return false;
		} else if (!path.equals(other.path))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return String.format("PersistentPage [name=%s, path=%s]", name, path);
	}
}
