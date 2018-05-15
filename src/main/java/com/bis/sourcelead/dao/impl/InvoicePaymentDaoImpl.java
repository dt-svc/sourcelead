package com.bis.sourcelead.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.joda.time.LocalDate;
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
		List<InvoicePayment> invoicePaymentList = session.createQuery(query.toString()).setLong("invoiceId", invoiceId).list();
		return invoicePaymentList;
		
	}


	@Override
	@Transactional(rollbackFor = { Exception.class })	
	public InvoicePayment invoicePaymentById(Long id) throws Exception {
		logger.debug("In InvoicePaymentDaoImpl invoicePaymentByInvoiceId method");
		Session session = this.sessionFactory.getCurrentSession();
		return (InvoicePayment) session.get(InvoicePayment.class, id);
	}


	@SuppressWarnings("unchecked")
	@Override
	@Transactional(rollbackFor = { Exception.class })	
	public List<InvoicePayment> getInvoicePaymentListByOrgloactionIdAndDates(Long orgLocationId, Date startDate, Date endDate) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		java.sql.Date sqlStartDate = new java.sql.Date(startDate.getTime());
		java.sql.Date sqlEndDate = new java.sql.Date(endDate.getTime());
		
		String query = "from InvoicePayment invoicePayment  left join fetch invoicePayment.invoiceId invoice  where (invoice.orgLocation.id  = :orgLocationId or invoice.responderLocationId.id  = :orgLocationId) and invoicePayment.paymentDate >= :startDate and invoicePayment.paymentDate <= :endDate ";
		List<InvoicePayment> invoicePaymentList = session.createQuery(query.toString()).setLong("orgLocationId", orgLocationId).setDate("startDate", sqlStartDate).setDate("endDate", sqlEndDate).list();
		return invoicePaymentList;
		
		
		/*Map<String, Object> params = new HashMap<String,Object>();
		if(null != orgLocationId){
			params.put("orgLocationId", orgLocationId);
		}
		if(null != startDate && null != endDate){
			params.put("startDate", startDate);
			params.put("startDate", startDate);
			subQuery = "and invoice.invoiceDate >= :startDateUTC and invoice.invoiceDate <= :endDateUTC";
		}
		String query = "from Invoice invoice  where invoice.orgLocation.id =:orgLocationId and invoice.invoiceStatus <> 'Linked' and invoice.invoiceStatus <> 'Rejected' "+subQuery;
		
		Query qry = session.createQuery(query);
		for (String str : qry.getNamedParameters())
        {
			if("orgLocationId".equalsIgnoreCase(str)){
				qry.setLong(str, (long) params.get("orgLocationId"));
			}
			if("startDateUTC".equalsIgnoreCase(str)){
				qry.setParameter(str, (Date) params.get("startDateUTC"));
			}
			if("endDateUTC".equalsIgnoreCase(str)){
				qry.setParameter(str, (Date) params.get("endDateUTC"));
			}
        }
		return qry.list();*/
		 
	}
}
