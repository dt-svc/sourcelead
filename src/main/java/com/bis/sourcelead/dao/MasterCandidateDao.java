package com.bis.sourcelead.dao;

import com.bis.sourcelead.entity.MasterCandidate;

public interface MasterCandidateDao {
	
	MasterCandidate saveMasterCandidate(MasterCandidate masterCandidate) throws Exception;
	
	MasterCandidate getMasterCandidateByEmailOrPhoneNumber(String email,String phoneNumber) throws Exception;
}
