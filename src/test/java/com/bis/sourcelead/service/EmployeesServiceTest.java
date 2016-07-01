package com.bis.sourcelead.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;

import com.bis.sourcelead.WebAppConfig;
import com.bis.sourcelead.entity.Employees;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.impl.EmployeesServiceImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = WebAppConfig.class, loader = AnnotationConfigWebContextLoader.class)
public class EmployeesServiceTest {

	
	@Configuration
	static class EmployeeServiceConfigTest {
		@Bean
		public EmployeesService emplosyeeService() {
			return new EmployeesServiceImpl();
		}
		

	}
	
	
	@Autowired
	EmployeesService employeeService;

	private static final Logger logger = LoggerFactory.getLogger(UserServiceTest.class);

	@Test
	public void addEmployee() throws Exception{
		
		Employees employee = new Employees();
		
		User user2 = new User();
		user2.setId(1L);
		employee.setUser(user2);
		employee.setCity("hyd");
		employee.setCountry("Ind");
		employee.setEmployeeState("active");
		employeeService.addEmployee(employee);
		System.out.println("Successfully Added!!!!");
	}
	
	
	@Test
	public void getEmployee() throws Exception{
		Employees employee =	employeeService.getEmployee("2");
		User user = new User();
		user.setId(1L);
		System.out.println("get value of ind"+employee.getCountry());
	}

	@Test
	public void deleteEmployee() throws Exception{
		Boolean flag =	employeeService.deleteEmployee("1");
	}
	
	
}
