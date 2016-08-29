package com.bis.sourcelead.dao.impl;

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

import com.bis.sourcelead.dao.CandidateResumesDao;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.CandidateResumes;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;

/**
 * @author Ajith Matta
 *
 */
@Repository
public class CandidateResumesDaoImpl implements CandidateResumesDao {

	
	private static final Logger logger = LoggerFactory.getLogger(CandidateSkillsDaoImpl.class);
	
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
	
	/* 
	 * This ADO method is used for save the candidate resumes 
	 */
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public CandidateResumes storeCandidateResumes(CandidateResumes candidateResumes) throws Exception {
		logger.debug("Storing Candidate");
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (candidateResumes != null) {
				session.save(candidateResumes);
			}
			logger.debug("Candidate Stored Successfully");
		} catch (Exception exception) {
			    StringBuilder message= new StringBuilder(MessageFormat.format(duplicateValue,"CandidateResumes"));
				throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		return candidateResumes;
	}
	
	
	/**
	 * to Candidates resumes List by candidateId
	 * @author Venkat Kesav
	 * @param candidateId
	 * @return candidateResumesList
	 * @throws NullPointerException
    */
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<CandidateResumes> getCandidateResumesListByCandidateId(Long candidateId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from CandidateResumes candidateResumes  where candidateResumes.candidateId =:candidateId";  
		List<CandidateResumes> candidateResumesList = session.createQuery(query).setLong("candidateId", candidateId).list();
		return candidateResumesList;
		
	}

}
