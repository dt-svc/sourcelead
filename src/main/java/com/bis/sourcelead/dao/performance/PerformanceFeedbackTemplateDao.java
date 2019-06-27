package com.bis.sourcelead.dao.performance;

import java.util.List;

import com.bis.sourcelead.dto.PerformanceFeedbackTemplateDTO;
import com.bis.sourcelead.entity.performance.PerformanceFeedbackTemplate;

public interface PerformanceFeedbackTemplateDao {

	PerformanceFeedbackTemplate savePerformanceFeedbackTemplate(PerformanceFeedbackTemplate performanceFeedbackTemplate) throws Exception;
	
	PerformanceFeedbackTemplate getPerformanceFeedbackTemplateById(Long id) throws Exception;
	
	List<PerformanceFeedbackTemplate> getPerformanceFeedbackTemplateList(Long orgLocationId) throws Exception;
	
	List<PerformanceFeedbackTemplateDTO> getPerformanceFeedbackTemplateDTOList(Long orgLocationId) throws Exception;
	
	PerformanceFeedbackTemplate getPerformanceFeedbackTemplateByUUID(String uuid) throws Exception;
}
