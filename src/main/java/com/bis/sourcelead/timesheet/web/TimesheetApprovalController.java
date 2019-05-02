package com.bis.sourcelead.timesheet.web;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.entity.Notifications;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.service.NotificationsService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SessionMaintainanceData;
import com.bis.sourcelead.util.SourceLeadSessionManager;
 

@Controller 
@RequestMapping("/app")
public class TimesheetApprovalController {
	 
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	@Autowired
	private NotificationsService notificationsService;
	
	@Autowired
	private CommonUtil commonUtil;
	
		
	@PreAuthorize("hasAnyRole('ROLE_FILL_MEMBER_TIMESHEETS','ROLE_VIEW_MEMBER_TIMESHEETS','ROLE_EXPORT_MEMBER_TIMESHEETS')")
	@RequestMapping(value = {"/timesheetApproval","/timesheetApproval/{notificationUUID}"})
	public ModelAndView newTimesheetApproval(@RequestParam(value="notifyLink", required=false) String notifyLink,
			@RequestParam(value="currentTimeFrequency", required=false) String currentTimeFrequency,
			@RequestParam(value="projectId", required=false) Long projectId,
			@RequestParam(value="projectMemberId", required=false) Long projectMemberId,
			@RequestParam(value="masterCandidateId", required=false) Long masterCandidateId,
			@RequestParam(value="totalHours", required=false) Float totalHours,
			@RequestParam(value="from", required=false) String from,
			@PathVariable Optional<String> notificationUUID,
			HttpServletRequest request) throws Exception {
		
		   OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			ModelAndView model = new ModelAndView();
			
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
							if(entry.getKey().equalsIgnoreCase("projectName") || entry.getKey().equalsIgnoreCase("searchData") || entry.getKey().equalsIgnoreCase("timesheetFrequency")  || entry.getKey().equalsIgnoreCase("frequencyMonth"))
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
						 String actualStartDate = commonUtil.dateStrFromOrgDateFormatter(loginOrgLocation.getDateFormat(),frequencyStartDate);
						 String actualEndDate = commonUtil.dateStrFromOrgDateFormatter(loginOrgLocation.getDateFormat(),frequencyEndDate);
						 weekFrequency = actualStartDate+"To"+actualEndDate;
						 model.addObject("weekFrequency", weekFrequency);
					}
				}
			}
			
			SessionMaintainanceData sessionData = (SessionMaintainanceData)request.getSession().getAttribute("sessionData");
			if(sessionData == null)
			{
				sessionData = new SessionMaintainanceData();
			}
			sessionData.setNav(Constants.TimeSheetApproval);
			
			request.getSession().setAttribute("sessionData",sessionData);


			model.addObject("sourceleadUrl", sourceleadUrl);
			
			 model.setViewName("timesheet/timesheetApproval");
	        return model;
	}
	
	 public static boolean isNullOrEmpty(String str) {
			return str == null || "".equals(str.trim());
		}
 
	 /**
		 * @author David
		 * This Method is for redirecting  to Timsheet Submissions List 
		 * @param request
		 * @param status
		 * @return
		 * @throws Exception
		 */
		@RequestMapping(value = "/timesheetlistByStatus")
		public ModelAndView timesheetListByStatus(HttpServletRequest request,
				@RequestParam(value = "status", required = false) String status) throws Exception {
			ModelAndView model = new ModelAndView();
			request.setAttribute("status", status);
			
			model.setViewName("timesheet/timesheetApprovalDashboardStatusList");
			model.addObject("sourceleadUrl", sourceleadUrl);
			return model;

		}
	 
}