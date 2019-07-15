package com.valloc.web;

import com.valloc.Category;
import com.valloc.framework.ServerErrorAjaxResultException;
import com.valloc.framework.ServerErrorPageResultException;
import com.valloc.log.LogManager;
import com.valloc.log.Logger;
import com.valloc.service.ApplicationService;
import com.valloc.service.ConfigurationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * Handles requests for the application home page.
 */
@Controller
@RequestMapping(value="/test")
public class TestController extends AbstractController
{
	static final Logger logger = LogManager.manager().newLogger(TestController.class, Category.TEST);
	@Autowired private ConfigurationService configurationService;
	@Autowired private ApplicationService applicationService;
	
	@RequestMapping
	public String get() {
		return "test/test";
	}
	
	@RequestMapping(value="/test-script-include", method=RequestMethod.GET)
	public ModelAndView get(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("test/test-script-include");
		loadCommonModelData(mav);
		return mav;
	}
	
	@RequestMapping(value="/bomb", method=RequestMethod.GET)
	public ModelAndView bomb(HttpServletRequest request) {
		boolean doThrow = true;
		if (doThrow) {
			throw new ServerErrorPageResultException();			
		}
		
		return null;
	}
	
	@RequestMapping(value="/ajax/echo/text/bomb")
	public @ResponseBody String ajaxEchoStringGetBomb(@RequestParam String message) {
		if (logger.isInfo()) {
			logger.info("ajaxEchoStringGet Echo: " + message);
		}
		boolean doThrow = true;
		if (doThrow) {
			throw new ServerErrorAjaxResultException();			
		}
		
		return message;
	}
	
	@RequestMapping(value="/ajax/echo/text")
	public @ResponseBody String ajaxEchoStringGet(@RequestParam String message) {
		if (logger.isInfo()) {
			logger.info("ajaxEchoStringGet Echo: " + message);
		}
		return message;
	}
	
	@RequestMapping(value="/ajax/echo/text", method=RequestMethod.POST)
	public @ResponseBody String ajaxEchoStringPost(@RequestParam String message) {
		if (logger.isInfo()) {
			logger.info("ajaxEchoStringPost Echo: " + message);
		}
		return message;
	}

	@RequestMapping(value="/ajax/echo/html")
	public @ResponseBody String ajaxEchoHtmlGet(@RequestParam String html) {
		if (logger.isInfo()) {
			logger.info("ajaxEchoHtmlGet Echo: " + html);
		}
		return html;
	}
	
	@RequestMapping(value="/ajax/echo/json")
	@ResponseBody 
	public FormPostJsonResponseObject ajaxEchoJsonGet(@RequestParam String message) {
		final String uuidMsg = "Generated UUID is " + UUID.randomUUID().toString();
		FormPostJsonResponseObject retval = new FormPostJsonResponseObject(message, uuidMsg);
		if (logger.isInfo()) {
			logger.info("ajaxEchoJsonGet Echo: " + retval);
		}
		return retval;
	}
	
	@RequestMapping(value="/ajax/echo/json", method=RequestMethod.POST)
	@ResponseBody 
	public FormPostJsonResponseObject ajaxEchoJsonPost(@RequestParam String message, HttpServletRequest request, HttpServletResponse response) {
		String uuidMsg = "Generated UUID is " + UUID.randomUUID().toString();
		FormPostJsonResponseObject retval = new FormPostJsonResponseObject(message, uuidMsg);
		if (logger.isInfo()) {
			logger.info("ajaxEchoJsonGet Echo: " + retval);
		}
		return retval;
	}
	
	@RequestMapping(value="/ajax/echo/postjson", method=RequestMethod.POST)
	@ResponseBody 
	public FormPostJsonResponseObject ajaxEchoJsonPostJson(@RequestBody FormPostJsonRequestObject jsonObj, HttpServletRequest request, HttpServletResponse response) {
		String message = String.format("The echoed message is \"%s\".", jsonObj.message);
		String data = String.format("Data is server-side-uuid=%s, int-data=%d, boolean-data=%b.", UUID.randomUUID().toString(), jsonObj.intData, jsonObj.booleanData);
		FormPostJsonResponseObject retval = new FormPostJsonResponseObject(message, data);
		if (logger.isInfo()) {
			logger.info("ajaxEchoJsonPostJson Echo: " + retval);
		}
		return retval;
	}
	
	public static class FormPostJsonRequestObject {
		public String message;
		public int intData;
		public boolean booleanData;
		@Override
		public String toString() {
			return "FormPostJsonRequestObject [message=" + message + ", intData=" + intData + ", booleanData=" + booleanData + "]";
		}
	}
	
	public class FormPostJsonResponseObject {
		public String message;
		public String data;
		public FormPostJsonResponseObject() {}
		public FormPostJsonResponseObject(String message, String data) {
			this.message = message;
			this.data = data;
		}
		@Override
		public String toString() {
			return "FormPostJsonResponseObject [message=" + message + ", data=" + data + "]";
		}
	}
	  
//	  @ExceptionHandler(Exception.class)
//	  public ResponseEntity<String> handleException(Exception ex) {
//
//	    // prepare responseEntity
//
//	    return responseEntity;
//	  }
	
//	@RequestMapping(method=RequestMethod.GET)
//	public ModelAndView get(HttpServletRequest request, HttpServletResponse response) {
//		
//		Session session = sessionManager.getSessionByHttpSession(request.getSession());
//		if (session == null) {
//			ModelAndView mav = new ModelAndView();
//			mav.setViewName("redirect:/login");
//			return mav;
//		}
//		
//		User user = session.getUser();
//		List<Page> pages = pageService.findAllPages(user);
//		
//		ModelAndView mav = new ModelAndView();
//		mav.setViewName("dashboard");
//		mav.addObject("pages", pages);
//		return mav;
//	}
//		
//	@RequestMapping(value="/page/create", method=RequestMethod.GET)
//	public ModelAndView pageCreateGet() {
//		ModelAndView mav = new ModelAndView();
//		mav.setViewName("page-designer");
//		mav.addObject("pageForm", new PageForm());
//		return mav;
//	}
//	
//	/*
//	 * This needs to have the client send content-type as application/json.
//	 * 
//	 * 
//	 * See: http://blog.springsource.com/2010/01/25/ajax-simplifications-in-spring-3-0
//	 * 
//	 */
//	@RequestMapping(value="/page/create", method=RequestMethod.POST)
//	public @ResponseBody String pageCreatePost(@RequestBody PageRequest json, PageForm pageForm, HttpServletRequest request, HttpServletResponse response, HttpSession httpSession) {
//		System.out.println(json);
//		
//		Session session = sessionManager.getSessionByHttpSession(request.getSession());
//		User user = session.getUser();
//		Page page = toPage(json);
//		
//
//		ServiceResult result = new ServiceResult();
//		pageService.newPage(user, page, request, response, result);
//		session.setUiMsg(result.getMessage());
//		
//		session.addUiAttribute("pageSaveSuccess", result.isSuccess());
//		session.addUiAttribute("username", user.getUsername());
//		session.addUiAttribute("path", result.isSuccess() ? page.getPath() : json.getOrigPath());
//		session.addUiAttribute("isHome", page.isHome());
//		session.addUiAttribute("uiMsg", session.getUiMsg());
//		
//		if (result.isSuccess()) {
//			response.setStatus(HttpServletResponse.SC_OK);
//		} else {
//			response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
//		}
//		
//		return "void body";
//	}
	
	

//    @RequestMapping(value = { "/movies", "/movies/all" }, method = RequestMethod.GET)
//    @UUIDInject(argSlot=1, keyClass=Object.class, getMethod="getAttribute",setMethod="setAttribute", attributeName="activityID")
//    ModelAndView allMovies(HttpServletRequest httpRequest, @RequestParam(required=false) String pageNumber) throws Exception
//    {
//        String[] filters = calculateFilters(null, null);
//        final String defaultSpecialFilter = SEOService.FILTERBY_FEATURED;
//        SEOPageBO seoPageInfo = getService().getMovies(SEOService.SUBCAT_ALL, defaultSpecialFilter, filters[1],  getHostUrlPrefix(httpRequest), pageNumber);
//        return new ModelAndView("seopage", "bo", seoPageInfo);
//    }
//
//    @RequestMapping(value = { "/movies/all/{path1}" }, method = RequestMethod.GET)
//    @UUIDInject(argSlot=1, keyClass=Object.class, getMethod="getAttribute",setMethod="setAttribute", attributeName="activityID")
//    ModelAndView allMovies(HttpServletRequest httpRequest, @PathVariable String path1, @RequestParam(required=false) String pageNumber) throws Exception
//    {
//        String[] filters = calculateFilters(path1, null);
//        SEOPageBO seoPageInfo = getService().getMovies(SEOService.SUBCAT_ALL, filters[0], filters[1],  getHostUrlPrefix(httpRequest), pageNumber);
//        return new ModelAndView("seopage", "bo", seoPageInfo);
//    }
    
    
	
//	@RequestMapping(value="/page/edit", method=RequestMethod.GET)
//	public ModelAndView pageEditGet(@RequestParam String pagePath, HttpSession httpSession) {
//
//		Session session = sessionManager.getSessionByHttpSession(httpSession);
//		if (session == null) {
//			ModelAndView mav = new ModelAndView();
//			mav.setViewName("redirect:/login");
//			return mav;
//		}
//		
//		User user = session.getUser();
//		Page page = pageService.findPageByPath(user, pagePath);	
//				
//		PageRequest pageRequest = toPageRequest(page);
//
//		StringWriter sw = new StringWriter();
//		BufferedWriter bw = new BufferedWriter(sw);
//		ObjectMapper jacksonObjectMapper = new ObjectMapper();
//		String result = null;
//		try {
//			JsonGenerator jsonGenerator = jacksonObjectMapper.getJsonFactory().createJsonGenerator(bw);
//			jacksonObjectMapper.writeValue(jsonGenerator, pageRequest);
//			result = sw.toString();
//		} catch (IOException e) {
//			// DOME: 
//			e.printStackTrace();
//		} finally {
//			
//		}
//				
//		ModelAndView mav = new ModelAndView();
//		mav.setViewName("page-designer");
//		mav.addObject(page);
//		mav.addObject("isEdit", Boolean.TRUE);
//		mav.addObject("pageLoadData", result);
//		mav.addObject("pageForm", new PageForm());
//		return mav;
//	}
	


//	@RequestMapping(value="/page/edit", method=RequestMethod.POST)
//	public @ResponseBody String pageEditPost(@RequestBody PageRequest json, PageForm pageForm, HttpServletRequest request, HttpServletResponse response, HttpSession httpSession) {
//		Session session = sessionManager.getSessionByHttpSession(request.getSession());
//		if (session == null) {
//			return "redirect:/login";
//		}
//		
//		User user = session.getUser();
//		Page page = toPage(json); 
//		ServiceResult result = new ServiceResult();
//		pageService.editPage(user, page, json.getOrigPath(), request, response, result);
//		session.setUiMsg(result.getMessage());
//		
//		session.addUiAttribute("pageSaveSuccess", result.isSuccess());
//		session.addUiAttribute("username", user.getUsername());
//		session.addUiAttribute("path", result.isSuccess() ? page.getPath() : json.getOrigPath());
//		session.addUiAttribute("isHome", page.isHome());
//		session.addUiAttribute("uiMsg", session.getUiMsg());
//		
//		if (result.isSuccess()) {
//			response.setStatus(HttpServletResponse.SC_OK);
//		} else {
//			response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
//		}
//		
//		return "void body";
//	}
	

}