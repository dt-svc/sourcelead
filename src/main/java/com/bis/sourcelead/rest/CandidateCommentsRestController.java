package com.bis.sourcelead.rest;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
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
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.CandidateCommentsService;
import com.bis.sourcelead.service.CandidateService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;

@RestController
@PropertySource("${propertyLocation:app.properties}")

/**
 * 
 * @author root
 * Handles client requests
 */
public class CandidateCommentsRestController {

	private static final Logger logger = LoggerFactory.getLogger(CandidateCommentsRestController.class);
	
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
	@RequestMapping(value = "/saveCandidateComment", method = RequestMethod.POST)
	public @ResponseBody String saveCandidateComment(@RequestParam(value = "json", required = false) String json, String candidateId, HttpServletRequest request) throws Exception {

		String json1 = "{" + json + "}";
		JSONObject jsonObj = new JSONObject(json1);
		

		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		
		if(StringUtils.isNotEmpty(candidateId)){
			Candidate candidate = candidateService.getCandidateByCandidateId(Long.parseLong(candidateId));
			
			candidateCommentsService.storeCandidateComments(jsonObj, user, candidate, orgLocation);

			SourceLeadSessionManager.setSessionAttribute(request, "resumeAddMessage",
					"Successfully you have added");
			return "success";
		}else{
			return "exception";
		}
	}
	
}