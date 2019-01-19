package com.bis.sourcelead.reports.service.Impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.entity.RequirementSubmissionHistory;
import com.bis.sourcelead.reports.dao.TimeToFillDao;
import com.bis.sourcelead.reports.service.TimeToFillService;

@Service
public class TimeToFillServiceImpl implements TimeToFillService{
	
	@Autowired
	TimeToFillDao timeToFillDao;

	@Override
	public List<RequirementSubmissionHistory> getRequirementHistoryByJobIdsAndStage(List<Long> jobIdList,
			Date[] searchDates, String stage) throws Exception {
		return timeToFillDao.getRequirementHistoryByJobIdsAndStage(jobIdList, searchDates, stage);
	}

	@Override
	public RequirementSubmissionHistory getRequirementHistoryBySubmissionIdsAndStage(Long submissionId, String stage)
			throws Exception {
		return timeToFillDao.getRequirementHistoryBySubmissionIdsAndStage(submissionId, stage);
	}

	@Override
	public List<RequirementSubmissionHistory> getRequirementHistoryByJobIdsAndStageForTimeToFill(List<Long> jobIdList, Date[] searchDates)
			throws Exception {
		return timeToFillDao.getRequirementHistoryByJobIdsAndStageForTimeToFill(jobIdList, searchDates);
	}

}
