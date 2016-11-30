package com.bis.sourcelead.service;

import java.util.List;

import org.apache.xmlbeans.impl.xb.xsdschema.Public;
import org.json.JSONObject;
import org.springframework.web.multipart.MultipartFile;

import com.bis.sourcelead.entity.Org;
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


public interface RequirementSubmissionService {
	
	RequirementSubmission getRequirementSubmissionById(Long submissionId)throws Exception;
	
	List<RequirementSubmission> getAllShortListCandidatesByOrgLocationsIdAndRequirementId(Long orgLocationsId, Long requirementId, List<Long> existingApplicantsIds) throws Exception;
	
	List<RequirementSubmission> getAllSubmittedAndShortListAndInterviewededCandidates(Long orgLocationsId, Long requirementId, List<Long> existingApplicantsIds) throws Exception;
	
	RequirementSubmission storeRequirementSubmission(RequirementSubmission requirementSubmission)throws Exception;

	List<RequirementSubmission> getSubmissionsByRequirementId(Long requirementId, User user, OrgLocations loginOrgLocation) throws Exception;

	void saveRequirementSubmission(JSONObject jsonObj,User user,OrgLocations orgLocations,String jsonStringvalues,MultipartFile multiFile) throws Exception;
	
	List<RequirementSubmission> getAllSubmissionListByRequirementIdAndSubmissionStage(Long orgLocationsId, Long requirementId,String submissionStatus) throws Exception;
	
	void storeClientSubmission(Long requirementId, User user, OrgLocations loginOrgLocation,String submissionIds,Org org) throws Exception;
	
	public Integer getRequirementSubmissionsCountByOrgLocationsIdAndSubmissionStatus(Long orgLocationsId,String submissionStatus,String dateRange);
	

	List<RequirementSubmission> getRequirementPerCandidate(Long candudateId) throws Exception;

	RequirementSubmission getRequirementSubmissionByIdAndSubmissionStatus(Long submissionId,String submissionStatus)throws Exception;
	
	
	RequirementSubmission getVendorSubmissionByClientSubmissionIdAndCLientLocationId(Long sourceSubmissionId, Long vendorLocationID);
	
	
	RequirementSubmission getSubmissionsBySourceSubmissionId(Long sourceSubmissionId, Long orgLocationsId);
	
	RequirementSubmission getSubmissionsByVendorSubmissionIdAndvendorOrgLocationId(Long sourceSubmissionId, Long vendorLocationID);
	
	Integer getAllSubmissionCountByRequirementIdAndSubmissionStage(Long orgLocationsId, Long requirementId, String submissionStatus);
	
	public Integer getApplicantCountByrequirementIdandAssignedUsercode(Long requirementId, String userCode) throws Exception;

	List<RequirementSubmission> getSubmissionsByRequirementIdandStatus(Long valueOf, User user,
			OrgLocations loginOrgLocation, String submissionType)throws Exception;

}
