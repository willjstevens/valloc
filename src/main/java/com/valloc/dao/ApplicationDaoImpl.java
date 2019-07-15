package com.valloc.dao;

import com.valloc.Exposure;
import com.valloc.object.persistent.PersistentUserMessage;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * DAO implementation doing basic database operations.
 *
 * @author wstevens
 */
@Repository
public class ApplicationDaoImpl extends AbstractDao implements ApplicationDao
{


	@Override
	@SuppressWarnings("unchecked")
	public List<PersistentUserMessage> getAllUserMessages() {
		Criteria criteria = getCurrentSession().createCriteria(PersistentUserMessage.class);
		criteria.add(Restrictions.isNull("deleteTimestamp"));
		return criteria.list();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<PersistentUserMessage> findUserMessagesByExposure(Exposure... exposures){
		Criteria criteria = getCurrentSession().createCriteria(PersistentUserMessage.class);
		criteria.add(Restrictions.in("exposure", exposures));
		return criteria.list();
	}

	
}