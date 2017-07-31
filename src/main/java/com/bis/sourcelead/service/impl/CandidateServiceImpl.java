package com.bis.sourcelead.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;

import javax.mail.internet.InternetAddress;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.CandidateDao;
import com.bis.sourcelead.dao.CandidateTimelineActivityDao;
import com.bis.sourcelead.elk.index.CandidateIndex;
import com.bis.sourcelead.elk.index.helper.CandidateIndexHelper;
import com.bis.sourcelead.elk.setup.SourceleadELKBaseIndexer;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.CandidateDocuments;
import com.bis.sourcelead.entity.CandidateEducation;
import com.bis.sourcelead.entity.CandidateEmployers;
import com.bis.sourcelead.entity.CandidateHistory;
import com.bis.sourcelead.entity.CandidateResumes;
import com.bis.sourcelead.entity.CandidateTimelineActivity;
import com.bis.sourcelead.entity.EmailAttachment;
import com.bis.sourcelead.entity.EmailMessage;
import com.bis.sourcelead.entity.EmailScenario;
import com.bis.sourcelead.entity.Expenses;
import com.bis.sourcelead.entity.MasterCandidate;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgRelations;
import com.bis.sourcelead.entity.OrgSpoc;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserRole;
import com.bis.sourcelead.service.CandidateDocumentsService;
import com.bis.sourcelead.service.CandidateEducationService;
import com.bis.sourcelead.service.CandidateEmployersService;
import com.bis.sourcelead.service.CandidateResumesService;
import com.bis.sourcelead.service.CandidateService;
import com.bis.sourcelead.service.EmailScenarioService;
import com.bis.sourcelead.service.MasterCandidateService;
import com.bis.sourcelead.service.OrgRelationsService;
import com.bis.sourcelead.service.OrgSpocService;
import com.bis.sourcelead.service.ProjectsService;
import com.bis.sourcelead.service.RequirementSubmissionService;
import com.bis.sourcelead.service.UserRoleService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.CandidateActivityCommonUtil;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.EmailScenerioUtil;
import com.bis.sourcelead.util.PdfUtil;
import com.bis.sourcelead.util.SourceLeadRandomCodeHelper;
import com.bis.sourcelead.util.UniversalSearchHelper;
import com.bis.sourcelead.web.LoginSessionController;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 
 * @author prasad K
 * @date 25th Aug 2016
 *
 */

@Service
public class CandidateServiceImpl implements CandidateService {
	

	private static final Logger logger = LoggerFactory.getLogger(CandidateServiceImpl.class);

	@Autowired
	CandidateDao candidateDao;

	@Autowired
	private CommonUtil commonUtil;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	CandidateEducationService candidateEducationService;
	
	@Autowired
	CandidateEmployersService candidateEmployersService;
	
	@Autowired
	CandidateDocumentsService candidateDocumentsService;
	
	/*@Autowired
	CandidateSkillsService candidateSkillsService;*/
	
	@Autowired @Lazy
	CandidateResumesService candidateResumesService;
	
	@Autowired
	PdfUtil pdfUtil;
	
	@Autowired
	CandidateIndexHelper candidateIndexHelper;
	
	@Autowired
	RequirementSubmissionService requirementSubmissionService;
	
	
	@Autowired
    private SourceleadELKBaseIndexer<CandidateIndex> sourceleadELKBaseIndexer;
	
	@Autowired
	private UniversalSearchHelper universalSearchHelper;
	
	@Autowired
	UserRoleService userRoleService;
	
	@Autowired
	OrgRelationsService orgRelationsService;
	
	@Autowired
	OrgSpocService orgSpocService;
	
	@Value("${sourceleadUrl}")
    private String sourceleadUrl;
    
    @Autowired
    EmailScenarioService emailScenarioService;
    
    @Autowired
    EmailScenerioUtil emailScenerioUtil;
    
    @Autowired
    MasterCandidateService masterCandidateService;
    
    @Autowired
	private ProjectsService projectsService;
    
    @Autowired
    CandidateTimelineActivityDao candidateTimelineActivityDao;
    
    @Autowired
    CandidateActivityCommonUtil candidateActivityCommonUtil;
    
    protected boolean isNullOrEmpty(String str) {
		return (str == null || "".equals(str.trim()));
	}
    
    
	public String getCandidateId(OrgLocations orgLocations) {
    	String companyShortCode = null;
    	Long number = 000000001L;
		String candidateId = null;
		Candidate checkNumber = candidateDao.checkCandidateIdNumberInDatebase(orgLocations.getId());
		if(checkNumber == null || checkNumber.getGeneratedId()== null  || checkNumber.getGeneratedId().isEmpty()){
			candidateId = number.toString();
		}else{
		String canIdNumber = checkNumber.getGeneratedId();
		int count =0;
		for(int i=0;i< canIdNumber.length();i++){
			char ch = canIdNumber.charAt(i);
			if(Character.isAlphabetic(ch)){
				count++;
			}
		}
		String canIdNum = canIdNumber.substring(count, canIdNumber.length());
		Long canId = Long.parseLong(canIdNum)+1;
			candidateId = canId.toString();
		}
		
        final DecimalFormat decimalFormat = new DecimalFormat("000000000");                
        String ConvetredCandidatesId = decimalFormat.format(Long.parseLong(candidateId));
        StringBuffer sb = new StringBuffer();
        if(orgLocations.getGeneratedUserId() != null && !"".equalsIgnoreCase(orgLocations.getGeneratedUserId())){
        	 companyShortCode = orgLocations.getGeneratedUserId();
        }
		return sb.append(companyShortCode).append(Constants.CANDIDATE_ENTITY).append(ConvetredCandidatesId).toString();
		
	}
	
	/**
	 * @author prasad k
	 * @since  aug 26 2016
	 * @version 0.0.1
	 * 
	 * This method is used to store/save candidate in candidate table.
	 * 1.internalley store/save in candidateEducation in candidateEducation table .
	 * 2.internalley store/save in candidateSkills in candidateSkills table .
	 * 3.internalley store/save in candidateResumes in candidateResumes table .
	 * 4.internalley store/save in candidateEmployers in candidateEmployers table .
	 *  
	 *  
	 * 
	 * @param jsonObj This is the first paramter to storeCandidate method
	 * @param orgLocations This is the second paramter to storeCandidate method
	 * @param user This is the third paramter to storeCandidate method
	 * @param multiFile This is the fourth paramter to storeCandidate method
	 * 
	 * @return Candidate 
	 * 
	 * @throws NullPointer
	 */
	
	@Override
	public Candidate storeCandidate(JSONObject jsonObj,OrgLocations orgLocations,User user,MultipartFile multiFile) throws Exception {
		
		
		StringBuffer activitySummary = new StringBuffer();
        Candidate candidate = null;
		if(jsonObj.has("candidateId") && jsonObj.getString("candidateId") != null && !"".equals(jsonObj.getString("candidateId"))){
			candidate = candidateDao.getCandidateByCandidateId(Long.parseLong(jsonObj.getString("candidateId")));
		}else{	
			candidate = new Candidate();
			candidate.setCreatedDate(commonUtil.currentDate());
			candidate.setCreatedBy(user.getUserCode());
			
			if(jsonObj.has("consultantUser") && jsonObj.getString("consultantUser") !=null){
				User consultantUser = new User();
				consultantUser.setId(Long.valueOf(jsonObj.getString("consultantUser")));
				candidate.setConsultantUser(consultantUser);
				candidate.setCandidateType("Consultant");
			}else{
				candidate.setCandidateType("Candidate");
			}
			candidate.setCandidateUUID(SourceLeadRandomCodeHelper.generateRandomUUID());
			candidate.setIsFavourite(0);
			candidate.setIsFavouriteAssociate(0);
			
			MasterCandidate masterCandidate = null;
			masterCandidate = masterCandidateService.getMasterCandidateByInteligence(jsonObj);
			if(null == masterCandidate){
				masterCandidate = masterCandidateService.storeMasterCandidateByJsonObject(jsonObj, user);
			}
			candidate.setMasterCandidateId(masterCandidate);
		}
        
		if(orgLocations != null){
		candidate.setOrgLocationId(orgLocations);
		}
		if(jsonObj.has("firstName") && jsonObj.getString("firstName") !=null){
			if(candidate.getFirstName() != null && candidate.getFirstName() != "" && !candidate.getFirstName().equalsIgnoreCase(jsonObj.getString("firstName"))){
				activitySummary.append("First Name").append(" : ").append(Constants.OLD_VALUE_TAG_INITIATION).append(candidate.getFirstName()).append(Constants.OLD_VALUE_TAG_END).append(" ").append(Constants.NEW_VALUE_TAG_INITIATION).append(jsonObj.getString("firstName")).append(Constants.NEW_VALUE_TAG_END).append(" ");
			}
		candidate.setFirstName(jsonObj.getString("firstName"));
		}
		
		if(jsonObj.has("lastName") && jsonObj.getString("lastName") !=null){
			if(candidate.getLastName() != null && candidate.getLastName() != "" && !candidate.getLastName().equalsIgnoreCase(jsonObj.getString("lastName"))){
				activitySummary.append("Last Name").append(" : ").append(Constants.OLD_VALUE_TAG_INITIATION).append(candidate.getLastName()).append(Constants.OLD_VALUE_TAG_END).append(" ").append(Constants.NEW_VALUE_TAG_INITIATION).append(jsonObj.getString("lastName")).append(Constants.NEW_VALUE_TAG_END).append(" ");
			}
		candidate.setLastName(jsonObj.getString("lastName") );
		}
		
		if(jsonObj.has("country") && jsonObj.getString("country") != null && jsonObj.getString("country") !="" && jsonObj.getString("country")!="undefined"){
			if(candidate.getCountry() != null && candidate.getCountry() != "" && !candidate.getCountry().equalsIgnoreCase(jsonObj.getString("country"))){
				activitySummary.append("Country").append(" : ").append(Constants.OLD_VALUE_TAG_INITIATION).append(candidate.getCountry()).append(Constants.OLD_VALUE_TAG_END).append(" ").append(Constants.NEW_VALUE_TAG_INITIATION).append(jsonObj.getString("country")).append(Constants.NEW_VALUE_TAG_END).append(" ");
			}
		candidate.setCountry(jsonObj.getString("country"));
		}
		if(jsonObj.has("state") && jsonObj.getString("state") !=null){
			if(candidate.getState() != null && candidate.getState() != "" && !candidate.getState().equalsIgnoreCase(jsonObj.getString("state"))){
				activitySummary.append("State").append(" : ").append(Constants.OLD_VALUE_TAG_INITIATION).append(candidate.getState()).append(Constants.OLD_VALUE_TAG_END).append(" ").append(Constants.NEW_VALUE_TAG_INITIATION).append(jsonObj.getString("state")).append(Constants.NEW_VALUE_TAG_END).append(" ");
			}
		candidate.setState(jsonObj.getString("state"));
		}
		if(jsonObj.has("city") && jsonObj.getString("city") !=null){
			if(candidate.getCity() != null && candidate.getCity() != "" && !candidate.getCity().equalsIgnoreCase(jsonObj.getString("city"))){
				activitySummary.append("City").append(" : ").append(Constants.OLD_VALUE_TAG_INITIATION).append(candidate.getCity()).append(Constants.OLD_VALUE_TAG_END).append(" ").append(Constants.NEW_VALUE_TAG_INITIATION).append(jsonObj.getString("city")).append(Constants.NEW_VALUE_TAG_END).append(" ");
			}
		candidate.setCity(jsonObj.getString("city"));
		}
		if(jsonObj.has("location") && jsonObj.getString("location") !=null){
			candidate.setLocation(jsonObj.getString("location"));
		}
		if(jsonObj.has("zipcode") && jsonObj.getString("zipcode") !=null){
			if(candidate.getZipCode() != null && candidate.getZipCode() != "" && !candidate.getZipCode().equalsIgnoreCase(jsonObj.getString("zipcode"))){
				activitySummary.append("Zip Code").append(" : ").append(Constants.OLD_VALUE_TAG_INITIATION).append(candidate.getZipCode()).append(Constants.OLD_VALUE_TAG_END).append(" ").append(Constants.NEW_VALUE_TAG_INITIATION).append(jsonObj.getString("zipcode")).append(Constants.NEW_VALUE_TAG_END).append(" ");
			}
			candidate.setZipCode(jsonObj.getString("zipcode"));
		}
		
		if(jsonObj.has("latitude") && jsonObj.getString("latitude") !=null){
			candidate.setLatitude(jsonObj.getString("latitude"));
		}
		
		if(jsonObj.has("longitude") && jsonObj.getString("longitude") !=null){
			candidate.setLongitude(jsonObj.getString("longitude"));
		}
		
		if(jsonObj.has("mobilePhone") && jsonObj.getString("mobilePhone") !=null){
			if(candidate.getMobilePhone() != null && candidate.getMobilePhone() != "" && !candidate.getMobilePhone().equalsIgnoreCase(jsonObj.getString("mobilePhone"))){
				activitySummary.append("Mobile Phone").append(" : ").append(Constants.OLD_VALUE_TAG_INITIATION).append(candidate.getMobilePhone()).append(Constants.OLD_VALUE_TAG_END).append(" ").append(Constants.NEW_VALUE_TAG_INITIATION).append(jsonObj.getString("mobilePhone")).append(Constants.NEW_VALUE_TAG_END).append(" ");
			}
		candidate.setMobilePhone(jsonObj.getString("mobilePhone"));
		}
		if(jsonObj.has("alternateMobilePhone") && jsonObj.getString("alternateMobilePhone") !=null){
			if(candidate.getAlternateMobilePhone() != null && candidate.getAlternateMobilePhone() != "" && !candidate.getAlternateMobilePhone().equalsIgnoreCase(jsonObj.getString("alternateMobilePhone"))){
				activitySummary.append("Alternate Mobile Phone").append(" : ").append(Constants.OLD_VALUE_TAG_INITIATION).append(candidate.getAlternateMobilePhone()).append(Constants.OLD_VALUE_TAG_END).append(" ").append(Constants.NEW_VALUE_TAG_INITIATION).append(jsonObj.getString("alternateMobilePhone")).append(Constants.NEW_VALUE_TAG_END).append(" ");
			}
			candidate.setAlternateMobilePhone(jsonObj.getString("alternateMobilePhone"));
		}
		if(jsonObj.has("email") && jsonObj.getString("email") !=null){
			if(candidate.getEmail() != null && candidate.getEmail() != "" && !candidate.getEmail().equalsIgnoreCase(jsonObj.getString("email"))){
				activitySummary.append("Email").append(" : ").append(Constants.OLD_VALUE_TAG_INITIATION).append(candidate.getEmail()).append(Constants.OLD_VALUE_TAG_END).append(" ").append(Constants.NEW_VALUE_TAG_INITIATION).append(jsonObj.getString("email")).append(Constants.NEW_VALUE_TAG_END).append(" ");
			}
		candidate.setEmail(jsonObj.getString("email").trim());
		}
		if(jsonObj.has("currentRate") && jsonObj.getString("currentRate") !=null){
			if(candidate.getCurrentRate() != null && candidate.getCurrentRate() != "" && !candidate.getCurrentRate().equalsIgnoreCase(jsonObj.getString("currentRate"))){
				activitySummary.append("Current Rate").append(" : ").append(Constants.OLD_VALUE_TAG_INITIATION).append(candidate.getCurrentRate()).append(Constants.OLD_VALUE_TAG_END).append(" ").append(Constants.NEW_VALUE_TAG_INITIATION).append(jsonObj.getString("currentRate")).append(Constants.NEW_VALUE_TAG_END).append(" ");
			}
		candidate.setCurrentRate(jsonObj.getString("currentRate"));
		}
		
		if(jsonObj.has("currentRateType") && jsonObj.getString("currentRateType") !=null){
			if(candidate.getCurrentRateType() != null && candidate.getCurrentRateType() != "" && !candidate.getCurrentRateType().equalsIgnoreCase(jsonObj.getString("currentRateType"))){
				activitySummary.append("Current Rate Type").append(" : ").append(Constants.OLD_VALUE_TAG_INITIATION).append(candidate.getCurrentRateType()).append(Constants.OLD_VALUE_TAG_END).append(" ").append(Constants.NEW_VALUE_TAG_INITIATION).append(jsonObj.getString("currentRateType")).append(Constants.NEW_VALUE_TAG_END).append(" ");
			}
			candidate.setCurrentRateType(jsonObj.getString("currentRateType"));
			}
		
		if(jsonObj.has("currentRateCurrency") && jsonObj.getString("currentRateCurrency") !=null){
			if(candidate.getCurrentRateCurrency() != null && candidate.getCurrentRateCurrency() != "" && !candidate.getCurrentRateCurrency().equalsIgnoreCase(jsonObj.getString("currentRateCurrency"))){
				activitySummary.append("Current Rate Currency").append(" : ").append(Constants.OLD_VALUE_TAG_INITIATION).append(candidate.getCurrentRateCurrency()).append(Constants.OLD_VALUE_TAG_END).append(" ").append(Constants.NEW_VALUE_TAG_INITIATION).append(jsonObj.getString("currentRateCurrency")).append(Constants.NEW_VALUE_TAG_END).append(" ");
			}
		candidate.setCurrentRateCurrency(jsonObj.getString("currentRateCurrency"));
		}
		if(jsonObj.has("designation") && jsonObj.getString("designation") !=null){
			if(candidate.getDesignation() != null && candidate.getDesignation() != "" && !candidate.getDesignation().equalsIgnoreCase(jsonObj.getString("designation"))){
				activitySummary.append("Designation").append(" : ").append(Constants.OLD_VALUE_TAG_INITIATION).append(candidate.getDesignation()).append(Constants.OLD_VALUE_TAG_END).append(" ").append(Constants.NEW_VALUE_TAG_INITIATION).append(jsonObj.getString("designation")).append(Constants.NEW_VALUE_TAG_END).append(" ");
			}
		candidate.setDesignation(jsonObj.getString("designation"));
		}
		
		if(jsonObj.has("designationOtherType") && jsonObj.has("designationType") && "Other".equalsIgnoreCase(jsonObj.getString("designationType"))){
			if(candidate.getDesignationOtherType() != null && candidate.getDesignationOtherType() != "" && !candidate.getDesignationOtherType().equalsIgnoreCase(jsonObj.getString("designationOtherType"))){
				activitySummary.append("Designation Other Type").append(" : ").append(Constants.OLD_VALUE_TAG_INITIATION).append(candidate.getDesignationOtherType()).append(Constants.OLD_VALUE_TAG_END).append(" ").append(Constants.NEW_VALUE_TAG_INITIATION).append(jsonObj.getString("designationOtherType")).append(Constants.NEW_VALUE_TAG_END).append(" ");
			}
			candidate.setDesignationOtherType(jsonObj.getString("designationOtherType"));
		}else{
			candidate.setDesignationOtherType(null);
		}
		
		if(jsonObj.has("expectedRate") && jsonObj.getString("expectedRate") !=null ){
			if(candidate.getExpectedRate() != null && candidate.getExpectedRate() != "" && !candidate.getExpectedRate().equalsIgnoreCase(jsonObj.getString("expectedRate"))){
				activitySummary.append("Expected Rate").append(" : ").append(Constants.OLD_VALUE_TAG_INITIATION).append(candidate.getExpectedRate()).append(Constants.OLD_VALUE_TAG_END).append(" ").append(Constants.NEW_VALUE_TAG_INITIATION).append(jsonObj.getString("expectedRate")).append(Constants.NEW_VALUE_TAG_END).append(" ");
			}
		candidate.setExpectedRate(jsonObj.getString("expectedRate"));
		}
		
		if(jsonObj.has("rateType") && jsonObj.getString("rateType") !=null ){
			if(candidate.getRateType() != null && candidate.getRateType() != "" && !candidate.getRateType().equalsIgnoreCase(jsonObj.getString("rateType"))){
				activitySummary.append("Rate Type").append(" : ").append(Constants.OLD_VALUE_TAG_INITIATION).append(candidate.getRateType()).append(Constants.OLD_VALUE_TAG_END).append(" ").append(Constants.NEW_VALUE_TAG_INITIATION).append(jsonObj.getString("rateType")).append(Constants.NEW_VALUE_TAG_END).append(" ");
			}
			candidate.setRateType(jsonObj.getString("rateType"));
			}
		
		if(jsonObj.has("expectedRateCurrencey") && jsonObj.getString("expectedRateCurrencey") !=null ){
			if(candidate.getExpectedRateCurrencey() != null && candidate.getExpectedRateCurrencey() != "" && !candidate.getExpectedRateCurrencey().equalsIgnoreCase(jsonObj.getString("expectedRateCurrencey"))){
				activitySummary.append("Expected Rate Currencey").append(" : ").append(Constants.OLD_VALUE_TAG_INITIATION).append(candidate.getExpectedRateCurrencey()).append(Constants.OLD_VALUE_TAG_END).append(" ").append(Constants.NEW_VALUE_TAG_INITIATION).append(jsonObj.getString("expectedRateCurrencey")).append(Constants.NEW_VALUE_TAG_END).append(" ");
			}
			candidate.setExpectedRateCurrencey(jsonObj.getString("expectedRateCurrencey"));
		}
		
		if(jsonObj.has("summary") && jsonObj.getString("summary") !=null){
			if(candidate.getSummary() != null && candidate.getSummary() != "" && !candidate.getSummary().equalsIgnoreCase(jsonObj.getString("summary"))){
				activitySummary.append("Summary").append(" : ").append(Constants.OLD_VALUE_TAG_INITIATION).append(candidate.getSummary()).append(Constants.OLD_VALUE_TAG_END).append(" ").append(Constants.NEW_VALUE_TAG_INITIATION).append(jsonObj.getString("summary")).append(Constants.NEW_VALUE_TAG_END).append(" ");
			}
		candidate.setSummary(jsonObj.getString("summary"));
		}
		if(jsonObj.has("designationType") && jsonObj.getString("designationType") !=null){
			if(candidate.getDesignationType() != null && candidate.getDesignationType() != "" && !candidate.getDesignationType().equalsIgnoreCase(jsonObj.getString("designationType"))){
				activitySummary.append("Designation Type").append(" : ").append(Constants.OLD_VALUE_TAG_INITIATION).append(candidate.getDesignationType()).append(Constants.OLD_VALUE_TAG_END).append(" ").append(Constants.NEW_VALUE_TAG_INITIATION).append(jsonObj.getString("designationType")).append(Constants.NEW_VALUE_TAG_END).append(" ");
			}
		candidate.setDesignationType(jsonObj.getString("designationType"));
		}
		if(jsonObj.has("experienceYears") && jsonObj.getString("experienceYears") !=null){
			if(candidate.getExperienceYears() != null && candidate.getExperienceYears() != "" && !candidate.getExperienceYears().equalsIgnoreCase(jsonObj.getString("experienceYears"))){
				activitySummary.append("Experience Years").append(" : ").append(Constants.OLD_VALUE_TAG_INITIATION).append(candidate.getExperienceYears()).append(Constants.OLD_VALUE_TAG_END).append(" ").append(Constants.NEW_VALUE_TAG_INITIATION).append(jsonObj.getString("experienceYears")).append(Constants.NEW_VALUE_TAG_END).append(" ");
			}
		candidate.setExperienceYears(jsonObj.getString("experienceYears"));
		}
		
		
		if(jsonObj.has("experienceMonths") && jsonObj.getString("experienceMonths") !=null){
			if(candidate.getExperienceMonths() != null && candidate.getExperienceMonths() != "" && !candidate.getExperienceMonths().equalsIgnoreCase(jsonObj.getString("experienceMonths"))){
				activitySummary.append("Experience Months").append(" : ").append(Constants.OLD_VALUE_TAG_INITIATION).append(candidate.getExperienceMonths()).append(Constants.OLD_VALUE_TAG_END).append(" ").append(Constants.NEW_VALUE_TAG_INITIATION).append(jsonObj.getString("experienceMonths")).append(Constants.NEW_VALUE_TAG_END).append(" ");
			}
			candidate.setExperienceMonths(jsonObj.getString("experienceMonths"));	
		}else{
			candidate.setExperienceMonths(null);	
		}
		
		if(jsonObj.has("noticePeriod") && jsonObj.getString("noticePeriod") !=null){
			if(candidate.getNoticePeriod() != null && candidate.getNoticePeriod() != "" && !candidate.getNoticePeriod().equalsIgnoreCase(jsonObj.getString("noticePeriod"))){
				activitySummary.append("Notice Period").append(" : ").append(Constants.OLD_VALUE_TAG_INITIATION).append(candidate.getNoticePeriod()).append(Constants.OLD_VALUE_TAG_END).append(" ").append(Constants.NEW_VALUE_TAG_INITIATION).append(jsonObj.getString("noticePeriod")).append(Constants.NEW_VALUE_TAG_END).append(" ");
			}
			candidate.setNoticePeriod(jsonObj.getString("noticePeriod"));	
		}
		
		if(jsonObj.has("address") && jsonObj.getString("address") !=null){
			if(candidate.getAddress() != null && candidate.getAddress() != "" && !candidate.getAddress().equalsIgnoreCase(jsonObj.getString("address"))){
				activitySummary.append("Address").append(" : ").append(Constants.OLD_VALUE_TAG_INITIATION).append(candidate.getAddress()).append(Constants.OLD_VALUE_TAG_END).append(" ").append(Constants.NEW_VALUE_TAG_INITIATION).append(jsonObj.getString("address")).append(Constants.NEW_VALUE_TAG_END).append(" ");
			}
			candidate.setAddress(jsonObj.getString("address"));
		}
		
		UserRole userRole = userRoleService.getUserDetailsByUserIdAndLocationIdAndUserType(user.getId(),orgLocations.getId(),"Recruiter");
		if(null != userRole && userRole.getRoles().getRoleName().equalsIgnoreCase("ROLE_CROWDSOURCING_RECRUITER")){
			candidate.setRecruiterUserId(userRole.getUser());	
		}
		
		if(jsonObj.has("candidateSkills") && jsonObj.getString("candidateSkills") !=null){
			String candidateSkills = null;
			try{
	    		   JSONArray jsonArray = jsonObj.getJSONArray("candidateSkills");
	    		   candidateSkills = jsonArray.toString();
	    	  }
	    	  catch(JSONException e){
	    		  candidateSkills= jsonObj.getString("candidateSkills");
	    	  }
			candidateSkills = candidateSkills.replaceAll("\"", "").replaceAll("\\[", "").replaceAll("\\]","");
			
			if(candidate.getSkillsName() != null && candidate.getSkillsName() != "" && !candidate.getSkillsName().equalsIgnoreCase(jsonObj.getString("candidateSkills"))){
				activitySummary.append("Skills").append(" : ").append(Constants.OLD_VALUE_TAG_INITIATION).append(candidate.getSkillsName()).append(Constants.OLD_VALUE_TAG_END).append(" ").append(Constants.NEW_VALUE_TAG_INITIATION).append(candidateSkills).append(Constants.NEW_VALUE_TAG_END).append(" ");
			}
			candidate.setSkillsName(candidateSkills);
		}
		
		if(jsonObj.has("visa")&& jsonObj.getString("visa") !=null){
			
			String visaList =null;
			try{
				JSONArray jsonArray = jsonObj.getJSONArray("visa");
				visaList = jsonArray.toString();
			}
			catch(JSONException e){
				visaList =jsonObj.getString("visa");
			}
			visaList = visaList.replaceAll("\\[", "").replaceAll("\\]","");
			visaList = visaList.replaceAll("\"", "");
			
			if(candidate.getVisaType() != null && candidate.getVisaType() != "" && !candidate.getVisaType().equalsIgnoreCase(jsonObj.getString("visa"))){
				activitySummary.append("Immigration Status").append(" : ").append(Constants.OLD_VALUE_TAG_INITIATION).append(candidate.getVisaType()).append(Constants.OLD_VALUE_TAG_END).append(" ").append(Constants.NEW_VALUE_TAG_INITIATION).append(visaList).append(Constants.NEW_VALUE_TAG_END).append(" ");
			}
			candidate.setVisaType(visaList);
	  	 
		}
		
		if(jsonObj.has("primarySkill") && jsonObj.getString("primarySkill") != null){
			if(candidate.getPrimarySkill() != null && candidate.getPrimarySkill() != "" && !candidate.getPrimarySkill().equalsIgnoreCase(jsonObj.getString("primarySkill"))){
				activitySummary.append("Primary Skills").append(" : ").append(Constants.OLD_VALUE_TAG_INITIATION).append(candidate.getPrimarySkill()).append(Constants.OLD_VALUE_TAG_END).append(" ").append(Constants.NEW_VALUE_TAG_INITIATION).append(jsonObj.getString("primarySkill")).append(Constants.NEW_VALUE_TAG_END).append(" ");
			}
			candidate.setPrimarySkill(jsonObj.getString("primarySkill"));
		}

		
		
		if(jsonObj.has("vendors") && !"".equalsIgnoreCase(jsonObj.getString("vendors"))){
			OrgRelations orgRelation = orgRelationsService.getOrRelationsById(Long.parseLong(jsonObj.getString("vendors")));
			candidate.setOrgRelationId(orgRelation);
		}else{
			candidate.setOrgRelationId(null);
		}
		
		if(jsonObj.has("vendorcontact") && !"".equalsIgnoreCase(jsonObj.getString("vendorcontact"))){
			OrgSpoc orgspoc = orgSpocService.getOrgSpocById(Long.parseLong(jsonObj.getString("vendorcontact")));
			candidate.setSpocId(orgspoc);
		}else{
			candidate.setSpocId(null);
		}
		
		HashSet<CandidateEducation>	 candidateEducationList = candidateEducationService.getCandidateEducationDetails(jsonObj, orgLocations,candidate,user.getUserCode());
		candidate.setCandidateEducation(candidateEducationList);
		
		HashSet<CandidateEmployers> candidateEmployersList = candidateEmployersService.getCandidateEmployersDetails(jsonObj,orgLocations,candidate,user.getUserCode());
		candidate.setCandidateEmployers(candidateEmployersList);
		
		/*HashSet<CandidateSkills> candidateSkillsList = candidateSkillsService.getCandidateSkillsDetails(jsonObj, orgLocations,candidate,user.getUserCode());
		candidate.setCandidateSkills(candidateSkillsList);*/
        
		if(multiFile != null){
		HashSet<CandidateResumes> candidateResumesList = candidateResumesService.getCandidateResumesDetails(orgLocations,user.getUserCode(),candidate,multiFile);
		candidate.setCandidateResumes(candidateResumesList);
		}
		/*if(multiFile != null){
			if(multiFile.getOriginalFilename()!=null){
				candidate.setFileName(multiFile.getOriginalFilename());
			}
		}*/
		
		if(jsonObj.has("isAssociate") && jsonObj.getString("isAssociate") != null && Constants.YES.equalsIgnoreCase(jsonObj.getString("isAssociate"))){
			candidate.setIsAssociate(Constants.YES);
		}else{
			candidate.setIsAssociate(Constants.NO);
		}
		candidate.setUpdatedBy(user.getUserCode());
		candidate.setUpdatedDate(commonUtil.currentDate());
		candidate.setStatus(Constants.CANDIDATE_ACTIVE);
		
		/*
		 * System Generated Id For Candidate
		 */
		/*String entityGenId = RandomStringUtils.randomNumeric(9);
		StringBuffer sb = new StringBuffer();
		candidate.setSystemGeneratedId(Constants.CANDIDATE_ENTITY);
		candidate.setGeneratedId(sb.append(candidate.getSystemGeneratedId()).append(entityGenId).toString());*/
		
		candidate.setGeneratedId(getCandidateId(orgLocations));

		Candidate savedCandidate = candidateDao.storeCandidate(candidate);
		candidateActivityCommonUtil.storeCandidateHistory(savedCandidate, user);
		
		/*
		 * this method is for track candidate activity
		 */
		if(jsonObj.has("candidateId") && jsonObj.getString("candidateId") != null && !"".equals(jsonObj.getString("candidateId"))){
			candidateActivityCommonUtil.storeCandidateActivity(user, orgLocations, savedCandidate, "", Constants.CANDIDATE_EDITTED, activitySummary.toString(), "CANDIDATE");
		}else{
			candidateActivityCommonUtil.storeCandidateActivity(user, orgLocations, savedCandidate, "", Constants.CANDIDATE_ADDED_MANUALLY, "", "CANDIDATE");
		}
		
		
		/*
		 * this method is for inviting candidate to sourcelead
		 */
		 if(jsonObj.has("inviteEmail") && null != jsonObj.getString("inviteEmail") && Constants.YES.equalsIgnoreCase(jsonObj.getString("inviteEmail"))){
				inviteCandidateToSourceLead(user,savedCandidate,orgLocations);
			}
		
		/*
		 * Saving Candidate DB data as ElasticSearch Documents
		 */
		saveOrUpdateCandidateElkDocument(savedCandidate);
		return null;
	}

	private void inviteCandidateToSourceLead(User loginUser, Candidate candidate, OrgLocations orgLocations) throws Exception
    {
         StringBuilder sb = new StringBuilder();
         EmailMessage emailMessage = new EmailMessage();
         emailMessage.setFirstName(candidate.getFirstName());
         if(null != candidate.getLastName() && !"".equalsIgnoreCase(candidate.getLastName())){
             emailMessage.setLastName(candidate.getLastName());
         }else{
             emailMessage.setLastName("");
         }
         emailMessage.setToEmail(candidate.getEmail());
         emailMessage.setFromEmail(loginUser.getEmail());
         if(null != candidate.getLastName() && !"".equalsIgnoreCase(candidate.getLastName())){
             emailMessage.setFromName(sb.append(loginUser.getFirstName()).append(" ").append(loginUser.getLastName()).toString());
         }else{
             emailMessage.setFromName(loginUser.getFirstName());
         }
         emailMessage.setEmailType(Constants.CUSTOM_USER_EMAIL);
         emailMessage.setCreatedBy(loginUser.getUserCode());
         emailMessage.setOrgLocationId(orgLocations.getId().longValue());
        
        //setting the class filed values
         Map<String, String> globalVarsMap = new HashMap<String, String>();
         globalVarsMap.put("loginUser", emailMessage.getFromName());
         if(null != candidate.getLastName() && !"".equalsIgnoreCase(candidate.getLastName())){
             StringBuilder candisb = new StringBuilder();
             globalVarsMap.put("candidate", candisb.append(candidate.getFirstName()).append(" ").append(candidate.getLastName()).toString());
         }else{
             globalVarsMap.put("candidate", candidate.getFirstName());
         }
        
         globalVarsMap.put("loginOrg",orgLocations.getOrgLocationName());
         StringBuilder loginBuilder = new StringBuilder();
         globalVarsMap.put("registerURL",loginBuilder.append("<a href='").append(sourceleadUrl).append("/register").append("' style='text-decoration:none; '><div style=' width:220px; border-radius:30px; background-color:#214b90; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Register</div></a>").toString());
        
         //setting the Recipient information
         //setting the Recipient information
         InternetAddress[] iAdressArray = InternetAddress.parse(emailMessage.getToEmail());
        
         //getting the current EmailScenario by EmailScenario unique name and entity type
         EmailScenario  emailScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.CANDIDATE_INVITATION,Constants.ENTITY_TYPE_EMAIL);

        
         //covert subject,body and signature
         String subject = emailScenerioUtil.bulidSubject(emailScenario.getSubject(),globalVarsMap);
         String body = emailScenerioUtil.buildTempBody(emailScenario.getBody(),globalVarsMap);
         String signature = emailScenerioUtil.buildSignature(emailScenario.getSignature(),globalVarsMap);
         String url = sourceleadUrl;
        
         //construct HTML template using required data
         String emailTemplate = emailScenerioUtil.AssembleEmailMessage(emailScenario,emailMessage, subject, body, signature, url, orgLocations.getOrg(),orgLocations);
         emailMessage.setSubject(subject);
         emailMessage.setMessageBody(emailTemplate);
        
        //calling the sendEmail method of emailScenerioUtil
         emailScenerioUtil.sendEmail(emailMessage,orgLocations.getId(),iAdressArray,new ArrayList<EmailAttachment>());
        
    }

	@Override
	public Candidate addCandidate(Candidate candidate) throws Exception {
		return candidateDao.storeCandidate(candidate);
	}


	/**
	 * to Candidates List by OrgCode OrgLocationCode
	 * 
	 * @author David Sammeta
	 * 
	 * @param request
	 * 
	 * @return String
	 * 
	 * @throws Exception
    */
	@Override
	public List<Candidate> getCandidatesListByOrgLocationID(Long orgLocationId,Long orgId,Integer candidateStatus,String updatedDateRange,String candidateType,String isAssociate) throws Exception {
		List<Candidate> candidateList = candidateDao.getCandidatesListByOrgLocationID(orgLocationId,orgId,candidateStatus,updatedDateRange,candidateType,isAssociate);
		for(Candidate can :candidateList){
			if(can.getUpdatedDate() != null){
			 can.setCandidateUpdateDateToString(Constants.DF_DMY.format(can.getUpdatedDate()));
			}
		}
		return candidateList;
	}
	
	/**
	 * to get Candidate By CandidateId
	 * 
	 * @author Venkat Kesav
	 * 
	 * @param candidateId
	 * 
	 * @return Candidate
	 * 
	 * @throws Exception
    */
	@Override
	public Candidate getCandidateByCandidateId(Long candidateId) throws Exception {
		return candidateDao.getCandidateByCandidateId(candidateId);
	}
	
	/**
	 * @author  prasad.k
	 * 
	 * This method is used to get Candidate By CandidateId And OrgLocationId
	 * 
	 * @param candidateId
	 * @param orgLocationId
	 * 
	 * @return candidate
	 * 
	 * @throws Exception
	 */
	@Override
	public Candidate getCandidateByCandidateIdAndOrgLocationId(Long candidateId,Long orgLocationId) throws Exception {
	
		 Candidate  candidate = candidateDao.getCandidateByCandidateIdAndOrgLocationId(candidateId,orgLocationId);
		 return candidate;
	}

	/**
	 * @author prasad.k
	 * 
	 * This mothod is used to get CandidateEmail
	 * 
	 * @param email
	 * 
	 * @return CandidateEmail
	 * 
	 * @throws Exception
	 */
	@Override
	public Candidate findByCandidateEmail(String email,Long orgLocationId) throws Exception {
		logger.debug("In CandidateService class...");
		return candidateDao.findByCandidateEmail(email,orgLocationId);
	}
	
	
	/**
	 * @author Venkat Kesav
	 * 
	 * This method is used to save Or Update Candidate
	 * 
	 * @param candidate
	 * 
	 * @return candidate
	 * 
	 * @throws Exception
	 */
	@Override
	public Candidate saveOrUpdateCandidate(Candidate candidate) throws Exception {
		logger.debug("In CandidateService class...");
		return candidateDao.storeCandidate(candidate);
	}

	@Override
	public List<Candidate> getCandidatesByOrgLocationID(Long orgLocationId,Long requirementId, String candidateType) throws Exception {
		return candidateDao.getCandidatesByOrgLocationID(orgLocationId,requirementId,candidateType);
	}

	@Override
	public List<Candidate> getAllCandidatesByLocation(Long orgLocationId) throws Exception {
		return candidateDao.getAllCandidatesByLocation(orgLocationId);
	}
/**
 * @author Rajashekar
 * Write candidate data to xls format
 */
	@Override
	public File getCandidateExcelFile(List<Candidate> candidatesList,String orgName,String isAssociate) throws Exception {
		
		Workbook createExcelworkbook =  new XSSFWorkbook();
		CellStyle style = commonUtil.getCellStyle(createExcelworkbook);
        
		DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
		 StringBuilder excelSheet = new StringBuilder();
		 Sheet createExcelsheet;
		 if(isAssociate.equalsIgnoreCase(Constants.YES)){
			 createExcelsheet = createExcelworkbook.createSheet(excelSheet.append(orgName).append("_Associate_").append(dateFormat.format(new Date())).toString());	 
		 }else{
			 createExcelsheet = createExcelworkbook.createSheet(excelSheet.append(orgName).append("_Candidate_").append(dateFormat.format(new Date())).toString());
		 }
		 
    	Row writeRow = null;
		String headers [] = Constants.CANDIDATE_EXCEL_HEADERS;
		createExcelsheet = commonUtil.setExcelHeaders(createExcelsheet,style,headers,0);
		 int column=0;
	        for(Candidate candidate : candidatesList){
	       	 writeRow = createExcelsheet.createRow(column+1);
	       	 createExcelsheet = createXlsSheet(createExcelsheet,writeRow,candidate,isAssociate);
	       	 column++;
	        }
	        StringBuilder orgNameSB = new StringBuilder();
	        File file;
	        if(isAssociate.equalsIgnoreCase(Constants.YES)){
	        	 file = File.createTempFile(orgNameSB.append(orgName).append("_Associates_").append(dateFormat.format(new Date())).toString(),".xls");	
	        }else{
	        	 file = File.createTempFile(orgNameSB.append(orgName).append("_Candidates_").append(dateFormat.format(new Date())).toString(),".xls");
	        }
	        
	        file.setReadable(true);
	        /*File file = new File(orgName+"_candidates_"+dateFormat.format(new Date())+".xls");*/
			 FileOutputStream fos = new FileOutputStream(file);
			 try {
				 createExcelworkbook.write(fos);
				fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}	 	
		return file;
	}

	/**
	 * @author Rajashekar
	 * writes candidate data to excel
	 * @param createExcelsheet
	 * @param writeRow
	 * @param candidate
	 * @return
	 */
private Sheet createXlsSheet(Sheet createExcelsheet, Row writeRow,Candidate candidate,String isAssociate) {
	if(candidate.getFirstName() !=null && !"".equalsIgnoreCase(candidate.getFirstName())){
		 writeRow.createCell(0).setCellValue(candidate.getFirstName());
	}
	if(candidate.getLastName() !=null && !"".equalsIgnoreCase(candidate.getLastName())){
		writeRow.createCell(1).setCellValue(candidate.getLastName());
	}
	if(candidate.getEmail() !=null && !"".equalsIgnoreCase(candidate.getEmail())){
		writeRow.createCell(2).setCellValue(candidate.getEmail());
	}
	if(candidate.getMobilePhone() !=null && !"".equalsIgnoreCase(candidate.getMobilePhone())){
		writeRow.createCell(3).setCellValue(candidate.getMobilePhone());
	}
	
	StringBuilder loc = new StringBuilder();
	if(candidate.getCity() !=null && !"".equalsIgnoreCase(candidate.getCity())){
		writeRow.createCell(4).setCellValue(loc.append(candidate.getCity()).append(",").append(candidate.getState()).append(",").append(candidate.getCountry()).toString());
	}else if(candidate.getState() !=null && !"".equalsIgnoreCase(candidate.getState())){
		writeRow.createCell(4).setCellValue(loc.append(candidate.getState()).append(",").append(candidate.getCountry()).toString());
	}else if(candidate.getCountry() !=null && !"".equalsIgnoreCase(candidate.getCountry())){
		writeRow.createCell(4).setCellValue(candidate.getCountry());
	}else{
		writeRow.createCell(4).setCellValue("--");
	}
	
	if(candidate.getDesignation() !=null && !"".equalsIgnoreCase(candidate.getDesignation())){
		writeRow.createCell(5).setCellValue(candidate.getDesignation());
	}
	if(candidate.getCandidateEducation() !=null && candidate.getCandidateEducation().size() >0 ){
		Set<CandidateEducation> candidateEducation = candidate.getCandidateEducation();
		StringBuilder educations = new StringBuilder();
		for(CandidateEducation candidateEdu : candidateEducation){
			educations.append(candidateEdu.getName()).append(",");
		}
		String educationList = educations.toString();
		writeRow.createCell(6).setCellValue(educationList.substring(0, educations.length()-1));
	}
	 StringBuilder exp = new StringBuilder();
	if(candidate.getExperienceYears() !=null && !"".equalsIgnoreCase(candidate.getExperienceYears()) && candidate.getExperienceMonths() !=null && !"".equalsIgnoreCase(candidate.getExperienceMonths())){
		writeRow.createCell(7).setCellValue(exp.append(candidate.getExperienceYears()).append(".").append(candidate.getExperienceMonths()).append("years").toString());
	}else{
		if(candidate.getExperienceYears() !=null && !"".equalsIgnoreCase(candidate.getExperienceYears())){
			writeRow.createCell(7).setCellValue(exp.append(candidate.getExperienceYears()).append("years").toString());
		}else{
			if(candidate.getExperienceMonths() !=null && !"".equalsIgnoreCase(candidate.getExperienceMonths())){
				writeRow.createCell(7).setCellValue(exp.append(candidate.getExperienceMonths()).append("Months").toString());
			}
		}
	}
	if(candidate.getSkillsName() !=null ){
		StringBuilder skills = new StringBuilder();
		String [] candidateSkillsList = candidate.getSkillsName().split(",");
	    for(String skill : candidateSkillsList){
	    	skills.append(skill).append(",");
	    }
	    writeRow.createCell(8).setCellValue(skills.toString().substring(0, skills.length()-1));
	}
	if(candidate.getExpectedRate() !=null && !"".equalsIgnoreCase(candidate.getExpectedRate()) && candidate.getRateType() !=null && !"".equalsIgnoreCase(candidate.getRateType())){
		StringBuilder rateBuilder = new StringBuilder();
		writeRow.createCell(9).setCellValue(rateBuilder.append(candidate.getExpectedRate()).append("/").append(candidate.getRateType()).toString());
	}else{
		writeRow.createCell(9).setCellValue(candidate.getExpectedRate());
	}
	if(null != candidate.getOrgRelationId()){
		if(null != candidate.getOrgRelationId().getResponderLocationId()){
			if(null != candidate.getOrgRelationId().getResponderLocationId().getOrgLocationName()  && !"".equalsIgnoreCase(candidate.getOrgRelationId().getResponderLocationId().getOrgName())){
				writeRow.createCell(10).setCellValue(candidate.getOrgRelationId().getResponderLocationId().getOrgLocationName());
			}
		}
	}else{
				writeRow.createCell(10).setCellValue("--");
	}
	
	if(null != candidate.getSpocId()){
		if(null != candidate.getSpocId().getVendorUser().getLastName() && !"".equalsIgnoreCase(candidate.getSpocId().getVendorUser().getLastName())){
			StringBuilder vendorName = new StringBuilder();
			writeRow.createCell(11).setCellValue(vendorName.append(candidate.getSpocId().getVendorUser().getFirstName()).append(" ").append(candidate.getSpocId().getVendorUser().getLastName()).toString());
	}else if(null != candidate.getSpocId().getVendorUser().getFirstName() && !"".equalsIgnoreCase(candidate.getSpocId().getVendorUser().getFirstName())){
			writeRow.createCell(11).setCellValue(candidate.getSpocId().getVendorUser().getFirstName());	
		}
	}else{
			writeRow.createCell(11).setCellValue("--");
	}
	
	if(isAssociate.equalsIgnoreCase(Constants.YES)){
		if(null != candidate.getIsFavouriteAssociate() && candidate.getIsFavouriteAssociate() == 1){
			writeRow.createCell(12).setCellValue("Y");
		}else{
			writeRow.createCell(12).setCellValue("--");
		}
	}else if(null != candidate.getIsFavourite() && candidate.getIsFavourite() == 1){
			writeRow.createCell(12).setCellValue("Y");
		}else{
			writeRow.createCell(12).setCellValue("--");
	}
	
	return createExcelsheet;
}

	@Override
	public File getCandidatePdf(List<Candidate> candidatesList, String orgName,String isAssociate) throws Exception {
		return pdfUtil.createCandidatePdfTable(candidatesList,orgName,isAssociate);
	}

	@Override
	public List<Candidate> getHotListCandidatesListByOrgLocationID(Long orgLocationId, String candidateType)
			throws Exception {
		return candidateDao.getHotListCandidatesListByOrgLocationID(orgLocationId, candidateType);
	}


	@Override
	public List<Candidate> getAllRequestedCandidatesForCampaign(List<Long> candidateId) throws Exception {
		return candidateDao.getAllRequestedCandidatesForCampaign(candidateId);
	}
	
	@Override
	public void saveOrUpdateCandidateElkDocument(Candidate candidate) throws Exception{
		 //Elastic Search Document Saving
		CandidateIndex candidateIndex = candidateIndexHelper.getCandidateIndexFromCandidate(candidate);
		sourceleadELKBaseIndexer.saveOrUpdateElkDocument(candidateIndex, Constants.ELK_CANDIDATE_INDEX, Constants.ELK_CANDIDATE_TYPE,candidate.getId().toString());
	}

	@Override
	public Long getCandidatesCountByOrgLocationID(Long orgLocationId,String candidateStatus) {
		return candidateDao.getCandidatesCountByOrgLocationID(orgLocationId,candidateStatus);
	}

	@Override
	public Long getCandidatesCountByCandidateTypeOrgLocationID(Long userId,Long orgLocationId,String candidateType) {
		return candidateDao.getCandidatesCountByCandidateTypeOrgLocationID(userId,orgLocationId,candidateType);
	}
	
	
	@Override
	public Integer getHotlistCandidatesCountByTeamIdAndOrgLocationId(Long teamId, Long orgLocationId) throws Exception {
		return candidateDao.getHotlistCandidatesCountByTeamIdAndOrgLocationId(teamId, orgLocationId);
	}

	@Override
	public List<Candidate> getHotListCandidatesByTeamAndOrgLocationId(Long teamId, Long orgLocationId)
			throws Exception {
		return candidateDao.getHotListCandidatesByTeamAndOrgLocationId(teamId, orgLocationId);
	}

	@Override
	public Integer getAllActiveCandidatesCountForCampaignByLocation(Long orgLocationId, String type) throws Exception {
		return candidateDao.getAllActiveCandidatesCountForCampaignByLocation(orgLocationId, type);
	}

	@Override
	public Integer getAllHotlistCandidatesCountNotAssignedToTeamByLocation(Long orgLocationId) throws Exception {
		return candidateDao.getAllHotlistCandidatesCountNotAssignedToTeamByLocation(orgLocationId);
	}

	@Override
	public Integer getAllCandidatesCountForCampaignByTeam(List<Long> teamId, String type,Long orgLocationId) throws Exception {
		return candidateDao.getAllCandidatesCountForCampaignByTeam(teamId, type,orgLocationId);
	}

	@Override
	public List<Candidate> getAllCandidatesForHotListingWhoAreTerminating(Long orgLocationId, Integer datRange,Long reportingManagerUserId)
			throws Exception {
		return candidateDao.getAllCandidatesForHotListingWhoAreTerminating(orgLocationId,datRange,reportingManagerUserId);
	}
	
	@Override
	public Candidate getProfessionalInfoByLoginUserIdAndCandidateType(Long userId, String candidateType) throws Exception {
		return candidateDao.getProfessionalInfoByLoginUserIdAndCandidateType(userId,candidateType);
	}
	
	@Override
	public List<Object> getAllUniversalSearchCandidateList(OrgLocations orgLocation,String searchValue)throws Exception {
		List<Object>   searchList = new ArrayList<>();
		 Long maxResults = getCandidatesCountByOrgLocationID(orgLocation.getId(),null);
		 ArrayList<String> anyKeyWordArray = universalSearchHelper.getSearchValuesFromChosenSelectField(searchValue);
			BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();;
			 boolQueryBuilder.must(QueryBuilders.matchQuery("orgLocationId", orgLocation.getId()));
				if(anyKeyWordArray != null && !anyKeyWordArray.isEmpty()){
					 BoolQueryBuilder anyBoolQueryBuilder = QueryBuilders.boolQuery();
					 for(String keywd : anyKeyWordArray){
						 BoolQueryBuilder anyBoolSubQueryBuilder = QueryBuilders.boolQuery();
						ArrayList<MatchQueryBuilder> matchQueryBuilderList = universalSearchHelper.getMatchQueryBuilderListForCandidate(keywd);
						if(matchQueryBuilderList != null && !matchQueryBuilderList.isEmpty()){
							for(MatchQueryBuilder matchQueryBuilder :matchQueryBuilderList){
								anyBoolSubQueryBuilder.should(matchQueryBuilder);
							}
							anyBoolQueryBuilder.should(anyBoolSubQueryBuilder);
						}
					}
					 boolQueryBuilder.must(anyBoolQueryBuilder);
				}
			
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			df.setTimeZone(TimeZone.getTimeZone("GMT"));
			
			SearchHit[] searchHits = sourceleadELKBaseIndexer.searchDocumentWithMaxResults(Constants.ELK_CANDIDATE_INDEX, Constants.ELK_CANDIDATE_TYPE,0,maxResults.intValue(),boolQueryBuilder).getHits();
			    if(null != searchHits && searchHits.length >0){
			    	ObjectMapper mapper = new ObjectMapper();
			    	for (SearchHit hit : searchHits) {
			    		String json = hit.getSourceAsString();
			    		mapper.setDateFormat(df);
			    	    CandidateIndex candidateIndex = mapper.readValue(json, CandidateIndex.class);
			    	    if(candidateIndex.getUpdatedDate() != null){
			    	    	candidateIndex.setCandidateUpdateDateToString(candidateIndex.getUpdatedDate());
			   			}
			    	    searchList.add(candidateIndex);
			    	} 
			    	
			    }
		return searchList;
			
	}


	@Override
	public Candidate getCandidateByEmailAndRecruiterUserId(String email, Long recruiterUserId) throws Exception {
		return candidateDao.getCandidateByEmailAndRecruiterUserId(email, recruiterUserId);
	}


	@Override
	public List<Candidate> getCandidatesByOrgLocationIDAndSearchText(Long orgLocationId, Long requirementId, String candidateType,
			String searchApplicant) throws Exception {
		return candidateDao.getCandidatesByOrgLocationIDAndSearchText(orgLocationId, requirementId, candidateType, searchApplicant);
	}

	@Override
	public Candidate storeProfessionalParseCandidate(JSONObject jsonObj, OrgLocations orgLocations, User user,
			MultipartFile multiFile, Long candidateId) throws Exception {
		
            Candidate  candidate = candidateDao.getCandidateByCandidateId(candidateId);
			if(orgLocations != null){
			candidate.setOrgLocationId(orgLocations);
			}
			candidate.setFirstName(candidate.getFirstName());
			if(null != candidate.getLastName() && !"".equalsIgnoreCase(candidate.getLastName())){
				candidate.setLastName(candidate.getLastName());	
			}
//			candidate.setCountry(candidate.getCountry());
			if(jsonObj.has("country") && jsonObj.getString("country") !=null){
				candidate.setCountry(jsonObj.getString("country"));
			}
			
			if(jsonObj.has("state") && jsonObj.getString("state") !=null){
			candidate.setState(jsonObj.getString("state"));
			}
			if(jsonObj.has("city") && jsonObj.getString("city") !=null){
			candidate.setCity(jsonObj.getString("city"));
			}
			if(jsonObj.has("location") && jsonObj.getString("location") !=null){
				candidate.setLocation(jsonObj.getString("location"));
			}
			if(jsonObj.has("zipcode") && jsonObj.getString("zipcode") !=null){
				candidate.setZipCode(jsonObj.getString("zipcode"));
			}
			
			if(jsonObj.has("mobilePhone") && jsonObj.getString("mobilePhone") !=null){
			candidate.setMobilePhone(jsonObj.getString("mobilePhone"));
			}
			if(jsonObj.has("alternateMobilePhone") && jsonObj.getString("alternateMobilePhone") !=null){
				candidate.setAlternateMobilePhone(jsonObj.getString("alternateMobilePhone"));
			}
			
			if(null != candidate.getEmail()){
				candidate.setEmail(candidate.getEmail());	
			}
			

			if(jsonObj.has("currentRate") && jsonObj.getString("currentRate") !=null){
			candidate.setCurrentRate(jsonObj.getString("currentRate"));
			}
			
			if(jsonObj.has("currentRateType") && jsonObj.getString("currentRateType") !=null){
				candidate.setCurrentRateType(jsonObj.getString("currentRateType"));
				}
			
			if(jsonObj.has("currentRateCurrency") && jsonObj.getString("currentRateCurrency") !=null){
			candidate.setCurrentRateCurrency(jsonObj.getString("currentRateCurrency"));
			}
			if(jsonObj.has("designation") && jsonObj.getString("designation") !=null){
			candidate.setDesignation(jsonObj.getString("designation"));
			}
			
			if(jsonObj.has("designationOtherType") && jsonObj.has("designationType") && "Other".equalsIgnoreCase(jsonObj.getString("designationType"))){
				candidate.setDesignationOtherType(jsonObj.getString("designationOtherType"));
			}else{
				candidate.setDesignationOtherType(null);
			}
			
			if(jsonObj.has("expectedRate") && jsonObj.getString("expectedRate") !=null ){
			candidate.setExpectedRate(jsonObj.getString("expectedRate"));
			}
			
			if(jsonObj.has("rateType") && jsonObj.getString("rateType") !=null ){
				candidate.setRateType(jsonObj.getString("rateType"));
				}
			
			if(jsonObj.has("expectedRateCurrencey") && jsonObj.getString("expectedRateCurrencey") !=null ){
				candidate.setExpectedRateCurrencey(jsonObj.getString("expectedRateCurrencey"));
			}
			if(jsonObj.has("summary") && jsonObj.getString("summary") !=null){
			candidate.setSummary(jsonObj.getString("summary"));
			}
			if(jsonObj.has("designationType") && jsonObj.getString("designationType") !=null){
			candidate.setDesignationType(jsonObj.getString("designationType"));
			}
			if(jsonObj.has("experienceYears") && jsonObj.getString("experienceYears") !=null){
			candidate.setExperienceYears(jsonObj.getString("experienceYears"));
			}
			
			
			if(jsonObj.has("experienceMonths") && jsonObj.getString("experienceMonths") !=null){
				candidate.setExperienceMonths(jsonObj.getString("experienceMonths"));	
			}
			
			if(jsonObj.has("noticePeriod") && jsonObj.getString("noticePeriod") !=null){
				candidate.setNoticePeriod(jsonObj.getString("noticePeriod"));	
			}
			
			if(jsonObj.has("address") && jsonObj.getString("address") !=null){
				candidate.setAddress(jsonObj.getString("address"));
			}
			
			UserRole userRole = userRoleService.getLoginUserPrimaryRole(user.getId());
			if(null != userRole && userRole.getRoles().getRoleName().equalsIgnoreCase("ROLE_CROWDSOURCING_RECRUITER")){
				candidate.setRecruiterUserId(userRole.getUser());	
			}
			
			if(jsonObj.has("candidateSkills") && jsonObj.getString("candidateSkills") !=null){
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
			
			if(jsonObj.has("primarySkill") && jsonObj.getString("primarySkill") !=null){
				candidate.setPrimarySkill(jsonObj.getString("primarySkill"));
			}
			
			if(jsonObj.has("visa")){
				
				String visaList =null;
				try{
					JSONArray jsonArray = jsonObj.getJSONArray("visa");
					visaList = jsonArray.toString();
				}
				catch(JSONException e){
					visaList =jsonObj.getString("visa");
				}
				visaList = visaList.replaceAll("\\[", "").replaceAll("\\]","");
				visaList = visaList.replaceAll("\"", "");
				candidate.setVisaType(visaList);
		  	 
			}else{
				candidate.setVisaType(null);
			}
			
			
			HashSet<CandidateEducation>	 candidateEducationList = candidateEducationService.getCandidateEducationDetails(jsonObj, orgLocations,candidate,user.getUserCode());
			candidate.setCandidateEducation(candidateEducationList);
			
			HashSet<CandidateEmployers> candidateEmployersList = candidateEmployersService.getCandidateEmployersDetails(jsonObj,orgLocations,candidate,user.getUserCode());
			candidate.setCandidateEmployers(candidateEmployersList);
			
			if(multiFile != null){
			HashSet<CandidateResumes> candidateResumesList = candidateResumesService.getCandidateResumesDetails(orgLocations,user.getUserCode(),candidate,multiFile);
			candidate.setCandidateResumes(candidateResumesList);
			}
			candidate.setUpdatedBy(user.getUserCode());
			candidate.setUpdatedDate(commonUtil.currentDate());
			candidate.setStatus(Constants.CANDIDATE_ACTIVE);
			candidate.setIsFavourite(0);
			Candidate savedCandidate = candidateDao.storeCandidate(candidate);
			return savedCandidate;
	}

	@Override
	public Integer getAllCandidatedsBasedOnCreatedByAndOrglocationCodeWhichAreCreatedToday(OrgLocations orgLocations,
			User user) throws Exception {
		
		   return candidateDao.getAllCandidatedsBasedOnCreatedByAndOrglocationCodeWhichAreCreatedToday(orgLocations, user);
	}
			
	@Override
	public List<Candidate> getCandidateReportsListByOrgLocationID(Long orgLocationId, String candidateType)
			throws Exception {
			List<Candidate> candidateList = candidateDao.getCandidateReportsListByOrgLocationID(orgLocationId,candidateType);
			for(Candidate can :candidateList){
				if(can.getUpdatedDate() != null){
				 can.setCandidateUpdateDateToString(LoginSessionController.DATEFORMAT.format(can.getUpdatedDate()));
				}
				User user  = userService.getUserByUserCode(can.getCreatedBy());
				if(!"".equalsIgnoreCase(user.getLastName()) && user.getLastName()!=null && !user.getLastName().isEmpty() ){
					
					StringBuilder sb =  new StringBuilder();
					can.setCreatedBy(sb.append(user.getFirstName()).append(" ").append(user.getLastName()).toString());
				}else{
					
					StringBuilder sb =  new StringBuilder();
					can.setCreatedBy(sb.append(user.getFirstName()).append(" ").toString());
				}
				
				 Integer requirementSubmissionCount = requirementSubmissionService.getRequirementHistoryCountByCandidateIdAndOrgLocationID(can.getId(), orgLocationId);
				 can.setCandidateRequiementCount(requirementSubmissionCount);
			}
			
		return candidateList;
	}

	@Override
	public List<Candidate> getCandidateReports(Long orgLocationId, String candidateType, String filterDate,
			Date startDateUTC, Date endDateUTC,String ownersList) throws Exception {
		List<Candidate> candidateReportsList = candidateDao.getCandidateReports(orgLocationId, candidateType, filterDate, startDateUTC, endDateUTC,ownersList);
		for(Candidate can : candidateReportsList){
			if(can.getUpdatedDate() != null){
				 can.setCandidateUpdateDateToString(LoginSessionController.DATEFORMAT.format(can.getUpdatedDate()));
				}
			 Integer requirementSubmissionCount = requirementSubmissionService.getRequirementHistoryCountByCandidateIdAndOrgLocationID(can.getId(), orgLocationId);
			 can.setCandidateRequiementCount(requirementSubmissionCount);
			}
		return candidateReportsList;
	}
	
	@Override
	public List<Candidate> getAllActiveAssociatesByOrglocationId(Long orgLocationId,String employeementType) throws Exception
	{
		return candidateDao.getAllActiveAssociatesByOrglocationId(orgLocationId,employeementType);
	}
	
	@Override
	public Candidate getCandidateById(Long candidateId) throws Exception
	{
		return candidateDao.getCandidateById(candidateId);
	}
	
	@Override
	public List<User> getAllCandidateOwnersByLocationIdAndCandidateType(Long orgLocationId, String candidateType)
			throws Exception {
		
		List<Candidate> candidatesList = candidateDao.getCandidateReportsListByOrgLocationID(orgLocationId, candidateType);
		List<Long> tempOwnersIds = new ArrayList<Long>();
		List<User> owners = new ArrayList<User>();
		
		if(null != candidatesList && candidatesList.size() >0){
			for(Candidate can  : candidatesList){
				
				User usr = userService.getUserByUserCode(can.getCreatedBy());
				tempOwnersIds.add(usr.getId());
			}
		}
		
		Set<Long> setWithUniqueValues = new HashSet<>(tempOwnersIds);
        for(Long ind :setWithUniqueValues){
        	
        	User usr = userService.getUserById(ind);
        	owners.add(usr);
        }
		return owners;
	}

	@Override
	public List<Candidate> getOwnersByCandidates(Long orgLocationId, String candidateType,  String ownersList) throws Exception {
		List<Candidate> ownerByCandidatesList = candidateDao.getOwnersByCandidates(orgLocationId, candidateType, ownersList);
		for(Candidate can : ownerByCandidatesList){
			 Integer requirementSubmissionCount = requirementSubmissionService.getRequirementHistoryCountByCandidateIdAndOrgLocationID(can.getId(), orgLocationId);
			 can.setCandidateRequiementCount(requirementSubmissionCount);
		}
		return ownerByCandidatesList;
	}

	@Override
	public List<Candidate> getHotCandidateListByTeamIdAndOrgLocationId(Long teamId, Long orgLocationId)
			throws Exception {
		return candidateDao.getHotCandidateListByTeamIdAndOrgLocationId(teamId, orgLocationId);
	}

	@Override
	public Long getCountOfAllCandidatesByCreatedUserAndOrglocationId(Long orgLocationId, User user, String timePeriod) throws Exception {
		return candidateDao.getCountOfAllCandidatesByCreatedUserAndOrglocationId(orgLocationId, user, timePeriod);
	}

	@Override
	public List<Candidate> getAllActiveCandidatesCountForCampaignByLocationAndStatus(Long orgLocationId) throws Exception {
		return candidateDao.getAllActiveCandidatesCountForCampaignByLocationAndStatus(orgLocationId);
	}
	
	@Override
	public Long getCountOfAllCandidatesByCreatedUserAndOrglocationId(Long orgLocationId, User user, Date startDateUTC, Date endDateUTC) throws Exception {
		return candidateDao.getCountOfAllCandidatesByCreatedUserAndOrglocationId(orgLocationId, user, startDateUTC,endDateUTC);
	}

	@Override
	public Candidate getCandidateByUUID(String candidateUUID) throws Exception {
		return candidateDao.getCandidateByUUID(candidateUUID);
	}

	@Override
	public Long getCandidatesCountByOrgLocationIDAndStatus(Long orgLocationId, String candidateStatus, String isAssociate) throws Exception {
		return candidateDao.getCandidatesCountByOrgLocationIDAndStatus(orgLocationId, candidateStatus, isAssociate);
	}

	@Override
	public List<Candidate> getAllActiveCandidatesByLocation(Long orgLocationId) throws Exception {
		return candidateDao.getAllActiveCandidatesByLocation(orgLocationId);
	}

	@Override
	public List<Candidate> getAllActiveAssociatesByLocation(Long orgLocationId) throws Exception
	{
		return candidateDao.getAllActiveAssociatesByLocation(orgLocationId);
	}

	@Override
	public List<Candidate> getAllActiveAssociatesByLocationAndAssoicatesList(Long orgLocationId,
			List<Long> associatesIds) throws Exception {
		return candidateDao.getAllActiveAssociatesByLocationAndAssoicatesList(orgLocationId, associatesIds);
	}

	@Override
	public List<Candidate> getAllActiveVendorAssociatesByLocationAndAssoicatesList(Long orgLocationId, Long spocId,
			List<Long> associatesIds) throws Exception {
		return candidateDao.getAllActiveVendorAssociatesByLocationAndAssoicatesList(orgLocationId, spocId, associatesIds);
	}

	@Override
	public List<Candidate> getAllActiveVendorAssociatesByLocation(Long orgLocationId, Long spocId) throws Exception {
		return candidateDao.getAllActiveVendorAssociatesByLocation(orgLocationId, spocId);
	}

	@Override
	public List<Candidate> getAllActiveVendorAssociatesByOrgLocationAndAssoicatesList(Long vendorOrgRelationId,
			List<Long> associatesIds) throws Exception {
		return candidateDao.getAllActiveVendorAssociatesByOrgLocationAndAssoicatesList(vendorOrgRelationId, associatesIds);
	}

	@Override
	public List<Candidate> getAllActiveVendorAssociatesByOrgLocation(Long orgLocationId) throws Exception {
		return candidateDao.getAllActiveVendorAssociatesByOrgLocation(orgLocationId);
	}

	@Override
	public Candidate getCandidateByConsultantUserIdOrgRelationId(Long consultantUserId, Long orgLocationId, Long orgRelationId) throws Exception
	{
		return candidateDao.getCandidateByConsultantUserIdOrgRelationId(consultantUserId,orgLocationId,orgRelationId);
	}

	@Override
	public Candidate getCandidateByConsultantUserId(Long consultantUserId, Long orgLocationId) throws Exception
	{
		return candidateDao.getCandidateByConsultantUserId(consultantUserId,orgLocationId);
	}

	@Override
	public Long getCandidatesCountByCandidateTypeOrgLocationIDAndUserId(Long orgLocationId, Long userId,
			String candidateType) throws Exception {
		return candidateDao.getCandidatesCountByCandidateTypeOrgLocationIDAndUserId(orgLocationId, userId, candidateType);
	}

	@Override
	public List<Candidate> getCandidatesListByOrgLocationIDAndUserId(Long orgLocationId, Long userId) throws Exception {
		return candidateDao.getCandidatesListByOrgLocationIDAndUserId(orgLocationId, userId);
	}

	@Override
	public List<Candidate> getCandidatesListByOrgLocationIdAndOrgRelationId(Long orgLocationId, Long orgRelationId) throws Exception
	{
		List<Candidate> candidateList = candidateDao.getCandidatesListByOrgLocationIdAndOrgRelationId(orgLocationId, orgRelationId);
		for (Candidate candidate : candidateList) {
			StringBuilder sb = new StringBuilder();
			candidate.setCandidateName(sb.append(candidate.getFirstName()).append(" ").append(candidate.getLastName()).toString());
			candidate.setEmail(candidate.getEmail());
			candidate.setCandidateUpdateDateToString(LoginSessionController.DATEFORMAT.format(candidate.getUpdatedDate()));
			candidate.setPrimarySkill(candidate.getPrimarySkill());
			StringBuilder stringBuilder = new StringBuilder();
			candidate.setVendorContactName(stringBuilder.append(candidate.getSpocId().getVendorUser().getFirstName()).append(" ").append(candidate.getSpocId().getVendorUser().getLastName()).toString());
			CandidateResumes candidateResumes = candidateResumesService.getCandidateResumesListByCandidateId(candidate.getId());
			if(candidateResumes != null){
			candidate.setCandidateResumeName(candidateResumes.getFileName());
			candidate.setCandidateResumeFileType(candidateResumes.getFileContentType());
			}if(candidate.getConsultantUser() != null){
				Integer assignedProjectsCount = projectsService.getUserAssignedProjectsCount(candidate.getConsultantUser().getId(), orgLocationId);
				candidate.setAssignedProjectsCount(assignedProjectsCount);
			}
		}
		return candidateList;
	}
	
	@Override
	public Candidate storeCandidateForUsers(JSONObject jsonObj,User user,OrgLocations orgLocations,MasterCandidate masterCandidate) throws Exception
	{
		Candidate candidate = candidateDao.getCandidateByConsultantUserId(user.getId(), orgLocations.getId());
		if(null == candidate){
			candidate = new Candidate();
			candidate.setConsultantUser(user);
			candidate.setMasterCandidateId(masterCandidate);
			candidate.setOrgLocationId(orgLocations);
			candidate.setCandidateType(Constants.CANDIDATE);
			candidate.setFirstName(user.getFirstName());
			candidate.setLastName(user.getLastName());
			candidate.setCountry(orgLocations.getCountry());
			candidate.setIsAssociate(Constants.YES);
			candidate.setEmail(user.getEmail());
			candidate.setStatus(Constants.RECORD_ACTIVE);
			candidate.setCreatedBy(user.getUserCode());
			
			if(jsonObj.has("phoneNumber") && !isNullOrEmpty(jsonObj.getString("phoneNumber"))){
				candidate.setMobilePhone(jsonObj.getString("phoneNumber"));
			}
			
			if(jsonObj.has("designationType") && !isNullOrEmpty(jsonObj.getString("designationType"))){
				candidate.setDesignationType(jsonObj.getString("designationType"));
			}
			
			if(jsonObj.has("designationOtherType") && !isNullOrEmpty(jsonObj.getString("designationOtherType"))){
				candidate.setDesignationOtherType(jsonObj.getString("designationOtherType"));
			}
			
			candidate.setCandidateUUID(SourceLeadRandomCodeHelper.generateRandomUUID());
			candidate.setCreatedDate(commonUtil.currentDate());
		}else{
			candidate.setConsultantUser(user);
			candidate.setMasterCandidateId(masterCandidate);
		}
		
		candidateDao.storeCandidate(candidate);
		saveOrUpdateCandidateElkDocument(candidate);
		return candidate;
	}

	@Override
	public int getCandidatePercentage(Candidate can, OrgLocations loginOrgLocation) throws Exception
	{

		 int percentage = 0;
		 Integer status = Constants.CANDIDATE_ACTIVE;
			if(can != null){
				//Basic Details - 30 %
				if(can.getFirstName()!=null && can.getLocation()!=null && can.getEmail()!=null && can.getDesignationType()!=null){
					percentage = 30;
				}
				//Resume  Details - 10 %
				List<CandidateResumes> candidateResumesList = candidateResumesService.getCandidateResumesListByCandidateIdAndStatus(can.getId(), status);
				if(!candidateResumesList.isEmpty()){
					percentage = percentage +10;
				}
				//Education Details - 10 %
				 List<CandidateEducation>  candidatesEducationList = candidateEducationService.getCandidateEducationListByCandidateIdAndOrgLocationID(can.getId(), loginOrgLocation.getId());
				 if(!candidatesEducationList.isEmpty()){
						percentage = percentage +10;
				 }
				//Employment  Details - 10 %
				 List<CandidateEmployers>   candidatesEmployersList = candidateEmployersService.getCandidatesEmployersListByCandidateIdAndOrgLocationID(can.getId(), loginOrgLocation.getId());
				 if(!candidatesEmployersList.isEmpty()){
						percentage = percentage +10;
				 }
				 
				 //Skills Details - 10 %
				 if(can.getSkillsName()!=null && !"".equalsIgnoreCase(can.getSkillsName()) ){
						percentage = percentage +10;
				 }
				 
				//PrimarySkill Details - 5 %
				 if(can.getPrimarySkill()!=null && !"".equalsIgnoreCase(can.getPrimarySkill())){
						percentage = percentage + 5;
				 }
				 
				 
				 int countOtherDetails = 0;
				 if(can.getSummary() != null && !"".equalsIgnoreCase(can.getSummary()) ){
					 countOtherDetails++;
				 }
				 if(can.getVisaType()!=null && !"".equalsIgnoreCase(can.getVisaType())){
					 countOtherDetails++;
				 }
				 if(can.getExpectedRate()!=null && !"".equalsIgnoreCase(can.getExpectedRate())){
					 countOtherDetails++;
				 }
				 if((can.getExperienceYears()!= null && !"".equalsIgnoreCase(can.getExperienceYears()) && Long.parseLong(can.getExperienceYears())>0) || (can.getExperienceMonths()!= null && !"".equalsIgnoreCase(can.getExperienceMonths())&& Long.parseLong(can.getExperienceMonths())>0)){
					 countOtherDetails++;
				 }
				//any two OtherDetails - 10 %
				 if(countOtherDetails>=2){
					 percentage = percentage + 10; 
				 }
				//if only one OtherDetails - 5 %
				 else if(countOtherDetails==1){
					 percentage = percentage + 5; 
				 }
				 
				//photo Details - 5 %
				 if(can.getDocFile()!=null){
					 percentage = percentage + 5; 
				 }

				//document Details - 10 %
				 List<CandidateDocuments>   candidateDocumentsList = candidateDocumentsService.getCandidateDocumentsListByCandidateIdAndOrgLocationID(can.getId(), loginOrgLocation.getId());
				 if(!candidateDocumentsList.isEmpty()){
						percentage = percentage +10;
				 }
			}
			return percentage;
	}


	@Override
	public List<Candidate> getRequestedCandidatesList(Long orgLocationsId, List<Long> roleIds,
			String resourceType,List<Long> associatesIds,Long projectId) throws Exception {
		return candidateDao.getRequestedCandidatesList(orgLocationsId, roleIds, resourceType,associatesIds,projectId);
	}


}