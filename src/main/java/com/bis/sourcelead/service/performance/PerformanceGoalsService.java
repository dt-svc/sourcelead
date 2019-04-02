package com.bis.sourcelead.service.performance;

import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.dto.performance.PerformanceGoalsDTO;
import com.bis.sourcelead.dto.performance.PerformanceGoalsDoNutChartCountsDTO;
import com.bis.sourcelead.entity.performance.PerformanceGoals;
import com.bis.sourcelead.entity.performance.PerformanceGoalsAssignTo;

public interface PerformanceGoalsService {

	PerformanceGoals storePerformanceGoals(PerformanceGoalsDTO performanceGoalsDTO,Long userId,Long orgLocationId,String dateFormat) throws Exception;
	
	PerformanceGoals getPerformanceGoalsById(Long goalId) throws Exception;
	
	PerformanceGoalsDoNutChartCountsDTO getTotalGoalsDoNutChartCounts(Long userId,Long orgLocationId) throws Exception;
	
	PerformanceGoalsDoNutChartCountsDTO getInprogressGoalsDoNutChartCounts(Long userId,Long orgLocationId) throws Exception;
	
	PerformanceGoalsDoNutChartCountsDTO getCompletedGoalsDoNutChartCounts(Long userId,Long orgLocationId) throws Exception;
	
	PerformanceGoalsDoNutChartCountsDTO getOverDueGoalsDoNutChartCounts(Long userId,Long orgLocationId) throws Exception;
	
	PerformanceGoalsDoNutChartCountsDTO getNotStartedGoalsDoNutChartCounts(Long userId,Long orgLocationId) throws Exception;
	
	ResponseList<PerformanceGoalsDTO> getPerformanceGoalsList(PerformanceGoalsDTO performanceGoalsDTO,Long userId,Long orgLocationId,String dateFormat) throws Exception;
	
	PerformanceGoals getPerformanceGoalsByUUID(String goalUUID) throws Exception;
	
}
