package com.bis.sourcelead.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.Projects;
import com.bis.sourcelead.entity.TimeSheetSubmissionCount;
import com.bis.sourcelead.service.ProjectsService;
import com.bis.sourcelead.service.TimesheetSubmissionsService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.Constants;
import com.bis.sourcelead.util.SessionMaintainanceData;
import com.bis.sourcelead.util.SourceLeadSessionManager;


@Controller
public class UserController {
	
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	@Autowired
	private ProjectsService projectsService;
	
	@Autowired
	public TimesheetSubmissionsService timesheetSubmissionsService;
	
	@Autowired
	CommonUtil commonUtil;
	
	
	
	/**
	 * To forward user creation jsp page
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/userCreation")
	public ModelAndView userCreation(HttpServletRequest request) throws Exception {
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
	   
		ModelAndView model = new ModelAndView();
		List<Projects> projectsList = projectsService.getProjectByOrgLocationsId(orgLocations.getId());
	    model.addObject("org_projectsList", projectsList);
	    
	    commonUtil.highLightMenu(Constants.Users, request);
	    
		model.setViewName("users/userCreation");
		model.addObject("sourceleadUrl", sourceleadUrl);
		return model;

	}
	
	/**
	 * To forward user summary jsp with userId, and displays the user summary details
	 * @param request
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/userSummaryDetails")
	public ModelAndView userSummaryDetails(HttpServletRequest request,
			@RequestParam(value = "userId", required = false) Long userId) throws Exception {
		ModelAndView model = new ModelAndView();
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
					// to get a project list by userId
		                List<Projects> projectsList = projectsService.getProjectByOrgLocationsIdAndUserId(orgLocations.getId(),userId);
						model.addObject("org_projectsList", projectsList);
						
						
						Integer assignedProjectsCount = projectsService.getUserAssignedProjectsCount(userId, orgLocations.getId());
						TimeSheetSubmissionCount userdetails  = timesheetSubmissionsService.getTimeSheetSubmissionsCountByUserIdAndOrgLocationId(userId, orgLocations.getId());
						
						model.addObject("totalOpenTimeSheetCount", userdetails.getSavedTimesheetSubmissionCount().intValue());
						model.addObject("approvedTimeSheetsCount", userdetails.getApprovedTimesheetSubmissionCount().intValue());
						model.addObject("rejectedTimeSheetsCount", userdetails.getRejectedTimesheetSubmissionCount().intValue());
						model.addObject("waitingTimeSheetsCount", userdetails.getWaitingForApprovalTimesheetSubmissionCount().intValue());
						model.addObject("assignedProjectsCount", assignedProjectsCount);
						
						request.setAttribute("userId", userId);
						model.setViewName("users/userSummary");
						model.addObject("sourceleadUrl", sourceleadUrl);
		return model;

	}
	 
	 protected static boolean isNullOrEmpty(String str) {
			return (str == null || "".equals(str.trim()));
		}
}
