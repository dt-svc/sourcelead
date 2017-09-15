package com.bis.sourcelead.rest;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dto.CandidateEmployerDTO;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.CandidateEmployers;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.CandidateEmployersService;
import com.bis.sourcelead.service.CandidateService;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.CandidateActivityCommonUtil;
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


	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	
	@Autowired
	private CandidateEmployersService candidateEmployersService;
	
	@Autowired
	private CommonUtil commonUtil;

	
	@Autowired
	private CandidateService candidateService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private OrgLocationService orgLocationService;
	
	@Autowired
	private CandidateActivityCommonUtil candidateActivityCommonUtil;
	
	

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
	
	//@PreAuthorize("hasAnyRole('ROLE_ADD_EMPLOYEER','ROLE_CROWDSOURCING_PROFILE','ROLE_CROWDSOURCING_CANDIDATES','ROLE_ADD_ASSOCIATE_EMPLOYER')")
	@RequestMapping(value = "/storeCandidateEmployers", method = RequestMethod.POST)
	public @ResponseBody String storeCandidateEmployers(CandidateEmployerDTO candidateEmployerDTO,@RequestParam(value="candidateId", required=false) Long candidateId ,HttpServletRequest request) throws Exception {

		
		OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		User users = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		
		/*String json1 = "{"+json+"}";
		JSONObject jsonObject = new JSONObject(json1);*/
	    candidateEmployersService.storeCandidateEmployers(candidateEmployerDTO,users,loginOrgLocation,candidateId);
		Candidate candidate = candidateService.getCandidateByCandidateId(candidateId);
		Integer percentage = candidateService.getCandidatePercentage(candidate,loginOrgLocation);
		return percentage.toString();
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

		String msg = "";
		OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		
		Candidate employers =candidateService.getCandidateById(Long.parseLong(candidateId));
		Long loc = employers.getOrgLocationId().getId();
		
		String resultJson;
		 List<CandidateEmployers>   candidatesEmployersList = candidateEmployersService.getCandidatesEmployersListByCandidateIdAndOrgLocationID(Long.valueOf(candidateId), loc);
		 
		 for(CandidateEmployers candidateEmployers : candidatesEmployersList){
			 
			/* msg = (String) SourceLeadSessionManager.getSessionAttribute(request, "addExep");
			 if(msg == null){
					msg = (String) SourceLeadSessionManager.getSessionAttribute(request, "editExep");
				}
				if(msg == null){
					msg = (String) SourceLeadSessionManager.getSessionAttribute(request, "deleteExep");
				}
				if(msg !=null && msg != ""){
					candidateEmployers.setMsg(msg);
				}
				 	SourceLeadSessionManager.removeSessionAttribute(request, "addExep");
				    SourceLeadSessionManager.removeSessionAttribute(request, "editExep");
				    SourceLeadSessionManager.removeSessionAttribute(request, "deleteExep");*/
		 }
		 
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
	
	@PreAuthorize("hasAnyRole('ROLE_VIEW_DELETE_EMPLOYEER','ROLE_CROWDSOURCING_PROFILE','ROLE_CROWDSOURCING_CANDIDATES','ROLE_VIEW_DELETE_ASSOCIATE_EMPLOYER')")
	@RequestMapping(value = "/removeCandidateEmployerBycandidateIdandCandidateEmployerID")
	public String removeCandidateEmployerBycandidateIdandCandidateEmployerID(@RequestParam(value = "loginUserId", required = false) Long loginUserId, @RequestParam(value = "orgLocationId", required = false) Long orgLocationId, @RequestParam(value = "candidateId", required = false) Long candidateId,@RequestParam(value = "candidateemployerId", required = false) Long candidateemployerId) throws Exception {

		//OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		User user = userService.getUserById(loginUserId);
		OrgLocations orgLocations = orgLocationService.getOrgLocations(orgLocationId);
		StringBuffer activitySummary = new StringBuffer();
		CandidateEmployers  candidatesEmployersList = candidateEmployersService.removeCandidateEmployerBycandidateIdandCandidateEmployerID(candidateId, candidateemployerId);
		Candidate candidate =candidateService.getCandidateById(candidateId);
			activitySummary.append("Employer").append(" : ").append(Constants.NEW_VALUE_TAG_INITIATION).append(candidatesEmployersList.getName()).append(Constants.NEW_VALUE_TAG_END);
			candidateActivityCommonUtil.storeCandidateEmployersHistory(candidatesEmployersList, user);
			candidateActivityCommonUtil.storeCandidateActivity(user, orgLocations, candidatesEmployersList.getCandidateId(), "", Constants.CANDIDATE_DELETED_EMPLOYER, activitySummary.toString(), "CANDIDATE");
			
		 Integer percentage = candidateService.getCandidatePercentage(candidate,orgLocations);
		 return percentage.toString();
	}
	
	
	/**
	 * This Method is to get CandidateEmployer By CandidateId And CandidateEmpoyerId
	 * 
	 * @author Sammeta David Raju
	 * 
	 * @param candidateemployersId
	 * @param candidateId This is the first parameter of getCandidateEmployerByCandidateIdAndCandidateEmpoyerId
	 * @param candidateemployerId  This is the second parameter of getCandidateEmployerByCandidateIdAndCandidateEmpoyerId
	 * 
	 * @return String
	 * 
	 * @throws Exception
	 */
	@PreAuthorize("hasAnyRole('ROLE_VIEW_EDIT_EMPLOYEER','ROLE_CROWDSOURCING_PROFILE','ROLE_CROWDSOURCING_CANDIDATES','ROLE_VIEW_EDIT_ASSOCIATE_EMPLOYER')")
	@RequestMapping(value = "/getCandidateEmployerByCandidateIdAndCandidateEmpoyerId")
	public String getCandidateEmployerByCandidateIdAndCandidateEmpoyerId(@RequestParam(value = "candidateId", required = false) Long candidateId,@RequestParam(value = "candidateEmpId", required = false) Long candidateEmpId,HttpServletRequest request) throws Exception {

		String resultJson;

		 CandidateEmployers  candidatesEmployersList = candidateEmployersService.getCandidateEmployerBycandidateIdandCandidateEmployerID(candidateId, candidateEmpId);
		 resultJson = commonUtil.toJSON(candidatesEmployersList);
		 return resultJson;
	}
	
	
	
	/**
	 * This Method is to get CandidateEmployer By CandidateId And CandidateEmpoyerId
	 * 
	 * @author Sammeta David Raju
	 * 
	 * @param candidateemployersId
	 * @param candidateId This is the first parameter of getCandidateEmployerByCandidateIdAndCandidateEmpoyerId
	 * @param candidateemployerId  This is the second parameter of getCandidateEmployerByCandidateIdAndCandidateEmpoyerId
	 * 
	 * @return String
	 * 
	 * @throws Exception
	 */
	@RequestMapping(value = "/upDateCandidateEmployerByCandidateIdAndCandidateExperianceId")
	public String upDateCandidateEmployerByCandidateIdAndCandidateExperianceId(CandidateEmployerDTO candidateEmployerDTO,@RequestParam(value = "candidateId", required = false) Long candidateId,@RequestParam(value = "candidateExperianceId", required = false) Long candidateExperianceId,HttpServletRequest request) throws Exception {

		    OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
		    User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			CandidateEmployers candidateEmp ;
			/*String jsonc = "{" + json + "}";
			JSONObject jsonObj = new JSONObject(jsonc);*/
			
			candidateEmp = candidateEmployersService.storeCandidateEmployers(candidateEmployerDTO,user,loginOrgLocation,candidateId);
			//SourceLeadSessionManager.setSessionAttribute(request, "editExep", "Employers details updated");

			return "success";
	}
	
	
}
