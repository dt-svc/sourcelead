package com.bis.sourcelead.web;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.entity.Notifications;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.Projects;
import com.bis.sourcelead.entity.SourceleadActions;
import com.bis.sourcelead.entity.Tabs;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserRole;
import com.bis.sourcelead.service.ModulesService;
import com.bis.sourcelead.service.NotificationsService;
import com.bis.sourcelead.service.ProjectMembersHistoryService;
import com.bis.sourcelead.service.ProjectMembersService;
import com.bis.sourcelead.service.ProjectsService;
import com.bis.sourcelead.service.SourceleadActionsService;
import com.bis.sourcelead.service.TabsService;
import com.bis.sourcelead.service.TimesheetSubmissionsService;
import com.bis.sourcelead.service.UserRoleService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;

@Controller
@PropertySource("${propertyLocation:app.properties}")
public class LoginSessionController {
	
	private static final Logger logger = LoggerFactory.getLogger(LoginSessionController.class);
	
	@Autowired
	UserRoleService userRoleService;
	
	@Autowired
	ProjectMembersService projectMembersService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	TimesheetSubmissionsService timesheetSubmissionsService;
	
	@Autowired
	TabsService  tabsService;

	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	@Autowired
	CommonUtil commonUtil;
	
	@Autowired 
	private NotificationsService notificationsService;
	
	@Autowired 
	private ProjectsService projectsService;
	
	@Autowired 
	private ProjectMembersHistoryService projectMembersHistoryService;
	
	@Autowired 
	private ModulesService modulesService;
	
	@Autowired 
	private SourceleadActionsService sourceleadActionsService;
	
	@RequestMapping(value = "/dashboard" , method = RequestMethod.GET)
	   public ModelAndView loginSession(HttpServletRequest request,Authentication authentication) throws Exception {
		
		User user = userService.findByUserName(authentication.getName());
		UserRole userRole = userRoleService.getLoginUserPrimaryRole(user.getId());
		
		OrgLocations orgLocations = userRole.getOrgLocations();
		Org org = orgLocations.getOrg();
		List<Long> modulesId = modulesService.getOrgRegisteredModules(org);
		List<Tabs> tabsList = null;
		if(org.getOrgType().equalsIgnoreCase("Company")){
			tabsList = tabsService.getTabsListByOrgLocationId(modulesId, userRole.getRoles().getId(), orgLocations.getId());
		}
		
		SourceLeadSessionManager.setSessionAttribute(request, "tabsList", tabsList);
		
		
		String[] modules = org.getModulesId().split(",");
		for(int i = 0 ; i< modules.length ; i++){
			if(modules[i].equalsIgnoreCase(Constants.MODULE_ATS_STR)){
				SourceLeadSessionManager.setSessionAttribute(request, "ats", "Y");
			}
			
			if(modules[i].equalsIgnoreCase(Constants.MODULE_VMS_STR)){
				SourceLeadSessionManager.setSessionAttribute(request, "vms", "Y");
			}
			
			if(modules[i].equalsIgnoreCase(Constants.MODULE_TIMESHEET_STR)){
				SourceLeadSessionManager.setSessionAttribute(request, "timesheet", "Y");
			}
		}
		
		
		setLoginSession(request, authentication,user,userRole);
		
		ModelAndView model = new ModelAndView();
		String msg = (String) SourceLeadSessionManager.getSessionAttribute(request, "resetPwdMsg");
		if(null != msg && !msg.isEmpty()){
			model.addObject("msg", msg);
			SourceLeadSessionManager.removeSessionAttribute(request, "resetPwdMsg");
		}
		model.addObject("sourceleadUrl", sourceleadUrl);
		model.setViewName("dashboard");
	    return model;
		
	}
	
	
	
	@RequestMapping(value = "/dashboardLinks" , method = RequestMethod.GET)
	   public ModelAndView loginSessionLinks(HttpServletRequest request,HttpServletResponse response,Authentication authentication,
			   @RequestParam(value = "orgLocationId", required = false) Long orgLocationId) throws Exception {
		
		ModelAndView model = new ModelAndView();
		User user = userService.findByUserName(authentication.getName());
		UserRole userRole = userRoleService.getUserDetailsByEmailAndLocationId(user.getEmail(), orgLocationId);
		if(null != userRole){
			OrgLocations orgLocations = userRole.getOrgLocations();
			Org org = orgLocations.getOrg();
			
			List<Long> modulesId = modulesService.getOrgRegisteredModules(org);
			List<SourceleadActions> actionsList = sourceleadActionsService.getLoginUserRoleActionsByOrgLocationId(modulesId, userRole.getRoles().getId(), orgLocations.getId());
			
			List<Tabs> tabsList = null;
			if(org.getOrgType().equalsIgnoreCase("Company")){
				tabsList = tabsService.getTabsListByOrgLocationId(modulesId, userRole.getRoles().getId(), orgLocations.getId());
			}
			
			String[] modules = org.getModulesId().split(",");
			for(int i = 0 ; i< modules.length ; i++){
				if(modules[i].equalsIgnoreCase(Constants.MODULE_ATS_STR)){
					SourceLeadSessionManager.setSessionAttribute(request, "ats", "Y");
				}
				
				if(modules[i].equalsIgnoreCase(Constants.MODULE_VMS_STR)){
					SourceLeadSessionManager.setSessionAttribute(request, "vms", "Y");
				}
				
				if(modules[i].equalsIgnoreCase(Constants.MODULE_TIMESHEET_STR)){
					SourceLeadSessionManager.setSessionAttribute(request, "timesheet", "Y");
				}
			}
			
			String userName = (String) authentication.getPrincipal();
			SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(userName,null, buildUserAuthority(actionsList)));
			System.out.println(authentication.getAuthorities());
			SourceLeadSessionManager.setSessionAttribute(request, "tabsList", tabsList);
			setLoginSession(request, authentication,user,userRole);
			model.addObject("sourceleadUrl", sourceleadUrl);
			model.setViewName("dashboard");
		}else{
			model.setViewName("registration/persmissionDenied");
		}
		
	    return model;
		
	}
	
	
	
	private void setLoginSession(HttpServletRequest request,Authentication authentication,User user,UserRole userRole) throws Exception{
		
      commonUtil.highLightMenu(Constants.Dashboard, request);
		
		
		setSessionUserAttributes(user,request,sourceleadUrl);
		
		try {
			OrgLocations orgLocations = userRole.getOrgLocations();
			setSessionOrgLocationAttributes(orgLocations,request);
			Org org = orgLocations.getOrg();
			setSessionOrgAttributes(org,request);
			
			User orgPrimaryUser = org.getUser();
			User orgLocationPrimaryUser = orgLocations.getUser();
			
			if(orgLocationPrimaryUser != null && (user.getId().equals(orgPrimaryUser.getId()) || user.getId().equals(orgLocationPrimaryUser.getId()))){
				SourceLeadSessionManager.setSessionAttribute(request, "orgSettingsVisible", "Y");
			}
			
			if("ROLE_SOURCELEAD_ADMIN".equalsIgnoreCase(userRole.getRoles().getRoleName())){
				SourceLeadSessionManager.setSessionAttribute(request, "enrollOrgVisible", "Y");
			}
			
			
			List<UserRole> userSpocRolesList = userRoleService.getUserSpocsListByUserId(user.getId());
			if(userSpocRolesList.size() > 1){
				SourceLeadSessionManager.setSessionAttribute(request, "userSpocRolesList", userSpocRolesList);
				SourceLeadSessionManager.setSessionAttribute(request, "userSpocRolesListCount", userSpocRolesList.size());
			}
			
			
			
			/*if("slead".equalsIgnoreCase(org.getOrgCode())){
				tabsList = tabsService.getSourceLeadTabsListByOrgId(userRole.getRoles().getId(), org.getId());
			}else{
				 tabsList = tabsService.getTabsListByLoginUserRoleIdAndOrgLocationId(userRole.getRoles().getId(), orgLocations.getId());
			}
			*/
			
			SourceLeadSessionManager.setSessionAttribute(request, "loginUserRole", userRole.getRoles().getRoleName());
			SourceLeadSessionManager.setSessionAttribute(request, "loginUserRoleId", userRole.getRoles().getId());

			SourceLeadSessionManager.setSessionAttribute(request, "roleHierarchyId", userRole.getRoles().getRoleHierarchy().getId());

			SourceLeadSessionManager.setSessionAttribute(request, "userType", userRole.getUserType());
			
			
		} catch (Exception e) {
			logger.error("Exception cause is : "+ExceptionUtils.getFullStackTrace(e));
		}
		
		String loginOrgLocation = (String) SourceLeadSessionManager.getSessionAttribute(request, "locationCode");
		String loginOrgCode = (String) SourceLeadSessionManager.getSessionAttribute(request, "orgCode");
		User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations orgLoc = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		
		
		//get notification count
		String orgCode = loginOrgCode;
		String locationCode = loginOrgLocation;
		Integer notificationCount = notificationsService.getNotificationsCountByUserId(loginUser.getId(),orgCode,locationCode);
		SourceLeadSessionManager.removeSessionAttribute(request, "notificationCount");
		SourceLeadSessionManager.setSessionAttribute(request, "notificationCount",notificationCount);
		
		// get notification list by user id
		Long notificationTo = loginUser.getId();
		List<Notifications> notificationList = notificationsService.getAllNotificationsListByUserId(notificationTo,orgCode,locationCode);
		calculateDays(notificationList);
		commonUtil.storeTimesheetSubmissionRecords(user);
		commonUtil.terminateAllProjectMembersByUser(user);
		
		try {
			List<Projects> projectsList = projectsService.getProjectByOrgLocationsIdAndEndDateGraterthanCurrentDate(orgLoc.getId(), loginUser.getId());
			for(Projects project : projectsList){
				commonUtil.terminateProject(project);
			}
			projectsService.saveAllProjects(projectsList);
		} catch (Exception e) {
			logger.error("Exception cause is : "+ExceptionUtils.getFullStackTrace(e));
		}
		
		
		SourceLeadSessionManager.removeSessionAttribute(request, "notificationList");
		SourceLeadSessionManager.setSessionAttribute(request, "notificationList",notificationList);
		
		
	}
	
	private void calculateDays(List<Notifications> notificationList) throws ParseException {
		for (Notifications notifications : notificationList) {
					//differentiating date and time
						Date date = notifications.getCreatedDate();
					    String[] parts = date.toString().split(" ");
					    notifications.setDateValue(parts[0]);
					    final SimpleDateFormat sdf = new SimpleDateFormat("H:mm");
					    final Date dateObj = sdf.parse(parts[1]);
					    notifications.setTime(new SimpleDateFormat("K:mm a").format(dateObj));
					    
					    // calculating days b/w dates 
					    Date currentDate = new Date();
						Date d1 = notifications.getCreatedDate();
						SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
						String strDate = dateFormat.format(currentDate);
						Date d2 = dateFormat.parse(strDate);
						long diff = d2.getTime() - d1.getTime();
						long diffDays = diff / (24 * 60 * 60 * 1000);
						notifications.setCalDays(diffDays);
					
				}
		
	}


	private void setSessionUserAttributes(User user, HttpServletRequest request,String sourceleadUrl)
	{
		
		SourceLeadSessionManager.setSessionAttribute(request, "sourceleadUrl", sourceleadUrl);
		SourceLeadSessionManager.setSessionAttribute(request, "user", user);
		SourceLeadSessionManager.setSessionAttribute(request, "userType", user.getUserType());
		SourceLeadSessionManager.setSessionAttribute(request, "userCode", user.getUserCode());
		SourceLeadSessionManager.setSessionAttribute(request, "userId", user.getId());
		SourceLeadSessionManager.setSessionAttribute(request, "userName", user.getFirstName()+" "+user.getLastName());
		
	}
	
	private void setSessionOrgLocationAttributes(OrgLocations orgLocations, HttpServletRequest request)
	{
		SourceLeadSessionManager.setSessionAttribute(request, "orgLocations", orgLocations);
		SourceLeadSessionManager.setSessionAttribute(request, "orgLocationId", orgLocations.getId());
		SourceLeadSessionManager.setSessionAttribute(request, "locationCode", orgLocations.getLocationCode());
		SourceLeadSessionManager.setSessionAttribute(request, "country", orgLocations.getCountry());
	}
	
	private void setSessionOrgAttributes(Org org, HttpServletRequest request)
	{
		SourceLeadSessionManager.setSessionAttribute(request, "org", org);
		SourceLeadSessionManager.setSessionAttribute(request, "orgCode", org.getOrgCode());
		SourceLeadSessionManager.setSessionAttribute(request, "orgName", org.getOrgName());
		SourceLeadSessionManager.setSessionAttribute(request, "website", org.getWebSite());
	}
	
	
  private List<GrantedAuthority> buildUserAuthority(List<SourceleadActions> actionsList) {

    	Set<GrantedAuthority> setAuths = new HashSet<GrantedAuthority>();
       
    	if(actionsList != null && !actionsList.isEmpty()){
    		// Build user's authorities
    		for (SourceleadActions sourceleadActions : actionsList) {
    			setAuths.add(new SimpleGrantedAuthority(sourceleadActions.getActionRole()));
    		}
    	}
    	List<GrantedAuthority> Result = new ArrayList<GrantedAuthority>(setAuths);
    	return Result;
    }
	
}