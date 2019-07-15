package com.valloc.web;

import com.valloc.framework.SessionManager;
import com.valloc.service.AccountService;
import com.valloc.service.ApplicationService;
import com.valloc.service.ConfigurationService;
import com.valloc.service.UtilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import static com.valloc.UrlConstants.*;

/**
 * Configuration for web related components.
 * 
 * See Spring MVC documentation for specifics on each method below.
 * 
 * @author wstevens
 */
@EnableWebMvc
@Configuration
@ComponentScan(basePackageClasses=WebWiringConfig.class)
public class WebWiringConfig extends WebMvcConfigurerAdapter
{
	@Autowired private AccountService accountService;
	@Autowired private ApplicationService applicationService;
	@Autowired private ConfigurationService configurationService;
	@Autowired private SessionManager sessionManager;
    @Autowired private UtilityService utilityService;

	@Bean
	public InternalResourceViewResolver internalResourceViewResolver() {
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setViewClass(JstlView.class);
		viewResolver.setPrefix("/WEB-INF/jsp/"); 
		viewResolver.setSuffix(".jsp");
		return viewResolver;
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {

        // add registration interceptor for its special (non-user [yet]) patterns
        RegistrationPageInterceptor registrationPageInterceptor = new RegistrationPageInterceptor(accountService, applicationService, configurationService, sessionManager);
        registry.addInterceptor(registrationPageInterceptor).addPathPatterns(REGISTRATION_PATTERNS);

		// build and add interceptor for secured reserved pages:
		SecureReservedPageInterceptor reservedPageInterceptor =
			new SecureReservedPageInterceptor(accountService, applicationService, configurationService, sessionManager);
		registry.addInterceptor(reservedPageInterceptor).
                addPathPatterns(SECURE_INTERCEPTOR_PATTERNS).
                excludePathPatterns(SECURE_INTERCEPTOR_EXCLUSION_PATTERNS);

        // add the stats application request interceptor
        ApplicationRequestInterceptor applicationRequestInterceptor = new ApplicationRequestInterceptor(applicationService, sessionManager);
        registry.addInterceptor(applicationRequestInterceptor).
                addPathPatterns(APPLICATION_REQUEST_INTERCEPTOR_PATTERNS).
                excludePathPatterns(APPLICATION_REQUEST_INTERCEPTOR_EXCLUSION_PATTERNS);
	}

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/").setViewName("index");
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // first set the registry order to -1 to take precedence; otherwise all /r/** requests
        //      still be passed to the catch-all @RequestMapping("/{username}[/{path}]");
        registry.setOrder(-1);
        // now add the special handler for all static resources
        registry.addResourceHandler("/r/**").addResourceLocations("/r/");
        registry.addResourceHandler("/favicon.ico").addResourceLocations("/favicon.ico");
		registry.addResourceHandler("/robots.txt").addResourceLocations("/robots.txt");
	}

    @Bean(name="multipartResolver")
    public MultipartResolver multipartResolver() {
        return new StandardServletMultipartResolver();
    }
}