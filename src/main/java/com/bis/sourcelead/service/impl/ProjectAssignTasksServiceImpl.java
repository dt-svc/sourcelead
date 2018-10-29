package com.bis.sourcelead.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.dto.ProjectTasksDTO;
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
	public List<Long> getProjectAssignTaskListOfLongByProjectTaskId(Long projectTaskId) throws Exception {
		return projectAssignTasksDao.getProjectAssignTaskListOfLongByProjectTaskId(projectTaskId);
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
	public void inlineUpdateAssignTaskUsers(ProjectTasksDTO projectTasksDTO,String assignValue, String projectTaskId ,Long userId)throws Exception {
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
		 
		 List<Long> updatedResourceRequests = projectAssignTasksDao.getProjectAssignTaskListOfLongByProjectTaskId(Long.valueOf(projectTaskId));
		 ArrayList<Long> removedMemberIds = new ArrayList<>();
		 removedMemberIds.addAll(updatedResourceRequests);
		 removedMemberIds.removeAll(assignUserids);
		 
		 assignUserids.removeAll(updatedResourceRequests);
		 if(assignUserids.size() > 0) {
			 for (Long assignedMemeber :assignUserids) {
				 ProjectAssignTask  assignTasks = new ProjectAssignTask();
				 ProjectTasks projectTask = tasksService.getTasksId(projectTaskId);
				 assignTasks.setProjectTaskId(projectTask);
				 ProjectMembers pm = projectMembersService.getProjectMemberById(assignedMemeber);
				 assignTasks.setProjectMemberId(pm);
				 if(pm.getProjectMemberCandidteId() != null) {
					 if(pm.getProjectMemberCandidteId().getConsultantUser() != null) {
						 assignTasks.setCreatedBy(pm.getProjectMemberCandidteId().getConsultantUser().getUserCode());
					 }
				 }
				 assignTasks.setStatus(Constants.RECORD_ACTIVE.toString());
				 assignTasks.setCreatedDate(commonUtil.currentDate());
				 projectAssignTasksDao.saveProjectAssignTask(assignTasks);
				 OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
				 		
				 projectTasksService.SendMailtoAssigntoMembersaboutAddingTask(assignTasks,projectTasksDTO,projectTask,loginOrgLocation,user);
				  				 
				 //this code is Assigned to user Activity
				 activity = new StringBuffer();
			     activitySummary = new StringBuffer();
				 activity.append("Add Assigned User");
				 activitySummary.append(assignTasks.getProjectMemberId().getEmployee().getUser().getFullName());
				 projectTaskActivityService.saveProjectTaskActivities(activity.toString(), activitySummary.toString(),projectTask,user);
			 }
		 }
		 
		 if(removedMemberIds != null && !removedMemberIds.isEmpty()){
				for(Long memberId : removedMemberIds){
					 ProjectAssignTask assignUs = projectAssignTasksDao.getProjectAssignTaskByIdAndMemberId(Long.valueOf(projectTaskId), memberId);
					 projectAssignTasksDao.deleteProjectAssignTask(assignUs);
					 
					 activity = new StringBuffer();
				     activitySummary = new StringBuffer();
					 activity.append("Delete Assigned User");
					 activitySummary.append(assignUs.getProjectMemberId().getEmployee().getUser().getFullName());
					 projectTaskActivityService.saveProjectTaskActivities(activity.toString(), activitySummary.toString(),assignUs.getProjectTaskId(),user);
				}
			}
		 
	}
	
	 
}
