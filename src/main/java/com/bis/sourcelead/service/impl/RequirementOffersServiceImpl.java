package com.bis.sourcelead.service.impl;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.RequirementOffersDao;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.EmailAttachment;
import com.bis.sourcelead.entity.EmailMessage;
import com.bis.sourcelead.entity.EmailRecipient;
import com.bis.sourcelead.entity.EmailScenario;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.Requirement;
import com.bis.sourcelead.entity.RequirementAssignTo;
import com.bis.sourcelead.entity.RequirementInterview;
import com.bis.sourcelead.entity.RequirementInterviewHistory;
import com.bis.sourcelead.entity.RequirementOfferHistory;
import com.bis.sourcelead.entity.RequirementOffers;
import com.bis.sourcelead.entity.RequirementSubmission;
import com.bis.sourcelead.entity.RequirementSubmissionHistory;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.enums.ApplicantSubmissionStatus;
import com.bis.sourcelead.service.CandidateService;
import com.bis.sourcelead.service.EmailScenarioService;
import com.bis.sourcelead.service.RequirementAssignToService;
import com.bis.sourcelead.service.RequirementOfferHistoryService;
import com.bis.sourcelead.service.RequirementOffersService;
import com.bis.sourcelead.service.RequirementService;
import com.bis.sourcelead.service.RequirementSubmissionHistoryService;
import com.bis.sourcelead.service.RequirementSubmissionService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.EmailScenerioUtil;

@Service
public class RequirementOffersServiceImpl implements RequirementOffersService{
	
	private static final Logger logger = LoggerFactory.getLogger(RequirementInterviewServiceImpl.class);
	
	@Autowired
	private CommonUtil commonUtil;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private RequirementSubmissionService requirementSubmissionService;

	
	@Autowired
	private RequirementOffersDao requirementOffersDao;
	
	@Autowired
	 private EmailScenerioUtil emailScenerioUtil;
	
	@Autowired
	private EmailScenarioService emailScenarioService;
	
	@Autowired
	@Value("${sourceleadUrl}")
    private String sourceleadUrl;
	
	@Autowired
	private RequirementAssignToService requirementAssignToService;
	
	@Autowired
	RequirementOfferHistoryService requirementOfferHistoryService;
	
	@Autowired
	private CandidateService candidateService;
	@Autowired
	private RequirementService requirementService;
	
	@Autowired
	private RequirementSubmissionHistoryService requirementSubmissionHistoryService;
	
	@Override
	public void saveRequirementOffers(JSONObject jsonObj, User loginUser, OrgLocations orgLocations,MultipartHttpServletRequest request) throws Exception {
		
		
		String maxAdminTimeTypeRowNum = jsonObj.getString("maxOfferRowNum");
		int maxCount = Integer.parseInt(maxAdminTimeTypeRowNum);
		RequirementInterview requirementInterview  = null;
		
		RequirementOffers requirementOffers = null;
		
		
		for (long c = 1; c < maxCount; c++) {
				if(jsonObj.has(c+"applicantId")){
						
						String applicantslist =null;
						try{
							JSONArray jsonArray = jsonObj.getJSONArray(c+"applicantId");
							applicantslist = jsonArray.toString();
						}
						catch(JSONException e){
							applicantslist =jsonObj.getString(c+"applicantId");
						}
						applicantslist = applicantslist.replaceAll("\\[", "").replaceAll("\\]","");
						applicantslist = applicantslist.replaceAll("\"", "");
						
					 	String[] applicants = applicantslist.split(",");
					
					     for(int i=0 ;i<applicants.length;i++ ){
					    	     requirementOffers  = new RequirementOffers();
								RequirementSubmission submission = new RequirementSubmission();
								submission.setId(Long.parseLong(applicants[i].replace("\"", "")));
								requirementOffers.setSubmissionId(submission);
								
								if(jsonObj.has(c+"joiningDate") && jsonObj.getString(c+"joiningDate") != null && !"".equalsIgnoreCase(jsonObj.getString(c+"joiningDate"))){
									requirementOffers.setDateOfJoining(commonUtil.getDateFormat(jsonObj.getString(c+"joiningDate")));
								}
								
								if(jsonObj.has(c+"PaymentType") && !jsonObj.getString(c+"PaymentType").isEmpty()){
									requirementOffers.setPaymentType(jsonObj.getString(c+"PaymentType"));
								}
								
								if(jsonObj.has(c+"paymentTypAmount") && !jsonObj.getString(c+"paymentTypAmount").isEmpty()){
									requirementOffers.setAmount(jsonObj.getString(c+"paymentTypAmount"));
								}
								
								if(jsonObj.has(c+"PaymentMode") && !jsonObj.getString(c+"PaymentMode").isEmpty()){
									requirementOffers.setPaymentMode(jsonObj.getString(c+"PaymentMode"));
								}
								
								if(jsonObj.has(c+"PositionType") && !jsonObj.getString(c+"PositionType").isEmpty()){
									requirementOffers.setPositionType(jsonObj.getString(c+"PositionType"));
								}
								
                               final MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request; 
                   		       final Map files = multiRequest.getFileMap();
                              
                   		       /* MultipartFile multiFile = null;*/
                               /*for (Iterator iter = files.values().iterator(); iter.hasNext();) {
                               	    multiFile = (MultipartFile) iter.next();
                                       String fileName = multiFile.getOriginalFilename();
                               }*/
                               MultipartFile  multiFile = (MultipartFile)files.get(c+"OfferAttachment");
                               if(null != multiFile && null != multiFile.getContentType() && null != multiFile.getOriginalFilename() && !"".equals(multiFile.getOriginalFilename())){
                            	   requirementOffers.setDocFile(multiFile.getBytes());
    							   requirementOffers.setFileName(multiFile.getOriginalFilename());
    							   requirementOffers.setFileContentType(multiFile.getContentType());
                               }
                               
							  
								requirementOffers.setStatus(Constants.RECORD_ACTIVE);
								requirementOffers.setOfferStatus(Constants.OFFERMADE);
								requirementOffers.setCreatedDate(new Date());
								requirementOffers.setCreatedBy(loginUser.getUserCode());
								requirementOffers.setUpdatedDate(new Date());
								requirementOffers.setUpdatedBy(loginUser.getUserCode());
								RequirementOffers updateOffer = requirementOffersDao.storeRequirementOffers(requirementOffers);
					           RequirementSubmissionHistory requirementSubmissionHistory = new RequirementSubmissionHistory();
					           RequirementSubmission requirementSubmission = requirementSubmissionService.getRequirementSubmissionById(Long.parseLong(applicants[i].replace("\"", "")));
					           requirementSubmissionHistory.setFromStage(requirementSubmission.getSubmissionStatus().getValue());
					           requirementSubmission.setSubmissionStatus(ApplicantSubmissionStatus.OFFERMADE);
					           
					           if(jsonObj.has("isClientOffer")){
					        	   requirementSubmission.setSubmissionStatus(ApplicantSubmissionStatus.CLIENT_OFFERMADE);
					           }else{
					        	   requirementSubmission.setSubmissionStatus(ApplicantSubmissionStatus.OFFERMADE);
					           }
					           requirementSubmissionService.storeRequirementSubmission(requirementSubmission);
					           
					           
					           //to save offer history
					           RequirementOfferHistory offerHistory = new RequirementOfferHistory();
					           offerHistory.setOfferId(updateOffer);
					           offerHistory.setSubmissionId(submission);
					           offerHistory.setOrgLocationId(orgLocations);
					           if(jsonObj.has("isClientOffer")){
					        	   offerHistory.setOfferSourceType(Constants.CLIENT_OFFERMADE);
					           }else{
					        	   offerHistory.setOfferSourceType(Constants.OFFERMADE);
					           }
					           if(jsonObj.has("notifyToApplicant") && !jsonObj.getString("notifyToApplicant").isEmpty()){
					        	   offerHistory.setNotifyToApplicant(jsonObj.getString("notifyToApplicant"));
					           }
					           if(jsonObj.has("notifyToVendor") && !jsonObj.getString("notifyToVendor").isEmpty()){
					        	   offerHistory.setNotifyToVendor(jsonObj.getString("notifyToVendor"));
					           }
					           
					           offerHistory.setStatus(Constants.RECORD_ACTIVE);
					           offerHistory.setCreatedDate(new Date());
					           offerHistory.setCreatedBy(loginUser.getUserCode());
					           offerHistory.setUpdatedDate(new Date());
					           offerHistory.setUpdatedBy(loginUser.getUserCode());
					           requirementOfferHistoryService.storeRequirementOfferHistory(offerHistory);
					           
					           
					           
					           if(requirementSubmission.getSourceSubmission() !=null){
					        	   RequirementSubmission sourcesubmission = requirementSubmissionService.getRequirementSubmissionById(requirementSubmission.getSourceSubmission().getId());
					        		   if(jsonObj.has("notifyToVendor") && !jsonObj.getString("notifyToVendor").isEmpty() && sourcesubmission != null){
					        			   requirementSubmissionHistory.setFromStage(requirementSubmission.getSubmissionStatus().getValue());
		                                	sourcesubmission.setSubmissionStatus(ApplicantSubmissionStatus.CLIENT_OFFERMADE);
		                                	requirementSubmissionService.storeRequirementSubmission(sourcesubmission);
		                                	
		                                	//to save offer history
		     					           RequirementOfferHistory vendorOfferHistory = new RequirementOfferHistory();
		     					           vendorOfferHistory.setOfferId(updateOffer);
							        	   vendorOfferHistory.setSubmissionId(requirementSubmission.getSourceSubmission());
							        	   vendorOfferHistory.setOrgLocationId(requirementSubmission.getVendorLocationId());
								           vendorOfferHistory.setOfferSourceType(Constants.CLIENT_OFFERMADE);
								           vendorOfferHistory.setNotifyToApplicant(Constants.NO);
								           vendorOfferHistory.setNotifyToVendor(Constants.NO);
								           vendorOfferHistory.setStatus(Constants.RECORD_ACTIVE);
								           vendorOfferHistory.setCreatedDate(new Date());
								           vendorOfferHistory.setCreatedBy(loginUser.getUserCode());
								           vendorOfferHistory.setUpdatedDate(new Date());
								           vendorOfferHistory.setUpdatedBy(loginUser.getUserCode());
		     					           requirementOfferHistoryService.storeRequirementOfferHistory(vendorOfferHistory);
		                                	
		                                	// This is to  notify  the Vendor
		                                	Requirement vendorRequirement= requirementService.getRequirementByRequirementId(sourcesubmission.getRequirement().getId());
											offerMailToVendors(loginUser,orgLocations,vendorRequirement);
					        		   }
					        	   
					           }
					           requirementSubmissionHistoryService.updateRequirementHistory(requirementSubmission, loginUser, requirementSubmissionHistory);
					            
					            // This is to  notify  the Applicant
					            if(jsonObj.has("notifyToApplicant")  && !jsonObj.getString("notifyToApplicant").isEmpty()){
								           RequirementSubmission candidatsub  = requirementSubmissionService.getRequirementSubmissionById(Long.parseLong(applicants[i].replace("\"", "")));
								           Candidate candidate=candidateService.getCandidateByCandidateId(candidatsub.getCandidate().getId());
								          addOfferMailScenario(loginUser, candidatsub, orgLocations, requirementOffers, candidate);
						        }
							}
					     
				}
			continue;
		}

		
	}

	
	@Override
	public RequirementOffers getRequirementOfferSubmissionById(Long submissionId) throws Exception {
		return requirementOffersDao.getRequirementOfferSubmissionById(submissionId);
	}


	@Override
	public RequirementOffers getRequirementOfferId(Long offerId) throws Exception {
		return requirementOffersDao.getRequirementOfferId(offerId);
		
	}


	@Override
	public RequirementOffers saveOfferStatus(RequirementOffers requirementOffers) throws Exception {
		return  requirementOffersDao.storeRequirementOffers(requirementOffers);
	}
	
	@Override
    public void offerMailToVendors(User loginUser,OrgLocations loginOrgLocations,Requirement clientrequirement) throws Exception {
		
		
            EmailMessage emailMessage = new EmailMessage();
            emailMessage.setFirstName(loginUser.getFirstName());
            emailMessage.setLastName(loginUser.getLastName());
            emailMessage.setToEmail(clientrequirement.getSpocId().getSpocUser().getEmail());
            emailMessage.setFromEmail("peopleaxisindia@gmail.com");
            emailMessage.setCreatedBy(loginUser.getUserCode());
            emailMessage.setFromName(loginUser.getFirstName() + " " + loginUser.getLastName());

            // setting the class filed values
            Map<String, String> globalVarsMap = new HashMap<String, String>();
            
            if (clientrequirement.getOrgLocationId().getOrg().getOrgName() != null && !"".equalsIgnoreCase(clientrequirement.getOrgLocationId().getOrg().getOrgName())) {
                    globalVarsMap.put("OrgnanizationName",clientrequirement.getOrgLocationId().getOrg().getOrgName());
            } else {
                    globalVarsMap.put("OrgnanizationName", "--");
            }
            
            if (clientrequirement.getSpocId().getSpocUser().getFirstName() != null && !"".equalsIgnoreCase(clientrequirement.getSpocId().getSpocUser().getFirstName())) {
                globalVarsMap.put("vendorContact",clientrequirement.getSpocId().getSpocUser().getFirstName());
            } else {
                globalVarsMap.put("vendorContact", "--");
            }
            if (clientrequirement.getJobTitle()!= null && !"".equalsIgnoreCase(clientrequirement.getJobTitle()))  {
                    globalVarsMap.put("requirementName", clientrequirement.getJobTitle());
            } else {
                    globalVarsMap.put("requirementName", "--");
            }
            
            if (loginUser.getFirstName() != null && !"".equalsIgnoreCase(loginUser.getUserName())) {
                    globalVarsMap.put("loginUser", loginUser.getFirstName());
            } else {
                    globalVarsMap.put("loginUser", "--");
            }
            if (loginOrgLocations.getOrg().getOrgName() != null && !"".equalsIgnoreCase(loginOrgLocations.getOrg().getOrgName())) {
                globalVarsMap.put("clientOrgnanizationName", loginOrgLocations.getOrg().getOrgName());
            } else {
                globalVarsMap.put("clientOrgnanizationName", "--");
            }
            
            
            globalVarsMap.put("link","<a href='"+sourceleadUrl+"/"+"' style='text-decoration:none; '><div style=' width:220px; border-radius:30px; background-color:#214b90; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Login</div></a>");

            // setting the Recipient information
            ArrayList<EmailRecipient> emailRecipientList = new ArrayList<EmailRecipient>();
            EmailRecipient emailRecipient = new EmailRecipient();
            emailRecipient.setEmail(emailMessage.getToEmail());
            emailRecipient.setName(emailMessage.getFirstName() + " " + emailMessage.getLastName());
            emailRecipientList.add(emailRecipient);

            // getting the current EmailScenario by EmailScenario unique name and
            // entity type
            EmailScenario emailScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.OFFER_MAIL_TO_VENDORS, Constants.ENTITY_TYPE_EMAIL);
           // EmailScenario notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.OFFER_MAIL_TO_VENDORS, Constants.ENTITY_TYPE_NOTIFICATION);


            // covert subject,body and signature
            String subject = emailScenerioUtil.bulidSubject(emailScenario.getSubject(), globalVarsMap);
            String body = emailScenerioUtil.buildTempBody(emailScenario.getBody(), globalVarsMap);
            String signature = emailScenerioUtil.buildSignature(emailScenario.getSignature(), globalVarsMap);
            String url = "";
            
            //String notificationbody = emailScenerioUtil.buildTempBody(notificationScenario.getBody(), globalVarsMap);
           // notificationScenario.setBody(notificationbody);


            // construct HTML template using required data
            String emailTemplate = emailScenerioUtil.AssembleEmailMessage(emailScenario, emailMessage, subject, body,
                            signature, url, new Org());
            emailMessage.setSubject(subject);
            emailMessage.setMessageBody(emailTemplate);

            // calling the sendEmail method of emailScenerioUtil
            
            try {
    			
                emailScenerioUtil.sendEmail(emailMessage,loginOrgLocations.getId(), emailRecipientList, new ArrayList<EmailAttachment>());
                String actionLink="";
               OrgLocations vendorLocation = clientrequirement.getOrgLocationId();
				//emailScenerioUtil.sendNotification(actionLink, loginUser, clientrequirement.getSpocId().getSpocUser(), vendorLocation, notificationScenario);
        		}catch (MessagingException e) {
        			logger.error(ExceptionUtils.getFullStackTrace(new RuntimeException(e)));
        		} catch (UnsupportedEncodingException e) {
        			logger.error(ExceptionUtils.getFullStackTrace(e));
        		}
            
            
}
	
	@Override
    public void addOfferMailScenario(User loginUser,RequirementSubmission applicant,OrgLocations orgLocations,RequirementOffers requirementOffers,Candidate candidate) throws Exception {
            
            EmailMessage emailMessage = new EmailMessage();
            emailMessage.setFirstName(loginUser.getFirstName());
            emailMessage.setLastName(loginUser.getLastName());
            emailMessage.setToEmail(applicant.getCandidate().getEmail());
            emailMessage.setFromEmail("peopleaxisindia@gmail.com");
            emailMessage.setCreatedBy(loginUser.getUserCode());
            emailMessage.setFromName(loginUser.getFirstName() + " " + loginUser.getLastName());

            // setting the class filed values
            Map<String, String> globalVarsMap = new HashMap<String, String>();
            
            if (orgLocations.getOrg().getOrgName() != null && !"".equalsIgnoreCase(orgLocations.getOrg().getOrgName())) {
                    globalVarsMap.put("organizationName",orgLocations.getOrg().getOrgName());
            } else {
                    globalVarsMap.put("organizationName", "--");
            }
            
            
            if (applicant.getCandidate().getFirstName()!= null        && !"".equalsIgnoreCase(applicant.getCandidate().getFirstName()))  {
                    globalVarsMap.put("applicantName", applicant.getCandidate().getFirstName());
            } else {
                    globalVarsMap.put("applicantName", "--");
            }
            
            
            
            
            if ( candidate.getDesignation() != null && !"".equalsIgnoreCase( candidate.getDesignation())) {
                    globalVarsMap.put("designation", candidate.getDesignation());
            } else {
                    globalVarsMap.put("designation", "--");
            }
            
    
            
            if (loginUser.getFirstName() != null && !"".equalsIgnoreCase(loginUser.getFirstName())) {
                    globalVarsMap.put("loginUser", loginUser.getFirstName());
            } else {
                    globalVarsMap.put("loginUser", "--");
            }
            
            
                            
            if (requirementOffers.getDateOfJoining() != null) {
                    requirementOffers.setDateOfJoiningStr(Constants.DF_DMY.format((requirementOffers.getDateOfJoining())));
                    globalVarsMap.put("dateofJoin",requirementOffers.getDateOfJoiningStr());
            } else {
                    globalVarsMap.put("dateofJoin", "--");
            }
            
            
        //commented by prasad salina, here i am sending offer id    
       /* globalVarsMap.put("acceptLink","<a href='"+sourceleadUrl+"/"+"acceptOrRejectOffer?offerStatus=Accepted&submissionId="+applicant.getId() +"' style='text-decoration:none; '><div style=' width:220px; border-radius:30px; background-color:#61D339 ; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Accept</div></a>");
        globalVarsMap.put("rejectLink","<a href='"+sourceleadUrl+"/"+"acceptOrRejectOffer?offerStatus=Rejected&submissionId="+applicant.getId() +"' style='text-decoration:none; '><div style=' width:220px; border-radius:30px; background-color:#D33B39; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Reject</div></a>");*/

         globalVarsMap.put("acceptLink","<a href='"+sourceleadUrl+"/"+"acceptOrRejectOffer?offerStatus=Accepted&offerId="+requirementOffers.getId() +"' style='text-decoration:none; '><div style=' width:220px; border-radius:30px; background-color:#61D339 ; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Accept</div></a>");
         globalVarsMap.put("rejectLink","<a href='"+sourceleadUrl+"/"+"acceptOrRejectOffer?offerStatus=Rejected&offerId="+requirementOffers.getId() +"' style='text-decoration:none; '><div style=' width:220px; border-radius:30px; background-color:#D33B39; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Reject</div></a>");
        
        
        if(requirementOffers.getFileName() != null && !requirementOffers.getFileName().isEmpty() ){
            globalVarsMap.put("attachment","<a href='"+sourceleadUrl+"/"+"downloadOfferAttachment?offerId="+requirementOffers.getId()+"' style='text-decoration:none; '><div style=' width:220px; border-radius:30px; background-color:#214b90; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center;letter-spacing:2px;'>Attachment"+"<br/></div></a>");

        }else{
            globalVarsMap.put(" ","<a href='"+sourceleadUrl+"/"+"downloadOfferAttachment?offerId="+requirementOffers.getId()+"' style='text-decoration:none; '><div style=' width:220px; border-radius:30px; background-color:#214b90; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center;letter-spacing:2px;'>Attachment"+"<br/></div></a>");

        }

            
            

            // setting the Recipient information
            ArrayList<EmailRecipient> emailRecipientList = new ArrayList<EmailRecipient>();
            EmailRecipient emailRecipient = new EmailRecipient();
            emailRecipient.setEmail(emailMessage.getToEmail());
            emailRecipient.setName(emailMessage.getFirstName() + " " + emailMessage.getLastName());
            emailRecipientList.add(emailRecipient);

            // getting the current EmailScenario by EmailScenario unique name and
            // entity type
            EmailScenario emailScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.ADD_OFFER_MAIL, Constants.ENTITY_TYPE_EMAIL);
            EmailScenario notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.ADD_OFFER_MAIL, Constants.ENTITY_TYPE_NOTIFICATION);


            // covert subject,body and signature
            String subject = emailScenerioUtil.bulidSubject(emailScenario.getSubject(), globalVarsMap);
            String body = emailScenerioUtil.buildTempBody(emailScenario.getBody(), globalVarsMap);
            String signature = emailScenerioUtil.buildSignature(emailScenario.getSignature(), globalVarsMap);
            String url = "";
            
            String notificationbody = emailScenerioUtil.buildTempBody(notificationScenario.getSubject(), globalVarsMap);
            notificationScenario.setSubject(notificationbody);


            // construct HTML template using required data
            String emailTemplate = emailScenerioUtil.AssembleEmailMessage(emailScenario, emailMessage, subject, body,signature, url, new Org());
            emailMessage.setSubject(subject);
            emailMessage.setMessageBody(emailTemplate);


    		try {
    			
            emailScenerioUtil.sendEmail(emailMessage,orgLocations.getId(), emailRecipientList, new ArrayList<EmailAttachment>());
    		}catch (MessagingException e) {
    			logger.error(ExceptionUtils.getFullStackTrace(new RuntimeException(e)));
    		} catch (UnsupportedEncodingException e) {
    			logger.error(ExceptionUtils.getFullStackTrace(e));
    		}
            String actionLink="";
            User applicantuser = applicant.getOrgLocations().getOrg().getUser();
			emailScenerioUtil.sendNotification(actionLink, loginUser, applicantuser, orgLocations, notificationScenario);
            
            
    }
	
	
	
	@Override
	public void offerAcceptEmailScenario(User loginUser,OrgLocations orgLocations,Requirement requirement,RequirementOffers requirementOffers,RequirementSubmission requirementSubmission)
			throws Exception {

		/*
		 * this touser is  the user who made Offer
		 */
		User touser = userService.getUserByUserCode(requirementOffers.getCreatedBy());
		
		EmailMessage emailMessage = new EmailMessage();
		emailMessage.setFirstName(touser.getFirstName());
		emailMessage.setLastName(touser.getLastName());
		emailMessage.setToEmail(touser.getEmail());
		emailMessage.setFromEmail("peopleaxisindia@gmail.com");
		emailMessage.setFromName(loginUser.getFirstName()+" "+loginUser.getLastName());

		// setting the class filed values
		Map<String, String> globalVarsMap = new HashMap<String, String>();
			
			if(requirementSubmission.getCandidate() != null){
				globalVarsMap.put("ApplicantName", requirementSubmission.getCandidate().getFirstName() +""+requirementSubmission.getCandidate().getLastName());	
			}
			else{
				globalVarsMap.put("ApplicantName", "---");	
			}
			if(requirementSubmission.getRequirement() != null){
				globalVarsMap.put("RequirementName", requirementSubmission.getRequirement().getJobTitle());
			}
			else{
				globalVarsMap.put("RequirementName", "---");

			}
			if(requirementOffers.getDateOfJoining() != null ){
			requirementOffers.setDateOfJoiningStr(Constants.DF_DMY.format((requirementOffers.getDateOfJoining())));	
			globalVarsMap.put("JoinDate", requirementOffers.getDateOfJoiningStr());
			}
			else{
			globalVarsMap.put("JoinDate", "---");
			}
			if(requirementOffers.getAmount() != null && !"".equalsIgnoreCase(requirementOffers.getAmount())){
			globalVarsMap.put("Rate",requirementOffers.getAmount() );
			}
			else{
			globalVarsMap.put("Rate","---" );
	
			}
			if(requirement.getParentRequirement()!= null ){
			globalVarsMap.put("ClientContactName", touser.getFirstName()+ " " +touser.getLastName());

			}
			else{
			globalVarsMap.put("ClientContactName", "---");
	
			}
			globalVarsMap.put("VendorContact",loginUser.getFirstName()+" "+loginUser.getLastName() );
		
		
		// setting the Recipient information
		ArrayList<EmailRecipient> emailRecipientList = new ArrayList<EmailRecipient>();
		EmailRecipient emailRecipient = new EmailRecipient();
		emailRecipient.setEmail(emailMessage.getToEmail());
		emailRecipient.setName(emailMessage.getFirstName() + " " + emailMessage.getLastName());
		emailRecipientList.add(emailRecipient);

		// getting the current EmailScenario by EmailScenario unique name and
		// entity type
		EmailScenario emailScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.OFFERACCEPT_EMAIL, Constants.ENTITY_TYPE_EMAIL);

		// covert subject,body and signature
		String subject = emailScenerioUtil.bulidSubject(emailScenario.getSubject(), globalVarsMap);
		String body = emailScenerioUtil.buildTempBody(emailScenario.getBody(), globalVarsMap);
		String signature = emailScenerioUtil.buildSignature(emailScenario.getSignature(), globalVarsMap);
		String url = "";

		// construct HTML template using required data
		String emailTemplate = emailScenerioUtil.AssembleEmailMessage(emailScenario, emailMessage, subject, body,
				signature, url, new Org());
		emailMessage.setSubject(subject);
		emailMessage.setMessageBody(emailTemplate);

		// calling the sendEmail method of emailScenerioUtil
		emailScenerioUtil.sendEmail(emailMessage,orgLocations.getId(), emailRecipientList, new ArrayList<EmailAttachment>());
	}
	
	@Override
	public void offerRejectEmailScenario(User loginUser,OrgLocations loginOrgLocations,Requirement requirement,RequirementOffers requirementOffers,RequirementSubmission requirementSubmission)
			throws Exception {

		/*
		 * this touser is  the user who made Offer
		 */
		User touser = userService.getUserByUserCode(requirementOffers.getCreatedBy());
		
		EmailMessage emailMessage = new EmailMessage();
		emailMessage.setFirstName(touser.getFirstName());
		emailMessage.setLastName(touser.getLastName());
		emailMessage.setToEmail(touser.getEmail());
		emailMessage.setFromEmail("peopleaxisindia@gmail.com");
		emailMessage.setFromName(loginUser.getFirstName()+" "+loginUser.getLastName());

		// setting the class filed values
		Map<String, String> globalVarsMap = new HashMap<String, String>();
		
		if(requirementSubmission.getCandidate() != null){
			globalVarsMap.put("applicantName", requirementSubmission.getCandidate().getFirstName() +""+requirementSubmission.getCandidate().getLastName());	
		}
		
		else{
			globalVarsMap.put("applicantName", "---");	
		}
		if(requirementSubmission.getRequirement() != null ){
			globalVarsMap.put("requirementName", requirementSubmission.getRequirement().getJobTitle());
		}
		else{
			globalVarsMap.put("requirementName", "---");

		}
		if(requirementOffers.getDateOfJoining() != null ){
		requirementOffers.setDateOfJoiningStr(Constants.DF_DMY.format((requirementOffers.getDateOfJoining())));	
		globalVarsMap.put("joinDate", requirementOffers.getDateOfJoiningStr());
		}
		else{
		globalVarsMap.put("joinDate", "---");
		}
		if(requirementOffers.getAmount() != null && !"".equalsIgnoreCase(requirementOffers.getAmount())){
		globalVarsMap.put("rate",requirementOffers.getAmount() );
		}
		else{
		globalVarsMap.put("rate","---" );

		}
		if(requirement.getParentRequirement()!= null ){
			globalVarsMap.put("clientContactName", touser.getFirstName()+ " " +touser.getLastName());

			}	else{
			globalVarsMap.put("clientContactName", "---");
	
			}		
		globalVarsMap.put("vendorContact", loginUser.getFirstName()+" "+loginUser.getLastName());
		
		
		// setting the Recipient information
		ArrayList<EmailRecipient> emailRecipientList = new ArrayList<EmailRecipient>();
		EmailRecipient emailRecipient = new EmailRecipient();
		emailRecipient.setEmail(emailMessage.getToEmail());
		emailRecipient.setName(emailMessage.getFirstName() + " " + emailMessage.getLastName());
		emailRecipientList.add(emailRecipient);

		// getting the current EmailScenario by EmailScenario unique name and
		// entity type
		EmailScenario emailScenario = emailScenarioService .getEmailScenarioByUniqueName(Constants.OFFERREJECT_EMAIL, Constants.ENTITY_TYPE_EMAIL);

		// covert subject,body and signature
		String subject = emailScenerioUtil.bulidSubject(emailScenario.getSubject(), globalVarsMap);
		String body = emailScenerioUtil.buildTempBody(emailScenario.getBody(), globalVarsMap);
		String signature = emailScenerioUtil.buildSignature(emailScenario.getSignature(), globalVarsMap);
		String url = "";

		// construct HTML template using required data
		String emailTemplate = emailScenerioUtil.AssembleEmailMessage(emailScenario, emailMessage, subject, body,
				signature, url, new Org());
		emailMessage.setSubject(subject);
		emailMessage.setMessageBody(emailTemplate);

		// calling the sendEmail method of emailScenerioUtil
		emailScenerioUtil.sendEmail(emailMessage,loginOrgLocations.getId(),emailRecipientList, new ArrayList<EmailAttachment>());
	}



	@Override
	public void sendNotificationAboutRequirementOfferMade(Long submissionId, Long offerId, Long offerHistoryId, User loginUser,OrgLocations orgLocations,String notificationtype) throws Exception {
		
		    RequirementSubmission requirementSubmission  = requirementSubmissionService.getRequirementSubmissionById(submissionId);
				  if(requirementSubmission != null){
					     RequirementOffers  requirementOffers  =  requirementOffersDao.getRequirementOfferId(offerId);
					     RequirementOfferHistory offerHistory = requirementOfferHistoryService.getRequirementOfferHistoryById(offerHistoryId);
					     Candidate candidate  =  candidateService.getCandidateByCandidateId(requirementSubmission.getCandidate().getId());
							     if(requirementOffers != null && offerHistory != null){
							    	     // this is used to notify the vedor about the offer
								    	 if("Vendor".equalsIgnoreCase(notificationtype)){
								    		  offerHistory.setNotifyToVendor(Constants.YES);
											  requirementOfferHistoryService.storeRequirementOfferHistory(offerHistory);
								        	   RequirementSubmission sourcesubmission = requirementSubmissionService.getRequirementSubmissionById(requirementSubmission.getSourceSubmission().getId());
								        	   if(sourcesubmission != null){
								        		   
								        		   sourcesubmission.setSubmissionStatus(ApplicantSubmissionStatus.CLIENT_OFFERMADE);
									        	   requirementSubmissionService.storeRequirementSubmission(sourcesubmission);
								        		   
								        		   RequirementOfferHistory vendorOfferHistory = new RequirementOfferHistory();
								        	  	     vendorOfferHistory.setOfferId(requirementOffers);
								        	  	     vendorOfferHistory.setSubmissionId(requirementSubmission.getSourceSubmission());
								        	  	     vendorOfferHistory.setOrgLocationId(requirementSubmission.getVendorLocationId());
								        	         vendorOfferHistory.setOfferSourceType(Constants.CLIENT_OFFERMADE);
								        	         vendorOfferHistory.setNotifyToApplicant(Constants.NO);
								        	         vendorOfferHistory.setNotifyToVendor(Constants.NO);
								        	         vendorOfferHistory.setStatus(Constants.RECORD_ACTIVE);
								        	         vendorOfferHistory.setCreatedDate(new Date());
								        	         vendorOfferHistory.setCreatedBy(loginUser.getUserCode());
								        	         vendorOfferHistory.setUpdatedDate(new Date());
								        	         vendorOfferHistory.setUpdatedBy(loginUser.getUserCode());
								        	         requirementOfferHistoryService.storeRequirementOfferHistory(vendorOfferHistory);
								        		   
								        		    List<Requirement> clientRequirement = requirementService.getRequirementListById(requirementSubmission.getRequirement().getId());
													Requirement vendorRequirement= requirementService.getRequirementByRequirementId(sourcesubmission.getRequirement().getId());
													offerMailToVendors(loginUser,orgLocations,vendorRequirement);
								        	   }
								    	 }
							    	    // this is used to notify the Applicant about the offer
										if("Applicant".equalsIgnoreCase(notificationtype)){
											  offerHistory.setNotifyToApplicant(Constants.YES);
											  requirementOfferHistoryService.storeRequirementOfferHistory(offerHistory);
									          addOfferMailScenario(loginUser, requirementSubmission, orgLocations, requirementOffers, candidate);
										 }
							     }
				         
				  }
		
	}


	@Override
	public String acceptOrRejectOffer(Long submissionId, Long OfferId, User loginUser, String offerStatus, String reason,
			OrgLocations orgLocations) throws Exception {
		
	 
		RequirementOffers requirementOffers  = requirementOffersDao.getRequirementOfferId(OfferId);
		List<RequirementOfferHistory> requirementOfferHistories = requirementOfferHistoryService.getAllRequirementOfferHistoriesByOfferId(OfferId);
	    
	    if(requirementOfferHistories != null && !requirementOfferHistories.isEmpty() ){  
		    if(offerStatus.equalsIgnoreCase("OFFERACCEPTED")){
		    	for (RequirementOfferHistory requirementOfferHistory : requirementOfferHistories) {
		    		   RequirementSubmission requirementSubmission = requirementSubmissionService.getRequirementSubmissionById(requirementOfferHistory.getSubmissionId().getId());
			    		if(requirementOfferHistory.getOfferSourceType() != null && "OFFERMADE".equals(requirementOfferHistory.getOfferSourceType())){
			    			requirementSubmission.setSubmissionStatus(ApplicantSubmissionStatus.OFFERACCEPTED);
			    		}else{
			    			requirementSubmission.setSubmissionStatus(ApplicantSubmissionStatus.CLIENT_OFFERACCEPTED);
			    		}
			    		 requirementSubmission.setUpdatedBy(loginUser.getUserCode());
						 requirementSubmission.setUpdatedDate(commonUtil.currentDate());
						 requirementSubmissionService.storeRequirementSubmission(requirementSubmission);
				  }
		    	
		    	  //Changing the status of offerTable 
			      if(requirementOffers  != null){
					    requirementOffers.setOfferStatus("OFFERACCEPTED");
					    requirementOffers.setUpdatedBy(loginUser.getUserCode());
					    requirementOffers.setUpdatedDate(commonUtil.currentDate());
					    requirementOffersDao.storeRequirementOffers(requirementOffers);
			     }
	    }else if(offerStatus.equalsIgnoreCase("OFFERREJECTED")){
	    	for (RequirementOfferHistory requirementOfferHistory : requirementOfferHistories) {
	    		    RequirementSubmission requirementSubmission = requirementSubmissionService.getRequirementSubmissionById(requirementOfferHistory.getSubmissionId().getId());
		    		if(requirementSubmission.getSourceSubmission() == null){
		    			requirementSubmission.setSubmissionStatus(ApplicantSubmissionStatus.OFFERREJECTED);
		    		}else{
		    			requirementSubmission.setSubmissionStatus(ApplicantSubmissionStatus.CLIENT_OFFERREJECTED);
		    		}
		    		 requirementSubmission.setUpdatedBy(loginUser.getUserCode());
					 requirementSubmission.setUpdatedDate(commonUtil.currentDate());
					 requirementSubmissionService.storeRequirementSubmission(requirementSubmission);
			  }
	    	
	    	  //Changing the status of offerTable 
			  if(requirementOffers  != null ){
				    requirementOffers.setOfferStatus("OFFERREJECTED");
				    requirementOffers.setUpdatedBy(loginUser.getUserCode());
				    requirementOffers.setUpdatedDate(commonUtil.currentDate());
				    if(!"".equalsIgnoreCase(reason) && !reason.isEmpty()){
						 requirementOffers.setRejectedReason(reason);
					 } 
				    requirementOffersDao.storeRequirementOffers(requirementOffers);
		    }
	    }
	 }	
		return offerStatus;
	}


	/*@Override
	public RequirementOffers offerDetails(Long submissionId, User loginUser,OrgLocations orgLocations ,String userType) throws Exception {
		
		Requirement req   =null;
		RequirementSubmission  requirementSubmission =null;
		RequirementOffers  requirementOffers =null;
		
		requirementSubmission = requirementSubmissionService.getRequirementSubmissionById(submissionId);
		
		if(requirementSubmission!=null){
		    req = requirementService.getRequirementByRequirementId(requirementSubmission.getRequirement().getId());
			List<RequirementAssignTo>  requirementAssignToList = requirementAssignToService.getRequirementAssignToListByRequirementId(requirementSubmission.getRequirement().getId());
			
			String loginUserType =userType;
			if(requirementAssignToList !=null &&  requirementAssignToList.size()>0){
				for(RequirementAssignTo requirementAssignTo :requirementAssignToList){
					     // if we have  Assigned any team 
						if(requirementAssignTo.getTeamId()!=null){
							if(requirementAssignTo.getAssignUserId().getUserCode().equalsIgnoreCase(requirementSubmission.getCreatedBy())
							    ||  ( loginUser.getId() == requirementAssignTo.getTeamId().getOwnerUserId().getId())
							    || (loginUserType.equalsIgnoreCase(Constants.ADMIN) )
									|| (req.getManagerUserId().getId() == loginUser.getId())){
									
									// This is for vendor Submission Check  for Offer
									RequirementSubmission sourcesubmission = requirementSubmissionService.getSubmissionsBySourceSubmissionId(submissionId, orgLocations.getId());
									if(sourcesubmission!=null){
										requirementOffers  = requirementOffersDao.getRequirementOfferSubmissionById(sourcesubmission.getId());
											if(requirementOffers!=null){
													if(requirementOffers.getNotifyToVendor() !=null && requirementOffers.getNotifyToVendor().equalsIgnoreCase("Y")){
														requirementOffers.setDateOfJoiningStr(Constants.DF_DMY.format((requirementOffers.getDateOfJoining())));
														if(sourcesubmission.getVendorLocationId() !=null){
															requirementOffers.setClientName(sourcesubmission.getVendorLocationId().getOrg().getOrgName());
														}
														return requirementOffers;
													}
											}
									}else{
										// This is for normal Own clent Submission Check  for Offer
										    requirementOffers  = requirementOffersDao.getRequirementOfferSubmissionById(requirementSubmission.getId());
												if(requirementOffers!=null){
													requirementOffers.setDateOfJoiningStr(Constants.DF_DMY.format((requirementOffers.getDateOfJoining())));
													return requirementOffers;
												}
									}
								}
							
							// if we have   not  selected the Team
						}else if(requirementAssignTo.getAssignUserId().getUserCode().equalsIgnoreCase(requirementSubmission.getCreatedBy())
						    || (loginUserType.equalsIgnoreCase(Constants.ADMIN) )
							|| (req.getManagerUserId().getId() == loginUser.getId())){
						
						// This is for vendor Submission Check  for Offer
						RequirementSubmission sourcesubmission = requirementSubmissionService.getSubmissionsBySourceSubmissionId(submissionId, orgLocations.getId());
						if(sourcesubmission!=null){
							requirementOffers  = requirementOffersDao.getRequirementOfferSubmissionById(sourcesubmission.getId());
								if(requirementOffers!=null){
									if( (requirementOffers.getNotifyToVendor() != null) && (requirementOffers.getNotifyToVendor().equalsIgnoreCase("Y"))){											requirementOffers.setDateOfJoiningStr(Constants.DF_DMY.format((requirementOffers.getDateOfJoining())));
								        	requirementOffers.setDateOfJoiningStr(Constants.DF_DMY.format((requirementOffers.getDateOfJoining())));
								        	if(sourcesubmission.getVendorLocationId() !=null){
												requirementOffers.setClientName(sourcesubmission.getVendorLocationId().getOrg().getOrgName());
											}
								        	return requirementOffers;
										}
								}
						}else{
							// This is for normal cleint  Submission Check  for Offer
							    requirementOffers  = requirementOffersDao.getRequirementOfferSubmissionById(requirementSubmission.getId());
							    if(requirementOffers!=null){
							    	  requirementOffers.setDateOfJoiningStr(Constants.DF_DMY.format((requirementOffers.getDateOfJoining())));
							    	  return requirementOffers;
							    }
						}
					}
				}
			}else{
				RequirementSubmission sourcesubmission = requirementSubmissionService.getSubmissionsByVendorSubmissionIdAndvendorOrgLocationId(submissionId, orgLocations.getId());
				if(sourcesubmission!=null){
					requirementOffers  = requirementOffersDao.getRequirementOfferSubmissionById(sourcesubmission.getId());
					if(requirementOffers != null){
                        if( requirementOffers.getNotifyToVendor() != null && "Y".equalsIgnoreCase(requirementOffers.getNotifyToVendor()) && !requirementOffers.getNotifyToVendor().isEmpty()){
                            requirementOffers.setDateOfJoiningStr(Constants.DF_DMY.format((requirementOffers.getDateOfJoining())));
                            return requirementOffers;
                        }
                    }

				}
				
			}
			
		}
		return null;
	}*/


	@Override
	public List<RequirementOffers> getRequirementOfferSubmissionByIdAndOrgLocationId(Long submissionId, Long orgLocationId)
			throws Exception {
		return requirementOffersDao.getRequirementOfferSubmissionByIdAndOrgLocationId(submissionId,orgLocationId);
	}




}
