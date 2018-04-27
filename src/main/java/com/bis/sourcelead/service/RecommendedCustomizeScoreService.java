package com.bis.sourcelead.service;

import com.bis.sourcelead.beans.CandidateCompareDTO;
import com.bis.sourcelead.dto.CandidateDTO;
import com.bis.sourcelead.entity.RecommendedCustomizeScore;
import com.bis.sourcelead.entity.Requirement;

public interface RecommendedCustomizeScoreService {

	RecommendedCustomizeScore saveCustomizeScore(Requirement requirement) throws Exception;
	
	RecommendedCustomizeScore getRecommendedCustomizeScoreByRequirementIdAndOrgLocationId(Long requirementId,Long orgLocationId) throws Exception;
	
	RecommendedCustomizeScore updateCustomizeScore(RecommendedCustomizeScore recommendedCustomizeScore) throws Exception;
	
	RecommendedCustomizeScore getRecommendedCustomizeScoreByRequirementId(Long requirementId) throws Exception;

	CandidateCompareDTO getCandidateListToCompare(Long requirementId, Long orgLocationId, String candidatesSelected,CandidateDTO candidateDTO) throws Exception;
	
}
