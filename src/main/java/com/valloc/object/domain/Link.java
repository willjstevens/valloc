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
public interface Link
{
	public Section getSection();
	public void setSection(Section section);
	public String getName();
	public void setName(String name);
	public String getUrl();
	public void setUrl(String url);
	public String getNote();
	public void setNote(String note);

    public void addLinkNote(LinkNote linkNote);
    public void removeLinkNote(LinkNote linkNote);
    public List<? extends LinkNote> getLinkNotes();
//    public List<LinkNote> getLinkNotes();
}
