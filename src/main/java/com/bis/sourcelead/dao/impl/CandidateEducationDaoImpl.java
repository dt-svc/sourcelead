package com.bis.sourcelead.dao.impl;

import java.math.BigInteger;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.HibernateException;
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

import com.bis.sourcelead.dao.CandidateEducationDao;
import com.bis.sourcelead.entity.CandidateEducation;
import com.bis.sourcelead.entity.CandidateEmployers;

@Repository
public class CandidateEducationDaoImpl implements CandidateEducationDao {
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
	
	protected boolean isNullOrEmpty(String str)
	{
		return (str == null || "".equals(str.trim()));
	}
	
	/**
	 * This Method is for saving candidate education details
	 * 
	 * @author Ajith Matta
	 * 
	 * @since
	 * 
	 * @param CandidateEducation
	 * 
	 * @return CandidateEducation
	 * 
	 * @throws Exception
	 */
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public CandidateEducation storeCandidateEducation(CandidateEducation candidateEducation) throws Exception
	{
		logger.debug("Adding Candidate Education");
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (candidateEducation != null) {
				session.saveOrUpdate(candidateEducation);
			}
			logger.debug("Candidate Education Added Successfully");
		} catch (ConstraintViolationException exception) {
		}
		return candidateEducation;
	}
	
	/**
	 * This Method is used to get candidate education details
	 * 
	 * @author Ajith Matta
	 * 
	 * @since
	 * 
	 * @param candidateId
	 * 
	 * @return CandidateEducation list
	 * 
	 * @throws Exception
	 */
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<CandidateEducation> getCandidateEducationListByCandidateIdAndOrgLocationID(Long candidateId, Long orgLocationId) throws Exception
	{
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from CandidateEducation ce where  ce.candidateId.id =:candidateId and ce.status ='1' ORDER BY ce.id DESC";
		List<CandidateEducation> candidateEducation = session.createQuery(query).setLong("candidateId", candidateId).list();
		return candidateEducation;
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<CandidateEducation> getCandidateEducationListByCandidateIdAndOrgLocationIDImmigration(Long candidateId, Long orgLocationId, String searchVal) throws Exception
	{
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from CandidateEducation ce where  ce.candidateId.id =:candidateId and ce.status ='1'  ";
		if(!StringUtils.isEmpty(searchVal) && searchVal != null) {
			query = query + " and (ce.specialization like :searchVal or ce.name like :searchVal or ce.institute like :searchVal) ";
		}
		query = query + " ORDER BY  DATE(ce.startDate) DESC, ce.id DESC";
		Query candidateQuery = session.createQuery(query).setLong("candidateId", candidateId);
		if(!StringUtils.isEmpty(searchVal) && searchVal != null) {
			candidateQuery.setParameter("searchVal", "%"+searchVal+"%");
		}
		List<CandidateEducation> candidateEducation = candidateQuery.list();
		return candidateEducation;
	}
	
	/**
	 * @author Sammeta David Raju
	 * 
	 *         This Method is for getting CandidateEducation By candidateId and
	 *         candidateEducationId
	 * 
	 * @param candidateId
	 * 
	 * @param candidateEducationId
	 * 
	 * @return {@link CandidateEducation}
	 * 
	 * @exception NullPointerException
	 * 
	 */
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public CandidateEducation getCandidateEducationBycandidateIdandEducationId(Long candidateId, Long candidateEducationId) throws Exception
	{
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from CandidateEducation  cEmp left join fetch cEmp.candidateId left join fetch cEmp.orglocationId where cEmp.id = :candidateEducationId and cEmp.candidateId.id = :candidateId ";
		List<CandidateEducation> educationList = session.createQuery(query.toString()).setLong("candidateEducationId", candidateEducationId).setLong("candidateId", candidateId).list();
		CandidateEducation canEMp = null;
		if (educationList != null && educationList.size() > 0) {
			canEMp = (CandidateEducation) educationList.iterator().next();
		}
		return canEMp;
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public CandidateEducation getCandidateEducationByCandidateId(Long candidateId) throws Exception
	{
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from CandidateEducation  cEmp left join fetch cEmp.candidateId where cEmp.candidateId.id = :candidateId and cEmp.status ='1' ";
		List<CandidateEducation> educationList = session.createQuery(query.toString()).setLong("candidateId", candidateId).list();
		CandidateEducation canEMp = null;
		if (educationList != null && educationList.size() > 0) {
			canEMp = (CandidateEducation) educationList.iterator().next();
		}
		return canEMp;
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Boolean validateEducationName(Long candidateId, String candidateEducation) throws Exception
	{
		Session session = this.sessionFactory.getCurrentSession();
		boolean flag = false;
		String query = "from CandidateEducation cEmp where cEmp.name = :candidateEducation and cEmp.candidateId.id = :candidateId and cEmp.status ='1' ";
		List<CandidateEducation> educationList = session.createQuery(query).setLong("candidateId", candidateId).setString("candidateEducation", candidateEducation).list();
		if (educationList != null && educationList.size() > 0) {
			flag = true;
			return flag;
		}
		else {
			return flag;
		}
	}
	
	@Override
	@Transactional
	public List<String> getCandidateEducationNamesByCandidateId(Long candidateId) throws Exception
	{
		Session session = this.sessionFactory.getCurrentSession();
		String query = "select edu.name from CandidateEducation edu where edu.candidateId.id =:candidateId and edu.status = '1'";
		List<String> educationNames = session.createQuery(query).setLong("candidateId", candidateId).list();
		return educationNames;
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class })	
	public CandidateEducation getCandidateEducationById(Long candidateEduId) throws Exception {
		CandidateEducation candidateEducation= null;
        try {
			Session session = this.sessionFactory.getCurrentSession();
			candidateEducation = (CandidateEducation)session.get(CandidateEducation.class,candidateEduId);
		} catch (HibernateException e) {
			logger.error("SQL Exception occured while doing getting record -->"+e.getMessage());
			e.printStackTrace();
		}
		return candidateEducation;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })	
	public Integer getCandidateActiveEducationsCountByCandidateId(Long candidateId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		Integer resumesCount = 0;
		
		String countQuery ="SELECT"
				+ " COUNT(EDU.ID) AS EDU_COUNT"
				+ " FROM"
				    + " CANDIDATE_EDUCATION EDU "
					+ " WHERE EDU.CANDIDATE_ID = :candidateId AND EDU.STATUS = '1' ";
		
		Query countqueryString = session.createSQLQuery(countQuery);
		countqueryString.setParameter("candidateId", candidateId);
		BigInteger filterCount =  (BigInteger) countqueryString.uniqueResult();
		resumesCount = filterCount.intValue();
		return resumesCount;
}

	@Override
	@Transactional(rollbackFor = { Exception.class })	
	public Integer getCandidateGlobalActiveEducationsCountByCandidateId(Long candidateId) throws Exception {

		Session session = this.sessionFactory.getCurrentSession();
		Integer resumesCount = 0;
		
		String countQuery ="SELECT"
				+ " COUNT(EDU.ID) AS EDU_COUNT"
				+ " FROM"
				    + " CANDIDATE_EDUCATION EDU "
					+ " WHERE EDU.CANDIDATE_GLOBAL_ID = :candidateId AND EDU.STATUS = '1' ";
		
		Query countqueryString = session.createSQLQuery(countQuery);
		countqueryString.setParameter("candidateId", candidateId);
		BigInteger filterCount =  (BigInteger) countqueryString.uniqueResult();
		resumesCount = filterCount.intValue();
		return resumesCount;

	}

	@Override
	@Transactional(rollbackFor = { Exception.class })	
	public List<CandidateEducation> getCandidateGlobalEducationListByCandidateIdAndOrgLocationID(Long candidateId,
			Long orgLocationId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from CandidateEducation ce  left join fetch ce.candidateGlobalId where  ce.candidateGlobalId.id =:candidateId and ce.status ='1' ORDER BY ce.id DESC";
		List<CandidateEducation> candidateEducation = session.createQuery(query).setLong("candidateId", candidateId).list();
		return candidateEducation;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })	
	public CandidateEducation getCandidateGlobalEducationBycandidateIdandEducationId(Long candidateId,
			Long candidateEducationId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from CandidateEducation  cEmp left join fetch cEmp.candidateGlobalId left join fetch cEmp.orglocationId where cEmp.id = :candidateEducationId and cEmp.candidateGlobalId.id = :candidateId ";
		List<CandidateEducation> educationList = session.createQuery(query.toString()).setLong("candidateEducationId", candidateEducationId).setLong("candidateId", candidateId).list();
		CandidateEducation canEMp = null;
		if (educationList != null && educationList.size() > 0) {
			canEMp = (CandidateEducation) educationList.iterator().next();
		}
		return canEMp;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })	
	public Boolean validateCandidateGlobalEducationName(Long candidateId, String candidateEducation) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		boolean flag = false;
		String query = "from CandidateEducation cEmp where cEmp.name = :candidateEducation and cEmp.candidateGlobalId.id = :candidateId and cEmp.status ='1' ";
		List<CandidateEducation> educationList = session.createQuery(query).setLong("candidateId", candidateId).setString("candidateEducation", candidateEducation).list();
		if (educationList != null && educationList.size() > 0) {
			flag = true;
			return flag;
		}
		else {
			return flag;
		}
	}
}
