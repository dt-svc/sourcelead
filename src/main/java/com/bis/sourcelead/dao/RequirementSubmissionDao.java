package com.bis.sourcelead.dao;

import java.util.List;

import com.bis.sourcelead.entity.RequirementSubmission;

/**
 * 
 * @author Laxman
 * @date 20th October 2016
 *
 */
public interface RequirementSubmissionDao {
	
	RequirementSubmission getRequirementSubmissionById(Long submissionId)throws Exception;
	
	List<RequirementSubmission> getAllAllShortListCandidatesByOrgLocationsIdAndRequirementId(Long orgLocationsId, Long requirementId) throws Exception;
	
	RequirementSubmission storeRequirementSubmission(RequirementSubmission requirementSubmission)throws Exception;
	
}
