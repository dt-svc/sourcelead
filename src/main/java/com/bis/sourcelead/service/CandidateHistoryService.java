package com.bis.sourcelead.service;

import java.util.HashSet;
import java.util.List;

import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.CandidateHistory;
import com.bis.sourcelead.entity.User;

public interface CandidateHistoryService {

	HashSet<CandidateHistory> getCandidateHistoryDetails(Candidate candidate,User user) throws Exception;
	
	List<CandidateHistory> getCandidateHistoryListByGlobalCandidateId(Long candidateGlobalId) throws Exception;
	
	public CandidateHistory storeCandidateHistory(CandidateHistory candidateHistory) throws Exception;
}
