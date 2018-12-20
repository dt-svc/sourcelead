package com.bis.sourcelead.couchbase;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.SourceleadActions;
import com.bis.sourcelead.entity.UserRole;
import com.bis.sourcelead.service.CandidateService;
import com.bis.sourcelead.service.LookUpDataService;
import com.bis.sourcelead.service.SourceleadActionsService;
import com.bis.sourcelead.service.TabsService;
import com.bis.sourcelead.service.UserRoleService;
import com.bis.sourcelead.service.UserService;

@Component
public class UserProfileStore {

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
	
	
 public UserSessionProfileDTO storeUsersProfileInCouch(Long userId,Long orgLocationId,UserSessionProfileDTO userProfileDTO) {
		 
		 
		 try {
			 UserRole userRole = userRoleService.getUserDetailsByUserIdAndLocationId(userId, orgLocationId);
			 
			 
			 if(userRole != null) {
				 
				 List<SourceleadActions> actionsList = null;
			        List<String> tabNamesList = null;
			        List<String> actionRolesList = new ArrayList<String>();
			        List<Long> actionIdsList = new ArrayList<Long>();
					List<UserProfileLocationsDTO> orgLocationsList = new ArrayList<>();
			        
	        	    actionsList = sourceleadActionsService.getLoginUserRoleActionsByOrgLocationId(null, userRole.getRoles().getId(), orgLocationId);
	        	    
	        	    for(SourceleadActions actions : actionsList){
	        	    	actionIdsList.add(actions.getId());
	        	    	actionRolesList.add(actions.getActionRole());
	        	    }
	        	    
	        	    tabNamesList = tabsService.getTabsNamesListByActionsIds(actionIdsList);
	        	    
	        	    UserProfileLocationsDTO orgLocationDTO =  getOrgLocationDetails(userRole.getOrgLocations(),userRole);
	        	    orgLocationDTO.setMenuList(tabNamesList);
	        	    orgLocationDTO.setRolesList(actionRolesList);
	        	    Candidate candidate = candidateService.getCandidateByConsultantUserId(userId, orgLocationId);
		        	   if(candidate != null) {
		        		   orgLocationDTO.setCandidateId(candidate.getId());
		        	   }
	        	    orgLocationDTO.setUserType(userRole.getUserType());
	        	    orgLocationsList.add(orgLocationDTO);
	        	    
	        	    userProfileDTO.setUserProfileLocations(orgLocationDTO);
				 
			 }
			 
		 }catch (Exception e) {
			e.printStackTrace();
		}
		 
		return userProfileDTO;
         
	 }
	 
	 
	 private UserProfileLocationsDTO getOrgLocationDetails(OrgLocations orgLocations,UserRole useRole){
		 UserProfileLocationsDTO orgLocationDTO = new UserProfileLocationsDTO();
		 orgLocationDTO.setOrgId(orgLocations.getOrg().getId());
		 orgLocationDTO.setOrgName(orgLocations.getOrgLocationName());
		 orgLocationDTO.setOrgCode(orgLocations.getOrg().getOrgCode());
		 orgLocationDTO.setOrgLocationId(orgLocations.getId());
		 orgLocationDTO.setOrgLocationName(orgLocations.getOrgName());
		 orgLocationDTO.setLocationCode(orgLocations.getLocationCode());
		 orgLocationDTO.setLocation(orgLocations.getOrgLocationAddress());
		 orgLocationDTO.setRoleId(useRole.getId());
		 orgLocationDTO.setRoleName(useRole.getRoles().getDisplayName());
		 orgLocationDTO.setDesignation(useRole.getDesignation());
		 orgLocationDTO.setUserState(useRole.getUserState().toString());
		 orgLocationDTO.setUserType(useRole.getUserType());
		 return orgLocationDTO;
	 }
	
	
}
