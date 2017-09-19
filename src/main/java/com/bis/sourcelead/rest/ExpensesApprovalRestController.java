package com.bis.sourcelead.rest;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URLConnection;
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
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.enums.Submission_Status;
import com.bis.sourcelead.service.AddExpensesService;
import com.bis.sourcelead.service.EmailScenarioService;
import com.bis.sourcelead.service.ExpensesApprovalService;
import com.bis.sourcelead.service.ExpensesEntriesService;
import com.bis.sourcelead.service.ExpensesService;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.EmailScenerioUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;
import com.bis.sourcelead.web.LoginSessionController;
import com.ibm.icu.math.BigDecimal;

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
	OrgLocationService orgLocationService;
	
	@Autowired
	EmailScenerioUtil emailScenerioUtil;
	
	@Value("${sourceleadUrl}")
    private String sourceleadUrl;
	
	@RequestMapping(value = "/getExpensesApprovalList")
	public String getExpensesApprovalList(HttpServletRequest request, String expenseStatus, String startDate, String endDate) throws Exception {
		User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations orgLoc = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		List<ExpensesApproverHierarchy> expApperoverHierarchy = expensesApprovalService.getExpensesApproverHierarchyByApproverUserIdAndOrgLocationId(loginUser, orgLoc, expenseStatus, startDate, endDate);
			return commonUtil.toJSON(expApperoverHierarchy);
   }
	
	@RequestMapping(value = "/getExpensesApprovalListForExports")
	public void getExpensesApprovalListForExports(HttpServletRequest request,HttpServletResponse response,@RequestParam(value="type", required=false) String type,@RequestParam(value="expenseStatus", required=false) String expenseStatus,@RequestParam(value="startDate", required=false) String startDate,@RequestParam(value="endDate", required=false) String endDate) throws Exception {
		User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations orgLoc = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		File file=null;
		List<ExpensesApproverHierarchy> expApperoverHierarchy = expensesApprovalService.getExpensesApproverHierarchyByApproverUserIdAndOrgLocationId(loginUser, orgLoc, expenseStatus, startDate, endDate);
		if(type.equalsIgnoreCase("pdf"))
			file = expensesApprovalService.getExpensesApproverHierarchyListForPdf(expApperoverHierarchy,orgLoc);
		else
			file = expensesApprovalService.getExpensesApproverHierarchyListForExcel(expApperoverHierarchy,orgLoc);
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
	 	expensesApprovalService.updateApprovalStatusByApproverForExpense(expenseId, user.getId(),approvalStatus, rejectedReason);
	 	
	 	List<ExpensesApproverHierarchy> expensesApproverHierarchyList = expensesApprovalService.getExpensesApproverHierarchyListByExpenseId(expenseId);
	 	if(expensesApproverHierarchyList != null &&!expensesApproverHierarchyList.isEmpty()){
			 Integer maxLevel = 0;
			 Integer userLevel = 0;
			 Boolean isAdmin =  (Boolean) SourceLeadSessionManager.getSessionAttribute(request,"isAdmin");
			 String currencyCode = (String) SourceLeadSessionManager.getSessionAttribute(request, "currencyCode");
			 

			 if(isAdmin){
				 sendEmailToExpenseApprovalStatus(expenseId, user,approvalStatus,rejectedReason,orgLoc,orgLoc.getOrg().getOrgCode(),currencyCode);
			 }
			 else{
				 for(ExpensesApproverHierarchy eah :expensesApproverHierarchyList){
					 if(eah.getHierarchyId() != null && eah.getHierarchyId().getLevel() != null && maxLevel < eah.getHierarchyId().getLevel()){
						 maxLevel = eah.getHierarchyId().getLevel();
					 }
					 if(eah.getHierarchyId() != null && eah.getHierarchyId().getApproverUserId() != null 
					    && eah.getHierarchyId().getApproverUserId().getId() != null && (eah.getHierarchyId().getApproverUserId().getId().equals(user.getId()))){
						 userLevel = eah.getHierarchyId().getLevel();
					 }
				 }
				 if(maxLevel.equals(userLevel) || ((userLevel == 1) && (approvalStatus.equalsIgnoreCase("REJECTED")))){
					 sendEmailToExpenseApprovalStatus(expenseId, user,approvalStatus,rejectedReason,orgLoc,orgLoc.getOrg().getOrgCode(),currencyCode);
				 }
			 }
			 
		 }
	 	
	 	
        return "success";
    }


	private void sendEmailToExpenseApprovalStatus(Long expenseId, User loginUser, String status, String rejectedReason, OrgLocations loginOrgLocation, String loginOrgCode, String currencyCode) throws Exception {
		
		ExpensesApproverHierarchy sah = expensesApprovalService.getExpensesApproverHierarchyByApproverUserIdAndExpenseId(loginUser.getId(), expenseId);
		//SimpleDateFormat dateFormat=loginOrgLocation.getLocFullFormDateFormatter();
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
			 globalVarsMap.put("frequencyType",sah.getExpenseId().getProjectMembers().getProjects().getInvoiceFrequency());
			 if(null != sah.getExpenseId()){
				 if(null != sah.getExpenseId().getStartDate()){
			 globalVarsMap.put("startDate",commonUtil.dateStrFromOrgDateFormatter(loginOrgLocation.getDateFormat(),sah.getExpenseId().getStartDate()));
				 }
			 }	 
			 if(null != sah.getExpenseId()){
				 if(null != sah.getExpenseId().getEndDate()){
			 globalVarsMap.put("endDate", commonUtil.dateStrFromOrgDateFormatter(loginOrgLocation.getDateFormat(),sah.getExpenseId().getEndDate()));
				 }
			 }
			 
			 StringBuilder amountCurrencyType = new StringBuilder();
			 BigDecimal amountCurrencyType1 = new BigDecimal(Double.toString(sah.getExpenseId().getTotalAmount()));
			 amountCurrencyType1 = amountCurrencyType1.setScale(2,BigDecimal.ROUND_HALF_UP );
			 
			 
			 amountCurrencyType.append(amountCurrencyType1).append(" ").append(currencyCode);
			 globalVarsMap.put("amount",amountCurrencyType1.toString());
	
			 
			Date todayDate = new Date();
			 globalVarsMap.put("submitForApprovalDate",commonUtil.dateStrFromOrgDateFormatter(loginOrgLocation.getDateFormat(),todayDate));
			
			// globalVarsMap.put("employeeFirstName",sah.getExpenseId().getProjectMembers().getEmployee().getUser().getLastName());
			// globalVarsMap.put("employeeLastName",sah.getExpenseId().getProjectMembers().getEmployee().getUser().getLastName());
			
			 globalVarsMap.put("loginOrg",loginOrgLocation.getOrgLocationName());
			 
			 
			 StringBuilder timeFrequencySB = new StringBuilder();
			 globalVarsMap.put("timeFrequency",timeFrequencySB.append(commonUtil.dateStrFromOrgDateFormatter(loginOrgLocation.getDateFormat(),sah.getExpenseId().getStartDate())).append(" to ").append(commonUtil.dateStrFromOrgDateFormatter(loginOrgLocation.getDateFormat(),sah.getExpenseId().getEndDate())).toString());
			 
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
			notificationScenario.setOrgCode(loginOrgCode);
			String actionLink = "";
			emailScenerioUtil.sendNotification(actionLink, loginUser, sah.getExpenseId().getProjectMembers().getEmployee().getUser(), loginOrgLocation, notificationScenario); 
	 	  }
		
	}
	
}
