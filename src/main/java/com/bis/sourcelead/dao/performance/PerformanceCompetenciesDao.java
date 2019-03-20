package com.bis.sourcelead.dao.performance;

import java.util.List;

import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.dto.performance.PerformanceCompetenciesDTO;
import com.bis.sourcelead.entity.performance.PerformanceCompetencies;

public interface PerformanceCompetenciesDao {
	
	PerformanceCompetencies savePerformanceCompetencies(PerformanceCompetencies performanceCompetencies) throws Exception;
	
	List<PerformanceCompetenciesDTO> getPerformanceCompetenciesListByOrgLocationId(Long orgLocationId) throws Exception;
	
	ResponseList<PerformanceCompetenciesDTO> getPerformanceCompetenciesList(PerformanceCompetenciesDTO performanceCompetenciesDTO,String dateFormat,Long orgLocationId) throws Exception;
	
	PerformanceCompetencies getPerformanceCompetenciesById(Long id) throws Exception;
	
	boolean isCompetenceNameExisted(Long orgLocationId, String competenceName) throws Exception;

}
