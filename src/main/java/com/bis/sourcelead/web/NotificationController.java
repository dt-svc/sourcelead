package com.bis.sourcelead.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bis.sourcelead.dao.impl.NotificationsDaoImpl;
import com.bis.sourcelead.entity.Notifications;
import com.bis.sourcelead.service.NotificationsService;
/**
 * 
 * @author Prasanna vadla
 *
 */

@Controller
public class NotificationController {
	
	/*
	 * To change the notification mode as read 
	 */
	private static final Logger logger = LoggerFactory.getLogger(NotificationController.class);
	
	@Autowired 
	private NotificationsService notificationsService;
	
	
	@RequestMapping(value = "/notificationStatusAsRead",  method = RequestMethod.POST)
	public @ResponseBody String notificationStatusAsRead(@RequestParam(value="userId", required=false) Long userId, 
														@RequestParam(value="orgCode", required=false) String orgCode,
														@RequestParam(value="locationCode", required=false) String locationCode)
			{
				Integer updatecount = notificationsService.updateNotificationStatusAsRead(userId,orgCode,locationCode);
				logger.info("updated count of notifiactions "+updatecount);
				return "success";
		
	}
	

}
