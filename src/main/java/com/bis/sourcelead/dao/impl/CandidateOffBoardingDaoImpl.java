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

import com.bis.sourcelead.dao.CandidateOffBoardingDao;
import com.bis.sourcelead.entity.CandidateOffBoarding;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;

@Repository
public class CandidateOffBoardingDaoImpl implements CandidateOffBoardingDao {

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
	

	private static final Logger logger = LoggerFactory.getLogger(CandidateOffBoardingDaoImpl.class);


	@Override
	@Transactional
	public CandidateOffBoarding saveCandidateOffBoarding(CandidateOffBoarding candidateOffBoarding) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (candidateOffBoarding != null) {
				session.saveOrUpdate(candidateOffBoarding);
			}
			logger.debug("candidateOffBoarding Added Successfully");
		} catch (Exception exception) {
			 StringBuilder message= new StringBuilder(MessageFormat.format(duplicateValue,"CANDIDATE_OFF_BOARDING"));
				throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		return candidateOffBoarding;
	}


	@Override
	@Transactional
	public CandidateOffBoarding getCandidateOffBoardingById(Long Id) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		List<CandidateOffBoarding> candidateOffBoarding = session.createQuery("from CandidateOffBoarding offboarding left join fetch offboarding.userId "
				+ " where offboarding.id =:id" ).setLong( "id", Id ).setCacheable(true).list();
		if (candidateOffBoarding.size() > 0) {
			return candidateOffBoarding.get(0);
		} else {
			return null;
		}
	}


	@Override
	@Transactional
	public List<CandidateOffBoarding> getCandidateOffBoardingListByOrgLocationId(Long orgLocationId) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
	    String query = "from CandidateOffBoarding offboarding left join fetch offboarding.orgLocationId where offboarding.orgLocationId.id =:orgLocationId ";
	    List<CandidateOffBoarding> candidateOffBoardingList = session.createQuery(query.toString()).setLong("orgLocationId", orgLocationId).setCacheable(true).list();
        return candidateOffBoardingList;
	}


	@Override
	@Transactional
	public Integer getCandidateOffBoardingListCountByOrgLocationId(Long orgLocationId) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		String query ="select count(*) from CandidateOffBoarding offboarding where offboarding.orgLocationId.id =:orgLocationId ";
		List<Long> onboardCount = session.createQuery(query.toString()).setLong("orgLocationId", orgLocationId).list();
			Integer count = 0;
			if(onboardCount != null && !onboardCount.isEmpty()){
					count = onboardCount.get(0).intValue();
			}
			return count;
	}


	@Override
	@Transactional
	public CandidateOffBoarding getCandidateOffBoardingByOffboardUUID(String offboardUUID) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		List<CandidateOffBoarding> candidateOffBoarding = session.createQuery("from CandidateOffBoarding offboarding  "
				+ " where offboarding.offboardUUID =:offboardUUID" ).setString( "offboardUUID", offboardUUID ).setCacheable(true).list();
		if (candidateOffBoarding.size() > 0) {
			return candidateOffBoarding.get(0);
		} else {
			return null;
		}
	}


	@Override
	@Transactional
	public CandidateOffBoarding getCandidateOffBoardingByUserIdAndLocationId(Long offboardingUser, Long orgLocationId)
			throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		List<CandidateOffBoarding> candidateOffBoarding = session.createQuery("from CandidateOffBoarding offboarding left join fetch offboarding.userId left join fetch offboarding.orgLocationId   "
				+ " where offboarding.orgLocationId.id =:orgLocationId and offboarding.userId.id = :offboardingUser and offboarding.status = '1' " ).setLong("offboardingUser", offboardingUser).setLong( "orgLocationId", orgLocationId ).setCacheable(true).list();
		if (candidateOffBoarding.size() > 0) {
			return candidateOffBoarding.get(0);
		} else {
			return null;
		}
	}
}
