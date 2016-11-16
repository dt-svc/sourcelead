package com.bis.sourcelead.dao;

import java.util.List;

import org.apache.xmlbeans.impl.xb.xsdschema.Public;

import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.Requirement;
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

	Integer getRequirementSubmissionsCountByOrgLocationsIdAndSubmissionStatus(Long orgLocationsId,String submissionStatus, String dateRange);
	
	RequirementSubmission getRequirementSubmissionByIdAndSubmissionStatus(Long submissionId,String submissionStatus)throws Exception;

	RequirementSubmission getSubmissionsBySourceSubmissionId(Long sourceSubmissionId, Long orgLocationsId);
	
	Integer getAllSubmissionCountByRequirementIdAndSubmissionStage(Long orgLocationsId, Long requirementId,String submissionStatus);
	
	RequirementSubmission getSubmissionsByVendorSubmissionIdAndvendorOrgLocationId(Long vendorSubmissionId, Long vendororgLocationsId);
	
	public Integer getApplicantCountByrequirementIdandAssignedUsercode(Long requirementId, String userCode) throws Exception;
 
	
}
