package com.bis.sourcelead.service.impl.performance;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.performance.PerformanceAppraisalAssignedAssociatesDao;
import com.bis.sourcelead.dao.performance.PerformanceGoalsAssignToDao;
import com.bis.sourcelead.entity.performance.PerformanceGoalsAssignTo;
import com.bis.sourcelead.service.performance.PerformanceGoalsAssignToService;

@Service
public class PerformanceGoalsAssignToServiceImpl implements PerformanceGoalsAssignToService{

	@Autowired
	PerformanceGoalsAssignToDao performanceGoalsAssignToDao;
	
	@Autowired
	PerformanceAppraisalAssignedAssociatesDao performanceAppraisalAssignedAssociatesDao;
	
	@Override
	public PerformanceGoalsAssignTo savePerformanceGoalsAssignTo(PerformanceGoalsAssignTo performanceGoalsAssignTo)
			throws Exception {
		return performanceGoalsAssignToDao.savePerformanceGoalsAssignTo(performanceGoalsAssignTo);
	}

	@Override
	public PerformanceGoalsAssignTo getPerformanceGoalsAssignToByAssignedUserIdAndOrgLocationId(Long assignedUserId,
			Long goalId) throws Exception {
		return performanceGoalsAssignToDao.getPerformanceGoalsAssignToByAssignedUserIdAndOrgLocationId(assignedUserId, goalId);
	}

	@Override
	public List<PerformanceGoalsAssignTo> getPerformanceGoalsAssignToListByGoalId(Long goalId) throws Exception {
		return performanceGoalsAssignToDao.getPerformanceGoalsAssignToListByGoalId(goalId);
	}


}
