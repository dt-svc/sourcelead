package com.bis.sourcelead.dao.performance;

import java.util.List;

import com.bis.sourcelead.entity.performance.PerformanceTagCompetencies;

public interface PerformanceTagCompetenciesDao {

	PerformanceTagCompetencies savePerformanceTagCompetencies(PerformanceTagCompetencies performanceTagCompetencies) throws Exception;
	
	PerformanceTagCompetencies getPerformanceTagCompetenciesById(Long id) throws Exception;
	
	List<PerformanceTagCompetencies> getPerformanceTagCompetenciesList(Long orgLocationId) throws Exception;
}
