package model.dao;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import model.CustomerBean;
import model.CustomerDAO;

public class CustomerDAOJdbcTest {
	private	static ApplicationContext context; 
	@BeforeClass
	public static void before() {
		context = new ClassPathXmlApplicationContext("beans.config.xml");
	}
	private CustomerDAO customerDao = null;
	@Before
	public void beforeEachTest() {
		customerDao = (CustomerDAO) context.getBean("customerDAOJdbc");
	}
	@Test
	public void testFindByPrimaryKey() {
		CustomerBean alex = customerDao.findByPrimaryKey("Alex");
		Assert.assertNotNull(alex);
		Assert.assertEquals(alex.getCustid(), "Alex");
		
		CustomerBean xxx = customerDao.findByPrimaryKey("xxx");
		Assert.assertNull(xxx);
	}
	@Test
	public void testUpdate() {
		boolean update1 = customerDao.update("EEE".getBytes(),
				"ellen@yahoo.com", null, "Ellen");
		Assert.assertTrue(update1);
		
		boolean update2 = customerDao.update("EEE".getBytes(),
				"ellen@yahoo.com", null, "XXX");
		Assert.assertFalse(update2);
	}
	@After
	public void afterEachTest() {
		customerDao = null;
	}
	@AfterClass
	public static void after() {
		((ConfigurableApplicationContext) context).close();		
	}
}
