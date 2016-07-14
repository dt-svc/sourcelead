package com.bis.sourcelead.dao.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.ProjectApproverHierarchyDao;
import com.bis.sourcelead.dao.ProjectsDao;
import com.bis.sourcelead.entity.ProjectApproverHierarchy;
import com.bis.sourcelead.entity.Projects;

@Repository
public class ProjectsDaoImpl implements ProjectsDao{
	
	@Autowired
	private SessionFactory sessionFactory;
	

    @Autowired
	private ProjectApproverHierarchyDao projectApproverHierarchyDao;
	
	private static final Logger logger = LoggerFactory.getLogger(ProjectsDaoImpl.class);

	/**
	 * This method for save project
	 */
	@Override
	@Transactional(rollbackFor = { Exception.class })	
	public Projects saveProject(Projects projects) throws Exception {
		logger.debug("In Projects Save method");
		Session session = this.sessionFactory.getCurrentSession();
		session.save(projects);
		return projects;
	}

	/**
	 * This method for update project
	 */
	@Override
	@Transactional(rollbackFor = { Exception.class })	
	public Projects updateProject(Projects projects) throws Exception {
		logger.debug("In Projects Update method");
		Session session = this.sessionFactory.getCurrentSession();
		session.saveOrUpdate(projects);
		logger.info("Record updated successfully..!");
		return projects;
	}

	/**
	 * This method for get project by id
	 */
	@Override
	@Transactional(rollbackFor = { Exception.class })	
	public Projects getProjectById(Long id) throws Exception {
		logger.debug("In Projects getProject method");
		Session session = this.sessionFactory.getCurrentSession();
        Projects projects = session.get(Projects.class, id);
        return session.get(Projects.class, id);
	}

	/**
	 * This method for delete project by id.
	 * Here we are doing soft deletion only, record not deleted from DB just we have to change the status
	 */
	@Override
	@Transactional(rollbackFor = { Exception.class })	
	public Boolean deleteProjectById(Long id) throws Exception {
		 logger.debug("In Projects deleteProject method");
		 Session session = this.sessionFactory.getCurrentSession();
		 boolean isDeleted = false;
		 if(id != null){
			 Projects projects= (Projects)session.load(Projects.class,id);
			 projects.setStatus("0");
			 session.saveOrUpdate(projects);
			 logger.info("Successfully deleted !!!");
			 isDeleted = true;
		 }
		return isDeleted;
	}

	/**
	 * This method for get all projects
	 */
	@Override
	@Transactional(rollbackFor = { Exception.class })	
	public Collection<Projects> getAllProjects() throws Exception {
		logger.debug("In Projects getAllProjects method");
        Session session = this.sessionFactory.getCurrentSession();
        Criteria crit = session.createCriteria(Projects.class);
        List<Projects> projectsList = crit.list();
        return projectsList;
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class })	
	public Collection<Projects> getAllProjectsAndManagersByLoginUserId(Long userId) throws Exception {
		logger.debug("In Projects getAllProjects method");
        Session session = this.sessionFactory.getCurrentSession();
        
        String query = "from  Projects projects right join fetch projects.projectMembers right join fetch projects.projectApprovers   where (projects.id IN(select ph.projectId.id from ProjectApproverHierarchy ph where ph.approverUserId.id =:userId)  OR projects.id IN(select pm.projects.id from ProjectMembers pm where pm.employee.id IN(select emp.id from Employees emp where emp.user.id = :userId)))";
        List<Projects> projectsList  =   session.createQuery(query.toString()).setLong("userId", userId).list();
       
        return projectsList;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })	
	public Integer projectIdValidation(Long projectId) throws Exception {
		
		
		Session session = this.sessionFactory.getCurrentSession();
		
		String query ="select count(*)from Projects projects where projects.projectSequeneNumber = :projectId";
		
		List<Long> deptCount =   session.createQuery(query.toString()).setLong("projectId", projectId)
        										.list();
			Integer count = 0;
			if(deptCount != null && !deptCount.isEmpty()){
					count = deptCount.get(0).intValue();
			}
			return count;
		
	}

     public class FilterNameValueMap<key,value> extends HashMap<key,value>{

	private static final long serialVersionUID = -2650993424650202629L;

     }

}
