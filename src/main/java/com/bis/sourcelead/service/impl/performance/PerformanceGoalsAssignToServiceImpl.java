package com.bis.sourcelead.service.impl.performance;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.performance.PerformanceAppraisalAssignedAssociatesDao;
import com.bis.sourcelead.dao.performance.PerformanceFeedbackDao;
import com.bis.sourcelead.dao.performance.PerformanceGoalsAssignToDao;
import com.bis.sourcelead.dto.performance.PerformanceGoalsAssignToDTO;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.performance.PerformanceAppraisalAssignedAssociates;
import com.bis.sourcelead.entity.performance.PerformanceFeedback;
import com.bis.sourcelead.entity.performance.PerformanceGoalsAssignTo;
import com.bis.sourcelead.service.performance.PerformanceGoalsAssignToService;
import com.bis.sourcelead.util.SourceLeadRandomCodeHelper;

@Service
public class PerformanceGoalsAssignToServiceImpl implements PerformanceGoalsAssignToService{

	@Autowired
	PerformanceGoalsAssignToDao performanceGoalsAssignToDao;
	
	@Autowired
	PerformanceAppraisalAssignedAssociatesDao performanceAppraisalAssignedAssociatesDao;
	
	@Autowired
	PerformanceFeedbackDao performanceFeedbackDao;
	
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
	public void saveAssignedPerformanceGoalsScore(PerformanceGoalsAssignToDTO performanceGoalsAssignTo)
			throws Exception {
		
		PerformanceAppraisalAssignedAssociates assignedAssociates = performanceAppraisalAssignedAssociatesDao.getPerformanceAppraisalAssignedAssociatesByUUID(performanceGoalsAssignTo.getAssignedUUID());
		User user = new User();
		user.setId(performanceGoalsAssignTo.getUserId());
		OrgLocations orgLocations = new OrgLocations();
		orgLocations.setId(performanceGoalsAssignTo.getOrgLocationId());
		Org org = new Org();
		org.setId(performanceGoalsAssignTo.getOrgId());
		PerformanceFeedback performanceFeedback = null;
		performanceFeedback = performanceFeedbackDao.getPerformanceFeedbackByAssignedUserId(performanceGoalsAssignTo.getUserId(), assignedAssociates.getId());
		if(performanceFeedback == null) {
			performanceFeedback = new PerformanceFeedback();
			performanceFeedback.setUserId(user);
			performanceFeedback.setOrgLocationId(orgLocations);
			performanceFeedback.setOrgId(org);
			performanceFeedback.setCreatedBy(user);
			performanceFeedback.setCreatedDate(new Date());
			performanceFeedback.setAppraisalAssignedId(assignedAssociates);
			performanceFeedback.setUuid(SourceLeadRandomCodeHelper.generateRandomUUID());
			performanceFeedback.setStatus(Constants.RECORD_ACTIVE);
			performanceFeedback.setSubmissionStage("Draft");
		}else {
			performanceFeedback.setUpdatedBy(user);
			performanceFeedback.setUpdatedDate(new Date());
		}
		performanceFeedback.setGoalsScore(performanceGoalsAssignTo.getGoalsScore());
		performanceFeedbackDao.savePerformanceFeedback(performanceFeedback);
	}

}
