package com.bis.sourcelead.rest;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dto.ApplicantAuthorizationDTO;
import com.bis.sourcelead.dto.Response;
import com.bis.sourcelead.entity.Department;
import com.bis.sourcelead.entity.Notifications;
import com.bis.sourcelead.entity.OfferLetterTemplate;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.Requirement;
import com.bis.sourcelead.entity.RequirementOfferHistory;
import com.bis.sourcelead.entity.RequirementOffers;
import com.bis.sourcelead.entity.RequirementSubmission;
import com.bis.sourcelead.entity.RequirementSubmissionHistory;
import com.bis.sourcelead.entity.RequirementSubmissionTemplateFields;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.enums.ApplicantSubmissionStatus;
import com.bis.sourcelead.enums.Result;
import com.bis.sourcelead.enums.SubmissionRateStatus;
import com.bis.sourcelead.service.CandidateService;
import com.bis.sourcelead.service.NotificationsService;
import com.bis.sourcelead.service.OfferLetterTemplateService;
import com.bis.sourcelead.service.RequirementAssignToService;
import com.bis.sourcelead.service.RequirementOfferHistoryService;
import com.bis.sourcelead.service.RequirementOffersService;
import com.bis.sourcelead.service.RequirementService;
import com.bis.sourcelead.service.RequirementSubmissionHistoryService;
import com.bis.sourcelead.service.RequirementSubmissionService;
import com.bis.sourcelead.service.RequirementSubmissionTemplateFieldsService;
import com.bis.sourcelead.service.TeamService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;

/**
 * sarath kumar punnana 
 * @author bis113
 *
 */

@RestController
public class RequirementOfferRestController {

	private static final Logger logger = LoggerFactory.getLogger(RequirementOfferRestController.class);
	
	@Autowired
	private ApplicationContext applicationContext;
	
	@Autowired
	RequirementSubmissionService requirementSubmissionService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	CommonUtil commonUtil;
	
	@Value("${static.path}")
	private String staticPath;
	
	@Autowired
	RequirementOffersService requirementOffersService;
	
	@Autowired
	RequirementAssignToService  requirementAssignToService;
	
	@Autowired
	private RequirementSubmissionTemplateFieldsService requirementSubmissionTemplateFieldsService;
	
	@Autowired
	RequirementService  requirementService;
	
	@Autowired
	RequirementOfferHistoryService requirementOfferHistoryService;
	
	@Autowired
	TeamService teamService ;
	
	@Autowired
	CandidateService candidateService ;
	
	@Autowired
	NotificationsService notificationsService;
	
    @Autowired
	RequirementSubmissionHistoryService requirementSubmissionHistoryService;

    @Autowired
    OfferLetterTemplateService offerLetterTemplateService;
	
	@PreAuthorize("hasRole('ROLE_CREAE_OFFER')")
	@RequestMapping(value = "/saveOffer" , method = RequestMethod.POST)
	public String saveOffer(@RequestParam(value="jsonData", required=false) String json,MultipartHttpServletRequest request) throws Exception {
			   					 
			

		    String json1 = "{" + json + "}";
			JSONObject jsonObj = new JSONObject(json1);
			
			
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			requirementOffersService.saveRequirementOffers(jsonObj, user, orgLocations,request);
			return "success";
	    
	}
	
	@RequestMapping(value = "/getAllSubmittedAndShortListAndInterviewededCandidates")
	public @ResponseBody String getAllSubmittedAndShortListAndInterviewededCandidates(@RequestParam(value="requirementId", required=false) Long requirementId,
			                                              @RequestParam(value="existingApplicantsIds", required=false) List<Long> existingApplicantsIds,
			                                              @RequestParam(value="isClientOffer", required=false) String isClientOffer,
			                                              @RequestParam(value="isWorkflowConfigured", required=false) String isWorkflowConfigured,
			                                              HttpServletRequest request) throws Exception {
		
		User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		String loginUserRole = (String) SourceLeadSessionManager.getSessionAttribute(request, "loginUserRole");
		String resultJson = null;
		List<RequirementSubmission> shortListCandidatesList = null;
		String isClientRequirement = null;
		
		Requirement requirement = requirementService.getRequirementByRequirementId(requirementId);
		
		if (requirement != null && requirement.getPrimaryRecruiterUserId() != null) {
			loginUser = requirement.getManagerUserId();
		}
		if(requirement.getIsClientJob() != null && requirement.getIsClientJob().equalsIgnoreCase(Constants.YES)){
			isClientRequirement = Constants.YES;
		}else{
			isClientRequirement = Constants.NO;
		}
		
		if(isClientOffer.equalsIgnoreCase(Constants.YES)){
			if(requirement.getParentRequirement() != null){
				shortListCandidatesList = requirementSubmissionService.getAllSubmittedAndShortListAndInterviewededCandidatesForClientOffer(requirement.getParentRequirement().getOrgLocationId(), requirement.getParentRequirement().getId(),existingApplicantsIds,isClientOffer);
			}else{
				shortListCandidatesList = requirementSubmissionService.getAllSubmittedAndShortListAndInterviewededCandidates(orgLocations, requirementId,existingApplicantsIds,isClientOffer,loginUser,loginUserRole,isClientRequirement, isWorkflowConfigured);
				
			}
		}else{
			shortListCandidatesList = requirementSubmissionService.getAllSubmittedAndShortListAndInterviewededCandidates(orgLocations, requirementId,existingApplicantsIds,isClientOffer,loginUser,loginUserRole,isClientRequirement,isWorkflowConfigured);
			
		}
		
		
		for (RequirementSubmission requirementSubmission : shortListCandidatesList) {
			 StringBuilder nameBuilder = new StringBuilder();
			RequirementSubmissionTemplateFields requirementSubmissionTemplateFields = requirementSubmissionTemplateFieldsService.getSubmissionsTemplateFieldsByRequirementSubmissionsId(requirementSubmission.getId());
			if(requirementSubmissionTemplateFields.getLastName() != null){
				requirementSubmission.setCandidateName(nameBuilder.append(requirementSubmissionTemplateFields.getFirstName()).append(" ").append(requirementSubmissionTemplateFields.getLastName()).toString());
			}else{
				requirementSubmission.setCandidateName(requirementSubmissionTemplateFields.getFirstName());
			}
		}
		resultJson = commonUtil.toJSON(shortListCandidatesList);
		return resultJson;
		
	}
	
	
	
	

	/**
	* @author David Raju 
	* 
	* This method get Offer Details using  the submission Id
	* 
	* @param submissionId
	* 
	* @param request
	* 
	* @return resultJson
	* 
	* @throws Exception
	*/
	@PreAuthorize("hasAnyRole('ROLE_VIEW_OFFER','ROLE_VIEW_ACCEPT_OFFER','ROLE_VIEW_REJECT_OFFER')")
	@RequestMapping(value = "/getOfferDetails")
	public @ResponseBody String getOfferDetails(@RequestParam(value="submissionId", required=false) Long submissionId,
			                                             
			                                              HttpServletRequest request) throws Exception {
		
		
		User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		
		String resultJson = null;
		Requirement req   =null;
		RequirementSubmission  requirementSubmission =null;
		RequirementOffers  requirementOffers =null;
		
		List<RequirementOfferHistory> offersList = requirementOfferHistoryService.getRequirementInternalAndIsClientOfferssBySubmissionIdAndOrgLocationsId(submissionId, orgLocations.getId());
		for(RequirementOfferHistory offers : offersList) {
			offers.setSubmissionStatusStr(offers.getSubmissionId().getSubmissionStatus().toString());
		}
		return resultJson = commonUtil.toJSON(offersList);
		
		/*requirementSubmission = requirementSubmissionService.getRequirementSubmissionById(submissionId);
		String loginUserType =  (String) SourceLeadSessionManager.getSessionAttribute(request, "userType");
		
		requirementOffers =  requirementOffersService.offerDetails(submissionId, loginUser, orgLocations, loginUserType);
		if(requirementOffers != null){
			
			Boolean  flag = requirementService.checkRequirementIsReleasedOrNot(requirementSubmission.getRequirement().getId(), orgLocations.getId());
			if(flag){
				requirementOffers.setRequirementReleasedOrNot(flag);
			}else{
				requirementOffers.setRequirementReleasedOrNot(false);
			}
			if(requirementOffers !=null){
				resultJson = commonUtil.toJSON(requirementOffers);
				return resultJson;
			}else{
				return null;
			}
		}else{
			
			return null;
		}*/
		
		
	}
	
	
	
	/**
	* @author David Raju 
	* 
	* This method is used to download OfferAttachment
	* 
	* @param offerId
	* @param request
	* 
	* @return resultJson
	* 
	* @throws Exception
	*/
	@PreAuthorize("hasAnyRole('ROLE_VIEW_ACCEPT_OFFER','ROLE_VIEW_REJECT_OFFER','ROLE_CROWDSOURCING_REQUIREMENTS')")
	@RequestMapping(value = "/acceptingOffer")
	public @ResponseBody String acceptingOffer(@RequestParam(value="submissionId", required=false) Long submissionId,
			                                   @RequestParam(value="offerStatus", required=false) String offerStatus,
			                                   @RequestParam(value="reason", required=false) String reason, 
			                                   @RequestParam(value="OfferId", required=false) Long OfferId,
			                                   HttpServletRequest request) throws Exception {
		
		
		User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		/*
		 * Requirement req   =null;
		RequirementSubmission  requirementSubmission =null;
		RequirementOffers  requirementOffers =null;
		
		  requirementSubmission = requirementSubmissionService.getRequirementSubmissionById(submissionId);
    	  
		  
		   // This if condition is for the vendor login accepts the Offer  so bringing offer made by  the client 
		   
		  RequirementSubmission clientsubmission = requirementSubmissionService.getSubmissionsByVendorSubmissionIdAndvendorOrgLocationId(submissionId,orgLocations.getId());
			
		  if(clientsubmission != null){
				requirementOffers  = requirementOffersService.getRequirementOfferSubmissionById(clientsubmission.getId());

			}else{
				requirementOffers  = requirementOffersService.getRequirementOfferSubmissionById(requirementSubmission.getId());
			}
    	

	     offerStatus  = requirementOffersService.acceptOrRejectOffer(submissionId, requirementOffers.getId(), loginUser, offerStatus, reason, orgLocations);*/
		  offerStatus  = requirementOffersService.acceptOrRejectOffer(submissionId, OfferId, loginUser, offerStatus, reason, orgLocations);
		 
		return offerStatus;
	}
	
	
	
	/**
	* @author David Raju 
	* 
	* This method is used to download OfferAttachment
	* 
	* @param offerId
	* @param request
	* 
	* @return resultJson
	* 
	* @throws Exception
	*/
	@RequestMapping(value = "/ws/downloadOfferAttachment", method = RequestMethod.GET)
    public void downloadOfferAttachment(@RequestParam(value="offerId", required=false) Long offerId ,HttpServletResponse response) throws Exception {
		
		
		   RequirementOffers requirementOffers  =requirementOffersService.getRequirementOfferId(offerId);
		
		    response.setContentType("application/octet-stream");
		    StringBuilder fileName = new StringBuilder();
	        response.setHeader("Content-Disposition", fileName.append("attachment; filename=\"").append(requirementOffers.getFileName()).append("\"").toString());
		    
			InputStream inputStream = new ByteArrayInputStream(requirementOffers.getDocFile());
			try {
				OutputStream outStream = response.getOutputStream();

				byte[] buffer = new byte[4096];
				int bytesRead = -1;

				while ((bytesRead = inputStream.read(buffer)) != -1) {
					outStream.write(buffer, 0, bytesRead);
				}

				inputStream.close();
				outStream.close();

			} catch (FileNotFoundException e) {
				logger.error(ExceptionUtils.getFullStackTrace(e));
			} 
		}
	
	@RequestMapping(value = "/ws/acceptOrRejectOffer")
    @ResponseBody  String acceptOrRejectOffer(@RequestParam(value="offerStatus", required=false) String offerStatus,
                           @RequestParam(value = "offerId", required = false) Long offerId,
                           @RequestParam(value="rejectedReason", required=false) String rejectedReason,
                           HttpServletRequest request) throws Exception {

			 String setofferStatus = "";
			 User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			 
			 RequirementOffers requirementOffers  = requirementOffersService.getRequirementOfferId(offerId);
				List<RequirementOfferHistory> requirementOfferHistories = requirementOfferHistoryService.getAllRequirementOfferHistoriesByOfferId(offerId);
			    
			    if(requirementOfferHistories != null && !requirementOfferHistories.isEmpty() ){  
				    if(offerStatus.equalsIgnoreCase("Accepted")){
				    	for (RequirementOfferHistory requirementOfferHistory : requirementOfferHistories) {
				    		   RequirementSubmission requirementSubmission = requirementSubmissionService.getRequirementSubmissionById(requirementOfferHistory.getSubmissionId().getId());
					    		if(requirementOfferHistory.getOfferSourceType() != null && "OFFERMADE".equals(requirementOfferHistory.getOfferSourceType())){
					    			requirementSubmission.setSubmissionStatus(ApplicantSubmissionStatus.OFFERACCEPTED.toString());
					    		}else{
					    			requirementSubmission.setSubmissionStatus(ApplicantSubmissionStatus.CLIENT_OFFERACCEPTED.toString());
					    		}
								 requirementSubmission.setUpdatedDate(commonUtil.currentDate());
								 requirementSubmissionService.storeRequirementSubmission(requirementSubmission);
						  }
				    	
				    	  //Changing the status of offerTable 
					      if(requirementOffers  != null){
							    requirementOffers.setOfferStatus("OFFERACCEPTED");
							    requirementOffers.setUpdatedDate(commonUtil.currentDate());
							    requirementOffersService.saveOfferStatus(requirementOffers);
					     }
					      setofferStatus ="you have accepted the offer";
			    }else if(offerStatus.equalsIgnoreCase("Rejected")){
			    	for (RequirementOfferHistory requirementOfferHistory : requirementOfferHistories) {
		    		    RequirementSubmission requirementSubmission = requirementSubmissionService.getRequirementSubmissionById(requirementOfferHistory.getSubmissionId().getId());
						  if(requirementOfferHistory.getOfferSourceType() != null && "OFFERMADE".equals(requirementOfferHistory.getOfferSourceType())){
			    			requirementSubmission.setSubmissionStatus(ApplicantSubmissionStatus.OFFERREJECTED.toString());
			    		}else{
			    			requirementSubmission.setSubmissionStatus(ApplicantSubmissionStatus.CLIENT_OFFERREJECTED.toString());
			    		}
			    		 requirementSubmission.setRejectedReason(rejectedReason);
						 requirementSubmission.setUpdatedDate(commonUtil.currentDate());
						 requirementSubmissionService.storeRequirementSubmission(requirementSubmission);
						 
					    RequirementSubmissionHistory requirementSubmissionHistory = new RequirementSubmissionHistory();
						  if(requirementOfferHistory.getOfferSourceType() != null && "OFFERMADE".equals(requirementOfferHistory.getOfferSourceType())){
						requirementSubmissionHistory.setFromStage(ApplicantSubmissionStatus.OFFERMADE.toString());
						requirementSubmission.setSubmissionStatus(ApplicantSubmissionStatus.OFFERREJECTED.toString());
					    }else{
					    	requirementSubmissionHistory.setFromStage(ApplicantSubmissionStatus.CLIENT_OFFERMADE.toString());
							requirementSubmission.setSubmissionStatus(ApplicantSubmissionStatus.CLIENT_OFFERREJECTED.toString());	
					    }
						requirementSubmissionHistoryService.updateRequirementHistory(requirementSubmission, user, requirementSubmissionHistory, requirementSubmission.getCreatedBy());
		    	
		    	  //Changing the status of offerTable 
				  if(requirementOffers  != null ){
					  if(requirementOfferHistory.getOfferSourceType() != null && "OFFERMADE".equals(requirementOfferHistory.getOfferSourceType())){
						  requirementOffers.setOfferStatus("OFFERREJECTED"); 
					  }else{
						  requirementOffers.setOfferStatus("CLIENT_OFFERREJECTED");   
					  }
					    requirementOffers.setRejectedReason(rejectedReason);
					    requirementOffers.setUpdatedDate(commonUtil.currentDate());
					    requirementOffersService.saveOfferStatus(requirementOffers);
			      }
				  requirementOffersService.sendApplicantOfferRejectMail(requirementOffers);
		    }
					  setofferStatus ="success";
			    }
			 }
			 
			 
            /*RequirementOffers requirementOffers = requirementOffersService.getRequirementOfferSubmissionById(submissionId);

            RequirementSubmission requirementSubmission   =requirementSubmissionService.getRequirementSubmissionById(submissionId);
            if(offerStatus.equalsIgnoreCase("Accepted")){
                     requirementSubmission.setSubmissionStatus(ApplicantSubmissionStatus.OFFERACCEPTED);
                     if(requirementSubmission !=null){
                    	 
                    	 requirementSubmissionService.storeRequirementSubmission(requirementSubmission);
                         requirementOffers.setOfferStatus("OFFERACCEPTED");
                        requirementOffersService.saveOfferStatus(requirementOffers);
                        setofferStatus ="you have accepted the offer";
                     }
                      
                    
            }else if(offerStatus.equalsIgnoreCase("Rejected")){
                    requirementSubmission.setSubmissionStatus(ApplicantSubmissionStatus.OFFERREJECTED);
                    if( requirementSubmission!=null ){
                    	requirementSubmissionService.storeRequirementSubmission(requirementSubmission);
                        requirementOffers.setOfferStatus("OFFERREJECTED");
                        requirementOffersService.saveOfferStatus(requirementOffers);
                        setofferStatus ="you have rejected the offer";
                    }
                    
            }*/
            
           return setofferStatus;
    }                



	/**
	* @author David Raju 
	* 
	* This method user to  notify To Applicant Of Requirement OfferMade 
	* 
	* @param offerId
	* 
	* @param submissionId
	* 
	* 
	* @param request
	* 
	* @return String
	* 
	* @throws Exception
	*/

	@RequestMapping(value = "/sendNotificationAboutRequirementOfferMade" , method = RequestMethod.POST)
	public String notifyToApplicantOfRequirementOfferMade(@RequestParam(value="submissionId", required=false) Long submissionId,
														  @RequestParam(value="offerId", required=false) Long offerId,
														  @RequestParam(value="notificationtype", required=false) String notificationtype,
														  @RequestParam(value = "offerHistoryId", required = false) Long offerHistoryId,
														  HttpServletRequest request) throws Exception {
		
			User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			
	        requirementOffersService.sendNotificationAboutRequirementOfferMade(submissionId, offerId, offerHistoryId,loginUser, orgLocations,notificationtype);
			return "success";
	    
	}

	
	/**
	* @author Venkat Kesav 
	* 
	* Handles accept Candidate
	* 
	* @param json, 
	* @param request
	* 
	* @return ResponseBody
	* 
	* @throws Exception
	*/
	@RequestMapping(value = "/acceptRejectCandidate", method = RequestMethod.POST)
	public @ResponseBody String acceptRejectCandidate(ApplicantAuthorizationDTO  applicantAuthorizationDTO,String submissionId, String decision, HttpServletRequest request) throws Exception {

		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		Org loginOrg = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
		applicantAuthorizationDTO.setStaticPath(staticPath);
		if(StringUtils.isNotEmpty(submissionId)){
			RequirementSubmission requirementSubmission = requirementSubmissionService.getRequirementSubmissionById(Long.parseLong(submissionId));
			
			requirementSubmissionService.acceptRejectCandidate(applicantAuthorizationDTO, user, requirementSubmission, decision,loginOrg,orgLocation);

			/*SourceLeadSessionManager.setSessionAttribute(request, "resumeAddMessage",
					"Successfully you have added");*/

			return "success";
		}else{
			return "exception";
		}
	}
	
	@RequestMapping(value = "/acceptCandidateFromNotification", method = RequestMethod.GET)
	public ModelAndView acceptCandidateFromNotification(String submissionId,
												  @RequestParam(value = "UUID", required = false) String UUID,HttpServletRequest request) throws Exception {

		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		Org loginOrg = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
		
		if(StringUtils.isNotEmpty(submissionId)){
			RequirementSubmission requirementSubmission = requirementSubmissionService.getRequirementSubmissionById(Long.parseLong(submissionId));
			requirementSubmission.setSubmissionRateStatus(SubmissionRateStatus.ACCEPTED);
			requirementSubmissionService.storeRequirementSubmission(requirementSubmission);
			requirementSubmissionService.sendEmailtoApplicant(loginOrg, user, requirementSubmission, orgLocation);
		
			 Notifications notifications = notificationsService.getNotificationsByUUID(UUID);
			 if(null != notifications){
				 notifications.setUserNotificationStatus("Accepted");
				 notificationsService.saveNotification(notifications);
			 }
			 
			StringBuilder redirectUrl = new StringBuilder();
			
			ModelAndView model = new ModelAndView();
			model.setViewName("/dashboard");
			return model;
			
		}else{
			
			ModelAndView model = new ModelAndView();
			model.setViewName("/dashboard");
			return model;
			
		}
	}
	
	@RequestMapping(value = "/acceptRejectCandidateFromDashboard", method = RequestMethod.POST)
	public @ResponseBody Response<String> acceptRejectCandidateFromDashboard(ApplicantAuthorizationDTO applicantAuthorizationDTO, Long submissionId, String status, HttpServletRequest request) throws Exception
	{
		Response<String> response = new Response<>();
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		if(user != null) {
			try {
					OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
					Org loginOrg = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
					if (null != submissionId) {
						RequirementSubmission requirementSubmission = requirementSubmissionService.getRequirementSubmissionById(submissionId);
						RequirementSubmissionHistory requirementSubmissionHistory = new RequirementSubmissionHistory();
						requirementSubmissionHistory.setFromStage(requirementSubmission.getSubmissionStatus().toString());
						if (StringUtils.equalsIgnoreCase(status, "accept")) {
							requirementSubmission.setSubmissionStatus(ApplicantSubmissionStatus.SUBMISSION.toString());
							requirementSubmission.setSubmissionRateStatus(SubmissionRateStatus.ACCEPTED);
							requirementSubmission.setSubmissionRequestUser(user.getFirstName() + ""	+ user.getLastName());
							requirementSubmission = requirementSubmissionService.storeRequirementSubmission(requirementSubmission);
							requirementSubmissionHistoryService.updateRequirementHistory(requirementSubmission, user, requirementSubmissionHistory, requirementSubmission.getCreatedBy());
							requirementSubmissionService.sendEmailtoApplicant(loginOrg, user, requirementSubmission, orgLocation);
							requirementSubmissionService.sendEmailtoAssignedMemberApplicantAuthorization(loginOrg, user, requirementSubmission, orgLocation);
						}
						else if (StringUtils.equalsIgnoreCase(status, "rejected")) {
							String previousStatus = requirementSubmission.getSubmissionStatus();
							requirementSubmission.setPreviousSubmissionStatus(previousStatus);
							requirementSubmission.setSubmissionStatus(ApplicantSubmissionStatus.REJECTED.toString());
							requirementSubmission.setSubmissionRequestUser(user.getFirstName() + ""	+ user.getLastName());
							requirementSubmission.setSubmissionRateStatus(SubmissionRateStatus.REJECTED);
							if (StringUtils.isNotBlank(applicantAuthorizationDTO.getComment())) {
								requirementSubmission.setRejectedReason(applicantAuthorizationDTO.getComment());
							}
							if (StringUtils.isNotBlank(applicantAuthorizationDTO.getRejectedCategory())) {
								requirementSubmission.setRejectedCategoryType(applicantAuthorizationDTO.getRejectedCategory());
							}
							if (StringUtils.isNotBlank(applicantAuthorizationDTO.getRejectedCategoryOther())) {
								requirementSubmission.setRejectedCategoryOther(applicantAuthorizationDTO.getRejectedCategoryOther());
							}
							requirementSubmission = requirementSubmissionService.storeRequirementSubmission(requirementSubmission);
							requirementSubmissionHistoryService.updateRequirementHistory(requirementSubmission, user, requirementSubmissionHistory, requirementSubmission.getCreatedBy());
						}
					}
					response.setObject("success");
				    response.setResponse(Result.SUCCESS.name());
					response.setResponseMessage(Result.SUCCESS.getValue());
				}catch (Exception e) {
					response.setResponse(Result.AWKWARD.name());
					response.setResponseMessage(Result.AWKWARD.getValue());
				}
			}else {
				response.setResponse(Result.SESSION_TIMEDOUT.name());
				response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
			}
		return response;
	}
	
	
	@RequestMapping(value = "/getOfferLetterTemplateList", method = RequestMethod.POST)
	public @ResponseBody List<OfferLetterTemplate> getOfferLetterTemplateList(HttpServletRequest request) throws Exception
	{
		OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		List<OfferLetterTemplate> offerLetterTemplateList = offerLetterTemplateService.getOfferLetterTemplateListByOrgLocationId(loginOrgLocation.getId());
		return offerLetterTemplateList;
		
	}
	  
}
