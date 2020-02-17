package com.bis.sourcelead.service;

import java.util.Date;
import java.util.List;

import org.json.JSONObject;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.CandidateGlobal;
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

	void offerMailToVendors(User loginUser,OrgLocations loginOrgLocations, Requirement vendorRequirement)	throws Exception;

	void addOfferMailScenario(User loginUser, RequirementSubmission applicant, OrgLocations orgLocations,RequirementOffers requirementOffers, Candidate candidate) throws Exception;
	
	void offerAcceptEmailScenario(User loginUser,OrgLocations loginOrgLocations,Requirement requirement,RequirementOffers requirementOffers,RequirementSubmission requirementSubmission) throws Exception;
	

	void sendNotificationAboutRequirementOfferMade(Long submissionId,Long OfferId, Long offerHistoryId, User loginUser,OrgLocations orgLocations ,String notificationtype) throws Exception;
	
	String acceptOrRejectOffer(Long submissionId,Long OfferId, User loginUser,String offerStatus,String reason, OrgLocations orgLocations ) throws Exception;
	
	//RequirementOffers offerDetails(Long submissionId, User loginUser, OrgLocations orgLocations ,String userType) throws Exception;
	
  
	void offerRejectEmailScenario(User loginUser,OrgLocations loginOrgLocations,Requirement requirement,RequirementOffers requirementOffers,RequirementSubmission requirementSubmission) throws Exception;

	List<RequirementOffers> getRequirementOfferSubmissionByIdAndOrgLocationId(Long submissionId, Long orgLocationId)throws Exception;
	
	List<RequirementOffers> getOfferAcceptedCandidatesByOrgLocationId(Long orgLocationId) throws Exception;
	
	RequirementOffers getRequirementOffersByUUID(String offerUUId) throws Exception;

	List<RequirementOffers> getOfferAcceptedVendorCandidatesByOrgLocationId(Long locationId) throws Exception;
	
	CandidateGlobal addCandidateToClientAfterOfferAccepted(RequirementOffers requirementOffers,OrgLocations orgLocations) throws Exception;
	
	void sendApplicantOfferAcceptMail(RequirementOffers requirementOffers) throws Exception;

	void sendApplicantOfferRejectMail(RequirementOffers requirementOffers) throws Exception;
	
	RequirementOffers getOnboardRequirementOfferId(Long offerId)throws Exception;
	
	List<RequirementOffers> getOfferAcceptedCandidatesByOrgLocationIdAndDateOfJoin(Long orgLocationId, Date joiningDate) throws Exception;
}

