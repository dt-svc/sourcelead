package com.bis.sourcelead.service.impl;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.internet.InternetAddress;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.NotificationsDao;
import com.bis.sourcelead.dto.NotificationDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.Notifications;
import com.bis.sourcelead.entity.Timezone;
import com.bis.sourcelead.entity.UserMailUnsubscribe;
import com.bis.sourcelead.service.NotificationsService;
import com.bis.sourcelead.service.UserMailUnsubscribeService;

@Service
public class NotificationsServiceImpl implements NotificationsService{
	
	
	@Autowired
	NotificationsDao  notificationDao;
	
	@Autowired
	UserMailUnsubscribeService userMailUnsubscribeService;
	
	@Override
	public Notifications saveNotification(Notifications notification) throws Exception{
		UserMailUnsubscribe unsubscriptionUser = null;
		if(notification.getNotificationTo() != null){
			unsubscriptionUser = userMailUnsubscribeService.getUserMailUnsubscriptionByUserEmail(notification.getNotificationTo().getEmail());
			
			if (unsubscriptionUser != null && StringUtils.isNotEmpty(unsubscriptionUser.getTabIds())) {
				String[] tabIdsArray = unsubscriptionUser.getTabIds().split(",");
				if (notification.getNotificationScenarioId().getTabId() != null) {
					if(ArrayUtils.contains( tabIdsArray, notification.getNotificationScenarioId().getTabId().getId().toString())) {
						return null;
					}else if(ArrayUtils.contains( tabIdsArray, "0") && !ArrayUtils.contains( Constants.UNSUBSCRIPTION_TABID_LISTED, notification.getNotificationScenarioId().getTabId().getId().toString())) {
						return null;
					}
				}else if(ArrayUtils.contains( tabIdsArray, "0")) {
					return null;
				}
			}
			return notificationDao.saveNotification(notification);
		}else{
			return null;
		}
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

	@Override
	public Notifications getNotificationsById(Long Id) throws Exception {
		return notificationDao.getNotificationsById(Id);
	}

	@Override
	public Notifications getNotificationsByUUID(String UUID) throws Exception {
		return notificationDao.getNotificationsByUUID(UUID);
	}
	
	@Override
	public Notifications getNotificationBySpocUUID(String spocUUID) throws Exception {
		return notificationDao.getNotificationBySpocUUID(spocUUID);
	}

	@Override
	public Notifications getNotificationDetailsByUUID(String UUID) throws Exception {
		return notificationDao.getNotificationDetailsByUUID(UUID);
	}

	@Override
	public ResponseList<Notifications> getDynamicAllNotificationsListByUserId(NotificationDTO notificationDTO, Long userId, String orgCode, String locationCode, Timezone timezone) {
		return notificationDao.getDynamicAllNotificationsListByUserId(notificationDTO, userId, orgCode, locationCode, timezone);
	}

	public List<Notifications> getTopTenNotificationsListByUserId(Long userId, String orgCode, String locationCode) {
		return notificationDao.getTopTenNotificationsListByUserId(userId, orgCode, locationCode);
	}

	@Override
	public String getNotificationUUIDByOrgSpocId(Long orgSpocId) throws Exception {
		return notificationDao.getNotificationUUIDByOrgSpocId(orgSpocId);
	}


}
