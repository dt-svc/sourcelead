package com.bis.sourcelead.service.performance;

import com.bis.sourcelead.dto.performance.PerformanceTagCompetenciesDTO;
import com.bis.sourcelead.entity.performance.PerformanceTagCompetencies;

public interface PerformanceTagCompetenciesService {

	PerformanceTagCompetencies savePerformanceTagCompetencies(PerformanceTagCompetencies performanceTagCompetencies) throws Exception;
	
	PerformanceTagCompetencies storePerformanceTagCompetencies(PerformanceTagCompetenciesDTO performanceTagCompetenciesDTO,Long orgLocationId,Long userId) throws Exception;
}
