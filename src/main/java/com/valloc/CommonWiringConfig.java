package com.valloc;

import com.valloc.framework.SessionManager;
import com.valloc.log.Level;
import com.valloc.log.LogManager;
import com.valloc.log.Logger;
import com.valloc.object.ObjectConverter;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.support.ResourceBundleMessageSource;

import java.util.UUID;

/**
 * A configuration which is imported by all tier configurations (web, service, dao); this allows 
 * for common loading when breaking tiers apart for unit tests.
 * 
 * @author wstevens
 */
@Configuration
public class CommonWiringConfig 
{
	private static final Logger logger = LogManager.manager().newLogger(CommonWiringConfig.class, Category.FRAMEWORK);
	private static final String ENV_VAR_LOG_INITLEVEL 	= "valloc_log_initlevel";
	private static final String ENV_VAR_DB_CONFIG		= "valloc_db_config";
	private static final String MESSAGES_DIR 			= "message";
	private static final String[] messages = {
		MESSAGES_DIR + '/' + "common"
	};
	
	@Bean(name="messageSource")
	@DependsOn({"environment"})
	public MessageSource messageSource() {
		ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
		messageSource.setBasenames(messages);
		return messageSource;
	}
	
	@Bean(name="environment")
	public Environment environment() {
		Environment environment = new CloudEnvironment();
		
		final String initLogLevel = System.getenv(ENV_VAR_LOG_INITLEVEL);
		logger.info("Init log level config %s is set to \"%s\"", ENV_VAR_LOG_INITLEVEL, initLogLevel);
		if (initLogLevel != null) {
			environment.setInitLogLevel(initLogLevel.toUpperCase());
			LogManager.manager().setLevel(Level.toLogLevel(environment.getInitLogLevel()));
		} else {
			System.out.println(String.format("Could NOT find critical initLogLevel value for configuration %s.", ENV_VAR_LOG_INITLEVEL));
		}
		
		final String databaseConfig = System.getenv(ENV_VAR_DB_CONFIG);
		if (logger.isFine()) {
			logger.fine("Init database config %s is set to \"%s\"", ENV_VAR_DB_CONFIG, databaseConfig);
		}
		if (databaseConfig != null) {
			environment.setDatabaseConfig(databaseConfig);
		} else {
			System.out.println(String.format("Could NOT find critical database config value for configuration %s.", ENV_VAR_DB_CONFIG));
		}
		
		String instanceId = UUID.randomUUID().toString();
		environment.setInstanceId(instanceId);
		if (logger.isInfo()) {
			logger.info("Container instance ID is %s.", instanceId);
		}
		return environment;
	}
	
	@Bean(name="sessionManager")
	public SessionManager sessionManager() {
		return new SessionManager();
	}

	@Bean(name="objectConverter")
	public ObjectConverter objectConverter() {
		return new ObjectConverter();
	}
}
