package com.bis.sourcelead.service;

import java.util.List;

import com.bis.sourcelead.dto.CandidateOnBoardingDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.CandidateOnBoarding;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;

public interface CandidateOnBoardingService {

	CandidateOnBoarding saveCandidateOnBoarding(CandidateOnBoarding candidateOnBoarding) throws Exception;
	
	CandidateOnBoarding saveOnBoardingCandidatesDetails(CandidateOnBoardingDTO candidateOnBoardingDTO,OrgLocations orgLocations,User user) throws Exception;
	
	List<CandidateOnBoarding> getCandidateOnBoardingListByOrgLocationId(Long orgLocationId) throws Exception;
	
	CandidateOnBoarding getCandidateOnBoardingByUUID(String onboardCandidateUUID) throws Exception;
	
	Integer getCandidateOnBoardingListCountByOrgLocationId(Long orgLocationId) throws Exception;
	
	CandidateOnBoarding getCandidateOnBoardingById(Long Id) throws Exception;
	
	public void createOnboardingUser(CandidateOnBoarding candidateOnBoarding,User user,OrgLocations orgLocations) throws Exception;
	
	CandidateOnBoarding getCandidateOnBoardingByEmailAndLocationId(String email,Long orgLocationId) throws Exception;
	
	CandidateOnBoarding getCandidateOnBoardingByUserIdAndLocationId(Long onboardingUser,Long orgLocationId) throws Exception;
	
	CandidateOnBoarding getCandidateOnBoardingByCandidateIdAndOfferId(Long candidateId ,Long offerId) throws Exception;
	
	CandidateOnBoarding getCandidateOnBoardingByPersonalEmailAndLocationId(String personalEmail,Long orgLocationId) throws Exception;
	
	CandidateOnBoarding getCandidateOnBoardingByCandidateIdAndLocationId(Long candidateId,Long orgLocationId) throws Exception;
	
	ResponseList<CandidateOnBoardingDTO> getCandidateOnBoardingList(CandidateOnBoardingDTO candidateOnBoardingDTO,OrgLocations orgLocations) throws Exception;
	
}
