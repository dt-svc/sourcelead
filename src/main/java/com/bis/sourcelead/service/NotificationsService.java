package com.bis.sourcelead.service;

import java.util.List;

import com.bis.sourcelead.entity.Notifications;

/**
 * 
 * @author Prasad salina
 *
 */
public interface NotificationsService {

	Notifications saveNotification(Notifications notification);

	Integer getNotificationsCountByUserId(Long userId, String orgCode, String locationCode);

	List<Notifications> getNotificationsListByUserId(Long notificationTo, String orgCode,String locationCode);

	Integer updateNotificationStatusAsRead(Long userId, String orgCode, String locationCode);

	Integer updateNotificationDeleteStatus(Long notificationId, String orgCode, String locationCode);

	List<Notifications> getAllNotificationsListByUserId(Long userId, String orgCode, String locationCode);

}
