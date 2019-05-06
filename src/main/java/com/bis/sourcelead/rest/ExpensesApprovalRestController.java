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
import java.util.Set;

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
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dto.ExpensesApproverDTO;
import com.bis.sourcelead.dto.ExpensesApproverHierarchyDTO;
import com.bis.sourcelead.dto.Response;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.EmailAttachment;
import com.bis.sourcelead.entity.EmailMessage;
import com.bis.sourcelead.entity.EmailScenario;
import com.bis.sourcelead.entity.ExpenseProjectApproverHierarchy;
import com.bis.sourcelead.entity.Expenses;
import com.bis.sourcelead.entity.ExpensesApproverHierarchy;
import com.bis.sourcelead.entity.ExpensesEntries;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.ProjectMembers;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserRole;
import com.bis.sourcelead.enums.Result;
import com.bis.sourcelead.enums.Submission_Status;
import com.bis.sourcelead.service.EmailScenarioService;
import com.bis.sourcelead.service.ExpenseProjectApproverHierarchyService;
import com.bis.sourcelead.service.ExpensesApprovalService;
import com.bis.sourcelead.service.ExpensesCategoryService;
import com.bis.sourcelead.service.ExpensesEntriesService;
import com.bis.sourcelead.service.ExpensesService;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.ProjectMembersService;
import com.bis.sourcelead.service.UserRoleService;
import com.bis.sourcelead.service.impl.ExpensesEmailsUtil;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.EmailScenerioUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;
import com.ibm.icu.math.BigDecimal;

@RestController
@RequestMapping("/app")
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
	ExpensesEmailsUtil expensesEmailsUtil;
	
	
	@Autowired
	private ExpenseProjectApproverHierarchyService expenseProjectApproverHierarchyService;
	
	@Autowired
	private UserRoleService userRoleService;
	
	@Value("${sourceleadUrl}")
    private String sourceleadUrl;
	

	@Value("${sourceleadLoginUrl}")
	private String sourceleadLoginUrl;
	
	@Autowired
	private ExpensesCategoryService expensesCategoryService;
	
	/*@RequestMapping(value = "/getExpensesApprovalList")
	public List<ExpensesApproverHierarchy> getExpensesApprovalList(HttpServletRequest request, String expenseStatus, String startDate, String endDate) throws Exception {
		User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations orgLoc = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		List<ExpensesApproverHierarchy> expApperoverHierarchy = expensesApprovalService.getExpensesApproverHierarchyByApproverUserIdAndOrgLocationId(loginUser, orgLoc, expenseStatus, startDate, endDate);
			return expApperoverHierarchy;
   }*/
	
	
	@RequestMapping(value = "/getExpensesApprovalList")
	public ModelAndView getExpensesApprovalList(HttpServletRequest request, ExpensesApproverDTO expensesApproverDTO) throws Exception {
		 ModelAndView model = new ModelAndView();
		try {
		User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations orgLoc = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		String isGlobalAccess = (String) SourceLeadSessionManager.getSessionAttribute(request, "isGlobalAccess");
		ExpensesApproverDTO expApperoverdto = expensesApprovalService.getExpensesApproverHierarchyByApproverUserIdAndOrgLocationId(loginUser, orgLoc, expensesApproverDTO,isGlobalAccess);
		model.addObject("totalRows",expApperoverdto.getTotalRows());
		model.addObject("pageCount",expApperoverdto.getPageCount());
		model.addObject("pageSize",expApperoverdto.getPageSize());
		model.addObject("searchValue",expApperoverdto.getSearchValue());
		model.addObject("expenseStatus",expApperoverdto.getExpenseStatus());
		model.addObject("orgLocationDateFormat",orgLoc.getDateFormat());
		model.addObject("expApperoverHierarchydto",expApperoverdto.getExpensesApproverHierarchyDTO());
		model.setViewName("expenses/expensesApprovalListDataTable");
		}catch(Exception e) {
			e.printStackTrace();
			 model.addObject("AWKWARD", true);
			  model.setViewName("expenses/expensesApprovalListDataTable");
		}
		return model;
   }
	
	
	
	@RequestMapping(value = "/getExpensesApprovalListForExports")
	public void getExpensesApprovalListForExports(HttpServletRequest request,HttpServletResponse response,ExpensesApproverDTO expensesApproverDTO) throws Exception {

		User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations orgLoc = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		String isGlobalAccess = (String) SourceLeadSessionManager.getSessionAttribute(request, "isGlobalAccess");
		File file=null;
		ExpensesApproverDTO expApperoverHierarchyList = expensesApprovalService.getExpensesApproverHierarchyByApproverUserIdAndOrgLocationId(loginUser, orgLoc, expensesApproverDTO,isGlobalAccess);
		List<ExpensesApproverHierarchyDTO> expApperoverHierarchy = expApperoverHierarchyList.getExpensesApproverHierarchyDTO();
		if(expApperoverHierarchy != null && !expApperoverHierarchy.isEmpty()) {
			if(expensesApproverDTO.getType().equalsIgnoreCase("pdf"))
				file = expensesApprovalService.getExpensesApproverHierarchyListForPdf(expApperoverHierarchy,orgLoc,loginUser,isGlobalAccess);
			else
				file = expensesApprovalService.getExpensesApproverHierarchyListForExcel(expApperoverHierarchy,orgLoc,loginUser,isGlobalAccess);
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
		
   }
	
	//unused method
	@RequestMapping(value = "/getExpensesApprovalDetailsByExpenseId")
	public String getExpensesApprovalListByExpenseId(@RequestParam(value="expenseId", required=false) String expenseId) throws Exception {
		Expenses expenses = expensesService.getExpenseById(Long.parseLong(expenseId));
		expenses.setStartDateStr(commonUtil.dateStrFromOrgDateFormatter(expenses.getOrgLocationId().getDateFormat(),expenses.getStartDate()));
		if(null != expenses.getEndDate()){
		expenses.setEndDateStr(commonUtil.dateStrFromOrgDateFormatter(expenses.getOrgLocationId().getDateFormat(),expenses.getEndDate()));
		}	
		return commonUtil.toJSON(expenses);
   }
	
	//unused method
	@RequestMapping(value = "/getExpensesApprovalCategoryByExpenseId")
	public ResponseList<ExpensesEntries> getExpensesApprovalCategoryByExpenseId(@RequestParam(value="expenseId", required=false) String expenseId,@RequestParam(value="locationCode") String locationCode) throws Exception {
		ResponseList<ExpensesEntries> reponseEntries = new ResponseList<ExpensesEntries>();
		List<ExpensesEntries> expensesEntries = expensesEntriesService.getExpensesEntriesByExpenseId(Long.parseLong(expenseId));
		
		 OrgLocations orglo = orgLocationService.getOrgLocationsByLocationCode(locationCode);
	        //SimpleDateFormat formatter = orglo.getLocFullFormDateFormatter();
		
		for(ExpensesEntries expEntries:expensesEntries){
			expEntries.setExpenseDateString(commonUtil.dateStrFromOrgDateFormatter(orglo.getDateFormat(),expEntries.getExpenseDate()));
		}
		reponseEntries.setData(expensesEntries);
		return reponseEntries;
   }
	
	
    @RequestMapping(value = "/saveExpenseApprovalStatusByExpenseId", method = RequestMethod.POST)
    public  Response<String> saveExpenseApprovalStatusByExpenseId(HttpServletRequest request,@RequestParam(value="expenseId", required=false) Long expenseId,
        @RequestParam(value="approvalStatus", required=false) String approvalStatus,@RequestParam(value="rejectedReason", required=false) String rejectedReason){
    	Response<String> reponse = new Response<String>();
    	User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations orgLoc = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		String isGlobalAccess = (String) SourceLeadSessionManager.getSessionAttribute(request, "isGlobalAccess");
		if(user != null && orgLoc != null) {
    	try {
		 String currencyCode = (String) SourceLeadSessionManager.getSessionAttribute(request, "currencyCode");
	 	expensesApprovalService.updateApprovalStatusByApproverForExpense(expenseId, user.getId(),approvalStatus, rejectedReason,orgLoc,isGlobalAccess);
	 	ExpensesApproverHierarchy eah = expensesApprovalService.getExpensesApproverHierarchyByApproverUserIdAndExpenseId(user.getId(), expenseId, orgLoc.getId(),isGlobalAccess);
	 	if(eah != null && eah.getExpenseId() != null){
	 		 String submissionStatusStr = eah.getExpenseId().getExpenseStatus().getValue();
	 		 if(StringUtils.isNotEmpty(submissionStatusStr) && 
		 			 (submissionStatusStr.equalsIgnoreCase(Submission_Status.getNameByValue("approved").getValue())
		 		     || submissionStatusStr.equalsIgnoreCase(Submission_Status.getNameByValue("rejected").getValue()))
		 	    )
		 	   {
	 			    ProjectMembers projectMembers = projectMembersService.getProjectMemberById(eah.getExpenseId().getProjectMembers().getId());
	 			    DateFormat TZ_DF_TS = new SimpleDateFormat (orgLoc.getDateFormat());
	 	            String startDate = commonUtil.dateStrFromOrgDateFormatter(orgLoc.getDateFormat(),eah.getExpenseId().getStartDate());
	 	            String endDate = commonUtil.dateStrFromOrgDateFormatter(orgLoc.getDateFormat(),eah.getExpenseId().getEndDate());
	 	            
	 	            //For Employee
			 		sendEmailNotificationToEmployeeAndApproversOfEndClient(eah.getExpenseId(),currencyCode,projectMembers.getProjectMemberCandidteId().getFirstName(), projectMembers.getProjectMemberCandidteId().getLastName(), projectMembers.getProjectMemberCandidteId().getEmail(), user, projectMembers.getProjectMemberCandidteId().getConsultantUser(), orgLoc, projectMembers, startDate, endDate, submissionStatusStr, rejectedReason);
			 		
			 		if(eah.getHierarchyId() != null && eah.getHierarchyId().getLevel() == 2 ){
			 			//For Approvers of EndClient
				 		 List<ExpenseProjectApproverHierarchy> approverList =  expenseProjectApproverHierarchyService.getProjectsHierarchyListByProjectIdAndOrgLocationId(projectMembers.getProjects().getId(), orgLoc.getId());
				 		 if(approverList != null && !approverList.isEmpty()){

				 			 for(ExpenseProjectApproverHierarchy  approver : approverList){
				 				if(!user.getUserCode().equalsIgnoreCase(approver.getApproverUserId().getUserCode())){
				 					 expensesEmailsUtil.sendEmailNotificationToManagers(eah.getExpenseId(),currencyCode,approver.getApproverUserId().getFirstName(),approver.getApproverUserId(), approver.getApproverUserId().getLastName(), approver.getApproverUserId().getEmail(), user,approver.getApproverUserId(), orgLoc, projectMembers, startDate, endDate, submissionStatusStr, rejectedReason);
				 				}
				 			 }
				 			 
				 			 if(eah.getExpenseId() != null && eah.getExpenseId().getProjects() != null && StringUtils.isNotEmpty(eah.getExpenseId().getProjects().getIsReportingManagerAsApprover())
				 					   &&  eah.getExpenseId().getProjects().getIsReportingManagerAsApprover().equalsIgnoreCase("Y")) {
				 				   if(projectMembers != null && projectMembers.getProjectMemberCandidteId() != null &&  projectMembers.getProjectMemberCandidteId().getConsultantUser() != null
				 						   &&  projectMembers.getProjectMemberCandidteId().getConsultantUser()!= null && projectMembers.getProjectMemberCandidteId().getOrgLocationId() != null) {
				 					   UserRole userRole = userRoleService.getUserDetailsByUserIdAndLocationId(projectMembers.getProjectMemberCandidteId().getConsultantUser().getId(), projectMembers.getProjectMemberCandidteId().getOrgLocationId().getId());
				 					   if(userRole != null && userRole.getReportingManager() != null) {
				 						  expensesEmailsUtil.sendEmailNotificationToManagers(eah.getExpenseId(),currencyCode,userRole.getReportingManager().getFirstName(),userRole.getUser(), userRole.getReportingManager().getLastName(), userRole.getReportingManager().getEmail(), user,userRole.getReportingManager(),  orgLoc, projectMembers, startDate, endDate, submissionStatusStr, rejectedReason);
				 					   }
				 				   }
				 			   }
				 		 }
				 		sendEmailToVendorAtExpensesApprovalStatus(eah.getExpenseId().getTotalAmount(),projectMembers,user,orgLoc,startDate, endDate,rejectedReason,currencyCode,approvalStatus);
			 		}
			 		
		 	   }else  if(StringUtils.isNotEmpty(submissionStatusStr) && submissionStatusStr.equalsIgnoreCase(Submission_Status.getNameByValue("submitted").getValue())) {
		 		   
		 		   
		 		    ProjectMembers projectMembers = projectMembersService.getProjectMemberById(eah.getExpenseId().getProjectMembers().getId());
			 		
	 			    DateFormat TZ_DF_TS = new SimpleDateFormat (orgLoc.getDateFormat());
	 	            String startDate = commonUtil.dateStrFromOrgDateFormatter(orgLoc.getDateFormat(),eah.getExpenseId().getStartDate());
	 	            String endDate = commonUtil.dateStrFromOrgDateFormatter(orgLoc.getDateFormat(),eah.getExpenseId().getEndDate());
	 	           //For Employee
	 	          if( eah.getHierarchyId().getLevel() != 1) {
	 	            sendEmailNotificationToEmployeeAndApproversOfEndClient(eah.getExpenseId(),currencyCode,projectMembers.getProjectMemberCandidteId().getFirstName(), projectMembers.getProjectMemberCandidteId().getLastName(), projectMembers.getProjectMemberCandidteId().getEmail(), user,projectMembers.getProjectMemberCandidteId().getConsultantUser(), orgLoc, projectMembers, startDate, endDate, "approved", rejectedReason);
	 	          }
	 	            if(eah.getHierarchyId() != null && eah.getHierarchyId().getLevel() == 1 && submissionStatusStr.equalsIgnoreCase(Submission_Status.getNameByValue("submitted").getValue())){
			 			//For Approvers of EndClient
				 		 List<ExpenseProjectApproverHierarchy> approverList =  expenseProjectApproverHierarchyService.getProjectsHierarchyListByProjectIdAndOrgLocationId(projectMembers.getProjects().getId(), orgLoc.getId());
				 		 if(approverList == null || approverList.isEmpty()) {
				 			if(projectMembers.getProjectMemberCandidteId() != null && projectMembers.getProjectMemberCandidteId().getOrgLocationId() != null && projectMembers.getProjectMemberCandidteId().getOrgLocationId().getId() != null){
					 			approverList =  expenseProjectApproverHierarchyService.getProjectsHierarchyListByProjectIdAndOrgLocationId(projectMembers.getProjects().getId(), projectMembers.getProjectMemberCandidteId().getOrgLocationId().getId());
					 		 }
				 		 }
				 		 if(approverList != null && !approverList.isEmpty()){
				 			 for(ExpenseProjectApproverHierarchy  approver : approverList){
				 				if(approver.getLevel() != null && approver.getLevel() != 1){
				 					expensesEmailsUtil.sendEmailNotificationTolevelTwoApprover(eah.getExpenseId(),currencyCode,approver.getApproverUserId().getFirstName(), approver.getApproverUserId().getLastName(), approver.getApproverUserId().getEmail(), user,approver.getApproverUserId(),  orgLoc, projectMembers, startDate, endDate, "approved", rejectedReason);
				 				}
				 			 }
				 		 } 
			 		}
		 	   }
	 	}
		 	reponse.setResponse(Result.SUCCESS.name());
		 	reponse.setResponseMessage(Result.SUCCESS.name());
    	}catch(Exception e) {
    		reponse.setResponse(Result.AWKWARD.name());
    	 	reponse.setResponseMessage(Result.AWKWARD.name());
    	}
	}else {
		reponse.setResponse(Result.SESSION_TIMEDOUT.name());
	 	reponse.setResponseMessage(Result.SESSION_TIMEDOUT.name());
	}
        return reponse;
    }

   
	private void sendEmailToVendorAtExpensesApprovalStatus(java.math.BigDecimal bigDecimal,ProjectMembers projectMembers, User user,OrgLocations orgLoc,String startDateStr,String endDateStr,
			String rejectedReason,String currencyCode, String status) throws Exception {
		
		 // Expenses expense = expensesService.getExpenseById(expenseId);
		
		List<ExpenseProjectApproverHierarchy> approverHierarchyList = expenseProjectApproverHierarchyService.getActiveOrgApproverUsersByProjectMemberMasterCandidateIdAndProjectId(projectMembers.getMasterCandidateId().getId(),projectMembers.getProjects().getId());
		for(ExpenseProjectApproverHierarchy approver : approverHierarchyList){
				
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
		     globalVarsMap.put("employeename",projectMembers.getProjectMemberCandidteId().getFullName());
			
//			 globalVarsMap.put("managerFirstName",projectManager.getFirstName());
//			 globalVarsMap.put("managerLastName",projectManager.getLastName());
			 //globalVarsMap.put("projectName", project.getProjectName());
			 globalVarsMap.put("projectname",projectMembers.getProjects().getProjectName());
			 globalVarsMap.put("expensesduration",startDateStr+" to "+endDateStr);
			 globalVarsMap.put("projectname",(orgLoc.getOrgLocationName()));
			 globalVarsMap.put("orgname",user.getFirstName()+(orgLoc.getOrg().getOrgName()));
			 
			 StringBuilder loginURLSB = new StringBuilder();
			 globalVarsMap.put("loginURL",loginURLSB.append("<a href='").append(sourceleadLoginUrl).append("'style='text-decoration:none;'><div style=' width:220px; border-radius:30px; background-color:#214b90; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Login</div></a>").toString());
			 globalVarsMap.put("loginlink",loginURLSB.toString());
			 StringBuilder amountCurrencyType = new StringBuilder();
			 BigDecimal amountCurrencyType1 = new BigDecimal((bigDecimal));
			 amountCurrencyType1 = amountCurrencyType1.setScale(2,BigDecimal.ROUND_HALF_UP );
			 
			 
			 amountCurrencyType.append(amountCurrencyType1).append(" ").append(currencyCode);
			 globalVarsMap.put("amount",amountCurrencyType1.toString());
			 globalVarsMap.put("reason",rejectedReason);
	
			 if(status.equals("REJECTED")){
				 commonUtil.sendTemplateEmail(emailMessage, globalVarsMap,orgLoc.getOrg(),orgLoc,Constants.EXPENSES_REJECTED_EMAIL_TOVENDOR,new ArrayList<EmailAttachment>());
			 }else{
				 commonUtil.sendTemplateEmail(emailMessage, globalVarsMap,orgLoc.getOrg(),orgLoc,Constants.EXPENSES_APPROVED_EMAIL_TOVENDOR,new ArrayList<EmailAttachment>());
			 }
			
			//calling the sendNotification method of emailScenerioUtil
			EmailScenario  notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.EXPENSES_APPROVED_EMAIL_TOVENDOR,Constants.ENTITY_TYPE_NOTIFICATION);
			String notificationBody = emailScenerioUtil.buildTemplateBody(notificationScenario.getBody(),globalVarsMap,notificationScenario);
			notificationScenario.setBody(notificationBody);
			notificationScenario.setOrgCode(orgLoc.getLocationCode());
			//String url = null;
			String actionLink = "";
			emailScenerioUtil.sendNotification(actionLink, user, approver.getApproverUserId(), orgLoc, notificationScenario,new HashMap<String,String>());
			
		    }
		}
	}
    private void sendEmailNotificationToEmployeeAndApproversOfEndClient(Expenses expenses,String currencyCode,String firstName,String lastName,String toEmail,User loginUser, User toUser,OrgLocations loginOrgLocation,ProjectMembers projectMembers,String startDate, String endDate,String status,String rejectedReason) throws Exception{
		
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
		 
		 globalVarsMap.put("employeeUser",projectMembers.getProjectMemberCandidteId().getFullName());
		 globalVarsMap.put("projectName",projectMembers.getProjects().getProjectName());
		 globalVarsMap.put("expenseId",expenses.getExpenseId());
		 globalVarsMap.put("frequencyType",projectMembers.getProjects().getInvoiceFrequency());
		 globalVarsMap.put("startDate",startDate);
		 globalVarsMap.put("endDate", endDate);
		 
		/* StringBuilder amountCurrencyType = new StringBuilder();
		 BigDecimal amountCurrencyType1 = new BigDecimal((expenses.getTotalAmount()));*/
		// BigDecimal amountCurrencyType2 = new BigDecimal((expenses.getExpensesEntries()));
		// amountCurrencyType1 = amountCurrencyType1.setScale(2,BigDecimal.ROUND_HALF_UP );
		 
		 Set<ExpensesEntries> expensesEntriesList = expenses.getExpensesEntries();
		 for(ExpensesEntries expList: expensesEntriesList) {
			 
			 globalVarsMap.put("amount", expList.getSubmittedamount().toString());
			 globalVarsMap.put("approvedAmount",expenses.getTotalAmount().toString());
			 
			
		 }
		/* amountCurrencyType.append(amountCurrencyType1).append(" ").append(currencyCode);
		 globalVarsMap.put("amount",amountCurrencyType1.toString());
		 globalVarsMap.put("approvedAmount",expenses.getTotalAmount().toString());
*/
		 
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
		 globalVarsMap.put("loginURL",loginURLSB.append("<a href='").append(sourceleadLoginUrl).append("'style='text-decoration:none;'><div style=' width:220px; border-radius:30px; background-color:#214b90; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Login</div></a>").toString());
		 
		 if("REJECTED".equalsIgnoreCase(status)){
			 commonUtil.sendTemplateEmail(emailMessage, globalVarsMap,loginOrgLocation.getOrg(),loginOrgLocation,Constants.MAIL_TEAM_MANAGER_REJECTED_EXPENSE,new ArrayList<EmailAttachment>());
		 }
		 if("APPROVED".equalsIgnoreCase(status)){
			 commonUtil.sendTemplateEmail(emailMessage, globalVarsMap,loginOrgLocation.getOrg(),loginOrgLocation,Constants.MAIL_TEAM_MANAGER_APPROVED_EXPENSE,new ArrayList<EmailAttachment>());
		 }
		
		 EmailScenario  notificationScenario = null;
		 String actionLink="";
		 StringBuilder notificationLink = new StringBuilder();
		 if("REJECTED".equalsIgnoreCase(status)){
		     actionLink = notificationLink.append(sourceleadUrl).append("/").append("addApprovalExpense?expenseId="+expenses.getExpenseId()+"&expenseLocationCode="+expenses.getLocationsCode()+"&type=view").toString();
			 notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.MAIL_TEAM_MANAGER_REJECTED_EXPENSE,Constants.ENTITY_TYPE_NOTIFICATION);
		 }
		 if("APPROVED".equalsIgnoreCase(status)){
		     actionLink = notificationLink.append(sourceleadUrl).append("/").append("addApprovalExpense?expenseId="+expenses.getExpenseId()+"&expenseLocationCode="+expenses.getLocationsCode()+"&type=view").toString();

			 notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.MAIL_TEAM_MANAGER_APPROVED_EXPENSE,Constants.ENTITY_TYPE_NOTIFICATION);
		 }
		 // TODO @Rajashekar notificationScenario.getBody() may throw NullPointerException please check it once 
		String notificationBody = emailScenerioUtil.buildTemplateBody(notificationScenario.getBody(),globalVarsMap,notificationScenario);
		notificationScenario.setBody(notificationBody);
		notificationScenario.setOrgCode(loginOrgLocation.getLocationCode());
		//emailScenerioUtil.sendNotification(actionLink, loginUser, projectMembers.getProjectMemberCandidteId().getConsultantUser(), loginOrgLocation, notificationScenario,new HashMap<String,String>());
		emailScenerioUtil.sendNotification(actionLink, loginUser, toUser, loginOrgLocation, notificationScenario,new HashMap<String,String>());  
    }
	
    private void sendEmailNotificationTolevelToApprover(Expenses expenses,String currencyCode,String firstName,String lastName,String toEmail,User loginUser, User toUser,OrgLocations loginOrgLocation,ProjectMembers projectMembers,String startDate, String endDate,String status,String rejectedReason) throws Exception{
		
    	 EmailMessage emailMessage = new EmailMessage();
    	 emailMessage.setFirstName(firstName);
	 	 emailMessage.setLastName(lastName);
	 	 emailMessage.setToEmail(toEmail);
	 	 emailMessage.setFromEmail(loginUser.getEmail());
	 	 emailMessage.setCreatedBy(loginUser.getUserCode());
	 	 StringBuilder userNameSB = new StringBuilder();
	 	 emailMessage.setFromName(userNameSB.append(loginUser.getFirstName()).append(" ").append(loginUser.getLastName()).toString());
	   	 emailMessage.setEmailType(Constants.CUSTOM_USER_EMAIL);
		 Map<String, String> globalVarsMap = new HashMap<>();
		
		// globalVarsMap.put("loginUserFirstName",loginUser.getFirstName());
		// globalVarsMap.put("loginUserLastName",loginUser.getLastName());
		 
		 
		 if(null != lastName && !"".equalsIgnoreCase(lastName)){
			 StringBuilder manager =new StringBuilder();
			 globalVarsMap.put("levelTwoApprover",firstName+" "+lastName.toString());
		 }else{
			 globalVarsMap.put("levelTwoApprover",firstName);
		 }
		 
		 globalVarsMap.put("employeeName",projectMembers.getProjectMemberCandidteId().getFullName());
		 globalVarsMap.put("projectName",projectMembers.getProjects().getProjectName());
		 globalVarsMap.put("expenseStartDate",startDate);
		 globalVarsMap.put("expenseEndDate", endDate);
		 globalVarsMap.put("approverOne", (userNameSB.append(loginUser.getFirstName()).append(" ").append(loginUser.getLastName()).toString()));
		 Set<ExpensesEntries> expensesEntriesList = expenses.getExpensesEntries();
		 for(ExpensesEntries expList: expensesEntriesList) {
			 //globalVarsMap.put("amount", expList.getSubmittedamount().toString());
			 globalVarsMap.put("climedamount",expenses.getTotalAmount().toString());
		 }
		 globalVarsMap.put("loginOrg",loginOrgLocation.getOrgLocationName());

		 StringBuilder timeFrequencySB = new StringBuilder();
		 globalVarsMap.put("timeFrequency",timeFrequencySB.append(startDate).append(" to ").append(endDate).toString());
		 
		 globalVarsMap.put("loginUser", loginUser.getFirstName());
		 globalVarsMap.put("loginUser", loginUser.getLastName());
		 
		 StringBuilder loginURLSB = new StringBuilder();
		 globalVarsMap.put("loginURL",loginURLSB.append("<a href='").append(sourceleadLoginUrl).append("'style='text-decoration:none;'><div style=' width:220px; border-radius:30px; background-color:#214b90; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Login</div></a>").toString());
		
		/* if("APPROVED".equalsIgnoreCase(status)){
			 commonUtil.sendTemplateEmail(emailMessage, globalVarsMap,loginOrgLocation.getOrg(),loginOrgLocation,Constants.MAIL_TO_LEVELTWO_APPROVER_WHILE_LEVENONE_APPROVEEXPENSES_INSEQUENTIALMECHANISM,new ArrayList<EmailAttachment>());
		 }
		
		 EmailScenario  notificationScenario = null;
		 String actionLink="";
		 StringBuilder notificationLink = new StringBuilder();
		 if("APPROVED".equalsIgnoreCase(status)){
		     actionLink = notificationLink.append(sourceleadUrl).append("/").append("addApprovalExpense?expenseId="+expenses.getExpenseId()+"&expenseLocationCode="+expenses.getLocationsCode()+"&type=view").toString();

			 notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.MAIL_TO_LEVELTWO_APPROVER_WHILE_LEVENONE_APPROVEEXPENSES_INSEQUENTIALMECHANISM,Constants.ENTITY_TYPE_NOTIFICATION);
		 }
		String notificationBody = emailScenerioUtil.buildTemplateBody(notificationScenario.getBody(),globalVarsMap,notificationScenario);
		notificationScenario.setBody(notificationBody);
		notificationScenario.setOrgCode(loginOrgLocation.getLocationCode());
		emailScenerioUtil.sendNotification(actionLink, loginUser, toUser, loginOrgLocation, notificationScenario,new HashMap<String,String>());  */
    }
    
    @RequestMapping(value="/getExpenseAttachmentStatus")
    
    public String getExpenseAttachmentStatusByCategoryId(@RequestParam(value="categoryId" ,required=true)long categoryId) {
    	 String isAttachemtRequired = expensesCategoryService.getExpenseAttachmentStatusByCategoryId(categoryId);
    	return isAttachemtRequired;
    	
    }
    
	
}
