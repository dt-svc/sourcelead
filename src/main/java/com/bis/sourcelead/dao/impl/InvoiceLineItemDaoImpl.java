package com.bis.sourcelead.dao.impl;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.InvoiceLineItemDao;
import com.bis.sourcelead.entity.InvoiceDocument;
import com.bis.sourcelead.entity.InvoiceLineItems;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;

@Repository
public class InvoiceLineItemDaoImpl implements InvoiceLineItemDao{
	
	private static final Logger logger = LoggerFactory.getLogger(InvoiceLineItemDaoImpl.class);
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
	public List<InvoiceLineItems> getInvoiceLineItemsByInvoiceId(Long invoiceId) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from InvoiceLineItems lineItems left join fetch lineItems.invoice  left join fetch lineItems.contract left join fetch lineItems.candidate where lineItems.invoice.id = :invoiceId and lineItems.status = '1' ";
		List<InvoiceLineItems> InvoiceLineItemsList = session.createQuery(query.toString()).setLong("invoiceId", invoiceId).list();
		return InvoiceLineItemsList;
	}



	@Override
	@Transactional
	public List<InvoiceLineItems> getInvoiceLineItemsByInvoiceIdAndOrglocationId(Long invoiceId, Long orgLocationId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession(); 
		String query = "from InvoiceLineItems invoiceItems left join fetch invoiceItems.contract left join fetch invoiceItems.candidate  where invoiceItems.invoice.id = :invoiceId and invoiceItems.orgLocation.id = :orgLocationId  and invoiceItems.status = '1'";
		List<InvoiceLineItems> invoiceLineItemsList = session.createQuery(query.toString()).setLong("invoiceId", invoiceId).setLong("orgLocationId", orgLocationId).list();
		return invoiceLineItemsList;
	}

	@Override
	@Transactional
	public InvoiceLineItems getInvoiceLineItemsById(Long Id) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		List<InvoiceLineItems> lineItem = session.createQuery("from InvoiceLineItems lineItems left join fetch lineItems.invoice where lineItems.id =:id" ).setLong( "id", Id ).list();
		if (lineItem.size() > 0) {
			return lineItem.get(0);
		} else {
			return null;
		}
	}



	@Override
	@Transactional
	public InvoiceLineItems saveInvoiceLineItems(InvoiceLineItems invoiceLineItems) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (invoiceLineItems != null) {
				session.saveOrUpdate(invoiceLineItems);
			}
			logger.debug("InvoiceLineItems Added Successfully");
		} catch (Exception exception) {
			 StringBuilder message= new StringBuilder(MessageFormat.format(duplicateValue,"INVOICE_LINE_ITEMS"));
				throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		return invoiceLineItems;
	}



	@Override
	@Transactional
	public void deleteLineItem(InvoiceLineItems invoiceLineItems) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (invoiceLineItems != null) {
				session.delete(invoiceLineItems);
			}
			logger.debug("InvoiceLineItems Deleted Successfully");
		} catch (Exception exception) {
			 StringBuilder message= new StringBuilder(MessageFormat.format(duplicateValue,"INVOICE_LINE_ITEMS"));
				throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		
	}



	@Override
	@Transactional
	public List<Long> getInvoiceLineItemsIdsByInvoiceId(Long invoiceId) throws Exception {
		
		logger.debug("In InvoiceLineItemDaoImpl class getInvoiceLineItemsIdsByInvoiceId method");
		Session session = this.sessionFactory.getCurrentSession();
			 
		String query = "select lineItems.id from InvoiceLineItems lineItems where lineItems.invoice.id = :invoiceId ";
		List<Long> lineItemsIdsList =  session.createQuery(query.toString()).setLong("invoiceId", invoiceId)
									.list();
		
		return lineItemsIdsList;
	}

}
