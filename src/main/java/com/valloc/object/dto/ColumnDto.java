/*
 * Property of Will Stevens
 * All rights reserved.
 */
package com.valloc.object.dto;

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
public class ColumnDto implements Column 
{
//	private List<Section> sections = new ArrayList<>();
    private List<SectionDto> sections = new ArrayList<>();

	public Page getPage() {
//		Util.throwNpe("No bi-directional relationships supported.");
		return null;
	}
	@Override
	public void setPage(Page page) {
//		Util.throwNpe("No bi-directional relationships supported.");
	}

	@Override
	public void addSection(Section section) {
//		sections.add(section);
        sections.add((SectionDto) section);
	}

	@Override
	public void removeSection(Section section) {
		sections.remove(section);
	}

	@Override
	public List<? extends Section> getSections() {
		return sections;
	}
}
