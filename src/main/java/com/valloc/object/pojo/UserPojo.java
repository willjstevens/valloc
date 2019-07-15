package com.valloc.object.pojo;

import com.valloc.object.domain.User;

import java.util.Date;


/**
 *
 * @author wstevens
 */
public class UserPojo implements User
{
	private int id;
	private String username;
	private String firstName;
	private String lastName;
	private String email;
	private Date birthdate;
	private String gender;
	private String password;
	private String passwordQuestion;
	private String passwordAnswer;
	private String locale;
	private String cookieValue;
	private String verificationCode;
	private Date verificationCodeIssuedTimestamp;
	private Date verificationCodeCompletedTimestamp;
	private boolean inAgreement;
	private boolean isEnabled;
	private boolean isAdmin;
	private boolean isDeleted;
	public Date lastLoginTimestamp;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Date getBirthdate() {
		return birthdate;
	}
	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPasswordQuestion() {
		return passwordQuestion;
	}
	public void setPasswordQuestion(String passwordQuestion) {
		this.passwordQuestion = passwordQuestion;
	}
	public String getPasswordAnswer() {
		return passwordAnswer;
	}
	public void setPasswordAnswer(String passwordAnswer) {
		this.passwordAnswer = passwordAnswer;
	}
	public String getLocale() {
		return locale;
	}
	public void setLocale(String locale) {
		this.locale = locale;
	}
	public String getCookieValue() {
		return cookieValue;
	}
	public void setCookieValue(String cookieValue) {
		this.cookieValue = cookieValue;
	}
	public String getVerificationCode() {
		return verificationCode;
	}
	public void setVerificationCode(String verificationCode) {
		this.verificationCode = verificationCode;
	}
	public Date getVerificationCodeIssuedTimestamp() {
		return verificationCodeIssuedTimestamp;
	}
	public void setVerificationCodeIssuedTimestamp(
			Date verificationCodeIssuedTimestamp) {
		this.verificationCodeIssuedTimestamp = verificationCodeIssuedTimestamp;
	}
	public Date getVerificationCodeCompletedTimestamp() {
		return verificationCodeCompletedTimestamp;
	}
	public void setVerificationCodeCompletedTimestamp(
			Date verificationCodeCompletedTimestamp) {
		this.verificationCodeCompletedTimestamp = verificationCodeCompletedTimestamp;
	}
	public boolean isEnabled() {
		return isEnabled;
	}
	public void setEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
	}
	public boolean isAdmin() {
		return isAdmin;
	}
	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}
	public boolean isDeleted() {
		return isDeleted;
	}
	public void setDeleted(boolean deleted) {
		this.isDeleted = deleted;
	}
	public boolean isInAgreement() {
		return inAgreement;
	}
	public void setInAgreement(boolean inAgreement) {
		this.inAgreement = inAgreement;
	}

	public Date getLastLoginTimestamp() {
		return lastLoginTimestamp;
	}

	public void setLastLoginTimestamp(Date lastLoginTimestamp) {
		this.lastLoginTimestamp = lastLoginTimestamp;
	}

	@Override
	public String toString() {
		return String.format(
				"UserPojo [username=%s, firstName=%s, lastName=%s, email=%s]",
				username, firstName, lastName, email);
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result
				+ ((username == null) ? 0 : username.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserPojo other = (UserPojo) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}
	
	
}
