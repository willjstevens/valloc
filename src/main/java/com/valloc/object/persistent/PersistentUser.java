package com.valloc.object.persistent;

import com.valloc.object.domain.User;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.util.Date;


/**
 * Standard-ish user domain object. Note entity mappings have been kept in XML since it
 * is not desired to "polute" business domain class with data tier technology annotations.
 * 
 * @author wstevens
 */
@Entity
@Table(name="user", schema="public")
@SequenceGenerator(
		name="id_seq",
 	    allocationSize=1,
 	    sequenceName="user_id_seq"
 )
public class PersistentUser extends UpdatableDomainObject implements User
{
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
	private boolean isEnabled;
	private boolean isAdmin;
	private boolean deleted;
	private boolean inAgreement;
	private Date lastLoginTimestamp;

	@Column(name="username", nullable=false)
	public String getUsername() {
		return username;
	}

	@Column(name="first_name", nullable=false)
	public String getFirstName() {
		return firstName;
	}

	@Column(name="last_name", nullable=false)
	public String getLastName() {
		return lastName;
	}

	@Column(name="email", nullable=false)
	public String getEmail() {
		return email;
	}

	@Column(name="birthdate", nullable=false)
	public Date getBirthdate() {
		return birthdate;
	}

	@Column(name="gender", nullable=false)
	public String getGender() {
		return gender;
	}
	
	@Column(name="password", nullable=false)
	public String getPassword() {
		return password;
	}

	@Column(name="password_question", nullable=false)
	public String getPasswordQuestion() {
		return passwordQuestion;
	}

	@Column(name="password_answer", nullable=false)
	public String getPasswordAnswer() {
		return passwordAnswer;
	}

	@Column(name="locale", nullable=false)
	public String getLocale() {
		return locale;
	}

	@Column(name="cookie_value")
	public String getCookieValue() {
		return cookieValue;
	}

	@Column(name="verification_code", nullable=false)
	public String getVerificationCode() {
		return verificationCode;
	}

	@Column(name="verification_code_issued_timestamp", nullable=false)
	public Date getVerificationCodeIssuedTimestamp() {
		return verificationCodeIssuedTimestamp;
	}

	@Column(name="verification_completed_timestamp")
	public Date getVerificationCodeCompletedTimestamp() {
		return verificationCodeCompletedTimestamp;
	}

	@Column(name="is_enabled", nullable=false)
	public boolean isEnabled() {
		return isEnabled;
	}

	@Column(name="is_admin", nullable=false)
	public boolean isAdmin() {
		return isAdmin;
	}

	@Column(name="is_deleted", nullable=false)
	public boolean isDeleted() {
		return deleted;
	}

	@Column(name="in_agreement", nullable=false)
	public boolean isInAgreement() {
		return inAgreement;
	}

	@Column(name="last_login_timestamp")
	public Date getLastLoginTimestamp() {
		return lastLoginTimestamp;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setPasswordQuestion(String passwordQuestion) {
		this.passwordQuestion = passwordQuestion;
	}

	public void setPasswordAnswer(String passwordAnswer) {
		this.passwordAnswer = passwordAnswer;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}

	public void setCookieValue(String cookieValue) {
		this.cookieValue = cookieValue;
	}

	public void setVerificationCode(String verificationCode) {
		this.verificationCode = verificationCode;
	}

	public void setVerificationCodeIssuedTimestamp(
			Date verificationCodeIssuedTimestamp) {
		this.verificationCodeIssuedTimestamp = verificationCodeIssuedTimestamp;
	}

	public void setVerificationCodeCompletedTimestamp(
			Date verificationCodeCompletedTimestamp) {
		this.verificationCodeCompletedTimestamp = verificationCodeCompletedTimestamp;
	}

	public void setEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	public void setDeleted(boolean isDeleted) {
		this.deleted = isDeleted;
	}

	public void setInAgreement(boolean inAgreement) {
		this.inAgreement = inAgreement;
	}

	public void setLastLoginTimestamp(Date lastLoginTimestamp) {
		this.lastLoginTimestamp = lastLoginTimestamp;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
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
		PersistentUser other = (PersistentUser) obj;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return String.format("User [username=%s, firstName=%s, lastName=%s, email=%s, getId()=%d]", username, firstName, lastName, email, getId());
	}
}
