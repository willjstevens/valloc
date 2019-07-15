/*
 * Property of Will Stevens
 * All rights reserved.
 */
package com.valloc.object.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.valloc.object.domain.User;
//import org.codehaus.jackson.annotate.JsonProperty;

/**
 *
 *
 * 
 * @author wstevens
 */
public class AccountDto
{
    
    public AccountDto() {}
    
    public AccountDto(String username, String email, String firstName,
			String lastName, String gender, String birthdate, String password,
			String passwordConfirmation, String passwordQuestion,
			String passwordAnswer) {
		this.username = username;
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
		this.gender = gender;
		this.birthdate = birthdate;
		this.password = password;
		this.passwordConfirmation = passwordConfirmation;
		this.passwordQuestion = passwordQuestion;
		this.passwordAnswer = passwordAnswer;
	}

    
	@JsonProperty(value="username")
	public String username;

    @JsonProperty(value="email")
	public String email;

    @JsonProperty(value="firstName")
	public String firstName;

    @JsonProperty(value="lastName")
	public String lastName;

    @JsonProperty(value="gender")
	public String gender;

    @JsonProperty(value="birthdate")
	public String birthdate;

    @JsonProperty(value="password")
	public String password;

    @JsonProperty(value="passwordConfirmation")
	public String passwordConfirmation;

    @JsonProperty(value="passwordQuestion")
	public String passwordQuestion;

    @JsonProperty(value="passwordAnswer")
	public String passwordAnswer;

    @JsonProperty(value="inAgreement")
	public boolean inAgreement;
    
    public static AccountDto userToAccountDto(User user) {
    	AccountDto dto = new AccountDto();
    	dto.username = user.getUsername();
    	dto.email = user.getEmail();
    	dto.firstName = user.getFirstName();
    	dto.lastName = user.getLastName();
    	dto.birthdate = "OVERRIDEME";
    	dto.gender = user.getGender();
    	dto.password = user.getPassword();
    	dto.passwordConfirmation = user.getPassword();
    	dto.passwordQuestion = user.getPasswordQuestion();
    	dto.passwordAnswer = user.getPasswordAnswer();
    	return dto;
    }
    
	@Override
    public String toString() {
    	StringBuilder builder = new StringBuilder();
    	builder.append("AccountDto [username=");
    	builder.append(username);
    	builder.append(", email=");
    	builder.append(email);
    	builder.append(", firstName=");
    	builder.append(firstName);
    	builder.append(", lastName=");
    	builder.append(lastName);
    	builder.append(", gender=");
    	builder.append(gender);
    	builder.append(", birthdate=");
    	builder.append(birthdate);
    	builder.append(", password=");
    	builder.append(password);
    	builder.append(", passwordConfirmation=");
    	builder.append(passwordConfirmation);
    	builder.append(", passwordQuestion=");
    	builder.append(passwordQuestion);
    	builder.append(", passwordAnswer=");
    	builder.append(passwordAnswer);
    	builder.append("]");
    	return builder.toString();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordConfirmation() {
        return passwordConfirmation;
    }

    public void setPasswordConfirmation(String passwordConfirmation) {
        this.passwordConfirmation = passwordConfirmation;
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

    public boolean isInAgreement() {
        return inAgreement;
    }

    public void setInAgreement(boolean inAgreement) {
        this.inAgreement = inAgreement;
    }
}
