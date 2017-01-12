package com.bis.sourcelead.web;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.entity.Notifications;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.NotificationsService;
import com.bis.sourcelead.service.TimesheetEntriesService;
import com.bis.sourcelead.service.impl.TimeSheetExcelView;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;
 

@Controller 
public class NewTimesheetController {
	private static final Logger logger = LoggerFactory.getLogger(NewTimesheetController.class);
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
	
	/**
	 * @author sarath kumar punnana 
	 * this  controller method is for timesheet screen redirection.
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/newTimesheet")
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
			
			model.addObject("sourceleadUrl", sourceleadUrl);
			model.setViewName("timesheet/newTimesheet");
			return model;

	}
		
	/**
	 * @author sarath kumar punnana
	 * this method is for null check
	 * @param str
	 * @return boolean
	 */
	public static boolean isNullOrEmpty(String str) {
		return str == null || "".equals(str.trim());
	}
 
}