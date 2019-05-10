package model.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import config.SpringJavaConfigurationTest;

public class DataSourceJavaTest {
	private	static ApplicationContext context; 
	@BeforeClass
	public static void before() {
		context = new AnnotationConfigApplicationContext(SpringJavaConfigurationTest.class);
	}
	@Test
	public void method() {
		DataSource dataSource = (DataSource) context.getBean("dataSource");
		try(
			Connection conn = dataSource.getConnection();
			Statement stmt = conn.createStatement();
			ResultSet rset = stmt.executeQuery("select * from dept");
		) {
			while(rset.next()) {
				String col1 = rset.getString(1);
				String col2 = rset.getString(2);
				System.out.println("<h3>"+col1+":"+col2+"</h3>");
			}
			rset.close();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	@AfterClass
	public static void after() {
		((ConfigurableApplicationContext) context).close();		
	}
}
