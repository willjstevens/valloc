package com.valloc.dao;

import com.valloc.Category;
import com.valloc.log.LogManager;
import com.valloc.log.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Collection;

/**
 * DAO implementation doing basic database operations.
 *
 * @author wstevens
 */
@Repository
public class AbstractDao implements Dao
{
	private static final Logger logger = LogManager.manager().newLogger(AbstractDao.class, Category.DATABASE);
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public void saveOrUpdate(Object persistentObject) {
		Session session = getCurrentSession();
		try {
			session.saveOrUpdate(persistentObject);
		} catch (HibernateException e) {
			logger.error("Error when trying to save or update object: %s.", e, persistentObject);
		}
	}


	@Override
	public <T> void saveOrUpdateBatch(Collection<T> vals, int batchFlushAmt) {
		Session session = getCurrentSession();
		try {
			int i = 1; // set on 1 to avoid flush/clear on first iteration
			for (T object : vals) {
				session.saveOrUpdate(object);
				final int modulo = i++ % batchFlushAmt; 
				if (modulo == 0) {
					session.flush();
					session.clear();
				}
			}
			session.flush();
			session.clear();
			if (logger.isFine()) {
				logger.fine("Done batching %d items.", i);
			}
		} catch (HibernateException e) {
			logger.error("Error while updating batch.", e);
		}
	}

    @Override
    public void delete(Object persistentObject) {
        getCurrentSession().delete(persistentObject);
    }

    @Override
    public void merge(Object persistentObject) {
        getCurrentSession().merge(persistentObject);
    }

    @Override
	public void flush() {
		getCurrentSession().flush();
	}

	@Override
	public void clear() {
		getCurrentSession().clear();
	}

	@Override
	public Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}
}