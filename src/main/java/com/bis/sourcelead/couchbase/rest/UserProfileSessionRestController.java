package com.bis.sourcelead.couchbase.rest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.couchbase.OrgLocationCacheDTO;
import com.bis.sourcelead.couchbase.UserRoleSessionProfileDTO;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.SourceleadActions;
import com.bis.sourcelead.entity.UserRole;
import com.bis.sourcelead.service.CandidateService;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.OrgService;
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
	OrgService orgService;
	
	@Autowired
	CandidateService candiateService;
	
	 @RequestMapping(value = "/ws/storeOrgLocationsInCouch", method = RequestMethod.GET)
	 public void storeOrgLocationsInCouch() throws Exception {
		
		 List<OrgLocations> orgLocationsList =  orgLocationService.getAllOrgLocations();
		 
		 if(orgLocationsList != null && !orgLocationsList.isEmpty()) {
			 for(OrgLocations orgLocations : orgLocationsList) {
				 OrgLocationCacheDTO orgLocationDTO = new OrgLocationCacheDTO();
				 orgLocationDTO.setDocumentType(Constants.COUCH_DOCUMENT_TYPE_ORGLOCATION);
				 orgLocationDTO.setOrgLocationName(orgLocations.getOrgLocationName());
				 orgLocationDTO.setOrgLocationId(String.valueOf(orgLocations.getId()));
				 orgLocationDTO.setOrgName(orgLocations.getOrgName());
				 orgLocationDTO.setOrgId(String.valueOf((orgLocations.getOrg().getId())));
				 orgLocationDTO.setImgUrl(orgLocations.getOrg().getFileUrl());
				 orgLocationDTO.setRegisteredLocation(orgLocations.getOrgLocationAddress());
				 orgLocationDTO.setCountry(orgLocations.getCountry());
				 orgLocationDTO.setState(orgLocations.getState());
				 orgLocationDTO.setCity(orgLocations.getCity());
				 orgLocationDTO.setZipcode(orgLocations.getZipCode());
				 orgLocationDTO.setDateFormat(orgLocations.getDateFormat());
				 orgLocationDTO.setWebsite(orgLocations.getWebsite());
				 if(StringUtils.isNotBlank(orgLocations.getWeekOffDays())) {
					 orgLocationDTO.setWeekOffDays(orgLocations.getWeekOffDays());
				 }else {
					 orgLocationDTO.setWeekOffDays("Sat,Sun");
				 }
				
				 orgLocationDTO.setWorkingHours(String.valueOf(orgLocations.getWorkingDaysPerWeek()));
				 orgLocationDTO.setOrgShortCode(orgLocations.getGeneratedUserId());
				 orgLocationDTO.setOrgLocationCode(orgLocations.getLocationCode());
				 orgLocationDTO.setOrgCode(orgLocations.getOrg().getOrgCode());
				 orgLocationDTO.setIsCompanyOnboarded(orgLocations.getIsCompanyOnboarded());
				 
				 
			     DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
			     LocalDateTime now = LocalDateTime.now();  
			     orgLocationDTO.setLastUpdatedDate(dtf.format(now));
				
				 if(orgLocations.getUser() != null) {
					 orgLocationDTO.setPrimaryUserId(String.valueOf(orgLocations.getUser().getId()));
				 }
				
				 if(orgLocations.getTimezone() != null) {
					 orgLocationDTO.setTimeZoneId(String.valueOf(orgLocations.getTimezone().getId()));
				 }
				 
				 if(orgLocations.getOrg().getUser() != null) {
					 orgLocationDTO.setSuperUserId(String.valueOf(orgLocations.getOrg().getUser().getId()));
				 }
				 
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
		 }
	 }
	 
	 
	 
	 @RequestMapping(value = "/ws/storeUsersProfileInCouch", method = RequestMethod.GET)
	 public void storeUsersProfileInCouch() throws Exception {
		
		 List<UserRole> userRolesList =  userRoleService.getAllUserRoles();
		 
		 if(userRolesList != null && !userRolesList.isEmpty()) {
			 for(UserRole userRole : userRolesList) {
				 UserRoleSessionProfileDTO userRoleDTO = new UserRoleSessionProfileDTO();
				 userRoleDTO.setDocumentType(Constants.COUCH_DOCUMENT_TYPE_USER_LOGIN_ACCOUNT);
				 userRoleDTO.setUserRoleId(String.valueOf(userRole.getId()));
				 userRoleDTO.setUserId(String.valueOf(userRole.getUser().getId()));
				 userRoleDTO.setRoleId(String.valueOf(userRole.getRoles().getId()));
				 userRoleDTO.setRoleName(userRole.getRoles().getRoleName());
				 userRoleDTO.setRoleDisplayName(userRole.getRoles().getDisplayName());
				 userRoleDTO.setEmail(userRole.getUser().getEmail());
				 userRoleDTO.setFirstName(userRole.getUser().getFirstName());
				 userRoleDTO.setLastName(userRole.getUser().getLastName());
				 
				 if(userRole.getReportingManager() != null) {
					 userRoleDTO.setReportingManagerId(String.valueOf(userRole.getReportingManager().getId()));
				 }
				
				 if(userRole.getResourceManager() != null) {
					 userRoleDTO.setResourceManagerId(String.valueOf(userRole.getResourceManager().getId()));
				 }
				
				 userRoleDTO.setOrgLocationId(String.valueOf(userRole.getOrgLocations().getId()));
				 userRoleDTO.setUserState(userRole.getUserState().toString());
				 userRoleDTO.setUserType(userRole.getUserType());
				 userRoleDTO.setDesignation(userRole.getDesignation());
				 userRoleDTO.setIsGlobalUser(userRole.getIsGlobal());
				 userRoleDTO.setOrgLocationCode(userRole.getOrgLocations().getLocationCode());
				 if(userRole.getRoles().getRoleHierarchy() != null) {
					 userRoleDTO.setRoleHierarchyId(String.valueOf(userRole.getRoles().getRoleHierarchy().getId()));
				 }
				 userRoleDTO.setUserCode(userRole.getUser().getUserCode());
				 Candidate candidate = candiateService.getCandidateByConsultantUserId(userRole.getUser().getId(),userRole.getOrgLocations().getId());
				 if(candidate != null) {
					 userRoleDTO.setCandidateId(String.valueOf(candidate.getId()));
					 userRoleDTO.setCandidateUUID(candidate.getCandidateUUID());
				 }
				
				 StringBuilder userNameSB = new StringBuilder();
				 if (null != userRole.getUser().getLastName()) {
					 userNameSB.append(userRole.getUser().getFirstName()).append(" ").append(userRole.getUser().getLastName());
					} else {
						 userNameSB.append(userRole.getUser().getFirstName());
					}
				 userRoleDTO.setUserName(userNameSB.toString());
				 userRoleDTO.setImgUrl(userRole.getUser().getFileUrl());
				 
				 boolean isSuperUser = orgLocationService.isLoginUserIsSuperUser(userRole.getUser().getId(),userRole.getOrgLocations().getId());
					if(isSuperUser == true) {
						userRoleDTO.setIsSuperUser(Constants.YES);
					}else {
						userRoleDTO.setIsSuperUser(Constants.NO);
					}
					
					boolean isSuperAdmin = orgService.isLoginUserIsSuperAdmin(userRole.getUser().getId(),userRole.getOrgLocations().getOrg().getId());
					if(isSuperAdmin == true){
						userRoleDTO.setIsSuperAdmin(Constants.YES);
					}else{
						userRoleDTO.setIsSuperAdmin(Constants.NO);
					}
				 
				 if(userRole.getShift() != null) {
					 userRoleDTO.setShiftId(String.valueOf(userRole.getShift().getId()));
				 }
				 
				 if(userRole.getGradeId() != null) {
					 userRoleDTO.setGradeId(String.valueOf(userRole.getGradeId().getId()));
				 }
				 
				 userRoleDTO.setUuid(userRole.getUuid());
				 
			     List<SourceleadActions> actionsList = null;
		         List<String> tabNamesList = null;
		         List<String> actionRolesList = new ArrayList<String>();
		         List<Long> actionIdsList = new ArrayList<Long>();
				 
	             actionsList = sourceleadActionsService.getLoginUserRoleActionsByOrgLocationId(null, userRole.getRoles().getId(), userRole.getOrgLocations().getId());
        	    
	        	 if(actionsList != null && !actionsList.isEmpty()) {
	        		  for(SourceleadActions actions : actionsList){
		        	    	actionIdsList.add(actions.getId());
		        	    	actionRolesList.add(actions.getActionRole());
		        	    }
	        		  
	        		 tabNamesList = tabsService.getTabsNamesListByActionsIds(actionIdsList);
	        		 tabNamesList.add("Dashboard");
	         	    
	         	     userRoleDTO.setMenuList(tabNamesList);
	         	     userRoleDTO.setRolesList(actionRolesList);
	        	 }
	        	 
        	     DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
			     LocalDateTime now = LocalDateTime.now();  
			     userRoleDTO.setLastUpdatedDate(dtf.format(now));
				 
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
		 }
	 }

}
