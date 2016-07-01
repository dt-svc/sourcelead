package com.bis.sourcelead.dao.impl;

import java.util.Collection;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.ProjectMembersDao;
import com.bis.sourcelead.entity.ProjectMembers;

@Repository
public class ProjectMembersDaoImpl implements ProjectMembersDao{
	
	@Autowired
	private SessionFactory sessionFactory;
	
	private static final Logger logger = LoggerFactory.getLogger(ProjectMembersDaoImpl.class);

	/**
	 * This method for save project member
	 */
	@Override
	@Transactional(rollbackFor = { Exception.class })	
	public ProjectMembers saveProjectMember(ProjectMembers projectMembers) throws Exception {
		logger.debug("In ProjectMember Save method");
		Session session = this.sessionFactory.getCurrentSession();
		session.save(projectMembers);
		return projectMembers;
	}

	/**
	 * This method for update project member
	 */
	@Override
	@Transactional(rollbackFor = { Exception.class })	
	public ProjectMembers updateProjectMember(ProjectMembers projectMembers) throws Exception {
		logger.debug("In ProjectMember Update method");
		Session session = this.sessionFactory.getCurrentSession();
		session.saveOrUpdate(projectMembers);
		logger.info("Record updated successfully..!");
		return projectMembers;
	}

	/**
	 * This method for get project member by id
	 */
	@Override
	@Transactional(rollbackFor = { Exception.class })	
	public ProjectMembers getProjectMemberById(Long id) throws Exception {
		logger.debug("In ProjectMember getProjectMember method");
		Session session = this.sessionFactory.getCurrentSession();
		ProjectMembers projectMembers = session.get(ProjectMembers.class, id);
        return session.get(ProjectMembers.class, id);
	}

	/**
	 * This method for delete project member by id
	 * Here we are doing soft deletion only, record not deleted from DB just we have to change the status
	 */
	@Override
	@Transactional(rollbackFor = { Exception.class })	
	public Boolean deleteProjectMemberById(Long id) throws Exception {
		 logger.debug("In ProjectMember deleteProjectMember method");
		 Session session = this.sessionFactory.getCurrentSession();
		  boolean isDeleted = false;
		  if(id != null){
			  ProjectMembers projectMembers= (ProjectMembers)session.load(ProjectMembers.class,id);
			  projectMembers.setStatus("0");
			  session.saveOrUpdate(projectMembers);
			  logger.info("Successfully deleted !!!"); 
			  isDeleted = true;
		  }
		return isDeleted;
	}

	/**
	 * This method for get all the project members
	 */
	@Override
	@Transactional(rollbackFor = { Exception.class })	
	public Collection<ProjectMembers> getAllProjectMembers() throws Exception {
		logger.debug("In ProjectMember getAllProjectMembers method");
        Session session = this.sessionFactory.getCurrentSession();
        Criteria crit = session.createCriteria(ProjectMembers.class);
        List<ProjectMembers> projectsMembersList = crit.list();
        return projectsMembersList;
	}

}
