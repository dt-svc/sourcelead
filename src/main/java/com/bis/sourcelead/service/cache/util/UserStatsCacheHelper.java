package com.bis.sourcelead.service.cache.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.couchbase.ConnectionManager;
import com.bis.sourcelead.couchbase.CouchbaseClient;
import com.bis.sourcelead.couchbase.UserProfileLocationsDTO;
import com.bis.sourcelead.couchbase.UserSessionProfileDTO;
import com.bis.sourcelead.dto.UserProfileDetailsDTO;
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
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.SourceLeadSessionManager;
import com.couchbase.client.java.document.json.JsonObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/** 
 * 
 */

@Component
public class UserStatsCacheHelper {
	
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
	OrgLocationService orgLocationService;
	
	@Autowired
	OrgService orgService;
	
	
	static CouchbaseClient c = null;
	static{
		c = ConnectionManager.instance().getClient();
	}

	@SuppressWarnings("unchecked")
	public JSONObject getUserProfileLoginCacheDetails(String loginUserName) {
		
		 String[] usernameWithRole = loginUserName.split("##");
		 String userName = usernameWithRole[0];
		 String userRole = usernameWithRole[1];
		 JSONObject userProfileJSON = new JSONObject();
		 Object sv = null;
		 sv = c.getUserProfile(userName);
		 if(sv != null) {
		  
		  JSONParser parser = new JSONParser(); 
		  try {
			  JSONObject jsonObject = (JSONObject) parser.parse(sv.toString());
			  for(Iterator iterator = jsonObject.keySet().iterator(); iterator.hasNext();) {
				    String key = (String) iterator.next();
				     Object jsonValue = jsonObject.get(key);
				     JSONObject jsonObjVal = (JSONObject) parser.parse(jsonValue.toString());
				     for (Object okey : jsonObjVal.keySet()) {
				    	 String keyStr = (String)okey;
				         Object keyvalue = jsonObjVal.get(keyStr);
				         if(!keyStr.equalsIgnoreCase("userProfileLocationsList")) {
				        	 userProfileJSON.put(keyStr, keyvalue);
				         }
				    
				     if(keyStr.equalsIgnoreCase("userProfileLocationsList")) {
				    	 JSONObject locJsonObject = (JSONObject) parser.parse(keyvalue.toString());
					     for(Iterator itr = locJsonObject.keySet().iterator(); itr.hasNext();) {
					    	    String entitykey = (String) itr.next();
					    	    if(entitykey.equalsIgnoreCase(userRole)) {
					    	    	 Object attributeValue = locJsonObject.get(entitykey);
					    	    	 JSONObject jsonLocObjVal = (JSONObject) parser.parse(attributeValue.toString());
								     for (Object lockey : jsonLocObjVal.keySet()) {
								    	 String lockeyStr = (String)lockey;
								         Object lockeyvalue = jsonLocObjVal.get(lockeyStr);
								         userProfileJSON.put(lockeyStr, lockeyvalue);
								     }
					    	    }
					     }
				     }
				    
			     }
				}
			  
		} catch (ParseException e) {
			e.printStackTrace();
		}
	  }
		return userProfileJSON;
	}
	
	
	public HashMap<String,String> getUserStats(String loginUserName){
		
		 String[] usernameWithRole = loginUserName.split("##");
		 String userName = usernameWithRole[0];
		 String userRole = usernameWithRole[1];
		 HashMap<String,String> loginUserCacheMap = new HashMap<>();
		 Object sv = null;
		 sv = c.getUserProfile(userName);
		 if(sv != null) {
		  JSONParser parser = new JSONParser(); 
		  try {
			  JSONObject jsonObject = (JSONObject) parser.parse(sv.toString());
			  for(Iterator iterator = jsonObject.keySet().iterator(); iterator.hasNext();) {
				    String key = (String) iterator.next();
				     Object jsonValue = jsonObject.get(key);
				     JSONObject jsonObjVal = (JSONObject) parser.parse(jsonValue.toString());
				     for (Object okey : jsonObjVal.keySet()) {
				    	 String keyStr = (String)okey;
				         Object keyvalue = jsonObjVal.get(keyStr);
				         if(!keyStr.equalsIgnoreCase("userProfileLocationsList")) {
				        	 loginUserCacheMap.put(keyStr, String.valueOf(keyvalue)) ;
				         }
				    
				     if(keyStr.equalsIgnoreCase("userProfileLocationsList")) {
				    	 JSONObject locJsonObject = (JSONObject) parser.parse(keyvalue.toString());
					     for(Iterator itr = locJsonObject.keySet().iterator(); itr.hasNext();) {
					    	    String entitykey = (String) itr.next();
					    	    if(entitykey.equalsIgnoreCase(userRole)) {
					    	    	 Object attributeValue = locJsonObject.get(entitykey);
					    	    	 JSONObject jsonLocObjVal = (JSONObject) parser.parse(attributeValue.toString());
								     for (Object lockey : jsonLocObjVal.keySet()) {
								    	 String lockeyStr = (String)lockey;
								         Object lockeyvalue = jsonLocObjVal.get(lockeyStr);
								         if(!lockeyStr.equalsIgnoreCase("menuList") && !lockeyStr.equalsIgnoreCase("rolesList")) {
								        	 loginUserCacheMap.put(lockeyStr, String.valueOf(lockeyvalue)) ;
								         }
								         
								     }
					    	    }
					     }
				     }
				    
			     }
				}
			  
		} catch (ParseException e) {
			e.printStackTrace();
		}
	  }
		return loginUserCacheMap;
	}
	
	
	
	public void updateUserStats(Long userId,String loginUserName) {
		
		 try {
			 UserSessionProfileDTO userProfileDTO = new UserSessionProfileDTO();
			 UserProfileDetailsDTO user = userService.getUserProfileDetailsByUserId(userId);
				 
					 List<UserRole> userRoleList = null;
					 Map<Long,UserProfileLocationsDTO> orgLocationsList = new HashMap<Long,UserProfileLocationsDTO>();
			        
			         userProfileDTO.setFirstName(user.getFirstName());
			 	     userProfileDTO.setLastName(user.getLastName());
			 	     userProfileDTO.setMiddleName(user.getMiddleName());
			 	     userProfileDTO.setUserId(String.valueOf(user.getUserId().longValue()));
			 	     userProfileDTO.setEmail(user.getEmail());
			 	     userProfileDTO.setGender(user.getGender());
			 	     userProfileDTO.setUserCode(user.getUserCode());
			 	     
			 	     StringBuilder userName = new StringBuilder();
			 	     if(StringUtils.isNotBlank(user.getLastName())) {
			 	    	userName  = userName.append(user.getFirstName()).append(" ").append(user.getLastName());
			 	     }else {
			 	    	userName  = userName.append(user.getFirstName());
			 	     }
			 	     
			 	     userProfileDTO.setUserName(userName.toString());
			 	  
			 	     if(StringUtils.isNotBlank(user.getImageUrl())){
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
			        		        	    tabNamesList.add("Dashboard");
			        		        	    
			        		        	    UserProfileLocationsDTO orgLocationDTO =  getOrgLocationDetails(orgLocations,userRole);
			        		        	    orgLocationDTO.setMenuList(tabNamesList);
			        		        	    orgLocationDTO.setRolesList(actionRolesList);
			        		        	    Candidate candidate = candidateService.getCandidateByConsultantUserId(user.getUserId().longValue(), orgLocations.getId());
				        		        	   if(candidate != null) {
				        		        		   orgLocationDTO.setCandidateId(String.valueOf(candidate.getId()));
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
				 
			 
		 }catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private UserProfileLocationsDTO getOrgLocationDetails(OrgLocations orgLocations,UserRole userRole) throws Exception{
		 UserProfileLocationsDTO orgLocationDTO = new UserProfileLocationsDTO();
		 orgLocationDTO.setOrgId(String.valueOf(orgLocations.getOrg().getId()));
		 orgLocationDTO.setOrgName(orgLocations.getOrgLocationName());
		 orgLocationDTO.setOrgCode(orgLocations.getOrg().getOrgCode());
		 orgLocationDTO.setOrgLocationId(String.valueOf(orgLocations.getId()));
		 orgLocationDTO.setOrgLocationName(orgLocations.getOrgLocationName());
		 orgLocationDTO.setLocationCode(orgLocations.getLocationCode());
		 if(StringUtils.isNoneBlank(orgLocations.getOrgLocationAddress())) {
			 orgLocationDTO.setLocation(orgLocations.getOrgLocationAddress());
		 }else {
			 orgLocationDTO.setLocation(orgLocations.getCountry());
		 }
		
		 orgLocationDTO.setRoleId(String.valueOf(userRole.getId()));
		 orgLocationDTO.setRoleName(userRole.getRoles().getRoleName());
		 if(StringUtils.isNoneBlank(userRole.getDesignation())) {
			 orgLocationDTO.setDesignation(userRole.getDesignation()); 
		 }else {
			 orgLocationDTO.setDesignation(userRole.getRoles().getDisplayName());
		 }
		 orgLocationDTO.setIsGlobalAccess(userRole.getIsGlobal());
		 orgLocationDTO.setUserState(userRole.getUserState().toString());
		 orgLocationDTO.setUserType(userRole.getUserType());
		 orgLocationDTO.setDateFormat(orgLocations.getDateFormat());
		 orgLocationDTO.setTimeZoneId(String.valueOf(orgLocations.getTimezone().getId()));
		 orgLocationDTO.setCountry(orgLocations.getCountry());
		 
		 if(StringUtils.isNotBlank(orgLocations.getOrg().getFileUrl())) {
			 orgLocationDTO.setOrgImageUrl(orgLocations.getOrg().getFileUrl());
		 }
		 
		 boolean isSuperUser = orgLocationService.isLoginUserIsSuperUser(userRole.getUser().getId(),orgLocations.getId());
			if(isSuperUser == true) {
				orgLocationDTO.setIsSuperUser(Constants.YES);
			}else {
				orgLocationDTO.setIsSuperUser(Constants.NO);
			}
			
			boolean isSuperAdmin = orgService.isLoginUserIsSuperAdmin(userRole.getUser().getId(),orgLocations.getOrg().getId());
			if(isSuperAdmin == true){
				orgLocationDTO.setIsSuperAdmin(Constants.YES);
			}else{
				orgLocationDTO.setIsSuperAdmin(Constants.NO);
			}
			
		 return orgLocationDTO;
	 }
	
	
	
	public JSONObject getOrgLocationCacheDetailsByOrgLcoationId(String orgLocationId) {
		
		 JSONObject orgLocationJson = new JSONObject();
		 Object sv = null;
		 sv = c.getOrgLocationsDetails(orgLocationId);
		 if(sv != null) {
		  
		  JSONParser parser = new JSONParser(); 
		  try {
			  JSONObject jsonObject = (JSONObject) parser.parse(sv.toString());
			  for(Iterator iterator = jsonObject.keySet().iterator(); iterator.hasNext();) {
				    String key = (String) iterator.next();
				     Object jsonValue = jsonObject.get(key);
				     JSONObject jsonObjVal = (JSONObject) parser.parse(jsonValue.toString());
				     for (Object okey : jsonObjVal.keySet()) {
				    	 String keyStr = (String)okey;
				         Object keyvalue = jsonObjVal.get(keyStr);
				         orgLocationJson.put(keyStr, keyvalue);
				     }
				}
			  
		} catch (ParseException e) {
			e.printStackTrace();
		}
	  }
		return orgLocationJson;
	}
}
