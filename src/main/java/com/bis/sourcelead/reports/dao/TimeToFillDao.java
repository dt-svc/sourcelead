package com.bis.sourcelead.reports.dao;

import java.util.Date;
import java.util.List;

import com.bis.sourcelead.entity.RequirementSubmissionHistory;

public interface TimeToFillDao {

	List<RequirementSubmissionHistory> getRequirementHistoryByJobIdsAndStage(List<Long> jobIdList,Date[] searchDates,String stage)throws Exception;
	
	List<RequirementSubmissionHistory> getRequirementHistoryByJobIdsAndStageForTimeToFill(List<Long> jobIdList,Date[] searchDates)throws Exception;
	
	RequirementSubmissionHistory getRequirementHistoryBySubmissionIdsAndStage(Long submissionId,String stage)throws Exception;
}
