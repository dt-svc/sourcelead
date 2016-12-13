package com.bis.sourcelead.service;

import java.util.List;

import com.bis.sourcelead.entity.RequirementInterviewHistory;

public interface RequirementInterviewHistoryService {

	
	RequirementInterviewHistory storeRequirementInterviewHistory(RequirementInterviewHistory requirementInterviewHistory)throws Exception;
	
	RequirementInterviewHistory getRequirementInterviewHistoryById(Long Id);
	
	List<RequirementInterviewHistory> getRequirementInternalAndIsClientInterviewsBySubmissionIdAndOrgLocationsId(Long submissionId, Long orgLocationsId) throws Exception;

}
