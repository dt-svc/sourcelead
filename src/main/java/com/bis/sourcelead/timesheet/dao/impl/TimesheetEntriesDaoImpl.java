package com.bis.sourcelead.timesheet.dao.impl;

import java.text.MessageFormat;
import java.util.Collection;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.entity.TimesheetEntries;
import com.bis.sourcelead.entity.TimesheetHistory;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;
import com.bis.sourcelead.timesheet.dao.TimesheetEntriesDao;
import com.bis.sourcelead.timesheet.dao.TimesheetHistoryDao;
import com.bis.sourcelead.util.CommonUtil;

@Repository
public class TimesheetEntriesDaoImpl implements TimesheetEntriesDao {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	TimesheetHistoryDao timesheetHistoryDao;
	
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
	public TimesheetEntries addTimesheetEntries(TimesheetEntries timesheetEntries) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		try {
			session.saveOrUpdate(timesheetEntries);
			TimesheetHistory timesheetHistory = getHistoryRecordFromTimesheetEntries(timesheetEntries);
			if(timesheetHistory != null){
				timesheetHistoryDao.saveTimesheetHistory(timesheetHistory);
			}
		}
		catch(HibernateException exception)	{
			 StringBuilder message= new StringBuilder(MessageFormat.format(duplicateValue,"TIMESHEET_ENTRIES"));
				throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		return timesheetEntries;
		
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public TimesheetEntries updateTimesheetEntries(TimesheetEntries timesheetEntries) throws Exception {
		// TODO Auto-generated method stub
		Session session = this.sessionFactory.getCurrentSession();
	    session.saveOrUpdate(timesheetEntries);
	    TimesheetHistory timesheetHistory = getHistoryRecordFromTimesheetEntries(timesheetEntries);
		if(timesheetHistory != null){
			timesheetHistoryDao.saveTimesheetHistory(timesheetHistory);
		}
	    return timesheetEntries;
		
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public TimesheetEntries getTimesheetEntries(String id) throws Exception {
		// TODO Auto-generated method stub
		Session session = this.sessionFactory.getCurrentSession();
		return (TimesheetEntries) session.get(TimesheetEntries.class, Long.valueOf(id));
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Boolean deleteTimesheetEntries(String id) throws Exception {
		// TODO Auto-generated method stub
		
		Session session = this.sessionFactory.getCurrentSession();
		TimesheetEntries timesheetEntries = (TimesheetEntries)session.get(TimesheetEntries.class, Long.valueOf(id));
		timesheetEntries.setStatus("1");
        session.saveOrUpdate(timesheetEntries);
		logger.info("Successfully deleted !!!");
		return null;
		/*Query query = session.createQuery("delete Cities where id = :ID");
		query.setParameter("ID", new Long(18));
		 
		int result = query.executeUpdate();
		return null;*/
		
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<TimesheetEntries> getAllTimesheetEntries() throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		Criteria crit = session.createCriteria(TimesheetEntries.class);
		List<TimesheetEntries> adItemsList = crit.list(); 
		return adItemsList;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Collection<TimesheetEntries> getAllTimesheetEntriesBySubmissionId(Long submissionId) throws Exception {
		// TODO Auto-generated method stub
		
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from TimesheetEntries te left join fetch te.projectTask pt  where te.timesheetSubmissions.Id =:submissionId and (te.status = '0' or te.status = '' or te.status IS NULL)  ";
		List<TimesheetEntries> timesheetEntriesList = session.createQuery(query).setLong("submissionId", submissionId).list();	
		return timesheetEntriesList;
	}

	private TimesheetHistory  getHistoryRecordFromTimesheetEntries(TimesheetEntries timesheetEntries){
		TimesheetHistory timesheetHistory = null;
		if(timesheetEntries != null){
			timesheetHistory = new TimesheetHistory();
			timesheetHistory.setTimesheetEntries(timesheetEntries);
			timesheetHistory.setTimesheetSubmissions(timesheetEntries.getTimesheetSubmissions());
			timesheetHistory.setStartDate(timesheetEntries.getStartDate());
			timesheetHistory.setEndDate(timesheetEntries.getEndDate());
			timesheetHistory.setProjectTask(timesheetEntries.getProjectTask());
			timesheetHistory.setMonHours(timesheetEntries.getMonHours());
			timesheetHistory.setTueHours(timesheetEntries.getTueHours());
			timesheetHistory.setWedHours(timesheetEntries.getWedHours());
			timesheetHistory.setThuHours(timesheetEntries.getThuHours());
			timesheetHistory.setFriHours(timesheetEntries.getFriHours());
			timesheetHistory.setSatHours(timesheetEntries.getSatHours());
			timesheetHistory.setSunHours(timesheetEntries.getSunHours());
			
			timesheetHistory.setMoncomments(timesheetEntries.getMoncomments());
			timesheetHistory.setTuecomments(timesheetEntries.getTuecomments());
			timesheetHistory.setWedcomments(timesheetEntries.getWedcomments());
			timesheetHistory.setThucomments(timesheetEntries.getThucomments());
			timesheetHistory.setFricomments(timesheetEntries.getFricomments());
			timesheetHistory.setSatcomments(timesheetEntries.getSatcomments());
			timesheetHistory.setSuncomments(timesheetEntries.getSuncomments());
			
			timesheetHistory.setCreatedBy(timesheetEntries.getUpdatedBy());
			timesheetHistory.setCreatedDate(timesheetEntries.getCreatedDate());
			timesheetHistory.setUpdatedBy(timesheetEntries.getUpdatedBy());
			timesheetHistory.setUpdatedDate(timesheetEntries.getUpdatedDate());
		}
		
		
		return timesheetHistory;
	}
	

}
