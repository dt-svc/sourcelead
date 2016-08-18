package com.bis.sourcelead.service.impl;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.ProjectMembersDao;
import com.bis.sourcelead.dao.impl.ProjectsDaoImpl;
import com.bis.sourcelead.entity.ProjectMembers;
import com.bis.sourcelead.entity.ProjectMembersHistory;
import com.bis.sourcelead.enums.Primary;
import com.bis.sourcelead.service.ProjectMembersHistoryService;
import com.bis.sourcelead.service.ProjectMembersService;
import com.bis.sourcelead.service.SubmissionApproverHierarchyService;

@Service
public class ProjectMembersServiceImpl implements ProjectMembersService{

	@Autowired
	public SubmissionApproverHierarchyService submissionApproverHierarchyService;
	
	@Autowired
	private ProjectMembersDao projectMembersDao;
	
	@Autowired
	private ProjectMembersHistoryService projectMembersHistoryService;
	
	
	private static final Logger logger = LoggerFactory.getLogger(ProjectsDaoImpl.class);
	
	@Override
	public ProjectMembers saveProjectMember(ProjectMembers projectMembers) throws Exception{
		logger.debug("In ProjectMembersServiceImpl class saveProjectMember method");
		return projectMembersDao.saveProjectMember(projectMembers);
	}

	@Override
	public ProjectMembers updateProjectMember(ProjectMembers projectMembers) throws Exception {
		logger.debug("In ProjectMembersServiceImpl class updateProjectMember method...");
		return projectMembersDao.updateProjectMember(projectMembers);
	}

	@Override
	public ProjectMembers getProjectMemberById(Long id) throws Exception {
		logger.debug("In ProjectMembersServiceImpl class getProjectMember method...");
		return projectMembersDao.getProjectMemberById(id);
	}

	@Override
	public Boolean deleteProjectMemberById(Long id) throws Exception {
		logger.debug("In ProjectMembersServiceImpl class deleteProjectMember method...");
		Boolean isDeleted =  projectMembersDao.deleteProjectMemberById(id);
		return isDeleted;
	}

	@Override
	public Collection<ProjectMembers> getAllProjectMembers() throws Exception {
		logger.debug("In ProjectMembersServiceImpl class getAllProjectMembers method...");
		Collection<ProjectMembers> projectMembersList = projectMembersDao.getAllProjectMembers();
		return projectMembersList;
	}
	
	@Override
	public Collection<ProjectMembers> getAllProjectMembersByProjectId(Long id) throws Exception {
		logger.debug("In ProjectMembersServiceImpl class getAllProjectMembersByProjectId method...");
		Collection<ProjectMembers> projectMembersList = projectMembersDao.getAllProjectMembersByProjectId(id);
		return projectMembersList;
	}

	@Override
	public List<Long> getAllProjectMembersIdsByemployeeId(Long userId) throws Exception {
		
		List<Long> projectMembersList = projectMembersDao.getAllProjectMembersIdsByemployeeId(userId);
		return projectMembersList;
		
	}
	
	
	@Override
	public List<ProjectMembers> getAllProjectMembersIdsByTimesheetApproverUserIdOrMemberUserId(Long userId) throws Exception {
		List<ProjectMembers> projectMembersList = projectMembersDao.getAllProjectMembersIdsByTimesheetApproverUserIdOrMemberUserId(userId);
		return projectMembersList;
		
	}

	@Override
	public List<ProjectMembers> getAllProjectsByUserId(Long userId) {
		return projectMembersDao.getAllProjectsByUserId(userId);
	}


	@Override
	public Integer getAllProjectMembersCountByProjectId(Long id) throws Exception {
		
		Integer projectMembersCount = projectMembersDao.getAllProjectMembersCountByProjectId(id);
		
		return projectMembersCount;
	}
	

	@Override
	public Integer updateProjectMemberDeletedReason(Long projectMemberId ,String reason) {
		return projectMembersDao.updateProjectMemberDeletedReason(projectMemberId,reason);
	}

	@Override
	public List<ProjectMembers> getAllProjectsByUserIdAndStatus(Long userId, String filterValue) throws Exception {
		List<ProjectMembers> allProjectsList = null;
		if(filterValue.equals("All")){
			 allProjectsList = projectMembersDao.getAllProjectsByUserId(userId);
		}
		if(filterValue.equals("Active")){
			 allProjectsList = projectMembersDao.getAllProjectsByUserIdAndStatus(userId,Constants.MEMBER_ACTIVE);
		}
		if(filterValue.equals("In-Active")){
			allProjectsList = projectMembersDao.getAllProjectsByUserIdAndStatus(userId,Constants.MEMBER_IN_ACTIVE);
		}
		for (ProjectMembers projectMembers : allProjectsList) {
			projectMembers.setProjectName(projectMembers.getProjects().getProjectName());
			 ProjectMembersHistory pmh = projectMembersHistoryService.getProjectMembersOpenHistoryByProjectMemberId(projectMembers.getId());
			 if(pmh == null){
				 pmh = projectMembersHistoryService.getProjectMembersRecentClosedByProjectMemberId(projectMembers.getId());
			 }
			 if(pmh != null && pmh.getProjectAssignedDate()!=null){
				 projectMembers.setStartDate(Constants.DF_YMD.format(pmh.getProjectAssignedDate()));
             }
        	 if(pmh != null && pmh.getProjectTerminationDate()!=null){
        		 projectMembers.setEndDate(Constants.DF_YMD.format(pmh.getProjectTerminationDate()));
        	 }
        	 
        	 /*projectMembers.setStartDate(com.bis.sourcelead.constants.Constants.DF_YMD.format(projectMembers.getProjectAssignedDate()));
			if (projectMembers.getProjectEndDate() != null) {
				projectMembers.setEndDate(com.bis.sourcelead.constants.Constants.DF_YMD.format(projectMembers.getProjectEndDate()));
			}*/
			if (projectMembers.getProjects().getEndDate() != null) {
				projectMembers.setProjectEndDateStr(com.bis.sourcelead.constants.Constants.DF_YMD.format(projectMembers.getProjects().getEndDate()));
			}
	        Float draftTimesheetsSubmissionsCountAsEmployee = submissionApproverHierarchyService.getTotalDraftAndSubmittedHoursByuserIdAndProjectIdForEmployee(userId, projectMembers.getProjects().getId(), "DRAFT");
	        Float draftTimesheetsSubmissionsCountAsApprover = submissionApproverHierarchyService.getTotalDraftAndSubmittedHoursByuserIdAndProjectIdForApprover(userId, projectMembers.getProjects().getId(), "DRAFT");
	        Float draftTimesheetsSubmissionsCount =  draftTimesheetsSubmissionsCountAsEmployee + draftTimesheetsSubmissionsCountAsApprover;
	        projectMembers.setDraftHrs(draftTimesheetsSubmissionsCount.toString());

	        Float submittedTimesheetsSubmissionsCountAsEmployee = submissionApproverHierarchyService.getTotalDraftAndSubmittedHoursByuserIdAndProjectIdForEmployee(userId, projectMembers.getProjects().getId(), "SUBMITTED");
	        Float submittedTimesheetsSubmissionsCountAsApprover = submissionApproverHierarchyService.getTotalDraftAndSubmittedHoursByuserIdAndProjectIdForApprover(userId, projectMembers.getProjects().getId(), "SUBMITTED");
	        Float submittedTimesheetsSubmissionsCount =  submittedTimesheetsSubmissionsCountAsEmployee + submittedTimesheetsSubmissionsCountAsApprover;
			projectMembers.setSubmittedHrs(submittedTimesheetsSubmissionsCount.toString());
			
		    Float approvalTimesheetsSubmissionsCountAsEmployee = submissionApproverHierarchyService.getTotalApprovedOrRejectedHoursByuserIdAndProjectIdForEmployee(userId, projectMembers.getProjects().getId(), "APPROVED");
		    Float approvalTimesheetsSubmissionsCountAsApprover = submissionApproverHierarchyService.getTotalApprovedOrRejectedHoursByuserIdAndProjectIdForApprover(userId, projectMembers.getProjects().getId(), "APPROVED");
	        Float approvalTimesheetsSubmissionsCount =  approvalTimesheetsSubmissionsCountAsEmployee + approvalTimesheetsSubmissionsCountAsApprover;
			projectMembers.setApprovedHrs(approvalTimesheetsSubmissionsCount.toString());
			
			Float rejectedTimesheetsSubmissionsCountAsEmployee = submissionApproverHierarchyService.getTotalApprovedOrRejectedHoursByuserIdAndProjectIdForEmployee(userId, projectMembers.getProjects().getId(), "REJECTED");
		    Float rejectedTimesheetsSubmissionsCountAsApprover = submissionApproverHierarchyService.getTotalApprovedOrRejectedHoursByuserIdAndProjectIdForApprover(userId, projectMembers.getProjects().getId(), "REJECTED");
		    Float rejectedTimesheetsSubmissionsCount =  rejectedTimesheetsSubmissionsCountAsEmployee + rejectedTimesheetsSubmissionsCountAsApprover;
		    projectMembers.setRejectedHrs(rejectedTimesheetsSubmissionsCount.toString());

		}
		return allProjectsList;
	}
	@Override
	public Integer updateEndDateOfProjectMemberId(Date endDate, Long projectMemberId) {
		return projectMembersDao.updateEndDateOfProjectMemberId(endDate,projectMemberId);
	}

	@Override
	public Integer getEmployeeAssignedProjectsCount(Long orgRelationId, Long orgLocationId , Long projectId) {
		return projectMembersDao.getEmployeeAssignedProjectsCount(orgRelationId, orgLocationId ,projectId);
	}

	@Override
	public List<ProjectMembers> getAllTerminatedProjectMembersIdsByTimesheetApproverUserIdOrMemberUserId(Long userId)
			throws Exception {
		// TODO Auto-generated method stub
		return projectMembersDao.getAllTerminatedProjectMembersIdsByTimesheetApproverUserIdOrMemberUserId(userId);
	}

	@Override
	public List<ProjectMembers> getAllTerminatedProjectMembersByProjectIdAndterminatedStatus(Long projectId,Primary isManuallyTerminated) throws Exception {
		return projectMembersDao.getAllTerminatedProjectMembersByProjectIdAndterminatedStatus(projectId,isManuallyTerminated);
	}


	
}