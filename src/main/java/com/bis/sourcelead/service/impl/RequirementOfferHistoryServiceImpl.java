package com.bis.sourcelead.service.impl;

import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.RequirementOfferHistoryDao;
import com.bis.sourcelead.dao.RequirementOffersDao;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.RequirementOfferHistory;
import com.bis.sourcelead.entity.RequirementOffers;
import com.bis.sourcelead.entity.RequirementSubmission;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.RequirementOfferHistoryService;
import com.bis.sourcelead.service.RequirementSubmissionService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.web.LoginSessionController;

@Service
public class RequirementOfferHistoryServiceImpl implements RequirementOfferHistoryService{
	

    @Autowired
    RequirementOfferHistoryDao requirementOfferHistoryDao;
    
    @Autowired
	private RequirementOffersDao requirementOffersDao;
    
	@Autowired
	private RequirementSubmissionService requirementSubmissionService;
	
	@Autowired
	private CommonUtil commonUtil;
	
	@Autowired
	private OrgLocationService orgLocationService;

	@Override
	public RequirementOfferHistory storeRequirementOfferHistory(RequirementOfferHistory requirementOfferHistory)
			throws Exception {
		requirementOfferHistory.setUpdatedDate(commonUtil.currentDate());
		return requirementOfferHistoryDao.storeRequirementOfferHistory(requirementOfferHistory);
	}
	
	@Override
	public RequirementOfferHistory getRequirementOfferHistoryById(Long id) {
		return requirementOfferHistoryDao.getRequirementOfferHistoryById(id);
	}

	@Override
	public List<RequirementOfferHistory> getRequirementInternalAndIsClientOfferssBySubmissionIdAndOrgLocationsId(
			Long submissionId, Long orgLocationsId) throws Exception {
		OrgLocations orgLocations = orgLocationService.getOrgLocations(orgLocationsId);
		//SimpleDateFormat format = orgLocations.getLocFullFormDateFormatter();
		List<RequirementOfferHistory> offersList = null;
		offersList = requirementOfferHistoryDao.getRequirementInternalAndIsClientOfferssBySubmissionIdAndOrgLocationsId(submissionId,orgLocationsId);
		if (offersList != null && !offersList.isEmpty() ) {
			for (RequirementOfferHistory requirementOfferHistory : offersList) {
				RequirementOffers offers = requirementOffersDao.getRequirementOfferId(requirementOfferHistory.getOfferId().getId());
				if(offers.getDateOfJoining() != null){
					requirementOfferHistory.setDateOfJoiningStr((commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),offers.getDateOfJoining())));
				}
				requirementOfferHistory.setOfferUUID(offers.getOfferUUID());
				if(offers.getDateOfJoining() != null){
					requirementOfferHistory.setDateOfJoiningStr((commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),offers.getDateOfJoining())));
				}
				if(offers.getOfferLetterTemplate() != null) {
					requirementOfferHistory.setTemplateUUID(offers.getOfferLetterTemplate().getTemplateUUID());
					requirementOfferHistory.setTemplateName(offers.getOfferLetterTemplate().getTemplateName());
				}
				if(offers.getOfferExpireDate() != null) {
					requirementOfferHistory.setOfferExpireDate((commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),offers.getOfferExpireDate())));
				}
				if(requirementOfferHistory.getSubmissionId().getVendorLocationId() != null){
					requirementOfferHistory.setIsNotifyToVendorRequired(Constants.YES);
				}else{
					requirementOfferHistory.setIsNotifyToVendorRequired(Constants.NO);
				}
				if(offers.getSubmissionId().getRequirement().getClientId() != null){
					if(offers.getSubmissionId().getRequirement().getClientId().getResponderType().equalsIgnoreCase("Client")){
						requirementOfferHistory.setClientName(offers.getSubmissionId().getRequirement().getClientId().getResponderLocationId().getOrgLocationName());
					}
					if(offers.getSubmissionId().getRequirement().getClientId().getResponderType().equalsIgnoreCase("Vendor")){
						requirementOfferHistory.setClientName(offers.getSubmissionId().getRequirement().getClientId().getLocationId().getOrgLocationName());
					}
				}else{
					RequirementSubmission requirementSubmission = requirementSubmissionService.getSubmissionsBySourceSubmissionId(offers.getSubmissionId().getId(),orgLocationsId);
				    if(requirementSubmission != null){
				    	requirementOfferHistory.setClientName(requirementSubmission.getOrgLocations().getOrgLocationName());
				    }else if(offers.getSubmissionId().getSourceSubmission() != null && offers.getSubmissionId().getSourceSubmission().getId() != null){
				    	RequirementSubmission parentRequirementSubmission = requirementSubmissionService.getSubmissionsBySourceSubmissionId(offers.getSubmissionId().getSourceSubmission().getId(),orgLocationsId);
				    	 if(parentRequirementSubmission != null){
						     requirementOfferHistory.setClientName(parentRequirementSubmission.getOrgLocations().getOrgLocationName());
				    	 }
				    }
				}
				
			}
		}
		
		
		return offersList;
	}

	@Override
	public List<RequirementOfferHistory> getAllRequirementOfferHistoriesByOfferId(Long offerId) {
		return requirementOfferHistoryDao.getAllRequirementOfferHistoriesByOfferId(offerId);
	}

	@Override
	public List<RequirementOfferHistory> getRequirementInternalAndIsClientOffersHistoryBySubmissionIdAndOrgLocationsId(Long submissionId, Long orgLocationsId) throws Exception {
		return requirementOfferHistoryDao.getRequirementInternalAndIsClientOffersHistoryBySubmissionIdAndOrgLocationsId(submissionId,orgLocationsId);
	}

	@Override
	public List<RequirementOfferHistory> getCrowdSourcingOfferDetailsBySubmissionId(Long submissionId,String locationCode)
			throws Exception {
		
		List<RequirementOfferHistory> offersList = null;
		offersList = requirementOfferHistoryDao.getCrowdSourcingOfferDetailsBySubmissionId(submissionId,locationCode);
		OrgLocations orglo = orgLocationService.getOrgLocationsByLocationCode(locationCode);
		if (offersList != null && !offersList.isEmpty() ) {
			for (RequirementOfferHistory requirementOfferHistory : offersList) {
				RequirementOffers offers = requirementOffersDao.getRequirementOfferId(requirementOfferHistory.getOfferId().getId());
				if(offers.getDateOfJoining() != null){
					requirementOfferHistory.setDateOfJoiningStr(commonUtil.dateStrFromOrgDateFormatter(orglo.getDateFormat(),(offers.getDateOfJoining())));
				}
				
				if(requirementOfferHistory.getSubmissionId().getVendorLocationId() != null){
					requirementOfferHistory.setIsNotifyToVendorRequired(Constants.YES);
				}else{
					requirementOfferHistory.setIsNotifyToVendorRequired(Constants.NO);
				}
				if(offers.getSubmissionId().getRequirement().getClientId() != null){
					if(offers.getSubmissionId().getRequirement().getClientId().getResponderType().equalsIgnoreCase("Client")){
						requirementOfferHistory.setClientName(offers.getSubmissionId().getRequirement().getClientId().getResponderLocationId().getOrgLocationName());
					}
					if(offers.getSubmissionId().getRequirement().getClientId().getResponderType().equalsIgnoreCase("Vendor")){
						requirementOfferHistory.setClientName(offers.getSubmissionId().getRequirement().getClientId().getLocationId().getOrgLocationName());
					}
				}else{
					RequirementSubmission requirementSubmission = requirementSubmissionService.getRequirementSubmissionById(offers.getSubmissionId().getId());
				    if(requirementSubmission != null){
				    	requirementOfferHistory.setClientName(requirementSubmission.getOrgLocations().getOrgLocationName());
				    }else if(offers.getSubmissionId().getSourceSubmission() != null && offers.getSubmissionId().getSourceSubmission().getId() != null){
				    	RequirementSubmission parentRequirementSubmission = requirementSubmissionService.getRequirementSubmissionById(offers.getSubmissionId().getSourceSubmission().getId());
				    	 if(parentRequirementSubmission != null){
						     requirementOfferHistory.setClientName(parentRequirementSubmission.getOrgLocations().getOrgLocationName());
				    	 }
				    }
				}
				
			}
		}
		
		
		return offersList;
	}
	
}
