package model;

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

public class ProductServiceTest {
	private	static ApplicationContext context; 
	@BeforeClass
	public static void before() {
		context = new AnnotationConfigApplicationContext(SpringJavaConfigurationTest.class);
	}
	
	private ProductService productService = null;
	@Before
	public void beforeEachTest() {
		productService = (ProductService) context.getBean("productService"); 
	}
	@Test
	public void testFindAll() {
		List<ProductBean> beans = productService.select(null);
		Assert.assertNotNull(beans);
//		Assert.assertNotEquals(beans.size(), 0);
		

		ProductBean product = new ProductBean();
		product.setId(5);
		List<ProductBean> bean = productService.select(product);
		Assert.assertNotNull(bean);
		Assert.assertEquals(bean.size(), 1);
	}
	@After
	public void afterEachTest() {
		productService = null;
	}
	@AfterClass
	public static void after() {
		((ConfigurableApplicationContext) context).close();		
	}
}
