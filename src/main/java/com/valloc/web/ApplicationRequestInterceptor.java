/*
 * Property of Will Stevens
 * All rights reserved.
 */
package com.valloc.web;

import com.valloc.Category;
import com.valloc.framework.Session;
import com.valloc.framework.SessionManager;
import com.valloc.log.LogManager;
import com.valloc.log.Logger;
import com.valloc.object.domain.ApplicationRequest;
import com.valloc.object.domain.User;
import com.valloc.object.pojo.ApplicationRequestPojo;
import com.valloc.service.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.Enumeration;
import java.util.Map;

/**
 * @author wstevens
 */
public class ApplicationRequestInterceptor extends HandlerInterceptorAdapter
{
    private static final Logger logger = LogManager.manager().newLogger(ApplicationRequestInterceptor.class, Category.INTERCEPTOR);
    private final static String REQUEST_APP_START_TIME_KEY = "request-app-start-time";
    @Autowired private ApplicationService applicationService;
    @Autowired private SessionManager sessionManager;

    public ApplicationRequestInterceptor(ApplicationService applicationService, SessionManager sessionManager) {
        super();
        this.applicationService = applicationService;
        this.sessionManager = sessionManager;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // Set necessary parameters here.
        request.setAttribute(REQUEST_APP_START_TIME_KEY, new Date());

        if (logger.isFinest()) {
            // Print request parameters.
            StringBuilder builder = new StringBuilder("Request parameters for request URI ").append(request.getRequestURI()).append(" are: ");
            Map<String, String[]> paramMap = request.getParameterMap();
            for (String key : paramMap.keySet()) {
                String[] values = paramMap.get(key);
                builder.append(key).append("=\"");
                if (values.length == 1) {
                    builder.append(values[0]);
                } else {
                    for (String value : values) {
                        builder.append(value).append(";");
                    }
                    builder.delete(builder.length()-1, builder.length());
                }
                builder.append("\"; ");
            }
            builder.delete(builder.length()-2, builder.length());
            logger.finest(builder.toString());

            // Print request headers.
            builder.delete(0, builder.length());
            builder = builder.append("Request headers for request URI ").append(request.getRequestURI()).append(" are: ");
            Enumeration<String> headerNames = request.getHeaderNames();
            while (headerNames.hasMoreElements()) {
                String headerName = headerNames.nextElement();
                String headerValue = request.getHeader(headerName);
                builder.append(headerName).append("=\"").append(headerValue).append("\"; ");
            }
            builder.delete(builder.length()-2, builder.length());
            logger.finest(builder.toString());
        }

        return super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        super.afterCompletion(request, response, handler, ex);

        final String requestUri = request.getRequestURI();
        ApplicationRequest requestStats = newApplicationRequest(request, response);
        applicationService.addApplicationRequestStats(requestStats);

        if (logger.isFinest()) {
            // Print response headers.
            StringBuilder builder = new StringBuilder("Response headers for request URI ").append(request.getRequestURI()).append(" are: ");
            for (String headerName : response.getHeaderNames()) {
                String headerValue = response.getHeader(headerName);
                builder.append(headerName).append("=\"").append(headerValue).append("\"; ");
            }
            builder.delete(builder.length()-2, builder.length());
            logger.finest(builder.toString());
        }
    }


    public ApplicationRequest newApplicationRequest(HttpServletRequest request, HttpServletResponse response) {
        ApplicationRequest applicationRequest = new ApplicationRequestPojo();

        Date startTimestamp = (Date) request.getAttribute(REQUEST_APP_START_TIME_KEY);
        Date endTimestamp = new Date();
        long totalTimeDiffMillis = endTimestamp.getTime() - startTimestamp.getTime();

        applicationRequest.setRequestRemoteHost(request.getRemoteHost());
        applicationRequest.setRequestRemoteAddress(request.getRemoteAddr());
        applicationRequest.setRequestRemotePort(request.getRemotePort());
        applicationRequest.setRequestMethod(request.getMethod());
        applicationRequest.setRequestScheme(request.getScheme());
        applicationRequest.setRequestContextPath(request.getContextPath());
        applicationRequest.setRequestUri(request.getRequestURI());
        applicationRequest.setRequestQueryString(request.getQueryString());
        applicationRequest.setRequestUrl(request.getRequestURL().toString());
        applicationRequest.setRequestContentLength(request.getContentLength());
        applicationRequest.setRequestEncoding(request.getCharacterEncoding());
        applicationRequest.setRequestIfNoneMatchHeader(request.getHeader("If-None-Match"));
        applicationRequest.setRequestUserAgentHeader(request.getHeader("User-Agent"));
        applicationRequest.setResponseStatusCode(response.getStatus());
        applicationRequest.setResponseEncoding(response.getCharacterEncoding());
        applicationRequest.setResponseEtagHeader(response.getHeader("ETag"));
        applicationRequest.setStartTimestamp(startTimestamp);
        applicationRequest.setEndTimestamp(endTimestamp);
        applicationRequest.setTotalTimeDiffMillis(totalTimeDiffMillis);

        // misc
        HttpSession httpSession = request.getSession(false); // don't create unless necessary (like user just came into system)
        if (httpSession != null) {
            Session session = sessionManager.getSessionByHttpSession(httpSession);
            if (session != null) {
                User user = session.getUser();
                if (user != null) {
                    applicationRequest.setUserId(user.getId());
                }
            }
        }

        return applicationRequest;
    }
}
