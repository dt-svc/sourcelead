package com.bis.sourcelead.payroll.dao.impl;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.payroll.dao.PayAdjustmentHistoryDao;
import com.bis.sourcelead.payroll.entity.PayAdjustmentHistory;

@Repository
public class PayAdjustmentHistoryDaoImpl implements PayAdjustmentHistoryDao {
	private static final Logger logger = LoggerFactory.getLogger(InvoiceDataDaoImpl.class);

	@Autowired
	SessionFactory sessionFactory;
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public PayAdjustmentHistory savePayAdjustmentHistory(PayAdjustmentHistory payAdjHistory)
	{
		Session session = this.sessionFactory.getCurrentSession();
		try {
			session.saveOrUpdate(payAdjHistory);
		} catch (Exception exception) {
			logger.error(ExceptionUtils.getFullStackTrace(exception));
		}
		return payAdjHistory;
	}
}
