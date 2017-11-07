package com.bis.sourcelead.dao.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.HolidayDAO;
import com.bis.sourcelead.entity.Holiday;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.util.CommonUtil;

@Repository
public class HolidayDaoImpl  implements HolidayDAO{
	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	private CommonUtil commonUtil;
	
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
		String status = "1";
		Session session = this.sessionFactory.getCurrentSession();
		String from = "from Holiday holiday where holiday.orgLocations.id =:id  and holiday.status =:status";
		List<Holiday> holidayList = session.createQuery(from).setLong("id", id).setString("status", status).setCacheable(true).list();
		return holidayList;
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<Holiday> getAllHolidaysListBasedOnYear(Long id,Long reportYear) {
		String status = "1";
		Session session = this.sessionFactory.getCurrentSession();
		Date[] dates = commonUtil.getStartDateEndDateByYear(reportYear.intValue());
		java.sql.Date satrtDate = new java.sql.Date(dates[0].getTime());
		java.sql.Date endDate = new java.sql.Date(dates[1].getTime());
		String from = "from Holiday holiday where holiday.orgLocations.id =:id  and holiday.status =:status and holiday.fromDate >= '"+satrtDate+"' AND holiday.toDate <= '"+endDate+"'";
		List<Holiday> holidayList = session.createQuery(from).setLong("id", id).setString("status", status).setCacheable(true).list();
		return holidayList;
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Holiday getHolidaysById(String holidayId) throws Exception {
		Holiday holiday = null;
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from Holiday holiday where holiday.id= :holidayId";
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

	@Override
	@Transactional
	public List<Holiday> validateHoliDayDates(String fromDateStr, String toDateStr, OrgLocations orgLocations) throws Exception {
		Date fromDate = commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(), fromDateStr);
		Date toDate =  commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(), toDateStr);
		
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from Holiday hday  where hday.orgLocations.id = :orgLocationId and ((hday.fromDate <= :fromDate and hday.toDate >= :fromDate) or (hday.fromDate <= :toDate and hday.toDate >= :toDate))";
		List<Holiday> restrictDayList = session.createQuery(query).setParameter("fromDate", fromDate).setParameter("toDate", toDate).setLong("orgLocationId", orgLocations.getId()).setCacheable(true).list();
		return restrictDayList;
	}


}
