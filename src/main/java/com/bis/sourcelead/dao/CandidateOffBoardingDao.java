package com.bis.sourcelead.dao;

import java.util.List;

import com.bis.sourcelead.entity.CandidateOffBoarding;

public interface CandidateOffBoardingDao {
	
	CandidateOffBoarding saveCandidateOffBoarding(CandidateOffBoarding candidateOffBoarding) throws Exception;
	
	CandidateOffBoarding getCandidateOffBoardingById(Long Id) throws Exception;
	
	List<CandidateOffBoarding> getCandidateOffBoardingListByOrgLocationId(Long orgLocationId) throws Exception;
	
	Integer getCandidateOffBoardingListCountByOrgLocationId(Long orgLocationId) throws Exception;
	
	CandidateOffBoarding getCandidateOffBoardingByOffboardUUID(String offboardUUID) throws Exception;
	
	CandidateOffBoarding getCandidateOffBoardingByUserIdAndLocationId(Long offboardingUser,Long orgLocationId) throws Exception;

	List<CandidateOffBoarding> candidateOffboringDelayMail();

}
