package com.bis.sourcelead.payroll.dao.impl;

import java.util.List;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bis.sourcelead.payroll.dao.InvoiceDataDao;
import com.bis.sourcelead.payroll.entity.InvoiceData;

@Repository
public class InvoiceDataDaoImpl implements InvoiceDataDao{

	private static final Logger logger = LoggerFactory.getLogger(InvoiceDataDaoImpl.class);

	@Autowired
	SessionFactory sessionFactory;
	@Override
	public InvoiceData saveInvoiceData(InvoiceData invoiceData) {
		
		Session session = this.sessionFactory.getCurrentSession();
		try {
			session.saveOrUpdate(invoiceData);
		} catch (Exception exception) {
			logger.error(ExceptionUtils.getFullStackTrace(exception));
		}
		return invoiceData;
	}

}
