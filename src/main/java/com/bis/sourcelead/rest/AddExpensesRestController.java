package com.bis.sourcelead.rest;

import java.util.ArrayList;	
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.internet.InternetAddress;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.ExpenseProjectApproverHierarchyDao;
import com.bis.sourcelead.dao.OrgLocationsDao;
import com.bis.sourcelead.dao.ProjectMembersDao;
import com.bis.sourcelead.dto.ExpensesDTO;
import com.bis.sourcelead.dto.Response;
import com.bis.sourcelead.entity.Countries;
import com.bis.sourcelead.entity.EmailAttachment;
import com.bis.sourcelead.entity.EmailMessage;
import com.bis.sourcelead.entity.EmailScenario;
import com.bis.sourcelead.entity.ExpenseProjectApproverHierarchy;
import com.bis.sourcelead.entity.Expenses;
import com.bis.sourcelead.entity.ExpensesApproverHierarchy;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgRelations;
import com.bis.sourcelead.entity.ProjectMembers;
import com.bis.sourcelead.entity.Projects;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.enums.Result;
import com.bis.sourcelead.enums.Submission_Status;
import com.bis.sourcelead.service.AddExpensesService;
import com.bis.sourcelead.service.CountriesService;
import com.bis.sourcelead.service.EmailScenarioService;
import com.bis.sourcelead.service.ExpensesApprovalService;
import com.bis.sourcelead.service.ExpensesEntryDocumentsService;
import com.bis.sourcelead.service.ExpensesService;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.OrgRelationsService;
import com.bis.sourcelead.service.ProjectMembersService;
import com.bis.sourcelead.service.ProjectsService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.EmailScenerioUtil;
import com.bis.sourcelead.util.PdfConversionUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;

@RestController
@RequestMapping("/app")
public class AddExpensesRestController {
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	@Autowired
	private ProjectsService projectsService; 
	
	@Autowired
	private OrgRelationsService orgRelationsService;
	
	@Autowired
	private OrgLocationService orgLocationService;
	
	@Autowired
	private CountriesService countriesService;
	
	
   	@Autowired
	private CommonUtil commonUtil;
	
	@Autowired
	private EmailScenarioService emailScenarioService;
	
	@Autowired
	EmailScenerioUtil emailScenerioUtil;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AddExpensesService addExpensesService;
	
	@Autowired
	ExpensesService expensesService;
	
	@Autowired
	private ProjectMembersService projectMembersService;
	
	@Autowired
	private ExpensesApprovalService expensesApprovalService;
	
	@Autowired
	ExpensesEntryDocumentsService expensesEntryDocumentsService;
	
	@Autowired
	OrgLocationsDao orgLocationsDao;
	
	@Autowired
	ProjectMembersDao projectMembersDao;
	
	@Autowired
	ExpenseProjectApproverHierarchyDao expenseProjectApproverHierarchyDao;
	
	@Autowired
	private PdfConversionUtil pdfConversionUtil;
	
	@RequestMapping(value = "/getClientByProjectMemberId", method = RequestMethod.GET)
	public @ResponseBody String getClientByProjectMemberId(@RequestParam(value = "projectMemberId", required = false) String projectMemberId,@RequestParam(value = "locationId", required = false) Long locationId) throws Exception {
		OrgLocations orgLocations = orgLocationsDao.getOrgLocations(locationId);
		//SimpleDateFormat simpleDateFormat = orgLocations.getLocFullFormDateFormatter();
		if(null != projectMemberId && !"".equalsIgnoreCase(projectMemberId)){
			ProjectMembers projectMembers =	projectMembersService.getProjectMemberById(Long.parseLong(projectMemberId));
			if(null != projectMembers.getProjects()){
				Projects project = projectMembers.getProjects();
				if(null != project.getOrgRelationId()){
					 OrgRelations orgRelation = orgRelationsService.getOrgRelationById(project.getOrgRelationId().getId());

					 if("Vendor".equalsIgnoreCase(orgRelation.getInitiatorType())){
						 project.setExpensesClientName(orgRelation.getResponderLocationId().getOrgLocationName());
					 }else if("Client".equalsIgnoreCase(orgRelation.getInitiatorType())){
						 project.setExpensesClientName(orgRelation.getLocationId().getOrgLocationName());
					 }

					}
					 project.setStartDateStr(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),project.getStartDate()));
					 if(null != project.getEndDate() && !"".equalsIgnoreCase(project.getEndDate().toString())){
					 project.setEndDateStr(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),project.getEndDate()));
					 }
				 return commonUtil.toJSON(project);
			}
	    }
		return projectMemberId;
    }
	
	@RequestMapping(value = "/withDrawExpense", method = RequestMethod.POST)
	public @ResponseBody void withDrawExpense(@RequestParam(value="expenseId", required=false) Long expenseId) throws Exception {
			if(null != expenseId){
				expensesApprovalService.updateExpenseStatusByExpenseId(expenseId,Submission_Status.DRAFT.getValue());
			}
	}
	
	
	@RequestMapping(value = "/ws/acceptOrRejectReopenExpenseBySuperUser", method = RequestMethod.POST)
	public @ResponseBody void acceptOrRejectReopenExpenseBySuperUser(@RequestParam(value="expenseId", required=false) Long expenseId,
			@RequestParam(value="reOpenedUserId", required=false) Long reOpenedUserId,
			@RequestParam(value="type", required=false) String type,
			@RequestParam(value="reason", required=false) String reason, HttpServletRequest request) throws Exception {
			
		    User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		    OrgLocations orgLoc = (OrgLocations)SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		    String isGlobalAccess = (String) SourceLeadSessionManager.getSessionAttribute(request, "isGlobalAccess");
		    User reopenedUser = userService.getUserById(reOpenedUserId);
			if(expenseId != null && null != type && "accept".equalsIgnoreCase(type)){                                                                                                                                                                                       
				expensesApprovalService.updateExpenseStatusByExpenseIdAndReOpenReason(expenseId,reason,loginUser,type,Constants.YES);
			    //send mail & notification logic
			  	//submissionApproverHierarchyService.sendReopenTimeSheetAcceptOrRejectMail(projectMemberId,reason,loginUser,orgLoc,timesheetFrequency,type,reopenedUser);
				Expenses expense = expensesService.getExpenseById(expenseId);
				User superUser = userService.getUserById(reOpenedUserId);
				ExpensesApproverHierarchy sah = expensesApprovalService.getExpensesApproverHierarchyByApproverUserIdAndExpenseId(loginUser.getId(), expenseId,orgLoc.getId(),isGlobalAccess);

				expensesApprovalService.sendReopenExpenseAcceptOrRejectMail(expense,reason,superUser,orgLoc,type,reopenedUser,loginUser, sah.getExpenseId().getProjectMembers().getProjects().getProjectName());
				//expensesApprovalService.sendExpenseReopenAcceptMailToEmployee(expense,orgLoc,type,reopenedUser,loginUser);

			}else if(expenseId != null &&  null != type && "reject".equalsIgnoreCase(type) && null != reason && !"".equalsIgnoreCase(reason)){
				expensesApprovalService.updateExpenseStatusByExpenseIdAndReOpenReason(expenseId,reason,loginUser,type,Constants.YES);
			    //send mail & notification logic
			  	//submissionApproverHierarchyService.sendReopenTimeSheetAcceptOrRejectMail(projectMemberId,reason,loginUser,orgLoc,timesheetFrequency,type,reopenedUser);
				Expenses expense = expensesService.getExpenseById(expenseId);
				User superUser = userService.getUserById(reOpenedUserId);
				ExpensesApproverHierarchy sah = expensesApprovalService.getExpensesApproverHierarchyByApproverUserIdAndExpenseId(loginUser.getId(), expenseId,orgLoc.getId(),isGlobalAccess);
				expensesApprovalService.sendReopenExpenseAcceptOrRejectMail(expense,reason,superUser,orgLoc,type,reopenedUser,loginUser, sah.getExpenseId().getProjectMembers().getProjects().getProjectName());

			}
}
	
	@RequestMapping(value = "/notifyReopenExpense", method = RequestMethod.POST)
	public @ResponseBody void notifyReopenExpense(@RequestParam(value="expenseId", required=false) Long expenseId,
			@RequestParam(value="reason", required=false) String reason, HttpServletRequest request) throws Exception {
		    String currencyCode = (String) SourceLeadSessionManager.getSessionAttribute(request, "currencyCode");
		    User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		    OrgLocations orgLoc = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		    
		    String isSuperUser = Constants.NO;
		   // SimpleDateFormat simpleDateFormat = orgLoc.getLocFullFormDateFormatter();
		    
		    
			//DateFormat format = simpleDateFormat;
			if(expenseId != null){
				Expenses expenses = expensesService.getExpenseById(expenseId);
				if(commonUtil.isSuperAdminForProjectMemberToReopenExpenseses(expenses.getProjectMembers().getId(), loginUser,orgLoc)){
			    	isSuperUser = Constants.YES;
			    }
				expensesApprovalService.updateExpenseStatusByExpenseIdAndReOpenReason(expenseId,reason,loginUser,"notify",isSuperUser);
			    //send mail & notification logic
			  	if(isSuperUser.equalsIgnoreCase(Constants.NO)){
			  		sendEmailToSuperUserForReOpenExpenseApprovalStatus(expenses.getProjectMembers().getId(),expenses,loginUser,orgLoc,reason,currencyCode);
			  	}
			}
		
	}
	@RequestMapping(value = "/getClientByProjectId", method = RequestMethod.GET)
	public @ResponseBody String getClientByProjectId(@RequestParam(value = "projectId", required = false) String projectId,
			@RequestParam(value = "locationCode") String locationCode) throws Exception {
		OrgLocations orgLocations = orgLocationService.getOrgLocationsByLocationCode(locationCode);
		//date formats
		//SimpleDateFormat dateFormat = orgLocations.getLocFullFormDateFormatter();
		if(null != projectId && !"".equalsIgnoreCase(projectId)){
			Projects project =	projectsService.getProjectById(Long.parseLong(projectId));
			if(null != project){
				if(null != project.getOrgRelationId()){
					 OrgRelations orgRelation = orgRelationsService.getOrgRelationById(project.getOrgRelationId().getId());
					 if("Vendor".equalsIgnoreCase(orgRelation.getInitiatorType())){
						 project.setExpensesClientName(orgRelation.getResponderLocationId().getOrgLocationName());
					 }else if("Client".equalsIgnoreCase(orgRelation.getInitiatorType())){
						 project.setExpensesClientName(orgRelation.getLocationId().getOrgLocationName());
					 }
					}
				/*if(null != project.getOrgRelationId()){
					 OrgRelations orgRelation = orgRelationsService.getOrgRelationById(project.getOrgRelationId().getId());
					 project.setExpensesClientName(orgRelation.getResponderLocationId().getOrg().getOrgName());
					}*/
					 project.setStartDateStr(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),project.getStartDate()));
					 if(null != project.getEndDate() && !"".equalsIgnoreCase(project.getEndDate().toString())){
					 project.setEndDateStr(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),project.getEndDate()));
					 }
				 return commonUtil.toJSON(project);
			}
	    }
		return projectId;
    }

	
	@RequestMapping(value = "/rest/storeAddExpensesData",method = RequestMethod.POST)
	public @ResponseBody String storeAddExpensesMobileData(HttpServletRequest request,@RequestBody ExpensesDTO expensesDTO) throws Exception {
		return storeAddExpensesData(request, expensesDTO);
	}
	
	/**
	 * This method serves the request for store expenses
	 * 
	 * @param request
	 * @param status
	 * @param expensesDTO
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/storeAddExpensesData",method = RequestMethod.POST)
	public @ResponseBody String storeAddExpensesData(HttpServletRequest request, ExpensesDTO expensesDTO) throws Exception {
		User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		if(loginUser == null && expensesDTO.getUserId() != null){
			loginUser = userService.getUserById(expensesDTO.getUserId());
		}
		OrgLocations orgLoc = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		String currencyCode = (String) SourceLeadSessionManager.getSessionAttribute(request, "currencyCode");
		if(orgLoc == null && StringUtils.isNotEmpty(expensesDTO.getLocationCode())){
			orgLoc = orgLocationService.getOrgLocationsByLocationCode(expensesDTO.getLocationCode());
			Countries countries = countriesService.getCountriesByCountryName(orgLoc.getCountry());
			currencyCode = countries.getCurrency();
		}
		 String loginUserType = (String) SourceLeadSessionManager.getSessionAttribute(request, "displayName");
		String isGlobalAccess = (String) SourceLeadSessionManager.getSessionAttribute(request, "isGlobalAccess"); 
		if(loginUser != null && orgLoc != null){
			addExpensesService.storeAddExpensesData(expensesDTO,loginUser,orgLoc,request,currencyCode,isGlobalAccess,loginUserType);
			return "success";
		}
		else{
			return "failure";
		}
		//JSONObject jsonObj = new JSONObject(request.getParameter("json"));

		
   }
 	
	
	@RequestMapping(value = "/deleteDocumentByDocumentId",method = RequestMethod.POST)
	public @ResponseBody String deleteDocumentByDocumentId(@RequestParam(value="documentId", required=false) String documentId) throws Exception {
		addExpensesService.deleteDocumentByDocumentId(documentId);
		return "DataSaved";
    }
	
	
	
	 @RequestMapping(value = "/getMembersForSelectedProject",  method = RequestMethod.GET)
	 public @ResponseBody  List<ProjectMembers> getMembersForSelectedProject(
			@RequestParam(value="projectId", required=false) Long projectId, HttpServletRequest request) throws Exception {
	 	
		 	List<ProjectMembers> projectMembers = null;
		 	if(projectId != null){
		 		Projects project = projectsService.getProjectById(projectId);
		 		String isGlobalAccess = (String) SourceLeadSessionManager.getSessionAttribute(request, "isGlobalAccess");
		 	    OrgLocations orgLoc = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
				projectMembers = projectMembersService.getAllProjectMembersWithMasterCandidateIdByProjectIdAndLocationId(projectId,orgLoc.getId(),isGlobalAccess);
				for(ProjectMembers pm:projectMembers) {
					pm.setProjectAssignedDateStr(commonUtil.dateStrFromOrgDateFormatter(orgLoc.getDateFormat(), pm.getProjectAssignedDate()));
				if(pm.getProjectEndDate() != null) {
					pm.setProjectEndDateStr(commonUtil.dateStrFromOrgDateFormatter(orgLoc.getDateFormat(), pm.getProjectEndDate()));
				}else {
					pm.setProjectEndDateStr(commonUtil.dateStrFromOrgDateFormatter(orgLoc.getDateFormat(),project.getEndDate()));
				}
			}
				
		 	}
			return projectMembers ;
	}
	 
	 
	 /*@RequestMapping(value = "/downloadExpensesEntryDocument", method = RequestMethod.GET)
	    public void downloadExpensesEntryDocument(@RequestParam(value="entryDocumentId", required=false) Long entryDocumentId ,HttpServletResponse response) throws Exception {
			
			
		 ExpensesEntryDocuments	expensesEntryDocument =	expensesEntryDocumentsService.getExpensesEntryDocumentById(entryDocumentId);	
			    
			    response.setContentType("application/octet-stream");
			    StringBuilder fileName = new StringBuilder();
		        response.setHeader("Content-Disposition", fileName.append("attachment; filename=\"").append(expensesEntryDocument.getFileName()).append("\"").toString());
				InputStream inputStream = new ByteArrayInputStream(expensesEntryDocument.getDocFile());
				try {
					OutputStream outStream = response.getOutputStream();

					byte[] buffer = new byte[4096];
					int bytesRead = -1;

					while ((bytesRead = inputStream.read(buffer)) != -1) {
						outStream.write(buffer, 0, bytesRead);
					}

					inputStream.close();
					outStream.close();

				} catch (FileNotFoundException e) {
					logger.error(ExceptionUtils.getFullStackTrace(e));
				} 

		} */
	 
	 
	
	 
	 private void sendEmailToSuperUserForReOpenExpenseApprovalStatus(Long projectMemberId,Expenses expenses, User loginUser, OrgLocations orgLoc, String reason, String currencyCode) throws Exception {
		 ProjectMembers clientProjectMember = projectMembersDao.getClientProjectMemberByProjectMemberId(projectMemberId);
		 ExpenseProjectApproverHierarchy pah =  expenseProjectApproverHierarchyDao.getApproverUsersByProjectIdAndProjectClientId(clientProjectMember.getProjects().getId(), clientProjectMember.getProjectClientsId().getId());	
			
		// ExpensesApproverHierarchy sah = expensesApprovalService.getExpensesApproverHierarchyByApproverUserIdAndExpenseId(loginUser.getId(), expenseId,orgLoc.getId());
			
		 		 EmailMessage emailMessage = new EmailMessage();
		     	 emailMessage.setFirstName(pah.getApproverUserId().getFirstName());
			 	 emailMessage.setLastName(pah.getApproverUserId().getLastName());
			 	 
			// 	OrgLocations ol = orgLocationsDao.getOrgLocations(orgLoc.getId());
			 	 
			 	 emailMessage.setToEmail(pah.getApproverUserId().getEmail());
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
				 
				 if(null != loginUser.getLastName() && !"".equalsIgnoreCase(loginUser.getLastName())){
//					 StringBuilder manager =new StringBuilder();`
					 globalVarsMap.put("superUser", orgLoc.getUser().getFullName());
				 }else{
					 globalVarsMap.put("superUser",orgLoc.getUser().getFullName());
				 }
				 
				 if(null != clientProjectMember.getProjectMemberCandidteId().getConsultantUser().getFullName()){
					 globalVarsMap.put("employeeUser",clientProjectMember.getProjectMemberCandidteId().getConsultantUser().getFullName());
				 }
				 
				 
						 if(null != clientProjectMember.getProjects()){
					      globalVarsMap.put("projectName", clientProjectMember.getProjects().getProjectName());
					 }
				 
//				 if(null != sah.getExpenseId()){
//					 if(null != sah.getExpenseId().getExpenseId()){
//				 globalVarsMap.put("expenseId",sah.getExpenseId().getExpenseId());
//					 }
//				 }
				 //SimpleDateFormat dateFormat=orgLoc.getLocFullFormDateFormatter();
				 if(null != expenses.getExpenseId()){
					 if(null != expenses.getStartDate()){
				 globalVarsMap.put("startDate",commonUtil.dateStrFromOrgDateFormatter(orgLoc.getDateFormat(),expenses.getStartDate()));
					 }
				 }	 
				 if(null != expenses.getExpenseId()){
					 if(null != expenses.getEndDate()){
				 globalVarsMap.put("endDate",commonUtil.dateStrFromOrgDateFormatter(orgLoc.getDateFormat(),expenses.getEndDate()));
					 }
				 }
				 
				 StringBuilder amopuntRate = new StringBuilder();
				 amopuntRate.append((expenses.getTotalAmount())).append(" ").append(currencyCode);
				 
				 globalVarsMap.put("amount",amopuntRate.toString());
				 
//				Date todayDate = new Date();
//				 globalVarsMap.put("submitForApprovalDate",LoginSessionController.DATEFORMAT.format(todayDate));
				
				// globalVarsMap.put("employeeFirstName",sah.getExpenseId().getProjectMembers().getEmployee().getUser().getLastName());
				// globalVarsMap.put("employeeLastName",sah.getExpenseId().getProjectMembers().getEmployee().getUser().getLastName());
				
				 globalVarsMap.put("loginOrg",orgLoc.getOrg().getOrgName());
				 
				 
//				 StringBuilder timeFrequencySB = new StringBuilder();
//				 globalVarsMap.put("timeFrequency",timeFrequencySB.append(sah.getExpenseId().getStartDate()).append(" to ").append(sah.getExpenseId().getEndDate()).toString());
//				 
//				 globalVarsMap.put("managerFirstName", orgLoc.getFirstName());
//				 globalVarsMap.put("managerLastName", orgLoc.getLastName());
				 
//				 if("REOPEN".equalsIgnoreCase(reason)){
					 globalVarsMap.put("reasonForReopen",reason);
//				 }
				 StringBuilder acceptBuilder=new StringBuilder();
			     StringBuilder rejectBuilder=new StringBuilder();
			     globalVarsMap.put("accept",acceptBuilder.append("<a href='").append(sourceleadUrl).append("/ws/acceptOrRejectReopenExpense?type=accept&expenseId=").append(expenses.getId()).append("&reOpenedUserId=").append(loginUser.getId()).append("&orgLocId=").append(orgLoc.getId()).append("&reason=").append(reason).append("'style='text-decoration:none;float:left; margin-left:55px;'><div style=' width:220px; border-radius:30px; background-color:#5cb85c; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Accept</div></a>").toString());
				 globalVarsMap.put("reject",rejectBuilder.append("<a href='").append(sourceleadUrl).append("/ws/acceptOrRejectReopenExpense?type=rejecting&expenseId=").append(expenses.getId()).append("&reOpenedUserId=").append(loginUser.getId()).append("&orgLocId=").append(orgLoc.getId()).append("&reason=").append(reason).append("'style='text-decoration:none;float:left;margin-left:55px;'><div style=' width:220px; border-radius:30px; background-color:#e74c51; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Reject</div></a>").toString());
			
				 
//				 StringBuilder loginURLSB = new StringBuilder();
//				 globalVarsMap.put("loginURL",loginURLSB.append("<a href='").append(sourceleadUrl).append("/").append("'style='text-decoration:none;'><div style=' width:220px; border-radius:30px; background-color:#214b90; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Login</div></a>").toString());
//				 
				 //setting the Recipient information
				 InternetAddress[] iAdressArray = InternetAddress.parse(emailMessage.getToEmail());
				 
				 //getting the current EmailScenario by EmailScenario unique name and entity type
				 EmailScenario  emailScenario = null;
//				 if("REOPEN".equalsIgnoreCase(reason)){
					 emailScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.MAIL_APPROVERS_REOPEN_EXPENSE,Constants.ENTITY_TYPE_EMAIL);
//				 }
//				 if("APPROVED".equalsIgnoreCase(status)){
//					 emailScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.MAIL_TEAM_MANAGER_APPROVED_EXPENSE,Constants.ENTITY_TYPE_EMAIL);
//				 }
				 
				
				
					 
					 String subject = emailScenerioUtil.bulidTemplateSubject(emailScenario.getSubject(),globalVarsMap,emailScenario);
					 String body = emailScenerioUtil.buildTemplateBody(emailScenario.getBody(),globalVarsMap,emailScenario);
					 String signature = null;
					 if(StringUtils.isNotBlank(emailScenario.getSignature())){
						 signature = emailScenerioUtil.buildTemplateSignature(emailScenario.getSignature(),globalVarsMap,emailScenario); 
					 }
					 String url = sourceleadUrl;
				 
				 //construct HTML template using required data
				 String emailTemplate = emailScenerioUtil.AssembleEmailMessage(emailScenario,emailMessage, subject, body, signature, url, orgLoc.getOrg(), orgLoc);
				 emailMessage.setSubject(subject);
				 emailMessage.setMessageBody(emailTemplate);	 
					 
					 
				 emailScenerioUtil.sendEmail(emailMessage,orgLoc.getId(),iAdressArray,new ArrayList<EmailAttachment>());
				 
				 EmailScenario  notificationScenario = null;
//				 if("REOPEN".equalsIgnoreCase(reason)){
					 notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.MAIL_APPROVERS_REOPEN_EXPENSE,Constants.ENTITY_TYPE_NOTIFICATION);
//				 }
//				 if("APPROVED".equalsIgnoreCase(status)){
//					 notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.MAIL_TEAM_MANAGER_APPROVED_EXPENSE,Constants.ENTITY_TYPE_NOTIFICATION);
//				 }
				 // TODO @Rajashekar notificationScenario.getBody() may throw NullPointerException please check it once 
				String notificationBody = emailScenerioUtil.buildTemplateBody(notificationScenario.getBody(),globalVarsMap,notificationScenario);
				notificationScenario.setBody(notificationBody);
				notificationScenario.setOrgCode(orgLoc.getOrg().getOrgCode());
				String actionLink = "";
				emailScenerioUtil.sendNotification(actionLink,loginUser,pah.getApproverUserId(), orgLoc, notificationScenario,new HashMap<String,String>()) ;
		 	   }
	
		@RequestMapping(value = "/changeStatusToBillable",method=RequestMethod.POST)
		@ResponseBody
		public Response<String> changeStatusToBillable(HttpServletRequest request, @RequestParam(defaultValue = "0") long ExpenseEntriesId, @RequestParam(defaultValue = "non-billable") String billableStatus) {
			Response<String> response = new Response<>();
			try {
				User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
				if (user != null) {
					response = addExpensesService.changeStatusToBillable(ExpenseEntriesId,billableStatus);
				} else {
					response.setResponse(Result.SESSION_TIMEDOUT.name());
					response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
				}
			} catch (Exception e) {
				response.setResponse(Result.AWKWARD.name());
				response.setResponseMessage(Result.AWKWARD.getValue());
			}
			return response;
		}

	 
	 
}