package com.bis.sourcelead.payroll.dao.impl;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.payroll.dao.USPaysheetDao;
import com.bis.sourcelead.payroll.entity.PaysheetFrequency;

@Repository
public class USPaysheetDaoImpl implements USPaysheetDao{

	private static final Logger logger = LoggerFactory.getLogger(USPaysheetDaoImpl.class);
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	@Transactional
	public PaysheetFrequency addPaysheetFrequency(PaysheetFrequency paysheetFrequency)
	{
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (paysheetFrequency != null) {
				session.saveOrUpdate(paysheetFrequency);
			}
		} catch (Exception e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}
		return paysheetFrequency;
	}
}
