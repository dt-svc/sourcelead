package com.bis.sourcelead.dao.impl;

import java.text.MessageFormat;
import java.util.Collection;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.ProjectApproverHierarchyDao;
import com.bis.sourcelead.dao.UserRoleDao;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.ProjectApproverHierarchy;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;
import com.bis.sourcelead.exception.SourceLeadObjectNotFoundException;

@Repository
public class ProjectApproverHierarchyDaoImpl implements ProjectApproverHierarchyDao{

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
	@Autowired
	UserRoleDao userRoleDao;
	
	private static final Logger logger = LoggerFactory.getLogger(ProjectApproverHierarchyDaoImpl.class);
	
	@Override
	@Transactional(rollbackFor = { Exception.class })	
	public ProjectApproverHierarchy saveProjectApproverHierarchy(ProjectApproverHierarchy projectApproverHierarchy)	throws Exception {
			logger.debug("In Projects Save method");
			Session session = this.sessionFactory.getCurrentSession();
			try{
				if(projectApproverHierarchy != null){
					session.saveOrUpdate(projectApproverHierarchy);
				}
			}catch(Exception exception){
				StringBuilder message= new StringBuilder(MessageFormat.format(duplicateValue,"PROJECT_APPROVER_HIERARCHY"));
				throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
			}
			
			return projectApproverHierarchy;
	}
	/**
	 * This method is for updating the ProjectApproverHierarchy  by  projectApproverHierarchy
	 */
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public ProjectApproverHierarchy updateProjectApproverHierarchy(ProjectApproverHierarchy projectApproverHierarchy) throws Exception {
		logger.debug("In ProjectApproverHierarchy Update method");
		Session session = this.sessionFactory.getCurrentSession();
		if(projectApproverHierarchy != null){
			session.saveOrUpdate(projectApproverHierarchy);
			logger.info("Record updated successfully..!");
		}
		if(projectApproverHierarchy == null){
			StringBuilder message= new StringBuilder(MessageFormat.format(statusUpdate,"PROJECT_APPROVER_HIERARCHY"));
			throw new SourceLeadObjectNotFoundException(message.toString());
		}
		return projectApproverHierarchy;
		
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public void deleteProjectApproverHierarchy(ProjectApproverHierarchy projectApproverHierarchy) throws Exception {
		logger.debug("In ProjectApproverHierarchy delete method");
		Session session = this.sessionFactory.getCurrentSession();
		if(projectApproverHierarchy != null){
			session.delete(projectApproverHierarchy);
			logger.info("Record deleted successfully..!");
		}
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class })	
	public ProjectApproverHierarchy getLevelOneProjectApproverByProjectId(Long ProjectId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		logger.debug("In ProjectApproverHierarchy getProjectApproverHierarchyByProjectId method");
		String query = "from ProjectApproverHierarchy projectHirachy left join fetch projectHirachy.approverUserId where projectHirachy.projectId = :ProjectId and projectHirachy.level = '2' ";
		Collection<ProjectApproverHierarchy> projectApproverHierarchies = session.createQuery(query.toString()).setLong("ProjectId", ProjectId).setCacheable(true).list();

		ProjectApproverHierarchy ph = null;
		if (projectApproverHierarchies != null && projectApproverHierarchies.size() > 0) {
			ph = (		ProjectApproverHierarchy) projectApproverHierarchies.iterator().next();
		}
		return ph;

	}
	

	@Override
	@Transactional(rollbackFor = { Exception.class })	
	public List<ProjectApproverHierarchy> getAllApproverUsersByProjectIdList(List<Long> projectIds) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		
		String query = "from  ProjectApproverHierarchy projectHirachy  left join fetch projectHirachy.approverUserId usr where projectHirachy.projectId.id IN (:projectIds) GROUP BY projectHirachy.approverUserId";
	        List<ProjectApproverHierarchy>  projectHirachyList =   session.createQuery(query.toString()).setParameterList("projectIds", projectIds).setCacheable(true).list();
	        return projectHirachyList;
	}
	
	
	@Override
	@Transactional(rollbackFor = { Exception.class })	
	public List<ProjectApproverHierarchy> getApproverUsersByProjectId(long projectId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from  ProjectApproverHierarchy projectHirachy   left join fetch projectHirachy.approverUserId usr where   projectHirachy.projectId =:projectId ";
	        List<ProjectApproverHierarchy>  projectHirachyList =   session.createQuery(query.toString()).setLong("projectId", projectId)
	        																							.setCacheable(true)
	        																							.list();
	        return projectHirachyList;
		
	}
	
	
	@Override
	@Transactional(rollbackFor = { Exception.class })	
	public List<ProjectApproverHierarchy> getAllProjectApproverHierarchyByProjectId(long projectId,long userId) throws Exception {
	
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from  ProjectApproverHierarchy projectHirachy   left join fetch projectHirachy.approverUserId usr where   projectHirachy.projectId =:projectId and usr.id = :userId";
		List<ProjectApproverHierarchy>  projectHirachyList =   session.createQuery(query.toString()).setLong("projectId", projectId).setLong("userId", userId).setCacheable(true).list();
		if(projectHirachyList != null && !projectHirachyList.isEmpty()){
			 query = "from  ProjectApproverHierarchy projectHirachy   left join fetch projectHirachy.approverUserId usr where   projectHirachy.projectId =:projectId and projectHirachy.level IN(select Max(subpah.level) from ProjectApproverHierarchy subpah  where subpah.projectId =:projectId)";
			 projectHirachyList =   session.createQuery(query.toString()).setLong("projectId", projectId).setCacheable(true).list();
		}
		else{
			query = "from  ProjectApproverHierarchy projectHirachy   left join fetch projectHirachy.approverUserId usr where   projectHirachy.projectId =:projectId ";
			 projectHirachyList =   session.createQuery(query.toString()).setLong("projectId", projectId).setCacheable(true).list();
		}
	    return projectHirachyList;
	}
	

	@Override
	@Transactional(rollbackFor = { Exception.class })	
	public List<ProjectApproverHierarchy> getProjectHierarchyListByLoginUserId(Long userId) {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from  ProjectApproverHierarchy projectHirachy where (projectHirachy.approverUserId.id =:userId OR projectHirachy.projectId.id IN(select pm.projects.id from ProjectMembers pm where pm.employee.id IN(select emp.id from Employees emp where emp.user.id = :userId)))";
		 List<ProjectApproverHierarchy>  projectHirachyList =   session.createQuery(query.toString()).setLong("userId", userId)
				                                                                                     .setCacheable(true)
				 																					 .list();
	        return projectHirachyList;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<Long> getProjectIdsByLoginUserId(Long userId) {
		logger.debug("In ProjectsDaoImpl class getProjectIdsByLoginUserId method");
		
		Session session = this.sessionFactory.getCurrentSession();
		List<Long> projectsIdList = null;
		Boolean isadmin = userRoleDao.isUserAdmin(userId);
		
			if(isadmin){
				 String query = "select ph.projectId.id from ProjectApproverHierarchy ph";
				 projectsIdList =  session.createQuery(query.toString())
										  .setCacheable(true).list();
			}else{
				String query = "select ph.projectId.id from ProjectApproverHierarchy ph where ph.approverUserId =:userId";
			    projectsIdList =  session.createQuery(query.toString())
											.setLong("userId", userId)
											.setCacheable(true).list();
			}
		return projectsIdList;
	}
	
	/**
	 * This method for get ProjectApproverHierarchy by id
	 */
	
	@Override
	@Transactional(rollbackFor = { Exception.class })	
	public ProjectApproverHierarchy getByProjectApproverHierarchyId(Long projectApproverHierarchy) throws Exception {

		logger.debug("In ProjectApproverHierarchy getByProjectApproverHierarchyId method");
		
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from  ProjectApproverHierarchy projectHirachy left join fetch projectHirachy.approverUserId where projectHirachy.id =:projectApproverHierarchy";
		 @SuppressWarnings("unchecked")
		List<ProjectApproverHierarchy>  projectHirachyList =   session.createQuery(query.toString()).setLong("projectApproverHierarchy", projectApproverHierarchy)
				                                                                                     .setCacheable(true)
	    																					         .list();
		 
		 ProjectApproverHierarchy pah = null;
		 if(projectHirachyList != null && !projectHirachyList.isEmpty()){
			 pah = projectHirachyList.iterator().next();
		 }
	        
		 return pah;
		
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })	
	public List<ProjectApproverHierarchy> getAllProjectApproverHierarchyByProjectIdForManager(Long projectId,Long userId,int managerLevel) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from  ProjectApproverHierarchy projectHirachy   left join fetch projectHirachy.approverUserId usr where   projectHirachy.projectId =:projectId and usr.id = :userId and projectHirachy.level =:managerLevel";
		return session.createQuery(query.toString()).setLong("projectId", projectId).setLong("userId", userId).setInteger("managerLevel", managerLevel).setCacheable(true).list();
	}
	@Override
	@Transactional(rollbackFor = { Exception.class })	
	public ProjectApproverHierarchy getApproverUsersByProjectIdLevel1(Long projectId, Long userId) {
		ProjectApproverHierarchy projectApproverHierarchy = null;
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from  ProjectApproverHierarchy projectHirachy   left join fetch projectHirachy.approverUserId usr where   projectHirachy.projectId =:projectId  and projectHirachy.level=1 ";
		List<ProjectApproverHierarchy> projectApproverHierarchies =  session.createQuery(query.toString()).setLong("projectId", projectId).setCacheable(true).list();
		if(projectApproverHierarchies != null){
			return projectApproverHierarchy = projectApproverHierarchies.get(0);	
		}
		return projectApproverHierarchy;
		
	}
	@Override
	@Transactional(rollbackFor = { Exception.class })	
	public List<ProjectApproverHierarchy> getAllProjectApproverHierarchyByProjectIdlevel2Manager(Long projectId,Long userId){
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from  ProjectApproverHierarchy projectHirachy   left join fetch projectHirachy.approverUserId usr where   projectHirachy.projectId =:projectId  and usr.id = :userId and projectHirachy.level=2 ";
		List<ProjectApproverHierarchy> projectApproverHierarchies =  session.createQuery(query.toString()).setLong("projectId", projectId).setLong("userId", userId).setCacheable(true).list();
		 return projectApproverHierarchies;
	}

	@Override
	@Transactional
	public List<ProjectApproverHierarchy> getProjectApproversListByUserIdAndOrgLocationId(Long userId,
			Long orgLocationId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from ProjectApproverHierarchy hierarchy left join fetch hierarchy.projectId projects left join fetch hierarchy.approverUserId where hierarchy.approverUserId.id = :userId and projects.orgLocationsId.id = :orgLocationId";
		 List<ProjectApproverHierarchy>  projectHirachyList =   session.createQuery(query.toString()).setLong("userId", userId).setLong("orgLocationId", orgLocationId)
				                                                                                     .setCacheable(true)
				 																					 .list();
	        return projectHirachyList;
	}
	@Override
	@Transactional
	public List<ProjectApproverHierarchy> getProjectsHierarchyListByProjectIdAndOrgLocationId(Long projectId,
			Long orgLocationId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from ProjectApproverHierarchy hierarchy left join fetch hierarchy.projectId projects left join fetch hierarchy.approverUserId where projects.id = :projectId and projects.orgLocationsId.id = :orgLocationId";
		 List<ProjectApproverHierarchy>  projectHirachyList =   session.createQuery(query.toString()).setLong("projectId", projectId).setLong("orgLocationId", orgLocationId)
				                                                                                     .setCacheable(true)
				 																					 .list();
	        return projectHirachyList;

	}
	@Override
	@Transactional
	public List<ProjectApproverHierarchy> getApproverUserIdsByOrgLocationIdAndLevelAndFrequency(Long orgLocationId, String invoiceFrequency, int managerLevel) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from ProjectApproverHierarchy hierarchy left join fetch hierarchy.approverUserId user left join fetch hierarchy.projectId proj left join fetch proj.orgLocationsId ol where ol.id = :orgLocationId and hierarchy.level = :managerLevel and proj.invoiceFrequency = :invoiceFrequency GROUP BY hierarchy.approverUserId.id " ;
	    List<ProjectApproverHierarchy> projectHirachyList = session.createQuery(query).setLong( "orgLocationId", orgLocationId).setInteger("managerLevel", managerLevel).setString("invoiceFrequency", invoiceFrequency).setCacheable(true).list();
        return projectHirachyList;
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class })	
	public ProjectApproverHierarchy getProjectApproverByProjectId(Long ProjectId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		logger.debug("In ProjectApproverHierarchy getProjectApproverHierarchyByProjectId method");
		String query = "from ProjectApproverHierarchy projectHirachy left join fetch projectHirachy.approverUserId where projectHirachy.projectId = :ProjectId ";
		Collection<ProjectApproverHierarchy> projectApproverHierarchies = session.createQuery(query.toString()).setLong("ProjectId", ProjectId).setCacheable(true).list();
		ProjectApproverHierarchy ph = null;
		if (projectApproverHierarchies != null && projectApproverHierarchies.size() > 0) {
			ph = (		ProjectApproverHierarchy) projectApproverHierarchies.iterator().next();
		}
		return ph;
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class })	
	public ProjectApproverHierarchy getProjectApproverByProjectIdAndLevel(Long projectId, Integer level)
			throws Exception {
		

		Session session = this.sessionFactory.getCurrentSession();
		logger.debug("In ProjectApproverHierarchy getProjectApproverByProjectIdAndLevel method");
		String query = "from ProjectApproverHierarchy projectHirachy left join fetch projectHirachy.approverUserId where projectHirachy.projectId = :projectId and projectHirachy.level = :level ";
		Collection<ProjectApproverHierarchy> projectApproverHierarchies = session.createQuery(query.toString()).setLong("projectId", projectId).setInteger("level", level).setCacheable(true).list();

		ProjectApproverHierarchy ph = null;
		if (projectApproverHierarchies != null && projectApproverHierarchies.size() > 0) {
			ph = (		ProjectApproverHierarchy) projectApproverHierarchies.iterator().next();
		}
		return ph;

	
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class })	
	public List<ProjectApproverHierarchy> getApproverUsersByProjectClientId(Long projectClientsId) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from  ProjectApproverHierarchy projectHirachy left join fetch projectHirachy.approverUserId usr where  projectHirachy.projectClientsId.id =:projectClientsId ";
	    List<ProjectApproverHierarchy>  projectHirachyList =   session.createQuery(query.toString()).setLong("projectClientsId", projectClientsId)
	        																							.list();
	        return projectHirachyList;
		
	}
	@Override
	@Transactional(rollbackFor = { Exception.class })	
	public ProjectApproverHierarchy getApproverUsersByProjectIdAndProjectClientId(Long projectId, Long projectClientId)
			throws Exception {

		Session session = this.sessionFactory.getCurrentSession();
		logger.debug("In ProjectApproverHierarchy getProjectApproverByProjectIdAndLevel method");
		String query = "from ProjectApproverHierarchy projectHirachy left join fetch projectHirachy.projectId projects left join fetch projectHirachy.projectClientsId pc left join fetch projectHirachy.approverUserId where projects.id = :projectId and pc.id = :projectClientId ";
		Collection<ProjectApproverHierarchy> projectApproverHierarchies = session.createQuery(query.toString()).setLong("projectId", projectId).setLong("projectClientId", projectClientId).setCacheable(true).list();

		ProjectApproverHierarchy ph = null;
		if (projectApproverHierarchies != null && projectApproverHierarchies.size() > 0) {
			ph = (		ProjectApproverHierarchy) projectApproverHierarchies.iterator().next();
		}
		return ph;
	}
}
