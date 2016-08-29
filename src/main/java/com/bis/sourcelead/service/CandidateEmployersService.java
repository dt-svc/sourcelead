package com.bis.sourcelead.service;

import java.util.List;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.CandidateEmployers;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.google.gson.JsonObject;

@Service
public interface CandidateEmployersService {

	public CandidateEmployers storeCandidateEmployers(JSONObject jsonObject,User user,OrgLocations orgLocationId,Long candidateId) throws Exception; ; 
	
	public List<CandidateEmployers>   getCandidatesEmployersListByCandidateIdAndOrgLocationID(Long candidateId, Long orgLocationId) throws Exception;

}
