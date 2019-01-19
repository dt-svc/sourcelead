package com.bis.sourcelead.dao.impl;

import java.text.MessageFormat;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.JobShareDao;
import com.bis.sourcelead.entity.JobShareCandidate;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;

@Repository
public class JobShareDaoImpl implements JobShareDao{
	
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
	
	private static final Logger logger = LoggerFactory.getLogger(JobShareDaoImpl.class);


	/*@Override
	@Transactional
	public JobShare saveJobShare(JobShare jobShare) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (jobShare != null) {
				session.saveOrUpdate(jobShare);
			}
			logger.debug("Saving JobShare");
		} catch (Exception exception) {
			 StringBuilder message= new StringBuilder(MessageFormat.format(duplicateValue,"JobShare"));
				throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		return jobShare;
	}
*/

	@Override
	@Transactional
	public JobShareCandidate saveJobShareCandidate(JobShareCandidate jobShareCandidate) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (jobShareCandidate != null) {
				session.saveOrUpdate(jobShareCandidate);
			}
			logger.debug("Saving JobShareCandidate");
		} catch (Exception exception) {
			 StringBuilder message= new StringBuilder(MessageFormat.format(duplicateValue,"JobShareCandidate"));
				throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		return jobShareCandidate;
	}

	 

}
