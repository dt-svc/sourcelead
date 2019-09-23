package com.bis.sourcelead.timesheet.dao.impl;

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

import com.bis.sourcelead.entity.TimeSheetDayEntries;
import com.bis.sourcelead.entity.TimeSheetDayEntriesHistory;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;
import com.bis.sourcelead.exception.SourceLeadObjectNotFoundException;
import com.bis.sourcelead.timesheet.dao.TimeSheetDayEntriesHistoryDao;

@Repository
public class TimeSheetDayEntriesHistoryDaoImpl implements TimeSheetDayEntriesHistoryDao {

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

	private static final Logger logger = LoggerFactory.getLogger(TimeSheetDayEntriesHistoryDaoImpl.class);
	
	
	@Override
	@Transactional(rollbackFor = { Exception.class })	
	public TimeSheetDayEntriesHistory saveTimeSheetDayEntriesHistory(TimeSheetDayEntriesHistory timesheetDayEntriesHistory)	throws Exception {
			logger.debug("In TimeSheetDayEntriesHistory Save method");
			Session session = this.sessionFactory.getCurrentSession();
			try{
				if(timesheetDayEntriesHistory != null){
					session.saveOrUpdate(timesheetDayEntriesHistory);
				}
			}catch(Exception exception){
				StringBuilder message= new StringBuilder(MessageFormat.format(duplicateValue,"TimeSheetDayEntriesHistory"));
				throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
			}
			
			return timesheetDayEntriesHistory;
	}
	/**
	 * This method is for updating the TimeSheetDayEntriesHistory  by  timeSheetDayEntriesHistory
	 */
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public TimeSheetDayEntriesHistory updateTimeSheetDayEntriesHistory(TimeSheetDayEntriesHistory timesheetDayEntriesHistory) throws Exception {
		logger.debug("In TimeSheetDayEntriesHistory Update method");
		Session session = this.sessionFactory.getCurrentSession();
		if(timesheetDayEntriesHistory != null){
			session.saveOrUpdate(timesheetDayEntriesHistory);
			logger.info("Record updated successfully..!");
		}
		if(timesheetDayEntriesHistory == null){
			StringBuilder message= new StringBuilder(MessageFormat.format(statusUpdate,"TimeSheetDayEntriesHistory"));
			throw new SourceLeadObjectNotFoundException(message.toString());
		}
		return timesheetDayEntriesHistory;
		
	}
	
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Boolean deleteAllTimeSheetDayEntriesHistoryByEntryId(Long timesheetDayEntriesId) throws Exception {
		logger.debug("In TimeSheetDayEntriesHistory delete method");
		Boolean flag = false;
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from TimeSheetDayEntriesHistory tdeh where tdeh.timeSheetDayEntries.Id =:timesheetDayEntriesId ";
		List<TimeSheetDayEntriesHistory> timesheetEntriesList = session.createQuery(query).setLong("timesheetDayEntriesId", timesheetDayEntriesId).list();	
		if(timesheetEntriesList != null && !timesheetEntriesList.isEmpty()){
			for(TimeSheetDayEntriesHistory tdeh : timesheetEntriesList){
				try {
					session.delete(tdeh);
				    logger.debug("TIMESHEET_DAY_ENTRIES_HISTORY Record Deleted Successfully");
				} catch (Exception exception) {
					 StringBuilder message= new StringBuilder(MessageFormat.format(duplicateValue,"TIMESHEET_DAY_ENTRIES_HISTORY"));
						throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
				}
			}
			flag = true;
		}
		return flag;
	}
	
	
}
