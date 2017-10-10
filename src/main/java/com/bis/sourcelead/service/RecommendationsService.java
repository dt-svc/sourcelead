package com.bis.sourcelead.service;

import java.util.List;

import com.bis.sourcelead.dto.CandidateDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.elk.index.CandidateIndex;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.RecommendedCustomizeScore;
import com.bis.sourcelead.entity.Requirement;

public interface RecommendationsService {

	List<Candidate> getAllCandidatesByLocation(Long id)throws Exception;

	Requirement getRequirementByRequirementId(Long requirementId) throws Exception;

	List<Candidate> getRecommendationsWithScore(List<Candidate> candidateList, Requirement requirement)throws Exception;
	
    List<Candidate> getRecommendationsWithScoreByUsingElk(Long orgLocationId,Requirement requirement) throws Exception;

    String getCandidateScore(Candidate candidate,Requirement requirement, RecommendedCustomizeScore recommendedCustomizeScore) throws Exception;
    
    ResponseList<CandidateIndex> getRecommendationsUpdateWithScoreByUsingElk(Long orgLocationId,RecommendedCustomizeScore recommendedCustomizeScore,Long requirementId, String candidatesSelected, CandidateDTO candidateDTO) throws Exception;
    
}
