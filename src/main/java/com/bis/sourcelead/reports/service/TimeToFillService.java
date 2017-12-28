package com.bis.sourcelead.reports.service;

import java.util.Date;
import java.util.List;

import com.bis.sourcelead.entity.RequirementSubmissionHistory;

public interface TimeToFillService {

	List<RequirementSubmissionHistory> getRequirementHistoryByJobIdsAndStage(List<Long> jobIdList,Date[] searchDates,String stage)throws Exception;
	
	RequirementSubmissionHistory getRequirementHistoryBySubmissionIdsAndStage(Long submissionId,String stage)throws Exception;
}
