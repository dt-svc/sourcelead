package com.bis.sourcelead.rest;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.annotations.Validator;
import com.bis.sourcelead.beans.BudgetReportDTO;
import com.bis.sourcelead.beans.TaskPriorityReportDTO;
import com.bis.sourcelead.beans.TasksPercentageReportDTO;
import com.bis.sourcelead.beans.TasksReportDTO;
import com.bis.sourcelead.beans.TasksStackedReportDTO;
import com.bis.sourcelead.beans.TimesheetReportDTO;
import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dto.CustomFormFieldDTO;
import com.bis.sourcelead.dto.ProjectTaskDocumentDTO;
import com.bis.sourcelead.dto.ProjectTasksDTO;
import com.bis.sourcelead.dto.Response;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.dto.TaskDTO;
import com.bis.sourcelead.dto.TimeSheetDayEntriesDTO;
import com.bis.sourcelead.dto.TimeSheetDayEntriesDTOList;
import com.bis.sourcelead.entity.Expenses;
import com.bis.sourcelead.entity.Invoice;
import com.bis.sourcelead.entity.LookUpData;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrglocationFormFields;
import com.bis.sourcelead.entity.ProjectAssignTask;
import com.bis.sourcelead.entity.ProjectBudget;
import com.bis.sourcelead.entity.ProjectMembers;
import com.bis.sourcelead.entity.ProjectMembersHistory;
import com.bis.sourcelead.entity.ProjectTaskActivity;
import com.bis.sourcelead.entity.ProjectTaskComments;
import com.bis.sourcelead.entity.ProjectTaskCustomFieldData;
import com.bis.sourcelead.entity.ProjectTaskDocuments;
import com.bis.sourcelead.entity.ProjectTasks;
import com.bis.sourcelead.entity.Projects;
import com.bis.sourcelead.entity.SubmissionApproverHierarchy;
import com.bis.sourcelead.entity.TimeSheetDayEntries;
import com.bis.sourcelead.entity.Timezone;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.enums.Result;
import com.bis.sourcelead.service.ExpensesService;
import com.bis.sourcelead.service.InvoiceService;
import com.bis.sourcelead.service.LookUpDataService;
import com.bis.sourcelead.service.OrglocationFormFieldsService;
import com.bis.sourcelead.service.ProjectAssignTasksService;
import com.bis.sourcelead.service.ProjectBudgetService;
import com.bis.sourcelead.service.ProjectMembersHistoryService;
import com.bis.sourcelead.service.ProjectMembersService;
import com.bis.sourcelead.service.ProjectTaskActivityService;
import com.bis.sourcelead.service.ProjectTaskCommentsService;
import com.bis.sourcelead.service.ProjectTaskCustomFieldDataService;
import com.bis.sourcelead.service.ProjectTaskDocumentsService;
import com.bis.sourcelead.service.ProjectTasksService;
import com.bis.sourcelead.service.ProjectsService;
import com.bis.sourcelead.service.TimeZoneService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.timesheet.dao.SubmissionApproverHierarchyDao;
import com.bis.sourcelead.timesheet.service.SubmissionApproverHierarchyService;
import com.bis.sourcelead.timesheet.service.TimeSheetDayEntriesService;
import com.bis.sourcelead.timesheet.service.TimesheetSubmissionsService;
import com.bis.sourcelead.util.CalendarUtil;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.ConversionUtil;
import com.bis.sourcelead.util.ReportsUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;
import com.ibm.icu.text.DateFormat;
import com.ibm.icu.text.SimpleDateFormat;

@RestController
@RequestMapping("/app/ProjectTasks")
public class ProjectTasksRestController {

	@Value("${static.path}")
	private String staticPath;

	@Autowired
	public ProjectTasksService tasksService;

	@Autowired
	CommonUtil commonUtil;

	@Autowired
	public UserService userService;

	@Autowired
	LookUpDataService lookUpDataService;

	@Autowired
	private ProjectTaskDocumentsService projectTaskDocumentsService;

	@Autowired
	ProjectAssignTasksService projectAssignTasksService;

	@Autowired
	ProjectTaskActivityService projectTaskActivityService;

	@Autowired
	ProjectTasksService projectTasksService;

	@Autowired
	ProjectTaskCommentsService projectTaskCommentsService;

	@Autowired
	TimeZoneService timeZoneService;

	@Autowired
	ProjectsService projectsService;

	@Autowired
	ProjectBudgetService projectBudgetService;

	@Autowired
	private ProjectMembersService projectMembersService;

	@Autowired
	private ProjectMembersHistoryService projectMembersHistoryService;

	@Autowired
	private SubmissionApproverHierarchyService submissionApproverHierarchyService;

	@Autowired
	private TimeSheetDayEntriesService timeSheetDayEntriesService;

	@Autowired
	private SubmissionApproverHierarchyDao submissionApproverHierarchyDao;

	@Autowired
	private ExpensesService expensesService;

	@Autowired
	private InvoiceService invoiceService;

	@Autowired
	ConversionUtil conversionUtil;

	@Autowired
	ReportsUtil reportsUtil;

	@Autowired
	private TimesheetSubmissionsService timesheetSubmissionsService;

	@Autowired
	private ProjectTaskCustomFieldDataService projectTaskCustomFieldDataService;

	@Autowired
	private OrglocationFormFieldsService orglocationFormFieldsService;

	/**
	 * @author David sammeta
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveOrUpdate", method = RequestMethod.GET)
	public @ResponseBody ProjectTasks addTasks() throws Exception {
		ProjectTasks tasks = new ProjectTasks();
		return tasksService.addTasks(tasks);
	}

	@RequestMapping(value = "/update", method = RequestMethod.GET)
	public @ResponseBody ProjectTasks updateTasks() throws Exception {
		ProjectTasks tasks = new ProjectTasks();
		return tasksService.updateTasks(tasks);
	}

	@RequestMapping(value = "/getTasks/{id}", method = RequestMethod.GET)
	public @ResponseBody ProjectTasks getTasksId(@PathVariable String id) throws Exception {
		ProjectTasks tasks = tasksService.getTasksId(id);
		return tasks;
	}

	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
	public @ResponseBody Boolean deleteTask(@PathVariable String id) throws Exception {
		Boolean status = tasksService.deleteTask(id);
		return status;
	}

	@RequestMapping(value = "/getAll", method = RequestMethod.GET)
	public @ResponseBody Collection<ProjectTasks> getAllTasks() throws Exception {
		Collection<ProjectTasks> tasksList = tasksService.getAllTasks();
		return tasksList;
	}

	/**
	 * save the projects task
	 * 
	 * @param json
	 * @param projectId
	 * @return JSON
	 * @throws Exception
	 */
	@PreAuthorize("hasAnyRole('ROLE_CREATE_PROJECT_TASKS')")
	@RequestMapping(value = "/saveProjectTasks", method = RequestMethod.POST)
	public @ResponseBody Response<String> saveProjectTasks(ProjectTasksDTO projectTasksDTO,
			@RequestParam(value = "projectId", required = false) Long projectId,
			@RequestParam(value = "userId", required = false) Long userId) throws Exception {
		Response<String> response = new Response<>();

		try {
			if (Validator.validate(projectTasksDTO)) {
				projectTasksDTO.setStaticPath(staticPath);
				String saveProjectTasks = tasksService.saveProjectTasksDTOByProjectId(projectId, projectTasksDTO,userId);
				response.setResponse(Result.SUCCESS.name());
				response.setResponseMessage(Result.SUCCESS.getValue());
			} else {
				response.setResponse(Result.INVALID_DATA.name());
				response.setResponseMessage(Result.INVALID_DATA.getValue());
			}
		} catch (Exception e) {
			response.setResponse(Result.AWKWARD.name());
			response.setResponseMessage(Result.AWKWARD.getValue());
		}
		return response;
	}

	@RequestMapping(value = "/saveProjectTaskCustomFieldDetails", method = RequestMethod.POST)
	public @ResponseBody void saveProjectTaskCustomFieldDetails(ProjectTasksDTO projectTasksDTO) throws Exception {
		if (projectTasksDTO.getCustomFormData() != null) {
			for (CustomFormFieldDTO customFields : projectTasksDTO.getCustomFormData()) {
				if (customFields.getCustomFormFieldId() > 0
						&& StringUtils.isNotEmpty(customFields.getCustomFormFieldData())) {
					ProjectTaskCustomFieldData projectTaskCustomFields = null;
					if (customFields.getEntityId() != null && customFields.getEntityId() > 0) {
						projectTaskCustomFields = projectTaskCustomFieldDataService
								.getProjectTaskCustomFieldDataById(customFields.getEntityId());
					} else {
						projectTaskCustomFields = new ProjectTaskCustomFieldData();
					}
					ProjectTasks saveTask = null;
					if (projectTasksDTO.getId() != null) {
						saveTask = projectTasksService.getTasksId(projectTasksDTO.getId().toString());
					} else {
						saveTask = new ProjectTasks();
					}
					projectTaskCustomFields.setProjectTask(saveTask);
					OrglocationFormFields orgFormField = orglocationFormFieldsService
							.getOrglocationFormFieldsById(customFields.getCustomFormFieldId());
					projectTaskCustomFields.setOrglocationsFormFields(orgFormField);
					projectTaskCustomFields.setCustomFormFieldData(customFields.getCustomFormFieldData());
					projectTaskCustomFieldDataService.saveProjectTaskCustomFieldData(projectTaskCustomFields);
				}

			}
		}
	}

	/**
	 * ]
	 * 
	 * @author Laxman
	 * @param comment
	 * @param projectTaskId
	 * @param userId
	 * @return success
	 * @throws Exception
	 */
 
	@RequestMapping(value = "/saveProjectTaskComment", method = RequestMethod.POST)
	public @ResponseBody Response<String> saveProjectTaskComment(ProjectTaskDocumentDTO projectTaskDocumentDTO,TaskDTO taskDTO,HttpServletRequest request)
			throws Exception {
		OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
		Long candidateId = (Long) SourceLeadSessionManager.getSessionAttribute(request,"candidateId");
		taskDTO.setCandidateId(candidateId);
		Response<String> response = new Response<>();
		try {
			if (Validator.validate(projectTaskDocumentDTO)) {
				projectTaskCommentsService.storeprojectComments(projectTaskDocumentDTO,taskDTO,loginOrgLocation);
				response.setResponse(Result.SUCCESS.name());
				response.setResponseMessage(Result.SUCCESS.getValue());
			} else {
				response.setResponse(Result.INVALID_DATA.name());
				response.setResponseMessage(Result.INVALID_DATA.getValue());
			}
		} catch (Exception e) {
			response.setResponse(Result.AWKWARD.name());
			response.setResponseMessage(Result.AWKWARD.getValue());
		}
		return response;
	}

	/**
	 * @author Laxman
	 * @param projectTasksDTO
	 * @param projectTaskId
	 * @param userId
	 * @param documentId
	 * @return success
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveProjectTaskDocuments", method = RequestMethod.POST)
	public @ResponseBody Response<String> saveProjectTaskDocuments(ProjectTaskDocumentDTO projectTaskDocumentDTO)
			throws Exception {

		Response<String> response = new Response<>();
		try {
			if (Validator.validate(projectTaskDocumentDTO) || (projectTaskDocumentDTO.getValidDcoument() != null
					&& !projectTaskDocumentDTO.getValidDcoument().isEmpty())) {
				projectTaskDocumentDTO.setStaticPath(staticPath);
				projectTaskDocumentsService.storeProjectTaskDocuments(projectTaskDocumentDTO);
				response.setResponse(Result.SUCCESS.name());
				response.setResponseMessage(Result.SUCCESS.getValue());
			} else {
				response.setResponse(Result.INVALID_DATA.name());
				response.setResponseMessage(Result.INVALID_DATA.getValue());
			}
		} catch (Exception e) {
			response.setResponse(Result.AWKWARD.name());
			response.setResponseMessage(Result.AWKWARD.getValue());
		}
		return response;

	}

	/**
	 * @author Laxman
	 * @param projectTaskId
	 * @return commnetList
	 * @throws Exception
	 */
	@RequestMapping(value = "/getTaskCommentList", method = RequestMethod.POST)
	public @ResponseBody List<ProjectTaskComments> getTaskCommentList(
			@RequestParam(value = "projectTaskId", required = false) Long projectTaskId) throws Exception {
		List<ProjectTaskComments> commnetList = projectTaskCommentsService
				.getProjectTaskCommentsByProjectTaskId(projectTaskId);
		for (ProjectTaskComments comments : commnetList) {
			Timezone timezone = timeZoneService
					.getTimezone(comments.getProjectTaskId().getProjectId().getOrgLocationsId().getTimezone().getId());
			comments.setCreatedStr(commonUtil.dateStrFromOrgDateFormatter(
					comments.getProjectTaskId().getProjectId().getOrgLocationsId().getDateFormat(),
					comments.getCreatedDate()));
			if (comments.getCreatedDate() != null) {
				DateFormat DF_DMY = new SimpleDateFormat("hh:mma");
				String currentTimeStr = Constants.TZ_DF.format(comments.getCreatedDate());
				String startTimeUTC = CommonUtil.convertTimeZones(Constants.UTC_TIMEZONE, timezone.getTimeZoneId(),
						currentTimeStr);
				DateFormat TZ_DF_TS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date convertedDate = TZ_DF_TS.parse(startTimeUTC);
				String convertedDateStr = DF_DMY.format(convertedDate);
				comments.setActivityTimezoneStr(convertedDateStr);
			}
			comments.setFileUrl(comments.getUserId().getFileUrl());
			long diff = commonUtil.currentDate().getTime() - comments.getCreatedDate().getTime();
			long diffSeconds = diff / 1000 % 60;
			long diffMinutes = diff / (60 * 1000) % 60;
			long diffHours = diff / (60 * 60 * 1000) % 24;
			long diffDays = diff / (24 * 60 * 60 * 1000);
			StringBuilder timeBuilder = new StringBuilder();
			if (diffDays == 0 && diffHours == 0 && diffMinutes == 0) {
				comments.setActionTimeStr(timeBuilder.append(diffSeconds).append("seconds ago").toString());
			} else if (diffDays == 0 && diffHours == 0) {
				comments.setActionTimeStr(timeBuilder.append(diffMinutes).append(" minutes ago").toString());
			} else if (diffDays == 0) {
				comments.setActionTimeStr(timeBuilder.append(diffHours).append(" hours ").append(diffMinutes)
						.append(" minutes ago").toString());
			} else if (diffDays < 7) {
				comments.setActionTimeStr(timeBuilder.append(diffDays).append(" days ").append(diffHours)
						.append(" hours ago").toString());
			} else {
				DateFormat standardFormat = new SimpleDateFormat("MMM dd, yyyy hh:mm:ss aa");
				String commonDateFormat = standardFormat.format(comments.getCreatedDate());
				comments.setActionTimeStr(commonDateFormat.toString());
			}
		}
		return commnetList;
	}

	/**
	 * @author Laxman
	 * @param projectTaskId
	 * @return taskDocumentList
	 * @throws Exception
	 */
	@RequestMapping(value = "/getProjectTasksDocuments", method = RequestMethod.POST)
	public @ResponseBody ResponseList<ProjectTaskDocuments> getProjectTasksDocuments(
			@RequestParam(value = "projectTaskId", required = false) Long projectTaskId) throws Exception {
		ResponseList<ProjectTaskDocuments> response = new ResponseList<>();
		try {
			if (projectTaskId != null) {
				List<ProjectTaskDocuments> taskDocumentList = projectTaskDocumentsService
						.getProjectTaskDocumentsListByProjectIdId(projectTaskId);
				response.setData(taskDocumentList);
				response.setResponse(Result.SUCCESS.name());
				response.setResponseMessage(Result.SUCCESS.getValue());
			} else {
				response.setResponse(Result.INVALID_DATA.name());
				response.setResponseMessage(Result.INVALID_DATA.getValue());
			}
		} catch (Exception e) {
			response.setResponse(Result.AWKWARD.name());
			response.setResponseMessage(Result.AWKWARD.getValue());
		}
		return response;
	}

	/**
	 * @author Laxman
	 * @param projectId
	 * @param subTaskId
	 * @return taskList;
	 * @throws Exception
	 */
	@RequestMapping(value = "/getsubTasksList", method = RequestMethod.POST)
	public @ResponseBody List<ProjectTasks> getsubTasksList(
			@RequestParam(value = "projectId", required = false) Long projectId,
			@RequestParam(value = "subTaskId", required = false) Long subTaskId) throws Exception {
		List<ProjectTasks> taskList = tasksService.getAllTasksByProjectIdAndParentTaskId(projectId, subTaskId);
		return taskList;
	}

	/**
	 * @author Laxman
	 * @param projectTaskId
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteProjectSubTasks", method = RequestMethod.POST)
	public @ResponseBody void deleteProjectSubTasks(
			@RequestParam(value = "projectTaskId", required = false) String projectTaskId) throws Exception {
		ProjectTasks tasks = tasksService.getTasksId(projectTaskId);
		if (tasks != null) {
			tasks.setStatus(String.valueOf(Constants.CANDIDATE_IN_ACTIVE));
			tasks.setUpdatedDate(new Date());
			tasksService.updateTasks(tasks);
		}
	}

	/**
	 * @author Laxman
	 * @param projectTaskId
	 * @return taskActivityList
	 * @throws Exception
	 */
	@RequestMapping(value = "/getTaskActivityList", method = RequestMethod.POST)
	public @ResponseBody List<ProjectTaskActivity> getTaskActivityList(
			@RequestParam(value = "projectTaskId", required = false) Long projectTaskId) throws Exception {
		List<ProjectTaskActivity> taskActivityList = projectTaskActivityService
				.getProjectTaskActivityByProjectTaskId(projectTaskId);

		for (ProjectTaskActivity activity : taskActivityList) {
			Timezone timezone = timeZoneService
					.getTimezone(activity.getProjectTaskId().getProjectId().getOrgLocationsId().getTimezone().getId());
			activity.setCreatedStr(commonUtil.dateStrFromOrgDateFormatter(
					activity.getProjectTaskId().getProjectId().getOrgLocationsId().getDateFormat(),
					activity.getCreatedDate()));
			if (activity.getCreatedDate() != null) {
				DateFormat DF_DMY = new SimpleDateFormat("hh:mma");
				String currentTimeStr = Constants.TZ_DF.format(activity.getCreatedDate());
				String startTimeUTC = CommonUtil.convertTimeZones(Constants.UTC_TIMEZONE, timezone.getTimeZoneId(),
						currentTimeStr);
				DateFormat TZ_DF_TS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date convertedDate = TZ_DF_TS.parse(startTimeUTC);
				String convertedDateStr = DF_DMY.format(convertedDate);
				activity.setActivityTimezoneStr(convertedDateStr);
			}
			activity.setFileUrl(activity.getUserId().getFileUrl());
			long diff = commonUtil.currentDate().getTime() - activity.getCreatedDate().getTime();
			long diffSeconds = diff / 1000 % 60;
			long diffMinutes = diff / (60 * 1000) % 60;
			long diffHours = diff / (60 * 60 * 1000) % 24;
			long diffDays = diff / (24 * 60 * 60 * 1000);
			StringBuilder timeBuilder = new StringBuilder();
			if (diffDays == 0 && diffHours == 0 && diffMinutes == 0) {
				activity.setActionTimeStr(timeBuilder.append(diffSeconds).append("seconds ago").toString());
			} else if (diffDays == 0 && diffHours == 0) {
				activity.setActionTimeStr(timeBuilder.append(diffMinutes).append(" minutes ago").toString());
			} else if (diffDays == 0) {
				activity.setActionTimeStr(timeBuilder.append(diffHours).append(" hours ").append(diffMinutes)
						.append(" minutes ago").toString());
			} else if (diffDays < 7) {
				activity.setActionTimeStr(timeBuilder.append(diffDays).append(" days ").append(diffHours)
						.append(" hours ago").toString());
			} else {
				DateFormat standardFormat = new SimpleDateFormat("MMM dd, yyyy hh:mm:ss aa");
				String commonDateFormat = standardFormat.format(activity.getCreatedDate());
				activity.setActionTimeStr(commonDateFormat.toString());
			}
			 
		}
		return taskActivityList;
	}

	/**
	 * @author Laxman
	 * @param projectId
	 * @param taskType
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getProjectTasksByTaskType", method = RequestMethod.POST)
	public @ResponseBody Collection<ProjectTasks> getProjectTasksByTaskType(
			@RequestParam(value = "projectId", required = false) Long projectId,
			@RequestParam(value = "taskType", required = false) String taskType) throws Exception {
		LookUpData lookupdata = lookUpDataService.getTasksTypeByUniqueKey(taskType);
		Collection<ProjectTasks> taskList = tasksService.getAllTasksByProjectIdAndTaskType(projectId,
				lookupdata.getId());
		return taskList;
	}

	/**
	 * @author Laxman
	 * @param taskStatusId
	 * @param taskId
	 * @return success
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateTaskStatus", method = RequestMethod.POST)
	public String updateTaskStatus(TaskDTO taskDTO,HttpServletRequest request) throws Exception {
		OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
		Long candidateId = (Long) SourceLeadSessionManager.getSessionAttribute(request,"candidateId");
		taskDTO.setCandidateId(candidateId);
		tasksService.updateTaskStatus(taskDTO,loginOrgLocation);
		return "success";
	}

	/**
	 * @author Laxman
	 * @param taskStatusId
	 * @param taskId
	 * @param userId
	 * @return success
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateTaskName", method = RequestMethod.POST)
	public String updateTaskName(ProjectTasksDTO projectTasksDTO,
			@RequestParam(value = "taskId", required = false) String taskId,
			@RequestParam(value = "userId", required = false) Long userId) throws Exception {
		tasksService.updateTaskName(projectTasksDTO.getTaskName(), taskId, userId);
		return projectTasksDTO.getTaskName();
	}

	/**
	 * @author Laxman
	 * @param taskpriorityId
	 * @param taskId
	 * @return success
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateTaskPriority", method = RequestMethod.POST)
	public String updateTaskPriority(TaskDTO taskDTO,HttpServletRequest request) throws Exception {
		OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
		Long candidateId = (Long) SourceLeadSessionManager.getSessionAttribute(request,"candidateId");
		taskDTO.setCandidateId(candidateId);
		tasksService.updateTaskPriority(taskDTO, loginOrgLocation);
		return "success";
	}

	/**
	 * @author Laxman
	 * @param taskSeverityId
	 * @param taskId
	 * @return success
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateTaskSeverity", method = RequestMethod.POST)
	public String updateTaskSeverity(TaskDTO taskDTO,HttpServletRequest request) throws Exception {
		OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
		Long candidateId = (Long) SourceLeadSessionManager.getSessionAttribute(request,"candidateId");
		taskDTO.setCandidateId(candidateId);
		tasksService.updateTaskSeverity(taskDTO, loginOrgLocation);
		return "success";
	}

	/**
	 * @author Laxman
	 * @param percentage
	 * @param taskId
	 * @param userId
	 * @return success
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateTaskPercentage", method = RequestMethod.POST)
	public String updateTaskPercentage(@RequestParam(value = "percentage", required = false) Integer percentage,
			@RequestParam(value = "taskId", required = false) String taskId,
			@RequestParam(value = "userId", required = false) Long userId) throws Exception {
		tasksService.updateTaskPercentage(percentage, taskId, userId);
		return "success";
	}

	/**
	 * @author Laxman
	 * @param billingId
	 * @param taskId
	 * @param userId
	 * @return successsssss
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateBillingType", method = RequestMethod.POST)
	public String updateBillingType(@RequestParam(value = "billingId", required = false) String billingId,
			@RequestParam(value = "taskId", required = false) String taskId,
			@RequestParam(value = "userId", required = false) Long userId) throws Exception {
		tasksService.updateBillingType(billingId, taskId, userId);
		return "success";
	}

	/**
	 * @author Laxman
	 * @param editDueDate
	 * @param taskId
	 * @param userId
	 * @return success
	 * @throws Exception
	 */
	
	@RequestMapping(value = "/updateDueDate", method = RequestMethod.POST)
	public String updateDueDate(TaskDTO taskDTO,HttpServletRequest request) throws Exception {
		OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
		Long candidateId = (Long) SourceLeadSessionManager.getSessionAttribute(request,"candidateId");
		taskDTO.setCandidateId(candidateId);
		
		tasksService.updateDueDate(taskDTO,loginOrgLocation);
		return "success";
	}

	/**
	 * @author Laxman
	 * @param estimatedTime
	 * @param taskId
	 * @param userId
	 * @return success
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateEstimatedTime", method = RequestMethod.POST)
	public String updateEstimatedTime(TaskDTO taskDTO,HttpServletRequest request) throws Exception {
		OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
		Long candidateId = (Long) SourceLeadSessionManager.getSessionAttribute(request,"candidateId");
		taskDTO.setCandidateId(candidateId);
		tasksService.updateEstimatedTime(taskDTO,loginOrgLocation);
		return "success";
	}

	/**
	 * @author Laxman
	 * @param ownerId
	 * @param taskId
	 * @param userId
	 * @return success
	 * @throws Exception
	 */

	@RequestMapping(value = "/updateTaskOwner", method = RequestMethod.POST)
	public String updateTaskOwner(@RequestParam(value = "ownerId", required = false) Long ownerId,
			@RequestParam(value = "taskId", required = false) String taskId,
			@RequestParam(value = "userId", required = false) Long userId) throws Exception {
		tasksService.updateOwner(ownerId, taskId, userId);
		return "success";
	}

	/**
	 * @author Laxman
	 * @param epicId
	 * @param taskId
	 * @param userId
	 * @return success
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateEpic", method = RequestMethod.POST)
	public String updateEpic(@RequestParam(value = "epicId", required = false) Long epicId,
			@RequestParam(value = "taskId", required = false) String taskId,
			@RequestParam(value = "userId", required = false) Long userId) throws Exception {
		tasksService.updateEpic(epicId, taskId, userId);
		return "success";
	}

	/**
	 * @author Laxman
	 * @param userStoryId
	 * @param taskId
	 * @param userId
	 * @return success
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateUserStroy", method = RequestMethod.POST)
	public String updateUserStroy(@RequestParam(value = "userStoryId", required = false) Long userStoryId,
			@RequestParam(value = "taskId", required = false) String taskId,
			@RequestParam(value = "userId", required = false) Long userId) throws Exception {
		tasksService.updateUserStory(userStoryId, taskId, userId);
		return "success";
	}

	/**
	 * @author Laxman
	 * @param getProjectTasksDescrption
	 * @param taskId
	 * @return success
	 * @throws Exception
	 */
	@RequestMapping(value = "/getProjectTasksDescrption", method = RequestMethod.POST)
	public ProjectTasks getProjectTasksDescrption(
			@RequestParam(value = "projectTaskId", required = false) String projectTaskId,
			@RequestParam(value = "taskId", required = false) String taskId) throws Exception {
		ProjectTasks projectTasks = tasksService.getTasksId(projectTaskId);
		return projectTasks;
	}

	/**
	 * @author Laxman
	 * @param description
	 * @param taskId
	 * @return success
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateTaskDescription", method = RequestMethod.POST)
	public String updateTaskDescription(ProjectTasksDTO projectTasksDTO,
			@RequestParam(value = "description", required = false) String description,
			@RequestParam(value = "taskId", required = false) String taskId,
			@RequestParam(value = "userId", required = false) Long userId) throws Exception {
		User user = userService.getUserById(Long.valueOf(projectTasksDTO.getLoginUserId()));
		StringBuffer activity = new StringBuffer();
		StringBuffer activitySummary = new StringBuffer();
		ProjectTasks projectTasks = tasksService.getTasksId(String.valueOf(projectTasksDTO.getId()));
		ProjectTasks oldProjectTasks = tasksService.getTasksId(String.valueOf(projectTasksDTO.getId()));
		activity.append("Description");
		projectTasks.setDescription(projectTasksDTO.getTaskDescription());
		projectTasks.setUpdatedDate(commonUtil.currentDate());
		tasksService.updateTasks(projectTasks);
		String resourceTable = getUpdateDescription(projectTasks, oldProjectTasks);
		activitySummary.append(resourceTable);
		projectTaskActivityService.saveProjectTaskActivities(activity.toString(), activitySummary.toString(),
				projectTasks, user);

		return "success";
	}

	private String getUpdateDescription(ProjectTasks projectTasks, ProjectTasks oldProjectTasks) {
		String tempHTML = "";
		if (oldProjectTasks.getDescription() != null) {
			tempHTML = tempHTML + "<p style='color:#909090;'>" + oldProjectTasks.getDescription() + "</p> ";
			tempHTML = tempHTML + "<p>" + projectTasks.getDescription() + "</p>";
		} else {
			tempHTML = tempHTML + "<p>" + projectTasks.getDescription() + "</p>";
		}

		return tempHTML;
	}

	/**
	 * @author Laxman
	 * @param assignValue
	 * @param taskId
	 * @return success
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateAssignTaskUsers", method = RequestMethod.POST)
	public String updateAssignTaskUsers(ProjectTasksDTO projectTasksDTO,
			@RequestParam(value = "assignValue", required = false) String assignValue,
			@RequestParam(value = "taskId", required = false) String projectTaskId,
			@RequestParam(value = "userId", required = false) Long userId,
			HttpServletRequest request) throws Exception {
		OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,
				"orgLocations");
		projectAssignTasksService.inlineUpdateAssignTaskUsers(projectTasksDTO, assignValue, projectTaskId, userId,loginOrgLocation);
		return "success";
	}

	/**
	 * @author Laxman
	 * @param documentId
	 * @param title
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewProjectTaskDocument/{title}", method = RequestMethod.GET)
	public void viewProjectTaskDocument(@RequestParam(value = "documentId", required = false) Long documentId,
			@PathVariable(value = "title") String title, HttpServletResponse response) throws Exception {
		projectTaskDocumentsService.viewProjectTaskDocument(documentId, response);
	}

	/**
	 * @author Laxman
	 * @param porjectTaskDocumentId
	 * @param userId
	 * @return success
	 * @throws Exception
	 */

	@RequestMapping(value = "/deleteProjectTaskDocument", method = RequestMethod.POST)
	public @ResponseBody String deleteProjectTaskDocument(
			@RequestParam(value = "porjectTaskDocumentId", required = false) Long porjectTaskDocumentId,
			@RequestParam(value = "userId", required = false) Long userId) throws Exception {
		User user = userService.getUserById(userId);
		ProjectTaskDocuments projectTaskDoc = projectTaskDocumentsService
				.getProjectTaskDocumentsById(porjectTaskDocumentId);
		projectTaskDocumentsService.deleteProjectTaskDocumentByPorjectTaskDocumentId(porjectTaskDocumentId);

		// store Task Activity

		StringBuffer activity = new StringBuffer();
		StringBuffer activitySummary = new StringBuffer();
		activity.append("Delete Attachment");
		activitySummary.append(" : ").append(projectTaskDoc.getFileName());
		projectTaskActivityService.saveProjectTaskActivities(activity.toString(), activitySummary.toString(),
				projectTaskDoc.getProjectTaskId(), user);
		return "success";
	}

	/**
	 * @author Laxman
	 * @param projectTaskDocId
	 * @return projectTaskDoc
	 * @throws Exception
	 */
	@RequestMapping(value = "/getProjectTaskDocument", method = RequestMethod.POST)
	public ProjectTaskDocuments getProjectTaskDocument(
			@RequestParam(value = "projectTaskDocId", required = false) Long projectTaskDocId) throws Exception {
		ProjectTaskDocuments projectTaskDoc = projectTaskDocumentsService.getProjectTaskDocumentsById(projectTaskDocId);
		return projectTaskDoc;
	}

	/**
	 * @author Laxman
	 * @param projectId
	 * @param taskType
	 * @return taskList
	 * @throws Exception
	 */
	@RequestMapping(value = "/getProjectTasksBySubTaskType", method = RequestMethod.POST)
	public @ResponseBody Collection<ProjectTasks> getProjectTasksBySubTaskType(
			@RequestParam(value = "projectId", required = false) Long projectId,
			@RequestParam(value = "taskType", required = false) String taskType) throws Exception {
		LookUpData lookupdata = lookUpDataService.getTasksTypeByUniqueKey(taskType);
		Collection<ProjectTasks> taskList = tasksService.getAllTasksByProjectIdAndSubTaskType(projectId,
				lookupdata.getId());
		return taskList;
	}

	/**
	 * @author Laxman
	 * @param projectId
	 * @return report
	 * @throws Exception
	 */
	@RequestMapping(value = "/getProjectTasksReportsListByProjectId", method = RequestMethod.POST)
	public TasksReportDTO getProjectTasksReportsListByProjectId(
			@RequestParam(value = "projectId", required = false) Long projectId,
			@RequestParam(value = "reportType", required = false) String reportType,
			@RequestParam(value = "startDate", required = false) String customStartDate,
			@RequestParam(value = "endDate", required = false) String customEndDate) throws Exception {

		Projects project = projectsService.getProjectById(projectId);

		Date[] searchDates = null;
		if (!"".equalsIgnoreCase(customStartDate) && !"".equalsIgnoreCase(customEndDate)) {
			searchDates = reportsUtil.customeDates(project.getOrgLocationsId(), customStartDate, customEndDate);
		} else if (reportType.equalsIgnoreCase("upToDate")) {
			searchDates = reportsUtil.projectUpToDates(project.getOrgLocationsId(), project.getStartDate(), new Date());
		} else {
			searchDates = reportsUtil.getProjectFilterDate(reportType, project.getOrgLocationsId());
		}

		Collection<ProjectTasks> taskList = tasksService.getAllTasksByProjectIdAndFilter(projectId, searchDates[0],
				searchDates[1]);
		int newCount = 0;
		int inProgressCount = 0;
		int doneCount = 0;
		int onHoldCount = 0;

		TasksReportDTO report = new TasksReportDTO();
		TaskPriorityReportDTO newTasksCounts = new TaskPriorityReportDTO();
		TaskPriorityReportDTO inProgressTasksCounts = new TaskPriorityReportDTO();
		TaskPriorityReportDTO onHoldTasksCounts = new TaskPriorityReportDTO();
		TaskPriorityReportDTO doneTasksCounts = new TaskPriorityReportDTO();

		report.setNewCounts(newTasksCounts);
		report.setInprogressCounts(inProgressTasksCounts);
		report.setOnHoldCounts(onHoldTasksCounts);
		report.setDoneCounts(doneTasksCounts);
		for (ProjectTasks tasks : taskList) {
			LookUpData status = tasks.getTaskStatus();
			if (status != null) {
				if (status.getName().equalsIgnoreCase(Constants.TASK_STATUS_NEW)) {
					newCount++;
					applyTaskSubCounts(tasks.getPriority(), newTasksCounts);
				} else if (status.getName().equalsIgnoreCase(Constants.TASK_STATUS_IN_PROGRESS)) {
					inProgressCount++;
					applyTaskSubCounts(tasks.getPriority(), inProgressTasksCounts);
				} else if (status.getName().equalsIgnoreCase(Constants.TASK_STATUS_DONE)) {
					doneCount++;
					applyTaskSubCounts(tasks.getPriority(), doneTasksCounts);
				} else if (status.getName().equalsIgnoreCase(Constants.TASK_STATUS_ON_HOLD)) {
					onHoldCount++;
					applyTaskSubCounts(tasks.getPriority(), onHoldTasksCounts);
				}
			}

		}
		report.setNewCount(newCount);
		report.setDoneCount(doneCount);
		report.setInProgressCount(inProgressCount);
		report.setOnHoldCount(onHoldCount);
		return report;
	}

	private Date getPreviousDate(DateTime zoned, DateTimeFormatter outputFormatter, DateFormat TZ_DF_TS,
			Timezone timezone, int numberOfDays) throws ParseException {
		DateTime yesterDayZoned = zoned.minusDays(numberOfDays);
		String yesterDayDate = outputFormatter.print(yesterDayZoned);
		String startDateString = CommonUtil.convertTimeZones(timezone.getTimeZoneId(), Constants.UTC_TIMEZONE,
				yesterDayDate);
		return TZ_DF_TS.parse(startDateString);
	}

	private void applyTaskSubCounts(LookUpData priority, TaskPriorityReportDTO subReport) {
		if (priority != null) {
			String name = priority.getName();
			if (name.equalsIgnoreCase(Constants.TASK_PRIORITY_P1)) {
				subReport.setP1Count(subReport.getP1Count() + 1);
			} else if (name.equalsIgnoreCase(Constants.TASK_PRIORITY_P2)) {
				subReport.setP2Count(subReport.getP2Count() + 1);
			} else if (name.equalsIgnoreCase(Constants.TASK_PRIORITY_P3)) {
				subReport.setP3Count(subReport.getP3Count() + 1);
			} else if (name.equalsIgnoreCase(Constants.TASK_PRIORITY_P4)) {
				subReport.setP4Count(subReport.getP4Count() + 1);
			} else {
				subReport.setOtherCount(subReport.getOtherCount() + 1);
			}
		} else {
			subReport.setOtherCount(subReport.getOtherCount() + 1);
		}
	}

	@RequestMapping(value = "/getOverdueTaskListByProjectId", method = RequestMethod.POST)
	public Collection<ProjectTasks> getOverdueTaskListByProjectId(
			@RequestParam(value = "projectId", required = false) Long projectId,
			@RequestParam(value = "reportType", required = false) String reportType,
			@RequestParam(value = "startDate", required = false) String customStartDate,
			@RequestParam(value = "endDate", required = false) String customEndDate) throws Exception {

		Projects project = projectsService.getProjectById(projectId);
		Date[] searchDates = null;
		if (!"".equalsIgnoreCase(customStartDate) && !"".equalsIgnoreCase(customEndDate)) {
			searchDates = reportsUtil.customeDates(project.getOrgLocationsId(), customStartDate, customEndDate);
		} else if (reportType.equalsIgnoreCase("upToDate")) {
			searchDates = reportsUtil.projectUpToDates(project.getOrgLocationsId(), project.getStartDate(), new Date());
		} else {
			searchDates = reportsUtil.getProjectFilterDate(reportType, project.getOrgLocationsId());
		}

		Collection<ProjectTasks> overDueTaskList = tasksService.getOverdueTaskListByProjectId(projectId, searchDates[0],
				searchDates[1]);
		for (ProjectTasks tasks : overDueTaskList) {

			java.util.Set<ProjectAssignTask> patList = tasks.getProjectAssignTask();
			for (ProjectAssignTask pat : patList) {
				pat.getProjectMemberId().getCandidateName();
			}
			tasks.setProjectAssignTask(patList);
			if (tasks.getTaskType() != null) {
				tasks.setTaskTypes(tasks.getTaskType().getName());
				tasks.setTaskStaus(tasks.getTaskStatus().getName());
				User user = userService.getUserByUserCode(tasks.getCreatedBy());
				tasks.setProjectTasksUUID(tasks.getProjectTasksUUID());
				if (user != null) {
					if (user.getFileUrl() != null) {
						tasks.setUserFileUrl(user.getFileUrl());
					}
					tasks.setUserName(user.getFullName());
				}

			}
			long diff = commonUtil.currentDate().getTime() - tasks.getDueDate().getTime();
			long diffSeconds = diff / 1000 % 60;
			long diffMinutes = diff / (60 * 1000) % 60;
			long diffHours = diff / (60 * 60 * 1000) % 24;
			long diffDays = diff / (24 * 60 * 60 * 1000);
			StringBuilder timeBuilder = new StringBuilder();
			if (diffDays == 0 && diffHours == 0 && diffMinutes == 0) {
				tasks.setActionTimeStr(timeBuilder.append(diffSeconds).append("seconds ago").toString());
			} else if (diffDays == 0 && diffHours == 0) {
				tasks.setActionTimeStr(timeBuilder.append(diffMinutes).append(" minutes ago").toString());
			} else if (diffDays == 0) {
				tasks.setActionTimeStr(timeBuilder.append(diffHours).append(" hours ").append(diffMinutes)
						.append(" minutes ago").toString());
			} else {
				tasks.setActionTimeStr(timeBuilder.append(diffDays).append(" days ").append(diffHours)
						.append(" hours ago").toString());
			}

			List<String> usersList = new ArrayList<>();
			List<ProjectAssignTask> assignUser = projectAssignTasksService
					.getProjectAssignTaskListByProjectTaskId(tasks.getId());
			if (assignUser != null && !assignUser.isEmpty() && assignUser.size() > 1) {
				for (ProjectAssignTask assignedTask : assignUser) {
					if (assignedTask.getProjectMemberId() != null
							&& assignedTask.getProjectMemberId().getEmployee() != null
							&& assignedTask.getProjectMemberId().getEmployee().getUser() != null) {
						usersList.add(assignedTask.getProjectMemberId().getEmployee().getUser().getFullName());
					}
				}
			} else if (assignUser != null && assignUser.size() == 1) {
				for (ProjectAssignTask assignedTask : assignUser) {
					if (assignedTask.getProjectMemberId() != null
							&& assignedTask.getProjectMemberId().getEmployee() != null
							&& assignedTask.getProjectMemberId().getEmployee().getUser() != null) {
						tasks.setUserFileUrl(assignedTask.getProjectMemberId().getEmployee().getUser().getFileUrl());
						tasks.setUserName(assignedTask.getProjectMemberId().getEmployee().getUser().getFullName());
					}
				}
			}
			tasks.setTaskAssignedUsers(usersList);
		}
		return overDueTaskList;
	}

	@RequestMapping(value = "/getTasksProgressByProjectId", method = RequestMethod.POST)
	public TasksPercentageReportDTO getTasksProgressByProjectId(
			@RequestParam(value = "projectId", required = false) Long projectId,
			@RequestParam(value = "reportType", required = false) String reportType,
			@RequestParam(value = "startDate", required = false) String customStartDate,
			@RequestParam(value = "endDate", required = false) String customEndDate) throws Exception {

		Projects project = projectsService.getProjectById(projectId);

		Date[] searchDates = null;
		if (!"".equalsIgnoreCase(customStartDate) && !"".equalsIgnoreCase(customEndDate)) {
			searchDates = reportsUtil.customeDates(project.getOrgLocationsId(), customStartDate, customEndDate);
		} else if (reportType.equalsIgnoreCase("upToDate")) {
			searchDates = reportsUtil.projectUpToDates(project.getOrgLocationsId(), project.getStartDate(), new Date());
		} else {
			searchDates = reportsUtil.getProjectFilterDate(reportType, project.getOrgLocationsId());
		}
		Collection<ProjectTasks> taskList = tasksService.getAllTasksByProjectIdAndFilter(projectId, searchDates[0],
				searchDates[1]);
		int zeroCount = 0;
		int tenCount = 0;
		int twentyCount = 0;
		int thirtyCount = 0;
		int fortyCount = 0;
		int fiftyCount = 0;
		int sixtyCount = 0;
		int seventyCount = 0;
		int eightyCount = 0;
		int nintyCount = 0;
		int hundredCount = 0;
		TasksPercentageReportDTO percentage = new TasksPercentageReportDTO();
		for (ProjectTasks tasks : taskList) {
			if (tasks.getPercentage() != null) {
				if (tasks.getPercentage().equals(Constants.TASK_PERCENTAGE_ZERO)) {
					zeroCount++;
				} else if (tasks.getPercentage().equals(Constants.TASK_PERCENTAGE_TEN)) {
					tenCount++;
				} else if (tasks.getPercentage().equals(Constants.TASK_PERCENTAGE_TWENTY)) {
					twentyCount++;
				} else if (tasks.getPercentage().equals(Constants.TASK_PERCENTAGE_THIRTY)) {
					thirtyCount++;
				} else if (tasks.getPercentage().equals(Constants.TASK_PERCENTAGE_FORTY)) {
					fortyCount++;
				} else if (tasks.getPercentage().equals(Constants.TASK_PERCENTAGE_FIFTY)) {
					fiftyCount++;
				} else if (tasks.getPercentage().equals(Constants.TASK_PERCENTAGE_SIXTY)) {
					sixtyCount++;
				} else if (tasks.getPercentage().equals(Constants.TASK_PERCENTAGE_SEVENTY)) {
					seventyCount++;
				} else if (tasks.getPercentage().equals(Constants.TASK_PERCENTAGE_EIGHTY)) {
					eightyCount++;
				} else if (tasks.getPercentage().equals(Constants.TASK_PERCENTAGE_NINTY)) {
					nintyCount++;
				} else if (tasks.getPercentage().equals(Constants.TASK_PERCENTAGE_HUNDRED)) {
					hundredCount++;
				}
			}
		}

		percentage.setZeroCount(zeroCount);
		percentage.setTenCount(tenCount);
		percentage.setTwentyCount(twentyCount);
		percentage.setThirtyCount(thirtyCount);
		percentage.setFortyCount(fortyCount);
		percentage.setFiftyCount(fiftyCount);
		percentage.setSixtyCount(sixtyCount);
		percentage.setSeventyCount(seventyCount);
		percentage.setEightyCount(eightyCount);
		percentage.setNintyCount(nintyCount);
		percentage.setHundredCount(hundredCount);
		return percentage;
	}

	@RequestMapping(value = "/getProjectTasksAStackedReportsListByProjectId", method = RequestMethod.POST)
	public List<TasksStackedReportDTO> getProjectTasksAStackedReportsListByProjectId(
			@RequestParam(value = "projectId", required = false) Long projectId,
			@RequestParam(value = "reportType", required = false) String reportType,
			@RequestParam(value = "startDate", required = false) String customStartDate,
			@RequestParam(value = "endDate", required = false) String customEndDate) throws Exception {

		Projects project = projectsService.getProjectById(projectId);

		Date[] searchDates = null;
		if (!"".equalsIgnoreCase(customStartDate) && !"".equalsIgnoreCase(customEndDate)) {
			searchDates = reportsUtil.customeDates(project.getOrgLocationsId(), customStartDate, customEndDate);
		} else if (reportType.equalsIgnoreCase("upToDate")) {
			searchDates = reportsUtil.projectUpToDates(project.getOrgLocationsId(), project.getStartDate(), new Date());
		} else {
			searchDates = reportsUtil.getProjectFilterDate(reportType, project.getOrgLocationsId());
		}

		Collection<ProjectTasks> taskList = tasksService.getAllTasksByProjectIdAndFilter(projectId, searchDates[0],
				searchDates[1]);

		Map<String, TasksStackedReportDTO> TasksStackedReportDTOList = new HashMap<>();
		for (ProjectTasks task : taskList) {
			List<ProjectTaskActivity> list1 = projectTaskActivityService
					.getProjectTaskActivityByProjectTaskId(task.getId());
			if (list1 == null || list1.isEmpty()) {
				applyStackedReportData(task.getTaskStatus(), task.getPriority(), TasksStackedReportDTOList,
						task.getCreatedDate());
			} else {
				for (ProjectTaskActivity activity : list1) {
					if (activity.getActivity().equals(Constants.TASK_ACTIVITY_STATUS)
							|| activity.getActivity().equals(Constants.TASK_ACTIVITY_PRIORITY)) {
						applyStackedReportData(task.getTaskStatus(), task.getPriority(), TasksStackedReportDTOList,
								activity.getCreatedDate());
					}
				}
			}
		}
		List<TasksStackedReportDTO> list = new LinkedList<>();

		for (TasksStackedReportDTO dto : TasksStackedReportDTOList.values()) {
			list.add(dto);
		}

		list.sort(new Comparator<TasksStackedReportDTO>() {

			@Override
			public int compare(TasksStackedReportDTO o1, TasksStackedReportDTO o2) {
				return o1.getDate1() < o2.getDate1() ? -1 : 1;
			}
		});
		return list;

	}

	private void applyStackedReportData(LookUpData status, LookUpData priority,
			Map<String, TasksStackedReportDTO> tasksStackedReportDTOList, Date date) {
		TasksStackedReportDTO tasksStackedReportDTO = null;
		String date1 = Constants.STDF.format(date);
		if (tasksStackedReportDTOList.containsKey(date1)) {
			tasksStackedReportDTO = tasksStackedReportDTOList.get(date1);
		} else {
			tasksStackedReportDTO = new TasksStackedReportDTO();
			tasksStackedReportDTO.setDoneCounts(new TaskPriorityReportDTO());
			tasksStackedReportDTO.setInprogressCounts(new TaskPriorityReportDTO());
			tasksStackedReportDTO.setNewCounts(new TaskPriorityReportDTO());
			tasksStackedReportDTO.setOnHoldCounts(new TaskPriorityReportDTO());
		}
		tasksStackedReportDTO.setDate(date1);
		tasksStackedReportDTO.setDate1(date.getTime());
		if (status != null) {
			if (status.getName().equalsIgnoreCase(Constants.TASK_STATUS_NEW)) {
				tasksStackedReportDTO.setNewCount(tasksStackedReportDTO.getNewCount() + 1);
				applyTaskSubCounts(priority, tasksStackedReportDTO.getNewCounts());
			} else if (status.getName().equalsIgnoreCase(Constants.TASK_STATUS_IN_PROGRESS)) {
				tasksStackedReportDTO.setInProgressCount(tasksStackedReportDTO.getInProgressCount() + 1);
				applyTaskSubCounts(priority, tasksStackedReportDTO.getInprogressCounts());
			} else if (status.getName().equalsIgnoreCase(Constants.TASK_STATUS_DONE)) {
				tasksStackedReportDTO.setDoneCount(tasksStackedReportDTO.getDoneCount() + 1);
				applyTaskSubCounts(priority, tasksStackedReportDTO.getDoneCounts());
			} else if (status.getName().equalsIgnoreCase(Constants.TASK_STATUS_ON_HOLD)) {
				tasksStackedReportDTO.setOnHoldCount(tasksStackedReportDTO.getOnHoldCount() + 1);
				applyTaskSubCounts(priority, tasksStackedReportDTO.getOnHoldCounts());
			}
		}
		tasksStackedReportDTOList.put(date1, tasksStackedReportDTO);
	}

	@RequestMapping(value = "/geBudgetStatusReport", method = RequestMethod.POST)
	public BudgetReportDTO geBudgetStatusReport(@RequestParam(value = "projectId", required = false) Long projectId,
			@RequestParam(value = "reportType", required = false) String reportType,
			@RequestParam(value = "startDate", required = false) String customStartDate,
			@RequestParam(value = "endDate", required = false) String customEndDate, HttpServletRequest request)
			throws Exception {

		OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,
				"orgLocations");
		Projects project = projectsService.getProjectById(projectId);

		Date[] searchDates = null;
		if (!"".equalsIgnoreCase(customStartDate) && !"".equalsIgnoreCase(customEndDate)) {
			searchDates = reportsUtil.customeDates(loginOrgLocation, customStartDate, customEndDate);
		} else if (reportType.equalsIgnoreCase("upToDate")) {
			searchDates = reportsUtil.projectUpToDates(loginOrgLocation, project.getStartDate(), new Date());
		} else {
			searchDates = reportsUtil.getProjectFilterDate(reportType, loginOrgLocation);
		}
		BudgetReportDTO report = new BudgetReportDTO();
		// project budget
		List<ProjectBudget> budgetList = projectBudgetService
				.getProjectBudgetByProjectTaskIdAndApprovedStatus(projectId, loginOrgLocation.getId());
		double budgetSum = budgetList.stream().filter(p -> p.getEstimatedBudgetValue() != null)
				.mapToDouble(p -> p.getEstimatedBudgetValue()).sum();
		double estimatehoursSum = budgetList.stream().filter(p -> p.getEstimatedHours() != null)
				.mapToDouble(p -> p.getEstimatedHours()).sum();
		if (budgetList.size() >= 1) {
			String currency = budgetList.get(0).getEstimatedBudgetCurrency();
			report.setCurrency(currency);
		}
		report.setBudgetTotal((float) budgetSum);
		report.setEstimatedHours((float) estimatehoursSum);

		// billing amount
		List<Invoice> invoiceList = invoiceService
				.getAllBillsAndInvoicesBYProjectIdOrgLocationIdInvoiceStatusStartDateAndEndDate(projectId,
						loginOrgLocation.getId(), "Accepted", searchDates[0], searchDates[1]);
		double actualBudgetSum = invoiceList.stream().filter(p -> p.getTotal() != null)
				.mapToDouble(p -> Double.parseDouble(p.getTotal())).sum();
		report.setActualBudget((float) actualBudgetSum);
		if (invoiceList.size() > 1) {
			String invoiceCurrency = invoiceList.get(0).getCurrencyCode();
			report.setInvoiceCurrency(invoiceCurrency);
		}
		if (report.getCurrency() != null && report.getInvoiceCurrency() != null) {
			if (report.getCurrency().equalsIgnoreCase(report.getInvoiceCurrency())) {
				report.setActualBudget((float) actualBudgetSum);
			} else {
				report.setActualBudget(conversionUtil.convert(report.getCurrency(), report.getInvoiceCurrency(),
						(float) actualBudgetSum));
			}
		}

		Map<String, SubmissionApproverHierarchy> approverList = submissionApproverHierarchyDao
				.getTotalHoursByProjectIdAndLocationIdApprovalStatusStartDateEndDate(loginOrgLocation.getId(),
						projectId, searchDates[0], searchDates[1]);
		if (approverList != null && !approverList.isEmpty()) {
			for (Map.Entry<String, SubmissionApproverHierarchy> entry : approverList.entrySet()) {
				if (entry.getKey() == "APPROVED") {
					report.setActualHours(entry.getValue().getTotalHours());
				} else if (!approverList.containsKey("APPROVED")) {
					report.setActualHours(0f);
				}
			}
		} else {
			report.setActualHours(0f);
		}
		return report;
	}

	@RequestMapping(value = "/getprojectTaskMembersByProjectId", method = RequestMethod.POST)
	public ModelAndView getprojectTaskMembersByProjectIdAndLoginUserId(HttpServletRequest request,
			@RequestParam(value = "projectId") String projectId, @RequestParam(value = "taskUUID") String taskUUID)
			throws NumberFormatException, Exception {
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,
				"orgLocations");
		ModelAndView model = new ModelAndView();
		ProjectMembers projectmember = projectMembersService.getProjectMemberByUserIdAndProjectId(user.getId(),
				Long.parseLong(projectId));
		ProjectMembersHistory projectMemberHistory = null;
		if (projectmember != null && projectmember.getId() != null) {
			projectMemberHistory = projectMembersHistoryService
					.getProjectMembersOpenHistoryByProjectMemberId(projectmember.getId());
			if (projectMemberHistory != null) {
				String startDate = commonUtil.dateStrFromOrgDateFormatter(loginOrgLocation.getDateFormat(),
						projectMemberHistory.getProjectAssignedDate());
				String endDate = commonUtil.dateStrFromOrgDateFormatter(loginOrgLocation.getDateFormat(),
						projectMemberHistory.getProjectTerminationDate());
				model.addObject("startDate", startDate);
				model.addObject("endDate", endDate);
			} else {
				projectMemberHistory = projectMembersHistoryService
						.getProjectMembersRecentClosedByProjectMemberId(projectmember.getId());
				if (projectMemberHistory != null) {
					String startDate = commonUtil.dateStrFromOrgDateFormatter(loginOrgLocation.getDateFormat(),
							projectMemberHistory.getProjectAssignedDate());
					String endDate = commonUtil.dateStrFromOrgDateFormatter(loginOrgLocation.getDateFormat(),
							projectMemberHistory.getProjectTerminationDate());
					model.addObject("startDate", startDate);
					model.addObject("endDate", endDate);
				}
			}
		}
		model.addObject("projectmember", projectmember);
		model.addObject("projectId", projectId);
		model.addObject("taskUUID", taskUUID);
		model.setViewName("project/projectTaskEntries");
		return model;
	}

	@RequestMapping(value = "/getSavedTaskDetails", method = RequestMethod.POST)
	public ModelAndView getSavedTaskDetails(HttpServletRequest request,
			@RequestParam(value = "taskUUID") String taskUUID, TimeSheetDayEntriesDTO timeSheetDayEntriesDto)
			throws Exception {
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,
				"orgLocations");
		ModelAndView model = new ModelAndView();
		Long projectMemberId = null;
		List<TimeSheetDayEntriesDTOList> timeSheetDayEntriesDtoListData = new ArrayList<>();

		int start = timeSheetDayEntriesDto.getStart();
		int length = 0;
		if (timeSheetDayEntriesDto.getPageNo() < 1) {
			timeSheetDayEntriesDto.setPageNo(1);
		}
		if (timeSheetDayEntriesDto.getPageSize() < 1) {
			length = 5;
			timeSheetDayEntriesDto.setPageSize(5);
		} else {
			length = timeSheetDayEntriesDto.getPageSize();
		}

		ResponseList<TimeSheetDayEntries> timeEntryDayList = timeSheetDayEntriesService.getTimesheetDayEntriesByTaskUUIDAndUserId(taskUUID, user.getId(), loginOrgLocation.getId(), start,
						length, timeSheetDayEntriesDto.getSearchValue());
		for (TimeSheetDayEntries timeDayEntries : timeEntryDayList.getData()) {
			TimeSheetDayEntriesDTOList timeSheetDayEntriesDtoList = new TimeSheetDayEntriesDTOList();
			if (timeDayEntries.getTimesheetSubmissions() != null
					&& timeDayEntries.getTimesheetSubmissions().getProjectMembers() != null && timeDayEntries
							.getTimesheetSubmissions().getProjectMembers().getProjectMemberCandidteId() != null) {
				timeSheetDayEntriesDtoList.setEmployeeName(timeDayEntries.getTimesheetSubmissions().getProjectMembers()
						.getProjectMemberCandidteId().getFullName());
				SubmissionApproverHierarchy subStatus = submissionApproverHierarchyService
						.getSubmissionApproverHierarchyForEmployeeBySubmissionId(
								timeDayEntries.getTimesheetSubmissions().getId(),
								timeDayEntries.getTimesheetSubmissions().getProjectMembers()
										.getProjectMemberCandidteId().getMasterCandidateId().getId());
				timeSheetDayEntriesDtoList
						.setApprovalStatus(subStatus.getSubmissionApprovalStatus().getValue().toUpperCase());
			}
			timeSheetDayEntriesDtoList.setTimesheetDate(commonUtil
					.dateStrFromOrgDateFormatter(loginOrgLocation.getDateFormat(), timeDayEntries.getTimesheetDate()));
			timeSheetDayEntriesDtoList.setHours(timeDayEntries.getTotalHours());
			timeSheetDayEntriesDtoList.setComments(timeDayEntries.getComments());
			timeSheetDayEntriesDtoList.setProjectMemberId(timeDayEntries.getTimesheetSubmissions().getProjectMembers().getId());
			timeSheetDayEntriesDtoList.setTimesheetEntryId(timeDayEntries.getId());
			timeSheetDayEntriesDtoListData.add(timeSheetDayEntriesDtoList);
		}

		timeSheetDayEntriesDto.setTotalRows(timeEntryDayList.getRecordsTotal());
		int totalRows = timeEntryDayList.getRecordsTotal();
		if (totalRows > 0) {
			int remainder = totalRows % length;
			int pageCount = totalRows / length;
			if (remainder > 0) {
				timeSheetDayEntriesDto.setPageCount(pageCount + 1);
			} else {
				timeSheetDayEntriesDto.setPageCount(pageCount);
			}
		}

		model.addObject("timeEntryDayListData", timeSheetDayEntriesDtoListData);
		model.addObject("totalRows", timeSheetDayEntriesDto.getTotalRows());
		model.addObject("pageCount", timeSheetDayEntriesDto.getPageCount());
		model.addObject("pageSize", timeSheetDayEntriesDto.getPageSize());
		model.addObject("searchInputValue", timeSheetDayEntriesDto.getSearchValue());
		model.setViewName("project/projectTaskTableData");
		return model;

	}

	@RequestMapping(value = "/getTimesheetTotatalHoursByProjectId", method = RequestMethod.POST)
	public TimesheetReportDTO getTimesheetTotatalHoursByProjectId(
			@RequestParam(value = "projectId", required = false) Long projectId,
			@RequestParam(value = "reportType", required = false) String reportType,
			@RequestParam(value = "startDate", required = false) String customStartDate,
			@RequestParam(value = "endDate", required = false) String customEndDate, HttpServletRequest request)
			throws Exception {

		Projects project = projectsService.getProjectById(projectId);
		OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,
				"orgLocations");

		Date[] searchDates = null;
		if (!"".equalsIgnoreCase(customStartDate) && !"".equalsIgnoreCase(customEndDate)) {
			searchDates = reportsUtil.customeDates(loginOrgLocation, customStartDate, customEndDate);
		} else if (reportType.equalsIgnoreCase("upToDate")) {
			searchDates = reportsUtil.projectUpToDates(loginOrgLocation, project.getStartDate(), new Date());
		} else {
			searchDates = reportsUtil.getProjectFilterDate(reportType, loginOrgLocation);
		}

		TimesheetReportDTO timesheetreport = new TimesheetReportDTO();
		Map<String, SubmissionApproverHierarchy> approverList = submissionApproverHierarchyDao
				.getTotalHoursByProjectIdAndLocationIdApprovalStatusStartDateEndDate(loginOrgLocation.getId(),
						projectId, searchDates[0], searchDates[1]);
		if (approverList != null && !approverList.isEmpty()) {
			for (Map.Entry<String, SubmissionApproverHierarchy> entry : approverList.entrySet()) {

				if (entry.getKey() == "SUBMITTEDFORAPPROVAL") {
					timesheetreport.setSubmittedHours(entry.getValue().getTotalHours());
					timesheetreport.setSubmittedBillableHours(entry.getValue().getBillableHours());
					timesheetreport.setSubmittedNonBillableHours(entry.getValue().getNonBillableHours());
				} else if (!approverList.containsKey("SUBMITTEDFORAPPROVAL")) {
					timesheetreport.setSubmittedHours(0f);
					timesheetreport.setSubmittedBillableHours(0f);
					timesheetreport.setSubmittedNonBillableHours(0f);
				}
				if (entry.getKey() == "APPROVED") {
					timesheetreport.setApprovedHours(entry.getValue().getTotalHours());
					timesheetreport.setApprovedBillableHours(entry.getValue().getBillableHours());
					timesheetreport.setApprovedNonBillableHours(entry.getValue().getNonBillableHours());
				} else if (!approverList.containsKey("APPROVED")) {
					timesheetreport.setApprovedHours(0f);
					timesheetreport.setApprovedBillableHours(0f);
					timesheetreport.setApprovedNonBillableHours(0f);
				}

				if (entry.getKey() == "REJECTED") {
					timesheetreport.setRejectedHours(entry.getValue().getTotalHours());
					timesheetreport.setRejectedBillableHours(entry.getValue().getBillableHours());
					timesheetreport.setRejectedNonBillableHours(entry.getValue().getNonBillableHours());
				} else if (!approverList.containsKey("REJECTED")) {
					timesheetreport.setRejectedHours(0f);
					timesheetreport.setRejectedBillableHours(0f);
					timesheetreport.setRejectedNonBillableHours(0f);
				}

				if (entry.getKey() == "NEW") {
					timesheetreport.setSavedHours(entry.getValue().getTotalHours());
					timesheetreport.setSavedBillableHours(entry.getValue().getBillableHours());
					timesheetreport.setSavedNonBillableHours(entry.getValue().getNonBillableHours());
				} else if (!approverList.containsKey("NEW")) {
					timesheetreport.setSavedHours(0f);
					timesheetreport.setSavedBillableHours(0f);
					timesheetreport.setSavedNonBillableHours(0f);
				}
			}
		} else {
			timesheetreport.setSubmittedHours(0f);
			timesheetreport.setSubmittedBillableHours(0f);
			timesheetreport.setSubmittedNonBillableHours(0f);

			timesheetreport.setApprovedHours(0f);
			timesheetreport.setApprovedBillableHours(0f);
			timesheetreport.setApprovedNonBillableHours(0f);

			timesheetreport.setRejectedHours(0f);
			timesheetreport.setRejectedBillableHours(0f);
			timesheetreport.setRejectedNonBillableHours(0f);

			timesheetreport.setSavedHours(0f);
			timesheetreport.setSavedBillableHours(0f);
			timesheetreport.setSavedNonBillableHours(0f);
		}
		return timesheetreport;
	}

	@RequestMapping(value = "/getExpensesByProjectId", method = RequestMethod.POST)
	public TimesheetReportDTO getExpensesByProjectId(
			@RequestParam(value = "projectId", required = false) Long projectId,
			@RequestParam(value = "reportType", required = false) String reportType,
			@RequestParam(value = "startDate", required = false) String customStartDate,
			@RequestParam(value = "endDate", required = false) String customEndDate, HttpServletRequest request)
			throws Exception {

		OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,
				"orgLocations");
		Projects project = projectsService.getProjectById(projectId);

		Date[] searchDates = null;
		if (!"".equalsIgnoreCase(customStartDate) && !"".equalsIgnoreCase(customEndDate)) {
			searchDates = reportsUtil.customeDates(loginOrgLocation, customStartDate, customEndDate);
		} else if (reportType.equalsIgnoreCase("upToDate")) {
			searchDates = reportsUtil.projectUpToDates(loginOrgLocation, project.getStartDate(), new Date());
		} else {
			searchDates = reportsUtil.getProjectFilterDate(reportType, loginOrgLocation);
		}

		TimesheetReportDTO timesheetreport = new TimesheetReportDTO();
		List<Expenses> expensesList = expensesService.getAllExpensesByProjectIdOrgLocationIdStartDateAndEndDate(
				projectId, loginOrgLocation.getId(), searchDates[0], searchDates[1]);
		if (expensesList != null && !expensesList.isEmpty()) {
			for (Expenses ex : expensesList) {
				if (ex.getExpenseStatus().toString().equalsIgnoreCase("APPROVED")) {
					timesheetreport.setApprovedAmount(ex.getTotalAmount().floatValue());
				} else if (ex.getExpenseStatus().toString().equalsIgnoreCase("SUBMITTED")) {
					timesheetreport.setSubmittedAmount(ex.getTotalAmount().floatValue());
				} else if (ex.getExpenseStatus().toString().equalsIgnoreCase("REJECTED")) {
					timesheetreport.setRejectedAmount(ex.getTotalAmount().floatValue());
				}
			}
		} else {
			timesheetreport.setApprovedAmount(0f);
			timesheetreport.setSubmittedAmount(0f);
			timesheetreport.setRejectedAmount(0f);

		}

		return timesheetreport;

	}

	@RequestMapping(value = "/calculateTotalTaskHoursForADay", method = RequestMethod.POST)
	public String calculateTotalTaskHoursForADay(@RequestParam(value = "timeDate", required = false) String timeDate,
			@RequestParam(value = "projectMember", required = false) Long projectMember,
			@RequestParam(value = "timesheetHour", required = false) String timesheetHour,
			@RequestParam(value = "editTaskTime", required = false) String editTaskTime,
			HttpServletRequest request)
			throws Exception {
		String falg = "false";
		OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,
				"orgLocations");
		ProjectMembers proMember = projectMembersService.getProjectMemberById(projectMember);
		if (timeDate != null && StringUtils.isNotBlank(timeDate)) {
			if (proMember != null && proMember.getProjects() != null && proMember.getMasterCandidateId().getId() != null) {
				Float totalHours = timeSheetDayEntriesService.getTotalHoursByProjectIdMasteCandidateIdandTimesheetDate(
				proMember.getProjects().getId(), proMember.getMasterCandidateId().getId(),commonUtil.orgLocationDateFormat(loginOrgLocation.getDateFormat(), timeDate));
				if(editTaskTime != null && StringUtils.isNotEmpty(editTaskTime) && editTaskTime.equalsIgnoreCase("edit")) {
					totalHours = CommonUtil.getCombinedHours(Float.parseFloat(timesheetHour.replaceAll(":", ".")),0f);
				}else {
					totalHours = CommonUtil.getCombinedHours(Float.parseFloat(timesheetHour.replaceAll(":", ".")),totalHours);
				}
				
				
				if (totalHours != null && totalHours >= 24) {
					falg = "false";
				} else {
					falg = "true";
				}
			}
		} else {
			falg = "noDate";
		}
		return falg;
	}

	@RequestMapping(value = "/editProjectTaskTimesheetDayEntry", method = RequestMethod.POST)
	public TimeSheetDayEntries editProjectTaskTimesheetDayEntry(
			@RequestParam(value = "timeDate", required = false) String timeDate,
			@RequestParam(value = "projectMember", required = false) Long projectMember,
			@RequestParam(value = "taskUUID", required = false) String taskUUID, HttpServletRequest request)
			throws Exception {
		OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,
				"orgLocations");
		TimeSheetDayEntries totalHours = null;
		ProjectMembers proMember = projectMembersService.getProjectMemberById(projectMember);
		ProjectTasks projectTask = projectTasksService.getProjectTasksByUUID(taskUUID);
		if (timeDate != null && StringUtils.isNotBlank(timeDate)) {
			if (proMember != null && proMember.getProjects() != null
					&& proMember.getMasterCandidateId().getId() != null) {
				totalHours = timeSheetDayEntriesService.getTimesheetDayEntryByProjectIdMasteCandidateIdandTimesheetDate(
						proMember.getProjects().getId(), proMember.getMasterCandidateId().getId(),
						commonUtil.orgLocationDateFormat(loginOrgLocation.getDateFormat(), timeDate),
						projectTask.getId());
			}
		}
		return totalHours;
	}

	@RequestMapping(value = "/deleteProjectTaskTimesheetDayEntry", method = RequestMethod.POST)
	public Integer deleteProjectTaskTimesheetDayEntry
	          (@RequestParam(value = "timesheetEntryId", required = false) String timesheetEntryId, HttpServletRequest request) throws NumberFormatException, Exception{
			  TimeSheetDayEntries timesheetDayEntries = timeSheetDayEntriesService.getTimesheetDayEntriesById(Long.parseLong(timesheetEntryId));
			  timesheetDayEntries.setStatus(0);
			  timesheetDayEntries = timeSheetDayEntriesService.saveTimesheetDayEntries(timesheetDayEntries);
			  return timesheetDayEntries.getStatus();
		
	}
	
	@RequestMapping(value = "/changeTaskStatusByList", method = RequestMethod.POST)
	public @ResponseBody void changeTaskStatusByList(TaskDTO taskDTO, HttpServletRequest request) throws Exception {
		OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
		Long candidateId = (Long) SourceLeadSessionManager.getSessionAttribute(request,"candidateId");
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request,"user");
		taskDTO.setUserId(user.getId());
		taskDTO.setCandidateId(candidateId);
		tasksService.updateTaskStatusList(taskDTO,loginOrgLocation);
		 
	}
	
}
