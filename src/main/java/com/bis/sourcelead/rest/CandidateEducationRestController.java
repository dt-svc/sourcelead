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

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.CandidateEducation;
import com.bis.sourcelead.entity.LookUpData;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.CandidateEducationService;
import com.bis.sourcelead.service.CandidateService;
import com.bis.sourcelead.service.LookUpDataService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;

/**
 * Handles requests for the CandidateEducation Service
 * 
 * @author Ajith Matta
 * @date 30th Aug 2016
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
	
	@Autowired
	LookUpDataService lookUpDataService;
	
	@Autowired
	private CandidateService candidateService;


	 /**
	 * @author Ajith Matta
	 * 
	 * @since  aug 26 2016
	 * 
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
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");

		candidateEducationService.storeCandidateEducation(jsonObject,Long.parseLong(candidateId),loginOrgLocation.getId(), user);
		Candidate candidate = candidateService.getCandidateByCandidateId(Long.parseLong(candidateId));
		SourceLeadSessionManager.setSessionAttribute(request, "addEducation", "New Education added for "+candidate.getFirstName()+" ");
		return "success";
	}
	
	/**
	 * This Method is for getting candidate employers 
	 *  
	 * @author Ajith Matta
	 * 
	 * @param candidateemployersId
	 * @param request
	 * 
	 * @return CandidateEmployers list
	 * 
	 * @throws Exception
	 */
	@RequestMapping(value = "/getCandidateEducationListByCandidateId")
	public String getCandidateEducationListByCandidateId(@RequestParam(value = "candidateId", required = false) String candidateId,HttpServletRequest request) throws Exception {
		
		String msg = "";
		 OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
		 String resultJson;
		 List<CandidateEducation>   candidatesEducationList = candidateEducationService.getCandidateEducationListByCandidateIdAndOrgLocationID(Long.valueOf(candidateId), loginOrgLocation.getId());
		 for(CandidateEducation candidateEducation :candidatesEducationList){
			 msg = (String) SourceLeadSessionManager.getSessionAttribute(request, "addEducation");
			 if(msg == null){
					msg = (String) SourceLeadSessionManager.getSessionAttribute(request, "editEducation");
				}
				if(msg == null){
					msg = (String) SourceLeadSessionManager.getSessionAttribute(request, "deleteEducation");
				}
				if(msg !=null && msg != ""){
					candidateEducation.setMsg(msg);
				}
				 	SourceLeadSessionManager.removeSessionAttribute(request, "addEducation");
				    SourceLeadSessionManager.removeSessionAttribute(request, "editEducation");
				    SourceLeadSessionManager.removeSessionAttribute(request, "deleteEducation");
			 
		 }
		 resultJson = commonUtil.toJSON(candidatesEducationList);
		 return resultJson;
	}	
	

	
	
	 /**
	 *  This Method is to remove CandidateEmployer By candidateId and CandidateEmployerID 
	 *  
	 * @author Sammeta David Raju
	 * 
	 * @param candidateemployersId
	 * @param candidateId
	 * @param request
	 * 
	 * @return String
	 * 
	 * @throws Exception
	 */
	@RequestMapping(value = "/removeCandidateEducationBycandidateIdandcandidateEducationId")
	public String removeCandidateEducationBycandidateIdandcandidateEducationId(@RequestParam(value = "candidateId", required = false) Long candidateId,@RequestParam(value = "candidateEducationId", required = false) Long candidateEducationId,HttpServletRequest request) throws Exception {
		String resultJson;
		CandidateEducation  candidateEducation = candidateEducationService.removeCandidateEducationBycandidateIdandcandidateEducationId(candidateId, candidateEducationId);
		 resultJson = commonUtil.toJSON(candidateEducation);
			SourceLeadSessionManager.setSessionAttribute(request, "deleteEducation", "Education deleted");

		 return resultJson;
	}


	@RequestMapping(value = "/getCandidateEducationBycandidateIdandEducationId")
	public String getCandidateEducationBycandidateIdandEducationId(@RequestParam(value = "candidateId", required = false) Long candidateId,@RequestParam(value = "candidateEducationId", required = false) Long candidateEducationId,HttpServletRequest request) throws Exception {
		String resultJson;
		 CandidateEducation  candidatesEducationList = candidateEducationService.getCandidateEducationBycandidateIdandEducationId(candidateId, candidateEducationId);
		
		 if(candidatesEducationList.getStartDate() != null){
			 candidatesEducationList.setStartDateStr(Constants.DF_YMD.format(candidatesEducationList.getStartDate())); 
			 }
			 if(candidatesEducationList.getEndDate() != null){
				 candidatesEducationList.setEndDateStr(Constants.DF_YMD.format(candidatesEducationList.getEndDate())); 
			 }
			 List<LookUpData> educationsList = lookUpDataService.getLookUpDataByCategory(Constants.EDUCATIONS); 
			 candidatesEducationList.setEducationList(educationsList);
		     resultJson = commonUtil.toJSON(candidatesEducationList);
		     return resultJson;
	}
	
	@RequestMapping(value = "/upDateCandidateEducationByCandidateIdAndCandidateEducationId")
	public String upDateCandidateEducationByCandidateIdAndCandidateEducationId(@RequestParam(value="json", required=false) String  json,@RequestParam(value = "candidateId", required = false) Long candidateId,HttpServletRequest request) throws Exception {

		    OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
		    User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			CandidateEducation candidateEdu;
			String jsonc = "{" + json + "}";
			JSONObject jsonObj = new JSONObject(jsonc);
			
			candidateEdu = candidateEducationService.storeCandidateEducation(jsonObj,candidateId,loginOrgLocation.getId(),user);
			SourceLeadSessionManager.setSessionAttribute(request, "editEducation", "Education details updated");

			return "success";
	}
	
	
	@RequestMapping(value = "/validateEduname")
	public Boolean validateEduname(@RequestParam(value="name", required=false) String  name,@RequestParam(value = "candidateId", required = false) Long candidateId,HttpServletRequest request) throws Exception {
		String candidateEducation = name;
			Boolean canEdu = candidateEducationService.validateEducationName(candidateId, candidateEducation );
			return canEdu;
	}
	
	
	
}

