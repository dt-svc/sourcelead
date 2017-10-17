package com.bis.sourcelead.rest;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.internet.InternetAddress;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.FileCopyUtils;
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
import com.bis.sourcelead.entity.ProjectApproverHierarchy;
import com.bis.sourcelead.entity.ProjectMembers;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.enums.Submission_Status;
import com.bis.sourcelead.service.AddExpensesService;
import com.bis.sourcelead.service.EmailScenarioService;
import com.bis.sourcelead.service.ExpensesApprovalService;
import com.bis.sourcelead.service.ExpensesEntriesService;
import com.bis.sourcelead.service.ExpensesService;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.ProjectApproverHierarchyService;
import com.bis.sourcelead.service.ProjectMembersService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.EmailScenerioUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;
import com.ibm.icu.math.BigDecimal;

@RestController
public class ExpensesApprovalRestController {

	@Autowired
	private CommonUtil commonUtil;
	
	@Autowired
	private ExpensesApprovalService expensesApprovalService;
	
	@Autowired
	private ProjectMembersService projectMembersService;
	
	
	@Autowired
	private ExpensesService expensesService;
	
	@Autowired
	private ExpensesEntriesService expensesEntriesService;
	
	
	@Autowired
	private EmailScenarioService emailScenarioService;
	
	
	@Autowired
	OrgLocationService orgLocationService;
	
	@Autowired
	EmailScenerioUtil emailScenerioUtil;
	
	
	@Autowired
	private ProjectApproverHierarchyService projectApproverHierarchyService;
	
	@Value("${sourceleadUrl}")
    private String sourceleadUrl;
	
	@RequestMapping(value = "/getExpensesApprovalList")
	public List<ExpensesApproverHierarchy> getExpensesApprovalList(HttpServletRequest request, String expenseStatus, String startDate, String endDate) throws Exception {
		User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations orgLoc = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		List<ExpensesApproverHierarchy> expApperoverHierarchy = expensesApprovalService.getExpensesApproverHierarchyByApproverUserIdAndOrgLocationId(loginUser, orgLoc, expenseStatus, startDate, endDate);
			return expApperoverHierarchy;
   }
	
	@RequestMapping(value = "/getExpensesApprovalListForExports")
	public void getExpensesApprovalListForExports(HttpServletRequest request,HttpServletResponse response,@RequestParam(value="type", required=false) String type,@RequestParam(value="expenseStatus", required=false) String expenseStatus,@RequestParam(value="startDate", required=false) String startDate,@RequestParam(value="endDate", required=false) String endDate) throws Exception {
		User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations orgLoc = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		File file=null;
		List<ExpensesApproverHierarchy> expApperoverHierarchy = expensesApprovalService.getExpensesApproverHierarchyByApproverUserIdAndOrgLocationId(loginUser, orgLoc, expenseStatus, startDate, endDate);
		if(type.equalsIgnoreCase("pdf"))
			file = expensesApprovalService.getExpensesApproverHierarchyListForPdf(expApperoverHierarchy,orgLoc,loginUser);
		else
			file = expensesApprovalService.getExpensesApproverHierarchyListForExcel(expApperoverHierarchy,orgLoc,loginUser);
		String mimeType = URLConnection.guessContentTypeFromName(file.getName());
		if (mimeType == null) {
			mimeType = "application/octet-stream";
		}
		response.setContentType(mimeType);
		StringBuilder fileName = new StringBuilder();
		response.setHeader("Content-Disposition", fileName.append("attachment; filename=\"").append(file.getName()).append("\"").toString());
		response.setContentLength((int) file.length());
		InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
		FileCopyUtils.copy(inputStream, response.getOutputStream());
		inputStream.close();
		response.getOutputStream().close();
		response.getOutputStream().flush();	
		if(file != null){
			file.delete();
		}
   }
	
	@RequestMapping(value = "/getExpensesApprovalDetailsByExpenseId")
	public String getExpensesApprovalListByExpenseId(@RequestParam(value="expenseId", required=false) String expenseId) throws Exception {
		Expenses expenses = expensesService.getExpenseById(Long.parseLong(expenseId));
		expenses.setStartDateStr(commonUtil.dateStrFromOrgDateFormatter(expenses.getOrgLocationId().getDateFormat(),expenses.getStartDate()));
		if(null != expenses.getEndDate()){
		expenses.setEndDateStr(commonUtil.dateStrFromOrgDateFormatter(expenses.getOrgLocationId().getDateFormat(),expenses.getEndDate()));
		}	
		return commonUtil.toJSON(expenses);
   }
	
	
	@RequestMapping(value = "/getExpensesApprovalCategoryByExpenseId")
	public String getExpensesApprovalCategoryByExpenseId(@RequestParam(value="expenseId", required=false) String expenseId,@RequestParam(value="locationCode") String locationCode) throws Exception {
		List<ExpensesEntries> expensesEntries = expensesEntriesService.getExpensesEntriesByExpenseId(Long.parseLong(expenseId));
		
		 OrgLocations orglo = orgLocationService.getOrgLocationsByLocationCode(locationCode);
	        //SimpleDateFormat formatter = orglo.getLocFullFormDateFormatter();
		
		for(ExpensesEntries expEntries:expensesEntries){
			expEntries.setExpenseDateString(commonUtil.dateStrFromOrgDateFormatter(orglo.getDateFormat(),expEntries.getExpenseDate()));
		}
		return commonUtil.toJSON(expensesEntries);
   }
	
	
	
	
	
	
    @RequestMapping(value = "/saveExpenseApprovalStatusByExpenseId", method = RequestMethod.POST)
    public  String saveExpenseApprovalStatusByExpenseId(HttpServletRequest request,@RequestParam(value="expenseId", required=false) Long expenseId,
                                                @RequestParam(value="approvalStatus", required=false) String approvalStatus,@RequestParam(value="rejectedReason", required=false) String rejectedReason) throws Exception{
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations orgLoc = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		 String currencyCode = (String) SourceLeadSessionManager.getSessionAttribute(request, "currencyCode");
	 	expensesApprovalService.updateApprovalStatusByApproverForExpense(expenseId, user.getId(),approvalStatus, rejectedReason);
	 	ExpensesApproverHierarchy eah = expensesApprovalService.getExpensesApproverHierarchyByApproverUserIdAndExpenseId(user.getId(), expenseId, orgLoc.getId());
	 	if(eah != null && eah.getExpenseId() != null){
	 		 String submissionStatusStr = eah.getExpenseId().getExpenseStatus().getValue();
	 		 if(StringUtils.isNotEmpty(submissionStatusStr) && 
		 			 (submissionStatusStr.equalsIgnoreCase(Submission_Status.getNameByValue("approved").getValue())
		 		     || submissionStatusStr.equalsIgnoreCase(Submission_Status.getNameByValue("rejected").getValue()))
		 	    )
		 	   {
	 			    ProjectMembers projectMembers = projectMembersService.getConsultantUserProjectMemberByProjectMemberId(eah.getExpenseId().getProjectMembers().getId());
	 			    DateFormat TZ_DF_TS = new SimpleDateFormat (orgLoc.getDateFormat());
	 	            String startDate = commonUtil.dateStrFromOrgDateFormatter(orgLoc.getDateFormat(),eah.getExpenseId().getStartDate());
	 	            String endDate = commonUtil.dateStrFromOrgDateFormatter(orgLoc.getDateFormat(),eah.getExpenseId().getEndDate());
	 			    //For Employee
			 		sendEmailNotificationToEmployeeAndApproversOfEndClient(eah.getExpenseId(),currencyCode,projectMembers.getProjectMemberCandidteId().getConsultantUser().getFirstName(), projectMembers.getProjectMemberCandidteId().getConsultantUser().getLastName(), projectMembers.getProjectMemberCandidteId().getConsultantUser().getEmail(), user, orgLoc, projectMembers, startDate, endDate, submissionStatusStr, rejectedReason);
			 		 
			 		//For Approvers of EndClient
			 		 List<ProjectApproverHierarchy> approverList =  projectApproverHierarchyService.getProjectsHierarchyListByProjectIdAndOrgLocationId(projectMembers.getProjects().getId(), orgLoc.getId());
			 		 if(approverList != null && approverList.isEmpty()){
			 			 for(ProjectApproverHierarchy  approver : approverList){
			 				if(!user.getUserCode().equalsIgnoreCase(approver.getApproverUserId().getUserCode())){
			 					sendEmailNotificationToEmployeeAndApproversOfEndClient(eah.getExpenseId(),currencyCode,approver.getApproverUserId().getFirstName(), approver.getApproverUserId().getLastName(), approver.getApproverUserId().getEmail(), user, orgLoc, projectMembers, startDate, endDate, submissionStatusStr, rejectedReason);
			 				}
			 			 }
			 		 }
			 		sendEmailToVendorAtExpensesApprovalStatus(eah.getExpenseId().getTotalAmount(),projectMembers,user,orgLoc,startDate, endDate,rejectedReason,currencyCode,approvalStatus);
		 	   }
	 	}
	 	
	 	
	 	
	 	
	 	
	 	
        return "success";
    }

   
	private void sendEmailToVendorAtExpensesApprovalStatus(Float amount,ProjectMembers projectMembers, User user,OrgLocations orgLoc,String startDateStr,String endDateStr,
			String rejectedReason,String currencyCode, String status) throws Exception {
		
		 // Expenses expense = expensesService.getExpenseById(expenseId);
		
		List<ProjectApproverHierarchy> approverHierarchyList = projectApproverHierarchyService.getActiveOrgApproverUsersByProjectMemberMasterCandidateIdAndProjectId(projectMembers.getMasterCandidateId().getId(),projectMembers.getProjects().getId());
		for(ProjectApproverHierarchy approver : approverHierarchyList){
				
		    if(approver.getProjectClientsId()!= null && 
		    		approver.getProjectClientsId().getOrgLocationsId() != null && 
		    		!orgLoc.getLocationCode().equalsIgnoreCase(approver.getProjectClientsId().getOrgLocationsId().getLocationCode())){
				
		     EmailMessage emailMessage = new EmailMessage();
			 emailMessage.setFirstName(approver.getApproverUserId().getFirstName());
			 emailMessage.setLastName(approver.getApproverUserId().getLastName());
			 emailMessage.setToEmail(approver.getApproverUserId().getEmail());
			 emailMessage.setFromEmail(user.getEmail());
			 emailMessage.setCreatedBy(user.getUserCode());
//			 emailMessage.setFromName(loginUser.getFirstName()+" "+loginUser.getLastName());
			 
			 /*String pattern = "dd-MM-yyyy";
	         SimpleDateFormat format = new SimpleDateFormat(pattern);*/
			

			 //setting the class filed values
			 Map<String, String> globalVarsMap = new HashMap<String, String>();
//			 globalVarsMap.put("loginUserFirstName",loginUser.getFirstName());
//			 globalVarsMap.put("loginUserLastName",loginUser.getLastName());
		     globalVarsMap.put("employeename",projectMembers.getProjectMemberCandidteId().getConsultantUser().getFullName());
			
//			 globalVarsMap.put("managerFirstName",projectManager.getFirstName());
//			 globalVarsMap.put("managerLastName",projectManager.getLastName());
			 //globalVarsMap.put("projectName", project.getProjectName());
			 globalVarsMap.put("projectname",projectMembers.getProjects().getProjectName());
			 globalVarsMap.put("expensesduration",startDateStr+" to "+endDateStr);
			 globalVarsMap.put("projectname",user.getFirstName()+(orgLoc.getOrgLocationName()));
			 
			 StringBuilder loginURLSB = new StringBuilder();
			 globalVarsMap.put("loginURL",loginURLSB.append("<a href='").append(sourceleadUrl).append("/").append("'style='text-decoration:none;'><div style=' width:220px; border-radius:30px; background-color:#214b90; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Login</div></a>").toString());
			 globalVarsMap.put("loginlink",loginURLSB.toString());
			 StringBuilder amountCurrencyType = new StringBuilder();
			 BigDecimal amountCurrencyType1 = new BigDecimal(Double.toString(amount));
			 amountCurrencyType1 = amountCurrencyType1.setScale(2,BigDecimal.ROUND_HALF_UP );
			 
			 
			 amountCurrencyType.append(amountCurrencyType1).append(" ").append(currencyCode);
			 globalVarsMap.put("amount",amountCurrencyType1.toString());
			 globalVarsMap.put("reason",rejectedReason);
			 
			 //setting the Recipient information
			 InternetAddress[] iAdressArray = InternetAddress.parse(emailMessage.getToEmail());
			 
			 //getting the current EmailScenario by EmailScenario unique name and entity type
			 EmailScenario  emailScenario = null;
			 if(status.equals("REJECTED")){
	             emailScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.EXPENSES_REJECTED_EMAIL_TOVENDOR,Constants.ENTITY_TYPE_EMAIL);
			 }else{
				 emailScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.EXPENSES_APPROVED_EMAIL_TOVENDOR,Constants.ENTITY_TYPE_EMAIL);
			 }
			 
			 //covert subject,body and signature
			 String subject = emailScenerioUtil.bulidSubject(emailScenario.getSubject(),globalVarsMap);
			 String body = emailScenerioUtil.buildTempBody(emailScenario.getBody(),globalVarsMap);
			 String signature = emailScenerioUtil.buildSignature(emailScenario.getSignature(),globalVarsMap);
			 String url = sourceleadUrl;
			 
			 //construct HTML template using required data
			 String emailTemplate = emailScenerioUtil.AssembleEmailMessage(emailScenario,emailMessage, subject, body, signature, url, orgLoc.getOrg(),orgLoc);
			 emailMessage.setSubject(subject);
			 emailMessage.setMessageBody(emailTemplate);
			 
			 //calling the sendEmail method of emailScenerioUtil
			 emailScenerioUtil.sendEmail(emailMessage,orgLoc.getId(),iAdressArray,new ArrayList<EmailAttachment>());
			 
			 
			//calling the sendNotification method of emailScenerioUtil
			EmailScenario  notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.EXPENSES_APPROVED_EMAIL_TOVENDOR,Constants.ENTITY_TYPE_NOTIFICATION);
			String notificationBody = emailScenerioUtil.buildBody(notificationScenario.getBody(),globalVarsMap);
			notificationScenario.setBody(notificationBody);
			notificationScenario.setOrgCode(orgLoc.getLocationCode());
			//String url = null;
			String actionLink = "";
			emailScenerioUtil.sendNotification(actionLink, user, approver.getApproverUserId(), orgLoc, notificationScenario);
			
		    }
		}
	}
    private void sendEmailNotificationToEmployeeAndApproversOfEndClient(Expenses expenses,String currencyCode,String firstName,String lastName,String toEmail,User loginUser,OrgLocations loginOrgLocation,ProjectMembers projectMembers,String startDate, String endDate,String status,String rejectedReason) throws Exception{
		 EmailMessage emailMessage = new EmailMessage();
    	 emailMessage.setFirstName(firstName);
	 	 emailMessage.setLastName(lastName);
	 	 emailMessage.setToEmail(toEmail);
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
		 
		 globalVarsMap.put("employeeUser",projectMembers.getProjectMemberCandidteId().getConsultantUser().getFullName());
		 globalVarsMap.put("projectName",projectMembers.getProjects().getProjectName());
		 globalVarsMap.put("expenseId",expenses.getExpenseId());
		 globalVarsMap.put("frequencyType",projectMembers.getProjects().getInvoiceFrequency());
		 globalVarsMap.put("startDate",startDate);
		 globalVarsMap.put("endDate", endDate);
		 
		 StringBuilder amountCurrencyType = new StringBuilder();
		 BigDecimal amountCurrencyType1 = new BigDecimal(Double.toString(expenses.getTotalAmount()));
		 amountCurrencyType1 = amountCurrencyType1.setScale(2,BigDecimal.ROUND_HALF_UP );
		 
		 
		 amountCurrencyType.append(amountCurrencyType1).append(" ").append(currencyCode);
		 globalVarsMap.put("amount",amountCurrencyType1.toString());

		 
		Date todayDate = new Date();
		 globalVarsMap.put("submitForApprovalDate",commonUtil.dateStrFromOrgDateFormatter(loginOrgLocation.getDateFormat(),todayDate));
		
		// globalVarsMap.put("employeeFirstName",sah.getExpenseId().getProjectMembers().getEmployee().getUser().getLastName());
		// globalVarsMap.put("employeeLastName",sah.getExpenseId().getProjectMembers().getEmployee().getUser().getLastName());
		
		 globalVarsMap.put("loginOrg",loginOrgLocation.getOrgLocationName());
		 
		 
		 StringBuilder timeFrequencySB = new StringBuilder();
		 globalVarsMap.put("timeFrequency",timeFrequencySB.append(startDate).append(" to ").append(endDate).toString());
		 
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
		 // TODO @Shivayogi  i have checked it,its working fine . if migration is not there it will throw null pointer.
		 String subject = emailScenerioUtil.bulidSubject(emailScenario.getSubject(),globalVarsMap);
		 String body = emailScenerioUtil.buildTempBody(emailScenario.getBody(),globalVarsMap);
		 String signature = emailScenerioUtil.buildSignature(emailScenario.getSignature(),globalVarsMap);
		 String url = sourceleadUrl;
		 String emailTemplate = emailScenerioUtil.AssembleEmailMessage(emailScenario,emailMessage, subject, body, signature, url, loginOrgLocation.getOrg(),loginOrgLocation);
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
		String notificationBody = emailScenerioUtil.buildBody(notificationScenario.getSubject(),globalVarsMap);
		notificationScenario.setBody(notificationBody);
		notificationScenario.setOrgCode(loginOrgLocation.getLocationCode());
		String actionLink = "";
		emailScenerioUtil.sendNotification(actionLink, loginUser, projectMembers.getProjectMemberCandidteId().getConsultantUser(), loginOrgLocation, notificationScenario);  
   }
	
	
}
