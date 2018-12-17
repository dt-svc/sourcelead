package com.bis.sourcelead.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.UploadXlsHistoryDao;
import com.bis.sourcelead.dto.UploadXlsHistoryDTO;
import com.bis.sourcelead.entity.UploadXlsHistory;
import com.bis.sourcelead.service.UploadXlsHistoryService;

@Service
public class UploadXlsHistoryServiceImpl implements UploadXlsHistoryService{

	@Autowired
	UploadXlsHistoryDao uploadXlsHistoryDao;
	
	@Override
	public UploadXlsHistory storeUploadXlsHistory(UploadXlsHistory uploadXlsHistory) throws Exception {
		return uploadXlsHistoryDao.storeUploadXlsHistory(uploadXlsHistory);
	}

	@Override
	public UploadXlsHistoryDTO getUploadXlsHistoryByUploadId(String uploadId) throws Exception {
		return uploadXlsHistoryDao.getUploadXlsHistoryByUploadId(uploadId);
	}

	@Override
	public UploadXlsHistory getUploadXlsHistoryDetailsByUploadId(String uploadId) throws Exception {
		return uploadXlsHistoryDao.getUploadXlsHistoryDetailsByUploadId(uploadId);
	}

	@Override
	public UploadXlsHistoryDTO getUploadXlsHistoryDTOBySessionId(String sessionId, Long userId, Long orgLocationId,
			String entityType) throws Exception {
		return uploadXlsHistoryDao.getUploadXlsHistoryDTOBySessionId(sessionId, userId, orgLocationId, entityType);
	}

	@Override
	public List<UploadXlsHistoryDTO> getUploadXlsHistoryListByUserId(Long userId, Long orgLocationId,
			String entityType,String dateFormat) throws Exception {
		return uploadXlsHistoryDao.getUploadXlsHistoryListByUserId(userId, orgLocationId, entityType,dateFormat);
	}

}
