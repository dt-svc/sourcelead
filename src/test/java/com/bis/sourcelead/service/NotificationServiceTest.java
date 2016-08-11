package com.bis.sourcelead.service;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;

import com.bis.sourcelead.WebAppConfig;
import com.bis.sourcelead.entity.EmailScenario;
import com.bis.sourcelead.entity.Notifications;
import com.bis.sourcelead.entity.User;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = WebAppConfig.class, loader = AnnotationConfigWebContextLoader.class)
public class NotificationServiceTest {
	
	@Autowired
	NotificationsService notificationsService;
	
	//@Test
	public void saveNotification() throws Exception{
		Notifications notifications = new Notifications();
		EmailScenario emailScenario = new EmailScenario();
		emailScenario.setId(27L);
		notifications.setNotificationScenarioId(emailScenario);
		User user = new User();
		user.setId(4L);
		notifications.setNotificationTo(user);
		notifications.setNotificationFrom(user);
		notifications.setBody("this is used for testing service");
		notifications.setMode("unread");
		notifications.setActionLink("link");
		notifications.setOrgCode("hangonn");
		notifications.setLocationCode("in.sourcelead.com");
		notifications.setCreatedDate(new Date());
		notifications.setCreatedBy("Source");
		notifications.setNotificationStatus("1");
		notificationsService.saveNotification(notifications);
		System.out.println("Notification Sucessfully Saved");
	}
	
	//@Test
	public void getNotificationsCountByUserId() throws Exception{
		Notifications notifications = new Notifications();
		notifications.setOrgCode("hangonn");
		notifications.setLocationCode("in.sourcelead.com");
		User user = new User();
		user.setId(4L);
		Integer notificationscount = notificationsService.getNotificationsCountByUserId(user.getId(), notifications.getOrgCode(), notifications.getLocationCode());
		System.out.println("Notifications count is...>>"+notificationscount);
	}
	
	//@Test
	public void getNotificationsListByUserId() throws Exception{
		Notifications notifications = new Notifications();
		notifications.setOrgCode("hangonn");
		notifications.setLocationCode("in.sourcelead.com");
		User user = new User();
		user.setId(4L);
		notifications.setNotificationTo(user);
		List<Notifications> list = notificationsService.getNotificationsListByUserId(notifications.getNotificationTo().getId(), notifications.getOrgCode(), notifications.getLocationCode());
		System.out.println("Notification List size is...>>"+list.size());
	}
	
	//@Test
	public void updateNotificationStatusAsRead() throws Exception{
		Notifications notifications = new Notifications();
		notifications.setOrgCode("hangonn");
		notifications.setLocationCode("in.sourcelead.com");
		User user = new User();
		user.setId(4L);
		Integer notificationscount = notificationsService.updateNotificationStatusAsRead(user.getId(), notifications.getOrgCode(), notifications.getLocationCode());
		System.out.println("Notifications count is...>>"+notificationscount);
	}
	
	//@Test
	public void updateNotificationDeleteStatus() throws Exception{
		Notifications notifications = new Notifications();
		notifications.setOrgCode("hangonn");
		notifications.setId(1L);
		notifications.setLocationCode("in.sourcelead.com");
		User user = new User();
		user.setId(4L);
		notifications.setNotificationTo(user);
		Integer notificationscount = notificationsService.updateNotificationDeleteStatus(notifications.getId(), notifications.getOrgCode(), notifications.getLocationCode());
		System.out.println("Notifications count is...>>"+notificationscount);
	}
	
	@Test
	public void getAllNotificationsListByUserId() throws Exception{
		Notifications notifications = new Notifications();
		notifications.setOrgCode("hangonn");
		notifications.setLocationCode("in.sourcelead.com");
		User user = new User();
		user.setId(4L);
		List<Notifications> list = notificationsService.getAllNotificationsListByUserId(user.getId(), notifications.getOrgCode(), notifications.getLocationCode());
		System.out.println("Notification List size is...>>"+list.size());
	}
}
