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
import com.bis.sourcelead.dao.UserRoleDao;
import com.bis.sourcelead.entity.ProjectApproverHierarchy;
import com.bis.sourcelead.entity.Projects;

@Repository
public class ProjectApproverHierarchyDaoImpl implements ProjectApproverHierarchyDao{

	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	UserRoleDao userRoleDao;
	
	private static final Logger logger = LoggerFactory.getLogger(ProjectApproverHierarchyDaoImpl.class);
	
	@Override
	@Transactional(rollbackFor = { Exception.class })	
	public ProjectApproverHierarchy saveProjectApproverHierarchy(ProjectApproverHierarchy projectApproverHierarchy)	throws Exception {
			logger.debug("In Projects Save method");
			Session session = this.sessionFactory.getCurrentSession();
			session.save(projectApproverHierarchy);
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
		session.saveOrUpdate(projectApproverHierarchy);
		logger.info("Record updated successfully..!");
		return projectApproverHierarchy;
		
	}
	
	
	@Override
	@Transactional(rollbackFor = { Exception.class })	
	public ProjectApproverHierarchy getLevelOneProjectApproverByProjectId(Long ProjectId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		logger.debug("In ProjectApproverHierarchy getProjectApproverHierarchyByProjectId method");
		String query = "from ProjectApproverHierarchy projectHirachy left join fetch projectHirachy.approverUserId where projectHirachy.projectId = :ProjectId and projectHirachy.level = '1' ";
		Collection<ProjectApproverHierarchy> projectApproverHierarchies = session.createQuery(query.toString()).setLong("ProjectId", ProjectId).setCacheable(true).list();

		ProjectApproverHierarchy ph = null;
		if (projectApproverHierarchies != null && projectApproverHierarchies.size() > 0) {
			ph = (		ProjectApproverHierarchy) projectApproverHierarchies.iterator().next();
		}
		return ph;

	}
	

	@Override
	@Transactional(rollbackFor = { Exception.class })	
	public List<ProjectApproverHierarchy> getApproverUsersByProjectId(long projectId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from  ProjectApproverHierarchy projectHirachy   left join fetch projectHirachy.approverUserId  where   projectHirachy.projectId =:projectId";
	        List<ProjectApproverHierarchy>  projectHirachyList =   session.createQuery(query.toString()).setLong("projectId", projectId)
	        																							.setCacheable(true)
	        																							.list();
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
		return (ProjectApproverHierarchy) session.get(ProjectApproverHierarchy.class, projectApproverHierarchy);
	}

	
}
