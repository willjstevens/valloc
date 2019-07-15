/*
 * Property of Will Stevens
 * All rights reserved.
 */
package com.valloc.object.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.valloc.object.domain.Link;
import com.valloc.object.domain.LinkNote;
import com.valloc.object.domain.Section;

import java.util.ArrayList;
import java.util.List;

//import org.codehaus.jackson.annotate.JsonIgnore;
//import org.codehaus.jackson.annotate.JsonProperty;

/**
 *
 *
 * 
 * @author wstevens
 */
public class LinkDto implements Link 
{
	private String name;
	private String url;
	private String note;
    private List<LinkNote> linkNotes = new ArrayList<>();
    private List<LinkNoteDto> linkNoteDtos = new ArrayList<>();
	
	@Override
	public Section getSection() {
//		Util.throwNpe("No bi-directional relationships supported.");
		return null;
	}

	@Override
	public void setSection(Section section) {
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
	public String getUrl() {
		return url;
	}

	@Override
	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public String getNote() {
		return note;
	}

	@Override
	public void setNote(String note) {
		this.note = note;
	}

    @JsonIgnore
    @Override
    public void addLinkNote(LinkNote linkNote) {}

    @JsonIgnore
    @Override
    public void removeLinkNote(LinkNote linkNote) {}

    @JsonIgnore
    @Override
//    public List<? extends LinkNote> getLinkNotes() {
    public List<LinkNote> getLinkNotes() {
        return linkNotes;
    }

    public void addLinkNoteDto(LinkNoteDto linkNote) {
        linkNoteDtos.add(linkNote);
    }

    @JsonProperty("linkNotes")
    public List<LinkNoteDto> getLinkNoteDtos() {
        return linkNoteDtos;
    }

    @JsonProperty("linkNotes")
    public void setLinkNoteDtos(List<LinkNoteDto> linkNoteDtos) {
        this.linkNoteDtos = linkNoteDtos;
    }
}
