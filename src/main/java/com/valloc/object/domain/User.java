package com.valloc.object.domain;

import java.util.Date;


/**
 *
 * @author wstevens
 */
public interface User
{	
	public int getId();
	public void setId(int id);
	public String getUsername();
	public void setUsername(String username);
	public String getFirstName();
	public void setFirstName(String firstName);
	public String getLastName();
	public void setLastName(String lastName);
	public String getEmail();
	public void setEmail(String email);
	public Date getBirthdate();
	public void setBirthdate(Date birthdate);
	public String getGender();
	public void setGender(String gender);
	public String getPassword();
	public void setPassword(String password);
	public String getPasswordQuestion();
	public void setPasswordQuestion(String passwordQuestion);
	public String getPasswordAnswer();
	public void setPasswordAnswer(String passwordAnswer);
	public String getLocale();
	public void setLocale(String locale);
	public String getCookieValue();
	public void setCookieValue(String cookieValue);
	public String getVerificationCode();
	public void setVerificationCode(String verificationCode);
	public Date getVerificationCodeIssuedTimestamp();
	public void setVerificationCodeIssuedTimestamp(Date verificationCodeIssuedTimestamp);
	public Date getVerificationCodeCompletedTimestamp();
	public void setVerificationCodeCompletedTimestamp(Date verificationCodeCompletedTimestamp);
	public boolean isEnabled();
	public void setEnabled(boolean isEnabled);
	public boolean isAdmin();
	public void setAdmin(boolean isAdmin);
	public boolean isDeleted();
	public void setDeleted(boolean deleted);
	public boolean isInAgreement();
	public void setInAgreement(boolean inAgreement);
	public Date getLastLoginTimestamp();
	public void setLastLoginTimestamp(Date lastLoginTimestamp);
}
