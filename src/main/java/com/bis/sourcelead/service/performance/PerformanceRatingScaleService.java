package com.bis.sourcelead.service.performance;

import com.bis.sourcelead.dto.performance.PerformanceRatingScaleDTO;
import com.bis.sourcelead.entity.performance.PerformanceRatingScale;

public interface PerformanceRatingScaleService {

	PerformanceRatingScale storePerformanceRatingScale(PerformanceRatingScaleDTO performanceRatingScaleDTO,Long orgLocationId,Long userId) throws Exception;
}
