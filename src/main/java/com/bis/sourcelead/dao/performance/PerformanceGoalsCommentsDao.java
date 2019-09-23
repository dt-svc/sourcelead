package com.bis.sourcelead.dao.performance;

import java.util.List;

import com.bis.sourcelead.dto.performance.PerformanceGoalsCommentsDTO;
import com.bis.sourcelead.entity.performance.PerformanceGoalsComments;

public interface PerformanceGoalsCommentsDao {
	
	PerformanceGoalsComments savePerformanceGoalsComments(PerformanceGoalsComments performanceGoalsComments) throws Exception;
	
	List<PerformanceGoalsCommentsDTO> getPerformanceGoalsCommentsList(Long goalId, String dateFormat) throws Exception;

}
