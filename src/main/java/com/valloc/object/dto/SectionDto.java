/*
 * Property of Will Stevens
 * All rights reserved.
 */
package com.valloc.object.dto;

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
public class SectionDto implements Section 
{
	private String name;
	private String note;
//	private List<Link> links = new ArrayList<>();
    private List<LinkDto> links = new ArrayList<>();
	
	@Override
	public Column getColumn() {
//		Util.throwNpe("No bi-directional relationships supported.");
		return null;
	}

	@Override
	public void setColumn(Column column) {
//		Util.throwNpe("No bi-directional relationships supported.");
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getNote() {
		return note;
	}

	@Override
	public void setNote(String note) {
		this.note = note;
	}

	@Override
	public void addLink(Link link) {
//		links.add(link);
        links.add((LinkDto) link);
	}

	@Override
	public void removeLink(Link link) {
		links.remove(link);
	}

	@Override
	public List<? extends Link> getLinks() {
		return links;
	}

}
