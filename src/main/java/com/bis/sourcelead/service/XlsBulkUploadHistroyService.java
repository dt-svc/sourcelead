package com.bis.sourcelead.service;

import com.bis.sourcelead.entity.XlsBulkUploadHistroy;

public interface XlsBulkUploadHistroyService {

	XlsBulkUploadHistroy storeXlsBulkUploadHistroy(XlsBulkUploadHistroy xlsBulkUploadHistroy) throws Exception;
	
	String getFileUrlByDocumentKey(String documentKey) throws Exception;
	
	XlsBulkUploadHistroy getXlsBulkUploadHistroyByDocumentKey(String documentKey) throws Exception;
}
