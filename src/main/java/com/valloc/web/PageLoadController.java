package com.valloc.web;

import com.valloc.Category;
import com.valloc.log.LogManager;
import com.valloc.log.Logger;
import com.valloc.serve.PageServeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Handles requests for the application home page.
 */
@Controller
public class PageLoadController extends AbstractController
{
    private static final Logger logger = LogManager.manager().newLogger(PageLoadController.class, Category.CONTROLLER);

    @Autowired private PageServeService pageServeService;

    public void init() {
        ModelAndView modelStore = new ModelAndView();
        loadModel(modelStore);
        pageServeService.setRequestAttributesMap(modelStore.getModel());
    }

    @RequestMapping(value="/{username}", method=RequestMethod.GET)
    public void get(@PathVariable String username, HttpServletRequest request, HttpServletResponse response) {
		pageServeService.servePage(username, request, response);
	}
	
	@RequestMapping(value="/{username}/{path}")
	public void get(@PathVariable String username, @PathVariable String path, HttpServletRequest request, HttpServletResponse response) {
        pageServeService.servePage(username, path, request, response);
	}


}

