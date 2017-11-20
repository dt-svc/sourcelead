package com.bis.sourcelead.dao.impl;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.LeaveManagementHistoryDao;
import com.bis.sourcelead.entity.LeaveManagementHistory;

@Repository
public class LeaveManagementHistoryDaoImpl implements LeaveManagementHistoryDao{

private static final Logger logger = LoggerFactory.getLogger(LeaveManagementHistoryDaoImpl.class);
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	@Transactional
	public LeaveManagementHistory saveLeaveManagementHistory(LeaveManagementHistory leaveManagementHistory)
			throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (leaveManagementHistory != null) {
				session.saveOrUpdate(leaveManagementHistory);
			}
		} catch (Exception exception) {
			logger.error(ExceptionUtils.getFullStackTrace(exception));
		}
		return leaveManagementHistory;
	}

}
