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

import com.bis.sourcelead.constants.Constants;
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
import com.bis.sourcelead.util.SourceLeadRandomCodeHelper;
import com.bis.sourcelead.util.SourceLeadSessionManager;
import com.ibm.icu.text.DecimalFormat;

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
	
 
	
	public String getProjectTaskGenaratedId(Projects projects) throws Exception {
		String projectNmae = projects.getProjectName();
		String companyShortCode = null;
		Integer generatedIdcount = 0;
		Long number = 1L;
		String requirementId = null;
		String[] splitName = projects.getProjectName().split(" ");
		int count = 0;
		StringBuilder sb = new StringBuilder();
		String splitProjectName ="";
		switch (splitName.length) {
		  case 1:
				  switch( projectNmae.length()) {
	                    case 2:
	            			for (int j = 0; j < projectNmae.length(); j++) {
	            				projectNmae.charAt(j);
	            				count++;
	            			}
	            			sb.append(projectNmae.substring(0, count));
	            			splitProjectName = sb.toString().toUpperCase();
	                        break;
	                    default:
	            			sb.append(projectNmae.substring(0, 2));
	            			 splitProjectName = sb.toString().toUpperCase();
		                }
		        break;
		  case 2: 
				String first = splitName[0].substring(0, 1);
				String second = splitName[1].substring(0, 1);
				 splitProjectName = sb.append(first).append(second).toString().toUpperCase();
		        break;
		  default:
				for (String s : projectNmae.split(" ")){
					sb.append(s.charAt(0));
				}
				 splitProjectName = sb.toString().substring(0, 2).toUpperCase();
		        break;
		}
		
		
		if(StringUtils.isNotBlank(projects.getOrgLocationsId().getGeneratedUserId())) {
			companyShortCode = projects.getOrgLocationsId().getGeneratedUserId();
		}
		StringBuilder sbuilder = new StringBuilder();
		sbuilder.append(companyShortCode).append(splitProjectName);
		String generatedUsreId = sbuilder.toString();
		for (int i = 0; i < generatedUsreId.length(); i++) {
			char ch = generatedUsreId.charAt(i);
			if (Character.isAlphabetic(ch)) {
				generatedIdcount++;
			}
		}
		generatedIdcount = generatedIdcount + 1;
		Long checkNumber = tasksDao.getProjectTasksGeneratedIdByProjectId(projects.getId(), generatedIdcount); 
		if (checkNumber == null) {
			requirementId = number.toString();
		} else {
			Long reqId = checkNumber + 1;
			requirementId = reqId.toString();
		}
		final DecimalFormat decimalFormat = new DecimalFormat("0");
		String convetredRequirementsId = decimalFormat.format(Long.parseLong(requirementId));
		
		return sbuilder.append(convetredRequirementsId).toString();
	}
	
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
		if(projectTasksDTO .getFileInfo() != null &&  !projectTasksDTO.getFileInfo().isEmpty()) {
			List<ProjectTaskDocuments> projDocDBList = projectTasksDTO .getFileInfo().stream()
					.map(bo -> new ProjectTaskDocuments(bo.getFileName(), bo.getFileURL(),bo.getContentType(),tasks)).collect(Collectors.toList());
	        tasks.getProjectTaskDocuments().addAll(projDocDBList);
	        for(FileInfo fileInfo : projectTasksDTO.getFileInfo()){
				FileUploadUtil.moveFile(projectTasksDTO.getStaticPath(), fileInfo, "tasks");
			}
		}
        Set<ProjectAssignTask> assignProjetcTasks = getAssignProjectTasks(projectTasksDTO,tasks); 
        tasks.setProjectAssignTask(assignProjetcTasks);
        User user = userService.getUserById(userId);
        tasks.setCreatedBy(user.getUserCode());
        tasks.setPercentage(Constants.CANDIDATE_IN_ACTIVE);
        tasks.setGeneratedId(getProjectTaskGenaratedId(projects));
        tasks.setProjectTasksUUID(SourceLeadRandomCodeHelper.generateRandomUUID());
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
		 projectTaskActivityService.savePriorityProjectTaskActivitiesByLookupData(activity.toString(), activitySummary.toString(),projectTasks,user,data);
		
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
		   projectTaskActivityService.saveProjectTaskActivitiesByLookupData(activity.toString(), activitySummary.toString(),projectTasks,user,data);
		
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

	@Override
	public void updateTaskPercentage(Integer percentage, String taskId, Long userId) throws Exception {
		User user = userService.getUserById(userId);
		ProjectTasks projectTasks = tasksDao.getTasksId(taskId);
		
		 //Task Percentage activity	
		 ProjectTasks  oldData = tasksDao.getTasksId(taskId);
	     StringBuffer activity = new StringBuffer();
	     StringBuffer activitySummary = new StringBuffer();
	     activity.append("Task Percentage");
	     projectTasks.setPercentage(percentage);
	     projectTasks.setUpdatedDate(commonUtil.currentDate());
	     tasksDao.updateTasks(projectTasks);
	    
	     activitySummary.append(oldData.getPercentage()).append(" to ").append(projectTasks.getPercentage());
		 projectTaskActivityService.saveProjectTaskActivities(activity.toString(), activitySummary.toString(),projectTasks,user);
		
	}
	
	@Override
	public Long getProjectTasksGeneratedIdByProjectId(Long projectId, Integer generatedIdCount) throws Exception {
		return tasksDao.getProjectTasksGeneratedIdByProjectId(projectId, generatedIdCount);
	}

	@Override
	public ProjectTasks getProjectTasksByUUID(String taskUUD) throws Exception {
		return tasksDao.getProjectTasksByUUID(taskUUD);
	}

	@Override
	public Integer getProjectTasksListCountByProjectIdAndStatus(Long projectId, String status) throws Exception {
		  return tasksDao.getProjectTasksListCountByProjectIdAndStatus(projectId,status);
	}

	@Override
	public List<ProjectTasks> getOverdueTaskListByProjectId(Long projectId,String filterDate, Date startDateUTC, Date endDateUTC) throws Exception {
		return tasksDao.getOverdueTaskListByProjectId(projectId,filterDate,startDateUTC,endDateUTC);
	}

	@Override
	public List<ProjectTasks> getAllTasksByProjectIdAndFilter(Long projectId, String filterDate, Date startDateUTC, Date endDateUTC) throws Exception {
		return tasksDao.getAllTasksByProjectIdAndFilter(projectId, filterDate, startDateUTC, endDateUTC);
	}

}