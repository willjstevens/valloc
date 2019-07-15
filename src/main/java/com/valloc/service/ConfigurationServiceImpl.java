/*
 * Property of Will Stevens
 * All rights reserved.
 */
package com.valloc.service;

import com.valloc.Category;
import com.valloc.Util;
import com.valloc.dao.SystemDao;
import com.valloc.log.Level;
import com.valloc.log.LogManager;
import com.valloc.log.Logger;
import com.valloc.object.persistent.PersistentConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static com.valloc.Category.*;

/**
 * 
 *
 * @author wstevens
 */
@Service
public class ConfigurationServiceImpl implements ConfigurationService
{
	private static final Logger logger = LogManager.manager().newLogger(ConfigurationServiceImpl.class, Category.CONFIGURATION);
	private static final String ENVIRONMENT_STR_DEV 	= "dev";
	private static final String ENVIRONMENT_STR_PROD	= "prod";
    private static final DateFormat appDataPublishFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS"); // 2013-09-26 11:47:15.634-05

	@Autowired private SystemDao systemDao;
	private int systemServiceScheduledExecutorDelay;
	private Level logLevel;
	private Date appDataPublishDate;
	private String environmentString;
	private String emailConnectionString;
	private String siteDomainHttpString;
	private String secureSiteDomainHttpsString;
	private int verificationPeriodAllowanceDays;
    private String pageFileDir;
    private int cookieExpirySeconds;
    private String providerSecureRequestHeaderName;
    private String oauthClientId;
    private String oauthClientSecret;
	
	@Override
	public void init() {
		load();
	}
	
	@Override
	public void load() {
		final List<PersistentConfig> allConfigs = systemDao.getAllConfigs();
		for (PersistentConfig config : allConfigs) {
			final Category category = config.getCategory();
			final String key = config.getKey();
			final String value = config.getValue();
			if (logger.isFiner()) {
				logger.finer("About to load config with key %s for category %s with value \"%s\".", key, category, value);
			}
			if (value == null) {
				throw new NullPointerException(String.format("Config value for key %s is null which is not allowed.", key));
			}
			if (category == CONTROLLER) {
				if (key.equals("cookieExpirySeconds")) {
					cookieExpirySeconds = Integer.parseInt(value);
				}
			} else if (category == SERVICE_SYSTEM) {
				if (key.equals("scheduledJobDelay")) {
					systemServiceScheduledExecutorDelay = Integer.parseInt(value);
				}
			} else if (category == SERVICE_ACCOUNT) {
				if (key.equals("verificationPeriodAllowanceDays")) {
					verificationPeriodAllowanceDays = Integer.parseInt(value);
				}
			} else if (category == LOG) {
				if (key.equals("level")) {
					logLevel = Level.toLogLevel(value);
                    if (logLevel != null) {
                        LogManager.manager().setLevel(logLevel);
                        logger.info("Set official log level to: %s", logLevel);
                    }
				}
			} else if (category == CLIENT) {
				if (key.equals("appDataPublishDate")) {
                    try {
                        appDataPublishDate = appDataPublishFormatter.parse(value);
                    } catch (ParseException e) {
                        appDataPublishDate = Util.now();
                        logger.error("Problem parsing app data publish string \"%s\". It will be explicitly set to a timestamp of now: %s.", e, value, appDataPublishDate.toString());
                    }
                }
			} else if (category == ENVIRONMENT) {
				if (key.equals("environment")) {
					environmentString = value.toLowerCase();
					if (!ENVIRONMENT_STR_DEV.equals(environmentString) && !ENVIRONMENT_STR_PROD.equals(environmentString)) {
						throw new IllegalArgumentException(String.format("Environment value %s is not valid.", environmentString));
					}
				} else if (key.equals("siteDomainHttp")) {
					siteDomainHttpString = value;
				} else if (key.equals("siteDomainHttps")) {
					secureSiteDomainHttpsString = value;
				} else if (key.equals("pageFileDir")) {
                    pageFileDir = value;
                } else if (key.equals("providerSecureRequestHeaderName")) {
                	providerSecureRequestHeaderName = value;
                }
			} else if (category == Category.UTILITY) {
				if (key.equals("emailConnectionString")) {
					emailConnectionString = value;
				}	
			} else if (category == Category.SECURITY_OAUTH) {
                if (key.equals("oauthClientId")) {
                    oauthClientId = value;
                } else if (key.equals("oauthClientSecret")) {
                    oauthClientSecret = value;
                }
            }
		}
	}

	@Override
	public void update(Category category, String key, String value) {
		PersistentConfig existingConfig = systemDao.findConfig(category, key);
		if (logger.isFine()) {
			logger.fine("About to change value for %s to \"%s\".", existingConfig, value);
		}
		existingConfig.setValue(value);
		systemDao.saveOrUpdate(existingConfig);
		if (logger.isInfo()) {
			logger.info("Changed config value to \"%s\" for %s.", value, existingConfig);
		}	
	}

	@Override
	public int getSystemServiceScheduledExecutorDelay() {
		return systemServiceScheduledExecutorDelay;
	}

	@Override
	public Level getLogLevel() {
		return logLevel;
	}

	@Override
	public Date getAppDataPublishDate() {
		return appDataPublishDate;
	}

	@Override
	public boolean isDevEnvironment() {
		return environmentString.equals(ENVIRONMENT_STR_DEV);
	}

	@Override
	public boolean isProdEnvironment() {
		return environmentString.equals(ENVIRONMENT_STR_PROD);
	}

	@Override
	public String getEmailConnectionString() {
		return emailConnectionString;
	}

	@Override
	public String getSiteDomainHttpString() {
		return siteDomainHttpString;
	}

	@Override
	public String getSecureSiteDomainHttpsString() {
		return secureSiteDomainHttpsString;
	}

	@Override
	public int getAccountServiceVerificationPeriodAllowanceDays() {
		return verificationPeriodAllowanceDays;
	}

    @Override
    public String getPageFileDir() {
        return pageFileDir;
    }

	@Override
	public int getCookieExpirySeconds() {
		return cookieExpirySeconds;
	}

	@Override
	public String getProviderSecureRequestHeaderName() {
		return providerSecureRequestHeaderName;
	}

    @Override
    public String getOAuthClientId() { return oauthClientId; }

    @Override
    public String getOAuthClientSecret() { return oauthClientSecret; }


}
