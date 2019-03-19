package com.bis.sourcelead.dao.performance;

import java.util.List;

import com.bis.sourcelead.dto.performance.PerformanceCompetenciesDTO;
import com.bis.sourcelead.entity.performance.PerformanceCompetencies;

public interface PerformanceCompetenciesDao {
	
	PerformanceCompetencies savePerformanceCompetencies(PerformanceCompetencies performanceCompetencies) throws Exception;
	
	List<PerformanceCompetenciesDTO> getPerformanceCompetenciesListByOrgLocationId(Long orgLocationId) throws Exception;

}
