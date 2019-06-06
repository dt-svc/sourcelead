package com.bis.sourcelead.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TimeZone;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.CandidateCertificationsDao;
import com.bis.sourcelead.dao.CandidateDao;
import com.bis.sourcelead.dao.CandidateEducationDao;
import com.bis.sourcelead.dao.CandidateEmployersDao;
import com.bis.sourcelead.dao.CandidateTimelineActivityDao;
import com.bis.sourcelead.dao.EmployeesDao;
import com.bis.sourcelead.dao.OrgLocationsDao;
import com.bis.sourcelead.dao.ReferContactsDao;
import com.bis.sourcelead.dao.RolesDao;
import com.bis.sourcelead.dao.UserDao;
import com.bis.sourcelead.dto.AddAssociateDTO;
import com.bis.sourcelead.dto.AddUserDTO;
import com.bis.sourcelead.dto.AssociateDTO;
import com.bis.sourcelead.dto.CandidateCertificationDTO;
import com.bis.sourcelead.dto.CandidateDTO;
import com.bis.sourcelead.dto.CandidateEducationDTO;
import com.bis.sourcelead.dto.CandidateEmployerDTO;
import com.bis.sourcelead.dto.CandidateMarketPlaceDTO;
import com.bis.sourcelead.dto.CustomFormFieldDTO;
import com.bis.sourcelead.dto.FileInfo;
import com.bis.sourcelead.dto.MarketplacePurchaseSalesDTO;
import com.bis.sourcelead.dto.MarketplaceWalletDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.elk.index.CandidateIndex;
import com.bis.sourcelead.elk.index.helper.CandidateIndexHelper;
import com.bis.sourcelead.elk.setup.SourceleadELKBaseIndexer;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.CandidateCertifications;
import com.bis.sourcelead.entity.CandidateCustomFieldData;
import com.bis.sourcelead.entity.CandidateEducation;
import com.bis.sourcelead.entity.CandidateEmployers;
import com.bis.sourcelead.entity.CandidateHistory;
import com.bis.sourcelead.entity.CandidateImportLog;
import com.bis.sourcelead.entity.CandidateMarketCart;
import com.bis.sourcelead.entity.CandidateMarketPlace;
import com.bis.sourcelead.entity.CandidateMarketPlaceCartView;
import com.bis.sourcelead.entity.CandidateMarketPlacePaymentTransactionHistory;
import com.bis.sourcelead.entity.CandidateMarketWallet;
import com.bis.sourcelead.entity.CandidateResumes;
import com.bis.sourcelead.entity.CandidateTimelineActivity;
import com.bis.sourcelead.entity.Countries;
import com.bis.sourcelead.entity.EmailAttachment;
import com.bis.sourcelead.entity.EmailMessage;
import com.bis.sourcelead.entity.Employees;
import com.bis.sourcelead.entity.MarketplacePurchaseOrder;
import com.bis.sourcelead.entity.MarketplaceSalesOrder;
import com.bis.sourcelead.entity.MasterCandidate;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgRelations;
import com.bis.sourcelead.entity.OrgSpoc;
import com.bis.sourcelead.entity.OrglocationFormFields;
import com.bis.sourcelead.entity.RecruiterReportCandidateCountView;
import com.bis.sourcelead.entity.ReferContacts;
import com.bis.sourcelead.entity.Roles;
import com.bis.sourcelead.entity.SubmissionCandidateListView;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserRole;
import com.bis.sourcelead.enums.Status;
import com.bis.sourcelead.service.CandidateCustomFieldDataService;
import com.bis.sourcelead.service.CandidateDocumentsService;
import com.bis.sourcelead.service.CandidateEducationService;
import com.bis.sourcelead.service.CandidateEmployersService;
import com.bis.sourcelead.service.CandidateHistoryService;
import com.bis.sourcelead.service.CandidateResumesService;
import com.bis.sourcelead.service.CandidateService;
import com.bis.sourcelead.service.CandidateTimelineActivityService;
import com.bis.sourcelead.service.ClientService;
import com.bis.sourcelead.service.CountriesService;
import com.bis.sourcelead.service.EmailScenarioService;
import com.bis.sourcelead.service.MasterCandidateService;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.OrgRelationsService;
import com.bis.sourcelead.service.OrgSpocService;
import com.bis.sourcelead.service.OrglocationFormFieldsService;
import com.bis.sourcelead.service.ProjectsService;
import com.bis.sourcelead.service.RequirementSubmissionService;
import com.bis.sourcelead.service.StatesService;
import com.bis.sourcelead.service.UserRoleService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.CandidateActivityCommonUtil;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.EmailScenerioUtil;
import com.bis.sourcelead.util.FileUploadUtil;
import com.bis.sourcelead.util.PdfUtil;
import com.bis.sourcelead.util.ReportsUtil;
import com.bis.sourcelead.util.SourceLeadRandomCodeHelper;
import com.bis.sourcelead.util.UniversalSearchHelper;
import com.bis.sourcelead.web.RegistrationController;
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.emory.mathcs.backport.java.util.Arrays;

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
	UserDao userDao;

	@Autowired
	OrgLocationsDao orgLocationsDao;
	
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
    
    @Autowired
    RegistrationController registrationController;
    
    @Autowired
    ClientService clientService;
    
    @Autowired
    RolesDao rolesDao;
    
    @Autowired
    EmployeesDao employeeDao;
    
    @Autowired
    private OrglocationFormFieldsService orglocationFormFieldsService;
    
    @Autowired
    private CandidateCustomFieldDataService candidateCustomFieldDataService;
    
    @Autowired
	ReferContactsDao referContactsDao;
    
    @Value("${static.path}")
	private String staticPath;
    
    @Autowired
    CountriesService countriesService;
    
    @Autowired
    StatesService statesService;
    
    @Autowired
    CandidateHistoryService candidateHistoryService;
    
    @Autowired
    CandidateTimelineActivityService candidateTimelineActivityService;
    
    @Autowired
    CandidateCertificationsDao candidateCertificationsDao;
    
    @Autowired
    CandidateEmployersDao candidateEmployersDao;

    @Autowired
    CandidateEducationDao candidateEducationDao;
    
    @Autowired
	ReportsUtil reportsUtil;
       
    protected boolean isNullOrEmpty(String str) {
		return (str == null || "".equals(str.trim()));
	}
    
    public void storeCandidateImportLog(CandidateImportLog candidateImportLog) 
    {
    		candidateDao.storeCandidateImportLog(candidateImportLog);
    }
    
    public List<CandidateImportLog> getCandidateImportLog(String uploadId, boolean all) 
    {
    		return candidateDao.getCandidateImportLog(uploadId, all);
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
        Date date = new Date();
		if(StringUtils.isNotBlank(candidateDto.getCandidateId())){
			candidate = candidateDao.getCandidateByCandidateId(Long.parseLong(candidateDto.getCandidateId()));
		}else{	
			
			candidate = new Candidate();
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
        
		if("India".equalsIgnoreCase(orgLocations.getCountry())) {
			candidate.setAadharNumber(candidateDto.getAadharNumber());
			candidate.setPanNumber(candidateDto.getPanNumber());
			}
		
		if("United States".equalsIgnoreCase(orgLocations.getCountry())) {
			candidate.setSsnNumber(candidateDto.getSsnNumber());;
			}
		candidate = getCandidateDetails(candidate, candidateDto, user, orgLocations, activitySummary);
		
		HashSet<CandidateEducation>	 candidateEducationList = candidateEducationService.getCandidateEducationDetails(candidateDto, orgLocations,candidate,user.getUserCode());
		candidate.setCandidateEducation(candidateEducationList);
		
		HashSet<CandidateEmployers> candidateEmployersList = candidateEmployersService.getCandidateEmployersDetails(candidateDto,orgLocations,candidate,user.getUserCode());
		candidate.setCandidateEmployers(candidateEmployersList);
		
		if(candidateDto.getFileInfo()!= null && !candidateDto.getFileInfo().isEmpty()){
			FileInfo fileInfo = candidateDto.getFileInfo().get(0);
			if(fileInfo != null){
			HashSet<CandidateResumes> candidateResumesList = candidateResumesService.getCandidateResumesDetails(orgLocations,user.getUserCode(),candidate, fileInfo);
			candidate.setCandidateResumes(candidateResumesList);
				}
			}
		Candidate savedCandidate = candidateDao.storeCandidate(candidate);
		
		if(candidateDto.getCustomFormData() !=null) {
			for( CustomFormFieldDTO customFields:candidateDto.getCustomFormData()) {
				if(customFields != null && customFields.getCustomFormFieldId() > 0 ) {
					CandidateCustomFieldData candidateCustomFields = null;
					if(customFields.getEntityId()!= null && customFields.getEntityId() > 0) {
						candidateCustomFields = candidateCustomFieldDataService.getCandidateCustomFieldDataById(customFields.getEntityId());
					}else {
						candidateCustomFields = new CandidateCustomFieldData();
					}
				candidateCustomFields.setCandidate(savedCandidate);
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
		saveOrUpdateCandidateElkDocument(savedCandidate);
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
		
		// resume details update in elk
		if(candidateDto.getFileInfo()!= null && !candidateDto.getFileInfo().isEmpty()){
		candidateResumesService.saveOrUpdateCandidateElkDocumentWithResumeDetails(savedCandidate);
		}
		if(candidateDto.getFileInfo()!= null && !candidateDto.getFileInfo().isEmpty()){
			FileInfo fileInfo = candidateDto.getFileInfo().get(0);
			if(fileInfo != null){
			FileUploadUtil.moveFile(candidateDto.getStaticPath(), fileInfo, "resumes");
				}
			}
		return savedCandidate;
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
 		 String sourceleadRegisterUrl =StringUtils.removeEnd(sourceleadUrl, sourceleadUrl.substring(sourceleadUrl.lastIndexOf("/")));
         globalVarsMap.put("registerURL",loginBuilder.append("<a href='").append(sourceleadRegisterUrl).append("/register").append("' style='text-decoration:none; '><div style=' width:220px; border-radius:30px; background-color:#214b90; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Register</div></a>").toString());
        
        //calling the sendEmail method of emailScenerioUtil
         commonUtil.sendTemplateEmail(emailMessage, globalVarsMap,orgLocations.getOrg(),orgLocations,Constants.CANDIDATE_INVITATION,new ArrayList<EmailAttachment>());
        
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
	public List<Candidate> getCandidatesListByOrgLocationID(Long orgLocationId,Long orgId,Integer candidateStatus,String updatedDateRange,String candidateType,String isAssociate,String candidateFilterSkills,String availability,String candidateTypeFilter,String createdByFilter,User user) throws Exception {
		List<Candidate> candidateList = candidateDao.getCandidatesListByOrgLocationID(orgLocationId,orgId,candidateStatus,updatedDateRange,candidateType,isAssociate,candidateFilterSkills,availability,candidateTypeFilter,createdByFilter,user);
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
						ArrayList<QueryBuilder> QueryBuilderList = universalSearchHelper.getQueryBuilderListForCandidate(keywd);
						if(QueryBuilderList != null && !QueryBuilderList.isEmpty()){
							for(QueryBuilder QueryBuilder :QueryBuilderList){
								anyBoolSubQueryBuilder.should(QueryBuilder);
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
			
			if(StringUtils.isNotBlank(candidateDTO.getMiddleName())){
				candidate.setMiddelName(candidateDTO.getMiddleName());
			}else{
				candidate.setMiddelName(null);
			}
			
			if(StringUtils.isNotBlank(candidateDTO.getGender())){
				candidate.setGender(candidateDTO.getGender());
			}else{
				candidate.setGender(null);
			}
			
			if(StringUtils.isNotBlank(candidateDTO.getMaritalStatus())){
				candidate.setMaritalStatus(candidateDTO.getMaritalStatus());
			}else{
				candidate.setMaritalStatus(null);
			}
			
			if(StringUtils.isNotBlank(candidateDTO.getDateofBirth())){
				candidate.setDateOfBirth(commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(), candidateDTO.getDateofBirth()));
			}else{
				candidate.setDateOfBirth(null);
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
			
			HashSet<CandidateEducation>	 candidateEducationList = candidateEducationService.getCandidateEducationDetails(candidateDTO, orgLocations,candidate,user.getUserCode());
			candidate.setCandidateEducation(candidateEducationList);
			
			HashSet<CandidateEmployers> candidateEmployersList = candidateEmployersService.getCandidateEmployersDetails(candidateDTO,orgLocations,candidate,user.getUserCode());
			candidate.setCandidateEmployers(candidateEmployersList);
			
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
			
			/*
			 * Saving Candidate DB data as ElasticSearch Documents
			 */
			saveOrUpdateCandidateElkDocument(savedCandidate);
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
	public BigInteger getCountOfAllCandidatesByCreatedUserAndOrglocationId(Long orgLocationId, User user, String startDateUTC, String endDateUTC,String isGlobalUser,Long orgId) throws Exception {
		return candidateDao.getCountOfAllCandidatesByCreatedUserAndOrglocationId(orgLocationId, user, startDateUTC,endDateUTC,isGlobalUser,orgId);
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
	public Candidate storeCandidateForUsers(CandidateDTO candidateDTO,AddUserDTO addUserDTO,User candidateUser,OrgLocations orgLocations,MasterCandidate masterCandidate,User loginUser) throws Exception
	{
		Candidate candidate = candidateDao.getCandidateByConsultantUserId(candidateUser.getId(), orgLocations.getId());
		if(null == candidate){
			candidate = new Candidate();
			candidate.setConsultantUser(candidateUser);
			candidate.setMasterCandidateId(masterCandidate);
			candidate.setOrgLocationId(orgLocations);
			candidate.setCandidateType(Constants.CANDIDATE);
			candidate.setFirstName(candidateUser.getFirstName());
			candidate.setLastName(candidateUser.getLastName());
			candidate.setMiddelName(candidateUser.getMiddelName());
			candidate.setGender(candidateUser.getGender());
			candidate.setCountry(orgLocations.getCountry());
			candidate.setIsAssociate(Constants.YES);
			candidate.setEmail(candidateUser.getEmail());
			candidate.setStatus(Constants.RECORD_ACTIVE);
			candidate.setCreatedBy(loginUser.getUserCode());
			candidate.setCandidateProfilePercentage(Constants.MIN_PROFILE_PERCENETAGE);
			
			if(StringUtils.isNotBlank(addUserDTO.getRate())){
				candidate.setCurrentRate(addUserDTO.getRate());
			}
			if(StringUtils.isNotBlank(addUserDTO.getRatePer())){
				candidate.setCurrentRateType(addUserDTO.getRatePer());
			}
			
			if(StringUtils.isNotBlank(addUserDTO.getCurrentCurrency())){
				candidate.setCurrentRateCurrency(addUserDTO.getCurrentCurrency());
			}
			
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
			
			/*if(StringUtils.isNotBlank(candidateDTO.getDesignationOtherType())){
				candidate.setDesignationOtherType(candidateDTO.getDesignationOtherType());
			}else
			{
				candidate.setDesignationOtherType(null);
			}*/
			
			if(StringUtils.isNotBlank(candidateDTO.getDesignation())){
				candidate.setDesignation(candidateDTO.getDesignation());
			}else
			{
				candidate.setDesignation(null);
			}
			
			 if(StringUtils.isNotBlank(addUserDTO.getWorkLocation())){
				    OrgLocations workLocation = orgLocationService.getOrgLocations(Long.parseLong(addUserDTO.getWorkLocation()));
					candidate.setWorkLocation(workLocation);
			 }else {
			    	candidate.setWorkLocation(orgLocations);
			 }
			
			if(StringUtils.isNotBlank(candidateDTO.getVendors())){
				if(candidate.getWorkLocation() != null) {
					candidateDTO.setWorkLocation(candidate.getWorkLocation().getId());
				}
				OrgRelations orgRelation = orgRelationsService.getOrRelationsById(Long.parseLong(candidateDTO.getVendors()));
				candidate.setOrgRelationId(orgRelation);
				Candidate vendorCandidate =  commonUtil.getVendorCandidateForCandidateCreation(candidateDTO, orgRelation, orgLocations, candidateUser);
				candidate.setParentCandidateId(vendorCandidate);
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
			if(orgLocations.getGeneratedUserId() != null && !orgLocations.getGeneratedUserId().isEmpty()) {
				candidate.setGeneratedId(getCandidateId(orgLocations));
			}
			Date date= new Date();
			candidate.setCreatedDate(commonUtil.orgLocationCommonDate(date, orgLocations));
			candidate.setUpdatedDate(commonUtil.orgLocationCommonDate(date, orgLocations));
		}else{
			candidate.setConsultantUser(candidateUser);
			candidate.setMasterCandidateId(masterCandidate);
		}
		if(candidateUser.getDateOfBirth() != null){
			candidate.setDateOfBirth(commonUtil.orgLocationCommonDate(candidateUser.getDateOfBirth(),orgLocations));
			}
		if(StringUtils.isNotBlank(candidateDTO.getPanNumber())){
			candidate.setPanNumber(candidateDTO.getPanNumber());
		}else{
			candidate.setPanNumber(null);
		}
		
		if(StringUtils.isNotBlank(addUserDTO.getSsn())) {
			candidate.setSsnNumber(addUserDTO.getSsn());
	    }
		
		if(StringUtils.isNotBlank(candidateDTO.getAadharNumber())){
			candidate.setAadharNumber(candidateDTO.getAadharNumber());
		}else{
			candidate.setAadharNumber(null);
		}
		if(StringUtils.isNotBlank(candidateUser.getMaritalStatus())) {
			candidate.setMaritalStatus(candidateUser.getMaritalStatus());
		}
		  
	    if(StringUtils.isNotBlank(addUserDTO.getResidenceAddress())){
	    	candidate.setResidenceAddress(addUserDTO.getResidenceAddress());
	    	candidate.setResidenceCountry(addUserDTO.getResidenceCountry());
	    	candidate.setResidenceState(addUserDTO.getResidenceState());
	    	candidate.setResidenceCity(addUserDTO.getResidenceCity());
	    	candidate.setResidenceLatitude(addUserDTO.getResidenceLatitude());
	    	candidate.setResidenceLongitude(addUserDTO.getResidenceLongitude());
	    	candidate.setResidenceZipCode(addUserDTO.getResidenceZipCode());
	    	candidate.setResidenceStreetAddress(addUserDTO.getResidenceAddress());
	    	
	    	if(StringUtils.isNotBlank(addUserDTO.getResidenceState()) && StringUtils.isNotBlank(addUserDTO.getResidenceCountry())) {
	    		Countries country = countriesService.getCountriesByCountryName(addUserDTO.getResidenceCountry());
		    	String stateCode = statesService.getStateCodeByCountryCodeAndState(country.getCode2(),addUserDTO.getResidenceState());
		    	if(stateCode != null) {
		    		candidate.setResidenceStateCode(stateCode);
		    	}
	    	}
	    	
	    }
		candidateDao.storeCandidate(candidate);
		saveOrUpdateCandidateElkDocument(candidate);
		return candidate;
	}

	@Override
	public int getCandidatePercentage(Candidate can, OrgLocations loginOrgLocation) throws Exception
	{

		 int percentage = 0;
			if(can != null){
				//Basic Details - 30 %
				if(can.getFirstName() != null  && can.getEmail() != null){
					percentage = 30;
				}
				//Resume  Details - 10 %
				Integer resumesCount = candidateResumesService.getCandidateActiveResumeCountByCandidateId(can.getId());
				if(resumesCount > 0){
					percentage = percentage +10;
				}
				//Education Details - 10 %
				 Integer educationsCount = candidateEducationService.getCandidateActiveEducationsCountByCandidateId(can.getId());
				 if(educationsCount > 0){
						percentage = percentage +10;
				 }
				//Employment  Details - 10 %
				 Integer employersCount = candidateEmployersService.getCandidateActiveEmployersCountByCandidateId(can.getId());
				 if(employersCount > 0){
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
				 Integer documentsCount = candidateDocumentsService.getCandidateActiveDocumentsByCandidateId(can.getId());
				 if(documentsCount > 0){
						percentage = percentage +10;
				 }
				 
				 can.setCandidateProfilePercentage(percentage);
				 candidateDao.storeCandidate(can);
			}
			return percentage;
	}


	@Override
	public ResponseList<CandidateDTO>  getRequestedCandidatesList(CandidateDTO candidateDTO,Long orgLocationsId, List<Long> roleIds,List<Long> teamIds,
			String resourceType,List<Long> associatesIds,Long projectId) throws Exception {
		return candidateDao.getRequestedCandidatesList(candidateDTO,orgLocationsId, roleIds,teamIds, resourceType,associatesIds,projectId);
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


	@Override
	public ResponseList<CandidateDTO> getCandidateListByOrgLocationIdAndOrgRelationId(Long orgLocationId, Long orgRelationId) throws Exception
	{
		
		ResponseList<CandidateDTO> canList = new ResponseList<>();
		List<CandidateDTO> candtoList = new ArrayList<>();
		
		List<Candidate> candidateList = candidateDao.getCandidatesListByOrgLocationIdAndOrgRelationId(orgLocationId, orgRelationId);
		OrgLocations orgLocations = orgLocationService.getOrgLocations(orgLocationId);
		//SimpleDateFormat simpleDateFormat = orgLocations.getLocFullFormDateFormatter();
		for (Candidate candidate : candidateList) {
			
			CandidateDTO canDTO = new CandidateDTO();
			
			StringBuilder sb = new StringBuilder();
			if(candidate.getLastName() != null){
			canDTO.setCandidateName(sb.append(candidate.getFirstName()).append(" ").append(candidate.getLastName()).toString());
			}else{
				canDTO.setCandidateName(candidate.getFirstName());
			}
			canDTO.setEmail(candidate.getEmail());
			
			canDTO.setCountry(candidate.getCountry());
			canDTO.setState(candidate.getState());
			canDTO.setCity(candidate.getCity());
			
			canDTO.setCandidateUpdateDateToString(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),candidate.getUpdatedDate()));
			canDTO.setPrimarySkill(candidate.getPrimarySkill());
			StringBuilder stringBuilder = new StringBuilder();
			canDTO.setVendorContactName(stringBuilder.append(candidate.getSpocId().getVendorUser().getFirstName()).append(" ").append(candidate.getSpocId().getVendorUser().getLastName()).toString());
			
			CandidateResumes candidateResumes = candidateResumesService.getCandidateResumesListByCandidateId(candidate.getId());
			if(candidateResumes != null){
				canDTO.setCandidateResumeName(candidateResumes.getFileName());
				canDTO.setCandidateResumeFileType(candidateResumes.getFileContentType());
				canDTO.setFileUrl(candidateResumes.getFileURL());
			}
			
			if(candidate.getConsultantUser() != null){
				Integer assignedProjectsCount = projectsService.getUserAssignedProjectsCount(candidate.getConsultantUser().getId(), orgLocationId);
				canDTO.setAssignedProjectsCount(assignedProjectsCount);
			}
			candtoList.add(canDTO);
		}
		canList.setData(candtoList);
		return canList;
	}
	
	@Override
	public Candidate getCandidateByShortURL(String shortURL) throws Exception
	{
		return candidateDao.getCandidateByShortURL(shortURL);
	}

	@Override
	public Integer getCandidatesCountByLocation(Long orgLocationId) throws Exception {
		return candidateDao.getCandidatesCountByLocation(orgLocationId);
	}

	@Override
	public List<Candidate> getAllAssociatesByOrglocationIdAndEmploymentType(Long orgLocationId, String employeementType)
			throws Exception {
		return candidateDao.getAllAssociatesByOrglocationIdAndEmploymentType(orgLocationId, employeementType);
	}

	@Override
	public ResponseList<CandidateDTO> getLocationCandidatesList(CandidateDTO candidateDTO, Long orgLocationsId,
			List<Long> roleIds, List<Long> associatesIds,Long loginOrgLocationId) throws Exception {
		return candidateDao.getLocationCandidatesList(candidateDTO, orgLocationsId, roleIds, associatesIds,loginOrgLocationId);
	}

	@Override
	public void saveOrglocationAsssociate(AddAssociateDTO addAssociateDTO, User user, OrgLocations orgLocations,Org org)
			throws Exception {
		
		if(addAssociateDTO.getLocationAssociates() != null && !addAssociateDTO.getLocationAssociates().isEmpty()){
			
			for(AssociateDTO associateDTO : addAssociateDTO.getLocationAssociates()){
				if(associateDTO.getCandidateId() != null) {
					Candidate candidate = candidateDao.getCandidateByCandidateId(associateDTO.getCandidateId());
					OrgSpoc orgSpoc = null;
					if(candidate != null) {
						
						Candidate vendorCandidate = null;
						vendorCandidate = candidateDao.findByCandidateEmail(candidate.getEmail(), orgLocations.getId());
						if(vendorCandidate == null) {
							vendorCandidate = new Candidate();
							MasterCandidate masterCandidate = new MasterCandidate();
							masterCandidate.setId(candidate.getMasterCandidateId().getId());
							if(candidate.getWorkLocation() != null) {
								vendorCandidate.setWorkLocation(candidate.getWorkLocation());
							}
							vendorCandidate.setMasterCandidateId(masterCandidate);
							vendorCandidate.setParentCandidateId(candidate);
							vendorCandidate.setConsultantUser(candidate.getConsultantUser());
							vendorCandidate.setSpocId(orgSpoc);
							vendorCandidate.setCandidateType("Candidate");
							vendorCandidate.setFirstName(candidate.getFirstName());
							vendorCandidate.setLastName(candidate.getLastName());
							vendorCandidate.setEmail(candidate.getEmail());
							vendorCandidate.setCountry(candidate.getCountry());
							vendorCandidate.setOrgLocationId(orgLocations);
							vendorCandidate.setIsAssociate(Constants.YES);
							vendorCandidate.setDesignationType("Offshore C2C");
							vendorCandidate.setGeneratedId(getCandidateId(orgLocations));
							
							if(associateDTO.getBillRate() != null){
								vendorCandidate.setCurrentRate(associateDTO.getBillRate().toString());
							}
							if(StringUtils.isNotBlank(associateDTO.getBillRateRateType())){
								vendorCandidate.setCurrentRateType(associateDTO.getBillRateRateType());
							}
							
							if(StringUtils.isNotBlank(associateDTO.getBillRateCurrency())){
								vendorCandidate.setCurrentRateCurrency(associateDTO.getBillRateCurrency());
							}
							
							vendorCandidate.setStatus(1);
							vendorCandidate.setIsAssociate(Constants.YES);
							vendorCandidate.setCandidateUUID(SourceLeadRandomCodeHelper.generateRandomUUID());
							vendorCandidate.setCreatedBy(user.getUserCode());
							vendorCandidate.setCreatedDate(new Date());
							vendorCandidate.setUpdatedDate(new Date());
							// by default 30 %
							vendorCandidate.setCandidateProfilePercentage(30);
							
							Candidate savedCandidate = candidateDao.storeCandidate(vendorCandidate);
	    					saveOrUpdateCandidateElkDocument(savedCandidate);
	    					
	    					UserRole userRole = new UserRole();
	    					userRole.setUser(candidate.getConsultantUser());
	    					userRole.setIsMailSent(Status.Y);
	    					userRole.setOrgLocations(orgLocations);
	    					userRole.setUserState(Status.NOT_LOGGEDIN);
	    					Roles roles = rolesDao.getRolesById(associateDTO.getAssociateRole());
	    					userRole.setRoles(roles);
	    					userRole.setUserType(roles.getDisplayName());
	    					User managerUser = userService.getUserById(associateDTO.getAssociateManager());
	    					userRole.setReportingManager(managerUser);
	    					userRole.setResourceManager(managerUser);
	    					userRole.setIsPrimary(0);
	    					userRole.setOrgId(org);
	    					userRole.setOrgLocations(orgLocations);
	    					userRole.setIsMailSent(Status.Y);
	    					
	    					userRole.setCreatedDate(new Date());
	    					userRole.setCreatedBy(user.getUserCode());
	    					userRoleService.addUserRole(userRole);
	    					
	    					Employees employee = new Employees();
	    					employee.setUser(candidate.getConsultantUser());
	    					employee.setOrgLocations(orgLocations);
	    					employee.setCreatedBy(user.getUserCode());
	    					employee.setHireDate(new Date());
	    					employee.setUserEmploymentType("Offshore C2C");
	    					employee.setSpocId(orgSpoc);
	    					
	    					if(associateDTO.getBillRate() != null){
	    						employee.setRate(associateDTO.getBillRate().toString());
							}
							if(StringUtils.isNotBlank(associateDTO.getBillRateRateType())){
								employee.setRatePer(associateDTO.getBillRateRateType());
							}
							
							if(StringUtils.isNotBlank(associateDTO.getBillRateCurrency())){
								employee.setCurrentCurrency(associateDTO.getBillRateCurrency());
							}
							
	    					employee.setCreatedDate(new Date());
	    					employee.setUpdatedDate(new Date());
	    					employeeDao.addEmployee(employee);
						}
					}
				}
			}
		}
		
	}
	
	

	@Override
	public Candidate getCandidateByEmailAndOrgLocationId(String email, Long orgLocationId) throws Exception {
		return candidateDao.getCandidateByEmailAndOrgLocationId(email, orgLocationId);
	}

	@Override
	public Candidate getCandidateByEmail(String email) throws Exception {
		return candidateDao.getCandidateByEmail(email);
	}

	@Override
	public Candidate getCandidateByParentCandidateIdAndOrgLocationId(Long parentCandidateId, Long orgLocationId)
			throws Exception {
		return candidateDao.getCandidateByParentCandidateIdAndOrgLocationId(parentCandidateId, orgLocationId);
	}

	@Override
	public List<RecruiterReportCandidateCountView> getCandidateCountsCreatedUserList(Long orgLocationId,
			List<String> userCodesList, String startDateUTC, String endDateUTC, String isGlobalUser, Long orgId)
			throws Exception {
		return candidateDao.getCandidateCountsCreatedUserList(orgLocationId, userCodesList, startDateUTC, endDateUTC, isGlobalUser, orgId);
	}

	@Override
	public ResponseList<SubmissionCandidateListView> getSubmissionCandidateListView(CandidateDTO candidateDTO,
			OrgLocations orgLcoations) throws Exception {
		return candidateDao.getSubmissionCandidateListView(candidateDTO, orgLcoations);
	}

	@Override
	public CandidateDTO getCandidateDTOByCandidateId(Long candidateId) throws Exception {
		return candidateDao.getCandidateDTOByCandidateId(candidateId);
	}

	@Override
	public List<CandidateDTO> getCandidateListForAddApplicant(OrgLocations orgLcoations,
			Long requirementId,String skills,String candidateDetails,Integer profilePercentage) throws Exception {
		return candidateDao.getCandidateListForAddApplicant(orgLcoations, requirementId,skills,candidateDetails,profilePercentage);
	}

	@Override
	public Candidate saveCandidateBulkImports(CandidateDTO candidateDTO, OrgLocations orgLocations,User user,List<OrgRelations> vendorsList) throws Exception {
		
		StringBuffer activitySummary = new StringBuffer();
		HashSet<CandidateResumes> candidateResumesList = null;
		if (null != candidateDTO.getExperienceYears()) {
			if (candidateDTO.getExperienceYears().matches("[-a-zA-Z]+")) {
				candidateDTO.setExperienceYears("0");
				candidateDTO.setExperienceMonths("0");
			}else{
				 String exp[] = getExperienceYearsAndMonths(candidateDTO.getExperienceYears());
				 if(exp.length >1){
					 candidateDTO.setExperienceYears(exp[0]);
						if(!exp[1].matches("[a-zA-Z]+")){
							if(!"years".equalsIgnoreCase(exp[1])){
							int months = ((Number)NumberFormat.getInstance().parse(exp[1])).intValue();
							candidateDTO.setExperienceMonths(String.valueOf(months));
							}
								
						} 
				 }else{
					 if(exp[0].contains("years") || exp[0].contains("Years") ){
						 int years = ((Number)NumberFormat.getInstance().parse(exp[0])).intValue();
						 candidateDTO.setExperienceYears(String.valueOf(years));
						 candidateDTO.setExperienceMonths("0");
					 }else{
						 candidateDTO.setExperienceYears("");
						 candidateDTO.setExperienceMonths("");
					 }
					 
				 }
					
					
			}
		}
		
		if (null != candidateDTO.getEmploymentType()) {
			if(candidateDTO.getEmploymentType().equalsIgnoreCase("W2")) {
				candidateDTO.setDesignationType("W-2");
			}
			
			else if(!candidateDTO.getEmploymentType().equalsIgnoreCase("C2C") && 
					!candidateDTO.getEmploymentType().equalsIgnoreCase("C2C-1099") &&
					!candidateDTO.getEmploymentType().equalsIgnoreCase("W-2") && 
					!candidateDTO.getEmploymentType().equalsIgnoreCase("C2H") && 
					!candidateDTO.getEmploymentType().equalsIgnoreCase("C2H -1099") &&
					!candidateDTO.getEmploymentType().equalsIgnoreCase("Part Time") && 
					!candidateDTO.getEmploymentType().equalsIgnoreCase("Full Time") && 
					!candidateDTO.getEmploymentType().equalsIgnoreCase("Off Shore")) {
				
				candidateDTO.setDesignationType("C2C");
			}else{
				candidateDTO.setDesignationType(candidateDTO.getEmploymentType());
			}
		}
		
		if (null != candidateDTO.getExpectedRate()) {
			String rate = getExpectedRate(candidateDTO.getExpectedRate());
			if (null != rate) {
				candidateDTO.setExpectedRate(rate);
				String countryCheck = orgLocations.getCountry();
				switch (countryCheck) {
					case "India":
						candidateDTO.setRateType("Monthly");
						break;
					default:
						candidateDTO.setRateType("Hourly");
				}
				
				if ("India".equals(orgLocations.getCountry())) {
					
					candidateDTO.setExpectedRateCurrencey("INR");
				}
				else if ("Australia".equals(orgLocations.getCountry())) {
					
					candidateDTO.setExpectedRateCurrencey("AUD");
				}
				else if ("United States".equals(orgLocations.getCountry())) {
					
					candidateDTO.setExpectedRateCurrencey("USD");
				}
				else if ("Singapore".equals(orgLocations.getCountry())) {
					
					candidateDTO.setExpectedRateCurrencey("SGD");
				}
				else if ("United Kingdom".equals(orgLocations.getCountry())) {
					
					candidateDTO.setExpectedRateCurrencey("GBP");
				}
				else if ("France".equals(orgLocations.getCountry())) {
					
					candidateDTO.setExpectedRateCurrencey("EUR");
				}
			}
			else {
				
				if ("India".equals(orgLocations.getCountry())) {
					
					candidateDTO.setExpectedRateCurrencey("INR");
				}
				else if ("Australia".equals(orgLocations.getCountry())) {
					
					candidateDTO.setExpectedRateCurrencey("AUD");
				}
				else if ("United States".equals(orgLocations.getCountry())) {
					
					candidateDTO.setExpectedRateCurrencey("USD");
				}
				else if ("Singapore".equals(orgLocations.getCountry())) {
					
					candidateDTO.setExpectedRateCurrencey("SGD");
				}
				else if ("United Kingdom".equals(orgLocations.getCountry())) {
					
					candidateDTO.setExpectedRateCurrencey("GBP");
				}
				else if ("France".equals(orgLocations.getCountry())) {
					
					candidateDTO.setExpectedRateCurrencey("EUR");
				}
				String countryCheck = orgLocations.getCountry();
				switch (countryCheck) {
					case "India":
						candidateDTO.setRateType("Monthly");
						break;
					default:
						candidateDTO.setRateType("Hourly");
				}
			}
		}
		
		
		
		if (null != candidateDTO.getExperienceYears()) {
			if (candidateDTO.getExperienceYears().matches("[-a-zA-Z]+")) {
				candidateDTO.setExperienceYears("0");
				candidateDTO.setExperienceMonths("0");
			}else{
				 String exp[] = getExperienceYearsAndMonths(candidateDTO.getExperienceYears());
				 if(exp.length >1){
					 candidateDTO.setExperienceYears(exp[0]);
						if(!exp[1].matches("[a-zA-Z]+")){
							if(!"years".equalsIgnoreCase(exp[1])){
							int months = ((Number)NumberFormat.getInstance().parse(exp[1])).intValue();
							candidateDTO.setExperienceMonths(String.valueOf(months));
							}
								
						} 
				 }else{
					 if(exp[0].contains("years") || exp[0].contains("Years") ){
						 int years = ((Number)NumberFormat.getInstance().parse(exp[0])).intValue();
						 candidateDTO.setExperienceYears(String.valueOf(years));
						 candidateDTO.setExperienceMonths("0");
					 }else{
						 candidateDTO.setExperienceYears("");
						 candidateDTO.setExperienceMonths("");
					 }
					 
				 }
					
					
			}
			
		}
		
		
		// Vendor and Vendor Contact
		if(candidateDTO.getVendor() != null && !candidateDTO.getVendor().equals("") && candidateDTO.getVendorcontact() != null && !candidateDTO.getVendorcontact().equals("")) {
			Long vendorId = 0l;
			for(OrgRelations rel : vendorsList) {
				if((candidateDTO.getVendor().equalsIgnoreCase(rel.getLocationId().getOrgLocationName())) || (candidateDTO.getVendor().equalsIgnoreCase(rel.getResponderLocationId().getOrgLocationName()))) {
					vendorId = rel.getId();
				}
			}
			if(vendorId > 0) {
				candidateDTO.setVendors(String.valueOf(vendorId));
			}
			
			List<OrgSpoc> vendorContactsList = orgSpocService.getOrgSpocByOrgRelationId(vendorId);
			
			for(OrgSpoc vendorContacts : vendorContactsList) {
				if(candidateDTO.getVendorcontact().equalsIgnoreCase(vendorContacts.getVendorUser().getEmail())) {
					candidateDTO.setVendorcontact(String.valueOf(vendorContacts.getId()));
				}
			}
		}
		
		Candidate candidate = new Candidate();
		candidate.setCreatedDate(commonUtil.orgLocationCommonDate(new Date(), orgLocations));
		candidate.setCreatedBy(user.getUserCode());
		candidate.setWorkLocation(orgLocations);
		candidate.setCandidateType("Candidate");
		candidate.setCandidateUUID(SourceLeadRandomCodeHelper.generateRandomUUID());
		candidate.setIsFavourite(0);
		candidate.setIsFavouriteAssociate(0);
		
		if(StringUtils.isNotBlank(candidateDTO.getAadharNumber())) {
			candidate.setAadharNumber(candidateDTO.getAadharNumber());
			}
		
		if(StringUtils.isNotBlank(candidateDTO.getPanNumber())) {
			candidate.setPanNumber(candidateDTO.getPanNumber());
			}
		
		if(StringUtils.isNotBlank(candidateDTO.getSsnNumber())) {
			candidate.setSsnNumber(candidateDTO.getSsnNumber());
			}
		
		MasterCandidate masterCandidate = null;
		masterCandidate = masterCandidateService.getMasterCandidateByInteligenceCandidateDTO(candidateDTO);
		if(null == masterCandidate){
			masterCandidate = masterCandidateService.storeMasterCandidateByCandidateDTO(candidateDTO, user);
		}
		candidate.setMasterCandidateId(masterCandidate);
		candidate.setGeneratedId(getCandidateId(orgLocations));
		
		candidate = getCandidateDetails(candidate, candidateDTO, user, orgLocations, activitySummary);
		
		HashSet<CandidateEducation>	 candidateEducationList = candidateEducationService.getCandidateEducationDetailsList(candidateDTO, orgLocations,candidate,user);
		candidate.setCandidateEducation(candidateEducationList);
		
		HashSet<CandidateEmployers> candidateEmployersList = candidateEmployersService.getCandidateEmployersDetailsList(candidateDTO,orgLocations,candidate,user);
		candidate.setCandidateEmployers(candidateEmployersList);
		
		if(candidateDTO.getDocFile() != null && !candidateDTO.getDocFile().equals("")) {
			candidateResumesList = new HashSet<CandidateResumes>();
			long time = new Date().getTime();
			File folder = new File(staticPath+"/"+orgLocations.getOrg().getId()+"/"+orgLocations.getId()+"/resumes/");
			if (!folder.exists())
				folder.mkdirs();
			File file = new File(folder,candidateDTO.getFirstName() + time+".docx");
			String fileUrl = "/slead-static/"+orgLocations.getOrg().getId()+"/"+orgLocations.getId()+"/resumes/"+file.getName();
			XWPFDocument document = new XWPFDocument(); 
			FileOutputStream out = new FileOutputStream(file);
			XWPFParagraph paragraph = document.createParagraph();
			XWPFRun run = paragraph.createRun();
			
			Scanner scanner = new Scanner(candidateDTO.getDocFile());
			while (scanner.hasNextLine()) {
			  String line = scanner.nextLine();
			  run.setText(line);
			  run.setFontFamily("verdana");
			  run.setFontSize(12);
			  run.addBreak();
			}
			
	        document.write(out);
	        out.close();
	        document.close();
	        scanner.close();
	        
	        CandidateResumes resume = new CandidateResumes();
	        resume.setOrgLocationId(orgLocations);
	        resume.setCandidateId(candidate);
	        resume.setFileContentType("application/msword");
	        resume.setFileName(candidateDTO.getFirstName()+" Resume.doc");
	        resume.setStatus(Constants.CANDIDATE_ACTIVE);
	        resume.setCreatedDate(new Date());
	        resume.setCreatedBy(user.getUserCode());
	        resume.setUpdatedDate(new Date());
	        resume.setUpdatedBy(user.getUserCode());
	        resume.setResumeUUID(SourceLeadRandomCodeHelper.generateRandomUUID());
	        resume.setTitle(candidateDTO.getFirstName()+" Resume");
	        resume.setFileURL(fileUrl);
	        candidateResumesList.add(resume);
	        candidate.setCandidateResumes(candidateResumesList);
		}
		
		int percentage = 0;
		if(candidate.getFirstName() != null  && candidate.getEmail() != null){
			percentage = 30;
		}
		if(candidateEducationList != null && candidateEducationList.size() > 0){
			percentage = percentage +10;
		}
		 if(candidateEmployersList != null && candidateEmployersList.size() > 0){
				percentage = percentage +10;
		 }
		 if(candidateEmployersList != null && candidateEmployersList.size() > 0){
				percentage = percentage +10;
		 }
		 
		 if(candidate.getSkillsName()!=null && !"".equalsIgnoreCase(candidate.getSkillsName()) ){
				percentage = percentage +10;
		 }
		 
		 if(candidate.getPrimarySkill()!=null && !"".equalsIgnoreCase(candidate.getPrimarySkill())){
				percentage = percentage + 5;
		 }
		 
		 int countOtherDetails = 0;
		 if(candidate.getSummary() != null && !"".equalsIgnoreCase(candidate.getSummary()) ){
			 countOtherDetails++;
		 }
		 if(candidate.getVisaType()!=null && !"".equalsIgnoreCase(candidate.getVisaType())){
			 countOtherDetails++;
		 }
		 if(candidate.getExpectedRate()!=null && !"".equalsIgnoreCase(candidate.getExpectedRate())){
			 countOtherDetails++;
		 }
		 if((candidate.getExperienceYears()!= null && !"".equalsIgnoreCase(candidate.getExperienceYears()) && Long.parseLong(candidate.getExperienceYears())>0) || (candidate.getExperienceMonths()!= null && !"".equalsIgnoreCase(candidate.getExperienceMonths())&& Long.parseLong(candidate.getExperienceMonths())>0)){
			 countOtherDetails++;
		 }
		 if(countOtherDetails>=2){
			 percentage = percentage + 10; 
		 }
		 else if(countOtherDetails==1){
			 percentage = percentage + 5; 
		 }
		 
		candidate.setCandidateProfilePercentage(percentage);
		
		Candidate savedCandidate = candidateDao.storeCandidate(candidate);
		saveOrUpdateCandidateElkDocument(savedCandidate);
		
	    /*if(StringUtils.isNotBlank(candidateDTO.getInviteEmail()) && Constants.YES.equalsIgnoreCase(candidateDTO.getInviteEmail())){
			inviteCandidateToSourceLead(user,savedCandidate,orgLocations);
	    }*/
		
	    return candidate;
		
	}
	
	private String[] getExperienceYearsAndMonths(String experience) {
	    
		return experience.split("[\\p{Punct}\\s]+");
		
	}
	
	private String getExpectedRate(String expectedRate)
	{
		
		String[] Res = expectedRate.split("[\\p{Punct}\\s]+");
		for (String s : Res) {
			if (isNumeric(s)) {
				return s;
			}
		}
		return null;
	}
	
	public static boolean isNumeric(String str)
	{
		return str.matches("-?\\d+(\\.\\d+)?");
	}

	@Override
	public List<Map<String, BigInteger>> getAllActiveCandidatesByLocationName(Long locationId, String name) {
		return candidateDao.getAllActiveCandidatesByLocationName(locationId, name);
	}

	@Override
	public List<CandidateDTO> getAllActiveCandidatesListByOrglocationId(Long orgLocationId, String employeementType)
			throws Exception {
		return candidateDao.getAllActiveCandidatesListByOrglocationId(orgLocationId, employeementType);
	}

	@Override
	public List<Candidate> getAllCandidatesList() throws Exception {
		return candidateDao.getAllCandidatesList();
	}

	@Override
	public Candidate getCandidateDetails(Candidate candidate, CandidateDTO candidateDto, User user,OrgLocations orgLocations,StringBuffer activitySummary) throws Exception {
		
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
		
		/*if(candidateDto.getDesignationOtherType() !=null){
			if(candidate.getDesignationOtherType() != null && candidate.getDesignationOtherType() != "" && !candidateDto.getDesignationOtherType().equalsIgnoreCase(candidate.getDesignationOtherType())){
				activitySummary.append("Designation Other Type").append(" : ").append(Constants.OLD_VALUE_TAG_INITIATION).append(candidate.getDesignationOtherType()).append(Constants.OLD_VALUE_TAG_END).append(" ").append(Constants.NEW_VALUE_TAG_INITIATION).append(candidateDto.getDesignationOtherType()).append(Constants.NEW_VALUE_TAG_END).append(" ");
			}else
				
			activitySummary.append("Designation Other Type").append(" : ").append(" -- ");
			
			candidate.setDesignationOtherType(candidateDto.getDesignationOtherType());
		}else{
			candidate.setDesignationOtherType(null);
		}*/
		
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
		if(candidateDto.getNoticePeriod() != null)
			noticePeriod = candidateDto.getNoticePeriod().replaceAll("\\s+", " ");
		
	    if(noticePeriod != null) {
		if(candidate.getNoticePeriod() != null  && candidate.getNoticePeriod() != "" && !noticePeriod.equalsIgnoreCase(candidate.getNoticePeriod())){
			 activitySummary.append("Notice Period").append(" : ").append(Constants.OLD_VALUE_TAG_INITIATION).append(candidate.getNoticePeriod()).append(Constants.OLD_VALUE_TAG_END).append(" ").append(Constants.NEW_VALUE_TAG_INITIATION).append(noticePeriod).append(Constants.NEW_VALUE_TAG_END).append(" ");
		}else if((candidate.getNoticePeriod() != null || candidate.getNoticePeriod() != "" ||  noticePeriod.equalsIgnoreCase(candidate.getNoticePeriod())) && noticePeriod !="" ){
			  activitySummary.append("Notice Period").append(" : ").append(" ").append(Constants.NEW_VALUE_TAG_INITIATION).append(noticePeriod).append(Constants.NEW_VALUE_TAG_END).append(" ");
		}else 
			activitySummary.append("Notice Period").append(" : ").append("---").append(" ");
			candidate.setNoticePeriod(noticePeriod);	
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
		
		HashSet<CandidateHistory> candiateHistoryList = candidateHistoryService.getCandidateHistoryDetails(candidate, user);
		candidate.setCandidateHistory(candiateHistoryList);
		
		HashSet<CandidateTimelineActivity> candiateTimeLineActivityList = null;
		if(StringUtils.isNotBlank(candidateDto.getCandidateId())){
			candiateTimeLineActivityList = candidateTimelineActivityService.getCandidateTimelineActivityDetails(user, orgLocations, candidate, "", Constants.CANDIDATE_ADDED_BY_IMPORTING, activitySummary.toString(), "CANDIDATE");
		}else{
			if(candidateDto.getCandidateCreationType() != null && candidateDto.getCandidateCreationType().equalsIgnoreCase(Constants.MANUAL_CANDIDATE)){
				candiateTimeLineActivityList = candidateTimelineActivityService.getCandidateTimelineActivityDetails(user, orgLocations, candidate, "", Constants.CANDIDATE_ADDED_MANUALLY, "", "CANDIDATE");
			}else if(candidateDto.getCandidateCreationType() != null && candidateDto.getCandidateCreationType().equalsIgnoreCase(Constants.IMPORT_CANDIDATE)){
				candiateTimeLineActivityList = candidateTimelineActivityService.getCandidateTimelineActivityDetails(user, orgLocations, candidate, "", Constants.CANDIDATE_ADDED_BY_IMPORTING, "", "CANDIDATE");
			}
		}
		candidate.setCandidateTimeLineActivity(candiateTimeLineActivityList);
		
		
		if(StringUtils.isNotBlank(candidateDto.getIsAssociate()) && Constants.YES.equalsIgnoreCase(candidateDto.getIsAssociate())){
			candidate.setIsAssociate(Constants.YES);
		}else{
			candidate.setIsAssociate(Constants.NO);
		}
		
		if(StringUtils.isNotBlank(candidateDto.getPanNumber())) {
			candidate.setPanNumber(candidateDto.getPanNumber());
		}
		
		if(StringUtils.isNotBlank(candidateDto.getAadharNumber())) {
			candidate.setAadharNumber(candidateDto.getAadharNumber());
		}
		
		if(StringUtils.isNotBlank(candidateDto.getSsnNumber())) {
			candidate.setSsnNumber(candidateDto.getSsnNumber());
		}
		
		candidate.setUpdatedBy(user.getUserCode());
		candidate.setUpdatedDate(commonUtil.orgLocationCommonDate(new Date(), orgLocations));
		candidate.setStatus(Constants.CANDIDATE_ACTIVE);
		candidate.setOrgLocationId(orgLocations);
		return candidate;
	}

	@Override
	public void saveBulkCandidates(List<Candidate> candidatesList,Long orgLocationId) throws Exception {
		candidateDao.saveBulkCandidates(candidatesList,orgLocationId);
	}

	@Override
	public List<String> getCandidateEmailsByOrgLocationId(Long orgLocationId) throws Exception {
		return candidateDao.getCandidateEmailsByOrgLocationId(orgLocationId);
	}

	@Override
	public Long getCandidateIdByEmailAndOrgLocationId(String email, Long orgLocationId) throws Exception {
		return candidateDao.getCandidateIdByEmailAndOrgLocationId(email, orgLocationId);
	}

	@Override
	public ResponseList<CandidateMarketPlaceDTO> getGlobalMarketCandidateList(CandidateMarketPlaceDTO candidateMarketPlaceDTO, String searchValue) {
		return candidateDao.getGlobalMarketCandidateList(candidateMarketPlaceDTO, searchValue);
	}

	@Override
	public void copyCandidateToMarketplace(String candidateIdsStr, User user) throws NumberFormatException, Exception {
		String[] candidateIds = candidateIdsStr.split(",");
		for(String candidateId : candidateIds){
			Candidate candidate = candidateDao.getCandidateByCandidateId(Long.parseLong(candidateId));
			CandidateMarketPlace marketPlace = new CandidateMarketPlace();
			//BeanUtils.copyProperties(candidate, marketPlace);
			marketPlace.setCandidateId(candidate);
			marketPlace.setCandidateUUID(candidate.getCandidateUUID());
			marketPlace.setOrgLocationId(candidate.getOrgLocationId());
			marketPlace.setMarketPrice(null);
			marketPlace.setMarketRateCurrencey(null);
			marketPlace.setCandidateMarketStatus(Constants.DRAFT);
			marketPlace.setStatus(Constants.CANDIDATE_ACTIVE);
			marketPlace.setCreatedDate(new Date());
			marketPlace.setCreatedBy(user);
			marketPlace.setUpdatedDate(new Date());
			marketPlace.setUpdatedBy(user);
			candidateDao.saveMarketPlace(marketPlace);
			
		}
		
	}

	@Override
	public ResponseList<CandidateDTO> getCandidatesByCandidateIds(List<String> candidateIdsStr) {
		ResponseList<CandidateDTO> candidates = candidateDao.getCandidatesByCandidateIds(candidateIdsStr);
		return candidates;
	}

	@Override
	public void saveBulkCandidates(List<Candidate> candidatesList) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void saveCandidateMarketPlaceList(List<CandidateMarketPlaceDTO> marketPlaceList, User user) throws NumberFormatException, Exception {

		for(CandidateMarketPlaceDTO dto : marketPlaceList){
			CandidateMarketPlace marketPlace = null;
			marketPlace = candidateDao.getCandidateMarketPlaceByCandidateId(Long.parseLong(dto.getCandidateId()));
			if(marketPlace == null){
				marketPlace = new CandidateMarketPlace();
			}
			Candidate candidate = candidateDao.getCandidateById(Long.parseLong(dto.getCandidateId()));
			//BeanUtils.copyProperties(candidate, marketPlace);
			candidate.setIsSharedInMarketplace(Constants.YES);
			marketPlace.setCandidateId(candidate);
			marketPlace.setMarketplaceUUID(SourceLeadRandomCodeHelper.generateRandomUUID());
			marketPlace.setCandidateUUID(candidate.getCandidateUUID());
			marketPlace.setOrgLocationId(candidate.getOrgLocationId());
			marketPlace.setCompanyName(candidate.getOrgLocationId().getOrg().getOrgName()); // added company name for the candidate
			marketPlace.setShowCompanyName(candidate.getOrgLocationId().getShowCompanyNameInMarkeplace());// ORG level setting to display org name in market place.
			marketPlace.setCandidateMarketStatus(Constants.ACTIVE);
			marketPlace.setMarketPrice(dto.getMarketPrice());
			marketPlace.setMarketRateCurrencey(dto.getMarketRateCurrencey());
			marketPlace.setStatus(Constants.CANDIDATE_ACTIVE);
			marketPlace.setCreatedDate(new Date());
			marketPlace.setCreatedBy(user);
			marketPlace.setUpdatedDate(new Date());
			marketPlace.setUpdatedBy(user);
			candidateDao.storeCandidate(candidate);
			saveOrUpdateCandidateElkDocument(candidate);
			candidateDao.saveMarketPlace(marketPlace);
			
		}
	}

	@Override
	public Map<String, Object[]> getAddedMarketplaceCandidates(List<Long> candidateIdsVal) {
		List<Long> marketplaceCandidates = candidateDao.getAddedMarketplaceCandidates(candidateIdsVal);
		candidateIdsVal.removeAll(marketplaceCandidates);
		Map<String, Object[]> candidatesIds = new HashMap<>();
		candidatesIds.put("candidatesInMarketplace", marketplaceCandidates.toArray());
		candidatesIds.put("candidatesNotInMarketplace", candidateIdsVal.toArray());
		return candidatesIds;
	}

	@Override
	public void updateMarketPlacePrice(Long id, String priceVal, String priceValType) throws Exception {
		CandidateMarketPlace marketPlace = candidateDao.getCandidateMarketPlaceById(id);
		CandidateMarketCart marketCart = candidateDao.getCandidateFromMarketCartByMarketplaceId(id);
		marketPlace.setMarketPrice(priceVal);
		marketPlace.setMarketRateCurrencey(priceValType);
		candidateDao.updateMarketPlacePrice(marketPlace);
		// updating cart price in global marketplace after it is edited and saved in local marketplace.
		if (marketPlace != null && marketCart != null) {
			marketCart.setAmount(Double.parseDouble(priceVal));
			candidateDao.addCandidateToMarketCart(marketCart);
		}
	}
	
	@Override
	public CandidateMarketPlace getCandidateMarketPlaceDetailsByIdAndOrgLocationId(Long id, Long orgLocationId) throws Exception{
		return candidateDao.getCandidateMarketPlaceDetailsByIdAndOrgLocationId(id, orgLocationId);
	}
	
	@Override
	public List<CandidateMarketPlace> getCandidateListMarketPlaceDetailsByIdAndOrgLocationId(List<Long> id, Long orgLocationId)throws Exception{
		return candidateDao.getCandidateListMarketPlaceDetailsByIdAndOrgLocationId(id, orgLocationId);
	}
	
	@Override
	public void withdrawCandidateFromMarketplace(CandidateMarketPlace marketplace, String withdrawReason) throws Exception {
		marketplace.setCandidateMarketStatus(Constants.STATUS_WITHDRAWN);
		marketplace.setWithdrawReason(withdrawReason);
		Candidate candidate = candidateDao.getCandidateById(marketplace.getCandidateId().getId());
		candidate.setIsSharedInMarketplace(Constants.NO);
		saveOrUpdateCandidateElkDocument(candidate);
		candidateDao.storeCandidate(candidate);
		candidateDao.withdrawCandidateFromMarketplace(marketplace);
		//Deleting the record from the cart if already added in cart by the user.
		candidateDao.deleteCandidateFromMarketcartByCandidateUuid(marketplace.getCandidateUUID());
	}

	@Override
	public CandidateMarketPlace getCandidateMarketPlaceDetailsByUUId(String uuid) throws Exception {
		// TODO Auto-generated method stub
		return candidateDao.getCandidateMarketPlaceDetailsByUUId(uuid);
	}

	@Override
	public void addCandidateToMarketCart(User user, CandidateMarketPlace marketplace, OrgLocations orgLocations) throws Exception {
		// TODO Auto-generated method stub
		CandidateMarketCart newMarketCart = new CandidateMarketCart();
		newMarketCart.setMarketplaceId(marketplace);
		newMarketCart.setCandidateId(marketplace.getCandidateId());
		newMarketCart.setEmail(marketplace.getCandidateId().getEmail());
		newMarketCart.setCandidateUUID(marketplace.getCandidateUUID());
		newMarketCart.setMarketplaceUUID(marketplace.getMarketplaceUUID());
		newMarketCart.setMarketCartUUID(SourceLeadRandomCodeHelper.generateRandomUUID());
		OrgLocations orgLocation = new OrgLocations();
		orgLocation.setId(orgLocations.getId());
		newMarketCart.setSourceOrgLocationId(marketplace.getOrgLocationId());
		newMarketCart.setSourceOrgId(marketplace.getOrgLocationId().getOrg());
		newMarketCart.setTargetOrgId(orgLocations.getOrg());
		newMarketCart.setTargetOrgLocationId(orgLocation);
		newMarketCart.setAmount(Double.valueOf(marketplace.getMarketPrice()));
		newMarketCart.setCurrency(marketplace.getMarketRateCurrencey());
		newMarketCart.setStatus(marketplace.getStatus());
		newMarketCart.setCartStatus(Constants.ADDED_CART);
		newMarketCart.setCreatedDate(new Date());
		newMarketCart.setCreatedBy(user);
		newMarketCart.setUpdatedDate(new Date());
		newMarketCart.setUpdatedBy(user);
		candidateDao.addCandidateToMarketCart(newMarketCart);
	}

	@Override
	public List<CandidateMarketPlaceDTO> getGlobalMarketplaceCandListGridCard(String[] searchCandidateIds, OrgLocations orgLocations, String candidateLocation, String searchValue, CandidateMarketPlaceDTO candidateMarketPlaceDTO) {
		List<CandidateMarketPlace> candidateMarketPlaces = null;
		List<CandidateMarketPlaceDTO> marketPlaceDTOs = new ArrayList<>();
		Date today = new Date();
		
		Calendar calendar = Calendar.getInstance();
		Map<String, Date> dateValue = new HashMap<>();
		String updatedDate = candidateMarketPlaceDTO.getUpdatedDate();
		Date[] checkedDateArray = new Date[0];
		try {
			Date formattedDate = commonUtil.getOrgLocationCurrentDate(orgLocations.getDateFormat(), today);
		if(updatedDate != null){
			if(updatedDate.equalsIgnoreCase("today")){
					 checkedDateArray = reportsUtil.getJobFilterDate(updatedDate, orgLocations);
			}
			if(updatedDate.equalsIgnoreCase("1_day_ago")){
				calendar.setTime(formattedDate);
				calendar.add(Calendar.DAY_OF_YEAR, -1);
				Date newDate = calendar.getTime();
				Date newFormattedDate = commonUtil.getOrgLocationCurrentDate(orgLocations.getDateFormat(), newDate);
				dateValue.put("fromDate", formattedDate);
				dateValue.put("toDate", newFormattedDate);
			}
			if(updatedDate.equalsIgnoreCase("3_days_ago")){
				calendar.setTime(formattedDate);
				calendar.add(Calendar.DAY_OF_YEAR, -3);
				Date newDate = calendar.getTime();
				Date newFormattedDate = commonUtil.getOrgLocationCurrentDate(orgLocations.getDateFormat(), newDate);
				dateValue.put("fromDate", formattedDate);
				dateValue.put("toDate", newFormattedDate);
			}
			if(updatedDate.equalsIgnoreCase("1_week_ago")){
				calendar.setTime(formattedDate);
				calendar.add(Calendar.DAY_OF_YEAR, -7);
				Date newDate = calendar.getTime();
				Date newFormattedDate = commonUtil.getOrgLocationCurrentDate(orgLocations.getDateFormat(), newDate);
				dateValue.put("fromDate", formattedDate);
				dateValue.put("toDate", newFormattedDate);
			}
			if(updatedDate.equalsIgnoreCase("2_weeks_ago")){
				calendar.setTime(formattedDate);
				calendar.add(Calendar.DAY_OF_YEAR, -14);
				Date newDate = calendar.getTime();
				Date newFormattedDate = commonUtil.getOrgLocationCurrentDate(orgLocations.getDateFormat(), newDate);
				dateValue.put("fromDate", formattedDate);
				dateValue.put("toDate", newFormattedDate);
			}
			if(updatedDate.equalsIgnoreCase("1_month_ago")){
				calendar.setTime(formattedDate);
				calendar.add(Calendar.MONTH, -1);
				Date newDate = calendar.getTime();
				Date newFormattedDate = commonUtil.getOrgLocationCurrentDate(orgLocations.getDateFormat(), newDate);
				dateValue.put("fromDate", formattedDate);
				dateValue.put("toDate", newFormattedDate);
			}
			if(updatedDate.equalsIgnoreCase("3_months_ago")){
				calendar.setTime(formattedDate);
				calendar.add(Calendar.MONTH, -3);
				Date newDate = calendar.getTime();
				Date newFormattedDate = commonUtil.getOrgLocationCurrentDate(orgLocations.getDateFormat(), newDate);
				dateValue.put("fromDate", formattedDate);
				dateValue.put("toDate", newFormattedDate);
			}
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		candidateMarketPlaces = candidateDao.getGlobalMarketplaceCandListGridCard(searchCandidateIds, orgLocations.getId(), candidateLocation, searchValue, candidateMarketPlaceDTO, dateValue,checkedDateArray);
		if(candidateMarketPlaces != null){
			for(CandidateMarketPlace marketPlace : candidateMarketPlaces){
				CandidateMarketPlaceDTO marketPlaceDTO = new CandidateMarketPlaceDTO();
				BeanUtils.copyProperties(marketPlace, marketPlaceDTO);
				marketPlaceDTO.setFullName(marketPlace.getCandidateId().getFullName());
				marketPlaceDTO.setImageUrl(marketPlace.getCandidateId().getFileUrl());
				marketPlaceDTO.setDesignation(marketPlace.getCandidateId().getDesignation());
				marketPlaceDTO.setCurrentRateCurrency(marketPlace.getCandidateId().getCurrentRateCurrency());
				marketPlaceDTO.setCurrentRate(marketPlace.getCandidateId().getCurrentRate());
				marketPlaceDTO.setCurrentRateType(marketPlace.getCandidateId().getCurrentRateType());
				marketPlaceDTO.setExpectedRateCurrencey(marketPlace.getCandidateId().getExpectedRateCurrencey());
				marketPlaceDTO.setExpectedRate(marketPlace.getCandidateId().getExpectedRate());
				marketPlaceDTO.setExpectedRateType(marketPlace.getCandidateId().getRateType());
				marketPlaceDTO.setExperienceYears(marketPlace.getCandidateId().getExperienceYears());
				marketPlaceDTO.setExperienceMonths(marketPlace.getCandidateId().getExperienceMonths());
				marketPlaceDTO.setLocation(marketPlace.getCandidateId().getLocation());
				marketPlaceDTO.setShowCompanyName(marketPlace.getShowCompanyName());
				marketPlaceDTO.setCompanyName(marketPlace.getCompanyName());
				marketPlaceDTO.setSkillsName(marketPlace.getCandidateId().getSkillsName());
				marketPlaceDTO.setSummary(marketPlace.getCandidateId().getSummary());
				marketPlaceDTO.setMarketRateCurrencey(marketPlace.getMarketRateCurrencey());
				marketPlaceDTO.setMarketPrice(marketPlace.getMarketPrice());
				marketPlaceDTO.setMarketplaceUUID(marketPlace.getMarketplaceUUID());
				marketPlaceDTO.setProfilePercentage(marketPlace.getCandidateId().getCandidateProfilePercentage());
				int count = candidateDao.getMarketCartStatusByMarketIdOrgId(marketPlace.getId(), orgLocations.getId());
				if(count != 0){
					marketPlaceDTO.setMarketCartStatus(Constants.ADDED_CART);
				}
				marketPlaceDTOs.add(marketPlaceDTO);
			}
		}
		return marketPlaceDTOs;
	}

	@Override
	public void deleteCandidateMarketCartByMarketUUID(String candidatgeMarketUUID) {
		candidateDao.deleteCandidateMarketCartByMarketUUID(candidatgeMarketUUID);
		
	} 

	public Integer getCandidateProfilePercentageByCandidateId(Long id) throws Exception {
		return candidateDao.getCandidateProfilePercentageByCandidateId(id);
	}

	@Override
	public Object[] updateMarketplaceCartCountAndPrice(Long orgLocationId, String userRole, User user)throws Exception {
		return candidateDao.updateMarketplaceCartCountAndPrice(orgLocationId, userRole, user);
	}
	public ResponseList<Candidate> getAllCandidatesByCreatedByUserCode(CandidateDTO candidateDTO,OrgLocations orgLocations,String userCode,Date[] searchDates) {
		return candidateDao.getAllCandidatesByCreatedByUserCode(candidateDTO,orgLocations,userCode,searchDates);
	}
	
	@Override
	public List<CandidateMarketPlaceCartView> getCandidateDetailsFromMarketcart(User user, String userRole, OrgLocations orgLocations, String creationStartDate, String creationEndDate, String checkedDate, String searchValue)throws Exception {
		Date[] checkedDateArray = new Date[0];
		if(checkedDate != null && !"".equalsIgnoreCase(checkedDate) && !checkedDate.equalsIgnoreCase("custom")){
			 checkedDateArray = reportsUtil.getJobFilterDate(checkedDate, orgLocations);
			 }
		return candidateDao.getCandidateDetailsFromMarketcart(user, userRole, orgLocations, creationStartDate, creationEndDate, checkedDateArray, searchValue);	
		}
	@Override
	public Boolean deleteCandidateFromMarketcartByCandidateUuid(String candidateUuid) throws Exception {
		return candidateDao.deleteCandidateFromMarketcartByCandidateUuid(candidateUuid);
		
	}
	
	@Override
	public ResponseList<CandidateMarketPlaceDTO> getGlobalDataRecommendations(
			CandidateMarketPlaceDTO candidateMarketPlaceDTO, OrgLocations orgLocations, String searchValue) {
		return candidateDao.getGlobalDataRecommendations(candidateMarketPlaceDTO, orgLocations, searchValue);
	}
	
	@Override
	public int updateMarketplaceCandidates(OrgLocations orgLocations, String companyNameFlag) throws Exception {
		return candidateDao.updateMarketplaceCandidates(orgLocations,companyNameFlag);
	}
	

	@Override
	public CandidateMarketPlaceDTO getCandidateMarketPlaceDTODetailsByUUId(String marketplaceUUID, OrgLocations orgLocations) throws Exception {
		CandidateMarketPlace marketPlace = candidateDao.getCandidateMarketPlaceDetailsByUUId(marketplaceUUID);
		CandidateMarketPlaceDTO marketPlaceDTO = new CandidateMarketPlaceDTO();
		BeanUtils.copyProperties(marketPlace, marketPlaceDTO);
		marketPlaceDTO.setImageUrl(marketPlace.getCandidateId().getFileUrl());
		marketPlaceDTO.setFullName(marketPlace.getCandidateId().getFullName());
		marketPlaceDTO.setLocation(marketPlace.getCandidateId().getLocation());
		marketPlaceDTO.setProfilePercentage(marketPlace.getCandidateId().getCandidateProfilePercentage());
		marketPlaceDTO.setDesignation(marketPlace.getCandidateId().getDesignation());
		marketPlaceDTO.setSummary(marketPlace.getCandidateId().getSummary());
		marketPlaceDTO.setCurrentRateCurrency(marketPlace.getCandidateId().getCurrentRateCurrency());
		marketPlaceDTO.setCurrentRate(marketPlace.getCandidateId().getCurrentRate());
		marketPlaceDTO.setCurrentRateType(marketPlace.getCandidateId().getCurrentRateType());
		marketPlaceDTO.setExpectedRateCurrencey(marketPlace.getCandidateId().getExpectedRateCurrencey());
		marketPlaceDTO.setExpectedRate(marketPlace.getCandidateId().getExpectedRate());
		marketPlaceDTO.setExpectedRateType(marketPlace.getCandidateId().getRateType());
		marketPlaceDTO.setExperienceYears(marketPlace.getCandidateId().getExperienceYears());
		marketPlaceDTO.setExperienceMonths(marketPlace.getCandidateId().getExperienceMonths());
		if(marketPlace.getCandidateId().getSkillsName() != null){
			String[] skills = marketPlace.getCandidateId().getSkillsName().split(",");
			marketPlaceDTO.setSkills(skills);
		}
		List<CandidateEmployers> candidateEmployers = candidateEmployersDao.getCandidatesEmployersListByCandidateIdAndOrgLocationID(marketPlace.getCandidateId().getId(), marketPlace.getOrgLocationId().getId());
		List<CandidateEmployerDTO> candidateEmployerDTOs = new ArrayList<>();
		
		for(CandidateEmployers employers : candidateEmployers){
			CandidateEmployerDTO employerDTO = new CandidateEmployerDTO();
			BeanUtils.copyProperties(employers, employerDTO);
			if(employers.getStartMonth()!= null && !employers.getStartMonth().equals("")){
				String[] startMonth = employers.getStartMonth().split("&");
				employerDTO.setStartMonth(startMonth[1]);
			}
			if(employers.getEndMonth() != null && !employers.getEndMonth().equals("")){
				String[] endMonth = employers.getEndMonth().split("&");
				employerDTO.setEndMonth(endMonth[1]);
			}
			if(employers.getIsCurrentPosition() != null && employers.getIsCurrentPosition().equalsIgnoreCase("Y")){
				employerDTO.setEndMonth("Present");
				employerDTO.setEndYear("");
			}
			candidateEmployerDTOs.add(employerDTO);
		}
		
		marketPlaceDTO.setCandidateEmployers(candidateEmployerDTOs);
		List<CandidateEducation> educations = candidateEducationDao.getCandidateEducationListByCandidateIdAndOrgLocationID(marketPlace.getCandidateId().getId(), marketPlace.getOrgLocationId().getId());
		List<CandidateEducationDTO> educationDTOs = new ArrayList<>();
		for(CandidateEducation education : educations){
			CandidateEducationDTO educationDTO = new CandidateEducationDTO();
			BeanUtils.copyProperties(education, educationDTO);
			SimpleDateFormat dtf = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat dtf2 = new SimpleDateFormat("MMM");
			SimpleDateFormat dtf3 = new SimpleDateFormat("yyyy");
			if(education.getStartDate() != null  && !education.getStartDate().equals("")){
				String startDateString = dtf.format(education.getStartDate());
				String startMonthName = dtf2.format(dtf.parse(startDateString));
				String startYear = dtf3.format(dtf3.parse(startDateString));
				educationDTO.setStartYear(startYear);
				educationDTO.setStartMonth(startMonthName);
			}
			if(education.getEndDate() != null  && !education.getEndDate().equals("")){
				String endDateString = dtf.format(education.getEndDate());
				String endMonthName = dtf2.format(dtf.parse(endDateString));
				String endYear = dtf3.format(dtf3.parse(endDateString));
				educationDTO.setEndYear(endYear);
				educationDTO.setEndMonth(endMonthName);
			}
			educationDTOs.add(educationDTO);
		}
		marketPlaceDTO.setCandidateEducation(educationDTOs);
		//Set<CandidateCertifications> certifications = marketPlace.getCandidateId().getCandidateCertifications();
		List<CandidateCertifications> certifications = candidateCertificationsDao.getCandidateCertificationListByCandidateIdAndOrgLocationID(marketPlace.getCandidateId().getId(), marketPlace.getOrgLocationId().getId());
		List<CandidateCertificationDTO> certificationDTOs = new ArrayList<>();
		for(CandidateCertifications certification : certifications){
			CandidateCertificationDTO certificationDTO = new CandidateCertificationDTO();
			BeanUtils.copyProperties(certification, certificationDTO);
			SimpleDateFormat dtf = new SimpleDateFormat("yyyy-MM-dd");
			if(certification.getValidFrom()!= null  && !certification.getValidFrom().equals("")){
				certificationDTO.setValidFrom(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),certification.getValidFrom()));
			}
			if(certification.getValidTo()!= null  && !certification.getValidTo().equals("")){
				certificationDTO.setValidTo(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),certification.getValidTo()));
			}
			certificationDTOs.add(certificationDTO);
		}
		marketPlaceDTO.setCandidateCertifications(certificationDTOs);
		int count = candidateDao.getMarketCartStatusByMarketIdOrgId(marketPlace.getId(), marketPlace.getOrgLocationId().getId());
		if(count != 0){
			marketPlaceDTO.setMarketCartStatus(Constants.ADDED_CART);
		}
		if(marketPlace.getCandidateId().getSkillsName() != null && !marketPlace.getCandidateId().getSkillsName().equals("")){
			List<CandidateMarketPlaceDTO> marketPlaceDTOs = new ArrayList<>();
			List<CandidateMarketPlace> globalCandidatesMarketplaceData = candidateDao.getGlobalMarketplaceDataByOrgId(orgLocations.getId(), marketPlace.getId());
			marketPlaceDTO.setSkillsName(marketPlace.getCandidateId().getSkillsName());
			String[] marketPlaceSkills = marketPlace.getCandidateId().getSkillsName().split(",");
			for (int j = 0; j < marketPlaceSkills.length; j++){
				marketPlaceSkills[j] = marketPlaceSkills[j].trim();
			}
			for(CandidateMarketPlace place : globalCandidatesMarketplaceData){
				String[] placeSkills = null;
				if(place.getCandidateId().getSkillsName() != null && !place.getCandidateId().getSkillsName().equals("")){
					placeSkills = place.getCandidateId().getSkillsName().split(",");
					for (int i = 0; i < placeSkills.length; i++){
						placeSkills[i] = placeSkills[i].trim();
					}
				if(!Collections.disjoint(Arrays.asList(placeSkills), Arrays.asList(marketPlaceSkills))){
					CandidateMarketPlaceDTO dto = new CandidateMarketPlaceDTO();
					BeanUtils.copyProperties(place, dto);
					dto.setImageUrl(place.getCandidateId().getFileUrl());
					dto.setFullName(place.getCandidateId().getFullName());
					dto.setLocation(place.getCandidateId().getLocation());
					marketPlaceDTOs.add(dto);
				}
			}
			}
			marketPlaceDTO.setCandidateMarketPlaceDTOs(marketPlaceDTOs);
		}
		return marketPlaceDTO;
	}
	
	@Override
	public List<CandidateMarketCart> getMarketCartIdsFromMarketcart(Long orgLocationId) throws Exception {
		List<CandidateMarketCart> marketCartIds = candidateDao.getMarketCartIdsFromMarketcart(orgLocationId);
		return marketCartIds;
	}
	
	@Override
	public List<CandidateDTO> getCandidatesListByIds(List<Long> Ids) throws Exception {
		return candidateDao.getCandidatesListByIds(Ids);
	}

	@Override
	public Boolean updateMarketCartStatus(List<Long> marketCartIds, Long orgLocationId) throws Exception {
		return candidateDao.updateMarketCartStatus(marketCartIds, orgLocationId);
	}
	
	@Override
	public CandidateMarketPlacePaymentTransactionHistory getMarketplaceTransactionDetails(Long orgLocationId) throws Exception{
		return candidateDao.getMarketplaceTransactionDetails(orgLocationId);
	}
	
	@Override
	public User getUserDetailsBySourceOrgLocationId(Long sourceOrgLocationId)throws Exception{
		return candidateDao.getUserDetailsBySourceOrgLocationId(sourceOrgLocationId);
	}

	@Override
	public CandidateMarketPlace getCandidateDetailsByCandidateId(Long candidateId) throws Exception{
		return candidateDao.getCandidateDetailsByCandidateId(candidateId);
	}
	

	@Override
	public void saveCandidateMarketplacePaymentTranscationHistory(CandidateMarketPlacePaymentTransactionHistory tranHistory) throws Exception {
		 candidateDao.saveCandidateMarketplacePaymentTranscationHistory(tranHistory);
	}

	@Override
	public void saveTransactionCompletedCandidates(CandidateDTO candidateDto, OrgLocations orgLocations, User user)throws Exception {
			storeCandidate(candidateDto, orgLocations, user);
	}

	@Override
	public void saveToMarketplacePurchaseOrder(List<CandidateMarketCart> marketCartList,OrgLocations orgLocations, String orderNo,String purchasedOrgName,String paymentId) throws Exception {
		
		
		for (CandidateMarketCart candidateMarketCart : marketCartList) {
			
			
			MarketplacePurchaseOrder newMarketplacePurchaseorder = new MarketplacePurchaseOrder();
			newMarketplacePurchaseorder.setOrderNo(orderNo);
			newMarketplacePurchaseorder.setCompanyName(purchasedOrgName);
			newMarketplacePurchaseorder.setPurchaseUuid(SourceLeadRandomCodeHelper.generateRandomUUID());
			newMarketplacePurchaseorder.setTotalPrice(candidateMarketCart.getAmount());			
			newMarketplacePurchaseorder.setOrderDate(candidateMarketCart.getCreatedDate());
			newMarketplacePurchaseorder.setCandidateMarketplace(candidateMarketCart.getMarketplaceId());
			newMarketplacePurchaseorder.setOrgLocationId(orgLocations);
			newMarketplacePurchaseorder.setPaymentStatus(Constants.SUCCESS);
         	candidateDao.saveToMarketplacePurchaseOrder(newMarketplacePurchaseorder);
			
		}
		
	}

	@Override
	public void saveToMarketplaceSalesOrder(List<CandidateMarketCart> marketCartList, String orderNo,String paymentId) throws Exception {
	
		
		String sourceOrgName ;
		for (CandidateMarketCart candidateMarketCart : marketCartList) {
			
		 sourceOrgName = candidateMarketCart.getSourceOrgId().getOrgName();	
		 MarketplaceSalesOrder newMarketplaceSalesorder = new MarketplaceSalesOrder();
		 newMarketplaceSalesorder.setOrderNo(orderNo);
		 newMarketplaceSalesorder.setTotalResumePrice(candidateMarketCart.getAmount());
		 newMarketplaceSalesorder.setSalesUuid(SourceLeadRandomCodeHelper.generateRandomUUID());
		 newMarketplaceSalesorder.setOrgLocationId(candidateMarketCart.getSourceOrgLocationId());
		 newMarketplaceSalesorder.setOrderDate(candidateMarketCart.getCreatedDate());
		 newMarketplaceSalesorder.setCandidateMarketPlace(candidateMarketCart.getMarketplaceId());
		 newMarketplaceSalesorder.setPaymentStatus(Constants.SUCCESS);
		 newMarketplaceSalesorder.setCompanyName(sourceOrgName);
		 
			candidateDao.saveToMarketplaceSalesOrder(newMarketplaceSalesorder);
	}

}

	@Override
	public CandidateMarketPlacePaymentTransactionHistory getOrderNo(Long id) throws Exception {
		
		return candidateDao.getOrderNo(id);
	}


	@Override
	public void deleteAllCandidatesFromMarketcart(Long orgLocationId) throws Exception {
		candidateDao.deleteAllCandidatesFromMarketcart(orgLocationId);
		
	}
	
	@Override
	public ResponseList<MarketplacePurchaseSalesDTO> getMarketplacePurchaseOrder(OrgLocations orgLocations,
			MarketplacePurchaseSalesDTO marketplacePurchaseSalesDTO,  String searchValue,String purchaseOrderFromDate,
			String purchaseOrderToDate, String purchaseOrderFilterDate) throws Exception {
		Date today = new Date();
		Date formattedDate = commonUtil.getOrgLocationCurrentDate(orgLocations.getDateFormat(), today);
		Calendar calendar = Calendar.getInstance();
		Map<String, Date> dateValue = new HashMap<>();

		if (purchaseOrderFilterDate.equalsIgnoreCase("today")) {
			dateValue.put("fromDate", formattedDate);
			dateValue.put("toDate", formattedDate);
		}
		if (purchaseOrderFilterDate.equalsIgnoreCase("1_week")) {
			calendar.setTime(formattedDate);
			calendar.add(Calendar.DAY_OF_YEAR, -7);
			Date newDate = calendar.getTime();
			Date newFormattedDate = commonUtil.getOrgLocationCurrentDate(orgLocations.getDateFormat(), newDate);
			dateValue.put("fromDate", formattedDate);
			dateValue.put("toDate", newFormattedDate);
		}
		if (purchaseOrderFilterDate.equalsIgnoreCase("1_month")) {
			calendar.setTime(formattedDate);
			calendar.add(Calendar.MONTH, -1);
			Date newDate = calendar.getTime();
			Date newFormattedDate = commonUtil.getOrgLocationCurrentDate(orgLocations.getDateFormat(), newDate);
			dateValue.put("fromDate", formattedDate);
			dateValue.put("toDate", newFormattedDate);
		}
		if (purchaseOrderFilterDate.equalsIgnoreCase("3_months")) {
			calendar.setTime(formattedDate);
			calendar.add(Calendar.MONTH, -3);
			Date newDate = calendar.getTime();
			Date newFormattedDate = commonUtil.getOrgLocationCurrentDate(orgLocations.getDateFormat(), newDate);
			dateValue.put("fromDate", formattedDate);
			dateValue.put("toDate", newFormattedDate);
		}
		if (purchaseOrderFilterDate.equalsIgnoreCase("6_months")) {
			calendar.setTime(formattedDate);
			calendar.add(Calendar.MONTH, -6);
			Date newDate = calendar.getTime();
			Date newFormattedDate = commonUtil.getOrgLocationCurrentDate(orgLocations.getDateFormat(), newDate);
			dateValue.put("fromDate", formattedDate);
			dateValue.put("toDate", newFormattedDate);
		}
		if (purchaseOrderFilterDate.equalsIgnoreCase("YTD")) {
			calendar.setTime(formattedDate);
			calendar.add(Calendar.YEAR, -1);
			Date newDate = calendar.getTime();
			Date newFormattedDate = commonUtil.getOrgLocationCurrentDate(orgLocations.getDateFormat(), newDate);
			dateValue.put("fromDate", formattedDate);
			dateValue.put("toDate", newFormattedDate);
		}
		if (purchaseOrderFilterDate.equalsIgnoreCase("custom")) {
			Date fromDateVal = commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(), purchaseOrderFromDate);
			Date toDateVal = commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(), purchaseOrderToDate);
			dateValue.put("fromDate", toDateVal);
			dateValue.put("toDate", fromDateVal);
		}
		ResponseList<MarketplacePurchaseSalesDTO> responseList = candidateDao.getMarketplacePurchaseOrder(orgLocations.getId(), marketplacePurchaseSalesDTO,orgLocations, searchValue, purchaseOrderFromDate, purchaseOrderToDate,dateValue);
		return responseList;
	}

	@Override
	public MarketplacePurchaseSalesDTO getPurchaseDetailByPurchaseUuid(String purchaseUuid, OrgLocations orgLocations) throws Exception {
		
		
		MarketplacePurchaseOrder mps = candidateDao.getPurchaseDetailByPurchaseUuid(purchaseUuid,orgLocations.getId());
		
		MarketplacePurchaseSalesDTO mpsDto = new MarketplacePurchaseSalesDTO();
		String startTime =  commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),mps.getOrderDate());
		mpsDto.setOrderNo(mps.getOrderNo());
		mpsDto.setOrderDate(startTime);
		mpsDto.setPurchaseUuid(mps.getPurchaseUuid());
		mpsDto.setDiscount(mps.getDiscount());
		mpsDto.setGrandTotal(mps.getGrandTotal());
		mpsDto.setTotalPrice(mps.getTotalPrice());
		mpsDto.setPaymentStatus(mps.getPaymentStatus());
		mpsDto.setTotalresume(mps.getTotalresume());
		mpsDto.setCompanyName(mps.getCompanyName());
		mpsDto.setServiceFee(mps.getServiceFee());
		mpsDto.setCandidateName(mps.getCandidateMarketplace().getCandidateId().getFullName());
		 return mpsDto;      
	}

	@Override
	public ResponseList<MarketplacePurchaseSalesDTO> getMarketplaceSalesOrderList(MarketplacePurchaseSalesDTO marketplacePurchaseSalesDTO, OrgLocations orgLocations, String searchValue, String salesOrderFromDate, String salesOrderToDate, String salesOrderFilterDate)throws Exception {
		Date today = new Date();
		Date formattedDate = commonUtil.getOrgLocationCurrentDate(orgLocations.getDateFormat(), today);
		Calendar calendar = Calendar.getInstance();
		Map<String, Date> dateValue = new HashMap<>();
		if(salesOrderFilterDate != null){
		if(salesOrderFilterDate.equalsIgnoreCase("today")){
			dateValue.put("fromDate", formattedDate);
			dateValue.put("toDate", formattedDate);
		}
		if(salesOrderFilterDate.equalsIgnoreCase("1_week")){
			calendar.setTime(formattedDate);
			calendar.add(Calendar.DAY_OF_YEAR, -7);
			Date newDate = calendar.getTime();
			Date newFormattedDate = commonUtil.getOrgLocationCurrentDate(orgLocations.getDateFormat(), newDate);
			dateValue.put("fromDate", formattedDate);
			dateValue.put("toDate", newFormattedDate);
		}
		if(salesOrderFilterDate.equalsIgnoreCase("1_month")){
			calendar.setTime(formattedDate);
			calendar.add(Calendar.MONTH, -1);
			Date newDate = calendar.getTime();
			Date newFormattedDate = commonUtil.getOrgLocationCurrentDate(orgLocations.getDateFormat(), newDate);
			dateValue.put("fromDate", formattedDate);
			dateValue.put("toDate", newFormattedDate);
		}
		if(salesOrderFilterDate.equalsIgnoreCase("3_months")){
			calendar.setTime(formattedDate);
			calendar.add(Calendar.MONTH, -3);
			Date newDate = calendar.getTime();
			Date newFormattedDate = commonUtil.getOrgLocationCurrentDate(orgLocations.getDateFormat(), newDate);
			dateValue.put("fromDate", formattedDate);
			dateValue.put("toDate", newFormattedDate);
		}
		if(salesOrderFilterDate.equalsIgnoreCase("6_months")){
			calendar.setTime(formattedDate);
			calendar.add(Calendar.MONTH, -6);
			Date newDate = calendar.getTime();
			Date newFormattedDate = commonUtil.getOrgLocationCurrentDate(orgLocations.getDateFormat(), newDate);
			dateValue.put("fromDate", formattedDate);
			dateValue.put("toDate", newFormattedDate);
		}
		if(salesOrderFilterDate.equalsIgnoreCase("YTD")){
			calendar.setTime(formattedDate);
			calendar.add(Calendar.YEAR, -1);
			Date newDate = calendar.getTime();
			Date newFormattedDate = commonUtil.getOrgLocationCurrentDate(orgLocations.getDateFormat(), newDate);
			dateValue.put("fromDate", formattedDate);
			dateValue.put("toDate", newFormattedDate);
		}
		if(salesOrderFilterDate.equalsIgnoreCase("custom")){
			Date fromDateVal = commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(), salesOrderFromDate);
			Date toDateVal = commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(), salesOrderToDate);
			dateValue.put("fromDate", toDateVal);
			dateValue.put("toDate", fromDateVal);
		}
		}
		ResponseList<MarketplacePurchaseSalesDTO> responseList = candidateDao.getMarketplaceSalesOrderList(marketplacePurchaseSalesDTO, orgLocations, searchValue, salesOrderFromDate, salesOrderToDate, dateValue);
		return responseList;
		}

	@Override
	public MarketplacePurchaseSalesDTO getMarketplaceSalesOrderBySalesUuid(OrgLocations orgLocations, String salesUuid) throws Exception {
		MarketplaceSalesOrder salesOrder = candidateDao.getMarketplaceSalesOrderBySalesUuid(orgLocations.getId(), salesUuid);
		MarketplacePurchaseSalesDTO salesOrderDto = null;
		String formattedDate =  null;
			salesOrderDto = new MarketplacePurchaseSalesDTO();
			BeanUtils.copyProperties(salesOrder, salesOrderDto);
			formattedDate =  commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(), salesOrder.getOrderDate());
			salesOrderDto.setOrderDate(formattedDate);
			salesOrderDto.setTotalresume(salesOrder.getNoOfResumes());
			salesOrderDto.setTotalPrice(salesOrder.getTotalResumePrice());
			salesOrderDto.setCandidateName(salesOrder.getCandidateMarketPlace().getCandidateId().getFullName());
		return salesOrderDto;
	}

	@Override
	public ResponseList<MarketplaceWalletDTO> getWalletList(MarketplaceWalletDTO marketplaceWalletDTO,
			String searchValue, String selectedTime, String transactionType, String fromDate, String toDate) throws Exception {
		Date today = new Date();
		Date formattedDate = commonUtil.getOrgLocationCurrentDate(marketplaceWalletDTO.getOrgLocations().getDateFormat(), today);
		Calendar calendar = Calendar.getInstance();
		Map<String, Date> dateValue = new HashMap<>();
		if(selectedTime.equalsIgnoreCase("today")){
			dateValue.put("fromDate", formattedDate);
			dateValue.put("toDate", formattedDate);
		}
		
		if(selectedTime.equalsIgnoreCase("weekAgo")){
			calendar.setTime(formattedDate);
			calendar.add(Calendar.DAY_OF_YEAR, -7);
			Date newDate = calendar.getTime();
			Date newFormattedDate = commonUtil.getOrgLocationCurrentDate(marketplaceWalletDTO.getOrgLocations().getDateFormat(), newDate);
			dateValue.put("fromDate", formattedDate);
			dateValue.put("toDate", newFormattedDate);
		}
		if(selectedTime.equalsIgnoreCase("thisMonth")){
			calendar.setTime(formattedDate);
			calendar.add(Calendar.MONTH, -1);
			Date newDate = calendar.getTime();
			Date newFormattedDate = commonUtil.getOrgLocationCurrentDate(marketplaceWalletDTO.getOrgLocations().getDateFormat(), newDate);
			dateValue.put("fromDate", formattedDate);
			dateValue.put("toDate", newFormattedDate);
		}
		if(selectedTime.equalsIgnoreCase("3monthsAgo")){
			calendar.setTime(formattedDate);
			calendar.add(Calendar.MONTH, -3);
			Date newDate = calendar.getTime();
			Date newFormattedDate = commonUtil.getOrgLocationCurrentDate(marketplaceWalletDTO.getOrgLocations().getDateFormat(), newDate);
			dateValue.put("fromDate", formattedDate);
			dateValue.put("toDate", newFormattedDate);
		}
		if(selectedTime.equalsIgnoreCase("6monthsAgo")){
			calendar.setTime(formattedDate);
			calendar.add(Calendar.MONTH, -6);
			Date newDate = calendar.getTime();
			Date newFormattedDate = commonUtil.getOrgLocationCurrentDate(marketplaceWalletDTO.getOrgLocations().getDateFormat(), newDate);
			dateValue.put("fromDate", formattedDate);
			dateValue.put("toDate", newFormattedDate);
		}
		if(selectedTime.equalsIgnoreCase("oneYearAgo")){
			calendar.setTime(formattedDate);
			calendar.add(Calendar.YEAR, -1);
			Date newDate = calendar.getTime();
			Date newFormattedDate = commonUtil.getOrgLocationCurrentDate(marketplaceWalletDTO.getOrgLocations().getDateFormat(), newDate);
			dateValue.put("fromDate", formattedDate);
			dateValue.put("toDate", newFormattedDate);
		}
		if(selectedTime.equalsIgnoreCase("customDates")){
			Date fromDateVal = commonUtil.orgLocationDateFormat(marketplaceWalletDTO.getOrgLocations().getDateFormat(), fromDate);
			Date toDateVal = commonUtil.orgLocationDateFormat(marketplaceWalletDTO.getOrgLocations().getDateFormat(), toDate);
			dateValue.put("fromDate", toDateVal);
			dateValue.put("toDate", fromDateVal);
		}
		return candidateDao.getWalletList(marketplaceWalletDTO, searchValue, transactionType, dateValue);
	}

	@Override
	public List<MarketplacePurchaseOrder> getPurchaseCandidateListByOrglocation(Long id) throws Exception{
		return candidateDao.getPurchaseCandidateListByOrglocation( id);
	}

	@Override
	public File getPurchaseCandidatePdf(List<MarketplacePurchaseOrder> purchaseCandidatesList, Long orgLocationId)
			throws Exception {
		return pdfUtil.createCandidatePdfTable(purchaseCandidatesList,orgLocationId);
	}

	@Override
	public File getPurchaseCandidateExcelFile(List<MarketplacePurchaseOrder> purchaseCandidatesList, Long orgLocationId)throws Exception {
		
		OrgLocations orgLocation = orgLocationsDao.getOrgLocations(orgLocationId);
		Workbook createExcelworkbook =  new XSSFWorkbook();
		CellStyle style = commonUtil.getCellStyle(createExcelworkbook);
        
		DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
		 StringBuilder excelSheet = new StringBuilder();
		 Sheet createExcelsheet;		
			 createExcelsheet = createExcelworkbook.createSheet(excelSheet.append("").append("Purchased Candidate").append(dateFormat.format(new Date())).toString());	 
		
		 
    	Row writeRow = null;
		String headers [] = Constants.PURCHASE_CANDIDATE_HEADERS;
		createExcelsheet = commonUtil.setExcelHeaders(createExcelsheet,style,headers,0);
		 int column=0;
	        for(MarketplacePurchaseOrder candidate : purchaseCandidatesList){
	       	 writeRow = createExcelsheet.createRow(column+1);
	       	 createExcelsheet = createXlsSheet(createExcelsheet,candidate,writeRow,purchaseCandidatesList,orgLocation);
	       	 column++;
	        }
	        StringBuilder orgNameSB = new StringBuilder();
	        File file;        
	        	 file = File.createTempFile(orgNameSB.append("").append("Purchased Candidates").append(dateFormat.format(new Date())).toString(),".xls");		     
	        
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

	private Sheet createXlsSheet(Sheet createExcelsheet, MarketplacePurchaseOrder candidate, Row writeRow,
			List<MarketplacePurchaseOrder> purchaseCandidatesList,OrgLocations orgLocation ) {
			if(candidate.getOrderNo() !=null && !"".equalsIgnoreCase(candidate.getOrderNo())){
				 writeRow.createCell(0).setCellValue(candidate.getOrderNo());}
			else {
				writeRow.createCell(0).setCellValue("--");
			}
			if(candidate.getOrderDate() !=null && !"".equalsIgnoreCase(commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(), candidate.getOrderDate()))) {
				
				writeRow.createCell(1).setCellValue(candidate.getOrderDate());}
			else {
				writeRow.createCell(1).setCellValue("--");
			}
			if(candidate.getCompanyName() !=null && !"".equalsIgnoreCase(candidate.getCompanyName())){
				writeRow.createCell(2).setCellValue(candidate.getCompanyName());}
				else {
					writeRow.createCell(2).setCellValue("--");
			}
			if(candidate.getTotalresume() !=null && !"".equalsIgnoreCase(candidate.getTotalresume())){
				writeRow.createCell(3).setCellValue(candidate.getTotalresume());}
				else {
					writeRow.createCell(3).setCellValue("--");
			}
			if(candidate.getTotalPrice() !=null && !"".equalsIgnoreCase(candidate.getTotalPrice().toString())){
				writeRow.createCell(4).setCellValue(candidate.getTotalPrice());}
				else {
					writeRow.createCell(4).setCellValue("--");
			}
			if(candidate.getServiceFee() !=null && !"".equalsIgnoreCase(candidate.getServiceFee())){
				writeRow.createCell(5).setCellValue(candidate.getServiceFee());}
				else {
					writeRow.createCell(5).setCellValue("--");
			}
			if(candidate.getDiscount() !=null && !"".equalsIgnoreCase(candidate.getDiscount())){
				writeRow.createCell(6).setCellValue(candidate.getDiscount());}
				else {
					writeRow.createCell(6).setCellValue("--");
			}
			if(candidate.getGrandTotal() !=null && !"".equalsIgnoreCase(candidate.getGrandTotal().toString())){
				writeRow.createCell(7).setCellValue(candidate.getGrandTotal());}
				else {
					writeRow.createCell(7).setCellValue("--");
			}
			if(candidate.getPaymentStatus() !=null && !"".equalsIgnoreCase(candidate.getPaymentStatus())){
				writeRow.createCell(8).setCellValue(candidate.getPaymentStatus());}
				else {
					writeRow.createCell(8).setCellValue("--");
			}
			
			return createExcelsheet;
	}

	@Override
	public void saveCandidateMarketPlaceWallet(CandidateMarketWallet wallet) throws Exception {
		candidateDao.saveCandidateMarketPlaceWallet(wallet);
	}

	@Override
	public Double getBalanceAmountFromWallet(Long id) {
		return candidateDao.getBalanceAmountFromWallet(id);
	}

	@Override
	public void updateMarketWalletAmount(Double balanceAmountValue, Long id) {
		candidateDao.updateMarketWalletAmount(balanceAmountValue, id);
	}

	@Override
	public void deleteCandidateFromMarketplaceByCandidateUuid(String candidateUuid) throws Exception {
		candidateDao.deleteCandidateFromMarketplaceByCandidateUuid(candidateUuid);
	}

	@Override
	public List<Candidate> getAllAssociateByLocation(Long orgLocationId) throws Exception {		
		return candidateDao.getAllAssociateByLocation(orgLocationId);
	}

	@Override
	public String getCandidatePanNumberByUserId(long userId, Long id) {
		return candidateDao.getCandidatePanNumberByUserId(userId, id);
	}

	@Override
	public List<Candidate> getOffshoreCandidatesByParentCandidateId(Long candidateId) throws Exception {
		return candidateDao.getOffshoreCandidatesByParentCandidateId(candidateId);
	}

	@Override
	public Candidate getCandidateDetailsByPanNumberAndId(String panNumber, Long orgLocationId) throws Exception {
		return candidateDao.getCandidateDetailsByPanNumberAndId(panNumber, orgLocationId);
	}

	@Override
	public Boolean validatePanNumber(String panNumber, Long orgLocationId) throws Exception {
		return candidateDao.validatePanNumber(panNumber, orgLocationId);
	}

	@Override
	public Candidate getCandidateDetailsByAadharNoAndId(String aadharNumber, Long orgLocationId) throws Exception {
		return candidateDao.getCandidateDetailsByAadharNoAndId(aadharNumber, orgLocationId);
	}

	@Override
	public Boolean validateAadharNumber(String aadharNumber, Long orgLocationId) throws Exception {
		return candidateDao.validateAadharNumber(aadharNumber, orgLocationId);
	}

	@Override
	public Candidate getCandidateDetailsBySsnNoAndId(String ssnNumber, Long orgLocationId) throws Exception {
		return candidateDao.getCandidateDetailsBySsnNoAndId(ssnNumber, orgLocationId);
	}
	
	@Override
	public List<Candidate> getCandidatesForUpdateWorkLocation(Long masterCandidateId, Long orgId) throws Exception {
		return candidateDao.getCandidatesForUpdateWorkLocation(masterCandidateId, orgId);
	}

	@Override
	public Boolean validateSsnNumber(String ssnNumber, Long orgLocationId) throws Exception {
		return candidateDao.validateSsnNumber(ssnNumber, orgLocationId);
	}

	@Override
	public String getCandidateDateOfBirthByUserId(long userId, OrgLocations orgLocation) {
		return candidateDao.getCandidateDateOfBirthByUserId(userId, orgLocation);
	}

	@Override
	public User getConsultantUserByCandidateIdOrMasterCandidateId(Long candidateId, Long masterCandidateId)
			throws Exception {
		return candidateDao.getConsultantUserByCandidateIdOrMasterCandidateId(candidateId, masterCandidateId);
	}

	
	
}

