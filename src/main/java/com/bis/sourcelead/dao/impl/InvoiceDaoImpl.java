package com.bis.sourcelead.dao.impl;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.InvoiceDao;
import com.bis.sourcelead.entity.Contracts;
import com.bis.sourcelead.entity.Invoice;

@Repository
public class InvoiceDaoImpl implements InvoiceDao {
	
	private static final Logger logger = LoggerFactory.getLogger(InvoiceDaoImpl.class);
	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	@Transactional
	public Invoice saveInvoce(Invoice invoice) throws Exception
	{
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (invoice != null) {
				session.saveOrUpdate(invoice);
			}
		} catch (Exception exception) {
			logger.error(ExceptionUtils.getFullStackTrace(exception));
		}
		return invoice;
	}


	@Override
	@Transactional(rollbackFor = { Exception.class })	
	public List<Invoice> invoiceListByOrgLocationId(Long orgLocationId,String invoiceStatus, String paymentStatus,Date startDateUTC,Date endDateUTC,String invoiceType) {
		Session session = this.sessionFactory.getCurrentSession();
		
		String[] invoiceTokens = invoiceStatus.split(",");
		String[] paymentTokens = paymentStatus.split(",");
		
		String subQuery = "";
		String subQuery1 = "";
		if (Arrays.asList(invoiceTokens).contains("DRAFT")) {
			subQuery = subQuery + "invoice.invoiceStatus = 'DRAFT' or ";
		}
		if (Arrays.asList(invoiceTokens).contains("SENT")) {
			subQuery = subQuery + "invoice.invoiceStatus = 'SENT' or ";
		}
		
		if ((invoiceTokens[0] != null && invoiceTokens[0].length() > 0)) {
			subQuery = "and (" + subQuery.substring(0, subQuery.length() - 4) + ")";
		} 
		
		if (Arrays.asList(paymentTokens).contains("Unpaid")) {
			subQuery1 = subQuery1 + "invoice.paymentStatus = 'Unpaid' or ";
		}
		if (Arrays.asList(paymentTokens).contains("Partial Paid")) {
			subQuery1 = subQuery1 + "invoice.paymentStatus = 'Partial Paid' or ";
		}
		if (Arrays.asList(paymentTokens).contains("Paid")) {
			subQuery1 = subQuery1 + "invoice.paymentStatus = 'Paid' or ";
		}
		if (Arrays.asList(paymentTokens).contains("OverDue")) {
			subQuery1 = subQuery1 + "invoice.paymentStatus = 'OverDue' or ";
		}
		
		if ((paymentTokens[0] != null && paymentTokens[0].length() > 0)) {
			subQuery1 = "and (" + subQuery1.substring(0, subQuery1.length() - 4) + ")";
		} 
		
		String subQuery2 = "";
		
		if(invoiceType.equalsIgnoreCase("payable")){
			subQuery2 = " and invoice.invoiceType = 'Payables' ";
		}else{
			subQuery2 = " and invoice.invoiceType = 'Receivable' ";
		}
		
		String query = "from Invoice invoice  where  invoice.orgLocation.id =:orgLocationId and invoice.status = '1' "+subQuery+" "+subQuery1+" "+subQuery2+" and invoice.createdDate >= :startDateUTC and invoice.createdDate <= :endDateUTC"; 
		List<Invoice> invoiceList = session.createQuery(query).setLong("orgLocationId", orgLocationId).setParameter("startDateUTC", startDateUTC).setParameter("endDateUTC", endDateUTC).setCacheable(true).list();
		return invoiceList;
	}
	
	

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Contracts> getSowcontractListByAssociateId(Long candidateId, Long orgLocationId, Long orgRelationId) throws Exception
	{
		Session session = this.sessionFactory.getCurrentSession();
		List<Contracts> contractsList = null;
		String query = "from Contracts contract left join fetch contract.requirementSubmission submission where contract.orgLocationId.id = :orgLocationId and contract.orgRelations.id = :orgRelationId and submission.candidate.id = :candidateId";
	     contractsList = session.createQuery(query.toString()).setLong("candidateId", candidateId).setLong("orgLocationId", orgLocationId).setLong("orgRelationId", orgRelationId).setCacheable(true).list();
	  if(null != contractsList){
		  return contractsList;
	  }
	  return contractsList;
		 
	}


	@Override
	@Transactional(rollbackFor = { Exception.class })	
	public Invoice getInvoicetById(Long invoiceId) throws Exception {
		logger.debug("In InvoiceDaoImpl getInvoicetById method");
		Session session = this.sessionFactory.getCurrentSession();
		return (Invoice) session.get(Invoice.class, invoiceId);
	}

}
