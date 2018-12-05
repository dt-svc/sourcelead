package com.bis.sourcelead.service;

import java.util.List;

import com.bis.sourcelead.dto.NotificationDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.Notifications;
import com.bis.sourcelead.entity.Timezone;

/**
 * 
 * @author Prasad salina
 *
 */
public interface NotificationsService {

	Notifications saveNotification(Notifications notification) throws Exception;

	Integer getNotificationsCountByUserId(Long userId, String orgCode, String locationCode);

	List<Notifications> getNotificationsListByUserId(Long notificationTo, String orgCode,String locationCode);

	Integer updateNotificationStatusAsRead(Long userId, String orgCode, String locationCode);

	Integer updateNotificationDeleteStatus(Long notificationId, String orgCode, String locationCode);

	List<Notifications> getAllNotificationsListByUserId(Long userId, String orgCode, String locationCode);
	
	List<Notifications> getTopTenNotificationsListByUserId(Long userId, String orgCode, String locationCode);
	
	Notifications getNotificationsById(Long Id) throws Exception;
	
	Notifications getNotificationsByUUID(String UUID) throws Exception;

	Notifications getNotificationBySpocUUID(String spocUUID) throws Exception;

	Notifications getNotificationDetailsByUUID(String UUID) throws Exception;

	ResponseList<Notifications> getDynamicAllNotificationsListByUserId(NotificationDTO notificationDTO, Long userId, String orgCode, String locationCode, Timezone timezone);
	
	String getNotificationUUIDByOrgSpocId(Long orgSpocId) throws Exception;
}
