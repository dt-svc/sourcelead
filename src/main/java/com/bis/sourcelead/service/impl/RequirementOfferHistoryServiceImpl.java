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
				RequirementOffers offers = requirementOffersDao.getRequirementOfferId(requirementOfferHistory.getId());
				if(offers.getDateOfJoining() != null){
					requirementOfferHistory.setDateOfJoiningStr(Constants.DF_DMY.format((offers.getDateOfJoining())));
				}
				RequirementSubmission sourcesubmission = requirementSubmissionService.getSubmissionsBySourceSubmissionId(offers.getSubmissionId().getId(),orgLocationsId);
				if(sourcesubmission != null && sourcesubmission.getVendorLocationId() !=null){
					requirementOfferHistory.setClientName(sourcesubmission.getVendorLocationId().getOrg().getOrgName());
				}
				
			}
		}
		
		
		return offersList;
	}
	
}
