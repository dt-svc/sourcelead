package com.bis.sourcelead.web;

import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.SourceleadPasswordEncoder;
import com.bis.sourcelead.entity.Employees;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.ProjectApproverHierarchy;
import com.bis.sourcelead.entity.ProjectMembers;
import com.bis.sourcelead.entity.Projects;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.enums.Project_Status;
import com.bis.sourcelead.enums.Status;
import com.bis.sourcelead.service.EmployeesService;
import com.bis.sourcelead.service.ProjectApproverHierarchyService;
import com.bis.sourcelead.service.ProjectMembersService;
import com.bis.sourcelead.service.ProjectsService;
import com.bis.sourcelead.service.TimesheetSubmissionsService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.Constants;
import com.bis.sourcelead.util.SourceLeadRandomCodeHelper;
import com.bis.sourcelead.util.SourceLeadSessionManager;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.json.JsonHierarchicalStreamDriver;
import com.thoughtworks.xstream.io.json.JsonWriter;

@Controller
@RestController
@PropertySource("${propertyLocation:app.properties}")
public class UserController {

	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Value("${sourceleadUrl}")
	private String sourceleadUrl;

	@Autowired
	UserService userService;

	@Autowired
	private ProjectsService projectsService;

	@Autowired
	private ProjectMembersService projectMembersService;

	@Autowired
	public TimesheetSubmissionsService timesheetSubmissionsService;

	@Autowired
	private ProjectApproverHierarchyService projectApproverHierarchyService;

	@Autowired
	private CommonUtil commonUtil;

	@Autowired
	EmployeesService employeesService;

	@Autowired
	private SourceleadPasswordEncoder sourceleadPasswordEncoder;

	@RequestMapping(value = "/userCreation")
	public ModelAndView userCreation(HttpServletRequest request) throws Exception {
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,
				"orgLocations");

		ModelAndView model = new ModelAndView();
		Collection<Projects> projectsList = projectsService.getProjectByOrgLocationsId(orgLocations.getId());
		model.addObject("org_projectsList", projectsList);

		/*
		 * Collection<User> userList =
		 * userService.getUsersByUserRolesAndOrgLocationId(orgLocations.getId())
		 * ; model.addObject("user_List", userList);
		 */

		model.setViewName("users/userCreation");
		model.addObject("sourceleadUrl", sourceleadUrl);
		return model;

	}

	@RequestMapping(value = "/userSummaryDetails")
	public ModelAndView userSummaryDetails(HttpServletRequest request,
			@RequestParam(value = "userId", required = false) Long userId) throws Exception {
		ModelAndView model = new ModelAndView();
		request.setAttribute("userId", userId);
		
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,
				"orgLocations");
		// this method is also using /userCreation 
		Collection<Projects> projectsList = projectsService.getProjectByOrgLocationsId(orgLocations.getId());
		model.addObject("org_projectsList", projectsList);
		model.setViewName("users/userSummary");
		model.addObject("sourceleadUrl", sourceleadUrl);
		return model;

	}

	@RequestMapping(value = "/addProject", method = RequestMethod.POST)
	public @ResponseBody String saveProject(@RequestParam(value = "json", required = false) String json,
			HttpServletRequest request) throws Exception {

		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,
				"orgLocations");

		json = "{" + json + "}";
		JSONObject jsonObj = new JSONObject(json);

		String resultJson = null;

		Projects projects = new Projects();
		OrgLocations ol = new OrgLocations();
		ol.setId(orgLocations.getId());
		projects.setOrgLocationsId(ol);

		projects.setProjectSequeneNumber(jsonObj.getString("ProjectSequeneNumber"));
		projects.setProjectName(jsonObj.getString("ProjectName"));
		projects.setProjectStatus(Project_Status.STARTED);

		if (jsonObj.has("StartDate") && !isNullOrEmpty(jsonObj.getString("StartDate"))) {
			try {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				projects.setStartDate(sdf.parse(jsonObj.getString("StartDate")));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		if (jsonObj.has("EndDate") && !isNullOrEmpty(jsonObj.getString("EndDate"))) {
			try {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				projects.setEndDate(sdf.parse(jsonObj.getString("EndDate")));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		projects.setCreatedDate(new Date());
		projects.setUpdatedDate(new Date());
		Projects updatedProjects = projectsService.saveProject(projects);

		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		commonUtil.storeSourceleadTasks(user, updatedProjects);

		ProjectApproverHierarchy pah1 = new ProjectApproverHierarchy();
		pah1.setProjectId(updatedProjects);
		User approverUserId1 = new User();
		approverUserId1.setId(Long.parseLong(jsonObj.getString("level1Manager")));
		pah1.setApproverUserId(approverUserId1);
		pah1.setLevel(1);
		ProjectApproverHierarchy saveProjectApproverHierarchy = projectApproverHierarchyService
				.saveProjectApproverHierarchy(pah1);

		if (!jsonObj.getString("level2Manager").isEmpty()) {
			ProjectApproverHierarchy pah2 = new ProjectApproverHierarchy();
			pah2.setProjectId(updatedProjects);
			User approverUserId2 = new User();
			approverUserId2.setId(Long.parseLong(jsonObj.getString("level2Manager")));
			pah2.setApproverUserId(approverUserId2);
			pah2.setLevel(2);
			ProjectApproverHierarchy ProjectApproverHierarchy = projectApproverHierarchyService
					.saveProjectApproverHierarchy(pah2);
		}

		Collection<Projects> projectsList = projectsService.getProjectByOrgLocationsId(orgLocations.getId());

		resultJson = toJSON(projectsList);
		return resultJson;
	}

	@RequestMapping(value = "/createUser", method = RequestMethod.POST)
	public @ResponseBody String createUser(@RequestParam(value = "json", required = false) String json,
			HttpServletRequest request) throws Exception {

		json = "{" + json + "}";

		JSONObject jsonObj = new JSONObject(json);

		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,
				"orgLocations");

		// For Saving of User Details
		userService.createUser(jsonObj, user, orgLocations.getId());

		return "usersList";
	}

	// to get the Project Manager Name by using projectId
	@RequestMapping(value = "/getProjectManager", method = RequestMethod.POST)
	public @ResponseBody String getProjectManager(@RequestParam(value = "projectId", required = false) String projectId,
			HttpServletRequest request) throws Exception {

		String resultJson = null;
		ProjectApproverHierarchy ph = null;
		if (projectId != null && !"".equals(projectId)) {
			ph = projectApproverHierarchyService.getLevelOneProjectApproverByProjectId(Long.parseLong(projectId));
		}
		resultJson = toJSON(ph);
		return resultJson;
	}

	// to validate user email
	@RequestMapping(value = "/validateUserEmail", method = RequestMethod.POST)
	public @ResponseBody String validateUserEmail(@RequestParam(value = "EmailId", required = false) String EmailId)
			throws Exception {

		String result = null;
		User user = userService.findByEmail(EmailId);

		if (user != null) {
			result = "true";
		} else {
			result = "false";
		}
		return result;
	}

	// to validate employee Id
	@RequestMapping(value = "/validateEmployeeId", method = RequestMethod.POST)
	public @ResponseBody Boolean validateEmployeeId(
			@RequestParam(value = "EmployeeId", required = false) String EmployeeId, HttpServletRequest request)
					throws Exception {
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,
				"orgLocations");
		Boolean flag = employeesService.validateEmployeeId(EmployeeId, orgLocations.getId());

		return flag;
	}

	// to validate existing employee Id
	@RequestMapping(value = "/validateExistingEmployeeId", method = RequestMethod.POST)
	public @ResponseBody Boolean validateExistingEmployeeId(@RequestParam(value = "id", required = false) Long id,
			@RequestParam(value = "userId", required = false) Long userId,
			@RequestParam(value = "EmployeeId", required = false) String EmployeeId, HttpServletRequest request)
					throws Exception {
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,
				"orgLocations");
		Employees employees = employeesService.getEmployeeByEmployeeIdAndUserIdAndOrgLocationsId(EmployeeId, userId,
				orgLocations.getId());
		System.out.println(employees);
		Boolean flag = false;
		if (employees == null) {
			flag = employeesService.validateEmployeeId(EmployeeId, orgLocations.getId());
		}

		return flag;
	}

	// to get the Project Manager Name by using projectId
	@RequestMapping(value = "/getApproversByUserId", method = RequestMethod.POST)
	public @ResponseBody String getApproversByUserId(
			@RequestParam(value = "currentApproverId", required = false) Long currentApproverId,
			HttpServletRequest request) throws Exception {
		String resultJson = null;
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,
				"orgLocations");
		Collection<User> userList = userService.getUsersByUserRolesAndOrgLocationIdAndNoCurrentUser(currentApproverId,
				orgLocations.getId());
		resultJson = toJSON(userList);
		return resultJson;
	}

	@RequestMapping(value = "/getAllApprovers")
	public @ResponseBody String getAllApprovers(HttpServletRequest request) throws Exception {
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,
				"orgLocations");
		String resultJson = null;
		Collection<User> userList = userService.getUsersByUserRolesAndOrgLocationId(orgLocations.getId());
		resultJson = toJSON(userList);
		return resultJson;

	}

	// to validate Project SequeneNumber
	@RequestMapping(value = "/validateProjectSequeneNumber", method = RequestMethod.POST)
	public @ResponseBody Boolean validateProjectSequeneNumber(
			@RequestParam(value = "ProjectSequeneNumber", required = false) String ProjectSequeneNumber,
			HttpServletRequest request) throws Exception {
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,
				"orgLocations");
		Boolean flag = projectsService.validateProjectSequeneNumber(ProjectSequeneNumber, orgLocations.getId());

		return flag;
	}

	// to re send email to user
	@RequestMapping(value = "/resendInvitationEmail", method = RequestMethod.POST)
	public @ResponseBody String resendInvitationEmail(@RequestParam(value = "userId", required = false) String userId,
			HttpServletRequest request) throws Exception {

		User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		// get user by userId
		User user = userService.getUserById(Long.parseLong(userId));

		// generate random password
		String password = SourceLeadRandomCodeHelper.generateRandomPassword();
		if (user != null) {
			user.setPassword(sourceleadPasswordEncoder.encode(password));
			user.setIsMailSent(Status.Y);
			userService.addUser(user);
			userService.sendEmail(user, loginUser, password);
		}

		return "success";
	}
	/**
	 * get the list of all projects of the particular user
	 * @param request
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	//to get all project details by user id
	@RequestMapping(value = "/getProjectDetailsById", method = RequestMethod.POST)
	public @ResponseBody String getProjectDetailsById(HttpServletRequest request,
			@RequestParam(value = "userId", required = false) Long userId) throws Exception {
		String resultJson = null;
		List<ProjectMembers> allProjectsList = projectMembersService.getAllProjectsByUserId(userId);
		for (ProjectMembers projectMembers : allProjectsList) {
			if (projectMembers.getProjectAssignedDate() != null) {
				projectMembers.setStartDate(
						com.bis.sourcelead.constants.Constants.DF_STD.format(projectMembers.getProjectAssignedDate()));
			}
			if (projectMembers.getProjectEndDate() != null) {
				projectMembers.setEndDate(
						com.bis.sourcelead.constants.Constants.DF_STD.format(projectMembers.getProjectEndDate()));
			} else if (projectMembers.getProjects().getEndDate() != null) {
				projectMembers.setEndDate(com.bis.sourcelead.constants.Constants.DF_STD
						.format(projectMembers.getProjects().getEndDate()));
			}
			Integer draftTimesheetsSubmissionsCount = timesheetSubmissionsService
					.getTotalDraftAndSubmittedHoursByuserId(userId, "DRAFT");
			projectMembers.setDraftHrs(draftTimesheetsSubmissionsCount.toString());

			Integer submittedTimesheetsSubmissionsCount = timesheetSubmissionsService
					.getTotalDraftAndSubmittedHoursByuserId(userId, "SUBMITTED");
			projectMembers.setSubmittedHrs(submittedTimesheetsSubmissionsCount.toString());

			Integer approvalTimesheetsSubmissionsCount = timesheetSubmissionsService
					.getTotalApprovedOrRejectedHoursByuserId(userId, "APPROVED");
			projectMembers.setApprovedHrs(approvalTimesheetsSubmissionsCount.toString());

			Integer rejectedTimesheetsSubmissionsCount = timesheetSubmissionsService
					.getTotalApprovedOrRejectedHoursByuserId(userId, "REJECTED");
			projectMembers.setRejectedHrs(rejectedTimesheetsSubmissionsCount.toString());
		}

		resultJson = toJSON(allProjectsList);
		return resultJson;
	}
	/**
	 * to assign the project to the particular user
	 * @param json
	 * @param userId
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveAssignProject", method = RequestMethod.POST)
	public @ResponseBody String saveAssignProject(@RequestParam(value = "json", required = false) String json,
			@RequestParam(value = "userId", required = false) Long userId, HttpServletRequest request)
					throws Exception {
		json = "{" + json + "}";
		JSONObject jsonObj = new JSONObject(json);
		Employees employee = employeesService.getEmployeeByUserId(userId);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Projects projects = projectsService.getProjectById(jsonObj.getLong("projectId"));
		Date projectStartdate = projects.getStartDate();
		if (jsonObj.has("StartDate") && !isNullOrEmpty(jsonObj.getString("StartDate"))) {
			if (projectStartdate.compareTo(sdf.parse(jsonObj.getString("StartDate"))) != 0) {
				projectsService.updateStartDateOfProjectId(sdf.parse(jsonObj.getString("StartDate")),
						jsonObj.getLong("projectId"));
			}
		}
		if (projects.getEndDate() != null) {
			Date projectEndDate = projects.getEndDate();
			if (jsonObj.has("EndDate") && !isNullOrEmpty(jsonObj.getString("EndDate"))) {
				if (projectEndDate.compareTo(sdf.parse(jsonObj.getString("EndDate"))) != 0) {
					projectsService.updateEndDateOfProjectId(sdf.parse(jsonObj.getString("EndDate")),
							jsonObj.getLong("projectId"));
				}
			}
		}
		ProjectMembers projectMembers = new ProjectMembers();
		projectMembers.setEmployee(employee);
		projectMembers.setProjects(projects);
		projectMembers.setTimesheetFrequency("weekly");
		if (jsonObj.has("StartDate") && !isNullOrEmpty(jsonObj.getString("StartDate"))) {
			try {
				projectMembers.setProjectAssignedDate(sdf.parse(jsonObj.getString("StartDate")));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		if (jsonObj.has("EndDate") && !isNullOrEmpty(jsonObj.getString("EndDate"))) {
			try {
				projectMembers.setProjectEndDate(sdf.parse(jsonObj.getString("EndDate")));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		User user = userService.getUserById(userId);
		if (employee.getRate() != null) {
			projectMembers.setCurrentBillRate(Float.parseFloat(employee.getRate()));
		}
		// need change role
		projectMembers.setRole(user.getUserType());
		if (employee.getRatePer() != null) {
			projectMembers.setRateType(employee.getRatePer());
		}
		projectMembers.setStatus(Constants.MEMBER_ACTIVE);
		projectMembersService.saveProjectMember(projectMembers);
		commonUtil.storeTimesheetSubmissionRecords(user);
		return "success";
	}
	/**
	 * to save the reason when we terminate the user from project 
	 * @param projectMemberId
	 * @param reason
	 * @return
	 */
	@RequestMapping(value = "/saveReason", method = RequestMethod.POST)
	public @ResponseBody String saveReason(
			@RequestParam(value = "projectMemberId", required = false) Long projectMemberId,
			@RequestParam(value = "reason", required = false) String reason) {
		Integer updatedCount = projectMembersService.updateProjectMemberDeletedReason(projectMemberId, reason);
		logger.info("updated records" + updatedCount);
		return "success";
	}
	
	/**
	 * to update the end date of project in assign project
	 * @param endDate
	 * @param projectId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateEndDateByProjectId", method = RequestMethod.POST)
	public String updateProjectTaskById(@RequestParam(value = "endDate", required = false) Date endDate,
			@RequestParam(value = "projectId", required = false) Long projectId) throws Exception {

		Projects projects = projectsService.getProjectById(projectId);
		if (endDate != null) {
			if (projects.getEndDate() == null) {
				projectsService.updateEndDateOfProjectId(endDate, projectId);
			}
			if (projects.getEndDate().compareTo(endDate) != 0) {
				projectsService.updateEndDateOfProjectId(endDate, projectId);
			}
		}
		return "updated";

	}

	private String toJSON(Object obj) {
		XStream xstream = new XStream(new JsonHierarchicalStreamDriver() {
			public HierarchicalStreamWriter createWriter(Writer writer) {
				return new JsonWriter(writer, JsonWriter.DROP_ROOT_MODE);
			}
		});
		return xstream.toXML(obj);
	}

	protected static boolean isNullOrEmpty(String str) {
		return (str == null || "".equals(str.trim()));
	}
}
