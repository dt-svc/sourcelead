package com.bis.sourcelead.service;

import org.json.JSONObject;

import com.bis.sourcelead.dto.CandidateDTO;
import com.bis.sourcelead.entity.MasterCandidate;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;

public interface MasterCandidateService {
	
	MasterCandidate saveMasterCandidate(MasterCandidate masterCandidate) throws Exception;
	
	MasterCandidate getMasterCandidateByInteligence(JSONObject jsonObj) throws Exception;
	
	MasterCandidate storeMasterCandidateByJsonObject(JSONObject jsonObj,User user,OrgLocations orgLocations) throws Exception;
	
	MasterCandidate getMasterCandidateByInteligenceCandidateDTO(CandidateDTO candidateDTO) throws Exception;
	
	MasterCandidate storeMasterCandidateByCandidateDTO(CandidateDTO candidateDTO,User user) throws Exception;
	
	Long getMasterCandidateIdByEmail(String email) throws Exception;
	
}
