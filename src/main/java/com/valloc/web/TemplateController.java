package com.valloc.web;

import com.valloc.Category;
import com.valloc.log.LogManager;
import com.valloc.log.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Handles requests for the application home page.
 */
@Controller
@RequestMapping(value="/template")
public class TemplateController extends AbstractController
{
	private static final Logger logger = LogManager.manager().newLogger(TemplateController.class, Category.CONTROLLER);
    private static final String TEMPLATE_JSP_DIR = "template/";
	
	@RequestMapping(value="/link-note-display")
	public String linkNoteDisplay() {
		return TEMPLATE_JSP_DIR + "link-note-display";
	}

}