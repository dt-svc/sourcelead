package com.bis.sourcelead.service;

import com.bis.sourcelead.entity.RecommendedCustomizeScore;
import com.bis.sourcelead.entity.Requirement;
import com.bis.sourcelead.entity.Tasks;

public interface RecommendedCustomizeScoreService {

	RecommendedCustomizeScore saveCustomizeScore(Requirement requirement) throws Exception;
	
	RecommendedCustomizeScore getRecommendedCustomizeScoreByRequirementIdAndOrgLocationId(Long requirementId,Long orgLocationId) throws Exception;
	
	RecommendedCustomizeScore updateCustomizeScore(RecommendedCustomizeScore recommendedCustomizeScore) throws Exception;
	
	RecommendedCustomizeScore getRecommendedCustomizeScoreByRequirementId(Long requirementId) throws Exception;
	
}
