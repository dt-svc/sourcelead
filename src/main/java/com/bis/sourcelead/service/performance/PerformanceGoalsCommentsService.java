package com.bis.sourcelead.service.performance;

import java.util.List;

import com.bis.sourcelead.dto.performance.PerformanceGoalsCommentsDTO;
import com.bis.sourcelead.entity.performance.PerformanceGoalsComments;

public interface PerformanceGoalsCommentsService {

	PerformanceGoalsComments savePerformanceGoalsComments(PerformanceGoalsComments performanceGoalsComments) throws Exception;
	
	void storePerformanceGoalsComments(PerformanceGoalsCommentsDTO performanceGoalsCommentsDTO) throws Exception;
	
	List<PerformanceGoalsCommentsDTO> getPerformanceGoalsCommentsList(Long goalId, String dateFormat) throws Exception;
}
