package com.bis.sourcelead.web;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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

import com.bis.sourcelead.entity.Notifications;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.ProjectMembers;
import com.bis.sourcelead.entity.TimesheetSubmissions;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserRole;
import com.bis.sourcelead.enums.Submission_Status;
import com.bis.sourcelead.service.NotificationsService;
import com.bis.sourcelead.service.ProjectMembersService;
import com.bis.sourcelead.service.TimesheetSubmissionsService;
import com.bis.sourcelead.service.UserRoleService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.CalendarUtil;
import com.bis.sourcelead.util.Constants;
import com.bis.sourcelead.util.SourceLeadSessionManager;

@Controller
@PropertySource(value = { "classpath:config/application.properties" })
public class LoginSessionController {
	@Autowired
	UserRoleService userRoleService;
	
	@Autowired
	ProjectMembersService projectMembersService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	TimesheetSubmissionsService timesheetSubmissionsService;

	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	@Autowired 
	private NotificationsService notificationsService;
	
	@RequestMapping(value = "/home" , method = RequestMethod.GET)
	   public ModelAndView loginSession(HttpServletRequest request, HttpServletResponse response,
				Authentication authentication) throws ParseException {
		
		
		User user = userService.findByUserName(authentication.getName());
		setSessionUserAttributes(user,request,sourceleadUrl);
		
		try {
			UserRole userRole = userRoleService.getLoginUserPrimaryRole(user.getId());
			OrgLocations orgLocations = userRole.getOrgLocations();
			setSessionOrgLocationAttributes(orgLocations,request);
			Org org = orgLocations.getOrg();
			setSessionOrgAttributes(org,request);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		ModelAndView model = new ModelAndView();
		//get notification count
		String orgCode = Constants.SOURCELEAD_ORGCODE;
		String locationCode = Constants.SOURCELEAD_LOCATIONCODE_IN;
		Long scenarioId = 8L;
		Integer notificationCount = notificationsService.getNotificationsCountByUserId(scenarioId,orgCode,locationCode);
		model.addObject("notificationCount", notificationCount);
		
		// get notification list by user id
		Long notificationTo = 2L;
		List<Notifications> notificationList = notificationsService.getNotificationsListByUserId(notificationTo,orgCode,locationCode);
		calculateDays(notificationList);
		storeTimesheetSubmissionRecords(user);
		model.addObject("notificationList", notificationList);
		model.setViewName("home");
	    return model;
		
	}
	
	private void storeTimesheetSubmissionRecords(User user){
		try {
			List<ProjectMembers> projectMembers =  projectMembersService.getAllProjectMembersIdsByTimesheetApproverUserIdOrMemberUserId(user.getId());
			
			if(projectMembers != null && !projectMembers.isEmpty()){
				ArrayList<TimesheetSubmissions> timesheetSubmissionsList = new ArrayList<TimesheetSubmissions>();
				for(ProjectMembers projectMember :projectMembers){
					Date nextMonday = timesheetSubmissionsService.getNextMondayByProjectMemberId(projectMember.getId());
					Date startMondayDate = null;
					if(nextMonday != null){
						startMondayDate =  nextMonday;
					}
					else{
						startMondayDate = projectMember.getProjectAssignedDate();
					}
					Calendar startCal = Calendar.getInstance(Constants.TIMEZONE_CENTRAL);
					startCal.setLenient(false);
					startCal.setTime(startMondayDate);
					Calendar startMonday = CalendarUtil.getCurrentWeekDayByCalendar(startCal, "MON");
					
					Calendar endCal = Calendar.getInstance(Constants.TIMEZONE_CENTRAL);
					endCal.setLenient(false);
					endCal.setTime(new Date());
					Calendar endMonday = CalendarUtil.getNextWeekDayByCalendar(endCal, "MON");
					
					int totalWeeks = 0; 
					totalWeeks = (int)((endMonday.getTimeInMillis()-startMonday.getTimeInMillis())/(7l * 24l * 60l * 60l * 1000l));
					long startMondayInMilliSeconds = startMonday.getTimeInMillis();
					for (int week = 0; week < totalWeeks; week++) {
						Calendar currentMondayOfTheWeek = Calendar.getInstance();
						long weekVal = 7l * 24l * 60l * 60l * week * 1000l; 
						currentMondayOfTheWeek.setTimeInMillis(startMondayInMilliSeconds + weekVal);
						
						TimesheetSubmissions timesheetSubmissions = new TimesheetSubmissions();
						
						timesheetSubmissions.setProjectMembers(projectMember);
						timesheetSubmissions.setStartDate(currentMondayOfTheWeek.getTime());
						timesheetSubmissions.setEndDate(new Date());
						
						
						Calendar currentWeekEndDate = Calendar.getInstance();
						currentWeekEndDate.setTimeInMillis(currentMondayOfTheWeek.getTimeInMillis() + (6l * 24l * 60l * 60l * 1000l));
						timesheetSubmissions.setEndDate(currentWeekEndDate.getTime());
						
						timesheetSubmissions.setSubmissionStatus(Submission_Status.getNameByValue("new"));
						timesheetSubmissions.setCreatedDate(new Date());
						timesheetSubmissions.setCreatedBy(user.getCreatedBy());
						timesheetSubmissions.setUpdatedDate(new Date());
						timesheetSubmissions.setUpdatedBy(user.getCreatedBy());
						timesheetSubmissionsList.add(timesheetSubmissions);
					}
				}
				timesheetSubmissionsService.saveAllTimesheetSubmissions(timesheetSubmissionsList);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
	}
	
	private void setSessionOrgAttributes(Org org, HttpServletRequest request)
	{
		SourceLeadSessionManager.setSessionAttribute(request, "org", org);
		SourceLeadSessionManager.setSessionAttribute(request, "orgCode", org.getOrgCode());
		SourceLeadSessionManager.setSessionAttribute(request, "orgName", org.getOrgName());
	}
	
	
}
