/*
 * Property of Will Stevens
 * All rights reserved.
 */
package com.valloc.object.domain;

import java.util.List;


/**
 *
 *
 * 
 * @author wstevens
 */
public interface Column
{
	public Page getPage();
	public void setPage(Page page);
	public void addSection(Section section);
	public void removeSection(Section section);
	public List<? extends Section> getSections();
}
