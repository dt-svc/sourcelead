package com.bis.sourcelead.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.Session;

import com.bis.sourcelead.entity.RequirementOffers;
import com.bis.sourcelead.entity.RequirementSubmission;

public interface RequirementOffersDao {
	
	RequirementOffers storeRequirementOffers(RequirementOffers requirementOffers)throws Exception;
	
	RequirementOffers getRequirementOfferSubmissionById(Long submissionId)throws Exception;
	
	RequirementOffers getRequirementOfferId(Long offerId)throws Exception;

	List<RequirementOffers> getRequirementOfferSubmissionByIdAndOrgLocationId(Long submissionId, Long orgLocationId)throws Exception;
	
	List<RequirementOffers> getOfferAcceptedCandidatesByOrgLocationId(Long orgLocationId) throws Exception;
	
	RequirementOffers getRequirementOffersByUUID(String offerUUId) throws Exception;

	List<RequirementOffers> getOfferAcceptedVendorCandidatesByOrgLocationId(Long locationId);
	
	RequirementOffers getOnboardRequirementOfferId(Long offerId)throws Exception;

	List<RequirementOffers> getOfferAcceptedCandidatesByOrgLocationIdAndDateOfJoin(Long orgLocationId, Date joiningDate) throws Exception;
}
