package com.bis.sourcelead.rest;


import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.mail.internet.InternetAddress;
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
import com.bis.sourcelead.entity.ProjectMembers;
import com.bis.sourcelead.entity.SubmissionApproverHierarchy;
import com.bis.sourcelead.entity.TimesheetSubmissions;
import com.bis.sourcelead.entity.TimesheetSubmissionsDocuments;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.enums.Submission_Status;
import com.bis.sourcelead.service.EmailScenarioService;
import com.bis.sourcelead.service.EmployeesService;
import com.bis.sourcelead.service.ProjectMembersService;
import com.bis.sourcelead.service.SubmissionApproverHierarchyService;
import com.bis.sourcelead.service.TimesheetSubmissionsDocumentsService;
import com.bis.sourcelead.service.TimesheetSubmissionsService;
import com.bis.sourcelead.util.EmailScenerioUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;
import com.bis.sourcelead.web.LoginSessionController;

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
	private ProjectMembersService projectMembersService;
	
	@Autowired
	EmailScenarioService emailScenarioService;
	
	@Autowired
	EmailScenerioUtil emailScenerioUtil;
	
	@Autowired
	TimesheetSubmissionsDocumentsService timesheetSubmissionsDocumentsService;
	

	@PreAuthorize("hasAnyRole('ROLE_FILL_MEMBER_TIMESHEETS','ROLE_VIEW_MEMBER_TIMESHEETS','ROLE_EXPORT_MEMBER_TIMESHEETS')") 
	@RequestMapping(value = "/getArrovalSubmissions",  method = RequestMethod.POST)
	public @ResponseBody  String getArrovalSubmissions(HttpServletRequest request, @RequestParam(value="employeeId", required=false) String employeeId) throws Exception {
		
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations orgLoc = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		String output = timesheetSubmissionsService.getArrovalSubmissions(employeeId, user,orgLoc.getId());
		return output;
         
	}
	
	
	// sarath kumar punnana : this controller  method is for get new timwsheet screen at page load.
	@PreAuthorize("hasAnyRole('ROLE_FILL_MEMBER_TIMESHEETS','ROLE_VIEW_MEMBER_TIMESHEETS')")
	@RequestMapping(value = "/dynamicTimesheet", method = RequestMethod.POST )    
	public @ResponseBody String dynamicTimesheet(HttpServletRequest request,@RequestParam(value="navigation", required=false) String navigation,@RequestParam(value="startDate", required=false) String startDate,@RequestParam(value="memberId", required=false) Long memberId,@RequestParam(value="projectMemberUserId", required=false) Long projectMemberUserId,@RequestParam(value="employeeId", required=false) Long employeeId) throws Exception {
		 
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations orgLoc = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		String output = timesheetSubmissionsService.dynamicTimesheet(user, navigation, startDate, memberId, projectMemberUserId, employeeId,orgLoc.getId());
		return output;
	}

	
	/*@PreAuthorize("hasAnyRole('ROLE_FILL_MY_TIMESHEETS','ROLE_MODIFY_MY_TIMESHEETS','ROLE_FILL_MEMBER_TIMESHEETS','ROLE_VIEW_MEMBER_TIMESHEETS')")
	@RequestMapping(value = "/downloadDocument", method = RequestMethod.GET)
    public void downloadDocument(@RequestParam(value="submissionDocumentId", required=false) Long submissionDocumentId ,HttpServletResponse response) throws Exception {
		
		
			TimesheetSubmissionsDocuments	timesheetSubmissionsDocuments =	timesheetSubmissionsDocumentsService.getTimesheetSubmissionsDocuments(submissionDocumentId);	
		    //TimesheetSubmissions timesheetSubmissions =    timesheetSubmissionsService.getTimesheetSubmissions(submissionId.toString());
		    
		    response.setContentType("application/octet-stream");
		    StringBuilder fileName = new StringBuilder();
	        response.setHeader("Content-Disposition", fileName.append("attachment; filename=\"").append(timesheetSubmissionsDocuments.getFileName()).append("\"").toString());
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

		}  */
		 

	//unused method : default method
	@RequestMapping(value = "timesheetSubmissions/saveOrUpdate", method = RequestMethod.GET)
	public @ResponseBody  TimesheetSubmissions addTimesheetSubmissions() throws Exception
			 
	{
		TimesheetSubmissions timesheetSubmissions = new TimesheetSubmissions();
		return timesheetSubmissionsService.addTimesheetSubmissions(timesheetSubmissions);
	}
	
	//unused method : default method
	@RequestMapping(value = "timesheetSubmissions/update", method = RequestMethod.GET)
	public @ResponseBody  TimesheetSubmissions updateTimesheetSubmissions() throws Exception
			 
	{
		TimesheetSubmissions timesheetSubmissions = new TimesheetSubmissions();
		return  timesheetSubmissionsService.updateTimesheetSubmissions(timesheetSubmissions);
	}
	
	
	//unused method : default method
	@RequestMapping(value = "timesheetSubmissions/delete/{id}", method = RequestMethod.GET)
	public @ResponseBody  Boolean deleteTimesheetSubmissions(@PathVariable String id) throws Exception
			 
	{
		return timesheetSubmissionsService.deleteTimesheetSubmissions(id);
	}
	
	
	@PreAuthorize("hasAnyRole('ROLE_FILL_MEMBER_TIMESHEETS','ROLE_VIEW_MEMBER_TIMESHEETS')")
    @RequestMapping(value = "/saveSubmissionApprovalStatusByProjectMemberId", method = RequestMethod.POST)
    public  String saveSubmissionApprovalStatusByProjectMemberId(HttpServletRequest request,
                                                @RequestParam(value="projectMemberId", required=false) Long projectMemberId,
                                                @RequestParam(value="startDate", required=false) String startDate,
                                                @RequestParam(value="endDate", required=false) String endDate,
                                                @RequestParam(value="approvalStatus", required=false) String approvalStatus,@RequestParam(value="rejectedReason", required=false) String rejectedReason) throws Exception{
                                                 
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
	 	String loginOrgCode = (String) SourceLeadSessionManager.getSessionAttribute(request, "orgCode");
	 	DateFormat  sdf = LoginSessionController.DATEFORMAT;
        Date sDate  =  sdf.parse(startDate);
        Date eDate  =  sdf.parse(endDate);
        submissionApproverHierarchyService.updateApprovalStatusByApproverForProjectMemberId(projectMemberId, user.getId(), sDate,eDate,approvalStatus, rejectedReason);
        
       sendEmailToEmployeeTimesheetApprovalStatus(projectMemberId, user, startDate, endDate, approvalStatus, rejectedReason, loginOrgLocation, loginOrgCode);
        
        return "success";
    }
	
	/*@PreAuthorize("hasAnyRole('ROLE_FILL_MY_TIMESHEETS','ROLE_MODIFY_MY_TIMESHEETS','ROLE_FILL_MEMBER_TIMESHEETS','ROLE_VIEW_MEMBER_TIMESHEETS')")
	@RequestMapping(value = "/deleteSubmissionDocument", method = RequestMethod.POST)
    public String deleteSubmissionDocument(@RequestParam(value="submissionDocumentId", required=false) Long submissionDocumentId ,HttpServletResponse response) throws Exception {
        
            TimesheetSubmissions timesheetSubmissions =    timesheetSubmissionsService.getTimesheetSubmissions(submissionId.toString());
			TimesheetSubmissionsDocuments	timesheetSubmissionsDocuments =	timesheetSubmissionsDocumentsService.getTimesheetSubmissionsDocuments(submissionDocumentId);
            timesheetSubmissionsDocuments.setDocFile(null);
            timesheetSubmissionsDocuments.setFileContentType(null);
            timesheetSubmissionsDocuments.setFileName(null);
            timesheetSubmissionsDocuments.setStatus(Constants.CANDIDATE_IN_ACTIVE);
            timesheetSubmissionsDocumentsService.addTimesheetSubmissionsDocuments(timesheetSubmissionsDocuments);
            return "success";
    }*/
	
	private void sendEmailToEmployeeTimesheetApprovalStatus(Long pmId, User loginUser, String startDate, String endDate, String status, String rejectedReason, OrgLocations loginOrgLocation, String loginOrgCode) throws Exception {
		
			 //Employees emp = employeesService.getEmployee(empId.toString());
	 	 
			 SimpleDateFormat dateFormat=loginOrgLocation.getLocFullFormDateFormatter();
			 ProjectMembers projectMembers = projectMembersService.getProjectMemberById(pmId);
			// DateFormat format = Constants.DF_DMY;
			 Date frequencyStartDate = dateFormat.parse(startDate.replaceAll("/", "-"));
			 Date frequencyEndDate = dateFormat.parse(endDate.replaceAll("/", "-"));
		 
			 SubmissionApproverHierarchy sah =submissionApproverHierarchyService.getSubmissionApproverHierarchyForEmployeeByProjectMemberId(frequencyStartDate, frequencyEndDate, pmId);
			 Submission_Status submissionStatus = sah.getSubmissionStatus();
		 	 String submissionStatusStr = submissionStatus.getValue();
		 	  if(!isNullOrEmpty(submissionStatusStr) && 
		 			 (submissionStatusStr.equalsIgnoreCase(Submission_Status.getNameByValue("approved").getValue())
		 		     || submissionStatusStr.equalsIgnoreCase(Submission_Status.getNameByValue("rejected").getValue()))
		 	    )
		 	   {
		 		 EmailMessage emailMessage = new EmailMessage();
		     	 emailMessage.setFirstName(projectMembers.getEmployee().getUser().getFirstName());
			 	 emailMessage.setLastName(projectMembers.getEmployee().getUser().getLastName());
			 	 emailMessage.setToEmail(projectMembers.getEmployee().getUser().getEmail());
			 	 emailMessage.setFromEmail(loginUser.getEmail());
			 	 emailMessage.setCreatedBy(loginUser.getUserCode());
			 	 
			 	 StringBuilder userNameSB = new StringBuilder();
			 	 emailMessage.setFromName(userNameSB.append(loginUser.getFirstName()).append(" ").append(loginUser.getLastName()).toString());
			 	emailMessage.setEmailType(Constants.CUSTOM_USER_EMAIL);
				 //setting the class filed values
				 Map<String, String> globalVarsMap = new HashMap<>();
				 globalVarsMap.put("loginUserFirstName",loginUser.getFirstName());
				 globalVarsMap.put("loginUserLastName",loginUser.getLastName());
				 globalVarsMap.put("employeeFirstName",projectMembers.getEmployee().getUser().getFirstName());
				 globalVarsMap.put("employeeLastName",projectMembers.getEmployee().getUser().getLastName());
				 globalVarsMap.put("orgName",loginOrgLocation.getOrgLocationName());
				 globalVarsMap.put("projectName", projectMembers.getProjects().getProjectName());
				 globalVarsMap.put("frequencyType",projectMembers.getProjects().getInvoiceFrequency());
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
					 emailScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.MAIL_TEAM_MEMBERS_REJECTED_TIMESHEET,Constants.ENTITY_TYPE_EMAIL);
				 }
				 if("APPROVED".equalsIgnoreCase(status)){
					 emailScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.MAIL_TEAM_MEMBERS_APPROVED_TIMESHEET,Constants.ENTITY_TYPE_EMAIL);
				 }
				 
				
				 // TODO @Rajashekar notificationScenario.getBody() may throw NullPointerException please check it once 
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
				emailScenerioUtil.sendNotification(actionLink, loginUser, projectMembers.getEmployee().getUser(), loginOrgLocation, notificationScenario); 
		 	  }
		
	     	
	}
	
	public static boolean isNullOrEmpty(String str) {
			return str == null || "".equals(str.trim());
    }
 
}
