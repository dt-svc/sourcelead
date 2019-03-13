package com.bis.sourcelead.dao;

import com.bis.sourcelead.entity.MasterCandidate;

public interface MasterCandidateDao {
	
	MasterCandidate saveMasterCandidate(MasterCandidate masterCandidate) throws Exception;
	
	MasterCandidate getMasterCandidateByEmailOrPhoneNumber(String email,String phoneNumber) throws Exception;
	
	Long getMasterCandidateIdByEmail(String email) throws Exception;

	void changeDocumentNamesWithWhiteSpacesToUnderScore(String staticPath);
}
