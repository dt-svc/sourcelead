package com.bis.sourcelead.service.performance;

import java.util.List;

import com.bis.sourcelead.dto.performance.PerformanceCompetenciesDTO;
import com.bis.sourcelead.dto.performance.PerformanceRatingLevelDTO;
import com.bis.sourcelead.entity.performance.PerformanceRatingLevel;

public interface PerformanceRatingLevelService {

	PerformanceRatingLevel storePerformanceRatingLevel(PerformanceRatingLevelDTO performanceRatingLevelDTO,Long orgLocationId,Long userId) throws Exception;

	List<PerformanceRatingLevelDTO> getPerformanceRatingLevelsListByOrgLocationId(Long orgLocationId,String dateFormat) throws Exception;
	
	boolean isRatingLevelNameExisted(Long orgLocationId, String ratingLevelName) throws Exception;
	
	PerformanceRatingLevelDTO getPerformanceRatingLevelDetailsById(Long ratingLevelId) throws Exception;
	
	PerformanceRatingLevel getPerformanceRatingLevelById(Long ratingLevelId) throws Exception;
	
	PerformanceRatingLevel savePerformanceRatingLevel(PerformanceRatingLevel PerformanceRatingLevel) throws Exception;
}
