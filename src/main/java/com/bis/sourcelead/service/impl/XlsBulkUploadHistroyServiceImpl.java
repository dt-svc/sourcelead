package com.bis.sourcelead.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.XlsBulkUploadHistroyDao;
import com.bis.sourcelead.dto.XlsBulkUploadHistroyDTO;
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

	@Override
	public List<XlsBulkUploadHistroyDTO> getXlsBulkUploadHistroyByUploadTypeAndOrgLocationId(String uploadEntityType,String uploadType,
			Long orgLocationId,String dateFormat) throws Exception {
		return xlsBulkUploadHistroyDao.getXlsBulkUploadHistroyByUploadTypeAndOrgLocationId(uploadEntityType,uploadType, orgLocationId,dateFormat);
	}

	@Override
	public List<XlsBulkUploadHistroyDTO> getRegisteredOrgsXlsBulkUploadHistroyByUploadType(String uploadType,
			String dateFormat) throws Exception {
		return xlsBulkUploadHistroyDao.getRegisteredOrgsXlsBulkUploadHistroyByUploadType(uploadType, dateFormat);
	}

	

}
