package com.bis.sourcelead.dao.impl;

import java.text.MessageFormat;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.TimesheetSubmissionsDocumentsDao;
import com.bis.sourcelead.entity.TimesheetSubmissionsDocuments;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;

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
	    List<TimesheetSubmissionsDocuments> timesheetSubmissionsDocumentsList = session.createQuery(query.toString()).setLong("submissionId", submissionId).setCacheable(true).list();
        return timesheetSubmissionsDocumentsList;
        
	}



	@Override
	@Transactional(rollbackFor = { Exception.class })
	public TimesheetSubmissionsDocuments getTimesheetSubmissionsDocuments(Long id) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		return (TimesheetSubmissionsDocuments) session.get(TimesheetSubmissionsDocuments.class, id);
		
		
	}
	
	


}
