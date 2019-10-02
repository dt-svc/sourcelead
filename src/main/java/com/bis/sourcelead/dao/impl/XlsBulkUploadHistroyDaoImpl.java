package com.bis.sourcelead.dao.impl;

import java.text.MessageFormat;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.XlsBulkUploadHistroyDao;
import com.bis.sourcelead.dto.XlsBulkUploadHistroyDTO;
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
		String query = "from XlsBulkUploadHistroy history left join fetch history.orgLocationId left join fetch history.orgLocationId.org left join fetch history.createdBy where history.documentKey = :documentKey  ";  
		List<XlsBulkUploadHistroy> xlsBulkUploadHistroyList =  session.createQuery(query).setString("documentKey", documentKey).list();
		XlsBulkUploadHistroy xlsBulkUploadHistroy = null;
		if (xlsBulkUploadHistroyList != null && xlsBulkUploadHistroyList.size() > 0) {
			xlsBulkUploadHistroy = (XlsBulkUploadHistroy) xlsBulkUploadHistroyList.iterator().next();
		}
		return xlsBulkUploadHistroy;
		
	}

	@Override
	public List<XlsBulkUploadHistroyDTO> getXlsBulkUploadHistroyByUploadTypeAndOrgLocationId(String uploadEntityType,String uploadType,
			Long orgLocationId,String dateFormat) throws Exception {
		List<XlsBulkUploadHistroyDTO> xlsBulkUploadHistroyDTOList = null;
		Session session = this.sessionFactory.getCurrentSession();
		String query = null;
		
		 String locationDateFormat = null;
		 if(dateFormat.equalsIgnoreCase("yyyy-MM-dd")){
			 locationDateFormat = "%Y-%m-%d";
		  }if(dateFormat.equalsIgnoreCase("dd-MM-yyyy")){
			  locationDateFormat = "%d-%m-%Y";
		  }if(dateFormat.equalsIgnoreCase("MM-dd-yyyy")){
			  locationDateFormat = "%m-%d-%Y";
		  }if(dateFormat.equalsIgnoreCase("yyyy-dd-MM")){
			  locationDateFormat = "%Y-%d-%m";
		  }
		
		query =  "SELECT "
				+" HISTORY.FAILED_COUNT AS failureCount, "
				+" HISTORY.FILE_URL AS fileUrl, "
				+" HISTORY.FAILURE_FILE_URL AS failureFileUrl, "
				+" HISTORY.DOCUMENT_KEY AS documentKey, "
				+" HISTORY.UUID AS uuid, "
				+" DATE_FORMAT(HISTORY.CREATED_DATE, '"+locationDateFormat+"') AS date, "
				+" DATE_FORMAT(HISTORY.CREATED_DATE, '%h:%i:%s %p') AS time "
				+" FROM XLS_BULK_UPLOAD_HISTORY HISTORY "
				+" WHERE "
				+" HISTORY.UPLOAD_ENTITY_TYPE = :uploadEntityType AND "
				+" HISTORY.UPLOAD_TYPE = :uploadType AND "
				+" HISTORY.ORG_LOCATION_ID = :orgLocationId AND HISTORY.STATUS = '1' ORDER BY HISTORY.ID DESC";
		
		Query queryObj = session.createSQLQuery(query);
		queryObj.setLong("orgLocationId", orgLocationId);
		queryObj.setString("uploadEntityType", uploadEntityType);
		queryObj.setString("uploadType", uploadType);
		queryObj.setMaxResults(10);
		queryObj.setResultTransformer(Transformers.aliasToBean(XlsBulkUploadHistroyDTO.class));
		xlsBulkUploadHistroyDTOList = queryObj.list();
		return xlsBulkUploadHistroyDTOList;
	}

	@Override
	public List<XlsBulkUploadHistroyDTO> getRegisteredOrgsXlsBulkUploadHistroyByUploadType(String uploadType,
			String dateFormat) throws Exception {
		List<XlsBulkUploadHistroyDTO> xlsBulkUploadHistroyDTOList = null;
		Session session = this.sessionFactory.getCurrentSession();
		String query = null;
		
		 String locationDateFormat = null;
		 if(dateFormat.equalsIgnoreCase("yyyy-MM-dd")){
			 locationDateFormat = "%Y-%m-%d";
		  }if(dateFormat.equalsIgnoreCase("dd-MM-yyyy")){
			  locationDateFormat = "%d-%m-%Y";
		  }if(dateFormat.equalsIgnoreCase("MM-dd-yyyy")){
			  locationDateFormat = "%m-%d-%Y";
		  }if(dateFormat.equalsIgnoreCase("yyyy-dd-MM")){
			  locationDateFormat = "%Y-%d-%m";
		  }
		
		query =  "SELECT "
				+" HISTORY.UPLOAD_ENTITY_TYPE AS uploadEntityType, "
				+" HISTORY.FILE_URL AS fileUrl, "
				+" HISTORY.DOCUMENT_KEY AS documentKey, "
				+" HISTORY.UUID AS uuid, "
				+" HISTORY.UPLOAD_STATUS AS uploadStatus, "
				+" HISTORY.ORG_LOCATION AS orgName, "
				+" DATE_FORMAT(HISTORY.CREATED_DATE, '"+locationDateFormat+"') AS date, "
				+" DATE_FORMAT(HISTORY.CREATED_DATE, '%h:%i:%s %p') AS time "
				+" FROM XLS_BULK_UPLOAD_HISTORY HISTORY "
				+" WHERE "
				+" HISTORY.UPLOAD_TYPE = :uploadType AND (HISTORY.UPLOAD_STATUS = 'New' OR HISTORY.UPLOAD_STATUS = 'InProgress') AND "
				+" HISTORY.STATUS = '1' ORDER BY HISTORY.ID DESC";
		
		Query queryObj = session.createSQLQuery(query);
		queryObj.setString("uploadType", uploadType);
		queryObj.setResultTransformer(Transformers.aliasToBean(XlsBulkUploadHistroyDTO.class));
		xlsBulkUploadHistroyDTOList = queryObj.list();
		return xlsBulkUploadHistroyDTOList;
	}

}
