package com.bis.sourcelead.service.impl;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.ProjectMembersDao;
import com.bis.sourcelead.dao.impl.ProjectsDaoImpl;
import com.bis.sourcelead.entity.ProjectMembers;
import com.bis.sourcelead.entity.ProjectMembersHistory;
import com.bis.sourcelead.service.ProjectMembersService;

@Service
public class ProjectMembersServiceImpl implements ProjectMembersService{

	
	@Autowired
	private ProjectMembersDao projectMembersDao;
	
	
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
		// TODO Auto-generated method stub
		
		List<Long> projectMembersList = projectMembersDao.getAllProjectMembersIdsByemployeeId(userId);
		return projectMembersList;
		
	}
	
	
	@Override
	public List<ProjectMembers> getAllProjectMembersIdsByTimesheetApproverUserIdOrMemberUserId(Long userId) throws Exception {
		// TODO Auto-generated method stub
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
	public List<ProjectMembers> getAllProjectsByUserIdAndStatus(Long userId, String status) {
		return projectMembersDao.getAllProjectsByUserIdAndStatus(userId,status);
	}
	@Override
	public Integer updateEndDateOfProjectMemberId(Date endDate, Long projectMemberId) {
		return projectMembersDao.updateEndDateOfProjectMemberId(endDate,projectMemberId);
	}

	@Override
	public Integer getEmployeeAssignedProjectsCount(Long orgRelationId, Long orgLocationId) {
		// TODO Auto-generated method stub
		return projectMembersDao.getEmployeeAssignedProjectsCount(orgRelationId, orgLocationId);
	}


	
}