package com.bis.sourcelead.dao;

import java.util.List;

import com.bis.sourcelead.entity.ProjectAssignTask;

public interface ProjectAssignTasksDao {
	ProjectAssignTask saveProjectAssignTask(ProjectAssignTask projectAssignTask) throws Exception;
	
	 ProjectAssignTask getProjectAssignTaskById(Long projectTaskId) throws Exception;
	
	 ProjectAssignTask getProjectAssignTaskByIdAndMemberId(Long projectTaskId,Long memberId) throws Exception;
	 
	 public List<ProjectAssignTask> getProjectAssignTaskListByProjectTaskId(Long projectTaskId) throws Exception;
	
	 List<Long> getProjectAssignTaskListOfLongByProjectTaskId(Long projectTaskId) throws Exception;
	
	 ProjectAssignTask deleteProjectAssignTask(ProjectAssignTask ProjectAssignTask) throws Exception;
}
