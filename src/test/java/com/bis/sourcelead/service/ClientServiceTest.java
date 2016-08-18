package com.bis.sourcelead.service;

import java.util.Date;

import org.json.JSONObject;
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
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgRelations;
import com.bis.sourcelead.entity.User;

/**
 * 
 * @author PrasadK
 * @date 08th jul 2016
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = WebAppConfig.class, loader = AnnotationConfigWebContextLoader.class)
public class ClientServiceTest {


	@Autowired
	OrgRelationsService orgRelationsService;

	@Autowired
	ClientService clientService;


	private static final Logger logger = LoggerFactory.getLogger(ClientServiceTest.class);

	//@Test
	public void addOrgRelations() throws Exception {
		OrgRelations orgRelations = new OrgRelations();
		logger.info("Client Service Test");
		orgRelations.setId(7l);
		orgRelations.setOrgName("cts");
		orgRelations.setWebSite("www.cts.com");
		orgRelations.setPhone("09866712679");
		orgRelations.setEmail("korasikha.prasad@gmail.com");
		orgRelations.setCountry("india");
		orgRelations.setState("Ap");
		orgRelations.setCity("Vizag");
		OrgLocations clientLocation = new OrgLocations();
		clientLocation.setId(1L);
		orgRelations.setClientLocationId(clientLocation);
		
		OrgLocations location = new OrgLocations();
		location.setId(3L);
		
		orgRelations.setLocationId(location);
		orgRelations.setRelationType("Customer Relation");
		orgRelations.setCreatedDate(new Date());
		orgRelations.setCreatedBy("PrasadK");
		orgRelations.setUpdatedDate(new Date());
		orgRelations.setUpdatedBy("ABC");
		orgRelationsService.addOrgRelations(orgRelations);
		System.out.println("Successfully Added!!!!");
	}
	
	
	@Test
	public void saveClient() throws Exception {
		
		 JSONObject jsonObj = new JSONObject();
		 jsonObj.put("orgName", "HCL");
		 jsonObj.put("country", "India");
		 jsonObj.put("state", "Telangana");
		 jsonObj.put("city", "Hyderabad");
		 jsonObj.put("webSite", "www.hcl.com");
		 jsonObj.put("emailId", "vishnu123@hcl.com");
		 jsonObj.put("phone", "1234567891");
		 jsonObj.put("maxTsRowNum", "3");
		 
		 jsonObj.put("1projectId", "300");
		 jsonObj.put("1projectName", "HCL Project1");
		 jsonObj.put("1projectManager", "3");
		 jsonObj.put("level2Manager", "");
		 jsonObj.put("1projectStartDate", "2016-08-22");
		 jsonObj.put("1projectEndDate", "2016-08-30");
		
		 
		 jsonObj.put("2projectId", "301");
		 jsonObj.put("2projectName", "HCL Project2");
		 jsonObj.put("2projectManager", "3");
		 jsonObj.put("level2Manager", "");
		 jsonObj.put("2projectStartDate", "2016-08-22");
		 jsonObj.put("2projectEndDate", "2016-08-30");
		 
		 
		 
		 User user = new User();
		 user.setId(1L);
		 user.setUserCode("sleadAdmin");
		 
		 OrgLocations orgLocations = new OrgLocations();
		 orgLocations.setId(1L);
		 orgLocations.setLocationCode("sleadIN");
		 
		 clientService.saveClient(jsonObj, user, orgLocations);
		 
		 
	}

	
}