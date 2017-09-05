package com.bis.sourcelead.service;

import org.json.JSONObject;

import com.bis.sourcelead.entity.MasterCandidate;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;

public interface MasterCandidateService {
	
	MasterCandidate saveMasterCandidate(MasterCandidate masterCandidate) throws Exception;
	
	MasterCandidate getMasterCandidateByInteligence(JSONObject jsonObj) throws Exception;
	
	MasterCandidate storeMasterCandidateByJsonObject(JSONObject jsonObj,User user,OrgLocations orgLocations) throws Exception;
	
}
