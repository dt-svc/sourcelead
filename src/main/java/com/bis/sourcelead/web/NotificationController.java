package com.bis.sourcelead.web;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.entity.Notifications;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.service.NotificationsService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;

/**
 * 
 * @author Prasanna vadla
 *
 */

@Controller
public class NotificationController {

	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	@Autowired
	private CommonUtil commonUtil;
	
	/*
	 * To change the notification mode as read
	 */
	private static final Logger logger = LoggerFactory.getLogger(NotificationController.class);
	@Autowired
	private NotificationsService notificationsService;

	

	/**
	 * to get a list of notification of particular user by user id
	 * @param request
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping(value = "/notificationsList")
	public ModelAndView notificationsList(HttpServletRequest request) throws ParseException {
		logger.debug("notificationsList");
		ModelAndView model = new ModelAndView();
		Long userId = (Long) SourceLeadSessionManager.getSessionAttribute(request,"userId");
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
		String locationCode =  (String) SourceLeadSessionManager.getSessionAttribute(request,"locationCode");
		List<Notifications> notificationsList = notificationsService.getAllNotificationsListByUserId(userId,orgLocations.getOrg().getOrgCode(), locationCode);
		if(notificationsList!=null){
			calculateDays(notificationsList);
		}
		model.setViewName("notifications/notificationsList");
		model.addObject("notificationsList", notificationsList);
		model.addObject("sourceleadUrl", sourceleadUrl);
		model.addObject("orgCode", orgLocations.getOrg().getOrgCode());
		model.addObject("locationCode", locationCode);
		return model;

	}
	
	@RequestMapping(value = "/getNotificationtLists")
	public @ResponseBody String getNotificationtLists(HttpServletRequest request) throws Exception {

	    String resultJson;
	    Long userId = (Long) SourceLeadSessionManager.getSessionAttribute(request,"userId");
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
		String locationCode =  (String) SourceLeadSessionManager.getSessionAttribute(request,"locationCode");
		List<Notifications> notificationsList = notificationsService.getAllNotificationsListByUserId(userId,orgLocations.getOrg().getOrgCode(), locationCode);
		if(notificationsList!=null){
			calculateDays(notificationsList);
			for(Notifications nl : notificationsList){
				nl.setNotificationFirstName(nl.getNotificationFrom().getFirstName());
				nl.setNotificationLastName(nl.getNotificationFrom().getLastName());
				nl.setNotificationFormId(nl.getNotificationFrom().getId().toString());
			}
		}
		resultJson = commonUtil.toJSON(notificationsList);
		return resultJson;
	}
	
	
	/**
	 * calculating the days between the project created date and current date 
	 * @param notificationList
	 * @throws ParseException
	 */
	
	public void calculateDays(List<Notifications> notificationList) throws ParseException {
		for (Notifications notifications : notificationList) {
			// differentiating date and time
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
	
}
