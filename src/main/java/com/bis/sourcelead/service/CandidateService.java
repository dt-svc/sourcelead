package com.bis.sourcelead.service;

import java.util.List;

import org.json.JSONObject;

import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;

public interface CandidateService {
	
	public Candidate storeCandidate(JSONObject jsonObj,OrgLocations orgLocations,User user) throws Exception;

	public Candidate addCandidate(Candidate candidate) throws Exception;

	public Candidate getCandidateByCandidateIdAndOrgLocationIdAndOrgId(Long candidateId,Long orgId,Long orgLocationId) throws Exception;
	
	public List<Candidate> getCandidatesListByOrgIdAndOrgLocationID(Long orgLocationId) throws Exception;
	
}
