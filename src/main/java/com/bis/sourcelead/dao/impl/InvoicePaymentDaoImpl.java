package com.bis.sourcelead.dao.impl;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.InvoicePaymentDao;
import com.bis.sourcelead.entity.InvoicePayment;

@Repository
public class InvoicePaymentDaoImpl implements InvoicePaymentDao {
	
	private static final Logger logger = LoggerFactory.getLogger(InvoicePaymentDaoImpl.class);
	@Autowired
	private SessionFactory sessionFactory;
	
	
	@Override
	@Transactional(rollbackFor = { Exception.class })	
	public InvoicePayment saveInvoicePayment(InvoicePayment invoicePayment) throws Exception {

		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (invoicePayment != null) {
				session.saveOrUpdate(invoicePayment);
			}
		} catch (Exception exception) {
			logger.error(ExceptionUtils.getFullStackTrace(exception));
		}
		return invoicePayment;
	}
}
