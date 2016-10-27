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
import com.bis.sourcelead.entity.RequirementInterviewer;
import com.bis.sourcelead.entity.RequirementSubmission;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.enums.ApplicantSubmissionStatus;
import com.bis.sourcelead.service.CandidateService;
import com.bis.sourcelead.service.EmailScenarioService;
import com.bis.sourcelead.service.RequirementInterviewService;
import com.bis.sourcelead.service.RequirementInterviewerService;
import com.bis.sourcelead.service.RequirementSubmissionService;
import com.bis.sourcelead.service.UserService;
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
	private RequirementSubmissionService requirementSubmissionService;
	
	@Autowired
	private RequirementInterviewerService requirementInterviewerService;

	@Value("${sourceleadUrl}")
    private String sourceleadUrl;
	
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
								
								if(jsonObj.has(c+"startTime") && !jsonObj.getString(c+"startTime").isEmpty()){
									requirementInterview.setStartTime(jsonObj.getString(c+"startTime"));
								}
								
								if(jsonObj.has(c+"endTime") && !jsonObj.getString(c+"endTime").isEmpty()){
									requirementInterview.setEndTime(jsonObj.getString(c+"endTime"));
								}
								
								if(jsonObj.has(c+"location") && !jsonObj.getString(c+"location").isEmpty()){
									requirementInterview.setLocation(jsonObj.getString(c+"location"));
								}
								
								if(jsonObj.has("notificationToApplicant") && !jsonObj.getString("notificationToApplicant").isEmpty()){
									requirementInterview.setNotificationToApplicant(jsonObj.getString("notificationToApplicant"));
								}
								
								if(jsonObj.has("notificationToSpoc") && !jsonObj.getString("notificationToSpoc").isEmpty()){
									requirementInterview.setNotificationToSpoc(jsonObj.getString("notificationToSpoc"));
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
								
							   requirementInterview.setStatus(Constants.RECORD_ACTIVE);
					           requirementInterview.setCreatedDate(new Date());
					           requirementInterview.setCreatedBy(user.getUserCode());
					           requirementInterview.setUpdatedDate(new Date());
					           requirementInterview.setUpdatedBy(user.getUserCode());
					           requirementInterviewDao.storeRequirementInterview(requirementInterview);
					           
					           RequirementSubmission requirementSubmission = requirementSubmissionService.getRequirementSubmissionById(Long.parseLong(applicants[i].replace("\"", "")));
					           requirementSubmission.setSubmissionStatus(ApplicantSubmissionStatus.INTERVIEW);
					           requirementSubmissionService.storeRequirementSubmission(requirementSubmission);
					           
					           
					           
					          Candidate candidate = candidateService.getCandidateByCandidateId(requirementSubmission.getCandidate().getId());
					           
					           
					           if(jsonObj.has("notificationToApplicant") && !jsonObj.getString("notificationToApplicant").isEmpty()&& jsonObj.getString("notificationToApplicant").equalsIgnoreCase("Y")){
					        	   sendEmailToApplicants(candidate,requirementSubmission, user, jsonObj.getString(c+"startTime"), jsonObj.getString(c+"endTime"), loginOrg, jsonObj.getString(c+"dateOfInterview"), jsonObj.getString(c+"location"),jsonObj.getString(c+"interviewType"),requirementInterview);
					           }
					           

					           if(jsonObj.has("notificationToSpoc") && !jsonObj.getString("notificationToSpoc").isEmpty()&& jsonObj.getString("notificationToSpoc").equalsIgnoreCase("Y")){
					        	   sendEmailToVendors(candidate,requirementSubmission, user, jsonObj.getString(c+"startTime"), jsonObj.getString(c+"endTime"), loginOrg, jsonObj.getString(c+"dateOfInterview"), jsonObj.getString(c+"location"),jsonObj.getString(c+"interviewType"));
					           }

	           
					           if(jsonObj.has("notificationToOthersEmails") && !jsonObj.getString("notificationToOthersEmails").isEmpty()){
									String[] othersEmails = jsonObj.getString("notificationToOthersEmails").split(",");
									for (String emailId : othersEmails) {
										User otherUser = new User();
										otherUser.setEmail(emailId);
										sendEmailToInterviewrs(candidate,otherUser,requirementSubmission, user, jsonObj.getString(c+"startTime"), jsonObj.getString(c+"endTime"), loginOrg, jsonObj.getString(c+"dateOfInterview"), jsonObj.getString(c+"location"),jsonObj.getString(c+"interviewType"),orgLocations);
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
							             sendEmailToInterviewrs(candidate,interviewerUser,requirementSubmission, user, jsonObj.getString(c+"startTime"), jsonObj.getString(c+"endTime"), loginOrg, jsonObj.getString(c+"dateOfInterview"), jsonObj.getString(c+"location"),jsonObj.getString(c+"interviewType"),orgLocations);
								         requirementInterviewerDao.storeRequirementInterviewer(interviewer);
									}
					           }
							}
				}
			continue;
		}
		
	}
	
	
	private void sendEmailToInterviewrs(Candidate candidate, User interviewerUser,RequirementSubmission requirementSubmission, User loginUser, String startTime, String endTime,Org loginOrg,String dateOfInterview,String location, String interviewType, OrgLocations orgLocations)	throws Exception {
		 
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
		 emailMessage.setFromName(loginUser.getFirstName()+" "+loginUser.getLastName());
		 
		 String time=startTime;
		 if(endTime!=null){
			  time=time+" "+endTime;
		 }
		 String candidateName = candidate.getFirstName();
		 if(candidate.getLastName()!=null){
			 candidateName=candidateName+" "+candidate.getLastName();
		 }
		 
		 String interviewTypeValue="--";
		 if(interviewType!=null){
			 interviewTypeValue = interviewType;
		 }
		 
		 String dateOfInterviewValue = dateOfInterview+" "+time;
		//setting the class filed values
		 Map<String, String> globalVarsMap = new HashMap<String, String>();
			 globalVarsMap.put("interviewerFirstName", emailMessage.getFirstName());
			 globalVarsMap.put("interviewerLastName",emailMessage.getLastName());
			 globalVarsMap.put("loginUser", emailMessage.getFromName());
			 globalVarsMap.put("requirementTitle", requirementSubmission.getRequirement().getJobTitle());
			 globalVarsMap.put("interviewDate", dateOfInterviewValue);
			 globalVarsMap.put("interviewType",interviewTypeValue);
			 globalVarsMap.put("applicantName",candidateName);
			 globalVarsMap.put("Location",location);
			 
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
		 emailScenerioUtil.sendEmail(emailMessage,emailRecipientList,new ArrayList<EmailAttachment>());

		 EmailScenario noificationEmailScenario =  emailScenarioService.getEmailScenarioByUniqueName(Constants.INTERVIEWER_INVITATION,Constants.ENTITY_TYPE_NOTIFICATION); 
		 String actionLink = "";
//		 String actionLink = ""+sourceleadUrl+"/"+"acceptClientOrVendorInvitation?actionLink=acceptNotification&respondingOrgType=ATS&respondingPartyOrgLocationId=";
		 
		 if(interviewerUser.getFirstName()!=null){
		 String notificationbody = emailScenerioUtil.buildTempBody(noificationEmailScenario.getBody(),globalVarsMap);
		 noificationEmailScenario.setBody(notificationbody);
		 
		 emailScenerioUtil.sendNotification(actionLink, loginUser, interviewerUser, orgLocations, noificationEmailScenario);
		 }
	}
	
	
	
	public void sendEmailToApplicants(Candidate candidate, RequirementSubmission requirementSubmission, User loginUser, String startTime, String endTime,Org loginOrg,String dateOfInterview,String location, String interviewType, RequirementInterview requirementInterview)	throws Exception {
		 EmailMessage emailMessage = new EmailMessage();
		 emailMessage.setFirstName(candidate.getFirstName());
		 emailMessage.setLastName(candidate.getLastName());
		 emailMessage.setToEmail(candidate.getEmail());
		 emailMessage.setFromEmail("peopleaxisindia@gmail.com");
		 emailMessage.setFromName(loginUser.getFirstName()+" "+loginUser.getLastName());
		 
		 String time=startTime;
		 if(endTime!=null){
			  time=time+" "+endTime;
		 }
		 String interviewTypeValue="--";
		 if(interviewType!=null){
			 interviewTypeValue = interviewType;
		 }
		//setting the class filed values
		 Map<String, String> globalVarsMap = new HashMap<String, String>();
			 globalVarsMap.put("applicantFirstName", emailMessage.getFirstName());
			 globalVarsMap.put("applicantLastName",emailMessage.getLastName());
			 globalVarsMap.put("loginUser", emailMessage.getFromName());
			 globalVarsMap.put("requirementTitle", requirementSubmission.getRequirement().getJobTitle());
			 globalVarsMap.put("loginOrgName",loginOrg.getOrgName());
			 globalVarsMap.put("interviewDate", dateOfInterview);
			 globalVarsMap.put("interviewTime",time);
			 globalVarsMap.put("interviewType",interviewTypeValue);
			 globalVarsMap.put("Location",location);
			// globalVarsMap.put("acceptURL","<a href="+sourceleadUrl+"/"+"acceptOrRejectInterview?actionLink=acceptOrRejectInterview&interviewStatus=Attending&requirementSubmissionId="+requirementSubmission.getId()+">Attending</a>");
			 //globalVarsMap.put("rejectURL","<a href="+sourceleadUrl+"/"+"acceptOrRejectInterview?actionLink=acceptOrRejectInterview&interviewStatus=NotAttending&requirementSubmissionId="+requirementSubmission.getId()+">Not Attending</a>");
			// globalVarsMap.put("tentativeURL","<a href="+sourceleadUrl+"/"+"acceptOrRejectInterview?actionLink=acceptOrRejectInterview&interviewStatus=Tentative&requirementSubmissionId="+requirementSubmission.getId()+">Tentative</a>");
			 
			 globalVarsMap.put("acceptURL","<a href='"+sourceleadUrl+"/"+"acceptOrRejectInterview?interviewStatus=Attending&requirementInterviewId="+requirementInterview.getId()+"' style='text-decoration:none; '><div style=' width:220px; border-radius:30px; background-color:#214b90; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Accept</div></a><br>");
			 globalVarsMap.put("rejectURL","<a href='"+sourceleadUrl+"/"+"acceptOrRejectInterview?interviewStatus=Not Attending&requirementInterviewId="+requirementInterview.getId()+"' style='text-decoration:none; '><div style=' width:220px; border-radius:30px; background-color:#214b90; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Reject</div></a><br>");
			 globalVarsMap.put("tentativeURL","<a href='"+sourceleadUrl+"/"+"acceptOrRejectInterview?interviewStatus=Maybe&requirementInterviewId="+requirementInterview.getId()+" style='text-decoration:none; '><div style=' width:220px; border-radius:30px; background-color:#214b90; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Tentative</div></a>");
			 
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
		 emailScenerioUtil.sendEmail(emailMessage,emailRecipientList,new ArrayList<EmailAttachment>());
		 
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

	public void sendEmailToVendors(Candidate candidate, RequirementSubmission requirementSubmission, User loginUser, String startTime, String endTime,Org loginOrg,String dateOfInterview,String location, String interviewType)	throws Exception {
		 EmailMessage emailMessage = new EmailMessage();
		 emailMessage.setFirstName(candidate.getFirstName());
		 emailMessage.setLastName(candidate.getLastName());
		 emailMessage.setToEmail(candidate.getEmail());
		 emailMessage.setFromEmail("peopleaxisindia@gmail.com");
		 emailMessage.setFromName(loginUser.getFirstName()+" "+loginUser.getLastName());
		 
		 String time=startTime;
		 if(endTime!=null){
			  time=time+" "+endTime;
		 }
		//setting the class filed values
		 Map<String, String> globalVarsMap = new HashMap<String, String>();
			 globalVarsMap.put("vendorFirstName", emailMessage.getFirstName());
			 globalVarsMap.put("vendorLastName",emailMessage.getLastName());
			 globalVarsMap.put("loginUser", emailMessage.getFromName());
			 globalVarsMap.put("requirementName", requirementSubmission.getRequirement().getJobTitle());
			 globalVarsMap.put("loginOrgName",loginOrg.getOrgName());
			 globalVarsMap.put("interviewDate", dateOfInterview);
			 globalVarsMap.put("interviewTime",time);
			 globalVarsMap.put("interviewType",interviewType);
			 globalVarsMap.put("location",location);
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
		 emailScenerioUtil.sendEmail(emailMessage,emailRecipientList,new ArrayList<EmailAttachment>());
		 
	}	

}
