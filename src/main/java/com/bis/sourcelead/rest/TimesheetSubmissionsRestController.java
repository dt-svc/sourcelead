package com.bis.sourcelead.rest;


import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
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
import com.bis.sourcelead.entity.TimesheetSubmissions;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.enums.Submission_Status;
import com.bis.sourcelead.service.EmailScenarioService;
import com.bis.sourcelead.service.EmployeesService;
import com.bis.sourcelead.service.SubmissionApproverHierarchyService;
import com.bis.sourcelead.service.TimesheetSubmissionsService;
import com.bis.sourcelead.util.CalendarUtil;
import com.bis.sourcelead.util.EmailScenerioUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@RestController 
public class TimesheetSubmissionsRestController {
	
	 
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	@Autowired
	public TimesheetSubmissionsService timesheetSubmissionsService;
	
	@Autowired
	private SubmissionApproverHierarchyService submissionApproverHierarchyService;
	
	@Autowired
	private ApplicationContext applicationContext;
	
	@Autowired
	private EmployeesService employeesService;
	
	@Autowired
	private DataSource dataSource;
	
	@Autowired
	EmailScenarioService emailScenarioService;
	
	@Autowired
	EmailScenerioUtil emailScenerioUtil;
	
	// sarath kumar punnana : this  controller method is for withdraw  timwsheet entries.
	@RequestMapping(value = "/withDrawTimesheet", method = RequestMethod.POST)
	public @ResponseBody void withDrawTimesheet(@RequestParam(value="submissionId", required=false) String submissionId) throws Exception {
				
		submissionApproverHierarchyService.updateSubmissionStatus(Long.parseLong(submissionId), Submission_Status.DRAFT.getValue());
	}
		

	
	@RequestMapping(value = "/getArrovalSubmissions",  method = RequestMethod.POST)
	public @ResponseBody  String getArrovalSubmissions(HttpServletRequest request, @RequestParam(value="employeeId", required=false) String employeeId) throws Exception {
		
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		String output = timesheetSubmissionsService.getArrovalSubmissions(employeeId, user);
		return output;
         
	}
	
	
	// sarath kumar punnana : this controller  method is for get new timwsheet screen at page load.
	@RequestMapping(value = "/dynamicTimesheet", method = RequestMethod.POST )    
	public @ResponseBody String dynamicTimesheet(HttpServletRequest request,@RequestParam(value="navigation", required=false) String navigation,@RequestParam(value="startDate", required=false) String startDate,@RequestParam(value="memberId", required=false) Long memberId,@RequestParam(value="projectMemberUserId", required=false) Long projectMemberUserId,@RequestParam(value="employeeId", required=false) Long employeeId) throws Exception {
		 
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		String output = timesheetSubmissionsService.dynamicTimesheet(user, navigation, startDate, memberId, projectMemberUserId, employeeId);
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
		String pattern = "dd-MM-yyyy";
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        Date sDate  =  Constants.DMY.parse(startDate);
        
        
        submissionApproverHierarchyService.updateApprovalStatusForApprover(employeeId, user.getId(), sDate, approvalStatus, rejectedReason);
        
        sendEmailToEmployeeTimesheetApprovalStatus(employeeId, user, startDate, endDate, approvalStatus, rejectedReason, loginOrgLocation, loginOrgCode);
        
      /*  Collection<TimesheetSubmissions> timesheetSubmissionsList = timesheetSubmissionsService.getTimesheetSubmissionsByAproverId(employeeId, user.getId(), sDate);
        if(timesheetSubmissionsList!=null){
        	for(TimesheetSubmissions timesheetSubmissions :timesheetSubmissionsList){
	             if(approvalStatus.equalsIgnoreCase("REJECTED") && approvalStatus!=null){
	            	 	timesheetSubmissions.setSubmissionApprovalStatus(Submission_ApprovalStatus.REJECTED);
	            	 	timesheetSubmissions.setSubmissionStatus(Submission_Status.REJECTED);
	            	 	timesheetSubmissions.setRejectedReason(rejectedReason);
	             }else if(approvalStatus.equalsIgnoreCase("APPROVED") && approvalStatus!=null){
	            	 timesheetSubmissions.setSubmissionApprovalStatus(Submission_ApprovalStatus.APPROVED);
	            	 timesheetSubmissions.setSubmissionStatus(Submission_Status.APPROVED);
	             }
	             
	             timesheetSubmissionsService.updateTimesheetSubmissions(timesheetSubmissions);
        	}  
        }*/
        return "success";
    }
	
	
	@RequestMapping(value = "/downloadDocument", method = RequestMethod.GET)
    public void downloadDocument(@RequestParam(value="submissionId", required=false) Long submissionId ,HttpServletResponse response) throws Exception {
		
		    TimesheetSubmissions timesheetSubmissions =    timesheetSubmissionsService.getTimesheetSubmissions(submissionId.toString());
		    
		    response.setContentType("application/octet-stream");
	        response.setHeader("Content-Disposition",
	                "attachment;filename="+timesheetSubmissions.getFileName());
		    
			InputStream inputStream = new ByteArrayInputStream(timesheetSubmissions.getDocFile());
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
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}  
		 

	
	@RequestMapping(value = "timesheetSubmissions/saveOrUpdate", method = RequestMethod.GET)
	public @ResponseBody  TimesheetSubmissions addTimesheetSubmissions() throws Exception
			 
	{
		TimesheetSubmissions timesheetSubmissions = new TimesheetSubmissions();
		
		ProjectMembers projectMembers = new ProjectMembers();
		projectMembers.setId(1l);
		timesheetSubmissions.setProjectMembers(projectMembers);
		timesheetSubmissions.setStartDate(new Date());
		timesheetSubmissions.setEndDate(new Date());
		/*timesheetSubmissions.setSubmissionStatus("ok");*/
		timesheetSubmissions.setStatus("Active");
		timesheetSubmissions.setCreatedDate(new Date());
		timesheetSubmissions.setCreatedBy("prasad");
		timesheetSubmissions.setUpdatedDate(new Date());
		timesheetSubmissions.setUpdatedBy("venket");
		
		TimesheetSubmissions savedTimesheetSubmissionss = timesheetSubmissionsService.addTimesheetSubmissions(timesheetSubmissions);
		return savedTimesheetSubmissionss; 
	}
	
	
	@RequestMapping(value = "timesheetSubmissions/update", method = RequestMethod.GET)
	public @ResponseBody  TimesheetSubmissions updateTimesheetSubmissions() throws Exception
			 
	{
		TimesheetSubmissions timesheetSubmissions = new TimesheetSubmissions();
		timesheetSubmissions.setId((long) 1);
		ProjectMembers projectMembers = new ProjectMembers();
		projectMembers.setId(1l);
		timesheetSubmissions.setProjectMembers(projectMembers);
		timesheetSubmissions.setStartDate(new Date());
		timesheetSubmissions.setEndDate(new Date());
		/*timesheetSubmissions.setSubmissionStatus("False");*/
		timesheetSubmissions.setStatus("Reject");
		timesheetSubmissions.setCreatedDate(new Date());
		timesheetSubmissions.setCreatedBy("Sri");
		timesheetSubmissions.setUpdatedDate(new Date());
		timesheetSubmissions.setUpdatedBy("Sai");
		
		TimesheetSubmissions updtaeTimesheetSubmissionss = timesheetSubmissionsService.updateTimesheetSubmissions(timesheetSubmissions);
		return updtaeTimesheetSubmissionss; 
	}
	
	
	@RequestMapping(value = "timesheetSubmissions/delete/{id}", method = RequestMethod.GET)
	public @ResponseBody  Boolean deleteTimesheetSubmissions(@PathVariable String id) throws Exception
			 
	{
		Boolean status = timesheetSubmissionsService.deleteTimesheetSubmissions(id);
		return status; 
	}
	
	@RequestMapping(value = "/deleteSubmissionDocument", method = RequestMethod.POST)
    public String deleteSubmissionDocument(@RequestParam(value="submissionId", required=false) Long submissionId ,HttpServletResponse response) throws Exception {
        
            TimesheetSubmissions timesheetSubmissions =    timesheetSubmissionsService.getTimesheetSubmissions(submissionId.toString());
            timesheetSubmissions.setDocFile(null);
            timesheetSubmissions.setFileContentType(null);
            timesheetSubmissions.setFileName(null);
            timesheetSubmissionsService.updateTimesheetSubmissions(timesheetSubmissions);
            return "success";
    }
	
	private void sendEmailToEmployeeTimesheetApprovalStatus(Long empId, User loginUser, String startDate, String endDate, String status, String rejectedReason, OrgLocations loginOrgLocation, String loginOrgCode) throws Exception {
		
			 Employees emp = employeesService.getEmployee(empId.toString());
		
	     	 EmailMessage emailMessage = new EmailMessage();
	     	 emailMessage.setFirstName(emp.getUser().getFirstName());
		 	 emailMessage.setLastName(emp.getUser().getLastName());
		 	 emailMessage.setToEmail(emp.getUser().getEmail());
		 	 emailMessage.setFromEmail("peopleaxisindia@gmail.com");
		 	 emailMessage.setFromName(loginUser.getFirstName()+" "+loginUser.getLastName());
	
			 //setting the class filed values
			 Map<String, String> globalVarsMap = new HashMap<String, String>();
			 globalVarsMap.put("loginUserFirstName",loginUser.getFirstName());
			 globalVarsMap.put("loginUserLastName",loginUser.getLastName());
			 globalVarsMap.put("employeeFirstName",emp.getUser().getFirstName());
			 globalVarsMap.put("employeeLastName",emp.getUser().getLastName());
			 globalVarsMap.put("orgName",loginOrgLocation.getOrg().getOrgName());
			 globalVarsMap.put("timeFrequency",startDate+" to "+endDate);
			 globalVarsMap.put("managerFirstName", loginUser.getFirstName());
			 globalVarsMap.put("managerLastName", loginUser.getLastName());
			 if(status.equalsIgnoreCase("REJECTED")){
				 globalVarsMap.put("rejectedReason",rejectedReason);
			 }
			 globalVarsMap.put("loginURL","<a href='"+sourceleadUrl+"/"+"' style='text-decoration:none; '><div style=' width:220px; border-radius:30px; background-color:#214b90; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Login</div></a>");
			 
			 //setting the Recipient information
			 ArrayList<EmailRecipient> emailRecipientList = new ArrayList<EmailRecipient>();
			 EmailRecipient emailRecipient = new EmailRecipient();
			 emailRecipient.setEmail(emailMessage.getToEmail());
			 emailRecipient.setName(emailMessage.getFirstName()+" "+emailMessage.getLastName());
			 emailRecipientList.add(emailRecipient);
			 
			 //getting the current EmailScenario by EmailScenario unique name and entity type
			 EmailScenario  emailScenario = null;
			 if(status.equalsIgnoreCase("REJECTED")){
				 emailScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.MAIL_TEAM_MEMBERS_REJECTED_TIMESHEET,Constants.ENTITY_TYPE_EMAIL);
			 }
			 if(status.equalsIgnoreCase("APPROVED")){
				 emailScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.MAIL_TEAM_MEMBERS_APPROVED_TIMESHEET,Constants.ENTITY_TYPE_EMAIL);
			 }
			 
			
			 //covert subject,body and signature
			 String subject = emailScenerioUtil.bulidSubject(emailScenario.getSubject(),globalVarsMap);
			 String body = emailScenerioUtil.buildTempBody(emailScenario.getBody(),globalVarsMap);
			 String signature = emailScenerioUtil.buildSignature(emailScenario.getSignature(),globalVarsMap);
			 String url = sourceleadUrl;;
			 
			 //construct HTML template using required data
			 String emailTemplate = emailScenerioUtil.AssembleEmailMessage(emailScenario,emailMessage, subject, body, signature, url, new Org());
			 emailMessage.setSubject(subject);
			 emailMessage.setMessageBody(emailTemplate);
			 
			 //calling the sendEmail method of emailScenerioUtil
			 emailScenerioUtil.sendEmail(emailMessage,emailRecipientList,new ArrayList<EmailAttachment>());
			 
			 
			 //calling the sendNotification method of emailScenerioUtil
			 EmailScenario  notificationScenario = null;
			 if(status.equalsIgnoreCase("REJECTED")){
				 notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.MAIL_TEAM_MEMBERS_REJECTED_TIMESHEET,Constants.ENTITY_TYPE_NOTIFICATION);
			 }
			 if(status.equalsIgnoreCase("APPROVED")){
				 notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.MAIL_TEAM_MEMBERS_APPROVED_TIMESHEET,Constants.ENTITY_TYPE_NOTIFICATION);
			 }
			String notificationBody = emailScenerioUtil.buildBody(notificationScenario.getBody(),globalVarsMap);
			notificationScenario.setBody(notificationBody);
			notificationScenario.setOrgCode(loginOrgCode);
			//String url = null;
			String actionLink = "";
			emailScenerioUtil.sendNotification(actionLink, loginUser, emp.getUser(), loginOrgLocation, notificationScenario);
	}
	
	public static boolean isNullOrEmpty(String str) {
			return (str == null || "".equals(str.trim()));
    }
 
	/*	json = "{"+json+"}";
	JSONObject jsonObj = new JSONObject(json);
	Gson gson =  new GsonBuilder().setDateFormat("yyyy-MM-dd").create();*/
	
	/*String result = "";
	String resultJson = null;
	
	String localsubmissionId = jsonObj.getString("submissionId");
    localsubmissionId = localsubmissionId.replaceAll("\\[", "").replaceAll("\\]","");
    String[] submissionIds = localsubmissionId.split(",");
	
	if(submissionIds != null && submissionIds.length>0){ 
		for(String submissionId : submissionIds){*/
			  	
			  	/*TimesheetSubmissions timesheetSubmissions = timesheetSubmissionsService.getTimesheetSubmissions(submissionId);
			  	timesheetSubmissions.setSubmissionStatus(Submission_Status.DRAFT);
			    timesheetSubmissions.setSubmissionApprovalStatus(Submission_ApprovalStatus.NEW);
			  	timesheetSubmissionsService.addTimesheetSubmissions(timesheetSubmissions);
		}
	}*/
}
