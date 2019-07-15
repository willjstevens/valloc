/*
 * Property of Will Stevens
 * All rights reserved.
 */
package com.valloc;

/**
 *
 *
 * 
 * @author wstevens
 */
public enum Action 
{
	UNINITIALIZED 	("UNINITIALIZED"),
	ADD 			("add"),
	EDIT 			("edit"),
	REMOVE 			("remove"),
	CHANGE 			("change");
	
	private String id;
	
	private Action(String id) {
		this.id = id;
	}
	
	public String id() {
		return id;
	}
	
	public static Action toAction(String actionStr) {
		Action action = UNINITIALIZED;
		
		if (actionStr == null || actionStr.isEmpty()) {
			throw new IllegalArgumentException();
		}
		for (Action candidate : Action.values()) {
			if (candidate.id.equals(actionStr)) {
				action = candidate;
				break;
			}
		}
		
		return action;
	}
}
