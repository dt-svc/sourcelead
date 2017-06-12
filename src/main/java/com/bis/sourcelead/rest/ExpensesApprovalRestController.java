package com.bis.sourcelead.rest;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.internet.InternetAddress;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.entity.EmailAttachment;
import com.bis.sourcelead.entity.EmailMessage;
import com.bis.sourcelead.entity.EmailScenario;
import com.bis.sourcelead.entity.Expenses;
import com.bis.sourcelead.entity.ExpensesApproverHierarchy;
import com.bis.sourcelead.entity.ExpensesEntries;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.enums.Submission_Status;
import com.bis.sourcelead.service.AddExpensesService;
import com.bis.sourcelead.service.EmailScenarioService;
import com.bis.sourcelead.service.ExpensesApprovalService;
import com.bis.sourcelead.service.ExpensesEntriesService;
import com.bis.sourcelead.service.ExpensesService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.EmailScenerioUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;

@RestController
public class ExpensesApprovalRestController {

	@Autowired
	private CommonUtil commonUtil;
	
	@Autowired
	private ExpensesApprovalService expensesApprovalService;
	
	@Autowired
	private ExpensesService expensesService;
	
	@Autowired
	private ExpensesEntriesService expensesEntriesService;
	
	
	@Autowired
	private EmailScenarioService emailScenarioService;
	
	@Autowired
	private AddExpensesService addExpensesService;
	
	@Autowired
	EmailScenerioUtil emailScenerioUtil;
	
	@Value("${sourceleadUrl}")
    private String sourceleadUrl;
	
	@RequestMapping(value = "/getExpensesApprovalList")
	public String getExpensesApprovalList(HttpServletRequest request) throws Exception {
		User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations orgLoc = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		List<ExpensesApproverHierarchy> expApperoverHierarchy = expensesApprovalService.getExpensesApproverHierarchyByApproverUserIdAndOrgLocationId(loginUser, orgLoc);
			return commonUtil.toJSON(expApperoverHierarchy);
   }
	
	
	@RequestMapping(value = "/getExpensesApprovalDetailsByExpenseId")
	public String getExpensesApprovalListByExpenseId(@RequestParam(value="expenseId", required=false) String expenseId) throws Exception {
		Expenses expenses = expensesService.getExpenseByExpenseId(Long.parseLong(expenseId));
		expenses.setStartDateStr(Constants.DMY.format(expenses.getStartDate()));
		if(null != expenses.getEndDate()){
		expenses.setEndDateStr(Constants.DMY.format(expenses.getEndDate()));
		}	
		return commonUtil.toJSON(expenses);
   }
	
	
	@RequestMapping(value = "/getExpensesApprovalCategoryByExpenseId")
	public String getExpensesApprovalCategoryByExpenseId(@RequestParam(value="expenseId", required=false) String expenseId) throws Exception {
		List<ExpensesEntries> expensesEntries = expensesEntriesService.getExpensesEntriesByExpenseId(Long.parseLong(expenseId));
		for(ExpensesEntries expEntries:expensesEntries){
			expEntries.setExpenseDateString(Constants.DMY.format(expEntries.getExpenseDate()));
		}
		return commonUtil.toJSON(expensesEntries);
   }
	
	
	
	
	
	
    @RequestMapping(value = "/saveExpenseApprovalStatusByExpenseId", method = RequestMethod.POST)
    public  String saveExpenseApprovalStatusByExpenseId(HttpServletRequest request,@RequestParam(value="expenseId", required=false) Long expenseId,
                                                @RequestParam(value="approvalStatus", required=false) String approvalStatus,@RequestParam(value="rejectedReason", required=false) String rejectedReason) throws Exception{
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations orgLoc = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
	 	expensesApprovalService.updateApprovalStatusByApproverForExpense(expenseId, user.getId(),approvalStatus, rejectedReason);
	 	
	 	sendEmailToExpenseApprovalStatus(expenseId, user,approvalStatus,rejectedReason,orgLoc,orgLoc.getOrg().getOrgCode());
	 	
        return "success";
    }


	private void sendEmailToExpenseApprovalStatus(Long expenseId, User loginUser, String status, String rejectedReason, OrgLocations loginOrgLocation, String loginOrgCode) throws Exception {
		
		ExpensesApproverHierarchy sah = expensesApprovalService.getExpensesApproverHierarchyByApproverUserIdAndExpenseId(loginUser.getId(), expenseId);
		 Submission_Status submissionStatus = sah.getExpenseStatus();
	 	 String submissionStatusStr = submissionStatus.getValue();
	 	  if(StringUtils.isNotEmpty(submissionStatusStr) && 
	 			 (submissionStatusStr.equalsIgnoreCase(Submission_Status.getNameByValue("approved").getValue())
	 		     || submissionStatusStr.equalsIgnoreCase(Submission_Status.getNameByValue("rejected").getValue()))
	 	    )
	 	   {
	 		 EmailMessage emailMessage = new EmailMessage();
	     	 emailMessage.setFirstName(sah.getExpenseId().getProjectMembers().getEmployee().getUser().getFirstName());
		 	 emailMessage.setLastName(sah.getExpenseId().getProjectMembers().getEmployee().getUser().getLastName());
		 	 emailMessage.setToEmail(sah.getExpenseId().getProjectMembers().getEmployee().getUser().getEmail());
		 	 emailMessage.setFromEmail(loginUser.getEmail());
		 	 emailMessage.setCreatedBy(loginUser.getUserCode());
		 	 
		 	 StringBuilder userNameSB = new StringBuilder();
		 	 emailMessage.setFromName(userNameSB.append(loginUser.getFirstName()).append(" ").append(loginUser.getLastName()).toString());
		 	emailMessage.setEmailType(Constants.CUSTOM_USER_EMAIL);
			 //setting the class filed values
			 Map<String, String> globalVarsMap = new HashMap<>();
			
			// globalVarsMap.put("loginUserFirstName",loginUser.getFirstName());
			// globalVarsMap.put("loginUserLastName",loginUser.getLastName());
			 
			 
			 if(null != loginUser.getLastName() && !"".equalsIgnoreCase(loginUser.getLastName())){
				 StringBuilder manager =new StringBuilder();
				 globalVarsMap.put("managerUser", manager.append(loginUser.getFirstName()).append(" ").append(loginUser.getLastName()).toString());
			 }else{
				 globalVarsMap.put("managerUser",loginUser.getFirstName());
			 }
			 
			 if(null != sah.getExpenseId().getProjectMembers().getEmployee().getUser().getLastName() && !"".equalsIgnoreCase(sah.getExpenseId().getProjectMembers().getEmployee().getUser().getLastName())){
				 StringBuilder employee =new StringBuilder();
				 globalVarsMap.put("employeeUser", employee.append(sah.getExpenseId().getProjectMembers().getEmployee().getUser().getFirstName()).append(" ").append(sah.getExpenseId().getProjectMembers().getEmployee().getUser().getLastName()).toString());
			 }else{
				 globalVarsMap.put("employeeUser",sah.getExpenseId().getProjectMembers().getEmployee().getUser().getFirstName());
			 }
			 
			 
			 if(null !=sah.getExpenseId()){
				 if(null != sah.getExpenseId().getProjectMembers()){
					 if(null != sah.getExpenseId().getProjectMembers().getProjects()){
				      globalVarsMap.put("projectName",sah.getExpenseId().getProjectMembers().getProjects().getProjectName());
				 }
				}
			 }
			 
			 if(null != sah.getExpenseId()){
				 if(null != sah.getExpenseId().getExpenseId()){
			 globalVarsMap.put("expenseId",sah.getExpenseId().getExpenseId());
				 }
			 }
			 
			 if(null != sah.getExpenseId()){
				 if(null != sah.getExpenseId().getStartDate()){
			 globalVarsMap.put("startDate",Constants.DMY.format(sah.getExpenseId().getStartDate()));
				 }
			 }	 
			 if(null != sah.getExpenseId()){
				 if(null != sah.getExpenseId().getEndDate()){
			 globalVarsMap.put("endDate", Constants.DMY.format(sah.getExpenseId().getEndDate()));
				 }
			 }
			 globalVarsMap.put("amount",Float.toString(sah.getExpenseId().getTotalAmount()));
			 
			Date todayDate = new Date();
			 globalVarsMap.put("submitForApprovalDate",Constants.DMY.format(todayDate));
			
			// globalVarsMap.put("employeeFirstName",sah.getExpenseId().getProjectMembers().getEmployee().getUser().getLastName());
			// globalVarsMap.put("employeeLastName",sah.getExpenseId().getProjectMembers().getEmployee().getUser().getLastName());
			
			 globalVarsMap.put("orgName",loginOrgLocation.getOrg().getOrgName());
			 
			 
			 StringBuilder timeFrequencySB = new StringBuilder();
			 globalVarsMap.put("timeFrequency",timeFrequencySB.append(sah.getExpenseId().getStartDate()).append(" to ").append(sah.getExpenseId().getEndDate()).toString());
			 
			 globalVarsMap.put("managerFirstName", loginUser.getFirstName());
			 globalVarsMap.put("managerLastName", loginUser.getLastName());
			 
			 if("REJECTED".equalsIgnoreCase(status)){
				 globalVarsMap.put("rejectedReason",rejectedReason);
			 }
			 
			 StringBuilder loginURLSB = new StringBuilder();
			 globalVarsMap.put("loginURL",loginURLSB.append("<a href='").append(sourceleadUrl).append("/").append("'style='text-decoration:none;'><div style=' width:220px; border-radius:30px; background-color:#214b90; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Login</div></a>").toString());
			 
			 //setting the Recipient information
			 InternetAddress[] iAdressArray = InternetAddress.parse(emailMessage.getToEmail());
			 
			 //getting the current EmailScenario by EmailScenario unique name and entity type
			 EmailScenario  emailScenario = null;
			 if("REJECTED".equalsIgnoreCase(status)){
				 emailScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.MAIL_TEAM_MANAGER_REJECTED_EXPENSE,Constants.ENTITY_TYPE_EMAIL);
			 }
			 if("APPROVED".equalsIgnoreCase(status)){
				 emailScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.MAIL_TEAM_MANAGER_APPROVED_EXPENSE,Constants.ENTITY_TYPE_EMAIL);
			 }
			 
			
			 // TODO @Rajashekar notificationScenario.getBody() may throw NullPointerException please check it once 
			 String subject = emailScenerioUtil.bulidSubject(emailScenario.getSubject(),globalVarsMap);
			 String body = emailScenerioUtil.buildTempBody(emailScenario.getBody(),globalVarsMap);
			 String signature = emailScenerioUtil.buildSignature(emailScenario.getSignature(),globalVarsMap);
			 String url = sourceleadUrl;
			 String emailTemplate = emailScenerioUtil.AssembleEmailMessage(emailScenario,emailMessage, subject, body, signature, url, loginOrgLocation.getOrg());
			 emailMessage.setSubject(subject);
			 emailMessage.setMessageBody(emailTemplate);
			 emailScenerioUtil.sendEmail(emailMessage,loginOrgLocation.getId(),iAdressArray,new ArrayList<EmailAttachment>());
			 EmailScenario  notificationScenario = null;
			 if("REJECTED".equalsIgnoreCase(status)){
				 notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.MAIL_TEAM_MANAGER_REJECTED_EXPENSE,Constants.ENTITY_TYPE_NOTIFICATION);
			 }
			 if("APPROVED".equalsIgnoreCase(status)){
				 notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.MAIL_TEAM_MANAGER_APPROVED_EXPENSE,Constants.ENTITY_TYPE_NOTIFICATION);
			 }
			 // TODO @Rajashekar notificationScenario.getBody() may throw NullPointerException please check it once 
			String notificationBody = emailScenerioUtil.buildBody(notificationScenario.getBody(),globalVarsMap);
			notificationScenario.setBody(notificationBody);
			notificationScenario.setOrgCode(loginOrgCode);
			String actionLink = "";
			emailScenerioUtil.sendNotification(actionLink, loginUser, sah.getExpenseId().getProjectMembers().getEmployee().getUser(), loginOrgLocation, notificationScenario); 
	 	  }
		
	}
	
}
