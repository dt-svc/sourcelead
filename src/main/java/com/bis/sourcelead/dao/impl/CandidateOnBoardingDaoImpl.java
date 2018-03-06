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


	@Override
	@Transactional
	public List<CandidateOnBoarding> getCandidateOnBoardingListByOrgLocationId(Long orgLocationId) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
	    String query = "from CandidateOnBoarding onboarding left join fetch onboarding.orgLocationId where onboarding.orgLocationId.id =:orgLocationId ";
	    List<CandidateOnBoarding> candidateOnBoardingList = session.createQuery(query.toString()).setLong("orgLocationId", orgLocationId).list();
        return candidateOnBoardingList;
	}


	@Override
	@Transactional
	public CandidateOnBoarding getCandidateOnBoardingByUUID(String onboardCandidateUUID) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		List<CandidateOnBoarding> candidateOnBoarding = session.createQuery("from CandidateOnBoarding onboarding left join fetch onboarding.candidateId left join fetch onboarding.offerId offer "
				+ " left join fetch offer.submissionId submission left join fetch submission.requirement left join fetch onboarding.departmentId left join fetch onboarding.roleId left join fetch onboarding.managerUserId left join fetch onboarding.teamId where onboarding.onboardCandidateUUID =:onboardCandidateUUID" ).setString( "onboardCandidateUUID", onboardCandidateUUID ).list();
		if (candidateOnBoarding.size() > 0) {
			return candidateOnBoarding.get(0);
		} else {
			return null;
		}
	}


	@Override
	@Transactional
	public Integer getCandidateOnBoardingListCountByOrgLocationId(Long orgLocationId) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		String query ="select count(*) from CandidateOnBoarding onboarding where onboarding.orgLocationId.id =:orgLocationId ";
		List<Long> onboardCount = session.createQuery(query.toString()).setLong("orgLocationId", orgLocationId).list();
			Integer count = 0;
			if(onboardCount != null && !onboardCount.isEmpty()){
					count = onboardCount.get(0).intValue();
			}
			return count;
	}


	@Override
	@Transactional
	public CandidateOnBoarding getCandidateOnBoardingById(Long Id) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		List<CandidateOnBoarding> candidateOnBoarding = session.createQuery("from CandidateOnBoarding onboarding left join fetch onboarding.roleId left join fetch onboarding.managerUserId "
				+ " left join fetch onboarding.teamId where onboarding.id =:id" ).setLong( "id", Id ).list();
		if (candidateOnBoarding.size() > 0) {
			return candidateOnBoarding.get(0);
		} else {
			return null;
		}
	}


	@Override
	@Transactional
	public CandidateOnBoarding getCandidateOnBoardingByEmailAndLocationId(String email, Long orgLocationId)
			throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		List<CandidateOnBoarding> candidateOnBoarding = session.createQuery("from CandidateOnBoarding onboarding left join fetch onboarding.orgLocationId  "
				+ " where onboarding.orgLocationId.id =:orgLocationId and onboarding.companyEmail = :email" ).setString("email", email).setLong( "orgLocationId", orgLocationId ).list();
		if (candidateOnBoarding.size() > 0) {
			return candidateOnBoarding.get(0);
		} else {
			return null;
		}
	}


	@Override
	@Transactional
	public CandidateOnBoarding getCandidateOnBoardingByUserIdAndLocationId(Long onboardingUser, Long orgLocationId)
			throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		List<CandidateOnBoarding> candidateOnBoarding = session.createQuery("from CandidateOnBoarding onboarding left join fetch onboarding.onboardingUser left join fetch onboarding.orgLocationId left join fetch onboarding.teamId  "
				+ " where onboarding.orgLocationId.id =:orgLocationId and onboarding.onboardingUser.id = :onboardingUser" ).setLong("onboardingUser", onboardingUser).setLong( "orgLocationId", orgLocationId ).list();
		if (candidateOnBoarding.size() > 0) {
			return candidateOnBoarding.get(0);
		} else {
			return null;
		}
	}


	@Override
	@Transactional
	public CandidateOnBoarding getCandidateOnBoardingByCandidateIdAndOfferId(Long candidateId ,Long offerId) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		List<CandidateOnBoarding> candidateOnBoarding = session.createQuery("from CandidateOnBoarding onboarding left join fetch onboarding.candidateId left join fetch onboarding.offerId offer "
				+ "  where onboarding.candidateId.id = :candidateId and onboarding.offerId.id = :offerId" ).setLong( "candidateId", candidateId ).setLong("offerId", offerId).list();
		if (candidateOnBoarding.size() > 0) {
			return candidateOnBoarding.get(0);
		} else {
			return null;
		}
	}


	@Override
	@Transactional
	public CandidateOnBoarding getCandidateOnBoardingByPersonalEmailAndLocationId(String personalEmail,
			Long orgLocationId) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		List<CandidateOnBoarding> candidateOnBoarding = session.createQuery("from CandidateOnBoarding onboarding left join fetch onboarding.orgLocationId  "
				+ " where onboarding.orgLocationId.id =:orgLocationId and onboarding.personalEmail = :personalEmail" ).setString("personalEmail", personalEmail).setLong( "orgLocationId", orgLocationId ).list();
		if (candidateOnBoarding.size() > 0) {
			return candidateOnBoarding.get(0);
		} else {
			return null;
		}
	}


	@Override
	@Transactional
	public CandidateOnBoarding getCandidateOnBoardingByCandidateIdAndLocationId(Long candidateId, Long orgLocationId)
			throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		List<CandidateOnBoarding> candidateOnBoarding = session.createQuery("from CandidateOnBoarding onboarding left join fetch onboarding.orgLocationId left join fetch onboarding.candidateId "
				+ " where onboarding.orgLocationId.id =:orgLocationId and onboarding.candidateId.id = :candidateId " ).setLong("candidateId", candidateId).setLong( "orgLocationId", orgLocationId ).list();
		if (candidateOnBoarding.size() > 0) {
			return candidateOnBoarding.get(0);
		} else {
			return null;
		}
	}

}
