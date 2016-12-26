package com.bis.sourcelead.dao;

import java.util.List;

import com.bis.sourcelead.entity.SourceleadSignature;

public interface SourceleadSignatureDao {
	
	List<SourceleadSignature> getSourceLeadSignature() throws Exception;

}
