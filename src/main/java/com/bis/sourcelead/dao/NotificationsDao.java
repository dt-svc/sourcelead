package com.bis.sourcelead.dao;

import java.util.List;

import com.bis.sourcelead.entity.Notifications;

public interface NotificationsDao {

	Notifications saveNotification(Notifications notification) throws Exception;

	 Integer getNotificationsCountByUserId(Long userId, String orgCode, String locationCode) ;

	List<Notifications> getNotificationsListByUserId(Long notificationTo, String orgCode, String locationCode);	
	
	Integer updateNotificationStatusAsRead(Long userId, String orgCode, String locationCode);
	
	Integer updateNotificationDeleteStatus(Long notificationId, String orgCode, String locationCode);

	List<Notifications> getAllNotificationsListByUserId(Long userId, String orgCode, String locationCode);
	
}
