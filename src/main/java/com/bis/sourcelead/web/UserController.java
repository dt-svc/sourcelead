package com.bis.sourcelead.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.Projects;
import com.bis.sourcelead.entity.Roles;
import com.bis.sourcelead.entity.TimeSheetSubmissionCount;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserRole;
import com.bis.sourcelead.service.ProjectsService;
import com.bis.sourcelead.service.RolesService;
import com.bis.sourcelead.service.TimesheetSubmissionsService;
import com.bis.sourcelead.service.UserRoleService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.CommonUtil;
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
	
	@Autowired
	UserService userService;
	
	@Autowired
	RolesService rolesService;
	
	@Autowired
	UserRoleService userRoleService;
	
	/**
	 * To forward user creation jsp page
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@PreAuthorize("hasRole('ROLE_CREATE_USER')")
	@RequestMapping(value = "/userCreation")
	public ModelAndView userCreation(HttpServletRequest request) throws Exception {
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
		ModelAndView model = new ModelAndView();
		
		    Long roleHierarchyId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "roleHierarchyId");
		
			List<Projects> projectsList = projectsService.getProjectByOrgLocationsId(orgLocations.getId());
		    model.addObject("org_projectsList", projectsList);
		    
		    List<Roles> rolesList = rolesService.getUserRolesListByModuleAndLoginUserRoleHierarchy(org,orgLocations.getId(),roleHierarchyId);
		    model.addObject("rolesList", rolesList);
		    
		   		    
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
	@PreAuthorize("hasAnyRole('ROLE_VIEW_USER', 'ROLE_VIEW_EDIT_USER', 'ROLE_VIEW_DELETE_USER')")
	@RequestMapping(value = "/userSummaryDetails")
	public ModelAndView userSummaryDetails(HttpServletRequest request,
			@RequestParam(value = "userId", required = false) Long userId) throws Exception {
		ModelAndView model = new ModelAndView();
		
			OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
			String isTimeSheetEnrolled = (String) SourceLeadSessionManager.getSessionAttribute(request,"timesheet");
			
			User user = userService.getUserByIdAndOrgLocationsId(userId, orgLocations.getId());
			if(user != null){
				// to get a project list by userId
               
				/*List<Projects> projectsList = projectsService.getProjectByOrgLocationsIdAndUserId(orgLocations.getId(),userId);
				model.addObject("org_projectsList", projectsList);*/
				
				if(!isNullOrEmpty(isTimeSheetEnrolled) && isTimeSheetEnrolled.equalsIgnoreCase("Y")){
					Integer assignedProjectsCount = projectsService.getUserAssignedProjectsCount(userId, orgLocations.getId());
					TimeSheetSubmissionCount userdetails  = timesheetSubmissionsService.getTimeSheetSubmissionsCountByUserIdAndOrgLocationId(userId, orgLocations.getId());
					
					model.addObject("totalOpenTimeSheetCount", userdetails.getSavedTimesheetSubmissionCount().intValue());
					model.addObject("approvedTimeSheetsCount", userdetails.getApprovedTimesheetSubmissionCount().intValue());
					model.addObject("rejectedTimeSheetsCount", userdetails.getRejectedTimesheetSubmissionCount().intValue());
					model.addObject("waitingTimeSheetsCount", userdetails.getWaitingForApprovalTimesheetSubmissionCount().intValue());
					model.addObject("assignedProjectsCount", assignedProjectsCount);
				}
				
				
				request.setAttribute("userId", userId);
				model.setViewName("users/userSummary");
			}else{
				model.setViewName("registration/persmissionDenied");
			}
			model.addObject("sourceleadUrl", sourceleadUrl);
			
		
		return model;

	}
	/**
	 * checks string is empty or null
	 * @param str
	 * @return
	 */
	 
	 protected static boolean isNullOrEmpty(String str) {
			return str == null || "".equals(str.trim());
		}
}
