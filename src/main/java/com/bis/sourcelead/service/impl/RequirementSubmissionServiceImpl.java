package com.bis.sourcelead.service.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.mail.internet.InternetAddress;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.joda.time.DateTime;
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
import com.bis.sourcelead.dao.InvoiceDao;
import com.bis.sourcelead.dao.RequirementDao;
import com.bis.sourcelead.dao.RequirementReferDao;
import com.bis.sourcelead.dao.RequirementSubmissionDao;
import com.bis.sourcelead.dao.RequirementSubmissionTemplateFieldsDao;
import com.bis.sourcelead.dao.UserDao;
import com.bis.sourcelead.dto.ApplicantAuthorizationDTO;
import com.bis.sourcelead.dto.ApplicantsJoinedReportDTO;
import com.bis.sourcelead.dto.AssociateJoineReportDTO;
import com.bis.sourcelead.dto.CandidateConfigurationDTO;
import com.bis.sourcelead.dto.CandidateResumesDTO;
import com.bis.sourcelead.dto.FileInfo;
import com.bis.sourcelead.dto.RequirementDTO;
import com.bis.sourcelead.dto.RequirementSubmissionsCountDTO;
import com.bis.sourcelead.dto.RequirementSubmissionsDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.elk.index.CandidateIndex;
import com.bis.sourcelead.elk.index.helper.CandidateIndexHelper;
import com.bis.sourcelead.elk.setup.RequirementElkUtil;
import com.bis.sourcelead.elk.setup.SourceleadELKBaseIndexer;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.CandidateEducation;
import com.bis.sourcelead.entity.CandidateEmployers;
import com.bis.sourcelead.entity.CandidateResumes;
import com.bis.sourcelead.entity.CandidateVerificationConfig;
import com.bis.sourcelead.entity.Contracts;
import com.bis.sourcelead.entity.Countries;
import com.bis.sourcelead.entity.EmailAttachment;
import com.bis.sourcelead.entity.EmailMessage;
import com.bis.sourcelead.entity.EmailRecipient;
import com.bis.sourcelead.entity.EmailScenario;
import com.bis.sourcelead.entity.MasterCandidate;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgRelations;
import com.bis.sourcelead.entity.OrgSpoc;
import com.bis.sourcelead.entity.RecommendedCustomizeScore;
import com.bis.sourcelead.entity.Requirement;
import com.bis.sourcelead.entity.RequirementIncentive;
import com.bis.sourcelead.entity.RequirementInterview;
import com.bis.sourcelead.entity.RequirementLocations;
import com.bis.sourcelead.entity.RequirementOffers;
import com.bis.sourcelead.entity.RequirementRateSubmissionNegotiation;
import com.bis.sourcelead.entity.RequirementRefer;
import com.bis.sourcelead.entity.RequirementSubmission;
import com.bis.sourcelead.entity.RequirementSubmissionHistory;
import com.bis.sourcelead.entity.RequirementSubmissionTemplateFields;
import com.bis.sourcelead.entity.RequirementSubmissionsView;
import com.bis.sourcelead.entity.SubmissionXls;
import com.bis.sourcelead.entity.Timezone;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.enums.ApplicantSubmissionStatus;
import com.bis.sourcelead.enums.SubmissionRateStatus;
import com.bis.sourcelead.enums.Submission_Status;
import com.bis.sourcelead.service.CandidateService;
import com.bis.sourcelead.service.CountriesService;
import com.bis.sourcelead.service.EmailScenarioService;
import com.bis.sourcelead.service.MasterCandidateService;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.OrgRelationsService;
import com.bis.sourcelead.service.OrgSpocService;
import com.bis.sourcelead.service.RecommendationsService;
import com.bis.sourcelead.service.RecommendedCustomizeScoreService;
import com.bis.sourcelead.service.RequirementInterviewService;
import com.bis.sourcelead.service.RequirementLocationsService;
import com.bis.sourcelead.service.RequirementOffersService;
import com.bis.sourcelead.service.RequirementRateSubmissionNegotiationService;
import com.bis.sourcelead.service.RequirementService;
import com.bis.sourcelead.service.RequirementSubmissionHistoryService;
import com.bis.sourcelead.service.RequirementSubmissionService;
import com.bis.sourcelead.service.RequirementSubmissionTemplateFieldsService;
import com.bis.sourcelead.service.SubmissionXlsService;
import com.bis.sourcelead.service.TimeZoneService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.CalendarUtil;
import com.bis.sourcelead.util.CandidateActivityCommonUtil;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.EmailScenerioUtil;
import com.bis.sourcelead.util.FileUploadUtil;
import com.bis.sourcelead.util.PdfUtil;
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
	
	@Value("${atsUrl}")
    private String atsUrl;
	
	@Value("${sourceleadLoginUrl}")
	private String sourceleadLoginUrl;
	
	@Value("${static.path}")
	private String staticPath;
	
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
	private InvoiceDao  invoiceDao;
	
	@Autowired
	CandidateService candidateService;
	
	@Autowired
	RequirementSubmissionTemplateFieldsService requirementSubmissionTemplateFieldsService;
	
	/*@Autowired
	CandidateSkillsService candidateSkillsService;*/
	
	@Autowired
	CandidateIndexHelper candidateIndexHelper;
	
	@Autowired
    private SourceleadELKBaseIndexer<CandidateIndex> sourceleadELKBaseIndexer;
	
	@Autowired
	RecommendationsService recommendationsService;
	
	@Autowired
	RecommendedCustomizeScoreService recommendedCustomizeScoreService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	RequirementService requirementService;
	 
	@Autowired
	RequirementElkUtil requirementElkUtil;
	
	@Autowired
	RequirementReferDao requirementReferDao;
	
	@Autowired
	OrgLocationService orgLocationService;
	
	@Autowired
	TimeZoneService timeZoneService;
	
	@Autowired
	OrgRelationsService orgRelationsService;
	
	@Autowired
	OrgSpocService orgSpocService;
	
	@Autowired
    CandidateActivityCommonUtil candidateActivityCommonUtil;
	
	@Autowired
	private  MasterCandidateService masterCandidateService;
	
	@Value("${env}")
	private String environment;
	
	@Autowired
	RequirementLocationsService requirementLocationsService;
	
	@Autowired
	CountriesService countiresService;
	
    @Autowired
	RequirementOffersService requirementOffersService;

    @Autowired
    RequirementInterviewService requirementInterviewService;
    
    @Autowired
    PdfUtil pdfUtil;
	@Override
	public RequirementSubmission getRequirementSubmissionById(Long submissionId) throws Exception {
		return requirementSubmissionDao.getRequirementSubmissionById(submissionId);
	}
	
	@Override
	public RequirementSubmission getClientRequierementSubmission(Long submissionId) throws Exception {
		return requirementSubmissionDao.getClientRequierementSubmission(submissionId);
	}
	
	@Override
	public List<RequirementSubmissionsDTO> getAllShortListCandidatesByOrgLocationsIdAndRequirementId(OrgLocations orgLocations,Long requirementId, List<Long> existingApplicantsIds, String isClientInterview,User user,String loginUserRole) throws Exception {
		List<RequirementSubmissionsDTO> requirementSubmissionsDTOList = new LinkedList<>();
		List<Object[]> objList = requirementSubmissionDao.getAllShortListCandidatesByOrgLocationsIdAndRequirementId(orgLocations,requirementId,existingApplicantsIds,isClientInterview,user,loginUserRole);
		if(objList != null && !objList.isEmpty()) {
			for(Object[] obj : objList) {
				RequirementSubmissionsDTO requirementSubmissionsDTO = new RequirementSubmissionsDTO();
				requirementSubmissionsDTO.setId(Long.parseLong(String.valueOf(obj[0])));
				requirementSubmissionsDTO.setCreatedUser(String.valueOf(obj[1]));
				requirementSubmissionsDTO.setClientSubmissions(String.valueOf(obj[2]));
				requirementSubmissionsDTOList.add(requirementSubmissionsDTO);
			}
		}
		return requirementSubmissionsDTOList;
	}
	
	@Override
	public List<RequirementSubmission> getAllSubmittedAndShortListAndInterviewededCandidates(OrgLocations orgLocations,Long requirementId, List<Long> existingApplicantsIds, String isClientOffer,User user,String loginUserRole,String isClientRequirement,String isWorkflowConfigured) throws Exception {
		return requirementSubmissionDao.getAllSubmittedAndShortListAndInterviewededCandidates(orgLocations,requirementId,existingApplicantsIds,isClientOffer,user,loginUserRole,isClientRequirement,isWorkflowConfigured);
	}

	
	@Override
	public RequirementSubmission storeRequirementSubmission(RequirementSubmission requirementSubmission) throws Exception {
		logger.debug("In ProjectsServiceImpl class saveProject method...");
		requirementSubmission.setUpdatedDate(commonUtil.currentDate());
		return requirementSubmissionDao.storeRequirementSubmission(requirementSubmission);
	}
	
	protected static boolean isNullOrEmpty(String str) {
		return (str == null || "".equals(str.trim()));
	}


	@SuppressWarnings("unused")
	@Override
	public RequirementSubmission saveRequirementSubmission(JSONObject jsonObj,User user,OrgLocations orgLocations,String jsonStringvalues,String loginUserRole) throws Exception {
		
		 Requirement requirement = null;
		 RequirementSubmission requirementSubmission = new RequirementSubmission();
		 Candidate candidate = null;
		 CandidateResumes candidateResumes = null;
		 Date date = new Date();
		 OrgLocations guestOrglocation=null;
		 String submissionstatus=null;
		 Countries countries = null;
		 if(orgLocations != null && StringUtils.isNotBlank(orgLocations.getCountry())) {
			 countries = countiresService.getCountriesByCountryName(orgLocations.getCountry());
		 }
		 
		 
		// SimpleDateFormat simpleDateFormat = orgLocations.getLocFullFormDateFormatter();
		 if(jsonObj.has("guestApplicant")){
			 submissionstatus="guestApplicant";
			 
		 }
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
				if(!isNullOrEmpty(loginUserRole) && loginUserRole.equalsIgnoreCase("ROLE_CROWDSOURCING_RECRUITER")){
					 candidate = candidateDao.getCandidateByEmailAndRecruiterUserId(jsonObj.getString("EmailId"), user.getId());
				}else {
					if(orgLocations != null){
						 candidate = candidateDao.findByCandidateEmail(jsonObj.getString("EmailId"),orgLocations.getId());
						}
						else{
							 candidate = candidateDao.findByCandidateEmail(jsonObj.getString("EmailId"),requirement.getOrgLocationId().getId());
						}
				}
		 }
		 
		 if(candidate == null){
			       candidate = new Candidate();
			       
					if(orgLocations == null){
						guestOrglocation=orgLocationService.getOrgLocations(requirement.getOrgLocationId().getId());
					}
					if(orgLocations != null){
			        candidate.setOrgLocationId(orgLocations);
					}else{
					candidate.setOrgLocationId(guestOrglocation);
					}
					
			        if(jsonObj.has("FirstName") && !isNullOrEmpty(jsonObj.getString("FirstName"))){
					candidate.setFirstName(jsonObj.getString("FirstName"));
					}
					
			        if(jsonObj.has("LastName") && !isNullOrEmpty(jsonObj.getString("LastName"))){
					candidate.setLastName(jsonObj.getString("LastName") );
					}
			        
			        if(jsonObj.has("location") && !isNullOrEmpty(jsonObj.getString("location"))){
			        	candidate.setLocation(jsonObj.getString("location"));
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
					
					 if(jsonObj.has("zipcode") && !isNullOrEmpty(jsonObj.getString("zipcode"))){
				        	candidate.setZipCode(jsonObj.getString("zipcode"));
						}
					
					if(jsonObj.has("PhoneNumber") && !isNullOrEmpty(jsonObj.getString("PhoneNumber"))){
						 candidate.setMobilePhone(jsonObj.getString("PhoneNumber"));
					}
					
					if(jsonObj.has("AlternateNumber") && !isNullOrEmpty(jsonObj.getString("AlternateNumber"))){
						 candidate.setAlternateMobilePhone(jsonObj.getString("AlternateNumber"));
					}
					
					if(jsonObj.has("EmailId") && !isNullOrEmpty(jsonObj.getString("EmailId"))){
					candidate.setEmail(jsonObj.getString("EmailId"));
					}
					
					if(jsonObj.has("currentCTCRateType") && !isNullOrEmpty(jsonObj.getString("currentCTCRateType"))){
						candidate.setCurrentRateCurrency(jsonObj.getString("currentCTCRateType"));
					}else {
						if(countries != null) {
							candidate.setCurrentRateCurrency(countries.getCurrency());
						}
					}
					
					if(jsonObj.has("CurrentCTC") && !isNullOrEmpty(jsonObj.getString("CurrentCTC"))){
						candidate.setCurrentRate(jsonObj.getString("CurrentCTC"));
					}
					
					if(jsonObj.has("currentCTCRatePer") && !isNullOrEmpty(jsonObj.getString("currentCTCRatePer"))){
						 candidate.setCurrentRateType(jsonObj.getString("currentCTCRatePer"));
					}else {
						candidate.setCurrentRateType("Monthly");
					}
					
					if(jsonObj.has("expectedCTCRateType") && !isNullOrEmpty(jsonObj.getString("expectedCTCRateType"))){
						 candidate.setExpectedRateCurrencey(jsonObj.getString("expectedCTCRateType"));
						 
					}else {
						if(countries != null) {
							candidate.setExpectedRateCurrencey(countries.getCurrency());
						}
					}
					 
					if(jsonObj.has("ExpectedCTC") && !isNullOrEmpty(jsonObj.getString("ExpectedCTC"))){
						 candidate.setExpectedRate(jsonObj.getString("ExpectedCTC"));
						 
					}
					
					if(jsonObj.has("expectedCTCRatePer") && !isNullOrEmpty(jsonObj.getString("expectedCTCRatePer"))){
						 candidate.setRateType(jsonObj.getString("expectedCTCRatePer"));
					}else {
						candidate.setRateType("Monthly");
					}
					
					if(jsonObj.has("NoticePeriod") && !isNullOrEmpty(jsonObj.getString("NoticePeriod"))){
						 candidate.setNoticePeriod(jsonObj.getString("NoticePeriod"));
						 
					}
					
					if(jsonObj.has("Designation") && !isNullOrEmpty(jsonObj.getString("Designation"))){
						candidate.setDesignation(jsonObj.getString("Designation"));
					}
					 if(jsonObj.has("WorkExperience") && !isNullOrEmpty(jsonObj.getString("WorkExperience"))){
						 candidate.setExperienceYears(jsonObj.getString("WorkExperience"));
					 }
					 
					 if(jsonObj.has("monthExp") && !isNullOrEmpty(jsonObj.getString("monthExp"))){
						 candidate.setExperienceMonths(jsonObj.getString("monthExp"));
					 }
					 
					 if(jsonObj.has("designationType") && jsonObj.getString("designationType") !=null){
							candidate.setDesignationType(jsonObj.getString("designationType"));
					 } 
				 
					if(jsonObj.has("designationOtherType") && jsonObj.has("designationType") && "Other".equalsIgnoreCase(jsonObj.getString("designationType"))){
						candidate.setDesignationOtherType(jsonObj.getString("designationOtherType"));
					}else{
						candidate.setDesignationOtherType(null);
					}
					 
					 
					if(jsonObj.has("vendors") && !"".equalsIgnoreCase(jsonObj.getString("vendors"))){
						OrgRelations orgRelation = orgRelationsService.getOrRelationsById(Long.parseLong(jsonObj.getString("vendors")));
						candidate.setOrgRelationId(orgRelation);
						Candidate vendorCandidate = commonUtil.getVendorCandidate(jsonObj, orgRelation, orgLocations, user);
						candidate.setParentCandidateId(vendorCandidate);
					}else{
						candidate.setOrgRelationId(null);
						candidate.setParentCandidateId(null);
					} 	
					
					if(jsonObj.has("vendorContact") && !"".equalsIgnoreCase(jsonObj.getString("vendorContact"))){
					   OrgSpoc orgspoc = orgSpocService.getOrgSpocById(Long.parseLong(jsonObj.getString("vendorContact")));
					  candidate.setSpocId(orgspoc);
					}else{
					  candidate.setSpocId(null);
					}
					if(jsonObj.has("primarySkill") && !isNullOrEmpty(jsonObj.getString("primarySkill"))){
						 candidate.setPrimarySkill(jsonObj.getString("primarySkill"));
					}
					MasterCandidate masterCandidate = null;
					masterCandidate = masterCandidateService.getMasterCandidateByInteligence(jsonObj);
					
//					User guestUser = null ;
//					if(user == null){
//						guestUser = userService.getUserById(requirement.getCreatedBy().getId());
//					}
				
					
					
					if(orgLocations != null){
						if(null == masterCandidate){
							masterCandidate = masterCandidateService.storeMasterCandidateByJsonObject(jsonObj, user,orgLocations);
						}
					candidate.setGeneratedId(candidateService.getCandidateId(orgLocations));
					candidate.setCreatedDate(commonUtil.orgLocationCommonDate(date, orgLocations));
					candidate.setUpdatedDate(commonUtil.orgLocationCommonDate(date, orgLocations));
					
					}
					else{
						if(null == masterCandidate){
							masterCandidate = masterCandidateService.storeMasterCandidateByJsonObject(jsonObj, user,guestOrglocation);
						}
						candidate.setGeneratedId(candidateService.getCandidateId(guestOrglocation));
						candidate.setCreatedDate(commonUtil.orgLocationCommonDate(date, guestOrglocation));
						candidate.setUpdatedDate(commonUtil.orgLocationCommonDate(date, guestOrglocation));	
					}
					
					if(user != null){
						
						candidate.setCreatedBy(user.getUserCode());	
					}
					
					else{
						
						candidate.setCreatedBy(null);	
					}
					candidate.setMasterCandidateId(masterCandidate);
					candidate.setCandidateType("Candidate");
					candidate.setIsAssociate(Constants.NO);
					candidate.setCandidateUUID(SourceLeadRandomCodeHelper.generateRandomUUID());
					candidate.setStatus(Constants.CANDIDATE_ACTIVE);
					if(!isNullOrEmpty(loginUserRole) && loginUserRole.equalsIgnoreCase("ROLE_CROWDSOURCING_RECRUITER")){
						candidate.setRecruiterUserId(user);
					}
					
					 
					 if(jsonObj.has("candidateSkills") && !isNullOrEmpty(jsonObj.getString("candidateSkills"))){
						 
						  
						 String candidateSkills = null;
							try{
					    		   JSONArray jsonArray = jsonObj.getJSONArray("candidateSkills");
					    		   candidateSkills = jsonArray.toString();
					    	  }
					    	  catch(JSONException e){
					    		  candidateSkills= jsonObj.getString("candidateSkills");
					    	  }
							candidateSkills = candidateSkills.replaceAll("\"", "").replaceAll("\\[", "").replaceAll("\\]","");
							
						 
						 candidate.setSkillsName(candidateSkills);
						 
					
					 }
					
					 candidate = candidateDao.storeCandidate(candidate);
					 candidateService.getCandidatePercentage(candidate, orgLocations);
					 CandidateIndex candidateIndex = candidateIndexHelper.getCandidateIndexFromCandidate(candidate);
					
					sourceleadELKBaseIndexer.saveOrUpdateElkDocument(candidateIndex, Constants.ELK_CANDIDATE_INDEX, Constants.ELK_CANDIDATE_TYPE,candidate.getId().toString());
					
					requirementSubmission.setCandidate(candidate);
					
					if(jsonObj.has("Education") && !isNullOrEmpty(jsonObj.getString("Education"))){
						
			    		
			    		String[] educationList = jsonObj.getString("Education").split(",");
			    		   
						for(int i=0; i<educationList.length; i++){
							CandidateEducation candidateEducation = new CandidateEducation();
							candidateEducation.setCandidateId(candidate);
							candidateEducation.setOrglocationId(orgLocations);
							candidateEducation.setName(educationList[i].startsWith(" ") ? educationList[i].substring(1) : educationList[i]);
							if(user != null){
							candidateEducation.setCreatedBy(user.getUserCode());
							}
							else{
								candidateEducation.setCreatedBy(null);

							}
							candidateEducation.setCreatedDate(new Date());
							candidateEducation.setStatus(Constants.CANDIDATE_ACTIVE);
							candidateEducationDao.storeCandidateEducation(candidateEducation);
							candidateActivityCommonUtil.storeCandidateEducationHistory(candidateEducation, user);
						}
					 }
					
					 if(jsonObj.has("Current/LastCompany") && !isNullOrEmpty(jsonObj.getString("Current/LastCompany"))){
						 CandidateEmployers candidateEmployers = new CandidateEmployers();
						 candidateEmployers.setOrgLocationId(orgLocations);
						 candidateEmployers.setCandidateId(candidate);
						 candidateEmployers.setName(jsonObj.getString("Current/LastCompany"));
						 candidateEmployers.setStatus(Constants.CANDIDATE_ACTIVE);
						 candidateEmployers.setCreatedDate(new Date());
						 if(user != null){
							 candidateEmployers.setCreatedBy(user.getUserCode());

						 }
						 else{
						 candidateEmployers.setCreatedBy(null);
						 }
						 candidateEmployers.setUpdatedDate(new Date());
						 candidateEmployersDao.storeCandidateEmployers(candidateEmployers);
						 candidateActivityCommonUtil.storeCandidateEmployersHistory(candidateEmployers, user);
					 }
					 
					 candidateActivityCommonUtil.storeCandidateHistory(candidate, user);
					 candidateActivityCommonUtil.storeCandidateActivity(user, orgLocations, candidate, requirement.getJobTitle(), Constants.CANDIDATE_ADDED_FROM_JOB, "", "JOBS");
					
		 }else{
			 requirementSubmission.setCandidate(candidate);
			 
			 
			if(jsonObj.has("PhoneNumber") && !isNullOrEmpty(jsonObj.getString("PhoneNumber"))){
				 candidate.setMobilePhone(jsonObj.getString("PhoneNumber"));
			}
			
			if(jsonObj.has("AlternateNumber") && !isNullOrEmpty(jsonObj.getString("AlternateNumber"))){
				 candidate.setAlternateMobilePhone(jsonObj.getString("AlternateNumber"));
			}
			
		    candidate = candidateDao.storeCandidate(candidate);
			CandidateIndex candidateIndex = candidateIndexHelper.getCandidateIndexFromCandidate(candidate);
			sourceleadELKBaseIndexer.saveOrUpdateElkDocument(candidateIndex, Constants.ELK_CANDIDATE_INDEX, Constants.ELK_CANDIDATE_TYPE,candidate.getId().toString());
				
			
		 }
		 
		
		 if(jsonObj.has("candidateResumeRequired") && !isNullOrEmpty(jsonObj.getString("candidateResumeRequired")) && jsonObj.getString("candidateResumeRequired").equalsIgnoreCase(Constants.YES)){
			 CandidateResumesDTO resumeDTO = candidateResumesDao.getCandidateResumesDTOByCandidateId(candidate.getId());
			 if(resumeDTO != null) {
				 CandidateResumes resume = new CandidateResumes();
				 resume.setId(resumeDTO.getResumeId().longValue());
				 requirementSubmission.setCandidateResumes(resume);
			 }
			
		 }
		 
		 
		 if(jsonObj.has("candidateResume") && !isNullOrEmpty(jsonObj.getString("candidateResume"))){
			 candidateResumes = candidateResumesDao.getCandidateResumeByResumeId(Long.parseLong(jsonObj.getString("candidateResume")));
			 candidateResumes.setId(Long.parseLong(jsonObj.getString("candidateResume")));
			 requirementSubmission.setCandidateResumes(candidateResumes);
			 
		 }else{
			 
			 if((jsonObj.has("fileInfo[0].fileName") && !isNullOrEmpty(jsonObj.getString("fileInfo[0].fileName"))) && (jsonObj.has("fileInfo[0].fileURL") && !isNullOrEmpty(jsonObj.getString("fileInfo[0].fileURL"))) && (jsonObj.has("fileInfo[0].contentType") && !isNullOrEmpty(jsonObj.getString("fileInfo[0].contentType")))){
				 FileInfo fileInfo = new FileInfo();
				 fileInfo.setFileName(jsonObj.get("fileInfo[0].fileName").toString());
				 fileInfo.setFileURL(jsonObj.get("fileInfo[0].fileURL").toString());
				 fileInfo.setContentType(jsonObj.get("fileInfo[0].contentType").toString());
				 candidateResumes = new CandidateResumes();
 				 candidateResumes.setCandidateId(candidate);
				 candidateResumes.setFileName(jsonObj.get("fileInfo[0].fileName").toString());
				 candidateResumes.setTitle(jsonObj.get("fileInfo[0].fileName").toString());
				 candidateResumes.setFileContentType(jsonObj.get("fileInfo[0].contentType").toString());
				 candidateResumes.setFileURL(jsonObj.get("fileInfo[0].fileURL").toString().replaceAll(" ", "_"));
 				 candidateResumes.setStatus(Constants.CANDIDATE_ACTIVE);
 				 candidateResumes.setResumeUUID(SourceLeadRandomCodeHelper.generateRandomUUID());
 				 candidateResumes.setCreatedDate(new Date());
 				 if(user !=null){
				 candidateResumes.setCreatedBy(user.getUserCode());
 				 }
 				 else{
 					 candidateResumes.setCreatedBy(null);
 
 				 }
				 candidateResumes.setUpdatedDate(new Date());
				 if(orgLocations != null && submissionstatus != "guestApplicant"){
					 candidateResumes.setOrgLocationId(orgLocations);

				 }
				 else{
				 candidateResumes.setOrgLocationId(guestOrglocation);
				 }
				 if(orgLocations != null && submissionstatus != "guestApplicant"){
				 FileUploadUtil.moveFile(staticPath, fileInfo, "addApplicants");
				 candidateResumesDao.storeCandidateResumes(candidateResumes);
				 }
				 else{
					 
					 FileUploadUtil.moveFile(staticPath, fileInfo, "guestAddApplicants");
					 candidateResumesDao.storeCandidateResumes(candidateResumes);
				 }
				 if(user !=null){
				 candidateActivityCommonUtil.storeCandidateResumesHistory(candidateResumes, user);
				 }
				 else{
					 candidateActivityCommonUtil.storeCandidateResumesHistory(candidateResumes, null);
				 }
				 requirementSubmission.setCandidateResumes(candidateResumes);
			 }
		 }
			if(orgLocations == null){
				guestOrglocation=orgLocationService.getOrgLocations(requirement.getOrgLocationId().getId());
			}
		 if(orgLocations !=null){
		 requirementSubmission.setOrgLocations(orgLocations);
		 }
		 else{
			 requirementSubmission.setOrgLocations(guestOrglocation); 
		 }
		 requirementSubmission.setSubmissionTemplateJsonStringValue(jsonStringvalues);
		 
		 /* the functionality of this block is to verify the submitted candidate is in the Cooling-Off Period or not
		 if the applicant is in cooling-off period, the candidate should be in rejected state(set previous state to Submission) at client side  [Venkat Kesav] START Code snippet*/
		 /*RequirementSubmissionHistory requirementSubmissionHistoryForCoolingOff = null;
			 String reqCoolingOffPeriod = requirement.getReqCoolingOffPeriod();
			 String [] coolingOffArr = reqCoolingOffPeriod.split(" ");
			 String reqCoolingOffPeriodDays;
			 int Days;
			 switch (coolingOffArr[1]) {
				 case "Week" :
					 reqCoolingOffPeriodDays = "7";
				      break;
				 case "Weeks" :
					 Days  = (Integer.parseInt(coolingOffArr[0]) * 7);
					 reqCoolingOffPeriodDays = Integer.toString(Days);
				     break;
				 case "Month" :
					 reqCoolingOffPeriodDays = "30";
				     break;
				 case "Months" :
					 Days  = (Integer.parseInt(coolingOffArr[0]) * 30);
					 reqCoolingOffPeriodDays = Integer.toString(Days);
				     break;
				 case "Year" :
					 reqCoolingOffPeriodDays = "365";
				     break;
				 default:
					 reqCoolingOffPeriodDays = "0";
			 }*/
		
		 if(jsonObj.has("needApplicantPermit") && !isNullOrEmpty(jsonObj.getString("needApplicantPermit"))){
             if(jsonObj.getString("needApplicantPermit").equals("Y")){
                 requirementSubmission.setNeedApplicantPermit("Y");
                 requirementSubmission.setSubmissionStatus(ApplicantSubmissionStatus.REQUEST_SENT.toString());
             }
         }
		else{
                 requirementSubmission.setSubmissionStatus(ApplicantSubmissionStatus.SUBMISSION.toString());
             }	 
		
			 /*requirementSubmissionHistoryForCoolingOff = requirementSubmissionHistoryService.isInCoolingOffPeriodByEmailAndLocationsId(requirementSubmission.getCandidate().getEmail(), requirement.getOrgLocationId(), reqCoolingOffPeriodDays, requirement.getId());
			 if(requirementSubmissionHistoryForCoolingOff != null){
				 if(requirementSubmissionHistoryForCoolingOff.getIsInCoolingOffPeriod()){
					 requirementSubmission.setSubmissionStatus(ApplicantSubmissionStatus.REJECTED);
					 if(jsonObj.has("needApplicantPermit") && !isNullOrEmpty(jsonObj.getString("needApplicantPermit")) && jsonObj.getString("needApplicantPermit").equals("Y")){
		                	 requirementSubmission.setPreviousSubmissionStatus(ApplicantSubmissionStatus.REQUEST_SENT);
		             }else {
		            	 	requirementSubmission.setPreviousSubmissionStatus(ApplicantSubmissionStatus.SUBMISSION);
		             }
					 requirementSubmission.setRejectedReason("Under cooling off period, completed by "+requirementSubmissionHistoryForCoolingOff.getCoolingOffPeriodEndDateStr()+".");
					 requirementSubmission.setRejectedCategoryType("Other");
					 requirementSubmission.setRejectedCategoryOther("Under cooling off period");
				 }
			 }*/
			 /*END Code snippet*/
		 
		 requirementSubmission.setStatus(Constants.CANDIDATE_ACTIVE);
		 if(user != null){
		 requirementSubmission.setCreatedBy(user.getUserCode());
		 }
		 else{
			 requirementSubmission.setCreatedBy(null);
		 }
		 requirementSubmission.setCreatedDate(new Date());
		 requirementSubmission.setUpdatedDate(new Date());
		 RecommendedCustomizeScore recommendedCustomizeScore = recommendedCustomizeScoreService.getRecommendedCustomizeScoreByRequirementId(requirement.getId());
		 String score = recommendationsService.getCandidateScore(candidate,requirement,recommendedCustomizeScore);
		 if(null !=score){
			 requirementSubmission.setCandidateScore(score);			 
		 }
		 StringBuilder userName = new StringBuilder();
		 StringBuilder nameBuilder = new StringBuilder();
		 if(user !=null){
		 if(!isNullOrEmpty(user.getLastName())){
			 userName = nameBuilder.append(user.getFirstName()).append(" ").append(user.getLastName());
	   	 }else{
	   		 userName = nameBuilder.append(user.getFirstName());
	   	 }
		 requirementSubmission.setCreatedUser(userName.toString());
		 requirementSubmission.setUpdatedUser(userName.toString());
		 }
		 else{
		 requirementSubmission.setCreatedUser(null);
		 requirementSubmission.setUpdatedUser(null);
		 }
		 if(!isNullOrEmpty(loginUserRole) && loginUserRole.equalsIgnoreCase("ROLE_CROWDSOURCING_RECRUITER")){
			 requirementSubmission.setRecruiterUserId(user);
			 requirementSubmission.setOrgLocations(requirement.getOrgLocationId());
		 }else{
			 if(orgLocations != null){
			 requirementSubmission.setOrgLocations(orgLocations);
			 }else{
				 requirementSubmission.setOrgLocations(guestOrglocation); 
			 }
		 }
		 
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
		 
		 if(jsonObj.has("location") && !isNullOrEmpty(jsonObj.getString("location"))){
			 templateFields.setLocation(jsonObj.getString("location"));
		 }
		 
		 if(jsonObj.has("zipcode") && !isNullOrEmpty(jsonObj.getString("zipcode"))){
			 templateFields.setZipCode(jsonObj.getString("zipcode"));
		 }
		 
		 if(jsonObj.has("DOB") && !isNullOrEmpty(jsonObj.getString("DOB"))){
				try {
					if(orgLocations != null){
					   templateFields.setDateOfBirth(commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),jsonObj.getString("DOB")));
					
					}
					else{
						   templateFields.setDateOfBirth(commonUtil.orgLocationDateFormat(guestOrglocation.getDateFormat(),jsonObj.getString("DOB")));
					}
				}
					catch (Exception e) {
					
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
			 StringBuilder sb = new StringBuilder();
			 if(jsonObj.has("monthExp") && !isNullOrEmpty(jsonObj.getString("monthExp"))){
				 templateFields.setWorkExperience(sb.append(jsonObj.getString("WorkExperience")).append(".").append(jsonObj.getString("monthExp")).toString());
			 }else{
				 templateFields.setWorkExperience(sb.append(jsonObj.getString("WorkExperience")).toString());
			 }
		 }
		 
		 if(jsonObj.has("Education") && !isNullOrEmpty(jsonObj.getString("Education"))){
			 templateFields.setEducation(jsonObj.getString("Education"));
		 }
		 
		 if(jsonObj.has("CurrentCTC") && !isNullOrEmpty(jsonObj.getString("CurrentCTC"))){
			 templateFields.setCurrentCTC(jsonObj.getString("CurrentCTC"));
		 }
		 
		 if(jsonObj.has("currentCTCRateType") && !isNullOrEmpty(jsonObj.getString("currentCTCRateType"))){
			 templateFields.setCurrentRateType(jsonObj.getString("currentCTCRateType"));
		 }else {
			 if(countries != null) {
					templateFields.setCurrentRateType(countries.getCurrency());
				}
			 
		 }
		 
		 if(jsonObj.has("ExpectedCTC") && !isNullOrEmpty(jsonObj.getString("ExpectedCTC"))){
			 templateFields.setExpectedCTC(jsonObj.getString("ExpectedCTC"));
		 }
		 
		 if(jsonObj.has("expectedCTCRateType") && !isNullOrEmpty(jsonObj.getString("expectedCTCRateType"))){
			 templateFields.setExpectedRateType(jsonObj.getString("expectedCTCRateType"));
		 }else {
			 if(countries != null) {
					templateFields.setExpectedRateType(countries.getCurrency());
				}
		 }
		 
			
		 if(jsonObj.has("currentCTCRatePer") && !isNullOrEmpty(jsonObj.getString("currentCTCRatePer"))){
			templateFields.setCurrentRatePer(jsonObj.getString("currentCTCRatePer"));
		 }else {
			 templateFields.setCurrentRatePer("Monthly");
		 }
			
		 if(jsonObj.has("expectedCTCRatePer") && !isNullOrEmpty(jsonObj.getString("expectedCTCRatePer"))){
			 templateFields.setExpectedRatePer(jsonObj.getString("expectedCTCRatePer"));
		 }else {
			 templateFields.setExpectedRatePer("Monthly");
		 }
		 
		 if(jsonObj.has("NoticePeriod") && !isNullOrEmpty(jsonObj.getString("NoticePeriod"))){
			 templateFields.setNoticePeriod(jsonObj.getString("NoticePeriod"));
		 }
		 
		 templateFields.setStatus(Constants.CANDIDATE_ACTIVE);
		 if(user != null){
		 templateFields.setCreatedBy(user.getUserCode());
		 }
		 else{
			 templateFields.setCreatedBy(null);

		 }
			
			if(orgLocations == null){
				guestOrglocation=orgLocationService.getOrgLocations(requirement.getOrgLocationId().getId());
			}
			 if(orgLocations !=null){
		 templateFields.setCreatedDate(commonUtil.orgLocationCommonDate(date, orgLocations));
		 templateFields.setUpdatedDate(commonUtil.orgLocationCommonDate(date, orgLocations));
			 }
			 
			 else{
				 
				 templateFields.setCreatedDate(commonUtil.orgLocationCommonDate(date, guestOrglocation));
				 templateFields.setUpdatedDate(commonUtil.orgLocationCommonDate(date, guestOrglocation));
			 }
		 requirementSubmissionTemplateFieldsDao.storeRequirementSubmissionTemplateFields(templateFields);
		 
		 if(!isNullOrEmpty(loginUserRole) && loginUserRole.equalsIgnoreCase("ROLE_CROWDSOURCING_RECRUITER")){
				if(null == requirement.getAppliedRecruiters()){
					requirement.setAppliedRecruiters(user.getId().toString());
				}else{
					List<Long> userId = new ArrayList<Long>();
					for (String s : requirement.getAppliedRecruiters().split(",")){
						userId.add(Long.parseLong(s));
					 }
					boolean flag = userId.contains(user.getId());
	         		if(flag == false){
	         			StringBuilder appliedUser = new StringBuilder();
	         			appliedUser = appliedUser.append(",").append(user.getId().toString());
	    				requirement.setAppliedRecruiters(requirement.getAppliedRecruiters()+appliedUser.toString());
	         		}
				}
				requirementDao.storeRequirement(requirement);
		 }
	
		 if(jsonObj.has("notifyToApplicant") && !isNullOrEmpty(jsonObj.getString("notifyToApplicant")) && (!"Y".equalsIgnoreCase(requirementSubmission.getNeedApplicantPermit()))){
			 if(jsonObj.getString("notifyToApplicant").equals("Y")){
				 notifyToapplicantMailAboutRequirement(requirementSubmission.getId(),requirement,candidate,user,orgLocations,"");
			 }
		 }
		 
		 if("Y".equalsIgnoreCase(requirementSubmission.getNeedApplicantPermit()) && (jsonObj.has("notifyToApplicant") && !isNullOrEmpty(jsonObj.getString("notifyToApplicant"))) || ("Y".equalsIgnoreCase(requirementSubmission.getNeedApplicantPermit()))){
			 applicantReceiveMailAboutRequirement(requirementSubmission.getId(),requirement,candidate,user,orgLocations);
		 }
		 if(jsonObj.has("guestApplicant") && !isNullOrEmpty(jsonObj.getString("guestApplicant")) && ("guestApplicant".equalsIgnoreCase(jsonObj.getString("guestApplicant")))){
		 sendEmailToGuestUserForAppliedJob(candidate, requirement);
		 }
		return requirementSubmission;
		
	}
	
	
	public void sendEmailToGuestUserForAppliedJob(Candidate user, Requirement requirement) throws Exception {
		
		 
		 StringBuilder nameBuilder = new StringBuilder();
		 StringBuilder expBuilder = new StringBuilder();
		 StringBuilder locationBuilder = new StringBuilder();
		 EmailMessage emailMessage = new EmailMessage();
		 String guestUserName = null;
		 String experience = null;
		 String location = null;
		 
		 if(!isNullOrEmpty(user.getLastName())){
			 guestUserName = nameBuilder.append(user.getFirstName()).append(" ").append(user.getLastName()).toString();
		 }else{
			 guestUserName = nameBuilder.append(user.getFirstName()).toString();
		 }
		 
		 if(!isNullOrEmpty(requirement.getMinExp()) && !isNullOrEmpty(requirement.getMaxExp())){
			 experience = expBuilder.append(requirement.getMinExp()).append(" to ").append(requirement.getMaxExp()).append(" Years").toString();
		 }else if(!isNullOrEmpty(requirement.getMinExp())){
			 experience = expBuilder.append(requirement.getMinExp()).append(" Years").toString();
		 } else if(!isNullOrEmpty(requirement.getMaxExp())){
			 experience = expBuilder.append(requirement.getMaxExp()).append(" Years").toString();
		 }else{
			 experience = "Not Mentioned";
		 }
		 
		 if(requirement.getRequirementLocations() != null){
			 for(RequirementLocations reqLoc : requirement.getRequirementLocations()) {
				 locationBuilder.append(reqLoc.getCity()).append(", ");
			 }
			 location = locationBuilder.toString().replaceAll("\\s*,\\s*$", "");
		 }else {
			 location = "---";
		 }
		 
		 emailMessage.setToEmail(user.getEmail());
		 emailMessage.setFirstName(user.getFullName());
		 emailMessage.setFromName("Sourcelead Admin");
		 emailMessage.setCreatedBy("sladmin");
		 if (environment.equalsIgnoreCase("prod")) {
			 emailMessage.setFromEmail("info@sourcelead.com");
		 }else{
			 emailMessage.setFromEmail("qa@sourcelead.com");	 
		 }
		//setting the class filed values
		 Map<String, String> globalVarsMap = new HashMap<String, String>();
		 globalVarsMap.put("guestUserName", guestUserName);
		 globalVarsMap.put("employerName", requirement.getPostedOrgName());
		// globalVarsMap.put("requirementTitle", requirement.getJobTitle());
		 globalVarsMap.put("experience", experience);
		 globalVarsMap.put("location", location);
		 globalVarsMap.put("guestUserEmail", user.getEmail());
		 
		 if(requirement.getJobId() !=null && !"".equalsIgnoreCase(requirement.getJobId())){
			 StringBuilder reqsb=new StringBuilder();
			 
			 globalVarsMap.put("requirementTitle",reqsb.append(requirement.getJobId()).append("-").append(requirement.getJobTitle()).toString());
		 }
		 
		 else{
			 globalVarsMap.put("requirementTitle", requirement.getJobTitle()); 
			 
		 }
		 //setting the Recipient information
		 InternetAddress[] iAdressArray = InternetAddress.parse(emailMessage.getToEmail());
		
		 //getting the current EmailScenario by EmailScenario unique name and entity type
		 EmailScenario  emailScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.GUEST_APPLIED_TO_REQUIREMENT,Constants.ENTITY_TYPE_EMAIL);
		
		 //covert subject,body and signature
		 String subject = emailScenerioUtil.bulidTemplateSubject(emailScenario.getSubject(),globalVarsMap,emailScenario);
		 String body = emailScenerioUtil.buildTemplateBody(emailScenario.getBody(),globalVarsMap,emailScenario);
		 String signature = emailScenerioUtil.buildTemplateSignature(emailScenario.getSignature(),globalVarsMap,emailScenario);
		 String url = sourceleadUrl;
		 
		 //construct HTML template using required data
		 
		 String emailTemplate = emailScenerioUtil.AssembleEmailMessage(emailScenario,emailMessage, subject, body, signature, url, new Org(),new OrgLocations());
		 emailMessage.setSubject(subject);
		 emailMessage.setMessageBody(emailTemplate);
		 
		 //calling the sendEmail method of emailScenerioUtil
		 emailScenerioUtil.sendEmail(emailMessage,1L,iAdressArray,new ArrayList<EmailAttachment>());
		
	}
	

	private void notifyToapplicantMailAboutRequirement(Long submissionId ,Requirement requirement,  Candidate candidate ,User loginUser,OrgLocations orgLocations, String submissionType) throws Exception {
		RequirementSubmissionTemplateFields requirementSubmissionTemplateFields = requirementSubmissionTemplateFieldsService.getSubmissionsTemplateFieldsByRequirementSubmissionsId(submissionId);
		
		 EmailMessage emailMessage = new EmailMessage();
		 emailMessage.setFirstName(loginUser.getFirstName());
		 emailMessage.setLastName(loginUser.getLastName());
		 emailMessage.setToEmail(requirementSubmissionTemplateFields.getEmailId());
		 emailMessage.setFromEmail(loginUser.getEmail());
		 emailMessage.setCreatedBy(loginUser.getUserCode());
		 StringBuilder emailBuilder = new StringBuilder();
		 if(loginUser.getLastName()!=null && loginUser.getLastName()!= "" && loginUser.getLastName()!="undefined"){
		 emailMessage.setFromName(emailBuilder.append(loginUser.getFirstName()).append(" ").append(loginUser.getLastName()).toString());
		 }
		 else
		 {
			 emailMessage.setFromName(loginUser.getFirstName());
		 }
		 emailMessage.setEmailType(Constants.CUSTOM_USER_EMAIL);
		//setting the class filed values
		 Map<String, String> globalVarsMap = new HashMap<String, String>();
		 
		 if(null!= requirement.getJobTitle() && !"".equalsIgnoreCase(requirement.getJobTitle())){
			 globalVarsMap.put("requirementName", requirement.getJobTitle());
			 }else{
				 globalVarsMap.put("requirementName","--");
			 }
		 if(null!= orgLocations.getOrgLocationName() && !"".equalsIgnoreCase(orgLocations.getOrgLocationName())){
			 globalVarsMap.put("organizationName", orgLocations.getOrgLocationName());
			 }else{
				 globalVarsMap.put("organizationName","--");
			 }
		 if(null != requirementSubmissionTemplateFields.getFirstName() && null!= requirementSubmissionTemplateFields.getLastName() && !"".equalsIgnoreCase(requirementSubmissionTemplateFields.getFirstName()) && !"".equalsIgnoreCase(requirementSubmissionTemplateFields.getLastName())){
			 StringBuilder candidateBuild = new StringBuilder();
			 globalVarsMap.put("applicantName", candidateBuild.append(requirementSubmissionTemplateFields.getFirstName()).append(" ").append(requirementSubmissionTemplateFields.getLastName()).toString());
			 }else{
				 globalVarsMap.put("applicantName",requirementSubmissionTemplateFields.getFirstName());
			 }
		 if((null!= requirement.getMinExp() && !"".equalsIgnoreCase(requirement.getMinExp())) && (null!= requirement.getMaxExp() && !"".equalsIgnoreCase(requirement.getMaxExp()))){
			 StringBuilder expBuild = new StringBuilder();
			 globalVarsMap.put("experience",expBuild.append(requirement.getMinExp()).append("-").append(requirement.getMaxExp()).toString()); 
		 	 }else if(requirement.getMinExp()!= null && !"".equalsIgnoreCase(requirement.getMinExp())){
			 globalVarsMap.put("experience", requirement.getMinExp());
		 	 }else{
			 globalVarsMap.put("experience","--");
			 }
		 List<RequirementLocations> locationsList;
		 String reqLocation = "";
		 StringBuilder requirementBuilder=new StringBuilder();
			try {
				locationsList = requirementLocationsService.getRequirementLocationsListByRequirementId(requirement.getId());
				if(locationsList != null && !locationsList.isEmpty()) {
					for(RequirementLocations locationsJob : locationsList) {
						if(locationsJob.getLocation() !=null && locationsJob.getLocation() !=null){
							reqLocation = requirementBuilder.append(locationsJob.getLocation()).toString();
						}else{
							reqLocation = "--";
						}
					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 
			globalVarsMap.put("location",reqLocation);
			
		 if(null != requirementSubmissionTemplateFields.getExpectedCTC() && !"".equalsIgnoreCase(requirementSubmissionTemplateFields.getExpectedCTC()) && null != requirementSubmissionTemplateFields.getExpectedRatePer() && !"".equalsIgnoreCase(requirementSubmissionTemplateFields.getExpectedRatePer())){
			 StringBuilder ratebuild = new StringBuilder();
			 globalVarsMap.put("requirementRate", ratebuild.append(requirementSubmissionTemplateFields.getExpectedCTC()).append(" / ").append(requirementSubmissionTemplateFields.getExpectedRatePer()).toString());
			 }else{
				 globalVarsMap.put("requirementRate","--");
			 }
		 if(requirement.getSkills()!= null && !"".equalsIgnoreCase(requirement.getSkills())){
			 globalVarsMap.put("skills", requirement.getSkills());
			 }else{
				 globalVarsMap.put("skills","--");
			 }
		 if(null!= requirement.getDescription() && !"".equalsIgnoreCase(requirement.getDescription())){
			 globalVarsMap.put("jobDescription", requirement.getDescription());
			 }else{
				 globalVarsMap.put("jobDescription","--");
			 }
		 if((null!= loginUser.getFirstName() && !"".equalsIgnoreCase(loginUser.getFirstName())) && (null!= loginUser.getLastName() && !"".equalsIgnoreCase(loginUser.getLastName()))){
			 StringBuilder nameBuild = new StringBuilder();
			 globalVarsMap.put("loginUserName", nameBuild.append(loginUser.getFirstName()).append(" ").append(loginUser.getLastName()).toString());
			 }
		 else if(null!= loginUser.getFirstName() && !"".equalsIgnoreCase(loginUser.getFirstName())){
			 globalVarsMap.put("loginUserName", loginUser.getFirstName());
		 	}else{
			 globalVarsMap.put("loginUserName","--");
			 }                       
		 
		 if(requirement.getJobType()!= null && !"".equalsIgnoreCase(requirement.getJobType())){
			 globalVarsMap.put("jobType", requirement.getJobType());
			 }else{
				 globalVarsMap.put("jobType","--");
			 }
		 
		 if(requirement.getIndustryName()!= null && !"".equalsIgnoreCase(requirement.getIndustryName())){
			 globalVarsMap.put("industryName", requirement.getIndustryName());
			 }else{
				 globalVarsMap.put("industryName","--");
			 }
		 
		 if(requirement.getClientName()!= null && !"".equalsIgnoreCase(requirement.getClientName())){
			 globalVarsMap.put("clientName", requirement.getClientName());
			 }else{
				 globalVarsMap.put("clientName","--");
			 }
		 if(requirement.getVisaType()!= null && !"".equalsIgnoreCase(requirement.getVisaType())){
			 globalVarsMap.put("visaType", requirement.getVisaType());
			 }else{
				 globalVarsMap.put("visaType","--");
			 }
		 //setting the Recipient information
		 InternetAddress[] iAdressArray = InternetAddress.parse(emailMessage.getToEmail());
		
		 //getting the current EmailScenario by EmailScenario unique name and entity type
		 EmailScenario  emailScenario = null;
		 if(orgLocations != null)
			 emailScenario =  emailScenarioService.getEmailScenarioByUniqueNameAndOrgLocationId(Constants.NOTIFY_APPLICANT_MAILABOUT_REQUIREMENT,Constants.ENTITY_TYPE_EMAIL, orgLocations.getId());
			  if(emailScenario == null){
					emailScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.NOTIFY_APPLICANT_MAILABOUT_REQUIREMENT,Constants.ENTITY_TYPE_EMAIL);
			   }
		 //covert subject,body and signature
		 String subject = "";
		 if(submissionType.equalsIgnoreCase("EditSubmission")) {
			 subject = "Application details are updated";
		}else {
			subject = emailScenerioUtil.bulidTemplateSubject(emailScenario.getSubject(),globalVarsMap,emailScenario);
		}
		 //covert subject,body and signature
		 String body = emailScenerioUtil.buildTemplateBody(emailScenario.getBody(),globalVarsMap,emailScenario);
		 String signature = emailScenerioUtil.buildTemplateSignature(emailScenario.getSignature(),globalVarsMap,emailScenario);
		 String url = sourceleadUrl;
		 
		 //construct HTML template using required data
		 String emailTemplate = emailScenerioUtil.AssembleEmailMessage(emailScenario,emailMessage, subject, body, signature, url, orgLocations.getOrg(),orgLocations);
		 emailMessage.setSubject(subject);
		 emailMessage.setMessageBody(emailTemplate);
		 
		//calling the sendEmail method of emailScenerioUtil
		 emailScenerioUtil.sendEmail(emailMessage,orgLocations.getId(),iAdressArray,new ArrayList<EmailAttachment>());
	}

	

	@Override
	public List<RequirementSubmission> getSubmissionsByRequirementId(Long requirementId,User user, OrgLocations loginOrgLocation,String loginUserRole) throws Exception {
		return requirementSubmissionDao.getSubmissionsByRequirementId(requirementId,user, loginOrgLocation,loginUserRole);
	}

	@Override
	public List<RequirementSubmission> getAllSubmissionListByRequirementIdAndSubmissionStage(OrgLocations loginOrgLocation,
			Long requirementId,String submissionStatus,User user,String loginUserRole) throws Exception {
		return requirementSubmissionDao.getAllSubmissionListByRequirementIdAndSubmissionStage(loginOrgLocation, requirementId, submissionStatus,user,loginUserRole);
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
 		 cell4.setCellValue(loginOrgLocation.getOrgLocationName());
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
    		 clientSubmission.setSubmissionStatus(ApplicantSubmissionStatus.SUBMISSION.toString());
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
    		 requirementSubmissionHistory.setFromStage(submission.getSubmissionStatus());
    		 submission.setSubmissionStatus(ApplicantSubmissionStatus.SUBMIT_TO_CLIENT.toString());
    		 submission.setUpdatedBy(user.getUserCode());
    		 submission.setUpdatedDate(new Date());
    		 requirementSubmissionDao.storeRequirementSubmission(submission);
    		 
    		 requirementSubmissionHistoryService.updateRequirementHistory(submission, user, requirementSubmissionHistory, submission.getCreatedBy());
    		 String data = clientSubmission.getSubmissionTemplateJsonStringValue();
    		 try {
    		     // create the json array from String rules
    		     JSONArray jsonRules = new JSONArray(data);
    		     // iterate over the rules 
    		     writeRow = createExcelsheet.createRow(i+8);
    		     for (int j=0; j<jsonRules.length();j++){
    		         JSONObject obj = (JSONObject) jsonRules.get(j);
    		         if(obj.has("value") && obj.getString("value") !=null) {
    		        	 createXlsSheet(createExcelsheet,writeRow,j,obj.getString("value"));
    		         }
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
    				 managerId = requirement.getSpocId().getVendorUser().getId();
    			 }else{
    				 managerId = requirement.getSpocId().getClientUser().getId();
    			 }
    			 
    			 User requirementManager = userDao.getUserById(managerId);
    			 emailMessage.setFirstName(requirementManager.getFirstName());
    			 emailMessage.setLastName(requirementManager.getLastName());
    			 emailMessage.setToEmail(requirementManager.getEmail());
    		 }
    	
    	 emailMessage.setCreatedBy(user.getUserCode());
		 emailMessage.setFromEmail(user.getEmail());
		 emailMessage.setFromName(fullName);
		 
		 
		 Map<String, String> globalVarsMap = new HashMap<String, String>();
		 globalVarsMap.put("ClientFirstName", emailMessage.getFirstName());
		 globalVarsMap.put("ClientLastName",emailMessage.getLastName());
		 globalVarsMap.put("LoginUser", emailMessage.getFromName());
		 globalVarsMap.put("RequirementTitle", requirement.getJobTitle());
		 globalVarsMap.put("LoginOrgName",loginOrgLocation.getOrgLocationName());
		 globalVarsMap.put("LoginUser",fullName);
		 globalVarsMap.put("TotalNoOfApplicants", String.valueOf(submissionId.length));
		 
		 
		 
		 
		
		 ArrayList<EmailAttachment> attachmentList = new ArrayList<EmailAttachment>();
		 EmailAttachment emailAttachment = new EmailAttachment();
		 emailAttachment.setUUID(UUID);
		 attachmentList.add(emailAttachment);
		 
		commonUtil.sendTemplateEmail(emailMessage, globalVarsMap,loginOrgLocation.getOrg(),loginOrgLocation,Constants.SUBMIT_TO_CLIENT,attachmentList);

		
	}

	@Override
	public Integer getRequirementSubmissionsCountByOrgLocationsIdAndSubmissionStatus(Long orgLocationsId, String submissionStatus, String dateRange,String userType,User user, Date startUTCDate, Date endUTCDate) {
		return requirementSubmissionDao.getRequirementSubmissionsCountByOrgLocationsIdAndSubmissionStatus(orgLocationsId,submissionStatus,dateRange,userType,user,startUTCDate,endUTCDate);
	}

	//to get candidates for submit to client
	@Override
	public List<RequirementSubmission> getAllShortLitstedCandidatesForClientSubmissionByRequirementId(OrgLocations loginOrgLocation,
			Requirement requirement,User user,String loginUserRole) throws Exception {
		return requirementSubmissionDao.getAllShortLitstedCandidatesForClientSubmissionByRequirementId(loginOrgLocation, requirement,user,loginUserRole);
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
	public RequirementSubmissionsCountDTO getAllPipeLineCountsByRequirementIdAndSubmissionStage(Long requirementId, User user,
			OrgLocations loginOrgLocation, String submissionStatus) throws Exception{
		return requirementSubmissionDao.getAllPipeLineCountsByRequirementIdAndSubmissionStage(requirementId,user,loginOrgLocation,submissionStatus);
	}
	
	@Override
	public Map<String, Integer> getAllPipeLineWorlflowCountsByRequirementIdAndSubmissionStage(Long requirementId, User user,
			OrgLocations loginOrgLocation, String submissionStatus, String[] workflowStages) throws Exception{
		return requirementSubmissionDao.getAllPipeLineWorlflowCountsByRequirementIdAndSubmissionStage(requirementId,user,loginOrgLocation,submissionStatus, workflowStages);
	}
	
	@Override
	public Integer duplicateCandidateCountsByRequirementId(Long requirementId, User user,OrgLocations loginOrgLocation) throws Exception{
		CandidateVerificationConfig candidateVerificationConfig = candidateService.getCandidateVerificationConfig(loginOrgLocation.getId());
		return requirementSubmissionDao.duplicateCandidateCountsByRequirementId(requirementId,user,loginOrgLocation,candidateVerificationConfig);
	}
	
	@Override
	public Integer getRequirementSubmissionsCountByOrgLocationsIdAndRequirementId(OrgLocations orgLocations, Long requirementId,User user,String loginUserRole) throws Exception {
		return requirementSubmissionDao.getRequirementSubmissionsCountByOrgLocationsIdAndRequirementId(orgLocations, requirementId,user,loginUserRole);
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
		
		RequirementSubmissionTemplateFields requirementSubmissionTemplateFields = requirementSubmissionTemplateFieldsService.getSubmissionsTemplateFieldsByRequirementSubmissionsId(submissionId);
		
		 EmailMessage emailMessage = new EmailMessage();
		 emailMessage.setFirstName(loginUser.getFirstName());
		 emailMessage.setLastName(loginUser.getLastName());
		 emailMessage.setToEmail(requirementSubmissionTemplateFields.getEmailId());
		 emailMessage.setFromEmail(loginUser.getEmail());
		 emailMessage.setCreatedBy(loginUser.getUserCode());
		 StringBuilder emailBuilder = new StringBuilder();
		 emailMessage.setFromName(emailBuilder.append(loginUser.getFirstName()).append(" ").append(loginUser.getLastName()).toString());
		 emailMessage.setEmailType(Constants.CUSTOM_USER_EMAIL);
		//setting the class filed values
		 Map<String, String> globalVarsMap = new HashMap<String, String>();
		 
		 if(null!= requirement.getJobTitle() && !"".equalsIgnoreCase(requirement.getJobTitle())){
			 globalVarsMap.put("requirementName", requirement.getJobTitle());
			 }else{
				 globalVarsMap.put("requirementName","--");
			 }
		 if(null!= orgLocations.getOrgLocationName() && !"".equalsIgnoreCase(orgLocations.getOrgLocationName())){
			 globalVarsMap.put("organizationName", orgLocations.getOrgLocationName());
			 }else{
				 globalVarsMap.put("organizationName","--");
			 }
		 if(null != requirementSubmissionTemplateFields.getFirstName() && null!= requirementSubmissionTemplateFields.getLastName() && !"".equalsIgnoreCase(requirementSubmissionTemplateFields.getFirstName()) && !"".equalsIgnoreCase(requirementSubmissionTemplateFields.getLastName())){
			 StringBuilder candidateBuild = new StringBuilder();
			 globalVarsMap.put("applicantName", candidateBuild.append(requirementSubmissionTemplateFields.getFirstName()).append(" ").append(requirementSubmissionTemplateFields.getLastName()).toString());
			 }else{
				 globalVarsMap.put("applicantName",requirementSubmissionTemplateFields.getFirstName());
			 }
		 if((null!= requirement.getMinExp() && !"".equalsIgnoreCase(requirement.getMinExp())) && (null!= requirement.getMaxExp() && !"".equalsIgnoreCase(requirement.getMaxExp()))){
			 StringBuilder expBuild = new StringBuilder();
			 globalVarsMap.put("experience",expBuild.append(requirement.getMinExp()).append("-").append(requirement.getMaxExp()).toString()); 
		 	 }else if(requirement.getMinExp()!= null && !"".equalsIgnoreCase(requirement.getMinExp())){
			 globalVarsMap.put("experience", requirement.getMinExp());
		 	 }else{
			 globalVarsMap.put("experience","--");
			 }
		 
		 List<RequirementLocations> reqLocationList = requirementLocationsService.getRequirementLocationsListByRequirementId(requirement.getId());
		 if(reqLocationList != null) {
			 StringBuilder locBuilder = new StringBuilder();
			 for(RequirementLocations reqLocations : reqLocationList) {
				 locBuilder.append(reqLocations.getLocation()).append("<br>");
			 }
			 globalVarsMap.put("location",locBuilder.toString());
		 }else {
			 globalVarsMap.put("location","--");
		 }
		 
		 
		 if(null != requirementSubmissionTemplateFields.getExpectedCTC() && !"".equalsIgnoreCase(requirementSubmissionTemplateFields.getExpectedCTC()) && null != requirementSubmissionTemplateFields.getExpectedRatePer() && !"".equalsIgnoreCase(requirementSubmissionTemplateFields.getExpectedRatePer())){
			 StringBuilder ratebuild = new StringBuilder();
			 globalVarsMap.put("requirementRate", ratebuild.append(requirementSubmissionTemplateFields.getExpectedRateType()).append(" ").append(requirementSubmissionTemplateFields.getExpectedCTC()).append(" / ").append(requirementSubmissionTemplateFields.getExpectedRatePer()).toString());
			 }else{
				 globalVarsMap.put("requirementRate","--");
			 }
		 if(requirement.getSkills()!= null && !"".equalsIgnoreCase(requirement.getSkills())){
			 globalVarsMap.put("skills", requirement.getSkills());
			 }else{
				 globalVarsMap.put("skills","--");
			 }
		 if(null!= requirement.getDescription() && !"".equalsIgnoreCase(requirement.getDescription())){
			 globalVarsMap.put("jobDescription", requirement.getDescription());
			 }else{
				 globalVarsMap.put("jobDescription","--");
			 }
		 if((null!= loginUser.getFirstName() && !"".equalsIgnoreCase(loginUser.getFirstName())) && (null!= loginUser.getLastName() && !"".equalsIgnoreCase(loginUser.getLastName()))){
			 StringBuilder nameBuild = new StringBuilder();
			 globalVarsMap.put("loginUserName", nameBuild.append(loginUser.getFirstName()).append(" ").append(loginUser.getLastName()).toString());
			 }
		 else if(null!= loginUser.getFirstName() && !"".equalsIgnoreCase(loginUser.getFirstName())){
			 globalVarsMap.put("loginUserName", loginUser.getFirstName());
		 	}else{
			 globalVarsMap.put("loginUserName","--");
			 }                       
		
		 StringBuilder acceptBuilder = new StringBuilder();
		 StringBuilder rejectBuilder = new StringBuilder();
		 StringBuilder negotiateBuilder = new StringBuilder();
		 
		 globalVarsMap.put("acceptLink",acceptBuilder.append("<a href='").append(atsUrl).append("/").append("ws/acceptOrRejectRequirement?rateStatus=Accepted&submissionId=").append(submissionId).append("'style='text-decoration:none;float:left; margin-left:15px;'><div style=' width:150px; border-radius:30px; background-color:#5cb85c; color:#FFF; font-size:16px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Accept</div></a>").toString());
	        
	     globalVarsMap.put("rejectLink",rejectBuilder.append("<a href='").append(atsUrl).append("/").append("ws/acceptOrRejectRequirement?rateStatus=RejectedApplicant&submissionId=").append(submissionId).append("'style='text-decoration:none;float:left;margin-left:15px;'><div style=' width:150px; border-radius:30px; background-color:#e74c51; color:#FFF; font-size:16px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Reject</div></a>").toString());

	     
	     if(null != requirementSubmissionTemplateFields.getExpectedCTC() && !"".equalsIgnoreCase(requirementSubmissionTemplateFields.getExpectedCTC()) && null != requirementSubmissionTemplateFields.getExpectedRatePer() && !"".equalsIgnoreCase(requirementSubmissionTemplateFields.getExpectedRatePer())){
	     
	     globalVarsMap.put("negotiateRateLink",negotiateBuilder.append("<a href='").append(atsUrl).append("/").append("ws/acceptOrRejectOrRateNegotiateApplicantRates?rateStatus=Negotiate&submissionId=").append(submissionId).append("'style='text-decoration:none;float:left;margin-left:15px;'><div style='width:200px; border-radius:30px; background-color:#f0ad4e;; color:#FFF; font-size:16px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Negotiate Rate</div></a>").toString());

	     }
	        
		commonUtil.sendTemplateEmail(emailMessage, globalVarsMap,orgLocations.getOrg(),orgLocations,Constants.APPLICANT_RECEIVE_MAILABOUT_REQUIREMENT_EMAIL,new ArrayList<EmailAttachment>());

	}

	@Override
	public void applicantAcceptMailAboutRequirement(Requirement requirement, User initiatorUser,RequirementSubmission requirementSubmission,
			OrgLocations orgLocations) throws Exception {
		
		Candidate candidate = requirementSubmission.getCandidate(); 
		 EmailMessage emailMessage = new EmailMessage();
		 emailMessage.setFirstName(initiatorUser.getFirstName());
		 emailMessage.setLastName(initiatorUser.getLastName());
		 emailMessage.setToEmail(initiatorUser.getEmail());
		 emailMessage.setFromEmail(candidate.getEmail());
		 emailMessage.setCreatedBy(initiatorUser.getUserCode());
		 //URL for page redirection
		//String redirectURL = "/requirementSummary-params-requirementUUID="+requirement.getRequirementUUID();
		String redirectURL = "/requirementSummary-params-type=APPLICANTS-and-requirementUUID="+requirement.getRequirementUUID()+"-and-candidateUUID="+candidate.getCandidateUUID()+"-and-submissionId="+requirementSubmission.getId();
		Long redirectOrgLocationId = requirement.getOrgLocationId().getId();
		
		 if(candidate.getFirstName() != null && candidate.getLastName()!= null && !"".equalsIgnoreCase(candidate.getFirstName()) && !"".equalsIgnoreCase(candidate.getLastName())){
			 StringBuilder msgBuild = new StringBuilder();
			 emailMessage.setFromName(msgBuild.append(candidate.getFirstName()).append(" ").append(candidate.getLastName()).toString());
		 }else{
			 emailMessage.setFromName(candidate.getFirstName());
		 }
		 emailMessage.setEmailType(Constants.CUSTOM_USER_EMAIL);
		//setting the class filed values
		 Map<String, String> globalVarsMap = new HashMap<String, String>();

		 if(initiatorUser.getFirstName()!= null && !"".equalsIgnoreCase(initiatorUser.getFirstName())){
			 globalVarsMap.put("initiatorUserName", initiatorUser.getFirstName());
			 }else{
				 globalVarsMap.put("initiatorUserName","--");
			 }
		 
		 if(requirement.getJobId()!= null && !"".equalsIgnoreCase(requirement.getJobId())){
			 
			 StringBuilder reqsb=new StringBuilder();
			 
			 globalVarsMap.put("requirementName",reqsb.append(requirement.getJobId()).append("-").append(requirement.getJobTitle()).toString());
			 }else{
				 globalVarsMap.put("requirementName", requirement.getJobTitle());
			 }
		
		 if(candidate.getFirstName() != null && candidate.getLastName()!= null && !"".equalsIgnoreCase(candidate.getFirstName()) && !"".equalsIgnoreCase(candidate.getLastName())){
			 StringBuilder candBuild = new StringBuilder();
			 globalVarsMap.put("applicantName", candBuild.append(candidate.getFirstName()).append(" ").append(candidate.getLastName()).toString());
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
		 
		 
		 List<String> requirementLocationsList = new ArrayList<>();
			List<RequirementLocations> locationsList;
			String location = "";
			StringBuilder requirementBuilder=new StringBuilder();
			try {
				locationsList = requirementLocationsService.getRequirementLocationsListByRequirementId(requirement.getId());
				if(locationsList != null && !locationsList.isEmpty()) {
					for(RequirementLocations locationsJob : locationsList) {
						requirementLocationsList.add(locationsJob.getLocation());
						if(locationsJob.getLocation() !=null && locationsJob.getLocation() !=null){
							location = requirementBuilder.append(locationsJob.getLocation()).toString();
						}else{
							location = "--";
						}
					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			globalVarsMap.put("location", location);
	
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
			
		 StringBuilder linkBuild = new StringBuilder();
			globalVarsMap.put("loginURL", linkBuild.append("<a href='").append(sourceleadLoginUrl+ "?redirectURL=" +atsUrl+redirectURL+"&redirectOrgLocationId="+redirectOrgLocationId).append(
					"' style='text-decoration:none; '><div style=' width:220px; border-radius:30px; background-color:#214b90; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>View Applicant</div></a>")
					.toString());
		
		 //getting the current EmailScenario by EmailScenario unique name and entity type
		 EmailScenario  notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.APPLICANT_ACCEPT_MAILABOUT_REQUIREMENT_EMAIL_NOTIFICATION,Constants.ENTITY_TYPE_NOTIFICATION);

		
		 
		String notificationbody = emailScenerioUtil.buildTemplateBody(notificationScenario.getBody(),globalVarsMap,notificationScenario);
		notificationScenario.setBody(notificationbody);
		 
		  
		 User fromUser = userService.getUserById(Constants.USERID);
		 
		 StringBuilder actionLinkBuilder = new StringBuilder();
	      // String actionLink =actionLinkBuilder.append("").append(sourceleadUrl).append("/").append("requirementSummary?type=APPLICANTRATE&requirementUUID="+requirement.getRequirementUUID()+"&candidateUUID="+requirement.getcgetCandidate().getCandidateUUID()+"&submissionId="+requirement.getSubmissionId()).toString();//calling the sendEmail method of emailScenerioUtil
		// emailScenerioUtil.sendNotification(actionLink, fromUser,initiatorUser, orgLocations, notificationScenario);
		commonUtil.sendTemplateEmail(emailMessage, globalVarsMap,orgLocations.getOrg(),orgLocations,Constants.APPLICANT_ACCEPT_MAILABOUT_REQUIREMENT_EMAIL_NOTIFICATION,new ArrayList<EmailAttachment>());


	}

	@Override
	public void applicantRejectMailAboutRequirement(Requirement requirement, User initiatorUser, Candidate candidate,
			OrgLocations orgLocations,String rejectreason, StringBuilder tempHTML, RequirementRateSubmissionNegotiation requirementRateSubmissionNegotiation, Long submissionId) throws Exception {
		
		RequirementSubmissionTemplateFields requirementSubmissionTemplateFields = requirementSubmissionTemplateFieldsService.getSubmissionsTemplateFieldsByRequirementSubmissionsId(submissionId);
		 EmailMessage emailMessage = new EmailMessage();
		 emailMessage.setFirstName(initiatorUser.getFirstName());
		 emailMessage.setLastName(initiatorUser.getLastName());
		 emailMessage.setToEmail(initiatorUser.getEmail());
		 emailMessage.setFromEmail(requirementSubmissionTemplateFields.getEmailId());
		 emailMessage.setCreatedBy(initiatorUser.getUserCode());
		 
		 //URL for page redirection
		 String redirectURL = "/requirementSummary-params-type=APPLICANTS-and-requirementUUID="+requirement.getRequirementUUID()+"-and-submissionId="+requirementSubmissionTemplateFields.getRequirementSubmission().getId()+"-and-candidateUUID="+requirementSubmissionTemplateFields.getRequirementSubmission().getCandidate().getCandidateUUID()+"-and-submissionStatus=all";	 
		 Long redirectOrgLocationId = requirement.getOrgLocationId().getId();
		 
		 if(requirementSubmissionTemplateFields.getFirstName() != null && requirementSubmissionTemplateFields.getLastName()!= null && !"".equalsIgnoreCase(requirementSubmissionTemplateFields.getFirstName()) && !"".equalsIgnoreCase(requirementSubmissionTemplateFields.getLastName())){
			 StringBuilder emailBuild = new StringBuilder();
			 emailMessage.setFromName(emailBuild.append(requirementSubmissionTemplateFields.getFirstName()).append(" ").append(requirementSubmissionTemplateFields.getLastName()).toString());
		 }else{
			 emailMessage.setFromName(requirementSubmissionTemplateFields.getFirstName());
		 }
		 emailMessage.setEmailType(Constants.CUSTOM_USER_EMAIL);
		 
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
		
		 if(requirementSubmissionTemplateFields.getFirstName() != null && requirementSubmissionTemplateFields.getLastName()!= null && !"".equalsIgnoreCase(requirementSubmissionTemplateFields.getFirstName()) && !"".equalsIgnoreCase(requirementSubmissionTemplateFields.getLastName())){
			 StringBuilder candiBuild = new StringBuilder();
			 globalVarsMap.put("applicantName", candiBuild.append(requirementSubmissionTemplateFields.getFirstName()).append(" ").append(requirementSubmissionTemplateFields.getLastName()).toString());
			 }else{
				 globalVarsMap.put("applicantName",requirementSubmissionTemplateFields.getFirstName());
			 }
		 if(null != requirementRateSubmissionNegotiation){
		 if(null != requirement.getCurrencyCode() && requirementRateSubmissionNegotiation.getExpectedRate()!= null && !"".equalsIgnoreCase(requirementRateSubmissionNegotiation.getExpectedRate()) && null != requirementRateSubmissionNegotiation.getExpectedRateType() && !"".equalsIgnoreCase(requirementRateSubmissionNegotiation.getExpectedRateType())){
			 StringBuilder rateBuild = new StringBuilder();
			 globalVarsMap.put("requirementRate", rateBuild.append(requirement.getCurrencyCode()).append("-").append(requirementRateSubmissionNegotiation.getExpectedRate()).append("-").append(requirementRateSubmissionNegotiation.getExpectedRateType()).toString());
			 }else if(null != requirement.getCurrencyCode() && null != requirementSubmissionTemplateFields.getExpectedCTC() && !"".equalsIgnoreCase(requirementSubmissionTemplateFields.getExpectedCTC()) && null != requirementSubmissionTemplateFields.getExpectedRatePer() && !"".equalsIgnoreCase(requirementSubmissionTemplateFields.getExpectedRatePer())){
				 StringBuilder rateBuild = new StringBuilder();
				 globalVarsMap.put("requirementRate",rateBuild.append(requirement.getCurrencyCode()).append(requirementSubmissionTemplateFields.getExpectedCTC()).append(" / ").append(requirementSubmissionTemplateFields.getExpectedRatePer()).toString());
			 }else{
				 globalVarsMap.put("requirementRate","---");
			 }
		 }
		 
		 if(rejectreason != null && !"".equalsIgnoreCase(rejectreason)){
			 globalVarsMap.put("rejectReason",rejectreason);
			 }else{
				 globalVarsMap.put("rejectReason","--");
			 }
		 
		 if(null !=tempHTML){
		 globalVarsMap.put("negotiationLink", tempHTML.toString());
		 }
		 
		 StringBuilder linkBuild = new StringBuilder();
		 globalVarsMap.put("loginURL", linkBuild.append("<a href='").append(sourceleadLoginUrl+ "?redirectURL=" +atsUrl+redirectURL+"&redirectOrgLocationId="+redirectOrgLocationId).append(
					"' style='text-decoration:none; '><div style=' width:220px; border-radius:30px; background-color:#214b90; color:#FFF; font-size:18px; font-weight:500; margin:50px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>View Applicant</div></a>")
					.toString());	 
		 //getting the current EmailScenario by EmailScenario unique name and entity type
		 EmailScenario  notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.APPLICANT_REJECT_MAILABOUT_REQUIREMENT_EMAIL_NOTIFICATION,Constants.ENTITY_TYPE_NOTIFICATION);

		
		 
		String notificationbody = emailScenerioUtil.buildTemplateBody(notificationScenario.getBody(),globalVarsMap,notificationScenario);
		notificationScenario.setBody(notificationbody);
		 
		  
		 User fromUser = userService.getUserById(Constants.USERID);
		 
		 StringBuilder actionLinkBuilder = new StringBuilder();
		 String actionLink = actionLinkBuilder.append("").append(atsUrl).append("/").append("requirementSummary?requirementUUID="+requirement.getRequirementUUID()+"&submissionId="+requirementSubmissionTemplateFields.getRequirementSubmission().getId()+"&candidateUUID="+requirementSubmissionTemplateFields.getRequirementSubmission().getCandidate().getCandidateUUID()+"&submissionStatus=all").toString();		 

		 //calling the sendEmail method of emailScenerioUtil
		 emailScenerioUtil.sendNotification(actionLink, fromUser,initiatorUser, orgLocations, notificationScenario,new HashMap<String,String>());
		 commonUtil.sendTemplateEmail(emailMessage, globalVarsMap,orgLocations.getOrg(),orgLocations,Constants.APPLICANT_REJECT_MAILABOUT_REQUIREMENT_EMAIL_NOTIFICATION,new ArrayList<EmailAttachment>());
	}
	
	
	@Override
	public void applicantNegotiateEmail(Requirement requirement, User initiatorUser, Candidate candidate,
			OrgLocations orgLocations,RequirementRateSubmissionNegotiation negotiation,StringBuilder tempHTML) throws Exception {
		
		RequirementSubmissionTemplateFields requirementSubmissionTemplateFields = requirementSubmissionTemplateFieldsService.getSubmissionsTemplateFieldsByRequirementSubmissionsId(negotiation.getRequirementsubmissionId().getId());
		 EmailMessage emailMessage = new EmailMessage();
		 emailMessage.setFirstName(initiatorUser.getFirstName());
		 emailMessage.setLastName(initiatorUser.getLastName());
		 emailMessage.setToEmail(initiatorUser.getEmail());
		 emailMessage.setFromEmail(requirementSubmissionTemplateFields.getEmailId());
		 emailMessage.setCreatedBy(initiatorUser.getUserCode());
		 if(null != requirementSubmissionTemplateFields.getFirstName() && null != requirementSubmissionTemplateFields.getLastName() && !"".equalsIgnoreCase(requirementSubmissionTemplateFields.getFirstName()) && !"".equalsIgnoreCase(requirementSubmissionTemplateFields.getLastName())){
			 StringBuilder msgBuild = new StringBuilder();
			 emailMessage.setFromName(msgBuild.append(requirementSubmissionTemplateFields.getFirstName()).append(" ").append(requirementSubmissionTemplateFields.getLastName()).toString());
		 }else{
			 emailMessage.setFromName(requirementSubmissionTemplateFields.getFirstName());
		 }
		 emailMessage.setEmailType(Constants.CUSTOM_USER_EMAIL);
		//setting the class field values
		 Map<String, String> globalVarsMap = new HashMap<String, String>();
		 
		 if(null != initiatorUser.getFirstName() && !"".equalsIgnoreCase(initiatorUser.getFirstName())){
			 globalVarsMap.put("initiatorUserName", initiatorUser.getFirstName());
			 }else{
				 globalVarsMap.put("initiatorUserName","--");
			 }
		 
		 if(null != requirement.getJobId() && !"".equalsIgnoreCase(requirement.getJobId())){
			 StringBuilder reqsb=new StringBuilder();
			 globalVarsMap.put("requirementName",reqsb.append(requirement.getJobId()).append("-").append(requirement.getJobTitle()).toString());
			 }else{
				 globalVarsMap.put("requirementName", requirement.getJobTitle()); 
			 }
		
		 if(null != requirementSubmissionTemplateFields.getFirstName() && null != requirementSubmissionTemplateFields.getLastName() && !"".equalsIgnoreCase(requirementSubmissionTemplateFields.getFirstName()) && !"".equalsIgnoreCase(requirementSubmissionTemplateFields.getLastName())){
			 StringBuilder sb = new StringBuilder();
			 globalVarsMap.put("applicantName", sb.append(requirementSubmissionTemplateFields.getFirstName()).append(" ").append(requirementSubmissionTemplateFields.getLastName()).toString());
			 }else{
				 globalVarsMap.put("applicantName",requirementSubmissionTemplateFields.getFirstName());
			 }
		 if(null != requirement.getRate() && !"".equalsIgnoreCase(requirement.getRate())){
			 StringBuilder sb = new StringBuilder();
			 globalVarsMap.put("requirementRate", sb.append(requirement.getCurrencyCode()).append("-").append(requirement.getRate()).append("-").append(requirement.getRateType()).toString());
			 }else{
				 globalVarsMap.put("requirementRate","--");
			 }
		 if(null != negotiation.getCurrentRate() && !"".equalsIgnoreCase(negotiation.getCurrentRate())){
			 StringBuilder sb = new StringBuilder();
			 globalVarsMap.put("applicantRate",sb.append(negotiation.getCurrentRate()).append(" ").append(negotiation.getCurrentRateType()).toString());
			 }else{
				 globalVarsMap.put("applicantRate","--");
			 }
		 if(null != tempHTML){
			 globalVarsMap.put("negotiationTable",tempHTML.toString());
		 }
		 
		
		 //getting the current EmailScenario by EmailScenario unique name and entity type
		 EmailScenario  notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.APPLICANT_NEGOTIATE_REQUIREMENT,Constants.ENTITY_TYPE_NOTIFICATION);

		 
		String notificationbody = emailScenerioUtil.buildTemplateBody(notificationScenario.getBody(),globalVarsMap,notificationScenario);
		notificationScenario.setBody(notificationbody);
		 
		  
		 User fromUser = userService.getUserById(Constants.USERID);
		 
		 StringBuilder actionLinkBuilder = new StringBuilder();
		 String actionLink = actionLinkBuilder.append("").append(atsUrl).append("/").append("requirementSummary?type=all&requirementUUID="+requirement.getRequirementUUID()).toString();		 
		 
		//calling the sendEmail method of emailScenerioUtil
		 emailScenerioUtil.sendNotification(actionLink, fromUser,initiatorUser, orgLocations, notificationScenario,new HashMap<String,String>());
		 commonUtil.sendTemplateEmail(emailMessage, globalVarsMap,orgLocations.getOrg(),orgLocations,Constants.APPLICANT_NEGOTIATE_REQUIREMENT,new ArrayList<EmailAttachment>());
	}
	
	@Override
	public RequirementSubmission acceptRejectCandidate(ApplicantAuthorizationDTO  applicantAuthorizationDTO,User user,
			RequirementSubmission requirementSubmission, String decision,Org loginOrg,  OrgLocations loginOrgLocation) throws Exception {
		if(requirementSubmission != null){
			
			if(StringUtils.equalsIgnoreCase(decision, "accept")){
				requirementSubmission.setSubmissionStatus(ApplicantSubmissionStatus.SUBMISSION.toString());
				requirementSubmission.setSubmissionRateStatus(SubmissionRateStatus.ACCEPTED);
				if(StringUtils.isNotBlank(applicantAuthorizationDTO.getComment())){
					requirementSubmission.setSubmissionRateComment(applicantAuthorizationDTO.getComment());
				}
				if(applicantAuthorizationDTO.getFileInfo() != null){
					FileInfo info = applicantAuthorizationDTO.getFileInfo().get(0);
					//requirementSubmission.setSubmissionRateDocFile(multiFile.getBytes());
					requirementSubmission.setSubmissionRateFileContentType(info.getContentType());
					requirementSubmission.setSubmissionRateFileName(info.getFileName());
					requirementSubmission.setDocURL(info.getFileURL());
					FileUploadUtil.moveFile(applicantAuthorizationDTO.getStaticPath(), info, "requestDocuments");
				}
				StringBuilder name = new StringBuilder();
				if(null !=user.getLastName() && !" ".equalsIgnoreCase(user.getLastName())){
					requirementSubmission.setSubmissionRequestUser(name.append(user.getFirstName()).append(" ").append(user.getLastName()).toString());
				}else{
					requirementSubmission.setSubmissionRequestUser(user.getFirstName());
				}
				
				sendEmailtoApplicant(loginOrg, user,requirementSubmission,loginOrgLocation);
				sendEmailToAssignedMember(loginOrg, user,requirementSubmission,loginOrgLocation);
			}
			else if(StringUtils.equalsIgnoreCase(decision, "reject")){
				
				String previousStatus = requirementSubmission.getSubmissionStatus();
				 
				requirementSubmission.setPreviousSubmissionStatus(previousStatus);
				requirementSubmission.setSubmissionStatus(ApplicantSubmissionStatus.REJECTED.toString());
				requirementSubmission.setSubmissionRateStatus(SubmissionRateStatus.REJECTED);
				if(StringUtils.isNotBlank(applicantAuthorizationDTO.getComment())){
					requirementSubmission.setRejectedReason(applicantAuthorizationDTO.getComment());
				}
				
				
				
				if(StringUtils.isNotBlank(applicantAuthorizationDTO.getRejectedCategory())){
					requirementSubmission.setRejectedCategoryType(applicantAuthorizationDTO.getRejectedCategory());
				}
				
				
				if(StringUtils.isNotBlank(applicantAuthorizationDTO.getRejectedCategoryOther())){
					requirementSubmission.setRejectedCategoryOther(applicantAuthorizationDTO.getRejectedCategoryOther());

				}
				
				
				StringBuilder name = new StringBuilder();
				if(null !=user.getLastName() && !" ".equalsIgnoreCase(user.getLastName())){
					requirementSubmission.setSubmissionRequestUser(name.append(user.getFirstName()).append(" ").append(user.getLastName()).toString());
				}else{
					requirementSubmission.setSubmissionRequestUser(user.getFirstName());
				}
			}
			else if(StringUtils.equalsIgnoreCase(decision, "request")){
				requirementSubmission.setSubmissionRateStatus(SubmissionRateStatus.REQUEST);
				if(applicantAuthorizationDTO.getFileInfo() != null){
					FileInfo info = applicantAuthorizationDTO.getFileInfo().get(0);
					//requirementSubmission.setSubmissionRateDocFile(multiFile.getBytes());
					requirementSubmission.setSubmissionRequestFileContentType(info.getContentType());
					requirementSubmission.setSubmissionRequestFileName(info.getFileName());
					requirementSubmission.setDocURL(info.getFileURL());
					FileUploadUtil.moveFile(applicantAuthorizationDTO.getStaticPath(), info, "requestDocuments");
				}
				if(StringUtils.isNotBlank(applicantAuthorizationDTO.getComment())){
					requirementSubmission.setSubmissionRequestComment(applicantAuthorizationDTO.getComment());
				}
				StringBuilder name = new StringBuilder();
				if(null !=user.getLastName() && !" ".equalsIgnoreCase(user.getLastName())){
					requirementSubmission.setSubmissionRequestUser(name.append(user.getFirstName()).append(" ").append(user.getLastName()).toString());
				}else{
					requirementSubmission.setSubmissionRequestUser(user.getFirstName());
				}
				
				//send mail to requirement manager.
				sendEmailtoRequirementManager(loginOrg, user,requirementSubmission,loginOrgLocation);
				
				
			}
			requirementSubmission.setUpdatedBy(user.getUserCode());
			requirementSubmission.setUpdatedDate(commonUtil.currentDate());
			
		}
		requirementSubmission.setUpdatedDate(commonUtil.currentDate());
		requirementSubmissionDao.storeRequirementSubmission(requirementSubmission);
		
		if(StringUtils.equalsIgnoreCase(decision, "accept")){
			RequirementSubmissionHistory requirementSubmissionHistory = new RequirementSubmissionHistory();
	           requirementSubmissionHistory.setFromStage("request_sent");
	           requirementSubmissionHistoryService.updateRequirementHistory(requirementSubmission, user, requirementSubmissionHistory, requirementSubmission.getCreatedBy());
		}
		
		return requirementSubmission;
	}
	

	@Override
	public RequirementRateSubmissionNegotiation getRequirementRateBySubmissionById(Long submissionId) {
		return requirementSubmissionDao.getRequirementRateBySubmissionById(submissionId);
	}

	@Override
	public List<RequirementSubmission> getSubmissionsByRequirementIdandStatus(Long requirementId, User user,
			OrgLocations loginOrgLocation, String submissionType,String loginUserRole) throws Exception {
		return requirementSubmissionDao.getSubmissionsByRequirementIdandStatus(requirementId,user,loginOrgLocation,submissionType,loginUserRole);
	}

	@Override
	public void storeClientSubmissionWithEmailPreview(Long requirementId, User user, OrgLocations loginOrgLocation,String submissionIds, Org org, JSONObject jsonData,Map files,String selectedShortlistsRate,String selectedShortlistsRatePer) throws Exception {
		 String requirementName = null;
		 String fullName = null;
		 String[] submissionId = submissionIds.split(",");
		 String[] submissionratePer = selectedShortlistsRatePer.split(",");
		 String[] clientShortlistsRate = selectedShortlistsRate.split(",");
   	     Requirement requirement = requirementDao.getRequirementByRequirementId(requirementId);
		 requirement.setId(requirementId);
		 Long redirectOrgLocationId = requirement.getClientId().getLocationId().getId();
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
   	Sheet createExcelsheet = createSubmissionTracker(org,fullName,submissionId,requirementName,createExcelworkbook,writeRow,dateFormat,loginOrgLocation);
   	 for(int i=0; i < submissionId.length; i++){
   		 RequirementSubmission submission = requirementSubmissionDao.getRequirementSubmissionById(Long.parseLong(submissionId[i]));
   		 RequirementSubmissionTemplateFields templateFields = requirementSubmissionTemplateFieldsDao.getSubmissionsTemplateFieldsByRequirementSubmissionsId(submission.getId());
   		 RequirementSubmission clientSubmission = new RequirementSubmission();
   		Requirement parentrequirement = null;
   		 
   		 if(requirement.getParentRequirement() != null && requirement.getIsClientJob() != null &&  requirement.getIsClientJob().equalsIgnoreCase("Y")){
   			 clientSubmission.setRequirement(requirement.getParentRequirement());
   			 clientSubmission.setOrgLocations(requirement.getParentRequirement().getOrgLocationId());
   			parentrequirement = requirementDao.getRequirementByRequirementId(requirement.getParentRequirement().getId());
       	 }
   		 else if(requirement.getParentRequirement() == null && requirement.getIsClientJob() != null &&  requirement.getIsClientJob().equalsIgnoreCase("Y")){
   			// clientSubmission.setRequirement(requirement);
   			 if(requirement.getClientId().getResponderType().equalsIgnoreCase("Client")){
   				clientSubmission.setOrgLocations(requirement.getClientId().getResponderLocationId());
   			 }else if(requirement.getClientId().getResponderType().equalsIgnoreCase("Vendor")){
   				clientSubmission.setOrgLocations(requirement.getClientId().getLocationId());
   			 }
   			 
       	 }
   		 
   		 clientSubmission.setCandidate(submission.getCandidate());
   		 clientSubmission.setCandidateResumes(submission.getCandidateResumes());
   		 clientSubmission.setSourceSubmission(submission);
   		 clientSubmission.setSubmissionTemplateJsonString(submission.getSubmissionTemplateJsonString());
   		 JSONArray array = new JSONArray(submission.getSubmissionTemplateJsonStringValue()); 
   		 JSONArray jsonArray = new JSONArray();
   		 
   		 for(int j=0; j<array.length(); j++){
   			JSONObject jsonObj = new JSONObject();
   			jsonObj =  array.getJSONObject(j);
   			if(jsonObj.has("name")){
	   			if(jsonObj.getString("name").equalsIgnoreCase("CurrentCTC")){
	   			 jsonObj.put("value", "-----");
	   			}
	   			else if(jsonObj.getString("name").equalsIgnoreCase("ExpectedCTC")){
	   				if(clientShortlistsRate != null && clientShortlistsRate.length>i && !isNullOrEmpty(clientShortlistsRate[i])){
	   					String clientRate = clientShortlistsRate[i];
	   					String submissionPer = "";
	   					if(submissionratePer.length > 0 && !isNullOrEmpty(submissionratePer[i])){
	   		  			 submissionPer = submissionratePer[i]; 
	   		  		 }
	   	   				if(!isNullOrEmpty(clientRate)){
	   	   				    StringBuilder rateBuilder = new StringBuilder();
	   	   				    rateBuilder = rateBuilder.append(templateFields.getExpectedRateType()).append(" ").append(clientRate).append(" / ").append(submissionPer);
	   	   					jsonObj.put("value", rateBuilder.toString());
	   	   				}else{
	   	   					jsonObj.put("value", "");
	   	   				}
	   				}
	  				
	  			 }
	   			else{
	   				if(jsonObj.has("value") && StringUtils.isNotBlank(jsonObj.getString("value"))) {
	   					jsonObj.put("value", jsonObj.getString("value"));
	   				}
	   			}
   			}
   			jsonArray.put(jsonObj);
   				 
   		 }
   		 
   		 
   		 clientSubmission.setSubmissionTemplateJsonStringValue(jsonArray.toString());
   		 
   		 StringBuilder sb = new StringBuilder();
   		 clientSubmission.setScore(submission.getScore());
   		 clientSubmission.setSubmissionStatus(ApplicantSubmissionStatus.SUBMISSION.toString());
   		 clientSubmission.setVendorLocationId(loginOrgLocation);
   		 clientSubmission.setStatus(Constants.CANDIDATE_ACTIVE);
   		 clientSubmission.setCreatedDate(new Date());
   		 clientSubmission.setCreatedBy(user.getUserCode());
   		 clientSubmission.setUpdatedDate(commonUtil.currentDate());
   		 if(user.getLastName() !=null){
   			 sb.append(user.getFirstName()).append(" ").append(user.getLastName());
   		 }else{
   			 sb.append(user.getFirstName());
   		 }
   		 clientSubmission.setCreatedUser(sb.toString());
		 if(null != submission.getCandidateScore() && !"".equalsIgnoreCase(submission.getCandidateScore())){
			 clientSubmission.setCandidateScore(submission.getCandidateScore());		 
		 }
		 
		/* the functionality of this block is to verify the submitted candidate is in the Cooling-Off Period or not
		 if the applicant is in cooling-off period, the candidate should be in rejected state(set previous state to Submission) at client side  [Venkat Kesav] START Code snippet*/
		 RequirementSubmissionHistory requirementSubmissionHistoryForCoolingOff = null;
		 if(null != requirement.getParentRequirement()){
			 String parentReqCoolingOffPeriod = requirement.getParentRequirement().getReqCoolingOffPeriod();
			 String [] coolingOffArr = parentReqCoolingOffPeriod.split(" ");
			 String reqCoolingOffPeriodDays;
			 int Days;
			 switch (coolingOffArr[1]) {
				 case "Week" :
					 reqCoolingOffPeriodDays = "7";
				      break;
				 case "Weeks" :
					 Days  = (Integer.parseInt(coolingOffArr[0]) * 7);
					 reqCoolingOffPeriodDays = Integer.toString(Days);
				     break;
				 case "Month" :
					 reqCoolingOffPeriodDays = "30";
				     break;
				 case "Months" :
					 Days  = (Integer.parseInt(coolingOffArr[0]) * 30);
					 reqCoolingOffPeriodDays = Integer.toString(Days);
				     break;
				 case "Year" :
					 reqCoolingOffPeriodDays = "365";
				     break;
				 default:
					 reqCoolingOffPeriodDays = "0";
			 }
			 CandidateConfigurationDTO candidateConfigurationDTO = new CandidateConfigurationDTO();
			 candidateConfigurationDTO.setEmail(submission.getCandidate().getEmail());
			 candidateConfigurationDTO.setPhoneNumber(submission.getCandidate().getMobilePhone());
			 candidateConfigurationDTO.setAadharNumber(submission.getCandidate().getAadharNumber()); 
			 candidateConfigurationDTO.setPanNumber(submission.getCandidate().getPanNumber());
			 candidateConfigurationDTO.setSsnNumber(submission.getCandidate().getSsnNumber());
			 CandidateVerificationConfig candidateVerificationConfig = candidateService.getCandidateVerificationConfig(parentrequirement.getOrgLocationId().getId());
			if(candidateVerificationConfig !=null) {
			 requirementSubmissionHistoryForCoolingOff = requirementSubmissionHistoryService.isInCoolingOffPeriodByLocationID(candidateVerificationConfig,candidateConfigurationDTO, requirement.getParentRequirement().getOrgLocationId(), reqCoolingOffPeriodDays, requirement.getParentRequirement().getId());
			}
			else {
				 requirementSubmissionHistoryForCoolingOff = requirementSubmissionHistoryService.isInCoolingOffPeriodByEmailAndLocationsId(submission.getCandidate().getEmail(), requirement.getParentRequirement().getOrgLocationId(), reqCoolingOffPeriodDays, requirement.getParentRequirement().getId());
			}
			
			 if(requirementSubmissionHistoryForCoolingOff != null){
				 if(requirementSubmissionHistoryForCoolingOff.getIsInCoolingOffPeriod()){
					 clientSubmission.setSubmissionStatus(ApplicantSubmissionStatus.REJECTED.toString());
					 clientSubmission.setPreviousSubmissionStatus(ApplicantSubmissionStatus.SUBMISSION.toString());
					 clientSubmission.setRejectedReason("Under cooling off period, completed by "+requirementSubmissionHistoryForCoolingOff.getCoolingOffPeriodEndDateStr()+".");
					 clientSubmission.setRejectedCategoryType("Other");
					 clientSubmission.setRejectedCategoryOther("Under cooling off period");
				 }
			 }else{
				 clientSubmission.setSubmissionStatus(ApplicantSubmissionStatus.SUBMISSION.toString());
			 }
			 /*END Code snippet*/
		 
		 }else{
			 clientSubmission.setSubmissionStatus(ApplicantSubmissionStatus.SUBMISSION.toString());
		 }
		 
   		 clientSubmission = requirementSubmissionDao.storeRequirementSubmission(clientSubmission);
   		 
         if(null != requirement.getParentRequirement()){
             Integer totalApplicatsCountForRequirement = 0;
             Requirement req = requirementService.getRequirementByRequirementId(requirement.getParentRequirement().getId()) ;
            //updating total number of applicants for requrement
             totalApplicatsCountForRequirement = getNumberOfApplicantsForRequirement(requirement.getParentRequirement().getOrgLocationId().getId(), requirement.getParentRequirement().getId());
//             totalApplicatsCountForRequirement = requirementSubmissionService.getRequirementSubmissionsCountByOrgLocationsIdAndRequirementId(orgLocations,requirement.getId(),user,userRoleName);
             if(totalApplicatsCountForRequirement > 0){
                req.setTotalApplicants(totalApplicatsCountForRequirement); 
                 requirementService.updateRequirement(req);
             }else{
                req.setTotalApplicants(0); 
             }
             //requirementElkUtil.saveElkRequirement(requirement,new JSONObject());    
         }
   		 
   		 
   		 
		 RequirementRateSubmissionNegotiation rateNegotiation = new RequirementRateSubmissionNegotiation();
		 rateNegotiation.setRequirementsubmissionId(clientSubmission);
		 if(null != clientShortlistsRate && clientShortlistsRate.length >i && null != clientShortlistsRate[i] && !"".equalsIgnoreCase(clientShortlistsRate[i])){
			 String clientRate = clientShortlistsRate[i];
			 String clientRatePer = submissionratePer[i];
			 rateNegotiation.setExpectedRate(clientRate);
			 rateNegotiation.setExpectedRateType(clientRatePer);
		 }
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
   		 
   		 
   		 if(!isNullOrEmpty(templateFields.getLocation())){
   			 clientSubmissionFields.setLocation(templateFields.getLocation());
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
   		 
   		 if(clientShortlistsRate.length > 0 && !isNullOrEmpty(clientShortlistsRate[i])){
   			 clientSubmissionFields.setExpectedCTC(clientShortlistsRate[i]); 
   		 }
   		 
   		if(submissionratePer.length > 0 && !isNullOrEmpty(submissionratePer[i])){
  			 clientSubmissionFields.setExpectedRatePer(submissionratePer[i]); 
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
   		 requirementSubmissionHistory.setFromStage(submission.getSubmissionStatus());
   		 requirementSubmissionHistory.setToStage(submission.getSubmissionStatus().toString());
   		submission.setPreviousSubmissionStatus(submission.getSubmissionStatus());
   		 submission.setSubmissionStatus(ApplicantSubmissionStatus.SUBMIT_TO_CLIENT.toString());
   		 submission.setUpdatedBy(user.getUserCode());
   		 submission.setUpdatedDate(commonUtil.currentDate());
   		 requirementSubmissionDao.storeRequirementSubmission(submission);
   		
   		 requirementSubmissionHistoryService.updateRequirementHistory(submission, user, requirementSubmissionHistory, submission.getCreatedBy());
   		 
   		 String userEmail = jsonData.getString("toMailId");
   		 User clientUser = userDao.getUserByEmail(userEmail);
   		 
   		 if(clientUser != null) {
	   		 // for store candidate submit to client with child record in submission history. 
	   		RequirementSubmissionHistory clientSubmissionHistory = new RequirementSubmissionHistory();
	   		clientSubmissionHistory.setFromStage(ApplicantSubmissionStatus.NEW.toString());
			requirementSubmissionHistoryService.updateRequirementHistory(clientSubmission, user, clientSubmissionHistory, clientUser.getUserCode());
	   		 
	   		 
	   		 /*if(requirementSubmissionHistoryForCoolingOff != null){
				 if(requirementSubmissionHistoryForCoolingOff.getIsInCoolingOffPeriod()){
					 RequirementSubmissionHistory requirementSubmissionHistoryNewToSubmission = new RequirementSubmissionHistory();
					 requirementSubmissionHistoryNewToSubmission.setFromStage(Submission_Status.NEW.toString());
					 clientSubmission.setSubmissionStatus(ApplicantSubmissionStatus.SUBMISSION);
					 requirementSubmissionHistoryService.updateRequirementHistory(clientSubmission, null, requirementSubmissionHistoryNewToSubmission, clientUser.getUserCode());
					 
					 RequirementSubmissionHistory requirementSubmissionHistorySubmissionToRejected = new RequirementSubmissionHistory();
					 requirementSubmissionHistorySubmissionToRejected.setFromStage(ApplicantSubmissionStatus.SUBMISSION.getValue());
					 clientSubmission.setSubmissionStatus(ApplicantSubmissionStatus.REJECTED);
					 requirementSubmissionHistoryService.updateRequirementHistory(clientSubmission, null, requirementSubmissionHistorySubmissionToRejected, clientUser.getUserCode());
					 
				 }
	   		}*/
   		 }
   		 
   		 
   		 
   		 String data = clientSubmission.getSubmissionTemplateJsonStringValue();
   		 try {
   		     // create the json array from String rules
   		     JSONArray jsonRules = new JSONArray(data);
   		     // iterate over the rules 
   		     writeRow = createExcelsheet.createRow(i+8);
   		     for (int j=0; j<jsonRules.length();j++){
   		         JSONObject obj = (JSONObject) jsonRules.get(j);
   		         if(obj.has("value") && obj.getString("value") !=null){
   		         createXlsSheet(createExcelsheet,writeRow,j,obj.getString("value"));
   		         }
   		     }
   		     
   		 } catch (JSONException e){
   		     e.printStackTrace();
   		 }
   	 }
   	 
   	 
   	SubmissionXls submissionXls =  saveSubmissionTracker(dateFormat,createExcelworkbook,requirement,user);
    
   	
   	
   	
   	sendMailTemplateWithAttachment(jsonData,user,files,loginOrgLocation,submissionXls,submissionId,redirectOrgLocationId);
	
		
	}
/**
 * prepares email template with attachments
 * don't use this method for email sending 
 * @param jsonObj
 * @param user
 * @param files
 * @param loginOrgLocation
 * @param submissionXls
 * @param submissionId 
 * @throws Exception
 * 
 */
	private void sendMailTemplateWithAttachment(JSONObject jsonObj, User user, Map files, OrgLocations loginOrgLocation,
			SubmissionXls submissionXls, String[] submissionId,Long redirectOrgLocationId) throws Exception {
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
		emailMessage.setEmailType(Constants.CUSTOM_USER_EMAIL);
		StringBuilder emailBuild = new StringBuilder();
		emailMessage.setFromName(emailBuild.append(user.getFirstName()).append("").append(user.getLastName()).toString());
		InternetAddress[] iAdressArray = null;
		Map<String, String> globalVarsMap = new HashMap<String, String>();

		if (jsonObj.has("toMailId")) {
			String othermail = "";
			String toamil = jsonObj.getString("toMailId");
			if (jsonObj.has("othersMail") && !"".equalsIgnoreCase(jsonObj.getString("othersMail"))) {
				othermail = jsonObj.getString("othersMail");
			}
			StringBuilder tomaillBuilder = new StringBuilder();
			iAdressArray = InternetAddress.parse(tomaillBuilder.append(toamil).append(",").append(othermail).toString());
			
			String subject = "";
			String body = "";
			//String signature = user.getFirstName();
			//String url = sourceleadUrl;
			RequirementSubmission reqSub=null;
			List<String> submissionIsd = Arrays.asList(submissionId);
			for(String subIds :submissionIsd){
			 reqSub = getRequirementSubmissionById(Long.parseLong(subIds));		
			 
			 /** resume tracking code*/

			 if(jsonObj.has("sendResumes")) {
				 CandidateResumes resumes = candidateResumesDao.getCandidateResumeByCandidateId(reqSub.getCandidate().getId());
				 if (resumes != null ) {
					 String fileURl = resumes.getFileURL().replace("/slead-static", staticPath);
					 File resumeFile = new File(fileURl);
					 if(resumeFile.exists()) {
						 byte[] bytes =  Files.readAllBytes(resumeFile.toPath());
						 EmailAttachment emailAttachment = new EmailAttachment();
						emailAttachment.setName(resumes.getFileName());
						emailAttachment.setDocFile(bytes);
						emailAttachment.setType(resumes.getFileContentType());
						attachmentList.add(emailAttachment);
					 }
				 }
			 }
			 
			 //URL for page redirection
			 String redirectURL ="";
			 if(reqSub.getRequirement().getParentRequirement() !=null){
				 redirectURL = "requirementSummary-params-type=all-and-requirementUUID="+reqSub.getRequirement().getParentRequirement().getRequirementUUID()+"-and-submissionId="+reqSub.getId();
			 }else{
				 redirectURL = "requirementSummary-params-type=all-and-requirementUUID="+reqSub.getRequirement().getRequirementUUID()+"-and-submissionId="+reqSub.getId();
			 }
			 //Long redirectOrgLocationId = loginOrgLocation.getId();
				
				StringBuilder linkBuild = new StringBuilder();
				globalVarsMap.put("loginURL", linkBuild.append("<a href='").append(sourceleadLoginUrl+ "?redirectURL=" +atsUrl+redirectURL+"&redirectOrgLocationId="+redirectOrgLocationId).append(
						"' style='text-decoration:none; '><div style=' width:220px; border-radius:30px; background-color:#214b90; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Applicants Received</div></a>")
						.toString());
			 if(reqSub.getRequirement().getJobTitle()!= null  && !"".equalsIgnoreCase(reqSub.getRequirement().getJobTitle())){
				 globalVarsMap.put("RequirementTitle", reqSub.getRequirement().getJobTitle());
				 }else{
					 globalVarsMap.put("RequirementTitle","--"); 
				 }
			 
			 if(reqSub.getRequirement().getSpocId().getVendorUser().getFirstName()!= null  && !"".equalsIgnoreCase(reqSub.getRequirement().getSpocId().getVendorUser().getFirstName())){
				 globalVarsMap.put("vendorName", reqSub.getRequirement().getSpocId().getVendorUser().getFirstName());
				 }else{
					 globalVarsMap.put("vendorName","--"); 
				 }
			 
			 
			 if(reqSub.getRequirement().getSpocId().getVendorLocationId()!= null  && !"".equalsIgnoreCase(reqSub.getRequirement().getSpocId().getVendorLocationId().getOrg().getOrgName())){
				 globalVarsMap.put("vendorOrgName", reqSub.getRequirement().getSpocId().getVendorLocationId().getOrg().getOrgName());
				 }else{
					 globalVarsMap.put("vendorOrgName","--"); 
				 }
			}

			// getting the current EmailScenario by EmailScenario unique name
			// and entity type
			EmailScenario emailScenario = null;
			if(loginOrgLocation != null)
				 emailScenario =  emailScenarioService.getEmailScenarioByUniqueNameAndOrgLocationId(Constants.SUBMIT_TO_CLIENT,Constants.ENTITY_TYPE_EMAIL, loginOrgLocation.getId());
				  if(emailScenario == null){
						emailScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.SUBMIT_TO_CLIENT,Constants.ENTITY_TYPE_EMAIL);
				   }
			EmailScenario  notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.NOTIFICATION_TO_CLIENT,Constants.ENTITY_TYPE_NOTIFICATION);

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
			String notificationbody = emailScenerioUtil.buildTemplateBody(notificationScenario.getBody(),globalVarsMap,emailScenario);
			notificationScenario.setBody(notificationbody);
			
			String emailTemplate = emailScenerioUtil.AssembleEmailMessage(emailScenario, emailMessage, subject, body, null, sourceleadUrl, loginOrgLocation.getOrg(),loginOrgLocation);
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
			
			User toUser = userService.findByEmail(jsonObj.getString("toMailId"));
			emailMessage.setSubmissionId(submissionId);
			
			emailScenerioUtil.sendEmail(emailMessage, loginOrgLocation.getId(), iAdressArray,attachmentList);

			StringBuilder actionLinkBuilder = new StringBuilder();   
			String actionLink = null;
			if(reqSub.getRequirement() != null && reqSub.getRequirement().getParentRequirement() != null) {
				 actionLink =actionLinkBuilder.append("").append(atsUrl).append("/").append("requirementSummary?type=all&requirementUUID="+reqSub.getRequirement().getParentRequirement().getRequirementUUID()+"&submissionId="+reqSub.getId()).toString();
				 emailScenerioUtil.sendNotification(actionLink, user, toUser,reqSub.getRequirement().getSpocId().getClientLocationId(), notificationScenario,new HashMap<String,String>());
			}
				 
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
	private Sheet createSubmissionTracker(Org org, String fullName, String[] submissionIds, String requirementName, Workbook createExcelworkbook, Row writeRow, DateFormat dateFormat,OrgLocations loginOrgLocation) throws NumberFormatException, Exception {
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
		 cell4.setCellValue(loginOrgLocation.getOrgLocationName());
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
		
		RequirementSubmissionTemplateFields requirementSubmissionTemplateFields = requirementSubmissionTemplateFieldsService.getSubmissionsTemplateFieldsByRequirementSubmissionsId(neogtation.getRequirementsubmissionId().getId());
		EmailMessage emailMessage = new EmailMessage();
		 emailMessage.setFirstName(requirementSubmissionTemplateFields.getFirstName());
		 emailMessage.setLastName(requirementSubmissionTemplateFields.getLastName());
		 emailMessage.setToEmail(requirementSubmissionTemplateFields.getEmailId());
		 emailMessage.setFromEmail(user.getEmail());
		 emailMessage.setCreatedBy(user.getUserCode());
		 if(null!= user.getFirstName() && !"".equalsIgnoreCase(user.getFirstName()) && null != user.getLastName() && !"".equalsIgnoreCase(user.getLastName())){
			 StringBuilder msgBuild = new StringBuilder();
			 emailMessage.setFromName(msgBuild.append(user.getFirstName()).append(" ").append(user.getLastName()).toString());
		 }else{
			 emailMessage.setFromName(user.getFirstName());
		 }
		 emailMessage.setEmailType(Constants.CUSTOM_USER_EMAIL);
		//setting the class filed values
		 Map<String, String> globalVarsMap = new HashMap<String, String>();
		 
		 if(null != user.getFirstName() && !"".equalsIgnoreCase(user.getFirstName()) && null != user.getLastName() && !"".equalsIgnoreCase(user.getLastName())){
			 StringBuilder sb = new StringBuilder();
			 globalVarsMap.put("userName", sb.append(user.getFirstName()).append(" ").append(user.getLastName()).toString());
			 }else{
				 globalVarsMap.put("userName",user.getFirstName());
			 }
		 
		 if(null != requirement.getJobId() && !"".equalsIgnoreCase(requirement.getJobId())){
			 StringBuilder reqsb=new StringBuilder();
			 
			 globalVarsMap.put("requirementName",reqsb.append(requirement.getJobId()).append("-").append(requirement.getJobTitle()).toString());
			 
			 }else{
				 globalVarsMap.put("requirementName", requirement.getJobTitle());
			 }
		
		 if(null != requirementSubmissionTemplateFields.getFirstName() && null != requirementSubmissionTemplateFields.getLastName() && !"".equalsIgnoreCase(requirementSubmissionTemplateFields.getFirstName()) && !"".equalsIgnoreCase(requirementSubmissionTemplateFields.getLastName())){
			 StringBuilder candiBuild = new StringBuilder();
			 globalVarsMap.put("applicantName", candiBuild.append(requirementSubmissionTemplateFields.getFirstName()).append("").append(requirementSubmissionTemplateFields.getLastName()).toString());
			 }else{
				 globalVarsMap.put("applicantName",requirementSubmissionTemplateFields.getFirstName());
			 }
		 if(null != orgLocations.getOrgLocationName() && !"".equalsIgnoreCase(orgLocations.getOrgLocationName())){
			 globalVarsMap.put("organizationName", orgLocations.getOrgLocationName());
			 }else{
				 globalVarsMap.put("organizationName","--");
			 }
		 if(null != tempHTML){
			 globalVarsMap.put("negotiationTable",tempHTML.toString());
		 }
		 
		
//		 EmailScenario  notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.APPLICANT_NEGOTIATE_REQUIREMENT,Constants.ENTITY_TYPE_NOTIFICATION);

		 /*String notificationbody = emailScenerioUtil.buildBody(notificationScenario.getBody(),globalVarsMap);
		notificationScenario.setBody(notificationbody);*/
		 
		  
		
		  // Unused Code
			 /* User toUser = orgLocations.getUser();
			 String actionLink="";*/
		 
		//calling the sendEmail method of emailScenerioUtil
		 commonUtil.sendTemplateEmail(emailMessage, globalVarsMap,orgLocations.getOrg(),orgLocations,Constants.USER_RATE_NEGOTIATION,new ArrayList<EmailAttachment>());
	}

	@Override
	public void sendEmailtoClient(Org loginOrg, User users, User pastUser, RequirementSubmission requirementSubmission, Candidate candidate,
			StringBuilder tempHTML,OrgLocations orgLocations) throws Exception {
		
		RequirementSubmissionTemplateFields requirementSubmissionTemplateFields = requirementSubmissionTemplateFieldsService.getSubmissionsTemplateFieldsByRequirementSubmissionsId(requirementSubmission.getId());
		EmailMessage emailMessage = new EmailMessage();
		 emailMessage.setFirstName(pastUser.getFirstName());
		 emailMessage.setLastName(pastUser.getLastName());
		 emailMessage.setToEmail(pastUser.getEmail());
		 emailMessage.setFromEmail(users.getEmail());
		 emailMessage.setCreatedBy(users.getUserCode());
		 emailMessage.setFromName(users.getFullName());
		 emailMessage.setEmailType(Constants.CUSTOM_USER_EMAIL);
		 
		 
		//setting the class filed values
		 Map<String, String> globalVarsMap = new HashMap<String, String>();
		 globalVarsMap.put("vendorContact",pastUser.getFullName());
		 globalVarsMap.put("loginUser",users.getFullName());
		 
		 if(requirementSubmission.getRequirement().getJobId()!= null && !"".equalsIgnoreCase(requirementSubmission.getRequirement().getJobId())){
			 
			 StringBuilder reqsb=new StringBuilder();
			 
			 globalVarsMap.put("requirementName",reqsb.append(requirementSubmission.getRequirement().getJobId()).append("-").append(requirementSubmission.getRequirement().getJobTitle()).toString());
			 }else{
				 globalVarsMap.put("requirementName", requirementSubmission.getRequirement().getJobTitle());
			 }
		
		 if(requirementSubmissionTemplateFields.getFirstName() != null && requirementSubmissionTemplateFields.getLastName()!= null && !"".equalsIgnoreCase(requirementSubmissionTemplateFields.getFirstName()) && !"".equalsIgnoreCase(requirementSubmissionTemplateFields.getLastName())){
			 StringBuilder candiBuild = new StringBuilder();
			 globalVarsMap.put("candidateName", candiBuild.append(requirementSubmissionTemplateFields.getFirstName()).append(" ").append(requirementSubmissionTemplateFields.getLastName()).toString());
			 }else{
				 globalVarsMap.put("candidateName",requirementSubmissionTemplateFields.getFirstName());
			 }
		 if(orgLocations.getOrgLocationName() != null && !"".equalsIgnoreCase(orgLocations.getOrgLocationName())){
			 globalVarsMap.put("loginOrgName", orgLocations.getOrgLocationName());
			 }else{
				 globalVarsMap.put("loginOrgName","--");
			 }
		 if(tempHTML != null){
			 globalVarsMap.put("negotiationTable",tempHTML.toString());
		 }
		 
		 //setting the Recipient information
		 InternetAddress[] iAdressArray = InternetAddress.parse(emailMessage.getToEmail());
		
		 //getting the current EmailScenario by EmailScenario unique name and entity type
		 EmailScenario  emailScenario = null;
		 if(orgLocations != null)
			 emailScenario =  emailScenarioService.getEmailScenarioByUniqueNameAndOrgLocationId(Constants.VENDOR_RATE_NEGOTIATION,Constants.ENTITY_TYPE_EMAIL, orgLocations.getId());
			  if(emailScenario == null){
					emailScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.VENDOR_RATE_NEGOTIATION,Constants.ENTITY_TYPE_EMAIL);
			   }
		 EmailScenario  notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.CLIENT_RATE_NEGOTIATION,Constants.ENTITY_TYPE_NOTIFICATION);

		
		 //covert subject,body and signature
		 String subject = emailScenerioUtil.bulidTemplateSubject(emailScenario.getSubject(),globalVarsMap,emailScenario);
		 String body = emailScenerioUtil.buildTemplateBody(emailScenario.getBody(),globalVarsMap,emailScenario);
		 String signature = emailScenerioUtil.buildTemplateSignature(emailScenario.getSignature(),globalVarsMap,emailScenario);
		 String url = sourceleadUrl;
		 
		String notificationbody = emailScenerioUtil.buildTemplateBody(notificationScenario.getBody(),globalVarsMap,notificationScenario);
		notificationScenario.setBody(notificationbody);
		 
		 //construct HTML template using required data
		 String emailTemplate = emailScenerioUtil.AssembleEmailMessage(emailScenario,emailMessage, subject, body, signature, url, loginOrg,orgLocations);
		 emailMessage.setSubject(subject);
		 emailMessage.setMessageBody(emailTemplate);
		  
		//calling the sendEmail method of emailScenerioUtil
		 emailScenerioUtil.sendEmail(emailMessage,orgLocations.getId(),iAdressArray,new ArrayList<EmailAttachment>());
		 String actionLink="";
		 emailScenerioUtil.sendNotification(actionLink, users, pastUser, requirementSubmission.getOrgLocations(), notificationScenario,new HashMap<String,String>());
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
			OrgLocations orgLocations, RequirementRateSubmissionNegotiation requirementRateSubmissionNegotiation, StringBuilder tempHtml) throws Exception {

		RequirementSubmissionTemplateFields requirementSubmissionTemplateFields = requirementSubmissionTemplateFieldsService.getSubmissionsTemplateFieldsByRequirementSubmissionsId(requirementRateSubmissionNegotiation.getRequirementsubmissionId().getId());
		 EmailMessage emailMessage = new EmailMessage();
		 emailMessage.setFirstName(requirementSubmissionTemplateFields.getFirstName());
		 emailMessage.setLastName(requirementSubmissionTemplateFields.getLastName());
		 emailMessage.setToEmail(requirementSubmissionTemplateFields.getEmailId());
		 emailMessage.setFromEmail(user.getEmail());
		 emailMessage.setCreatedBy(user.getUserCode());
		 if(user.getFirstName()!= null && !"".equalsIgnoreCase(user.getFirstName()) && user.getLastName()!= null && !"".equalsIgnoreCase(user.getLastName())){
			 StringBuilder msgBuild = new StringBuilder();
			 emailMessage.setFromName(msgBuild.append(user.getFirstName()).append(" ").append(user.getLastName()).toString());
		 }else{
			 emailMessage.setFromName(user.getFirstName());
		 }
		 emailMessage.setEmailType(Constants.CUSTOM_USER_EMAIL);

		//setting the class filed values
		 Map<String, String> globalVarsMap = new HashMap<String, String>();

		 if(requirement.getJobId()!= null && !"".equalsIgnoreCase(requirement.getJobId())){
			 
			 StringBuilder reqsb=new StringBuilder();
			 
			 globalVarsMap.put("requirementName",reqsb.append(requirement.getJobId()).append("-").append(requirement.getJobTitle()).toString());
			 
			
			 }else{
				 globalVarsMap.put("requirementName", requirement.getJobTitle());
			 }
		 if(requirementSubmissionTemplateFields.getFirstName() != null && requirementSubmissionTemplateFields.getLastName()!= null && !"".equalsIgnoreCase(requirementSubmissionTemplateFields.getFirstName()) && !"".equalsIgnoreCase(requirementSubmissionTemplateFields.getLastName())){
			 StringBuilder candBuild = new StringBuilder();
			 globalVarsMap.put("applicantName", candBuild.append(requirementSubmissionTemplateFields.getFirstName()).append("").append(requirementSubmissionTemplateFields.getLastName()).toString());
			 }else{
				 globalVarsMap.put("applicantName",requirementSubmissionTemplateFields.getFirstName());
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
		 
		 if(tempHtml != null){
			 globalVarsMap.put("negotiationTable",tempHtml.toString());
		 }
		 
		 globalVarsMap.put("loginOrgName", orgLocations.getOrg().getOrgName());
		
		 User toUser = orgLocations.getUser();
		 
		 String actionLink="";
		//calling the sendEmail method of emailScenerioUtil
		 commonUtil.sendTemplateEmail(emailMessage, globalVarsMap,orgLocations.getOrg(),orgLocations,Constants.USER_ACCEPT_RATE_NEGOTIATION,new ArrayList<EmailAttachment>());
		

	}

	@Override
	public void sendEmailtoRequirementManager(Org loginOrg, User users, RequirementSubmission requirementSubmission,
			OrgLocations loginOrgLocation) throws Exception {
		
		 EmailMessage emailMessage = new EmailMessage();
		 emailMessage.setFirstName(requirementSubmission.getRequirement().getManagerUserId().getFirstName());
		 emailMessage.setLastName(requirementSubmission.getRequirement().getManagerUserId().getFirstName());
		 emailMessage.setToEmail(requirementSubmission.getRequirement().getManagerUserId().getEmail());
		 emailMessage.setFromEmail(users.getEmail());
		 emailMessage.setCreatedBy(users.getUserCode());
		 emailMessage.setFromName(users.getFirstName()+" "+users.getLastName());
		 emailMessage.setEmailType(Constants.CUSTOM_USER_EMAIL);

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
	public void sendEmailtoApplicant(Org loginOrg, User loginUser,RequirementSubmission requirementSubmission,
			 OrgLocations loginOrgLocation) throws Exception {
		
		 RequirementSubmissionTemplateFields requirementSubmissionTemplateFields = requirementSubmissionTemplateFieldsService.getSubmissionsTemplateFieldsByRequirementSubmissionsId(requirementSubmission.getId());
		 User assignedUser = userService.getUserByUserCode(requirementSubmission.getCreatedBy());
		
		 EmailMessage emailMessage = new EmailMessage();
		 emailMessage.setFirstName(requirementSubmissionTemplateFields.getFirstName());
		 emailMessage.setLastName(requirementSubmissionTemplateFields.getLastName());
		 emailMessage.setToEmail(requirementSubmissionTemplateFields.getEmailId());
		 emailMessage.setFromEmail(assignedUser.getEmail());
		 emailMessage.setCreatedBy(loginUser.getUserCode());
		 emailMessage.setFromName(assignedUser.getFullName());	 
		 emailMessage.setEmailType(Constants.CUSTOM_USER_EMAIL);

        // To get the RateNegatiotion of RequirementSubmission based on Submission Id
		 
		 RequirementRateSubmissionNegotiation reqRateNegotiation  = requirementRateSubmissionNegotiationService.getLatestRequirementRateSubmissionNegotiationBySubmissionId(requirementSubmission.getId());
		
		 
		 Map<String, String> globalVarsMap = new HashMap<String, String>();
		 if(requirementSubmission.getRequirement().getJobId() != null && !"".equalsIgnoreCase(requirementSubmission.getRequirement().getJobId())){
			 StringBuilder builder=new StringBuilder();
			 globalVarsMap.put("RequirementName", builder.append(requirementSubmission.getRequirement().getJobId()).append("-").append(requirementSubmission.getRequirement().getJobTitle()).toString());
		 }else{
			 globalVarsMap.put("RequirementName", requirementSubmission.getRequirement().getJobTitle());
		 }
		 
		 if(requirementSubmissionTemplateFields.getLastName() != null && !"".equalsIgnoreCase(requirementSubmissionTemplateFields.getLastName())){
			 StringBuilder candName = new StringBuilder();
			 globalVarsMap.put("ApplicantName", candName.append(requirementSubmissionTemplateFields.getFirstName()).append(" ").append(requirementSubmissionTemplateFields.getLastName()).toString());
		 }else{
			 globalVarsMap.put("ApplicantName",requirementSubmissionTemplateFields.getFirstName());
		 }
		
		 List<String> requirementLocationsList = new ArrayList<>();
			List<RequirementLocations> locationsList;
			String location = "";
			StringBuilder requirementBuilder=new StringBuilder();
			try {
				locationsList = requirementLocationsService.getRequirementLocationsListByRequirementId(requirementSubmission.getRequirement().getId());
				if(locationsList != null && !locationsList.isEmpty()) {
					for(RequirementLocations locationsJob : locationsList) {
						requirementLocationsList.add(locationsJob.getLocation());
						if(locationsJob.getLocation() !=null && locationsJob.getLocation() !=null){
							location = requirementBuilder.append(locationsJob.getLocation()).toString();
						}else{
							location = "--";
						}
					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			globalVarsMap.put("RequirementLocation", location);
		 
		 
		 
		 if(Constants.JOB_OTHER.equalsIgnoreCase(requirementSubmission.getRequirement().getJobType())) {
			 globalVarsMap.put("EmploymentType", requirementSubmission.getRequirement().getOtherJobType());
		 }
		 else if(requirementSubmission.getRequirement().getJobType() != null && !"".equalsIgnoreCase(requirementSubmission.getRequirement().getJobType())){
			 globalVarsMap.put("EmploymentType", requirementSubmission.getRequirement().getJobType());
		 }else{
			 globalVarsMap.put("EmploymentType","--");
		 }
		 StringBuilder sb1 = new StringBuilder();
		 if( reqRateNegotiation !=null && reqRateNegotiation.getExpectedRate() != null && !"".equalsIgnoreCase(reqRateNegotiation.getExpectedRate())){
			
			 globalVarsMap.put("Rate", sb1.append(reqRateNegotiation.getExpectedRate()).append("/").append(reqRateNegotiation.getExpectedRateType()).toString());
		 }else if(requirementSubmission.getCandidate().getExpectedRate() != null && !"".equalsIgnoreCase(requirementSubmission.getCandidate().getExpectedRate())){
			 
			 globalVarsMap.put("Rate", sb1.append(requirementSubmission.getCandidate().getExpectedRateCurrencey()).append(" ").append(requirementSubmission.getCandidate().getExpectedRate()).append("/").append(requirementSubmission.getCandidate().getRateType()).toString());
		 }else{
			 globalVarsMap.put("Rate","--");
		 }
		 
		 if(assignedUser.getFirstName()!= null && !"".equalsIgnoreCase(assignedUser.getFirstName()) && assignedUser.getLastName()!= null && !"".equalsIgnoreCase(assignedUser.getLastName())){
			 StringBuilder assUser = new StringBuilder();
			 globalVarsMap.put("ManagerName", assUser.append(assignedUser.getFirstName()).append(" ").append(assignedUser.getLastName()).toString());
		 }else{
			 globalVarsMap.put("ManagerName",assignedUser.getFirstName());
		 }
		 
		 globalVarsMap.put("loginOrg", loginOrgLocation.getOrgLocationName());
		 StringBuilder linkBuild = new StringBuilder();
		 globalVarsMap.put("loginLink",linkBuild.append("<a href='").append(sourceleadLoginUrl).append("' style='text-decoration:none; '><div style=' width:220px; border-radius:30px; background-color:#214b90; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Login</div></a>").toString());
		 
		 InternetAddress[] iAdressArray = InternetAddress.parse(emailMessage.getToEmail());
		
		 EmailScenario  emailScenario = null;
		 if(loginOrgLocation != null)
			 emailScenario =  emailScenarioService.getEmailScenarioByUniqueNameAndOrgLocationId(Constants.Applicant_Will_Receive_Mail_About_The_Acceptance_Of_Requirement_By_Manager,Constants.ENTITY_TYPE_EMAIL, loginOrgLocation.getId());
			  if(emailScenario == null){
					emailScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.Applicant_Will_Receive_Mail_About_The_Acceptance_Of_Requirement_By_Manager,Constants.ENTITY_TYPE_EMAIL);
			   }
		 //	 EmailScenario  notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.Recruiter_Will_Receive_Mail_About_The_Acceptance_Of_Requirement_By_Manager,Constants.ENTITY_TYPE_NOTIFICATION);
		 
			//covert subject,body and signature
				 String subject = emailScenerioUtil.bulidTemplateSubject(emailScenario.getSubject(),globalVarsMap,emailScenario);
				 String body = emailScenerioUtil.buildTemplateBody(emailScenario.getBody(),globalVarsMap,emailScenario);
				 String signature = null;
				 if(StringUtils.isNotBlank(emailScenario.getSignature())){
					 signature = emailScenerioUtil.buildTemplateSignature(emailScenario.getSignature(),globalVarsMap,emailScenario); 
				 }
				 String url = sourceleadUrl;
		 
//		 String notificationbody = emailScenerioUtil.buildTempBody(notificationScenario.getBody(),globalVarsMap);
//		 notificationScenario.setBody(notificationbody);
		 
		 String emailTemplate = emailScenerioUtil.AssembleEmailMessage(emailScenario,emailMessage, subject, body, signature, url, loginOrg,loginOrgLocation);
		 emailMessage.setSubject(subject);
		 emailMessage.setMessageBody(emailTemplate);
		  
		 emailScenerioUtil.sendEmail(emailMessage,loginOrgLocation.getId(),iAdressArray,new ArrayList<EmailAttachment>());
		// String actionLink="";
		// emailScenerioUtil.sendNotification(actionLink, loginUser, requirementSubmission.getCandidate(), loginOrgLocation, notificationScenario);
		 
	}
	
	
	
	
	public void sendEmailtoAssignedMemberApplicantAuthorization(Org loginOrg, User loginUser, RequirementSubmission requirementSubmission, OrgLocations loginOrgLocation) throws Exception
	{
		RequirementSubmissionTemplateFields requirementSubmissionTemplateFields = requirementSubmissionTemplateFieldsService.getSubmissionsTemplateFieldsByRequirementSubmissionsId(requirementSubmission.getId());
		User assignedUser = userService.getUserByUserCode(requirementSubmission.getCreatedBy());
		EmailMessage emailMessage = new EmailMessage();
		emailMessage.setFirstName(assignedUser.getFirstName());
		emailMessage.setLastName(assignedUser.getLastName());
		emailMessage.setToEmail(assignedUser.getEmail());
		emailMessage.setFromEmail(loginUser.getEmail());
		emailMessage.setCreatedBy(loginUser.getUserCode());
		emailMessage.setFromName(loginUser.getFullName());
		emailMessage.setEmailType(Constants.CUSTOM_USER_EMAIL);
		// To get the RateNegatiotion of RequirementSubmission based on
		// Submission Id
		RequirementRateSubmissionNegotiation reqRateNegotiation = requirementRateSubmissionNegotiationService.getLatestRequirementRateSubmissionNegotiationBySubmissionId(requirementSubmission.getId());
		Map<String, String> globalVarsMap = new HashMap<String, String>();
		if (requirementSubmission.getRequirement().getJobTitle() != null
				&& !"".equalsIgnoreCase(requirementSubmission.getRequirement().getJobTitle())) {
			globalVarsMap.put("requirmentName", requirementSubmission.getRequirement().getJobTitle());
		}
		else {
			globalVarsMap.put("requirmentName", "--");
		}
		if (requirementSubmissionTemplateFields.getLastName() != null
				&& !"".equalsIgnoreCase(requirementSubmissionTemplateFields.getLastName())) {
			StringBuilder candName = new StringBuilder();
			globalVarsMap.put("applicantName", candName.append(requirementSubmissionTemplateFields.getFirstName()).append(" ").append(requirementSubmissionTemplateFields.getLastName()).toString());
		}
		else {
			globalVarsMap.put("applicantName", requirementSubmissionTemplateFields.getFirstName());
		}
		
		 List<String> requirementLocationsList = new ArrayList<>();
			List<RequirementLocations> locationsList;
			String location = "";
			StringBuilder requirementBuilder=new StringBuilder();
			try {
				locationsList = requirementLocationsService.getRequirementLocationsListByRequirementId(requirementSubmission.getRequirement().getId());
				if(locationsList != null && !locationsList.isEmpty()) {
					for(RequirementLocations locationsJob : locationsList) {
						requirementLocationsList.add(locationsJob.getLocation());
						if(locationsJob.getLocation() !=null && locationsJob.getLocation() !=null){
							location = requirementBuilder.append(locationsJob.getLocation()).toString();
						}else{
							location = "--";
						}
					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			globalVarsMap.put("requirmentLocation", location);
		if (reqRateNegotiation != null && reqRateNegotiation.getExpectedRate() != null
				&& !"".equalsIgnoreCase(reqRateNegotiation.getExpectedRate())) {
			globalVarsMap.put("applicantRate", reqRateNegotiation.getExpectedRate());
		}
		else if (requirementSubmission.getCandidate().getExpectedRate() != null
				&& !"".equalsIgnoreCase(requirementSubmission.getCandidate().getExpectedRate())) {
			globalVarsMap.put("applicantRate", requirementSubmission.getCandidate().getExpectedRate());
		}
		else {
			globalVarsMap.put("applicantRate", "--");
		}
		if (assignedUser.getFirstName() != null && !"".equalsIgnoreCase(assignedUser.getFirstName())
				&& assignedUser.getLastName() != null
				&& !"".equalsIgnoreCase(assignedUser.getLastName())) {
			StringBuilder assUser = new StringBuilder();
			globalVarsMap.put("assignedUser", assUser.append(assignedUser.getFirstName()).append(" ").append(assignedUser.getLastName()).toString());
		}
		else {
			globalVarsMap.put("assignedUser", assignedUser.getFirstName());
		}
		globalVarsMap.put("loginOrg", loginOrgLocation.getOrgLocationName());
		globalVarsMap.put("loginUser", loginUser.getFirstName());
		EmailScenario notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.Applicant_Authorization_Manager_Acceptence_Receive_Mail_About_The_Acceptance_Of_Requirement_By_Manager, Constants.ENTITY_TYPE_NOTIFICATION);
		String notificationbody = emailScenerioUtil.buildTemplateBody(notificationScenario.getBody(), globalVarsMap,notificationScenario);
		notificationScenario.setBody(notificationbody);
		commonUtil.sendTemplateEmail(emailMessage, globalVarsMap,loginOrgLocation.getOrg(),loginOrgLocation,Constants.Applicant_Authorization_Manager_Acceptence_Receive_Mail_About_The_Acceptance_Of_Requirement_By_Manager,new ArrayList<EmailAttachment>());

		String actionLink = "";
		emailScenerioUtil.sendNotification(actionLink, loginUser, assignedUser, loginOrgLocation, notificationScenario,new HashMap<String,String>());
	}
	
	
	
	private void sendEmailToAssignedMember(Org loginOrg, User loginUser, RequirementSubmission requirementSubmission,
			OrgLocations loginOrgLocation) throws Exception {
		
		 RequirementSubmissionTemplateFields requirementSubmissionTemplateFields = requirementSubmissionTemplateFieldsService.getSubmissionsTemplateFieldsByRequirementSubmissionsId(requirementSubmission.getId());
		 //URL for page redirection
		 String redirectURL = "/requirementSummary-params-type=APPLICANTS-and-requirementUUID="+requirementSubmission.getRequirement().getRequirementUUID()+"-and-submissionId="+requirementSubmissionTemplateFields.getRequirementSubmission().getId()+"-and-candidateUUID="+requirementSubmissionTemplateFields.getRequirementSubmission().getCandidate().getCandidateUUID()+"-and-submissionStatus=all";	 
		 Long redirectOrgLocationId = loginOrgLocation.getId();
		 EmailMessage emailMessage = new EmailMessage();
		 User assignedUser = userService.getUserByUserCode(requirementSubmission.getCreatedBy());
		 if(null != requirementSubmission.getCreatedUser()){
		 emailMessage.setFirstName(requirementSubmission.getCreatedUser());
		 }else{
			 
			emailMessage.setFirstName(assignedUser.getFirstName());
			emailMessage.setLastName(assignedUser.getLastName());
		 }
		 emailMessage.setToEmail(assignedUser.getEmail());
		 emailMessage.setFromEmail(loginUser.getEmail());
		 emailMessage.setCreatedBy(loginUser.getUserCode());
		 //emailMessage.setEmailType(Constants.CUSTOM_USER_EMAIL);
		 
		 if(null != loginUser.getLastName()){
			 StringBuilder managerName = new StringBuilder(); 
			 emailMessage.setFromName(managerName.append(loginUser.getFirstName()).append(" ").append(loginUser.getLastName()).toString());	 
		 }else{
			 emailMessage.setFromName(loginUser.getFirstName());
		 }
		
		 Map<String, String> globalVarsMap = new HashMap<String, String>();
		 if(loginUser.getFirstName()!= null && !"".equalsIgnoreCase(loginUser.getFirstName()) && loginUser.getLastName()!= null && !"".equalsIgnoreCase(loginUser.getLastName())){
			 StringBuilder userName = new StringBuilder();
			 globalVarsMap.put("LoginUser", userName.append(loginUser.getFirstName()).append(" ").append(loginUser.getLastName()).toString());
		 }else{
			 globalVarsMap.put("LoginUser",loginUser.getFirstName());
		 }
		 
		 if(loginOrgLocation.getOrgLocationName()!= null && !"".equalsIgnoreCase(loginOrgLocation.getOrgLocationName())){
			 globalVarsMap.put("LoginOrg",loginOrgLocation.getOrgLocationName());
		 }else{
			 globalVarsMap.put("LoginOrg","--");
		 }
		 
		 if(requirementSubmission.getRequirement().getJobTitle() != null && !"".equalsIgnoreCase(requirementSubmission.getRequirement().getJobTitle())){
			 globalVarsMap.put("RequirementName", requirementSubmission.getRequirement().getJobTitle());
		 }else{
			 globalVarsMap.put("RequirementName","--");
		 }
		 
		 if(requirementSubmissionTemplateFields.getLastName() != null && !"".equalsIgnoreCase(requirementSubmissionTemplateFields.getLastName())){
			 StringBuilder candidateName = new StringBuilder(); 
			 globalVarsMap.put("ApplicantName", candidateName.append(requirementSubmissionTemplateFields.getFirstName()).append(" ").append(requirementSubmissionTemplateFields.getLastName()).toString());
		 }else{
			 globalVarsMap.put("ApplicantName",requirementSubmissionTemplateFields.getFirstName());
		 }
		
		 List<String> requirementLocationsList = new ArrayList<>();
			List<RequirementLocations> locationsList;
			String location = "";
			StringBuilder requirementBuilder=new StringBuilder();
			try {
				locationsList = requirementLocationsService.getRequirementLocationsListByRequirementId(requirementSubmission.getRequirement().getId());
				if(locationsList != null && !locationsList.isEmpty()) {
					for(RequirementLocations locationsJob : locationsList) {
						requirementLocationsList.add(locationsJob.getLocation());
						if(locationsJob.getLocation() !=null && locationsJob.getLocation() !=null){
							location = requirementBuilder.append(locationsJob.getLocation()).toString();
						}else{
							location = "--";
						}
					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 globalVarsMap.put("RequirementLocation", location);
		 
		// To get the RateNegatiotion of RequirementSubmission based on Submission Id
		 RequirementRateSubmissionNegotiation reqRateNegotiation  = requirementRateSubmissionNegotiationService.getLatestRequirementRateSubmissionNegotiationBySubmissionId(requirementSubmission.getId());
				 
		 if( reqRateNegotiation !=null && reqRateNegotiation.getExpectedRate() != null && !"".equalsIgnoreCase(reqRateNegotiation.getExpectedRate())){
			 globalVarsMap.put("Rate", reqRateNegotiation.getExpectedRate());
		 }else if(requirementSubmissionTemplateFields.getExpectedCTC() != null && !"".equalsIgnoreCase(requirementSubmissionTemplateFields.getExpectedCTC())){
			 globalVarsMap.put("Rate", requirementSubmissionTemplateFields.getExpectedCTC());
		 }else{
			 globalVarsMap.put("Rate","--");
		 }
		 
		 if(assignedUser.getFirstName()!= null && !"".equalsIgnoreCase(assignedUser.getFirstName()) && assignedUser.getLastName()!= null && !"".equalsIgnoreCase(assignedUser.getLastName())){
			 StringBuilder assUser = new StringBuilder();
			 globalVarsMap.put("AssignedUser", assUser.append(assignedUser.getFirstName()).append(" ").append(assignedUser.getLastName()).toString());
		 }else{
			 globalVarsMap.put("AssignedUser",assignedUser.getFirstName());
		 }
		 StringBuilder linkBuild = new StringBuilder();

			globalVarsMap.put("loginURL", linkBuild.append("<a href='").append(sourceleadLoginUrl+ "?redirectURL=" +atsUrl+redirectURL+"&redirectOrgLocationId="+redirectOrgLocationId).append(
					"' style='text-decoration:none; '><div style=' width:220px; border-radius:30px; background-color:#214b90; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Applicant Authorization</div></a>")
					.toString());
		 InternetAddress[] iAdressArray = InternetAddress.parse(emailMessage.getToEmail());
		
		 EmailScenario  emailScenario = null;
		 if(loginOrgLocation != null)
			 emailScenario =  emailScenarioService.getEmailScenarioByUniqueNameAndOrgLocationId(Constants.Recruiter_Will_Receive_Mail_About_The_Acceptance_Of_Requirement_By_Manager,Constants.ENTITY_TYPE_EMAIL, loginOrgLocation.getId());
			  if(emailScenario == null){
					emailScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.Recruiter_Will_Receive_Mail_About_The_Acceptance_Of_Requirement_By_Manager,Constants.ENTITY_TYPE_EMAIL);
			   }
		 EmailScenario  notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.Recruiter_Will_Receive_Mail_About_The_Acceptance_Of_Requirement_By_Manager,Constants.ENTITY_TYPE_NOTIFICATION);
		 
		 //covert subject,body and signature
		 String subject = emailScenerioUtil.bulidTemplateSubject(emailScenario.getSubject(),globalVarsMap,emailScenario);
		 String body = emailScenerioUtil.buildTemplateBody(emailScenario.getBody(),globalVarsMap,emailScenario);
		 String signature = emailScenerioUtil.buildTemplateSignature(emailScenario.getSignature(),globalVarsMap,emailScenario);
		 String url = sourceleadUrl;
		 
		 String notificationbody = emailScenerioUtil.buildTemplateBody(notificationScenario.getBody(),globalVarsMap,notificationScenario);
		 notificationScenario.setBody(notificationbody);
		 
		 String emailTemplate = emailScenerioUtil.AssembleEmailMessage(emailScenario,emailMessage, subject, body, signature, url, loginOrg,loginOrgLocation);
		 emailMessage.setSubject(subject);
		 emailMessage.setMessageBody(emailTemplate);
		  
		 emailScenerioUtil.sendEmail(emailMessage,loginOrgLocation.getId(),iAdressArray,new ArrayList<EmailAttachment>());
		 String actionLink="";
		 StringBuilder actionLinkBuilder = new StringBuilder();
		 actionLink = actionLinkBuilder.append("").append(atsUrl).append("/")
					.append("requirementSummary?requirementUUID=" + requirementSubmission.getRequirementUUID()).toString();
		 emailScenerioUtil.sendNotification(actionLink, loginUser, assignedUser, loginOrgLocation, notificationScenario,new HashMap<String,String>());
		
	}

	@Override
	public List<RequirementSubmission> getApplicantsByStatus(Long orgLocationsId, User user,String status) throws Exception {
		
        
        List<RequirementSubmission> submittedApplicantsList =  requirementSubmissionDao.getApplicantsByStatus(orgLocationsId, user, status);
        OrgLocations orgLoc = orgLocationService.getOrgLocations(orgLocationsId);
        Timezone timezone = null;
		if(null != orgLoc.getTimezone()){
	    	 timezone = timeZoneService.getTimezone(orgLoc.getTimezone().getId());
		 }
        for(RequirementSubmission reqsub :submittedApplicantsList){
        	RequirementSubmissionTemplateFields requirementSubmissionTemplateFields = requirementSubmissionTemplateFieldsService.getSubmissionsTemplateFieldsByRequirementSubmissionsId(reqsub.getId());
        	StringBuilder sb = new  StringBuilder();
        	if(requirementSubmissionTemplateFields.getLastName() != null && !" ".equalsIgnoreCase(requirementSubmissionTemplateFields.getLastName())){
	        		
        		reqsub.setCandidateName(sb.append(requirementSubmissionTemplateFields.getFirstName()).append(" ").append(requirementSubmissionTemplateFields.getLastName()).toString());
        	}else{
                    reqsub.setCandidateName(sb.append(requirementSubmissionTemplateFields.getFirstName()).toString());
        	}
        	reqsub.setRequirementId(reqsub.getRequirement().getId());
        	StringBuilder jbt = new  StringBuilder();
            reqsub.setRequirementName(jbt.append(reqsub.getRequirement().getJobTitle()).toString());
            reqsub.setRequirementUUID(reqsub.getRequirement().getRequirementUUID());
            /*if("ACCEPTED".equalsIgnoreCase(reqsub.getSubmissionRateStatus().toString())){
            	StringBuilder app = new  StringBuilder();
            	  User  approvedUser = userDao.getUserByUserCode(reqsub.getUpdatedBy());
            	  reqsub.setUpdatedBy(app.append(approvedUser.getFirstName()).append(" ").append(approvedUser.getLastName()).toString());	
            }*/
            User  submittedSourceUser = userDao.getUserByUserCode(reqsub.getCreatedBy());
            StringBuilder sub = new  StringBuilder();
            if(submittedSourceUser.getLastName() != null && submittedSourceUser.getLastName() !=""){
            reqsub.setSubmittedBy(sub.append(submittedSourceUser.getFirstName()).append(" ").append(submittedSourceUser.getLastName()).toString() );
            }else{
           	reqsub.setSubmittedBy(sub.append(submittedSourceUser.getFirstName()).toString());	
            }
            if(reqsub.getCreatedDate() != null){
				String currentTimeStr = Constants.TZ_DF.format(reqsub.getCreatedDate());
				if(null != timezone && null != timezone.getTimeZoneId()){
					String currentDateInTimezone= CommonUtil.convertTimeZones("UTC" ,timezone.getTimeZoneId(), currentTimeStr);
					DateFormat TZ_DF_TS = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
					Date converteDate = TZ_DF_TS.parse(currentDateInTimezone);
					currentDateInTimezone = commonUtil.dateStrFromOrgDateFormatter(orgLoc.getDateFormat(),converteDate);
					reqsub.setSubmittedOn(currentDateInTimezone);
				}
		    }
        }
        return submittedApplicantsList;
	}

	@Override
	public void sendEmailtoVendor(Org loginOrg, User users, User pastUser, RequirementSubmission requirementSubmission, Candidate candidate,
			StringBuilder tempHTML,OrgLocations orgLocations) throws Exception {
		
		RequirementSubmissionTemplateFields requirementSubmissionTemplateFields = requirementSubmissionTemplateFieldsService.getSubmissionsTemplateFieldsByRequirementSubmissionsId(requirementSubmission.getId());
		EmailMessage emailMessage = new EmailMessage();
		 emailMessage.setFirstName(pastUser.getFirstName());
		 emailMessage.setLastName(pastUser.getLastName());
		 emailMessage.setToEmail(pastUser.getEmail());
		 emailMessage.setFromEmail(users.getEmail());
		 emailMessage.setCreatedBy(users.getUserCode());
		 StringBuilder msgBuild = new StringBuilder();
		 emailMessage.setFromName(msgBuild.append(users.getFirstName()).append(" ").append(users.getLastName()).toString());
		 
		 emailMessage.setEmailType(Constants.CUSTOM_USER_EMAIL);

		 
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
		 
		 if(requirementSubmission.getRequirement().getJobId()!= null && !"".equalsIgnoreCase(requirementSubmission.getRequirement().getJobId())){
			 StringBuilder reqsb=new StringBuilder();
			 
			 globalVarsMap.put("requirementName",reqsb.append(requirementSubmission.getRequirement().getJobId()).append("-").append(requirementSubmission.getRequirement().getJobTitle()).toString());
			 }else{
				 globalVarsMap.put("requirementName", requirementSubmission.getRequirement().getJobTitle());
			 }
		
		 if(requirementSubmissionTemplateFields.getFirstName() != null && requirementSubmissionTemplateFields.getLastName()!= null && !"".equalsIgnoreCase(requirementSubmissionTemplateFields.getFirstName()) && !"".equalsIgnoreCase(requirementSubmissionTemplateFields.getLastName())){
			 StringBuilder candiBuild = new StringBuilder();
			 globalVarsMap.put("candidateName", candiBuild.append(requirementSubmissionTemplateFields.getFirstName()).append(" ").append(requirementSubmissionTemplateFields.getLastName()).toString());
			 }else{
				 globalVarsMap.put("candidateName",requirementSubmissionTemplateFields.getFirstName());
			 }
		 if(orgLocations.getOrgLocationName() != null && !"".equalsIgnoreCase(orgLocations.getOrgLocationName())){
			 globalVarsMap.put("loginOrgName", orgLocations.getOrgLocationName());
			 }else{
				 globalVarsMap.put("loginOrgName","--");
			 }
		 if(tempHTML != null){
			 globalVarsMap.put("negotiationTable",tempHTML.toString());
		 }
		 
		
		 //getting the current EmailScenario by EmailScenario unique name and entity type
		 EmailScenario  notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.CLIENT_RATE_NEGOTIATION,Constants.ENTITY_TYPE_NOTIFICATION);

		 String notificationbody = emailScenerioUtil.buildTemplateBody(notificationScenario.getBody(),globalVarsMap,notificationScenario);
		 notificationScenario.setBody(notificationbody);
		 
		 StringBuilder notificationLink = new StringBuilder();
		 String actionLink="";
	     actionLink = notificationLink.append(atsUrl).append("/").append("requirementSummary?requirementUUID="+requirementSubmission.getSourceSubmission().getRequirement().getRequirementUUID()+"&submissionId="+requirementSubmission.getId()+"&candidateUUID="+requirementSubmissionTemplateFields.getRequirementSubmission().getCandidate().getCandidateUUID()+"&submissionStatus=SUBMIT_TO_CLIENT").toString();

		//calling the sendEmail method of emailScenerioUtil
		 commonUtil.sendTemplateEmail(emailMessage, globalVarsMap,orgLocations.getOrg(),orgLocations,Constants.CLIENT_RATE_NEGOTIATION,new ArrayList<EmailAttachment>());
		 emailScenerioUtil.sendNotification(actionLink, users, pastUser, requirementSubmission.getVendorLocationId(), notificationScenario,new HashMap<String,String>());
	}

	@Override
	public void userRejectRateNegotiation(Requirement requirement, User user, Candidate candidate,
			OrgLocations orgLocations, RequirementRateSubmissionNegotiation requirementRateSubmissionNegotiation, String rejectreason, StringBuilder tempHTML)
			throws Exception {
		
		RequirementSubmissionTemplateFields requirementSubmissionTemplateFields = requirementSubmissionTemplateFieldsService.getSubmissionsTemplateFieldsByRequirementSubmissionsId(requirementRateSubmissionNegotiation.getRequirementsubmissionId().getId());
		 EmailMessage emailMessage = new EmailMessage();
		 emailMessage.setFirstName(requirementSubmissionTemplateFields.getFirstName());
		 emailMessage.setLastName(requirementSubmissionTemplateFields.getLastName());
		 emailMessage.setToEmail(requirementSubmissionTemplateFields.getEmailId());
		 emailMessage.setFromEmail(user.getEmail());
		 emailMessage.setCreatedBy(user.getUserCode());
		 if(user.getFirstName()!= null && !"".equalsIgnoreCase(user.getFirstName()) && user.getLastName()!= null && !"".equalsIgnoreCase(user.getLastName())){
			 StringBuilder msgBuild = new StringBuilder();
			 emailMessage.setFromName(msgBuild.append(user.getFirstName()).append(" ").append(user.getLastName()).toString());
		 }else{
			 emailMessage.setFromName(user.getFirstName());
		 }
		 emailMessage.setEmailType(Constants.CUSTOM_USER_EMAIL);

		 
		//setting the class filed values
		 Map<String, String> globalVarsMap = new HashMap<String, String>();

		 if(null != requirement.getJobTitle() && !"".equalsIgnoreCase(requirement.getJobTitle())){
			 globalVarsMap.put("requirementName", requirement.getJobTitle());
			 }else{
				 globalVarsMap.put("requirementName","--");
			 }
		 if(null != requirementSubmissionTemplateFields.getFirstName() && null != requirementSubmissionTemplateFields.getLastName() && !"".equalsIgnoreCase(requirementSubmissionTemplateFields.getFirstName()) && !"".equalsIgnoreCase(requirementSubmissionTemplateFields.getLastName())){
			 StringBuilder candBuild = new StringBuilder();
			 globalVarsMap.put("applicantName", candBuild.append(requirementSubmissionTemplateFields.getFirstName()).append("").append(requirementSubmissionTemplateFields.getLastName()).toString());
			 }else{
				 globalVarsMap.put("applicantName",requirementSubmissionTemplateFields.getFirstName());
			 }
		 if(null != user.getFirstName() && !"".equalsIgnoreCase(user.getFirstName()) && null != user.getLastName() && !"".equalsIgnoreCase(user.getLastName())){
			 StringBuilder sb = new StringBuilder();
			 globalVarsMap.put("userName", sb.append(user.getFirstName()).append(" ").append(user.getLastName()).toString());
			 }else{
				 globalVarsMap.put("userName",user.getFirstName());
			 }
		 if(null != requirementRateSubmissionNegotiation.getCurrentRate() && !"".equalsIgnoreCase(requirementRateSubmissionNegotiation.getCurrentRate()) && null != requirementRateSubmissionNegotiation.getCurrentRateType() && !"".equalsIgnoreCase(requirementRateSubmissionNegotiation.getCurrentRateType())){
			 StringBuilder sb = new StringBuilder();
			 globalVarsMap.put("rejectRate", sb.append(requirementRateSubmissionNegotiation.getCurrentRate()).append(" / ").append(requirementRateSubmissionNegotiation.getCurrentRateType()).toString());
			 }else{
				 globalVarsMap.put("rejectRate","--");
			 }
		 
		 globalVarsMap.put("negotiationTable",tempHTML.toString());
		 
		 if(rejectreason != null && !"".equalsIgnoreCase(rejectreason)){
			 globalVarsMap.put("rejectedReason",rejectreason);
			 }else{
				 globalVarsMap.put("rejectedReason","--");
			 }
		 
		 User toUser = orgLocations.getUser();
		 
		 String actionLink="";
		//calling the sendEmail method of emailScenerioUtil
		 commonUtil.sendTemplateEmail(emailMessage, globalVarsMap,orgLocations.getOrg(),orgLocations,Constants.USER_REJECT_RATE_NEGOTIATION,new ArrayList<EmailAttachment>());
		
	}

	@Override
	public void clientOrVendorAcceptOrRejectRateNegotiation(Org loginOrg, User users, User pastUser,
			RequirementSubmission requirementSubmission, Candidate candidate, StringBuilder tempHTML,
			OrgLocations loginOrgLocation,String type, String status) throws Exception {

		RequirementSubmissionTemplateFields requirementSubmissionTemplateFields = requirementSubmissionTemplateFieldsService.getSubmissionsTemplateFieldsByRequirementSubmissionsId(requirementSubmission.getId());
		
		EmailMessage emailMessage = new EmailMessage();
		 emailMessage.setFirstName(pastUser.getFirstName());
		 emailMessage.setLastName(pastUser.getLastName());
		 emailMessage.setToEmail(pastUser.getEmail());
		 emailMessage.setFromEmail(users.getEmail());
		 emailMessage.setCreatedBy(users.getUserCode());
		 StringBuilder msgBuild = new StringBuilder();
		 emailMessage.setFromName(msgBuild.append(users.getFirstName()).append(" ").append(users.getLastName()).toString());
		 emailMessage.setEmailType(Constants.CUSTOM_USER_EMAIL);

		 
		 
		//setting the class filed values
		 Map<String, String> globalVarsMap = new HashMap<String, String>();
		 
		 if(Constants.CLIENT.equalsIgnoreCase(type)){
			 if(null!= pastUser.getFirstName() && !"".equalsIgnoreCase(pastUser.getFirstName()) && null != pastUser.getLastName() && !"".equalsIgnoreCase(pastUser.getLastName())){
				 StringBuilder pastUserBuild = new StringBuilder();
				 globalVarsMap.put("vendorName", pastUserBuild.append(pastUser.getFirstName()).append(" ").append(pastUser.getLastName()).toString());
				 }else{
					 globalVarsMap.put("vendorName",pastUser.getFirstName());
				 }
			 
			 if(null != users.getFirstName() && !"".equalsIgnoreCase(users.getFirstName()) && null != users.getLastName() && !"".equalsIgnoreCase(users.getLastName())){
				 StringBuilder userBuild = new StringBuilder();
				 globalVarsMap.put("clientName", userBuild.append(users.getFirstName()).append(" ").append(users.getLastName()).toString());
				 }else{
					 globalVarsMap.put("clientName",users.getFirstName());
				 }
			 
			 if(null != requirementSubmission.getRequirement().getJobTitle() && !"".equalsIgnoreCase(requirementSubmission.getRequirement().getJobTitle())){
				 globalVarsMap.put("requirementName", requirementSubmission.getRequirement().getJobTitle());
				 }else{
					 globalVarsMap.put("requirementName","--");
				 }
			
			 if(null != requirementSubmission.getFinalizedRate() && !"".equalsIgnoreCase(requirementSubmission.getFinalizedRate()) && null != requirementSubmission.getFinalizedRateType() && !"".equalsIgnoreCase(requirementSubmission.getFinalizedRateType())){
				 StringBuilder sb = new StringBuilder();
				 globalVarsMap.put("acceptedRate", sb.append(requirementSubmission.getFinalizedRate()).append(" / ").append(requirementSubmission.getFinalizedRateType()).toString());
				 }else{
					 globalVarsMap.put("acceptedRate","--");
				 }
			 
			 if(null != requirementSubmissionTemplateFields.getFirstName() && null!= requirementSubmissionTemplateFields.getLastName() && !"".equalsIgnoreCase(requirementSubmissionTemplateFields.getFirstName()) && !"".equalsIgnoreCase(requirementSubmissionTemplateFields.getLastName())){
				 StringBuilder candiBuild = new StringBuilder();
				 globalVarsMap.put("applicantName", candiBuild.append(requirementSubmissionTemplateFields.getFirstName()).append(" ").append(requirementSubmissionTemplateFields.getLastName()).toString());
				 }else{
					 globalVarsMap.put("applicantName",requirementSubmissionTemplateFields.getFirstName());
				 }
			 if(null != loginOrgLocation.getOrgLocationName() && !"".equalsIgnoreCase(loginOrgLocation.getOrgLocationName())){
				 globalVarsMap.put("clientOrg", loginOrgLocation.getOrgLocationName());
				 }else{
					 globalVarsMap.put("clientOrg","--");
				 }
		 }else if(Constants.VENDOR.equalsIgnoreCase(type)){
			 
			 if(null!= pastUser.getFirstName() && !"".equalsIgnoreCase(pastUser.getFirstName()) && null != pastUser.getLastName() && !"".equalsIgnoreCase(pastUser.getLastName())){
				 StringBuilder pastUserBuild = new StringBuilder();
				 globalVarsMap.put("clientName", pastUserBuild.append(pastUser.getFirstName()).append(" ").append(pastUser.getLastName()).toString());
				 }else{
					 globalVarsMap.put("clientName",pastUser.getFirstName());
				 }
			 
			 if(null != users.getFirstName() && !"".equalsIgnoreCase(users.getFirstName()) && null != users.getLastName() && !"".equalsIgnoreCase(users.getLastName())){
				 StringBuilder userBuild = new StringBuilder();
				 globalVarsMap.put("vendorName", userBuild.append(users.getFirstName()).append(" ").append(users.getLastName()).toString());
				 }else{
					 globalVarsMap.put("vendorName",users.getFirstName());
				 }
			 
			 if(null != requirementSubmission.getRequirement().getJobTitle() && !"".equalsIgnoreCase(requirementSubmission.getRequirement().getJobTitle())){
				 globalVarsMap.put("requirementName", requirementSubmission.getRequirement().getJobTitle());
				 }else{
					 globalVarsMap.put("requirementName","--");
				 }
			
			 if(null != requirementSubmission.getFinalizedRate() && !"".equalsIgnoreCase(requirementSubmission.getFinalizedRate()) && null != requirementSubmission.getFinalizedRateType() && !"".equalsIgnoreCase(requirementSubmission.getFinalizedRateType())){
				 StringBuilder sb = new StringBuilder();
				 globalVarsMap.put("acceptedRate", sb.append(requirementSubmission.getFinalizedRate()).append(" / ").append(requirementSubmission.getFinalizedRateType()).toString());
				 }else{
					 globalVarsMap.put("acceptedRate","--");
				 }
			 
			 if(null != requirementSubmissionTemplateFields.getFirstName() && null!= requirementSubmissionTemplateFields.getLastName() && !"".equalsIgnoreCase(requirementSubmissionTemplateFields.getFirstName()) && !"".equalsIgnoreCase(requirementSubmissionTemplateFields.getLastName())){
				 StringBuilder candiBuild = new StringBuilder();
				 globalVarsMap.put("applicantName", candiBuild.append(requirementSubmissionTemplateFields.getFirstName()).append(" ").append(requirementSubmissionTemplateFields.getLastName()).toString());
				 }else{
					 globalVarsMap.put("applicantName",requirementSubmissionTemplateFields.getFirstName());
				 }
			 if(null != loginOrg.getOrgName() && !"".equalsIgnoreCase(loginOrgLocation.getOrgLocationName())){
				 globalVarsMap.put("loginOrg", loginOrgLocation.getOrgLocationName());
				 }else{
					 globalVarsMap.put("loginOrg","--");
				 }
		 }
		 
		 if(null != tempHTML){
			 globalVarsMap.put("negotiationTable",tempHTML.toString());
		 }
		 
		
		 //getting the current EmailScenario by EmailScenario unique name and entity type
		 EmailScenario notificationScenario = null;
		if(Constants.CLIENT.equalsIgnoreCase(type) && "Accepted".equalsIgnoreCase(status)){
			commonUtil.sendTemplateEmail(emailMessage, globalVarsMap,loginOrgLocation.getOrg(),loginOrgLocation,Constants.CLIENT_ACCEPT_RATE_NEGOTIATION,new ArrayList<EmailAttachment>());
			 notificationScenario  = emailScenarioService.getEmailScenarioByUniqueName(Constants.CLIENT_ACCEPT_RATE_NEGOTIATION,Constants.ENTITY_TYPE_NOTIFICATION);
		 }else if(Constants.VENDOR.equalsIgnoreCase(type) && "Accepted".equalsIgnoreCase(status)){
			commonUtil.sendTemplateEmail(emailMessage, globalVarsMap,loginOrgLocation.getOrg(),loginOrgLocation,Constants.VENDOR_ACCEPT_RATE_NEGOTIATION,new ArrayList<EmailAttachment>());
			 notificationScenario  = emailScenarioService.getEmailScenarioByUniqueName(Constants.VENDOR_ACCEPT_RATE_NEGOTIATION,Constants.ENTITY_TYPE_NOTIFICATION);
		 }
		 
		 if(Constants.CLIENT.equalsIgnoreCase(type) && "Rejected".equalsIgnoreCase(status)){
			commonUtil.sendTemplateEmail(emailMessage, globalVarsMap,loginOrgLocation.getOrg(),loginOrgLocation,Constants.CLIENT_REJECT_RATE_NEGOTIATION,new ArrayList<EmailAttachment>());

			 notificationScenario  = emailScenarioService.getEmailScenarioByUniqueName(Constants.CLIENT_REJECT_RATE_NEGOTIATION,Constants.ENTITY_TYPE_NOTIFICATION);
		 }else if(Constants.VENDOR.equalsIgnoreCase(type) && "Rejected".equalsIgnoreCase(status)){
				commonUtil.sendTemplateEmail(emailMessage, globalVarsMap,loginOrgLocation.getOrg(),loginOrgLocation,Constants.VENDOR_REJECT_RATE_NEGOTIATION,new ArrayList<EmailAttachment>());

			 notificationScenario  = emailScenarioService.getEmailScenarioByUniqueName(Constants.VENDOR_REJECT_RATE_NEGOTIATION,Constants.ENTITY_TYPE_NOTIFICATION);
		 }
		 
		 
		 String notificationbody = emailScenerioUtil.buildTemplateBody(notificationScenario.getBody(),globalVarsMap,notificationScenario);
		 notificationScenario.setBody(notificationbody);
		  
		 String actionLink="";
		//calling the sendEmail method of emailScenerioUtil
		 
		 if(Constants.CLIENT.equalsIgnoreCase(type) && "Accepted".equalsIgnoreCase(status)){
		 emailScenerioUtil.sendNotification(actionLink, users, pastUser, requirementSubmission.getVendorLocationId(), notificationScenario,new HashMap<String,String>());
		 }else if(Constants.VENDOR.equalsIgnoreCase(type) && "Accepted".equalsIgnoreCase(status)){
			 emailScenerioUtil.sendNotification(actionLink, users, pastUser, requirementSubmission.getOrgLocations(), notificationScenario,new HashMap<String,String>());
		 }
		 if(Constants.CLIENT.equalsIgnoreCase(type) && "Rejected".equalsIgnoreCase(status)){
			 emailScenerioUtil.sendNotification(actionLink, users, pastUser, requirementSubmission.getVendorLocationId(), notificationScenario,new HashMap<String,String>());
		 }else if(Constants.VENDOR.equalsIgnoreCase(type) && "Rejected".equalsIgnoreCase(status)){
			 emailScenerioUtil.sendNotification(actionLink, users, pastUser, requirementSubmission.getOrgLocations(), notificationScenario,new HashMap<String,String>());
		 }
	}

	@Override
	public List<Long> getSubmittedCandidateIdsByRequirementId(Long requirementId) throws Exception {
		return requirementSubmissionDao.getSubmittedCandidateIdsByRequirementId(requirementId);
	}

	@Override
	public Integer getRequirementPerCandidateCount(Long candudateId,Long orgLocationId) throws Exception {
		return requirementSubmissionDao.getRequirementPerCandidateCount(candudateId,orgLocationId);
	}

	@Override
	public Integer getNumberOfApplicantsForRequirement(Long orgLocationId, Long requirementId)throws Exception {
		return requirementSubmissionDao.getNumberOfApplicantsForRequirement(orgLocationId,requirementId);
	}

	@Override
	public RequirementSubmission saveRequirementSubmissionFromCrowdSourcing(JSONObject jsonObj, User user,String jsonStringvalues,OrgLocations orgLocations) throws Exception {
		
		 Requirement requirement = null;;
		 RequirementSubmission requirementSubmission = new RequirementSubmission();
		 Candidate candidate = null;
		 CandidateResumes candidateResumes = null;
	//	 SimpleDateFormat simpleDateFormat = orgLocations.getLocFullFormDateFormatter();
		 if(jsonObj.has("requirementId")){
			 requirement = requirementDao.getRequirementByRequirementId(Long.parseLong(jsonObj.getString("requirementId")));
			 requirementSubmission.setRequirement(requirement);
			 requirementSubmission.setSubmissionTemplateJsonString(requirement.getSubmissionTemplateJsonString());
		 }
		 
		 if(jsonObj.has("candidateId") && !isNullOrEmpty(jsonObj.getString("candidateId"))){
			 candidate = candidateDao.getCandidateByCandidateId(Long.parseLong(jsonObj.getString("candidateId")));
			 candidate.setId(Long.parseLong(jsonObj.getString("candidateId")));
			 requirementSubmission.setCandidate(candidate);
		 }
		 
		 
		 if(jsonObj.has("candidateResume") && !isNullOrEmpty(jsonObj.getString("candidateResume"))){
			 candidateResumes = candidateResumesDao.getCandidateResumeByResumeId(Long.parseLong(jsonObj.getString("candidateResume")));
			 candidateResumes.setId(Long.parseLong(jsonObj.getString("candidateResume")));
			 requirementSubmission.setCandidateResumes(candidateResumes);
		 }else{
			 
		       if(jsonObj.has("FirstName") && !isNullOrEmpty(jsonObj.getString("FirstName"))){
				candidate.setFirstName(jsonObj.getString("FirstName"));
				}
				
		        if(jsonObj.has("LastName") && !isNullOrEmpty(jsonObj.getString("LastName"))){
				candidate.setLastName(jsonObj.getString("LastName") );
				}
		        
		        if(jsonObj.has("location") && !isNullOrEmpty(jsonObj.getString("location"))){
		        	candidate.setLocation(jsonObj.getString("location"));
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
				
				 if(jsonObj.has("zipcode") && !isNullOrEmpty(jsonObj.getString("zipcode"))){
			        	candidate.setZipCode(jsonObj.getString("zipcode"));
					}
				
				if(jsonObj.has("PhoneNumber") && !isNullOrEmpty(jsonObj.getString("PhoneNumber"))){
					 candidate.setMobilePhone(jsonObj.getString("PhoneNumber"));
				}
				
				if(jsonObj.has("AlternateNumber") && !isNullOrEmpty(jsonObj.getString("AlternateNumber"))){
					 candidate.setAlternateMobilePhone(jsonObj.getString("AlternateNumber"));
				}
				
				if(jsonObj.has("EmailId") && !isNullOrEmpty(jsonObj.getString("EmailId"))){
				candidate.setEmail(jsonObj.getString("EmailId"));
				}
				
				if(jsonObj.has("currentCTCRateType") && !isNullOrEmpty(jsonObj.getString("currentCTCRateType"))){
					candidate.setCurrentRateCurrency(jsonObj.getString("currentCTCRateType"));
				}
				
				if(jsonObj.has("CurrentCTC") && !isNullOrEmpty(jsonObj.getString("CurrentCTC"))){
					candidate.setCurrentRate(jsonObj.getString("CurrentCTC"));
				}
				
				if(jsonObj.has("currentCTCRatePer") && !isNullOrEmpty(jsonObj.getString("currentCTCRatePer"))){
					 candidate.setCurrentRateType(jsonObj.getString("currentCTCRatePer"));
				}
				
				if(jsonObj.has("expectedCTCRateType") && !isNullOrEmpty(jsonObj.getString("expectedCTCRateType"))){
					 candidate.setExpectedRateCurrencey(jsonObj.getString("expectedCTCRateType"));
					 
				}
				 
				if(jsonObj.has("ExpectedCTC") && !isNullOrEmpty(jsonObj.getString("ExpectedCTC"))){
					 candidate.setExpectedRate(jsonObj.getString("ExpectedCTC"));
					 
				}
				
				if(jsonObj.has("expectedCTCRatePer") && !isNullOrEmpty(jsonObj.getString("expectedCTCRatePer"))){
					 candidate.setRateType(jsonObj.getString("expectedCTCRatePer"));
				}
				
				if(jsonObj.has("Designation") && !isNullOrEmpty(jsonObj.getString("Designation"))){
					candidate.setDesignation(jsonObj.getString("Designation"));
				}
				 
				if(jsonObj.has("WorkExperience") && !isNullOrEmpty(jsonObj.getString("WorkExperience"))){
					 candidate.setExperienceYears(jsonObj.getString("WorkExperience"));
				 }
				 
				 
				if(jsonObj.has("monthExp") && !isNullOrEmpty(jsonObj.getString("monthExp"))){
					 candidate.setExperienceMonths(jsonObj.getString("monthExp"));
				 }
				
				 if(jsonObj.has("NoticePeriod") && !isNullOrEmpty(jsonObj.getString("NoticePeriod"))){
					 candidate.setNoticePeriod(jsonObj.getString("NoticePeriod"));
				 }
		 
		        if(jsonObj.has("candidateSkills") && !isNullOrEmpty(jsonObj.getString("candidateSkills"))){
			    String candidateSkills = null;
				try{
		    		   JSONArray jsonArray = jsonObj.getJSONArray("candidateSkills");
		    		   candidateSkills = jsonArray.toString();
		    	  }
		    	  catch(JSONException e){
		    		  candidateSkills= jsonObj.getString("candidateSkills");
		    	  }
				candidateSkills = candidateSkills.replaceAll("\"", "").replaceAll("\\[", "").replaceAll("\\]","");
			    candidate.setSkillsName(candidateSkills);
			    
			    if(jsonObj.has("primarySkill") && !isNullOrEmpty(jsonObj.getString("primarySkill"))){
					 candidate.setPrimarySkill(jsonObj.getString("primarySkill"));
				 }
			    
			    MasterCandidate masterCandidate = null;
				masterCandidate = masterCandidateService.getMasterCandidateByInteligence(jsonObj);
				if(null == masterCandidate){
					masterCandidate = masterCandidateService.storeMasterCandidateByJsonObject(jsonObj, user,orgLocations);
				}
				candidate.setMasterCandidateId(masterCandidate);
			    
			    candidateDao.storeCandidate(candidate);
				CandidateIndex candidateIndex = candidateIndexHelper.getCandidateIndexFromCandidate(candidate);
				sourceleadELKBaseIndexer.saveOrUpdateElkDocument(candidateIndex, Constants.ELK_CANDIDATE_INDEX, Constants.ELK_CANDIDATE_TYPE,candidate.getId().toString());
		 }
		 
		 
		 if(jsonObj.has("Education") && !isNullOrEmpty(jsonObj.getString("Education"))){
	    		
	    		String[] educationList = jsonObj.getString("Education").split(",");
	    		   
				for(int i=0; i<educationList.length; i++){
					CandidateEducation candidateEducation = new CandidateEducation();
					candidateEducation.setCandidateId(candidate);
					candidateEducation.setOrglocationId(orgLocations);
					candidateEducation.setName(educationList[i].startsWith(" ") ? educationList[i].substring(1) : educationList[i]);
					candidateEducation.setCreatedBy(user.getUserCode());
					candidateEducation.setCreatedDate(new Date());
					candidateEducation.setStatus(Constants.CANDIDATE_ACTIVE);
					candidateEducationDao.storeCandidateEducation(candidateEducation);
				}
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
			 
			 /*if(multiFile != null && multiFile.getOriginalFilename() != null && !multiFile.getOriginalFilename().isEmpty()){
				 candidateResumes = new CandidateResumes();
				 candidateResumes.setCandidateId(candidate);
				 //candidateResumes.setDocFile(multiFile.getBytes());
				 candidateResumes.setFileName(multiFile.getOriginalFilename());
				 candidateResumes.setTitle(multiFile.getOriginalFilename());
				 candidateResumes.setFileContentType(multiFile.getContentType());
				 candidateResumes.setStatus(Constants.CANDIDATE_ACTIVE);
				 candidateResumes.setResumeUUID(SourceLeadRandomCodeHelper.generateRandomUUID());
				 candidateResumes.setCreatedDate(new Date());
				 candidateResumes.setCreatedBy(user.getUserCode());
				 candidateResumes.setUpdatedDate(new Date());
				 candidateResumes.setOrgLocationId(orgLocations);
				 candidateResumesDao.storeCandidateResumes(candidateResumes);
				 requirementSubmission.setCandidateResumes(candidateResumes);
			 }*/
			 
			 
			 if((jsonObj.has("fileInfo[0].fileName") && !isNullOrEmpty(jsonObj.getString("fileInfo[0].fileName"))) && (jsonObj.has("fileInfo[0].fileURL") && !isNullOrEmpty(jsonObj.getString("fileInfo[0].fileURL"))) && (jsonObj.has("fileInfo[0].contentType") && !isNullOrEmpty(jsonObj.getString("fileInfo[0].contentType")))){
				 FileInfo fileInfo = new FileInfo();
				 fileInfo.setFileName(jsonObj.get("fileInfo[0].fileName").toString());
				 fileInfo.setFileURL(jsonObj.get("fileInfo[0].fileURL").toString());
				 fileInfo.setContentType(jsonObj.get("fileInfo[0].contentType").toString());
				 candidateResumes = new CandidateResumes();
 				 candidateResumes.setCandidateId(candidate);
				 candidateResumes.setFileName(jsonObj.get("fileInfo[0].fileName").toString());
				 candidateResumes.setTitle(jsonObj.get("fileInfo[0].fileName").toString());
				 candidateResumes.setFileContentType(jsonObj.get("fileInfo[0].contentType").toString());
				 candidateResumes.setFileURL(jsonObj.get("fileInfo[0].fileURL").toString());
 				 candidateResumes.setStatus(Constants.CANDIDATE_ACTIVE);
 				 candidateResumes.setResumeUUID(SourceLeadRandomCodeHelper.generateRandomUUID());
 				 candidateResumes.setCreatedDate(new Date());
				 candidateResumes.setCreatedBy(user.getUserCode());
				 candidateResumes.setUpdatedDate(new Date());
				 candidateResumes.setOrgLocationId(orgLocations);
				 FileUploadUtil.moveFile(staticPath, fileInfo, "addApplicants");
				 candidateResumesDao.storeCandidateResumes(candidateResumes);
				 requirementSubmission.setCandidateResumes(candidateResumes);
			 }
		 
	 }
		 
		 requirementSubmission.setOrgLocations(requirement.getOrgLocationId());
		 requirementSubmission.setSubmissionTemplateJsonStringValue(jsonStringvalues);
		 
		 /* the functionality of this block is to verify the submitted candidate is in the Cooling-Off Period or not
		 if the applicant is in cooling-off period, the candidate should be in rejected state(set previous state to Submission) at client side  [Venkat Kesav] START Code snippet*/
		 RequirementSubmissionHistory requirementSubmissionHistoryForCoolingOff = null;
			 String reqCoolingOffPeriod = requirement.getReqCoolingOffPeriod();
			 String [] coolingOffArr = reqCoolingOffPeriod.split(" ");
			 String reqCoolingOffPeriodDays;
			 int Days;
			 switch (coolingOffArr[1]) {
				 case "Week" :
					 reqCoolingOffPeriodDays = "7";
				      break;
				 case "Weeks" :
					 Days  = (Integer.parseInt(coolingOffArr[0]) * 7);
					 reqCoolingOffPeriodDays = Integer.toString(Days);
				     break;
				 case "Month" :
					 reqCoolingOffPeriodDays = "30";
				     break;
				 case "Months" :
					 Days  = (Integer.parseInt(coolingOffArr[0]) * 30);
					 reqCoolingOffPeriodDays = Integer.toString(Days);
				     break;
				 case "Year" :
					 reqCoolingOffPeriodDays = "365";
				     break;
				 default:
					 reqCoolingOffPeriodDays = "0";
			 }
			 requirementSubmission.setSubmissionStatus(ApplicantSubmissionStatus.SUBMISSION.toString());
			 requirementSubmissionHistoryForCoolingOff = requirementSubmissionHistoryService.isInCoolingOffPeriodByEmailAndLocationsId(requirementSubmission.getCandidate().getEmail(), requirement.getOrgLocationId(), reqCoolingOffPeriodDays, requirement.getId());
			 if(requirementSubmissionHistoryForCoolingOff != null){
				 if(requirementSubmissionHistoryForCoolingOff.getIsInCoolingOffPeriod()){
					 requirementSubmission.setSubmissionStatus(ApplicantSubmissionStatus.REJECTED.toString());
					 requirementSubmission.setPreviousSubmissionStatus(ApplicantSubmissionStatus.SUBMISSION.toString());
					 requirementSubmission.setRejectedReason("Under cooling off period, completed by "+requirementSubmissionHistoryForCoolingOff.getCoolingOffPeriodEndDateStr()+".");
					 requirementSubmission.setRejectedCategoryType("Other");
					 requirementSubmission.setRejectedCategoryOther("Under cooling off period");
				 }
			 }else{
				 requirementSubmission.setSubmissionStatus(ApplicantSubmissionStatus.SUBMISSION.toString());
			 }
			 /*END Code snippet*/
			 
		 requirementSubmission.setStatus(Constants.CANDIDATE_ACTIVE);
		 requirementSubmission.setCreatedBy(user.getUserCode());
		 requirementSubmission.setCreatedDate(new Date());
		 requirementSubmission.setUpdatedDate(commonUtil.currentDate());
		 RecommendedCustomizeScore recommendedCustomizeScore = recommendedCustomizeScoreService.getRecommendedCustomizeScoreByRequirementId(requirementSubmission.getRequirement().getId());
		 String score = recommendationsService.getCandidateScore(candidate,requirementSubmission.getRequirement(),recommendedCustomizeScore);
		 if(null != score){
			 requirementSubmission.setCandidateScore(score);
		 }
		 
		 
		 StringBuilder userName = new StringBuilder();
		 StringBuilder nameBuilder = new StringBuilder();
		 
		 if(!isNullOrEmpty(user.getLastName())){
			 userName = nameBuilder.append(user.getFirstName()).append(" ").append(user.getLastName());
	   	 }else{
	   		 userName = nameBuilder.append(user.getFirstName());
	   	 }
		 
		 requirementSubmission.setCreatedUser(userName.toString());
		 requirementSubmission.setUpdatedUser(userName.toString());
		 
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
		 
		 if(jsonObj.has("location") && !isNullOrEmpty(jsonObj.getString("location"))){
			 templateFields.setLocation(jsonObj.getString("location"));
		 }
		 
		 if(jsonObj.has("zipcode") && !isNullOrEmpty(jsonObj.getString("zipcode"))){
			 templateFields.setZipCode(jsonObj.getString("zipcode"));
		 }
		 
		 if(jsonObj.has("DOB") && !isNullOrEmpty(jsonObj.getString("DOB"))){
				try {
					   templateFields.setDateOfBirth(	commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),jsonObj.getString("DOB")));
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
			 StringBuilder sb = new StringBuilder();
			 if(jsonObj.has("monthExp") && !isNullOrEmpty(jsonObj.getString("monthExp"))){
				 templateFields.setWorkExperience(sb.append(jsonObj.getString("WorkExperience")).append(".").append(jsonObj.getString("monthExp")).toString());
			 }else{
				 templateFields.setWorkExperience(sb.append(jsonObj.getString("WorkExperience")).toString());
			 }
		 }
		 
		 if(jsonObj.has("Education") && !isNullOrEmpty(jsonObj.getString("Education"))){
			 templateFields.setEducation(jsonObj.getString("Education"));
		 }
		 
		 
		 if(jsonObj.has("currentCTCRateType") && !isNullOrEmpty(jsonObj.getString("currentCTCRateType"))){
			 templateFields.setCurrentRateType(jsonObj.getString("currentCTCRateType"));
		 }
		 
		 
		 if(jsonObj.has("CurrentCTC") && !isNullOrEmpty(jsonObj.getString("CurrentCTC"))){
			 templateFields.setCurrentCTC(jsonObj.getString("CurrentCTC"));
		 }
		 
		 if(jsonObj.has("expectedCTCRateType") && !isNullOrEmpty(jsonObj.getString("expectedCTCRateType"))){
			 templateFields.setExpectedRateType(jsonObj.getString("expectedCTCRateType"));
		 }
		 
		 if(jsonObj.has("ExpectedCTC") && !isNullOrEmpty(jsonObj.getString("ExpectedCTC"))){
			 templateFields.setExpectedCTC(jsonObj.getString("ExpectedCTC"));
		 }
			
		 if(jsonObj.has("currentCTCRatePer") && !isNullOrEmpty(jsonObj.getString("currentCTCRatePer"))){
			templateFields.setCurrentRatePer(jsonObj.getString("currentCTCRatePer"));
		 }
			
		 if(jsonObj.has("expectedCTCRatePer") && !isNullOrEmpty(jsonObj.getString("expectedCTCRatePer"))){
			 templateFields.setExpectedRatePer(jsonObj.getString("expectedCTCRatePer"));
		 }
		 
		 if(jsonObj.has("NoticePeriod") && !isNullOrEmpty(jsonObj.getString("NoticePeriod"))){
			 templateFields.setNoticePeriod(jsonObj.getString("NoticePeriod"));
		 }
		 
		 templateFields.setStatus(Constants.CANDIDATE_ACTIVE);
		 templateFields.setCreatedBy(user.getUserCode());
		 templateFields.setCreatedDate(new Date());
		 templateFields.setUpdatedDate(commonUtil.currentDate());
		 requirementSubmissionTemplateFieldsDao.storeRequirementSubmissionTemplateFields(templateFields);
		 
		 
		 if(null != requirement){
				
				if(null == requirement.getAppliedProfessionals()){
					requirement.setAppliedProfessionals(user.getId().toString());
				}else{
					List<Long> userId = new ArrayList<Long>();
					for (String s : requirement.getAppliedProfessionals().split(",")){
						userId.add(Long.parseLong(s));
					 }
					boolean flag = userId.contains(user.getId());
	         		if(flag == false){
	         			StringBuilder appliedUser = new StringBuilder();
	         			appliedUser = appliedUser.append(",").append(user.getId().toString());
	    				requirement.setAppliedProfessionals(requirement.getAppliedProfessionals()+appliedUser.toString());
	         		}
				}
				
				Integer totalApplicatsCountForRequirement = 0;
				totalApplicatsCountForRequirement = requirementSubmissionDao.getNumberOfApplicantsForRequirement(requirement.getOrgLocationId().getId(), requirement.getId());
				if(totalApplicatsCountForRequirement > 0){
					requirement.setTotalApplicants(totalApplicatsCountForRequirement); 
				}else{
					requirement.setTotalApplicants(0); 
				}
				
				requirement.setUpdatedDate(new Date());
				requirementService.updateRequirement(requirement);
				
				RequirementDTO requirementDTO =commonUtil.getRequirementDTO(requirement);
				requirementElkUtil.saveElkRequirement(requirement,requirementDTO);	
			}
			
			
			 if(jsonObj.has("referId") && !isNullOrEmpty(jsonObj.getString("referId"))){
			    
				RequirementRefer refer = requirementReferDao.getRequirementReferById(Long.valueOf(jsonObj.getString("referId")));
			    refer.setReferStatus("Applied");
			    requirementReferDao.addRequirementRefer(refer);
			 }
		 
		 
			sendEmailToProfessionalForAppliedJob(user, requirement);
		 
		    return requirementSubmission;
		 
	}
	
	
	
	public void sendEmailToProfessionalForAppliedJob(User user, Requirement requirement) throws Exception {
		
		 
		 StringBuilder nameBuilder = new StringBuilder();
		 StringBuilder expBuilder = new StringBuilder();
		 StringBuilder locationBuilder = new StringBuilder();
		 EmailMessage emailMessage = new EmailMessage();
		 String professionalName = null;
		 String experience = null;
		 
		 if(!isNullOrEmpty(user.getLastName())){
			 professionalName = nameBuilder.append(user.getFirstName()).append(" ").append(user.getLastName()).toString();
		 }else{
			 professionalName = nameBuilder.append(user.getFirstName()).toString();
		 }
		 
		 if(!isNullOrEmpty(requirement.getMinExp()) && !isNullOrEmpty(requirement.getMaxExp())){
			 experience = expBuilder.append(requirement.getMinExp()).append(" to ").append(requirement.getMaxExp()).append(" Years").toString();
		 }else if(!isNullOrEmpty(requirement.getMinExp())){
			 experience = expBuilder.append(requirement.getMinExp()).append(" Years").toString();
		 } else if(!isNullOrEmpty(requirement.getMaxExp())){
			 experience = expBuilder.append(requirement.getMaxExp()).append(" Years").toString();
		 }else{
			 experience = "Not Mentioned";
		 }
		 
		/* if(!isNullOrEmpty(requirement.getCity())){
			 location = locationBuilder.append(requirement.getCountry()).append(", ").append(requirement.getState()).append(", ").append(requirement.getCity()).toString();
		 }else if(!isNullOrEmpty(requirement.getState()) && isNullOrEmpty(requirement.getCity())){
			 location = locationBuilder.append(requirement.getCountry()).append(", ").append(requirement.getState()).append(", ").toString();
		 }else{
			 location = locationBuilder.append(requirement.getCountry()).toString();
		 }*/
		 
		 
		 List<String> requirementLocationsList = new ArrayList<>();
			List<RequirementLocations> locationsList;
			String location = "";
			StringBuilder requirementBuilder=new StringBuilder();
			try {
				locationsList = requirementLocationsService.getRequirementLocationsListByRequirementId(requirement.getId());
				if(locationsList != null && !locationsList.isEmpty()) {
					for(RequirementLocations locationsJob : locationsList) {
						requirementLocationsList.add(locationsJob.getLocation());
						if(locationsJob.getLocation() !=null && locationsJob.getLocation() !=null){
							location = requirementBuilder.append(locationsJob.getLocation()).toString();
						}else{
							location = "--";
						}
					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		emailMessage.setFirstName(user.getFirstName());
		 emailMessage.setToEmail(user.getEmail());
		 emailMessage.setFromName("Sourcelead Admin");
		 emailMessage.setCreatedBy("sladmin");
		 if (environment.equalsIgnoreCase("prod")) {
			 emailMessage.setFromEmail("info@sourcelead.com");
		 }else{
			 emailMessage.setFromEmail("qa@sourcelead.com");	 
		 }
		//setting the class filed values
		 Map<String, String> globalVarsMap = new HashMap<String, String>();
		 globalVarsMap.put("professionalName", professionalName);
		 globalVarsMap.put("employerName", requirement.getPostedOrgName());
		// globalVarsMap.put("requirementTitle", requirement.getJobTitle());
		 globalVarsMap.put("experience", experience);
		 if(location !=null && !"".equalsIgnoreCase(location)){
			 globalVarsMap.put("location", location); 
		 }
		 globalVarsMap.put("professionalEmail", user.getEmail());
		 
		 if(requirement.getJobId() !=null && !"".equalsIgnoreCase(requirement.getJobId())){
			 StringBuilder reqsb=new StringBuilder();
			 
			 globalVarsMap.put("requirementTitle",reqsb.append(requirement.getJobId()).append("-").append(requirement.getJobTitle()).toString());
		 }
		 
		 else{
			 globalVarsMap.put("requirementTitle", requirement.getJobTitle()); 
			 
		 }
		 //setting the Recipient information
		 InternetAddress[] iAdressArray = InternetAddress.parse(emailMessage.getToEmail());
		
		 //getting the current EmailScenario by EmailScenario unique name and entity type
		 EmailScenario  emailScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.APPLIED_TO_REQUIREMENT,Constants.ENTITY_TYPE_EMAIL);
		
		 //covert subject,body and signature
		 String subject = emailScenerioUtil.bulidTemplateSubject(emailScenario.getSubject(),globalVarsMap,emailScenario);
		 String body = emailScenerioUtil.buildTemplateBody(emailScenario.getBody(),globalVarsMap,emailScenario);
		 String signature = emailScenerioUtil.buildTemplateSignature(emailScenario.getSignature(),globalVarsMap,emailScenario);
		 String url = sourceleadUrl;
		 
		 //construct HTML template using required data
		 String emailTemplate = emailScenerioUtil.AssembleEmailMessage(emailScenario,emailMessage, subject, body, signature, url, new Org(),new OrgLocations());
		 emailMessage.setSubject(subject);
		 emailMessage.setMessageBody(emailTemplate);
		 
		 //calling the sendEmail method of emailScenerioUtil
		 emailScenerioUtil.sendEmail(emailMessage,1L,iAdressArray,new ArrayList<EmailAttachment>());
		 
		
	}

	@Override
	public void applicantAcceptMailAboutRateNegotiation(Requirement requirement, User initiatorUser, RequirementSubmission requirementSubmission,
			OrgLocations orgLocations,StringBuilder tempHTML) throws Exception {
		 
		RequirementSubmissionTemplateFields requirementSubmissionTemplateFields = requirementSubmissionTemplateFieldsService.getSubmissionsTemplateFieldsByRequirementSubmissionsId(requirementSubmission.getId());
		 EmailMessage emailMessage = new EmailMessage();
		/*
		 * when professional apply for job, submission createdBy user is Same.
		 * in this scenario mail is forwarded to same user(professional). 
		 * to avoid this we took requirement manager (@venkat Kesav)
		 */
		 if(initiatorUser.getEmail().equalsIgnoreCase(requirementSubmissionTemplateFields.getEmailId())){
			 emailMessage.setToEmail(requirementSubmissionTemplateFields.getRequirementSubmission().getRequirement().getManagerUserId().getEmail());
			 emailMessage.setFirstName(requirementSubmissionTemplateFields.getRequirementSubmission().getRequirement().getManagerUserId().getFirstName());
			 emailMessage.setLastName(requirementSubmissionTemplateFields.getRequirementSubmission().getRequirement().getManagerUserId().getLastName());
		 }else{
			 emailMessage.setToEmail(initiatorUser.getEmail());
			 emailMessage.setFirstName(initiatorUser.getFirstName());
			 emailMessage.setLastName(initiatorUser.getLastName());
		 }
		 emailMessage.setFromEmail(requirementSubmissionTemplateFields.getEmailId());
		 emailMessage.setCreatedBy(initiatorUser.getUserCode());
		 emailMessage.setEmailType(Constants.CUSTOM_USER_EMAIL);

		 if(requirementSubmissionTemplateFields.getFirstName() != null && requirementSubmissionTemplateFields.getLastName()!= null && !"".equalsIgnoreCase(requirementSubmissionTemplateFields.getFirstName()) && !"".equalsIgnoreCase(requirementSubmissionTemplateFields.getLastName())){
			 StringBuilder msgBuild = new StringBuilder();
			 emailMessage.setFromName(msgBuild.append(requirementSubmissionTemplateFields.getFirstName()).append(" ").append(requirementSubmissionTemplateFields.getLastName()).toString());
		 }else{
			 emailMessage.setFromName(requirementSubmissionTemplateFields.getFirstName());
		 }
		//setting the class filed values
		 Map<String, String> globalVarsMap = new HashMap<String, String>();

		 if(emailMessage.getLastName()!= null && !"".equalsIgnoreCase(emailMessage.getLastName())){
			 StringBuilder sb = new StringBuilder();
			 globalVarsMap.put("userName", sb.append(emailMessage.getFirstName()).append(" ").append(emailMessage.getLastName()).toString());
			 }else{
				 globalVarsMap.put("userName",emailMessage.getFirstName());
			 }
		 
		 if(requirement.getJobTitle()!= null && !"".equalsIgnoreCase(requirement.getJobTitle())){
			 globalVarsMap.put("requirementName", requirement.getJobTitle());
			 }else{
				 globalVarsMap.put("requirementName","--");
			 }
		
		 if(requirementSubmissionTemplateFields.getFirstName() != null && requirementSubmissionTemplateFields.getLastName()!= null && !"".equalsIgnoreCase(requirementSubmissionTemplateFields.getFirstName()) && !"".equalsIgnoreCase(requirementSubmissionTemplateFields.getLastName())){
			 StringBuilder candBuild = new StringBuilder();
			 globalVarsMap.put("applicantName", candBuild.append(requirementSubmissionTemplateFields.getFirstName()).append("").append(requirementSubmissionTemplateFields.getLastName()).toString());
			 }else{
				 globalVarsMap.put("applicantName",requirementSubmissionTemplateFields.getFirstName());
			 }
		 
		 if(requirement.getRate()!= null && !"".equalsIgnoreCase(requirement.getRate()) && requirementSubmission.getFinalizedRateType() != null && !"".equalsIgnoreCase(requirementSubmission.getFinalizedRateType())){
			 StringBuilder rateBuild = new StringBuilder();
			 globalVarsMap.put("finalRate", rateBuild.append(requirementSubmission.getFinalizedRate()).append(" / ").append(requirementSubmission.getFinalizedRateType()).toString());
			 }else{
				 globalVarsMap.put("finalRate",requirementSubmission.getFinalizedRate());
			 }
		
		 if(null != tempHTML){
			 globalVarsMap.put("negotiationTable",tempHTML.toString());
		 }
		 
		
		  
		 User toUser = orgLocations.getUser();
		 
		 String actionLink="";
		//calling the sendEmail method of emailScenerioUtil
		 commonUtil.sendTemplateEmail(emailMessage, globalVarsMap,orgLocations.getOrg(),orgLocations,Constants.APPLICANT_ACCEPT_RATE,new ArrayList<EmailAttachment>());
	}

	@Override
	public RequirementSubmission getRequirementSubmissionByRequirementIdAndCandidateId(Long requirementId,
			Long candidateId) throws Exception {
		return requirementSubmissionDao.getRequirementSubmissionByRequirementIdAndCandidateId(requirementId, candidateId);
	}
	
	@Override
	public void sendRejectionMailToVendorContact(User user, String reason,Long submissionId,OrgLocations orgLocations) throws Exception {
		
		RequirementSubmission submission = getRequirementSubmissionById(submissionId);
		//URL for page redirection
		String redirectURL = "/requirementSummary-params-requirementUUID="+submission.getSourceSubmission().getRequirement().getRequirementUUID()+"-and-submissionId="+submission.getSourceSubmission().getId()+"-and-candidateUUID="+submission.getCandidate().getCandidateUUID()+"-and-submissionStatus=REJECTED";		
		Long redirectOrgLocationId = submission.getSourceSubmission().getRequirement().getOrgLocationId().getId();
		if(!submission.getCreatedBy().equalsIgnoreCase(submission.getUpdatedBy())){
		  User userVendor = userService.getUserByUserCode(submission.getCreatedBy());
		  User userClient  = userService.getUserByUserCode(submission.getUpdatedBy());
    	 EmailMessage emailMessage = new EmailMessage();
		 emailMessage.setFirstName(userVendor.getFirstName());
		 emailMessage.setLastName(userVendor.getLastName());
		 emailMessage.setToEmail(userVendor.getEmail());
		 emailMessage.setFromEmail(userClient.getEmail());
		 emailMessage.setCreatedBy(user.getUserCode());
		 StringBuilder msgBuild = new StringBuilder();
		 emailMessage.setFromName(msgBuild.append(userClient.getFirstName()).append(" ").append(userClient.getLastName()).toString());
		 emailMessage.setEmailType(Constants.CUSTOM_USER_EMAIL);

		//setting the class filed values
		 Map<String, String> globalVarsMap = new HashMap<String, String>();
		 if(null!= userVendor.getFirstName()  && !"".equalsIgnoreCase(userVendor.getFirstName())){
		 globalVarsMap.put("vendorContactName", userVendor.getFirstName()); 
		 }else{
		 globalVarsMap.put("vendorContactName", "--");	 
		 }
		 
		 if(null !=submission.getCandidate().getFirstName()  && !"".equalsIgnoreCase(submission.getCandidate().getFirstName())){
		 globalVarsMap.put("applicantName", submission.getCandidate().getFirstName());	 
		 }else{
	     globalVarsMap.put("applicantName","--"); 
		 }
		 if(userClient.getFirstName()!= null  && !"".equalsIgnoreCase(userClient.getFirstName())){
		 globalVarsMap.put("clientContactName", userClient.getFirstName());	 
		 }else{
		 globalVarsMap.put("clientContactName","--"); 
		 }
		 
		 if(null != submission.getRequirement().getJobTitle()  && !"".equalsIgnoreCase(submission.getRequirement().getJobTitle())){
		 globalVarsMap.put("requirementName", submission.getRequirement().getJobTitle()); 
		 }else{
		 globalVarsMap.put("requirementName","--"); 
		 }
		 if(null != reason && !"".equalsIgnoreCase(reason)){
		 globalVarsMap.put("reason", reason);
		 }else{
		 globalVarsMap.put("reason","--"); 
		 }
		 
		 if(null != submission.getOrgLocations().getOrgLocationName() && !"".equalsIgnoreCase(submission.getOrgLocations().getOrgLocationName())){
		 globalVarsMap.put("clientOrgName", submission.getOrgLocations().getOrgLocationName());
		 }else{
		 globalVarsMap.put("clientOrgName","--"); 
		 }		 
		 StringBuilder linkBuild = new StringBuilder();
		 globalVarsMap.put("loginURL", linkBuild.append("<a href='").append(sourceleadLoginUrl+ "?redirectURL=" +atsUrl+redirectURL+"&redirectOrgLocationId="+redirectOrgLocationId).append(
					"' style='text-decoration:none; '><div style=' width:220px; border-radius:30px; background-color:#214b90; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Rejected Applicants</div></a>")
					.toString());
		 //setting the Recipient information
		 ArrayList<EmailRecipient> emailRecipientList = new ArrayList<EmailRecipient>();
		 EmailRecipient emailRecipient = new EmailRecipient();
		 emailRecipient.setEmail(emailMessage.getToEmail());
		 StringBuilder recipBuild = new StringBuilder();
		 emailRecipient.setName(recipBuild.append(emailMessage.getFirstName()).append(" ").append(emailMessage.getLastName()).toString());
		 emailRecipientList.add(emailRecipient);
	
		 
		 //getting the current EmailScenario by EmailScenario unique name and entity type
		 EmailScenario notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.REJECTMAILFROMCLIENT_TO_VENDOR,Constants.ENTITY_TYPE_NOTIFICATION);
		 
		 //calling the sendEmail method of emailScenerioUtil
		 commonUtil.sendTemplateEmail(emailMessage, globalVarsMap,orgLocations.getOrg(),orgLocations,Constants.REJECTMAILFROMCLIENT_TO_VENDOR,new ArrayList<EmailAttachment>());
		 
		String notificationBody = emailScenerioUtil.buildTemplateBody(notificationScenario.getBody(),globalVarsMap,notificationScenario);
		notificationScenario.setBody(notificationBody);
		StringBuilder notificationLink = new StringBuilder();
		 String actionLink="";
	     actionLink = notificationLink.append(atsUrl).append("/").append("requirementSummary?requirementUUID="+submission.getSourceSubmission().getRequirement().getRequirementUUID()+"&submissionId="+submission.getSourceSubmission().getId()+"&candidateUUID="+submission.getCandidate().getCandidateUUID()+"&submissionStatus=REJECTED").toString();

		if(null != submission.getVendorLocationId()){
		emailScenerioUtil.sendNotification(actionLink, userClient, userVendor, submission.getVendorLocationId(), notificationScenario,new HashMap<String,String>());
		}
	   }
	}
	@Override
	public void shortListedMailToVendorFromClient(Requirement req, String submissionsList,OrgLocations orgLocations, User user) throws Exception {
		String[] submissionId = submissionsList.split(",");
		StringBuilder shortlistTable = null;
		File file;
		EmailMessage emailMessage = new EmailMessage();
		List<Long> vendorIds = new ArrayList<>();
			for (int i = 0; i < submissionId.length; i++) {
				StringBuilder linkBuild = new StringBuilder();
				RequirementSubmission reqsub = requirementSubmissionDao.getRequirementSubmissionById(Long.parseLong(submissionId[i]));
				List<EmailAttachment> attachmentList = new ArrayList<EmailAttachment>();
				if(null != reqsub.getVendorLocationId()){
					if(vendorIds.size()>=0 && !vendorIds.contains(reqsub.getVendorLocationId().getId())){
						User userVendor = userService.getUserByUserCode(reqsub.getCreatedBy());
						User userClient  = userService.getUserByUserCode(reqsub.getUpdatedBy());
						int candidateCount = getShortlistedCount(submissionId,reqsub.getVendorLocationId().getId());
							if(candidateCount <= 5){
							shortlistTable = buildShortlistedApplicantTable(submissionId,reqsub.getVendorLocationId().getId());
							}else{
								file = getShortListCandidateExcelEmailAttachmentForVendors(submissionId,reqsub.getVendorLocationId().getId(),candidateCount,reqsub);
								 //FileInputStream input = new FileInputStream(file);
								   // MultipartFile multipartFile = new MockMultipartFile("file",file.getName(), "text/plain", IOUtils.toByteArray(input));
								  byte[] data = FileUtils.readFileToByteArray(file);
								  EmailAttachment emailAttachment = new EmailAttachment();
									emailAttachment.setName(file.getName());
									emailAttachment.setDocFile(data);
									emailAttachment.setType("text/plain");
									attachmentList.add(emailAttachment);
								}
						/*if(null != reqsub.getSourceSubmission()){
							if (reqsub.getSourceSubmission().getRequirement().getId().longValue() == childReq.getId().longValue()) {*/
								
								 emailMessage.setFirstName(userVendor.getFirstName());
								 emailMessage.setLastName(userVendor.getLastName());
								 emailMessage.setToEmail(userVendor.getEmail());
								 emailMessage.setFromEmail(userClient.getEmail());
								 emailMessage.setCreatedBy(user.getUserCode());
								 StringBuilder msgBuild = new StringBuilder();
								 emailMessage.setFromName(msgBuild.append(userClient.getFirstName()).append(" ").append(userClient.getLastName()).toString());
								 emailMessage.setEmailType(Constants.CUSTOM_USER_EMAIL);

								//setting the class filed values
								 Map<String, String> globalVarsMap = new HashMap<String, String>();
								 if(null!= userVendor.getFirstName()  && !"".equalsIgnoreCase(userVendor.getFirstName())){
								 globalVarsMap.put("vendorContactName", userVendor.getFirstName()); 
								 }else{
								 globalVarsMap.put("vendorContactName", "--");	 
								 }
								 
								 if(userClient.getFirstName()!= null  && !"".equalsIgnoreCase(userClient.getFirstName())){
								 globalVarsMap.put("clientContactName", userClient.getFirstName());	 
								 }else{
								 globalVarsMap.put("clientContactName","--"); 
								 }
								 if(candidateCount != 0){
								 globalVarsMap.put("countOfApplicants",Long.toString(candidateCount));	 
								 }else{
								 globalVarsMap.put("countOfApplicants","--"); 
								 }
								 if(null != reqsub.getRequirement().getJobId()  && !"".equalsIgnoreCase(reqsub.getRequirement().getJobId())){
									 
									 StringBuilder reqsb=new StringBuilder();
								 globalVarsMap.put("requirementTitle",reqsb.append(reqsub.getRequirement().getJobId()).append("-").append(reqsub.getRequirement().getJobTitle()).toString()); 
								 }else{
								 globalVarsMap.put("requirementTitle",reqsub.getRequirement().getJobTitle()); 
								 }
								 if(Constants.JOB_OTHER.equalsIgnoreCase(reqsub.getRequirement().getJobType())) {
									 globalVarsMap.put("EmploymentType", reqsub.getRequirement().getOtherJobType());
								 }
								 else if(reqsub.getRequirement().getJobType() != null && !"".equalsIgnoreCase(reqsub.getRequirement().getJobType())){
									 globalVarsMap.put("EmploymentType", reqsub.getRequirement().getJobType());
								 }else{
									 globalVarsMap.put("EmploymentType","--");
								 }
								 
								 StringBuilder location = new StringBuilder();
								 /*if(reqsub.getRequirement().getState() != null && !"".equalsIgnoreCase(reqsub.getRequirement().getState())){
									 globalVarsMap.put("RequirementLocation", location.append(reqsub.getRequirement().getCountry()).append(",").append(reqsub.getRequirement().getState()).toString());
								 }*/
								 
								 List<String> requirementLocationsList = new ArrayList<>();
									List<RequirementLocations> locationsList;
									String Reqlocation = "";
									StringBuilder requirementBuilder=new StringBuilder();
									try {
										locationsList = requirementLocationsService.getRequirementLocationsListByRequirementId(reqsub.getRequirement().getId());
										if(locationsList != null && !locationsList.isEmpty()) {
											for(RequirementLocations locationsJob : locationsList) {
												requirementLocationsList.add(locationsJob.getLocation());
												if(locationsJob.getLocation() !=null && locationsJob.getLocation() !=null){
													Reqlocation = requirementBuilder.append(locationsJob.getLocation()).toString();
												}else{
													Reqlocation = "--";
												}
											}
										}
									} catch (Exception e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
								 
								 
									 globalVarsMap.put("RequirementLocation", Reqlocation);
								 
								 if(null != reqsub.getOrgLocations().getOrgLocationName() && !"".equalsIgnoreCase(reqsub.getOrgLocations().getOrgLocationName())){
								 globalVarsMap.put("clientOrgName", reqsub.getOrgLocations().getOrgLocationName());
								 }else{
								 globalVarsMap.put("clientOrgName","--"); 
								 }
								 if(null != shortlistTable  && !"".equalsIgnoreCase(shortlistTable.toString())){
								 globalVarsMap.put("shortlistedTable", shortlistTable.toString());	 
								 }
								 
								 globalVarsMap.put("loginLink",linkBuild.append("<a href='").append(sourceleadLoginUrl).append("' style='text-decoration:none; '><div style=' width:220px; border-radius:30px; background-color:#214b90; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Login</div></a>").toString());
								 
								 //setting the Recipient information
								 ArrayList<EmailRecipient> emailRecipientList = new ArrayList<EmailRecipient>();
								 EmailRecipient emailRecipient = new EmailRecipient();
								 emailRecipient.setEmail(emailMessage.getToEmail());
								 StringBuilder recipBuild = new StringBuilder();
								 emailRecipient.setName(recipBuild.append(emailMessage.getFirstName()).append(" ").append(emailMessage.getLastName()).toString());
								 emailRecipientList.add(emailRecipient);
							
								 
								 //getting the current EmailScenario by EmailScenario unique name and entity type
								 EmailScenario notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.SHORTLISTMAILFROMCLIENT_TO_VENDOR,Constants.ENTITY_TYPE_NOTIFICATION);
								 
								 
								 
								 //calling the sendEmail method of emailScenerioUtil
								 commonUtil.sendTemplateEmail(emailMessage, globalVarsMap,orgLocations.getOrg(),orgLocations,Constants.SHORTLISTMAILFROMCLIENT_TO_VENDOR,new ArrayList<EmailAttachment>());
								 
								String notificationBody = emailScenerioUtil.buildTemplateBody(notificationScenario.getBody(),globalVarsMap,notificationScenario);
								notificationScenario.setBody(notificationBody);
								StringBuilder notificationLink = new StringBuilder();
								 String actionLink="";
							     actionLink = notificationLink.append(atsUrl).append("/").append("requirementSummary?requirementUUID="+reqsub.getSourceSubmission().getRequirement().getRequirementUUID()+"&submissionStatus=all").toString();

								if(null != reqsub.getVendorLocationId()){
								emailScenerioUtil.sendNotification(actionLink, userClient, userVendor, reqsub.getVendorLocationId(), notificationScenario,new HashMap<String,String>());
								}
						 }	
					 vendorIds.add(reqsub.getVendorLocationId().getId());
					 
				}
				
				
			  }
	}

	
	private File getShortListCandidateExcelEmailAttachmentForVendors(String[] submissionId, Long vendorLocationId, int candidateCount,RequirementSubmission reqsub) throws Exception {
		File file = null;
		Workbook createExcelworkbook = new XSSFWorkbook();
		StringBuilder tempFileBuilder = new StringBuilder();
		StringBuilder excelStringBuilder=new StringBuilder();
		OrgLocations orgLocations = orgLocationService.getOrgLocations(reqsub.getOrgLocations().getId());
		//SimpleDateFormat simpleDateFormat = orgLocations.getLocFullFormDateFormatter();
		Sheet createExcelsheet = createExcelworkbook.createSheet(excelStringBuilder.append("shortListed Candidates List").toString());
		Date date = new Date();
		String formatedDate = commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),date);
		Row writeRow = null;
		createExcelsheet = getExcelSheetWithHeaderForShortListCandidates(createExcelsheet, writeRow, createExcelworkbook,candidateCount,reqsub);
		int column = 2;
		for(int i=0;i<submissionId.length;i++){
			List<RequirementSubmissionTemplateFields> requirementSubmissionTemplate = requirementSubmissionTemplateFieldsService.getSubmissionsTemplateFieldsByRequirementSubmissionsIdAndOrgLocationId(Long.parseLong(submissionId[i]), vendorLocationId);
			for(RequirementSubmissionTemplateFields requirementSubmissionTemplateFields:requirementSubmissionTemplate){
				writeRow = createExcelsheet.createRow(column + 2);
				createExcelsheet = createXlsSheetSubmitClient(createExcelsheet, writeRow, requirementSubmissionTemplateFields);
				column++;
			}
		}
			 file = File.createTempFile(tempFileBuilder.append("ShortList Candidate List").append(formatedDate).toString(),".xls");
				file.setReadable(true);

				FileOutputStream fos = new FileOutputStream(file);
				try {
					createExcelworkbook.write(fos);
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			return file;
	}

	private Sheet createXlsSheetSubmitClient(Sheet createExcelsheet, Row writeRow,
			RequirementSubmissionTemplateFields requirementSubmissionTemplateFields) {
		StringBuilder sb = new StringBuilder();
		if(requirementSubmissionTemplateFields.getLastName() != null && !"".equalsIgnoreCase(requirementSubmissionTemplateFields.getLastName())){
			writeRow.createCell(0).setCellValue(sb.append(requirementSubmissionTemplateFields.getFirstName()).append("-").append(requirementSubmissionTemplateFields.getLastName()).toString());
		}else if(requirementSubmissionTemplateFields.getFirstName() != null && !"".equalsIgnoreCase(requirementSubmissionTemplateFields.getFirstName())){
			writeRow.createCell(0).setCellValue(sb.append(requirementSubmissionTemplateFields.getFirstName()).toString());
		}

		if(requirementSubmissionTemplateFields.getPhoneNumber() != null && !"".equalsIgnoreCase(requirementSubmissionTemplateFields.getPhoneNumber())){
			writeRow.createCell(1).setCellValue(requirementSubmissionTemplateFields.getPhoneNumber());
		}
		if(requirementSubmissionTemplateFields.getWorkExperience() != null && !"".equalsIgnoreCase(requirementSubmissionTemplateFields.getWorkExperience())){
			writeRow.createCell(2).setCellValue(requirementSubmissionTemplateFields.getWorkExperience());
		}
			writeRow.createCell(3).setCellValue("Shortlisted");
		
		return createExcelsheet;
	}

	private Sheet getExcelSheetWithHeaderForShortListCandidates(Sheet createExcelsheet, Row writeRow,
			Workbook createExcelworkbook, int candidateCount, RequirementSubmission reqsub) {
		CellStyle style = commonUtil.getCellStyle(createExcelworkbook);
		prepareHeaders(writeRow, createExcelsheet, style,Constants.SHORTLIST_CANDIDATE_COUNT, candidateCount,reqsub);
		String headers[] = Constants.SHORTLIST_CANDIDATE_FROM_CLIENT_TO_VENDOR_HEADER;
		createExcelsheet = commonUtil.setExcelHeaders(createExcelsheet, style, headers, 3);
		return createExcelsheet;
	}

	private void prepareHeaders(Row writeRow, Sheet createExcelsheet, CellStyle style, String shortlist_candidate_count,
			int candidateCount, RequirementSubmission reqsub) {
		writeRow = createExcelsheet.createRow(1);
		createExcelsheet.setColumnWidth(0, 10000);
		createExcelsheet.setColumnWidth(1, 10000);

		Cell cell1 = writeRow.createCell(0);
		cell1.setCellValue("Requirement Name :");
		cell1.setCellStyle(style);
		Cell cell2 = writeRow.createCell(1);
		cell2.setCellValue(reqsub.getRequirement().getJobTitle());
		cell2.setCellStyle(style);
		
		writeRow = createExcelsheet.createRow(2);
		createExcelsheet.setColumnWidth(0, 10000);
		createExcelsheet.setColumnWidth(1, 10000);

		Cell cell3 = writeRow.createCell(0);
		cell3.setCellValue(shortlist_candidate_count);
		cell3.setCellStyle(style);
		Cell cell4 = writeRow.createCell(1);
		cell4.setCellValue(candidateCount);
		cell4.setCellStyle(style);
	}

	private int getShortlistedCount(String[] submissionId, Long vendorLocationId) {
		int count = 0;
		 for(int i=0;i<submissionId.length;i++){
	     		List<RequirementSubmissionTemplateFields> requirementSubmissionTemplate = requirementSubmissionTemplateFieldsService.getSubmissionsTemplateFieldsByRequirementSubmissionsIdAndOrgLocationId(Long.parseLong(submissionId[i]), vendorLocationId);
	     		for(RequirementSubmissionTemplateFields requirementSubmissionTemplateFields:requirementSubmissionTemplate){
	     				count++;
	     			}
	     		}
		 return count;	
		 }
	     	
		 

	public StringBuilder buildShortlistedApplicantTable(String[] submissionId, Long vendorLocationId) throws Exception {
		StringBuilder tempHTML = new StringBuilder();
		if(submissionId.length !=0){
	         tempHTML.append("<table width='100%' bgcolor='#F9F9F9' cellpadding='0' border='2' cellspacing='0' style='background-color:#F9F9F9;'>");
	         tempHTML.append("<tr>");
	         tempHTML.append("<th> Applicant Name </th>");
	         tempHTML.append("<th> Phone Number </th>");
	         tempHTML.append("<th> Experience </th>");
	         tempHTML.append("<th> Status </th>");
	         tempHTML.append("</tr>");
	         
	         for(int i=0;i<submissionId.length;i++){
	     		List<RequirementSubmissionTemplateFields> requirementSubmissionTemplate = requirementSubmissionTemplateFieldsService.getSubmissionsTemplateFieldsByRequirementSubmissionsIdAndOrgLocationId(Long.parseLong(submissionId[i]), vendorLocationId);
	     		for(RequirementSubmissionTemplateFields requirementSubmissionTemplateFields:requirementSubmissionTemplate){
			         tempHTML.append("<tr>");
			         
			         if(requirementSubmissionTemplateFields.getLastName() != null && !"".equalsIgnoreCase(requirementSubmissionTemplateFields.getLastName())){
		        		 tempHTML.append("<td>").append(requirementSubmissionTemplateFields.getFirstName()).append(" ").append(requirementSubmissionTemplateFields.getLastName()).append("</td>");
		        	 }else{
		        		 tempHTML.append("<td>").append(requirementSubmissionTemplateFields.getFirstName()).append("</td>");
		        	 }
			         
			         if(requirementSubmissionTemplateFields.getPhoneNumber() != null && !"".equalsIgnoreCase(requirementSubmissionTemplateFields.getPhoneNumber())){
		        		 tempHTML.append("<td>").append(requirementSubmissionTemplateFields.getPhoneNumber()).append("</td>");
		        	 }else{
		        		 tempHTML.append("<td>").append("--").append("</td>");
		        	 }
			         
			         if(requirementSubmissionTemplateFields.getWorkExperience() != null && !"".equalsIgnoreCase(requirementSubmissionTemplateFields.getWorkExperience())){
		        		 tempHTML.append("<td>").append(requirementSubmissionTemplateFields.getWorkExperience()).append("</td>");
		        	 }else{
		        		 tempHTML.append("<td>").append("--").append("</td>");
		        	 }
			         
		        		tempHTML.append("<td>").append("Shortlisted").append("</td>");

			         
	     		}
	         }
	         tempHTML.append("</tr>");
		     tempHTML.append("</table> ");
		}
		return tempHTML;
	}

	@Override
	public RequirementSubmission validateRequirementByRecruiterCandidateEmail(String email, Long requirementId,
			Long recruiterUserId) throws Exception {
		return requirementSubmissionDao.validateRequirementByRecruiterCandidateEmail(email, requirementId, recruiterUserId);
	}
	

	@Override
	public Integer getRecruiterSubmissionsCountByRecruiterIdAndRequirementId(Long requirementId, Long userId)
			throws Exception {
		return requirementSubmissionDao.getRecruiterSubmissionsCountByRecruiterIdAndRequirementId(requirementId, userId);
	}

	@Override
	public Integer getProfessionalJobAppliedCount(Long candidateId, Date startDate, Date endDate)
			throws Exception {
		return requirementSubmissionDao.getProfessionalJobAppliedCount(candidateId, startDate, endDate);
	}

	@Override
	public Integer getProfessionalJobSubmisssionsCountBySubmissionType(Long candidateId, String submissionStatus,
			Date startDate, Date endDate) throws Exception {
		return requirementSubmissionDao.getProfessionalJobSubmisssionsCountBySubmissionType(candidateId, submissionStatus, startDate, endDate);
	}


	@Override
	public List<RequirementSubmission> getProfessionalJobSubmisssionsCountListBySubmissionType(Long candidateId, String submissionStatus, Date startDate, Date endDate) throws Exception {
		return requirementSubmissionDao.getProfessionalJobSubmisssionsCountListBySubmissionType(candidateId, submissionStatus, startDate, endDate);
	}

	@Override
	public List<RequirementSubmission> getProfessionalJobAppliedCountList(Long candidateId, Date startDate, Date endDate) throws Exception {
		return requirementSubmissionDao.getProfessionalJobAppliedCountList(candidateId, startDate, endDate);
	}
	@Override
	public Integer getProfessionalJobAppliedCountByappliedDate(Long candidateId, Date appliedDate) throws Exception {
		return requirementSubmissionDao.getProfessionalJobAppliedCountByappliedDate(candidateId, appliedDate);
	}

	@Override
	public Integer getReferByMeJobAppliedCount(Long userId, Date startDate, Date endDate) throws Exception {
		return requirementSubmissionDao.getReferByMeJobAppliedCount(userId, startDate, endDate);
	}

	@Override
	public Integer getReferByMeProfessionalJobSubmisssionsCountBySubmissionType(Long userId, String submissionStatus,
			Date startDate, Date endDate) throws Exception {
		return requirementSubmissionDao.getReferByMeProfessionalJobSubmisssionsCountBySubmissionType(userId, submissionStatus, startDate, endDate);
	}

	@Override
	public List<RequirementSubmission> getReferByMeProfessionalJobSubmisssionsCountListBySubmissionType(Long userId, String submissionStatus, Date startDate, Date endDate) throws Exception {
		return requirementSubmissionDao.getReferByMeProfessionalJobSubmisssionsCountListBySubmissionType(userId, submissionStatus, startDate, endDate);
	}
	@Override
	public Integer getReferByMeJobAppliedCountByappliedDate(Long userId, Date appliedDate) throws Exception {
		return requirementSubmissionDao.getReferByMeJobAppliedCountByappliedDate(userId, appliedDate);
	}

	@Override
	public Integer getRecruiterSubmittedJobCountByappliedDate(Long userId, Date appliedDate) throws Exception {
		return requirementSubmissionDao.getRecruiterSubmittedJobCountByappliedDate(userId, appliedDate);
	}

	@Override
	public Integer getRequiterReferJobAppliedCount(Long userId, Date startDate, Date endDate)
			throws Exception {
		return requirementSubmissionDao.getRequiterReferJobAppliedCount(userId, startDate, endDate);
	}

	@Override
	public Integer getRecruiterSubmittedJobCountByStartDateAndEndDate(Long userId, Date startDate, Date endDate)
			throws Exception {
		return requirementSubmissionDao.getRecruiterSubmittedJobCountByStartDateAndEndDate(userId, startDate, endDate);
	}

	@Override
	public Integer getRecruiterSubmittedCandidatesCountByStage(Long userId, String submissionStatus, Date startDate,
			Date endDate) throws Exception {
		return requirementSubmissionDao.getRecruiterSubmittedCandidatesCountByStage(userId, submissionStatus, startDate, endDate);
	}

	@Override
	public Integer getReferByMeJobAppliedCandidateCount(Long userId, Date startDate, Date endDate) throws Exception {
		return requirementSubmissionDao.getReferByMeJobAppliedCandidateCount(userId, startDate, endDate);
	}

	@Override
	public Integer getRecruiterSubmittedCandidateCountByStartDateAndEndDate(Long userId, Date startDate, Date endDate)
			throws Exception {
		return requirementSubmissionDao.getRecruiterSubmittedCandidateCountByStartDateAndEndDate(userId, startDate, endDate);
	}

	@Override
	public List<RequirementSubmission> getRecruiterSubmittedCandidatesCountListByStage(Long userId, String submissionStatus, Date startDate, Date endDate) throws Exception {
		return requirementSubmissionDao.getRecruiterSubmittedCandidatesCountListByStage(userId, submissionStatus, startDate, endDate);
	}
	public RequirementSubmission getRequirementSubmissionByRequirementIdAndUserId(Long requirementId, Long userId)
			throws Exception {
		return requirementSubmissionDao.getRequirementSubmissionByRequirementIdAndUserId(requirementId, userId);
	}

	@Override
	public List<RequirementSubmission> getRecruiterSubmittedCandidatesList(Long recruiterUserId, Long requirementId)
			throws Exception {
		return requirementSubmissionDao.getRecruiterSubmittedCandidatesList(recruiterUserId, requirementId);
	}
	

	@Override
	public List<RequirementSubmission> getRecruiterSubmittedCandidatesListForReportsByLocationIdAndStatus(
			Long loginOrgLocationId, String client,String filterDate,Date startDateUTC,Date endDateUTC) throws Exception {
		return requirementSubmissionDao.getRecruiterSubmittedCandidatesListForReportsByLocationIdAndStatus(loginOrgLocationId, client,filterDate,startDateUTC,endDateUTC);
	}
	

	@Override
	public List<RequirementSubmission> getAllVendorsIdbyorgLocationId(OrgLocations orgLocation,Date startDateUTC,Date endDateUTC) {
		return requirementSubmissionDao.getAllVendorsIdbyorgLocationId(orgLocation,startDateUTC,endDateUTC);
	}
	
	
	@Override
	public RequirementSubmission validateRecruiterSubmittedCandidateEmail(String email, Long requirementId, Long userId)
			throws Exception {
		return requirementSubmissionDao.validateRecruiterSubmittedCandidateEmail(email, requirementId, userId);
	}
	
	@Override
	public Integer getConsolidatedReportsForMailingForIndividualUserForToday(User user,
			 OrgLocations orgLocations,String status,Requirement requirement) throws Exception {
		return requirementSubmissionDao.getConsolidatedReportsForMailingForIndividualUserForToday(user, orgLocations,status,requirement);
	}

	@Override
	public List<RequirementSubmission> getAllSubmissionsfromVendorsBasedOnLocationIdAndUserId(User user,
			OrgLocations orgLocations, String status, Requirement requirement) throws Exception {
		return requirementSubmissionDao.getAllSubmissionsfromVendorsBasedOnLocationIdAndUserId(user, orgLocations,status,requirement);

	}

	@Override
	public List<RequirementSubmission> getApplicantsListByOrgLocationAndUserId(OrgLocations loginOrgLocations,
			List<Long> applicantsList, Date startUTCDate, Date endUTCDate) {
		return requirementSubmissionDao.getApplicantsListByOrgLocationAndUserId(loginOrgLocations,applicantsList,startUTCDate,endUTCDate);
	}

	@Override
	public Integer getRequirementHistoryCountByCandidateIdAndOrgLocationID(Long candidateId, Long orgLocationId) throws Exception {
		return requirementSubmissionDao.getRequirementHistoryCountByCandidateIdAndOrgLocationID(candidateId, orgLocationId);
	}

	@Override
	public boolean isCandidateSubmittedForRequirementByCandidateIdAndRequirementId(Long candidateId, Long requirementId)
			throws Exception {
		return requirementSubmissionDao.isCandidateSubmittedForRequirementByCandidateIdAndRequirementId(candidateId, requirementId);
	}

	@Override
	public List<Long> getcandidateListBySubmissionId(List<Long> submissionsIds,Long orgLocationId) {
		return requirementSubmissionDao.getcandidateListBySubmissionId(submissionsIds,orgLocationId);
	}

	@Override
	public List<Long> getSubmissionIdByCandidateId(List<Long> candidateIds, Long orgLocationId) {
		return requirementSubmissionDao.getSubmissionIdByCandidateId(candidateIds,orgLocationId);
	}
	
	@Override
	public List<RequirementSubmissionsView> getOfferAcceptedCandidatesListByOrgLocationId(Long orgLocationId) {
		return requirementSubmissionDao.getOfferAcceptedCandidatesListByOrgLocationId(orgLocationId);
	}

	@Override
	public Long getCountOfAllApplicantsByCreatedUserAndOrglocationIdByStage(Long orgLocationId, User user,String stage, String timePeriod)
			throws Exception {
		return requirementSubmissionDao.getCountOfAllApplicantsByCreatedUserAndOrglocationIdByStage(orgLocationId, user,stage,timePeriod);
	}

	@Override
	public List<RequirementSubmission> getApplicantCountSubmittedByVendor(Long orgLocationId, Long orgRelationId) {
		return requirementSubmissionDao.getApplicantCountSubmittedByVendor(orgLocationId,orgRelationId);
	}
	
	@Override
	public List<RequirementSubmission> getApplicantRequirmentCountSubmittedByVendor(Long orgLocationId, Long requirementId,Long vendorLocationID) {
		return requirementSubmissionDao.getApplicantRequirmentCountSubmittedByVendor(orgLocationId,requirementId,vendorLocationID);
	}

	@Override
	public List<RequirementSubmission> getVendorSpocSubmissions(String userCode, Long orgLocationId, Date startDateUTC, Date endDateUTC) {
		return requirementSubmissionDao.getVendorSpocSubmissions(userCode,orgLocationId,startDateUTC,endDateUTC);
	}
	
	@Override
	public List<RequirementSubmission> getSubmissionsForSOWByRequirementId(Long requirementId, String sowType,OrgLocations loginOrgLocation) {
		return requirementSubmissionDao.getSubmissionsForSOWByRequirementId(requirementId, sowType,loginOrgLocation);
	}

	@Override
	public List<RequirementSubmission> getApplicantCountSubmittedByVendorBasedOnStartAndEndDate(Long orgLocationId,
			Long orgRelationId, Date startUTCDate, Date endUTCDate) {
		return requirementSubmissionDao.getApplicantCountSubmittedByVendorBasedOnStartAndEndDate(orgLocationId,orgRelationId, startUTCDate, endUTCDate);
	}

	@Override
	public RequirementSubmission getRequirementSubmissionByCandidateIdAndRequirementId(Long candidateId, Long requirementId) throws Exception
	{
		return requirementSubmissionDao.getRequirementSubmissionByCandidateIdAndRequirementId(candidateId, requirementId);
	}

	@Override
	public List<RequirementSubmission> getSubmissionsForSOWByOrgLocationId(String sowType, OrgLocations loginOrgLocation)
	{
		return requirementSubmissionDao.getSubmissionsForSOWByOrgLocationId(sowType,loginOrgLocation);
	}

	@Override
	public List<RequirementSubmissionHistory> getRequirementSubmissionsListByOrgLocationsIdAndSubmissionStatus(Long orgLocationsId, String submissionStatus, String dateRange, String userType, User user, Date startUTCDate, Date endUTCDate)
	{
		return requirementSubmissionDao.getRequirementSubmissionsListByOrgLocationsIdAndSubmissionStatus(orgLocationsId,submissionStatus,dateRange,userType,user,startUTCDate,endUTCDate);
	}

	@Override
	public ResponseList<RequirementSubmission> loadSubmissionsListByRequirementId(RequirementSubmissionsDTO requirementSubmissionsDTO, OrgLocations orgLocation) throws Exception
	{
		CandidateVerificationConfig candidateVerificationConfig = candidateService.getCandidateVerificationConfig(orgLocation.getId());
		return requirementSubmissionDao.loadSubmissionsListByRequirementId(requirementSubmissionsDTO, orgLocation,candidateVerificationConfig);
	}
	@Override
	public ResponseList<RequirementSubmission> loadWorkflowSubmissionsListByRequirementId(RequirementSubmissionsDTO requirementSubmissionsDTO, OrgLocations orgLocation) throws Exception
	{
		CandidateVerificationConfig candidateVerificationConfig = candidateService.getCandidateVerificationConfig(orgLocation.getId());
		return requirementSubmissionDao.loadWorkflowSubmissionsListByRequirementId(requirementSubmissionsDTO, orgLocation,candidateVerificationConfig);
	}
	@Override
	public RequirementSubmission editRequirementSubmission(JSONObject jsonObj, User user, OrgLocations orgLocations,
			String jsonStringvalues) throws Exception {
	//	SimpleDateFormat simpleDateFormat = orgLocations.getLocFullFormDateFormatter();
		RequirementSubmission requirementSubmission = null;
		 if(jsonObj.has("submissionId") && !isNullOrEmpty(jsonObj.getString("submissionId"))){
			 requirementSubmission = requirementSubmissionDao.getRequirementSubmissionById(Long.valueOf(jsonObj.getString("submissionId")));
			 CandidateResumes candidateResumes = new CandidateResumes();
			 if(requirementSubmission != null){
				
				 if((jsonObj.has("fileInfo[0].fileName") && !isNullOrEmpty(jsonObj.getString("fileInfo[0].fileName"))) && (jsonObj.has("fileInfo[0].fileURL") && !isNullOrEmpty(jsonObj.getString("fileInfo[0].fileURL"))) && (jsonObj.has("fileInfo[0].contentType") && !isNullOrEmpty(jsonObj.getString("fileInfo[0].contentType")))){
					 FileInfo fileInfo = new FileInfo();
					 fileInfo.setFileName(jsonObj.get("fileInfo[0].fileName").toString());
					 fileInfo.setFileURL(jsonObj.get("fileInfo[0].fileURL").toString());
					 fileInfo.setContentType(jsonObj.get("fileInfo[0].contentType").toString());
					 candidateResumes = new CandidateResumes();
	 				 candidateResumes.setCandidateId(requirementSubmission.getCandidate());
					 candidateResumes.setFileName(jsonObj.get("fileInfo[0].fileName").toString());
					 candidateResumes.setTitle(jsonObj.get("fileInfo[0].fileName").toString());
					 candidateResumes.setFileContentType(jsonObj.get("fileInfo[0].contentType").toString());
					 candidateResumes.setFileURL(jsonObj.get("fileInfo[0].fileURL").toString());
	 				 candidateResumes.setStatus(Constants.CANDIDATE_ACTIVE);
	 				 candidateResumes.setResumeUUID(SourceLeadRandomCodeHelper.generateRandomUUID());
	 				 candidateResumes.setCreatedDate(new Date());
	 				 if(user !=null){
					 candidateResumes.setCreatedBy(user.getUserCode());
	 				 }
	 				 else{
	 					 candidateResumes.setCreatedBy(null);
	 
	 				 }
					 candidateResumes.setUpdatedDate(new Date());
					 candidateResumes.setOrgLocationId(orgLocations);
					 FileUploadUtil.moveFile(staticPath, fileInfo, "addApplicants");
					 candidateResumesDao.storeCandidateResumes(candidateResumes);
					 candidateActivityCommonUtil.storeCandidateResumesHistory(candidateResumes, null);
					 requirementSubmission.setCandidateResumes(candidateResumes);
				 }else {
					 if(jsonObj.has("candidateResume") && !isNullOrEmpty(jsonObj.getString("candidateResume"))){
						 candidateResumes.setId(Long.parseLong(jsonObj.getString("candidateResume")));
						 requirementSubmission.setCandidateResumes(candidateResumes);
					 }
				 }
			 
				 
				 if(jsonObj.has("notifyToApplicant") && !isNullOrEmpty(jsonObj.getString("notifyToApplicant"))){
					 if(jsonObj.getString("notifyToApplicant").equals("Y")){
						 notifyToapplicantMailAboutRequirement(requirementSubmission.getId(),requirementSubmission.getRequirement(),candidateResumes.getCandidateId(),user,orgLocations,"EditSubmission");
					 }
				 }
				 
				 requirementSubmission.setSubmissionTemplateJsonStringValue(jsonStringvalues);
				 requirementSubmissionDao.storeRequirementSubmission(requirementSubmission);
				 
				 RequirementSubmissionTemplateFields templateFields = requirementSubmissionTemplateFieldsDao.getSubmissionsTemplateFieldsByRequirementSubmissionsId(requirementSubmission.getId());
				 templateFields.setRequirementSubmission(requirementSubmission);
					
				 if(jsonObj.has("FirstName") && !isNullOrEmpty(jsonObj.getString("FirstName"))){
					 templateFields.setFirstName(jsonObj.getString("FirstName"));
				 }
				 
				 if(jsonObj.has("LastName") && !isNullOrEmpty(jsonObj.getString("LastName"))){
					 templateFields.setLastName(jsonObj.getString("LastName"));
				 }else {
					 templateFields.setLastName(null);
				 }
				 
				 if(jsonObj.has("PhoneNumber") && !isNullOrEmpty(jsonObj.getString("PhoneNumber"))){
					 templateFields.setPhoneNumber(jsonObj.getString("PhoneNumber"));
				 }else {
					 templateFields.setPhoneNumber(null);
				 }
				 
				 if(jsonObj.has("AlternateNumber") && !isNullOrEmpty(jsonObj.getString("AlternateNumber"))){
					 templateFields.setAlternateNumber(jsonObj.getString("AlternateNumber"));
				 }else {
					 templateFields.setAlternateNumber(null);
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
				 
				 if(jsonObj.has("location") && !isNullOrEmpty(jsonObj.getString("location"))){
					 templateFields.setLocation(jsonObj.getString("location"));
				 }
				 
				 if(jsonObj.has("zipcode") && !isNullOrEmpty(jsonObj.getString("zipcode"))){
					 templateFields.setZipCode(jsonObj.getString("zipcode"));
				 }
				 
				 if(jsonObj.has("DOB") && !isNullOrEmpty(jsonObj.getString("DOB"))){
						try {
							   templateFields.setDateOfBirth(commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),jsonObj.getString("DOB")));
							} catch (Exception e) {
							  e.printStackTrace();
							}
					}
				 
				 if(jsonObj.has("Current/LastCompany") && !isNullOrEmpty(jsonObj.getString("Current/LastCompany"))){
					 templateFields.setCurrentORLastCompany(jsonObj.getString("Current/LastCompany"));
				 }
				 
				 if(jsonObj.has("Designation") && !isNullOrEmpty(jsonObj.getString("Designation"))){
					 templateFields.setDesignation(jsonObj.getString("Designation"));
				 }else {
					 templateFields.setDesignation(null);
				 }
				 
				 if(jsonObj.has("WorkExperience") && !isNullOrEmpty(jsonObj.getString("WorkExperience"))){
					 StringBuilder sb = new StringBuilder();
					 if(jsonObj.has("monthExp") && !isNullOrEmpty(jsonObj.getString("monthExp"))){
						 templateFields.setWorkExperience(sb.append(jsonObj.getString("WorkExperience")).append(".").append(jsonObj.getString("monthExp")).toString());
					 }else{
						 templateFields.setWorkExperience(sb.append(jsonObj.getString("WorkExperience")).toString());
					 }
				 }
				 
				 if(jsonObj.has("Education") && !isNullOrEmpty(jsonObj.getString("Education"))){
					 templateFields.setEducation(jsonObj.getString("Education"));
				 }
				 
				 if(jsonObj.has("CurrentCTC") && !isNullOrEmpty(jsonObj.getString("CurrentCTC"))){
					 templateFields.setCurrentCTC(jsonObj.getString("CurrentCTC"));
				 }
				 
				 if(jsonObj.has("currentCTCRateType") && !isNullOrEmpty(jsonObj.getString("currentCTCRateType"))){
					 templateFields.setCurrentRateType(jsonObj.getString("currentCTCRateType"));
				 }
				 
				 if(jsonObj.has("ExpectedCTC") && !isNullOrEmpty(jsonObj.getString("ExpectedCTC"))){
					 templateFields.setExpectedCTC(jsonObj.getString("ExpectedCTC"));
				 }
				 
				 if(jsonObj.has("expectedCTCRateType") && !isNullOrEmpty(jsonObj.getString("expectedCTCRateType"))){
					 templateFields.setExpectedRateType(jsonObj.getString("expectedCTCRateType"));
				 }
				 
					
				 if(jsonObj.has("currentCTCRatePer") && !isNullOrEmpty(jsonObj.getString("currentCTCRatePer"))){
					templateFields.setCurrentRatePer(jsonObj.getString("currentCTCRatePer"));
				 }
					
				 if(jsonObj.has("expectedCTCRatePer") && !isNullOrEmpty(jsonObj.getString("expectedCTCRatePer"))){
					 templateFields.setExpectedRatePer(jsonObj.getString("expectedCTCRatePer"));
				 }
				 
				 if(jsonObj.has("NoticePeriod") && !isNullOrEmpty(jsonObj.getString("NoticePeriod"))){
					 templateFields.setNoticePeriod(jsonObj.getString("NoticePeriod"));
				 }
				 
				 templateFields.setStatus(Constants.CANDIDATE_ACTIVE);
				 templateFields.setCreatedBy(user.getUserCode());
				 templateFields.setCreatedDate(new Date());
				 templateFields.setUpdatedDate(commonUtil.currentDate());
				 requirementSubmissionTemplateFieldsDao.storeRequirementSubmissionTemplateFields(templateFields);
			 }
		 }
		
		
		return requirementSubmission;
	}

	@Override
	public List<RequirementSubmission> getAllSubmittedAndShortListAndInterviewededCandidatesForClientOffer(
			OrgLocations orgLocations, Long requirementId, List<Long> existingApplicantsIds, String isClientOffer)
			throws Exception {
		return requirementSubmissionDao.getAllSubmittedAndShortListAndInterviewededCandidatesForClientOffer(orgLocations, requirementId, existingApplicantsIds, isClientOffer);
	}

	@Override
	public RequirementSubmission getClientSubmissionsBySourceSubmissionIdAndVendorLocationId(Long sourceSubmissionId,
			Long vendorLocationId) throws Exception {
		return requirementSubmissionDao.getClientSubmissionsBySourceSubmissionIdAndVendorLocationId(sourceSubmissionId, vendorLocationId);
	}

	@Override
	public List<RequirementSubmission> getSubmmisionsForIsClientJob(List<Long> submissionIds) throws Exception {
		return requirementSubmissionDao.getSubmmisionsForIsClientJob(submissionIds);
	}

	@Override
	public RequirementSubmission getRequirementVendorOrCrowdsourcingSubmissionByRequirementIdAndCandidateId(
			Long requirementId, Long candidateId) throws Exception {
		return requirementSubmissionDao.getRequirementVendorOrCrowdsourcingSubmissionByRequirementIdAndCandidateId(requirementId, candidateId);
	}

	@Override
	public boolean getRequirementSubmissionByCandidateIdAndJobId(Long candidateId, Long requirementId)
			throws Exception {
		return requirementSubmissionDao.getRequirementSubmissionByCandidateIdAndJobId(candidateId, requirementId);
	}

	@Override
	public Integer[] getRequirementSubmissionsListsByOrgLocationsIdAndSubmissionStatus(Long orgLocationsId,String dateRange, String userType, User user, Date startUTCDate, Date endUTCDate) throws Exception {
		return requirementSubmissionDao.getRequirementSubmissionsListsByOrgLocationsIdAndSubmissionStatus(orgLocationsId, dateRange, userType, user, startUTCDate, endUTCDate);
	}

	@Override
	public File getPdfFile(ResponseList<RequirementSubmissionHistory> dashboardJobsCountlist,
			OrgLocations loginOrgLocations) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Sheet getExcelFile(Sheet createExcelsheet, Workbook createExcelworkbook, ResponseList<RequirementSubmissionHistory> dashboardJobsCountlist, OrgLocations orgLocations,String submissionStatus) throws Exception {
		Row writeRow = null;
        createExcelsheet = getExcelSheetWithHeader(createExcelsheet,writeRow,createExcelworkbook,dashboardJobsCountlist,orgLocations,submissionStatus);
        List<RequirementSubmissionHistory> list =  dashboardJobsCountlist.getData();
        int column=0;
        for(RequirementSubmissionHistory rsh : list){
       	 writeRow = createExcelsheet.createRow(column+1);
       	 createExcelsheet = createXlsSheet(createExcelsheet,writeRow,column,rsh,orgLocations,submissionStatus);
       	 column++;
        }
		return createExcelsheet;
	}

	private Sheet getExcelSheetWithHeader(Sheet createExcelsheet, Row writeRow, Workbook createExcelworkbook, ResponseList<RequirementSubmissionHistory> dashboardJobsCountlist, OrgLocations orgLocations,String submissionStatus) {
		 CellStyle style = createExcelworkbook.createCellStyle();
	     style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
	     style.setFillPattern(CellStyle.SOLID_FOREGROUND);
	     Font font = createExcelworkbook.createFont();
         font.setColor(IndexedColors.BLACK.getIndex());
         style.setFont(font);
         String[] headers = null;
         if(submissionStatus.equalsIgnoreCase("SUBMISSION")) {
        	  headers = Constants.REQIREMENT_APPLICANT_HEADERS;
         }else if(submissionStatus.equalsIgnoreCase("SUBMIT_TO_CLIENT")) {
        	 headers = Constants.REQIREMENT_SUBMISSION_HEADERS;
         }else if(submissionStatus.equalsIgnoreCase("SHORTLIST")) {
        	 headers = Constants.REQIREMENT_SHORTLIST_HEADERS;
         }else if(submissionStatus.equalsIgnoreCase("INTERVIEW")) {
        	 headers = Constants.REQIREMENT_INTERVIEW_HEADERS;
         }else if(submissionStatus.equalsIgnoreCase("OFFERMADE")) {
        	 headers = Constants.REQIREMENT_OFFERMADE_HEADERS;
         }else if(submissionStatus.equalsIgnoreCase("REJECTED")) {
        	 headers = Constants.REQIREMENT_REJECTED_HEADERS;
         }else if(submissionStatus.equalsIgnoreCase("JOINED")) {
        	 headers = Constants.REQIREMENT_JOINED_HEADERS;
         }else if(submissionStatus.equalsIgnoreCase("BACKOUT")) {
        	 headers = Constants.REQIREMENT_BACKOUT_HEADERS;
         }
		return createExcelsheet = commonUtil.setExcelHeaders(createExcelsheet,style,headers,0);
	}
	
	private Sheet createXlsSheet(Sheet createExcelsheet, Row writeRow, int column, RequirementSubmissionHistory rsh, OrgLocations orgLocations,String submissionStatus) throws Exception {
		StringBuilder candidateName = new StringBuilder();
		
		//Candidate Name
		if(rsh.getSubmissionId().getCandidate().getLastName() != null) {
			writeRow.createCell(0).setCellValue(candidateName.append(rsh.getSubmissionId().getCandidate().getFirstName()).append(" ").append(rsh.getSubmissionId().getCandidate().getLastName()).toString()); 
		}else {
			writeRow.createCell(0).setCellValue(candidateName.append(rsh.getSubmissionId().getCandidate().getFirstName()).toString()); 
		}
		
		//Candidate Email
		writeRow.createCell(1).setCellValue(rsh.getSubmissionId().getCandidate().getEmail());
		
		//Requirement Name
		if(rsh.getSubmissionId().getRequirement() != null) {
			if(rsh.getSubmissionId().getRequirement().getGeneratedId() != null) {
				writeRow.createCell(2).setCellValue(rsh.getSubmissionId().getRequirement().getGeneratedId() +" - "+rsh.getSubmissionId().getRequirement().getJobTitle());
			}else {
				writeRow.createCell(2).setCellValue(rsh.getSubmissionId().getRequirement().getJobTitle());
			}
		}
		
				
		//Clinet Name
		if(rsh.getSubmissionId().getRequirement().getClientId() != null) {
			if (rsh.getSubmissionId().getRequirement().getClientId().getResponderType().equalsIgnoreCase("Client")) {
				if (rsh.getSubmissionId().getRequirement().getClientId().getResponderLocationId() != null) {
					writeRow.createCell(3).setCellValue(rsh.getSubmissionId().getRequirement().getClientId().getResponderLocationId().getOrgLocationName());
				}else {
					writeRow.createCell(3).setCellValue("--");
				}
			}
			if (rsh.getSubmissionId().getRequirement().getClientId().getResponderType().equalsIgnoreCase("Vendor")) {
				if (rsh.getSubmissionId().getRequirement().getClientId().getLocationId() != null) {
					writeRow.createCell(3).setCellValue(rsh.getSubmissionId().getRequirement().getClientId().getLocationId().getOrgLocationName());
				}else {
					writeRow.createCell(3).setCellValue("--");
				}
			}
		}else {
			writeRow.createCell(3).setCellValue("--");
		}
		 
		 if(submissionStatus.equalsIgnoreCase("SUBMISSION")) {
			//Candidate Phone Number
				if(rsh.getSubmissionId().getCandidate().getMobilePhone() != null){
					writeRow.createCell(4).setCellValue(rsh.getSubmissionId().getCandidate().getMobilePhone());
				}else {
					writeRow.createCell(4).setCellValue("--");
				}
				
			//Job Location
				List<String> requirementLocationsList = new ArrayList<>();
				List<RequirementLocations> locationsList = requirementLocationsService.getRequirementLocationsListByRequirementId(rsh.getSubmissionId().getRequirement().getId());
				if(locationsList != null && !locationsList.isEmpty()) {
					for(RequirementLocations location : locationsList) {
						requirementLocationsList.add(location.getLocation());
					}
				}
				writeRow.createCell(5).setCellValue(requirementLocationsList.toString()); 
				
				//Sourece Name
				writeRow.createCell(6).setCellValue(rsh.getSubmissionId().getCreatedUser());
				
				//Aplicant Status
				writeRow.createCell(7).setCellValue(rsh.getSubmissionId().getSubmissionStatus().toString());
				
				//Job Rating
				 if(rsh.getSubmissionId().getRequirement().getJobRate() != null) {
					 writeRow.createCell(8).setCellValue(rsh.getSubmissionId().getRequirement().getJobRate());
				 }else {
					 writeRow.createCell(8).setCellValue("--");

				 }

				//Incentivize
				 if(rsh.getSubmissionId().getRequirement().getIncentivized() != null) {
					 writeRow.createCell(9).setCellValue(rsh.getSubmissionId().getRequirement().getIncentivized());
				 }else {
					 writeRow.createCell(9).setCellValue("--");
				 }
				
		 }else if(submissionStatus.equalsIgnoreCase("SUBMIT_TO_CLIENT")) {
			 //Applied On
			 writeRow.createCell(4).setCellValue(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),rsh.getCreatedDate())); 
			 
			 //Submitted On
			 writeRow.createCell(5).setCellValue(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),rsh.getCreatedDate())); 

			 //Sub rate
			 if(rsh.getSubmissionId().getFinalizedRate() != null) {
				 writeRow.createCell(6).setCellValue(rsh.getSubmissionId().getFinalizedRate());
			 }else {
				 writeRow.createCell(6).setCellValue("--");
			 }
			 
			 //Submitted by
			 writeRow.createCell(7).setCellValue(rsh.getCreatedUser());
			 
			//Sourece Name
			 writeRow.createCell(8).setCellValue(rsh.getSubmissionId().getCreatedUser());
				
			//Aplicant Status
			 writeRow.createCell(9).setCellValue(rsh.getToStage());
			
			//Job Rating
			 if(rsh.getSubmissionId().getRequirement().getJobRate() != null) {
				 writeRow.createCell(10).setCellValue(rsh.getSubmissionId().getRequirement().getJobRate());
			 }else {
				 writeRow.createCell(10).setCellValue("--");

			 }

			//Incentivize
			 if(rsh.getSubmissionId().getRequirement().getIncentivized() != null) {
				 writeRow.createCell(11).setCellValue(rsh.getSubmissionId().getRequirement().getIncentivized());
			 }else {
				 writeRow.createCell(11).setCellValue("--");
			 }
			 
		 }else if(submissionStatus.equalsIgnoreCase("INTERVIEW")){
			 //Applied On
			 writeRow.createCell(4).setCellValue(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),rsh.getCreatedDate())); 
			 
			 //No Of Interviews
			 int interviewedCount = requirementSubmissionDao.getAllSubmissionCountByRequirementIdAndSubmissionStage(orgLocations.getId(), rsh.getSubmissionId().getRequirement().getId(), "INTERVIEWED");
			 writeRow.createCell(5).setCellValue(interviewedCount);
			 
			//Sourece Name
			 writeRow.createCell(6).setCellValue(rsh.getSubmissionId().getCreatedUser());
				
			//Aplicant Status
			 writeRow.createCell(7).setCellValue(rsh.getToStage());
			
			//Job Rating
			 if(rsh.getSubmissionId().getRequirement().getJobRate() != null) {
				 writeRow.createCell(8).setCellValue(rsh.getSubmissionId().getRequirement().getJobRate());
			 }else {
				 writeRow.createCell(8).setCellValue("--");

			 }

			//Incentivize
			 if(rsh.getSubmissionId().getRequirement().getIncentivized() != null) {
				 writeRow.createCell(9).setCellValue(rsh.getSubmissionId().getRequirement().getIncentivized());
			 }else {
				 writeRow.createCell(9).setCellValue("--");
			 }
		 }else if(submissionStatus.equalsIgnoreCase("OFFERMADE")) {
				List<RequirementOffers> offersList = requirementOffersService.getRequirementOfferSubmissionByIdAndOrgLocationId(rsh.getSubmissionId().getId(), orgLocations.getId());
				for (RequirementOffers requirementOffers : offersList) {
					//Join Date
					 writeRow.createCell(4).setCellValue(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),requirementOffers.getDateOfJoining()));
					 
					 //Position Type 
					 writeRow.createCell(5).setCellValue(requirementOffers.getPositionType());
					 
					 //Offered On
					 if("OFFERMADE".equalsIgnoreCase(rsh.getToStage())){
						 writeRow.createCell(6).setCellValue(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),rsh.getCreatedDate()));
					}
				}
				//Sourece Name
				writeRow.createCell(7).setCellValue(rsh.getSubmissionId().getCreatedUser());
				
				//Aplicant Status
				 writeRow.createCell(8).setCellValue(rsh.getToStage());
				
				//Job Rating
				 if(rsh.getSubmissionId().getRequirement().getJobRate() != null) {
					 writeRow.createCell(9).setCellValue(rsh.getSubmissionId().getRequirement().getJobRate());
				 }else {
					 writeRow.createCell(9).setCellValue("--");

				 }

				//Incentivize
				 if(rsh.getSubmissionId().getRequirement().getIncentivized() != null) {
					 writeRow.createCell(10).setCellValue(rsh.getSubmissionId().getRequirement().getIncentivized());
				 }else {
					 writeRow.createCell(10).setCellValue("--");
				 }
		 }else if(submissionStatus.equalsIgnoreCase("REJECTED")) {
			 //Rejected Type
			 if(rsh.getSubmissionId().getRejectedCategoryType() != null) {
				 writeRow.createCell(4).setCellValue(rsh.getSubmissionId().getRejectedCategoryType());
			 }else {
				 writeRow.createCell(4).setCellValue("--");
			 }
			 
			 //"Rejected Reason
			 if(rsh.getSubmissionId().getRejectedReason() != null) {
				 writeRow.createCell(5).setCellValue(rsh.getSubmissionId().getRejectedReason());
			 }else {
				 writeRow.createCell(5).setCellValue("--");
			 }
			 
			//Sourece Name
			 writeRow.createCell(6).setCellValue(rsh.getSubmissionId().getCreatedUser());
				
			//Aplicant Status
			 writeRow.createCell(7).setCellValue(rsh.getToStage());
			
			//Job Rating
			 if(rsh.getSubmissionId().getRequirement().getJobRate() != null) {
				 writeRow.createCell(8).setCellValue(rsh.getSubmissionId().getRequirement().getJobRate());
			 }else {
				 writeRow.createCell(8).setCellValue("--");

			 }

			//Incentivize
			 if(rsh.getSubmissionId().getRequirement().getIncentivized() != null) {
				 writeRow.createCell(9).setCellValue(rsh.getSubmissionId().getRequirement().getIncentivized());
			 }else {
				 writeRow.createCell(9).setCellValue("--");
			 }
		 }else if(submissionStatus.equalsIgnoreCase("JOINED")) {
			 //join Date
			 writeRow.createCell(4).setCellValue(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),rsh.getSubmissionId().getJoinedDate()));
			 
			//Sourece Name
			 writeRow.createCell(5).setCellValue(rsh.getSubmissionId().getCreatedUser());
				
			//Aplicant Status
			 writeRow.createCell(6).setCellValue(rsh.getToStage());
			
			//Job Rating
			 if(rsh.getSubmissionId().getRequirement().getJobRate() != null) {
				 writeRow.createCell(7).setCellValue(rsh.getSubmissionId().getRequirement().getJobRate());
			 }else {
				 writeRow.createCell(7).setCellValue("--");

			 }

			//Incentivize
			 if(rsh.getSubmissionId().getRequirement().getIncentivized() != null) {
				 writeRow.createCell(8).setCellValue(rsh.getSubmissionId().getRequirement().getIncentivized());
			 }else {
				 writeRow.createCell(8).setCellValue("--");
			 }
			 
		 }else if(submissionStatus.equalsIgnoreCase("BACKOUT")) {
			 //Backout Type
			 if(rsh.getSubmissionId().getBackOutCategoryType() != null) {
				 writeRow.createCell(4).setCellValue(rsh.getSubmissionId().getBackOutCategoryType());
			 }else {
				 writeRow.createCell(4).setCellValue("--");
			 }
			 
			 //Backout Reason
			 if(rsh.getSubmissionId().getBackOutReason() !=null) {
				 writeRow.createCell(5).setCellValue(rsh.getSubmissionId().getBackOutReason());
			 }else {
				 writeRow.createCell(5).setCellValue("--");
			 }
			
			//Sourece Name
			 writeRow.createCell(6).setCellValue(rsh.getSubmissionId().getCreatedUser());
				
			//Aplicant Status
			 writeRow.createCell(7).setCellValue(rsh.getToStage());
			
			//Job Rating
			 if(rsh.getSubmissionId().getRequirement().getJobRate() != null) {
				 writeRow.createCell(8).setCellValue(rsh.getSubmissionId().getRequirement().getJobRate());
			 }else {
				 writeRow.createCell(8).setCellValue("--");

			 }

			//Incentivize
			 if(rsh.getSubmissionId().getRequirement().getIncentivized() != null) {
				 writeRow.createCell(9).setCellValue(rsh.getSubmissionId().getRequirement().getIncentivized());
			 }else {
				 writeRow.createCell(9).setCellValue("--");
			 }
		 }else if(submissionStatus.equalsIgnoreCase("SHORTLIST")) {
			 //Applied On
			 writeRow.createCell(4).setCellValue(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),rsh.getCreatedDate())); 
			 
			//Sourece Name
			 writeRow.createCell(5).setCellValue(rsh.getSubmissionId().getCreatedUser());
				
			//Aplicant Status
			 writeRow.createCell(6).setCellValue(rsh.getToStage());
			
			//Job Rating
			 if(rsh.getSubmissionId().getRequirement().getJobRate() != null) {
				 writeRow.createCell(7).setCellValue(rsh.getSubmissionId().getRequirement().getJobRate());
			 }else {
				 writeRow.createCell(7).setCellValue("--");

			 }

			//Incentivize
			 if(rsh.getSubmissionId().getRequirement().getIncentivized() != null) {
				 writeRow.createCell(8).setCellValue(rsh.getSubmissionId().getRequirement().getIncentivized());
			 }else {
				 writeRow.createCell(8).setCellValue("--");
			 }
		 }
		
		return createExcelsheet;
	}

	@Override
	public List<RequirementSubmission> getAllSubmissionsNotInTemplateFileds() throws Exception {
		return requirementSubmissionDao.getAllSubmissionsNotInTemplateFileds();
	}

	@Override
	public Integer getRequirementSubmissionCountByCandiateIdAndOrgLocationId(Long candidateId, Long orgLocationId)
			throws Exception {
		return requirementSubmissionDao.getRequirementSubmissionCountByCandiateIdAndOrgLocationId(candidateId, orgLocationId);
	}

	@Override
	public List<RequirementSubmissionsDTO> getCandidateSubmittedRequirementsListByCandidateId(Long candidateId,
			Long orgLocationId,String dateFormat) throws Exception {
		return requirementSubmissionDao.getCandidateSubmittedRequirementsListByCandidateId(candidateId, orgLocationId,dateFormat);
	}

	@Override
	public Sheet getApplicaantJoinedListExcelFile(Sheet createExcelsheet, Workbook createExcelworkbook, ResponseList<RequirementSubmissionHistory> dashboardJobsCountlist, OrgLocations loginOrgLocations, String submissionStatus) throws Exception
	{
		Row writeRow = null;
        createExcelsheet = getApplicantJoinedListExcelSheetWithHeader(createExcelsheet,writeRow,createExcelworkbook,dashboardJobsCountlist,loginOrgLocations,submissionStatus);
        List<RequirementSubmissionHistory> list =  dashboardJobsCountlist.getData();
        int column=0;
        for(RequirementSubmissionHistory rsh : list){
       	 writeRow = createExcelsheet.createRow(column+1);
       	 createExcelsheet = createApplicantJoinedXlsSheet(createExcelsheet,writeRow,column,rsh,loginOrgLocations,submissionStatus);
       	 column++;
        }
		return createExcelsheet;
	}
	
	
	private Sheet getApplicantJoinedListExcelSheetWithHeader(Sheet createExcelsheet, Row writeRow, Workbook createExcelworkbook, ResponseList<RequirementSubmissionHistory> dashboardJobsCountlist, OrgLocations orgLocations,String submissionStatus) {
		 CellStyle style = createExcelworkbook.createCellStyle();
	     style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
	     style.setFillPattern(CellStyle.SOLID_FOREGROUND);
	     Font font = createExcelworkbook.createFont();
        font.setColor(IndexedColors.BLACK.getIndex());
        style.setFont(font);
        String[] headers = null;
       	 headers = Constants.APPLICANTS_JOINED_HEADERS;
		return createExcelsheet = commonUtil.setExcelHeaders(createExcelsheet,style,headers,0);
	}
	
	
	
	
	private Sheet createApplicantJoinedXlsSheet(Sheet createExcelsheet, Row writeRow, int column, RequirementSubmissionHistory rsh, OrgLocations orgLocations,String submissionStatus) throws Exception {
		StringBuilder candidateName = new StringBuilder();
		
		//Candidate Name
		if(rsh.getSubmissionId().getCandidate().getLastName() != null) {
			writeRow.createCell(0).setCellValue(candidateName.append(rsh.getSubmissionId().getCandidate().getFirstName()).append(" ").append(rsh.getSubmissionId().getCandidate().getLastName()).toString()); 
		}else {
			writeRow.createCell(0).setCellValue(candidateName.append(rsh.getSubmissionId().getCandidate().getFirstName()).toString()); 
		}
		
		//Candidate Email
		writeRow.createCell(1).setCellValue(rsh.getSubmissionId().getCandidate().getEmail());
		
		
		if(rsh.getSubmissionId().getCandidate().getMobilePhone() != null) {
			writeRow.createCell(2).setCellValue(rsh.getSubmissionId().getCandidate().getMobilePhone());	
		}
		else{
			writeRow.createCell(2).setCellValue("--");
		}
		//Requirement Name
		if(rsh.getSubmissionId().getRequirement() != null) {
			if(rsh.getSubmissionId().getRequirement().getGeneratedId() != null) {
				writeRow.createCell(3).setCellValue(rsh.getSubmissionId().getRequirement().getGeneratedId() +" - "+rsh.getSubmissionId().getRequirement().getJobTitle());
			}else {
				writeRow.createCell(3).setCellValue(rsh.getSubmissionId().getRequirement().getJobTitle());
			}
		}
		 if(rsh.getSubmissionId().getRequirement().getJobRate() != null){
				writeRow.createCell(4).setCellValue(rsh.getSubmissionId().getRequirement().getJobRate());

		 }
		 else{
			 writeRow.createCell(4).setCellValue("--"); 
		 }
		 if(rsh.getSubmissionId().getRequirement().getProcess() != null){
				writeRow.createCell(5).setCellValue(rsh.getSubmissionId().getRequirement().getProcess());
 
		 }
		 else{
			 writeRow.createCell(5).setCellValue("--"); 
		 }
		//Clinet Name
		if(rsh.getSubmissionId().getRequirement().getClientId() != null) {
			if (rsh.getSubmissionId().getRequirement().getClientId().getResponderType().equalsIgnoreCase("Client")) {
				if (rsh.getSubmissionId().getRequirement().getClientId().getResponderLocationId() != null) {
					writeRow.createCell(6).setCellValue(rsh.getSubmissionId().getRequirement().getClientId().getResponderLocationId().getOrgLocationName());
				}else {
					writeRow.createCell(6).setCellValue("--");
				}
			}
			if (rsh.getSubmissionId().getRequirement().getClientId().getResponderType().equalsIgnoreCase("Vendor")) {
				if (rsh.getSubmissionId().getRequirement().getClientId().getLocationId() != null) {
					writeRow.createCell(6).setCellValue(rsh.getSubmissionId().getRequirement().getClientId().getLocationId().getOrgLocationName());
				}else {
					writeRow.createCell(6).setCellValue("--");
				}
			}
		}else {
			writeRow.createCell(6).setCellValue("--");
		}
		 
		RequirementInterview reqInterview=requirementInterviewService.getinterviewBySubmissionId(rsh.getSubmissionId().getId());
		if(reqInterview != null && reqInterview.getDateOfInterview() !=  null){
			writeRow.createCell(7).setCellValue(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),reqInterview.getDateOfInterview()));
		}
		else{
			writeRow.createCell(7).setCellValue("--");
		}
		
		RequirementOffers reqOffers=requirementOffersService.getRequirementOfferSubmissionById(rsh.getSubmissionId().getId());
			
		if(reqOffers != null && reqOffers.getAmount() !=  null && reqOffers.getPaymentType() !=null && reqOffers.getPaymentMode() != null){
			writeRow.createCell(8).setCellValue(reqOffers.getAmount()+"-"+reqOffers.getPaymentType()+"/"+reqOffers.getPaymentMode());
		}
		else{
			writeRow.createCell(8).setCellValue("--");
		}
		//Sourece Name
		 writeRow.createCell(9).setCellValue(rsh.getSubmissionId().getCreatedUser());
		 //Submitted On
		 writeRow.createCell(10).setCellValue(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),rsh.getCreatedDate())); 

			if(rsh.getSubmissionId().getJoinedDate()  != null){
				 writeRow.createCell(11).setCellValue(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),rsh.getSubmissionId().getJoinedDate()));
			}
			else{
				writeRow.createCell(11).setCellValue("--");
			}
			if(rsh.getSubmissionId().getRequirement().getPayOutPeriod() != null){
				String payTerm=rsh.getSubmissionId().getRequirement().getClientId().getPaymentTerm();
				payTerm=payTerm.replaceAll("\\D", "");
				if(!payTerm.isEmpty()){
				 writeRow.createCell(12).setCellValue(payTerm);
				}
				else{
					writeRow.createCell(12).setCellValue("--");
				}
			}
			else{
				writeRow.createCell(12).setCellValue("--");
			}
			if(rsh.getSubmissionId().getRequirement().getPayOutPeriod() != null &&  rsh.getSubmissionId().getJoinedDate()  != null){
				String payTerm=rsh.getSubmissionId().getRequirement().getClientId().getPaymentTerm();
				 Integer result = 0;
                 try {
                     payTerm=payTerm.replaceAll("\\D", "");
                     result = Integer.parseInt(payTerm);
                 } catch (Exception e) {
                     result = 0;
                 }
                 if(result != 0){
     				Date generatedInvoiceDate=CalendarUtil.addDaysForGenerateInvoice(rsh.getSubmissionId().getJoinedDate(), result);
    				DateTime dtOrg = new DateTime(generatedInvoiceDate);
    				DateTime generatedInvoiceDatePlusOne = dtOrg.plusDays(1);
    				 writeRow.createCell(13).setCellValue(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),generatedInvoiceDatePlusOne.toDate()));
	 
                 }
			}
			else{
				writeRow.createCell(13).setCellValue("--");
			}

		return createExcelsheet;
	}

	@Override
	public File getApplicantsJoinedListPdfFile(ResponseList<RequirementSubmissionHistory> dashboardJobsCountlist, OrgLocations loginOrgLocations)throws Exception
	{
		return pdfUtil.ApplicantsJoinedListPdfTable(dashboardJobsCountlist,loginOrgLocations);
	}
	
	@Override
	public Integer getRequirementCountBySubmissionStatusJoinedandAssignedUsercode(Long requirementId, String userCode,Date[] searchDates) throws Exception
	{
		return requirementSubmissionDao.getRequirementCountBySubmissionStatusJoinedandAssignedUsercode(requirementId, userCode,searchDates);

	}
	
	@Override
	public RequirementIncentive getRequirementIncentiveByRequirementId(Long requirementId) throws Exception
	{
		return requirementSubmissionDao.getRequirementIncentiveByRequirementId(requirementId);

	}

	@Override
	public List<ApplicantsJoinedReportDTO> getRequirementIncentiveByRequirementIdAndLocation(Long requirementId, String usercode, Long orgLocationId, Date[] searchDates) throws Exception
	{
		return requirementSubmissionDao.getRequirementIncentiveByRequirementIdAndLocation(requirementId,usercode,orgLocationId,searchDates);

	}


	@Override
	public ResponseList<AssociateJoineReportDTO> getAssociateJoineReport(AssociateJoineReportDTO requirementSubmissionHistoryDTO, Long orgLocationsId, Date startUTCDate, Date endUTCDate) throws Exception
	{
		return requirementSubmissionDao.getAssociateJoineReport(requirementSubmissionHistoryDTO,orgLocationsId,startUTCDate,endUTCDate);

	}

	@Override
	public ResponseList<AssociateJoineReportDTO> getAssociateTerminateReport(AssociateJoineReportDTO requirementSubmissionHistoryDTO, Long orgLocationsId, Date startUTCDate, Date endUTCDate) throws Exception
	{
		return requirementSubmissionDao.getAssociateJoineReport(requirementSubmissionHistoryDTO,orgLocationsId,startUTCDate,endUTCDate);

	}

	@Override
	public ResponseList<AssociateJoineReportDTO> getAssociateJoinedGraph(AssociateJoineReportDTO requirementSubmissionHistoryDTO, Long orgLocationsId, Date startUTCDate, Date endUTCDate) throws Exception
	{
		return requirementSubmissionDao.getAssociateJoinedGraph(requirementSubmissionHistoryDTO,orgLocationsId,startUTCDate,endUTCDate);

	}


	@SuppressWarnings("null")
	@Override
	public List<RequirementSubmissionsDTO > getCandidateForInvoiceDashBoard(OrgLocations orgLocations, User user) {

		List<RequirementSubmissionsDTO> listOfApplicants = new ArrayList<RequirementSubmissionsDTO>();
		List<RequirementSubmission> submissionList = requirementSubmissionDao.getCandidateForInvoiceDashBoard(orgLocations, user);

		submissionList.forEach(requirementSubmission -> {

			RequirementSubmissionsDTO applicantsDto = new RequirementSubmissionsDTO();
			applicantsDto.setCandidateName(requirementSubmission.getCandidate().getFullName());
			applicantsDto.setRequirementName(requirementSubmission.getRequirement().getJobTitle());
			applicantsDto.setSubmissionId(requirementSubmission.getId());
			applicantsDto.setId(requirementSubmission.getCandidate().getId());
			if(requirementSubmission.getRequirementOffersList().size()>0){
				for(RequirementOffers offer : requirementSubmission.getRequirementOffersList()){
					if(offer.getOfferStatus().equals("OFFERACCEPTED")){
						applicantsDto.setRate(offer.getAmount());	
					}
				}
				
			}
			if (requirementSubmission.getRequirement().getClientId().getResponderType().equalsIgnoreCase("Client")) {
				if (requirementSubmission.getRequirement().getClientId().getResponderLocationId() != null) {
					applicantsDto.setClientOrVendorName(requirementSubmission.getRequirement().getClientId().getResponderLocationId().getOrgLocationName());
					applicantsDto.setOrgRelationId(requirementSubmission.getRequirement().getClientId().getId());
				}
			}
			if (requirementSubmission.getRequirement().getClientId() != null && requirementSubmission.getJoinedDate() != null) {
				if(StringUtils.isNotBlank(requirementSubmission.getRequirement().getClientId().getPaymentTerm())){
					String term = requirementSubmission.getRequirement().getClientId().getPaymentTerm();
					term = term.replaceAll("\\D", "");
					int result = Integer.parseInt(term);
				Date generatedInvoiceDate = CalendarUtil.addDaysForGenerateInvoice(requirementSubmission.getJoinedDate(), result);
				DateTime dtOrg = new DateTime(generatedInvoiceDate);
				Date locationDate = commonUtil.orgLocationCommonDate(new Date(),orgLocations);
				Boolean finaldate = dtOrg.toDate().before(locationDate);
				if(finaldate){
					applicantsDto.setGenerateDate(true);	
				}
				applicantsDto.setInvoiceGenerateDate(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(), dtOrg.toDate()));
				}
				applicantsDto.setJoinedDate(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),requirementSubmission.getJoinedDate()));
			}
			listOfApplicants.add(applicantsDto);
		});
		
		return listOfApplicants;
	}

	@Override
	public List<RequirementSubmissionsDTO> getCandidateForInvoiceJobClosure(OrgLocations orgLocations, User user,
			Long orgRelationId,String contractType,String selectedAssociates) {
		
		List<RequirementSubmissionsDTO> listOfApplicants = new ArrayList<RequirementSubmissionsDTO>();
		
		List<RequirementSubmission> submissionList = null;
		List<Long> associateIds = new ArrayList<>();
		if(!isNullOrEmpty(selectedAssociates)){
			String[] selectedAssoicatesIds = selectedAssociates.split(",");
			for (int i = 0; i < selectedAssoicatesIds.length; i++) {
				associateIds.add(Long.parseLong(selectedAssoicatesIds[i]));
			}
			submissionList = requirementSubmissionDao.getCandidateForInvoiceJobClosureEdit(orgLocations, user,orgRelationId,associateIds);	
		}else{
			submissionList = requirementSubmissionDao.getCandidateForInvoiceJobClosure(orgLocations, user,orgRelationId);
		}
		
		submissionList.forEach(requirementSubmission -> {

			RequirementSubmissionsDTO applicantsDto = new RequirementSubmissionsDTO();
			applicantsDto.setCandidateName(requirementSubmission.getCandidate().getFullName());
			applicantsDto.setRequirementName(requirementSubmission.getRequirement().getJobTitle());
			applicantsDto.setSubmissionId(requirementSubmission.getId());
			applicantsDto.setEmail(requirementSubmission.getCandidate().getEmail());
			applicantsDto.setRequirementId(requirementSubmission.getRequirement().getId());
			applicantsDto.setId(requirementSubmission.getCandidate().getId());
			if(requirementSubmission.getRequirementOffersList().size()>0){
				for(RequirementOffers offer : requirementSubmission.getRequirementOffersList()){
					if(offer.getOfferStatus().equals("OFFERACCEPTED")){
						applicantsDto.setRate(offer.getAmount());	
					}
				}
				
			}
			
			if (requirementSubmission.getRequirement().getClientId().getResponderType().equalsIgnoreCase("Client")) {
				if (requirementSubmission.getRequirement().getClientId().getResponderLocationId() != null) {
					applicantsDto.setClientOrVendorName(requirementSubmission.getRequirement().getClientId().getResponderLocationId().getOrgLocationName());
				}
			}
			if (requirementSubmission.getRequirement().getClientId() != null && requirementSubmission.getJoinedDate() != null) {
				if(StringUtils.isNotBlank(requirementSubmission.getRequirement().getClientId().getPaymentTerm())){
					String term = requirementSubmission.getRequirement().getClientId().getPaymentTerm();
					term = term.replaceAll("\\D", "");
					int result = Integer.parseInt(term);
				Date generatedInvoiceDate = CalendarUtil.addDaysForGenerateInvoice(requirementSubmission.getJoinedDate(), result);
				DateTime dtOrg = new DateTime(generatedInvoiceDate);
				applicantsDto.setInvoiceGenerateDate(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(), dtOrg.toDate()));
				}
				applicantsDto.setJoinedDate(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),requirementSubmission.getJoinedDate()));
			}
			try{
				List<Contracts> contractsList = getSowContract(requirementSubmission.getCandidate().getId(), orgLocations, orgRelationId, contractType);
				if (null != contractsList && contractsList.size() > 0) {
					applicantsDto.setContractsList(contractsList);
				}	
			}catch (Exception e) {
				e.printStackTrace();
			}
				
			
			listOfApplicants.add(applicantsDto);
		});
		
		return listOfApplicants;
	}
	
	private List<Contracts> getSowContract(Long candidateId, OrgLocations orgLocation, Long orgRelationId, String contractType) throws Exception
	{
		List<Contracts> contractsList = null;
		if (null != orgRelationId) {
			if (contractType.equalsIgnoreCase("vendorSow")) {
				contractsList = invoiceDao.getSowcontractListByAssociateId(candidateId, orgLocation.getId(), orgRelationId, Constants.Vendor_SOW);
			}
			else {
				contractsList = invoiceDao.getSowcontractListByAssociateId(candidateId, orgLocation.getId(), orgRelationId, Constants.Client_SOW);
			}
		}
		return contractsList;
	}
	
	
	@Override
	public List<RequirementSubmissionsDTO> getCandidateForBillsJobClosure(OrgLocations orgLocations, User user,
			Long orgRelationId,String contractType,String selectedAssociates) {
		
		List<RequirementSubmissionsDTO> listOfApplicants = new ArrayList<RequirementSubmissionsDTO>();
		
		List<RequirementSubmission> submissionList = null;
		List<Long> associateIds = new ArrayList<>();
		if(!isNullOrEmpty(selectedAssociates)){
			String[] selectedAssoicatesIds = selectedAssociates.split(",");
			for (int i = 0; i < selectedAssoicatesIds.length; i++) {
				associateIds.add(Long.parseLong(selectedAssoicatesIds[i]));
			}
			submissionList = requirementSubmissionDao.getCandidateForBillJobClosureEdit(orgLocations, user,orgRelationId,associateIds);	
		}else{
			submissionList = requirementSubmissionDao.getCandidateForBillJobClosure(orgLocations, user,orgRelationId);
		}
		
		submissionList.forEach(requirementSubmission -> {

			RequirementSubmissionsDTO applicantsDto = new RequirementSubmissionsDTO();
			applicantsDto.setCandidateName(requirementSubmission.getCandidate().getFullName());
			applicantsDto.setRequirementName(requirementSubmission.getRequirement().getJobTitle());
			applicantsDto.setSubmissionId(requirementSubmission.getId());
			applicantsDto.setEmail(requirementSubmission.getCandidate().getEmail());
			applicantsDto.setRequirementId(requirementSubmission.getRequirement().getId());
			applicantsDto.setId(requirementSubmission.getCandidate().getId());
			if(requirementSubmission.getRequirementOffersList().size()>0){
				for(RequirementOffers offer : requirementSubmission.getRequirementOffersList()){
					if(offer.getOfferStatus().equals("OFFERACCEPTED")){
						applicantsDto.setRate(offer.getAmount());	
					}
				}
				
			}
			
			if (requirementSubmission.getCandidate().getOrgRelationId().getResponderType().equalsIgnoreCase("Vendor")) {
				if (requirementSubmission.getCandidate().getOrgRelationId().getResponderType() != null) {
					applicantsDto.setClientOrVendorName(requirementSubmission.getCandidate().getOrgRelationId().getResponderLocationId().getOrgLocationName());
				}
			}
			try {
				// get released requirement based on parent requirement id
				Requirement req = requirementDao.getRequirementByParentRequirementId(requirementSubmission.getRequirement().getId());
				if (req.getClientId() != null && requirementSubmission.getJoinedDate() != null) {
					if(StringUtils.isNotBlank(req.getClientId().getPaymentTerm())){
					String term = req.getClientId().getPaymentTerm();
					term = term.replaceAll("\\D", "");
					int result = Integer.parseInt(term);
					Date generatedInvoiceDate = CalendarUtil.addDaysForGenerateInvoice(requirementSubmission.getJoinedDate(), result);
					DateTime dtOrg = new DateTime(generatedInvoiceDate);
					applicantsDto.setInvoiceGenerateDate(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(), dtOrg.toDate()));
					}
					applicantsDto.setJoinedDate(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),requirementSubmission.getJoinedDate()));
				}
				
				List<Contracts> contractsList = getSowContract(requirementSubmission.getCandidate().getId(), orgLocations, orgRelationId, contractType);
				if (null != contractsList && contractsList.size() > 0) {
					applicantsDto.setContractsList(contractsList);
				}	
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			
			listOfApplicants.add(applicantsDto);
		});
		
		return listOfApplicants;
	}

	@Override
	public List<RequirementSubmission> getSubmissionListByOrgLocationAndSubmissionStatusAndJoiningDate(
			Long orgLocationId, String submissionStatus, Date joiningDate) throws Exception {
		return requirementSubmissionDao.getSubmissionListByOrgLocationAndSubmissionStatusAndJoiningDate(orgLocationId, submissionStatus, joiningDate);
	}

} 