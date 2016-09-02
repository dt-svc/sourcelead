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

import com.bis.sourcelead.dao.CandidateDao;
import com.bis.sourcelead.entity.Candidate;
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
	 * @author prasad k
	 * @since  sep 1 2016
	 * @version 0.0.1
	 * 
	 * This method is used to store/save candidate in candidate table.
	 * 1.internalley store/save in candidateEducation in candidateEducation table .
	 * 2.internalley store/save in candidateSkills in candidateSkills table .
	 * 3.internalley store/save in candidateResumes in candidateResumes table .
	 * 4.internalley store/save in candidateEmployers in candidateEmployers table .
	 *  
	 * @param candidate This is the first paramter to storeCandidate method
	 * 
	 * @return Candidate 
	 * 
	 * @throws NullPointer
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
	 * 
	 * @author David Sammeta
	 * 
	 * @param request
	 * 
	 * @return String
	 * 
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
	
	/**
	 * to get Candidate By CandidateId
	 * 
	 * @author Venkat Kesav
	 * 
	 * @param candidateId
	 * 
	 * @return Candidate
	 * 
	 * @throws NullPointerException
    */
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
	
	
	/**
	 * @author David Raju Sammeta
	 * 
	 * This Method is for getting Candidate by Candidate Id OrgId and OrgLocationID
	 * 
	 * @return Candidate
	 * 
	 * @exception NullPointerException
	 */
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Candidate getCandidateByCandidateIdAndOrgLocationId(Long candidateId, Long orgLocationId)throws Exception {
			
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from Candidate cnadidate  where cnadidate.orgLocationId.id =:orgLocationId";  
		List<Candidate> candidateList = session.createQuery(query).setLong("orgLocationId", orgLocationId).list();
		Candidate candidate = null;
		if(candidateList != null && !candidateList.isEmpty()){
			candidate = candidateList.iterator().next();
		}
		return candidate;
		
	}

	/**
	 * @author prasad.k
	 * 
	 * This mothod is used to get CandidateEmail
	 * 
	 * @param email
	 * 
	 * @return CandidateEmail
	 * 
	 * @throws Exception
	 */
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Candidate findByCandidateEmail(String email) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		List<Candidate> candidate = new ArrayList<Candidate>();
		logger.debug("findByEmail start");
		try{
			candidate = session.createQuery("from Candidate where email=:email" ).setString( "email", email ).setCacheable(true).list();
			
			logger.debug("findByEmail end");
			if (candidate.size() > 0) {
				return candidate.get(0);
			} else {
				return null;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
}
