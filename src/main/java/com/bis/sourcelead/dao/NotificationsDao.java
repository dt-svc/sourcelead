package com.bis.sourcelead.dao;

import java.util.List;

import com.bis.sourcelead.entity.Notifications;

public interface NotificationsDao {

	Notifications saveNotification(Notifications notification);

	 Integer getNotificationsCountByUserId(Long userId, String orgCode, String locationCode) ;

	List<Notifications> getNotificationsListByUserId(Long notificationTo, String orgCode, String locationCode);	
}
