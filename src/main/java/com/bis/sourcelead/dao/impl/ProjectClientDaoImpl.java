package com.bis.sourcelead.dao.impl;

import java.text.MessageFormat;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
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
		List<ProjectClients> pcList =session.createQuery(query).setLong("projectId", projectId).setLong("orgLocationsId", orgLocationsId).list(); 
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
				+ " left join fetch pc.orgRelationId  where pc.projectId.id = :projectId and pc.status = '1' order by pc.id asc ");
		projectClientsList = candidateListQuery.setLong("projectId", projectId).list();
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
		List<ProjectClients> pcList =session.createQuery(query).setLong("projectId", projectId).setLong("orgLocationsId", orgLocationsId).setLong("orgRelationId", orgRelationId).list(); 
		ProjectClients pc = null;
		if(pcList != null && !pcList.isEmpty()){
			pc = pcList.iterator().next();
		}
		return pc;  
		
	}
	
	
	@Override
	@Transactional
	public ProjectClients getProjectClientsByProjectID(Long projectId) throws Exception {
		
		logger.debug("In getProjectClientsListByProjectId method");
		Session session = this.sessionFactory.getCurrentSession();
		String candidateListQuery= "from ProjectClients pc where pc.id = :projectId and pc.status = '1' ";
		List<ProjectClients> pcList =session.createQuery(candidateListQuery).setLong("projectId", projectId).list(); 
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
		List<ProjectClients> pcList =session.createQuery(query).setString("uuid", uuid).list(); 
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
				 + "(:userId IN (select userRole.user.id from UserRole userRole where userRole.roles.roleName = 'ROLE_ADMIN' and userRole.userState = 'ACTIVE' and userRole.orgLocations.id = pc.orgLocationsId.id))"
			  + " ) group by pc.orgRelationId " );
		projectClientsList = candidateListQuery.setLong("userId", userId).setLong("locationsId", locationsId).list();
		return projectClientsList;
	}
	
	
	@Override
	@Transactional
	public List<ProjectClients> getProjectClientsListByLoginUserAndOrgLocationIdForTimesheetApproval(Long locationsId, Long userId,String isGlobalUser)
			throws Exception {
		logger.debug("In getProjectClientsListByProjectId method");
		Session session = this.sessionFactory.getCurrentSession();
		String globalUserQuery = "";
		if(StringUtils.isNotEmpty(isGlobalUser) && isGlobalUser.equalsIgnoreCase("Y")) {
      	  globalUserQuery =   "(pc.orgLocationsId.org.id IN(select ol.org.id from OrgLocations ol where ol.id = :locationsId)) ";
        }else {
      	  globalUserQuery = " pc.orgLocationsId.id = :locationsId ";
        }
		Query candidateListQuery= 
				session.createQuery(" from ProjectClients pc left join fetch pc.projectId proj left join fetch pc.orgLocationsId "
				+ " left join fetch pc.orgRelationId  where  "+globalUserQuery+"  and pc.status = '1' and pc.id IN "
				 +"("
				 + " select hierarchy.projectClientsId.id from ProjectApproverHierarchy hierarchy  where  "
				 + "  hierarchy.approverUserId.id = :userId "
				 + " OR "
				 + "(proj.isReportingManagerAsApprover = 'Y' and  :userId IN(select userRole.reportingManager.id from UserRole userRole where userRole.user.id IN(select can.consultantUser.id from Candidate can where can.isAssociate = 'Y' and can.masterCandidateId IN(select pm.projectMemberCandidteId.masterCandidateId.id from ProjectMembers pm where pm.projectClientsId.id = pc.id and pm.projects.id = proj.id))))"
				 + " OR "
				 + "(:userId IN (select userRole.user.id from UserRole userRole where userRole.roles.roleName = 'ROLE_ADMIN' and userRole.userState = 'ACTIVE' and userRole.orgLocations.id = pc.orgLocationsId.id))"
				 + " ) " );
		List<ProjectClients> projectClientsList = candidateListQuery.setLong("userId", userId).setLong("locationsId", locationsId).list();
		return projectClientsList;
	}

	@Override
	@Transactional
	public List<ProjectClients> getAllProjectClientsForExpensesByApproverUserAndOrgLocationId(Long userId, Long locationsId,String isGlobalUser,String purpose) throws Exception
	{
		logger.debug("In getProjectClientsListByProjectId method");
		Session session = this.sessionFactory.getCurrentSession();
		
		String globalUserQuery = "";
		String globalUserQuery2 = "";
		
		 if(StringUtils.isNotEmpty(isGlobalUser) && isGlobalUser.equalsIgnoreCase("Y")) {
		    	globalUserQuery = " pc.orgLocationsId.org.id IN (select ol.org.id from OrgLocations ol where ol.id = :locationsId)";
		    	globalUserQuery2 = " hierarchy.projectClientsId.orgLocationsId.org.id IN (select ol.org.id from OrgLocations ol where ol.id = :locationsId)";
		    }else {
		    	globalUserQuery = " pc.orgLocationsId.id = :locationsId";
		    	globalUserQuery2 = " hierarchy.projectClientsId.orgLocationsId.id = :locationsId";
		    }
		 
		 String purposeQuery = "";
		 if(StringUtils.isNotEmpty(purpose) && purpose.equalsIgnoreCase("newExpense")) {
			 purposeQuery = " and  pc.projectId.isProjectForExpenses = 'Y'  ";
		 }
		 
		List<ProjectClients> projectClientsList = null; 
		Query candidateListQuery= session.createQuery("from ProjectClients pc left join fetch pc.projectId project left join fetch pc.orgRelationId rel left join fetch rel.locationId inloc left join fetch inloc.org  left join fetch rel.responderLocationId  resloc left join fetch resloc.org left join fetch pc.orgLocationsId "
			  	 + " left join fetch pc.orgRelationId  where  "+globalUserQuery+" and pc.status = '1' "+purposeQuery
			  	 + " and "
			  	 + " pc.projectId.id IN( select pm.projects.id from ProjectMembers pm where pm.status = '1')"
			  	 + " and "
				 +"  pc.id IN("
				 + " select hierarchy.projectClientsId.id from ExpenseProjectApproverHierarchy hierarchy  where  "
				 + " ("+globalUserQuery2+" and hierarchy.approverUserId.id = :userId "
				 + " OR "
				 + " (:userId IN (select userRole.user.id from UserRole userRole where userRole.roles.roleName = 'ROLE_ADMIN' and userRole.userState = 'ACTIVE' and userRole.orgLocations.id = hierarchy.projectClientsId.orgLocationsId.id) )"
				 +" OR  (pc.projectId.isReportingManagerAsApprover = 'Y')"
				 + " )) group by pc.projectId ");
		projectClientsList = candidateListQuery.setLong("userId", userId).setLong("locationsId", locationsId).list();
		return projectClientsList;
	}

	@Override
	@Transactional
	public List<ProjectClients> getAllProjectsByOrgLocationIdAndStatusInProgressAndNotStarted(Long orgLocationsId) throws Exception
	{
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from ProjectClients pc left join fetch pc.orgLocationsId left join fetch pc.orgRelationId  where pc.status = '1' and (pc.projectId.projectStatus = 'INPROGRESS' or pc.projectId.projectStatus = 'NOTSTARTED') and pc.orgLocationsId =:orgLocationsId "; 
		List<ProjectClients> projectsList =  session.createQuery(query.toString()).setLong("orgLocationsId", orgLocationsId).list();
		return projectsList;
	}

	@Override
	@Transactional
	public List<Long> getProjectClientsListByOrgRelationsListAndOrgLocationsId(List<Long> orgRelationId,
			Long orgLocationsId) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		String query = "select pc.id from ProjectClients pc where pc.status = '1' "
				+ "and (pc.projectId.projectStatus = 'INPROGRESS' or pc.projectId.projectStatus = 'NOTSTARTED') "
				+ " and pc.orgRelationId.id IN (:orgRelationId) and pc.orgLocationsId = :orgLocationsId "; 
		List<Long> projectClientsIdLis =  session.createQuery(query.toString()).setParameterList("orgRelationId", orgRelationId).setLong("orgLocationsId", orgLocationsId).list();
		return projectClientsIdLis;
		
	}

	@Override
	@Transactional
	public ProjectClients getProjectClientsByProjectIdAndOrgId(Long projectId, Long orgId) throws Exception {
		
		logger.debug("In Project Clients getProjectClientsByProjectIdAndOrgLocationId method");
		Session session = this.sessionFactory.getCurrentSession();
		String query = " from ProjectClients pc left join fetch pc.projectId left join fetch pc.orgLocationsId "
				+ "left join fetch pc.orgRelationId ol left join fetch ol.locationId left join fetch ol.responderLocationId  "
				+ "where pc.projectId.id = :projectId and pc.orgLocationsId.org.id = :orgId" ;
		List<ProjectClients> pcList =session.createQuery(query).setLong("projectId", projectId).setLong("orgId", orgId).list(); 
		ProjectClients pc = null;
		if(pcList != null && !pcList.isEmpty()){
			pc = pcList.iterator().next();
		}
		return pc;  
		
	}

	@Override
	@Transactional
	public List<Long> getProjectClientsIDsByProjectId(Long projectId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "select pc.id from ProjectClients pc where pc.status = '1' and pc.projectId.id = :projectId"; 
		List<Long> projectClientsIdLis =  session.createQuery(query.toString()).setLong("projectId", projectId).list();
		return projectClientsIdLis;
	
	}

	@Override
	@Transactional
	public ProjectClients getProjectClientsById(Long id) throws Exception {
		
		logger.debug("In Project Clients getProjectClientsById method");
		Session session = this.sessionFactory.getCurrentSession();
		String query = " from ProjectClients pc left join fetch pc.projectId left join fetch pc.orgLocationsId "
				+ "left join fetch pc.orgRelationId ol left join fetch ol.locationId left join fetch ol.responderLocationId  "
				+ "where pc.id = :id" ;
		List<ProjectClients> pcList =session.createQuery(query).setLong("id", id).list(); 
		ProjectClients pc = null;
		if(pcList != null && !pcList.isEmpty()){
			pc = pcList.iterator().next();
		}
		return pc;
	  
		
	
	}

	@Override
	@Transactional
	public ProjectClients getProjectClientsByProjectIdAndLevel(Long projectid, Integer level) throws Exception {
		logger.debug("In Project Clients getProjectClientsById method");
		Session session = this.sessionFactory.getCurrentSession();
		String query = " from ProjectClients pc left join fetch pc.projectId left join fetch pc.orgLocationsId "
				+ "left join fetch pc.orgRelationId ol left join fetch ol.locationId left join fetch ol.responderLocationId  "
				+ "where pc.projectId.id = :projectid and pc.level = :level" ;
		List<ProjectClients> pcList =session.createQuery(query).setLong("projectid", projectid).setInteger("level", level).list(); 
		ProjectClients pc = null;
		if(pcList != null && !pcList.isEmpty()){
			pc = pcList.iterator().next();
		}
		return pc;
	}

	@Override
	@Transactional
	public List<Long> getProjectClientsByRelationId(Long orgRelationId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "select pc.id from ProjectClients pc where pc.status = '1' and pc.orgRelationId = :orgRelationId "; 
		List<Long> projectsList =  session.createQuery(query.toString()).setLong("orgRelationId", orgRelationId).list();
		return projectsList;
	}
	
}
