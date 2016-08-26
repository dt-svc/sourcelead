package com.bis.sourcelead.rest;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.CandidateResumesService;
import com.bis.sourcelead.service.CandidateService;
import com.bis.sourcelead.service.ProjectApproverHierarchyService;
import com.bis.sourcelead.util.SourceLeadSessionManager;

@RestController
@PropertySource("${propertyLocation:app.properties}")
/**
 * 
 * @author root
 * Handles client requests
 */
public class CandidateResumeRestController {

	private static final Logger logger = LoggerFactory.getLogger(CandidateResumeRestController.class);
	
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;

	@Autowired
	HttpServletRequest request;
	ProjectApproverHierarchyService projectApproverHierarchyService;

	@Autowired
	CandidateService candidateService;
	
	@Autowired
	CandidateResumesService candidateResumesService;
	
	/**
	* @author Venkat Kesav 
	* Handles Resume saving for Candidate
	* @param json
	* @param request
	* @return ResponseBody
	* @throws Exception
	*/
	@RequestMapping(value = "/saveCandidateResume", method = RequestMethod.POST)
	public @ResponseBody String saveCandidateResume(@RequestParam(value = "json", required = false) String json,HttpServletRequest request, String candidateId) throws Exception {

		String json1 = "{" + json + "}";
		JSONObject jsonObj = new JSONObject(json1);

		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		
		Candidate candidate = null;
		
		candidateResumesService.storeCandidateResume(jsonObj, user, candidate, orgLocation);

		SourceLeadSessionManager.setSessionAttribute(request, "resumeAddMessage",
				"Successfully you have added");

		return "success";
	}


	/**
	 * check given string empty or null
	 * @param str
	 * @return boolean
	 */
	protected static boolean isNullOrEmpty(String str) {
		return str == null || "".equals(str.trim());
	}

}