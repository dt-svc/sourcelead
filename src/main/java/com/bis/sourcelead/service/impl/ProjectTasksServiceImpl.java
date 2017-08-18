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
import com.bis.sourcelead.service.ProjectTasksService;
import com.bis.sourcelead.service.ProjectsService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;

@Service
public class ProjectTasksServiceImpl implements ProjectTasksService {

	@Autowired
	private ProjectTasksDao tasksDao;

	@Autowired
	HttpServletRequest request;
	
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
	public List<ProjectTasks> getAllTasksByProjectIdAndTaskType(Long projectId, String TaskType) throws Exception {
		List<ProjectTasks> taskList = tasksDao.getAllTasksByProjectIdAndTaskType(projectId,TaskType);
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
	public String saveProjectTasksByProjectId(Long projectId, JSONObject jsonObj, List<ProjectTaskDocumentBO> projectTaskDocumentBOList) throws Exception {
		OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		ProjectTasks  tasks  = new ProjectTasks();
		
		LookUpData taskType = lookUpDataService.getTasksById(Long.parseLong(jsonObj.getString("taskstype")));
		tasks.setTaskType(taskType);
		tasks.setTaskName(jsonObj.getString("taskName"));
		tasks.setBillableType(jsonObj.getString("taskBillableType"));
		Projects projects = projectsService.getProjectById(projectId);
		tasks.setProjectId(projects);
		if(jsonObj.has("epic") && StringUtils.isNotEmpty(jsonObj.getString("epic"))){
			ProjectTasks parentTaks = tasksDao.getTasksId(jsonObj.getString("epic"));
			if(parentTaks != null) {
				tasks.setParentTaskId(parentTaks);
			}
		}
		if(jsonObj.has("userStory") && StringUtils.isNotEmpty(jsonObj.getString("userStory"))){
			ProjectTasks parentTaks = tasksDao.getTasksId(jsonObj.getString("userStory"));
			if(parentTaks != null) {
				tasks.setParentTaskId(parentTaks);
			}
		}
		
		if(jsonObj.getString("duDate") != null && jsonObj.getString("duDate") != "" && jsonObj.getString("duDate") != "undefined"){
    		Date duDate = commonUtil.orgLocationDateFormat(loginOrgLocation.getDateFormat(),jsonObj.getString("duDate"));
    		tasks.setDueDate(duDate);
    	}
		if(jsonObj.getString("estimatedDate") != null && jsonObj.getString("estimatedDate") != "" && jsonObj.getString("estimatedDate") != "undefined"){
    		Date estimatedDate = commonUtil.orgLocationDateFormat(loginOrgLocation.getDateFormat(),jsonObj.getString("estimatedDate"));
    		tasks.setEstimateDate(estimatedDate);
    	}
		
		if(jsonObj.has("priority") && StringUtils.isNotEmpty(jsonObj.getString("priority"))){
			LookUpData priority = lookUpDataService.getTasksById(Long.parseLong(jsonObj.getString("priority")));
			tasks.setPriority(priority);
		}
		if(jsonObj.has("severity") && StringUtils.isNotEmpty(jsonObj.getString("severity"))){
			LookUpData severity = lookUpDataService.getTasksById(Long.parseLong(jsonObj.getString("severity")));
			tasks.setSeverity(severity);
		}
		if(jsonObj.has("taskStatus") && StringUtils.isNotEmpty(jsonObj.getString("taskStatus"))){
			LookUpData taskStatus = lookUpDataService.getTasksById(Long.parseLong(jsonObj.getString("taskStatus")));
			tasks.setTaskStatus(taskStatus);
		}
		
		if(jsonObj.has("taskDescription") && StringUtils.isNotEmpty(jsonObj.getString("taskDescription"))){
			tasks.setDescription(jsonObj.getString("taskDescription"));
		}
		if(jsonObj.has("currencyType") && StringUtils.isNotEmpty(jsonObj.getString("currencyType"))){
			tasks.setBusinessCurrency(jsonObj.getString("currencyType"));
		}
		if(jsonObj.has("businessValue") && StringUtils.isNotEmpty(jsonObj.getString("businessValue"))){
			tasks.setBusinessValue(jsonObj.getString("businessValue"));
		}
		
		if(jsonObj.has("taskOwner") && StringUtils.isNotEmpty(jsonObj.getString("taskOwner"))){
			User user = userService.getUserById(Long.parseLong(jsonObj.getString("taskOwner")));
			if(user != null) {
				tasks.setOwnerUserId(user);
			}
		}
		
        List<ProjectTaskDocuments> projDocDBList = projectTaskDocumentBOList.stream().map(bo -> new ProjectTaskDocuments(bo.getFileName(), bo.getFileType(),bo.getFileContents(),tasks,bo.getStatus())).collect(Collectors.toList());
        tasks.getProjectTaskDocuments().addAll(projDocDBList);
        
        Set<ProjectAssignTask> assignProjetcTasks = getAssignProjectTasks(jsonObj,tasks); 
        tasks.setProjectAssignTask(assignProjetcTasks);
        tasks.setCreatedDate(commonUtil.currentDate());
        tasksDao.addTasks(tasks);
        
        return "success";
	}

	private Set<ProjectAssignTask> getAssignProjectTasks(JSONObject jsonObj, ProjectTasks tasks) throws Exception {
		 Set<ProjectAssignTask> projectAssignTaskSet = new HashSet<>();
		if(jsonObj.has("assignToTask")){
			String assignId =null;
			try{
				JSONArray jsonArray = jsonObj.getJSONArray("assignToTask");
				assignId = jsonArray.toString();
			}
			catch(JSONException e){
				assignId =jsonObj.getString("assignToTask");
			}
			assignId = assignId.replaceAll("\\[", "").replaceAll("\\]","");
			String[] assign = assignId.split(",");
			for(int i=0 ;i<assign.length;i++ ){
				
				ProjectAssignTask assignTask = new ProjectAssignTask();
				
				assignTask.setProjectTaskId(tasks);
				ProjectMembers pm = projectMembersService.getProjectMemberById(Long.parseLong(assign[i].replace("\"", "")));
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
	public List<ProjectTasks> getAllTasksByProjectIdAndTaskTypeAndTaskStatus(Long projectId, String taskType, String taskStatus) throws Exception {
		List<ProjectTasks> allTaskList = tasksDao.getAllTasksByProjectIdAndTaskTypeAndTaskStatus(projectId,taskType,taskStatus);
		return allTaskList;
	}

}