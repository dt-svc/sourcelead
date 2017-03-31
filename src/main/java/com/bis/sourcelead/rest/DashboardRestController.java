package com.bis.sourcelead.rest;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

import javax.mail.internet.InternetAddress;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.json.JSONObject;
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
import com.bis.sourcelead.elk.index.RequirementIndex;
import com.bis.sourcelead.elk.setup.RequirementElkUtil;
import com.bis.sourcelead.elk.setup.SourceleadELKBaseIndexer;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.ConsolidatedReport;
import com.bis.sourcelead.entity.DashboardCounts;
import com.bis.sourcelead.entity.EmailAttachment;
import com.bis.sourcelead.entity.EmailMessage;
import com.bis.sourcelead.entity.EmailScenario;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.Requirement;
import com.bis.sourcelead.entity.RequirementApproverHierarchy;
import com.bis.sourcelead.entity.RequirementAssignTo;
import com.bis.sourcelead.entity.RequirementInterviewHistory;
import com.bis.sourcelead.entity.RequirementSubmission;
import com.bis.sourcelead.entity.Tasks;
import com.bis.sourcelead.entity.Team;
import com.bis.sourcelead.entity.TeamMembers;
import com.bis.sourcelead.entity.Timezone;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserRole;
import com.bis.sourcelead.enums.REQUIREMENT_APPROVER_HIERARCHY_STATUS;
import com.bis.sourcelead.enums.REQUIREMENT_SUBMISSION_STATUS;
import com.bis.sourcelead.enums.Status;
import com.bis.sourcelead.service.CandidateService;
import com.bis.sourcelead.service.EmailScenarioService;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.OrgService;
import com.bis.sourcelead.service.RequirementApproverHierarchyService;
import com.bis.sourcelead.service.RequirementAssignToService;
import com.bis.sourcelead.service.RequirementInterviewHistoryService;
import com.bis.sourcelead.service.RequirementInterviewService;
import com.bis.sourcelead.service.RequirementInterviewerService;
import com.bis.sourcelead.service.RequirementService;
import com.bis.sourcelead.service.RequirementSubmissionService;
import com.bis.sourcelead.service.TaskService;
import com.bis.sourcelead.service.TeamMembersService;
import com.bis.sourcelead.service.TeamService;
import com.bis.sourcelead.service.TimeZoneService;
import com.bis.sourcelead.service.TimesheetSubmissionsService;
import com.bis.sourcelead.service.UserRoleService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.EmailScenerioUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;

/**
 * This class is used for...
 * 
 * @author: Venkat Kesav
 * @date: 15-July-2016
 */

@RestController
public class DashboardRestController {

	
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	@Autowired
	public TimesheetSubmissionsService timesheetSubmissionsService;
	
	@Autowired
	public OrgLocationService orgLocationService;
	
	 @Autowired
	 RequirementService requirementService;
	 
	 @Autowired
	 RequirementInterviewerService requirementInterviewerService;
	 
	 @Autowired
	 RequirementAssignToService requirementAssignToService;
	 
	 @Autowired
	 EmailScenerioUtil emailScenerioUtil;
	 
	 @Autowired
	 EmailScenarioService emailScenarioService;
	
	 @Autowired
	 CandidateService candidateService;
	 
	 @Autowired
	 RequirementSubmissionService requirementSubmissionService;
	 
	 @Autowired
	 RequirementInterviewService requirementInterviewService;
	 
	 @Autowired
	 RequirementApproverHierarchyService requirementApproverHierarchyService;
	 
	 @Autowired
	 RequirementInterviewHistoryService requirementInterviewHistoryService;
	 
	 @Autowired
	 UserService userService;
	 
	 
	 @Autowired
	 TeamService teamService;
	 
	 @Autowired
	 TeamMembersService teamMembersService;
	 
	 
	 
	 @Autowired
	 OrgService orgService;
	 
	 @Autowired
     UserRoleService userRoleService;
	 
	 @Autowired
	 private RequirementElkUtil requirementElkUtil;
	 
	 @Autowired
	 private SourceleadELKBaseIndexer<RequirementIndex> sourceleadELKBaseIndexer;
	 
	 @Autowired
	 TimeZoneService timeZoneService;
	 
	@Autowired
	CommonUtil commonUtil;
	
	@Autowired
	TaskService taskService;
	/****
	 * This  controller method is   for getting TimesheetSubmission Counts in dashboard Landing Page Based on User Login
	 * @author: Sammeta David
	 */
	
	@PreAuthorize("hasAnyRole('ROLE_FILL_MY_TIMESHEETS','ROLE_EXPORT_MY_TIMESHEETS','ROLE_MODIFY_MY_TIMESHEETS','ROLE_FILL_MEMBER_TIMESHEETS','ROLE_VIEW_MEMBER_TIMESHEETS','ROLE_EXPORT_MEMBER_TIMESHEETS')")
	@RequestMapping(value = "/getTimeSheetSubmissionStatus")
	public String getTimeSheetSubmissionStatus(@RequestParam(value="timesheetType", required=false) String timesheetType,HttpServletRequest request) throws Exception {
		

		
		List<Org> orgList = orgService.getOrgs();
		for (Org org : orgList) {
			OrgLocations orgLocations = orgLocationService.getOrgLocationByOrgId(org.getId());
			ArrayList<Long> userIdList = new ArrayList<Long>();
			ArrayList<String> userCodeList = new ArrayList<String>();

			List<User> userRoleList = userService.getTabAccessOwnersByOrgLocationIdAndTabId(orgLocations.getId(),
					Long.valueOf(Constants.TAB_REQUIREMENT));

			if (userRoleList != null && userRoleList.size() > 0) {

				for (User user : userRoleList) {
							Integer  todayReleasedRequirementsCount  = 0;
							
							List<ConsolidatedReport> list = new ArrayList<ConsolidatedReport>();
							System.out.println("UserName" + user.getFirstName());
		
							String[] modules = org.getModulesId().split(",");
							for (int i = 0; i < modules.length; i++) {
									if (modules[i].equalsIgnoreCase(Constants.MODULE_VMS_STR)) {
										
										  todayReleasedRequirementsCount = requirementService.getAllRequirementsByOrglocationIdAndUserIdReleasedByUser(orgLocations.getId(), user);
										  break;
									}
									
							}
							List<Requirement> requirementList = requirementService.getAllRequirementsByOrglocationIdAndUserId(orgLocations.getId(), user);
							Integer releasedRequirementCount = 0;
							Integer numberOfActiveRequirementCount = 0;
							Integer numberOfCreatedCandidatesCount = 0;
		
							for (Requirement requirement : requirementList) {
		
										ConsolidatedReport conreport = new ConsolidatedReport();
				
										conreport.setRequirementName(requirement.getJobTitle());
										for (int i = 0; i < modules.length; i++) {
				
											
											/*
											 * This is for checking the login Organization is
											 * ATS or not And Get ALl Vendor Submitted Jobs
											 * */
											 
											 
											if (modules[i].equalsIgnoreCase(Constants.MODULE_ATS_STR)) {
				
												List<RequirementSubmission> submissionsFromClient = requirementSubmissionService.getAllSubmissionsfromVendorsBasedOnLocationIdAndUserId(user, orgLocations,
																"CLIENT_SUBMISSIONS", requirement);
												if (submissionsFromClient.size() > 0 && submissionsFromClient != null) {
													   conreport.setSubmissionCount(submissionsFromClient.size());
													   break;
												}
											}
											
										}
				
										Integer applicantsCount = requirementSubmissionService.getConsolidatedReportsForMailingForIndividualUserForToday(user, orgLocations,"APPLICANTS", requirement);
										conreport.setApplicantsCount(applicantsCount);
				
										Integer shortlistedCount = requirementSubmissionService.getConsolidatedReportsForMailingForIndividualUserForToday(user, orgLocations,
														"SHORTLISTED", requirement);
										conreport.setShortlistedCount(shortlistedCount);
				
										Integer submittedToClientCount = requirementSubmissionService.getConsolidatedReportsForMailingForIndividualUserForToday(user, orgLocations,
														"SUBMIT_TO_CLIENT", requirement);
										conreport.setSubmitToClientCount(submittedToClientCount);
				
										Integer interviewedCount = requirementSubmissionService.getConsolidatedReportsForMailingForIndividualUserForToday(user, orgLocations,
														"INTERVIEWED", requirement);
										conreport.setInterviewsCount(interviewedCount);
				
										Integer rejectedCount = requirementSubmissionService.getConsolidatedReportsForMailingForIndividualUserForToday(user, orgLocations,
														"REJECTED", requirement);
										conreport.setRejectedCount(rejectedCount);
				
										Integer requestSentCount = requirementSubmissionService.getConsolidatedReportsForMailingForIndividualUserForToday(user, orgLocations,
														"REQUESTSENT", requirement);
										conreport.setRequestSentCount(requestSentCount);
				
										Integer offeredCount = requirementSubmissionService.getConsolidatedReportsForMailingForIndividualUserForToday(user, orgLocations,
														"OFFERED", requirement);
										conreport.setOffersCount(offeredCount);
				
				  
										list.add(conreport);
				
									}
									numberOfCreatedCandidatesCount =	 candidateService.getAllCandidatedsBasedOnCreatedByAndOrglocationCodeWhichAreCreatedToday(orgLocations,user);
									sendEmailToscheduleUserMailForRequirementConsolidatedReports(list,user,todayReleasedRequirementsCount,requirementList.size(),numberOfCreatedCandidatesCount);
							
						}

			     }
		     }
		
	return "Success";
	}
	private void sendEmailToscheduleUserMailForRequirementConsolidatedReports(List<ConsolidatedReport> list, User user,
			Integer todayReleasedRequirementsCount, int size, Integer numberOfCreatedCandidatesCount) throws Exception {
		
		 StringBuilder  sb  = new StringBuilder();
		 
			
		 
		 
		 sb.append("<div>");
		 sb.append("<span style='width:260px; display: inline-block; margin-bottom: 5px;'>Released Requirement Count:</span> <strong style='padding-left:10px;'>"+todayReleasedRequirementsCount+"</strong>");
		 sb.append("<div>");
		 sb.append("<div>");
		 sb.append("<span style='width:260px; display: inline-block; margin-bottom: 5px;'>Created Candidates Count:</span> <strong style='padding-left:10px;'>"+numberOfCreatedCandidatesCount+"</strong>");
		 sb.append("<div>");
		 sb.append("<div>");
		 sb.append("<span style='width:260px; display: inline-block; margin-bottom: 5px;'>Number Of Active Requirement:</span> <strong style='padding-left:10px;'>"+size+"</strong>");
		 sb.append("<div>");

			
			
	    	sb.append("<table width='900' align='center' style='border-collapse: collapse; margin: 0 auto;border: 1px solid #375d81; text-align: center; font-size: 12px;' cellpadding='2px' cellspacing='0' border='0'>");
	    	sb.append("<thead style='background-color:#375d81; color: #fff;'>");
	    	sb.append("<tr style='min-height: 30px; vertical-align: middle;'>");		    
	    	sb.append("<th style='border: 1px solid #12385c; text-align: center; padding:15px 0;'>Requirement Name</th>");	      
	    	sb.append("<th style=.border: 1px solid #12385c; text-align: center; padding:15px 0;'>Applicants</th>");		        
	    	sb.append("<th style='border: 1px solid #12385c; text-align: center; padding:15px 0;'>Shortlisted </th>");		          
	    	sb.append("<th style='border: 1px solid #12385c; text-align: center; padding:15px 0;'>Submit To Client </th>");
	    	sb.append("<th style='border: 1px solid #12385c; text-align: center; padding:15px 0;'>Rejected </th>");
	    	sb.append("<th style='border: 1px solid #12385c; text-align: center; padding:15px 0;'>Interviewed </th>");
	    	sb.append("<th style='border: 1px solid #12385c; text-align: center; padding:15px 0;'>Request Sent </th>");
	    	sb.append("<th style='border: 1px solid #12385c; text-align: center; padding:15px 0;'>Offered </th>");
	    	sb.append("<th style='border: 1px solid #12385c; text-align: center; padding:15px 0;'>Submissions from Client </th>");
	    	
	    	sb.append("</tr>");	
	    	sb.append("</thead>");	
	    	sb.append("<tbody>");
	    	for(ConsolidatedReport conreport :list){
	    		
	    	sb.append("<tr style='min-height: 30px; line-height: 30px; vertical-align: middle;'>");
	    	sb.append("<td style='display: block; overflow: hidden; text-overflow: ellipsis; white-space: nowrap; width: 200px;' width='300'>"+conreport.getRequirementName()+"</td>");		              
	    	sb.append("<td style='border: 1px solid #375d81;' width='225'>"+conreport.getApplicantsCount()+"</td>");	              
	    	sb.append("<td style='border: 1px solid #375d81;' width='275'>"+conreport.getShortlistedCount()+"</td>");	              
	    	sb.append("<td style='border: 1px solid #375d81;' width='275'>"+conreport.getSubmitToClientCount()+"</td>");		            
	    	sb.append("<td style='border: 1px solid #375d81;' width='200'>"+conreport.getRejectedCount()+"</td>");
	    	sb.append("<td style='border: 1px solid #375d81;' width='200'>"+conreport.getInterviewsCount()+"</td>");	
	    	sb.append("<td style='border: 1px solid #375d81;' width='200'>"+conreport.getRequestSentCount()+"</td>");	
	    	sb.append("<td style='border: 1px solid #375d81;' width='200'>"+conreport.getOffersCount()+"</td>");	
	    	sb.append("<td style='border: 1px solid #375d81;' width='200'>"+conreport.getSubmissionCount()+"</td>");
	    	sb.append("</tr>");		            
	    	   
	        }
	    	sb.append("</tbody>");	              
	    	sb.append("</table>");   
			
			 StringBuilder nameBuilder = new StringBuilder();
			 StringBuilder expBuilder = new StringBuilder();
			 StringBuilder locationBuilder = new StringBuilder();
			 EmailMessage emailMessage = new EmailMessage();
			 String professionalName = null;
			 String experience = null;
			 String location = null;
			 
			 
			 emailMessage.setToEmail(user.getEmail());
			 emailMessage.setFromName("Sourcelead Admin");
			 emailMessage.setCreatedBy("sladmin");
			 emailMessage.setFromName("info@sourcelead.com");
			 
			//setting the class filed values
			 Map<String, String> globalVarsMap = new HashMap<String, String>();
			 globalVarsMap.put("professionalName", professionalName);
			 globalVarsMap.put("experience", experience);
			 globalVarsMap.put("location", location);
			 globalVarsMap.put("professionalEmail", "sammetadavid@gmail.com");
			 
			
			 //setting the Recipient information
			 InternetAddress[] iAdressArray = InternetAddress.parse(emailMessage.getToEmail());
			
			 //getting the current EmailScenario by EmailScenario unique name and entity type
			 EmailScenario  emailScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.APPLIED_TO_REQUIREMENT,Constants.ENTITY_TYPE_EMAIL);
			
			 //covert subject,body and signature
			 String subject = emailScenerioUtil.bulidSubject(emailScenario.getSubject(),globalVarsMap);
			 String body = emailScenerioUtil.buildTempBody(emailScenario.getBody(),globalVarsMap);
			 String signature = emailScenerioUtil.buildSignature(emailScenario.getSignature(),globalVarsMap);
			 String url = sourceleadUrl;
			 
			 //construct HTML template using required data
			 String emailTemplate = emailScenerioUtil.AssembleEmailMessage(emailScenario,emailMessage, subject, sb.toString(), signature, url, new Org());
			 emailMessage.setSubject(subject);
			 emailMessage.setMessageBody(emailTemplate);
			 
			 //calling the sendEmail method of emailScenerioUtil
			 emailScenerioUtil.sendEmail(emailMessage,1L,iAdressArray,new ArrayList<EmailAttachment>());
		
	}
		
		
		public void sendEmailToProfessionalForAppliedJob(List<ConsolidatedReport> list,User user,Integer releasedRequirementCount,Integer numberOfActiveRequirementCount,Integer numberOfCreatedCandidatesCount) throws Exception {
			
			 StringBuilder  sb  = new StringBuilder();
			 
			sb.append("Released Requirement Count ").append(releasedRequirementCount);

			sb.append("Created Candidates Count ").append(numberOfCreatedCandidatesCount);
			
			sb.append("Number Of Active Requirement ").append(numberOfActiveRequirementCount);
			
			
	    	sb.append("<table width='900' align='center' style='border-collapse: collapse; margin: 0 auto;border: 1px solid #375d81; text-align: center;' cellpadding='2px' cellspacing='0' border='0'>");
	    	sb.append("<thead style='background-color:#375d81; color: #fff;'>");
	    	sb.append("<tr style='min-height: 30px; line-height: 30px; vertical-align: middle;'>");		    
	    	sb.append("<th style='border: 1px solid #12385c; text-align: center;'>Requirement Name</th>");	      
	    	sb.append("<th style=.border: 1px solid #12385c; text-align: center;'>Applicants </th>");		        
	    	sb.append("<th style='border: 1px solid #12385c; text-align: center;'>Shortlisted </th>");		          
	    	sb.append("<th style='border: 1px solid #12385c; text-align: center;'>Submit To Client </th>");
	    	sb.append("<th style='border: 1px solid #12385c; text-align: center;'>Rejected </th>");
	    	sb.append("<th style='border: 1px solid #12385c; text-align: center;'>Interviewed </th>");
	    	sb.append("<th style='border: 1px solid #12385c; text-align: center;'>Request Sent </th>");
	    	sb.append("<th style='border: 1px solid #12385c; text-align: center;'>Offered </th>");
	    	sb.append("<th style='border: 1px solid #12385c; text-align: center;'>Submissions from Client </th>");
	    	
	    	sb.append("</tr>");	
	    	sb.append("</thead>");	
	    	sb.append("<tbody>");
	    	for(ConsolidatedReport conreport :list){
	    		
	    	sb.append("<tr style='min-height: 30px; line-height: 30px; vertical-align: middle;'>");
	    	sb.append("<td style='border: 1px solid #375d81;' width='300'>"+conreport.getRequirementName()+"</td>");		              
	    	sb.append("<td style='border: 1px solid #375d81;' width='225'>"+conreport.getApplicantsCount()+"</td>");	              
	    	sb.append("<td style='border: 1px solid #375d81;' width='275'>"+conreport.getShortlistedCount()+"</td>");	              
	    	sb.append("<td style='border: 1px solid #375d81;' width='275'>"+conreport.getSubmitToClientCount()+"</td>");		            
	    	sb.append("<td style='border: 1px solid #375d81;' width='200'>"+conreport.getRejectedCount()+"</td>");
	    	sb.append("<td style='border: 1px solid #375d81;' width='200'>"+conreport.getInterviewsCount()+"</td>");	
	    	sb.append("<td style='border: 1px solid #375d81;' width='200'>"+conreport.getRequestSentCount()+"</td>");	
	    	sb.append("<td style='border: 1px solid #375d81;' width='200'>"+conreport.getOffersCount()+"</td>");	
	    	sb.append("<td style='border: 1px solid #375d81;' width='200'>"+conreport.getSubmissionCount()+"</td>");
	    	sb.append("</tr>");		            
	    	   
	        }
	    	sb.append("</tbody>");	              
	    	sb.append("</table>");   
			
			 StringBuilder nameBuilder = new StringBuilder();
			 StringBuilder expBuilder = new StringBuilder();
			 StringBuilder locationBuilder = new StringBuilder();
			 EmailMessage emailMessage = new EmailMessage();
			 String professionalName = null;
			 String experience = null;
			 String location = null;
			 
			 
			 emailMessage.setToEmail(user.getEmail());
			 emailMessage.setFromName("Sourcelead Admin");
			 emailMessage.setCreatedBy("sladmin");
			 emailMessage.setFromName("info@sourcelead.com");
			 
			//setting the class filed values
			 Map<String, String> globalVarsMap = new HashMap<String, String>();
			 globalVarsMap.put("professionalName", professionalName);
			 globalVarsMap.put("experience", experience);
			 globalVarsMap.put("location", location);
			 globalVarsMap.put("professionalEmail", "sammetadavid@gmail.com");
			 
			
			 //setting the Recipient information
			 InternetAddress[] iAdressArray = InternetAddress.parse(emailMessage.getToEmail());
			
			 //getting the current EmailScenario by EmailScenario unique name and entity type
			 EmailScenario  emailScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.APPLIED_TO_REQUIREMENT,Constants.ENTITY_TYPE_EMAIL);
			
			 //covert subject,body and signature
			 String subject = emailScenerioUtil.bulidSubject(emailScenario.getSubject(),globalVarsMap);
			 String body = emailScenerioUtil.buildTempBody(emailScenario.getBody(),globalVarsMap);
			 String signature = emailScenerioUtil.buildSignature(emailScenario.getSignature(),globalVarsMap);
			 String url = sourceleadUrl;
			 
			 //construct HTML template using required data
			 String emailTemplate = emailScenerioUtil.AssembleEmailMessage(emailScenario,emailMessage, subject, sb.toString(), signature, url, new Org());
			 emailMessage.setSubject(subject);
			 emailMessage.setMessageBody(emailTemplate);
			 
			 //calling the sendEmail method of emailScenerioUtil
			 emailScenerioUtil.sendEmail(emailMessage,1L,iAdressArray,new ArrayList<EmailAttachment>());
			
		}
		

	/****
	 * 
	 * @author: Pavan Jampana 
	 * This  controller method is  to get Timesheet Submissions list based on status 
	 */
	
	@RequestMapping(value = "/totalTimesheetList/{status}")
	public @ResponseBody void tiesheetStatusList(@PathVariable("status") String status,HttpServletResponse response) throws Exception {
		 StringBuilder redirectUrl = new StringBuilder();
		 response.sendRedirect(redirectUrl.append("/sourcelead/timesheetlistByStatus?status=").append(status).toString());
	}
	
	
	@RequestMapping(value = "/dashboard/{orgLocationId}" , method = RequestMethod.GET)
	 public @ResponseBody void userSummary(@PathVariable Long orgLocationId,HttpServletRequest request,HttpServletResponse response) throws Exception {
			
		     //to update user state in user role based on location----> Prasad Salina
		     User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		     OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");

		     UserRole userRole = userRoleService.getUserRoleByUserIdAndLocationId(user.getId(), orgLocationId);
		     if(userRole != null && userRole.getUserState() != null && userRole.getUserState().equals(Status.NOT_LOGGEDIN)){
   			    userRole.setUserState(Status.ACTIVE);
   			   UserRole userRoleElk = userRoleService.addUserRole(userRole);
   			   
   			   
   			   
   			   //user update/save in user Elk
   			  userService.saveOrUpdateUserElkDocument(userRoleElk,null,user.getUserCode());
   		      }
				
		     StringBuilder redirectUrl = new StringBuilder();
		     response.sendRedirect(redirectUrl.append("/sourcelead/dashboardLinks?orgLocationId=").append(orgLocationId).toString());
			
	}
	
	
	 /**
	   * @author sarath kumar
	   * @since  aug 30 2016
	   * @version 0.0.1
	   * 
	   * This method is used to for getting CandidatesList By OrgLocationID
	   * this method is called at onload when click on timesheet left menu
	   * 
	   * @param user This is the first parameter to getCandidatesListByOrgLocationID method
	   * @param navigation This is the second parameter to getCandidatesListByOrgLocationID method
	   * @param startDate This is the third parameter to getCandidatesListByOrgLocationID method
	   *  
	   * @return string 
	   * 
	   * @throws NullPointer
	   */
	 
	 @PreAuthorize("hasRole('ROLE_VIEW_REQUIREMENT')")
	 @RequestMapping(value = "/getAssignedRequirementListByOrgLocationIDAndUserId")
	 public String getAssignedRequirementListByOrgLocationIDAndUserId(@RequestParam(value="status", required=false) String status,@RequestParam(value="clients", required=false) String clients,HttpServletRequest request) throws Exception {

		String resultJson;
		User  user = (User)SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
		String userRole = (String) SourceLeadSessionManager.getSessionAttribute(request, "loginUserRole");
		
		String userCode = user.getUserCode();
		String userType = null;
		List<Requirement>   requirementList = null;
		
		if(Constants.ROLE_ADMIN.equalsIgnoreCase(userRole) || Constants.SOURCELEAD_ADMIN.equalsIgnoreCase(userRole)){
			userType = Constants.ADMIN;
			//requirementList = requirementService.getAssignedRequirementListByOrgLocationIDAndUserId(loginOrgLocation.getId(), userType, user.getId(), userCode);
			requirementList = requirementService.getAssignedRequirementListByOrgLocationIDAndUserIdSubStatusApprovedOpenNew(loginOrgLocation.getId(), userType, user.getId(), userCode);
		}else{
			//requirementList = requirementService.getAssignedRequirementListByOrgLocationIDAndUserId(loginOrgLocation.getId(), userType, user.getId(), userCode);
			requirementList = requirementService.getAssignedRequirementListByOrgLocationIDAndUserIdSubStatusApprovedOpenNew(loginOrgLocation.getId(), userType, user.getId(), userCode);
		}
		
		//List<Requirement>   requirementList = requirementService.getAssignedRequirementListByOrgLocationIDAndUserId(loginOrgLocation.getId(), userType, user.getId(), userCode);
		for(Requirement requirement : requirementList){
			/*if(requirement.getClientId() != null){
				requirement.setClientName(requirement.getClientId().getResponderLocationId().getOrg().getOrgName());
			}*/
			//changed ny prasad salina
			if(requirement.getClientId() != null){
				if(requirement.getClientId().getResponderType().equalsIgnoreCase("Client")){
					requirement.setClientName(requirement.getClientId().getResponderLocationId().getOrg().getOrgName());
				}
				if(requirement.getClientId().getResponderType().equalsIgnoreCase("Vendor")){
					requirement.setClientName(requirement.getClientId().getLocationId().getOrg().getOrgName());
				}
			}
			
			
			
			int applicantsCount = requirementSubmissionService.getAllSubmissionCountByRequirementIdAndSubmissionStage(loginOrgLocation.getId(), requirement.getId(), "APPLICANTS");
			int shortlistedCount = requirementSubmissionService.getAllSubmissionCountByRequirementIdAndSubmissionStage(loginOrgLocation.getId(), requirement.getId(), "SHORTLISTED");
			int submittedCount = requirementSubmissionService.getAllSubmissionCountByRequirementIdAndSubmissionStage(loginOrgLocation.getId(), requirement.getId(), "SUBMITTED");
			int interviewedCount = requirementSubmissionService.getAllSubmissionCountByRequirementIdAndSubmissionStage(loginOrgLocation.getId(), requirement.getId(), "INTERVIEWED");
			int offeredCount = requirementSubmissionService.getAllSubmissionCountByRequirementIdAndSubmissionStage(loginOrgLocation.getId(), requirement.getId(), "OFFERED");
			
			requirement.setNoOfApplicants(String.valueOf(applicantsCount));
			requirement.setNoOfShortlistedApplicants(String.valueOf(shortlistedCount));
			requirement.setNoOfSubmittedApplicants(String.valueOf(submittedCount));
			requirement.setNoOfInterviewedApplicants(String.valueOf(interviewedCount));
			requirement.setNoOfOfferedApplicants(String.valueOf(offeredCount));
			
			if(null != requirement.getReleasedBy() && !"".equalsIgnoreCase(requirement.getReleasedBy())){
				List<Long> userId = new ArrayList<Long>();
				for (String s : requirement.getReleasedBy().split(",")){
					userId.add(Long.parseLong(s));
				 }
				boolean flag = userId.contains(user.getId());
	     		if(flag == true){
	     			requirement.setIsRequirementReleasedByuser("Y");
	     		}else{
	     			requirement.setIsRequirementReleasedByuser("N");
	     		}
	     		
	     		
	     		if(userRole.equalsIgnoreCase("ROLE_ADMIN") || userRole.equalsIgnoreCase("ROLE_SOURCELEAD_ADMIN")){
         			requirement.setIsRequirementReleasedByuser("Y");
         		}
			}

		}
		
		resultJson = commonUtil.toJSON(requirementList);
		return resultJson;
	 }
	 
	 /**
	  * This method is used for getting all Submissions,Applicat's,Interviews,Offers counts and Show in Dashboard page.
	  * 
	  * @author prasad K
	  * @param daterange
	  * @return String
	  * @throws NullPointer
	  */
	
	 @RequestMapping(value = "/getRequirementSubmissionsCount" , method = RequestMethod.GET)
	 public String  getRequirementSubmissionsCount(@RequestParam(value="daterange", required=false) String daterange,HttpServletRequest request) throws Exception {
		
		String resultJson;
	    OrgLocations loginOrgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		
		DashboardCounts dc= new DashboardCounts();
		Timezone timezone = null;
		String userRole = (String) SourceLeadSessionManager.getSessionAttribute(request, "loginUserRole");
		User  user = (User)SourceLeadSessionManager.getSessionAttribute(request, "user");
		if(loginOrgLocations.getTimezone() != null){
			timezone = timeZoneService.getTimezone(loginOrgLocations.getTimezone().getId());
		}
		String userType = null;
		Date startUTCDate = null;
		Date endUTCDate = null;
		DateFormat TZ_DF_TS = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
		if("today".equals(daterange)){
			 DateTime zoned = new DateTime(DateTimeZone.forID(timezone.getTimeZoneId()));
			 DateTimeFormatter outputFormatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'00:00:00");
			 DateTimeFormatter outputFormatter1 = DateTimeFormat.forPattern("yyyy-MM-dd'T'23:59:59");
			 String startDate = outputFormatter.print(zoned);
			 String startDateString = CommonUtil.convertTimeZones(timezone.getTimeZoneId(),Constants.UTC_TIMEZONE, startDate);
			 startUTCDate = TZ_DF_TS.parse(startDateString);
			 String endDate = outputFormatter1.print(zoned);
			 String endDateString = CommonUtil.convertTimeZones(timezone.getTimeZoneId(),Constants.UTC_TIMEZONE, endDate);
			 endUTCDate = TZ_DF_TS.parse(endDateString);
		}else{
			 DateTime zoned = new DateTime(DateTimeZone.forID(timezone.getTimeZoneId()));
			 DateTimeFormatter outputFormatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'00:00:00");
			 String startDate = outputFormatter.print(zoned);
			 String startDateString = CommonUtil.convertTimeZones(timezone.getTimeZoneId(),Constants.UTC_TIMEZONE, startDate);
			 startUTCDate = TZ_DF_TS.parse(startDateString);
		}
		
		if(Constants.ROLE_ADMIN.equalsIgnoreCase(userRole) || Constants.SOURCELEAD_ADMIN.equalsIgnoreCase(userRole)){
			userType = Constants.ADMIN;
		} 
		
		int toDayReqSubmissionscount = requirementSubmissionService.getRequirementSubmissionsCountByOrgLocationsIdAndSubmissionStatus(loginOrgLocations.getId(),"SUBMIT_TO_CLIENT",daterange,userType,user,startUTCDate,endUTCDate);
		//int previousReqSubmissionscount = requirementSubmissionService.getRequirementSubmissionsCountByOrgLocationsIdAndSubmissionStatus(loginOrgLocations.getId(),"SUBMISSION","previous");
		
		int toDayReqApplicantscount = requirementSubmissionService.getRequirementSubmissionsCountByOrgLocationsIdAndSubmissionStatus(loginOrgLocations.getId(),"SUBMISSION",daterange,userType,user,startUTCDate,endUTCDate);
		//int previousReqApplicantscount = requirementSubmissionService.getRequirementSubmissionsCountByOrgLocationsIdAndSubmissionStatus(loginOrgLocations.getId(),"SUBMISSION","previous");
	   
		int toDayReqInterviewscount = requirementSubmissionService.getRequirementSubmissionsCountByOrgLocationsIdAndSubmissionStatus(loginOrgLocations.getId(),"INTERVIEW",daterange,userType,user,startUTCDate,endUTCDate);
	    //  int previousReqInterviewscount = requirementSubmissionService.getRequirementSubmissionsCountByOrgLocationsIdAndSubmissionStatus(loginOrgLocations.getId(),"INTERVIEW","previous");
	   
	    int toDayReqOfferscount = requirementSubmissionService.getRequirementSubmissionsCountByOrgLocationsIdAndSubmissionStatus(loginOrgLocations.getId(),"OFFERMADE",daterange,userType,user,startUTCDate,endUTCDate);
	    // int previousReqOfferscount = requirementSubmissionService.getRequirementSubmissionsCountByOrgLocationsIdAndSubmissionStatus(loginOrgLocations.getId(),"OFFERMADE","previous");
		
	    dc.setSubmissionCount(toDayReqSubmissionscount);
	    //dc.setSubmissionCount(previousReqSubmissionscount);
	    
	    dc.setApplicantsCount(toDayReqApplicantscount);
	   // dc.setApplicantsCount(previousReqApplicantscount);
	    
	    dc.setInterviewsCount(toDayReqInterviewscount);
	   // dc.setInterviewsCount(previousReqInterviewscount);
	    
	    dc.setOffersCount(toDayReqOfferscount);
	   // dc.setOffersCount(previousReqOfferscount);
	   
	    resultJson = commonUtil.toJSON(dc);
		return resultJson;
	   }

	 
	 /**
	   * @author sarath kumar
	   * @since  oct 26 2016
	   * @version 0.0.1
	   * 
	   *  
	   * @return string 
	   * 
	   * @throws NullPointer
	   */
	 
	 @RequestMapping(value = "/getAssignedRequirementApproverListByOrgLocationIDAndUserId")
	 public String getAssignedRequirementApproverListByOrgLocationIDAndUserId(@RequestParam(value="status", required=false) String status,@RequestParam(value="clients", required=false) String clients,HttpServletRequest request) throws Exception {

		String resultJson;
		User  user = (User)SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
		String userRole = (String) SourceLeadSessionManager.getSessionAttribute(request, "loginUserRole");
		
		String userCode = user.getUserCode();
		String userType = null;
		List<Requirement>   requirementList = null;
		
		if(Constants.ROLE_ADMIN.equalsIgnoreCase(userRole) || Constants.SOURCELEAD_ADMIN.equalsIgnoreCase(userRole)){
			userType = Constants.ADMIN;
			requirementList = requirementService.getAssignedRequirementListByOrgLocationIDAndUserId(loginOrgLocation.getId(), userType, user.getId(), userCode);
		}else{
			requirementList = requirementService.getAssignedRequirementListByOrgLocationIDAndUserId(loginOrgLocation.getId(), userType, user.getId(), userCode);
		}
		
		//List<Requirement>   requirementList = requirementService.getAssignedRequirementListByOrgLocationIDAndUserId(loginOrgLocation.getId(),user.getId());
		for(Requirement requirement : requirementList){
			// to get the RequirementApproverHierarchy to set the Status
			RequirementApproverHierarchy requirementApproverHierarchy = requirementApproverHierarchyService.getRequirementApproverHierarchyByRequirementId(requirement.getId(), user.getId());
			if(requirementApproverHierarchy!=null){
				requirement.setRequirementApproverHierarchyStatus(requirementApproverHierarchy.getRequirementApproverStatus().toString());
			}
		}	
		resultJson = commonUtil.toJSON(requirementList);
		return resultJson;
	 }
	 
	 /**
	   * @author sarath kumar
	   * @since  oct 26 2016
	   * @version 0.0.1
	   * 
	   *  
	   * @return string 
	   * 
	   * @throws NullPointer
	   */
	 
	 @RequestMapping(value = "/getApproverRequirementApproverListByOrgLocationIDAndUserId",  method = RequestMethod.GET)
	 public String getApproverRequirementApproverListByOrgLocationIDAndUserId(@RequestParam(value="statusType", required=false) String statusType,HttpServletRequest request) throws Exception {

		String resultJson;
		User  user = (User)SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
		String userRole = (String) SourceLeadSessionManager.getSessionAttribute(request, "loginUserRole");
		
		String userCode = user.getUserCode();
		String userType = null;
		List<Requirement>   requirementList = null;
		ArrayList<Requirement>   newList = new ArrayList<Requirement>();
		
		if(Constants.ROLE_ADMIN.equalsIgnoreCase(userRole) || Constants.SOURCELEAD_ADMIN.equalsIgnoreCase(userRole)){
			userType = Constants.ADMIN;
			requirementList = requirementService.getApproverRequirementApproverListByOrgLocationIDAndUserId(loginOrgLocation.getId(), userType, user.getId(), userCode,statusType);
		}else{
			requirementList = requirementService.getApproverRequirementApproverListByOrgLocationIDAndUserId(loginOrgLocation.getId(), userType, user.getId(), userCode,statusType);
		}
		
		//List<Requirement>   requirementList = requirementService.getAssignedRequirementListByOrgLocationIDAndUserId(loginOrgLocation.getId(),user.getId());
		for(Requirement requirement : requirementList){
			// to get the RequirementApproverHierarchy to set the Status
			RequirementApproverHierarchy requirementApproverHierarchy = requirementApproverHierarchyService.getRequirementApproverHierarchyByRequirementId(requirement.getId(), user.getId());
			if(null != requirementApproverHierarchy ){
				       requirement.setRequirementApproverHierarchyStatus(requirementApproverHierarchy.getRequirementApproverStatus().toString());
				       // to set the approved/rejected by user
				       User approvedUser = userService.getUserByUserCode(requirementApproverHierarchy.getUpdatedBy());
			                    if(approvedUser !=null ){
			                    	StringBuilder sb = new StringBuilder();
									requirement.setApprovedBy(sb.append(approvedUser.getFirstName()).append(" ").append(approvedUser.getLastName()).toString());
			                    }
			                    newList.add(requirement);
			}else if(!"SUBMITTED".equalsIgnoreCase(statusType) && !"".equalsIgnoreCase(statusType)){
				
				 List<RequirementApproverHierarchy>  rahlist = requirementApproverHierarchyService.getRequirementApproverHierarchyListByRequirementId(requirement.getId());
			                if(rahlist !=null ){
									 for(RequirementApproverHierarchy rah :rahlist ){
										  requirement.setRequirementApproverHierarchyStatus(rah.getRequirementApproverStatus().toString());
						            	  if("APPROVED".equalsIgnoreCase(rah.getRequirementApproverStatus().toString()) || "REJECTED".equalsIgnoreCase(rah.getRequirementApproverStatus().toString())  || "OPEN".equalsIgnoreCase(requirement.getRequirementSubmissionStatus().toString())){
						            		  User approvedUser = userService.getUserByUserCode(rah.getUpdatedBy());
						            		  if(approvedUser !=null ){
							                    	StringBuilder sb = new StringBuilder();
													requirement.setApprovedBy(sb.append(approvedUser.getFirstName()).append(" ").append(approvedUser.getLastName()).toString());
							                    }
						            		 
						            	  }
					              }
							 newList.add(requirement);
			                }
			}
		}	
		resultJson = commonUtil.toJSON(newList);
		return resultJson;
	 }
	 
	
	 /**
		 * 
		 * @author: Venkat Kesav
		 * This  controller method is  to get Interviews list 
		 */
		@PreAuthorize("hasRole('ROLE_VIEW_INTERVIEW')")
		@RequestMapping(value = "/getInterviews")
		public String getInterviews(String dateRange, HttpServletRequest request) throws Exception {
			
			

			String resultJson;
			String userRole = (String) SourceLeadSessionManager.getSessionAttribute(request, "loginUserRole");

		    OrgLocations loginOrgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			
			List<RequirementInterviewHistory> requirementInterviewList = null;
			
			String currentTime = null;
			String userType = null;
			Timezone timezone = null;
			String userCode = user.getUserCode();
			Long userId = user.getId();
			if(loginOrgLocations.getTimezone() != null){
				timezone = timeZoneService.getTimezone(loginOrgLocations.getTimezone().getId());
			}
			
			Date startUTCDate = null;
			Date endUTCDate = null;
			DateFormat TZ_DF_TS = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
			if(dateRange != null && !"today".equals(dateRange) && !"previous".equals(dateRange) && !"upcoming".equals(dateRange)){
				DateFormat END_TZ_DF = new SimpleDateFormat ("yyyy-MM-dd'T'23:59:59");
				Date startTime =  commonUtil.getDateFormat(dateRange);
				String startTimeTS = Constants.TZ_DF.format(startTime); 
				String endTimeTS = END_TZ_DF.format(startTime);
				String startDateString = CommonUtil.convertTimeZones(timezone.getTimeZoneId(),"UTC", startTimeTS);
				String endDateString = CommonUtil.convertTimeZones(timezone.getTimeZoneId(),"UTC", endTimeTS);
				 startUTCDate = TZ_DF_TS.parse(startDateString);
				 endUTCDate = TZ_DF_TS.parse(endDateString);
			}
			else if("today".equals(dateRange)){
				 DateTime zoned = new DateTime(DateTimeZone.forID(timezone.getTimeZoneId()));
				 DateTimeFormatter outputFormatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'00:00:00");
				 DateTimeFormatter outputFormatter1 = DateTimeFormat.forPattern("yyyy-MM-dd'T'23:59:59");
				 String startDate = outputFormatter.print(zoned);
				 String startDateString = CommonUtil.convertTimeZones(timezone.getTimeZoneId(),Constants.UTC_TIMEZONE, startDate);
				 startUTCDate = TZ_DF_TS.parse(startDateString);
				 String endDate = outputFormatter1.print(zoned);
				 String endDateString = CommonUtil.convertTimeZones(timezone.getTimeZoneId(),Constants.UTC_TIMEZONE, endDate);
				 endUTCDate = TZ_DF_TS.parse(endDateString);
			}
			else{
				 DateTime zoned = new DateTime(DateTimeZone.forID(timezone.getTimeZoneId()));
				 DateTimeFormatter outputFormatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'00:00:00");
				 String startDate = outputFormatter.print(zoned);
				 String startDateString = CommonUtil.convertTimeZones(timezone.getTimeZoneId(),Constants.UTC_TIMEZONE, startDate);
				 startUTCDate = TZ_DF_TS.parse(startDateString);
			}
			if(Constants.ROLE_ADMIN.equalsIgnoreCase(userRole) || Constants.SOURCELEAD_ADMIN.equalsIgnoreCase(userRole)){
				userType = Constants.ADMIN;
				requirementInterviewList = requirementInterviewHistoryService.getAllInterviewAssignedToLoginUser(loginOrgLocations, dateRange, currentTime, userType, userId, userCode,startUTCDate,endUTCDate,timezone,user);
				}else{
				requirementInterviewList = requirementInterviewHistoryService.getAllInterviewAssignedToLoginUser(loginOrgLocations, dateRange, currentTime, userType, userId, userCode,startUTCDate,endUTCDate,timezone,user);
			}
			
			
			
			
			resultJson = commonUtil.toJSON(requirementInterviewList);
			return resultJson;
		}
		
		/**
		 * @author sarath kumaar
		 * @since  oct 18 2016
		 * @version 0.0.1
		 * 
		 * This method is used to accept/Reject Requirement in Requirement table.
		 *  
		 * @param jsonObj This is the first paramter to storeRequirement method
		 * @param orgLocations This is the second paramter to storeRequirement method
		 * @param user This is the third paramter to storeRequirement method
		 * @param multiFile This is the fourth paramter to storeRequirement method
		 * 
		 * @return success msg  
		 * 
		 * @throws NullPointer
		*/
		
		@RequestMapping(value = "/requirementsAcceptOrReject", method = RequestMethod.POST)
		public @ResponseBody String requirementsAcceptOrReject(@RequestParam(value = "requirementId", required = false) Long requirementId,@RequestParam(value = "status", required = false)  String status, @RequestParam(value = "reason", required = false) String reason, HttpServletRequest request) throws Exception {
			
		 
			User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			
			Requirement requirement = requirementService.getRequirementByRequirementId(requirementId);
			RequirementApproverHierarchy requirementApproverHierarchy = requirementApproverHierarchyService.getRequirementApproverHierarchyByRequirementId(requirementId, loginUser.getId());
		    
			/*// to get  RequirementApproverHierarchy with maximum Level
			Integer maxLevel = requirementApproverHierarchyService.getRequirementApproverHierarachyWithHighestLevel(requirementId);

			if(status.equalsIgnoreCase("APPROVED")){ 
			    if(maxLevel!=null){
			    	 if(maxLevel == requirementApproverHierarchy.getLevel()){
			    		 requirement.setRequirementSubmissionStatus(REQUIREMENT_SUBMISSION_STATUS.OPEN);
					   }else{
					    	List<RequirementApproverHierarchy> list1  =  requirementApproverHierarchyService.getRequirementApproverHierarchyListByRequirementId(requirementId);
					    	for(RequirementApproverHierarchy rah :list1){
					    		if(maxLevel.equals(rah.getLevel())){
									User approverUser = userService.getUserById(rah.getApproverUserId().getId());
									requirementService.approverTwoEmailScenario(loginUser, approverUser,requirement,null,orgLocation);
								   } 
					    	} 
			    }
	  }
			    
				requirementApproverHierarchy.setRequirementApproverStatus(REQUIREMENT_APPROVER_HIERARCHY_STATUS.APPROVED);
				User intiatorUser = userService.getUserByUserCode(requirement.getCreatedBy());
				requirementService.acceptEmailScenario(loginUser, intiatorUser, requirement,orgLocation);
				
			}
			if(status.equalsIgnoreCase("REJECTED")){
				 if(maxLevel!=null){
			    	 if(maxLevel == requirementApproverHierarchy.getLevel()){
			    		 requirement.setRequirementSubmissionStatus(REQUIREMENT_SUBMISSION_STATUS.REJECTED);
					   }
			    }
				 
				requirementApproverHierarchy.setRequirementApproverStatus(REQUIREMENT_APPROVER_HIERARCHY_STATUS.REJECTED);
				requirementApproverHierarchy.setRejectedReason(reason);
				
				User initiatorUser = userService.getUserByUserCode(requirement.getCreatedBy()); 
				requirementService.rejectedEmailScenario(loginUser, initiatorUser, requirement,null,reason,orgLocation); 
			}
			requirementService.addRequirement(requirement);
			requirementApproverHierarchyService.addRequirementApproverHierarchy(requirementApproverHierarchy);
			*/
			
		//  This for the Requirement Location 
					StringBuilder location = new StringBuilder();
					if(null != requirement.getCity() && !requirement.getCity().equalsIgnoreCase("")){
						location.append(requirement.getCity()).append(" ,");
					 }
					if(null != requirement.getState() && !requirement.getState().equalsIgnoreCase("")){
						location.append(requirement.getState()).append(" ,");
					 }
					
					 location.append(requirement.getCountry()).append(" ");
					
				
			// requirement created user
			User intiatorUser = userService.getUserByUserCode(requirement.getCreatedBy());

			// to get RequirementApproverHierarchy with maximum Level
			Integer maxLevel = requirementApproverHierarchyService.getRequirementApproverHierarachyWithHighestLevel(requirementId);

			if (maxLevel != null) {
				if (maxLevel.equals(requirementApproverHierarchy.getLevel())) {
						if (status.equalsIgnoreCase("APPROVED")) {
							requirement.setRequirementSubmissionStatus(REQUIREMENT_SUBMISSION_STATUS.OPEN);
						} else if (status.equalsIgnoreCase("REJECTED")) {
							requirement.setRequirementSubmissionStatus(REQUIREMENT_SUBMISSION_STATUS.REJECTED);
						}
							requirement.setUpdatedBy(loginUser.getUserCode());
							requirement.setUpdatedDate(commonUtil.currentDate());
							requirementService.addRequirement(requirement);
							List<RequirementApproverHierarchy> list1 = requirementApproverHierarchyService.getRequirementApproverHierarchyListByRequirementId(requirementId);
							for (RequirementApproverHierarchy rah : list1) {
								if (status.equalsIgnoreCase("APPROVED")) {
									rah.setRequirementApproverStatus(REQUIREMENT_APPROVER_HIERARCHY_STATUS.APPROVED);
									requirement.setReqlevel2ApproverStatus(REQUIREMENT_APPROVER_HIERARCHY_STATUS.APPROVED.toString());
								} else if (status.equalsIgnoreCase("REJECTED")) {
									rah.setRejectedReason(reason);
									rah.setRequirementApproverStatus(REQUIREMENT_APPROVER_HIERARCHY_STATUS.REJECTED);
									requirement.setReqlevel2ApproverStatus(REQUIREMENT_APPROVER_HIERARCHY_STATUS.REJECTED.toString());
								}
								rah.setUpdatedBy(loginUser.getUserCode());
								rah.setUpdatedDate(commonUtil.currentDate());
								requirementApproverHierarchyService.addRequirementApproverHierarchy(rah);
							}
						// this is for sending email to requirement initiator
						if (status.equalsIgnoreCase("APPROVED")) {
							requirementService.acceptEmailScenario(loginUser, intiatorUser, requirement,location, orgLocation);
						} else if (status.equalsIgnoreCase("REJECTED")) {
							requirementService.rejectedEmailScenario(loginUser, intiatorUser, requirement, location, reason,orgLocation);
						}
				}else{

					    RequirementApproverHierarchy level1approver = requirementApproverHierarchyService.getRequirementApproverHierarchyByRequirementIdAndMaximumLevel(requirementId);
						User approverUser = userService.getUserById(level1approver.getApproverUserId().getId());
						if (status.equalsIgnoreCase("APPROVED")) {
							if (null != level1approver) {
								// this is for sending email to second level approver
								requirementService.approverTwoEmailScenario(loginUser, approverUser, requirement, location,	orgLocation);
							}
							requirementApproverHierarchy.setRequirementApproverStatus(REQUIREMENT_APPROVER_HIERARCHY_STATUS.APPROVED);
							requirement.setReqlevel1ApproverStatus(REQUIREMENT_APPROVER_HIERARCHY_STATUS.APPROVED.toString());
						} else if (status.equalsIgnoreCase("REJECTED")) {
							// this is for sending email to requirement initiator
							requirementApproverHierarchy.setRequirementApproverStatus(REQUIREMENT_APPROVER_HIERARCHY_STATUS.REJECTED);
							requirement.setReqlevel1ApproverStatus(REQUIREMENT_APPROVER_HIERARCHY_STATUS.REJECTED.toString());
							requirementApproverHierarchy.setRejectedReason(reason);
							requirementService.rejectedEmailScenario(loginUser, intiatorUser, requirement, location, reason,orgLocation);
							requirement.setRequirementSubmissionStatus(REQUIREMENT_SUBMISSION_STATUS.REJECTED);
							
							// This is  to update  the level2 Approver Record For RejectedS
						    RequirementApproverHierarchy level2approver = requirementApproverHierarchyService.getRequirementApproverHierarchyByRequirementIdExcludingApproverHierarchyId(requirementApproverHierarchy.getId(), requirementId);
						    level2approver.setRequirementApproverStatus(REQUIREMENT_APPROVER_HIERARCHY_STATUS.REJECTED);
						    requirement.setReqlevel1ApproverStatus(REQUIREMENT_APPROVER_HIERARCHY_STATUS.REJECTED.toString());
						    level2approver.setUpdatedBy(loginUser.getUserCode());
						    level2approver.setUpdatedDate(commonUtil.currentDate());
							requirementApproverHierarchyService.addRequirementApproverHierarchy(level2approver);
	
						}
						requirementApproverHierarchy.setUpdatedBy(loginUser.getUserCode());
						requirementApproverHierarchy.setUpdatedDate(commonUtil.currentDate());
						requirementApproverHierarchyService.addRequirementApproverHierarchy(requirementApproverHierarchy);
						requirement.setUpdatedBy(loginUser.getUserCode());
						requirement.setUpdatedDate(commonUtil.currentDate());
						requirementService.addRequirement(requirement);
				}
			}
			requirementElkUtil.saveElkRequirement(requirement,new JSONObject());
			return "success";
			
		}
		
		@RequestMapping(value = "/getApplicantsByStatus", method = RequestMethod.POST)
		public String getApplicantsByStatus(@RequestParam(value = "status", required = false) String status, HttpServletRequest request) throws Exception {

		 String resultJson;
		 User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		 OrgLocations loginOrgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		 List<RequirementSubmission> requirementSubmissionList = requirementSubmissionService.getApplicantsByStatus(loginOrgLocations.getId(), user,status);
		 resultJson = commonUtil.toJSON(requirementSubmissionList);
		 return resultJson;

		}
		
		@PreAuthorize("hasRole('ROLE_VIEW_INTERVIEW')")
		@RequestMapping(value = "/getInterviewsandTasks")
		public String getInterviewsandTasks(String dateRange, HttpServletRequest request) throws Exception {
			
			String resultJson;
			String userRole = (String) SourceLeadSessionManager.getSessionAttribute(request, "loginUserRole");

		    OrgLocations loginOrgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			
			List<RequirementInterviewHistory> requirementInterviewList = null;
			
			String currentTime = null;
			String userType = null;
			Timezone timezone = null;
			String userCode = user.getUserCode();
			Long userId = user.getId();
			if(loginOrgLocations.getTimezone() != null){
				timezone = timeZoneService.getTimezone(loginOrgLocations.getTimezone().getId());
			}
			
			Date startUTCDate = null;
			Date endUTCDate = null;
			DateFormat TZ_DF_TS = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
			DateFormat END_TZ_DF = new SimpleDateFormat ("yyyy-MM-dd'T'23:59:59");
			Date startTime =  commonUtil.getDateFormat(dateRange);
			
			String startTimeTS = Constants.TZ_DF.format(startTime); 
			String endTimeTS = END_TZ_DF.format(startTime);
			String startDateString = CommonUtil.convertTimeZones(timezone.getTimeZoneId(),Constants.UTC_TIMEZONE, startTimeTS);
			String endDateString = CommonUtil.convertTimeZones(timezone.getTimeZoneId(),Constants.UTC_TIMEZONE, endTimeTS);
				 startUTCDate = TZ_DF_TS.parse(startDateString);
				 endUTCDate = TZ_DF_TS.parse(endDateString);
			if(Constants.ROLE_ADMIN.equalsIgnoreCase(userRole) || Constants.SOURCELEAD_ADMIN.equalsIgnoreCase(userRole)){
				userType = Constants.ADMIN;
					requirementInterviewList = requirementInterviewHistoryService.getAllInterviewAssignedToLoginUser(loginOrgLocations, dateRange, currentTime, userType, userId, userCode,startUTCDate,endUTCDate,timezone,user);
				}else{
					requirementInterviewList = requirementInterviewHistoryService.getAllInterviewAssignedToLoginUser(loginOrgLocations, dateRange, currentTime, userType, userId, userCode,startUTCDate,endUTCDate,timezone,user);
			}
			
			// tasks
			String taskCurrentTimeStr = "";
			String taskCurrentDateInTimezone ="";
			Date converteDate = null;
			
			String status =  "myTask";
			if(!"".equalsIgnoreCase(dateRange)){
				Date date1 = new SimpleDateFormat("dd-MM-yyyy").parse(dateRange);
				taskCurrentTimeStr = Constants.TZ_DF.format(date1);
				taskCurrentDateInTimezone= CommonUtil.convertTimeZones(timezone.getTimeZoneId(), Constants.UTC_TIMEZONE, taskCurrentTimeStr);
				converteDate = TZ_DF_TS.parse(taskCurrentDateInTimezone);
				taskCurrentDateInTimezone = Constants.DMY.format(converteDate);
			}
			
			List<Tasks> taklist = taskService.tasksListByIdAndOrgLocationIdAssignedUserId(loginOrgLocations.getId(),user.getId(),status,taskCurrentDateInTimezone,converteDate,timezone);
			
			DateFormat week_Day = new SimpleDateFormat ("EEEE");
			
			String dayOfweek = week_Day.format(startTime);
			SimpleDateFormat formatter = new SimpleDateFormat("MMM dd yyyy");
			String selectedDate  = formatter.format(startTime);
			RequirementInterviewHistory reqInterviewHistory =  new RequirementInterviewHistory();
			
			
			reqInterviewHistory.setInterviewerList(requirementInterviewList);
			reqInterviewHistory.setTasksList(taklist);
			
			reqInterviewHistory.setDayOfweek(dayOfweek);
			reqInterviewHistory.setSelectedDate(selectedDate);
			
			resultJson = commonUtil.toJSON(reqInterviewHistory);
			return resultJson;
		}
}
