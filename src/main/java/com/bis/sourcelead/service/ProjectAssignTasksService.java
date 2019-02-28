package com.bis.sourcelead.service;

import java.util.List;
import com.bis.sourcelead.dto.ProjectTasksDTO;
import com.bis.sourcelead.dto.ProjectassignTasksDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.ProjectAssignTask;

public interface ProjectAssignTasksService {

     ProjectAssignTask saveProjectAssignTask(ProjectAssignTask projectAssignTask) throws Exception;
	
	 ProjectAssignTask getProjectAssignTaskById(Long projectTaskId) throws Exception;
	 
	 ProjectAssignTask getProjectAssignTaskByIdAndMemberId(Long projectTaskId,Long memberId) throws Exception;
	
	 public List<ProjectAssignTask> getProjectAssignTaskListByProjectTaskId(Long projectTaskId) throws Exception;
	
	 List<Long> getProjectAssignTaskListOfLongByProjectTaskId(Long projectTaskId,List<Long> assignedMembersIds) throws Exception;
	
	 ProjectAssignTask deleteProjectAssignTask(ProjectAssignTask ProjectAssignTask) throws Exception;

	void inlineUpdateAssignTaskUsers(ProjectTasksDTO projectTasksDTO,String assignValue, String projectTaskId, Long userId,OrgLocations loginOrgLocation)throws Exception;
	
	List<ProjectassignTasksDTO> getProjectAssignTaskListByOrglocationId(Long orglocationId) throws Exception;
	
	ResponseList<ProjectAssignTask> getProjectAssignTaskListByCandidateIdOrglocationId(ProjectassignTasksDTO projectassignTasksDTO) throws Exception;

}
