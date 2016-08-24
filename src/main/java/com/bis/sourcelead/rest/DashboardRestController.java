package com.bis.sourcelead.rest;

import java.io.Writer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.TimeSheetSubmissionCount;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.TimesheetSubmissionsService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.json.JsonHierarchicalStreamDriver;
import com.thoughtworks.xstream.io.json.JsonWriter;

/**
 * This class is used for...
 * 
 * @author: Venkat Kesav
 * @date: 15-July-2016
 */

@RestController
public class DashboardRestController {

	private static final Logger logger = LoggerFactory.getLogger(DashboardRestController.class);

	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	@Autowired
	public TimesheetSubmissionsService timesheetSubmissionsService;
	
	@Autowired
	public OrgLocationService orgLocationService;
	
	@Autowired
	CommonUtil commonUtil;
	/****
	 * This  controller method is   for getting TimesheetSubmission Counts in dashboard Landing Page Based on User Login
	 * @author: Sammeta David
	 */
	
	@RequestMapping(value = "/getTimeSheetSubmissionStatus")
	public String getTimeSheetSubmissionStatus(HttpServletRequest request) throws Exception {

		String resultJson;
		String userRole = (String) SourceLeadSessionManager.getSessionAttribute(request, "loginUserRole");

	    OrgLocations loginOrgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		TimeSheetSubmissionCount timeSheetSubmissionCount = new TimeSheetSubmissionCount();
		
		if("ROLE_ADMIN".equalsIgnoreCase(userRole) || "ROLE_APPROVER".equalsIgnoreCase(userRole)){
			timeSheetSubmissionCount = timesheetSubmissionsService.getTimeSheetSubmissionsCountByUserIdAndOrgLocationIdForApprover(user.getId(), loginOrgLocations.getId());
		}else if("ROLE_USER".equalsIgnoreCase(userRole)){
			timeSheetSubmissionCount = timesheetSubmissionsService.getTimeSheetSubmissionsCountByUserIdAndOrgLocationId(user.getId(), loginOrgLocations.getId());
		}
		resultJson = commonUtil.toJSON(timeSheetSubmissionCount);
		return resultJson;
	}
	/****
	 * 
	 * @author: Pavan Jampana 
	 * This  controller method is  to get Timesheet Submissions list based on status 
	 */
	
	@RequestMapping(value = "/totalTimesheetList/{status}")
	public @ResponseBody void tiesheetStatusList(@PathVariable("status") String status,HttpServletResponse response) throws Exception {
		response.sendRedirect("/sourcelead/timesheetlistByStatus?status=" + status);
	}

}
