package com.bis.sourcelead.dao.impl;

import java.math.BigInteger;
import java.text.MessageFormat;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.CandidateDocumentsDao;
import com.bis.sourcelead.entity.CandidateDocuments;
import com.bis.sourcelead.entity.CandidateEducation;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;

@Repository
public class CandidateDocumentsDaoImpl implements CandidateDocumentsDao{
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
	 * @since  april 07 2017
	 * @version 0.0.1
	 * 
	 * This method is used to store Candidate Documents in candidate_Documents table.
	 *  
	 * 
	 * @param candidateDocuments This is the first parameter to storeCandidateResumes method
	 * 
	 * @return CandidateDocuments
	 * 
	 * @throws NullPointer
	 */
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public CandidateDocuments storeCandidateDocuments(CandidateDocuments candidateDocuments) throws Exception {
		logger.debug("Storing CandidateDocuments");
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (candidateDocuments != null) {
				session.saveOrUpdate(candidateDocuments);
			}
			logger.debug("CandidateDocuments Stored Successfully");
		} catch (Exception exception) {
			    StringBuilder message= new StringBuilder(MessageFormat.format(duplicateValue,"CandidateDocuments"));
				throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		return candidateDocuments;
	}
   
	/**
	 * This method for to get Documents List.
	 */
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<CandidateDocuments> getCandidateDocumentsListByCandidateIdAndOrgLocationID(Long candidateId, Long orgLocationId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from CandidateDocuments cd left join fetch cd.requestedDocumentsId where cd.candidateId.id =:candidateId  and cd.status = 1 ORDER BY cd.id DESC";
		List<CandidateDocuments> candidateDocumentsList = session.createQuery(query).setLong("candidateId", candidateId).list();
		return candidateDocumentsList;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public CandidateDocuments updateCandidateDocuments(CandidateDocuments candidateDocuments) throws Exception {
		logger.debug("In Terminate Document Update method");
		Session session = this.sessionFactory.getCurrentSession();
		try{
			if(candidateDocuments != null){
				session.saveOrUpdate(candidateDocuments);
				logger.info("Record updated successfully..!");
			}
		}catch(ConstraintViolationException exception)
		{
			StringBuilder message= new StringBuilder(MessageFormat.format(duplicateValue,"CANDIDATE_DOCUMENTS"));
			throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		return candidateDocuments;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public CandidateDocuments getCandidateDocumentByCandidateId(Long candidateId,String documentUUID) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from CandidateDocuments  cd left join fetch cd.candidateId where cd.candidateId.id = :candidateId and cd.documentUUID =:documentUUID";
		List<CandidateDocuments> candidateDocumentsList =  session.createQuery(query.toString()).setLong("candidateId", candidateId).setString("documentUUID", documentUUID).list();
		CandidateDocuments canDoc = null;
		if (candidateDocumentsList != null && candidateDocumentsList.size() > 0) {
			canDoc = (CandidateDocuments) candidateDocumentsList.iterator().next();
		}
		return canDoc;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public CandidateDocuments getCandidateDocumentByDocumentId(Long documentId) throws Exception {
		logger.debug("In Candidate Document By documentId");
		Session session = this.sessionFactory.getCurrentSession();
		String query = " from CandidateDocuments cd  where cd.id = :documentId" ;
		List<CandidateDocuments> candidateDocumentsList =session.createQuery(query).setLong("documentId", documentId).list(); 
		CandidateDocuments candidateDocuments = null;
		if(candidateDocumentsList != null && !candidateDocumentsList.isEmpty()){
			candidateDocuments = candidateDocumentsList.iterator().next();
		}
		return candidateDocuments;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public CandidateDocuments getCandidateDocumentByDocumentUUID(String documentUUID) throws Exception {
		logger.debug("In Candidate Resume By ResumeId");
		Session session = this.sessionFactory.getCurrentSession();
		String query = " from CandidateDocuments cd where cd.documentUUID = :documentUUID" ;
		List<CandidateDocuments> candidateDocumentsList = session.createQuery(query).setString("documentUUID", documentUUID).list(); 
		CandidateDocuments candidateDocuments = null;
		if(candidateDocumentsList != null && !candidateDocumentsList.isEmpty()){
			candidateDocuments = candidateDocumentsList.iterator().next();
		}
		return candidateDocuments;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<CandidateDocuments> getCandidateDocumentsListByRequestedDocumentsId(Long requestedDocumentsId) throws Exception
	{
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from CandidateDocuments cd left join fetch cd.candidateId where cd.requestedDocumentsId.id =:requestedDocumentsId and cd.status = 1 ";
		List<CandidateDocuments> candidateDocumentsList = session.createQuery(query).setLong("requestedDocumentsId", requestedDocumentsId).list();
		return candidateDocumentsList;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Boolean validateCandidateDocument(Long candidateId, String candidateDocument) throws Exception
	{
		Session session = this.sessionFactory.getCurrentSession();
		boolean flag = false;
		String query ="from CandidateDocuments cd where cd.documentType = :candidateDocument and cd.candidateId.id = :candidateId and cd.status ='1' ";
		List<CandidateDocuments> candidateDocumentsList = session.createQuery(query).setLong("candidateId", candidateId).setString("candidateDocument", candidateDocument).list();
		if(candidateDocumentsList !=null && candidateDocumentsList.size() >0){
			flag = true;
			return flag;
		}else{
			return flag;
		}
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Integer getCandidateActiveDocumentsByCandidateId(Long candidateId) {
		Session session = this.sessionFactory.getCurrentSession();
		Integer documentsCount = 0;
		
		String countQuery ="SELECT"
				+ " COUNT(DOCS.ID) AS DOCS_COUNT"
				+ " FROM"
				    + " CANDIDATE_DOCUMENTS DOCS "
					+ " WHERE DOCS.CANDIDATE_ID = :candidateId AND DOCS.STATUS = '1' ";
		
		Query countqueryString = session.createSQLQuery(countQuery);
		countqueryString.setParameter("candidateId", candidateId);
		BigInteger filterCount =  (BigInteger) countqueryString.uniqueResult();
		documentsCount = filterCount.intValue();
		return documentsCount;
}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Integer getCandidateGlobalActiveDocumentsByCandidateId(Long candidateId) {

		Session session = this.sessionFactory.getCurrentSession();
		Integer documentsCount = 0;
		
		String countQuery ="SELECT"
				+ " COUNT(DOCS.ID) AS DOCS_COUNT"
				+ " FROM"
				    + " CANDIDATE_DOCUMENTS DOCS "
					+ " WHERE DOCS.CANDIDATE_GLOBAL_ID = :candidateId AND DOCS.STATUS = '1' ";
		
		Query countqueryString = session.createSQLQuery(countQuery);
		countqueryString.setParameter("candidateId", candidateId);
		BigInteger filterCount =  (BigInteger) countqueryString.uniqueResult();
		documentsCount = filterCount.intValue();
		return documentsCount;

	}

}
