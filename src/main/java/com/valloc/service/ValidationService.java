package com.valloc.service;


/**
 * Typical service with miscellaneous operations.
 *
 * @author wstevens
 */
public interface ValidationService
{	
	public void assertNotNull(String value);
	public void assertNotNullAndNotEmpty(String value);
	public boolean isNull(String value);
	public boolean isNotNull(String value);
	public boolean isEmpty(String value);
	public boolean isNotEmpty(String value);
	public boolean isNullOrEmpty(String value);
	public boolean isNotNullAndNotEmpty(String value);
	public boolean isValidSize(String value, int minSize, int maxSize);
	public boolean isUnderMinSize(String value, int minSize);
	public boolean isOverMaxSize(String value, int maxSize);
	public boolean containsSpaces(String value);
	public boolean isValidFormat(String value, String regex);
	public boolean isValidEmailFormat(String email);
	public boolean isValidDateFormat(String dateString, String format);
}
