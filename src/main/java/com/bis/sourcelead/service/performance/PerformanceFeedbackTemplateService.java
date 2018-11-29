package com.bis.sourcelead.service.performance;

import com.bis.sourcelead.entity.performance.PerformanceFeedbackTemplate;

public interface PerformanceFeedbackTemplateService {

	PerformanceFeedbackTemplate savePerformanceFeedbackTemplate(PerformanceFeedbackTemplate performanceFeedbackTemplate) throws Exception;
	
	PerformanceFeedbackTemplate getPerformanceFeedbackTemplateById(Long id) throws Exception;
}
