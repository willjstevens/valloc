/*
 * Property of Will Stevens
 * All rights reserved.
 */
package com.valloc.web;

import com.valloc.Category;
import com.valloc.RootWiringConfig;
import com.valloc.log.LogManager;
import com.valloc.log.Logger;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

/**
 *
 *
 * 
 * @author wstevens
 */
public final class VallocAppInitializer implements WebApplicationInitializer {
    private static final Logger logger = LogManager.manager().newLogger(VallocAppInitializer.class, Category.FRAMEWORK);

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
	    // Create the 'root' Spring application context
	    AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
	    rootContext.register(RootWiringConfig.class);

	    // Manage the lifecycle of the root application context
	    servletContext.addListener(new ContextLoaderListener(rootContext));

	    // Create the dispatcher servlet's Spring application context
	    AnnotationConfigWebApplicationContext dispatcherContext = new AnnotationConfigWebApplicationContext();
	    dispatcherContext.register(WebWiringConfig.class);

	    // Register and map the dispatcher servlet
	    ServletRegistration.Dynamic dispatcher = servletContext.addServlet("dispatcher", new DispatcherServlet(dispatcherContext));
        dispatcher.setMultipartConfig(new MultipartConfigElement(null, 5000000, 5000000, 0));
	    dispatcher.setLoadOnStartup(1);
	    dispatcher.addMapping("/");

        WebSessionListener sessionListener = null;
        try {
            sessionListener = servletContext.createListener(WebSessionListener.class);
        } catch (ServletException e) {
            logger.error("Problem when creating the session listener: %s.", e, e);
        }
        sessionListener.setApplicationContext(rootContext);
        servletContext.addListener(sessionListener);
	}
}
