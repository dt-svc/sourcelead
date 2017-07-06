package com.bis.sourcelead.web;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.bis.sourcelead.entity.TimesheetSubmissionsDocuments;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.NotificationsService;
import com.bis.sourcelead.service.ProjectMembersService;
import com.bis.sourcelead.service.SubmissionApproverHierarchyService;
import com.bis.sourcelead.service.TimesheetEntriesService;
import com.bis.sourcelead.service.TimesheetSubmissionsDocumentsService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.service.impl.TimeSheetExcelView;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.PdfConversionUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;
 

@Controller 
public class TimesheetController {
	
	private static final Logger logger = LoggerFactory.getLogger(TimesheetController.class);
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
	SubmissionApproverHierarchyService submissionApproverHierarchyService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	private TimesheetSubmissionsDocumentsService timesheetSubmissionsDocumentsService;
	
	@Autowired
	private PdfConversionUtil pdfConversionUtil;
	
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
	 * 
	 * @throws NullPointer
	 */
	
	@RequestMapping(value = "/timesheet")
	@PreAuthorize("hasAnyRole('ROLE_FILL_MY_TIMESHEETS','ROLE_MODIFY_MY_TIMESHEETS','ROLE_EXPORT_MY_TIMESHEETS')")
	public ModelAndView newTimesheet(HttpServletRequest request) {
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
			
			
			Long notificationTo = loginUser.getId();
			List<Notifications> notificationList = notificationsService.getAllNotificationsListByUserId(notificationTo,orgCode,locationCode);
			SourceLeadSessionManager.removeSessionAttribute(request, "notificationList");
			SourceLeadSessionManager.setSessionAttribute(request, "notificationList",notificationList);
			
			List<ProjectMembers> projectMembersList =  projectMembersService.getProjectMembersListByOrgLocationsIdAndEmpUserId(orgLoc.getId(), loginUser.getId());
			model.addObject("ProjectMembersList", projectMembersList);
			
			model.addObject("sourceleadUrl", sourceleadUrl);
			model.setViewName("timesheet/timesheet");
			return model;

	}
	
	
	@RequestMapping(value = "/acceptOrRejectReopenTimeSheet")
	@ResponseBody  ModelAndView acceptOrRejectReopenTimeSheet(@RequestParam(value="projectMemberId", required=false) Long projectMemberId,
			@RequestParam(value="userId", required=false) Long userId,
			@RequestParam(value="timesheetFrequency", required=false) String timesheetFrequency,
			@RequestParam(value="type", required=false) String type,
			@RequestParam(value="reason", required=false) String reason,HttpServletRequest request) throws Exception {
		
		    User superUser = userService.getUserById(userId);
		    ProjectMembers projectMembers = projectMembersService.getProjectMemberById(projectMemberId);
		    
		    ModelAndView model = new ModelAndView();
			DateFormat format = Constants.DF_DMY;
			if(StringUtils.isNotEmpty(timesheetFrequency) && null != type && "accept".equalsIgnoreCase(type)){
			 	 String[] frequency = timesheetFrequency.split("To");
			 	 String weekStartDate = frequency[0].trim();
			  	 String weekEndDate = frequency[1].trim();
			  	 Date startDate = format.parse(weekStartDate.replaceAll("/", "-"));
				 Date endDate = format.parse(weekEndDate.replaceAll("/", "-"));
			  	
				 User reopenedUser = submissionApproverHierarchyService.updateSubmissionStatusByProjectMemberIdStartDateAndEndDateWithReOpenReason(projectMemberId, startDate, endDate,reason,superUser,type,Constants.NO);
			  	 
			  	//send mail & notification logic
			  	 submissionApproverHierarchyService.sendReopenTimeSheetAcceptOrRejectMail(projectMemberId,reason,superUser,projectMembers.getProjects().getOrgLocationsId(),timesheetFrequency,type,reopenedUser);
			}else if(StringUtils.isNotEmpty(timesheetFrequency) && null != type && "reject".equalsIgnoreCase(type) && null != reason && !"".equalsIgnoreCase(reason)){
				 String[] frequency = timesheetFrequency.split("To");
			 	 String weekStartDate = frequency[0].trim();
			  	 String weekEndDate = frequency[1].trim();
			  	 Date startDate = format.parse(weekStartDate.replaceAll("/", "-"));
			  	 Date endDate = format.parse(weekEndDate.replaceAll("/", "-"));
			  	 
				 User reopenedUser = submissionApproverHierarchyService.updateSubmissionStatusByProjectMemberIdStartDateAndEndDateWithReOpenReason(projectMemberId, startDate, endDate,reason,superUser,type,Constants.NO);
			  	//send mail & notification logic
			  	 submissionApproverHierarchyService.sendReopenTimeSheetAcceptOrRejectMail(projectMemberId,reason,superUser,projectMembers.getProjects().getOrgLocationsId(),timesheetFrequency,type,reopenedUser);
			}
			
			model.setViewName("timesheet/reopenTimeSheetAcceptOrReject");
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
 
}