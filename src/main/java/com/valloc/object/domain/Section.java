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
public interface Section
{
	public Column getColumn();
	public void setColumn(Column column);
	public String getName();
	public void setName(String name);
	public String getNote();
	public void setNote(String note);
	public void addLink(Link link);
	public void removeLink(Link link);
	public List<? extends Link> getLinks();
}
