package com.bis.sourcelead.dao.impl;

import java.text.MessageFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.ObjectNotFoundException;
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

import com.bis.sourcelead.dao.ProjectMembersDao;
import com.bis.sourcelead.entity.ProjectMembers;
import com.bis.sourcelead.entity.Projects;
import com.bis.sourcelead.exception.NoRecordsFoundException;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;
import com.bis.sourcelead.exception.SourceLeadObjectNotFoundException;
import com.bis.sourcelead.service.ProjectMembersService;
import com.bis.sourcelead.util.CalendarUtil;
import com.bis.sourcelead.util.Constants;

@Repository
public class ProjectMembersDaoImpl implements ProjectMembersDao{
	
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
	
	private static final Logger logger = LoggerFactory.getLogger(ProjectMembersDaoImpl.class);

	/**
	 * This method for save project member
	 */
	@Override
	@Transactional(rollbackFor = { Exception.class })	
	public ProjectMembers saveProjectMember(ProjectMembers projectMembers) throws Exception {
		logger.debug("In ProjectMember Save method");
		Session session = this.sessionFactory.getCurrentSession();
		try{
			if(projectMembers != null){
				session.save(projectMembers);
				logger.debug("Project Member saved successfully..");
			}
		}catch(ConstraintViolationException exception)
		{
			StringBuilder message= new StringBuilder(MessageFormat.format(duplicateValue,"ORG"));
			throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		
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
		try{
			if(projectMembers != null){
				session.saveOrUpdate(projectMembers);
				logger.info("Record updated successfully..!");
			}
		}catch(ConstraintViolationException exception)
		{
			StringBuilder message= new StringBuilder(MessageFormat.format(duplicateValue,"PROJECT_MEMBERS"));
			throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		
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
			  try{
			  ProjectMembers projectMembers= (ProjectMembers)session.load(ProjectMembers.class,id);
			  projectMembers.setStatus("0");
			  session.saveOrUpdate(projectMembers);
			  logger.info("Successfully deleted !!!"); 
			  isDeleted = true;
			  }catch(Exception exception){
				  StringBuilder message= new StringBuilder(MessageFormat.format(statusDelete,"PROJECT_MEMBERS"));
				 throw new SourceLeadObjectNotFoundException(message.toString());  
			  }
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
		Query projectsMembersListQuery= session.createQuery("from ProjectMembers pm left join fetch pm.projects left join fetch pm.employee emp left join fetch emp.user where pm.projects.id = :id and pm.status='ACTIVE'");
		projectsMembersList=projectsMembersListQuery.setLong("id", id).setCacheable(true).list();
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
	
	/**
	 * This Method gives List Of all Projectmembers based on login user who is approver/employee to that particular project
	 *  who did have 4 weeks prior/post submission records in DB
	 * 
	 */
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<ProjectMembers> getAllProjectMembersIdsByTimesheetApproverUserIdOrMemberUserId(Long userId) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		java.sql.Date  currentDate = new java.sql.Date(new Date().getTime());
		Calendar currentCal = Calendar.getInstance(Constants.TIMEZONE_CENTRAL);
		currentCal.setLenient(false);
		currentCal.setTime(new Date());
		Calendar startMonday = CalendarUtil.getCurrentWeekDayByCalendar(currentCal, "MON");
		
		Calendar previousMonday = CalendarUtil.getDateIncrementByincrement(startMonday,-28);
		Date previousMondayDate = previousMonday.getTime();
		java.sql.Date  previousMondaySqlDate = new java.sql.Date(previousMondayDate.getTime());
		
		Calendar nextMonday = CalendarUtil.getDateIncrementByincrement(startMonday,28);
		Date nextMondayDate = nextMonday.getTime();
		java.sql.Date  nextMondaySqlDate = new java.sql.Date(nextMondayDate.getTime());
		
		String subQueryForUserIdChecking = "(pm.employee.id IN(select emp.id from Employees emp where emp.user.id = :userId) OR pm.projects.id IN(select pah.projectId.id from ProjectApproverHierarchy pah where pah.approverUserId.id = :userId))";
		String query = " from ProjectMembers pm left join fetch pm.projects left join fetch pm.employee where "+subQueryForUserIdChecking+" and (pm.id IN(select pmh.projectMemberId.id from ProjectMembersHistory pmh where pmh.status ='OPEN' and  pmh.projectAssignedDate <= :nextMondayDate and (pmh.projectTerminationDate IS NULL OR pmh.projectTerminationDate >= :previousMonday))) and (pm.id NOT IN(select ts.projectMembers.id from TimesheetSubmissions ts where ts.status ='ACTIVE' and ts.startDate = :nextMondayDate))" ;
		List<ProjectMembers> idsList =session.createQuery(query.toString()).setInteger("userId", userId.intValue()).setParameter("previousMonday", previousMondaySqlDate).setParameter("nextMondayDate", nextMondaySqlDate) 
			    .list(); 
		return idsList;  
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<ProjectMembers> getAllProjectsByUserId(Long userId) {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from ProjectMembers pm left join fetch pm.projects left join fetch pm.employee emp  where pm.employee.id IN(select emp.id from Employees emp where emp.user.id = :userId )  OR (pm.projects.id IN(select pah.projectId from ProjectApproverHierarchy pah where pah.approverUserId = :userId))" ;
		List<ProjectMembers> allProjectsList =session.createQuery(query.toString()).setInteger("userId", userId.intValue()).setCacheable(true) 
			    .list();
		return allProjectsList;
	}


	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Integer getAllProjectMembersCountByProjectId(Long id) {
		
		    Session session = this.sessionFactory.getCurrentSession();
		    String status = Constants.MEMBER_ACTIVE;
		    String query = "select count(*) from ProjectMembers pm  where pm.projects.id = :id and pm.status = :status";
		    List<Long> ProjectMembersCount = (List<Long>) session.createQuery(query.toString()).setLong("id", id).setString("status", status).setCacheable(true).list();
		    
		    Integer count = null;
			if(ProjectMembersCount != null && !ProjectMembersCount.isEmpty()){
				count = ProjectMembersCount.get(0).intValue();
			}
			
		    return count;
		    
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Integer updateProjectMemberDeletedReason(Long projectMemberId,String reason) {
		Session session =  this.sessionFactory.getCurrentSession();
		String status = Constants.MEMBER_IN_ACTIVE;
		Query query = session.createQuery("update ProjectMembers pm set pm.status =:status ,terminateReason =:terminateReason where pm.id = :projectMemberId ");
		query.setLong("projectMemberId", projectMemberId);
		query.setString("status", status);
		query.setString("terminateReason",reason);
		int result = query.executeUpdate();
		return result;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<ProjectMembers> getAllProjectsByUserIdAndStatus(Long userId, String status) {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from ProjectMembers pm left join fetch pm.projects left join fetch pm.employee emp  where pm.status =:status and pm.employee.id IN(select emp.id from Employees emp where emp.user.id = :userId )  OR (pm.projects.id IN(select pah.projectId from ProjectApproverHierarchy pah where pah.approverUserId = :userId)" ;
		List<ProjectMembers> allProjectsList =session.createQuery(query.toString()).setInteger("userId", userId.intValue()).setString("status",status).setCacheable(true) 
			    .list();
		return allProjectsList;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Integer updateEndDateOfProjectMemberId(Date endDate, Long projectMemberId) {
		Session session =  this.sessionFactory.getCurrentSession();
		Query query = session.createQuery("update ProjectMembers pm set pm.projectEndDate =:endDate where pm.id = :projectMemberId");
		query.setLong("projectMemberId", projectMemberId);
		query.setDate("endDate", endDate);
		int result = query.executeUpdate();
		return result;
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Integer getEmployeeAssignedProjectsCount(Long orgRelationId, Long orgLocationId) {
		
		Session session = this.sessionFactory.getCurrentSession();
		
		String query = "from ProjectMembers pm where pm.projects.id IN (select p.id from Projects p  "
				+ "where p.orgRelationId.id = :orgRelationId and p.orgLocationsId.id = :orgLocationId)" ;
		List<ProjectMembers> projectMembersList =session.createQuery(query.toString()).setLong("orgRelationId", orgRelationId)
				.setLong("orgLocationId", orgLocationId).setCacheable(true).list(); 
		      
	    return projectMembersList.size();
}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<ProjectMembers> getAllTerminatedProjectMembersIdsByTimesheetApproverUserIdOrMemberUserId(Long userId)
			throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		java.sql.Date  currentDate = new java.sql.Date(new Date().getTime());
		
		
		String subQueryForUserIdChecking = "(pm.employee.id IN(select emp.id from Employees emp where emp.user.id = :userId) OR pm.projects.id IN(select pah.projectId.id from ProjectApproverHierarchy pah where pah.approverUserId.id = :userId))";
		String query = " from ProjectMembers pm left join fetch pm.projects left join fetch pm.employee where "+subQueryForUserIdChecking+" and (pm.id IN(select pmh.projectMemberId.id from ProjectMembersHistory pmh where pmh.status ='OPEN' and  pmh.projectTerminationDate < :currentDate))" ;
		List<ProjectMembers> idsList =session.createQuery(query.toString()).setInteger("userId", userId.intValue()).setParameter("currentDate", currentDate)
			    .list(); 
		return idsList;  
	}

}
