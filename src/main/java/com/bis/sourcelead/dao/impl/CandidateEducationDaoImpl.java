package com.bis.sourcelead.dao.impl;

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

import com.bis.sourcelead.dao.CandidateEducationDao;
import com.bis.sourcelead.entity.CandidateEducation;

@Repository
public class CandidateEducationDaoImpl implements CandidateEducationDao{
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
	 *  This Method is for saving candidate education details
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
	public CandidateEducation storeCandidateEducation(CandidateEducation candidateEducation) throws Exception {

		logger.debug("Adding Candidate Education");
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (candidateEducation != null) {
				session.saveOrUpdate(candidateEducation);
			}
			logger.debug("Candidate Education Added Successfully");
		}catch(ConstraintViolationException exception)
			{
			
			}
		return candidateEducation;
		
	}

	
	/**
	 * This Method is used to get candidate education details 
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
	public List<CandidateEducation>   getCandidateEducationListByCandidateIdAndOrgLocationID(Long candidateId, Long orgLocationId) throws Exception{
		 
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from CandidateEducation ce where ce.orglocationId =:orgLocationId and ce.candidateId.id =:candidateId and ce.status ='1' ORDER BY ce.id DESC";  
		List<CandidateEducation> candidateEducation = session.createQuery(query).setLong("candidateId", candidateId).setLong("orgLocationId", orgLocationId).setCacheable(true).list();
		return candidateEducation;	
		
	}

	/**
	 * @author Sammeta David Raju
	 * 
	 * This Method is for getting CandidateEducation By candidateId and candidateEducationId
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
	public CandidateEducation getCandidateEducationBycandidateIdandEducationId(Long candidateId,
			Long candidateEducationId) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from CandidateEducation  cEmp left join fetch cEmp.candidateId where cEmp.id = :candidateEducationId and cEmp.candidateId.id = :candidateId ";
		List<CandidateEducation> educationList =  session.createQuery(query.toString()).setLong("candidateEducationId", candidateEducationId).setLong("candidateId", candidateId)
				.setCacheable(true).list();
		

		CandidateEducation canEMp = null;
		if (educationList != null && educationList.size() > 0) {
			canEMp = (CandidateEducation) educationList.iterator().next();
		}
		return canEMp;
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Boolean validateEducationName(Long candidateId, String candidateEducation) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		boolean flag = false;
		String query ="from CandidateEducation cEmp where cEmp.name = :candidateEducation and cEmp.candidateId.id = :candidateId and cEmp.status ='1' ";
		List<CandidateEducation> educationList = session.createQuery(query).setLong("candidateId", candidateId).setString("candidateEducation", candidateEducation).setCacheable(true).list();
		if(educationList !=null && educationList.size() >0){
			flag = true;
			return flag;
		}else{
			return flag;
		}
	}

}
