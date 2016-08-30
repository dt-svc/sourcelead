package com.bis.sourcelead.dao;

import java.util.List;

import com.bis.sourcelead.entity.Candidate;

public interface CandidateDao {

	
	public Candidate storeCandidate(Candidate candidate) throws Exception;
	
	public Candidate getCandidateByCandidateId(Long candidateId) throws Exception;
	
	public Candidate getCandidateByCandidateIdAndOrgLocationIdAndOrgId(Long candidateId,Long orgId,Long orgLocationId) throws Exception;

	public List<Candidate> getCandidatesListByOrgLocationID(Long orgLocationId) throws Exception;
	
}