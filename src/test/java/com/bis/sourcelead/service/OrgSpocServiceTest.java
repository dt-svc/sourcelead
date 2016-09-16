package com.bis.sourcelead.service;

import java.util.Date;
import java.util.List;

import org.json.JSONObject;
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
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgSpoc;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.enums.Status;
import com.bis.sourcelead.service.impl.UserServiceImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = WebAppConfig.class, loader = AnnotationConfigWebContextLoader.class)
public class OrgSpocServiceTest {

	@Configuration
	static class UserServiceTestContextConfiguration {
		@Bean
		public UserService userService() {
			return new UserServiceImpl();
		}
	}

	@Autowired
	OrgSpocService orgSpocService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	OrgLocationService orgLocationService;

	private static final Logger logger = LoggerFactory.getLogger(OrgSpocServiceTest.class);
	
	
	//@Test
	public void addOrgSpoc() throws Exception{
		OrgSpoc orgSpoc = new OrgSpoc();
		orgSpoc.setOrgRelationId(3l);
		
		User user = new User();
		user.setCreatedBy("ajith");
		user.setCreatedDate(new Date());
		user.setDocFile(new byte[4096]);
		user.setEmail("ajith@gmail.com");
		user.setFileContentType("open");
		user.setFirstName("matta");
		user.setIsMailSent(Status.N);
		user.setJobTitle("developer");
		user.setLastName("reddy");
		user.setLocation("hyderabad");
		user.setMobilePhone("890876590");
		user.setPassword("bis@123");
		user.setStatus("active");
		user.setTerminateReason("fail");
		user.setUpdatedBy("srinivas");
		user.setUpdatedDate(new Date());
		user.setUserCode("132425");
		user.setUserName("shiva");
		user.setUserState(Status.ACTIVE);
		user.setUserType("employee");
		user.setWorkPhone("1233467890");
		orgSpoc.setSpocUser(user);
		
		User ownerUser = userService.getUserById(3l);
		orgSpoc.setOwnerUser(ownerUser);
		
		orgSpoc.setInvitationStatus("New");
		
		OrgLocations orgLocations = orgLocationService.getOrgLocations(3L);
		orgSpoc.setInitiatorLocationId(orgLocations);
		OrgLocations clientOrgLocations = orgLocationService.getOrgLocations(5L);
		orgSpoc.setResponderLocationId(clientOrgLocations);
		orgSpoc.setSpocStatus("Active");
		orgSpoc.setStatus("Active");
		
		
		orgSpoc = orgSpocService.addOrgSpoc(orgSpoc);
		logger.info("added org spoc sucessfully....");
	}
	
	@Test
	public void addVendorContacts() throws Exception{
//		JSONObject jsonObj,User user,OrgLocations orgLocations, String orgRelationId, String clientLocationId
		JSONObject jsonObj = new JSONObject();;
		User user = new User();;
		OrgLocations orgLocations = new OrgLocations();
		
		String orgRelationId = "";
		String clientLocationId = "";
		String responderType = "";
		
		
		orgSpocService.addVendorContacts(jsonObj, user, orgLocations, orgRelationId, clientLocationId,responderType);
	}
	
	//@Test
	public void getAllSpocsByOrgRelationId() throws Exception{
		List<OrgSpoc> list = orgSpocService.getAllSpocsByOrgRelationId(3l, 3l, 3l);
		logger.info("retrieved sucessfully...."+list.size());
	}
	
	//@Test
	public void getOrgSpocById() throws Exception{
		OrgSpoc orgSpoc = orgSpocService.getOrgSpocById(6l);
		logger.info("retrieved sucessfully...."+orgSpoc.getSpocStatus());
	}
	
	//@Test
	public void validateSpacEmailIdByOwnerUserIdAndOrgRelationId() throws Exception{
		OrgSpoc orgSpoc = orgSpocService.validateSpacEmailIdByOwnerUserIdAndOrgRelationId("info@acer123.com", 1l, 3l);
		logger.info("retrieved sucessfully...."+orgSpoc.getSpocStatus());
	}
	
	//@Test
	public void getSpocAndOwnerDetailsById() throws Exception{
		OrgSpoc orgSpoc = orgSpocService.getSpocAndOwnerDetailsById(4l, 3l);
		logger.info("retrieved sucessfully...."+orgSpoc.getSpocStatus());
	}
	
	//@Test
	public void getOwnerIdbySpocId() throws Exception{
		List<Long> list = orgSpocService.getOwnerIdbySpocId(6l, 3l);
		logger.info("retrieved sucessfully...."+list.size());
	}
	
	//@Test
	public void getSpocUserByUserId() throws Exception{
		OrgSpoc orgSpoc = orgSpocService.getSpocUserByUserId(4l);
		logger.info("retrieved sucessfully...."+orgSpoc.getSpocStatus());
	}
}
