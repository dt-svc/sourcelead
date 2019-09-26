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

import com.bis.sourcelead.dao.CandidateTimelineActivityDao;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.CandidateTimelineActivity;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;

/**
 * 
 * @author Venkat Kesav
 * @date 17th July 2017
 *
 */
@Repository
public class CandidateTimelineActivityDaoImpl implements CandidateTimelineActivityDao {
	private static final Logger logger = LoggerFactory.getLogger(CandidateTimelineActivityDaoImpl.class);
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
	
	protected boolean isNullOrEmpty(String str)
	{
		return (str == null || "".equals(str.trim()));
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<CandidateTimelineActivity> getCandidateTimelineActivities(Long candidateId, Long orgLocationId) throws Exception
	{
		logger.debug("Fetching Candidate Activities");
		Session session = this.sessionFactory.getCurrentSession();
		List<CandidateTimelineActivity> candidateTimelineActivityList = null;
		if (candidateId != null) {
			String query = "from CandidateTimelineActivity cta left join fetch cta.createdUser user left join fetch cta.candidate candidate left join fetch cta.orgLocationId orglocation where cta.orgLocationId.id =:orgLocationId and cta.candidate.id =:candidateId ORDER BY cta.createdDate DESC";
			candidateTimelineActivityList = session.createQuery(query).setLong("orgLocationId", orgLocationId).setLong("candidateId", candidateId).list();
		}
		logger.debug("Successfully Fetched Candidate Activities");
		return candidateTimelineActivityList;
	}
	
	
	/**
	 * @author Venkat Kesav
	 * @since  july 18 2017
	 * @version 0.0.1
	 * 
	 * This method is used to store/save candidateActivities in CandidateTimelineActivity table.
	 *  
	 * @param candidateTimelineActivity This is the first paramter to storeCandidate method
	 * 
	 * @return CandidateTimelineActivity 
	 * 
	 * @throws NullPointer
	 */
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public CandidateTimelineActivity storeCandidateTimelineActivity(CandidateTimelineActivity candidateTimelineActivity) throws Exception {
		logger.debug("Storing CandidateTimelineActivity");
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (candidateTimelineActivity != null) {
				session.saveOrUpdate(candidateTimelineActivity);
			}
			logger.debug("CandidateTimelineActivity Stored Successfully");
		} catch (Exception exception) {
			    StringBuilder message= new StringBuilder(MessageFormat.format(duplicateValue,"CandidateTimelineActivity"));
				throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		return candidateTimelineActivity;
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<CandidateTimelineActivity> getCandidateGlobalTimelineActivities(Long candidateId, Long orgLocationId) throws Exception
	{
		logger.debug("Fetching Candidate Activities");
		Session session = this.sessionFactory.getCurrentSession();
		List<CandidateTimelineActivity> candidateTimelineActivityList = null;
		if (candidateId != null) {
			String query = "from CandidateTimelineActivity cta left join fetch cta.createdUser user left join fetch cta.candidateGlobalId candidate left join fetch cta.orgLocationId orglocation where cta.orgLocationId.id =:orgLocationId and cta.candidateGlobalId.id =:candidateId ORDER BY cta.createdDate DESC";
			candidateTimelineActivityList = session.createQuery(query).setLong("orgLocationId", orgLocationId).setLong("candidateId", candidateId).list();
		}
		logger.debug("Successfully Fetched Candidate Activities");
		return candidateTimelineActivityList;
	}
}
