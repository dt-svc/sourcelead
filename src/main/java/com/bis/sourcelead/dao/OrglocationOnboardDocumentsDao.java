package com.bis.sourcelead.dao;

import com.bis.sourcelead.entity.OrglocationOnboardDocuments;

public interface OrglocationOnboardDocumentsDao {

	OrglocationOnboardDocuments saveOrglocationOnboardDocuments(OrglocationOnboardDocuments orglocationOnboardDocuments) throws Exception;
	
	OrglocationOnboardDocuments getOrglocationOnboardDocumentByDocumentName(String documentName,Long orgLocationId) throws Exception;
	
	OrglocationOnboardDocuments getOrglocationOnboardDocumentsByUUId(String uuid) throws Exception;
	
	public void deleteOrglocationOnboardDocuments(OrglocationOnboardDocuments orglocationOnboardDocuments) throws Exception;
}
