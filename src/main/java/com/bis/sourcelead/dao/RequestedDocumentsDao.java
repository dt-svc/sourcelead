package com.bis.sourcelead.dao;

import com.bis.sourcelead.entity.RequestedDocuments;

public interface RequestedDocumentsDao {
	
	RequestedDocuments saveRequestedDocuments(RequestedDocuments requestedDocuments) throws Exception;
	
	RequestedDocuments getRequestedDocumentsByRequestDocUUID(String requestDocUUID) throws Exception;
	
}
