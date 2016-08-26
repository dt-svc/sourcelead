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

import com.bis.sourcelead.entity.CandidateEmployers;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.service.CandidateEmployersService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;

/**
 * Handles requests for the Candidate service.
 * 
 * @author Ajith Matta
 * @date 30th June 2016
 *
 */

@RestController
public class CandidateEmployersRestController {

	private static final Logger logger = LoggerFactory.getLogger(CandidateRestController.class);

	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	
	@Autowired
	private CandidateEmployersService candidateEmployersService;
	
	@Autowired
	private CommonUtil commonUtil;

	

	/*------------Create a Candidate Employers--------------------------------*/
	

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
	
	@RequestMapping(value = "/storeCandidateEmployers", method = RequestMethod.POST)
	public @ResponseBody String storeCandidateEmployers(@RequestParam(value = "json", required = false) String json) throws Exception {

		String jsonc = "{" + json + "}";
		JSONObject jsonObject = new JSONObject(jsonc);
	 candidateEmployersService.storeCandidateEmployers(jsonObject);
		return json;
	}
	
	/**
	 *  This Method is for getting candidate employers 
	 *   @author Ajith Matta
	 * @param candidateemployersId
	 * @param request
	 * @return CandidateEmployers list
	 * @throws Exception
	 */
	@RequestMapping(value = "/getCandidatesEmployersListByCandidateIdAndOrgLocationID")
	public String getCandidatesEmployersListByCandidateIdAndOrgLocationID(@RequestParam(value = "candidateId", required = false) String candidateId,HttpServletRequest request) throws Exception {

		OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "locationCode");
		String resultJson;
		

		 List<CandidateEmployers>   candidatesEmployersList = candidateEmployersService.getCandidatesEmployersListByCandidateIdAndOrgLocationID(Long.valueOf(candidateId), loginOrgLocation.getId());
		 resultJson = commonUtil.toJSON(candidatesEmployersList);
		 return resultJson;
	}
	
	
	
}
