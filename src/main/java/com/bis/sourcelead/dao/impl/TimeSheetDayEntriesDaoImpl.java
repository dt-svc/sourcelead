package com.bis.sourcelead.dao.impl;

import java.text.MessageFormat;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.TimeSheetDayEntriesDao;
import com.bis.sourcelead.entity.TimeSheetDayEntries;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;

@Repository
public class TimeSheetDayEntriesDaoImpl implements TimeSheetDayEntriesDao{

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

	private static final Logger logger = LoggerFactory.getLogger(TimesheetEntriesDaoImpl.class);
	
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public TimeSheetDayEntries saveTimesheetDayEntries(TimeSheetDayEntries dayentries) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		try {
			session.saveOrUpdate(dayentries);
		}
		catch(HibernateException exception)	{
			 StringBuilder message= new StringBuilder(MessageFormat.format(duplicateValue,"TimeSheetDayEntries"));
				throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		logger.info("saved timesheet day entries");
		return dayentries;
		
	}
	
	
	
	

}
