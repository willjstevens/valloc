/*
 * Property of Will Stevens
 * All rights reserved.
 */
package com.valloc.object.persistent;

import com.valloc.object.domain.Column;
import com.valloc.object.domain.Page;
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
@Table(name="column", schema="public")
@SequenceGenerator(
	    name="id_seq",
	    allocationSize=1,
	    sequenceName="column_id_seq"
	)
public class PersistentColumn extends UpdatableDomainObject implements Column
{
	private PersistentPage page;
	private List<PersistentSection> sections = new ArrayList<>();
    
    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name="page_id", insertable=false, updatable=false, nullable=false)
	@Override
	public PersistentPage getPage() {
		return page;
	}
	@Override
	public void setPage(Page page) {
		this.page = (PersistentPage) page;
	}

    @OneToMany(cascade={CascadeType.ALL})
    @OrderColumn(name="section_order")
    @JoinColumn(name="column_id", nullable=false)
	@Override
	public List<PersistentSection> getSections() {
		return sections;
	}
	public void setSections(List<PersistentSection> sections) {
		this.sections = sections;
		for (PersistentSection section : sections) {
			if (section.getColumn() == null) {
				section.setColumn(this);
			}
		}
	}
	@Override
	public void addSection(Section section) {
		PersistentSection persistentSection = (PersistentSection) section;
		sections.add(persistentSection);
		persistentSection.setColumn(this);
	}
	@Override
	public void removeSection(Section section) {
		PersistentSection persistentSection = (PersistentSection) section;
		sections.remove(persistentSection);
		persistentSection.setColumn(null);
	}

}
