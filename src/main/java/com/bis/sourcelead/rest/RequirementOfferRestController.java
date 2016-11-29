package com.bis.sourcelead.rest;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.Requirement;
import com.bis.sourcelead.entity.RequirementAssignTo;
import com.bis.sourcelead.entity.RequirementOffers;
import com.bis.sourcelead.entity.RequirementSubmission;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.enums.ApplicantSubmissionStatus;
import com.bis.sourcelead.service.CandidateService;
import com.bis.sourcelead.service.RequirementAssignToService;
import com.bis.sourcelead.service.RequirementOffersService;
import com.bis.sourcelead.service.RequirementService;
import com.bis.sourcelead.service.RequirementSubmissionService;
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
	
	@Autowired
	RequirementOffersService requirementOffersService;
	
	@Autowired
	RequirementAssignToService  requirementAssignToService;
	
	@Autowired
	RequirementService  requirementService;
	
	@Autowired
	TeamService teamService ;
	
	@Autowired
	CandidateService candidateService ;
	
	
	
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
			                                              HttpServletRequest request) throws Exception {
		
		User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");

		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		String resultJson = null;
	
		List<RequirementSubmission> shortListCandidatesList = requirementSubmissionService.getAllSubmittedAndShortListAndInterviewededCandidates(orgLocations.getId(), requirementId,existingApplicantsIds);
		
		/*for (RequirementSubmission sub : shortListCandidatesList) {
			String candidateName = "";
 		   if(StringUtils.isNotEmpty(sub.getCandidate().getFirstName())){
 			   candidateName = sub.getCandidate().getFirstName();
 		   }
 		   if(StringUtils.isNotEmpty(sub.getCandidate().getLastName()) && StringUtils.isNotBlank(sub.getCandidate().getLastName())){
 			   candidateName = candidateName+" "+sub.getCandidate().getLastName();
 		   }
 		  sub.setCandidateName(candidateName);
		}*/
		
	    List<RequirementSubmission>  list = new ArrayList<RequirementSubmission>();
		for (RequirementSubmission sub : shortListCandidatesList) {
			 RequirementAssignTo  requirementAssignTo = requirementAssignToService.getRequirementAssignToByRequirementId(sub.getRequirement().getId());
			 /*
			  * Offer can be shown to either loggedin user is admin of org  or 
			  *                                               manager of  requirement or 
			  *                                               requirement assigned to owner or
			  *                                               requirement team lead
			  *                                               
			  */
			 String loginUserType =  (String) SourceLeadSessionManager.getSessionAttribute(request, "userType");
			 if(requirementAssignTo != null && 
					 (sub.getCreatedBy().equalsIgnoreCase(loginUser.getUserCode()) 
					   || (sub.getRequirement().getManagerUserId().getId() == loginUser.getId())
					   || (StringUtils.isNotEmpty(loginUserType) && loginUserType.equalsIgnoreCase(Constants.ADMIN)) 
					   || ( requirementAssignTo.getTeamId() !=null && requirementAssignTo.getTeamId().getOwnerUserId().getId() == loginUser.getId())
					  )
					){
						
							String candidateName = "";
				 		   if(StringUtils.isNotEmpty(sub.getCandidate().getFirstName())){
				 			   candidateName = sub.getCandidate().getFirstName();
				 		   }
				 		   if(StringUtils.isNotEmpty(sub.getCandidate().getLastName()) && StringUtils.isNotBlank(sub.getCandidate().getLastName())){
				 			   candidateName = candidateName+" "+sub.getCandidate().getLastName();
				 		   }
				 		  sub.setCandidateName(candidateName);
				 		 list.add(sub);
					 
			 }
			 
		}
	
	
		
		resultJson = commonUtil.toJSON(list);
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
	@PreAuthorize("hasRole('ROLE_VIEW_OFFER')")
	@RequestMapping(value = "/getOfferDetails")
	public @ResponseBody String getOfferDetails(@RequestParam(value="submissionId", required=false) Long submissionId,
			                                             
			                                              HttpServletRequest request) throws Exception {
		
		
		User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");

		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		String resultJson = null;
		Requirement req   =null;
		RequirementSubmission  requirementSubmission =null;
		RequirementOffers  requirementOffers =null;
		
		requirementSubmission = requirementSubmissionService.getRequirementSubmissionById(submissionId);
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
		}
		
		
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
	@PreAuthorize("hasAnyRole('ROLE_ACCEPT_OFFER','ROLE_REJECT_OFFER')")
	@RequestMapping(value = "/acceptingOffer")
	public @ResponseBody String acceptingOffer(@RequestParam(value="submissionId", required=false) Long submissionId,
			                                   @RequestParam(value="offerStatus", required=false) String offerStatus,
			                                   @RequestParam(value="reason", required=false) String reason, 
			                                   HttpServletRequest request) throws Exception {
		
		
		User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		Requirement req   =null;
		RequirementSubmission  requirementSubmission =null;
		RequirementOffers  requirementOffers =null;
		
		requirementSubmission = requirementSubmissionService.getRequirementSubmissionById(submissionId);
		requirementOffers  = requirementOffersService.getRequirementOfferSubmissionById(requirementSubmission.getId());

	    offerStatus  = requirementOffersService.acceptOrRejectOffer(submissionId, requirementOffers.getId(), loginUser, offerStatus, reason, orgLocations);
		 
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
	@RequestMapping(value = "/downloadOfferAttachment", method = RequestMethod.GET)
    public void downloadOfferAttachment(@RequestParam(value="offerId", required=false) Long offerId ,HttpServletResponse response) throws Exception {
		
		
		   RequirementOffers requirementOffers  =requirementOffersService.getRequirementOfferId(offerId);
		
		    response.setContentType("application/octet-stream");
	        response.setHeader("Content-Disposition",
	                "attachment;filename="+requirementOffers.getFileName());
		    
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
	
	@RequestMapping(value = "/acceptOrRejectOffer")
    @ResponseBody  String acceptOrRejectOffer(@RequestParam(value="offerStatus", required=false) String offerStatus,
                           @RequestParam(value = "submissionId", required = false) Long submissionId,
                           HttpServletRequest request) throws Exception {

			 String setofferStatus = "";
            RequirementOffers requirementOffers = requirementOffersService.getRequirementOfferSubmissionById(submissionId);

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
                    
            }
            
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
														  HttpServletRequest request) throws Exception {
		
			User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			
	        requirementOffersService.sendNotificationAboutRequirementOfferMade(submissionId, offerId, loginUser, orgLocations,notificationtype);
			return "success";
	    
	}

	
	
	
}
