package com.bis.sourcelead.service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.when;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.intercept.aspectj.AspectJMethodSecurityInterceptor;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;

import com.bis.sourcelead.WebAppConfig;
import com.bis.sourcelead.dao.OrgLocationsDao;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.enums.Status;
import com.bis.sourcelead.form.OrgSettings;
import com.bis.sourcelead.service.impl.OrgLocationsServiceImpl;
import com.mchange.util.AssertException;

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
	
	/*@Mock
	OrgLocationsDao orgLocationsDao;
	
	@Before
    public void setUp()
        throws Exception
    {
        MockitoAnnotations.initMocks( this );
        orgLocationService = new OrgLocationsServiceImpl();
    }*/

	private static final Logger logger = LoggerFactory.getLogger(OrgLocationService.class);

	//@Test
	public void addOrgLocation() throws Exception {
		
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
		orgLocations.setLocationCode("sleadUS");
		orgLocations.setStatus(Status.ACTIVE);
		orgLocations.setCreatedBy("Vinu");
		orgLocations.setCreatedDate(new Date());
		orgLocations.setUpdatedBy("Prasanna");
		orgLocations.setUpdatedDate(new Date());
		orgLocationService.addOrgLocations(orgLocations);
		System.out.println("Successfully Added!!!!");
	}

	// @Test
	public void getOrgLocations() throws Exception {
		OrgLocations orgLocations = orgLocationService.getOrgLocations(3L);
		System.out.println("get value of ind" + orgLocations.getOrg());
	}

	// @Test
	public void deleteOrgLocations() throws Exception {
		long l = 4;
		orgLocationService.deleteOrgLocations(l);
		System.out.println("Successfully deleted!!!!");
	}

	// @Test
	public void updateOrgLocations() throws Exception {
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
		orgLocations.setStatus(Status.INACTIVE);
		orgLocations.setCreatedBy("Laxman");
		orgLocations.setCreatedDate(new Date());
		orgLocations.setUpdatedBy("Prasanna");
		orgLocations.setUpdatedDate(new Date());
		orgLocationService.updateOrgLocations(orgLocations);
	}

	//@Test
	public void getAllOrgLocations() throws Exception {
		/*
		OrgLocations orgLocations = new OrgLocations();
		
		List<String> messages = Arrays.asList("Hello", "World!", "How", "Are", "You");
		
		List<String> messageslist = Arrays.asList("Hello", "World!", "How", "Are", "You");
		
		when( orgLocationsDao.getAllOrgLocations()).thenReturn( messages );
		Assert.assertEquals(messages, messageslist);*/
		
		List<OrgLocations> orgLocationsList = orgLocationService.getAllOrgLocations();
	}
	
	@Test
	public void getOrgLocationsByLocationCode() throws Exception{
		
		OrgLocations orgLocations = new OrgLocations();
		orgLocations.setLocationCode("sleadUS");
		orgLocations = orgLocationService.getOrgLocationsByLocationCode(orgLocations.getLocationCode());
		System.out.println("locations are..."+orgLocations.getCountry());
	}
	
	//@Test
	public void getOrgLocationsByOrgCode() throws Exception{
		OrgLocations orgLocations = new OrgLocations();
		Org org = new Org();
		orgLocations.setId(1l);
		orgLocations.setLocationCode("sleadIN");
		org.setId(1L);
		org.setOrgCode("slead");
		orgLocations.setOrg(org);
		orgLocations = orgLocationService.getOrgLocationsByOrgCode(org.getId().toString());
		logger.info("getOrgLocationsByOrgCode " + orgLocations.getCountry());
	}
	
	//@Test
	public void saveOrgLocations() throws Exception{
		OrgLocations orgLocations = new OrgLocations();
		OrgSettings orgsettings = new OrgSettings();
		Org org = new Org();
		org.setId(1l);
		orgLocations.setOrg(org);
		org.setOrgName("BIS");
		orgsettings.setId(3);
		orgsettings.setCity("hyd");
		orgsettings.setZipcode("34567");
		orgsettings.setFax("789");
		orgsettings.setPhone("9087656789");
		orgLocations.setId(3l);
		orgLocations.setLocationCode("sleadIN");
		orgLocations.setCity("hyd");
		orgLocations.setZipCode("34567");
		orgLocations.setFax("789");
		orgLocations.setPhone("9087656789");
		
		orgLocations = orgLocationService.saveOrgLocations(orgsettings,orgLocations);
		logger.info("saveOrgLocations " + orgLocations.getId());
	}
	
	//@Test
	public void findByEmail() throws Exception{
		OrgLocations orgLocations = new OrgLocations();
		orgLocations = orgLocationService.findByEmail("abc@gmail.com");
		if(orgLocations != null)
		{
		logger.info("findByEmail " + orgLocations.getId());
	}
	}
}
