package com.bis.sourcelead.web;

import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.ProjectApproverHierarchy;
import com.bis.sourcelead.entity.SubmissionApproverHierarchy;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.ProjectApproverHierarchyService;
import com.bis.sourcelead.service.SubmissionApproverHierarchyService;
import com.bis.sourcelead.service.TimesheetSubmissionsService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.json.JsonHierarchicalStreamDriver;
import com.thoughtworks.xstream.io.json.JsonWriter;

/**
 * This class is used for Timesheet Submission List.
 * 
 * @author: Pavan Jampana
 * @date: 20-July-2016
 */

@Controller
public class TimesheetSubmissionListController {

	private static final Logger logger = LoggerFactory.getLogger(TimesheetSubmissionListController.class);

	@Value("${sourceleadUrl}")
	private String sourceleadUrl;

	@Autowired
	public TimesheetSubmissionsService timesheetSubmissionsService;
	
	@Autowired
	public SubmissionApproverHierarchyService submissionApproverHierarchyService;
	
	@Autowired
	public OrgLocationService orgLocationService;
	
	 @Autowired
	 CommonUtil commonUtil;
	
	@Autowired
	public ProjectApproverHierarchyService projectApproverHierarchyService;

	
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
		
		model.setViewName("timesheet/timesheetStatusList");
		model.addObject("sourceleadUrl", sourceleadUrl);
		return model;

	}
	
	/***
	 * @author Sammeta David
	 * This method is get TimesheetList By Status
	 * @param status
	 * @param request
	 * @return
	 * @throws Null Exception
	 */
	 @RequestMapping(value = "/getTimesheetListByStatus", method = RequestMethod.POST)
	 public @ResponseBody String getTimesheetListByStatus(@RequestParam(value="status", required=false) String status,HttpServletRequest request) throws Exception{ 
		 String resultJson = null;
			request.setAttribute("status", status);
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			String userRole = (String) SourceLeadSessionManager.getSessionAttribute(request, "loginUserRole");
			String loginOrgLocation = (String) SourceLeadSessionManager.getSessionAttribute(request, "locationCode");
		    OrgLocations loginOrgLocations = orgLocationService.getOrgLocationsByLocationCode(loginOrgLocation);
			
			String output =	submissionApproverHierarchyService.getTotalTimesheetSubmissionListByUserIdAndOrgLocationIdAndStatusBasedOnUserRoleLogin(user.getId(), loginOrgLocations.getId(), status, userRole);

		    return output;
	 }
	 

}
