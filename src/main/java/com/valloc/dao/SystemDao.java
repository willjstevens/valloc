package com.valloc.dao;

import com.valloc.Category;
import com.valloc.Exposure;
import com.valloc.object.persistent.PersistentConfig;
import com.valloc.object.persistent.PersistentSystemNotification;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Sample {@link @Repository} DAO interface for typical data operations.
 *
 * @author wstevens
 */
@Repository
public interface SystemDao extends Dao
{
	public List<PersistentSystemNotification> findUnprocessedMessages(String instanceId);
	public List<PersistentConfig> getAllConfigs();
	public PersistentConfig findConfig(Category category, String key);
	public List<PersistentConfig> findConfigsByExposure(Exposure... exposures);
}