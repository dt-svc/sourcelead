package com.bis.sourcelead.service.performance;

import com.bis.sourcelead.dto.performance.PerformanceFeedbackDTO;
import com.bis.sourcelead.entity.performance.PerformanceFeedback;

public interface PerformanceFeedbackService {

	void saveAssignedPerformanceFeedback(PerformanceFeedbackDTO performanceFeedbackDTO) throws Exception;
	
	PerformanceFeedback getPerformanceFeedbackByAssignedUserId(Long userId,Long appraisalAssignedId) throws Exception;
}
