package model.dao;

import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import config.SpringJavaConfigurationTest;
import model.ProductBean;
import model.ProductDAO;

public class ProductDAOJdbcTest {
	private	static ApplicationContext context; 
	@BeforeClass
	public static void before() {
		context = new AnnotationConfigApplicationContext(SpringJavaConfigurationTest.class);
	}
	
	private ProductDAO productDao = null;
	@Before
	public void beforeEachTest() {
		productDao = (ProductDAO) context.getBean("productDAOJdbc");
	}
	@Test
	public void testFindAll() {
		List<ProductBean> beans = productDao.findAll();
		Assert.assertNotNull(beans);
	}
	@After
	public void afterEachTest() {
		productDao = null;
	}
	@AfterClass
	public static void after() {
		((ConfigurableApplicationContext) context).close();		
	}
}
