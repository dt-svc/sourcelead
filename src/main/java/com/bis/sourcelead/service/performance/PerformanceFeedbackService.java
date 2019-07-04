package com.bis.sourcelead.service.performance;

import com.bis.sourcelead.dto.performance.PerformanceFeedbackDTO;

public interface PerformanceFeedbackService {

	void saveAssignedPerformanceFeedback(PerformanceFeedbackDTO performanceFeedbackDTO) throws Exception;
}
