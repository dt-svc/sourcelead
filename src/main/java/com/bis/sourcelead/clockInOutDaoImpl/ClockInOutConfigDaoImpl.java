package com.bis.sourcelead.clockInOutDaoImpl;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.clockInOutDao.ClockInOutConfigDao;
import com.bis.sourcelead.clockInOutEntity.ClockInOut;
import com.bis.sourcelead.clockInOutEntity.ClockInOutConfig;

@Repository
public class ClockInOutConfigDaoImpl implements ClockInOutConfigDao{
	
	private static final Logger logger = LoggerFactory.getLogger(ClockInOutConfigDaoImpl.class);

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	@Transactional
	public ClockInOutConfig saveClockInOutConfiguration(ClockInOutConfig clockInOut) {
		
		Session session = this.sessionFactory.getCurrentSession();
		
		try {
			if (clockInOut != null) {
				session.saveOrUpdate(clockInOut);
			}
		} catch (Exception exception) {
			logger.error(ExceptionUtils.getFullStackTrace(exception));
		}
		return clockInOut;
		
	}

	@Override
	@Transactional
	public ClockInOutConfig getClockInOutConfigBasedOnOrgLocationId(Long orgLocationId) {
		
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from ClockInOutConfig clockInOut where clockInOut.orgLocationId.id = :orgLocationId";
		ClockInOutConfig clockInOut = (ClockInOutConfig) session.createQuery(query).setLong("orgLocationId", orgLocationId).uniqueResult();
		if(clockInOut != null) {
			return clockInOut;
		}
		return null;
	}
	
	@Override
	@Transactional
	public ClockInOutConfig getClockInOutConfigBasedOnId(Long clockInOutId) {
		
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from ClockInOutConfig clockInOut where clockInOut.id = :clockInOutId";
		ClockInOutConfig clockInOut = (ClockInOutConfig) session.createQuery(query).setLong("clockInOutId", clockInOutId).uniqueResult();
		if(clockInOut != null) {
			return clockInOut;
		}
		return null;
	}

	@Override
	@Transactional
	public void saveClockInOutTime(ClockInOut clockInOut) {
		
		Session session = this.sessionFactory.getCurrentSession();
		
		try {
			if (clockInOut != null) {
				session.saveOrUpdate(clockInOut);
			}
		} catch (Exception exception) {
			logger.error(ExceptionUtils.getFullStackTrace(exception));
		}
	}

}
