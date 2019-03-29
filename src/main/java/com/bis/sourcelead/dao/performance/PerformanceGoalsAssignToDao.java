package com.bis.sourcelead.dao.performance;

import com.bis.sourcelead.entity.performance.PerformanceGoalsAssignTo;

public interface PerformanceGoalsAssignToDao {

	PerformanceGoalsAssignTo savePerformanceGoalsAssignTo(PerformanceGoalsAssignTo performanceGoalsAssignTo) throws Exception;
	
	PerformanceGoalsAssignTo getPerformanceGoalsAssignToByAssignedUserIdAndOrgLocationId(Long assignedUserId,Long goalId) throws Exception;
}
