package com.bis.sourcelead.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.RequirementOfferHistoryDao;
import com.bis.sourcelead.dao.RequirementOffersDao;
import com.bis.sourcelead.entity.RequirementInterviewHistory;
import com.bis.sourcelead.entity.RequirementOfferHistory;
import com.bis.sourcelead.entity.RequirementOffers;
import com.bis.sourcelead.entity.RequirementSubmission;
import com.bis.sourcelead.service.RequirementOfferHistoryService;
import com.bis.sourcelead.service.RequirementSubmissionService;

@Service
public class RequirementOfferHistoryServiceImpl implements RequirementOfferHistoryService{
	
	private static final Logger logger = LoggerFactory.getLogger(RequirementOfferHistoryServiceImpl.class);

    @Autowired
    RequirementOfferHistoryDao requirementOfferHistoryDao;
    
    @Autowired
	private RequirementOffersDao requirementOffersDao;
    
	@Autowired
	private RequirementSubmissionService requirementSubmissionService;

	@Override
	public RequirementOfferHistory storeRequirementOfferHistory(RequirementOfferHistory requirementOfferHistory)
			throws Exception {
		return requirementOfferHistoryDao.storeRequirementOfferHistory(requirementOfferHistory);
	}
	
	@Override
	public RequirementOfferHistory getRequirementOfferHistoryById(Long id) {
		return requirementOfferHistoryDao.getRequirementOfferHistoryById(id);
	}

	@Override
	public List<RequirementOfferHistory> getRequirementInternalAndIsClientOfferssBySubmissionIdAndOrgLocationsId(
			Long submissionId, Long orgLocationsId) throws Exception {
		
		List<RequirementOfferHistory> offersList = null;
		offersList = requirementOfferHistoryDao.getRequirementInternalAndIsClientOfferssBySubmissionIdAndOrgLocationsId(submissionId,orgLocationsId);
		if (offersList != null && !offersList.isEmpty() ) {
			for (RequirementOfferHistory requirementOfferHistory : offersList) {
				RequirementOffers offers = requirementOffersDao.getRequirementOfferId(requirementOfferHistory.getOfferId().getId());
				if(offers.getDateOfJoining() != null){
					requirementOfferHistory.setDateOfJoiningStr(Constants.DF_DMY.format((offers.getDateOfJoining())));
				}
				
				if(requirementOfferHistory.getSubmissionId().getVendorLocationId() != null){
					requirementOfferHistory.setIsNotifyToVendorRequired(Constants.YES);
				}else{
					requirementOfferHistory.setIsNotifyToVendorRequired(Constants.NO);
				}
				/*if(offers.getSubmissionId().getRequirement().getClientId() != null){
					if(offers.getSubmissionId().getRequirement().getClientId().getResponderType().equalsIgnoreCase("Client")){
						requirementOfferHistory.setClientName(offers.getSubmissionId().getRequirement().getClientId().getResponderLocationId().getOrg().getOrgName());
					}
					if(offers.getSubmissionId().getRequirement().getClientId().getResponderType().equalsIgnoreCase("Vendor")){
						requirementOfferHistory.setClientName(offers.getSubmissionId().getRequirement().getClientId().getLocationId().getOrg().getOrgName());
					}
				}else{*/
					RequirementSubmission requirementSubmission = requirementSubmissionService.getSubmissionsBySourceSubmissionId(offers.getSubmissionId().getId(),orgLocationsId);
				    if(requirementSubmission != null){
				    	requirementOfferHistory.setClientName(requirementSubmission.getOrgLocations().getOrg().getOrgName());
				    }else if(offers.getSubmissionId().getSourceSubmission() != null && offers.getSubmissionId().getSourceSubmission().getId() != null){
				    	RequirementSubmission parentRequirementSubmission = requirementSubmissionService.getSubmissionsBySourceSubmissionId(offers.getSubmissionId().getSourceSubmission().getId(),orgLocationsId);
				    	 if(parentRequirementSubmission != null){
						     requirementOfferHistory.setClientName(parentRequirementSubmission.getOrgLocations().getOrg().getOrgName());
				    	 }
				    }
				/*}*/
				
			}
		}
		
		
		return offersList;
	}

	@Override
	public List<RequirementOfferHistory> getAllRequirementOfferHistoriesByOfferId(Long offerId) {
		return requirementOfferHistoryDao.getAllRequirementOfferHistoriesByOfferId(offerId);
	}
	
}
