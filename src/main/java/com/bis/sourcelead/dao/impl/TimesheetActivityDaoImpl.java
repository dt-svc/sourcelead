package com.bis.sourcelead.dao.impl;

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
	
	/*@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<TimesheetActivities> getTimesheetSubmissionApproverHirerchayHistoryBySubmissionId(Long submissionId) {
		 Session session = this.sessionFactory.getCurrentSession();
		 String query = "from SubmissionApproverHierarchyHistory submissionHistory where submissionHistory.submissionId =:submissionId";
		 List<TimesheetActivities> submissionList = session.createQuery(query).setLong("submissionId",submissionId).list();
		 return submissionList;
	}*/

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
