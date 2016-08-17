package com.bis.sourcelead.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;

import java.util.HashSet;
import java.util.Iterator;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
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
		UserRole userRole = getUserRoleDetails(user,loginUser.getUserCode(),orgLocationsId);
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
		user.setFirstName(jsonObj.getString("firstName"));
		user.setLastName(jsonObj.getString("lastName"));
		user.setEmail(jsonObj.getString("email"));
		user.setMobilePhone(jsonObj.getString("phoneNumber"));
		user.setUserName(jsonObj.getString("email"));
		user.setUserState(Status.NOT_LOGGEDIN);
		if((jsonObj.has("isMailSent") && !isNullOrEmpty(jsonObj.getString("isMailSent"))) && ("Y".equals(jsonObj.getString("isMailSent")))){
			user.setIsMailSent(Status.Y);
		}else{
			user.setIsMailSent(Status.N);
		}
		
		user.setUserType(jsonObj.getString("roleType"));
		user.setUserCode(SourceLeadRandomCodeHelper.generateRandomUserCode());
		user.setCreatedBy(userCode);
		user.setCreatedDate(new Date());
		return user;
	}
	
	private UserRole getUserRoleDetails(User user,String userCode, Long orgLocationsId) {
		UserRole userRole = new UserRole();
		userRole.setUser(user);
		
		OrgLocations orgLocations = new OrgLocations();
		orgLocations.setId(orgLocationsId);
		userRole.setOrgLocations(orgLocations);
		
		Roles roles = null;
		String roleName = "";
		try {
			if(user != null && user.getUserType() != null && "Admin".equals(user.getUserType())){
				roleName = Constants.ROLE_ADMIN;
			}else if(user != null && user.getUserType() != null && "Approver".equals(user.getUserType())){
				roleName = Constants.ROLE_APPROVER;
			}else if(user != null && user.getUserType() != null && "Employee".equals(user.getUserType())){
				roleName = Constants.ROLE_USER;
			}
			roles = rolesService.getRolesByRoleName(roleName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		userRole.setRoles(roles);
		userRole.setIsPrimary(1);
		userRole.setCreatedDate(new Date());
		userRole.setCreatedBy(userCode);
		return userRole;
	}
	
	private Employees getEmployeeDetails(User user,JSONObject jsonObj, Long orgLocationsId,String userCode) throws JSONException {
		Employees employees = new Employees();
		employees.setUser(user);
		OrgLocations orgLocations = new OrgLocations();
		orgLocations.setId(orgLocationsId);
		employees.setOrgLocations(orgLocations);
		employees.setEmployeeId(jsonObj.getString("employeeId"));
		employees.setRate(jsonObj.getString("rate"));
		employees.setRatePer(jsonObj.getString("ratePer"));
		employees.setDailyHours(jsonObj.getString("dayHours"));
		
		employees.setInvoiceFrequency(jsonObj.getString("invoiceFrequency"));
		if(jsonObj.has("dateofJoin") && !isNullOrEmpty(jsonObj.getString("dateofJoin"))){
			try {
				   SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				   employees.setHireDate(sdf.parse(jsonObj.getString("dateofJoin")));
				} catch (Exception e) {
				  e.printStackTrace();
				}
		}
		employees.setCreatedBy(userCode);
		employees.setCreatedDate(new Date());
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
		pmh.setProjectAssignedDate(new Date());
        pmh.setCreatedDate(new Date());
		pmh.setCreatedBy(userCode);
		pmh.setUpdatedDate(new Date());
		pmh.setUpdatedBy(userCode);
		pmhList.add(pmh);
		
		pm.setCreatedBy(userCode);
		pm.setCreatedDate(new Date());
		pm.setUpdatedDate(new Date());
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
		 globalVarsMap.put("password",password);
		
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
	public User getUserById(Long Id) throws Exception {
		logger.debug("In UserService class...");
		return userDao.getUserById(Id);
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

}
