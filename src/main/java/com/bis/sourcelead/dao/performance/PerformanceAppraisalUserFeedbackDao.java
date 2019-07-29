package com.bis.sourcelead.dao.performance;

import com.bis.sourcelead.entity.performance.PerformanceAppraisalUserFeedback;

public interface PerformanceAppraisalUserFeedbackDao {
	
	void savePerformanceAppraisalUserFeedback(PerformanceAppraisalUserFeedback performanceAppraisalUserFeedback) throws Exception;
	
	PerformanceAppraisalUserFeedback getPerformanceAppraisalUserFeedbackAssignedUserId(Long userId,Long appraisalAssignedId,Long feedbackTemplateId) throws Exception;
	
	Integer getFeedbackCategoriesScore(Long userId,Long appraisalAssignedId) throws Exception;

}
