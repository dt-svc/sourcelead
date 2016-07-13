package com.bis.sourcelead.dao.impl;

import java.util.Collection;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.ProjectApproverHierarchyDao;
import com.bis.sourcelead.entity.ProjectApproverHierarchy;

@Repository
public class ProjectApproverHierarchyDaoImpl implements ProjectApproverHierarchyDao{

	@Autowired
	private SessionFactory sessionFactory;
	
	private static final Logger logger = LoggerFactory.getLogger(ProjectApproverHierarchyDaoImpl.class);
	
	@Override
	@Transactional(rollbackFor = { Exception.class })	
	public ProjectApproverHierarchy saveProjectApproverHierarchy(ProjectApproverHierarchy projectApproverHierarchy)	throws Exception {
		// TODO Auto-generated method stub
			logger.debug("In Projects Save method");
			Session session = this.sessionFactory.getCurrentSession();
			session.save(projectApproverHierarchy);
			return projectApproverHierarchy;
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class })	
	public ProjectApproverHierarchy getLevelOneProjectApproverByProjectId(Long ProjectId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		logger.debug("In ProjectApproverHierarchy getProjectApproverHierarchyByProjectId method");
		String query = "from ProjectApproverHierarchy projectHirachy left join fetch projectHirachy.approverUserId where projectHirachy.projectId = :ProjectId and projectHirachy.level = 'Level1' ";
		Collection<ProjectApproverHierarchy> projectApproverHierarchies = session.createQuery(query.toString()).setLong("ProjectId", ProjectId).list();

		ProjectApproverHierarchy ph = null;
		if (projectApproverHierarchies != null && projectApproverHierarchies.size() > 0) {
			ph = (		ProjectApproverHierarchy) projectApproverHierarchies.iterator().next();
		}
		return ph;

	}
	

	@Override
	@Transactional(rollbackFor = { Exception.class })	
	public Collection<ProjectApproverHierarchy> getApproverUsersByProjectId(long projectId) throws Exception {
		// TODO Auto-generated method stub
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from  ProjectApproverHierarchy projectHirachy  left join fetch projectHirachy.approverUserId  where   projectHirachy.projectId =:projectId";
	        List<ProjectApproverHierarchy>  projectHirachyList =   session.createQuery(query.toString()).setLong("projectId", projectId).list();
	        return projectHirachyList;
		
	}
	
}
