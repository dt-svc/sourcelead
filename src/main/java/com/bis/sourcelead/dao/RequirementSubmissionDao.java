package com.bis.sourcelead.dao;

import java.util.List;

import org.apache.xmlbeans.impl.xb.xsdschema.Public;

import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.Requirement;
import com.bis.sourcelead.entity.RequirementRateSubmissionNegotiation;
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
	
	List<RequirementSubmission> getAllShortListCandidatesByOrgLocationsIdAndRequirementId(Long orgLocationsId, Long requirementId, List<Long> existingApplicantsIds, String isClientInterview) throws Exception;
	
	List<RequirementSubmission>  getAllSubmittedAndShortListAndInterviewededCandidates(Long orgLocationsId, Long requirementId, List<Long> existingApplicantsIds, String isClientOffer) throws Exception;
	
	RequirementSubmission storeRequirementSubmission(RequirementSubmission requirementSubmission)throws Exception;

	List<RequirementSubmission> getSubmissionsByRequirementId(Long requirementId, User user, OrgLocations loginOrgLocation) throws Exception;

	List<RequirementSubmission> getRequirementPerCandidate(Long candudateId)throws Exception;
	
	List<RequirementSubmission> getAllSubmissionListByRequirementIdAndSubmissionStage(Long orgLocationsId, Long requirementId,String submissionStatus) throws Exception;

	Integer getRequirementSubmissionsCountByOrgLocationsIdAndSubmissionStatus(Long orgLocationsId,String submissionStatus, String dateRange,String userType,User user);
	
	Integer getRequirementSubmissionsCountByOrgLocationsIdAndRequirementId(Long orgLocationsId,Long requirementId);
	
	List<RequirementSubmission> getAllShortLitstedCandidatesForClientSubmissionByRequirementId(Long id,Long requirementId) throws Exception;
	
	RequirementSubmission getRequirementSubmissionByIdAndSubmissionStatus(Long submissionId,String submissionStatus)throws Exception;
	
	List<RequirementSubmission>   getRequirementHistoryListByCandidateIdAndOrgLocationID(Long candidateId, Long orgLocationId) throws Exception;
	
	List<RequirementSubmission>   getRequirementHistoryListByCandidateIdAndOrgLocationIdAndRequirementId(Long candidateId, Long orgLocationId,Long requirementId) throws Exception;

	/**
	 * @author Prasad Salina
	 * @param email
	 * @param requirementId 
	 * @param orgLocationsId
	 * @return
	 */
	RequirementSubmission validateRequirementCandidateEmail(String email, Long requirementId, Long orgLocationsId) throws Exception;
	
	RequirementSubmission getSubmissionsBySourceSubmissionId(Long sourceSubmissionId, Long orgLocationsId);
	
	
	/**
	 * @author Prasad Salina
	 * @param submissionIds
	 * To get candidate resume ids of submissions
	 */
	List<Long> getResumeIdsByRequirementSubmissionId(List<Long> submissionIds) throws Exception;
	
	Integer getAllSubmissionCountByRequirementIdAndSubmissionStage(Long orgLocationsId, Long requirementId,String submissionStatus);
	
	RequirementSubmission getSubmissionsByVendorSubmissionIdAndvendorOrgLocationId(Long vendorSubmissionId, Long vendororgLocationsId);
	
	public Integer getApplicantCountByrequirementIdandAssignedUsercode(Long requirementId, String userCode) throws Exception;
 
	RequirementSubmission getVendorSubmissionByClientSubmissionIdAndCLientLocationId(Long clientSubmissionId,Long clientOrgLocationId);


	RequirementRateSubmissionNegotiation getRequirementRateBySubmissionById(Long submissionId);

	List<RequirementSubmission> getSubmissionsByRequirementIdandStatus(Long requirementId, User user,
			OrgLocations loginOrgLocation, String submissionType)throws Exception;
	
	RequirementSubmission getClientSubmissionsBySubmissionIdAndOrgLocationId(Long submissionId, Long orgLocationId);

	List<RequirementSubmission> duplicateCandidateCountList(Long requirementId, User user, OrgLocations loginOrgLocation) throws Exception;

}
