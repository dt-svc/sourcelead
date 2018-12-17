package com.bis.sourcelead.dao.impl;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.NotificationsDao;
import com.bis.sourcelead.dto.ConnectionsDTO;
import com.bis.sourcelead.dto.NotificationDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.Notifications;
import com.bis.sourcelead.entity.RecruiterReportAssociateCountView;
import com.bis.sourcelead.entity.Timezone;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;
import com.bis.sourcelead.service.OrgSpocService;
import com.bis.sourcelead.service.TimeZoneService;
import com.bis.sourcelead.util.CommonUtil;
import org.hibernate.transform.Transformers;


@Repository
@Transactional(rollbackFor = { Exception.class })
public class NotificationsDaoImpl implements NotificationsDao{

	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	private CommonUtil commonUtil;
	
	@Autowired
	private TimeZoneService timeZoneService;
	
	@Autowired
	private OrgSpocService orgSpocService;
	
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
	
	private static final Logger logger = LoggerFactory.getLogger(NotificationsDaoImpl.class);
	
	/**
	 * This method for save Notification
	 */
	@Override
	public Notifications saveNotification(Notifications notification) throws Exception{
		logger.debug("In NotificationsDaoImpl class save method");
		Session session = this.sessionFactory.getCurrentSession();
		try{
			if(notification != null){
				session.saveOrUpdate(notification);
			}
		}catch(Exception exception){
			StringBuilder message= new StringBuilder(MessageFormat.format(duplicateValue,"Notifications"));
			throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		
		return notification;
	}

	/**
	 * This method for get notifications count by userId
	 */
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Integer getNotificationsCountByUserId(Long userId, String orgCode, String locationCode) {
		Session session =  this.sessionFactory.getCurrentSession();
		String mode = Constants.NOTIFICATION_UNREAD;
		String notificationStatus = Constants.NOTIFICATION_NOT_DELETED;
		String query = "select count(*)  from Notifications nf where nf.notificationTo = :userId  and nf.orgCode = :orgCode and nf.locationCode = :locationCode and nf.mode =:mode and nf.notificationStatus =:notificationStatus ";
		List<Long> notificationList = (List<Long>)session.createQuery(query).setLong("userId", userId)
																	.setString("mode", mode)
																	.setString("notificationStatus", notificationStatus)
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
	public List<Notifications> getNotificationsListByUserId(Long notificationTo, String orgCode, String locationCode) {
		
		Session session =  this.sessionFactory.getCurrentSession();
		String mode = Constants.NOTIFICATION_UNREAD;
		String notificationStatus = Constants.NOTIFICATION_NOT_DELETED;
		String query = "from Notifications nf where nf.notificationTo = :notificationTo  and nf.orgCode = :orgCode and nf.locationCode = :locationCode and nf.mode =:mode and nf.notificationStatus =:notificationStatus";
		@SuppressWarnings("unchecked")
		List<Notifications> notificationList = (List<Notifications>)session.createQuery(query).setLong("notificationTo", notificationTo)
																	.setString("mode", mode)
																	.setString("notificationStatus", notificationStatus)
				   													.setString("orgCode", orgCode)
				   													.setString("locationCode", locationCode).list();
		return notificationList;
	}

	/**
	 * used for change the notifications mode as read
	 */
	@Override
	public Integer updateNotificationStatusAsRead(Long userId, String orgCode, String locationCode) {
		// TODO Auto-generated method stub
		Session session =  this.sessionFactory.getCurrentSession();
		String mode = Constants.NOTIFICATION_READ;
		String notificationStatus = Constants.NOTIFICATION_NOT_DELETED;
		Query query = session.createQuery("update Notifications nf set nf.mode =:mode where nf.notificationTo = :userId and nf.orgCode = :orgCode and nf.locationCode = :locationCode and nf.notificationStatus =:notificationStatus");
		query.setLong("userId", userId);
		query.setString("mode", mode);
		query.setString("notificationStatus", notificationStatus);
		query.setString("orgCode", orgCode);
		query.setString("locationCode", locationCode);
		int result = query.executeUpdate();
		return result;			  
	}

	@Override
	public Integer updateNotificationDeleteStatus(Long notificationId, String orgCode, String locationCode) {
		// TODO Auto-generated method stub
		Session session =  this.sessionFactory.getCurrentSession();
		String status = Constants.NOTIFICATION_DELETED;
		Query query = session.createQuery("update Notifications nf set nf.notificationStatus =:status where nf.id = :notificationId and nf.orgCode = :orgCode and nf.locationCode = :locationCode ");
		query.setLong("notificationId", notificationId);
		query.setString("status", status);
		query.setString("orgCode", orgCode);
		query.setString("locationCode", locationCode);
		int result = query.executeUpdate();
		return result;	
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<Notifications> getAllNotificationsListByUserId(Long userId, String orgCode, String locationCode) {
		Session session =  this.sessionFactory.getCurrentSession();
		String notificationStatus = Constants.NOTIFICATION_NOT_DELETED;
		String query = "from Notifications nf where nf.notificationTo = :userId  and nf.orgCode = :orgCode and nf.locationCode = :locationCode and nf.notificationStatus =:notificationStatus ORDER BY nf.createdDate DESC";
		@SuppressWarnings("unchecked")
		List<Notifications> notificationList = (List<Notifications>)session.createQuery(query).setLong("userId", userId)
																	.setString("notificationStatus", notificationStatus)
				   													.setString("orgCode", orgCode)
				   													.setString("locationCode", locationCode).list();
		return notificationList;
	}

	@Override
	public Notifications getNotificationsById(Long Id) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		List<Notifications> notifications = new ArrayList<Notifications>();
		logger.debug("getNotificationsById start");
		notifications = session.createQuery("from Notifications where id=:id" ).setLong( "id", Id ).list();
		logger.debug("getNotificationsById end");
		if (notifications.size() > 0) {
			return notifications.get(0);
		} else {
			return null;
		}
   	}

	@Override
	public Notifications getNotificationsByUUID(String UUID) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		List<Notifications> notifications = new ArrayList<Notifications>();
		logger.debug("getNotificationsById start");
		notifications = session.createQuery("from Notifications ns where ns.uuId=:UUID and ns.userNotificationStatus <> 'Accepted' " ).setString( "UUID", UUID ).list();
		logger.debug("getNotificationsById end");
		if (notifications.size() > 0) {
			return notifications.get(0);
		} else {
			return null;
		}
   	}
	
	@Override
	public Notifications getNotificationBySpocUUID(String spocUUID) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		List<Notifications> notifications = new ArrayList<Notifications>();
		logger.debug("getNotificationBySpocUUID start");
		notifications = session.createQuery("from Notifications ns where ns.spocUUID=:spocUUID" ).setString( "spocUUID", spocUUID ).list();
		logger.debug("getNotificationBySpocUUID end");
		if (notifications.size() > 0) {
			return notifications.get(0);
		} else {
			return null;
		}
   	}

	@Override
	public Notifications getNotificationDetailsByUUID(String UUID) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		List<Notifications> notifications = new ArrayList<Notifications>();
		logger.debug("getNotificationsById start");
		notifications = session.createQuery("from Notifications ns where ns.uuId=:UUID " ).setString( "UUID", UUID ).list();
		logger.debug("getNotificationsById end");
		if (notifications.size() > 0) {
			return notifications.get(0);
		} else {
			return null;
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public ResponseList<Notifications> getDynamicAllNotificationsListByUserId(NotificationDTO notificationDTO, Long userId,
			String orgCode, String locationCode, Timezone timezone) {
		Session session =  this.sessionFactory.getCurrentSession();
		ResponseList<Notifications> response = new ResponseList<>();
		List<Long> countlist = null;
		Integer filterCount = 0;
		
		StringBuilder query1 = new StringBuilder("");
		if(notificationDTO.getSearchValue() !=null && !"".equalsIgnoreCase(notificationDTO.getSearchValue())) {
			query1.append("and ((nf.notificationFrom.firstName like '" + notificationDTO.getSearchValue() + "%' OR nf.notificationFrom.lastName like '%" + notificationDTO.getSearchValue() + "' )")
			   .append("OR (concat(nf.notificationFrom.firstName,nf.notificationFrom.lastName) like '%" + notificationDTO.getSearchValue().replaceAll("[\\s]+", "").trim() + "%'))" );
		}		
		String notificationStatus = Constants.NOTIFICATION_NOT_DELETED;
		String query = "from Notifications nf where nf.notificationTo = :userId  and nf.orgCode = :orgCode and nf.locationCode = :locationCode and nf.notificationStatus =:notificationStatus "+query1+" ORDER BY nf.createdDate DESC";
		String quertyCount = "Select count (*) from Notifications nf where nf.notificationTo = :userId  and nf.orgCode = :orgCode and nf.locationCode = :locationCode and nf.notificationStatus =:notificationStatus "+query1+"";
		
		List<Notifications> notificationList = (List<Notifications>)session.createQuery(query).setLong("userId", userId) .setString("notificationStatus", notificationStatus) .setString("orgCode", orgCode).setString("locationCode", locationCode).setFirstResult(notificationDTO.getStart()).setMaxResults(notificationDTO.getLength()).list();
		countlist = session.createQuery(quertyCount).setLong("userId", userId) .setString("notificationStatus", notificationStatus) .setString("orgCode", orgCode).setString("locationCode", locationCode).list();
		filterCount =  countlist.iterator().next().intValue();
		int totalCount = 0;
		if (notificationDTO != null) {
			if(countlist != null && !countlist.isEmpty()) {
				totalCount = countlist.iterator().next().intValue();
			}
		} 
		response.setRecordsTotal(totalCount);
		
		if(query1.toString() != null && !query1.toString().isEmpty()){
			response.setRecordsFiltered(filterCount);
		}else {
			response.setRecordsFiltered(totalCount);
		}
		
		response.setData(notificationList);
		return response;
	}

	@Override
	@SuppressWarnings("unchecked")
	@Transactional(rollbackFor = { Exception.class })
	public List<Notifications> getTopTenNotificationsListByUserId(Long userId, String orgCode, String locationCode) {
		Session session =  this.sessionFactory.getCurrentSession();
		String notificationStatus = Constants.NOTIFICATION_NOT_DELETED;
		String query = "from Notifications nf where nf.notificationTo = :userId  and nf.orgCode = :orgCode and nf.locationCode = :locationCode and nf.notificationStatus =:notificationStatus ORDER BY nf.createdDate DESC";
		@SuppressWarnings("unchecked")
		List<Notifications> notificationList = (List<Notifications>)session.createQuery(query).setLong("userId", userId)
																	.setString("notificationStatus", notificationStatus)
				   													.setString("orgCode", orgCode)
				   													.setString("locationCode", locationCode).setMaxResults(10).list();
			return notificationList;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public String getNotificationUUIDByOrgSpocId(Long orgSpocId) throws Exception {
		
		
		Session session = this.sessionFactory.getCurrentSession();
		String query = null;
		String uuid = null;
			 query ="SELECT "
						+" UUID "
						+" FROM"
						+" NOTIFICATIONS NT "
						+" WHERE"
						+" NT.SPOC_UUID IN (SELECT OS.UUID FROM ORG_SPOC OS WHERE OS.ID = :orgSpocId)";
			        
			
			   Query queryObj = session.createSQLQuery(query);
				queryObj.setLong("orgSpocId", orgSpocId);
				uuid = (String) queryObj.uniqueResult();
				return uuid;
	
	}
		
}
