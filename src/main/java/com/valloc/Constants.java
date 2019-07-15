/*
 * Property of Will Stevens
 * All rights reserved.
 */
package com.valloc;

import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 *
 *
 * 
 * @author wstevens
 */
public final class Constants 
{
	private Constants() {}

	public static final String VALLOC_COM				    = "valloc.com";
    public static final String OWNER_EMAIL                  = "willjstevens@gmail.com";
	public static final String CHARSET_UTF_8_STR		    = "UTF-8";
	public static final String DEFAULT_CHARSET_STR		    = CHARSET_UTF_8_STR;
	public static final Charset CHARSET_UTF_8 			    = Charset.forName(CHARSET_UTF_8_STR);
	public static final Charset DEFAULT_CHARSET			    = CHARSET_UTF_8;
    public static final String REPLACEMENT_TOKEN_0 		    = "{0}";
    public static final String REPLACEMENT_TOKEN_1 		    = "{1}";
    public static final String REPLACEMENT_TOKEN_2 		    = "{2}";
    public static final String REPLACEMENT_TOKEN_3 		    = "{3}";
    public static final String REPLACEMENT_TOKEN_4 		    = "{4}";
	public static final String TARGET_URL 				    = "targetUrl";
	public static final String COOKIE_SESSION_KEY 		    = "vsk";
	public static final String SECURE_SCHEME			    = "https";
	public static final String LOGIN_PROCESS_FLAG 		    = "loginProcess";
	public static final String VSESSION_KEY				    = "vsession";
	public static final String PAGE_LOAD_DATA               = "pld";
    public static final char DOT                            = '.';
    public static final char FORWARD_SLASH                  = '/';
    public static final String FORWARD_SLASH_STR            = Character.toString(FORWARD_SLASH);
    public static final DateFormat PRINTED_TIMESTAMP_FORMAT = new SimpleDateFormat("MM-dd-yyyy hh:mm:ss a");
    public static final DateFormat CONVERSATION_TS_FORMAT   = new SimpleDateFormat("EEEEEEE, MMMM d h:mm a");

    public static final String[] USERNAME_EXCLUSIONS = {
            "account",
            "dashboard",
            "page",
            "login",
            "logout",
            "feedback",
            "import",
            "quickstart",
            "valloc",
            "trending",
            "jen",
            "news",
            "dev",
            "development"
    };
}
