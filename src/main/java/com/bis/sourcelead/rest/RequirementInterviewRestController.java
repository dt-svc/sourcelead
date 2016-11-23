package com.bis.sourcelead.rest;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.RequirementInterview;
import com.bis.sourcelead.entity.RequirementSubmission;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.RequirementInterviewService;
import com.bis.sourcelead.service.RequirementSubmissionService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.CalendarUtil;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;

/**
 * Prasanna Vadla
 * @author bis113
 *
 */

@RestController
public class RequirementInterviewRestController {

	private static final Logger logger = LoggerFactory.getLogger(RequirementInterviewRestController.class);
	
	@Autowired
	private ApplicationContext applicationContext;
	
	
	@Autowired
	UserService userService;
	
	@Autowired
	CommonUtil commonUtil;
	
	@Autowired
	RequirementSubmissionService requirementSubmissionService;
	
	@Autowired
	RequirementInterviewService requirementInterviewService;
	
	@RequestMapping(value = "/getAllInterviewers")
	public @ResponseBody String getAllApprovers(HttpServletRequest request) throws Exception {
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		String resultJson = null;
		List<User> interviewersList = userService.getAllUsersByOrgLocationCode(orgLocations.getId());
		resultJson = commonUtil.toJSON(interviewersList);
		return resultJson;

	}
	
	@RequestMapping(value = "/getAllShortListCandidates")
	public @ResponseBody String getAllShortListCandidates(@RequestParam(value="requirementId", required=false) Long requirementId,
			                                              @RequestParam(value="existingApplicantsIds", required=false) List<Long> existingApplicantsIds,
			                                              HttpServletRequest request) throws Exception {
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		String resultJson = null;
		List<RequirementSubmission> shortListCandidatesList = requirementSubmissionService.getAllShortListCandidatesByOrgLocationsIdAndRequirementId(orgLocations.getId(), requirementId,existingApplicantsIds);
		for (RequirementSubmission sub : shortListCandidatesList) {
			String candidateName = "";
 		   if(StringUtils.isNotEmpty(sub.getCandidate().getFirstName())){
 			   candidateName = sub.getCandidate().getFirstName();
 		   }
 		   if(StringUtils.isNotEmpty(sub.getCandidate().getLastName()) && StringUtils.isNotBlank(sub.getCandidate().getLastName())){
 			   candidateName = candidateName+" "+sub.getCandidate().getLastName();
 		   }
 		  sub.setCandidateName(candidateName);
		}
		resultJson = commonUtil.toJSON(shortListCandidatesList);
		return resultJson;
	}
	
	/**To add interview
	 * @author Prasad Salina
	 * @param json
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@PreAuthorize("hasRole('ROLE_ADD_INTERVIEW')")
	@RequestMapping(value = "/saveInterview" , method = RequestMethod.POST)
	   public String saveInterview(@RequestParam(value="jsonData", required=false) String json, HttpServletRequest request) throws Exception {
			
 	    String json1 = "{" + json + "}";
			JSONObject jsonObj = new JSONObject(json1);
			
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			Org loginOrg = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
			
			requirementInterviewService.saveRequirementInterview(jsonObj,user,orgLocations,loginOrg);
 	    
 	    
			return "success";

  }
	
	@PreAuthorize("hasAnyRole('ROLE_VIEW_INTERVIEW')")
	@RequestMapping(value = "/getAllInternalInterviews" , method = RequestMethod.POST)
	 public @ResponseBody  String getAllInternalInterviews(@RequestParam(value="submissionId", required=false) Long submissionId, HttpServletRequest request) throws Exception {
		
	     OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		 String resultJson = null;
			
		 List<RequirementInterview> interviewsList = requirementInterviewService.getRequirementInterviewsBySubmissionIdAndOrgLocationsId(submissionId, orgLocations.getId());
		 resultJson = commonUtil.toJSON(interviewsList);
		 return resultJson;
	   }
	  
	
	@RequestMapping(value = "/getAllCustomerInterviews" , method = RequestMethod.POST)
	 public @ResponseBody  String getAllCustomerInterviews(@RequestParam(value="submissionId", required=false) Long submissionId,
			 @RequestParam(value = "requirementId", required = false) Long requirementId,
			 HttpServletRequest request) throws Exception {
		
	     OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		 String resultJson = null;
			
		 List<RequirementInterview> interviewsList = requirementInterviewService.getCustomerInterviewsBySubmissionIdAndOrgLocationsId(submissionId, orgLocations.getId(),requirementId);
		 resultJson = commonUtil.toJSON(interviewsList);
		 return resultJson;
	   }
	 
	 @RequestMapping(value = "/acceptOrRejectInterview")
	 @ResponseBody  String acceptOrRejectInterview(@RequestParam(value="interviewStatus", required=false) String interviewStatus,
				@RequestParam(value = "requirementInterviewId", required = false) String requirementInterviewId,
			 HttpServletRequest request) throws Exception {
		 
		 RequirementInterview requirementInterview = requirementInterviewService.getRequirementInterviewById(requirementInterviewId);
		 
		 String message = " ";
/*		 if(requirementInterview.getInterviewStatus() == null && !("").equals(requirementInterview.getInterviewStatus())){*/
			 requirementInterview.setInterviewStatus(interviewStatus);
			 requirementInterviewService.saveRequirementInterview(requirementInterview);
			 if(interviewStatus.equalsIgnoreCase("Attending")){
				 message = "You have Accepted the Interview.";
			 }
			 if(interviewStatus.equalsIgnoreCase("Not Attending")){
				 message = "You have Rejected the Interview.";
			 }
			 if(interviewStatus.equalsIgnoreCase("Maybe")){
				 message = "Not sure for the Interview.";
			 }
		return message;
	 }	
	 
	 @RequestMapping(value = "/sendMailToapplicant")
	 @ResponseBody String sendMailToapplicant(@RequestParam(value="submissionId", required=false) String submissionId,
				@RequestParam(value = "requirementInterviewId", required = false) String requirementInterviewId,
			 HttpServletRequest request) throws Exception {
		 
		 
		 RequirementSubmission requirementSubmission = requirementSubmissionService.getRequirementSubmissionById(Long.parseLong(submissionId));
		 
		 RequirementInterview requirementInterview = requirementInterviewService.getRequirementInterviewById(requirementInterviewId);
		 
		 User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		 Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
		 OrgLocations orgLoc = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		 requirementInterviewService.sendEmailToApplicants(requirementSubmission.getCandidate(),orgLoc, requirementSubmission, loginUser,org, requirementInterview);
		 
		 requirementInterview.setNotificationToApplicant("Y");
		 requirementInterviewService.saveRequirementInterview(requirementInterview);
		 
		return "success";
	 }		
	 @RequestMapping(value = "/sendMailToVendor")
	 @ResponseBody String sendMailToVendor(@RequestParam(value="submissionId", required=false) String submissionId,
				@RequestParam(value = "requirementInterviewId", required = false) String requirementInterviewId,
			 HttpServletRequest request) throws Exception {
		 
		 
		 RequirementSubmission requirementSubmission = requirementSubmissionService.getRequirementSubmissionById(Long.parseLong(submissionId));
		 
		 RequirementInterview requirementInterview = requirementInterviewService.getRequirementInterviewById(requirementInterviewId);
		 OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		 User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		 Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");

		 requirementInterviewService.sendEmailToVendors(requirementSubmission.getCandidate(), requirementSubmission, loginUser,org, requirementInterview,orgLocations);
	 
		 requirementInterview.setNotificationToSpoc("Y");
		 requirementInterviewService.saveRequirementInterview(requirementInterview);
		 
		return "success";
	 }		
}
