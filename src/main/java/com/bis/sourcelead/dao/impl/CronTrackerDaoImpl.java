package com.bis.sourcelead.dao.impl;

import java.text.MessageFormat;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.CronTrackerDao;
import com.bis.sourcelead.entity.CronTracker;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;

/**
 * 
 * @author rajat rana
 *
 */

@Repository
@Transactional
public class CronTrackerDaoImpl implements CronTrackerDao{

	@Autowired
	private SessionFactory sessionFactory;
	
	
	
	private static final Logger logger = LoggerFactory.getLogger(CronTrackerDaoImpl.class);


	@Override
	public CronTracker saveCronTracker(CronTracker cronTracker) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (cronTracker != null) {
				session.saveOrUpdate(cronTracker);
			}
		} catch (Exception exception) {
			   exception.printStackTrace();
		}
		return cronTracker;
	}
	
	

}
