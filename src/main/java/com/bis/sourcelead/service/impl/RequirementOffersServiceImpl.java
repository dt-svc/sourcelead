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

import org.apache.commons.lang.StringUtils;
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
import com.bis.sourcelead.dao.CandidateResumesDao;
import com.bis.sourcelead.dao.MasterCandidateDao;
import com.bis.sourcelead.dao.OrgRelationsDao;
import com.bis.sourcelead.dao.OrgSpocDao;
import com.bis.sourcelead.dao.RequirementOffersDao;
import com.bis.sourcelead.dto.ContractsDTO;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.CandidateResumes;
import com.bis.sourcelead.entity.Contracts;
import com.bis.sourcelead.entity.EmailAttachment;
import com.bis.sourcelead.entity.EmailMessage;
import com.bis.sourcelead.entity.EmailScenario;
import com.bis.sourcelead.entity.MasterCandidate;
import com.bis.sourcelead.entity.OfferLetterTemplate;
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
import com.bis.sourcelead.service.OfferLetterTemplateService;
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
	
	@Value("${atsUrl}")
    private String atsUrl;
	
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
	
	@Autowired
	CandidateResumesDao candidateResumesDao;

	
	@Override
	public void saveRequirementOffers(JSONObject jsonObj, User loginUser, OrgLocations orgLocations,MultipartHttpServletRequest request) throws Exception {
		
		
		String maxAdminTimeTypeRowNum = jsonObj.getString("maxOfferRowNum");
		int maxCount = Integer.parseInt(maxAdminTimeTypeRowNum);
		
		RequirementOffers requirementOffers = null;
		RequirementOffers vendorOffers = null;
		
		 StringBuilder userName = new StringBuilder();
		 if(loginUser != null){
				if(null != loginUser.getLastName() && !"".equalsIgnoreCase(loginUser.getLastName())){
					userName = userName.append(loginUser.getFirstName()).append(" ").append(loginUser.getLastName());
				}else{
					userName = userName.append(loginUser.getFirstName());
				}
			}
		 
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
								RequirementSubmission submission = requirementSubmissionService.getRequirementSubmissionById(Long.parseLong(applicants[i].replace("\"", "")));
								requirementOffers.setSubmissionId(submission);
								if(jsonObj.has(c+"joiningDate") && jsonObj.getString(c+"joiningDate") != null && !"".equalsIgnoreCase(jsonObj.getString(c+"joiningDate"))){
									requirementOffers.setDateOfJoining(commonUtil.orgLocationDateFormat(submission.getOrgLocations().getDateFormat(),jsonObj.getString(c+"joiningDate")));
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
								
								if(!jsonObj.getString("offerExpireDate").isEmpty()) {
									requirementOffers.setOfferExpireDate(commonUtil.orgLocationDateFormat(submission.getOrgLocations().getDateFormat(),jsonObj.getString("offerExpireDate")));
								}
								
								if(!jsonObj.getString("offerTemplateId").isEmpty()) {
									OfferLetterTemplate olt = new OfferLetterTemplate();
									olt.setId(Long.valueOf(jsonObj.getString("offerTemplateId")));
									requirementOffers.setOfferLetterTemplate(olt);
								}
								
								
                               final MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request; 
                   		       final Map files = multiRequest.getFileMap();
                              
                   		  
                               MultipartFile  multiFile = (MultipartFile)files.get(c+"OfferAttachment");
                               if(null != multiFile && null != multiFile.getContentType() && null != multiFile.getOriginalFilename() && !"".equals(multiFile.getOriginalFilename())){
                            	   requirementOffers.setDocFile(multiFile.getBytes());
    							   requirementOffers.setFileName(multiFile.getOriginalFilename());
    							   requirementOffers.setFileContentType(multiFile.getContentType());
                               }
                               
								requirementOffers.setCreatedDate(new Date());
								requirementOffers.setCreatedBy(loginUser.getUserCode());
								requirementOffers.setUpdatedDate(new Date());
								requirementOffers.setUpdatedBy(loginUser.getUserCode());
								requirementOffers.setOfferUUID(SourceLeadRandomCodeHelper.generateRandomUUID());
								requirementOffers.setStatus(Constants.RECORD_ACTIVE);
								
								 if(jsonObj.has("clientOffer") && jsonObj.getString("clientOffer").equalsIgnoreCase(Constants.YES)){
									 
									 if(submission.getSourceSubmission() != null) {
										 requirementOffers.setOfferStatus(Constants.OFFERMADE); 
									 }else {
										 requirementOffers.setOfferStatus(Constants.CLIENT_OFFERMADE);
									 }
								 }else{
									 requirementOffers.setOfferStatus(Constants.OFFERMADE);
								 }
								
								RequirementOffers updateOffer = requirementOffersDao.storeRequirementOffers(requirementOffers);
								
						           //to save offer history
						           RequirementOfferHistory offerHistory = new RequirementOfferHistory();
						           offerHistory.setOfferId(updateOffer);
						           offerHistory.setOrgLocationId(submission.getOrgLocations());
					        	   offerHistory.setSubmissionId(submission);
					        	   
						           if(jsonObj.has("clientOffer") && jsonObj.getString("clientOffer").equalsIgnoreCase(Constants.YES)){
						        	
						        	   if(submission.getSourceSubmission() != null) {
						        		   offerHistory.setNotifyToVendor(Constants.YES);
							        	   offerHistory.setNotifyToApplicant(Constants.YES);
							        	   offerHistory.setOfferSourceType(Constants.OFFERMADE);
						        	   }else{
						        		   offerHistory.setOfferSourceType(Constants.CLIENT_OFFERMADE);
							        	   if(jsonObj.has("notifyToApplicant") && !jsonObj.getString("notifyToApplicant").isEmpty()){
								        	   offerHistory.setNotifyToApplicant(jsonObj.getString("notifyToApplicant"));
								           }
								           if(jsonObj.has("notifyToVendor") && !jsonObj.getString("notifyToVendor").isEmpty()){
								        	   offerHistory.setNotifyToVendor(jsonObj.getString("notifyToVendor"));
								           }
						        	   }
						        	 
						           }else{
						        	   offerHistory.setOfferSourceType(Constants.OFFERMADE);
						        	   if(jsonObj.has("notifyToApplicant") && !jsonObj.getString("notifyToApplicant").isEmpty()){
							        	   offerHistory.setNotifyToApplicant(jsonObj.getString("notifyToApplicant"));
							           }
							           if(jsonObj.has("notifyToVendor") && !jsonObj.getString("notifyToVendor").isEmpty()){
							        	   offerHistory.setNotifyToVendor(jsonObj.getString("notifyToVendor"));
							           }
						           }
						           offerHistory.setStatus(Constants.RECORD_ACTIVE);
						           offerHistory.setCreatedDate(new Date());
						           offerHistory.setCreatedBy(loginUser.getUserCode());
						           offerHistory.setUpdatedDate(new Date());
						           offerHistory.setUpdatedBy(loginUser.getUserCode());
						           requirementOfferHistoryService.storeRequirementOfferHistory(offerHistory);
						           
						           if(jsonObj.has("clientOffer") && jsonObj.getString("clientOffer").equalsIgnoreCase(Constants.YES) && submission.getSourceSubmission() != null){
						        	   
						        	   RequirementOfferHistory vendortOfferHistory = new RequirementOfferHistory();
						        	   vendortOfferHistory.setOfferId(updateOffer);
						        	   
						        	   vendortOfferHistory.setOrgLocationId(submission.getSourceSubmission().getOrgLocations());
					        		   vendortOfferHistory.setSubmissionId(submission.getSourceSubmission());
						        	 
						        	   vendortOfferHistory.setOfferSourceType(Constants.CLIENT_OFFERMADE);
						        	   if(jsonObj.has("notifyToApplicant") && !jsonObj.getString("notifyToApplicant").isEmpty()){
						        		   vendortOfferHistory.setNotifyToApplicant(jsonObj.getString("notifyToApplicant"));
							           }
							           if(jsonObj.has("notifyToVendor") && !jsonObj.getString("notifyToVendor").isEmpty()){
							        	   vendortOfferHistory.setNotifyToVendor(jsonObj.getString("notifyToVendor"));
							           }
						        	   vendortOfferHistory.setStatus(Constants.RECORD_ACTIVE);
						        	   vendortOfferHistory.setCreatedDate(new Date());
						        	   vendortOfferHistory.setCreatedDate(new Date());
						        	   vendortOfferHistory.setCreatedBy(loginUser.getUserCode());
						        	   vendortOfferHistory.setUpdatedDate(new Date());
						        	   vendortOfferHistory.setUpdatedBy(loginUser.getUserCode());
							           requirementOfferHistoryService.storeRequirementOfferHistory(vendortOfferHistory);
						           }
								
								
					           RequirementSubmission requirementSubmission = requirementSubmissionService.getRequirementSubmissionById(Long.parseLong(applicants[i].replace("\"", "")));
					           String submissionStatus = "";
					           if(jsonObj.has("clientOffer") && jsonObj.getString("clientOffer").equalsIgnoreCase(Constants.YES)){
					        	   if(requirementSubmission.getSourceSubmission() != null) {
					        		   requirementSubmission.setPreviousSubmissionStatus(requirementSubmission.getSubmissionStatus());
					        		   requirementSubmission.setSubmissionStatus(ApplicantSubmissionStatus.OFFERMADE.toString());
					        	   }else {
					        		   requirementSubmission.setPreviousSubmissionStatus(requirementSubmission.getSubmissionStatus());
					        		   requirementSubmission.setSubmissionStatus(ApplicantSubmissionStatus.CLIENT_OFFERMADE.toString());
					        	   }
					        	  
					           }else {
				        		   requirementSubmission.setPreviousSubmissionStatus(requirementSubmission.getSubmissionStatus());
					        	   requirementSubmission.setSubmissionStatus(ApplicantSubmissionStatus.OFFERMADE.toString());
					           }
					           submissionStatus = requirementSubmission.getSubmissionStatus();
					           requirementSubmission.setUpdatedDate(commonUtil.currentDate());
					           requirementSubmissionService.storeRequirementSubmission(requirementSubmission);
					           
					           RequirementSubmission sourcesubmission = null;
					           if(requirementSubmission.getSourceSubmission() != null) {
					        	   sourcesubmission = requirementSubmissionService.getRequirementSubmissionById(requirementSubmission.getSourceSubmission().getId());
					           }
					           
					           
					           if(jsonObj.has("clientOffer") && jsonObj.getString("clientOffer").equalsIgnoreCase(Constants.YES)){
					        	   
					        	   if(requirementSubmission.getSourceSubmission() != null) {
					        		   RequirementSubmissionHistory requirementSubmissionHistory = new RequirementSubmissionHistory();
							           requirementSubmissionHistory.setFromStage(submission.getSubmissionStatus());
				        			   requirementSubmissionHistory.setToStage(Constants.OFFERMADE);
				        			   requirementSubmissionHistory.setCreatedBy(loginUser.getUserCode());
				       				   requirementSubmissionHistory.setUpdatedBy(loginUser.getUserCode());	
				        			   requirementSubmissionHistory.setSubmissionId(requirementSubmission);
				        			   requirementSubmissionHistory.setCreatedDate(new Date());
				        			   requirementSubmissionHistory.setUpdatedDate(new Date());
				        			   requirementSubmissionHistory.setCreatedUser(userName.toString());
				        			   requirementSubmissionHistory.setMasterUserCode(submission.getCreatedBy());
				        			   requirementSubmissionHistoryService.addRequirementHistory(requirementSubmissionHistory);
				        			   
				        			   
				        			   RequirementSubmissionHistory vendorSubmissionHistory = new RequirementSubmissionHistory();
				        			   vendorSubmissionHistory.setFromStage(sourcesubmission.getSubmissionStatus());
				        			   vendorSubmissionHistory.setToStage(Constants.CLIENT_OFFERMADE.toString());
				        			   vendorSubmissionHistory.setCreatedBy(loginUser.getUserCode());
				        			   vendorSubmissionHistory.setUpdatedBy(loginUser.getUserCode());	
				        			   vendorSubmissionHistory.setSubmissionId(sourcesubmission);
				        			   vendorSubmissionHistory.setCreatedDate(new Date());
				        			   vendorSubmissionHistory.setUpdatedDate(new Date());
				        			   vendorSubmissionHistory.setCreatedUser(userName.toString());
				        			   vendorSubmissionHistory.setMasterUserCode(sourcesubmission.getCreatedBy());
				        			   requirementSubmissionHistoryService.addRequirementHistory(vendorSubmissionHistory);
				        			   
				        			   sourcesubmission.setSubmissionStatus(ApplicantSubmissionStatus.CLIENT_OFFERMADE.toString());
						        	   sourcesubmission.setUpdatedDate(commonUtil.currentDate());
							           requirementSubmissionService.storeRequirementSubmission(sourcesubmission);
					        	   }else {
					        		   RequirementSubmissionHistory requirementSubmissionHistory = new RequirementSubmissionHistory();
							           requirementSubmissionHistory.setFromStage(submission.getSubmissionStatus());
				        			   requirementSubmissionHistory.setToStage(Constants.CLIENT_OFFERMADE.toString());
				        			   requirementSubmissionHistory.setCreatedBy(loginUser.getUserCode());
				       				   requirementSubmissionHistory.setUpdatedBy(loginUser.getUserCode());	
				        			   requirementSubmissionHistory.setSubmissionId(requirementSubmission);
				        			   requirementSubmissionHistory.setCreatedDate(new Date());
				        			   requirementSubmissionHistory.setUpdatedDate(new Date());
				        			   requirementSubmissionHistory.setCreatedUser(userName.toString());
				        			   requirementSubmissionHistory.setMasterUserCode(submission.getCreatedBy());
				        			   requirementSubmissionHistoryService.addRequirementHistory(requirementSubmissionHistory);
					        	   }
					        	  
	                            	
					           }else if(requirementSubmission.getSourceSubmission() == null){
						        	   RequirementSubmissionHistory requirementSubmissionHistory = new RequirementSubmissionHistory();
							           requirementSubmissionHistory.setFromStage(submission.getSubmissionStatus());
				        			   requirementSubmissionHistory.setToStage(Constants.OFFERMADE);
				        			   requirementSubmissionHistory.setCreatedBy(loginUser.getUserCode());
				       				   requirementSubmissionHistory.setUpdatedBy(loginUser.getUserCode());	
				        			   requirementSubmissionHistory.setSubmissionId(requirementSubmission);
				        			   requirementSubmissionHistory.setCreatedDate(new Date());
				        			   requirementSubmissionHistory.setUpdatedDate(new Date());
				        			   requirementSubmissionHistory.setCreatedUser(userName.toString());
				        			   requirementSubmissionHistory.setMasterUserCode(submission.getCreatedBy());
				        			   requirementSubmissionHistoryService.addRequirementHistory(requirementSubmissionHistory);
					           }
					           
					           /*if(jsonObj.has("clientOffer") && jsonObj.getString("clientOffer").equalsIgnoreCase(Constants.YES) ){
					        	   sourcesubmission.setSubmissionStatus(ApplicantSubmissionStatus.CLIENT_OFFERMADE);
					        	   sourcesubmission.setUpdatedDate(commonUtil.currentDate());
						           requirementSubmissionService.storeRequirementSubmission(sourcesubmission);
					           }*/
					         
					           
					           if(jsonObj.has("clientOffer") && jsonObj.getString("clientOffer").equalsIgnoreCase(Constants.NO)  && requirementSubmission.getSourceSubmission() !=null){
					        		   if(jsonObj.has("notifyToVendor") && !jsonObj.getString("notifyToVendor").isEmpty() && sourcesubmission != null){
					        			   RequirementSubmissionHistory requirementSubmissionHistory = new RequirementSubmissionHistory();
								           requirementSubmissionHistory.setFromStage(submissionStatus);
					        			   requirementSubmissionHistory.setToStage(Constants.OFFERMADE);
					        			   requirementSubmissionHistory.setCreatedBy(loginUser.getUserCode());
					       				   requirementSubmissionHistory.setUpdatedBy(loginUser.getUserCode());	
					        			   requirementSubmissionHistory.setSubmissionId(requirementSubmission);
					        			   requirementSubmissionHistory.setCreatedDate(new Date());
					        			   requirementSubmissionHistory.setUpdatedDate(new Date());
					        			   requirementSubmissionHistory.setCreatedUser(userName.toString());
					        			   requirementSubmissionHistory.setMasterUserCode(submission.getCreatedBy());
					        			   requirementSubmissionHistoryService.addRequirementHistory(requirementSubmissionHistory);
					        			   
					        			   
					        			   RequirementSubmissionHistory vendorSubmissionHistory = new RequirementSubmissionHistory();
					        			   vendorSubmissionHistory.setFromStage(sourcesubmission.getSubmissionStatus());
					        			   vendorSubmissionHistory.setToStage(Constants.CLIENT_OFFERMADE);
					        			   vendorSubmissionHistory.setCreatedBy(loginUser.getUserCode());
					        			   vendorSubmissionHistory.setUpdatedBy(loginUser.getUserCode());	
					        			   vendorSubmissionHistory.setSubmissionId(sourcesubmission);
					        			   vendorSubmissionHistory.setCreatedDate(new Date());
					        			   vendorSubmissionHistory.setUpdatedDate(new Date());
					        			   vendorSubmissionHistory.setCreatedUser(userName.toString());
					        			   vendorSubmissionHistory.setMasterUserCode(sourcesubmission.getCreatedBy());
					        			   requirementSubmissionHistoryService.addRequirementHistory(vendorSubmissionHistory);
		                                	
					        			    sourcesubmission.setSubmissionStatus(ApplicantSubmissionStatus.CLIENT_OFFERMADE.toString());
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
            emailMessage.setFromName(nameBuilder.append(loginUser.getFullName()).toString());
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
            
            if (loginUser != null && !"".equalsIgnoreCase(loginUser.getFullName())) {
                    globalVarsMap.put("loginUser", loginUser.getFullName());
            } else {
                    globalVarsMap.put("loginUser", "--");
            }
            if (loginOrgLocations.getOrgLocationName() != null && !"".equalsIgnoreCase(loginOrgLocations.getOrgLocationName())) {
                globalVarsMap.put("clientOrgnanizationName", loginOrgLocations.getOrgLocationName());
            } else {
                globalVarsMap.put("clientOrgnanizationName", "--");
            }
            
            StringBuilder linkSB=new StringBuilder();
            
           /* StringBuilder viewOfferLink=new StringBuilder();
            if(requirementOffers.getOfferLetterTemplate() != null) {
           	 globalVarsMap.put("viewOffer",viewOfferLink.append("<a href='").append(sourceleadUrl).append("/ws/candidateOfferLetterTempleateView?offerUUID="+requirementOffers.getOfferUUID()).append("'style='text-decoration:underline; display:block; margin-left:5px; margin-top:-25px;' target='_blank'><div style='width:100%; color:#368ee0; font-size:16px; font-weight:600; margin:10px auto 5px auto; padding:10px 0px; text-align:left; letter-spacing:0px;'>View Offer</div></a>").toString());
            }*/
            
            globalVarsMap.put("viewOffer",linkSB.append("<a href='").append(sourceleadUrl).append("/offerLetterTempleateView").append(">View Offer Letter</div></a>").toString());
            globalVarsMap.put("link",linkSB.append("<a href='").append(sourceleadUrl).append("/").append("' style='text-decoration:none; '><div style=' width:220px; border-radius:30px; background-color:#214b90; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Login</div></a>").toString());
            // setting the Recipient information
            InternetAddress[] iAdressArray = InternetAddress.parse(emailMessage.getToEmail());

            // getting the current EmailScenario by EmailScenario unique name and
            // entity type
            EmailScenario  emailScenario = null;
			 if(loginOrgLocations != null)
			 emailScenario =  emailScenarioService.getEmailScenarioByUniqueNameAndOrgLocationId(Constants.OFFER_MAIL_TO_VENDORS,Constants.ENTITY_TYPE_EMAIL, loginOrgLocations.getId());
			  if(emailScenario == null){
					emailScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.OFFER_MAIL_TO_VENDORS,Constants.ENTITY_TYPE_EMAIL);
			   }
            EmailScenario notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.OFFER_MAIL_TO_VENDORS, Constants.ENTITY_TYPE_NOTIFICATION);


            //covert subject,body and signature
			 String subject = emailScenerioUtil.bulidTemplateSubject(emailScenario.getSubject(),globalVarsMap,emailScenario);
			 String body = emailScenerioUtil.buildTemplateBody(emailScenario.getBody(),globalVarsMap,emailScenario);
			 String signature = null;
			 if(StringUtils.isNotBlank(emailScenario.getSignature())){
				 signature = emailScenerioUtil.buildTemplateSignature(emailScenario.getSignature(),globalVarsMap,emailScenario); 
			 }
			 String url = sourceleadUrl;
            
			 String notificationbody = emailScenerioUtil.buildTemplateBody(notificationScenario.getBody(), globalVarsMap,emailScenario);
		        notificationScenario.setBody(notificationbody);


            // construct HTML template using required data
            String emailTemplate = emailScenerioUtil.AssembleEmailMessage(emailScenario, emailMessage, subject, body, signature, url, loginOrgLocations.getOrg(),loginOrgLocations);
            emailMessage.setSubject(subject);
            emailMessage.setMessageBody(emailTemplate);

            // calling the sendEmail method of emailScenerioUtil
            
            try {
    			
                emailScenerioUtil.sendEmail(emailMessage,loginOrgLocations.getId(), iAdressArray, new ArrayList<EmailAttachment>());
                StringBuilder actionLinkBuilder = new StringBuilder();
                String actionLink =actionLinkBuilder.append("").append(atsUrl).append("/").append("requirementSummary?type=OFFERMADE&requirementUUID="+clientrequirement.getRequirementUUID()).toString();	 
               OrgLocations vendorLocation = clientrequirement.getOrgLocationId();
				emailScenerioUtil.sendNotification(actionLink, loginUser, clientrequirement.getSpocId().getVendorUser(), vendorLocation, notificationScenario,new HashMap<String,String>());
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
	    	emailMessage.setFromName(loginUser.getFullName());
            
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
            
            if (loginUser != null && !"".equalsIgnoreCase(loginUser.getFullName())) {
                    globalVarsMap.put("loginUser", loginUser.getFullName());
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
       /* globalVarsMap.put("acceptLink","<a href='"+sourceleadUrl+"/"+"ws/acceptOrRejectOffer?offerStatus=Accepted&submissionId="+applicant.getId() +"' style='text-decoration:none; '><div style=' width:220px; border-radius:30px; background-color:#61D339 ; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Accept</div></a>");
        globalVarsMap.put("rejectLink","<a href='"+sourceleadUrl+"/"+"ws/acceptOrRejectOffer?offerStatus=Rejected&submissionId="+applicant.getId() +"' style='text-decoration:none; '><div style=' width:220px; border-radius:30px; background-color:#D33B39; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Reject</div></a>");*/

         StringBuilder acceptLinkBuilder=new StringBuilder();
         StringBuilder rejectLinkBuilder=new StringBuilder();
         StringBuilder negotiationLinkBuilder=new StringBuilder();
         StringBuilder viewOfferLink = new StringBuilder();
         globalVarsMap.put("acceptLink",acceptLinkBuilder.append("<a href='").append(atsUrl).append("/").append("ws/acceptOrRejectRequirementOffer?offerStatus=Accepted&offerId=").append(requirementOffers.getId()).append("'style='text-decoration:none;float:left; margin-left:5px;'><div style=' width:160px; border-radius:30px; background-color:#5cb85c; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px; text-align:center; letter-spacing:0px;'>Accept</div></a>").toString());
         globalVarsMap.put("rejectLink",rejectLinkBuilder.append("<a href='").append(atsUrl).append("/").append("ws/acceptOrRejectRequirementOffer?offerStatus=Rejected&offerId=").append(requirementOffers.getId()).append("'style='text-decoration:none;float:left;margin-left:10px;'><div style=' width:160px; border-radius:30px; background-color:#e74c51; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px; text-align:center; letter-spacing:0px;'>Reject</div></a>").toString());
         globalVarsMap.put("negotiationLink",negotiationLinkBuilder.append("<a href='").append(atsUrl).append("/ws/acceptOrRejectOrRateNegotiateApplicantRates?rateStatus=Negotiate&submissionId=").append(requirementOffers.getSubmissionId().getId()).append("'style='text-decoration:none;float:left;margin-left:10px;'><div style='width:160px; border-radius:30px; background-color:#f0ad4e; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px; text-align:center; letter-spacing:0px;'>Negotiate Rate</div></a>").toString());
         if(requirementOffers.getOfferLetterTemplate() != null) {
        	 globalVarsMap.put("viewOffer",viewOfferLink.append("<a href='").append(sourceleadUrl).append("/ws/candidateOfferLetterTempleateView?offerUUID="+requirementOffers.getOfferUUID()).append("'style='text-decoration:underline; display:block; margin-left:5px; margin-top:-25px;' target='_blank'><div style='width:100%; color:#368ee0; font-size:16px; font-weight:600; margin:10px auto 5px auto; padding:10px 0px; text-align:left; letter-spacing:0px;'>View Offer</div></a>").toString());
         }
        
        StringBuilder attachmentLinkBuilder=new StringBuilder();
        if(requirementOffers.getFileName() != null && !requirementOffers.getFileName().isEmpty() ){
            globalVarsMap.put("attachment",attachmentLinkBuilder.append("<a href='").append(atsUrl).append("/").append("ws/downloadOfferAttachment?offerId=").append(requirementOffers.getId()).append("' style='text-decoration:none; '><div style='width:100px; margin: 15px auto; font-size:15px;'>Attachment").append("<br/></div></a>").toString());
        }/*else{
            globalVarsMap.put(" ",attachmentLinkBuilder.append("<a href='").append(sourceleadUrl).append("/").append("ws/downloadOfferAttachment?offerId=").append(requirementOffers.getId()).append("' style='text-decoration:none; '><div style=' width:220px; border-radius:30px; background-color:#214b90; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center;letter-spacing:2px;'>Attachment").append("<br/></div></a>").toString());

        }*/
        // setting the Recipient information
        InternetAddress[] iAdressArray = InternetAddress.parse(emailMessage.getToEmail());

        // getting the current EmailScenario by EmailScenario unique name and
        // entity type
        EmailScenario  emailScenario = null;
		 if(orgLocations != null)
		 emailScenario =  emailScenarioService.getEmailScenarioByUniqueNameAndOrgLocationId(Constants.ADD_OFFER_MAIL,Constants.ENTITY_TYPE_EMAIL, orgLocations.getId());
		  if(emailScenario == null){
				emailScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.ADD_OFFER_MAIL,Constants.ENTITY_TYPE_EMAIL);
		   }
        EmailScenario notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.ADD_OFFER_MAIL, Constants.ENTITY_TYPE_NOTIFICATION);


        //covert subject,body and signature
		 String subject = emailScenerioUtil.bulidTemplateSubject(emailScenario.getSubject(),globalVarsMap,emailScenario);
		 String body = emailScenerioUtil.buildTemplateBody(emailScenario.getBody(),globalVarsMap,emailScenario);
		 String signature = null;
		 if(StringUtils.isNotBlank(emailScenario.getSignature())){
			 signature = emailScenerioUtil.buildTemplateSignature(emailScenario.getSignature(),globalVarsMap,emailScenario); 
		 }
		 String url = sourceleadUrl;
        
        String notificationbody = emailScenerioUtil.buildTemplateBody(notificationScenario.getBody(), globalVarsMap,emailScenario);
        notificationScenario.setBody(notificationbody);


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
		StringBuilder actionLinkBuilder = new StringBuilder();                          
		String actionLink =actionLinkBuilder.append("").append(atsUrl).append("/").append("requirementSummary?type=OFFERMADE&requirementUUID="+applicant.getRequirement().getRequirementUUID()).toString();	 
        User applicantuser = applicant.getOrgLocations().getOrg().getUser();
		emailScenerioUtil.sendNotification(actionLink, loginUser, applicantuser, orgLocations, notificationScenario,new HashMap<String,String>());
        
        
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
		// calling the sendEmail method of emailScenerioUtil
		commonUtil.sendTemplateEmail(emailMessage, globalVarsMap,orgLocations.getOrg(),orgLocations,Constants.OFFERACCEPT_EMAIL,new ArrayList<EmailAttachment>());

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
		EmailScenario emailScenario = null;
		 if(loginOrgLocations != null)
			 emailScenario =  emailScenarioService.getEmailScenarioByUniqueNameAndOrgLocationId(Constants.OFFERREJECT_EMAIL,Constants.ENTITY_TYPE_EMAIL, loginOrgLocations.getId());
			  if(emailScenario == null){
					emailScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.OFFERREJECT_EMAIL,Constants.ENTITY_TYPE_EMAIL);
			   }
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
								        		   
								        		   sourcesubmission.setSubmissionStatus(ApplicantSubmissionStatus.CLIENT_OFFERMADE.toString());
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
			    		
	    		       RequirementSubmissionHistory requirementSubmissionHistory = new RequirementSubmissionHistory();
			           requirementSubmissionHistory.setFromStage(requirementSubmission.getSubmissionStatus());
		    		   
		    		   if(requirementOfferHistory.getOfferSourceType() != null && "OFFERMADE".equals(requirementOfferHistory.getOfferSourceType())){
			    			requirementSubmission.setSubmissionStatus(ApplicantSubmissionStatus.OFFERACCEPTED.toString());
			    		}else{
			    			requirementSubmission.setSubmissionStatus(ApplicantSubmissionStatus.CLIENT_OFFERACCEPTED.toString());
			    		}
			    		 requirementSubmission.setUpdatedBy(loginUser.getUserCode());
						 requirementSubmission.setUpdatedDate(commonUtil.currentDate());
						 requirementSubmissionService.storeRequirementSubmission(requirementSubmission);
						
						 requirementSubmissionHistoryService.updateRequirementHistory(requirementSubmission, loginUser, requirementSubmissionHistory, requirementSubmission.getCreatedBy());
				  }
		    	
		    	//this condition is for candidate to Associate when offerAccepted 
		    	if(requirementOffers.getSubmissionId().getOrgLocations().getId().longValue() == orgLocations.getId().longValue()){
		    		Candidate candidate = candidateService.getCandidateByCandidateIdAndOrgLocationId(requirementOffers.getSubmissionId().getCandidate().getId(),orgLocations.getId());
			    	if(candidate != null){
			    		candidate.setIsAssociate(Constants.YES);
				    	candidate.setUpdatedBy(loginUser.getUserCode());
				    	candidate.setUpdatedDate(commonUtil.currentDate());	
			    	}else{
			    		candidate = addCandidateToClientAfterOfferAccepted(requirementOffers,requirementOffers.getSubmissionId().getOrgLocations());
			    		candidate.setCreatedBy(loginUser.getUserCode());
			    		candidate.setUpdatedBy(loginUser.getUserCode());
			    	}
			    	candidateService.saveOrUpdateCandidate(candidate);
			    	candidateService.saveOrUpdateCandidateElkDocument(candidate);
			    	
			    	if(requirementOffers.getSubmissionId().getRequirement().getIsClientJob().equalsIgnoreCase(Constants.YES) && 
			    			requirementOffers.getSubmissionId().getRequirement().getParentRequirement() == null){
			    		RequirementSubmission clientSubmission = requirementSubmissionService.getClientSubmissionsBySourceSubmissionIdAndVendorLocationId(requirementOffers.getSubmissionId().getId(), orgLocations.getId());
			    		if(clientSubmission != null){
			    			addCandidateToClientAfterOfferAccepted(requirementOffers,requirementOffers.getSubmissionId().getOrgLocations());
			    		}
			    		
			    	}
			    	
		    	}else{
		    		Candidate candidate = addCandidateToClientAfterOfferAccepted(requirementOffers,requirementOffers.getSubmissionId().getOrgLocations());
		    		candidate.setCreatedBy(loginUser.getUserCode());
		    		candidate.setUpdatedBy(loginUser.getUserCode());
		    		candidateService.saveOrUpdateCandidate(candidate);
			    	candidateService.saveOrUpdateCandidateElkDocument(candidate);
		    	}
		    	
		    	
		    	  //Changing the status of offerTable 
			      if(requirementOffers  != null){
			    	  if(requirementOffers.getOfferStatus().equalsIgnoreCase("CLIENT_OFFERMADE")) {
			    		  requirementOffers.setOfferStatus("CLIENT_OFFERACCEPTED");
			    	  }else {
			    		  requirementOffers.setOfferStatus("OFFERACCEPTED");
			    	  }
					   
					    requirementOffers.setUpdatedBy(loginUser.getUserCode());
					    requirementOffers.setUpdatedDate(commonUtil.currentDate());
					    requirementOffersDao.storeRequirementOffers(requirementOffers);
			     }
			      
			      
			     
			      if(requirementOffers.getSubmissionId().getOrgLocations().getId().longValue() != orgLocations.getId().longValue()){
			    	  if(requirementOffers.getSubmissionId().getSourceSubmission() != null) {
			    		  sendOfferAcceptedMail(requirementOffers,loginUser,orgLocations);
			    	  }
			    	 
			      }
			      
		      	  if((null != requirementOffers.getSubmissionId().getVendorLocationId() && null != requirementOffers.getSubmissionId().getSourceSubmission()) &&
		      	  ("C2C".equalsIgnoreCase(requirementOffers.getSubmissionId().getRequirement().getJobType()) || "C2H".equalsIgnoreCase(requirementOffers.getSubmissionId().getRequirement().getJobType()) || "Contract".equalsIgnoreCase(requirementOffers.getSubmissionId().getRequirement().getJobType())) ){
		      		
		      		ContractsDTO contractsDTO = populateContractsDTO(requirementOffers,loginUser,orgLocations);
		      		contractsTemplateService.saveNewContract(contractsDTO, orgLocations, "DRAFT", loginUser, orgLocations.getOrg(), "NEW");
			      }
			      
			      
	    }else if("OFFERREJECTED".equalsIgnoreCase(offerStatus)){
	    	for (RequirementOfferHistory requirementOfferHistory : requirementOfferHistories) {
	    		    RequirementSubmission requirementSubmission = requirementSubmissionService.getRequirementSubmissionById(requirementOfferHistory.getSubmissionId().getId());
	    		    RequirementSubmissionHistory requirementSubmissionHistory = new RequirementSubmissionHistory();
			        requirementSubmissionHistory.setFromStage(requirementSubmission.getSubmissionStatus());
	    		    
			        if(requirementOfferHistory.getOfferSourceType() != null && "OFFERMADE".equals(requirementOfferHistory.getOfferSourceType())){
		    			requirementSubmission.setSubmissionStatus(ApplicantSubmissionStatus.OFFERREJECTED.toString());
		    		}else{
		    			requirementSubmission.setSubmissionStatus(ApplicantSubmissionStatus.CLIENT_OFFERREJECTED.toString());
		    		}
			        
		    		 requirementSubmission.setUpdatedBy(loginUser.getUserCode());
					 requirementSubmission.setUpdatedDate(commonUtil.currentDate());
					 requirementSubmissionService.storeRequirementSubmission(requirementSubmission);
					 requirementSubmissionHistoryService.updateRequirementHistory(requirementSubmission, loginUser, requirementSubmissionHistory, requirementSubmission.getCreatedBy());
			  }
	    	
	    	
	    	
	    	requirementOffers.setRejectedReason(reason);
	    		if(requirementOffers.getSubmissionId().getOrgLocations().getId().longValue() != orgLocations.getId().longValue()){
	    			 if(requirementOffers.getSubmissionId().getSourceSubmission() != null) {
	    				 sendOfferRejectedMail(requirementOffers,loginUser,orgLocations);
	    			 }
			      }
	    	
	    	  //Changing the status of offerTable 
			  if(requirementOffers  != null ){
				  if(requirementOffers.getOfferStatus().equalsIgnoreCase("CLIENT_OFFERMADE")) {
		    		  requirementOffers.setOfferStatus("CLIENT_OFFERREJECTED");
		    	  }else {
		    		  requirementOffers.setOfferStatus("OFFERREJECTED");
		    	  }
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


	private ContractsDTO populateContractsDTO(RequirementOffers requirementOffers, User loginUser, OrgLocations orgLocations) throws Exception
	{
		ContractsDTO contractsDTO = new ContractsDTO();
		// SimpleDateFormat simpleDateFormat =
		// orgLocations.getLocFullFormDateFormatter();
		RequirementSubmissionTemplateFields candidate = requirementSubmissionTemplateFieldsService.getSubmissionsTemplateFieldsByRequirementSubmissionsId(requirementOffers.getSubmissionId().getId());
		if (candidate != null) {
			contractsDTO.setContractType("Vendor SOW");
			if (null != candidate.getFirstName() && null != candidate.getLastName()) {
				StringBuilder candisb = new StringBuilder();
				contractsDTO.setContractTitle(candisb.append(candidate.getFirstName()).append(" ").append(candidate.getLastName()).append(" SOW").toString());
			}
			else {
				StringBuilder candisb = new StringBuilder();
				contractsDTO.setContractTitle(candisb.append(candidate.getFirstName()).append(" ").append(" SOW").toString());
			}
			contractsDTO.setSubmissionOffersList(requirementOffers.getSubmissionId().getId().toString());
			/*
			 * to allow default owner un-comment the below line --Pavan jampana
			 */
			// jsonObj.put("owner", loginUser.getId());
			contractsDTO.setOrgRelationId(requirementOffers.getSubmissionId().getSourceSubmission().getRequirement().getClientId().getId().toString());
			contractsDTO.setVendorContact(requirementOffers.getSubmissionId().getSourceSubmission().getRequirement().getSpocId().getVendorUser().getId().toString());
			contractsDTO.setFlatReward(requirementOffers.getAmount());
			contractsDTO.setBillingRatePer(requirementOffers.getPaymentMode());
			contractsDTO.setBillingRateType(requirementOffers.getPaymentType());
			// Date startTime =
			// commonUtil.getDateFormat(jsonObj.getString("startDate"));
			String currentTimeStr = commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(), requirementOffers.getDateOfJoining());
			contractsDTO.setStartDate(currentTimeStr);
		}
		return contractsDTO;
	}

	private void sendOfferAcceptedMail(RequirementOffers requirementOffers, User loginUser, OrgLocations orgLocations) throws Exception
	{
		User vendorContactUser = null;
		StringBuilder sb = new StringBuilder();
		EmailMessage emailMessage = new EmailMessage();
		emailMessage.setFirstName(loginUser.getFirstName());
		emailMessage.setLastName(loginUser.getLastName());
		emailMessage.setToEmail(requirementOffers.getSubmissionId().getSourceSubmission().getRequirement().getSpocId().getClientUser().getEmail());
		emailMessage.setFromEmail(loginUser.getEmail());
		emailMessage.setFromName(sb.append(loginUser.getFirstName()).append(" ").append(loginUser.getLastName()).toString());
		// emailMessage.setEmailType(Constants.CUSTOM_USER_EMAIL);
		// setting the class filed values
		Map<String, String> globalVarsMap = new HashMap<String, String>();
		globalVarsMap.put("ClientContactName", requirementOffers.getSubmissionId().getSourceSubmission().getRequirement().getSpocId().getClientUser().getFirstName());
		StringBuilder vendorName = new StringBuilder();
		if (null != emailMessage.getLastName()) {
			globalVarsMap.put("VendorContactName", vendorName.append(emailMessage.getFirstName()).append(" ").append(emailMessage.getLastName()).toString());
		}
		else {
			globalVarsMap.put("VendorContactName", vendorName.append(emailMessage.getFirstName()).toString());
		}
		globalVarsMap.put("OfferedJoinDate", conversionUtil.getDateAsString(requirementOffers.getDateOfJoining()));
		StringBuilder applicant = new StringBuilder();
		if (null != requirementOffers.getSubmissionId().getCandidate().getLastName()) {
			globalVarsMap.put("OfferedApplicantName", applicant.append(requirementOffers.getSubmissionId().getCandidate().getFirstName()).append(" ").append(requirementOffers.getSubmissionId().getCandidate().getLastName()).toString());
		}
		else {
			globalVarsMap.put("OfferedApplicantName", applicant.append(requirementOffers.getSubmissionId().getCandidate().getFirstName()).toString());
		}
		if (requirementOffers.getSubmissionId().getRequirement().getJobId() != null
				&& !"".equalsIgnoreCase(requirementOffers.getSubmissionId().getRequirement().getJobId())) {
			StringBuilder sbv = new StringBuilder();
			globalVarsMap.put("OfferedRequirementName", sbv.append(requirementOffers.getSubmissionId().getRequirement().getJobId()).append("-").append(requirementOffers.getSubmissionId().getRequirement().getJobTitle()).toString());
		}
		else {
			globalVarsMap.put("OfferedRequirementName", requirementOffers.getSubmissionId().getRequirement().getJobTitle());
		}
		if (requirementOffers.getSubmissionId().getRequirement().getDescription() != null
				&& !"".equalsIgnoreCase(requirementOffers.getSubmissionId().getRequirement().getDescription())) {
			globalVarsMap.put("requirementDescription", requirementOffers.getSubmissionId().getRequirement().getDescription());
		}
		else {
			globalVarsMap.put("requirementDescription", "--");
		}
		// globalVarsMap.put("offeredRequirementName",requirementOffers.getSubmissionId().getRequirement().getJobTitle());
		globalVarsMap.put("OfferedRate", requirementOffers.getAmount());
		// setting the Recipient information
		InternetAddress[] iAdressArray = InternetAddress.parse(emailMessage.getToEmail());
		EmailScenario emailScenario = null;
		if (orgLocations != null)
			emailScenario = emailScenarioService.getEmailScenarioByUniqueNameAndOrgLocationId(Constants.OFFER_ACCEPTED, Constants.ENTITY_TYPE_EMAIL, orgLocations.getId());
		if (emailScenario == null) {
			emailScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.OFFER_ACCEPTED, Constants.ENTITY_TYPE_EMAIL);
		}
		// covert subject,body and signature
		String subject = emailScenerioUtil.bulidTemplateSubject(emailScenario.getSubject(), globalVarsMap, emailScenario);
		String body = emailScenerioUtil.buildTemplateBody(emailScenario.getBody(), globalVarsMap, emailScenario);
		String signature = null;
		if (StringUtils.isNotBlank(emailScenario.getSignature())) {
			signature = emailScenerioUtil.buildTemplateSignature(emailScenario.getSignature(), globalVarsMap, emailScenario);
		}
		String url = sourceleadUrl;
		// construct HTML template using required data
		String emailTemplate = emailScenerioUtil.AssembleEmailMessage(emailScenario, emailMessage, subject, body, signature, url, orgLocations.getOrg(), orgLocations);
		emailMessage.setSubject(subject);
		emailMessage.setMessageBody(emailTemplate);
		// getting the current EmailScenario by EmailScenario unique name and
		// entity type
		EmailScenario notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.OFFER_ACCEPTED_NOTIFICATION, Constants.ENTITY_TYPE_NOTIFICATION);
		String notificationbody = emailScenerioUtil.buildTemplateBody(notificationScenario.getBody(), globalVarsMap, emailScenario);
		notificationScenario.setBody(notificationbody);
		// construct HTML template using required data
		// String emailTemplate =
		// emailScenerioUtil.AssembleEmailMessage(emailScenario, emailMessage,
		// subject, body,signature, url, orgLocations.getOrg(),orgLocations);
		if (null != requirementOffers.getSubmissionId()) {
			if (null != requirementOffers.getSubmissionId().getSourceSubmission()) {
				vendorContactUser = userService.getUserById(requirementOffers.getSubmissionId().getSourceSubmission().getRequirement().getSpocId().getClientUser().getId());
				if (null != requirementOffers.getSubmissionId().getSourceSubmission()) {
					OrgLocations vendorOrgLoc = requirementOffers.getSubmissionId().getSourceSubmission().getRequirement().getSpocId().getClientLocationId();
					requirementOffers.getSubmissionId().getSourceSubmission().getRequirement().getSpocId().getClientUser();
					// calling the sendEmail method of emailScenerioUtil
					emailScenerioUtil.sendEmail(emailMessage, orgLocations.getId(), iAdressArray, new ArrayList<EmailAttachment>());
					StringBuilder actionLinkBuilder = new StringBuilder();
					String actionLink = actionLinkBuilder.append("").append(atsUrl).append("/").append("requirementSummary?requirementUUID="
							+ requirementOffers.getSubmissionId().getRequirement().getRequirementUUID()).toString();
					emailScenerioUtil.sendNotification(actionLink, loginUser, vendorContactUser, vendorOrgLoc, notificationScenario, new HashMap<String, String>());
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
		 
		 globalVarsMap.put("ClientContactName",requirementOffers.getSubmissionId().getSourceSubmission().getRequirement().getSpocId().getClientUser().getFirstName());
		 StringBuilder vendorName = new StringBuilder();
		 if(null != emailMessage.getLastName()){
			 globalVarsMap.put("VendorContactName",vendorName.append(emailMessage.getFirstName()).append(" ").append(emailMessage.getLastName()).toString());
		 }else{
			 globalVarsMap.put("VendorContactName",vendorName.append(emailMessage.getFirstName()).toString());
		 }
		 globalVarsMap.put("JoinDate",requirementOffers.getDateOfJoining().toString());
		 
		 StringBuilder applicant = new StringBuilder();
		 if(null != requirementOffers.getSubmissionId().getCandidate().getLastName()){
			 globalVarsMap.put("OfferRejectedApplicantName",applicant.append(requirementOffers.getSubmissionId().getCandidate().getFirstName()).append(" ").append(requirementOffers.getSubmissionId().getCandidate().getLastName()).toString());
		 }else{
			 globalVarsMap.put("OfferRejectedApplicantName",applicant.append(requirementOffers.getSubmissionId().getCandidate().getFirstName()).toString());
		 }
		 globalVarsMap.put("RequirementName",requirementOffers.getSubmissionId().getRequirement().getJobTitle());
		 globalVarsMap.put("Rate",requirementOffers.getAmount());
		 globalVarsMap.put("RejectReason",requirementOffers.getRejectedReason());
		 
		//setting the Recipient information
		 InternetAddress[] iAdressArray = InternetAddress.parse(emailMessage.getToEmail());
		 
		//getting the current EmailScenario by EmailScenario unique name and entity type
		 EmailScenario  emailScenario = null;
		 if(orgLocations != null)
		 emailScenario =  emailScenarioService.getEmailScenarioByUniqueNameAndOrgLocationId(Constants.OFFER_REJECTED,Constants.ENTITY_TYPE_EMAIL, orgLocations.getId());
		  if(emailScenario == null){
				emailScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.OFFER_REJECTED,Constants.ENTITY_TYPE_EMAIL);
		   }
		  
		  //covert subject,body and signature
			 String subject = emailScenerioUtil.bulidTemplateSubject(emailScenario.getSubject(),globalVarsMap,emailScenario);
			 String body = emailScenerioUtil.buildTemplateBody(emailScenario.getBody(),globalVarsMap,emailScenario);
			 String signature = null;
			 if(StringUtils.isNotBlank(emailScenario.getSignature())){
				 signature = emailScenerioUtil.buildTemplateSignature(emailScenario.getSignature(),globalVarsMap,emailScenario); 
			 }
			 String url = sourceleadUrl;
			 
			 //construct HTML template using required data
			 String emailTemplate = emailScenerioUtil.AssembleEmailMessage(emailScenario,emailMessage, subject, body, signature, url,orgLocations.getOrg(),orgLocations);
			 emailMessage.setSubject(subject);
			 emailMessage.setMessageBody(emailTemplate);

		 //getting the current EmailScenario by EmailScenario unique name and entity type
		 EmailScenario  notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.OFFER_REJECTED_NOTIFICATION,Constants.ENTITY_TYPE_NOTIFICATION);

		 String notificationbody = emailScenerioUtil.buildTemplateBody(notificationScenario.getBody(),globalVarsMap,emailScenario);
		 notificationScenario.setBody(notificationbody);

		 if(null != requirementOffers.getSubmissionId()){
			 if(null != requirementOffers.getSubmissionId().getSourceSubmission()){
		 vendorContactUser = userService.getUserById(requirementOffers.getSubmissionId().getSourceSubmission().getRequirement().getSpocId().getClientUser().getId());
		 if(null !=requirementOffers.getSubmissionId().getSourceSubmission()){
		 OrgLocations vendorOrgLoc = requirementOffers.getSubmissionId().getSourceSubmission().getRequirement().getSpocId().getClientLocationId();
				 
		 requirementOffers.getSubmissionId().getSourceSubmission().getRequirement().getSpocId().getClientUser();
		 //calling the sendEmail method of emailScenerioUtil

//		 commonUtil.sendTemplateEmail(emailMessage, globalVarsMap,orgLocations.getOrg(),orgLocations,Constants.OFFER_REJECTED,new ArrayList<EmailAttachment>());
		//calling the sendEmail method of emailScenerioUtil
		 emailScenerioUtil.sendEmail(emailMessage,orgLocations.getId(),iAdressArray,new ArrayList<EmailAttachment>());
		 StringBuilder actionLinkBuilder = new StringBuilder();
		 String actionLink =actionLinkBuilder.append("").append(atsUrl).append("/").append("requirementSummary?requirementUUID="+requirementOffers.getSubmissionId().getSourceSubmission().getRequirement().getRequirementUUID()).toString();	 
		 emailScenerioUtil.sendNotification(actionLink, loginUser, vendorContactUser, vendorOrgLoc, notificationScenario,new HashMap<String,String>());
		
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
			offer.setSpocUserCode(offer.getSubmissionId().getCreatedBy());
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
					/*SimpleDateFormat localDateFormat = new SimpleDateFormat("HH:mm:ss");
					String time = localDateFormat.format(converteDate);
					offer.setDateOfJoiningStr(time);*/
				}
			}
			
			Contracts contracts = contractsTemplateService.getContractByReqSubmissionSowTypeAndOrgLocId(offer.getSubmissionId().getId(), orglocation, Constants.Vendor_SOW);
			if(contracts != null && contracts.getContractUUID() != null)
			offer.setContractUUID(contracts.getContractUUID());
			
			Boolean flag = contractsTemplateService.getVendorMsaCreatedOrNot(offer.getSubmissionId().getVendorLocationId().getId(), orglocation.getId());
			if(flag == true){
				offer.setVendorMsaCreated(flag);
			}
			if(offer.getSubmissionId().getSourceSubmission() != null){
				if(offer.getSubmissionId().getSourceSubmission().getRequirement() != null){
					if(offer.getSubmissionId().getSourceSubmission().getRequirement().getClientId() != null)
					offer.setOrgRelationId(offer.getSubmissionId().getSourceSubmission().getRequirement().getClientId().getId());
				}
			}
		}
		return offerList;
	}


	@Override
	public Candidate addCandidateToClientAfterOfferAccepted(RequirementOffers requirementOffers,OrgLocations orgLocations) throws Exception {
		
		RequirementSubmission clientSubmission = null;
		Candidate candidate = null; 
		MasterCandidate masterCandidte = null;
		if(requirementOffers.getSubmissionId().getRequirement().getIsClientJob().equalsIgnoreCase(Constants.YES) && 
    			requirementOffers.getSubmissionId().getRequirement().getParentRequirement() == null){
			clientSubmission = requirementSubmissionService.getClientSubmissionsBySourceSubmissionIdAndVendorLocationId(requirementOffers.getSubmissionId().getId(), orgLocations.getId());
			if(clientSubmission != null){
				candidate = candidateService.findByCandidateEmail(clientSubmission.getCandidate().getEmail(), clientSubmission.getOrgLocations().getId());
			}
		}else{
			candidate = candidateService.findByCandidateEmail(requirementOffers.getSubmissionId().getCandidate().getEmail(), orgLocations.getId());
		}
		
		if(candidate == null){
			candidate = new Candidate();
			masterCandidte = masterCandidateDao.getMasterCandidateByEmailOrPhoneNumber(requirementOffers.getSubmissionId().getCandidate().getEmail(), requirementOffers.getSubmissionId().getCandidate().getMobilePhone());
    		if(masterCandidte != null){
    			candidate.setMasterCandidateId(masterCandidte);
    		}
		}
    		
    		
    		RequirementSubmission requirementSubmission = requirementSubmissionService.getRequirementSubmissionById(requirementOffers.getSubmissionId().getId());
    		Set<RequirementSubmissionTemplateFields> submissionTemplateFieldsList = requirementSubmission.getRequirementSubmissionTemplateFieldsList();
    		if(submissionTemplateFieldsList != null){
    			for(RequirementSubmissionTemplateFields templateFields : submissionTemplateFieldsList){
    				candidate.setFirstName(templateFields.getFirstName());
		    		candidate.setLastName(templateFields.getLastName());
		    		candidate.setEmail(templateFields.getEmailId());
		    		candidate.setMobilePhone(templateFields.getPhoneNumber());
		    		candidate.setLocation(templateFields.getLocation());
		    		candidate.setCountry(templateFields.getCountry());
		    		candidate.setState(templateFields.getState());
		    		candidate.setCity(templateFields.getCity());
		    		candidate.setZipCode(templateFields.getZipCode());
		    		if(templateFields.getCandidateWorkExperienceYears() != null){
		    			candidate.setExperienceYears(templateFields.getCandidateWorkExperienceYears().toString());
		    		}
		    		if(templateFields.getCandidateWorkExperienceMonths() != null){
		    			candidate.setExperienceMonths(templateFields.getCandidateWorkExperienceMonths().toString());
		    		}
		    		
    			}
    		}
    		
    		candidate.setCandidateType("Candidate");
    		candidate.setCandidateUUID(SourceLeadRandomCodeHelper.generateRandomUUID());
    		
    		if(requirementOffers.getSubmissionId().getRequirement().getIsClientJob().equalsIgnoreCase(Constants.YES) && 
        			requirementOffers.getSubmissionId().getRequirement().getParentRequirement() == null){
    			
    			if(StringUtils.isNotBlank(requirementOffers.getOtherPositionType())) {
        			candidate.setDesignationOtherType(requirementOffers.getOtherPositionType());
        		}
        		
    			if(StringUtils.isBlank(candidate.getDesignationType())) {
    				if(StringUtils.isNotBlank(requirementOffers.getPositionType())) {
            			candidate.setDesignationType(requirementOffers.getPositionType());
            		}else {
            			candidate.setDesignationType("C2C");
            		}
    			}
    			
    		}else {
    			if(StringUtils.isNotBlank(requirementOffers.getOtherPositionType())) {
        			candidate.setDesignationOtherType(requirementOffers.getOtherPositionType());
        		}
        		
        		if(StringUtils.isNotBlank(requirementOffers.getPositionType())) {
        			candidate.setDesignationType(requirementOffers.getPositionType());
        		}else {
        			candidate.setDesignationType("C2C");
        		}
    		}
    		
    		
    		
    		candidate.setStatus(Constants.RECORD_ACTIVE);
    		candidate.setCurrentRate(requirementOffers.getAmount());
    		candidate.setCurrentRateType(requirementOffers.getPaymentMode());
    		candidate.setCurrentRateCurrency(requirementOffers.getPaymentType());
    		candidate.setExpectedRate(requirementOffers.getAmount());
    		candidate.setExpectedRateCurrencey(requirementOffers.getPaymentType());
    		candidate.setRateType(requirementOffers.getPaymentMode());
    		
    		if(requirementSubmission.getSourceSubmission() != null){
    			OrgRelations orgRelations = orgRelationsDao.getOrgRelationByResponderLocationIdAndLocationId(requirementOffers.getSubmissionId().getVendorLocationId().getId(),orgLocations.getId());
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
        		candidate.setOrgLocationId(orgLocations);
        		candidate.setIsAssociate(Constants.YES);
    		}else{
    			if(clientSubmission != null){
    				candidate.setOrgRelationId(requirementOffers.getSubmissionId().getRequirement().getClientId());
    				candidate.setSpocId(requirementOffers.getSubmissionId().getRequirement().getSpocId());
    				candidate.setOrgLocationId(clientSubmission.getOrgLocations());
    				if(candidate.getParentCandidateId() == null && clientSubmission.getCandidate() != null) {
    					candidate.setParentCandidateId(clientSubmission.getCandidate());
    				}
				}else{
    				candidate.setOrgLocationId(orgLocations);
				}
    			candidate.setIsAssociate(Constants.NO);
    		}
    		
    		candidate.setGeneratedId(candidateService.getCandidateId(orgLocations));
    		candidate.setCreatedDate(new Date());
    		candidate.setUpdatedDate(new Date());
    		candidate.setStatus(1);
    		
    		Candidate vendorCandidate = null;
    		
    		if(requirementSubmission.getSourceSubmission() != null){
    		vendorCandidate = candidateService.findByCandidateEmail(requirementSubmission.getSourceSubmission().getCandidate().getEmail(), requirementSubmission.getSourceSubmission().getOrgLocations().getId());
    		if(vendorCandidate == null){
    			vendorCandidate = new Candidate();
    			MasterCandidate vendorMasterCandidate = masterCandidateDao.getMasterCandidateByEmailOrPhoneNumber(requirementSubmission.getSourceSubmission().getCandidate().getEmail(), requirementSubmission.getSourceSubmission().getCandidate().getMobilePhone());
    			if(vendorMasterCandidate != null){
        			vendorCandidate.setMasterCandidateId(vendorMasterCandidate);
        		}
    			vendorCandidate.setOrgLocationId(requirementSubmission.getSourceSubmission().getOrgLocations());
    			vendorCandidate.setCandidateType("Candidate");
    			vendorCandidate.setCandidateUUID(SourceLeadRandomCodeHelper.generateRandomUUID());
    			vendorCandidate.setFirstName(requirementSubmission.getSourceSubmission().getCandidate().getFirstName());
    			vendorCandidate.setLastName(requirementSubmission.getSourceSubmission().getCandidate().getLastName());
    			vendorCandidate.setEmail(requirementSubmission.getSourceSubmission().getCandidate().getEmail());
    			vendorCandidate.setCountry(requirementSubmission.getSourceSubmission().getCandidate().getCountry());
    			vendorCandidate.setStatus(1);
    			vendorCandidate = candidateService.saveOrUpdateCandidate(vendorCandidate);
    			candidateService.saveOrUpdateCandidateElkDocument(vendorCandidate);
    		 }
    		}
    		
    		if(vendorCandidate != null) {
    			candidate.setParentCandidateId(vendorCandidate);
    			if(vendorCandidate.getPrimarySkill() != null && !vendorCandidate.getPrimarySkill().isEmpty()) {
        			candidate.setPrimarySkill(vendorCandidate.getPrimarySkill());
        		}
    		}
    		
    		
    		candidateService.saveOrUpdateCandidate(candidate);
	    	candidateService.saveOrUpdateCandidateElkDocument(candidate);
	    	
	    	if(requirementSubmission != null && requirementSubmission.getCandidateResumes() != null) {
    			CandidateResumes resume = candidateResumesDao.getCandidateResumeByResumeId(requirementSubmission.getCandidateResumes().getId());
    			if(resume != null) {
    				CandidateResumes candResume = new CandidateResumes();
    				candResume.setOrgLocationId(orgLocations);
    				candResume.setCandidateId(candidate);
    				candResume.setFileContentType(resume.getFileContentType());
    				candResume.setFileName(resume.getFileName());
    				candResume.setFileURL(resume.getFileURL());
    				candResume.setResumeUUID(SourceLeadRandomCodeHelper.generateRandomUUID());
    				candResume.setStatus(Constants.RECORD_ACTIVE);
    				candResume.setCreatedBy(orgLocations.getCreatedBy());
    				if(orgLocations.getDateFormat() != null) {
    					candResume.setCreatedDate(commonUtil.getOrgLocationCurrentDate(orgLocations.getDateFormat(), new Date()));	
    					candResume.setUpdatedDate(commonUtil.getOrgLocationCurrentDate(orgLocations.getDateFormat(), new Date()));
    				}else {
    					candResume.setCreatedDate(commonUtil.getOrgLocationCurrentDate("dd-MM-yyyy", new Date()));
    					candResume.setUpdatedDate(commonUtil.getOrgLocationCurrentDate(orgLocations.getDateFormat(), new Date()));
    				}
    				
    				candidateResumesDao.storeCandidateResumes(candResume);
    			}
    		}
	    	
	    	if(requirementSubmission.getSourceSubmission() != null){
	    		requirementSubmission.setVendorCandidateId(vendorCandidate);
		    	requirementSubmission.setCandidate(candidate);
		    	requirementSubmissionService.storeRequirementSubmission(requirementSubmission);
	    	}else{
	    		if(clientSubmission != null){
		    			clientSubmission.setVendorCandidateId(requirementSubmission.getCandidate());
		    			clientSubmission.setCandidate(candidate);
		    			requirementSubmissionService.storeRequirementSubmission(clientSubmission);
	    			}else{
		    			requirementSubmission.setVendorCandidateId(requirementSubmission.getCandidate());
		    			requirementSubmission.setCandidate(candidate);
		    			requirementSubmissionService.storeRequirementSubmission(requirementSubmission);
	    			}
	    	}
    		
		
		return candidate;
		
	}

	@Override
	public void sendApplicantOfferAcceptMail(RequirementOffers requirementOffers) throws Exception
	{
		User touser = userService.getUserByUserCode(requirementOffers.getCreatedBy());
		OrgLocations orgloc = orgLocationService.getOrgLocations(requirementOffers.getSubmissionId().getOrgLocations().getId());
		RequirementSubmissionTemplateFields tempFields = requirementSubmissionTemplateFieldsService.getSubmissionsTemplateFieldsByRequirementSubmissionsId(requirementOffers.getSubmissionId().getId());
	//	SimpleDateFormat simpleDateFormat = orgLocations.getLocFullFormDateFormatter();
		EmailMessage emailMessage = new EmailMessage();
		StringBuilder toUserSb = new StringBuilder();
		if(StringUtils.isNotBlank(touser.getLastName())){
			toUserSb.append(touser.getFirstName()).append(" ").append(touser.getLastName());
		}else{
			toUserSb.append(touser.getFirstName());
		}
		emailMessage.setFirstName(touser.getFirstName());
		emailMessage.setLastName(touser.getLastName());
		emailMessage.setToEmail(touser.getEmail());
		emailMessage.setFromEmail(tempFields.getEmailId());
		StringBuilder contactBuilder=new StringBuilder();
		if(StringUtils.isNotBlank(tempFields.getLastName())){
			emailMessage.setFromName(contactBuilder.append(tempFields.getFirstName()).append(" ").append(tempFields.getLastName()).toString());
		}else{
			emailMessage.setFromName(contactBuilder.append(tempFields.getFirstName()).toString());
		}
		emailMessage.setEmailType(Constants.CUSTOM_USER_EMAIL);
		// setting the class filed values
		Map<String, String> globalVarsMap = new HashMap<String, String>();
			
			if(StringUtils.isNotBlank(tempFields.getLastName())){
				StringBuilder applicantNameBuilder=new StringBuilder();
				globalVarsMap.put("applicantName",applicantNameBuilder.append(tempFields.getFirstName()).append("").append(tempFields.getLastName()).toString());	
			}
			else{
				globalVarsMap.put("applicantName",tempFields.getFirstName());	
			}
			if(requirementOffers.getSubmissionId() != null && requirementOffers.getSubmissionId().getRequirement() != null){
				StringBuilder reqSb = new StringBuilder();
				if(StringUtils.isNotBlank(requirementOffers.getSubmissionId().getRequirement().getJobId()))
					globalVarsMap.put("jobTitle", reqSb.append(requirementOffers.getSubmissionId().getRequirement().getJobId()).append(" - ").append(requirementOffers.getSubmissionId().getRequirement().getJobTitle()).toString());
				else
					globalVarsMap.put("jobTitle", reqSb.append(requirementOffers.getSubmissionId().getRequirement().getJobTitle()).toString());
			}
			else{
				globalVarsMap.put("jobTitle", "---");
			}
			
			globalVarsMap.put("orgUser", toUserSb.toString());
			
			if(null != requirementOffers.getDateOfJoining() ){
			requirementOffers.setDateOfJoiningStr(commonUtil.dateStrFromOrgDateFormatter(orgloc.getDateFormat(),(requirementOffers.getDateOfJoining())));	
			globalVarsMap.put("joinDate", requirementOffers.getDateOfJoiningStr());
			}
			else{
			globalVarsMap.put("joinDate", "---");
			}
		

		// getting the current EmailScenario by EmailScenario unique name and
		// entity type
		EmailScenario notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.APPLICANT_OFFER_ACCEPT_EMAIL, Constants.ENTITY_TYPE_NOTIFICATION);


		// calling the sendEmail method of emailScenerioUtil
		commonUtil.sendTemplateEmail(emailMessage, globalVarsMap,orgloc.getOrg(),orgloc,Constants.APPLICANT_OFFER_ACCEPT_EMAIL,new ArrayList<EmailAttachment>());
		
       String notificationbody = emailScenerioUtil.buildTemplateBody(notificationScenario.getBody(), globalVarsMap,notificationScenario);
       notificationScenario.setBody(notificationbody);
       
       StringBuilder actionLinkBuilder = new StringBuilder();
       String actionLink =actionLinkBuilder.append("").append(atsUrl).append("/").append("requirementSummary?requirementUUID="+requirementOffers.getSubmissionId().getRequirement().getRequirementUUID()).toString();	 
       emailScenerioUtil.sendNotification(actionLink, touser, touser , orgloc, notificationScenario,new HashMap<String,String>());
		
	}
	
	@Override
	public void sendApplicantOfferRejectMail(RequirementOffers requirementOffers) throws Exception
	{
		User touser = userService.getUserByUserCode(requirementOffers.getCreatedBy());
		OrgLocations orgloc = orgLocationService.getOrgLocations(requirementOffers.getSubmissionId().getOrgLocations().getId());
		RequirementSubmissionTemplateFields tempFields = requirementSubmissionTemplateFieldsService.getSubmissionsTemplateFieldsByRequirementSubmissionsId(requirementOffers.getSubmissionId().getId());
	//	SimpleDateFormat simpleDateFormat = orgLocations.getLocFullFormDateFormatter();
		EmailMessage emailMessage = new EmailMessage();
		StringBuilder toUserSb = new StringBuilder();
		if(StringUtils.isNotBlank(touser.getLastName())){
			toUserSb.append(touser.getFirstName()).append(" ").append(touser.getLastName());
		}else{
			toUserSb.append(touser.getFirstName());
		}
		emailMessage.setFirstName(touser.getFirstName());
		emailMessage.setLastName(touser.getLastName());
		emailMessage.setToEmail(touser.getEmail());
		emailMessage.setFromEmail(tempFields.getEmailId());
		StringBuilder contactBuilder=new StringBuilder();
		if(StringUtils.isNotBlank(tempFields.getLastName())){
			emailMessage.setFromName(contactBuilder.append(tempFields.getFirstName()).append(" ").append(tempFields.getLastName()).toString());
		}else{
			emailMessage.setFromName(contactBuilder.append(tempFields.getFirstName()).toString());
		}
		emailMessage.setEmailType(Constants.CUSTOM_USER_EMAIL);
		// setting the class filed values
		Map<String, String> globalVarsMap = new HashMap<String, String>();
			
			if(StringUtils.isNotBlank(tempFields.getLastName())){
				StringBuilder applicantNameBuilder=new StringBuilder();
				globalVarsMap.put("applicantName",applicantNameBuilder.append(tempFields.getFirstName()).append("").append(tempFields.getLastName()).toString());	
			}
			else{
				globalVarsMap.put("applicantName",tempFields.getFirstName());	
			}
			if(requirementOffers.getSubmissionId() != null && requirementOffers.getSubmissionId().getRequirement() != null){
				StringBuilder reqSb = new StringBuilder();
				if(StringUtils.isNotBlank(requirementOffers.getSubmissionId().getRequirement().getJobId()))
					globalVarsMap.put("jobTitle", reqSb.append(requirementOffers.getSubmissionId().getRequirement().getJobId()).append(" - ").append(requirementOffers.getSubmissionId().getRequirement().getJobTitle()).toString());
				else
					globalVarsMap.put("jobTitle", reqSb.append(requirementOffers.getSubmissionId().getRequirement().getJobTitle()).toString());
			}
			else{
				globalVarsMap.put("jobTitle", "---");
			}
			
			globalVarsMap.put("orgUser", toUserSb.toString());
			
			if(null != requirementOffers.getRejectedReason()){
				globalVarsMap.put("reason", requirementOffers.getRejectedReason());
			}
			else{
				globalVarsMap.put("reason", "---");
			}
		
		// getting the current EmailScenario by EmailScenario unique name and
		// entity type
		EmailScenario notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.APPLICANT_OFFER_REJECT_EMAIL, Constants.ENTITY_TYPE_NOTIFICATION);



		// calling the sendEmail method of emailScenerioUtil
		commonUtil.sendTemplateEmail(emailMessage, globalVarsMap,orgloc.getOrg(),orgloc,Constants.APPLICANT_OFFER_REJECT_EMAIL,new ArrayList<EmailAttachment>());
		
       String notificationbody = emailScenerioUtil.buildTemplateBody(notificationScenario.getBody(), globalVarsMap,notificationScenario);
       notificationScenario.setBody(notificationbody);
       
       StringBuilder actionLinkBuilder = new StringBuilder();
       String actionLink =actionLinkBuilder.append("").append(atsUrl).append("/").append("requirementSummary?type=APPLICANTS&requirementUUID="+requirementOffers.getSubmissionId().getRequirement().getRequirementUUID()+"&candidateUUID="+requirementOffers.getSubmissionId().getCandidate().getCandidateUUID()+"&submissionId="+requirementOffers.getSubmissionId().getId()).toString();	 
       emailScenerioUtil.sendNotification(actionLink, touser, touser , orgloc, notificationScenario,new HashMap<String,String>());
		
	}


	@Override
	public RequirementOffers getOnboardRequirementOfferId(Long offerId) throws Exception {
		return requirementOffersDao.getOnboardRequirementOfferId(offerId);
	}


	@Override
	public List<RequirementOffers> getOfferAcceptedCandidatesByOrgLocationIdAndDateOfJoin(Long orgLocationId,
			Date joiningDate) throws Exception {
		return requirementOffersDao.getOfferAcceptedCandidatesByOrgLocationIdAndDateOfJoin(orgLocationId, joiningDate);
	}

}