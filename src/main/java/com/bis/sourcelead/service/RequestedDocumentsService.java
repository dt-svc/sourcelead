package com.bis.sourcelead.service;

import org.json.JSONObject;

import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.RequestedDocuments;
import com.bis.sourcelead.entity.User;

public interface RequestedDocumentsService {
	
	RequestedDocuments saveRequestedDocuments(RequestedDocuments requestedDocuments) throws Exception;
	
	public void storeRequestedDocumentsDetails(JSONObject jsonObj,User user,OrgLocations orgLocations) throws Exception;
	
	RequestedDocuments getRequestedDocumentsByRequestDocUUID(String requestDocUUID) throws Exception;
}
