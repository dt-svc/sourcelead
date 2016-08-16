package com.bis.sourcelead.web;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.entity.Notifications;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.Tabs;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserRole;
import com.bis.sourcelead.service.NotificationsService;
import com.bis.sourcelead.service.ProjectMembersService;
import com.bis.sourcelead.service.TabsService;
import com.bis.sourcelead.service.TimesheetSubmissionsService;
import com.bis.sourcelead.service.UserRoleService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SessionMaintainanceData;
import com.bis.sourcelead.util.SourceLeadSessionManager;

@Controller
@PropertySource("${propertyLocation:app.properties}")
public class LoginSessionController {
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
	
	@RequestMapping(value = "/dashboard" , method = RequestMethod.GET)
	   public ModelAndView loginSession(HttpServletRequest request, HttpServletResponse response,
				Authentication authentication) throws ParseException {
		
		SessionMaintainanceData sessionData = (SessionMaintainanceData)request.getSession().getAttribute("sessionData");
		if(sessionData == null)
		{
			sessionData = new SessionMaintainanceData();
		}
		sessionData.setNav(Constants.Dashboard);
		request.getSession().setAttribute("sessionData",sessionData);
		
		User user = userService.findByUserName(authentication.getName());
		setSessionUserAttributes(user,request,sourceleadUrl);
		
		try {
			UserRole userRole = userRoleService.getLoginUserPrimaryRole(user.getId());
			OrgLocations orgLocations = userRole.getOrgLocations();
			setSessionOrgLocationAttributes(orgLocations,request);
			Org org = orgLocations.getOrg();
			setSessionOrgAttributes(org,request);
			
			User orgPrimaryUser = org.getUser();
			User orgLocationPrimaryUser = orgLocations.getUser();
			
			if(user.getId().equals(orgPrimaryUser.getId()) || user.getId().equals(orgLocationPrimaryUser.getId())){
				SourceLeadSessionManager.setSessionAttribute(request, "orgSettingsVisible", "Y");
			}
			
			if(userRole.getRoles().getRoleName().equalsIgnoreCase("SOURCELEAD_ADMIN")){
				SourceLeadSessionManager.setSessionAttribute(request, "enrollOrgVisible", "Y");
			}
			
			
			List<Tabs> tabsList = null;
			
			if("slead".equalsIgnoreCase(org.getOrgCode())){
				tabsList = tabsService.getSourceLeadTabsListByOrgId(userRole.getRoles().getId(), org.getId());
			}else{
				tabsList = tabsService.getTabsListByLoginUserRoleIdAndOrgLocationId(userRole.getRoles().getId(), orgLocations.getId());
			}
			
			SourceLeadSessionManager.setSessionAttribute(request, "tabsList", tabsList);
			SourceLeadSessionManager.setSessionAttribute(request, "loginUserRole", userRole.getRoles().getRoleName());
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		String loginOrgLocation = (String) SourceLeadSessionManager.getSessionAttribute(request, "locationCode");
		String loginOrgCode = (String) SourceLeadSessionManager.getSessionAttribute(request, "orgCode");
		User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		
		ModelAndView model = new ModelAndView();
		//get notification count
		String orgCode = loginOrgCode;
		String locationCode = loginOrgLocation;
		Integer notificationCount = notificationsService.getNotificationsCountByUserId(loginUser.getId(),orgCode,locationCode);
		SourceLeadSessionManager.removeSessionAttribute(request, "notificationCount");
		SourceLeadSessionManager.setSessionAttribute(request, "notificationCount",notificationCount);
		//model.addObject("notificationCount", notificationCount);
		
		// get notification list by user id
		Long notificationTo = loginUser.getId();
		List<Notifications> notificationList = notificationsService.getAllNotificationsListByUserId(notificationTo,orgCode,locationCode);
		calculateDays(notificationList);
		commonUtil.storeTimesheetSubmissionRecords(user);
		//model.addObject("notificationList", notificationList);
		SourceLeadSessionManager.removeSessionAttribute(request, "notificationList");
		SourceLeadSessionManager.setSessionAttribute(request, "notificationList",notificationList);
		model.addObject("sourceleadUrl", sourceleadUrl);
		model.setViewName("dashboard");
	    return model;
		
	}
	
	private void calculateDays(List<Notifications> notificationList) throws ParseException {
		for (Notifications notifications : notificationList) {
					//differentiating date and time
						Date date = notifications.getCreatedDate();
					    String[] parts = date.toString().split(" ");
					    notifications.setDateValue(parts[0]);
					    final SimpleDateFormat sdf = new SimpleDateFormat("H:mm");
					    final Date dateObj = sdf.parse(parts[1].toString());
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
		SourceLeadSessionManager.setSessionAttribute(request, "userCode", user.getUserCode());
		SourceLeadSessionManager.setSessionAttribute(request, "userId", user.getId());
		SourceLeadSessionManager.setSessionAttribute(request, "userName", user.getFirstName()+" "+user.getLastName());
		SourceLeadSessionManager.setSessionAttribute(request, "userType", user.getUserType());
	}
	
	private void setSessionOrgLocationAttributes(OrgLocations orgLocations, HttpServletRequest request)
	{
		SourceLeadSessionManager.setSessionAttribute(request, "orgLocations", orgLocations);
		SourceLeadSessionManager.setSessionAttribute(request, "locationCode", orgLocations.getLocationCode());
		SourceLeadSessionManager.setSessionAttribute(request, "country", orgLocations.getCountry());
	}
	
	private void setSessionOrgAttributes(Org org, HttpServletRequest request)
	{
		SourceLeadSessionManager.setSessionAttribute(request, "org", org);
		SourceLeadSessionManager.setSessionAttribute(request, "orgCode", org.getOrgCode());
		SourceLeadSessionManager.setSessionAttribute(request, "orgName", org.getOrgName());
		SourceLeadSessionManager.setSessionAttribute(request, "website", org.getWebSite());
		SourceLeadSessionManager.setSessionAttribute(request, "vms", org.getVms());
		SourceLeadSessionManager.setSessionAttribute(request, "ats", org.getAts());
		SourceLeadSessionManager.setSessionAttribute(request, "timesheet", org.getTimeSheet());
		
	}
	
	
}