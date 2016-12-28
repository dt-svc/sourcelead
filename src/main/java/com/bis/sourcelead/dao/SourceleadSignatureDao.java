package com.bis.sourcelead.dao;

import java.util.List;

import com.bis.sourcelead.entity.SourceleadSignature;

public interface SourceleadSignatureDao {
	
	List<SourceleadSignature> getSourceLeadSignatureByLocationId(Long orgLocationId) throws Exception;
	
	List<SourceleadSignature> getSourceLeadSignature() throws Exception;
	
	SourceleadSignature saveSourceleadSignature(SourceleadSignature signature) throws Exception;
	
	SourceleadSignature getSourceleadSignatureByLabelNameAndOrgLocationId(String labelName, Long orgLocationId)  throws Exception;

}
