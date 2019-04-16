package com.bis.sourcelead.timesheet.web;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.entity.Notifications;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.ProjectMembers;
import com.bis.sourcelead.entity.SubmissionApproverHierarchy;
import com.bis.sourcelead.entity.TimesheetSubmissionsDocuments;
import com.bis.sourcelead.entity.Timezone;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.NotificationsService;
import com.bis.sourcelead.service.ProjectMembersService;
import com.bis.sourcelead.service.TimeZoneService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.timesheet.rest.TimesheetEmailUtil;
import com.bis.sourcelead.timesheet.service.SubmissionApproverHierarchyService;
import com.bis.sourcelead.timesheet.service.TimesheetEntriesService;
import com.bis.sourcelead.timesheet.service.TimesheetSubmissionsDocumentsService;
import com.bis.sourcelead.timesheet.service.impl.TimeSheetExcelView;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.PdfConversionUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;
 

@Controller 
@RequestMapping("/app")
public class TimesheetController {
	
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	 
	@Autowired
	public TimesheetEntriesService timesheetEntriesService;
	
	@Autowired
	public TimeSheetExcelView timeSheetExcelView;
	
	@Autowired
	public CommonUtil commonUtil;
	
	@Autowired
	private NotificationsService notificationsService;
	
	@Autowired
	private ProjectMembersService projectMembersService;
	
	@Autowired
	private SubmissionApproverHierarchyService submissionApproverHierarchyService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private TimeZoneService timeZoneService;
	
	@Autowired
	private TimesheetSubmissionsDocumentsService timesheetSubmissionsDocumentsService;
	
	@Autowired
	private PdfConversionUtil pdfConversionUtil;
	
	@Autowired
	private TimesheetEmailUtil timesheetEmailUtil;
	
	/**
	 * @author naresh bolisetty and sarath kumar
	 * @since  jan 27 2017
	 * @version 0.0.1
	 * 
	 * this  controller method is for timesheet screen redirection.
	 * 
	 * @param request
	 *  
	 * @return TimesheetEntries 
	 * @throws Exception 
	 * @throws NullPointer
	 */
	
	@RequestMapping(value = {"/timesheet","/timesheet/{notificationUUID}"})
	@PreAuthorize("hasAnyRole('ROLE_FILL_MY_TIMESHEETS','ROLE_MODIFY_MY_TIMESHEETS','ROLE_EXPORT_MY_TIMESHEETS')")
	public ModelAndView newTimesheet(HttpServletRequest request,@PathVariable Optional<String> notificationUUID) throws Exception {
			ModelAndView model = new ModelAndView();
			
			commonUtil.highLightMenu(Constants.TimeSheet, request);
			String loginOrgLocation = (String) SourceLeadSessionManager.getSessionAttribute(request, "locationCode");
			String loginOrgCode = (String) SourceLeadSessionManager.getSessionAttribute(request, "orgCode");
			User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			OrgLocations orgLoc = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			
			String orgCode = loginOrgCode;
			String locationCode = loginOrgLocation;
			Integer notificationCount = notificationsService.getNotificationsCountByUserId(loginUser.getId(),orgCode,locationCode);
			SourceLeadSessionManager.removeSessionAttribute(request, "notificationCount");
			SourceLeadSessionManager.setSessionAttribute(request, "notificationCount",notificationCount);
			
			Timezone timezone = timeZoneService.getTimezone(orgLoc.getTimezone().getId());
			Long notificationTo = loginUser.getId();
			List<Notifications> notificationList = notificationsService.getAllNotificationsListByUserId(notificationTo,orgCode,locationCode);
			calculateDays(notificationList, timezone);
			SourceLeadSessionManager.removeSessionAttribute(request, "notificationList");
			SourceLeadSessionManager.setSessionAttribute(request, "notificationList",notificationList);
			
			if(notificationUUID != null && notificationUUID.isPresent()) {
				Notifications notifications = notificationsService.getNotificationsByUUID(notificationUUID.get());
				if(notifications != null && StringUtils.isNotEmpty(notifications.getParameterList())) {
					HashMap<String, String>  paramList = CommonUtil.getJsonAsMap(notifications.getParameterList());
					model.addObject("notifyLink", true);
					
					String weekFrequency = "";
					String startDateStr = "";
					String endDateStr = "";
					String dateFormat = "";
					if(paramList != null && !paramList.isEmpty()) {
						for(Map.Entry<String,String> entry : paramList.entrySet()) {
							if(entry.getKey().equalsIgnoreCase("projectMemberId"))
							{
								model.addObject(entry.getKey(), entry.getValue());
							}else if(entry.getKey().equalsIgnoreCase("startDateStr")) {
								startDateStr = entry.getValue();
							}else if(entry.getKey().equalsIgnoreCase("endDateStr")) {
								endDateStr = entry.getValue();
							}else if(entry.getKey().equalsIgnoreCase("dateFormat")) {
								dateFormat = entry.getValue();
							}
						}
					}
					if(StringUtils.isNotEmpty(dateFormat) && StringUtils.isNotEmpty(startDateStr) && StringUtils.isNotEmpty(endDateStr)) {
						 Date frequencyStartDate = commonUtil.orgLocationDateFormat(dateFormat,startDateStr);
						 Date frequencyEndDate = commonUtil.orgLocationDateFormat(dateFormat,endDateStr);
						 String actualStartDate = commonUtil.dateStrFromOrgDateFormatter(orgLoc.getDateFormat(),frequencyStartDate);
						 String actualEndDate = commonUtil.dateStrFromOrgDateFormatter(orgLoc.getDateFormat(),frequencyEndDate);
						 weekFrequency = actualStartDate+"To"+actualEndDate;
						 model.addObject("currentTimeFrequency", weekFrequency);
					}
				}	
			}	
			List<ProjectMembers> projectMembersList =  projectMembersService.getProjectMembersListByOrgLocationsIdAndEmpUserId(orgLoc.getId(), loginUser.getId());
			model.addObject("ProjectMembersList", projectMembersList);
			
			model.addObject("sourceleadUrl", sourceleadUrl);
			model.setViewName("timesheet/timesheet");
			return model;

	}
	
	
	@RequestMapping(value = "/ws/acceptOrRejectReopenTimeSheet")
	@ResponseBody  ModelAndView acceptOrRejectReopenTimeSheet(@RequestParam(value="projectMemberId", required=false) Long projectMemberId,
			@RequestParam(value="userId", required=false) Long userId,
			@RequestParam(value="timesheetFrequency", required=false) String timesheetFrequency,
			@RequestParam(value="type", required=false) String type,
			@RequestParam(value="reason", required=false) String reason,HttpServletRequest request) throws Exception {
		
		    User superUser = userService.getUserById(userId);
		    ProjectMembers projectMembers = projectMembersService.getProjectMemberById(projectMemberId);
		     OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		     String isGlobalAccess = (String) SourceLeadSessionManager.getSessionAttribute(request, "isGlobalAccess");
		   // SimpleDateFormat simpleDateFormat = orgLocations.getLocFullFormDateFormatter();
		    ModelAndView model = new ModelAndView();
		    
		    String[] frequencyDate = timesheetFrequency.split("To");
		 	 String weekStartDateTimeSheet = frequencyDate[0].trim();
		  	 String weekEndDateTimeSheet = frequencyDate[1].trim();
		  	 Date startDateTimeSheet = commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),weekStartDateTimeSheet.replaceAll("/", "-"));
			 Date endDateTimeSheet = commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),weekEndDateTimeSheet.replaceAll("/", "-"));
			SubmissionApproverHierarchy subApproverHierarchy = submissionApproverHierarchyService.getSubmissionApproverHierarchyByMasterCandidateIdStartDateAndEndDateForApproverLogin(userId, projectMembers.getProjects().getId(),projectMembers.getMasterCandidateId().getId(),orgLocations.getId(),startDateTimeSheet, endDateTimeSheet,isGlobalAccess);
			String approvalStatus = subApproverHierarchy.getSubmissionApprovalStatus().getValue();
		    	//DateFormat format = simpleDateFormat;
			if(approvalStatus.equalsIgnoreCase("new") || subApproverHierarchy.getReopenRejectedReason() != null){
				type = "actionPerformed";
			}else{
				if(StringUtils.isNotEmpty(timesheetFrequency) && null != type && "accept".equalsIgnoreCase(type)){
				 	 String[] frequency = timesheetFrequency.split("To");
				 	 String weekStartDate = frequency[0].trim();
				  	 String weekEndDate = frequency[1].trim();
				  	 Date startDate = commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),weekStartDate.replaceAll("/", "-"));
					 Date endDate = commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),weekEndDate.replaceAll("/", "-"));
					 User reopenedUser = submissionApproverHierarchyService.updateSubmissionStatusByProjectMemberIdStartDateAndEndDateWithReOpenReason(projectMemberId, startDate, endDate,reason,superUser,type,Constants.NO);
				  	//send mail & notification logic
					 timesheetEmailUtil.sendReopenTimeSheetAcceptOrRejectMail(projectMemberId,reason,superUser,projectMembers.getProjects().getOrgLocationsId(),timesheetFrequency,type,reopenedUser);
				}else if(!approvalStatus.equalsIgnoreCase("new") && (StringUtils.isNotEmpty(timesheetFrequency) && null != type && "reject".equalsIgnoreCase(type) && null != reason && !"".equalsIgnoreCase(reason))){
					String[] frequency = timesheetFrequency.split("To");
				 	 String weekStartDate = frequency[0].trim();
				  	 String weekEndDate = frequency[1].trim();
				  	 Date startDate = commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),weekStartDate.replaceAll("/", "-"));
				  	 Date endDate = commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),weekEndDate.replaceAll("/", "-"));
					 User reopenedUser = submissionApproverHierarchyService.updateSubmissionStatusByProjectMemberIdStartDateAndEndDateWithReOpenReason(projectMemberId, startDate, endDate,reason,superUser,type,Constants.NO);
				  	//send mail & notification logic
					 timesheetEmailUtil.sendReopenTimeSheetAcceptOrRejectMail(projectMemberId,reason,superUser,projectMembers.getProjects().getOrgLocationsId(),timesheetFrequency,type,reopenedUser);
				}	
			}
			 
		    /*if(subApproverHierarchy.getReopenRejectedReason()!= null && !"".equalsIgnoreCase(subApproverHierarchy.getReopenRejectedReason())){
		    	model.addObject("rejectedReason",true);
		    }*/
			model.setViewName("timesheet/reopenTimeSheetAcceptOrReject");
			model.addObject("SubmissionApprovalStatus",subApproverHierarchy.getSubmissionApprovalStatus().getValue());
			model.addObject("projectMemberId",projectMemberId);
			model.addObject("userId",userId);
			model.addObject("timesheetFrequency",timesheetFrequency);
			model.addObject("type",type);
			model.addObject("sourceleadUrl", sourceleadUrl);
			return model;
	}
	
	
	@RequestMapping(value = "/viewDocument/{title}", method = RequestMethod.GET)
    public void viewDocument(@RequestParam(value="submissionDocumentId", required=false) Long submissionDocumentId,
    		@PathVariable(value="title") String title,HttpServletResponse response) throws Exception {
	
	TimesheetSubmissionsDocuments	timesheetSubmissionsDocuments =	timesheetSubmissionsDocumentsService.getTimesheetSubmissionsDocuments(submissionDocumentId);
		
	byte[] pdfBytes = null;
	if("application/pdf".equalsIgnoreCase(timesheetSubmissionsDocuments.getFileContentType())){
		pdfBytes = timesheetSubmissionsDocuments.getDocFile();
	}
	else{
		 pdfBytes = pdfConversionUtil.convertDocToPdf(timesheetSubmissionsDocuments.getDocFile());
	}
	 response.setHeader("Content-disposition","inline; filename=contractPdf.pdf");
  	    response.setContentType("application/pdf");
  	    ServletOutputStream sos = response.getOutputStream();
  	    if(null != pdfBytes){
  	    	response.setContentLength(pdfBytes.length);
  	    	sos.write(pdfBytes);
  	    }
          sos.flush();
          sos.close();
	}
		
	/**
	 * @author naresh bolisetty and sarath kumar
	 * 
	 * this method is for null check
	 * 
	 * @since  jan 27 2016
	 * 
	 * @param str
	 * 
	 * @return boolean
	 */
	
	public static boolean isNullOrEmpty(String str) {
		return str == null || "".equals(str.trim());
	}
	
	private void calculateDays(List<Notifications> notificationList, Timezone timezone) throws ParseException {
		for (Notifications notifications : notificationList) {
			long diff = CommonUtil.currentDate().getTime() - notifications.getCreatedDate().getTime();
		    long diffSeconds = diff / 1000 % 60;
			long diffMinutes = diff / (60 * 1000) % 60;
			long diffHours = diff / (60 * 60 * 1000) % 24;
			long diffDays = diff / (24 * 60 * 60 * 1000);
			StringBuilder  time = new StringBuilder();
			if(diffDays == 0 && diffHours == 0 && diffMinutes == 0){
				notifications.setCalDays(time.append(diffSeconds).append(" sec ago ").toString());
			}else if(diffDays == 0 && diffHours == 0){
				notifications.setCalDays(time.append(diffMinutes).append(" min ").append(diffSeconds).append(" sec ago ").toString());
			}else if(diffDays == 0){
				notifications.setCalDays(time.append(diffHours).append(" hours ").append(diffMinutes).append(" min ago ").toString());
			}else if(diffDays < 7){
				notifications.setCalDays(time.append(diffDays).append(" day(s) ago").toString());
			}else{
				DateFormat standardFormat = new SimpleDateFormat( "MMM dd, yyyy hh:mm:ss aa");
				String currentTimeStr = Constants.TZ_DF.format(notifications.getCreatedDate());
				String currentDateInTimezone= CommonUtil.convertTimeZones("UTC" ,timezone.getTimeZoneId(), currentTimeStr);
				DateFormat TZ_DF_TS = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
				Date converteDate = TZ_DF_TS.parse(currentDateInTimezone);
				String commonDateFormat = standardFormat.format(converteDate);
				notifications.setCalDays(commonDateFormat.toString());
					
				}
		}
	}
 
}