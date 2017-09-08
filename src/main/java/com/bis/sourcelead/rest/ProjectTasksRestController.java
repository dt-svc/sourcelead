package com.bis.sourcelead.rest;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.bis.sourcelead.annotations.Validator;
import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dto.ProjectTasksDTO;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.LookUpData;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.ProjectTaskActivity;
import com.bis.sourcelead.entity.ProjectTaskComments;
import com.bis.sourcelead.entity.ProjectTaskDocuments;
import com.bis.sourcelead.entity.ProjectTasks;
import com.bis.sourcelead.entity.Timezone;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.LookUpDataService;
import com.bis.sourcelead.service.ProjectAssignTasksService;
import com.bis.sourcelead.service.ProjectTaskActivityService;
import com.bis.sourcelead.service.ProjectTaskCommentsService;
import com.bis.sourcelead.service.ProjectTaskDocumentsService;
import com.bis.sourcelead.service.ProjectTasksService;
import com.bis.sourcelead.service.TimeZoneService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.FileUploadUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;
import com.ibm.icu.text.DateFormat;
import com.ibm.icu.text.SimpleDateFormat;
@RestController 
@RequestMapping("/ProjectTasks")
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
	
	/**
	 * @author David sammeta
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/saveOrUpdate", method = RequestMethod.GET)
	public @ResponseBody  ProjectTasks addTasks() throws Exception {
		ProjectTasks  tasks  = new ProjectTasks();
		return tasksService.addTasks(tasks);
	}
	
	
	@RequestMapping(value = "/update", method = RequestMethod.GET)
	public @ResponseBody  ProjectTasks updateTasks() throws Exception {
		ProjectTasks  tasks  = new ProjectTasks();
		return tasksService.updateTasks(tasks);
	}
	
	@RequestMapping(value = "/getTasks/{id}", method = RequestMethod.GET)
	public @ResponseBody  ProjectTasks getTasksId(@PathVariable String id) throws Exception {
		ProjectTasks tasks = tasksService.getTasksId(id);
		return tasks; 
	}

	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
	public @ResponseBody  Boolean deleteTask(@PathVariable String id) throws Exception {
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
	  * @param json
	  * @param projectId
	  * @return JSON
	  * @throws Exception
	  */
	 @PreAuthorize("hasAnyRole('ROLE_CREATE_PROJECT_TASKS')")
	 @RequestMapping(value = "/saveProjectTasks" , method = RequestMethod.POST)
	 public @ResponseBody  String saveProjectTasks(ProjectTasksDTO projectTasksDTO,  @RequestParam(value="projectId", required=false) Long projectId,@RequestParam(value="userId", required=false) Long userId) throws Exception {
		 if(Validator.validate(projectTasksDTO)) {
			 projectTasksDTO.setStaticPath(staticPath);
			// FileUploadUtil.fillUploadedFileInfo(projectTasksDTO, projectTasksDTO.getFileInfo());
			 String  saveProjectTasks = tasksService.saveProjectTasksDTOByProjectId(projectId, projectTasksDTO,userId);
		 }else
			 return "DataMissing";
		 
		 	
			 return "success";
	 }
	 
	 /**]
	  * @author Laxman
	  * @param comment
	  * @param projectTaskId
	  * @param userId
	  * @return success
	  * @throws Exception
	  */
	 
	 @RequestMapping(value = "/saveProjectTaskComment" , method = RequestMethod.POST)
	 public @ResponseBody  String saveProjectTaskComment(@RequestParam(value="comment", required=false) String comment, @RequestParam(value="projectTaskId", required=false) String projectTaskId, 
			 @RequestParam(value="userId", required=false) Long userId) throws Exception {
		     User user = userService.getUserById(userId);
		     ProjectTasks tasks = projectTasksService.getTasksId(projectTaskId);
			 projectTaskCommentsService.storeprojectComments(comment,tasks,user);
			 return "success";
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
	 @RequestMapping(value = "/saveProjectTaskDocuments" , method = RequestMethod.POST)
	 public @ResponseBody  String saveProjectTaskDocuments(ProjectTasksDTO projectTasksDTO, @RequestParam(value="projectTaskId", required=false) String projectTaskId, 
			 @RequestParam(value="userId", required=false) Long userId, @RequestParam(value="documentId", required=false) Long documentId) throws Exception {
			 if(Validator.validate(projectTasksDTO)) {
				 projectTasksDTO.setStaticPath(staticPath);
					//FileUploadUtil.fillUploadedFileInfo(projectTasksDTO, projectTasksDTO.getFileInfo());
					projectTaskDocumentsService.storeProjectTaskDocuments(projectTasksDTO, projectTaskId,documentId,userId);
				} else
				  return "DataMissing";
			return "success";
			
	 }
	 
	 /**
	  * @author Laxman
	  * @param projectTaskId
	  * @return commnetList
	  * @throws Exception
	  */
	 @RequestMapping(value = "/getTaskCommentList" , method = RequestMethod.POST)
	 public @ResponseBody  List<ProjectTaskComments> getTaskCommentList( @RequestParam(value="projectTaskId", required=false) Long projectTaskId) throws Exception {
		 List<ProjectTaskComments> commnetList=projectTaskCommentsService.getProjectTaskCommentsByProjectTaskId(projectTaskId);
		 for(ProjectTaskComments comments:commnetList) {
			 Timezone timezone = timeZoneService.getTimezone(comments.getProjectTaskId().getProjectId().getOrgLocationsId().getTimezone().getId());
			 comments.setCreatedStr(commonUtil.dateStrFromOrgDateFormatter(comments.getProjectTaskId().getProjectId().getOrgLocationsId().getDateFormat(), comments.getCreatedDate()));
			 if(comments.getCreatedDate() != null) {
			    	DateFormat DF_DMY = new SimpleDateFormat("hh:mma");
					String currentTimeStr = Constants.TZ_DF.format(comments.getCreatedDate());  
					String startTimeUTC= CommonUtil.convertTimeZones(Constants.UTC_TIMEZONE ,timezone.getTimeZoneId(), currentTimeStr);
					DateFormat TZ_DF_TS = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
					Date convertedDate = TZ_DF_TS.parse(startTimeUTC);
					String convertedDateStr = DF_DMY.format(convertedDate);
					comments.setActivityTimezoneStr(convertedDateStr);
			 }
			 
				 long diff = commonUtil.currentDate().getTime() - comments.getCreatedDate().getTime();
				 long diffSeconds = diff / 1000 % 60;
				 long diffMinutes = diff / (60 * 1000) % 60;
				 long diffHours = diff / (60 * 60 * 1000) % 24;
				 long diffDays = diff / (24 * 60 * 60 * 1000);
				 StringBuilder timeBuilder = new StringBuilder();
				 if(diffDays == 0 && diffHours == 0 && diffMinutes == 0){
					 comments.setActionTimeStr(timeBuilder.append(diffSeconds).append("seconds ago").toString());
				 }else if(diffDays == 0 && diffHours == 0){
					 comments.setActionTimeStr(timeBuilder.append(diffMinutes).append(" minutes ago").toString());
				 }else if(diffDays == 0){
					 comments.setActionTimeStr(timeBuilder.append(diffHours).append(" hours ").append(diffMinutes).append(" minutes ago").toString());
				 }else if(diffDays < 7){
					 comments.setActionTimeStr(timeBuilder.append(diffDays).append( " days ").append(diffHours).append(" hours ago").toString());
				 }else{
				 DateFormat standardFormat = new SimpleDateFormat( "MMM dd, yyyy hh:mm:ss aa");
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
	 @RequestMapping(value = "/getProjectTasksDocuments" , method = RequestMethod.POST)
	 public @ResponseBody   List<ProjectTaskDocuments> getProjectTasksDocuments(@RequestParam(value="projectTaskId", required=false) Long projectTaskId) throws Exception {
		 List<ProjectTaskDocuments> taskDocumentList =  projectTaskDocumentsService.getProjectTaskDocumentsListByProjectIdId(projectTaskId);
		 return taskDocumentList;
	 }
	 
	 /**
	  * @author Laxman
	  * @param projectId
	  * @param subTaskId
	  * @return taskList;
	  * @throws Exception
	  */
	 @RequestMapping(value = "/getsubTasksList" , method = RequestMethod.POST)
	 public @ResponseBody   List<ProjectTasks> getsubTasksList(@RequestParam(value="projectId", required=false) Long projectId,@RequestParam(value="subTaskId", required=false) Long subTaskId) throws Exception {
		 List<ProjectTasks> taskList=tasksService.getAllTasksByProjectIdAndParentTaskId(projectId,subTaskId);
		 return taskList;
	 }
	 
	 /**
	  * @author Laxman
	  * @param projectTaskId
	  * @return taskActivityList
	  * @throws Exception
	  */
	 @RequestMapping(value = "/getTaskActivityList" , method = RequestMethod.POST)
	 public @ResponseBody   List<ProjectTaskActivity> getTaskActivityList(@RequestParam(value="projectTaskId", required=false) Long projectTaskId) throws Exception {
		 List<ProjectTaskActivity> taskActivityList=projectTaskActivityService.getProjectTaskActivityByProjectTaskId(projectTaskId);
		
		 for(ProjectTaskActivity activity :taskActivityList ) {
			 Timezone timezone = timeZoneService.getTimezone(activity.getProjecId().getOrgLocationsId().getTimezone().getId());
			 activity.setCreatedStr(commonUtil.dateStrFromOrgDateFormatter(activity.getProjectTaskId().getProjectId().getOrgLocationsId().getDateFormat(), activity.getCreatedDate()));
			 
			 if(activity.getCreatedDate() != null) {
			    	DateFormat DF_DMY = new SimpleDateFormat("hh:mma");
					String currentTimeStr = Constants.TZ_DF.format(activity.getCreatedDate());  
					String startTimeUTC= CommonUtil.convertTimeZones(Constants.UTC_TIMEZONE ,timezone.getTimeZoneId(), currentTimeStr);
					DateFormat TZ_DF_TS = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
					Date convertedDate = TZ_DF_TS.parse(startTimeUTC);
					String convertedDateStr = DF_DMY.format(convertedDate);
					activity.setActivityTimezoneStr(convertedDateStr);

			 }
				 long diff = commonUtil.currentDate().getTime() - activity.getCreatedDate().getTime();
				 long diffSeconds = diff / 1000 % 60;
				 long diffMinutes = diff / (60 * 1000) % 60;
				 long diffHours = diff / (60 * 60 * 1000) % 24;
				 long diffDays = diff / (24 * 60 * 60 * 1000);
				 StringBuilder timeBuilder = new StringBuilder();
				 if(diffDays == 0 && diffHours == 0 && diffMinutes == 0){
				 activity.setActionTimeStr(timeBuilder.append(diffSeconds).append("seconds ago").toString());
				 }else if(diffDays == 0 && diffHours == 0){
				 activity.setActionTimeStr(timeBuilder.append(diffMinutes).append(" minutes ago").toString());
				 }else if(diffDays == 0){
				 activity.setActionTimeStr(timeBuilder.append(diffHours).append(" hours ").append(diffMinutes).append(" minutes ago").toString());
				 }else if(diffDays < 7){
				 activity.setActionTimeStr(timeBuilder.append(diffDays).append( " days ").append(diffHours).append(" hours ago").toString());
				 }else{
				 DateFormat standardFormat = new SimpleDateFormat( "MMM dd, yyyy hh:mm:ss aa");
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
	 @RequestMapping(value = "/getProjectTasksByTaskType" , method = RequestMethod.POST)
	 public @ResponseBody   Collection<ProjectTasks> getProjectTasksByTaskType(@RequestParam(value="projectId", required=false) Long projectId,@RequestParam(value="taskType", required=false) String taskType) throws Exception {
		    LookUpData lookupdata = lookUpDataService.getTasksTypeByUniqueKey(taskType); 
		 	Collection<ProjectTasks> taskList=tasksService.getAllTasksByProjectIdAndTaskType(projectId, lookupdata.getId());
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
	 public String updateTaskStatus(@RequestParam(value="taskStatusId", required=false) Long taskStatusId, @RequestParam(value="taskId", required=false) String taskId,@RequestParam(value="userId", required=false) Long userId) throws Exception{ 
		 tasksService.updateTaskStatus(taskStatusId,taskId,userId);
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
	 public String updateTaskName(@RequestParam(value="taskName", required=false) String taskName, @RequestParam(value="taskId", required=false) String taskId,@RequestParam(value="userId", required=false) Long userId) throws Exception{ 
		 tasksService.updateTaskName(taskName,taskId,userId);
		 return taskName;
	  }
	 
 
	/**
	  * @author Laxman
	  * @param taskpriorityId
	  * @param taskId
	  * @return success
	  * @throws Exception
	  */
	 @RequestMapping(value = "/updateTaskPriority", method = RequestMethod.POST)
	 public String updateTaskPriority(@RequestParam(value="taskpriorityId", required=false) Long taskpriorityId, @RequestParam(value="taskId", required=false) String taskId,@RequestParam(value="userId", required=false) Long userId) throws Exception{ 
		 tasksService.updateTaskPriority(taskpriorityId,taskId,userId);
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
	 public String updateTaskSeverity(@RequestParam(value="taskSeverityId", required=false) Long taskSeverityId, @RequestParam(value="taskId", required=false) String taskId,@RequestParam(value="userId", required=false) Long userId) throws Exception{ 
		 tasksService.updateTaskSeverity(taskSeverityId,taskId,userId);
		 return "success";
	  }
	 
	 
	 @RequestMapping(value = "/updateTaskPercentage", method = RequestMethod.POST)
	 public String updateTaskPercentage(@RequestParam(value="percentage", required=false) Integer percentage, @RequestParam(value="taskId", required=false) String taskId,@RequestParam(value="userId", required=false) Long userId) throws Exception{ 
		 tasksService.updateTaskPercentage(percentage,taskId,userId);
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
	 public ProjectTasks getProjectTasksDescrption(@RequestParam(value="projectTaskId", required=false) String projectTaskId, @RequestParam(value="taskId", required=false) String taskId) throws Exception{ 
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
	 public String updateTaskDescription(@RequestParam(value="description", required=false) String description, @RequestParam(value="taskId", required=false) String taskId,@RequestParam(value="userId", required=false) Long userId) throws Exception{ 
		 User user = userService.getUserById(userId);
		 StringBuffer activity = new StringBuffer();
		 StringBuffer activitySummary = new StringBuffer();
		 ProjectTasks projectTasks = tasksService.getTasksId(taskId);
		 ProjectTasks oldProjectTasks = tasksService.getTasksId(taskId);
		 activity.append("Description");
		 projectTasks.setDescription(description);
		 projectTasks.setUpdatedDate(commonUtil.currentDate());
		 tasksService.updateTasks(projectTasks);
		 String resourceTable = getUpdateDescription(projectTasks,oldProjectTasks);
		 activitySummary.append(resourceTable);
		 projectTaskActivityService.saveProjectTaskActivities(activity.toString(), activitySummary.toString(),projectTasks,user);
		 
		 return "success";
	  }
	 
	 private String getUpdateDescription(ProjectTasks projectTasks, ProjectTasks oldProjectTasks) {
		 String tempHTML = "";
		 if(oldProjectTasks.getDescription() != null) {
			 tempHTML = tempHTML+"<p style='color:#909090;'>"+oldProjectTasks.getDescription()+"</p> ";
			 tempHTML = tempHTML+"<p>"+projectTasks.getDescription()+"</p>";
		 }else {
			 tempHTML = tempHTML+"<p>"+projectTasks.getDescription()+"</p>";
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
	 public String updateAssignTaskUsers(@RequestParam(value="assignValue", required=false) String assignValue, @RequestParam(value="taskId", required=false) String projectTaskId,@RequestParam(value="userId", required=false) Long userId) throws Exception{ 
		  projectAssignTasksService.inlineUpdateAssignTaskUsers(assignValue,projectTaskId,userId);
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
	 public void viewProjectTaskDocument(@RequestParam(value="documentId", required=false) Long documentId, @PathVariable(value="title") String title, HttpServletResponse response) throws Exception {
		 projectTaskDocumentsService.viewProjectTaskDocument(documentId,response);
	} 
			 
	 /**
	  * @author Laxman
	  * @param porjectTaskDocumentId
	  * @param userId
	  * @return success
	  * @throws Exception
	  */

	 @RequestMapping(value = "/deleteProjectTaskDocument", method = RequestMethod.POST)
	 public @ResponseBody String deleteProjectTaskDocument(@RequestParam(value = "porjectTaskDocumentId", required = false) Long porjectTaskDocumentId,@RequestParam(value="userId", required=false) Long userId) throws Exception {
		 User user = userService.getUserById(userId);
		 ProjectTaskDocuments projectTaskDoc = projectTaskDocumentsService.getProjectTaskDocumentsById(porjectTaskDocumentId);
		 projectTaskDocumentsService.deleteProjectTaskDocumentByPorjectTaskDocumentId(porjectTaskDocumentId);
		 
		 //store Task Activity 
		
	     StringBuffer activity = new StringBuffer();
	     StringBuffer activitySummary = new StringBuffer();
	     activity.append("Delete Attachment");	
	     activitySummary.append(" : ").append(projectTaskDoc.getFileName());
		 projectTaskActivityService.saveProjectTaskActivities(activity.toString(), activitySummary.toString(),projectTaskDoc.getProjectTaskId(),user);
		return "success";
	 }
	 
	 /**
	  * @author Laxman
	  * @param projectTaskDocId
	  * @return projectTaskDoc
	  * @throws Exception
	  */
	 @RequestMapping(value = "/getProjectTaskDocument", method = RequestMethod.POST)
	 public ProjectTaskDocuments getProjectTaskDocument(@RequestParam(value = "projectTaskDocId", required = false) Long projectTaskDocId) throws Exception
	 {
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
	 @RequestMapping(value = "/getProjectTasksBySubTaskType" , method = RequestMethod.POST)
	 public @ResponseBody   Collection<ProjectTasks> getProjectTasksBySubTaskType(@RequestParam(value="projectId", required=false) Long projectId,@RequestParam(value="taskType", required=false) String taskType) throws Exception {
		 LookUpData lookupdata = lookUpDataService.getTasksTypeByUniqueKey(taskType);
			 Collection<ProjectTasks> taskList=tasksService.getAllTasksByProjectIdAndSubTaskType(projectId,  lookupdata.getId());
			 return taskList;
	 }
		 
}
