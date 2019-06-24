	package com.bis.sourcelead.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
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
import com.bis.sourcelead.entity.HolidayAndRestrictday;
import com.bis.sourcelead.entity.HolidayShift;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.ProjectTasks;
import com.bis.sourcelead.entity.Shift;
import com.bis.sourcelead.util.CommonUtil;

@Repository
@Transactional
@SuppressWarnings("unchecked")
public class HolidayDaoImpl  implements HolidayDAO{
	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	private CommonUtil commonUtil;
	
	private static final Logger logger = LoggerFactory.getLogger(HolidayDaoImpl.class);
	
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
	public List<Holiday> getAllHolidaysList(Long id) {
		String status = "1";
		Session session = this.sessionFactory.getCurrentSession();
		String from = "from Holiday holiday where holiday.orgLocations.id =:id  and holiday.status =:status ORDER BY holiday.fromDate ASC";
		List<Holiday> holidayList = session.createQuery(from).setLong("id", id).setString("status", status).list();
		return holidayList;
	}
	 
	
	@Override
	public List<Holiday> getAllHolidaysListBasedOnYear(Long id,Long reportYear,String shiftIds) {
		String status = "1";
		Session session = this.sessionFactory.getCurrentSession();
		String subQuery = "";
		String from = null;
		List<Holiday> holidayList = null;
		if(StringUtils.isNotBlank(shiftIds)) {
			subQuery = " and holiday.id IN (select hs.holiday.id from HolidayShift hs where hs.shift.id IN("+shiftIds+"))";
		}
		if(shiftIds != null && shiftIds != "") {
			from = "from Holiday holiday where holiday.orgLocations.id =:id  and holiday.status =:status and (year(holiday.fromDate)='"+reportYear+"'  OR year(holiday.toDate)= '"+reportYear+"') "+subQuery+" ORDER BY holiday.fromDate ASC";
			holidayList = session.createQuery(from).setLong("id", id).setString("status", status).list();
		}else {
			from = "from Holiday holiday where holiday.orgLocations.id =:id  and holiday.status =:status and (year(holiday.fromDate)='"+reportYear+"'  OR year(holiday.toDate)= '"+reportYear+"') ORDER BY holiday.fromDate ASC";
			holidayList = session.createQuery(from).setLong("id", id).setString("status", status).list();
		}
         
		return holidayList;
	}
	
	@Override
	public Holiday getHolidaysById(String holidayId) throws Exception {
		Holiday holiday = null;
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from Holiday holiday where holiday.id= :holidayId";
		List<Holiday> holidayList = session.createQuery(query).setString("holidayId", holidayId).list();
		if (holidayList != null && holidayList.size() > 0) {
			holiday = holidayList.get(0);
		}
		return holiday;
	}
	
	@Override
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
	public List<Holiday> validateHoliDayDates(String fromDateStr, String toDateStr, OrgLocations orgLocations) throws Exception {
		Date fromDate = commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(), fromDateStr);
		Date toDate =  commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(), toDateStr);
		
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from Holiday hday  where hday.orgLocations.id = :orgLocationId and ((hday.fromDate <= :fromDate and hday.toDate >= :fromDate) or (hday.fromDate <= :toDate and hday.toDate >= :toDate))";
		List<Holiday> restrictDayList = session.createQuery(query).setParameter("fromDate", fromDate).setParameter("toDate", toDate).setLong("orgLocationId", orgLocations.getId()).list();
		return restrictDayList;
	}

	@Override
	public List<Holiday> getHolidaybyLocationIdAndFromdate(Date fromDate, Date endDate, Long orgLocationId) throws Exception
	{
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from Holiday hday where (hday.fromDate >= :fromDate and hday.fromDate <= :endDate) and hday.orgLocations.id = :orgLocationId and hday.status != '0'";
		List<Holiday> holiday = session.createQuery(query).setParameter("fromDate", fromDate).setParameter("endDate", endDate).setLong("orgLocationId", orgLocationId).list();
		if (holiday.size() > 0) {
			return holiday;
		}
		else {
			return null;
		}
	}

	@Override
	public Holiday getHolidaysByUUID(String holidayUUID)
	{
		Holiday holiday = null;
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from Holiday holiday where holiday.holidayUUID= :holidayUUID";
		List<Holiday> holidayList = session.createQuery(query).setString("holidayUUID", holidayUUID).list();
		if (holidayList != null && holidayList.size() > 0) {
			holiday = holidayList.get(0);
		}
		return holiday;
	}

	@Override
	public List<ProjectTasks> getHolidayProjectTaskByorgLocationIdAndHolidayId(Long orglocationId, Long holidayId) {
		Session session = this.sessionFactory.getCurrentSession();
		String query="from ProjectTasks proTask left join fetch proTask.holiday holiday left join fetch holiday.orgLocations orgLoc  where holiday.id = :holidayId and orgLoc.id = :orglocationId";
		List<ProjectTasks> projectTasks = session.createQuery(query).setLong("orglocationId", orglocationId).setLong("holidayId", holidayId).list();
		return projectTasks;
	}

	@Override
	public List<Holiday> getAllHoliday() {
		Session session = this.sessionFactory.getCurrentSession();
		String query="from Holiday holiday";
		List<Holiday> holidayList = session.createQuery(query).list();
		return holidayList;
	}

	@Override
	public List<Holiday> getAllHolidaysListByOrgloationIdAndShiftId(Long locationId, String shiftIds) {
		Session session = this.sessionFactory.getCurrentSession();
		String subQuery = "";
		if(StringUtils.isNotBlank(shiftIds)) {
			subQuery = " and holiday.id IN (select hs.holiday.id from HolidayShift hs where hs.shift.id IN("+shiftIds+"))";
		}
		String from = null;
		List<Holiday> holidayList  = null;
		if(shiftIds != null && shiftIds != "") {
			from = "from Holiday holiday where holiday.orgLocations.id =:locationId "+subQuery+" and holiday.status ='1' ORDER BY holiday.fromDate ASC";
			 holidayList = session.createQuery(from).setLong("locationId", locationId).list();
		}else {
			from = "from Holiday holiday where holiday.orgLocations.id =:locationId  and holiday.status ='1' ORDER BY holiday.fromDate ASC";
			 holidayList = session.createQuery(from).setLong("locationId", locationId).list();
		}
		 
		return holidayList;
	}

	@Override
	public List<Long> getHolidayShiftCout(Long holidayId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "select hs.shift.id from HolidayShift hs where hs.holiday.id = :holidayId ";
		List<Long> holidayShiftList =  session.createQuery(query.toString()).setLong("holidayId", holidayId).list();
		return holidayShiftList;
	}
	
	@Override
	public List<HolidayShift> getHolidayShiftListByHolidayId(Long holidayId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from HolidayShift hs where hs.holiday.id = :holidayId ";
		List<HolidayShift> holidayShiftList =  session.createQuery(query.toString()).setLong("holidayId", holidayId).list();
		return holidayShiftList;
	}

	@Override
	public void removeHolidayShiftList(List<Long> shiftIds,Long holidayId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from HolidayShift hs where hs.holiday.id =:holidayId and hs.shift.id IN(:shiftIds)";
		List<HolidayShift> holidayShiftList = session.createQuery(query).setLong("holidayId", holidayId).setParameterList("shiftIds", shiftIds).list();
       if(holidayShiftList != null && !holidayShiftList.isEmpty()) {
    	   for(HolidayShift pdp :holidayShiftList) {
    		   session.delete(pdp);
    	   }
       }
	}

	@Override
	public void deleteHolidayByHolidayId(Long holidayId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from HolidayShift hs where hs.holiday.id =:holidayId";
		List<HolidayShift> holidayShiftList = session.createQuery(query).setLong("holidayId", holidayId).list();
       if(holidayShiftList != null && !holidayShiftList.isEmpty()) {
    	   for(HolidayShift pdp :holidayShiftList) {
    		   session.delete(pdp);
    	   }
       }
	}

	@Override
	public List<HolidayAndRestrictday> getHolidayByFromToDate(Date fromDate, Date toDate,OrgLocations orgLocations)
	{
		Date from = commonUtil.orgLocationCommonDate(fromDate, orgLocations);
		Date to =  commonUtil.orgLocationCommonDate(toDate, orgLocations);
		java.sql.Date startDate = new java.sql.Date(fromDate.getTime());
		java.sql.Date endDate = new java.sql.Date(toDate.getTime());
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from HolidayAndRestrictday day where day.holiday.orgLocations.id = :orgLocationId and day.restrictDay.id IS NULL and ((day.day > :fromDate and day.day <:toDate) or (day.day = :fromDate or day.day = :toDate)) and day.status = '1'";
		List<HolidayAndRestrictday> list = session.createQuery(query).setParameter("fromDate", startDate).setParameter("toDate", endDate).setLong("orgLocationId", orgLocations.getId()).list();
		return list;
	}

	@Override
	public List<Holiday> getHolidaybyLocationIdAndFromdateForTimesheets(Date startDate, Date endDate, Long orgLocationId, Long userId, Long workLocationId, Long shiftId) throws Exception
	{
		Session session = this.sessionFactory.getCurrentSession();
		List<Holiday> shiftHolidayList = new ArrayList<>();
		try {
			String query = "from Holiday hday where ((hday.fromDate  between  :startDate and :endDate) or (hday.toDate  between  :startDate and :endDate) or (hday.fromDate = :startDate or hday.fromDate = :endDate)or (hday.toDate = :startDate or hday.toDate = :endDate)) and hday.id NOT IN(select udh.holiday.id from UserDroppedHolidays udh where udh.user.id = :userId and udh.orgLocations.id = :workLocationId and udh.toDate = hday.toDate and udh.fromDate = hday.fromDate and udh.status = '1') and hday.orgLocations.id = :orgLocationId and hday.status != '0'";
			List<Holiday> holidayList = session.createQuery(query).setParameter("startDate", startDate).setParameter("endDate", endDate).setLong("orgLocationId", orgLocationId).setLong("userId", userId).setLong("workLocationId", workLocationId).list();
			if (holidayList.size() > 0) {
				for (Holiday holiday : holidayList) {
					Set<HolidayShift> shiftsList = holiday.getHolidayShift();
					for (HolidayShift shift : shiftsList) {
						if (shift.getShift().getId().equals(shiftId)) {
							shiftHolidayList.add(holiday);
						}
					}
				}
				return shiftHolidayList;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
