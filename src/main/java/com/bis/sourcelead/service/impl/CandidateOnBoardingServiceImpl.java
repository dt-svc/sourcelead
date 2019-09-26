package com.bis.sourcelead.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.SourceleadPasswordEncoder;
import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.couchbase.util.UserStatsCacheHelper;
import com.bis.sourcelead.dao.CandidateOnBoardingDao;
import com.bis.sourcelead.dao.EmployeesDao;
import com.bis.sourcelead.dao.OnboardTasksDao;
import com.bis.sourcelead.dao.RolesDao;
import com.bis.sourcelead.dao.TaskDao;
import com.bis.sourcelead.dao.TeamMemberDao;
import com.bis.sourcelead.dao.UserDao;
import com.bis.sourcelead.dao.UserRoleDao;
import com.bis.sourcelead.dto.CandidateOnBoardingDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.elk.index.UserIndex;
import com.bis.sourcelead.elk.index.helper.UserIndexHelper;
import com.bis.sourcelead.elk.setup.SourceleadELKBaseIndexer;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.CandidateGlobal;
import com.bis.sourcelead.entity.CandidateOnBoarding;
import com.bis.sourcelead.entity.EmailAttachment;
import com.bis.sourcelead.entity.EmailMessage;
import com.bis.sourcelead.entity.EmailScenario;
import com.bis.sourcelead.entity.Employees;
import com.bis.sourcelead.entity.OnboardTasks;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgRelations;
import com.bis.sourcelead.entity.OrgSpoc;
import com.bis.sourcelead.entity.RequirementOffers;
import com.bis.sourcelead.entity.Roles;
import com.bis.sourcelead.entity.Shift;
import com.bis.sourcelead.entity.Tasks;
import com.bis.sourcelead.entity.Team;
import com.bis.sourcelead.entity.TeamMembers;
import com.bis.sourcelead.entity.Timezone;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserRole;
import com.bis.sourcelead.enums.Status;
import com.bis.sourcelead.service.AssociateBalanceLeaveService;
import com.bis.sourcelead.service.CandidateGlobalService;
import com.bis.sourcelead.service.CandidateOnBoardingService;
import com.bis.sourcelead.service.CandidateService;
import com.bis.sourcelead.service.EmailScenarioService;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.OrgRelationsService;
import com.bis.sourcelead.service.OrgSpocService;
import com.bis.sourcelead.service.RegistrationService;
import com.bis.sourcelead.service.TimeZoneService;
import com.bis.sourcelead.util.CandidateGlobalUtil;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.EmailScenerioUtil;
import com.bis.sourcelead.util.SourceLeadRandomCodeHelper;

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
	private OrgLocationService orgLocationService;
	
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
	
	@Value("${sourceleadLoginUrl}")
	private String sourceleadLoginUrl;
	
		
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
	
	@Autowired
	RegistrationService registrationService;
	
	@Autowired
	OrgRelationsService orgRelationsService;
	
	@Autowired
	OrgSpocService orgSpocService;
	
	@Autowired
	AssociateBalanceLeaveService associateBalanceLeaveService;
	
	@Value("${isCouchbaseEnabled}")
	private String isCouchbaseEnabled;
	
	@Autowired
	UserStatsCacheHelper userStatsCacheHelper;
	
	@Autowired
	CandidateGlobalService candidateGlobalService;
	
	@Autowired
	CandidateGlobalUtil candidateGlobalUtil;
	
	protected static boolean isNullOrEmpty(String str) {
		return (str == null || "".equals(str.trim()));
	}

	@Override
	public CandidateOnBoarding saveCandidateOnBoarding(CandidateOnBoarding candidateOnBoarding) throws Exception {
		return candidateOnBoardingDao.saveCandidateOnBoarding(candidateOnBoarding);
	}

	@Override
	public CandidateOnBoarding saveOnBoardingCandidatesDetails(CandidateOnBoardingDTO candidateOnBoardingDTO, OrgLocations orgLocations, User user)
			throws Exception {
		
		CandidateOnBoarding candidateOnBoarding = null;
		
		//date format code
		//SimpleDateFormat dateFormat = orgLocations.getLocFullFormDateFormatter();
		
		if(StringUtils.isNotBlank(candidateOnBoardingDTO.getSavedonboardId())){
			candidateOnBoarding = candidateOnBoardingDao.getCandidateOnBoardingById(Long.valueOf(candidateOnBoardingDTO.getSavedonboardId()));
		}else{
			candidateOnBoarding = new CandidateOnBoarding();
			candidateOnBoarding.setOnboardCandidateUUID(SourceLeadRandomCodeHelper.generateRandomUUID());
		}
		
		String[] onboardCandiate = candidateOnBoardingDTO.getOnboardCandiate().split(",");
		
		
		RequirementOffers offer = new RequirementOffers();
		offer.setId(Long.valueOf(onboardCandiate[1]));
		
		CandidateGlobal candidate = candidateGlobalService.getCandidateGlobalByCandidateId(Long.valueOf(onboardCandiate[0]));
		
		candidateOnBoarding.setCandidateId(candidate);
		candidateOnBoarding.setOfferId(offer);
		candidateOnBoarding.setFirstName(candidateOnBoardingDTO.getFirstName());
		
		if(StringUtils.isNotBlank(candidateOnBoardingDTO.getLastName())){
			candidateOnBoarding.setLastName(candidateOnBoardingDTO.getLastName());
		}else{
			candidateOnBoarding.setLastName(null);
		}
		
		if(StringUtils.isNotBlank(candidateOnBoardingDTO.getAddress1())){
			candidateOnBoarding.setAddress1(candidateOnBoardingDTO.getAddress1());
		}else {
			candidateOnBoarding.setAddress1(null);
		}
		
		if(StringUtils.isNotBlank(candidateOnBoardingDTO.getAddress2())){
			candidateOnBoarding.setAddress2(candidateOnBoardingDTO.getAddress2());
		}else{
			candidateOnBoarding.setAddress2(null);
		}
		
		if(StringUtils.isNotBlank(candidateOnBoardingDTO.getLocation())){
			candidateOnBoarding.setLocation(candidateOnBoardingDTO.getLocation());
		}
		
		if(StringUtils.isNotBlank(candidateOnBoardingDTO.getCountry())){
			candidateOnBoarding.setCountry(candidateOnBoardingDTO.getCountry());
		}
		
		if(StringUtils.isNotBlank(candidateOnBoardingDTO.getState())){
			candidateOnBoarding.setState(candidateOnBoardingDTO.getState());
		}else{
			candidateOnBoarding.setState(null);
		}
		
		if(StringUtils.isNotBlank(candidateOnBoardingDTO.getCity())){
			candidateOnBoarding.setCity(candidateOnBoardingDTO.getCity());
		}else{
			candidateOnBoarding.setCity(null);
		}
		
		if(StringUtils.isNotBlank(candidateOnBoardingDTO.getZipCode())){
			candidateOnBoarding.setZipCode(candidateOnBoardingDTO.getZipCode());
		}else{
			candidateOnBoarding.setZipCode(null);
		}
		
		if(StringUtils.isNotBlank(candidateOnBoardingDTO.getMobileNumber())){
			candidateOnBoarding.setMobileNumber(candidateOnBoardingDTO.getMobileNumber());
		}
		
		if(StringUtils.isNotBlank(candidateOnBoardingDTO.getPersonalEmail())){
			candidateOnBoarding.setPersonalEmail(candidateOnBoardingDTO.getPersonalEmail());
		}
		
		if(StringUtils.isNotBlank(candidateOnBoardingDTO.getCompanyEmail())){
			candidateOnBoarding.setCompanyEmail(candidateOnBoardingDTO.getCompanyEmail());
		}else{
			candidateOnBoarding.setCompanyEmail(null);
		}
		
		
		if(StringUtils.isNotBlank(candidateOnBoardingDTO.getGender())){
			candidateOnBoarding.setGender(candidateOnBoardingDTO.getGender());
		}else{
			candidateOnBoarding.setGender(null);
		}
		
		if(StringUtils.isNotBlank(candidateOnBoardingDTO.getDateOfBirth())){
			candidateOnBoarding.setDateOfBirth(candidateOnBoardingDTO.getDateOfBirth());
		}else{
			candidateOnBoarding.setDateOfBirth(null);
		}
		
		if(StringUtils.isNotBlank(candidateOnBoardingDTO.getAuthorizedWorkUntil())){
			candidateOnBoarding.setAuthorizedWorkUntil(candidateOnBoardingDTO.getAuthorizedWorkUntil());
		}else{
			candidateOnBoarding.setAuthorizedWorkUntil(null);
		}
		
		if(StringUtils.isNotBlank(candidateOnBoardingDTO.getEmploymentEligibility())){
			candidateOnBoarding.setEmploymentEligibility(candidateOnBoardingDTO.getEmploymentEligibility());
		}else{
			candidateOnBoarding.setEmploymentEligibility(null);
		}
		
		if(StringUtils.isNotBlank(candidateOnBoardingDTO.getUscisAlien())){
			candidateOnBoarding.setUscisAlien(candidateOnBoardingDTO.getUscisAlien());
		}else{
			candidateOnBoarding.setUscisAlien(null);
		}
		
		if(StringUtils.isNotBlank(candidateOnBoardingDTO.getShift())){
			Shift shift = new Shift();
			shift.setId(Long.valueOf(candidateOnBoardingDTO.getShift()));
			candidateOnBoarding.setShift(shift);
		}else{
			candidateOnBoarding.setShift(null);
		}
		
		if(StringUtils.isNotBlank(candidateOnBoardingDTO.getOnboardCandidateRole())){
			Roles roles = new Roles();
			roles.setId(Long.valueOf(candidateOnBoardingDTO.getOnboardCandidateRole()));
			candidateOnBoarding.setRoleId(roles);
		}
		
		if(StringUtils.isNotBlank(candidateOnBoardingDTO.getStartDate())){
			candidateOnBoarding.setStartDate(commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),candidateOnBoardingDTO.getStartDate()));
		}
		
		if(StringUtils.isNotBlank(candidateOnBoardingDTO.getEndDate())){
			candidateOnBoarding.setEndDate(commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),candidateOnBoardingDTO.getEndDate()));
		}else{
			candidateOnBoarding.setEndDate(null);
		}
		
		if(StringUtils.isNotBlank(candidateOnBoardingDTO.getSalaryRate())){
			candidateOnBoarding.setSalaryRate(candidateOnBoardingDTO.getSalaryRate());
		}
		
		if(StringUtils.isNotBlank(candidateOnBoardingDTO.getPaymentType())){
			candidateOnBoarding.setPaymentType(candidateOnBoardingDTO.getPaymentType());
		}	
		
		if(StringUtils.isNotBlank(candidateOnBoardingDTO.getPaymentMode())){
			candidateOnBoarding.setPaymentMode(candidateOnBoardingDTO.getPaymentMode());
		}
		if(StringUtils.isNotBlank(candidateOnBoardingDTO.getReportingManager())){
			User reportingManager = new User();
			reportingManager.setId(Long.valueOf(candidateOnBoardingDTO.getReportingManager()));
			candidateOnBoarding.setManagerUserId(reportingManager);
		}else{
			candidateOnBoarding.setManagerUserId(null);
		}
		
		if(StringUtils.isNotBlank(candidateOnBoardingDTO.getTeam())){
			Team team = new Team();
			team.setId(Long.valueOf(candidateOnBoardingDTO.getTeam()));
			candidateOnBoarding.setTeamId(team);
		}else{
			candidateOnBoarding.setTeamId(null);
		}
		
		if(StringUtils.isNotBlank(candidateOnBoardingDTO.getEmploymentType())){
			candidateOnBoarding.setEmploymentType(candidateOnBoardingDTO.getEmploymentType());
			if(candidateOnBoardingDTO.getEmploymentType().equalsIgnoreCase("C2C")) {
				OrgRelations orgRelation = orgRelationsService.getOrRelationsById(Long.parseLong(candidateOnBoardingDTO.getVendors()));
				candidate.setOrgRelationId(orgRelation);
				CandidateGlobal vendorCandidate = candidateGlobalUtil.getC2CVendorCandidateGlobal(candidate, orgRelation, orgLocations, user);
				candidate.setParentCandidateId(vendorCandidate);
				
			    OrgSpoc orgspoc = orgSpocService.getOrgSpocById(Long.parseLong(candidateOnBoardingDTO.getVendorContact()));
			    candidate.setSpocId(orgspoc);
				  
			}else {
				candidate.setParentCandidateId(null);
				candidate.setSpocId(null);
			}
			candidate.setDesignationType(candidateOnBoardingDTO.getEmploymentType());
			CandidateGlobal savedCandidate = candidateGlobalService.storeCandidateGlobal(candidate);
			candidateGlobalService.saveOrUpdateCandidateGlobalElkDocument(savedCandidate);
		}else{
			candidateOnBoarding.setEmploymentType(null);
			candidateOnBoarding.setEmploymentOtherType(null);
		}
		
		if(StringUtils.isNotBlank(candidateOnBoardingDTO.getEmploymentOtherType())){
			
			if(StringUtils.isNotBlank(candidateOnBoardingDTO.getEmploymentType()) && candidateOnBoardingDTO.getEmploymentType().equalsIgnoreCase("Other")){
				candidateOnBoarding.setEmploymentOtherType(candidateOnBoardingDTO.getEmploymentOtherType());
			}else{
				candidateOnBoarding.setEmploymentOtherType(null);
			}
			
		}
		
		
		if(StringUtils.isNotBlank(candidateOnBoardingDTO.getProbationNotice())){
			candidateOnBoarding.setProbationNotice(candidateOnBoardingDTO.getProbationNotice());
		}else{
			candidateOnBoarding.setProbationNotice(null);
		}
		
		if(StringUtils.isNotBlank(candidateOnBoardingDTO.getTerminationNotice())){
			candidateOnBoarding.setTerminationNotice(candidateOnBoardingDTO.getTerminationNotice());
		}else{
			candidateOnBoarding.setTerminationNotice(null);
		}
		
		if(StringUtils.isNotBlank(candidateOnBoardingDTO.getDueDate())){
			candidateOnBoarding.setDueDate(commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),candidateOnBoardingDTO.getDueDate()));
		}else{
			candidateOnBoarding.setDueDate(null);
		}
		
		
		if(StringUtils.isNotBlank(candidateOnBoardingDTO.getSpecialConditions())){
			candidateOnBoarding.setSpecialConditions(candidateOnBoardingDTO.getSpecialConditions());
		}else{
			candidateOnBoarding.setSpecialConditions(null);
		}
		
		if(StringUtils.isNotBlank(candidateOnBoardingDTO.getOnboardingTemplate())){
					candidateOnBoarding.setOnboardTemplateId(candidateOnBoardingDTO.getOnboardingTemplate());
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
		boolean isUserActive = true;
		
		String password = null;
		
		if(!isNullOrEmpty(candidateOnBoarding.getCompanyEmail())){
			onboardingUser = userDao.findByUserName(candidateOnBoarding.getCompanyEmail());
		}else{
			onboardingUser = userDao.findByUserName(candidateOnBoarding.getPersonalEmail());
		}
		
		if(onboardingUser == null){
			password = SourceLeadRandomCodeHelper.generateRandomPassword();
			onboardingUser = getUserDetails(candidateOnBoarding,password,loginUser);
		    userDao.addUser(onboardingUser);
		    if("Newly Registered".equalsIgnoreCase(onboardingUser.getRegistrationType()))
		    	registrationService.sendActivationEmail(onboardingUser,orgLocations);
		}
		
		
		List<UserRole> rolesList = userRoleDao.getUserSpocsListByUserId(onboardingUser.getId());
		if(rolesList != null && rolesList.size() > 0) {
			for(UserRole roles : rolesList) {
				if(roles.getUserState() != null  && !roles.getUserState().equals(Status.ACTIVE)) {
					roles.setIsPrimary(0);
					userRoleDao.addUserRole(roles);
					isUserActive = false;
				}else {
					isUserActive = true;
				}
			}
		}
		
		onboardingUserRole = userRoleDao.getUserDetailsByUserIdAndLocationId(onboardingUser.getId(), orgLocations.getId());
	    
		if(onboardingUserRole == null){
			onboardingUserRole = saveUserRoles(candidateOnBoarding,onboardingUser,loginUser,orgLocations);
		    userRoleDao.addUserRole(onboardingUserRole);
		}
		
		if(isUserActive == false) {
			onboardingUser.setPassword(sourceleadPasswordEncoder.encode(password));
			userDao.addUser(onboardingUser);
			onboardingUserRole.setIsPrimary(1);
			userRoleDao.addUserRole(onboardingUserRole);
		}
		
		CandidateGlobal candidateGlobal = candidateGlobalService.getCandidateGlobalByCandidateId(candidateOnBoarding.getCandidateId().getId());
		Candidate candidate = candidateService.findByCandidateEmail(candidateGlobal.getEmail(),candidateGlobal.getOrgLocationId().getId());
		if(null != candidate){
			if(candidateOnBoarding.getShift() != null) {
				candidate.setShift(candidateOnBoarding.getShift());
			}
			candidate.setConsultantUser(onboardingUser);
			candidateService.addCandidate(candidate);
		}else {
			candidate = candidateGlobalUtil.storeCandidateFromCandidateGlobal(candidateGlobal, onboardingUser);
			if(candidateOnBoarding.getShift() != null) {
				candidate.setShift(candidateOnBoarding.getShift());
			}
			candidate.setConsultantUser(onboardingUser);
			candidateService.addCandidate(candidate);
		}
	    
	    onboardingEmployee = employeesDao.getEmployeeByUserIdAndOrgLocationsId(onboardingUser.getId(), orgLocations.getId());
	    
	    if(onboardingEmployee == null){
	    	onboardingEmployee = saveOnboardingEmployee(candidateOnBoarding,onboardingUser,loginUser,orgLocations);
	 	    employeesDao.addEmployee(onboardingEmployee);
	 	   associateBalanceLeaveService.addAssociateLeaveType(onboardingUser,orgLocations.getId(),onboardingEmployee,onboardingUserRole);
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
		 
		 if(isCouchbaseEnabled.equalsIgnoreCase("true")) {
			UserRole userRole = userRoleDao.getUserRoleById(onboardingUserRole.getId());
			userStatsCacheHelper.saveUserProfileDetailsToCouchbase(userRole);
			}
		
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
	    
	    user.setLocation(candidateOnBoarding.getAddress1());
	    user.setGender(candidateOnBoarding.getGender());
	    user.setPassword(sourceleadPasswordEncoder.encode(password));
		
		user.setUserCode(SourceLeadRandomCodeHelper.generateRandomUserCode());
		user.setCreatedBy(loginUser.getUserCode());
		user.setVerificationUUID(RandomStringUtils.randomAlphanumeric(16));
		user.setUrlSentTime(new Date());
		user.setIsVerified(Constants.NO);
		user.setCreatedDate(commonUtil.currentDate());
		user.setRegistrationType("Newly Registered");
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
	   
	    userRole.setIsMailSent(Status.Y);
	    userRole.setUserState(Status.NOT_LOGGEDIN);
	    userRole.setCreatedBy(loginUser.getUserCode());
	    userRole.setCreatedDate(commonUtil.currentDate());
	    userRole.setUpdatedBy(loginUser.getUserCode());
	    userRole.setUpdatedDate(commonUtil.currentDate());
	   
	    return userRole;
	}
	
	private Employees saveOnboardingEmployee(CandidateOnBoarding candidateOnBoarding,User onboardingUser,User loginUser,OrgLocations orgLocations) throws Exception{
		    
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
		    employees.setRate(candidateOnBoarding.getSalaryRate());
		    employees.setCurrentCurrency(candidateOnBoarding.getPaymentType());
		    employees.setRatePer(candidateOnBoarding.getPaymentMode());
		    employees.setCreatedBy(loginUser.getUserCode());
		    employees.setCreatedDate(commonUtil.currentDate());
		    employees.setUpdatedBy(loginUser.getUserCode());
		    employees.setUpdatedDate(commonUtil.currentDate());
		    
		    if(candidateOnBoarding.getProbationNotice() != null)
		    employees.setProbationPeriod(candidateOnBoarding.getProbationNotice());
		    
		    CandidateGlobal candidate = candidateGlobalService.getCandidateGlobalByCandidateId(candidateOnBoarding.getCandidateId().getId());
		    if(candidate.getOrgRelationId() != null) {
		    	employees.setOrgRelationId(candidate.getOrgRelationId());
		    }
		    
		    if(candidate.getSpocId() != null) {
		    	employees.setSpocId(candidate.getSpocId());
		    }
		    
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
		 
		 OrgLocations locations= orgLocationService.getOrgLocations(locationId);
		 
		 
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
		 globalVarsMap.put("LoginURL",loginBuilder.append("<a href='").append(sourceleadLoginUrl).append("' style='text-decoration:none; '><div style=' width:220px; border-radius:30px; background-color:#214b90; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Login</div></a>").toString());
		 globalVarsMap.put("UserEmailId",user.getEmail());
		 if(password != null){
			 globalVarsMap.put("Password",password);
		 }else{
			 globalVarsMap.put("Password","Login with your existing sourcelead credentials");
		 }
		 
		 //calling the sendEmail method of emailScenerioUtil
		 commonUtil.sendTemplateEmail(emailMessage, globalVarsMap,userRole.getOrgLocations().getOrg(),userRole.getOrgLocations(),Constants.INVITE_USER,new ArrayList<EmailAttachment>());
		
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
		// SimpleDateFormat simpleDateFormat = orgLocations.getLocFullFormDateFormatter();
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
		 task.setTaskDateStr(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),task.getTaskDate()));
		 if( task.getTaskDateStr()!= null  && !"".equalsIgnoreCase( task.getTaskDateStr().toString())){
		 globalVarsMap.put("date", task.getTaskDateStr());	 
		 	}
		 }else{
	     globalVarsMap.put("date","--"); 
		 }
		 
		 Timezone timezone = timeZoneService.getTimezone(Long.valueOf(orgLocations.getTimeZoneId()));

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
		 
		 
		
		 //getting the current EmailScenario by EmailScenario unique name and entity type
		 EmailScenario  notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.TASK_MECHANISM_NOTIFICATION,Constants.ENTITY_TYPE_NOTIFICATION);

		 
		 String notificationbody = emailScenerioUtil.buildTemplateBody(notificationScenario.getBody(),globalVarsMap,notificationScenario);
		 notificationScenario.setBody(notificationbody);
		 
		 //calling the Email 
		 
		 if(!isNullOrEmpty(task.getAlertType())){
			 if(task.getAlertType().equalsIgnoreCase("Email")){
				 commonUtil.sendTemplateEmail(emailMessage, globalVarsMap,task.getOwnerOrglocationId().getOrg(),task.getOwnerOrglocationId(),Constants.TASK_MECHANISM_EMAIL,new ArrayList<EmailAttachment>());
			 }
			 //calling the Notification 
			 if(task.getAlertType().equalsIgnoreCase("Notification")){
				 String actionLink="";
				 emailScenerioUtil.sendNotification(actionLink, task.getTaskOwnerId(),task.getAssignedUserId(), task.getOwnerOrglocationId(), notificationScenario,new HashMap<String,String>());
			 }
			 //calling the Email  and notification 
			 if(task.getAlertType().equalsIgnoreCase("Email,Notification")){
				 commonUtil.sendTemplateEmail(emailMessage, globalVarsMap,task.getOwnerOrglocationId().getOrg(),task.getOwnerOrglocationId(),Constants.TASK_MECHANISM_EMAIL,new ArrayList<EmailAttachment>());
				 String actionLink="";
				 emailScenerioUtil.sendNotification(actionLink, task.getTaskOwnerId(),task.getAssignedUserId(),task.getOwnerOrglocationId(), notificationScenario,new HashMap<String,String>());
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

	@Override
	public ResponseList<CandidateOnBoardingDTO> getCandidateOnBoardingList(
			CandidateOnBoardingDTO candidateOnBoardingDTO, OrgLocations orgLocations) throws Exception {
		return candidateOnBoardingDao.getCandidateOnBoardingList(candidateOnBoardingDTO, orgLocations);
	}

	
	
}
