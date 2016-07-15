package com.bis.sourcelead.dao.impl;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.TimesheetSubmissionsDao;
import com.bis.sourcelead.entity.TimesheetSubmissions;
import com.bis.sourcelead.enums.Submission_ApprovalStatus;
import com.bis.sourcelead.util.CalendarUtil;
import com.bis.sourcelead.util.Constants;

@Repository
public class TimesheetSubmissionsDaoImpl implements TimesheetSubmissionsDao {

	@Autowired
	private SessionFactory sessionFactory;

	private static final Logger logger = LoggerFactory.getLogger(TimesheetSubmissionsDaoImpl.class);
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public TimesheetSubmissions addTimesheetSubmissions(TimesheetSubmissions timesheetSubmissions) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		try {
			session.save(timesheetSubmissions);
		}
		catch(HibernateException exception)	{
		}
		return timesheetSubmissions;
		
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public void saveAllTimesheetSubmissions(List<TimesheetSubmissions> timesheetSubmissionsList) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if(timesheetSubmissionsList != null && !timesheetSubmissionsList.isEmpty()){
				for(TimesheetSubmissions timesheetSubmissions : timesheetSubmissionsList){
					session.save(timesheetSubmissions);
				}
				
			}
			
		}
		catch(HibernateException exception)	{
		}
		
	}
	

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public TimesheetSubmissions updateTimesheetSubmissions(TimesheetSubmissions timesheetSubmissions) throws Exception {
		// TODO Auto-generated method stub
		Session session = this.sessionFactory.getCurrentSession();
	    session.update(timesheetSubmissions);
	    return timesheetSubmissions;
		
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public TimesheetSubmissions getTimesheetSubmissions(String id) throws Exception {
		// TODO Auto-generated method stub
		Session session = this.sessionFactory.getCurrentSession();
		return (TimesheetSubmissions) session.get(TimesheetSubmissions.class, Long.parseLong(id));
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Boolean deleteTimesheetSubmissions(String id) throws Exception {
		// TODO Auto-generated method stub
		
		Session session = this.sessionFactory.getCurrentSession();
		TimesheetSubmissions timesheetSubmissions = (TimesheetSubmissions)session.get(TimesheetSubmissions.class, Long.parseLong(id));
		timesheetSubmissions.setStatus("10");
		session.update(timesheetSubmissions);
		logger.info("Successfully deleted !!!");
		return null;
		/*Query query = session.createQuery("delete Cities where id = :ID");
		query.setParameter("ID", new Long(18));
		 
		int result = query.executeUpdate();
		return null;*/
		
	}
	
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Date getNextMondayByProjectMemberId(Long pojectMemberId) {
		Session session = this.sessionFactory.getCurrentSession();
         String query = "select MAX(ts.startDate) from TimesheetSubmissions ts  where ts.projectMembers.id =:pojectMemberId ";
         List<java.sql.Timestamp>  wtsList = (List<java.sql.Timestamp>)session.createQuery(query).setLong("pojectMemberId", pojectMemberId).list();
         Date date = null;
          if(wtsList != null && !wtsList.isEmpty()){
            java.sql.Timestamp sqlDate = wtsList.iterator().next();
         	if(sqlDate != null){
         		Calendar startCal = Calendar.getInstance(Constants.TIMEZONE_CENTRAL);
				startCal.setLenient(false);
				startCal.setTime(new Date(sqlDate.getTime()));
				Calendar monday = CalendarUtil.getNextWeekDayByCalendar(startCal, "MON");
				date = monday.getTime();
         	}
          }
          return date;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<TimesheetSubmissions> getAllTimesheetSubmissions(Date startDate , Date endDate , Long  userId) throws Exception {
			
		    // TODO Auto-generated method stub
			Session session = this.sessionFactory.getCurrentSession();
			String query = "from TimesheetSubmissions ts ";  
			List<TimesheetSubmissions> timesheetSubmissionsList = session.createQuery(query).list();
			return timesheetSubmissionsList;
			
			/*and (DATE(ts.endDate) BETWEEN :startDate and :endDate)*/
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<TimesheetSubmissions> getApprovalTimesheetSubmissions(Long userId) throws Exception {
			
		    // TODO Auto-generated method stub
			Session session = this.sessionFactory.getCurrentSession();  
			String query = "from TimesheetSubmissions ts  left join fetch ts.projectMembers pm  left join fetch pm.employee emp left join fetch emp.user where ts.projectMembers.id IN ( select pm.id from ProjectMembers pm where  pm.projects.id  IN ( select pah.projectId from ProjectApproverHierarchy pah where pah.approverUserId =:userId)) group by ts.projectMembers.id";   
			List<TimesheetSubmissions> timesheetSubmissionsList = session.createQuery(query).setInteger("userId", userId.intValue()).list();
			for(TimesheetSubmissions ts : timesheetSubmissionsList){
				Long combinedTotalHours = 0l ; 
				java.sql.Date startDate = new java.sql.Date(ts.getStartDate().getTime());
				Integer empId = ts.getProjectMembers().getEmployee().getId().intValue();
				String query1 = "from TimesheetSubmissions ts  left join fetch ts.projectMembers pm  left join fetch pm.employee emp left join fetch emp.user where emp.id =:empId and ts.startDate =:startDate and ts.projectMembers.id IN ( select pm.id from ProjectMembers pm where  pm.projects.id  IN ( select pah.projectId from ProjectApproverHierarchy pah where pah.approverUserId =:userId))";
				List<TimesheetSubmissions> timesheetSubmissionsList1 = session.createQuery(query1)
																			  .setInteger("empId", empId)	
						                                                      .setInteger("userId", userId.intValue())
						                                                      .setParameter("startDate", startDate).list();
			    Boolean isRejected = false; 
			    Boolean isAPPROVED = false; 
			    Boolean isSUBMITTEDFORAPPROVAL = false; 
			    Boolean isNEW = false;
			    String status = "";
				
			    
				for(TimesheetSubmissions ts1 : timesheetSubmissionsList1){
					combinedTotalHours = combinedTotalHours + Long.parseLong(ts1.getTotalHours());
					if( ts1.getSubmissionApprovalStatus() == Submission_ApprovalStatus.REJECTED){
						isRejected   = true;  
						status = "REJECTED";
					}
					else if(!isRejected  && ts1.getSubmissionApprovalStatus() == Submission_ApprovalStatus.NEW){
						 isNEW   = true;  
						 status = "NEW";
					}
					else if(!isRejected && !isNEW &&  ts1.getSubmissionApprovalStatus() == Submission_ApprovalStatus.SUBMITTEDFORAPPROVAL){
						 isSUBMITTEDFORAPPROVAL   = true;  
						 status = "SUBMITTED FOR APPROVAL";
					}
					else if(!isRejected && !isNEW&& !isSUBMITTEDFORAPPROVAL && ts1.getSubmissionApprovalStatus() == Submission_ApprovalStatus.APPROVED ){
						 isAPPROVED   = true;  
						 status = "APPROVED";
					}
					
				}
				ts.setCombinedTotalHours(combinedTotalHours.toString());
				ts.setCombinedStatus(status);
			}
			
			
			/*
			String query1 = "from TimesheetSubmissions  ts  left join fetch ts.projectMembers pm  left join fetch pm.employee emp left join fetch emp.user  where (SELECT DISTINCT ts1.projectMembers.id  FROM TimesheetSubmissions ts1 ) IN ( select pm.id from ProjectMembers pm where  pm.projects.id  IN ( select pah.projectId from ProjectApproverHierarchy pah where pah.approverUserId =:userId))";  
			List<TimesheetSubmissions> timesheetSubmissionsList1 = session.createQuery(query1).setInteger("userId", userId.intValue()).list();
			*/
			
			return timesheetSubmissionsList;
			
			
			
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Collection<TimesheetSubmissions> getTimesheetSubmissionsByAproverId(Long projectMemberId, Long userId)
			throws Exception {
		// TODO Auto-generated method stub
		
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from TimesheetSubmissions ts ";  
		List<TimesheetSubmissions> timesheetSubmissionsList = session.createQuery(query).list();
		return timesheetSubmissionsList;
		
	}
	
}
