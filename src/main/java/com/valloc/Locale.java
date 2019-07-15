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
public enum Locale 
{
	EN_US (java.util.Locale.US);
	
	private java.util.Locale javaLocale;
	
	Locale(java.util.Locale javaLocale) {
		this.javaLocale = javaLocale;
	}
	
	public java.util.Locale javaLocale() {
		return javaLocale;
	}
}
