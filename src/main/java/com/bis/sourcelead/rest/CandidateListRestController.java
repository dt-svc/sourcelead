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

import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.CandidateService;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.OrgService;
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
	public class CandidateListRestController {
	
	
		@Value("${sourceleadUrl}")
		private String sourceleadUrl;
		
		
		 @Autowired
		 private OrgService orgService;
		 
		 @Autowired
		 private OrgLocationService orgLocationService;
		
		 @Autowired
		 CommonUtil commonUtil;
		 

		 @Autowired
		 CandidateService candidateService;
		 
		
		 
		  /**
		     *  @author David Raju
			 * to Candidates List by OrgCode OrgLocationCode
			 * @author David Sammeta
			 * @param request
			 * @return String
			 * @throws Exception
	        */
				@RequestMapping(value = "/getCandidatesListByOrgIdAndOrgLocationID")
				public String getCandidatesListByOrgIdAndOrgLocationID(HttpServletRequest request) throws Exception {

					OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "locationCode");
					String resultJson;
					Org loginOrgCode = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");

					 List<Candidate>   candidatesList = candidateService.getCandidatesListByOrgIdAndOrgLocationID(loginOrgCode.getId(), loginOrgLocation.getId());
					 resultJson = commonUtil.toJSON(candidatesList);
					 return resultJson;
				}
		 
		 
				/**
				 *  This Method is for getting candidate by candidate ID
				 *   @author David Raju
				 * @param candidateId
				 * @param request
				 * @return Candidate
				 * @throws Exception
				 */
			 @RequestMapping(value = "/getCandidateById" , method = RequestMethod.POST)
			 public @ResponseBody  String getCandidateById(@RequestParam(value="candidateId", required=false) String  candidateId,HttpServletRequest request) throws Exception {
				   
				    OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
					User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
					Org loginOrgCode = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
					 
					Candidate can = candidateService.getCandidateByCandidateIdAndOrgLocationIdAndOrgId(Long.valueOf(candidateId), loginOrgCode.getId(), loginOrgLocation.getId());
					
					return null;
			   }
		 
		 
		 
		
	}
