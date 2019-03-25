package com.bis.sourcelead.service.performance;

import java.util.List;

import com.bis.sourcelead.dto.performance.PerformanceTagCompetenciesDTO;
import com.bis.sourcelead.entity.performance.PerformanceTagCompetencies;

public interface PerformanceTagCompetenciesService {

	PerformanceTagCompetencies savePerformanceTagCompetencies(PerformanceTagCompetencies performanceTagCompetencies) throws Exception;
	
	PerformanceTagCompetencies storePerformanceTagCompetencies(PerformanceTagCompetenciesDTO performanceTagCompetenciesDTO,Long orgLocationId,Long userId) throws Exception;
	
	PerformanceTagCompetencies getPerformanceTagCompetenciesById(Long id) throws Exception;
	
	List<PerformanceTagCompetenciesDTO> getPerformanceTagCompetenciesList(Long orgLocationId,String dateFormat) throws Exception;
}
