package com.bis.sourcelead.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.XlsBulkUploadHistroyDao;
import com.bis.sourcelead.entity.XlsBulkUploadHistroy;
import com.bis.sourcelead.service.XlsBulkUploadHistroyService;

@Service
public class XlsBulkUploadHistroyServiceImpl implements XlsBulkUploadHistroyService{
	
	@Autowired
	XlsBulkUploadHistroyDao xlsBulkUploadHistroyDao;

	@Override
	public XlsBulkUploadHistroy storeXlsBulkUploadHistroy(XlsBulkUploadHistroy xlsBulkUploadHistroy) throws Exception {
		return xlsBulkUploadHistroyDao.storeXlsBulkUploadHistroy(xlsBulkUploadHistroy);
	}

	@Override
	public String getFileUrlByDocumentKey(String documentKey) throws Exception {
		return xlsBulkUploadHistroyDao.getFileUrlByDocumentKey(documentKey);
	}

	@Override
	public XlsBulkUploadHistroy getXlsBulkUploadHistroyByDocumentKey(String documentKey) throws Exception {
		return xlsBulkUploadHistroyDao.getXlsBulkUploadHistroyByDocumentKey(documentKey);
	}

}
