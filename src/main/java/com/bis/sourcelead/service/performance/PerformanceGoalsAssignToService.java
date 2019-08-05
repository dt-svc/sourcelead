package com.bis.sourcelead.service.performance;

import java.util.List;

import com.bis.sourcelead.entity.performance.PerformanceGoalsAssignTo;

public interface PerformanceGoalsAssignToService {

	PerformanceGoalsAssignTo savePerformanceGoalsAssignTo(PerformanceGoalsAssignTo performanceGoalsAssignTo) throws Exception;
	
	PerformanceGoalsAssignTo getPerformanceGoalsAssignToByAssignedUserIdAndOrgLocationId(Long assignedUserId,Long goalId) throws Exception;
	
	List<PerformanceGoalsAssignTo> getPerformanceGoalsAssignToListByGoalId(Long goalId) throws Exception;
	
}
