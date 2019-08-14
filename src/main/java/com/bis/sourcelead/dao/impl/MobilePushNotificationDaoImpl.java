package com.bis.sourcelead.dao.impl;

import java.text.MessageFormat;
import java.util.Collection;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.MobilePushNotificationDao;
import com.bis.sourcelead.entity.MobileDeviceTokenInformaion;
import com.bis.sourcelead.entity.MobilePushNotification;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;

@Repository
@Transactional
@SuppressWarnings("unchecked")
public class MobilePushNotificationDaoImpl implements MobilePushNotificationDao	{
	
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Value("${SourceLead.100}")
	private String notFound;
	@Value("${SourceLead.101}")
	private String noRecords;
	@Value("${SourceLead.102}")
	private String statusUpdate;
	@Value("${SourceLead.103}")
	private String statusDelete;
	@Value("${SourceLead.106}")
	private String duplicateValue;
	
	private static final Logger logger = LoggerFactory.getLogger(MobilePushNotificationDaoImpl.class);


	@Override
	public MobileDeviceTokenInformaion saveMobileDeviceTokenInformaion(MobileDeviceTokenInformaion mobileDeviceTokenInformaion) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (mobileDeviceTokenInformaion != null) {
				session.saveOrUpdate(mobileDeviceTokenInformaion);
			}
		} catch (Exception exception) {
			 StringBuilder message= new StringBuilder(MessageFormat.format(duplicateValue,"MOBILE_DEVICE_TOKEN_INFORMATION"));
				throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		return mobileDeviceTokenInformaion;
	}


	@Override
	public Boolean validateMobileDevicceTokenByUserIdAndTokeIndAndDeviceType(Long userId, String tokenId, String deviceType) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		 boolean flag = false;
			 String query = "from MobileDeviceTokenInformaion mdti  where mdti.userId.id =:userId and mdti.deviceToken =:tokenId and mdti.deviceType =:deviceType";
				Collection<MobileDeviceTokenInformaion> tokenList = session.createQuery(query) .setString("tokenId", tokenId).setLong("userId", userId).setString("deviceType", deviceType).list();
				if (tokenList != null && tokenList.size() > 0) {
					flag = true;
				}
		return flag;
	}


	@Override
	public List<MobileDeviceTokenInformaion> mobileDeviceTokenInformaionList(Long userId,String deviceType) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from MobileDeviceTokenInformaion mdti  where mdti.userId.id =:userId and mdti.deviceType =:deviceType";
		List<MobileDeviceTokenInformaion> tokenList = session.createQuery(query).setLong("userId", userId).setString("deviceType", deviceType).list();
		return tokenList;
	}


	@Override
	public MobilePushNotification saveMobileDeviceTokenInformaion(MobilePushNotification mobilePushNotification) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (mobilePushNotification != null) {
				session.saveOrUpdate(mobilePushNotification);
			}
		} catch (Exception exception) {
			 StringBuilder message= new StringBuilder(MessageFormat.format(duplicateValue,"MOBILE_PUSH_NOTIFICATION"));
				throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		return mobilePushNotification;
	}


	@Override
	public MobileDeviceTokenInformaion getMobileDeviceTokenInformaionByUserId(Long userId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from MobileDeviceTokenInformaion mdti  where mdti.userId.id =:userId";
		List<MobileDeviceTokenInformaion> tokenList =session.createQuery(query).setLong("userId", userId).list();
		MobileDeviceTokenInformaion deviceToken = null;
		if(tokenList != null && !tokenList.isEmpty())
		{
			deviceToken = (MobileDeviceTokenInformaion) tokenList.iterator().next();
		}
		return deviceToken;
	}


	@Override
	public void deleteMobileDeviceTokenInformationByTokenId(String tokenId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "delete from MobileDeviceTokenInformaion mdti where mdti.deviceToken =:toeknId";
		Query querq = session.createQuery(query);
	}


	@Override
	public List<MobileDeviceTokenInformaion> mobileDeviceTokenInformaionLisByUserId(Long userId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from MobileDeviceTokenInformaion mdti  where mdti.userId.id =:userId and mdti.status = 1";
		List<MobileDeviceTokenInformaion> tokenList = session.createQuery(query).setLong("userId", userId).list();
		return tokenList;
	}

 
}
