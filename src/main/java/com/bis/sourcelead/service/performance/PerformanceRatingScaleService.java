package com.bis.sourcelead.service.performance;

import java.util.List;

import com.bis.sourcelead.dto.performance.PerformanceRatingScaleDTO;
import com.bis.sourcelead.entity.performance.PerformanceRatingScale;

public interface PerformanceRatingScaleService {

	PerformanceRatingScale storePerformanceRatingScale(PerformanceRatingScaleDTO performanceRatingScaleDTO,Long orgLocationId,Long userId) throws Exception;
	
	PerformanceRatingScale savePerformanceRatingScale(PerformanceRatingScale performanceRatingScale) throws Exception;
	
	List<PerformanceRatingScaleDTO> getPerformanceRatingScaleListByOrgLocationId(Long orgLocationId,String dateFormat) throws Exception;
	
	boolean isRatingScaleNameExisted(Long orgLocationId, String ratingScaleName) throws Exception;
	
	boolean isRatingShortCodeExisted(Long orgLocationId, String ratingShortCode) throws Exception;
	
	PerformanceRatingScale getPerformanceRatingScaleByID(Long ratingScaleId) throws Exception;
	
	PerformanceRatingScaleDTO getPerformanceRatingScaleDTOByScaleId(Long ratingScaleId) throws Exception;
}
