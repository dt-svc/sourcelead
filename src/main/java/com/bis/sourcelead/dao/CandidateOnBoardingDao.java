package com.bis.sourcelead.dao;

import java.util.List;

import com.bis.sourcelead.entity.CandidateOnBoarding;

public interface CandidateOnBoardingDao {

	CandidateOnBoarding saveCandidateOnBoarding(CandidateOnBoarding candidateOnBoarding) throws Exception;
	
	List<CandidateOnBoarding> getCandidateOnBoardingListByOrgLocationId(Long orgLocationId) throws Exception;
	
	CandidateOnBoarding getCandidateOnBoardingByUUID(String onboardCandidateUUID) throws Exception;
	
	Integer getCandidateOnBoardingListCountByOrgLocationId(Long orgLocationId) throws Exception;
	
	CandidateOnBoarding getCandidateOnBoardingById(Long Id) throws Exception;
	
	CandidateOnBoarding getCandidateOnBoardingByEmailAndLocationId(String email,Long orgLocationId) throws Exception;
}
