package com.bis.sourcelead.couchbase.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bis.sourcelead.couchbase.OrgLocationCacheDTO;
import com.bis.sourcelead.couchbase.UserRoleSessionProfileDTO;
import com.bis.sourcelead.couchbase.util.UserStatsCacheHelper;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.UserRole;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.SourceleadActionsService;
import com.bis.sourcelead.service.TabsService;
import com.bis.sourcelead.service.UserRoleService;
import com.bis.sourcelead.util.SourceLeadSessionManager;
import com.couchbase.client.java.document.json.JsonObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/app")
public class UserProfileSessionRestController {
	
	@Autowired
	OrgLocationService orgLocationService;
	
	@Autowired
	UserRoleService userRoleService;
	
	@Autowired
	SourceleadActionsService sourceleadActionsService;
	
	@Autowired
	TabsService tabsService;
	
	
	@Autowired
	UserStatsCacheHelper userStatsCacheHelper;
	
	 @RequestMapping(value = "/ws/storeOrgLocationsInCouch", method = RequestMethod.GET)
	 public void storeOrgLocationsInCouch() throws Exception {
		
		 List<OrgLocations> orgLocationsList =  orgLocationService.getAllOrgLocations();
		 
		 if(orgLocationsList != null && !orgLocationsList.isEmpty()) {
			 System.out.println("Uploading orgLocations details to Couchbase starts......");
			 for(OrgLocations orgLocations : orgLocationsList) {
				 OrgLocationCacheDTO orgLocationDTO = userStatsCacheHelper.getOrgLocationCacheDetails(orgLocations);
				 ObjectMapper sessionObj = new ObjectMapper();
		         try {
					String sessionJsonStr = sessionObj.writeValueAsString(orgLocationDTO);
					JsonObject jsonObject = JsonObject.fromJson(sessionJsonStr);
					StringBuilder orgLocationKey = new StringBuilder();
					orgLocationKey.append("OrgLocation_").append(orgLocationDTO.getOrgLocationCode());
					SourceLeadSessionManager.setUserProfileJsonString(orgLocationKey.toString(), "userProfile", jsonObject);
				} catch (JsonProcessingException e) {
					e.printStackTrace();
				}
			 }
			 System.out.println("Uploading orgLocations details to Couchbase ends");
		 }
	 }
	 
	 
	 
	 @RequestMapping(value = "/ws/storeUsersProfileInCouch", method = RequestMethod.GET)
	 public void storeUsersProfileInCouch() throws Exception {
		
		 List<UserRole> userRolesList =  userRoleService.getAllUserRoles();
		 
		 if(userRolesList != null && !userRolesList.isEmpty()) {
			 System.out.println("Uploading userprofile details to Couchbase starts......");
			 for(UserRole userRole : userRolesList) {
				 
				 UserRoleSessionProfileDTO userRoleDTO = userStatsCacheHelper.getUserRoleCacheDetails(userRole);
				 ObjectMapper sessionObj = new ObjectMapper();
		         try {
					String sessionJsonStr = sessionObj.writeValueAsString(userRoleDTO);
					JsonObject jsonObject = JsonObject.fromJson(sessionJsonStr);
					StringBuilder userRoleKey = new StringBuilder();
					userRoleKey.append(userRole.getUser().getUserName()).append("_AUTH_").append(userRole.getUuid());
					SourceLeadSessionManager.setUserProfileJsonString(userRoleKey.toString(), "userProfile", jsonObject);
				} catch (JsonProcessingException e) {
					e.printStackTrace();
				}
			 }
			 System.out.println("Uploading userprofile details to Couchbase ends");
		 }
	 }

}
