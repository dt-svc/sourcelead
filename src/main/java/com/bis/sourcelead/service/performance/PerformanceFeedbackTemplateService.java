package com.bis.sourcelead.service.performance;

import java.util.List;

import com.bis.sourcelead.dto.PerformanceFeedbackTemplateDTO;
import com.bis.sourcelead.entity.performance.PerformanceFeedbackTemplate;

public interface PerformanceFeedbackTemplateService {

	PerformanceFeedbackTemplate savePerformanceFeedbackTemplate(PerformanceFeedbackTemplate performanceFeedbackTemplate) throws Exception;
	
	PerformanceFeedbackTemplate getPerformanceFeedbackTemplateById(Long id) throws Exception;
	
	List<PerformanceFeedbackTemplate> getPerformanceFeedbackTemplateList(Long orgLocationId) throws Exception;
	
	List<PerformanceFeedbackTemplateDTO> getPerformanceFeedbackTemplateDTOList(Long orgLocationId) throws Exception;
	
	boolean isTemplateNameExisted(Long orgLocationId, String templateName) throws Exception;
}
