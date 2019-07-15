/*
 * Property of Will Stevens
 * All rights reserved.
 */
package com.valloc.object.persistent;

import com.valloc.object.domain.Link;
import com.valloc.object.domain.LinkNote;
import com.valloc.object.domain.Section;

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
@Table(name="link")
@SequenceGenerator(
	    name="id_seq",
	    allocationSize=1,
	    sequenceName="link_id_seq"
	)
public class PersistentLink extends UpdatableDomainObject implements Link
{
	private PersistentSection section;
	private String name;
	private String url;
	private String note;
    private List<PersistentLinkNote> linkNotes = new ArrayList<>();

    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name="section_id", insertable=false, updatable=false, nullable=false)
	@Override
	public PersistentSection getSection() {
		return section;
	}
	@Override
	public void setSection(Section section) {
		this.section = (PersistentSection) section;
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
	
	@Column(name="url")
	@Override
	public String getUrl() {
		return url;
	}
	@Override
	public void setUrl(String url) {
		this.url = url;
	}

	@Column(name="note")
	@Override
	public String getNote() {
		return note;
	}
	@Override
	public void setNote(String note) {
		this.note = note;
	}

    @OneToMany(cascade={CascadeType.ALL})
    @OrderColumn(name="link_note_order")
    @JoinColumn(name="link_id", nullable=false)
    @Override
    public List<PersistentLinkNote> getLinkNotes() {
        return linkNotes;
    }
    public void setLinkNotes(List<PersistentLinkNote> linkNotes) {
        this.linkNotes = linkNotes;
        for (PersistentLinkNote linkNote : linkNotes) {
            if (linkNote.getLink() == null) {
                linkNote.setLink(this);
            }
        }
    }

    @Override
    public void addLinkNote(LinkNote linkNote) {
        PersistentLinkNote persistentLinkNote = (PersistentLinkNote) linkNote;
        linkNotes.add(persistentLinkNote);
        persistentLinkNote.setLink(this);
    }
    @Override
    public void removeLinkNote(LinkNote linkNote) {
        PersistentLinkNote persistentLinkNote = (PersistentLinkNote) linkNote;
        linkNotes.remove(persistentLinkNote);
        persistentLinkNote.setLink(null);
    }
}
