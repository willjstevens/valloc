package com.valloc.dao;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration loaded in non-DAO unit tests not concerned with dao tier.  This could be easily overrided if 
 * a service tier was being tested and the test expected a particular object to be returned from the data tier.
 * 
 * @author wstevens
 */
@Configuration
public class MockDaoConfig 
{
	@Bean(name="systemDaoMOCK")
	public SystemDao systemDao() {
		return new MockSystemDao();
	}
}