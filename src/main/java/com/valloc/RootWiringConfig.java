package com.valloc;

import com.valloc.dao.DaoWiringConfig;
import com.valloc.service.ServiceWiringConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * The root configuration class which ties all other common and tier configurations together at 
 * runtime.  This class is only used when the web application runs, and not used in unit testing.
 * 
 * @author wstevens
 */
@Configuration
@Import({
	CommonWiringConfig.class, 
	DaoWiringConfig.class,
	ServiceWiringConfig.class
})
@ComponentScan(basePackageClasses={Valloc.class})
public class RootWiringConfig 
{
}