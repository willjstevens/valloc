/**
 * 
 */
package com.valloc.dao;

import com.valloc.Category;
import com.valloc.Environment;
import com.valloc.log.LogManager;
import com.valloc.log.Logger;
import com.valloc.object.persistent.*;
import org.apache.commons.dbcp.BasicDataSource;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Properties;

/**
 * A configuration to load all necessary database related beans.
 * 
 * @author wstevens
 */
@Configuration
@EnableTransactionManagement
public class DaoWiringConfig 
{
	private static final Logger logger = LogManager.manager().newLogger(DaoWiringConfig.class, Category.DATABASE);

	@SuppressWarnings("rawtypes")
	private static final Class[] ANNOTATED_CLASSES = {
        PersistentApplicationError.class,
        PersistentApplicationRequest.class,
		PersistentColumn.class,
		PersistentConfig.class,
        PersistentFeedback.class,
		PersistentLink.class,
        PersistentLinkNote.class,
        PersistentLinkServe.class,
		PersistentPage.class,
		PersistentPageGuest.class,
		PersistentSection.class,
		PersistentSystemNotification.class,
		PersistentSystemNotificationHistory.class,
		PersistentUser.class,
		PersistentUserMessage.class
	};
	
	@Autowired private Environment environment;
	private LocalSessionFactoryBean factoryBean;
	private DataSource dataSource;

//	@Bean(name="dataSource", destroyMethod="close")
	@Bean(name="dataSource")
	@DependsOn("environment")
	public DataSource dataSource() {
		final BasicDataSource dataSource = new BasicDataSource();
		
		// 1. Yank database config string and parse
		final String databaseConfig = environment.getDatabaseConfig();
		URI databaseUri = null;
		try {
			databaseUri = new URI(databaseConfig);
		} catch (URISyntaxException e) {
			logger.error("Problem when loading database config string %s: %s", e, databaseConfig, e);
		}
		final String host = databaseUri.getHost();
		final int port = databaseUri.getPort();
		final String path = databaseUri.getPath();
		final String[] userInfo = databaseUri.getUserInfo().split(":");
		final String username = userInfo[0];
		final String password = userInfo[1];

		// add params for Heroku security check from 2/8/16, and ssl requirement from 2/1/18
		final String parameters = "sslmode=require&ssl=true&sslfactory=org.postgresql.ssl.NonValidatingFactory";
		if (logger.isFine()) {
			logger.fine("Parsed database info: host=%s, port=%d, path=%s, username=%s, password=%s, parameters=%s.",
					host, port, path, username, password, parameters);
		}
		
		// 2. Assemble database URL
//		final String databaseUrl = String.format("jdbc:postgresql://%s:%d%s", host, port, path);
		final String databaseUrl = String.format("jdbc:postgresql://%s:%d%s?%s", host, port, path, parameters);

		if (logger.isInfo()) {
			logger.info("Database URL: %s", databaseUrl);
		}

		// 3. Establish data source
		dataSource.setDriverClassName(org.postgresql.Driver.class.getName());
		dataSource.setUrl(databaseUrl);
		dataSource.setUsername(username);
		dataSource.setPassword(password);
		
		this.dataSource = dataSource;
		
		return dataSource;
	}

	@Bean(name="factoryBean")
	@DependsOn("dataSource")
	public LocalSessionFactoryBean factoryBean() {
		LocalSessionFactoryBean factoryBean = new LocalSessionFactoryBean();
		
		Properties hibernateProperties = new Properties();
		// These can be extracted to configuration somewhere...
		hibernateProperties.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
		hibernateProperties.put("hibernate.jdbc.batch_size", "10");
        hibernateProperties.put("hibernate.temp.use_jdbc_metadata_defaults", "false");

		hibernateProperties.put("hibernate.show_sql", "false");
//        hibernateProperties.put("hibernate.format_sql", "true");

		factoryBean.setAnnotatedClasses(ANNOTATED_CLASSES);
		factoryBean.setDataSource(dataSource);
		factoryBean.setHibernateProperties(hibernateProperties);

		this.factoryBean = factoryBean;
		
		return factoryBean;
	}

	@Bean(name="transactionManager")
	@DependsOn("factoryBean")
	public HibernateTransactionManager transactionManager() {
		SessionFactory sessionFactory = factoryBean.getObject();
		return new HibernateTransactionManager(sessionFactory);
	}
	
	@Bean(name="systemDao")
	public SystemDao systemDao() {
		return new SystemDaoImpl();
	}

	@Bean(name="accountDao")
	public AccountDao accountDao() {
		return new AccountDaoImpl();
	}

	@Bean(name="pageDao")
	public PageDao pageDao() {
		return new PageDaoImpl();
	}
}