package com.bis.sourcelead.service;

import org.json.JSONObject;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.RequirementOffers;
import com.bis.sourcelead.entity.User;

public interface RequirementOffersService {

	void saveRequirementOffers(JSONObject jsonObj, User user, OrgLocations orgLocations,MultipartHttpServletRequest multiFile) throws Exception;;
	
	RequirementOffers getRequirementOfferSubmissionById(Long submissionId)throws Exception;
	
	RequirementOffers getRequirementOfferId(Long offerId)throws Exception;
	
	RequirementOffers saveOfferStatus(RequirementOffers requirementOffers)throws Exception;
	
	
}

