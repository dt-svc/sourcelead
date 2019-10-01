package com.bis.sourcelead.util;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.CandidateCertificationsDao;
import com.bis.sourcelead.dao.CandidateCommentsDao;
import com.bis.sourcelead.dao.CandidateDao;
import com.bis.sourcelead.dao.CandidateDocumentsDao;
import com.bis.sourcelead.dao.CandidateReferencesDao;
import com.bis.sourcelead.dao.CandidateTimelineActivityDao;
import com.bis.sourcelead.dao.OrgRelationsDao;
import com.bis.sourcelead.dao.ReferContactsDao;
import com.bis.sourcelead.dto.CandidateDTO;
import com.bis.sourcelead.dto.CustomFormFieldDTO;
import com.bis.sourcelead.dto.FileInfo;
import com.bis.sourcelead.elk.index.CandidateIndex;
import com.bis.sourcelead.elk.index.helper.CandidateGlobalIndexHelper;
import com.bis.sourcelead.elk.setup.SourceleadELKBaseIndexer;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.CandidateCertifications;
import com.bis.sourcelead.entity.CandidateComments;
import com.bis.sourcelead.entity.CandidateCustomFieldData;
import com.bis.sourcelead.entity.CandidateDocuments;
import com.bis.sourcelead.entity.CandidateEducation;
import com.bis.sourcelead.entity.CandidateEmployers;
import com.bis.sourcelead.entity.CandidateGlobal;
import com.bis.sourcelead.entity.CandidateHistory;
import com.bis.sourcelead.entity.CandidateReferences;
import com.bis.sourcelead.entity.CandidateResumes;
import com.bis.sourcelead.entity.CandidateTimelineActivity;
import com.bis.sourcelead.entity.EmailAttachment;
import com.bis.sourcelead.entity.EmailMessage;
import com.bis.sourcelead.entity.MasterCandidate;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgRelations;
import com.bis.sourcelead.entity.OrgSpoc;
import com.bis.sourcelead.entity.OrglocationFormFields;
import com.bis.sourcelead.entity.ReferContacts;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserRole;
import com.bis.sourcelead.service.CandidateCertificationsService;
import com.bis.sourcelead.service.CandidateCustomFieldDataService;
import com.bis.sourcelead.service.CandidateDocumentsService;
import com.bis.sourcelead.service.CandidateEducationService;
import com.bis.sourcelead.service.CandidateEmployersService;
import com.bis.sourcelead.service.CandidateGlobalService;
import com.bis.sourcelead.service.CandidateHistoryService;
import com.bis.sourcelead.service.CandidateResumesService;
import com.bis.sourcelead.service.MasterCandidateService;
import com.bis.sourcelead.service.OrgRelationsService;
import com.bis.sourcelead.service.OrgSpocService;
import com.bis.sourcelead.service.OrglocationFormFieldsService;
import com.bis.sourcelead.service.UserRoleService;

@Component
public class CandidateGlobalUtil {

	private static final Logger logger = LoggerFactory.getLogger(CandidateGlobalUtil.class);
	
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	@Autowired
	private CommonUtil commonUtil;
	
	@Autowired
	CandidateGlobalService candidateGlobalService;
	
	@Autowired
	MasterCandidateService masterCandidateService;
	
	@Autowired
	CandidateEducationService candidateEducationService;
	
	@Autowired
	CandidateEmployersService candidateEmployersService;
	
	@Autowired
	CandidateResumesService candidateResumesService;
	
	@Autowired
	CandidateActivityCommonUtil candidateActivityCommonUtil;
	
	@Autowired
	CandidateCustomFieldDataService candidateCustomFieldDataService;
	
	@Autowired
	OrglocationFormFieldsService orglocationFormFieldsService;
	
	@Autowired
	ReferContactsDao referContactsDao;
	
	@Autowired
	OrgSpocService orgSpocService;
	
	@Autowired
	UserRoleService userRoleService;
	
	@Autowired
	OrgRelationsService orgRelationsService;
	
	@Autowired
	CandidateHistoryService candidateHistoryService;
	
	@Autowired
	CandidateTimelineActivityDao candidateTimelineActivityDao;
	
	@Autowired
    private SourceleadELKBaseIndexer<CandidateIndex> sourceleadELKBaseIndexer;
	
	@Autowired
	CandidateGlobalIndexHelper candidateGlobalIndexHelper;
	
	@Autowired
	CandidateDocumentsService candidateDocumentsService;
	
	@Autowired
	CandidateDao candidateDao;
	
	@Autowired
	OrgRelationsDao orgRelationsDao;
	
	@Autowired
	CandidateCertificationsDao candidateCertificationsDao;
	
	@Autowired
	CandidateDocumentsDao candidateDocumentsDao;
	
	@Autowired
	CandidateCommentsDao candidateCommentsDao;
	
	@Autowired
	CandidateReferencesDao candidateReferencesDao;
	
	
	public CandidateGlobal storeCandidateGlobal(CandidateDTO candidateDto,OrgLocations orgLocations,User user) throws Exception {
		
		StringBuffer activitySummary = new StringBuffer();
        CandidateGlobal candidate = null;
        Date date = new Date();
		if(StringUtils.isNotBlank(candidateDto.getCandidateId())){
			candidate = candidateGlobalService.getCandidateGlobalByCandidateId(Long.parseLong(candidateDto.getCandidateId()));
		}else{	
			
			candidate = new CandidateGlobal();
			candidate.setCreatedDate(commonUtil.orgLocationCommonDate(date, orgLocations));
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
			candidate.setGeneratedId(getCandidateId(orgLocations));
		}
        
		if(orgLocations.getCountry().equalsIgnoreCase("India")) {
			candidate.setAadharNumber(candidateDto.getAadharNumber());
			candidate.setPanNumber(candidateDto.getPanNumber());
			}
		
		if(orgLocations.getCountry().equalsIgnoreCase("United States")) {
			candidate.setSsnNumber(candidateDto.getSsnNumber());;
			}
		candidate = getCandidateDetails(candidate, candidateDto, user, orgLocations, activitySummary);
		
		HashSet<CandidateEducation>	 candidateEducationList = candidateGlobalService.getCandidateEducationDetails(candidateDto, orgLocations,candidate,user.getUserCode());
		candidate.setCandidateEducation(candidateEducationList);
		
		HashSet<CandidateEmployers> candidateEmployersList = candidateGlobalService.getCandidateEmployersDetails(candidateDto,orgLocations,candidate,user.getUserCode());
		candidate.setCandidateEmployers(candidateEmployersList);
		
		if(candidateDto.getFileInfo()!= null && !candidateDto.getFileInfo().isEmpty()){
			FileInfo fileInfo = candidateDto.getFileInfo().get(0);
			if(fileInfo != null){
			HashSet<CandidateResumes> candidateResumesList = candidateGlobalService.getCandidateResumesDetails(orgLocations,user.getUserCode(),candidate, fileInfo);
			candidate.setCandidateResumes(candidateResumesList);
				}
			}
		if(candidateDto.getCandidateSource()  != null) {
			candidate.setCandidateSource(candidateDto.getCandidateSource());
			}
		CandidateGlobal savedCandidate = candidateGlobalService.storeCandidateGlobal(candidate);
		
		if(StringUtils.isNotBlank(candidateDto.getCandidateId())){
			storeCandidateActivity(user, orgLocations, candidate, "", Constants.CANDIDATE_ADDED_BY_IMPORTING, activitySummary.toString(), "CANDIDATE");
		}else {
			if(candidateDto.getCandidateCreationType() != null && candidateDto.getCandidateCreationType().equalsIgnoreCase(Constants.MANUAL_CANDIDATE)){
				storeCandidateActivity(user, orgLocations, candidate, "", Constants.CANDIDATE_ADDED_MANUALLY, "", "CANDIDATE");
			}else if(candidateDto.getCandidateCreationType() != null && candidateDto.getCandidateCreationType().equalsIgnoreCase(Constants.IMPORT_CANDIDATE)){
				storeCandidateActivity(user, orgLocations, candidate, "", Constants.CANDIDATE_ADDED_BY_IMPORTING, "", "CANDIDATE");
			}
		}
		if(candidateDto.getCustomFormData() !=null) {
			for( CustomFormFieldDTO customFields:candidateDto.getCustomFormData()) {
				if(customFields != null && customFields.getCustomFormFieldId() > 0 ) {
					CandidateCustomFieldData candidateCustomFields = null;
					if(customFields.getEntityId()!= null && customFields.getEntityId() > 0) {
						candidateCustomFields = candidateCustomFieldDataService.getCandidateCustomFieldDataById(customFields.getEntityId());
					}else {
						candidateCustomFields = new CandidateCustomFieldData();
					}
				candidateCustomFields.setCandidateGlobalId(savedCandidate);
				OrglocationFormFields orgFormField = orglocationFormFieldsService.getOrglocationFormFieldsById(customFields.getCustomFormFieldId());
				candidateCustomFields.setOrglocationsFormFields(orgFormField);
				candidateCustomFields.setCustomFormFieldData(customFields.getCustomFormFieldData());
				candidateCustomFieldDataService.savejobCustomFieldData(candidateCustomFields);
				}
				
			}	
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
		 candidateGlobalService.saveOrUpdateCandidateGlobalElkDocument(savedCandidate);
	     getCandidatePercentage(savedCandidate, orgLocations);
		
		// store candidate as a contact
		
		ReferContacts referContacts=null;
		 referContacts=referContactsDao.getReferContactsByEmails(user.getId(), candidateDto.getEmail());
	
		if(referContacts !=null){
			if(candidateDto.getFirstName() != null){
				referContacts.setFirstName(candidateDto.getFirstName());
			}
			if(candidateDto.getLastName() != null){
				referContacts.setLastName(candidateDto.getLastName());
			}
			if(candidateDto.getPhoneNumber() != null){
				referContacts.setContactUserPhone(candidateDto.getPhoneNumber());
			}
			referContacts.setUpdatedDate(new Date());
			referContacts.setUpdatedBy(user.getUserCode());
					referContacts.setCandidateType(1);
		}else{
			 referContacts = new ReferContacts();
			
			if(candidateDto.getEmail() != null){
				referContacts.setContactUserEmail(candidateDto.getEmail());
			}
			
			if(candidateDto.getFirstName() != null){
				referContacts.setFirstName(candidateDto.getFirstName());
			}
			if(candidateDto.getLastName() != null){
				referContacts.setLastName(candidateDto.getLastName());
			}
			if(candidateDto.getPhoneNumber() != null){
				referContacts.setContactUserPhone(candidateDto.getPhoneNumber());
			}
			
			referContacts.setReferFromUser(user);
			String contactUUID = SourceLeadRandomCodeHelper.generateUUID();
			referContacts.setContactUserUUID(contactUUID);
			referContacts.setCreatedDate(new Date());
			referContacts.setCandidateType(1);
			
			referContacts.setCreatedBy(user.getUserCode());
		}
	
	referContactsDao.addReferContacts(referContacts);
		
	
		if(candidateDto.getFileInfo()!= null && !candidateDto.getFileInfo().isEmpty()){
			FileInfo fileInfo = candidateDto.getFileInfo().get(0);
			if(fileInfo != null){
			FileUploadUtil.moveFile(candidateDto.getStaticPath(), fileInfo, "resumes");
				}
			}
		return savedCandidate;
	}
	
	
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
	
public CandidateGlobal getCandidateDetails(CandidateGlobal candidate, CandidateDTO candidateDto, User user,OrgLocations orgLocations,StringBuffer activitySummary) throws Exception {
		
		if(candidateDto.getFirstName() != null){
			if(candidate.getFirstName() != null && candidate.getFirstName() != "" && !candidateDto.getFirstName().equalsIgnoreCase(candidate.getFirstName())){
				activitySummary.append("First Name").append(" : ").append(Constants.OLD_VALUE_TAG_INITIATION).append(candidate.getFirstName()).append(Constants.OLD_VALUE_TAG_END).append(" ").append(Constants.NEW_VALUE_TAG_INITIATION).append(candidateDto.getFirstName()).append(Constants.NEW_VALUE_TAG_END).append(" ");
			}else if(candidate.getFirstName() != null && candidate.getFirstName() != "" && candidateDto.getFirstName().equalsIgnoreCase(candidate.getFirstName())){
				activitySummary.append("First Name").append(" : ").append(" ").append(Constants.NEW_VALUE_TAG_INITIATION).append(candidateDto.getFirstName()).append(Constants.NEW_VALUE_TAG_END).append(" ");
			}else
				activitySummary.append("First Name").append(" : ").append("---").append(" ");
		candidate.setFirstName(candidateDto.getFirstName());
		}
		
		if(candidateDto.getLastName() != null){
			if(candidate.getLastName() != null && !candidateDto.getLastName().equalsIgnoreCase(candidate.getLastName())){
				activitySummary.append("Last Name").append(" : ").append(Constants.OLD_VALUE_TAG_INITIATION).append(candidate.getLastName()).append(Constants.OLD_VALUE_TAG_END).append(" ").append(Constants.NEW_VALUE_TAG_INITIATION).append(candidateDto.getLastName()).append(Constants.NEW_VALUE_TAG_END).append(" ");
			}else if((candidate.getLastName() != null || candidate.getLastName() != "" ||  candidateDto.getLastName().equalsIgnoreCase(candidate.getLastName())) && candidateDto.getLastName() !="" ){
				activitySummary.append("Last Name").append(" : ").append(" ").append(Constants.NEW_VALUE_TAG_INITIATION).append(candidateDto.getLastName()).append(Constants.NEW_VALUE_TAG_END).append(" ");
			}else
				activitySummary.append("Last Name").append(" : ").append("---").append(" ");
		candidate.setLastName(candidateDto.getLastName());
		}
		
		if(candidateDto.getMiddleName() != null){
			if(candidate.getMiddelName() != null && !candidateDto.getMiddleName().equalsIgnoreCase(candidate.getMiddelName())){
				activitySummary.append("Middle Name").append(" : ").append(Constants.OLD_VALUE_TAG_INITIATION).append(candidate.getMiddelName()).append(Constants.OLD_VALUE_TAG_END).append(" ").append(Constants.NEW_VALUE_TAG_INITIATION).append(candidateDto.getMiddleName()).append(Constants.NEW_VALUE_TAG_END).append(" ");
			}else if((candidate.getLastName() != null || candidate.getLastName() != "" ||  candidateDto.getMiddleName().equalsIgnoreCase(candidate.getMiddelName())) && candidateDto.getMiddleName() !="" ){
				activitySummary.append("Middle Name").append(" : ").append(" ").append(Constants.NEW_VALUE_TAG_INITIATION).append(candidateDto.getMiddleName()).append(Constants.NEW_VALUE_TAG_END).append(" ");
			}else
				activitySummary.append("Middle Name").append(" : ").append("---").append(" ");
			candidate.setMiddelName(candidateDto.getMiddleName());
		}
		
		if(candidateDto.getGender() != null){
			if(candidate.getGender() != null && !candidateDto.getGender().equalsIgnoreCase(candidate.getGender())){
				activitySummary.append("Gender").append(" : ").append(Constants.OLD_VALUE_TAG_INITIATION).append(candidate.getGender()).append(Constants.OLD_VALUE_TAG_END).append(" ").append(Constants.NEW_VALUE_TAG_INITIATION).append(candidateDto.getGender()).append(Constants.NEW_VALUE_TAG_END).append(" ");
			}else if((candidate.getLastName() != null || candidate.getGender() != "" ||  candidateDto.getGender().equalsIgnoreCase(candidate.getGender())) && candidateDto.getGender() !="" ){
				activitySummary.append("Gender").append(" : ").append(" ").append(Constants.NEW_VALUE_TAG_INITIATION).append(candidateDto.getGender()).append(Constants.NEW_VALUE_TAG_END).append(" ");
			}else
				activitySummary.append("Gender").append(" : ").append("---").append(" ");
			candidate.setGender(candidateDto.getGender());
		}
		
		if(candidateDto.getMaritalStatus() != null){
			if(candidate.getMaritalStatus() != null && !candidateDto.getMaritalStatus().equalsIgnoreCase(candidate.getMaritalStatus())){
				activitySummary.append("Marital Status").append(" : ").append(Constants.OLD_VALUE_TAG_INITIATION).append(candidate.getMaritalStatus()).append(Constants.OLD_VALUE_TAG_END).append(" ").append(Constants.NEW_VALUE_TAG_INITIATION).append(candidateDto.getMaritalStatus()).append(Constants.NEW_VALUE_TAG_END).append(" ");
			}else if((candidate.getLastName() != null || candidate.getMaritalStatus() != "" ||  candidateDto.getMaritalStatus().equalsIgnoreCase(candidate.getMaritalStatus())) && candidateDto.getMaritalStatus() !="" ){
				activitySummary.append("Marital Status").append(" : ").append(" ").append(Constants.NEW_VALUE_TAG_INITIATION).append(candidateDto.getMaritalStatus()).append(Constants.NEW_VALUE_TAG_END).append(" ");
			}else
				activitySummary.append("Marital Status").append(" : ").append("---").append(" ");
			candidate.setMaritalStatus(candidateDto.getMaritalStatus());
		}
		
		if(StringUtils.isNotBlank(candidateDto.getDateofBirth())){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			if(candidate.getDateOfBirth() != null && !(sdf.parse(candidateDto.getDateofBirth()).compareTo(candidate.getDateOfBirth()) <= 0)){
				activitySummary.append("Date of Birth").append(" : ").append(Constants.OLD_VALUE_TAG_INITIATION).append(candidate.getDateOfBirth()).append(Constants.OLD_VALUE_TAG_END).append(" ").append(Constants.NEW_VALUE_TAG_INITIATION).append(candidateDto.getMaritalStatus()).append(Constants.NEW_VALUE_TAG_END).append(" ");
			}else if(candidateDto.getDateofBirth() !="" ){
				activitySummary.append("Date of Birth").append(" : ").append(" ").append(Constants.NEW_VALUE_TAG_INITIATION).append(candidateDto.getDateofBirth()).append(Constants.NEW_VALUE_TAG_END).append(" ");
			}else
				activitySummary.append("Date of Birth").append(" : ").append("---").append(" ");
			candidate.setDateOfBirth(commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(), candidateDto.getDateofBirth()));
		}else{
			candidate.setDateOfBirth(null);
		}
		
		if(candidateDto.getCountry() != null){
			if(candidate.getCountry() != null && candidate.getCountry() != "" && !candidateDto.getCountry().equalsIgnoreCase(candidate.getCountry())){
				activitySummary.append("Country").append(" : ").append(Constants.OLD_VALUE_TAG_INITIATION).append(candidate.getCountry()).append(Constants.OLD_VALUE_TAG_END).append(" ").append(Constants.NEW_VALUE_TAG_INITIATION).append(candidateDto.getCountry()).append(Constants.NEW_VALUE_TAG_END).append(" ");
			}else if((candidate.getCountry() != null || candidate.getCountry() != "" ||  candidateDto.getCountry().equalsIgnoreCase(candidate.getCountry())) && candidateDto.getCountry() !="" ){
				activitySummary.append("Country").append(" : ").append(" ").append(Constants.NEW_VALUE_TAG_INITIATION).append(candidateDto.getCountry()).append(Constants.NEW_VALUE_TAG_END).append(" ");
			}else
				activitySummary.append("Country").append(" : ").append("---").append(" ");
		candidate.setCountry(candidateDto.getCountry());
		}
		if(candidateDto.getState() != null){
			if( candidate.getState() != null && candidate.getState() != "" && !candidateDto.getState().equalsIgnoreCase(candidate.getState())){
				activitySummary.append("State").append(" : ").append(Constants.OLD_VALUE_TAG_INITIATION).append(candidate.getState()).append(Constants.OLD_VALUE_TAG_END).append(" ").append(Constants.NEW_VALUE_TAG_INITIATION).append(candidateDto.getState()).append(Constants.NEW_VALUE_TAG_END).append(" ");
			}else if((candidate.getState() != null || candidate.getState() != "" ||  candidateDto.getState().equalsIgnoreCase(candidate.getState())) && candidateDto.getState() !="" ){
				activitySummary.append("State").append(" : ").append(" ").append(Constants.NEW_VALUE_TAG_INITIATION).append(candidateDto.getState()).append(Constants.NEW_VALUE_TAG_END).append(" ");
			}else
				activitySummary.append("State").append(" : ").append("---").append(" ");
		candidate.setState(candidateDto.getState());
		}
		if(candidateDto.getCity() != null  ){
			if(candidate.getCity() != null && candidate.getCity() != "" && !candidateDto.getCity().equalsIgnoreCase(candidate.getCity())){
				activitySummary.append("City").append(" : ").append(Constants.OLD_VALUE_TAG_INITIATION).append(candidate.getCity()).append(Constants.OLD_VALUE_TAG_END).append(" ").append(Constants.NEW_VALUE_TAG_INITIATION).append(candidateDto.getCity()).append(Constants.NEW_VALUE_TAG_END).append(" ");
			}else if((candidate.getCity() != null || candidate.getCity() != "" ||  candidateDto.getCity().equalsIgnoreCase(candidate.getCity())) && candidateDto.getCity() !="" ){
				activitySummary.append("City").append(" : ").append(" ").append(Constants.NEW_VALUE_TAG_INITIATION).append(candidateDto.getCity()).append(Constants.NEW_VALUE_TAG_END).append(" ");
			}else
				activitySummary.append("City").append(" : ").append("---").append(" ");
		candidate.setCity(candidateDto.getCity());
		}
		if(candidateDto.getLocation() != null){
			candidate.setLocation(candidateDto.getLocation());
		}
		if(candidateDto.getZipcode()!= null){
			if(candidate.getZipCode() != null && candidate.getZipCode() != "" &&  !candidateDto.getZipcode().equalsIgnoreCase(candidate.getZipCode())){
				activitySummary.append("Zip Code").append(" : ").append(Constants.OLD_VALUE_TAG_INITIATION).append(candidate.getZipCode()).append(Constants.OLD_VALUE_TAG_END).append(" ").append(Constants.NEW_VALUE_TAG_INITIATION).append(candidateDto.getZipcode()).append(Constants.NEW_VALUE_TAG_END).append(" ");
			}else if((candidate.getZipCode() != null || candidate.getZipCode() != "" ||  candidateDto.getZipcode().equalsIgnoreCase(candidate.getZipCode())) && candidateDto.getZipcode() !="" ){
				activitySummary.append("Zip Code").append(" : ").append(" ").append(Constants.NEW_VALUE_TAG_INITIATION).append(candidateDto.getZipcode()).append(Constants.NEW_VALUE_TAG_END).append(" ");
			}else
				activitySummary.append("Zip Code").append(" : ").append("---").append(" ");
			candidate.setZipCode(candidateDto.getZipcode());
		}
		
		
		if(candidateDto.getLatitude() != null){
			candidate.setLatitude(candidateDto.getLatitude());
		}
		
		if(candidateDto.getLongitude() != null){
			candidate.setLongitude(candidateDto.getLongitude());
		}
		
		if(candidateDto.getMobilePhone() != null){
			if(candidate.getMobilePhone() != null && candidate.getMobilePhone() != "" && !candidateDto.getMobilePhone().equalsIgnoreCase(candidate.getMobilePhone())){
				activitySummary.append("Mobile Phone").append(" : ").append(Constants.OLD_VALUE_TAG_INITIATION).append(candidate.getMobilePhone()).append(Constants.OLD_VALUE_TAG_END).append(" ").append(Constants.NEW_VALUE_TAG_INITIATION).append(candidateDto.getMobilePhone()).append(Constants.NEW_VALUE_TAG_END).append(" ");
			}else if((candidate.getMobilePhone() != null || candidate.getMobilePhone() != "" ||  candidateDto.getMobilePhone().equalsIgnoreCase(candidate.getMobilePhone())) && candidateDto.getMobilePhone() !="" ){
				activitySummary.append("Mobile Phone").append(" : ").append(" ").append(Constants.NEW_VALUE_TAG_INITIATION).append(candidateDto.getMobilePhone()).append(Constants.NEW_VALUE_TAG_END).append(" ");
			}else
				activitySummary.append("Mobile Phone").append(" : ").append("---").append(" ");
		candidate.setMobilePhone(candidateDto.getMobilePhone());
		}
		if(candidateDto.getAlternateMobilePhone() != null ){
			if(candidate.getAlternateMobilePhone() != null && candidate.getAlternateMobilePhone() != "" && !candidateDto.getAlternateMobilePhone().equalsIgnoreCase(candidate.getAlternateMobilePhone())){
				activitySummary.append("Alternate Mobile Phone").append(" : ").append(Constants.OLD_VALUE_TAG_INITIATION).append(candidate.getAlternateMobilePhone()).append(Constants.OLD_VALUE_TAG_END).append(" ").append(Constants.NEW_VALUE_TAG_INITIATION).append(candidateDto.getAlternateMobilePhone()).append(Constants.NEW_VALUE_TAG_END).append(" ");
			}else if((candidate.getAlternateMobilePhone() != null || candidate.getAlternateMobilePhone() != "" ||  candidateDto.getAlternateMobilePhone().equalsIgnoreCase(candidate.getAlternateMobilePhone())) && candidateDto.getAlternateMobilePhone() !="" ){
				activitySummary.append("Alternate Mobile Phone").append(" : ").append(" ").append(Constants.NEW_VALUE_TAG_INITIATION).append(candidateDto.getAlternateMobilePhone()).append(Constants.NEW_VALUE_TAG_END).append(" ");
			}else
				activitySummary.append("Alternate Mobile Phone").append(" : ").append("---").append(" ");
			candidate.setAlternateMobilePhone(candidateDto.getAlternateMobilePhone());
		}
		if(candidateDto.getEmail() != null){
			if(candidate.getEmail() != null && candidate.getEmail() != "" && !candidateDto.getEmail().equalsIgnoreCase(candidate.getEmail())){
				activitySummary.append("Email").append(" : ").append(Constants.OLD_VALUE_TAG_INITIATION).append(candidate.getEmail()).append(Constants.OLD_VALUE_TAG_END).append(" ").append(Constants.NEW_VALUE_TAG_INITIATION).append(candidateDto.getEmail()).append(Constants.NEW_VALUE_TAG_END).append(" ");
			}else if((candidate.getEmail() != null || candidate.getEmail() != "" ||  candidateDto.getEmail().equalsIgnoreCase(candidate.getEmail())) && candidateDto.getEmail() !="" ){
				activitySummary.append("Email").append(" : ").append(" ").append(Constants.NEW_VALUE_TAG_INITIATION).append(candidateDto.getEmail()).append(Constants.NEW_VALUE_TAG_END).append(" ");
			}else
				activitySummary.append("Email").append(" : ").append("---").append(" ");
				candidate.setEmail(candidateDto.getEmail().trim());
		}
		if(candidateDto.getCurrentRate() !=null){
			if(candidate.getCurrentRate() != null && candidate.getCurrentRate() != "" && !candidateDto.getCurrentRate().equalsIgnoreCase(candidate.getCurrentRate())){
				activitySummary.append("Current Rate").append(" : ").append(Constants.OLD_VALUE_TAG_INITIATION).append(candidate.getCurrentRate()).append(Constants.OLD_VALUE_TAG_END).append(" ").append(Constants.NEW_VALUE_TAG_INITIATION).append(candidateDto.getCurrentRate()).append(Constants.NEW_VALUE_TAG_END).append(" ");
			}else if((candidate.getCurrentRate() != null || candidate.getCurrentRate() != "" ||  candidateDto.getCurrentRate().equalsIgnoreCase(candidate.getCurrentRate())) && candidateDto.getCurrentRate() !="" ){
				activitySummary.append("Current Rate").append(" : ").append(" ").append(Constants.NEW_VALUE_TAG_INITIATION).append(candidateDto.getCurrentRate()).append(Constants.NEW_VALUE_TAG_END).append(" ");
			}else
				activitySummary.append("Current Rate").append(" : ").append("---");
		candidate.setCurrentRate(candidateDto.getCurrentRate());
		}
		
		if(candidateDto.getCurrentRateType() != null){
			if(candidate.getCurrentRateType() != null && candidate.getCurrentRateType() != "" && !candidateDto.getCurrentRateType().equalsIgnoreCase(candidate.getCurrentRateType())){
				activitySummary.append("Current Rate Type").append(" : ").append(Constants.OLD_VALUE_TAG_INITIATION).append(candidate.getCurrentRateType()).append(Constants.OLD_VALUE_TAG_END).append(" ").append(Constants.NEW_VALUE_TAG_INITIATION).append(candidateDto.getCurrentRateType()).append(Constants.NEW_VALUE_TAG_END).append(" ");
			}else if((candidate.getCurrentRateType() != null || candidate.getCurrentRateType() != "" ||  candidateDto.getCurrentRateType().equalsIgnoreCase(candidate.getCurrentRateType())) && candidateDto.getCurrentRateType() !="" ){
				activitySummary.append("Current Rate Type").append(" : ").append(" ").append(Constants.NEW_VALUE_TAG_INITIATION).append(candidateDto.getCurrentRateType()).append(Constants.NEW_VALUE_TAG_END).append(" ");
			}else
				activitySummary.append("Current Rate Type").append(" : ").append("---").append(" ");
			candidate.setCurrentRateType(candidateDto.getCurrentRateType());
			}
		
		if(candidateDto.getCurrentRateCurrency() !=null){
			if(candidate.getCurrentRateCurrency() != null  && candidate.getCurrentRateCurrency() != "" && !candidateDto.getCurrentRateCurrency().equalsIgnoreCase(candidate.getCurrentRateCurrency())){
				activitySummary.append("Current Rate Currency").append(" : ").append(Constants.OLD_VALUE_TAG_INITIATION).append(candidate.getCurrentRateCurrency()).append(Constants.OLD_VALUE_TAG_END).append(" ").append(Constants.NEW_VALUE_TAG_INITIATION).append(candidateDto.getCurrentRateCurrency()).append(Constants.NEW_VALUE_TAG_END).append(" ");
			}else if((candidate.getCurrentRateCurrency() != null || candidate.getCurrentRateCurrency() != "" ||  candidateDto.getCurrentRateCurrency().equalsIgnoreCase(candidate.getCurrentRateCurrency())) && candidateDto.getCurrentRateCurrency() !="" ){
				activitySummary.append("Current Rate Currency").append(" : ").append(" ").append(Constants.NEW_VALUE_TAG_INITIATION).append(candidateDto.getCurrentRateCurrency()).append(Constants.NEW_VALUE_TAG_END).append(" ");
			}else
				activitySummary.append("Current Rate Currency").append(" : ").append("---").append(" ");
		candidate.setCurrentRateCurrency(candidateDto.getCurrentRateCurrency());
		}
		if(candidateDto.getDesignation() !=null){
			if(candidate.getDesignation() != null && candidate.getDesignation() != "" && !candidateDto.getDesignation().equalsIgnoreCase(candidate.getDesignation())){
				activitySummary.append("Designation").append(" : ").append(Constants.OLD_VALUE_TAG_INITIATION).append(candidate.getDesignation()).append(Constants.OLD_VALUE_TAG_END).append(" ").append(Constants.NEW_VALUE_TAG_INITIATION).append(candidateDto.getDesignation()).append(Constants.NEW_VALUE_TAG_END).append(" ");
			}else if((candidate.getDesignation() != null || candidate.getDesignation() != "" ||  candidateDto.getDesignation().equalsIgnoreCase(candidate.getDesignation())) && candidateDto.getDesignation() !="" ){
				activitySummary.append("Designation").append(" : ").append(" ").append(Constants.NEW_VALUE_TAG_INITIATION).append(candidateDto.getDesignation()).append(Constants.NEW_VALUE_TAG_END).append(" ");
			}else
				activitySummary.append("Designation").append(" : ").append("---").append(" ");
		candidate.setDesignation(candidateDto.getDesignation());
		}
		
		if(candidateDto.getExpectedRate() !=null ){
			if(candidate.getExpectedRate() != null  && candidate.getExpectedRate() != "" && !candidateDto.getExpectedRate().equalsIgnoreCase(candidate.getExpectedRate())){
				activitySummary.append("Expected Rate").append(" : ").append(Constants.OLD_VALUE_TAG_INITIATION).append(candidate.getExpectedRate()).append(Constants.OLD_VALUE_TAG_END).append(" ").append(Constants.NEW_VALUE_TAG_INITIATION).append(candidateDto.getExpectedRate()).append(Constants.NEW_VALUE_TAG_END).append(" ");
			}else if((candidate.getExpectedRate() != null || candidate.getExpectedRate() != "" ||  candidateDto.getExpectedRate().equalsIgnoreCase(candidate.getExpectedRate())) && candidateDto.getExpectedRate() !="" ){
				activitySummary.append("Expected Rate").append(" : ").append(" ").append(Constants.NEW_VALUE_TAG_INITIATION).append(candidateDto.getExpectedRate()).append(Constants.NEW_VALUE_TAG_END).append(" ");
			}else
				activitySummary.append("Expected Rate").append(" : ").append("---").append(" ");
		candidate.setExpectedRate(candidateDto.getExpectedRate());
		}
		
		if(candidateDto.getRateType() != null){
			if(candidate.getRateType() != null  && candidate.getRateType() != "" && !candidateDto.getRateType().equalsIgnoreCase(candidate.getRateType())){
				activitySummary.append("Rate Type").append(" : ").append(Constants.OLD_VALUE_TAG_INITIATION).append(candidate.getRateType()).append(Constants.OLD_VALUE_TAG_END).append(" ").append(Constants.NEW_VALUE_TAG_INITIATION).append(candidateDto.getRateType()).append(Constants.NEW_VALUE_TAG_END).append(" ");
			}else if((candidate.getRateType() != null || candidate.getRateType() != "" ||  candidateDto.getRateType().equalsIgnoreCase(candidate.getRateType())) && candidateDto.getRateType() !="" ){
				activitySummary.append("Rate Type").append(" : ").append(" ").append(Constants.NEW_VALUE_TAG_INITIATION).append(candidateDto.getRateType()).append(Constants.NEW_VALUE_TAG_END).append(" ");
			}else
				activitySummary.append("Rate Type").append(" : ").append("---").append(" ");
			candidate.setRateType(candidateDto.getRateType());
			}
		
		if(candidateDto.getExpectedRateCurrencey() !=null ){
			if(candidate.getExpectedRateCurrencey() != null  && candidate.getExpectedRateCurrencey() != "" && !candidateDto.getExpectedRateCurrencey().equalsIgnoreCase(candidate.getExpectedRateCurrencey())){
				activitySummary.append("Expected Rate Currencey").append(" : ").append(Constants.OLD_VALUE_TAG_INITIATION).append(candidate.getExpectedRateCurrencey()).append(Constants.OLD_VALUE_TAG_END).append(" ").append(Constants.NEW_VALUE_TAG_INITIATION).append(candidateDto.getExpectedRateCurrencey()).append(Constants.NEW_VALUE_TAG_END).append(" ");
			}else if((candidate.getExpectedRateCurrencey() != null || candidate.getExpectedRateCurrencey() != "" ||  candidateDto.getExpectedRateCurrencey().equalsIgnoreCase(candidate.getExpectedRateCurrencey())) && candidateDto.getExpectedRateCurrencey() !="" ){
				activitySummary.append("Expected Rate Currencey").append(" : ").append(" ").append(Constants.NEW_VALUE_TAG_INITIATION).append(candidateDto.getExpectedRateCurrencey()).append(Constants.NEW_VALUE_TAG_END).append(" ");
			}else
				activitySummary.append("Expected Rate Currencey").append(" : ").append("---").append(" ");
			candidate.setExpectedRateCurrencey(candidateDto.getExpectedRateCurrencey());
		}
		
		if(candidateDto.getSummary() !=null){
			if(candidate.getSummary() != null && candidate.getSummary() != "" && !candidateDto.getSummary().equalsIgnoreCase(candidate.getSummary())){
				activitySummary.append("Summary").append(" : ").append(Constants.OLD_VALUE_TAG_INITIATION).append(candidate.getSummary()).append(Constants.OLD_VALUE_TAG_END).append(" ").append(Constants.NEW_VALUE_TAG_INITIATION).append(candidateDto.getSummary()).append(Constants.NEW_VALUE_TAG_END).append(" ");
			}else if((candidate.getSummary() != null || candidate.getSummary() != "" ||  candidateDto.getSummary().equalsIgnoreCase(candidate.getSummary())) && candidateDto.getSummary() !="" ){
				activitySummary.append("Summary").append(" : ").append(" ").append(Constants.NEW_VALUE_TAG_INITIATION).append(candidateDto.getSummary()).append(Constants.NEW_VALUE_TAG_END).append(" ");
			}else
				activitySummary.append("Summary").append(" : ").append("---").append(" ");
		candidate.setSummary(candidateDto.getSummary());
		}
		
			if(candidate.getDesignationType() != null  && candidate.getDesignationType() != "" && !candidateDto.getDesignationType().equalsIgnoreCase(candidate.getDesignationType()) ){
				activitySummary.append("Employment Type").append(" : ").append(Constants.OLD_VALUE_TAG_INITIATION).append(candidate.getDesignationType()).append(Constants.OLD_VALUE_TAG_END).append(" ").append(Constants.NEW_VALUE_TAG_INITIATION).append(candidateDto.getDesignationType()).append(Constants.NEW_VALUE_TAG_END).append(" ");
			}else if((candidate.getDesignationType() != null || candidate.getDesignationType() != "" ||  candidateDto.getDesignationType().equalsIgnoreCase(candidate.getDesignationType())) && candidateDto.getDesignationType() !="" ){
				activitySummary.append("Employment Type").append(" : ").append(" ").append(Constants.NEW_VALUE_TAG_INITIATION).append(candidateDto.getDesignationType()).append(Constants.NEW_VALUE_TAG_END).append(" ");
			}else
				activitySummary.append("Employment Type").append(" : ").append("---").append(" ");
		candidate.setDesignationType(candidateDto.getDesignationType());
		
		  if(candidate.getDesignationType() != null && "Other".equalsIgnoreCase(candidate.getDesignationType())) {
		    	candidate.setDesignationOtherType(candidateDto.getDesignationOtherType());
		    }
		
			if(candidate.getExperienceYears() != null  && candidate.getExperienceYears() != "" && !candidateDto.getExperienceYears().equalsIgnoreCase(candidate.getExperienceYears())){
				activitySummary.append("Experience Years").append(" : ").append(Constants.OLD_VALUE_TAG_INITIATION).append(candidate.getExperienceYears()).append(Constants.OLD_VALUE_TAG_END).append(" ").append(Constants.NEW_VALUE_TAG_INITIATION).append(candidateDto.getExperienceYears()).append(Constants.NEW_VALUE_TAG_END).append(" ");
			}else if((candidate.getExperienceYears() != null || candidate.getExperienceYears() != "" ||  candidateDto.getExperienceYears().equalsIgnoreCase(candidate.getExperienceYears())) && candidateDto.getExperienceYears() !="" ){
				activitySummary.append("Experience Years").append(" : ").append(" ").append(Constants.NEW_VALUE_TAG_INITIATION).append(candidateDto.getExperienceYears()).append(Constants.NEW_VALUE_TAG_END).append(" ");
			}else
				activitySummary.append("Experience Years").append(" : ").append("---").append(" ");
		candidate.setExperienceYears(candidateDto.getExperienceYears());
		
		
		if(StringUtils.isNotBlank(candidateDto.getExperienceMonths())){
			if(candidate.getExperienceMonths() != null  && candidate.getExperienceMonths() != "" && !candidateDto.getExperienceMonths().equalsIgnoreCase(candidate.getExperienceMonths())){
				activitySummary.append("Experience Months").append(" : ").append(Constants.OLD_VALUE_TAG_INITIATION).append(candidate.getExperienceMonths()).append(Constants.OLD_VALUE_TAG_END).append(" ").append(Constants.NEW_VALUE_TAG_INITIATION).append(candidateDto.getExperienceMonths()).append(Constants.NEW_VALUE_TAG_END).append(" ");
			}else if((candidate.getExperienceMonths() != null || candidate.getExperienceMonths() != "" ||  candidateDto.getExperienceMonths().equalsIgnoreCase(candidate.getExperienceMonths())) && candidateDto.getExperienceMonths() !="" ){
				activitySummary.append("Experience Months").append(" : ").append(" ").append(Constants.NEW_VALUE_TAG_INITIATION).append(candidateDto.getExperienceMonths()).append(Constants.NEW_VALUE_TAG_END).append(" ");
			}else
				activitySummary.append("Experience Months").append(" : ").append("---").append(" ");
			candidate.setExperienceMonths(candidateDto.getExperienceMonths());	
		}else{
			candidate.setExperienceMonths(null);	
		}
		
		 String noticePeriod = null;
		 candidate.setOtherNoticePeriod(null);
		if(candidateDto.getNoticePeriod() != null) {
			noticePeriod = candidateDto.getNoticePeriod().replaceAll("\\s+", " ");
		}
		
	    if(noticePeriod != null) {
	    	
		if(candidate.getNoticePeriod() != null  && candidate.getNoticePeriod() != "" && !noticePeriod.equalsIgnoreCase(candidate.getNoticePeriod())){
			 activitySummary.append("Notice Period").append(" : ").append(Constants.OLD_VALUE_TAG_INITIATION).append(candidate.getNoticePeriod()).append(Constants.OLD_VALUE_TAG_END).append(" ").append(Constants.NEW_VALUE_TAG_INITIATION).append(noticePeriod).append(Constants.NEW_VALUE_TAG_END).append(" ");
		}else if((candidate.getNoticePeriod() != null || candidate.getNoticePeriod() != "" ||  noticePeriod.equalsIgnoreCase(candidate.getNoticePeriod())) && noticePeriod !="" ){
			  activitySummary.append("Notice Period").append(" : ").append(" ").append(Constants.NEW_VALUE_TAG_INITIATION).append(noticePeriod).append(Constants.NEW_VALUE_TAG_END).append(" ");
		}else 
			activitySummary.append("Notice Period").append(" : ").append("---").append(" ");
			candidate.setNoticePeriod(noticePeriod);	
	     }
	    
	    if(candidate.getNoticePeriod() != null && candidate.getNoticePeriod().equalsIgnoreCase("Other")) {
	    	candidate.setOtherNoticePeriod(candidateDto.getOtherNoticePeriod());
	    }
		
		if(candidateDto.getAddress() !=null){
			if(candidate.getAddress() != null   && candidate.getAddress() != "" && !candidateDto.getAddress().equalsIgnoreCase(candidate.getAddress())){
				activitySummary.append("Address").append(" : ").append(Constants.OLD_VALUE_TAG_INITIATION).append(candidate.getAddress()).append(Constants.OLD_VALUE_TAG_END).append(" ").append(Constants.NEW_VALUE_TAG_INITIATION).append(candidateDto.getAddress()).append(Constants.NEW_VALUE_TAG_END).append(" ");
			}else if((candidate.getAddress() != null || candidate.getAddress() != "" ||  candidateDto.getAddress().equalsIgnoreCase(candidate.getAddress())) && candidateDto.getAddress() !="" ){
				activitySummary.append("Address").append(" : ").append(" ").append(Constants.NEW_VALUE_TAG_INITIATION).append(candidateDto.getAddress()).append(Constants.NEW_VALUE_TAG_END).append(" ");
			}else
			activitySummary.append("Address").append(" : ").append("---").append(" ");
			candidate.setAddress(candidateDto.getAddress());
		  }
		
		UserRole userRole = userRoleService.getUserDetailsByUserIdAndLocationIdAndUserType(user.getId(),orgLocations.getId(),"Recruiter");
		if(null != userRole && userRole.getRoles().getRoleName().equalsIgnoreCase("ROLE_CROWDSOURCING_RECRUITER")){
			candidate.setRecruiterUserId(userRole.getUser());	
		}
		
		if(StringUtils.isNotBlank(candidateDto.getCandidateSkills())){
			
			if(candidate.getSkillsName() != null  && candidate.getSkillsName() != "" && !candidateDto.getCandidateSkills().equalsIgnoreCase(candidate.getSkillsName())){
				activitySummary.append("Skills").append(" : ").append(Constants.OLD_VALUE_TAG_INITIATION).append(candidate.getSkillsName()).append(Constants.OLD_VALUE_TAG_END).append(" ").append(Constants.NEW_VALUE_TAG_INITIATION).append(candidateDto.getCandidateSkills()).append(Constants.NEW_VALUE_TAG_END).append(" ");
			}else if(candidate.getSkillsName() != null || candidate.getSkillsName() != "" || candidateDto.getCandidateSkills().equalsIgnoreCase(candidate.getSkillsName())){
				activitySummary.append("Skills").append(" : ").append(" ").append(Constants.NEW_VALUE_TAG_INITIATION).append(candidateDto.getCandidateSkills()).append(Constants.NEW_VALUE_TAG_END).append(" ");
			}else
				activitySummary.append("Skills").append(" : ").append("---").append(" ");
			candidate.setSkillsName(candidateDto.getCandidateSkills());
		}
		
		if(StringUtils.isNotBlank(candidateDto.getVisa())){
			
			if(candidate.getVisaType() != null  && candidate.getVisaType() != "" && !candidateDto.getVisa().equalsIgnoreCase(candidate.getVisaType())) {
				activitySummary.append("Immigration Status").append(" : ").append(Constants.OLD_VALUE_TAG_INITIATION).append(candidate.getVisaType()).append(Constants.OLD_VALUE_TAG_END).append(" ").append(Constants.NEW_VALUE_TAG_INITIATION).append(candidateDto.getVisa()).append(Constants.NEW_VALUE_TAG_END).append(" ");
			}else if((candidate.getVisaType() != null || candidate.getVisaType() != "" ||  candidateDto.getVisa().equalsIgnoreCase(candidate.getVisaType())) && candidateDto.getVisa() !="" ){
				activitySummary.append("Immigration Status").append(" : ").append(" ").append(Constants.NEW_VALUE_TAG_INITIATION).append(candidateDto.getVisa()).append(Constants.NEW_VALUE_TAG_END).append(" ");
			}else
				activitySummary.append("Immigration Status").append(" : ").append("---").append(" ");
			candidate.setVisaType(candidateDto.getVisa());
	  	 
		}
		
			if(candidate.getPrimarySkill() != null   && candidate.getPrimarySkill() != "" && !candidateDto.getPrimarySkill().equalsIgnoreCase(candidate.getPrimarySkill())){
				activitySummary.append("Primary Skills").append(" : ").append(Constants.OLD_VALUE_TAG_INITIATION).append(candidate.getPrimarySkill()).append(Constants.OLD_VALUE_TAG_END).append(" ").append(Constants.NEW_VALUE_TAG_INITIATION).append(candidateDto.getPrimarySkill()).append(Constants.NEW_VALUE_TAG_END).append(" ");
			}else if((candidate.getPrimarySkill() != null || candidate.getPrimarySkill() != "" ||  candidateDto.getPrimarySkill().equalsIgnoreCase(candidate.getPrimarySkill())) && candidateDto.getPrimarySkill() !="" ){
				activitySummary.append("Primary Skills").append(" : ").append(" ").append(Constants.NEW_VALUE_TAG_INITIATION).append(candidateDto.getPrimarySkill()).append(Constants.NEW_VALUE_TAG_END).append(" ");
			}else
				activitySummary.append("Primary Skills").append(" : ").append("---").append(" ");
			candidate.setPrimarySkill(candidateDto.getPrimarySkill());

		if(StringUtils.isNotBlank(candidateDto.getVendors())){
			OrgRelations orgRelation = orgRelationsService.getOrRelationsById(Long.parseLong(candidateDto.getVendors()));
			candidate.setOrgRelationId(orgRelation);
			CandidateGlobal vendorCandidate =  getVendorCandidateForCandidateCreation(candidateDto, orgRelation, orgLocations, user);
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
		
		HashSet<CandidateHistory> candiateHistoryList = candidateGlobalService.getCandidateGlobalHistoryDetails(candidate, user);
		candidate.setCandidateHistory(candiateHistoryList);
		
		HashSet<CandidateTimelineActivity> candiateTimeLineActivityList = null;
		
		candidate.setCandidateTimeLineActivity(candiateTimeLineActivityList);
		
		
		if(StringUtils.isNotBlank(candidateDto.getIsAssociate()) && Constants.YES.equalsIgnoreCase(candidateDto.getIsAssociate())){
			candidate.setIsAssociate(Constants.YES);
		}else{
			candidate.setIsAssociate(Constants.NO);
		}
		candidate.setUpdatedBy(user.getUserCode());
		candidate.setUpdatedDate(commonUtil.orgLocationCommonDate(new Date(), orgLocations));
		candidate.setStatus(Constants.CANDIDATE_ACTIVE);
		candidate.setOrgLocationId(orgLocations);
		return candidate;
	}

public void storeCandidateActivity(User user, OrgLocations orgLocations, CandidateGlobal candidate, String actionOn, String actionDesc, String actionSummary, String actionIcon) throws Exception
{
	CandidateTimelineActivity candidateTimelineActivity = new CandidateTimelineActivity();
	
	candidateTimelineActivity.setCandidateGlobalId(candidate);
	candidateTimelineActivity.setOrgLocationId(orgLocations);
	if(user !=null){
		candidateTimelineActivity.setActionBy(user.getFullName());
		candidateTimelineActivity.setCreatedUser(user);
		candidateTimelineActivity.setCreatedBy(user.getUserCode());
		candidateTimelineActivity.setUpdatedBy(user.getUserCode());
	}
	else{
		candidateTimelineActivity.setActionBy(null);
		candidateTimelineActivity.setCreatedUser(null);
		candidateTimelineActivity.setCreatedBy(null);
		candidateTimelineActivity.setUpdatedBy(null);
	}
	candidateTimelineActivity.setActionOn(actionOn);
	candidateTimelineActivity.setActionDesc(actionDesc);
	candidateTimelineActivity.setActionSummary(actionSummary);
	candidateTimelineActivity.setActionIcon(actionIcon);
	candidateTimelineActivity.setStatus(Constants.RECORD_ACTIVE);
	candidateTimelineActivity.setCreatedDate(commonUtil.currentDate());
	candidateTimelineActivity.setUpdatedDate(commonUtil.currentDate());
	candidateTimelineActivityDao.storeCandidateTimelineActivity(candidateTimelineActivity);
}


private void inviteCandidateToSourceLead(User loginUser, CandidateGlobal candidate, OrgLocations orgLocations) throws Exception
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
		 String sourceleadRegisterUrl =StringUtils.removeEnd(sourceleadUrl, sourceleadUrl.substring(sourceleadUrl.lastIndexOf("/")));
     globalVarsMap.put("registerURL",loginBuilder.append("<a href='").append(sourceleadRegisterUrl).append("/register").append("' style='text-decoration:none; '><div style=' width:220px; border-radius:30px; background-color:#214b90; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Register</div></a>").toString());
    
    //calling the sendEmail method of emailScenerioUtil
     commonUtil.sendTemplateEmail(emailMessage, globalVarsMap,orgLocations.getOrg(),orgLocations,Constants.CANDIDATE_INVITATION,new ArrayList<EmailAttachment>());
    
}

public int getCandidatePercentage(CandidateGlobal can, OrgLocations loginOrgLocation) throws Exception
{

	 int percentage = 0;
		if(can != null){
			//Basic Details - 30 %
			if(can.getFirstName() != null  && can.getEmail() != null){
				percentage = 30;
			}
			//Resume  Details - 10 %
			Integer resumesCount = candidateResumesService.getCandidateGlobalActiveResumeCountByCandidateId(can.getId());
			if(resumesCount > 0){
				percentage = percentage +10;
			}
			//Education Details - 10 %
			 Integer educationsCount = candidateEducationService.getCandidateGlobalActiveEducationsCountByCandidateId(can.getId());
			 if(educationsCount > 0){
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
			 Integer documentsCount = candidateDocumentsService.getCandidateGlobalActiveDocumentsByCandidateId(can.getId());
			 if(documentsCount > 0){
					percentage = percentage +10;
			 }
			 
			 can.setCandidateProfilePercentage(percentage);
			 candidateGlobalService.storeCandidateGlobal(can);
		}
		return percentage;
}


public CandidateGlobal getVendorCandidateForCandidateCreation(CandidateDTO candidateDTO,OrgRelations orgRelation,OrgLocations orgLocations,User loginUser) throws Exception{
	CandidateGlobal candidate = null;
	  OrgLocations vendorOrgLocation = null;
	  if(orgRelation.getLocationId().getId().equals(orgLocations.getId())){
		  vendorOrgLocation = orgRelation.getResponderLocationId();
	  }else if(orgRelation.getResponderLocationId().getId().equals(orgLocations.getId())){
		  vendorOrgLocation = orgRelation.getLocationId();
	  }
	  
	  if(StringUtils.isNotBlank(candidateDTO.getEmail())){
		  candidate = candidateGlobalService.findByCandidateGlobalEmail(candidateDTO.getEmail().trim(),vendorOrgLocation.getId());
	  }else if(StringUtils.isNotBlank(candidateDTO.getEmailId())){
		  candidate = candidateGlobalService.findByCandidateGlobalEmail(candidateDTO.getEmailId().trim(),vendorOrgLocation.getId());
	  }
	  
	  if(candidate == null){
		  candidate = new CandidateGlobal();
		  
		  MasterCandidate masterCandidate = null;
			masterCandidate = masterCandidateService.getMasterCandidateByInteligenceCandidateDTO(candidateDTO);
			if(null == masterCandidate){
				masterCandidate = masterCandidateService.storeMasterCandidateByCandidateDTO(candidateDTO, loginUser);
			}
			candidate.setMasterCandidateId(masterCandidate);
		  
			candidate.setOrgLocationId(vendorOrgLocation);
			candidate.setCandidateType("Candidate");
			candidate.setCandidateUUID(SourceLeadRandomCodeHelper.generateRandomUUID());
			
			if(StringUtils.isNotBlank(candidateDTO.getFirstName())){
				candidate.setFirstName(candidateDTO.getFirstName());
			}
		
			if(StringUtils.isNotBlank(candidateDTO.getLastName())){
				candidate.setLastName(candidateDTO.getLastName());
			}
			
			if(StringUtils.isNotBlank(candidateDTO.getEmail())){
				candidate.setEmail(candidateDTO.getEmail());
			}
			
			if(StringUtils.isNotBlank(candidateDTO.getCountry())){
				candidate.setCountry(candidateDTO.getCountry());
			}
		
		candidate.setIsAssociate(Constants.NO);
		candidate.setDesignationType("W-2");
		if(candidateDTO.getWorkLocation() != null) {
			OrgLocations workLocation = new OrgLocations();
			workLocation.setId(candidateDTO.getWorkLocation());
			candidate.setWorkLocation(workLocation);
		}
		candidate.setStatus(1);
		CandidateGlobal savedCandidate = candidateGlobalService.storeCandidateGlobal(candidate);
	    candidateGlobalService.saveOrUpdateCandidateGlobalElkDocument(savedCandidate);
	  }else {
		  if(candidateDTO.getWorkLocation() != null) {
				OrgLocations workLocation = new OrgLocations();
				workLocation.setId(candidateDTO.getWorkLocation());
				candidate.setWorkLocation(workLocation);
			}
		  candidateGlobalService.saveOrUpdateCandidateGlobalElkDocument(candidate);
	  }
	  
	  return candidate;
}



public CandidateGlobal getC2CVendorCandidateGlobal(CandidateGlobal vendorCandidate,OrgRelations orgRelation,OrgLocations orgLocations,User loginUser) throws Exception{
	  CandidateGlobal candidate = null;
	  OrgLocations vendorOrgLocation = null;
	  if(orgRelation.getInitiatorType().equalsIgnoreCase("Client") && orgRelation.getLocationId().getId().equals(orgLocations.getId())){
		  vendorOrgLocation = orgRelation.getResponderLocationId();
	  }else if(orgRelation.getInitiatorType().equalsIgnoreCase("Vendor") && orgRelation.getResponderLocationId().getId().equals(orgLocations.getId())){
		  vendorOrgLocation = orgRelation.getLocationId();
	  }
	  
	  candidate = candidateGlobalService.findByCandidateGlobalEmail(vendorCandidate.getEmail(),vendorOrgLocation.getId());
	  
	  if(candidate == null){
		  candidate = new CandidateGlobal();
		 
			candidate.setMasterCandidateId(vendorCandidate.getMasterCandidateId());
		  
			candidate.setOrgLocationId(vendorOrgLocation);
			candidate.setCandidateType("Candidate");
			candidate.setCandidateUUID(SourceLeadRandomCodeHelper.generateRandomUUID());
			
			candidate.setFirstName(vendorCandidate.getFirstName());
			candidate.setLastName(vendorCandidate.getLastName());
			candidate.setEmail(vendorCandidate.getEmail());
			candidate.setLocation(vendorCandidate.getLocation());
			candidate.setCountry(vendorCandidate.getCountry());
			candidate.setState(vendorCandidate.getState());
			candidate.setCity(vendorCandidate.getCity());
			candidate.setZipCode(vendorCandidate.getZipCode());
			candidate.setMobilePhone(vendorCandidate.getMobilePhone());
			candidate.setAlternateMobilePhone(vendorCandidate.getAlternateMobilePhone());
			candidate.setDesignationType("Full Time");
			candidate.setSkillsName(vendorCandidate.getSkills());
			 
			candidate.setIsAssociate(Constants.NO);
			candidate.setStatus(1);
			CandidateGlobal savedCandidate = candidateGlobalService.storeCandidateGlobal(candidate);
			candidateGlobalService.saveOrUpdateCandidateGlobalElkDocument(savedCandidate);
	  }
	  
	  return candidate;
}



public Candidate storeCandidateFromCandidateGlobal(CandidateGlobal candidateGlobal,User consultantUser) throws Exception {
	
	Candidate candidate = new Candidate();
	Date date = new Date();
	candidate.setCreatedDate(commonUtil.orgLocationCommonDate(date, candidateGlobal.getOrgLocationId()));
	candidate.setCreatedBy(candidateGlobal.getCreatedBy());
	candidate.setCandidateType("Candidate");
	candidate.setConsultantUser(consultantUser);
	candidate.setCandidateUUID(SourceLeadRandomCodeHelper.generateRandomUUID());
	candidate.setIsFavourite(0);
	candidate.setIsFavouriteAssociate(0);
	candidate.setMasterCandidateId(candidateGlobal.getMasterCandidateId());
	candidate.setAadharNumber(candidateGlobal.getAadharNumber());
	candidate.setPanNumber(candidateGlobal.getPanNumber());
	candidate.setSsnNumber(candidateGlobal.getSsnNumber());;
	candidate.setGeneratedId(getCandidateId(candidateGlobal.getOrgLocationId()));
	candidate.setFirstName(candidateGlobal.getFirstName());
	candidate.setLastName(candidateGlobal.getLastName());
	candidate.setMiddelName(candidateGlobal.getMiddelName());
	candidate.setGender(candidateGlobal.getGender());
	candidate.setMaritalStatus(candidateGlobal.getMaritalStatus());
	candidate.setDateOfBirth(candidateGlobal.getDateOfBirth());
	candidate.setCountry(candidateGlobal.getCountry());
	candidate.setState(candidateGlobal.getState());
	candidate.setCity(candidateGlobal.getCity());
	candidate.setLocation(candidateGlobal.getLocation());
	candidate.setZipCode(candidateGlobal.getZipCode());
	candidate.setLatitude(candidateGlobal.getLatitude());
	candidate.setMobilePhone(candidateGlobal.getMobilePhone());
	candidate.setAlternateMobilePhone(candidateGlobal.getAlternateMobilePhone());
	candidate.setEmail(candidateGlobal.getEmail().trim());
	candidate.setCurrentRate(candidateGlobal.getCurrentRate());
	candidate.setCurrentRateType(candidateGlobal.getCurrentRateType());
	candidate.setCurrentRateCurrency(candidateGlobal.getCurrentRateCurrency());
	candidate.setDesignation(candidateGlobal.getDesignation());
	candidate.setExpectedRate(candidateGlobal.getExpectedRate());
	candidate.setRateType(candidateGlobal.getRateType());
	candidate.setExpectedRateCurrencey(candidateGlobal.getExpectedRateCurrencey());
	candidate.setSummary(candidateGlobal.getSummary());
	candidate.setDesignationType(candidateGlobal.getDesignationType());
	candidate.setDesignationOtherType(candidateGlobal.getDesignationOtherType());
	candidate.setExperienceYears(candidateGlobal.getExperienceYears());
	candidate.setExperienceMonths(candidateGlobal.getExperienceMonths());	
	candidate.setNoticePeriod(candidateGlobal.getNoticePeriod());	
	candidate.setOtherNoticePeriod(candidateGlobal.getOtherNoticePeriod());
	candidate.setAddress(candidateGlobal.getAddress());
	candidate.setRecruiterUserId(candidateGlobal.getRecruiterUserId());
	candidate.setSkillsName(candidateGlobal.getSkillsName());
	candidate.setVisaType(candidateGlobal.getVisaType());
	candidate.setPrimarySkill(candidateGlobal.getPrimarySkill());
	candidate.setCandidateGlobalId(candidateGlobal);
	
	if(candidateGlobal.getOrgRelationId() != null) {
		Candidate vendorCandidate = storeVendorCandidateDetailsForCandidateCreation(candidateGlobal.getParentCandidateId().getId(), candidateGlobal.getOrgLocationId().getId());
		candidate.setParentCandidateId(vendorCandidate);
		candidate.setOrgRelationId(candidateGlobal.getOrgRelationId());
	}
	
	if(candidateGlobal.getSpocId() != null) {
		candidate.setSpocId(candidateGlobal.getSpocId());
	}
	
	candidate.setIsAssociate(Constants.YES);
	
	candidate.setUpdatedBy(candidateGlobal.getUpdatedBy());
	candidate.setUpdatedDate(commonUtil.orgLocationCommonDate(new Date(), candidateGlobal.getOrgLocationId()));
	candidate.setStatus(Constants.CANDIDATE_ACTIVE);
	candidate.setOrgLocationId(candidateGlobal.getOrgLocationId());
	candidateDao.storeCandidate(candidate);
	candidateGlobal.setCandidateId(candidateGlobal.getId());
    candidateGlobalService.saveOrUpdateCandidateGlobalElkDocument(candidateGlobal);
	
	List<CandidateEducation> educationsList = candidateEducationService.getCandidateGlobalEducationListByCandidateIdAndOrgLocationID(candidateGlobal.getId(), candidateGlobal.getOrgLocationId().getId());
	if(educationsList != null && !educationsList.isEmpty()) {
		for(CandidateEducation education : educationsList) {
			education.setCandidateId(candidate);
			candidateEducationService.saveCandidateEducation(education);
		}
	}
	
	List<CandidateResumes> resumesList = candidateResumesService.getCandidateGlobalResumesListByCandidateIdAndStatus(candidateGlobal.getId(), Constants.RECORD_ACTIVE);
	if(resumesList != null && !resumesList.isEmpty()) {
		for(CandidateResumes candidateResumes : resumesList) {
			candidateResumes.setCandidateId(candidate);
			candidateResumesService.updateCandidateResume(candidateResumes);
		}
	}
	
	List<CandidateEmployers> employersList = candidateEmployersService.getCandidatesGlobalEmployersListByCandidateIdAndOrgLocationID(candidateGlobal.getId(), candidateGlobal.getOrgLocationId().getId());
	if(employersList != null && !employersList.isEmpty()) {
		for(CandidateEmployers candidateEmployers : employersList) {
			candidateEmployers.setCandidateId(candidate);
			candidateEmployersService.saveCandidateEmployers(candidateEmployers);
		}
	}
	
	List<CandidateHistory> historyList = candidateHistoryService.getCandidateHistoryListByGlobalCandidateId(candidateGlobal.getId());
	if(historyList != null && !historyList.isEmpty()) {
		for(CandidateHistory candidateHistory : historyList) {
			candidateHistory.setCandidateId(candidate);
			candidateHistoryService.storeCandidateHistory(candidateHistory);
		}
	}
	
	List<CandidateTimelineActivity> timeLineActivityList = candidateTimelineActivityDao.getCandidateGlobalTimelineActivities(candidateGlobal.getId(), candidateGlobal.getOrgLocationId().getId());
	if(timeLineActivityList != null && !timeLineActivityList.isEmpty()) {
		for(CandidateTimelineActivity candidateTimelineActivity : timeLineActivityList) {
			candidateTimelineActivity.setCandidate(candidate);
			candidateTimelineActivityDao.storeCandidateTimelineActivity(candidateTimelineActivity);
		}
	}
	
	List<CandidateCertifications> certificationsList = candidateCertificationsDao.getCandidateGlobalCertificationListByCandidateIdAndOrgLocationID(candidateGlobal.getId(), candidateGlobal.getOrgLocationId().getId());
	if(certificationsList != null && !certificationsList.isEmpty()) {
		for(CandidateCertifications candidateCertifications : certificationsList) {
			candidateCertifications.setCandidateId(candidate);
			candidateCertificationsDao.storeCandidateCertifications(candidateCertifications);
		}
	}
	
	List<CandidateDocuments> documentsList = candidateDocumentsDao.getCandidateGlobalDocumentsListByCandidateIdAndOrgLocationID(candidateGlobal.getId(), candidateGlobal.getOrgLocationId().getId());
	if(documentsList != null && documentsList.isEmpty()) {
		for(CandidateDocuments candidateDocuments : documentsList) {
			candidateDocuments.setCandidateId(candidate);
			candidateDocumentsDao.storeCandidateDocuments(candidateDocuments);
		}
	}
	
	List<CandidateComments> commentsList = candidateCommentsDao.getAllCommentsOnCandidateGlobalByCandidateIdAndOrgLocation(candidateGlobal.getId(), candidateGlobal.getOrgLocationId().getId());
	if(commentsList != null && !commentsList.isEmpty()) {
		for(CandidateComments candidateComments : commentsList) {
			candidateComments.setCandidateId(candidate);
			candidateCommentsDao.storeCandidateComments(candidateComments);
		}
		
	}
	
	List<CandidateReferences> referencesList = candidateReferencesDao.getCandidateReferencesListByCandidateGlobalIdAndOrgLocationID(candidateGlobal.getId(), candidateGlobal.getOrgLocationId().getId());
	if(referencesList != null && !referencesList.isEmpty()) {
		for(CandidateReferences candidateReferences : referencesList) {
			candidateReferences.setCandidateId(candidate);
			candidateReferencesDao.storeCandidateReferences(candidateReferences);
		}
	}
	
	List<CandidateCustomFieldData> customFieldsList = candidateCustomFieldDataService.getCandidateCustomFieldDataByCandidateGlobalId(candidateGlobal.getId());
	if(customFieldsList != null && !customFieldsList.isEmpty()) {
		for(CandidateCustomFieldData candidateCustomFieldData : customFieldsList) {
			candidateCustomFieldData.setCandidate(candidate);
			candidateCustomFieldDataService.savejobCustomFieldData(candidateCustomFieldData);
		}
	}
	return candidate;
}


public Candidate storeVendorCandidateDetailsForCandidateCreation(Long globalCandidateId,Long orgLocationId) throws Exception{
	CandidateGlobal candidateGlobal = candidateGlobalService.getCandidateGlobalByCandidateId(globalCandidateId);
	Candidate candidate = null;
	OrgRelations orgRelation = orgRelationsDao.getOrgRelationById(candidateGlobal.getOrgRelationId().getId());
	  OrgLocations vendorOrgLocation = null;
	  if(orgRelation.getLocationId().getId().equals(orgLocationId)){
		  vendorOrgLocation = orgRelation.getResponderLocationId();
	  }else if(orgRelation.getResponderLocationId().getId().equals(orgLocationId)){
		  vendorOrgLocation = orgRelation.getLocationId();
	  }
	  
	  candidate = candidateDao.findByCandidateEmail(candidateGlobal.getEmail().trim(),vendorOrgLocation.getId());
	  
	  if(candidate == null){
		  candidate = new Candidate();
			candidate.setMasterCandidateId(candidateGlobal.getMasterCandidateId());
			candidate.setOrgLocationId(vendorOrgLocation);
			candidate.setCandidateType("Candidate");
			candidate.setCandidateUUID(SourceLeadRandomCodeHelper.generateRandomUUID());
			
			if(StringUtils.isNotBlank(candidateGlobal.getFirstName())){
				candidate.setFirstName(candidateGlobal.getFirstName());
			}
		
			if(StringUtils.isNotBlank(candidateGlobal.getLastName())){
				candidate.setLastName(candidateGlobal.getLastName());
			}
			
			if(StringUtils.isNotBlank(candidateGlobal.getEmail())){
				candidate.setEmail(candidateGlobal.getEmail());
			}
			
			if(StringUtils.isNotBlank(candidateGlobal.getCountry())){
				candidate.setCountry(candidateGlobal.getCountry());
			}
		
		candidate.setIsAssociate(Constants.NO);
		candidate.setDesignationType("W-2");
				candidate.setStatus(1);
		candidate = candidateDao.storeCandidate(candidate);
		candidateGlobal.setCandidateId(candidate.getId());
		candidateGlobalService.saveOrUpdateCandidateGlobalElkDocument(candidateGlobal);
	  }
	  
	  return candidate;
}



public CandidateGlobal storeCandidateGlobalFromCandidate(Candidate candidate) throws Exception {
	
	CandidateGlobal candidateGlobal = candidateGlobalService.findByCandidateGlobalEmail(candidate.getEmail(), candidate.getOrgLocationId().getId());
	if(candidateGlobal == null) {
		candidateGlobal = new CandidateGlobal();
	}
	Date date = new Date();
	candidateGlobal.setCreatedDate(commonUtil.orgLocationCommonDate(date, candidate.getOrgLocationId()));
	candidateGlobal.setCreatedBy(candidate.getCreatedBy());
	candidateGlobal.setCandidateType("Candidate");
	candidateGlobal.setConsultantUser(candidate.getConsultantUser());
	candidateGlobal.setCandidateUUID(SourceLeadRandomCodeHelper.generateRandomUUID());
	candidateGlobal.setIsFavourite(0);
	candidateGlobal.setIsFavouriteAssociate(0);
	candidateGlobal.setMasterCandidateId(candidate.getMasterCandidateId());
	candidateGlobal.setAadharNumber(candidate.getAadharNumber());
	candidateGlobal.setPanNumber(candidate.getPanNumber());
	candidateGlobal.setSsnNumber(candidate.getSsnNumber());;
	candidateGlobal.setGeneratedId(getCandidateId(candidate.getOrgLocationId()));
	candidateGlobal.setFirstName(candidate.getFirstName());
	candidateGlobal.setLastName(candidate.getLastName());
	candidateGlobal.setMiddelName(candidate.getMiddelName());
	candidateGlobal.setGender(candidate.getGender());
	candidateGlobal.setMaritalStatus(candidate.getMaritalStatus());
	candidateGlobal.setDateOfBirth(candidate.getDateOfBirth());
	candidateGlobal.setCountry(candidate.getCountry());
	candidateGlobal.setState(candidate.getState());
	candidateGlobal.setCity(candidate.getCity());
	candidateGlobal.setLocation(candidate.getLocation());
	candidateGlobal.setZipCode(candidate.getZipCode());
	candidateGlobal.setLatitude(candidate.getLatitude());
	candidateGlobal.setMobilePhone(candidate.getMobilePhone());
	candidateGlobal.setAlternateMobilePhone(candidate.getAlternateMobilePhone());
	candidateGlobal.setEmail(candidate.getEmail().trim());
	candidateGlobal.setCurrentRate(candidate.getCurrentRate());
	candidateGlobal.setCurrentRateType(candidate.getCurrentRateType());
	candidateGlobal.setCurrentRateCurrency(candidate.getCurrentRateCurrency());
	candidateGlobal.setDesignation(candidate.getDesignation());
	candidateGlobal.setExpectedRate(candidate.getExpectedRate());
	candidateGlobal.setRateType(candidate.getRateType());
	candidateGlobal.setExpectedRateCurrencey(candidate.getExpectedRateCurrencey());
	candidateGlobal.setSummary(candidate.getSummary());
	candidateGlobal.setDesignationType(candidate.getDesignationType());
	candidateGlobal.setDesignationOtherType(candidate.getDesignationOtherType());
	candidateGlobal.setExperienceYears(candidate.getExperienceYears());
	candidateGlobal.setExperienceMonths(candidate.getExperienceMonths());	
	candidateGlobal.setNoticePeriod(candidate.getNoticePeriod());	
	candidateGlobal.setOtherNoticePeriod(candidate.getOtherNoticePeriod());
	candidateGlobal.setAddress(candidate.getAddress());
	candidateGlobal.setRecruiterUserId(candidate.getRecruiterUserId());
	candidateGlobal.setSkillsName(candidate.getSkillsName());
	candidateGlobal.setVisaType(candidate.getVisaType());
	candidateGlobal.setPrimarySkill(candidate.getPrimarySkill());
	candidateGlobal.setOrgRelationId(candidate.getOrgRelationId());
	candidateGlobal.setSpocId(candidate.getSpocId());
	candidateGlobal.setIsAssociate(Constants.YES);
	
	candidateGlobal.setUpdatedBy(candidate.getUpdatedBy());
	candidateGlobal.setUpdatedDate(candidate.getUpdatedDate());
	candidateGlobal.setStatus(candidate.getStatus());
	candidateGlobal.setOrgLocationId(candidate.getOrgLocationId());
	candidateGlobal.setProfilePercentage(30);
	
	candidateGlobalService.storeCandidateGlobal(candidateGlobal);
	
	return candidateGlobal;
}

}
