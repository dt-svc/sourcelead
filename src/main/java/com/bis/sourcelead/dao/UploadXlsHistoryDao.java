package com.bis.sourcelead.dao;

import com.bis.sourcelead.dto.UploadXlsHistoryDTO;
import com.bis.sourcelead.entity.UploadXlsHistory;

public interface UploadXlsHistoryDao {

    UploadXlsHistory storeUploadXlsHistory(UploadXlsHistory uploadXlsHistory) throws Exception;
	
    UploadXlsHistoryDTO getUploadXlsHistoryByUploadId(String uploadId) throws Exception;
    
    UploadXlsHistory getUploadXlsHistoryDetailsByUploadId(String uploadId) throws Exception;
    
    UploadXlsHistoryDTO getUploadXlsHistoryDTOBySessionId(String sessionId,Long userId,Long orgLocationId,String entityType) throws Exception;
}
