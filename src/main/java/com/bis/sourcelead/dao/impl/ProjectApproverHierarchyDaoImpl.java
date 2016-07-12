package com.bis.sourcelead.dao.impl;

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
	
}
