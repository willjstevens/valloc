package com.valloc.dao;

import com.valloc.Exposure;
import com.valloc.object.persistent.PersistentUserMessage;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Sample {@link @Repository} DAO interface for typical data operations.
 *
 * @author wstevens
 */
@Repository
public interface ApplicationDao extends Dao
{
	public List<PersistentUserMessage> getAllUserMessages();
	public List<PersistentUserMessage> findUserMessagesByExposure(Exposure... exposures);

}