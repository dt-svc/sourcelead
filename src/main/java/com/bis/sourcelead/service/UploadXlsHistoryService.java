package com.bis.sourcelead.service;

import java.util.List;

import com.bis.sourcelead.dto.UploadXlsHistoryDTO;
import com.bis.sourcelead.entity.UploadXlsHistory;

public interface UploadXlsHistoryService {

	UploadXlsHistory storeUploadXlsHistory(UploadXlsHistory uploadXlsHistory) throws Exception;
	
	UploadXlsHistoryDTO getUploadXlsHistoryByUploadId(String uploadId) throws Exception;
	
	UploadXlsHistory getUploadXlsHistoryDetailsByUploadId(String uploadId) throws Exception;
	
	UploadXlsHistoryDTO getUploadXlsHistoryDTOBySessionId(String sessionId,Long userId,Long orgLocationId,String entityType) throws Exception;
	
	List<UploadXlsHistoryDTO> getUploadXlsHistoryListByUserId(Long userId,Long orgLocationId,String entityType,String dateFormat) throws Exception;
	
}
