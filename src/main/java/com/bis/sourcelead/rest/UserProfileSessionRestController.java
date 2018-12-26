package com.bis.sourcelead.rest;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.couchbase.UserDashboardCountsDTO;
import com.bis.sourcelead.couchbase.UserDashboardJobsCountDTO;
import com.bis.sourcelead.couchbase.UserLocationStatsCountDTO;
import com.bis.sourcelead.couchbase.UserProfileLocationsDTO;
import com.bis.sourcelead.couchbase.UserSessionProfileDTO;
import com.bis.sourcelead.dto.UserProfileDetailsDTO;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.SourceleadActions;
import com.bis.sourcelead.entity.Timezone;
import com.bis.sourcelead.entity.UserRole;
import com.bis.sourcelead.enums.Result;
import com.bis.sourcelead.service.CandidateService;
import com.bis.sourcelead.service.LookUpDataService;
import com.bis.sourcelead.service.RequirementSubmissionService;
import com.bis.sourcelead.service.SourceleadActionsService;
import com.bis.sourcelead.service.TabsService;
import com.bis.sourcelead.service.TimeZoneService;
import com.bis.sourcelead.service.UserRoleService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.SourceLeadSessionManager;
import com.couchbase.client.java.document.json.JsonObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/app")
public class UserProfileSessionRestController {
	private static final Logger logger = LoggerFactory.getLogger(UserProfileSessionRestController.class);
	
	@Autowired
	UserService userService;
	
	@Autowired
	UserRoleService userRoleService;
	
	@Autowired
	SourceleadActionsService sourceleadActionsService;
	
	@Autowired
	TabsService tabsService;
	
	@Autowired
	CandidateService candidateService;
	
	@Autowired
	LookUpDataService lookUpDataService;
	
	@Autowired
	TimeZoneService timeZoneService;
	
	@Autowired
	RequirementSubmissionService requirementSubmissionService;
	
	 @RequestMapping(value = "/ws/storeUsersProfileInCouch", method = RequestMethod.GET)
	 public void storeUsersProfileInCouch() {
		
		 
		 
		 try {
			 UserSessionProfileDTO userProfileDTO = new UserSessionProfileDTO();
			 List<UserProfileDetailsDTO> userList = userService.getAllSourceleadUsers();
			 if(userList != null && !userList.isEmpty()) {
				 
				 System.out.println("Adding User Profiles In Couch Starts......");
				 logger.debug("Adding User Profiles In Couch Starts......");
				 
				 for(UserProfileDetailsDTO user : userList) {
					 
					 List<UserRole> userRoleList = null;
					 Map<Long,UserProfileLocationsDTO> orgLocationsList = new HashMap<Long,UserProfileLocationsDTO>();
			        
			         userProfileDTO.setFirstName(user.getFirstName());
			 	     userProfileDTO.setLastName(user.getLastName());
			 	     userProfileDTO.setMiddleName(user.getMiddleName());
			 	     userProfileDTO.setUserId(user.getUserId().longValue());
			 	     userProfileDTO.setEmail(user.getEmail());
			 	     userProfileDTO.setGender(user.getGender());
			 	     
			 	     StringBuilder userName = new StringBuilder();
			 	     if(StringUtils.isNoneBlank(user.getLastName())) {
			 	    	userName  = userName.append(user.getFirstName()).append(" ").append(user.getLastName());
			 	     }else {
			 	    	userName  = userName.append(user.getFirstName());
			 	     }
			 	     
			 	     userProfileDTO.setUserName(userName.toString());
			 	  
			 	     if(StringUtils.isNoneBlank(user.getImageUrl())){
			    	    userProfileDTO.setImageUrl(user.getImageUrl());
						 }else{
							userProfileDTO.setImageUrl("/resources/images/userprofile-img.png");
						 }
			 	     
			         try {
			        	    if(user != null){
			        		     userRoleList = userRoleService.getUserLoginProfilesListByUserId(user.getUserId().longValue());
			        		     if(null != userRoleList && userRoleList.size() > 0){
			        		    	 for(UserRole userRole : userRoleList){
			        		    		 
				        		    		List<SourceleadActions> actionsList = null;
				        			        List<String> tabNamesList = null;
				        			        List<String> actionRolesList = new ArrayList<String>();
				        			        List<Long> actionIdsList = new ArrayList<Long>();
				        			        
				        			        
			        		    		    OrgLocations orgLocations = userRole.getOrgLocations();
			        		        	    actionsList = sourceleadActionsService.getLoginUserRoleActionsByOrgLocationId(null, userRole.getRoles().getId(), orgLocations.getId());
			        		        	    
			        		        	    for(SourceleadActions actions : actionsList){
			        		        	    	actionIdsList.add(actions.getId());
			        		        	    	actionRolesList.add(actions.getActionRole());
			        		        	    }
			        		        	    
			        		        	    tabNamesList = tabsService.getTabsNamesListByActionsIds(actionIdsList);
			        		        	    
			        		        	    UserProfileLocationsDTO orgLocationDTO =  getOrgLocationDetails(orgLocations,userRole);
			        		        	    orgLocationDTO.setMenuList(tabNamesList);
			        		        	    orgLocationDTO.setRolesList(actionRolesList);
			        		        	    Candidate candidate = candidateService.getCandidateByConsultantUserId(user.getUserId().longValue(), orgLocations.getId());
				        		        	   if(candidate != null) {
				        		        		   orgLocationDTO.setCandidateId(candidate.getId());
				        		        	   }
			        		        	    orgLocationDTO.setUserType(userRole.getUserType());
			        		        	    orgLocationsList.put(userRole.getId(),orgLocationDTO);
			        		    	 }
			        		     }
			        		     
			        		    
			        	     }
			        	    
			               } catch (Exception e) {
							e.printStackTrace();
						 }
			         
			         userProfileDTO.setUserProfileLocationsList(orgLocationsList);
			         ObjectMapper sessionObj = new ObjectMapper();
			         try {
						String sessionJsonStr = sessionObj.writeValueAsString(userProfileDTO);
						JsonObject jsonObject = JsonObject.fromJson(sessionJsonStr);
						SourceLeadSessionManager.setUserProfileJsonString(user.getUserName(), "userProfile", jsonObject);
					} catch (JsonProcessingException e) {
						e.printStackTrace();
					}
				 }
				 
				 System.out.println("Adding User Profiles In Couch Ends......");
				 logger.debug("Adding User Profiles In Couch Ends......");
			 }
			 
		 }catch (Exception e) {
			e.printStackTrace();
		}
		 
         
	 }
	 
	 
	 private UserProfileLocationsDTO getOrgLocationDetails(OrgLocations orgLocations,UserRole useRole){
		 UserProfileLocationsDTO orgLocationDTO = new UserProfileLocationsDTO();
		 orgLocationDTO.setOrgId(orgLocations.getOrg().getId());
		 orgLocationDTO.setOrgName(orgLocations.getOrgLocationName());
		 orgLocationDTO.setOrgCode(orgLocations.getOrg().getOrgCode());
		 orgLocationDTO.setOrgLocationId(orgLocations.getId());
		 orgLocationDTO.setOrgLocationName(orgLocations.getOrgLocationName());
		 orgLocationDTO.setLocationCode(orgLocations.getLocationCode());
		 if(StringUtils.isNoneBlank(orgLocations.getOrgLocationAddress())) {
			 orgLocationDTO.setLocation(orgLocations.getOrgLocationAddress());
		 }else {
			 orgLocationDTO.setLocation(orgLocations.getCountry());
		 }
		
		 orgLocationDTO.setRoleId(useRole.getId());
		 orgLocationDTO.setRoleName(useRole.getRoles().getDisplayName());
		 orgLocationDTO.setDesignation(useRole.getDesignation());
		 orgLocationDTO.setUserState(useRole.getUserState().toString());
		 orgLocationDTO.setUserType(useRole.getUserType());
		 orgLocationDTO.setDateFormat(orgLocations.getDateFormat());
		 orgLocationDTO.setTimeZoneId(orgLocations.getTimezone().getId());
		 orgLocationDTO.setCountry(orgLocations.getCountry());
		 return orgLocationDTO;
	 }
	
	 
	 
	 @RequestMapping(value = "/ws/storeUsersDashboardStatsInCouch", method = RequestMethod.GET)
	 public void storeUsersDashboardStatsInCouch() {
		 
		 try {
			 UserDashboardCountsDTO dashboardCountsDTO = new UserDashboardCountsDTO();
			 List<Map<Long,UserLocationStatsCountDTO>> userLocationsList = new ArrayList<>();
			 List<UserProfileDetailsDTO> userList = userService.getAllSourceleadUsers();
			 if(userList != null && !userList.isEmpty()) {
				 
				 System.out.println("Adding User Profiles In Couch Starts......");
				 logger.debug("Adding User Profiles In Couch Starts......");
				 
				 for(UserProfileDetailsDTO user : userList) {
					 
					 List<UserRole> userRoleList = null;
					
			 	     dashboardCountsDTO.setUserId(user.getUserId().longValue());
			 	     
			         try {
			        	    if(user != null){
			        		     userRoleList = userRoleService.getUserLoginProfilesListByUserId(user.getUserId().longValue());
			        		     if(null != userRoleList && userRoleList.size() > 0){
			        		    	 for(UserRole userRole : userRoleList){
			        		    		    Map<Long,UserLocationStatsCountDTO> orgLocationsList = new HashMap<Long,UserLocationStatsCountDTO>();
			        		    		    OrgLocations orgLocations = userRole.getOrgLocations();
			        		    		    orgLocationsList = getDashboardJobsStatsCount(orgLocations,userRole);
			        		    		    userLocationsList.add(orgLocationsList);
			        		    	 }
			        		     }
			        		    
			        	     }
			        	    
			               } catch (Exception e) {
							e.printStackTrace();
						 }
			         
			         dashboardCountsDTO.setUserProfileLocationsList(userLocationsList);
			         ObjectMapper sessionObj = new ObjectMapper();
			         try {
						String sessionJsonStr = sessionObj.writeValueAsString(dashboardCountsDTO);
						JsonObject jsonObject = JsonObject.fromJson(sessionJsonStr);
						SourceLeadSessionManager.setUserDashboardStatsJsonString(user.getUserName(), "userDashboardStats", jsonObject);
					} catch (JsonProcessingException e) {
						e.printStackTrace();
					}
				 }
				 
				 System.out.println("Adding User Profiles In Couch Ends......");
				 logger.debug("Adding User Profiles In Couch Ends......");
			 }
			 
		 }catch (Exception e) {
			e.printStackTrace();
		}
	 }
	
	 
	 
	 private Map<Long,UserLocationStatsCountDTO> getDashboardJobsStatsCount(OrgLocations loginOrgLocations,UserRole userRole) {

		 Map<Long,UserLocationStatsCountDTO> orgLocationsList = new HashMap<Long,UserLocationStatsCountDTO>(); 
		 UserLocationStatsCountDTO locationStatsCount = new UserLocationStatsCountDTO();
		
		 try {
		 Timezone timezone = null;
			if(loginOrgLocations.getTimezone() != null){
				timezone = timeZoneService.getTimezone(loginOrgLocations.getTimezone().getId());
			}
			
			Date startUTCDate = null;
			Date endUTCDate = null;
			DateFormat TZ_DF_TS = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
			DateTimeFormatter outputFormatter = DateTimeFormat.forPattern("yyyy-MM-dd 00:00:00");
			DateTimeFormatter outputFormatter1 = DateTimeFormat.forPattern("yyyy-MM-dd 23:59:59");
			 
			 List<Map<String,UserDashboardJobsCountDTO>> jobsStatusCountList = new ArrayList<Map<String,UserDashboardJobsCountDTO>>(); 
			
			 Map<String,UserDashboardJobsCountDTO> jobsTodayStatusCountList = new HashMap<String,UserDashboardJobsCountDTO>(); 
			 DateTime zoned = new DateTime(DateTimeZone.forID(timezone.getTimeZoneId()));
			 String customStartTimeStr = outputFormatter.print(zoned);
			 startUTCDate = TZ_DF_TS.parse(customStartTimeStr);
			 String endDate = outputFormatter1.print(zoned);
			 Date endDateString = TZ_DF_TS.parse(endDate);
			 endUTCDate = endDateString;
			 
			 Integer[] requirementSubmissionTodayHistoryCounts = requirementSubmissionService.getRequirementSubmissionsListsByOrgLocationsIdAndSubmissionStatus(loginOrgLocations.getId(),"today", userRole.getUserType(), userRole.getUser(), startUTCDate, endUTCDate);
				 
			 UserDashboardJobsCountDTO todayCountDTO = new UserDashboardJobsCountDTO();
			 todayCountDTO.setApplicantsCount(requirementSubmissionTodayHistoryCounts[0]);
			 todayCountDTO.setInterviewsCount(requirementSubmissionTodayHistoryCounts[1]);
			 todayCountDTO.setClientSubmissionsCount(requirementSubmissionTodayHistoryCounts[2]);
			 todayCountDTO.setOffersCount(requirementSubmissionTodayHistoryCounts[3]);
			 jobsTodayStatusCountList.put("today", todayCountDTO);
			 jobsStatusCountList.add(jobsTodayStatusCountList);
			 locationStatsCount.setJobsCountsList(jobsStatusCountList);
			 

			Map<String,UserDashboardJobsCountDTO> jobsOneMonthStatusCountList = new HashMap<String,UserDashboardJobsCountDTO>(); 
			String startDate = outputFormatter.print(zoned);
			Date startDateUTC = TZ_DF_TS.parse(startDate);
			
			DateTime oneMonthZoned = zoned.minusDays(30);
			String oneMonthDate = outputFormatter.print(oneMonthZoned);
			Date oneMonthDateUTC = TZ_DF_TS.parse(oneMonthDate);
			
			Date[] dates = new Date[2];
			dates[0] = oneMonthDateUTC;
			dates[1] = startDateUTC;
			startUTCDate = dates[0]; 
			endUTCDate = dates[1];

			
			Integer[] requirementSubmissionOneMonthHistoryCounts = requirementSubmissionService.getRequirementSubmissionsListsByOrgLocationsIdAndSubmissionStatus(loginOrgLocations.getId(),"previous", userRole.getUserType(), userRole.getUser(), startUTCDate, endUTCDate);
			
			 UserDashboardJobsCountDTO oneMonthCountDTO = new UserDashboardJobsCountDTO();
			 oneMonthCountDTO.setApplicantsCount(requirementSubmissionOneMonthHistoryCounts[0]);
			 oneMonthCountDTO.setInterviewsCount(requirementSubmissionOneMonthHistoryCounts[1]);
			 oneMonthCountDTO.setClientSubmissionsCount(requirementSubmissionOneMonthHistoryCounts[2]);
			 oneMonthCountDTO.setOffersCount(requirementSubmissionOneMonthHistoryCounts[3]);
			 jobsOneMonthStatusCountList.put("oneMonth",oneMonthCountDTO);
			 jobsStatusCountList.add(jobsOneMonthStatusCountList);
			 locationStatsCount.setJobsCountsList(jobsStatusCountList);
			 
			 orgLocationsList.put(loginOrgLocations.getId(), locationStatsCount);
			 
		 }catch (Exception e) {
			e.printStackTrace();
		}
		   
	    return orgLocationsList;
		
	 }
	 

}
