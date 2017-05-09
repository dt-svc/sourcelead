package com.bis.sourcelead.dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.InvoiceLineItemDao;
import com.bis.sourcelead.entity.InvoiceLineItems;

@Repository
public class InvoiceLineItemDaoImpl implements InvoiceLineItemDao{
	
	private static final Logger logger = LoggerFactory.getLogger(InvoiceLineItemDaoImpl.class);
	@Autowired
	private SessionFactory sessionFactory;
	
	
	
	@Override
	@Transactional
	public List<InvoiceLineItems> getInvoiceLineItemsByInvoiceId(Long invoiceId) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from InvoiceLineItems lineItems  where lineItems.invoice.id = :invoiceId and invoice.status = '1' ";
		List<InvoiceLineItems> InvoiceLineItemsList = session.createQuery(query.toString()).setLong("invoiceId", invoiceId).setCacheable(true).list();
		return InvoiceLineItemsList;
	}

}
