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

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.entity.CandidateResumes;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.Requirement;
import com.bis.sourcelead.entity.RequirementApproverHierarchy;
import com.bis.sourcelead.entity.RequirementAssignTo;
import com.bis.sourcelead.entity.RequirementSubmission;
import com.bis.sourcelead.entity.RequirementSubmissionTemplateFields;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.CandidateResumesService;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.OrgService;
import com.bis.sourcelead.service.RequirementApproverHierarchyService;
import com.bis.sourcelead.service.RequirementAssignToService;
import com.bis.sourcelead.service.RequirementService;
import com.bis.sourcelead.service.RequirementSubmissionService;
import com.bis.sourcelead.service.RequirementSubmissionTemplateFieldsService;
import com.bis.sourcelead.service.UserService;
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
	 UserService userService;

	 @Autowired
	 RequirementService requirementService;
	 
	 @Autowired
	 RequirementSubmissionService requirementSubmissionService;
	 
	 @Autowired
	 CandidateResumesService candidateResumesService;
	 
	 @Autowired
	 RequirementAssignToService requirementAssignToService;
	 
	 @Autowired
	 RequirementApproverHierarchyService requirementApproverHierarchyService;
	 
	 @Autowired
	 RequirementSubmissionTemplateFieldsService requirementSubmissionTemplateFieldsService;
	 
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
		User  user = (User)SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
		List<Requirement>   requirementList = requirementService.getRequirementListByOrgLocationID(loginOrgLocation.getId(),status,clients,user.getId());
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
			String resultJson = null;
		    OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			Org loginOrgCode = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
			if(requirementId !=null && !requirementId.isEmpty()){
				Requirement requirement = requirementService.getRequirementByRequirementId(Long.valueOf(requirementId));
				
				// to get the RequirementApproverHierarchy to set the Status
				RequirementApproverHierarchy requirementApproverHierarchy = requirementApproverHierarchyService.getRequirementApproverHierarchyByRequirementId(Long.parseLong(requirementId), user.getId());
					if(requirementApproverHierarchy!=null){
						requirement.setRequirementApproverHierarchyStatus(requirementApproverHierarchy.getRequirementApproverStatus().toString());
					}
					if(requirement.getSpocId() !=null){
					requirement.setClientName(requirement.getClientId().getResponderLocationId().getOrg().getOrgName());
					requirement.setSpocName(requirement.getSpocId().getSpocUser().getFirstName()+" "+requirement.getSpocId().getSpocUser().getLastName());
					requirement.setSpocEmail(requirement.getSpocId().getSpocUser().getEmail());
					requirement.setSpocMobilePhone(requirement.getSpocId().getSpocUser().getMobilePhone());
					requirement.setClientSubmissionTemplate(requirement.getClientSubmissionTemplateId().getTemplateName());
					}
					if(requirement.getManagerUserId() != null && requirement.getManagerUserId().getFirstName() != null && requirement.getManagerUserId().getLastName() != null ){
						requirement.setManager(requirement.getManagerUserId().getFirstName()+" "+requirement.getManagerUserId().getLastName());	
					}
					resultJson = commonUtil.toJSON(requirement);
			
				
				 
			}
			
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
		 
		 @RequestMapping(value = "/getRequirementAssignedListByRequirementId" , method = RequestMethod.POST)
		 public @ResponseBody  String getRequirementAssignedListByRequirementId(@RequestParam(value="requirementId", required=false) String  requirementId,HttpServletRequest request) throws Exception {
				String resultJson = null;
				List<RequirementAssignTo> requirementAssignToList = null;
				if(requirementId !=null && !requirementId.isEmpty()){
			       requirementAssignToList = requirementAssignToService.getRequirementAssignToListByRequirementId(Long.parseLong(requirementId));

			       if(requirementAssignToList !=null && !requirementAssignToList.isEmpty()){
			           for (RequirementAssignTo assing : requirementAssignToList) { 
					       assing.setAssignedUserName(assing.getAssignUserId().getFirstName()+" "+assing.getAssignUserId().getLastName());
					       if(assing.getRoleId() != null ){
					    	   assing.setAssignedUserRole(assing.getRoleId().getDisplayName());
					       }
					       if(assing.getTeamId() != null ){
					    	   assing.setTeamName(assing.getTeamId().getName());
					       }
				      }
			       }
					resultJson = commonUtil.toJSON(requirementAssignToList);
				}else{
					resultJson = "failure";
				}
				return resultJson;
	     }
	 	   
		 /**
		  * @author Prasanna vadla
		  * @param requirementId
		  * @param request
		  * @return
		  * @throws Exception
		  */
		 
		 @RequestMapping(value = "/getSubmissionsByRequirementId" , method = RequestMethod.POST)
		 @ResponseBody  String getSubmissionsByRequirementId(@RequestParam(value="requirementId", required=false) String  requirementId,HttpServletRequest request) throws Exception {
				String resultJson = null;
				 OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
					User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
					if(requirementId !=null && !requirementId.isEmpty()){
						List<RequirementSubmission> requirementSubmissions = requirementSubmissionService.getSubmissionsByRequirementId(Long.valueOf(requirementId),user,loginOrgLocation);
						if(requirementSubmissions != null){
							for (RequirementSubmission requirementSubmission : requirementSubmissions) {
								CandidateResumes candidateResumes = candidateResumesService.getCandidateResumeByResumeId(requirementSubmission.getCandidateResumes().getId());
								RequirementSubmissionTemplateFields requirementSubmissionTemplateFields = requirementSubmissionTemplateFieldsService.getSubmissionsTemplateFieldsByRequirementSubmissionsId(requirementSubmission.getId());
								requirementSubmission.setCandidateName(requirementSubmissionTemplateFields.getFirstName()+" "+requirementSubmissionTemplateFields.getLastName());
								requirementSubmission.setCandidateEmail(requirementSubmissionTemplateFields.getEmailId());
								requirementSubmission.setExpectedCTC(requirementSubmissionTemplateFields.getExpectedCTC());
								requirementSubmission.setWorkExperience(requirementSubmissionTemplateFields.getWorkExperience());
								requirementSubmission.setCandidateId(requirementSubmission.getCandidate().getId().toString());
								requirementSubmission.setPhoneNumber(requirementSubmissionTemplateFields.getPhoneNumber());
								if(requirementSubmission.getCreatedDate() != null){
									requirementSubmission.setCreatedDateStr(Constants.DF_YMD.format(requirementSubmission.getCreatedDate()));
								}
								User userVal = userService.getUserByUserCode(requirementSubmission.getCreatedBy());
								requirementSubmission.setUserName(userVal.getFirstName()+" "+userVal.getLastName());
								requirementSubmission.setResumeName(candidateResumes.getFileName());
							}
						}
						
						resultJson = commonUtil.toJSON(requirementSubmissions);
					}
				return resultJson;
		 }
}
