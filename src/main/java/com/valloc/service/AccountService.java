package com.valloc.service;

import com.valloc.framework.ServiceResult;
import com.valloc.framework.Session;
import com.valloc.object.persistent.PersistentUser;
import com.valloc.web.AccountForm;

import java.util.Date;


/**
 * Typical service with miscellaneous operations.
 *
 * @author wstevens
 */
public interface AccountService extends VallocService
{

	public void init();
	public ServiceResult<PersistentUser> addUser(AccountForm accountForm);
	public ServiceResult<?> editUser(AccountForm accountForm);
	public ServiceResult<PersistentUser> findUserByUsername(String username);
	public ServiceResult<?> verifyAccount(String email, String verificationCode);
	public ServiceResult<Session> loginByUsernamePassword(String username, String password);
	public ServiceResult<Session> loginByCookie(String cookieValue);
	public ServiceResult<?> logout(String username);
    public String formatBirtdate(Date birthdate);
}
