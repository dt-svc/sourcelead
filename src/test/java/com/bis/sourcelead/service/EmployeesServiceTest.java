package com.bis.sourcelead.service;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;

import com.bis.sourcelead.WebAppConfig;
import com.bis.sourcelead.entity.Employees;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = WebAppConfig.class, loader = AnnotationConfigWebContextLoader.class)
public class EmployeesServiceTest {
	
	@Autowired
	EmployeesService employeesService;
	

	private static final Logger logger = LoggerFactory.getLogger(EmployeesServiceTest.class);

	@Test
	public void addEmployee() throws Exception{
		
		Employees employee = new Employees();
		
		User user2 = new User();
		user2.setId(3L);
		employee.setUser(user2);
		employee.setEmployeeId("3e3ee");
		employee.setHireDate(new Date());
		employee.setDob(new Date());
		employee.setCity("hyd");
		employee.setCountry("Ind");
		employee.setEmployeeState("active");
		employee.setState("TG");
		OrgLocations orgLocations = new OrgLocations();
		orgLocations.setId(1L);
		employee.setOrgLocations(orgLocations);
		employee.setInvoiceFrequency("Weekly");
		employee.setStatus("Active");
		employee.setCreatedDate(new Date());
		employee.setCreatedBy("source");
		employee.setUpdatedBy("Source");
		employee.setUpdatedDate(new Date());
		employee.setRate("1000000");
		employee.setRatePer("Weekly");
		employee.setDailyHours("8");
		employeesService.addEmployee(employee);
		System.out.println("addEmployee " +employee.getCountry());
	}
	
	@Test
	public void updateEmployee() throws Exception{
		
		Employees employee = new Employees();
		employee.setId(3L);
		User user2 = new User();
		user2.setId(3L);
		employee.setUser(user2);
		employee.setEmployeeId("fd5dfg");
		employee.setHireDate(new Date());
		employee.setDob(new Date());
		employee.setCity("hyd");
		employee.setCountry("Ind");
		employee.setEmployeeState("Disabled");
		employee.setState("TG");
		OrgLocations orgLocations = new OrgLocations();
		orgLocations.setId(1L);
		employee.setOrgLocations(orgLocations);
		employee.setInvoiceFrequency("Weekly");
		employee.setStatus("ACTIVE");
		employee.setCreatedDate(new Date());
		employee.setCreatedBy("SOURCE");
		employee.setUpdatedBy("Source");
		employee.setUpdatedDate(new Date());
		employee.setRate("1000000");
		employee.setRatePer("Weekly");
		employee.setDailyHours("8");
		employee = employeesService.updateEmployee(employee);
		logger.info("updateEmployee successfully " + employee.getId());
	}
	
	@Test
	public void getEmployee() throws Exception{
		Employees employee = employeesService.getEmployee("3");
		
		logger.info("getEmployee" + employee.getStatus());
	}

		
	@Test
	public void getEmployeeByUserId() throws Exception{
		Employees employee = employeesService.getEmployeeByUserId(3L);
		logger.info("getEmployeeByUserId " + employee.getStatus());
	}
	
	@Test
	public void validateEmployeeId() throws Exception{
		Employees employee = new Employees();
		OrgLocations orgLocations = new OrgLocations();
		orgLocations.setId(1L);
		employee.setOrgLocations(orgLocations);
		Boolean flag = employeesService.validateEmployeeId("fd5dfg", employee.getOrgLocations().getId());
		logger.info("validateEmployeeId " + flag);
	}
	
	@Test
	public void getEmployeeByEmployeeIdAndUserIdAndOrgLocationsId() throws Exception{
		Employees employee = new Employees();
		employee.setId(3L);
		User user2 = new User();
		user2.setId(3L);
		employee.setUser(user2);
		employee.setEmployeeId("fd5dfg");
		OrgLocations orgLocations = new OrgLocations();
		orgLocations.setId(1L);
		employee.setOrgLocations(orgLocations);
		employee = employeesService.getEmployeeByEmployeeIdAndUserIdAndOrgLocationsId(employee.getEmployeeId(), employee.getUser().getId(), employee.getOrgLocations().getId());
		logger.info("getEmployeeByEmployeeIdAndUserIdAndOrgLocationsId " + employee.getEmployeeId());
	}
	
	@Test
	public void getAllEmployees() throws Exception{
		List<Employees> emplist= employeesService.getAllEmployees();
		logger.info("getAllEmployees" + emplist.size());
	}
	
	@Test
	public void getEmployeesExcludeTeamMembers() throws Exception{
		List<Employees> emplist= employeesService.getEmployeesExcludeTeamMembers(1L, 2L);
		if(emplist != null)
		{
		logger.info("getEmployeesExcludeTeamMembers " + emplist.size());
	}
	}
	@Test
	public void getAllEmployeesByOrgLocationId() throws Exception{
		Collection<Employees> emplist= employeesService.getAllEmployeesByOrgLocationId(1L, 1L, 1L);
		if(emplist != null)
		{
		logger.info("getAllEmployeesByOrgLocationId " + emplist.size());
	}
	}
	
	@Test
	public void validateEmployeeEmailId() throws Exception{
		Boolean flag =  employeesService.validateEmployeeEmailId(4L, "raj@gmail.com");
		if(flag != null)
		{
		logger.info("validateEmployeeEmailId " + flag);
	}
	}
	@Test
		public void deleteEmployee() throws Exception{
			Boolean flag =	employeesService.deleteEmployee("3");
			if(flag != null)
			{
			System.out.println("Successfully deleted!!!!");
			}
		}
}
