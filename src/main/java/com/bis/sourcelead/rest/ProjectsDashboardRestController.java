package com.bis.sourcelead.rest;

import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bis.sourcelead.SourceleadPasswordEncoder;
import com.bis.sourcelead.constants.Constants;
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
import com.bis.sourcelead.service.TimesheetSubmissionsService;
import com.bis.sourcelead.service.UserRoleService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.EmailScenerioUtil;
import com.bis.sourcelead.util.SourceLeadRandomCodeHelper;
import com.bis.sourcelead.util.SourceLeadSessionManager;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.json.JsonHierarchicalStreamDriver;
import com.thoughtworks.xstream.io.json.JsonWriter;

/**
 * 
 * @author David Sammeta
 *
 */

@RestController
public class ProjectsDashboardRestController {
	
	private static final Logger logger = LoggerFactory.getLogger(ProjectsDashboardRestController.class);
	
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	 @Autowired
	 private ProjectsService projectsService; 
	 
	 @Autowired
	 private ProjectTasksService tasksService;
	 
	 @Autowired
	 private ProjectMembersHistoryService projectMembersHistoryService;
	 
	 @Autowired
	 public UserService userService;
	
	 @Autowired
	 public EmployeesService employeesService;
	
	 @Autowired
	 public ProjectMembersService projectMembersService;
	
	 @Autowired
	 public ProjectApproverHierarchyService projectApproverHierarchyService;
	 
	 @Autowired
	 public SubmissionApproverHierarchyService submissionApproverHierarchyService;
	 
	 @Autowired
	 private OrgLocationService orgLocationService;
	 
	 @Autowired
	 private OrgRelationsService orgRelationsService;
	 
	 @Autowired
	 public RolesService rolesService;
	 
	 @Autowired
	 public UserRoleService userRoleService;
	 
	 @Autowired
	 CommonUtil commonUtil;
	 
	 @Autowired
	 EmailScenarioService emailScenarioService;
	 
	 @Autowired
	 TimesheetSubmissionsService timesheetSubmissionsService;
	
	 @Autowired
	 EmailScenerioUtil emailScenerioUtil;
	 
	 @Autowired
	 private SourceleadPasswordEncoder sourceleadPasswordEncoder;
	
/**
 * to filter the projects based on client and project status
 * @author David Sammeta
 * @param values
 * @param request
 * @param clients
 * @return String
 * @throws Exception
 */
			@RequestMapping(value = "/getProjectsByStatusAndClient")
			public String getProjectsByStatusAndClient(@RequestParam(value="values", required=false) String values,HttpServletRequest request,@RequestParam(value="clients", required=false) String clients) throws Exception {

			    String loginOrgLocation = (String) SourceLeadSessionManager.getSessionAttribute(request, "locationCode");
			    OrgLocations loginOrgLocations = orgLocationService.getOrgLocationsByLocationCode(loginOrgLocation);
				String resultJson = "";
				User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			    List<Projects> projectsList = projectsService.getProjectsBasedOnClientsAndStatusSelectionInFilters(user.getId(), clients, values,loginOrgLocations.getId());
			    for(Projects ps : projectsList){
			    	ps.setManagerLevel1(ps.getManagerLevel1());
			    	if(ps.getManagerLevel2() != null){
			    		ps.setManagerLevel2(ps.getManagerLevel2());	
			    	}
			    }
				 resultJson = commonUtil.toJSON(projectsList);
				 return resultJson;
			}
	
	/**
	 * get project details by project Id
	 * @param id
	 * @return Json
	 * @throws Exception
	 */
	 @RequestMapping(value = "/getProjectById", method = RequestMethod.POST)
	 public @ResponseBody String getProjectById(@RequestParam(value="ProjectId", required=false) Long projectId,HttpServletRequest request) throws Exception{ 
		   String loginOrgLocation = (String) SourceLeadSessionManager.getSessionAttribute(request, "locationCode");
		   OrgLocations loginOrgLocations = orgLocationService.getOrgLocationsByLocationCode(loginOrgLocation);
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			Projects projects = projectsService.getProjectByIdAndLoginOrgId(projectId, loginOrgLocations.getId());
			
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
	 
	 @RequestMapping(value = "/updateProjectByProjectId", method = RequestMethod.POST)
	 public String updateProjectByProjectId(@RequestParam(value="json", required=false) String json,@RequestParam(value="ProjectId", required=false) Long projectId,HttpServletRequest request) throws Exception{ 

		 json = "{"+json+"}";	
		 JSONObject jsonObj = new JSONObject(json);
		 Date currentDate = new Date();
		 Date previousEndDate = null;
		 
		 User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user"); 
		 Projects projects = projectsService.getProjectById(projectId);
		 
		 String pattern = "yyyy-MM-dd";
	     SimpleDateFormat format = new SimpleDateFormat(pattern);
	     
			    if(jsonObj.getString("ProjectSequeneNumber") != null){
			    	 projects.setProjectSequeneNumber(jsonObj.getString("ProjectSequeneNumber"));
			    }
		    	projects.setProjectName(jsonObj.getString("projectName"));
		    	if(jsonObj.getString("description") != null){
		    		projects.setDescription(jsonObj.getString("description"));
		    	}
		    	projects.setProjectStatus(Project_Status.getNameByValue(jsonObj.getString("projectStatusId")));
		    	if(jsonObj.getString("endDateStr1") != null){
		    		previousEndDate = projects.getEndDate();
		    		Date endDate = format.parse(jsonObj.getString("endDateStr1"));
					projects.setEndDate(endDate);
		    	}
		    	if(jsonObj.has("clientId")){
		    		OrgRelations orgRelations = new OrgRelations();
		    		if(jsonObj.getString("clientId") != null && jsonObj.getString("clientId") != ""){
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
			    	 projectsService.updateProject(projects);
			    }
			    else if(projects.getEndDate() != null && currentDate.after(projects.getEndDate()) && !(projects.getEndDate().equals(currentDate))) {
			    	projects = commonUtil.terminateProject(projects);
			    	projectsService.updateProject(projects);
			    }
			    else{
			    	projects = projectsService.updateProject(projects);
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
	  * update the project by project ID
	  * @author David Sammeta
	  * @param projectId
	  * @param fieldName
	  * @param fieldValue
	  * @param request
	  * @return String
	  * @throws Exception
	  */

	 @RequestMapping(value = "/updateProjectById", method = RequestMethod.POST)
	 public String updateProjectById(@RequestParam(value="json", required=false) String json,@RequestParam(value="ProjectId", required=false) Long projectId,@RequestParam(value="fieldName", required=false) String fieldName,@RequestParam(value="fieldValue", required=false) String fieldValue,HttpServletRequest request) throws Exception{ 

		 json = "{"+json+"}";	
		 JSONObject jsonObj = new JSONObject(json);
		    
		 User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user"); 
		 Projects projects = projectsService.getProjectById(projectId);
		    if("projectName".equalsIgnoreCase(fieldName)){
		    	projects.setProjectName(fieldValue);
		    }
		    else if ("description".equalsIgnoreCase(fieldName)) {
		    	projects.setDescription(fieldValue);
			}else if("projectSequeneNumber".equalsIgnoreCase(fieldName)){
				if(fieldValue != null && fieldValue != ""){
	        	  projects.setProjectSequeneNumber(fieldValue);
				}
	        }else if("endDateStr".equalsIgnoreCase(fieldName))  {
			 String pattern = "yyyy-MM-dd";
		     SimpleDateFormat format = new SimpleDateFormat(pattern);
	          if(fieldValue != null && fieldValue != ""){
	        	  Date endtDate = format.parse(fieldValue);
					projects.setEndDate(endtDate);
	         }
			}else if ("projectStatusId".equalsIgnoreCase(fieldName)) {
		    	projects.setProjectStatus(Project_Status.getNameByValue(fieldValue));
			}else if (fieldName.equalsIgnoreCase("clientId")) {
				OrgRelations orgRelations = new OrgRelations();
				 if(fieldValue != null && fieldValue != ""){
				orgRelations.setId(Long.valueOf(fieldValue));
		    	projects.setOrgRelationId(orgRelations);
				 }
			}else if ("level1Manager".equalsIgnoreCase(fieldName)) {
				if(fieldValue != null && fieldValue != ""){
					Collection<ProjectApproverHierarchy> projectApproverHierarchies = projectApproverHierarchyService.getApproverUsersByProjectId(projectId);
					 for(ProjectApproverHierarchy prHierarchy :projectApproverHierarchies ){
						 if(prHierarchy.getLevel() == 1){
							 ProjectApproverHierarchy pah  = projectApproverHierarchyService.getByProjectApproverHierarchyId(prHierarchy.getId());
							 Long fieldValuelong = Long.valueOf(fieldValue);
							 User usr = userService.getUserById(fieldValuelong);
							 pah.setApproverUserId(usr);
							 projectApproverHierarchyService.updateProjectApproverHierarchy(pah);
						 }
					 }
				 }
			}else if ("level2Manager".equalsIgnoreCase(fieldName)) {
				if(fieldValue != null && fieldValue != ""){
					Collection<ProjectApproverHierarchy> projectApproverHierarchies = projectApproverHierarchyService.getApproverUsersByProjectId(projectId);
					 for(ProjectApproverHierarchy prHierarchy :projectApproverHierarchies ){
						 if(prHierarchy.getLevel() == 2){
							 ProjectApproverHierarchy pah  = projectApproverHierarchyService.getByProjectApproverHierarchyId(prHierarchy.getId());
							 Long fieldValuelong = Long.valueOf(fieldValue);
							 User usr = userService.getUserById(fieldValuelong);
							 pah.setApproverUserId(usr);
							 projectApproverHierarchyService.updateProjectApproverHierarchy(pah);
						 }
					 }
				 }
			}
			    projects.setUpdatedBy(user.getUserCode());
			    projects.setUpdatedDate(new Date());
			    projectsService.updateProject(projects);
			    return null;
		  }
	 
/**
 * update project task by task id	 
 * @author David Sammeta
 * @param taskId
 * @param fieldName
 * @param fieldValue
 * @return String
 * @throws Exception
 */
	 
	 @RequestMapping(value = "/updateProjectTaskById", method = RequestMethod.POST)
	 public String updateProjectTaskById(@RequestParam(value="taskId", required=false) String taskId,@RequestParam(value="fieldName", required=false) String fieldName,@RequestParam(value="fieldValue", required=false) String fieldValue) throws Exception{ 
		    
		    ProjectTasks projectTasks = tasksService.getTasksId(taskId);
		    if(fieldName.equalsIgnoreCase("taskName")){  
		    	projectTasks.setTaskName(fieldValue);
		    }
		    else if (fieldName.equalsIgnoreCase("description")) {
		    	projectTasks.setDescription(fieldValue);
			}     
		    tasksService.updateTasks(projectTasks);
		    return null;
	  }
	 
	 /**
	  * save the projects task
	  * @param json
	  * @param projectId
	  * @return JSON
	  * @throws Exception
	  */
	 @RequestMapping(value = "/saveProjectTasks" , method = RequestMethod.POST)
	 public @ResponseBody  String saveProjectTasks(@RequestParam(value="json", required=false) String json,
			 @RequestParam(value="projectId", required=false) Long projectId) throws Exception {
		 	
		    json = "{"+json+"}";	
		    JSONObject jsonObj = new JSONObject(json);
		 	
		 	String resultJson = null;
		 	String maxAdminTimeTypeRowNum = jsonObj.getString("maxTsRowNum");
			int maxCount = Integer.parseInt(maxAdminTimeTypeRowNum);
			
			for (int c = 1; c < maxCount; c++) {
				ProjectTasks  tasks  = new ProjectTasks();
				if(jsonObj.has(c+"taskName")){
					tasks.setTaskName(jsonObj.getString(c+"taskName"));
					if(jsonObj.has(c+"taskDescription")){
					tasks.setDescription(jsonObj.getString(c+"taskDescription"));
					}
				tasks.setCreatedDate(new Date());
				tasks.setUpdatedDate(new Date());
				Projects projects = projectsService.getProjectById(projectId);
				tasks.setProjectId(projects);	
				
				ProjectTasks savedtasks = tasksService.addTasks(tasks);
				}
			}
			Collection<ProjectTasks> taskList=tasksService.getAllTasksByProjectId(projectId);
		 	
			 resultJson = commonUtil.toJSON(taskList);
			 return resultJson;
		 	
	 }
	 
	/** 
	 * get projects tasks based on project id
	 * @param projectId
	 * @return JSON
	 * @throws Exception
	 */
	 @RequestMapping(value = "/getProjectTasks" , method = RequestMethod.POST)
	 public @ResponseBody  String getProjectTasks(
			 @RequestParam(value="projectId", required=false) Long projectId) throws Exception {
		 	
		 	 String resultJson = null;
			 Collection<ProjectTasks> taskList=tasksService.getAllTasksByProjectId(projectId);
			 resultJson = commonUtil.toJSON(taskList);
			 return resultJson;
		 	
	 }
/**
 * Get Employees list based on project id
 * @param projectId
 * @return JSON
 * @throws Exception
 */
	 @RequestMapping(value = "/getProjectEmployeeListById", method = RequestMethod.POST)
	 public @ResponseBody String getProjectEmployeeListById(@RequestParam(value="projectId", required=false) Long projectId) throws Exception{ 
		 
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
	  * Save Employee from project summary
	  * @param json
	  * @param request
	  * @return JSON
	  * @throws Exception
	  */
	 @RequestMapping(value = "/addEmployeeForProjectDashboard",  method = RequestMethod.POST)
		public @ResponseBody  String addEmployeeForProjectDashboard(@RequestParam(value="json", required=false) String json,HttpServletRequest request) throws Exception {
		    json = "{"+json+"}";
		    JSONObject jsonObj = new JSONObject(json);
		 	OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		 	User users = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
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
			sendEmail(user,users,password);
			}
			
			return null;
		}
/**
 * Get Teammembers list by project id	
 * @param projectId
 * @param request
 * @return
 * @throws Exception
 */

	@RequestMapping(value = "/getTeamMembersList",  method = RequestMethod.POST)
		public @ResponseBody  String getTeamMembersList(@RequestParam(value="ProjectId", required=false) Long projectId, HttpServletRequest request) throws Exception {
		 	String resultJson = null;
		 	String loginOrgLocation = (String) SourceLeadSessionManager.getSessionAttribute(request, "locationCode");
			OrgLocations loginOrgLocations = orgLocationService.getOrgLocationsByLocationCode(loginOrgLocation);
			Collection<Employees> employeeList = employeesService.getEmployeesExcludeTeamMembers(projectId, loginOrgLocations.getId());
			resultJson = commonUtil.toJSON(employeeList);
			return resultJson;
		}
/**
 * Assign Employee to project by  project id	 
 * @param projectId
 * @param employeeId
 * @param startDate
 * @param endDate
 * @param mailtoMembers
 * @param request
 * @return Json
 * @throws Exception
 */
	 @RequestMapping(value = "/storeAssignEmployee",  method = RequestMethod.POST)
		 public @ResponseBody  String storeAssignEmployee(
				@RequestParam(value="ProjectId", required=false) Long projectId,
				@RequestParam(value="employeeId", required=false) Long employeeId,
				@RequestParam(value="startDate", required=false) String startDate,
				@RequestParam(value="endDate", required=false) String endDate,
				@RequestParam(value="mailtoMembers", required=false) String mailtoMembers, HttpServletRequest request) throws Exception {
		 	
			 	ProjectMembers pm = new ProjectMembers();
			 	OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			 	String loginOrgCode = (String) SourceLeadSessionManager.getSessionAttribute(request, "orgCode");
			 	User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			 	
			  if(projectId != null){
				  
					Projects projects = projectsService.getProjectById(projectId);
					Employees emp = employeesService.getEmployee(employeeId.toString());
				 	pm.setEmployee(emp);
					pm.setProjects(projects);
					String pattern = "yyyy-MM-dd";
		            SimpleDateFormat format = new SimpleDateFormat(pattern);
		            
		            HashSet<ProjectMembersHistory> pmhList = new HashSet<ProjectMembersHistory>();
					
					ProjectMembersHistory pmh = new ProjectMembersHistory();
					pmh.setProjectMemberId(pm);
					pmh.setStatus(Constants.PROJECT_MEMBER_HISTORY_OPEN);
		            if(StringUtils.isNotEmpty(startDate)){
		            	 Date currentStartDate = format.parse(startDate);
		            	 pmh.setProjectAssignedDate(currentStartDate);
		            }
		            else{
		            	pmh.setProjectAssignedDate(projects.getStartDate());
		            }
		            if(StringUtils.isNotEmpty(endDate)){
		            	if(endDate != null && endDate != "--"){
		            		Date currentEndDate = format.parse(endDate);
		            		pmh.setProjectTerminationDate(currentEndDate);
		            	}
		            	
		            }else{
		            	 pmh.setProjectTerminationDate(projects.getEndDate());
		            }
		            pmh.setCreatedDate(new Date());
					pmh.setCreatedBy(user.getUserCode());
					pmh.setUpdatedDate(new Date());
					pmh.setUpdatedBy(user.getUserCode());
					pmhList.add(pmh);
		            
		            pm.setStatus(Constants.MEMBER_ACTIVE);
					pm.setCreatedDate(new Date());
					pm.setUpdatedDate(new Date());
					pm.setCreatedBy(user.getUserCode());
					pm.setUpdatedBy(user.getUserCode());
					pm.setMemberHistoryList(pmhList);
					projectMembersService.saveProjectMember(pm);
					commonUtil.storeTimesheetSubmissionRecords(emp.getUser());
					
					/**
				     * This method for send Email to team member while assign employee from project dashboard
				     */
					ProjectApproverHierarchy projectApproverHierarchy = projectApproverHierarchyService.getLevelOneProjectApproverByProjectId(projects.getId());
					
					if(!isNullOrEmpty(mailtoMembers) && ("YES".equals(mailtoMembers))){
						sendEmailToTeamMembers(projects, emp, user, projectApproverHierarchy, loginOrgLocation, loginOrgCode);
					}
			  }
				return null;
		}
	 
	 /**
	  * This method for send Email to team member
	  * @return
	  * @throws Exception
	  * @author  venkatKesav
	  * @since   2016-07-22
	  */
	  private void sendEmailToTeamMembers(Projects projects, Employees emp, User loginUser, ProjectApproverHierarchy projectApproverHierarchy, OrgLocations loginOrgLocation, String loginOrgCode) throws Exception {
		  	 User projectManager = userService.getUserById(projectApproverHierarchy.getApproverUserId().getId());
		  	
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
			 String url = sourceleadUrl;;
			 
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
	  
	  /**
	  * This method for send Email to user creation
	  * @return
	  * @throws Exception
	  * @author  venkatKesav
	  * @since   2016-07-25
	  */
	  private void sendEmail(User user, User loginUser ,String password) throws Exception {
		  
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
			 String url = null;
			 
			 //construct HTML template using required data
			 String emailTemplate = emailScenerioUtil.AssembleEmailMessage(emailScenario,emailMessage, subject, body, signature, url, new Org());
			 emailMessage.setSubject(subject);
			 emailMessage.setMessageBody(emailTemplate);
			 
			 //calling the sendEmail method of emailScenerioUtil
			 emailScenerioUtil.sendEmail(emailMessage,emailRecipientList,new ArrayList<EmailAttachment>());
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
		String roleName = "ROLE_USER";
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
}
