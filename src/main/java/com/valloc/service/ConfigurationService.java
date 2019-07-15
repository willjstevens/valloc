/*
 * Property of Will Stevens
 * All rights reserved.
 */
package com.valloc.service;

import com.valloc.Category;
import com.valloc.log.Level;

import java.util.Date;

/**
 * 
 *
 * @author wstevens
 */
public interface ConfigurationService extends VallocService
{
	public void init();
	public void load();
	public void update(Category category, String key, String value);
	
	public Date getAppDataPublishDate();
	public Level getLogLevel();
	public int getSystemServiceScheduledExecutorDelay();
	public int getAccountServiceVerificationPeriodAllowanceDays();
	public boolean isDevEnvironment();
	public boolean isProdEnvironment();
	public String getEmailConnectionString();
	public String getSiteDomainHttpString();
	public String getSecureSiteDomainHttpsString();
    public String getPageFileDir();
    public int getCookieExpirySeconds();
    public String getProviderSecureRequestHeaderName();
    public String getOAuthClientId();
    public String getOAuthClientSecret();
}
