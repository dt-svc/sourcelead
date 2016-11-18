package com.bis.sourcelead.dao.impl;

import java.text.MessageFormat;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.CandidateResumesDao;
import com.bis.sourcelead.entity.CandidateResumes;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;

/**
 * @author Venkat Kesav
 *
 */
@Repository
public class CandidateResumesDaoImpl implements CandidateResumesDao {

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
	 * @since  aug 30 2016
	 * @version 0.0.1
	 * 
	 * This method is used to store Candidate Resume in candidate_Resumes table.
	 *  
	 * 
	 * @param candidateResumes This is the first parameter to storeCandidateResumes method
	 * 
	 * @return CandidateResumes
	 * 
	 * @throws NullPointer
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
	 * @author Venkat Kesav
	 * @since  aug 30 2016
	 * @version 0.0.1
	 * 
	 * This method is used to store Candidate Resume in candidate_Resumes table.
	 *  
	 * 
	 * @param candidateId This is the first parameter to getCandidateResumesListByCandidateIdAndStatus method
	 * @param status This is the first parameter to getCandidateResumesListByCandidateIdAndStatus method
	 * 
	 * @return candidateResumesList
	 * 
	 * @throws NullPointerException
	 */
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<CandidateResumes> getCandidateResumesListByCandidateIdAndStatus(Long candidateId, Integer status) throws Exception {
		logger.debug("In get Resumes list by candidateid and status method");
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from CandidateResumes candidateResumes where candidateResumes.candidateId =:candidateId and candidateResumes.status = :status";  
		List<CandidateResumes> candidateResumesList = session.createQuery(query).setLong("candidateId", candidateId).setInteger("status", status).setCacheable(true).list();
		return candidateResumesList;
		
	}

	/**
	 * @author Venkat Kesav
	 * @since  aug 30 2016
	 * @version 0.0.1
	 * 
	 * This method is used to update Candidate Resume in candidate_Resumes table.
	 *  
	 * 
	 * @param candidateResumes This is the first parameter to updateCandidateResume method
	 * 
	 * @return candidateResumes
	 * 
	 * @throws NullPointerException
	 */
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public CandidateResumes updateCandidateResume(CandidateResumes candidateResumes) throws Exception {
		logger.debug("In Terminate Resume Update method");
		Session session = this.sessionFactory.getCurrentSession();
		try{
			if(candidateResumes != null){
				session.saveOrUpdate(candidateResumes);
				logger.info("Record updated successfully..!");
			}
		}catch(ConstraintViolationException exception)
		{
			StringBuilder message= new StringBuilder(MessageFormat.format(duplicateValue,"CANDIDATE_RESUMES"));
			throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		return candidateResumes;
	}

	
	/**
	 * @author Venkat Kesav
	 * @since  aug 30 2016
	 * @version 0.0.1
	 * 
	 * This method is used to get Candidate Resume By ResumeId from candidate_Resumes table.
	 *  
	 * 
	 * @param resumeId This is the first parameter to getCandidateResumeByResumeId method
	 * 
	 * @return candidateResumes
	 * 
	 * @throws NullPointerException
	 */
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public CandidateResumes getCandidateResumeByResumeId(Long resumeId) throws Exception {
		logger.debug("In Candidate Resume By ResumeId");
		Session session = this.sessionFactory.getCurrentSession();
		String query = " from CandidateResumes candidateResumes where candidateResumes.id = :resumeId" ;
		List<CandidateResumes> candidateResumesList =session.createQuery(query).setLong("resumeId", resumeId).setCacheable(true).list(); 
		CandidateResumes candidateResumes = null;
		if(candidateResumesList != null && !candidateResumesList.isEmpty()){
			candidateResumes = candidateResumesList.iterator().next();
		}
		return candidateResumes;
	}

/**
 * To Get Latest Resumes
 */
		@Override
		@Transactional(rollbackFor = { Exception.class })
		public CandidateResumes getCandidateLatesResumes(Long candidateId, int status) throws Exception {
			Session session = this.sessionFactory.getCurrentSession();
			String query = "from CandidateResumes candidateResumes where candidateResumes.createdDate IN (SELECT max(canresume.createdDate) FROM CandidateResumes canresume where canresume.candidateId.id =:candidateId) and candidateResumes.status = :status";  
			List<CandidateResumes> candidateResumesList = session.createQuery(query).setLong("candidateId", candidateId).setInteger("status", status).setCacheable(true).list();
			CandidateResumes candidateResumes = null;
			if(candidateResumesList != null && !candidateResumesList.isEmpty()){
				candidateResumes = candidateResumesList.iterator().next();
			}
			return candidateResumes;
}

}
