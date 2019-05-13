package com.bis.sourcelead.timesheet.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.entity.TimesheetActivities;
import com.bis.sourcelead.timesheet.dao.TimesheetActivityDao;

@Repository
public class TimesheetActivityDaoImpl  implements TimesheetActivityDao {

	@Autowired
	private SessionFactory sessionFactory;
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<TimesheetActivities> getTimesheetActivityByProjectIdMasterCandidateIdFrequencyStartAndEndDate(Long projectId, Long masterCandidateId,Date frequencyStartDate, Date frequencyEndDate) {
		 Session session = this.sessionFactory.getCurrentSession();
		 String query = "from TimesheetActivities activity where activity.projectId.id =:projectId and activity.masterCandidate.id =:masterCandidateId and activity.frequencyStartDate = :frequencyStartDate and activity.frequencyEndDate =:frequencyEndDate ORDER BY activity.id DESC";
		 List<TimesheetActivities> submissionList = session.createQuery(query).setLong("projectId",projectId).setLong("masterCandidateId", masterCandidateId).setDate("frequencyStartDate", frequencyStartDate).setDate("frequencyEndDate",frequencyEndDate).list();
		 return submissionList;
	}	

	@Override
	@Transactional
	public TimesheetActivities saveTimesheetActivity(TimesheetActivities ta) {
		Session session = this.sessionFactory.getCurrentSession();
		try {
			session.saveOrUpdate(ta);
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return ta;
	}

	@Override
	@Transactional
	public Map<String,Date> getTimesheetActivityByProjectIdMasterCandidateIdFrequencyStartAndEndDateAndStatus(Long projectId, Long masterCandidateId, Date frequencyStartDate, Date frequencyEndDate, String status) {
		 Session session = this.sessionFactory.getCurrentSession();
		 Map<String,Date> dateMap = new HashMap<>();
		 if(status.equalsIgnoreCase("SUBMITTEDFORAPPROVAL")) {
			 String query = " SELECT TA.CREATED_DATE FROM TIMESHEET_ACTIVITIES TA WHERE TA.ID IN(SELECT MIN(AV.ID)  FROM TIMESHEET_ACTIVITIES AV WHERE AV.PROJECT_ID = :projectId AND AV.MASTER_CANDIDATE_ID = :masterCandidateId AND AV.FREQUENCY_START_DATE = :frequencyStartDate AND AV.FREQUENCY_END_DATE = :frequencyEndDate AND AV.ACTIVITY_STATUS = 'SUBMITTED') ";
			 List<Object> submittedOn = session.createSQLQuery(query).setLong("projectId",projectId).setLong("masterCandidateId", masterCandidateId).setDate("frequencyStartDate", frequencyStartDate).setDate("frequencyEndDate",frequencyEndDate).list();
		     Date d1 = null;
		     if(submittedOn != null && !submittedOn.isEmpty()) {
				 d1= (Date) submittedOn.get(0);
			 }
			 dateMap.put("SUBMITTED",d1);
			 dateMap.put("APPROVED",null);
		 }else{
			 String query = " SELECT TA.CREATED_DATE FROM TIMESHEET_ACTIVITIES TA WHERE TA.ID IN(SELECT MIN(AV.ID)  FROM TIMESHEET_ACTIVITIES AV WHERE AV.PROJECT_ID = :projectId AND AV.MASTER_CANDIDATE_ID = :masterCandidateId AND AV.FREQUENCY_START_DATE = :frequencyStartDate AND AV.FREQUENCY_END_DATE = :frequencyEndDate AND AV.ACTIVITY_STATUS = 'SUBMITTED')";
			 List<Object> submittedOn = session.createSQLQuery(query).setLong("projectId",projectId).setLong("masterCandidateId", masterCandidateId).setDate("frequencyStartDate", frequencyStartDate).setDate("frequencyEndDate",frequencyEndDate).list();
			 Date d1 = null;
			 if(submittedOn != null && !submittedOn.isEmpty()) {
				 d1= (Date) submittedOn.get(0);
			 }
			 dateMap.put("SUBMITTED",d1);
			 
			  query = " SELECT TA.CREATED_DATE FROM TIMESHEET_ACTIVITIES TA WHERE TA.ID IN(SELECT MAX(AV.ID)  FROM TIMESHEET_ACTIVITIES AV WHERE AV.PROJECT_ID = :projectId AND AV.MASTER_CANDIDATE_ID = :masterCandidateId AND AV.FREQUENCY_START_DATE = :frequencyStartDate AND AV.FREQUENCY_END_DATE = :frequencyEndDate AND AV.ACTIVITY_STATUS = 'SUBMITTED')";
			  List<Object> approvedOn = session.createSQLQuery(query).setLong("projectId",projectId).setLong("masterCandidateId", masterCandidateId).setDate("frequencyStartDate", frequencyStartDate).setDate("frequencyEndDate",frequencyEndDate).list();
			  Date d2 = null;
			  if(approvedOn != null && !approvedOn.isEmpty()) {
					 d2= (Date) approvedOn.get(0);
			   }
			  dateMap.put("APPROVED",d2);
		 }
		 return dateMap;
	}

	@Override
	@Transactional
	public TimesheetActivities getTimesheetActivityById(Long timesheetActivitiyId) {
		Session session = this.sessionFactory.getCurrentSession();
		TimesheetActivities timesheetActivities = null;
		try {
			timesheetActivities = (TimesheetActivities) session.get(TimesheetActivities.class, timesheetActivitiyId);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return timesheetActivities;
	}

}
