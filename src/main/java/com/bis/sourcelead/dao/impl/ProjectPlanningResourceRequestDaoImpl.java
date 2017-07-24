package com.bis.sourcelead.dao.impl;

import java.text.MessageFormat;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.ProjectPlanningResourceRequestDao;
import com.bis.sourcelead.entity.ProjectPlanningResourceRequest;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;

@Repository
public class ProjectPlanningResourceRequestDaoImpl implements ProjectPlanningResourceRequestDao{

private static final Logger logger = LoggerFactory.getLogger(ProjectPlanningResourceRequestDaoImpl.class);
	
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
	public List<Long> getProjectPlanningResourceRequestIdsByRequestId(Long projectPlanningRequestId) throws Exception {
		
		logger.debug("In ProjectPlanningResourceRequestDaoImpl class getProjectPlanningResourceRequestIdsByRequestId method");
		Session session = this.sessionFactory.getCurrentSession();
			 
		String query = "select resourceRequest.id from ProjectPlanningResourceRequest resourceRequest where resourceRequest.projectPlanningRequestId.id = :projectPlanningRequestId ";
		List<Long> lineItemsIdsList =  session.createQuery(query.toString()).setLong("projectPlanningRequestId", projectPlanningRequestId)
									.setCacheable(true).list();
		
		return lineItemsIdsList;
	}

	@Override
	@Transactional
	public ProjectPlanningResourceRequest getProjectPlanningResourceRequestById(Long id) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from ProjectPlanningResourceRequest resourceRequest left join fetch resourceRequest.projectPlanningRequestId "
				+ " where resourceRequest.id = :id and resourceRequest.status = '1' ";  
		List<ProjectPlanningResourceRequest> projectPlanningResourceRequestList = session.createQuery(query).setLong("id", id).setCacheable(true).list();
		ProjectPlanningResourceRequest projectPlanningResourceRequest = null;
		if(projectPlanningResourceRequestList != null && !projectPlanningResourceRequestList.isEmpty()){
			projectPlanningResourceRequest = projectPlanningResourceRequestList.iterator().next();
		}
		return projectPlanningResourceRequest;
	}

	@Override
	@Transactional
	public void deleteProjectPlanningResourceRequest(ProjectPlanningResourceRequest projectPlanningResourceRequest)
			throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (projectPlanningResourceRequest != null) {
				session.delete(projectPlanningResourceRequest);
			}
			logger.debug("ProjectPlanningResourceRequest Deleted Successfully");
		} catch (Exception exception) {
			 StringBuilder message= new StringBuilder(MessageFormat.format(duplicateValue,"PROJECT_PLANNING_RESOURCE_REQUEST"));
				throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		
	}
}
