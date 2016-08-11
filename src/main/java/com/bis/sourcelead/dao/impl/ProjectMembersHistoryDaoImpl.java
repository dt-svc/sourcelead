package com.bis.sourcelead.dao.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.ProjectMembersHistoryDao;
import com.bis.sourcelead.entity.ProjectMembersHistory;
import com.bis.sourcelead.util.CalendarUtil;
import com.bis.sourcelead.util.Constants;

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
		if(projectMembersHistoryList != null && projectMembersHistoryList.isEmpty()){
			for(ProjectMembersHistory pmh :projectMembersHistoryList){
	                 Calendar startCal = Calendar.getInstance(Constants.TIMEZONE_CENTRAL);
					 startCal.setLenient(false);
					 startCal.setTime(pmh.getProjectAssignedDate());
					 dateList.add(CalendarUtil.getCurrentWeekDayByCalendarForTimesheets(startCal,"MON").getTime());
					 dateList.add(CalendarUtil.getCurrentWeekDayByCalendarForTimesheets(startCal,"TUE").getTime());
					 dateList.add(CalendarUtil.getCurrentWeekDayByCalendarForTimesheets(startCal,"WED").getTime());
					 dateList.add(CalendarUtil.getCurrentWeekDayByCalendarForTimesheets(startCal,"THU").getTime());
					 dateList.add(CalendarUtil.getCurrentWeekDayByCalendarForTimesheets(startCal,"FRI").getTime());
					 dateList.add(CalendarUtil.getCurrentWeekDayByCalendarForTimesheets(startCal,"SAT").getTime());
					 dateList.add(CalendarUtil.getCurrentWeekDayByCalendarForTimesheets(startCal,"SUN").getTime());
					 
			}
		}
		// TODO Auto-generated method stub
		return dateList;
	}

}
