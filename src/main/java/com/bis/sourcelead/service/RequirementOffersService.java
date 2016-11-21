package com.bis.sourcelead.service;

import java.util.List;

import org.json.JSONObject;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.Requirement;
import com.bis.sourcelead.entity.RequirementOffers;
import com.bis.sourcelead.entity.RequirementSubmission;
import com.bis.sourcelead.entity.User;

public interface RequirementOffersService {

	void saveRequirementOffers(JSONObject jsonObj, User user, OrgLocations orgLocations,MultipartHttpServletRequest multiFile) throws Exception;;
	
	RequirementOffers getRequirementOfferSubmissionById(Long submissionId)throws Exception;
	
	RequirementOffers getRequirementOfferId(Long offerId)throws Exception;
	
	RequirementOffers saveOfferStatus(RequirementOffers requirementOffers)throws Exception;

	void offerMailToVendors(User loginUser,OrgLocations loginOrgLocations, Requirement clientrequirement, List<Requirement> vendorRequirement)	throws Exception;

	void addOfferMailScenario(User loginUser, RequirementSubmission applicant, OrgLocations orgLocations,RequirementOffers requirementOffers, Candidate candidate) throws Exception;
	
	void offerAcceptEmailScenario(User loginUser,OrgLocations loginOrgLocations,Requirement requirement,RequirementOffers requirementOffers,RequirementSubmission requirementSubmission) throws Exception;
	
	void offerRejectEmailScenario(User loginUser,OrgLocations loginOrgLocations,Requirement requirement,RequirementOffers requirementOffers,RequirementSubmission requirementSubmission) throws Exception;
}

