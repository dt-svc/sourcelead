package com.bis.sourcelead.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.NotificationsDao;
import com.bis.sourcelead.entity.Notifications;
import com.bis.sourcelead.service.NotificationsService;

@Service
public class NotificationsServiceImpl implements NotificationsService{
	
	
	@Autowired
	NotificationsDao  notificationDao;

	@Override
	public Notifications saveNotification(Notifications notification) throws Exception{
		return notificationDao.saveNotification(notification);
	}

	@Override
	public Integer getNotificationsCountByUserId(Long userId, String orgCode, String locationCode) {
		return notificationDao.getNotificationsCountByUserId(userId, orgCode, locationCode);
	}

	@Override
	public List<Notifications> getNotificationsListByUserId(Long notificationTo, String orgCode, String locationCode) {
		return notificationDao.getNotificationsListByUserId(notificationTo,orgCode,locationCode);
	}

	@Override
	public Integer updateNotificationStatusAsRead(Long userId, String orgCode, String locationCode) {
		return notificationDao.updateNotificationStatusAsRead(userId,orgCode,locationCode);
	}

	@Override
	public Integer updateNotificationDeleteStatus(Long notificationId, String orgCode, String locationCode) {
		return notificationDao.updateNotificationDeleteStatus(notificationId,orgCode,locationCode);
	}

	@Override
	public List<Notifications> getAllNotificationsListByUserId(Long userId, String orgCode, String locationCode) {
		return notificationDao.getAllNotificationsListByUserId(userId,orgCode,locationCode);
	}


}
