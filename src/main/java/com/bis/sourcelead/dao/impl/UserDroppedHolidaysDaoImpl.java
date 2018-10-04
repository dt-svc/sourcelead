package com.bis.sourcelead.dao.impl;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.UserDroppedHolidaysDao;
import com.bis.sourcelead.entity.UserDroppedHolidays;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;

@Repository
public class UserDroppedHolidaysDaoImpl implements UserDroppedHolidaysDao {

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
	

	private static final Logger logger = LoggerFactory.getLogger(UserDroppedHolidaysDaoImpl.class);
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public UserDroppedHolidays addUserDroppedHolidays(UserDroppedHolidays userDroppedHolidays) throws Exception {
		logger.debug("Adding UserDroppedHolidays");
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (userDroppedHolidays != null) {
					session.saveOrUpdate(userDroppedHolidays);
			}
			logger.debug("UserDroppedHolidays Added Successfully");
		} catch (Exception exception) {
			 StringBuilder message= new StringBuilder(MessageFormat.format(duplicateValue,"USER_DROPPED_HOLIDAY"));
				throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		return userDroppedHolidays;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<Long> getDroppedHolidayIdsListByUserIdAndLocationId(Long userId, Long locationId) throws Exception {
		  Session session = this.sessionFactory.getCurrentSession();
		  String actionsQuery = "select udh.holiday.id from UserDroppedHolidays udh where udh.user.id = :userId and udh.orgLocations.id = :locationId and udh.status = '1'";
	       @SuppressWarnings("unchecked")
		   List<Long> droppedHolidayIds = session.createQuery(actionsQuery.toString()).setLong("userId", userId).setLong("locationId", locationId).list();
	       if(droppedHolidayIds == null || droppedHolidayIds.isEmpty()) {
	    	   droppedHolidayIds = new ArrayList<Long>(); 
	       }
	       return droppedHolidayIds;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public UserDroppedHolidays getUserDroppedHolidaysFromUserIdlocationIdAndHolidayId(Long userId, Long locationId,
			Long holidayId) throws Exception {
		   Session session = this.sessionFactory.getCurrentSession();
		   String actionsQuery = "from UserDroppedHolidays udh where udh.user.id = :userId and udh.orgLocations.id = :locationId and udh.holiday.id = :holidayId";
	       @SuppressWarnings("unchecked")
		   List<UserDroppedHolidays> userDropHolidaysList = session.createQuery(actionsQuery.toString()).setLong("userId", userId).setLong("holidayId", holidayId).setLong("locationId", locationId).list();
	       UserDroppedHolidays udh = null;
	       if(userDropHolidaysList != null && !userDropHolidaysList.isEmpty()) {
	    	   udh = userDropHolidaysList.iterator().next(); 
	       }
	       return udh;
	}


}