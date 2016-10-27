package com.bis.sourcelead.dao;

import java.util.List;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.RequirementSubmission;
import com.bis.sourcelead.entity.User;

/**
 * 
 * @author Laxman
 * @date 20th October 2016
 *
 */
public interface RequirementSubmissionDao {
	
	RequirementSubmission getRequirementSubmissionById(Long submissionId)throws Exception;
	
	List<RequirementSubmission> getAllShortListCandidatesByOrgLocationsIdAndRequirementId(Long orgLocationsId, Long requirementId, List<Long> existingApplicantsIds) throws Exception;
	
	List<RequirementSubmission>  getAllSubmittedAndShortListAndInterviewededCandidates(Long orgLocationsId, Long requirementId, List<Long> existingApplicantsIds) throws Exception;
	
	RequirementSubmission storeRequirementSubmission(RequirementSubmission requirementSubmission)throws Exception;

	List<RequirementSubmission> getSubmissionsByRequirementId(Long requirementId, User user, OrgLocations loginOrgLocation) throws Exception;

	List<RequirementSubmission> getRequirementPerCandidate(Long candudateId)throws Exception;
	
	List<RequirementSubmission> getAllSubmissionListByRequirementIdAndSubmissionStage(Long orgLocationsId, Long requirementId,String submissionStatus) throws Exception;
	
	Integer getRequirementSubmissionsCountByOrgLocationsIdAndSubmissionStatus(Long orgLocationsId,String submissionStatus);

	RequirementSubmission getSubmissionsBySourceSubmissionId(Long sourceSubmissionId, Long orgLocationsId);
	
}
