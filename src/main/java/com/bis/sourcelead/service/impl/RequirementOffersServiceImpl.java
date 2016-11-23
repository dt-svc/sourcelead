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
import com.bis.sourcelead.entity.RequirementOffers;
import com.bis.sourcelead.entity.RequirementSubmission;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.enums.ApplicantSubmissionStatus;
import com.bis.sourcelead.service.CandidateService;
import com.bis.sourcelead.service.EmailScenarioService;
import com.bis.sourcelead.service.RequirementAssignToService;
import com.bis.sourcelead.service.RequirementOffersService;
import com.bis.sourcelead.service.RequirementService;
import com.bis.sourcelead.service.RequirementSubmissionService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.EmailScenerioUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;

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
	private CandidateService candidateService;
	@Autowired
	private RequirementService requirementService;
	
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
								
								if(jsonObj.has("notifyToVendor") && !jsonObj.getString("notifyToVendor").isEmpty()){
									requirementOffers.setNotifyToVendor(jsonObj.getString("notifyToVendor"));
								}
								
								if(jsonObj.has("notifyToApplicant") && !jsonObj.getString("notifyToApplicant").isEmpty()){
									requirementOffers.setNotifyToApplicant(jsonObj.getString("notifyToApplicant"));
								}
                               
								
                               final MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request; 
                   		       final Map files = multiRequest.getFileMap();
                              
                   		       /* MultipartFile multiFile = null;*/
                               /*for (Iterator iter = files.values().iterator(); iter.hasNext();) {
                               	    multiFile = (MultipartFile) iter.next();
                                       String fileName = multiFile.getOriginalFilename();
                               }*/
                               MultipartFile  multiFile = (MultipartFile)files.get(c+"OfferAttachment");
                               requirementOffers.setDocFile(multiFile.getBytes());
							   requirementOffers.setFileName(multiFile.getOriginalFilename());
							   requirementOffers.setFileContentType(multiFile.getContentType());
							  
								requirementOffers.setStatus(Constants.RECORD_ACTIVE);
								requirementOffers.setCreatedDate(new Date());
								requirementOffers.setCreatedBy(loginUser.getUserCode());
								requirementOffers.setUpdatedDate(new Date());
								requirementOffers.setUpdatedBy(loginUser.getUserCode());
					            requirementOffersDao.storeRequirementOffers(requirementOffers);
					           
					           RequirementSubmission requirementSubmission = requirementSubmissionService.getRequirementSubmissionById(Long.parseLong(applicants[i].replace("\"", "")));
					           requirementSubmission.setSubmissionStatus(ApplicantSubmissionStatus.OFFERMADE);
					           requirementSubmissionService.storeRequirementSubmission(requirementSubmission);
					           
								RequirementSubmission sourcesubmission = requirementSubmissionService.getSubmissionsBySourceSubmissionId(requirementSubmission.getId(), orgLocations.getId());
                                if(sourcesubmission !=null ){
                                	sourcesubmission.setSubmissionStatus(ApplicantSubmissionStatus.OFFERMADE);
                                	requirementSubmissionService.storeRequirementSubmission(sourcesubmission);
                                }
					            
                                
                                // This is to  notify  the Vendor
					            if(jsonObj.has("notifyToVendor")  && !jsonObj.getString("notifyToVendor").isEmpty()){
									List<Requirement> vendorRequirement = requirementService.getRequirementListById(requirementSubmission.getRequirement().getId());
									Requirement clientRequirement= requirementService.getRequirementByRequirementId(requirementSubmission.getRequirement().getId());
									offerMailToVendors(loginUser,orgLocations,clientRequirement,vendorRequirement);
					            }
					            
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
    public void offerMailToVendors(User loginUser,OrgLocations loginOrgLocations,Requirement clientrequirement,List<Requirement> vendorRequirement) throws Exception {
		for(Requirement req :vendorRequirement){
		
            EmailMessage emailMessage = new EmailMessage();
            emailMessage.setFirstName(loginUser.getFirstName());
            emailMessage.setLastName(loginUser.getLastName());
            emailMessage.setToEmail(req.getSpocId().getSpocUser().getEmail());
            emailMessage.setFromEmail("peopleaxisindia@gmail.com");
            emailMessage.setCreatedBy(loginUser.getUserCode());
            emailMessage.setFromName(loginUser.getFirstName() + " " + loginUser.getLastName());

            // setting the class filed values
            Map<String, String> globalVarsMap = new HashMap<String, String>();
            
            if (req.getOrgLocationId().getOrg().getOrgName() != null && !"".equalsIgnoreCase(req.getOrgLocationId().getOrg().getOrgName())) {
                    globalVarsMap.put("OrgnanizationName",req.getOrgLocationId().getOrg().getOrgName());
            } else {
                    globalVarsMap.put("OrgnanizationName", "--");
            }
            
            if (req.getSpocId().getSpocUser().getFirstName() != null && !"".equalsIgnoreCase(req.getSpocId().getSpocUser().getFirstName())) {
                globalVarsMap.put("vendorContact",req.getSpocId().getSpocUser().getFirstName());
            } else {
                globalVarsMap.put("vendorContact", "--");
            }
            if (req.getJobTitle()!= null && !"".equalsIgnoreCase(req.getJobTitle()))  {
                    globalVarsMap.put("requirementName", req.getJobTitle());
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
            EmailScenario notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.OFFER_MAIL_TO_VENDORS, Constants.ENTITY_TYPE_NOTIFICATION);


            // covert subject,body and signature
            String subject = emailScenerioUtil.bulidSubject(emailScenario.getSubject(), globalVarsMap);
            String body = emailScenerioUtil.buildTempBody(emailScenario.getBody(), globalVarsMap);
            String signature = emailScenerioUtil.buildSignature(emailScenario.getSignature(), globalVarsMap);
            String url = "";
            
            String notificationbody = emailScenerioUtil.buildTempBody(notificationScenario.getBody(), globalVarsMap);
            notificationScenario.setBody(notificationbody);


            // construct HTML template using required data
            String emailTemplate = emailScenerioUtil.AssembleEmailMessage(emailScenario, emailMessage, subject, body,
                            signature, url, new Org());
            emailMessage.setSubject(subject);
            emailMessage.setMessageBody(emailTemplate);

            // calling the sendEmail method of emailScenerioUtil
            
            try {
    			
                emailScenerioUtil.sendEmail(emailMessage,loginOrgLocations.getId(), emailRecipientList, new ArrayList<EmailAttachment>());
                String actionLink="";
               OrgLocations vendorLocation = req.getOrgLocationId();
				emailScenerioUtil.sendNotification(actionLink, loginUser, req.getSpocId().getSpocUser(), vendorLocation, notificationScenario);
        		}catch (MessagingException e) {
        			logger.error(ExceptionUtils.getFullStackTrace(new RuntimeException(e)));
        		} catch (UnsupportedEncodingException e) {
        			logger.error(ExceptionUtils.getFullStackTrace(e));
        		}
            
            
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
            
            
        globalVarsMap.put("acceptLink","<a href='"+sourceleadUrl+"/"+"acceptOrRejectOffer?offerStatus=Accepted&submissionId="+applicant.getId() +"' style='text-decoration:none; '><div style=' width:220px; border-radius:30px; background-color:#61D339 ; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Accept</div></a>");
        
        globalVarsMap.put("rejectLink","<a href='"+sourceleadUrl+"/"+"acceptOrRejectOffer?offerStatus=Rejected&submissionId="+applicant.getId() +"' style='text-decoration:none; '><div style=' width:220px; border-radius:30px; background-color:#D33B39; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Reject</div></a>");

        
//        File logFile = new File(sLogFilename);
//        if(logFile.length() == 0)
//            System.out.println("NO ERRORS!");
//        else
//            System.out.println("SOME ERRORS!");
        
        
        if( !requirementOffers.getFileName().isEmpty() ){
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

		EmailMessage emailMessage = new EmailMessage();
		emailMessage.setFirstName(loginUser.getFirstName());
		emailMessage.setLastName(loginUser.getLastName());
		emailMessage.setToEmail(requirement.getSpocId().getSpocUser().getEmail());
		emailMessage.setFromEmail("peopleaxisindia@gmail.com");
		emailMessage.setFromName(loginUser.getFirstName()+" "+loginUser.getLastName());

		// setting the class filed values
		Map<String, String> globalVarsMap = new HashMap<String, String>();
			
			if(requirementSubmission.getCandidateName() != null && !"".equalsIgnoreCase(requirementSubmission.getCandidateName())){
				globalVarsMap.put("ApplicantName", requirementSubmission.getCandidateName());	
			}
			else{
				globalVarsMap.put("ApplicantName", "---");	
			}
			if(requirement.getJobTitle() != null && !"".equalsIgnoreCase(requirement.getJobTitle())){
				globalVarsMap.put("requirementName", requirement.getJobTitle());
			}
			else{
				globalVarsMap.put("requirementName", "---");

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
			if(requirement.getSpocId().getSpocUser().getFirstName() != null && !"".equalsIgnoreCase(requirement.getSpocId().getSpocUser().getFirstName())){
			globalVarsMap.put("ClientContactName", requirement.getSpocId().getSpocUser().getFirstName()+ " " +requirement.getSpocId().getSpocUser().getLastName());

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
		EmailScenario emailScenario = emailScenarioService
				.getEmailScenarioByUniqueName(Constants.OFFERACCEPT_EMAIL, Constants.ENTITY_TYPE_EMAIL);

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

		EmailMessage emailMessage = new EmailMessage();
		emailMessage.setFirstName(loginUser.getFirstName());
		emailMessage.setLastName(loginUser.getLastName());
		emailMessage.setToEmail(requirement.getSpocId().getSpocUser().getEmail());
		emailMessage.setFromEmail("peopleaxisindia@gmail.com");
		emailMessage.setFromName(loginUser.getFirstName()+""+loginUser.getLastName());

		// setting the class filed values
		Map<String, String> globalVarsMap = new HashMap<String, String>();
		
		if(requirementSubmission.getCandidateName() != null && !"".equalsIgnoreCase(requirementSubmission.getCandidateName())){
			globalVarsMap.put("ApplicantName", requirementSubmission.getCandidateName());	
		}
		else{
			globalVarsMap.put("ApplicantName", "---");	
		}
		if(requirement.getJobTitle() != null && !"".equalsIgnoreCase(requirement.getJobTitle())){
			globalVarsMap.put("requirementName", requirement.getJobTitle());
		}
		else{
			globalVarsMap.put("requirementName", "---");

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
		if(requirement.getSpocId().getSpocUser().getFirstName() != null && !"".equalsIgnoreCase(requirement.getSpocId().getSpocUser().getFirstName())){
			globalVarsMap.put("ClientContactName", requirement.getSpocId().getSpocUser().getFirstName()+ " " +requirement.getSpocId().getSpocUser().getLastName());

			}
			else{
			globalVarsMap.put("ClientContactName", "---");
	
			}		
		globalVarsMap.put("VendorContact", loginUser.getFirstName()+" "+loginUser.getLastName());
		
		
		// setting the Recipient information
		ArrayList<EmailRecipient> emailRecipientList = new ArrayList<EmailRecipient>();
		EmailRecipient emailRecipient = new EmailRecipient();
		emailRecipient.setEmail(emailMessage.getToEmail());
		emailRecipient.setName(emailMessage.getFirstName() + " " + emailMessage.getLastName());
		emailRecipientList.add(emailRecipient);

		// getting the current EmailScenario by EmailScenario unique name and
		// entity type
		EmailScenario emailScenario = emailScenarioService
				.getEmailScenarioByUniqueName(Constants.OFFERREJECT_EMAIL, Constants.ENTITY_TYPE_EMAIL);

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
	public void notifyToApplicantOfRequirementOfferMade(Long submissionId, Long offerId, User loginUser,OrgLocations orgLocations) throws Exception {
		
		    RequirementSubmission requirementSubmission  = requirementSubmissionService.getRequirementSubmissionById(submissionId);
				  if(requirementSubmission != null){
					  
					     RequirementOffers  requirementOffers  =  requirementOffersDao.getRequirementOfferId(offerId);
					     Candidate candidate  =  candidateService.getCandidateByCandidateId(requirementSubmission.getCandidate().getId());
							     if( requirementOffers != null){
							          addOfferMailScenario(loginUser, requirementSubmission, orgLocations, requirementOffers, candidate);
							     }
				         
				  }
		
	}


	@Override
	public String acceptOrRejectOffer(Long submissionId, Long OfferId, User loginUser, String offerStatus, String reason,
			OrgLocations orgLocations) throws Exception {
	

		
		Requirement req   =null;
		RequirementSubmission  requirementSubmission =null;
		RequirementOffers  requirementOffers =null;
		
		requirementSubmission = requirementSubmissionService.getRequirementSubmissionById(submissionId);
		requirementOffers  = requirementOffersDao.getRequirementOfferSubmissionById(requirementSubmission.getId());

		
		if(offerStatus.equalsIgnoreCase("ACCEPT")){
			
			  // This is for Cleint Submission Check  for Offer
			  RequirementSubmission sourcesubmission = requirementSubmissionService.getSubmissionsByVendorSubmissionIdAndvendorOrgLocationId(requirementSubmission.getId(), orgLocations.getId());
				if(sourcesubmission!=null){
					  
					  // This is  if the vendor accepts the Offer 
					   //changing the vendor submitted     Candidate status  
					    sourcesubmission.setSubmissionStatus(ApplicantSubmissionStatus.OFFERACCEPTED);
					    sourcesubmission.setUpdatedBy(loginUser.getUserCode());
					    sourcesubmission.setUpdatedDate(commonUtil.currentDate());
					    requirementSubmissionService.storeRequirementSubmission(sourcesubmission);
					    
					 
					    // This is  if the Client accepts the Offer 
					   //changing the ClientOfferMade    Candidate status  
					    requirementSubmission.setSubmissionStatus(ApplicantSubmissionStatus.OFFERACCEPTED);
					    requirementSubmission.setUpdatedBy(loginUser.getUserCode());
					    requirementSubmission.setUpdatedDate(commonUtil.currentDate());
					    requirementSubmissionService.storeRequirementSubmission(requirementSubmission);
					    
					    // for saving  the offerStatus in Requirement Offer Table
					    requirementOffers  = requirementOffersDao.getRequirementOfferSubmissionById(sourcesubmission.getId());
					    requirementOffers.setOfferStatus("OFFERACCEPTED");
					    requirementOffers.setUpdatedBy(loginUser.getUserCode());
					    requirementOffers.setUpdatedDate(commonUtil.currentDate());
					    requirementOffersDao.storeRequirementOffers(requirementOffers);
					  
					
				}else{
				    RequirementSubmission sourcesub  = requirementSubmissionService.getSubmissionsBySourceSubmissionId(submissionId, orgLocations.getId());
				    if(sourcesub!=null){
				    	 // This is  if the Own Org member accepts the Offer
						    sourcesub.setSubmissionStatus(ApplicantSubmissionStatus.OFFERACCEPTED);
						    sourcesub.setUpdatedBy(loginUser.getUserCode());
						    sourcesub.setUpdatedDate(commonUtil.currentDate());
				            requirementSubmissionService.storeRequirementSubmission(sourcesub);
				    }
				   
				    
				      // This is  if the Client accepts the Offer 
					   //changing the ClientOfferMade    Candidate status  
					    requirementSubmission.setSubmissionStatus(ApplicantSubmissionStatus.OFFERACCEPTED);
					    requirementSubmission.setUpdatedBy(loginUser.getUserCode());
					    requirementSubmission.setUpdatedDate(commonUtil.currentDate());
					    requirementSubmissionService.storeRequirementSubmission(requirementSubmission);
					    
					    // for saving  the offerStatus in Requirement Offer Table
					    requirementOffers  = requirementOffersDao.getRequirementOfferSubmissionById(requirementSubmission.getId());
					    requirementOffers.setOfferStatus("OFFERACCEPTED");
					    requirementOffers.setUpdatedBy(loginUser.getUserCode());
					    requirementOffers.setUpdatedDate(commonUtil.currentDate());
					    requirementOffersDao.storeRequirementOffers(requirementOffers);
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
							    sourcesubmission.setUpdatedBy(loginUser.getUserCode());
							    sourcesubmission.setUpdatedDate(commonUtil.currentDate());
							    requirementSubmissionService.storeRequirementSubmission(sourcesubmission);
							    
							 
							    // This is  if the Client accepts the Offer 
							   //changing the ClientOfferMade    Candidate status  
							    requirementSubmission.setSubmissionStatus(ApplicantSubmissionStatus.OFFERREJECTED);
							    requirementSubmission.setUpdatedBy(loginUser.getUserCode());
							    requirementSubmission.setUpdatedDate(commonUtil.currentDate());
							    requirementSubmissionService.storeRequirementSubmission(requirementSubmission);
							    
							    // for saving  the offerStatus in Requirement Offer Table
							    requirementOffers  = requirementOffersDao.getRequirementOfferSubmissionById(sourcesubmission.getId());
							    requirementOffers.setOfferStatus("OFFERACCEPTED");
							    requirementOffers.setUpdatedBy(loginUser.getUserCode());
							    requirementOffers.setUpdatedDate(commonUtil.currentDate());
							    requirementOffersDao.storeRequirementOffers(requirementOffers);
							  
							
						}else{
							    RequirementSubmission sourcesub  = requirementSubmissionService.getSubmissionsBySourceSubmissionId(requirementSubmission.getId(), orgLocations.getId());
							    if(sourcesub!=null){
							    	 // This is  if the Own Org member accepts the Offer
									sourcesub.setSubmissionStatus(ApplicantSubmissionStatus.OFFERREJECTED);
									sourcesub.setUpdatedBy(loginUser.getUserCode());
									sourcesub.setUpdatedDate(commonUtil.currentDate());
							        requirementSubmissionService.storeRequirementSubmission(sourcesub);
							    }
							     // This is  if the Client Rejects the Offer 
								   //changing the ClientOfferMade    Candidate status  
								    requirementSubmission.setSubmissionStatus(ApplicantSubmissionStatus.OFFERREJECTED);
								    requirementSubmission.setUpdatedBy(loginUser.getUserCode());
								    requirementSubmission.setUpdatedDate(commonUtil.currentDate());
								    requirementSubmissionService.storeRequirementSubmission(requirementSubmission);
								    
							    // for saving  the offerStatus in Requirement Offer Table
							    requirementOffers  = requirementOffersDao.getRequirementOfferSubmissionById(requirementSubmission.getId());
							    requirementOffers.setOfferStatus("OFFERREJECTED");
							    requirementOffers.setUpdatedBy(loginUser.getUserCode());
							    requirementOffers.setUpdatedDate(commonUtil.currentDate());
							  
							    requirementOffersDao.storeRequirementOffers(requirementOffers);
						}
						
						
			    	 if(!"".equalsIgnoreCase(reason) && !reason.isEmpty()){
						 requirementOffers.setRejectedReason(reason);
						 // for saving offer Rejected Reason
						 
						 requirementOffersDao.storeRequirementOffers(requirementOffers);
					 } 
			     }
			     
			  // Mail Logic here  for Reject Offer
			  // for saving offer OFFER REJECTED Status In Submission Table
			  requirementSubmissionService.storeRequirementSubmission(requirementSubmission);
		}
		
		
		return offerStatus;
	}


	@Override
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
	}




}
