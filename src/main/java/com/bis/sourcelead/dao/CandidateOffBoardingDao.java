package com.bis.sourcelead.dao;

import java.util.List;

import com.bis.sourcelead.dto.CandidateOffBoardingDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.CandidateOffBoarding;
import com.bis.sourcelead.entity.OrgLocations;

public interface CandidateOffBoardingDao {
	
	CandidateOffBoarding saveCandidateOffBoarding(CandidateOffBoarding candidateOffBoarding) throws Exception;
	
	CandidateOffBoarding getCandidateOffBoardingById(Long Id) throws Exception;
	
	List<CandidateOffBoarding> getCandidateOffBoardingListByOrgLocationId(Long orgLocationId) throws Exception;
	
	Integer getCandidateOffBoardingListCountByOrgLocationId(Long orgLocationId) throws Exception;
	
	CandidateOffBoarding getCandidateOffBoardingByOffboardUUID(String offboardUUID) throws Exception;
	
	CandidateOffBoarding getCandidateOffBoardingByUserIdAndLocationId(Long offboardingUser,Long orgLocationId) throws Exception;

	List<CandidateOffBoarding> candidateOffboringDelayMail();
	
	ResponseList<CandidateOffBoardingDTO> getCandidateOffBoardingList(CandidateOffBoardingDTO candidateOffBoardingDTO,OrgLocations orgLocations) throws Exception;

}
