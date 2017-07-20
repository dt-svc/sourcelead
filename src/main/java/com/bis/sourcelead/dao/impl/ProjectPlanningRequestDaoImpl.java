package com.bis.sourcelead.dao.impl;

import java.util.List;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.ProjectPlanningRequestDao;
import com.bis.sourcelead.entity.ProjectPlanningRequest;

@Repository
public class ProjectPlanningRequestDaoImpl implements ProjectPlanningRequestDao{

private static final Logger logger = LoggerFactory.getLogger(ProjectPlanningRequestDaoImpl.class);
	
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
	public ProjectPlanningRequest saveProjectPlanningRequest(ProjectPlanningRequest projectPlanningRequest)
			throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (projectPlanningRequest != null) {
				session.saveOrUpdate(projectPlanningRequest);
			}
		} catch (Exception exception) {
			logger.error(ExceptionUtils.getFullStackTrace(exception));
		}
		return projectPlanningRequest;
	}

	@Override
	@Transactional
	public List<ProjectPlanningRequest> getProjectPlanningRequestByProjectId(Long projectId) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from ProjectPlanningRequest projectPlanningRequest left join fetch projectPlanningRequest.projectId "
				+ " where projectPlanningRequest.projectId = :projectId and projectPlanningRequest.status = '1' ";
		List<ProjectPlanningRequest> projectPlanningRequestList =  session.createQuery(query.toString()).setLong("projectId", projectId).setCacheable(true).list();
		
		return projectPlanningRequestList;
	}

	@Override
	@Transactional
	public Integer getProjectPlanningRequestCountByProjectId(Long projectId) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		
		String query ="select count(*)from ProjectPlanningRequest projectPlanningRequest where projectPlanningRequest.projectId = :projectId and projectPlanningRequest.status = '1'";
		
		List<Long> requestsCount =   session.createQuery(query.toString())
				                         .setLong("projectId", projectId)
				                         .setCacheable(true)
				                         .list();
			Integer count = 0;
			if(requestsCount != null && !requestsCount.isEmpty()){
					count = requestsCount.get(0).intValue();
			}
			return count;
	}
}
