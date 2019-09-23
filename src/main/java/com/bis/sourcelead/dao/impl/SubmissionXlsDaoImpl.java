package com.bis.sourcelead.dao.impl;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.SubmissionXlsDao;
import com.bis.sourcelead.entity.SubmissionXls;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;

@Repository
public class SubmissionXlsDaoImpl implements SubmissionXlsDao {
	
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
	
	
	private static final Logger logger = LoggerFactory.getLogger(SubmissionXlsDaoImpl.class);

	@Override
	@Transactional
	public SubmissionXls storeSubmissionXls(SubmissionXls submissionXls) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		logger.debug("SubmissionXls submissionXls Added Successfully");
		try {
			if (submissionXls != null) {
				session.saveOrUpdate(submissionXls);
			}
			logger.debug("SubmissionXls submissionXls Added Successfully");
		} catch (Exception exception) {
			 StringBuilder message= new StringBuilder(MessageFormat.format(duplicateValue,"SubmissionXls"));
				throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		return submissionXls;
	}

	@Override
	@Transactional
	public SubmissionXls getSubmissionXlsByUUID(String UUID) {
		Session session = this.sessionFactory.getCurrentSession();
		List<SubmissionXls> submissionXls = new ArrayList<SubmissionXls>();
		logger.debug("getSubmissionXlsByUUID  start");
		submissionXls = session.createQuery("from SubmissionXls xls where xls.uuid = :uuid" ).setString( "uuid", UUID ).list();
		logger.debug("getSubmissionXlsByUUID end");
		if (submissionXls.size() > 0) {
			return submissionXls.get(0);
		} else {
			return null;
		}
}

}
