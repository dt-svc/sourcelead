package com.bis.sourcelead.service.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.internet.MimeBodyPart;
import javax.mail.util.ByteArrayDataSource;

import org.apache.commons.codec.binary.Base64;
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

import com.beust.jcommander.internal.Lists;
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
import com.bis.sourcelead.entity.RequirementSubmissionHistory;
import com.bis.sourcelead.entity.RequirementSubmissionTemplateFields;
import com.bis.sourcelead.entity.SubmissionXls;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.enums.ApplicantSubmissionStatus;
import com.bis.sourcelead.enums.SubmissionRateStatus;
import com.bis.sourcelead.service.CandidateService;
import com.bis.sourcelead.service.EmailScenarioService;
import com.bis.sourcelead.service.RequirementRateSubmissionNegotiationService;
import com.bis.sourcelead.service.RequirementSubmissionHistoryService;
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
	RequirementSubmissionHistoryService requirementSubmissionHistoryService;
	
	@Autowired
	RequirementRateSubmissionNegotiationService requirementRateSubmissionNegotiationService;
	
	@Autowired
	private CommonUtil commonUtil;
	
	@Autowired
	CandidateService candidateService;
	
	@Override
	public RequirementSubmission getRequirementSubmissionById(Long submissionId) throws Exception {
		return requirementSubmissionDao.getRequirementSubmissionById(submissionId);
	}
	
	@Override
	public List<RequirementSubmission> getAllShortListCandidatesByOrgLocationsIdAndRequirementId(Long orgLocationsId,Long requirementId, List<Long> existingApplicantsIds, String isClientInterview) throws Exception {
		return requirementSubmissionDao.getAllShortListCandidatesByOrgLocationsIdAndRequirementId(orgLocationsId,requirementId,existingApplicantsIds,isClientInterview);
	}
	
	@Override
	public List<RequirementSubmission> getAllSubmittedAndShortListAndInterviewededCandidates(Long orgLocationsId,Long requirementId, List<Long> existingApplicantsIds, String isClientOffer) throws Exception {
		return requirementSubmissionDao.getAllSubmittedAndShortListAndInterviewededCandidates(orgLocationsId,requirementId,existingApplicantsIds,isClientOffer);
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
	public RequirementSubmission saveRequirementSubmission(JSONObject jsonObj,User user,OrgLocations orgLocations,String jsonStringvalues,MultipartFile multiFile) throws Exception {
		
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
		 }else if(candidate == null && jsonObj.has("EmailId") && !isNullOrEmpty(jsonObj.getString("EmailId"))){
			 candidate = candidateDao.findByCandidateEmail(jsonObj.getString("EmailId"),orgLocations.getId());
			 if(candidate != null){
				 requirementSubmission.setCandidate(candidate);
			 }
		 }if(candidate == null){
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
					candidate.setCandidateType("Candidate");
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
		 requirementSubmission.setUpdatedDate(commonUtil.currentDate());
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
		 templateFields.setUpdatedDate(commonUtil.currentDate());
		 requirementSubmissionTemplateFieldsDao.storeRequirementSubmissionTemplateFields(templateFields);
		return requirementSubmission;
		
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
			String submissionIds, String selectedShortlistsRate, Org org) throws Exception {
		
		 String[] submissionId = submissionIds.split(",");
		 String[] clientShortlistsRate = selectedShortlistsRate.split(",");
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
    		 StringBuilder sb = new StringBuilder();
    		 fullName = sb.append(user.getFirstName()).append(" ").append(user.getLastName()).toString();
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
    			 clientSubmission.setOrgLocations(requirement.getClientId().getResponderLocationId());
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
    		 clientSubmission.setUpdatedDate(commonUtil.currentDate());
    		 clientSubmission = requirementSubmissionDao.storeRequirementSubmission(clientSubmission);
    		 
    		 String clientRate = clientShortlistsRate[i];
    		 RequirementRateSubmissionNegotiation rateNegotiation = new RequirementRateSubmissionNegotiation();
    		 rateNegotiation.setRequirementsubmissionId(clientSubmission);
			 rateNegotiation.setExpectedRate(clientRate);
			 rateNegotiation.setExpectedRateType("Yearly");
 			 rateNegotiation.setRateInitiatorType(SubmissionRateStatus.VENDOR);
 			 rateNegotiation.setInitiatorOrgLocationId(loginOrgLocation);
 			 rateNegotiation.setRateReason("");
 			 rateNegotiation.setRateStage(SubmissionRateStatus.NEWRATE);
 			 rateNegotiation.setStatus(Constants.RECORD_ACTIVE);
 			 rateNegotiation.setCreatedDate(commonUtil.currentDate());
 			 rateNegotiation.setCreatedBy(user.getUserCode());
 			 rateNegotiation.setUpdatedDate(commonUtil.currentDate());
 			 rateNegotiation.setUpdatedBy(user.getUserCode());
    		 requirementRateSubmissionNegotiationService.saveRateNegotiation(rateNegotiation);
    		 
    		 
    		 
    		 
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
    		 clientSubmissionFields.setUpdatedDate(commonUtil.currentDate());
    		 requirementSubmissionTemplateFieldsDao.storeRequirementSubmissionTemplateFields(clientSubmissionFields);
    		 RequirementSubmissionHistory requirementSubmissionHistory = new RequirementSubmissionHistory();
    		 requirementSubmissionHistory.setFromStage(submission.getSubmissionStatus().getValue());
    		 submission.setSubmissionStatus(ApplicantSubmissionStatus.SUBMIT_TO_CLIENT);
    		 submission.setUpdatedBy(user.getUserCode());
    		 submission.setUpdatedDate(new Date());
    		 requirementSubmissionDao.storeRequirementSubmission(submission);
    		 
    		 requirementSubmissionHistoryService.updateRequirementHistory(submission, user, requirementSubmissionHistory);
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
    	 
    	 
    	 StringBuilder sb = new StringBuilder();
    	 String fileName = sb.append("Submission").append(SourceLeadRandomCodeHelper.generateSubmissionXlsRandomCode()).append("").append(dateFormat.format(new Date())).append(".xls").toString();
		 
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
		 StringBuilder recepientBuilder = new StringBuilder();
		 emailRecipient.setName(recepientBuilder.append(emailMessage.getFirstName()).append(" ").append(emailMessage.getLastName()).toString());
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
		 emailAttachment.setUUID(UUID);
		 attachmentList.add(emailAttachment);
		 
		 emailScenerioUtil.sendEmail(emailMessage,loginOrgLocation.getId(),emailRecipientList,attachmentList);
		
	}

	@Override
	public Integer getRequirementSubmissionsCountByOrgLocationsIdAndSubmissionStatus(Long orgLocationsId, String submissionStatus, String dateRange,String userType,User user) {
		return requirementSubmissionDao.getRequirementSubmissionsCountByOrgLocationsIdAndSubmissionStatus(orgLocationsId,submissionStatus,dateRange,userType,user);
	}

	//to get candidates for submit to client
	@Override
	public List<RequirementSubmission> getAllShortLitstedCandidatesForClientSubmissionByRequirementId(Long orgLocationsId,
			Long requirementId) throws Exception {
		return requirementSubmissionDao.getAllShortLitstedCandidatesForClientSubmissionByRequirementId(orgLocationsId, requirementId);
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
	public RequirementSubmission validateRequirementCandidateEmail(String email, Long requirementId,
			Long orgLocationsId) throws Exception {
		return requirementSubmissionDao.validateRequirementCandidateEmail(email,requirementId,orgLocationsId);
	}

	@Override
	public RequirementSubmission getSubmissionsBySourceSubmissionId(Long sourceSubmissionId,Long orgLocationsId) {
		return requirementSubmissionDao.getSubmissionsBySourceSubmissionId(sourceSubmissionId,orgLocationsId);
	}

	@Override
	public List<Long> getResumeIdsByRequirementSubmissionId(List<Long> submissionIds) throws Exception {
		return requirementSubmissionDao.getResumeIdsByRequirementSubmissionId(submissionIds);
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
	public Integer getRequirementSubmissionsCountByOrgLocationsIdAndRequirementId(Long orgLocationsId, Long requirementId) {
		return requirementSubmissionDao.getRequirementSubmissionsCountByOrgLocationsIdAndRequirementId(orgLocationsId, requirementId);
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
		 StringBuilder emailBuilder = new StringBuilder();
		 emailMessage.setFromName(emailBuilder.append(loginUser.getFirstName()).append(" ").append(loginUser.getLastName()).toString());
		 
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
		 if(candidate.getFirstName() != null && candidate.getLastName()!= null && !"".equalsIgnoreCase(candidate.getFirstName()) && !"".equalsIgnoreCase(candidate.getLastName())){
			 StringBuilder candidateBuild = new StringBuilder();
			 globalVarsMap.put("applicantName", candidateBuild.append(candidate.getFirstName()).append("").append(candidate.getLastName()).toString());
			 }else{
				 globalVarsMap.put("applicantName",candidate.getFirstName());
			 }
		 if((requirement.getMinExp()!= null && !"".equalsIgnoreCase(requirement.getMinExp())) && (requirement.getMaxExp()!= null && !"".equalsIgnoreCase(requirement.getMaxExp()))){
			 StringBuilder expBuild = new StringBuilder();
			 globalVarsMap.put("experience",expBuild.append(requirement.getMinExp()).append("-").append(requirement.getMaxExp()).toString()); 
		 	 }else if(requirement.getMinExp()!= null && !"".equalsIgnoreCase(requirement.getMinExp())){
			 globalVarsMap.put("experience", requirement.getMinExp());
		 	 }else{
			 globalVarsMap.put("experience","--");
			 }
		 if(requirement.getState()!= null && !"".equalsIgnoreCase(requirement.getState())){
			 StringBuilder locBuildeer = new StringBuilder();
			 globalVarsMap.put("location", locBuildeer.append(requirement.getCountry()).append(",").append(requirement.getState()).toString());
		 	}else if(requirement.getCity()!= null && !"".equalsIgnoreCase(requirement.getCity())){
		 		StringBuilder locBuildeer = new StringBuilder();
			 	globalVarsMap.put("location", locBuildeer.append(requirement.getCountry()).append(",").append(requirement.getState()).append(",").append(requirement.getCity()).toString());
		 	}else{
			globalVarsMap.put("location","--");
			}
		 if(requirement.getRate()!= null && !"".equalsIgnoreCase(requirement.getRate())){
			 StringBuilder rateBuild = new StringBuilder();
			 globalVarsMap.put("requirementRate", rateBuild.append(requirement.getCurrencyCode()).append("-").append(requirement.getRate()).append("-").append(requirement.getRateType()).toString());
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
			 StringBuilder nameBuild = new StringBuilder();
			 globalVarsMap.put("loginUserName", nameBuild.append(loginUser.getFirstName()).append(" ").append(loginUser.getLastName()).toString());
			 }
		 else if(loginUser.getFirstName()!= null && !"".equalsIgnoreCase(loginUser.getFirstName())){
			 globalVarsMap.put("loginUserName", loginUser.getFirstName());
		 	}else{
			 globalVarsMap.put("loginUserName","--");
			 }                       
		
		 StringBuilder acceptBuilder = new StringBuilder();
		 StringBuilder rejectBuilder = new StringBuilder();
		 StringBuilder negotiateBuilder = new StringBuilder();
		 
		 globalVarsMap.put("acceptLink",acceptBuilder.append("<a href='").append(sourceleadUrl).append("/").append("acceptOrRejectOrRateNegotiateApplicantRates?rateStatus=Accepted&submissionId=").append(submissionId).append("' style='text-decoration:none; '><div style=' width:100px; border-radius:30px; background-color:#61D339 ; color:#FFF; font-size:15px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Accept</div></a>").toString());
	        
	     globalVarsMap.put("rejectLink",rejectBuilder.append("<a href='").append(sourceleadUrl).append("/").append("acceptOrRejectOrRateNegotiateApplicantRates?rateStatus=Rejected&submissionId=").append(submissionId).append("' style='text-decoration:none; '><div style=' width:100px; border-radius:30px; background-color:#D33B39; color:#FFF; font-size:15px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Reject</div></a>").toString());

	     globalVarsMap.put("negotiateRateLink",negotiateBuilder.append("<a href='").append(sourceleadUrl).append("/").append("acceptOrRejectOrRateNegotiateApplicantRates?rateStatus=Negotiate&submissionId=").append(submissionId).append("' style='text-decoration:none; '><div style=' width:100px; border-radius:30px; background-color:#D33B39; color:#FFF; font-size:15px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Negotiate Rate</div></a>").toString());


	        
	        
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
		 StringBuilder recepBuild = new StringBuilder();
		 emailRecipient.setName(recepBuild.append(emailMessage.getFirstName()).append(" ").append(emailMessage.getLastName()).toString());
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
		 StringBuilder msgBuild = new StringBuilder();
		 emailMessage.setFromName(msgBuild.append(candidate.getFirstName()).append(" ").append(candidate.getLastName()).toString());
		 
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
		
		 if(candidate.getFirstName() != null && candidate.getLastName()!= null && !"".equalsIgnoreCase(candidate.getFirstName()) && !"".equalsIgnoreCase(candidate.getLastName())){
			 StringBuilder candBuild = new StringBuilder();
			 globalVarsMap.put("applicantName", candBuild.append(candidate.getFirstName()).append("").append(candidate.getLastName()).toString());
			 }else{
				 globalVarsMap.put("applicantName",candidate.getFirstName());
			 }
		 if((requirement.getMinExp()!= null && !"".equalsIgnoreCase(requirement.getMinExp())) && (requirement.getMaxExp()!= null && !"".equalsIgnoreCase(requirement.getMaxExp()))){
			 StringBuilder expBuild = new StringBuilder();
			 globalVarsMap.put("experience", expBuild.append(requirement.getMinExp()).append("-").append(requirement.getMaxExp()).toString()); 
		 	 }else if(requirement.getMinExp()!= null && !"".equalsIgnoreCase(requirement.getMinExp())){
			 globalVarsMap.put("experience", requirement.getMinExp());
		 	 }else{
			 globalVarsMap.put("experience","--");
			 }
		 if(requirement.getState()!= null && !"".equalsIgnoreCase(requirement.getState())){
			 StringBuilder locBuild = new StringBuilder();
			 globalVarsMap.put("location", locBuild.append(requirement.getCountry()).append(",").append(requirement.getState()).toString());
		 	}else if(requirement.getCity()!= null && !"".equalsIgnoreCase(requirement.getCity())){
		 		StringBuilder locBuild = new StringBuilder();
		 		globalVarsMap.put("location", locBuild.append(requirement.getCountry()).append(",").append(requirement.getState()).append(",").append(requirement.getCity()).toString());
		 	}else{
			globalVarsMap.put("location","--");
			}
		 if(requirement.getRate()!= null && !"".equalsIgnoreCase(requirement.getRate())){
			 StringBuilder rateBuild = new StringBuilder();
			 globalVarsMap.put("requirementRate", rateBuild.append(requirement.getCurrencyCode()).append("-").append(requirement.getRate()).append("-").append(requirement.getRateType()).toString());
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
		 StringBuilder receipBuild = new StringBuilder();
		 emailRecipient.setName(receipBuild.append(emailMessage.getFirstName()).append(" ").append(emailMessage.getLastName()).toString());
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
		 StringBuilder emailBuild = new StringBuilder();
		 emailMessage.setFromName(emailBuild.append(candidate.getFirstName()).append(" ").append(candidate.getLastName()).toString());
		 
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
		
		 if(candidate.getFirstName() != null && candidate.getLastName()!= null && !"".equalsIgnoreCase(candidate.getFirstName()) && !"".equalsIgnoreCase(candidate.getLastName())){
			 StringBuilder candiBuild = new StringBuilder();
			 globalVarsMap.put("applicantName", candiBuild.append(candidate.getFirstName()).append("").append(candidate.getLastName()).toString());
			 }else{
				 globalVarsMap.put("applicantName",candidate.getFirstName());
			 }
		 if((requirement.getMinExp()!= null && !"".equalsIgnoreCase(requirement.getMinExp())) && (requirement.getMaxExp()!= null && !"".equalsIgnoreCase(requirement.getMaxExp()))){
			 StringBuilder expBuild = new StringBuilder();
			 globalVarsMap.put("experience", expBuild.append(requirement.getMinExp()).append("-").append(requirement.getMaxExp()).toString()); 
		 	 }else if(requirement.getMinExp()!= null && !"".equalsIgnoreCase(requirement.getMinExp())){
			 globalVarsMap.put("experience", requirement.getMinExp());
		 	 }else{
			 globalVarsMap.put("experience","--");
			 }
		 if(requirement.getState()!= null && !"".equalsIgnoreCase(requirement.getState())){
			 StringBuilder locBuild = new StringBuilder();
			 globalVarsMap.put("location", locBuild.append(requirement.getCountry()).append(",").append(requirement.getState()).toString());
		 	}else if(requirement.getCity()!= null && !"".equalsIgnoreCase(requirement.getCity())){
		 		StringBuilder locBuild = new StringBuilder();
			 globalVarsMap.put("location", locBuild.append(requirement.getCountry()).append(",").append(requirement.getState()).append(",").append(requirement.getCity()).toString());
		 	}else{
			globalVarsMap.put("location","--");
			}
		 if(requirement.getRate()!= null && !"".equalsIgnoreCase(requirement.getRate())){
			 StringBuilder rateBuild = new StringBuilder();
			 globalVarsMap.put("requirementRate", rateBuild.append(requirement.getCurrencyCode()).append("-").append(requirement.getRate()).append("-").append(requirement.getRateType()).toString());
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
		 StringBuilder mailBuild = new StringBuilder();
		 emailRecipient.setName(mailBuild.append(emailMessage.getFirstName()).append(" ").append(emailMessage.getLastName()).toString());
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
			OrgLocations orgLocations,RequirementRateSubmissionNegotiation negotiation,StringBuilder tempHTML) throws Exception {
		 EmailMessage emailMessage = new EmailMessage();
		 emailMessage.setFirstName(initiatorUser.getFirstName());
		 emailMessage.setLastName(initiatorUser.getLastName());
		 emailMessage.setToEmail(initiatorUser.getEmail());
		 emailMessage.setFromEmail("peopleaxisindia@gmail.com");
		 emailMessage.setCreatedBy(initiatorUser.getUserCode());
		 StringBuilder msgBuild = new StringBuilder();
		 emailMessage.setFromName(msgBuild.append(candidate.getFirstName()).append(" ").append(candidate.getLastName()).toString());
		 
		 
		 
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
		
		 if(candidate.getFirstName() != null && candidate.getLastName()!= null && !"".equalsIgnoreCase(candidate.getFirstName()) && !"".equalsIgnoreCase(candidate.getLastName())){
			 StringBuilder sb = new StringBuilder();
			 globalVarsMap.put("applicantName", sb.append(candidate.getFirstName()).append("").append(candidate.getLastName()).toString());
			 }else{
				 globalVarsMap.put("applicantName",candidate.getFirstName());
			 }
		 if(requirement.getRate()!= null && !"".equalsIgnoreCase(requirement.getRate())){
			 StringBuilder sb = new StringBuilder();
			 globalVarsMap.put("requirementRate", sb.append(requirement.getCurrencyCode()).append("-").append(requirement.getRate()).append("-").append(requirement.getRateType()).toString());
			 }else{
				 globalVarsMap.put("requirementRate","--");
			 }
		 if(negotiation.getCurrentRate() != null && !"".equalsIgnoreCase(negotiation.getCurrentRate())){
			 StringBuilder sb = new StringBuilder();
			 globalVarsMap.put("applicantRate",sb.append(negotiation.getCurrentRate()).append(" ").append(negotiation.getCurrentRateType()).toString());
			 }else{
				 globalVarsMap.put("applicantRate","--");
			 }
		 if(tempHTML != null){
			 globalVarsMap.put("negotiationTable",tempHTML.toString());
		 }
		 
		 //setting the Recipient information
		 ArrayList<EmailRecipient> emailRecipientList = new ArrayList<EmailRecipient>();
		 EmailRecipient emailRecipient = new EmailRecipient();
		 emailRecipient.setEmail(emailMessage.getToEmail());
		 StringBuilder receipBuild = new StringBuilder();
		 emailRecipient.setName(receipBuild.append(emailMessage.getFirstName()).append(" ").append(emailMessage.getLastName()).toString());
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
			RequirementSubmission requirementSubmission, String decision, MultipartFile multiFile,Org loginOrg,  OrgLocations loginOrgLocation) throws Exception {
		if(requirementSubmission != null){
			
			if(StringUtils.equalsIgnoreCase(decision, "accept")){
				requirementSubmission.setSubmissionRateStatus(SubmissionRateStatus.ACCEPTED);
				if(jsonObj.has("comment")){
					requirementSubmission.setSubmissionRateComment(jsonObj.getString("comment"));
				}
				if(multiFile != null){
					requirementSubmission.setSubmissionRateDocFile(multiFile.getBytes());
					requirementSubmission.setSubmissionRateFileContentType(multiFile.getOriginalFilename());
					requirementSubmission.setSubmissionRateFileName(multiFile.getContentType());
				}
				sendEmailtoApplicant(loginOrg, user,requirementSubmission,loginOrgLocation);
			}
			else if(StringUtils.equalsIgnoreCase(decision, "reject")){
				requirementSubmission.setSubmissionRateStatus(SubmissionRateStatus.REJECTED);
				if(jsonObj.has("comment")){
					requirementSubmission.setSubmissionRateComment(jsonObj.getString("comment"));
				}
			}
			else if(StringUtils.equalsIgnoreCase(decision, "request")){
				requirementSubmission.setSubmissionRateStatus(SubmissionRateStatus.REQUEST);
				if(multiFile != null){
					requirementSubmission.setSubmissionRequestDocFile(multiFile.getBytes());
					requirementSubmission.setSubmissionRequestFileContentType(multiFile.getContentType());
					requirementSubmission.setSubmissionRequestFileName(multiFile.getOriginalFilename());
				}
				if(jsonObj.has("comment")){
					requirementSubmission.setSubmissionRequestComment(jsonObj.getString("comment"));
				}
				requirementSubmission.setSubmissionRequestUser(user.getUserCode());
				
				//send mail to requirement manager.
				sendEmailtoRequirementManager(loginOrg, user,requirementSubmission,loginOrgLocation);
				
				
			}
			requirementSubmission.setUpdatedBy(user.getUserCode());
			requirementSubmission.setUpdatedDate(commonUtil.currentDate());
			
		}
		requirementSubmission.setUpdatedDate(commonUtil.currentDate());
		requirementSubmissionDao.storeRequirementSubmission(requirementSubmission);
		
		return requirementSubmission;
	}
	

	@Override
	public RequirementRateSubmissionNegotiation getRequirementRateBySubmissionById(Long submissionId) {
		return requirementSubmissionDao.getRequirementRateBySubmissionById(submissionId);
	}

	@Override
	public List<RequirementSubmission> getSubmissionsByRequirementIdandStatus(Long requirementId, User user,
			OrgLocations loginOrgLocation, String submissionType) throws Exception {
		return requirementSubmissionDao.getSubmissionsByRequirementIdandStatus(requirementId,user,loginOrgLocation,submissionType);
	}

	@Override
	public void storeClientSubmissionWithEmailPreview(Long requirementId, User user, OrgLocations loginOrgLocation,String submissionIds, Org org, JSONObject jsonData,Map files,String selectedShortlistsRate) throws Exception {
		
		 String requirementName = null;
		 String fullName = null;
		 String[] submissionId = submissionIds.split(",");
		 String[] clientShortlistsRate = selectedShortlistsRate.split(",");
   	     Requirement requirement = requirementDao.getRequirementByRequirementId(requirementId);
		 requirement.setId(requirementId);
		 if(requirement.getParentRequirement() != null){
			 requirementName = requirement.getParentRequirement().getJobTitle();
		 }else{
			 requirementName = requirement.getJobTitle();
		 }
		 
   	 if(!isNullOrEmpty(user.getLastName())){
   		StringBuilder sb = new StringBuilder();
   		 fullName = sb.append(user.getFirstName()).append(" ").append(user.getLastName()).toString();
   	 }else{
   		 fullName = user.getFirstName();
   	 }
   	 Workbook createExcelworkbook =  new XSSFWorkbook();
   	 Row writeRow = null;
   	DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy"); 
   	Sheet createExcelsheet = createSubmissionTracker(org,fullName,submissionId,requirementName,createExcelworkbook,writeRow,dateFormat);
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
   		 clientSubmission.setUpdatedDate(commonUtil.currentDate());
   		 clientSubmission = requirementSubmissionDao.storeRequirementSubmission(clientSubmission);
   		 
   		 
   		 String clientRate = clientShortlistsRate[i];
		 RequirementRateSubmissionNegotiation rateNegotiation = new RequirementRateSubmissionNegotiation();
		 rateNegotiation.setRequirementsubmissionId(clientSubmission);
		 rateNegotiation.setExpectedRate(clientRate);
		 rateNegotiation.setExpectedRateType("Yearly");
			 rateNegotiation.setRateInitiatorType(SubmissionRateStatus.VENDOR);
			 rateNegotiation.setInitiatorOrgLocationId(loginOrgLocation);
			 rateNegotiation.setRateReason("");
			 rateNegotiation.setRateStage(SubmissionRateStatus.NEWRATE);
			 rateNegotiation.setStatus(Constants.RECORD_ACTIVE);
			 rateNegotiation.setCreatedDate(commonUtil.currentDate());
			 rateNegotiation.setCreatedBy(user.getUserCode());
			 rateNegotiation.setUpdatedDate(commonUtil.currentDate());
			 rateNegotiation.setUpdatedBy(user.getUserCode());
		 requirementRateSubmissionNegotiationService.saveRateNegotiation(rateNegotiation);
   		 
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
   		 clientSubmissionFields.setUpdatedDate(commonUtil.currentDate());
   		 requirementSubmissionTemplateFieldsDao.storeRequirementSubmissionTemplateFields(clientSubmissionFields);
   		 RequirementSubmissionHistory requirementSubmissionHistory = new RequirementSubmissionHistory();
   		 requirementSubmissionHistory.setFromStage(submission.getSubmissionStatus().getValue());
   		 submission.setSubmissionStatus(ApplicantSubmissionStatus.SUBMIT_TO_CLIENT);
   		 submission.setUpdatedBy(user.getUserCode());
   		 submission.setUpdatedDate(commonUtil.currentDate());
   		 requirementSubmissionDao.storeRequirementSubmission(submission);
   		 
   		 requirementSubmissionHistoryService.updateRequirementHistory(submission, user, requirementSubmissionHistory);
   		 
   		 String data = clientSubmission.getSubmissionTemplateJsonStringValue();
   		 try {
   		     // create the json array from String rules
   		     JSONArray jsonRules = new JSONArray(data);
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
   	 
   	 
   	SubmissionXls submissionXls =  saveSubmissionTracker(dateFormat,createExcelworkbook,requirement,user);
    
   	sendMailTemplateWithAttachment(jsonData,user,files,loginOrgLocation,submissionXls,submissionId);
	
		
	}
/**
 * prepares email template with attachments
 * @param jsonObj
 * @param user
 * @param files
 * @param loginOrgLocation
 * @param submissionXls
 * @param submissionId 
 * @throws Exception
 */
	private void sendMailTemplateWithAttachment(JSONObject jsonObj, User user, Map files, OrgLocations loginOrgLocation,
			SubmissionXls submissionXls, String[] submissionId) throws Exception {
		List<EmailAttachment> attachmentList = new ArrayList<>();
		EmailMessage emailMessage = new EmailMessage();
		
		//to check send resumes condition
		if (jsonObj.has("sendResumes")) {
			emailMessage.setSendResumes(Constants.YES);
		}else{
			emailMessage.setSendResumes(Constants.NO);
		}
		
		
		emailMessage.setCreatedBy(user.getUserCode());
		emailMessage.setFromEmail(user.getEmail());
		StringBuilder emailBuild = new StringBuilder();
		emailMessage.setFromName(emailBuild.append(user.getFirstName()).append("").append(user.getLastName()).toString());
		ArrayList<EmailRecipient> emailRecipientList = new ArrayList<EmailRecipient>();

		if (jsonObj.has("toMailId")) {

			EmailRecipient toEmailRecipient = new EmailRecipient();
			String toamil = jsonObj.getString("toMailId");
			toEmailRecipient.setEmail(toamil);

			if (jsonObj.has("othersMail") && !"".equalsIgnoreCase(jsonObj.getString("othersMail"))) {
				String othermail = jsonObj.getString("othersMail");
				String[] parts = othermail.split(",");
				List<String> emailList = new ArrayList<String>();
				emailList.addAll(Arrays.asList(parts));
				for (String eml : emailList) {
					EmailRecipient emailRecipient = new EmailRecipient();
					emailRecipient.setEmail(eml);
					emailRecipientList.add(emailRecipient);
				}
			}
			emailRecipientList.add(toEmailRecipient);
			String subject = "";
			String body = "";
			//String signature = user.getFirstName();
			String url = "";

			// getting the current EmailScenario by EmailScenario unique name
			// and entity type
			EmailScenario emailScenario = null;
			emailScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.SUBMIT_TO_CLIENT,
					Constants.ENTITY_TYPE_EMAIL);

			// covert subject,body and signature

			if (jsonObj.has("mailSubject")) {
				subject = jsonObj.getString("mailSubject");
			}
			if (jsonObj.has("mailBody")) {
				body = jsonObj.getString("mailBody");
			}
			/*if (jsonObj.has("submitToClientSignature")) {
				signature = jsonObj.getString("submitToClientSignature");
			}*/

			// construct HTML template using required data
			String emailTemplate = emailScenerioUtil.AssembleEmailMessage(emailScenario, emailMessage, subject, body,
					null, url, new Org());
			emailMessage.setSubject(subject);
			emailMessage.setMessageBody(emailTemplate);

			String attcahmentMaxRowNumber = jsonObj.getString("attcahmentMaxRowNumber");
			int attcahmentMaxRowNumberCount = Integer.parseInt(attcahmentMaxRowNumber);
			if (submissionXls != null) {
				if (jsonObj.has("sendTracker")) {
					EmailAttachment emailAttachment = new EmailAttachment();
					emailAttachment.setName(submissionXls.getFileName());
					emailAttachment.setDocFile(submissionXls.getDocFile());
					emailAttachment.setType(submissionXls.getFileContentType());
					attachmentList.add(emailAttachment);
				}
			}
			for (int a = 0; a < attcahmentMaxRowNumberCount; a++) {
				MultipartFile multiFile = (MultipartFile) files.get(a + "docFile");
				if (multiFile != null && !multiFile.isEmpty()) {
					EmailAttachment emailAttachment = new EmailAttachment();
					emailAttachment.setName(multiFile.getOriginalFilename());
					emailAttachment.setDocFile(multiFile.getBytes());
					emailAttachment.setType(multiFile.getContentType());
					attachmentList.add(emailAttachment);

				}
			}
			
			
			emailScenerioUtil.sendWithAttachmentEmail(emailMessage, loginOrgLocation.getId(), emailRecipientList,attachmentList,submissionId);

		}

	}
	
	public static void addToZipFile(byte[] by, ZipOutputStream zos , String fileName) throws FileNotFoundException, IOException {
	    byte[] content = by;
	    if(content != null){
	    	 int size = content.length;
       
		        ByteArrayInputStream ins = null;
		        ins = new ByteArrayInputStream(content);
        
		        ZipEntry zipEntry = new ZipEntry(fileName); 
				zos.putNextEntry(zipEntry);
		
				final int BUFFER = 2048;
				byte[] bytes = new byte[1024];
				byte data[] = new byte[BUFFER];
		
				int length = 0;
				while ((length = ins.read(bytes)) >= 0) {
					zos.write(bytes, 0, length);
				}
		
        }
		zos.closeEntry();
	}
	
	
	private SubmissionXls saveSubmissionTracker(DateFormat dateFormat, Workbook createExcelworkbook, Requirement requirement, User user) throws Exception {
		StringBuilder sb = new StringBuilder();
		String fileName = sb.append("Submission").append(SourceLeadRandomCodeHelper.generateSubmissionXlsRandomCode()).append("").append(dateFormat.format(new Date())).append(".xls").toString();
		 String UUID = null;
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
		 return submissionXlsService.storeSubmissionXls(submissionXls);
		
	}
/**
 * Creates schedule Tracker Header for Client Submission
 * @param org
 * @param fullName
 * @param submissionIds
 * @param requirementName
 * @param createExcelworkbook
 * @param writeRow
 * @param dateFormat
 * @return
 * @throws NumberFormatException
 * @throws Exception
 */
	private Sheet createSubmissionTracker(Org org, String fullName, String[] submissionIds, String requirementName, Workbook createExcelworkbook, Row writeRow, DateFormat dateFormat) throws NumberFormatException, Exception {
		StringBuilder sb = new StringBuilder(); 
		Sheet createExcelsheet = createExcelworkbook.createSheet(sb.append("submissions ").append(dateFormat.format(new Date())).toString());
		 
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
		 cell8.setCellValue(String.valueOf(submissionIds.length));
		 cell8.setCellStyle(style);
		   
		 return createExcelsheet = setExcelHeaderTracker(submissionIds,createExcelsheet,style);
		
	}

	private Sheet setExcelHeaderTracker(String[] submissionId, Sheet createExcelsheet, CellStyle style) throws NumberFormatException, Exception {
		 Row excelHeader = createExcelsheet.createRow(7);
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


	
	@Override
	public RequirementSubmission getClientSubmissionsBySubmissionIdAndOrgLocationId(Long submissionId, Long orgLocationId){
		return requirementSubmissionDao.getClientSubmissionsBySubmissionIdAndOrgLocationId(submissionId, orgLocationId);
	}
	

	@Override
	public void userNegotiateEmail(Requirement requirement, User user, Candidate candidate, OrgLocations orgLocations,
			RequirementRateSubmissionNegotiation neogtation, StringBuilder tempHTML) throws Exception {
		EmailMessage emailMessage = new EmailMessage();
		 emailMessage.setFirstName(candidate.getFirstName());
		 emailMessage.setLastName(candidate.getLastName());
		 emailMessage.setToEmail(candidate.getEmail());
		 emailMessage.setFromEmail("peopleaxisindia@gmail.com");
		 emailMessage.setCreatedBy(user.getUserCode());
		 StringBuilder msgBuild = new StringBuilder();
		 emailMessage.setFromName(msgBuild.append(user.getFirstName()).append(" ").append(user.getLastName()).toString());
		 
		 
		 
		//setting the class filed values
		 Map<String, String> globalVarsMap = new HashMap<String, String>();
		 
		 if(user.getFirstName()!= null && !"".equalsIgnoreCase(user.getFirstName()) && user.getLastName()!= null && !"".equalsIgnoreCase(user.getLastName())){
			 StringBuilder sb = new StringBuilder();
			 globalVarsMap.put("userName", sb.append(user.getFirstName()).append(" ").append(user.getLastName()).toString());
			 }else{
				 globalVarsMap.put("userName",user.getFirstName());
			 }
		 
		 if(requirement.getJobTitle()!= null && !"".equalsIgnoreCase(requirement.getJobTitle())){
			 globalVarsMap.put("requirementName", requirement.getJobTitle());
			 }else{
				 globalVarsMap.put("requirementName","--");
			 }
		
		 if(candidate.getFirstName()!= null && candidate.getLastName()!= null && !"".equalsIgnoreCase(candidate.getFirstName()) && !"".equalsIgnoreCase(candidate.getLastName())){
			 StringBuilder candiBuild = new StringBuilder();
			 globalVarsMap.put("applicantName", candiBuild.append(candidate.getFirstName()).append("").append(candidate.getLastName()).toString());
			 }else{
				 globalVarsMap.put("applicantName",candidate.getFirstName());
			 }
		 if(orgLocations.getOrg().getOrgName()!= null && !"".equalsIgnoreCase(orgLocations.getOrg().getOrgName())){
			 globalVarsMap.put("organizationName", orgLocations.getOrg().getOrgName());
			 }else{
				 globalVarsMap.put("organizationName","--");
			 }
		 if(tempHTML != null){
			 globalVarsMap.put("negotiationTable",tempHTML.toString());
		 }
		 
		 //setting the Recipient information
		 ArrayList<EmailRecipient> emailRecipientList = new ArrayList<EmailRecipient>();
		 EmailRecipient emailRecipient = new EmailRecipient();
		 emailRecipient.setEmail(emailMessage.getToEmail());
		 StringBuilder recipBuild = new StringBuilder();
		 emailRecipient.setName(recipBuild.append(emailMessage.getFirstName()).append(" ").append(emailMessage.getLastName()).toString());
		 emailRecipientList.add(emailRecipient);
		
		 //getting the current EmailScenario by EmailScenario unique name and entity type
		 EmailScenario  emailScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.USER_RATE_NEGOTIATION,Constants.ENTITY_TYPE_EMAIL);
//		 EmailScenario  notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.APPLICANT_NEGOTIATE_REQUIREMENT,Constants.ENTITY_TYPE_NOTIFICATION);

		
		 //covert subject,body and signature
		 String subject = emailScenerioUtil.bulidSubject(emailScenario.getSubject(),globalVarsMap);
		 String body = emailScenerioUtil.buildTempBody(emailScenario.getBody(),globalVarsMap);
		 String signature = emailScenerioUtil.buildSignature(emailScenario.getSignature(),globalVarsMap);
		 String url = "";
		 
		/*String notificationbody = emailScenerioUtil.buildBody(notificationScenario.getBody(),globalVarsMap);
		notificationScenario.setBody(notificationbody);*/
		 
		 //construct HTML template using required data
		 String emailTemplate = emailScenerioUtil.AssembleEmailMessage(emailScenario,emailMessage, subject, body, signature, url, new Org());
		 emailMessage.setSubject(subject);
		 emailMessage.setMessageBody(emailTemplate);
		  
		
		  // Unused Code
			 /* User toUser = orgLocations.getUser();
			 String actionLink="";*/
		 
		//calling the sendEmail method of emailScenerioUtil
		 emailScenerioUtil.sendEmail(emailMessage,orgLocations.getId(),emailRecipientList,new ArrayList<EmailAttachment>());		
	}

	@Override
	public void sendEmailtoClient(Org loginOrg, User users, User pastUser, Requirement requirement, Candidate candidate,
			StringBuilder tempHTML,OrgLocations orgLocations) throws Exception {
		EmailMessage emailMessage = new EmailMessage();
		 emailMessage.setFirstName(pastUser.getFirstName());
		 emailMessage.setLastName(pastUser.getLastName());
		 emailMessage.setToEmail(pastUser.getEmail());
		 emailMessage.setFromEmail("peopleaxisindia@gmail.com");
		 emailMessage.setCreatedBy(users.getUserCode());
		 StringBuilder msgBuild = new StringBuilder();
		 emailMessage.setFromName(msgBuild.append(users.getFirstName()).append(" ").append(users.getLastName()).toString());
		 
		 
		 
		//setting the class filed values
		 Map<String, String> globalVarsMap = new HashMap<String, String>();
		 
		 if(pastUser.getFirstName()!= null && !"".equalsIgnoreCase(pastUser.getFirstName()) && pastUser.getLastName()!= null && !"".equalsIgnoreCase(pastUser.getLastName())){
			 StringBuilder pastUserBuild = new StringBuilder();
			 globalVarsMap.put("vendorContact", pastUserBuild.append(pastUser.getFirstName()).append(" ").append(pastUser.getLastName()).toString());
			 }else{
				 globalVarsMap.put("vendorContact",pastUser.getFirstName());
			 }
		 
		 if(users.getFirstName()!= null && !"".equalsIgnoreCase(users.getFirstName()) && users.getLastName()!= null && !"".equalsIgnoreCase(users.getLastName())){
			 StringBuilder userBuild = new StringBuilder();
			 globalVarsMap.put("loginUser", userBuild.append(users.getFirstName()).append(" ").append(users.getLastName()).toString());
			 }else{
				 globalVarsMap.put("loginUser",users.getFirstName());
			 }
		 
		 if(requirement.getJobTitle()!= null && !"".equalsIgnoreCase(requirement.getJobTitle())){
			 globalVarsMap.put("requirementName", requirement.getJobTitle());
			 }else{
				 globalVarsMap.put("requirementName","--");
			 }
		
		 if(candidate.getFirstName() != null && candidate.getLastName()!= null && !"".equalsIgnoreCase(candidate.getFirstName()) && !"".equalsIgnoreCase(candidate.getLastName())){
			 StringBuilder candiBuild = new StringBuilder();
			 globalVarsMap.put("candidateName", candiBuild.append(candidate.getFirstName()).append("").append(candidate.getLastName()).toString());
			 }else{
				 globalVarsMap.put("candidateName",candidate.getFirstName());
			 }
		 if(loginOrg.getOrgName() != null && !"".equalsIgnoreCase(loginOrg.getOrgName())){
			 globalVarsMap.put("loginOrgName", loginOrg.getOrgName());
			 }else{
				 globalVarsMap.put("loginOrgName","--");
			 }
		 if(tempHTML != null){
			 globalVarsMap.put("negotiationTable",tempHTML.toString());
		 }
		 
		 //setting the Recipient information
		 ArrayList<EmailRecipient> emailRecipientList = new ArrayList<EmailRecipient>();
		 EmailRecipient emailRecipient = new EmailRecipient();
		 emailRecipient.setEmail(emailMessage.getToEmail());
		 StringBuilder recipBuild = new StringBuilder();
 		 emailRecipient.setName(recipBuild.append(emailMessage.getFirstName()).append(" ").append(emailMessage.getLastName()).toString());
		 emailRecipientList.add(emailRecipient);
		
		 //getting the current EmailScenario by EmailScenario unique name and entity type
		 EmailScenario  emailScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.VENDOR_RATE_NEGOTIATION,Constants.ENTITY_TYPE_EMAIL);
//		 EmailScenario  notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.APPLICANT_NEGOTIATE_REQUIREMENT,Constants.ENTITY_TYPE_NOTIFICATION);

		
		 //covert subject,body and signature
		 String subject = emailScenerioUtil.bulidSubject(emailScenario.getSubject(),globalVarsMap);
		 String body = emailScenerioUtil.buildTempBody(emailScenario.getBody(),globalVarsMap);
		 String signature = emailScenerioUtil.buildSignature(emailScenario.getSignature(),globalVarsMap);
		 String url = "";
		 
		/*String notificationbody = emailScenerioUtil.buildBody(notificationScenario.getBody(),globalVarsMap);
		notificationScenario.setBody(notificationbody);*/
		 
		 //construct HTML template using required data
		 String emailTemplate = emailScenerioUtil.AssembleEmailMessage(emailScenario,emailMessage, subject, body, signature, url, new Org());
		 emailMessage.setSubject(subject);
		 emailMessage.setMessageBody(emailTemplate);
		  
		//calling the sendEmail method of emailScenerioUtil
		 emailScenerioUtil.sendEmail(emailMessage,orgLocations.getId(),emailRecipientList,new ArrayList<EmailAttachment>());
	}

	@Override
	public List<RequirementSubmission> getRequirementHistoryListByCandidateIdAndOrgLocationID(Long candidateId, Long orgLocationId) throws Exception {
		return requirementSubmissionDao.getRequirementHistoryListByCandidateIdAndOrgLocationID(candidateId, orgLocationId);
	}
	
	@Override
	public List<RequirementSubmission> getRequirementHistoryListByCandidateIdAndOrgLocationIdAndRequirementId(
			Long candidateId, Long orgLocationId, Long requirementId) throws Exception {
		return requirementSubmissionDao.getRequirementHistoryListByCandidateIdAndOrgLocationIdAndRequirementId(candidateId, orgLocationId, requirementId);
	}
	@Override
	public List<RequirementSubmission> duplicateCandidateCountList(Long requirementId, User user,
			OrgLocations loginOrgLocation) throws Exception {
		return requirementSubmissionDao.duplicateCandidateCountList(requirementId, user, loginOrgLocation) ;
	}

	@Override
	public void userAcceptRateNegotiation(Requirement requirement, User user, Candidate candidate,
			OrgLocations orgLocations, RequirementRateSubmissionNegotiation requirementRateSubmissionNegotiation) throws Exception {

		 EmailMessage emailMessage = new EmailMessage();
		 emailMessage.setFirstName(candidate.getFirstName());
		 emailMessage.setLastName(candidate.getLastName());
		 emailMessage.setToEmail(candidate.getEmail());
		 emailMessage.setFromEmail("peopleaxisindia@gmail.com");
		 emailMessage.setCreatedBy(user.getUserCode());
		 StringBuilder msgBuild = new StringBuilder();
		 emailMessage.setFromName(msgBuild.append(user.getFirstName()).append(" ").append(user.getLastName()).toString());
		 
		//setting the class filed values
		 Map<String, String> globalVarsMap = new HashMap<String, String>();

		 if(requirement.getJobTitle()!= null && !"".equalsIgnoreCase(requirement.getJobTitle())){
			 globalVarsMap.put("requirementName", requirement.getJobTitle());
			 }else{
				 globalVarsMap.put("requirementName","--");
			 }
		 if(candidate.getFirstName() != null && candidate.getLastName()!= null && !"".equalsIgnoreCase(candidate.getFirstName()) && !"".equalsIgnoreCase(candidate.getLastName())){
			 StringBuilder candBuild = new StringBuilder();
			 globalVarsMap.put("applicantName", candBuild.append(candidate.getFirstName()).append("").append(candidate.getLastName()).toString());
			 }else{
				 globalVarsMap.put("applicantName",candidate.getFirstName());
			 }
		 if(user.getFirstName()!= null && !"".equalsIgnoreCase(user.getFirstName()) && user.getLastName()!= null && !"".equalsIgnoreCase(user.getLastName())){
			 StringBuilder sb = new StringBuilder();
			 globalVarsMap.put("userName", sb.append(user.getFirstName()).append(" ").append(user.getLastName()).toString());
			 }else{
				 globalVarsMap.put("userName",user.getFirstName());
			 }
		 if(requirementRateSubmissionNegotiation.getCurrentRate() != null && !"".equalsIgnoreCase(requirementRateSubmissionNegotiation.getCurrentRate()) && requirementRateSubmissionNegotiation.getCurrentRateType() != null && !"".equalsIgnoreCase(requirementRateSubmissionNegotiation.getCurrentRateType())){
			 StringBuilder sb = new StringBuilder();
			 globalVarsMap.put("finalRate", sb.append(requirementRateSubmissionNegotiation.getCurrentRate()).append(" / ").append(requirementRateSubmissionNegotiation.getCurrentRateType()).toString());
			 }else{
				 globalVarsMap.put("finalRate","--");
			 }
		 //setting the Recipient information
		 ArrayList<EmailRecipient> emailRecipientList = new ArrayList<EmailRecipient>();
		 EmailRecipient emailRecipient = new EmailRecipient();
		 emailRecipient.setEmail(emailMessage.getToEmail());
		 StringBuilder receipBuild = new StringBuilder();
		 emailRecipient.setName(receipBuild.append(emailMessage.getFirstName()).append(" ").append(emailMessage.getLastName()).toString());
		 emailRecipientList.add(emailRecipient);
		
		 //getting the current EmailScenario by EmailScenario unique name and entity type
		 EmailScenario  emailScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.USER_ACCEPT_RATE_NEGOTIATION,Constants.ENTITY_TYPE_EMAIL);
		
		 //covert subject,body and signature
		 String subject = emailScenerioUtil.bulidSubject(emailScenario.getSubject(),globalVarsMap);
		 String body = emailScenerioUtil.buildTempBody(emailScenario.getBody(),globalVarsMap);
		 String signature = emailScenerioUtil.buildSignature(emailScenario.getSignature(),globalVarsMap);
		 String url = "";
		 
		 //construct HTML template using required data
		 String emailTemplate = emailScenerioUtil.AssembleEmailMessage(emailScenario,emailMessage, subject, body, signature, url, new Org());
		 emailMessage.setSubject(subject);
		 emailMessage.setMessageBody(emailTemplate);
		  
		 User toUser = orgLocations.getUser();
		 
		 String actionLink="";
		//calling the sendEmail method of emailScenerioUtil
		 emailScenerioUtil.sendEmail(emailMessage,orgLocations.getId(),emailRecipientList,new ArrayList<EmailAttachment>());
		

	}

	@Override
	public void sendEmailtoRequirementManager(Org loginOrg, User users, RequirementSubmission requirementSubmission,
			OrgLocations loginOrgLocation) throws Exception {
		
		 EmailMessage emailMessage = new EmailMessage();
		 emailMessage.setFirstName(requirementSubmission.getRequirement().getManagerUserId().getFirstName());
		 emailMessage.setLastName(requirementSubmission.getRequirement().getManagerUserId().getFirstName());
		 emailMessage.setToEmail(requirementSubmission.getRequirement().getManagerUserId().getEmail());
		 emailMessage.setFromEmail("peopleaxisindia@gmail.com");
		 emailMessage.setCreatedBy(users.getUserCode());
		 emailMessage.setFromName(users.getFirstName()+" "+users.getLastName());
		 
		//setting the class filed values
		 Map<String, String> globalVarsMap = new HashMap<String, String>();
		 if(requirementSubmission.getCandidate().getFirstName()!= null && !"".equalsIgnoreCase(requirementSubmission.getCandidate().getFirstName()) && requirementSubmission.getCandidate().getLastName()!= null && !"".equalsIgnoreCase(requirementSubmission.getCandidate().getLastName())){
			 globalVarsMap.put("AssignedUser", requirementSubmission.getCandidate().getFirstName()+" "+requirementSubmission.getCandidate().getLastName());
		 }else{
			 globalVarsMap.put("AssignedUser","--");
		 }
		 
		 
		 if(requirementSubmission.getRequirement().getManagerUserId().getFirstName() != null && !"".equalsIgnoreCase(requirementSubmission.getRequirement().getManagerUserId().getLastName())){
			 globalVarsMap.put("ManagerName", requirementSubmission.getRequirement().getManagerUserId().getFirstName() +""+requirementSubmission.getRequirement().getManagerUserId().getLastName() );
		 }else{
			 globalVarsMap.put("ManagerName","--");
		 }
		 
		 if(requirementSubmission.getCandidate().getFirstName() != null && !"".equalsIgnoreCase(requirementSubmission.getCandidate().getLastName())){
			 globalVarsMap.put("ApplicantName", requirementSubmission.getCandidate().getFirstName()+""+requirementSubmission.getCandidate().getLastName());
		 }else{
			 globalVarsMap.put("ApplicantName","--");
		 }
		 if(requirementSubmission.getRequirement().getJobTitle() != null && !"".equalsIgnoreCase(requirementSubmission.getRequirement().getJobTitle())){
			 globalVarsMap.put("RequirementName", requirementSubmission.getRequirement().getJobTitle());
		 }else{
			 globalVarsMap.put("RequirementName","--");
		 }
		 
		 if(loginOrgLocation.getCountry() != null && !"".equalsIgnoreCase(loginOrgLocation.getCountry())){
			 globalVarsMap.put("RequirementLocation", loginOrgLocation.getCountry());
		 }else{
			 globalVarsMap.put("RequirementLocation","--");
		 }
		 
		 if(requirementSubmission.getCandidate().getRate() != null && !"".equalsIgnoreCase(requirementSubmission.getCandidate().getRate())){
			 globalVarsMap.put("ApplicantRate", requirementSubmission.getCandidate().getRate());
		 }else{
			 globalVarsMap.put("ApplicantRate","--");
		 }

		
	}
	public void sendEmailtoApplicant(Org loginOrg, User users,RequirementSubmission requirementSubmission,
			 OrgLocations loginOrgLocation) throws Exception {
		
		 EmailMessage emailMessage = new EmailMessage();
		 emailMessage.setFirstName(requirementSubmission.getCandidate().getFirstName());
		 emailMessage.setLastName(requirementSubmission.getCandidate().getLastName());
		 emailMessage.setToEmail(requirementSubmission.getCandidate().getEmail());
		 emailMessage.setFromEmail("peopleaxisindia@gmail.com");
		 emailMessage.setCreatedBy(users.getUserCode());
		 emailMessage.setFromName(requirementSubmission.getRequirement().getManagerUserId().getFirstName()+" "+requirementSubmission.getRequirement().getManagerUserId().getLastName());
		 
		 Map<String, String> globalVarsMap = new HashMap<String, String>();
		 if(requirementSubmission.getRequirement().getJobTitle() != null && !"".equalsIgnoreCase(requirementSubmission.getRequirement().getJobTitle())){
			 globalVarsMap.put("RequirementName", requirementSubmission.getRequirement().getJobTitle());
		 }else{
			 globalVarsMap.put("RequirementName","--");
		 }
		 
		 if(requirementSubmission.getCandidate().getFirstName() != null && !"".equalsIgnoreCase(requirementSubmission.getCandidate().getLastName())){
			 globalVarsMap.put("ApplicantName", requirementSubmission.getCandidate().getFirstName()+""+requirementSubmission.getCandidate().getLastName());
		 }else{
			 globalVarsMap.put("ApplicantName","--");
		 }
		 
		 if(loginOrgLocation.getCountry() != null && !"".equalsIgnoreCase(loginOrgLocation.getCountry())){
			 globalVarsMap.put("RequirementLocation", loginOrgLocation.getCountry());
		 }else{
			 globalVarsMap.put("RequirementLocation","--");
		 }
		 
		 if(loginOrgLocation.getCountry() != null && !"".equalsIgnoreCase(loginOrgLocation.getCountry())){
			 globalVarsMap.put("EmploymentType", loginOrgLocation.getCountry());
		 }else{
			 globalVarsMap.put("EmploymentType","--");
		 }
		 
		 if(requirementSubmission.getCandidate().getRate() != null && !"".equalsIgnoreCase(requirementSubmission.getCandidate().getRate())){
			 globalVarsMap.put("Rate", requirementSubmission.getCandidate().getRate());
		 }else{
			 globalVarsMap.put("Rate","--");
		 }
		 
		 if(requirementSubmission.getCandidate().getFirstName()!= null && !"".equalsIgnoreCase(requirementSubmission.getCandidate().getFirstName()) && requirementSubmission.getCandidate().getLastName()!= null && !"".equalsIgnoreCase(requirementSubmission.getCandidate().getLastName())){
			 globalVarsMap.put("AssignedUser", requirementSubmission.getCandidate().getFirstName()+" "+requirementSubmission.getCandidate().getLastName());
		 }else{
			 globalVarsMap.put("AssignedUser","--");
		 }
		 
		 if(requirementSubmission.getRequirement().getManagerUserId().getFirstName() != null && !"".equalsIgnoreCase(requirementSubmission.getRequirement().getManagerUserId().getLastName())){
			 globalVarsMap.put("ManagerName", requirementSubmission.getRequirement().getManagerUserId().getFirstName() +""+requirementSubmission.getRequirement().getManagerUserId().getLastName() );
		 }else{
			 globalVarsMap.put("ManagerName","--");
		 }
		 
		 ArrayList<EmailRecipient> emailRecipientList = new ArrayList<EmailRecipient>();
		 EmailRecipient emailRecipient = new EmailRecipient();
		 emailRecipient.setEmail(emailMessage.getToEmail());
		 emailRecipient.setName(emailMessage.getFirstName()+" "+emailMessage.getLastName());
		 emailRecipientList.add(emailRecipient);
		
		 EmailScenario  emailScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.Applicant_Will_Receive_Mail_About_The_Acceptance_Of_Requirement_By_Manager,Constants.ENTITY_TYPE_EMAIL);

		 String subject = emailScenerioUtil.bulidSubject(emailScenario.getSubject(),globalVarsMap);
		 String body = emailScenerioUtil.buildTempBody(emailScenario.getBody(),globalVarsMap);
		 String signature = emailScenerioUtil.buildSignature(emailScenario.getSignature(),globalVarsMap);
		 String url = "";
		 
		 String emailTemplate = emailScenerioUtil.AssembleEmailMessage(emailScenario,emailMessage, subject, body, signature, url, new Org());
		 emailMessage.setSubject(subject);
		 emailMessage.setMessageBody(emailTemplate);
		  
		 emailScenerioUtil.sendEmail(emailMessage,loginOrgLocation.getId(),emailRecipientList,new ArrayList<EmailAttachment>());
		 
	}

	@Override
	public List<RequirementSubmission> getApplicantsByStatus(Long orgLocationsId, String status) throws Exception {
		
        
        List<RequirementSubmission> submittedApplicantsList =  requirementSubmissionDao.getApplicantsByStatus(orgLocationsId, status);
        
        for(RequirementSubmission reqsub :submittedApplicantsList){
            
            reqsub.setCandidateName(reqsub.getCandidate().getFirstName() +""+ reqsub.getCandidate().getLastName());
            reqsub.setRequirementName(reqsub.getRequirement().getJobTitle());
            
            User  submittedSourceUser = userDao.getUserByUserCode(reqsub.getCreatedBy());
            
            reqsub.setSubmittedBy(submittedSourceUser.getFirstName() +"" + submittedSourceUser.getLastName());
            reqsub.setSubmittedOn(Constants.DF_DMY.format(reqsub.getCreatedDate()));

        }
        return submittedApplicantsList;
	}

	@Override
	public void sendEmailtoVendor(Org loginOrg, User users, User pastUser, Requirement requirement, Candidate candidate,
			StringBuilder tempHTML,OrgLocations orgLocations) throws Exception {
		EmailMessage emailMessage = new EmailMessage();
		 emailMessage.setFirstName(pastUser.getFirstName());
		 emailMessage.setLastName(pastUser.getLastName());
		 emailMessage.setToEmail(pastUser.getEmail());
		 emailMessage.setFromEmail("peopleaxisindia@gmail.com");
		 emailMessage.setCreatedBy(users.getUserCode());
		 StringBuilder msgBuild = new StringBuilder();
		 emailMessage.setFromName(msgBuild.append(users.getFirstName()).append(" ").append(users.getLastName()).toString());
		 
		 
		 
		//setting the class filed values
		 Map<String, String> globalVarsMap = new HashMap<String, String>();
		 
		 if(pastUser.getFirstName()!= null && !"".equalsIgnoreCase(pastUser.getFirstName()) && pastUser.getLastName()!= null && !"".equalsIgnoreCase(pastUser.getLastName())){
			 StringBuilder pastUserBuild = new StringBuilder();
			 globalVarsMap.put("clientContact", pastUserBuild.append(pastUser.getFirstName()).append(" ").append(pastUser.getLastName()).toString());
			 }else{
				 globalVarsMap.put("clientContact",pastUser.getFirstName());
			 }
		 
		 if(users.getFirstName()!= null && !"".equalsIgnoreCase(users.getFirstName()) && users.getLastName()!= null && !"".equalsIgnoreCase(users.getLastName())){
			 StringBuilder userBuild = new StringBuilder();
			 globalVarsMap.put("loginUser", userBuild.append(users.getFirstName()).append(" ").append(users.getLastName()).toString());
			 }else{
				 globalVarsMap.put("loginUser",users.getFirstName());
			 }
		 
		 if(requirement.getJobTitle()!= null && !"".equalsIgnoreCase(requirement.getJobTitle())){
			 globalVarsMap.put("requirementName", requirement.getJobTitle());
			 }else{
				 globalVarsMap.put("requirementName","--");
			 }
		
		 if(candidate.getFirstName() != null && candidate.getLastName()!= null && !"".equalsIgnoreCase(candidate.getFirstName()) && !"".equalsIgnoreCase(candidate.getLastName())){
			 StringBuilder candiBuild = new StringBuilder();
			 globalVarsMap.put("candidateName", candiBuild.append(candidate.getFirstName()).append("").append(candidate.getLastName()).toString());
			 }else{
				 globalVarsMap.put("candidateName",candidate.getFirstName());
			 }
		 if(loginOrg.getOrgName() != null && !"".equalsIgnoreCase(loginOrg.getOrgName())){
			 globalVarsMap.put("loginOrgName", loginOrg.getOrgName());
			 }else{
				 globalVarsMap.put("loginOrgName","--");
			 }
		 if(tempHTML != null){
			 globalVarsMap.put("negotiationTable",tempHTML.toString());
		 }
		 
		 //setting the Recipient information
		 ArrayList<EmailRecipient> emailRecipientList = new ArrayList<EmailRecipient>();
		 EmailRecipient emailRecipient = new EmailRecipient();
		 emailRecipient.setEmail(emailMessage.getToEmail());
		 StringBuilder recipBuild = new StringBuilder();
 		 emailRecipient.setName(recipBuild.append(emailMessage.getFirstName()).append(" ").append(emailMessage.getLastName()).toString());
		 emailRecipientList.add(emailRecipient);
		
		 //getting the current EmailScenario by EmailScenario unique name and entity type
		 EmailScenario  emailScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.CLIENT_RATE_NEGOTIATION,Constants.ENTITY_TYPE_EMAIL);
//		 EmailScenario  notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.APPLICANT_NEGOTIATE_REQUIREMENT,Constants.ENTITY_TYPE_NOTIFICATION);

		
		 //covert subject,body and signature
		 String subject = emailScenerioUtil.bulidSubject(emailScenario.getSubject(),globalVarsMap);
		 String body = emailScenerioUtil.buildTempBody(emailScenario.getBody(),globalVarsMap);
		 String signature = emailScenerioUtil.buildSignature(emailScenario.getSignature(),globalVarsMap);
		 String url = "";
		 
		/*String notificationbody = emailScenerioUtil.buildBody(notificationScenario.getBody(),globalVarsMap);
		notificationScenario.setBody(notificationbody);*/
		 
		 //construct HTML template using required data
		 String emailTemplate = emailScenerioUtil.AssembleEmailMessage(emailScenario,emailMessage, subject, body, signature, url, new Org());
		 emailMessage.setSubject(subject);
		 emailMessage.setMessageBody(emailTemplate);
		  
		 String actionLink="";
		//calling the sendEmail method of emailScenerioUtil
		 emailScenerioUtil.sendEmail(emailMessage,orgLocations.getId(),emailRecipientList,new ArrayList<EmailAttachment>());
	}

}


