package com.valloc.dao;

import org.hibernate.*;
import org.hibernate.jdbc.ReturningWork;
import org.hibernate.jdbc.Work;
import org.hibernate.procedure.ProcedureCall;
import org.hibernate.stat.SessionStatistics;

import java.io.Serializable;
import java.sql.Connection;
import java.util.Collection;


/**
 * A mock DAO typically loaded in non-DAO unit tests not concerned with dao tier.  This could be easily overrided if 
 * a service tier was being tested and the test expected a particular object to be returned from the data tier.
 *
 * @author wstevens
 */
public class MockDao implements Dao
{
	@Override
	public void clear() {
		// TODO Auto-generated method stub
		
	}

    @Override
    public void merge(Object persistentObject) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
	public void saveOrUpdate(Object persistentObject) {
		// do nothing
	}

	@Override
	public Session getCurrentSession() {
		
		return new MockHibernateSession();
	}	
	
	@Override
	public void flush() {
		// TODO Auto-generated method stub
		
	}

	@SuppressWarnings("rawtypes") 
	private static class MockHibernateSession implements Session {

		/**
		 * 
		 */
		private static final long serialVersionUID = -3229277170296972675L;

		@Override
		public Transaction beginTransaction() {
			return null;
		}

		@Override
		public Criteria createCriteria(Class arg0) {
			return null;
		}

		@Override
		public Criteria createCriteria(String arg0) {
			return null;
		}

		@Override
		public Criteria createCriteria(Class arg0, String arg1) {
			return null;
		}

		@Override
		public Criteria createCriteria(String arg0, String arg1) {
			return null;
		}

		@Override
		public Query createQuery(String arg0) {
			return null;
		}

		@Override
		public SQLQuery createSQLQuery(String arg0) {
			return null;
		}

        @Override
        public ProcedureCall getNamedProcedureCall(String name) {
            return null;
        }

        @Override
        public ProcedureCall createStoredProcedureCall(String procedureName) {
            return null;
        }

        @Override
        public ProcedureCall createStoredProcedureCall(String procedureName, Class... resultClasses) {
            return null;
        }

        @Override
        public ProcedureCall createStoredProcedureCall(String procedureName, String... resultSetMappings) {
            return null;
        }

        @Override
		public Query getNamedQuery(String arg0) {
			return null;
		}

		@Override
		public String getTenantIdentifier() {
			return null;
		}

		@Override
		public Transaction getTransaction() {
			return null;
		}

		@Override
		public LockRequest buildLockRequest(LockOptions arg0) {
			return null;
		}

		@Override
		public IdentifierLoadAccess byId(String arg0) {
			return null;
		}

		@Override
		public IdentifierLoadAccess byId(Class arg0) {
			return null;
		}

		@Override
		public NaturalIdLoadAccess byNaturalId(String arg0) {
			return null;
		}

		@Override
		public NaturalIdLoadAccess byNaturalId(Class arg0) {
			return null;
		}

		@Override
		public SimpleNaturalIdLoadAccess bySimpleNaturalId(String arg0) {
			return null;
		}

		@Override
		public SimpleNaturalIdLoadAccess bySimpleNaturalId(Class arg0) {
			return null;
		}

		@Override
		public void cancelQuery() throws HibernateException {
		}

		@Override
		public void clear() {
		}

		@Override
		public Connection close() throws HibernateException {
			return null;
		}

		@Override
		public boolean contains(Object arg0) {
			return false;
		}

		@Override
		public Query createFilter(Object arg0, String arg1) {
			return null;
		}

		@Override
		public void delete(Object arg0) {
		}

		@Override
		public void delete(String arg0, Object arg1) {
		}

		@Override
		public void disableFetchProfile(String arg0)
				throws UnknownProfileException {
		}

		@Override
		public void disableFilter(String arg0) {
		}

		@Override
		public Connection disconnect() {
			return null;
		}

		@Override
		public <T> T doReturningWork(ReturningWork<T> arg0)
				throws HibernateException {
			return null;
		}

		@Override
		public void doWork(Work arg0) throws HibernateException {
		}

		@Override
		public void enableFetchProfile(String arg0)
				throws UnknownProfileException {
		}

		@Override
		public Filter enableFilter(String arg0) {
			return null;
		}

		@Override
		public void evict(Object arg0) {
		}

		@Override
		public void flush() throws HibernateException {
		}

		@Override
		public Object get(Class arg0, Serializable arg1) {
			return null;
		}

		@Override
		public Object get(String arg0, Serializable arg1) {
			return null;
		}

		@Override
		public Object get(Class arg0, Serializable arg1, LockMode arg2) {
			return null;
		}

		@Override
		public Object get(Class arg0, Serializable arg1, LockOptions arg2) {
			return null;
		}

		@Override
		public Object get(String arg0, Serializable arg1, LockMode arg2) {
			return null;
		}

		@Override
		public Object get(String arg0, Serializable arg1, LockOptions arg2) {
			return null;
		}

		@Override
		public CacheMode getCacheMode() {
			return null;
		}

		@Override
		public LockMode getCurrentLockMode(Object arg0) {
			return null;
		}

		@Override
		public Filter getEnabledFilter(String arg0) {
			return null;
		}

		@Override
		public String getEntityName(Object arg0) {
			return null;
		}

		@Override
		public FlushMode getFlushMode() {
			return null;
		}

		@Override
		public Serializable getIdentifier(Object arg0) {
			return null;
		}

		@Override
		public LobHelper getLobHelper() {
			return null;
		}

        @Override
        public void addEventListeners(SessionEventListener... listeners) {

        }

        @Override
		public SessionFactory getSessionFactory() {
			return null;
		}

		@Override
		public SessionStatistics getStatistics() {
			return null;
		}

		@Override
		public TypeHelper getTypeHelper() {
			return null;
		}

		@Override
		public boolean isConnected() {
			return false;
		}

		@Override
		public boolean isDefaultReadOnly() {
			return false;
		}

		@Override
		public boolean isDirty() throws HibernateException {
			return false;
		}

		@Override
		public boolean isFetchProfileEnabled(String arg0)
				throws UnknownProfileException {
			return false;
		}

		@Override
		public boolean isOpen() {
			return false;
		}

		@Override
		public boolean isReadOnly(Object arg0) {
			return false;
		}

		@Override
		public Object load(Class arg0, Serializable arg1) {
			return null;
		}

		@Override
		public Object load(String arg0, Serializable arg1) {
			return null;
		}

		@Override
		public void load(Object arg0, Serializable arg1) {
		}

		@Override
		public Object load(Class arg0, Serializable arg1, LockMode arg2) {
			return null;
		}

		@Override
		public Object load(Class arg0, Serializable arg1, LockOptions arg2) {
			return null;
		}

		@Override
		public Object load(String arg0, Serializable arg1, LockMode arg2) {
			return null;
		}

		@Override
		public Object load(String arg0, Serializable arg1, LockOptions arg2) {
			return null;
		}

		@Override
		public void lock(Object arg0, LockMode arg1) {
		}

		@Override
		public void lock(String arg0, Object arg1, LockMode arg2) {
		}

		@Override
		public Object merge(Object arg0) {
			return null;
		}

		@Override
		public Object merge(String arg0, Object arg1) {
			return null;
		}

		@Override
		public void persist(Object arg0) {
		}

		@Override
		public void persist(String arg0, Object arg1) {
		}

		@Override
		public void reconnect(Connection arg0) {
		}

		@Override
		public void refresh(Object arg0) {
		}

		@Override
		public void refresh(String arg0, Object arg1) {
		}

		@Override
		public void refresh(Object arg0, LockMode arg1) {
		}

		@Override
		public void refresh(Object arg0, LockOptions arg1) {
		}

		@Override
		public void refresh(String arg0, Object arg1, LockOptions arg2) {
		}

		@Override
		public void replicate(Object arg0, ReplicationMode arg1) {
		}

		@Override
		public void replicate(String arg0, Object arg1, ReplicationMode arg2) {
		}

		@Override
		public Serializable save(Object arg0) {
			return null;
		}

		@Override
		public Serializable save(String arg0, Object arg1) {
			return null;
		}

		@Override
		public void saveOrUpdate(Object arg0) {
		}

		@Override
		public void saveOrUpdate(String arg0, Object arg1) {
		}

		@Override
		public SharedSessionBuilder sessionWithOptions() {
			return null;
		}

		@Override
		public void setCacheMode(CacheMode arg0) {
		}

		@Override
		public void setDefaultReadOnly(boolean arg0) {
		}

		@Override
		public void setFlushMode(FlushMode arg0) {
		}

		@Override
		public void setReadOnly(Object arg0, boolean arg1) {
		}

		@Override
		public void update(Object arg0) {
		}

		@Override
		public void update(String arg0, Object arg1) {
		}
		
	}

	@Override
	public <T> void saveOrUpdateBatch(Collection<T> vals, int batchFlushAmt) {
	}

    @Override
    public void delete(Object persistentObject) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}