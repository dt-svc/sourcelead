package com.bis.sourcelead.service.performance;

import java.util.List;

import com.bis.sourcelead.dto.PerformanceFeedbackTemplateDTO;
import com.bis.sourcelead.dto.ProjectassignTasksDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.dto.performance.PerformanceAppraisalDetailsDTO;
import com.bis.sourcelead.dto.performance.PerformanceAppraisalTemplateDTO;
import com.bis.sourcelead.dto.performance.PerformanceGoalsDTO;
import com.bis.sourcelead.dto.performance.PerformanceJobsDetailsDTO;

public interface PerformanceAppraisalService {

	ResponseList<PerformanceAppraisalTemplateDTO> getAssignedMyAppraisalList(PerformanceAppraisalTemplateDTO performanceAppraisalTemplateDTO,Long userId,Long orgLocationId,String dateFormat) throws Exception;
	
	ResponseList<PerformanceAppraisalTemplateDTO> getAssignedPeersAppraisalList(PerformanceAppraisalTemplateDTO performanceAppraisalTemplateDTO,Long userId,Long orgLocationId,String dateFormat) throws Exception;
	
	PerformanceAppraisalDetailsDTO getSelfAppraisalTemplateDetails(String assignedUUID, String dateFormat) throws Exception;
	
	List<PerformanceFeedbackTemplateDTO> getPerformanceAssignedFeedbackTemplateDTOList(String assignedUUID) throws Exception;
	
	PerformanceFeedbackTemplateDTO getPerformanceFeedbackTemplateDTOByUUID(String uuid) throws Exception;
	
	PerformanceFeedbackTemplateDTO getPerformanceSubmittedFeedbackTemplateDTOByUUID(String templateUUID,String assignedUUID,Long userId) throws Exception;
	
	PerformanceGoalsDTO getperformanceGoalsDetails(String assignedUUID,Long userId) throws Exception;
	
	List<ProjectassignTasksDTO> getPerformanceAssignedProjectAssignedTasksDTOList(String assignedUUID,String dateFormat) throws Exception;
	
	List<PerformanceJobsDetailsDTO> getPerformanceJobsDetails(String assignedUUID,String dateFormat) throws Exception;
}
