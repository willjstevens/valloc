/*
 * Property of Will Stevens
 * All rights reserved.
 */
package com.valloc.service;

import com.valloc.Category;
import com.valloc.log.LogManager;
import com.valloc.log.Logger;
import com.valloc.web.PageLoadController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 
 *
 * @author wstevens
 */
@Service
@Transactional
public class LifeCycleServiceImpl implements LifeCycleService, ApplicationListener<ContextRefreshedEvent>
{
	private static final Logger logger = LogManager.manager().newLogger(LifeCycleServiceImpl.class, Category.SERVICE_LIFECYCLE);
	@Autowired private SystemService systemService;
	@Autowired private ConfigurationService configurationService;
	@Autowired private ApplicationService applicationService;
	@Autowired private AdminService adminService;
	@Autowired private UtilityService utilityService;
	@Autowired private AccountService accountService;
	@Autowired private DashboardService dashboardService;
    @Autowired private PageLoadController pageLoadController;
//	@Autowired private PageService 
	
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		init();
	}
	
	@Override
	public void init() {
        // services
		configurationService.init();
		systemService.init();
		utilityService.init();
		adminService.init();
		applicationService.init();
		accountService.init();

        // controllers
        pageLoadController.init();

        // do miscellaneous things
        LogManager.manager().setApplicationService(applicationService);

		if (logger.isInfo()) {
			logger.info("Done initializing all Valloc services.");
		}
	}
}
