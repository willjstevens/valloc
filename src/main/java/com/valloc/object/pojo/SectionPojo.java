/*
 * Property of Will Stevens
 * All rights reserved.
 */
package com.valloc.object.pojo;

import com.valloc.object.domain.Column;
import com.valloc.object.domain.Link;
import com.valloc.object.domain.Section;

import java.util.ArrayList;
import java.util.List;

/**
 *
 *
 * 
 * @author wstevens
 */
public class SectionPojo implements Section 
{
	private Column column;
	private String name;
	private String note;
	private List<Link> links = new ArrayList<>();
	
	public Column getColumn() {
		return column;
	}
	public void setColumn(Column column) {
		this.column = column;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}

	public void addLink(Link link) {
		links.add(link);
		link.setSection(this);
	}
	public void removeLink(Link link) {
		links.remove(link);
		link.setSection(null);
	}
	public List<? extends Link> getLinks() {
		return links;
	}

}
