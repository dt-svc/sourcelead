package com.bis.sourcelead.dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.NotificationsDao;
import com.bis.sourcelead.entity.Notifications;

@Repository
public class NotificationsDaoImpl implements NotificationsDao{

	@Autowired
	private SessionFactory sessionFactory;
	
	private static final Logger logger = LoggerFactory.getLogger(NotificationsDaoImpl.class);
	
	/**
	 * This method for save Notification
	 */
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Notifications saveNotification(Notifications notification) {
		logger.debug("In NotificationsDaoImpl class save method");
		Session session = this.sessionFactory.getCurrentSession();
		session.save(notification);
		return notification;
	}

	/**
	 * This method for get notifications count by userId
	 */
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Integer getNotificationsCountByUserId(Long userId, String orgCode, String locationCode) {
		Session session =  this.sessionFactory.getCurrentSession();
		String query = "select count(*)  from Notifications nf where nf.notificationScenarioId = :userId  and nf.orgCode = :orgCode and nf.locationCode = :locationCode ";
		List<Long> notificationList = (List<Long>)session.createQuery(query).setLong("userId", userId)
				   													.setString("orgCode", orgCode)
				   													.setString("locationCode", locationCode).list();
		Integer notificationCount = null;
		
			if(notificationList != null && !notificationList.isEmpty()){
				notificationCount = notificationList.get(0).intValue();
			}
		return notificationCount;
	}


	/**
	 * This method for get notifications list by userId
	 */
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<Notifications> getNotificationsListByUserId(Long notificationTo, String orgCode, String locationCode) {
		
		Session session =  this.sessionFactory.getCurrentSession();
		String query = "from Notifications nf where nf.notificationTo = :notificationTo  and nf.orgCode = :orgCode and nf.locationCode = :locationCode ";
		List<Notifications> notificationList = (List<Notifications>)session.createQuery(query).setLong("notificationTo", notificationTo)
				   													.setString("orgCode", orgCode)
				   													.setString("locationCode", locationCode).list();
		return notificationList;
	}

}
