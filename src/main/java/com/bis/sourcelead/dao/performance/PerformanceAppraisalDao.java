package com.bis.sourcelead.dao.performance;

import java.util.List;

import com.bis.sourcelead.dto.PerformanceFeedbackTemplateDTO;
import com.bis.sourcelead.dto.ProjectassignTasksDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.dto.performance.PerformanceAppraisalDetailsDTO;
import com.bis.sourcelead.dto.performance.PerformanceAppraisalTemplateDTO;
import com.bis.sourcelead.dto.performance.PerformanceFeedbackDTO;
import com.bis.sourcelead.dto.performance.PerformanceGeneralTasksDetailsDTO;
import com.bis.sourcelead.dto.performance.PerformanceGoalsDTO;
import com.bis.sourcelead.dto.performance.PerformanceJobsDetailsDTO;

public interface PerformanceAppraisalDao {

	ResponseList<PerformanceAppraisalTemplateDTO> getAssignedMyAppraisalList(PerformanceAppraisalTemplateDTO performanceAppraisalTemplateDTO,Long userId,Long orgLocationId,String dateFormat) throws Exception;
	
	ResponseList<PerformanceAppraisalTemplateDTO> getAssignedPeersAppraisalList(PerformanceAppraisalTemplateDTO performanceAppraisalTemplateDTO,Long userId,Long orgLocationId,String dateFormat) throws Exception;
	
	PerformanceAppraisalDetailsDTO getSelfAppraisalTemplateDetails(String assignedUUID, String dateFormat) throws Exception;
	
	List<PerformanceFeedbackTemplateDTO> getPerformanceAssignedFeedbackTemplateDTOList(String assignedUUID,String templateIds) throws Exception;
	
	PerformanceFeedbackTemplateDTO getPerformanceFeedbackTemplateDTOByUUID(String uuid) throws Exception;
	
	PerformanceFeedbackTemplateDTO getPerformanceSubmittedFeedbackTemplateDTOByUUID(String templateUUID,String assignedUUID,Long userId) throws Exception;
	
	PerformanceGoalsDTO getperformanceGoalsDetails(String assignedUUID,Long userId) throws Exception;
	
	List<ProjectassignTasksDTO> getPerformanceAssignedProjectAssignedTasksDTOList(String assignedUUID,String dateFormat) throws Exception;
	
	List<PerformanceJobsDetailsDTO> getPerformanceJobsDetails(String assignedUUID,String dateFormat) throws Exception;
	
	PerformanceGeneralTasksDetailsDTO getGeneralTasksDetails(String assignedUUID,String dateFormat) throws Exception;
	
	PerformanceFeedbackDTO getPerformanceFeedbackDetails(String assignedUUID,Long userId) throws Exception;
	
	String getJsonCategoriesString(String assignedUUID) throws Exception;
}
