package com.bis.sourcelead.dao.impl;

import java.text.MessageFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
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
import com.bis.sourcelead.dao.ProjectMembersHistoryDao;
import com.bis.sourcelead.entity.ProjectMembersHistory;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;

@Repository
public class ProjectMembersHistoryDaoImpl implements ProjectMembersHistoryDao {

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
	 * This method for get project member by id
	 */
	@Override
	@Transactional(rollbackFor = { Exception.class })	
	public ProjectMembersHistory getProjectMembersOpenHistoryByProjectMemberId(Long projectMemberId) throws Exception {
		// TODO Auto-generated method stub
		logger.debug("In ProjectMembersHistory getProjectMembersOpenHistoryByProjectMemberId method");
        Session session = this.sessionFactory.getCurrentSession();
		Query projectMembersHistoryListQuery= session.createQuery("from ProjectMembersHistory pmh left join fetch pmh.projectMemberId where pmh.projectMemberId.id = :projectMemberId and pmh.status='OPEN'");
		List<ProjectMembersHistory> projectMembersHistoryList= projectMembersHistoryListQuery.setLong("projectMemberId", projectMemberId).setCacheable(true).list();
		ProjectMembersHistory pmh = null;
		if(projectMembersHistoryList != null && !projectMembersHistoryList.isEmpty()){
			pmh = projectMembersHistoryList.iterator().next();
		}
		
		return pmh;
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public ProjectMembersHistory updateProjectMembersHistory(ProjectMembersHistory projectMembersHistory) throws Exception {
		logger.debug("In ProjectMembersHistory Update method");
		Session session = this.sessionFactory.getCurrentSession();
		try{
			if(projectMembersHistory != null){
				session.saveOrUpdate(projectMembersHistory);
				logger.info("Record updated successfully..!");
			}
		}catch(ConstraintViolationException exception)
		{
			StringBuilder message= new StringBuilder(MessageFormat.format(duplicateValue,"PROJECT_MEMBERS_HISTORY"));
			throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		
		return projectMembersHistory;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })	
	public HashSet<Date> getAllProjectMemberHistoryDatesByProjectMemberIdAndWeekStartDateEndDate(Long projectMemberId,
			Date startDate, Date endDate) {
		
		Session session = this.sessionFactory.getCurrentSession();
		java.sql.Date sqlStartDate = null;
		if(startDate != null){
			sqlStartDate = new java.sql.Date(startDate.getTime());
		}
		
		java.sql.Date sqlEndDate = null;
		if(endDate != null){
			sqlEndDate = new java.sql.Date(endDate.getTime());
		}
		
		Query projectMembersHistoryListQuery= session.createQuery("from ProjectMembersHistory pmh left join fetch pmh.projectMemberId where pmh.projectMemberId.id = :projectMemberId and (( pmh.projectTerminationDate IS NOT NULL and :startDate between pmh.projectAssignedDate and pmh.projectTerminationDate) OR ( pmh.projectTerminationDate IS NOT NULL and  :endDate between pmh.projectAssignedDate and pmh.projectTerminationDate) OR (pmh.projectTerminationDate IS NULL and :endDate >= pmh.projectAssignedDate))");
		List<ProjectMembersHistory> projectMembersHistoryList= projectMembersHistoryListQuery.setLong("projectMemberId", projectMemberId).setParameter("startDate", sqlStartDate).setParameter("endDate", sqlEndDate).setCacheable(true).list();
		HashSet<Date> dateList = new HashSet<Date>();
		if(projectMembersHistoryList != null && !projectMembersHistoryList.isEmpty()){
			for(ProjectMembersHistory pmh :projectMembersHistoryList){
				
				
					 if(pmh.getProjectAssignedDate().after(startDate)){
						startDate = pmh.getProjectAssignedDate();	
					 } 
					 if(pmh.getProjectTerminationDate()!= null && pmh.getProjectTerminationDate().before(endDate)){
						 endDate = pmh.getProjectTerminationDate();
					 }
					 
					 
					   Calendar calendar = Calendar.getInstance(Constants.TIMEZONE_CENTRAL);
					    calendar.setLenient(false);
					    calendar.setTime(startDate);

					    while (calendar.getTime().before(endDate) || calendar.getTime().equals(endDate))
					    {
					        Date result = calendar.getTime();
					        dateList.add(result);
					        calendar.add(Calendar.DATE, 1);
					    }
					 
					 
	                 /*Calendar startCal = Calendar.getInstance(Constants.TIMEZONE_CENTRAL);
					 startCal.setLenient(false);
					 startCal.setTime(pmh.getProjectAssignedDate());
					 Calendar mondayCal = CalendarUtil.getCurrentWeekDayByCalendarForTimesheets(startCal,"MON");
					 try {
						 dateList.add(CalendarUtil.getDateIncrementByincrement(mondayCal, 0).getTime());
						 dateList.add(CalendarUtil.getDateIncrementByincrement(mondayCal, 1).getTime());
						 dateList.add(CalendarUtil.getDateIncrementByincrement(mondayCal, 2).getTime());
						 dateList.add(CalendarUtil.getDateIncrementByincrement(mondayCal, 3).getTime());
						 dateList.add(CalendarUtil.getDateIncrementByincrement(mondayCal, 4).getTime());
						 dateList.add(CalendarUtil.getDateIncrementByincrement(mondayCal, 5).getTime());
						 dateList.add(CalendarUtil.getDateIncrementByincrement(mondayCal, 6).getTime());
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}*/
					
					 
			}
		}
		// TODO Auto-generated method stub
		return dateList;
	}

}
