package com.bis.sourcelead.couchbase.rest;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.*;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.couchbase.OrgLocationCacheDTO;
import com.bis.sourcelead.couchbase.UserRoleSessionProfileDTO;
import com.bis.sourcelead.couchbase.util.UserStatsCacheHelper;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.Requirement;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserRole;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.RequirementService;
import com.bis.sourcelead.service.SourceleadActionsService;
import com.bis.sourcelead.service.TabsService;
import com.bis.sourcelead.service.UserRoleService;
import com.bis.sourcelead.util.SourceLeadSessionManager;
import com.bis.sourcelead.workflow.dao.RequirementWorkFlowDao;
import com.bis.sourcelead.workflow.dto.RequirementWorkFlowDTO;
import com.bis.sourcelead.workflow.entity.RequirementWorkFlow;
import com.bis.sourcelead.workflow.service.RequirementWorkFlowService;
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
	
	@Autowired
	SourceLeadSessionManager sourceLeadSessionManager;
	
	@Autowired
	private RequirementWorkFlowService requirementWorkFlowService;
	
	@Autowired
	RequirementService requirementService;
	
	 @RequestMapping(value = "/ws/storeOrgLocationsInCouch", method = RequestMethod.GET)
	 public void storeOrgLocationsInCouch() throws Exception {
		
		 List<OrgLocations> orgLocationsList =  orgLocationService.getAllOrgLocations();
		 
		 if(orgLocationsList != null && !orgLocationsList.isEmpty()) {
			 System.out.println("Uploading orgLocations details to Couchbase starts......");
			 for(OrgLocations orgLocations : orgLocationsList) {
				
			 if(orgLocations != null &&  orgLocations.getOrg() != null) {	 
				 OrgLocationCacheDTO orgLocationDTO = userStatsCacheHelper.getOrgLocationCacheDetails(orgLocations);
				 ObjectMapper sessionObj = new ObjectMapper();
		         try {
					String sessionJsonStr = sessionObj.writeValueAsString(orgLocationDTO);
					JsonObject jsonObject = JsonObject.fromJson(sessionJsonStr);
					StringBuilder orgLocationKey = new StringBuilder();
					orgLocationKey.append("OrgLocation_").append(orgLocationDTO.getOrgLocationCode());
					sourceLeadSessionManager.setOrgLocationsJsonString(orgLocationKey.toString(), "orgLocations", jsonObject);
				} catch (JsonProcessingException e) {
					e.printStackTrace();
				}
		         
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
				
				 if(userRole.getUser() != null &&  StringUtils.isNotBlank(userRole.getUser().getUserName())) {
					 UserRoleSessionProfileDTO userRoleDTO = userStatsCacheHelper.getUserRoleCacheDetails(userRole);
					 ObjectMapper sessionObj = new ObjectMapper();
			         try {
						String sessionJsonStr = sessionObj.writeValueAsString(userRoleDTO);
						JsonObject jsonObject = JsonObject.fromJson(sessionJsonStr);
						StringBuilder userRoleKey = new StringBuilder();
						userRoleKey.append(userRole.getUser().getUserName().toLowerCase()).append("_AUTH_").append(userRole.getUuid());
						sourceLeadSessionManager.setUserProfileJsonString(userRoleKey.toString(), "userProfile", jsonObject);
					} catch (JsonProcessingException e) {
						e.printStackTrace();
					}
				 }
			 }
			 System.out.println("Uploading userprofile details to Couchbase ends");
		 }
	 }
	 
	@RequestMapping(value = "/ws/saveWorkflowIdForAllOrgsInRequirement", method = RequestMethod.GET)
	public void saveWorkflowIdForAllOrgsInRequirement() {
		System.out.println("Started saving workflowId in requirement table for all orgs.");
		List<OrgLocations> orgLocationsList = new ArrayList<OrgLocations>();
		List<Requirement> requirementList = new ArrayList<Requirement>();
		Iterator<OrgLocations> i = null;
		Iterator<Requirement> i1 = null;
		RequirementWorkFlowDTO requirementWorkFlowDTO = new RequirementWorkFlowDTO();
		RequirementWorkFlow workFlow = null;
		User user = new User();
		try {
			orgLocationsList = orgLocationService.getAllOrgLocations();
			System.out.println("number of orgLocations - " + orgLocationsList.size());
			i = orgLocationsList.iterator();
			while (i.hasNext()) {
				OrgLocations org = i.next();
				if (org.getId() != null) {
					System.out.println("saving workflow for - " + org.getId()+" orgLocation");
					requirementList = requirementService.getAllRequirementsByOrglocationId(org.getId());
					if (requirementList != null && requirementList.size() > 0) {
						i1 = requirementList.iterator();
					}
					workFlow = requirementWorkFlowService.getRequirementWorkFlowByOrgLocId(org.getId());
						if (workFlow != null && i1 != null) {
							while (i1.hasNext()) {
								Requirement requirement = i1.next();
								if (requirement.getWorkflowId() == null) {
									requirement.setWorkflowId(workFlow);
									requirementService.updateRequirement(requirement);
								}
							}
						}
					if (workFlow == null) {
						user = org.getUser();
						requirementWorkFlowDTO.setWorkFlowName(Constants.DEFAULT_JOB_WORKFLOW);
						requirementWorkFlowDTO.setDescription(Constants.DEFAULT_JOB_WORKFLOW);
						requirementWorkFlowDTO.setConfigureType(Constants.DEFAULT);
						workFlow = requirementWorkFlowService.saveRequirementWorkFlowDetailsInfo(requirementWorkFlowDTO, user, org);
						workFlow.setConfigureType(Constants.DEFAULT);
						requirementWorkFlowService.saveWorkflowIdForAllOrgsInRequirement(workFlow, org);
						if (workFlow != null && i1 != null) {
							while (i1.hasNext()) {
								Requirement requirement = i1.next();
								if (requirement.getWorkflowId() == null) {
									requirement.setWorkflowId(workFlow);
									requirementService.updateRequirement(requirement);
								}
							}
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e + " in saveWorkflowIdForAllOrgsInRequirement");
		}
		System.out.println("Finished saving workflowId in requirement table for all orgs.");
	}
}
