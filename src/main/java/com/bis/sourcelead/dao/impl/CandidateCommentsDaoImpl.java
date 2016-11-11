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

import com.bis.sourcelead.dao.CandidateCommentsDao;
import com.bis.sourcelead.entity.CandidateComments;
import com.bis.sourcelead.entity.CandidateResumes;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;

/**
 * @author Venkat Kesav
 *
 */
@Repository
public class CandidateCommentsDaoImpl implements CandidateCommentsDao {

	private static final Logger logger = LoggerFactory.getLogger(CandidateResumesDaoImpl.class);
	
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
	
	/**
	 * @author Venkat Kesav
	 * @since  nov 10 2016
	 * @version 0.0.1
	 * 
	 * This method is used to store Candidate Comments in candidate_Comments table.
	 *  
	 * 
	 * @param candidateComments This is the first parameter to storeCandidateComments method
	 * 
	 * @return CandidateComments
	 * 
	 * @throws NullPointer
	 */
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public CandidateComments storeCandidateComments(CandidateComments candidateComments) throws Exception {
		logger.debug("Storing Candidate");
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (candidateComments != null) {
				session.save(candidateComments);
			}
			logger.debug("Candidate Comments is Stored Successfully");
		} catch (Exception exception) {
			    StringBuilder message= new StringBuilder(MessageFormat.format(duplicateValue,"CandidateComments"));
				throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		return candidateComments;
	}
	
}
