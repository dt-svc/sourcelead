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

import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.CandidateDao;
import com.bis.sourcelead.dao.CandidateTimelineActivityDao;
import com.bis.sourcelead.dto.AddUserDTO;
import com.bis.sourcelead.dto.CandidateDTO;
import com.bis.sourcelead.dto.FileInfo;
import com.bis.sourcelead.elk.index.CandidateIndex;
import com.bis.sourcelead.elk.index.helper.CandidateIndexHelper;
import com.bis.sourcelead.elk.setup.SourceleadELKBaseIndexer;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.CandidateDocuments;
import com.bis.sourcelead.entity.CandidateEducation;
import com.bis.sourcelead.entity.CandidateEmployers;
import com.bis.sourcelead.entity.CandidateResumes;
import com.bis.sourcelead.entity.EmailAttachment;
import com.bis.sourcelead.entity.EmailMessage;
import com.bis.sourcelead.entity.EmailScenario;
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
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.OrgRelationsService;
import com.bis.sourcelead.service.OrgSpocService;
import com.bis.sourcelead.service.ProjectsService;
import com.bis.sourcelead.service.RequirementSubmissionService;
import com.bis.sourcelead.service.UserRoleService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.CandidateActivityCommonUtil;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.EmailScenerioUtil;
import com.bis.sourcelead.util.FileUploadUtil;
import com.bis.sourcelead.util.PdfUtil;
import com.bis.sourcelead.util.SourceLeadRandomCodeHelper;
import com.bis.sourcelead.util.UniversalSearchHelper;
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
	private OrgLocationService orgLocationService;
	
	@Autowired
	CandidateDocumentsService candidateDocumentsService;
	
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
    
    
	/*public String getCandidateId(OrgLocations orgLocations) {
    	String companyShortCode = null;
    	Long number = 1L;
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
		
        final DecimalFormat decimalFormat = new DecimalFormat("0");                
        String ConvetredCandidatesId = decimalFormat.format(Long.parseLong(candidateId));
        StringBuffer sb = new StringBuffer();
        if(orgLocations.getGeneratedUserId() != null && !"".equalsIgnoreCase(orgLocations.getGeneratedUserId())){
        	 companyShortCode = orgLocations.getGeneratedUserId();
        }
		return sb.append(companyShortCode).append(Constants.CANDIDATE_ENTITY).append(ConvetredCandidatesId).toString();
		
	}*/
    
    @Override
   	public String getCandidateId(OrgLocations orgLocations) {
       	String companyShortCode = null;
       	Long number = 1L;
   		String candidateId = null;
   		Integer generatedIdcount = 0;
   		StringBuilder sbuilder = new StringBuilder();
   		sbuilder.append(orgLocations.getGeneratedUserId()).append(Constants.CANDIDATE_ENTITY);
   		String generatedUsreId = sbuilder.toString();
   		for(int i=0;i< generatedUsreId.length();i++){
   			char ch = generatedUsreId.charAt(i);
   			if(Character.isAlphabetic(ch)){
   				generatedIdcount++;
   			}
   		}
   		// in dao to count from next character
   		generatedIdcount = generatedIdcount+1;
   		 Long checkNumber = candidateDao.getCandidateGeneratedIdByOrgLocationId(orgLocations.getId(),generatedIdcount);
   		if(checkNumber == null){
   			candidateId = number.toString();
   		}else{
   		Long canId = checkNumber+1;
   		candidateId = canId.toString();
   		}
   		final DecimalFormat decimalFormat = new DecimalFormat("0");                
           String convetredCandidateId = decimalFormat.format(Long.parseLong(candidateId));
           StringBuffer sb = new StringBuffer();
           if(orgLocations.getGeneratedUserId() != null && !"".equalsIgnoreCase(orgLocations.getGeneratedUserId())){
           	 companyShortCode = orgLocations.getGeneratedUserId();
           }
   		return sb.append(companyShortCode).append(Constants.CANDIDATE_ENTITY).append(convetredCandidateId).toString();
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
	public Candidate storeCandidate(CandidateDTO candidateDto,OrgLocations orgLocations,User user) throws Exception {
		
		
		StringBuffer activitySummary = new StringBuffer();
        Candidate candidate = null;
		if(StringUtils.isNotBlank(candidateDto.getCandidateId())){
			candidate = candidateDao.getCandidateByCandidateId(Long.parseLong(candidateDto.getCandidateId()));
		}else{	
			candidate = new Candidate();
			candidate.setCreatedDate(commonUtil.currentDate());
			candidate.setCreatedBy(user.getUserCode());
			
			if(StringUtils.isNotBlank(candidateDto.getConsultantUser())){
				User consultantUser = new User();
				consultantUser.setId(Long.parseLong(candidateDto.getConsultantUser()));
				candidate.setConsultantUser(consultantUser);
				candidate.setCandidateType("Consultant");
			}else{
				candidate.setCandidateType("Candidate");
			}
			candidate.setCandidateUUID(SourceLeadRandomCodeHelper.generateRandomUUID());
			candidate.setIsFavourite(0);
			candidate.setIsFavouriteAssociate(0);
			
			MasterCandidate masterCandidate = null;
			masterCandidate = masterCandidateService.getMasterCandidateByInteligenceCandidateDTO(candidateDto);
			if(null == masterCandidate){
				masterCandidate = masterCandidateService.storeMasterCandidateByCandidateDTO(candidateDto, user);
			}
			candidate.setMasterCandidateId(masterCandidate);
		}
        
		if(orgLocations != null){
		candidate.setOrgLocationId(orgLocations);
		}
		if(candidateDto.getFirstName() != null){
			if(candidate.getFirstName() != null){
				activitySummary.append("First Name").append(" : ").append(Constants.OLD_VALUE_TAG_INITIATION).append(candidate.getFirstName()).append(Constants.OLD_VALUE_TAG_END).append(" ").append(Constants.NEW_VALUE_TAG_INITIATION).append(candidateDto.getFirstName()).append(Constants.NEW_VALUE_TAG_END).append(" ");
			}
		candidate.setFirstName(candidateDto.getFirstName());
		}
		
		if(candidateDto.getLastName() != null){
			if(candidate.getLastName() != null ){
				activitySummary.append("Last Name").append(" : ").append(Constants.OLD_VALUE_TAG_INITIATION).append(candidate.getLastName()).append(Constants.OLD_VALUE_TAG_END).append(" ").append(Constants.NEW_VALUE_TAG_INITIATION).append(candidateDto.getLastName()).append(Constants.NEW_VALUE_TAG_END).append(" ");
			}
		candidate.setLastName(candidateDto.getLastName());
		}
		
		if(candidateDto.getCountry() != null){
			if(candidate.getCountry() != null){
				activitySummary.append("Country").append(" : ").append(Constants.OLD_VALUE_TAG_INITIATION).append(candidate.getCountry()).append(Constants.OLD_VALUE_TAG_END).append(" ").append(Constants.NEW_VALUE_TAG_INITIATION).append(candidateDto.getCountry()).append(Constants.NEW_VALUE_TAG_END).append(" ");
			}
		candidate.setCountry(candidateDto.getCountry());
		}
		if(candidateDto.getState() != null){
			if(candidate.getState() != null ){
				activitySummary.append("State").append(" : ").append(Constants.OLD_VALUE_TAG_INITIATION).append(candidate.getState()).append(Constants.OLD_VALUE_TAG_END).append(" ").append(Constants.NEW_VALUE_TAG_INITIATION).append(candidateDto.getState()).append(Constants.NEW_VALUE_TAG_END).append(" ");
			}
		candidate.setState(candidateDto.getState());
		}
		if(candidateDto.getCity() != null){
			if(candidate.getCity() != null ){
				activitySummary.append("City").append(" : ").append(Constants.OLD_VALUE_TAG_INITIATION).append(candidate.getCity()).append(Constants.OLD_VALUE_TAG_END).append(" ").append(Constants.NEW_VALUE_TAG_INITIATION).append(candidateDto.getCity()).append(Constants.NEW_VALUE_TAG_END).append(" ");
			}
		candidate.setCity(candidateDto.getCity());
		}
		if(candidateDto.getLocation() != null){
			candidate.setLocation(candidateDto.getLocation());
		}
		if(candidateDto.getZipcode()!= null){
			if(candidate.getZipCode() != null){
				activitySummary.append("Zip Code").append(" : ").append(Constants.OLD_VALUE_TAG_INITIATION).append(candidate.getZipCode()).append(Constants.OLD_VALUE_TAG_END).append(" ").append(Constants.NEW_VALUE_TAG_INITIATION).append(candidateDto.getZipcode()).append(Constants.NEW_VALUE_TAG_END).append(" ");
			}
			candidate.setZipCode(candidateDto.getZipcode());
		}
		
		if(candidateDto.getLatitude() != null){
			candidate.setLatitude(candidateDto.getLatitude());
		}
		
		if(candidateDto.getLongitude() != null){
			candidate.setLongitude(candidateDto.getLongitude());
		}
		
		if(candidateDto.getMobilePhone() != null){
			if(candidate.getMobilePhone() != null ){
				activitySummary.append("Mobile Phone").append(" : ").append(Constants.OLD_VALUE_TAG_INITIATION).append(candidate.getMobilePhone()).append(Constants.OLD_VALUE_TAG_END).append(" ").append(Constants.NEW_VALUE_TAG_INITIATION).append(candidateDto.getMobilePhone()).append(Constants.NEW_VALUE_TAG_END).append(" ");
			}
		candidate.setMobilePhone(candidateDto.getMobilePhone());
		}
		if(candidateDto.getAlternateMobilePhone() != null){
			if(candidate.getAlternateMobilePhone() != null){
				activitySummary.append("Alternate Mobile Phone").append(" : ").append(Constants.OLD_VALUE_TAG_INITIATION).append(candidate.getAlternateMobilePhone()).append(Constants.OLD_VALUE_TAG_END).append(" ").append(Constants.NEW_VALUE_TAG_INITIATION).append(candidateDto.getAlternateMobilePhone()).append(Constants.NEW_VALUE_TAG_END).append(" ");
			}
			candidate.setAlternateMobilePhone(candidateDto.getAlternateMobilePhone());
		}
		if(candidateDto.getEmail() != null){
			if(candidate.getEmail() != null){
				activitySummary.append("Email").append(" : ").append(Constants.OLD_VALUE_TAG_INITIATION).append(candidate.getEmail()).append(Constants.OLD_VALUE_TAG_END).append(" ").append(Constants.NEW_VALUE_TAG_INITIATION).append(candidateDto.getEmail()).append(Constants.NEW_VALUE_TAG_END).append(" ");
			}
		candidate.setEmail(candidateDto.getEmail().trim());
		}
		if(candidateDto.getCurrentRate() !=null){
			if(candidate.getCurrentRate() != null ){
				activitySummary.append("Current Rate").append(" : ").append(Constants.OLD_VALUE_TAG_INITIATION).append(candidate.getCurrentRate()).append(Constants.OLD_VALUE_TAG_END).append(" ").append(Constants.NEW_VALUE_TAG_INITIATION).append(candidateDto.getCurrentRate()).append(Constants.NEW_VALUE_TAG_END).append(" ");
			}
		candidate.setCurrentRate(candidateDto.getCurrentRate());
		}
		
		if(candidateDto.getCurrentRateType() != null){
			if(candidate.getCurrentRateType() != null){
				activitySummary.append("Current Rate Type").append(" : ").append(Constants.OLD_VALUE_TAG_INITIATION).append(candidate.getCurrentRateType()).append(Constants.OLD_VALUE_TAG_END).append(" ").append(Constants.NEW_VALUE_TAG_INITIATION).append(candidateDto.getCurrentRateType()).append(Constants.NEW_VALUE_TAG_END).append(" ");
			}
			candidate.setCurrentRateType(candidateDto.getCurrentRateType());
			}
		
		if(candidateDto.getCurrentRateCurrency() !=null){
			if(candidate.getCurrentRateCurrency() != null){
				activitySummary.append("Current Rate Currency").append(" : ").append(Constants.OLD_VALUE_TAG_INITIATION).append(candidate.getCurrentRateCurrency()).append(Constants.OLD_VALUE_TAG_END).append(" ").append(Constants.NEW_VALUE_TAG_INITIATION).append(candidateDto.getCurrentRateCurrency()).append(Constants.NEW_VALUE_TAG_END).append(" ");
			}
		candidate.setCurrentRateCurrency(candidateDto.getCurrentRateCurrency());
		}
		if(candidateDto.getDesignation() !=null){
			if(candidate.getDesignation() != null){
				activitySummary.append("Designation").append(" : ").append(Constants.OLD_VALUE_TAG_INITIATION).append(candidate.getDesignation()).append(Constants.OLD_VALUE_TAG_END).append(" ").append(Constants.NEW_VALUE_TAG_INITIATION).append(candidateDto.getDesignation()).append(Constants.NEW_VALUE_TAG_END).append(" ");
			}
		candidate.setDesignation(candidateDto.getDesignation());
		}
		
		if(candidateDto.getDesignationOtherType() !=null){
			if(candidate.getDesignationOtherType() != null){
				activitySummary.append("Designation Other Type").append(" : ").append(Constants.OLD_VALUE_TAG_INITIATION).append(candidate.getDesignationOtherType()).append(Constants.OLD_VALUE_TAG_END).append(" ").append(Constants.NEW_VALUE_TAG_INITIATION).append(candidateDto.getDesignationOtherType()).append(Constants.NEW_VALUE_TAG_END).append(" ");
			}
			candidate.setDesignationOtherType(candidateDto.getDesignationOtherType());
		}else{
			candidate.setDesignationOtherType(null);
		}
		
		if(candidateDto.getExpectedRate() !=null ){
			if(candidate.getExpectedRate() != null){
				activitySummary.append("Expected Rate").append(" : ").append(Constants.OLD_VALUE_TAG_INITIATION).append(candidate.getExpectedRate()).append(Constants.OLD_VALUE_TAG_END).append(" ").append(Constants.NEW_VALUE_TAG_INITIATION).append(candidateDto.getExpectedRate()).append(Constants.NEW_VALUE_TAG_END).append(" ");
			}
		candidate.setExpectedRate(candidateDto.getExpectedRate());
		}
		
		if(candidateDto.getRateType() != null){
			if(candidate.getRateType() != null){
				activitySummary.append("Rate Type").append(" : ").append(Constants.OLD_VALUE_TAG_INITIATION).append(candidate.getRateType()).append(Constants.OLD_VALUE_TAG_END).append(" ").append(Constants.NEW_VALUE_TAG_INITIATION).append(candidateDto.getRateType()).append(Constants.NEW_VALUE_TAG_END).append(" ");
			}
			candidate.setRateType(candidateDto.getRateType());
			}
		
		if(candidateDto.getExpectedRateCurrencey() !=null ){
			if(candidate.getExpectedRateCurrencey() != null ){
				activitySummary.append("Expected Rate Currencey").append(" : ").append(Constants.OLD_VALUE_TAG_INITIATION).append(candidate.getExpectedRateCurrencey()).append(Constants.OLD_VALUE_TAG_END).append(" ").append(Constants.NEW_VALUE_TAG_INITIATION).append(candidateDto.getExpectedRateCurrencey()).append(Constants.NEW_VALUE_TAG_END).append(" ");
			}
			candidate.setExpectedRateCurrencey(candidateDto.getExpectedRateCurrencey());
		}
		
		if(candidateDto.getSummary() !=null){
			if(candidate.getSummary() != null){
				activitySummary.append("Summary").append(" : ").append(Constants.OLD_VALUE_TAG_INITIATION).append(candidate.getSummary()).append(Constants.OLD_VALUE_TAG_END).append(" ").append(Constants.NEW_VALUE_TAG_INITIATION).append(candidateDto.getSummary()).append(Constants.NEW_VALUE_TAG_END).append(" ");
			}
		candidate.setSummary(candidateDto.getSummary());
		}
		
			if(candidate.getDesignationType() != null ){
				activitySummary.append("Designation Type").append(" : ").append(Constants.OLD_VALUE_TAG_INITIATION).append(candidate.getDesignationType()).append(Constants.OLD_VALUE_TAG_END).append(" ").append(Constants.NEW_VALUE_TAG_INITIATION).append(candidateDto.getDesignationType()).append(Constants.NEW_VALUE_TAG_END).append(" ");
			}
		candidate.setDesignationType(candidateDto.getDesignationType());
		
			if(candidate.getExperienceYears() != null ){
				activitySummary.append("Experience Years").append(" : ").append(Constants.OLD_VALUE_TAG_INITIATION).append(candidate.getExperienceYears()).append(Constants.OLD_VALUE_TAG_END).append(" ").append(Constants.NEW_VALUE_TAG_INITIATION).append(candidateDto.getExperienceYears()).append(Constants.NEW_VALUE_TAG_END).append(" ");
			}
		candidate.setExperienceYears(candidateDto.getExperienceYears());
		
		
		if(StringUtils.isNotBlank(candidateDto.getExperienceMonths())){
			if(candidate.getExperienceMonths() != null){
				activitySummary.append("Experience Months").append(" : ").append(Constants.OLD_VALUE_TAG_INITIATION).append(candidate.getExperienceMonths()).append(Constants.OLD_VALUE_TAG_END).append(" ").append(Constants.NEW_VALUE_TAG_INITIATION).append(candidateDto.getExperienceMonths()).append(Constants.NEW_VALUE_TAG_END).append(" ");
			}
			candidate.setExperienceMonths(candidateDto.getExperienceMonths());	
		}else{
			candidate.setExperienceMonths(null);	
		}
		
			if(candidate.getNoticePeriod() != null){
				activitySummary.append("Notice Period").append(" : ").append(Constants.OLD_VALUE_TAG_INITIATION).append(candidate.getNoticePeriod()).append(Constants.OLD_VALUE_TAG_END).append(" ").append(Constants.NEW_VALUE_TAG_INITIATION).append(candidateDto.getNoticePeriod()).append(Constants.NEW_VALUE_TAG_END).append(" ");
			}
			candidate.setNoticePeriod(candidateDto.getNoticePeriod());	
		
		if(StringUtils.isNotBlank(candidateDto.getAddress())){
			if(candidate.getAddress() != null ){
				activitySummary.append("Address").append(" : ").append(Constants.OLD_VALUE_TAG_INITIATION).append(candidate.getAddress()).append(Constants.OLD_VALUE_TAG_END).append(" ").append(Constants.NEW_VALUE_TAG_INITIATION).append(candidateDto.getAddress()).append(Constants.NEW_VALUE_TAG_END).append(" ");
			}
			candidate.setAddress(candidateDto.getAddress());
		}
		
		UserRole userRole = userRoleService.getUserDetailsByUserIdAndLocationIdAndUserType(user.getId(),orgLocations.getId(),"Recruiter");
		if(null != userRole && userRole.getRoles().getRoleName().equalsIgnoreCase("ROLE_CROWDSOURCING_RECRUITER")){
			candidate.setRecruiterUserId(userRole.getUser());	
		}
		
		if(StringUtils.isNotBlank(candidateDto.getCandidateSkills())){
			
			if(candidate.getSkillsName() != null){
				activitySummary.append("Skills").append(" : ").append(Constants.OLD_VALUE_TAG_INITIATION).append(candidate.getSkillsName()).append(Constants.OLD_VALUE_TAG_END).append(" ").append(Constants.NEW_VALUE_TAG_INITIATION).append(candidateDto.getCandidateSkills()).append(Constants.NEW_VALUE_TAG_END).append(" ");
			}
			candidate.setSkillsName(candidateDto.getCandidateSkills());
		}
		
		if(StringUtils.isNotBlank(candidateDto.getVisa())){
			
			if(candidate.getVisaType() != null){
				activitySummary.append("Immigration Status").append(" : ").append(Constants.OLD_VALUE_TAG_INITIATION).append(candidate.getVisaType()).append(Constants.OLD_VALUE_TAG_END).append(" ").append(Constants.NEW_VALUE_TAG_INITIATION).append(candidateDto.getVisa()).append(Constants.NEW_VALUE_TAG_END).append(" ");
			}
			candidate.setVisaType(candidateDto.getVisa());
	  	 
		}
		
			if(candidate.getPrimarySkill() != null){
				activitySummary.append("Primary Skills").append(" : ").append(Constants.OLD_VALUE_TAG_INITIATION).append(candidate.getPrimarySkill()).append(Constants.OLD_VALUE_TAG_END).append(" ").append(Constants.NEW_VALUE_TAG_INITIATION).append(candidateDto.getPrimarySkill()).append(Constants.NEW_VALUE_TAG_END).append(" ");
			}
			candidate.setPrimarySkill(candidateDto.getPrimarySkill());

		if(StringUtils.isNotBlank(candidateDto.getVendors())){
			OrgRelations orgRelation = orgRelationsService.getOrRelationsById(Long.parseLong(candidateDto.getVendors()));
			candidate.setOrgRelationId(orgRelation);
			Candidate vendorCandidate =  commonUtil.getVendorCandidateForCandidateCreation(candidateDto, orgRelation, orgLocations, user);
			candidate.setParentCandidateId(vendorCandidate);
		}else{
			candidate.setOrgRelationId(null);
			candidate.setParentCandidateId(null);
		}
		
		if(StringUtils.isNotBlank(candidateDto.getVendorcontact())){
			OrgSpoc orgspoc = orgSpocService.getOrgSpocById(Long.parseLong(candidateDto.getVendorcontact()));
			candidate.setSpocId(orgspoc);
		}else{
			candidate.setSpocId(null);
		}
		
		HashSet<CandidateEducation>	 candidateEducationList = candidateEducationService.getCandidateEducationDetails(candidateDto, orgLocations,candidate,user.getUserCode());
		candidate.setCandidateEducation(candidateEducationList);
		
		HashSet<CandidateEmployers> candidateEmployersList = candidateEmployersService.getCandidateEmployersDetails(candidateDto,orgLocations,candidate,user.getUserCode());
		candidate.setCandidateEmployers(candidateEmployersList);
		
		if(candidateDto.getFileInfo()!= null && !candidateDto.getFileInfo().isEmpty()){
			FileInfo fileInfo = candidateDto.getFileInfo().get(0);
			if(fileInfo != null){
			HashSet<CandidateResumes> candidateResumesList = candidateResumesService.getCandidateResumesDetails(orgLocations,user.getUserCode(),candidate, fileInfo);
			candidate.setCandidateResumes(candidateResumesList);
			FileUploadUtil.moveFile(candidateDto.getStaticPath(), fileInfo, "resumes");
				}
			}
		
		if(StringUtils.isNotBlank(candidateDto.getIsAssociate()) && Constants.YES.equalsIgnoreCase(candidateDto.getIsAssociate())){
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
		candidate.setGeneratedId(getCandidateId(orgLocations));

		Candidate savedCandidate = candidateDao.storeCandidate(candidate);
		candidateActivityCommonUtil.storeCandidateHistory(savedCandidate, user);
		
		/*
		 * this method is for track candidate activity
		 */
		if(StringUtils.isNotBlank(candidateDto.getCandidateId())){
			candidateActivityCommonUtil.storeCandidateActivity(user, orgLocations, savedCandidate, "", Constants.CANDIDATE_EDITTED, activitySummary.toString(), "CANDIDATE");
		}else{
			candidateActivityCommonUtil.storeCandidateActivity(user, orgLocations, savedCandidate, "", Constants.CANDIDATE_ADDED_MANUALLY, "", "CANDIDATE");
		}
		
		
		/*
		 * this method is for inviting candidate to sourcelead
		 */
		 if(StringUtils.isNotBlank(candidateDto.getInviteEmail()) && Constants.YES.equalsIgnoreCase(candidateDto.getInviteEmail())){
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
		OrgLocations orgLocations = orgLocationService.getOrgLocations(orgLocationId);
		//SimpleDateFormat simpleDateFormat = orgLocations.getLocFullFormDateFormatter();
		for(Candidate can :candidateList){
			if(can.getUpdatedDate() != null){
			 can.setCandidateUpdateDateToString(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),can.getUpdatedDate()));
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
	public Candidate storeProfessionalParseCandidate(CandidateDTO candidateDTO, OrgLocations orgLocations, User user,
			 Long candidateId) throws Exception {
		
            Candidate  candidate = candidateDao.getCandidateByCandidateId(candidateId);
			if(orgLocations != null){
			candidate.setOrgLocationId(orgLocations);
			}
			candidate.setFirstName(candidate.getFirstName());
			if(null != candidate.getLastName() && !"".equalsIgnoreCase(candidate.getLastName())){
				candidate.setLastName(candidate.getLastName());	
			}
//			candidate.setCountry(candidate.getCountry());
			if(StringUtils.isNotBlank(candidateDTO.getCountry())){
				candidate.setCountry(candidateDTO.getCountry());
			}else{
				candidate.setCountry(null);
			}
			
			if(StringUtils.isNotBlank(candidateDTO.getState())){
			candidate.setState(candidateDTO.getState());
			}else{
				candidate.setState(null);
			}
			if(StringUtils.isNotBlank(candidateDTO.getCity())){
			candidate.setCity(candidateDTO.getCity());
			}else{
				candidate.setCity(null);
			}
			if(StringUtils.isNotBlank(candidateDTO.getLocation())){
				candidate.setLocation(candidateDTO.getLocation());
			}else{
				candidate.setLocation(null);
			}
			if(StringUtils.isNotBlank(candidateDTO.getZipcode())){
				candidate.setZipCode(candidateDTO.getZipcode());
			}else{
				candidate.setZipCode(null);
			}			
			if(StringUtils.isNotBlank(candidateDTO.getMobilePhone())){
			candidate.setMobilePhone(candidateDTO.getMobilePhone());
			}else{
				candidate.setMobilePhone(null);
			}
			if(StringUtils.isNotBlank(candidateDTO.getAlternateMobilePhone())){
				candidate.setAlternateMobilePhone(candidateDTO.getAlternateMobilePhone());
			}else{
				candidate.setAlternateMobilePhone(null);
			}			
			if(null != candidate.getEmail()){
				candidate.setEmail(candidate.getEmail());	
			}else{
				candidate.setEmail(null);
			}
			if(StringUtils.isNotBlank(candidateDTO.getCurrentRate())){
				candidate.setCurrentRate(candidateDTO.getCurrentRate());
			}else{
				candidate.setCurrentRate(null);
			}
			if(StringUtils.isNotBlank(candidateDTO.getCurrentRateType())){
				candidate.setCurrentRateType(candidateDTO.getCurrentRateType());
			}else{
				candidate.setCurrentRateType(null);
			}
			if(StringUtils.isNotBlank(candidateDTO.getCurrentRateCurrency())){
				candidate.setCurrentRateCurrency(candidateDTO.getCurrentRateCurrency());
			}else{
				candidate.setCurrentRateCurrency(null);
			}
			if(StringUtils.isNotBlank(candidateDTO.getDesignation())){
				candidate.setDesignation(candidateDTO.getDesignation());
			}else{
				candidate.setDesignation(null);
			}
			
			if(StringUtils.isNotBlank(candidateDTO.getDesignationOtherType())){
				candidate.setDesignationOtherType(candidateDTO.getDesignationOtherType());
			}else{
				candidate.setDesignationOtherType(null);
			}
			
			if(StringUtils.isNotBlank(candidateDTO.getExpectedRate())){
			candidate.setExpectedRate(candidateDTO.getExpectedRate());
			}else{
				candidate.setExpectedRate(null);
			}
			if(StringUtils.isNotBlank(candidateDTO.getRateType())){
				candidate.setRateType(candidateDTO.getRateType());
			}else{
				candidate.setRateType(null);
			}
			if(StringUtils.isNotBlank(candidateDTO.getExpectedRateCurrencey())){
				candidate.setExpectedRateCurrencey(candidateDTO.getExpectedRateCurrencey());
			}else{
				candidate.setExpectedRateCurrencey(null);
			}
			if(StringUtils.isNotBlank(candidateDTO.getSummary())){
			candidate.setSummary(candidateDTO.getSummary());
			}else{
				candidate.setSummary(null);
			}
			if(StringUtils.isNotBlank(candidateDTO.getDesignationType())){
			candidate.setDesignationType(candidateDTO.getDesignationType());
			}else{
				candidate.setDesignationType(null);
			}
			if(StringUtils.isNotBlank(candidateDTO.getExperienceYears())){
			candidate.setExperienceYears(candidateDTO.getExperienceYears());
			}else{
				candidate.setExperienceYears(null);
			}
			if(StringUtils.isNotBlank(candidateDTO.getExperienceMonths())){
				candidate.setExperienceMonths(candidateDTO.getExperienceMonths());	
			}else{
				candidate.setExperienceMonths(null);
			}
			if(StringUtils.isNotBlank(candidateDTO.getNoticePeriod())){
				candidate.setNoticePeriod(candidateDTO.getNoticePeriod());	
			}else{
				candidate.setNoticePeriod(null);
			}
			if(StringUtils.isNotBlank(candidateDTO.getAddress())){
				candidate.setAddress(candidateDTO.getAddress());
			}else{
				candidate.setAddress(null);
			}
			UserRole userRole = userRoleService.getLoginUserPrimaryRole(user.getId());
			if(null != userRole && userRole.getRoles().getRoleName().equalsIgnoreCase("ROLE_CROWDSOURCING_RECRUITER")){
				candidate.setRecruiterUserId(userRole.getUser());	
			}
			
			if(StringUtils.isNotBlank(candidateDTO.getCandidateSkills())){
				/*String candidateSkills = null;
				try{
		    		   JSONArray jsonArray = jsonObj.getJSONArray("candidateSkills");
		    		   candidateSkills = jsonArray.toString();
		    	  }
		    	  catch(JSONException e){
		    		  candidateSkills= jsonObj.getString("candidateSkills");
		    	  }
				candidateSkills = candidateSkills.replaceAll("\"", "").replaceAll("\\[", "").replaceAll("\\]","");*/
				
				candidate.setSkillsName(candidateDTO.getCandidateSkills().replaceAll("\"", "").replaceAll("\\[", "").replaceAll("\\]",""));
			}else{
				candidate.setSkillsName(null);
			}
			
			if(StringUtils.isNotBlank(candidateDTO.getPrimarySkill())){
				candidate.setPrimarySkill(candidateDTO.getPrimarySkill());
			}
			
			if(StringUtils.isNotBlank(candidateDTO.getVisa())){
				
				/*String visaList =null;
				try{
					JSONArray jsonArray = jsonObj.getJSONArray("visa");
					visaList = jsonArray.toString();
				}
				catch(JSONException e){
					visaList =jsonObj.getString("visa");
				}
				visaList = visaList.replaceAll("\\[", "").replaceAll("\\]","");
				visaList = visaList.replaceAll("\"", "");*/
				candidate.setVisaType(candidateDTO.getVisa());
		  	 
			}else{
				candidate.setVisaType(null);
			}
			
			
			/*HashSet<CandidateEducation>	 candidateEducationList = candidateEducationService.getCandidateEducationDetails(jsonObj, orgLocations,candidate,user.getUserCode());
			candidate.setCandidateEducation(candidateEducationList);
			
			HashSet<CandidateEmployers> candidateEmployersList = candidateEmployersService.getCandidateEmployersDetails(jsonObj,orgLocations,candidate,user.getUserCode());
			candidate.setCandidateEmployers(candidateEmployersList);*/
			
			/*if(multiFile != null){
			HashSet<CandidateResumes> candidateResumesList = candidateResumesService.getCandidateResumesDetails(orgLocations,user.getUserCode(),candidate,multiFile);
			candidate.setCandidateResumes(candidateResumesList);
			}*/
			if(candidateDTO.getFileInfo() != null && !candidateDTO.getFileInfo().isEmpty()){
				FileInfo fileInfo = candidateDTO.getFileInfo().get(0);
				HashSet<CandidateResumes> candidateResumesList = candidateResumesService.getCandidateResumesDetails(orgLocations,user.getUserCode(),candidate, fileInfo);
				candidate.setCandidateResumes(candidateResumesList);
				
				if(fileInfo != null){
						candidate.setFileName(fileInfo.getFileName());
						FileUploadUtil.moveFile(candidateDTO.getStaticPath(), fileInfo, "resumes");
					}
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
			OrgLocations orgLocations = orgLocationService.getOrgLocations(orgLocationId);
			//SimpleDateFormat simpleDateFormat = orgLocations.getLocFullFormDateFormatter();
			for(Candidate can :candidateList){
				if(can.getUpdatedDate() != null){
				 can.setCandidateUpdateDateToString(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),can.getUpdatedDate()));
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
		OrgLocations orgLocations = orgLocationService.getOrgLocations(orgLocationId);
		//SimpleDateFormat simpleDateFormat = orgLocations.getLocFullFormDateFormatter();
		for(Candidate can : candidateReportsList){
			if(can.getUpdatedDate() != null){
				 can.setCandidateUpdateDateToString(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),can.getUpdatedDate()));
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
				if(can.getCreatedBy() != null){
				User usr = userService.getUserByUserCode(can.getCreatedBy());
				if(usr.getId() != null){
				tempOwnersIds.add(usr.getId());
				}
				}
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
		OrgLocations orgLocations = orgLocationService.getOrgLocations(orgLocationId);
		//SimpleDateFormat simpleDateFormat = orgLocations.getLocFullFormDateFormatter();
		for (Candidate candidate : candidateList) {
			StringBuilder sb = new StringBuilder();
			candidate.setCandidateName(sb.append(candidate.getFirstName()).append(" ").append(candidate.getLastName()).toString());
			candidate.setEmail(candidate.getEmail());
			candidate.setCandidateUpdateDateToString(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),candidate.getUpdatedDate()));
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
	public Candidate storeCandidateForUsers(CandidateDTO candidateDTO,User user,OrgLocations orgLocations,MasterCandidate masterCandidate) throws Exception
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
			
			if(StringUtils.isNotBlank(candidateDTO.getPhoneNumber())){
				candidate.setMobilePhone(candidateDTO.getPhoneNumber());
			}else
			{
				candidate.setMobilePhone(null);
			}
			
			if(StringUtils.isNotBlank(candidateDTO.getDesignationType())){
				candidate.setDesignationType(candidateDTO.getDesignationType());
			}else
			{
				candidate.setDesignationType(null);
			}
			
			if(StringUtils.isNotBlank(candidateDTO.getDesignationOtherType())){
				candidate.setDesignationOtherType(candidateDTO.getDesignationOtherType());
			}else
			{
				candidate.setDesignationOtherType(null);
			}
			
			if(StringUtils.isNotBlank(candidateDTO.getVendors())){
				OrgRelations orgRelation = orgRelationsService.getOrRelationsById(Long.parseLong(candidateDTO.getVendors()));
				candidate.setOrgRelationId(orgRelation);
				}else{
				candidate.setOrgRelationId(null);
				}

				if(StringUtils.isNotBlank(candidateDTO.getVendorcontact())){
				OrgSpoc orgspoc = orgSpocService.getOrgSpocById(Long.parseLong(candidateDTO.getVendorcontact()));
				candidate.setSpocId(orgspoc);
				}else{
				candidate.setSpocId(null);
				}
			
			candidate.setCandidateUUID(SourceLeadRandomCodeHelper.generateRandomUUID());
			candidate.setGeneratedId(getCandidateId(orgLocations));
			candidate.setCreatedDate(commonUtil.currentDate());
			candidate.setUpdatedDate(commonUtil.currentDate());
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
				 if(can.getFileUrl()!=null){
					 percentage = percentage + 5; 
				 }else if(can.getConsultantUser() != null && can.getConsultantUser().getFileUrl() != null){
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

	@Override
	public List<Candidate> getAssociatesListForSow(Long locationId, String sowType)
	{
		List<Candidate> associateList = candidateDao.getAssociatesListForSow(locationId,sowType);
		for(Candidate can :associateList){
			StringBuilder sb = new StringBuilder();
			if(can.getLastName() != null && !"".equalsIgnoreCase(can.getLastName()))
				can.setCandidateName(sb.append(can.getFirstName()).append(" ").append(can.getLastName()).toString());
			else
				can.setCandidateName(can.getFirstName());
		}
		return associateList;
	}


	@Override
	public Candidate getCandidateByParentCandidateId(Long parentCandidateId) throws Exception {
		return candidateDao.getCandidateByParentCandidateId(parentCandidateId);
	}

}