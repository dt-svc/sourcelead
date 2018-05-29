package com.bis.sourcelead.dao;

import java.util.List;

import com.bis.sourcelead.dto.CandidateOnBoardingDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.CandidateOnBoarding;
import com.bis.sourcelead.entity.OrgLocations;

public interface CandidateOnBoardingDao {

	CandidateOnBoarding saveCandidateOnBoarding(CandidateOnBoarding candidateOnBoarding) throws Exception;
	
	List<CandidateOnBoarding> getCandidateOnBoardingListByOrgLocationId(Long orgLocationId) throws Exception;
	
	CandidateOnBoarding getCandidateOnBoardingByUUID(String onboardCandidateUUID) throws Exception;
	
	Integer getCandidateOnBoardingListCountByOrgLocationId(Long orgLocationId) throws Exception;
	
	CandidateOnBoarding getCandidateOnBoardingById(Long Id) throws Exception;
	
	CandidateOnBoarding getCandidateOnBoardingByEmailAndLocationId(String email,Long orgLocationId) throws Exception;
	
	CandidateOnBoarding getCandidateOnBoardingByUserIdAndLocationId(Long onboardingUser,Long orgLocationId) throws Exception;
	
	CandidateOnBoarding getCandidateOnBoardingByCandidateIdAndOfferId(Long candidateId ,Long offerId) throws Exception;
	
	CandidateOnBoarding getCandidateOnBoardingByPersonalEmailAndLocationId(String personalEmail,Long orgLocationId) throws Exception;
	
	CandidateOnBoarding getCandidateOnBoardingByCandidateIdAndLocationId(Long candidateId,Long orgLocationId) throws Exception;
	
	ResponseList<CandidateOnBoardingDTO> getCandidateOnBoardingList(CandidateOnBoardingDTO candidateOnBoardingDTO,OrgLocations orgLocations) throws Exception;
}
