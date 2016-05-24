package com.bis.sourcelead.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;

import com.bis.sourcelead.WebAppConfig;
import com.bis.sourcelead.dao.CustomerDao;
import com.bis.sourcelead.dao.impl.CustomerDaoImpl;
import com.bis.sourcelead.entity.Customer;
import com.bis.sourcelead.service.impl.TestEhCacheServiceImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = WebAppConfig.class, loader = AnnotationConfigWebContextLoader.class)
public class TestEhCacheServiceTest {
	
	@Configuration
	static class TestEhCacheServiceTestContextConfiguration {
		
		@Bean
		public TestEhCacheService ehCacheService() {
			return new TestEhCacheServiceImpl();
		}
		
		@Bean
		public CustomerDao customerDao() {
			return new CustomerDaoImpl();
		}
	}
		
		@Autowired
		TestEhCacheService testEhCacheService;
		
		@Test
		public void saveCustomer() throws Exception{
			testEhCacheService.saveCustomer(buildCustomer());
		}
		
		private Customer buildCustomer(){
			Customer customer = new Customer();
			customer.setCustomerName("Venkat");
			customer.setCustomerAddress("Miyapur Allwyn");
			customer.setCity("Hyderabad");
			customer.setZipcode("12345");
			return customer;
			
		}
				
}
