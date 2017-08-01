package com.bis.sourcelead.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.mail.internet.InternetAddress;

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
import com.bis.sourcelead.entity.EmailScenario;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgSpoc;
import com.bis.sourcelead.entity.ProposeTime;
import com.bis.sourcelead.entity.Requirement;
import com.bis.sourcelead.entity.RequirementInterview;
import com.bis.sourcelead.entity.RequirementInterviewHistory;
import com.bis.sourcelead.entity.RequirementInterviewer;
import com.bis.sourcelead.entity.RequirementSubmission;
import com.bis.sourcelead.entity.RequirementSubmissionHistory;
import com.bis.sourcelead.entity.RequirementSubmissionTemplateFields;
import com.bis.sourcelead.entity.SubmissionTemplate;
import com.bis.sourcelead.entity.Timezone;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.enums.ApplicantSubmissionStatus;
import com.bis.sourcelead.service.CandidateService;
import com.bis.sourcelead.service.EmailScenarioService;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.OrgSpocService;
import com.bis.sourcelead.service.ProposeTimeService;
import com.bis.sourcelead.service.RequirementInterviewHistoryService;
import com.bis.sourcelead.service.RequirementInterviewService;
import com.bis.sourcelead.service.RequirementService;
import com.bis.sourcelead.service.RequirementSubmissionHistoryService;
import com.bis.sourcelead.service.RequirementSubmissionService;
import com.bis.sourcelead.service.RequirementSubmissionTemplateFieldsService;
import com.bis.sourcelead.service.SubmissionTemplateService;
import com.bis.sourcelead.service.TimeZoneService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.CalendarUtil;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.EmailScenerioUtil;
import com.bis.sourcelead.web.LoginSessionController;

@Service
public class RequirementInterviewServiceImpl implements RequirementInterviewService{
	
	private static final Logger logger = LoggerFactory.getLogger(RequirementInterviewServiceImpl.class);
	
	@Value("${defaultFromEmailAddressForProd}")
	private String defaultFromEmailAddressForProd;
	
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
	private RequirementInterviewHistoryService requirementInterviewHistoryService;
	
	@Autowired
	private RequirementSubmissionTemplateFieldsService requirementSubmissionTemplateFieldsService;
	
	@Autowired
	private SubmissionTemplateService submissionTemplateService;
	
	
	@Autowired
	private TimeZoneService timeZoneService;
	
	@Value("${sourceleadUrl}")
    private String sourceleadUrl;
	
	@Autowired
	private RequirementSubmissionHistoryService requirementSubmissionHistoryService;
	
	@Autowired
	private RequirementService requirementService;
	
	@Autowired
	private OrgSpocService orgSpocService;
	
	@Autowired
	ProposeTimeService proposeTimeService; 
	
	@Override
	public void saveRequirementInterview(JSONObject jsonObj, User user, OrgLocations orgLocations, Org loginOrg) throws Exception  {
		
		String maxAdminTimeTypeRowNum = jsonObj.getString("maxTsRowNum");
		int maxCount = Integer.parseInt(maxAdminTimeTypeRowNum);
		RequirementInterview requirementInterview  = null;
		RequirementInterviewer savedInterviewer = null;
		List<RequirementInterview> intervieresArrayList = null ;
		List<RequirementInterviewer> requirementIntervier= null;
		
		for (long c = 1; c < maxCount; c++) {
				if(jsonObj.has(c+"applicant") && !jsonObj.getString(c+"applicant").isEmpty()){
						String applicantIds = jsonObj.getString(c+"applicant");
						applicantIds = applicantIds.replaceAll("\\[", "").replaceAll("\\]","");
						String[] applicants = applicantIds.split(",");
						
						intervieresArrayList = new ArrayList<RequirementInterview>();
						requirementIntervier = new ArrayList<RequirementInterviewer>();
						
					     for(int i=0 ;i<applicants.length;i++ ){
								requirementInterview  = new RequirementInterview();
								RequirementSubmission submission = new RequirementSubmission();
								submission.setId(Long.parseLong(applicants[i].replace("\"", "")));
								requirementInterview.setSubmissionId(submission);
								
									Timezone zone = timeZoneService.getTimezone(Long.parseLong(jsonObj.getString(c+"timeZone")));
									requirementInterview.setTimezone(zone);
								
								if(jsonObj.has(c+"dateOfInterview") && jsonObj.getString(c+"dateOfInterview") != null && !"".equalsIgnoreCase(jsonObj.getString(c+"dateOfInterview"))){
									//requirementInterview.setDateOfInterview(commonUtil.getDateFormat(jsonObj.getString(c+"dateOfInterview")));
									
									// changed by prasanna vadla .. due to timezone 
									Date startTime =  CalendarUtil.getDateFromDateAndTime(commonUtil.getDateFormat(jsonObj.getString(c+"dateOfInterview")), 
										      jsonObj.getString(c+"startTime"));
									String currentTimeStr = Constants.TZ_DF.format(startTime);  
									String startTimeUTC= CommonUtil.convertTimeZones(zone.getTimeZoneId(), "UTC", currentTimeStr);
									Date converteDate = Constants.DF_YMD.parse(startTimeUTC);
									requirementInterview.setDateOfInterview(converteDate);
								}
								
								if(jsonObj.has(c+"interviewType") && !jsonObj.getString(c+"interviewType").isEmpty()){
									requirementInterview.setInterviewType(jsonObj.getString(c+"interviewType"));
									if(jsonObj.has(c+"interviewTypeother") && !jsonObj.getString(c+"interviewTypeother").isEmpty()){
										requirementInterview.setCustomInterviewType(jsonObj.getString(c+"interviewTypeother"));
									}
								}
								
								if(jsonObj.has(c+"startTime") && !jsonObj.getString(c+"startTime").isEmpty()){
									  Date startTime =  CalendarUtil.getDateFromDateAndTime(commonUtil.getDateFormat(jsonObj.getString(c+"dateOfInterview")), 
											      jsonObj.getString(c+"startTime"));
										String currentTimeStr = Constants.TZ_DF.format(startTime);  
										String startTimeUTC= CommonUtil.convertTimeZones(zone.getTimeZoneId(), "UTC", currentTimeStr);
										DateFormat TZ_DF_TS = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
										Date convertedDate = TZ_DF_TS.parse(startTimeUTC);
										requirementInterview.setStartTime(convertedDate);
								}
								
								if(jsonObj.has(c+"endTime") && !jsonObj.getString(c+"endTime").isEmpty()){
									Date endTime =  CalendarUtil.getDateFromDateAndTime(commonUtil.getDateFormat(jsonObj.getString(c+"dateOfInterview")), 
										      jsonObj.getString(c+"endTime"));
									String currentTimeStr = Constants.TZ_DF.format(endTime);  
									String endTimeUTC= CommonUtil.convertTimeZones(zone.getTimeZoneId(), "UTC", currentTimeStr);
									DateFormat TZ_DF_TS = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
									Date convertedDate = TZ_DF_TS.parse(endTimeUTC);
								    requirementInterview.setEndTime(convertedDate);
								}
								
								
								
								if(jsonObj.has(c+"location") && !jsonObj.getString(c+"location").isEmpty()){
									requirementInterview.setLocation(jsonObj.getString(c+"location"));
								}
								
								if(jsonObj.has("notificationToOthers") && !jsonObj.getString("notificationToOthers").isEmpty()){
									requirementInterview.setNotificationToOthers(jsonObj.getString("notificationToOthers"));
								}
								
								if(jsonObj.has("proposeTime") && !jsonObj.getString("proposeTime").isEmpty()){
									requirementInterview.setProposeTime(jsonObj.getString("proposeTime"));
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
					           requirementSubmission.setUpdatedDate(commonUtil.currentDate());
					           requirementSubmissionService.storeRequirementSubmission(requirementSubmission);
					           requirementSubmissionHistoryService.updateRequirementHistory(requirementSubmission, user, requirementSubmissionHistory);
					           
					           if(requirementSubmission.getSourceSubmission() != null){
					        	   RequirementSubmission parentSubmission = requirementSubmissionService.getRequirementSubmissionById(requirementSubmission.getSourceSubmission().getId());
						           if(jsonObj.has("notificationToSpoc") && !jsonObj.getString("notificationToSpoc").isEmpty() && parentSubmission != null){
						        	   parentSubmission.setSubmissionStatus(ApplicantSubmissionStatus.CLIENT_INTERVIEW);
						        	   parentSubmission.setUpdatedDate(commonUtil.currentDate());
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
							             //sendEmailToInterviewrs(candidate,interviewerUser,requirementSubmission, user, loginOrg,requirementInterview,orgLocations);
							             savedInterviewer =  requirementInterviewerDao.storeRequirementInterviewer(interviewer);
								         
								         intervieresArrayList.add(savedInterviewer.getRequirementInterview());
								         requirementIntervier.add(savedInterviewer);
								         
								         if(jsonObj.has("interviewSubmissionTemplate") && !jsonObj.getString("interviewSubmissionTemplate").isEmpty()){
							            	 String templateId = jsonObj.getString("interviewSubmissionTemplate");
							            	// interViewerFeedbackTemplateSendEmailToInterviewrs(candidate,interviewerUser,requirementSubmission, user, loginOrg,requirementInterview,orgLocations,templateId,interviewer); 
							             }
									}
					           }
							}
					     
				}
				continue;
		}
		String maxAdminTimeTypeRowNumber = jsonObj.getString("maxTsRowNum");
		int maxCountnumber = Integer.parseInt(maxAdminTimeTypeRowNumber);
		for (long c = 1; c < maxCountnumber; c++) {
				if(jsonObj.has(c+"applicant") && !jsonObj.getString(c+"applicant").isEmpty()){
						String applicantIds = jsonObj.getString(c+"applicant");
						applicantIds = applicantIds.replaceAll("\\[", "").replaceAll("\\]","");
						String[] applicants = applicantIds.split(",");
						List<RequirementSubmissionTemplateFields> InterviewcandidateList = null;
						User interviewerUser = null;
						if(jsonObj.has(c+"interviewer") && !jsonObj.getString(c+"interviewer").isEmpty()){
			        	    String interviewerIds = jsonObj.getString(c+"interviewer");
			        	    interviewerIds = interviewerIds.replaceAll("\\[", "").replaceAll("\\]","");
							String[] interviewers = interviewerIds.split(",");
							for(int j=0 ;j<interviewers.length;j++ ){
							interviewerUser = userService.getUserById(Long.parseLong(interviewers[j].replace("\"", "")));
							 InterviewcandidateList = new ArrayList<RequirementSubmissionTemplateFields>();
						for(int i=0 ;i<applicants.length;i++){
							//requirementSubmissionTemplateFieldsService.getSubmissionsTemplateFieldsByRequirementSubmissionsId(Long.parseLong(applicants[i]))
							RequirementSubmissionTemplateFields requirementSubmission = requirementSubmissionTemplateFieldsService.getSubmissionsTemplateFieldsByRequirementSubmissionsId(Long.parseLong(applicants[i].replace("\"", "")));
							InterviewcandidateList.add(requirementSubmission);
						}
					
						String templateId = "";
						if(jsonObj.has("interviewSubmissionTemplate") && !jsonObj.getString("interviewSubmissionTemplate").isEmpty()){
							templateId = jsonObj.getString("interviewSubmissionTemplate");
						}
						sendMailToInterViewrsRedefined(InterviewcandidateList,interviewerUser, requirementInterview, user,orgLocations,templateId,intervieresArrayList,requirementIntervier);
					}	
			
			 }
			}
		}
	}	
	
	
	private void sendMailToInterViewrsRedefined(List<RequirementSubmissionTemplateFields> interviewcandidateList, User interviewerUser,RequirementInterview requirementInterview,User loginUser, OrgLocations orgLocations, String templateId, List<RequirementInterview> intervieresArrayList, List<RequirementInterviewer> requirementIntervier) throws Exception {

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
		 StringBuilder interviewStringBuilder=new StringBuilder();
		 emailMessage.setToEmail(interviewerUser.getEmail());
		 emailMessage.setFromEmail(loginUser.getEmail());
		 emailMessage.setCreatedBy(loginUser.getUserCode());
		 emailMessage.setFromName(interviewStringBuilder.append(loginUser.getFirstName()).append(" ").append(loginUser.getLastName()).toString());
		 emailMessage.setEmailType(Constants.CUSTOM_USER_EMAIL);
		 
		 /*String dateOfInterviewValue ="";
		 if(requirementInterview.getDateOfInterview()!=null){
			 dateOfInterviewValue = Constants.DMY.format(requirementInterview.getDateOfInterview());
		 }*/
		 String interviewTypeValue="--";
		 if(requirementInterview.getInterviewType() !=null){
			 interviewTypeValue = requirementInterview.getInterviewType();
		 }
	 
		 String time = "";
		 String startTime = "";
		 String endTime = "";
		 String dateOfInterviewStr ="";
		 
		 if(requirementInterview.getDateOfInterview() != null){

				String currentTimeStr = Constants.TZ_DF.format(requirementInterview.getStartTime());
				DateFormat TZ_DF_TS = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
				String interviewDateInTimezone= CommonUtil.convertTimeZones(Constants.UTC_TIMEZONE  ,requirementInterview.getTimezone().getTimeZoneId(), currentTimeStr);
				Date interviewConverteDate = TZ_DF_TS.parse(interviewDateInTimezone);
				interviewDateInTimezone = LoginSessionController.DATEFORMAT.format(interviewConverteDate);
				dateOfInterviewStr = interviewDateInTimezone;
		    }
		    
		    if(requirementInterview.getStartTime() != null){
		    	DateFormat DF_DMY = new SimpleDateFormat("hh:mma");
				String currentTimeStr = Constants.TZ_DF.format(requirementInterview.getStartTime());  
				String startTimeUTC= CommonUtil.convertTimeZones(Constants.UTC_TIMEZONE ,requirementInterview.getTimezone().getTimeZoneId(), currentTimeStr);
				DateFormat TZ_DF_TS = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
				Date convertedDate = TZ_DF_TS.parse(startTimeUTC);
				String convertedDateStr = DF_DMY.format(convertedDate);
				startTime = convertedDateStr;
			}
				 
		    if(requirementInterview.getEndTime() != null){
		    	DateFormat DF_DMY = new SimpleDateFormat("hh:mma");
				String currentTimeStr = Constants.TZ_DF.format(requirementInterview.getEndTime());  
				String startTimeUTC= CommonUtil.convertTimeZones(Constants.UTC_TIMEZONE ,requirementInterview.getTimezone().getTimeZoneId(), currentTimeStr);
				DateFormat TZ_DF_TS = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
				Date convertedDate = TZ_DF_TS.parse(startTimeUTC);
				String convertedDateStr = DF_DMY.format(convertedDate);  
				endTime = convertedDateStr;
		    }
		 
		 if(endTime!=null){
			 StringBuilder timeBuilder=new StringBuilder();
			  time = timeBuilder.append(startTime).append(" - ").append(endTime).append(" ("+requirementInterview.getTimezone().getTimeZoneId()+")").toString();
		 }
		 StringBuilder dateOfInterviewSB=new StringBuilder();
		 String dateOfInterviewValue = dateOfInterviewSB.append(dateOfInterviewStr).append(" ").append(time).toString();
		 
		 StringBuilder  interviewTable  = buildInterviewCandidatteTable(interviewcandidateList,interviewTypeValue,dateOfInterviewValue,requirementInterview,templateId,intervieresArrayList,requirementIntervier,interviewerUser);
		 StringBuilder linkBuild = new StringBuilder();
		//setting the class filed values
		 Map<String, String> globalVarsMap = new HashMap<String, String>();
			 globalVarsMap.put("interviewerFirstName", emailMessage.getFirstName());
			 globalVarsMap.put("interviewerLastName",emailMessage.getLastName());
			 globalVarsMap.put("loginUser", emailMessage.getFromName());
			 RequirementSubmission submission = requirementSubmissionService.getRequirementSubmissionById(requirementInterview.getSubmissionId().getId());
			 Requirement requirement = requirementService.getRequirementByRequirementId(submission.getRequirement().getId());
			 globalVarsMap.put("requirementTitle", requirement.getJobTitle());
			 globalVarsMap.put("LoginLink",linkBuild.append("<a href='").append(sourceleadUrl).append("/").append("' style='text-decoration:none; '><div style=' width:220px; border-radius:30px; background-color:#214b90; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Login</div></a>").toString());
			globalVarsMap.put("interviewTableDetails",interviewTable.toString());
		 //setting the Recipient information
		 InternetAddress[] iAdressArray = InternetAddress.parse(emailMessage.getToEmail());
		 
		 //getting the current EmailScenario by EmailScenario unique name and entity type
		 EmailScenario  emailScenario = null;
			 emailScenario =  emailScenarioService.getEmailScenarioByUniqueName(Constants.INTERVIEWER_INVITATION_REDIFINED,Constants.ENTITY_TYPE_EMAIL);
		
		 //covert subject,body and signature
		 String subject = emailScenerioUtil.bulidSubject(emailScenario.getSubject(),globalVarsMap);
		 String body = emailScenerioUtil.buildTempBody(emailScenario.getBody(),globalVarsMap);
		 String signature = emailScenerioUtil.buildSignature(emailScenario.getSignature(),globalVarsMap);
		 String url = sourceleadUrl;
		 
		 //construct HTML template using required data
		 String emailTemplate = emailScenerioUtil.AssembleEmailMessage(emailScenario,emailMessage, subject, body, signature, url, new Org(),orgLocations);
		 emailMessage.setSubject(subject);
		 emailMessage.setMessageBody(emailTemplate);
		 
		 
		 //calling the sendEmail method of emailScenerioUtil
		 emailScenerioUtil.sendEmail(emailMessage,orgLocations.getId(),iAdressArray,new ArrayList<EmailAttachment>());

		 EmailScenario noificationEmailScenario =  emailScenarioService.getEmailScenarioByUniqueName(Constants.INTERVIEWER_INVITATION_REDIFINED,Constants.ENTITY_TYPE_NOTIFICATION); 
		 String actionLink = "";
		 
		 if(interviewerUser.getFirstName()!=null){
		 String notificationbody = emailScenerioUtil.buildTempBody(noificationEmailScenario.getBody(),globalVarsMap);
		 noificationEmailScenario.setBody(notificationbody);
		 
		 emailScenerioUtil.sendNotification(actionLink, loginUser, interviewerUser, orgLocations, noificationEmailScenario);
		 }
	}
	
	
	
	public StringBuilder buildInterviewCandidatteTable(List<RequirementSubmissionTemplateFields> interviewcandidateList, String interviewTypeValue, String dateOfInterviewValue, RequirementInterview requirementInterview, String templateId, List<RequirementInterview> intervieresArrayList, List<RequirementInterviewer> requirementIntervier, User interviewerUser) throws Exception {
		StringBuilder tempHTML = new StringBuilder();
	         tempHTML.append("<table width='100%' bgcolor='#F9F9F9' cellpadding='0' border='2' cellspacing='0' style='background-color:#F9F9F9;'>");
	         tempHTML.append("<tr>");
	         tempHTML.append("<th> Applicant Name </th>");
	         tempHTML.append("<th> Interview Type </th>");
	         tempHTML.append("<th> Interview Time & Date </th>");
	         tempHTML.append("<th> Details </th>");
	         if(null != templateId && !"".equalsIgnoreCase(templateId)){
	         tempHTML.append("<th> Feedback </th>");
	         }
	         tempHTML.append("</tr>");
	        
	     		for(RequirementSubmissionTemplateFields requirementSubmissionTemplateFields:interviewcandidateList){
	     			 
	     			tempHTML.append("<tr>");
			         
			         if(requirementSubmissionTemplateFields.getLastName() != null && !"".equalsIgnoreCase(requirementSubmissionTemplateFields.getLastName())){
		        		 tempHTML.append("<td style='text-align: center;'>").append(requirementSubmissionTemplateFields.getFirstName()).append(" ").append(requirementSubmissionTemplateFields.getLastName()).append("</td>");
		        	 }else{
		        		 tempHTML.append("<td style='text-align: center;'>").append(requirementSubmissionTemplateFields.getFirstName()).append("</td>");
		        	 }
			         
			         if(interviewTypeValue != null && !"".equalsIgnoreCase(interviewTypeValue)){
		        		 tempHTML.append("<td style='text-align: center;'>").append(interviewTypeValue).append("</td>");
		        	 }else{
		        		 tempHTML.append("<td style='text-align: center;'>").append("--").append("</td>");
		        	 }
			         if(dateOfInterviewValue != null && !"".equalsIgnoreCase(dateOfInterviewValue)){
		        		 tempHTML.append("<td style='text-align: center;'>").append(dateOfInterviewValue).append("</td>");
		        	 }else{
		        		 tempHTML.append("<td style='text-align: center;'>").append("--").append("</td>");
		        	 }
			         if(requirementInterview.getLocation() != null && !"".equalsIgnoreCase(requirementInterview.getLocation())){
		        		 tempHTML.append("<td style='text-align: center;'>").append(requirementInterview.getLocation()).append("</td>");
		        	 }else{
		        		 tempHTML.append("<td style='text-align: center;'>").append("--").append("</td>");
		        	 }
			         int i=0;
			         if(null != templateId && !"".equalsIgnoreCase(templateId)){
					for (RequirementInterview interviersArray : intervieresArrayList) {
						if (null != requirementSubmissionTemplateFields.getRequirementSubmission() && !"".equalsIgnoreCase(requirementSubmissionTemplateFields.getRequirementSubmission().toString())) {
							if (requirementSubmissionTemplateFields.getRequirementSubmission().getId().equals(interviersArray.getSubmissionId().getId())) {
								
								for (RequirementInterviewer requirementIntervierId : requirementIntervier) {
										if (requirementIntervierId.getRequirementInterview().getId().equals(interviersArray.getId()) && requirementIntervierId.getInterviewerUser().getId().equals(interviewerUser.getId()) && i == 0) {
											tempHTML.append("<td>").append("<a href='").append(sourceleadUrl).append("/publicInterviewFeedBackTemplate?templateId=").append(templateId).append("&requirementInterviewid=").append(requirementIntervierId.getId()).append("'style='text-decoration:none; '><div style=' width:100px; border-radius:30px; background-color:#D33B39; color:#FFF; font-size:15px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Feedback</div></a>").append("</td>");
											i++;
										}
								}
							}
						}
					}
	     		}
				}
	         tempHTML.append("</tr>");
		     tempHTML.append("</table> ");
		return tempHTML;
	}
	
	
	/**
	 *@author Laxman
	 *Update Reschedule Interview
	 */
	@Override
	public void updateRescheduleInterview(JSONObject jsonObj, User user, OrgLocations orgLocations, Org loginOrg) throws Exception {
		RequirementInterview requirementInterview = requirementInterviewDao.getRequirementInterviewById(jsonObj.getString("interviewId"));
		Timezone zone = timeZoneService.getTimezone(Long.parseLong(jsonObj.getString("rescheduleTimeZone")));
		requirementInterview.setTimezone(zone);
		
		if(jsonObj.has("rescheduleDate") && jsonObj.getString("rescheduleDate") != null && !"".equalsIgnoreCase(jsonObj.getString("rescheduleDate"))){
			// changed by prasanna vadla .. due to timezone 
			Date startTime =  CalendarUtil.getDateFromDateAndTime(commonUtil.getDateFormat(jsonObj.getString("rescheduleDate")), 
				      jsonObj.getString("startTime"));
			String currentTimeStr = Constants.TZ_DF.format(startTime);  
			String startTimeUTC= CommonUtil.convertTimeZones(zone.getTimeZoneId(), "UTC", currentTimeStr);
			Date converteDate = Constants.DF_YMD.parse(startTimeUTC);
			requirementInterview.setDateOfInterview(converteDate);
		}
		
		if(jsonObj.has("startTime") && !jsonObj.getString("startTime").isEmpty()){
			  Date startTime =  CalendarUtil.getDateFromDateAndTime(commonUtil.getDateFormat(jsonObj.getString("rescheduleDate")), 
					      jsonObj.getString("startTime"));
				String currentTimeStr = Constants.TZ_DF.format(startTime);  
				String startTimeUTC= CommonUtil.convertTimeZones(zone.getTimeZoneId(), "UTC", currentTimeStr);
				DateFormat TZ_DF_TS = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
				Date convertedDate = TZ_DF_TS.parse(startTimeUTC);
				requirementInterview.setStartTime(convertedDate);
		}
		
		if(jsonObj.has("endTime") && !jsonObj.getString("endTime").isEmpty()){
			Date endTime =  CalendarUtil.getDateFromDateAndTime(commonUtil.getDateFormat(jsonObj.getString("rescheduleDate")), 
				      jsonObj.getString("endTime"));
			String currentTimeStr = Constants.TZ_DF.format(endTime);  
			String endTimeUTC= CommonUtil.convertTimeZones(zone.getTimeZoneId(), "UTC", currentTimeStr);
			DateFormat TZ_DF_TS = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
			Date convertedDate = TZ_DF_TS.parse(endTimeUTC);
		    requirementInterview.setEndTime(convertedDate);
		}
		if(jsonObj.has("reschedulComment") && !jsonObj.getString("reschedulComment").isEmpty()){
			requirementInterview.setRescheduleComment(jsonObj.getString("reschedulComment"));
		}
		
		if(jsonObj.has("scheduleType") && !jsonObj.getString("scheduleType").isEmpty()){
			requirementInterview.setScheduleType(jsonObj.getString("scheduleType"));
		}
		requirementInterview.setUpdatedDate(new Date());
        requirementInterview.setUpdatedBy(user.getUserCode());
        RequirementInterview updateInterview = requirementInterviewDao.storeRequirementInterview(requirementInterview);
        
        RequirementInterviewHistory  interviewHistory = requirementInterviewHistoryService.getRequirementInterviewHistoryByInterviewId(updateInterview.getId());
        if(jsonObj.has("notificationToApplicant") && !jsonObj.getString("notificationToApplicant").isEmpty()){
     	   interviewHistory.setNotificationToApplicant(jsonObj.getString("notificationToApplicant"));
        }
        if(jsonObj.has("notificationToSpoc") && !jsonObj.getString("notificationToSpoc").isEmpty()){
     	   interviewHistory.setNotificationToSpoc(jsonObj.getString("notificationToSpoc"));
        }
        requirementInterviewHistoryService.storeRequirementInterviewHistory(interviewHistory);
        
        Candidate candidate = candidateService.getCandidateByCandidateId(interviewHistory.getSubmissionId().getCandidate().getId());
        
        RequirementSubmission requirementSubmission = requirementSubmissionService.getRequirementSubmissionById(updateInterview.getSubmissionId().getId());
        if(jsonObj.has("notificationToApplicant") && !jsonObj.getString("notificationToApplicant").isEmpty()&& jsonObj.getString("notificationToApplicant").equalsIgnoreCase("Y")){
     	  // sendEmailToApplicants(candidate,orgLocations,requirementSubmission, user, loginOrg, requirementInterview);
     	   sendRescheduledInterviewEmailToApplicant(candidate,orgLocations,requirementSubmission, user, loginOrg, requirementInterview);
        }
        if(jsonObj.has("notificationToSpoc") && !jsonObj.getString("notificationToSpoc").isEmpty()&& jsonObj.getString("notificationToSpoc").equalsIgnoreCase("Y") && requirementSubmission.getVendorLocationId() != null){
     	   sendEmailToVendors(candidate,requirementSubmission, user,loginOrg, requirementInterview,orgLocations);
        }else if(jsonObj.has("notificationToSpoc") && !jsonObj.getString("notificationToSpoc").isEmpty()&& jsonObj.getString("notificationToSpoc").equalsIgnoreCase("Y")){
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
	}
	
	
	@Override
	public void acceptProposeTimeBytProposeTimeId(String proposeDate,String proposeTime, User user, OrgLocations orgLocations, Org loginOrg,Long proposeTimeId) throws Exception {
		//Update proposeTime
		ProposeTime proposetime = proposeTimeService.getProposeTimeById(proposeTimeId);
		proposetime.setInterviewTimeStatus("Accepted");
		proposetime.setUpdatedDate(commonUtil.currentDate());
		proposetime.setUpdatedBy(user.getUserCode());
		proposeTimeService.saveProposeTime(proposetime);
		
		//update requirement Interview
		RequirementInterview requirementInterview = requirementInterviewDao.getRequirementInterviewById(String.valueOf(proposetime.getInterviewId().getId()));
		Timezone zone = timeZoneService.getTimezone(requirementInterview.getTimezone().getId());
		requirementInterview.setTimezone(zone);
		
		if(proposeDate != null && proposeDate != ""){
			// changed by prasanna vadla .. due to timezone 
			Date startTime =  CalendarUtil.getDateFromDateAndTime(commonUtil.getDateFormat(proposeDate),  proposeTime);
			String currentTimeStr = Constants.TZ_DF.format(startTime);  
			String startTimeUTC= CommonUtil.convertTimeZones(zone.getTimeZoneId(), "UTC", currentTimeStr);
			Date converteDate = Constants.DF_YMD.parse(startTimeUTC);
			requirementInterview.setDateOfInterview(converteDate);
		}
		
		if(proposeTime != ""){
			  Date startTime =  CalendarUtil.getDateFromDateAndTime(commonUtil.getDateFormat(proposeDate),  proposeTime);
				String currentTimeStr = Constants.TZ_DF.format(startTime);  
				String startTimeUTC= CommonUtil.convertTimeZones(zone.getTimeZoneId(), "UTC", currentTimeStr);
				DateFormat TZ_DF_TS = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
				Date convertedDate = TZ_DF_TS.parse(startTimeUTC);
				requirementInterview.setStartTime(convertedDate);
		}
		requirementInterview.setEndTime(null);
		requirementInterview.setUpdatedDate(new Date());
        requirementInterview.setUpdatedBy(user.getUserCode());
        RequirementInterview updateInterview = requirementInterviewDao.storeRequirementInterview(requirementInterview);
        
        RequirementInterviewHistory  interviewHistory = requirementInterviewHistoryService.getRequirementInterviewHistoryByInterviewId(updateInterview.getId());
        requirementInterviewHistoryService.storeRequirementInterviewHistory(interviewHistory);
        Candidate candidate = candidateService.getCandidateByCandidateId(interviewHistory.getSubmissionId().getCandidate().getId());
        RequirementSubmission requirementSubmission = requirementSubmissionService.getRequirementSubmissionById(updateInterview.getSubmissionId().getId());
       
        //Send Email to Applicant after accept proposeTime 
        sendRescheduledInterviewEmailToApplicant(candidate,orgLocations,requirementSubmission, user, loginOrg, requirementInterview);
       
	}
	
	
	private void sendRescheduledInterviewEmailToApplicant(Candidate candidate,OrgLocations loginOrgLocations,RequirementSubmission requirementSubmission, User loginUser, Org loginOrg, RequirementInterview requirementInterview) throws Exception {
		 
		RequirementSubmissionTemplateFields requirementSubmissionTemplateFields = requirementSubmissionTemplateFieldsService.getSubmissionsTemplateFieldsByRequirementSubmissionsId(requirementSubmission.getId());
		Timezone timezone = timeZoneService.getTimezone(loginOrgLocations.getTimezone().getId());
		EmailMessage emailMessage = new EmailMessage();
		 emailMessage.setFirstName(requirementSubmissionTemplateFields.getFirstName());
		 emailMessage.setLastName(requirementSubmissionTemplateFields.getLastName());
		 emailMessage.setToEmail(requirementSubmissionTemplateFields.getEmailId());
		 emailMessage.setFromEmail(loginUser.getEmail());
		 emailMessage.setCreatedBy(loginUser.getUserCode());
		 StringBuilder timeStringBuilder=new StringBuilder();
		 emailMessage.setFromName(timeStringBuilder.append(loginUser.getFirstName()).append(" ").append(loginUser.getLastName()).toString());
		 emailMessage.setEmailType(Constants.CUSTOM_USER_EMAIL);
		 
		 String time = "";
		 String startTime = "";
		 String endTime = "";
		 String dateOfInterviewStr ="";
		 
		 if(requirementInterview.getDateOfInterview() != null){

				String currentTimeStr = Constants.TZ_DF.format(requirementInterview.getStartTime());
				DateFormat TZ_DF_TS = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
				String interviewDateInTimezone= CommonUtil.convertTimeZones(Constants.UTC_TIMEZONE  ,requirementInterview.getTimezone().getTimeZoneId(), currentTimeStr);
				Date interviewConverteDate = TZ_DF_TS.parse(interviewDateInTimezone);
				interviewDateInTimezone = LoginSessionController.DATEFORMAT.format(interviewConverteDate);
				dateOfInterviewStr = interviewDateInTimezone;
		    }
		    
		    if(requirementInterview.getStartTime() != null){
		    	DateFormat DF_DMY = new SimpleDateFormat("hh:mma");
				String currentTimeStr = Constants.TZ_DF.format(requirementInterview.getStartTime());  
				String startTimeUTC= CommonUtil.convertTimeZones(Constants.UTC_TIMEZONE ,requirementInterview.getTimezone().getTimeZoneId(), currentTimeStr);
				DateFormat TZ_DF_TS = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
				Date convertedDate = TZ_DF_TS.parse(startTimeUTC);
				String convertedDateStr = DF_DMY.format(convertedDate);
				startTime = convertedDateStr;
			}
				 
		    if(requirementInterview.getEndTime() != null){
		    	DateFormat DF_DMY = new SimpleDateFormat("hh:mma");
				String currentTimeStr = Constants.TZ_DF.format(requirementInterview.getEndTime());  
				String startTimeUTC= CommonUtil.convertTimeZones(Constants.UTC_TIMEZONE ,requirementInterview.getTimezone().getTimeZoneId(), currentTimeStr);
				DateFormat TZ_DF_TS = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
				Date convertedDate = TZ_DF_TS.parse(startTimeUTC);
				String convertedDateStr = DF_DMY.format(convertedDate);  
				endTime = convertedDateStr;
		    }
		 
		 if(endTime!=null){
			 StringBuilder timeBuilder=new StringBuilder();
			  time = timeBuilder.append(startTime).append(" - ").append(endTime).append(" ("+requirementInterview.getTimezone().getTimeZoneId()+")").toString();
		 }
		 String interviewTypeValue="--";
		 if(requirementInterview.getInterviewType() !=null){
			 interviewTypeValue = requirementInterview.getInterviewType();
		 }
		//setting the class filed values
		 Map<String, String> globalVarsMap = new HashMap<String, String>();
			 globalVarsMap.put("applicantName", emailMessage.getFirstName());
			 globalVarsMap.put("loginUser", emailMessage.getFromName());
			 //globalVarsMap.put("requirementTitle", requirementSubmission.getRequirement().getJobTitle());
			 globalVarsMap.put("loginOrgName",loginOrgLocations.getOrgLocationName());
			 globalVarsMap.put("interviewDate", dateOfInterviewStr);
			 globalVarsMap.put("interviewTime",time);
			 globalVarsMap.put("interviewType",interviewTypeValue);
			 globalVarsMap.put("comment",requirementInterview.getRescheduleComment());
			 StringBuilder acceptBuilder = new StringBuilder();
			 StringBuilder rejectBuilder = new StringBuilder();
			 //StringBuilder tentativeBuilder = new StringBuilder();
			 
			 if(requirementSubmission.getRequirement().getJobId() !=null && !"".equalsIgnoreCase(requirementSubmission.getRequirement().getJobId())){
				 StringBuilder reqsb=new StringBuilder();
				 
				 globalVarsMap.put("requirementTitle",reqsb.append(requirementSubmission.getRequirement().getJobId()).append("-").append(requirementSubmission.getRequirement().getJobTitle()).toString());
			 }
			 
			 else{
				 globalVarsMap.put("requirementTitle", requirementSubmission.getRequirement().getJobTitle()); 
				 
			 }
			 
			 globalVarsMap.put("acceptURL",acceptBuilder.append("<a href='").append(sourceleadUrl).append("/acceptOrRejectInterview?interviewStatus=Attending&requirementInterviewId=").append(requirementInterview.getId()).append("'style='text-decoration:none;float:left; margin-left:55px;'><div style=' width:220px; border-radius:30px; background-color:#5cb85c; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Accept</div></a>").toString());
			 globalVarsMap.put("rejectURL",rejectBuilder.append("<a href='").append(sourceleadUrl).append("/acceptOrRejectInterview?interviewStatus=Not_Attending&requirementInterviewId=").append(requirementInterview.getId()).append("'style='text-decoration:none;float:left;margin-left:55px;'><div style=' width:220px; border-radius:30px; background-color:#e74c51; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Reject</div></a>").toString());
			 //globalVarsMap.put("proposeNewTime",tentativeBuilder.append("<a href='").append(sourceleadUrl).append("/acceptOrRejectInterview?interviewStatus=Maybe&requirementInterviewId=").append(requirementInterview.getId()).append("'style='text-decoration:none;float:left;margin-left:55px;'><div style='width:220px; border-radius:30px; background-color:#f0ad4e;; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Tentative</div></a>").toString());
			 
		 //setting the Recipient information
		 InternetAddress[] iAdressArray = InternetAddress.parse(emailMessage.getToEmail());
		
		 //getting the current EmailScenario by EmailScenario unique name and entity type
		 EmailScenario  emailScenario = null;
			 emailScenario =  emailScenarioService.getEmailScenarioByUniqueName(Constants.INTERVIEW_RESCHEDULEMAIL_TO_APPLICANT,Constants.ENTITY_TYPE_EMAIL);
		
		 //covert subject,body and signature
		 String subject = emailScenerioUtil.bulidSubject(emailScenario.getSubject(),globalVarsMap);
		 String body = emailScenerioUtil.buildTempBody(emailScenario.getBody(),globalVarsMap);
		 String signature = emailScenerioUtil.buildSignature(emailScenario.getSignature(),globalVarsMap);
		 String url = sourceleadUrl;
		 
		 //construct HTML template using required data
		 String emailTemplate = emailScenerioUtil.AssembleEmailMessage(emailScenario,emailMessage, subject, body, signature, url, loginOrg,loginOrgLocations);
		 emailMessage.setSubject(subject);
		 emailMessage.setMessageBody(emailTemplate);
		 
		 
		 //calling the sendEmail method of emailScenerioUtil
		 emailScenerioUtil.sendEmail(emailMessage,loginOrgLocations.getId(),iAdressArray,new ArrayList<EmailAttachment>());
		}


	@Override
	public void sendEmailToInterviewrs(Candidate candidate, User interviewerUser,RequirementSubmission requirementSubmission, User loginUser,Org org,RequirementInterview requirementInterview,OrgLocations orgLocations)	throws Exception {
		 
		RequirementSubmissionTemplateFields requirementSubmissionTemplateFields = requirementSubmissionTemplateFieldsService.getSubmissionsTemplateFieldsByRequirementSubmissionsId(requirementSubmission.getId());
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
		 StringBuilder interviewStringBuilder=new StringBuilder();
		 emailMessage.setToEmail(interviewerUser.getEmail());
		 emailMessage.setFromEmail(loginUser.getEmail());
		 emailMessage.setCreatedBy(loginUser.getUserCode());
		 emailMessage.setFromName(interviewStringBuilder.append(loginUser.getFirstName()).append(" ").append(loginUser.getLastName()).toString());
		 emailMessage.setEmailType(Constants.CUSTOM_USER_EMAIL);
		 String candidateName = requirementSubmissionTemplateFields.getFirstName();
		 if(null != candidate.getLastName()){
			 StringBuilder nameBuilder=new StringBuilder();
			 candidateName=nameBuilder.append(candidateName).append(" ").append(requirementSubmissionTemplateFields.getLastName()).toString();
		 }
		 
		
		 String interviewTypeValue="--";
		 if(requirementInterview.getInterviewType() !=null){
			 interviewTypeValue = requirementInterview.getInterviewType();
		 }
	 
		 String time = "";
		 String startTime = "";
		 String endTime = "";
		 String dateOfInterviewStr ="";
		 String dateOfInterviewValue ="";
		 
		 if(requirementInterview.getDateOfInterview() != null){

				String currentTimeStr = Constants.TZ_DF.format(requirementInterview.getStartTime());
				DateFormat TZ_DF_TS = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
				String interviewDateInTimezone= CommonUtil.convertTimeZones(Constants.UTC_TIMEZONE  ,requirementInterview.getTimezone().getTimeZoneId(), currentTimeStr);
				Date interviewConverteDate = TZ_DF_TS.parse(interviewDateInTimezone);
				interviewDateInTimezone = LoginSessionController.DATEFORMAT.format(interviewConverteDate);
				dateOfInterviewStr = interviewDateInTimezone;
		    }
		    
		    if(requirementInterview.getStartTime() != null){
		    	DateFormat DF_DMY = new SimpleDateFormat("hh:mma");
				String currentTimeStr = Constants.TZ_DF.format(requirementInterview.getStartTime());  
				String startTimeUTC= CommonUtil.convertTimeZones(Constants.UTC_TIMEZONE ,requirementInterview.getTimezone().getTimeZoneId(), currentTimeStr);
				DateFormat TZ_DF_TS = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
				Date convertedDate = TZ_DF_TS.parse(startTimeUTC);
				String convertedDateStr = DF_DMY.format(convertedDate);
				startTime = convertedDateStr;
			}
				 
		    if(requirementInterview.getEndTime() != null){
		    	DateFormat DF_DMY = new SimpleDateFormat("hh:mma");
				String currentTimeStr = Constants.TZ_DF.format(requirementInterview.getEndTime());  
				String startTimeUTC= CommonUtil.convertTimeZones(Constants.UTC_TIMEZONE ,requirementInterview.getTimezone().getTimeZoneId(), currentTimeStr);
				DateFormat TZ_DF_TS = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
				Date convertedDate = TZ_DF_TS.parse(startTimeUTC);
				String convertedDateStr = DF_DMY.format(convertedDate);  
				endTime = convertedDateStr;
		    }
		 
		 if(endTime!=null){
			 StringBuilder timeBuilder=new StringBuilder();
			  time = timeBuilder.append(startTime).append(" - ").append(endTime).append(" ("+requirementInterview.getTimezone().getTimeZoneId()+")").toString();
		 }
		 StringBuilder dateOfInterviewSB=new StringBuilder();
		 if(null != dateOfInterviewStr){
		  dateOfInterviewValue = dateOfInterviewSB.append(dateOfInterviewStr).append(" ").append(time).toString();
		 }else{
		 dateOfInterviewValue = dateOfInterviewSB.append("--").toString();
		 }
		//setting the class filed values
		 Map<String, String> globalVarsMap = new HashMap<String, String>();
			 globalVarsMap.put("interviewerFirstName", emailMessage.getFirstName());
			 globalVarsMap.put("interviewerLastName",emailMessage.getLastName());
			 globalVarsMap.put("loginUser", emailMessage.getFromName());
			 globalVarsMap.put("requirementTitle", requirementSubmission.getRequirement().getJobTitle());
			 globalVarsMap.put("interviewDate", dateOfInterviewValue);
			 globalVarsMap.put("interviewType",interviewTypeValue);
			 globalVarsMap.put("applicantName",candidateName);
			 globalVarsMap.put("Details",requirementInterview.getLocation());
			 
		 //setting the Recipient information
		 InternetAddress[] iAdressArray = InternetAddress.parse(emailMessage.getToEmail());
		
		 //getting the current EmailScenario by EmailScenario unique name and entity type
		 EmailScenario  emailScenario = null;
			 emailScenario =  emailScenarioService.getEmailScenarioByUniqueName(Constants.INTERVIEWER_INVITATION,Constants.ENTITY_TYPE_EMAIL);
		
		 //covert subject,body and signature
		 String subject = emailScenerioUtil.bulidSubject(emailScenario.getSubject(),globalVarsMap);
		 String body = emailScenerioUtil.buildTempBody(emailScenario.getBody(),globalVarsMap);
		 String signature = emailScenerioUtil.buildSignature(emailScenario.getSignature(),globalVarsMap);
		 String url = sourceleadUrl;
		 
		 //construct HTML template using required data
		 String emailTemplate = emailScenerioUtil.AssembleEmailMessage(emailScenario,emailMessage, subject, body, signature, url, org,orgLocations);
		 emailMessage.setSubject(subject);
		 emailMessage.setMessageBody(emailTemplate);
		 
		 
		 //calling the sendEmail method of emailScenerioUtil
		 emailScenerioUtil.sendEmail(emailMessage,orgLocations.getId(),iAdressArray,new ArrayList<EmailAttachment>());

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
		 
		RequirementSubmissionTemplateFields requirementSubmissionTemplateFields = requirementSubmissionTemplateFieldsService.getSubmissionsTemplateFieldsByRequirementSubmissionsId(requirementSubmission.getId());
		Timezone timezone = timeZoneService.getTimezone(loginOrgLocations.getTimezone().getId());
		EmailMessage emailMessage = new EmailMessage();
		 emailMessage.setFirstName(requirementSubmissionTemplateFields.getFirstName());
		 emailMessage.setLastName(requirementSubmissionTemplateFields.getLastName());
		 emailMessage.setToEmail(requirementSubmissionTemplateFields.getEmailId());
		 emailMessage.setFromEmail(loginUser.getEmail());
		 emailMessage.setCreatedBy(loginUser.getUserCode());
		 StringBuilder timeStringBuilder=new StringBuilder();
		 emailMessage.setFromName(timeStringBuilder.append(loginUser.getFirstName()).append(" ").append(loginUser.getLastName()).toString());
		 emailMessage.setEmailType(Constants.CUSTOM_USER_EMAIL);
		 
		 String time = "";
		 String startTime = "";
		 String endTime = "";
		 String dateOfInterviewStr ="";
		 
		 if(requirementInterview.getDateOfInterview() != null){

				String currentTimeStr = Constants.TZ_DF.format(requirementInterview.getStartTime());
				DateFormat TZ_DF_TS = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
				String interviewDateInTimezone= CommonUtil.convertTimeZones(Constants.UTC_TIMEZONE  ,requirementInterview.getTimezone().getTimeZoneId(), currentTimeStr);
				Date interviewConverteDate = TZ_DF_TS.parse(interviewDateInTimezone);
				interviewDateInTimezone = LoginSessionController.DATEFORMAT.format(interviewConverteDate);
				dateOfInterviewStr = interviewDateInTimezone;
		    }
		    
		    if(requirementInterview.getStartTime() != null){
		    	DateFormat DF_DMY = new SimpleDateFormat("hh:mma");
				String currentTimeStr = Constants.TZ_DF.format(requirementInterview.getStartTime());  
				String startTimeUTC= CommonUtil.convertTimeZones(Constants.UTC_TIMEZONE ,requirementInterview.getTimezone().getTimeZoneId(), currentTimeStr);
				DateFormat TZ_DF_TS = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
				Date convertedDate = TZ_DF_TS.parse(startTimeUTC);
				String convertedDateStr = DF_DMY.format(convertedDate);
				startTime = convertedDateStr;
			}
				 
		    if(requirementInterview.getEndTime() != null){
		    	DateFormat DF_DMY = new SimpleDateFormat("hh:mma");
				String currentTimeStr = Constants.TZ_DF.format(requirementInterview.getEndTime());  
				String startTimeUTC= CommonUtil.convertTimeZones(Constants.UTC_TIMEZONE ,requirementInterview.getTimezone().getTimeZoneId(), currentTimeStr);
				DateFormat TZ_DF_TS = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
				Date convertedDate = TZ_DF_TS.parse(startTimeUTC);
				String convertedDateStr = DF_DMY.format(convertedDate);  
				endTime = convertedDateStr;
		    }
		 
		 if(endTime!=null){
			 StringBuilder timeBuilder=new StringBuilder();
			  time = timeBuilder.append(startTime).append(" - ").append(endTime).append(" ("+requirementInterview.getTimezone().getTimeZoneId()+")").toString();
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
			 globalVarsMap.put("loginOrgName",loginOrgLocations.getOrgLocationName());
			 globalVarsMap.put("interviewDate", dateOfInterviewStr);
			 globalVarsMap.put("interviewTime",time);
			 globalVarsMap.put("interviewType",interviewTypeValue);
			 globalVarsMap.put("Details",requirementInterview.getLocation());
			 StringBuilder acceptBuilder = new StringBuilder();
			 StringBuilder rejectBuilder = new StringBuilder();
			 StringBuilder tentativeBuilder = new StringBuilder();
			 
			 globalVarsMap.put("acceptURL",acceptBuilder.append("<a href='").append(sourceleadUrl).append("/acceptOrRejectInterview?interviewStatus=Attending&requirementInterviewId=").append(requirementInterview.getId()).append("'style='text-decoration:none;float:left; margin-left:55px;'><div style=' width:220px; border-radius:30px; background-color:#5cb85c; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Accept</div></a>").toString());
			 globalVarsMap.put("rejectURL",rejectBuilder.append("<a href='").append(sourceleadUrl).append("/acceptOrRejectInterview?interviewStatus=Not_Attending&requirementInterviewId=").append(requirementInterview.getId()).append("'style='text-decoration:none;float:left;margin-left:55px;'><div style=' width:220px; border-radius:30px; background-color:#e74c51; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Reject</div></a>").toString());
			if("Y".equalsIgnoreCase(requirementInterview.getProposeTime())){
				globalVarsMap.put("tentativeURL",tentativeBuilder.append("<a href='").append(sourceleadUrl).append("/proposeNewTimePublicpage?interviewStatus=Maybe&requirementInterviewId=").append(requirementInterview.getId()).append("'style='text-decoration:none;float:left;margin-left:55px;'><div style='width:220px; border-radius:30px; background-color:#f0ad4e;; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Propose New Time</div></a>").toString());	
			}
			 
			 
		 //setting the Recipient information
		 InternetAddress[] iAdressArray = InternetAddress.parse(emailMessage.getToEmail());
		
		 //getting the current EmailScenario by EmailScenario unique name and entity type
		 EmailScenario  emailScenario = null;
			 emailScenario =  emailScenarioService.getEmailScenarioByUniqueName(Constants.APPLICANT_INTERVIEW,Constants.ENTITY_TYPE_EMAIL);
		
		 //covert subject,body and signature
		 String subject = emailScenerioUtil.bulidSubject(emailScenario.getSubject(),globalVarsMap);
		 String body = emailScenerioUtil.buildTempBody(emailScenario.getBody(),globalVarsMap);
		 String signature = emailScenerioUtil.buildSignature(emailScenario.getSignature(),globalVarsMap);
		 String url = sourceleadUrl;
		 
		 //construct HTML template using required data
		 String emailTemplate = emailScenerioUtil.AssembleEmailMessage(emailScenario,emailMessage, subject, body, signature, url, loginOrg,loginOrgLocations);
		 emailMessage.setSubject(subject);
		 emailMessage.setMessageBody(emailTemplate);
		 
		 
		 //calling the sendEmail method of emailScenerioUtil
		 emailScenerioUtil.sendEmail(emailMessage,loginOrgLocations.getId(),iAdressArray,new ArrayList<EmailAttachment>());
		 
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
					StringBuilder requirementBuilder = new StringBuilder();
				    requirementInterview.setRemainderTime(requirementBuilder.append(hours).append(" : ").append(minutes).append(" minutes").toString());
				}
				
			    if(requirementInterview.getDateOfInterview() != null){
			    	requirementInterview.setDateOfInterviewStr(LoginSessionController.DATEFORMAT.format(requirementInterview.getDateOfInterview()));
			    }
			    
			    if(requirementInterview.getStartTime() != null){
			    	 String hours = Integer.toString(requirementInterview.getStartTime().getHours());
			    	 String minutes = Integer.toString(requirementInterview.getStartTime().getMinutes());
			    	 StringBuilder startBuilder = new StringBuilder();
			    	 requirementInterview.setStartTimeStr(CalendarUtil.get12FormatTimeFrom24FormatTime(startBuilder.append(hours).append(":").append(minutes).toString()));
			    }
			    
			    if(requirementInterview.getEndTime() != null){
			    	 String hours = Integer.toString(requirementInterview.getEndTime().getHours());
			    	 String minutes = Integer.toString(requirementInterview.getEndTime().getMinutes());
			    	 StringBuilder endBuilder = new StringBuilder();
			    	 requirementInterview.setEndTimeStr(CalendarUtil.get12FormatTimeFrom24FormatTime(endBuilder.append(hours).append(":").append(minutes).toString()));
			    }
			    
			    String InterviewersList = "";
				Set<RequirementInterviewer> Interviewers = requirementInterview.getRequirementInterviewers();
				int i = 0;
				for(RequirementInterviewer interviewer: Interviewers){
					if(i <= 50){
						 StringBuilder interviewerBuilder = new StringBuilder();
						InterviewersList = interviewerBuilder.append(InterviewersList).append(interviewer.getInterviewerUser().getFirstName()).append(" ").append(interviewer.getInterviewerUser().getLastName()).toString();
						if(i != Interviewers.size()-1){
							 StringBuilder interviewBuilder = new StringBuilder();
							InterviewersList = interviewBuilder.append(InterviewersList).append(",").toString();
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
					 StringBuilder interviewslistBuilder = new StringBuilder();
				    requirementInterview.setRemainderTime(interviewslistBuilder.append(hours).append(" : ").append(minutes).append(" minutes").toString());
				}
				
			    if(requirementInterview.getDateOfInterview() != null){
			    	requirementInterview.setDateOfInterviewStr(LoginSessionController.DATEFORMAT.format(requirementInterview.getDateOfInterview()));
			    }
			    
			    if(requirementInterview.getStartTime() != null){
			    	 String hours = Integer.toString(requirementInterview.getStartTime().getHours());
			    	 String minutes = Integer.toString(requirementInterview.getStartTime().getMinutes());
			    	 StringBuilder startBuilder = new StringBuilder();
			    	 requirementInterview.setStartTimeStr(CalendarUtil.get12FormatTimeFrom24FormatTime(startBuilder.append(hours).append(":").append(minutes).toString()));
			    }
			    
			    if(requirementInterview.getEndTime() != null){
			    	 String hours = Integer.toString(requirementInterview.getEndTime().getHours());
			    	 String minutes = Integer.toString(requirementInterview.getEndTime().getMinutes());
			    	 StringBuilder endBuilder = new StringBuilder();
			    	 requirementInterview.setEndTimeStr(CalendarUtil.get12FormatTimeFrom24FormatTime(endBuilder.append(hours).append(":").append(minutes).toString()));
			    }
			    
			    if(requirementInterview.getSubmissionId().getRequirement().getClientId() != null){
					if(requirementInterview.getSubmissionId().getRequirement().getClientId().getResponderType().equalsIgnoreCase("Client")){
						requirementInterview.setClientName(requirementInterview.getSubmissionId().getRequirement().getClientId().getResponderLocationId().getOrgLocationName());
					}
					if(requirementInterview.getSubmissionId().getRequirement().getClientId().getResponderType().equalsIgnoreCase("Vendor")){
						requirementInterview.setClientName(requirementInterview.getSubmissionId().getRequirement().getClientId().getLocationId().getOrgLocationName());
					}
				}
			    
			    String InterviewersList = "";
				Set<RequirementInterviewer> Interviewers = requirementInterview.getRequirementInterviewers();
				int i = 0;
				for(RequirementInterviewer interviewer: Interviewers){
					if(i <= 50){
						 StringBuilder interviewersBuilder = new StringBuilder();
						InterviewersList = interviewersBuilder.append(InterviewersList).append(interviewer.getInterviewerUser().getFirstName()).append(" ").append(interviewer.getInterviewerUser().getLastName()).toString();
						if(i != Interviewers.size()-1){
							 StringBuilder intervieBuilder = new StringBuilder();
							InterviewersList = intervieBuilder.append(InterviewersList).append(",").toString();
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
		 emailMessage.setFromEmail(loginUser.getEmail());
		 emailMessage.setCreatedBy(loginUser.getUserCode());
		 StringBuilder nameStringBuilder=new StringBuilder();
		 emailMessage.setFromName(nameStringBuilder.append(loginUser.getFirstName()).append(" ").append(loginUser.getLastName()).toString());
		 emailMessage.setEmailType(Constants.CUSTOM_USER_EMAIL);
		 String time = "";
		 String startTime = "";
		 String endTime = "";
		 String dateOfInterviewStr ="";
		 
		 if(requirementInterview.getDateOfInterview() != null){

				String currentTimeStr = Constants.TZ_DF.format(requirementInterview.getStartTime());
				DateFormat TZ_DF_TS = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
				String interviewDateInTimezone= CommonUtil.convertTimeZones(Constants.UTC_TIMEZONE  ,requirementInterview.getTimezone().getTimeZoneId(), currentTimeStr);
				Date interviewConverteDate = TZ_DF_TS.parse(interviewDateInTimezone);
				interviewDateInTimezone = LoginSessionController.DATEFORMAT.format(interviewConverteDate);
				dateOfInterviewStr = interviewDateInTimezone;
		    }
		    
		    if(requirementInterview.getStartTime() != null){
		    	DateFormat DF_DMY = new SimpleDateFormat("hh:mma");
				String currentTimeStr = Constants.TZ_DF.format(requirementInterview.getStartTime());  
				String startTimeUTC= CommonUtil.convertTimeZones(Constants.UTC_TIMEZONE ,requirementInterview.getTimezone().getTimeZoneId(), currentTimeStr);
				DateFormat TZ_DF_TS = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
				Date convertedDate = TZ_DF_TS.parse(startTimeUTC);
				String convertedDateStr = DF_DMY.format(convertedDate);
				startTime = convertedDateStr;
			}
				 
		    if(requirementInterview.getEndTime() != null){
		    	DateFormat DF_DMY = new SimpleDateFormat("hh:mma");
				String currentTimeStr = Constants.TZ_DF.format(requirementInterview.getEndTime());  
				String startTimeUTC= CommonUtil.convertTimeZones(Constants.UTC_TIMEZONE ,requirementInterview.getTimezone().getTimeZoneId(), currentTimeStr);
				DateFormat TZ_DF_TS = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
				Date convertedDate = TZ_DF_TS.parse(startTimeUTC);
				String convertedDateStr = DF_DMY.format(convertedDate);  
				endTime = convertedDateStr;
		    }
		 
		 if(endTime!=null){
			 StringBuilder endTimeBuilder=new StringBuilder();
			  time=endTimeBuilder.append(startTime).append(" - ").append(endTime).append(" ("+requirementInterview.getTimezone().getTimeZoneId()+")").toString();
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
			 //globalVarsMap.put("requirementName", requirementSubmission.getRequirement().getJobTitle());
			 globalVarsMap.put("loginOrgName",loginOrgLocations.getOrgLocationName());
			 globalVarsMap.put("interviewDate", dateOfInterviewStr);
			 globalVarsMap.put("interviewTime",time);
			 globalVarsMap.put("interviewType",interviewTypeValue);
			 globalVarsMap.put("Details",requirementInterview.getLocation());
			 
			 if(requirementSubmission.getRequirement().getJobId() !=null && !"".equalsIgnoreCase(requirementSubmission.getRequirement().getJobId())){
				 StringBuilder reqsb=new StringBuilder();
				 
				 globalVarsMap.put("requirementName",reqsb.append(requirementSubmission.getRequirement().getJobId()).append("-").append(requirementSubmission.getRequirement().getJobTitle()).toString());
			 }
			 
			 else{
				 globalVarsMap.put("requirementName", requirementSubmission.getRequirement().getJobTitle()); 
				 
			 }
			 
			 
			 StringBuilder applicantNameBuilder=new StringBuilder();
			 RequirementSubmissionTemplateFields requirementSubmissionTemplateFields = requirementSubmissionTemplateFieldsService.getSubmissionsTemplateFieldsByRequirementSubmissionsId(requirementSubmission.getId());
			 if(requirementSubmissionTemplateFields != null){
				    if(requirementSubmissionTemplateFields.getLastName() != null){
				    	globalVarsMap.put("applicantName",applicantNameBuilder.append(requirementSubmissionTemplateFields.getFirstName()).append(" ").append(requirementSubmissionTemplateFields.getLastName()).toString());
					}else{
						globalVarsMap.put("applicantName",applicantNameBuilder.append(requirementSubmissionTemplateFields.getFirstName()).toString());
					}
			 }
			 
		 //setting the Recipient information
		 InternetAddress[] iAdressArray = InternetAddress.parse(emailMessage.getToEmail());
		 
		 //getting the current EmailScenario by EmailScenario unique name and entity type
		 EmailScenario  emailScenario = null;
			 //emailScenario =  emailScenarioService.getEmailScenarioByUniqueName(Constants.VENDOR_INTERVIEW,Constants.ENTITY_TYPE_EMAIL);
		 
		 if(requirementInterview.getScheduleType() !=null && requirementInterview.getScheduleType().equalsIgnoreCase("reschedule")){
			 
			 emailScenario =  emailScenarioService.getEmailScenarioByUniqueName(Constants.VENDOR_INTERVIEW_RESCHEDULE,Constants.ENTITY_TYPE_EMAIL);
		 }
		 else{
			 
			 emailScenario =  emailScenarioService.getEmailScenarioByUniqueName(Constants.VENDOR_INTERVIEW,Constants.ENTITY_TYPE_EMAIL); 
		 }
		
		 //covert subject,body and signature
		 String subject = emailScenerioUtil.bulidSubject(emailScenario.getSubject(),globalVarsMap);
		 String body = emailScenerioUtil.buildTempBody(emailScenario.getBody(),globalVarsMap);
		 String signature = emailScenerioUtil.buildSignature(emailScenario.getSignature(),globalVarsMap);
		 String url = sourceleadUrl;
		 
		 //construct HTML template using required data
		 String emailTemplate = emailScenerioUtil.AssembleEmailMessage(emailScenario,emailMessage, subject, body, signature, url, loginOrg,loginOrgLocations);
		 emailMessage.setSubject(subject);
		 emailMessage.setMessageBody(emailTemplate);
		 
		 
		 //calling the sendEmail method of emailScenerioUtil
		 emailScenerioUtil.sendEmail(emailMessage,loginOrgLocations.getId(),iAdressArray,new ArrayList<EmailAttachment>());
		 
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
		 RequirementSubmissionTemplateFields requirementSubmissionTemplateFields = requirementSubmissionTemplateFieldsService.getSubmissionsTemplateFieldsByRequirementSubmissionsId(requirementSubmission.getId());
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
		 emailMessage.setFromEmail(loginUser.getEmail());
		 emailMessage.setCreatedBy(loginUser.getUserCode());
		 StringBuilder timeBuilder = new StringBuilder();
		 emailMessage.setFromName(timeBuilder.append(loginUser.getFirstName()).append(" ").append(loginUser.getLastName()).toString());
		 emailMessage.setEmailType(Constants.CUSTOM_USER_EMAIL);
		 String candidateName = requirementSubmissionTemplateFields.getFirstName();
		 if(requirementSubmissionTemplateFields.getLastName()!=null){
			 StringBuilder nameBuilder = new StringBuilder();
			 candidateName=nameBuilder.append(candidateName).append(" ").append(requirementSubmissionTemplateFields.getLastName()).toString();
		 }
		 
		 String dateOfInterviewValue ="";
		 if(requirementInterview.getDateOfInterview()!=null){
			 dateOfInterviewValue = LoginSessionController.DATEFORMAT.format(requirementInterview.getDateOfInterview());
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
	    	 StringBuilder startBuilder = new StringBuilder();
	    	 time = CalendarUtil.get12FormatTimeFrom24FormatTime(startBuilder.append(hours).append(":").append(minutes).toString());
	    }
	    
	    if(requirementInterview.getEndTime() != null){
	    	 String hours = Integer.toString(requirementInterview.getEndTime().getHours());
	    	 String minutes = Integer.toString(requirementInterview.getEndTime().getMinutes());
	    	 StringBuilder endBuilder = new StringBuilder();
	    	 endTime = CalendarUtil.get12FormatTimeFrom24FormatTime(endBuilder.append(hours).append(":").append(minutes).toString());
	    }
		 
		 if(endTime!=null){
			 StringBuilder endTimeStrBuilder = new StringBuilder();
			  time=endTimeStrBuilder.append(time).append(" - ").append(endTime).toString();
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
		 InternetAddress[] iAdressArray = InternetAddress.parse(emailMessage.getToEmail());
		
		 //getting the current EmailScenario by EmailScenario unique name and entity type
		 EmailScenario  emailScenario = null;
			 emailScenario =  emailScenarioService.getEmailScenarioByUniqueName(Constants.INTERVIEWER_TEMPLATE,Constants.ENTITY_TYPE_EMAIL);
			 
		 //covert subject,body and signature
		 String subject = emailScenerioUtil.bulidSubject(emailScenario.getSubject(),globalVarsMap);
		 String body = emailScenerioUtil.buildTempBody(emailScenario.getBody(),globalVarsMap);
		 String signature = emailScenerioUtil.buildSignature(emailScenario.getSignature(),globalVarsMap);
		 String url = sourceleadUrl;
		 
		 //construct HTML template using required data
		 String emailTemplate = emailScenerioUtil.AssembleEmailMessage(emailScenario,emailMessage, subject, body, signature, url, org,orgLocations);
		 emailMessage.setSubject(subject);
		 emailMessage.setMessageBody(emailTemplate);
		 
		 
		 //calling the sendEmail method of emailScenerioUtil
		 emailScenerioUtil.sendEmail(emailMessage,orgLocations.getId(),iAdressArray,new ArrayList<EmailAttachment>());

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
			    	requirementInterview.setDateOfInterviewStr(LoginSessionController.DATEFORMAT.format(requirementInterview.getDateOfInterview()));
			    }
			    
			    if(requirementInterview.getStartTime() != null){
			    	 String hours = Integer.toString(requirementInterview.getStartTime().getHours());
			    	 String minutes = Integer.toString(requirementInterview.getStartTime().getMinutes());
			    	 StringBuilder startBuilder=new StringBuilder();
			    	 requirementInterview.setStartTimeStr(CalendarUtil.get12FormatTimeFrom24FormatTime(startBuilder.append(hours).append(":").append(minutes).toString()));
			    }
			    
			    if(requirementInterview.getEndTime() != null){
			    	 String hours = Integer.toString(requirementInterview.getEndTime().getHours());
			    	 String minutes = Integer.toString(requirementInterview.getEndTime().getMinutes());
			    	 StringBuilder endBuilder=new StringBuilder();
			    	 requirementInterview.setEndTimeStr(CalendarUtil.get12FormatTimeFrom24FormatTime(endBuilder.append(hours).append(":").append(minutes).toString()));
			    }
			    
			    RequirementSubmission requirementSubmission = requirementSubmissionService.getSubmissionsBySourceSubmissionId(requirementInterview.getSubmissionId().getId(),orgLocationsId);
			    if(requirementSubmission != null){
				   OrgLocations orgLocations = orgLocationService.getOrgLocations(requirementSubmission.getOrgLocations().getId());
				   requirementInterview.setClientName(orgLocations.getOrgLocationName());
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
	@Override
	public Integer getInterviewCountByCandidateId(Long candidateId, String submissionStatus, Date startDate,
			Date endDate) throws Exception {
		return requirementInterviewDao.getInterviewCountByCandidateId(candidateId, submissionStatus, startDate, endDate);
	}
	@Override
	public List<RequirementInterview> getRequirementInterviewsBySubmissionAndOrgLocId(List<Long> submissionsList, Long locationId,
			Date startUTCDate, Date endUTCDate) {
		return requirementInterviewDao.getRequirementInterviewsBySubmissionAndOrgLocId(submissionsList, locationId, startUTCDate, endUTCDate);
	}


	@Override
	public void applicantInterviewAcceptmailToInterviewInitiator(String requirementInterviewId) throws Exception {
		RequirementInterview reqnterview = getRequirementInterviewById(requirementInterviewId);
		User interviewCreactorUser =  userService.getUserByUserCode(reqnterview.getCreatedBy());
		RequirementSubmission reqSubmission = requirementSubmissionService.getRequirementSubmissionById(reqnterview.getSubmissionId().getId());
		EmailMessage emailMessage = new EmailMessage();
		 OrgSpoc spoc= null;
		if(null != reqSubmission.getVendorLocationId()){
			emailMessage.setFirstName(interviewCreactorUser.getFirstName());
			 emailMessage.setLastName(interviewCreactorUser.getLastName());
			 emailMessage.setToEmail(interviewCreactorUser.getEmail());
			
			 if(null != reqnterview.getSubmissionId().getSourceSubmission()){
				 RequirementSubmission reqSub =  requirementSubmissionService.getSubmissionsBySourceSubmissionId(reqSubmission.getId(), reqSubmission.getVendorLocationId().getId());
				Requirement req =  requirementService.getRequirementByRequirementId(reqSub.getSourceSubmission().getRequirement().getId());
				spoc = orgSpocService.getSpocBySpocUUID(req.getSpocId().getUUID());
			 }
			 emailMessage.setFromEmail(spoc.getVendorUser().getEmail());
			 emailMessage.setCreatedBy(interviewCreactorUser.getUserCode());
			 emailMessage.setEmailType(Constants.CUSTOM_USER_EMAIL);
		}else{
		 emailMessage.setFirstName(interviewCreactorUser.getFirstName());
		 emailMessage.setLastName(interviewCreactorUser.getLastName());
		 emailMessage.setToEmail(interviewCreactorUser.getEmail());
		 emailMessage.setFromEmail(defaultFromEmailAddressForProd);
		 emailMessage.setCreatedBy(interviewCreactorUser.getUserCode());
		 emailMessage.setEmailType(Constants.CUSTOM_USER_EMAIL);
		}
		 Map<String, String> globalVarsMap = new HashMap<String, String>();
		 
		if (interviewCreactorUser.getFullName() != null && !"".equalsIgnoreCase(interviewCreactorUser.getFullName())) {
			globalVarsMap.put("InterviewCreatorName", interviewCreactorUser.getFullName());
		} else {
			globalVarsMap.put("InterviewCreatorName", "--");
		}

		RequirementSubmissionTemplateFields interviewDetails = requirementSubmissionTemplateFieldsService
				.getSubmissionsTemplateFieldsByRequirementSubmissionsId(reqnterview.getSubmissionId().getId());
		if (null != interviewDetails.getFirstName() && !"".equalsIgnoreCase(interviewDetails.getFirstName())) {
			globalVarsMap.put("ApplicantName", interviewDetails.getFirstName());
		} else {
			globalVarsMap.put("ApplicantName", "--");
		}
		Requirement requirement = requirementService.getRequirementByRequirementId(reqSubmission.getRequirement().getId());

		if (requirement.getJobId() != null && !"".equalsIgnoreCase(requirement.getJobId())) {
			StringBuilder sbv = new StringBuilder();
			globalVarsMap.put("requirementName", sbv.append(requirement.getJobId()).append("-").append(requirement.getJobTitle()).toString());
		} else {
			globalVarsMap.put("requirementName", requirement.getJobTitle());
		}
		if (null != reqnterview.getDateOfInterview()) {
			String currentTimeStr = Constants.TZ_DF.format(reqnterview.getStartTime());
			DateFormat TZ_DF_TS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String interviewDateInTimezone = CommonUtil.convertTimeZones(Constants.UTC_TIMEZONE,
					reqnterview.getTimezone().getTimeZoneId(), currentTimeStr);
			Date interviewConverteDate = TZ_DF_TS.parse(interviewDateInTimezone);
			interviewDateInTimezone = LoginSessionController.DATEFORMAT.format(interviewConverteDate);
			reqnterview.setDateOfInterviewStr(interviewDateInTimezone);
			globalVarsMap.put("InterviewDate", reqnterview.getDateOfInterviewStr());
		} else {
			globalVarsMap.put("InterviewDate", "--");
		}
		StringBuilder sb = new StringBuilder();
		String interviewStartTime = null;
		String interviewEndTime = null;
		if (null != reqnterview.getStartTime() || null != reqnterview.getEndTime()) {
			if (null != reqnterview.getStartTime()) {
				DateFormat DF_DMY = new SimpleDateFormat("hh:mma");
				String currentTimeStr = Constants.TZ_DF.format(reqnterview.getStartTime());
				String startTimeUTC = CommonUtil.convertTimeZones(Constants.UTC_TIMEZONE,
						reqnterview.getTimezone().getTimeZoneId(), currentTimeStr);
				DateFormat TZ_DF_TS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date convertedDate = TZ_DF_TS.parse(startTimeUTC);
				String convertedDateStr = DF_DMY.format(convertedDate);
				interviewStartTime = convertedDateStr;
//				globalVarsMap.put("InterviewTime",sb.append(interviewStartTime).toString());
			}
			if (null != reqnterview.getEndTime()) {
				DateFormat DF_DMY = new SimpleDateFormat("hh:mma");
				String currentTimeStr = Constants.TZ_DF.format(reqnterview.getEndTime());
				String startTimeUTC = CommonUtil.convertTimeZones(Constants.UTC_TIMEZONE,
						reqnterview.getTimezone().getTimeZoneId(), currentTimeStr);
				DateFormat TZ_DF_TS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date convertedDate = TZ_DF_TS.parse(startTimeUTC);
				String convertedDateStr = DF_DMY.format(convertedDate);
				interviewEndTime = convertedDateStr;
//				globalVarsMap.put("InterviewTime",sb.append(interviewEndTime).toString());
			}

			if(null != reqnterview.getStartTime() && null != reqnterview.getEndTime()){
			globalVarsMap.put("InterviewTime",sb.append(interviewStartTime).append("-").append(interviewEndTime).toString());
			}
		} else if (null != reqnterview.getStartTime()) {
			globalVarsMap.put("InterviewTime", sb.append(interviewStartTime).toString());
		} else {
			globalVarsMap.put("InterviewTime", "--");
		}
		if (null != reqnterview.getInterviewType() && !"".equalsIgnoreCase(reqnterview.getInterviewType())) {
			globalVarsMap.put("InterviewType", reqnterview.getInterviewType());
		} else {
			globalVarsMap.put("InterviewType", "--");
		}
		if (null != reqnterview.getLocation() && !"".equalsIgnoreCase(reqnterview.getLocation())) {
			globalVarsMap.put("Details", reqnterview.getLocation());
		} else {
			globalVarsMap.put("Details", "--");
		}
		if(null != reqSubmission.getVendorLocationId()){
		if (null != spoc.getVendorUser()) {
			globalVarsMap.put("LoginorgUserName", spoc.getVendorUser().getFirstName());
		} else {
			globalVarsMap.put("Details", "--");
		}

		if (null != reqSubmission.getVendorLocationId()) {
			globalVarsMap.put("LoginorgUserLocation", reqSubmission.getVendorLocationId().getOrgLocationName());
		} else {
			globalVarsMap.put("Details", "--");
		}
		}
		 InternetAddress[] iAdressArray = InternetAddress.parse(emailMessage.getToEmail());
		 
		//getting the current EmailScenario by EmailScenario unique name and entity type
		 EmailScenario  emailScenario = null;
		 EmailScenario  notificationScenario = null;
		 if(reqnterview.getInterviewStatus().equalsIgnoreCase("Attending") && null != reqSubmission.getVendorLocationId()){
			 emailScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.VENDOR_APPLICANT_ACCEPT_MAILTO_INTERVIEWCREATOR,Constants.ENTITY_TYPE_EMAIL);
			 notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.VENDOR_APPLICANT_ACCEPT_MAILTO_INTERVIEWCREATOR,Constants.ENTITY_TYPE_NOTIFICATION);
		 }else if(reqnterview.getInterviewStatus().equalsIgnoreCase("Attending")){
			 emailScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.APPLICANT_ACCEPTMAILTO_INTERVIEW_CREATOR,Constants.ENTITY_TYPE_EMAIL);
			 notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.APPLICANT_ACCEPTMAILTO_INTERVIEW_CREATOR,Constants.ENTITY_TYPE_NOTIFICATION);
		 }
		 if(reqnterview.getInterviewStatus().equalsIgnoreCase("Not_Attending") && null != reqSubmission.getVendorLocationId()){
		    emailScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.VENDOR_APPLICANT_REJECTMAIL_TO_INTERVIEWCREATOR,Constants.ENTITY_TYPE_EMAIL);
		    notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.VENDOR_APPLICANT_REJECTMAIL_TO_INTERVIEWCREATOR,Constants.ENTITY_TYPE_NOTIFICATION);
		 }else if(reqnterview.getInterviewStatus().equalsIgnoreCase("Not_Attending")){
		    emailScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.APPLICANT_REJECT_MAILTO_INTERVIEW_CREATOR,Constants.ENTITY_TYPE_EMAIL);
		    notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.APPLICANT_REJECT_MAILTO_INTERVIEW_CREATOR,Constants.ENTITY_TYPE_NOTIFICATION);
		 }
		 if(reqnterview.getInterviewStatus().equalsIgnoreCase("Maybe") && null != reqSubmission.getVendorLocationId()){
		   emailScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.VENDOR_APPLICANT_TENTATIVE_MAILTO_INTERVIEWCREATOR,Constants.ENTITY_TYPE_EMAIL);
		   notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.VENDOR_APPLICANT_TENTATIVE_MAILTO_INTERVIEWCREATOR,Constants.ENTITY_TYPE_NOTIFICATION);
		 }else if(reqnterview.getInterviewStatus().equalsIgnoreCase("Maybe")){
		   emailScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.APPLICANT_TENTATIVE_MAILTO_INTERVIEWCREATOR,Constants.ENTITY_TYPE_EMAIL);
		   notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.APPLICANT_TENTATIVE_MAILTO_INTERVIEWCREATOR,Constants.ENTITY_TYPE_NOTIFICATION);
		 }
		 
		 
		 
		String	subject = emailScenerioUtil.bulidSubject(emailScenario.getSubject(), globalVarsMap);
		String	body = emailScenerioUtil.buildTempBody(emailScenario.getBody(), globalVarsMap);
		String	signature = emailScenerioUtil.buildSignature(emailScenario.getSignature(), globalVarsMap);
		String	url = sourceleadUrl;
			
		String notificationbody = emailScenerioUtil.buildBody(notificationScenario.getBody(),globalVarsMap);
		notificationScenario.setBody(notificationbody);
		 
		 //construct HTML template using required data
		 String emailTemplate = emailScenerioUtil.AssembleEmailMessage(emailScenario,emailMessage, subject, body, signature, url, reqSubmission.getOrgLocations().getOrg(),reqSubmission.getOrgLocations());
		 emailMessage.setSubject(subject);
		 emailMessage.setMessageBody(emailTemplate);
		 String actionLink= "";

		 if(null != reqSubmission.getVendorLocationId()){
		 emailScenerioUtil.sendNotification(actionLink,spoc.getVendorUser(), interviewCreactorUser, spoc.getClientLocationId(), notificationScenario);	 
		 }else{
		 User fromuser = userService.getUserById(Constants.USERID);
		 emailScenerioUtil.sendNotification(actionLink,fromuser,interviewCreactorUser, reqSubmission.getOrgLocations(), notificationScenario);
		 }
		 emailScenerioUtil.sendEmail(emailMessage,reqSubmission.getOrgLocations().getOrg().getId(),iAdressArray,new ArrayList<EmailAttachment>());
	
	}


	@Override
	public RequirementInterview getinterviewBySubmissionId(Long submissionId) {
		return requirementInterviewDao.getinterviewBySubmissionId(submissionId);
		
	}


	@Override
	public void applicantInterviewProposeTimeEmailToInterviewInitiator(String requirementInterviewId,OrgLocations orgLocations, Org loginOrg,User user) throws Exception {
		  RequirementInterview requirementinterview = requirementInterviewDao.getRequirementInterviewById(requirementInterviewId);
		  Candidate candidate = candidateService.getCandidateByCandidateId(requirementinterview.getSubmissionId().getCandidate().getId());
	      RequirementSubmission requirementSubmission = requirementSubmissionService.getRequirementSubmissionById(requirementinterview.getSubmissionId().getId());
	      List<RequirementInterviewer> requirementInterviewer=requirementInterviewerDao.getInterviwersListByInterviewId(requirementinterview.getId());
	      User intUser=userService.getUserByUserCode(requirementinterview.getCreatedBy()) ;
	      List<User> interviewrUserList = new ArrayList<>(); 
	      if(!requirementInterviewer.isEmpty() && requirementInterviewer.size() > 0){
	    	  for(RequirementInterviewer reqInterviewer : requirementInterviewer){
	    		  interviewrUserList.add(reqInterviewer.getInterviewerUser()); 
	    	  }
	      }else{
	    	 
	    	  interviewrUserList.add(intUser);
	      }
	      for(User interviewrUser : interviewrUserList){
//	    	  if(user.getId().longValue() != interviewrUser.getId().longValue()){
	    		  EmailMessage emailMessage = new EmailMessage();
	    			 OrgSpoc spoc= null;
	    				if(null != requirementSubmission.getVendorLocationId()){
	    					emailMessage.setFirstName(interviewrUser.getFirstName());
	    					 emailMessage.setLastName(interviewrUser.getLastName());
	    					 emailMessage.setToEmail(interviewrUser.getEmail());
	    					
	    					 if(null != requirementinterview.getSubmissionId().getSourceSubmission()){
	    						 RequirementSubmission reqSub =  requirementSubmissionService.getSubmissionsBySourceSubmissionId(requirementSubmission.getId(), requirementSubmission.getVendorLocationId().getId());
	    						Requirement req =  requirementService.getRequirementByRequirementId(reqSub.getSourceSubmission().getRequirement().getId());
	    						spoc = orgSpocService.getSpocBySpocUUID(req.getSpocId().getUUID());
	    					 }
	    					 emailMessage.setFromEmail(spoc.getVendorUser().getEmail());
	    					 emailMessage.setCreatedBy(interviewrUser.getUserCode());
	    					 emailMessage.setEmailType(Constants.CUSTOM_USER_EMAIL);
	    					 StringBuilder timeStringBuilder=new StringBuilder();
	    					 if(null != user.getLastName()){
	    						 emailMessage.setFromName(timeStringBuilder.append(user.getFirstName()).append(" ").append(user.getLastName()).toString());	 
	    					 }else{
	    						 emailMessage.setFromName(timeStringBuilder.append(user.getFirstName()).toString());
	    					 }
	    				}else{
	    					emailMessage.setFirstName(candidate.getFirstName());
	    					 emailMessage.setLastName(candidate.getLastName());
	    					 emailMessage.setToEmail(interviewrUser.getEmail());
	    					 emailMessage.setFromEmail(candidate.getEmail());
	    					 emailMessage.setCreatedBy(user.getUserCode());
	    					 emailMessage.setEmailType(Constants.CUSTOM_USER_EMAIL);
	    				}
	    			 
	    			 String time = "";
	    			 String startTime = "";
	    			 String endTime = "";
	    			 String dateOfInterviewStr ="";
	    			 
	    			 if(requirementinterview.getDateOfInterview() != null){

	    					String currentTimeStr = Constants.TZ_DF.format(requirementinterview.getStartTime());
	    					DateFormat TZ_DF_TS = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
	    					String interviewDateInTimezone= CommonUtil.convertTimeZones(Constants.UTC_TIMEZONE  ,requirementinterview.getTimezone().getTimeZoneId(), currentTimeStr);
	    					Date interviewConverteDate = TZ_DF_TS.parse(interviewDateInTimezone);
	    					interviewDateInTimezone = LoginSessionController.DATEFORMAT.format(interviewConverteDate);
	    					dateOfInterviewStr = interviewDateInTimezone;
	    			    }
	    			    
	    			    if(requirementinterview.getStartTime() != null){
	    			    	DateFormat DF_DMY = new SimpleDateFormat("hh:mma");
	    					String currentTimeStr = Constants.TZ_DF.format(requirementinterview.getStartTime());  
	    					String startTimeUTC= CommonUtil.convertTimeZones(Constants.UTC_TIMEZONE ,requirementinterview.getTimezone().getTimeZoneId(), currentTimeStr);
	    					DateFormat TZ_DF_TS = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
	    					Date convertedDate = TZ_DF_TS.parse(startTimeUTC);
	    					String convertedDateStr = DF_DMY.format(convertedDate);
	    					startTime = convertedDateStr;
	    				}
	    					 
	    			    if(requirementinterview.getEndTime() != null){
	    			    	DateFormat DF_DMY = new SimpleDateFormat("hh:mma");
	    					String currentTimeStr = Constants.TZ_DF.format(requirementinterview.getEndTime());  
	    					String startTimeUTC= CommonUtil.convertTimeZones(Constants.UTC_TIMEZONE ,requirementinterview.getTimezone().getTimeZoneId(), currentTimeStr);
	    					DateFormat TZ_DF_TS = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
	    					Date convertedDate = TZ_DF_TS.parse(startTimeUTC);
	    					String convertedDateStr = DF_DMY.format(convertedDate);  
	    					endTime = convertedDateStr;
	    			    }
	    			 
	    			 if(endTime!=null){
	    				 StringBuilder timeBuilder=new StringBuilder();
	    				  time = timeBuilder.append(startTime).append(" - ").append(endTime).append(" ("+requirementinterview.getTimezone().getTimeZoneId()+")").toString();
	    			 }
	    			 
	    			//setting the class filed values
	    			 Map<String, String> globalVarsMap = new HashMap<String, String>();
	    				 if(emailMessage.getLastName() != null){
	    					 globalVarsMap.put("applicantName", emailMessage.getFirstName() +" "+emailMessage.getLastName());
	    				 }else{
	    					 globalVarsMap.put("applicantName", emailMessage.getFirstName());
	    				 }
	    				 if(interviewrUser.getLastName() != null){
	    					 globalVarsMap.put("interviewerName", interviewrUser.getFirstName() +" "+interviewrUser.getLastName());	 
	    				 }else{
	    					 globalVarsMap.put("interviewerName", interviewrUser.getFirstName());
	    				 }
	    				 globalVarsMap.put("loginUser", emailMessage.getFromName());
	    				 globalVarsMap.put("requirementTitle", requirementSubmission.getRequirement().getJobTitle());
	    				 globalVarsMap.put("interviewDate", dateOfInterviewStr);
	    				 globalVarsMap.put("interviewTime",time);
	    				 StringBuilder  interviewTable  = buildProposeNewTimeListTable(requirementInterviewId);
	    				 globalVarsMap.put("proposeNewTimeList", interviewTable.toString());
	    				 
	    			 //setting the Recipient information
	    			 InternetAddress[] iAdressArray = InternetAddress.parse(emailMessage.getToEmail());
	    			
	    			 //getting the current EmailScenario by EmailScenario unique name and entity type
	    			 EmailScenario  emailScenario = null;
	    			 EmailScenario noificationEmailScenario =null;
	    			 if(null != requirementSubmission.getVendorLocationId()){
	    				 emailScenario =  emailScenarioService.getEmailScenarioByUniqueName(Constants.VENDOR_PROPOSE_NEW_TIME,Constants.ENTITY_TYPE_EMAIL);
	    					noificationEmailScenario =  emailScenarioService.getEmailScenarioByUniqueName(Constants.VENDOR_PROPOSE_NEW_TIME,Constants.ENTITY_TYPE_NOTIFICATION);
	    			 }else{
	    				emailScenario =  emailScenarioService.getEmailScenarioByUniqueName(Constants.PROPOSE_NEW_TIME,Constants.ENTITY_TYPE_EMAIL);
	    				noificationEmailScenario =  emailScenarioService.getEmailScenarioByUniqueName(Constants.PROPOSE_NEW_TIME,Constants.ENTITY_TYPE_NOTIFICATION);
	    			 }
	    			 //covert subject,body and signature
	    			 String subject = emailScenerioUtil.bulidSubject(emailScenario.getSubject(),globalVarsMap);
	    			 String body = emailScenerioUtil.buildTempBody(emailScenario.getBody(),globalVarsMap);
	    			 String signature = emailScenerioUtil.buildSignature(emailScenario.getSignature(),globalVarsMap);
	    			 String url = sourceleadUrl;
	    			 
	    			 //construct HTML template using required data
	    			 String emailTemplate = emailScenerioUtil.AssembleEmailMessage(emailScenario,emailMessage, subject, body, signature, url, orgLocations.getOrg(),orgLocations);
	    			 emailMessage.setSubject(subject);
	    			 emailMessage.setMessageBody(emailTemplate);
	    			 
	    			 //calling the sendEmail method of emailScenerioUtil
	    			 emailScenerioUtil.sendEmail(emailMessage,orgLocations.getId(),iAdressArray,new ArrayList<EmailAttachment>());
	    			
	    			  
	    			 String actionLink = "";
	    			 
	    			 String notificationbody = emailScenerioUtil.buildTempBody(noificationEmailScenario.getBody(),globalVarsMap);
	    			 noificationEmailScenario.setBody(notificationbody);
	    			 
	    			 if(null != requirementSubmission.getVendorLocationId()){
	    				 emailScenerioUtil.sendNotification(actionLink,spoc.getVendorUser(), interviewrUser, spoc.getClientLocationId(), noificationEmailScenario);	 
	    				 }else{
	    				 User fromuser = userService.getUserById(Constants.USERID);
	    				 emailScenerioUtil.sendNotification(actionLink,fromuser,interviewrUser, requirementSubmission.getOrgLocations(), noificationEmailScenario);
	    				 } 
	    	  }
//	      }
		  
		  
	}


	private StringBuilder buildProposeNewTimeListTable(String requirementInterviewId) throws Exception {
		List<ProposeTime> proposeTimeList =  proposeTimeService.getProposeTimeByInterViewId(Long.parseLong(requirementInterviewId));
		StringBuilder tempHTML = new StringBuilder();
		tempHTML.append("<table width='250px'>");
		tempHTML.append("<tbody>");
			for(ProposeTime pt:proposeTimeList){
				tempHTML.append("<tr>");
				tempHTML.append("<td>");
						tempHTML.append("<div>");
							tempHTML.append("<input type='text' style='width:125px; padding:3px 10px;'  value='"+LoginSessionController.DATEFORMAT.format(pt.getProposeDate())+"' readonly>");
						tempHTML.append("</div>");
			  tempHTML.append("</td>");
			
			  tempHTML.append("<td>");
					tempHTML.append("<div>");
						tempHTML.append("<input type='text' style='width:100px; padding:3px 10px;'  value='"+pt.getProposeTime()+"' readonly>");
					tempHTML.append("</div>");
			tempHTML.append("</td>");
			tempHTML.append("</tr>");
			}
			tempHTML.append("</tbody>");
		tempHTML.append("</table>");
		return tempHTML;
	}
	
	
	/*@Override
	public List<RequirementInterview> getAllInterviewListByCurrentDate(String currentTime, String triggerTime) {
		return requirementInterviewDao.getAllInterviewListByCurrentDate(currentTime,  triggerTime);
	}*/

}