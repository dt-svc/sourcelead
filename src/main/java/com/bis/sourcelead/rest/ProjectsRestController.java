package com.bis.sourcelead.rest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.annotations.Validator;
import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dto.ClientProjectDTO;
import com.bis.sourcelead.dto.ProjectAdditionalDetailsDTO;
import com.bis.sourcelead.dto.ProjectAssignedMembersDTO;
import com.bis.sourcelead.dto.ProjectsDTO;
import com.bis.sourcelead.elk.index.ProjectsIndex;
import com.bis.sourcelead.elk.index.helper.ProjectsIndexHelper;
import com.bis.sourcelead.elk.setup.SourceleadELKBaseIndexer;
import com.bis.sourcelead.entity.Employees;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgRelations;
import com.bis.sourcelead.entity.ProjectClients;
import com.bis.sourcelead.entity.ProjectMembers;
import com.bis.sourcelead.entity.ProjectMembersHistory;
import com.bis.sourcelead.entity.ProjectQuestionnaire;
import com.bis.sourcelead.entity.ProjectTasks;
import com.bis.sourcelead.entity.Projects;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserRole;
import com.bis.sourcelead.service.EmailScenarioService;
import com.bis.sourcelead.service.EmployeesService;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.OrgRelationsService;
import com.bis.sourcelead.service.OrgService;
import com.bis.sourcelead.service.ProjectApproverHierarchyService;
import com.bis.sourcelead.service.ProjectClientService;
import com.bis.sourcelead.service.ProjectMembersHistoryService;
import com.bis.sourcelead.service.ProjectMembersService;
import com.bis.sourcelead.service.ProjectQuestionnaireService;
import com.bis.sourcelead.service.ProjectTasksService;
import com.bis.sourcelead.service.ProjectsService;
import com.bis.sourcelead.service.RolesService;
import com.bis.sourcelead.service.UserRoleService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.EmailScenerioUtil;
import com.bis.sourcelead.util.FileUploadUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;

@RestController
@PropertySource("${propertyLocation:app.properties}")
public class ProjectsRestController {
	
	@Value("${static.path}")
	private String staticPath;

	@Value("${sourceleadUrl}")
	private String sourceleadUrl;

	@Autowired
	EmailScenarioService emailScenarioService;

	@Autowired
	EmailScenerioUtil emailScenerioUtil;

	@Autowired
	private ProjectsService projectsService;

	@Autowired
	private OrgService orgService;

	@Autowired
	private OrgLocationService orgLocationService;

	@Autowired
	private ProjectTasksService tasksService;

	@Autowired
	public UserService userService;

	@Autowired
	public EmployeesService employeesService;

	@Autowired
	public UserRoleService userRoleService;

	@Autowired
	public ProjectApproverHierarchyService projectApproverHierarchyService;

	@Autowired
	public ProjectMembersService projectMembersService;

	@Autowired
	public RolesService rolesService;

	@Autowired
	CommonUtil commonUtil;

	@Autowired
	ProjectsIndexHelper projectsIndexHelper;

	@Autowired
	SourceleadELKBaseIndexer<ProjectsIndex> sourceleadELKBaseIndexer;

	@Autowired
	ProjectTasksService projectTasksService;
	
	@Autowired
	ProjectQuestionnaireService projectQuestionnaireService;
	
	@Autowired
	ProjectMembersHistoryService projectMembersHistoryService;
	
	@Autowired
	OrgRelationsService orgRelationsService;
	
	@Autowired
	ProjectClientService projectClientService;

	/**
	 * This method for save the project by json
	 * 
	 * @param json
	 * @param request
	 * @return value is Project pojo
	 * @throws Exception
	 */

	@RequestMapping(value = "/storeProject", method = RequestMethod.POST)
	public @ResponseBody Projects storeProject(ProjectsDTO projectsDTO) throws Exception {
		
		Projects projects = null;
		 if(Validator.validate(projectsDTO)) {
			 projectsDTO.setStaticPath(staticPath);
			 FileUploadUtil.fillUploadedFileInfo(projectsDTO, projectsDTO.getFileInfo());
			 projects = projectsService.storeProject(projectsDTO);
		 }
		return projects;
	}
	
	
	
       @RequestMapping(value = "/assignProjectMembersToProject", method = RequestMethod.POST)
		public  String assignProjectMembersToProject(ProjectAssignedMembersDTO projectAssignedMembersDTO,@RequestParam(value="projectUUID", required=true) String projectUUID,
			   @RequestParam(value="userId", required=true) Long userId,@RequestParam(value="orgLocationId", required=true) Long orgLocationId) throws Exception {
		    
		    User user = userService.getUserById(userId);
		    OrgLocations orgLocations = orgLocationService.getOrgLocations(orgLocationId);
		    projectMembersService.saveAssignedProjectMembers(projectAssignedMembersDTO, projectUUID, user, orgLocations);
		   
		    return "success";
	    }
       
       
       @RequestMapping(value = "/storeProjectAdditionalDetails", method = RequestMethod.POST)
		public  String storeProjectAdditionalDetails(ProjectAdditionalDetailsDTO projectAdditionalDetailsDTO,@RequestParam(value="projectId", required=true) Long projectId,
			   @RequestParam(value="userId", required=true) Long userId,@RequestParam(value="orgLocationId", required=true) Long orgLocationId,
			   @RequestParam(value="saveType", required=true) String saveType) throws Exception {
		    
		    User user = userService.getUserById(userId);
		    OrgLocations orgLocations = orgLocationService.getOrgLocations(orgLocationId);
		    projectAdditionalDetailsDTO.setSaveType(saveType);
		    projectQuestionnaireService.saveProjectQuestionnaireDetails(projectAdditionalDetailsDTO, projectId, user, orgLocations);
		   
		    return "success";
	    }
       
       
       
       
	   @RequestMapping(value = "/storeClientProject", method = RequestMethod.POST)
	   	public @ResponseBody Projects storeClientProject(ClientProjectDTO clientProjectDTO) throws Exception {
	   		
	   		Projects projects = null;
	   		projects = projectsService.storeClientProject(clientProjectDTO);
	   		
	   		return projects;
	   	}
       
       
      
       
       @RequestMapping(value = "/getProjectAssignedMembesListByProjectId", method = RequestMethod.GET)
		public  List<ProjectMembers> getProjectAssignedMembesListByProjectId(@RequestParam(value="projectId", required=true) Long projectId,HttpServletRequest request) throws Exception {
    	   
    	   OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
    	   List<ProjectMembers> projectMembersList = projectMembersService.getAllProjectMembersByProjectIdAndStatus(projectId,"Active",orgLocations);
		   return projectMembersList;
	    }
       
       
       @RequestMapping(value = "/getProjectQuestionnaireDetails", method = RequestMethod.GET)
		public  ProjectQuestionnaire getProjectQuestionnaireDetails(@RequestParam(value="projectId", required=true) Long projectId) throws Exception {
    	   ProjectQuestionnaire projectQuestionnaire = projectQuestionnaireService.getProjectQuestionnaireByProjectId(projectId);
		   return projectQuestionnaire;
	    }
       
       

	@RequestMapping(value = "/validateProjectId", method = RequestMethod.POST)
	public @ResponseBody String validateJobSequenceNumber(
			@RequestParam(value = "ProjectId", required = false) Long projectId) throws Exception {
		String result;
		String resultJson;
		Integer projectIdCount = projectsService.projectIdValidation(projectId);
		if (projectIdCount > 0) {
			result = "duplicate";
		} else {
			result = "unique";
		}
		resultJson = commonUtil.toJSON(result);
		return resultJson;
	}

	// to validate Task Name
	@RequestMapping(value = "/taskNameValidation", method = RequestMethod.GET)
	public @ResponseBody Boolean taskNameValidation(@RequestParam(value = "TaskName", required = false) String TaskName,
			@RequestParam(value = "ProjectId", required = false) Long projectId,
			@RequestParam(value = "taskId", required = false) Long taskId) throws Exception {
		ProjectTasks projectTasks = null;
		if (null != projectId) {
			projectTasks = projectTasksService.validateTaskNameByTaskId(taskId, TaskName, projectId);
		}
		Boolean flag = false;
		if (null == projectTasks) {
			flag = projectsService.validateTasksName(TaskName, projectId);
		}
		return flag;
	}

	@RequestMapping(value = "/sourceLeadTasks")
	public String sourceLeadTasks() throws Exception {
		ModelAndView model = new ModelAndView();
		Collection<ProjectTasks> sourceleadProjectTasks = tasksService.getAllSourceleadTasks();
		model.addObject("Tasks_List", sourceleadProjectTasks);
		return commonUtil.toJSON(sourceleadProjectTasks);
	}

	@RequestMapping(value = "/getById/{id}", method = RequestMethod.GET, headers = "Accept=application/json")
	public Projects getProjectById(@PathVariable Long id) throws Exception {
		Projects projects = projectsService.getProjectById(id);
		return projects;
	}

	/**
	 * This method for delete project by id
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET, headers = "Accept=application/json")
	public Boolean delete(@PathVariable Long id) throws Exception {
		Boolean isDeleted = projectsService.deleteProjectById(id);
		return isDeleted;
	}

	
	/**
	 * This method for getEmployeesList by by excluding Level1 and Level2
	 * Managers
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getEmployeesListByExcludingManagers", method = RequestMethod.POST)
	public @ResponseBody String getEmployeesListByExcludingManagers(
			@RequestParam(value = "level1Manager", required = false) Long level1Manager,
			@RequestParam(value = "level2Manager", required = false) Long level2Manager,
			@RequestParam(value = "searchByName", required = false) String searchByName,
			@RequestParam("orgLocId") Long orgLocId) throws Exception {

		Collection<Employees> employeList = employeesService.getAllEmployeesByOrgLocationIdAndSearchByName(orgLocId,
				level1Manager, level2Manager, searchByName);

		for (Employees employees : employeList) {
			UserRole userRole = userRoleService.getUserRoleByUserIdAndLocationId(employees.getUser().getId(), orgLocId);
			if (userRole != null) {
				employees.setUserType(userRole.getUserType());
			}
		}
		return commonUtil.toJSON(employeList);

	}

	/**
	 * @author Venkat Kesav Assign Employee to project by project id in ELK
	 * @param projectId
	 * @param request
	 * @return Json
	 * @throws Exception
	 */

	@PreAuthorize("hasRole('ROLE_ASSIGN_EMPLOYEE_TO_PROJECT')")
	@RequestMapping(value = "/storeAssignEmployeeIntoELK", method = RequestMethod.POST)
	public @ResponseBody String storeAssignEmployeeIntoELK(
			@RequestParam(value = "ProjectId", required = false) Long projectId) throws Exception {

		if (projectId != null) {
			Projects projects = projectsService.getProjectById(projectId);
			Collection<ProjectMembers> projectMembers = projectMembersService
					.getAllProjectMembersByProjectId(projects.getId());
			ProjectsIndex projectsIndex = projectsIndexHelper.getProjectsIndexFromProjects(projects, null,
					projectMembers);
			// Elastic Search Document Saving
			sourceleadELKBaseIndexer.saveOrUpdateElkDocument(projectsIndex, Constants.ELK_PROJECT_INDEX,
					Constants.ELK_PROJECT_TYPE, projects.getId().toString());
		}
		return "success";
	}

	

	// to validate user email
	@RequestMapping(value = "/getEmailVerification", method = RequestMethod.POST)
	public @ResponseBody String validateUserEmail(@RequestParam(value = "EmailId", required = false) String EmailId)
			throws Exception {

		String result;
		User user = userService.findByEmail(EmailId);

		if (user != null) {
			result = "true";
		} else {
			result = "false";
		}
		return result;
	}

	// to validate Project SequeneNumber
	@RequestMapping(value = "/validateProjectSequeneNumberById", method = RequestMethod.POST)
	public @ResponseBody Boolean validateProjectSequeneNumber(
			@RequestParam(value = "ProjectSequeneNumber", required = false) String ProjectSequeneNumber,
			@RequestParam("orgLocId") Long orgLocId) throws Exception {

		return projectsService.validateProjectSequeneNumber(ProjectSequeneNumber, orgLocId);
	}

	// Validate for Cilent Website
	@RequestMapping(value = "/ws/validateClientWebsite", method = RequestMethod.GET)
	public @ResponseBody String validateOrgWebsite(@RequestParam(value = "website", required = false) String website,
			@RequestParam(value = "sourceleadBLockedwebsites", required = false) String sourceleadBLockedwebsites) {
		String status = "success";
		String orgInfo = orgService.websiteValidation(website, sourceleadBLockedwebsites);
		if (orgInfo != null && !"".equalsIgnoreCase(orgInfo)) {
			if ("Website Duplicated".equalsIgnoreCase(orgInfo)) {
				status = "Website Duplicated";
			} else if ("Invalid Website".equalsIgnoreCase(orgInfo)) {
				status = "Invalid Website";
			}
		}
		return status;
	}

	// to validate Task Name
	@RequestMapping(value = "/validateTasksNameById", method = RequestMethod.GET)
	public @ResponseBody Boolean validateTasksNameById(
			@RequestParam(value = "TaskName", required = false) String TaskName,
			@RequestParam(value = "ProjectId", required = false) Long projectId)
			throws Exception {
		return projectsService.validateTasksName(TaskName, projectId);
	}
	
	
	
	  @RequestMapping(value = "/getSourceLeadRegisteredOrgsForClientProject", method = RequestMethod.GET)
			public  List<OrgLocations> getSourceLeadRegisteredOrgsForClientProject(@RequestParam(value="orgLocationIds", required=true) String orgLocationIds) throws Exception {
			    
			   String[] orgLocIds = orgLocationIds.split(",");
			   List<Long> orgLocationIdsList = new ArrayList<>();
			   for(int i=0; i<orgLocIds.length; i++){
				   orgLocationIdsList.add(Long.valueOf(orgLocIds[i])); 
			   }
			   
			   List<OrgLocations> orgLocationsList = orgLocationService.getAllRegisteredOrgLocationsForClientProject(orgLocationIdsList);
		       return orgLocationsList;
		    }
	  
	  
	    @RequestMapping(value = "/getEndClientProjects", method = RequestMethod.GET)
		public  List<Projects> getEndClientProjects(@RequestParam(value="clientOrgLocationId", required=true) Long clientOrgLocationId,HttpServletRequest request) throws Exception {
		   
	       OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
		   List<Projects> projectsList = projectsService.getEndClientProjectByOrgLocationsIdAndClientOrgLocationId(orgLocations.getId(),clientOrgLocationId);
	       return projectsList;
	    }

	    @RequestMapping(value = "/getProjectsListByOrgLocationForTransfer", method = RequestMethod.GET)
		public  List<Projects> getProjectsListByOrgLocationForTransfer(@RequestParam(value="orgLocationId", required=true) Long orgLocationId) throws Exception {
		   
		   List<Projects> projectsList = projectsService.getAllProjectsByOrgLocationIdAndStatus(orgLocationId);
	       return projectsList;
	    }
	    
	    
	    @RequestMapping(value = "/transferProjectMembers", method = RequestMethod.POST)
		public  String transferProjectMembers(@RequestParam(value="projectUUID", required=true) String projectUUID,
				@RequestParam(value="projectId", required=true) Long projectId,@RequestParam(value="projectOrgLocationId", required=true) Long projectOrgLocationId,
				@RequestParam(value="projectMembers", required=true) String projectMembers,HttpServletRequest request) throws Exception {
		   
	    	OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
	    	Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request,"org");
	    	User user = (User) SourceLeadSessionManager.getSessionAttribute(request,"user");
	    	Projects projects = projectsService.getProjectById(projectId);
	    	
	        projectMembersService.transferMembers(orgLocations, org, projects, projectMembers,user);
	    	
	    	
	       return "success";
	    }
	  

	protected static boolean isNullOrEmpty(String str) {
		return str == null || "".equals(str.trim());
	}
}
