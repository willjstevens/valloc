package com.valloc.dao;

import com.valloc.object.domain.Page;
import com.valloc.object.domain.User;
import com.valloc.object.dto.DashboardListingPageDto;
import com.valloc.object.dto.PageGuestResultDto;
import com.valloc.object.persistent.PersistentPage;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Sample {@link @Repository} DAO interface for typical data operations.
 *
 * @author wstevens
 */
@Repository
public interface PageDao extends Dao
{
	/*
	 * Used when adding a new page and checking for existence.
	 */
    public PersistentPage findPageByPath(User user, String path);


    public List<PersistentPage> findAllPagesForUser(User user);

    public PersistentPage findHomePageForUser(User user);

    public PersistentPage findHomePageByUsername(String username);
    public PersistentPage findPage(String username, String path);

    /**
	 * When an existing page is clicked to be edited or deleted.
	 * 
	 * @param user
	 * @param url
	 * @return
	 */
	public PersistentPage findPageByPathForUser(User user, String path);
	
	/*
	 * Used for dashboard listing of pages
	 */
	public List<PersistentPage> findOwnedPagesForUser(User user);
	
	/*
	 * For page designer for Wills, select all users with access to Wills OpTier page 
	 */
	public List<PageGuestResultDto> findPageGuests(User user, Page page);
	
	/*
	 * For dashboard, select all pages the user has modify-access to of another's users
	 */
	public List<DashboardListingPageDto> findGuestModifiablePages(User guestUser);
	
	/*
	 * For dashboard, select page data for guest user about to modify the page (for Bob)
	 */
	public PersistentPage findGuestModifiablePage(User guestUser, String ownerUsername, String pagePath);

    public List<PersistentPage> findPagesAndSectionsForUser(User user);

}