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
		List<ProjectPlanningRequest> projectPlanningRequestList =  session.createQuery(query.toString()).setLong("projectId", projectId).list();
		
		return projectPlanningRequestList;
	}

	@Override
	@Transactional
	public Integer getProjectPlanningRequestCountByProjectId(Long projectId) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		
		String query ="select count(*)from ProjectPlanningRequest projectPlanningRequest where projectPlanningRequest.projectId = :projectId and projectPlanningRequest.status = '1'";
		
		List<Long> requestsCount =   session.createQuery(query.toString())
				                         .setLong("projectId", projectId)
				                         
				                         .list();
			Integer count = 0;
			if(requestsCount != null && !requestsCount.isEmpty()){
					count = requestsCount.get(0).intValue();
			}
			return count;
	}

	@Override
	@Transactional
	public ProjectPlanningRequest getProjectPlanningRequestByRequestUUID(String requestUUID) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from ProjectPlanningRequest planningRequest left join fetch planningRequest.projectId left join fetch planningRequest.approverUser "
				+ "where planningRequest.requestUUID = :requestUUID and planningRequest.status = '1' ";  
		List<ProjectPlanningRequest> projectPlanningRequestList = session.createQuery(query).setString("requestUUID", requestUUID).list();
		ProjectPlanningRequest projectPlanningRequest = null;
		if(projectPlanningRequestList != null && !projectPlanningRequestList.isEmpty()){
			projectPlanningRequest = projectPlanningRequestList.iterator().next();
		}
		return projectPlanningRequest;
	}

	@Override
	@Transactional
	public ProjectPlanningRequest getProjectPlanningRequestById(Long id) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from ProjectPlanningRequest planningRequest left join fetch planningRequest.projectId left join fetch planningRequest.approverUser "
				+ "where planningRequest.id = :id and planningRequest.status = '1' ";  
		List<ProjectPlanningRequest> projectPlanningRequestList = session.createQuery(query).setLong("id", id).list();
		ProjectPlanningRequest projectPlanningRequest = null;
		if(projectPlanningRequestList != null && !projectPlanningRequestList.isEmpty()){
			projectPlanningRequest = projectPlanningRequestList.iterator().next();
		}
		return projectPlanningRequest;
	}

	@Override
	@Transactional
	public List<ProjectPlanningRequest> getProjectPlanningRequestByApproverId(Long approverUserId, Long orgLocationId,String requestStatus,String userRole)
			throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		List<ProjectPlanningRequest> projectPlanningRequestList = null;
		
		if(!userRole.equalsIgnoreCase("ROLE_ADMIN") && !userRole.equalsIgnoreCase("ROLE_SOURCELEAD_ADMIN")) {
			String query = "from ProjectPlanningRequest projectPlanningRequest left join fetch projectPlanningRequest.projectId left join fetch projectPlanningRequest.approverUser "
					+ " where projectPlanningRequest.approverUser.id = :approverUserId and projectPlanningRequest.orgLocationId.id = :orgLocationId "
					+ " and projectPlanningRequest.requestStatus = :requestStatus and projectPlanningRequest.status = '1' ";
			 projectPlanningRequestList =  session.createQuery(query.toString()).setLong("approverUserId", approverUserId).setLong("orgLocationId", orgLocationId).setString("requestStatus", requestStatus).list();
			
		}else {
			String query = "from ProjectPlanningRequest projectPlanningRequest left join fetch projectPlanningRequest.projectId left join fetch projectPlanningRequest.approverUser "
					+ " where  projectPlanningRequest.orgLocationId.id = :orgLocationId "
					+ " and projectPlanningRequest.requestStatus = :requestStatus and projectPlanningRequest.status = '1' ";
			 projectPlanningRequestList =  session.createQuery(query.toString()).setLong("orgLocationId", orgLocationId).setString("requestStatus", requestStatus).list();
			
		}
		
		return projectPlanningRequestList;
	}

	@Override
	@Transactional
	public List<ProjectPlanningRequest> getProjectPlanningRequestByApproverIdAndOrgLocationId(Long approverUserId,Long orgLocationId,String userRole) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		List<ProjectPlanningRequest> projectPlanningRequestList = null;
		if(!userRole.equalsIgnoreCase("ROLE_ADMIN") && !userRole.equalsIgnoreCase("ROLE_SOURCELEAD_ADMIN")) {
			String query = "from ProjectPlanningRequest projectPlanningRequest left join fetch projectPlanningRequest.projectId left join fetch projectPlanningRequest.approverUser "
					+ " where projectPlanningRequest.approverUser.id = :approverUserId and projectPlanningRequest.orgLocationId.id = :orgLocationId "
					+ " and projectPlanningRequest.status = '1' ";
			 projectPlanningRequestList =  session.createQuery(query.toString()).setLong("approverUserId", approverUserId).setLong("orgLocationId", orgLocationId).list();
			
		}else {
			String query = "from ProjectPlanningRequest projectPlanningRequest left join fetch projectPlanningRequest.projectId left join fetch projectPlanningRequest.approverUser "
					+ " where projectPlanningRequest.orgLocationId.id = :orgLocationId "
					+ " and projectPlanningRequest.status = '1' ";
			 projectPlanningRequestList =  session.createQuery(query.toString()).setLong("orgLocationId", orgLocationId).list();
			
		}
		
		return projectPlanningRequestList;
	}
}
