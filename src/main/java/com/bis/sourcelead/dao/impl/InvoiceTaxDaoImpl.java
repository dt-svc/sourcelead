package com.bis.sourcelead.dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.InvoiceTaxDao;
import com.bis.sourcelead.entity.InvoiceTax;

@Repository
public class InvoiceTaxDaoImpl implements InvoiceTaxDao {

	private static final Logger logger = LoggerFactory.getLogger(InvoiceTaxDaoImpl.class);
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Value("${SourceLead.100}")
	private String notFound;
	@Value("${SourceLead.101}")
	private String noRecords;
	@Value("${SourceLead.102}")
	private String statusUpdate;
	@Value("${SourceLead.103}")
	private String statusDelete;
	@Value("${SourceLead.106}")
	private String duplicateValue;
	
	
	@Override
	@Transactional
	public List<InvoiceTax> getInvoiceTaxListByInvoiceId(Long invoiceId) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from InvoiceTax tax  where tax.invoice.id = :invoiceId and tax.status = '1' ";
		List<InvoiceTax> invoiceTaxList = session.createQuery(query.toString()).setLong("invoiceId", invoiceId).setCacheable(true).list();
		return invoiceTaxList;
	}
	
}
