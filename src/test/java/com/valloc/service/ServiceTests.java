package com.valloc.service;

import com.valloc.CommonWiringConfig;
import com.valloc.dao.MockDaoConfig;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Sample service tests.
 * 
 * @author wstevens
 */
@ContextConfiguration(classes={ServiceWiringConfig.class, CommonWiringConfig.class, MockDaoConfig.class})
@RunWith(SpringJUnit4ClassRunner.class)
public class ServiceTests 
{
	@Autowired
	private SystemService systemService;
	
	@Test
	public void testSystemService() {
		systemService.startPollingMessages();
		systemService.stopPollingMessages();

		// Do something more useful here...
		Assert.assertTrue(true); 
	}
}