package com.valloc.web;

import com.valloc.CommonWiringConfig;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Sample controller unit test classes.  
 * 
 * @author wstevens
 */
@ContextConfiguration(loader=MockWebApplicationContextLoader.class, classes={WebWiringConfig.class, CommonWiringConfig.class})
@MockWebApplication(name="ControllerTests")
@RunWith(SpringJUnit4ClassRunner.class)
public class WebTests 
{
//	@Autowired
//	private MainController mainController;
	
//	@Test
//	public void testController() {
//		// Here this test could test for the return view, the expected response body, a Model map, etc..
//		Assert.assertEquals("account", mainController.accountCreateGet()); 
//	}
}