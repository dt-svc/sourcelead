package com.bis.sourcelead.dao.impl;

import java.text.MessageFormat;
import java.util.Date;
import java.util.List;

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
import com.bis.sourcelead.dao.TimeSheetDayEntriesHistoryDao;
import com.bis.sourcelead.entity.TimeSheetDayEntries;
import com.bis.sourcelead.entity.TimeSheetDayEntriesHistory;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;

@Repository
public class TimeSheetDayEntriesDaoImpl implements TimeSheetDayEntriesDao{

	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	private TimeSheetDayEntriesHistoryDao timeSheetDayEntriesHistoryDao;
	
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
	public TimeSheetDayEntries saveTimesheetDayEntries(TimeSheetDayEntries timeSheetDayEntries) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		try {
			session.saveOrUpdate(timeSheetDayEntries);
			TimeSheetDayEntriesHistory timesheetHistory = getHistoryRecordFromTimeSheetDayEntries(timeSheetDayEntries);
			if(timesheetHistory != null){
				timeSheetDayEntriesHistoryDao.saveTimeSheetDayEntriesHistory(timesheetHistory);
			}
		}
		catch(HibernateException exception)	{
			 StringBuilder message= new StringBuilder(MessageFormat.format(duplicateValue,"TimeSheetDayEntries"));
				throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		logger.info("saved timesheet day entries");
		return timeSheetDayEntries;
		
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public TimeSheetDayEntries getTimesheetDayEntriesById(Long id) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from TimeSheetDayEntries tde left join fetch tde.timesheetSubmissions left join fetch tde.projectTask   where tde.Id =:id ";
		List<TimeSheetDayEntries> timesheetEntriesList = session.createQuery(query).setLong("id", id).list();
		TimeSheetDayEntries tde = null;
		if(timesheetEntriesList !=null && !timesheetEntriesList.isEmpty()){
             tde = timesheetEntriesList.iterator().next();
		}
		return tde;
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<TimeSheetDayEntries> getAllTimeSheetDayEntriesBySubmissionId(Long submissionId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from TimeSheetDayEntries tde left join fetch tde.projectTask pt  where tde.timesheetSubmissions.Id =:submissionId ";
		List<TimeSheetDayEntries> timesheetEntriesList = session.createQuery(query).setLong("submissionId", submissionId).list();	
		return timesheetEntriesList;
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<Long> getAllTimeSheetDayEntryIdsBySubmissionId(Long submissionId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "Select tde.Id from TimeSheetDayEntries tde  where tde.timesheetSubmissions.Id =:submissionId ";
		List<Long> timesheetEntriesList = session.createQuery(query).setLong("submissionId", submissionId).list();	
		return timesheetEntriesList;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<TimeSheetDayEntries> getAllTimeSheetDayEntriesBySubmissionIdWithStartDateAndEndDate(
			Long submissionId, Date startDate, Date endDate) throws Exception {
		java.sql.Date sqlStartDate = null;
		if(startDate != null){
			sqlStartDate = new java.sql.Date(startDate.getTime());
		}
		
		java.sql.Date sqlEndDate = null;
		if(endDate != null){
			sqlEndDate = new java.sql.Date(endDate.getTime());
		}
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from TimeSheetDayEntries tde left join fetch tde.projectTask pt  where tde.timesheetSubmissions.Id =:submissionId and tde.timesheetDate between :startDate and :endDate ";
		List<TimeSheetDayEntries> timesheetEntriesList = session.createQuery(query).setLong("submissionId", submissionId).setParameter("startDate", sqlStartDate).setParameter("endDate", sqlEndDate).list();	
		return timesheetEntriesList;
	}
	
	private TimeSheetDayEntriesHistory  getHistoryRecordFromTimeSheetDayEntries(TimeSheetDayEntries timeSheetDayEntries){
		TimeSheetDayEntriesHistory timeSheetDayEntriesHistory = null;
		if(timeSheetDayEntries != null){
			timeSheetDayEntriesHistory = new TimeSheetDayEntriesHistory();
			timeSheetDayEntriesHistory.setTimeSheetDayEntries(timeSheetDayEntries);
			timeSheetDayEntriesHistory.setTimesheetSubmissions(timeSheetDayEntries.getTimesheetSubmissions());
			timeSheetDayEntriesHistory.setProjectTask(timeSheetDayEntries.getProjectTask());
			timeSheetDayEntriesHistory.setTimesheetDate(timeSheetDayEntries.getTimesheetDate());
			timeSheetDayEntriesHistory.setBillableHours(timeSheetDayEntries.getBillableHours());
			timeSheetDayEntriesHistory.setNonBillableHours(timeSheetDayEntries.getNonBillableHours());
			timeSheetDayEntriesHistory.setTotalHours(timeSheetDayEntries.getTotalHours());
			timeSheetDayEntriesHistory.setTaskType(timeSheetDayEntries.getTaskType());
			timeSheetDayEntriesHistory.setComments(timeSheetDayEntries.getComments());
			timeSheetDayEntriesHistory.setCreatedBy(timeSheetDayEntries.getUpdatedBy());
			timeSheetDayEntriesHistory.setCreatedDate(timeSheetDayEntries.getCreatedDate());
			timeSheetDayEntriesHistory.setUpdatedBy(timeSheetDayEntries.getUpdatedBy());
			timeSheetDayEntriesHistory.setUpdatedDate(timeSheetDayEntries.getUpdatedDate());
		}
		return timeSheetDayEntriesHistory;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Boolean deleteTimeSheetDayEntries(Long id) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		TimeSheetDayEntries tde = getTimesheetDayEntriesById(id);
		Boolean flag = false;
		try {
			timeSheetDayEntriesHistoryDao.deleteAllTimeSheetDayEntriesHistoryByEntryId(id);
			if(tde != null){
				session.delete(tde);
			    logger.debug("TIMESHEET_DAY_ENTRIES Record Deleted Successfully");
			    flag = true;
			}
			
		} catch (Exception exception) {
			 StringBuilder message= new StringBuilder(MessageFormat.format(duplicateValue,"TIMESHEET_DAY_ENTRIES"));
				throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		return flag;
	}

	

	

}
