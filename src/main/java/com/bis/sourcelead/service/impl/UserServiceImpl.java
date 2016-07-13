package com.bis.sourcelead.service.impl;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.bis.sourcelead.SourceleadPasswordEncoder;
import com.bis.sourcelead.dao.UserDao;
import com.bis.sourcelead.entity.Employees;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.ProjectMembers;
import com.bis.sourcelead.entity.Projects;
import com.bis.sourcelead.entity.Roles;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserRole;
import com.bis.sourcelead.enums.Status;
import com.bis.sourcelead.form.UserProfile;
import com.bis.sourcelead.service.EmployeesService;
import com.bis.sourcelead.service.ProjectMembersService;
import com.bis.sourcelead.service.RolesService;
import com.bis.sourcelead.service.UserRoleService;
import com.bis.sourcelead.service.UserService;
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
	public void createUser(JSONObject jsonObj, String userCode, Long orgLocationsId) throws JSONException {
		logger.debug("In UserService class createUser method...");
		//For Saving of User Details
		User user = getUserDetails(jsonObj,userCode);
		String password = SourceLeadRandomCodeHelper.generateRandomVerificationCode();
		user.setPassword(sourceleadPasswordEncoder.encode(password));
		try {
			userDao.addUser(user);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		//For saving the User Role 
		UserRole userRole = getUserRoleDetails(user,userCode,orgLocationsId);
		try {
			userRoleService.addUserRole(userRole);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	   //For saving Employee details (Every User is an Employee)
		Employees employee = getEmployeeDetails(user,jsonObj,orgLocationsId,userCode);
		try {
			employeesService.addEmployee(employee);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		//For saving Employee details (Every User is an Employee)
		ProjectMembers pm = getProjectMembersDetails(employee,jsonObj,userCode);
		try {
			projectMembersService.saveProjectMember(pm);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private User getUserDetails(JSONObject jsonObj,String userCode) throws JSONException {
		User user = new User();
		user.setFirstName(jsonObj.getString("FirstName"));
		user.setLastName(jsonObj.getString("LastName"));
		user.setEmail(jsonObj.getString("EmailId"));
		user.setMobilePhone(jsonObj.getString("PhoneNumber"));
		user.setUserName(jsonObj.getString("EmailId"));
		user.setUserState(Status.ACTIVE);
		user.setUserType(jsonObj.getString("role"));
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
				roleName = "ROLE_ADMIN";
			}else if(user != null && user.getUserType() != null && "Approver".equals(user.getUserType())){
				roleName = "ROLE_APPROVER";
			}else if(user != null && user.getUserType() != null && "Employee".equals(user.getUserType())){
				roleName = "ROLE_USER";
			}
			roles = rolesService.getRolesByRoleName(roleName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		userRole.setRoles(roles);
		userRole.setIsPrimary(0);
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
		employees.setEmployeeId(jsonObj.getString("EmployeeId"));
		employees.setRate(jsonObj.getString("Rate"));
		employees.setRatePer(jsonObj.getString("RatePer"));
		employees.setDailyHours(jsonObj.getString("DayHours"));
		
		employees.setInvoiceFrequency(jsonObj.getString("InvoiceFrequency"));
		if(jsonObj.has("DateofJoin") && !isNullOrEmpty(jsonObj.getString("DateofJoin"))){
			try {
				   SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				   employees.setHireDate(sdf.parse(jsonObj.getString("DateofJoin")));
				} catch (Exception e) {
				  e.printStackTrace();
				}
		}
		employees.setCreatedBy(userCode);
		employees.setCreatedDate(new Date());
		return employees;
	}

	
	private ProjectMembers getProjectMembersDetails(Employees employee, JSONObject jsonObj, String userCode) throws NumberFormatException, JSONException {
		ProjectMembers pm = new ProjectMembers();
		
		if(jsonObj.has("projectId") && !isNullOrEmpty(jsonObj.getString("projectId"))){
			Projects project = new Projects();
			project.setId(Long.parseLong(jsonObj.getString("projectId")));
			pm.setProjects(project);
		}else{
			//need to add default project
		}
		
		pm.setEmployee(employee);
		pm.setProjectAssignedDate(new Date());
		pm.setCreatedBy(userCode);
		pm.setCreatedDate(new Date());
		pm.setUpdatedDate(new Date());
		return pm;
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
	public User saveUserProfile(UserProfile userProfile, MultipartHttpServletRequest request,User user) throws Exception {
		
		user = getUserProfileDetails(userProfile,request,user);
		userDao.addUser(user);
		return user;
	}
	
	
	private User getUserProfileDetails(UserProfile userProfile, MultipartHttpServletRequest request,User user){
		user.setFirstName(userProfile.getFirstName());
		user.setLastName(userProfile.getLastName());
		user.setMobilePhone(userProfile.getMobilePhone());
		user.setJobTitle(userProfile.getJobTitle());
		user.setLocation(userProfile.getLocation());
		Iterator<String> itrator = request.getFileNames();
        MultipartFile multiFile = request.getFile(itrator.next());
        if(null != multiFile.getContentType()){
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
	public Collection<User> getUsersByUserRoles() throws Exception {
		// TODO Auto-generated method stub
		return userDao.getUsersByUserRoles();
	}
	
	protected static boolean isNullOrEmpty(String str) {
		return (str == null || "".equals(str.trim()));
	}

}
