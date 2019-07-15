/*
 * Property of Will Stevens
 * All rights reserved.
 */
package com.valloc.service;

import com.valloc.*;
import com.valloc.dao.AccountDao;
import com.valloc.dao.PageDao;
import com.valloc.framework.ServerErrorAjaxResultException;
import com.valloc.framework.ServerErrorPageResultException;
import com.valloc.framework.ServiceResult;
import com.valloc.io.IoUtil;
import com.valloc.io.JspWriterAdapter;
import com.valloc.log.LogManager;
import com.valloc.log.Logger;
import com.valloc.object.ObjectConverter;
import com.valloc.object.domain.*;
import com.valloc.object.dto.*;
import com.valloc.object.persistent.*;
import com.valloc.serve.PageServeService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.util.*;

import static com.valloc.Constants.CHARSET_UTF_8_STR;
import static com.valloc.Constants.VALLOC_COM;
import static com.valloc.UrlConstants.HTTP_SCHEME;
import static com.valloc.UrlConstants.SCHEME_SEPARATOR;
import static com.valloc.Visibility.PUBLIC;

/**
 *
 *
 * @author wstevens
 */
@Service
public class PageServiceImpl extends AbstractServiceImpl implements PageService
{
	private static final Logger logger = LogManager.manager().newLogger(PageServiceImpl.class, Category.SERVICE_DASHBOARD);
	@Autowired private AccountDao accountDao;
	@Autowired private ValidationService validationService;
	@Autowired private ApplicationService applicationService;
	@Autowired private ConfigurationService configurationService;
	@Autowired private PageServeService pageServeService;
    @Autowired private UtilityService utilityService;
	@Autowired private PageDao pageDao;
	@Autowired private ObjectConverter objectConverter;
	@Autowired private IoUtil ioUtil;

    @Override
    public ServiceResult<PageDesignerDto> loadPageNewPage() {
        ServiceResult<PageDesignerDto> serviceResult = new ServiceResult<>();
        try {
            PageDesignerDto pageDesignerDto = new PageDesignerDto();
            PageDto pageDto = new PageDto();
            pageDesignerDto.setPageDto(pageDto);
            // Set defaults on a new page object
            pageDesignerDto.setIsEdit(false);
            pageDto.setVisibility(Visibility.PUBLIC);
            for (int i = 0; i < SpecConstants.COLUMN_COUNT_VALUE; i++) {
                pageDto.addColumn(new ColumnDto());
            }
            serviceResult.setObject(pageDesignerDto);
            serviceResult.setSuccess();
        } catch (Exception e) {
            logger.error("Error occurred when creating new page.", e);
            ServerErrorPageResultException throwMe = getServerErrorPageResultException();
            throw throwMe;
        }
        return serviceResult;
    }

    @Transactional
    @Override
    public ServiceResult<PageGuestDto> searchForPageGuest(PageGuestDto pageGuestDto) {
        ServiceResult<PageGuestDto> serviceResult = new ServiceResult<>();
        try {
            String username = pageGuestDto.getUsername();
            if (username != null) { // as preferable we have username so first attempt by that
                username = username.toLowerCase();
                User user = accountDao.findUserByUsername(username);
                if (user != null) { // exact match found
                	if (logger.isFiner()) {
						logger.finer("Found user object for page guest username %s.", username);
					}
                    PageGuestDto dto = new PageGuestDto(user.getUsername(), user.getFirstName(), user.getLastName());
                    serviceResult.setObject(dto);
                    UserMessage userMessage = applicationService.getCustomizedUserMessage("designer_pageInfo_pageGuests_search_userFound", user.getFirstName(), user.getLastName());
                    serviceResult.setUserMessage(userMessage);
                    serviceResult.setObject(dto);
                    serviceResult.setSuccess();
                } else { // if no user from username then try with first/last name
                    searchByFirstAndLastName(serviceResult, pageGuestDto);
                }
            }

            if (!serviceResult.isSuccess()) {
                UserMessage userMessage = applicationService.getUserMessage("designer_pageInfo_pageGuests_search_validate_noUsersFound");
                serviceResult.setUserMessage(userMessage);
            }
            // Later add-in first/last name search below
//            if (!serviceResult.isSuccess()) { // if no luck so try with first/last name
//                searchByFirstAndLastName(serviceResult, pageGuestDto);
//            }
        } catch (Exception e) {
            logger.error("Error occurred when retrieve page guests for user.", e);
            ServerErrorAjaxResultException throwMe = getServerErrorAjaxResultException();
            throw throwMe;
        }
        return serviceResult;
    }

    @Transactional
    @Override
    public ServiceResult<PageDesignerDto> loadPageEditPage(User user, String path) {
        ServiceResult<PageDesignerDto> serviceResult = new ServiceResult<>();
        try {
            Page page = pageDao.findPage(user.getUsername(), path);
            if (page != null) {
                PageDto pageDto = (PageDto) objectConverter.toPageDto(page);
                PageDesignerDto pageDesignerDto = new PageDesignerDto();
                pageDesignerDto.setPageDto(pageDto);
                pageDesignerDto.setOriginalPath(path);
                pageDesignerDto.setIsEdit(true);
                serviceResult.setObject(pageDesignerDto);
                serviceResult.setSuccess();
            } else {
                UserMessage userMessage = applicationService.getUserMessage("designer_pageInfo_pageNotFound");
                serviceResult.addSupportingUserMessage(userMessage);
            }
        } catch (Exception e) {
            logger.error("Error occurred when loading page edit data for user %s for page path %s.", e, user.getUsername(), path);
            ServerErrorPageResultException throwMe = getServerErrorPageResultException();
            throw throwMe;
        }
        return serviceResult;
    }

    @Transactional
    @Override
    public ServiceResult<PageDesignerDto> loadPageGuestEditPage(User guestUser, String ownerUsername, String path) {
        ServiceResult<PageDesignerDto> serviceResult = new ServiceResult<>();
        try {
            Page page = pageDao.findGuestModifiablePage(guestUser, ownerUsername, path);
            if (page != null) {
                PageDto pageDto = (PageDto) objectConverter.toPageDto(page);
                pageDto.clearPageGuestDtos(); // clear now they do not even go out to the client
                PageDesignerDto pageDesignerDto = new PageDesignerDto();
                pageDesignerDto.setPageDto(pageDto);
                pageDesignerDto.setOriginalPath(path); // probably not necessary but for availability
                pageDesignerDto.setGuestEdit(true);
                pageDesignerDto.setIsEdit(true);
                serviceResult.setObject(pageDesignerDto);
                serviceResult.setSuccess();
            } else {
                UserMessage userMessage = applicationService.getUserMessage("designer_pageInfo_pageGuestEdit_pageNotFoundOrIllegalAccess");
                serviceResult.addSupportingUserMessage(userMessage);
            }
        } catch (Exception e) {
            logger.error("Error occurred when loading page guest edit data for guest %s for owner %s and page path %s.", e, guestUser.getUsername(), ownerUsername, path);
            ServerErrorPageResultException throwMe = getServerErrorPageResultException();
            throw throwMe;
        }
        return serviceResult;
    }

    @Transactional
    @Override
    public ServiceResult<PageDesignerDto> savePageGuestEdit(PageDto pageDto, User guestUser, JspWriterAdapter jspWriterAdapter) {
        ServiceResult<PageDesignerDto> serviceResult = new ServiceResult<>();
        try {
            String ownerUsername = pageDto.getUsername();
            String path = pageDto.getPath();
            Page persistentPage = pageDao.findGuestModifiablePage(guestUser, ownerUsername, path);
            if (persistentPage == null) {
                addSupportingUserMessage(serviceResult, "designer_pageInfo_pageGuestEdit_noAccess");
            }
            // if no business errors then proceed
            boolean successfulValidation = !serviceResult.hasSupportingUserMessages();
            if (successfulValidation) {

                /*
                 * Retrieve user objects for link note history objects.  First iterate through to get username on
                 *      each LinkNote object, then clear existing persistent column objects and transfer over new
                 *      persistent section, link and link note objects.  Replace user objects on link notes in place
                 *      of usernames
                 */
                List<PersistentUser> persistentUsers = loadPersistentLinkNotes(pageDto, guestUser);
                Map<String, PersistentUser> userLookup = loadPersistentUserLookup(persistentUsers);

                // set all sections into columns
                List<? extends Column> existingColumns = persistentPage.getColumns();
                List<? extends Column> columnDtos = pageDto.getColumns();
                for (int i = 0; i < existingColumns.size(); i++) {
                    Column existingColumn = existingColumns.get(i);
                    // clear all existing sections from existing columns and rebuild
                    Iterator<? extends Section> existingSections = existingColumn.getSections().iterator();
                    while (existingSections.hasNext()) {
                        Section persistentSection = existingSections.next();
                        existingSections.remove();
                        existingColumn.removeSection(persistentSection);
                        pageDao.delete(persistentSection);
                    }
                    // add in new persistent sections and downwards
                    Column columnDto = columnDtos.get(i);
                    for (Section sectionDto : columnDto.getSections()) {
                        PersistentSection persistentSection = (PersistentSection) objectConverter.toPersistentSection(sectionDto);
                        existingColumn.addSection(persistentSection);
                        List<? extends Link> persistentLinks = persistentSection.getLinks();
                        List<? extends Link> linkDtos = sectionDto.getLinks();
                        for (int k = 0; k < persistentLinks.size(); k++) {
                            Link persistentLink = persistentLinks.get(k);
                            Link dtoLink = linkDtos.get(k);
                            List<? extends LinkNote> persistentLinkNotes = persistentLink.getLinkNotes();
                            List<? extends LinkNoteDto> dtoLinkNotes = ((LinkDto)dtoLink).getLinkNoteDtos();
                            for (int j = 0; j < persistentLinkNotes.size(); j++) {
                                LinkNote persistentLinkNote = persistentLinkNotes.get(j);
                                LinkNoteDto dtoLinkNote = dtoLinkNotes.get(j);
                                User noteUser = userLookup.get(dtoLinkNote.getUsername());
                                if (noteUser == null) {
                                    noteUser = guestUser; // if blank then this is a new note
                                }
                                persistentLinkNote.setUser(noteUser);
                                if (dtoLinkNote.getPostTimestamp() == null) {
                                    persistentLinkNote.setPostTimestamp(Util.now());
                                } else {
                                    persistentLinkNote.setPostTimestamp(dtoLinkNote.getPostTimestamp());
                                }
                            }
                        }
                    }
                }

                // condition and save persistent object
                conditionalizePage(persistentPage);
                pageDao.saveOrUpdate(persistentPage);

                // cleanup existing resources
                pageServeService.removePageCache(persistentPage);

                // write file out to disk and retrieve contents
                jspWriterAdapter.setJspAttribute("page", persistentPage);
                jspWriterAdapter.setJspAttribute("user", persistentPage.getUser());
                jspWriterAdapter.setJspAttribute("utilityService", utilityService);
                jspWriterAdapter.write();
                byte[] contentBytes = jspWriterAdapter.getContentsBytes();
                // reset page load cache
                pageServeService.addPageCache(persistentPage, contentBytes);

                // create a fresh Designer DTO and load with data
                PageDesignerDto newPageDesignerDto = new PageDesignerDto();
                newPageDesignerDto.setGuestEdit(true);
                newPageDesignerDto.setIsEdit(true);
                newPageDesignerDto.setPageDto((PageDto) objectConverter.toPageDto(persistentPage));
                newPageDesignerDto.setOriginalPath(persistentPage.getPath());
                // set final objects on service result
                serviceResult.setObject(newPageDesignerDto);
                loadSaveSuccessAttributes(serviceResult, ownerUsername, persistentPage);
                loadSaveSuccessMessage(serviceResult, ownerUsername, persistentPage);
                serviceResult.setSuccess();
                if (logger.isFine()) {
                    logger.fine("Successfully did Guest edit for page \"%s\" under path /%s for username %s.", persistentPage.getName(), persistentPage.getPath(), guestUser.getUsername());
                }
            }
        } catch (Exception e) {
            logger.error("Error occurred when Guest editing page under guest user %s for page %s under owner username %s.", e, guestUser, pageDto.getPath(), pageDto.getUsername());
            ServerErrorAjaxResultException throwMe = getServerErrorAjaxResultException();
            ioUtil.deletePageFile(pageDto.getUsername(), pageDto.getPath());
            throw throwMe;
        }
        return serviceResult;
    }

    private Map<String, PersistentUser> loadPersistentUserLookup(List<PersistentUser> persistentUsers) {
        Map<String, PersistentUser> userLookup = new HashMap<>();
        for (PersistentUser persistentUser : persistentUsers) {
            userLookup.put(persistentUser.getUsername(), persistentUser);
        }
        return userLookup;
    }

    private void searchByFirstAndLastName(ServiceResult<PageGuestDto> serviceResult, PageGuestDto pageGuestDto) {
        String firstName = pageGuestDto.getFirstName();
        String lastName = pageGuestDto.getLastName();
        if (firstName != null && lastName != null) {
        	if (logger.isFiner()) {
				logger.finer("Searching for user by first name \"%s\" and last name \"%s\".", firstName, lastName);
			}
            List<PersistentUser> candidateUsers = accountDao.findUsersByFirstAndLastName(firstName, lastName);
            if (logger.isFiner()) {
				logger.finer("Found %d candidate records for %s %s.", candidateUsers.size(), firstName, lastName);
			}
            if (candidateUsers.size() == 1) { // exact match found
                User user = candidateUsers.get(0);
                PageGuestDto dto = new PageGuestDto(user.getUsername(), user.getFirstName(), user.getLastName());
                serviceResult.setObject(dto);
                UserMessage userMessage = applicationService.getUserMessage("designer_pageInfo_pageGuests_search_userFound");
                serviceResult.addSupportingUserMessage(userMessage);
                serviceResult.setSuccess();
            } else if (candidateUsers.size() > 1) { // non-unique results
                UserMessage userMessage = applicationService.getUserMessage("designer_pageInfo_pageGuests_search_validate_nonUniqueUsersFound");
                serviceResult.addSupportingUserMessage(userMessage);
            } else if (candidateUsers.isEmpty()) { // no users found
                UserMessage userMessage = applicationService.getUserMessage("designer_pageInfo_pageGuests_search_validate_noUsersFound");
                serviceResult.addSupportingUserMessage(userMessage);
            }
        } else {
        	if (logger.isFiner()) {
				logger.finer("Either one or both of first name (%s) and last name (%s) were found null.", firstName, lastName);
			}
            UserMessage userMessage = applicationService.getUserMessage("designer_pageInfo_pageGuests_search_validate_firstOrLastIsNull");
            serviceResult.addSupportingUserMessage(userMessage);
        }
    }

    @Transactional
    @Override
    public ServiceResult<PageDesignerDto> newPage(PageDesignerDto pageDesignerDto, User user, JspWriterAdapter jspWriterAdapter) {
        ServiceResult<PageDesignerDto> serviceResult = new ServiceResult<>();
        PageDto pageDto = pageDesignerDto.getPageDto();
        try {
            validateEditableFields(serviceResult, pageDto, user); // for both creation and editable
            // if no business errors then proceed
            boolean successfulValidation = !serviceResult.hasSupportingUserMessages();
            if (successfulValidation) {
                // convert DTO to persistent object and stage for use
                PersistentPage persistentPage = (PersistentPage) objectConverter.toPersistentPage(pageDto);
                persistentPage.setUser(user);
                persistentPage.setSharedPrivately(!pageDto.getPageGuestDtos().isEmpty());
                conditionalizePage(persistentPage);
                // load necessary userPage object and do cross-page coordination for setting home page
                UserPages userPages = new UserPages(user);
                if (userPages.hasNoPages()) {
                    // since no existing page implicitly set this first page as home
                	persistentPage.setHome(true);
                }
                // if the user set this page to home and there are already pages, then evict existing home
                else if (persistentPage.isHome() && userPages.hasPages()) {
                    evictExistingHomePage(userPages.getExistingHome()); // if new page is home, evict the old one
                }
                // get all user objects for guests and load users
                loadPersistentPageGuestsAndLinkNotes(pageDto, persistentPage, user);
                pageDao.saveOrUpdate(persistentPage);
                // write file out to disk and retrieve contents
                jspWriterAdapter.setJspAttribute("page", persistentPage);
                jspWriterAdapter.setJspAttribute("user", user);
                jspWriterAdapter.setJspAttribute("utilityService", utilityService);
                jspWriterAdapter.write();
                byte[] contentBytes = jspWriterAdapter.getContentsBytes();
                // add to page load cache 
                pageServeService.addPageCache(persistentPage, contentBytes);
                // load success messages and data
                loadSaveSuccessAttributes(serviceResult, user.getUsername(), persistentPage);
                loadSaveSuccessMessage(serviceResult, user.getUsername(), persistentPage);

                // Create a fresh Designer DTO and load with data
                PageDesignerDto newPageDesignerDto = new PageDesignerDto();
                newPageDesignerDto.setIsEdit(true); // now in edit mode
                newPageDesignerDto.setPageDto((PageDto) objectConverter.toPageDto(persistentPage));
                newPageDesignerDto.setOriginalPath(persistentPage.getPath());
                // set final objects on service result
                serviceResult.setObject(newPageDesignerDto);
                loadSaveSuccessAttributes(serviceResult, user.getUsername(), persistentPage);
                loadSaveSuccessMessage(serviceResult, user.getUsername(), persistentPage);

                serviceResult.setSuccess();
                if (logger.isFine()) {
					logger.fine("Successfully added page \"%s\" under path /%s for username %s.", persistentPage.getName(), persistentPage.getPath(), user.getUsername());
				}
            }
        } catch (Exception e) {
            logger.error("Error occurred when creating page for user: %s.", e, user);
            ServerErrorAjaxResultException throwMe = getServerErrorAjaxResultException();
            jspWriterAdapter.close();
            ioUtil.deletePageFile(user.getUsername(), pageDto.getPath());
            throw throwMe;
        }
        return serviceResult;
    }

    @Transactional
	@Override
	public ServiceResult<PageDesignerDto> editPage(PageDesignerDto pageDesignerDto, User user, JspWriterAdapter jspWriterAdapter) {
        ServiceResult<PageDesignerDto> serviceResult = new ServiceResult<>();
        PageDto pageDto = pageDesignerDto.getPageDto();
        String originalPath = pageDesignerDto.getOriginalPath();
        try {
			PersistentPage originalPage = pageDao.findPageByPath(user, originalPath);
            UserPages userPages = new UserPages(user);
            String newPath = pageDto.getPath();
            // check if user a different path than the existing one; if so validate it does not collide with other existing pages
        	boolean pathHasChanged = !newPath.equals(originalPath);
        	if (pathHasChanged) {
        		if (logger.isFiner()) {
					logger.finer("In page edit, user (%s) changed path from /%s to /%s.", user.getUsername(), originalPath, newPath);
				}
        		for (PersistentPage existingPage : userPages.allUserPages) {
        			if (newPath.equals(existingPage.getPath())) {
                        UserMessage userMessage = applicationService.getCustomizedUserMessage("designer_pageInfo_validate_pathAlreadyExists", newPath);
                        serviceResult.addSupportingUserMessage(userMessage);
        				break;
        			}
        		}
        	}
            // if no business errors then proceed
            boolean successfulValidation = !serviceResult.hasSupportingUserMessages();
            if (successfulValidation) {
                // convert DTO to persistent object and stage for use
                PersistentPage newPersistentPage = (PersistentPage) objectConverter.toPersistentPage(pageDto);
                newPersistentPage.setUser(user);
                newPersistentPage.setSharedPrivately(!pageDto.getPageGuestDtos().isEmpty());
                // set/reset user home on classes
                if (userPages.hasOnePage()) {
					if (!newPersistentPage.isHome()) {
						if (logger.isFiner()) {
							// user might have unchecked box to not be home, on only page available
							logger.finer("Setting username %s page %s to be home since unselected.", user.getUsername(), newPersistentPage.getPath());
						}
						newPersistentPage.setHome(true);
					}
				} else if (userPages.hasMultiplePages()) {
					if (newPersistentPage.isHome()) {
						// if new page is home, evict the old one
						evictExistingHomePage(userPages.getExistingHome());
					} else if (!newPersistentPage.isHome() && originalPage.isHome()) {
						// if user deselected the page as home, then re-elect a new home page
						for (Page existingPage : userPages.allUserPages) {
							if (!existingPage.equals(originalPage)) { // take first non-original; could hit already hibernate-deleted object
								if (logger.isFiner()) {
									logger.finer("User (%s) un-selected home on the current page /%s so page /%s has been arbitrarily set as home page.", user.getUsername(), originalPage.getPath(), existingPage.getPath());
								}
								existingPage.setHome(true);
								pageDao.saveOrUpdate(existingPage);
								pageServeService.setPageAsHome(existingPage);
								break;
							}
						}
					}
				}

                // get all user objects for guests and load users
                loadPersistentPageGuestsAndLinkNotes(pageDto, newPersistentPage, user);

                // clean-up previous page version resources
                if (pathHasChanged) {
                	ioUtil.deletePageFile(user.getUsername(), originalPath);
                }
                pageDao.delete(originalPage);
                pageServeService.removePageCache(originalPage);

                // condition and add new resources
                conditionalizePage(newPersistentPage);
                pageDao.saveOrUpdate(newPersistentPage);

                // write file out to disk and retrieve contents
                jspWriterAdapter.setJspAttribute("page", newPersistentPage);
                jspWriterAdapter.setJspAttribute("user", user);
                jspWriterAdapter.setJspAttribute("utilityService", utilityService);
                jspWriterAdapter.write();
                byte[] contentBytes = jspWriterAdapter.getContentsBytes();
                // reset page load cache
                pageServeService.addPageCache(newPersistentPage, contentBytes);

                // Create a fresh Designer DTO and load with data
                PageDesignerDto newPageDesignerDto = new PageDesignerDto();
                newPageDesignerDto.setIsEdit(true);
                newPageDesignerDto.setPageDto((PageDto) objectConverter.toPageDto(newPersistentPage));
                newPageDesignerDto.setOriginalPath(newPath);
                // set final objects on service result
                serviceResult.setObject(newPageDesignerDto);
                loadSaveSuccessAttributes(serviceResult, user.getUsername(), newPersistentPage);
                loadSaveSuccessMessage(serviceResult, user.getUsername(), newPersistentPage);
                serviceResult.setSuccess();
                if (logger.isFine()) {
					logger.fine("Successfully edited page \"%s\" under path /%s for username %s.", newPersistentPage.getName(), newPersistentPage.getPath(), user.getUsername());
				}
            }
        } catch (Exception e) {
            logger.error("Error occurred when editing page for user: %s.", e, user);
            ServerErrorAjaxResultException throwMe = getServerErrorAjaxResultException();
            jspWriterAdapter.close();
            ioUtil.deletePageFile(user.getUsername(), pageDto.getPath());
            throw throwMe;
        }
        return serviceResult;
	}

	private void loadSaveSuccessMessage(ServiceResult<?> serviceResult, String username, Page page) {
		UserMessage userMessage = applicationService.getCustomizedUserMessage("designer_pageInfo_messages_saveSuccess", page.getName());
        serviceResult.setRelayMessage(userMessage);
	}

    private void loadSaveSuccessAttributes(ServiceResult<?> serviceResult, String username, Page page) {
        UrlBuilder pageUrlBuilder = new UrlBuilder(configurationService);
        pageUrlBuilder.appendUserPage(username, page.getPath());
        if (page.getVisibility() == Visibility.PRIVATE) {
            pageUrlBuilder.setDomainPrefixSecure();
        } else {
            // absolute for the HREF attribute, otherwise will default to secure since already in HTTPS
            pageUrlBuilder.makeAbsolute();
        }
        serviceResult.addAttribute("displayUrl", pageUrlBuilder.buildActionParts());
        serviceResult.addAttribute("hrefUrl", pageUrlBuilder.buildUrl());
    }

    @Transactional
	@Override
	public ServiceResult<?> removePage(User user, String path) {
		ServiceResult<?> serviceResult = new ServiceResult<>();
        try {
			Page page = pageDao.findPageByPath(user, path);
			UserPages userPages = new UserPages(user);
			// do clean up operations like remove from database, page load cache and cached file on disk
			pageDao.delete(page);
			ioUtil.deletePageFile(user.getUsername(), path);
			pageServeService.removePageCache(page);
			if (page.isHome()) { // if this page was previously home, then reelect a home page
				for (Page existingPage : userPages.allUserPages) {
					if (!page.equals(existingPage)) { // take first non-original; could hit already hibernate-deleted object
						existingPage.setHome(true);
						pageDao.saveOrUpdate(existingPage);
						// reset page load cache
						pageServeService.setPageAsHome(existingPage);
						break;
					}
				}
			}
			serviceResult.setSuccess();
			UserMessage userMessage = applicationService.getCustomizedUserMessage("designer_pageInfo_messages_removeSuccess", page.getName());
			serviceResult.setUserMessage(userMessage);			
            if (logger.isFine()) {
				logger.fine("Successfully removed page \"%s\" under path /%s for username %s.", page.getName(), page.getPath(), user.getUsername());
			}
        } catch (Exception e) {
            logger.error("Error occurred when removing a page /%s for user: %s.", e, path, user);
            ServerErrorAjaxResultException throwMe = getServerErrorAjaxResultException();
            ioUtil.deletePageFile(user.getUsername(), path);
            throw throwMe;
        }
        return serviceResult;
	}

    @Transactional
    @Override
    public ServiceResult<PageDesignerDto> importPage(User user, InputStream inputStream) {
        ServiceResult<PageDesignerDto> serviceResult = new ServiceResult<>();
        try {
            // stage base page
            Page importedPage = new PersistentPage();
            importedPage.setUser(user);
            importedPage.setName("Quick Start Home Page");
            importedPage.setPath("quickstart");
            importedPage.setVisibility(PUBLIC);
            conditionalizePage(importedPage);
            UserPages userPages = new UserPages(user);
            if (userPages.hasNoPages()) {
                // since no existing page implicitly set this first page as home
                importedPage.setHome(true);
            }
            // check for existing page path and reset if necessary
            int i = 1;
            String path = importedPage.getPath();
            while (userPages.hasExistingPageByPath(path)) {
                path += i;
                importedPage.setPath(path);
                importedPage.setName(importedPage.getName() + " " + i);
            }
            if (importedPage.getName().length() > SpecConstants.PAGE_NAME_MAX_SIZE_VALUE) {
                String badName = importedPage.getName();
                importedPage.setName(badName.substring(badName.length() - SpecConstants.PAGE_NAME_MAX_SIZE_VALUE));
            }
            // parse import file into a flat list of sections
            List<Section> importedSections = new ArrayList<>();
            Document importFileDoc = Jsoup.parse(inputStream, CHARSET_UTF_8_STR, HTTP_SCHEME + SCHEME_SEPARATOR + VALLOC_COM);
            Elements sectionEls = importFileDoc.select("dt h3");
            for (Element sectionEl : sectionEls) {
                Element dtParent = sectionEl.parent();
                parseImportFileBookmarkItem(dtParent, importedSections, null);
            }
            // distribute into 3 (or registered) number of page columns
            final int sectionsPerColumn = (int) Math.ceil((double) importedSections.size() / (double) SpecConstants.COLUMN_COUNT_VALUE);
            int sectionIndex = 0, importedSectionSize = importedSections.size();
            for (i = 0; i < SpecConstants.COLUMN_COUNT_VALUE; i++) {
                Column column = new PersistentColumn();
                importedPage.addColumn(column);
                for (int k = 0; k < sectionsPerColumn && sectionIndex < importedSectionSize; k++, sectionIndex++) {
                    column.addSection(importedSections.get(sectionIndex));
                }
            }
            // encode then save in the data in database, no file generation or cache storing
            pageDao.saveOrUpdate(importedPage);

            // stage for redirect to /page/edit with a message
            PageDesignerDto newPageDesignerDto = new PageDesignerDto();
            newPageDesignerDto.setIsEdit(true);
            newPageDesignerDto.setPageDto((PageDto) objectConverter.toPageDto(importedPage));
            newPageDesignerDto.setOriginalPath(importedPage.getPath()); // reuse the generated import
            // set final objects on service result
            serviceResult.setObject(newPageDesignerDto);
            UserMessage userMessage = applicationService.getUserMessage("import_messages_success");
            serviceResult.setRelayMessage(userMessage);
            serviceResult.setSuccess();
            if (logger.isFine()) {
                logger.fine("Successfully imported page \"%s\" under path /%s for username %s.", importedPage.getName(), importedPage.getPath(), user.getUsername());
            }
        } catch (Exception e) {
            logger.error("Error occurred when importing a page for user: %s.", e, user);
            ServerErrorPageResultException throwMe = getServerErrorPageResultException();
            throw throwMe;
        }
        return serviceResult;
    }

    private void parseImportFileBookmarkItem(Element itemEl, List<Section> importedSections, Section currentSection) {
        Elements h3Children = itemEl.select("h3:first-child");
        boolean isSection = h3Children.size() == 1; // we have an h3 tag as first child
        Elements aChildren = itemEl.select("a:first-child");
        boolean isLink = aChildren.size() == 1; // we have an a tag as first child

        if (isSection) {
            Element h3Tag = h3Children.first();
            String sectionName = h3Tag.text();
            Section newEmbeddedSection = new PersistentSection();
            newEmbeddedSection.setName(sectionName.substring(0, Math.min(sectionName.length(), SpecConstants.SECTION_NAME_MAX_SIZE_VALUE)));
            importedSections.add(newEmbeddedSection);
            Elements childItems = itemEl.select("dl > dt");
            for (Element childItem : childItems) {
                parseImportFileBookmarkItem(childItem, importedSections, newEmbeddedSection);
            }
        } else if (isLink) {
            Element aTag = aChildren.first();
            String linkName = aTag.text();
            String linkUrl = aTag.attr("href");
            Link link = new PersistentLink();
            link.setName(linkName.substring(0, Math.min(linkName.length(), SpecConstants.LINK_NAME_MAX_SIZE_VALUE)));
            link.setUrl(linkUrl);
            link.setSection(currentSection);
            currentSection.addLink(link);
        }
    }

    @Transactional
	@Override
	public ServiceResult<?> generatePageFile(String username, String path, JspWriterAdapter jspWriterAdapter) {
        ServiceResult<?> serviceResult = new ServiceResult<>();
        try {
			Page page = pageDao.findPage(username, path);
            // write file out to disk and retrieve contents
            jspWriterAdapter.setJspAttribute("page", page);
            jspWriterAdapter.setJspAttribute("user", page.getUser());
            jspWriterAdapter.setJspAttribute("utilityService", utilityService);
            jspWriterAdapter.write();
            byte[] pageContents = jspWriterAdapter.getContentsBytes();
            // reset page load cache
            pageServeService.addPageContentsToCache(page, pageContents);
            serviceResult.setSuccess();
            if (logger.isFine()) {
				logger.fine("Successfully generated page file and contents for page \"%s\" under path /%s for username %s.", page.getName(), page.getPath(), username);
			}
        } catch (Exception e) {
            logger.error("Error occurred when generating page file for username %s and path %s.", e, username, path);
            ServerErrorAjaxResultException throwMe = getServerErrorAjaxResultException();
            throw throwMe;
        }
        return serviceResult;
	}

    @Transactional
    @Override
    public ServiceResult<?> pinLink(User user, String pagePath, String sectionName, String linkTitle, String linkUrl, String linkNote, JspWriterAdapter jspWriterAdapter) {
        ServiceResult<?> serviceResult = new ServiceResult<>();
        try {
            PersistentPage persistentPage = pageDao.findPageByPath(user, pagePath);
            if (persistentPage != null) {
                outer: for (PersistentColumn persistentColumn : persistentPage.getColumns()) {
                    for (PersistentSection persistentSection : persistentColumn.getSections()) {
                        if (persistentSection.getName().equals(sectionName)) {
                            PersistentLink persistentLink = new PersistentLink();
                            persistentSection.addLink(persistentLink);
                            persistentLink.setName(linkTitle);
                            persistentLink.setUrl(linkUrl);
                            if (linkNote != null) {
                                PersistentLinkNote persistentLinkNote = new PersistentLinkNote();
                                persistentLink.addLinkNote(persistentLinkNote);
                                persistentLinkNote.setUser(user);
                                persistentLinkNote.setNote(linkNote);
                                persistentLinkNote.setPostTimestamp(Util.now());
                            }
                            break outer;
                        }
                    }
                }

                // re-save existing page structure
                pageDao.saveOrUpdate(persistentPage);
                // cleanup old cache (file will be deleted on jsp writer write
                pageServeService.removePageCache(persistentPage);
                // regenerate page and save to caching service
                jspWriterAdapter.setJspAttribute("page", persistentPage);
                jspWriterAdapter.setJspAttribute("user", user);
                jspWriterAdapter.setJspAttribute("utilityService", utilityService);
                jspWriterAdapter.write();
                byte[] contentBytes = jspWriterAdapter.getContentsBytes();
                // reload page to cache
                pageServeService.addPageCache(persistentPage, contentBytes);

                // setup result info
                setUserMessage(serviceResult, "pinLink_saveSuccess");
                serviceResult.addAttribute("pageName", persistentPage.getName());
                loadSaveSuccessAttributes(serviceResult, user.getUsername(), persistentPage);
                serviceResult.setSuccess();
                if (logger.isFine()) {
                	logger.fine("Successfully pinned link titled \"%s\" to page \"%s\" for user %s.", linkTitle, persistentPage.getName(), user);
                }
            } else {
                logger.warn("Somehow there was no page for supplied path \"%s\" when pinning a link for user %s.", pagePath, user);
            }
        } catch (Exception e) {
            logger.error("Error occurred when pinning a link for user: %s.", e, user);
            ServerErrorAjaxResultException throwMe = getServerErrorAjaxResultException();
            throw throwMe;
        }
        return serviceResult;
    }

    @Transactional
    @Override
    public ServiceResult<Page> findGuestModifiablePage(User guestUser, String ownerUsername, String pagePath) {
        ServiceResult<Page> serviceResult = new ServiceResult<>();
        try {
            PersistentPage persistentPage = pageDao.findGuestModifiablePage(guestUser, ownerUsername, pagePath);
            if (persistentPage != null) {
                serviceResult.setObject(persistentPage);
                serviceResult.setSuccess();
            }
        } catch (Exception e) {
            logger.error("Error occurred when finding guest-modifiable with path %s for username %s.", e, pagePath, ownerUsername);
        }
        return serviceResult;
    }

    private List<PersistentUser> loadPersistentLinkNotes(PageDto pageDto, User user) {
        List<PersistentUser> pageUsers = null;
        Set<String> usernames = new HashSet<>();
        // now get all user objects for link notes history and load those too
        extractLinkNoteUsernames(pageDto, usernames, user);
        if (!usernames.isEmpty()) {
            // fetch all real persistent user objects
            pageUsers = accountDao.findAllUsersByUsername(usernames);
            if (logger.isFiner()) {
                logger.finer("Found %d users in username search from expected amount %d.", pageUsers.size(), usernames.size());
            }
        }

        return pageUsers;
    }

    private void loadPersistentPageGuestsAndLinkNotes(PageDto pageDto, PersistentPage page, User user) {
        Set<String> usernames = new HashSet<>();
        usernames.add(user.getUsername()); // implicitly add the calling user
        // first extract guest usernames
        Map<String, PageGuestDto> guestDtoMap = new HashMap<>();
        if (!pageDto.getPageGuestDtos().isEmpty()) {
            // get all user objects for guests and load users
            for (PageGuestDto dto : pageDto.getPageGuestDtos()) {
                String username = dto.getUsername();
                usernames.add(username);
                guestDtoMap.put(username, dto);
            }
        }
        // now get all user objects for link notes history and load those too
        extractLinkNoteUsernames(pageDto, usernames, user);
        // fetch all real persistent user objects
        PersistentUser persistentOwnerUser = (PersistentUser) user;
        List<PersistentUser> pageUsers = accountDao.findAllUsersByUsername(usernames);
        if (logger.isFiner()) {
            logger.finer("Found %d users in username search from expected amount %d.", pageUsers.size(), usernames.size());
        }
        // transfer guest object references over
        for (PersistentUser guestUser : pageUsers) {
            PageGuestDto dto = guestDtoMap.get(guestUser.getUsername());
            if (dto != null) { // could be null if user added a note at one time, then was removed from the guest list
                PersistentPageGuest persistentPageGuest = new PersistentPageGuest(persistentOwnerUser, guestUser, page, dto.getCanModify());
                page.addPageGuest(persistentPageGuest);
            }
        }

        Map<String, PersistentUser> userLookup = loadPersistentUserLookup(pageUsers);

        // transfer user objects into link note objects
        finalizePersistentLinkNotes(userLookup, pageDto, user);
    }

    private void extractLinkNoteUsernames(PageDto pageDto, Set<String> usernames, User user) {
        // get all user objects for link notes history and load those too
        for (Column column : pageDto.getColumns()) {
            for (Section section : column.getSections()) {
                for (Link link : section.getLinks()) {
                    LinkDto linkDto = (LinkDto) link;
                    for (LinkNoteDto linkNoteDto : linkDto.getLinkNoteDtos()) {
                        String username = linkNoteDto.getUsername();
                        if (username == null) {
                            // if username is null, then this is a new note added by the current user
                            username = user.getUsername();
                        }
                        usernames.add(username);
                    }
                }
            }
        }
    }

    private void finalizePersistentLinkNotes(Map<String, PersistentUser> userLookup, PageDto pageDto, User user) {
        // transfer user objects into link note objects
        for (Column column : pageDto.getColumns()) {
            for (Section section : column.getSections()) {
                for (Link link : section.getLinks()) {
                    LinkDto linkDto = (LinkDto) link;
                    for (LinkNoteDto linkNoteDto : linkDto.getLinkNoteDtos()) {
                        // first establish user
                        User linkNoteUser = null;
                        String username = linkNoteDto.getUsername();
                        // if username is null, then this is a new note added by the current user, otherwise username is already set
                        linkNoteUser = username == null ? user : userLookup.get(username);
                        // now build persistent link note object
                        PersistentLinkNote persistentLinkNote = (PersistentLinkNote) linkNoteDto.getLinkNoteBackReference();
                        persistentLinkNote.setUser(linkNoteUser);

                        // if no post timestamp then this is a new note
                        if (linkNoteDto.getPostTimestamp() == null) {
                            persistentLinkNote.setPostTimestamp(Util.now());
                        }
                    }
                }
            }
        }
    }

    private void validateEditableFields(ServiceResult<?> serviceResult, PageDto pageDto, User user) {
        // validate if page path does not already exist
        boolean pageWithPathAlreadyExists = pageDao.findPageByPath(user, pageDto.getPath()) != null; // page path already in-use
        if (pageWithPathAlreadyExists) {
            addSupportingUserMessage(serviceResult, "designer_pageInfo_validate_pathAlreadyExists");
        }
    }
    
    private void evictExistingHomePage(Page existingHome) {
    	pageServeService.removePageAsHome(existingHome);
        existingHome.setHome(false);
        pageDao.saveOrUpdate(existingHome);
        if (logger.isFiner()) {
			logger.finer("Evicted page %s for user %s.", existingHome, existingHome.getUser());
		}
    }

    private void conditionalizePage(Page page) {
        // misc conditioning
        page.setPath(page.getPath().toLowerCase());
        // strip special characters
        for (Column column : page.getColumns()) {
            for (Section section : column.getSections()) {
               for (Link link : section.getLinks()) {
                    if (link.getUrl().contains("'")) {
                        link.setUrl(link.getUrl().replace("'", ""));
                    }
               }
            }
        }
    }

    private class UserPages {
        private List<PersistentPage> allUserPages;
        private PersistentPage existingHome;
        private UserPages(User user) {
            // this object is mandatory for all usages so eager load
            // TODO: DOES this return null or an empty list?
            allUserPages = pageDao.findAllPagesForUser(user);
            if (logger.isFiner()) {
				logger.finer("Located %d pages for user %s.", allUserPages.size(), user);
			}
        }
        PersistentPage getExistingHome() {
            if (existingHome == null) { // lazy load on-demand
            	for (PersistentPage candidateHomePage : allUserPages) {
            		if (candidateHomePage.isHome()) {
            			existingHome = candidateHomePage;
            			break;
            		}
            	}
            }
            return existingHome;
        }
        boolean hasNoPages() {
            return allUserPages.isEmpty();
        }
        boolean hasPages() {
            return !allUserPages.isEmpty();
        }
        boolean hasOnePage() {
            return allUserPages.size() == 1;
        }
        boolean hasMultiplePages() {
            return allUserPages.size() > 1;
        }
        boolean hasExistingPageByPath(String path) {
            boolean hasExistingPageByPath = false;
            path = path.toLowerCase();
            for (Page existingPage : allUserPages) {
                String existingPagePath = existingPage.getPath().toLowerCase();
                if (existingPagePath.equals(path)) {
                    hasExistingPageByPath = true;
                }
            }
            return hasExistingPageByPath;
        }
    }
}