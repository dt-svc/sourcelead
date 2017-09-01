package com.bis.sourcelead.service;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;

import com.bis.sourcelead.dto.ProjectTasksDTO;
import com.bis.sourcelead.entity.ProjectMembers;
import com.bis.sourcelead.entity.ProjectTasks;
import com.bis.sourcelead.entity.Projects;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.model.ProjectDocumentBO;
import com.bis.sourcelead.model.ProjectTaskDocumentBO;

public interface ProjectTasksService {
	
    public ProjectTasks addTasks(ProjectTasks tasks)throws Exception;
    
    public void saveAll(Collection<ProjectTasks> taskList)throws Exception;
	
    public ProjectTasks updateTasks(ProjectTasks tasks)throws Exception;
    
    public ProjectTasks getTasksId(String id)throws Exception;
    
    public Boolean deleteTask(String id)throws Exception;
    
    public List<ProjectTasks> getAllTasks()throws Exception;
    
    public List<ProjectTasks> getAllTasksByProjectId(Long projectId)throws Exception;
    
    public List<ProjectTasks> getAllTasksByProjectIdAndTaskType(Long projectId,Long TaskType)throws Exception;
    
    public List<ProjectTasks> getAllTasksByProjectIdAndSubTaskType(Long projectId,Long subTaskType)throws Exception;
    
    public List<ProjectTasks> getAllTasksByProjectIdAndExistedProjectTaskIds(Long projectId,List<Long> avilableProjectTaskIds)throws Exception;
    
    public Collection	<ProjectTasks> getAllSourceleadTasks()throws Exception;
    
//    String saveProjectTasksByProjectId(Long projectId,JSONObject jsonObj,List<ProjectTaskDocumentBO> projectTaskDocuments,Long userId) throws Exception ;
    
    Set<ProjectTasks> prepareProjectTasksListByJson(JSONObject jsonObj,HttpServletRequest request,Projects projects) throws Exception;
    
    public ProjectTasks validateTaskNameByTaskId(Long taskId,String taskName,Long projectId)throws Exception;
    
    public List<ProjectTasks> getAllTasksByProjectIdAndTaskTypeAndTaskStatus(Long projectId,String taskType,String taskStatus)throws Exception;
    
    public List<ProjectTasks> getAllTasksByProjectIdAndParentTaskId(Long projectId,Long parentTaskId)throws Exception;

	public void updateTaskPriority(Long taskpriorityId, String taskId, Long userId)throws Exception;

	public void updateTaskSeverity(Long taskSeverityId, String taskId, Long userId)throws Exception;

	public void updateTaskStatus(Long taskStatusId, String taskId, Long userId)throws Exception;
	
	public void updateTaskName(String taskName, String taskId, Long userId)throws Exception;
    
	public String saveProjectTasksDTOByProjectId(Long projectId, ProjectTasksDTO projectTasksDTO, Long userId) throws Exception;
}