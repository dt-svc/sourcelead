package com.bis.sourcelead.web;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.entity.Notifications;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.NotificationsService;
import com.bis.sourcelead.service.UserRoleService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.Constants;
import com.bis.sourcelead.util.SourceLeadSessionManager;

@Controller
public class LoginSessionController {
	
	@Autowired
	UserRoleService userRoleService;
	
	@Autowired
	UserService userService;

	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	@Autowired 
	private NotificationsService notificationsService;
	
	@RequestMapping(value = "/home" , method = RequestMethod.GET)
	   public ModelAndView loginSession(HttpServletRequest request, HttpServletResponse response,
				Authentication authentication) throws ParseException {
		
		
		User user = userService.findByUserName(authentication.getName());
		setSessionUserAttributes(user,request);
		
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
		model.addObject("notificationList", notificationList);
		model.addObject("sourceleadUrl", sourceleadUrl);
		model.setViewName("home");
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


	private void setSessionUserAttributes(User user, HttpServletRequest request)
	{
		SourceLeadSessionManager.setSessionAttribute(request, "user", user);
		SourceLeadSessionManager.setSessionAttribute(request, "user", user);
		SourceLeadSessionManager.setSessionAttribute(request, "userCode", user.getUserCode());
		SourceLeadSessionManager.setSessionAttribute(request, "userId", user.getId());
	}
	
	
}
