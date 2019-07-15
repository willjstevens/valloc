/*
 * Property of Will Stevens
 * All rights reserved.
 */
package com.valloc;

import java.util.stream.Stream;

/**
 *
 *
 * 
 * @author wstevens
 */
public final class UrlConstants 
{
	private UrlConstants() {}

    public static final String HTTP_SCHEME                      = "http";
    public static final String HTTPS_SCHEME                     = "https";
    public static final String SCHEME_SEPARATOR                 = "://";
	public static final char URL_PATH_SEPARATOR_CHAR 			= '/';
	public static final char QUERY_STRING_SEPARATOR 			= '?';
	public static final char QUERY_STRING_ASSIGNMENT			= '=';
	public static final char QUERY_STRING_AND					= '&';
	public static final String JSP_SUFFIX						= ".jsp";
	public static final String URL_PATH_SEPARATOR 				= Character.toString(URL_PATH_SEPARATOR_CHAR);
	public static final String AJAX_URL_PATH					= URL_PATH_SEPARATOR_CHAR + "a";
	public static final String INTERCEPTOR_WILDCARD_SUFFIX		= URL_PATH_SEPARATOR_CHAR + "**";

    // Top-level Page paths
    public static final String HOME_PAGE                        = URL_PATH_SEPARATOR_CHAR + "";
    public static final String QUICK_START                      = URL_PATH_SEPARATOR_CHAR + "quickstart";
    public static final String IMPORT                           = URL_PATH_SEPARATOR_CHAR + "import";
    public static final String INVALID_BROWSER                  = URL_PATH_SEPARATOR_CHAR + "invalidbrowser";
    public static final String PRIVACY_POLICY                   = URL_PATH_SEPARATOR_CHAR + "privacypolicy";
    public static final String TERMS_OF_SERVICE                 = URL_PATH_SEPARATOR_CHAR + "termsofservice";
    public static final String HOW_IT_WORKS                     = URL_PATH_SEPARATOR_CHAR + "howitworks";
    public static final String FEEDBACK                         = URL_PATH_SEPARATOR_CHAR + "feedback";
    public static final String FEEDBACK_POST                    = AJAX_URL_PATH + FEEDBACK;
    public static final String SERVE_PAGE                       = URL_PATH_SEPARATOR_CHAR + "srv";
    public static final String PIN_LINK                         = URL_PATH_SEPARATOR_CHAR + "pinlink";
    public static final String PIN_LINK_POST                    = AJAX_URL_PATH + PIN_LINK;
    public static final String CONTACT_US_POST                  = AJAX_URL_PATH + URL_PATH_SEPARATOR_CHAR + "contactus"; // only a POST for this URL

	// Site Sections	
	public static final String ACCOUNT							= URL_PATH_SEPARATOR_CHAR + "account";
	public static final String ACCOUNT_CREATE					= ACCOUNT + URL_PATH_SEPARATOR_CHAR + "create";
	public static final String ACCOUNT_CREATE_POST				= AJAX_URL_PATH + ACCOUNT_CREATE;
	public static final String ACCOUNT_CREATE_COMPLETE			= ACCOUNT_CREATE + URL_PATH_SEPARATOR_CHAR + "complete";
	public static final String ACCOUNT_VERIFY					= ACCOUNT + URL_PATH_SEPARATOR_CHAR + "verify";
	public static final String ACCOUNT_VERIFY_SUCCESS			= ACCOUNT_VERIFY + URL_PATH_SEPARATOR_CHAR + "success";
	public static final String ACCOUNT_VERIFY_BAD_VERIFICATION	= ACCOUNT_VERIFY + URL_PATH_SEPARATOR_CHAR + "badverification";
	public static final String ACCOUNT_VERIFY_EXPIRED_CODE		= ACCOUNT_VERIFY + URL_PATH_SEPARATOR_CHAR + "expiredcode";
	public static final String ACCOUNT_VERIFY_COMPLETED			= ACCOUNT_VERIFY + URL_PATH_SEPARATOR_CHAR + "completed";		
	public static final String ACCOUNT_EDIT						= ACCOUNT + URL_PATH_SEPARATOR_CHAR + "edit";
	public static final String ACCOUNT_EDIT_POST				= AJAX_URL_PATH + ACCOUNT_EDIT;
	public static final String ACCOUNT_EDIT_LOAD				= AJAX_URL_PATH + ACCOUNT_EDIT + URL_PATH_SEPARATOR_CHAR + "loadAccount"; 

	public static final String DASHBOARD						= URL_PATH_SEPARATOR_CHAR + "dashboard";
	
	public static final String PAGE								= URL_PATH_SEPARATOR_CHAR + "page";
	public static final String PAGE_NEW							= PAGE + URL_PATH_SEPARATOR_CHAR + "new";
	public static final String PAGE_NEW_POST					= AJAX_URL_PATH + PAGE_NEW;		
	public static final String PAGE_EDIT						= PAGE + URL_PATH_SEPARATOR_CHAR + "edit";
    public static final String PAGE_EDIT_PATH_TOKEN             = "{path}";
    public static final String PAGE_EDIT_GET					= PAGE_EDIT + URL_PATH_SEPARATOR_CHAR + PAGE_EDIT_PATH_TOKEN;
    public static final String PAGE_EDIT_POST					= AJAX_URL_PATH + PAGE_EDIT;
    public static final String PAGE_REMOVE_PATH_TOKEN           = "{path}";
	public static final String PAGE_REMOVE_POST					= AJAX_URL_PATH +
                                                                        PAGE + URL_PATH_SEPARATOR_CHAR +
                                                                        "remove" + URL_PATH_SEPARATOR_CHAR +
                                                                        PAGE_REMOVE_PATH_TOKEN;
    public static final String PAGE_GUEST_EDIT                  = PAGE + URL_PATH_SEPARATOR_CHAR + "guestedit";
    public static final String PAGE_GUEST_EDIT_GET              = PAGE_GUEST_EDIT +
                                                                        URL_PATH_SEPARATOR_CHAR +
                                                                        "{username}" +
                                                                        URL_PATH_SEPARATOR_CHAR +
                                                                        "{path}";
    public static final String PAGE_GUEST_EDIT_POST             = AJAX_URL_PATH + PAGE_GUEST_EDIT_GET;
    public static final String PAGE_GUEST_SEARCH      			= AJAX_URL_PATH + URL_PATH_SEPARATOR_CHAR + "searchForPageGuest";
    public static final String PAGE_GUESTS_POPUP                = PAGE + URL_PATH_SEPARATOR_CHAR + "pageGuests";
    public static final String PAGE_GUESTS_POPUP_GET            = PAGE_GUESTS_POPUP + URL_PATH_SEPARATOR_CHAR + "new";
    public static final String PAGE_GUESTS_POPUP_EDIT           = PAGE_GUESTS_POPUP + URL_PATH_SEPARATOR_CHAR + "edit";
    public static final String PAGE_DIRECT_EDIT                  = PAGE + URL_PATH_SEPARATOR_CHAR + "directedit";
    public static final String PAGE_DIRECT_EDIT_GET              = PAGE_DIRECT_EDIT +
                                                                        URL_PATH_SEPARATOR_CHAR +
                                                                        "{username}" +
                                                                        URL_PATH_SEPARATOR_CHAR +
                                                                        "{path}";

    public static final String LOGIN							= URL_PATH_SEPARATOR_CHAR + "login"; 
    public static final String LOGIN_POST						= AJAX_URL_PATH + LOGIN; 
	public static final String LOGOUT                           = URL_PATH_SEPARATOR_CHAR + "logout";

	// Misc JSP include paths
	public static final String JSP_ROOT						    = "/WEB-INF/jsp";
	public static final String PAGE_BUILDER					    = JSP_ROOT + URL_PATH_SEPARATOR_CHAR + "builder" + JSP_SUFFIX;
	public static final String EMAIL_ROOT					    = JSP_ROOT + URL_PATH_SEPARATOR_CHAR + "email";
	public static final String EMAIL_ACCOUNT_VERIFICATION	    = EMAIL_ROOT + URL_PATH_SEPARATOR_CHAR + "email-verification" + JSP_SUFFIX;

    // General Misc paths
    public static final String LOAD_APP_DATA                    = AJAX_URL_PATH + URL_PATH_SEPARATOR_CHAR + "loadAppData";
    public static final String RESOURCES_DIR                    = URL_PATH_SEPARATOR_CHAR + "r";
    public static final String RESOURCES_DIR_WILDCARD           = RESOURCES_DIR + URL_PATH_SEPARATOR_CHAR + INTERCEPTOR_WILDCARD_SUFFIX;
    public static final String IMAGE_DIR                        = RESOURCES_DIR + URL_PATH_SEPARATOR_CHAR + "i";
    public static final String FAVICON                          = "favicon.ico";
    public static final String OAUTH2_CALLBACK                  = "oauth2callback";
    public static final String LOGO_BANNER                      = IMAGE_DIR + URL_PATH_SEPARATOR_CHAR + "banner.jpg";

    // Interceptor Patterns and Exclusions patterns
    public static final String[] REGISTRATION_PATTERNS = {
            ACCOUNT_CREATE +           INTERCEPTOR_WILDCARD_SUFFIX,
            ACCOUNT_CREATE_POST +      INTERCEPTOR_WILDCARD_SUFFIX,
            ACCOUNT_CREATE_COMPLETE +  INTERCEPTOR_WILDCARD_SUFFIX,
            ACCOUNT_VERIFY +           INTERCEPTOR_WILDCARD_SUFFIX
    };
    public static final String[] SECURE_INTERCEPTOR_PATTERNS = {
            QUICK_START +           INTERCEPTOR_WILDCARD_SUFFIX,
            LOGIN + 		        INTERCEPTOR_WILDCARD_SUFFIX,
            ACCOUNT_EDIT + 	        INTERCEPTOR_WILDCARD_SUFFIX,
            ACCOUNT_EDIT_POST + 	INTERCEPTOR_WILDCARD_SUFFIX,
            DASHBOARD + 	        INTERCEPTOR_WILDCARD_SUFFIX,
            PAGE +                  INTERCEPTOR_WILDCARD_SUFFIX,
            FEEDBACK +              INTERCEPTOR_WILDCARD_SUFFIX,
            PIN_LINK +              INTERCEPTOR_WILDCARD_SUFFIX,
            AJAX_URL_PATH +         INTERCEPTOR_WILDCARD_SUFFIX,
            PAGE_DIRECT_EDIT +      INTERCEPTOR_WILDCARD_SUFFIX
    };
//    public static final String[] SECURE_INTERCEPTOR_EXCLUSION_PATTERNS =
//            Util.concat(REGISTRATION_PATTERNS,
//                        new String[] {
//                            LOAD_APP_DATA  +    INTERCEPTOR_WILDCARD_SUFFIX,
//                            FAVICON,
//                            CONTACT_US_POST
//                        }
//                    );
    public static final String[] SECURE_INTERCEPTOR_EXCLUSION_PATTERNS =
        Stream.of(
                REGISTRATION_PATTERNS,
                new String[] {
                        LOAD_APP_DATA  +    INTERCEPTOR_WILDCARD_SUFFIX,
                        FAVICON,
                        CONTACT_US_POST,
                        LOGIN_POST
                }
        ).flatMap(Stream::of).toArray(String[]::new);



    public static final String[] APPLICATION_REQUEST_INTERCEPTOR_PATTERNS = {
            URL_PATH_SEPARATOR_CHAR + INTERCEPTOR_WILDCARD_SUFFIX
    };
    public static final String[] APPLICATION_REQUEST_INTERCEPTOR_EXCLUSION_PATTERNS = {
            RESOURCES_DIR_WILDCARD,
            AJAX_URL_PATH + URL_PATH_SEPARATOR + INTERCEPTOR_WILDCARD_SUFFIX,
            FAVICON
    };
    public static final String[] OAUTH_INTERCEPTOR_PATTERNS = {
            OAUTH2_CALLBACK + INTERCEPTOR_WILDCARD_SUFFIX
    };
}
