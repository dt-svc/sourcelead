package com.bis.sourcelead.service;

import java.util.List;

import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.Requirement;
import com.bis.sourcelead.entity.RequirementRateSubmissionNegotiation;

public interface RequirementRateSubmissionNegotiationService {

	/*public void saveRateNegotiation(RequirementRateSubmissionNegotiation rateNegotiation);*/

	public RequirementRateSubmissionNegotiation getRequirementRateSubmissionById(Long ratesubmissionId);

	public List<RequirementRateSubmissionNegotiation> getRequirementRateSubmissionByOrgLocationsId(Long orgLocationsId)
			throws Exception;
	
	
	public void saveRateNegotiation(RequirementRateSubmissionNegotiation rateNegotiation);
	
	public RequirementRateSubmissionNegotiation getLatestRequirementRateSubmissionNegotiationBySubmissionId(Long submissionId);
	
	public List<RequirementRateSubmissionNegotiation> getAllRateHistoryBySubmissionIdAndCandidateId(Long submissionId, Long candidateId) throws Exception;
	
	public StringBuilder buildNegotiationHistory(List<RequirementRateSubmissionNegotiation>  negotiationList, Requirement requirement, Long submissionId, String type) throws Exception;

	public RequirementRateSubmissionNegotiation getRequirementRateSubmissionByMaxId(Long submissionId);
}
