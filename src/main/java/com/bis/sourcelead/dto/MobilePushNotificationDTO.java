package com.bis.sourcelead.dto;

import com.bis.sourcelead.entity.MobileDeviceTokenInformaion;
import com.bis.sourcelead.entity.User;

public class MobilePushNotificationDTO {
	
	private User userId;
	
	private String deviceToken;
	
	private String deviceType;
	
	private String deviceUUID;
	
	private String notificationTitle;
	   		
	private String notificationDescritpion;
	
	private MobileDeviceTokenInformaion deviceId;
	

	public User getUserId() {
		return userId;
	}

	public void setUserId(User userId) {
		this.userId = userId;
	}

	public String getDeviceToken() {
		return deviceToken;
	}

	public void setDeviceToken(String deviceToken) {
		this.deviceToken = deviceToken;
	}

	public String getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	public String getDeviceUUID() {
		return deviceUUID;
	}

	public void setDeviceUUID(String deviceUUID) {
		this.deviceUUID = deviceUUID;
	}

	public String getNotificationDescritpion() {
		return notificationDescritpion;
	}

	public void setNotificationDescritpion(String notificationDescritpion) {
		this.notificationDescritpion = notificationDescritpion;
	}

	public MobileDeviceTokenInformaion getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(MobileDeviceTokenInformaion deviceId) {
		this.deviceId = deviceId;
	}

	public String getNotificationTitle() {
		return notificationTitle;
	}

	public void setNotificationTitle(String notificationTitle) {
		this.notificationTitle = notificationTitle;
	}
}
