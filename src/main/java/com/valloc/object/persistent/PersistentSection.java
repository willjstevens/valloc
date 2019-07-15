/*
 * Property of Will Stevens
 * All rights reserved.
 */
package com.valloc.object.persistent;

import com.valloc.object.domain.Link;
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
@Table(name="section")
@SequenceGenerator(
	    name="id_seq",
	    allocationSize=1,
	    sequenceName="section_id_seq"
	)
public class PersistentSection extends UpdatableDomainObject implements Section
{
	private PersistentColumn column;
	private String name;
	private String note;
	private List<PersistentLink> links = new ArrayList<>();

    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name="column_id", insertable=false, updatable=false, nullable=false)
	@Override
	public PersistentColumn getColumn() {
		return column;
	}
	@Override
	public void setColumn(com.valloc.object.domain.Column column) {
		this.column = (PersistentColumn) column;
	}

	@Column(name="name", nullable=false)
	@Override
	public String getName() {
		return name;
	}
	@Override
	public void setName(String title) {
		this.name = title;
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
    @OrderColumn(name="link_order")
    @JoinColumn(name="section_id", nullable=false)
	@Override
	public List<PersistentLink> getLinks() {
		return links;
	}
	public void setLinks(List<PersistentLink> links) {
		this.links = links;
		for (PersistentLink link : links) {
			if (link.getSection() == null) {
				link.setSection(this);
			}
		}
	}

	@Override
	public void addLink(Link link) {
		PersistentLink persistentLink = (PersistentLink) link;
		links.add(persistentLink);
		persistentLink.setSection(this);
	}
	@Override
	public void removeLink(Link link) {
		PersistentLink persistentLink = (PersistentLink) link;
		links.remove(persistentLink);
		persistentLink.setSection(null);
	}
}
