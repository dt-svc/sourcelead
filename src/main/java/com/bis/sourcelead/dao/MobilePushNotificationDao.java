package com.bis.sourcelead.dao;

import java.util.List;

import com.bis.sourcelead.entity.MobileDeviceTokenInformaion;
import com.bis.sourcelead.entity.MobilePushNotification;

public interface MobilePushNotificationDao {
	
	/**
	 * @author Laxman
	 * @param department
	 * @return
	 * @throws Exception
	 */
	 MobileDeviceTokenInformaion saveMobileDeviceTokenInformaion(MobileDeviceTokenInformaion mobileDeviceTokenInformaion) throws Exception;
	
	 MobilePushNotification saveMobileDeviceTokenInformaion(MobilePushNotification mobilePushNotification) throws Exception;
	 
     Boolean validateMobileDevicceTokenByUserIdAndTokeIndAndDeviceType(Long userId,String tokenId,String deviceType) throws Exception;

	 List<MobileDeviceTokenInformaion> mobileDeviceTokenInformaionList(Long userId,String deviceType)throws Exception;
	 
	 List<MobileDeviceTokenInformaion> mobileDeviceTokenInformaionLisByUserId(Long userId)throws Exception;

	 MobileDeviceTokenInformaion getMobileDeviceTokenInformaionByUserId(Long userId)throws Exception;

     void deleteMobileDeviceTokenInformationByTokenId(String tokenId)throws Exception;

}
