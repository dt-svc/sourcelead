package com.bis.sourcelead.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.RequestedDocumentsDao;
import com.bis.sourcelead.entity.RequestedDocuments;
import com.bis.sourcelead.service.RequestedDocumentsService;

@Service
public class RequestedDocumentsServiceImpl implements RequestedDocumentsService{

	@Autowired
	RequestedDocumentsDao requestedDocumentsDao;
	
	@Override
	public RequestedDocuments saveRequestedDocuments(RequestedDocuments requestedDocuments) throws Exception
	{
		return requestedDocumentsDao.saveRequestedDocuments(requestedDocuments);
	}
	
}
