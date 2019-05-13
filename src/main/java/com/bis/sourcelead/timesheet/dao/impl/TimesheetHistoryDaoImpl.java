package com.bis.sourcelead.timesheet.dao.impl;

import java.text.MessageFormat;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.entity.ProjectApproverHierarchy;
import com.bis.sourcelead.entity.TimesheetHistory;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;
import com.bis.sourcelead.exception.SourceLeadObjectNotFoundException;
import com.bis.sourcelead.timesheet.dao.TimesheetHistoryDao;

@Repository
public class TimesheetHistoryDaoImpl implements TimesheetHistoryDao {

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

	private static final Logger logger = LoggerFactory.getLogger(TimesheetHistoryDaoImpl.class);
	
	
	@Override
	@Transactional(rollbackFor = { Exception.class })	
	public TimesheetHistory saveTimesheetHistory(TimesheetHistory timesheetHistory)	throws Exception {
			logger.debug("In Projects Save method");
			Session session = this.sessionFactory.getCurrentSession();
			try{
				if(timesheetHistory != null){
					session.saveOrUpdate(timesheetHistory);
				}
			}catch(Exception exception){
				StringBuilder message= new StringBuilder(MessageFormat.format(duplicateValue,"Timesheet_History"));
				throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
			}
			
			return timesheetHistory;
	}
	/**
	 * This method is for updating the TimesheetHistory  by  timesheetHistory
	 */
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public TimesheetHistory updateTimesheetHistory(TimesheetHistory timesheetHistory) throws Exception {
		logger.debug("In TimesheetHistory Update method");
		Session session = this.sessionFactory.getCurrentSession();
		if(timesheetHistory != null){
			session.saveOrUpdate(timesheetHistory);
			logger.info("Record updated successfully..!");
		}
		if(timesheetHistory == null){
			StringBuilder message= new StringBuilder(MessageFormat.format(statusUpdate,"Timesheet_History"));
			throw new SourceLeadObjectNotFoundException(message.toString());
		}
		return timesheetHistory;
		
	}
	
	
	
	
	
}
