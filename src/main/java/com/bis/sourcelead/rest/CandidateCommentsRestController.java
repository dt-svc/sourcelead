package com.bis.sourcelead.rest;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.CandidateComments;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.RequirementSubmission;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.CandidateCommentsService;
import com.bis.sourcelead.service.CandidateService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;

@RestController
@PropertySource("${propertyLocation:app.properties}")
@RequestMapping("/path")
/**
 * 
 * @author root
 * Handles client requests
 */
public class CandidateCommentsRestController {

	
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;

	@Autowired
	CandidateService candidateService;
	
	@Autowired
	CandidateCommentsService candidateCommentsService;
	
	@Autowired
	CommonUtil commonUtil;
	
	@Autowired
	UserService userService;
	
	/**
	* @author Venkat Kesav 
	* 
	* Handles Comment saving for Candidate
	* 
	* @param json, candidateId
	* @param request
	* 
	* @return ResponseBody
	* 
	* @throws Exception
	*/
	
	@PreAuthorize("hasAnyRole('ROLE_POST_COMMENTS','ROLE_CROWDSOURCING_CANDIDATES')")
	@RequestMapping(value = "/saveCandidateComment", method = RequestMethod.POST)
	public @ResponseBody String saveCandidateComment(@RequestParam(value = "json", required = false) String json, String candidateId, String submissionId, HttpServletRequest request) throws Exception {

		String json1 = "{" + json + "}";
		JSONObject jsonObj = new JSONObject(json1);
		

		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		
		if(StringUtils.isNotEmpty(candidateId)){
			Candidate candidate = null;
			if(StringUtils.isNumeric(candidateId)){
				candidate = new Candidate();
				candidate.setId(Long.parseLong(candidateId));
			}
			
			RequirementSubmission submission = null;
			if(StringUtils.isNotEmpty(submissionId) && StringUtils.isNumeric(submissionId)){
				submission = new RequirementSubmission();
				submission.setId(Long.parseLong(submissionId));
			}
			
			candidateCommentsService.storeCandidateComments(jsonObj, user, candidate, orgLocation, submission);

			SourceLeadSessionManager.setSessionAttribute(request, "resumeAddMessage",
					"Successfully you have added");
			return "success";
		}else{
			return "exception";
		}
	}
	
	
	
	
	/**
	* @author Venkat Kesav 
	* 
	* Handles Comment list for Candidate
	* 
	* @param candidateId
	* 
	* @param request
	* 
	* @return ResponseBody
	* 
	* @throws Exception
	*/
	@RequestMapping(value = "/getAllCommentsOnCandidateByCandidateIdAndOrgLocation", method = RequestMethod.POST)
	public @ResponseBody String getAllCommentsOnCandidateByCandidateIdAndOrgLocation(String candidateId, HttpServletRequest request) throws Exception {


		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		
		if(StringUtils.isNotEmpty(candidateId)){
			
			List<CandidateComments> candidateCommentsList = candidateCommentsService.getAllCommentsOnCandidateByCandidateIdAndOrgLocation(Long.parseLong(candidateId), orgLocation);

			String resultJson = commonUtil.toJSON(candidateCommentsList);
			return resultJson;
		}else{
			return "exception";
		}
	}
	
	
	/**
	* @author Venkat Kesav 
	* 
	* Handles Comment list for Candidate
	* 
	* @param candidateId
	* 
	* @param request
	* 
	* @return ResponseBody
	* 
	* @throws Exception
	*/
	@RequestMapping(value = "/getAllCommentsOnCandidateByCandidateIdAndOrgLocationAndSubmissionId", method = RequestMethod.POST)
	public @ResponseBody String getAllCommentsOnCandidateByCandidateIdAndOrgLocationAndSubmissionId(String candidateId, String submissionId, HttpServletRequest request) throws Exception {


		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		
		if(StringUtils.isNumeric(candidateId) && StringUtils.isNotEmpty(candidateId) && StringUtils.isNumeric(submissionId) && StringUtils.isNotEmpty(submissionId)){
			
			List<CandidateComments> candidateCommentsList = candidateCommentsService.getAllCommentsOnCandidateByCandidateIdAndOrgLocationAndSubmissionId(Long.parseLong(candidateId), Long.parseLong(submissionId), orgLocation);

			String resultJson = commonUtil.toJSON(candidateCommentsList);
			return resultJson;
		}else{
			return "exception";
		}
	}
	
}