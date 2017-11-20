package com.bis.sourcelead.dao.impl;

import java.text.MessageFormat;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.ProjectClientDao;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.ProjectClients;
import com.bis.sourcelead.entity.ProjectMembers;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;

@Repository
public class ProjectClientDaoImpl implements ProjectClientDao{

	@Autowired
	private SessionFactory sessionFactory;
	
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
	
	private static final Logger logger = LoggerFactory.getLogger(ProjectClientDaoImpl.class);

	@Override
	@Transactional
	public ProjectClients storeProjectClients(ProjectClients projectClients) throws Exception {

		logger.debug("In Project Clients Save method");
		Session session = this.sessionFactory.getCurrentSession();
		try{
			if(projectClients != null){
				session.saveOrUpdate(projectClients);
				logger.debug("Project Clients saved successfully..");
			}
		}catch(ConstraintViolationException exception)
		{
			StringBuilder message= new StringBuilder(MessageFormat.format(duplicateValue,"PROJECT_CLIENTS"));
			throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		
		return projectClients;
		
	}

	@Override
	@Transactional
	public ProjectClients getProjectClientsByProjectIdAndOrgLocationId(Long projectId, Long orgLocationsId)
			throws Exception {
		
		logger.debug("In Project Clients getProjectClientsByProjectIdAndOrgLocationId method");
		Session session = this.sessionFactory.getCurrentSession();
		String query = " from ProjectClients pc left join fetch pc.projectId left join fetch pc.orgLocationsId "
				+ "left join fetch pc.orgRelationId ol left join fetch ol.locationId left join fetch ol.responderLocationId  "
				+ "where pc.projectId.id = :projectId and pc.orgLocationsId.id = :orgLocationsId" ;
		List<ProjectClients> pcList =session.createQuery(query).setLong("projectId", projectId).setLong("orgLocationsId", orgLocationsId).setCacheable(true).list(); 
		ProjectClients pc = null;
		if(pcList != null && !pcList.isEmpty()){
			pc = pcList.iterator().next();
		}
		return pc;  
		
	}

	@Override
	@Transactional
	public List<ProjectClients> getProjectClientsListByProjectId(Long projectId) throws Exception {
		
		logger.debug("In getProjectClientsListByProjectId method");
		Session session = this.sessionFactory.getCurrentSession();
		List<ProjectClients> projectClientsList = null;
		Query candidateListQuery= session.createQuery("from ProjectClients pc left join fetch pc.projectId left join fetch pc.orgLocationsId "
				+ " left join fetch pc.orgRelationId  where pc.projectId.id = :projectId and pc.status = '1' ");
		projectClientsList = candidateListQuery.setLong("projectId", projectId).setCacheable(true).list();
		return projectClientsList;
	}

	@Override
	@Transactional
	public ProjectClients getProjectClientsByProjectIdAndOrgLocationIdAndOrgRelationId(Long projectId,
			Long orgLocationsId, Long orgRelationId) throws Exception {
		
		logger.debug("In Project Clients getProjectClientsByProjectIdAndOrgLocationId method");
		Session session = this.sessionFactory.getCurrentSession();
		String query = " from ProjectClients pc left join fetch pc.projectId left join fetch pc.orgLocationsId "
				+ "left join fetch pc.orgRelationId ol left join fetch ol.locationId left join fetch ol.responderLocationId  "
				+ "where pc.projectId.id = :projectId and pc.orgLocationsId.id = :orgLocationsId and pc.orgRelationId.id = :orgRelationId " ;
		List<ProjectClients> pcList =session.createQuery(query).setLong("projectId", projectId).setLong("orgLocationsId", orgLocationsId).setLong("orgRelationId", orgRelationId).setCacheable(true).list(); 
		ProjectClients pc = null;
		if(pcList != null && !pcList.isEmpty()){
			pc = pcList.iterator().next();
		}
		return pc;  
		
	}

	@Override
	@Transactional
	public ProjectClients getProjectClientsByUUID(String uuid) throws Exception {
		
		logger.debug("In Project Clients getProjectClientsByUUID method");
		Session session = this.sessionFactory.getCurrentSession();
		String query = " from ProjectClients pc left join fetch pc.projectId left join fetch pc.orgLocationsId "
				+ "left join fetch pc.orgRelationId ol left join fetch ol.locationId left join fetch ol.responderLocationId  "
				+ "where pc.uuid = :uuid" ;
		List<ProjectClients> pcList =session.createQuery(query).setString("uuid", uuid).setCacheable(true).list(); 
		ProjectClients pc = null;
		if(pcList != null && !pcList.isEmpty()){
			pc = pcList.iterator().next();
		}
		return pc;
	}

	@Override
	@Transactional
	public List<ProjectClients> getProjectClientsListByLoginUserAndOrgLocationId(Long locationsId, Long userId)
			throws Exception {
		logger.debug("In getProjectClientsListByProjectId method");
		Session session = this.sessionFactory.getCurrentSession();
		List<ProjectClients> projectClientsList = null;
		Query candidateListQuery= session.createQuery("from ProjectClients pc left join fetch pc.projectId left join fetch pc.orgLocationsId "
				+ " left join fetch pc.orgRelationId  where pc.orgRelationId IS NOT NULL and pc.orgLocationsId.id = :locationsId and pc.status = '1' and pc.id IN "
				 +"("
				 + " select hierarchy.projectClientsId.id from ProjectApproverHierarchy hierarchy  where  "
				 + "  hierarchy.approverUserId.id = :userId "
				 + " OR "
				 + "(:userId IN (select userRole.user.id from UserRole userRole where userRole.roles.roleName = 'ROLE_ADMIN' and userRole.orgLocations.id = pc.orgLocationsId.id))"
			  + " ) group by pc.orgRelationId " );
		projectClientsList = candidateListQuery.setLong("userId", userId).setLong("locationsId", locationsId).setCacheable(true).list();
		return projectClientsList;
	}

	@Override
	@Transactional
	public List<ProjectClients> getAllProjectClientsByApproverUserAndOrgLocationId(Long userId, Long locationsId) throws Exception
	{
		logger.debug("In getProjectClientsListByProjectId method");
		Session session = this.sessionFactory.getCurrentSession();
		List<ProjectClients> projectClientsList = null; 
		Query candidateListQuery= session.createQuery("from ProjectClients pc left join fetch pc.projectId left join fetch pc.orgRelationId rel left join fetch rel.locationId inloc left join fetch inloc.org  left join fetch rel.responderLocationId  resloc left join fetch resloc.org left join fetch pc.orgLocationsId "
			  	 + " left join fetch pc.orgRelationId  where  pc.orgLocationsId.id = :locationsId and pc.status = '1' "
			  	 + " and "
			  	 + " pc.projectId.id IN( select pm.projects.id from ProjectMembers pm where pm.status = '1')"
			  	 + " and "
				 +"  pc.id IN("
				 + " select hierarchy.projectClientsId.id from ProjectApproverHierarchy hierarchy  where  "
				 + " (hierarchy.projectClientsId.orgLocationsId.id = :locationsId and hierarchy.approverUserId.id = :userId "
				 + " OR "
				 + " (:userId IN (select userRole.user.id from UserRole userRole where userRole.roles.roleName = 'ROLE_ADMIN' and userRole.orgLocations.id = hierarchy.projectClientsId.orgLocationsId.id) )"
			     + " ))");
		projectClientsList = candidateListQuery.setLong("userId", userId).setLong("locationsId", locationsId).setCacheable(true).list();
		return projectClientsList;
	}
	
}
