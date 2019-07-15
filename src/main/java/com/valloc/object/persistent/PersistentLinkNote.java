/*
 * Property of Will Stevens
 * All rights reserved.
 */
package com.valloc.object.persistent;

import com.valloc.object.domain.Link;
import com.valloc.object.domain.LinkNote;
import com.valloc.object.domain.User;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;

/**
 *
 *
 * 
 * @author wstevens
 */
@Entity
@Table(name="link_note", schema="public")
@SequenceGenerator(
	    name="id_seq",
	    allocationSize=1,
	    sequenceName="link_note_id_seq"
	)
public class PersistentLinkNote extends UpdatableDomainObject implements LinkNote
{
	private PersistentLink link;
    private PersistentUser user;
	private String note;
    private Date postTimestamp;

    public PersistentLinkNote() {}

    public PersistentLinkNote(PersistentUser user, String note, Date postTimestamp) {
        this.user = user;
        this.note = note;
        this.postTimestamp = postTimestamp;
    }

    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name="link_id", insertable=false, updatable=false, nullable=false)
	@Override
	public PersistentLink getLink() {
		return link;
	}
	@Override
	public void setLink(Link link) {
		this.link = (PersistentLink) link;
	}

    @ManyToOne
    @JoinColumn(name="user_id", nullable=false)
    @Override
    public PersistentUser getUser() {
        return user;
    }
    @Override
    public void setUser(User user) {
        this.user = (PersistentUser) user;
    }

	@Column(name="note", nullable=false)
    @Lob
    @Type(type="org.hibernate.type.TextType")
	@Override
	public String getNote() {
		return note;
	}
	@Override
	public void setNote(String note) {
		this.note = note;
	}


    /* (non-Javadoc)
     * @see com.valloc.domain.DomainObject#getupdateTimestamp()
     */
    @Column(name="post_timestamp", nullable=false)
    @Override
    public Date getPostTimestamp() {
        return postTimestamp;
    }
    public void setPostTimestamp(Date postTimestamp) {
        this.postTimestamp = postTimestamp;
    }
}
