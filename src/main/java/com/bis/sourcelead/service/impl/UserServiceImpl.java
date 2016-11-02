package com.bis.sourcelead.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
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
import com.bis.sourcelead.dao.UserDao;
import com.bis.sourcelead.entity.EmailAttachment;
import com.bis.sourcelead.entity.EmailMessage;
import com.bis.sourcelead.entity.EmailRecipient;
import com.bis.sourcelead.entity.EmailScenario;
import com.bis.sourcelead.entity.Employees;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.ProjectMembers;
import com.bis.sourcelead.entity.ProjectMembersHistory;
import com.bis.sourcelead.entity.Projects;
import com.bis.sourcelead.entity.Roles;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserRole;
import com.bis.sourcelead.enums.Status;
import com.bis.sourcelead.form.UserProfile;
import com.bis.sourcelead.service.EmailScenarioService;
import com.bis.sourcelead.service.EmployeesService;
import com.bis.sourcelead.service.ProjectMembersService;
import com.bis.sourcelead.service.ProjectsService;
import com.bis.sourcelead.service.RolesService;
import com.bis.sourcelead.service.UserRoleService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.EmailScenerioUtil;
import com.bis.sourcelead.util.SourceLeadRandomCodeHelper;

/**
 * This Service Class is used to get User Object
 * 
 * @author: Naresh Bolisetty
 * @date: 27-May-2016
 */

@Service
public class UserServiceImpl implements UserService {

	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

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
	 
	@Value("${sourceleadUrl}")
    private String sourceleadUrl;
	
	@Autowired
	CommonUtil commonUtil;
	
	@Autowired
	private HttpServletRequest request;

	@Override
	public User findByUserName(String username) {
		logger.debug("In UserService class...");
		return userDao.findByUserName(username);
	}

	@Override
	public User addUser(User user) throws Exception {
		logger.debug("In UserService class...");
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
	public User storeUser(JSONObject jsonObj,User loginuser,OrgLocations orgLocations,String sourceType,String responderType,Org loginOrg) throws Exception {
	   User user1 = null;
	   User user = null;
	   String password = null;
	   UserRole userRole = null;
	   user = userDao.findByEmail(jsonObj.getString("email"));
	   
	   if(null == user){
		   user = getUserDetails(jsonObj,loginuser.getUserCode());
		   password = SourceLeadRandomCodeHelper.generateRandomPassword();
		   user.setPassword(sourceleadPasswordEncoder.encode(password));
		}else if(null != user && user.getUserState().equals(Status.NOT_LOGGEDIN)){
			password = SourceLeadRandomCodeHelper.generateRandomPassword();
			user.setPassword(sourceleadPasswordEncoder.encode(password));
		}
		
	    if((jsonObj.has("isMailSent") && !isNullOrEmpty(jsonObj.getString("isMailSent"))) && ("Y".equals(jsonObj.getString("isMailSent")))){
			user.setIsMailSent(Status.Y);
		}else{
			user.setIsMailSent(Status.N);
		}
	   
	    if(sourceType.equalsIgnoreCase("PROJECTS")){
	 		user.setUserType("Employee");
	    } 
		
       //For saving the User Role 
	   userRole = userRoleService.getUserDetailsByEmailAndLocationId(jsonObj.getString("email"),orgLocations.getId());
	   if(userRole == null){
		   userRole = getUserRoleDetails(jsonObj,user,loginuser.getUserCode(),orgLocations.getId());
		   if(null == user.getId()){
				userRole.setIsPrimary(1);
		  }else{
				userRole.setIsPrimary(0);
			}
	   }
		
		HashSet<UserRole> userRoleList = new HashSet<UserRole>();
		userRoleList.add(userRole);
		user.setUserRole(userRoleList);
		
	   //For saving Employee details (Every User is an Employee)
		Employees employee = getEmployeeDetails(user,jsonObj,orgLocations.getId(),loginuser.getUserCode());
		HashSet<Employees> employeeList = new HashSet<Employees>();
		employeeList.add(employee);
		user.setEmployee(employeeList);
		
		 //For saving the User  
		try {
			user1 = userDao.addUser(user);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		//For saving Employee details (Every User is an Employee)
		if(jsonObj.has("projectId") && !isNullOrEmpty(jsonObj.getString("projectId"))){
			ProjectMembers pm = getProjectMembersDetails(employee,jsonObj,loginuser.getUserCode(),orgLocations.getId());
			try {
				projectMembersService.saveProjectMember(pm);
			} catch (Exception e) {
				e.printStackTrace();
			}
			commonUtil.storeTimesheetSubmissionRecords(user);
		}
		
		   //send mail to user
			if(("".equals(responderType))  && (sourceType.equalsIgnoreCase("CREATEUSER") || sourceType.equalsIgnoreCase("PROJECTS"))
					 &&  (jsonObj.has("isMailSent") && !isNullOrEmpty(jsonObj.getString("isMailSent")))
					 && ("Y".equals(jsonObj.getString("isMailSent")))){
				sendEmail(user,loginuser,password);
			}
			
			if((("Client".equals(responderType) || "Vendor".equals(responderType)) 
					&& jsonObj.has("isMailSent") && !isNullOrEmpty(jsonObj.getString("isMailSent"))) 
					&& ("Y".equals(jsonObj.getString("isMailSent")))){
				sendEmailToClientOrVendor(user,loginuser,password,responderType,loginOrg,orgLocations.getOrg(),orgLocations);
			}
			/**
			 * This method is used to newly added admin as a level2 manager for all existing projectmembers
			 * @author: Naresh Bolisetty
			 * @throws Exception 
			 * 
			 */
			if(userRole != null && StringUtils.isNotEmpty(userRole.getRoles().getRoleName()) && userRole.getRoles().getRoleName().equalsIgnoreCase(Constants.ROLE_ADMIN)){
				commonUtil.addAdminAsSecondLevelManager(orgLocations.getId(), user, loginuser);
			}
		 
        return user1;
				
		
	}
	
	@Override
	public void createUser(JSONObject jsonObj,User loginUser , Long orgLocationsId) throws Exception {
		logger.debug("In UserService class createUser method...");
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
			commonUtil.storeTimesheetSubmissionRecords(user);
		}
		
		
		//send mail to user
		if((jsonObj.has("isMailSent") && !isNullOrEmpty(jsonObj.getString("isMailSent"))) && ("Y".equals(jsonObj.getString("isMailSent")))){
			sendEmail(user,loginUser,password);
		}
		 
	}
	
	private User getUserDetails(JSONObject jsonObj,String userCode) throws JSONException {
		User user = new User();
		
		if(jsonObj.has("firstName")){
			user.setFirstName(jsonObj.getString("firstName"));
		}
		
		if(jsonObj.has("lastName")){
			user.setLastName(jsonObj.getString("lastName"));
		}
		
		if(jsonObj.has("email")){
			user.setEmail(jsonObj.getString("email"));
		}
		
		if(jsonObj.has("email")){
			user.setUserName(jsonObj.getString("email"));
		}
		
		if(jsonObj.has("phoneNumber")){
			user.setMobilePhone(jsonObj.getString("phoneNumber"));
		}
		/*if(jsonObj.has("reportingManager") && !isNullOrEmpty(jsonObj.getString("reportingManager"))){
			User reportingManager = new User();
			reportingManager.setId(Long.parseLong(jsonObj.getString("reportingManager")));
			user.setReportingManager(reportingManager);
		}*/
		user.setUserState(Status.NOT_LOGGEDIN);
		
		if(jsonObj.has("roleType")){
			Roles roles;
			try {
				roles = rolesService.getRolesById(Long.parseLong(jsonObj.getString("roleType")));
				user.setUserType(roles.getDisplayName());
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		user.setUserCode(SourceLeadRandomCodeHelper.generateRandomUserCode());
		user.setCreatedBy(userCode);
		user.setCreatedDate(commonUtil.currentDate());
		return user;
	}
	
	private UserRole getUserRoleDetails(JSONObject jsonObj,User user,String userCode, Long orgLocationsId) throws NumberFormatException, JSONException {
		UserRole userRole = new UserRole();
		userRole.setUser(user);
		
		OrgLocations orgLocations = new OrgLocations();
		orgLocations.setId(orgLocationsId);
		userRole.setOrgLocations(orgLocations);
		
		if(jsonObj.has("reportingManager") && !isNullOrEmpty(jsonObj.getString("reportingManager"))){
			User reportingManager = new User();
			reportingManager.setId(Long.parseLong(jsonObj.getString("reportingManager")));
			userRole.setReportingManager(reportingManager);
		}
		
		
		Roles roles = null;
		try {
			if(jsonObj.has("roleType") && !isNullOrEmpty(jsonObj.getString("roleType"))){
				roles = rolesService.getRolesById(Long.parseLong(jsonObj.getString("roleType")));
			}
			else if(jsonObj.has("roleTypeId") && !isNullOrEmpty(jsonObj.getString("roleTypeId"))){
				roles = rolesService.getRolesById(Long.parseLong(jsonObj.getString("roleTypeId")));
			}else{
				roles = rolesService.getRolesById(2L);
			}
			
		} catch (Exception e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}
		
		userRole.setRoles(roles);
		userRole.setCreatedDate(commonUtil.currentDate());
		userRole.setCreatedBy(userCode);
		return userRole;
	}
	
	private Employees getEmployeeDetails(User user,JSONObject jsonObj, Long orgLocationsId,String userCode) throws JSONException {
		Employees employees = new Employees();
		employees.setUser(user);
		OrgLocations orgLocations = new OrgLocations();
		orgLocations.setId(orgLocationsId);
		employees.setOrgLocations(orgLocations);
		if(jsonObj.has("employeeId")){
			employees.setEmployeeId(jsonObj.getString("employeeId"));
		}
		if(jsonObj.has("rate")){
			employees.setRate(jsonObj.getString("rate"));
		}
		if(jsonObj.has("ratePer")){
			employees.setRatePer(jsonObj.getString("ratePer"));
		}
		if(jsonObj.has("dayHours")){
			employees.setDailyHours(jsonObj.getString("dayHours"));
		}
		if(jsonObj.has("invoiceFrequency")){
			employees.setInvoiceFrequency(jsonObj.getString("invoiceFrequency"));
		}
		
		if(jsonObj.has("dateofJoin") && !isNullOrEmpty(jsonObj.getString("dateofJoin"))){
			try {
				   SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				   employees.setHireDate(sdf.parse(jsonObj.getString("dateofJoin")));
				} catch (Exception e) {
				  e.printStackTrace();
				}
		}
		employees.setCreatedBy(userCode);
		employees.setCreatedDate(commonUtil.currentDate());
		return employees;
	}

	
	private ProjectMembers getProjectMembersDetails(Employees employee, JSONObject jsonObj, String userCode, Long orgLocationsId) throws Exception {
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
		project.setId(Long.parseLong(jsonObj.getString("projectId")));
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
	public void sendEmail(User user, User loginUser, String password) throws Exception {
		 EmailMessage emailMessage = new EmailMessage();
		 emailMessage.setFirstName(user.getFirstName());
		 emailMessage.setLastName(user.getLastName());
		 emailMessage.setToEmail(user.getEmail());
		 emailMessage.setFromEmail("peopleaxisindia@gmail.com");
		 emailMessage.setFromName(loginUser.getFirstName()+" "+loginUser.getLastName());
		 
		//setting the class filed values
		 Map<String, String> globalVarsMap = new HashMap<String, String>();
		 globalVarsMap.put("firstName", emailMessage.getFirstName());
		 globalVarsMap.put("lastName",emailMessage.getLastName());
		 globalVarsMap.put("administratorFirstName", loginUser.getFirstName());
		 globalVarsMap.put("administratorLastName",loginUser.getLastName());
		 globalVarsMap.put("role",user.getUserType());
		 globalVarsMap.put("loginURL","<a href='"+sourceleadUrl+"/"+"' style='text-decoration:none; '><div style=' width:220px; border-radius:30px; background-color:#214b90; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Login</div></a>");
		 globalVarsMap.put("userEmailId",user.getEmail());
		 if(null != user && user.getUserState().equals(Status.NOT_LOGGEDIN)){
			 globalVarsMap.put("password",password);
		 }else{
			 globalVarsMap.put("password","Note : You have already registered. Login with your registered password");
		 }
		 
		
		 //setting the Recipient information
		 ArrayList<EmailRecipient> emailRecipientList = new ArrayList<EmailRecipient>();
		 EmailRecipient emailRecipient = new EmailRecipient();
		 emailRecipient.setEmail(emailMessage.getToEmail());
		 emailRecipient.setName(emailMessage.getFirstName()+" "+emailMessage.getLastName());
		 emailRecipientList.add(emailRecipient);
		
		 //getting the current EmailScenario by EmailScenario unique name and entity type
		 EmailScenario  emailScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.INVITE_USER,Constants.ENTITY_TYPE_EMAIL);
		
		 //covert subject,body and signature
		 String subject = emailScenerioUtil.bulidSubject(emailScenario.getSubject(),globalVarsMap);
		 String body = emailScenerioUtil.buildTempBody(emailScenario.getBody(),globalVarsMap);
		 String signature = emailScenerioUtil.buildSignature(emailScenario.getSignature(),globalVarsMap);
		 String url = sourceleadUrl;
		 
		 //construct HTML template using required data
		 String emailTemplate = emailScenerioUtil.AssembleEmailMessage(emailScenario,emailMessage, subject, body, signature, url, new Org());
		 emailMessage.setSubject(subject);
		 emailMessage.setMessageBody(emailTemplate);
		 
		 //calling the sendEmail method of emailScenerioUtil
		 emailScenerioUtil.sendEmail(emailMessage,emailRecipientList,new ArrayList<EmailAttachment>());
		
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
	public List<User> getManagersByUserRolesAndOrgLocationId(String roleType , Long orgLocationsId) throws Exception {
		return userDao.getManagersByUserRolesAndOrgLocationId(roleType, orgLocationsId);
	}
	
	@Override
	public List<User> getManagersByUserIdAndUserRolesAndOrgLocationId(Long userId,String roleType , Long orgLocationsId) throws Exception {
		return userDao.getManagersByUserIdAndUserRolesAndOrgLocationId(userId, roleType, orgLocationsId);
	}
	
	@Override
	public List<Long> getAllUserIdsByReportingManagerId(Long reportingManagerId) throws Exception {
		return userDao.getAllUserIdsByReportingManagerId(reportingManagerId);
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
	public List<User> getAllUsersByOrgLocationCodeExceptTeamOwner(Long teamOwnerUserId, Long orgLocationsId)
			throws Exception {
		return userDao.getAllUsersByOrgLocationCodeExceptTeamOwner(teamOwnerUserId,orgLocationsId);
	}

	
	
	@Override
	public void sendEmailToClientOrVendor(User user, User loginUser, String password, String responderType,Org loginOrg,Org respondingPartyOrg,OrgLocations respondingPartyOrgLocation)	throws Exception {
		 
		String actionLink = null; 
		 EmailMessage emailMessage = new EmailMessage();
		 emailMessage.setFirstName(user.getFirstName());
		 emailMessage.setLastName(user.getLastName());
		 emailMessage.setToEmail(user.getEmail());
		 emailMessage.setFromEmail("peopleaxisindia@gmail.com");
		 emailMessage.setFromName(loginUser.getFirstName()+" "+loginUser.getLastName());
		 
		//setting the class filed values
		 Map<String, String> globalVarsMap = new HashMap<String, String>();
		 String UUID = SourceLeadRandomCodeHelper.generateUUID();
		 if("Client".equals(responderType)){
			 globalVarsMap.put("firstName", emailMessage.getFirstName());
			 globalVarsMap.put("lastName",emailMessage.getLastName());
			 globalVarsMap.put("vendorFirstName", loginUser.getFirstName());
			 globalVarsMap.put("vendorLastName",loginUser.getLastName());
			 globalVarsMap.put("clientFirstName", user.getFirstName());
			 globalVarsMap.put("clientlastName",user.getLastName());
			 globalVarsMap.put("clientOrgName",respondingPartyOrg.getOrgName());
			 globalVarsMap.put("vendorOrgName",loginOrg.getOrgName());
			 globalVarsMap.put("acceptURL","<a href='"+sourceleadUrl+"/"+"acceptClientOrVendorInvitation?respondingOrgType=VMS&respondingPartyOrgLocationId="+respondingPartyOrgLocation.getId()+"&UUID="+UUID+"' style='text-decoration:none; '><div style=' width:220px; border-radius:30px; background-color:#214b90; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Accept</div></a>");
			 
		     if(null != password){
			    globalVarsMap.put("userName",user.getEmail());
			    globalVarsMap.put("password",password);
			 }else if(null == password && user.getUserState().equals(Status.NOT_LOGGEDIN)){
				 password = SourceLeadRandomCodeHelper.generateRandomPassword();
				 user.setPassword(sourceleadPasswordEncoder.encode(password));
				 user.setIsMailSent(Status.Y);
				 userDao.addUser(user);
				 globalVarsMap.put("userName",user.getEmail());
				 globalVarsMap.put("password",password);
			 }else{
				 globalVarsMap.put("userName",user.getEmail());
				 globalVarsMap.put("password","Note : You have already registered with this org. Login with your registered password");
			 } 
			 
			 actionLink = ""+sourceleadUrl+"/"+"acceptClientOrVendorInvitation?actionLink=acceptNotification&respondingOrgType=VMS&respondingPartyOrgLocationId="+respondingPartyOrgLocation.getId()+"&UUID="+UUID+" ";
			 
			 
		 }else if("Vendor".equals(responderType)){
			 globalVarsMap.put("firstName", emailMessage.getFirstName());
			 globalVarsMap.put("lastName",emailMessage.getLastName());
			 globalVarsMap.put("vendorFirstName", user.getFirstName());
			 globalVarsMap.put("vendorLastName",user.getLastName());
			 globalVarsMap.put("clientFirstName", loginUser.getFirstName());
			 globalVarsMap.put("clientLastName",loginUser.getLastName());
			 globalVarsMap.put("vendorOrgName",respondingPartyOrg.getOrgName());
			 globalVarsMap.put("clientOrgName",loginOrg.getOrgName());
			 globalVarsMap.put("acceptURL","<a href='"+sourceleadUrl+"/"+"acceptClientOrVendorInvitation?actionLink=acceptNotification&respondingOrgType=ATS&respondingPartyOrgLocationId="+respondingPartyOrgLocation.getId()+"&UUID="+UUID+"' style='text-decoration:none; '><div style=' width:220px; border-radius:30px; background-color:#214b90; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Accept</div></a>");
			
			 if(null != password){
			    globalVarsMap.put("userName",user.getEmail());
			    globalVarsMap.put("password",password);
			 }else if(null == password && user.getUserState().equals(Status.NOT_LOGGEDIN)){
				 password = SourceLeadRandomCodeHelper.generateRandomPassword();
				 user.setPassword(sourceleadPasswordEncoder.encode(password));
				 user.setIsMailSent(Status.Y);
				 userDao.addUser(user);
				 globalVarsMap.put("userName",user.getEmail());
				 globalVarsMap.put("password",password);
			 }else{
				 globalVarsMap.put("userName",user.getEmail());
				 globalVarsMap.put("password","Note : You have already registered with this org. Login with your registered password");
			 }
			 
			 
			 actionLink = ""+sourceleadUrl+"/"+"acceptClientOrVendorInvitation?actionLink=acceptNotification&respondingOrgType=ATS&respondingPartyOrgLocationId="+respondingPartyOrgLocation.getId()+"&UUID="+UUID+"";
			 
		 }
		 
		 //setting the Recipient information
		 ArrayList<EmailRecipient> emailRecipientList = new ArrayList<EmailRecipient>();
		 EmailRecipient emailRecipient = new EmailRecipient();
		 emailRecipient.setEmail(emailMessage.getToEmail());
		 emailRecipient.setName(emailMessage.getFirstName()+" "+emailMessage.getLastName());
		 emailRecipientList.add(emailRecipient);
		
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
		 String emailTemplate = emailScenerioUtil.AssembleEmailMessage(emailScenario,emailMessage, subject, body, signature, url, new Org());
		 emailMessage.setSubject(subject);
		 emailMessage.setMessageBody(emailTemplate);
		 
		 
		 //calling the sendEmail method of emailScenerioUtil
		 emailScenerioUtil.sendEmail(emailMessage,emailRecipientList,new ArrayList<EmailAttachment>());
		 String notificationbody = emailScenerioUtil.buildTempBody(noificationEmailScenario.getBody(),globalVarsMap);
		 noificationEmailScenario.setBody(notificationbody);
		 noificationEmailScenario.setUUID(UUID);
		 
		 emailScenerioUtil.sendNotification(actionLink, loginUser, user, respondingPartyOrgLocation, noificationEmailScenario);
	}

	@Override
	public List<User> getUsersByTeamType(Long id, String teamType) throws Exception {
		return userDao.getUsersByTeamType(id,teamType);
	}


	@Override
	public List<User> getUsersByTeamTypeAndNoCurrentUser(Long currentApproverId) throws Exception {
		return userDao.getUsersByTeamTypeAndNoCurrentUser(currentApproverId);
	}
	
	
	
}
