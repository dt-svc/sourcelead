package com.bis.sourcelead.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.HashSet;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONArray;
import org.json.JSONException;
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
import com.bis.sourcelead.entity.EmailAttachment;
import com.bis.sourcelead.entity.EmailMessage;
import com.bis.sourcelead.entity.EmailRecipient;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.Requirement;
import com.bis.sourcelead.entity.RequirementSubmission;
import com.bis.sourcelead.entity.RequirementSubmissionTemplateFields;
import com.bis.sourcelead.entity.SubmissionXls;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.enums.ApplicantSubmissionStatus;
import com.bis.sourcelead.service.RequirementSubmissionService;
import com.bis.sourcelead.service.SubmissionXlsService;
import com.bis.sourcelead.util.EmailScenerioUtil;
import com.bis.sourcelead.util.SourceLeadRandomCodeHelper;

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
	
	@Autowired
	private SubmissionXlsService submissionXlsService;
	
	@Autowired
	EmailScenerioUtil emailScenerioUtil;
	
	@Override
	public RequirementSubmission getRequirementSubmissionById(Long submissionId) throws Exception {
		return requirementSubmissionDao.getRequirementSubmissionById(submissionId);
	}
	
	@Override
	public List<RequirementSubmission> getAllShortListCandidatesByOrgLocationsIdAndRequirementId(Long orgLocationsId,Long requirementId, List<Long> existingApplicantsIds) throws Exception {
		return requirementSubmissionDao.getAllShortListCandidatesByOrgLocationsIdAndRequirementId(orgLocationsId,requirementId,existingApplicantsIds);
	}
	
	@Override
	public List<RequirementSubmission> getAllSubmittedAndShortListAndInterviewededCandidates(Long orgLocationsId,Long requirementId, List<Long> existingApplicantsIds) throws Exception {
		return requirementSubmissionDao.getAllSubmittedAndShortListAndInterviewededCandidates(orgLocationsId,requirementId,existingApplicantsIds);
	}

	
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
					 if(jsonObj.has("WorkExperience") && !isNullOrEmpty(jsonObj.getString("WorkExperience"))){
						 candidate.setExperienceYears(jsonObj.getString("WorkExperience"));
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

	@Override
	public List<RequirementSubmission> getAllSubmissionListByRequirementIdAndSubmissionStage(Long orgLocationsId,
			Long requirementId,String submissionStatus) throws Exception {
		return requirementSubmissionDao.getAllSubmissionListByRequirementIdAndSubmissionStage(orgLocationsId, requirementId, submissionStatus);
	}

	@Override
	public void storeClientSubmission(Long requirementId, User user, OrgLocations loginOrgLocation,
			String submissionIds) throws Exception {
		
		 String[] submissionId = submissionIds.split(",");
    	 Requirement requirement = requirementDao.getRequirementByRequirementId(requirementId);
		 requirement.setId(requirementId);
		 
		 
		 Workbook createExcelworkbook =  new XSSFWorkbook();
 		 Sheet createExcelsheet = createExcelworkbook.createSheet("submissions");
 		 createExcelsheet = setExcelHeader(submissionIds,createExcelsheet);
		 
		 
    	 for(int i=0; i < submissionId.length; i++){
    		 RequirementSubmission submission = requirementSubmissionDao.getRequirementSubmissionById(Long.parseLong(submissionId[i]));
    		 RequirementSubmission clientSubmission = new RequirementSubmission();
    		 
    		 if(requirement.getParentRequirement() != null){
    			 clientSubmission.setRequirement(requirement.getParentRequirement());
    			 clientSubmission.setOrgLocations(requirement.getParentRequirement().getOrgLocationId());
        	 }else{
        		 clientSubmission.setRequirement(requirement);
        		 clientSubmission.setOrgLocations(loginOrgLocation);
        	 }
    		 clientSubmission.setCandidate(submission.getCandidate());
    		 clientSubmission.setCandidateResumes(submission.getCandidateResumes());
    		 clientSubmission.setSourceSubmission(submission);
    		 clientSubmission.setSubmissionTemplateJsonString(submission.getSubmissionTemplateJsonString());
    		 clientSubmission.setSubmissionTemplateJsonStringValue(submission.getSubmissionTemplateJsonStringValue());
    		 clientSubmission.setSubmissionStatus(ApplicantSubmissionStatus.SUBMISSION);
    		 clientSubmission.setStatus(Constants.CANDIDATE_ACTIVE);
    		 clientSubmission.setCreatedDate(new Date());
    		 clientSubmission.setCreatedBy(user.getUserCode());
    		 clientSubmission = requirementSubmissionDao.storeRequirementSubmission(clientSubmission);
    		 
    		 RequirementSubmissionTemplateFields templateFields = requirementSubmissionTemplateFieldsDao.getSubmissionsTemplateFieldsByRequirementSubmissionsId(submission.getId());
    		 
    		 RequirementSubmissionTemplateFields clientSubmissionFields = new RequirementSubmissionTemplateFields();
    		 clientSubmissionFields.setRequirementSubmission(clientSubmission);
    		
    		 if(!isNullOrEmpty(templateFields.getFirstName())){
    			 clientSubmissionFields.setFirstName(templateFields.getFirstName()); 
    		 }
    		 
    		 if(!isNullOrEmpty(templateFields.getLastName())){
    			 clientSubmissionFields.setLastName(templateFields.getLastName()); 
    		 }
    		 
    		 if(!isNullOrEmpty(templateFields.getPhoneNumber())){
    			 clientSubmissionFields.setPhoneNumber(templateFields.getPhoneNumber()); 
    		 }
    		 
    		 if(!isNullOrEmpty(templateFields.getAlternateNumber())){
    			 clientSubmissionFields.setAlternateNumber(templateFields.getAlternateNumber()); 
    		 }
    		 
    		 if(!isNullOrEmpty(templateFields.getEmailId())){
    			 clientSubmissionFields.setEmailId(templateFields.getEmailId()); 
    		 }
    		 
    		 if(!isNullOrEmpty(templateFields.getCountry())){
    			 clientSubmissionFields.setCountry(templateFields.getCountry()); 
    		 }
    		 
    		 if(!isNullOrEmpty(templateFields.getState())){
    			 clientSubmissionFields.setState(templateFields.getState()); 
    		 }
    		 
    		 if(!isNullOrEmpty(templateFields.getCity())){
    			 clientSubmissionFields.setCity(templateFields.getCity()); 
    		 }
    		 
    		 if(null != templateFields.getDateOfBirth()){
    			 clientSubmissionFields.setDateOfBirth(templateFields.getDateOfBirth()); 
    		 }
    		 
    		 if(!isNullOrEmpty(templateFields.getCurrentORLastCompany())){
    			 clientSubmissionFields.setCurrentORLastCompany(templateFields.getCurrentORLastCompany()); 
    		 }
    		 
    		 if(!isNullOrEmpty(templateFields.getDesignation())){
    			 clientSubmissionFields.setDesignation(templateFields.getDesignation()); 
    		 }
    		 
    		 if(!isNullOrEmpty(templateFields.getEducation())){
    			 clientSubmissionFields.setEducation(templateFields.getEducation()); 
    		 }
    		 
    		 if(!isNullOrEmpty(templateFields.getWorkExperience())){
    			 clientSubmissionFields.setWorkExperience(templateFields.getWorkExperience()); 
    		 }
    		 
    		 if(!isNullOrEmpty(templateFields.getCurrentCTC())){
    			 clientSubmissionFields.setCurrentCTC(templateFields.getCurrentCTC()); 
    		 }
    		 
    		 if(!isNullOrEmpty(templateFields.getExpectedCTC())){
    			 clientSubmissionFields.setExpectedCTC(templateFields.getExpectedCTC()); 
    		 }
    		 
    		 if(!isNullOrEmpty(templateFields.getNoticePeriod())){
    			 clientSubmissionFields.setNoticePeriod(templateFields.getNoticePeriod()); 
    		 }
    		 
    		 clientSubmissionFields.setStatus(Constants.CANDIDATE_ACTIVE);
    		 clientSubmissionFields.setCreatedBy(user.getUserCode());
    		 clientSubmissionFields.setCreatedDate(new Date());
    		 requirementSubmissionTemplateFieldsDao.storeRequirementSubmissionTemplateFields(clientSubmissionFields);
    		 
    		 submission.setSubmissionStatus(ApplicantSubmissionStatus.SUBMIT_TO_CLIENT);
    		 submission.setUpdatedBy(user.getUserCode());
    		 submission.setUpdatedDate(new Date());
    		 requirementSubmissionDao.storeRequirementSubmission(submission);
    		 
    		 
    		 String data = clientSubmission.getSubmissionTemplateJsonStringValue();
    		 try {
    		     // create the json array from String rules
    		     JSONArray jsonRules = new JSONArray(data);
    		     System.out.println(jsonRules.length());
    		     // iterate over the rules 
    		     Row writeRow = createExcelsheet.createRow(i+1);
    		     for (int j=0; j<jsonRules.length();j++){
    		         JSONObject obj = (JSONObject) jsonRules.get(j);
    		         createXlsSheet(createExcelsheet,writeRow,j,obj.getString("value"));
    		     }
    		     
    		 } catch (JSONException e){
    		     e.printStackTrace();
    		 }
    		 
    		 DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
        	 
        	 String fileName = "Submission"+SourceLeadRandomCodeHelper.generateSubmissionXlsRandomCode()+""+dateFormat.format(new Date())+".xls";
    		 
    		 ByteArrayOutputStream bos = new ByteArrayOutputStream();
    		 createExcelworkbook.write(bos);
    		 byte[] bytes = bos.toByteArray();
    		 
    		 String UUID = SourceLeadRandomCodeHelper.generateSubmissionXlsRandomCode();
    		 SubmissionXls submissionXls = new SubmissionXls();
    		 submissionXls.setUuid(UUID);
    		 submissionXls.setDocFile(bytes);
    		 submissionXls.setFileContentType("XLS");
    		 submissionXls.setFileName(fileName);
    		 submissionXls.setRequirement(requirement);
    		 submissionXls.setCreatedBy(user.getUserCode());
    		 submissionXls.setCreatedDate(new Date());
    		 submissionXlsService.storeSubmissionXls(submissionXls);
    		 
    		 EmailMessage emailMessage = new EmailMessage();
    		 emailMessage.setFromEmail("peopleaxisindia@gmail.com");
    		 emailMessage.setToEmail("ivenkat07@gmail.com");
    		 emailMessage.setSubject("hiiiiiii");
    		 
    		 ArrayList<EmailRecipient> emailRecipientList = new ArrayList<EmailRecipient>();
    		 EmailRecipient emailRecipient = new EmailRecipient();
    		 emailRecipient.setEmail(emailMessage.getToEmail());
    		 emailRecipientList.add(emailRecipient);
    		 
    		
    		 ArrayList<EmailAttachment> attachmentList = new ArrayList<EmailAttachment>();
    		 EmailAttachment emailAttachment = new EmailAttachment();
    		 emailAttachment.setName(UUID);
    		 attachmentList.add(emailAttachment);
    		 
    		 emailScenerioUtil.sendEmail(emailMessage,emailRecipientList,attachmentList);
    		 
    		 
    		 
    	 }
		
	}

	@Override
	public Integer getRequirementSubmissionsCountByOrgLocationsIdAndSubmissionStatus(Long orgLocationsId, String submissionStatus) {
		return requirementSubmissionDao.getRequirementSubmissionsCountByOrgLocationsIdAndSubmissionStatus(orgLocationsId,submissionStatus);
	}

	@Override
	public List<RequirementSubmission> getRequirementPerCandidate(Long candudateId) throws Exception {
		return requirementSubmissionDao.getRequirementPerCandidate(candudateId);
	}
	
	
	 private Sheet setExcelHeader(String submissionsList,Sheet createExcelsheet) throws Exception {
  	   
 	    Row excelHeader = createExcelsheet.createRow(0);
 	    String[] submissionId = submissionsList.split(",");
   	    for(int i=0; i < submissionId.length; i++){
   		 RequirementSubmission submission = requirementSubmissionDao.getRequirementSubmissionById(Long.parseLong(submissionId[i]));
   		 String data = submission.getSubmissionTemplateJsonStringValue();
   		 try {
   		     JSONArray jsonRules = new JSONArray(data);
   		     System.out.println(jsonRules.length());
   		     for (int j=0; j<jsonRules.length();j++){
   		         JSONObject obj = (JSONObject) jsonRules.get(j);
   		         excelHeader.createCell(j).setCellValue(obj.getString("label"));
   		     }
   		 } catch (JSONException e){
   		     e.printStackTrace();
   		 }
   	 }
		
   	  return createExcelsheet;
	}

private void createXlsSheet(Sheet createExcelsheet,Row writeRow,int column,String value) throws IOException {
	   createExcelsheet.setColumnWidth(column, 5000);
	   if(!isNullOrEmpty(value)){ 
			writeRow.createCell(column).setCellValue(value);
		} else {
			 writeRow.createCell(column).setCellValue("No Data");
		}
	   
}
	
	

}
