package com.bis.sourcelead.service;

import java.util.List;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.CandidateEmployers;
import com.google.gson.JsonObject;

@Service
public interface CandidateEmployersService {

	public CandidateEmployers storeCandidateEmployers(JSONObject jsonObject) throws Exception; ; 
	
	public List<CandidateEmployers>   getCandidatesEmployersListByCandidateIdAndOrgLocationID(Long candidateId, Long orgLocationId) throws Exception;

}
