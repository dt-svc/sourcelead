package com.bis.sourcelead.dao.impl;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.RestrictDayDao;
import com.bis.sourcelead.entity.LeaveType;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.RestrictDay;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.ConversionUtil;

@Repository
public class RestrictDayDaoImpl implements RestrictDayDao{

	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	private ConversionUtil conversionUtil;
	
	@Autowired
	CommonUtil commonUtil;
	
	private static final Logger logger = LoggerFactory.getLogger(UserDaoImpl.class);
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public RestrictDay storeRestrictedDay(RestrictDay restrictDay) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (restrictDay != null) {
				session.saveOrUpdate(restrictDay);
			}
		} catch (Exception exception) {
			logger.error(ExceptionUtils.getFullStackTrace(exception));
		}
		return restrictDay;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<RestrictDay> getRestrictDayListByOrgLocationId(Long orgLocationId) {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from RestrictDay resDay  where resDay.orgLocationId = :orgLocationId and resDay.status = '1' ";
		List<RestrictDay> restrictDayList = session.createQuery(query).setLong("orgLocationId", orgLocationId).list();
		return restrictDayList;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public RestrictDay getRestrictDayById(Long restrictDayId) {
		List<RestrictDay> restrictDayList = null;
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from RestrictDay resDay  where resDay.id = :restrictDayId";
		restrictDayList = session.createQuery(query).setLong("restrictDayId", restrictDayId).list();
		if(restrictDayList != null) {
			return restrictDayList.get(0);
		}else {
			return null;
		}
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<RestrictDay> validateRestrictDayDates(String fromDateStr, String toDateStr,OrgLocations orgLocations) throws Exception {
		
		
		Date fromDate = commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(), fromDateStr);
		Date toDate =  commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(), toDateStr);
		
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from RestrictDay resDay  where resDay.orgLocationId = :orgLocationId  and resDay.status = '1' and ((resDay.startDate <= :fromDate and resDay.endDate >= :fromDate) or (resDay.startDate <= :toDate and resDay.endDate >= :toDate ))";
		List<RestrictDay> restrictDayList = session.createQuery(query).setParameter("fromDate", fromDate).setParameter("toDate", toDate).setLong("orgLocationId", orgLocations.getId()).list();
		return restrictDayList;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public RestrictDay getRestrictDayByUUID(String restrictUUID)
	{
		List<RestrictDay> restrictDayList = null;
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from RestrictDay resDay  where resDay.restrictUUID = :restrictUUID";
		restrictDayList = session.createQuery(query).setString("restrictUUID", restrictUUID).list();
		if(restrictDayList != null) {
			return restrictDayList.get(0);
		}else {
			return null;
		}
	}

}
