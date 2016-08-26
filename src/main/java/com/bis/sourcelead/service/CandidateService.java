package com.bis.sourcelead.service;

import java.util.List;

import org.json.JSONObject;

import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.Employees;

public interface CandidateService {
	
	public Candidate storeCandidate(JSONObject jsonObj) throws Exception;

	public Candidate getCandidateByCandidateIdAndOrgLocationIdAndOrgId(Long candidateId,Long orgId,Long orgLocationId) throws Exception;
	
	public List<Candidate> getCandidatesListByOrgIdAndOrgLocationID(Long orgId,Long orgLocationId) throws Exception;
	
}
