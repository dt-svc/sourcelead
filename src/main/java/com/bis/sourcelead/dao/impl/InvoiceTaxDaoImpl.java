package com.bis.sourcelead.dao.impl;

import java.text.MessageFormat;
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
import com.bis.sourcelead.entity.InvoiceLineItems;
import com.bis.sourcelead.entity.InvoiceTax;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;

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
		List<InvoiceTax> invoiceTaxList = session.createQuery(query.toString()).setLong("invoiceId", invoiceId).list();
		return invoiceTaxList;
	}


	@Override
	@Transactional
	public List<Long> getInvoiceTaxIdsByInvoiceId(Long invoiceId) throws Exception {
		
		logger.debug("In InvoiceLineItemDaoImpl class getInvoiceLineItemsIdsByInvoiceId method");
		Session session = this.sessionFactory.getCurrentSession();
			 
		String query = "select tax.id from InvoiceTax tax where tax.invoice.id = :invoiceId ";
		List<Long> taxIdsList =  session.createQuery(query.toString()).setLong("invoiceId", invoiceId)
									.list();
		
		return taxIdsList;
	}


	@Override
	@Transactional
	public void deleteInvoiceTax(InvoiceTax invoiceTax) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (invoiceTax != null) {
				session.delete(invoiceTax);
			}
			logger.debug("InvoiceTax Deleted Successfully");
		} catch (Exception exception) {
			 StringBuilder message= new StringBuilder(MessageFormat.format(duplicateValue,"INVOICE_TAX"));
				throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		
	}


	@Override
	@Transactional
	public InvoiceTax getInvoiceTaxById(Long Id) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		List<InvoiceTax> invoiceTax = session.createQuery("from InvoiceTax tax left join fetch tax.invoice where tax.id =:id" ).setLong( "id", Id ).list();
		if (invoiceTax.size() > 0) {
			return invoiceTax.get(0);
		} else {
			return null;
		}
	}
	
}
