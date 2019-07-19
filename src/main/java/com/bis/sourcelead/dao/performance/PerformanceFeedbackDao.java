package com.bis.sourcelead.dao.performance;

import com.bis.sourcelead.entity.performance.PerformanceFeedback;

public interface PerformanceFeedbackDao {

	void savePerformanceFeedback(PerformanceFeedback performanceFeedback) throws Exception;
	
	PerformanceFeedback getPerformanceFeedbackByAssignedUserId(Long userId,Long appraisalAssignedId) throws Exception;
}
