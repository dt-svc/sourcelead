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

import com.bis.sourcelead.dao.ProjectPlanningNewResourceRequestDao;
import com.bis.sourcelead.entity.ProjectPlanningNewResourceRequest;
import com.bis.sourcelead.entity.ProjectPlanningResourceRequest;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;

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

	@Override
	@Transactional
	public void deleteProjectPlanningNewResourceRequest(ProjectPlanningNewResourceRequest projectPlanningNewResourceRequest) 	throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (projectPlanningNewResourceRequest != null) {
				session.delete(projectPlanningNewResourceRequest);
			}
			logger.debug("ProjectPlanningNewResourceRequest Deleted Successfully");
		} catch (Exception exception) {
			 StringBuilder message= new StringBuilder(MessageFormat.format(duplicateValue,"PROJECT_PLANNING_NEW_RESOURCE_REQUEST"));
				throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public ProjectPlanningNewResourceRequest getProjectPlanningNewResourceRequestById(Long id) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from ProjectPlanningNewResourceRequest resourceRequest left join fetch resourceRequest.projectPlanningRequestId "
				+ " where resourceRequest.id = :id and resourceRequest.status = '1' ";  
		List<ProjectPlanningNewResourceRequest> projectPlanningNewResourceRequestList = session.createQuery(query).setLong("id", id).setCacheable(true).list();
		ProjectPlanningNewResourceRequest projectPlanningNewResourceRequest = null;
		if(projectPlanningNewResourceRequestList != null && !projectPlanningNewResourceRequestList.isEmpty()){
			projectPlanningNewResourceRequest = projectPlanningNewResourceRequestList.iterator().next();
		}
		return projectPlanningNewResourceRequest;
	}

	@Override
	@Transactional
	public ProjectPlanningNewResourceRequest saveProjectPlanningNewResourceRequest(ProjectPlanningNewResourceRequest projectPlanningNewResourceRequest) throws Exception {
			Session session = this.sessionFactory.getCurrentSession();
			try {
				if (projectPlanningNewResourceRequest != null) {
					session.saveOrUpdate(projectPlanningNewResourceRequest);
				}
				logger.debug("ProjectPlanningNewResourceRequest Saved Successfully");
			} catch (Exception exception) {
				 StringBuilder message= new StringBuilder(MessageFormat.format(duplicateValue,"PROJECT_PLANNING_NEW_RESOURCE_REQUEST"));
					throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
			}
		return projectPlanningNewResourceRequest;
	}

	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public ProjectPlanningNewResourceRequest getProjectPlanningNewResourceRequestByRequestUUID(String requestUUID) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from ProjectPlanningNewResourceRequest resourceRequest left join fetch resourceRequest.projectPlanningRequestId  where resourceRequest.requestUUID = :requestUUID and resourceRequest.status = '1' ";  
		List<ProjectPlanningNewResourceRequest> newResourceRequestList = session.createQuery(query).setString("requestUUID", requestUUID).setCacheable(true).list();
		ProjectPlanningNewResourceRequest projectPlanningNewResourceRequest = null;
		if(newResourceRequestList != null && !newResourceRequestList.isEmpty()){
			projectPlanningNewResourceRequest = newResourceRequestList.iterator().next();
		}
		return projectPlanningNewResourceRequest;
	}

}
