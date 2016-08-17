package com.bis.sourcelead.rest;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.entity.Notifications;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.NotificationsService;
import com.bis.sourcelead.util.SourceLeadSessionManager;

/**
 * 
 * @author Prasanna vadla
 *
 */

@RestController
public class NotificationRestController {

	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	/*
	 * To change the notification mode as read
	 */
	private static final Logger logger = LoggerFactory.getLogger(NotificationRestController.class);

	@Autowired
	private NotificationsService notificationsService;
	/**
	 * when user click on the notifications, we are updating those notifications mode as read 
	 * @param userId
	 * @param orgCode
	 * @param locationCode
	 * @return
	 */
	@RequestMapping(value = "/notificationStatusAsRead", method = RequestMethod.POST)
	public @ResponseBody String notificationStatusAsRead(@RequestParam(value = "orgCode", required = false) String orgCode,
						@RequestParam(value = "locationCode", required = false) String locationCode, HttpServletRequest request) {
		User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		
		List<Notifications> notificationList = notificationsService.getNotificationsListByUserId(loginUser.getId(),orgCode,locationCode);
		
		for(Notifications notification : notificationList){
			notification.setMode(Constants.NOTIFICATION_READ);
			Integer updatecount = notificationsService.updateNotificationStatusAsRead(loginUser.getId(), orgCode, locationCode);
		}
		return "success";
	}
	
	
	/**
	 * when user delete the notification, we are updating that notification status as deleted of that particular user
	 * 
	 * @param notificationId
	 * @param orgCode
	 * @param locationCode
	 * @return
	 */
	@RequestMapping(value = "/deleteNotification", method = RequestMethod.POST)
	public @ResponseBody String deleteNotification(
			@RequestParam(value = "notificationId", required = false) Long notificationId,
			@RequestParam(value = "orgCode", required = false) String orgCode,
			@RequestParam(value = "locationCode", required = false) String locationCode) {
			Integer updatedCount = notificationsService.updateNotificationDeleteStatus(notificationId,orgCode,locationCode);
		return "success";
	}

	/**
	 * when user delete all the notification, we are updating all notification status as deleted of that particular user
	 * @param notificationIds
	 * @param orgCode
	 * @param locationCode
	 * @return
	 */
	@RequestMapping(value = "/deleteAllNotification", method = RequestMethod.POST)
	public @ResponseBody String deleteAllNotification(
			@RequestParam(value = "notificationIds", required = false) String notificationIds,
			@RequestParam(value = "orgCode", required = false) String orgCode,
			@RequestParam(value = "locationCode", required = false) String locationCode) {
		String[] notificationId  = notificationIds.split(",");
		for (int i = 0; i < notificationId.length; i++) {
			Integer updatedCount = notificationsService.updateNotificationDeleteStatus(Long.parseLong(notificationId[i]),orgCode,locationCode);
		}
		
		return "success";
	}
}
