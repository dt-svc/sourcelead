package com.bis.sourcelead.rest;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.FileCopyUtils;
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
import com.bis.sourcelead.dto.LookupDataDTO;
import com.bis.sourcelead.dto.ProjectAdditionalDetailsDTO;
import com.bis.sourcelead.dto.ProjectAssignedMembersDTO;
import com.bis.sourcelead.dto.ProjectMembersDTO;
import com.bis.sourcelead.dto.ProjectMembersRateHistoryDTO;
import com.bis.sourcelead.dto.ProjectsDTO;
import com.bis.sourcelead.dto.Response;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.elk.index.ProjectsIndex;
import com.bis.sourcelead.elk.index.helper.ProjectsIndexHelper;
import com.bis.sourcelead.elk.setup.SourceleadELKBaseIndexer;
import com.bis.sourcelead.entity.EmailAttachment;
import com.bis.sourcelead.entity.EmailMessage;
import com.bis.sourcelead.entity.EmailScenario;
import com.bis.sourcelead.entity.Employees;
import com.bis.sourcelead.entity.LookUpData;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.ProjectClients;
import com.bis.sourcelead.entity.ProjectMembers;
import com.bis.sourcelead.entity.ProjectMembersHistory;
import com.bis.sourcelead.entity.ProjectMembersRateHistory;
import com.bis.sourcelead.entity.ProjectQuestionnaire;
import com.bis.sourcelead.entity.ProjectTaskActivity;
import com.bis.sourcelead.entity.ProjectTasks;
import com.bis.sourcelead.entity.Projects;
import com.bis.sourcelead.entity.ReleaseNote;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserRole;
import com.bis.sourcelead.enums.Primary;
import com.bis.sourcelead.enums.Result;
import com.bis.sourcelead.service.CandidateService;
import com.bis.sourcelead.service.EmailScenarioService;
import com.bis.sourcelead.service.EmployeesService;
import com.bis.sourcelead.service.LeaveService;
import com.bis.sourcelead.service.LookUpDataService;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.OrgRelationsService;
import com.bis.sourcelead.service.OrgService;
import com.bis.sourcelead.service.ProjectApproverHierarchyService;
import com.bis.sourcelead.service.ProjectClientService;
import com.bis.sourcelead.service.ProjectMembersHistoryService;
import com.bis.sourcelead.service.ProjectMembersRateHistoryService;
import com.bis.sourcelead.service.ProjectMembersService;
import com.bis.sourcelead.service.ProjectQuestionnaireService;
import com.bis.sourcelead.service.ProjectTaskActivityService;
import com.bis.sourcelead.service.ProjectTasksService;
import com.bis.sourcelead.service.ProjectsService;
import com.bis.sourcelead.service.RolesService;
import com.bis.sourcelead.service.UserRoleService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.timesheet.rest.TimesheetRestController;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.EmailScenerioUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;

@RestController
@PropertySource("${propertyLocation:app.properties}")
@RequestMapping("/app")
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
	
	@Autowired
	ProjectMembersRateHistoryService projectMembersRateHistoryService;
	
	@Autowired
	private LeaveService leaveService;
	
	@Autowired
	private CandidateService candidateService;
	
	@Autowired
	private TimesheetRestController timesheetRestController;

	@Autowired
	private LookUpDataService lookUpDataService;
	
	@Autowired
	private ProjectTaskActivityService projectTaskActivityService;
	/**
	 * This method for save the project by json
	 * 
	 * @param json
	 * @param request
	 * @return value is Project pojo
	 * @throws Exception
	 */

	@RequestMapping(value = { "/storeProject", "/rest/storeProject" }, method = RequestMethod.POST)
	public @ResponseBody Response<ProjectsDTO> storeProject(ProjectsDTO projectsDTO) throws Exception {
		Response<ProjectsDTO> response =new  Response<>();
		Projects projects = null;
		try {
		 if(Validator.validate(projectsDTO)) {
			 projectsDTO.setStaticPath(staticPath);
			 projects = projectsService.storeProject(projectsDTO);
			 
		    ProjectsDTO stroredProjectsDTO = new ProjectsDTO();
			stroredProjectsDTO.setId(projects.getId());
			stroredProjectsDTO.setProjectUUID(projects.getProjectUUID());
			stroredProjectsDTO.setStartDateStr(projects.getStartDateStr());
			stroredProjectsDTO.setEndDateStr(projects.getEndDateStr());
			if(StringUtils.isNotBlank(projects.getIsProjectShared()) && projects.getIsProjectShared().equalsIgnoreCase(Constants.YES)) {
				stroredProjectsDTO.setSavedSharedClientOrgLocId(projects.getSharedLocationId().getId().toString());
			}
			
			response.setObject(stroredProjectsDTO);
			
			 response.setResponse(Result.SUCCESS.name());
			 response.setResponseMessage(Result.SUCCESS.getValue());
		 }else {
    		 response.setResponse(Result.INVALID_DATA.name());
			 response.setResponseMessage(Result.INVALID_DATA.getValue());
    	 }
		}catch (Exception e) {
			e.printStackTrace();
			response.setResponse(Result.AWKWARD.name());
			response.setResponseMessage(Result.AWKWARD.getValue());
		}
		return response;
	}
	
	
	
       @RequestMapping(value = "/assignProjectMembersToProject", method = RequestMethod.POST)
		public  Response<String> assignProjectMembersToProject(ProjectAssignedMembersDTO projectAssignedMembersDTO,@RequestParam(value="projectUUID", required=true) String projectUUID,
			   @RequestParam(value="userId", required=true) Long userId,@RequestParam(value="projectClientUUID", required=false) String projectClientUUID,@RequestParam(value="orgLocationId", required=true) Long orgLocationId,
			   HttpServletRequest request) throws Exception {
    	    projectAssignedMembersDTO.setProjectClientUUID(projectClientUUID);
			Response<String> response = new Response<>();
			 User user = userService.getUserById(userId);
			 String isGlobalAccess = (String) SourceLeadSessionManager.getSessionAttribute(request,"isGlobalAccess");
			try {
				if(user != null) {
					outerLoop:{
					    if(projectAssignedMembersDTO.getProjectMembers() != null && projectAssignedMembersDTO.getProjectMembers().size() > 0){
				   			for(ProjectMembersDTO projectMembersDTO : projectAssignedMembersDTO.getProjectMembers()){
				   				if(StringUtils.isEmpty(projectMembersDTO.getValidateRow()))
									 continue;
								if(!Validator.validate(projectMembersDTO)) {
									response.setObject("DataMissing");
									response.setResponse(Result.INVALID_DATA.name());
									response.setResponseMessage(Result.INVALID_DATA.getValue());
									break outerLoop;
								}
				   			}
				   		    OrgLocations orgLocations = orgLocationService.getOrgLocations(orgLocationId);
				   		    Projects projects = projectsService.getProjectByProjectUUID(projectUUID);
				   		    if(StringUtils.isNotBlank(projects.getIsProjectShared()) && 
			   		    		projects.getIsProjectShared().equalsIgnoreCase(Constants.YES) && !projects.getOrgLocationsId().getId().equals(orgLocations.getId())) {
				   		    	ProjectClients projectClients = projectClientService.getProjectClientsByProjectIdAndOrgLocationId(projects.getId(), orgLocations.getId());
				   		    	if(StringUtils.isNotBlank(projectClients.getIsProjectShared()) && projectClients.getIsProjectShared().equalsIgnoreCase(Constants.YES)) {
				   		    		projectMembersService.saveAssignedSharedProjectMembers(projectAssignedMembersDTO, projectUUID, user, orgLocations,isGlobalAccess);
				   		    	}else {
				   		    		OrgLocations clientOrgLocation = orgLocationService.getOrgLocations(projects.getOrgLocationsId().getId());
				   		    		if(StringUtils.isNotBlank(clientOrgLocation.getOrg().getOrgType()) && clientOrgLocation.getOrg().getOrgType().equalsIgnoreCase("Guest")) {
				   		    			projectMembersService.saveAssignedSharedProjectMembers(projectAssignedMembersDTO, projectUUID, user, orgLocations,isGlobalAccess);
				   		    		}
				   		    	}
				   		    }else {
				   		        projectMembersService.saveAssignedProjectMembers(projectAssignedMembersDTO, projectUUID, user, orgLocations,isGlobalAccess);
				   		    }
						   
						    response.setResponse(Result.SUCCESS.name());
							response.setResponseMessage(Result.SUCCESS.getValue());
					     }else {
					    	 response.setResponse(Result.SUCCESS.name());
						     response.setResponseMessage(Result.SUCCESS.getValue());
					     }
				       }
				}else {
					response.setResponse(Result.SESSION_TIMEDOUT.name());
					response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
				}
			}catch (Exception e) {
				response.setResponse(Result.AWKWARD.name());
				response.setResponseMessage(Result.AWKWARD.getValue());
				e.printStackTrace();
			}
		   
		    return response;
	    }
       
       
	/*private void approvedLeaveForNewlyAddedEmployee(List<ProjectMembersDTO> projectMembers, OrgLocations orglocationId, User user) throws Exception {
		if(projectMembers != null && !projectMembers.isEmpty()) {
			for(ProjectMembersDTO members:projectMembers) {
				if(members.getCandidateId()!= null) {
					 Candidate candidate = candidateService.getCandidateByCandidateId(members.getCandidateId());
					 if(candidate != null && candidate.getConsultantUser()!= null) {
						 List<Leave> leaveList = leaveService.getLeaveByUserIdOrglocationIdandStatus(candidate.getConsultantUser(),orglocationId.getId(),Constants.APPROVED);
						 if(leaveList!= null && !leaveList.isEmpty()) {
							 for(Leave leave:leaveList) {
							 //	timesheetRestController.linkLeaveTypeWithTimeSheet(user,orglocationId, leave.getLeaveType().getId(),leave,Constants.APPROVED);
							 } 
						 }
					 }
				}
			}	
		}
	}*/



	@RequestMapping(value = "/storeProjectAdditionalDetails", method = RequestMethod.POST)
		public  Response<String> storeProjectAdditionalDetails(ProjectAdditionalDetailsDTO projectAdditionalDetailsDTO,@RequestParam(value="projectId", required=true) Long projectId,
			   @RequestParam(value="userId", required=true) Long userId,@RequestParam(value="orgLocationId", required=true) Long orgLocationId,
			   @RequestParam(value="saveType", required=true) String saveType,HttpServletRequest request) throws Exception {
		       User user = userService.getUserById(userId);
		       Response<String> response = new Response<>();
		       
				try {
					if(user != null) {
						if(Validator.validate(projectAdditionalDetailsDTO)) {
							OrgLocations orgLocations = orgLocationService.getOrgLocations(orgLocationId);
						    projectAdditionalDetailsDTO.setSaveType(saveType);
						    projectQuestionnaireService.saveProjectQuestionnaireDetails(projectAdditionalDetailsDTO, projectId, user, orgLocations);
						    SourceLeadSessionManager.setSessionAttribute(request, "projectCreateMsg", "New project successfully created");
							response.setResponse(Result.SUCCESS.name());
							response.setResponseMessage(Result.SUCCESS.getValue());
						}else {
							response.setResponse(Result.INVALID_DATA.name());
							response.setResponseMessage(Result.INVALID_DATA.getValue());
						}
					}else {
			    		 response.setResponse(Result.INVALID_DATA.name());
						 response.setResponseMessage(Result.INVALID_DATA.getValue());
			    	 }
				}catch (Exception e) {
					response.setResponse(Result.AWKWARD.name());
					response.setResponseMessage(Result.AWKWARD.getValue());
				}
			   
			    
			    return response;
	    }
       
       
       
       
	   @RequestMapping(value = { "/storeClientProject", "/rest/storeClientProject" }, method = RequestMethod.POST)
	   	public @ResponseBody  Response<ProjectsDTO> storeClientProject(ClientProjectDTO clientProjectDTO) throws Exception {
		   Response<ProjectsDTO> response = new Response<>();
		   try {
			   if(Validator.validate(clientProjectDTO)) {
				   Projects projects = null;
			   		projects = projectsService.storeClientProject(clientProjectDTO);
			   		ProjectsDTO projectsDTO = new ProjectsDTO();
			   		projectsDTO.setProjectUUID(projects.getProjectUUID());
			   		projectsDTO.setProjectClientUUID(projects.getProjectClientUUID());
			   		response.setObject(projectsDTO);
			   		response.setResponse(Result.SUCCESS.name());
					response.setResponseMessage(Result.SUCCESS.getValue());
			   }else {
				     response.setResponse(Result.INVALID_DATA.name());
					 response.setResponseMessage(Result.INVALID_DATA.getValue());
			     }
		    }catch (Exception e) {
		    	e.printStackTrace();
				response.setResponse(Result.AWKWARD.name());
				response.setResponseMessage(Result.AWKWARD.getValue());
			}
	   		return response;
	   	}
       
       
      
       
       @RequestMapping(value = "/getProjectAssignedMembesListByProjectId", method = RequestMethod.GET)
		public  List<ProjectMembers> getProjectAssignedMembesListByProjectId(@RequestParam(value="projectId", required=true) Long projectId,HttpServletRequest request) throws Exception {
    	   
    	   OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
    	   String isGlobalAccess = (String) SourceLeadSessionManager.getSessionAttribute(request,"isGlobalAccess");
    	   List<ProjectMembers> projectMembersList = projectMembersService.getAllProjectMembersByProjectIdAndStatus(projectId,"Active",orgLocations,"","",isGlobalAccess);
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

	// to validate Project Short Code
		@RequestMapping(value = "/validateProjectShortCodeByOrgLocationId", method = RequestMethod.POST)
		public @ResponseBody Boolean validateProjectShortCodeByOrgLocationId(@RequestParam(value = "projectShortCode", required = false) String projectShortCode,
				@RequestParam("orgLocId") Long orgLocId) throws Exception {

			return projectsService.validateProjectShortCode(projectShortCode, orgLocId);
		}
		
		// to validate Project project Name
		@RequestMapping(value = "/validateOrgLocationProjectName", method = RequestMethod.GET)
		public @ResponseBody Boolean validateOrgLocationProjectName(@RequestParam(value = "projectUUID", required = false) String projectUUID,
				@RequestParam(value = "projectName", required = false) String projectName,HttpServletRequest request) throws Exception {
			
			boolean flag = false;
			OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
			if(StringUtils.isNotBlank(projectName)) {
				Projects projects = projectsService.getProjectByOrgLocationsIdAndProjectName(orgLocations.getId(), projectName.trim());
				if(projects != null) {
					if(!projects.getProjectUUID().equalsIgnoreCase(projectUUID)) {
						if(projects.getProjectName().equalsIgnoreCase(projectName.trim())) {
							flag = true;
						}
					}
				}
			}
			
			return flag;
		}	
		
		
	// Validate for Cilent Website
	@RequestMapping(value = "/validateClientWebsite", method = RequestMethod.GET)
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
	  
	  
	    @RequestMapping(value = { "/getEndClientProjects", "/rest/getEndClientProjects" }, method = RequestMethod.GET)
		public  List<ProjectsDTO> getEndClientProjects(@RequestParam(value="clientOrgLocationId", required=true) Long clientOrgLocationId,HttpServletRequest request) throws Exception {
		   
	       OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
		   List<Projects> projectsList = projectsService.getEndClientProjectByOrgLocationsIdAndClientOrgLocationId(orgLocations.getId(),clientOrgLocationId);
	       List<ProjectsDTO> projectsDTOList = new ArrayList<>();
		   if(projectsList != null && projectsList.size() > 0) {
	    	   for(Projects projects : projectsList) {
	    		   ProjectsDTO projectsDTO = new ProjectsDTO();
	    		   projectsDTO.setId(projects.getId());
	    		   projectsDTO.setProjectName(projects.getProjectName());
	    		   projectsDTOList.add(projectsDTO);
	    	   }
	       }
		   return projectsDTOList;
	    }

	    @RequestMapping(value = { "/getProjectsListByOrgLocationForTransfer", "/rest/getProjectsListByOrgLocationForTransfer" }, method = RequestMethod.GET)
		public  List<ProjectsDTO> getProjectsListByOrgLocationForTransfer(@RequestParam(value="orgLocationId", required=true) Long orgLocationId,@RequestParam(value="projectClientUUID", required=true) String projectClientUUID) throws Exception {
		   
		   List<Projects> projectsList = projectsService.getAllProjectsByOrgLocationIdAndStatusForTransfer(orgLocationId,projectClientUUID);
		   List<ProjectsDTO> projectsDTOList = new ArrayList<>();
		   
		   if(projectsList != null && projectsList.size() > 0) {
	    	   for(Projects projects : projectsList) {
	    		   ProjectsDTO projectsDTO = new ProjectsDTO();
	    		   projectsDTO.setId(projects.getId());
	    		   projectsDTO.setProjectName(projects.getProjectName());
	    		   projectsDTOList.add(projectsDTO);
	    	   }
	       }
		   return projectsDTOList;
	    }
	    
	    
	    @RequestMapping(value = "/transferProjectMembers", method = RequestMethod.POST)
		public  String transferProjectMembers(@RequestParam(value="projectUUID", required=true) String projectUUID,
				@RequestParam(value="projectId", required=true) Long projectId,@RequestParam(value="projectOrgLocationId", required=true) Long projectOrgLocationId,
				@RequestParam(value="projectMembers", required=true) String projectMembers,HttpServletRequest request) throws Exception {
		   
	    	OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
	    	Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request,"org");
	    	User user = (User) SourceLeadSessionManager.getSessionAttribute(request,"user");
	    	Projects projects = projectsService.getProjectById(projectId);
	    	
	       projectMembersService.transferMembers(orgLocations, org, projects, projectMembers,user,projectOrgLocationId);
	    	
	    	
	       return "success";
	    }
	    
	    
	    @RequestMapping(value = "/editProjectAssignedProjectMember", method = RequestMethod.POST)
		public  ProjectMembersDTO editProjectAssignedProjectMember(@RequestParam(value="projectMemberId", required=true) Long projectMemberId,
				HttpServletRequest request) throws Exception {
	    
	      OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
	     
	      ProjectMembersDTO projectMebersDTO = new ProjectMembersDTO();
	      ProjectMembers projectMebers = projectMembersService.getProjectMemberById(projectMemberId);
	      if(projectMebers != null) {
	    	  projectMebersDTO.setBillRateRateType(projectMebers.getRateType());
	    	  projectMebersDTO.setBillRateCurrency(projectMebers.getBillRateCurrency());
	    	  projectMebersDTO.setBillRate(projectMebers.getCurrentBillRate());
	    	  
	    	  projectMebersDTO.setPayRate(projectMebers.getPayRate());
	    	  projectMebersDTO.setPayRateRateType(projectMebers.getPayRateType());
	    	  projectMebersDTO.setPayRateCurrency(projectMebers.getPayRateCurrency());
	    	  SimpleDateFormat dateFormat = orgLocations.getLocFullFormDateFormatter();
	    	  
	    	  ProjectMembersHistory pmh = projectMembersHistoryService.getProjectMembersOpenHistoryByProjectMemberId(projectMebers.getId());
	    	  if(pmh.getProjectAssignedDate() != null) {
	    		  projectMebersDTO.setProjectStartDate(dateFormat.format(pmh.getProjectAssignedDate()));
	    	  }
	    	 
	    	  if(pmh.getProjectTerminationDate() != null) {
	    		  projectMebersDTO.setProjectEndDate(dateFormat.format(pmh.getProjectTerminationDate()));
	    	  }
	    	
	    	  if(projectMebers.getProjects().getEndDate() != null) {
		    	  projectMebersDTO.setMemberEndDate(dateFormat.format(projectMebers.getProjects().getEndDate()));
	    	  }
	    	  
	    	  projectMebersDTO.setEstHours(projectMebers.getEstimatedHours());
	    	  projectMebersDTO.setEstBudget(projectMebers.getEstimatedBudget());
	    	  projectMebersDTO.setPercentageOfWork(projectMebers.getPercentageOfWork());
	    	  
	    	  projectMebersDTO.setProjectMemberId(projectMebers.getId());
	    	  
	    	  ProjectMembersRateHistory pmrh = projectMembersRateHistoryService.getProjectMembersRateHistoryByMemberIdAndStatus(projectMebers.getId());
	    	  if(pmrh != null) {
	    		  if(pmrh.getPayRateStartDate() != null) {
	    			  projectMebersDTO.setPayRateEstimatedDate(dateFormat.format(pmrh.getPayRateStartDate()));
	    		  }
	    		  
	    		  if(pmrh.getBillRateStartDate() != null) {
	    			  projectMebersDTO.setBillRateEstimatedDate(dateFormat.format(pmrh.getBillRateStartDate()));
	    		  }
	    	  }
	      }
	    	
	       return projectMebersDTO;
	    }
	    
	    @RequestMapping(value = "/updateProjectMemberDetails", method = RequestMethod.POST)
		public String updateProjectMemberDetails(ProjectMembersDTO projectMembersDTO,
				HttpServletRequest request) throws Exception {
	    	 
	    	OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
	    	User user = (User) SourceLeadSessionManager.getSessionAttribute(request,"user");
	    	
	    	ProjectMembers projectMembers = projectMembersService.getProjectMemberById(projectMembersDTO.getProjectMemberId()); 
	    	ProjectMembersRateHistory pmrh = null;
	    	
	    	boolean flag = projectMembersService.getPayRateOrBillRateChanges(projectMembers, projectMembersDTO);
	    	
	    	ProjectMembersRateHistory projectMembersRateHistory = null;
	    	
	    	if(projectMembers != null) {
	    		
	    		
	    		if(flag == true) {
		         pmrh = projectMembersRateHistoryService.getProjectMembersRateHistoryByMemberIdAndStatus(projectMembers.getId());
		    		if(pmrh == null) {
		    			pmrh = getProjectMembersRateHistoryDetails(projectMembers, user);
		    			if(projectMembers.getProjectAssignedDate() != null) {
		    				pmrh.setPayRateStartDate(projectMembers.getProjectAssignedDate());
		    				pmrh.setBillRateStartDate(projectMembers.getProjectAssignedDate());
		    			}else {
		    				Date projectAssignedStartDate = commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),projectMembersDTO.getMemberStartDate());
		    				pmrh.setPayRateStartDate(projectAssignedStartDate);
		    				pmrh.setBillRateStartDate(projectAssignedStartDate);
		    			}
		    		}
		    		
	    			
	    			Date payRateEstimatedDate = commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),projectMembersDTO.getPayRateEstimatedDate());
	    			Calendar cal = Calendar.getInstance();
	    			cal.setTime(payRateEstimatedDate);
	    			cal.add( Calendar.DAY_OF_YEAR, -1);
	    			pmrh.setPayRateEndDate(cal.getTime());
	    			
	    			Date billRateEstimatedDate = commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),projectMembersDTO.getBillRateEstimatedDate());
	    			Calendar cal1 = Calendar.getInstance();
	    			cal1.setTime(billRateEstimatedDate);
	    			cal1.add( Calendar.DAY_OF_YEAR, -1);
	    			pmrh.setBillRateEndDate(cal1.getTime());
	    			
	    			pmrh.setStatus(0);
	    			pmrh = projectMembersRateHistoryService.saveProjectMembersRateHistory(pmrh);
	    		}
	    		
	    		
	    		projectMembers.setPayRate(projectMembersDTO.getPayRate());
	    		projectMembers.setPayRateType(projectMembersDTO.getPayRateRateType());
	    		projectMembers.setPayRateCurrency(projectMembersDTO.getPayRateCurrency());
	    		
	    		projectMembers.setCurrentBillRate(projectMembersDTO.getBillRate());
	    		projectMembers.setRateType(projectMembersDTO.getBillRateRateType());
	    		projectMembers.setBillRateCurrency(projectMembersDTO.getBillRateCurrency());
	    		
	    		if(StringUtils.isNotBlank(projectMembersDTO.getMemberStartDate())){
	    			Date projectAssignedStartDate = commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),projectMembersDTO.getMemberStartDate());
	    			projectMembers.setProjectAssignedDate(projectAssignedStartDate);
	    		}
	    		
	    		if(projectMembersDTO.getEstBudget() != null){
	    			projectMembers.setEstimatedBudget(projectMembersDTO.getEstBudget());
	    		}else {
	    			projectMembers.setEstimatedBudget(null);
	    		}
	    		
	    		if(projectMembersDTO.getEstHours() != null){
	    			projectMembers.setEstimatedHours(projectMembersDTO.getEstHours());
	    		}else {
	    			projectMembers.setEstimatedHours(null);
	    		}
	    		
	    		if(projectMembersDTO.getPercentageOfWork() != null){
	    			projectMembers.setPercentageOfWork(projectMembersDTO.getPercentageOfWork());
	    		}else {
	    			projectMembers.setPercentageOfWork(null);
	    		}
	    		
	    		projectMembersService.saveProjectMember(projectMembers);
	    		
	    		if(StringUtils.isNotBlank(projectMembersDTO.getMemberEndDate())){

					ProjectMembersHistory pmh = projectMembersHistoryService.getProjectMembersOpenHistoryByProjectMemberId(projectMembers.getId());
					
					if(pmh == null){
						pmh = projectMembersHistoryService.getProjectMembersRecentClosedByProjectMemberId(projectMembers.getId());
					}
					Date endDate1 = commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),projectMembersDTO.getMemberEndDate());
					if(pmh.getProjectTerminationDate()== null){
						projectMembersHistoryService.updateEndDateOfProjectMemberId(endDate1, projectMembers.getId());
					}

					if (pmh.getProjectTerminationDate() != null && pmh.getProjectTerminationDate().compareTo(commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),projectMembersDTO.getMemberEndDate())) != 0) {
						projectMembersHistoryService.updateEndDateOfProjectMemberId(endDate1, projectMembers.getId());
						
					}
					// mail forward to members inline end date edit
					projectEnddatechangetoEmployee(projectMembers,user, orgLocations, projectMembersDTO.getMemberEndDate());
					
					Date terminationDate = commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),projectMembersDTO.getMemberEndDate());
					Date currentDate = new  Date();
					
					if(currentDate.after(terminationDate) && !currentDate.equals(terminationDate)){
						commonUtil.terminateProjectMemberByProjectMemberId(projectMembers.getId(),Primary.Yes);
					}
					else{
						commonUtil.inactivateTimesheetSubmissionsBasedOnMemberIdAndMemberTerminationDate(projectMembers.getId(), terminationDate);
					}
	    			
	    		}
	    		
	    		
	    		
	    		
	    		if(flag == true) {
		    		projectMembersRateHistory = getProjectMembersRateHistoryDetails(projectMembers, user);
		    		Date payRateEstimatedDate = commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),projectMembersDTO.getPayRateEstimatedDate());
		    		projectMembersRateHistory.setPayRateStartDate(payRateEstimatedDate);
		    		Date billRateEstimatedDate = commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),projectMembersDTO.getBillRateEstimatedDate());
	    			projectMembersRateHistory.setBillRateStartDate(billRateEstimatedDate);
	    			projectMembersRateHistory.setStatus(1);
	    			projectMembersRateHistory.setRateHistoryId(pmrh);
		    		projectMembersRateHistoryService.saveProjectMembersRateHistory(projectMembersRateHistory);
	    		}
	    		
	    		if(flag == false) {
	    			
	    			pmrh = projectMembersRateHistoryService.getProjectMembersRateHistoryByMemberIdAndStatus(projectMembers.getId());
	    			if(pmrh != null && pmrh.getRateHistoryId() != null) {
	    				
	    				ProjectMembersRateHistory parentRateHistory = projectMembersRateHistoryService.getProjectMembersRateHistoryById(pmrh.getRateHistoryId().getId());
	    				
	    				Date payRateEstimatedDate = commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),projectMembersDTO.getPayRateEstimatedDate());
	    				Date billRateEstimatedDate = commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),projectMembersDTO.getBillRateEstimatedDate());
	    				
	    				if(!pmrh.getPayRateStartDate().equals(payRateEstimatedDate)) {
	    					pmrh.setPayRateStartDate(payRateEstimatedDate);
	    					Calendar cal1 = Calendar.getInstance();
			    			cal1.setTime(payRateEstimatedDate);
			    			cal1.add( Calendar.DAY_OF_YEAR, -1);
			    			parentRateHistory.setPayRateEndDate(cal1.getTime());
	    				}
	    				
	    				if(!pmrh.getBillRateStartDate().equals(billRateEstimatedDate)) {
	    					pmrh.setBillRateStartDate(billRateEstimatedDate);
	    					Calendar cal1 = Calendar.getInstance();
			    			cal1.setTime(billRateEstimatedDate);
			    			cal1.add( Calendar.DAY_OF_YEAR, -1);
			    			parentRateHistory.setBillRateEndDate(cal1.getTime());
	    				}
			    		
			    		projectMembersRateHistoryService.saveProjectMembersRateHistory(pmrh);
			    		projectMembersRateHistoryService.saveProjectMembersRateHistory(parentRateHistory);
			    		
	    				
	    			}
	    			
	    			
	    		}
	    		
	    	}
	    	
	    	
	    	return "success";
	    }
	    
	    
	    
	    private void projectEnddatechangetoEmployee(ProjectMembers emp, User loginUser,  OrgLocations loginOrgLocation, String changedEndDate) throws Exception {
			 Projects projects = projectsService.getProjectById(emp.getProjects().getId());
			 //SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
			 StringBuilder fromNameBuilder=new StringBuilder();
		     EmailMessage emailMessage = new EmailMessage();
			 emailMessage.setFirstName(emp.getEmployee().getUser().getFirstName());
			 emailMessage.setLastName(emp.getEmployee().getUser().getLastName());
			 emailMessage.setToEmail(emp.getEmployee().getUser().getEmail());
			 emailMessage.setFromEmail(loginUser.getEmail());
			 emailMessage.setCreatedBy(loginUser.getUserCode());
			 emailMessage.setFromName(fromNameBuilder.append(loginUser.getFirstName()).append(" ").append(loginUser.getLastName()).toString());
			 emailMessage.setEmailType(Constants.CUSTOM_USER_EMAIL);
			 //setting the class filed values
			 Map<String, String> globalVarsMap = new HashMap<String, String>();
			 
			 globalVarsMap.put("projectStartdate",commonUtil.dateStrFromOrgDateFormatter(loginOrgLocation.getDateFormat(),projects.getStartDate()));
			 globalVarsMap.put("projectEnddate",changedEndDate);
			 
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
			 globalVarsMap.put("loginOrgName",loginOrgLocation.getOrg().getOrgName());
			 
			 //calling the sendEmail method of emailScenerioUtil
			 if(null != projects.getOrgLocationsId())
			 commonUtil.sendTemplateEmail(emailMessage, globalVarsMap,projects.getOrgLocationsId().getOrg(),projects.getOrgLocationsId(),Constants.EDITPROJECT_MAIL_TEAMMEMBER,new ArrayList<EmailAttachment>());
			 
			//calling the sendNotification method of emailScenerioUtil
			EmailScenario  notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.EDITPROJECT_MAIL_TEAMMEMBER,Constants.ENTITY_TYPE_NOTIFICATION);
			String notificationBody = emailScenerioUtil.buildTemplateBody(notificationScenario.getBody(),globalVarsMap,notificationScenario);
			notificationScenario.setBody(notificationBody);
			//String url = null;
			String actionLink = "";
			emailScenerioUtil.sendNotification(actionLink, loginUser, emp.getEmployee().getUser(), loginOrgLocation, notificationScenario,new HashMap<String,String>());
		}
	    
	    
	    public ProjectMembersRateHistory getProjectMembersRateHistoryDetails(ProjectMembers projectMembers, User user) {
	    	
	    	ProjectMembersRateHistory projectMembersRateHistory = new ProjectMembersRateHistory();
    		projectMembersRateHistory.setProjectMemberId(projectMembers);
    		projectMembersRateHistory.setCandidateId(projectMembers.getProjectMemberCandidteId());
    		
    		if(projectMembers.getCurrentBillRate() != null) {
    			projectMembersRateHistory.setBillRate(projectMembers.getCurrentBillRate());
    		}
    		
    		projectMembersRateHistory.setBillRateCurrency(projectMembers.getBillRateCurrency());
    		projectMembersRateHistory.setBillRateType(projectMembers.getRateType());
    		
    		if(projectMembers.getPayRate() != null) {
    			projectMembersRateHistory.setPayRate(projectMembers.getPayRate());
    		}
    		
    		projectMembersRateHistory.setPayRateCurrency(projectMembers.getPayRateCurrency());
    		projectMembersRateHistory.setPayRateType(projectMembers.getPayRateType());
    		
    		projectMembersRateHistory.setCreatedBy(user);
    		projectMembersRateHistory.setCreatedDate(new Date());
    		projectMembersRateHistory.setUpdatedDate(new Date());
    		
    		return projectMembersRateHistory;
	    }
	    
	    
	    
	    @RequestMapping(value = "/getProjectMemberRateTablesList", method = RequestMethod.POST)
		public  List<ProjectMembersRateHistoryDTO> getProjectMemberRateTablesList(@RequestParam(value="projectMemberId", required=true) Long projectMemberId,
				HttpServletRequest request) throws Exception {
	    	
	    	OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
	    	List<ProjectMembersRateHistoryDTO> pmrhDTOList = new ArrayList<>();
	    	List<ProjectMembersRateHistory> pmrhList = projectMembersRateHistoryService.getProjectMembersRateHistoryListByProjectMemberId(projectMemberId);
	    	
	    	if(pmrhList != null && !pmrhList.isEmpty()) {
	    		
	    		for(ProjectMembersRateHistory pmrh : pmrhList) {
	    			ProjectMembersRateHistoryDTO  pmrhDTO = new ProjectMembersRateHistoryDTO();
	    			pmrhDTO.setBillRate(pmrh.getBillRate());
	    			pmrhDTO.setBillRateCurrency(pmrh.getBillRateCurrency());
	    			pmrhDTO.setBillRateType(pmrh.getBillRateType());
	    			if(pmrh.getBillRateStartDate() != null) {
	    				pmrhDTO.setBillRateStartDate(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(), pmrh.getBillRateStartDate()));
	    			}
	    			
	    			if(pmrh.getBillRateEndDate() != null) {
	    				pmrhDTO.setBillRateEndDate(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(), pmrh.getBillRateEndDate()));
	    			}
	    			
	    			
	    			pmrhDTO.setPayRate(pmrh.getPayRate());
	    			pmrhDTO.setPayRateCurrency(pmrh.getPayRateCurrency());
	    			pmrhDTO.setPayRateType(pmrh.getPayRateType());
	    			if(pmrh.getPayRateStartDate() != null) {
	    				pmrhDTO.setPayRateStartDate(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(), pmrh.getPayRateStartDate()));
	    			}
	    			
	    			if(pmrh.getPayRateEndDate() != null) {
	    				pmrhDTO.setPayRateEndDate(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(), pmrh.getPayRateEndDate()));
	    			}
	    			
	    			User createdUser = pmrh.getCreatedBy();
	    			StringBuilder userName = new StringBuilder();
	    			if(StringUtils.isNotBlank(createdUser.getLastName())) {
	    				pmrhDTO.setCreatedBy(userName.append(createdUser.getFirstName()).append(" ").append(createdUser.getLastName()).toString());
	    			}else {
	    				pmrhDTO.setCreatedBy(userName.append(createdUser.getFirstName()).toString());
	    			}
	    			
	    			pmrhDTOList.add(pmrhDTO);
	    			
	    		}
	    	}
	    	
	    	return pmrhDTOList;
	    	
	    }
	  

	protected static boolean isNullOrEmpty(String str) {
		return str == null || "".equals(str.trim());
	}
	
	
	@RequestMapping(value = "/getExportProjects")
	public void getExportUsersData(ProjectsDTO projectsDTO,HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		 OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
	 	 User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
	 	String userRole = (String) SourceLeadSessionManager.getSessionAttribute(request, "loginUserRole");
		Long candidateId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "candidateId");
		Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
		projectsDTO.setOrgId(org.getId());
		 Workbook createExcelworkbook =  new XSSFWorkbook();
		 StringBuilder excelSB = new StringBuilder();
 		 Sheet createExcelsheet = createExcelworkbook.createSheet(excelSB.append("projects ").append(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),new Date())).toString());

		 ResponseList<ProjectsDTO> projectsList = projectsService.getProjectsListByClientsAndStatus(projectsDTO,user.getId(), projectsDTO.getClients(), projectsDTO.getValues(),orgLocations.getId(),userRole,candidateId);
			
 		File file = null;
 		if(!"pdf".equalsIgnoreCase(projectsDTO.getExportType())){
 			createExcelsheet = projectsService.getExcelFile(createExcelsheet,createExcelworkbook,projectsList,orgLocations);
 			 StringBuilder templateSB = new StringBuilder();
	 		  file = File.createTempFile(templateSB.append("projects_").append(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),new Date())).toString(),".xls");
	 		  file.setReadable(true);
	 		  
			 FileOutputStream fos = new FileOutputStream(file);
			 try {
				createExcelworkbook.write(fos);
				fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}	 	
 		}else {
 			file = projectsService.getPdfFile(projectsList,orgLocations);
 			
 		}
 		String mimeType = URLConnection.guessContentTypeFromName(file.getName());
		if (mimeType == null) {
			mimeType = "application/octet-stream";
		}
		response.setContentType(mimeType);
		StringBuilder fileNameSB = new StringBuilder();
		response.setHeader("Content-Disposition", fileNameSB.append("attachment; filename=\"").append(file.getName()).append("\"").toString());
		response.setContentLength((int) file.length());
		InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
		FileCopyUtils.copy(inputStream, response.getOutputStream());
		inputStream.close();
		response.getOutputStream().close();
		response.getOutputStream().flush();	
		if(file!= null){
			file.delete();
		}
	}
	
	
	
	 @RequestMapping(value = "/getProjectPopDetails", method = RequestMethod.POST)
		public  ProjectsDTO getProjectPopDetails(@RequestParam(value="projectUUID", required=true) String projectUUID,
				@RequestParam(value="orgLocationId", required=true) Long orgLocationId,HttpServletRequest request) throws Exception {
		    ProjectsDTO projectDTO = null;
	    	Projects projects = projectsService.getProjectByProjectUUID(projectUUID);
	    	if(projects != null) {
	    		projectDTO = new ProjectsDTO();
	    		projectDTO.setProjectName(projects.getProjectName());
	    		projectDTO.setDateFormat(projects.getOrgLocationsId().getDateFormat());
	    		projectDTO.setStartDate(commonUtil.dateStrFromOrgDateFormatter(projects.getOrgLocationsId().getDateFormat(), projects.getStartDate()));
	    		if(projects.getEndDate() != null) {
	    			projectDTO.setEndDate(commonUtil.dateStrFromOrgDateFormatter(projects.getOrgLocationsId().getDateFormat(), projects.getEndDate()));
	    		}
	    		projectDTO.setTimeSheetFrequency(projects.getTimesheetFrequency());
	    	}
	    	
	    	return projectDTO;
	    	
	    }
	
	
	 //Project Attribute related code bellow written by Laxman Start
	 
	 /**
	  * @author Laxman
	  * @param lookupDataDTO
	  * @param request
	  * @return
	  * @throws Exception
	  */
	@RequestMapping(value = "/saveTaskLookupData", method = RequestMethod.POST)
	public  String saveTaskLookupData(LookupDataDTO lookupDataDTO,HttpServletRequest request) throws Exception {
		    OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		    LookUpData lookupData = null;
		    if(lookupDataDTO.getLookupId() != null) {
		    	lookupData = lookUpDataService.getTasksById(lookupDataDTO.getLookupId());
		    }else {
		    	lookupData = new LookUpData();
		    }
	    	lookupData.setName(lookupDataDTO.getName());
	    	lookupData.setDescription(lookupDataDTO.getDescription());
	    	if(lookupDataDTO.getTaskType().equalsIgnoreCase("TaskType")) {
	    		lookupData.setUniqueKey(lookupDataDTO.getName());
		    	lookupData.setCategory(Constants.TASKTYPE);
	    	}else if(lookupDataDTO.getTaskType().equalsIgnoreCase("TaskStatus")){
		    	lookupData.setCategory(Constants.TASKSTATUS);
	    	}else if(lookupDataDTO.getTaskType().equalsIgnoreCase("TaskPriority")){
		    	lookupData.setCategory(Constants.TASKPRIORITY);
		    	lookupData.setColorPic(lookupDataDTO.getColorPic());
	    	}else if(lookupDataDTO.getTaskType().equalsIgnoreCase("TaskSeverity")){
		    	lookupData.setCategory(Constants.TASKSEVERITY);
		    	lookupData.setColorPic(lookupDataDTO.getColorPic());
	    	}
	    	lookupData.setOrgLocationId(orgLocations.getId());
	    	lookupData.setReferId(lookupDataDTO.getReferId());
	    	lookUpDataService.saveLookupData(lookupData);
	    	return "success";
	    	
	    }
	
	 
		/**
		 * @author Laxman
		 * @param lookupId
		 * @return
		 * @throws Exception
		 */
		@RequestMapping(value = "/getLookupDataByLookupId", method = RequestMethod.POST)
		public  LookUpData getLookupDataByLookupId(@RequestParam(value="lookupId", required=true) Long lookupId) throws Exception {
			    LookUpData lookupData = lookUpDataService.getTasksById(lookupId);
		    	return lookupData;
		    }
		
		/**
		 * @author Laxman
		 * @param lookupId
		 * @throws Exception
		 */
		@RequestMapping(value = "/deleteLookupDataByLookupId", method = RequestMethod.POST)
		public  void deleteLookupDataByLookupId(@RequestParam(value="lookupId", required=true) Long lookupId,
				@RequestParam(value="taskType", required=true) String taskType) throws Exception {
			    LookUpData lookupData = lookUpDataService.getTasksById(lookupId);
			    LookUpData referLookupData = lookUpDataService.getTasksById(lookupData.getReferId());
			   
			   //update task list in referid 
			   List<ProjectTasks> taskList = projectTasksService.getTasksListByLookupIdAndCategoryType(lookupId, taskType);
			   if(taskList != null) {
		    	   for(ProjectTasks pt:taskList) {
				    	if(taskType.equalsIgnoreCase(Constants.TASKTYPE)) {
				    		pt.setTaskType(referLookupData);
						}else if(taskType.equalsIgnoreCase(Constants.TASKSEVERITY)) {
							pt.setSeverity(referLookupData);
						}else if(taskType.equalsIgnoreCase(Constants.TASKPRIORITY)) {
							pt.setPriority(referLookupData);
						}else if(taskType.equalsIgnoreCase(Constants.TASKSTATUS)) {
							pt.setTaskStatus(referLookupData);
						} 
				    	projectTasksService.updateTasks(pt);
				    }
			    }
			 //update task activity list in referid
		      List<ProjectTaskActivity> activityList = projectTaskActivityService.getTasksActitivtyListByLookupIdAndCategoryType(lookupId, taskType);
		       if(activityList != null && !activityList.isEmpty()) {
			    	for(ProjectTaskActivity pta:activityList) {
			    		if(taskType.equalsIgnoreCase(Constants.TASKPRIORITY)) {
			    			pta.setPriority(referLookupData);
			    		}else if(taskType.equalsIgnoreCase(Constants.TASKSTATUS)) {
			    			pta.setTaskStatus(referLookupData);
			    		}
			    		projectTaskActivityService.saveProjectTaskActivity(pta);
			    	}
			    }
		       //delete referId in lookupdata
			    lookUpDataService.deleteLookupDateByLookupId(lookupData);
		    }
		
		/**
		 * @author Laxman
		 * @param request
		 * @return
		 * @throws Exception
		 */
		
		@RequestMapping(value = "/getTaskTypeList", method = RequestMethod.POST)
		public  List<LookUpData> getTaskTypeList(HttpServletRequest request) throws Exception {
		    OrgLocations loginOrgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
			List<LookUpData> taskTypeList = lookUpDataService.getLookUpdataByCategoryAndOrgLocationId(Constants.TASKTYPE, loginOrgLocations.getId());
	   	    for(LookUpData lpd:taskTypeList) {
	   	    	if(lpd.getReferId() != null) {
	   	    		LookUpData data = lookUpDataService.getTasksById(lpd.getReferId());
	   	   	    	lpd.setReferName(data.getName());
	   	    	}
	   	    }
	   	    return taskTypeList;
		 }
		
		/**
		 * @author Laxman
		 * @param request
		 * @return
		 * @throws Exception
		 */
		@RequestMapping(value = "/getTaskStatusList", method = RequestMethod.POST)
		public  List<LookUpData> getTaskStatusList(HttpServletRequest request) throws Exception {
		    OrgLocations loginOrgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
			List<LookUpData> taskStatusList = lookUpDataService.getLookUpdataByCategoryAndOrgLocationId(Constants.TASKSTATUS, loginOrgLocations.getId());
	   	    for(LookUpData lpd:taskStatusList) {
	   	    	if(lpd.getReferId() != null) {
	   	    		LookUpData data = lookUpDataService.getTasksById(lpd.getReferId());
	   	   	    	lpd.setReferName(data.getName());
	   	    	}
	   	    }
	   	    return taskStatusList;
		 }
		
		/**
		 * @author Laxman
		 * @param request
		 * @return
		 * @throws Exception
		 */
		@RequestMapping(value = "/getTaskPriorityList", method = RequestMethod.POST)
		public  List<LookUpData> getTaskPriorityList(HttpServletRequest request) throws Exception {
		    OrgLocations loginOrgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
			List<LookUpData> taskPriorityList = lookUpDataService.getLookUpdataByCategoryAndOrgLocationId(Constants.TASKPRIORITY, loginOrgLocations.getId());
	   	    for(LookUpData lpd:taskPriorityList) {
	   	    	if(lpd.getReferId() != null) {
	   	    		LookUpData data = lookUpDataService.getTasksById(lpd.getReferId());
	   	   	    	lpd.setReferName(data.getName());
	   	    	}
	   	    }
	   	    return taskPriorityList;
		 }
		
		/**
		 * @author Laxman
		 * @param request
		 * @return
		 * @throws Exception
		 */
		@RequestMapping(value = "/getTaskSeverityList", method = RequestMethod.POST)
		public  List<LookUpData> getTaskSeverityList(HttpServletRequest request) throws Exception {
		    OrgLocations loginOrgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
			List<LookUpData> taskSeverityList = lookUpDataService.getLookUpdataByCategoryAndOrgLocationId(Constants.TASKSEVERITY, loginOrgLocations.getId());
	   	    for(LookUpData lpd:taskSeverityList) {
	   	    	if(lpd.getReferId() != null) {
	   	    		LookUpData data = lookUpDataService.getTasksById(lpd.getReferId());
	   	   	    	lpd.setReferName(data.getName());
	   	    	}
	   	    }
	   	    return taskSeverityList;
		 }
		
		
		
	// to validate TaskType Name
	 /**
	  * @author Laxman
	  * @param taskTypeName
	  * @param lookupId
	  * @param request
	  * @return
	  * @throws Exception
	  */
	 @RequestMapping(value = "/validateTaskTypeName", method = RequestMethod.POST)
	 public @ResponseBody Boolean validateTaskTypeName(
			 @RequestParam(value = "taskTyePe", required = false) String taskTyepr,
	 		@RequestParam(value = "taskTypeName", required = false) String taskTypeName,@RequestParam(value = "lookupId", required = false) Long lookupId,HttpServletRequest request) throws Exception {
		    OrgLocations loginOrgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
		    LookUpData lookup = null;
	 		if(lookupId != null) {
	 			if(taskTyepr.equalsIgnoreCase("TaskType")) {
		 			lookup  = lookUpDataService.validateTaskTypeNameByTaskNameAndLookupId(taskTypeName,lookupId,Constants.TASKTYPE,loginOrgLocations.getId());
		    	}else if(taskTyepr.equalsIgnoreCase("TaskStatus")){
		 			lookup  = lookUpDataService.validateTaskTypeNameByTaskNameAndLookupId(taskTypeName,lookupId,Constants.TASKSTATUS,loginOrgLocations.getId());
		    	}else if(taskTyepr.equalsIgnoreCase("TaskPriority")){
		 			lookup  = lookUpDataService.validateTaskTypeNameByTaskNameAndLookupId(taskTypeName,lookupId,Constants.TASKPRIORITY,loginOrgLocations.getId());
		    	}else if(taskTyepr.equalsIgnoreCase("TaskSeverity")){
		 			lookup  = lookUpDataService.validateTaskTypeNameByTaskNameAndLookupId(taskTypeName,lookupId,Constants.TASKSEVERITY,loginOrgLocations.getId());
		    	}
	 		}
	 		 Boolean flag = false;
	 		 if(lookup == null) {
	 			if(taskTyepr.equalsIgnoreCase("TaskType")) {
	 				flag = lookUpDataService.validateTaskTypeName(taskTypeName,Constants.TASKTYPE,loginOrgLocations.getId());
	 			}else if(taskTyepr.equalsIgnoreCase("TaskStatus")){
	 				flag = lookUpDataService.validateTaskTypeName(taskTypeName,Constants.TASKSTATUS,loginOrgLocations.getId());
	 			}else if(taskTyepr.equalsIgnoreCase("TaskPriority")){
	 				flag = lookUpDataService.validateTaskTypeName(taskTypeName,Constants.TASKPRIORITY,loginOrgLocations.getId());
	 			}else if(taskTyepr.equalsIgnoreCase("TaskSeverity")){
	 				flag = lookUpDataService.validateTaskTypeName(taskTypeName,Constants.TASKSEVERITY,loginOrgLocations.getId());
	 			}
	 			
	 		 }
	 		return flag;
	 	}
	 	
		
		
}
