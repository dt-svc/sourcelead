package com.bis.sourcelead.dao.impl;

import java.math.BigInteger;
import java.text.MessageFormat;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.CandidateEmployersDao;
import com.bis.sourcelead.entity.CandidateEmployers;
import com.bis.sourcelead.entity.Immigration;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;
import com.bis.sourcelead.util.CommonUtil;

/**
 * @author Ajith Matta
 * @create date 25 Aug 2016
 */

@Repository
public class CandidateEmployersDaoImpl  implements CandidateEmployersDao{
	
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
	
	@Autowired
	private CommonUtil commonUtil;
	
	
	
	private static final Logger logger = LoggerFactory.getLogger(CandidateEmployersDaoImpl.class);
	
	/**
	 * @author Ajith Matta
	 * 
	 * This Method is for save the candidate employers by json 
	 * 
	 * @param candidateEmployers
	 * 
	 * @return CandidateEmployers
	 * 
	 * @exception NullPointerException
	 */
	@Override
	@Transactional(rollbackFor = { Exception.class })	
	public CandidateEmployers storeCandidateEmployers(CandidateEmployers candidateEmployers) throws Exception {
		
		logger.debug("Storing CandidateEmployers");
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (candidateEmployers != null) {
				session.saveOrUpdate(candidateEmployers);
			}
			logger.debug("CandidateEmployers Stored Successfully");
		} catch (Exception exception) {
			    StringBuilder message= new StringBuilder(MessageFormat.format(duplicateValue,"CandidateEmployers"));
				throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		return candidateEmployers;
		
		
	}
	
	/**
	 * @author Ajith Matta
	 * 
	 *This Method  is for getting Candidates Employers List By CandidateId And OrgLocationID
	 * 
	 * @param candidateId
	 *
	 * @param orgLocationId
	 * 
	 * @return Candidate employers list
	 * 
	 * @exception NullPointerException
	 */
	@Override
	@Transactional(rollbackFor = { Exception.class })	
	public List<CandidateEmployers> getCandidatesEmployersListByCandidateIdAndOrgLocationID(Long candidateId, Long loc)
			throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from CandidateEmployers candidateEmployers  left join fetch candidateEmployers.candidateId  where candidateEmployers.orgLocationId =:orgLocationId and candidateEmployers.candidateId.id =:candidateId and  candidateEmployers.status = '1' ORDER BY (candidateEmployers.endYear) DESC,candidateEmployers.id DESC";  
		List<CandidateEmployers> candidateList = session.createQuery(query).setLong("orgLocationId", loc).setLong("candidateId", candidateId).list();
		return candidateList;
	}

	
	@Override
	@Transactional(rollbackFor = { Exception.class })	
	public List<CandidateEmployers> getCandidatesEmployersListByCandidateIdAndOrgLocationIDImmigration(Long candidateId, String searchVal)
			throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from CandidateEmployers candidateEmployers  left join fetch candidateEmployers.candidateId  where candidateEmployers.candidateId.id =:candidateId and  candidateEmployers.status = '1'";
		if(!StringUtils.isEmpty(searchVal) && searchVal != null) {
			query = query + " and (candidateEmployers.name like :searchVal or candidateEmployers.designation like :searchVal) ";
		}
		query = query + " ORDER BY (candidateEmployers.startYear) DESC, candidateEmployers.id DESC";  
		Query candidateQuery = session.createQuery(query).setLong("candidateId", candidateId);
		if(!StringUtils.isEmpty(searchVal) && searchVal != null) {
			candidateQuery.setParameter("searchVal", "%"+searchVal+"%");
		}
		List<CandidateEmployers> candidateList = candidateQuery.list();
		return candidateList;
	}
	
	/**
	 * This method is used for display the get Candidate Employer By candidateId and CandidateEmployerID 
	 * 
	 * @author Sammeta David Raju
	 * 
	 * @param candidateEmployersId
	 * 
	 * @param candidateId
	 * 
	 * @return CandidateEmployers
	 * 
	 * @throws Exception
    */
	@Override
	@Transactional(rollbackFor = { Exception.class })	
	public CandidateEmployers getCandidateEmployerBycandidateIdandCandidateEmployerID(Long candidateEmployersId,Long candidateId) { 

		
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from CandidateEmployers  cEmp left join fetch cEmp.candidateId left join fetch cEmp.orgLocationId where cEmp.id = :candidateEmployersId and cEmp.candidateId.id = :candidateId ";
		List<CandidateEmployers> employersList =  session.createQuery(query.toString()).setLong("candidateEmployersId", candidateEmployersId).setLong("candidateId", candidateId)
				.list();
		

		CandidateEmployers canEMp = null;
		if (employersList != null && employersList.size() > 0) {
			canEMp = (CandidateEmployers) employersList.iterator().next();
		}
		return canEMp;

	}

	@Override
	@Transactional(rollbackFor = { Exception.class })	
	public CandidateEmployers getCandidateCurrentEmployer(Long candidateId, Long orgLocationId) throws Exception {
		
		
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from CandidateEmployers  employer left join fetch employer.candidateId left join fetch employer.orgLocationId where employer.candidateId = :candidateId and "
				+ " employer.orgLocationId.id = :orgLocationId and employer.isCurrentPosition = 'Y' and employer.status = '1' ";
		List<CandidateEmployers> employersList =  session.createQuery(query.toString()).setLong("candidateId", candidateId).setLong("orgLocationId", orgLocationId)
				.list();
		

		CandidateEmployers canEMp = null;
		if (employersList != null && employersList.size() > 0) {
			canEMp = (CandidateEmployers) employersList.iterator().next();
		}
		return canEMp;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })	
	public CandidateEmployers getCandidateLastCompanyEmployer(Long candidateId, Long orgLocationId) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from CandidateEmployers  employer left join fetch employer.candidateId left join fetch employer.orgLocationId where employer.candidateId = :candidateId and "
				+ " employer.orgLocationId.id = :orgLocationId and employer.isCurrentPosition = 'N' and employer.status = '1' ";
		List<CandidateEmployers> employersList =  session.createQuery(query.toString()).setLong("candidateId", candidateId).setLong("orgLocationId", orgLocationId)
				.list();
		

		CandidateEmployers canEMp = null;
		if (employersList != null && employersList.size() > 0) {
			canEMp = (CandidateEmployers) employersList.iterator().next();
		}
		return canEMp;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })	
	public CandidateEmployers getCandidateEmployerById(Long candidateEmployersId) throws Exception {
		CandidateEmployers candidateEmployer= null;
        try {
			Session session = this.sessionFactory.getCurrentSession();
			candidateEmployer = (CandidateEmployers)session.get(CandidateEmployers.class,candidateEmployersId);
		} catch (HibernateException e) {
			logger.error("SQL Exception occured while doing getting record -->"+e.getMessage());
			e.printStackTrace();
		}
		return candidateEmployer;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })	
	public Integer getCandidateActiveEmployersCountByCandidateId(Long candidateId) {
		Session session = this.sessionFactory.getCurrentSession();
		Integer employersCount = 0;
		
		String countQuery ="SELECT"
				+ " COUNT(EMPL.ID) AS EMPL_COUNT"
				+ " FROM"
				    + " CANDIDATE_EMPLOYERS EMPL "
					+ " WHERE EMPL.CANDIDATE_ID = :candidateId AND EMPL.STATUS = '1' ";
		
		Query countqueryString = session.createSQLQuery(countQuery);
		countqueryString.setParameter("candidateId", candidateId);
		BigInteger filterCount =  (BigInteger) countqueryString.uniqueResult();
		employersCount = filterCount.intValue();
		return employersCount;
}

	@Override
	@Transactional(rollbackFor = { Exception.class })	
	public List<CandidateEmployers> getCandidatesGlobalEmployersListByCandidateIdAndOrgLocationID(Long candidateId,
			Long orgLocationId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from CandidateEmployers candidateEmployers  left join fetch candidateEmployers.candidateGlobalId  where candidateEmployers.orgLocationId =:orgLocationId and candidateEmployers.candidateGlobalId.id =:candidateId and  candidateEmployers.status = '1' ORDER BY (candidateEmployers.endYear) DESC,candidateEmployers.id DESC";  
		List<CandidateEmployers> candidateList = session.createQuery(query).setLong("orgLocationId", orgLocationId).setLong("candidateId", candidateId).list();
		return candidateList;
	}


}
