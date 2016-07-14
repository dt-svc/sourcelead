package com.bis.sourcelead.dao.impl;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.ProjectMembersDao;
import com.bis.sourcelead.entity.ProjectMembers;
import com.bis.sourcelead.util.CalendarUtil;
import com.bis.sourcelead.util.Constants;

@Repository
public class ProjectMembersDaoImpl implements ProjectMembersDao{
	
	@Autowired
	private SessionFactory sessionFactory;
	
	private static final Logger logger = LoggerFactory.getLogger(ProjectMembersDaoImpl.class);

	/**
	 * This method for save project member
	 */
	@Override
	@Transactional(rollbackFor = { Exception.class })	
	public ProjectMembers saveProjectMember(ProjectMembers projectMembers) throws Exception {
		logger.debug("In ProjectMember Save method");
		Session session = this.sessionFactory.getCurrentSession();
		session.save(projectMembers);
		return projectMembers;
	}

	/**
	 * This method for update project member
	 */
	@Override
	@Transactional(rollbackFor = { Exception.class })	
	public ProjectMembers updateProjectMember(ProjectMembers projectMembers) throws Exception {
		logger.debug("In ProjectMember Update method");
		Session session = this.sessionFactory.getCurrentSession();
		session.saveOrUpdate(projectMembers);
		logger.info("Record updated successfully..!");
		return projectMembers;
	}

	/**
	 * This method for get project member by id
	 */
	@Override
	@Transactional(rollbackFor = { Exception.class })	
	public ProjectMembers getProjectMemberById(Long id) throws Exception {
		logger.debug("In ProjectMember getProjectMember method");
		Session session = this.sessionFactory.getCurrentSession();
		ProjectMembers projectMembers = session.get(ProjectMembers.class, id);
        return session.get(ProjectMembers.class, id);
	}

	/**
	 * This method for delete project member by id
	 * Here we are doing soft deletion only, record not deleted from DB just we have to change the status
	 */
	@Override
	@Transactional(rollbackFor = { Exception.class })	
	public Boolean deleteProjectMemberById(Long id) throws Exception {
		 logger.debug("In ProjectMember deleteProjectMember method");
		 Session session = this.sessionFactory.getCurrentSession();
		  boolean isDeleted = false;
		  if(id != null){
			  ProjectMembers projectMembers= (ProjectMembers)session.load(ProjectMembers.class,id);
			  projectMembers.setStatus("0");
			  session.saveOrUpdate(projectMembers);
			  logger.info("Successfully deleted !!!"); 
			  isDeleted = true;
		  }
		return isDeleted;
	}

	/**
	 * This method for get all the project members
	 */
	@Override
	@Transactional(rollbackFor = { Exception.class })	
	public Collection<ProjectMembers> getAllProjectMembers() throws Exception {
		logger.debug("In ProjectMember getAllProjectMembers method");
        Session session = this.sessionFactory.getCurrentSession();
        Criteria crit = session.createCriteria(ProjectMembers.class);
        List<ProjectMembers> projectsMembersList = crit.list();
        return projectsMembersList;
	}
	
	/**
	 * This method for get all the project members by project id
	 */
	@Override
	@Transactional(rollbackFor = { Exception.class })	
	public Collection<ProjectMembers> getAllProjectMembersByProjectId(Long id) throws Exception {
		logger.debug("In ProjectMember getAllProjectMembersByProjectId method");
        Session session = this.sessionFactory.getCurrentSession();
        List<ProjectMembers> projectsMembersList = null;
		Query projectsMembersListQuery= session.createQuery("from ProjectMembers pm left join fetch pm.projects left join fetch pm.employee emp left join fetch emp.user where pm.projects.id = :id");
		projectsMembersList=projectsMembersListQuery.setLong("id", id).list();
		return projectsMembersList;
        
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })	
	public List<Long> getAllProjectMembersIdsByemployeeId(Long userId) throws Exception {
		// TODO Auto-generated method stub
		
		Session session = this.sessionFactory.getCurrentSession();
		String query = "select pm.id from ProjectMembers pm where pm.employee.id IN(select emp.id from Employees emp where emp.user.id = :userId )" ;
		List<Long> idsList =session.createQuery(query.toString()).setInteger("userId", userId.intValue()) 
			    .list(); 
		return idsList;  
		
        
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<ProjectMembers> getAllProjectMembersIdsByTimesheetApproverUserIdOrMemberUserId(Long userId) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		
		java.sql.Date  currentDate = new java.sql.Date(new Date().getTime());
		
		Calendar currentCal = Calendar.getInstance(Constants.TIMEZONE_CENTRAL);
		currentCal.setLenient(false);
		currentCal.setTime(new Date());
		Calendar endMonday = CalendarUtil.getDateIncrementByincrement(currentCal,28);
		Date endMondayDate = endMonday.getTime();
		java.sql.Date  currentMonday = new java.sql.Date(endMondayDate.getTime());
		
		String subQueryForUserIdChecking = "(pm.employee.id IN(select emp.id from Employees emp where emp.user.id = :userId) OR pm.projects.id IN(select pah.projectId.id from ProjectApproverHierarchy pah where pah.approverUserId.id = :userId))";
		String query = " from ProjectMembers pm left join fetch pm.projects left join fetch pm.employee where "+subQueryForUserIdChecking+" and (pm.projects.startDate <= :currentDate and (pm.projects.endDate IS NULL OR pm.projects.endDate > :currentDate) and pm.projects.projectStatus ='STARTED') and (pm.id NOT IN(select ts.projectMembers.id from TimesheetSubmissions ts where ts.startDate = :currentMonday))" ;
		List<ProjectMembers> idsList =session.createQuery(query.toString()).setInteger("userId", userId.intValue()).setParameter("currentDate", currentDate).setParameter("currentMonday", currentMonday) 
			    .list(); 
		return idsList;  
	}

}
