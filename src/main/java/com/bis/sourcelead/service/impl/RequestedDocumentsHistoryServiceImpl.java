package com.bis.sourcelead.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.RequestedDocumentsHistoryDao;
import com.bis.sourcelead.entity.RequestedDocumentsHistory;
import com.bis.sourcelead.service.RequestedDocumentsHistoryService;

@Service
public class RequestedDocumentsHistoryServiceImpl implements RequestedDocumentsHistoryService{

	@Autowired
	RequestedDocumentsHistoryDao requestedDocumentsHistoryDao;
	
	@Override
	public RequestedDocumentsHistory saveRequestedDocumentsHistory(RequestedDocumentsHistory requestedDocumentsHistory) throws Exception
	{
		return requestedDocumentsHistoryDao.saveRequestedDocumentsHistory(requestedDocumentsHistory);
	}
	
	
}
