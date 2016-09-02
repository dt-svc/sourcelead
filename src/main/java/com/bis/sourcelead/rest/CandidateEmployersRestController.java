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
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.CandidateEmployersService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;

/**
 * Handles requests for the CandidateCandidateEmployers Service.
 * 
 * @author Ajith Matta
 * @date 30th June 2016
 *
 */

@RestController
public class CandidateEmployersRestController {

	private static final Logger logger = LoggerFactory.getLogger(CandidateEmployersRestController.class);

	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	
	@Autowired
	private CandidateEmployersService candidateEmployersService;
	
	@Autowired
	private CommonUtil commonUtil;

	

	/*------------Create a Candidate Employers details--------------------------------*/
	

	 /**
	 * @author Ajith Matta
	 * 
	 * @since  aug 26 2016
	 * 
	 * @version 0.0.1
	 * 
	 * This method is used to save the candidate Employers.
	 * 
	 * @param   json This is the first parameter of storeCandidateEmployers
	 * @param   candidateId   This is the second parameter of storeCandidateEmployers
	 * @param   request    This is the third parameter of storeCandidateEmployers
	 * @return string 
	 * 
	 * @throws NullPointer
	 */
	
	@RequestMapping(value = "/storeCandidateEmployers", method = RequestMethod.POST)
	public @ResponseBody String storeCandidateEmployers(@RequestParam(value = "json", required = false) String json,@RequestParam(value="candidateId", required=false) Long candidateId ,HttpServletRequest request) throws Exception {

		
		OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		User users = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		
		String json1 = "{"+json+"}";
		JSONObject jsonObject = new JSONObject(json1);
	    candidateEmployersService.storeCandidateEmployers(jsonObject,users,loginOrgLocation,candidateId);
		return json;
	}
	
	/**
	 * This Method is for getting candidate employers 
	 * 
	 * @author Ajith Matta
	 * 
	 * @param candidateemployersId This is the first parameter of getCandidatesEmployersListByCandidateIdAndOrgLocationID
	 * @param request              This is the second parameter of getCandidatesEmployersListByCandidateIdAndOrgLocationID
	 * 
	 * @return CandidateEmployers list
	 * 
	 * @throws Exception
	 */
	@RequestMapping(value = "/getCandidatesEmployersListByCandidateIdAndOrgLocationID")
	public String getCandidatesEmployersListByCandidateIdAndOrgLocationID(@RequestParam(value = "candidateId", required = false) String candidateId,HttpServletRequest request) throws Exception {

		OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		String resultJson;
		

		 List<CandidateEmployers>   candidatesEmployersList = candidateEmployersService.getCandidatesEmployersListByCandidateIdAndOrgLocationID(Long.valueOf(candidateId), loginOrgLocation.getId());
		 resultJson = commonUtil.toJSON(candidatesEmployersList);
		 return resultJson;
	}
	
	
	/**
	 * This Method is to remove CandidateEmployer By candidateId and CandidateEmployerID 
	 * 
	 * @author Sammeta David Raju
	 * 
	 * @param candidateemployersId
	 * @param candidateId This is the first parameter of removeCandidateEmployerBycandidateIdandCandidateEmployerID
	 * @param candidateemployerId  This is the second parameter of removeCandidateEmployerBycandidateIdandCandidateEmployerID
	 * 
	 * @return String
	 * 
	 * @throws Exception
	 */
	@RequestMapping(value = "/removeCandidateEmployerBycandidateIdandCandidateEmployerID")
	public String removeCandidateEmployerBycandidateIdandCandidateEmployerID(@RequestParam(value = "candidateId", required = false) Long candidateId,@RequestParam(value = "candidateemployerId", required = false) Long candidateemployerId,HttpServletRequest request) throws Exception {

		String resultJson;

		CandidateEmployers  candidatesEmployersList = candidateEmployersService.removeCandidateEmployerBycandidateIdandCandidateEmployerID(candidateId, candidateemployerId);
		 resultJson = commonUtil.toJSON(candidatesEmployersList);
		 return resultJson;
	}
	
	
}
