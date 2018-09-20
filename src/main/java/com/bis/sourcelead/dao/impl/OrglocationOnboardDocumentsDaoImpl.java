package com.bis.sourcelead.dao.impl;

import java.text.MessageFormat;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.OrglocationOnboardDocumentsDao;
import com.bis.sourcelead.entity.OrglocationOnboardDocuments;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;

@Repository
public class OrglocationOnboardDocumentsDaoImpl implements OrglocationOnboardDocumentsDao{
	
	
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

	private static final Logger logger = LoggerFactory.getLogger(OrglocationOnboardDocumentsDaoImpl.class);


	@Override
	@Transactional
	public OrglocationOnboardDocuments saveOrglocationOnboardDocuments(
			OrglocationOnboardDocuments orglocationOnboardDocuments) throws Exception {
		
		
		logger.debug("Adding orglocationOnboardDocuments");
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (orglocationOnboardDocuments != null) {
				session.saveOrUpdate(orglocationOnboardDocuments);
			}
			logger.debug("orglocationOnboardDocuments Added Successfully");
		} catch (ConstraintViolationException exception) {
			StringBuffer message= new StringBuffer(MessageFormat.format(duplicateValue,"ORGLOCATION_ONBAORD_DOCUMENTS"));
			throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		return orglocationOnboardDocuments;
	}


	@Override
	@Transactional
	public OrglocationOnboardDocuments getOrglocationOnboardDocumentByDocumentName(String documentName,
			Long orgLocationId) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from OrglocationOnboardDocuments documents where documents.documentName = :documentName and documents.orgLocationId.id = :orgLocationId and documents.status = '1' ";  
		List<OrglocationOnboardDocuments> orglocationOnboardDocumentsList =  session.createQuery(query).setString("documentName", documentName).setLong("orgLocationId", orgLocationId).list();
		OrglocationOnboardDocuments orglocationOnboardDocuments = null;
		if (orglocationOnboardDocumentsList != null && orglocationOnboardDocumentsList.size() > 0) {
			orglocationOnboardDocuments = (OrglocationOnboardDocuments) orglocationOnboardDocumentsList.iterator().next();
		}
		return orglocationOnboardDocuments;
	}


	@Override
	@Transactional
	public OrglocationOnboardDocuments getOrglocationOnboardDocumentsByUUId(String uuid) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from OrglocationOnboardDocuments documents where documents.uuid = :uuid and documents.status = '1' ";  
		List<OrglocationOnboardDocuments> orglocationOnboardDocumentsList =  session.createQuery(query).setString("uuid", uuid).list();
		OrglocationOnboardDocuments orglocationOnboardDocuments = null;
		if (orglocationOnboardDocumentsList != null && orglocationOnboardDocumentsList.size() > 0) {
			orglocationOnboardDocuments = (OrglocationOnboardDocuments) orglocationOnboardDocumentsList.iterator().next();
		}
		return orglocationOnboardDocuments;
	}


	@Override
	@Transactional
	public void deleteOrglocationOnboardDocuments(OrglocationOnboardDocuments orglocationOnboardDocuments)
			throws Exception {
	
		logger.debug("Deleting orglocationOnboardDocuments");
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (orglocationOnboardDocuments != null) {
				session.delete(orglocationOnboardDocuments);
			}
			logger.debug("orglocationOnboardDocuments Deleted Successfully");
		} catch (ConstraintViolationException exception) {
			StringBuffer message= new StringBuffer(MessageFormat.format(duplicateValue,"ORGLOCATION_ONBAORD_DOCUMENTS"));
			throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		
	}

}
