package com.bis.sourcelead.rest;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.Roles;
import com.bis.sourcelead.service.CandidateService;
import com.bis.sourcelead.service.RolesService;
import com.bis.sourcelead.util.CommonUtil;

@RestController
public class ProjectPlanningRestController {

	@Autowired
	CommonUtil commonUtil;
	
	@Autowired
	CandidateService candidateService;
	
	@Autowired
	RolesService roleService;
	
	 @RequestMapping(value = "/getRequestedAssociatesList", method = RequestMethod.GET)
	 public @ResponseBody String  getRequestedAssociatesList(@RequestParam(value="orgLocationId", required=false) Long orgLocationId,
			 @RequestParam(value="roles", required=false) String roles,@RequestParam(value="resourceType", required=false) String resourceType) throws Exception{ 
		 
		String resultJson;
		
		List<Long> roleIds = null; 
		
		if(!isNullOrEmpty(roles)){
			roleIds = new ArrayList<Long>();
			 String[] rolesList = roles.split(",");
			 for(int i=0; i < rolesList.length; i++){
				 roleIds.add(Long.valueOf(rolesList[i]));
			 }
			 
		}
		
		 List<Candidate> candidatesList = candidateService.getRequestedCandidatesList(orgLocationId, roleIds, resourceType);
		 
		 resultJson = commonUtil.toJSON(candidatesList);
		 return resultJson;
	  }
	 
	 
	 
	 @RequestMapping(value = "/getOrgLocationRoles", method = RequestMethod.GET)
	 public @ResponseBody String  getOrgLocationRoles(@RequestParam(value="orgLocations", required=false) String orgLocations) throws Exception{ 
		 
		String resultJson;
		
		List<Long> orgLocationIds = null; 
		List<Long> roleIds = null; 
		
		 String[] orgLocationId = orgLocations.split(",");
		
		 List<Roles> rolesList = roleService.getRoles(Long.valueOf(orgLocationId[0]));
		 
		 resultJson = commonUtil.toJSON(rolesList);
		 return resultJson;
	  }
	 
	 

	 
	 protected static boolean isNullOrEmpty(String str) {
			return (str == null || "".equals(str.trim()));
		}
}
