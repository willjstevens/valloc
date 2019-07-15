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
public class PageGuestResultDto 
{
	private String firstName;
	private String lastName;
	private String username;
	private boolean canModify;
	
	public PageGuestResultDto() {}

	public PageGuestResultDto(String firstName, String lastName, String username, boolean canModify) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
		this.canModify = canModify;
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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public boolean canModify() {
		return canModify;
	}

	public void setCanModify(boolean canModify) {
		this.canModify = canModify;
	}

	
}
