package com.bis.sourcelead.service;

import org.json.JSONObject;

import com.bis.sourcelead.entity.CandidateOnBoarding;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;

public interface CandidateOnBoardingService {

	CandidateOnBoarding saveCandidateOnBoarding(CandidateOnBoarding candidateOnBoarding) throws Exception;
	
	CandidateOnBoarding saveOnBoardingCandidatesDetails(JSONObject jsonObj,OrgLocations orgLocations,User user) throws Exception;
}
