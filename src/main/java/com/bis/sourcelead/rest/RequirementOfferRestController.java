package com.bis.sourcelead.rest;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.RequirementSubmission;
import com.bis.sourcelead.service.RequirementSubmissionService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;

/**
 * sarath kumar punnana 
 * @author bis113
 *
 */

@RestController
public class RequirementOfferRestController {

	private static final Logger logger = LoggerFactory.getLogger(RequirementOfferRestController.class);
	
	@Autowired
	private ApplicationContext applicationContext;
	
	@Autowired
	RequirementSubmissionService requirementSubmissionService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	CommonUtil commonUtil;
	
	
	@RequestMapping(value = "/saveOffer" , method = RequestMethod.POST)
	   public String saveOffer(@RequestParam(value="jsonData", required=false) String json, HttpServletRequest request) throws Exception {
			
			return "success";
	    
	}
	
	@RequestMapping(value = "/getAllSubmittedAndShortListAndInterviewededCandidates")
	public @ResponseBody String getAllSubmittedAndShortListAndInterviewededCandidates(@RequestParam(value="requirementId", required=false) Long requirementId,
			                                              @RequestParam(value="existingApplicantsIds", required=false) List<Long> existingApplicantsIds,
			                                              HttpServletRequest request) throws Exception {
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		String resultJson = null;
		List<RequirementSubmission> shortListCandidatesList = requirementSubmissionService.getAllSubmittedAndShortListAndInterviewededCandidates(orgLocations.getId(), requirementId,existingApplicantsIds);
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
	
}
