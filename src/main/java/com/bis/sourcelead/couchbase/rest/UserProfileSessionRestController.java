package com.bis.sourcelead.couchbase.rest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.couchbase.OrgLocationCacheDTO;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.util.SourceLeadSessionManager;
import com.couchbase.client.java.document.json.JsonObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/app")
public class UserProfileSessionRestController {
	
	@Autowired
	OrgLocationService orgLocationService;
	
	 @RequestMapping(value = "/ws/storeUsersProfileInCouch", method = RequestMethod.GET)
	 public void storeUsersProfileInCouch() throws Exception {
		
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
				 orgLocationDTO.setWeekOffDays(orgLocations.getWeekOffDays());
				 orgLocationDTO.setWorkingHours(String.valueOf(orgLocations.getWorkingDaysPerWeek()));
				 orgLocationDTO.setOrgShortCode(orgLocations.getGeneratedUserId());
				 orgLocationDTO.setOrgLocationCode(orgLocations.getLocationCode());
				 orgLocationDTO.setOrgCode(orgLocations.getOrg().getOrgCode());
				 
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
					SourceLeadSessionManager.setUserProfileJsonString(orgLocationDTO.getOrgLocationCode(), "userProfile", jsonObject);
				} catch (JsonProcessingException e) {
					e.printStackTrace();
				}
			 }
		 }
	 }

}
