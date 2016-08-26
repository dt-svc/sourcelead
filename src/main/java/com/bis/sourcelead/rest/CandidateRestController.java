package com.bis.sourcelead.rest;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.CandidateService;
import com.bis.sourcelead.util.SourceLeadSessionManager;
/**
 * Handles requests for the Candidate service.
 * 
 * @author Prasad K
 * @date 30th June 2016
 *
 */

@RestController
public class CandidateRestController {

	private static final Logger logger = LoggerFactory.getLogger(CandidateRestController.class);

	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	@Autowired
	private CandidateService candidateService;

	

	/*------------Create a Candidate--------------------------------*/
	
	
	/**
	 * Store Candidate
	 * 
	 * @param json
	 * @param request
	 * @return ResponseBody
	 * @throws Exception
	 */
	@RequestMapping(value = "/storeCandidate", method = RequestMethod.POST)
	public @ResponseBody String storeCandidate(@RequestParam(value = "json", required = false) String json,HttpServletRequest request) throws Exception {

		String jsonc = "{" + json + "}";
		JSONObject jsonObj = new JSONObject(jsonc);
		
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
     
		Candidate candidate = candidateService.storeCandidate(jsonObj,orgLocation,user);
		candidate.setOrgLocationId(orgLocation);
		candidateService.addCandidate(candidate);
		return "candidates/candidatesList";
	}
	
}
