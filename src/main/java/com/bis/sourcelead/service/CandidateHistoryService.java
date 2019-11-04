package com.bis.sourcelead.service;

import java.util.HashSet;
import java.util.List;

import com.bis.sourcelead.entity.CandidateGlobal;
import com.bis.sourcelead.entity.CandidateHistory;
import com.bis.sourcelead.entity.User;

public interface CandidateHistoryService {

	void saveCandidateHistoryDetails(CandidateGlobal candidate,User user) throws Exception;
	
	List<CandidateHistory> getCandidateHistoryListByGlobalCandidateId(Long candidateGlobalId) throws Exception;
	
	public CandidateHistory storeCandidateHistory(CandidateHistory candidateHistory) throws Exception;
}
