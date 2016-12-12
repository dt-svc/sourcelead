package com.bis.sourcelead.dao;

import java.util.List;

import com.bis.sourcelead.entity.RequirementInterviewHistory;

public interface RequirementInterviewHistoryDao {
	
	RequirementInterviewHistory storeRequirementInterviewHistory(RequirementInterviewHistory requirementInterviewHistory)throws Exception;

	List<RequirementInterviewHistory> getRequirementInternalAndIsClientInterviewsBySubmissionIdAndOrgLocationsId(
			Long submissionId, Long orgLocationsId) throws Exception;

	RequirementInterviewHistory getRequirementInterviewHistoryById(Long id);
	
}
