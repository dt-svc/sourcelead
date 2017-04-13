package com.bis.sourcelead.dao.impl;

import java.text.MessageFormat;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.CandidateOnBoardingDao;
import com.bis.sourcelead.entity.CandidateOnBoarding;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;

@Repository
public class CandidateOnBoardingDaoImpl  implements CandidateOnBoardingDao{


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
	

	private static final Logger logger = LoggerFactory.getLogger(CandidateOnBoardingDaoImpl.class);


	@Override
	@Transactional
	public CandidateOnBoarding saveCandidateOnBoarding(CandidateOnBoarding candidateOnBoarding) throws Exception {
		

		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (candidateOnBoarding != null) {
				session.saveOrUpdate(candidateOnBoarding);
			}
			logger.debug("candidateOnBoarding Added Successfully");
		} catch (Exception exception) {
			 StringBuilder message= new StringBuilder(MessageFormat.format(duplicateValue,"CANDIDATE_ON_BOARDING"));
				throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		return candidateOnBoarding;
	}

}
