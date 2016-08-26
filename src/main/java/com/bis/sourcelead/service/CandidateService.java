package com.bis.sourcelead.service;

import org.json.JSONObject;

import com.bis.sourcelead.entity.Candidate;

public interface CandidateService {
	
	public Candidate storeCandidate(JSONObject jsonObj) throws Exception;

}
