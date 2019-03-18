package com.bis.sourcelead.dao;

import java.util.List;

import com.bis.sourcelead.dto.XlsBulkUploadHistroyDTO;
import com.bis.sourcelead.entity.XlsBulkUploadHistroy;

public interface XlsBulkUploadHistroyDao {

	XlsBulkUploadHistroy storeXlsBulkUploadHistroy(XlsBulkUploadHistroy xlsBulkUploadHistroy) throws Exception;
	
	String getFileUrlByDocumentKey(String documentId) throws Exception;
	
	XlsBulkUploadHistroy getXlsBulkUploadHistroyByDocumentKey(String documentKey) throws Exception;
	
	List<XlsBulkUploadHistroyDTO> getXlsBulkUploadHistroyByUploadTypeAndOrgLocationId(String uploadEntityType,String uploadType,Long orgLocationId,String dateFormat) throws Exception;
	
	List<XlsBulkUploadHistroyDTO> getRegisteredOrgsXlsBulkUploadHistroyByUploadType(String uploadType,String dateFormat) throws Exception;
}
