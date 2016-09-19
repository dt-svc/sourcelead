package com.bis.sourcelead.rest;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.Requirement;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.OrgService;
import com.bis.sourcelead.service.RequirementService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;


/**
 * Handles requests for the Candidate List Service.
 * 
 * @author Sammeta David 
 * @date 26th August 2016
 *
 */
	
@RestController
public class RequirementListRestController {
	
	
	 @Value("${sourceleadUrl}")
	 private String sourceleadUrl;
		
	 @Autowired
	 private OrgService orgService;
	 
	 @Autowired
	 private OrgLocationService orgLocationService;
	
	 @Autowired
	 CommonUtil commonUtil;
	 

	 @Autowired
	 RequirementService requirementService;
	 
	 /**
	   * @author sarath kumar
	   * @since  aug 30 2016
	   * @version 0.0.1
	   * 
	   * This method is used to for getting CandidatesList By OrgLocationID
	   * this method is called at onload when click on timesheet left menu
	   * 
	   * @param user This is the first parameter to getCandidatesListByOrgLocationID method
	   * @param navigation This is the second parameter to getCandidatesListByOrgLocationID method
	   * @param startDate This is the third parameter to getCandidatesListByOrgLocationID method
	   *  
	   * @return string 
	   * 
	   * @throws NullPointer
	   */
	 
	 @RequestMapping(value = "/getRequirementListByOrgLocationID")
	 public String getRequirementListByOrgLocationID(@RequestParam(value="status", required=false) String status,@RequestParam(value="clients", required=false) String clients,HttpServletRequest request) throws Exception {

		String resultJson;
		Org   org = (Org)SourceLeadSessionManager.getSessionAttribute(request, "org");
		OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
		List<Requirement>   requirementList = requirementService.getRequirementListByOrgLocationID(loginOrgLocation.getId(),status,clients);
		resultJson = commonUtil.toJSON(requirementList);
		return resultJson;
	 }
	 
	 
	 /**
	 * @author sarath kumar
	 * @since  sep 13 2016
	 * @version 0.0.1
	 * 
	 * This method is used to get Requirement by requirementId .
	 *  
	 * @param requirementId This is the first paramter to getRequirementByRequirementId method
	 * 
	 * @return Requirement 
	 * 
	 * @throws NullPointer
	 */
	 
	 @RequestMapping(value = "/getRequirementById" , method = RequestMethod.POST)
	 public @ResponseBody  String getCandidateById(@RequestParam(value="requirementId", required=false) String  requirementId,HttpServletRequest request) throws Exception {
			String resultJson;
		    OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			Org loginOrgCode = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
			 
			Requirement requirement = requirementService.getRequirementByRequirementId(Long.valueOf(requirementId));
			requirement.setSpocName(requirement.getSpocId().getSpocUser().getFirstName()+" "+requirement.getSpocId().getSpocUser().getLastName());
			requirement.setSpocEmail(requirement.getSpocId().getSpocUser().getEmail());
			requirement.setSpocMobilePhone(requirement.getSpocId().getSpocUser().getMobilePhone());
			
			resultJson = commonUtil.toJSON(requirement);
			return resultJson;
			
     }
	 
	 	   
		
}
