package com.bis.sourcelead.rest;

import java.util.LinkedHashSet;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bis.sourcelead.annotations.Validator;
import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dto.CandidateEducationDTO;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.CandidateEducation;
import com.bis.sourcelead.entity.LookUpData;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.CandidateEducationService;
import com.bis.sourcelead.service.CandidateService;
import com.bis.sourcelead.service.LookUpDataService;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.UserService;
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
	
	@Autowired
	UserService userService;
	
	@Autowired
	OrgLocationService orgLocationService;


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
	//@PreAuthorize("hasAnyRole('ROLE_ADD_CANDIDATE_EDUCATION','ROLE_CROWDSOURCING_PROFILE','ROLE_CROWDSOURCING_CANDIDATES','ROLE_ADD_ASSOCIATE_EDUCATION_DETAILS')")
	@RequestMapping(value = "/storeCandidateEducation", method = RequestMethod.POST)
	public @ResponseBody String storeCandidateEducation(CandidateEducationDTO candidateEducationDTO,@RequestParam(value = "candidateId", required = false) String candidateId,HttpServletRequest request) throws Exception {

		if(Validator.validate(candidateEducationDTO)) {
		OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");

		candidateEducationService.storeCandidateEducation(candidateEducationDTO,Long.parseLong(candidateId),loginOrgLocation.getId(), user);
		Candidate candidate = candidateService.getCandidateByCandidateId(Long.parseLong(candidateId));
		Integer percentage = candidateService.getCandidatePercentage(candidate,loginOrgLocation);
		return percentage.toString();
		}else{
			return "DataMissing";
		}
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
			/* msg = (String) SourceLeadSessionManager.getSessionAttribute(request, "addEducation");
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
				    SourceLeadSessionManager.removeSessionAttribute(request, "deleteEducation");*/
			 
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
	
//	@PreAuthorize("hasAnyRole('ROLE_DELETE_CANDIDATE_EDUCATION','ROLE_CROWDSOURCING_PROFILE','ROLE_CROWDSOURCING_CANDIDATES','ROLE_VIEW_DELETE_ASSOCIATE_EDUCATION_DETAILS')")
	@RequestMapping(value = "/removeCandidateEducationBycandidateIdandcandidateEducationId")
	public String removeCandidateEducationBycandidateIdandcandidateEducationId(@RequestParam(value = "loginUserId", required = false) Long loginUserId, @RequestParam(value = "orgLocationId", required = false) Long orgLocationId,
																				@RequestParam(value = "candidateId", required = false) Long candidateId,@RequestParam(value = "candidateEducationId", required = false) Long candidateEducationId) throws Exception {
		User user = userService.getUserById(loginUserId);
		OrgLocations orgLocations = orgLocationService.getOrgLocations(orgLocationId);
		CandidateEducation  candidateEducation = candidateEducationService.removeCandidateEducationBycandidateIdandcandidateEducationId(candidateId, candidateEducationId, user, orgLocations);
		 //OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		 Candidate candidate = candidateService.getCandidateByCandidateId(candidateId);
			Integer percentage = candidateService.getCandidatePercentage(candidate,orgLocations);
			return percentage.toString();
	}

	
	@PreAuthorize("hasAnyRole('ROLE_VIEW_EDIT_CANDIDATE_EDUCATION','ROLE_CROWDSOURCING_PROFILE','ROLE_CROWDSOURCING_CANDIDATES','ROLE_VIEW_EDIT_ASSOCIATE_EDUCATION_DETAILS')")
	@RequestMapping(value = "/getCandidateEducationBycandidateIdandEducationId")
	public String getCandidateEducationBycandidateIdandEducationId(@RequestParam(value = "candidateId", required = false) Long candidateId,@RequestParam(value = "candidateEducationId", required = false) Long candidateEducationId,HttpServletRequest request) throws Exception {
		String resultJson;
		 CandidateEducation  candidatesEducationList = candidateEducationService.getCandidateEducationBycandidateIdandEducationId(candidateId, candidateEducationId);
		 Candidate can = candidateService.getCandidateById(candidateId);
		 //SimpleDateFormat formatter = can.getOrgLocationId().getLocFullFormDateFormatter();
		 if(candidatesEducationList.getStartDate() != null){
			 candidatesEducationList.setStartDateStr(commonUtil.dateStrFromOrgDateFormatter(can.getOrgLocationId().getDateFormat(),candidatesEducationList.getStartDate())); 
			 }
			 if(candidatesEducationList.getEndDate() != null){
				 candidatesEducationList.setEndDateStr(commonUtil.dateStrFromOrgDateFormatter(can.getOrgLocationId().getDateFormat(),candidatesEducationList.getEndDate())); 
			 }
			/* List<LookUpData> educationsList = lookUpDataService.getLookUpDataByCategory(Constants.EDUCATIONS); 
			 candidatesEducationList.setEducationList(educationsList);*/
			 
				
				 LinkedHashSet<String> lhs = new LinkedHashSet<String>();
				 List<LookUpData> educationsList = lookUpDataService.getLookUpDataByCategory(Constants.EDUCATIONS); 
					for(LookUpData lookUpData :educationsList){
						lhs.add(lookUpData.getName());
					}
					if(StringUtils.isNotEmpty(candidatesEducationList.getName())){
						String [] cel = candidatesEducationList.getName().split(",");
						for(String el : cel){
							lhs.add(el);
						}
					}
					candidatesEducationList.setAllAndCandidateEducations(lhs);
				
				     resultJson = commonUtil.toJSON(candidatesEducationList);
				     return resultJson;
	}
	
	@RequestMapping(value = "/upDateCandidateEducationByCandidateIdAndCandidateEducationId")
	public String upDateCandidateEducationByCandidateIdAndCandidateEducationId(CandidateEducationDTO candidateEducationDTO,@RequestParam(value = "candidateId", required = false) Long candidateId,HttpServletRequest request) throws Exception {

		    OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
		    User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			CandidateEducation candidateEdu;
			/*String jsonc = "{" + json + "}";
			JSONObject jsonObj = new JSONObject(jsonc);*/
			candidateEdu = candidateEducationService.storeCandidateEducation(candidateEducationDTO,candidateId,loginOrgLocation.getId(), user);

			//candidateEdu = candidateEducationService.storeCandidateEducation(jsonObj,candidateId,loginOrgLocation.getId(),user);
			return "success";
	}
	
	
	@RequestMapping(value = "/validateEduname")
	public Boolean validateEduname(@RequestParam(value="name", required=false) String  name,@RequestParam(value = "candidateId", required = false) Long candidateId,HttpServletRequest request) throws Exception {
		String candidateEducation = name;
			Boolean canEdu = candidateEducationService.validateEducationName(candidateId, candidateEducation );
			return canEdu;
	}
	
	
	
}

