/*
 * Property of Will Stevens
 * All rights reserved.
 */
package com.valloc.object.dto;


/**
 *
 *
 * 
 * @author wstevens
 */
public class PageGuestDto // does NOT implement PageGuest
{
	private String username;
	private String firstName;
	private String lastName;
	private boolean canModify;
	
	public PageGuestDto() {}

    public PageGuestDto(String username, String firstName, String lastName) {
        this(username, firstName, lastName, false);
    }

	public PageGuestDto(String username, String firstName, String lastName, boolean canModify) {
		this.username = username;
		this.firstName = firstName;
		this.lastName = lastName;
		this.canModify = canModify;
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

	public boolean getCanModify() {
		return canModify;
	}

	public void setCanModify(boolean canModify) {
		this.canModify = canModify;
	}
}
