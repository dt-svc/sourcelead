package com.bis.sourcelead.service;

import com.bis.sourcelead.dto.OrglocationOnboardDocumentsDTO;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrglocationOnboardDocuments;
import com.bis.sourcelead.entity.User;

public interface OrglocationOnboardDocumentsService {

	OrglocationOnboardDocuments saveOrglocationOnboardDocuments(OrglocationOnboardDocuments orglocationOnboardDocuments) throws Exception;
	
	public String addOrglocationOnboardDocuments(OrglocationOnboardDocumentsDTO documentsDTO, OrgLocations orgLocations,User loginUser) throws Exception;
	
    OrglocationOnboardDocuments getOrglocationOnboardDocumentByDocumentName(String documentName,Long orgLocationId) throws Exception;
	
	OrglocationOnboardDocuments getOrglocationOnboardDocumentsByUUId(String uuid) throws Exception;
	
	public void deleteOrglocationOnboardDocuments(OrglocationOnboardDocuments orglocationOnboardDocuments) throws Exception;
}
