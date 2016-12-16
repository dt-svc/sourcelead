package com.bis.sourcelead.service;

import java.util.List;

import com.bis.sourcelead.entity.RequirementRateSubmissionNegotiation;

public interface RequirementRateSubmissionNegotiationService {

	/*public void saveRateNegotiation(RequirementRateSubmissionNegotiation rateNegotiation);*/

	public RequirementRateSubmissionNegotiation getRequirementRateSubmissionById(Long ratesubmissionId);

	public List<RequirementRateSubmissionNegotiation> getRequirementRateSubmissionByOrgLocationsId(Long orgLocationsId)
			throws Exception;
	
	
	public void saveRateNegotiation(RequirementRateSubmissionNegotiation rateNegotiation);
	
	public RequirementRateSubmissionNegotiation getLatestRequirementRateSubmissionNegotiationBySubmissionId(Long submissionId);
	
	public List<RequirementRateSubmissionNegotiation> getAllRateHistoryBySubmissionIdAndCandidateId(Long submissionId, Long candidateId) throws Exception;

}
