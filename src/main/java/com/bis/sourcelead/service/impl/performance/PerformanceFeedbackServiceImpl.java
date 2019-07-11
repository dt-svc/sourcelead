package com.bis.sourcelead.service.impl.performance;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.performance.PerformanceAppraisalAssignedAssociatesDao;
import com.bis.sourcelead.dao.performance.PerformanceFeedbackDao;
import com.bis.sourcelead.dto.performance.PerformanceFeedbackDTO;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.performance.PerformanceAppraisalAssignedAssociates;
import com.bis.sourcelead.entity.performance.PerformanceFeedback;
import com.bis.sourcelead.service.performance.PerformanceFeedbackService;
import com.bis.sourcelead.util.SourceLeadRandomCodeHelper;

@Service
public class PerformanceFeedbackServiceImpl implements PerformanceFeedbackService{
	@Autowired
	PerformanceAppraisalAssignedAssociatesDao performanceAppraisalAssignedAssociatesDao;
	
	@Autowired
	PerformanceFeedbackDao performanceFeedbackDao;

	@Override
	public void saveAssignedPerformanceFeedback(PerformanceFeedbackDTO performanceFeedbackDTO) throws Exception {
		

		
		PerformanceAppraisalAssignedAssociates assignedAssociates = performanceAppraisalAssignedAssociatesDao.getPerformanceAppraisalAssignedAssociatesByUUID(performanceFeedbackDTO.getAssignedUUID());
		User user = new User();
		user.setId(performanceFeedbackDTO.getUserId());
		OrgLocations orgLocations = new OrgLocations();
		orgLocations.setId(performanceFeedbackDTO.getOrgLocationId());
		Org org = new Org();
		org.setId(performanceFeedbackDTO.getOrgId());
		PerformanceFeedback performanceFeedback = null;
		Integer goalsScore = 0;
		Integer tasksScore = 0;
		Integer competenciesScore = 0;
		Integer categoryScore = 0;
		performanceFeedback = performanceFeedbackDao.getPerformanceFeedbackByAssignedUserId(performanceFeedbackDTO.getUserId(), assignedAssociates.getId());
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
			
		}else {
			performanceFeedback.setUpdatedBy(user);
			performanceFeedback.setUpdatedDate(new Date());
			if(performanceFeedback.getFeedbackCategoriesScore() != null) {
				categoryScore = performanceFeedback.getFeedbackCategoriesScore();
			}
			if(performanceFeedback.getCompetenciesScore() != null) {
				competenciesScore = performanceFeedback.getCompetenciesScore();
			}
			
			if(performanceFeedback.getGoalsScore() != null) {
				goalsScore = performanceFeedback.getGoalsScore();
			}
			
			if(performanceFeedback.getTasksScore() != null) {
				tasksScore = performanceFeedback.getTasksScore();
			}
			
		}
		
		if(performanceFeedbackDTO.getGoalsScore() != null) {
			performanceFeedback.setGoalsScore(performanceFeedbackDTO.getGoalsScore());
			goalsScore = performanceFeedbackDTO.getGoalsScore();
		}
		
		if(performanceFeedbackDTO.getTasksScore() != null) {
			performanceFeedback.setTasksScore(performanceFeedbackDTO.getTasksScore());
			tasksScore = performanceFeedbackDTO.getTasksScore();
		}
		
		Integer finalScore = categoryScore+goalsScore+tasksScore+competenciesScore;
		performanceFeedback.setFinalScore(finalScore);
		
		if(StringUtils.isNotBlank(performanceFeedbackDTO.getSubmissionStage())) {
			performanceFeedback.setSubmissionStage(performanceFeedbackDTO.getSubmissionStage());
		}else {
			performanceFeedback.setSubmissionStage("Draft");
		}
		
		if(StringUtils.isNotBlank(performanceFeedbackDTO.getComments())) {
			performanceFeedback.setComments(performanceFeedbackDTO.getComments());
		}
		
		performanceFeedbackDao.savePerformanceFeedback(performanceFeedback);
	
		
	}

	@Override
	public PerformanceFeedback getPerformanceFeedbackByAssignedUserId(Long userId, Long appraisalAssignedId)
			throws Exception {
		return performanceFeedbackDao.getPerformanceFeedbackByAssignedUserId(userId, appraisalAssignedId);
	}

}
