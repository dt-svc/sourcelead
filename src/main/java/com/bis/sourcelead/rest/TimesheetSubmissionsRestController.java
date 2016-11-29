package com.bis.sourcelead.rest;


import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.entity.EmailAttachment;
import com.bis.sourcelead.entity.EmailMessage;
import com.bis.sourcelead.entity.EmailRecipient;
import com.bis.sourcelead.entity.EmailScenario;
import com.bis.sourcelead.entity.Employees;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.TimesheetSubmissions;
import com.bis.sourcelead.entity.TimesheetSubmissionsDocuments;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.enums.Submission_Status;
import com.bis.sourcelead.service.EmailScenarioService;
import com.bis.sourcelead.service.EmployeesService;
import com.bis.sourcelead.service.SubmissionApproverHierarchyService;
import com.bis.sourcelead.service.TimesheetSubmissionsDocumentsService;
import com.bis.sourcelead.service.TimesheetSubmissionsService;
import com.bis.sourcelead.util.EmailScenerioUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;

@RestController 
public class TimesheetSubmissionsRestController {
	
	private static final Logger logger = LoggerFactory.getLogger(TimesheetSubmissionsRestController.class);
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	@Autowired
	public TimesheetSubmissionsService timesheetSubmissionsService;
	
	@Autowired
	private SubmissionApproverHierarchyService submissionApproverHierarchyService;
	
	@Autowired
	private EmployeesService employeesService;
	
	@Autowired
	EmailScenarioService emailScenarioService;
	
	@Autowired
	EmailScenerioUtil emailScenerioUtil;
	
	@Autowired
	TimesheetSubmissionsDocumentsService timesheetSubmissionsDocumentsService;
	
	// sarath kumar punnana : this  controller method is for withdraw  timwsheet entries.
	@PreAuthorize("hasRole('ROLE_MODIFY_MY_TIMESHEETS')")
	@RequestMapping(value = "/withDrawTimesheet", method = RequestMethod.POST)
	public @ResponseBody void withDrawTimesheet(@RequestParam(value="submissionId", required=false) String submissionId) throws Exception {
				
		submissionApproverHierarchyService.updateSubmissionStatus(Long.parseLong(submissionId), Submission_Status.DRAFT.getValue());
	}
		

	@PreAuthorize("hasAnyRole('ROLE_CREATE_TIMESHEETS_APPROVAL','ROLE_VIEW_TIMESHEETS_APPROVAL','ROLE_EDIT_TIMESHEETS_APPROVAL')") 
	@RequestMapping(value = "/getArrovalSubmissions",  method = RequestMethod.POST)
	public @ResponseBody  String getArrovalSubmissions(HttpServletRequest request, @RequestParam(value="employeeId", required=false) String employeeId) throws Exception {
		
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations orgLoc = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		String output = timesheetSubmissionsService.getArrovalSubmissions(employeeId, user,orgLoc.getId());
		return output;
         
	}
	
	
	// sarath kumar punnana : this controller  method is for get new timwsheet screen at page load.
	@RequestMapping(value = "/dynamicTimesheet", method = RequestMethod.POST )    
	public @ResponseBody String dynamicTimesheet(HttpServletRequest request,@RequestParam(value="navigation", required=false) String navigation,@RequestParam(value="startDate", required=false) String startDate,@RequestParam(value="memberId", required=false) Long memberId,@RequestParam(value="projectMemberUserId", required=false) Long projectMemberUserId,@RequestParam(value="employeeId", required=false) Long employeeId) throws Exception {
		 
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations orgLoc = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		String output = timesheetSubmissionsService.dynamicTimesheet(user, navigation, startDate, memberId, projectMemberUserId, employeeId,orgLoc.getId());
		return output;
	}
	
	@RequestMapping(value = "/saveSubmissionApprovalStatus", method = RequestMethod.POST)
    public  String saveSubmissionApprovalStatus(HttpServletRequest request,
									    		@RequestParam(value="employeeId", required=false) Long employeeId,
												@RequestParam(value="startDate", required=false) String startDate,
												@RequestParam(value="endDate", required=false) String endDate,
												@RequestParam(value="approvalStatus", required=false) String approvalStatus,@RequestParam(value="rejectedReason", required=false) String rejectedReason) throws Exception{
									             
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
	 	String loginOrgCode = (String) SourceLeadSessionManager.getSessionAttribute(request, "orgCode");
		Date sDate  =  Constants.DMY.parse(startDate);
        submissionApproverHierarchyService.updateApprovalStatusForApprover(employeeId, user.getId(), sDate, approvalStatus, rejectedReason);
        
        sendEmailToEmployeeTimesheetApprovalStatus(employeeId, user, startDate, endDate, approvalStatus, rejectedReason, loginOrgLocation, loginOrgCode);
        
        return "success";
    }
	
	
	@RequestMapping(value = "/downloadDocument", method = RequestMethod.GET)
    public void downloadDocument(@RequestParam(value="submissionDocumentId", required=false) Long submissionDocumentId ,HttpServletResponse response) throws Exception {
		
		
			TimesheetSubmissionsDocuments	timesheetSubmissionsDocuments =	timesheetSubmissionsDocumentsService.getTimesheetSubmissionsDocuments(submissionDocumentId);	
		    //TimesheetSubmissions timesheetSubmissions =    timesheetSubmissionsService.getTimesheetSubmissions(submissionId.toString());
		    
		    response.setContentType("application/octet-stream");
	        response.setHeader("Content-Disposition",
	                "attachment;filename="+timesheetSubmissionsDocuments.getFileName());
		    
			InputStream inputStream = new ByteArrayInputStream(timesheetSubmissionsDocuments.getDocFile());
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
		 

	
	@RequestMapping(value = "timesheetSubmissions/saveOrUpdate", method = RequestMethod.GET)
	public @ResponseBody  TimesheetSubmissions addTimesheetSubmissions() throws Exception
			 
	{
		TimesheetSubmissions timesheetSubmissions = new TimesheetSubmissions();
		return timesheetSubmissionsService.addTimesheetSubmissions(timesheetSubmissions);
	}
	
	
	@RequestMapping(value = "timesheetSubmissions/update", method = RequestMethod.GET)
	public @ResponseBody  TimesheetSubmissions updateTimesheetSubmissions() throws Exception
			 
	{
		TimesheetSubmissions timesheetSubmissions = new TimesheetSubmissions();
		return  timesheetSubmissionsService.updateTimesheetSubmissions(timesheetSubmissions);
	}
	
	
	@RequestMapping(value = "timesheetSubmissions/delete/{id}", method = RequestMethod.GET)
	public @ResponseBody  Boolean deleteTimesheetSubmissions(@PathVariable String id) throws Exception
			 
	{
		return timesheetSubmissionsService.deleteTimesheetSubmissions(id);
	}
	
	@RequestMapping(value = "/deleteSubmissionDocument", method = RequestMethod.POST)
    public String deleteSubmissionDocument(@RequestParam(value="submissionDocumentId", required=false) Long submissionDocumentId ,HttpServletResponse response) throws Exception {
        
            /*TimesheetSubmissions timesheetSubmissions =    timesheetSubmissionsService.getTimesheetSubmissions(submissionId.toString());*/
			TimesheetSubmissionsDocuments	timesheetSubmissionsDocuments =	timesheetSubmissionsDocumentsService.getTimesheetSubmissionsDocuments(submissionDocumentId);
            timesheetSubmissionsDocuments.setDocFile(null);
            timesheetSubmissionsDocuments.setFileContentType(null);
            timesheetSubmissionsDocuments.setFileName(null);
            timesheetSubmissionsDocuments.setStatus(Constants.CANDIDATE_IN_ACTIVE);
            timesheetSubmissionsDocumentsService.addTimesheetSubmissionsDocuments(timesheetSubmissionsDocuments);
            return "success";
    }
	
	private void sendEmailToEmployeeTimesheetApprovalStatus(Long empId, User loginUser, String startDate, String endDate, String status, String rejectedReason, OrgLocations loginOrgLocation, String loginOrgCode) throws Exception {
		
			 Employees emp = employeesService.getEmployee(empId.toString());
		
	     	 EmailMessage emailMessage = new EmailMessage();
	     	 emailMessage.setFirstName(emp.getUser().getFirstName());
		 	 emailMessage.setLastName(emp.getUser().getLastName());
		 	 emailMessage.setToEmail(emp.getUser().getEmail());
		 	 emailMessage.setFromEmail("peopleaxisindia@gmail.com");
		 	emailMessage.setCreatedBy(loginUser.getUserCode());
		 	 emailMessage.setFromName(loginUser.getFirstName()+" "+loginUser.getLastName());
	
			 //setting the class filed values
			 Map<String, String> globalVarsMap = new HashMap<>();
			 globalVarsMap.put("loginUserFirstName",loginUser.getFirstName());
			 globalVarsMap.put("loginUserLastName",loginUser.getLastName());
			 globalVarsMap.put("employeeFirstName",emp.getUser().getFirstName());
			 globalVarsMap.put("employeeLastName",emp.getUser().getLastName());
			 globalVarsMap.put("orgName",loginOrgLocation.getOrg().getOrgName());
			 globalVarsMap.put("timeFrequency",startDate+" to "+endDate);
			 globalVarsMap.put("managerFirstName", loginUser.getFirstName());
			 globalVarsMap.put("managerLastName", loginUser.getLastName());
			 if("REJECTED".equalsIgnoreCase(status)){
				 globalVarsMap.put("rejectedReason",rejectedReason);
			 }
			 globalVarsMap.put("loginURL","<a href='"+sourceleadUrl+"/"+"' style='text-decoration:none; '><div style=' width:220px; border-radius:30px; background-color:#214b90; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Login</div></a>");
			 
			 //setting the Recipient information
			 ArrayList<EmailRecipient> emailRecipientList = new ArrayList<>();
			 EmailRecipient emailRecipient = new EmailRecipient();
			 emailRecipient.setEmail(emailMessage.getToEmail());
			 emailRecipient.setName(emailMessage.getFirstName()+" "+emailMessage.getLastName());
			 emailRecipientList.add(emailRecipient);
			 
			 //getting the current EmailScenario by EmailScenario unique name and entity type
			 EmailScenario  emailScenario = null;
			 if("REJECTED".equalsIgnoreCase(status)){
				 emailScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.MAIL_TEAM_MEMBERS_REJECTED_TIMESHEET,Constants.ENTITY_TYPE_EMAIL);
			 }
			 if("APPROVED".equalsIgnoreCase(status)){
				 emailScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.MAIL_TEAM_MEMBERS_APPROVED_TIMESHEET,Constants.ENTITY_TYPE_EMAIL);
			 }
			 
			
			 // TODO @Rajashekar notificationScenario.getBody() may throw NullPointerException please check it once 
			 String subject = emailScenerioUtil.bulidSubject(emailScenario.getSubject(),globalVarsMap);
			 String body = emailScenerioUtil.buildTempBody(emailScenario.getBody(),globalVarsMap);
			 String signature = emailScenerioUtil.buildSignature(emailScenario.getSignature(),globalVarsMap);
			 String url = sourceleadUrl;;
			 String emailTemplate = emailScenerioUtil.AssembleEmailMessage(emailScenario,emailMessage, subject, body, signature, url, new Org());
			 emailMessage.setSubject(subject);
			 emailMessage.setMessageBody(emailTemplate);
			 emailScenerioUtil.sendEmail(emailMessage,loginOrgLocation.getId(),emailRecipientList,new ArrayList<EmailAttachment>());
			 EmailScenario  notificationScenario = null;
			 if("REJECTED".equalsIgnoreCase(status)){
				 notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.MAIL_TEAM_MEMBERS_REJECTED_TIMESHEET,Constants.ENTITY_TYPE_NOTIFICATION);
			 }
			 if("APPROVED".equalsIgnoreCase(status)){
				 notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.MAIL_TEAM_MEMBERS_APPROVED_TIMESHEET,Constants.ENTITY_TYPE_NOTIFICATION);
			 }
			 // TODO @Rajashekar notificationScenario.getBody() may throw NullPointerException please check it once 
			String notificationBody = emailScenerioUtil.buildBody(notificationScenario.getBody(),globalVarsMap);
			notificationScenario.setBody(notificationBody);
			notificationScenario.setOrgCode(loginOrgCode);
			String actionLink = "";
			emailScenerioUtil.sendNotification(actionLink, loginUser, emp.getUser(), loginOrgLocation, notificationScenario);
	}
	
	public static boolean isNullOrEmpty(String str) {
			return str == null || "".equals(str.trim());
    }
 
}
