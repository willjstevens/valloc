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
public final class SpecConstants
{
	private SpecConstants() {}

	public static final String SEPARATOR 							= "_";
	public static final String SPEC 								= "spec";
	public static final String SPEC_PREFIX 							= SPEC + SEPARATOR;
	public static final String MIN_SIZE								= "minSize";
	public static final String MAX_SIZE								= "maxSize";
	public static final String PATTERN								= "pattern";
	public static final String PATTERN_PREFIX						= PATTERN + SEPARATOR;
	
	/*
	 * Config related
	 */
	private static final String CONFIG 								= "config";
	public static final String CONFIG_VAL_MAX_SIZE_KEY				= CONFIG + SEPARATOR + "valMaxSize";
	public static final int CONFIG_VAL_MAX_SIZE_VALUE				= 2000;
	
	/*
	 * SystemNotification related
	 */
	private static final String SYSTEM_NOTIF						= "systemNotification";
	public static final String SYSTEM_NOTIF_DATA_MAX_SIZE_KEY		= SYSTEM_NOTIF + SEPARATOR + "dataMaxSize";
	public static final int SYSTEM_NOTIF_DATA_MAX_SIZE_VALUE		= 2000;
	
	/*
	 * UserMessage related
	 */
	public static final String USER_MESSAGE_MESSAGE_MAX_SIZE_KEY	= "messageMaxSize";
	public static final int USER_MESSAGE_MESSAGE_MAX_SIZE_VALUE		= 2000;
	// Account form (create and edit)
	public static final String ACCOUNT								= "account";
	public static final String ACCOUNT_PREFIX						= ACCOUNT + SEPARATOR;
	//	Username
	public static final String USERNAME								= "username";
	public static final String USERNAME_PREFIX						= ACCOUNT_PREFIX + USERNAME + SEPARATOR;
	public static final String USERNAME_MIN_SIZE_KEY				= USERNAME_PREFIX + SPEC_PREFIX + MIN_SIZE;
	public static final int USERNAME_MIN_SIZE_VALUE					= 3;
	public static final String USERNAME_MAX_SIZE_KEY				= USERNAME_PREFIX + SPEC_PREFIX + MAX_SIZE;
	public static final int USERNAME_MAX_SIZE_VALUE					= 15;
	public static final String USERNAME_PATTERN_KEY					= USERNAME_PREFIX + PATTERN;
	public static final String USERNAME_PATTERN_VALUE				= "^[a-zA-Z0-9]*$";
	//  Email
	public static final String EMAIL								= "email";
	public static final String EMAIL_PREFIX							= ACCOUNT_PREFIX + EMAIL + SEPARATOR;
	public static final String EMAIL_MIN_SIZE_KEY					= EMAIL_PREFIX + SPEC_PREFIX + MIN_SIZE;
	public static final int EMAIL_MIN_SIZE_VALUE					= 8;
	public static final String EMAIL_MAX_SIZE_KEY					= EMAIL_PREFIX + SPEC_PREFIX + MAX_SIZE;
	public static final int EMAIL_MAX_SIZE_VALUE					= 50;
	// Shared first/last name values
	public static final String NAME									= "name";
	public static final String NAME_PREFIX							= ACCOUNT_PREFIX + NAME + SEPARATOR;
	public static final String NAME_PATTERN_KEY						= NAME_PREFIX + PATTERN;
	public static final String NAME_PATTERN_VALUE					= "[a-zA-Z\\s']+";	
	//  First Name
	public static final String FIRST_NAME							= "firstName";
	public static final String FIRST_NAME_PREFIX					= ACCOUNT_PREFIX + FIRST_NAME + SEPARATOR;
	public static final String FIRST_NAME_MIN_SIZE_KEY				= FIRST_NAME_PREFIX + SPEC_PREFIX + MIN_SIZE;
	public static final int FIRST_NAME_MIN_SIZE_VALUE				= 3;
	public static final String FIRST_NAME_MAX_SIZE_KEY				= FIRST_NAME_PREFIX + SPEC_PREFIX + MAX_SIZE;
	public static final int FIRST_NAME_MAX_SIZE_VALUE				= 25;
	//  Last Name
	public static final String LAST_NAME							= "lastName";
	public static final String LAST_NAME_PREFIX						= ACCOUNT_PREFIX + LAST_NAME + SEPARATOR;
	public static final String LAST_NAME_MIN_SIZE_KEY				= LAST_NAME_PREFIX + SPEC_PREFIX + MIN_SIZE;
	public static final int LAST_NAME_MIN_SIZE_VALUE				= 3;
	public static final String LAST_NAME_MAX_SIZE_KEY				= LAST_NAME_PREFIX + SPEC_PREFIX + MAX_SIZE;
	public static final int LAST_NAME_MAX_SIZE_VALUE				= 25;
	// Birthdate
	public static final String BIRTHDATE							= "birthdate";
	public static final String BIRTHDATE_PREFIX						= ACCOUNT_PREFIX + BIRTHDATE + SEPARATOR;
	public static final String BIRTHDATE_PATTERN					= BIRTHDATE_PREFIX + PATTERN_PREFIX;
	public static final String BIRTHDATE_PATTERN_CLIENT_KEY			= BIRTHDATE_PATTERN + "client";
	public static final String BIRTHDATE_PATTERN_CLIENT_VALUE		= "FIXME"; // /\d{1,2}/\d{1,2}/\d{4}/
	public static final String BIRTHDATE_PATTERN_SERVER_KEY			= BIRTHDATE_PATTERN + "server";
	public static final String BIRTHDATE_PATTERN_SERVER_VALUE		= "MM/dd/yyyy";
	//  Password and Password Confirmation
	public static final String PASSWORD								= "password";
	public static final String PASSWORD_PREFIX						= ACCOUNT_PREFIX + PASSWORD + SEPARATOR;
	public static final String PASSWORD_MIN_SIZE_KEY				= PASSWORD_PREFIX + SPEC_PREFIX + MIN_SIZE;
	public static final int PASSWORD_MIN_SIZE_VALUE					= 6;
	public static final String PASSWORD_MAX_SIZE_KEY				= PASSWORD_PREFIX + SPEC_PREFIX + MAX_SIZE;
	public static final int PASSWORD_MAX_SIZE_VALUE					= 10;
	public static final String PASSWORD_PATTERN_KEY					= PASSWORD_PREFIX + PATTERN;
	public static final String PASSWORD_PATTERN_VALUE				= "/^[a-zA-Z0-9$*!@#]+$/"; // /^[a-zA-Z0-9\$\*\!\@\#]+$/
	//  Password Question
	public static final String PASSWORD_QUESTION					= "passwordQuestion";
	public static final String PASSWORD_QUESTION_PREFIX				= ACCOUNT_PREFIX + PASSWORD_QUESTION + SEPARATOR;
	public static final String PASSWORD_QUESTION_MIN_SIZE_KEY		= PASSWORD_QUESTION_PREFIX + SPEC_PREFIX + MIN_SIZE;
	public static final int PASSWORD_QUESTION_MIN_SIZE_VALUE		= 5;
	public static final String PASSWORD_QUESTION_MAX_SIZE_KEY		= PASSWORD_QUESTION_PREFIX + SPEC_PREFIX + MAX_SIZE;
	public static final int PASSWORD_QUESTION_MAX_SIZE_VALUE		= 100;
	//  Password Answer
	public static final String PASSWORD_ANSWER						= "passwordAnswer";
	public static final String PASSWORD_ANSWER_PREFIX				= ACCOUNT_PREFIX + PASSWORD_ANSWER + SEPARATOR;
	public static final String PASSWORD_ANSWER_MIN_SIZE_KEY			= PASSWORD_ANSWER_PREFIX + SPEC_PREFIX + MIN_SIZE;
	public static final int PASSWORD_ANSWER_MIN_SIZE_VALUE			= 5;
	public static final String PASSWORD_ANSWER_MAX_SIZE_KEY			= PASSWORD_ANSWER_PREFIX + SPEC_PREFIX + MAX_SIZE;
	public static final int PASSWORD_ANSWER_MAX_SIZE_VALUE			= 100;
	
	/*
	 * Designer related
	 */
    // Account form (create and edit)
    public static final String DESIGNER								= "designer";
    public static final String DESIGNER_PREFIX						= DESIGNER + SEPARATOR;
    // Page name
    public static final String PAGE_NAME                            = "pageName";
    public static final String PAGE_NAME_PREFIX				        = DESIGNER_PREFIX + PAGE_NAME + SEPARATOR;
    public static final String PAGE_NAME_MIN_SIZE_KEY               = PAGE_NAME_PREFIX + SPEC_PREFIX + MIN_SIZE;
    public static final int PAGE_NAME_MIN_SIZE_VALUE                = 1;
    public static final String PAGE_NAME_MAX_SIZE_KEY               = PAGE_NAME_PREFIX + SPEC_PREFIX + MAX_SIZE;
    public static final int PAGE_NAME_MAX_SIZE_VALUE                = 25;
    // Page path
    public static final String PAGE_PATH                            = "pagePath";
    public static final String PAGE_PATH_PREFIX				        = DESIGNER_PREFIX + PAGE_PATH + SEPARATOR;
    public static final String PAGE_PATH_MIN_SIZE_KEY               = PAGE_PATH_PREFIX + SPEC_PREFIX + MIN_SIZE;
    public static final int PAGE_PATH_MIN_SIZE_VALUE                = 1;
    public static final String PAGE_PATH_MAX_SIZE_KEY               = PAGE_PATH_PREFIX + SPEC_PREFIX + MAX_SIZE;
    public static final int PAGE_PATH_MAX_SIZE_VALUE                = 50;
    // Page description
    public static final String PAGE_DESCRIPTION                     = "pageDescription";
    public static final String PAGE_DESCRIPTION_PREFIX				= DESIGNER_PREFIX + PAGE_DESCRIPTION + SEPARATOR;
    public static final String PAGE_DESCRIPTION_MIN_SIZE_KEY        = PAGE_DESCRIPTION_PREFIX + SPEC_PREFIX + MIN_SIZE;
    public static final int PAGE_DESCRIPTION_MIN_SIZE_VALUE         = 1;
    public static final String PAGE_DESCRIPTION_MAX_SIZE_KEY        = PAGE_DESCRIPTION_PREFIX + SPEC_PREFIX + MAX_SIZE;
    public static final int PAGE_DESCRIPTION_MAX_SIZE_VALUE         = 2000;

    // Column related
    public static final String COLUMN_COUNT                         = "columnCount";
    public static final int COLUMN_COUNT_VALUE                      = 3;

    // Section name
    public static final String SECTION_NAME                         = "sectionName";
    public static final String SECTION_NAME_PREFIX				    = DESIGNER_PREFIX + SECTION_NAME + SEPARATOR;
    public static final String SECTION_NAME_MIN_SIZE_KEY            = SECTION_NAME_PREFIX + SPEC_PREFIX + MIN_SIZE;
    public static final int SECTION_NAME_MIN_SIZE_VALUE             = 1;
    public static final String SECTION_NAME_MAX_SIZE_KEY            = SECTION_NAME_PREFIX + SPEC_PREFIX + MAX_SIZE;
    public static final int SECTION_NAME_MAX_SIZE_VALUE             = 100;
    // Section note
    public static final String SECTION_NOTE                         = "sectionNote";
    public static final String SECTION_NOTE_PREFIX				    = DESIGNER_PREFIX + SECTION_NOTE + SEPARATOR;
    public static final String SECTION_NOTE_MIN_SIZE_KEY            = SECTION_NOTE_PREFIX + SPEC_PREFIX + MIN_SIZE;
    public static final int SECTION_NOTE_MIN_SIZE_VALUE             = 1;
    public static final String SECTION_NOTE_MAX_SIZE_KEY            = SECTION_NOTE_PREFIX + SPEC_PREFIX + MAX_SIZE;
    public static final int SECTION_NOTE_MAX_SIZE_VALUE             = 2000;
    // Link name
    public static final String LINK_NAME                            = "linkName";
    public static final String LINK_NAME_PREFIX				        = DESIGNER_PREFIX + LINK_NAME + SEPARATOR;
    public static final String LINK_NAME_MIN_SIZE_KEY               = LINK_NAME_PREFIX + SPEC_PREFIX + MIN_SIZE;
    public static final int LINK_NAME_MIN_SIZE_VALUE                = 1;
    public static final String LINK_NAME_MAX_SIZE_KEY               = LINK_NAME_PREFIX + SPEC_PREFIX + MAX_SIZE;
    public static final int LINK_NAME_MAX_SIZE_VALUE                = 100;
    // Link URL
    public static final String LINK_URL                             = "linkUrl";
    public static final String LINK_URL_PREFIX				        = DESIGNER_PREFIX + LINK_URL + SEPARATOR;
    public static final String LINK_URL_MIN_SIZE_KEY                = LINK_URL_PREFIX + SPEC_PREFIX + MIN_SIZE;
    public static final int LINK_URL_MIN_SIZE_VALUE                 = 1;
    public static final String LINK_URL_MAX_SIZE_KEY                = LINK_URL_PREFIX + SPEC_PREFIX + MAX_SIZE;
    public static final int LINK_URL_MAX_SIZE_VALUE                 = 2500;
    // Link note
    public static final String LINK_NOTE                            = "linkNote";
    public static final String LINK_NOTE_PREFIX				        = DESIGNER_PREFIX + LINK_NOTE + SEPARATOR;
    public static final String LINK_NOTE_MIN_SIZE_KEY               = LINK_NOTE_PREFIX + SPEC_PREFIX + MIN_SIZE;
    public static final int LINK_NOTE_MIN_SIZE_VALUE                = 1;
    public static final String LINK_NOTE_MAX_SIZE_KEY               = LINK_NOTE_PREFIX + SPEC_PREFIX + MAX_SIZE;
    public static final int LINK_NOTE_MAX_SIZE_VALUE                = 2000;

    /*
     * Feedback related
     */
    public static final String FEEDBACK								= "feedback";
    public static final String FEEDBACK_PREFIX						= FEEDBACK + SEPARATOR;
    //	Name
    public static final String FEEDBACK_NAME						= "name";
    public static final String FEEDBACK_NAME_PREFIX					= FEEDBACK_PREFIX + FEEDBACK_NAME + SEPARATOR;
    public static final String FEEDBACK_NAME_MAX_SIZE_KEY			= FEEDBACK_NAME_PREFIX + SPEC_PREFIX + MAX_SIZE;
    public static final int FEEDBACK_NAME_MAX_SIZE_VALUE			= 25;
    //  Email
    public static final String FEEDBACK_EMAIL						= "email";
    public static final String FEEDBACK_EMAIL_PREFIX				= FEEDBACK_PREFIX + FEEDBACK_EMAIL + SEPARATOR;
    public static final String FEEDBACK_EMAIL_MAX_SIZE_KEY			= FEEDBACK_EMAIL_PREFIX + SPEC_PREFIX + MAX_SIZE;
    public static final int FEEDBACK_EMAIL_MAX_SIZE_VALUE			= 50;
    //  Note
    public static final String FEEDBACK_COMMENT						= "comment";
    public static final String FEEDBACK_COMMENT_PREFIX				= FEEDBACK_PREFIX + FEEDBACK_COMMENT + SEPARATOR;
    public static final String FEEDBACK_COMMENT_MAX_SIZE_KEY		= FEEDBACK_COMMENT_PREFIX + SPEC_PREFIX + MAX_SIZE;
    public static final int FEEDBACK_COMMENT_MAX_SIZE_VALUE			= 2000;

}
