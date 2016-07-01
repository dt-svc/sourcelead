package com.bis.sourcelead.service;

import java.util.Date;
import java.util.List;

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
import com.bis.sourcelead.entity.Cities;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.impl.OrgLocationsServiceImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = WebAppConfig.class, loader = AnnotationConfigWebContextLoader.class)
public class OrgLocationsServiceTest {

	@Configuration
	static class OrgLocationsServiceConfigTest {
		@Bean
		public OrgLocationService orgLocationService() {
			return new OrgLocationsServiceImpl();
		}
				

	}
	
	@Autowired
	OrgLocationService orgLocationService;

	private static final Logger logger = LoggerFactory.getLogger(OrgLocationService.class);
	
		//@Test
		public void addOrgLocation() throws Exception{
			OrgLocations orgLocations = new OrgLocations();
			Org org = new Org();
			org.setId(1L);
			
			User user = new User();
			user.setId(1L);
			
			orgLocations.setOrg(org);
			orgLocations.setUser(user);
			orgLocations.setCountry("US");
			orgLocations.setState("TG");
			orgLocations.setCity("HYD");
			orgLocations.setZipCode("55555");
			orgLocations.setStatus("active");
			orgLocations.setCreatedBy("Vinu");
			orgLocations.setCreatedDate(new Date());
			orgLocations.setUpdatedBy("Prasanna");
			orgLocations.setUpdatedDate(new Date());
			orgLocationService.addOrgLocations(orgLocations);
			System.out.println("Successfully Added!!!!");
		}
		
		//@Test
		public void getOrgLocations() throws Exception{
			OrgLocations orgLocations =	orgLocationService.getOrgLocations(3L);
			System.out.println("get value of ind"+orgLocations.getOrg());
		}

		//@Test
		public void deleteOrgLocations() throws Exception{
			long l = 4;
			orgLocationService.deleteOrgLocations(l);
			System.out.println("Successfully deleted!!!!");
		}
		

		//@Test
		public void updateOrgLocations() throws Exception{
			OrgLocations orgLocations = new OrgLocations();
			Org org = new Org();
			org.setId(1L);
			
			User user = new User();
			user.setId(1L);
			
			orgLocations.setId(3L);
			orgLocations.setOrg(org);
			orgLocations.setUser(user);
			orgLocations.setCountry("Us");
			orgLocations.setState("TG");
			orgLocations.setCity("NZB");
			orgLocations.setZipCode("22222");
			orgLocations.setStatus("in-active");
			orgLocations.setCreatedBy("Laxman");
			orgLocations.setCreatedDate(new Date());
			orgLocations.setUpdatedBy("Prasanna");
			orgLocations.setUpdatedDate(new Date());
			orgLocationService.updateOrgLocations(orgLocations);
		}
		
		@Test
		public void getAllOrgLocations() throws Exception{
			List<OrgLocations> orgLocationsList  = orgLocationService.getAllOrgLocations();
		}
	
}
