package com.bis.sourcelead.dao.impl;

import java.text.MessageFormat;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.UploadXlsHistoryDao;
import com.bis.sourcelead.dto.UploadXlsHistoryDTO;
import com.bis.sourcelead.entity.UploadXlsHistory;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;

@Repository
public class UploadXlsHistoryDaoImpl implements UploadXlsHistoryDao{

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
	

	private static final Logger logger = LoggerFactory.getLogger(UploadXlsHistoryDaoImpl.class);


	@Override
	@Transactional
	public UploadXlsHistory storeUploadXlsHistory(UploadXlsHistory uploadXlsHistory) throws Exception {
		logger.debug("Storing UploadXlsHistory");
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (uploadXlsHistory != null) {
				session.saveOrUpdate(uploadXlsHistory);
			}
			logger.debug("UploadXlsHistory Stored Successfully");
		} catch (Exception exception) {
			    StringBuilder message= new StringBuilder(MessageFormat.format(duplicateValue,"UploadXlsHistory"));
				throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		return uploadXlsHistory;
	}


	@Override
	@Transactional
	public UploadXlsHistoryDTO getUploadXlsHistoryByUploadId(String uploadId) throws Exception {
		
		List<UploadXlsHistoryDTO> uploadXlsHistoryDTOList = null;
		UploadXlsHistoryDTO uploadXlsHistoryDTO = null;
		Session session = this.sessionFactory.getCurrentSession();
		String query = null;
				query ="SELECT "
				+" HISTORY.TOTAL_COUNT AS totalCount, "
				+" HISTORY.SUCCESS_COUNT AS successCount, "
				+" HISTORY.FAILURE_COUNT AS failureCount "
				+" FROM UPLOAD_XLS_HISTORY HISTORY "
				+" WHERE "
				+" HISTORY.UPLOAD_ID = :uploadId ";
		    
            Query queryObj = session.createSQLQuery(query);
			queryObj.setString("uploadId", uploadId);
			queryObj.setResultTransformer(Transformers.aliasToBean(UploadXlsHistoryDTO.class));
			uploadXlsHistoryDTOList = queryObj.list();
			if(uploadXlsHistoryDTOList != null && !uploadXlsHistoryDTOList.isEmpty()) {
				uploadXlsHistoryDTO = uploadXlsHistoryDTOList.get(0);
			}
			
			return uploadXlsHistoryDTO;
	
	}
	
	
	@Override
	@Transactional
	public UploadXlsHistory getUploadXlsHistoryDetailsByUploadId(String uploadId) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from UploadXlsHistory history  where history.uploadId = :uploadId  ";  
		List<UploadXlsHistory> uploadXlsHistoryList =  session.createQuery(query).setString("uploadId", uploadId).list();
		UploadXlsHistory uploadXlsHistory = null;
		if (uploadXlsHistoryList != null && uploadXlsHistoryList.size() > 0) {
			uploadXlsHistory = (UploadXlsHistory) uploadXlsHistoryList.iterator().next();
		}
		return uploadXlsHistory;
		
	}


	@Override
	@Transactional
	public UploadXlsHistoryDTO getUploadXlsHistoryDTOBySessionId(String sessionId, Long userId, Long orgLocationId,
			String entityType) throws Exception {
		
		List<UploadXlsHistoryDTO> uploadXlsHistoryDTOList = null;
		UploadXlsHistoryDTO uploadXlsHistoryDTO = null;
		Session session = this.sessionFactory.getCurrentSession();
		String query = null;
				query ="SELECT "
				+" HISTORY.TOTAL_COUNT AS totalCount, "
				+" HISTORY.SUCCESS_COUNT AS successCount, "
				+" HISTORY.FAILURE_COUNT AS failureCount, "
				+" HISTORY.UPLOAD_PROCESSING_STATUS AS uploadProcessingStatus "
				+" FROM UPLOAD_XLS_HISTORY HISTORY "
				+" WHERE "
				+" HISTORY.SESSION_ID = :sessionId AND "
				+" HISTORY.USER_ID = :userId AND "
				+" HISTORY.ORG_LOCATION_ID = :orgLocationId AND "
				+" HISTORY.UPLOAD_ENTITY_TYPE = :entityType AND "
				+" HISTORY.UPLOAD_PROCESSING_STATUS = 'Inprogress' ";
		    
            Query queryObj = session.createSQLQuery(query);
			queryObj.setString("sessionId", sessionId);
			queryObj.setLong("userId", userId);
			queryObj.setLong("orgLocationId", orgLocationId);
			queryObj.setString("entityType", entityType);
			queryObj.setResultTransformer(Transformers.aliasToBean(UploadXlsHistoryDTO.class));
			uploadXlsHistoryDTOList = queryObj.list();
			if(uploadXlsHistoryDTOList != null && !uploadXlsHistoryDTOList.isEmpty()) {
				uploadXlsHistoryDTO = uploadXlsHistoryDTOList.get(0);
			}
			
			return uploadXlsHistoryDTO;
	
	}


	@Override
	@Transactional
	public List<UploadXlsHistoryDTO> getUploadXlsHistoryListByUserId(Long userId, Long orgLocationId, String entityType,String dateFormat)
			throws Exception {
		List<UploadXlsHistoryDTO> uploadXlsHistoryDTOList = null;
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
				+" HISTORY.ID AS uploadHistoryId, "
				+" HISTORY.TOTAL_COUNT AS totalCount, "
				+" HISTORY.SUCCESS_COUNT AS successCount, "
				+" HISTORY.FAILURE_COUNT AS failureCount, "
				+" HISTORY.FILE_NAME AS fileName, "
				+" HISTORY.FILE_URL AS fileUrl, "
				+" HISTORY.FILE_CONTENT_TYPE AS fileContentType, "
				+" HISTORY.UUID AS uuid, "
				+" DATE_FORMAT(HISTORY.CREATED_DATE, '"+locationDateFormat+"') AS date, "
				+" DATE_FORMAT(HISTORY.CREATED_DATE, '%h:%i:%s %p') AS time, "
				+" HISTORY.UPLOAD_PROCESSING_STATUS AS uploadProcessingStatus "
				+" FROM UPLOAD_XLS_HISTORY HISTORY "
				+" WHERE "
				+" HISTORY.USER_ID = :userId AND "
				+" HISTORY.ORG_LOCATION_ID = :orgLocationId AND "
				+" HISTORY.UPLOAD_ENTITY_TYPE = :entityType ORDER BY HISTORY.ID DESC";
		
		Query queryObj = session.createSQLQuery(query);
		queryObj.setLong("userId", userId);
		queryObj.setLong("orgLocationId", orgLocationId);
		queryObj.setString("entityType", entityType);
		queryObj.setMaxResults(5);
		queryObj.setResultTransformer(Transformers.aliasToBean(UploadXlsHistoryDTO.class));
		uploadXlsHistoryDTOList = queryObj.list();
		return uploadXlsHistoryDTOList;
	}

}
