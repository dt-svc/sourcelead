package com.bis.sourcelead.web;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.entity.Notifications;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.TimesheetEntries;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.NotificationsService;
import com.bis.sourcelead.service.TimesheetEntriesService;
import com.bis.sourcelead.service.impl.TimeSheetExcelView;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;
 

@Controller 
public class TimesheetEntriesController {
	private static final Logger logger = LoggerFactory.getLogger(TimesheetEntriesController.class);
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
	@RequestMapping(value = "/timesheet")
	@PreAuthorize("hasAnyRole('ROLE_CREATE_MY_TIMESHEETS','ROLE_VIEW_MY_TIMESHEETS','ROLE_EDIT_MY_TIMESHEETS','ROLE_WITHDRAW_MY_TIMESHEETS')")
	public ModelAndView timesheet(HttpServletRequest request) {
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
			model.setViewName("timesheet/timesheet");
			return model;

	}

	/**
	 * @author Pavan Jampana
	 * This method is for TimeSheet Pdf Download
	 * @return
	 */
	@RequestMapping(value = "/downloadTimesheetPdf", method = RequestMethod.GET)
	public ModelAndView generateAllTimesheetPdf()  {
		ModelAndView view = new ModelAndView("redirect:/timesheetPdf/downloadPdf");
		return view;
	}
		
	/**
	 * @author shivayogi s kadagad
	 * controller to download Exceltimesheet
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/Excel", method = RequestMethod.GET)
	public ModelAndView getExcel()  {
		
		Collection<TimesheetEntries> timesheetEntriesList = null;
		try {
			timesheetEntriesList = timesheetEntriesService .getAllTimesheetEntries();
		} catch (Exception exception) {
			logger.error(ExceptionUtils.getFullStackTrace(exception)); 
		} 		
		return new ModelAndView(timeSheetExcelView,"timesheetEntriesList",timesheetEntriesList);
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