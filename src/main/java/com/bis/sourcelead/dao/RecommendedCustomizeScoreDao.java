package com.bis.sourcelead.dao;

import com.bis.sourcelead.entity.RecommendedCustomizeScore;

public interface RecommendedCustomizeScoreDao {

	RecommendedCustomizeScore saveCustomizeScore(RecommendedCustomizeScore recommendedCustomizeScore) throws Exception;
	
	RecommendedCustomizeScore getRecommendedCustomizeScoreByRequirementIdAndOrgLocationId(Long requirementId,Long orgLocationId) throws Exception;
	
	RecommendedCustomizeScore getRecommendedCustomizeScoreByRequirementId(Long requirementId) throws Exception;
}
