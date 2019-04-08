package com.bis.sourcelead.dao.performance;

import java.util.List;

import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.dto.performance.PerformanceGoalsAssignToDTO;
import com.bis.sourcelead.dto.performance.PerformanceGoalsDTO;
import com.bis.sourcelead.dto.performance.PerformanceGoalsDoNutChartCountsDTO;
import com.bis.sourcelead.entity.performance.PerformanceGoals;

public interface PerformanceGoalsDao {
	
	PerformanceGoals savePerformanceGoals(PerformanceGoals performanceGoals) throws Exception;
	
	PerformanceGoals getPerformanceGoalsById(Long goalId) throws Exception;
	
	PerformanceGoalsDoNutChartCountsDTO getTotalGoalsDoNutChartCounts(Long userId,Long orgLocationId) throws Exception;
	
	PerformanceGoalsDoNutChartCountsDTO getInprogressGoalsDoNutChartCounts(Long userId,Long orgLocationId) throws Exception;
	
	PerformanceGoalsDoNutChartCountsDTO getCompletedGoalsDoNutChartCounts(Long userId, Long orgLocationId) throws Exception;
	
	PerformanceGoalsDoNutChartCountsDTO getOverDueGoalsDoNutChartCounts(Long userId,Long orgLocationId) throws Exception;
	
	PerformanceGoalsDoNutChartCountsDTO getNotStartedGoalsDoNutChartCounts(Long userId,Long orgLocationId) throws Exception;
	
	ResponseList<PerformanceGoalsDTO> getPerformanceGoalsList(PerformanceGoalsDTO performanceGoalsDTO,Long userId,Long orgLocationId,String dateFormat) throws Exception;
	
	PerformanceGoals getPerformanceGoalsByUUID(String goalUUID) throws Exception;
	
	List<PerformanceGoalsDTO> getPerformanceSubGoalsList(Long goalId,String dateFormat) throws Exception;
	
	List<PerformanceGoalsAssignToDTO> getPerformanceGoalsAssignedUsersList(String goalUUID,String dateFormat) throws Exception;
	
}
