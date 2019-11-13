package com.bis.sourcelead.timesheet.dao.impl;

import java.text.MessageFormat;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.entity.TimesheetSubmissionsDocuments;
import com.bis.sourcelead.entity.TimesheetSubmissionsDocumentsHistory;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;
import com.bis.sourcelead.timesheet.dao.TimesheetSubmissionsDocumentsDao;

@Repository
public class TimesheetSubmissionsDocumentsDaoImpl implements TimesheetSubmissionsDocumentsDao {

	
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

	
	@Autowired
	private SessionFactory sessionFactory;
	
	
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public TimesheetSubmissionsDocuments addTimesheetSubmissionsDocuments(TimesheetSubmissionsDocuments timesheetSubmissionsDocuments) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		try {
			session.saveOrUpdate(timesheetSubmissionsDocuments);
		}
		catch(Exception exception)	{
			StringBuilder message= new StringBuilder(MessageFormat.format(duplicateValue,"TIMESHEET_SUBMISSIONS"));
			throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		return timesheetSubmissionsDocuments;
		
	}



	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<TimesheetSubmissionsDocuments> getAllTimesheetSubmissionsDocumentsBySubmissionId(Long submissionId)
			throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
	    String query = "from TimesheetSubmissionsDocuments tsd where tsd.timesheetSubmissions.Id =:submissionId and tsd.status = '1') ";
	    List<TimesheetSubmissionsDocuments> timesheetSubmissionsDocumentsList = session.createQuery(query.toString()).setLong("submissionId", submissionId).list();
        return timesheetSubmissionsDocumentsList;
        
	}



	@Override
	@Transactional(rollbackFor = { Exception.class })
	public TimesheetSubmissionsDocuments getTimesheetSubmissionsDocuments(Long id) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		return (TimesheetSubmissionsDocuments) session.get(TimesheetSubmissionsDocuments.class, id);
		
		
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public TimesheetSubmissionsDocumentsHistory addTimesheetSubmissionsDocumentsHistory(TimesheetSubmissionsDocumentsHistory timesheetSubmissionsDocuments) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		try {
			session.saveOrUpdate(timesheetSubmissionsDocuments);
		}
		catch(Exception exception)	{
			StringBuilder message= new StringBuilder(MessageFormat.format(duplicateValue,"TIMESHEET_SUBMISSIONS"));
			throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		return timesheetSubmissionsDocuments;
		
	}



	@SuppressWarnings("unchecked")
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<TimesheetSubmissionsDocumentsHistory> getAllTimesheetSubmissionsDocumentsHistoryBySubmissionId(Long submissionId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
	    String query = "from TimesheetSubmissionsDocuments tsd where tsd.timesheetSubmissions.Id =:submissionId ";
	    List<TimesheetSubmissionsDocumentsHistory> timesheetSubmissionsDocumentsHistoryList = session.createQuery(query.toString()).setLong("submissionId", submissionId).list();
        return timesheetSubmissionsDocumentsHistoryList;
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<TimesheetSubmissionsDocuments> getAllTimesheetSubmissionsDocumentsByProjectIdMasterCandidateIdAndFrequencyStartDateEndDate(
			Long masterCandidateId, Long projectId, Date freqStartDate, Date freqEndDate) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		java.sql.Date sqlStartDate = null;
		if(freqStartDate != null){
			sqlStartDate = new java.sql.Date(freqStartDate.getTime());
		}
		
		java.sql.Date sqlEndDate = null;
		if(freqEndDate != null){
			sqlEndDate = new java.sql.Date(freqEndDate.getTime());
		}
	    String query = "from TimesheetSubmissionsDocuments tsd "
	    		+ "     left join fetch tsd.timesheetSubmissions ts "
	    		+ "   where ts.masterCandidateId.id  = :masterCandidateId "
	    		+ "   and  ts.projects.id = :projectId "
	    		+ " and ts.startDate BETWEEN '"+sqlStartDate+"' and '"+sqlEndDate+"' and tsd.status = '1'";
	    List<TimesheetSubmissionsDocuments> tsDocList = session.createQuery(query.toString()).setLong("masterCandidateId", masterCandidateId).setLong("projectId", projectId).list();
        return tsDocList;
	}



	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<TimesheetSubmissionsDocumentsHistory> getAllTimesheetSubmissionsDocumentsHistoryByTimesheetActivityId(Long activityId) {
		Session session = this.sessionFactory.getCurrentSession();
	    String query = "from TimesheetSubmissionsDocumentsHistory tsdHistory where tsdHistory.timesheetActivity =:activityId ";
	    List<TimesheetSubmissionsDocumentsHistory> timesheetSubmissionsDocumentsHistory = session.createQuery(query.toString()).setLong("activityId", activityId).list();
        return timesheetSubmissionsDocumentsHistory;
	}



	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<Long> getAllTimesheetSubmissionsDocumentIdsBySubmissionId(Long submissionId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
	    String query = "Select tsd.Id from TimesheetSubmissionsDocuments tsd where tsd.timesheetSubmissions.Id =:submissionId and tsd.status = '1') ";
	    List<Long> timesheetSubmissionsDocumentsHistoryList = session.createQuery(query.toString()).setLong("submissionId", submissionId).list();
        return timesheetSubmissionsDocumentsHistoryList;
	}



	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<Long> getAllTimesheetSubmissionsDocumentIdsByProjectIdMasterCandidateIdAndFrequencyStartDateEndDate(
			Long masterCandidateId, Long projectId, Date freqStartDate, Date freqEndDate) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		java.sql.Date sqlStartDate = null;
		if(freqStartDate != null){
			sqlStartDate = new java.sql.Date(freqStartDate.getTime());
		}
		
		java.sql.Date sqlEndDate = null;
		if(freqEndDate != null){
			sqlEndDate = new java.sql.Date(freqEndDate.getTime());
		}
	    String query = "Select tsd.Id  from TimesheetSubmissionsDocuments tsd "
	    		+ "   where tsd.timesheetSubmissions.masterCandidateId.id  = :masterCandidateId "
	    		+ "   and  tsd.timesheetSubmissions.projects.id = :projectId "
	    		+ " and tsd.timesheetSubmissions.startDate BETWEEN '"+sqlStartDate+"' and '"+sqlEndDate+"' and tsd.status = '1'";
	    List<Long> timesheetSubmissionsDocumentsHistoryList = session.createQuery(query.toString()).setLong("masterCandidateId", masterCandidateId).setLong("projectId", projectId).list();
        return timesheetSubmissionsDocumentsHistoryList;
	}



	


}
