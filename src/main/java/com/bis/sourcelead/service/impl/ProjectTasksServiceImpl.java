package com.bis.sourcelead.service.impl;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.ProjectTasksDao;
import com.bis.sourcelead.dto.FileInfo;
import com.bis.sourcelead.dto.ProjectTasksDTO;
import com.bis.sourcelead.entity.CandidateResumes;
import com.bis.sourcelead.entity.LookUpData;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.ProjectAssignTask;
import com.bis.sourcelead.entity.ProjectMembers;
import com.bis.sourcelead.entity.ProjectTaskDocuments;
import com.bis.sourcelead.entity.ProjectTasks;
import com.bis.sourcelead.entity.Projects;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.model.ProjectTaskDocumentBO;
import com.bis.sourcelead.service.LookUpDataService;
import com.bis.sourcelead.service.ProjectMembersService;
import com.bis.sourcelead.service.ProjectTaskActivityService;
import com.bis.sourcelead.service.ProjectTasksService;
import com.bis.sourcelead.service.ProjectsService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.FileUploadUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;

@Service
public class ProjectTasksServiceImpl implements ProjectTasksService {

	@Autowired
	private ProjectTasksDao tasksDao;

	@Autowired
	HttpServletRequest request;
	
	@Autowired
	ProjectTaskActivityService projectTaskActivityService;
	
	@Override
	public ProjectTasks addTasks(ProjectTasks tasks) throws Exception {
		tasks.setUpdatedDate(commonUtil.currentDate());
		return tasksDao.addTasks(tasks);
	}

	@Override
	public ProjectTasks updateTasks(ProjectTasks tasks) throws Exception {
		tasks.setUpdatedDate(commonUtil.currentDate());
		return tasksDao.updateTasks(tasks);
	}

	@Override
	public ProjectTasks getTasksId(String id) throws Exception {
		return tasksDao.getTasksId(id);
	}

	@Override
	public ProjectTasks validateTaskNameByTaskId(Long taskId, String taskName,Long projectId) throws Exception {
		return tasksDao.validateTaskNameByTaskId(taskId,taskName,projectId);
	}
	
	@Override
	public Boolean deleteTask(String id) throws Exception {
		Boolean status = tasksDao.deleteTask(id);
		return status;
	}

	@Override
	public List<ProjectTasks> getAllTasks() throws Exception {
		List<ProjectTasks> list = tasksDao.getAllTasks();
		return list;
	}

	@Override
	public List<ProjectTasks> getAllTasksByProjectId(Long projectId) throws Exception {
		List<ProjectTasks> list = tasksDao.getAllTasksByProjectId(projectId);
		return list;
	}
	
	
	@Override
	public List<ProjectTasks> getAllTasksByProjectIdAndExistedProjectTaskIds(Long projectId,
			List<Long> avilableProjectTaskIds) throws Exception {
		
		List<ProjectTasks> list = tasksDao.getAllTasksByProjectIdAndExistedProjectTaskIds(projectId, avilableProjectTaskIds);
		return list;
		
	}
	

	@Override
	public Collection<ProjectTasks> getAllSourceleadTasks() throws Exception {
		
		Collection<ProjectTasks> list = tasksDao.getAllSourceleadTasks();
		return list;
		
	}

	
	@Override
	public List<ProjectTasks> getAllTasksByProjectIdAndTaskType(Long projectId, Long TaskType) throws Exception {
		List<ProjectTasks> taskList = tasksDao.getAllTasksByProjectIdAndTaskType(projectId,TaskType);
		return taskList;
	}

	@Override
	public List<ProjectTasks> getAllTasksByProjectIdAndSubTaskType(Long projectId, Long subTaskType) throws Exception {
		List<ProjectTasks> taskList = tasksDao.getAllTasksByProjectIdAndSubTaskType(projectId,subTaskType);
		return taskList;
	}
	
	@Override
	public void saveAll(Collection<ProjectTasks> taskList) throws Exception {
		tasksDao.saveAll(taskList);
	}
	
	@Autowired
	 private CommonUtil commonUtil;
	
	@Autowired
	 private ProjectsService projectsService;
	
	@Autowired
	 private UserService userService;
	
	@Autowired
	ProjectMembersService projectMembersService;
	
	@Autowired
	LookUpDataService lookUpDataService;
	
	@Override
	public List<ProjectTasks> getAllTasksByProjectIdAndTaskTypeAndTaskStatus(Long projectId, String taskType, String taskStatus) throws Exception {
		List<ProjectTasks> allTaskList = tasksDao.getAllTasksByProjectIdAndTaskTypeAndTaskStatus(projectId,taskType,taskStatus);
		return allTaskList;
	}

	@Override
	public List<ProjectTasks> getAllTasksByProjectIdAndParentTaskId(Long projectId, Long parentTaskId) throws Exception {
		List<ProjectTasks> allparentTaskList = tasksDao.getAllTasksByProjectIdAndParentTaskId(projectId,parentTaskId);
		return allparentTaskList;
	}

	@Override
	public String saveProjectTasksDTOByProjectId(Long projectId, ProjectTasksDTO projectTasksDTO, Long userId) throws Exception {
		OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		ProjectTasks  tasks  = new ProjectTasks();
		
		LookUpData taskType = lookUpDataService.getTasksTypeByUniqueKey(projectTasksDTO.getTaskstype());
		tasks.setTaskType(taskType);
		tasks.setTaskName(projectTasksDTO.getTaskName());
		tasks.setBillableType(projectTasksDTO.getTaskBillableType());
		Projects projects = projectsService.getProjectById(projectId);
		tasks.setProjectId(projects);
		
		if(projectTasksDTO.getEpic() != null ) {
			ProjectTasks parentTaks = tasksDao.getTasksId(projectTasksDTO.getEpic());
			if(parentTaks != null) {
				tasks.setParentTaskId(parentTaks);
			}
		}
		if(projectTasksDTO.getUserStory() != null ){
			ProjectTasks parentTaks = tasksDao.getTasksId(projectTasksDTO.getUserStory());
			if(parentTaks != null) {
				tasks.setParentTaskId(parentTaks);
			}
		}
		if(projectTasksDTO.getSubTask() != null){
			ProjectTasks parentSubTask = tasksDao.getTasksId(projectTasksDTO.getSubTask());
			if(parentSubTask != null) {
				tasks.setParentTaskId(parentSubTask);
			}
		}
		
		if(projectTasksDTO.getDuDate() != null && !projectTasksDTO.getDuDate().isEmpty()){
	    		Date duDate = commonUtil.orgLocationDateFormat(loginOrgLocation.getDateFormat(),projectTasksDTO.getDuDate());
	    		tasks.setDueDate(duDate);
		}
		if(projectTasksDTO.getEstimatedDate() != null && !projectTasksDTO.getEstimatedDate().isEmpty()){
	    		Date estimatedDate = commonUtil.orgLocationDateFormat(loginOrgLocation.getDateFormat(),projectTasksDTO.getEstimatedDate());
	    		tasks.setEstimateDate(estimatedDate);
		}
		
		
		if(projectTasksDTO.getPriority() != null){
			LookUpData priority = lookUpDataService.getTasksById(projectTasksDTO.getPriority());
			tasks.setPriority(priority);
		}
		if(projectTasksDTO.getSeverity() != null){
			LookUpData severity = lookUpDataService.getTasksById(projectTasksDTO.getSeverity());
			tasks.setSeverity(severity);
		}
		if(projectTasksDTO.getTaskStatus() != null){
			LookUpData taskStatus = lookUpDataService.getTasksById(projectTasksDTO.getTaskStatus());
			tasks.setTaskStatus(taskStatus);
		}
		
		if(projectTasksDTO.getTaskDescription() != null){
			tasks.setDescription(projectTasksDTO.getTaskDescription());
		}
		if(projectTasksDTO.getCurrencyType() != null){
			tasks.setBusinessCurrency(projectTasksDTO.getCurrencyType());
		}
		if(projectTasksDTO.getBusinessValue() != null){
			tasks.setBusinessValue(projectTasksDTO.getBusinessValue());
		}
		
		if(projectTasksDTO.getTaskOwner() != null){
			User user = userService.getUserById(projectTasksDTO.getTaskOwner());
			if(user != null) {
				tasks.setOwnerUserId(user);
			}
		}
		if(projectTasksDTO .getUploadedFileInfo() != null &&  !projectTasksDTO.getUploadedFileInfo().isEmpty()) {
			List<ProjectTaskDocuments> projDocDBList = projectTasksDTO .getUploadedFileInfo().stream()
					.map(bo -> new ProjectTaskDocuments(bo.getFileName(), bo.getFileURL(),bo.getContentType(),tasks)).collect(Collectors.toList());
	        tasks.getProjectTaskDocuments().addAll(projDocDBList);
	        for(FileInfo fileInfo : projectTasksDTO.getUploadedFileInfo()){
				FileUploadUtil.moveFile(projectTasksDTO.getStaticPath(), fileInfo, "tasks");
			}
		}
        Set<ProjectAssignTask> assignProjetcTasks = getAssignProjectTasks(projectTasksDTO,tasks); 
        tasks.setProjectAssignTask(assignProjetcTasks);
        User user = userService.getUserById(userId);
        tasks.setCreatedBy(user.getUserCode());
        tasks.setCreatedDate(commonUtil.currentDate());
        tasksDao.addTasks(tasks);
        
        return "success";
	}

	private Set<ProjectAssignTask> getAssignProjectTasks(ProjectTasksDTO projectTasksDTO, ProjectTasks tasks) throws Exception {
		 Set<ProjectAssignTask> projectAssignTaskSet = new HashSet<>();
		 
		if(projectTasksDTO != null && projectTasksDTO.getAssignTaskId() != null){
			String assignId =null;
				List<Long> jsonArray = projectTasksDTO.getAssignTaskId();
				assignId = jsonArray.toString().replaceAll("\\[", "").replaceAll("\\]","").trim();
			String[] assign = assignId.split(",");
			for(String memberId : assign)
			{
				ProjectAssignTask assignTask = new ProjectAssignTask();
				assignTask.setProjectTaskId(tasks);
				ProjectMembers pm = projectMembersService.getProjectMemberById(Long.parseLong(memberId.trim()));
				assignTask.setProjectMemberId(pm);
				assignTask.setStatus("1");
				assignTask.setCreatedDate(CommonUtil.currentDate());
				projectAssignTaskSet.add(assignTask);
			}
		}
		return projectAssignTaskSet;
	}

	@Override
	public Set<ProjectTasks> prepareProjectTasksListByJson(JSONObject jsonObj,  HttpServletRequest request,
			Projects projects) throws Exception {
		 
		    HashSet<ProjectTasks> projectTasksList = new HashSet<ProjectTasks>(); 
		    if(jsonObj.has("maxTsRowNum")){
			String maxAdminTimeTypeRowNum = jsonObj.getString("maxTsRowNum");
			int maxCount = Integer.parseInt(maxAdminTimeTypeRowNum);

			for (int c = 1; c < maxCount; c++) {
				ProjectTasks  tasks  = new ProjectTasks();
				if(jsonObj.has(c+"taskName")){
					if(jsonObj.has(c+"taskDescription")){
						tasks.setDescription(jsonObj.getString(c+"taskDescription"));
					}
						tasks.setTaskName(jsonObj.getString(c+"taskName"));
						tasks.setBillableType(jsonObj.getString(c+"taskBillableType"));
						tasks.setCreatedDate(new Date());
						tasks.setUpdatedDate(new Date());
						tasks.setProjectId(projects);
						projectTasksList.add(tasks);
			  }
			}
		    }
		   return projectTasksList;
		
	}

	
	@Override
	public void updateTaskPriority(Long taskpriorityId, String taskId, Long userId) throws Exception {
		 User user = userService.getUserById(userId);
		 ProjectTasks projectTasks = tasksDao.getTasksId(taskId);
		 StringBuffer activity = new StringBuffer();
		 StringBuffer  activitySummary = new StringBuffer();
		 LookUpData  oldData =null;
		 LookUpData  nullOldData =null;
		 LookUpData data  = null;
		 if(taskpriorityId != null) {
			data = lookUpDataService.getTasksById(taskpriorityId);
			if(projectTasks.getPriority() != null) {
				oldData = lookUpDataService.getTasksById(projectTasks.getPriority().getId());
			}
		    activity.append("Priority");
		    
			 projectTasks.setPriority(data);
		}else {
			nullOldData = lookUpDataService.getTasksById(projectTasks.getPriority().getId());
			activity.append("Priority");
			projectTasks.setPriority(null);
		}
		 projectTasks.setUpdatedDate(commonUtil.currentDate());
		 tasksDao.updateTasks(projectTasks);
		 if(oldData != null) {
			 activitySummary.append(oldData.getName()).append(" to ").append(data.getName());
		 }else {
			 if(data != null) {
				 activitySummary.append("Unassigned").append(" to ").append(data.getName());
			 }else {
				 activitySummary.append(nullOldData.getName()).append(" to ").append("Unassigned");
			 }
			 
		 }
		 projectTaskActivityService.saveProjectTaskActivities(activity.toString(), activitySummary.toString(),projectTasks,user);
		
	}

	@Override
	public void updateTaskSeverity(Long taskSeverityId, String taskId, Long userId)throws Exception {
		 User user = userService.getUserById(userId);
		 ProjectTasks projectTasks = tasksDao.getTasksId(taskId);
		 StringBuffer activity = new StringBuffer();
		 StringBuffer activitySummary = new StringBuffer();
		 LookUpData  oldData =null;
		 LookUpData  nullOldData =null;
		 LookUpData data  = null;
		if(taskSeverityId != null) {
            data = lookUpDataService.getTasksById(taskSeverityId);
            if(projectTasks.getSeverity() != null) {
            	oldData = lookUpDataService.getTasksById(projectTasks.getSeverity().getId());
            }
		    activity.append("Severity");
			projectTasks.setSeverity(data);
		}else {
			nullOldData = lookUpDataService.getTasksById(projectTasks.getSeverity().getId());
			activity.append("Severity");
			projectTasks.setSeverity(null);
		}
		 projectTasks.setUpdatedDate(commonUtil.currentDate());
		 tasksDao.updateTasks(projectTasks);
		 if(oldData != null) {
			 activitySummary.append(oldData.getName()).append(" to ").append(data.getName());
		 }else {
			 if(data != null) {
				 activitySummary.append("Unassigned").append(" to ").append(data.getName());
			 }else {
				 activitySummary.append(nullOldData.getName()).append(" to ").append("Unassigned");
			 }
			 
		 }
		 projectTaskActivityService.saveProjectTaskActivities(activity.toString(), activitySummary.toString(),projectTasks,user);
		
	}

	@Override
	public void updateTaskStatus(Long taskStatusId, String taskId, Long userId)throws Exception {
		    User user = userService.getUserById(userId);
		    ProjectTasks projectTasks = tasksDao.getTasksId(taskId);
		    LookUpData  data = lookUpDataService.getTasksById(taskStatusId);
		    
		    LookUpData  oldData = lookUpDataService.getTasksById(projectTasks.getTaskStatus().getId());
		    StringBuffer activity = new StringBuffer();
		    StringBuffer activitySummary = new StringBuffer();
		    activity.append("Status");
		    
			projectTasks.setTaskStatus(data);
		    projectTasks.setUpdatedDate(commonUtil.currentDate());
		    tasksDao.updateTasks(projectTasks);
		   activitySummary.append(oldData.getName()).append(" to ").append(data.getName());
		   projectTaskActivityService.saveProjectTaskActivities(activity.toString(), activitySummary.toString(),projectTasks,user);
		
	}

	@Override
	public void updateTaskName(String taskName, String taskId, Long userId) throws Exception {
		    User user = userService.getUserById(userId);
		    ProjectTasks projectTasks = tasksDao.getTasksId(taskId);
		    //Task name activity	
		    ProjectTasks  oldData = tasksDao.getTasksId(taskId);
		    StringBuffer activity = new StringBuffer();
		    StringBuffer activitySummary = new StringBuffer();
		    activity.append("Task Name");
		    
		    projectTasks.setTaskName(taskName);
		    projectTasks.setUpdatedDate(commonUtil.currentDate());
		    tasksDao.updateTasks(projectTasks);
		    
		    activitySummary.append(oldData.getTaskName()).append(" to ").append(projectTasks.getTaskName());
			projectTaskActivityService.saveProjectTaskActivities(activity.toString(), activitySummary.toString(),projectTasks,user);
		
	}

	

}