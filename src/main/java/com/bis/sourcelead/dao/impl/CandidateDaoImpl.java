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

import com.bis.sourcelead.dao.CandidateDao;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.ClassField;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;

/**
 * 
 * @author Prasad K
 * @date 29th Mar 2016
 *
 */
@Repository
public class CandidateDaoImpl implements CandidateDao {
	
	private static final Logger logger = LoggerFactory.getLogger(CandidateDaoImpl.class);
	
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
	
	
	protected boolean isNullOrEmpty(String str) {
		return (str == null || "".equals(str.trim()));
	}

	 /**
	  * @author PrasadK
     * This method for to store candidate into Database
     * @return candidate
     * @exception NullPointerException
     */
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Candidate storeCandidate(Candidate candidate) throws Exception {
		logger.debug("Storing Candidate");
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (candidate != null) {
				session.saveOrUpdate(candidate);
			}
			logger.debug("Candidate Stored Successfully");
		} catch (Exception exception) {
			    StringBuilder message= new StringBuilder(MessageFormat.format(duplicateValue,"Candidate"));
				throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		return candidate;
	}
	
	/**
	 * @author David Raju Sammeta
	 * 
	 * This Method is for getting Candidate by Candidate Id
	 * 
	 * @return Candidate
	 * 
	 * @exception NullPointerException
	 */
	@Override
	public Candidate getCandidateByCandidateIdAndOrgLocationIdAndOrgId(Long candidateId, Long orgId, Long orgLocationId)
			throws Exception {
		return null;
	}

	/**
	 * to Candidates List by OrgCode OrgLocationCode
	 * @author David Sammeta
	 * @param request
	 * @return String
	 * @throws NullPointerException
    */
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<Candidate> getCandidatesListByOrgLocationID(Long orgLocationId) throws Exception {
		 // TODO Auto-generated method stub
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from Candidate cnadidate  where cnadidate.orgLocationId.id =:orgLocationId";  
		List<Candidate> candidateList = session.createQuery(query).setLong("orgLocationId", orgLocationId).list();
		return candidateList;
		
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Candidate getCandidateByCandidateId(Long candidateId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from Candidate cnadidate  where cnadidate.id =:candidateId";
		List candidateList = session.createQuery(query).setLong("candidateId", candidateId).list();
		
		Candidate candidate = null;
		if(candidateList != null && !candidateList.isEmpty())
		{
			candidate = (Candidate) candidateList.iterator().next();
		}
		return candidate;
	}
}
