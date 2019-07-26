package com.bis.sourcelead.service.performance;

import com.bis.sourcelead.dto.performance.PerformanceFeedbackCategoryDTO;

public interface PerformanceAppraisalUserFeedbackService {

	void storePerformanceAppraisalUserFeedback(PerformanceFeedbackCategoryDTO performanceFeedbackCategoryDTO) throws Exception;
}
