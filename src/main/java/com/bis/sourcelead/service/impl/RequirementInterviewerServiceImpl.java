package com.bis.sourcelead.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.internet.InternetAddress;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.RequirementInterviewerDao;
import com.bis.sourcelead.dto.RequirementInterviewerDTO;
import com.bis.sourcelead.entity.EmailAttachment;
import com.bis.sourcelead.entity.EmailMessage;
import com.bis.sourcelead.entity.EmailScenario;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.Requirement;
import com.bis.sourcelead.entity.RequirementInterview;
import com.bis.sourcelead.entity.RequirementInterviewer;
import com.bis.sourcelead.entity.RequirementSubmission;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.EmailScenarioService;
import com.bis.sourcelead.service.RequirementInterviewService;
import com.bis.sourcelead.service.RequirementInterviewerService;
import com.bis.sourcelead.service.RequirementService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.EmailScenerioUtil;

@Service
public class RequirementInterviewerServiceImpl implements RequirementInterviewerService{
	

	@Autowired
	RequirementInterviewerDao requirementInterviewerDao;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private RequirementService requirementservice;
	
	@Autowired
	private EmailScenarioService emailScenarioService;
	
	@Autowired
	private EmailScenerioUtil emailScenerioUtil;
	
	@Autowired
	private RequirementInterviewService requirementInterviewService;
	
	@Value("${sourceleadUrl}")
    private String sourceleadUrl;
	
	@Autowired
	private CommonUtil commonUtil;
	
	@Override
	public List<RequirementInterviewer> getInterviwersListByInterviewId(Long requirementInterviewId) {
		return requirementInterviewerDao.getInterviwersListByInterviewId(requirementInterviewId);
	}
	
	@Override
	public RequirementInterviewer getInterviwerstByInterviewId(Long requirementInterviewerId) {
		return requirementInterviewerDao.getInterviwerstByInterviewId(requirementInterviewerId);
	}

	@Override
	public RequirementInterviewer getInterviwerByInterviewIdAndUserId(Long requirementInterviewerId, Long userId) {
		return requirementInterviewerDao.getInterviwerByInterviewIdAndUserId(requirementInterviewerId,userId);
	}

	@Override
	public List<RequirementInterviewer> getinterviewListByOrgLocationAndUserId(OrgLocations loginOrgLocations,List<Long> interviewerIds,Date startUTCDate, Date endUTCDate) {
		return requirementInterviewerDao.getinterviewListByOrgLocationAndUserId(loginOrgLocations,interviewerIds,startUTCDate,endUTCDate);
	}

	@Override
	public void updatedFeedbackMailFromInterviewerToRequirementOwner(RequirementInterviewer requirementInterviewer,RequirementSubmission requirementSubmission,JSONObject json,String templateId) throws Exception {
		
		User interviewerUser = null;
		if(requirementInterviewer.getInterviewerUser() != null) {
			interviewerUser = userService.getUserById(requirementInterviewer.getInterviewerUser().getId());
		}
		
		 Requirement requirement = requirementservice.getRequirementByRequirementId(requirementSubmission.getRequirement().getId());
		 RequirementInterview requirementInterview = requirementInterviewService.getinterviewBySubmissionId(requirementSubmission.getId());
		 if(requirementInterview!= null) {
			 User InterviewScheduler=userService.getUserByUserCode(requirementInterview.getCreatedBy());
		 requirementInterview.getCandidateName();
		 EmailMessage emailMessage = new EmailMessage();
		/* emailMessage.setFirstName(requirement.getCreatedBy().getFirstName());
		 emailMessage.setLastName(requirement.getCreatedBy().getLastName());
		 emailMessage.setToEmail(requirement.getCreatedBy().getEmail());*/
		 emailMessage.setFirstName(InterviewScheduler.getFirstName());
		 emailMessage.setLastName(InterviewScheduler.getLastName());
		 emailMessage.setToEmail(InterviewScheduler.getEmail());
		 
			if(interviewerUser != null) {
				 emailMessage.setFromEmail(interviewerUser.getEmail());
				 emailMessage.setCreatedBy(interviewerUser.getUserCode());
				 StringBuilder msgBuild = new StringBuilder();
				 emailMessage.setFromName(msgBuild.append(interviewerUser.getFirstName()).append(" ").append(interviewerUser.getLastName()).toString());
			}else {
				emailMessage.setFromEmail(requirementInterviewer.getExternalInterviewerEmail());
				emailMessage.setFromName(requirementInterviewer.getExternalInterviewerName());
			}

		 emailMessage.setEmailType(Constants.CUSTOM_USER_EMAIL);
		//setting the class filed values
		 Map<String, String> globalVarsMap = new HashMap<String, String>();
		 
		 if(requirement.getJobTitle()!= null && !"".equalsIgnoreCase(requirement.getJobTitle())){
			 globalVarsMap.put("requirementName", requirement.getJobTitle());
			 }else{
				 globalVarsMap.put("requirementName","--");
			 }
		 if(null != requirementInterview.getInterviewType() && !"".equalsIgnoreCase(requirementInterview.getInterviewType())){
			 globalVarsMap.put("interviewType", requirementInterview.getInterviewType());
			 }else{
				 globalVarsMap.put("interviewType","--");
			 }
		 
		 if(json.has("Selected for Interview") ){	
			 globalVarsMap.put("selectedYesOrNo",json.getString("Selected for Interview"));
			 }else{
				 globalVarsMap.put("selectedYesOrNo","--");
			 }
		 //should finish it
		 if(requirement.getManagerUserId()!= null){
			 User managerUser = userService.getUserById(requirement.getManagerUserId().getId());
			 globalVarsMap.put("managerName", managerUser.getFullName());
			 }else{
				 globalVarsMap.put("managerName","--");
			 }
		 if(requirementSubmission.getCandidate()!= null){
			 StringBuilder candidateName = new StringBuilder();
			 if(null != requirementSubmission.getCandidate().getLastName() && !"".equalsIgnoreCase(requirementSubmission.getCandidate().getLastName()))
			 globalVarsMap.put("applicantName",candidateName.append(requirementSubmission.getCandidate().getFirstName()).append(requirementSubmission.getCandidate().getLastName()).toString());
			 }else{
				 globalVarsMap.put("applicantName",requirementSubmission.getCandidate().getFirstName());
			 }
		
			 if(interviewerUser != null && interviewerUser.getFullName()!= null && !"".equalsIgnoreCase(interviewerUser.getFullName())){
				 globalVarsMap.put("interviewerName", interviewerUser.getFullName());
			 }else {
				 emailMessage.setFromName(requirementInterviewer.getExternalInterviewerName());
			 }
		 
			 StringBuilder feedbackBuilder = new StringBuilder();
			 globalVarsMap.put("feedBackLink", feedbackBuilder.append("<a href='").append(sourceleadUrl).append("/publicInterviewFeedBackTemplate?templateId=").append(templateId).append("&requirementInterviewid=").append(requirementInterviewer.getUuid()).append("'style='text-decoration:none; '><div style=' width:100px; border-radius:30px; background-color:#D33B39; color:#FFF; font-size:15px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Feedback</div></a>").toString());
			
			
		 //getting the current EmailScenario by EmailScenario unique name and entity type
		 EmailScenario  notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.UPDATED_FEEDBACK_NOTIFICATIONTO_REQUIREMENTOWNER,Constants.ENTITY_TYPE_NOTIFICATION);

		 
		 String notificationbody = emailScenerioUtil.buildTemplateBody(notificationScenario.getBody(),globalVarsMap,notificationScenario);
		 notificationScenario.setBody(notificationbody);
		 
		OrgLocations OrgLoc = requirement.getOrgLocationId();

		 //calling the sendEmail method of emailScenerioUtil
		 commonUtil.sendTemplateEmail(emailMessage, globalVarsMap,OrgLoc.getOrg(),OrgLoc,Constants.UPDATED_FEEDBACK_NOTIFICATIONTO_REQUIREMENTOWNER,new ArrayList<EmailAttachment>());
		 
		 StringBuilder actionLinkBuilder = new StringBuilder();
		 String actionLink = actionLinkBuilder.append("").append(sourceleadUrl).append("/").append("requirementSummary?requirementUUID="+requirement.getRequirementUUID()+"&submissionId="+requirementSubmission.getId()+"&candidateUUID="+requirementSubmission.getCandidate().getCandidateUUID()).toString();		 

		if(interviewerUser != null) {
			emailScenerioUtil.sendNotification(actionLink, InterviewScheduler, requirement.getCreatedBy(), OrgLoc, notificationScenario,new HashMap<String,String>());
		}
		 
		}
		 
		 //send email to point of contact
		 User spocUser = null;
		 if(requirementSubmission.getSourceSubmission() != null) {
			  spocUser = requirementSubmission.getSourceSubmission().getRequirement().getSpocId().getVendorUser();
		 }
		
		 if(requirementInterview != null && spocUser != null) {
		 requirementInterview.getCandidateName();
		 EmailMessage emailMessage = new EmailMessage();
		 emailMessage.setFirstName(requirement.getCreatedBy().getFirstName());
		 emailMessage.setLastName(requirement.getCreatedBy().getLastName());
		 emailMessage.setToEmail(requirement.getCreatedBy().getEmail());
		 emailMessage.setFromEmail(spocUser.getEmail());
		 emailMessage.setCreatedBy(spocUser.getUserCode());
		 StringBuilder msgBuild = new StringBuilder();
		 emailMessage.setFromName(msgBuild.append(spocUser.getFirstName()).append(" ").append(spocUser.getLastName()).toString());
		 emailMessage.setEmailType(Constants.CUSTOM_USER_EMAIL);
		//setting the class filed values
		 Map<String, String> globalVarsMap = new HashMap<String, String>();
		 
		 if(requirement.getJobTitle()!= null && !"".equalsIgnoreCase(requirement.getJobTitle())){
			 globalVarsMap.put("requirementName", requirement.getJobTitle());
			 }else{
				 globalVarsMap.put("requirementName","--");
			 }
		 if(null != requirementInterview.getInterviewType() && !"".equalsIgnoreCase(requirementInterview.getInterviewType())){
			 globalVarsMap.put("interviewType", requirementInterview.getInterviewType());
			 }else{
				 globalVarsMap.put("interviewType","--");
			 }
		 
		 if(json.has("Selected for Interview") ){	
			 globalVarsMap.put("selectedYesOrNo",json.getString("Selected for Interview"));
			 }else{
				 globalVarsMap.put("selectedYesOrNo","--");
			 }
		 //should finish it
		 if(requirement.getManagerUserId()!= null){
			 User managerUser = userService.getUserById(requirement.getManagerUserId().getId());
			 globalVarsMap.put("managerName", managerUser.getFullName());
			 }else{
				 globalVarsMap.put("managerName","--");
			 }
		 if(requirementSubmission.getCandidate()!= null){
			 StringBuilder candidateName = new StringBuilder();
			 if(null != requirementSubmission.getCandidate().getLastName() && !"".equalsIgnoreCase(requirementSubmission.getCandidate().getLastName()))
			 globalVarsMap.put("applicantName",candidateName.append(requirementSubmission.getCandidate().getFirstName()).append(requirementSubmission.getCandidate().getLastName()).toString());
			 }else{
				 globalVarsMap.put("applicantName",requirementSubmission.getCandidate().getFirstName());
			 }
		 if(interviewerUser.getFullName()!= null && !"".equalsIgnoreCase(interviewerUser.getFullName())){
			 globalVarsMap.put("interviewerName", interviewerUser.getFullName());
			 }else{
				 globalVarsMap.put("interviewerName","--");
			 }
		 
			 StringBuilder feedbackBuilder = new StringBuilder();
			 globalVarsMap.put("feedBackLink", feedbackBuilder.append("<a href='").append(sourceleadUrl).append("/publicInterviewFeedBackTemplate?templateId=").append(templateId).append("&requirementInterviewid=").append(requirementInterviewer.getId()).append("&interviewerUser=").append(interviewerUser.getId()).append("'style='text-decoration:none; '><div style=' width:100px; border-radius:30px; background-color:#D33B39; color:#FFF; font-size:15px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Feedback</div></a>").toString());
			
			
		 //getting the current EmailScenario by EmailScenario unique name and entity type
		 EmailScenario  notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.UPDATED_FEEDBACK_NOTIFICATIONTO_REQUIREMENTOWNER,Constants.ENTITY_TYPE_NOTIFICATION);

		 
		 String notificationbody = emailScenerioUtil.buildTemplateBody(notificationScenario.getBody(),globalVarsMap,notificationScenario);
		 notificationScenario.setBody(notificationbody);

		 OrgLocations OrgLoc = requirement.getOrgLocationId();
		 
		 
		 //calling the sendEmail method of emailScenerioUtil
		 commonUtil.sendTemplateEmail(emailMessage, globalVarsMap,requirementSubmission.getOrgLocations().getOrg(),requirementSubmission.getOrgLocations(),Constants.UPDATED_FEEDBACK_NOTIFICATIONTO_REQUIREMENTOWNER,new ArrayList<EmailAttachment>());
		 String actionLink="";
		 emailScenerioUtil.sendNotification(actionLink, spocUser, requirement.getCreatedBy(), OrgLoc, notificationScenario,new HashMap<String,String>());
		}
	}
	@Override
	public Boolean isInterviwerForByRequirementIdInterviewIdAndUserId(String reqUUID, Long requirementInterviewerId, Long userId)
	{
		return requirementInterviewerDao.isInterviwerForByRequirementIdInterviewIdAndUserId(reqUUID, requirementInterviewerId, userId);
		
	}

	@Override
	public List<RequirementInterviewerDTO> getInterviwersListByInterviewIdFromDTO(Long interviewId) {
		return requirementInterviewerDao.getInterviwersListByInterviewIdFromDTO(interviewId);
	}

	@Override
	public RequirementInterviewer getRequirementInterviewerByUUID(String uuid) throws Exception {
		return requirementInterviewerDao.getRequirementInterviewerByUUID(uuid);
	}

	@Override
	public List<RequirementInterviewerDTO> getInterviwersListByInterviewIdAndSubmissionIdFromDTO(Long interviewId,
			Long submissionId) {
		return requirementInterviewerDao.getInterviwersListByInterviewIdAndSubmissionIdFromDTO(interviewId, submissionId);
	}

	@Override
	public RequirementInterviewer getRequirementInterviewerByInterviewIdAndExternalInterviewerEmail(
			Long requirementInterviewId, String email) throws Exception {
		return requirementInterviewerDao.getRequirementInterviewerByInterviewIdAndExternalInterviewerEmail(requirementInterviewId, email);
	}

	@Override
	public List<RequirementInterviewer> getInterviwersListByInterviewIdAndUserID(Long requirementInterviewId,Long userId) throws Exception {
		return requirementInterviewerDao.getInterviwersListByInterviewIdAndUserID(requirementInterviewId, userId);
	}

}
