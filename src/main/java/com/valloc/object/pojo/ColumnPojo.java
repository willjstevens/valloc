/*
 * Property of Will Stevens
 * All rights reserved.
 */
package com.valloc.object.pojo;

import com.valloc.object.domain.Column;
import com.valloc.object.domain.Page;
import com.valloc.object.domain.Section;

import java.util.ArrayList;
import java.util.List;

/**
 *
 *
 * 
 * @author wstevens
 */
public class ColumnPojo implements Column 
{
	private Page page;
	private List<Section> sections = new ArrayList<>();
	
	public Page getPage() {
		return page;
	}
	public void setPage(Page page) {
		this.page = page;
	}

	public void addSection(Section section) {
		sections.add(section);
		section.setColumn(this);
	}
	public void removeSection(Section section) {
		sections.remove(section);
		section.setColumn(null);
	}
	public List<? extends Section> getSections() {
		return sections;
	}
	
}
