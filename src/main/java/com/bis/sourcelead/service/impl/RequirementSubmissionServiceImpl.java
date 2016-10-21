package com.bis.sourcelead.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
<<<<<<< HEAD
import java.util.List;
=======
import java.util.HashSet;
>>>>>>> fc293ea83903a8e989446ed1d7a2baf11ffc992e

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.CandidateDao;
import com.bis.sourcelead.dao.CandidateEducationDao;
import com.bis.sourcelead.dao.CandidateEmployersDao;
import com.bis.sourcelead.dao.CandidateResumesDao;
import com.bis.sourcelead.dao.RequirementDao;
import com.bis.sourcelead.dao.RequirementSubmissionDao;
import com.bis.sourcelead.dao.RequirementSubmissionTemplateFieldsDao;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.CandidateEducation;
import com.bis.sourcelead.entity.CandidateEmployers;
import com.bis.sourcelead.entity.CandidateResumes;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.Requirement;
import com.bis.sourcelead.entity.RequirementSubmission;
import com.bis.sourcelead.entity.RequirementSubmissionTemplateFields;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.enums.ApplicantSubmissionStatus;
import com.bis.sourcelead.service.RequirementSubmissionService;

/**
 * 
 * @author Laxman
 * @date 20th October 2016
 *
 */


@Service
public class RequirementSubmissionServiceImpl implements RequirementSubmissionService{
	private static final Logger logger = LoggerFactory.getLogger(RequirementSubmissionServiceImpl.class);
	
	@Autowired
	private RequirementSubmissionDao requirementSubmissionDao;
	
	@Autowired
	private RequirementDao requirementDao;
	
	@Autowired
	private RequirementSubmissionTemplateFieldsDao  requirementSubmissionTemplateFieldsDao;
	
	@Autowired
	private CandidateDao candidateDao;
	
	@Autowired
	private CandidateEducationDao candidateEducationDao;
	
	@Autowired
	private CandidateResumesDao candidateResumesDao;
	
	@Autowired
	private CandidateEmployersDao candidateEmployersDao;
	

	@Override
	public RequirementSubmission storeRequirementSubmission(RequirementSubmission requirementSubmission) throws Exception {
		logger.debug("In ProjectsServiceImpl class saveProject method...");
		return requirementSubmissionDao.storeRequirementSubmission(requirementSubmission);
	}
	
	protected static boolean isNullOrEmpty(String str) {
		return (str == null || "".equals(str.trim()));
	}


	@Override
	public void saveRequirementSubmission(JSONObject jsonObj,User user,OrgLocations orgLocations,String jsonStringvalues,MultipartFile multiFile) throws Exception {
		
		 Requirement requirement = null;;
		 RequirementSubmission requirementSubmission = new RequirementSubmission();
		 Candidate candidate = null;
		 CandidateResumes candidateResumes = null;
		 
		 if(jsonObj.has("requirementId")){
			 requirement = requirementDao.getRequirementByRequirementId(Long.parseLong(jsonObj.getString("requirementId")));
			 requirementSubmission.setRequirement(requirement);
			 requirementSubmission.setSubmissionTemplateJsonString(requirement.getSubmissionTemplateJsonString());
			 
		 }
		 
		 if(jsonObj.has("candidateId") && !isNullOrEmpty(jsonObj.getString("candidateId"))){
			 candidate = candidateDao.getCandidateByCandidateId(Long.parseLong(jsonObj.getString("candidateId")));
			 candidate.setId(Long.parseLong(jsonObj.getString("candidateId")));
			 requirementSubmission.setCandidate(candidate);
		 }else{
			 
			 
			       candidate = new Candidate();
			       candidate.setOrgLocationId(orgLocations);
					
			        if(jsonObj.has("FirstName") && !isNullOrEmpty(jsonObj.getString("FirstName"))){
					candidate.setFirstName(jsonObj.getString("FirstName"));
					}
					
			        if(jsonObj.has("LastName") && !isNullOrEmpty(jsonObj.getString("LastName"))){
					candidate.setLastName(jsonObj.getString("LastName") );
					}
					
			        if(jsonObj.has("country") && !isNullOrEmpty(jsonObj.getString("country"))){
			        	candidate.setCountry(jsonObj.getString("country"));
					}
					 
					if(jsonObj.has("state") && !isNullOrEmpty(jsonObj.getString("state"))){
						 candidate.setState(jsonObj.getString("state"));
					}
					 
					if(jsonObj.has("city") && !isNullOrEmpty(jsonObj.getString("city"))){
						 candidate.setCity(jsonObj.getString("city"));
					}
					
					if(jsonObj.has("PhoneNumber") && !isNullOrEmpty(jsonObj.getString("PhoneNumber"))){
						 candidate.setMobilePhone(jsonObj.getString("PhoneNumber"));
					}
					
					if(jsonObj.has("EmailId") && !isNullOrEmpty(jsonObj.getString("EmailId"))){
					candidate.setEmail(jsonObj.getString("EmailId"));
					}
					
					if(jsonObj.has("CurrentCTC") && !isNullOrEmpty(jsonObj.getString("CurrentCTC"))){
						candidate.setCurrentRate(jsonObj.getString("CurrentCTC"));
					}
					 
					if(jsonObj.has("ExpectedCTC") && !isNullOrEmpty(jsonObj.getString("ExpectedCTC"))){
						 candidate.setExpectedRate(jsonObj.getString("ExpectedCTC"));
					}
					
					if(jsonObj.has("Designation") && !isNullOrEmpty(jsonObj.getString("Designation"))){
						candidate.setDesignation(jsonObj.getString("Designation"));
					}
					candidate.setCreatedBy(user.getUserCode());
					candidate.setCreatedDate(new Date());
					candidate.setStatus(Constants.CANDIDATE_ACTIVE);
					
					candidateDao.storeCandidate(candidate);
					requirementSubmission.setCandidate(candidate);
					
					if(jsonObj.has("Education") && !isNullOrEmpty(jsonObj.getString("Education"))){
						CandidateEducation candidateEducation = new CandidateEducation();
						candidateEducation.setCandidateId(candidate);
						candidateEducation.setOrglocationId(orgLocations);
						candidateEducation.setName(jsonObj.getString("Education"));
						candidateEducation.setCreatedBy(user.getUserCode());
						candidateEducation.setCreatedDate(new Date());
						candidateEducation.setStatus(Constants.CANDIDATE_ACTIVE);
						candidateEducationDao.storeCandidateEducation(candidateEducation);
						
					 }
					
					 if(jsonObj.has("Current/LastCompany") && !isNullOrEmpty(jsonObj.getString("Current/LastCompany"))){
						 CandidateEmployers candidateEmployers = new CandidateEmployers();
						 candidateEmployers.setOrgLocationId(orgLocations);
						 candidateEmployers.setCandidateId(candidate);
						 candidateEmployers.setName(jsonObj.getString("Current/LastCompany"));
						 candidateEmployers.setStatus(Constants.CANDIDATE_ACTIVE);
						 candidateEmployers.setCreatedDate(new Date());
						 candidateEmployers.setCreatedBy(user.getUserCode());
						 candidateEmployers.setUpdatedDate(new Date());
						 candidateEmployersDao.storeCandidateEmployers(candidateEmployers);
						 
					 }
					
					
		 }
		 
		 if(jsonObj.has("candidateResume") && !isNullOrEmpty(jsonObj.getString("candidateResume"))){
			 candidateResumes = candidateResumesDao.getCandidateResumeByResumeId(Long.parseLong(jsonObj.getString("candidateResume")));
			 candidateResumes.setId(Long.parseLong(jsonObj.getString("candidateResume")));
			 requirementSubmission.setCandidateResumes(candidateResumes);
		 }else{
			 
			 if(multiFile != null && multiFile.getOriginalFilename() != null && !multiFile.getOriginalFilename().isEmpty()){
				 candidateResumes = new CandidateResumes();
				 candidateResumes.setCandidateId(candidate);
				 candidateResumes.setDocFile(multiFile.getBytes());
				 candidateResumes.setFileName(multiFile.getOriginalFilename());
				 candidateResumes.setTitle(multiFile.getOriginalFilename());
				 candidateResumes.setFileContentType(multiFile.getContentType());
				 candidateResumes.setStatus(Constants.CANDIDATE_ACTIVE);
				 candidateResumes.setCreatedDate(new Date());
				 candidateResumes.setCreatedBy(user.getUserCode());
				 candidateResumes.setUpdatedDate(new Date());
				 candidateResumes.setOrgLocationId(orgLocations);
				 candidateResumesDao.storeCandidateResumes(candidateResumes);
				 requirementSubmission.setCandidateResumes(candidateResumes);
			 }
		 }
		 
		 requirementSubmission.setOrgLocations(orgLocations);
		 requirementSubmission.setSubmissionTemplateJsonStringValue(jsonStringvalues);
		 requirementSubmission.setSubmissionStatus(ApplicantSubmissionStatus.SUBMISSION);
		 requirementSubmission.setStatus(Constants.CANDIDATE_ACTIVE);
		 requirementSubmission.setCreatedBy(user.getUserCode());
		 requirementSubmission.setCreatedDate(new Date());
		 requirementSubmission = requirementSubmissionDao.storeRequirementSubmission(requirementSubmission);
		 
		 RequirementSubmissionTemplateFields templateFields = new RequirementSubmissionTemplateFields();
		 templateFields.setRequirementSubmission(requirementSubmission);
		
		 if(jsonObj.has("FirstName") && !isNullOrEmpty(jsonObj.getString("FirstName"))){
			 templateFields.setFirstName(jsonObj.getString("FirstName"));
		 }
		 
		 if(jsonObj.has("LastName") && !isNullOrEmpty(jsonObj.getString("LastName"))){
			 templateFields.setLastName(jsonObj.getString("LastName"));
		 }
		 
		 if(jsonObj.has("PhoneNumber") && !isNullOrEmpty(jsonObj.getString("PhoneNumber"))){
			 templateFields.setPhoneNumber(jsonObj.getString("PhoneNumber"));
		 }
		 
		 if(jsonObj.has("AlternateNumber") && !isNullOrEmpty(jsonObj.getString("AlternateNumber"))){
			 templateFields.setAlternateNumber(jsonObj.getString("AlternateNumber"));
		 }
		 
		 if(jsonObj.has("EmailId") && !isNullOrEmpty(jsonObj.getString("EmailId"))){
			 templateFields.setEmailId(jsonObj.getString("EmailId"));
		 }
		 
		 if(jsonObj.has("country") && !isNullOrEmpty(jsonObj.getString("country"))){
			 templateFields.setCountry(jsonObj.getString("country"));
		 }
		 
		 if(jsonObj.has("state") && !isNullOrEmpty(jsonObj.getString("state"))){
			 templateFields.setState(jsonObj.getString("state"));
		 }
		 
		 if(jsonObj.has("city") && !isNullOrEmpty(jsonObj.getString("city"))){
			 templateFields.setCity(jsonObj.getString("city"));
		 }
		 
		 if(jsonObj.has("DOB") && !isNullOrEmpty(jsonObj.getString("DOB"))){
				try {
					   SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					   templateFields.setDateOfBirth(sdf.parse(jsonObj.getString("DOB")));
					} catch (Exception e) {
					  e.printStackTrace();
					}
			}
		 
		 if(jsonObj.has("Current/LastCompany") && !isNullOrEmpty(jsonObj.getString("Current/LastCompany"))){
			 templateFields.setCurrentORLastCompany(jsonObj.getString("Current/LastCompany"));
		 }
		 
		 if(jsonObj.has("Designation") && !isNullOrEmpty(jsonObj.getString("Designation"))){
			 templateFields.setDesignation(jsonObj.getString("Designation"));
		 }
		 
		 if(jsonObj.has("WorkExperience") && !isNullOrEmpty(jsonObj.getString("WorkExperience"))){
			 templateFields.setWorkExperience(jsonObj.getString("WorkExperience"));
		 }
		 
		 if(jsonObj.has("Education") && !isNullOrEmpty(jsonObj.getString("Education"))){
			 templateFields.setEducation(jsonObj.getString("Education"));
		 }
		 
		 if(jsonObj.has("CurrentCTC") && !isNullOrEmpty(jsonObj.getString("CurrentCTC"))){
			 templateFields.setCurrentCTC(jsonObj.getString("CurrentCTC"));
		 }
		 
		 if(jsonObj.has("ExpectedCTC") && !isNullOrEmpty(jsonObj.getString("ExpectedCTC"))){
			 templateFields.setExpectedCTC(jsonObj.getString("ExpectedCTC"));
		 }
		 
		 if(jsonObj.has("NoticePeriod") && !isNullOrEmpty(jsonObj.getString("NoticePeriod"))){
			 templateFields.setNoticePeriod(jsonObj.getString("NoticePeriod"));
		 }
		 
		 templateFields.setStatus(Constants.CANDIDATE_ACTIVE);
		 templateFields.setCreatedBy(user.getUserCode());
		 templateFields.setCreatedDate(new Date());
		 
		 requirementSubmissionTemplateFieldsDao.storeRequirementSubmissionTemplateFields(templateFields);
		
	}

	@Override
	public List<RequirementSubmission> getSubmissionsByRequirementId(Long requirementId,User user, OrgLocations loginOrgLocation) throws Exception {
		return requirementSubmissionDao.getSubmissionsByRequirementId(requirementId,user, loginOrgLocation);
	}

}
