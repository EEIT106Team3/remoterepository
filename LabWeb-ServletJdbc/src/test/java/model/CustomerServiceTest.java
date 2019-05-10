package model;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class CustomerServiceTest {
	private	static ApplicationContext context; 
	@BeforeClass
	public static void before() {
		context = new ClassPathXmlApplicationContext("beans.config.xml");
	}
	private CustomerService customerService = null;
	@Before
	public void beforeEachTest() {
		customerService = (CustomerService) context.getBean("customerService");
	}
	
	@Test
	public void testLogin() {
		CustomerBean alex = customerService.login("Alex", "A");
		Assert.assertNotNull(alex);
		Assert.assertEquals(alex.getCustid(), "Alex");
		
		CustomerBean babe = customerService.login("Babe", "BBB");
		Assert.assertNull(babe);
	}
	
	@Test
	public void testChangePassword() {
		boolean ellen = customerService.changePassword("Ellen", "xxx", "x");
		Assert.assertFalse(ellen);
	}
	
	@After
	public void afterEachTest() {
		customerService = null;
	}
	@AfterClass
	public static void after() {
		((ConfigurableApplicationContext) context).close();		
	}
}
