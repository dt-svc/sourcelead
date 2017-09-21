package com.bis.sourcelead.dao.impl;

import java.util.List;

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

@Repository
public class HolidayDaoImpl  implements HolidayDAO{
	@Autowired
	private SessionFactory sessionFactory;
	
	private static final Logger logger = LoggerFactory.getLogger(HolidayDaoImpl.class);
	
	@Transactional(rollbackFor = { Exception.class })
	public Holiday addHoliday(Holiday holiday){

		logger.debug("Adding Holiday");
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (holiday != null) {
				session.saveOrUpdate(holiday);
			}
			logger.debug("OrgSpoc Added Successfully");
		} catch (Exception exception) {
		}
		return holiday;
	
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Holiday> getAllHolidaysList(Long id) {

		Session session = this.sessionFactory.getCurrentSession();
		String from = "from Holiday holiday where holiday.orgLocationId =:id  ";
		
		Query query = session.createQuery(from);
		return query.setCacheable(true).list();
	
	}

}
