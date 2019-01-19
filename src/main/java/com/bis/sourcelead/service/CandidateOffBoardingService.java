package com.bis.sourcelead.service;

import java.util.List;

import org.json.JSONObject;

import com.bis.sourcelead.dto.CandidateOffBoardingDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.CandidateOffBoarding;
import com.bis.sourcelead.entity.CandidateOnBoarding;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;

public interface CandidateOffBoardingService {

	CandidateOffBoarding saveCandidateOffBoarding(CandidateOffBoarding candidateOffBoarding) throws Exception;
	
	CandidateOffBoarding saveOffBoardingCandidatesDetails(CandidateOffBoardingDTO candidateOffBoardingDTO,OrgLocations orgLocations,User user) throws Exception;
	
	CandidateOffBoarding getCandidateOffBoardingById(Long Id) throws Exception;
	
	List<CandidateOffBoarding> getCandidateOffBoardingListByOrgLocationId(Long orgLocationId) throws Exception;
	
	Integer getCandidateOffBoardingListCountByOrgLocationId(Long orgLocationId) throws Exception;
	
	CandidateOffBoarding getCandidateOffBoardingByOffboardUUID(String offboardUUID) throws Exception;
	
	public void terminateOffboardingUser(CandidateOffBoarding candidateOffBoarding,User user,OrgLocations orgLocations) throws Exception;
	
	CandidateOffBoarding getCandidateOffBoardingByUserIdAndLocationId(Long offboardingUser,Long orgLocationId) throws Exception;

	List<CandidateOffBoarding> candidateOffboringDelayMail();
	
	ResponseList<CandidateOffBoardingDTO> getCandidateOffBoardingList(CandidateOffBoardingDTO candidateOffBoardingDTO,OrgLocations orgLocations) throws Exception;
}
