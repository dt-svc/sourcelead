package com.bis.sourcelead.service;

import java.util.List;

import com.bis.sourcelead.dto.XlsBulkUploadHistroyDTO;
import com.bis.sourcelead.entity.XlsBulkUploadHistroy;

public interface XlsBulkUploadHistroyService {

	XlsBulkUploadHistroy storeXlsBulkUploadHistroy(XlsBulkUploadHistroy xlsBulkUploadHistroy) throws Exception;
	
	String getFileUrlByDocumentKey(String documentKey) throws Exception;
	
	XlsBulkUploadHistroy getXlsBulkUploadHistroyByDocumentKey(String documentKey) throws Exception;
	
	List<XlsBulkUploadHistroyDTO> getXlsBulkUploadHistroyByUploadTypeAndOrgLocationId(String uploadEntityType, String uploadType,Long orgLocationId,String dateFormat) throws Exception;
	
	List<XlsBulkUploadHistroyDTO> getRegisteredOrgsXlsBulkUploadHistroyByUploadType(String uploadType,String dateFormat) throws Exception;
	
}
