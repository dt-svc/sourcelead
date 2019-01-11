package com.bis.sourcelead.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.dto.ProjectTasksDTO;
import com.bis.sourcelead.dto.ProjectassignTasksDTO;
import com.bis.sourcelead.dto.ResponseList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.ProjectAssignTasksDao;
import com.bis.sourcelead.entity.ProjectAssignTask;
import com.bis.sourcelead.entity.ProjectMembers;
import com.bis.sourcelead.entity.ProjectTasks;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.ProjectAssignTasksService;
import com.bis.sourcelead.service.ProjectMembersService;
import com.bis.sourcelead.service.ProjectTaskActivityService;

import com.bis.sourcelead.service.ProjectTasksService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;

@Service
public class ProjectAssignTasksServiceImpl implements ProjectAssignTasksService{
	
	@Autowired
	ProjectAssignTasksDao projectAssignTasksDao;
  
	@Autowired
    ProjectTasksService projectTasksService;
		
	@Autowired
	HttpServletRequest request;
	
	 @Autowired
	 CommonUtil commonUtil;
	 
	 @Autowired
	 public ProjectMembersService projectMembersService;
	
	 @Autowired
	 private ProjectTasksService tasksService;
	 
	 @Autowired
	 ProjectTaskActivityService projectTaskActivityService;
	 
	 @Autowired
	 UserServiceImpl userServiceImpl;
	
	@Override
	public ProjectAssignTask saveProjectAssignTask(ProjectAssignTask projectAssignTask) throws Exception {
		return projectAssignTasksDao.saveProjectAssignTask(projectAssignTask);
	}

	@Override
	public ProjectAssignTask getProjectAssignTaskById(Long projectTaskId) throws Exception {
		return projectAssignTasksDao.getProjectAssignTaskById(projectTaskId);
	}

	@Override
	public List<ProjectAssignTask> getProjectAssignTaskListByProjectTaskId(Long projectTaskId) throws Exception {
		return projectAssignTasksDao.getProjectAssignTaskListByProjectTaskId(projectTaskId);
	}

	@Override
	public List<Long> getProjectAssignTaskListOfLongByProjectTaskId(Long projectTaskId,List<Long> assignedMembersIds) throws Exception {
		return projectAssignTasksDao.getProjectAssignTaskListOfLongByProjectTaskId(projectTaskId,assignedMembersIds);
	}

	@Override
	public ProjectAssignTask deleteProjectAssignTask(ProjectAssignTask ProjectAssignTask) throws Exception {
		return projectAssignTasksDao.deleteProjectAssignTask(ProjectAssignTask);
	}

	@Override
	public ProjectAssignTask getProjectAssignTaskByIdAndMemberId(Long projectTaskId, Long memberId) throws Exception {
		return projectAssignTasksDao.getProjectAssignTaskByIdAndMemberId(projectTaskId, memberId);
	}

	@Override
	public void inlineUpdateAssignTaskUsers(ProjectTasksDTO projectTasksDTO,String assignValue, String projectTaskId ,Long userId,OrgLocations orgLocations)throws Exception {
		User user = userServiceImpl.getUserById(userId);
		StringBuffer activity  = null;
		 StringBuffer activitySummary = null;
		 String[] assignid = assignValue.split(",");
		 List<Long> assignUserids = new ArrayList<Long>();
			 if(!assignValue.isEmpty() && assignid.length > 0) {
				 for(int i=0; i<assignid.length;i++) {
					 assignUserids.add(Long.valueOf(assignid[i]));
				 }
			 }
		 
	     List<Long> assignedMembersIds = projectMembersService.getAllProjectMembersIdsByProjectIdAndOrgLocationId(Long.valueOf(projectTasksDTO.getProjectId()), orgLocations.getId());
		 List<Long> updatedResourceRequests = projectAssignTasksDao.getProjectAssignTaskListOfLongByProjectTaskId(Long.valueOf(projectTaskId),assignedMembersIds);
		 ArrayList<Long> removedMemberIds = new ArrayList<>();
		 removedMemberIds.addAll(updatedResourceRequests);
		 removedMemberIds.removeAll(assignUserids);
		 
		 assignUserids.removeAll(updatedResourceRequests);
		 if(assignUserids.size() > 0) {
			 for (Long assignedMemeber :assignUserids) {
				 
					ProjectMembers pm = projectMembersService.getProjectMemberById(assignedMemeber);
					List<ProjectMembers> pmList = projectMembersService.getAllProjectMembersListByMasterCandidateId(pm.getMasterCandidateId().getId(), Long.valueOf(projectTasksDTO.getProjectId()));
							if(pmList != null && !pmList.isEmpty()) {
								for(ProjectMembers members : pmList) {
									ProjectAssignTask assignTask = new ProjectAssignTask();
									 ProjectTasks projectTask = tasksService.getTasksId(projectTaskId);
									assignTask.setProjectTaskId(projectTask);
									
									assignTask.setProjectMemberId(members);
									assignTask.setStatus(Constants.MEMBER_ACTIVE);
									
									assignTask.setCreatedDate(CommonUtil.currentDate());
									assignTask.setCreatedBy(user.getUserCode());
									assignTask.setUpdatedDate(CommonUtil.currentDate());
									assignTask.setUpdatedBy(user.getUserCode());
									projectAssignTasksDao.saveProjectAssignTask(assignTask);
									if(orgLocations.getId().longValue() == members.getProjectMemberCandidteId().getOrgLocationId().getId()) {
										String redirectURL = "/projectTaskSummary-params-taskUUID="+projectTask.getProjectTasksUUID()+"-and-projectClientUUID="+projectTasksDTO.getProjectClientUUID();
										long redirectOrgLocationId = projectTask.getProjectId().getOrgLocationsId().getId();
										projectTasksService.SendMailtoAssigntoMembersaboutAddingTask(assignTask,projectTasksDTO,projectTask,orgLocations,user, redirectURL, redirectOrgLocationId);
										 //this code is Assigned to user Activity
										 activity = new StringBuffer();
									     activitySummary = new StringBuffer();
										 activity.append("Added Assigned User");
										 activitySummary.append(members.getCandidateName());
										 projectTaskActivityService.saveProjectTaskActivities(activity.toString(), activitySummary.toString(),projectTask,user);
									}
								}
							}
			 }
		 }
		 
		 if(removedMemberIds != null && !removedMemberIds.isEmpty()){
				for(Long memberId : removedMemberIds){
					ProjectMembers pm = projectMembersService.getProjectMemberById(memberId);
					ProjectTasks tasks = projectTasksService.getTasksId(projectTaskId);
					List<ProjectMembers> pmList = projectMembersService.getAllProjectMembersListByMasterCandidateId(pm.getMasterCandidateId().getId(), tasks.getProjectId().getId());
					if(pmList != null && !pmList.isEmpty()) {
						for(ProjectMembers members : pmList) {
							ProjectAssignTask assignUs = projectAssignTasksDao.getProjectAssignTaskByIdAndMemberId(Long.valueOf(projectTaskId), members.getId());
							if(assignUs != null) {
							 projectAssignTasksDao.deleteProjectAssignTask(assignUs);
							 activity = new StringBuffer();
						     activitySummary = new StringBuffer();
							 activity.append("Deleted Assigned User");
							 activitySummary.append(members.getCandidateName());
							 projectTaskActivityService.saveProjectTaskActivities(activity.toString(), activitySummary.toString(),tasks,user);
							}
						}
					}
				}
			}
	}

	@Override
	public List<ProjectassignTasksDTO> getProjectAssignTaskListByOrglocationId(Long orglocationId) throws Exception {
		return projectAssignTasksDao.getProjectAssignTaskListByOrglocationId(orglocationId);
	}

	@Override
	public ResponseList<ProjectAssignTask> getProjectAssignTaskListByCandidateIdOrglocationId(ProjectassignTasksDTO projectassignTasksDTO) throws Exception {
		return projectAssignTasksDao.getProjectAssignTaskListByCandidateIdOrglocationId(projectassignTasksDTO);
	}
	
	 
}
