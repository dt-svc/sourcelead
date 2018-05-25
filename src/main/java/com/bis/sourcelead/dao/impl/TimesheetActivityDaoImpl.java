package com.bis.sourcelead.dao.impl;

import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.TimesheetActivityDao;
import com.bis.sourcelead.entity.TimesheetActivities;

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

}
