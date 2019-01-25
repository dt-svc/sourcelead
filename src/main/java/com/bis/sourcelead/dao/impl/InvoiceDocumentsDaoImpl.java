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

import com.bis.sourcelead.dao.InvoiceDocumentsDao;
import com.bis.sourcelead.entity.InvoiceDocument;
import com.bis.sourcelead.entity.OnBoardingTemplateDocuments;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;

@Repository
public class InvoiceDocumentsDaoImpl implements InvoiceDocumentsDao {
	
	private static final Logger logger = LoggerFactory.getLogger(InvoiceDocumentsDaoImpl.class);
	
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
	public List<InvoiceDocument> getInvoiceDocumentsListByInvoiceId(Long invoiceId) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from InvoiceDocument documents  where documents.invoice.id = :invoiceId and documents.status = '1' ";
		List<InvoiceDocument> invoiceDocumentsList = session.createQuery(query.toString()).setLong("invoiceId", invoiceId).list();
		return invoiceDocumentsList;
	}

	@Override
	@Transactional
	public InvoiceDocument getInvoiceDocumentById(Long documentId) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		List<InvoiceDocument> documents = session.createQuery("from InvoiceDocument documents where documents.id =:id" ).setLong( "id", documentId ).list();
		if (documents.size() > 0) {
			return documents.get(0);
		} else {
			return null;
		}
	}

	@Override
	@Transactional
	public InvoiceDocument saveInvoiceDocument(InvoiceDocument invoiceDocument) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (invoiceDocument != null) {
				session.saveOrUpdate(invoiceDocument);
			}
			logger.debug("InvoiceDocument Added Successfully");
		} catch (Exception exception) {
			 StringBuilder message= new StringBuilder(MessageFormat.format(duplicateValue,"INVOICE_DOCUMENTS"));
				throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		return invoiceDocument;
	}
	
	

}
