package com.bis.sourcelead.timesheet.dao.impl;

import java.sql.Timestamp;
import java.text.DecimalFormat;
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

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.OrgLocationsDao;
import com.bis.sourcelead.dao.ProjectMembersDao;
import com.bis.sourcelead.dao.ProjectsDao;
import com.bis.sourcelead.entity.ExpensesEntryDocuments;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.ProjectMembers;
import com.bis.sourcelead.entity.Projects;
import com.bis.sourcelead.entity.SubmissionApproverHierarchy;
import com.bis.sourcelead.entity.TimesheetSubmissions;
import com.bis.sourcelead.enums.Submission_ApprovalStatus;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;
import com.bis.sourcelead.exception.SourceLeadObjectNotFoundException;
import com.bis.sourcelead.timesheet.dao.SubmissionApproverHierarchyDao;
import com.bis.sourcelead.timesheet.dao.TimesheetSubmissionsDao;
import com.bis.sourcelead.util.CalendarUtil;

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
	
	@Autowired
	private ProjectsDao projectsDao;
	
	@Autowired
	private OrgLocationsDao orgLocationsDao;
	
	@Autowired
	private ProjectMembersDao projectMembersDao;
	
	
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
	public List<TimesheetSubmissions> saveAllTimesheetSubmissions(List<TimesheetSubmissions> timesheetSubmissionsList) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if(timesheetSubmissionsList != null && !timesheetSubmissionsList.isEmpty()){
				for(TimesheetSubmissions timesheetSubmissions : timesheetSubmissionsList){
					session.saveOrUpdate(timesheetSubmissions);
					/*if(timesheetSubmissions.getApproverHierarchyList() != null && !timesheetSubmissions.getApproverHierarchyList().isEmpty()) {
						for(SubmissionApproverHierarchy sah:timesheetSubmissions.getApproverHierarchyList()) {
							      sah.setSubmissionId(timesheetSubmissions);
							      			session.saveOrUpdate(sah);
						}
					}*/
				}
				
			}
			
		}
		catch(Exception exception)	{
			StringBuilder message= new StringBuilder(MessageFormat.format(duplicateValue,"TIMESHEET_SUBMISSIONS"));
			throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		return timesheetSubmissionsList;
		
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
	 * @throws Exception 
	 *
	 */
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Date getNextMondayByProjectMemberId(Long pojectMemberId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		ProjectMembers projectMembers = projectMembersDao.getProjectMemberById(pojectMemberId);
		Projects projects = projectsDao.getProjectById(projectMembers.getProjects().getId());
         String query = "select MAX(ts.startDate) from TimesheetSubmissions ts  where ts.projectMembers.id =:pojectMemberId and ts.status = '1' ";
         List<java.sql.Timestamp>  wtsList = (List<java.sql.Timestamp>)session.createQuery(query).setLong("pojectMemberId", pojectMemberId).list();
         Date date = null;
          if(wtsList != null && !wtsList.isEmpty()){
            java.sql.Timestamp sqlDate = wtsList.iterator().next();
         	if(sqlDate != null){
         		Calendar startCal = Calendar.getInstance(Constants.TIMEZONE_CENTRAL);
				startCal.setLenient(false);
				startCal.setTime(new Date(sqlDate.getTime()));
				Calendar monday = CalendarUtil.getNextWeekDayByCalendar(startCal, "MON",projects.getOrgLocationsId().getDateFormat());
				date = monday.getTime();
         	}
          }
          return date;
	}

	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<TimesheetSubmissions> getApprovalTimesheetSubmissions(Long userId,Long locationsId) throws Exception {
		/*String sub = ":approverUserId IN (select userRole.user.id from UserRole userRole where userRole.roles = '1')";*/
		Session session = this.sessionFactory.getCurrentSession();  
		String query = "from TimesheetSubmissions ts  left join fetch ts.projectMembers pm left join fetch pm.projects project left join fetch project.orgLocationsId left join fetch pm.employee emp left join fetch emp.user  user where project.orgLocationsId.id = :locationsId and user.id IN(select userRole.user.id from UserRole userRole where userRole.userState != 'INACTIVE')  and  ts.status != '0' "
				+ " and ( "
				+ "  ( ts.projectMembers.id IN ( select pm.id from ProjectMembers pm where pm.projects.id  IN ( select pah.projectId from ProjectApproverHierarchy pah where pah.approverUserId =:approverUserId)) ) "
				+ "   OR "
				+     Constants.loginUserAsAdmin+""
			    + ")  GROUP BY emp.id";   
		List<TimesheetSubmissions> timesheetSubmissionsList = session.createQuery(query).setLong("approverUserId", userId).setLong("userId", userId).setLong("locationsId", locationsId).list();
	    for(TimesheetSubmissions ts : timesheetSubmissionsList){
			HashMap<String, Date> submissionDateRange =	 getMinAndMaxTimesheetSubmissionDatesForApprover(userId,ts.getProjectMembers().getEmployee().getUser().getId(),locationsId);
			Date employeeStartDate = submissionDateRange.get("STARTDATE");
			Date employeeEndDate = submissionDateRange.get("ENDDATE");
			HashMap<String,Date> dateList =  new HashMap<String,Date>(); 
			dateList.put("STARTDATE",employeeStartDate);
			dateList.put("ENDDATE",employeeEndDate);
		    ts.setDateList(dateList);
		}
		return timesheetSubmissionsList;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<TimesheetSubmissions> getTimesheetSubmissionsByAproverId(Long empId, Long userId,Date startDate)
			throws Exception {
		// TODO Auto-generated method stub
		
		Session session = this.sessionFactory.getCurrentSession();
		/*String sub = ":userId IN (select userRole.user.id from UserRole userRole where userRole.roles = '1')";*/
		java.sql.Date sDate = new java.sql.Date(startDate.getTime());
		String query1 = "from TimesheetSubmissions ts  left join fetch ts.projectMembers pm left join fetch pm.projects project left join fetch project.orgLocationsId  left join fetch pm.employee emp left join fetch emp.user where emp.id =:empId and ts.status = '1' and ts.startDate =:startDate and ( ( ts.projectMembers.id IN ( select pm.id from ProjectMembers pm where  pm.projects.id  IN ( select pah.projectId from ProjectApproverHierarchy pah where pah.approverUserId =:userId))) OR "+Constants.loginUserAsAdmin+" ) ";
		List<TimesheetSubmissions> timesheetSubmissionsList1 = session.createQuery(query1)
																		  .setLong("empId", empId)	
					                                                      .setLong("userId", userId)
					                                                      .setParameter("startDate", startDate).list();
		  return timesheetSubmissionsList1;
   }
   
   @Override
	@Transactional(rollbackFor = { Exception.class })
	public Integer getTimeSheetSubmissionsCountBasedOnStatus(Long userId,String approvalStatus) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		
		String subQueryForUserIdChecking = "(emp.id IN(select emp.id from Employees emp where emp.user.id = :userId) OR pm.projects.id IN(select pah.projectId.id from ProjectApproverHierarchy pah where pah.approverUserId.id = :userId))";
		String query = "from  TimesheetSubmissions ts   left join fetch ts.projectMembers pm   left join fetch pm.employee emp where "+subQueryForUserIdChecking+" and ts.id IN(select sah.submissionId.id from SubmissionApproverHierarchy sah where sah.hierarchyId.approverUserId.id = :userId and sah.submissionApprovalStatus = :approvalStatus)" ;
		List<TimesheetSubmissions> tslist =session.createQuery(query.toString()).setLong("userId", userId).setString("approvalStatus", approvalStatus).list(); 
		      
		
	    return tslist.size();
		  
		
	}
   
   /*@Override
   @Transactional(rollbackFor = { Exception.class })
   public Integer getUsersTimeSheetSubmissionsCountBasedOnStatus(Long userId, String approvalStatus,Long orgLocationId) throws Exception {
   
   Session session = this.sessionFactory.getCurrentSession();
   
   String query = "from TimesheetSubmissions ts left join fetch ts.projectMembers pm left join fetch pm.employee emp left join fetch emp.orgLocations "
           + "where (emp.id IN(select emp.id from Employees emp where emp.user.id = :userId and emp.orgLocations.id = :orgLocationId ))"
           + " and ts.id IN(select sah.submissionId.id SubmissionApproverHierarchy sah where sah.hierarchyId.approverUserId = :userId and sah.submissionApprovalStatus = :approvalStatus))" ;
   List<TimesheetSubmissions> tslist =session.createQuery(query.toString()).setLong("userId", userId)
           .setString("approvalStatus", approvalStatus).setLong("orgLocationId", orgLocationId).list(); 
         
   return tslist.size();
     

}*/


	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<TimesheetSubmissions> getAllEmployeeTimesheetSubmissions(Date startDate,Date endDate,Long  userId) throws Exception {
			
		    // TODO Auto-generated method stub
			Session session = this.sessionFactory.getCurrentSession();
			java.sql.Date sqlStartDate = null;
			if(startDate != null){
				sqlStartDate = new java.sql.Date(startDate.getTime());
			}
			
			String query = "from TimesheetSubmissions ts  left join fetch ts.projectMembers pm  left join fetch pm.projects pr  left join fetch pm.employee emp left join fetch emp.user where ( pm.id IN(select pmh.projectMemberId.id from ProjectMembersHistory pmh where  pmh.projectTerminationDate IS NULL OR (pmh.projectTerminationDate >= :startDate) ) ) and emp.user.id = :userId and ts.status ='1' and ts.startDate = :startDate  order by pr.id ";  
			List<TimesheetSubmissions> timesheetSubmissionsList = session.createQuery(query).setLong("userId", userId).setParameter("startDate", sqlStartDate).list();
			return timesheetSubmissionsList;
	}
	
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public String getWeekCommentByCurrentFrequency(Date startDate,Date endDate,Long projectId,Long  masterCandidateId) throws Exception {
			
		    // TODO Auto-generated method stub
	        String weekComment = "";
			Session session = this.sessionFactory.getCurrentSession();
			java.sql.Date sqlStartDate = null;
			if(startDate != null){
				sqlStartDate = new java.sql.Date(startDate.getTime());
			}
			
			java.sql.Date sqlEndDate = null;
			if(endDate != null){
				sqlEndDate = new java.sql.Date(endDate.getTime());
			}
			
			String query = "from TimesheetSubmissions ts where "
					+ "   ts.masterCandidateId.id  = :masterCandidateId  "
					+ "  and ts.projects.id = :projectId  "
					+ "  and ts.startDate BETWEEN '"+sqlStartDate+"' and '"+sqlEndDate+"'  and ts.weekcomments IS NOT NULL ";  
			List<TimesheetSubmissions> timesheetSubmissionsList = session.createQuery(query).setLong("projectId", projectId).setLong("masterCandidateId", masterCandidateId).list();
			if(timesheetSubmissionsList != null && !timesheetSubmissionsList.isEmpty()) {
				weekComment = timesheetSubmissionsList.iterator().next().getWeekcomments();
			}
			return weekComment;
	}
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public void updateWeekCommentForAllSubmissions(String weekComment,Date startDate,Date endDate,Long projectId,Long  masterCandidateId) throws Exception {
			
			Session session = this.sessionFactory.getCurrentSession();
			java.sql.Date sqlStartDate = null;
			if(startDate != null){
				sqlStartDate = new java.sql.Date(startDate.getTime());
			}
			
			java.sql.Date sqlEndDate = null;
			if(endDate != null){
				sqlEndDate = new java.sql.Date(endDate.getTime());
			}
			String qryString = "update TimesheetSubmissions ts set ts.weekcomments= :weekComment where "
					+ "  ts.masterCandidateId.id  = :masterCandidateId"
					+ "   and  ts.projects.id = :projectId   "
					+ "  and ts.startDate BETWEEN '"+sqlStartDate+"' and '"+sqlEndDate+"'";
			
			
			int count = session.createQuery(qryString).setString("weekComment", weekComment).setLong("projectId", projectId).setLong("masterCandidateId", masterCandidateId).executeUpdate();
	}
	
	
	
	/*@Override
	@Transactional(rollbackFor = { Exception.class })
	public TimesheetSubmissions getAllEmployeeTimesheetSubmissionsByProjectMemberId(Date startDate,Date endDate,Long projectMemberId) throws Exception {
			
		    // TODO Auto-generated method stub
			Session session = this.sessionFactory.getCurrentSession();
			java.sql.Date sqlStartDate = null;
			if(startDate != null){
				sqlStartDate = new java.sql.Date(startDate.getTime());
			}
			java.sql.Date sqlEndDate = null;
			if(endDate != null){
				sqlEndDate = new java.sql.Date(endDate.getTime());
			}
			String query = "from TimesheetSubmissions ts  left join fetch ts.projectMembers pm  left join fetch pm.projects pr  left join fetch pm.employee emp left join fetch emp.user where pm.id = :projectMemberId and ts.status ='1' and ts.startDate = :startDate and ts.endDate = :endDate  order by pr.id ";  
			List<TimesheetSubmissions> timesheetSubmissionsList = session.createQuery(query).setLong("projectMemberId", projectMemberId).setParameter("startDate", sqlStartDate).setParameter("endDate", sqlEndDate).list();
			TimesheetSubmissions ts = null;
			if(timesheetSubmissionsList != null && !timesheetSubmissionsList.isEmpty()){
				ts = timesheetSubmissionsList.iterator().next();
			}
			return ts;
	}*/
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public TimesheetSubmissions getAllCandidateTimesheetSubmissionsByProjectIdAndMasterCandidateId(Date startDate,Date endDate,Long projectId,Long masterCandidateId) throws Exception {
			
		    // TODO Auto-generated method stub
			Session session = this.sessionFactory.getCurrentSession();
			java.sql.Date sqlStartDate = null;
			if(startDate != null){
				sqlStartDate = new java.sql.Date(startDate.getTime());
			}
			java.sql.Date sqlEndDate = null;
			if(endDate != null){
				sqlEndDate = new java.sql.Date(endDate.getTime());
			}
			String query = "from TimesheetSubmissions ts  left join fetch ts.projectMembers pm  left join fetch ts.projects pr left join fetch ts.masterCandidateId mc  left join fetch pm.employee emp left join fetch emp.user where pr.id = :projectId and mc.id = :masterCandidateId and ts.status ='1' and ts.startDate = :startDate and ts.endDate = :endDate  order by pr.id ";  
			List<TimesheetSubmissions> timesheetSubmissionsList = session.createQuery(query).setLong("masterCandidateId", masterCandidateId).setLong("projectId", projectId).setParameter("startDate", sqlStartDate).setParameter("endDate", sqlEndDate).list();
			TimesheetSubmissions ts = null;
			if(timesheetSubmissionsList != null && !timesheetSubmissionsList.isEmpty()){
				ts = timesheetSubmissionsList.iterator().next();
			}
			return ts;
	}
	
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<TimesheetSubmissions> getAllEmployeeTimesheetSubmissionsByProjectMemberIdForUpdation(Date startDate,Date endDate,Long projectMemberId) throws Exception {
			
		    // TODO Auto-generated method stub
			Session session = this.sessionFactory.getCurrentSession();
			java.sql.Date sqlStartDate = null;
			if(startDate != null){
				sqlStartDate = new java.sql.Date(startDate.getTime());
			}
			java.sql.Date sqlEndDate = null;
			if(endDate != null){
				sqlEndDate = new java.sql.Date(endDate.getTime());
			}
			String query = "from TimesheetSubmissions ts  left join fetch ts.projectMembers pm  left join fetch pm.projects pr  left join fetch pm.employee emp left join fetch emp.user where pm.id = :projectMemberId and ts.status ='1' and ts.startDate = :startDate and ts.endDate = :endDate  order by pr.id ";  
			List<TimesheetSubmissions> timesheetSubmissionsList = session.createQuery(query).setLong("projectMemberId", projectMemberId).setParameter("startDate", sqlStartDate).setParameter("endDate", sqlEndDate).list();
			return timesheetSubmissionsList;
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<TimesheetSubmissions> getAllCandidateTimesheetSubmissionsByProjectIdAndMasterCandidateIdForUpdation(Date startDate,Date endDate,Long projectId,Long masterCandidateId) throws Exception {
			
		    // TODO Auto-generated method stub
			Session session = this.sessionFactory.getCurrentSession();
			java.sql.Date sqlStartDate = null;
			if(startDate != null){
				sqlStartDate = new java.sql.Date(startDate.getTime());
			}
			java.sql.Date sqlEndDate = null;
			if(endDate != null){
				sqlEndDate = new java.sql.Date(endDate.getTime());
			}
			String query = "from TimesheetSubmissions ts  left join fetch ts.projectMembers pm  left join fetch pm.projects pr left join fetch ts.masterCandidateId mc  left join fetch pm.employee emp left join fetch emp.user where  pr.id = :projectId and mc.id = :masterCandidateId and ts.status ='1' and ts.startDate = :startDate and ts.endDate = :endDate  order by pr.id ";  
			List<TimesheetSubmissions> timesheetSubmissionsList = session.createQuery(query).setLong("projectId", projectId).setLong("masterCandidateId", masterCandidateId).setParameter("startDate", sqlStartDate).setParameter("endDate", sqlEndDate).list();
			return timesheetSubmissionsList;
	}
	
	
	
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public TimesheetSubmissions getAllApprovalEmployeeTimesheetSubmissions(Date startDate,Date endDate,Long  userId) throws Exception {
			
	    // TODO Auto-generated method stub
		Session session = this.sessionFactory.getCurrentSession();  
		String query = "from TimesheetSubmissions ts  left join fetch ts.projectMembers pm  left join fetch pm.employee emp left join fetch emp.user where ts.projectMembers.id IN ( select pm.id from ProjectMembers pm where  pm.projects.id  IN ( select pah.projectId from ProjectApproverHierarchy pah where pah.approverUserId =:userId))  and ts.status = '1'  and ts.startDate =:startDate GROUP BY emp.id,ts.startDate";   
		List<TimesheetSubmissions> timesheetSubmissionsList = session.createQuery(query).setInteger("userId", userId.intValue()).setParameter("startDate", startDate).list();
		
		
		for(TimesheetSubmissions ts : timesheetSubmissionsList){
			Float combinedTotalHours = 0f ; 
			Date sDate = ts.getStartDate();
			Long empId = ts.getProjectMembers().getEmployee().getId();
			
			
			 List<SubmissionApproverHierarchy> sahList = submissionApproverHierarchyDao.getSubmissionApproverHierarchyForEmployeeIdAndStartDate(empId, userId, ts.getId());
			 
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
	public TimesheetSubmissions getAllApprovalEmployeeTimesheetSubmissionsByEmployeeUserId(Date startDate,Date endDate,Long  userId,Long  empUserId,Long employeeId) throws Exception {
			
		/*String sub = ":userId IN (select userRole.user.id from UserRole userRole where userRole.roles = '1')";*/
		/*
		Session session = this.sessionFactory.getCurrentSession();  
		String query = "from TimesheetSubmissions ts  left join fetch ts.projectMembers pm left join fetch pm.projects project left join fetch project.orgLocationsId  left join fetch pm.employee emp left join fetch emp.user where  ts.projectMembers.employee.id IN(select emp.id from Employees emp where emp.user.id = :empUserId)  and (( ts.projectMembers.id IN ( select pm.id from ProjectMembers pm where pm.projects.id  IN ( select pah.projectId from ProjectApproverHierarchy pah where pah.approverUserId = :userId)) ) OR "+Constants.loginUserAsAdmin+")  and ts.status = '1' and ts.startDate =:startDate GROUP BY emp.id,ts.startDate";   
		session.createQuery(query).setLong("userId", userId).setLong("empUserId", empUserId).setParameter("startDate", sqlStartDate).list();*/
		java.sql.Date sqlStartDate = new java.sql.Date(startDate.getTime());
		List<TimesheetSubmissions> timesheetSubmissionsList = getTimesheetSubmissionsByAproverId(employeeId, userId, sqlStartDate);
		TimesheetSubmissions finalTs  = null;
		if(timesheetSubmissionsList != null && !timesheetSubmissionsList.isEmpty()){
			Float combinedTotalHours = 0f ; 
			Boolean isRejected = false; 
		    Boolean isAPPROVED = false; 
		    Boolean isSUBMITTEDFORAPPROVAL = false; 
		    Boolean isNEW = false;
			String status = "";
			String rejectReason = "";
			for(TimesheetSubmissions ts : timesheetSubmissionsList){
				java.sql.Date sDate = new java.sql.Date(ts.getStartDate().getTime());
				Long empId = ts.getProjectMembers().getEmployee().getId();
			    List<SubmissionApproverHierarchy> sahList = submissionApproverHierarchyDao.getSubmissionApproverHierarchyForEmployeeIdAndStartDate(employeeId, userId, ts.getId());
			    for(SubmissionApproverHierarchy sah : sahList){
					  if(sah.getSubmissionId().getTotalHours()!=null && !sah.getSubmissionId().getTotalHours().isEmpty()){
					    combinedTotalHours = combinedTotalHours + Float.parseFloat(sah.getSubmissionId().getTotalHours());
					  }
						if( sah.getSubmissionApprovalStatus() == Submission_ApprovalStatus.REJECTED){
							isRejected   = true;  
							status = "REJECTED";
							rejectReason = sah.getRejectedReason();
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
			     }
			     finalTs = ts;
			    }
			    DecimalFormat formatter = new DecimalFormat("#0.00");
			    finalTs.setCombinedTotalHours(formatter.format(combinedTotalHours));
			    finalTs.setCombinedStatus(status);
			    finalTs.setRejectedReason(rejectReason);
		}
		return finalTs;

	}
	
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public HashMap<String,Date> getMinAndMaxTimesheetSubmissionDatesForEmployee(Long  userId,Long locationsId) throws Exception {
		
		//(ts.projectMembers.projectEndDate IS NULL OR  (ts.projectMembers.projectEndDate > ts.startDate))
		
		  Session session = this.sessionFactory.getCurrentSession();
		  String query = "select  distinct min(ts.startDate),max(ts.endDate) from TimesheetSubmissions ts  where ts.status = '1' and  ts.projectMembers.projects.orgLocationsId.id = :locationsId and  ts.projectMembers.employee.id IN(select emp.id from Employees emp where emp.user.id = :userId)";
		  List results = session.createQuery(query).setLong("userId", userId).setLong("locationsId", locationsId).list();
													
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
		
		String query = "from TimesheetSubmissions ts  left join fetch ts.projectMembers pm  left join fetch pm.projects pr  left join fetch pm.employee emp left join fetch emp.user where pm.id = :memberId and (ts.startDate > :terminatedDate OR :terminatedDate BETWEEN ts.startDate and ts.endDate) ";  
		List<TimesheetSubmissions> timesheetSubmissionsList = session.createQuery(query).setLong("memberId", memberId).setParameter("terminatedDate", sqlTerminatedDate).list();
		return timesheetSubmissionsList;
	}


	
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<TimesheetSubmissions> getTotalDraftAndSubmittedListByuserIdAndOrgLocationIdForEmployeeLogin(Long userId,
			String status, Long orgLocationId) throws Exception {
		

        Session session = this.sessionFactory.getCurrentSession();
        
        String subquery =   " ts.id IN(select sah.submissionId from SubmissionApproverHierarchy sah where sah.submissionStatus = :status) ";
		String query = "from  TimesheetSubmissions   ts  left join fetch ts.projectMembers pm left join fetch pm.projects left join fetch pm.employee emp where "+subquery+" and emp.orgLocations.id = :orgLocationId and  emp.user.id = :userId order by pm.projects.id ASC)" ;
		@SuppressWarnings("unchecked")
		List<TimesheetSubmissions> tslist =session.createQuery(query.toString()).setLong("userId", userId).setLong("orgLocationId",orgLocationId).setString("status", status).list(); 
		
		return tslist;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public HashMap<String, Date> getMinAndMaxTimesheetSubmissionDatesForApprover(Long approverUserId, Long empUserId,Long locationsId)
			throws Exception {
		 
		  /*String sub = ":approverUserId IN (select userRole.user.id from UserRole userRole where userRole.roles = '1')";*/
		  Session session = this.sessionFactory.getCurrentSession();
		  String query = "select  distinct min(ts.startDate),max(ts.endDate) from TimesheetSubmissions ts  where ts.status = '1' and ts.projectMembers.projects.orgLocationsId.id = :locationsId and ts.projectMembers.employee.id IN(select emp.id from Employees emp where emp.user.id = :empUserId)  and (( ts.projectMembers.id IN ( select pm.id from ProjectMembers pm where pm.projects.id  IN ( select pah.projectId from ProjectApproverHierarchy pah where pah.approverUserId = :approverUserId))) OR "+Constants.loginUserAsAdmin+") ";
		  List results = session.createQuery(query).setLong("empUserId", empUserId).setLong("approverUserId", approverUserId).setLong("userId", approverUserId).setLong("locationsId", locationsId).list();
													
		  HashMap<String,Date> dateList =  new HashMap<String,Date>();
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
	public Date getLeftNavStartDateForEmployeeBasedOnCurrentStartDate(Long userId, Date currentStartDate) {
		java.sql.Date sqlCurrentStartDate = null;
		if(currentStartDate != null){
			sqlCurrentStartDate = new java.sql.Date(currentStartDate.getTime());
		}  
		Session session = this.sessionFactory.getCurrentSession();
	    String query = "select  distinct max(ts.startDate) from TimesheetSubmissions ts   where ts.startDate < :currentStartDate and  ts.status = '1' and ts.projectMembers.employee.id IN(select emp.id from Employees emp where emp.user.id = :userId)";
		List results = session.createQuery(query).setLong("userId", userId).setParameter("currentStartDate", sqlCurrentStartDate).list();
													
		  Date leftNavStartDate=  null;
		  if(results != null && !results.isEmpty()){
			  for(Object result :results){
				  java.sql.Timestamp startDateTimeStamp  = (Timestamp) result;
				  if(startDateTimeStamp != null){
					  leftNavStartDate = new Date(startDateTimeStamp.getTime());
				  }
			  }
		    }
		  return leftNavStartDate;
    }

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Date getRightNavStartDateForEmployeeBasedOnCurrentStartDate(Long userId, Date currentStartDate) {
		  Session session = this.sessionFactory.getCurrentSession();
		  java.sql.Date sqlCurrentStartDate = null;
		  if(currentStartDate != null){
			 sqlCurrentStartDate = new java.sql.Date(currentStartDate.getTime());
		  } 
		  String query = "select  distinct min(ts.startDate) from TimesheetSubmissions  ts where ts.startDate > :currentStartDate and ts.status = '1' and ts.projectMembers.employee.id IN(select emp.id from Employees emp where emp.user.id = :userId)";
		  List results = session.createQuery(query).setLong("userId", userId).setParameter("currentStartDate", sqlCurrentStartDate).list();
													
		  Date rightNavStartDate=  null;
		  if(results != null && !results.isEmpty()){
			  for(Object result :results){
				  java.sql.Timestamp startDateTimeStamp = (java.sql.Timestamp)result;
				  if(startDateTimeStamp != null){
					  rightNavStartDate = new Date(startDateTimeStamp.getTime());
				  }
			  }
		    }
		  return rightNavStartDate;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Date getLeftNavStartDateForApproverBasedOnCurrentStartDate(Long approverUserId, Long empUserId,
			Date currentStartDate) {
		  /*String sub = ":approverUserId IN (select userRole.user.id from UserRole userRole where userRole.roles = '1')";*/
		  Session session = this.sessionFactory.getCurrentSession();
		  java.sql.Date sqlCurrentStartDate = null;
		  if(currentStartDate != null){
			 sqlCurrentStartDate = new java.sql.Date(currentStartDate.getTime());
		  } 
		  String query = "select  distinct max(ts.startDate) from TimesheetSubmissions ts  where   ts.startDate < :currentStartDate and ts.status = '1' and ts.projectMembers.employee.id IN(select emp.id from Employees emp where emp.user.id = :empUserId)  and ( (ts.projectMembers.id IN ( select pm.id from ProjectMembers pm where pm.projects.id  IN ( select pah.projectId from ProjectApproverHierarchy pah where pah.approverUserId = :approverUserId)) ) OR "+Constants.loginUserAsAdmin+") ";
		  List results = session.createQuery(query).setLong("empUserId", empUserId).setLong("approverUserId", approverUserId).setLong("userId", approverUserId).setParameter("currentStartDate", sqlCurrentStartDate).list();
													
		  Date leftNavStartDate=  null;
		  if(results != null && !results.isEmpty()){
			  for(Object result :results){
				  java.sql.Timestamp startDateTimeStamp = (java.sql.Timestamp)result;
				  if(startDateTimeStamp != null){
					  leftNavStartDate = new Date(startDateTimeStamp.getTime());
				  }
			  }
		    }
		  return leftNavStartDate;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Date getRightNavStartDateForApproverBasedOnCurrentStartDate(Long approverUserId, Long empUserId,
			Date currentStartDate) {
		  
		  /*String sub = ":approverUserId IN (select userRole.user.id from UserRole userRole where userRole.roles = '1')";*/
		  Session session = this.sessionFactory.getCurrentSession();
		  java.sql.Date sqlCurrentStartDate = null;
		  if(currentStartDate != null){
			 sqlCurrentStartDate = new java.sql.Date(currentStartDate.getTime());
		  } 
		  String query = "select  distinct min(ts.startDate) from TimesheetSubmissions ts  where  ts.startDate > :currentStartDate and ts.status = '1' and ts.projectMembers.employee.id IN(select emp.id from Employees emp where emp.user.id = :empUserId)  and ( ( ts.projectMembers.id IN ( select pm.id from ProjectMembers pm where pm.projects.id  IN ( select pah.projectId from ProjectApproverHierarchy pah where pah.approverUserId = :approverUserId))) OR "+Constants.loginUserAsAdmin+")";
		  List results = session.createQuery(query).setLong("empUserId", empUserId).setLong("approverUserId", approverUserId).setLong("userId", approverUserId).setParameter("currentStartDate", sqlCurrentStartDate).list();
													
		  Date rightNavStartDate=  null;
		  if(results != null && !results.isEmpty()){
			  for(Object result :results){
				  java.sql.Timestamp startDateTimeStamp = (java.sql.Timestamp)result;
				  if(startDateTimeStamp != null){
					  rightNavStartDate = new Date(startDateTimeStamp.getTime());
				  }
			  }
		    }
		  return rightNavStartDate;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public HashSet<TimesheetSubmissions> getWeekFrequency(Long projectId,Long locationsId,Long userId) throws Exception {
		OrgLocations orgLocations = orgLocationsDao.getOrgLocations(locationsId);
		Date currentWeekStartDate = CalendarUtil.getCurrentWeekDay("MON");
		java.sql.Date  currentWeekStartDayDate = new java.sql.Date(currentWeekStartDate.getTime());
		Calendar cal = Calendar.getInstance(Constants.TIMEZONE_CENTRAL);
		cal.setLenient(false);
		cal.setTime(new Date());
		
		Calendar startMonday = CalendarUtil.getCurrentWeekDayByCalendar(cal, "MON",orgLocations.getDateFormat());
		
		Calendar previousMonday = CalendarUtil.getDateIncrementByincrement(startMonday,-42);
		Date previousWeekStartDate = previousMonday.getTime();
		java.sql.Date  previousWeekStartDayDate = new java.sql.Date(previousWeekStartDate.getTime());
		
		
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from TimesheetSubmissions tss left join fetch tss.projectMembers tm left join fetch tm.projects projects left join fetch projects.orgLocationsId locations left join fetch tm.employee emp left join fetch emp.user usr where  usr.id =:userId and tss.projectMembers.id = :projectId and locations.id = :locationsId and tss.startDate >= :frmDate and tss.endDate < :endsDate order by tss.startDate desc";
		//String query = "from TimesheetSubmissions tss left join fetch tss.projectMembers tm left join fetch tm.employee emp left join fetch emp.user usr where  usr.id = :userID and  tss.startDate BETWEEN :frmDate AND :endDate";
		List<TimesheetSubmissions> timeSheetSubmissions  = session.createQuery(query).setLong("userId",userId).setLong("projectId",projectId ).setDate("frmDate", previousWeekStartDayDate).setDate("endsDate", currentWeekStartDayDate).setLong("locationsId", locationsId).list();
		HashSet<TimesheetSubmissions> tsSet = null;
		if(timeSheetSubmissions != null && !timeSheetSubmissions.isEmpty()){
			tsSet = new HashSet<>(timeSheetSubmissions);
		}
		return tsSet;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public HashSet<TimesheetSubmissions> getWeekFrequencyByProjMemId(Long projectId) throws Exception {
		Date currentWeekStartDate = CalendarUtil.getCurrentWeekDay("MON");
		java.sql.Date  currentWeekStartDayDate = new java.sql.Date(currentWeekStartDate.getTime());
		Projects project = projectsDao.getProjectById(projectId);
		Calendar cal = Calendar.getInstance(Constants.TIMEZONE_CENTRAL);
		cal.setLenient(false);
		cal.setTime(new Date());
		
		Calendar startMonday = CalendarUtil.getCurrentWeekDayByCalendar(cal, "MON",project.getOrgLocationsId().getDateFormat());
		
		Calendar previousMonday = CalendarUtil.getDateIncrementByincrement(startMonday,-42);
		Date previousWeekStartDate = previousMonday.getTime();
		java.sql.Date  previousWeekStartDayDate = new java.sql.Date(previousWeekStartDate.getTime());
		
		
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from TimesheetSubmissions tss left join fetch tss.projectMembers tm left join fetch tm.projects projects left join fetch projects.orgLocationsId locations left join fetch tm.employee emp left join fetch emp.user usr where tss.projectMembers.id = :projectId and tss.startDate >= :frmDate and tss.endDate < :endsDate order by tss.startDate desc";
		//String query = "from TimesheetSubmissions tss left join fetch tss.projectMembers tm left join fetch tm.employee emp left join fetch emp.user usr where  usr.id = :userID and  tss.startDate BETWEEN :frmDate AND :endDate";
		List<TimesheetSubmissions> timeSheetSubmissions  = session.createQuery(query).setLong("projectId",projectId ).setDate("frmDate", previousWeekStartDayDate).setDate("endsDate", currentWeekStartDayDate).list();
		HashSet<TimesheetSubmissions> tsSet = null;
		if(timeSheetSubmissions != null && !timeSheetSubmissions.isEmpty()){
			tsSet = new HashSet<>(timeSheetSubmissions);
		}
		return tsSet;
	}

	@Override
	@Transactional
	public List<TimesheetSubmissions> getTimeSheetSubmissionByProjectMemberId(Long pojectMemberId) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();  
		String query = "from TimesheetSubmissions ts  left join fetch ts.projectMembers pm  where pm.id = :pojectMemberId and ts.status = '1' ";   
		List<TimesheetSubmissions> timesheetSubmissionsList = session.createQuery(query).setLong("pojectMemberId", pojectMemberId.intValue()).list();
		
		return timesheetSubmissionsList;
	}
	
	@Override
	@Transactional
	public List<TimesheetSubmissions> getTimesheetSubmissionsByProjectIdAndMasterCandidateId(Long projectId,Long masterCandidateId) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();  
		String query = "from TimesheetSubmissions ts  left join fetch ts.projectMembers pm  left join fetch ts.projects pr left join fetch ts.masterCandidateId mc  left join fetch pm.employee emp left join fetch emp.user where pr.id = :projectId and mc.id = :masterCandidateId ";  
		List<TimesheetSubmissions> timesheetSubmissionsList = session.createQuery(query).setLong("masterCandidateId", masterCandidateId).setLong("projectId", projectId).list();
		
		return timesheetSubmissionsList;
	}

	@Override
	@Transactional
	public TimesheetSubmissions getSubmissionDetailsByProjectIdMasterCandiDateIdStartDateandEndDate(Long projectId,Long masterCanidateId, Date frequencyStartDate, Date frequencyEndDate) {
		Session session = this.sessionFactory.getCurrentSession(); 
		java.sql.Date sDate = new java.sql.Date(frequencyStartDate.getTime());
		java.sql.Date eDate = new java.sql.Date(frequencyEndDate.getTime());
		String query = "from TimesheetSubmissions ts left join fetch ts.projectMembers proMembers left join fetch ts.projects project left join fetch ts.masterCandidateId masterCandidate where project.id = :projectId and masterCandidate.id = :masterCanidateId and ts.startDate= :startDate and ts.endDate= :endDate";
		List<TimesheetSubmissions> timesheetSubmissionsList = session.createQuery(query).setLong("masterCanidateId", masterCanidateId).setLong("projectId", projectId).setDate("startDate",sDate).setDate("endDate",eDate).list();
		if(timesheetSubmissionsList!= null && !timesheetSubmissionsList.isEmpty()) {
			return timesheetSubmissionsList.get(0);
		}else {
			return null;
		}
	}


}
