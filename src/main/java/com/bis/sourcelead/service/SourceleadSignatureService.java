package com.bis.sourcelead.service;

import java.util.List;

import org.json.JSONObject;

import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.SourceleadSignature;
import com.bis.sourcelead.entity.User;

public interface SourceleadSignatureService {
	
	List<SourceleadSignature> getSourceLeadSignatureByLocationId(Long orgLocationId) throws Exception;
	
	List<SourceleadSignature> getSourceLeadSignature() throws Exception;
	
	SourceleadSignature saveSourceleadSignature(SourceleadSignature signature) throws Exception;
	
	void saveOrgSignatrue(JSONObject jsonObj,OrgLocations orgLocations,User user) throws Exception; 
	
	SourceleadSignature getSourceleadSignatureByLabelNameAndOrgLocationId(String labelName, Long orgLocationId)  throws Exception;
	
	String buildSignature(User user,OrgLocations orgLocations) throws Exception;
	
	void restoreSignature(User user,OrgLocations orgLocations) throws Exception;
	
	

}
