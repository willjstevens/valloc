package com.valloc.dao;

import com.valloc.Category;
import com.valloc.Exposure;
import com.valloc.MockFactory;
import com.valloc.object.persistent.PersistentConfig;
import com.valloc.object.persistent.PersistentSystemNotification;

import java.util.ArrayList;
import java.util.List;


/**
 * A mock DAO typically loaded in non-DAO unit tests not concerned with dao tier.  This could be easily overrided if 
 * a service tier was being tested and the test expected a particular object to be returned from the data tier.
 *
 * @author wstevens
 */
public class MockSystemDao extends MockDao implements SystemDao
{


	@Override
	public List<PersistentConfig> getAllConfigs() {
		List<PersistentConfig> allConfigs = new ArrayList<>();
		PersistentConfig config1 = MockFactory.newConfig();
		config1.setKey("key-1");
		PersistentConfig config2 = MockFactory.newConfig();
		config2.setKey("key-2");
		return allConfigs;
	}

	@Override
	public PersistentConfig findConfig(Category category, String key) {
		PersistentConfig retval = MockFactory.newConfig();
		retval.setCategory(category);
		retval.setKey(key);
		return retval;
	}

	@Override
	public List<PersistentSystemNotification> findUnprocessedMessages(String instanceId) {
		List<PersistentSystemNotification> systemNotifications = new ArrayList<>();
		systemNotifications.add(MockFactory.newMessage());
		systemNotifications.add(MockFactory.newMessage());
		return systemNotifications;
	}

	@Override
	public List<PersistentConfig> findConfigsByExposure(Exposure... exposures) {
		return null;
	}

	
	
}