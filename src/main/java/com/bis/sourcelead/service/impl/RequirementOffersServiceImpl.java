package com.bis.sourcelead.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.bis.sourcelead.entity.EmailAttachment;
import com.bis.sourcelead.entity.EmailMessage;
import com.bis.sourcelead.entity.EmailRecipient;
import com.bis.sourcelead.entity.EmailScenario;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.Requirement;
import com.bis.sourcelead.entity.RequirementInterview;
import com.bis.sourcelead.entity.RequirementOffers;
import com.bis.sourcelead.entity.RequirementSubmission;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.enums.ApplicantSubmissionStatus;
import com.bis.sourcelead.service.EmailScenarioService;
import com.bis.sourcelead.service.RequirementOffersService;
import com.bis.sourcelead.service.RequirementService;
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
	private RequirementService requirementService;
	
	@Override
	public void saveRequirementOffers(JSONObject jsonObj, User user, OrgLocations orgLocations,MultipartHttpServletRequest request) throws Exception {
		
		
		String maxAdminTimeTypeRowNum = jsonObj.getString("maxOfferRowNum");
		int maxCount = Integer.parseInt(maxAdminTimeTypeRowNum);
		RequirementInterview requirementInterview  = null;
		
		RequirementOffers requirementOffers = null;
		
		
		for (long c = 1; c < maxCount; c++) {
				if(jsonObj.has(c+"applicant")){
						
						String applicantslist =null;
						try{
							JSONArray jsonArray = jsonObj.getJSONArray(c+"applicant");
							applicantslist = jsonArray.toString();
						}
						catch(JSONException e){
							applicantslist =jsonObj.getString(c+"applicant");
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
								requirementOffers.setCreatedBy(user.getUserCode());
								requirementOffers.setUpdatedDate(new Date());
								requirementOffers.setUpdatedBy(user.getUserCode());
					            requirementOffersDao.storeRequirementOffers(requirementOffers);
					           
					           RequirementSubmission requirementSubmission = requirementSubmissionService.getRequirementSubmissionById(Long.parseLong(applicants[i].replace("\"", "")));
					           requirementSubmission.setSubmissionStatus(ApplicantSubmissionStatus.OFFERMADE);
					           
								RequirementSubmission sourcesubmission = requirementSubmissionService.getSubmissionsBySourceSubmissionId(requirementSubmission.getId(), orgLocations.getId());
                                if(sourcesubmission !=null ){
                                	sourcesubmission.setSubmissionStatus(ApplicantSubmissionStatus.OFFERMADE);
                                }
					            requirementSubmissionService.storeRequirementSubmission(requirementSubmission);
					            
					            if(jsonObj.has("notifyToVendor")){
					            User loginUser= user;
								List<Requirement> vendorRequirement = requirementService.getRequirementListById(requirementSubmission.getRequirement().getId());
								Requirement clientRequirement= requirementService.getRequirementByRequirementId(requirementSubmission.getRequirement().getId());
								offerMailToVendors(loginUser,clientRequirement,vendorRequirement);
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
    public void offerMailToVendors(User loginUser,Requirement clientrequirement,List<Requirement> vendorRequirement) throws Exception {
		for(Requirement req :vendorRequirement){
		
            EmailMessage emailMessage = new EmailMessage();
            emailMessage.setFirstName(loginUser.getFirstName());
            emailMessage.setLastName(loginUser.getLastName());
            emailMessage.setToEmail(req.getSpocId().getSpocUser().getEmail());
            emailMessage.setFromEmail("peopleaxisindia@gmail.com");
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
            emailScenerioUtil.sendEmail(emailMessage, emailRecipientList, new ArrayList<EmailAttachment>());
		}
}
}
