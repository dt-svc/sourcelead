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
import com.bis.sourcelead.dto.AddUserDTO;
import com.bis.sourcelead.dto.CandidateDTO;
import com.bis.sourcelead.dto.ClientDTO;
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
	public void storeClient() throws Exception
	{
		AddUserDTO addUserDto = new AddUserDTO();
		addUserDto.setFirstName("Demo_final13");
		addUserDto.setLastName("User");
		addUserDto.setEmail("demomail123@gmail.com");
		addUserDto.setPhoneNumber("890876590");
		addUserDto.setRoleType("Admin");
		addUserDto.setIsMailSent("Y");
		addUserDto.setEmployeeId("123");
		addUserDto.setRate("15000");
		addUserDto.setDateofJoin("2016-08-17");
		addUserDto.setDayHours("8");
		CandidateDTO candidto = new CandidateDTO();
		candidto.setFirstName("Demo_final13");
		candidto.setLastName("User");
		candidto.setEmail("demomail123@gmail.com");
		candidto.setPhoneNumber("890876590");
		
		
		ClientDTO dto = new ClientDTO();
		 dto.setVendorName("ibm");
		 dto.setCountry("India");
		 dto.setState("Telangana");
		 dto.setCity("Hyderabad");
		 dto.setCompanyWebsite("www.ibm.com");
		 dto.setLevel("2");
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
		clientService.storeClient(dto,addUserDto,candidto, users, orgLocations, responderType, loginOrg);
		logger.info("Client details Stored Successfully....");
	}
	
	@Test
	public void updateVendorDetails() throws Exception {
		
		ClientDTO dto = new ClientDTO();
		 dto.setVendorName("ibm");
		 dto.setCountry("India");
		 dto.setState("Telangana");
		 dto.setCity("Hyderabad");
		 dto.setCompanyWebsite("www.ibm.com");
		 dto.setLevel("2");
		 
		 Long orgRelationId = 5l;
		 Long vendorLocationId = 8l;
		 Long loginOrgLocationId = 3l;
		 
		 clientService.updateVendorDetails(dto, orgRelationId, vendorLocationId, loginOrgLocationId,new User());
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