package com.bis.sourcelead.timesheet.rest;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.mail.internet.InternetAddress;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.ProjectApproverHierarchyDao;
import com.bis.sourcelead.dao.ProjectMembersDao;
import com.bis.sourcelead.entity.EmailAttachment;
import com.bis.sourcelead.entity.EmailMessage;
import com.bis.sourcelead.entity.EmailScenario;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.ProjectApproverHierarchy;
import com.bis.sourcelead.entity.ProjectClients;
import com.bis.sourcelead.entity.ProjectMembers;
import com.bis.sourcelead.entity.SubmissionApproverHierarchy;
import com.bis.sourcelead.entity.TimesheetSubmissions;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserRole;
import com.bis.sourcelead.service.EmailScenarioService;
import com.bis.sourcelead.service.ProjectApproverHierarchyService;
import com.bis.sourcelead.service.ProjectClientService;
import com.bis.sourcelead.service.UserRoleService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.timesheet.service.SubmissionApproverHierarchyService;
import com.bis.sourcelead.timesheet.service.TimesheetSubmissionsService;
import com.bis.sourcelead.util.CalendarUtil;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.EmailScenerioUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;

@Component
public class TimesheetEmailUtil {

	@Value("${sourceleadUrl}")
	private String sourceleadUrl;

	@Value("${sourceleadLoginUrl}")
	private String sourceleadLoginUrl;

	@Autowired
	private ProjectApproverHierarchyService projectApproverHierarchyService;

	@Autowired
	private ProjectClientService projectClientService;

	@Autowired
	private CommonUtil commonUtil;

	@Autowired
	private EmailScenarioService emailScenarioService;

	@Autowired
	private EmailScenerioUtil emailScenerioUtil;

	@Autowired
	private SubmissionApproverHierarchyService submissionApproverHierarchyService;

	@Autowired
	private UserService userService;
	
	@Autowired
	private ProjectMembersDao projectMembersDao;
	
	@Autowired
	private ProjectApproverHierarchyDao projectApproverHierarchyDao;
	
	@Autowired
	private UserRoleService userRoleService;
	
	@Autowired
	private TimesheetSubmissionsService timesheetSubmissionsService ;
	
	/**
	 * @param firstName
	 * @param lastName
	 * @param toEmail
	 * @param loginUser
	 * @param loginOrgLocation
	 * @param projectMember
	 * @param startDate
	 * @param endDate
	 * @param status
	 * @param rejectedReason
	 * @throws Exception
	 * @Description send mail and notification to employee and end client.
	 */
	void sendEmailNotificationToEmployeeAndApproversOfEndClient(String firstName, String lastName, String toEmail,
		User loginUser, OrgLocations loginOrgLocation, ProjectMembers projectMember, String startDate,
		String endDate, String status, String rejectedReason) throws Exception {
		EmailMessage emailMessage = new EmailMessage();
		emailMessage.setFirstName(firstName);
		emailMessage.setLastName(lastName);
		emailMessage.setToEmail(toEmail);
		emailMessage.setFromEmail(loginUser.getEmail());
		emailMessage.setCreatedBy(loginUser.getUserCode());
		
		//redirection Url
		String redirectURL = "/timesheet/";
		Long redirectOrgLocationId = loginOrgLocation.getId();
				
		StringBuilder userNameSB = new StringBuilder();
		if (loginUser.getLastName() != null && loginUser.getLastName() != "undefined" && loginUser.getLastName() != ""
				&& !loginUser.getLastName().isEmpty()) {
			emailMessage.setFromName(
					userNameSB.append(loginUser.getFirstName()).append(" ").append(loginUser.getLastName()).toString());
		} else {
			emailMessage.setFromName(userNameSB.append(loginUser.getFirstName()).append(" ").toString());

		}
		emailMessage.setEmailType(Constants.CUSTOM_USER_EMAIL);
		// setting the class filed values
		Map<String, String> globalVarsMap = new HashMap<>();
		globalVarsMap.put("LoginUserFirstName", loginUser.getFirstName());
		globalVarsMap.put("LoginUserLastName", loginUser.getLastName());
		globalVarsMap.put("EmployeeFirstName", projectMember.getProjectMemberCandidteId().getFirstName());
		globalVarsMap.put("EmployeeLastName", projectMember.getProjectMemberCandidteId().getLastName());
		globalVarsMap.put("OrgName", loginOrgLocation.getOrgLocationName());
		globalVarsMap.put("projectName", projectMember.getProjects().getProjectName());
		globalVarsMap.put("frequencyType", projectMember.getProjects().getInvoiceFrequency());
		StringBuilder timeFrequencySB = new StringBuilder();
		globalVarsMap.put("TimeFrequency", timeFrequencySB.append(startDate).append(" to ").append(endDate).toString());

		globalVarsMap.put("ManagerFirstName", loginUser.getFirstName());
		globalVarsMap.put("ManagerLastName", loginUser.getLastName());
		if ("REJECTED".equalsIgnoreCase(status)) {
			globalVarsMap.put("RejectedReason", rejectedReason);
		}
		StringBuilder loginURLSB = new StringBuilder();
		
		if("REJECTED".equalsIgnoreCase(status)) {
		globalVarsMap.put("loginURL", loginURLSB.append("<a href='").append(sourceleadLoginUrl+ "?redirectURL=" +redirectURL+"&redirectOrgLocationId="+redirectOrgLocationId).append(
				"'style='text-decoration:none;'><div style=' width:220px; border-radius:30px; background-color:#214b90; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Timesheet Rejected</div></a>")
				.toString());
		}else {
			globalVarsMap.put("loginURL", loginURLSB.append("<a href='").append(sourceleadLoginUrl+ "?redirectURL=" +redirectURL+"&redirectOrgLocationId="+redirectOrgLocationId).append(
					"'style='text-decoration:none;'><div style=' width:220px; border-radius:30px; background-color:#214b90; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Timesheet Approved</div></a>")
					.toString());
		}

		
		
		
		EmailScenario emailScenario = null;
		if ("REJECTED".equalsIgnoreCase(status)) {
			emailScenario = emailScenarioService.getEmailScenarioByUniqueName(
					Constants.MAIL_TEAM_MEMBERS_REJECTED_TIMESHEET, Constants.ENTITY_TYPE_EMAIL);
		}
		if ("APPROVED".equalsIgnoreCase(status)) {
			emailScenario = emailScenarioService.getEmailScenarioByUniqueName(
					Constants.MAIL_TEAM_MEMBERS_APPROVED_TIMESHEET, Constants.ENTITY_TYPE_EMAIL);
		}
		if ("REJECTED".equalsIgnoreCase(status)) {
		 commonUtil.sendTemplateEmail(emailMessage, globalVarsMap,loginOrgLocation.getOrg(),loginOrgLocation,Constants.MAIL_TEAM_MEMBERS_REJECTED_TIMESHEET,new ArrayList<EmailAttachment>());
		}else{
		 commonUtil.sendTemplateEmail(emailMessage, globalVarsMap,loginOrgLocation.getOrg(),loginOrgLocation,Constants.MAIL_TEAM_MEMBERS_APPROVED_TIMESHEET,new ArrayList<EmailAttachment>());
		}
		
		EmailScenario notificationScenario = null;
		if ("REJECTED".equalsIgnoreCase(status)) {
			notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(
					Constants.MAIL_TEAM_MEMBERS_REJECTED_TIMESHEET, Constants.ENTITY_TYPE_NOTIFICATION);
		}
		if ("APPROVED".equalsIgnoreCase(status)) {
			notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(
					Constants.MAIL_TEAM_MEMBERS_APPROVED_TIMESHEET, Constants.ENTITY_TYPE_NOTIFICATION);
		}
		String notificationBody = emailScenerioUtil.buildTemplateBody(notificationScenario.getBody(), globalVarsMap,emailScenario);
		notificationScenario.setBody(notificationBody);
		notificationScenario.setOrgCode(loginOrgLocation.getLocationCode());

		StringBuilder actionLinkBuilder = new StringBuilder();
		Date startDateFormat = commonUtil.orgLocationDateFormat(loginOrgLocation.getDateFormat(), startDate);
		Date endDateFormat = commonUtil.orgLocationDateFormat(loginOrgLocation.getDateFormat(), endDate);
		SubmissionApproverHierarchy sah = submissionApproverHierarchyService.getSubmissionApproverHierarchyForEmployeeByProjectIdAndMasterCandidateId(startDateFormat,endDateFormat, projectMember.getProjects().getId(),projectMember.getMasterCandidateId().getId());
		HashMap<String, String> paramList = new HashMap<>();
		String actionLink = null;
		if (sah != null && sah.getSubmissionId() != null && sah.getSubmissionId().getProjectMembers() != null) {
			paramList.put("startDateStr", startDate);
			paramList.put("endDateStr", endDate);
			paramList.put("dateFormat", loginOrgLocation.getDateFormat());
			paramList.put("projectMemberId", projectMember.getId().toString());
			actionLink = actionLinkBuilder.append(sourceleadUrl).append("/").append("timesheet/").append(Constants.PLACE_UUID).toString();
		}

		emailScenerioUtil.sendNotification(actionLink, loginUser,projectMember.getProjectMemberCandidteId().getConsultantUser(), loginOrgLocation, notificationScenario,paramList);
	}

	
	/**
	 * @param projectMember
	 * @param loginUser
	 * @param loginOrgLocation
	 * @param loginOrgCode
	 * @param startDate
	 * @param endDate
	 * @param status
	 * @param rejectedReason
	 * @throws Exception
	 * @description send email and notification to vendors about time sheet approval status
	 */
	void sendEmailToVendorAtTimesheetApprovalStatus(ProjectMembers projectMember, User loginUser,
			OrgLocations loginOrgLocation, String startDate, String endDate, String status,
			String rejectedReason) throws Exception {
		List<ProjectApproverHierarchy> approverHierarchyList = projectApproverHierarchyService
				.getActiveOrgApproverUsersByProjectMemberMasterCandidateIdAndProjectId(
						projectMember.getMasterCandidateId().getId(), projectMember.getProjects().getId());
		// login Org ProjectClient(Current-Level-Org)
		ProjectClients projectClients = projectClientService.getProjectClientsByProjectIdAndOrgLocationId(
				projectMember.getProjects().getId(), loginOrgLocation.getId());

		for (ProjectApproverHierarchy approver : approverHierarchyList) {
			// Sending Mail to Low-Level Org Approvers(Vendor Approvers)
			if (approver.getProjectClientsId() != null && approver.getProjectClientsId().getOrgLocationsId() != null && projectClients != null
            && !projectClients.getOrgLocationsId().getLocationCode().equalsIgnoreCase(approver.getProjectClientsId().getOrgLocationsId().getLocationCode())
					&& projectClients.getLevel() < approver.getProjectClientsId().getLevel()) {

				EmailMessage emailMessage = new EmailMessage();
				emailMessage.setFirstName(approver.getApproverUserId().getFirstName());
				emailMessage.setLastName(approver.getApproverUserId().getLastName());
				emailMessage.setToEmail(approver.getApproverUserId().getEmail());
				emailMessage.setFromEmail(loginUser.getEmail());
				emailMessage.setCreatedBy(loginUser.getUserCode());

				DateFormat TZ_DF_TS = new SimpleDateFormat(loginOrgLocation.getDateFormat());
				String startDateStr = commonUtil.dateStrFromOrgDateFormatter(loginOrgLocation.getDateFormat(),
						TZ_DF_TS.parse(startDate));
				String endDateStr = commonUtil.dateStrFromOrgDateFormatter(loginOrgLocation.getDateFormat(),
						TZ_DF_TS.parse(endDate));

				// setting the class filed values
				Map<String, String> globalVarsMap = new HashMap<String, String>();
				StringBuilder loginName = new StringBuilder();
				if (null != projectMember.getProjectMemberCandidteId().getLastName()
						&& !"".equalsIgnoreCase(projectMember.getProjectMemberCandidteId().getLastName())) {
					globalVarsMap.put("employeename",loginName.append(projectMember.getProjectMemberCandidteId().getFirstName()).append(" ")
									.append(projectMember.getProjectMemberCandidteId().getLastName()).toString());
				} else {
					globalVarsMap.put("employeename", projectMember.getProjectMemberCandidteId().getFirstName());
				}
				StringBuilder managerName = new StringBuilder();
				if (null != approver.getApproverUserId().getLastName()
						&& !"".equalsIgnoreCase(approver.getApproverUserId().getLastName())) {
					globalVarsMap.put("vendorcontactname",managerName.append(approver.getApproverUserId().getFirstName()).append(" ")
									.append(approver.getApproverUserId().getLastName()).toString());
				} else {
					globalVarsMap.put("vendorcontactname", approver.getApproverUserId().getFirstName());
				}
				globalVarsMap.put("projectname", projectMember.getProjects().getProjectName());
				globalVarsMap.put("frequency", startDateStr + " to " + endDateStr);
				globalVarsMap.put("clientName",loginUser.getFullName() + "," + (loginOrgLocation.getOrgLocationName()));
				globalVarsMap.put("reason", rejectedReason);

				EmailScenario emailScenario = null;
				if (status.equals("REJECTED")) {
					emailScenario = emailScenarioService.getEmailScenarioByUniqueName(
							Constants.TIMESHEET_REJECTED_EMAIL_TOVENDOR, Constants.ENTITY_TYPE_EMAIL);
				} else {
					emailScenario = emailScenarioService.getEmailScenarioByUniqueName(
							Constants.TIMESHEET_APPROVED_EMAIL_TOVENDOR, Constants.ENTITY_TYPE_EMAIL);
				}

				// covert subject,body and signature
				String subject = emailScenerioUtil.bulidSubject(emailScenario.getSubject(), globalVarsMap);
				String body = emailScenerioUtil.buildTempBody(emailScenario.getBody(), globalVarsMap);
				String signature = emailScenerioUtil.buildSignature(emailScenario.getSignature(), globalVarsMap);
				String url = sourceleadUrl;

				// construct HTML template using required data
				String emailTemplate = emailScenerioUtil.AssembleEmailMessage(emailScenario, emailMessage, subject,body, signature, url, loginOrgLocation.getOrg(), loginOrgLocation);
				emailMessage.setSubject(subject);
				emailMessage.setMessageBody(emailTemplate);

				// calling the sendEmail method of emailScenerioUtil*/
				 
				 
				if ("REJECTED".equalsIgnoreCase(status)) {
					 commonUtil.sendTemplateEmail(emailMessage, globalVarsMap,loginOrgLocation.getOrg(),loginOrgLocation,Constants.TIMESHEET_REJECTED_EMAIL_TOVENDOR,new ArrayList<EmailAttachment>());
					}else{
					 commonUtil.sendTemplateEmail(emailMessage, globalVarsMap,loginOrgLocation.getOrg(),loginOrgLocation,Constants.TIMESHEET_APPROVED_EMAIL_TOVENDOR,new ArrayList<EmailAttachment>());
					}
		
				EmailScenario notificationScenario = null;
				if (status.equals("REJECTED")) {
					notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(
							Constants.TIMESHEET_REJECTED_EMAIL_TOVENDOR, Constants.ENTITY_TYPE_NOTIFICATION);
				} else {
					notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(
							Constants.TIMESHEET_APPROVED_EMAIL_TOVENDOR, Constants.ENTITY_TYPE_NOTIFICATION);
				}

				String notificationBody = emailScenerioUtil.buildTemplateBody(notificationScenario.getBody(), globalVarsMap,emailScenario);
				notificationScenario.setBody(notificationBody);
				notificationScenario.setOrgCode(loginOrgLocation.getLocationCode());

				StringBuilder actionLinkBuilder = new StringBuilder();
				Date startDateFormat = commonUtil.orgLocationDateFormat(loginOrgLocation.getDateFormat(), startDateStr);
				Date endDateFormat = commonUtil.orgLocationDateFormat(loginOrgLocation.getDateFormat(), endDateStr);
				SubmissionApproverHierarchy sah = submissionApproverHierarchyService.getSubmissionApproverHierarchyForEmployeeByProjectIdAndMasterCandidateId(startDateFormat,
								endDateFormat, projectMember.getProjects().getId(),
								projectMember.getMasterCandidateId().getId());
				HashMap<String, String> paramList = new HashMap<>();
				String actionLink = null;
				if (sah != null && sah.getSubmissionId() != null && sah.getSubmissionId().getProjectMembers() != null) {
					String frequencyMonth = CalendarUtil.getMonthFromGivenDate(startDateFormat);
					String frequencyYear = CalendarUtil.getYearFromGivenDate(endDateFormat);
					paramList.put("frequencyMonth", frequencyMonth + "-" + frequencyYear);
					paramList.put("startDateStr", startDate);
					paramList.put("endDateStr", endDate);
					paramList.put("dateFormat", loginOrgLocation.getDateFormat());
					paramList.put("timesheetFrequency", projectMember.getProjects().getInvoiceFrequency());
					paramList.put("searchData",sah.getSubmissionId().getProjectMembers().getProjectMemberCandidteId().getEmail());
					paramList.put("projectName",sah.getSubmissionId().getProjectMembers().getProjects().getProjectName());
					actionLink = actionLinkBuilder.append(sourceleadUrl).append("/").append("timesheetApproval/").append(Constants.PLACE_UUID).toString();
				}

				emailScenerioUtil.sendNotification(actionLink, loginUser, approver.getApproverUserId(),
						approver.getProjectClientsId().getOrgLocationsId(), notificationScenario, paramList);

			}
		}
	}
	
	/**
	 * @param masterCanidateId
	 * @param projectId
	 * @param frequencyStartDate
	 * @param frequencyEndDate
	 * @param loginUser
	 * @param loginOrgLocation
	 * @param loginOrgCode
	 * @throws Exception
	 * @Description send email  to manager.
	 */
	void sendEmailToManagerAtTimesheetSubmissionsNew(ProjectMembers projMembers,Long masterCanidateId, Long projectId,
			Date frequencyStartDate, Date frequencyEndDate, User loginUser, OrgLocations loginOrgLocation,String loginOrgCode) throws Exception {
		List<ProjectApproverHierarchy> approverHierarchyList = projectApproverHierarchyService
				.getActiveOrgApproverUsersByProjectMemberMasterCandidateIdAndProjectId(masterCanidateId, projectId);
		if(approverHierarchyList != null && !approverHierarchyList.isEmpty() && projMembers != null && projMembers.getProjects() != null && StringUtils.isNotEmpty( projMembers.getProjects().getIsReportingManagerAsApprover())
				   &&  projMembers.getProjects().getIsReportingManagerAsApprover().equalsIgnoreCase("Y") && projMembers.getProjectMemberCandidteId() != null &&  projMembers.getProjectMemberCandidteId().getConsultantUser() != null
				   &&  projMembers.getProjectMemberCandidteId().getConsultantUser()!= null && projMembers.getProjectMemberCandidteId().getOrgLocationId() != null) {
			
			
			UserRole userRole = userRoleService.getProjectmemberUserDetailsByUserIdAndLocationId(projMembers.getProjectMemberCandidteId().getConsultantUser().getId(), projMembers.getProjectMemberCandidteId().getOrgLocationId().getId(),projMembers.getProjectMemberCandidteId().getDesignationType());
		   ProjectApproverHierarchy approver =  approverHierarchyList.iterator().next();
		   
		   if(userRole.getReportingManager() != null) {
			   ProjectApproverHierarchy reportingManager = new ProjectApproverHierarchy();
			   reportingManager.setApproverUserId(userRole.getReportingManager()); 
			   reportingManager.setProjectId(approver.getProjectId());
			   reportingManager.setProjectClientsId(approver.getProjectClientsId());
			   approverHierarchyList.add(reportingManager);
		   }
		   
		}
		//redirection URL
		String redirectURL = "/timesheetApproval/";
		Long redirectOrgLocationId = loginOrgLocation.getId();
		
		for (ProjectApproverHierarchy approver : approverHierarchyList) {

			if (!loginUser.getUserCode().equalsIgnoreCase(approver.getApproverUserId().getUserCode())) {

				User projectManager = userService.getUserById(approver.getApproverUserId().getId());
				// Employees emp = employeesService.getEmployeeByUserId(loginUser.getId());

				EmailMessage emailMessage = new EmailMessage();
				emailMessage.setFirstName(projectManager.getFirstName());
				emailMessage.setLastName(projectManager.getLastName());
				emailMessage.setToEmail(projectManager.getEmail());
				emailMessage.setFromEmail(loginUser.getEmail());
				emailMessage.setCreatedBy(loginUser.getUserCode());
				emailMessage.setEmailType(Constants.CUSTOM_USER_EMAIL);
				String startDateStr = commonUtil.dateStrFromOrgDateFormatter(loginOrgLocation.getDateFormat(),frequencyStartDate);
				String endDateStr = commonUtil.dateStrFromOrgDateFormatter(loginOrgLocation.getDateFormat(),frequencyEndDate);
				SubmissionApproverHierarchy sah = submissionApproverHierarchyService.getSubmissionApproverHierarchyForEmployeeByProjectIdAndMasterCandidateId(frequencyStartDate,
						frequencyEndDate, projectId, masterCanidateId);
				if(sah != null && sah.getSubmissionId() != null && sah.getSubmissionId().getProjectMembers() != null && sah.getSubmissionId().getProjectMembers().getProjects() != null) {
					// setting the class filed values
					Map<String, String> globalVarsMap = new HashMap<String, String>();
					StringBuilder loginName = new StringBuilder();
					if (null != loginUser.getLastName() && !"".equalsIgnoreCase(loginUser.getLastName())) {
						globalVarsMap.put("loginUserFullName", loginName.append(loginUser.getFirstName()).append(" ")
								.append(loginUser.getLastName()).toString());
					} else {
						globalVarsMap.put("loginUserFullName", loginUser.getFirstName());
					}
					StringBuilder managerName = new StringBuilder();
					if (null != projectManager.getLastName() && !"".equalsIgnoreCase(projectManager.getLastName())) {
						globalVarsMap.put("managerFullName", managerName.append(projectManager.getFirstName()).append(" ")
								.append(projectManager.getLastName()).toString());
					} else {
						globalVarsMap.put("managerFullName", projectManager.getFirstName());
					}
					globalVarsMap.put("OrgName", loginOrgLocation.getOrgLocationName());
					globalVarsMap.put("TimeFrequency", startDateStr + " to " + endDateStr);
					
					StringBuilder linkBuild = new StringBuilder();
					globalVarsMap.put("loginURL", linkBuild.append("<a href='").append(sourceleadLoginUrl+ "?redirectURL=" +redirectURL+"&redirectOrgLocationId="+redirectOrgLocationId).append(
							"' style='text-decoration:none; '><div style=' width:220px; border-radius:30px; background-color:#214b90; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Timesheet Submission</div></a>")
							.toString());
					
					
					/*globalVarsMap.put("loginURL", "<a href='" +sourceleadLoginUrl+ "?redirectURL=" +redirectURL+"&redirectOrgLocationId="+redirectOrgLocationId
							+ "' style='text-decoration:none; '><div style=' width:220px; border-radius:30px; background-color:#214b90; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Timesheet Submission</div></a>");
					*/
					
					
					globalVarsMap.put("projectName",sah.getSubmissionId().getProjectMembers().getProjects().getProjectName());
					// setting the Recipient information
					//InternetAddress[] iAdressArray = InternetAddress.parse(emailMessage.getToEmail());

					// getting the current EmailScenario by EmailScenario unique name and entity
					// type
					EmailScenario emailScenario = emailScenarioService.getEmailScenarioByUniqueName(
					         Constants.MAIL_PROJECT_MANAGER_TIMESHEET, Constants.ENTITY_TYPE_EMAIL);
				
					commonUtil.sendTemplateEmail(emailMessage, globalVarsMap,loginOrgLocation.getOrg(),loginOrgLocation,Constants.MAIL_PROJECT_MANAGER_TIMESHEET,new ArrayList<EmailAttachment>());
					
			
					// calling the sendNotification method of emailScenerioUtil
					EmailScenario notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(
							Constants.MAIL_PROJECT_MANAGER_TIMESHEET, Constants.ENTITY_TYPE_NOTIFICATION);
					String notificationBody = emailScenerioUtil.buildTemplateBody(notificationScenario.getBody(), globalVarsMap,emailScenario);
					notificationScenario.setBody(notificationBody);
					notificationScenario.setOrgCode(loginOrgCode);
					// String url = null;

					StringBuilder currentTimeFrequency = new StringBuilder();
					currentTimeFrequency.append(startDateStr).append("To").append(endDateStr);
					String actionLink = "";

					StringBuilder actionLinkBuilder = new StringBuilder();
					
					HashMap<String, String> paramList = new HashMap<>();
					if (sah != null && sah.getSubmissionId() != null && sah.getSubmissionId().getProjectMembers() != null) {
						String frequencyMonth = CalendarUtil.getMonthFromGivenDate(frequencyStartDate);
						String frequencyYear = CalendarUtil.getYearFromGivenDate(frequencyStartDate);
						paramList.put("frequencyMonth", frequencyMonth + "-" + frequencyYear);
						paramList.put("startDateStr", startDateStr);
						paramList.put("endDateStr", endDateStr);
						paramList.put("dateFormat", loginOrgLocation.getDateFormat());
						paramList.put("timesheetFrequency", approver.getProjectId().getInvoiceFrequency());
						paramList.put("searchData",sah.getSubmissionId().getProjectMembers().getProjectMemberCandidteId().getEmail());
						paramList.put("projectName",sah.getSubmissionId().getProjectMembers().getProjects().getProjectName());
						actionLink = actionLinkBuilder.append(sourceleadUrl).append("/").append("timesheetApproval/").append(Constants.PLACE_UUID).toString();
					}
					emailScenerioUtil.sendNotification(actionLink, loginUser, projectManager,
							approver.getProjectClientsId().getOrgLocationsId(), notificationScenario, paramList);
				}

			}
		}
	}
	
	

	 /**
	 * @author  venkata kesav
	 * @since  aug 19 2016
	 * This method is used to send email to project managers .
	 * this method is called at saveTimesheet() method.
	 * @param timesheetSubmissions This is the first parameter to sendEmailToManagerAtTimesheetSubmissions method
	 * @param loginUser This is the second parameter to sendEmailToManagerAtTimesheetSubmissions method
	 * @param loginOrgLocation This is the third parameter to sendEmailToManagerAtTimesheetSubmissions method
	 * @param loginOrgCode This is the third parameter to sendEmailToManagerAtTimesheetSubmissions method
	 * @return void 
	 * @throws NullPointer
	 */
	public void sendEmailToManagerAtTimesheetSubmissions(TimesheetSubmissions timesheetSubmissions, User loginUser,
			OrgLocations loginOrgLocation, String loginOrgCode) throws Exception {
		List<ProjectApproverHierarchy> approverHierarchyList = projectApproverHierarchyService
				.getApproverUsersByProjectId(timesheetSubmissions.getProjectMembers().getProjects().getId());

		for (ProjectApproverHierarchy approver : approverHierarchyList) {

			if (!loginUser.getUserCode().equalsIgnoreCase(approver.getApproverUserId().getUserCode())
					&& (approver.getLevel() == Constants.LEVEL1 || approver.getLevel() == Constants.LEVEL2)) {

				User projectManager = userService.getUserById(approver.getApproverUserId().getId());
				EmailMessage emailMessage = new EmailMessage();
				emailMessage.setFirstName(projectManager.getFirstName());
				emailMessage.setLastName(projectManager.getLastName());
				emailMessage.setToEmail(projectManager.getEmail());
				emailMessage.setFromEmail(loginUser.getEmail());
				emailMessage.setCreatedBy(loginUser.getUserCode());
				emailMessage.setFromName(loginUser.getFirstName() + " " + loginUser.getLastName());
				emailMessage.setEmailType(Constants.CUSTOM_USER_EMAIL);
				String startDateStr = commonUtil.dateStrFromOrgDateFormatter(loginOrgLocation.getDateFormat(),
						timesheetSubmissions.getStartDate());
				String endDateStr = commonUtil.dateStrFromOrgDateFormatter(loginOrgLocation.getDateFormat(),
						timesheetSubmissions.getEndDate());

				// setting the class filed values
				Map<String, String> globalVarsMap = new HashMap<String, String>();
				globalVarsMap.put("loginUserFirstName", loginUser.getFirstName());
				globalVarsMap.put("loginUserLastName", loginUser.getLastName());
				globalVarsMap.put("managerFirstName", projectManager.getFirstName());
				globalVarsMap.put("managerLastName", projectManager.getLastName());
				// globalVarsMap.put("projectName", project.getProjectName());
				globalVarsMap.put("orgName", loginOrgLocation.getOrgLocationName());
				globalVarsMap.put("timeFrequency", startDateStr + " to " + endDateStr);
				globalVarsMap.put("loginURL", "<a href='" + sourceleadLoginUrl
						+ "' style='text-decoration:none; '><div style=' width:220px; border-radius:30px; background-color:#214b90; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Login</div></a>");

				// setting the Recipient information
				InternetAddress[] iAdressArray = InternetAddress.parse(emailMessage.getToEmail());

				// getting the current EmailScenario by EmailScenario unique name and entity
				// type
				EmailScenario emailScenario = emailScenarioService.getEmailScenarioByUniqueName(
						Constants.MAIL_PROJECT_MANAGER_TIMESHEET, Constants.ENTITY_TYPE_EMAIL);

				// covert subject,body and signature
				String subject = emailScenerioUtil.bulidSubject(emailScenario.getSubject(), globalVarsMap);
				String body = emailScenerioUtil.buildTempBody(emailScenario.getBody(), globalVarsMap);
				String signature = emailScenerioUtil.buildSignature(emailScenario.getSignature(), globalVarsMap);
				String url = sourceleadUrl;
				;

				// construct HTML template using required data
				String emailTemplate = emailScenerioUtil.AssembleEmailMessage(emailScenario, emailMessage, subject,
						body, signature, url, loginOrgLocation.getOrg(), loginOrgLocation);
				emailMessage.setSubject(subject);
				emailMessage.setMessageBody(emailTemplate);

				// calling the sendEmail method of emailScenerioUtil
				emailScenerioUtil.sendEmail(emailMessage, loginOrgLocation.getId(), iAdressArray,
						new ArrayList<EmailAttachment>());

				// calling the sendNotification method of emailScenerioUtil
				EmailScenario notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(
						Constants.MAIL_PROJECT_MANAGER_TIMESHEET, Constants.ENTITY_TYPE_NOTIFICATION);
				String notificationBody = emailScenerioUtil.buildTemplateBody(notificationScenario.getBody(), globalVarsMap,notificationScenario);
				notificationScenario.setBody(notificationBody);
				notificationScenario.setOrgCode(loginOrgCode);
				String actionLink = "";
				emailScenerioUtil.sendNotification(actionLink, loginUser, projectManager, loginOrgLocation,
						notificationScenario, new HashMap<String, String>());

			}
		}
	}
	
	
	/**
	 * @param projectMemberId
	 * @param reason
	 * @param loginUser
	 * @param orgLocations
	 * @param timesheetFrequency
	 * @param totalHours
	 * @throws Exception
	 */
	public void sendReopenTimeSheetMail(Long projectMemberId,String reason, User loginUser, OrgLocations orgLocations, String timesheetFrequency, Float totalHours) throws Exception {
		   ProjectMembers clientProjectMember = projectMembersDao.getClientProjectMemberByProjectMemberId(projectMemberId);
		   ProjectApproverHierarchy pah =  projectApproverHierarchyDao.getApproverUsersByProjectIdAndProjectClientId(clientProjectMember.getProjects().getId(), clientProjectMember.getProjectClientsId().getId(),orgLocations.getOrg().getId(),"");
			 if(null != pah && null != pah.getApproverUserId()){
				 EmailMessage emailMessage = new EmailMessage();
				 emailMessage.setFirstName(pah.getApproverUserId().getFirstName());
				 emailMessage.setLastName(pah.getApproverUserId().getLastName());
				 emailMessage.setToEmail(pah.getApproverUserId().getEmail());
				 
				 emailMessage.setFromEmail(loginUser.getEmail());
				 emailMessage.setCreatedBy(loginUser.getUserCode());
				 emailMessage.setFromName(loginUser.getFullName());
				 
				 emailMessage.setEmailType(Constants.CUSTOM_USER_EMAIL);
				
				 //Redirection URL
				String redirectURL = "/timesheetApproval/";
				Long redirectOrgLocationId = orgLocations.getId();
					
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
				 globalVarsMap.put("totalHours",CommonUtil.getHoursDisplayFormat(totalHours,null));
				 globalVarsMap.put("reason",reason);
				 
				 if(clientProjectMember.getProjectMemberCandidteId() !=null && !"".equalsIgnoreCase(clientProjectMember.getProjectMemberCandidteId().getFullName())){
					 globalVarsMap.put("employeeName",clientProjectMember.getProjectMemberCandidteId().getFullName());
				 }
				 globalVarsMap.put("projectName", clientProjectMember.getProjects().getProjectName());
				
				 StringBuilder acceptBuilder = new StringBuilder();
				 StringBuilder rejectBuilder = new StringBuilder();
				 globalVarsMap.put("acceptURL",acceptBuilder.append("<a href='").append(sourceleadUrl).append("/ws/acceptOrRejectReopenTimeSheet?type=accept&projectMemberId=").append(projectMemberId).append("&userId=").append(pah.getApproverUserId().getId()).append("&timesheetFrequency=").append(timesheetFrequency).append("'style='text-decoration:none; display:inline-block; margin-left:10px; margin-top:20px;'><div style=' width:170px; border-radius:30px; background-color:#5cb85c; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Accept</div></a>").toString());
				 globalVarsMap.put("rejectURL",rejectBuilder.append("<a href='").append(sourceleadUrl).append("/ws/acceptOrRejectReopenTimeSheet?type=reject&projectMemberId=").append(projectMemberId).append("&userId=").append(pah.getApproverUserId().getId()).append("&timesheetFrequency=").append(timesheetFrequency).append("'style='text-decoration:none; display:inline-block; margin-left:15px; margin-top:20px;'><div style=' width:170px; border-radius:30px; background-color:#e74c51; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Reject</div></a>").toString());
				 StringBuilder stringBuildforLink = new StringBuilder();
				 
				globalVarsMap.put("loginLink",stringBuildforLink.append("<a href='").append(sourceleadLoginUrl+ "?redirectURL=" +redirectURL+"&redirectOrgLocationId="+redirectOrgLocationId).append( "'style='text-decoration:none; display:inline-block; margin-left:15px; margin-top:20px;'><div style=' width:170px; border-radius:30px; background-color:#214b90; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Reopen Timesheet</div></a>").toString());
				 //setting the Recipient information
				 InternetAddress[] iAdressArray = InternetAddress.parse(emailMessage.getToEmail());
				 
				 //getting the current EmailScenario by EmailScenario unique name and entity type
				 EmailScenario  emailScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.MAIL_REOPEN_TIMESHEET,Constants.ENTITY_TYPE_EMAIL);
			 
				 commonUtil.sendTemplateEmail(emailMessage, globalVarsMap,orgLocations.getOrg(),orgLocations,Constants.MAIL_REOPEN_TIMESHEET,new ArrayList<EmailAttachment>());
				 
				EmailScenario  notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.MAIL_REOPEN_TIMESHEET,Constants.ENTITY_TYPE_NOTIFICATION);
				String notificationBody = emailScenerioUtil.buildTemplateBody(notificationScenario.getBody(),globalVarsMap,emailScenario);
				notificationScenario.setBody(notificationBody);
				
				StringBuilder actionLinkBuilder = new StringBuilder();
				Date startDateFormat = commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(), weekStartDate);
				Date endDateFormat = commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(), weekEndDate);
				SubmissionApproverHierarchy sah = submissionApproverHierarchyService.getSubmissionApproverHierarchyForEmployeeByProjectIdAndMasterCandidateId(startDateFormat,endDateFormat,clientProjectMember.getProjects().getId(),clientProjectMember.getMasterCandidateId().getId());
				HashMap<String,String> paramList = new HashMap<>();
				String actionLink = null;
				if(sah!= null && sah.getSubmissionId() != null && sah.getSubmissionId().getProjectMembers()!= null) {
					String frequencyMonth =  CalendarUtil.getMonthFromGivenDate(startDateFormat);
					String frequencyYear =  CalendarUtil.getYearFromGivenDate(endDateFormat);
					paramList.put("frequencyMonth", frequencyMonth+"-"+frequencyYear);
					paramList.put("startDateStr",weekStartDate);
					paramList.put("endDateStr",weekEndDate);
					paramList.put("dateFormat",orgLocations.getDateFormat());
					paramList.put("timesheetFrequency",clientProjectMember.getProjects().getInvoiceFrequency());
					paramList.put("searchData",sah.getSubmissionId().getProjectMembers().getProjectMemberCandidteId().getEmail());
					paramList.put("projectName",sah.getSubmissionId().getProjectMembers().getProjects().getProjectName());
					actionLink =actionLinkBuilder.append(sourceleadUrl).append("/").append("timesheetApproval/").append(Constants.PLACE_UUID).toString();
				}
				
				
				emailScenerioUtil.sendNotification(actionLink , loginUser, pah.getApproverUserId(), pah.getProjectClientsId().getOrgLocationsId(), notificationScenario,paramList);
			 }
	}
	
	
	public void sendReopenTimeSheetAcceptOrRejectMail(Long projectMemberId,String reason, User loginUser, OrgLocations orgLocations, String timesheetFrequency,String type, User reopenedUser) throws Exception {
		EmailScenario emailScenario = null;
		EmailScenario notificationScenario = null;
		EmailMessage emailMessage = new EmailMessage();
		emailMessage.setFirstName(reopenedUser.getFirstName());
		emailMessage.setLastName(reopenedUser.getLastName());
		emailMessage.setToEmail(reopenedUser.getEmail());

		emailMessage.setFromEmail(loginUser.getEmail());
		emailMessage.setCreatedBy(loginUser.getUserCode());
		emailMessage.setFromName(loginUser.getFullName());

		emailMessage.setEmailType(Constants.CUSTOM_USER_EMAIL);

		ProjectMembers projectMembers = projectMembersDao.getConsultantUserProjectMemberByProjectMemberId(projectMemberId);

		String[] frequency = timesheetFrequency.split("To");
		String weekStartDate = frequency[0].trim();
		String weekEndDate = frequency[1].trim();
		TimesheetSubmissions timesheetSubmissions = timesheetSubmissionsService	.getTimesheetSubmissionsByMemberIdAndStartDate(projectMemberId,commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(), weekStartDate));

		// setting the class filed values
		Map<String, String> globalVarsMap = new HashMap<String, String>();
		globalVarsMap.put("superUserName", loginUser.getFullName());
		// globalVarsMap.put("employeeName",projectMembers.getEmployee().getUser().getFullName());
		globalVarsMap.put("managerName", reopenedUser.getFullName());
		// globalVarsMap.put("projectName",
		// projectMembers.getProjects().getProjectName());
		globalVarsMap.put("reason", reason);

		globalVarsMap.put("loginURL", "<a href='" + sourceleadLoginUrl
				+ "' style='text-decoration:none; '><div style=' width:220px; border-radius:30px; background-color:#214b90; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Login</div></a>");

		if (projectMembers.getProjectMemberCandidteId() != null
				&& projectMembers.getProjectMemberCandidteId().getConsultantUser() != null && !"".equalsIgnoreCase(projectMembers.getProjectMemberCandidteId().getConsultantUser().getFullName())) {

			globalVarsMap.put("employeeName",projectMembers.getProjectMemberCandidteId().getConsultantUser().getFullName());
		}
		if (projectMembers.getProjects().getProjectSequeneNumber() != null
				&& !"".equalsIgnoreCase(projectMembers.getProjects().getProjectSequeneNumber())) {
			StringBuilder builder = new StringBuilder();
			globalVarsMap.put("projectName", builder.append(projectMembers.getProjects().getProjectSequeneNumber())
					.append("-").append(projectMembers.getProjects().getProjectName()).toString());

		}

		else {

			globalVarsMap.put("projectName", projectMembers.getProjects().getProjectName());
		}

		HashMap<String, String> paramList = new HashMap<>();
		String actionLink = null;
		if (null != type && "accept".equalsIgnoreCase(type)) {
			emailScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.MAIL_ACCEPT_REOPEN_TIMESHEET,
					Constants.ENTITY_TYPE_EMAIL);
			notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(
					Constants.MAIL_ACCEPT_REOPEN_TIMESHEET, Constants.ENTITY_TYPE_NOTIFICATION);
			StringBuilder actionLinkBuilder = new StringBuilder();
			Date startDateFormat = commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(), weekStartDate);
			Date endDateFormat = commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(), weekEndDate);
			SubmissionApproverHierarchy sah = submissionApproverHierarchyService.getSubmissionApproverHierarchyForEmployeeByProjectIdAndMasterCandidateId(startDateFormat,
					endDateFormat, projectMembers.getProjects().getId(),	projectMembers.getMasterCandidateId().getId());
			if (sah != null && sah.getSubmissionId() != null && sah.getSubmissionId().getProjectMembers() != null) {
				String frequencyMonth = CalendarUtil.getMonthFromGivenDate(startDateFormat);
				String frequencyYear = CalendarUtil.getYearFromGivenDate(endDateFormat);
				paramList.put("frequencyMonth", frequencyMonth + "-" + frequencyYear);
				paramList.put("startDateStr", weekStartDate);
				paramList.put("endDateStr", weekEndDate);
				paramList.put("dateFormat", orgLocations.getDateFormat());
				paramList.put("timesheetFrequency", projectMembers.getProjects().getInvoiceFrequency());
				paramList.put("searchData",sah.getSubmissionId().getProjectMembers().getProjectMemberCandidteId().getEmail());
				paramList.put("projectName", sah.getSubmissionId().getProjectMembers().getProjects().getProjectName());
				actionLink = actionLinkBuilder.append(sourceleadUrl).append("/").append("timesheetApproval/")
						.append(Constants.PLACE_UUID).toString();
			}

		}
		if (null != type && "reject".equalsIgnoreCase(type)) {
			emailScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.MAIL_REJECT_REOPEN_TIMESHEET,
					Constants.ENTITY_TYPE_EMAIL);
			notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(
					Constants.MAIL_REJECT_REOPEN_TIMESHEET, Constants.ENTITY_TYPE_NOTIFICATION);
			StringBuilder actionLinkBuilder = new StringBuilder();
			Date startDateFormat = commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(), weekStartDate);
			Date endDateFormat = commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(), weekEndDate);
			SubmissionApproverHierarchy sah = submissionApproverHierarchyService.getSubmissionApproverHierarchyForEmployeeByProjectIdAndMasterCandidateId(startDateFormat,
							endDateFormat, projectMembers.getProjects().getId(),projectMembers.getMasterCandidateId().getId());
			if (sah != null && sah.getSubmissionId() != null && sah.getSubmissionId().getProjectMembers() != null) {
				String frequencyMonth = CalendarUtil.getMonthFromGivenDate(startDateFormat);
				String frequencyYear = CalendarUtil.getYearFromGivenDate(endDateFormat);
				paramList.put("frequencyMonth", frequencyMonth + "-" + frequencyYear);
				paramList.put("startDateStr", weekStartDate);
				paramList.put("endDateStr", weekEndDate);
				paramList.put("dateFormat", orgLocations.getDateFormat());
				paramList.put("timesheetFrequency", projectMembers.getProjects().getInvoiceFrequency());
				paramList.put("searchData",sah.getSubmissionId().getProjectMembers().getProjectMemberCandidteId().getEmail());
				paramList.put("projectName", sah.getSubmissionId().getProjectMembers().getProjects().getProjectName());
				actionLink = actionLinkBuilder.append(sourceleadUrl).append("/").append("timesheetApproval/").append(Constants.PLACE_UUID).toString();
			}

		}

		if (null != type && "reject".equalsIgnoreCase(type)) {
		    commonUtil.sendTemplateEmail(emailMessage, globalVarsMap,orgLocations.getOrg(),orgLocations,Constants.MAIL_REJECT_REOPEN_TIMESHEET,new ArrayList<EmailAttachment>());
		}else{
			commonUtil.sendTemplateEmail(emailMessage, globalVarsMap,orgLocations.getOrg(),orgLocations,Constants.MAIL_ACCEPT_REOPEN_TIMESHEET,new ArrayList<EmailAttachment>());
		}
		// calling the sendNotification method of emailScenerioUtil
		String notificationBody = emailScenerioUtil.buildTemplateBody(notificationScenario.getBody(), globalVarsMap,emailScenario);
		notificationScenario.setBody(notificationBody);
		UserRole userRole = userRoleService.getUserByUserId(reopenedUser.getId());
		emailScenerioUtil.sendNotification(actionLink, loginUser, reopenedUser, userRole.getOrgLocations(),
				notificationScenario, paramList);
	}

}
