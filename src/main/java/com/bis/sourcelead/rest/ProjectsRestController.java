package com.bis.sourcelead.rest;

import java.io.IOException;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.elk.index.ProjectsIndex;
import com.bis.sourcelead.elk.index.helper.ProjectsIndexHelper;
import com.bis.sourcelead.elk.setup.SourceleadELKBaseIndexer;
import com.bis.sourcelead.entity.Employees;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.ProjectMembers;
import com.bis.sourcelead.entity.ProjectTasks;
import com.bis.sourcelead.entity.Projects;

import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserRole;
import com.bis.sourcelead.model.ProjectDocumentBO;
import com.bis.sourcelead.service.EmailScenarioService;
import com.bis.sourcelead.service.EmployeesService;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.OrgService;
import com.bis.sourcelead.service.ProjectApproverHierarchyService;
import com.bis.sourcelead.service.ProjectMembersService;
import com.bis.sourcelead.service.ProjectTasksService;
import com.bis.sourcelead.service.ProjectsService;
import com.bis.sourcelead.service.RolesService;
import com.bis.sourcelead.service.UserRoleService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.EmailScenerioUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;

@RestController
@PropertySource("${propertyLocation:app.properties}")
public class ProjectsRestController {

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

	/**
	 * This method for save the project by json
	 * 
	 * @param json
	 * @param request
	 * @return value is Project pojo
	 * @throws Exception
	 */

	@RequestMapping(value = "/storeProject", method = RequestMethod.POST)
	public @ResponseBody Long storeProject(@RequestParam("project_files") MultipartFile[] files,
			@RequestParam("json") String jsonPayload) throws Exception {
		JSONObject jsonObj = new JSONObject(jsonPayload);
		List<ProjectDocumentBO> projectDocuments = Arrays.asList(files).stream().filter(p->!p.isEmpty()).map(file -> {
			try {
				return new ProjectDocumentBO(file.getOriginalFilename(), file.getContentType(), file.getBytes());
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}).collect(Collectors.toList());

		Long projectId = projectsService.storeProject(jsonObj, projectDocuments);
		return projectId;

	}

	/**
	 * @author shivayogi.
	 * @Description : this is duplicate method, this method is not used
	 *              anywhere.This method is same as storeProject() .
	 */
	@RequestMapping(value = "/saveProject", method = RequestMethod.POST)
	public @ResponseBody String saveProject(@RequestParam(value = "json", required = false) String json,
			HttpServletRequest request) throws Exception {

		String json1 = "{" + json + "}";
		JSONObject jsonObj = new JSONObject(json1);

		OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,
				"orgLocations");
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		String loginOrgCode = (String) SourceLeadSessionManager.getSessionAttribute(request, "orgCode");
		projectsService.saveProjectByUserOrgLocationsJson(user, loginOrgLocation, loginOrgCode, jsonObj);
		SourceLeadSessionManager.setSessionAttribute(request, "projectCreateMsg",
				"Successfully you have created a project");
		return "projectDashboard";

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

	protected static boolean isNullOrEmpty(String str) {
		return str == null || "".equals(str.trim());
	}
}
