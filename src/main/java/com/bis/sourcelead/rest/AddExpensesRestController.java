package com.bis.sourcelead.rest;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.mail.internet.InternetAddress;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.OrgLocationsDao;
import com.bis.sourcelead.entity.EmailAttachment;
import com.bis.sourcelead.entity.EmailMessage;
import com.bis.sourcelead.entity.EmailScenario;
import com.bis.sourcelead.entity.Expenses;
import com.bis.sourcelead.entity.ExpensesApproverHierarchy;
import com.bis.sourcelead.entity.ExpensesEntryDocuments;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgRelations;
import com.bis.sourcelead.entity.ProjectMembers;
import com.bis.sourcelead.entity.Projects;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.enums.Submission_Status;
import com.bis.sourcelead.service.AddExpensesService;
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
public class AddExpensesRestController {
	private static final Logger logger = LoggerFactory.getLogger(TimesheetRestController.class);
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	@Autowired
	private ProjectsService projectsService; 
	
	@Autowired
	private OrgRelationsService orgRelationsService;
	
	@Autowired
	private OrgLocationService orgLocationService;
	
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
	
	
	@RequestMapping(value = "/acceptOrRejectReopenExpenseBySuperUser", method = RequestMethod.POST)
	public @ResponseBody void acceptOrRejectReopenExpenseBySuperUser(@RequestParam(value="expenseId", required=false) Long expenseId,
			@RequestParam(value="reOpenedUserId", required=false) Long reOpenedUserId,
			@RequestParam(value="type", required=false) String type,
			@RequestParam(value="reason", required=false) String reason, HttpServletRequest request) throws Exception {
			
		    User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		    OrgLocations orgLoc = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		    User reopenedUser = userService.getUserById(reOpenedUserId);
			if(expenseId != null && null != type && "accept".equalsIgnoreCase(type)){
				expensesApprovalService.updateExpenseStatusByExpenseIdAndReOpenReason(expenseId,reason,loginUser,type,Constants.YES);
			    //send mail & notification logic
			  	//submissionApproverHierarchyService.sendReopenTimeSheetAcceptOrRejectMail(projectMemberId,reason,loginUser,orgLoc,timesheetFrequency,type,reopenedUser);
				Expenses expense = expensesService.getExpenseByExpenseId(expenseId);
				User superUser = userService.getUserById(reOpenedUserId);
				ExpensesApproverHierarchy sah = expensesApprovalService.getExpensesApproverHierarchyByApproverUserIdAndExpenseId(loginUser.getId(), expenseId);
				expensesApprovalService.sendReopenExpenseAcceptOrRejectMail(expense,reason,superUser,orgLoc,type,reopenedUser, sah.getExpenseId().getProjectMembers().getProjects().getProjectName());
			  	
			}else if(expenseId != null &&  null != type && "reject".equalsIgnoreCase(type) && null != reason && !"".equalsIgnoreCase(reason)){
				expensesApprovalService.updateExpenseStatusByExpenseIdAndReOpenReason(expenseId,reason,loginUser,type,Constants.YES);
			    //send mail & notification logic
			  	//submissionApproverHierarchyService.sendReopenTimeSheetAcceptOrRejectMail(projectMemberId,reason,loginUser,orgLoc,timesheetFrequency,type,reopenedUser);
				Expenses expense = expensesService.getExpenseByExpenseId(expenseId);
				User superUser = userService.getUserById(reOpenedUserId);
				ExpensesApproverHierarchy sah = expensesApprovalService.getExpensesApproverHierarchyByApproverUserIdAndExpenseId(loginUser.getId(), expenseId);
				expensesApprovalService.sendReopenExpenseAcceptOrRejectMail(expense,reason,superUser,orgLoc,type,reopenedUser, sah.getExpenseId().getProjectMembers().getProjects().getProjectName());

			}
		
	}
	
	
	
	 	  

	@RequestMapping(value = "/notifyReopenExpense", method = RequestMethod.POST)
	public @ResponseBody void notifyReopenExpense(@RequestParam(value="expenseId", required=false) Long expenseId,
			@RequestParam(value="reason", required=false) String reason, HttpServletRequest request) throws Exception {
			
		    User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		    OrgLocations orgLoc = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		    
		    String isSuperUser = Constants.NO;
		   // SimpleDateFormat simpleDateFormat = orgLoc.getLocFullFormDateFormatter();
		    if(null != orgLoc.getUser() && null != orgLoc.getUser().getId() && orgLoc.getUser().getId().longValue() == loginUser.getId().longValue()){
		    	isSuperUser = Constants.YES;
		    }
		    
			//DateFormat format = simpleDateFormat;
			if(expenseId != null){
			 	
				expensesApprovalService.updateExpenseStatusByExpenseIdAndReOpenReason(expenseId,reason,loginUser,"notify",isSuperUser);
				sendEmailToSuperUserForReOpenExpenseApprovalStatus(expenseId,loginUser,orgLoc,reason);
			    //send mail & notification logic
			  	/*if(isSuperUser.equalsIgnoreCase(Constants.NO)){
			  		submissionApproverHierarchyService.sendReopenTimeSheetMail(projectMemberId,reason,loginUser,orgLoc,timesheetFrequency,totalHours);
			  	}*/
			  	
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
	
	
	@RequestMapping(value = "/storeAddExpensesData",method = RequestMethod.POST)
	public @ResponseBody String storeAddExpensesData(MultipartHttpServletRequest request,@RequestParam(value="status", required=false) String status) throws Exception {
		User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations orgLoc = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		JSONObject jsonObj = new JSONObject(request.getParameter("json"));
		addExpensesService.storeAddExpensesData(jsonObj,loginUser,orgLoc,request,status);
		return "DataSaved";
}
	
	 @RequestMapping(value = "/getMembersForSelectedProject",  method = RequestMethod.GET)
	 public @ResponseBody  String getMembersForSelectedProject(
			@RequestParam(value="projectId", required=false) Long projectId, HttpServletRequest request) throws Exception {
	 	
		 	Collection<ProjectMembers> projectMembers = null;
		 	if(projectId != null){
				projectMembers = projectMembersService.getAllProjectMembersByProjectId(projectId);
				
				if(null != projectMembers && projectMembers.size() > 0){
					for(ProjectMembers pm : projectMembers){
						if(null != pm.getEmployee().getUser()){
							StringBuilder eName = new StringBuilder();
							
							if(null != pm.getEmployee().getUser() && (pm.getEmployee().getUser().getFirstName() != null && pm.getEmployee().getUser().getFirstName() != "" && pm.getEmployee().getUser().getLastName() != null && pm.getEmployee().getUser().getLastName() != "" )){
								pm.setEmpName(eName.append(pm.getEmployee().getUser().getFirstName()).append(" ").append(pm.getEmployee().getUser().getLastName()).toString());
							}else{
								pm.setEmpName(eName.append(pm.getEmployee().getUser().getFirstName()).toString());
							}
							
						}
					}
				}
		 	}
		 	return commonUtil.toJSON(projectMembers);
	}
	 
	 
	 @RequestMapping(value = "/downloadExpensesEntryDocument", method = RequestMethod.GET)
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

		} 
	 
	 
	
	 
	 private void sendEmailToSuperUserForReOpenExpenseApprovalStatus(Long expenseId, User loginUser, OrgLocations orgLoc, String reason) throws Exception {
			
			ExpensesApproverHierarchy sah = expensesApprovalService.getExpensesApproverHierarchyByApproverUserIdAndExpenseId(loginUser.getId(), expenseId);
			
		 		 EmailMessage emailMessage = new EmailMessage();
		     	 emailMessage.setFirstName(sah.getExpenseId().getProjectMembers().getEmployee().getUser().getFirstName());
			 	 emailMessage.setLastName(sah.getExpenseId().getProjectMembers().getEmployee().getUser().getLastName());
			 	 
			// 	OrgLocations ol = orgLocationsDao.getOrgLocations(orgLoc.getId());
			 	 
			 	 emailMessage.setToEmail(orgLoc.getUser().getEmail());
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
//					 StringBuilder manager =new StringBuilder();
					 globalVarsMap.put("superUser", orgLoc.getUser().getFullName());
				 }else{
					 globalVarsMap.put("superUser",orgLoc.getUser().getFullName());
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
				 
//				 if(null != sah.getExpenseId()){
//					 if(null != sah.getExpenseId().getExpenseId()){
//				 globalVarsMap.put("expenseId",sah.getExpenseId().getExpenseId());
//					 }
//				 }
				 //SimpleDateFormat dateFormat=orgLoc.getLocFullFormDateFormatter();
				 if(null != sah.getExpenseId()){
					 if(null != sah.getExpenseId().getStartDate()){
				 globalVarsMap.put("startDate",commonUtil.dateStrFromOrgDateFormatter(orgLoc.getDateFormat(),sah.getExpenseId().getStartDate()));
					 }
				 }	 
				 if(null != sah.getExpenseId()){
					 if(null != sah.getExpenseId().getEndDate()){
				 globalVarsMap.put("endDate",commonUtil.dateStrFromOrgDateFormatter(orgLoc.getDateFormat(),sah.getExpenseId().getEndDate()));
					 }
				 }
				 globalVarsMap.put("amount",Float.toString(sah.getExpenseId().getTotalAmount()));
				 
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
			     globalVarsMap.put("accept",acceptBuilder.append("<a href='").append(sourceleadUrl).append("/acceptOrRejectReopenExpense?type=accept&expenseId=").append(expenseId).append("&reOpenedUserId=").append(loginUser.getId()).append("&reason=").append(reason).append("'style='text-decoration:none;float:left; margin-left:55px;'><div style=' width:220px; border-radius:30px; background-color:#5cb85c; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Accept</div></a>").toString());
				 globalVarsMap.put("reject",rejectBuilder.append("<a href='").append(sourceleadUrl).append("/acceptOrRejectReopenExpense?type=rejecting&expenseId=").append(expenseId).append("&reOpenedUserId=").append(loginUser.getId()).append("&reason=").append(reason).append("'style='text-decoration:none;float:left;margin-left:55px;'><div style=' width:220px; border-radius:30px; background-color:#e74c51; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Reject</div></a>").toString());
			
				 
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
				 
				
				 // TODO @Rajashekar notificationScenario.getBody() may throw NullPointerException please check it once 
				 String subject = emailScenerioUtil.bulidSubject(emailScenario.getSubject(),globalVarsMap);
				 String body = emailScenerioUtil.buildTempBody(emailScenario.getBody(),globalVarsMap);
				 String signature = emailScenerioUtil.buildSignature(emailScenario.getSignature(),globalVarsMap);
				 String url = sourceleadUrl;
				 String emailTemplate = emailScenerioUtil.AssembleEmailMessage(emailScenario,emailMessage, subject, body, signature, url, orgLoc.getOrg(),orgLoc);
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
				String notificationBody = emailScenerioUtil.buildBody(notificationScenario.getBody(),globalVarsMap);
				notificationScenario.setBody(notificationBody);
				notificationScenario.setOrgCode(orgLoc.getOrg().getOrgCode());
				String actionLink = "";
				emailScenerioUtil.sendNotification(actionLink,loginUser,orgLoc.getUser(), orgLoc, notificationScenario) ;
		 	   
			
		}
	
}