package com.bis.sourcelead.dao.impl;

import java.util.List;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.HolidayDAO;
import com.bis.sourcelead.entity.Holiday;
import com.bis.sourcelead.entity.Option;

@Repository
public class HolidayDaoImpl  implements HolidayDAO{
	@Autowired
	private SessionFactory sessionFactory;
	
	private static final Logger logger = LoggerFactory.getLogger(HolidayDaoImpl.class);
	
	@Transactional(rollbackFor = { Exception.class })
	public Holiday storeHoliday(Holiday holiday){

		logger.debug("Adding Holiday");
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (holiday != null) {
				session.saveOrUpdate(holiday);
			}
			logger.debug("Holiday Added Successfully");
		} catch (Exception exception) {
		}
		return holiday;
	
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<Holiday> getAllHolidaysList(Long id) {
		Session session = this.sessionFactory.getCurrentSession();
		String from = "from Holiday holiday where holiday.orgLocations.id =:id  ";
		List<Holiday> holidayList = session.createQuery(from).setLong("id", id).setCacheable(true).list();
		return holidayList;
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Holiday getHolidaysById(String holidayId) throws Exception {
		Holiday holiday = null;
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from Option option where option.id= :optionId";
		List<Holiday> holidayList = session.createQuery(query).setString("holidayId", holidayId).setCacheable(true).list();
		if (holidayList != null && holidayList.size() > 0) {
			holiday = holidayList.get(0);
		}
		return holiday;
	}
	
	@Override
	@Transactional
	public Holiday saveDeleteHoliday(Holiday holidays) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (holidays != null) {
				session.saveOrUpdate(holidays);
			}
		} catch (Exception exception) {
			logger.error(ExceptionUtils.getFullStackTrace(exception));
		}
		return holidays;
	}


}
