package com.bis.sourcelead.service.impl;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.ProjectMembersDao;
import com.bis.sourcelead.dao.impl.ProjectsDaoImpl;
import com.bis.sourcelead.entity.ProjectMembers;
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

}
