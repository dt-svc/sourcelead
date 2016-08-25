package com.bis.sourcelead.dao;

import com.bis.sourcelead.entity.Candidate;

public interface CandidateDao {

	
	public Candidate storeCandidate(Candidate candidate) throws Exception;
	
}