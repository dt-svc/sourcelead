package com.bis.sourcelead.dao.performance;

import java.util.List;

import com.bis.sourcelead.dto.performance.PerformanceRatingLevelDTO;
import com.bis.sourcelead.entity.performance.PerformanceRatingLevel;

public interface PerformanceRatingLevelDao {
	

	PerformanceRatingLevel savePerformanceRatingLevel(PerformanceRatingLevel PerformanceRatingLevel) throws Exception;
	
	List<PerformanceRatingLevelDTO> getPerformanceRatingLevelsListByOrgLocationId(Long orgLocationId,String dateFormat) throws Exception;
	
	boolean isRatingLevelNameExisted(Long orgLocationId, String ratingLevelName) throws Exception;
	
	PerformanceRatingLevelDTO getPerformanceRatingLevelDetailsById(Long ratingLevelId) throws Exception;
	
	PerformanceRatingLevel getPerformanceRatingLevelById(Long ratingLevelId) throws Exception;
}
