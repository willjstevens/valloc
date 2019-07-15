package com.valloc.dao;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.util.Collection;

/**
 * Sample {@link @Repository} DAO interface for typical data operations.
 *
 * @author wstevens
 */
@Repository
public interface Dao
{
	public void saveOrUpdate(Object persistentObject);
	public <T> void saveOrUpdateBatch(Collection<T> vals, int batchFlushAmt);
    public void delete(Object persistentObject);
    public void merge(Object persistentObject);
	public void flush();
	public void clear();
	Session getCurrentSession();
}