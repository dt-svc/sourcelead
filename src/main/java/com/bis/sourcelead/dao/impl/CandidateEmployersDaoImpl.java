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

import com.bis.sourcelead.dao.CandidateEmployersDao;
import com.bis.sourcelead.entity.CandidateEmployers;
import com.bis.sourcelead.entity.Projects;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;

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
	
	
	protected boolean isNullOrEmpty(String str) {
		return (str == null || "".equals(str.trim()));
	}
	
	private static final Logger logger = LoggerFactory.getLogger(CandidateEmployersDaoImpl.class);
	
	/**
	 * @author Ajith Matta
	 * 
	 * This Method is for save the candidate employers by json 
	 * 
	 * @param candidateEmployers
	 * 
	 * @return Candidateemployers
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
	public List<CandidateEmployers> getCandidatesEmployersListByCandidateIdAndOrgLocationID(Long candidateId, Long orgLocationId)
			throws Exception {

		Session session = this.sessionFactory.getCurrentSession();
		String query = "from CandidateEmployers candidateEmployers  where candidateEmployers.orgLocationId.id =:orgLocationId and candidateEmployers.candidateId.id =:candidateId and  candidateEmployers.status ='ACTIVE'";  
		List<CandidateEmployers> candidateList = session.createQuery(query).setLong("orgLocationId", orgLocationId).setLong("candidateId", candidateId).setCacheable(true).list();
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
		String query = "from CandidateEmployers  cEmp left join fetch cEmp.candidateId where cEmp.id = :candidateEmployersId and cEmp.candidateId.id = :candidateId ";
		List<CandidateEmployers> employersList =  session.createQuery(query.toString()).setLong("candidateEmployersId", candidateEmployersId).setLong("candidateId", candidateId)
				.setCacheable(true).list();
		

		CandidateEmployers canEMp = null;
		if (employersList != null && employersList.size() > 0) {
			canEMp = (CandidateEmployers) employersList.iterator().next();
		}
		return canEMp;

	}


}
