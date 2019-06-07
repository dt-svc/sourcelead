package com.bis.sourcelead.service.impl;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;
import java.util.stream.Collectors;

import javax.mail.internet.InternetAddress;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.codehaus.jackson.map.ObjectMapper;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.SourceleadPasswordEncoder;
import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.couchbase.util.UserStatsCacheHelper;
import com.bis.sourcelead.dao.OrgDao;
import com.bis.sourcelead.dao.OrgLocationsDao;
import com.bis.sourcelead.dao.RequirementSubmissionDao;
import com.bis.sourcelead.dao.UserDao;
import com.bis.sourcelead.dto.AddUserDTO;
import com.bis.sourcelead.dto.AddUserProfileDTO;
import com.bis.sourcelead.dto.ApplicantsJoinedReportDTO;
import com.bis.sourcelead.dto.CandidateDTO;
import com.bis.sourcelead.dto.CustomFormFieldDTO;
import com.bis.sourcelead.dto.DashboardItemsDTO;
import com.bis.sourcelead.dto.FileInfo;
import com.bis.sourcelead.dto.ProbationDTO;
import com.bis.sourcelead.dto.Response;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.dto.UserDTO;
import com.bis.sourcelead.elk.index.UserIndex;
import com.bis.sourcelead.elk.index.helper.UserIndexHelper;
import com.bis.sourcelead.elk.setup.SourceleadELKBaseIndexer;
import com.bis.sourcelead.entity.AppliedLeaveDateView;
import com.bis.sourcelead.entity.AssociateBalanceLeaveView;
import com.bis.sourcelead.entity.AssociateGrades;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.Countries;
import com.bis.sourcelead.entity.DashboardItems;
import com.bis.sourcelead.entity.Department;
import com.bis.sourcelead.entity.EmailAttachment;
import com.bis.sourcelead.entity.EmailConfigurationFromMail;
import com.bis.sourcelead.entity.EmailMessage;
import com.bis.sourcelead.entity.EmailScenario;
import com.bis.sourcelead.entity.Employees;
import com.bis.sourcelead.entity.LeaveType;
import com.bis.sourcelead.entity.MasterCandidate;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgRelations;
import com.bis.sourcelead.entity.OrgSpoc;
import com.bis.sourcelead.entity.OrglocationFormFields;
import com.bis.sourcelead.entity.ProbationHistory;
import com.bis.sourcelead.entity.ProjectMembers;
import com.bis.sourcelead.entity.ProjectMembersHistory;
import com.bis.sourcelead.entity.Projects;
import com.bis.sourcelead.entity.Roles;
import com.bis.sourcelead.entity.Shift;
import com.bis.sourcelead.entity.SourceleadActions;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserCustomFieldData;
import com.bis.sourcelead.entity.UserRole;
import com.bis.sourcelead.enums.Result;
import com.bis.sourcelead.enums.Status;
import com.bis.sourcelead.reportsDTO.LeaveBalanceReportDTO;
import com.bis.sourcelead.service.AppliedLeaveDateService;
import com.bis.sourcelead.service.AssociateBalanceLeaveService;
import com.bis.sourcelead.service.CandidateService;
import com.bis.sourcelead.service.CountriesService;
import com.bis.sourcelead.service.DepartmentService;
import com.bis.sourcelead.service.EmailScenarioConfigurationService;
import com.bis.sourcelead.service.EmailScenarioService;
import com.bis.sourcelead.service.EmployeesService;
import com.bis.sourcelead.service.LeaveTypeService;
import com.bis.sourcelead.service.MasterCandidateService;
import com.bis.sourcelead.service.ModulesService;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.OrgRelationsService;
import com.bis.sourcelead.service.OrgSpocService;
import com.bis.sourcelead.service.OrglocationFormFieldsService;
import com.bis.sourcelead.service.ProbationHistoryService;
import com.bis.sourcelead.service.ProjectMembersService;
import com.bis.sourcelead.service.ProjectsService;
import com.bis.sourcelead.service.RegistrationService;
import com.bis.sourcelead.service.RequirementSubmissionService;
import com.bis.sourcelead.service.RolesService;
import com.bis.sourcelead.service.SourceleadActionsService;
import com.bis.sourcelead.service.StatesService;
import com.bis.sourcelead.service.UserCustomFieldDataService;
import com.bis.sourcelead.service.UserRoleService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.EmailScenerioUtil;
import com.bis.sourcelead.util.FileUploadUtil;
import com.bis.sourcelead.util.PdfUtil;
import com.bis.sourcelead.util.SourceLeadRandomCodeHelper;
import com.bis.sourcelead.util.UniversalSearchHelper;
import com.google.gson.JsonObject;
import com.ibm.icu.math.BigDecimal;

/**
 * This Service Class is used to get User Object
 * 
 * @author: Naresh Bolisetty
 * @date: 27-May-2016
 */

@Service
public class UserServiceImpl implements UserService {

	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	@Value("${defaultFromEmailAddressForProd}")
	private String defaultFromEmailAddressForProd;
	
	@Autowired
	private UserDao userDao;
	
	@Value("${static.path}")
    private String staticPath;
	
	
	@Autowired
	private UserRoleService userRoleService;
	
	@Autowired
	private RolesService rolesService;
	
	@Autowired
	private EmployeesService employeesService;
	
	@Autowired
	private SourceleadPasswordEncoder sourceleadPasswordEncoder;
	
	@Autowired
	private ProjectMembersService projectMembersService;
	
	@Autowired
	private ProjectsService projectsService;
	
	@Autowired
	EmailScenarioService emailScenarioService;
	
	@Autowired
	EmailScenerioUtil emailScenerioUtil;
	
	@Autowired
	UserIndexHelper userIndexHelper;
	
	@Autowired
    private SourceleadELKBaseIndexer<UserIndex> sourceleadELKBaseIndexer;
	
	@Autowired
	private OrgSpocService orgSpocService;
	
	@Autowired
	private OrgRelationsService orgRelationsService;
	
	@Value("${sourceleadUrl}")
    private String sourceleadUrl;
	
	@Value("${sourceleadLoginUrl}")
	private String sourceleadLoginUrl;
	
	@Autowired
	private CommonUtil commonUtil;
	
	@Autowired
	OrgLocationsDao orglocationsDao;
	
	@Autowired
	private UniversalSearchHelper universalSearchHelper;	
	
	@Autowired
	OrgDao orgDao;
	
	@Autowired
	private DepartmentService departmentService;
	
	@Autowired
	private CandidateService  candidateService;
	
	@Autowired
	MasterCandidateService masterCandidateService;
	
	@Autowired
	private OrgLocationService orgLocationService;
	
	@Autowired
	private RegistrationService registrationService;
	
	@Autowired
	private OrglocationFormFieldsService orglocationFormFieldsService;
	
	@Autowired
	private UserCustomFieldDataService userCustomFieldDataService;
	
	@Autowired
	StatesService statesService;
	
	@Autowired
	CountriesService countriesService;

	@Autowired
	EmailScenarioConfigurationService emailScenarioConfigurationService;

	@Autowired
	private AssociateBalanceLeaveService associateBalanceLeaveService;
	
	@Autowired
	RequirementSubmissionService requirementSubmissionService;
	
	@Autowired
	PdfUtil pdfUtil;
	
	@Autowired
	LeaveTypeService leaveTypeService;
	
	@Autowired
	AppliedLeaveDateService appliedLeaveDateService;
	
	@Autowired
	private ProbationHistoryService probationHistoryService;
	
	@Autowired
	private RequirementSubmissionDao requirementSubmissionDao;
	
	@Autowired
	private ModulesService modulesService;
	
	@Autowired
	private SourceleadActionsService sourceleadActionsService;
	
	@Value("${isCouchbaseEnabled}")
	private String isCouchbaseEnabled;
	
	@Autowired
	UserStatsCacheHelper userStatsCacheHelper;
	
	@Override
	public User findByUserName(String username) {
		logger.debug("In UserService class...");
		return userDao.findByUserName(username);
	}

	@Override
	public User addUser(User user) throws Exception {
		logger.debug("In UserService class...");
//		user.setCreatedDate(new Date());
		user.setUpdatedDate(new Date());
		return userDao.addUser(user);
	}
	
	 /**
	  * @author Sammeta David Raju
	  * 
	  * @date: 19-Aug-2016
	  * 
      *  This Method is for Common Storing Method For User Creation
      *  
      *  @return 
      */
	@SuppressWarnings("unused")
	@Override
	public User storeUser(AddUserDTO addUserDTO,CandidateDTO candidateDTO,User loginuser,OrgLocations orgLocations,String sourceType,String responderType,Org loginOrg) throws Exception {
	   User user1 = null;
	   User user = null;
	   Candidate candidate = null;
	   String password = null;
	   UserRole userRole = null;
	   boolean isUserActive = false;
	   boolean associateBalance = false;
	   if(StringUtils.isNotBlank(candidateDTO.getAssociateId())){
		   user = userDao.getUserById(Long.valueOf(candidateDTO.getAssociateId()));
	   }else {
		   user = userDao.findByUserName(addUserDTO.getEmail());
	   }
	   
	   if(null != user){
		  isUserActive = userRoleService.isUserActive(user.getId());
		 
		  if(StringUtils.isNotBlank(addUserDTO.getFirstName())) {
			  user.setFirstName(addUserDTO.getFirstName());
		  }
		 
		  if(StringUtils.isNotBlank(addUserDTO.getLastName())) {
			  user.setLastName(addUserDTO.getLastName());
		  }
		
		  if(StringUtils.isNotBlank(addUserDTO.getPhoneNumber())) {
			  user.setMobilePhone(addUserDTO.getPhoneNumber());
		  }
		  
		  user.setEmail(addUserDTO.getEmail());
		  user.setUserName(addUserDTO.getEmail());
		  
			  if(StringUtils.isNotBlank(addUserDTO.getSsn())) {
				  user.setSsn(addUserDTO.getSsn());
			  }
		  
		     if(StringUtils.isNotBlank(addUserDTO.getExtensionNumber())){
		    	user.setExtensionNumber(addUserDTO.getExtensionNumber());
		     }
		     if(StringUtils.isNotBlank(addUserDTO.getMiddleName())) {
			  user.setMiddelName(addUserDTO.getMiddleName());
			  }else{
			  user.setMiddelName(null);
			  }

			  if(StringUtils.isNotBlank(addUserDTO.getGender())) {
			  user.setGender(addUserDTO.getGender());
			  }else{
			  user.setGender(null);
			  }

			  if(StringUtils.isNotBlank(addUserDTO.getMaritalStatus())) {
			  user.setMaritalStatus(addUserDTO.getMaritalStatus());
			  }else{
			  user.setMaritalStatus(null);
			  }

			  if(StringUtils.isNotBlank(addUserDTO.getDateofBirth())){
			  try {
			  user.setDateOfBirth(commonUtil.orgLocationDateFormat(addUserDTO.getDateFormat(), addUserDTO.getDateofBirth()));

			  } catch (Exception e) {
			  e.printStackTrace();
			  }
			  }
	   }
	   
	   if(null == user){
		   user = getUserDetails(addUserDTO,loginuser.getUserCode());
		   password = SourceLeadRandomCodeHelper.generateRandomPassword();
 		   user.setPassword(sourceleadPasswordEncoder.encode(password));
 		   
 		  user.setVerificationUUID(RandomStringUtils.randomAlphanumeric(16));
 		  user.setUrlSentTime(new Date());
 		  user.setIsVerified(Constants.NO);
 		 associateBalance  = true;
		}else if(isUserActive == false){
			password = SourceLeadRandomCodeHelper.generateRandomPassword();
			user.setPassword(sourceleadPasswordEncoder.encode(password));
		}
	   
	    if(StringUtils.isNotBlank(addUserDTO.getPhoneNumber())){
			user.setMobilePhone(addUserDTO.getPhoneNumber());
		}
	    
	    if(StringUtils.isNotBlank(addUserDTO.getExtensionNumber())){
	    	user.setExtensionNumber(addUserDTO.getExtensionNumber());
	    }
		
	    if(sourceType.equalsIgnoreCase("PROJECTS")){
	 		user.setUserType("Employee");
	    } 
		
	    /*if(StringUtils.isNotBlank(addUserDTO.getWorkLocation())){
	    	user.setWorkLocation(orgLocationService.getOrgLocations(Long.parseLong(addUserDTO.getWorkLocation())));
	    }*/	
	    
	    
	    //For saving the User Role 
	    if(StringUtils.isNotBlank(candidateDTO.getAssociateId()) && user != null && user.getId() != null){
	    	 userRole = getUserRoleEditDetails(addUserDTO,user,loginuser.getUserCode(),orgLocations.getId());
	    } else {
	    	
    	   userRole = userRoleService.getUserDetailsByEmailAndLocationId(addUserDTO.getEmail(),orgLocations.getId());
	   	   if(userRole == null){
	   		   userRole = getUserRoleDetails(addUserDTO,user,loginuser.getUserCode(),orgLocations.getId());
	   		   
	   		   if(StringUtils.isNotBlank(addUserDTO.getDesignation())){
	   				 userRole.setDesignation(addUserDTO.getDesignation());
	   		    }
	   		   
	   		   if(null == user.getId()){
	   				userRole.setIsPrimary(1);
	   				 if(sourceType.equalsIgnoreCase("PROJECTS")){
	   					 userRole.setUserType("Employee");
	   				 }
	   		  }else{
	   			  UserRole primaryUserRole = userRoleService.getLoginUserPrimaryRole(user.getId());
	   			  if(primaryUserRole == null){
	   				  userRole.setIsPrimary(1);
	   			  }else{
	   				  userRole.setIsPrimary(0);
	   			  }
	   			}
	   	   }else {
	   		   userRole.setPhoneNumber(addUserDTO.getPhoneNumber());
	   			if(StringUtils.isNotBlank(addUserDTO.getExtensionNumber())) {
	   				userRole.setExtensionNumber(addUserDTO.getExtensionNumber());
	   			}
	   			
	   		 if(StringUtils.isNotBlank(addUserDTO.getDesignation())){
	   				 userRole.setDesignation(addUserDTO.getDesignation());
	   		    }
	   	   }
	    }
	   
	 
	   
	   
	   
	   
		HashSet<UserRole> userRoleList = new HashSet<UserRole>();
		if(StringUtils.isNotBlank(addUserDTO.getIsGlobalAccess())) {
			userRole.setIsGlobal(Constants.YES);
		}else {
			userRole.setIsGlobal(Constants.NO);	
		}
		
		if(StringUtils.isNotBlank(addUserDTO.getGrade())) {
			AssociateGrades grades = new AssociateGrades();
			grades.setId(Long.valueOf(addUserDTO.getGrade()));
			userRole.setGradeId(grades);
		}
		userRoleList.add(userRole);
		user.setUserRole(userRoleList);
		
	   //For saving Employee details (Every User is an Employee)
		Employees employee = null;
		if(null != user && null != user.getId()){
			employee = employeesService.getEmployeeByUserIdAndOrgLocationsId(user.getId(), orgLocations.getId());
		}
		if(null == employee){
			employee = getEmployeeDetails(user,addUserDTO,orgLocations.getId(),loginuser.getUserCode());
			HashSet<Employees> employeeList = new HashSet<Employees>();
			employeeList.add(employee);
			user.setEmployee(employeeList);
		}
		
		 //For saving the User  
		try {
			user1 = userDao.addUser(user);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		
		
		if(employee != null) {
			ProbationHistory probationHistory = new ProbationHistory();
			probationHistory.setEmployeeId(employee);
			if(StringUtils.isNotBlank(addUserDTO.getDateofJoin())){
			probationHistory.setDateOfJoin(commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(), addUserDTO.getDateofJoin()));
			}
			probationHistory.setProbationPeriod(addUserDTO.getProbationPeriod());
			probationHistory.setFromStatus(employee.getProbationStatus());
			probationHistory.setToStatus(addUserDTO.getProbationStatus());
			if(StringUtils.isNotBlank(addUserDTO.getConfirmationDate())){
			probationHistory.setActualConfirmationDate(commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(), addUserDTO.getConfirmationDate()));
			}
			
			if(StringUtils.isNotBlank(addUserDTO.getProbationStatus()) && addUserDTO.getProbationStatus().equalsIgnoreCase(Constants.CONFIRMED)) {
				probationHistory.setConfirmedDate(new Date());
			}else {
				probationHistory.setConfirmedDate(null);
			}
			probationHistory.setStatus(1);
			probationHistory.setUuid(SourceLeadRandomCodeHelper.generateUUID());
			probationHistory.setCreatedDate(new Date());
			probationHistory.setCreatedBy(loginuser.getId());
			probationHistoryService.saveProbationHistory(probationHistory);
		}

		if(addUserDTO.getCustomFormData() !=null && !responderType.equalsIgnoreCase("vendor") && !responderType.equalsIgnoreCase("client")) {
			for( CustomFormFieldDTO customFields:addUserDTO.getCustomFormData()) {
				if(customFields.getCustomFormFieldId() > 0 &&StringUtils.isNotEmpty(customFields.getCustomFormFieldData())) {
					UserCustomFieldData userCustomFields = null;
					if(customFields.getEntityId()!= null && customFields.getEntityId() > 0) {
						userCustomFields = userCustomFieldDataService.getUserCustomFieldDataById(customFields.getEntityId());
					}else {
						userCustomFields = new UserCustomFieldData();
					}
				userCustomFields.setUser(user1);
				OrglocationFormFields orgFormField = orglocationFormFieldsService.getOrglocationFormFieldsById(customFields.getCustomFormFieldId());
				userCustomFields.setOrglocationsFormFields(orgFormField);
				userCustomFields.setCustomFormFieldData(customFields.getCustomFormFieldData());
				userCustomFieldDataService.saveUserCustomFieldData(userCustomFields);
				}
				
			}	
		}
		
		
		
		//for updating cosultantUserId in candidate
		if(StringUtils.isNotBlank(candidateDTO.getAssociateId())){
			candidate = candidateService.getCandidateByConsultantUserId(Long.parseLong(candidateDTO.getAssociateId()), orgLocations.getId());
			if(null != candidate){
				candidate.setConsultantUser(user1);
				candidate.setUpdatedBy(loginuser.getUserCode());
				candidate.setUpdatedDate(commonUtil.currentDate());
				candidate.setIsAssociate(Constants.YES);
				candidate.setEmail(addUserDTO.getEmail());
				candidate.setFirstName(addUserDTO.getFirstName());
				candidate.setLastName(addUserDTO.getLastName());
				
				if(StringUtils.isNotBlank(addUserDTO.getSsn())) {
					candidate.setSsnNumber(addUserDTO.getSsn());
			    }
				
				if(StringUtils.isNotBlank(candidateDTO.getPanNumber())){
					candidate.setPanNumber(candidateDTO.getPanNumber());
				}else{
					candidate.setPanNumber(null);
				}
				if(StringUtils.isNotBlank(candidateDTO.getAadharNumber())){
					candidate.setAadharNumber(candidateDTO.getAadharNumber());
				}else{
					candidate.setAadharNumber(null);
				}
				if(StringUtils.isNotBlank(addUserDTO.getVendors())){
					OrgRelations orgRelation = orgRelationsService.getOrRelationsById(Long.parseLong(addUserDTO.getVendors()));
					candidate.setOrgRelationId(orgRelation);
				}else{
					candidate.setOrgRelationId(null);
				}
				
				if(StringUtils.isNotBlank(addUserDTO.getVendorcontact())){
					OrgSpoc orgspoc = orgSpocService.getOrgSpocById(Long.parseLong(addUserDTO.getVendorcontact()));
					candidate.setSpocId(orgspoc);
				}else{
					candidate.setSpocId(null);
				}
				if(StringUtils.isNotBlank(addUserDTO.getDesignationType())){
					candidate.setDesignationType(addUserDTO.getDesignationType());
				}
				
				if(StringUtils.isNotBlank(addUserDTO.getDesignationOtherType())){
					candidate.setDesignationOtherType(addUserDTO.getDesignationOtherType());
				}
				
				
			    if(StringUtils.isNotBlank(addUserDTO.getWorkLocation())){
				    OrgLocations workLocation = orgLocationService.getOrgLocations(Long.parseLong(addUserDTO.getWorkLocation()));
					candidate.setWorkLocation(workLocation);
			    }else {
			    	candidate.setWorkLocation(orgLocations);
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
			    	
			    	
			    	Countries country = countriesService.getCountriesByCountryName(addUserDTO.getResidenceCountry());
			    	String stateCode = statesService.getStateCodeByCountryCodeAndState(country.getCode2(),addUserDTO.getResidenceState());
			    	if(stateCode != null) {
			    		candidate.setResidenceStateCode(stateCode);
			    	}
			    	
			    }
				MasterCandidate masterCandidate = masterCandidateService.getMasterCandidateByInteligenceCandidateDTO(candidateDTO);
				if(null == masterCandidate){
					masterCandidate = masterCandidateService.storeMasterCandidateByCandidateDTO(candidateDTO, loginuser);
				}
				candidate.setMasterCandidateId(masterCandidate);
				candidateService.saveOrUpdateCandidate(candidate);
				candidateService.saveOrUpdateCandidateElkDocument(candidate);
			}	
			
			else{
				// existing candidate saving as a User
				candidate = candidateService.findByCandidateEmail(candidateDTO.getEmail(),orgLocations.getId());
				if(candidate == null){
					MasterCandidate masterCandidate = null;
					masterCandidate = masterCandidateService.getMasterCandidateByInteligenceCandidateDTO(candidateDTO);
					if(null == masterCandidate){
						masterCandidate = masterCandidateService.storeMasterCandidateByCandidateDTO(candidateDTO, loginuser);
					}
				 candidate = candidateService.storeCandidateForUsers(candidateDTO, addUserDTO,user, orgLocations, masterCandidate,loginuser);
					
				}else{
					candidate.setConsultantUser(user);
				    if(StringUtils.isNotBlank(addUserDTO.getWorkLocation())){
					    OrgLocations workLocation = orgLocationService.getOrgLocations(Long.parseLong(addUserDTO.getWorkLocation()));
						candidate.setWorkLocation(workLocation);
				    }else {
				    	candidate.setWorkLocation(orgLocations);
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
				    	
				    	
				    	Countries country = countriesService.getCountriesByCountryName(addUserDTO.getResidenceCountry());
				    	String stateCode = statesService.getStateCodeByCountryCodeAndState(country.getCode2(),addUserDTO.getResidenceState());
				    	if(stateCode != null) {
				    		candidate.setResidenceStateCode(stateCode);
				    	}
				    	
				    }
					if(StringUtils.isNotBlank(candidateDTO.getPanNumber())){
						candidate.setPanNumber(candidateDTO.getPanNumber());
					}else{
						candidate.setPanNumber(null);
					}
					if(StringUtils.isNotBlank(candidateDTO.getAadharNumber())){
						candidate.setAadharNumber(candidateDTO.getAadharNumber());
					}else{
						candidate.setAadharNumber(null);
					}
					candidate.setIsAssociate(Constants.YES);
					candidateService.addCandidate(candidate);
					candidateService.saveOrUpdateCandidateElkDocument(candidate);
				}
				
			}
		}else{
			// existing candidate saving as a User
			candidate = candidateService.findByCandidateEmail(candidateDTO.getEmail(),orgLocations.getId());
			if(candidate == null){
				MasterCandidate masterCandidate = null;
				masterCandidate = masterCandidateService.getMasterCandidateByInteligenceCandidateDTO(candidateDTO);
				if(null == masterCandidate){
					masterCandidate = masterCandidateService.storeMasterCandidateByCandidateDTO(candidateDTO, loginuser);
				}
			 candidate = candidateService.storeCandidateForUsers(candidateDTO, addUserDTO,user, orgLocations, masterCandidate,loginuser);
				
			}else{
				if(StringUtils.isNotBlank(candidateDTO.getPanNumber())){
					candidate.setPanNumber(candidateDTO.getPanNumber());
				}else{
					candidate.setPanNumber(null);
				}
				if(StringUtils.isNotBlank(candidateDTO.getAadharNumber())){
					candidate.setAadharNumber(candidateDTO.getAadharNumber());
				}else{
					candidate.setAadharNumber(null);
				}
				candidate.setConsultantUser(user);
				candidate.setIsAssociate(Constants.YES);
				candidateService.addCandidate(candidate);
				candidateService.saveOrUpdateCandidateElkDocument(candidate);
			}
			
		}
		
		//For saving Employee details (Every User is an Employee)
		if(StringUtils.isNotBlank(candidateDTO.getProjectId())){
			ProjectMembers pm = getProjectMembersDetails(employee,candidateDTO,loginuser.getUserCode(),orgLocations.getId());
			try {
				projectMembersService.saveProjectMember(pm);
			} catch (Exception e) {
				e.printStackTrace();
			}
			//commonUtil.storeTimesheetSubmissionRecords(user);
		}
		
		   //send mail to user
			if(("".equals(responderType))  && (sourceType.equalsIgnoreCase("CREATEUSER") || sourceType.equalsIgnoreCase("PROJECTS"))
					 &&  (StringUtils.isNotBlank(candidateDTO.getIsMailSent()))
					 && ("Y".equals(candidateDTO.getIsMailSent()))){

				sendEmail(user,loginuser,password,orgLocations.getId());
			}
			
			
			/**
			 * @author Laxman
			 * Update the leaveTypes
			 */
			if(associateBalance == true) {
			     associateBalanceLeaveService.addAssociateLeaveType(user1,orgLocations.getId(),employee,userRole);
			}
			
			
			/**
			 * This method is used to newly added admin as a level2 manager for all existing projectmembers
			 * @author: Naresh Bolisetty
			 * @throws Exception 
			 * 
			 */
			/*if(userRole != null && StringUtils.isNotEmpty(userRole.getRoles().getRoleName()) && userRole.getRoles().getRoleName().equalsIgnoreCase(Constants.ROLE_ADMIN)){
				commonUtil.addAdminAsSecondLevelManager(orgLocations.getId(), user, loginuser);
			}*/
			/*
			 * Saving USER DB data as ElasticSearch Documents
			 */
			
			if(isUserActive == false && Constants.YES.equalsIgnoreCase(addUserDTO.getIsMailSent()))
				registrationService.sendActivationEmail(user1,orgLocations);
			
			saveOrUpdateUserElkDocument(userRole,employee,loginuser.getUserCode());
			
			if(isCouchbaseEnabled.equalsIgnoreCase("true")) {
				userRole = userRoleService.getUserRoleById(userRole.getId());
				userStatsCacheHelper.saveUserProfileDetailsToCouchbase(userRole);
			}
			
			user1.setTempPwd(password);
			user1.setCandidateId(candidate.getId());
        return user1;
				
		
	}
	
	@Override
	public void createUser(JSONObject jsonObj,User loginUser , Long orgLocationsId) throws Exception {
		/*logger.debug("In UserService class createUser method...");
		//For Saving of User Details
		User user = getUserDetails(jsonObj,loginUser.getUserCode());
		String password = SourceLeadRandomCodeHelper.generateRandomPassword();
		user.setPassword(sourceleadPasswordEncoder.encode(password));
		try {
			userDao.addUser(user);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		//For saving the User Role 
		UserRole userRole = getUserRoleDetails(jsonObj,user,loginUser.getUserCode(),orgLocationsId);
		try {
			userRoleService.addUserRole(userRole);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	   //For saving Employee details (Every User is an Employee)
		Employees employee = getEmployeeDetails(user,jsonObj,orgLocationsId,loginUser.getUserCode());
		try {
			employeesService.addEmployee(employee);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		//For saving Employee details (Every User is an Employee)
		if(jsonObj.has("projectId") && !isNullOrEmpty(jsonObj.getString("projectId"))){
			ProjectMembers pm = getProjectMembersDetails(employee,jsonObj,loginUser.getUserCode(),orgLocationsId);
			try {
				projectMembersService.saveProjectMember(pm);
			} catch (Exception e) {
				e.printStackTrace();
			}
			//commonUtil.storeTimesheetSubmissionRecords(user);
		}
		
		
		//send mail to user
		if((jsonObj.has("isMailSent") && !isNullOrEmpty(jsonObj.getString("isMailSent"))) && ("Y".equals(jsonObj.getString("isMailSent")))){

			sendEmail(user,loginUser,password,orgLocationsId);
		}
		
		 * Saving/Update USER DB data as ElasticSearch Documents
		 
		saveOrUpdateUserElkDocument(userRole,employee,loginUser.getUserCode());*/

		 
	}
	
	private User getUserDetails(AddUserDTO addUserDTO,String userCode) throws JSONException {
		User user = new User();
		
		if(StringUtils.isNotBlank(addUserDTO.getFirstName())){
			user.setFirstName(addUserDTO.getFirstName());
		}else{
			user.setFirstName(null);
		}
		
		if(StringUtils.isNotBlank(addUserDTO.getLastName())){
			user.setLastName(addUserDTO.getLastName());
		}else{
			user.setLastName(null);
		}
		
		if(StringUtils.isNotBlank(addUserDTO.getEmail())){
			user.setEmail(addUserDTO.getEmail());
			user.setUserName(addUserDTO.getEmail());
		}else{
			user.setEmail(null);
			user.setUserName(null);
		}
		
	    if(StringUtils.isNotBlank(addUserDTO.getSsn())) {
			  user.setSsn(addUserDTO.getSsn());
	    }
	    
	    if(StringUtils.isNotBlank(addUserDTO.getMiddleName())) {
			  user.setMiddelName(addUserDTO.getMiddleName());
	    }else{
	    	 user.setMiddelName(null);
	    }
	    
	    if(StringUtils.isNotBlank(addUserDTO.getGender())) {
			  user.setGender(addUserDTO.getGender());
	    }else{
	    	 user.setGender(null);
	    }
	    
	    if(StringUtils.isNotBlank(addUserDTO.getMaritalStatus())) {
			  user.setMaritalStatus(addUserDTO.getMaritalStatus());
	    }else{
	    	 user.setMaritalStatus(null);
	    }
	    
	    if(StringUtils.isNotBlank(addUserDTO.getAllowEmployeeToFill()) && ("Y".equals(addUserDTO.getAllowEmployeeToFill()))){
	    	user.setAllowEmployeeToFill(Status.Y);
			}else{
				user.setAllowEmployeeToFill(Status.N);
			}
	    
	    if(StringUtils.isNotBlank(addUserDTO.getDateofBirth())){
			try {
				user.setDateOfBirth(commonUtil.orgLocationDateFormat(addUserDTO.getDateFormat(), addUserDTO.getDateofBirth()));
				  
				} catch (Exception e) {
				  e.printStackTrace();
				}
		}
		
		user.setUserCode(SourceLeadRandomCodeHelper.generateRandomUserCode());
		user.setCreatedBy(userCode);
		user.setCreatedDate(commonUtil.currentDate());
		return user;
	}
	
	private UserRole getUserRoleDetails(AddUserDTO addUserDTO,User user,String userCode, Long orgLocationsId) throws Exception {
		UserRole userRole = new UserRole();
		userRole.setUser(user);
		
		OrgLocations orgLocations = new OrgLocations();
		orgLocations.setId(orgLocationsId);
		userRole.setOrgLocations(orgLocations);
		
		if(StringUtils.isNotBlank(addUserDTO.getReportingManager())){
			User reportingManager = new User();
			reportingManager.setId(Long.parseLong(addUserDTO.getReportingManager()));
			userRole.setReportingManager(reportingManager);
		}else {
			userRole.setReportingManager(user);
		}
		
		if(StringUtils.isNotBlank(addUserDTO.getResourceManager())){
			User resourceManager = new User();
			resourceManager.setId(Long.parseLong(addUserDTO.getResourceManager()));
			userRole.setResourceManager(resourceManager);
		}
		
		if(StringUtils.isNotBlank(addUserDTO.getShiftId())) {
			Shift shift = new Shift();
			shift.setId(Long.parseLong(addUserDTO.getShiftId()));
			userRole.setShift(shift);
		}
		
	    if(StringUtils.isNotBlank(addUserDTO.getIsMailSent()) && ("Y".equals(addUserDTO.getIsMailSent()))){
	    	userRole.setIsMailSent(Status.Y);
			}else{
				userRole.setIsMailSent(Status.N);
			}
		   
		userRole.setUserState(Status.NOT_LOGGEDIN);
		
		Roles roles = null;
		
		if(StringUtils.isNotBlank(addUserDTO.getRoleType())){
			try {
				roles = rolesService.getRolesById(Long.parseLong(addUserDTO.getRoleType()));
				userRole.setUserType(roles.getDisplayName());
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		
		try {

			if(StringUtils.isNotBlank(addUserDTO.getRoleTypeId())){
			roles = rolesService.getRolesById(Long.parseLong(addUserDTO.getRoleTypeId()));
			}else if(StringUtils.isNotBlank(addUserDTO.getRoleType())){
			roles = rolesService.getRolesById(Long.parseLong(addUserDTO.getRoleType()));
			userRole.setUserType(roles.getDisplayName()); 
			}
			else{
			roles = rolesService.getRolesByRoleNameAndOrgLocationId(Constants.ROLE_MANAGER,orgLocations.getId());
			}


		} catch (Exception e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}
		userRole.setPhoneNumber(addUserDTO.getPhoneNumber());
		if(StringUtils.isNotBlank(addUserDTO.getExtensionNumber())) {
			userRole.setExtensionNumber(addUserDTO.getExtensionNumber());
		}
		
		userRole.setRoles(roles);
		userRole.setUserType(roles.getDisplayName());
		userRole.setCreatedDate(commonUtil.currentDate());
		userRole.setCreatedBy(userCode);
		userRole.setUpdatedDate(commonUtil.currentDate());
		userRole.setUpdatedBy(userCode);
		userRole.setUuid(SourceLeadRandomCodeHelper.generateRandomUUID());
		return userRole;
	}
	
	
	
	private UserRole getUserRoleEditDetails(AddUserDTO addUserDTO,User user,String userCode, Long orgLocationsId) throws Exception {
		UserRole userRole = userRoleService.getUserRoleByUserIdAndLocationId(user.getId(), orgLocationsId);
		userRole.setUser(user);
		
		OrgLocations orgLocations = new OrgLocations();
		orgLocations.setId(orgLocationsId);
		userRole.setOrgLocations(orgLocations);
		
		if(StringUtils.isNotBlank(addUserDTO.getReportingManager())){
			User reportingManager = new User();
			reportingManager.setId(Long.parseLong(addUserDTO.getReportingManager()));
			userRole.setReportingManager(reportingManager);
		}else {
			userRole.setReportingManager(user);
		}
		
		if(StringUtils.isNotBlank(addUserDTO.getResourceManager())){
			User resourceManager = new User();
			resourceManager.setId(Long.parseLong(addUserDTO.getResourceManager()));
			userRole.setResourceManager(resourceManager);
		}
		
		if(StringUtils.isNotBlank(addUserDTO.getShiftId())) {
			Shift shift = new Shift();
			shift.setId(Long.parseLong(addUserDTO.getShiftId()));
			userRole.setShift(shift);
		}
		
	    if(StringUtils.isNotBlank(addUserDTO.getIsMailSent()) && ("Y".equals(addUserDTO.getIsMailSent()))){
	    	userRole.setIsMailSent(Status.Y);
			}else{
				userRole.setIsMailSent(Status.N);
			}
		   
		userRole.setUserState(Status.NOT_LOGGEDIN);
		
		Roles roles = null;
		
		if(StringUtils.isNotBlank(addUserDTO.getRoleType())){
			try {
				roles = rolesService.getRolesById(Long.parseLong(addUserDTO.getRoleType()));
				userRole.setUserType(roles.getDisplayName());
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		
		try {

			if(StringUtils.isNotBlank(addUserDTO.getRoleTypeId())){
			roles = rolesService.getRolesById(Long.parseLong(addUserDTO.getRoleTypeId()));
			}else if(StringUtils.isNotBlank(addUserDTO.getRoleType())){
			roles = rolesService.getRolesById(Long.parseLong(addUserDTO.getRoleType()));
			userRole.setUserType(roles.getDisplayName()); 
			}
			else{
			roles = rolesService.getRolesByRoleNameAndOrgLocationId(Constants.ROLE_MANAGER,orgLocations.getId());
			}


		} catch (Exception e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}
		userRole.setPhoneNumber(addUserDTO.getPhoneNumber());
		if(StringUtils.isNotBlank(addUserDTO.getExtensionNumber())) {
			userRole.setExtensionNumber(addUserDTO.getExtensionNumber());
		}
		
		userRole.setRoles(roles);
		userRole.setUserType(roles.getDisplayName());
		userRole.setCreatedDate(commonUtil.currentDate());
		userRole.setCreatedBy(userCode);
		userRole.setUpdatedDate(commonUtil.currentDate());
		userRole.setUpdatedBy(userCode);
		return userRole;
	}
	
	private Employees getEmployeeDetails(User user,AddUserDTO addUserDTO, Long orgLocationsId,String userCode) throws NumberFormatException, Exception {
		Employees employees = new Employees();
		employees.setUser(user);
		OrgLocations orgLocations = new OrgLocations();
		orgLocations.setId(orgLocationsId);
		employees.setOrgLocations(orgLocations);
		if(StringUtils.isNotBlank(addUserDTO.getEmployeeId())){
			employees.setEmployeeId(addUserDTO.getEmployeeId());
		}
		if(StringUtils.isNotBlank(addUserDTO.getRate())){
			employees.setRate(addUserDTO.getRate());
		}
		if(StringUtils.isNotBlank(addUserDTO.getRatePer())){
			employees.setRatePer(addUserDTO.getRatePer());
		}
		if(StringUtils.isNotBlank(addUserDTO.getDayHours())){
			employees.setDailyHours(addUserDTO.getDayHours());
		}
		if(StringUtils.isNotBlank(addUserDTO.getCurrentCurrency())){
			employees.setCurrentCurrency(addUserDTO.getCurrentCurrency());
		}
		
		if(StringUtils.isNotBlank(addUserDTO.getDesignationType())){
			employees.setUserEmploymentType(addUserDTO.getDesignationType());
		}
		
		if(StringUtils.isNotBlank(addUserDTO.getDesignationOtherType())){
			employees.setDesignationOtherType(addUserDTO.getDesignationOtherType());
		}
		
		if(StringUtils.isNotBlank(addUserDTO.getDepartment())){
			Department department= departmentService.getDepartmentById(Long.parseLong(addUserDTO.getDepartment()));
			employees.setDepartmentId(department);
		}else{
			employees.setDepartmentId(null);
		}
		
		if(StringUtils.isNotBlank(addUserDTO.getVendors())){
			OrgRelations orgRelation = orgRelationsService.getOrRelationsById(Long.parseLong(addUserDTO.getVendors()));
			employees.setOrgRelationId(orgRelation);
		}else{
			employees.setOrgRelationId(null);
		}
		
		if(StringUtils.isNotBlank(addUserDTO.getVendorcontact())){
			OrgSpoc orgspoc = orgSpocService.getOrgSpocById(Long.parseLong(addUserDTO.getVendorcontact()));
			employees.setSpocId(orgspoc);
		}else{
			employees.setSpocId(null);
		}
		/*if(jsonObj.has("invoiceFrequency")){
			if(jsonObj.getString("invoiceFrequency") != null && !"".equalsIgnoreCase(jsonObj.getString("invoiceFrequency"))){
				employees.setInvoiceFrequency(jsonObj.getString("invoiceFrequency"));
			}else{
				employees.setInvoiceFrequency("Weekly");
			}
			
		}*/
		
		if(StringUtils.isNotBlank(addUserDTO.getDateofJoin())){
			try {
				 employees.setHireDate(commonUtil.orgLocationDateFormat(addUserDTO.getDateFormat(), addUserDTO.getDateofJoin()));
				  
				} catch (Exception e) {
				  e.printStackTrace();
				}
		}
		
		if(StringUtils.isNotBlank(addUserDTO.getConfirmationDate())){
			try {
				 employees.setConfirmationDate(commonUtil.orgLocationDateFormat(addUserDTO.getDateFormat(), addUserDTO.getConfirmationDate()));
				  
				} catch (Exception e) {
				  e.printStackTrace();
				}
		}else {
				employees.setConfirmationDate(null);
		}
		
		if(StringUtils.isNotBlank(addUserDTO.getProbationPeriod())){
				  employees.setProbationPeriod(addUserDTO.getProbationPeriod());
		}else {
				  employees.setProbationPeriod(null);
		}
		
		
		if(StringUtils.isNotBlank(addUserDTO.getProbationStatus())){
				  employees.setProbationStatus(addUserDTO.getProbationStatus());
		}else {
				  employees.setProbationStatus(null);
		}
		employees.setCreatedBy(userCode);
		employees.setCreatedDate(commonUtil.currentDate());
		return employees;
	}

	
	private ProjectMembers getProjectMembersDetails(Employees employee, CandidateDTO candidateDTO, String userCode, Long orgLocationsId) throws Exception {
		ProjectMembers pm = new ProjectMembers();

		
		/*if(jsonObj.has("projectId") && !isNullOrEmpty(jsonObj.getString("projectId"))){
			Projects project = new Projects();

			project.setId(Long.parseLong(jsonObj.getString("projectId")));
			pm.setProjects(project);
		}else{
			//To add default project
			project = projectsService.getPrimaryProjectByOrgLocationsId(orgLocationsId);
			pm.setProjects(project);
		}*/
		Projects project = new Projects();
		project.setId(Long.parseLong(candidateDTO.getProjectId()));
		pm.setProjects(project);
		pm.setEmployee(employee);
		pm.setStatus(Constants.MEMBER_ACTIVE);
		
		HashSet<ProjectMembersHistory> pmhList = new HashSet<ProjectMembersHistory>();
		ProjectMembersHistory pmh = new ProjectMembersHistory();
	    Projects  newproject	=  projectsService.getProjectById(project.getId());
		pmh.setProjectTerminationDate(newproject.getEndDate());
		pmh.setProjectMemberId(pm);
		pmh.setStatus(Constants.PROJECT_MEMBER_HISTORY_OPEN);
		pmh.setProjectAssignedDate(commonUtil.currentDate());
        pmh.setCreatedDate(commonUtil.currentDate());
		pmh.setCreatedBy(userCode);
		pmh.setUpdatedDate(commonUtil.currentDate());
		pmh.setUpdatedBy(userCode);
		pmhList.add(pmh);
		
		pm.setCreatedBy(userCode);
		pm.setCreatedDate(commonUtil.currentDate());
		pm.setUpdatedDate(commonUtil.currentDate());
		pm.setMemberHistoryList(pmhList);
		return pm;
	}
	
	@Override
	public void sendEmail(User user, User loginUser, String password, Long locationId) throws Exception {
		
		 UserRole userRole = userRoleService.getUserRoleByUserIdAndLocationId(user.getId(), locationId);
		 if(userRole == null){
		    userRole = userRoleService.getLoginUserPrimaryRole(user.getId());
		 }
		OrgLocations locations= orgLocationService.getOrgLocations(locationId);
		 
		 StringBuilder sb = new StringBuilder();
		 EmailMessage emailMessage = new EmailMessage();
		 emailMessage.setFirstName(user.getFirstName());
		 emailMessage.setLastName(user.getLastName());
		 emailMessage.setToEmail(user.getEmail());
		 emailMessage.setFromEmail(loginUser.getEmail());
		 emailMessage.setFromName(sb.append(loginUser.getFirstName()).append(" ").append(loginUser.getLastName()).toString());
		 emailMessage.setEmailType(Constants.CUSTOM_USER_EMAIL);

		 emailMessage.setCreatedBy(loginUser.getUserCode());
		 emailMessage.setOrgLocationId(locationId);
		 
		//setting the class filed values
		 Map<String, String> globalVarsMap = new HashMap<String, String>();
		 StringBuilder builder=new StringBuilder();
		 if(user.getLastName() != null && user.getLastName() !=""){
			 globalVarsMap.put("FirstName",builder.append(user.getFirstName()).append(" ").append(user.getLastName()).toString());	 
		 }else{
			 globalVarsMap.put("FirstName", user.getFirstName());	 
		 }
		 String loginUserFullName = loginUser.getFullName().substring(0, 1).toUpperCase() + loginUser.getFullName().substring(1);
		 globalVarsMap.put("loginUser",loginUserFullName);
		 globalVarsMap.put("orgName", locations.getOrgLocationName());
		 globalVarsMap.put("Role",userRole.getUserType());
		 StringBuilder loginBuilder = new StringBuilder();
		 globalVarsMap.put("LoginURL",loginBuilder.append("<a href='").append(sourceleadLoginUrl).append("' style='text-decoration:none; '><div style=' width:100px; border-radius:30px; background-color:#a21e23; color:#FFF; font-size:15px; font-weight:500; margin:10px auto 5px auto; padding:5px 0px 10px; text-align:center;'>Login</div></a>").toString());
		 globalVarsMap.put("UserEmailId",user.getEmail());
		 if(password != null){
			 globalVarsMap.put("Password",password);
		 }else{
			 globalVarsMap.put("Password","Login with your existing sourcelead credentials");
		 }
		List<EmailAttachment> attachmentList=new ArrayList<EmailAttachment>();
		
		commonUtil.sendTemplateEmail(emailMessage, globalVarsMap,userRole.getOrgLocations().getOrg(),userRole.getOrgLocations(),Constants.INVITE_USER,attachmentList);
		
	}

	@Override
	public String validateEmailandCandidate(String website, String email, String sourceleadBLockedwebsites)
			throws Exception {
		logger.debug("In UserService class...");
		return userDao.validateEmailandCandidate(website, email, sourceleadBLockedwebsites);
	}

	@Override
	public Collection<User> getUsers() throws Exception {
		logger.debug("In UserService class...");
		return userDao.getUsers();
	}

	@Override
	public User findByEmail(String email) throws Exception {
		logger.debug("In UserService class...");
		return userDao.findByEmail(email);
	}
	
	@Override
	public User findByEmailAndOrgLocationsId(String email, Long orgLocationsId) throws Exception {
		logger.debug("In UserService class...");
		return userDao.findByEmailAndOrgLocationsId(email,orgLocationsId);
	}

	@Override
	public User getUserById(Long Id) throws Exception {
		logger.debug("In UserService class...");
		return userDao.getUserById(Id);
	}
	
	@Override
	public User getUserByUserCode(String userCode) throws Exception {
		logger.debug("In UserService class...");
		return userDao.getUserByUserCode(userCode);
	}
	
	@Override
	public User getUserByIdAndOrgLocationsId(Long Id, Long orgLocationsId) throws Exception {
		logger.debug("In UserService class...");
		return userDao.getUserByIdAndOrgLocationsId(Id,orgLocationsId);
	}
	
	@Override
	public User saveUserProfileImage(AddUserProfileDTO addUserProfileDTO, User user) throws Exception {
		
		user = getUserProfileImage(addUserProfileDTO,user);
		userDao.addUser(user);
		return user;
	}
	
	private User getUserProfileImage(AddUserProfileDTO addUserProfileDTO,User user){
		
		if(addUserProfileDTO.getFileInfo() != null){
			FileInfo fileInfo = addUserProfileDTO.getFileInfo().get(0);
			user.setFileContentType(fileInfo.getContentType());
	   		user.setFileName(fileInfo.getFileName());
	   		user.setFileUrl(fileInfo.getFileURL());
	   		try {
				FileUploadUtil.moveFile(addUserProfileDTO.getStaticPath(), fileInfo, "profile");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return user; 
	}

	@Override
	public User saveUserProfile(AddUserProfileDTO addUserProfileDTO, User user) throws Exception {
		
		user = getUserProfileDetails(addUserProfileDTO,user);
		userDao.addUser(user);
		return user;
	}
	
	
	private User getUserProfileDetails(AddUserProfileDTO addUserProfileDTO,User user){
		user.setFirstName(addUserProfileDTO.getFirstName());
		user.setLastName(addUserProfileDTO.getLastName());
		user.setMobilePhone(addUserProfileDTO.getMobilePhone());
		user.setJobTitle(addUserProfileDTO.getJobTitle());
		user.setLocation(addUserProfileDTO.getLocation());
		user.setLinkedIn(addUserProfileDTO.getLinkedIn());
		user.setFacebook(addUserProfileDTO.getFacebook());
		user.setTwitter(addUserProfileDTO.getTwitter());
		user.setGooglePlus(addUserProfileDTO.getGooglePlus());
		
        /*if(null != multiFile.getContentType() && !multiFile.getContentType().equalsIgnoreCase("application/octet-stream")){
    	  try {
    	   		user.setFileContentType(multiFile.getContentType());
    	   		user.setDocFile(multiFile.getBytes());
    	   
	       }catch (Exception e) {
    	      	 e.printStackTrace();
			    }
        }*/
		
		
		return user; 
	}

	@Override
	public Collection<User> getUsersByUserRoles(Long orgLocationsId) throws Exception {
		return userDao.getUsersByUserRoles(orgLocationsId);

	}
	
	@Override
	public List<User> getUsersByUserRolesAndOrgLocationIdAndNoCurrentUser(Long userId, Long orgLocationsId) throws Exception {
		return userDao.getUsersByUserRolesAndOrgLocationIdAndNoCurrentUser(userId,orgLocationsId);
	}
	
	@Override
	public List<User> getUsersByUserRolesAndOrgLocationId(Long orgLocationsId) throws Exception {
		return userDao.getUsersByUserRolesAndOrgLocationId(orgLocationsId);
	}
	
	protected static boolean isNullOrEmpty(String str) {
		return (str == null || "".equals(str.trim()));
	}

	@Override
	public List<User> getUsersRoleCountByRoleIdAndLocationIdAndNotLoggedInUser(Long roleId, Long orgLocationsId,Long userId) throws Exception {
		return userDao.getUsersRoleCountByRoleIdAndLocationIdAndNotLoggedInUser(roleId, orgLocationsId,userId);
	}

	@Override
	public List<Employees> getAllUserOrgCodeAndLocationCodeTimeSheetNotification(Long userID) throws Exception {
		
		return userDao.getAllUserOrgCodeAndLocationCodeTimeSheetNotification(userID);
	}

	@Override
	public List<User> getOwnersByOrgLocationId(Long orgLocationsId) throws Exception {
		return userDao.getOwnersByOrgLocationId(orgLocationsId);
	}

	@Override
	public List<User> getManagersByUserRolesAndOrgLocationId(Long roleId , Long orgLocationsId,Long tabId) throws Exception {
		return userDao.getManagersByUserRolesAndOrgLocationId(roleId, orgLocationsId,tabId);
	}
	
	@Override
	public List<User> getManagersByUserIdAndUserRolesAndOrgLocationId(Long userId,Long roleId , Long orgLocationsId) throws Exception {
		return userDao.getManagersByUserIdAndUserRolesAndOrgLocationId(userId, roleId, orgLocationsId);
	}
	
	@Override
	public List<User> getManagersByUserIdAndUserRolesAndOrgLocationIdAndUserTabId(Long userId, Long roleId, Long orgLocationsId, Long tabId) throws Exception {
		return userDao.getManagersByUserIdAndUserRolesAndOrgLocationIdAndUserTabId(userId, roleId, orgLocationsId,tabId);
	}
	

	@Override
	public User getUserDetailsByUserCode(String userCode) {
		return userDao.getUserDetailsByUserCode(userCode);
	}


	@Override
	public List<User> getOwnersByOrgLocationIdAndNotExistedOwnerId(Long orgLocationsId, Long ownerId, List<Long> ownerListOfSpoc) {
		return userDao.getOwnersByOrgLocationIdAndNotExistedOwnerId(orgLocationsId,ownerId,ownerListOfSpoc);
	}
	
	
	
	/**
	 * @author Sammeta David
	 * to fetch get All Users By OrgLocation Code
	 * @param orgLocationsId
	 * @return List<User> 
	 */
	@Override
	public List<User> getAllUsersByOrgLocationCode(Long orgLocationsId) throws Exception {
		return userDao.getAllUsersByOrgLocationCode(orgLocationsId);
	}

	/**
	 * @author Sammeta David
	 * to fetch get All Users By OrgLocation Code Except TeamOwner
	 * @param orgLocationsId,teamOwnerUserId
	 * @return List<User> 
	 */
	
	@Override
	public List<User> getAllUsersByOrgLocationCodeExceptTeamOwner(Long teamOwnerUserId, Long orgLocationsId, Long tabId)
			throws Exception {
		return userDao.getAllUsersByOrgLocationCodeExceptTeamOwner(teamOwnerUserId,orgLocationsId, tabId);
	}

	
	
	@Override
	public void sendEmailToClientOrVendor(String spocUUID,User user,User ownerUser, User loginUser, String password, String responderType,Org loginOrg,Org respondingPartyOrg,OrgLocations respondingPartyOrgLocation,OrgLocations orgLocation) throws Exception {
		 
		if(null == ownerUser){
			ownerUser = loginUser;
		}
		
		 boolean isUserActive = false;
		 isUserActive = userRoleService.isUserActive(user.getId());
		
		
		 String actionLink = null; 
		 String rejectActionLink = null; 
		 EmailMessage emailMessage = new EmailMessage();
		 emailMessage.setFirstName(user.getFirstName());
		 emailMessage.setLastName(user.getLastName());
		 emailMessage.setToEmail(user.getEmail());
		 emailMessage.setFromEmail(ownerUser.getEmail());
		 emailMessage.setCreatedBy(loginUser.getUserCode());
		 StringBuilder emailMsgBuilder = new StringBuilder();
		 emailMessage.setFromName(emailMsgBuilder.append(ownerUser.getFirstName()).append(" ").append(ownerUser.getLastName()).toString());
		 OrgLocations loginOrgLocation = orglocationsDao.getOrgLocations(orgLocation.getId());
		//setting the class filed values
		 Map<String, String> globalVarsMap = new HashMap<String, String>();
		 String UUID = SourceLeadRandomCodeHelper.generateUUID();
		 if("Client".equals(responderType)){
			 globalVarsMap.put("FirstName", emailMessage.getFirstName());
			 globalVarsMap.put("LastName",emailMessage.getLastName());
			 globalVarsMap.put("vendorFirstName", ownerUser.getFirstName());
			 globalVarsMap.put("vendorLastName",ownerUser.getLastName());
			 globalVarsMap.put("clientFirstName", user.getFirstName());
			 globalVarsMap.put("clientlastName",user.getLastName());
			 globalVarsMap.put("clientOrgName",respondingPartyOrgLocation.getOrgLocationName());
			 globalVarsMap.put("vendorOrgName",orgLocation.getOrgLocationName());
			 StringBuilder clientLoginUser = new StringBuilder();
			 if(null != loginUser.getLastName()){
				 globalVarsMap.put("loginUsername",clientLoginUser.append(ownerUser.getFirstName()).append(" ").append(ownerUser.getLastName()).toString());
			 }else{
				 globalVarsMap.put("loginUsername",ownerUser.getLastName());
			 }
			 StringBuilder urlBuilder = new StringBuilder();
			 globalVarsMap.put("acceptURL",urlBuilder.append("<a href='").append(sourceleadLoginUrl).append("' style='text-decoration:none; '><div style=' width:220px; border-radius:30px; background-color:#214b90; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Login</div></a>").toString());
			// globalVarsMap.put("acceptURL",urlBuilder.append("<a href='").append(sourceleadUrl).append("/").append("ws/acceptClientOrVendorInvitation?respondingOrgType=VMS&respondingPartyOrgLocationId=").append(respondingPartyOrgLocation.getId()).append("&initiatingPartyOrgLocationId=").append(loginOrgLocation.getId()).append("&UUID=").append(UUID).append("&spocUUID=").append(spocUUID).append("' style='text-decoration:none; '><div style=' width:220px; border-radius:30px; background-color:#214b90; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Accept</div></a>").toString());
			 StringBuilder rejecturlBuilder = new StringBuilder();
			 globalVarsMap.put("rejectURL",rejecturlBuilder.append("<a href='").append(sourceleadUrl).append("/").append("ws/acceptClientOrVendorInvitation?actionLink=rejectNotification&respondingOrgType=VMS&respondingPartyOrgLocationId=").append(respondingPartyOrgLocation.getId()).append("&initiatingPartyOrgLocationId=").append(loginOrgLocation.getId()).append("&UUID=").append(UUID).append("&spocUUID=").append(spocUUID).append("' style='text-decoration:none; '><div style=' width:220px; border-radius:30px; background-color:#214b90; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Reject</div></a>").toString());
			 StringBuilder linkBuilder = new StringBuilder();
			 globalVarsMap.put("supportLink",linkBuilder.append("<a href='http://www.sourcelead.com/contact.html' target='_blank' style='outline:none; border:none;'>http://www.sourcelead.com/contact.html</a>").toString());
			
			 StringBuilder fbLinkBuilder = new StringBuilder();
			 globalVarsMap.put("facebookLink",fbLinkBuilder.append("<a href='http://www.facebook.com' target='_blank' alt='Facebook' title='Facebook' style='margin:5px 5px 0px 0px; display: inline-block;'><img src='https://www.mail-signatures.com/articles/wp-content/themes/emailsignatures/images/facebook-35x35.gif' width='30px' height='30px'></a>").toString());
			
			 StringBuilder linkedInLinkBuilder = new StringBuilder();
			 globalVarsMap.put("linkedInLink",linkedInLinkBuilder.append("<a href='http://in.linkedin.com' target='_blank' alt='LinkedIn' title='LinkedIn' style='margin:5px 5px 0px 0px; display: inline-block;'><img src='https://www.mail-signatures.com/articles/wp-content/themes/emailsignatures/images/linkedin-35x35.gif' width='30px' height='30px'></a>").toString());
			
			 StringBuilder twitterLinkBuilder = new StringBuilder();
			 globalVarsMap.put("twitterLink",twitterLinkBuilder.append("<a href='http://twitter.com/login' target='_blank' alt='Twitter' title='Twitter' style='margin:5px 5px 0px 0px; display: inline-block;'><img src='https://www.mail-signatures.com/articles/wp-content/themes/emailsignatures/images/twitter-35x35.gif' width='30px' height='30px'></a>").toString());
			
			 StringBuilder googleLinkBuilder = new StringBuilder();
			 globalVarsMap.put("googleLink",googleLinkBuilder.append("<a href='http://www.google.com' target='_blank' alt='Google+' title='Google+' style='margin:5px 5px 0px 0px; display: inline-block;'><img src='https://www.mail-signatures.com/articles/wp-content/themes/emailsignatures/images/gplus-35x35.gif' width='30px' height='30px'></a>").toString());
			 
			 if(null != password){
			    globalVarsMap.put("userName",user.getEmail());
			    globalVarsMap.put("Password",password);
			 }else if(null == password && isUserActive == false){
				 password = SourceLeadRandomCodeHelper.generateRandomPassword();
				 user.setPassword(sourceleadPasswordEncoder.encode(password));
				 userDao.addUser(user);
				 
				    UserRole userRole = userRoleService.getUserDetailsByUserIdAndLocationId(user.getId(), respondingPartyOrgLocation.getId());
					if(userRole != null) {
						userRole.setIsMailSent(Status.Y);
						userRoleService.addUserRole(userRole);
					}
					
				 globalVarsMap.put("userName",user.getEmail());
				 globalVarsMap.put("Password",password);
			 }else{
				 globalVarsMap.put("userName",user.getEmail());
				 globalVarsMap.put("Password","Login with your existing sourcelead credentials");
			 } 
		     StringBuilder actionUrlBuilder = new StringBuilder();
			 actionLink = actionUrlBuilder.append("").append(sourceleadUrl).append("/").append("ws/acceptClientOrVendorInvitation?actionLink=acceptNotification&respondingOrgType=VMS&respondingPartyOrgLocationId=").append(respondingPartyOrgLocation.getId()).append("&initiatingPartyOrgLocationId=").append(loginOrgLocation.getId()).append("&UUID=").append(UUID).append("&spocUUID=").append(spocUUID).append(" ").toString();
			 
			 StringBuilder rejectActionBuilder = new StringBuilder();
			 rejectActionLink = rejectActionBuilder.append("").append(sourceleadUrl).append("/").append("ws/acceptClientOrVendorInvitation?actionLink=rejectNotification&respondingOrgType=VMS&respondingPartyOrgLocationId=").append(respondingPartyOrgLocation.getId()).append("&initiatingPartyOrgLocationId=").append(loginOrgLocation.getId()).append("&UUID=").append(UUID).append("&spocUUID=").append(spocUUID).append(" ").toString();
			 
			 
		 }else if("Vendor".equals(responderType)){
			 globalVarsMap.put("FirstName", emailMessage.getFirstName());
			 globalVarsMap.put("LastName",emailMessage.getLastName());
			 globalVarsMap.put("vendorFirstName", user.getFirstName());
			 globalVarsMap.put("vendorLastName",user.getLastName());
			 globalVarsMap.put("clientFirstName", ownerUser.getFirstName());
			 globalVarsMap.put("clientLastName",ownerUser.getLastName());
			 globalVarsMap.put("vendorOrgName",respondingPartyOrgLocation.getOrgLocationName());
			 globalVarsMap.put("clientOrgName",orgLocation.getOrgLocationName());
			 
			 StringBuilder vendorLoginUser = new StringBuilder();
			 if(null != loginUser.getLastName()){
				 globalVarsMap.put("loginUsername",vendorLoginUser.append(ownerUser.getFirstName()).append(" ").append(ownerUser.getLastName()).toString());
			 }else{
				 globalVarsMap.put("loginUsername",ownerUser.getLastName());
			 }
			 StringBuilder actionUrlBuilder = new StringBuilder();
			 globalVarsMap.put("acceptURL",actionUrlBuilder.append("<a href='").append(sourceleadUrl).append("/").append("ws/acceptClientOrVendorInvitation?respondingOrgType=ATS&respondingPartyOrgLocationId=").append(respondingPartyOrgLocation.getId()).append("&initiatingPartyOrgLocationId=").append(loginOrgLocation.getId()).append("&UUID=").append(UUID).append("&spocUUID=").append(spocUUID).append("' style='text-decoration:none; '><div style=' width:220px; border-radius:30px; background-color:#214b90; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Accept</div></a>").toString());
			 StringBuilder rejectActionUrlBuilder = new StringBuilder();
			 globalVarsMap.put("rejectURL",rejectActionUrlBuilder.append("<a href='").append(sourceleadUrl).append("/").append("ws/acceptClientOrVendorInvitation?actionLink=rejectNotification&respondingOrgType=ATS&respondingPartyOrgLocationId=").append(respondingPartyOrgLocation.getId()).append("&initiatingPartyOrgLocationId=").append(loginOrgLocation.getId()).append("&UUID=").append(UUID).append("&spocUUID=").append(spocUUID).append("' style='text-decoration:none; '><div style=' width:220px; border-radius:30px; background-color:#214b90; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Reject</div></a>").toString());
			 StringBuilder vendorLinkBuilder = new StringBuilder();
			 globalVarsMap.put("supportLink",vendorLinkBuilder.append("<a href='http://www.sourcelead.com/contact.html' target='_blank' style='outline:none; border:none;'>http://www.sourcelead.com/contact.html</a>").toString());
			 
			 StringBuilder fbLinkBuilder = new StringBuilder();
			 globalVarsMap.put("facebookLink",fbLinkBuilder.append("<a href='http://www.facebook.com' target='_blank' alt='Facebook' style='margin:5px 5px 0px 0px; display: inline-block;'><img src='https://www.mail-signatures.com/articles/wp-content/themes/emailsignatures/images/facebook-35x35.gif' width='30px' height='30px'></a>").toString());
			
			 StringBuilder linkedInLinkBuilder = new StringBuilder();
			 globalVarsMap.put("linkedInLink",linkedInLinkBuilder.append("<a href='http://in.linkedin.com' target='_blank' alt='LinkedIn' style='margin:5px 5px 0px 0px; display: inline-block;'><img src='https://www.mail-signatures.com/articles/wp-content/themes/emailsignatures/images/linkedin-35x35.gif' width='30px' height='30px'></a>").toString());
			
			 StringBuilder twitterLinkBuilder = new StringBuilder();
			 globalVarsMap.put("twitterLink",twitterLinkBuilder.append("<a href='http://twitter.com/login' target='_blank' alt='Twitter' style='margin:5px 5px 0px 0px; display: inline-block;'><img src='https://www.mail-signatures.com/articles/wp-content/themes/emailsignatures/images/twitter-35x35.gif' width='30px' height='30px'></a>").toString());
			
			 StringBuilder googleLinkBuilder = new StringBuilder();
			 globalVarsMap.put("googleLink",googleLinkBuilder.append("<a href='http://www.google.com' target='_blank' alt='Google Plus' style='margin:5px 5px 0px 0px; display: inline-block;'><img src='https://www.mail-signatures.com/articles/wp-content/themes/emailsignatures/images/gplus-35x35.gif' width='30px' height='30px'></a>").toString());
			 
			 
			 if(null != password){
			    globalVarsMap.put("userName",user.getEmail());
			    globalVarsMap.put("password",password);
			 }else if(null == password && isUserActive == false){
				 password = SourceLeadRandomCodeHelper.generateRandomPassword();
				 user.setPassword(sourceleadPasswordEncoder.encode(password));
				 userDao.addUser(user);
			     UserRole userRole = userRoleService.getUserDetailsByUserIdAndLocationId(user.getId(), respondingPartyOrgLocation.getId());
				  if(userRole != null) {
					userRole.setIsMailSent(Status.Y);
					userRoleService.addUserRole(userRole);
				  }
				 globalVarsMap.put("userName",user.getEmail());
				 globalVarsMap.put("password",password);
			 }else{
				 globalVarsMap.put("userName",user.getEmail());
				 globalVarsMap.put("password","Login with your existing sourcelead credentials");
			 }
			 
			 StringBuilder actionLinkBuilder = new StringBuilder();
			 actionLink = actionLinkBuilder.append("").append(sourceleadUrl).append("/").append("ws/acceptClientOrVendorInvitation?actionLink=acceptNotification&respondingOrgType=ATS&respondingPartyOrgLocationId=").append(respondingPartyOrgLocation.getId()).append("&initiatingPartyOrgLocationId=").append(loginOrgLocation.getId()).append("&UUID=").append(UUID).append("&spocUUID=").append(spocUUID).append("").toString();
			 
			 StringBuilder rejectActionBuilder = new StringBuilder();
			 rejectActionLink = rejectActionBuilder.append("").append(sourceleadUrl).append("/").append("ws/acceptClientOrVendorInvitation?actionLink=rejectNotification&respondingOrgType=ATS&respondingPartyOrgLocationId=").append(respondingPartyOrgLocation.getId()).append("&initiatingPartyOrgLocationId=").append(loginOrgLocation.getId()).append("&UUID=").append(UUID).append("&spocUUID=").append(spocUUID).append("").toString();
		 }
		 
		 //setting the Recipient information
		 InternetAddress[] iAdressArray = InternetAddress.parse(emailMessage.getToEmail());
		
		 //getting the current EmailScenario by EmailScenario unique name and entity type
		 EmailScenario  emailScenario = null;
		 EmailScenario noificationEmailScenario = null;
		 if("Client".equals(responderType)){
			 if(orgLocation != null)
				 emailScenario =  emailScenarioService.getEmailScenarioByUniqueNameAndOrgLocationId(Constants.ACCEPT_CLIENT_INVITATION,Constants.ENTITY_TYPE_EMAIL, orgLocation.getId());
				  if(emailScenario == null){
						emailScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.ACCEPT_CLIENT_INVITATION,Constants.ENTITY_TYPE_EMAIL);
				   }
			 noificationEmailScenario =  emailScenarioService.getEmailScenarioByUniqueName(Constants.ACCEPT_CLIENT_INVITATION,Constants.ENTITY_TYPE_NOTIFICATION);
		 }else if("Vendor".equals(responderType)){
			 emailScenario =  emailScenarioService.getEmailScenarioByUniqueName(Constants.ACCEPT_VENDOR_INVITATION,Constants.ENTITY_TYPE_EMAIL);
			 if(orgLocation != null)
				  if(emailScenario == null){
						emailScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.ACCEPT_VENDOR_INVITATION,Constants.ENTITY_TYPE_EMAIL);
				   }
			 noificationEmailScenario =  emailScenarioService.getEmailScenarioByUniqueName(Constants.ACCEPT_VENDOR_INVITATION,Constants.ENTITY_TYPE_NOTIFICATION);
		 }
		
		 //covert subject,body and signature
		 String subject = emailScenerioUtil.bulidTemplateSubject(emailScenario.getSubject(),globalVarsMap,emailScenario);
		 String body = emailScenerioUtil.buildTemplateBody(emailScenario.getBody(),globalVarsMap,emailScenario);
		 String signature = emailScenerioUtil.buildTemplateSignature(emailScenario.getSignature(),globalVarsMap,emailScenario);
		 String url = sourceleadUrl;
		 
		 
		 //construct HTML template using required data
		 String emailTemplate = emailScenerioUtil.AssembleEmailMessage(emailScenario,emailMessage, subject, body, signature, url, loginOrg,respondingPartyOrgLocation);
		 emailMessage.setSubject(subject);
		 emailMessage.setMessageBody(emailTemplate);
		 emailMessage.setOrgLocationId(respondingPartyOrgLocation.getId());
		 
		 //calling the sendEmail method of emailScenerioUtil
		 emailMessage.setEmailType(Constants.CUSTOM_USER_EMAIL);
		 emailScenerioUtil.sendEmail(emailMessage,respondingPartyOrgLocation.getId(),iAdressArray,new ArrayList<EmailAttachment>());
		 String notificationbody = emailScenerioUtil.buildTemplateBody(noificationEmailScenario.getBody(),globalVarsMap,noificationEmailScenario);
		 noificationEmailScenario.setBody(notificationbody);
		 noificationEmailScenario.setUUID(UUID);
		 
		 emailScenerioUtil.sendNotificationToClientOrVendor(actionLink,rejectActionLink, loginUser,ownerUser, user, respondingPartyOrgLocation, noificationEmailScenario,spocUUID);
	}
	
	@Override
	public List<User> getUserListExceptExistedUsersList(List<Long> userIds, Long orgLocationId,Long tabId)
			throws Exception {
		return userDao.getUserListExceptExistedUsersList(userIds, orgLocationId,tabId);
	}

	@Override
	public List<User> getUsersByTeamType(Long id, String teamType) throws Exception {
		return userDao.getUsersByTeamType(id,teamType);
	}
	
	@Override
	public List<User> getUsersByTeamTypeAndTabId(Long id, String teamType, Long tabId) throws Exception {
		return userDao.getUsersByTeamTypeAndTabId(id,teamType,tabId);
	}


	@Override
	public List<User> getUsersByTeamTypeAndNoCurrentUser(Long currentApproverId, Long orgLocationsId) throws Exception {
		return userDao.getUsersByTeamTypeAndNoCurrentUser(currentApproverId,orgLocationsId);
	}
	
	@Override
	public List<User> getUserListExceptExistedTeamMembersList(List<Long> teamMembersId, Long orgLocationId)
			throws Exception {
		return userDao.getUserListExceptExistedTeamMembersList(teamMembersId, orgLocationId);
	}

	@Override
	public List<User> getReoprtingManagersByOrgLocationId(Long orgLocationsId) throws Exception {
		return userDao.getReoprtingManagersByOrgLocationId(orgLocationsId);
	}
	
	@Override
	public List<User> getAllUsersByOrgLocationCodeExcludingEmployees(Long orgLocationsId) throws Exception {
	
		return userDao.getAllUsersByOrgLocationCodeExcludingEmployees(orgLocationsId);

	}

	@Override
	public List<User> getTabAccessOwnersByOrgLocationIdAndTabId(Long orgLocationsId, Long tabId) throws Exception {
		
		List<User> userList = null;
		userList = userDao.getTabAccessOwnersByOrgLocationIdAndTabId(orgLocationsId, tabId);
		return userList;
	}
	
	
	@Override
	public User getTabAccessOwnersByOrgLocationIdEmailAndTabId(Long orgLocationsId, String email, Long tabId) throws Exception {
		
		User user = userDao.getTabAccessOwnersByOrgLocationIdEmailAndTabId(orgLocationsId, email, tabId);
		if (null != user) {
			UserRole userRole = userRoleService.getUserRoleByUserIdAndLocationId(user.getId(), orgLocationsId);
			if (null != userRole) {
				user.setUserType(userRole.getUserType());
			}
		}
		return user;
	}
	
	
	@Override
	public List<User> getTabAccessOwnersByOrgLocationIdAndTabIdAndNoCurrentUser(Long currentApproverId,
			Long orgLocationsId, Long tabId) throws Exception {
		return userDao.getTabAccessOwnersByOrgLocationIdAndTabIdAndNoCurrentUser(currentApproverId,orgLocationsId, tabId);
	}

	@Override
	public List<User> getUsersForAddAsConsultant(Org org,Long orgLocationsId) throws Exception {
		return userDao.getUsersForAddAsConsultant(org,orgLocationsId);
	}

	@Override
	public List<User> getUsersForConsultantsByUserRoleIdAndOrgLocationId(Long roleId, Long orgLocationsId) throws Exception {
		return userDao.getUsersForConsultantsByUserRoleIdAndOrgLocationId(roleId, orgLocationsId);
	}

	@Override
	public List<User> getTabAccessUsersByOrgLocationIdAndTabIdAndUsersIds(Long orgLocationsId, List<Long> userIds,
			Long tabId) throws Exception {
		return userDao.getTabAccessUsersByOrgLocationIdAndTabIdAndUsersIds(orgLocationsId, userIds, tabId);
	}

	@Override
	public List<User> getTabAccessUsersByOrgLocationIdAndTabIdAndNotInUsersId(Long orgLocationsId, Long userId, Long tabId)
			throws Exception {
		return userDao.getTabAccessUsersByOrgLocationIdAndTabIdAndNotInUsersId(orgLocationsId, userId, tabId);
	}

	@Override
	public List<User> getUsersListByOrglocationId(Long orgLocationId) throws Exception {
		return userDao.getUsersListByOrglocationId(orgLocationId);
	}

	@Override
	public void saveOrUpdateUserElkDocument(UserRole userRole, Employees employee, String loginUserCode) throws Exception {
		//Elastic Search Document Saving
		UserIndex userIndex = userIndexHelper.getUserIndexFromUser(userRole,employee,loginUserCode);
		sourceleadELKBaseIndexer.saveOrUpdateElkDocument(userIndex, Constants.ELK_USER_INDEX, Constants.ELK_USER_TYPE,userRole.getId().toString());
		
	}


	@Override
	public List<UserIndex> getAllUniversalSearchUsersList(OrgLocations orgLocation, String searchValue, User user)
			throws Exception {
		 BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
		 boolQueryBuilder.must(QueryBuilders.matchQuery("orgLocationId", orgLocation.getId()));
		 boolQueryBuilder.mustNot(QueryBuilders.matchQuery("userId", user.getId()));
		 Long maxResults = userRoleService.getUsersCountByOrgLocationID(orgLocation.getId());
		 
			//All Keywords
//			ArrayList<String> allKeyWordArray = getSearchKeywordsFromChosenSelectField(jsonObj, "allKeywords");
			ArrayList<String> anyKeyWordArray = universalSearchHelper.getSearchValuesFromChosenSelectField(searchValue);
			if (null != anyKeyWordArray && !anyKeyWordArray.isEmpty()) {
				BoolQueryBuilder anyBoolQueryBuilder = QueryBuilders.boolQuery();
				for (String keywd : anyKeyWordArray) {
					BoolQueryBuilder anyBoolSubQueryBuilder = QueryBuilders.boolQuery();
					ArrayList<QueryBuilder> QueryBuilderList = universalSearchHelper.getUsersQueryBuilderList(keywd);

					if (null != QueryBuilderList && !QueryBuilderList.isEmpty()) {
						for (QueryBuilder QueryBuilder : QueryBuilderList) {
							anyBoolSubQueryBuilder.should(QueryBuilder);
						}
						anyBoolQueryBuilder.should(anyBoolSubQueryBuilder);
					}
				}
				boolQueryBuilder.must(anyBoolQueryBuilder);
			}
		
			List<UserIndex> usersList = new ArrayList<UserIndex>();
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			df.setTimeZone(TimeZone.getTimeZone("GMT"));
			SearchHit[] searchHits = sourceleadELKBaseIndexer.searchDocumentWithMaxResults(Constants.ELK_USER_INDEX, Constants.ELK_USER_TYPE,0,maxResults.intValue(),boolQueryBuilder).getHits();
			if(null != searchHits && searchHits.length >0){
				ObjectMapper mapper = new ObjectMapper();
				for (SearchHit hit : searchHits) {
					String json = hit.getSourceAsString();
					mapper.setDateFormat(df);
					UserIndex userIndex = mapper.readValue(json, UserIndex.class);
					usersList.add(userIndex);
				}
			}
		
		return usersList;
	}

	@Override
	public User getUserByEmailAndUserType(String email, String userType) throws Exception {
		return userDao.getUserByEmailAndUserType(email, userType);
	}
	
	@Override
	public User getUserByEmail(String email) throws Exception {
		return userDao.getUserByEmail(email);
	}

	@Override
	public User getUserByUserName(String userName) throws Exception {
		return userDao.getUserByUserName(userName);
	}

	@Override
	public List<User> getTabAccessOwnersByOrgLocationIdAndTabIdAndNotOwners(Long orgLocationsId, Long tabId,
			List<Long> ownersList) throws Exception {
		List<User> userList = userDao.getTabAccessOwnersByOrgLocationIdAndTabIdAndNotOwners(orgLocationsId, tabId,ownersList);
		if(null != userList && !userList.isEmpty()){
			for (User user : userList) {
				UserRole userRole = userRoleService.getUserRoleByUserIdAndLocationId(user.getId(),orgLocationsId);
				if(null != userRole){
					user.setUserType(userRole.getUserType());
				}
			}
		}
		return userList;
	}

	@Override
	public void sendEmailFromSourcelead(User user, User loginUser, String password, Long locationId, Long orgId)
			throws Exception {
		
		 UserRole userRole = userRoleService.getUserRoleByUserIdAndLocationId(user.getId(), locationId);
		 if(userRole == null){
		    userRole = userRoleService.getLoginUserPrimaryRole(user.getId());
		 }
		 
		 boolean isUserActive = false;
		 isUserActive = userRoleService.isUserActive(user.getId());
		OrgLocations locations= orgLocationService.getOrgLocations(locationId);
		 
		 StringBuilder sb = new StringBuilder();
		 EmailMessage emailMessage = new EmailMessage();
		 StringBuilder builder=new StringBuilder();
		 emailMessage.setFirstName(builder.append(user.getFirstName()).append(" ").append(user.getLastName()).toString());
		 emailMessage.setLastName(user.getLastName());
		 emailMessage.setToEmail(user.getEmail());
		 emailMessage.setFromEmail(loginUser.getEmail());
		 emailMessage.setFromName(sb.append(loginUser.getFirstName()).append(" ").append(loginUser.getLastName()).toString());
		 //emailMessage.setEmailType(Constants.CUSTOM_USER_EMAIL);

		 emailMessage.setCreatedBy(loginUser.getUserCode());
		 emailMessage.setOrgLocationId(locationId);
		 
		//setting the class filed values
		 Map<String, String> globalVarsMap = new HashMap<String, String>();
		 globalVarsMap.put("firstName", emailMessage.getFirstName());
		 //globalVarsMap.put("lastName",emailMessage.getLastName());
		 globalVarsMap.put("administratorFirstName", loginUser.getFirstName());
		 globalVarsMap.put("administratorLastName",loginUser.getLastName());
		 globalVarsMap.put("orgName", locations.getOrgLocationName());
		 globalVarsMap.put("role",userRole.getUserType());
		 StringBuilder loginBuilder = new StringBuilder();
		 globalVarsMap.put("loginURL",loginBuilder.append("<a href='").append(sourceleadLoginUrl).append("' style='text-decoration:none; '><div style=' width:220px; border-radius:30px; background-color:#214b90; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Login</div></a>").toString());
		 globalVarsMap.put("userEmailId",user.getEmail());
		 if(isUserActive == false){
			 globalVarsMap.put("password",password);
		 }else{
			 globalVarsMap.put("password","Login with your existing sourcelead credentials");
		 }
		 
		
		 //setting the Recipient information
		 InternetAddress[] iAdressArray = InternetAddress.parse(emailMessage.getToEmail());
		 
		 //getting the current EmailScenario by EmailScenario unique name and entity type
		 EmailScenario  emailScenario = null;
		 if(locations != null)
			 emailScenario =  emailScenarioService.getEmailScenarioByUniqueNameAndOrgLocationId(Constants.INVITE_USER,Constants.ENTITY_TYPE_EMAIL, locations.getId());
			  if(emailScenario == null){
					emailScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.INVITE_USER,Constants.ENTITY_TYPE_EMAIL);
			   }
		 //covert subject,body and signature
		 String subject = emailScenerioUtil.bulidSubject(emailScenario.getSubject(),globalVarsMap);
		 String body = emailScenerioUtil.buildTempBody(emailScenario.getBody(),globalVarsMap);
		 String signature = emailScenerioUtil.buildSignature(emailScenario.getSignature(),globalVarsMap);
		 String url = sourceleadUrl;
		 
		 Org registredOrg = orgDao.findOrgByID(orgId);
		 OrgLocations regLocation = orglocationsDao.getOrgLocations(locationId);
		 
		 //construct HTML template using required data
		 String emailTemplate = emailScenerioUtil.AssembleEmailMessage(emailScenario,emailMessage, subject, body, signature, url,registredOrg,regLocation);
		 emailMessage.setSubject(subject);
		 emailMessage.setMessageBody(emailTemplate);
		 
		 //calling the sendEmail method of emailScenerioUtil
		 emailScenerioUtil.sendEmail(emailMessage,locationId,iAdressArray,new ArrayList<EmailAttachment>());
		
	}

	@Override
	public void passwordResetMailToLoginUser(User user,OrgLocations orgLocations) throws Exception {
		
		 EmailMessage emailMessage = new EmailMessage();
		 emailMessage.setFirstName(user.getFirstName());
		 emailMessage.setLastName(user.getLastName());
		 emailMessage.setToEmail(user.getEmail());
		 
		 EmailConfigurationFromMail confFromMail = emailScenarioConfigurationService.getConfFromMailByStatus(orgLocations.getId());
		 if(confFromMail != null) {
			 emailMessage.setFromEmail(confFromMail.getEmail());
		 }else {
			 emailMessage.setFromEmail(defaultFromEmailAddressForProd);
		 }
		 
		 emailMessage.setCreatedBy(user.getUserCode());
		 Map<String, String> globalVarsMap = new HashMap<String, String>();
		 StringBuffer userNameStringBuffer =  new StringBuffer();
		 if (user.getLastName() != null && !"".equalsIgnoreCase(user.getLastName()) ) {
			 globalVarsMap.put("userName",userNameStringBuffer.append(user.getFirstName()).append(" ").append(user.getLastName()).toString());
			 } else {
				 globalVarsMap.put("userName", user.getFirstName());
			}
		
		
		 //getting the current EmailScenario by EmailScenario unique name and entity type
		 //using same migration which has been already used in updatedRequirementMailToManager method
		 EmailScenario  notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.PASSWORD_RESET_MAIL_TO_USER,Constants.ENTITY_TYPE_NOTIFICATION);

		 
		 
		 String notificationbody = emailScenerioUtil.buildTemplateBody(notificationScenario.getBody(),globalVarsMap,notificationScenario);
		 notificationScenario.setBody(notificationbody);

		 //calling the sendEmail method of emailScenerioUtil
		 commonUtil.sendTemplateEmail(emailMessage, globalVarsMap,orgLocations.getOrg(),orgLocations,Constants.PASSWORD_RESET_MAIL_TO_USER,new ArrayList<EmailAttachment>());

		 String actionLink="";
		 emailScenerioUtil.sendNotification(actionLink, user, user, orgLocations, notificationScenario,new HashMap<String,String>());
		}
	
	
	@Override
	public List<User> getAllUsersWithRequirementTabAccessAndMemeberInRequirementMemberOrgLocationIdAndTabId(
			Long orgLocationsId, Long tabId) throws Exception {
		return userDao.getAllUsersWithRequirementTabAccessAndMemeberInRequirementMemberOrgLocationIdAndTabId(orgLocationsId,tabId);
	}

	@Override
	public LinkedHashSet<User> getUsersListAsReoprtingManagersByOrgLocationIdHavingTabAccess(Long orgLocationsId, User user, Long tabId) throws Exception
		{
		return userDao.getUsersListAsReoprtingManagersByOrgLocationIdHavingTabAccess(orgLocationsId,user,tabId);
	}

	@Override
	public List<User> getUsersByOrgLocationIdExceptLoggedInUser(Long orgLocationsId, Long userId) throws Exception {
		return userDao.getUsersByOrgLocationIdExceptLoggedInUser(orgLocationsId, userId);
	}

	@Override
	public List<User> getUsersHierarchyListByOrgLocationIdAndRoleIdAndNoCurrentUser(Long userId, Long roleId,
			Long orgLocationsId) throws Exception {
		return userDao.getUsersHierarchyListByOrgLocationIdAndRoleIdAndNoCurrentUser(userId, roleId, orgLocationsId);
	}

	@Override
	public List<User> getUserListByRoleActionIdAndOrgLocationId(Long roleActionId, Long orgLocationId)
			throws Exception {
		return userDao.getUserListByRoleActionIdAndOrgLocationId(roleActionId, orgLocationId);
	}

	@Override
	public User getUserByVerficationUUID(String token)
	{
		return userDao.getUserByVerficationUUID(token);
	}

	@Override
	public User getUserByUserCodeAndOrgLocationsId(String userCode, Long orgLocationsId) throws Exception {
		return userDao.getUserByUserCodeAndOrgLocationsId(userCode, orgLocationsId);
	}

	@Override
	public ResponseList<UserDTO> getUsersDTOListByOrglocationId(Long orgLocationId,UserDTO userDTO) throws Exception {
		return userDao.getUsersDTOListByOrglocationId(orgLocationId,userDTO);
	}

	@Override
	public ResponseList<UserRole> getUsersResponseListByOrglocationId(UserDTO userDTO,OrgLocations orgLocation,Date[] searchDates) throws Exception {
		return userDao.getUsersResponseListByOrglocationId(userDTO,orgLocation,searchDates);
	}

	@Override
	public List<UserDTO> getLinkedUserListByUserId(Long id) throws Exception {
		return userDao.getLinkedUserListByUserId(id);
	}

	@Override
	public List<Long> getUserIdsListByLinkedUserId(Long userId) throws Exception {
		return userDao.getUserIdsListByLinkedUserId(userId);
	}

	@Override
	public List<User> getUsersListForSignatoryByOrglocationId(Long locationId) {
		return userDao.getUsersListForSignatoryByOrglocationId(locationId);
	}
	

	@Override
	public User getUserbyConsultantUserId(Long consultantUserId) {
		return userDao.getUserbyConsultantUserId(consultantUserId);
	}
	
	@Override
	public List<User> getGlobalUsersByOrgId(Long roleId, Long orgId, Long tabId) throws Exception {
		return userDao.getGlobalUsersByOrgId(roleId, orgId, tabId);
	}

	@Override
	public List<User> getTabAccessGlobalOwnersByOrgIdAndTabId(Long orgLocationId,Long orgId, Long tabId) throws Exception {
		
		List<User> userList = null;
		userList = userDao.getTabAccessGlobalOwnersByOrgIdAndTabId(orgId, tabId);
		return userList;
	}

	@Override
	public List<User> getTabAccessGlobalOwnersByOrgLocationIdAndTabIdAndNoCurrentUser(Long currentApproverId,
			Long orgId, Long tabId) throws Exception {
		return userDao.getTabAccessGlobalOwnersByOrgLocationIdAndTabIdAndNoCurrentUser(currentApproverId, orgId, tabId);
	}

	@Override
	public List<User> getResourceManagersByOrgLocationIdAndTabId(Long orgLocationsId, Long tabId) throws Exception {
		return userDao.getResourceManagersByOrgLocationIdAndTabId(orgLocationsId, tabId);

	}

	@Override
	public List<User> getReportingManagersByUserRolesAndOrgLocationId(Long roleId, Long orgLocationsId)
			throws Exception {
		return userDao.getReportingManagersByUserRolesAndOrgLocationId(roleId, orgLocationsId);
	}

	@Override
	public List<User> getGlobalReportingManagersUsersByOrgId(Long roleId, Long orgId) throws Exception {
		return userDao.getGlobalReportingManagersUsersByOrgId(roleId, orgId);
	}

	@Override
	public List<User> getReportingManagersByUserIdAndUserRolesAndOrgLocationIdAndUserTabId(Long userId, Long roleId,
			Long orgLocationsId) throws Exception {
		return userDao.getReportingManagersByUserIdAndUserRolesAndOrgLocationIdAndUserTabId(userId, roleId, orgLocationsId);
	}

	@Override
	public ResponseList<UserDTO> getRecruitersReports(Long userId, Long orgLocationId, Long orgId, String isGlobalUser,UserDTO userDTO,String loginUserRole,Date[] searchDates,List<Long> teamsList,String type,List<Long> rolesList)
			throws Exception {
		return userDao.getRecruitersReports(userId, orgLocationId, orgId, isGlobalUser,userDTO,loginUserRole,searchDates,teamsList,type,rolesList);
	}


	@Override
	public DashboardItemsDTO getDashboardItemsByUserId(long loginUserId, long orgLocationId) {
		try {
			DashboardItems dashboardItems = userDao.getDashboardItemsByUserId(loginUserId, orgLocationId);
			if (dashboardItems != null) {
				DashboardItemsDTO dashboardItemsDTO = new DashboardItemsDTO();
				
				JSONArray jsonArray = new JSONArray(dashboardItems.getItems());
				if (jsonArray != null && jsonArray.length() > 0) {
					List<JSONObject> itemsObjectList = new LinkedList<>();
					for (int i = 0; i < jsonArray.length(); i++) {
						JSONObject jsonObject = jsonArray.getJSONObject(i);
						/*System.out.println("JSON OBJECT : " + jsonObject);*/
						itemsObjectList.add(jsonObject.getInt("order") - 1, jsonObject);
					}
					dashboardItemsDTO.setItemsObjectList(itemsObjectList);
				}
				dashboardItemsDTO.setId(dashboardItems.getId());
				return dashboardItemsDTO;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Response<String> saveDashboardItems(DashboardItemsDTO dashboardItemsDTO, User user, OrgLocations orgLocations) {
		Response<String> response = new Response<>();
		try {
			DashboardItems dashboardItems = null;
			if (dashboardItemsDTO.getId() > 0) {
				dashboardItems = userDao.getDashboardItemsByDashboardId(dashboardItemsDTO.getId());
			}
			else {
				dashboardItems = new DashboardItems();
			}
			/*String items = "";
			for (int i = 0; i < dashboardItemsDTO.getItemsArray().length(); i++) {
				String jsonString = dashboardItemsDTO.getItemsArray().getString(i);
				items += "[" + jsonString + "]";
			}*/
			dashboardItems.setItems(dashboardItemsDTO.getJsonData());
			dashboardItems.setUser(user);
			dashboardItems.setOrgLocations(orgLocations);
			
			long result = userDao.saveDashboardItems(dashboardItems);
			if (result > 0) {
				response.setResponse(Result.SUCCESS.name());
				response.setResponseMessage(Result.SUCCESS.getValue());
			} else {
				response.setResponse(Result.FAILED.name());
				response.setResponseMessage(Result.FAILED.getValue());
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.setResponse(Result.AWKWARD.name());
			response.setResponseMessage(Result.AWKWARD.getValue());
		}
		return response;
	}

	@Override
	public List<LeaveBalanceReportDTO> getUserNameAndTypeByOrgLocationId(String searchValue,Long OrgLocationId,List<Long> userRole,List<Long> orgDepartmentsList,String topleave,Date MonthStart,Date MonthEnd) throws Exception {
		 return userDao.getUserNameAndTypeByOrgLocationId(searchValue,OrgLocationId,userRole,orgDepartmentsList,topleave,MonthStart,MonthEnd);
	 }

	@Override
	public void sendEmailToUserForEmailUpdation(User user, User loginUser, OrgLocations orgLocations, String toEmail,
			String existingEmail) throws Exception {
		
		 EmailMessage emailMessage = new EmailMessage();
		 emailMessage.setFirstName(user.getFirstName());
		 emailMessage.setLastName(user.getLastName());
		 emailMessage.setToEmail(toEmail);
		 emailMessage.setFromEmail(loginUser.getEmail());
		 emailMessage.setFromName(loginUser.getFullName());
		 emailMessage.setEmailType(Constants.CUSTOM_USER_EMAIL);

		 emailMessage.setCreatedBy(loginUser.getUserCode());
		 emailMessage.setOrgLocationId(orgLocations.getId());
		 
		//setting the class filed values
		 Map<String, String> globalVarsMap = new HashMap<String, String>();
		 globalVarsMap.put("Login_User_Name",loginUser.getFullName());
		 globalVarsMap.put("Associate_Name", user.getFullName());
		 globalVarsMap.put("Existing_Email", existingEmail);
		 globalVarsMap.put("New_Email", user.getEmail());
		 StringBuilder loginBuilder = new StringBuilder();
		 globalVarsMap.put("URL_Link",loginBuilder.append("<a href='").append(sourceleadLoginUrl).append("' style='text-decoration:none; '><div style=' width:100px; border-radius:30px; background-color:#a21e23; color:#FFF; font-size:15px; font-weight:500; margin:10px auto 5px auto; padding:5px 0px 10px; text-align:center;'>Login</div></a>").toString());
		
		List<EmailAttachment> attachmentList=new ArrayList<EmailAttachment>();
		
		commonUtil.sendTemplateEmail(emailMessage, globalVarsMap,orgLocations.getOrg(),orgLocations,Constants.USER_EMAIL_UPDATION,attachmentList);
		
	}

	@Override
	public List<User> getUsersListUsingMultipleStringIds(String ids) throws Exception
	{
		return userDao.getUsersListUsingMultipleStringIds(ids);
	}
	
	@Override
	public List<String> getUserHierarchyUserCodeList(Long userId) throws Exception
	{
		return userDao.getUserHierarchyUserCodeList(userId);
	}
	
	@Override
	public List<Long> getUserHierarchyIdList(Long userId) throws Exception
	{
		return userDao.getUserHierarchyIdList(userId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public ResponseList<ApplicantsJoinedReportDTO> getApplicantsJoinedReport(ApplicantsJoinedReportDTO applicantsJoinedReportDTO,Long orglocationId, Date[] searchDates, List<Long> teamsList, String type, List<Long> clientIdsList) throws Exception
	{
		ResponseList<ApplicantsJoinedReportDTO> response = userDao.getApplicantsJoinedReport(applicantsJoinedReportDTO,orglocationId,searchDates,teamsList,type,clientIdsList);
		
		if(response.getData() != null && !response.getData().isEmpty()) {
			for(ApplicantsJoinedReportDTO applicantsJoinedReportDTO2 : response.getData()) {
				
				long reqClientCount = requirementSubmissionDao.getRequirementClientCountByRequirementIdAndUserIdAndDates(applicantsJoinedReportDTO2.getRequirementID(), applicantsJoinedReportDTO2.getUserId(), searchDates);
				long requirementCount = requirementSubmissionDao.getRequirementCountByUserIdAndDates(applicantsJoinedReportDTO2.getUserId(), searchDates);
				long offerCount = requirementSubmissionDao.getOfferCountByUserIdAndDates(applicantsJoinedReportDTO2.getCreatedBy(), searchDates);
				long joinedCount = requirementSubmissionDao.getJoinedCountByUserIdAndDates(applicantsJoinedReportDTO2.getCreatedBy(), searchDates);
				
				applicantsJoinedReportDTO2.setReqClientCount(new BigInteger(reqClientCount+""));
				applicantsJoinedReportDTO2.setRequirementCount(new BigInteger(requirementCount+""));
				applicantsJoinedReportDTO2.setOfferCount(new BigInteger(offerCount+""));
				applicantsJoinedReportDTO2.setJoinedCount(new BigInteger(joinedCount+""));
			}
		}
		
		
//		Map<Long, List<>> map = res.getData().stream().collect(Collectors.groupingBy(ApplicantsJoinedReportDTO::getUserId,Collectors.mapping(ApplicantsJoinedReportDTO::getUserId, Collectors.toList()));
		
		Map<BigInteger, List<ApplicantsJoinedReportDTO>> map = response.getData().stream().collect(Collectors.groupingBy(ApplicantsJoinedReportDTO::getUserId));
		ResponseList<ApplicantsJoinedReportDTO> responseList = new ResponseList<>();
		List<ApplicantsJoinedReportDTO> list = new LinkedList<>();
		for(BigInteger userId : map.keySet()){
			list.add(map.get(userId).get(0));
		}
		responseList.setRecordsFiltered(list.size()); 
		responseList.setRecordsTotal(list.size());
	/*	for(ApplicantsJoinedReportDTO applicantsJoinedReportDTO2:response.get){
			
			
			responseList.setRecordsTotal(applicantsJoinedReportDTO.getRecordsTotal());
			responseList.setRecordsFiltered(applicantsJoinedReportDTO2.getRecordsFiltered());
			

		}*/
		responseList.setData(list);
		   
		Long joiners = null;
		float finalRate = 0f;
		float k=0f;
		for(ApplicantsJoinedReportDTO dto : responseList.getData()){
			List<ApplicantsJoinedReportDTO> mainList = map.get(dto.getUserId());
			Float totalIncentiveAmount = 0f;
			Integer totalReqCount = 0;
			Float percentage=0f;
			List<ApplicantsJoinedReportDTO> searchReqList =null;
			for(ApplicantsJoinedReportDTO sub : mainList){
				// query to get total amount 
				List<ApplicantsJoinedReportDTO> applicantsJoinedReportDTOList=requirementSubmissionService.getRequirementIncentiveByRequirementIdAndLocation(sub.getRequirementID().longValue(),sub.getCreatedBy(),sub.getOrglocationId().longValue(),searchDates);
					searchReqList = applicantsJoinedReportDTOList;
					Long reqId = 0l;
				for(ApplicantsJoinedReportDTO applicantJoinedReportDTO:searchReqList){
					reqId = applicantJoinedReportDTO.getRequirementID().longValue();
					Integer count= requirementSubmissionService.getRequirementCountBySubmissionStatusJoinedandAssignedUsercode(applicantJoinedReportDTO.getRequirementID().longValue(), applicantJoinedReportDTO.getCreatedBy(),searchDates);			
					if(applicantJoinedReportDTO.getIncentJoiners() != null){
						joiners=Long.parseLong(applicantJoinedReportDTO.getIncentJoiners());
					}
					if(applicantJoinedReportDTO.getFinalRate() != null){
						float finalRates = Float.parseFloat(applicantJoinedReportDTO.getFinalRate());
						finalRate=finalRates;

					}
					else{
						float offeredAmount = Float.parseFloat(applicantJoinedReportDTO.getOfferAmount());
						finalRate=offeredAmount;

					}
					
					if("flat".equalsIgnoreCase(applicantJoinedReportDTO.getIncentAmountType())  && count >= joiners){
						float incentAmount = Float.parseFloat(applicantJoinedReportDTO.getIncentAmount());
						totalIncentiveAmount=totalIncentiveAmount+incentAmount;

					}
					if("percentage".equalsIgnoreCase(applicantJoinedReportDTO.getIncentAmountType()) && count >= joiners){
//						if(finalRate != 0){
							k = (finalRate*(joiners/100.0f));
//						}
						percentage=k;
						totalIncentiveAmount=totalIncentiveAmount+percentage;
					}
					
               }
				if(sub.getRequirementCount() != null){
					totalReqCount=sub.getRequirementCount().intValue();
				}
				if(sub.getReqClientCount() != null){
					totalReqCount=totalReqCount + sub.getReqClientCount().intValue();
				}
				sub.setIncentAmount(totalIncentiveAmount.toString());
				dto.setTotalRequirementCount(BigInteger.valueOf(totalReqCount));
//               java.math.BigDecimal k1 = java.math.BigDecimal.valueOf(totalIncentiveAmount);
			}
			
		}
	
		return responseList;
	}
	
	

	@Override
	public Sheet exportAllApplicantJoinedList(Sheet createExcelsheet, Workbook createExcelworkbook, ResponseList<ApplicantsJoinedReportDTO>  userDTOList,OrgLocations orgLocations) throws Exception
	{

			Row writeRow = null;
			if(!userDTOList.getData().isEmpty()){
		        createExcelsheet = getExcelSheetWithHeader(createExcelsheet,writeRow,createExcelworkbook,userDTOList.getData().get(0).getDatesList(),orgLocations);
			}
	        ResponseList<ApplicantsJoinedReportDTO> list = userDTOList;
	        int column=0;
	        for(ApplicantsJoinedReportDTO lrv : list.getData()){
	       	 writeRow = createExcelsheet.createRow(column+1);
	       	 createExcelsheet = createXlsSheet(createExcelsheet,writeRow,column,lrv,orgLocations);
	       	 column++;
	        }
			return createExcelsheet;
		
	}	
private Sheet getExcelSheetWithHeader(Sheet createExcelsheet, Row writeRow, Workbook createExcelworkbook,Set<String> datesList, OrgLocations orgLocations) {

		
		
		CellStyle style = createExcelworkbook.createCellStyle();
	     style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
	     style.setFillPattern(CellStyle.SOLID_FOREGROUND);
	     org.apache.poi.ss.usermodel.Font font = createExcelworkbook.createFont();
        font.setColor(IndexedColors.BLACK.getIndex());
         style.setFont(font);
		 
		 writeRow = createExcelsheet.createRow(0);
		 createExcelsheet.setColumnWidth(0, 10000);
		 createExcelsheet.setColumnWidth(1, 10000);
		 createExcelsheet.setColumnWidth(2, 5000);
		 createExcelsheet.setColumnWidth(3, 5000);
		 createExcelsheet.setColumnWidth(4, 5000);
//		 createExcelsheet.setColumnWidth(5, 5000);
//		 String headers [] = Constants.APPLICANTS_JOINED_LIST_REPORT_HEADERS;
		 
		 List<String> headerList = new LinkedList<>();
		 headerList.add("Recruiter Name");
		 for(String date : datesList) {
			 headerList.add(date.split("<<>>").length > 1 ? date.split("<<>>")[0] : date);
		 }
		 headerList.add("No Of Jobs");
		 headerList.add("Total Offer");
		 headerList.add("Total Joiners");
		 headerList.add("Total Leaves");
		 
		 String headers[] = new String[headerList.size()];
		 
		 int i=0;
		 for(String header : headerList) {
			 headers[i] = header;
			 i++;
		 }
		 
		 return createExcelsheet = commonUtil.setExcelHeaders(createExcelsheet,style,headers,0);
	}

private Sheet createXlsSheet(Sheet createExcelsheet, Row writeRow, int column,ApplicantsJoinedReportDTO userDTO, OrgLocations orgLocations) throws Exception {
	
	int cellCount = 0;
	if(userDTO.getName() !=null){
		 writeRow.createCell(cellCount++).setCellValue(userDTO.getName()); 
	 }
	else{
		   writeRow.createCell(cellCount++).setCellValue("--");   
	   }
	   /*if(userDTO.getFromDate()!=null){
		   writeRow.createCell(1).setCellValue(userDTO.getFromDate());
	   }
	   else{
		   writeRow.createCell(1).setCellValue("--");   
	   }*/
		if(userDTO.getDatesList()!=null && !userDTO.getDatesList().isEmpty()) {
			for(String date : userDTO.getDatesList()) {
				writeRow.createCell(cellCount++).setCellValue(date.split("<<>>").length > 1 ? date.split("<<>>")[1] : date);
			}
		}
	
	   if(userDTO.getRequirementCount() != null){
		   writeRow.createCell(cellCount++).setCellValue(userDTO.getTotalRequirementCount().longValue());
	   }
	   else{
		   writeRow.createCell(cellCount++).setCellValue("0");   
	   }
	   if(userDTO.getOfferCount() != null){
		   writeRow.createCell(cellCount++).setCellValue(userDTO.getOfferCount().longValue());
	   }
	   else{
		   writeRow.createCell(cellCount++).setCellValue("0");   
	   }
	   if(userDTO.getJoinedCount() !=null){
		   writeRow.createCell(cellCount++).setCellValue(userDTO.getJoinedCount().longValue());
	   }
	   else{
		   writeRow.createCell(cellCount++).setCellValue("0");   
	   }
	   
	   List<LeaveType> leaveTypesList=leaveTypeService.getLeaveTypeListByLocationId(orgLocations.getId().toString());
	 
		List<AppliedLeaveDateView> appliedLeaves = null;
		float appliedNoofLeaves = 0;
		float appliedLeaveCount = 0;
		float totalLeavesCount=0;
	   for(LeaveType leaveType:leaveTypesList){
		   AssociateBalanceLeaveView ablv = new AssociateBalanceLeaveView();
			ablv.setIncludeWeekoff(leaveType.getIncludeWeekoff());
			ablv.setIncludeHolidays(leaveType.getIncludeHolidays());
		   appliedLeaves = appliedLeaveDateService.getAppliedLeaveDatesByLeaveTypeIdAndOrgLocationIdAndUserId(orgLocations.getId(), leaveType.getId(), userDTO.getFromDate(), userDTO.getToDate(), userDTO.getUserId().longValue(),orgLocations.getOrg().getId());
			appliedNoofLeaves = (float)associateBalanceLeaveService.getExcludeHolidayLeaveCount(appliedLeaves,orgLocations,ablv,userDTO.getFromDate(),userDTO.getToDate(),leaveType);
			totalLeavesCount=totalLeavesCount+appliedNoofLeaves;
	   }
	 
	/*	 if(leaveReportDTO.getLeaveTypeBillable() !=null){
			 writeRow.createCell(5).setCellValue(leaveReportDTO.getLeaveTypeBillable()); 
		 }
		 else{
			   writeRow.createCell(5).setCellValue("--");   
		   }*/
//}
	   if(totalLeavesCount !=0){
		   writeRow.createCell(cellCount++).setCellValue(totalLeavesCount);
	   }
	   else{
		   writeRow.createCell(cellCount++).setCellValue("0");   
	   }
	   
return createExcelsheet;

}
@Override
public File exportAllApplicantJoinedListPdf(ResponseList<ApplicantsJoinedReportDTO> userDTOList, OrgLocations orgLocations,Date[] searchDates) throws Exception
{
	return pdfUtil.createAllApplicantJoinedListPdfTable(userDTOList,orgLocations,searchDates);

}

	@Override
	public Map<String, Long> getApplicantsJoinedCountByDates(String createdBy, Date fromDate, Date toDate, String dateFormat)
	{
		Map<String, Long> datesMap = new HashMap<>();
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		List<Object[]> datesList = userDao.getApplicantsJoinedCountByDates(createdBy, fromDate, toDate);
		if(datesList!= null && !datesList.isEmpty()) {
			for(Object[] obj : datesList) {
				datesMap.put(sdf.format(obj[0]), (Long) obj[1]);
			}
		}
		return datesMap;
	}

	@Override
	public List<ProbationDTO> getAllProbationUsersListForResourceManager(Long resourcemanagerId, Date futureDate,Long orgLocationId) {
		return userDao.getAllProbationUsersListForResourceManager(resourcemanagerId,futureDate,orgLocationId);
	}


	@Override
	public void sendEmailForNewReportingManager(User user, User loginUser, String password, Long locationId, Long existingReportingManagerID, Long currentReportingManagerID) throws Exception {
		
		 UserRole userRole = userRoleService.getUserRoleByUserIdAndLocationId(user.getId(), locationId);
		 UserRole exUserRole = userRoleService.getUserRoleByUserIdAndLocationId(existingReportingManagerID, locationId);
		 if(userRole == null){
		    userRole = userRoleService.getLoginUserPrimaryRole(user.getId());
		 }
		 if(exUserRole == null){
			 exUserRole = userRoleService.getLoginUserPrimaryRole(existingReportingManagerID);
		 }
		OrgLocations locations= orgLocationService.getOrgLocations(locationId);
		 
		 StringBuilder sb = new StringBuilder();
		 EmailMessage emailMessage = new EmailMessage();
		 emailMessage.setFirstName(user.getFirstName());
		 emailMessage.setLastName(user.getLastName());
		 emailMessage.setToEmail(user.getEmail());
		 emailMessage.setFromEmail(loginUser.getEmail());
		 emailMessage.setFromName(sb.append(loginUser.getFirstName()).append(" ").append(loginUser.getLastName()).toString());
		 emailMessage.setEmailType(Constants.CHANGE_REPORTING_MANAGER_EMAIL);

		 emailMessage.setCreatedBy(loginUser.getUserCode());
		 emailMessage.setOrgLocationId(locationId);
		 
		//setting the class filed values
		 Map<String, String> globalVarsMap = new HashMap<String, String>();
		 StringBuilder builder=new StringBuilder();
		 if(user.getLastName() != null && user.getLastName() !=""){
			 globalVarsMap.put("toAssociate",builder.append(user.getFirstName()).append(" ").append(user.getLastName()).toString());	 
		 }else{
			 globalVarsMap.put("toAssociate", user.getFirstName());	 
		 }
		 String loginUserFullName = loginUser.getFullName().substring(0, 1).toUpperCase() + loginUser.getFullName().substring(1);
		 globalVarsMap.put("loginUser",loginUserFullName);
		 globalVarsMap.put("LoginOrgName", locations.getOrgLocationName());
		 globalVarsMap.put("PreviousReportingManager",exUserRole.getUser().getFirstName()+" "+exUserRole.getUser().getLastName());
		 globalVarsMap.put("NewReportingManager",userRole.getReportingManager().getFullName());

		 List<EmailAttachment> attachmentList=new ArrayList<EmailAttachment>();
		 
		 
		 EmailScenario notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.CHANGE_REPORTING_MANAGER_NOTIFICATOPN, Constants.ENTITY_TYPE_NOTIFICATION);
		 String notificationBody = emailScenerioUtil.buildTemplateBody(notificationScenario.getBody(), globalVarsMap, notificationScenario);
		 notificationScenario.setBody(notificationBody);

		 
		commonUtil.sendTemplateEmail(emailMessage, globalVarsMap,userRole.getOrgLocations().getOrg(),userRole.getOrgLocations(),Constants.CHANGE_REPORTING_MANAGER_EMAIL,attachmentList);
		
		String actionLink="";
		emailScenerioUtil.sendNotification(actionLink, loginUser, user, locations, notificationScenario, new HashMap<String, String>());
	}

	@Override
	public void sendEmployemntConfirmationMailToAssocaite(User fromUser, User toUser,OrgLocations orgLocations,String confirmationDate, String designation) throws Exception {
		
		 EmailMessage emailMessage = new EmailMessage();
		 emailMessage.setFirstName(toUser.getFirstName());
		 emailMessage.setLastName(toUser.getLastName());
		 emailMessage.setToEmail(toUser.getEmail());
		 emailMessage.setFromEmail(fromUser.getEmail());
		 
		 StringBuilder from=new StringBuilder();
		 if(fromUser.getLastName() != null && fromUser.getLastName() !=""){
			 emailMessage.setFromName(from.append(fromUser.getFirstName()).append(" ").append(fromUser.getLastName()).toString()); 
		 }else{
			 emailMessage.setFromName(fromUser.getFirstName());
		 }
		 emailMessage.setEmailType(Constants.CONFIRMATION_OF_EMPLOYMENT_MAIL);

		 emailMessage.setCreatedBy(fromUser.getUserCode());
		 emailMessage.setOrgLocationId(orgLocations.getId());
		 
		//setting the class filed values
		 Map<String, String> globalVarsMap = new HashMap<String, String>();
		 StringBuilder builder=new StringBuilder();
		 if(toUser.getLastName() != null && toUser.getLastName() !=""){
			 globalVarsMap.put("associateName",builder.append(toUser.getFirstName()).append(" ").append(toUser.getLastName()).toString());	 
		 }else{
			 globalVarsMap.put("associateName", toUser.getFirstName());	 
		 }
		 globalVarsMap.put("loginUser",emailMessage.getFromName());
		 globalVarsMap.put("associateEmail", toUser.getEmail());
		 globalVarsMap.put("associateRole",designation);
		 globalVarsMap.put("confirmationDate",commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(), confirmationDate).toString());
		 globalVarsMap.put("loginOrgName",orgLocations.getOrgLocationName());

		 List<EmailAttachment> attachmentList = new ArrayList<EmailAttachment>();
		 
		 UserRole uRole = userRoleService.getUserRoleByUserId(toUser.getId());
		 
		commonUtil.sendTemplateEmail(emailMessage, globalVarsMap,uRole.getOrgLocations().getOrg(),uRole.getOrgLocations(),Constants.CONFIRMATION_OF_EMPLOYMENT_MAIL,attachmentList);
		
		
	}

	@Override
	public User validateSSNDuplicate(String ssnNumber, Long locationId,String userId)
	{
		return userDao.validateSSNDuplicate(ssnNumber,locationId,userId);
	}
	
	@Override
	public int addItemToDashboard(String itemName, String itemSize, List<String> rolesList) {
		int count = 0;
		try {
			List<DashboardItems> dashboardItemsList = userDao.getAllDashboardItems();
			if (dashboardItemsList != null && !dashboardItemsList.isEmpty()) {
				for (DashboardItems dashboardItems : dashboardItemsList) {
					UserRole userRole = userRoleService.getUserRoleByUserIdAndLocationId(dashboardItems.getUser().getId(), dashboardItems.getOrgLocations().getId());
					if (userRole != null) {
						List<Long> modulesId = modulesService.getOrgRegisteredModulesByOrgLocationId(dashboardItems.getOrgLocations());
						List<SourceleadActions> actionsList = sourceleadActionsService.getLoginUserRoleActionsByOrgLocationId(modulesId, userRole.getRoles().getId(), dashboardItems.getOrgLocations().getId());
						if (actionsList != null && !actionsList.isEmpty()) {
							boolean hasRole = false;
							for (SourceleadActions action : actionsList) {
								for (String roleAction : rolesList) {
									if (action.getActionRole().equalsIgnoreCase(roleAction)) {
										hasRole = true;
										break;
									}
								}
							}
							if (hasRole) {
								boolean containsItem = false;
								JSONArray jsonArray = new JSONArray(dashboardItems.getItems());
								if (jsonArray != null && jsonArray.length() > 0) {
									for (int i = 0; i < jsonArray.length(); i++) {
										JSONObject jsonObject = jsonArray.getJSONObject(i);
										if(jsonObject.has("name") && jsonObject.getString("name").equalsIgnoreCase(itemName)) {
											containsItem = true;
										}
									}
								}
								if(!containsItem) {
									String items = dashboardItems.getItems();
									JsonObject jsonObject = new JsonObject();
									jsonObject.addProperty("name", itemName);
									jsonObject.addProperty("checked", true);
									jsonObject.addProperty("order", jsonArray.length() + 1);
									jsonObject.addProperty("size", itemSize);
									items = items.replace("]", "," + jsonObject + "]");
									dashboardItems.setItems(items);
									userDao.saveDashboardItems(dashboardItems);
									System.out.println(dashboardItems.getId() + " : " + items);
									count++;
								}
							}
						}
					}

				}
			}
			System.out.println("Total Updates : " + count);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}

	
}

