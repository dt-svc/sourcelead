package com.bis.sourcelead.service.impl;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

import javax.mail.internet.InternetAddress;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.BooleanUtils;
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

import com.bis.sourcelead.SourceleadPasswordEncoder;
import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.ProjectApproverHierarchyDao;
import com.bis.sourcelead.dao.ProjectsDao;
import com.bis.sourcelead.elk.index.ProjectsIndex;
import com.bis.sourcelead.elk.index.helper.ProjectsIndexHelper;
import com.bis.sourcelead.elk.setup.SourceleadELKBaseIndexer;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.EmailAttachment;
import com.bis.sourcelead.entity.EmailMessage;
import com.bis.sourcelead.entity.EmailScenario;
import com.bis.sourcelead.entity.Employees;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgRelations;
import com.bis.sourcelead.entity.ProjectApproverHierarchy;
import com.bis.sourcelead.entity.ProjectDocuments;
import com.bis.sourcelead.entity.ProjectMembers;
import com.bis.sourcelead.entity.ProjectMembersHistory;
import com.bis.sourcelead.entity.ProjectTasks;
import com.bis.sourcelead.entity.Projects;
import com.bis.sourcelead.entity.Roles;
import com.bis.sourcelead.entity.TimesheetSubmissions;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserRole;
import com.bis.sourcelead.enums.Primary;
import com.bis.sourcelead.enums.Project_Status;
import com.bis.sourcelead.enums.Status;
import com.bis.sourcelead.model.ProjectDocumentBO;
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
import com.bis.sourcelead.service.TimesheetSubmissionsService;
import com.bis.sourcelead.service.UserRoleService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.EmailScenerioUtil;
import com.bis.sourcelead.util.SourceLeadRandomCodeHelper;
import com.bis.sourcelead.util.SourceLeadSessionManager;
import com.bis.sourcelead.util.UniversalSearchHelper;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ProjectsServiceImpl implements ProjectsService{
	
	private static final Logger logger = LoggerFactory.getLogger(ProjectsServiceImpl.class);

	@Value("${defaultFromEmailAddressForProd}")
	private String defaultFromEmailAddressForProd;
	
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
	 
	 @Autowired
	 private TimesheetSubmissionsService timesheetSubmissionsService;
	 
	 @Autowired
	 private ProjectsIndexHelper projectsIndexHelper;
	 
	 @Autowired
	 private SourceleadELKBaseIndexer<ProjectsIndex> sourceleadELKBaseIndexer;
	 
	 @Autowired
	 private UniversalSearchHelper universalSearchHelper;
    
	 
	 
	 @Override
		public String getProjectId(OrgLocations orgLocations) {
			
	    	Long number = 000000001L;
			String candidateId = null;
			Projects checkNumber = projectsDao.checkProjectIdNumberInDatebase(orgLocations.getId());
			if(checkNumber == null || checkNumber.getGeneratedId().isEmpty()){
				candidateId = number.toString();
			}else{
			String canIdNumber = checkNumber.getGeneratedId();
			String canIdNum = canIdNumber.substring(2, canIdNumber.length());
			Long canId = Long.parseLong(canIdNum)+1;
				candidateId = canId.toString();
			}
			
	        final DecimalFormat decimalFormat = new DecimalFormat("000000000");                
	        String ConvetredCandidatesId = decimalFormat.format(Long.parseLong(candidateId));
	        StringBuffer sb = new StringBuffer();
			return sb.append(Constants.PROJECT_ENTITY).append(ConvetredCandidatesId).toString();
			
		}
	 
	@Override
	public Projects saveProject(Projects projects) throws Exception{
		logger.debug("In ProjectsServiceImpl class saveProject method...");
		projects.setUpdatedDate(commonUtil.currentDate());
		return projectsDao.saveProject(projects);
	}

	@Override
	public Projects updateProject(Projects projects) throws Exception {
		logger.debug("In ProjectsServiceImpl class updateProject method...");
		projects.setUpdatedDate(commonUtil.currentDate());
		return projectsDao.updateProject(projects);
	}

	@Override
	public Projects getProjectById(Long id) throws Exception {
		logger.debug("In ProjectsServiceImpl class getProject method...");
		return projectsDao.getProjectById(id);
	}
	
	@Override
	public List<Long> getProjectIdsByOrgLocationsId(Long orgLocationsId) throws Exception {
		logger.debug("In ProjectsServiceImpl class getProjectIdsByOrgLocationsId method...");
		return projectsDao.getProjectIdsByOrgLocationsId(orgLocationsId);
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
	public List<Projects> getAllProjectsEndDateGraterthanCurrentDate() throws Exception {
		logger.debug("In ProjectsServiceImpl class getAllProjectsEndDateGraterthanCurrentDate method...");
		return projectsDao.getAllProjectsEndDateGraterthanCurrentDate();
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
	public String storeProject(JSONObject jsonObj, List<ProjectDocumentBO> projectDocumentBOList) throws Exception {
		    
		  
		String resultJson = null;
	 	OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
	 	User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
	 	String loginOrgCode = (String) SourceLeadSessionManager.getSessionAttribute(request, "orgCode");
	// 	String pattern = "dd-MM-yyyy";
        SimpleDateFormat format = loginOrgLocation.getLocFullFormDateFormatter();
	 	
        
        
    	Projects projects = new Projects();
    	projects.setProjectSequeneNumber(jsonObj.getString("ProjectSequeneNumber").trim());
    	projects.setProjectName(jsonObj.getString("ProjectName").trim());
    	
    	
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
    	
    	
		if(jsonObj.has("invoiceFrequency") && jsonObj.getString("invoiceFrequency") != null && !"".equalsIgnoreCase(jsonObj.getString("invoiceFrequency"))){
			projects.setInvoiceFrequency(jsonObj.getString("invoiceFrequency"));
	    }else{
	    	projects.setInvoiceFrequency(Constants.WEEKLY);
	    }
		if(jsonObj.has("timeSheetFrequency") && StringUtils.isNotEmpty(jsonObj.getString("timeSheetFrequency"))){
			projects.setTimesheetFrequency(jsonObj.getString("timeSheetFrequency"));
		}
		
		if(jsonObj.has("EstimatedHours") && StringUtils.isNotEmpty(jsonObj.getString("EstimatedHours"))){
			projects.setEstimatedHours(jsonObj.getDouble("EstimatedHours"));
		}
		if(jsonObj.has("EstimatedBudget") && StringUtils.isNotEmpty(jsonObj.getString("EstimatedBudget"))){
			projects.setEstimatedBudget(jsonObj.getDouble("EstimatedBudget"));
		}
		
		if(jsonObj.has("isAllowFreelanceDevelopers") && StringUtils.isNotEmpty(jsonObj.getString("isAllowFreelanceDevelopers"))){
			projects.setAllowFreeLance(BooleanUtils.toBoolean(jsonObj.getString("isAllowFreelanceDevelopers"),"Y",""));
		}
		if(jsonObj.has("isAllowRemoteWork") && StringUtils.isNotEmpty(jsonObj.getString("isAllowRemoteWork"))){
			projects.setAllowRemote(BooleanUtils.toBoolean(jsonObj.getString("isAllowRemoteWork"),"Y",""));
		}
		if(jsonObj.has("isAllowOverTime") && StringUtils.isNotEmpty(jsonObj.getString("isAllowOverTime"))){
			projects.setAllowOvertime(BooleanUtils.toBoolean(jsonObj.getString("isAllowOverTime"),"Y",""));
		}
		if(jsonObj.has("isAllowOffshoreResource") && StringUtils.isNotEmpty(jsonObj.getString("isAllowOffshoreResource"))){
			projects.setAllowOffShore(BooleanUtils.toBoolean(jsonObj.getString("isAllowOffshoreResource"),"Y",""));
		}
		if(jsonObj.has("isTimeEntryAfterDeadline") && StringUtils.isNotEmpty(jsonObj.getString("isTimeEntryAfterDeadline"))){
			projects.setAllowTimeEntryAfterDeadLine(BooleanUtils.toBoolean(jsonObj.getString("isTimeEntryAfterDeadline"),"Y",""));
		}
		if(jsonObj.has("isAllowBidding") && StringUtils.isNotEmpty(jsonObj.getString("isAllowBidding"))){
			projects.setAllowBidding(BooleanUtils.toBoolean(jsonObj.getString("isAllowBidding"),"Y",""));
		}
		
		if(jsonObj.has("flatPercentage") && StringUtils.isNotEmpty(jsonObj.getString("flatPercentage"))){
			projects.setFlatPercent(jsonObj.getDouble("flatPercentage"));
		}
		
		if(jsonObj.has("flatPercentageId") && StringUtils.isNotEmpty(jsonObj.getString("flatPercentageId"))){
			projects.setOvertimeRateType(jsonObj.getString("flatPercentageId"));
		}
		
		List<ProjectDocuments> projDocDBList = projectDocumentBOList.stream().map(bo -> new ProjectDocuments(bo.getFileName(), bo.getFileType(),bo.getFileContents(),projects,bo.getStatus())).collect(Collectors.toList());
		
		projects.getProjectDocuments().addAll(projDocDBList);
	
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
	    String clientOrgLocationId = jsonObj.getString("selectClient");	
	    if(!clientOrgLocationId.isEmpty() && clientOrgLocationId !=null){
	    	  OrgLocations clientOrgLocations = orgLocationService.getOrgLocations(Long.valueOf(clientOrgLocationId));
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
    	
	    if((jsonObj.has("isTimesheetAttachmentRequired")) && StringUtils.isNotEmpty(jsonObj.getString("isTimesheetAttachmentRequired")) && ("Y".equals(jsonObj.getString("isTimesheetAttachmentRequired")))){
	    	projects.setIsTimesheetAttachmentRequired(Constants.RECORD_ACTIVE);
	    }else{
	    	projects.setIsTimesheetAttachmentRequired(Constants.RECORD_INACTIVE);
	    }

    	//prepare ProjectApproverHierarchy
    	Set<ProjectApproverHierarchy> projectApproverHierarchyList = projectApproverHierarchyService.prepareProjectApproverHierarchyListByJson(jsonObj,request,projects);
    	projects.setProjectApprovers(projectApproverHierarchyList);
		
		//prepare project task set...
		/*tasksService.*/
    	/*User user1 = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");*/
    	
    	//save the default tasks
		Set<ProjectTasks>  projectTasksList = getDefaultTasksForProject(projects);
		projects.setProjectTasks(projectTasksList);
		
		// generated id for project
		
		projects.setGeneratedId(getProjectId(loginOrgLocation));
		//prepare projectmembers list...
		Set<ProjectMembers>  projectMemberList = projectMembersService.prepareProjectMembersListByJson(jsonObj,request, projects);
		projects.setProjectMembers(projectMemberList);
		projects.setProjectUUID(SourceLeadRandomCodeHelper.generateRandomUUID());
		Projects updatedProjects = saveProject(projects);
		
		/*
		 * Saving Project DB data as ElasticSearch Documents
		 */
		saveOrUpdateProjectElkDocument(updatedProjects);
		
		/**
		 * this block is for submissions for project member
		*/
		/*if (jsonObj.has("employeeMaxCount")){
		String employeeMaxCount = jsonObj.getString("employeeMaxCount");
		if(StringUtils.isNotEmpty(employeeMaxCount)){
		int empMaxCount = Integer.parseInt(employeeMaxCount);
			for (int c = 1; c < empMaxCount; c++) {
			Employees emp = employeesService.getEmployee(jsonObj.getString("employeeId"+c));
			commonUtil.storeTimesheetSubmissionRecords(emp.getUser());
			}
		 }
		}*/
		
		
		/**
		 * this block is for submissions for project member
		 * commented by Prasad salina on 11/01/17
		*/
		/*Collection<ProjectMembers> projectMembersList = projectMembersService.getAllProjectMembersByProjectId(updatedProjects.getId());
		if (projectMembersList != null && !projectMembersList.isEmpty()){
			for (ProjectMembers pm : projectMembersList) {
				if(pm.getEmployee() != null){
					commonUtil.storeTimesheetSubmissionRecords(pm.getEmployee().getUser());
				}
		  	  
			}
		}*/
		
		/*
		 * This block is for Email To Managers and Project Members
		 * 
		 */
		
		//send mail to assigned team members
		if((jsonObj.has("isMailToTeamMembers") && !isNullOrEmpty(jsonObj.getString("isMailToTeamMembers"))) && ("Y".equals(jsonObj.getString("isMailToTeamMembers")))){
			
			User  approverUserId2  = null;
			User  approverUserId1 = null;
			if( null !=  jsonObj.getString("level1Manager")  && jsonObj.has("level1Manager") && !jsonObj.getString("level1Manager").isEmpty()){
				  String level1UserDetails[] = jsonObj.getString("level1Manager").split(",");
				  approverUserId1 =   userService.getUserById(Long.valueOf(level1UserDetails[0]));
			if(jsonObj.has("level2Manager") && null != jsonObj.getString("level2Manager") &&jsonObj.has("level2Manager")  && !jsonObj.getString("level2Manager").isEmpty()){
		      approverUserId2 =   userService.getUserById(Long.valueOf(jsonObj.getString("level2Manager")));
			}
			
			for(ProjectMembers projectMembers : projectMemberList){
				if(null != projectMembers.getEmployee()){
					Employees emp = projectMembers.getEmployee();
						
					//if  we have   both level1 and Level2 ProjectManagers
					if( null != approverUserId1 && null != approverUserId2){
						  if(approverUserId1.getId().longValue() != emp.getUser().getId().longValue() && approverUserId2.getId().longValue() != emp.getUser().getId().longValue()){
							  sendEmailToTeamMembers(projects, emp, user, approverUserId1, loginOrgLocation, loginOrgCode);
				    }
						//if  we have   both level1 only One ProjectManagers
					}else if(approverUserId1.getId().longValue() != emp.getUser().getId().longValue()){
						  sendEmailToTeamMembers(projects, emp, user, approverUserId1, loginOrgLocation, loginOrgCode);
					}   
						
					}
				}
			}
		}
		
		
		if((jsonObj.has("isMailToProjectManager") && StringUtils.isNotBlank(jsonObj.getString("isMailToProjectManager"))) && ("Y".equals(jsonObj.getString("isMailToProjectManager")))){
			if(jsonObj.has("level1Manager") && null != jsonObj.getString("level1Manager") && !jsonObj.getString("level1Manager").isEmpty()){
				User  approverUserId1 =new User();
				String level1UserDetails[] = jsonObj.getString("level1Manager").split(",");
				approverUserId1.setId(Long.valueOf(level1UserDetails[0]));
				if(approverUserId1 != null && user.getId() != approverUserId1.getId()){
					sendEmailToProjectManager(projects, user, approverUserId1, loginOrgLocation, "", loginOrgCode);
				}
				
			}
			

			/*
			 * This is Mailing for Level2 Manager who was assigned to Project
			 * 
			 */
			
			if(jsonObj.has("level2Manager") && null != jsonObj.getString("level2Manager") && !jsonObj.getString("level2Manager").isEmpty()){
				User  approverUserId2 =new User();
				approverUserId2.setId(Long.valueOf(jsonObj.getString("level2Manager")));
				if(!jsonObj.getString("level2Manager").isEmpty() && null !=  approverUserId2 && user.getId() != approverUserId2.getId()){
					sendEmailToProjectManager(projects, user, approverUserId2, loginOrgLocation, "", loginOrgCode);
				}
			}
		}
		 
	  SourceLeadSessionManager.setSessionAttribute(request, "projectCreateMsg", "Successfully you have created a project");
	  return "projectDashboard";
		 
		
	}
	
	/**
	 * @author Laxman
	 * setting default tasks for every project
	 * @param projects
	 * @return set
	 */
	private Set<ProjectTasks> getDefaultTasksForProject(Projects projects) {
		Set<ProjectTasks> set = new HashSet<>();
		String[] defaultTasks = Constants.DEFAULT_TASKS;
		for (String taskName : defaultTasks) {
			ProjectTasks projectTasks = new ProjectTasks();
			projectTasks.setTaskName(taskName);
			projectTasks.setDescription(taskName);
			if (!Constants.HOLIDAY.equalsIgnoreCase(taskName) && !Constants.COMPENSATORY_LEAVE.equalsIgnoreCase(taskName) && !Constants.WORK_FROM_HOME.equalsIgnoreCase(taskName)) {
				projectTasks.setBillableType(Constants.TASK_NON_BILLABLE);
			} else {
				projectTasks.setBillableType(Constants.TASK_BILLABLE);
			}
			projectTasks.setCreatedDate(new Date());
			projectTasks.setUpdatedDate(new Date());
			projectTasks.setProjectId(projects);
			set.add(projectTasks);
		}
		return set;
	}

	@Override
	public List<Projects> getProjectsBasedOnClientsAndStatusSelectionInFilters(Long userId, String clients,
			String status,Long orgLocationsId) throws Exception {
		logger.debug("In ProjectsServiceImpl class getProjectsBasedOnClientsAndStatusSelectionInFilters method...");
    	List<Projects> projectList = projectsDao.getProjectsBasedOnClientsAndStatusSelectionInFilters(userId,clients,status,orgLocationsId);
    	 OrgLocations orglo = orgLocationService.getOrgLocations(orgLocationsId);
         SimpleDateFormat formatter = orglo.getLocFullFormDateFormatter();
    	for (Projects projects :projectList ){
	    	    projects.setStartDateStr(formatter.format(projects.getStartDate()));
	    	    projects.setEmployeeStartDateStr(formatter.format(projects.getStartDate()));
			    if(projects.getEndDate() != null){
			    	 projects.setEndDateStr(formatter.format(projects.getEndDate()));
			    	 projects.setEmployeeEndDateStr(formatter.format(projects.getEndDate()));
			    }
			    
			    if(null != projects.getOrgRelationId()){
					if(null != projects.getOrgRelationId().getLocationId() && projects.getOrgRelationId().getLocationId().getId().longValue() == orgLocationsId.longValue()){
						projects.setClient(projects.getOrgRelationId().getResponderLocationId().getOrgLocationName());
					}if(null != projects.getOrgRelationId().getResponderLocationId() && projects.getOrgRelationId().getResponderLocationId().getId().longValue() == orgLocationsId.longValue()){
						projects.setClient(projects.getOrgRelationId().getLocationId().getOrgLocationName());
					}
				}
			    
			    Collection<ProjectApproverHierarchy> projectApproverHierarchies = projectApproverHierarchyDao.getApproverUsersByProjectId(projects.getId());
			    for(ProjectApproverHierarchy approverHierarchy :projectApproverHierarchies){
			    	StringBuilder projectStringBuilder=new StringBuilder();
			    	if(approverHierarchy.getLevel()==1){
			    		projects.setManagerLevel1(projectStringBuilder.append(approverHierarchy.getApproverUserId().getFirstName()).append(" ").append(approverHierarchy.getApproverUserId().getLastName()).toString());
			    	}else if(approverHierarchy.getLevel()==2){
			    		projects.setManagerLevel2(projectStringBuilder.append(approverHierarchy.getApproverUserId().getFirstName()).append(" ").append(approverHierarchy.getApproverUserId().getLastName()).toString());
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
	public List<Projects> getProjectsByOrgLocationsIdAndApproverUserId(Long id, Long userId) {
		// TODO Auto-generated method stub
		return projectsDao.getProjectsByOrgLocationsIdAndApproverUserId(id,userId);
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

	/*@Override
	public HashSet<User> getAllUserTimeSheetSubmissionStatus() throws Exception {
		
		return projectsDao.getAllUserTimeSheetSubmissionStatus();
	}*/

	@Override
	public void userEmailNotificationForTimeSheetSubmission(User user,Long locationsId,Map<String, String> timesheetFrequencyMap,Projects projects,String level) throws Exception {
		OrgLocations orgLocations = orgLocationService.getOrgLocations(locationsId);
		
		Set<Projects> projectsSet = new HashSet<>();
		 EmailMessage emailMessage = new EmailMessage();
		 emailMessage.setFirstName(user.getFirstName());
		 emailMessage.setLastName(user.getLastName());
		 emailMessage.setToEmail(user.getEmail());
		 emailMessage.setFromEmail(defaultFromEmailAddressForProd);
		 StringBuilder fromNameSB = new StringBuilder();
		 emailMessage.setFromName(fromNameSB.append(user.getFirstName()).append(" ").append(user.getLastName()).toString());
		 emailMessage.setEmailType(Constants.CUSTOM_USER_EMAIL);
		//setting the class filed values
		 Map<String, String> globalVarsMap = new HashMap<String, String>();
		 globalVarsMap.put("firstName", emailMessage.getFirstName());
		 globalVarsMap.put("lastName",emailMessage.getLastName());
		 globalVarsMap.put("administratorFirstName", "SourceLead");
		 globalVarsMap.put("administratorLastName","Admin");
		 globalVarsMap.put("role",user.getUserType());
		 globalVarsMap.put("userEmailId",user.getEmail()); //
		 LinkedList<String> frequency = new LinkedList<>();
		 StringBuilder tempHTML = new StringBuilder();
		 tempHTML.append("<table width='100%' bgcolor='#F9F9F9' cellpadding='0' border='2' cellspacing='0' style='background-color:#F9F9F9;'>");
			        tempHTML.append("<tr>");
					tempHTML.append("<th> Project Name </th>");
					tempHTML.append("<th> Frequency </th>");
					tempHTML.append("</tr>");
		
			/*if(timesheetSubmissions != null && !timesheetSubmissions.isEmpty()){
				 for(TimesheetSubmissions ts :timesheetSubmissions){
					 tempHTML.append("<tr>");
					 tempHTML.append("<td>").append(ts.getProjectMembers().getProjects().getProjectName()).append("</td>").toString();
					 tempHTML.append("<td>").append(Constants.DMY.format(ts.getStartDate())).append(" To ").append(Constants.DMY.format(ts.getEndDate())).append("</td>").toString();
					 tempHTML.append("</tr>");
					 projectsSet.add(ts.getProjectMembers().getProjects());
				 }
			}*/
					
			if(timesheetFrequencyMap != null && !timesheetFrequencyMap.isEmpty()){
				for (Map.Entry<String, String> entry : timesheetFrequencyMap.entrySet()) {
					    String key = entry.getKey();
					    String value =entry.getValue();
				    	 tempHTML.append("<tr>");
						 tempHTML.append("<td>").append(projects.getProjectName()).append("</td>").toString();
						 tempHTML.append("<td>").append(key.replaceAll("/", "-")).append(" To ").append(value.replaceAll("/", "-")).append("</td>").toString();
						 tempHTML.append("</tr>");
						 projectsSet.add(projects);
				}
			}
			
			
	     tempHTML.append("</table> ");
		 globalVarsMap.put("weekFrequency",tempHTML.toString());
		 //setting the Recipient information
		 InternetAddress[] iAdressArray = InternetAddress.parse(emailMessage.getToEmail());
		
		 //getting the current EmailScenario by EmailScenario unique name and entity type
		 EmailScenario  emailScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.TIMESHEET_EMAIL_NOTIFICATION,Constants.ENTITY_TYPE_EMAIL);
		
		 //covert subject,body and signature
		 String subject = emailScenerioUtil.bulidSubject(emailScenario.getSubject(),globalVarsMap);
		 String body = emailScenerioUtil.buildTempBody(emailScenario.getBody(),globalVarsMap);
		 String signature = emailScenerioUtil.buildSignature(emailScenario.getSignature(),globalVarsMap);
		 String url = sourceleadUrl;
		 
		 //construct HTML template using required data
		 String emailTemplate = emailScenerioUtil.AssembleEmailMessage(emailScenario,emailMessage, subject, body, signature, url, orgLocations.getOrg(),orgLocations);
		 emailMessage.setSubject(subject);
		 emailMessage.setMessageBody(emailTemplate);
		 
		 //calling the sendEmail method of emailScenerioUtil
		 emailScenerioUtil.sendEmail(emailMessage,locationsId,iAdressArray,new ArrayList<EmailAttachment>());
		 sendWeeklyBaseNotiFication(user,orgLocations,projectsSet);
	}


	private void sendWeeklyBaseNotiFicationToAdmin(User user, OrgLocations orgLocations) throws Exception {
		if(user.getId() !=1 && user.getId() !=2){
			List<Employees> empList = userService.getAllUserOrgCodeAndLocationCodeTimeSheetNotification(user.getId());
		    Employees emp = empList.get(0);
		   /* logger.info("Employee Location code :"+emp.getOrgLocations().getLocationCode());
			logger.info("Employee org code :"+emp.getOrgLocations().getOrg().getOrgCode());*/
		    User toUser = emp.getUser();
		    User fromUser = null;
		    if(user.getId() != orgLocations.getUser().getId()){
		    	  fromUser = orgLocations.getUser();
		    }else{
		    	fromUser = userService.getUserById(1L);
		    }
		    logger.info("Aprover for the Employee "+user.getFirstName()+" is :"+fromUser.getFirstName()+"("+fromUser.getUserType()+")");
		        EmailScenario  emailScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.TIMESHEET_EMAIL_NOTIFICATION,Constants.ENTITY_TYPE_NOTIFICATION);
			    Map<String, String> globalVarsMap = new HashMap<String, String>();
			    globalVarsMap.put("firstName", user.getFirstName());
			    globalVarsMap.put("lastName",user.getLastName());
				String body = emailScenerioUtil.buildBody(emailScenario.getBody(),globalVarsMap);
				emailScenario.setBody(body);
				emailScenario.setOrgCode(emp.getOrgLocations().getOrg().getOrgCode());
				String actionLink = "";
				OrgLocations loginOrgLocation = new OrgLocations();
			   loginOrgLocation.setLocationCode(emp.getOrgLocations().getLocationCode());
			   loginOrgLocation.setOrg(emp.getOrgLocations().getOrg());
			   emailScenerioUtil.sendNotification(actionLink, fromUser, toUser, loginOrgLocation, emailScenario);	
		}
	}
	

	private void sendWeeklyBaseNotiFication(User user, OrgLocations orgLocations, Set<Projects> projectsSet) throws Exception {
		if(user.getId().longValue() !=1l && user.getId().longValue() !=2l){
			List<Employees> empList = userService.getAllUserOrgCodeAndLocationCodeTimeSheetNotification(user.getId());
		    Employees emp = empList.get(0);
		    logger.info("Employee Location code :"+emp.getOrgLocations().getLocationCode());
			logger.info("Employee org code :"+emp.getOrgLocations().getOrg().getOrgCode());
		    User toUser = emp.getUser();
		    for(Projects projects :projectsSet){
		    	 User fromUser = null;
		    	List<ProjectApproverHierarchy> pahList = projectApproverHierarchyService.getApproverUsersByProjectId(projects.getId());
		    	//for(ProjectApproverHierarchy pah :pahList){
		    		//if(pah.getLevel() != null && pah.getLevel().intValue() == 1){
//		    	for(ProjectApproverHierarchy pah :pahList){
		    	ProjectApproverHierarchy pah = pahList.get(0);
		    		if(pah.getLevel() != null && pah.getLevel().intValue() == 1){
		    			fromUser = pah.getApproverUserId();
		    		}
		    		 logger.info("Aprover for the Employee "+user.getFirstName()+" is :"+fromUser.getFirstName()+"("+fromUser.getUserType()+")");
				        EmailScenario  emailScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.TIMESHEET_EMAIL_NOTIFICATION,Constants.ENTITY_TYPE_NOTIFICATION);
					    Map<String, String> globalVarsMap = new HashMap<String, String>();
					    globalVarsMap.put("firstName", user.getFirstName());
					    globalVarsMap.put("lastName",user.getLastName());
						String body = emailScenerioUtil.buildBody(emailScenario.getBody(),globalVarsMap);
						emailScenario.setBody(body);
						emailScenario.setOrgCode(emp.getOrgLocations().getOrg().getOrgCode());
						String actionLink = "";
						OrgLocations loginOrgLocation = new OrgLocations();
					   loginOrgLocation.setLocationCode(emp.getOrgLocations().getLocationCode());
					   loginOrgLocation.setOrg(emp.getOrgLocations().getOrg());
					   emailScenerioUtil.sendNotification(actionLink, fromUser, toUser, loginOrgLocation, emailScenario);
//		    	}
		    }
		}
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
		
		SimpleDateFormat dateFormat=loginOrgLocations.getLocFullFormDateFormatter();
		
		if(projects.getCreatedDate() != null){
			projects.setCreatedDateStr(dateFormat.format(projects.getCreatedDate()));	
		}
	    projects.setStartDateStr(dateFormat.format(projects.getStartDate()));
	    if(projects.getEndDate() != null){
	    	 projects.setEndDateStr(dateFormat.format(projects.getEndDate()));
	    }
	    if(projects.getUpdatedDate() != null){
	    	projects.setUpdatedDateStr(dateFormat.format(projects.getUpdatedDate()));
	    }
	    if(projects.getProjectSequeneNumber() != null){
	    	projects.setProjectSequeneNumber(projects.getProjectSequeneNumber());
	    }
	    User createdByUseraName= userService.getUserDetailsByUserCode(projects.getCreatedBy());
	    if(null !=createdByUseraName){
	    	StringBuilder userName = new StringBuilder();
	    	if(null != createdByUseraName.getLastName()){
	    		projects.setCreatedByName(userName.append(createdByUseraName.getFirstName()).append(" ").append(createdByUseraName.getLastName()).toString());	
	    	}else{
	    		projects.setCreatedByName(userName.append(createdByUseraName.getLastName()).toString());
	    	}
	    	
	    }
	    StringBuilder projectApprover=new StringBuilder();
	    projects.setCreatedBy(projectApprover.append(user.getFirstName()).append(" ").append(user.getLastName()).toString());
	    Collection<ProjectApproverHierarchy> projectApproverHierarchies = projectApproverHierarchyService.getApproverUsersByProjectId(projectId);
	   
	    /**
	     * If the Project has Only Manager the His Level is taken as Higher Level  
	     */
	    if(projectApproverHierarchies.size()==1){
	    	
	    	for(ProjectApproverHierarchy approverHierarchy : projectApproverHierarchies){
		    	if(approverHierarchy.getLevel() != null){
		    		projects.setManager1(approverHierarchy.getApproverUserId().getId());
		    		UserRole userRole = userRoleService.getUserRoleByUserIdAndLocationId(approverHierarchy.getApproverUserId().getId(),loginOrgLocations.getId());
		    		if(null != userRole){
		    			projects.setManagerLevel1UserType(userRole.getUserType());
		    		}
		    		projects.setManagerLevel1(projectApprover.append(approverHierarchy.getApproverUserId().getFirstName()).append(" ").append(approverHierarchy.getApproverUserId().getLastName()).toString());
		    	}
	    	}
	    }else{
	    	
	    	for(ProjectApproverHierarchy approverHierarchy : projectApproverHierarchies){
		    	if(approverHierarchy.getLevel() != null && approverHierarchy.getLevel() == 1){
		    		projects.setManager1(approverHierarchy.getApproverUserId().getId());
		    		UserRole userRole = userRoleService.getUserRoleByUserIdAndLocationId(approverHierarchy.getApproverUserId().getId(),loginOrgLocations.getId());
		    		if(null != userRole){
		    			projects.setManagerLevel1UserType(userRole.getUserType());
		    		}
		    		projects.setManagerLevel1(projectApprover.append(approverHierarchy.getApproverUserId().getFirstName()).append(" ").append(approverHierarchy.getApproverUserId().getLastName()).toString());
		    	}
		    	if(approverHierarchy.getLevel() != null && approverHierarchy.getLevel() == 2){
		    		projects.setManager2(approverHierarchy.getApproverUserId().getId());
		    		projects.setManagerLevel2(projectApprover.append(approverHierarchy.getApproverUserId().getFirstName()).append(" ").append(approverHierarchy.getApproverUserId().getLastName()).toString());
		    	}
		    	
		    }
	    }
	    
	    
	    projects.setProjectApproverHierarchiesList(projectApproverHierarchies);
	    
	    //Collection<User> userList = userService.getUsersByUserRolesAndOrgLocationId(loginOrgLocations.getId());
	    List<User> userList = userService.getTabAccessOwnersByOrgLocationIdAndTabId(loginOrgLocations.getId(),Long.valueOf(Constants.TAB_TIMESHEET_APPROVAL));
	    projects.setManagersList(userList);
	    if(projects.getManager1() != null){
	    	  List<User> userListwithoutmaneger1 = userService.getTabAccessOwnersByOrgLocationIdAndTabIdAndNoCurrentUser(projects.getManager1(),loginOrgLocations.getId(),Long.valueOf(Constants.TAB_TIMESHEET_APPROVAL));
			  projects.setManagersListwithoutManager1(userListwithoutmaneger1);
	    }
	    
	    String[] approvers = new String[projectApproverHierarchies.size()];
	    int i = 0;
	    for(ProjectApproverHierarchy php : projectApproverHierarchies){
	    	StringBuilder approverHierarchies=new StringBuilder();
	    	approvers[i] = approverHierarchies.append(php.getApproverUserId().getFirstName()).append("&").append(php.getApproverUserId().getId()).toString();
	    	i++;
	    }
	    projects.setProjectManagerName(approvers);
	    
	    Collection<OrgRelations> orgClientList =orgRelationsService.getAllClientsByOrgLocationId(loginOrgLocations.getId()); 
	    String[] clientList = new String[orgClientList.size()];
	    int j = 0;
		for(OrgRelations ocl :orgClientList){
			if(null != projects.getOrgRelationId()){
				if(projects.getOrgRelationId().getId().equals(ocl.getId()) && ocl.getLocationId().getId().longValue() == loginOrgLocations.getId().longValue()){
					projects.setRelationId(ocl.getId());
					projects.setClient(ocl.getResponderLocationId().getOrgLocationName());
				}if(projects.getOrgRelationId().getId().equals(ocl.getId()) && ocl.getResponderLocationId().getId().longValue() == loginOrgLocations.getId().longValue()){
					projects.setRelationId(ocl.getId());
					projects.setClient(ocl.getLocationId().getOrgLocationName());
				}
			}
			
			if(null != ocl.getLocationId() && null != ocl.getLocationId().getId() && ocl.getLocationId().getId().longValue() == loginOrgLocations.getId().longValue()){
				if(null != ocl.getResponderLocationId()){
					StringBuilder approverHierarchiesSB=new StringBuilder();
					clientList[j] = approverHierarchiesSB.append(ocl.getResponderLocationId().getOrgLocationName()).append(",").append(ocl.getId()).toString();
				}
			}
			if(null != ocl.getResponderLocationId() && null != ocl.getResponderLocationId().getId() && ocl.getResponderLocationId().getId().longValue() == loginOrgLocations.getId().longValue()){
				if(null != ocl.getLocationId()){
					StringBuilder approverHierarchiesSB=new StringBuilder();
					clientList[j] = approverHierarchiesSB.append(ocl.getLocationId().getOrgLocationName()).append(",").append(ocl.getId()).toString();
				}
			}
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
	  * @return Projects
	  * @throws Exception
	  * 
	  * update by prasad salina here changed method return type String to Projects
	  */
	
	
	 private void sendProjectEditMailtoProjectManager(Projects projects, User loginUser, User approverUserId1, OrgLocations loginOrgLocation, String teamMembersList,String loginOrgCode,Date changedEndDate) throws Exception {
	  	 User projectManager = userService.getUserById(approverUserId1.getId());
	  	 
	  	 if(isNullOrEmpty(teamMembersList) && ("undefined".equals(teamMembersList))){
	  		teamMembersList = "-";
	  	 }
	  	 StringBuilder fromNameBuilder=new StringBuilder();
	     EmailMessage emailMessage = new EmailMessage();
		 emailMessage.setFirstName(projectManager.getFirstName());
		 emailMessage.setLastName(projectManager.getLastName());
		 emailMessage.setToEmail(projectManager.getEmail());
		 emailMessage.setFromEmail(loginUser.getEmail());
		 emailMessage.setFromName(fromNameBuilder.append(loginUser.getFirstName()).append(" ").append(loginUser.getLastName()).toString());
		 emailMessage.setEmailType(Constants.CUSTOM_USER_EMAIL);
		 //setting the class filed values
		 Map<String, String> globalVarsMap = new HashMap<String, String>();
		 SimpleDateFormat dateFormat=loginOrgLocation.getLocFullFormDateFormatter();
		 
		 projects.setStartDateStr(dateFormat.format(projects.getStartDate()));
		    if(changedEndDate != null){
		    	 projects.setEndDateStr(dateFormat.format(changedEndDate));
		    }
			
		    
		    
		 globalVarsMap.put("projectStartdate",projects.getStartDateStr());
		 globalVarsMap.put("projectEnddate",projects.getEndDateStr());
		 
		 if(loginUser.getFirstName() != null && loginUser.getLastName()!=null && !"".equalsIgnoreCase(loginUser.getFirstName()) && !"".equalsIgnoreCase(loginUser.getLastName())){
			 StringBuilder userBuild = new StringBuilder();
			 globalVarsMap.put("loginUser",userBuild.append(loginUser.getFirstName()).append(" ").append(loginUser.getLastName()).toString());
			 }else{
				 globalVarsMap.put("loginUser",loginUser.getFirstName());
			}
		 if(projectManager.getFirstName() != null && projectManager.getLastName()!=null && !"".equalsIgnoreCase(projectManager.getFirstName()) && !"".equalsIgnoreCase(projectManager.getLastName())){
			 StringBuilder userBuild = new StringBuilder();
			 globalVarsMap.put("managerName",userBuild.append(projectManager.getFirstName()).append(" ").append(projectManager.getLastName()).toString());
			 }else{
				 globalVarsMap.put("managerName",projectManager.getFirstName());
			}
		 globalVarsMap.put("projectName", projects.getProjectName());
		 globalVarsMap.put("loginOrgName",loginOrgLocation.getOrgLocationName());
		  
		 //setting the Recipient information
		 InternetAddress[] iAdressArray = InternetAddress.parse(emailMessage.getToEmail());
		 
		 //getting the current EmailScenario by EmailScenario unique name and entity type
		 EmailScenario  emailScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.EDITPROJECT_MAIL_MANAGER,Constants.ENTITY_TYPE_EMAIL);
		
		 //covert subject,body and signature
		 String subject = emailScenerioUtil.bulidSubject(emailScenario.getSubject(),globalVarsMap);
		 String body = emailScenerioUtil.buildTempBody(emailScenario.getBody(),globalVarsMap);
		 String signature = emailScenerioUtil.buildSignature(emailScenario.getSignature(),globalVarsMap);
		 String url = sourceleadUrl;
		 
		 //construct HTML template using required data
		 String emailTemplate = emailScenerioUtil.AssembleEmailMessage(emailScenario,emailMessage, subject, body, signature, url, loginOrgLocation.getOrg(),loginOrgLocation);
		 emailMessage.setSubject(subject);
		 emailMessage.setMessageBody(emailTemplate);
		 
		 //calling the sendEmail method of emailScenerioUtil
		 if(null != projects.getOrgLocationsId())
		 emailScenerioUtil.sendEmail(emailMessage,projects.getOrgLocationsId().getId(),iAdressArray,new ArrayList<EmailAttachment>());
		 
		 
		//calling the sendNotification method of emailScenerioUtil
		EmailScenario  notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.EDITPROJECT_MAIL_MANAGER,Constants.ENTITY_TYPE_NOTIFICATION);
		String notificationBody = emailScenerioUtil.buildBody(notificationScenario.getBody(),globalVarsMap);
		notificationScenario.setBody(notificationBody);
		notificationScenario.setOrgCode(loginOrgCode);
		//String url = null;
		String actionLink = "";
		emailScenerioUtil.sendNotification(actionLink, loginUser, projectManager, loginOrgLocation, notificationScenario);
	}
	 
	 
	 private void sendProjectEditMailtoTeamMembers(Projects projects, Employees emp, User loginUser, User approverUserId1, OrgLocations loginOrgLocation,String loginOrgCode, Date changedEndDate) throws Exception {
	  	 StringBuilder fromNameBuilder=new StringBuilder();
	     EmailMessage emailMessage = new EmailMessage();
		 emailMessage.setFirstName(emp.getUser().getFirstName());
		 emailMessage.setLastName(emp.getUser().getLastName());
		 emailMessage.setToEmail(emp.getUser().getEmail());
		 emailMessage.setFromEmail(loginUser.getEmail());
		 emailMessage.setCreatedBy(loginUser.getUserCode());
		 emailMessage.setFromName(fromNameBuilder.append(loginUser.getFirstName()).append(" ").append(loginUser.getLastName()).toString());
		 emailMessage.setEmailType(Constants.CUSTOM_USER_EMAIL);
		 //setting the class filed values
		 Map<String, String> globalVarsMap = new HashMap<String, String>();
		 
		 
		 projects.setStartDateStr(Constants.DF_DMY.format(projects.getStartDate()));
		    if(changedEndDate != null){
		    	 projects.setEndDateStr(Constants.DF_DMY.format(changedEndDate));
		    }
			
		    
		    
		 globalVarsMap.put("projectStartdate",projects.getStartDateStr());
		 globalVarsMap.put("projectEnddate",projects.getEndDateStr());
		 
		 if(loginUser.getFirstName() != null && loginUser.getLastName()!=null && !"".equalsIgnoreCase(loginUser.getFirstName()) && !"".equalsIgnoreCase(loginUser.getLastName())){
			 StringBuilder userBuild = new StringBuilder();
			 globalVarsMap.put("loginUser",userBuild.append(loginUser.getFirstName()).append(" ").append(loginUser.getLastName()).toString());
			 }else{
				 globalVarsMap.put("loginUser",loginUser.getFirstName());
			}
		 if(emailMessage.getFirstName() != null && emailMessage.getLastName()!=null && !"".equalsIgnoreCase(emailMessage.getFirstName()) && !"".equalsIgnoreCase(emailMessage.getLastName())){
			 StringBuilder userBuild = new StringBuilder();
			 globalVarsMap.put("employeeName",userBuild.append(emailMessage.getFirstName()).append(" ").append(emailMessage.getLastName()).toString());
			 }else{
				 globalVarsMap.put("employeeName",emailMessage.getFirstName());
			}
		 globalVarsMap.put("projectName", projects.getProjectName());
		 globalVarsMap.put("loginOrgName",loginOrgLocation.getOrgLocationName());
		 
		 //setting the Recipient information
		 InternetAddress[] iAdressArray = InternetAddress.parse(emailMessage.getToEmail());
		 
		 //getting the current EmailScenario by EmailScenario unique name and entity type
		 EmailScenario  emailScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.EDITPROJECT_MAIL_TEAMMEMBER,Constants.ENTITY_TYPE_EMAIL);
		
		 //covert subject,body and signature
		 String subject = emailScenerioUtil.bulidSubject(emailScenario.getSubject(),globalVarsMap);
		 String body = emailScenerioUtil.buildTempBody(emailScenario.getBody(),globalVarsMap);
		 String signature = emailScenerioUtil.buildSignature(emailScenario.getSignature(),globalVarsMap);
		 String url = sourceleadUrl;
		 
		 //construct HTML template using required data
		 String emailTemplate = emailScenerioUtil.AssembleEmailMessage(emailScenario,emailMessage, subject, body, signature, url, loginOrgLocation.getOrg(),loginOrgLocation);
		 emailMessage.setSubject(subject);
		 emailMessage.setMessageBody(emailTemplate);
		 
		 //calling the sendEmail method of emailScenerioUtil
		 if(null != projects.getOrgLocationsId())
		 emailScenerioUtil.sendEmail(emailMessage,projects.getOrgLocationsId().getId(),iAdressArray,new ArrayList<EmailAttachment>());
		 
		 
		//calling the sendNotification method of emailScenerioUtil
		EmailScenario  notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.EDITPROJECT_MAIL_TEAMMEMBER,Constants.ENTITY_TYPE_NOTIFICATION);
		String notificationBody = emailScenerioUtil.buildBody(notificationScenario.getBody(),globalVarsMap);
		notificationScenario.setBody(notificationBody);
		notificationScenario.setOrgCode(loginOrgCode);
		//String url = null;
		String actionLink = "";
		emailScenerioUtil.sendNotification(actionLink, loginUser, emp.getUser(), loginOrgLocation, notificationScenario);
	}
	
	@Override
	public Projects getProjectDetailsUpdateByProjectId(Long projectId, User user, JSONObject jsonObj) throws Exception {
	 	OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
	 	String loginOrgCode = (String) SourceLeadSessionManager.getSessionAttribute(request, "orgCode");

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
		    	
		    	if(jsonObj.has("isAllowFreelanceDevelopers") && StringUtils.isNotEmpty(jsonObj.getString("isAllowFreelanceDevelopers"))){
					projects.setAllowFreeLance(BooleanUtils.toBoolean(jsonObj.getString("isAllowFreelanceDevelopers"),"Y",""));
				}else{
					projects.setAllowFreeLance(null);
				}
				if(jsonObj.has("isAllowRemoteWork") && StringUtils.isNotEmpty(jsonObj.getString("isAllowRemoteWork"))){
					projects.setAllowRemote(BooleanUtils.toBoolean(jsonObj.getString("isAllowRemoteWork"),"Y",""));
				}else{
					projects.setAllowRemote(null);
				}
				
				if(jsonObj.has("isAllowOverTime") && StringUtils.isNotEmpty(jsonObj.getString("isAllowOverTime"))){
					projects.setAllowOvertime(BooleanUtils.toBoolean(jsonObj.getString("isAllowOverTime"),"Y",""));
				}else{
					projects.setAllowOvertime(null);
				}
				if(jsonObj.has("isAllowOffshoreResource") && StringUtils.isNotEmpty(jsonObj.getString("isAllowOffshoreResource"))){
					projects.setAllowOffShore(BooleanUtils.toBoolean(jsonObj.getString("isAllowOffshoreResource"),"Y",""));
				}else{
					projects.setAllowOffShore(null);
				}
				if(jsonObj.has("isTimeEntryAfterDeadline") && StringUtils.isNotEmpty(jsonObj.getString("isTimeEntryAfterDeadline"))){
					projects.setAllowTimeEntryAfterDeadLine(BooleanUtils.toBoolean(jsonObj.getString("isTimeEntryAfterDeadline"),"Y",""));
				}else{
					projects.setAllowTimeEntryAfterDeadLine(null);
				}
				if(jsonObj.has("isAllowBidding") && StringUtils.isNotEmpty(jsonObj.getString("isAllowBidding"))){
					projects.setAllowBidding(BooleanUtils.toBoolean(jsonObj.getString("isAllowBidding"),"Y",""));
				}else{
					projects.setAllowBidding(null);
				}
				
				if(jsonObj.has("flatPercentage") && StringUtils.isNotEmpty(jsonObj.getString("flatPercentage"))){
					projects.setFlatPercent(jsonObj.getDouble("flatPercentage"));
				}else{
					projects.setFlatPercent(null);
				}
				
				if(jsonObj.has("flatPercentageId") && StringUtils.isNotEmpty(jsonObj.getString("flatPercentageId"))){
					projects.setOvertimeRateType(jsonObj.getString("flatPercentageId"));
				}else{
					projects.setOvertimeRateType(null);
				}
		    	
		    	
		    	projects.setProjectStatus(Project_Status.getNameByValue(jsonObj.getString("projectStatusId")));
		    	
		    		if(jsonObj.has("endDateStr1")){
		    			 SimpleDateFormat format=loginOrgLocation.getLocFullFormDateFormatter();
//			    	     SimpleDateFormat format = new SimpleDateFormat(pattern);
			    	     if(!jsonObj.getString("endDateStr1").isEmpty() && jsonObj.getString("endDateStr1") != null && !"NO DATA".equalsIgnoreCase(jsonObj.getString("endDateStr1"))){
					    		previousEndDate = projects.getEndDate();
					    		Date endDate = format.parse(jsonObj.getString("endDateStr1"));
					    		//this method is used to send email for edit project end date
								
					    		 if(projects.getEndDate() != null ){		
					                  if(endDate.compareTo(projects.getEndDate()) != 0 ){
								    		sendProjectEditMailtoTeamMembersAndOwnersWhenProjectEndDateIsEdited(jsonObj,projects,user,loginOrgLocation,loginOrgCode);

					                  }
					                  
					    		 } else if(!jsonObj.getString("endDateStr1").isEmpty() && jsonObj.getString("endDateStr1") != null && !"NO DATA".equalsIgnoreCase(jsonObj.getString("endDateStr1"))){
							    		sendProjectEditMailtoTeamMembersAndOwnersWhenProjectEndDateIsEdited(jsonObj,projects,user,loginOrgLocation,loginOrgCode);
					    		 }
								 projects.setEndDate(endDate);

					    	}else{
					    		Date endDate = null;
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
		    		
		    		if((jsonObj.has("isTimesheetAttachmentRequired")) && StringUtils.isNotEmpty(jsonObj.getString("isTimesheetAttachmentRequired")) && ("Y".equals(jsonObj.getString("isTimesheetAttachmentRequired")))){
		    	    	projects.setIsTimesheetAttachmentRequired(Constants.RECORD_ACTIVE);
		    	    }else{
		    	    	projects.setIsTimesheetAttachmentRequired(Constants.RECORD_INACTIVE);
		    	    }	
		    		
		    	if(jsonObj.has("level1Manager") || jsonObj.has("level2Manager") ){
			    		HashSet<Long> oldlist = new HashSet<>();
			    		HashSet<Long> freshlist = new HashSet<>();
			    		HashSet<Long> templist = null;
			    		Collection<ProjectApproverHierarchy> projectApproverHierarchies = projectApproverHierarchyService.getApproverUsersByProjectId(projectId);
			    		
			    		for(ProjectApproverHierarchy prHierarchy :projectApproverHierarchies ){
								 ProjectApproverHierarchy pah  = projectApproverHierarchyService.getByProjectApproverHierarchyId(prHierarchy.getId());
								 Long fieldValuelong = null;
								     String level1UserDetails[] = jsonObj.getString("level1Manager").split(",");
								     if(prHierarchy.getLevel() == 1 || (prHierarchy.getLevel() == 2 && projectApproverHierarchies.size() == 1))
								     {
								    	 fieldValuelong = Long.valueOf(level1UserDetails[0]);
								     }
								     if(prHierarchy.getLevel() == 2 &&  jsonObj.has("level2Manager")){
								    	 if(!"".equalsIgnoreCase(jsonObj.getString("level2Manager"))){
								    		 fieldValuelong = Long.valueOf(jsonObj.getString("level2Manager"));
								    	 }
								    	 
								     }
								      if(prHierarchy.getLevel() == 2 && (!jsonObj.has("level2Manager") || (jsonObj.has("level2Manager") && StringUtils.isEmpty(jsonObj.getString("level2Manager"))))){
								    	 oldlist.add(pah.getApproverUserId().getId());
								    	 projectApproverHierarchyService.deleteProjectApproverHierarchy(pah);
								     }
								     
								     if(fieldValuelong != null){
								    	 User usr = userService.getUserById(fieldValuelong);
										 oldlist.add(pah.getApproverUserId().getId());
										 freshlist.add(usr.getId());
										 pah.setApproverUserId(usr);
										 projectApproverHierarchyService.updateProjectApproverHierarchy(pah);
								     }
								    
			    	    }
			    		
			    		// if   we  are adding new  level 2 manager while editing
			    		if(jsonObj.has("level2Manager") && projectApproverHierarchies.size() == 1){ 	
							if(!jsonObj.getString("level2Manager").isEmpty()){
								Long[] ids = new Long[1000];
								User approverUserId2 = new User();
								ProjectApproverHierarchy pah2 = new ProjectApproverHierarchy();
								pah2.setProjectId(projects);
								approverUserId2.setId(Long.parseLong(jsonObj.getString("level2Manager")));
								pah2.setApproverUserId(approverUserId2);
								pah2.setLevel(2);
								ids[1] = approverUserId2.getId();
								projectApproverHierarchyService.saveProjectApproverHierarchy(pah2);
								freshlist.add(Long.parseLong(jsonObj.getString("level2Manager")));
								
							}
						 }
			    		if(oldlist != null && !oldlist.isEmpty()){
			    			templist = (HashSet<Long>) oldlist.clone();
			    		}
			    		//getting projectmember userId's who are terminated or who are dropped from project
			    		oldlist.removeAll(freshlist);
			    		if(oldlist != null && !oldlist.isEmpty()){
			    			for(Long terminatedUserId : oldlist){
			    				if(projects != null && projects.getId() != null){
			    					ProjectMembers pm = projectMembersService.getProjectMemberByUserIdAndProjectId(terminatedUserId, projects.getId());
				    				if(null != pm && pm.getId() != null){
				    					ProjectMembersHistory projectMembersHistory = projectMembersHistoryService.getProjectMembersOpenHistoryByProjectMemberId(pm.getId());
				    					if(projectMembersHistory != null){
				    						projectMembersHistory.setProjectTerminationDate(currentDate);
					    					projectMembersHistoryService.updateProjectMembersHistory(projectMembersHistory);
				    					}
					    				commonUtil.terminateProjectMemberByProjectMemberId(pm.getId(), Primary.Yes);
				    				}
				    				 
			    				}
			    			}
			    		}
			    		
			    		if(templist != null && !templist.isEmpty()){
			    			//getting projectmember userId's who are added to the project
			    			freshlist.removeAll(templist);
			    			if(freshlist != null && !freshlist.isEmpty()){
			    				for(Long freshlyaddedUserId : freshlist){
			    					UserRole userRole = userRoleService.getUserDetailsByUserIdAndLocationId(freshlyaddedUserId,projects.getOrgLocationsId().getId());
			    					if(userRole != null && !userRole.getRoles().getRoleName().equalsIgnoreCase(Constants.ROLE_ADMIN)){
			    						storeProjectMemberByMemberUserIdAndLoginUser(freshlyaddedUserId, user, projects, currentDate);
			    					}
			    				}
			    			}
			    			
			    		}
			    		
		    	}
			    projects.setUpdatedBy(user.getUserCode());
			    projects.setUpdatedDate(currentDate);
			    if(projects.getProjectStatus().equals(Project_Status.COMPLETED)){
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
			    	    		/* if(projectMember.getEmployee()!= null && projectMember.getEmployee().getUser() != null){
			    	    			 commonUtil.storeTimesheetSubmissionRecords(projectMember.getEmployee().getUser());
			    	    		 }*/
			    	    	 }
			    	     }
			    	}
			    }
		 
				/*
	    	     * Termination Of ProjectMember whose assignDate is greaterthan project endDate /
	    	     *   in-activate the submissions whose startDate is greaterthan project endDate
	    	     */
	    	    if(projects.getEndDate() != null) {
	    	    		commonUtil.terminateProjectMembersWhoseAssignedDateOrTerminatedisGreaterThanProjectEndDate(projects, user);
	    	    }
	    	    

	    	    /**
	    	     * To use onclick parameters for emp tab and task tab
	    	     */
	    	    projects.setStartDateStr(Constants.DF_DMY.format(projects.getStartDate()));
			    if(projects.getEndDate() != null){
			    	 projects.setEndDateStr(Constants.DF_DMY.format(projects.getEndDate()));
			    }
				
			    
			  /*  Set<ProjectMembers>  projectMemberList = projectMembersService.prepareProjectMembersListByJson(jsonObj,request, projects);
				projects.setProjectMembers(projectMemberList);*/
				 /*
	    		 * Updating Project data as ElasticSearch Documents
	    		 */
	    		saveOrUpdateProjectElkDocument(projects);

	    		
	    	
		 return projects;

	}
	
	 void  sendProjectEditMailtoTeamMembersAndOwnersWhenProjectEndDateIsEdited(JSONObject jsonObj ,Projects projects,User user, OrgLocations loginOrgLocation,String loginOrgCode){
		 

		 try{
			 
			//  This is to send mail to managers TeamMembers when project end Date is Edited
			  Date previousEndDate = null;
				if(jsonObj.has("endDateStr1")){
		    		String pattern = "dd-MM-yyyy";
		    	     SimpleDateFormat format = new SimpleDateFormat(pattern);
		    	     if(!jsonObj.getString("endDateStr1").isEmpty() && jsonObj.getString("endDateStr1") != null && !"NO DATA".equalsIgnoreCase(jsonObj.getString("endDateStr1"))){
				    		previousEndDate = projects.getEndDate();
				    		Date changedEndDate = format.parse(jsonObj.getString("endDateStr1"));
							 
				    		if(projects.getEndDate() != null ){
				    			projects.getEndDate();
				    		}
				    		
	                 	    User  approverUserId2  = null;
	         				User  approverUserId1 = null;
	         				if( null !=  jsonObj.getString("level1Manager")  && jsonObj.has("level1Manager") && !jsonObj.getString("level1Manager").isEmpty()){
	         					  String level1UserDetails[] = jsonObj.getString("level1Manager").split(",");
	         					  approverUserId1 =   userService.getUserById(Long.valueOf(level1UserDetails[0]));
	         				if(jsonObj.has("level2Manager") && null != jsonObj.getString("level2Manager") &&jsonObj.has("level2Manager")  && !jsonObj.getString("level2Manager").isEmpty()){
	         			      approverUserId2 =   userService.getUserById(Long.valueOf(jsonObj.getString("level2Manager")));
	         				}
	         				
	         			    Collection<ProjectMembers>  projectMemberList = projectMembersService.getAllProjectMembersByProjectId(projects.getId()) ;

	         			    if(projectMemberList != null && projectMemberList.size() >0){
	         			    	for(ProjectMembers projectMembers : projectMemberList){
		         					if(null != projectMembers.getEmployee()){
		         						Employees emp = projectMembers.getEmployee();
		         							
		         						//if  we have   both level1 and Level2 ProjectManagers
		         						if( null != approverUserId1 && null != approverUserId2){
		         							  if(approverUserId1.getId().longValue() != emp.getUser().getId().longValue() && approverUserId2.getId().longValue() != emp.getUser().getId().longValue()){
		         								  if(user.getId().longValue() != emp.getUser().getId().longValue()){
		         									  //sendProjectEditMailtoTeamMembers(projects, emp, user, approverUserId1, loginOrgLocation,loginOrgCode, changedEndDate);
		         								  }
		         					    }
		         							//if  we have   both level1 only One ProjectManagers
		         						}else if(approverUserId1.getId().longValue() != emp.getUser().getId().longValue()){
		         							 if(user.getId().longValue() != emp.getUser().getId().longValue()){
		         								// sendProjectEditMailtoTeamMembers(projects, emp, user, approverUserId1, loginOrgLocation,loginOrgCode, changedEndDate);
		         							 }
		         							 
		         							 }   
		         							
		         						}
		         					}
	         			      }
	         				}
	         					if(jsonObj.has("level1Manager") && null != jsonObj.getString("level1Manager") && !jsonObj.getString("level1Manager").isEmpty()){
	         						User  approverUserId111 =new User();
	         						String level1UserDetails[] = jsonObj.getString("level1Manager").split(",");
	         						approverUserId111.setId(Long.valueOf(level1UserDetails[0]));
	         						if(approverUserId111 != null && user.getId() != approverUserId111.getId()){
	         							//sendProjectEditMailtoProjectManager(projects, user, approverUserId111, loginOrgLocation,"",loginOrgCode,changedEndDate);
	         						}
	         						
	         					}
	         					

	         					/*
	         					 * This is Mailing for Level2 Manager who was assigned to Project
	         					 * 
	         					 */
	         					
	         					if(jsonObj.has("level2Manager") && null != jsonObj.getString("level2Manager") && !jsonObj.getString("level2Manager").isEmpty()){
	         						User  approverUserId22 =new User();
	         						approverUserId22.setId(Long.valueOf(jsonObj.getString("level2Manager")));
	         						if(!jsonObj.getString("level2Manager").isEmpty() && null !=  approverUserId22 && user.getId() != approverUserId22.getId()){
	         							 if(user.getId().longValue() != approverUserId22.getId().longValue()){
	         								 	//sendProjectEditMailtoProjectManager(projects, user, approverUserId22, loginOrgLocation,"",loginOrgCode,changedEndDate);
	         							 }
	         						 }
	         					}
	         				
	                
				    	}else{
				    		Date endDate = null;
				    		projects.setEndDate(endDate);
				    	}
		    	}
		    	
		 }catch(Exception e){
			 e.printStackTrace();
		 }
		 
			
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

			userService.sendEmail(user,users,password,loginOrgLocation.getId());

		}
		userService.saveOrUpdateUserElkDocument(userRole,emp,users.getUserCode());

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
		user.setCreatedDate(new Date());
		user.setCreatedBy(users.getUserCode());
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
	 * @throws Exception 
	 * 
	 */
	private ProjectMembers storeProjectMemberByMemberUserIdAndLoginUser(Long memberUserId,User loginUser,Projects projects,Date currentDate) throws Exception{
		
		Employees emp = employeesService.getEmployeeByUserId(memberUserId);
		
		ProjectMembers pm = projectMembersService.getProjectMemberByEmpIdAndProjectId(emp.getId(), projects.getId());
		HashSet<ProjectMembersHistory> pmhList = new HashSet<ProjectMembersHistory>();
		if(pm == null){
			pm = new ProjectMembers();
			ProjectMembersHistory pmh = new ProjectMembersHistory();
			pmh.setProjectMemberId(pm);
			pmh.setStatus(Constants.PROJECT_MEMBER_HISTORY_OPEN);
			pmh.setProjectAssignedDate(currentDate);
			pmh.setProjectTerminationDate(projects.getEndDate());
	 	    pmh.setCreatedDate(currentDate);
			pmh.setCreatedBy(loginUser.getUserCode());
			pmh.setUpdatedDate(currentDate);
			pmh.setUpdatedBy(loginUser.getUserCode());
			pmhList.add(pmh);
	    	pm.setProjects(projects);
	    	pm.setEmployee(emp);
			pm.setStatus(Constants.MEMBER_ACTIVE);
			pm.setCreatedDate(currentDate);
			pm.setUpdatedDate(currentDate);
			pm.setCreatedBy(loginUser.getUserCode());
			pm.setUpdatedBy(loginUser.getUserCode());
			pm.setMemberHistoryList(pmhList);
			pm = projectMembersService.saveProjectMember(pm);
		}else{
			pm.setStatus(Constants.MEMBER_ACTIVE);
			pm.setCreatedDate(currentDate);
			pm.setUpdatedDate(currentDate);
			pm.setCreatedBy(loginUser.getUserCode());
			pm.setUpdatedBy(loginUser.getUserCode());
			pm.setMemberHistoryList(pmhList);
			pm = projectMembersService.updateProjectMember(pm);
		}
		
		
		return pm;
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
		/*if(!jsonObj.getString("invoiceFrequency").isEmpty()){
			employee.setInvoiceFrequency(jsonObj.getString("invoiceFrequency"));
		}*/
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
			userRole.setUserType("Employee");
			userRole.setUserState(Status.NOT_LOGGEDIN);
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
	public ProjectMembers getProjectEmployeeListByProjectId(Long projectId) throws Exception {
		
		ProjectMembers myPM =  new ProjectMembers();
		
		
		Collection<ProjectMembers> projectMembersList = projectMembersService.getAllProjectMembersByProjectId(projectId);
		Projects pr = projectsDao.getProjectById(projectId);
		
		SimpleDateFormat dateFormat=pr.getOrgLocationsId().getLocFullFormDateFormatter();
		ArrayList<ProjectMembers> projectMembersArrList = new ArrayList<ProjectMembers>();
		 for(ProjectMembers pm : projectMembersList){
			 if(pm.getEmployee() != null){
				 StringBuilder membersListStringBuilder=new StringBuilder();
				 pm.setEmpName(membersListStringBuilder.append(pm.getEmployee().getUser().getFirstName()).append(" ").append(pm.getEmployee().getUser().getLastName()).toString());	 
				 pm.setEmpEmail(pm.getEmployee().getUser().getEmail());	 
			 }
			 pm.setProjectStatus(pr.getProjectStatus().getValue());
			 
			 ProjectMembersHistory pmh = projectMembersHistoryService.getProjectMembersOpenHistoryByProjectMemberId(pm.getId());
			 if(pmh == null){
				 pmh = projectMembersHistoryService.getProjectMembersRecentClosedByProjectMemberId(pm.getId());
			 }
			 if(pmh != null && pmh.getProjectAssignedDate()!=null){
				 pm.setProjectAssignedDateStr(dateFormat.format(pmh.getProjectAssignedDate()));
            }
	       	 if(pmh != null && pmh.getProjectTerminationDate()!=null){
	       		pm.setProjectEndDateStr(dateFormat.format(pmh.getProjectTerminationDate()));
	       	 }
	       	 //to set actual project end date
	       	 if(pm.getProjects().getEndDate() != null){
	        		pm.setEndDate(dateFormat.format(pm.getProjects().getEndDate()));
	        	 }
	       	 if(pm.getEmployee() != null && pm.getEmployee().getUser() != null && pm.getEmployee().getUser().getId() != null){
	          		pm.setUserId(pm.getEmployee().getUser().getId().toString());
	          	 }
		      if(pm.getTerminateReason() != null){
		    	  pm.setTerminateReason(pm.getTerminateReason());
		      }
	       	 
	       	 if(pm.getEmployee()!=null){
	
	                Float draftTimesheetsSubmissionsCountAsEmployee = submissionApproverHierarchyService.getTotalDraftAndSubmittedHoursByuserIdAndProjectIdForEmployee(pm.getEmployee().getUser().getId(), projectId, "DRAFT");
	    	      //  Float draftTimesheetsSubmissionsCountAsApprover = submissionApproverHierarchyService.getTotalDraftAndSubmittedHoursByuserIdAndProjectIdForApprover(pm.getEmployee().getUser().getId(), projectId, "DRAFT");
	    	      //  Float draftTimesheetsSubmissionsCount =  draftTimesheetsSubmissionsCountAsEmployee + draftTimesheetsSubmissionsCountAsApprover;
	    	        pm.setDraftHrs(draftTimesheetsSubmissionsCountAsEmployee.toString());
	
	    	        Float submittedTimesheetsSubmissionsCountAsEmployee = submissionApproverHierarchyService.getTotalDraftAndSubmittedHoursByuserIdAndProjectIdForEmployee(pm.getEmployee().getUser().getId(), projectId, "SUBMITTED");
	    	       // Float submittedTimesheetsSubmissionsCountAsApprover = submissionApproverHierarchyService.getTotalDraftAndSubmittedHoursByuserIdAndProjectIdForApprover(pm.getEmployee().getUser().getId(), projectId, "SUBMITTED");
	    	       // Float submittedTimesheetsSubmissionsCount =  submittedTimesheetsSubmissionsCountAsEmployee + submittedTimesheetsSubmissionsCountAsApprover;
	    	        pm.setSubmittedHrs(submittedTimesheetsSubmissionsCountAsEmployee.toString());
	    			
	    		    Float approvalTimesheetsSubmissionsCountAsEmployee = submissionApproverHierarchyService.getTotalApprovedOrRejectedHoursByuserIdAndProjectIdForEmployee(pm.getEmployee().getUser().getId(), projectId, "APPROVED");
	    		   // Float approvalTimesheetsSubmissionsCountAsApprover = submissionApproverHierarchyService.getTotalApprovedOrRejectedHoursByuserIdAndProjectIdForApprover(pm.getEmployee().getUser().getId(), projectId, "APPROVED");
	    	       // Float approvalTimesheetsSubmissionsCount =  approvalTimesheetsSubmissionsCountAsEmployee + approvalTimesheetsSubmissionsCountAsApprover;
	    	        pm.setApprovedHrs(approvalTimesheetsSubmissionsCountAsEmployee.toString());
	    			
	    			Float rejectedTimesheetsSubmissionsCountAsEmployee = submissionApproverHierarchyService.getTotalApprovedOrRejectedHoursByuserIdAndProjectIdForEmployee(pm.getEmployee().getUser().getId(), projectId, "REJECTED");
	    		   // Float rejectedTimesheetsSubmissionsCountAsApprover = submissionApproverHierarchyService.getTotalApprovedOrRejectedHoursByuserIdAndProjectIdForApprover(pm.getEmployee().getUser().getId(), projectId, "REJECTED");
	    		    //Float rejectedTimesheetsSubmissionsCount =  rejectedTimesheetsSubmissionsCountAsEmployee + rejectedTimesheetsSubmissionsCountAsApprover;
	    		    pm.setRejectedHrs(rejectedTimesheetsSubmissionsCountAsEmployee.toString());
	                
	                
	       	 }	
			 projectMembersArrList.add(pm);
		 }
		 ObjectMapper mapper = new ObjectMapper();
		 String jsonInString = mapper.writeValueAsString(projectMembersArrList);
		 
		 
		 myPM.setProjectMembersList(projectMembersArrList);
		 if(pr.getProjectStatus() != null){
			 myPM.setStatus(pr.getProjectStatus().getValue());	 
		 }
		 
		 
		 return myPM;
	}
	
	
	@Override
	public ProjectMembers getProjectEmployeeListByProjectIdAndStatus(Long projectId,String status) throws Exception {
		
		ProjectMembers myPM =  new ProjectMembers();
		
		
		Collection<ProjectMembers> projectMembersList = projectMembersService.getAllProjectMembersByProjectIdAndStatus(projectId,status);
		Projects pr = projectsDao.getProjectById(projectId);
		
		SimpleDateFormat dateFormat=pr.getOrgLocationsId().getLocFullFormDateFormatter();
		
		ArrayList<ProjectMembers> projectMembersArrList = new ArrayList<ProjectMembers>();
		 for(ProjectMembers pm : projectMembersList){
			 if(pm.getEmployee() != null){
				 StringBuilder membersListStringBuilder=new StringBuilder();
				 pm.setEmpName(membersListStringBuilder.append(pm.getEmployee().getUser().getFirstName()).append(" ").append(pm.getEmployee().getUser().getLastName()).toString());	 
				 pm.setEmpEmail(pm.getEmployee().getUser().getEmail());	
				 UserRole userRole = userRoleService.getUserRoleByUserIdAndLocationId(pm.getEmployee().getUser().getId(), pm.getProjects().getOrgLocationsId().getId());
				 pm.setUserRoleType(userRole.getUserType());	
			 }
			 pm.setProjectStatus(pr.getProjectStatus().getValue());
			 
			 ProjectMembersHistory pmh = projectMembersHistoryService.getProjectMembersOpenHistoryByProjectMemberId(pm.getId());
			 if(pmh == null){
				 pmh = projectMembersHistoryService.getProjectMembersRecentClosedByProjectMemberId(pm.getId());
			 }
			 if(pmh != null && pmh.getProjectAssignedDate()!=null){
				 pm.setProjectAssignedDateStr(dateFormat.format(pmh.getProjectAssignedDate()));
            }
	       	 if(pmh != null && pmh.getProjectTerminationDate()!=null){
	       		pm.setProjectEndDateStr(dateFormat.format(pmh.getProjectTerminationDate()));
	       	 }
	       	 //to set actual project end date
	       	 if(pm.getProjects().getEndDate() != null){
	        		pm.setEndDate(dateFormat.format(pm.getProjects().getEndDate()));
	        	 }
	       	 if(pm.getEmployee() != null && pm.getEmployee().getUser() != null && pm.getEmployee().getUser().getId() != null){
	          		pm.setUserId(pm.getEmployee().getUser().getId().toString());
	          	 }
		      if(pm.getTerminateReason() != null){
		    	  pm.setTerminateReason(pm.getTerminateReason());
		      }
	       	 
	       	 if(pm.getEmployee()!=null){
	
	                Float draftTimesheetsSubmissionsCountAsEmployee = submissionApproverHierarchyService.getTotalDraftAndSubmittedHoursByuserIdAndProjectIdForEmployee(pm.getEmployee().getUser().getId(), projectId, "DRAFT");
	    	      //  Float draftTimesheetsSubmissionsCountAsApprover = submissionApproverHierarchyService.getTotalDraftAndSubmittedHoursByuserIdAndProjectIdForApprover(pm.getEmployee().getUser().getId(), projectId, "DRAFT");
	    	      //  Float draftTimesheetsSubmissionsCount =  draftTimesheetsSubmissionsCountAsEmployee + draftTimesheetsSubmissionsCountAsApprover;
	    	        pm.setDraftHrs(draftTimesheetsSubmissionsCountAsEmployee.toString());
	
	    	        Float submittedTimesheetsSubmissionsCountAsEmployee = submissionApproverHierarchyService.getTotalDraftAndSubmittedHoursByuserIdAndProjectIdForEmployee(pm.getEmployee().getUser().getId(), projectId, "SUBMITTED");
	    	       // Float submittedTimesheetsSubmissionsCountAsApprover = submissionApproverHierarchyService.getTotalDraftAndSubmittedHoursByuserIdAndProjectIdForApprover(pm.getEmployee().getUser().getId(), projectId, "SUBMITTED");
	    	       // Float submittedTimesheetsSubmissionsCount =  submittedTimesheetsSubmissionsCountAsEmployee + submittedTimesheetsSubmissionsCountAsApprover;
	    	        pm.setSubmittedHrs(submittedTimesheetsSubmissionsCountAsEmployee.toString());
	    			
	    		    Float approvalTimesheetsSubmissionsCountAsEmployee = submissionApproverHierarchyService.getTotalApprovedOrRejectedHoursByuserIdAndProjectIdForEmployee(pm.getEmployee().getUser().getId(), projectId, "APPROVED");
	    		   // Float approvalTimesheetsSubmissionsCountAsApprover = submissionApproverHierarchyService.getTotalApprovedOrRejectedHoursByuserIdAndProjectIdForApprover(pm.getEmployee().getUser().getId(), projectId, "APPROVED");
	    	       // Float approvalTimesheetsSubmissionsCount =  approvalTimesheetsSubmissionsCountAsEmployee + approvalTimesheetsSubmissionsCountAsApprover;
	    	        pm.setApprovedHrs(approvalTimesheetsSubmissionsCountAsEmployee.toString());
	    			
	    			Float rejectedTimesheetsSubmissionsCountAsEmployee = submissionApproverHierarchyService.getTotalApprovedOrRejectedHoursByuserIdAndProjectIdForEmployee(pm.getEmployee().getUser().getId(), projectId, "REJECTED");
	    		   // Float rejectedTimesheetsSubmissionsCountAsApprover = submissionApproverHierarchyService.getTotalApprovedOrRejectedHoursByuserIdAndProjectIdForApprover(pm.getEmployee().getUser().getId(), projectId, "REJECTED");
	    		    //Float rejectedTimesheetsSubmissionsCount =  rejectedTimesheetsSubmissionsCountAsEmployee + rejectedTimesheetsSubmissionsCountAsApprover;
	    		    pm.setRejectedHrs(rejectedTimesheetsSubmissionsCountAsEmployee.toString());
	                
	                
	       	 }	
			 projectMembersArrList.add(pm);
		 }
		 ObjectMapper mapper = new ObjectMapper();
		 String jsonInString = mapper.writeValueAsString(projectMembersArrList);
		 
		 
		 myPM.setProjectMembersList(projectMembersArrList);
		 if(pr.getProjectStatus() != null){
			 myPM.setStatus(pr.getProjectStatus().getValue());	 
		 }
		 
		 
		 return myPM;
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
	    String clientLocationCode = jsonObj.getString("selectClient");	
	    if(!clientLocationCode.isEmpty() && clientLocationCode!=null){
	    	  OrgLocations clientOrgLocations = orgLocationService.getOrgLocations(Long.valueOf(clientLocationCode));
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
	    projects.setProjectUUID(SourceLeadRandomCodeHelper.generateRandomUUID());
    	Projects updatedProjects = saveProject(projects);
    	
    	Long[] ids = new Long[1000];
    	
    	ProjectApproverHierarchy pah1 = new ProjectApproverHierarchy();
    	pah1.setProjectId(updatedProjects);
    	User approverUserId1 = new User();
    	
        if(jsonObj.has("level1Manager")){
		    	if(jsonObj.getString("level1Manager") != null && !jsonObj.getString("level1Manager").isEmpty()){
		    		approverUserId1.setId(Long.parseLong(jsonObj.getString("level1Manager")));
			    	pah1.setApproverUserId(approverUserId1);
			    	if(jsonObj.has("level2Manager")){ 	
			    		pah1.setLevel(1);
			    	}else{
			    		pah1.setLevel(2);
			    	}
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
		
		
		
		//send mail to assigned Project Manager
		if((jsonObj.has("isMailToProjectManager") && !isNullOrEmpty(jsonObj.getString("isMailToProjectManager"))) && ("Y".equals(jsonObj.getString("isMailToProjectManager")))){
			
			String  employeesList = employeesList(jsonObj, updatedProjects, approverUserId2, loginOrgLocation, loginOrgCode);
			
			if(approverUserId1 != null && user.getId() != approverUserId1.getId()){
				sendEmailToProjectManager(projects, user, approverUserId1, loginOrgLocation, employeesList, loginOrgCode);
			}
			if(!jsonObj.getString("level2Manager").isEmpty() && approverUserId2 != null && user.getId() != approverUserId2.getId()){
				sendEmailToProjectManager(projects, user, approverUserId2, loginOrgLocation, employeesList, loginOrgCode);
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
			  	 StringBuilder fromNameBuilder=new StringBuilder();
			     EmailMessage emailMessage = new EmailMessage();
				 emailMessage.setFirstName(projectManager.getFirstName());
				 emailMessage.setLastName(projectManager.getLastName());
				 emailMessage.setToEmail(projectManager.getEmail());
				 emailMessage.setFromEmail(loginUser.getEmail());
				 emailMessage.setCreatedBy(loginUser.getUserCode());
				 emailMessage.setFromName(fromNameBuilder.append(loginUser.getFirstName()).append(" ").append(loginUser.getLastName()).toString());
				 emailMessage.setEmailType(Constants.CUSTOM_USER_EMAIL);
				 //setting the class filed values
				 Map<String, String> globalVarsMap = new HashMap<String, String>();
				 globalVarsMap.put("loginUserFirstName",loginUser.getFirstName());
				 globalVarsMap.put("loginUserLastName",loginUser.getLastName());
				 globalVarsMap.put("managerFirstName",projectManager.getFirstName());
				 globalVarsMap.put("managerLastName",projectManager.getLastName());
				 globalVarsMap.put("teamMembers",teamMembersList);
				 globalVarsMap.put("projectName", projects.getProjectName());
				 globalVarsMap.put("orgName",loginOrgLocation.getOrgLocationName());
				 StringBuilder loginURLBuilder=new StringBuilder();
				 globalVarsMap.put("loginURL",loginURLBuilder.append("<a href='").append(sourceleadUrl).append("/").append("' style='text-decoration:none; '><div style=' width:220px; border-radius:30px; background-color:#214b90; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Login</div></a>").toString());
				 
				 //setting the Recipient information
				 InternetAddress[] iAdressArray = InternetAddress.parse(emailMessage.getToEmail());
				 
				 //getting the current EmailScenario by EmailScenario unique name and entity type
				 EmailScenario  emailScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.MAIL_PROJECT_MANAGER,Constants.ENTITY_TYPE_EMAIL);
				
				 //covert subject,body and signature
				 String subject = emailScenerioUtil.bulidSubject(emailScenario.getSubject(),globalVarsMap);
				 String body = emailScenerioUtil.buildTempBody(emailScenario.getBody(),globalVarsMap);
				 String signature = emailScenerioUtil.buildSignature(emailScenario.getSignature(),globalVarsMap);
				 String url = sourceleadUrl;
				 
				 //construct HTML template using required data
				 String emailTemplate = emailScenerioUtil.AssembleEmailMessage(emailScenario,emailMessage, subject, body, signature, url, loginOrgLocation.getOrg(),loginOrgLocation);
				 emailMessage.setSubject(subject);
				 emailMessage.setMessageBody(emailTemplate);
				 
				 //calling the sendEmail method of emailScenerioUtil
				 if(null != projects.getOrgLocationsId())
				 emailScenerioUtil.sendEmail(emailMessage,projects.getOrgLocationsId().getId(),iAdressArray,new ArrayList<EmailAttachment>());
				 
				 
				//calling the sendNotification method of emailScenerioUtil
				EmailScenario  notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.MAIL_PROJECT_MANAGER,Constants.ENTITY_TYPE_NOTIFICATION);
				String notificationBody = emailScenerioUtil.buildTempBody(notificationScenario.getSubject(),globalVarsMap);
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
				  	 StringBuilder fromNameBuilder=new StringBuilder();
				     EmailMessage emailMessage = new EmailMessage();
				     StringBuilder builder=new StringBuilder();
					 emailMessage.setFirstName(builder.append(emp.getUser().getFirstName()).append(" ").append(emp.getUser().getLastName()).toString());
					 emailMessage.setLastName(emp.getUser().getLastName());
					 emailMessage.setToEmail(emp.getUser().getEmail());
					 emailMessage.setFromEmail(loginUser.getEmail());
					 emailMessage.setCreatedBy(loginUser.getUserCode());
					 emailMessage.setFromName(fromNameBuilder.append(loginUser.getFirstName()).append(" ").append(loginUser.getLastName()).toString());
					 emailMessage.setEmailType(Constants.CUSTOM_USER_EMAIL);
					 //setting the class filed values
					 Map<String, String> globalVarsMap = new HashMap<String, String>();
					 StringBuilder sb=new StringBuilder();
					 globalVarsMap.put("loginUserFirstName",sb.append(loginUser.getFirstName()).append(" ").append(loginUser.getLastName()).toString());
					// globalVarsMap.put("loginUserLastName",loginUser.getLastName());
					 globalVarsMap.put("managerFirstName",projectManager.getFirstName());
					 globalVarsMap.put("managerLastName",projectManager.getLastName());
					 globalVarsMap.put("employeeFirstName",emailMessage.getFirstName());
					 globalVarsMap.put("employeeLastName",emailMessage.getLastName());
					 globalVarsMap.put("projectName", projects.getProjectName());
					 globalVarsMap.put("orgName",loginOrgLocation.getOrgLocationName());
					 StringBuilder loginURLBuilder=new StringBuilder();
					 globalVarsMap.put("loginURL",loginURLBuilder.append("<a href='").append(sourceleadUrl).append("/").append("' style='text-decoration:none; '><div style=' width:220px; border-radius:30px; background-color:#214b90; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Login</div></a>").toString());
					 
					 //setting the Recipient information
					 InternetAddress[] iAdressArray = InternetAddress.parse(emailMessage.getToEmail());
					 
					 //getting the current EmailScenario by EmailScenario unique name and entity type
					 EmailScenario  emailScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.MAIL_TEAM_MEMBERS,Constants.ENTITY_TYPE_EMAIL);
					
					 //covert subject,body and signature
					 String subject = emailScenerioUtil.bulidSubject(emailScenario.getSubject(),globalVarsMap);
					 String body = emailScenerioUtil.buildTempBody(emailScenario.getBody(),globalVarsMap);
					 String signature = emailScenerioUtil.buildSignature(emailScenario.getSignature(),globalVarsMap);
					 String url = sourceleadUrl;
					 
					 //construct HTML template using required data
					 String emailTemplate = emailScenerioUtil.AssembleEmailMessage(emailScenario,emailMessage, subject, body, signature, url, loginOrgLocation.getOrg(),loginOrgLocation);
					 emailMessage.setSubject(subject);
					 emailMessage.setMessageBody(emailTemplate);
					 
					 //calling the sendEmail method of emailScenerioUtil
					 if(null != projects.getOrgLocationsId())
					 emailScenerioUtil.sendEmail(emailMessage,projects.getOrgLocationsId().getId(),iAdressArray,new ArrayList<EmailAttachment>());
					 
					 
					//calling the sendNotification method of emailScenerioUtil
					EmailScenario  notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.MAIL_TEAM_MEMBERS,Constants.ENTITY_TYPE_NOTIFICATION);
					String notificationBody = emailScenerioUtil.buildBody(notificationScenario.getBody(),globalVarsMap);
					notificationScenario.setBody(notificationBody);
					notificationScenario.setOrgCode(loginOrgCode);
					//String url = null;
					String actionLink = "";
					emailScenerioUtil.sendNotification(actionLink, loginUser, emp.getUser(), loginOrgLocation, notificationScenario);
				}
			  
			  
			  private String  employeesList(JSONObject jsonObj, Projects projects,User user, OrgLocations loginOrgLocation,String loginOrgCode) throws Exception {
					
				  String teamMembersList = "";
					String pattern = "yyyy-MM-dd";
			        SimpleDateFormat format = new SimpleDateFormat(pattern);
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
												
												User approverUserId1 = new User();
												approverUserId1.setId(Long.valueOf(jsonObj.getString("level1Manager")));
												sendEmailToTeamMembers(projects, emp, user, approverUserId1, loginOrgLocation, loginOrgCode);
												
											}
											
											//commonUtil.storeTimesheetSubmissionRecords(emp.getUser());
											
											/**
										    * This teamMembersList string to store all team members for mail to project manager.
										    */
											StringBuilder teamStringBuilder=new StringBuilder();
											StringBuilder teamMembersStringBuilder=new StringBuilder();
											String teamMember = "";
											teamMember = teamStringBuilder.append(emp.getUser().getFirstName()).append(" ").append(emp.getUser().getLastName()).toString();
											teamMembersList = teamMembersStringBuilder.append(teamMembersList).append(teamMember).append("<br/>").toString();
									    }
									}
							    }
						 }
				    }
				return teamMembersList;
				}

/**
 * prepares email boday with table 
 */
@Override
public void userEmailToAdmin(StringBuilder tempHTML, OrgLocations orgLocations,User user1) throws Exception {
	     User user = null;
	     if(user1!=null){
	    	 user = user1;
	     }else{
	    	 user = orgLocations.getUser();
	     }
	     if(user != null){
	    	 StringBuilder fNameStringBuilder=new StringBuilder();
			 EmailMessage emailMessage = new EmailMessage();
			 emailMessage.setFirstName(user.getFirstName());
			 emailMessage.setLastName(user.getLastName());
			 emailMessage.setToEmail(user.getEmail());
			 emailMessage.setFromEmail(defaultFromEmailAddressForProd);
			 emailMessage.setFromName(fNameStringBuilder.append(user.getFirstName()).append(" ").append(user.getLastName()).toString());
			// emailMessage.setEmailType(Constants.CUSTOM_USER_EMAIL);
			//setting the class filed values
			 Map<String, String> globalVarsMap = new HashMap<String, String>();
			 globalVarsMap.put("firstName", emailMessage.getFirstName());
			 globalVarsMap.put("lastName",emailMessage.getLastName());
			 globalVarsMap.put("administratorFirstName", user.getFirstName());
			 globalVarsMap.put("administratorLastName",user.getLastName());
			 globalVarsMap.put("role",user.getUserType());
			 globalVarsMap.put("userEmailId",user.getEmail()); //
			 globalVarsMap.put("weekFrequency",tempHTML.toString());
			 //setting the Recipient information
			 InternetAddress[] iAdressArray = InternetAddress.parse(emailMessage.getToEmail());
			
			 //getting the current EmailScenario by EmailScenario unique name and entity type
			 EmailScenario  emailScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.TIMESHEET_MANAGER_NOTIFICATION,Constants.ENTITY_TYPE_EMAIL);
			
			 //covert subject,body and signature
			 String subject = emailScenerioUtil.bulidSubject(emailScenario.getSubject(),globalVarsMap);
			 String body = emailScenerioUtil.buildTempBody(emailScenario.getBody(),globalVarsMap);
			 String signature = emailScenerioUtil.buildSignature(emailScenario.getSignature(),globalVarsMap);
			 String url = sourceleadUrl;
			 
			 //construct HTML template using required data
			 String emailTemplate = emailScenerioUtil.AssembleEmailMessage(emailScenario,emailMessage, subject, body, signature, url, orgLocations.getOrg(),orgLocations);
			 emailMessage.setSubject(subject);
			 emailMessage.setMessageBody(emailTemplate);
			 
			 //calling the sendEmail method of emailScenerioUtil
			emailScenerioUtil.sendEmail(emailMessage,orgLocations.getId(),iAdressArray,new ArrayList<EmailAttachment>());
			if(orgLocations != null){
				sendWeeklyBaseNotiFicationToAdmin(user,orgLocations);	
			}
			 
	     }
	    
		
}


			@Override
			public Integer getProjectApproversCountByUserIdAndOrgLocationId(Long userId, Long orgLocationId)
					throws Exception {
				return projectsDao.getProjectApproversCountByUserIdAndOrgLocationId(userId, orgLocationId);
			}

			
			
			@Override
			public void tereminateAsProjectApprover(JSONObject jsonObj,User user,Long userId,OrgLocations orgLocations) throws Exception {
				
				   boolean flag = false;
				
		    		List<ProjectApproverHierarchy> projectApproverList = projectApproverHierarchyService.getProjectApproversListByUserIdAndOrgLocationId(userId,orgLocations.getId());
		    		Integer projectApproverCount = getProjectApproversCountByUserIdAndOrgLocationId(userId,orgLocations.getId());
		    		for(ProjectApproverHierarchy hierarchy : projectApproverList){
		    			
							
						   
							User usr = userService.getUserById(Long.valueOf(jsonObj.getString("projectApproverUser")));
							User terminatedUser = hierarchy.getApproverUserId();
							hierarchy.setApproverUserId(usr);
							hierarchy.setUpdatedBy(user.getUserCode());
							hierarchy.setUpdatedDate(new Date());
						
							
							List<ProjectApproverHierarchy> hierarchyList = projectApproverHierarchyService.getProjectsHierarchyListByProjectIdAndOrgLocationId(hierarchy.getProjectId().getId(), orgLocations.getId());	
							for(ProjectApproverHierarchy approverHierarchy : hierarchyList){
								if(approverHierarchy.getApproverUserId().getId().equals(usr.getId())){
									flag = true;
								}
							}
							
							/*if(flag == false){
								
					    			//getting projectmember userId's who are added to the project
					    					ProjectMembers pm = storeProjectMemberByMemberUserIdAndLoginUser(usr.getId(), user, hierarchy.getProjectId(), new Date());
					    					if(pm != null){
					    						commonUtil.storeTimesheetSubmissionRecords(user);
					    					}
										 
							}*/
							ProjectApproverHierarchy assignedProjectManager=null;
							 if(hierarchy.getProjectId() != null && hierarchy.getProjectId().getId() != null){
			    					ProjectMembers pm1 = projectMembersService.getProjectMemberByUserIdAndProjectId(terminatedUser.getId(), hierarchy.getProjectId().getId());
				    				if(pm1.getId() != null){
				    					ProjectMembersHistory projectMembersHistory = projectMembersHistoryService.getProjectMembersOpenHistoryByProjectMemberId(pm1.getId());
				    					
				    					if(null != projectMembersHistory){
				    						projectMembersHistory.setProjectTerminationDate(new Date());
					    					projectMembersHistoryService.updateProjectMembersHistory(projectMembersHistory);
				    					}
				    					
					    				commonUtil.terminateProjectMemberByProjectMemberId(pm1.getId(), Primary.Yes);
				    				}
				    				
				    				List<TimesheetSubmissions> submissionsList = timesheetSubmissionsService.getTimeSheetSubmissionByProjectMemberId(pm1.getId());
				    				if(null != submissionsList){
				    					for(TimesheetSubmissions submissions : submissionsList){
				    						submissions.setStatus(Constants.MEMBER_IN_ACTIVE);
				    						submissions.setUpdatedBy(user.getUserCode());
				    						submissions.setUpdatedDate(new Date());
				    						timesheetSubmissionsService.addTimesheetSubmissions(submissions);
				    					}
				    				}
				    				 
			    				}
						
							 assignedProjectManager=projectApproverHierarchyService.updateProjectApproverHierarchy(hierarchy);
							 assignedNewUserAsaassignedProjectManager(user,usr,userId,orgLocations,projectApproverCount);
		    		}
		    		
			}
			
			
			
			
			private void assignedNewUserAsaassignedProjectManager(User loginUser, User assUserName, Long userId, OrgLocations orgLocations,Integer projectApproverCount) throws Exception {
				 EmailMessage emailMessage = new EmailMessage();
				 emailMessage.setFirstName(loginUser.getFirstName());
				 emailMessage.setLastName(loginUser.getLastName());
				 emailMessage.setToEmail(assUserName.getEmail());
				 emailMessage.setFromEmail(loginUser.getEmail());
				 emailMessage.setCreatedBy(loginUser.getUserCode());
				 StringBuilder msgBuild = new StringBuilder();
				emailMessage.setFromName(msgBuild.append(loginUser.getFirstName()).append(" ").append(loginUser.getLastName()).toString());
				emailMessage.setEmailType(Constants.CUSTOM_USER_EMAIL);
				//setting the class filed values
				 Map<String, String> globalVarsMap = new HashMap<String, String>();
				 	 User termentaedUserName = userService.getUserById(userId);
					 globalVarsMap.put("loginUser", loginUser.getFirstName());
					 globalVarsMap.put("assignedUser", assUserName.getFirstName());
					 globalVarsMap.put("reqCount", projectApproverCount.toString());
					 globalVarsMap.put("terminatedUser",termentaedUserName.getFirstName());
					 StringBuilder loginBuilder = new StringBuilder();
					 globalVarsMap.put("loginURL",loginBuilder.append("<a href='").append(sourceleadUrl).append("/").append("' style='text-decoration:none; '><div style=' width:220px; border-radius:30px; background-color:#214b90; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Login</div></a>").toString());
				  
				  
				
				 InternetAddress[] iAdressArray = InternetAddress.parse(emailMessage.getToEmail());
				
				 //getting the current EmailScenario by EmailScenario unique name and entity type
				 //using same migration which has been already used in updatedRequirementMailToManager method
				 EmailScenario  emailScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.USER_NEW_ACTION_ASSIGNED_As_A_PROJECT_APROVER,Constants.ENTITY_TYPE_EMAIL);
				 EmailScenario  notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.USER_NEW_ACTION_ASSIGNED_As_A_PROJECT_APROVER,Constants.ENTITY_TYPE_NOTIFICATION);
			
				 
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
				 emailScenerioUtil.sendNotification(actionLink, loginUser, assUserName, orgLocations, notificationScenario);
				}
			
			
			
			

			@Override
			public List<Projects> getProjectBasedClientsByOrgLocationsIdAndApproverUserId(Long orgLocationsId,
					Long userId) throws Exception {
				return projectsDao.getProjectBasedClientsByOrgLocationsIdAndApproverUserId(orgLocationsId, userId);
			}

			/*@Override
			public HashSet<User> getAllUserTimeSheetSubmissionStatusCurrentWeek() throws Exception {
				return projectsDao.getAllUserTimeSheetSubmissionStatusCurrentWeek();
			}
			  */
			
			@Override
			public void saveOrUpdateProjectElkDocument(Projects projects) throws Exception{
				List<ProjectApproverHierarchy> projectApproverHierarchy = projectApproverHierarchyService.getApproverUsersByProjectId(projects.getId());
				Collection<ProjectMembers> projectMembers = projectMembersService.getAllProjectMembersByProjectId(projects.getId());
;				Projects updatedProject = getProjectById(projects.getId());
				 //Elastic Search Document Saving
				ProjectsIndex projectsIndex = projectsIndexHelper.getProjectsIndexFromProjects(updatedProject, projectApproverHierarchy, projectMembers);
				sourceleadELKBaseIndexer.saveOrUpdateElkDocument(projectsIndex, Constants.ELK_PROJECT_INDEX, Constants.ELK_PROJECT_TYPE,updatedProject.getId().toString());
			}
			
			@Override
			public Integer getProjectCountByOrgLocationId(Long orgLocationId)
					throws Exception {
				return projectsDao.getProjectCountByOrgLocationId(orgLocationId);
			}

			@Override
			public List<Object> getAllUniversalSearchProjectsList(OrgLocations orgLocation, String searchValue) throws Exception {
				List<Object>   searchList = new ArrayList<>();
				BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
				boolQueryBuilder.must(QueryBuilders.matchQuery("orgLocationId", orgLocation.getId()));
				 //Integer maxResults = projectsService.getAllProjects().size();
				 
				 Integer maxResults = getProjectCountByOrgLocationId(orgLocation.getId());
					
					//All Keywords
				 ArrayList<String> anyKeyWordArray = universalSearchHelper.getSearchValuesFromChosenSelectField(searchValue);
					if(anyKeyWordArray != null && !anyKeyWordArray.isEmpty()){
						 BoolQueryBuilder anyBoolQueryBuilder = QueryBuilders.boolQuery();
						 for(String keywd : anyKeyWordArray){
							 BoolQueryBuilder anyBoolSubQueryBuilder = QueryBuilders.boolQuery();
							ArrayList<MatchQueryBuilder> matchQueryBuilderList = universalSearchHelper.getProjectsMatchQueryBuilderList(keywd);
							if(matchQueryBuilderList != null && !matchQueryBuilderList.isEmpty()){
								for(MatchQueryBuilder matchQueryBuilder :matchQueryBuilderList){
									anyBoolSubQueryBuilder.should(matchQueryBuilder);
								}
								anyBoolQueryBuilder.should(anyBoolSubQueryBuilder);
							}
						}
						 boolQueryBuilder.must(anyBoolQueryBuilder);
					}
					DateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
					df.setTimeZone(TimeZone.getTimeZone("GMT"));
					SearchHit[] searchHits = sourceleadELKBaseIndexer.searchDocumentWithMaxResults(Constants.ELK_PROJECT_INDEX, Constants.ELK_PROJECT_TYPE,0,maxResults.intValue(),boolQueryBuilder).getHits();
					if(searchHits != null && searchHits.length >0){
						ObjectMapper mapper = new ObjectMapper();
						for (SearchHit hit : searchHits) {
							String json = hit.getSourceAsString();
							mapper.setDateFormat(df);
							ProjectsIndex projectsIndex = mapper.readValue(json, ProjectsIndex.class);
							searchList.add(projectsIndex);
						}
					}
				
				return searchList;
			}

			@Override
			public Boolean validateTasksName(String taskname, Long projectId) throws Exception {
				Boolean flag = projectsDao.validateTasksName(taskname,projectId);
				return flag;
			}

			@Override
			public List<Projects> getClientProjectsByOrgRelationId(Long orgRelationId, Long orgLocationId) throws Exception
			{
				return projectsDao.getClientProjectsByOrgRelationId(orgRelationId,orgLocationId);
			}

			@Override
			public List<Projects> getAllProjectsByOrgLocationIdAndStatus(Long orgLocationId) throws Exception
			{
				return projectsDao.getAllProjectsByOrgLocationIdAndStatus(orgLocationId);
			}

			@Override
			public Projects getProjectByProjectUUID(String projectUUID) throws Exception {
				return projectsDao.getProjectByProjectUUID(projectUUID);
			}


}
