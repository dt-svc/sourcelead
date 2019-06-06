package com.bis.sourcelead.dao.performance;

import java.util.List;

import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.dto.performance.PerformanceAppraisalAssignToListDTO;
import com.bis.sourcelead.entity.performance.PerformanceAppraisalTemplate;

public interface PerformanceAppraisalTemplateDao {
	
	PerformanceAppraisalTemplate savePerformanceAppraisalTemplate(PerformanceAppraisalTemplate performanceAppraisalTemplate) throws Exception;
	
	List<PerformanceAppraisalTemplate> getPerformanceAppraisalTemplateList(Long orgLocationId) throws Exception;
	
	PerformanceAppraisalTemplate getPerformanceAppraisalTemplateById(Long id) throws Exception;
	
	ResponseList<PerformanceAppraisalAssignToListDTO> getPerformanceAppraisalAssignToList(PerformanceAppraisalAssignToListDTO performanceAppraisalAssignToListDTO,Long orgLocationId) throws Exception;
	
	PerformanceAppraisalTemplate getPerformanceAppraisalTemplateByUUID(String uuid) throws Exception;

}
