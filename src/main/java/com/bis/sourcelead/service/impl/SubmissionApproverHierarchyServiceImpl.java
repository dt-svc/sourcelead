package com.bis.sourcelead.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.internet.InternetAddress;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.EmployeesDao;
import com.bis.sourcelead.dao.OrgLocationsDao;
import com.bis.sourcelead.dao.ProjectApproverHierarchyDao;
import com.bis.sourcelead.dao.ProjectMembersDao;
import com.bis.sourcelead.dao.SubmissionApproverHierarchyDao;
import com.bis.sourcelead.dao.UserDao;
import com.bis.sourcelead.entity.EmailAttachment;
import com.bis.sourcelead.entity.EmailMessage;
import com.bis.sourcelead.entity.EmailScenario;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.ProjectApproverHierarchy;
import com.bis.sourcelead.entity.ProjectMembers;
import com.bis.sourcelead.entity.SubmissionApproverHierarchy;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.EmailScenarioService;
import com.bis.sourcelead.service.SubmissionApproverHierarchyService;
import com.bis.sourcelead.service.TimesheetSubmissionsService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.EmailScenerioUtil;

@Service
public class SubmissionApproverHierarchyServiceImpl implements SubmissionApproverHierarchyService {

	@Autowired
	SubmissionApproverHierarchyDao submissionApproverHierarchyDao;
	
	@Autowired
    ProjectApproverHierarchyDao projectApproverHierarchyDao;
	
	@Autowired
	TimesheetSubmissionsService timesheetSubmissionsService;
	
	@Autowired
	private CommonUtil commonUtil;
	
	@Autowired
    EmployeesDao  employeesDao;
	
	@Autowired
	OrgLocationsDao orgLocationsDao;
	
	@Autowired
	EmailScenerioUtil emailScenerioUtil;
	
	@Autowired
	EmailScenarioService emailScenarioService;
	
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	@Autowired
	ProjectMembersDao projectMembersDao;
	
	
	@Override
	public SubmissionApproverHierarchy addSubmissionApproverHierarchy(
			SubmissionApproverHierarchy submissionApproverHierarchy) throws Exception {
		submissionApproverHierarchy.setUpdatedDate(commonUtil.currentDate());
		return submissionApproverHierarchyDao.addSubmissionApproverHierarchy(submissionApproverHierarchy);
	}

	@Override
	public SubmissionApproverHierarchy updateSubmissionApproverHierarchy(
			SubmissionApproverHierarchy submissionApproverHierarchy) throws Exception {
		submissionApproverHierarchy.setUpdatedDate(commonUtil.currentDate());
		return submissionApproverHierarchyDao.updateSubmissionApproverHierarchy(submissionApproverHierarchy);
	}

	@Override
	public SubmissionApproverHierarchy getSubmissionApproverHierarchy(String id) throws Exception {
		return submissionApproverHierarchyDao.getSubmissionApproverHierarchy(id);
	}

	@Override
	public Boolean deleteSubmissionApproverHierarchy(String id) throws Exception {
		return submissionApproverHierarchyDao.deleteSubmissionApproverHierarchy(id);
	}

	@Override
	public List<SubmissionApproverHierarchy> getAllSubmissionApproverHierarchies() throws Exception {
		return submissionApproverHierarchyDao.getAllSubmissionApproverHierarchies();
	}

	@Override
	public SubmissionApproverHierarchy getSubmissionApproverHierarchyForEmployee(Long submissionId) {
		return submissionApproverHierarchyDao.getSubmissionApproverHierarchyForEmployee(submissionId);
	}
	
	@Override
	public SubmissionApproverHierarchy getSubmissionLowLevelApproverHierarchyForEmployee(Long submissionId) {
		return submissionApproverHierarchyDao.getSubmissionLowLevelApproverHierarchyForEmployee(submissionId);
	}
	
	
	@Override
	public List<SubmissionApproverHierarchy>getSubmissionApproverHierarchyBySubmissionId(Long submissionId) {
		return submissionApproverHierarchyDao.getSubmissionApproverHierarchyBySubmissionId(submissionId);
	}
	
	

	@Override
	public SubmissionApproverHierarchy getSubmissionApproverHierarchyForApprover(Long submissionId,
			Long approverUserId) {
		return submissionApproverHierarchyDao.getSubmissionApproverHierarchyForApprover(submissionId, approverUserId);
	}

	@Override
	public List<SubmissionApproverHierarchy> getSubmissionApproverHierarchyForEmployeeIdAndStartDate(Long empId,
			Long approverUserId,Long submissionId) {
		return submissionApproverHierarchyDao.getSubmissionApproverHierarchyForEmployeeIdAndStartDate(empId, approverUserId, submissionId);
	}

	@Override
	public void updateSubmissionStatusByProjectMemberIdStartDateAndEndDate(Long projectMemberId,Date startDate,Date endDate,String status) {
		submissionApproverHierarchyDao.updateSubmissionStatusByProjectMemberIdStartDateAndEndDate(projectMemberId, startDate, endDate, status);
		
	}
	
	@Override
	public void updateSubmissionStatusByProjectIdMasterCandidateIdStartDateAndEndDate(Long projectId,Long masterCandidateId,Date startDate,Date endDate,String status) {
		submissionApproverHierarchyDao.updateSubmissionStatusByProjectIdMasterCandidateIdStartDateAndEndDate(projectId, masterCandidateId,startDate, endDate, status);
		
	}
	
	@Override
	public User updateSubmissionStatusByProjectMemberIdStartDateAndEndDateWithReOpenReason(Long projectMemberId,Date startDate,Date endDate,String reason, User loginUser, String type, String isSuperUser) throws Exception {
		User reopendUser = submissionApproverHierarchyDao.updateSubmissionStatusByProjectMemberIdStartDateAndEndDateWithReOpenReason(projectMemberId, startDate, endDate,reason,loginUser,type,isSuperUser);
		return reopendUser;
	}
	
	@Override
	public void sendReopenTimeSheetMail(Long projectMemberId,String reason, User loginUser, OrgLocations orgLocations, String timesheetFrequency, Float totalHours) throws Exception {
		   ProjectMembers clientProjectMember = projectMembersDao.getClientProjectMemberByProjectMemberId(projectMemberId);
		   ProjectApproverHierarchy pah =  projectApproverHierarchyDao.getApproverUsersByProjectIdAndProjectClientId(clientProjectMember.getProjects().getId(), clientProjectMember.getProjectClientsId().getId());
			 if(null != pah && null != pah.getApproverUserId()){
				 EmailMessage emailMessage = new EmailMessage();
				 emailMessage.setFirstName(pah.getApproverUserId().getFirstName());
				 emailMessage.setLastName(pah.getApproverUserId().getLastName());
				 emailMessage.setToEmail(pah.getApproverUserId().getEmail());
				 
				 emailMessage.setFromEmail(loginUser.getEmail());
				 emailMessage.setCreatedBy(loginUser.getUserCode());
				 emailMessage.setFromName(loginUser.getFullName());
				 
				 emailMessage.setEmailType(Constants.CUSTOM_USER_EMAIL);
				 
				 //ProjectMembers projectMembers = projectMembersDao.getConsultantUserProjectMemberByProjectMemberId(projectMemberId);
				 
				 String[] frequency = timesheetFrequency.split("To");
			 	 String weekStartDate = frequency[0].trim();
			  	 String weekEndDate = frequency[1].trim();
				 
				//setting the class filed values
				 Map<String, String> globalVarsMap = new HashMap<String, String>();
				 globalVarsMap.put("superUserName",pah.getApproverUserId().getFullName());
				 //globalVarsMap.put("employeeName",projectMembers.getEmployee().getUser().getFullName());
				 globalVarsMap.put("managerName",loginUser.getFullName());
				// globalVarsMap.put("projectName", projectMembers.getProjects().getProjectName());
				 globalVarsMap.put("frequencyType",clientProjectMember.getProjects().getInvoiceFrequency());
				 StringBuilder emailMsgBuilder = new StringBuilder();
				 globalVarsMap.put("frequencyPeriod",emailMsgBuilder.append(weekStartDate).append(" TO ").append(weekEndDate).toString());
				 globalVarsMap.put("totalHours",totalHours.toString());
				 globalVarsMap.put("reason",reason);
				 
				 
				 
				 
				 
				 if(clientProjectMember.getProjectMemberCandidteId() !=null && !"".equalsIgnoreCase(clientProjectMember.getProjectMemberCandidteId().getFullName())){
					 StringBuilder builder=new StringBuilder();
					 globalVarsMap.put("employeeName",clientProjectMember.getProjectMemberCandidteId().getFullName());
				 }
				 globalVarsMap.put("projectName", clientProjectMember.getProjects().getProjectName());
				
				
				 
				 StringBuilder acceptBuilder = new StringBuilder();
				 StringBuilder rejectBuilder = new StringBuilder();
				 globalVarsMap.put("acceptURL",acceptBuilder.append("<a href='").append(sourceleadUrl).append("/acceptOrRejectReopenTimeSheet?type=accept&projectMemberId=").append(projectMemberId).append("&userId=").append(pah.getApproverUserId().getId()).append("&timesheetFrequency=").append(timesheetFrequency).append("'style='text-decoration:none; display:inline-block; margin-left:10px; margin-top:20px;'><div style=' width:170px; border-radius:30px; background-color:#5cb85c; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Accept</div></a>").toString());
				 globalVarsMap.put("rejectURL",rejectBuilder.append("<a href='").append(sourceleadUrl).append("/acceptOrRejectReopenTimeSheet?type=reject&projectMemberId=").append(projectMemberId).append("&userId=").append(pah.getApproverUserId().getId()).append("&timesheetFrequency=").append(timesheetFrequency).append("'style='text-decoration:none; display:inline-block; margin-left:15px; margin-top:20px;'><div style=' width:170px; border-radius:30px; background-color:#e74c51; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Reject</div></a>").toString());
				 StringBuilder stringBuildforLink = new StringBuilder();
				 
				globalVarsMap.put("loginLink",stringBuildforLink.append("<a href='").append(sourceleadUrl).append("/").append( "'style='text-decoration:none; display:inline-block; margin-left:15px; margin-top:20px;'><div style=' width:170px; border-radius:30px; background-color:#214b90; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Login</div></a>").toString());
				 //setting the Recipient information
				 InternetAddress[] iAdressArray = InternetAddress.parse(emailMessage.getToEmail());
				 
				 //getting the current EmailScenario by EmailScenario unique name and entity type
				 EmailScenario  emailScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.MAIL_REOPEN_TIMESHEET,Constants.ENTITY_TYPE_EMAIL);
				
				 //covert subject,body and signature
				 String subject = emailScenerioUtil.bulidSubject(emailScenario.getSubject(),globalVarsMap);
				 String body = emailScenerioUtil.buildTempBody(emailScenario.getBody(),globalVarsMap);
				 String signature = emailScenerioUtil.buildSignature(emailScenario.getSignature(),globalVarsMap);
				 String url = sourceleadUrl;;
				 
				 //construct HTML template using required data
				 String emailTemplate = emailScenerioUtil.AssembleEmailMessage(emailScenario,emailMessage, subject, body, signature, url, orgLocations.getOrg(),orgLocations);
				 emailMessage.setSubject(subject);
				 emailMessage.setMessageBody(emailTemplate);
				 
				 //calling the sendEmail method of emailScenerioUtil
				 emailScenerioUtil.sendEmail(emailMessage,orgLocations.getId(),iAdressArray,new ArrayList<EmailAttachment>());
				 
			
				EmailScenario  notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.MAIL_REOPEN_TIMESHEET,Constants.ENTITY_TYPE_NOTIFICATION);
				String notificationBody = emailScenerioUtil.buildBody(notificationScenario.getBody(),globalVarsMap);
				notificationScenario.setBody(notificationBody);
				String actionLink = "";
				emailScenerioUtil.sendNotification(actionLink, loginUser, pah.getApproverUserId(), orgLocations, notificationScenario);
			 }
	}
	
	@Override
	public void sendReopenTimeSheetAcceptOrRejectMail(Long projectMemberId,String reason, User loginUser, OrgLocations orgLocations, String timesheetFrequency,String type, User reopenedUser) throws Exception {
		
				 EmailScenario emailScenario = null;
				 EmailScenario  notificationScenario = null;
				 EmailMessage emailMessage = new EmailMessage();
				 emailMessage.setFirstName(reopenedUser.getFirstName());
				 emailMessage.setLastName(reopenedUser.getLastName());
				 emailMessage.setToEmail(reopenedUser.getEmail());
				 
				 emailMessage.setFromEmail(loginUser.getEmail());
				 emailMessage.setCreatedBy(loginUser.getUserCode());
				 emailMessage.setFromName(loginUser.getFullName());
				 
				 
				 emailMessage.setEmailType(Constants.CUSTOM_USER_EMAIL);
				 
				 ProjectMembers projectMembers = projectMembersDao.getConsultantUserProjectMemberByProjectMemberId(projectMemberId);
				 
				//setting the class filed values
				 Map<String, String> globalVarsMap = new HashMap<String, String>();
				 globalVarsMap.put("superUserName",loginUser.getFullName());
				// globalVarsMap.put("employeeName",projectMembers.getEmployee().getUser().getFullName());
				 globalVarsMap.put("managerName",reopenedUser.getFullName());
				// globalVarsMap.put("projectName", projectMembers.getProjects().getProjectName());
				 globalVarsMap.put("reason",reason);
				 
				 globalVarsMap.put("loginURL","<a href='"+sourceleadUrl+"/"+"' style='text-decoration:none; '><div style=' width:220px; border-radius:30px; background-color:#214b90; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Login</div></a>");
			
				 

				 if(projectMembers.getProjectMemberCandidteId() !=null && projectMembers.getProjectMemberCandidteId().getConsultantUser() != null && !"".equalsIgnoreCase(projectMembers.getProjectMemberCandidteId().getConsultantUser().getFullName())){
					 
					 globalVarsMap.put("employeeName",projectMembers.getProjectMemberCandidteId().getConsultantUser().getFullName());
				 }
				 if( projectMembers.getProjects().getProjectSequeneNumber() !=null && !"".equalsIgnoreCase( projectMembers.getProjects().getProjectSequeneNumber())){
					 StringBuilder builder=new StringBuilder();
				 globalVarsMap.put("projectName",builder.append(projectMembers.getProjects().getProjectSequeneNumber()).append("-").append(projectMembers.getProjects().getProjectName()).toString());
				 
				 }
				 
				 else{
					 
					 globalVarsMap.put("projectName", projectMembers.getProjects().getProjectName());
				 }
				 
				 
				 
				 //setting the Recipient information
				 InternetAddress[] iAdressArray = InternetAddress.parse(emailMessage.getToEmail());
				 
				 //getting the current EmailScenario by EmailScenario unique name and entity type
				 if(null != type && "accept".equalsIgnoreCase(type)){
					 emailScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.MAIL_ACCEPT_REOPEN_TIMESHEET,Constants.ENTITY_TYPE_EMAIL);
					 notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.MAIL_ACCEPT_REOPEN_TIMESHEET,Constants.ENTITY_TYPE_NOTIFICATION);
				 }if(null != type && "reject".equalsIgnoreCase(type)){
					 emailScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.MAIL_REJECT_REOPEN_TIMESHEET,Constants.ENTITY_TYPE_EMAIL);
					 notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.MAIL_REJECT_REOPEN_TIMESHEET,Constants.ENTITY_TYPE_NOTIFICATION); 
				 }
				 
				
				 //covert subject,body and signature
				 String subject = emailScenerioUtil.bulidSubject(emailScenario.getSubject(),globalVarsMap);
				 String body = emailScenerioUtil.buildTempBody(emailScenario.getBody(),globalVarsMap);
				 String signature = emailScenerioUtil.buildSignature(emailScenario.getSignature(),globalVarsMap);
				 String url = sourceleadUrl;;
				 
				 //construct HTML template using required data
				 String emailTemplate = emailScenerioUtil.AssembleEmailMessage(emailScenario,emailMessage, subject, body, signature, url, orgLocations.getOrg(),orgLocations);
				 emailMessage.setSubject(subject);
				 emailMessage.setMessageBody(emailTemplate);
				 
				 //calling the sendEmail method of emailScenerioUtil
				 emailScenerioUtil.sendEmail(emailMessage,orgLocations.getId(),iAdressArray,new ArrayList<EmailAttachment>());
				
				//calling the sendNotification method of emailScenerioUtil
				String notificationBody = emailScenerioUtil.buildBody(notificationScenario.getBody(),globalVarsMap);
				notificationScenario.setBody(notificationBody);
				String actionLink = "";
				emailScenerioUtil.sendNotification(actionLink, loginUser, reopenedUser, orgLocations, notificationScenario);
	}

	@Override
	public SubmissionApproverHierarchy isThereAnyApproverChangesStatusForTheSubmission(Long submissionId,String approvalStatus) {
		return submissionApproverHierarchyDao.isThereAnyApproverChangesStatusForTheSubmission(submissionId,approvalStatus);
	}
	
	
	@Override
    public SubmissionApproverHierarchy isThereAnyApproverChangesStatusForProjectMember(Long projectMemberId,Date frequencyStartDate,Date frequencyEndDate,String approvalStatus) {
		return submissionApproverHierarchyDao.isThereAnyApproverChangesStatusForProjectMember(projectMemberId,frequencyStartDate,frequencyEndDate,approvalStatus);
	}
	
	@Override
    public SubmissionApproverHierarchy isThereAnyApproverChangesStatusForProjectAndMasterCandidateId(Long projectId,Long masterCandidateId,Date frequencyStartDate,Date frequencyEndDate,String approvalStatus) {
		return submissionApproverHierarchyDao.isThereAnyApproverChangesStatusForProjectAndMasterCandidateId(projectId,masterCandidateId,frequencyStartDate,frequencyEndDate,approvalStatus);
	}
	
	

	@Override
	public Float getTotalApprovedOrRejectedHoursByuserIdAndProjectIdForApprover(Long userId, Long projectId,
			String approvalStatus) {
		return submissionApproverHierarchyDao.getTotalApprovedOrRejectedHoursByuserIdAndProjectIdForApprover(userId, projectId, approvalStatus);
	}

	@Override
	public Float getTotalDraftAndSubmittedHoursByuserIdAndProjectIdForApprover(Long userId, Long projectId,
			String submissionStatus) {
		return submissionApproverHierarchyDao.getTotalDraftAndSubmittedHoursByuserIdAndProjectIdForApprover(userId, projectId, submissionStatus);
	}

	@Override
	public Float getTotalApprovedOrRejectedHoursByMasterCandidateIdAndProjectIdForEmployee(Long masterCandidateId, Long projectId,
			String approvalStatus) {
		return submissionApproverHierarchyDao.getTotalApprovedOrRejectedHoursByMasterCandidateIdAndProjectIdForEmployee(masterCandidateId, projectId, approvalStatus);
	}

	@Override
	public Float getTotalDraftAndSubmittedHoursByMasterCandidateIdAndProjectIdForEmployee(Long masterCandidateId, Long projectId,
			String submissionStatus) {
		return submissionApproverHierarchyDao.getTotalDraftAndSubmittedHoursByMasterCandidateIdAndProjectIdForEmployee(masterCandidateId, projectId, submissionStatus);
	}

	@Override
	public List<SubmissionApproverHierarchy> getTotalApprovedOrRejectedListByMasterCandidateIdForEmployeeLogin(Long userId, String submissionStatus) {
		return submissionApproverHierarchyDao.getTotalApprovedOrRejectedListByMasterCandidateIdForEmployeeLogin(userId,submissionStatus);
	}

	@Override
	public List<SubmissionApproverHierarchy> getTotalDraftAndSubmittedListByMasterCandidateIdForEmployeeLogin(Long userId, String submissionStatus) {
		return submissionApproverHierarchyDao.getTotalDraftAndSubmittedListByMasterCandidateIdForEmployeeLogin(userId,submissionStatus);

	}

	@Override
	public List<SubmissionApproverHierarchy> getTotalApprovedOrRejectedListByuserIdAndOrgLocationIdForApproverLogin(
			Long userId, Long orgLocationId, String submissionStatus) {
		return submissionApproverHierarchyDao.getSubmissionApproverHierarchyListByuserIdAndOrgLocationIdForApproverLogin(userId,orgLocationId,submissionStatus);

	}

	@Override
	public List<SubmissionApproverHierarchy> getTotalDraftAndSubmittedListByuserIdAndOrgLocationIdForApproverLogin(
			Long userId, Long orgLocationId, String submissionStatus) {
		
		return submissionApproverHierarchyDao.getSubmissionApproverHierarchyListByuserIdAndOrgLocationIdForApproverLogin(userId,orgLocationId,submissionStatus);

	}

	@Override
	public List<SubmissionApproverHierarchy> getTotalTimesheetSubmissionListByUserIdAndOrgLocationIdAndStatusBasedOnUserRoleLogin(
			Long userId, Long orgLocationId, String status,String userRole) throws Exception {
		List<SubmissionApproverHierarchy> list = new ArrayList<SubmissionApproverHierarchy>();
		
         if(status != null){
 			//if("ROLE_ADMIN".equalsIgnoreCase(userRole) || "ROLE_APPROVER".equalsIgnoreCase(userRole)   ||  "ROLE_TEAM_LEAD".equalsIgnoreCase(userRole)){
 				/*
 				 *  New  Timesheet List  as  per new User Story 
 				 */
 				 if("APPROVED".equalsIgnoreCase(status)){
     				list = submissionApproverHierarchyDao.getSubmissionApproverHierarchyListByuserIdAndOrgLocationIdForApproverLogin(userId, orgLocationId,  status);
  				}
                 if("REJECTED".equalsIgnoreCase(status)){
     				list = submissionApproverHierarchyDao.getSubmissionApproverHierarchyListByuserIdAndOrgLocationIdForApproverLogin(userId, orgLocationId, status);
  				}
                 if("SUBMITTED".equalsIgnoreCase(status)){
     				list = submissionApproverHierarchyDao.getSubmissionApproverHierarchyListByuserIdAndOrgLocationIdForApproverLogin(userId, orgLocationId, status);
  				}
                 if("DRAFT".equalsIgnoreCase(status)){
     				list = submissionApproverHierarchyDao.getSubmissionApproverHierarchyListByuserIdAndOrgLocationIdForApproverLogin(userId, orgLocationId, status);
  				}
 				
                 /*}else if("ROLE_USER".equalsIgnoreCase(userRole)  ){
 				
 				
 				if("APPROVED".equalsIgnoreCase(status)){
    				list = submissionApproverHierarchyDao.getTotalApprovedOrRejectedListByuserIdAndOrgLocationIdForEmployeeLogin(userId, orgLocationId,  status);
 				}
                if("REJECTED".equalsIgnoreCase(status)){
    				list = submissionApproverHierarchyDao.getTotalApprovedOrRejectedListByuserIdAndOrgLocationIdForEmployeeLogin(userId, orgLocationId, status);
 				}
                if("SUBMITTED".equalsIgnoreCase(status)){
    				list = submissionApproverHierarchyDao.getTotalDraftAndSubmittedListByuserIdAndOrgLocationIdForEmployeeLogin(userId, orgLocationId, status);
 				}
                if("DRAFT".equalsIgnoreCase(status)){
    				list = submissionApproverHierarchyDao.getTotalDraftAndSubmittedListByuserIdAndOrgLocationIdForEmployeeLogin(userId, orgLocationId, status);
 				}

 				
 			}*/

 		}
     	
         
         return list;
	}

	@Override
	public List<SubmissionApproverHierarchy> getSubmissionApproverHierarchyByApproverUserId(Long approverUserId) {
		return submissionApproverHierarchyDao.getSubmissionApproverHierarchyByApproverUserId(approverUserId);
	}

	@Override
	public void saveAllSubmissionApproverHierarchy(List<SubmissionApproverHierarchy> submissionApproverHierarchyList)
			throws Exception {
		submissionApproverHierarchyDao.saveAllSubmissionApproverHierarchy(submissionApproverHierarchyList);
		
	}

	@Override
	public SubmissionApproverHierarchy getSubmissionApproverHierarchyForEmployeeByProjectMemberId(
			Date frequencyStartDate, Date frequencyEndDate, Long projectMemberId) {
		return submissionApproverHierarchyDao.getSubmissionApproverHierarchyForEmployeeByProjectMemberId(frequencyStartDate, frequencyEndDate, projectMemberId);
	}
	
	@Override
	public SubmissionApproverHierarchy getSubmissionApproverHierarchyForEmployeeByProjectIdAndMasterCandidateId(
			Date frequencyStartDate, Date frequencyEndDate, Long projectId,Long masterCandidateId) {
		return submissionApproverHierarchyDao.getSubmissionApproverHierarchyForEmployeeByProjectIdAndMasterCandidateId(frequencyStartDate, frequencyEndDate, projectId,masterCandidateId);
	}
	

	/*@Override
	public SubmissionApproverHierarchy getSubmissionApproverHierarchyByProjectMemberIdStartDateAndEndDateForApproverLogin (Long approverUserId,
			Long projectMemberId, Date startDate, Date endDate) throws Exception{
		return submissionApproverHierarchyDao.getSubmissionApproverHierarchyByProjectMemberIdStartDateAndEndDateForApproverLogin(approverUserId,projectMemberId, startDate, endDate);
	}*/
	
	@Override
	public SubmissionApproverHierarchy getSubmissionApproverHierarchyByMasterCandidateIdStartDateAndEndDateForApproverLogin(Long approverUserId,Long projectId,Long pmMasterCandidateId,Long locationsId,Date startDate, Date endDate) throws Exception
	{
		return submissionApproverHierarchyDao.getSubmissionApproverHierarchyByMasterCandidateIdStartDateAndEndDateForApproverLogin(approverUserId, projectId, pmMasterCandidateId, locationsId, startDate, endDate);
	}
	@Override
	public void updateApprovalStatusByApproverForProjectMemberId(Long projectMemberId, Long approverUserId,
			Date startDate, Date endDate, String status, String rejectedReason) throws Exception {
		 submissionApproverHierarchyDao.updateApprovalStatusByApproverForProjectMemberId(projectMemberId, approverUserId, startDate, endDate, status, rejectedReason);
	}

	@Override
	public Boolean getCronRemainderForEmployeeByProjectMemberId(Date frequencyStartDate, Date frequencyEndDate,
			Long projectMemberId) {
		return  submissionApproverHierarchyDao.getCronRemainderForEmployeeByProjectMemberId(frequencyStartDate, frequencyEndDate, projectMemberId);
	}

	@Override
	public SubmissionApproverHierarchy getSubmissionApproverHierarchyByProjectMemberIdStartDateAndEndDateForApproverLoginForTsReports(
			Long approverUserId, Long projectMemberId, Date startDate, Date endDate) throws Exception {
		return submissionApproverHierarchyDao.getSubmissionApproverHierarchyByProjectMemberIdStartDateAndEndDateForApproverLoginForTsReports(approverUserId, projectMemberId, startDate, endDate);
	}

	@Override
	public Map<String, Float> getTotalHoursByProjectMemberIdStartDateAndEndDateForApproverLoginForTsReports(
			Long approverUserId, Long projectMemberId, Date startDate, Date endDate) throws Exception {
		return submissionApproverHierarchyDao.getTotalHoursByProjectMemberIdStartDateAndEndDateForApproverLoginForTsReports(approverUserId, projectMemberId, startDate, endDate);
	}

	@Override
	public Map<String, Float> getTotalHoursByProjectMemberIdStartDateAndEndDateForInvoice(Long projectMemberId,
			Date startDate, Date endDate) throws Exception {
		return submissionApproverHierarchyDao.getTotalHoursByProjectMemberIdStartDateAndEndDateForInvoice(projectMemberId, startDate, endDate);
	}

	@Override
	public List<SubmissionApproverHierarchy> getApprovedSubmissionApproverHierarchiesByProjectIdFrequencyStartDateAndFrequencyEndDate(Long projectId, Date frequencyStartDate, Date frequencyEndDate,String invoiceType)
	{
		return submissionApproverHierarchyDao.getApprovedSubmissionApproverHierarchiesByProjectIdFrequencyStartDateAndFrequencyEndDate(projectId,frequencyStartDate,frequencyEndDate,invoiceType);
	}

	@Override
	public void updateAllSubmissionApproverHierarchyInvoiceStatusBySubmissionApproverHierarchyId(Long submissionHierarchyId,String invoiceType,String status) throws Exception {
		 submissionApproverHierarchyDao.updateAllSubmissionApproverHierarchyInvoiceStatusBySubmissionApproverHierarchyId(submissionHierarchyId,invoiceType,status);
	}

	

}
