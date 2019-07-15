package com.valloc.service;

import com.valloc.Category;
import com.valloc.UrlBuilder;
import com.valloc.Visibility;
import com.valloc.dao.AccountDao;
import com.valloc.dao.PageDao;
import com.valloc.framework.ServerErrorPageResultException;
import com.valloc.framework.ServiceResult;
import com.valloc.io.IoUtil;
import com.valloc.log.LogManager;
import com.valloc.log.Logger;
import com.valloc.object.ObjectConverter;
import com.valloc.object.domain.User;
import com.valloc.object.domain.UserMessage;
import com.valloc.object.dto.*;
import com.valloc.object.persistent.PersistentColumn;
import com.valloc.object.persistent.PersistentPage;
import com.valloc.object.persistent.PersistentSection;
import com.valloc.serve.PageServeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Standard implementation for account operations.
 *
 * @author wstevens
 */
@Service
public class DashboardServiceImpl extends AbstractServiceImpl implements DashboardService
{
	private static final Logger logger = LogManager.manager().newLogger(DashboardServiceImpl.class, Category.SERVICE_DASHBOARD);
	@Autowired private AccountDao accountDao;
	@Autowired private ValidationService validationService;
	@Autowired private ApplicationService applicationService;
	@Autowired private ConfigurationService configurationService;
	@Autowired private PageServeService pageServeService;
    @Autowired private UtilityService utilityService;
	@Autowired private PageDao pageDao;
	@Autowired private ObjectConverter objectConverter;
	@Autowired private IoUtil ioUtil;
	
    @Transactional
	@Override
	public ServiceResult<DashboardListingPagesDto> getAccessiblePages(User user) {
		ServiceResult<DashboardListingPagesDto> serviceResult = new ServiceResult<>();
		try {
			// fetch and load owned pages
		    List<DashboardListingPageDto> ownedPages = new ArrayList<>();
			List<PersistentPage> userPages = pageDao.findOwnedPagesForUser(user);
			for (PersistentPage persistentPage : userPages) {
                DashboardListingPageDto pageDto = objectConverter.toDashboardListingPageDto(persistentPage);
				ownedPages.add(pageDto);
			}
			// fetch and load guest pages
			List<DashboardListingPageDto> guestPages = pageDao.findGuestModifiablePages(user);
			// order all and load into DTO
			DashboardListingPagesDto dto = new DashboardListingPagesDto();
			dto.pages = orderAllPages(ownedPages, guestPages);
			// build custom information and each page full path
			UrlBuilder urlBuilder = new UrlBuilder(configurationService);
			for (DashboardListingPageDto pageDto : dto.pages) {
				// here we know it is a pageDto implementation so cast for username retrieval
				String username = pageDto.getUsername();
				String pagePath = pageDto.getPath();
                // set page edit URL
                if (pageDto.isOwnerPage()) {
				    urlBuilder.appendEditPage(pagePath);
                } else if (pageDto.isGuestPage()) {
                    urlBuilder.appendGuestEditPage(username, pagePath);
                }
                pageDto.setEditPath(urlBuilder.buildActionParts());
                urlBuilder.clear();
                //  build display path and href URLs
                urlBuilder.appendUserPage(username, pagePath);
                if (pageDto.getVisibility() == Visibility.PRIVATE) {
                    urlBuilder.setDomainPrefixSecure();
                } else {
                    // absolute for the HREF attribute, otherwise will default to secure since already in HTTPS
                    urlBuilder.makeAbsolute();
                }
                pageDto.setDisplayPath(urlBuilder.buildActionParts());
                pageDto.setHrefUrl(urlBuilder.buildUrl());
				urlBuilder.clear();
                // set listing message
                if (pageDto.isOwnerPage() && pageDto.isSharedPrivately()) {
                    UserMessage userMessage = applicationService.getUserMessage("dashboard_pageListing_sharedPage_ownerStatement");
                    pageDto.setListingMessage(userMessage.getMessage());
                } else if (pageDto.isGuestPage()) {
                    UserMessage userMessage = applicationService.getCustomizedUserMessage("dashboard_pageListing_sharedPage_guestStatement", pageDto.getOwnerFirstName(), pageDto.getOwnerLastName());
                    pageDto.setListingMessage(userMessage.getMessage());
                } else {
                    pageDto.setListingMessage(pageDto.getDescription());
                }
                // if page is home then set on DTO
                if (pageDto.isHome()) {
                    dto.homePath = pageDto.getPath();
                    pageDto.setHomeMessage(applicationService.getUserMessage("designer_pageInfo_currentHome").getMessage());
                } else {
                    pageDto.setHomeMessage(applicationService.getUserMessage("designer_pageInfo_setAsHome").getMessage());
                }
			}
			serviceResult.setObject(dto);
			serviceResult.setSuccess();
			if (logger.isFine()) {
				logger.fine("Returning %d accessible pages for user %s.", dto.pages.size(), user);
			}
		} catch (Exception e) {
			logger.error("Error occurred when retrieving all accessible pages for user: %s.", e, user);
			ServerErrorPageResultException throwMe = getServerErrorPageResultException();
			throw throwMe;
		}
		return serviceResult;
	}
	
	private List<DashboardListingPageDto> orderAllPages(List<DashboardListingPageDto> ownedPages, List<DashboardListingPageDto> guestPages) {
		List<DashboardListingPageDto> allPages = new ArrayList<>();
		Comparator<DashboardListingPageDto> pageNameComparator = new Comparator<DashboardListingPageDto>() {
            @Override public int compare(DashboardListingPageDto o1, DashboardListingPageDto o2) {
                return o1.getName().compareTo(o2.getName());
            }
        };
		Set<DashboardListingPageDto> sorter = new TreeSet<>(pageNameComparator);
		sorter.addAll(ownedPages);
		sorter.addAll(guestPages);
        allPages.addAll(sorter);
		return allPages;
	}

    @Transactional
    @Override
    public ServiceResult<List<PageDto>> getUserPagesAndSections(User user) {
        ServiceResult<List<PageDto>> serviceResult = new ServiceResult<>();
        try {
            List<PersistentPage> pages = pageDao.findAllPagesForUser(user);
            if (!pages.isEmpty()) {
                List<PageDto> pageDtos = new ArrayList<>();
                for (PersistentPage persistentPage : pages) {
                    PageDto pageDto = new PageDto();
                    pageDtos.add(pageDto);
                    pageDto.setName(persistentPage.getName());
                    pageDto.setPath(persistentPage.getPath());
                    for (PersistentColumn persistentColumn : persistentPage.getColumns()) {
                        ColumnDto columnDto = new ColumnDto();
                        pageDto.addColumn(columnDto);
                        for (PersistentSection persistentSection : persistentColumn.getSections()) {
                            SectionDto sectionDto = new SectionDto();
                            columnDto.addSection(sectionDto);
                            sectionDto.setName(persistentSection.getName());
                        }
                    }
                }
                serviceResult.setObject(pageDtos);
                serviceResult.setSuccess();
            } else {
                setUserMessage(serviceResult, "pinLink_validate_noPages");
            }
        } catch (Exception e) {
            logger.error("Error occurred when getting user pages and sections for user: %s.", e, user);
            ServerErrorPageResultException throwMe = getServerErrorPageResultException();
            throw throwMe;
        }
        return serviceResult;
    }
}