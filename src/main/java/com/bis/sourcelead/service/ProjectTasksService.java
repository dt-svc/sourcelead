package com.bis.sourcelead.service;

import java.io.File;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.json.JSONObject;

import com.bis.sourcelead.dto.ProjectTasksDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.dto.TaskDTO;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.ProjectAssignTask;
import com.bis.sourcelead.entity.ProjectTasks;
import com.bis.sourcelead.entity.Projects;
import com.bis.sourcelead.entity.User;

public interface ProjectTasksService {
	
    public ProjectTasks addTasks(ProjectTasks tasks)throws Exception;
    
    public void saveAll(Collection<ProjectTasks> taskList)throws Exception;
	
    public ProjectTasks updateTasks(ProjectTasks tasks)throws Exception;
    
    public ProjectTasks getTasksId(String id)throws Exception;
    
    public ProjectTasks getProjectTasksByUUID(String taskUUD)throws Exception;
    
    public ProjectTasks getProjectTasksByProjectIdAndProjectIdAndTaskName(Long projectId,String taskName)throws Exception;
    
    public Boolean deleteTask(String id)throws Exception;
    
    public List<ProjectTasks> getAllTasks()throws Exception;
    
    public List<ProjectTasks> getAllTasksByProjectId(Long projectId)throws Exception;
    
    public List<ProjectTasks> getAllTasksByProjectIdAndTaskType(Long projectId,Long TaskType)throws Exception;
    
    public List<ProjectTasks> getAllTasksByProjectIdAndSubTaskType(Long projectId,Long subTaskType)throws Exception;
    
    public List<ProjectTasks> getAllTasksByProjectIdAndExistedProjectTaskIds(Long projectId,List<Long> avilableProjectTaskIds)throws Exception;
    
    public Collection	<ProjectTasks> getAllSourceleadTasks()throws Exception;
    
    Set<ProjectTasks> prepareProjectTasksListByJson(JSONObject jsonObj,HttpServletRequest request,Projects projects) throws Exception;
    
    public ProjectTasks validateTaskNameByTaskId(Long taskId,String taskName,Long projectId)throws Exception;
    
    public List<ProjectTasks> getAllTasksByProjectIdAndTaskTypeAndTaskStatus(Long projectId,String taskType,String taskStatus)throws Exception;
    
    public List<ProjectTasks> getAllTasksByProjectIdAndParentTaskId(Long projectId,Long parentTaskId)throws Exception;

	public void updateTaskPriority(TaskDTO taskDTO, OrgLocations loginOrgLocation)throws Exception;

	public void updateTaskSeverity(TaskDTO taskDTO, OrgLocations loginOrgLocation)throws Exception;

	public void updateTaskStatus(TaskDTO taskDTO, OrgLocations loginOrgLocation)throws Exception;
	
	public void updateTaskName(String taskName, String taskId, Long userId)throws Exception;
	
	public void updateTaskPercentage(Integer percentage, String taskId, Long userId)throws Exception;
	
	public void updateBillingType(String billing, String taskId, Long userId)throws Exception;
	
	public void updateDueDate(TaskDTO taskDTO, OrgLocations loginOrgLocation)throws Exception;
	
	public void updateEstimatedTime(TaskDTO taskDTO, OrgLocations loginOrgLocation)throws Exception;
	
	public void updateOwner(Long ownerId, String taskId, Long userId)throws Exception;
	
	public void updateEpic(Long epicId, String taskId, Long userId)throws Exception;
	
	public void updateUserStory(Long userStoryId, String taskId, Long userId)throws Exception;
	
	public String saveProjectTasksDTOByProjectId(Long projectId, ProjectTasksDTO projectTasksDTO, Long userId) throws Exception;
	
	public Long getProjectTasksGeneratedIdByProjectId(Long projectId,Integer generatedIdCount) throws Exception;
	
	Integer getProjectTasksListCountByProjectIdAndStatus(Long projectId,String  status) throws Exception;
	
	public List<ProjectTasks> getOverdueTaskListByProjectId(Long projectId, Date startDateUTC, Date endDateUTC)throws Exception;
	
	 public List<ProjectTasks> getAllTasksByProjectIdAndFilter(Long projectId, Date startDateUTC, Date endDateUTC)throws Exception;
	 
	 ResponseList<ProjectTasks> getAllProjectTasksByProjectIdAndTaskTypeAndTaskStatus(ProjectTasksDTO projectTasksDTO,Long projectId,String taskType,String taskStatus,String userRole,String assignedMembers)throws Exception;

	 public void SendMailtoAssigntoMembersaboutAddingTask(ProjectAssignTask projectAssignTask, ProjectTasksDTO projectTasksDTO, ProjectTasks tasks, OrgLocations loginOrgLocation,User loginUser, String redirectURL, long redirectOrgLocationId) throws Exception;

	public Sheet getTaskExcelFile(Sheet createExcelsheet, Workbook createExcelworkbook, ResponseList<ProjectTasks> taskList, OrgLocations orgLocations) throws Exception;

	public File getTaskPdfFile(ResponseList<ProjectTasks> taskList, OrgLocations orgLocations) throws Exception;

	public ProjectTasks getProjectTaskByleaveTypeId(long leaveTypeId, Long projectId);
	
	List<ProjectTasks> getTasksListByLeaveType(Long levaTypeId)throws Exception;
	
	List<ProjectTasks> getTasksListByLookupIdAndCategoryType(Long lookupId,String taskType)throws Exception;
	
	public ResponseList<ProjectTasks> getOverdueTaskReportListByOrglocationId(ProjectTasksDTO  projectTasksDTO, Date[] searchDates)throws Exception;

	public void updateTaskStatusList(TaskDTO taskDTO, OrgLocations loginOrgLocation)throws Exception;

 	 
}
