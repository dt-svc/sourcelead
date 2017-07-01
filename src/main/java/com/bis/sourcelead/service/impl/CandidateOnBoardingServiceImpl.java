package com.bis.sourcelead.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.internet.InternetAddress;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.SourceleadPasswordEncoder;
import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.CandidateOnBoardingDao;
import com.bis.sourcelead.dao.EmployeesDao;
import com.bis.sourcelead.dao.OnboardTasksDao;
import com.bis.sourcelead.dao.RolesDao;
import com.bis.sourcelead.dao.TaskDao;
import com.bis.sourcelead.dao.TeamMemberDao;
import com.bis.sourcelead.dao.UserDao;
import com.bis.sourcelead.dao.UserRoleDao;
import com.bis.sourcelead.elk.index.UserIndex;
import com.bis.sourcelead.elk.index.helper.UserIndexHelper;
import com.bis.sourcelead.elk.setup.SourceleadELKBaseIndexer;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.CandidateOnBoarding;
import com.bis.sourcelead.entity.EmailAttachment;
import com.bis.sourcelead.entity.EmailMessage;
import com.bis.sourcelead.entity.EmailScenario;
import com.bis.sourcelead.entity.Employees;
import com.bis.sourcelead.entity.OnboardTasks;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.RequirementOffers;
import com.bis.sourcelead.entity.Roles;
import com.bis.sourcelead.entity.Tasks;
import com.bis.sourcelead.entity.Team;
import com.bis.sourcelead.entity.TeamMembers;
import com.bis.sourcelead.entity.Timezone;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserRole;
import com.bis.sourcelead.enums.Status;
import com.bis.sourcelead.service.CandidateOnBoardingService;
import com.bis.sourcelead.service.CandidateService;
import com.bis.sourcelead.service.EmailScenarioService;
import com.bis.sourcelead.service.TimeZoneService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.EmailScenerioUtil;
import com.bis.sourcelead.util.SourceLeadRandomCodeHelper;
import com.bis.sourcelead.web.LoginSessionController;

@Service
public class CandidateOnBoardingServiceImpl implements CandidateOnBoardingService{
	
	@Autowired
	CandidateOnBoardingDao candidateOnBoardingDao;
	
	@Autowired
	CommonUtil commonUtil;
	
	@Autowired
	private SourceleadPasswordEncoder sourceleadPasswordEncoder;
	
	@Autowired
	UserDao userDao;
	
	@Autowired
	UserRoleDao userRoleDao;
	
	@Autowired
	EmployeesDao employeesDao;
	
	@Autowired
	RolesDao rolesDao;
	
	@Autowired
	TeamMemberDao teamMemberDao;
	
	@Value("${sourceleadUrl}")
    private String sourceleadUrl;
		
	@Autowired
	EmailScenarioService emailScenarioService;
	
	@Autowired
	EmailScenerioUtil emailScenerioUtil;
	
	@Autowired
	UserIndexHelper userIndexHelper;
	
	@Autowired
    private SourceleadELKBaseIndexer<UserIndex> sourceleadELKBaseIndexer;
	
	@Autowired
	OnboardTasksDao onboardTasksDao;
	
	@Autowired
	TaskDao taskDao;
	
	@Autowired
	TimeZoneService timeZoneService;
	
	@Autowired
	CandidateService candidateService;
	
	protected static boolean isNullOrEmpty(String str) {
		return (str == null || "".equals(str.trim()));
	}

	@Override
	public CandidateOnBoarding saveCandidateOnBoarding(CandidateOnBoarding candidateOnBoarding) throws Exception {
		return candidateOnBoardingDao.saveCandidateOnBoarding(candidateOnBoarding);
	}

	@Override
	public CandidateOnBoarding saveOnBoardingCandidatesDetails(JSONObject jsonObj, OrgLocations orgLocations, User user)
			throws Exception {
		
		CandidateOnBoarding candidateOnBoarding = null;
		
		//date format code
		SimpleDateFormat dateFormat = orgLocations.getLocFullFormDateFormatter();
		
		if(jsonObj.has("savedonboardId") && !isNullOrEmpty(jsonObj.getString("savedonboardId"))){
			candidateOnBoarding = candidateOnBoardingDao.getCandidateOnBoardingById(Long.valueOf(jsonObj.getString("savedonboardId")));
		}else{
			candidateOnBoarding = new CandidateOnBoarding();
			candidateOnBoarding.setOnboardCandidateUUID(SourceLeadRandomCodeHelper.generateRandomUUID());
		}
		
		String[] onboardCandiate = jsonObj.getString("onboardCandiate").split(",");
		
		Candidate candidate = new Candidate();
		candidate.setId(Long.valueOf(onboardCandiate[0]));
		
		RequirementOffers offer = new RequirementOffers();
		offer.setId(Long.valueOf(onboardCandiate[1]));
		
		candidateOnBoarding.setCandidateId(candidate);
		candidateOnBoarding.setOfferId(offer);
		candidateOnBoarding.setFirstName(jsonObj.getString("firstName"));
		
		if(jsonObj.has("lastName") && !isNullOrEmpty(jsonObj.getString("lastName"))){
			candidateOnBoarding.setLastName(jsonObj.getString("lastName"));
		}else{
			candidateOnBoarding.setLastName(null);
		}
		
		if(jsonObj.has("address1") && !isNullOrEmpty(jsonObj.getString("address1"))){
			candidateOnBoarding.setAddress1(jsonObj.getString("address1"));
		}
		
		if(jsonObj.has("address2") && !isNullOrEmpty(jsonObj.getString("address2"))){
			candidateOnBoarding.setAddress2(jsonObj.getString("address2"));
		}else{
			candidateOnBoarding.setAddress2(null);
		}
		
		if(jsonObj.has("location") && !isNullOrEmpty(jsonObj.getString("location"))){
			candidateOnBoarding.setLocation(jsonObj.getString("location"));
		}
		
		if(jsonObj.has("country") && !isNullOrEmpty(jsonObj.getString("country"))){
			candidateOnBoarding.setCountry(jsonObj.getString("country"));
		}
		
		if(jsonObj.has("state") && !isNullOrEmpty(jsonObj.getString("state"))){
			candidateOnBoarding.setState(jsonObj.getString("state"));
		}else{
			candidateOnBoarding.setState(null);
		}
		
		if(jsonObj.has("city") && !isNullOrEmpty(jsonObj.getString("city"))){
			candidateOnBoarding.setCity(jsonObj.getString("city"));
		}else{
			candidateOnBoarding.setCity(null);
		}
		
		if(jsonObj.has("zipCode") && !isNullOrEmpty(jsonObj.getString("zipCode"))){
			candidateOnBoarding.setZipCode(jsonObj.getString("zipCode"));
		}else{
			candidateOnBoarding.setZipCode(null);
		}
		
		if(jsonObj.has("mobileNumber") && !isNullOrEmpty(jsonObj.getString("mobileNumber"))){
			candidateOnBoarding.setMobileNumber(jsonObj.getString("mobileNumber"));
		}
		
		if(jsonObj.has("personalEmail") && !isNullOrEmpty(jsonObj.getString("personalEmail"))){
			candidateOnBoarding.setPersonalEmail(jsonObj.getString("personalEmail"));
		}
		
		if(jsonObj.has("companyEmail") && !isNullOrEmpty(jsonObj.getString("companyEmail"))){
			candidateOnBoarding.setCompanyEmail(jsonObj.getString("companyEmail"));
		}else{
			candidateOnBoarding.setCompanyEmail(null);
		}
		
		
		if(jsonObj.has("onboardCandidateRole") && !isNullOrEmpty(jsonObj.getString("onboardCandidateRole"))){
			Roles roles = new Roles();
			roles.setId(Long.valueOf(jsonObj.getString("onboardCandidateRole")));
			candidateOnBoarding.setRoleId(roles);
		}
		
		if(jsonObj.has("startDate") && !isNullOrEmpty(jsonObj.getString("startDate"))){
			candidateOnBoarding.setStartDate(dateFormat.parse(jsonObj.getString("startDate")));
		}
		
		if(jsonObj.has("endDate") && !isNullOrEmpty(jsonObj.getString("endDate"))){
			candidateOnBoarding.setEndDate(dateFormat.parse(jsonObj.getString("endDate")));
		}else{
			candidateOnBoarding.setEndDate(null);
		}
		
		if(jsonObj.has("salaryRate") && !isNullOrEmpty(jsonObj.getString("salaryRate"))){
			candidateOnBoarding.setSalaryRate(jsonObj.getString("salaryRate"));
		}
		
		if(jsonObj.has("reportingManager") && !isNullOrEmpty(jsonObj.getString("reportingManager"))){
			User reportingManager = new User();
			reportingManager.setId(Long.valueOf(jsonObj.getString("reportingManager")));
			candidateOnBoarding.setManagerUserId(reportingManager);
		}else{
			candidateOnBoarding.setManagerUserId(null);
		}
		
		if(jsonObj.has("team") && !isNullOrEmpty(jsonObj.getString("team"))){
			Team team = new Team();
			team.setId(Long.valueOf(jsonObj.getString("team")));
			candidateOnBoarding.setTeamId(team);
		}else{
			candidateOnBoarding.setTeamId(null);
		}
		
		if(jsonObj.has("employmentType") && !isNullOrEmpty(jsonObj.getString("employmentType"))){
			candidateOnBoarding.setEmploymentType(jsonObj.getString("employmentType"));
		}else{
			candidateOnBoarding.setEmploymentType(null);
			candidateOnBoarding.setEmploymentOtherType(null);
		}
		
		if(jsonObj.has("employmentOtherType") && !isNullOrEmpty(jsonObj.getString("employmentOtherType"))){
			
			if(jsonObj.has("employmentType") && !isNullOrEmpty(jsonObj.getString("employmentType")) && jsonObj.getString("employmentType").equalsIgnoreCase("Other")){
				candidateOnBoarding.setEmploymentOtherType(jsonObj.getString("employmentOtherType"));
			}else{
				candidateOnBoarding.setEmploymentOtherType(null);
			}
			
		}
		
		
		if(jsonObj.has("probationNotice") && !isNullOrEmpty(jsonObj.getString("probationNotice"))){
			candidateOnBoarding.setProbationNotice(jsonObj.getString("probationNotice"));
		}else{
			candidateOnBoarding.setProbationNotice(null);
		}
		
		if(jsonObj.has("terminationNotice") && !isNullOrEmpty(jsonObj.getString("terminationNotice"))){
			candidateOnBoarding.setTerminationNotice(jsonObj.getString("terminationNotice"));
		}else{
			candidateOnBoarding.setTerminationNotice(null);
		}
		
		if(jsonObj.has("dueDate") && !isNullOrEmpty(jsonObj.getString("dueDate"))){
			candidateOnBoarding.setDueDate(dateFormat.parse(jsonObj.getString("dueDate")));
		}else{
			candidateOnBoarding.setDueDate(null);
		}
		
		
		if(jsonObj.has("specialConditions") && !isNullOrEmpty(jsonObj.getString("specialConditions"))){
			candidateOnBoarding.setSpecialConditions(jsonObj.getString("specialConditions"));
		}else{
			candidateOnBoarding.setSpecialConditions(null);
		}
		
		if(jsonObj.has("onboardingTemplate") && !isNullOrEmpty(jsonObj.getString("onboardingTemplate"))){
			
			String templateIds = jsonObj.getString("onboardingTemplate");
			templateIds = templateIds.replaceAll("\\[", "").replaceAll("\\]","");
			templateIds = templateIds.replaceAll("\"", "");
			candidateOnBoarding.setOnboardTemplateId(templateIds);
		}else{
			candidateOnBoarding.setOnboardTemplateId(null);
		}
		
		candidateOnBoarding.setOrgLocationId(orgLocations);
		candidateOnBoarding.setStatus(Constants.CANDIDATE_ACTIVE);
		candidateOnBoarding.setCandidateOnBoardStatus("Draft");
		candidateOnBoarding.setCreatedDate(CommonUtil.currentDate());
		candidateOnBoarding.setCreatedBy(user.getUserCode());
		candidateOnBoarding.setUpdatedDate(CommonUtil.currentDate());
		candidateOnBoarding.setUpdatedBy(user.getUserCode());
		
			
		candidateOnBoardingDao.saveCandidateOnBoarding(candidateOnBoarding);
		
		return candidateOnBoarding;
	}

	@Override
	public List<CandidateOnBoarding> getCandidateOnBoardingListByOrgLocationId(Long orgLocationId) throws Exception {
		return candidateOnBoardingDao.getCandidateOnBoardingListByOrgLocationId(orgLocationId);
	}

	@Override
	public CandidateOnBoarding getCandidateOnBoardingByUUID(String onboardCandidateUUID) throws Exception {
		return candidateOnBoardingDao.getCandidateOnBoardingByUUID(onboardCandidateUUID);
	}

	@Override
	public Integer getCandidateOnBoardingListCountByOrgLocationId(Long orgLocationId) throws Exception {
		return candidateOnBoardingDao.getCandidateOnBoardingListCountByOrgLocationId(orgLocationId);
	}

	@Override
	public CandidateOnBoarding getCandidateOnBoardingById(Long Id) throws Exception {
		return candidateOnBoardingDao.getCandidateOnBoardingById(Id);
	}

	@Override
	public void createOnboardingUser(CandidateOnBoarding candidateOnBoarding,User loginUser,OrgLocations orgLocations) throws Exception {
		
		
		User onboardingUser = null;
		UserRole onboardingUserRole = null;
		Employees onboardingEmployee = null;
		TeamMembers onboardingTeamMember = null;
		
		String password = SourceLeadRandomCodeHelper.generateRandomPassword();
		
		if(!isNullOrEmpty(candidateOnBoarding.getCompanyEmail())){
			onboardingUser = userDao.findByUserName(candidateOnBoarding.getCompanyEmail());
		}else{
			onboardingUser = userDao.findByUserName(candidateOnBoarding.getPersonalEmail());
		}
		
		if(onboardingUser == null){
			onboardingUser = getUserDetails(candidateOnBoarding,password,loginUser);
		    userDao.addUser(onboardingUser);
		}
		
		onboardingUserRole = userRoleDao.getUserDetailsByUserIdAndLocationId(onboardingUser.getId(), orgLocations.getId());
	    
		if(onboardingUserRole == null){
			onboardingUserRole = saveUserRoles(candidateOnBoarding,onboardingUser,loginUser,orgLocations);
		    userRoleDao.addUserRole(onboardingUserRole);
		}
		
		Candidate candidate = candidateService.getCandidateByCandidateId(candidateOnBoarding.getCandidateId().getId());
		if(null != candidate){
			candidate.setConsultantUser(onboardingUser);
			candidateService.addCandidate(candidate);
		}
	    
	    onboardingEmployee = employeesDao.getEmployeeByUserIdAndOrgLocationsId(onboardingUser.getId(), orgLocations.getId());
	    
	    if(onboardingEmployee == null){
	    	onboardingEmployee = saveOnboardingEmployee(candidateOnBoarding,onboardingUser,loginUser,orgLocations);
	 	    employeesDao.addEmployee(onboardingEmployee);
	    }
	    
	    if(null != candidateOnBoarding.getTeamId()){
	    	onboardingTeamMember = teamMemberDao.getTeamMemberByMemberUserIdAndLocationId(onboardingUser.getId(), orgLocations.getId());
	    	if(onboardingTeamMember == null){
	    		onboardingTeamMember = saveOnboardingTeamMember(candidateOnBoarding,onboardingUser,loginUser);
		    	teamMemberDao.saveOrUpdateTeamMembers(onboardingTeamMember);
	    	}
	    }
	    
	    sendEmailToOnbardingUser(onboardingUser,loginUser,password,orgLocations.getId());
	    saveOrUpdateUserElkDocument(onboardingUserRole,onboardingEmployee,loginUser.getUserCode());
	    
	    List<OnboardTasks> onboardTasksList = onboardTasksDao.getOnBoardTasksListByCandidateOnBoardingId(candidateOnBoarding.getId());
		 if(null != onboardTasksList && onboardTasksList.size() > 0){
			 
			 for(OnboardTasks onboardTasks : onboardTasksList){
				 if(!isNullOrEmpty(onboardTasks.getAssignToUsers())){
					 String[] userIds = onboardTasks.getAssignToUsers().split(",");
					 for(int i=0; i<userIds.length; i++){
						 
						 Tasks tasks = null;
						 User taskUser = userDao.getUserById(Long.valueOf(userIds[i]));
						 tasks = getTaskDetails(onboardTasks, taskUser, loginUser, orgLocations);
						 tasks.setOnboardingId(candidateOnBoarding);
						 taskDao.saveTasks(tasks);
						 mailToUserTask(tasks,orgLocations);
					 }
				 }else{
					 Tasks tasks = null;
					 tasks = getTaskDetails(onboardTasks, onboardingUser, loginUser, orgLocations);
					 tasks.setOnboardingId(candidateOnBoarding);
					 taskDao.saveTasks(tasks);
					 mailToUserTask(tasks,orgLocations);
				 }
			 }
			 
		 }
		 
		 candidateOnBoarding.setCandidateOnBoardStatus("Finished");
		 candidateOnBoarding.setOnboardingUser(onboardingUser);
		 candidateOnBoarding.setOnboardSubmitted(Constants.NO);
		 candidateOnBoarding.setRoleId(onboardingUserRole.getRoles());
		 if(null != onboardingTeamMember){
			 candidateOnBoarding.setTeamId(onboardingTeamMember.getTeamId());
		 }
		 
		 candidateOnBoardingDao.saveCandidateOnBoarding(candidateOnBoarding);
		
	}
	
	public Tasks getTaskDetails(OnboardTasks onboardTasks,User user,User loginUser,OrgLocations orgLocations){
		 
		 Tasks tasks = new Tasks();
		 tasks.setOwnerOrglocationId(orgLocations);
		 tasks.setAssignedOrglocationId(orgLocations);
		 tasks.setTaskOwnerId(loginUser);
		 tasks.setAssignedUserId(user);
		 if(!isNullOrEmpty(onboardTasks.getPriority())){
			 tasks.setPriority(onboardTasks.getPriority());
		 }
		 tasks.setSubject(onboardTasks.getTaskName());
		 
		 if(!isNullOrEmpty(onboardTasks.getTaskRepeat())){
			 tasks.setTaskRepeat(onboardTasks.getTaskRepeat());
		 }
		 
		 if(null != onboardTasks.getDueAfterMonths() && null == onboardTasks.getDueAfterDays()){
			 Calendar cal = Calendar.getInstance(); 
			 cal.add(Calendar.MONTH, onboardTasks.getDueAfterMonths());
			 tasks.setTaskDate(cal.getTime());
		 }
		 
		 if(null != onboardTasks.getDueAfterDays() && null == onboardTasks.getDueAfterMonths()){
			 Calendar cal = Calendar.getInstance(); 
			 cal.add(Calendar.DATE, onboardTasks.getDueAfterDays());
			 tasks.setTaskDate(cal.getTime());
		 }
		 
		 if(null != onboardTasks.getDueAfterMonths() && null != onboardTasks.getDueAfterDays()){
			 Calendar cal = Calendar.getInstance(); 
			 cal.add(Calendar.MONTH, onboardTasks.getDueAfterMonths());
			 Date date = cal.getTime();
			 Date addedDate = addDays(date, onboardTasks.getDueAfterDays());
			 tasks.setTaskDate(addedDate);
		 }
		 
		 if(!isNullOrEmpty(onboardTasks.getTaskEmail()) && isNullOrEmpty(onboardTasks.getTaskNotification())){
			 tasks.setAlertType("Email");
		 }
		 
		 if(isNullOrEmpty(onboardTasks.getTaskEmail()) && !isNullOrEmpty(onboardTasks.getTaskNotification())){
			 tasks.setAlertType("Notification");
		 }
		 
		 if(!isNullOrEmpty(onboardTasks.getTaskEmail()) && !isNullOrEmpty(onboardTasks.getTaskNotification())){
			 tasks.setAlertType("Email,Notification");
		 }
		 
		 tasks.setTaskTime("23:59:00");
		 tasks.setDescription(onboardTasks.getDescription());
		 tasks.setTaskStatus(Constants.TASK_STATUS_OPEN);
		 tasks.setCreatedBy(loginUser.getUserCode());
		 tasks.setCreatedDate(commonUtil.currentDate());
		 tasks.setUpdatedBy(loginUser.getUserCode());
		 tasks.setUpdatedDate(commonUtil.currentDate());
		 return tasks;
		
		 
	}
	
	public static Date addDays(Date d, int days)
    {
        d.setTime(d.getTime() + days * 1000 * 60 * 60 * 24);
        return d;
    }
	
	
	public void saveOrUpdateUserElkDocument(UserRole userRole, Employees employee, String loginUserCode) throws Exception {
		//Elastic Search Document Saving
		UserIndex userIndex = userIndexHelper.getUserIndexFromUser(userRole,employee,loginUserCode);
		sourceleadELKBaseIndexer.saveOrUpdateElkDocument(userIndex, Constants.ELK_USER_INDEX, Constants.ELK_USER_TYPE,userRole.getId().toString());
		
	}

	
	private User getUserDetails(CandidateOnBoarding candidateOnBoarding,String password,User loginUser){
		
		User user = new User();
		
		user.setFirstName(candidateOnBoarding.getFirstName());
		
		if(!isNullOrEmpty(candidateOnBoarding.getLastName())){
			user.setLastName(candidateOnBoarding.getLastName());
		}
	
		if(!isNullOrEmpty(candidateOnBoarding.getCompanyEmail())){
			user.setEmail(candidateOnBoarding.getCompanyEmail());
			user.setUserName(candidateOnBoarding.getCompanyEmail());
		}else{
			user.setEmail(candidateOnBoarding.getPersonalEmail());
			user.setUserName(candidateOnBoarding.getPersonalEmail());
		}
		
	    if(!isNullOrEmpty(candidateOnBoarding.getMobileNumber())){
    	 user.setMobilePhone(candidateOnBoarding.getMobileNumber());
        }
	    
	    user.setIsMailSent(Status.Y);
	    user.setLocation(candidateOnBoarding.getAddress1());
	   
	    
	    user.setPassword(sourceleadPasswordEncoder.encode(password));
		
		user.setUserCode(SourceLeadRandomCodeHelper.generateRandomUserCode());
		user.setCreatedBy(loginUser.getUserCode());
		user.setCreatedDate(commonUtil.currentDate());
		return user;
	}
	
	private UserRole saveUserRoles(CandidateOnBoarding candidateOnBoarding,User onboardingUser,User loginUser,OrgLocations orgLocations){
		
		UserRole userRole = new UserRole();
		   
	    userRole.setUser(onboardingUser);
	    
	    Roles roles;
		try {
			roles = rolesDao.getRolesById(candidateOnBoarding.getRoleId().getId());
			userRole.setRoles(roles);
			userRole.setUserType(roles.getDisplayName());
		} catch (Exception e) {
			e.printStackTrace();
		}
	   
	    if(null != candidateOnBoarding.getManagerUserId()){
	    	User reportingManager = new User();
	    	reportingManager.setId(candidateOnBoarding.getManagerUserId().getId());
	    	userRole.setReportingManager(reportingManager);
	    }
	    userRole.setOrgLocations(orgLocations);
	    
	    try {
			boolean isUserActive = userRoleDao.isUserActive(onboardingUser.getId());
			 if(isUserActive == false){
				  userRole.setIsPrimary(1);
			  }else{
				  userRole.setIsPrimary(0);
			  }
		} catch (Exception e) {
			e.printStackTrace();
		}
	   
	    userRole.setUserState(Status.NOT_LOGGEDIN);
	    userRole.setCreatedBy(loginUser.getUserCode());
	    userRole.setCreatedDate(commonUtil.currentDate());
	    userRole.setUpdatedBy(loginUser.getUserCode());
	    userRole.setUpdatedDate(commonUtil.currentDate());
	   
	    return userRole;
	}
	
	private Employees saveOnboardingEmployee(CandidateOnBoarding candidateOnBoarding,User onboardingUser,User loginUser,OrgLocations orgLocations){
		    
		    Employees employees = new Employees();
		    employees.setUser(onboardingUser);
		    employees.setOrgLocations(orgLocations);
		    employees.setCountry(candidateOnBoarding.getCountry());
		    if(!isNullOrEmpty(candidateOnBoarding.getState())){
		    	employees.setState(candidateOnBoarding.getState());
		    }
		    if(!isNullOrEmpty(candidateOnBoarding.getCity())){
		    	employees.setCity(candidateOnBoarding.getCity());
		    }
		    employees.setUserEmploymentType(candidateOnBoarding.getEmploymentType());
		    employees.setDesignationOtherType(candidateOnBoarding.getEmploymentOtherType());
		    employees.setHireDate(candidateOnBoarding.getStartDate());
		    employees.setCreatedBy(loginUser.getUserCode());
		    employees.setCreatedDate(commonUtil.currentDate());
		    employees.setUpdatedBy(loginUser.getUserCode());
		    employees.setUpdatedDate(commonUtil.currentDate());
		    return employees;
	}
	
	
	private TeamMembers saveOnboardingTeamMember(CandidateOnBoarding candidateOnBoarding,User onboardingUser,User loginUser){
	    
	    TeamMembers teamMembers = new TeamMembers();
	    
	    Team team = new Team();
	    team.setId(candidateOnBoarding.getTeamId().getId());
	    teamMembers.setTeamId(team);
	    teamMembers.setMemberUserId(onboardingUser);
	    teamMembers.setStatus("1");
	   
	    teamMembers.setCreatedBy(loginUser.getUserCode());
	    teamMembers.setCreatedDate(commonUtil.currentDate());
	    teamMembers.setUpdatedBy(loginUser.getUserCode());
	    teamMembers.setUpdatedDate(commonUtil.currentDate());
	    return teamMembers;
}

	public void sendEmailToOnbardingUser(User user, User loginUser, String password, Long locationId) throws Exception {
		
		 UserRole userRole = userRoleDao.getUserRoleByUserIdAndLocationId(user.getId(), locationId);
		 if(userRole == null){
		    userRole = userRoleDao.getLoginUserPrimaryRole(user.getId());
		 }
		 
		 boolean isUserActive = false;
		 isUserActive = userRoleDao.isUserActive(user.getId());
		 
		 StringBuilder sb = new StringBuilder();
		 EmailMessage emailMessage = new EmailMessage();
		 emailMessage.setFirstName(user.getFirstName());
		 emailMessage.setLastName(user.getLastName());
		 emailMessage.setToEmail(user.getEmail());
		 emailMessage.setFromEmail(loginUser.getEmail());
		 emailMessage.setFromName(sb.append(loginUser.getFirstName()).append(" ").append(loginUser.getLastName()).toString());
		 //emailMessage.setEmailType(Constants.CUSTOM_USER_EMAIL);

		 emailMessage.setCreatedBy(loginUser.getUserCode());
		 emailMessage.setOrgLocationId(locationId);
		 
		//setting the class filed values
		 Map<String, String> globalVarsMap = new HashMap<String, String>();
		 StringBuilder builder=new StringBuilder();
		 
		 if(user.getLastName() !=null){
		 globalVarsMap.put("firstName",builder.append(user.getFirstName()).append(" ").append(user.getLastName()).toString());
		 }
		 else{
		 globalVarsMap.put("firstName", user.getFirstName());
		 }
		// globalVarsMap.put("lastName",emailMessage.getLastName());
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
	
	public void mailToUserTask(Tasks task,OrgLocations orgLocations) throws Exception{
		 EmailMessage emailMessage = new EmailMessage();
		 emailMessage.setFirstName(task.getTaskOwnerId().getFirstName());
		 emailMessage.setLastName(task.getTaskOwnerId().getLastName());
		 emailMessage.setToEmail(task.getAssignedUserId().getEmail());
		 emailMessage.setFromEmail(task.getTaskOwnerId().getEmail());
		 emailMessage.setCreatedBy(task.getTaskOwnerId().getUserCode());
		 StringBuilder msgBuild = new StringBuilder();
		 emailMessage.setFromName(msgBuild.append(task.getTaskOwnerId().getFirstName()).append(" ").append(task.getTaskOwnerId().getLastName()).toString());
		 emailMessage.setEmailType(Constants.CUSTOM_USER_EMAIL);
		 SimpleDateFormat simpleDateFormat = orgLocations.getLocFullFormDateFormatter();
		//setting the class filed values
		 Map<String, String> globalVarsMap = new HashMap<String, String>();
		 if(!isNullOrEmpty(task.getSubject())){
		 globalVarsMap.put("subject", task.getSubject()); 
		 }else{
		 globalVarsMap.put("subject", "--");	 
		 }
		 if(!isNullOrEmpty(task.getPriority())){
		   globalVarsMap.put("priority", task.getPriority()); 
		 }else{
		 globalVarsMap.put("priority", "--");	 
		 }
		 if(null != task.getTaskDate() && !"".equalsIgnoreCase(task.getTaskDate().toString())){
		 task.setTaskDateStr(simpleDateFormat.format(task.getTaskDate()));
		 if( task.getTaskDateStr()!= null  && !"".equalsIgnoreCase( task.getTaskDateStr().toString())){
		 globalVarsMap.put("date", task.getTaskDateStr());	 
		 	}
		 }else{
	     globalVarsMap.put("date","--"); 
		 }
		 
		 Timezone timezone = timeZoneService.getTimezone(orgLocations.getTimezone().getId());

			if(task.getTaskTime() != null){
			   	DateFormat DF_DMY = new SimpleDateFormat("hh:mma");
				String currentTimeStr = Constants.TZ_DF.format(task.getTaskDate());  
				String startTimeUTC= CommonUtil.convertTimeZones(Constants.UTC_TIMEZONE ,timezone.getTimeZoneId(), currentTimeStr);
				DateFormat TZ_DF_TS = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
				Date convertedDate = TZ_DF_TS.parse(startTimeUTC);
				String convertedDateStr = DF_DMY.format(convertedDate);
				globalVarsMap.put("time", convertedDateStr);	
			
		   }
		 
		 if(task.getDescription()!= null  && !"".equalsIgnoreCase(task.getDescription())){
		 globalVarsMap.put("description", task.getDescription()); 
		 }else{
		 globalVarsMap.put("description","--"); 
		 }
		 if(task.getTaskOwnerId().getFirstName() != null && task.getTaskOwnerId().getLastName()!= null  && !"".equalsIgnoreCase( task.getTaskOwnerId().getFirstName()) && !"".equalsIgnoreCase( task.getTaskOwnerId().getLastName())){
			 StringBuilder userBuild = new StringBuilder();
			 globalVarsMap.put("LoginUser",userBuild.append(task.getTaskOwnerId().getFirstName()).append(" ").append(task.getTaskOwnerId().getLastName()).toString());
		 }else{
		 globalVarsMap.put("LoginUser",task.getTaskOwnerId().getFirstName()); 
		 }
		 if (task.getAssignedUserId().getFirstName() != null && task.getAssignedUserId().getLastName()!= null  && !"".equalsIgnoreCase( task.getAssignedUserId().getFirstName()) && !"".equalsIgnoreCase( task.getAssignedUserId().getLastName())){
			 StringBuilder userBuild = new StringBuilder();
			 globalVarsMap.put("assignedUser",userBuild.append(task.getAssignedUserId().getFirstName()).append(" ").append(task.getAssignedUserId().getLastName()).toString().trim());
		 }else{
		 globalVarsMap.put("assignedUser",task.getAssignedUserId().getFirstName().trim()); 
		 }
		 
		 
		 //setting the Recipient information
		 InternetAddress[] iAdressArray = InternetAddress.parse(emailMessage.getToEmail());
		
		 //getting the current EmailScenario by EmailScenario unique name and entity type
		 EmailScenario  emailScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.TASK_MECHANISM_EMAIL,Constants.ENTITY_TYPE_EMAIL);
		 EmailScenario  notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.TASK_MECHANISM_NOTIFICATION,Constants.ENTITY_TYPE_NOTIFICATION);

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
		 
		 //calling the Email 
		 
		 if(!isNullOrEmpty(task.getAlertType())){
			 if(task.getAlertType().equalsIgnoreCase("Email")){
				 emailScenerioUtil.sendEmail(emailMessage,task.getOwnerOrglocationId().getId(),iAdressArray,new ArrayList<EmailAttachment>());
			 }
			 //calling the Notification 
			 if(task.getAlertType().equalsIgnoreCase("Notification")){
				 String actionLink="";
				 emailScenerioUtil.sendNotification(actionLink, task.getTaskOwnerId(),task.getAssignedUserId(), task.getOwnerOrglocationId(), notificationScenario);
			 }
			 //calling the Email  and notification 
			 if(task.getAlertType().equalsIgnoreCase("Email,Notification")){
				 emailScenerioUtil.sendEmail(emailMessage,task.getOwnerOrglocationId().getId(),iAdressArray,new ArrayList<EmailAttachment>());
				 String actionLink="";
				 emailScenerioUtil.sendNotification(actionLink, task.getTaskOwnerId(),task.getAssignedUserId(),task.getOwnerOrglocationId(), notificationScenario);
			 }
		 }

}

	@Override
	public CandidateOnBoarding getCandidateOnBoardingByEmailAndLocationId(String email, Long orgLocationId)
			throws Exception {
		return candidateOnBoardingDao.getCandidateOnBoardingByEmailAndLocationId(email, orgLocationId);
	}

	@Override
	public CandidateOnBoarding getCandidateOnBoardingByUserIdAndLocationId(Long onboardingUser, Long orgLocationId)
			throws Exception {
		return candidateOnBoardingDao.getCandidateOnBoardingByUserIdAndLocationId(onboardingUser, orgLocationId);
	}

	@Override
	public CandidateOnBoarding getCandidateOnBoardingByCandidateIdAndOfferId(Long candidateId ,Long offerId) throws Exception {
		return candidateOnBoardingDao.getCandidateOnBoardingByCandidateIdAndOfferId(candidateId ,offerId);
	}

	@Override
	public CandidateOnBoarding getCandidateOnBoardingByPersonalEmailAndLocationId(String personalEmail,
			Long orgLocationId) throws Exception {
		return candidateOnBoardingDao.getCandidateOnBoardingByPersonalEmailAndLocationId(personalEmail, orgLocationId);
	}

	@Override
	public CandidateOnBoarding getCandidateOnBoardingByCandidateIdAndLocationId(Long candidateId, Long orgLocationId)
			throws Exception {
		return candidateOnBoardingDao.getCandidateOnBoardingByCandidateIdAndLocationId(candidateId, orgLocationId);
	}

	
	
}
