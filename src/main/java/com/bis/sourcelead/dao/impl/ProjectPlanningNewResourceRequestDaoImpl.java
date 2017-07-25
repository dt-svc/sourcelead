package com.bis.sourcelead.dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.ProjectPlanningNewResourceRequestDao;

@Repository
public class ProjectPlanningNewResourceRequestDaoImpl implements ProjectPlanningNewResourceRequestDao{

private static final Logger logger = LoggerFactory.getLogger(ProjectPlanningNewResourceRequestDaoImpl.class);
	
	@Autowired
	SessionFactory sessionFactory;
	
	@Value("${SourceLead.100}")
	private String notFound;
	
	@Value("${SourceLead.101}")
	private String noRecords;
	
	@Value("${SourceLead.102}")
	private String statusUpdate;
	
	@Value("${SourceLead.103}")
	private String statusDelete;
	
	@Value("${SourceLead.106}")
	private String duplicateValue;

	@Override
	@Transactional
	public List<Long> getProjectPlanningNewResourceRequestIdsByRequestId(Long projectPlanningRequestId) throws Exception {
		
		logger.debug("In ProjectPlanningResourceRequestDaoImpl class getProjectPlanningResourceRequestIdsByRequestId method");
		Session session = this.sessionFactory.getCurrentSession();
			 
		String query = "select resourceRequest.id from ProjectPlanningNewResourceRequest resourceRequest where resourceRequest.projectPlanningRequestId.id = :projectPlanningRequestId ";
		@SuppressWarnings("unchecked")
		List<Long> lineItemsIdsList =  session.createQuery(query.toString()).setLong("projectPlanningRequestId", projectPlanningRequestId)
									.setCacheable(true).list();
		
		return lineItemsIdsList;
	}


}
