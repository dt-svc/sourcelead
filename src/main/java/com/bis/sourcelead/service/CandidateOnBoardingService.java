package com.bis.sourcelead.service;

import java.util.List;

import org.json.JSONObject;

import com.bis.sourcelead.entity.CandidateOnBoarding;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;

public interface CandidateOnBoardingService {

	CandidateOnBoarding saveCandidateOnBoarding(CandidateOnBoarding candidateOnBoarding) throws Exception;
	
	CandidateOnBoarding saveOnBoardingCandidatesDetails(JSONObject jsonObj,OrgLocations orgLocations,User user) throws Exception;
	
	List<CandidateOnBoarding> getCandidateOnBoardingListByOrgLocationId(Long orgLocationId) throws Exception;
	
	CandidateOnBoarding getCandidateOnBoardingByUUID(String onboardCandidateUUID) throws Exception;
	
	Integer getCandidateOnBoardingListCountByOrgLocationId(Long orgLocationId) throws Exception;
	
	CandidateOnBoarding getCandidateOnBoardingById(Long Id) throws Exception;
}
