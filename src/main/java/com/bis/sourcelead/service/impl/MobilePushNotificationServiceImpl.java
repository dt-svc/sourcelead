package com.bis.sourcelead.service.impl;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.MobilePushNotificationDao;
import com.bis.sourcelead.dto.MobilePushNotificationDTO;
import com.bis.sourcelead.entity.MobileDeviceTokenInformaion;
import com.bis.sourcelead.entity.MobilePushNotification;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.MobilePushNotificationService;
import com.bis.sourcelead.util.PushNotificationUtill;
import com.bis.sourcelead.util.SourceLeadRandomCodeHelper;
import com.bis.sourcelead.util.SourceLeadSessionManager;

@Service
public class MobilePushNotificationServiceImpl implements MobilePushNotificationService{
	
	 @Autowired
	 HttpServletRequest request;
	 
	 @Autowired
	 MobilePushNotificationDao mobilePushNotificationDao;

	 @Autowired
	 PushNotificationUtill pushNotificationUtill;
	 
	 

	@Override
	public String saveMobileDeviceTokenInformation(MobilePushNotificationDTO mobilePushNotificationDTO) throws Exception {
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		MobileDeviceTokenInformaion mdti =new MobileDeviceTokenInformaion();
		mdti.setUserId(user);
		mdti.setDeviceToken(mobilePushNotificationDTO.getDeviceToken());
		mdti.setDeviceType(mobilePushNotificationDTO.getDeviceType());
		mdti.setDeviceUUID(SourceLeadRandomCodeHelper.generateRandomPassword());
		mdti.setStatus(Constants.RECORD_ACTIVE);
		mdti.setCreatedDate(new Date());
		mobilePushNotificationDao.saveMobileDeviceTokenInformaion(mdti);
		return "success";
	}


	@Override
	public String saveMobilePushNotification(MobilePushNotificationDTO mobilePushNotificationDTO) throws Exception {
		MobileDeviceTokenInformaion mdti = mobilePushNotificationDao.getMobileDeviceTokenInformaionByUserId(mobilePushNotificationDTO.getUserId().getId());
		if(mdti != null) {
			MobilePushNotification mpn = new MobilePushNotification();
			mpn.setUserId(mobilePushNotificationDTO.getUserId());
			mpn.setNotificationDescritpion(mobilePushNotificationDTO.getNotificationDescritpion().replaceAll("&nbsp;", " "));
			mpn.setStatus(Constants.RECORD_ACTIVE);
			mpn.setCreatedDate(new Date());
			mobilePushNotificationDao.saveMobileDeviceTokenInformaion(mpn);
            List<MobileDeviceTokenInformaion> mobileTokeknLis = mobilePushNotificationDao.mobileDeviceTokenInformaionLisByUserId(mdti.getUserId().getId());
            {
            	if(mobileTokeknLis != null) {
            		for(MobileDeviceTokenInformaion mdtf :mobileTokeknLis) {
                		mobilePushNotificationDTO.setDeviceToken(mdtf.getDeviceToken());
                		mobilePushNotificationDTO.setDeviceType(mdtf.getDeviceType());
                		pushNotificationUtill.sendAndriodPushNotification(mobilePushNotificationDTO);
                	}
            	}
            	
            }
		}
		return "success";
	}

	
	@Override
	public Boolean validateMobileDevicceTokenByUserIdAndTokeIndAndDeviceType(Long userId, String tokenId, String deviceType) throws Exception {
		Boolean flag =mobilePushNotificationDao.validateMobileDevicceTokenByUserIdAndTokeIndAndDeviceType(userId, tokenId, deviceType);
		return flag;
	}


	@Override
	public List<MobileDeviceTokenInformaion> mobileDeviceTokenInformaionList(Long userId,String deviceType) throws Exception {
		return mobilePushNotificationDao.mobileDeviceTokenInformaionList(userId,deviceType);
	}


	@Override
	public MobileDeviceTokenInformaion getMobileDeviceTokenInformaionByUserId(Long userId) throws Exception {
		return mobilePushNotificationDao.getMobileDeviceTokenInformaionByUserId(userId);
	}


	@Override
	public void deleteMobileDeviceTokenInformationByTokenId(String tokenId) throws Exception {
		mobilePushNotificationDao.deleteMobileDeviceTokenInformationByTokenId(tokenId);		
	}


	
	 
}
