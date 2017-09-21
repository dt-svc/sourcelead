package com.bis.sourcelead.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.RestrictDayDao;
import com.bis.sourcelead.entity.RestrictDay;
import com.bis.sourcelead.entity.Roles;

@Repository
public class RestrictDayDaoImpl implements RestrictDayDao{

	@Autowired
	private SessionFactory sessionFactory;
	
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
		String query = "from RestrictDay resDay  where resDay.orgLocationId = :orgLocationId ";
		List<RestrictDay> restrictDayList = session.createQuery(query).setLong("orgLocationId", orgLocationId).setCacheable(true).list();
		return restrictDayList;
	}
	
	
}
