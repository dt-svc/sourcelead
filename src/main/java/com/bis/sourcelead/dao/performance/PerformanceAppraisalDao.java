package com.bis.sourcelead.dao.performance;

import java.util.List;

import com.bis.sourcelead.dto.PerformanceFeedbackTemplateDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.dto.performance.PerformanceAppraisalDetailsDTO;
import com.bis.sourcelead.dto.performance.PerformanceAppraisalTemplateDTO;

public interface PerformanceAppraisalDao {

	ResponseList<PerformanceAppraisalTemplateDTO> getAssignedMyAppraisalList(PerformanceAppraisalTemplateDTO performanceAppraisalTemplateDTO,Long userId,Long orgLocationId,String dateFormat) throws Exception;
	
	ResponseList<PerformanceAppraisalTemplateDTO> getAssignedPeersAppraisalList(PerformanceAppraisalTemplateDTO performanceAppraisalTemplateDTO,Long userId,Long orgLocationId,String dateFormat) throws Exception;
	
	PerformanceAppraisalDetailsDTO getSelfAppraisalTemplateDetails(String assignedUUID, String dateFormat) throws Exception;
	
	List<PerformanceFeedbackTemplateDTO> getPerformanceAssignedFeedbackTemplateDTOList(String assignedUUID) throws Exception;
	
	PerformanceFeedbackTemplateDTO getPerformanceFeedbackTemplateDTOByUUID(String uuid) throws Exception;
}
