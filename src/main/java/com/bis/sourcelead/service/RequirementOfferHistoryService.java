package com.bis.sourcelead.service;

import java.util.List;

import com.bis.sourcelead.entity.RequirementOfferHistory;

public interface RequirementOfferHistoryService {

	
	RequirementOfferHistory storeRequirementOfferHistory(RequirementOfferHistory requirementOfferHistory)throws Exception;

	List<RequirementOfferHistory> getRequirementInternalAndIsClientOfferssBySubmissionIdAndOrgLocationsId(
			Long submissionId, Long orgLocationsId) throws Exception;

	RequirementOfferHistory getRequirementOfferHistoryById(Long id);
	
	List<RequirementOfferHistory> getRequirementInternalAndIsClientOffersHistoryBySubmissionIdAndOrgLocationsId(Long submissionId, Long orgLocationsId) throws Exception;
	
	List<RequirementOfferHistory> getAllRequirementOfferHistoriesByOfferId(Long offerId);
	
	List<RequirementOfferHistory> getCrowdSourcingOfferDetailsBySubmissionId(Long submissionId,String locationCode) throws Exception;

}
