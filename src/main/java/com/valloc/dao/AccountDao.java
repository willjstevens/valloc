package com.valloc.dao;

import com.valloc.object.persistent.PersistentUser;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * Sample {@link @Repository} DAO interface for typical data operations.
 *
 * @author wstevens
 */
@Repository
public interface AccountDao extends Dao
{
	public PersistentUser findUserByUsername(String username);
	public PersistentUser findUserByEmail(String email);
	public PersistentUser findUserByUsernameOrEmail(String username, String email);
    public List<PersistentUser> findUsersByFirstAndLastName(String firstName, String lastName);
    public List<PersistentUser> findAllUsersByUsername(Set<String> usernames);
    public PersistentUser findUserByCookieValue(String cookieValue);
//	public List<SystemNotification> findUnprocessedMessages(String instanceId);
//	public Config findConfig(Category category, String key);
}