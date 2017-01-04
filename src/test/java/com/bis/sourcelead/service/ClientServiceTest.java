package com.bis.sourcelead.service;

import java.util.List;

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

import com.bis.sourcelead.SecurityConfig;
import com.bis.sourcelead.WebAppConfig;
import com.bis.sourcelead.entity.Org;
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
@ContextConfiguration(classes = {WebAppConfig.class, SecurityConfig.class}, loader = AnnotationConfigWebContextLoader.class)
public class ClientServiceTest {


	@Autowired
	OrgRelationsService orgRelationsService;

	@Autowired
	ClientService clientService;


	private static final Logger logger = LoggerFactory.getLogger(ClientServiceTest.class);
	
	
	
	/**
	 * test case for client List
	 * @throws Exception
	 */
	@Test
	public void getAllClientsByOrgLocationId() throws Exception{
		List<OrgRelations> clientsList= orgRelationsService.getAllClientsByOrgLocationId(1L);
		System.out.println("Successfully Retrieved..."+clientsList.size());
	} 
	
	@Test
	public void storeClient() throws Exception{
		 JSONObject jsonObj = new JSONObject();
		 jsonObj.put("firstName", "Demo_final13");
		 jsonObj.put("lastName", "User");
		 jsonObj.put("email", "demomail123@gmail.com");
		 jsonObj.put("phoneNumber", "890876590");
		 jsonObj.put("roleType", "Admin");
		 jsonObj.put("isMailSent", "Y");
		 jsonObj.put("employeeId", "123");
		 jsonObj.put("rate", "15000");
		 jsonObj.put("invoiceFrequency", "Monthly");
		 jsonObj.put("dateofJoin", "2016-08-17");
		 jsonObj.put("dayHours", "8");
		 jsonObj.put("projectId", "1");
		 
		 jsonObj.put("orgName", "tcs");
		 jsonObj.put("companyWebsite", "www.tcsh.com");
		 jsonObj.put("webSite", "www.tcsh.com");
		 jsonObj.put("email", "demomail123@gmail.com");
		 jsonObj.put("phone", "890876590");
		 jsonObj.put("country", "India");
		 jsonObj.put("state", "Telangana");
		 jsonObj.put("city", "Hyderabad");
		 jsonObj.put("owner", "3");
		 jsonObj.put("spocStatus", "Active");
		 
		 User users = new User();
		 users.setUserCode("sdf21d");
		 users.setFirstName("Raj");
		 users.setLastName("Tarun");
		 
		 OrgLocations orgLocations = new OrgLocations();
		 orgLocations.setId(3L);
		 orgLocations.setLocationCode("gBLoIN");
		 
		 String responderType = "Client";
		 
		 Org loginOrg = new Org();
		 loginOrg.setOrgName("Hangonn");
		 
		 clientService.storeClient(jsonObj, users, orgLocations, responderType, loginOrg);
		logger.info("Client details Stored Successfully....");
	}
	
	@Test
	public void updateVendorDetails() throws Exception {
		
		 JSONObject jsonObj = new JSONObject();
		 jsonObj.put("vendorName", "ibm");
		 jsonObj.put("vendorCountry", "India");
		 jsonObj.put("vendorState", "Telangana");
		 jsonObj.put("vendorCity", "Hyderabad");
		 jsonObj.put("companyWebsite", "www.ibm.com");
		 jsonObj.put("level", "2");
		 
		 Long orgRelationId = 5l;
		 Long vendorLocationId = 8l;
		 Long loginOrgLocationId = 3l;
		 
		 clientService.updateVendorDetails(jsonObj, orgRelationId, vendorLocationId, loginOrgLocationId);
		 logger.info("Vendor details updated sucessfully sucessfully...");
	}
	
	@Test
	public void updateVendorContactDetails() throws Exception {
				
		JSONObject jsonObj = new JSONObject();
		 jsonObj.put("vendorContactFirstName", "rajesh");
		 jsonObj.put("vendorContactLastName", "khanna");
		 jsonObj.put("mobilePhone", "9876543210");
		 jsonObj.put("spocStatus", "Active");
		 jsonObj.put("email", "rajesh@gmail.com");
		 jsonObj.put("owner", "3");
		 User user = new User();
		 user.setId(1L);
		 Long spocId = 5l;
		 Long loginOrgId = 2l;
		//clientService.updateVendorContactDetails(jsonObj, spocId, loginOrgId,user);
		logger.info("Vendor details updated sucessfully...");
	}
}