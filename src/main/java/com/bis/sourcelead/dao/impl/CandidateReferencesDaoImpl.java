package com.bis.sourcelead.dao.impl;

import java.text.MessageFormat;
import java.util.Collection;
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

import com.bis.sourcelead.dao.CandidateReferencesDao;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.CandidateReferences;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;

@Repository
public class CandidateReferencesDaoImpl implements CandidateReferencesDao{
	private static final Logger logger = LoggerFactory.getLogger(OrgDaoImpl.class);
	
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
	 * @author Prasad K
	 * @since  jun21 06 2017
	 * @version 0.0.1
	 * 
	 * This method is used to store Candidate References in Candidate_References table.
	 * 
	 * @param candidateReferences This is the first parameter to storeCandidateResumes method
	 * 
	 * @return CandidateReferences
	 * 
	 * @throws NullPointer
	 */
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public CandidateReferences storeCandidateReferences(CandidateReferences candidateReferences) throws Exception {
		logger.debug("Storing CandidateReferences");
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (candidateReferences != null) {
				session.saveOrUpdate(candidateReferences);
			}
			logger.debug("CandidateReferences Stored Successfully");
		} catch (Exception exception) {
			    StringBuilder message= new StringBuilder(MessageFormat.format(duplicateValue,"CandidateReferences"));
				throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		return candidateReferences;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<CandidateReferences> getCandidateReferencesListByCandidateIdAndOrgLocationID(Long candidateId,
			Long orgLocationId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from CandidateReferences cr left join fetch cr.candidateId where cr.candidateId.id =:candidateId and cr.status ='1' ORDER BY cr.createdDate DESC";
		List<CandidateReferences> candidateReferencesList = session.createQuery(query).setLong("candidateId", candidateId).list();
		return candidateReferencesList;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public CandidateReferences getCandidateReferencesByCandidateIdAndReferenceId(Long candidateId,Long refId) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from CandidateReferences  cr left join fetch cr.candidateId left join fetch cr.orgLocationId where cr.id = :refId and cr.candidateId.id = :candidateId ";
		List<CandidateReferences> candidateReferencesList =  session.createQuery(query.toString()).setLong("candidateId", candidateId).setLong("refId", refId).list();
		CandidateReferences canRef = null;
		if (candidateReferencesList != null && candidateReferencesList.size() > 0) {
			canRef = (CandidateReferences) candidateReferencesList.iterator().next();
		}
		return canRef;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public CandidateReferences updateCandidateReferences(CandidateReferences candidateReferences) throws Exception {
		logger.debug("In Terminate Reference Update method");
		Session session = this.sessionFactory.getCurrentSession();
		try{
			if(candidateReferences != null){
				session.saveOrUpdate(candidateReferences);
				logger.info("Record updated successfully..!");
			}
		}catch(ConstraintViolationException exception)
		{
			StringBuilder message= new StringBuilder(MessageFormat.format(duplicateValue,"CANDIDATE_REFERENCES"));
			throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		return candidateReferences;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public CandidateReferences getCandidateReferenceByReferenceId(Long refId) throws Exception {
		logger.debug("In Candidate Reference By refId");
		Session session = this.sessionFactory.getCurrentSession();
		String query = " from CandidateReferences cr left join fetch cr.candidateId left join fetch cr.candidateGlobalId where cr.id = :refId" ;
		List<CandidateReferences> candidateReferencesList =session.createQuery(query).setLong("refId", refId).list(); 
		CandidateReferences candidateReferences = null;
		if(candidateReferencesList != null && !candidateReferencesList.isEmpty()){
			candidateReferences = candidateReferencesList.iterator().next();
		}
		return candidateReferences;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Boolean validateReferenceEmail(String referenceEmail, Long candidateId,Long candidateReferenceId) throws Exception {
		 Session session = this.sessionFactory.getCurrentSession();
		 boolean flag = false;
		 Collection<CandidateReferences> candidateReferencesList = null;
		 if(referenceEmail != null && candidateId != null){
			 if(candidateReferenceId != null) {
				 String query = "from CandidateReferences canRef where canRef.email = :referenceEmail and canRef.candidateId.id = :candidateId and canRef.id =:candidateReferenceId and canRef.status='1'";
					candidateReferencesList = session.createQuery(query.toString()).setLong("candidateReferenceId", candidateReferenceId).setLong("candidateId", candidateId).setString("referenceEmail", referenceEmail).list();
					if (candidateReferencesList != null && candidateReferencesList.size() > 0) {
						flag = false;
					}else {
						flag = true;
					}
			 }else {
				 String query = "from CandidateReferences canRef where canRef.email = :referenceEmail and canRef.candidateId.id = :candidateId and canRef.status='1'";
				 candidateReferencesList = session.createQuery(query.toString()) .setLong("candidateId", candidateId).setString("referenceEmail", referenceEmail).list();
				 if (candidateReferencesList != null && candidateReferencesList.size() > 0) {
						flag = true;
					}
			 }

				
		 }
		return flag;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public CandidateReferences validateReferenceEmailByReferEmailAndReferId(String referEmail, Long referId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from CandidateReferences cr  where cr.id =:referId and cr.email =:referEmail ";
		List<CandidateReferences> candidateReferencesList =session.createQuery(query) .setString("referEmail", referEmail).setLong("referId", referId).list();
		CandidateReferences candidateReferences = null;
		if(candidateReferencesList != null && !candidateReferencesList.isEmpty())
		{
			candidateReferences = (CandidateReferences) candidateReferencesList.iterator().next();
		}
		return candidateReferences;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Boolean validateReferenceEmailByReferEmailAndCandidateId(String referenceEmail, Long candidateId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		 boolean flag = false;
			 String query = "from CandidateReferences cr  where cr.email =:referenceEmail and cr.candidateId.id =:candidateId ";
				Collection<CandidateReferences> candidateReferencesList = session.createQuery(query) .setString("referenceEmail", referenceEmail).setLong("candidateId", candidateId).list();
				if (candidateReferencesList != null && candidateReferencesList.size() > 0) {
					flag = true;
				}
		return flag;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<CandidateReferences> getCandidateReferencesListByCandidateGlobalIdAndOrgLocationID(Long candidateId, Long orgLocationId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from CandidateReferences cr left join fetch cr.candidateGlobalId where cr.candidateGlobalId.id =:candidateId and cr.status ='1' ORDER BY cr.createdDate DESC";
		List<CandidateReferences> candidateReferencesList = session.createQuery(query).setLong("candidateId", candidateId).list();
		return candidateReferencesList;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Boolean validateReferenceEmailByReferEmailAndCandidateGlobalId(String referenceEmail, Long candidateId)
			throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		 boolean flag = false;
			 String query = "from CandidateReferences cr  where cr.email =:referenceEmail and cr.candidateGlobalId.id =:candidateId ";
				Collection<CandidateReferences> candidateReferencesList = session.createQuery(query) .setString("referenceEmail", referenceEmail).setLong("candidateId", candidateId).list();
				if (candidateReferencesList != null && candidateReferencesList.size() > 0) {
					flag = true;
				}
		return flag;
	}
	
}
