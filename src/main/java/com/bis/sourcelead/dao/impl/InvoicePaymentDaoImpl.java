package com.bis.sourcelead.dao.impl;

import java.util.List;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.InvoicePaymentDao;
import com.bis.sourcelead.entity.Department;
import com.bis.sourcelead.entity.Invoice;
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


	@Override
	@Transactional(rollbackFor = { Exception.class })	
	public List<InvoicePayment> getInvoicePaymentList(Long invoiceId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from InvoicePayment invoicePayment  where invoicePayment.invoiceId = :invoiceId and invoicePayment.status = '1' ";
		List<InvoicePayment> invoicePaymentList = session.createQuery(query.toString()).setLong("invoiceId", invoiceId).setCacheable(true).list();
		return invoicePaymentList;
		
	}


	@Override
	@Transactional(rollbackFor = { Exception.class })	
	public InvoicePayment invoicePaymentById(Long id) throws Exception {
		logger.debug("In InvoicePaymentDaoImpl invoicePaymentByInvoiceId method");
		Session session = this.sessionFactory.getCurrentSession();
		return (InvoicePayment) session.get(InvoicePayment.class, id);
	}
}
