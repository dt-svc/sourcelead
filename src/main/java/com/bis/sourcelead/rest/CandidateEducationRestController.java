package com.bis.sourcelead.rest;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bis.sourcelead.annotations.Validator;
import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dto.CandidateEducationDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.elk.index.CandidateIndex;
import com.bis.sourcelead.elk.setup.SourceleadELKBaseIndexer;
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
	
	@Autowired
	private SourceleadELKBaseIndexer<CandidateIndex> sourceleadELKBaseIndexer;

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
		candidateEducationDTO.setDateFormat(loginOrgLocation.getDateFormat());
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
	@RequestMapping(value = {"/getCandidateEducationListByCandidateId","/rest/getCandidateEducationListByCandidateId"}, method = RequestMethod.GET)
	public ResponseList<CandidateEducationDTO> getCandidateEducationListByCandidateId(@RequestParam(value = "candidateId", required = false) String candidateId,HttpServletRequest request) throws Exception {
		
		 OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
		 ResponseList<CandidateEducationDTO> candiEduList = new ResponseList<>();
		 List<CandidateEducation>   candidatesEducationList = candidateEducationService.getCandidateEducationListByCandidateIdAndOrgLocationID(Long.valueOf(candidateId), loginOrgLocation.getId());
		List<CandidateEducationDTO> eduList = new ArrayList<>();
		 for(CandidateEducation candidateEducation :candidatesEducationList){
			 CandidateEducationDTO edu = new CandidateEducationDTO();
			 edu.setName(candidateEducation.getName());
			 edu.setSpecialization(candidateEducation.getSpecialization());
			 edu.setStartDateStr(candidateEducation.getStartDateStr());
			 edu.setEndDateStr(candidateEducation.getEndDateStr());
			 edu.setGrade(candidateEducation.getGrade());
			 edu.setGradeType(candidateEducation.getGradeType());
			 edu.setInstitute(candidateEducation.getInstitute());
			 edu.setAddress(candidateEducation.getAddress());
			 edu.setId(candidateEducation.getId());
			 edu.setCandidateId(candidateEducation.getCandidateId().getId());
			 edu.setCandidateEduId(candidateEducation.getId().toString());
			 eduList.add(edu);
		 }
		 candiEduList.setData(eduList);
		 return candiEduList;
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
		 if (null != candidate && null != candidate.getId()) {
			 CandidateIndex candidateIndex = new CandidateIndex();
			 candidateIndex.setCandidateId(candidate.getId());
			 DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			 candidateIndex.setUpdatedDate(dateFormat.format(new Date()));
			 candidateIndex.setCurrentResumeId(Constants.CANDIATE_RESUME_IN_ACTIVE);
			
			sourceleadELKBaseIndexer.saveOrUpdateElkDocument(candidateIndex, Constants.ELK_CANDIDATE_INDEX, Constants.ELK_CANDIDATE_TYPE, candidate.getId().toString());
		}	
		 
		 Integer percentage = candidateService.getCandidatePercentage(candidate,orgLocations);
			return percentage.toString();
	}

	
//	@PreAuthorize("hasAnyRole('ROLE_VIEW_EDIT_CANDIDATE_EDUCATION','ROLE_CROWDSOURCING_PROFILE','ROLE_CROWDSOURCING_CANDIDATES','ROLE_VIEW_EDIT_ASSOCIATE_EDUCATION_DETAILS')")
	@RequestMapping(value = {"/getCandidateEducationBycandidateIdandEducationId","/rest/getCandidateEducationBycandidateIdandEducationId"}, method = RequestMethod.POST)
	public CandidateEducationDTO getCandidateEducationBycandidateIdandEducationId(@RequestParam(value = "candidateId", required = false) Long candidateId,@RequestParam(value = "candidateEducationId", required = false) Long candidateEducationId,HttpServletRequest request) throws Exception {
		
		 CandidateEducationDTO candEduDTO = new CandidateEducationDTO();
		 CandidateEducation  candidatesEducationList = candidateEducationService.getCandidateEducationBycandidateIdandEducationId(candidateId, candidateEducationId);
		 Candidate can = candidateService.getCandidateById(candidateId);
		 //SimpleDateFormat formatter = can.getOrgLocationId().getLocFullFormDateFormatter();
		    
		    if(candidatesEducationList.getName() != null){
		    	candEduDTO.setName(candidatesEducationList.getName());
		    }
		    
		    if(candidatesEducationList.getInstitute() != null){
		    	candEduDTO.setInstitute(candidatesEducationList.getInstitute());
		    }
		    
		    if(candidatesEducationList.getSpecialization() != null){
		    	candEduDTO.setSpecialization(candidatesEducationList.getSpecialization());
		    }
		    
		    if(candidatesEducationList.getAddress() !=null){
		    	candEduDTO.setAddress(candidatesEducationList.getAddress());
		    }
		    
		    if(candidatesEducationList.getGrade() !=null){
		    	candEduDTO.setGrade(candidatesEducationList.getGrade());
		    }
		    
		    if(candidatesEducationList.getGradeType() !=null){
		    	candEduDTO.setGradeType(candidatesEducationList.getGradeType());
		    }
		    
		    if(candidatesEducationList.getAllAndCandidateEducations() != null){
		    	candEduDTO.setAllAndCandidateEducations(candidatesEducationList.getAllAndCandidateEducations());
		    }
		 
		    if(candidatesEducationList.getStartDate() != null){
			 candEduDTO.setStartDateStr(commonUtil.dateStrFromOrgDateFormatter(can.getOrgLocationId().getDateFormat(),candidatesEducationList.getStartDate())); 
			 }
			 if(candidatesEducationList.getEndDate() != null){
				 candEduDTO.setEndDateStr(commonUtil.dateStrFromOrgDateFormatter(can.getOrgLocationId().getDateFormat(),candidatesEducationList.getEndDate())); 
			 }
			
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
					candEduDTO.setAllAndCandidateEducations(lhs);
				
				     return candEduDTO;
	}
	
	@RequestMapping(value = "/upDateCandidateEducationByCandidateIdAndCandidateEducationId")
	public String upDateCandidateEducationByCandidateIdAndCandidateEducationId(CandidateEducationDTO candidateEducationDTO,@RequestParam(value = "candidateId", required = false) Long candidateId,HttpServletRequest request) throws Exception {

		    OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
		    User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			CandidateEducation candidateEdu;
			candidateEducationDTO.setDateFormat(loginOrgLocation.getDateFormat());
			candidateEdu = candidateEducationService.storeCandidateEducation(candidateEducationDTO,candidateId,loginOrgLocation.getId(), user);
			return "success";
	}
	
	
	@RequestMapping(value = "/validateEduname")
	public Boolean validateEduname(@RequestParam(value="name", required=false) String  name,@RequestParam(value = "candidateId", required = false) Long candidateId,HttpServletRequest request) throws Exception {
		String candidateEducation = name;
			Boolean canEdu = candidateEducationService.validateEducationName(candidateId, candidateEducation );
			return canEdu;
	}
	
	
	
}

