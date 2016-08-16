package com.bis.sourcelead.dao.impl;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.SubmissionApproverHierarchyDao;
import com.bis.sourcelead.dao.TimesheetSubmissionsDao;
import com.bis.sourcelead.entity.SubmissionApproverHierarchy;
import com.bis.sourcelead.entity.TimesheetSubmissions;
import com.bis.sourcelead.enums.Submission_ApprovalStatus;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;
import com.bis.sourcelead.exception.SourceLeadObjectNotFoundException;
import com.bis.sourcelead.util.CalendarUtil;
import com.bis.sourcelead.util.Constants;

@Repository
public class TimesheetSubmissionsDaoImpl implements TimesheetSubmissionsDao {

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
	private SubmissionApproverHierarchyDao submissionApproverHierarchyDao;
	
	private static final Logger logger = LoggerFactory.getLogger(TimesheetSubmissionsDaoImpl.class);
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public TimesheetSubmissions addTimesheetSubmissions(TimesheetSubmissions timesheetSubmissions) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		try {
			session.saveOrUpdate(timesheetSubmissions);
		}
		catch(Exception exception)	{
			StringBuilder message= new StringBuilder(MessageFormat.format(duplicateValue,"TIMESHEET_SUBMISSIONS"));
			throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
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
					session.saveOrUpdate(timesheetSubmissions);
				}
				
			}
			
		}
		catch(Exception exception)	{
			StringBuilder message= new StringBuilder(MessageFormat.format(duplicateValue,"TIMESHEET_SUBMISSIONS"));
			throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		
	}
	

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public TimesheetSubmissions updateTimesheetSubmissions(TimesheetSubmissions timesheetSubmissions) throws Exception {
		// TODO Auto-generated method stub
		Session session = this.sessionFactory.getCurrentSession();
	   if(timesheetSubmissions != null){
		   session.update(timesheetSubmissions);
	   }
	   if(timesheetSubmissions == null){
			StringBuilder message= new StringBuilder(MessageFormat.format(statusUpdate,"TIMESHEET_SUBMISSIONS"));
			throw new SourceLeadObjectNotFoundException(message.toString());
	   }
		
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
	
	}
	
	
	/**
	 * This method gives startDate of coming week from the existing submission records if exists
	 *
	 */
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
	public List<TimesheetSubmissions> getApprovalTimesheetSubmissions(Long userId) throws Exception {
			
		    // TODO Auto-generated method stub
			Session session = this.sessionFactory.getCurrentSession();  
			String query = "from TimesheetSubmissions ts  left join fetch ts.projectMembers pm  left join fetch pm.employee emp left join fetch emp.user user where user.userState != 'INACTIVE' and ts.projectMembers.id IN ( select pm.id from ProjectMembers pm where pm.projects.id  IN ( select pah.projectId from ProjectApproverHierarchy pah where pah.approverUserId =:userId))  GROUP BY emp.id";   
			List<TimesheetSubmissions> timesheetSubmissionsList = session.createQuery(query).setInteger("userId", userId.intValue()).list();
			
			
			for(TimesheetSubmissions ts : timesheetSubmissionsList){
			
			  String query1 = "select  distinct min(ts.startDate),max(ts.endDate) from TimesheetSubmissions ts  where ts.projectMembers.employee.id IN(select emp.id from Employees emp where emp.user.id = :userId)";
			  List results1 = session.createQuery(query1).setLong("userId", ts.getProjectMembers().getEmployee().getUser().getId()).list();
														
			  HashMap<String,Date> dateList =  new HashMap<String,Date>();;
			  if(results1 != null && !results1.isEmpty()){
				  for(Object result :results1){
					  Object[] list = (Object[])result;
					  java.sql.Timestamp startDateTimeStamp = (java.sql.Timestamp)list[0];
					  java.sql.Timestamp endDateTimeStamp = (java.sql.Timestamp)list[1];
					  
					  Date utilStartDate = null ;
					  Date utilEndDate = null ;
					  if(startDateTimeStamp != null){
						   utilStartDate = new Date(startDateTimeStamp.getTime());
					  }
					  if(endDateTimeStamp != null){
						   utilEndDate = new Date(endDateTimeStamp.getTime());
					  }
					 
					  dateList.put("STARTDATE",utilStartDate);
					  dateList.put("ENDDATE",utilEndDate);
				  }
			  }
			  ts.setDateList(dateList);
			}
			
			
			for(TimesheetSubmissions ts : timesheetSubmissionsList){
				Float combinedTotalHours = 0f ; 
				
				Date startDate = CalendarUtil.getCurrentWeekDayForTimesheets("MON");
				Long empId = ts.getProjectMembers().getEmployee().getId();
				
			    List<SubmissionApproverHierarchy> sahList = submissionApproverHierarchyDao.getSubmissionApproverHierarchyForEmployeeIdAndStartDate(empId, userId, startDate);
			/*	java.sql.Date startDate = new java.sql.Date(ts.getStartDate().getTime());
				Integer empId = ts.getProjectMembers().getEmployee().getId().intValue();
				String query1 = "from TimesheetSubmissions ts  left join fetch ts.projectMembers pm  left join fetch pm.employee emp left join fetch emp.user where emp.id =:empId and ts.startDate =:startDate and ts.projectMembers.id IN ( select pm.id from ProjectMembers pm where  pm.projects.id  IN ( select pah.projectId from ProjectApproverHierarchy pah where pah.approverUserId =:userId))";
				List<TimesheetSubmissions> timesheetSubmissionsList1 = session.createQuery(query1)
																			  .setInteger("empId", empId)	
						                                                      .setInteger("userId", userId.intValue())
						                                                      .setParameter("startDate", startDate).list();*/
			    Boolean isRejected = false; 
			    Boolean isAPPROVED = false; 
			    Boolean isSUBMITTEDFORAPPROVAL = false; 
			    Boolean isNEW = false;
			    String status = "";
				
			    
				for(SubmissionApproverHierarchy sah : sahList){
					if(sah.getSubmissionId().getTotalHours()!=null && !sah.getSubmissionId().getTotalHours().isEmpty()){
					combinedTotalHours = combinedTotalHours + Float.parseFloat(sah.getSubmissionId().getTotalHours());
					}
						if( sah.getSubmissionApprovalStatus() == Submission_ApprovalStatus.REJECTED){
							isRejected   = true;  
							status = "REJECTED";
							ts.setRejectedReason(sah.getRejectedReason());
						}
						else if(!isRejected  && sah.getSubmissionApprovalStatus() == Submission_ApprovalStatus.NEW){
							 isNEW   = true;  
							 status = "NEW";
						}
						else if(!isRejected && !isNEW &&  sah.getSubmissionApprovalStatus() == Submission_ApprovalStatus.SUBMITTEDFORAPPROVAL){
							 isSUBMITTEDFORAPPROVAL   = true;  
							 status = "SUBMITTED FOR APPROVAL";
						}
						else if(!isRejected && !isNEW&& !isSUBMITTEDFORAPPROVAL && sah.getSubmissionApprovalStatus() == Submission_ApprovalStatus.APPROVED ){
							 isAPPROVED   = true;  
							 status = "APPROVED";
						}
					
				
				ts.setCombinedTotalHours(combinedTotalHours.toString());
				ts.setCombinedStatus(status);
			 }
			}
			
			
			/*
			String query1 = "from TimesheetSubmissions  ts  left join fetch ts.projectMembers pm  left join fetch pm.employee emp left join fetch emp.user  where (SELECT DISTINCT ts1.projectMembers.id  FROM TimesheetSubmissions ts1 ) IN ( select pm.id from ProjectMembers pm where  pm.projects.id  IN ( select pah.projectId from ProjectApproverHierarchy pah where pah.approverUserId =:userId))";  
			List<TimesheetSubmissions> timesheetSubmissionsList1 = session.createQuery(query1).setInteger("userId", userId.intValue()).list();
			*/
			
			return timesheetSubmissionsList;
	}
	

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Collection<TimesheetSubmissions> getTimesheetSubmissionsByAproverId(Long projectMemberId, Long userId,Date startDate)
			throws Exception {
		// TODO Auto-generated method stub
		
		Session session = this.sessionFactory.getCurrentSession();
		java.sql.Date sDate = new java.sql.Date(startDate.getTime());
		String query1 = "from TimesheetSubmissions ts  left join fetch ts.projectMembers pm  left join fetch pm.employee emp left join fetch emp.user where emp.id =:empId and ts.status = 'ACTIVE' and ts.startDate =:startDate and ts.projectMembers.id IN ( select pm.id from ProjectMembers pm where  pm.projects.id  IN ( select pah.projectId from ProjectApproverHierarchy pah where pah.approverUserId =:userId))";
		List<TimesheetSubmissions> timesheetSubmissionsList1 = session.createQuery(query1)
																		  .setLong("empId", projectMemberId)	
					                                                      .setLong("userId", userId)
					                                                      .setParameter("startDate", startDate).list();
		  return timesheetSubmissionsList1;
   }
   
   @Override
	@Transactional(rollbackFor = { Exception.class })
	public Integer getTimeSheetSubmissionsCountBasedOnStatus(Long userId,String approvalStatus) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		
		String subQueryForUserIdChecking = "(emp.id IN(select emp.id from Employees emp where emp.user.id = :userId) OR pm.projects.id IN(select pah.projectId.id from ProjectApproverHierarchy pah where pah.approverUserId.id = :userId))";
		String query = "from  TimesheetSubmissions ts   left join fetch ts.projectMembers pm   left join fetch pm.employee emp where "+subQueryForUserIdChecking+" and  ts.submissionApprovalStatus = :approvalStatus)" ;
		List<TimesheetSubmissions> tslist =session.createQuery(query.toString()).setLong("userId", userId).setString("approvalStatus", approvalStatus).list(); 
		      
		
	    return tslist.size();
		  
		
	}


	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<TimesheetSubmissions> getAllEmployeeTimesheetSubmissions(Date startDate,Date endDate,Long  userId) throws Exception {
			
		    // TODO Auto-generated method stub
			Session session = this.sessionFactory.getCurrentSession();
			java.sql.Date sqlStartDate = null;
			if(startDate != null){
				sqlStartDate = new java.sql.Date(startDate.getTime());
			}
			
			String query = "from TimesheetSubmissions ts  left join fetch ts.projectMembers pm  left join fetch pm.projects pr  left join fetch pm.employee emp left join fetch emp.user where ( pm.id IN(select pmh.projectMemberId.id from ProjectMembersHistory pmh where  pmh.projectTerminationDate IS NULL OR (pmh.projectTerminationDate >= :startDate) ) ) and emp.user.id = :userId and ts.status ='ACTIVE' and ts.startDate = :startDate  order by pr.id ";  
			List<TimesheetSubmissions> timesheetSubmissionsList = session.createQuery(query).setLong("userId", userId).setParameter("startDate", sqlStartDate).list();
			return timesheetSubmissionsList;
	}
	
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public TimesheetSubmissions getAllApprovalEmployeeTimesheetSubmissions(Date startDate,Date endDate,Long  userId) throws Exception {
			
	    // TODO Auto-generated method stub
		Session session = this.sessionFactory.getCurrentSession();  
		String query = "from TimesheetSubmissions ts  left join fetch ts.projectMembers pm  left join fetch pm.employee emp left join fetch emp.user where ts.projectMembers.id IN ( select pm.id from ProjectMembers pm where  pm.projects.id  IN ( select pah.projectId from ProjectApproverHierarchy pah where pah.approverUserId =:userId))  and ts.startDate =:startDate GROUP BY emp.id,ts.startDate";   
		List<TimesheetSubmissions> timesheetSubmissionsList = session.createQuery(query).setInteger("userId", userId.intValue()).setParameter("startDate", startDate).list();
		
		
		for(TimesheetSubmissions ts : timesheetSubmissionsList){
			Float combinedTotalHours = 0f ; 
			Date sDate = ts.getStartDate();
			Long empId = ts.getProjectMembers().getEmployee().getId();
			
			
			 List<SubmissionApproverHierarchy> sahList = submissionApproverHierarchyDao.getSubmissionApproverHierarchyForEmployeeIdAndStartDate(empId, userId, sDate);
			 
			/*String query1 = "from TimesheetSubmissions ts  left join fetch ts.projectMembers pm  left join fetch pm.employee emp left join fetch emp.user where emp.id =:empId and ts.startDate =:startDate and ts.projectMembers.id IN ( select pm.id from ProjectMembers pm where  pm.projects.id  IN ( select pah.projectId from ProjectApproverHierarchy pah where pah.approverUserId =:userId))";
			List<TimesheetSubmissions> timesheetSubmissionsList1 = session.createQuery(query1)
																		  .setInteger("empId", empId)	
					                                                      .setInteger("userId", userId.intValue())
					                                                      .setParameter("startDate", sDate).list();*/
		    Boolean isRejected = false; 
		    Boolean isAPPROVED = false; 
		    Boolean isSUBMITTEDFORAPPROVAL = false; 
		    Boolean isNEW = false;
		    String status = "";
			
		    
			for(SubmissionApproverHierarchy sah : sahList){
				if(sah.getSubmissionId().getTotalHours()!=null && !sah.getSubmissionId().getTotalHours().isEmpty()){
					combinedTotalHours = combinedTotalHours + Float.parseFloat(sah.getSubmissionId().getTotalHours());
					}
					if( sah.getSubmissionApprovalStatus() == Submission_ApprovalStatus.REJECTED){
						isRejected   = true;  
						status = "REJECTED";
					}
					else if(!isRejected  && sah.getSubmissionApprovalStatus() == Submission_ApprovalStatus.NEW){
						 isNEW   = true;  
						 status = "NEW";
					}
					else if(!isRejected && !isNEW &&  sah.getSubmissionApprovalStatus() == Submission_ApprovalStatus.SUBMITTEDFORAPPROVAL){
						 isSUBMITTEDFORAPPROVAL   = true;  
						 status = "SUBMITTED FOR APPROVAL";
					}
					else if(!isRejected && !isNEW&& !isSUBMITTEDFORAPPROVAL && sah.getSubmissionApprovalStatus() == Submission_ApprovalStatus.APPROVED ){
						 isAPPROVED   = true;  
						 status = "APPROVED";
					}
				
			
			ts.setCombinedTotalHours(combinedTotalHours.toString());
			ts.setCombinedStatus(status);
		}
		}
		TimesheetSubmissions timesheetSubmissions = null;
		if(timesheetSubmissionsList != null && !timesheetSubmissionsList.isEmpty()){
			timesheetSubmissions = timesheetSubmissionsList.iterator().next();
		}
		return timesheetSubmissions;

	}
	
	
	
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public TimesheetSubmissions getAllApprovalEmployeeTimesheetSubmissionsByProjectMemberId(Date startDate,Date endDate,Long  userId,Long  memberId,Long employeeId) throws Exception {
			
	    // TODO Auto-generated method stub
		Session session = this.sessionFactory.getCurrentSession();  
		String query = "from TimesheetSubmissions ts  left join fetch ts.projectMembers pm  left join fetch pm.employee emp left join fetch emp.user where emp.id =:employeeId and ts.projectMembers.id IN ( select pm.id from ProjectMembers pm where pm.projects.id  IN ( select pah.projectId from ProjectApproverHierarchy pah where pah.approverUserId =:userId))  and ts.startDate =:startDate GROUP BY emp.id,ts.startDate";   
		List<TimesheetSubmissions> timesheetSubmissionsList = session.createQuery(query).setLong("employeeId", employeeId).setLong("userId", userId).setParameter("startDate", startDate).list();
		
		
		for(TimesheetSubmissions ts : timesheetSubmissionsList){
			Float combinedTotalHours = 0f ; 
			java.sql.Date sDate = new java.sql.Date(ts.getStartDate().getTime());
			Long empId = ts.getProjectMembers().getEmployee().getId();
			
		    List<SubmissionApproverHierarchy> sahList = submissionApproverHierarchyDao.getSubmissionApproverHierarchyForEmployeeIdAndStartDate(empId, userId, startDate);
			 
			/*String query1 = "from TimesheetSubmissions ts  left join fetch ts.projectMembers pm  left join fetch pm.employee emp left join fetch emp.user where emp.id =:employeeId  and ts.startDate =:startDate and ts.projectMembers.id IN ( select pm.id from ProjectMembers pm where  pm.projects.id  IN ( select pah.projectId from ProjectApproverHierarchy pah where pah.approverUserId =:userId))";
			List<TimesheetSubmissions> timesheetSubmissionsList1 = session.createQuery(query1)
					                                                      .setLong("employeeId", employeeId)
					                                                      .setLong("userId", userId)
					                                                      .setParameter("startDate", sDate).list();*/
		    Boolean isRejected = false; 
		    Boolean isAPPROVED = false; 
		    Boolean isSUBMITTEDFORAPPROVAL = false; 
		    Boolean isNEW = false;
		    String status = "";
			
		    
		    
		    for(SubmissionApproverHierarchy sah : sahList){
				if(sah.getSubmissionId().getTotalHours()!=null && !sah.getSubmissionId().getTotalHours().isEmpty()){
				combinedTotalHours = combinedTotalHours + Float.parseFloat(sah.getSubmissionId().getTotalHours());
				}
					if( sah.getSubmissionApprovalStatus() == Submission_ApprovalStatus.REJECTED){
						isRejected   = true;  
						status = "REJECTED";
					}
					else if(!isRejected  && sah.getSubmissionApprovalStatus() == Submission_ApprovalStatus.NEW){
						 isNEW   = true;  
						 status = "NEW";
					}
					else if(!isRejected && !isNEW &&  sah.getSubmissionApprovalStatus() == Submission_ApprovalStatus.SUBMITTEDFORAPPROVAL){
						 isSUBMITTEDFORAPPROVAL   = true;  
						 status = "SUBMITTED FOR APPROVAL";
					}
					else if(!isRejected && !isNEW&& !isSUBMITTEDFORAPPROVAL && sah.getSubmissionApprovalStatus() == Submission_ApprovalStatus.APPROVED ){
						 isAPPROVED   = true;  
						 status = "APPROVED";
					}
				
			
			   ts.setCombinedTotalHours(combinedTotalHours.toString());
			   ts.setCombinedStatus(status);
			   ts.setRejectedReason(sah.getRejectedReason());
		    }
		    
		    
			/*for(TimesheetSubmissions ts1 : timesheetSubmissionsList1){
				if(ts1.getTotalHours()!=null && !ts1.getTotalHours().isEmpty()){
				combinedTotalHours = combinedTotalHours + Float.parseFloat(ts1.getTotalHours());
				}
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
				
			
			ts.setCombinedTotalHours(combinedTotalHours.toString());
			ts.setCombinedStatus(status);
		}*/
		}
		TimesheetSubmissions timesheetSubmissions = null;
		if(timesheetSubmissionsList != null && !timesheetSubmissionsList.isEmpty()){
			timesheetSubmissions = timesheetSubmissionsList.iterator().next();
		}
		return timesheetSubmissions;

	}
	
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public HashMap<String,Date> getMinAndMaxTimesheetSubmissionDatesForEmployee(Long  userId) throws Exception {
		
		//(ts.projectMembers.projectEndDate IS NULL OR  (ts.projectMembers.projectEndDate > ts.startDate))
		
		  Session session = this.sessionFactory.getCurrentSession();
		  String query = "select  distinct min(ts.startDate),max(ts.endDate) from TimesheetSubmissions ts  where ts.status = 'ACTIVE' and ts.projectMembers.employee.id IN(select emp.id from Employees emp where emp.user.id = :userId)";
		  List results = session.createQuery(query).setLong("userId", userId).list();
													
		  HashMap<String,Date> dateList =  new HashMap<String,Date>();;
		  if(results != null && !results.isEmpty()){
			  for(Object result :results){
				  Object[] list = (Object[])result;
				  java.sql.Timestamp startDateTimeStamp = (java.sql.Timestamp)list[0];
				  java.sql.Timestamp endDateTimeStamp = (java.sql.Timestamp)list[1];
				  
				  Date utilStartDate = null ;
				  Date utilEndDate = null ;
				  if(startDateTimeStamp != null){
					   utilStartDate = new Date(startDateTimeStamp.getTime());
				  }
				  if(endDateTimeStamp != null){
					   utilEndDate = new Date(endDateTimeStamp.getTime());
				  }
				 
				  dateList.put("STARTDATE",utilStartDate);
				  dateList.put("ENDDATE",utilEndDate);
			  }
		  }
		 return dateList;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Float getTotalApprovedOrRejectedHoursByuserId(Long userId, String approvalStatus) {
		Session session = this.sessionFactory.getCurrentSession();
		String subQueryForUserIdChecking = "(emp.id IN(select emp.id from Employees emp where emp.user.id = :userId) OR pm.projects.id IN(select pah.projectId.id from ProjectApproverHierarchy pah where pah.approverUserId.id = :userId))";
		String query = "from TimesheetSubmissions ts  left join fetch ts.projectMembers pm  left join fetch pm.employee emp where "+subQueryForUserIdChecking+" and  ts.submissionApprovalStatus = :approvalStatus)" ;
		List<TimesheetSubmissions> tslist =session.createQuery(query.toString()).setLong("userId", userId).setString("approvalStatus", approvalStatus).list(); 
		Float totalHours = 0.0F;
		if(tslist !=null){
		   for (int i = 0; i < tslist.size(); i++) {
			   if(tslist.get(i).getTotalHours() != null && ! "".equalsIgnoreCase(tslist.get(i).getTotalHours())){
				   totalHours = totalHours + Float.parseFloat(tslist.get(i).getTotalHours());
			   }
		   }
	   }
		return totalHours;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Float getTotalDraftAndSubmittedHoursByuserId(Long userId, String submissionStatus) {
		Session session = this.sessionFactory.getCurrentSession();
		String subQueryForUserIdChecking = "(emp.id IN(select emp.id from Employees emp where emp.user.id = :userId) OR pm.projects.id IN(select pah.projectId.id from ProjectApproverHierarchy pah where pah.approverUserId.id = :userId))";
		String query = "from TimesheetSubmissions ts  left join fetch ts.projectMembers pm  left join fetch pm.employee emp where "+subQueryForUserIdChecking+" and  ts.submissionStatus = :submissionStatus)" ;
		List<TimesheetSubmissions> tslist =session.createQuery(query.toString()).setLong("userId", userId).setString("submissionStatus", submissionStatus).list(); 
		Float totalHours = 0.0F;
		if(tslist !=null){
		   for (int i = 0; i < tslist.size(); i++) {
			   if(tslist.get(i).getTotalHours() != null && ! "".equalsIgnoreCase(tslist.get(i).getTotalHours())){
				   totalHours = totalHours + Float.parseFloat(tslist.get(i).getTotalHours());
			   }
		   }  
	   }
		return totalHours;
	}
		
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Collection<TimesheetSubmissions> getTimeSheetSubmissionsListByStatus(Long userId, String approvalStatus)
			throws Exception {

		Session session = this.sessionFactory.getCurrentSession();

		String subQueryForUserIdChecking = "(emp.id IN(select emp.id from Employees emp where emp.user.id = :userId) OR pm.projects.id IN(select pah.projectId.id from ProjectApproverHierarchy pah where pah.approverUserId.id = :userId))";
		String query = "from  TimesheetSubmissions ts   left join fetch ts.projectMembers pm  left join fetch pm.projects pr   left join fetch pm.employee emp where "
				+ subQueryForUserIdChecking + " and  ts.submissionApprovalStatus = :approvalStatus)";
		List<TimesheetSubmissions> tslist = session.createQuery(query.toString()).setLong("userId", userId)
				.setString("approvalStatus", approvalStatus).list();

		return tslist;

	}



@Override
	@Transactional(rollbackFor = { Exception.class })
	public Collection<TimesheetSubmissions> getProjectsBasedOnUserIdAndStatusAndClientForFilters(Long userId,
			String clientId,String values) throws Exception {
		

	    Collection<TimesheetSubmissions> tslist = null;
        Session session = this.sessionFactory.getCurrentSession();
        String subQuery2 = "";
		String[] tokens = values.split(",");
		ArrayList<TimesheetSubmissions> timesheetSubmissions = new ArrayList<TimesheetSubmissions>();
		String[] clients = clientId.split(",");
		if(clients[0].length()>0){
			
				for(String client :clients){
						if(Arrays.asList(tokens).contains("All")){
							subQuery2 = subQuery2+ "ts.submissionApprovalStatus = 'All' or ";
						}
						  
						if(Arrays.asList(tokens).contains("Approved")){
							subQuery2 = subQuery2+ "ts.submissionApprovalStatus = 'Approved' or ";
						}
						
						if(Arrays.asList(tokens).contains("Draft")){
							subQuery2 = subQuery2 + "ts.submissionApprovalStatus = 'Draft' or ";
						}
						if(Arrays.asList(tokens).contains("Rejected")){
							subQuery2 = subQuery2 + "ts.submissionApprovalStatus = 'Rejected' or ";
						}
						
						if (tokens[0] != null && tokens[0].length() > 0){
							subQuery2 = "and (" + subQuery2.substring(0,subQuery2.length() - 4)+")";
						}
					 String subquery1 ="pm.projects.orgRelationId ="+client;
					 String subQueryForUserIdChecking = "pm.projects.id IN(select pah.projectId.id from ProjectApproverHierarchy pah where pah.approverUserId.id = :userId))";
					 String query = "from  TimesheetSubmissions ts   left join fetch ts.projectMembers pm   left join fetch pm.employee emp where "+subQueryForUserIdChecking+"  "+subQuery2+" and "+subquery1+" )" ;
					 tslist = session.createQuery(query.toString()).setLong("userId", userId).list(); 
					 
					 timesheetSubmissions.addAll(tslist);
		}	
		}else{
			
			if(Arrays.asList(tokens).contains("All")){
				subQuery2 = subQuery2+ "ts.submissionApprovalStatus = 'All' or ";
			}
			  
			if(Arrays.asList(tokens).contains("Approved")){
				subQuery2 = subQuery2+ "ts.submissionApprovalStatus = 'Approved' or ";
			}
			
			if(Arrays.asList(tokens).contains("Draft")){
				subQuery2 = subQuery2 + "ts.submissionApprovalStatus = 'Draft' or ";
			}
			if(Arrays.asList(tokens).contains("Rejected")){
				subQuery2 = subQuery2 + "ts.submissionApprovalStatus = 'Rejected' or ";
			}
			
			if (tokens[0] != null && tokens[0].length() > 0){
				subQuery2 = "and (" + subQuery2.substring(0,subQuery2.length() - 4)+")";
			}
		
		String subQueryForUserIdChecking = "(emp.id IN(select emp.id from Employees emp where emp.user.id = :userId) OR pm.projects.id IN(select pah.projectId.id from ProjectApproverHierarchy pah where pah.approverUserId.id = :userId))";
		String query = "from  TimesheetSubmissions ts   left join fetch ts.projectMembers pm   left join fetch pm.employee emp where "+subQueryForUserIdChecking+"  "+subQuery2+" )" ;
		tslist = session.createQuery(query.toString()).setLong("userId", userId).list();
			
			
			
		}
		
		Collection<TimesheetSubmissions> userCollection = new HashSet<TimesheetSubmissions>(timesheetSubmissions);
		return userCollection;
	}


	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Integer getUsersTimeSheetSubmissionsCountBasedOnStatus(Long userId, String approvalStatus,Long orgLocationId) throws Exception {
	
	Session session = this.sessionFactory.getCurrentSession();
	
	String query = "from TimesheetSubmissions ts left join fetch ts.projectMembers pm left join fetch pm.employee emp left join fetch emp.orgLocations "
			+ "where (emp.id IN(select emp.id from Employees emp where emp.user.id = :userId and emp.orgLocations.id = :orgLocationId ))"
			+ " and ts.submissionApprovalStatus = :approvalStatus)" ;
	List<TimesheetSubmissions> tslist =session.createQuery(query.toString()).setLong("userId", userId)
			.setString("approvalStatus", approvalStatus).setLong("orgLocationId", orgLocationId).list(); 
	      
	return tslist.size();
	  

}
	

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<TimesheetSubmissions> getSubmissionApproverHierarchyForEmployeeList(Long approverUserId) {
		
		Session session = this.sessionFactory.getCurrentSession();  
		String query = "from TimesheetSubmissions   ts  left join fetch ts.projectMembers pm  left join fetch pm.employee emp left join fetch emp.user where ts.projectMembers.id IN ( select pm.id from ProjectMembers pm where  pm.projects.id  IN ( select pah.projectId from ProjectApproverHierarchy pah where pah.approverUserId =:approverUserId))  GROUP BY emp.id";   
		List<TimesheetSubmissions> timesheetSubmissionsList = session.createQuery(query).setInteger("approverUserId", approverUserId.intValue()).list();
		
		return timesheetSubmissionsList;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Collection<TimesheetSubmissions> getApprovalTimesheetSubmissionsByUserIdAndEmplyeeId(Long userId, String employeeId) throws Exception {

		
	    // TODO Auto-generated method stub
		Session session = this.sessionFactory.getCurrentSession();
		ArrayList<TimesheetSubmissions> finalList = new ArrayList<TimesheetSubmissions>();
		List<TimesheetSubmissions> timesheetSubmissionsList = null;
		String[] employee = employeeId.split(",");
		for(String emp :employee){
			
			String subquery  = "and ts.projectMembers.employee.id  =:emp";
		
		String query = "from TimesheetSubmissions ts  left join fetch ts.projectMembers pm  left join fetch pm.employee emp left join fetch emp.user where ts.projectMembers.id IN ( select pm.id from ProjectMembers pm where  pm.projects.id  IN ( select pah.projectId from ProjectApproverHierarchy pah where pah.approverUserId =:userId))  "+subquery+" GROUP BY emp.id";   
		timesheetSubmissionsList = session.createQuery(query).setInteger("userId", userId.intValue()).setString("emp", emp).list();
		
		for(TimesheetSubmissions ts : timesheetSubmissionsList){
		
		  String query1 = "select  distinct min(ts.startDate),max(ts.endDate) from TimesheetSubmissions ts  where ts.projectMembers.employee.id IN(select emp.id from Employees emp where emp.user.id = :userId)";
		  List results1 = session.createQuery(query1).setLong("userId", ts.getProjectMembers().getEmployee().getUser().getId()).list();
													
		  HashMap<String,Date> dateList =  new HashMap<String,Date>();;
		  if(results1 != null && !results1.isEmpty()){
			  for(Object result :results1){
				  Object[] list = (Object[])result;
				  java.sql.Timestamp startDateTimeStamp = (java.sql.Timestamp)list[0];
				  java.sql.Timestamp endDateTimeStamp = (java.sql.Timestamp)list[1];
				  
				  Date utilStartDate = null ;
				  Date utilEndDate = null ;
				  if(startDateTimeStamp != null){
					   utilStartDate = new Date(startDateTimeStamp.getTime());
				  }
				  if(endDateTimeStamp != null){
					   utilEndDate = new Date(endDateTimeStamp.getTime());
				  }
				 
				  dateList.put("STARTDATE",utilStartDate);
				  dateList.put("ENDDATE",utilEndDate);
			  }
		  }
		  ts.setDateList(dateList);
		}
		
		
		for(TimesheetSubmissions ts : timesheetSubmissionsList){
			Float combinedTotalHours = 0f ; 
			
			Date startDate = CalendarUtil.getCurrentWeekDayForTimesheets("MON");
			Long empId = ts.getProjectMembers().getEmployee().getId();
			
		    List<SubmissionApproverHierarchy> sahList = submissionApproverHierarchyDao.getSubmissionApproverHierarchyForEmployeeIdAndStartDate(empId, userId, startDate);

		    Boolean isRejected = false; 
		    Boolean isAPPROVED = false; 
		    Boolean isSUBMITTEDFORAPPROVAL = false; 
		    Boolean isNEW = false;
		    String status = "";
			
		    
			for(SubmissionApproverHierarchy sah : sahList){
				if(sah.getSubmissionId().getTotalHours()!=null && !sah.getSubmissionId().getTotalHours().isEmpty()){
				combinedTotalHours = combinedTotalHours + Float.parseFloat(sah.getSubmissionId().getTotalHours());
				}
					if( sah.getSubmissionApprovalStatus() == Submission_ApprovalStatus.REJECTED){
						isRejected   = true;  
						status = "REJECTED";
					}
					else if(!isRejected  && sah.getSubmissionApprovalStatus() == Submission_ApprovalStatus.NEW){
						 isNEW   = true;  
						 status = "NEW";
					}
					else if(!isRejected && !isNEW &&  sah.getSubmissionApprovalStatus() == Submission_ApprovalStatus.SUBMITTEDFORAPPROVAL){
						 isSUBMITTEDFORAPPROVAL   = true;  
						 status = "SUBMITTED FOR APPROVAL";
					}
					else if(!isRejected && !isNEW&& !isSUBMITTEDFORAPPROVAL && sah.getSubmissionApprovalStatus() == Submission_ApprovalStatus.APPROVED ){
						 isAPPROVED   = true;  
						 status = "APPROVED";
					}
				
			
			ts.setCombinedTotalHours(combinedTotalHours.toString());
			ts.setCombinedStatus(status);
		 }
		}
		finalList.addAll(timesheetSubmissionsList);
	}
		return finalList;

	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public TimesheetSubmissions getTimesheetSubmissionsByMemberIdAndStartDate(Long memberId, Date startDate)
			throws Exception {
        // TODO Auto-generated method stub
		Session session = this.sessionFactory.getCurrentSession();
		java.sql.Date sqlStartDate = null;
		if(startDate != null){
			sqlStartDate = new java.sql.Date(startDate.getTime());
		}
		
		String query = "from TimesheetSubmissions ts  left join fetch ts.projectMembers pm  left join fetch pm.projects pr  left join fetch pm.employee emp left join fetch emp.user where pm.id = :memberId and ts.startDate = :startDate ";  
		List<TimesheetSubmissions> timesheetSubmissionsList = session.createQuery(query).setLong("memberId", memberId).setParameter("startDate", sqlStartDate).list();
		TimesheetSubmissions ts = null;
		if(timesheetSubmissionsList != null && !timesheetSubmissionsList.isEmpty()){
			ts = timesheetSubmissionsList.iterator().next();
		}
		return ts;
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<TimesheetSubmissions> getAllTimesheetSubmissionsGreaterThanProjectMemberTerminatedDateAndMemberId(Long memberId, Date terminatedDate)
			throws Exception {
        // TODO Auto-generated method stub
		Session session = this.sessionFactory.getCurrentSession();
		java.sql.Date sqlTerminatedDate = null;
		if(terminatedDate != null){
			sqlTerminatedDate = new java.sql.Date(terminatedDate.getTime());
		}
		
		String query = "from TimesheetSubmissions ts  left join fetch ts.projectMembers pm  left join fetch pm.projects pr  left join fetch pm.employee emp left join fetch emp.user where pm.id = :memberId and ts.startDate > :terminatedDate ";  
		List<TimesheetSubmissions> timesheetSubmissionsList = session.createQuery(query).setLong("memberId", memberId).setParameter("terminatedDate", sqlTerminatedDate).list();
		return timesheetSubmissionsList;
	}


	@Override
	public List<TimesheetSubmissions> getTotalDraftAndSubmittedListByuserIdAndOrgLocationIdForEmployeeLogin(Long userId,
			String status, Long orgLocationId) throws Exception {
		


        Session session = this.sessionFactory.getCurrentSession();
       
        String subquery =   "ts.id IN(select sah.submissionId from SubmissionApproverHierarchy sah where sah.submissionStatus = :status)";
		String query = "from  TimesheetSubmissions   ts  left join fetch ts.projectMembers pm left join fetch pm.projects left join fetch pm.employee emp where  emp.orgLocations.id = :orgLocationId and  emp.user.id = :userId and  sah.submissionStatus = :status order by pm.projects.id ASC)" ;
		@SuppressWarnings("unchecked")
		List<TimesheetSubmissions> tslist =session.createQuery(query.toString()).setLong("userId", userId).setLong("orgLocationId",orgLocationId).setString("status", status).setCacheable(true).list(); 
		
		return tslist;
	}


}
