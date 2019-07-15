package com.valloc.dao;

import com.valloc.Visibility;
import com.valloc.object.domain.Page;
import com.valloc.object.domain.User;
import com.valloc.object.dto.DashboardListingPageDto;
import com.valloc.object.dto.PageGuestResultDto;
import com.valloc.object.persistent.PersistentPage;
import com.valloc.object.persistent.PersistentPageGuest;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Query;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * DAO implementation doing basic database operations.
 *
 * @author wstevens
 */
@Repository
public class PageDaoImpl extends AbstractDao implements PageDao
{

	@Override
	public PersistentPage findHomePageByUsername(String username) {
        Criteria criteria = getCurrentSession().createCriteria(PersistentPage.class);
        criteria.createAlias("user", "user");
        criteria.add(Restrictions.eq("user.username", username));
        criteria.add(Restrictions.eq("home", true));
        PersistentPage page = (PersistentPage) criteria.uniqueResult();
        return page;
	}

	@Override
	public PersistentPage findPage(String username, String path) {
        Criteria criteria = getCurrentSession().createCriteria(PersistentPage.class);
        criteria.createAlias("user", "user");
        criteria.add(Restrictions.eq("user.username", username));
        criteria.add(Restrictions.like("path", path));
        PersistentPage page = (PersistentPage) criteria.uniqueResult();
        return page;
	}
	
    @Override
    public PersistentPage findHomePageForUser(User user) {
        Criteria criteria = getCurrentSession().createCriteria(PersistentPage.class);
        criteria.createAlias("user", "user");
        criteria.add(Restrictions.eq("user", user));
        criteria.add(Restrictions.eq("home", true));
        PersistentPage page = (PersistentPage) criteria.uniqueResult();
        return page;
    }

    @Override
    public List<PersistentPage> findAllPagesForUser(User user) {
        Criteria criteria = getCurrentSession().createCriteria(PersistentPage.class);
        criteria.add(Restrictions.eq("user", user));
        criteria.addOrder(Order.asc("name"));
        List<PersistentPage> pages = criteria.list();
        return pages;
    }

    @Override
    public PersistentPage findPageByPath(User user, String path) {
        Criteria criteria = getCurrentSession().createCriteria(PersistentPage.class);
        criteria.add(Restrictions.eq("user", user));
        criteria.add(Restrictions.like("path", path));
        PersistentPage page = (PersistentPage) criteria.uniqueResult();
        return page;
    }

    private static final String SELECT_GUESTS_FOR_PAGE =
			"select guest.guestUser.firstName, " + 
				"	guest.guestUser.lastName, " + 
				"	guest.guestUser.username, " + 
				"	guest.canModify " +
				"from PersistentPageGuest guest inner join guest.guestUser " +
				"where guest.page in (" + 
				"	select page.id from PersistentPage page inner join page.user " + 
				"	where page.user.id = :userId and page.path = :path" + 
				")";
	@Override
	public List<PageGuestResultDto> findPageGuests(User user, Page page) {
		Query query = getCurrentSession().createQuery(SELECT_GUESTS_FOR_PAGE);
		query.setParameter("userId", user.getId());
		query.setParameter("path", page.getPath());
		@SuppressWarnings("unchecked")
		List<Object[]> queryResult = query.list();
		List<PageGuestResultDto> results = new ArrayList<>();
		for (Object[] res : queryResult) {
			String firstName = (String) res[0];
			String lastName = (String) res[1];
			String username = (String) res[2];
			boolean canModify = (boolean) res[3];
			results.add(new PageGuestResultDto(firstName, lastName, username, canModify));
		}
		return results;
	}
	
	private static final String SELECT_GUEST_MODIFIABLE_PAGES = 
			"select page.name, " + 
				"	page.path, " +
                "   page.home, " +
				"	page.visibility, " + 
				" 	page.description, " + 
				" 	page.user.firstName, " + 
				"	page.user.lastName, " + 
				"	page.user.username " + 
				"from PersistentPage page inner join page.user " +
				"where page in (" + 
				"	select pageGuest.page from PersistentPageGuest pageGuest inner join page.user " + 
				"	where pageGuest.guestUser.id = :userId " +
				"	and pageGuest.canModify = true"	+
				")";
	@Override
	public List<DashboardListingPageDto> findGuestModifiablePages(User guestUser) {
		Query query = getCurrentSession().createQuery(SELECT_GUEST_MODIFIABLE_PAGES);
		query.setParameter("userId", guestUser.getId());
		@SuppressWarnings("unchecked")
		List<Object[]> queryResult = query.list();
		List<DashboardListingPageDto> results = new ArrayList<>();
		for (Object[] result : queryResult) {
            DashboardListingPageDto page = new DashboardListingPageDto();
            page.setName((String) result[0]);
            page.setPath((String) result[1]);
            page.setHome((boolean) result[2]);
            page.setVisibility((Visibility) result[3]);
            page.setDescription((String) result[4]);
            page.setOwnerFirstName((String) result[5]);
            page.setOwnerLastName((String) result[6]);
            page.setUsername((String) result[7]);
            // implicit fields on guest pages
            page.setGuestPage(true);
            // add to results
            results.add(page);
		}
		return results;
	}

	/*
	 * select * from page where id = (
		select page_id from page_guest 
			where page_id = 
				(select p.id from page p inner join public.user u on p.user_id = u.id
					 where username = 'will'
					 and path = 'optier' 
					)
			and guest_user_id = 3 
			and can_modify = true
		)
	 */
	@Override
	public PersistentPage findGuestModifiablePage(User guestUser, String ownerUsername, String pagePath) {
		Criteria criteria = getCurrentSession().createCriteria(PersistentPage.class);
		
		DetachedCriteria pageSubquery = DetachedCriteria.forClass(PersistentPage.class);
		pageSubquery.setProjection(Property.forName("id"));
		pageSubquery.createAlias("user", "user");
		pageSubquery.add(Restrictions.eq("user.username", ownerUsername));
		pageSubquery.add(Restrictions.eq("path", pagePath));
		
		DetachedCriteria pageGuestSubquery = DetachedCriteria.forClass(PersistentPageGuest.class);
		pageGuestSubquery.createAlias("page", "page");
		pageGuestSubquery.setProjection(Property.forName("page.id"));
		pageGuestSubquery.add(Restrictions.eq("guestUser", guestUser));
		pageGuestSubquery.add(Restrictions.eq("canModify", true));
		pageGuestSubquery.add(Property.forName("page.id").eq(pageSubquery));
		criteria.add(Property.forName("id").eq(pageGuestSubquery));
		
		return (PersistentPage) criteria.uniqueResult();
	}
	
	@Override
	public List<PersistentPage> findOwnedPagesForUser(User user) {
		Criteria criteria = getCurrentSession().createCriteria(PersistentPage.class);
		criteria.add(Restrictions.eq("user", user));
		criteria.addOrder(Order.asc("name"));
		@SuppressWarnings("unchecked")
		List<PersistentPage> pages = criteria.list();
		return pages;
	}
	
	@Override
	public PersistentPage findPageByPathForUser(User user, String path) {
		Criteria criteria = getCurrentSession().createCriteria(PersistentPage.class);
		criteria.add(Restrictions.eq("user", user));
		criteria.add(Restrictions.like("path", path));
		return (PersistentPage) criteria.uniqueResult();				
	}


    @Override
    public List<PersistentPage> findPagesAndSectionsForUser(User user) {
        Criteria criteria = getCurrentSession().createCriteria(PersistentPage.class);
        criteria.add(Restrictions.eq("user", user));
        // Fetch mode not working: https://hibernate.atlassian.net/browse/HHH-3524
        criteria.setFetchMode("columns.sections.links", FetchMode.SELECT);
        @SuppressWarnings("unchecked")
        List<PersistentPage> pages = criteria.list();
        return pages;
    }

}