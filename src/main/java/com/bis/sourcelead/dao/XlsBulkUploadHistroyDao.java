package com.bis.sourcelead.dao;

import com.bis.sourcelead.entity.XlsBulkUploadHistroy;

public interface XlsBulkUploadHistroyDao {

	XlsBulkUploadHistroy storeXlsBulkUploadHistroy(XlsBulkUploadHistroy xlsBulkUploadHistroy) throws Exception;
	
	String getFileUrlByDocumentKey(String documentId) throws Exception;
	
	XlsBulkUploadHistroy getXlsBulkUploadHistroyByDocumentKey(String documentKey) throws Exception;
}
