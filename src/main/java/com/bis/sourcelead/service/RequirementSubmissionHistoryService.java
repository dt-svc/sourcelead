package com.bis.sourcelead.service;

import java.util.List;

import com.bis.sourcelead.entity.RequirementSubmission;
import com.bis.sourcelead.entity.RequirementSubmissionHistory;
import com.bis.sourcelead.entity.User;

public interface RequirementSubmissionHistoryService {

	/**
	 * updating the Requirement Submission Hostory
	 * @param requirementSubmission
	 * @param user
	 * @param requirementSubmissionHistory
	 * @return
	 * @throws Exception
	 */
	RequirementSubmissionHistory updateRequirementHistory(RequirementSubmission requirementSubmission, User user, RequirementSubmissionHistory requirementSubmissionHistory)throws Exception;

	List<RequirementSubmissionHistory> getRequirementHistoryByLocationIdAndStage(Long orgLocationID,String submissionType, Long submissionId)throws Exception;
	
	List<RequirementSubmissionHistory> getRequirementHistoryListByCandidateIdAndOrgLocationIdAndRequirementIdAndSubmissionId(Long candidateId, Long orgLocationId, Long requirementId,Long submissionId)throws Exception; 

	RequirementSubmissionHistory getRequirementSubmissionHistoryBySubmissionId(Long submissionId);

	List<RequirementSubmissionHistory> getAllRequirementSubmissionHistoryBySubmissionId(List<Long> submissionIds)throws Exception;

}
