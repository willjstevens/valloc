package com.valloc.web;

import com.valloc.Category;
import com.valloc.framework.ServiceResult;
import com.valloc.framework.Session;
import com.valloc.framework.SessionManager;
import com.valloc.io.IoUtil;
import com.valloc.log.LogManager;
import com.valloc.log.Logger;
import com.valloc.object.domain.User;
import com.valloc.object.dto.DashboardListingPagesDto;
import com.valloc.service.DashboardService;
import com.valloc.service.UtilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

import static com.valloc.UrlConstants.DASHBOARD;

/**
 * Handles requests for the application home page.
 */
@Controller
public class DashboardController extends AbstractController
{

	private static final Logger logger = LogManager.manager().newLogger(DashboardController.class, Category.CONTROLLER);
	@Autowired private SessionManager sessionManager;
	@Autowired private DashboardService dashboardService;
	@Autowired private UtilityService utilityService;
	@Autowired private IoUtil ioUtil;
	
	@RequestMapping(value=DASHBOARD)
	public ModelAndView get(HttpSession httpSession) {
		Session session = sessionManager.getSessionByHttpSession(httpSession);
		User user = session.getUser();
		ServiceResult<DashboardListingPagesDto> serviceResult = dashboardService.getAccessiblePages(user);
		DashboardListingPagesDto dto = serviceResult.getObject();
		ModelAndView model = new ModelAndView();
		model.setViewName("dashboard");
        loadModel(model);
        loadCustomMessage(model, "dashboard_pageTitle", user.getFirstName());
        loadCustomMessage(model, "dashboard_pageHeader", user.getFirstName());
		String json = utilityService.jsonStringify(dto);
		if (logger.isFiner()) {
			logger.finer("Dashboard page JSON data is:\n%s", json);
		}
		model.addObject("pld", json);
        model.addObject("hasPages", !dto.pages.isEmpty());
		return model;
	}
}

