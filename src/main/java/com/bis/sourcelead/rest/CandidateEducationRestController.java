package com.bis.sourcelead.rest;

import java.util.List;

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

import com.bis.sourcelead.entity.CandidateEducation;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.service.CandidateEducationService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;

/**
 * Handles requests for the CandidateEducation Service
 * 
 * @author Ajith Matta
 * @date 30th June 2016
 *
 */

@RestController
public class CandidateEducationRestController {

	private static final Logger logger = LoggerFactory.getLogger(CandidateEducationRestController.class);

	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	
	@Autowired
	private CandidateEducationService candidateEducationService;
	
	
	
	@Autowired
	private CommonUtil commonUtil;

	

	/*------------Create a Candidate Education details--------------------------------*/
	

	 /**
	 * @author Ajith Matta
	 * @since  aug 26 2016
	 * @version 0.0.1
	 * 
	 * This method is used to save the candidate Employers.
	 * Candidate employers details saved by json
	 * 
	 * @param This is the first parameter of candidate employers
	 *  
	 * @return string 
	 * 
	 * @throws NullPointer
	 */
	
	@RequestMapping(value = "/storeCandidateEducation", method = RequestMethod.POST)
	public @ResponseBody String storeCandidateEducation(@RequestParam(value = "json", required = false) String json,@RequestParam(value = "candidateId", required = false) String candidateId,HttpServletRequest request) throws Exception {

		String jsonc = "{" + json + "}";
		JSONObject jsonObject = new JSONObject(jsonc);
		OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		
		candidateEducationService.storeCandidateEducation(jsonObject,Long.parseLong(candidateId),loginOrgLocation.getId());
		return "success";
	}
	
	/**
	 *  This Method is for getting candidate employers 
	 *   @author Ajith Matta
	 * @param candidateemployersId
	 * @param request
	 * @return CandidateEmployers list
	 * @throws Exception
	 */
	@RequestMapping(value = "/getCandidateEducationListByCandidateId")
	public String getCandidateEducationListByCandidateId(@RequestParam(value = "candidateId", required = false) String candidateId,HttpServletRequest request) throws Exception {

		 OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
		 String resultJson;
		 List<CandidateEducation>   candidatesEducationList = candidateEducationService.getCandidateEducationListByCandidateIdAndOrgLocationID(Long.valueOf(candidateId), loginOrgLocation.getId());
		 resultJson = commonUtil.toJSON(candidatesEducationList);
		 return resultJson;
	}	
	
	
}
