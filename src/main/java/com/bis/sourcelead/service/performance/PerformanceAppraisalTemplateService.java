package com.bis.sourcelead.service.performance;

import java.util.List;

import com.bis.sourcelead.dto.performance.PerformanceAppraisalTemplateDTO;
import com.bis.sourcelead.entity.performance.PerformanceAppraisalTemplate;

public interface PerformanceAppraisalTemplateService {

	PerformanceAppraisalTemplate savePerformanceAppraisalTemplate(PerformanceAppraisalTemplate performanceAppraisalTemplate) throws Exception;
	
	PerformanceAppraisalTemplate storePerformanceAppraisalTemplate(PerformanceAppraisalTemplateDTO performanceAppraisalTemplateDTO, Long orgLocationId, Long userId, String dateFormat) throws Exception;
	
	List<PerformanceAppraisalTemplate> getPerformanceAppraisalTemplateList(Long orgLocationId) throws Exception;
	
	PerformanceAppraisalTemplateDTO getPerformanceAppraisalTemplateDTOById(Long id) throws Exception;
	
	PerformanceAppraisalTemplate getPerformanceAppraisalTemplateById(Long id) throws Exception;
}
