package com.bis.sourcelead.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
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
import org.springframework.beans.factory.annotation.Value;
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
import com.bis.sourcelead.dao.UserDao;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.CandidateEducation;
import com.bis.sourcelead.entity.CandidateEmployers;
import com.bis.sourcelead.entity.CandidateResumes;
import com.bis.sourcelead.entity.EmailAttachment;
import com.bis.sourcelead.entity.EmailMessage;
import com.bis.sourcelead.entity.EmailRecipient;
import com.bis.sourcelead.entity.EmailScenario;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.Requirement;
import com.bis.sourcelead.entity.RequirementRateSubmissionNegotiation;
import com.bis.sourcelead.entity.RequirementSubmission;
import com.bis.sourcelead.entity.RequirementSubmissionTemplateFields;
import com.bis.sourcelead.entity.SubmissionXls;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.enums.ApplicantSubmissionStatus;
import com.bis.sourcelead.enums.SubmissionRateStatus;
import com.bis.sourcelead.service.CandidateService;
import com.bis.sourcelead.service.EmailScenarioService;
import com.bis.sourcelead.service.RequirementSubmissionService;
import com.bis.sourcelead.service.SubmissionXlsService;
import com.bis.sourcelead.util.CommonUtil;
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
	EmailScenarioService emailScenarioService;
	
	@Autowired
	UserDao userDao;
	
	@Value("${sourceleadUrl}")
    private String sourceleadUrl;
	
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
	
	@Autowired
	private CommonUtil commonUtil;
	
	@Autowired
	CandidateService candidateService;
	
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
		 
		 if(jsonObj.has("needApplicantPermit") && !isNullOrEmpty(jsonObj.getString("needApplicantPermit"))){
			 if(jsonObj.getString("needApplicantPermit").equals("Y")){
				 requirementSubmission.setNeedApplicantPermit("Y");
				 requirementSubmission.setSubmissionStatus(ApplicantSubmissionStatus.REQUEST_SENT);
			 } 
		 }else{
			 requirementSubmission.setNeedApplicantPermit("N");
			 requirementSubmission.setSubmissionStatus(ApplicantSubmissionStatus.SUBMISSION);
		 }
		
		 requirementSubmission.setStatus(Constants.CANDIDATE_ACTIVE);
		 requirementSubmission.setCreatedBy(user.getUserCode());
		 requirementSubmission.setCreatedDate(new Date());
		 requirementSubmission = requirementSubmissionDao.storeRequirementSubmission(requirementSubmission);
		
		 if("Y".equalsIgnoreCase(requirementSubmission.getNeedApplicantPermit())){
			 applicantReceiveMailAboutRequirement(requirementSubmission.getId(),requirement,candidate,user,orgLocations);
		 }
		 
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
			String submissionIds,Org org) throws Exception {
		
		 String[] submissionId = submissionIds.split(",");
    	 Requirement requirement = requirementDao.getRequirementByRequirementId(requirementId);
		 requirement.setId(requirementId);
		 
		 String requirementName = null;
		 if(requirement.getParentRequirement() != null){
			 requirementName = requirement.getParentRequirement().getJobTitle();
		 }else{
			 requirementName = requirement.getJobTitle();
		 }
		 
		 
		 String fullName = null;
    	 if(!isNullOrEmpty(user.getLastName())){
    		 fullName = user.getFirstName()+" "+user.getLastName();
    	 }else{
    		 fullName = user.getFirstName();
    	 }
    	 
    	 DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
		 
		 Workbook createExcelworkbook =  new XSSFWorkbook();
 		 Sheet createExcelsheet = createExcelworkbook.createSheet("submissions "+dateFormat.format(new Date()));
 		 Row writeRow = null;
 		 
 		 CellStyle style = createExcelworkbook.createCellStyle();
	     style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
	     style.setFillPattern(CellStyle.SOLID_FOREGROUND);
	     Font font = createExcelworkbook.createFont();
         font.setColor(IndexedColors.BLACK.getIndex());
         style.setFont(font);
 		 
 		 writeRow = createExcelsheet.createRow(1);
 		 createExcelsheet.setColumnWidth(0, 10000);
 		 createExcelsheet.setColumnWidth(1, 10000);
 		 
 		 Cell cell1 = writeRow.createCell(0);
 		 cell1.setCellValue("Requirement Name");
 		 cell1.setCellStyle(style);
 		 
 		 Cell cell2 = writeRow.createCell(1);
 		 cell2.setCellValue(requirementName);
 		 cell2.setCellStyle(style);
 		 
 		 
 		 writeRow = createExcelsheet.createRow(2);
 		 createExcelsheet.setColumnWidth(0, 10000);
 		 createExcelsheet.setColumnWidth(1, 10000);
 		 
 		 Cell cell3 = writeRow.createCell(0);
 		 cell3.setCellValue("Vendor");
 		 cell3.setCellStyle(style);
 		 
 		 Cell cell4 = writeRow.createCell(1);
 		 cell4.setCellValue(org.getOrgName());
 		 cell4.setCellStyle(style);
 		 
 		 writeRow = createExcelsheet.createRow(3);
		 createExcelsheet.setColumnWidth(0, 10000);
		 createExcelsheet.setColumnWidth(1, 10000);
		 
		 Cell cell5 = writeRow.createCell(0);
		 cell5.setCellValue("Submitted By");
		 cell5.setCellStyle(style);
		 
		 Cell cell6 = writeRow.createCell(1);
		 cell6.setCellValue(fullName);
		 cell6.setCellStyle(style);
		 
		 writeRow = createExcelsheet.createRow(4);
		 createExcelsheet.setColumnWidth(0, 10000);
		 createExcelsheet.setColumnWidth(1, 10000);
		 
		 Cell cell7 = writeRow.createCell(0);
		 cell7.setCellValue("Number Of Candidates");
		 cell7.setCellStyle(style);
		 
		 Cell cell8 = writeRow.createCell(1);
		 cell8.setCellValue(String.valueOf(submissionId.length));
		 cell8.setCellStyle(style);
 		   
 		 createExcelsheet = setExcelHeader(submissionIds,createExcelsheet,style);
 		 String UUID = null;
		 
    	 for(int i=0; i < submissionId.length; i++){
    		 RequirementSubmission submission = requirementSubmissionDao.getRequirementSubmissionById(Long.parseLong(submissionId[i]));
    		 RequirementSubmission clientSubmission = new RequirementSubmission();
    		 
    		 if(requirement.getParentRequirement() != null && requirement.getIsClientJob() != null &&  requirement.getIsClientJob().equalsIgnoreCase("Y")){
    			 clientSubmission.setRequirement(requirement.getParentRequirement());
    			 clientSubmission.setOrgLocations(requirement.getParentRequirement().getOrgLocationId());
        	 }
    		 else if(requirement.getParentRequirement() == null && requirement.getIsClientJob() != null &&  requirement.getIsClientJob().equalsIgnoreCase("Y")){
    			 clientSubmission.setRequirement(requirement);
    			 if("sladmin".equalsIgnoreCase(user.getUserCode())){
                     clientSubmission.setOrgLocations(requirement.getClientId().getResponderLocationId());
                 }else{
                     clientSubmission.setOrgLocations(requirement.getClientId().getLocationId());    
                 }
    			 
        	 }
    		 
    		 clientSubmission.setCandidate(submission.getCandidate());
    		 clientSubmission.setCandidateResumes(submission.getCandidateResumes());
    		 clientSubmission.setSourceSubmission(submission);
    		 clientSubmission.setSubmissionTemplateJsonString(submission.getSubmissionTemplateJsonString());
    		 clientSubmission.setSubmissionTemplateJsonStringValue(submission.getSubmissionTemplateJsonStringValue());
    		 clientSubmission.setSubmissionStatus(ApplicantSubmissionStatus.SUBMISSION);
    		 clientSubmission.setVendorLocationId(loginOrgLocation);
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
    		     writeRow = createExcelsheet.createRow(i+8);
    		     for (int j=0; j<jsonRules.length();j++){
    		         JSONObject obj = (JSONObject) jsonRules.get(j);
    		         createXlsSheet(createExcelsheet,writeRow,j,obj.getString("value"));
    		     }
    		     
    		 } catch (JSONException e){
    		     e.printStackTrace();
    		 }
    	 }
    	 
    	 
    	 
    	 String fileName = "Submission"+SourceLeadRandomCodeHelper.generateSubmissionXlsRandomCode()+""+dateFormat.format(new Date())+".xls";
		 
		 ByteArrayOutputStream bos = new ByteArrayOutputStream();
		 createExcelworkbook.write(bos);
		 byte[] bytes = bos.toByteArray();
		 
		 UUID = SourceLeadRandomCodeHelper.generateUUID();
		 SubmissionXls submissionXls = new SubmissionXls();
		 submissionXls.setUuid(UUID);
		 submissionXls.setDocFile(bytes);
		 submissionXls.setFileContentType("XLS");
		 submissionXls.setFileName(fileName);
		 submissionXls.setRequirement(requirement);
		 submissionXls.setSubmissionStatus("Client Submission");
		 submissionXls.setCreatedBy(user.getUserCode());
		 submissionXls.setCreatedDate(new Date());
		 submissionXlsService.storeSubmissionXls(submissionXls);
    	 
    	 
    	 EmailMessage emailMessage = new EmailMessage();
    	
    		 if(requirement.getSpocId() != null){
    			 Long managerId = null;
    			 if(null != requirement.getParentRequirement()){
    				 managerId = requirement.getSpocId().getOwnerUser().getId();
    			 }else{
    				 managerId = requirement.getSpocId().getSpocUser().getId();
    			 }
    			 
    			 User requirementManager = userDao.getUserById(managerId);
    			 emailMessage.setFirstName(requirementManager.getFirstName());
    			 emailMessage.setLastName(requirementManager.getLastName());
    			 emailMessage.setToEmail(requirementManager.getEmail());
    		 }
    	
    	 emailMessage.setCreatedBy(user.getUserCode());
		 emailMessage.setFromEmail("peopleaxisindia@gmail.com");
		 emailMessage.setFromName(fullName);
		 
		 ArrayList<EmailRecipient> emailRecipientList = new ArrayList<EmailRecipient>();
		 EmailRecipient emailRecipient = new EmailRecipient();
		 emailRecipient.setEmail(emailMessage.getToEmail());
		 emailRecipient.setName(emailMessage.getFirstName()+" "+emailMessage.getLastName());
		 emailRecipientList.add(emailRecipient);
		 
		 Map<String, String> globalVarsMap = new HashMap<String, String>();
		 globalVarsMap.put("clientFirstName", emailMessage.getFirstName());
		 globalVarsMap.put("clientLastName",emailMessage.getLastName());
		 globalVarsMap.put("loginUser", emailMessage.getFromName());
		 globalVarsMap.put("requirementTitle", requirement.getJobTitle());
		 globalVarsMap.put("loginOrgName",org.getOrgName());
		 globalVarsMap.put("LoginUser",fullName);
		 globalVarsMap.put("totalNoOfApplicants", String.valueOf(submissionId.length));
		 
		 
		 
		 //getting the current EmailScenario by EmailScenario unique name and entity type
		 EmailScenario  emailScenario = null;
			 emailScenario =  emailScenarioService.getEmailScenarioByUniqueName(Constants.SUBMIT_TO_CLIENT,Constants.ENTITY_TYPE_EMAIL);
		
		 //covert subject,body and signature
		 String subject = emailScenerioUtil.bulidSubject(emailScenario.getSubject(),globalVarsMap);
		 String body = emailScenerioUtil.buildTempBody(emailScenario.getBody(),globalVarsMap);
		 String signature = emailScenerioUtil.buildSignature(emailScenario.getSignature(),globalVarsMap);
		 String url = "";
		 
		 //construct HTML template using required data
		 String emailTemplate = emailScenerioUtil.AssembleEmailMessage(emailScenario,emailMessage, subject, body, signature, url, new Org());
		 emailMessage.setSubject(subject);
		 emailMessage.setMessageBody(emailTemplate);
		 
		
		 ArrayList<EmailAttachment> attachmentList = new ArrayList<EmailAttachment>();
		 EmailAttachment emailAttachment = new EmailAttachment();
		 emailAttachment.setName(UUID);
		 attachmentList.add(emailAttachment);
		 
		 emailScenerioUtil.sendEmail(emailMessage,loginOrgLocation.getId(),emailRecipientList,attachmentList);
		
	}

	@Override
	public Integer getRequirementSubmissionsCountByOrgLocationsIdAndSubmissionStatus(Long orgLocationsId, String submissionStatus, String dateRange) {
		return requirementSubmissionDao.getRequirementSubmissionsCountByOrgLocationsIdAndSubmissionStatus(orgLocationsId,submissionStatus,dateRange);
	}

	@Override
	public List<RequirementSubmission> getRequirementPerCandidate(Long candudateId) throws Exception {
		return requirementSubmissionDao.getRequirementPerCandidate(candudateId);
	}
	
	
	 private Sheet setExcelHeader(String submissionsList,Sheet createExcelsheet,CellStyle style) throws Exception {
 	    Row excelHeader = createExcelsheet.createRow(7);
 	    String[] submissionId = submissionsList.split(",");
   	    for(int i=0; i < submissionId.length; i++){
   		 RequirementSubmission submission = requirementSubmissionDao.getRequirementSubmissionById(Long.parseLong(submissionId[i]));
   		 String data = submission.getSubmissionTemplateJsonStringValue();
   		 try {
   		     JSONArray jsonRules = new JSONArray(data);
   		     System.out.println(jsonRules.length());
   		     for (int j=0; j<jsonRules.length();j++){
   		         JSONObject obj = (JSONObject) jsonRules.get(j);
   		         Cell cell = excelHeader.createCell(j);
   		         cell.setCellValue(obj.getString("label"));
   		         cell.setCellStyle(style);
   		     }
   		 } catch (JSONException e){
   		     e.printStackTrace();
   		 }
   	 }
		
   	  return createExcelsheet;
	}

private void createXlsSheet(Sheet createExcelsheet,Row writeRow,int column,String value) throws IOException {
	   createExcelsheet.setColumnWidth(column, 8000);
	   if(!isNullOrEmpty(value)){ 
			writeRow.createCell(column).setCellValue(value);
		} 
	   
}
	

	@Override
	public RequirementSubmission getSubmissionsBySourceSubmissionId(Long sourceSubmissionId,Long orgLocationsId) {
		return requirementSubmissionDao.getSubmissionsBySourceSubmissionId(sourceSubmissionId,orgLocationsId);
	}

	
	@Override
	public RequirementSubmission getRequirementSubmissionByIdAndSubmissionStatus(Long submissionId,
			String submissionStatus) throws Exception {
		return requirementSubmissionDao.getRequirementSubmissionByIdAndSubmissionStatus(submissionId,submissionStatus);
	}

	@Override
	public Integer getAllSubmissionCountByRequirementIdAndSubmissionStage(Long orgLocationsId, Long requirementId,
			String submissionStatus) {
		return requirementSubmissionDao.getAllSubmissionCountByRequirementIdAndSubmissionStage(orgLocationsId, requirementId, submissionStatus);
	}

	@Override
	public RequirementSubmission getSubmissionsByVendorSubmissionIdAndvendorOrgLocationId(Long sourceSubmissionId,
			Long orgLocationsId) {
		
		return requirementSubmissionDao.getSubmissionsByVendorSubmissionIdAndvendorOrgLocationId(sourceSubmissionId,orgLocationsId);
		
	}

	@Override
	public Integer getApplicantCountByrequirementIdandAssignedUsercode(Long requirementId, String userCode)
			throws Exception {
		
		    return requirementSubmissionDao.getApplicantCountByrequirementIdandAssignedUsercode(requirementId, userCode);
	}

	@Override
	public RequirementSubmission getVendorSubmissionByClientSubmissionIdAndCLientLocationId(Long clientSubmissionId,
			Long clientLocationId) {
			return requirementSubmissionDao.getVendorSubmissionByClientSubmissionIdAndCLientLocationId(clientSubmissionId,clientLocationId);
			
   }
	
	
	public void applicantReceiveMailAboutRequirement(Long submissionId ,Requirement requirement,  Candidate candidate ,User loginUser,OrgLocations orgLocations) throws Exception {
		
		 
		 EmailMessage emailMessage = new EmailMessage();
		 emailMessage.setFirstName(loginUser.getFirstName());
		 emailMessage.setLastName(loginUser.getLastName());
		 emailMessage.setToEmail(candidate.getEmail());
		 emailMessage.setFromEmail("peopleaxisindia@gmail.com");
		 emailMessage.setCreatedBy(loginUser.getUserCode());
		 emailMessage.setFromName(loginUser.getFirstName()+" "+loginUser.getLastName());
		 
		//setting the class filed values
		 Map<String, String> globalVarsMap = new HashMap<String, String>();
		 
		 if(requirement.getJobTitle()!= null && !"".equalsIgnoreCase(requirement.getJobTitle())){
			 globalVarsMap.put("requirementName", requirement.getJobTitle());
			 }else{
				 globalVarsMap.put("requirementName","--");
			 }
		 if(orgLocations.getOrg().getOrgName()!= null && !"".equalsIgnoreCase(orgLocations.getOrg().getOrgName())){
			 globalVarsMap.put("organizationName", orgLocations.getOrg().getOrgName());
			 }else{
				 globalVarsMap.put("organizationName","--");
			 }
		 if(candidate.getFirstName()+""+candidate.getLastName()!= null && !"".equalsIgnoreCase(candidate.getFirstName()+""+candidate.getLastName())){
			 globalVarsMap.put("applicantName", candidate.getFirstName()+""+candidate.getLastName());
			 }else{
				 globalVarsMap.put("applicantName","--");
			 }
		 if((requirement.getMinExp()!= null && !"".equalsIgnoreCase(requirement.getMinExp())) && (requirement.getMaxExp()!= null && !"".equalsIgnoreCase(requirement.getMaxExp()))){
			 globalVarsMap.put("experience", requirement.getMinExp()+"-"+requirement.getMaxExp()); 
		 	 }else if(requirement.getMinExp()!= null && !"".equalsIgnoreCase(requirement.getMinExp())){
			 globalVarsMap.put("experience", requirement.getMinExp());
		 	 }else{
			 globalVarsMap.put("experience","--");
			 }
		 if(requirement.getState()!= null && !"".equalsIgnoreCase(requirement.getState())){
			 globalVarsMap.put("location", requirement.getCountry()+","+requirement.getState());
		 	}else if(requirement.getCity()!= null && !"".equalsIgnoreCase(requirement.getCity())){
			 globalVarsMap.put("location", requirement.getCountry()+","+requirement.getState()+","+requirement.getCity());
		 	}else{
			globalVarsMap.put("location","--");
			}
		 if(requirement.getRate()!= null && !"".equalsIgnoreCase(requirement.getRate())){
			 globalVarsMap.put("requirementRate", requirement.getCurrencyCode()+"-"+requirement.getRate()+"-"+requirement.getRateType());
			 }else{
				 globalVarsMap.put("requirementRate","--");
			 }
		 if(requirement.getSkills()!= null && !"".equalsIgnoreCase(requirement.getSkills())){
			 globalVarsMap.put("skills", requirement.getSkills());
			 }else{
				 globalVarsMap.put("skills","--");
			 }
		 if(requirement.getDescription()!= null && !"".equalsIgnoreCase(requirement.getDescription())){
			 globalVarsMap.put("jobDescription", requirement.getDescription());
			 }else{
				 globalVarsMap.put("jobDescription","--");
			 }
		 if((loginUser.getFirstName()!= null && !"".equalsIgnoreCase(loginUser.getFirstName())) && (loginUser.getLastName()!= null && !"".equalsIgnoreCase(loginUser.getLastName()))){
			 globalVarsMap.put("loginUserName", loginUser.getFirstName()+" "+loginUser.getLastName());
			 }
		 else if(loginUser.getFirstName()!= null && !"".equalsIgnoreCase(loginUser.getFirstName())){
			 globalVarsMap.put("loginUserName", loginUser.getFirstName());
		 	}else{
			 globalVarsMap.put("loginUserName","--");
			 }                       
		
		 globalVarsMap.put("acceptLink","<a href='"+sourceleadUrl+"/"+"acceptOrRejectOrRateNegotiateApplicantRates?rateStatus=Accepted&submissionId="+submissionId+"' style='text-decoration:none; '><div style=' width:100px; border-radius:30px; background-color:#61D339 ; color:#FFF; font-size:15px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Accept</div></a>");
	        
	     globalVarsMap.put("rejectLink","<a href='"+sourceleadUrl+"/"+"acceptOrRejectOrRateNegotiateApplicantRates?rateStatus=Rejected&submissionId="+submissionId+"' style='text-decoration:none; '><div style=' width:100px; border-radius:30px; background-color:#D33B39; color:#FFF; font-size:15px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Reject</div></a>");

	     globalVarsMap.put("negotiateRateLink","<a href='"+sourceleadUrl+"/"+"acceptOrRejectOrRateNegotiateApplicantRates?rateStatus=Negotiate&submissionId="+submissionId+"' style='text-decoration:none; '><div style=' width:100px; border-radius:30px; background-color:#D33B39; color:#FFF; font-size:15px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Negotiate Rate</div></a>");


	        
	        
//	        if( !requirementOffers.getFileName().isEmpty() ){
//	            globalVarsMap.put("attachment","<a href='"+sourceleadUrl+"/"+"downloadOfferAttachment?offerId="+requirementOffers.getId()+"' style='text-decoration:none; '><div style=' width:220px; border-radius:30px; background-color:#214b90; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center;letter-spacing:2px;'>Attachment"+"<br/></div></a>");
//
//	        }else{
//	            globalVarsMap.put(" ","<a href='"+sourceleadUrl+"/"+"downloadOfferAttachment?offerId="+requirementOffers.getId()+"' style='text-decoration:none; '><div style=' width:220px; border-radius:30px; background-color:#214b90; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center;letter-spacing:2px;'>Attachment"+"<br/></div></a>");
//
//	        }
		 
		 
		 //setting the Recipient information
		 ArrayList<EmailRecipient> emailRecipientList = new ArrayList<EmailRecipient>();
		 EmailRecipient emailRecipient = new EmailRecipient();
		 emailRecipient.setEmail(emailMessage.getToEmail());
		 emailRecipient.setName(emailMessage.getFirstName()+" "+emailMessage.getLastName());
		 emailRecipientList.add(emailRecipient);
		
		 //getting the current EmailScenario by EmailScenario unique name and entity type
		 EmailScenario  emailScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.APPLICANT_RECEIVE_MAILABOUT_REQUIREMENT_EMAIL,Constants.ENTITY_TYPE_EMAIL);
		 //EmailScenario  notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.APPLICANT_RECEIVE_MAILABOUT_REQUIREMENT_NOTIFICATION,Constants.ENTITY_TYPE_NOTIFICATION);

		
		 //covert subject,body and signature
		 String subject = emailScenerioUtil.bulidSubject(emailScenario.getSubject(),globalVarsMap);
		 String body = emailScenerioUtil.buildTempBody(emailScenario.getBody(),globalVarsMap);
		 String signature = emailScenerioUtil.buildSignature(emailScenario.getSignature(),globalVarsMap);
		 String url = "";
		 
		// String notificationbody = emailScenerioUtil.buildBody(notificationScenario.getBody(),globalVarsMap);
		// notificationScenario.setBody(notificationbody);
		 
		 //construct HTML template using required data
		 String emailTemplate = emailScenerioUtil.AssembleEmailMessage(emailScenario,emailMessage, subject, body, signature, url, new Org());
		 emailMessage.setSubject(subject);
		 emailMessage.setMessageBody(emailTemplate);
		 
		 String actionLink="";
		//calling the sendEmail method of emailScenerioUtil
		 //emailScenerioUtil.sendNotification(actionLink, loginUser,approverUser, loginOrgLocation, notificationScenario);
		 emailScenerioUtil.sendEmail(emailMessage,orgLocations.getId(),emailRecipientList,new ArrayList<EmailAttachment>());

	}

	@Override
	public void applicantAcceptMailAboutRequirement(Requirement requirement, User initiatorUser, Candidate candidate,
			OrgLocations orgLocations) throws Exception {
		 
		 EmailMessage emailMessage = new EmailMessage();
		 emailMessage.setFirstName(initiatorUser.getFirstName());
		 emailMessage.setLastName(initiatorUser.getLastName());
		 emailMessage.setToEmail(initiatorUser.getEmail());
		 emailMessage.setFromEmail("peopleaxisindia@gmail.com");
		 emailMessage.setCreatedBy(initiatorUser.getUserCode());
		 emailMessage.setFromName(candidate.getFirstName()+" "+candidate.getLastName());
		 
		//setting the class filed values
		 Map<String, String> globalVarsMap = new HashMap<String, String>();

		 if(initiatorUser.getFirstName()!= null && !"".equalsIgnoreCase(initiatorUser.getFirstName())){
			 globalVarsMap.put("initiatorUserName", initiatorUser.getFirstName());
			 }else{
				 globalVarsMap.put("initiatorUserName","--");
			 }
		 
		 if(requirement.getJobTitle()!= null && !"".equalsIgnoreCase(requirement.getJobTitle())){
			 globalVarsMap.put("requirementName", requirement.getJobTitle());
			 }else{
				 globalVarsMap.put("requirementName","--");
			 }
		
		 if(candidate.getFirstName()+""+candidate.getLastName()!= null && !"".equalsIgnoreCase(candidate.getFirstName()+""+candidate.getLastName())){
			 globalVarsMap.put("applicantName", candidate.getFirstName()+""+candidate.getLastName());
			 }else{
				 globalVarsMap.put("applicantName","--");
			 }
		 if((requirement.getMinExp()!= null && !"".equalsIgnoreCase(requirement.getMinExp())) && (requirement.getMaxExp()!= null && !"".equalsIgnoreCase(requirement.getMaxExp()))){
			 globalVarsMap.put("experience", requirement.getMinExp()+"-"+requirement.getMaxExp()); 
		 	 }else if(requirement.getMinExp()!= null && !"".equalsIgnoreCase(requirement.getMinExp())){
			 globalVarsMap.put("experience", requirement.getMinExp());
		 	 }else{
			 globalVarsMap.put("experience","--");
			 }
		 if(requirement.getState()!= null && !"".equalsIgnoreCase(requirement.getState())){
			 globalVarsMap.put("location", requirement.getCountry()+","+requirement.getState());
		 	}else if(requirement.getCity()!= null && !"".equalsIgnoreCase(requirement.getCity())){
			 globalVarsMap.put("location", requirement.getCountry()+","+requirement.getState()+","+requirement.getCity());
		 	}else{
			globalVarsMap.put("location","--");
			}
		 if(requirement.getRate()!= null && !"".equalsIgnoreCase(requirement.getRate())){
			 globalVarsMap.put("requirementRate", requirement.getCurrencyCode()+"-"+requirement.getRate()+"-"+requirement.getRateType());
			 }else{
				 globalVarsMap.put("requirementRate","--");
			 }
		 if(requirement.getSkills()!= null && !"".equalsIgnoreCase(requirement.getSkills())){
			 globalVarsMap.put("skills", requirement.getSkills());
			 }else{
				 globalVarsMap.put("skills","--");
			 }
		 if(requirement.getDescription()!= null && !"".equalsIgnoreCase(requirement.getDescription())){
			 globalVarsMap.put("jobDescription", requirement.getDescription());
			 }else{
				 globalVarsMap.put("jobDescription","--");
			 }
		
		 //setting the Recipient information
		 ArrayList<EmailRecipient> emailRecipientList = new ArrayList<EmailRecipient>();
		 EmailRecipient emailRecipient = new EmailRecipient();
		 emailRecipient.setEmail(emailMessage.getToEmail());
		 emailRecipient.setName(emailMessage.getFirstName()+" "+emailMessage.getLastName());
		 emailRecipientList.add(emailRecipient);
		
		 //getting the current EmailScenario by EmailScenario unique name and entity type
		 EmailScenario  emailScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.APPLICANT_ACCEPT_MAILABOUT_REQUIREMENT_EMAIL_NOTIFICATION,Constants.ENTITY_TYPE_EMAIL);
		 EmailScenario  notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.APPLICANT_ACCEPT_MAILABOUT_REQUIREMENT_EMAIL_NOTIFICATION,Constants.ENTITY_TYPE_NOTIFICATION);

		
		 //covert subject,body and signature
		 String subject = emailScenerioUtil.bulidSubject(emailScenario.getSubject(),globalVarsMap);
		 String body = emailScenerioUtil.buildTempBody(emailScenario.getBody(),globalVarsMap);
		 String signature = emailScenerioUtil.buildSignature(emailScenario.getSignature(),globalVarsMap);
		 String url = "";
		 
		String notificationbody = emailScenerioUtil.buildBody(notificationScenario.getBody(),globalVarsMap);
		notificationScenario.setBody(notificationbody);
		 
		 //construct HTML template using required data
		 String emailTemplate = emailScenerioUtil.AssembleEmailMessage(emailScenario,emailMessage, subject, body, signature, url, new Org());
		 emailMessage.setSubject(subject);
		 emailMessage.setMessageBody(emailTemplate);
		  
		 User toUser = orgLocations.getUser();
		 
		 String actionLink="";
		//calling the sendEmail method of emailScenerioUtil
		 emailScenerioUtil.sendNotification(actionLink, initiatorUser,toUser, orgLocations, notificationScenario);
		 emailScenerioUtil.sendEmail(emailMessage,orgLocations.getId(),emailRecipientList,new ArrayList<EmailAttachment>());

	}

	@Override
	public void applicantRejectMailAboutRequirement(Requirement requirement, User initiatorUser, Candidate candidate,
			OrgLocations orgLocations,String rejectreason) throws Exception {
		 EmailMessage emailMessage = new EmailMessage();
		 emailMessage.setFirstName(initiatorUser.getFirstName());
		 emailMessage.setLastName(initiatorUser.getLastName());
		 emailMessage.setToEmail(initiatorUser.getEmail());
		 emailMessage.setFromEmail("peopleaxisindia@gmail.com");
		 emailMessage.setCreatedBy(initiatorUser.getUserCode());
		 emailMessage.setFromName(candidate.getFirstName()+" "+candidate.getLastName());
		 
		//setting the class filed values
		 Map<String, String> globalVarsMap = new HashMap<String, String>();
		 
		 if(initiatorUser.getFirstName()!= null && !"".equalsIgnoreCase(initiatorUser.getFirstName())){
			 globalVarsMap.put("initiatorUserName", initiatorUser.getFirstName());
			 }else{
				 globalVarsMap.put("initiatorUserName","--");
			 }
		 
		 if(requirement.getJobTitle()!= null && !"".equalsIgnoreCase(requirement.getJobTitle())){
			 globalVarsMap.put("requirementName", requirement.getJobTitle());
			 }else{
				 globalVarsMap.put("requirementName","--");
			 }
		
		 if(candidate.getFirstName()+""+candidate.getLastName()!= null && !"".equalsIgnoreCase(candidate.getFirstName()+""+candidate.getLastName())){
			 globalVarsMap.put("applicantName", candidate.getFirstName()+""+candidate.getLastName());
			 }else{
				 globalVarsMap.put("applicantName","--");
			 }
		 if((requirement.getMinExp()!= null && !"".equalsIgnoreCase(requirement.getMinExp())) && (requirement.getMaxExp()!= null && !"".equalsIgnoreCase(requirement.getMaxExp()))){
			 globalVarsMap.put("experience", requirement.getMinExp()+"-"+requirement.getMaxExp()); 
		 	 }else if(requirement.getMinExp()!= null && !"".equalsIgnoreCase(requirement.getMinExp())){
			 globalVarsMap.put("experience", requirement.getMinExp());
		 	 }else{
			 globalVarsMap.put("experience","--");
			 }
		 if(requirement.getState()!= null && !"".equalsIgnoreCase(requirement.getState())){
			 globalVarsMap.put("location", requirement.getCountry()+","+requirement.getState());
		 	}else if(requirement.getCity()!= null && !"".equalsIgnoreCase(requirement.getCity())){
			 globalVarsMap.put("location", requirement.getCountry()+","+requirement.getState()+","+requirement.getCity());
		 	}else{
			globalVarsMap.put("location","--");
			}
		 if(requirement.getRate()!= null && !"".equalsIgnoreCase(requirement.getRate())){
			 globalVarsMap.put("requirementRate", requirement.getCurrencyCode()+"-"+requirement.getRate()+"-"+requirement.getRateType());
			 }else{
				 globalVarsMap.put("requirementRate","--");
			 }
		 if(requirement.getSkills()!= null && !"".equalsIgnoreCase(requirement.getSkills())){
			 globalVarsMap.put("skills", requirement.getSkills());
			 }else{
				 globalVarsMap.put("skills","--");
			 }
		 if(requirement.getDescription()!= null && !"".equalsIgnoreCase(requirement.getDescription())){
			 globalVarsMap.put("jobDescription", requirement.getDescription());
			 }else{
				 globalVarsMap.put("jobDescription","--");
			 }
		 if(rejectreason != null && !"".equalsIgnoreCase(rejectreason)){
			 globalVarsMap.put("rejectReason",rejectreason);
			 }else{
				 globalVarsMap.put("rejectReason","--");
			 }
		 //setting the Recipient information
		 ArrayList<EmailRecipient> emailRecipientList = new ArrayList<EmailRecipient>();
		 EmailRecipient emailRecipient = new EmailRecipient();
		 emailRecipient.setEmail(emailMessage.getToEmail());
		 emailRecipient.setName(emailMessage.getFirstName()+" "+emailMessage.getLastName());
		 emailRecipientList.add(emailRecipient);
		
		 //getting the current EmailScenario by EmailScenario unique name and entity type
		 EmailScenario  emailScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.APPLICANT_REJECT_MAILABOUT_REQUIREMENT_EMAIL_NOTIFICATION,Constants.ENTITY_TYPE_EMAIL);
		 EmailScenario  notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.APPLICANT_REJECT_MAILABOUT_REQUIREMENT_EMAIL_NOTIFICATION,Constants.ENTITY_TYPE_NOTIFICATION);

		
		 //covert subject,body and signature
		 String subject = emailScenerioUtil.bulidSubject(emailScenario.getSubject(),globalVarsMap);
		 String body = emailScenerioUtil.buildTempBody(emailScenario.getBody(),globalVarsMap);
		 String signature = emailScenerioUtil.buildSignature(emailScenario.getSignature(),globalVarsMap);
		 String url = "";
		 
		String notificationbody = emailScenerioUtil.buildBody(notificationScenario.getBody(),globalVarsMap);
		notificationScenario.setBody(notificationbody);
		 
		 //construct HTML template using required data
		 String emailTemplate = emailScenerioUtil.AssembleEmailMessage(emailScenario,emailMessage, subject, body, signature, url, new Org());
		 emailMessage.setSubject(subject);
		 emailMessage.setMessageBody(emailTemplate);
		  
		 User toUser = orgLocations.getUser();
		 
		 String actionLink="";
		//calling the sendEmail method of emailScenerioUtil
		 emailScenerioUtil.sendNotification(actionLink, initiatorUser,toUser, orgLocations, notificationScenario);
		 emailScenerioUtil.sendEmail(emailMessage,orgLocations.getId(),emailRecipientList,new ArrayList<EmailAttachment>());
	}
	
	
	@Override
	public void applicantNegotiateEmail(Requirement requirement, User initiatorUser, Candidate candidate,
			OrgLocations orgLocations,RequirementRateSubmissionNegotiation negotiation) throws Exception {
		 EmailMessage emailMessage = new EmailMessage();
		 emailMessage.setFirstName(initiatorUser.getFirstName());
		 emailMessage.setLastName(initiatorUser.getLastName());
		 emailMessage.setToEmail(initiatorUser.getEmail());
		 emailMessage.setFromEmail("peopleaxisindia@gmail.com");
		 emailMessage.setCreatedBy(initiatorUser.getUserCode());
		 emailMessage.setFromName(candidate.getFirstName()+" "+candidate.getLastName());
		 
		 
		 
		//setting the class filed values
		 Map<String, String> globalVarsMap = new HashMap<String, String>();
		 
		 if(initiatorUser.getFirstName()!= null && !"".equalsIgnoreCase(initiatorUser.getFirstName())){
			 globalVarsMap.put("initiatorUserName", initiatorUser.getFirstName());
			 }else{
				 globalVarsMap.put("initiatorUserName","--");
			 }
		 
		 if(requirement.getJobTitle()!= null && !"".equalsIgnoreCase(requirement.getJobTitle())){
			 globalVarsMap.put("requirementName", requirement.getJobTitle());
			 }else{
				 globalVarsMap.put("requirementName","--");
			 }
		
		 if(candidate.getFirstName()+""+candidate.getLastName()!= null && !"".equalsIgnoreCase(candidate.getFirstName()+""+candidate.getLastName())){
			 globalVarsMap.put("applicantName", candidate.getFirstName()+""+candidate.getLastName());
			 }else{
				 globalVarsMap.put("applicantName","--");
			 }
		 if(requirement.getRate()!= null && !"".equalsIgnoreCase(requirement.getRate())){
			 globalVarsMap.put("requirementRate", requirement.getCurrencyCode()+"-"+requirement.getRate()+"-"+requirement.getRateType());
			 }else{
				 globalVarsMap.put("requirementRate","--");
			 }
		 if(negotiation.getCurrentRate() != null && !"".equalsIgnoreCase(negotiation.getCurrentRate())){
			 globalVarsMap.put("applicantRate",negotiation.getCurrentRate()+" "+negotiation.getCurrentRateType());
			 }else{
				 globalVarsMap.put("applicantRate","--");
			 }
		 //setting the Recipient information
		 ArrayList<EmailRecipient> emailRecipientList = new ArrayList<EmailRecipient>();
		 EmailRecipient emailRecipient = new EmailRecipient();
		 emailRecipient.setEmail(emailMessage.getToEmail());
		 emailRecipient.setName(emailMessage.getFirstName()+" "+emailMessage.getLastName());
		 emailRecipientList.add(emailRecipient);
		
		 //getting the current EmailScenario by EmailScenario unique name and entity type
		 EmailScenario  emailScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.APPLICANT_NEGOTIATE_REQUIREMENT,Constants.ENTITY_TYPE_EMAIL);
		 EmailScenario  notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.APPLICANT_NEGOTIATE_REQUIREMENT,Constants.ENTITY_TYPE_NOTIFICATION);

		
		 //covert subject,body and signature
		 String subject = emailScenerioUtil.bulidSubject(emailScenario.getSubject(),globalVarsMap);
		 String body = emailScenerioUtil.buildTempBody(emailScenario.getBody(),globalVarsMap);
		 String signature = emailScenerioUtil.buildSignature(emailScenario.getSignature(),globalVarsMap);
		 String url = "";
		 
		String notificationbody = emailScenerioUtil.buildBody(notificationScenario.getBody(),globalVarsMap);
		notificationScenario.setBody(notificationbody);
		 
		 //construct HTML template using required data
		 String emailTemplate = emailScenerioUtil.AssembleEmailMessage(emailScenario,emailMessage, subject, body, signature, url, new Org());
		 emailMessage.setSubject(subject);
		 emailMessage.setMessageBody(emailTemplate);
		  
		 User toUser = orgLocations.getUser();
		 
		 String actionLink="";
		//calling the sendEmail method of emailScenerioUtil
		 emailScenerioUtil.sendNotification(actionLink, initiatorUser,toUser, orgLocations, notificationScenario);
		 emailScenerioUtil.sendEmail(emailMessage,orgLocations.getId(),emailRecipientList,new ArrayList<EmailAttachment>());
	}
	
	@Override
	public RequirementSubmission acceptRejectCandidate(JSONObject jsonObj, User user,
			RequirementSubmission requirementSubmission, String decision, MultipartFile multiFile) throws Exception {
		if(requirementSubmission != null){
			if(jsonObj.has("comment")){
				requirementSubmission.setSubmissionRateComment(jsonObj.getString("comment"));
			}
			if(StringUtils.equalsIgnoreCase(decision, "accept")){
				requirementSubmission.setSubmissionRateStatus(SubmissionRateStatus.ACCEPTED);
			}else if(StringUtils.equalsIgnoreCase(decision, "reject")){
				requirementSubmission.setSubmissionRateStatus(SubmissionRateStatus.REJECTED);
			}
			requirementSubmission.setUpdatedBy(user.getUserCode());
			requirementSubmission.setUpdatedDate(commonUtil.currentDate());
			if(multiFile != null){
				requirementSubmission.setSubmissionRateDocFile(multiFile.getBytes());
				requirementSubmission.setSubmissionRateFileContentType(multiFile.getOriginalFilename());
				requirementSubmission.setSubmissionRateFileName(multiFile.getContentType());
			 }
		}
		requirementSubmissionDao.storeRequirementSubmission(requirementSubmission);
		
		return requirementSubmission;
	}

	@Override
	public RequirementRateSubmissionNegotiation getRequirementRateBySubmissionById(Long submissionId) {
		return requirementSubmissionDao.getRequirementRateBySubmissionById(submissionId);
	}
}
