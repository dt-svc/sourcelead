package com.bis.sourcelead.service;

import com.bis.sourcelead.entity.RecommendedCustomizeScore;
import com.bis.sourcelead.entity.Requirement;

public interface RecommendedCustomizeScoreService {

	RecommendedCustomizeScore saveCustomizeScore(Requirement requirement) throws Exception;
	
	
}
