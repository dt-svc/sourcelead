package com.bis.sourcelead.service.performance;

import com.bis.sourcelead.entity.performance.PerformanceGoalsAssignTo;

public interface PerformanceGoalsAssignToService {

	PerformanceGoalsAssignTo savePerformanceGoalsAssignTo(PerformanceGoalsAssignTo performanceGoalsAssignTo) throws Exception;
	
	PerformanceGoalsAssignTo getPerformanceGoalsAssignToByAssignedUserIdAndOrgLocationId(Long assignedUserId,Long goalId) throws Exception;
	
}
