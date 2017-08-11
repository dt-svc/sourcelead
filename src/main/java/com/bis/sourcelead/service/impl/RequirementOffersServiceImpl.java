package com.bis.sourcelead.service.impl;

import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;

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
import com.bis.sourcelead.dao.MasterCandidateDao;
import com.bis.sourcelead.dao.OrgRelationsDao;
import com.bis.sourcelead.dao.OrgSpocDao;
import com.bis.sourcelead.dao.RequirementOffersDao;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.Contracts;
import com.bis.sourcelead.entity.EmailAttachment;
import com.bis.sourcelead.entity.EmailMessage;
import com.bis.sourcelead.entity.EmailRecipient;
import com.bis.sourcelead.entity.EmailScenario;
import com.bis.sourcelead.entity.MasterCandidate;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgRelations;
import com.bis.sourcelead.entity.OrgSpoc;
import com.bis.sourcelead.entity.Requirement;
import com.bis.sourcelead.entity.RequirementOfferHistory;
import com.bis.sourcelead.entity.RequirementOffers;
import com.bis.sourcelead.entity.RequirementSubmission;
import com.bis.sourcelead.entity.RequirementSubmissionHistory;
import com.bis.sourcelead.entity.RequirementSubmissionTemplateFields;
import com.bis.sourcelead.entity.Timezone;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.enums.ApplicantSubmissionStatus;
import com.bis.sourcelead.service.CandidateService;
import com.bis.sourcelead.service.ContractsTemplateService;
import com.bis.sourcelead.service.EmailScenarioService;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.RequirementOfferHistoryService;
import com.bis.sourcelead.service.RequirementOffersService;
import com.bis.sourcelead.service.RequirementService;
import com.bis.sourcelead.service.RequirementSubmissionHistoryService;
import com.bis.sourcelead.service.RequirementSubmissionService;
import com.bis.sourcelead.service.RequirementSubmissionTemplateFieldsService;
import com.bis.sourcelead.service.TimeZoneService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.ConversionUtil;
import com.bis.sourcelead.util.EmailScenerioUtil;
import com.bis.sourcelead.util.SourceLeadRandomCodeHelper;
import com.bis.sourcelead.web.LoginSessionController;

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
	TimeZoneService timeZoneService;
	
	@Autowired
	private RequirementOffersDao requirementOffersDao;
	
	@Autowired
	 private EmailScenerioUtil emailScenerioUtil;
	
	@Autowired
	private EmailScenarioService emailScenarioService;
	
	@Autowired
	private ConversionUtil conversionUtil;
	
	@Value("${sourceleadUrl}")
    private String sourceleadUrl;
	
	@Autowired
	RequirementOfferHistoryService requirementOfferHistoryService;
	
	@Autowired
	private CandidateService candidateService;
	@Autowired
	private RequirementService requirementService;
	
	@Autowired
	private RequirementSubmissionHistoryService requirementSubmissionHistoryService;
	
	@Autowired
	private RequirementSubmissionTemplateFieldsService requirementSubmissionTemplateFieldsService;
	
	@Autowired
	private ContractsTemplateService contractsTemplateService;
	
	@Autowired
	OrgLocationService orgLocationService;
	
	@Autowired
	MasterCandidateDao masterCandidateDao;
	
	@Autowired
	OrgRelationsDao orgRelationsDao;
	
	@Autowired
	OrgSpocDao orgSpocDao;

	
	@Override
	public void saveRequirementOffers(JSONObject jsonObj, User loginUser, OrgLocations orgLocations,MultipartHttpServletRequest request) throws Exception {
		
		
		String maxAdminTimeTypeRowNum = jsonObj.getString("maxOfferRowNum");
		int maxCount = Integer.parseInt(maxAdminTimeTypeRowNum);
		
		RequirementOffers requirementOffers = null;
		//SimpleDateFormat simpleDateFormat = orgLocations.getLocFullFormDateFormatter();
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
									requirementOffers.setDateOfJoining(commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),jsonObj.getString(c+"joiningDate")));
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
								
								if(jsonObj.has(c+"JobOtherType") && !jsonObj.getString(c+"JobOtherType").isEmpty()){
									requirementOffers.setOtherPositionType(jsonObj.getString(c+"JobOtherType"));
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
                               
                                requirementOffers.setOfferUUID(SourceLeadRandomCodeHelper.generateRandomUUID());
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
					           requirementSubmission.setUpdatedDate(commonUtil.currentDate());
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
		                                	sourcesubmission.setUpdatedDate(commonUtil.currentDate());
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
            emailMessage.setToEmail(clientrequirement.getSpocId().getVendorUser().getEmail());
            emailMessage.setFromEmail(loginUser.getEmail());
            emailMessage.setCreatedBy(loginUser.getUserCode());
            StringBuilder nameBuilder=new StringBuilder();
            emailMessage.setFromName(nameBuilder.append(loginUser.getFirstName()).append(" ").append(loginUser.getLastName()).toString());
            emailMessage.setEmailType(Constants.CUSTOM_USER_EMAIL);
            // setting the class filed values
            Map<String, String> globalVarsMap = new HashMap<String, String>();
            
            if (clientrequirement.getOrgLocationId().getOrgLocationName() != null && !"".equalsIgnoreCase(clientrequirement.getOrgLocationId().getOrgLocationName())) {
                    globalVarsMap.put("OrgnanizationName",clientrequirement.getOrgLocationId().getOrgLocationName());
            } else {
                    globalVarsMap.put("OrgnanizationName", "--");
            }
            
            if (clientrequirement.getSpocId().getVendorUser().getFirstName() != null && !"".equalsIgnoreCase(clientrequirement.getSpocId().getVendorUser().getFirstName())) {
                globalVarsMap.put("vendorContact",clientrequirement.getSpocId().getVendorUser().getFirstName());
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
            if (loginOrgLocations.getOrgLocationName() != null && !"".equalsIgnoreCase(loginOrgLocations.getOrgLocationName())) {
                globalVarsMap.put("clientOrgnanizationName", loginOrgLocations.getOrgLocationName());
            } else {
                globalVarsMap.put("clientOrgnanizationName", "--");
            }
            
            StringBuilder linkSB=new StringBuilder();
            globalVarsMap.put("link",linkSB.append("<a href='").append(sourceleadUrl).append("/").append("' style='text-decoration:none; '><div style=' width:220px; border-radius:30px; background-color:#214b90; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Login</div></a>").toString());

            // setting the Recipient information
            InternetAddress[] iAdressArray = InternetAddress.parse(emailMessage.getToEmail());

            // getting the current EmailScenario by EmailScenario unique name and
            // entity type
            EmailScenario emailScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.OFFER_MAIL_TO_VENDORS, Constants.ENTITY_TYPE_EMAIL);
            EmailScenario notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.OFFER_MAIL_TO_VENDORS, Constants.ENTITY_TYPE_NOTIFICATION);


            // covert subject,body and signature
            String subject = emailScenerioUtil.bulidSubject(emailScenario.getSubject(), globalVarsMap);
            String body = emailScenerioUtil.buildTempBody(emailScenario.getBody(), globalVarsMap);
            String signature = emailScenerioUtil.buildSignature(emailScenario.getSignature(), globalVarsMap);
            String url = sourceleadUrl;
            
            String notificationbody = emailScenerioUtil.buildTempBody(notificationScenario.getBody(), globalVarsMap);
           notificationScenario.setBody(notificationbody);


            // construct HTML template using required data
            String emailTemplate = emailScenerioUtil.AssembleEmailMessage(emailScenario, emailMessage, subject, body, signature, url, loginOrgLocations.getOrg(),loginOrgLocations);
            emailMessage.setSubject(subject);
            emailMessage.setMessageBody(emailTemplate);

            // calling the sendEmail method of emailScenerioUtil
            
            try {
    			
                emailScenerioUtil.sendEmail(emailMessage,loginOrgLocations.getId(), iAdressArray, new ArrayList<EmailAttachment>());
                String actionLink="";
               OrgLocations vendorLocation = clientrequirement.getOrgLocationId();
				emailScenerioUtil.sendNotification(actionLink, loginUser, clientrequirement.getSpocId().getVendorUser(), vendorLocation, notificationScenario);
        		}catch (MessagingException e) {
        			logger.error(ExceptionUtils.getFullStackTrace(new RuntimeException(e)));
        		} catch (UnsupportedEncodingException e) {
        			logger.error(ExceptionUtils.getFullStackTrace(e));
        		}
            
            
}
	
	@Override
    public void addOfferMailScenario(User loginUser,RequirementSubmission applicant,OrgLocations orgLocations,RequirementOffers requirementOffers,Candidate candidate) throws Exception {
		RequirementSubmissionTemplateFields requirementSubmissionTemplateFields = requirementSubmissionTemplateFieldsService.getSubmissionsTemplateFieldsByRequirementSubmissionsId(applicant.getId());
            EmailMessage emailMessage = new EmailMessage();
            emailMessage.setFirstName(loginUser.getFirstName());
            emailMessage.setLastName(loginUser.getLastName());
            emailMessage.setToEmail(requirementSubmissionTemplateFields.getEmailId());
            emailMessage.setFromEmail(loginUser.getEmail());
            emailMessage.setCreatedBy(loginUser.getUserCode());
            StringBuilder attachmentBuilder=new StringBuilder();
            emailMessage.setFromName(attachmentBuilder.append(loginUser.getFirstName()).append(" ").append(loginUser.getLastName()).toString());
            emailMessage.setEmailType(Constants.CUSTOM_USER_EMAIL);
            //SimpleDateFormat simpleDateFormat = orgLocations.getLocFullFormDateFormatter();
            // setting the class filed values
            Map<String, String> globalVarsMap = new HashMap<String, String>();
            
            if (orgLocations.getOrgLocationName() != null && !"".equalsIgnoreCase(orgLocations.getOrgLocationName())) {
                    globalVarsMap.put("organizationName",orgLocations.getOrgLocationName());
            } else {
                    globalVarsMap.put("organizationName", "--");
            }
            
            if (requirementOffers.getPositionType() != null && !"".equalsIgnoreCase(requirementOffers.getPositionType())) {
                globalVarsMap.put("positionType",requirementOffers.getPositionType());
	        } else {
	                globalVarsMap.put("positionType", "--");
	        }
            
           if (applicant.getRequirement().getJobId() != null && !"".equalsIgnoreCase(applicant.getRequirement().getJobId())) {
            	StringBuilder sbv = new StringBuilder();
                globalVarsMap.put("requirementTitle",sbv.append(applicant.getRequirement().getJobId()).append("-").append(applicant.getRequirement().getJobTitle()).toString());
	        } else {
	                globalVarsMap.put("requirementTitle",applicant.getRequirement().getJobTitle());
	        }
            
            if (requirementOffers.getAmount() != null && !"".equalsIgnoreCase(requirementOffers.getAmount())) {
            	StringBuilder sb =new StringBuilder();
            	globalVarsMap.put("finalRate",sb.append(requirementOffers.getAmount()).append(" / ").append(requirementOffers.getPaymentMode()).toString());
	        } else {
	                globalVarsMap.put("finalRate", "--");
	        }
            
            if (null != requirementSubmissionTemplateFields.getFirstName() && !"".equalsIgnoreCase(requirementSubmissionTemplateFields.getFirstName()) && requirementSubmissionTemplateFields.getLastName()!= null && !"".equalsIgnoreCase(requirementSubmissionTemplateFields.getLastName()))  {
                StringBuilder sb = new StringBuilder();   
            	globalVarsMap.put("applicantName", sb.append(requirementSubmissionTemplateFields.getFirstName()).append(" ").append(requirementSubmissionTemplateFields.getLastName()).toString());
            } else {
                 globalVarsMap.put("applicantName", requirementSubmissionTemplateFields.getFirstName());
            }
            
            
            
            
            if ( requirementSubmissionTemplateFields.getDesignation() != null && !"".equalsIgnoreCase( requirementSubmissionTemplateFields.getDesignation())) {
                    globalVarsMap.put("designation", requirementSubmissionTemplateFields.getDesignation());
            } else {
                    globalVarsMap.put("designation", "--");
            }
            
    
            
            if (loginUser.getFirstName() != null && !"".equalsIgnoreCase(loginUser.getFirstName())) {
                    globalVarsMap.put("loginUser", loginUser.getFirstName());
            } else {
                    globalVarsMap.put("loginUser", "--");
            }
            
                            
            if (requirementOffers.getDateOfJoining() != null) {
                    requirementOffers.setDateOfJoiningStr(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),(requirementOffers.getDateOfJoining())));
                    globalVarsMap.put("dateofJoin",requirementOffers.getDateOfJoiningStr());
            } else {
                    globalVarsMap.put("dateofJoin", "--");
            }
            
            
        //commented by prasad salina, here i am sending offer id    
       /* globalVarsMap.put("acceptLink","<a href='"+sourceleadUrl+"/"+"acceptOrRejectOffer?offerStatus=Accepted&submissionId="+applicant.getId() +"' style='text-decoration:none; '><div style=' width:220px; border-radius:30px; background-color:#61D339 ; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Accept</div></a>");
        globalVarsMap.put("rejectLink","<a href='"+sourceleadUrl+"/"+"acceptOrRejectOffer?offerStatus=Rejected&submissionId="+applicant.getId() +"' style='text-decoration:none; '><div style=' width:220px; border-radius:30px; background-color:#D33B39; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Reject</div></a>");*/

         StringBuilder acceptLinkBuilder=new StringBuilder();
         StringBuilder rejectLinkBuilder=new StringBuilder();
         StringBuilder negotiationLinkBuilder=new StringBuilder();
         globalVarsMap.put("acceptLink",acceptLinkBuilder.append("<a href='").append(sourceleadUrl).append("/").append("acceptOrRejectRequirementOffer?offerStatus=Accepted&offerId=").append(requirementOffers.getId()).append("'style='text-decoration:none;float:left; margin-left:55px;'><div style=' width:220px; border-radius:30px; background-color:#5cb85c; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Accept</div></a>").toString());
         globalVarsMap.put("rejectLink",rejectLinkBuilder.append("<a href='").append(sourceleadUrl).append("/").append("acceptOrRejectRequirementOffer?offerStatus=Rejected&offerId=").append(requirementOffers.getId()).append("'style='text-decoration:none;float:left;margin-left:55px;'><div style=' width:220px; border-radius:30px; background-color:#e74c51; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Reject</div></a>").toString());
         globalVarsMap.put("negotiationLink",negotiationLinkBuilder.append("<a href='").append(sourceleadUrl).append("/acceptOrRejectOrRateNegotiateApplicantRates?rateStatus=Negotiate&submissionId=").append(requirementOffers.getSubmissionId().getId()).append("'style='text-decoration:none;float:left;margin-left:55px;'><div style='width:220px; border-radius:30px; background-color:#f0ad4e;; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Negotiate Rate</div></a>").toString());
        
        StringBuilder attachmentLinkBuilder=new StringBuilder();
        if(requirementOffers.getFileName() != null && !requirementOffers.getFileName().isEmpty() ){
            globalVarsMap.put("attachment",attachmentLinkBuilder.append("<a href='").append(sourceleadUrl).append("/").append("downloadOfferAttachment?offerId=").append(requirementOffers.getId()).append("' style='text-decoration:none; '><div style=' width:220px; border-radius:30px; background-color:#214b90; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center;letter-spacing:2px;'>Attachment").append("<br/></div></a>").toString());
        }else{
            globalVarsMap.put(" ",attachmentLinkBuilder.append("<a href='").append(sourceleadUrl).append("/").append("downloadOfferAttachment?offerId=").append(requirementOffers.getId()).append("' style='text-decoration:none; '><div style=' width:220px; border-radius:30px; background-color:#214b90; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center;letter-spacing:2px;'>Attachment").append("<br/></div></a>").toString());

        }

            // setting the Recipient information
            InternetAddress[] iAdressArray = InternetAddress.parse(emailMessage.getToEmail());

            // getting the current EmailScenario by EmailScenario unique name and
            // entity type
            EmailScenario emailScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.ADD_OFFER_MAIL, Constants.ENTITY_TYPE_EMAIL);
//            EmailScenario notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.ADD_OFFER_MAIL, Constants.ENTITY_TYPE_NOTIFICATION);


            // covert subject,body and signature
            String subject = emailScenerioUtil.bulidSubject(emailScenario.getSubject(), globalVarsMap);
            String body = emailScenerioUtil.buildTempBody(emailScenario.getBody(), globalVarsMap);
            String signature = emailScenerioUtil.buildSignature(emailScenario.getSignature(), globalVarsMap);
            String url = sourceleadUrl;
            
//            String notificationbody = emailScenerioUtil.buildTempBody(notificationScenario.getSubject(), globalVarsMap);
//            notificationScenario.setSubject(notificationbody);


            // construct HTML template using required data
            String emailTemplate = emailScenerioUtil.AssembleEmailMessage(emailScenario, emailMessage, subject, body,signature, url, orgLocations.getOrg(),orgLocations);
            emailMessage.setSubject(subject);
            emailMessage.setMessageBody(emailTemplate);


    		try {
    			
            emailScenerioUtil.sendEmail(emailMessage,orgLocations.getId(), iAdressArray, new ArrayList<EmailAttachment>());
    		}catch (MessagingException e) {
    			logger.error(ExceptionUtils.getFullStackTrace(new RuntimeException(e)));
    		} catch (UnsupportedEncodingException e) {
    			logger.error(ExceptionUtils.getFullStackTrace(e));
    		}
//            String actionLink="";
//            User applicantuser = applicant.getOrgLocations().getOrg().getUser();
//			emailScenerioUtil.sendNotification(actionLink, loginUser, applicantuser, orgLocations, notificationScenario);
            
            
    }
	
	
	
	@Override
	public void offerAcceptEmailScenario(User loginUser,OrgLocations orgLocations,Requirement requirement,RequirementOffers requirementOffers,RequirementSubmission requirementSubmission)
			throws Exception {

		/*
		 * this touser is  the user who made Offer
		 */
		User touser = userService.getUserByUserCode(requirementOffers.getCreatedBy());
		RequirementSubmissionTemplateFields requirementSubmissionTemplateFields = requirementSubmissionTemplateFieldsService.getSubmissionsTemplateFieldsByRequirementSubmissionsId(requirementSubmission.getId());
	//	SimpleDateFormat simpleDateFormat = orgLocations.getLocFullFormDateFormatter();
		EmailMessage emailMessage = new EmailMessage();
		emailMessage.setFirstName(touser.getFirstName());
		emailMessage.setLastName(touser.getLastName());
		emailMessage.setToEmail(touser.getEmail());
		emailMessage.setFromEmail(loginUser.getEmail());
		StringBuilder contactBuilder=new StringBuilder();
		emailMessage.setFromName(contactBuilder.append(loginUser.getFirstName()).append(" ").append(loginUser.getLastName()).toString());
		emailMessage.setEmailType(Constants.CUSTOM_USER_EMAIL);
		// setting the class filed values
		Map<String, String> globalVarsMap = new HashMap<String, String>();
			
			if(null != requirementSubmissionTemplateFields){
				StringBuilder applicantNameBuilder=new StringBuilder();
				globalVarsMap.put("ApplicantName",applicantNameBuilder.append(requirementSubmissionTemplateFields.getFirstName()).append("").append(requirementSubmissionTemplateFields.getLastName()).toString());	
			}
			else{
				globalVarsMap.put("ApplicantName", "---");	
			}
			if(null != requirementSubmission.getRequirement()){
				globalVarsMap.put("RequirementName", requirementSubmission.getRequirement().getJobTitle());
			}
			else{
				globalVarsMap.put("RequirementName", "---");

			}
			if(null != requirementOffers.getDateOfJoining() ){
			requirementOffers.setDateOfJoiningStr(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),(requirementOffers.getDateOfJoining())));	
			globalVarsMap.put("JoinDate", requirementOffers.getDateOfJoiningStr());
			}
			else{
			globalVarsMap.put("JoinDate", "---");
			}
			if(null != requirementOffers.getAmount() && !"".equalsIgnoreCase(requirementOffers.getAmount())){
			globalVarsMap.put("Rate",requirementOffers.getAmount() );
			}
			else{
			globalVarsMap.put("Rate","---" );
	
			}
			if(null!= requirement.getParentRequirement() ){
			StringBuilder clientContactNameBuilder=new StringBuilder();
			globalVarsMap.put("ClientContactName",clientContactNameBuilder.append(touser.getFirstName()).append(" ").append(touser.getLastName()).toString());

			}
			else{
			globalVarsMap.put("ClientContactName", "---");
	
			}
			StringBuilder vendorContactBuilder=new StringBuilder();
			globalVarsMap.put("VendorContact",vendorContactBuilder.append(loginUser.getFirstName()).append(" ").append(loginUser.getLastName()).toString());
		
		
		// setting the Recipient information
		InternetAddress[] iAdressArray = InternetAddress.parse(emailMessage.getToEmail());

		// getting the current EmailScenario by EmailScenario unique name and
		// entity type
		EmailScenario emailScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.OFFERACCEPT_EMAIL, Constants.ENTITY_TYPE_EMAIL);

		// covert subject,body and signature
		String subject = emailScenerioUtil.bulidSubject(emailScenario.getSubject(), globalVarsMap);
		String body = emailScenerioUtil.buildTempBody(emailScenario.getBody(), globalVarsMap);
		String signature = emailScenerioUtil.buildSignature(emailScenario.getSignature(), globalVarsMap);
		String url = sourceleadUrl;

		// construct HTML template using required data
		String emailTemplate = emailScenerioUtil.AssembleEmailMessage(emailScenario, emailMessage, subject, body, signature, url, orgLocations.getOrg(),orgLocations);
		emailMessage.setSubject(subject);
		emailMessage.setMessageBody(emailTemplate);

		// calling the sendEmail method of emailScenerioUtil
		emailScenerioUtil.sendEmail(emailMessage,orgLocations.getId(), iAdressArray, new ArrayList<EmailAttachment>());
	}
	
	@Override
	public void offerRejectEmailScenario(User loginUser,OrgLocations loginOrgLocations,Requirement requirement,RequirementOffers requirementOffers,RequirementSubmission requirementSubmission)
			throws Exception {

		/*
		 * this touser is  the user who made Offer
		 */
		User touser = userService.getUserByUserCode(requirementOffers.getCreatedBy());
		RequirementSubmissionTemplateFields requirementSubmissionTemplateFields = requirementSubmissionTemplateFieldsService.getSubmissionsTemplateFieldsByRequirementSubmissionsId(requirementSubmission.getId());
		//SimpleDateFormat simpleDateFormat = loginOrgLocations.getLocFullFormDateFormatter();
		EmailMessage emailMessage = new EmailMessage();
		emailMessage.setFirstName(touser.getFirstName());
		emailMessage.setLastName(touser.getLastName());
		emailMessage.setToEmail(touser.getEmail());
		emailMessage.setFromEmail(loginUser.getEmail());
		StringBuilder nameStringBuilder=new StringBuilder();
		emailMessage.setFromName(nameStringBuilder.append(loginUser.getFirstName()).append(" ").append(loginUser.getLastName()).toString());
		emailMessage.setEmailType(Constants.CUSTOM_USER_EMAIL);
		// setting the class filed values
		Map<String, String> globalVarsMap = new HashMap<String, String>();
		
		if(null != requirementSubmissionTemplateFields){
			StringBuilder applicantNameBuilder=new StringBuilder();
			globalVarsMap.put("applicantName",applicantNameBuilder.append(requirementSubmissionTemplateFields.getFirstName()).append("").append(requirementSubmissionTemplateFields.getLastName()).toString());	
		}
		
		else{
			globalVarsMap.put("applicantName", "---");	
		}
		if(null != requirementSubmission.getRequirement() ){
			globalVarsMap.put("requirementName", requirementSubmission.getRequirement().getJobTitle());
		}
		else{
			globalVarsMap.put("requirementName", "---");

		}
		if(null != requirementOffers.getDateOfJoining() ){
		requirementOffers.setDateOfJoiningStr(commonUtil.dateStrFromOrgDateFormatter(loginOrgLocations.getDateFormat(),(requirementOffers.getDateOfJoining())));	
		globalVarsMap.put("joinDate", requirementOffers.getDateOfJoiningStr());
		}
		else{
		globalVarsMap.put("joinDate", "---");
		}
		if(null != requirementOffers.getAmount() && !"".equalsIgnoreCase(requirementOffers.getAmount())){
		globalVarsMap.put("rate",requirementOffers.getAmount() );
		}
		else{
		globalVarsMap.put("rate","---" );

		}
		if(null!= requirement.getParentRequirement() ){
			StringBuilder clientContactNameBuilder=new StringBuilder();
			globalVarsMap.put("clientContactName", clientContactNameBuilder.append(touser.getFirstName()).append(" " ).append(touser.getLastName()).toString());

			}	else{
			globalVarsMap.put("clientContactName", "---");
	
			}		
		StringBuilder vendorContactBuilder=new StringBuilder();
		globalVarsMap.put("vendorContact", vendorContactBuilder.append(loginUser.getFirstName()).append(" ").append(loginUser.getLastName()).toString());
		
		
		// setting the Recipient information
		InternetAddress[] iAdressArray = InternetAddress.parse(emailMessage.getToEmail());

		// getting the current EmailScenario by EmailScenario unique name and
		// entity type
		EmailScenario emailScenario = emailScenarioService .getEmailScenarioByUniqueName(Constants.OFFERREJECT_EMAIL, Constants.ENTITY_TYPE_EMAIL);

		// covert subject,body and signature
		String subject = emailScenerioUtil.bulidSubject(emailScenario.getSubject(), globalVarsMap);
		String body = emailScenerioUtil.buildTempBody(emailScenario.getBody(), globalVarsMap);
		String signature = emailScenerioUtil.buildSignature(emailScenario.getSignature(), globalVarsMap);
		String url = sourceleadUrl;

		// construct HTML template using required data
		String emailTemplate = emailScenerioUtil.AssembleEmailMessage(emailScenario, emailMessage, subject, body, signature, url, loginOrgLocations.getOrg(),loginOrgLocations);
		emailMessage.setSubject(subject);
		emailMessage.setMessageBody(emailTemplate);

		// calling the sendEmail method of emailScenerioUtil
		emailScenerioUtil.sendEmail(emailMessage,loginOrgLocations.getId(),iAdressArray, new ArrayList<EmailAttachment>());
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
								        		   sourcesubmission.setUpdatedDate(commonUtil.currentDate());
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
		    if("OFFERACCEPTED".equalsIgnoreCase(offerStatus)){
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
		    	
		    	//this condition is for candidate to Associate when offerAccepted 
		    	if(requirementOffers.getSubmissionId().getCandidate().getOrgLocationId().getId().longValue() == orgLocations.getId().longValue()){
		    		Candidate candidate = candidateService.getCandidateByCandidateId(requirementOffers.getSubmissionId().getCandidate().getId());
			    	candidate.setIsAssociate(Constants.YES);
			    	candidate.setUpdatedBy(loginUser.getUserCode());
			    	candidate.setUpdatedDate(commonUtil.currentDate());
			    	
			    	candidateService.saveOrUpdateCandidate(candidate);
			    	candidateService.saveOrUpdateCandidateElkDocument(candidate);
		    	}else{
		    		Candidate candidate = addCandidateToClientAfterOfferAccepted(requirementOffers,orgLocations);
		    		candidate.setCreatedBy(loginUser.getUserCode());
		    		candidate.setUpdatedBy(loginUser.getUserCode());
		    		candidateService.saveOrUpdateCandidate(candidate);
			    	candidateService.saveOrUpdateCandidateElkDocument(candidate);
		    	}
		    	
		    	
		    	  //Changing the status of offerTable 
			      if(requirementOffers  != null){
					    requirementOffers.setOfferStatus("OFFERACCEPTED");
					    requirementOffers.setUpdatedBy(loginUser.getUserCode());
					    requirementOffers.setUpdatedDate(commonUtil.currentDate());
					    requirementOffersDao.storeRequirementOffers(requirementOffers);
			     }
			      
			      
			     
			      if(requirementOffers.getSubmissionId().getOrgLocations().getId().longValue() != orgLocations.getId().longValue()){
			      sendOfferAcceptedMail(requirementOffers,loginUser,orgLocations);
			      }
			      
		      	  if((null != requirementOffers.getSubmissionId().getVendorLocationId() && null != requirementOffers.getSubmissionId().getSourceSubmission()) &&
		      	  ("C2C".equalsIgnoreCase(requirementOffers.getSubmissionId().getRequirement().getJobType()) || "C2H".equalsIgnoreCase(requirementOffers.getSubmissionId().getRequirement().getJobType()) || "Contract".equalsIgnoreCase(requirementOffers.getSubmissionId().getRequirement().getJobType())) ){
		      		
		      		JSONObject jsonObj = populateJsonObject(requirementOffers,loginUser,orgLocations);
		      		contractsTemplateService.saveNewContract(jsonObj, orgLocations, "DRAFT", loginUser, orgLocations.getOrg(), "NEW", null);
			      }
			      
			      
	    }else if("OFFERREJECTED".equalsIgnoreCase(offerStatus)){
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
	    		if(requirementOffers.getSubmissionId().getOrgLocations().getId().longValue() != orgLocations.getId().longValue()){
	    			sendOfferRejectedMail(requirementOffers,loginUser,orgLocations);
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
															requirementOffers.setClientName(sourcesubmission.getVendorLocationId().getOrgLocationName());
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
												requirementOffers.setClientName(sourcesubmission.getVendorLocationId().getOrgLocationName());
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


	private JSONObject populateJsonObject(RequirementOffers requirementOffers, User loginUser, OrgLocations orgLocations) throws Exception
	{
		JSONObject jsonObj = new JSONObject(); 
	//	SimpleDateFormat simpleDateFormat = orgLocations.getLocFullFormDateFormatter();
		RequirementSubmissionTemplateFields candidate = requirementSubmissionTemplateFieldsService.getSubmissionsTemplateFieldsByRequirementSubmissionsId(requirementOffers.getSubmissionId().getId());
		if(null != candidate){
		jsonObj.put("contractType", "Vendor SOW");
		
		if(null != candidate.getFirstName() && null != candidate.getLastName()){
			StringBuilder candisb = new StringBuilder();
			jsonObj.put("contractTitle", candisb.append(candidate.getFirstName()).append(" ").append(candidate.getLastName()).append(" SOW").toString());
		}else{
			StringBuilder candisb = new StringBuilder();
			jsonObj.put("contractTitle", candisb.append(candidate.getFirstName()).append(" ").append(" SOW").toString());
		}
		
		jsonObj.put("submissionOffersList", requirementOffers.getSubmissionId().getId());
		
		/*
		 * to allow default owner un-comment the below line --Pavan jampana
		 */
//		jsonObj.put("owner", loginUser.getId());
		
		jsonObj.put("orgRelationId", requirementOffers.getSubmissionId().getSourceSubmission().getRequirement().getClientId().getId());
		
		jsonObj.put("vendorContact", requirementOffers.getSubmissionId().getSourceSubmission().getRequirement().getSpocId().getVendorUser().getId());
		
		jsonObj.put("flatReward", requirementOffers.getAmount());
		
		jsonObj.put("billingRatePer", requirementOffers.getPaymentMode());
		
		jsonObj.put("billingRateType", requirementOffers.getPaymentType());
		
		
		//Date startTime =  commonUtil.getDateFormat(jsonObj.getString("startDate"));
		String currentTimeStr = commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),requirementOffers.getDateOfJoining());  
		
		jsonObj.put("startDate", currentTimeStr);
		}
		return jsonObj;
	}


	private void sendOfferAcceptedMail(RequirementOffers requirementOffers, User loginUser, OrgLocations orgLocations) throws Exception {
		User vendorContactUser = null;
		 StringBuilder sb = new StringBuilder();
		 EmailMessage emailMessage = new EmailMessage();
		 emailMessage.setFirstName(loginUser.getFirstName());
		 emailMessage.setLastName(loginUser.getLastName());
		 emailMessage.setToEmail(requirementOffers.getSubmissionId().getSourceSubmission().getRequirement().getSpocId().getClientUser().getEmail());
		 emailMessage.setFromEmail(loginUser.getEmail());
		 emailMessage.setFromName(sb.append(loginUser.getFirstName()).append(" ").append(loginUser.getLastName()).toString());
		 //emailMessage.setEmailType(Constants.CUSTOM_USER_EMAIL);

		 
		//setting the class filed values
		 Map<String, String> globalVarsMap = new HashMap<String, String>();
		 
		 globalVarsMap.put("clientContactName",requirementOffers.getSubmissionId().getSourceSubmission().getRequirement().getSpocId().getClientUser().getFirstName());
		 StringBuilder vendorName = new StringBuilder();
		 if(null != emailMessage.getLastName()){
			 globalVarsMap.put("vendorContactName",vendorName.append(emailMessage.getFirstName()).append(" ").append(emailMessage.getLastName()).toString());
		 }else{
			 globalVarsMap.put("vendorContactName",vendorName.append(emailMessage.getFirstName()).toString());
		 }
		 
		 globalVarsMap.put("offeredJoinDate",conversionUtil.getDateAsString(requirementOffers.getDateOfJoining()));
		 
		 StringBuilder applicant = new StringBuilder();
		 if(null != requirementOffers.getSubmissionId().getCandidate().getLastName()){
			 globalVarsMap.put("offeredApplicantName",applicant.append(requirementOffers.getSubmissionId().getCandidate().getFirstName()).append(" ").append(requirementOffers.getSubmissionId().getCandidate().getLastName()).toString());
		 }else{
			 globalVarsMap.put("offeredApplicantName",applicant.append(requirementOffers.getSubmissionId().getCandidate().getFirstName()).toString());
		 }
		 
		 if (requirementOffers.getSubmissionId().getRequirement().getJobId() != null && !"".equalsIgnoreCase(requirementOffers.getSubmissionId().getRequirement().getJobId())) {
				StringBuilder sbv = new StringBuilder();
				globalVarsMap.put("offeredRequirementName", sbv.append(requirementOffers.getSubmissionId().getRequirement().getJobId()).append("-").append(requirementOffers.getSubmissionId().getRequirement().getJobTitle()).toString());
			} else {
				globalVarsMap.put("offeredRequirementName", requirementOffers.getSubmissionId().getRequirement().getJobTitle());
			}
		 
		 
		 if (requirementOffers.getSubmissionId().getRequirement().getDescription() != null && !"".equalsIgnoreCase(requirementOffers.getSubmissionId().getRequirement().getDescription())) {
             globalVarsMap.put("requirementDescription",requirementOffers.getSubmissionId().getRequirement().getDescription());
	        } else {
	                globalVarsMap.put("requirementDescription","--");
	        }
		 
		 //globalVarsMap.put("offeredRequirementName",requirementOffers.getSubmissionId().getRequirement().getJobTitle());
		 globalVarsMap.put("offeredRate",requirementOffers.getAmount());
	
		 
		
		 //setting the Recipient information
		 InternetAddress[] iAdressArray = InternetAddress.parse(emailMessage.getToEmail());
		 
		 //getting the current EmailScenario by EmailScenario unique name and entity type
		 EmailScenario  emailScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.OFFER_ACCEPTED,Constants.ENTITY_TYPE_EMAIL);
		 EmailScenario  notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.OFFER_ACCEPTED_NOTIFICATION,Constants.ENTITY_TYPE_NOTIFICATION);
		
		 //covert subject,body and signature
		 String subject = emailScenerioUtil.bulidSubject(emailScenario.getSubject(),globalVarsMap);
		 String body = emailScenerioUtil.buildTempBody(emailScenario.getBody(),globalVarsMap);
		 String signature = emailScenerioUtil.buildSignature(emailScenario.getSignature(),globalVarsMap);
		 String url = sourceleadUrl;
		 
		 String notificationbody = emailScenerioUtil.buildTempBody(notificationScenario.getSubject(),globalVarsMap);
		 notificationScenario.setBody(notificationbody);

		 
		 //construct HTML template using required data
		 String emailTemplate = emailScenerioUtil.AssembleEmailMessage(emailScenario, emailMessage, subject, body,signature, url, orgLocations.getOrg(),orgLocations);
		 emailMessage.setSubject(subject);
		 emailMessage.setMessageBody(emailTemplate);
		 if(null != requirementOffers.getSubmissionId()){
			 if(null != requirementOffers.getSubmissionId().getSourceSubmission()){
		 vendorContactUser = userService.getUserById(requirementOffers.getSubmissionId().getSourceSubmission().getRequirement().getSpocId().getClientUser().getId());
		 if(null !=requirementOffers.getSubmissionId().getSourceSubmission()){
		 OrgLocations vendorOrgLoc = requirementOffers.getSubmissionId().getSourceSubmission().getRequirement().getSpocId().getClientLocationId();
				 
		 requirementOffers.getSubmissionId().getSourceSubmission().getRequirement().getSpocId().getClientUser();
		 //calling the sendEmail method of emailScenerioUtil
		 emailScenerioUtil.sendEmail(emailMessage,orgLocations.getId(),iAdressArray,new ArrayList<EmailAttachment>());
		 String actionLink="";
		 emailScenerioUtil.sendNotification(actionLink, loginUser, vendorContactUser, vendorOrgLoc, notificationScenario);
		   }
		  }
		 }
		// TODO Auto-generated method stub
		
	}
	
	private void sendOfferRejectedMail(RequirementOffers requirementOffers, User loginUser, OrgLocations orgLocations) throws Exception {
		User vendorContactUser = null;
		 StringBuilder sb = new StringBuilder();
		 EmailMessage emailMessage = new EmailMessage();
		 emailMessage.setFirstName(loginUser.getFirstName());
		 emailMessage.setLastName(loginUser.getLastName());
		 emailMessage.setToEmail(requirementOffers.getSubmissionId().getSourceSubmission().getRequirement().getSpocId().getClientUser().getEmail());
		 emailMessage.setFromEmail(loginUser.getEmail());
		 emailMessage.setFromName(sb.append(loginUser.getFirstName()).append(" ").append(loginUser.getLastName()).toString());
		 //emailMessage.setEmailType(Constants.CUSTOM_USER_EMAIL);

		 
		//setting the class filed values
		 Map<String, String> globalVarsMap = new HashMap<String, String>();
		 
		 globalVarsMap.put("clientContactName",requirementOffers.getSubmissionId().getSourceSubmission().getRequirement().getSpocId().getClientUser().getFirstName());
		 StringBuilder vendorName = new StringBuilder();
		 if(null != emailMessage.getLastName()){
			 globalVarsMap.put("vendorContactName",vendorName.append(emailMessage.getFirstName()).append(" ").append(emailMessage.getLastName()).toString());
		 }else{
			 globalVarsMap.put("vendorContactName",vendorName.append(emailMessage.getFirstName()).toString());
		 }
		 globalVarsMap.put("JoinDate",requirementOffers.getDateOfJoining().toString());
		 
		 StringBuilder applicant = new StringBuilder();
		 if(null != requirementOffers.getSubmissionId().getCandidate().getLastName()){
			 globalVarsMap.put("offerRejectedApplicantName",applicant.append(requirementOffers.getSubmissionId().getCandidate().getFirstName()).append(" ").append(requirementOffers.getSubmissionId().getCandidate().getLastName()).toString());
		 }else{
			 globalVarsMap.put("offerRejectedApplicantName",applicant.append(requirementOffers.getSubmissionId().getCandidate().getFirstName()).toString());
		 }
		 globalVarsMap.put("RequirementName",requirementOffers.getSubmissionId().getRequirement().getJobTitle());
		 globalVarsMap.put("Rate",requirementOffers.getAmount());
	
		 
		
		 //setting the Recipient information
		 InternetAddress[] iAdressArray = InternetAddress.parse(emailMessage.getToEmail());
		 
		 //getting the current EmailScenario by EmailScenario unique name and entity type
		 EmailScenario  emailScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.OFFER_REJECTED,Constants.ENTITY_TYPE_EMAIL);
		 EmailScenario  notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.OFFER_REJECTED_NOTIFICATION,Constants.ENTITY_TYPE_NOTIFICATION);
		
		 //covert subject,body and signature
		 String subject = emailScenerioUtil.bulidSubject(emailScenario.getSubject(),globalVarsMap);
		 String body = emailScenerioUtil.buildTempBody(emailScenario.getBody(),globalVarsMap);
		 String signature = emailScenerioUtil.buildSignature(emailScenario.getSignature(),globalVarsMap);
		 String url = sourceleadUrl;
		 
		 String notificationbody = emailScenerioUtil.buildTempBody(notificationScenario.getSubject(),globalVarsMap);
		 notificationScenario.setBody(notificationbody);

		 
		 //construct HTML template using required data
		 String emailTemplate = emailScenerioUtil.AssembleEmailMessage(emailScenario, emailMessage, subject, body,signature, url, orgLocations.getOrg(),orgLocations);
		 emailMessage.setSubject(subject);
		 emailMessage.setMessageBody(emailTemplate);
		 
		 if(null != requirementOffers.getSubmissionId()){
			 if(null != requirementOffers.getSubmissionId().getSourceSubmission()){
		 vendorContactUser = userService.getUserById(requirementOffers.getSubmissionId().getSourceSubmission().getRequirement().getSpocId().getClientUser().getId());
		 if(null !=requirementOffers.getSubmissionId().getSourceSubmission()){
		 OrgLocations vendorOrgLoc = requirementOffers.getSubmissionId().getSourceSubmission().getRequirement().getSpocId().getClientLocationId();
				 
		 requirementOffers.getSubmissionId().getSourceSubmission().getRequirement().getSpocId().getClientUser();
		 //calling the sendEmail method of emailScenerioUtil
		 emailScenerioUtil.sendEmail(emailMessage,orgLocations.getId(),iAdressArray,new ArrayList<EmailAttachment>());
		 String actionLink="";
		 emailScenerioUtil.sendNotification(actionLink, loginUser, vendorContactUser, vendorOrgLoc, notificationScenario);
		
		// TODO Auto-generated method stub
			 }
		 }
		}
	}

	
	@Override
	public List<RequirementOffers> getRequirementOfferSubmissionByIdAndOrgLocationId(Long submissionId, Long orgLocationId)
			throws Exception {
		return requirementOffersDao.getRequirementOfferSubmissionByIdAndOrgLocationId(submissionId,orgLocationId);
	}


	@Override
	public List<RequirementOffers> getOfferAcceptedCandidatesByOrgLocationId(Long orgLocationId) throws Exception {
		return requirementOffersDao.getOfferAcceptedCandidatesByOrgLocationId(orgLocationId);
	}


	@Override
	public RequirementOffers getRequirementOffersByUUID(String offerUUId) throws Exception {
		return requirementOffersDao.getRequirementOffersByUUID(offerUUId);
	}


	@Override
	public List<RequirementOffers> getOfferAcceptedVendorCandidatesByOrgLocationId(Long locationId) throws Exception
	{
		OrgLocations orglocation = orgLocationService.getOrgLocations(locationId);
		//SimpleDateFormat simpleDateFormat = orglocation.getLocFullFormDateFormatter();
		Timezone timezone = null;
		if (null != orglocation.getTimezone()) {
			timezone = timeZoneService.getTimezone(orglocation.getTimezone().getId());
		}
		List<RequirementOffers> offerList = requirementOffersDao.getOfferAcceptedVendorCandidatesByOrgLocationId(locationId);
		for(RequirementOffers offer : offerList){
//		offerList.forEach(offer->{
			StringBuilder sb = new StringBuilder();
			if(offer.getSubmissionId().getCandidate().getFirstName() != null && "".equalsIgnoreCase(offer.getSubmissionId().getCandidate().getFirstName()) && offer.getSubmissionId().getCandidate().getLastName() != null && "".equalsIgnoreCase(offer.getSubmissionId().getCandidate().getLastName()) ){
				sb.append(offer.getSubmissionId().getCandidate().getFirstName()).append(" ").append(offer.getSubmissionId().getCandidate().getLastName());
			}else {
				sb.append(offer.getSubmissionId().getCandidate().getFirstName());
			}
			offer.setCandidateName(sb.toString());
			offer.setClientName(offer.getSubmissionId().getVendorLocationId().getOrgLocationName());
			StringBuilder vendorContactSb = new StringBuilder();
			if(offer.getSubmissionId().getSourceSubmission() != null && offer.getSubmissionId().getSourceSubmission().getRequirement() != null && offer.getSubmissionId().getSourceSubmission().getRequirement().getSpocId() != null){
				if(offer.getSubmissionId().getSourceSubmission().getRequirement().getSpocId().getVendorUser().getLastName() != null && "".equalsIgnoreCase(offer.getSubmissionId().getSourceSubmission().getRequirement().getSpocId().getVendorUser().getLastName()))
					vendorContactSb.append(offer.getSubmissionId().getSourceSubmission().getRequirement().getSpocId().getVendorUser().getFirstName()).append(" ").append(offer.getSubmissionId().getSourceSubmission().getRequirement().getSpocId().getVendorUser().getLastName());
				else
					vendorContactSb.append(offer.getSubmissionId().getSourceSubmission().getRequirement().getSpocId().getVendorUser().getFirstName());
			}
			offer.setVendorContact(vendorContactSb.toString());
			offer.setJobTitle(offer.getSubmissionId().getRequirement().getJobTitle());
			if (offer.getDateOfJoining() != null) {
				String currentTimeStr = Constants.TZ_DF.format(offer.getDateOfJoining());
				if (timezone != null && timezone.getTimeZoneId() != null) {
					String currentDateInTimezone = CommonUtil.convertTimeZones("UTC", timezone.getTimeZoneId(), currentTimeStr);
					DateFormat TZ_DF_TS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					Date converteDate = null;
					try {
						converteDate = TZ_DF_TS.parse(currentDateInTimezone);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					currentDateInTimezone = commonUtil.dateStrFromOrgDateFormatter(orglocation.getDateFormat(),converteDate);
					offer.setDateOfJoiningStr(currentDateInTimezone);
					SimpleDateFormat localDateFormat = new SimpleDateFormat("HH:mm:ss");
					String time = localDateFormat.format(converteDate);
					offer.setDateOfJoiningStr(time);
				}
			}
			
			Contracts contracts = contractsTemplateService.getContractByReqSubmissionSowTypeAndOrgLocId(offer.getSubmissionId().getId(), orglocation, Constants.Vendor_SOW);
			if(contracts != null && contracts.getContractUUID() != null)
			offer.setContractUUID(contracts.getContractUUID());
			
			Boolean flag = contractsTemplateService.getVendorMsaCreatedOrNot(offer.getSubmissionId().getVendorLocationId().getId(), orglocation.getId());
			if(flag == true){
				offer.setVendorMsaCreated(flag);
			}
			offer.setOrgRelationId(offer.getSubmissionId().getSourceSubmission().getRequirement().getClientId().getId());
		}
		return offerList;
	}


	@Override
	public Candidate addCandidateToClientAfterOfferAccepted(RequirementOffers requirementOffers,OrgLocations orgLocations) throws Exception {
		
		Candidate candidate = candidateService.findByCandidateEmail(requirementOffers.getSubmissionId().getCandidate().getEmail(), orgLocations.getId());
		if(candidate == null){
			candidate = new Candidate();
			MasterCandidate masterCandidte = masterCandidateDao.getMasterCandidateByEmailOrPhoneNumber(requirementOffers.getSubmissionId().getCandidate().getEmail(), requirementOffers.getSubmissionId().getCandidate().getMobilePhone());
    		if(masterCandidte != null){
    			candidate.setMasterCandidateId(masterCandidte);
    		}
    		
    		
    		RequirementSubmission requirementSubmission = requirementSubmissionService.getRequirementSubmissionById(requirementOffers.getSubmissionId().getId());
    		Set<RequirementSubmissionTemplateFields> submissionTemplateFieldsList = requirementSubmission.getRequirementSubmissionTemplateFieldsList();
    		if(submissionTemplateFieldsList != null){
    			for(RequirementSubmissionTemplateFields templateFields : submissionTemplateFieldsList){
    				candidate.setFirstName(templateFields.getFirstName());
		    		candidate.setLastName(templateFields.getLastName());
		    		candidate.setEmail(templateFields.getEmailId());
		    		candidate.setMobilePhone(templateFields.getPhoneNumber());
		    		candidate.setCountry(templateFields.getCountry());
		    		candidate.setState(templateFields.getState());
		    		candidate.setCity(templateFields.getCity());
		    		candidate.setZipCode(templateFields.getZipCode());
		    		candidate.setExperienceYears(templateFields.getCandidateWorkExperienceYears().toString());
		    		candidate.setExperienceMonths(templateFields.getCandidateWorkExperienceMonths().toString());
    			}
    		}
    		candidate.setOrgLocationId(orgLocations);
    		candidate.setCandidateType("Candidate");
    		candidate.setCandidateUUID(SourceLeadRandomCodeHelper.generateRandomUUID());
    		
    		candidate.setDesignationType("C2C");
    		OrgRelations orgRelations = orgRelationsDao.getVendorOrgRelationByResponderLocationIdAndLocationId(requirementOffers.getSubmissionId().getVendorLocationId().getId(), orgLocations.getId());
    		if(orgRelations != null){
    			candidate.setOrgRelationId(orgRelations);
    			Set<OrgSpoc> orgSpocList = orgRelations.getOrgSpocList();
    			if(orgSpocList != null){
    				for(OrgSpoc orgSpoc : orgSpocList){
    					if(orgSpoc.getVendorUser().getUserCode().equalsIgnoreCase(requirementOffers.getSubmissionId().getCreatedBy())){
    						candidate.setSpocId(orgSpoc);
    					}
    				}
    			}
    		}
    		
    		candidate.setIsAssociate(Constants.YES);
    		candidate.setCreatedDate(new Date());
    		candidate.setUpdatedDate(new Date());
    		candidateService.saveOrUpdateCandidate(candidate);
	    	candidateService.saveOrUpdateCandidateElkDocument(candidate);
	    	
	    	requirementSubmission.setVendorCandidateId(requirementSubmission.getCandidate());
	    	requirementSubmission.setCandidate(candidate);
	    	requirementSubmissionService.storeRequirementSubmission(requirementSubmission);
    		
		}
		return candidate;
		
	}




}