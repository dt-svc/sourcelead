package com.bis.sourcelead.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.SourceleadPasswordEncoder;
import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.ProjectApproverHierarchyDao;
import com.bis.sourcelead.dao.ProjectsDao;
import com.bis.sourcelead.entity.EmailAttachment;
import com.bis.sourcelead.entity.EmailMessage;
import com.bis.sourcelead.entity.EmailRecipient;
import com.bis.sourcelead.entity.EmailScenario;
import com.bis.sourcelead.entity.Employees;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgRelations;
import com.bis.sourcelead.entity.ProjectApproverHierarchy;
import com.bis.sourcelead.entity.ProjectMembers;
import com.bis.sourcelead.entity.ProjectMembersHistory;
import com.bis.sourcelead.entity.ProjectTasks;
import com.bis.sourcelead.entity.Projects;
import com.bis.sourcelead.entity.Roles;
import com.bis.sourcelead.entity.SubmissionApproverHierarchy;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserRole;
import com.bis.sourcelead.enums.Primary;
import com.bis.sourcelead.enums.Project_Status;
import com.bis.sourcelead.enums.Status;
import com.bis.sourcelead.service.EmailScenarioService;
import com.bis.sourcelead.service.EmployeesService;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.OrgRelationsService;
import com.bis.sourcelead.service.ProjectApproverHierarchyService;
import com.bis.sourcelead.service.ProjectMembersHistoryService;
import com.bis.sourcelead.service.ProjectMembersService;
import com.bis.sourcelead.service.ProjectTasksService;
import com.bis.sourcelead.service.ProjectsService;
import com.bis.sourcelead.service.RolesService;
import com.bis.sourcelead.service.SubmissionApproverHierarchyService;
import com.bis.sourcelead.service.UserRoleService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.EmailScenerioUtil;
import com.bis.sourcelead.util.SourceLeadRandomCodeHelper;
import com.bis.sourcelead.util.SourceLeadSessionManager;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ProjectsServiceImpl implements ProjectsService{
	
	private static final Logger logger = LoggerFactory.getLogger(ProjectsServiceImpl.class);

	
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	@Autowired
	HttpServletRequest request;
	
	@Autowired
	private ProjectsDao projectsDao;
	
	@Autowired
	private ProjectApproverHierarchyDao projectApproverHierarchyDao;
	  
	@Autowired
	private EmailScenarioService emailScenarioService;
	    
	@Autowired
	private EmailScenerioUtil emailScenerioUtil;
	    
	@Autowired
	private UserService userService;
	
	@Autowired
	public ProjectApproverHierarchyService projectApproverHierarchyService;
	
	@Autowired
	public ProjectMembersService projectMembersService;
	
	 @Autowired
	 private OrgRelationsService orgRelationsService;

	 @Autowired
	 private OrgLocationService orgLocationService;
	 
	 @Autowired
	 private CommonUtil commonUtil;
	 
	 @Autowired
	 private  ProjectMembersHistoryService projectMembersHistoryService;
	 
	 @Autowired
	 public EmployeesService employeesService;
	 
	 @Autowired
	 public RolesService rolesService;
	 
	 @Autowired
	 public UserRoleService userRoleService;
	 
	 @Autowired
	 public SubmissionApproverHierarchyService submissionApproverHierarchyService;
	 
	 @Autowired
	 private SourceleadPasswordEncoder sourceleadPasswordEncoder;
	 
	 @Autowired
	 private ProjectTasksService tasksService;
	 
	 @Autowired
	 private ProjectTasksService projectTasksService;

	@Override
	public Projects saveProject(Projects projects) throws Exception{
		logger.debug("In ProjectsServiceImpl class saveProject method...");
		return projectsDao.saveProject(projects);
	}

	@Override
	public Projects updateProject(Projects projects) throws Exception {
		logger.debug("In ProjectsServiceImpl class updateProject method...");
		return projectsDao.updateProject(projects);
	}

	@Override
	public Projects getProjectById(Long id) throws Exception {
		logger.debug("In ProjectsServiceImpl class getProject method...");
		return projectsDao.getProjectById(id);
	}
	
	@Override
	public List<Projects> getProjectByOrgLocationsId(Long orgLocationsId) throws Exception {
		logger.debug("In ProjectsServiceImpl class getProjectByOrgLocationsId method...");
		return projectsDao.getProjectByOrgLocationsId(orgLocationsId);
	}
	
	@Override
	public List<Projects> getProjectByOrgLocationsIdAndEndDateGraterthanCurrentDate(Long orgLocationsId, Long loginUserId) throws Exception {
		logger.debug("In ProjectsServiceImpl class getProjectByOrgLocationsIdAndEndDateGraterthanCurrentDate method...");
		return projectsDao.getProjectByOrgLocationsIdAndEndDateGraterthanCurrentDate(orgLocationsId, loginUserId);
	}
	
	@Override
	public Projects getPrimaryProjectByOrgLocationsId(Long orgLocationsId) throws Exception {
		logger.debug("In ProjectsServiceImpl class getPrimaryProjectByOrgLocationsId method...");
		return projectsDao.getPrimaryProjectByOrgLocationsId(orgLocationsId);
	}

	@Override
	public Boolean deleteProjectById(Long Id) throws Exception {
		logger.debug("In ProjectsServiceImpl class deleteProject method...");
		Boolean isDeleted = projectsDao.deleteProjectById(Id);
		return isDeleted;
	}
	
	@Override
	public Boolean validateProjectSequeneNumber(String projectSequeneNumber, Long orgLocationsId) throws Exception {
		logger.debug("In ProjectsServiceImpl class validateProjectSequeneNumber method...");
		Boolean flag = projectsDao.validateProjectSequeneNumber(projectSequeneNumber,orgLocationsId);
		return flag;
	}

	@Override
	public List<Projects> getAllProjects() throws Exception {
		logger.debug("In ProjectsServiceImpl class getAllProjects method...");
		List<Projects> projectsList = projectsDao.getAllProjects();
		return projectsList;
	}
	
	public List<Projects> getAllLoginUserProjectsList(List<Long> projectIds,Long orgLocationId) throws Exception {
		logger.debug("In ProjectsServiceImpl class getAllProjectsAndManagers method...");
		List<Projects> projectsList = projectsDao.getAllLoginUserProjectsList(projectIds,orgLocationId);
		return projectsList;
	}
	
	@Override
	public Integer projectIdValidation(Long projectId) throws Exception {
		logger.debug("In ProjectsServiceImpl class deleteProject method...");
		Integer validation = projectsDao.projectIdValidation(projectId);
		return validation;
	}

	@Override
	public List<Projects> getAllClientProjectsByOrgRelationId(Long orgRelationId, Long locationId) throws Exception {
		logger.debug("In ProjectsServiceImpl class getAllClientProjectsByOrgRelationId method...");
		List<Projects> clientProjectsList = projectsDao.getAllClientProjectsByOrgRelationId(orgRelationId,locationId);
		return clientProjectsList;
	}

	@Override
	public Integer updateStartDateOfProjectId(Date startDate , Long projectId) {
		return projectsDao.updateStartDateOfProjectId(startDate,projectId);
	}

	@Override
	public Integer updateEndDateOfProjectId(Date endDate, Long projectId) {
		return projectsDao.updateEndDateOfProjectId(endDate,projectId);
	}
	
	@Override
	public List<Long> getApprovarAssignedProjects(Long userId, Long orgLocationId) throws Exception {
		logger.debug("In ProjectsServiceImpl class getApprovarAssignedProjects method...");
		List<Long> projectIdsList = projectsDao.getApprovarAssignedProjects(userId, orgLocationId);
		return projectIdsList;
	}

	@Override
	public Projects getProjectByProjectSequenceNumber(String projectSequeneNumber) throws Exception {
		
		return projectsDao.getProjectByProjectSequenceNumber(projectSequeneNumber);
	}
	
	 /**
	 * @author sarath kumar
	 * @since  aug 19 2016
	 * @version 0.0.1
	 * 
	 * This method is used to save projects
	 * 1.from general project saving
	 * 2.from user saving
	 * 
	 * @param jsonObj This is the first paramter to storeProject method
	 *  
	 * @return string 
	 * 
	 * @throws JSONException
	 */
	
	@Override
	public String storeProject(JSONObject jsonObj) throws Exception {
		    
		  
		String resultJson = null;
	 	OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
	 	User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
	 	String loginOrgCode = (String) SourceLeadSessionManager.getSessionAttribute(request, "orgCode");
	 	String pattern = "yyyy-MM-dd";
        SimpleDateFormat format = new SimpleDateFormat(pattern);
	 	
    	Projects projects = new Projects();
    	projects.setProjectSequeneNumber(jsonObj.getString("ProjectSequeneNumber"));
    	projects.setProjectName(jsonObj.getString("ProjectName"));
    	
    	if((jsonObj.has("ProjectDescription"))){
    	projects.setDescription(jsonObj.getString("ProjectDescription"));
    	}
    	if(jsonObj.getString("StartDate") != null && jsonObj.getString("StartDate") != "" && jsonObj.getString("StartDate") != "undefined"){
    		Date startDate = format.parse(jsonObj.getString("StartDate"));
	    	projects.setStartDate(startDate);
    	}
    	if(!jsonObj.getString("EndDate").isEmpty()){
    		Date endDate = format.parse(jsonObj.getString("EndDate"));
	    	projects.setEndDate(endDate);
    	}
    	if((jsonObj.has("Satatus"))){
    	projects.setProjectStatus(Project_Status.getNameByValue((jsonObj.getString("Satatus"))));
    	}
    	projects.setCreatedDate(commonUtil.currentDate());
    	projects.setUpdatedDate(commonUtil.currentDate());
    	if(user.getUserCode() != null){
    		projects.setCreatedBy(user.getUserCode());	
    	}
    	projects.setOrgLocationsId(loginOrgLocation);
    	
    	// Saving of Client by bringing OrgRelation id based on Client OrgId
    	if((jsonObj.has("selectClient"))){
	    String clientOrgCode = jsonObj.getString("selectClient");	
	    if(!clientOrgCode.isEmpty() && clientOrgCode!=null){
	    	  OrgLocations clientOrgLocations = orgLocationService.getOrgLocationsByOrgCode(clientOrgCode);
	    	  if(clientOrgLocations!=null){
	    		  OrgRelations  orgRelations = orgRelationsService.getOrgRelationIdByOrgLocationIdAndClientLocationId(loginOrgLocation.getId(), clientOrgLocations.getId());
		    		  if(orgRelations!=null){
		    			  projects.setOrgRelationId(orgRelations);
		    		  }
	    	  }
	    	  
	    }
    	}
	    
	    if((jsonObj.has("isMailToTeamMembers") && StringUtils.isNotEmpty(jsonObj.getString("isMailToTeamMembers"))) && ("Y".equals(jsonObj.getString("isMailToTeamMembers")))){
			projects.setIsMailToTeamMembers(Status.Y);
		}else{
			projects.setIsMailToTeamMembers(Status.N);
		}
	    
	    if((jsonObj.has("isMailToProjectManager") && StringUtils.isNotEmpty(jsonObj.getString("isMailToProjectManager"))) && ("Y".equals(jsonObj.getString("isMailToProjectManager")))){
		  projects.setIsMailToProjectManager(Status.Y);
		}else{
			projects.setIsMailToProjectManager(Status.N);
		}
    	

    	//prepare ProjectApproverHierarchy
    	Set<ProjectApproverHierarchy> projectApproverHierarchyList = projectApproverHierarchyService.prepareProjectApproverHierarchyListByJson(jsonObj, request, projects);
    	projects.setProjectApprovers(projectApproverHierarchyList);
		
		//prepare project task set...
		/*tasksService.*/
		Set<ProjectTasks>  projectTasksList = projectTasksService.prepareProjectTasksListByJson(jsonObj, request, projects);
		projects.setProjectTasks(projectTasksList);
		//prepare projectmembers list...
		
		Set<ProjectMembers>  projectMemberList = projectMembersService.prepareProjectMembersListByJson(jsonObj, request, projects);
		projects.setProjectMembers(projectMemberList);
		
		Projects updatedProjects = saveProject(projects);
		
		/**
		 * this block is for submissions for project member
		*/
		if (jsonObj.has("employeeMaxCount")){
		String employeeMaxCount = jsonObj.getString("employeeMaxCount");
		if(StringUtils.isNotEmpty(employeeMaxCount)){
		int empMaxCount = Integer.parseInt(employeeMaxCount);
			for (int c = 1; c < empMaxCount; c++) {
			Employees emp = employeesService.getEmployee(jsonObj.getString("employeeId"+c));
			commonUtil.storeTimesheetSubmissionRecords(emp.getUser());
			}
		}
		}
		
		
		/*if((jsonObj.has("isMailToProjectManager") && StringUtils.isNotBlank(jsonObj.getString("isMailToProjectManager"))) && ("Y".equals(jsonObj.getString("isMailToProjectManager")))){
			if(approverUserId1 != null && user.getId() != approverUserId1.getId()){
				sendEmailToProjectManager(projects, user, approverUserId1, loginOrgLocation, teamMembersList, loginOrgCode);
			}
			if(!jsonObj.getString("level2Manager").isEmpty() && approverUserId2 != null && user.getId() != approverUserId2.getId()){
				sendEmailToProjectManager(projects, user, approverUserId2, loginOrgLocation, teamMembersList, loginOrgCode);
			}
		}*/
		 
	  SourceLeadSessionManager.setSessionAttribute(request, "projectCreateMsg", "Successfully you have created a project");
	  return "projectDashboard";
		 
		
	}
	
	@Override
	public List<Projects> getProjectsBasedOnClientsAndStatusSelectionInFilters(Long userId, String clients,
			String status,Long orgLocationsId) throws Exception {
		logger.debug("In ProjectsServiceImpl class getProjectsBasedOnClientsAndStatusSelectionInFilters method...");
    	List<Projects> projectList = projectsDao.getProjectsBasedOnClientsAndStatusSelectionInFilters(userId,clients,status,orgLocationsId);
		 for (Projects projects :projectList ){
	    	    projects.setStartDateStr(Constants.DF_DMY.format(projects.getStartDate()));
	    	    projects.setEmployeeStartDateStr(Constants.DF_YMD.format(projects.getStartDate()));
			    if(projects.getEndDate() != null){
			    	 projects.setEndDateStr(Constants.DF_DMY.format(projects.getEndDate()));
			    	 projects.setEmployeeEndDateStr(Constants.DF_YMD.format(projects.getEndDate()));
			    }
			    if(projects.getOrgRelationId()!=null) {
	                projects.setClient(projects.getOrgRelationId().getClientLocationId().getOrg().getOrgName());
	            }
			    Collection<ProjectApproverHierarchy> projectApproverHierarchies = projectApproverHierarchyDao.getApproverUsersByProjectId(projects.getId());
			    for(ProjectApproverHierarchy approverHierarchy :projectApproverHierarchies){
			    	if(approverHierarchy.getLevel()==1){
			    		projects.setManagerLevel1(approverHierarchy.getApproverUserId().getFirstName()+" "+approverHierarchy.getApproverUserId().getLastName());
			    	}else if(approverHierarchy.getLevel()==2){
			    		projects.setManagerLevel2(approverHierarchy.getApproverUserId().getFirstName()+" "+approverHierarchy.getApproverUserId().getLastName());
			    	}
			    }
	    }
		return projectList;
		
	}


	@Override
	public List<Projects> getProjectByOrgLocationsIdAndUserId(Long id, Long userId) {
		// TODO Auto-generated method stub
		return projectsDao.getProjectByOrgLocationsIdAndUserId(id,userId);
	}


	@Override
	public Projects getProjectByIdAndLoginOrgId(Long projectId, Long orgLocationId) throws Exception {
		// TODO Auto-generated method stub
		return projectsDao.getProjectByIdAndLoginOrgId(projectId, orgLocationId);
	}

	
	

    @Override
	public Integer getUserAssignedProjectsCount(Long userId, Long orgLocationId) {
		// TODO Auto-generated method stub
		return projectsDao.getUserAssignedProjectsCount(userId, orgLocationId);
	}

	@Override
	public List<User> getAllUesrTimeSheetSubmissionStatus() throws Exception {
		
		return projectsDao.getAllUesrTimeSheetSubmissionStatus();
	}

	@Override
	public void userEmailNotificationForTimeShhetSubmission(User user) throws Exception {
		
		EmailMessage emailMessage = new EmailMessage();
		 emailMessage.setFirstName(user.getFirstName());
		 emailMessage.setLastName(user.getLastName());
		 emailMessage.setToEmail(user.getEmail());
		 emailMessage.setFromEmail("peopleaxisindia@gmail.com");
		 emailMessage.setFromName(user.getFirstName()+" "+user.getLastName());
		 
		//setting the class filed values
		 Map<String, String> globalVarsMap = new HashMap<String, String>();
		 globalVarsMap.put("firstName", emailMessage.getFirstName());
		 globalVarsMap.put("lastName",emailMessage.getLastName());
		 globalVarsMap.put("administratorFirstName", user.getFirstName());
		 globalVarsMap.put("administratorLastName",user.getLastName());
		 globalVarsMap.put("role",user.getUserType());
		 globalVarsMap.put("userEmailId",user.getEmail());
		
		 //setting the Recipient information
		 ArrayList<EmailRecipient> emailRecipientList = new ArrayList<EmailRecipient>();
		 EmailRecipient emailRecipient = new EmailRecipient();
		 emailRecipient.setEmail(emailMessage.getToEmail());
		 emailRecipient.setName(emailMessage.getFirstName()+" "+emailMessage.getLastName());
		 emailRecipientList.add(emailRecipient);
		
		 //getting the current EmailScenario by EmailScenario unique name and entity type
		 EmailScenario  emailScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.TIMESHEET_EMAIL_NOTIFICATION,Constants.ENTITY_TYPE_EMAIL);
		
		 //covert subject,body and signature
		 String subject = emailScenerioUtil.bulidSubject(emailScenario.getSubject(),globalVarsMap);
		 String body = emailScenerioUtil.buildTempBody(emailScenario.getBody(),globalVarsMap);
		 String signature = emailScenerioUtil.buildSignature(emailScenario.getSignature(),globalVarsMap);
		 String url = "";
		 
		 //construct HTML template using required data
		 String emailTemplate = emailScenerioUtil.AssembleEmailMessage(emailScenario,emailMessage, subject, body, signature, url, new Org());
		 emailMessage.setSubject(subject);
		 emailMessage.setMessageBody(emailTemplate);
		 
		 //calling the sendEmail method of emailScenerioUtil
		 emailScenerioUtil.sendEmail(emailMessage,emailRecipientList,new ArrayList<EmailAttachment>());
		 sendWeeklyBaseNotiFication(user);
		
		//projectsDao.userEmailNotificationForTimeShhetSubmission(user);
	}

//	@Override
//	public List<SubmissionApproverHierarchy> getApproveUserForTimeSheets(Long userId) throws Exception {
//		return projectsDao.getApproveUserForTimeSheets(userId);
//	}
	

	private void sendWeeklyBaseNotiFication(User user) throws Exception {
		List<Employees> empList = userService.getAllUserOrgCodeAndLocationCodeTimeSheetNotification(user.getId());
	    Employees emp = empList.get(0);
	    logger.info("Employee Location code :"+emp.getOrgLocations().getLocationCode());
		logger.info("Employee org code :"+emp.getOrgLocations().getOrg().getOrgCode());
	    User toUser = emp.getUser();
	    List<SubmissionApproverHierarchy> sah = projectsDao.getApproveUserForTimeSheets(user.getId());
	    User fromUser = sah.get(0).getHeirarchyId().getApproverUserId();
	    logger.info("Aprover for the Employee "+user.getFirstName()+" is :"+fromUser.getFirstName()+"("+fromUser.getUserType()+")");
	        EmailScenario  emailScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.WEEK_TIME_SHEET_SUBMISSION,Constants.ENTITY_TYPE_NOTIFICATION);
		    Map<String, String> globalVarsMap = new HashMap<String, String>();
		    globalVarsMap.put("firstName", user.getFirstName());
		    globalVarsMap.put("lastName",user.getLastName());
			String body = emailScenerioUtil.buildBody(emailScenario.getBody(),globalVarsMap);
			emailScenario.setBody(body);
			emailScenario.setOrgCode(emp.getOrgLocations().getOrg().getOrgCode());
			String actionLink = "";
			OrgLocations loginOrgLocation = new OrgLocations();
		loginOrgLocation.setLocationCode(emp.getOrgLocations().getLocationCode());
			emailScenerioUtil.sendNotification(actionLink, fromUser, toUser, loginOrgLocation, emailScenario);		
	}

	@Override
	public List<User> getAllUesrPrevoiusWeekTimeSheetSubmissionStatus() throws Exception {
		
		return projectsDao.getAllUesrPrevoiusWeekTimeSheetSubmissionStatus();
	}

	@Override
	public void saveAllProjects(List<Projects> projectsList) throws Exception {
		// TODO Auto-generated method stub
		 projectsDao.saveAllProjects(projectsList);
	}

	/**
	 * get project details by project Id
	 * @author Laxman
	 * @param id
	 * @return Json
	 * @throws Exception
	 */
	
	@Override
	public String getProjectDetailsByProjectIdAndLoginOrgId(Long projectId, User user, String loginOrgLocation)throws Exception {
		OrgLocations loginOrgLocations = orgLocationService.getOrgLocationsByLocationCode(loginOrgLocation);
		Projects projects = getProjectByIdAndLoginOrgId(projectId, loginOrgLocations.getId());
		
		String resultJson =null;
		
		if(projects.getCreatedDate() != null){
			projects.setCreatedDateStr(Constants.DF_DMY.format(projects.getCreatedDate()));	
		}
	    projects.setStartDateStr(Constants.DF_YMD.format(projects.getStartDate()));
	    if(projects.getEndDate() != null){
	    	 projects.setEndDateStr(Constants.DF_YMD.format(projects.getEndDate()));
	    }
	    if(projects.getUpdatedDate() != null){
	    	projects.setUpdatedDateStr(Constants.DF_DMY.format(projects.getUpdatedDate()));
	    }
	    if(projects.getProjectSequeneNumber() != null){
	    	projects.setProjectSequeneNumber(projects.getProjectSequeneNumber());
	    }
	    projects.setCreatedBy(user.getFirstName()+" "+user.getLastName());
	    Collection<ProjectApproverHierarchy> projectApproverHierarchies = projectApproverHierarchyService.getApproverUsersByProjectId(projectId);
	   
	    for(ProjectApproverHierarchy approverHierarchy : projectApproverHierarchies){
	    	if(approverHierarchy.getLevel() != null && approverHierarchy.getLevel() == 1){
	    		projects.setManager1(approverHierarchy.getApproverUserId().getId());
	    		projects.setManagerLevel1(approverHierarchy.getApproverUserId().getFirstName() +" " + approverHierarchy.getApproverUserId().getLastName());
	    	}
	    	if(approverHierarchy.getLevel() != null && approverHierarchy.getLevel() == 2){
	    		projects.setManager2(approverHierarchy.getApproverUserId().getId());
	    		projects.setManagerLevel2(approverHierarchy.getApproverUserId().getFirstName() +" " + approverHierarchy.getApproverUserId().getLastName());
	    	}
	    	
	    }
	    projects.setProjectApproverHierarchiesList(projectApproverHierarchies);
	    
	    Collection<User> userList = userService.getUsersByUserRolesAndOrgLocationId(loginOrgLocations.getId());
	    projects.setManagersList(userList);
	    if(projects.getManager1() != null){
	    	  List<User> userListwithoutmaneger1 = userService.getUsersByUserRolesAndOrgLocationIdAndNoCurrentUser(projects.getManager1(),loginOrgLocations.getId());
			    projects.setManagersListwithoutManager1(userListwithoutmaneger1);
	    }
	    
	    String[] approvers = new String[projectApproverHierarchies.size()];
	    int i = 0;
	    for(ProjectApproverHierarchy php : projectApproverHierarchies){
	    	approvers[i] = php.getApproverUserId().getFirstName()+"&"+php.getApproverUserId().getId();
	    	i++;
	    }
	    projects.setProjectManagerName(approvers);
	    
	    Collection<OrgRelations> orgClientList =orgRelationsService.getAllClientsByOrgLocationId(loginOrgLocations.getId()); 
	    String[] clientList = new String[orgClientList.size()];
	    int j = 0;
		for(OrgRelations ocl :orgClientList){
			if(projects.getOrgRelationId() !=null){
				if(projects.getOrgRelationId().getId().equals(ocl.getId())){
					projects.setRelationId(ocl.getId());
					projects.setClient(ocl.getClientLocationId().getOrg().getOrgName());
				}
			}
			clientList[j] = ocl.getClientLocationId().getOrg().getOrgName()+"&"+ocl.getId();
			j++;
		 }
		projects.setClientName(clientList);
		
		
		Integer projectMembersCount  = projectMembersService.getAllProjectMembersCountByProjectId(projectId);
		projects.setProjectMembersCount(projectMembersCount.toString());
		
		 resultJson = commonUtil.toJSON(projects);
		 return resultJson;
	}

	 /**
	  * update the project by project ID
	  * @author Laxman
	  * @param projectId
	  * @param json
	  * @param request
	  * @return String
	  * @throws Exception
	  */
	
	@Override
	public String getProjectDetailsUpdateByProjectId(Long projectId, User user, JSONObject jsonObj) throws Exception {
		
		 Date currentDate = new Date();
		 Date previousEndDate = null;
         Projects projects = getProjectById(projectId);
		 
		
	     
		        if(jsonObj.has("ProjectSequeneNumber")){
	        	  if(jsonObj.getString("ProjectSequeneNumber") != null){
			    	 projects.setProjectSequeneNumber(jsonObj.getString("ProjectSequeneNumber"));
			     }
		        }
		    	projects.setProjectName(jsonObj.getString("projectName"));
		    	if(jsonObj.getString("description") != null){
		    		projects.setDescription(jsonObj.getString("description"));
		    	}
		    	projects.setProjectStatus(Project_Status.getNameByValue(jsonObj.getString("projectStatusId")));
		    	
		    		if(jsonObj.has("endDateStr1")){
			    		String pattern = "yyyy-MM-dd";
			    	     SimpleDateFormat format = new SimpleDateFormat(pattern);
			    	     if(!jsonObj.getString("endDateStr1").isEmpty() && jsonObj.getString("endDateStr1") != null && !"NO DATA".equalsIgnoreCase(jsonObj.getString("endDateStr1"))){
					    		previousEndDate = projects.getEndDate();
					    		Date endDate = format.parse(jsonObj.getString("endDateStr1"));
								projects.setEndDate(endDate);
					    	}
			    	}
		    	
		    		if(jsonObj.has("clientId")){
			    		OrgRelations orgRelations = new OrgRelations();
			    		if(!jsonObj.getString("clientId").isEmpty()){
			    			orgRelations.setId(Long.valueOf(jsonObj.getString("clientId")));
					    	projects.setOrgRelationId(orgRelations);
			    		}
			    		
			    	}
		    	
		    	
		    	if(jsonObj.has("level1Manager")){
			    		Collection<ProjectApproverHierarchy> projectApproverHierarchies = projectApproverHierarchyService.getApproverUsersByProjectId(projectId);
						 for(ProjectApproverHierarchy prHierarchy :projectApproverHierarchies ){
							 if(prHierarchy.getLevel() == 1){
								 ProjectApproverHierarchy pah  = projectApproverHierarchyService.getByProjectApproverHierarchyId(prHierarchy.getId());
								 Long fieldValuelong = Long.valueOf(jsonObj.getString("level1Manager"));
								 User usr = userService.getUserById(fieldValuelong);
								 pah.setApproverUserId(usr);
								 projectApproverHierarchyService.updateProjectApproverHierarchy(pah);
							 }
			    	}
		    	}
		    	if(jsonObj.has("level2Manager")){
			    		 Collection<ProjectApproverHierarchy> projectApproverHierarchies = projectApproverHierarchyService.getApproverUsersByProjectId(projectId);
						 for(ProjectApproverHierarchy prHierarchy :projectApproverHierarchies ){
							 if(prHierarchy.getLevel() == 2){
								 ProjectApproverHierarchy pah  = projectApproverHierarchyService.getByProjectApproverHierarchyId(prHierarchy.getId());
								 Long fieldValuelong = Long.valueOf(jsonObj.getString("level2Manager"));
								 User usr = userService.getUserById(fieldValuelong);
								 pah.setApproverUserId(usr);
								 projectApproverHierarchyService.updateProjectApproverHierarchy(pah);
							 }
						 }
		    	}
			    projects.setUpdatedBy(user.getUserCode());
			    projects.setUpdatedDate(currentDate);
			    if(projects.getProjectStatus().equals(Project_Status.COMPLETED)){
			    	projects.setEndDate(currentDate);
			    	projects = commonUtil.terminateProject(projects);
			    	 updateProject(projects);
			    }
			    else if(projects.getEndDate() != null && currentDate.after(projects.getEndDate()) && !(projects.getEndDate().equals(currentDate))) {
			    	projects = commonUtil.terminateProject(projects);
			    	updateProject(projects);
			    }
			    else{
			    	projects = updateProject(projects);
			    	if((projects.getEndDate() == null) || (previousEndDate == null) || (previousEndDate!= null && projects.getEndDate() != null && !projects.getEndDate().equals(previousEndDate) && projects.getEndDate().after(previousEndDate))){
			    		 List<ProjectMembers> pmList = projectMembersService.getAllTerminatedProjectMembersByProjectIdAndterminatedStatus(projects.getId(), Primary.NO);
			    	     if(pmList != null && !pmList.isEmpty()){
			    	    	 for(ProjectMembers projectMember :pmList){
			    	    		 projectMember.setStatus(Constants.MEMBER_ACTIVE);
			    	    		 projectMember.setIsManuallyTerminated(null);
			    	    		 projectMember.setTerminateReason(null);
			    	    		 projectMember.setUpdatedDate(new Date());
			    	    		 projectMember.setUpdatedBy(user.getUserCode());
			    	    		 HashSet<ProjectMembersHistory> memberHistoryList = new HashSet<ProjectMembersHistory>();
							  	  ProjectMembersHistory pmh = projectMembersHistoryService.getProjectMembersRecentClosedByProjectMemberId(projectMember.getId());
								  if(pmh != null){
									  pmh.setProjectTerminationDate(projects.getEndDate());
									  pmh.setStatus(Constants.PROJECT_MEMBER_HISTORY_OPEN);
									  pmh.setUpdatedDate(new Date());
									  pmh.setUpdatedBy(user.getUserCode());
									  memberHistoryList.add(pmh);
									  projectMember.setMemberHistoryList(memberHistoryList);
								  }
								  projectMembersService.saveProjectMember(projectMember);
			    	    		 if(projectMember.getEmployee()!= null && projectMember.getEmployee().getUser() != null){
			    	    			 commonUtil.storeTimesheetSubmissionRecords(projectMember.getEmployee().getUser());
			    	    		 }
			    	    	 }
			    	     }
			    	}
			    	
			    }
		 
		 return null;
	}

	 /**
	  * Save Employee from project summary
	  * @param json
	  * @param request
	  * @return JSON
	  * @throws Exception
	  */
	
	@Override
	public String storeEmployeeInProjectDashboard(OrgLocations loginOrgLocation, User users, JSONObject jsonObj)
			throws Exception {
		// User saving
	 	String password = SourceLeadRandomCodeHelper.generateRandomPassword();
		User user = populateUser(jsonObj,users,password);
		User  usr=	userService.addUser(user);
		
		//Employee Saving
		Employees employee = populateEMployee(user,loginOrgLocation,jsonObj,users);
		Employees  emp=	employeesService.addEmployee(employee);
		
		// UserRoles saving
		UserRole userRole = populateUserRole(usr,loginOrgLocation,users);
 		userRoleService.addUserRole(userRole);
 		
		//send mail to user
		if((jsonObj.has("isMailSent") && !isNullOrEmpty(jsonObj.getString("isMailSent"))) && ("Y".equals(jsonObj.getString("isMailSent")))){
			userService.sendEmail(user,users,password);
		}
		
		return null;
	}

	/**
	 * populate User Data
	 * @param jsonObj
	 * @param users
	 * @param password
	 * @return User
	 * @throws JSONException
	 */
	private User populateUser(JSONObject jsonObj, User users, String password) throws JSONException {
		User user= new User();
		user.setFirstName(jsonObj.getString("employeeFirstName"));
		user.setLastName(jsonObj.getString("employeeLastName"));
		user.setEmail(jsonObj.getString("EmailId"));
		user.setUserType("Employee");
		user.setCreatedDate(new Date());
		user.setCreatedBy(users.getUserCode());
		user.setUserState(Status.NOT_LOGGEDIN);
		user.setUserName(jsonObj.getString("EmailId"));
		user.setPassword(sourceleadPasswordEncoder.encode(password));
		
		if((jsonObj.has("isMailSent") && !isNullOrEmpty(jsonObj.getString("isMailSent"))) && ("Y".equals(jsonObj.getString("isMailSent")))){
			user.setIsMailSent(Status.Y);
		}else{
			user.setIsMailSent(Status.N);
		}
		user.setUserCode(SourceLeadRandomCodeHelper.generateRandomUserCode());
		return user;
		
	}
	/**
	 * populate Employees Data	
	 * @param user
	 * @param loginOrgLocation
	 * @param jsonObj
	 * @param users
	 * @return Employees
	 * @throws JSONException
	 */
	private Employees populateEMployee(User user, OrgLocations loginOrgLocation, JSONObject jsonObj, User users) throws JSONException {
		Employees employee  = new Employees();
		employee.setUser(user);
		if(!jsonObj.getString("invoiceFrequency").isEmpty()){
			employee.setInvoiceFrequency(jsonObj.getString("invoiceFrequency"));
		}
		employee.setOrgLocations(loginOrgLocation);
		employee.setCreatedBy(users.getUserCode());
		employee.setStatus("ACTIVE");
		employee.setCreatedDate(new Date());
		return employee;
	}
		
	/**
	 * 	populate UserRole Data
	 * @param usr
	 * @param loginOrgLocation
	 * @param users
	 * @return
	 * @throws Exception
	 */
		private UserRole populateUserRole(User usr, OrgLocations loginOrgLocation, User users) throws Exception {
			String roleName = Constants.ROLE_USER;
			Roles roles = rolesService.getRolesByRoleName(roleName);
			UserRole userRole = new UserRole();
			userRole.setRoles(roles);
			userRole.setUser(usr);
			userRole.setOrgLocations(loginOrgLocation);
			userRole.setIsPrimary(1);
			userRole.setCreatedBy(users.getUserCode());
			userRole.setCreatedDate(new Date());
			return userRole;	
		}
			
	/**
	 * Checks String empty or null
	 * @param string
	 * @return boolean
	 */
	protected static boolean isNullOrEmpty(String str) {
		return (str == null || "".equals(str.trim()));
	}

	/**
	 * Get Employees list based on project id
	 * @param projectId
	 * @return JSON
	 * @throws Exception
	 */
	
	@Override
	public String getProjectEmployeeListByProjectId(Long projectId) throws Exception {
		
		Collection<ProjectMembers> projectMembersList = projectMembersService.getAllProjectMembersByProjectId(projectId);
		 ArrayList<ProjectMembers> projectMembersArrList = new ArrayList<ProjectMembers>();
		 for(ProjectMembers pm : projectMembersList){
			 pm.setEmpName(pm.getEmployee().getUser().getFirstName()+" "+pm.getEmployee().getUser().getLastName());
			 ProjectMembersHistory pmh = projectMembersHistoryService.getProjectMembersOpenHistoryByProjectMemberId(pm.getId());
			 if(pmh != null && pmh.getProjectAssignedDate()!=null){
				 pm.setProjectAssignedDateStr(Constants.DF_YMD.format(pmh.getProjectAssignedDate()));
            }
       	 if(pmh != null && pmh.getProjectTerminationDate()!=null){
       		pm.setProjectEndDateStr(Constants.DF_YMD.format(pmh.getProjectTerminationDate()));
       	 }
       	 //to set actual project end date
       	 if(pm.getProjects().getEndDate() != null){
        		pm.setEndDate(Constants.DF_YMD.format(pm.getProjects().getEndDate()));
        	 }
       	 if(pm.getEmployee() != null && pm.getEmployee().getUser() != null && pm.getEmployee().getUser().getId() != null){
          		pm.setUserId(pm.getEmployee().getUser().getId().toString());
          	 }
       	 if(pm.getEmployee()!=null){

                Float draftTimesheetsSubmissionsCountAsEmployee = submissionApproverHierarchyService.getTotalDraftAndSubmittedHoursByuserIdAndProjectIdForEmployee(pm.getEmployee().getUser().getId(), projectId, "DRAFT");
    	        Float draftTimesheetsSubmissionsCountAsApprover = submissionApproverHierarchyService.getTotalDraftAndSubmittedHoursByuserIdAndProjectIdForApprover(pm.getEmployee().getUser().getId(), projectId, "DRAFT");
    	        Float draftTimesheetsSubmissionsCount =  draftTimesheetsSubmissionsCountAsEmployee + draftTimesheetsSubmissionsCountAsApprover;
    	        pm.setDraftHrs(draftTimesheetsSubmissionsCount.toString());

    	        Float submittedTimesheetsSubmissionsCountAsEmployee = submissionApproverHierarchyService.getTotalDraftAndSubmittedHoursByuserIdAndProjectIdForEmployee(pm.getEmployee().getUser().getId(), projectId, "SUBMITTED");
    	        Float submittedTimesheetsSubmissionsCountAsApprover = submissionApproverHierarchyService.getTotalDraftAndSubmittedHoursByuserIdAndProjectIdForApprover(pm.getEmployee().getUser().getId(), projectId, "SUBMITTED");
    	        Float submittedTimesheetsSubmissionsCount =  submittedTimesheetsSubmissionsCountAsEmployee + submittedTimesheetsSubmissionsCountAsApprover;
    	        pm.setSubmittedHrs(submittedTimesheetsSubmissionsCount.toString());
    			
    		    Float approvalTimesheetsSubmissionsCountAsEmployee = submissionApproverHierarchyService.getTotalApprovedOrRejectedHoursByuserIdAndProjectIdForEmployee(pm.getEmployee().getUser().getId(), projectId, "APPROVED");
    		    Float approvalTimesheetsSubmissionsCountAsApprover = submissionApproverHierarchyService.getTotalApprovedOrRejectedHoursByuserIdAndProjectIdForApprover(pm.getEmployee().getUser().getId(), projectId, "APPROVED");
    	        Float approvalTimesheetsSubmissionsCount =  approvalTimesheetsSubmissionsCountAsEmployee + approvalTimesheetsSubmissionsCountAsApprover;
    	        pm.setApprovedHrs(approvalTimesheetsSubmissionsCount.toString());
    			
    			Float rejectedTimesheetsSubmissionsCountAsEmployee = submissionApproverHierarchyService.getTotalApprovedOrRejectedHoursByuserIdAndProjectIdForEmployee(pm.getEmployee().getUser().getId(), projectId, "REJECTED");
    		    Float rejectedTimesheetsSubmissionsCountAsApprover = submissionApproverHierarchyService.getTotalApprovedOrRejectedHoursByuserIdAndProjectIdForApprover(pm.getEmployee().getUser().getId(), projectId, "REJECTED");
    		    Float rejectedTimesheetsSubmissionsCount =  rejectedTimesheetsSubmissionsCountAsEmployee + rejectedTimesheetsSubmissionsCountAsApprover;
    		    pm.setRejectedHrs(rejectedTimesheetsSubmissionsCount.toString());
                
                
       	 }	
			 projectMembersArrList.add(pm);
		 }
		 ObjectMapper mapper = new ObjectMapper();
		 String jsonInString = mapper.writeValueAsString(projectMembersArrList);
		 return jsonInString;
	}

	/**
	  * This method for save the  project by json
	  * @author Laxman
	  * @param json
	  * @param request
	  * @return value is Project pojo
	  * @throws Exception
	  */
	@Override
	public String saveProjectByUserOrgLocationsJson(User user, OrgLocations loginOrgLocation, String loginOrgCode,
			JSONObject jsonObj) throws Exception {

		String resultJson = null;
		
		String pattern = "yyyy-MM-dd";
        SimpleDateFormat format = new SimpleDateFormat(pattern);
	 	
    	Projects projects = new Projects();
    	projects.setProjectSequeneNumber(jsonObj.getString("ProjectSequeneNumber"));
    	projects.setProjectName(jsonObj.getString("ProjectName"));
    	projects.setDescription(jsonObj.getString("ProjectDescription"));
    	if(jsonObj.getString("StartDate") != null && jsonObj.getString("StartDate") != "" && jsonObj.getString("StartDate") != "undefined"){
    		Date startDate = format.parse(jsonObj.getString("StartDate"));
	    	projects.setStartDate(startDate);
    	}
    	if(!jsonObj.getString("EndDate").isEmpty()){
    		Date endDate = format.parse(jsonObj.getString("EndDate"));
	    	projects.setEndDate(endDate);
    	}
    	projects.setProjectStatus(Project_Status.getNameByValue((jsonObj.getString("Satatus"))));
    	projects.setCreatedDate(new Date());
    	projects.setUpdatedDate(new Date());
    	if(user.getUserCode() != null){
    		projects.setCreatedBy(user.getUserCode());	
    	}
    	projects.setOrgLocationsId(loginOrgLocation);
    	
    	// Saving of Client by bringing OrgRelation id based on Client OrgId
	    String clientOrgCode = jsonObj.getString("selectClient");	
	    if(!clientOrgCode.isEmpty() && clientOrgCode!=null){
	    	  OrgLocations clientOrgLocations = orgLocationService.getOrgLocationsByOrgCode(clientOrgCode);
	    	  if(clientOrgLocations!=null){
	    		  OrgRelations  orgRelations = orgRelationsService.getOrgRelationIdByOrgLocationIdAndClientLocationId(loginOrgLocation.getId(), clientOrgLocations.getId());
		    		  if(orgRelations!=null){
		    			  projects.setOrgRelationId(orgRelations);
		    		  }
	    	  }
	    	  
	    }
	    
	    if((jsonObj.has("isMailToTeamMembers") && !isNullOrEmpty(jsonObj.getString("isMailToTeamMembers"))) && ("Y".equals(jsonObj.getString("isMailToTeamMembers")))){
			projects.setIsMailToTeamMembers(Status.Y);
		}else{
			projects.setIsMailToTeamMembers(Status.N);
		}
	    
	  if((jsonObj.has("isMailToProjectManager") && !isNullOrEmpty(jsonObj.getString("isMailToProjectManager"))) && ("Y".equals(jsonObj.getString("isMailToProjectManager")))){
		  projects.setIsMailToProjectManager(Status.Y);
		}else{
			projects.setIsMailToProjectManager(Status.N);
		}
    	
    	Projects updatedProjects = saveProject(projects);
    	
    	Long[] ids = new Long[1000];
    	
    	ProjectApproverHierarchy pah1 = new ProjectApproverHierarchy();
    	pah1.setProjectId(updatedProjects);
    	User approverUserId1 = new User();
    	
        if(jsonObj.has("level1Manager")){
		    	if(jsonObj.getString("level1Manager") != null && !jsonObj.getString("level1Manager").isEmpty()){
		    		approverUserId1.setId(Long.parseLong(jsonObj.getString("level1Manager")));
			    	pah1.setApproverUserId(approverUserId1);
			    	pah1.setLevel(1);
			    	ProjectApproverHierarchy saveProjectApproverHierarchy = projectApproverHierarchyService.saveProjectApproverHierarchy(pah1);
			    	ids[0] = saveProjectApproverHierarchy.getApproverUserId().getId();
			    	
		    	}
	    	}
    	//send mail to assigned Project Manager
    	String teamMembersList = "";
		
    	User approverUserId2 = new User();
		 if(jsonObj.has("level2Manager")){ 	
			if(!jsonObj.getString("level2Manager").isEmpty()){
				ProjectApproverHierarchy pah2 = new ProjectApproverHierarchy();
				pah2.setProjectId(updatedProjects);
				approverUserId2.setId(Long.parseLong(jsonObj.getString("level2Manager")));
				pah2.setApproverUserId(approverUserId2);
				pah2.setLevel(2);
				ProjectApproverHierarchy projectApproverHierarchy = projectApproverHierarchyService.saveProjectApproverHierarchy(pah2);
				ids[1] = projectApproverHierarchy.getApproverUserId().getId();
			}
		 }
	 
	//admin as approver hierarchy ..
	Collection<User> uLsit = userService.getUsersByUserRoles(loginOrgLocation.getId());
	CopyOnWriteArrayList<User> userLsit = new CopyOnWriteArrayList<User>(uLsit);
	if(userLsit != null && !userLsit.isEmpty()){
	for(User userRole : userLsit){
		  if(ids[0] != null &&  ids[0].equals(userRole.getId())){
			  userLsit.remove(userRole);
		  }
		  else if (ids[1] != null && ids[1].equals(userRole.getId())) {
			  userLsit.remove(userRole);
		  }else{
			    ProjectApproverHierarchy pah3 = new ProjectApproverHierarchy();
			    pah3.setProjectId(updatedProjects);
				pah3.setApproverUserId(userRole);
				pah3.setLevel(2);
			    ProjectApproverHierarchy projectApproverHierarchy = projectApproverHierarchyService.saveProjectApproverHierarchy(pah3);
		  } 
		  
	}
	}
    	
	
    	String maxAdminTimeTypeRowNum = jsonObj.getString("maxTsRowNum");
		int maxCount = Integer.parseInt(maxAdminTimeTypeRowNum);

		for (int c = 1; c < maxCount; c++) {
			ProjectTasks  tasks  = new ProjectTasks();
			if(jsonObj.has(c+"taskName")){
				if(jsonObj.has(c+"taskDescription")){
					tasks.setDescription(jsonObj.getString(c+"taskDescription"));
				}
					tasks.setTaskName(jsonObj.getString(c+"taskName"));
					tasks.setCreatedDate(new Date());
					tasks.setUpdatedDate(new Date());
					tasks.setProjectId(projects);
			ProjectTasks savedtasks = tasksService.addTasks(tasks);
		  }
		}
		
		if (jsonObj.has("employeeMaxCount")){
			String employeeMaxCount = jsonObj.getString("employeeMaxCount");
			if(!jsonObj.getString("employeeMaxCount").isEmpty()){
					int empMaxCount = Integer.parseInt(employeeMaxCount);
					for (int c = 1; c < empMaxCount; c++) {
						
						ProjectMembers pm = new ProjectMembers();
						if(StringUtils.isNotEmpty(jsonObj.getString("employeeId"+c))){
							Employees emp = employeesService.getEmployee(jsonObj.getString("employeeId"+c));
						    if(emp != null){
						    	pm.setEmployee(emp);
						    	
						        HashSet<ProjectMembersHistory> pmhList = new HashSet<ProjectMembersHistory>();
									
								ProjectMembersHistory pmh = new ProjectMembersHistory();
								pmh.setProjectMemberId(pm);
								pmh.setStatus(Constants.PROJECT_MEMBER_HISTORY_OPEN);
					            if(StringUtils.isNotEmpty(jsonObj.getString("employeestartDate"+c))){
					            	Date currentStartDate = format.parse(jsonObj.getString("employeestartDate"+c));
					            	 pmh.setProjectAssignedDate(currentStartDate);
					            }else{
					            	  pmh.setProjectAssignedDate(projects.getStartDate());
					            }
					            
					            if(StringUtils.isNotEmpty(jsonObj.getString("employeeendDate"+c))){
					            	Date currentEndDate = format.parse(jsonObj.getString("employeeendDate"+c));
					            	pmh.setProjectTerminationDate(currentEndDate);
					            }
					            else{
					            	 pmh.setProjectTerminationDate(projects.getEndDate());
						    	}

					            pmh.setCreatedDate(new Date());
								pmh.setCreatedBy(user.getUserCode());
								pmh.setUpdatedDate(new Date());
								pmh.setUpdatedBy(user.getUserCode());
								pmhList.add(pmh);
								
								pm.setProjects(projects);
								pm.setStatus(Constants.MEMBER_ACTIVE);
								pm.setCreatedDate(new Date());
								pm.setUpdatedDate(new Date());
								pm.setCreatedBy(user.getUserCode());
								pm.setUpdatedBy(user.getUserCode());
								pm.setMemberHistoryList(pmhList);
								projectMembersService.saveProjectMember(pm);
								
								//send mail to assigned team members
								if((jsonObj.has("isMailToTeamMembers") && !isNullOrEmpty(jsonObj.getString("isMailToTeamMembers"))) && ("Y".equals(jsonObj.getString("isMailToTeamMembers")))){
									sendEmailToTeamMembers(projects, emp, user, approverUserId1, loginOrgLocation, loginOrgCode);
									
								}
								
								commonUtil.storeTimesheetSubmissionRecords(emp.getUser());
								
								/**
							    * This teamMembersList string to store all team members for mail to project manager.
							    */
								String teamMember = "";
								teamMember = emp.getUser().getFirstName()+" "+emp.getUser().getLastName();
								teamMembersList = teamMembersList+teamMember+"<br/>";
						    }
						}
				    }
			 }
	    }
		
		//send mail to assigned Project Manager
		if((jsonObj.has("isMailToProjectManager") && !isNullOrEmpty(jsonObj.getString("isMailToProjectManager"))) && ("Y".equals(jsonObj.getString("isMailToProjectManager")))){
			if(approverUserId1 != null && user.getId() != approverUserId1.getId()){
				sendEmailToProjectManager(projects, user, approverUserId1, loginOrgLocation, teamMembersList, loginOrgCode);
			}
			if(!jsonObj.getString("level2Manager").isEmpty() && approverUserId2 != null && user.getId() != approverUserId2.getId()){
				sendEmailToProjectManager(projects, user, approverUserId2, loginOrgLocation, teamMembersList, loginOrgCode);
			}
		}
		
		Collection<ProjectTasks> tasksList = tasksService.getAllTasks();
		
		 resultJson = commonUtil.toJSON(tasksList);
		 
		 return resultJson;
	}
	
	  /**
		  * This method for send Email to project manager
		  * @return
		  * @throws Exception
		  * @author  venkatKesav
		  * @since   2016-07-25
		  */
		  private void sendEmailToProjectManager(Projects projects, User loginUser, User approverUserId1, OrgLocations loginOrgLocation, String teamMembersList, String loginOrgCode) throws Exception {
			  	 User projectManager = userService.getUserById(approverUserId1.getId());
			  	 
			  	 if(isNullOrEmpty(teamMembersList) && ("undefined".equals(teamMembersList))){
			  		teamMembersList = "-";
			  	 }
			  	
			     EmailMessage emailMessage = new EmailMessage();
				 emailMessage.setFirstName(projectManager.getFirstName());
				 emailMessage.setLastName(projectManager.getLastName());
				 emailMessage.setToEmail(projectManager.getEmail());
				 emailMessage.setFromEmail("peopleaxisindia@gmail.com");
				 emailMessage.setFromName(loginUser.getFirstName()+" "+loginUser.getLastName());

				 //setting the class filed values
				 Map<String, String> globalVarsMap = new HashMap<String, String>();
				 globalVarsMap.put("loginUserFirstName",loginUser.getFirstName());
				 globalVarsMap.put("loginUserLastName",loginUser.getLastName());
				 globalVarsMap.put("managerFirstName",projectManager.getFirstName());
				 globalVarsMap.put("managerLastName",projectManager.getLastName());
				 globalVarsMap.put("teamMembers",teamMembersList);
				 globalVarsMap.put("projectName", projects.getProjectName());
				 globalVarsMap.put("orgName",loginOrgLocation.getOrg().getOrgName());
				 globalVarsMap.put("loginURL","<a href='"+sourceleadUrl+"/"+"' style='text-decoration:none; '><div style=' width:220px; border-radius:30px; background-color:#214b90; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Login</div></a>");
				 
				 //setting the Recipient information
				 ArrayList<EmailRecipient> emailRecipientList = new ArrayList<EmailRecipient>();
				 EmailRecipient emailRecipient = new EmailRecipient();
				 emailRecipient.setEmail(emailMessage.getToEmail());
				 emailRecipient.setName(emailMessage.getFirstName()+" "+emailMessage.getLastName());
				 emailRecipientList.add(emailRecipient);
				 
				 //getting the current EmailScenario by EmailScenario unique name and entity type
				 EmailScenario  emailScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.MAIL_PROJECT_MANAGER,Constants.ENTITY_TYPE_EMAIL);
				
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
				 
				 
				//calling the sendNotification method of emailScenerioUtil
				EmailScenario  notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.MAIL_PROJECT_MANAGER,Constants.ENTITY_TYPE_NOTIFICATION);
				String notificationBody = emailScenerioUtil.buildBody(notificationScenario.getBody(),globalVarsMap);
				notificationScenario.setBody(notificationBody);
				notificationScenario.setOrgCode(loginOrgCode);
				//String url = null;
				String actionLink = "";
				emailScenerioUtil.sendNotification(actionLink, loginUser, projectManager, loginOrgLocation, notificationScenario);
			}
		  
		  /**
			  * This method for send Email to team member
			  * @return
			  * @throws Exception
			  * @author  venkatKesav
			  * @since   2016-07-22
			  */
			  private void sendEmailToTeamMembers(Projects projects, Employees emp, User loginUser, User approverUserId1, OrgLocations loginOrgLocation, String loginOrgCode) throws Exception {
				  	 User projectManager = userService.getUserById(approverUserId1.getId());
				  	
				     EmailMessage emailMessage = new EmailMessage();
					 emailMessage.setFirstName(emp.getUser().getFirstName());
					 emailMessage.setLastName(emp.getUser().getLastName());
					 emailMessage.setToEmail(emp.getUser().getEmail());
					 emailMessage.setFromEmail("peopleaxisindia@gmail.com");
					 emailMessage.setFromName(loginUser.getFirstName()+" "+loginUser.getLastName());

					 //setting the class filed values
					 Map<String, String> globalVarsMap = new HashMap<String, String>();
					 globalVarsMap.put("loginUserFirstName",loginUser.getFirstName());
					 globalVarsMap.put("loginUserLastName",loginUser.getLastName());
					 globalVarsMap.put("managerFirstName",projectManager.getFirstName());
					 globalVarsMap.put("managerLastName",projectManager.getLastName());
					 globalVarsMap.put("employeeFirstName",emailMessage.getFirstName());
					 globalVarsMap.put("employeeLastName",emailMessage.getLastName());
					 globalVarsMap.put("projectName", projects.getProjectName());
					 globalVarsMap.put("orgName",loginOrgLocation.getOrg().getOrgName());
					 globalVarsMap.put("loginURL","<a href='"+sourceleadUrl+"/"+"' style='text-decoration:none; '><div style=' width:220px; border-radius:30px; background-color:#214b90; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Login</div></a>");
					 
					 //setting the Recipient information
					 ArrayList<EmailRecipient> emailRecipientList = new ArrayList<EmailRecipient>();
					 EmailRecipient emailRecipient = new EmailRecipient();
					 emailRecipient.setEmail(emailMessage.getToEmail());
					 emailRecipient.setName(emailMessage.getFirstName()+" "+emailMessage.getLastName());
					 emailRecipientList.add(emailRecipient);
					 
					 //getting the current EmailScenario by EmailScenario unique name and entity type
					 EmailScenario  emailScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.MAIL_TEAM_MEMBERS,Constants.ENTITY_TYPE_EMAIL);
					
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
					 
					 
					//calling the sendNotification method of emailScenerioUtil
					EmailScenario  notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.MAIL_TEAM_MEMBERS,Constants.ENTITY_TYPE_NOTIFICATION);
					String notificationBody = emailScenerioUtil.buildBody(notificationScenario.getBody(),globalVarsMap);
					notificationScenario.setBody(notificationBody);
					notificationScenario.setOrgCode(loginOrgCode);
					//String url = null;
					String actionLink = "";
					emailScenerioUtil.sendNotification(actionLink, loginUser, emp.getUser(), loginOrgLocation, notificationScenario);
				}
			  
}
