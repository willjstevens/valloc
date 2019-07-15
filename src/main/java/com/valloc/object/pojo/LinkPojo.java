/*
 * Property of Will Stevens
 * All rights reserved.
 */
package com.valloc.object.pojo;

import com.valloc.object.domain.Link;
import com.valloc.object.domain.LinkNote;
import com.valloc.object.domain.Section;

import java.util.ArrayList;
import java.util.List;

/**
 *
 *
 * 
 * @author wstevens
 */
public class LinkPojo implements Link 
{
	private Section section;
	private String name;
	private String url;
	private String note;
	private List<LinkNote> linkNotes = new ArrayList<>();

	public Section getSection() {
		return section;
	}
	public void setSection(Section section) {
		this.section = section;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}

	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}


    public void addLinkNote(LinkNote linkNote) {
        linkNotes.add(linkNote);
        linkNote.setLink(this);
    }
    public void removeLinkNote(LinkNote linkNote) {
        linkNotes.remove(linkNote);
        linkNote.setLink(null);
    }
    public List<? extends LinkNote> getLinkNotes() {
        return linkNotes;
    }
//    public List<LinkNote> getLinkNotes() {
//    return linkNotes;
//}
}
