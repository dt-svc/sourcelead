package com.bis.sourcelead.dao.impl;

import java.math.BigInteger;
import java.text.MessageFormat;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.CandidateResumesDao;
import com.bis.sourcelead.dto.CandidateDTO;
import com.bis.sourcelead.dto.CandidateResumesDTO;
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
		String query = "from CandidateResumes candidateResumes left join fetch candidateResumes.candidateId where candidateResumes.candidateId =:candidateId and candidateResumes.status = :status ORDER BY candidateResumes.id DESC";  
		List<CandidateResumes> candidateResumesList = session.createQuery(query).setLong("candidateId", candidateId).setInteger("status", status).list();
		return candidateResumesList;
		
	}
	
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<CandidateResumes> getCandidateResumesByIds(List<Long> submissionResumeIds) {
		logger.debug("In get Resumes list by candidateid and status method");
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from CandidateResumes candidateResumes where candidateResumes.id IN (:submissionResumeIds)";  
		List<CandidateResumes> candidateResumesList = session.createQuery(query).setParameterList("submissionResumeIds", submissionResumeIds).list();
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

		String query = " from CandidateResumes candidateResumes left join fetch candidateResumes.candidateGlobalId  where candidateResumes.id = :resumeId" ;
		List<CandidateResumes> candidateResumesList =session.createQuery(query).setLong("resumeId", resumeId).list(); 
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
			String query = "from CandidateResumes candidateResumes left join fetch candidateResumes.orgLocationId  left join fetch candidateResumes.candidateId  where candidateResumes.createdDate IN (SELECT max(canresume.createdDate) FROM CandidateResumes canresume where canresume.candidateId.id =:candidateId) and candidateResumes.status = :status";  
			List<CandidateResumes> candidateResumesList = session.createQuery(query).setLong("candidateId", candidateId).setInteger("status", status).list();
			CandidateResumes candidateResumes = null;
			if(candidateResumesList != null && !candidateResumesList.isEmpty()){
				candidateResumes = candidateResumesList.iterator().next();
			}
			return candidateResumes;
}


@Override
@Transactional
public CandidateResumes getCandidateResumeByResumeUUID(String resumeUUID) throws Exception {
	logger.debug("In Candidate Resume By ResumeId");
	Session session = this.sessionFactory.getCurrentSession();
	String query = " from CandidateResumes candidateResumes where candidateResumes.resumeUUID = :resumeUUID" ;
	List<CandidateResumes> candidateResumesList = session.createQuery(query).setString("resumeUUID", resumeUUID).list(); 
	CandidateResumes candidateResumes = null;
	if(candidateResumesList != null && !candidateResumesList.isEmpty()){
		candidateResumes = candidateResumesList.iterator().next();
	}
	return candidateResumes;
}


@Override
@Transactional(rollbackFor = { Exception.class })
public Integer getCandidateResumeCountByResumeId(Long candidateId) throws Exception {
	logger.debug("In Candidate Resume By ResumeId");
	Integer count= 0;
	Session session = this.sessionFactory.getCurrentSession();
	String query = " from CandidateResumes candidateResumes where candidateId.id = :candidateId and status = '1' " ;
	List<Long> candidateResumesCount =session.createQuery(query).setLong("candidateId", candidateId).list(); 
	if(candidateResumesCount != null){
	count = candidateResumesCount.size();
	}
	return count;
}


@Override
@Transactional(rollbackFor = { Exception.class })
public Integer getCandidateResumeCountByCandidateId(Long candidateId) throws Exception {
	logger.debug("In Candidate Resume By ResumeId");
	Integer count= 0;
	Session session = this.sessionFactory.getCurrentSession();
	String query = " from CandidateResumes candidateResumes where candidateId.id = :candidateId and status = '1' " ;
	List<Long> candidateResumesCount =session.createQuery(query).setLong("candidateId", candidateId).list(); 
	if(candidateResumesCount != null){
	count = candidateResumesCount.size();
	}
	return count;
}


@Override
@Transactional(rollbackFor = { Exception.class })
public CandidateResumes getCandidateResumesListByCandidateId(Long candidateId) {
	Session session = this.sessionFactory.getCurrentSession();
	String query = "from CandidateResumes candidateResumes left join fetch candidateResumes.orgLocationId  left join fetch candidateResumes.candidateId  where candidateResumes.createdDate IN (SELECT max(canresume.createdDate) FROM CandidateResumes canresume where canresume.candidateId.id =:candidateId)";  
	List<CandidateResumes> candidateResumesList = session.createQuery(query).setLong("candidateId", candidateId).list();
	CandidateResumes candidateResumes = null;
	if(candidateResumesList != null && !candidateResumesList.isEmpty()){
		candidateResumes = candidateResumesList.iterator().next();
	}
	return candidateResumes;
}


@Override
@Transactional(rollbackFor = { Exception.class })
public CandidateResumes getCandidateResumeByCandidateId(Long candidateId) throws Exception {
	
	Session session = this.sessionFactory.getCurrentSession();
	String query = "from CandidateResumes candidateResumes left join fetch candidateResumes.candidateId  where candidateResumes.candidateId.id =:candidateId and candidateResumes.status = '1' ORDER BY candidateResumes.updatedDate DESC";  
	List<CandidateResumes> candidateResumesList = session.createQuery(query).setLong("candidateId", candidateId).list();
	CandidateResumes candidateResumes = null;
	if(candidateResumesList != null && !candidateResumesList.isEmpty()){
		candidateResumes = candidateResumesList.iterator().next();
	}
	return candidateResumes;
}


@Override
@Transactional(rollbackFor = { Exception.class })
public CandidateResumesDTO getCandidateResumesDTOByCandidateId(Long candidateId) throws Exception {
	
	List<CandidateResumesDTO> candidateResumesDTOList = null;
	CandidateResumesDTO candidateResumesDTO = null;
	Session session = this.sessionFactory.getCurrentSession();
	String query = null;
			query ="SELECT "
			+" CR.ID AS resumeId, "
			+" CR.TITLE AS title, "
			+" CR.CANDIDATE_ID AS candidateId, "
			+" CR.ORG_LOCATION_ID AS orgLocationId, "
			+" DATE_FORMAT(CR.CREATED_DATE ,\"%Y-%m-%d\") AS createdDate, " 
			+" DATE_FORMAT(CR.UPDATED_DATE ,\"%Y-%m-%d\") AS updatedDate " 
			+" FROM CANDIDATE_RESUMES CR "
			+" WHERE "
			+" CR.CANDIDATE_ID = :candidateId AND CR.STATUS = '1' "
			+" ORDER BY DATE_FORMAT(CR.UPDATED_DATE ,\"%Y-%m-%d\") DESC ";
			
        Query queryObj = session.createSQLQuery(query);
		queryObj.setLong("candidateId", candidateId);
		queryObj.setResultTransformer(Transformers.aliasToBean(CandidateResumesDTO.class));
		candidateResumesDTOList = queryObj.list();
		if(candidateResumesDTOList != null && !candidateResumesDTOList.isEmpty()) {
			candidateResumesDTO = candidateResumesDTOList.get(0);
		}
		
		return candidateResumesDTO;

}


@Override
@Transactional(rollbackFor = { Exception.class })
public Integer getCandidateActiveResumeCountByCandidateId(Long candidateId) throws Exception {
	Session session = this.sessionFactory.getCurrentSession();
	Integer educationsCount = 0;
	
	String countQuery ="SELECT"
			+ " COUNT(CR.ID) AS RESUMES_COUNT"
			+ " FROM "
			    + " CANDIDATE_RESUMES CR "
				+ " WHERE CR.CANDIDATE_ID = :candidateId AND CR.STATUS = '1' ";
	
	
	Query countqueryString = session.createSQLQuery(countQuery);
	countqueryString.setParameter("candidateId", candidateId);
	BigInteger filterCount =  (BigInteger) countqueryString.uniqueResult();
	educationsCount = filterCount.intValue();
	return educationsCount;
}


@Override
@Transactional(rollbackFor = { Exception.class })
public CandidateResumesDTO getCandidateLatesResumeDetails(Long candidateId) throws Exception {
	
	List<CandidateResumesDTO> candidateResumesDTOList = null;
	CandidateResumesDTO candidateResumesDTO = null;
	Session session = this.sessionFactory.getCurrentSession();
	String query = null;
			query ="SELECT "
			+" CR.ID AS resumeId, "
			+" CR.FILE_NAME AS resumeName, "
			+" CR.FILE_CONTENT_TYPE AS fileContentType, "
			+" CR.FILE_URL AS fileUrl "
			+" FROM CANDIDATE_RESUMES CR "
			+" WHERE "
			+" CR.CANDIDATE_ID = :candidateId AND CR.STATUS = '1' "
			+" ORDER BY CR.CREATED_DATE DESC ";
			
        Query queryObj = session.createSQLQuery(query);
		queryObj.setLong("candidateId", candidateId);
		queryObj.setResultTransformer(Transformers.aliasToBean(CandidateResumesDTO.class));
		candidateResumesDTOList = queryObj.list();
		if(candidateResumesDTOList != null && !candidateResumesDTOList.isEmpty()) {
			candidateResumesDTO = candidateResumesDTOList.get(0);
		}
		
		return candidateResumesDTO;

}


@Override
@Transactional(rollbackFor = { Exception.class })
public CandidateResumesDTO getCandidateGlobalLatesResumeDetails(Long candidateId) throws Exception {
	
	List<CandidateResumesDTO> candidateResumesDTOList = null;
	CandidateResumesDTO candidateResumesDTO = null;
	Session session = this.sessionFactory.getCurrentSession();
	String query = null;
			query ="SELECT "
			+" CR.ID AS resumeId, "
			+" CR.FILE_NAME AS resumeName, "
			+" CR.FILE_CONTENT_TYPE AS fileContentType, "
			+" CR.FILE_URL AS fileUrl "
			+" FROM CANDIDATE_RESUMES CR "
			+" WHERE "
			+" CR.CANDIDATE_GLOBAL_ID = :candidateId AND CR.STATUS = '1' "
			+" ORDER BY CR.CREATED_DATE DESC ";
			
        Query queryObj = session.createSQLQuery(query);
		queryObj.setLong("candidateId", candidateId);
		queryObj.setResultTransformer(Transformers.aliasToBean(CandidateResumesDTO.class));
		candidateResumesDTOList = queryObj.list();
		if(candidateResumesDTOList != null && !candidateResumesDTOList.isEmpty()) {
			candidateResumesDTO = candidateResumesDTOList.get(0);
		}
		
		return candidateResumesDTO;
}


@Override
@Transactional(rollbackFor = { Exception.class })
public Integer getCandidateGlobalActiveResumeCountByCandidateId(Long candidateId) throws Exception {
	

	Session session = this.sessionFactory.getCurrentSession();
	Integer educationsCount = 0;
	
	String countQuery ="SELECT"
			+ " COUNT(CR.ID) AS RESUMES_COUNT"
			+ " FROM "
			    + " CANDIDATE_RESUMES CR "
				+ " WHERE CR.CANDIDATE_GLOBAL_ID = :candidateId AND CR.STATUS = '1' ";
	
	
	Query countqueryString = session.createSQLQuery(countQuery);
	countqueryString.setParameter("candidateId", candidateId);
	BigInteger filterCount =  (BigInteger) countqueryString.uniqueResult();
	educationsCount = filterCount.intValue();
	return educationsCount;

}


@Override
@Transactional(rollbackFor = { Exception.class })
public List<CandidateResumes> getCandidateGlobalResumesListByCandidateIdAndStatus(Long candidateId, Integer status)
		throws Exception {
	logger.debug("In get Resumes list by candidateid and status method");
	Session session = this.sessionFactory.getCurrentSession();
	String query = "from CandidateResumes candidateResumes left join fetch candidateResumes.candidateGlobalId where candidateResumes.candidateGlobalId =:candidateId and candidateResumes.status = :status ORDER BY candidateResumes.id DESC";  
	List<CandidateResumes> candidateResumesList = session.createQuery(query).setLong("candidateId", candidateId).setInteger("status", status).list();
	return candidateResumesList;
}


}
