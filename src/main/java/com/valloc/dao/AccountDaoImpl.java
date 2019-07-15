package com.valloc.dao;

import com.valloc.object.persistent.PersistentUser;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * DAO implementation doing basic database operations.
 *
 * @author wstevens
 */
@Repository
public class AccountDaoImpl extends AbstractDao implements AccountDao
{

	@Override
	public PersistentUser findUserByUsername(String username) {
		Criteria criteria = getCurrentSession().createCriteria(PersistentUser.class);
		criteria.add(Restrictions.like("username", username).ignoreCase());
		criteria.add(Restrictions.eq("deleted", Boolean.FALSE));
		PersistentUser retval = (PersistentUser) criteria.uniqueResult();
		return retval;
	}

	@Override
	public PersistentUser findUserByEmail(String email) {
		Criteria criteria = getCurrentSession().createCriteria(PersistentUser.class);
		criteria.add(Restrictions.like("email", email));
		criteria.add(Restrictions.eq("deleted", Boolean.FALSE));
		PersistentUser retval = (PersistentUser) criteria.uniqueResult();
		return retval;
	}

	@Override
	public PersistentUser findUserByUsernameOrEmail(String username, String email) {
		Criteria criteria = getCurrentSession().createCriteria(PersistentUser.class);
		criteria.add(Restrictions.disjunction().
					add(Restrictions.eq("username", username)).
					add(Restrictions.eq("email", email))
		);
		criteria.add(Restrictions.eq("deleted", Boolean.FALSE));
		return (PersistentUser) criteria.uniqueResult();
	}

    @Override
    public List<PersistentUser> findUsersByFirstAndLastName(String firstName, String lastName) {
        if (firstName == null || lastName == null) {
            throw new IllegalArgumentException("Need to supply both first and last name.");
        }
        Criteria criteria = getCurrentSession().createCriteria(PersistentUser.class);
        criteria.add(Restrictions.like("firstName", firstName).ignoreCase());
        criteria.add(Restrictions.like("lastName", lastName).ignoreCase());
        criteria.add(Restrictions.eq("deleted", Boolean.FALSE));
        return criteria.list();
    }

    @Override
    public List<PersistentUser> findAllUsersByUsername(Set<String> usernames) {
        Criteria criteria = getCurrentSession().createCriteria(PersistentUser.class);
        criteria.add(Restrictions.in("username", usernames));
        criteria.add(Restrictions.eq("deleted", Boolean.FALSE));
        return criteria.list();
    }
    
    @Override
	public PersistentUser findUserByCookieValue(String cookieValue) {
		Criteria criteria = getCurrentSession().createCriteria(PersistentUser.class);
		criteria.add(Restrictions.like("cookieValue", cookieValue));
		criteria.add(Restrictions.eq("deleted", Boolean.FALSE));
		return (PersistentUser) criteria.uniqueResult();
	}
}