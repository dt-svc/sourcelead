package com.bis.sourcelead.dao.impl;

import java.text.MessageFormat;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.CandidateCertificationsDao;
import com.bis.sourcelead.entity.CandidateCertifications;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
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

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<CandidateCertifications> getCandidateCertificationListByCandidateIdAndOrgLocationID(Long candidateId,
			Long orgLocationId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from CandidateCertifications cc where cc.orgLocationId.id =:orgLocationId and cc.candidateId.id =:candidateId";  
		List<CandidateCertifications> candidateCertifications = session.createQuery(query).setLong("candidateId", candidateId).setLong("orgLocationId", orgLocationId).list();
		return candidateCertifications;	
	}
}

