package com.valloc.dao;

import com.valloc.Category;
import com.valloc.Exposure;
import com.valloc.object.persistent.PersistentConfig;
import com.valloc.object.persistent.PersistentSystemNotification;
import com.valloc.object.persistent.PersistentSystemNotificationHistory;
import org.hibernate.Criteria;
import org.hibernate.criterion.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * DAO implementation doing basic database operations.
 *
 * @author wstevens
 */
@Repository
public class SystemDaoImpl extends AbstractDao implements SystemDao
{

	@Override
	@SuppressWarnings("unchecked")
	public List<PersistentSystemNotification> findUnprocessedMessages(String instanceId) {
		/*
		 * Comparable SQL should be:
		 *
		 *	 select * from message where id not in (
  		 *	     select message_id from message_history where instance_id = '1e6c27401-c3cd-470b-8fbc-d67148fe056c'
		 *	 );
		 *	
		 */
		Criteria criteria = getCurrentSession().createCriteria(PersistentSystemNotification.class);
		// subquery for message_history table
		DetachedCriteria subquery = DetachedCriteria.forClass(PersistentSystemNotificationHistory.class);
		subquery.setProjection(Property.forName("message.id"));
		subquery.add(Restrictions.eq("instanceId", instanceId));
		// tie back to parent message table
		criteria.add(Subqueries.propertyNotIn("id", subquery));
		criteria.addOrder(Order.asc("insertTimestamp"));
		return criteria.list();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<PersistentConfig> getAllConfigs() {
		Criteria criteria = getCurrentSession().createCriteria(PersistentConfig.class);
		return criteria.list();
	}

	@Override
	public PersistentConfig findConfig(Category category, String key) {
		Criteria criteria = getCurrentSession().createCriteria(PersistentConfig.class);
		criteria.add(Restrictions.eq("category", category));
		criteria.add(Restrictions.eq("key", key));
		return (PersistentConfig) criteria.uniqueResult();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<PersistentConfig> findConfigsByExposure(Exposure... exposures) {
		Criteria criteria = getCurrentSession().createCriteria(PersistentConfig.class);
		criteria.add(Restrictions.in("exposure", exposures));
		return criteria.list();
	}
	
}