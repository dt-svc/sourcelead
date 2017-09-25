package com.bis.sourcelead.dao.impl;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.LeaveDao;
import com.bis.sourcelead.entity.Leave;

@Repository
public class LeaveDaoImpl implements LeaveDao{

	private static final Logger logger = LoggerFactory.getLogger(LeaveDaoImpl.class);
	
	@Autowired
	private SessionFactory sessionFactory;

	
	@Override
	@Transactional
	public Leave saveLeave(Leave leave) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (leave != null) {
				session.saveOrUpdate(leave);
			}
		} catch (Exception exception) {
			logger.error(ExceptionUtils.getFullStackTrace(exception));
		}
		return leave;
	}

}
