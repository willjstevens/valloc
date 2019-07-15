/*
 * Property of Will Stevens
 * All rights reserved.
 */
package com.valloc.web;


/**
 *
 *
 * 
 * @author wstevens
 */
public class AccountForm 
{
	public String username;
	public String email;
	public String firstName;
	public String lastName;
	public String birthdate;
	public String gender;
	public String password;
	public String passwordConfirmation;
	public String passwordQuestion;
	public String passwordAnswer;
	public boolean inAgreement;
	
	public AccountForm() {}

	public AccountForm(String username, String password, String passwordConfirmation, String passwordQuestion, String passwordAnswer) {
		this.username = username;
		this.password = password;
		this.passwordConfirmation = passwordConfirmation;
		this.passwordQuestion = passwordQuestion;
		this.passwordAnswer = passwordAnswer;
	}

	public AccountForm(String username, String email, String firstName,
					   String lastName, String password, boolean inAgreement) {
		this.username = username;
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
		this.password = password;
		this.inAgreement = inAgreement;
	}

    public AccountForm(String username, String email, String firstName,
                       String lastName, String password, String passwordConfirmation,
                       String passwordQuestion, String passwordAnswer, boolean inAgreement) {
        this.username = username;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.passwordConfirmation = passwordConfirmation;
        this.passwordQuestion = passwordQuestion;
        this.passwordAnswer = passwordAnswer;
        this.inAgreement = inAgreement;
    }

	public AccountForm(String username, String email, String firstName,
			String lastName, String birthdate, String gender, String password, String passwordConfirmation,
			String passwordQuestion, String passwordAnswer, boolean inAgreement) {
		this.username = username;
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
		this.birthdate = birthdate;
		this.gender = gender;
		this.password = password;
		this.passwordConfirmation = passwordConfirmation;
		this.passwordQuestion = passwordQuestion;
		this.passwordAnswer = passwordAnswer;
		this.inAgreement = inAgreement;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("AccountForm [username=");
		builder.append(username);
		builder.append(", email=");
		builder.append(email);
		builder.append(", firstName=");
		builder.append(firstName);
		builder.append(", lastName=");
		builder.append(lastName);
		builder.append(", birthdate=");
		builder.append(birthdate);
		builder.append(", gender=");
		builder.append(gender);
		builder.append(", password=");
		builder.append(password);
		builder.append(", passwordConfirmation=");
		builder.append(passwordConfirmation);
		builder.append(", passwordQuestion=");
		builder.append(passwordQuestion);
		builder.append(", passwordAnswer=");
		builder.append(passwordAnswer);
		builder.append(", inAgreement=");
		builder.append(inAgreement);
		builder.append("]");
		return builder.toString();
	}	
}
