package com.bis.sourcelead.service;

import java.util.List;

import com.bis.sourcelead.dto.MobilePushNotificationDTO;
import com.bis.sourcelead.entity.MobileDeviceTokenInformaion;

public interface MobilePushNotificationService {

	String  saveMobileDeviceTokenInformation(MobilePushNotificationDTO mobilePushNotificationDTO) throws Exception;
	
	Boolean validateMobileDevicceTokenByUserIdAndTokeIndAndDeviceType(Long userId,String tokenId,String deviceType) throws Exception;
 
	List<MobileDeviceTokenInformaion> mobileDeviceTokenInformaionList(Long userId,String deviceType)throws Exception;
	
	MobileDeviceTokenInformaion getMobileDeviceTokenInformaionByUserId(Long userId)throws Exception;
	
	String saveMobilePushNotification(MobilePushNotificationDTO mobilePushNotificationDTO) throws Exception;
	
	void deleteMobileDeviceTokenInformationByTokenId(String tokenId)throws Exception;
}
