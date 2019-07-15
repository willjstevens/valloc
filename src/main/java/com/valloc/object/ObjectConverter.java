package com.valloc.object;

import com.valloc.Category;
import com.valloc.Util;
import com.valloc.log.LogManager;
import com.valloc.log.Logger;
import com.valloc.object.domain.*;
import com.valloc.object.dto.*;
import com.valloc.object.persistent.*;
import com.valloc.object.pojo.*;

/**
 * Standard implementation for account operations.
 *
 * @author wstevens
 */
public class ObjectConverter
{
	private static final Logger logger = LogManager.manager().newLogger(ObjectConverter.class, Category.UTILITY);

	public void transferUser(User source, User target) {
		target.setUsername(source.getUsername());
		target.setFirstName(source.getFirstName());
		target.setLastName(source.getLastName());
		target.setEmail(source.getEmail());
		target.setBirthdate(source.getBirthdate());
		target.setGender(source.getGender());
		target.setPassword(source.getPassword());
		target.setPasswordQuestion(source.getPasswordQuestion());
		target.setPasswordAnswer(source.getPasswordAnswer());
		target.setLocale(source.getLocale());
		target.setCookieValue(source.getCookieValue());
		target.setVerificationCode(source.getVerificationCode());
		target.setVerificationCodeIssuedTimestamp(source.getVerificationCodeIssuedTimestamp());
		target.setVerificationCodeCompletedTimestamp(source.getVerificationCodeCompletedTimestamp());
		target.setInAgreement(source.isInAgreement());
		target.setEnabled(source.isEnabled());
		target.setDeleted(source.isDeleted());
		target.setLastLoginTimestamp(source.getLastLoginTimestamp());
	}
	
	public void transferPage(Page source, Page target) {
		// Here we do not transfer the parent user object or the child column objects
		target.setName(source.getName());
		target.setPath(source.getPath());
		target.setVisibility(source.getVisibility());
		target.setHome(source.isHome());
		target.setSharedPrivately(source.isSharedPrivately());
		target.setDescription(source.getDescription());
	}
	
	public void transferColumn(Column source, Column target) {
		// NOOP: Nothing to transfer as of now. 
	}
	
	public void transferSection(Section source, Section target) {
		target.setName(source.getName());
		target.setNote(source.getNote());
	}
	
	public void transferLink(Link source, Link target) {
		target.setName(source.getName());
		target.setUrl(source.getUrl());
		target.setNote(source.getNote());
	}

    public void transferLinkNote(LinkNote source, LinkNote target) {
        target.setNote(source.getNote());
        target.setPostTimestamp(source.getPostTimestamp());
    }

    public void transferLinkNoteDtoToLinkNote(LinkNoteDto source, LinkNote target) {
        target.setNote(source.getNote());
        target.setPostTimestamp(source.getPostTimestamp());
    }

    public void transferLinkServe(LinkServe source, LinkServe target) {
        target.setOwnerUserId(source.getOwnerUserId());
        target.setOwnerPageId(source.getOwnerPageId());
        target.setRequestUrl(source.getRequestUrl());
        target.setServeTimestamp(source.getServeTimestamp());
    }

    public void transferFeedback(Feedback source, Feedback target) {
        target.setName(source.getName());
        target.setEmail(source.getEmail());
        target.setType(source.getType());
        target.setComment(source.getComment());
    }

    public void transferApplicationRequest(ApplicationRequest source, ApplicationRequest target) {
        target.setUserId(source.getUserId());
        target.setRequestRemoteHost(source.getRequestRemoteHost());
        target.setRequestRemoteAddress(source.getRequestRemoteAddress());
        target.setRequestRemotePort(source.getRequestRemotePort());
        target.setRequestMethod(source.getRequestMethod());
        target.setRequestScheme(source.getRequestScheme());
        target.setRequestContextPath(source.getRequestContextPath());
        target.setRequestUri(source.getRequestUri());
        target.setRequestQueryString(source.getRequestQueryString());
        target.setRequestUrl(source.getRequestUrl());
        target.setRequestContentLength(source.getRequestContentLength());
        target.setRequestEncoding(source.getRequestEncoding());
        target.setRequestIfNoneMatchHeader(source.getRequestIfNoneMatchHeader());
        target.setRequestUserAgentHeader(source.getRequestUserAgentHeader());
        target.setResponseStatusCode(source.getResponseStatusCode());
        target.setResponseEncoding(source.getResponseEncoding());
        target.setResponseEtagHeader(source.getResponseEtagHeader());
        target.setStartTimestamp(source.getStartTimestamp());
        target.setEndTimestamp(source.getEndTimestamp());
        target.setTotalTimeDiffMillis(source.getTotalTimeDiffMillis());
    }

    public void transferApplicationError(ApplicationError source, ApplicationError target) {
        target.setCustomMessage(source.getCustomMessage());
        target.setToString(source.getToString());
        target.setCauseMessage(source.getCauseMessage());
        target.setSimpleClassName(source.getSimpleClassName());
        target.setFullClassName(source.getFullClassName());
        target.setOccuranceTimestamp(source.getOccuranceTimestamp());
    }

	public Page toPageDtoShallow(Page source) {
		PageDto target = new PageDto();
		transferPage(source, target);
		// attempt to set username 
		User user = source.getUser();
		if (user != null) {
			target.setUsername(user.getUsername());
			if (logger.isFiner()) {
				logger.finer("Username %s was found and copied on shallow page DTO transfer.", user.getUsername());
			}
		}
		return target;
	}

    public DashboardListingPageDto toDashboardListingPageDto(Page source) {
        DashboardListingPageDto target = new DashboardListingPageDto();
        target.setGuestPage(false);
//        transferPage(source, target);
        target.setName(source.getName());
        target.setPath(source.getPath());
        target.setVisibility(source.getVisibility());
        target.setHome(source.isHome());
        target.setSharedPrivately(source.isSharedPrivately());
        target.setDescription(source.getDescription());
        // attempt to set username
        User user = source.getUser();
        if (user != null) {
            target.setUsername(user.getUsername());
            if (logger.isFiner()) {
                logger.finer("Username %s was found and copied on shallow page DTO transfer.", user.getUsername());
            }
        }
        target.setOwnerPage(true); // by default
        target.setGuestPage(false); // by default
        return target;
    }

	public Page toPersistentPage(Page source) {
		PersistentPage target = new PersistentPage();
		transferPage(source, target);
		for (Column column : source.getColumns()) {
			Column persistentColumn = toPersistentColumn(column);
			target.addColumn(persistentColumn);
		}
        // Note: no transfering of PersistentPageGuest objects since those will be loaded after a query for all
        //      guest user objects and set within the service call itself
		return target;
	}
	
	public Column toPersistentColumn(Column source) {
		PersistentColumn target = new PersistentColumn();
		transferColumn(source, target);
		for (Section section : source.getSections()) {
			Section persistentSection = toPersistentSection(section);
			target.addSection(persistentSection);
		}
		return target;
	}
	
	public Section toPersistentSection(Section source) {
		PersistentSection target = new PersistentSection();
		transferSection(source, target);
		for (Link link : source.getLinks()) {
			Link persistentLink = toPersistentLink(link);
			target.addLink(persistentLink);
		}
		return target;
	}
	
	public Link toPersistentLink(Link source) {
		PersistentLink target = new PersistentLink();
		transferLink(source, target);
        // TODO: add explanation comment
        boolean isDtoLink = source instanceof LinkDto;
        if (isDtoLink) {
            LinkDto sourceLinkDto = (LinkDto) source;
            for (LinkNoteDto linkNoteSourceDto : sourceLinkDto.getLinkNoteDtos()) {
//                PersistentLinkNote linkNoteTarget = new PersistentLinkNote();
//                transferLinkNoteDtoToLinkNote(linkNoteSourceDto, linkNoteTarget);
//                linkNoteSourceDto.setLinkNoteBackReference(linkNoteTarget);
//                target.addLinkNote(linkNoteTarget);

                PersistentLinkNote linkNoteTarget = new PersistentLinkNote();
                transferLinkNoteDtoToLinkNote(linkNoteSourceDto, linkNoteTarget);
                linkNoteSourceDto.setLinkNoteBackReference(linkNoteTarget);
                target.addLinkNote(linkNoteTarget);
            }
        } else {
//            for (LinkNote linkNote : source.getLinkNotes()) {
//                LinkNote persistentLinkNote = toPersistentLinkNote(linkNote);
//                target.addLinkNote(persistentLinkNote);
//            }
        }

//        for (LinkNote linkNote : source.getLinkNotes()) {
//            LinkNote persistentLinkNote = toPersistentLinkNote(linkNote);
//            target.addLinkNote(persistentLinkNote);
//        }


		return target;
	}

    public LinkNote toPersistentLinkNote(LinkNote source) {
        PersistentLinkNote target = new PersistentLinkNote();
        transferLinkNote(source, target);
        return target;
    }

    public LinkServe toPersistentLinkServe(LinkServe source) {
        PersistentLinkServe target = new PersistentLinkServe();
        transferLinkServe(source, target);
        return target;
    }

    public Feedback toPersistentFeedback(Feedback source) {
        PersistentFeedback target = new PersistentFeedback();
        transferFeedback(source, target);
        return target;
    }

    public ApplicationRequest toPersistentApplicationRequest(ApplicationRequest source) {

		// condition values for a persistent object
		source.setUserId(source.getUserId());
		source.setRequestRemoteHost(source.getRequestRemoteHost());
		source.setRequestRemoteAddress(source.getRequestRemoteAddress());
		source.setRequestRemotePort(source.getRequestRemotePort());
		source.setRequestMethod(source.getRequestMethod());
		source.setRequestScheme(source.getRequestScheme());
		source.setRequestContextPath(source.getRequestContextPath());
		source.setRequestUri(Util.safeTrim(source.getRequestUri(), 200));
		source.setRequestQueryString(Util.safeTrim(source.getRequestQueryString(), 1000));
		source.setRequestUrl(Util.safeTrim(source.getRequestUrl(), 2000));
		source.setRequestContentLength(source.getRequestContentLength());
		source.setRequestEncoding(source.getRequestEncoding());
		source.setRequestIfNoneMatchHeader(source.getRequestIfNoneMatchHeader());
		source.setRequestUserAgentHeader(Util.safeTrim(source.getRequestUserAgentHeader(), 200));
		source.setResponseStatusCode(source.getResponseStatusCode());
		source.setResponseEncoding(source.getResponseEncoding());
		source.setResponseEtagHeader(source.getResponseEtagHeader());
		source.setStartTimestamp(source.getStartTimestamp());
		source.setEndTimestamp(source.getEndTimestamp());
		source.setTotalTimeDiffMillis(source.getTotalTimeDiffMillis());

		// transfer over
        PersistentApplicationRequest target = new PersistentApplicationRequest();
        transferApplicationRequest(source, target);
        return target;
    }

    public ApplicationError toPersistentApplicationError(ApplicationError source) {
        PersistentApplicationError target = new PersistentApplicationError();
        transferApplicationError(source, target);
        return target;
    }

	public Page toPageDto(Page source) {
		PageDto target = new PageDto();
		transferPage(source, target);
		for (Column column : source.getColumns()) {
			Column columnDto = toColumnDto(column);
			target.addColumn(columnDto);
		}
        for (PageGuest pageGuest : source.getPageGuests()) {
            PageGuestDto pageGuestDto = toPageGuestDto(pageGuest);
            target.addPageGuest(pageGuestDto);
        }
		// attempt to set username 
		User user = source.getUser();
		if (user != null) {
			target.setUsername(user.getUsername());
			if (logger.isFiner()) {
				logger.finer("Username %s was found and copied on shallow page DTO transfer.", user.getUsername());
			}
		}
		return target;
	}
	
	public Column toColumnDto(Column source) {
		ColumnDto target = new ColumnDto();
		// note: nothing to transfer shallowly for now
		for (Section section : source.getSections()) {
			Section sectionDto = toSectionDto(section);
			target.addSection(sectionDto);
		}
		return target;
	}

	public Section toSectionDto(Section source) {
		SectionDto target = new SectionDto();
		transferSection(source, target);
		for (Link link : source.getLinks()) {
			Link linkDto = toLinkDto(link);
			target.addLink(linkDto);
		}
		return target;
	}
	
	public Link toLinkDto(Link source) {
		LinkDto target = new LinkDto();
		transferLink(source, target);
        for (LinkNote linkNote : source.getLinkNotes()) {
            LinkNoteDto linkNoteDto = toLinkNoteDto(linkNote);
            target.addLinkNoteDto(linkNoteDto);
        }
		return target;
	}

    public LinkNoteDto toLinkNoteDto(LinkNote source) {
        LinkNoteDto target = new LinkNoteDto();
        target.setNote(source.getNote());
        target.setPostTimestamp(source.getPostTimestamp());
        User user = source.getUser();
        target.setUsername(user.getUsername());
        target.setFirstName(user.getFirstName());
        target.setLastName(user.getLastName());
        return target;
    }

	public PageGuestDto toPageGuestDto(PageGuest pageGuest) {
		PageGuestDto target = new PageGuestDto();
		User guest = pageGuest.getGuestUser();
		target.setUsername(guest.getUsername());
		target.setFirstName(guest.getFirstName());
		target.setLastName(guest.getLastName());
		target.setCanModify(pageGuest.canModify());
		return target;
	}
	
	public Page toPagePojo(Page source) {
		PageDto target = new PageDto();
		transferPage(source, target);
		for (Column column : source.getColumns()) {
			Column columnPojo = toColumnPojo(column);
			target.addColumn(columnPojo);
		}
        for (PageGuest pageGuest : source.getPageGuests()) {
            PageGuest pageGuestPojo = toPageGuestPojo(pageGuest);
            target.addPageGuest(pageGuestPojo);
        }
		return target;
	}
	
	public Column toColumnPojo(Column source) {
		ColumnPojo target = new ColumnPojo();
		// note: nothing to transfer shallowly for now
		for (Section section : source.getSections()) {
			Section sectionPojo = toSectionPojo(section);
			target.addSection(sectionPojo);
		}
		return target;
	}

	public Section toSectionPojo(Section source) {
		SectionPojo target = new SectionPojo();
		transferSection(source, target);
		for (Link link : source.getLinks()) {
			Link linkPojo = toLinkPojo(link);
			target.addLink(linkPojo);
		}
		return target;
	}
	
	public Link toLinkPojo(Link source) {
		LinkPojo target = new LinkPojo();
		transferLink(source, target);
        for (LinkNote linkNote : source.getLinkNotes()) {
            LinkNote linkNotePojo = toLinkNotePojo(linkNote);
            target.addLinkNote(linkNotePojo);
        }
		return target;
	}

    public LinkNote toLinkNotePojo(LinkNote source) {
        LinkNotePojo target = new LinkNotePojo();
        transferLinkNote(source, target);
        return target;
    }

	public PageGuest toPageGuestPojo(PageGuest source) {
		PageGuestPojo target = new PageGuestPojo();
		target.setOwnerUser(source.getOwnerUser());
		target.setGuestUser(source.getGuestUser());
		target.setPage(source.getPage());
		target.setCanModify(source.canModify());
		return target;
	}
}
