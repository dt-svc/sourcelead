package com.bis.sourcelead.dao;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import com.bis.sourcelead.dto.ProjectTasksDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.ProjectTasks;

public interface ProjectTasksDao {

	
    public ProjectTasks addTasks(ProjectTasks tasks)throws Exception;
    
    public void saveAll(Collection<ProjectTasks> taskList) throws Exception;
	
    public ProjectTasks updateTasks(ProjectTasks tasks)throws Exception;
    
    public ProjectTasks getTasksId(String id)throws Exception;
    
    public Boolean deleteTask(String id)throws Exception;
    
    public ProjectTasks getProjectTasksByUUID(String taskUUD)throws Exception;
    
    public List<ProjectTasks> getAllTasks()throws Exception;
    
    public List<ProjectTasks> getAllTasksByProjectIdAndTaskType(Long projectId,Long TaskType)throws Exception;
    
    public ProjectTasks getProjectTasksByProjectIdAndProjectIdAndTaskName(Long projectId,String taskName)throws Exception;
    
    public List<ProjectTasks> getAllTasksByProjectIdAndSubTaskType(Long projectId,Long subTaskType)throws Exception;
    
    public List<ProjectTasks> getAllTasksByProjectId(Long projectId)throws Exception;
    
    public List<ProjectTasks> getAllTasksByProjectIdAndExistedProjectTaskIds(Long projectId,List<Long> avilableProjectTaskIds)throws Exception;
     
    public List<ProjectTasks> getAllSourceleadTasks()throws Exception;
    
    public ProjectTasks validateTaskNameByTaskId(Long taskId,String taskName,Long projectId)throws Exception;
    
    public List<ProjectTasks> getAllTasksByProjectIdAndTaskTypeAndTaskStatus(Long projectId,String taskType,String taskStatus)throws Exception;
    
    public List<ProjectTasks> getAllTasksByProjectIdAndParentTaskId(Long projectId,Long parentTaskId)throws Exception;
    
    public Long getProjectTasksGeneratedIdByProjectId(Long projectId,Integer generatedIdCount) throws Exception;
    
    Integer getProjectTasksCountByProjectId(Long projectId) throws Exception;
    
    Integer getProjectTasksListCountByProjectIdAndStatus(Long projectId,String  status) throws Exception;
    
    public List<ProjectTasks> getOverdueTaskListByProjectId(Long projectId, Date startDateUTC, Date endDateUTC)throws Exception;
    
    public List<ProjectTasks> getAllTasksByProjectIdAndFilter(Long projectId, Date startDateUTC, Date endDateUTC)throws Exception;
    
	ResponseList<ProjectTasks> getAllProjectTasksByProjectIdAndTaskTypeAndTaskStatus(ProjectTasksDTO projectTasksDTO,Long projectId,String taskType,String taskStatus,String userRole,String assignedMembers)throws Exception;

	public ProjectTasks getProjectTaskByleaveTypeId(long leaveTypeId, Long projectId);
	
	List<ProjectTasks> getTasksListByLeaveType(Long levaTypeId)throws Exception;
    
	List<ProjectTasks> getTasksListByLookupIdAndCategoryType(Long lookupId,String taskType)throws Exception;
	
	public ResponseList<ProjectTasks> getOverdueTaskReportListByOrglocationId(ProjectTasksDTO  projectTasksDTO, Date[] searchDates)throws Exception;


}