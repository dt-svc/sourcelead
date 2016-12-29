package com.bis.sourcelead.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.RequirementInterviewDao;
import com.bis.sourcelead.dao.RequirementInterviewerDao;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.EmailAttachment;
import com.bis.sourcelead.entity.EmailMessage;
import com.bis.sourcelead.entity.EmailRecipient;
import com.bis.sourcelead.entity.EmailScenario;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.RequirementInterview;
import com.bis.sourcelead.entity.RequirementInterviewHistory;
import com.bis.sourcelead.entity.RequirementInterviewer;
import com.bis.sourcelead.entity.RequirementSubmission;
import com.bis.sourcelead.entity.RequirementSubmissionHistory;
import com.bis.sourcelead.entity.SubmissionTemplate;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.enums.ApplicantSubmissionStatus;
import com.bis.sourcelead.service.CandidateService;
import com.bis.sourcelead.service.EmailScenarioService;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.RequirementInterviewHistoryService;
import com.bis.sourcelead.service.RequirementInterviewService;
import com.bis.sourcelead.service.RequirementInterviewerService;
import com.bis.sourcelead.service.RequirementSubmissionHistoryService;
import com.bis.sourcelead.service.RequirementSubmissionService;
import com.bis.sourcelead.service.SubmissionTemplateService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.CalendarUtil;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.EmailScenerioUtil;

@Service
public class RequirementInterviewServiceImpl implements RequirementInterviewService{
	
	private static final Logger logger = LoggerFactory.getLogger(RequirementInterviewServiceImpl.class);
	
	@Autowired
	private CommonUtil commonUtil;
	
	@Autowired
	CandidateService candidateService;
	
	@Autowired
	EmailScenarioService emailScenarioService;
	
	@Autowired
	EmailScenerioUtil emailScenerioUtil;
	
	@Autowired
	private RequirementInterviewDao requirementInterviewDao;
	
	@Autowired
	private RequirementInterviewerDao requirementInterviewerDao;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private OrgLocationService orgLocationService;
	
	@Autowired
	private RequirementSubmissionService requirementSubmissionService;
	
	@Autowired
	private RequirementInterviewerService requirementInterviewerService;
	
	@Autowired
	private RequirementInterviewHistoryService requirementInterviewHistoryService;
	
	@Autowired
	private SubmissionTemplateService submissionTemplateService;
	
	@Value("${sourceleadUrl}")
    private String sourceleadUrl;
	
	@Autowired
	private RequirementSubmissionHistoryService requirementSubmissionHistoryService;
	
	@Override
	public void saveRequirementInterview(JSONObject jsonObj, User user, OrgLocations orgLocations, Org loginOrg) throws Exception  {
		
		String maxAdminTimeTypeRowNum = jsonObj.getString("maxTsRowNum");
		int maxCount = Integer.parseInt(maxAdminTimeTypeRowNum);
		RequirementInterview requirementInterview  = null;
		
		for (long c = 1; c < maxCount; c++) {
				if(jsonObj.has(c+"applicant") && !jsonObj.getString(c+"applicant").isEmpty()){
						String applicantIds = jsonObj.getString(c+"applicant");
						applicantIds = applicantIds.replaceAll("\\[", "").replaceAll("\\]","");
						String[] applicants = applicantIds.split(",");
					
					     for(int i=0 ;i<applicants.length;i++ ){
								requirementInterview  = new RequirementInterview();
								RequirementSubmission submission = new RequirementSubmission();
								submission.setId(Long.parseLong(applicants[i].replace("\"", "")));
								requirementInterview.setSubmissionId(submission);
								
								if(jsonObj.has(c+"dateOfInterview") && jsonObj.getString(c+"dateOfInterview") != null && !"".equalsIgnoreCase(jsonObj.getString(c+"dateOfInterview"))){
									requirementInterview.setDateOfInterview(commonUtil.getDateFormat(jsonObj.getString(c+"dateOfInterview")));
								}
								
								if(jsonObj.has(c+"interviewType") && !jsonObj.getString(c+"interviewType").isEmpty()){
									requirementInterview.setInterviewType(jsonObj.getString(c+"interviewType"));
								}
								
								/*if(jsonObj.has(c+"timeZone") && !jsonObj.getString(c+"timeZone").isEmpty()){
									requirementInterview.setTimeZone(jsonObj.getString(c+"timeZone"));
								}*/
								
								if(jsonObj.has(c+"startTime") && !jsonObj.getString(c+"startTime").isEmpty()){
									  Date startTime =  CalendarUtil.getDateFromDateAndTime(commonUtil.getDateFormat(jsonObj.getString(c+"dateOfInterview")), 
											      jsonObj.getString(c+"startTime"));
									   requirementInterview.setStartTime(startTime);
								}
								
								if(jsonObj.has(c+"endTime") && !jsonObj.getString(c+"endTime").isEmpty()){
									Date endTime =  CalendarUtil.getDateFromDateAndTime(commonUtil.getDateFormat(jsonObj.getString(c+"dateOfInterview")), 
										      jsonObj.getString(c+"endTime"));
								      requirementInterview.setEndTime(endTime);
								}
								
								if(jsonObj.has(c+"location") && !jsonObj.getString(c+"location").isEmpty()){
									requirementInterview.setLocation(jsonObj.getString(c+"location"));
								}
								
								if(jsonObj.has("notificationToOthers") && !jsonObj.getString("notificationToOthers").isEmpty()){
									requirementInterview.setNotificationToOthers(jsonObj.getString("notificationToOthers"));
								}
								
								if(jsonObj.has("notificationToOthersEmails") && !jsonObj.getString("notificationToOthersEmails").isEmpty()){
									requirementInterview.setNotificationToOthersEmails(jsonObj.getString("notificationToOthersEmails"));
								}
								
								if(jsonObj.has("reminderHours") && !jsonObj.getString("reminderHours").isEmpty() && jsonObj.has("reminderMinutes") && !jsonObj.getString("reminderMinutes").isEmpty()){
									Integer reminderHours = Integer.parseInt(jsonObj.getString("reminderHours"));
									Integer reminderMinutes = Integer.parseInt(jsonObj.getString("reminderMinutes"));
									Integer remainedBefore = ((reminderHours*60) + reminderMinutes);
									
									requirementInterview.setRemainedBefore(remainedBefore.toString());
								}
								
							   	if(jsonObj.has("interviewSubmissionTemplate") && jsonObj.getString("interviewSubmissionTemplate") != null && !"".equalsIgnoreCase(jsonObj.getString("interviewSubmissionTemplate"))){
							   			SubmissionTemplate sub= submissionTemplateService.getSubmissionTemplateById(Long.parseLong(jsonObj.getString("interviewSubmissionTemplate")));
										   requirementInterview.setInterviewTemplateId(sub);
							   	}
							   requirementInterview.setStatus(Constants.RECORD_ACTIVE);
					           requirementInterview.setCreatedDate(new Date());
					           requirementInterview.setCreatedBy(user.getUserCode());
					           requirementInterview.setUpdatedDate(new Date());
					           requirementInterview.setUpdatedBy(user.getUserCode());
					           RequirementInterview updateInterview = requirementInterviewDao.storeRequirementInterview(requirementInterview);
					           RequirementSubmissionHistory requirementSubmissionHistory = new RequirementSubmissionHistory();
					           RequirementSubmission requirementSubmission = requirementSubmissionService.getRequirementSubmissionById(Long.parseLong(applicants[i].replace("\"", "")));
					           requirementSubmissionHistory.setFromStage(requirementSubmission.getSubmissionStatus().getValue());
					           if(jsonObj.has("isClientInterview")){
					        	   requirementSubmission.setSubmissionStatus(ApplicantSubmissionStatus.CLIENT_INTERVIEW);
					           }else{
					        	   requirementSubmission.setSubmissionStatus(ApplicantSubmissionStatus.INTERVIEW);
					           }
					           requirementSubmissionService.storeRequirementSubmission(requirementSubmission);
					           requirementSubmissionHistoryService.updateRequirementHistory(requirementSubmission, user, requirementSubmissionHistory);
					           
					           if(requirementSubmission.getSourceSubmission() != null){
					        	   RequirementSubmission parentSubmission = requirementSubmissionService.getRequirementSubmissionById(requirementSubmission.getSourceSubmission().getId());
						           if(jsonObj.has("notificationToSpoc") && !jsonObj.getString("notificationToSpoc").isEmpty() && parentSubmission != null){
						        	   parentSubmission.setSubmissionStatus(ApplicantSubmissionStatus.CLIENT_INTERVIEW);
						        	   requirementSubmissionService.storeRequirementSubmission(parentSubmission);
						           }
					           }
					           
					           
					           //to save interview history
					           RequirementInterviewHistory interviewHistory = new RequirementInterviewHistory();
					           interviewHistory.setInterviewId(updateInterview);
					           interviewHistory.setSubmissionId(submission);
					           interviewHistory.setOrgLocationId(orgLocations);
					           if(jsonObj.has("isClientInterview")){
					        	   interviewHistory.setInterviewSourceType(Constants.CLIENT_INTERVIEW);
					           }else{
					        	   interviewHistory.setInterviewSourceType(Constants.INTERVIEW);
					           }
					           if(jsonObj.has("notificationToApplicant") && !jsonObj.getString("notificationToApplicant").isEmpty()){
					        	   interviewHistory.setNotificationToApplicant(jsonObj.getString("notificationToApplicant"));
					           }
					           if(jsonObj.has("notificationToSpoc") && !jsonObj.getString("notificationToSpoc").isEmpty()){
					        	   interviewHistory.setNotificationToSpoc(jsonObj.getString("notificationToSpoc"));
					           }
					           interviewHistory.setStatus(Constants.RECORD_ACTIVE);
					           interviewHistory.setCreatedDate(new Date());
					           interviewHistory.setCreatedBy(user.getUserCode());
					           interviewHistory.setUpdatedDate(new Date());
					           interviewHistory.setUpdatedBy(user.getUserCode());
					           requirementInterviewHistoryService.storeRequirementInterviewHistory(interviewHistory);
					           
					           //to save vendor interview history
					           if(jsonObj.has("notificationToSpoc") && !jsonObj.getString("notificationToSpoc").isEmpty()&& jsonObj.getString("notificationToSpoc").equalsIgnoreCase("Y") && requirementSubmission.getVendorLocationId() != null){
					        	   RequirementInterviewHistory vendorInterviewHistory = new RequirementInterviewHistory();
					        	   vendorInterviewHistory.setInterviewId(updateInterview);
					        	   vendorInterviewHistory.setSubmissionId(requirementSubmission.getSourceSubmission());
					        	   vendorInterviewHistory.setOrgLocationId(requirementSubmission.getVendorLocationId());
						           vendorInterviewHistory.setInterviewSourceType(Constants.CLIENT_INTERVIEW);
						           vendorInterviewHistory.setNotificationToApplicant(Constants.NO);
						           vendorInterviewHistory.setNotificationToSpoc(Constants.NO);
						           vendorInterviewHistory.setStatus(Constants.RECORD_ACTIVE);
						           vendorInterviewHistory.setCreatedDate(new Date());
						           vendorInterviewHistory.setCreatedBy(user.getUserCode());
						           vendorInterviewHistory.setUpdatedDate(new Date());
						           vendorInterviewHistory.setUpdatedBy(user.getUserCode());
						           requirementInterviewHistoryService.storeRequirementInterviewHistory(vendorInterviewHistory);
					           }
					        		   
					        		   
					          Candidate candidate = candidateService.getCandidateByCandidateId(requirementSubmission.getCandidate().getId());
					           
					           
					           if(jsonObj.has("notificationToApplicant") && !jsonObj.getString("notificationToApplicant").isEmpty()&& jsonObj.getString("notificationToApplicant").equalsIgnoreCase("Y")){
					        	   sendEmailToApplicants(candidate,orgLocations,requirementSubmission, user, loginOrg, requirementInterview);
					           }
					           

					           if(jsonObj.has("notificationToSpoc") && !jsonObj.getString("notificationToSpoc").isEmpty()&& jsonObj.getString("notificationToSpoc").equalsIgnoreCase("Y") && requirementSubmission.getVendorLocationId() != null){

					        	   sendEmailToVendors(candidate,requirementSubmission, user,loginOrg, requirementInterview,orgLocations);

					           }

	           
					           if(jsonObj.has("notificationToOthersEmails") && !jsonObj.getString("notificationToOthersEmails").isEmpty()){
									String[] othersEmails = jsonObj.getString("notificationToOthersEmails").split(",");
									for (String emailId : othersEmails) {
										User otherUser = new User();
										otherUser.setEmail(emailId);
										sendEmailToInterviewrs(candidate,otherUser,requirementSubmission, user, loginOrg,requirementInterview,orgLocations);
									}
									
									
							  }

					           //to store RequirementInterviewers
					           if(jsonObj.has(c+"interviewer") && !jsonObj.getString(c+"interviewer").isEmpty()){
					        	    String interviewerIds = jsonObj.getString(c+"interviewer");
					        	    interviewerIds = interviewerIds.replaceAll("\\[", "").replaceAll("\\]","");
									String[] interviewers = interviewerIds.split(",");
									
									for(int j=0 ;j<interviewers.length;j++ ){
										 RequirementInterviewer interviewer = new RequirementInterviewer();
								         interviewer.setRequirementInterview(requirementInterview);
										 User interviewerUser = userService.getUserById(Long.parseLong(interviewers[j].replace("\"", "")));
										 interviewer.setInterviewerUser(interviewerUser);
										 interviewer.setStatus(Constants.RECORD_ACTIVE);
								         interviewer.setCreatedDate(new Date());
								         interviewer.setCreatedBy(user.getUserCode());
								         interviewer.setUpdatedDate(new Date());
							             interviewer.setUpdatedBy(user.getUserCode());
							            /* sendEmailToInterviewrs(candidate,interviewerUser,requirementSubmission, user, jsonObj.getString(c+"startTime"), jsonObj.getString(c+"endTime"), loginOrg, jsonObj.getString(c+"dateOfInterview"), jsonObj.getString(c+"location"),jsonObj.getString(c+"interviewType"),orgLocations);*/
							             sendEmailToInterviewrs(candidate,interviewerUser,requirementSubmission, user, loginOrg,requirementInterview,orgLocations);
								         requirementInterviewerDao.storeRequirementInterviewer(interviewer);
								         if(jsonObj.has("interviewSubmissionTemplate") && !jsonObj.getString("interviewSubmissionTemplate").isEmpty()){
							            	 String templateId = jsonObj.getString("interviewSubmissionTemplate");
							            	 interViewerFeedbackTemplateSendEmailToInterviewrs(candidate,interviewerUser,requirementSubmission, user, loginOrg,requirementInterview,orgLocations,templateId,interviewer); 
							             }
									}
					           }
							}
				}
			continue;
		}
		
	}
	@Override
	public void sendEmailToInterviewrs(Candidate candidate, User interviewerUser,RequirementSubmission requirementSubmission, User loginUser,Org org,RequirementInterview requirementInterview,OrgLocations orgLocations)	throws Exception {
		 
		EmailMessage emailMessage = new EmailMessage();
		
		if(interviewerUser.getFirstName()!=null){
			 emailMessage.setFirstName(interviewerUser.getFirstName());
		}
		else{
			 emailMessage.setFirstName("");
		}
		if(interviewerUser.getLastName()!=null){
			 emailMessage.setLastName(interviewerUser.getLastName());
		}
		else{
			 emailMessage.setLastName("");
		}
		 emailMessage.setToEmail(interviewerUser.getEmail());
		 emailMessage.setFromEmail("peopleaxisindia@gmail.com");
		 emailMessage.setCreatedBy(loginUser.getUserCode());
		 emailMessage.setFromName(loginUser.getFirstName()+" "+loginUser.getLastName());
		 
		 String candidateName = candidate.getFirstName();
		 if(candidate.getLastName()!=null){
			 candidateName=candidateName+" "+candidate.getLastName();
		 }
		 
		 String dateOfInterviewValue ="";
		 if(requirementInterview.getDateOfInterview()!=null){
			 dateOfInterviewValue = Constants.DMY.format(requirementInterview.getDateOfInterview());
		 }
		 String interviewTypeValue="--";
		 if(requirementInterview.getInterviewType() !=null){
			 interviewTypeValue = requirementInterview.getInterviewType();
		 }
	 
		 String time = "";
		 String endTime = ""; 
		 if(requirementInterview.getStartTime() != null){
	    	 String hours = Integer.toString(requirementInterview.getStartTime().getHours());
	    	 String minutes = Integer.toString(requirementInterview.getStartTime().getMinutes());
	    	 time = CalendarUtil.get12FormatTimeFrom24FormatTime(hours+":"+minutes);
	    }
	    
	    if(requirementInterview.getEndTime() != null){
	    	 String hours = Integer.toString(requirementInterview.getEndTime().getHours());
	    	 String minutes = Integer.toString(requirementInterview.getEndTime().getMinutes());
	    	 endTime = CalendarUtil.get12FormatTimeFrom24FormatTime(hours+":"+minutes);
	    }
		 
		 if(endTime!=null){
			  time=time+" - "+endTime;
		 }
		 dateOfInterviewValue = dateOfInterviewValue +" "+time;
		//setting the class filed values
		 Map<String, String> globalVarsMap = new HashMap<String, String>();
			 globalVarsMap.put("interviewerFirstName", emailMessage.getFirstName());
			 globalVarsMap.put("interviewerLastName",emailMessage.getLastName());
			 globalVarsMap.put("loginUser", emailMessage.getFromName());
			 globalVarsMap.put("requirementTitle", requirementSubmission.getRequirement().getJobTitle());
			 globalVarsMap.put("interviewDate", dateOfInterviewValue);
			 globalVarsMap.put("interviewType",interviewTypeValue);
			 globalVarsMap.put("applicantName",candidateName);
			 globalVarsMap.put("Location",requirementInterview.getLocation());
			 
		 //setting the Recipient information
		 ArrayList<EmailRecipient> emailRecipientList = new ArrayList<EmailRecipient>();
		 EmailRecipient emailRecipient = new EmailRecipient();
		 emailRecipient.setEmail(emailMessage.getToEmail());
		 emailRecipient.setName(emailMessage.getFirstName()+" "+emailMessage.getLastName());
		 emailRecipientList.add(emailRecipient);
		
		 //getting the current EmailScenario by EmailScenario unique name and entity type
		 EmailScenario  emailScenario = null;
			 emailScenario =  emailScenarioService.getEmailScenarioByUniqueName(Constants.INTERVIEWER_INVITATION,Constants.ENTITY_TYPE_EMAIL);
		
		 //covert subject,body and signature
		 String subject = emailScenerioUtil.bulidSubject(emailScenario.getSubject(),globalVarsMap);
		 String body = emailScenerioUtil.buildTempBody(emailScenario.getBody(),globalVarsMap);
		 String signature = emailScenerioUtil.buildSignature(emailScenario.getSignature(),globalVarsMap);
		 String url = "";
		 
		 //construct HTML template using required data
		 String emailTemplate = emailScenerioUtil.AssembleEmailMessage(emailScenario,emailMessage, subject, body, signature, url, new Org());
		 emailMessage.setSubject(subject);
		 emailMessage.setMessageBody(emailTemplate);
		 
		 
		 //calling the sendEmail method of emailScenerioUtil
		 emailScenerioUtil.sendEmail(emailMessage,orgLocations.getId(),emailRecipientList,new ArrayList<EmailAttachment>());

		 EmailScenario noificationEmailScenario =  emailScenarioService.getEmailScenarioByUniqueName(Constants.INTERVIEWER_INVITATION,Constants.ENTITY_TYPE_NOTIFICATION); 
		 String actionLink = "";
		 
		 if(interviewerUser.getFirstName()!=null){
		 String notificationbody = emailScenerioUtil.buildTempBody(noificationEmailScenario.getBody(),globalVarsMap);
		 noificationEmailScenario.setBody(notificationbody);
		 
		 emailScenerioUtil.sendNotification(actionLink, loginUser, interviewerUser, orgLocations, noificationEmailScenario);
		 }
	}
	
	@Override
	public void sendEmailToApplicants(Candidate candidate,OrgLocations loginOrgLocations,RequirementSubmission requirementSubmission, User loginUser, Org loginOrg, RequirementInterview requirementInterview) throws Exception {
		 EmailMessage emailMessage = new EmailMessage();
		 emailMessage.setFirstName(candidate.getFirstName());
		 emailMessage.setLastName(candidate.getLastName());
		 emailMessage.setToEmail(candidate.getEmail());
		 emailMessage.setFromEmail("peopleaxisindia@gmail.com");
		 emailMessage.setCreatedBy(loginUser.getUserCode());
		 emailMessage.setFromName(loginUser.getFirstName()+" "+loginUser.getLastName());
		 
		 String time = "";
		 String endTime = "";
		 String dateOfInterviewStr ="";
		 if(requirementInterview.getDateOfInterview()!=null){
		  dateOfInterviewStr = Constants.DMY.format(requirementInterview.getDateOfInterview());
		 }

		 
		 if(requirementInterview.getStartTime() != null){
	    	 String hours = Integer.toString(requirementInterview.getStartTime().getHours());
	    	 String minutes = Integer.toString(requirementInterview.getStartTime().getMinutes());
	    	 time = CalendarUtil.get12FormatTimeFrom24FormatTime(hours+":"+minutes);
	    }
	    
	    if(requirementInterview.getEndTime() != null){
	    	 String hours = Integer.toString(requirementInterview.getEndTime().getHours());
	    	 String minutes = Integer.toString(requirementInterview.getEndTime().getMinutes());
	    	 endTime = CalendarUtil.get12FormatTimeFrom24FormatTime(hours+":"+minutes);
	    }
		 
		 if(endTime!=null){
			  time=time+" - "+endTime;
		 }
		 String interviewTypeValue="--";
		 if(requirementInterview.getInterviewType() !=null){
			 interviewTypeValue = requirementInterview.getInterviewType();
		 }
		//setting the class filed values
		 Map<String, String> globalVarsMap = new HashMap<String, String>();
			 globalVarsMap.put("applicantFirstName", emailMessage.getFirstName());
			 globalVarsMap.put("applicantLastName",emailMessage.getLastName());
			 globalVarsMap.put("loginUser", emailMessage.getFromName());
			 globalVarsMap.put("requirementTitle", requirementSubmission.getRequirement().getJobTitle());
			 globalVarsMap.put("loginOrgName",loginOrg.getOrgName());
			 globalVarsMap.put("interviewDate", dateOfInterviewStr);
			 globalVarsMap.put("interviewTime",time);
			 globalVarsMap.put("interviewType",interviewTypeValue);
			 globalVarsMap.put("Location",requirementInterview.getLocation());
			 StringBuilder acceptBuilder = new StringBuilder();
			 StringBuilder rejectBuilder = new StringBuilder();
			 StringBuilder tentativeBuilder = new StringBuilder();
			 
			 globalVarsMap.put("acceptURL",acceptBuilder.append("<a href='").append(sourceleadUrl).append("/acceptOrRejectInterview?interviewStatus=Attending&requirementInterviewId=").append(requirementInterview.getId()).append("'style='text-decoration:none;float:left; margin-left:55px;'><div style=' width:220px; border-radius:30px; background-color:#5cb85c; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Accept</div></a>").toString());
			 globalVarsMap.put("rejectURL",rejectBuilder.append("<a href='").append(sourceleadUrl).append("/acceptOrRejectInterview?interviewStatus=Not Attending&requirementInterviewId=").append(requirementInterview.getId()).append("'style='text-decoration:none;float:left;margin-left:55px;'><div style=' width:220px; border-radius:30px; background-color:#e74c51; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Reject</div></a>").toString());
			 globalVarsMap.put("tentativeURL",tentativeBuilder.append("<a href='").append(sourceleadUrl).append("/acceptOrRejectInterview?interviewStatus=Maybe&requirementInterviewId=").append(requirementInterview.getId()).append("'style='text-decoration:none;float:left;margin-left:55px;'><div style='width:220px; border-radius:30px; background-color:#f0ad4e;; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Tentative</div></a>").toString());
			 
			/* globalVarsMap.put("acceptURL","<a href='"+sourceleadUrl+"/"+"acceptOrRejectInterview?interviewStatus=Attending&requirementInterviewId="+requirementInterview.getId()+"' style='text-decoration:none;float:left; margin-left:55px;'><div style=' width:220px; border-radius:30px; background-color:#5cb85c; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Accept</div></a>");
			 globalVarsMap.put("rejectURL","<a href='"+sourceleadUrl+"/"+"acceptOrRejectInterview?interviewStatus=Not Attending&requirementInterviewId="+requirementInterview.getId()+"' style='text-decoration:none;float:left;margin-left:55px;'><div style=' width:220px; border-radius:30px; background-color:#e74c51; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Reject</div></a>");
			 globalVarsMap.put("tentativeURL","<a href='"+sourceleadUrl+"/"+"acceptOrRejectInterview?interviewStatus=Maybe&requirementInterviewId="+requirementInterview.getId()+" 'style='text-decoration:none;float:left;margin-left:55px;'><div style='width:220px; border-radius:30px; background-color:#f0ad4e;; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Tentative</div></a>");
			*/ 
		 //setting the Recipient information
		 ArrayList<EmailRecipient> emailRecipientList = new ArrayList<EmailRecipient>();
		 EmailRecipient emailRecipient = new EmailRecipient();
		 emailRecipient.setEmail(emailMessage.getToEmail());
		 emailRecipient.setName(emailMessage.getFirstName()+" "+emailMessage.getLastName());
		 emailRecipientList.add(emailRecipient);
		
		 //getting the current EmailScenario by EmailScenario unique name and entity type
		 EmailScenario  emailScenario = null;
			 emailScenario =  emailScenarioService.getEmailScenarioByUniqueName(Constants.APPLICANT_INTERVIEW,Constants.ENTITY_TYPE_EMAIL);
		
		 //covert subject,body and signature
		 String subject = emailScenerioUtil.bulidSubject(emailScenario.getSubject(),globalVarsMap);
		 String body = emailScenerioUtil.buildTempBody(emailScenario.getBody(),globalVarsMap);
		 String signature = emailScenerioUtil.buildSignature(emailScenario.getSignature(),globalVarsMap);
		 String url = "";
		 
		 //construct HTML template using required data
		 String emailTemplate = emailScenerioUtil.AssembleEmailMessage(emailScenario,emailMessage, subject, body, signature, url, new Org());
		 emailMessage.setSubject(subject);
		 emailMessage.setMessageBody(emailTemplate);
		 
		 
		 //calling the sendEmail method of emailScenerioUtil
		 emailScenerioUtil.sendEmail(emailMessage,loginOrgLocations.getId(),emailRecipientList,new ArrayList<EmailAttachment>());
		 
	}


	@Override
	public RequirementInterview getRequirementInterviewById(String requirementInterviewId) {
		return requirementInterviewDao.getRequirementInterviewById(requirementInterviewId);
	}


	@Override
	public void saveRequirementInterview(RequirementInterview requirementInterview) throws Exception {
		requirementInterviewDao.storeRequirementInterview(requirementInterview);
	}	
	
	

	@Override
	public List<RequirementInterview> getRequirementInterviewsBySubmissionIdAndOrgLocationsId(Long submissionId, Long orgLocationsId) throws Exception {
		logger.debug("In RequirementInterviewServiceImpl class getRequirementInterviewsBySubmissionIdAndOrgLocationsId method...");
		
		List<RequirementInterview> interviewslist = null;
		interviewslist = requirementInterviewDao.getRequirementInterviewsBySubmissionIdAndOrgLocationsId(submissionId,orgLocationsId);
		if (interviewslist != null && !interviewslist.isEmpty() ) {
			for (RequirementInterview requirementInterview : interviewslist) {
				if(requirementInterview.getRemainedBefore() != null && !requirementInterview.getRemainedBefore().isEmpty()){
					Integer hours = (Integer.parseInt(requirementInterview.getRemainedBefore()) / 60);
					Integer minutes = (Integer.parseInt(requirementInterview.getRemainedBefore()) % 60);
				    requirementInterview.setRemainderTime(hours + " : " + minutes + " minutes");
				}
				
			    if(requirementInterview.getDateOfInterview() != null){
			    	requirementInterview.setDateOfInterviewStr(Constants.DMY.format(requirementInterview.getDateOfInterview()));
			    }
			    
			    if(requirementInterview.getStartTime() != null){
			    	 String hours = Integer.toString(requirementInterview.getStartTime().getHours());
			    	 String minutes = Integer.toString(requirementInterview.getStartTime().getMinutes());
			    	 requirementInterview.setStartTimeStr(CalendarUtil.get12FormatTimeFrom24FormatTime(hours+":"+minutes));
			    }
			    
			    if(requirementInterview.getEndTime() != null){
			    	 String hours = Integer.toString(requirementInterview.getEndTime().getHours());
			    	 String minutes = Integer.toString(requirementInterview.getEndTime().getMinutes());
			    	 requirementInterview.setEndTimeStr(CalendarUtil.get12FormatTimeFrom24FormatTime(hours+":"+minutes));
			    }
			    
			    String InterviewersList = "";
				Set<RequirementInterviewer> Interviewers = requirementInterview.getRequirementInterviewers();
				int i = 0;
				for(RequirementInterviewer interviewer: Interviewers){
					if(i <= 50){
						InterviewersList = InterviewersList+interviewer.getInterviewerUser().getFirstName()+" "+interviewer.getInterviewerUser().getLastName();
						if(i != Interviewers.size()-1){
							InterviewersList = InterviewersList+",";
						}
					}
					i++;
				}
				requirementInterview.setInterviewersList(InterviewersList);
			}
		}
		return interviewslist;
	}

	
	@Override
	public List<RequirementInterview> getRequirementInternalAndIsClientInterviewsBySubmissionIdAndOrgLocationsId(Long submissionId, Long orgLocationsId) throws Exception {
		logger.debug("In RequirementInterviewServiceImpl class getRequirementInternalAndIsClientInterviewsBySubmissionIdAndOrgLocationsId method...");
		
		List<RequirementInterview> interviewslist = null;
		interviewslist = requirementInterviewDao.getRequirementInternalAndIsClientInterviewsBySubmissionIdAndOrgLocationsId(submissionId,orgLocationsId);
		if (interviewslist != null && !interviewslist.isEmpty() ) {
			for (RequirementInterview requirementInterview : interviewslist) {
				if(requirementInterview.getRemainedBefore() != null && !requirementInterview.getRemainedBefore().isEmpty()){
					Integer hours = (Integer.parseInt(requirementInterview.getRemainedBefore()) / 60);
					Integer minutes = (Integer.parseInt(requirementInterview.getRemainedBefore()) % 60);
				    requirementInterview.setRemainderTime(hours + " : " + minutes + " minutes");
				}
				
			    if(requirementInterview.getDateOfInterview() != null){
			    	requirementInterview.setDateOfInterviewStr(Constants.DMY.format(requirementInterview.getDateOfInterview()));
			    }
			    
			    if(requirementInterview.getStartTime() != null){
			    	 String hours = Integer.toString(requirementInterview.getStartTime().getHours());
			    	 String minutes = Integer.toString(requirementInterview.getStartTime().getMinutes());
			    	 requirementInterview.setStartTimeStr(CalendarUtil.get12FormatTimeFrom24FormatTime(hours+":"+minutes));
			    }
			    
			    if(requirementInterview.getEndTime() != null){
			    	 String hours = Integer.toString(requirementInterview.getEndTime().getHours());
			    	 String minutes = Integer.toString(requirementInterview.getEndTime().getMinutes());
			    	 requirementInterview.setEndTimeStr(CalendarUtil.get12FormatTimeFrom24FormatTime(hours+":"+minutes));
			    }
			    
			    if(requirementInterview.getSubmissionId().getRequirement().getClientId() != null){
					if(requirementInterview.getSubmissionId().getRequirement().getClientId().getResponderType().equalsIgnoreCase("Client")){
						requirementInterview.setClientName(requirementInterview.getSubmissionId().getRequirement().getClientId().getResponderLocationId().getOrg().getOrgName());
					}
					if(requirementInterview.getSubmissionId().getRequirement().getClientId().getResponderType().equalsIgnoreCase("Vendor")){
						requirementInterview.setClientName(requirementInterview.getSubmissionId().getRequirement().getClientId().getLocationId().getOrg().getOrgName());
					}
				}
			    
			    String InterviewersList = "";
				Set<RequirementInterviewer> Interviewers = requirementInterview.getRequirementInterviewers();
				int i = 0;
				for(RequirementInterviewer interviewer: Interviewers){
					if(i <= 50){
						InterviewersList = InterviewersList+interviewer.getInterviewerUser().getFirstName()+" "+interviewer.getInterviewerUser().getLastName();
						if(i != Interviewers.size()-1){
							InterviewersList = InterviewersList+",";
						}
					}
					i++;
				}
				requirementInterview.setInterviewersList(InterviewersList);
			}
		}
		return interviewslist;
	}
	
	
	
	//vendors mail sending the private method
	@Override
	public void sendEmailToVendors(Candidate candidate, RequirementSubmission requirementSubmission, User loginUser,Org loginOrg,RequirementInterview requirementInterview,OrgLocations loginOrgLocations)	throws Exception {
		 EmailMessage emailMessage = new EmailMessage();
		 User user = userService.getUserByUserCode(requirementSubmission.getCreatedBy());
		 emailMessage.setFirstName(user.getFirstName());
		 emailMessage.setLastName(user.getLastName());
		 emailMessage.setToEmail(user.getEmail());
		 emailMessage.setFromEmail("peopleaxisindia@gmail.com");
		 emailMessage.setCreatedBy(loginUser.getUserCode());
		 emailMessage.setFromName(loginUser.getFirstName()+" "+loginUser.getLastName());
		 
		 String time = "";
		 String endTime = "";
		 if(requirementInterview.getStartTime() != null){
	    	 String hours = Integer.toString(requirementInterview.getStartTime().getHours());
	    	 String minutes = Integer.toString(requirementInterview.getStartTime().getMinutes());
	    	 time = CalendarUtil.get12FormatTimeFrom24FormatTime(hours+":"+minutes);
	    }
	    
	    if(requirementInterview.getEndTime() != null){
	    	 String hours = Integer.toString(requirementInterview.getEndTime().getHours());
	    	 String minutes = Integer.toString(requirementInterview.getEndTime().getMinutes());
	    	 endTime = CalendarUtil.get12FormatTimeFrom24FormatTime(hours+":"+minutes);
	    }
		 
		 if(endTime!=null){
			  time=time+" - "+endTime;
		 }
		 
		 
		 String dateOfInterviewStr ="";
		 if(requirementInterview.getDateOfInterview()!=null){
		  dateOfInterviewStr = Constants.DMY.format(requirementInterview.getDateOfInterview());
		 }
		 
		 String interviewTypeValue="--";
		 if(requirementInterview.getInterviewType() !=null){
			 interviewTypeValue = requirementInterview.getInterviewType();
		 }
		 
		//setting the class filed values
		 Map<String, String> globalVarsMap = new HashMap<String, String>();
			 globalVarsMap.put("vendorFirstName", emailMessage.getFirstName());
			 globalVarsMap.put("vendorLastName",emailMessage.getLastName());
			 globalVarsMap.put("loginUser", emailMessage.getFromName());
			 globalVarsMap.put("requirementName", requirementSubmission.getRequirement().getJobTitle());
			 globalVarsMap.put("loginOrgName",loginOrg.getOrgName());
			 globalVarsMap.put("interviewDate", dateOfInterviewStr);
			 globalVarsMap.put("interviewTime",time);
			 globalVarsMap.put("interviewType",interviewTypeValue);
			 globalVarsMap.put("location",requirementInterview.getLocation());
			 globalVarsMap.put("applicantName",emailMessage.getFirstName() +" "+emailMessage.getLastName());
		 //setting the Recipient information
		 ArrayList<EmailRecipient> emailRecipientList = new ArrayList<EmailRecipient>();
		 EmailRecipient emailRecipient = new EmailRecipient();
		 emailRecipient.setEmail(emailMessage.getToEmail());
		 emailRecipient.setName(emailMessage.getFirstName()+" "+emailMessage.getLastName());
		 emailRecipientList.add(emailRecipient);
		
		 //getting the current EmailScenario by EmailScenario unique name and entity type
		 EmailScenario  emailScenario = null;
			 emailScenario =  emailScenarioService.getEmailScenarioByUniqueName(Constants.VENDOR_INTERVIEW,Constants.ENTITY_TYPE_EMAIL);
		
		 //covert subject,body and signature
		 String subject = emailScenerioUtil.bulidSubject(emailScenario.getSubject(),globalVarsMap);
		 String body = emailScenerioUtil.buildTempBody(emailScenario.getBody(),globalVarsMap);
		 String signature = emailScenerioUtil.buildSignature(emailScenario.getSignature(),globalVarsMap);
		 String url = "";
		 
		 //construct HTML template using required data
		 String emailTemplate = emailScenerioUtil.AssembleEmailMessage(emailScenario,emailMessage, subject, body, signature, url, new Org());
		 emailMessage.setSubject(subject);
		 emailMessage.setMessageBody(emailTemplate);
		 
		 
		 //calling the sendEmail method of emailScenerioUtil
		 emailScenerioUtil.sendEmail(emailMessage,loginOrgLocations.getId(),emailRecipientList,new ArrayList<EmailAttachment>());
		 
		 EmailScenario noificationEmailScenario =  emailScenarioService.getEmailScenarioByUniqueName(Constants.VENDOR_INTERVIEW,Constants.ENTITY_TYPE_NOTIFICATION); 
		 String actionLink = "";
		 
		 String notificationbody = emailScenerioUtil.buildTempBody(noificationEmailScenario.getBody(),globalVarsMap);
		 noificationEmailScenario.setBody(notificationbody);
		 
		 emailScenerioUtil.sendNotification(actionLink, loginUser, user, requirementSubmission.getVendorLocationId(), noificationEmailScenario);
		 
		 
	}

	/**
	 * author Laxman
	 * @param candidate
	 * @param interviewerUser
	 * @param requirementSubmission
	 * @param loginUser
	 * @param org
	 * @param requirementInterview
	 * @param orgLocations
	 * @throws Exception
	 * This method is send email from Interviewrs
	 */
	public void interViewerFeedbackTemplateSendEmailToInterviewrs(Candidate candidate, User interviewerUser,RequirementSubmission requirementSubmission, User loginUser,Org org,RequirementInterview requirementInterview,OrgLocations orgLocations,String templateId,RequirementInterviewer interviewer)	throws Exception {
		 Long requirementInterviewerid =interviewer.getId(); 
		EmailMessage emailMessage = new EmailMessage();
		
		if(interviewerUser.getFirstName()!=null){
			 emailMessage.setFirstName(interviewerUser.getFirstName());
		}
		else{
			 emailMessage.setFirstName("");
		}
		if(interviewerUser.getLastName()!=null){
			 emailMessage.setLastName(interviewerUser.getLastName());
		}
		else{
			 emailMessage.setLastName("");
		}
		 emailMessage.setToEmail(interviewerUser.getEmail());
		 emailMessage.setFromEmail("peopleaxisindia@gmail.com");
		 emailMessage.setCreatedBy(loginUser.getUserCode());
		 emailMessage.setFromName(loginUser.getFirstName()+" "+loginUser.getLastName());
		 
		 String candidateName = candidate.getFirstName();
		 if(candidate.getLastName()!=null){
			 candidateName=candidateName+" "+candidate.getLastName();
		 }
		 
		 String dateOfInterviewValue ="";
		 if(requirementInterview.getDateOfInterview()!=null){
			 dateOfInterviewValue = Constants.DMY.format(requirementInterview.getDateOfInterview());
		 }
		 String interviewTypeValue="--";
		 if(requirementInterview.getInterviewType() !=null){
			 interviewTypeValue = requirementInterview.getInterviewType();
		 }
	 
		 String time = "";
		 String endTime = ""; 
		 if(requirementInterview.getStartTime() != null){
	    	 String hours = Integer.toString(requirementInterview.getStartTime().getHours());
	    	 String minutes = Integer.toString(requirementInterview.getStartTime().getMinutes());;
	    	 time = CalendarUtil.get12FormatTimeFrom24FormatTime(hours+":"+minutes);
	    }
	    
	    if(requirementInterview.getEndTime() != null){
	    	 String hours = Integer.toString(requirementInterview.getEndTime().getHours());
	    	 String minutes = Integer.toString(requirementInterview.getEndTime().getMinutes());
	    	 endTime = CalendarUtil.get12FormatTimeFrom24FormatTime(hours+":"+minutes);
	    }
		 
		 if(endTime!=null){
			  time=time+" - "+endTime;
		 }
		 String interviewTime =  time;
		//setting the class filed values
		 Map<String, String> globalVarsMap = new HashMap<String, String>();
			 globalVarsMap.put("interviewerFirstName", emailMessage.getFirstName());
			 globalVarsMap.put("interviewerLastName",emailMessage.getLastName());
			 globalVarsMap.put("loginUser", emailMessage.getFromName());
			 globalVarsMap.put("requirementTitle", requirementSubmission.getRequirement().getJobTitle());
			 globalVarsMap.put("interviewDate", dateOfInterviewValue);
			 globalVarsMap.put("interviewTime", interviewTime);
			 globalVarsMap.put("interviewType",interviewTypeValue);
			 globalVarsMap.put("applicantName",candidateName);
			 StringBuilder feedbackBuilder = new StringBuilder();
			 
			 globalVarsMap.put("FeedbackLink",feedbackBuilder.append("<a href='").append(sourceleadUrl).append("/publicInterviewFeedBackTemplate?templateId=").append(templateId).append("&requirementInterviewid=").append(requirementInterviewerid).append("'style='text-decoration:none; '><div style=' width:100px; border-radius:30px; background-color:#D33B39; color:#FFF; font-size:15px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Feedback</div></a>").toString());
			 //globalVarsMap.put("FeedbackLink","<a href='"+sourceleadUrl+"/"+"publicInterviewFeedBackTemplate?templateId="+templateId+"&requirementInterviewid="+requirementInterviewerid+"' style='text-decoration:none; '><div style=' width:100px; border-radius:30px; background-color:#D33B39; color:#FFF; font-size:15px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Feedback</div></a>");
		 //setting the Recipient information
		 ArrayList<EmailRecipient> emailRecipientList = new ArrayList<EmailRecipient>();
		 EmailRecipient emailRecipient = new EmailRecipient();
		 emailRecipient.setEmail(emailMessage.getToEmail());
		 emailRecipient.setName(emailMessage.getFirstName()+" "+emailMessage.getLastName());
		 emailRecipientList.add(emailRecipient);
		
		 //getting the current EmailScenario by EmailScenario unique name and entity type
		 EmailScenario  emailScenario = null;
			 emailScenario =  emailScenarioService.getEmailScenarioByUniqueName(Constants.INTERVIEWER_TEMPLATE,Constants.ENTITY_TYPE_EMAIL);
			 
		 //covert subject,body and signature
		 String subject = emailScenerioUtil.bulidSubject(emailScenario.getSubject(),globalVarsMap);
		 String body = emailScenerioUtil.buildTempBody(emailScenario.getBody(),globalVarsMap);
		 String signature = emailScenerioUtil.buildSignature(emailScenario.getSignature(),globalVarsMap);
		 String url = sourceleadUrl;
		 
		 //construct HTML template using required data
		 String emailTemplate = emailScenerioUtil.AssembleEmailMessage(emailScenario,emailMessage, subject, body, signature, url, new Org());
		 emailMessage.setSubject(subject);
		 emailMessage.setMessageBody(emailTemplate);
		 
		 
		 //calling the sendEmail method of emailScenerioUtil
		 emailScenerioUtil.sendEmail(emailMessage,orgLocations.getId(),emailRecipientList,new ArrayList<EmailAttachment>());

		 EmailScenario noificationEmailScenario =  emailScenarioService.getEmailScenarioByUniqueName(Constants.INTERVIEWER_INVITATION,Constants.ENTITY_TYPE_NOTIFICATION); 
		 String actionLink = "";
		 
		 if(interviewerUser.getFirstName()!=null){
		 String notificationbody = emailScenerioUtil.buildTempBody(noificationEmailScenario.getBody(),globalVarsMap);
		 noificationEmailScenario.setBody(notificationbody);
		 
		 emailScenerioUtil.sendNotification(actionLink, loginUser, interviewerUser, orgLocations, noificationEmailScenario);
		 }
	}
	
	
	
	@Override
	public List<RequirementInterview> getAllInterviewAssignedToLoginUser(Long orgLocationsId, String dateRange, String currentTime, String userType, Long userId, String userCode)
			throws Exception {
		List<RequirementInterview> list = requirementInterviewDao.getAllInterviewAssignedToLoginUser(orgLocationsId, dateRange, currentTime, userType, userId, userCode);
		return list;

			}

	@Override
	public List<RequirementInterview> getCustomerInterviewsBySubmissionIdAndOrgLocationsId(Long submissionId, Long orgLocationsId,Long requirementId) throws Exception {
		List<RequirementInterview> customerInterviewList = null;
		customerInterviewList = requirementInterviewDao.getCustomerInterviewsBySubmissionIdAndOrgLocationsId(submissionId,orgLocationsId,requirementId);
		if (customerInterviewList != null && !customerInterviewList.isEmpty() ) {
			for (RequirementInterview requirementInterview : customerInterviewList) {
								
			    if(requirementInterview.getDateOfInterview() != null){
			    	requirementInterview.setDateOfInterviewStr(Constants.DMY.format(requirementInterview.getDateOfInterview()));
			    }
			    
			    if(requirementInterview.getStartTime() != null){
			    	 String hours = Integer.toString(requirementInterview.getStartTime().getHours());
			    	 String minutes = Integer.toString(requirementInterview.getStartTime().getMinutes());
			    	 requirementInterview.setStartTimeStr(CalendarUtil.get12FormatTimeFrom24FormatTime(hours+":"+minutes));
			    }
			    
			    if(requirementInterview.getEndTime() != null){
			    	 String hours = Integer.toString(requirementInterview.getEndTime().getHours());
			    	 String minutes = Integer.toString(requirementInterview.getEndTime().getMinutes());
			    	 requirementInterview.setEndTimeStr(CalendarUtil.get12FormatTimeFrom24FormatTime(hours+":"+minutes));
			    }
			    
			    RequirementSubmission requirementSubmission = requirementSubmissionService.getSubmissionsBySourceSubmissionId(requirementInterview.getSubmissionId().getId(),orgLocationsId);
			    if(requirementSubmission != null){
				   OrgLocations orgLocations = orgLocationService.getOrgLocations(requirementSubmission.getOrgLocations().getId());
				   requirementInterview.setClientName(orgLocations.getOrg().getOrgName());
			    }
			    
		}
		}
		return customerInterviewList;
	}
	
	@Override
	public RequirementInterview getRequirementInterviewByIdAndSubmissionIdAndOrgLocationsId(Long requirementInterviewId,
			Long submissionId, Long orgLocationsId) {
		return requirementInterviewDao.getRequirementInterviewByIdAndSubmissionIdAndOrgLocationsId(requirementInterviewId, submissionId, orgLocationsId);
	}
	@Override
	public List<RequirementInterview> getRequirementInterviewsHistoryBySubmissionIdAndOrgLocationsId(Long submissionId,
			Long orgLocationsId) throws Exception {
		return requirementInterviewDao.getRequirementInterviewsHistoryBySubmissionIdAndOrgLocationsId(submissionId,orgLocationsId);
	}
	
	/*@Override
	public List<RequirementInterview> getAllInterviewListByCurrentDate(String currentTime, String triggerTime) {
		return requirementInterviewDao.getAllInterviewListByCurrentDate(currentTime,  triggerTime);
	}*/

}
