package com.bis.sourcelead.dao;

import java.util.List;

import org.json.JSONObject;

import com.bis.sourcelead.entity.CandidateEmployers;

public interface CandidateEmployersDao {

	public CandidateEmployers storeCandidateEmployers(JSONObject jsonObject) throws Exception;
	
	public List<CandidateEmployers>   getCandidatesEmployersListByCandidateIdAndOrgLocationID(Long candidateId, Long orgLocationId) throws Exception;

}
