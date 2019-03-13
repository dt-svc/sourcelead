package com.bis.sourcelead.dao.impl;

import java.math.BigInteger;
import java.text.MessageFormat;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.XlsBulkUploadHistroyDao;
import com.bis.sourcelead.entity.UploadXlsHistory;
import com.bis.sourcelead.entity.XlsBulkUploadHistroy;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;

@Repository
@Transactional
public class XlsBulkUploadHistroyDaoImpl implements XlsBulkUploadHistroyDao{
	
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

	private static final Logger logger = LoggerFactory.getLogger(XlsBulkUploadHistroyDaoImpl.class);

	@Override
	public XlsBulkUploadHistroy storeXlsBulkUploadHistroy(XlsBulkUploadHistroy xlsBulkUploadHistroy) throws Exception {
		logger.debug("Adding xlsBulkUploadHistroy");
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (xlsBulkUploadHistroy != null) {
				session.saveOrUpdate(xlsBulkUploadHistroy);
			}
			logger.debug("xlsBulkUploadHistroy Added Successfully");
		} catch (ConstraintViolationException exception) {
			StringBuffer message= new StringBuffer(MessageFormat.format(duplicateValue,"XLS_BULK_UPLOAD_HISTORY"));
			throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		return xlsBulkUploadHistroy;
	}

	@Override
	public String getFileUrlByDocumentKey(String documentkey) throws Exception {

		Session session = this.sessionFactory.getCurrentSession();
		String query = null;
		query = "SELECT " + " HISTORY.FILE_URL " + " FROM XLS_BULK_UPLOAD_HISTORY HISTORY " + " WHERE "
				+ " HISTORY.DOCUMENT_KEY = :documentkey ";
		Query queryObj = session.createSQLQuery(query);
		queryObj.setString("documentkey", documentkey);
		String fileUrl = (String) queryObj.uniqueResult();
		return fileUrl;
	
	}

	@Override
	public XlsBulkUploadHistroy getXlsBulkUploadHistroyByDocumentKey(String documentKey) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from XlsBulkUploadHistroy history where history.documentKey = :documentKey  ";  
		List<XlsBulkUploadHistroy> xlsBulkUploadHistroyList =  session.createQuery(query).setString("documentKey", documentKey).list();
		XlsBulkUploadHistroy xlsBulkUploadHistroy = null;
		if (xlsBulkUploadHistroyList != null && xlsBulkUploadHistroyList.size() > 0) {
			xlsBulkUploadHistroy = (XlsBulkUploadHistroy) xlsBulkUploadHistroyList.iterator().next();
		}
		return xlsBulkUploadHistroy;
		
	}

}
