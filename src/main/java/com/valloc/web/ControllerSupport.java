package com.valloc.web;

import com.valloc.Category;
import com.valloc.framework.*;
import com.valloc.log.LogManager;
import com.valloc.log.Logger;
import com.valloc.object.dto.ErrorResponseDto;
import com.valloc.service.ApplicationService;
import com.valloc.service.ConfigurationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

/**
 * Handles requests for the application home page.
 */
@ControllerAdvice
public class ControllerSupport extends AbstractController
{
	private static final Logger logger = LogManager.manager().newLogger(ControllerSupport.class, Category.CONTROLLER);
	@Autowired private ConfigurationService configurationService;
	@Autowired private ApplicationService applicationService;
		
	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ResponseBody
	public ModelAndView handleException(Exception e) {
		logger.error(e.getMessage(), e);
		ModelAndView mav = new ModelAndView();
		loadModel(mav);
		mav.setViewName("error/error");
		mav.addObject("error_title", applicationService.getCustomizedUserMessage("error_title", Integer.toString(HttpStatus.INTERNAL_SERVER_ERROR.value())).getMessage());
		mav.addObject("error_message", applicationService.getUserMessage("error_500_message").getMessage());
		return mav;
	}

	@ExceptionHandler(ServerErrorPageResultException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ModelAndView handleServerErrorPageResultException(ServerErrorPageResultException e) {
		logger.error(e.getMessage(), e);		
		ModelAndView mav = new ModelAndView();
        loadModel(mav);
		mav.setViewName("error/500");
		mav.addObject("error_title", applicationService.getCustomizedUserMessage("error_title", Integer.toString(HttpStatus.INTERNAL_SERVER_ERROR.value())).getMessage());
		mav.addObject("error_message", applicationService.getUserMessage("error_500_message").getMessage());
		return mav;
	}

	@ExceptionHandler(ServerErrorAjaxResultException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ResponseBody
	public ErrorResponseDto handleServerErrorAjaxResultException(ServerErrorAjaxResultException e) {
		logger.error(e.getMessage(), e);
		ErrorResponseDto errorResponseDto = 
				new ErrorResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), 
						applicationService.getCustomizedUserMessage("error_title", Integer.toString(HttpStatus.INTERNAL_SERVER_ERROR.value())).getMessage(),
						applicationService.getUserMessage("error_500_message").getMessage());		
		return errorResponseDto;
	}

	@ExceptionHandler(BadRequestPageResultException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ModelAndView handleBadRequestPageResultException(BadRequestPageResultException e) {
		logger.warn(e.getMessage());
		ModelAndView mav = new ModelAndView();
        loadModel(mav);
		mav.setViewName("error/400");
		mav.addObject("error_title", applicationService.getCustomizedUserMessage("error_title", Integer.toString(HttpStatus.BAD_REQUEST.value())).getMessage());
		mav.addObject("error_message", applicationService.getUserMessage("error_400_message").getMessage());
		return mav;
	}

	@ExceptionHandler(BadRequestAjaxResultException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public ErrorResponseDto handleBadRequestAjaxResultException(BadRequestAjaxResultException e) {
		logger.warn(e.getMessage());
		ErrorResponseDto errorResponseDto = 
				new ErrorResponseDto(HttpStatus.BAD_REQUEST.value(), 
						applicationService.getCustomizedUserMessage("error_title", Integer.toString(HttpStatus.BAD_REQUEST.value())).getMessage(),
						applicationService.getUserMessage("error_400_message").getMessage());		
		return errorResponseDto;
	}	

	@ExceptionHandler(ForbiddenPageResultException.class)
	@ResponseStatus(HttpStatus.FORBIDDEN)
	public ModelAndView handleForbiddenPageResultException(ForbiddenPageResultException e) {
		ModelAndView mav = new ModelAndView();
        loadModel(mav);
		mav.setViewName("error/403");
		mav.addObject("error_title", applicationService.getCustomizedUserMessage("error_title", Integer.toString(HttpStatus.FORBIDDEN.value())).getMessage());
		mav.addObject("error_message", applicationService.getUserMessage("error_403_message").getMessage());		
		return mav;
	}

	@ExceptionHandler(ForbiddenAjaxResultException.class)
	@ResponseStatus(HttpStatus.FORBIDDEN)
	@ResponseBody
	public ErrorResponseDto handleForbiddenAjaxResultException(ForbiddenAjaxResultException e) {
		ErrorResponseDto errorResponseDto = 
				new ErrorResponseDto(HttpStatus.FORBIDDEN.value(), 
						applicationService.getCustomizedUserMessage("error_title", Integer.toString(HttpStatus.FORBIDDEN.value())).getMessage(),
						applicationService.getUserMessage("error_403_message").getMessage());		
		return errorResponseDto;
	}	

	@ExceptionHandler(PageNotFoundPageResultException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ModelAndView handlePageNotFoundPageResultException(PageNotFoundPageResultException e) {
		ModelAndView mav = new ModelAndView();
        loadModel(mav);
        mav.setViewName("error/404");
		mav.addObject("error_title", applicationService.getCustomizedUserMessage("error_title", Integer.toString(HttpStatus.NOT_FOUND.value())).getMessage());
		mav.addObject("error_message", applicationService.getUserMessage("error_404_message").getMessage());		
		return mav;
	}

	@ExceptionHandler(PageNotFoundAjaxResultException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ResponseBody
	public ErrorResponseDto handlePageNotFoundAjaxResultException(PageNotFoundAjaxResultException e) {
		ErrorResponseDto errorResponseDto = 
				new ErrorResponseDto(HttpStatus.NOT_FOUND.value(), 
						applicationService.getCustomizedUserMessage("error_title", Integer.toString(HttpStatus.NOT_FOUND.value())).getMessage(),
						applicationService.getUserMessage("error_404_message").getMessage());		
		return errorResponseDto;
	}

}