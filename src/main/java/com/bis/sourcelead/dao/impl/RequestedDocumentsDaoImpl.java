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

import com.bis.sourcelead.dao.RequestedDocumentsDao;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.RequestedDocuments;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;
import com.bis.sourcelead.util.CommonUtil;

@Repository
public class RequestedDocumentsDaoImpl implements RequestedDocumentsDao{
	
private static final Logger logger = LoggerFactory.getLogger(RequestedDocumentsDaoImpl.class);
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	private CommonUtil commonUtil;
	
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
	
	
	protected boolean isNullOrEmpty(String str) {
		return (str == null || "".equals(str.trim()));
	}


	@Override
	@Transactional
	public RequestedDocuments saveRequestedDocuments(RequestedDocuments requestedDocuments) throws Exception
	{
		logger.debug("Storing Requested Documents");
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (requestedDocuments != null) {
				session.saveOrUpdate(requestedDocuments);
			}
			logger.debug("Requested Documents Stored Successfully");
		} catch (Exception exception) {
			    StringBuilder message= new StringBuilder(MessageFormat.format(duplicateValue,"RequestedDocuments"));
				throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		return requestedDocuments;
	}


	@Override
	@Transactional
	public RequestedDocuments getRequestedDocumentsByRequestDocUUID(String requestDocUUID) throws Exception
	{
		Session session = this.sessionFactory.getCurrentSession();
		List<RequestedDocuments> requestedDocuments = new ArrayList<RequestedDocuments>();
		logger.debug("getCandidateById start");
		requestedDocuments = session.createQuery("from RequestedDocuments requestedDocuments left join fetch requestedDocuments.candidateId left join fetch requestedDocuments.candidateGlobalId "
				+ " left join fetch requestedDocuments.orgLocationId orgLocation left join fetch orgLocation.org where requestedDocuments.requestDocUUID = :requestDocUUID" ).setString("requestDocUUID", requestDocUUID ).list();
		logger.debug("getCandidateById end");
		if (requestedDocuments.size() > 0) {
			return requestedDocuments.get(0);
		} else {
			return null;
		}
	}

}
