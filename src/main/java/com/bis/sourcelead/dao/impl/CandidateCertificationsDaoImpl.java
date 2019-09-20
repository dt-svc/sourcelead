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

import com.bis.sourcelead.dao.CandidateCertificationsDao;
import com.bis.sourcelead.entity.CandidateCertifications;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;

/**
 * @author Shivayogi
 * @date 25th August 2016
 */

@Repository
public class CandidateCertificationsDaoImpl implements CandidateCertificationsDao {

	private static final Logger logger = LoggerFactory.getLogger(CandidateCertificationsDaoImpl.class);
	
	@Autowired
	SessionFactory sessionFactory;
	
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
	 * @author Ajith
	 * 
	 * This Method is for storing  CandidateCertifications
	 * 
	 * @param candidateCertifications
	 * 
	 * @return {@link CandidateCertifications}
	 * 
	 * @exception NullPointerException
	 * 
	 */
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public CandidateCertifications storeCandidateCertifications(CandidateCertifications candidateCertifications) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		
		logger.debug("Storing CandidateCertifications");
		try {
			if (candidateCertifications != null) {
				session.saveOrUpdate(candidateCertifications);
			}
			logger.debug("candidateCertifications Stored Successfully");
		} catch (Exception exception) {
			    StringBuilder message= new StringBuilder(MessageFormat.format(duplicateValue,"CandidateCertifications"));
				throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		return candidateCertifications;
		
		
		
	}
	
	
	/**
	 * @author Sammeta David Raju
	 * 
	 * This Method is for getting CandidateCertificationList By CandidateId And OrgLocationID
	 * 
	 * @param candidateId
	 * 
	 * @param orgLocationId
	 * 
	 * @return  List<CandidateCertifications>
	 * 
	 *@exception NullPointerException 
	 */
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<CandidateCertifications> getCandidateCertificationListByCandidateIdAndOrgLocationID(Long candidateId,
			Long orgLocationId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from CandidateCertifications cc where cc.candidateId.id =:candidateId and cc.status = '1' ORDER BY cc.createdDate DESC";  
		List<CandidateCertifications> candidateCertifications = session.createQuery(query).setLong("candidateId", candidateId).list();
		return candidateCertifications;	
	}


	/**
	 * @author Sammeta David Raju
	 * 
	 * This Method is for getting CandidateCertificate By candidateId and CertificateId
	 * 
	 * @param candidateId
	 * @param candidateCertificateId
	 * 
	 * @return  {@link CandidateCertifications}
	 * 
	 *@exception NullPointerException 
	 */
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public CandidateCertifications getCandidateCertificateBycandidateIdandCertificateId(Long candidateId,
			Long candidateCertificateId) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from CandidateCertifications  cEmp left join fetch cEmp.candidateId left join fetch cEmp.orgLocationId where cEmp.id = :candidateCertificateId and cEmp.candidateId.id = :candidateId ";
		List<CandidateCertifications> certificatesList =  session.createQuery(query.toString()).setLong("candidateCertificateId", candidateCertificateId).setLong("candidateId", candidateId)
				.list();
		

		CandidateCertifications canEMp = null;
		if (certificatesList != null && certificatesList.size() > 0) {
			canEMp = (CandidateCertifications) certificatesList.iterator().next();
		}
		return canEMp;
	}


	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<CandidateCertifications> getCandidateGlobalCertificationListByCandidateIdAndOrgLocationID(
			Long candidateId, Long orgLocationId) throws Exception {

		Session session = this.sessionFactory.getCurrentSession();
		String query = "from CandidateCertifications cc where cc.candidateGlobalId.id =:candidateId and cc.status = '1' ORDER BY cc.createdDate DESC";  
		List<CandidateCertifications> candidateCertifications = session.createQuery(query).setLong("candidateId", candidateId).list();
		return candidateCertifications;	
	
	}


	@Override
	@Transactional(rollbackFor = { Exception.class })
	public CandidateCertifications getCandidateGlobalCertificateBycandidateIdandCertificateId(Long candidateId,
			Long candidateCertificateId) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from CandidateCertifications  cEmp left join fetch cEmp.candidateGlobalId left join fetch cEmp.orgLocationId where cEmp.id = :candidateCertificateId and cEmp.candidateGlobalId.id = :candidateId ";
		List<CandidateCertifications> certificatesList =  session.createQuery(query.toString()).setLong("candidateCertificateId", candidateCertificateId).setLong("candidateId", candidateId)
				.list();
		

		CandidateCertifications canEMp = null;
		if (certificatesList != null && certificatesList.size() > 0) {
			canEMp = (CandidateCertifications) certificatesList.iterator().next();
		}
		return canEMp;
	}
}

