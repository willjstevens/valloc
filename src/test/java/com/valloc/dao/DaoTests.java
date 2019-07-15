/**
 * 
 */
package com.valloc.dao;

import com.valloc.Category;
import com.valloc.CommonWiringConfig;
import com.valloc.MockFactory;
import com.valloc.object.dto.DashboardListingPageDto;
import com.valloc.object.dto.PageGuestResultDto;
import com.valloc.object.persistent.*;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Typical DAO unit tests.
 * 
 * @author wstevens
 */
@ContextConfiguration(classes={DaoWiringConfig.class, CommonWiringConfig.class})
@TransactionConfiguration(defaultRollback=true)
@RunWith(SpringJUnit4ClassRunner.class)
public class DaoTests 
{
	@Autowired
	private SystemDao systemDao;

	@Autowired
	private AccountDao accountDao;
	
	@Autowired
	private PageDao pageDao;
	
	@Test
	@Transactional
	public void testConfig() {
		PersistentConfig config = MockFactory.newConfig();
		systemDao.saveOrUpdate(config);
		
		PersistentConfig config2 = MockFactory.newConfig();
		config2.setCategory(Category.FILE);
		config2.setKey("temp-file-location");
		systemDao.saveOrUpdate(config2);
		
		config2.setValue("UPDATED VALUE");
		systemDao.saveOrUpdate(config2);

		List<PersistentConfig> allConfigs = systemDao.getAllConfigs();
		for(PersistentConfig conf : allConfigs) {
			systemDao.saveOrUpdate(conf);
		}
		Assert.assertEquals(2, allConfigs.size());
		
		config2 = systemDao.findConfig(Category.FILE, "temp-file-location");
		Assert.assertNotNull(config2);
		
		PersistentConfig nonExistentConfig = systemDao.findConfig(Category.FILE, "BOGUS-KEY");
		Assert.assertNull(nonExistentConfig);
	}
	
	@Test
	@Transactional
	public void testSystemDao() {
		PersistentSystemNotification systemNotification = MockFactory.newMessage();
		systemDao.saveOrUpdate(systemNotification);
		List<PersistentSystemNotificationHistory> systemNotificationHistories = MockFactory.newSystemNotificationHistories(3, systemNotification);
		systemDao.saveOrUpdateBatch(systemNotificationHistories, 5);

		List<PersistentSystemNotification> unprocessedMessages = systemDao.findUnprocessedMessages("some-instance-id");
		Assert.assertEquals(1, unprocessedMessages.size());
		
//		SystemNotificationHistory someInstanceIdProcessedHistory = MockFactory.newMessageHistory();
//		someInstanceIdProcessedHistory.setInstanceId("some-instance-id");
//		message.addMessageHistory(someInstanceIdProcessedHistory);
//		systemDao.saveOrUpdate(someInstanceIdProcessedHistory); // processed and logged
//		systemDao.flush();
//		unprocessedMessages = systemDao.findUnprocessedMessages("some-instance-id"); // now query should produce nothing since processed
//		Assert.assertEquals(0, unprocessedMessages.size()); // everything is processed
	}

	
	@Test
	@Transactional
	public void testPageStuff() {
		// build objects
		PersistentUser will = MockFactory.newUser();
		PersistentUser alice = MockFactory.newUser();
		alice.setUsername("alice");
		alice.setEmail("alice@gmail.com");
		alice.setFirstName("Alice");
		alice.setLastName("in Wonderland");
		alice.setGender("female");
		PersistentUser bob = MockFactory.newUser();
		bob.setUsername("bob");
		bob.setEmail("bob@gmail.com");
		bob.setFirstName("Bob");
		bob.setLastName("Smith");
		
		PersistentPage optierPage = MockFactory.newPage();
		optierPage.setPath("optier");
		optierPage.setUser(will);
		
		PersistentPageGuest aliceGuestOptierPage = new PersistentPageGuest(will, alice, optierPage, false);
		PersistentPageGuest bobGuestOptierPage = new PersistentPageGuest(will, bob, optierPage, true);
		
		pageDao.saveOrUpdate(will);
		pageDao.saveOrUpdate(alice);
		pageDao.saveOrUpdate(bob);
		pageDao.saveOrUpdate(optierPage);
		pageDao.saveOrUpdate(aliceGuestOptierPage);
		pageDao.saveOrUpdate(bobGuestOptierPage);
		
		// now test finder for page guests on a page
		List<PageGuestResultDto> dtos = pageDao.findPageGuests(will, optierPage);
		Assert.assertTrue(dtos.size() == 2);
		
		// now test getting all a guest user's modifiable pages
		PersistentPage articlesPage = MockFactory.newPage();
		articlesPage.setUser(will);
		articlesPage.setName("Trending Articles");
		articlesPage.setPath("trending");
		articlesPage.setSharedPrivately(true);
		pageDao.saveOrUpdate(articlesPage);
		PersistentPageGuest bobGuestTrending = new PersistentPageGuest(will, bob, articlesPage, true);
		pageDao.saveOrUpdate(bobGuestTrending);
		
		List<DashboardListingPageDto> modifiablePages = pageDao.findGuestModifiablePages(bob);
		Assert.assertTrue(modifiablePages.size() == 2); // bob can write/modify 2 pages
		modifiablePages = pageDao.findGuestModifiablePages(alice);
		Assert.assertTrue(modifiablePages.isEmpty()); // alice has no write/modify access
		
		PersistentPage optTierPageForBobToModify = pageDao.findGuestModifiablePage(bob, "will", "optier");
		Assert.assertNotNull(optTierPageForBobToModify);

        PersistentPage queriedPage = pageDao.findPageByPathForUser(will, "optier");
        Assert.assertEquals(2, queriedPage.getPageGuests().size());
	}
	
	@Test
	@Transactional
	public void testPageHiearchy() {
		// build objects
		PersistentUser will = MockFactory.newUser();
        PersistentPage page = MockFactory.newPage();
		page.setUser(will);

        // setup a guest
        PersistentUser alice = MockFactory.newUser();
        alice.setUsername("alice");
        alice.setEmail("alice@gmail.com");
        alice.setFirstName("Alice");
        alice.setLastName("in Wonderland");
        alice.setGender("female");
        PersistentPageGuest aliceGuestPage = new PersistentPageGuest(will, alice, page, false);

		
		PersistentColumn col1 = MockFactory.newColumn();
		page.addColumn(col1);
		PersistentSection sec1 = MockFactory.newSection();
		col1.addSection(sec1);
		PersistentLink link1 = MockFactory.newLink();
		sec1.addLink(link1);

		PersistentColumn col2 = MockFactory.newColumn();
		page.addColumn(col2);
		PersistentSection sec2 = MockFactory.newSection();
		sec2.setName("Boring Stuff");
		col2.addSection(sec2);
		PersistentLink link2 = MockFactory.newLink();
		link2.setName("Whatever");
		link2.setUrl("http://whatever.com");
		sec2.addLink(link2);
		
		pageDao.saveOrUpdate(will);
        pageDao.saveOrUpdate(alice);
		pageDao.saveOrUpdate(page);
		Assert.assertTrue(page.getId() > 0);

        PersistentPage newPage = pageDao.findPageByPathForUser(will, "path");
        Assert.assertEquals(1, newPage.getPageGuests().size());
	}

    @Test
    @Transactional
    public void testUserSearch() {
        // build objects
        PersistentUser bob = MockFactory.newUser();
        bob.setUsername("bobby");
        bob.setEmail("bob@gmail.com");
        bob.setFirstName("Bob");
        bob.setLastName("Smith");
        accountDao.saveOrUpdate(bob);

        PersistentUser user = accountDao.findUserByUsername("bobby");
        Assert.assertNotNull(user);
        PersistentUser nonexistent = accountDao.findUserByUsername("alice");
        Assert.assertNull(nonexistent);
        List<PersistentUser> users = accountDao.findUsersByFirstAndLastName("Bob", "Smith");
        Assert.assertEquals(1, users.size());
        users = accountDao.findUsersByFirstAndLastName("bob", "smith");
        Assert.assertEquals(1, users.size());
    }

    @Test
    @Transactional
    public void testUserPagesTest() {

        PersistentUser user = accountDao.findUserByUsername("will");
        Assert.assertNotNull(user);
        List<PersistentPage> pages = pageDao.findPagesAndSectionsForUser(user);
        System.out.println(pages.get(0).getColumns().get(0).getSections().get(0).getLinks().size());
    }

//	@Test
//	@Transactional
//	public void testSelect() {
//		User user = MockFactory.newUser();
//		systemDao.saveOrUpdate(user);
//		User newlyAddedUser = systemDao.findUserByUsername(user.getUsername());
//		final String expected = user.getUsername();
//		final String actual = newlyAddedUser.getUsername();
//		Assert.assertEquals(expected, actual); 
//	}
}