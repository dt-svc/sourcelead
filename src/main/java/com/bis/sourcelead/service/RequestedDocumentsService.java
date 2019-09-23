package com.bis.sourcelead.service;

import org.json.JSONObject;

import com.bis.sourcelead.dto.CandidateDocumentsDTO;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.CandidateGlobal;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.RequestedDocuments;
import com.bis.sourcelead.entity.User;

public interface RequestedDocumentsService {
	
	RequestedDocuments saveRequestedDocuments(RequestedDocuments requestedDocuments) throws Exception;
	
	public void storeRequestedDocumentsDetails(CandidateDocumentsDTO candidateDocumentsDTO,User user,OrgLocations orgLocations) throws Exception;
	
	RequestedDocuments getRequestedDocumentsByRequestDocUUID(String requestDocUUID) throws Exception;
	
	public void rejectRequestedDocuments(JSONObject jsonObj,User user,OrgLocations orgLocations,String documentsIds, String candidateType) throws Exception;
	
	public void acceptRequestedDocuments(User user,String documentsIds, OrgLocations orgLocations, Candidate candidate) throws Exception;
	
	public void acceptRequestedCandidateGlobalDocuments(User user,String documentsIds, OrgLocations orgLocations, CandidateGlobal candidateGlobal) throws Exception;
}
