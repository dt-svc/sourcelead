package com.bis.sourcelead.rest;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.Requirement;
import com.bis.sourcelead.entity.RequirementAssignTo;
import com.bis.sourcelead.entity.RequirementOffers;
import com.bis.sourcelead.entity.RequirementSubmission;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.enums.ApplicantSubmissionStatus;
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
			 if(requirementAssignTo!=null){
					 if(sub.getCreatedBy().equalsIgnoreCase(loginUser.getUserCode()) 
							 || (sub.getRequirement().getManagerUserId().getId() == loginUser.getId())
							 || (loginUser.getUserType().equalsIgnoreCase(Constants.ADMIN)) 
							 || (requirementAssignTo.getTeamId().getOwnerUserId().getId() == loginUser.getId())){
						
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
			 
		}
	
	
		
		resultJson = commonUtil.toJSON(list);
		return resultJson;
	}
	
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
		
		if(requirementSubmission!=null){
		    req = requirementService.getRequirementByRequirementId(requirementSubmission.getRequirement().getId());
			List<RequirementAssignTo>  requirementAssignToList = requirementAssignToService.getRequirementAssignToListByRequirementId(requirementSubmission.getRequirement().getId());
			
			if(requirementAssignToList !=null &&  requirementAssignToList.size()>0){
				for(RequirementAssignTo requirementAssignTo :requirementAssignToList){
					// if we have  Assigned any team 
						if(requirementAssignTo.getTeamId()!=null){
							if(requirementAssignTo.getAssignUserId().getUserCode().equalsIgnoreCase(requirementSubmission.getCreatedBy())
							    ||  ( loginUser.getId() == requirementAssignTo.getTeamId().getOwnerUserId().getId())
							    || (loginUser.getUserType().equalsIgnoreCase(Constants.ADMIN) )
									|| (req.getManagerUserId().getId() == loginUser.getId())){
									
									
									// This is for Cleint Submission Check  for Offer
									RequirementSubmission sourcesubmission = requirementSubmissionService.getSubmissionsBySourceSubmissionId(submissionId, orgLocations.getId());
									if(sourcesubmission!=null){
										requirementOffers  = requirementOffersService.getRequirementOfferSubmissionById(sourcesubmission.getId());
											if(requirementOffers!=null){
													if(requirementOffers.getNotifyToVendor().equalsIgnoreCase("Y")){
														requirementOffers.setDateOfJoiningStr(Constants.DF_DMY.format((requirementOffers.getDateOfJoining())));
														resultJson = commonUtil.toJSON(requirementOffers);
													}
											}
									}else{
										// This is for normal Submission Check  for Offer
										    requirementOffers  = requirementOffersService.getRequirementOfferSubmissionById(requirementSubmission.getId());
												if(requirementOffers!=null){
													requirementOffers.setDateOfJoiningStr(Constants.DF_DMY.format((requirementOffers.getDateOfJoining())));
												    resultJson = commonUtil.toJSON(requirementOffers);
												}
									}
								}
							// if we have   not  selected the Team
						}else if(requirementAssignTo.getAssignUserId().getUserCode().equalsIgnoreCase(requirementSubmission.getCreatedBy())
						    || (loginUser.getUserType().equalsIgnoreCase(Constants.ADMIN) )
							|| (req.getManagerUserId().getId() == loginUser.getId())){
						
						// This is for Cleint Submission Check  for Offer
						RequirementSubmission sourcesubmission = requirementSubmissionService.getSubmissionsBySourceSubmissionId(submissionId, orgLocations.getId());
						if(sourcesubmission!=null){
							requirementOffers  = requirementOffersService.getRequirementOfferSubmissionById(sourcesubmission.getId());
								if(requirementOffers!=null){
									if(requirementOffers.getNotifyToVendor().equalsIgnoreCase("Y")){
											requirementOffers.setDateOfJoiningStr(Constants.DF_DMY.format((requirementOffers.getDateOfJoining())));
											resultJson = commonUtil.toJSON(requirementOffers);
										}
								}
						}else{
							// This is for normal Submission Check  for Offer
							    requirementOffers  = requirementOffersService.getRequirementOfferSubmissionById(requirementSubmission.getId());
							    if(requirementOffers!=null){
							    	  requirementOffers.setDateOfJoiningStr(Constants.DF_DMY.format((requirementOffers.getDateOfJoining())));
							    	  resultJson = commonUtil.toJSON(requirementOffers);
							    }
							  
						}
						
					}
				}
			}else{
				RequirementSubmission sourcesubmission = requirementSubmissionService.getSubmissionsByVendorSubmissionIdAndvendorOrgLocationId(submissionId, orgLocations.getId());
				if(sourcesubmission!=null){
					requirementOffers  = requirementOffersService.getRequirementOfferSubmissionById(sourcesubmission.getId());
						if(requirementOffers.getNotifyToVendor().equalsIgnoreCase("Y")){
							requirementOffers.setDateOfJoiningStr(Constants.DF_DMY.format((requirementOffers.getDateOfJoining())));
							resultJson = commonUtil.toJSON(requirementOffers);
						}
				}
				
			}
			
		}
	
		return resultJson;
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

		
		if(offerStatus.equalsIgnoreCase("ACCEPT")){
			
			  // This is for Cleint Submission Check  for Offer
			  RequirementSubmission sourcesubmission = requirementSubmissionService.getSubmissionsByVendorSubmissionIdAndvendorOrgLocationId(requirementSubmission.getId(), orgLocations.getId());
				if(sourcesubmission!=null){
					  
					  // This is  if the vendor accepts the Offer 
					   //changing the vendor submitted     Candidate status  
					    sourcesubmission.setSubmissionStatus(ApplicantSubmissionStatus.OFFERACCEPTED);
					    requirementSubmissionService.storeRequirementSubmission(sourcesubmission);
					    
					 
					    // This is  if the Client accepts the Offer 
					   //changing the ClientOfferMade    Candidate status  
					    requirementSubmission.setSubmissionStatus(ApplicantSubmissionStatus.OFFERACCEPTED);
					    requirementSubmissionService.storeRequirementSubmission(requirementSubmission);
					    
					    // for saving  the offerStatus in Requirement Offer Table
					    requirementOffers  = requirementOffersService.getRequirementOfferSubmissionById(sourcesubmission.getId());
					    requirementOffers.setOfferStatus("OFFERACCEPTED");
					    requirementOffersService.saveOfferStatus(requirementOffers);
					  
					
				}else{
				    RequirementSubmission sourcesub  = requirementSubmissionService.getSubmissionsBySourceSubmissionId(submissionId, orgLocations.getId());
				    if(sourcesub!=null){
				    	 // This is  if the Own Org member accepts the Offer
						sourcesub.setSubmissionStatus(ApplicantSubmissionStatus.OFFERACCEPTED);
				        requirementSubmissionService.storeRequirementSubmission(sourcesub);
				    }
				   
				    
				     // This is  if the Client accepts the Offer 
					   //changing the ClientOfferMade    Candidate status  
					    requirementSubmission.setSubmissionStatus(ApplicantSubmissionStatus.OFFERACCEPTED);
					    requirementSubmissionService.storeRequirementSubmission(requirementSubmission);
					    
				    
				    // for saving  the offerStatus in Requirement Offer Table
				    requirementOffers  = requirementOffersService.getRequirementOfferSubmissionById(requirementSubmission.getId());
				    requirementOffers.setOfferStatus("OFFERACCEPTED");
				  
				    requirementOffersService.saveOfferStatus(requirementOffers);
				}
			
			
			
			
			// Mail Logic here  for Accept Offer
			
		}else if(offerStatus.equalsIgnoreCase("REJECT")){
			     if(requirementOffers!=null){
			    	 
			    	 
			    	 // This is for Cleint Submission Check  for Offer
					  RequirementSubmission sourcesubmission = requirementSubmissionService.getSubmissionsByVendorSubmissionIdAndvendorOrgLocationId(requirementSubmission.getId(), orgLocations.getId());
						if(sourcesubmission!=null){
							  
							  // This is  if the vendor accepts the Offer 
							   //changing the vendor submitted     Candidate status  
							    sourcesubmission.setSubmissionStatus(ApplicantSubmissionStatus.OFFERREJECTED);
							    requirementSubmissionService.storeRequirementSubmission(sourcesubmission);
							    
							 
							    // This is  if the Client accepts the Offer 
							   //changing the ClientOfferMade    Candidate status  
							    requirementSubmission.setSubmissionStatus(ApplicantSubmissionStatus.OFFERREJECTED);
							    requirementSubmissionService.storeRequirementSubmission(requirementSubmission);
							    
							    // for saving  the offerStatus in Requirement Offer Table
							    requirementOffers  = requirementOffersService.getRequirementOfferSubmissionById(sourcesubmission.getId());
							    requirementOffers.setOfferStatus("OFFERACCEPTED");
							    requirementOffersService.saveOfferStatus(requirementOffers);
							  
							
						}else{
							    RequirementSubmission sourcesub  = requirementSubmissionService.getSubmissionsBySourceSubmissionId(requirementSubmission.getId(), orgLocations.getId());
							    if(sourcesub!=null){
							    	 // This is  if the Own Org member accepts the Offer
									sourcesub.setSubmissionStatus(ApplicantSubmissionStatus.OFFERREJECTED);
							        requirementSubmissionService.storeRequirementSubmission(sourcesub);
							    }
							     // This is  if the Client Rejects the Offer 
								   //changing the ClientOfferMade    Candidate status  
								    requirementSubmission.setSubmissionStatus(ApplicantSubmissionStatus.OFFERREJECTED);
								    requirementSubmissionService.storeRequirementSubmission(requirementSubmission);
								    
							    // for saving  the offerStatus in Requirement Offer Table
							    requirementOffers  = requirementOffersService.getRequirementOfferSubmissionById(requirementSubmission.getId());
							    requirementOffers.setOfferStatus("OFFERREJECTED");
							  
							    requirementOffersService.saveOfferStatus(requirementOffers);
						}
						
						
			    	 if(!"".equalsIgnoreCase(reason) && !reason.isEmpty()){
						 requirementOffers.setRejectedReason(reason);
						 // for saving offer Rejected Reason
						 requirementOffersService.saveOfferStatus(requirementOffers);
					 } 
			     }
			     
			     
			  // Mail Logic here  for Reject Offer
			  // for saving offer OFFER REJECTED Status In Submission Table
			  requirementSubmissionService.storeRequirementSubmission(requirementSubmission);
		}
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
            
            RequirementOffers requirementOffers = requirementOffersService.getRequirementOfferId(submissionId);
            RequirementSubmission requirementSubmission   =requirementSubmissionService.getRequirementSubmissionById(submissionId);
            if(offerStatus.equalsIgnoreCase("Accepted")){
                     requirementSubmission.setSubmissionStatus(ApplicantSubmissionStatus.OFFERACCEPTED);
                     requirementSubmissionService.storeRequirementSubmission(requirementSubmission);
                    requirementOffers.setOfferStatus("OFFERACCEPTED");
                    requirementOffersService.saveOfferStatus(requirementOffers);
                    
            }else if(offerStatus.equalsIgnoreCase("Rejected")){
                    requirementSubmission.setSubmissionStatus(ApplicantSubmissionStatus.OFFERREJECTED);
                    requirementSubmissionService.storeRequirementSubmission(requirementSubmission);
                    requirementOffers.setOfferStatus("OFFERREJECTED");
                    requirementOffersService.saveOfferStatus(requirementOffers);
            }
            
           return offerStatus;
    }                


	
	
}
