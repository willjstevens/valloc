/**
 * 
 */
package com.valloc.service;

import com.valloc.Category;
import com.valloc.io.IoUtil;
import com.valloc.log.LogManager;
import com.valloc.log.Logger;
import com.valloc.serve.PageServeService;
import com.valloc.serve.PageServeServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;


/**
 * Configuration for the application services.
 * 
 * @author wstevens
 */
@Configuration
public class ServiceWiringConfig 
{
    private static final Logger logger = LogManager.manager().newLogger(ConfigurationServiceImpl.class, Category.SERVICE);

	@Bean(name="accountService")
	@DependsOn("configurationService")
	public AccountService accountService() {
		return new AccountServiceImpl();
	}
	
	@Bean(name="configurationService")
	public ConfigurationService configurationService() {
		return new ConfigurationServiceImpl();
	}
	
	@Bean(name="systemService")
	@DependsOn("configurationService")
	public SystemService systemService() {
		return new SystemServiceImpl();
	}

	@Bean(name="utilityService")
	public UtilityService utilityService() {
		return new UtilityServiceImpl();
	}

	@Bean(name="adminService")
	public AdminService adminService() {
		return new AdminServiceImpl();
	}

	@Bean(name="applicationService")
	public ApplicationService applicationService() {
		return new ApplicationServiceImpl();
	}

	@Bean(name="validationService")
	public ValidationService validationService() {
		return new ValidationServiceImpl();
	}

	@Bean(name="dashboardService")
	public DashboardService dashboardService() {
		return new DashboardServiceImpl();
	}
	
	@Bean(name="lifeCycleService")
	@DependsOn({"configurationService", "systemService", "adminService", "applicationService"})
	public LifeCycleService lifeCycleService() {
		return new LifeCycleServiceImpl();
	}
	
	@Bean(name="pageServeService")
	public PageServeService pageServeService() {
		return new PageServeServiceImpl(); 
	}

    @Bean(name="ioUtil")
    public IoUtil ioUtil() {
        return new IoUtil();
    }
}
