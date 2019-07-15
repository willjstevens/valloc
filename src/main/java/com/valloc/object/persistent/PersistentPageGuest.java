/*
 * Property of Will Stevens
 * All rights reserved.
 */
package com.valloc.object.persistent;

import com.valloc.object.domain.Page;
import com.valloc.object.domain.PageGuest;
import com.valloc.object.domain.User;

import javax.persistence.*;

/**
 * 
 *
 * 
 * @author wstevens
 */
@Entity
@Table(name="page_guest")
@SequenceGenerator(
	    name="id_seq",
	    allocationSize=1,
	    sequenceName="page_guest_id_seq"
	)
public class PersistentPageGuest extends UpdatableDomainObject implements PageGuest
{
	private PersistentUser ownerUser;
	private PersistentUser guestUser;
	private PersistentPage page;
	private boolean canModify;

	public PersistentPageGuest() {}
	
	public PersistentPageGuest(PersistentUser ownerUser, PersistentUser guestUser, PersistentPage page, boolean canModify) {
		this.ownerUser = ownerUser;
		this.guestUser = guestUser;
		this.canModify = canModify;
	}

    @ManyToOne
    @JoinColumn(name="owner_user_id", nullable=false)
	public PersistentUser getOwnerUser() {
		return ownerUser;
	}
	public void setOwnerUser(User ownerUser) {
		this.ownerUser = (PersistentUser) ownerUser;
	}

    @ManyToOne
    @JoinColumn(name="page_id")
    public PersistentPage getPage() {
        return page;
    }
    public void setPage(Page page) {
        this.page = (PersistentPage) page;
    }

    @ManyToOne
    @JoinColumn(name="guest_user_id", nullable=false)
    public PersistentUser getGuestUser() {
        return guestUser;
    }
	public void setGuestUser(User guestUser) {
		this.guestUser = (PersistentUser) guestUser;
	}

	@Column(name="can_modify", nullable=false)
	public boolean getCanModify() {
		return canModify;
	}
	public void setCanModify(boolean canModify) {
		this.canModify = canModify;
	}
    @Override
    public boolean canModify() {
        return canModify;
    }
}