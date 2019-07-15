package com.valloc.log;

import com.valloc.Category;
import org.junit.Assert;
import org.junit.Test;

/**
 * Sample service tests.
 * 
 * @author wstevens
 */
//@ContextConfiguration(classes={ServiceWiringConfig.class, CommonWiringConfig.class, MockDaoConfig.class})
//@RunWith(SpringJUnit4ClassRunner.class)
public class LoggerTests 
{
	@Test
	public void testBasicUsage() {
		LogManager logManager = LogManager.manager();
		
		Logger logger = logManager.newLogger(String.class, Category.ALL);
		
		logger.setLevel(Level.INFO);
		Assert.assertTrue(logger.isInfo());
		Assert.assertFalse(logger.isFine());
		logger.info("This will print");
		logger.fine("This will not print");

		logger.setLevel(Level.FINER);
		Assert.assertTrue(logger.isInfo());
		Assert.assertTrue(logger.isFine());
		Assert.assertTrue(logger.isFiner());
		Assert.assertFalse(logger.isFinest());
		logger.info("This will print");
		logger.fine("This will print");
		logger.finer("This will print");
		logger.finest("This will not print");
		
		logger.setLevel(Level.WARN);
		Assert.assertFalse(logger.isInfo());
		Assert.assertFalse(logger.isFine());
		logger.warn("This will print");
		logger.info("This will not print");
		logger.fine("This will not print");
	}
	
	@Test
	public void testCategories() {
		LogManager logManager = LogManager.manager();
		
		Logger stringLogger = logManager.newLogger(String.class, Category.SESSION);
		Logger intLogger = logManager.newLogger(Integer.class, Category.INITIALIZATION, Category.SESSION);
		
		stringLogger.setLevel(Level.INFO);
		Assert.assertTrue(stringLogger.isInfo());
		Assert.assertFalse(stringLogger.isFine());
		stringLogger.info("This will print");
		stringLogger.fine("This will not print");

		intLogger.setLevel(Level.INFO);
		Assert.assertTrue(intLogger.isInfo());
		Assert.assertFalse(intLogger.isFine());
		intLogger.info("This will print");
		intLogger.fine("This will not print");
		
		// set all loggers and all category to fine
		logManager.setLevel(Level.FINE);
		Assert.assertTrue(stringLogger.isFine());
		Assert.assertTrue(intLogger.isFine());
		stringLogger.fine("This will print");
		intLogger.fine("This will print");
		
		// test only category SESSION to finest (both loggers here)
		logManager.setLevel(Level.FINEST, Category.SESSION);
		Assert.assertTrue(stringLogger.isFinest());
		Assert.assertTrue(intLogger.isFinest());
		stringLogger.finest("This will print");
		intLogger.finest("This will print");
		
		// reset all back to info
		logManager.setLevel(Level.INFO);
		Assert.assertTrue(stringLogger.isInfo());
		Assert.assertTrue(intLogger.isInfo());
		Assert.assertFalse(stringLogger.isFinest());
		Assert.assertFalse(intLogger.isFinest());
		stringLogger.info("This will print");
		intLogger.info("This will print");
		stringLogger.finest("This will not print");
		intLogger.finest("This will not print");
		
		// then set only INITIALIZATION category to FINER
		logManager.setLevel(Level.FINER, Category.INITIALIZATION);
		Assert.assertFalse(stringLogger.isFiner());
		Assert.assertTrue(intLogger.isFiner());
		Assert.assertFalse(intLogger.isFinest());
		intLogger.finer("This will print");
		intLogger.finest("This will not print");
		logManager.setLevel(Level.INFO, Category.INITIALIZATION);
		Assert.assertTrue(intLogger.isInfo());
		Assert.assertFalse(intLogger.isFiner());
		Assert.assertFalse(intLogger.isFinest());
		intLogger.info("This will print");
		intLogger.finer("This will not print");
	}
}