
package com.bis.sourcelead.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.EmailAttachment;
import com.bis.sourcelead.entity.EmailMessage;
import com.bis.sourcelead.entity.EmailScenario;
import com.bis.sourcelead.entity.Expenses;
import com.bis.sourcelead.entity.ExpensesEntries;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.ProjectMembers;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.CandidateService;
import com.bis.sourcelead.service.EmailScenarioService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.EmailScenerioUtil;

@Component
public class ExpensesEmailsUtil {

	@Value("${sourceleadUrl}")
	private String sourceleadUrl;

	@Value("${sourceleadLoginUrl}")
	private String sourceleadLoginUrl;


	@Autowired
	private CommonUtil commonUtil;

	@Autowired
	private EmailScenarioService emailScenarioService;

	@Autowired
	private EmailScenerioUtil emailScenerioUtil;

	@Autowired
	CandidateService candidateService;
	@Autowired
	UserService userService;

	// when employee submit expense mail to managers

	public void sendExpensesEmailToManager(User loginUser, Expenses expenses, OrgLocations orgLoc, String currencyCode)
			throws Exception {

		EmailMessage emailMessage = new EmailMessage();
		if (null != expenses.getProjectManagerUserList()) {
			for (User managerUser : expenses.getProjectManagerUserList()) {

				if (managerUser.getEmail().equalsIgnoreCase(loginUser.getEmail()))
					continue;

				StringBuilder sb = new StringBuilder();
				emailMessage.setFirstName(managerUser.getFirstName());
				if (null != managerUser.getLastName()) {
					emailMessage.setLastName(managerUser.getLastName());
				}
				emailMessage.setToEmail(managerUser.getEmail());
				emailMessage.setFromEmail(loginUser.getEmail());
				if (null != loginUser.getLastName() && !"".equalsIgnoreCase(loginUser.getLastName())) {
					emailMessage.setFromName(
							sb.append(loginUser.getFirstName()).append(" ").append(loginUser.getLastName()).toString());
				} else {
					emailMessage.setFromName(loginUser.getFirstName());
				}
				emailMessage.setEmailType(Constants.CUSTOM_USER_EMAIL);

				emailMessage.setCreatedBy(loginUser.getUserCode());
				emailMessage.setOrgLocationId(orgLoc.getId());

				// setting the class filed values
				Map<String, String> globalVarsMap = new HashMap<String, String>();
				if (null != loginUser.getLastName() && !"".equalsIgnoreCase(loginUser.getLastName())) {
					StringBuilder employee = new StringBuilder();
					globalVarsMap.put("employeeUser", employee.append(loginUser.getFirstName()).append(" ")
							.append(loginUser.getLastName()).toString());
				} else {
					globalVarsMap.put("employeeUser", loginUser.getFirstName());
				}

				if (null != managerUser.getLastName() && !"".equalsIgnoreCase(managerUser.getLastName())) {
					StringBuilder manager = new StringBuilder();
					globalVarsMap.put("managerUser", manager.append(managerUser.getFirstName()).append(" ")
							.append(managerUser.getLastName()).toString());
				} else {
					globalVarsMap.put("managerUser", managerUser.getFirstName());
				}

				if (null != expenses.getProjectMembers()) {
					if (null != expenses.getProjectMembers().getProjects()) {
						globalVarsMap.put("projectName", expenses.getProjectMembers().getProjects().getProjectName());
					}
				}

				if (null != expenses.getPurpose()) {
					globalVarsMap.put("purposeOfVisit", expenses.getPurpose());
				}

				if (null != expenses.getNoOfExpenses()) {
					globalVarsMap.put("expenseTypes", expenses.getNoOfExpenses());
				}

				if (null != expenses.getExpenseId()) {
					globalVarsMap.put("Expense_Id", expenses.getExpenseId());
				}
				
				if (null != expenses.getTotalsubmittedAmount()) {
					globalVarsMap.put("totalSubmittedAmount", expenses.getTotalAmount().toString());
				}

			/*	Set<ExpensesEntries> expensesEntries = expenses.getExpensesEntries();
				for (ExpensesEntries exp : expensesEntries) {

					globalVarsMap.put("totalAmount", exp.getSubmittedamount().toString());

				}*/

				globalVarsMap.put("totalAmount", expenses.getTotalAmount().toString());
				String expenseLocationCode = managerUser.getLocationCode();

				Long redirectOrgLocationId = managerUser.getOrgLocationId();
				String redirectURL = "/addApprovalExpense-params-expenseId=" + expenses.getExpenseId()
						+ "-and-expenseLocationCode=" + expenseLocationCode + "-and-type=view";

				StringBuilder stringBuildforLink = new StringBuilder();
				
				globalVarsMap.put("loginLink",stringBuildforLink.append("<a href='").append(sourceleadLoginUrl+ "?redirectURL=" +redirectURL+"&redirectOrgLocationId="+redirectOrgLocationId).append("' style='text-decoration:none; '><div style=' width:220px; border-radius:30px; background-color:#214b90; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Login</div></a>").toString());
				// getting the current EmailScenario by EmailScenario unique name and entity
				// type
				EmailScenario emailScenario = emailScenarioService
						.getEmailScenarioByUniqueName(Constants.EXPENSES_EMAIL_TO_MANAGER, Constants.ENTITY_TYPE_EMAIL);
				EmailScenario notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(
						Constants.EXPENSES_EMAIL_TO_MANAGER, Constants.ENTITY_TYPE_NOTIFICATION);

				commonUtil.sendTemplateEmail(emailMessage, globalVarsMap, orgLoc.getOrg(), orgLoc,
						Constants.EXPENSES_EMAIL_TO_MANAGER, new ArrayList<EmailAttachment>());

				String notificationBody = emailScenerioUtil.buildTemplateBody(notificationScenario.getBody(),
						globalVarsMap, emailScenario);
				notificationScenario.setBody(notificationBody);
				StringBuilder notificationLink = new StringBuilder();
				String actionLink = "";
				actionLink = notificationLink.append(sourceleadUrl).append("/").append("addApprovalExpense?expenseId="+expenses.getExpenseId()+"&expenseLocationCode="+expenses.getLocationsCode()+"&type=view").toString();
				emailScenerioUtil.sendNotification(actionLink, loginUser, managerUser, orgLoc, notificationScenario,
						new HashMap<String, String>());
			}
		}
	}

	// mail to employee when manager submit expense

	public void sendExpenseMailToEmployee(User loginUser, Expenses expenses, OrgLocations orgLoc, String currencyCode)
			throws Exception {

		EmailMessage emailMessage = new EmailMessage();
		StringBuilder sb = new StringBuilder();
		emailMessage.setFirstName(expenses.getProjectMembers().getEmpName());

		emailMessage.setToEmail(expenses.getProjectMembers().getEmpEmail());
		emailMessage.setFromEmail(loginUser.getEmail());
		if (null != loginUser.getLastName() && !"".equalsIgnoreCase(loginUser.getLastName())) {
			emailMessage.setFromName(
					sb.append(loginUser.getFirstName()).append(" ").append(loginUser.getLastName()).toString());
		} else {
			emailMessage.setFromName(loginUser.getFirstName());
		}
		emailMessage.setEmailType(Constants.CUSTOM_USER_EMAIL);

		emailMessage.setCreatedBy(loginUser.getUserCode());
		emailMessage.setOrgLocationId(orgLoc.getId());

		// setting the class filed values
		Map<String, String> globalVarsMap = new HashMap<String, String>();
		StringBuilder employee = new StringBuilder();

		if (null != expenses.getProjectMembers().getEmpName()) {
			globalVarsMap.put("employeeName", expenses.getProjectMembers().getEmpName());
		}
		if (null != expenses.getProjectMembers()) {
			if (null != expenses.getProjectMembers().getProjects()) {
				globalVarsMap.put("projectName", expenses.getProjectMembers().getProjects().getProjectName());
			}
		}
		if (null != expenses.getPurpose()) {
			globalVarsMap.put("purpose", expenses.getPurpose());
		}
		if (null != expenses.getExpenseId()) {
			globalVarsMap.put("Expense_Id", expenses.getExpenseId());
		}
		
		globalVarsMap.put("loginUser", loginUser.getFirstName());
		globalVarsMap.put("loginOrg", orgLoc.getOrgLocationName());

		if (null != expenses.getTotalsubmittedAmount()) {
			globalVarsMap.put("totalAmount", expenses.getTotalAmount().toString());
		}

		String redirectURL = "/expenses/";
		Long redirectOrgLocationId = orgLoc.getId();

		StringBuilder stringBuildforLink = new StringBuilder();
		globalVarsMap.put("expensesSubmitted",stringBuildforLink.append("<a href='").append(sourceleadLoginUrl+ "?redirectURL=" +redirectURL+"&redirectOrgLocationId="+redirectOrgLocationId).append("' style='text-decoration:none; '><div style=' width:220px; border-radius:30px; background-color:#214b90; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Login</div></a>").toString());
		
		// getting the current EmailScenario by EmailScenario unique name and entity
		// type
		EmailScenario emailScenario = emailScenarioService.getEmailScenarioByUniqueName(
				Constants.MAILTOEMPLOYEEWHEN_MANAGERSUBMITEXPENSE, Constants.ENTITY_TYPE_EMAIL);
		EmailScenario notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(
				Constants.MAILTOEMPLOYEEWHEN_MANAGERSUBMITEXPENSE, Constants.ENTITY_TYPE_NOTIFICATION);

		commonUtil.sendTemplateEmail(emailMessage, globalVarsMap, orgLoc.getOrg(), orgLoc,
				Constants.MAILTOEMPLOYEEWHEN_MANAGERSUBMITEXPENSE, new ArrayList<EmailAttachment>());

		String notificationBody = emailScenerioUtil.buildTemplateBody(notificationScenario.getBody(), globalVarsMap,
				emailScenario);
		notificationScenario.setBody(notificationBody);
		StringBuilder notificationLink = new StringBuilder();
		String actionLink = "";
		 actionLink = notificationLink.append(sourceleadUrl).append("/").append("expenses?expenseId="+expenses.getExpenseId()+"&expenseLocationCode="+expenses.getLocationsCode()+"&type=view").toString();
		Long candidateId = expenses.getProjectMembers().getCandidateId();
		Candidate candidate = candidateService.getCandidateById(candidateId);
		User empUser = userService.getUserById(candidate.getConsultantUser().getId());
		emailScenerioUtil.sendNotification(actionLink, loginUser, empUser, orgLoc, notificationScenario,
				new HashMap<String, String>());
	}

	public void sendEmailNotificationTolevelTwoApprover(Expenses expenseId, String currencyCode, String firstName,
			String lastName, String email, User user, User approverUserId, OrgLocations orgLoc,
			ProjectMembers projectMembers, String startDate, String endDate, String string, String rejectedReason)
			throws Exception {

		EmailMessage emailMessage = new EmailMessage();
		StringBuilder sb = new StringBuilder();
		emailMessage.setFirstName(approverUserId.getUserName());

		emailMessage.setToEmail(approverUserId.getEmail());
		emailMessage.setFromEmail(user.getEmail());
		if (null != user.getLastName() && !"".equalsIgnoreCase(user.getLastName())) {
			emailMessage.setFromName(sb.append(user.getFirstName()).append(" ").append(user.getLastName()).toString());
		} else {
			emailMessage.setFromName(user.getFirstName());
		}
		emailMessage.setEmailType(Constants.CUSTOM_USER_EMAIL);

		emailMessage.setCreatedBy(user.getUserCode());
		emailMessage.setOrgLocationId(orgLoc.getId());

		// setting the class filed values
		Map<String, String> globalVarsMap = new HashMap<String, String>();
		StringBuilder employee = new StringBuilder();

		if (null != expenseId.getProjectMembers().getEmpName()) {
			globalVarsMap.put("employeeName", expenseId.getProjectMembers().getEmpName());
		}
		if (null != expenseId.getProjectMembers()) {
			if (null != expenseId.getProjectMembers().getProjects()) {
				globalVarsMap.put("projectName", expenseId.getProjectMembers().getProjects().getProjectName());
			}
		}
		if (null != expenseId.getExpenseId()) {
			globalVarsMap.put("Expense_Id", expenseId.getExpenseId());
		}
		
		globalVarsMap.put("approved_Amount", expenseId.getTotalAmount().toString());
		
		String startdate = commonUtil.elkDateFormatter(expenseId.getStartDate());
		String enddate = commonUtil.elkDateFormatter(expenseId.getEndDate());
		globalVarsMap.put("loginUser", user.getFullName());
		globalVarsMap.put("loginOrg", orgLoc.getOrgLocationName());
		globalVarsMap.put("expense_StartDate", startdate);
		globalVarsMap.put("expense_EndDate", enddate);
		globalVarsMap.put("manager_Two", approverUserId.getFullName());

		String redirectURL = "/expensesApprovalList/";
		Long redirectOrgLocationId = orgLoc.getId();

		StringBuilder stringBuildforLink = new StringBuilder();
		globalVarsMap.put("LoginURL",stringBuildforLink.append("<a href='").append(sourceleadLoginUrl+ "?redirectURL=" +redirectURL+"&redirectOrgLocationId="+redirectOrgLocationId).append("' style='text-decoration:none; '><div style=' width:220px; border-radius:30px; background-color:#214b90; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Login</div></a>").toString());
		
		// getting the current EmailScenario by EmailScenario unique name and entity
		// type
		EmailScenario emailScenario = emailScenarioService.getEmailScenarioByUniqueName(
				Constants.MAILTOAPPROVER2WHENAPPROVER1APPROVESTHEEXPENSE, Constants.ENTITY_TYPE_EMAIL);
		EmailScenario notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(
				Constants.MAILTOAPPROVER2WHENAPPROVER1APPROVESTHEEXPENSE, Constants.ENTITY_TYPE_NOTIFICATION);

		commonUtil.sendTemplateEmail(emailMessage, globalVarsMap, orgLoc.getOrg(), orgLoc,
				Constants.MAILTOAPPROVER2WHENAPPROVER1APPROVESTHEEXPENSE, new ArrayList<EmailAttachment>());

		String notificationBody = emailScenerioUtil.buildTemplateBody(notificationScenario.getBody(), globalVarsMap,
				emailScenario);
		notificationScenario.setBody(notificationBody);
		StringBuilder notificationLink = new StringBuilder();
		String actionLink = "";
		actionLink = notificationLink.append(sourceleadUrl).append("/").append("addApprovalExpense?expenseId="+expenseId.getExpenseId()+"&expenseLocationCode="+expenseId.getLocationsCode()+"&type=view").toString();
		Long candidateId = expenseId.getProjectMembers().getCandidateId();
		Candidate candidate = candidateService.getCandidateById(candidateId);
		User empUser = userService.getUserById(candidate.getConsultantUser().getId());
		emailScenerioUtil.sendNotification(actionLink, user, empUser, orgLoc, notificationScenario,
				new HashMap<String, String>());
	}

	public void sendEmailNotificationToManagers(Expenses expenses, String currencyCode, String firstName,
			User approverUserId, String lastName, String email, User loginUser, User id, OrgLocations loginOrgLocation,
			ProjectMembers projectMembers, String startDate, String endDate, String submissionStatusStr,
			String rejectedReason) throws Exception {

		EmailMessage emailMessage = new EmailMessage();

		emailMessage.setFirstName(firstName);
		emailMessage.setLastName(lastName);
		emailMessage.setToEmail(email);
		emailMessage.setFromEmail(loginUser.getEmail());
		emailMessage.setCreatedBy(loginUser.getUserCode());

		StringBuilder userNameSB = new StringBuilder();
		emailMessage.setFromName(
				userNameSB.append(loginUser.getFirstName()).append(" ").append(loginUser.getLastName()).toString());
		emailMessage.setEmailType(Constants.CUSTOM_USER_EMAIL);
		// setting the class filed values
		Map<String, String> globalVarsMap = new HashMap<>();
		globalVarsMap.put("loginUser", loginUser.getFullName());

		if (null != expenses.getExpenseId()) {
			globalVarsMap.put("Expense_Id", expenses.getExpenseId());
		}
		
		globalVarsMap.put("employeeName", projectMembers.getProjectMemberCandidteId().getFullName());
		globalVarsMap.put("projectName", projectMembers.getProjects().getProjectName());
		globalVarsMap.put("purpose", expenses.getPurpose());
		globalVarsMap.put("expense_StartDate", startDate);
		globalVarsMap.put("expense_EndDate", endDate);

		Set<ExpensesEntries> expensesEntriesList = expenses.getExpensesEntries();
		for (ExpensesEntries expList : expensesEntriesList) {
			globalVarsMap.put("approved_Amount", expenses.getTotalAmount().toString());
		}
		globalVarsMap.put("loginOrg", loginOrgLocation.getOrgLocationName());

		StringBuilder timeFrequencySB = new StringBuilder();

		globalVarsMap.put("manager_Name", firstName + " " + lastName);

		if ("REJECTED".equalsIgnoreCase(submissionStatusStr)) {
			globalVarsMap.put("reject_Reason", rejectedReason);
		}

		String redirectURL = "/expensesApprovalList/";
		Long redirectOrgLocationId = loginOrgLocation.getId();
		StringBuilder stringBuildforLink = new StringBuilder();
		globalVarsMap.put("loginURL",stringBuildforLink.append("<a href='").append(sourceleadLoginUrl+ "?redirectURL=" +redirectURL+"&redirectOrgLocationId="+redirectOrgLocationId).append("' style='text-decoration:none; '><div style=' width:220px; border-radius:30px; background-color:#214b90; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Login</div></a>").toString());

		if ("REJECTED".equalsIgnoreCase(submissionStatusStr)) {
			commonUtil.sendTemplateEmail(emailMessage, globalVarsMap, loginOrgLocation.getOrg(), loginOrgLocation,
					Constants.MAILTOMANAGER1WHENMANAGER2ORADMIN_REJECT_THE_EXPENSE, new ArrayList<EmailAttachment>());
		}
		if ("APPROVED".equalsIgnoreCase(submissionStatusStr)) {
			commonUtil.sendTemplateEmail(emailMessage, globalVarsMap, loginOrgLocation.getOrg(), loginOrgLocation,
					Constants.MAILTOMANAGER1WHENMANAGER2ORADMIN_APPROVESTHE_EXPENSE, new ArrayList<EmailAttachment>());
		}

		EmailScenario notificationScenario = null;
		String actionLink = "";
		StringBuilder notificationLink = new StringBuilder();
		if ("REJECTED".equalsIgnoreCase(submissionStatusStr)) {
			actionLink = notificationLink.append(sourceleadUrl).append("/").append("addApprovalExpense?expenseId="
					+ expenses.getExpenseId() + "&expenseLocationCode=" + expenses.getLocationsCode() + "&type=view")
					.toString();
			notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(
					Constants.MAILTOMANAGER1WHENMANAGER2ORADMIN_REJECT_THE_EXPENSE, Constants.ENTITY_TYPE_NOTIFICATION);
		}
		if ("APPROVED".equalsIgnoreCase(submissionStatusStr)) {
			actionLink = notificationLink.append(sourceleadUrl).append("/").append("addApprovalExpense?expenseId="
					+ expenses.getExpenseId() + "&expenseLocationCode=" + expenses.getLocationsCode() + "&type=view")
					.toString();

			notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(
					Constants.MAILTOMANAGER1WHENMANAGER2ORADMIN_APPROVESTHE_EXPENSE,
					Constants.ENTITY_TYPE_NOTIFICATION);
		}
		String notificationBody = emailScenerioUtil.buildTemplateBody(notificationScenario.getBody(), globalVarsMap,
				notificationScenario);
		notificationScenario.setBody(notificationBody);
		notificationScenario.setOrgCode(loginOrgLocation.getLocationCode());
		emailScenerioUtil.sendNotification(actionLink, loginUser, id, loginOrgLocation, notificationScenario,
				new HashMap<String, String>());

	}

	public void sendEmailNotificationToEmployee(Expenses expenses, String currencyCode, String firstName,
			String lastName, String toEmail, User loginUser, User toUser, OrgLocations loginOrgLocation,
			ProjectMembers projectMembers, String startDate, String endDate, String status, String rejectedReason)
			throws Exception {

		EmailMessage emailMessage = new EmailMessage();
		emailMessage.setFirstName(firstName);
		emailMessage.setLastName(lastName);
		emailMessage.setToEmail(toEmail);
		emailMessage.setFromEmail(loginUser.getEmail());
		emailMessage.setCreatedBy(loginUser.getUserCode());

		StringBuilder userNameSB = new StringBuilder();
		emailMessage.setFromName(
				userNameSB.append(loginUser.getFirstName()).append(" ").append(loginUser.getLastName()).toString());
		emailMessage.setEmailType(Constants.CUSTOM_USER_EMAIL);
		// setting the class filed values
		Map<String, String> globalVarsMap = new HashMap<>();

		// globalVarsMap.put("loginUserFirstName",loginUser.getFirstName());
		// globalVarsMap.put("loginUserLastName",loginUser.getLastName());

		if (null != loginUser.getLastName() && !"".equalsIgnoreCase(loginUser.getLastName())) {
			StringBuilder manager = new StringBuilder();
			globalVarsMap.put("managerUser",
					manager.append(loginUser.getFirstName()).append(" ").append(loginUser.getLastName()).toString());
		} else {
			globalVarsMap.put("managerUser", loginUser.getFirstName());
		}
		if (null != expenses.getExpenseId()) {
			globalVarsMap.put("Expense_Id", expenses.getExpenseId());
		}
		
		globalVarsMap.put("employeeUser", projectMembers.getProjectMemberCandidteId().getFullName());
		globalVarsMap.put("projectName", projectMembers.getProjects().getProjectName());
		globalVarsMap.put("expenseId", expenses.getExpenseId());
		globalVarsMap.put("frequencyType", projectMembers.getProjects().getInvoiceFrequency());
		globalVarsMap.put("startDate", startDate);
		globalVarsMap.put("endDate", endDate);

	
		Set<ExpensesEntries> expensesEntriesList = expenses.getExpensesEntries();
		for (ExpensesEntries expList : expensesEntriesList) {

			globalVarsMap.put("amount", expList.getSubmittedamount().toString());
			globalVarsMap.put("approvedAmount", expenses.getTotalAmount().toString());

		}
		

		Date todayDate = new Date();
		globalVarsMap.put("submitForApprovalDate",
				commonUtil.dateStrFromOrgDateFormatter(loginOrgLocation.getDateFormat(), todayDate));

		// globalVarsMap.put("employeeFirstName",sah.getExpenseId().getProjectMembers().getEmployee().getUser().getLastName());
		// globalVarsMap.put("employeeLastName",sah.getExpenseId().getProjectMembers().getEmployee().getUser().getLastName());

		globalVarsMap.put("loginOrg", loginOrgLocation.getOrgLocationName());

		StringBuilder timeFrequencySB = new StringBuilder();
		globalVarsMap.put("timeFrequency", timeFrequencySB.append(startDate).append(" to ").append(endDate).toString());

		globalVarsMap.put("managerFirstName", loginUser.getFirstName());
		globalVarsMap.put("managerLastName", loginUser.getLastName());

		if ("REJECTED".equalsIgnoreCase(status)) {
			globalVarsMap.put("rejectedReason", rejectedReason);
		}

		String redirectURL = "/expenses/";
		Long redirectOrgLocationId = loginOrgLocation.getId();

		StringBuilder stringBuildforLink = new StringBuilder();
		globalVarsMap.put("loginURL",stringBuildforLink.append("<a href='").append(sourceleadLoginUrl+ "?redirectURL=" +redirectURL+"&redirectOrgLocationId="+redirectOrgLocationId).append("' style='text-decoration:none; '><div style=' width:220px; border-radius:30px; background-color:#214b90; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Login</div></a>").toString());
		

		if ("REJECTED".equalsIgnoreCase(status)) {
			commonUtil.sendTemplateEmail(emailMessage, globalVarsMap, loginOrgLocation.getOrg(), loginOrgLocation,
					Constants.MAIL_TEAM_MANAGER_REJECTED_EXPENSE, new ArrayList<EmailAttachment>());
		}
		if ("APPROVED".equalsIgnoreCase(status)) {
			commonUtil.sendTemplateEmail(emailMessage, globalVarsMap, loginOrgLocation.getOrg(), loginOrgLocation,
					Constants.MAIL_TEAM_MANAGER_APPROVED_EXPENSE, new ArrayList<EmailAttachment>());
		}

		EmailScenario notificationScenario = null;
		String actionLink = "";
		StringBuilder notificationLink = new StringBuilder();
		if ("REJECTED".equalsIgnoreCase(status)) {
			actionLink = notificationLink.append(sourceleadUrl).append("/").append("expenses?expenseId="
					+ expenses.getExpenseId() + "&expenseLocationCode=" + expenses.getLocationsCode() + "&type=view")
					.toString();
			notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(
					Constants.MAIL_TEAM_MANAGER_REJECTED_EXPENSE, Constants.ENTITY_TYPE_NOTIFICATION);
		}
		if ("APPROVED".equalsIgnoreCase(status)) {
			actionLink = notificationLink.append(sourceleadUrl).append("/").append("expenses?expenseId="
					+ expenses.getExpenseId() + "&expenseLocationCode=" + expenses.getLocationsCode() + "&type=view")
					.toString();

			notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(
					Constants.MAIL_TEAM_MANAGER_APPROVED_EXPENSE, Constants.ENTITY_TYPE_NOTIFICATION);

		}
		// TODO @Rajashekar notificationScenario.getBody() may throw
		// NullPointerException please check it once
		String notificationBody = emailScenerioUtil.buildTemplateBody(notificationScenario.getBody(), globalVarsMap,
				notificationScenario);
		notificationScenario.setBody(notificationBody);
		notificationScenario.setOrgCode(loginOrgLocation.getLocationCode());
		// emailScenerioUtil.sendNotification(actionLink, loginUser,
		// projectMembers.getProjectMemberCandidteId().getConsultantUser(),
		// loginOrgLocation, notificationScenario,new HashMap<String,String>());
		emailScenerioUtil.sendNotification(actionLink, loginUser, toUser, loginOrgLocation, notificationScenario,
				new HashMap<String, String>());
	}

	// mail to manager When Manager Submits ExpenseBehalfofEmployee
	public void sendExpensesEmailToManagerWhenManagerSubmitsExpenseBehalfofEmployee(User loginUser, Expenses expenses,
			OrgLocations orgLoc, String currencyCode) throws Exception {

		EmailMessage emailMessage = new EmailMessage();
		if (null != expenses.getProjectManagerUserList()) {
			for (User managerUser : expenses.getProjectManagerUserList()) {

				if (managerUser.getEmail().equalsIgnoreCase(loginUser.getEmail()))
					continue;

				StringBuilder sb = new StringBuilder();
				emailMessage.setFirstName(managerUser.getFirstName());
				if (null != managerUser.getLastName()) {
					emailMessage.setLastName(managerUser.getLastName());
				}
				emailMessage.setToEmail(managerUser.getEmail());
				emailMessage.setFromEmail(loginUser.getEmail());
				if (null != loginUser.getLastName() && !"".equalsIgnoreCase(loginUser.getLastName())) {
					emailMessage.setFromName(
							sb.append(loginUser.getFirstName()).append(" ").append(loginUser.getLastName()).toString());
				} else {
					emailMessage.setFromName(loginUser.getFirstName());
				}
				emailMessage.setEmailType(Constants.CUSTOM_USER_EMAIL);

				emailMessage.setCreatedBy(loginUser.getUserCode());
				emailMessage.setOrgLocationId(orgLoc.getId());

				// setting the class filed values
				Map<String, String> globalVarsMap = new HashMap<String, String>();
				if (null != loginUser.getLastName() && !"".equalsIgnoreCase(loginUser.getLastName())) {
					StringBuilder employee = new StringBuilder();
					globalVarsMap.put("loginUser", employee.append(loginUser.getFirstName()).append(" ")
							.append(loginUser.getLastName()).toString());
				} else {
					globalVarsMap.put("loginUser", loginUser.getFirstName());
				}

				if (null != managerUser.getLastName() && !"".equalsIgnoreCase(managerUser.getLastName())) {
					StringBuilder manager = new StringBuilder();
					globalVarsMap.put("approver", manager.append(managerUser.getFirstName()).append(" ")
							.append(managerUser.getLastName()).toString());
				} else {
					globalVarsMap.put("approver", managerUser.getFirstName());
				}
				if (null != expenses.getExpenseId()) {
					globalVarsMap.put("Expense_Id", expenses.getExpenseId());
				}
				
				if (expenses.getProjectMembers().getMasterCandidateId() != null) {
					globalVarsMap.put("employeeName",
							expenses.getProjectMembers().getMasterCandidateId().getFirstName());
				}

				if (null != expenses.getProjectMembers()) {
					if (null != expenses.getProjectMembers().getProjects()) {
						globalVarsMap.put("projectName", expenses.getProjectMembers().getProjects().getProjectName());
					}
				}

				if (null != expenses.getPurpose()) {
					globalVarsMap.put("purpose", expenses.getPurpose());
				}

				if (null != expenses.getTotalsubmittedAmount()) {
					globalVarsMap.put("amount", expenses.getTotalsubmittedAmount().toString());
				}

				/*Set<ExpensesEntries> expensesEntries = expenses.getExpensesEntries();
				for (ExpensesEntries exp : expensesEntries) {

					globalVarsMap.put("amount", exp.getSubmittedamount().toString());

				}*/
				globalVarsMap.put("amount", expenses.getTotalAmount().toString());
				
				String expenseLocationCode = managerUser.getLocationCode();

				Long redirectOrgLocationId = managerUser.getOrgLocationId();
				
				String redirectURL = "/addApprovalExpense-params-expenseId=" + expenses.getExpenseId()
						+ "-and-expenseLocationCode=" + expenseLocationCode + "-and-type=view";

				StringBuilder stringBuildforLink = new StringBuilder();
				globalVarsMap.put("loginURL",stringBuildforLink.append("<a href='").append(sourceleadLoginUrl+ "?redirectURL=" +redirectURL+"&redirectOrgLocationId="+redirectOrgLocationId).append("' style='text-decoration:none; '><div style=' width:220px; border-radius:30px; background-color:#214b90; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Login</div></a>").toString());


				// getting the current EmailScenario by EmailScenario unique name and entity
				// type
				EmailScenario emailScenario = emailScenarioService.getEmailScenarioByUniqueName(
						Constants.MAILTO_LEVEL2_WHENLEVEL1_SUBMITSEXPENSE, Constants.ENTITY_TYPE_EMAIL);
				EmailScenario notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(
						Constants.MAILTO_LEVEL2_WHENLEVEL1_SUBMITSEXPENSE, Constants.ENTITY_TYPE_NOTIFICATION);

				commonUtil.sendTemplateEmail(emailMessage, globalVarsMap, orgLoc.getOrg(), orgLoc,
						Constants.MAILTO_LEVEL2_WHENLEVEL1_SUBMITSEXPENSE, new ArrayList<EmailAttachment>());

				String notificationBody = emailScenerioUtil.buildTemplateBody(notificationScenario.getBody(),
						globalVarsMap, emailScenario);
				notificationScenario.setBody(notificationBody);
				StringBuilder notificationLink = new StringBuilder();
				String actionLink = "";
				actionLink = notificationLink.append(sourceleadUrl).append("/")
						.append("addApprovalExpense?expenseId=" + expenses.getExpenseId() + "&expenseLocationCode="
								+ expenses.getLocationsCode() + "&type=view")
						.toString();

				emailScenerioUtil.sendNotification(actionLink, loginUser, managerUser, orgLoc, notificationScenario,
						new HashMap<String, String>());
			}
		}
	}

}
