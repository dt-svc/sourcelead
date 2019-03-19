package com.bis.sourcelead.service.performance;

import java.util.List;

import com.bis.sourcelead.dto.performance.PerformanceCompetenciesDTO;
import com.bis.sourcelead.entity.performance.PerformanceCompetencies;

public interface PerformanceCompetenciesService {

	PerformanceCompetencies savePerformanceCompetencies(PerformanceCompetencies performanceCompetencies) throws Exception;
	
	List<PerformanceCompetenciesDTO> getPerformanceCompetenciesListByOrgLocationId(Long orgLocationId) throws Exception;
	
	PerformanceCompetencies storePerformanceCompetencies(PerformanceCompetenciesDTO performanceCompetenciesDTO, Long orgLocationId, Long userId) throws Exception;
}
