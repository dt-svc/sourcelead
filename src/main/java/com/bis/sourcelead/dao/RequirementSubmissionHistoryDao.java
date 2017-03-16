package com.bis.sourcelead.dao;

import java.text.ParseException;
import java.util.List;

import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.RequirementSubmission;
import com.bis.sourcelead.entity.RequirementSubmissionHistory;
import com.bis.sourcelead.service.RequirementSubmissionHistoryService;

public interface RequirementSubmissionHistoryDao {

	RequirementSubmissionHistory updateRequirementHistory(RequirementSubmissionHistory requirementSubmissionHistory)throws Exception;

	List<RequirementSubmissionHistory> getRequirementHistoryByLocationIdAndStage(Long orgLocationID,String submissionType, Long submissionId) throws Exception;

	RequirementSubmissionHistory getRequirementSubmissionHistoryBySubmissionId(Long submissionId);
	
	Boolean  isInCoolingOffPeriodByEmailAndLocationsId(String email, Long orgLocationId, String reqCoolingOffPeriod, Long requirementId) throws ParseException;
	
	List<RequirementSubmissionHistory> getRequirementHistoryListByCandidateIdAndOrgLocationIdAndRequirementIdAndSubmissionId(Long candidateId, Long orgLocationId, Long requirementId,Long submissionId)throws Exception;

}
