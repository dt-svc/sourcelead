package com.bis.sourcelead.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import javax.mail.internet.InternetAddress;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bis.sourcelead.SourceleadPasswordEncoder;
import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.OrgDao;
import com.bis.sourcelead.dao.OrgLocationsDao;
import com.bis.sourcelead.dao.UserDao;
import com.bis.sourcelead.dto.AddUserDTO;
import com.bis.sourcelead.dto.CandidateDTO;
import com.bis.sourcelead.elk.index.UserIndex;
import com.bis.sourcelead.elk.index.helper.UserIndexHelper;
import com.bis.sourcelead.elk.setup.SourceleadELKBaseIndexer;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.Department;
import com.bis.sourcelead.entity.EmailAttachment;
import com.bis.sourcelead.entity.EmailMessage;
import com.bis.sourcelead.entity.EmailScenario;
import com.bis.sourcelead.entity.Employees;
import com.bis.sourcelead.entity.MasterCandidate;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgRelations;
import com.bis.sourcelead.entity.OrgSpoc;
import com.bis.sourcelead.entity.ProjectMembers;
import com.bis.sourcelead.entity.ProjectMembersHistory;
import com.bis.sourcelead.entity.Projects;
import com.bis.sourcelead.entity.Roles;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserRole;
import com.bis.sourcelead.enums.Status;
import com.bis.sourcelead.form.UserProfile;
import com.bis.sourcelead.service.CandidateService;
import com.bis.sourcelead.service.DepartmentService;
import com.bis.sourcelead.service.EmailScenarioService;
import com.bis.sourcelead.service.EmployeesService;
import com.bis.sourcelead.service.MasterCandidateService;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.OrgRelationsService;
import com.bis.sourcelead.service.OrgSpocService;
import com.bis.sourcelead.service.ProjectMembersService;
import com.bis.sourcelead.service.ProjectsService;
import com.bis.sourcelead.service.RolesService;
import com.bis.sourcelead.service.UserRoleService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.EmailScenerioUtil;
import com.bis.sourcelead.util.SourceLeadRandomCodeHelper;
import com.bis.sourcelead.util.UniversalSearchHelper;

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
	
	@Override
	public User findByUserName(String username) {
		logger.debug("In UserService class...");
		return userDao.findByUserName(username);
	}

	@Override
	public User addUser(User user) throws Exception {
		logger.debug("In UserService class...");
		user.setCreatedDate(new Date());
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
	@Override
	public User storeUser(AddUserDTO addUserDTO,CandidateDTO candidateDTO,User loginuser,OrgLocations orgLocations,String sourceType,String responderType,Org loginOrg) throws Exception {
	   User user1 = null;
	   User user = null;
	   String password = null;
	   UserRole userRole = null;
	   boolean isUserActive = false;
	   user = userDao.findByUserName(addUserDTO.getEmail());
	   if(null != user){
		  isUserActive = userRoleService.isUserActive(user.getId());
	   }
	   
	   if(null == user){
		   user = getUserDetails(addUserDTO,loginuser.getUserCode());
		   password = SourceLeadRandomCodeHelper.generateRandomPassword();
 		   user.setPassword(sourceleadPasswordEncoder.encode(password));
		}else if(isUserActive == false){
			password = SourceLeadRandomCodeHelper.generateRandomPassword();
			user.setPassword(sourceleadPasswordEncoder.encode(password));
		}
	   
	    if(StringUtils.isNotBlank(addUserDTO.getPhoneNumber())){
			user.setMobilePhone(addUserDTO.getPhoneNumber());
		}
		
	    if(StringUtils.isNotBlank(addUserDTO.getIsMailSent()) && ("Y".equals(addUserDTO.getIsMailSent()))){
			user.setIsMailSent(Status.Y);
		}else{
			user.setIsMailSent(Status.N);
		}
	   
	    if(sourceType.equalsIgnoreCase("PROJECTS")){
	 		user.setUserType("Employee");
	    } 
		
       //For saving the User Role 
	   userRole = userRoleService.getUserDetailsByEmailAndLocationId(addUserDTO.getEmail(),orgLocations.getId());
	   if(userRole == null){
		   userRole = getUserRoleDetails(addUserDTO,user,loginuser.getUserCode(),orgLocations.getId());
		   if(null == user.getId()){
				userRole.setIsPrimary(1);
				 if(sourceType.equalsIgnoreCase("PROJECTS")){
					 userRole.setUserType("Employee");
				 }
		  }else{
			  if(isUserActive == false){
				  userRole.setIsPrimary(1);
			  }else{
				  userRole.setIsPrimary(0);
			  }
			}
	   }
		
		HashSet<UserRole> userRoleList = new HashSet<UserRole>();
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
		
		//for updating cosultantUserId in candidate
		if(StringUtils.isNotBlank(candidateDTO.getAssociateId())){
			Candidate candidate = candidateService.getCandidateByCandidateId(Long.parseLong(candidateDTO.getAssociateId()));
			if(null != candidate){
				candidate.setConsultantUser(user1);
				candidate.setUpdatedBy(loginuser.getUserCode());
				candidate.setUpdatedDate(commonUtil.currentDate());
				candidateService.saveOrUpdateCandidate(candidate);
				candidateService.saveOrUpdateCandidateElkDocument(candidate);
			}	
		}else{
			MasterCandidate masterCandidate = null;
			masterCandidate = masterCandidateService.getMasterCandidateByInteligenceCandidateDTO(candidateDTO);
			if(null == masterCandidate){
				masterCandidate = masterCandidateService.storeMasterCandidateByCandidateDTO(candidateDTO, loginuser);
			}
			candidateService.storeCandidateForUsers(candidateDTO, user, orgLocations, masterCandidate);
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
			saveOrUpdateUserElkDocument(userRole,employee,loginuser.getUserCode());
			user1.setTempPwd(password);
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

			/*if(StringUtils.isNotBlank(addUserDTO.getRoleTypeId())){
				roles = rolesService.getRolesById(Long.parseLong(addUserDTO.getRoleTypeId()));
			}else{
				roles = rolesService.getRolesByRoleNameAndOrgLocationId(Constants.ROLE_MANAGER,orgLocations.getId());
			}*/
			
			if(StringUtils.isNotBlank(addUserDTO.getRoleTypeId())){
				if(!isNullOrEmpty(addUserDTO.getRoleTypeId())){
					roles = rolesService.getRolesById(Long.parseLong(addUserDTO.getRoleTypeId()));
				}else{
					roles = rolesService.getRolesByRoleNameAndOrgLocationId(Constants.ROLE_MANAGER,orgLocations.getId());
				}
			}


		} catch (Exception e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}
		
		if(StringUtils.isNotBlank(addUserDTO.getDepartment())){
			
			List<Department> departmentList = departmentService.getDepartmentByOrgLocationId(orgLocations.getId());
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
				   //SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
					 OrgLocations orgLoc = orgLocationService.getOrgLocations(orgLocationsId);
						 //SimpleDateFormat formatter = orgLoc.getLocFullFormDateFormatter();
						 employees.setHireDate(commonUtil.orgLocationDateFormat(orgLoc.getDateFormat(), addUserDTO.getDateofJoin()));
				  
				} catch (Exception e) {
				  e.printStackTrace();
				}
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
		 
		 boolean isUserActive = false;
		 isUserActive = userRoleService.isUserActive(user.getId());
		 
		 StringBuilder sb = new StringBuilder();
		 EmailMessage emailMessage = new EmailMessage();
		 StringBuilder builder=new StringBuilder();
		 emailMessage.setFirstName(builder.append(user.getFirstName()).append(" ").append(user.getLastName()).toString());
		 emailMessage.setLastName(user.getLastName());
		 emailMessage.setToEmail(user.getEmail());
		 emailMessage.setFromEmail(loginUser.getEmail());
		 emailMessage.setFromName(sb.append(loginUser.getFirstName()).append(" ").append(loginUser.getLastName()).toString());
		 emailMessage.setEmailType(Constants.CUSTOM_USER_EMAIL);

		 emailMessage.setCreatedBy(loginUser.getUserCode());
		 emailMessage.setOrgLocationId(locationId);
		 
		//setting the class filed values
		 Map<String, String> globalVarsMap = new HashMap<String, String>();
		 globalVarsMap.put("firstName", emailMessage.getFirstName());
		 /*globalVarsMap.put("lastName",emailMessage.getLastName());*/
		 globalVarsMap.put("administratorFirstName", loginUser.getFirstName());
		 globalVarsMap.put("administratorLastName",loginUser.getLastName());
		 globalVarsMap.put("role",userRole.getUserType());
		 StringBuilder loginBuilder = new StringBuilder();
		 globalVarsMap.put("loginURL",loginBuilder.append("<a href='").append(sourceleadUrl).append("/").append("' style='text-decoration:none; '><div style=' width:220px; border-radius:30px; background-color:#214b90; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Login</div></a>").toString());
		 globalVarsMap.put("userEmailId",user.getEmail());
		 if(isUserActive == false){
			 globalVarsMap.put("password",password);
		 }else{
			 globalVarsMap.put("password","Login with your existing sourcelead credentials");
		 }
		 
		
		 //setting the Recipient information
		 InternetAddress[] iAdressArray = InternetAddress.parse(emailMessage.getToEmail());
		 
		 //getting the current EmailScenario by EmailScenario unique name and entity type
		 EmailScenario  emailScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.INVITE_USER,Constants.ENTITY_TYPE_EMAIL);
		
		 //covert subject,body and signature
		 String subject = emailScenerioUtil.bulidSubject(emailScenario.getSubject(),globalVarsMap);
		 String body = emailScenerioUtil.buildTempBody(emailScenario.getBody(),globalVarsMap);
		 String signature = emailScenerioUtil.buildSignature(emailScenario.getSignature(),globalVarsMap);
		 String url = sourceleadUrl;
		 
		 //construct HTML template using required data
		 String emailTemplate = emailScenerioUtil.AssembleEmailMessage(emailScenario,emailMessage, subject, body, signature, url,userRole.getOrgLocations().getOrg(),userRole.getOrgLocations());
		 emailMessage.setSubject(subject);
		 emailMessage.setMessageBody(emailTemplate);
		 
		 //calling the sendEmail method of emailScenerioUtil
		 emailScenerioUtil.sendEmail(emailMessage,locationId,iAdressArray,new ArrayList<EmailAttachment>());
		
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
	public User saveUserProfile(UserProfile userProfile, MultipartFile multiFile,User user) throws Exception {
		
		user = getUserProfileDetails(userProfile,multiFile,user);
		userDao.addUser(user);
		return user;
	}
	
	
	private User getUserProfileDetails(UserProfile userProfile, MultipartFile multiFile,User user){
		user.setFirstName(userProfile.getFirstName());
		user.setLastName(userProfile.getLastName());
		user.setMobilePhone(userProfile.getMobilePhone());
		user.setJobTitle(userProfile.getJobTitle());
		user.setLocation(userProfile.getLocation());
		user.setLinkedIn(userProfile.getLinkedIn());
		user.setFacebook(userProfile.getFacebook());
		user.setTwitter(userProfile.getTwitter());
		user.setGooglePlus(userProfile.getGooglePlus());
		
        if(null != multiFile.getContentType() && !multiFile.getContentType().equalsIgnoreCase("application/octet-stream")){
    	  try {
    	   		user.setFileContentType(multiFile.getContentType());
    	   		user.setDocFile(multiFile.getBytes());
    	   
	       }catch (Exception e) {
    	      	 e.printStackTrace();
			    }
        }
		
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
			 globalVarsMap.put("firstName", emailMessage.getFirstName());
			 globalVarsMap.put("lastName",emailMessage.getLastName());
			 globalVarsMap.put("vendorFirstName", ownerUser.getFirstName());
			 globalVarsMap.put("vendorLastName",ownerUser.getLastName());
			 globalVarsMap.put("clientFirstName", user.getFirstName());
			 globalVarsMap.put("clientlastName",user.getLastName());
			 globalVarsMap.put("clientOrgName",respondingPartyOrgLocation.getOrgLocationName());
			 globalVarsMap.put("vendorOrgName",orgLocation.getOrgLocationName());
			 StringBuilder clientLoginUser = new StringBuilder();
			 if(null != loginUser.getLastName()){
				 globalVarsMap.put("loginUsername",clientLoginUser.append(loginUser.getFirstName()).append(" ").append(loginUser.getLastName()).toString());
			 }else{
				 globalVarsMap.put("loginUsername",loginUser.getLastName());
			 }
			 StringBuilder urlBuilder = new StringBuilder();
			 globalVarsMap.put("acceptURL",urlBuilder.append("<a href='").append(sourceleadUrl).append("/").append("acceptClientOrVendorInvitation?respondingOrgType=VMS&respondingPartyOrgLocationId=").append(respondingPartyOrgLocation.getId()).append("&initiatingPartyOrgLocationId=").append(loginOrgLocation.getId()).append("&UUID=").append(UUID).append("&spocUUID=").append(spocUUID).append("' style='text-decoration:none; '><div style=' width:220px; border-radius:30px; background-color:#214b90; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Accept</div></a>").toString());
			 StringBuilder rejecturlBuilder = new StringBuilder();
			 globalVarsMap.put("rejectURL",rejecturlBuilder.append("<a href='").append(sourceleadUrl).append("/").append("acceptClientOrVendorInvitation?actionLink=rejectNotification&respondingOrgType=VMS&respondingPartyOrgLocationId=").append(respondingPartyOrgLocation.getId()).append("&initiatingPartyOrgLocationId=").append(loginOrgLocation.getId()).append("&UUID=").append(UUID).append("&spocUUID=").append(spocUUID).append("' style='text-decoration:none; '><div style=' width:220px; border-radius:30px; background-color:#214b90; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Reject</div></a>").toString());
			 StringBuilder linkBuilder = new StringBuilder();
			 globalVarsMap.put("supportLink",linkBuilder.append("<a href='http://support.sourcelead.com/faq' target='_blank' style='outline:none; border:none;'>http://support.sourcelead.com/faq</a>").toString());
			
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
			    globalVarsMap.put("password",password);
			 }else if(null == password && isUserActive == false){
				 password = SourceLeadRandomCodeHelper.generateRandomPassword();
				 user.setPassword(sourceleadPasswordEncoder.encode(password));
				 user.setIsMailSent(Status.Y);
				 userDao.addUser(user);
				 globalVarsMap.put("userName",user.getEmail());
				 globalVarsMap.put("password",password);
			 }else{
				 globalVarsMap.put("userName",user.getEmail());
				 globalVarsMap.put("password","Login with your existing sourcelead credentials");
			 } 
		     StringBuilder actionUrlBuilder = new StringBuilder();
			 actionLink = actionUrlBuilder.append("").append(sourceleadUrl).append("/").append("acceptClientOrVendorInvitation?actionLink=acceptNotification&respondingOrgType=VMS&respondingPartyOrgLocationId=").append(respondingPartyOrgLocation.getId()).append("&initiatingPartyOrgLocationId=").append(loginOrgLocation.getId()).append("&UUID=").append(UUID).append("&spocUUID=").append(spocUUID).append(" ").toString();
			 
			 StringBuilder rejectActionBuilder = new StringBuilder();
			 rejectActionLink = rejectActionBuilder.append("").append(sourceleadUrl).append("/").append("acceptClientOrVendorInvitation?actionLink=rejectNotification&respondingOrgType=VMS&respondingPartyOrgLocationId=").append(respondingPartyOrgLocation.getId()).append("&initiatingPartyOrgLocationId=").append(loginOrgLocation.getId()).append("&UUID=").append(UUID).append("&spocUUID=").append(spocUUID).append(" ").toString();
			 
			 
		 }else if("Vendor".equals(responderType)){
			 globalVarsMap.put("firstName", emailMessage.getFirstName());
			 globalVarsMap.put("lastName",emailMessage.getLastName());
			 globalVarsMap.put("vendorFirstName", user.getFirstName());
			 globalVarsMap.put("vendorLastName",user.getLastName());
			 globalVarsMap.put("clientFirstName", ownerUser.getFirstName());
			 globalVarsMap.put("clientLastName",ownerUser.getLastName());
			 globalVarsMap.put("vendorOrgName",respondingPartyOrgLocation.getOrgLocationName());
			 globalVarsMap.put("clientOrgName",orgLocation.getOrgLocationName());
			 
			 StringBuilder vendorLoginUser = new StringBuilder();
			 if(null != loginUser.getLastName()){
				 globalVarsMap.put("loginUsername",vendorLoginUser.append(loginUser.getFirstName()).append(" ").append(loginUser.getLastName()).toString());
			 }else{
				 globalVarsMap.put("loginUsername",loginUser.getLastName());
			 }
			 StringBuilder actionUrlBuilder = new StringBuilder();
			 globalVarsMap.put("acceptURL",actionUrlBuilder.append("<a href='").append(sourceleadUrl).append("/").append("acceptClientOrVendorInvitation?respondingOrgType=ATS&respondingPartyOrgLocationId=").append(respondingPartyOrgLocation.getId()).append("&initiatingPartyOrgLocationId=").append(loginOrgLocation.getId()).append("&UUID=").append(UUID).append("&spocUUID=").append(spocUUID).append("' style='text-decoration:none; '><div style=' width:220px; border-radius:30px; background-color:#214b90; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Accept</div></a>").toString());
			 StringBuilder rejectActionUrlBuilder = new StringBuilder();
			 globalVarsMap.put("rejectURL",rejectActionUrlBuilder.append("<a href='").append(sourceleadUrl).append("/").append("acceptClientOrVendorInvitation?actionLink=rejectNotification&respondingOrgType=ATS&respondingPartyOrgLocationId=").append(respondingPartyOrgLocation.getId()).append("&initiatingPartyOrgLocationId=").append(loginOrgLocation.getId()).append("&UUID=").append(UUID).append("&spocUUID=").append(spocUUID).append("' style='text-decoration:none; '><div style=' width:220px; border-radius:30px; background-color:#214b90; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Reject</div></a>").toString());
			 StringBuilder vendorLinkBuilder = new StringBuilder();
			 globalVarsMap.put("supportLink",vendorLinkBuilder.append("<a href='http://support.sourcelead.com/faq' target='_blank' style='outline:none; border:none;'>http://support.sourcelead.com/faq</a>").toString());
			 
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
				 user.setIsMailSent(Status.Y);
				 userDao.addUser(user);
				 globalVarsMap.put("userName",user.getEmail());
				 globalVarsMap.put("password",password);
			 }else{
				 globalVarsMap.put("userName",user.getEmail());
				 globalVarsMap.put("password","Login with your existing sourcelead credentials");
			 }
			 
			 StringBuilder actionLinkBuilder = new StringBuilder();
			 actionLink = actionLinkBuilder.append("").append(sourceleadUrl).append("/").append("acceptClientOrVendorInvitation?actionLink=acceptNotification&respondingOrgType=ATS&respondingPartyOrgLocationId=").append(respondingPartyOrgLocation.getId()).append("&initiatingPartyOrgLocationId=").append(loginOrgLocation.getId()).append("&UUID=").append(UUID).append("&spocUUID=").append(spocUUID).append("").toString();
			 
			 StringBuilder rejectActionBuilder = new StringBuilder();
			 rejectActionLink = rejectActionBuilder.append("").append(sourceleadUrl).append("/").append("acceptClientOrVendorInvitation?actionLink=rejectNotification&respondingOrgType=ATS&respondingPartyOrgLocationId=").append(respondingPartyOrgLocation.getId()).append("&initiatingPartyOrgLocationId=").append(loginOrgLocation.getId()).append("&UUID=").append(UUID).append("&spocUUID=").append(spocUUID).append("").toString();
		 }
		 
		 //setting the Recipient information
		 InternetAddress[] iAdressArray = InternetAddress.parse(emailMessage.getToEmail());
		
		 //getting the current EmailScenario by EmailScenario unique name and entity type
		 EmailScenario  emailScenario = null;
		 EmailScenario noificationEmailScenario = null;
		 if("Client".equals(responderType)){
			 emailScenario =  emailScenarioService.getEmailScenarioByUniqueName(Constants.ACCEPT_CLIENT_INVITATION,Constants.ENTITY_TYPE_EMAIL);
			 noificationEmailScenario =  emailScenarioService.getEmailScenarioByUniqueName(Constants.ACCEPT_CLIENT_INVITATION,Constants.ENTITY_TYPE_NOTIFICATION);
		 }else if("Vendor".equals(responderType)){
			 emailScenario =  emailScenarioService.getEmailScenarioByUniqueName(Constants.ACCEPT_VENDOR_INVITATION,Constants.ENTITY_TYPE_EMAIL);
			 noificationEmailScenario =  emailScenarioService.getEmailScenarioByUniqueName(Constants.ACCEPT_VENDOR_INVITATION,Constants.ENTITY_TYPE_NOTIFICATION);
		 }
		
		 //covert subject,body and signature
		 String subject = emailScenerioUtil.bulidSubject(emailScenario.getSubject(),globalVarsMap);
		 String body = emailScenerioUtil.buildTempBody(emailScenario.getBody(),globalVarsMap);
		 String signature = emailScenerioUtil.buildSignature(emailScenario.getSignature(),globalVarsMap);
		 String url = sourceleadUrl;
		 
		 //construct HTML template using required data
		 String emailTemplate = emailScenerioUtil.AssembleEmailMessage(emailScenario,emailMessage, subject, body, signature, url, loginOrg,respondingPartyOrgLocation);
		 emailMessage.setSubject(subject);
		 emailMessage.setMessageBody(emailTemplate);
		 emailMessage.setOrgLocationId(respondingPartyOrgLocation.getId());
		 
		 //calling the sendEmail method of emailScenerioUtil
		 emailMessage.setEmailType(Constants.CUSTOM_USER_EMAIL);
		 emailScenerioUtil.sendEmail(emailMessage,respondingPartyOrgLocation.getId(),iAdressArray,new ArrayList<EmailAttachment>());
		 String notificationbody = emailScenerioUtil.buildTempBody(noificationEmailScenario.getBody(),globalVarsMap);
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
					ArrayList<MatchQueryBuilder> matchQueryBuilderList = universalSearchHelper.getUsersMatchQueryBuilderList(keywd);

					if (null != matchQueryBuilderList && !matchQueryBuilderList.isEmpty()) {
						for (MatchQueryBuilder matchQueryBuilder : matchQueryBuilderList) {
							anyBoolSubQueryBuilder.should(matchQueryBuilder);
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
		 globalVarsMap.put("role",userRole.getUserType());
		 StringBuilder loginBuilder = new StringBuilder();
		 globalVarsMap.put("loginURL",loginBuilder.append("<a href='").append(sourceleadUrl).append("/").append("' style='text-decoration:none; '><div style=' width:220px; border-radius:30px; background-color:#214b90; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Login</div></a>").toString());
		 globalVarsMap.put("userEmailId",user.getEmail());
		 if(isUserActive == false){
			 globalVarsMap.put("password",password);
		 }else{
			 globalVarsMap.put("password","Login with your existing sourcelead credentials");
		 }
		 
		
		 //setting the Recipient information
		 InternetAddress[] iAdressArray = InternetAddress.parse(emailMessage.getToEmail());
		 
		 //getting the current EmailScenario by EmailScenario unique name and entity type
		 EmailScenario  emailScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.INVITE_USER,Constants.ENTITY_TYPE_EMAIL);
		
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
		 emailMessage.setFromEmail(defaultFromEmailAddressForProd);
		 emailMessage.setCreatedBy(user.getUserCode());
		// StringBuilder msgBuild = new StringBuilder();
		// emailMessage.setFromName(msgBuild.append(loginUser.getFirstName()).append(" ").append(loginUser.getLastName()).toString());
		// emailMessage.setEmailType(Constants.CUSTOM_USER_EMAIL);
		//setting the class filed values
		 Map<String, String> globalVarsMap = new HashMap<String, String>();
		 StringBuffer userNameStringBuffer =  new StringBuffer();
		 if (user.getLastName() != null && !"".equalsIgnoreCase(user.getLastName()) ) {
			 globalVarsMap.put("userName",userNameStringBuffer.append(user.getFirstName()).append(" ").append(user.getLastName()).toString());
			 } else {
				 globalVarsMap.put("userName", user.getFirstName());
			}
		
		 InternetAddress[] iAdressArray = InternetAddress.parse(emailMessage.getToEmail());
		
		 //getting the current EmailScenario by EmailScenario unique name and entity type
		 //using same migration which has been already used in updatedRequirementMailToManager method
		 EmailScenario  emailScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.PASSWORD_RESET_MAIL_TO_USER,Constants.ENTITY_TYPE_EMAIL);
		 EmailScenario  notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.PASSWORD_RESET_MAIL_TO_USER,Constants.ENTITY_TYPE_NOTIFICATION);

		 
		 //covert subject,body and signature
		 String subject = emailScenerioUtil.bulidSubject(emailScenario.getSubject(),globalVarsMap);
		 String body = emailScenerioUtil.buildTempBody(emailScenario.getBody(),globalVarsMap);
		 String signature = emailScenerioUtil.buildSignature(emailScenario.getSignature(),globalVarsMap);
		 String url = sourceleadUrl;
		 
		 String notificationbody = emailScenerioUtil.buildTempBody(notificationScenario.getBody(),globalVarsMap);
		 notificationScenario.setBody(notificationbody);

		 //construct HTML template using required data
		 String emailTemplate = emailScenerioUtil.AssembleEmailMessage(emailScenario,emailMessage, subject, body, signature, url, orgLocations.getOrg(),orgLocations);
		 emailMessage.setSubject(subject);
		 emailMessage.setMessageBody(emailTemplate);
		 
		 //calling the sendEmail method of emailScenerioUtil
		 emailScenerioUtil.sendEmail(emailMessage,orgLocations.getId(),iAdressArray,new ArrayList<EmailAttachment>());
		 String actionLink="";
		 emailScenerioUtil.sendNotification(actionLink, user, user, orgLocations, notificationScenario);
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
	
}
