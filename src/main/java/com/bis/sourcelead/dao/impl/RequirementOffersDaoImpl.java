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

import com.bis.sourcelead.dao.RequirementOffersDao;
import com.bis.sourcelead.entity.RequirementInterview;
import com.bis.sourcelead.entity.RequirementOffers;
import com.bis.sourcelead.entity.RequirementSubmission;

@Repository
public class RequirementOffersDaoImpl implements RequirementOffersDao{
	
	private static final Logger logger = LoggerFactory.getLogger(RequirementOffersDaoImpl.class);
	
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

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public RequirementOffers storeRequirementOffers(RequirementOffers requirementOffers) throws Exception {
		
		logger.debug("Adding Requirement Offers ");
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (requirementOffers != null) {
				session.saveOrUpdate(requirementOffers);
			}
			logger.debug("Requirement Offers Added Successfully");
		}catch(ConstraintViolationException exception)
			{
			
			}
		return requirementOffers;
		
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public RequirementOffers getRequirementOfferSubmissionById(Long submissionId) throws Exception {
	

		Session session = this.sessionFactory.getCurrentSession();
		String query = "from RequirementOffers rs left join fetch rs.submissionId  sub left join fetch sub.sourceSubmission    where rs.submissionId = :submissionId";
		List<RequirementOffers> requirementOffersList =  session.createQuery(query.toString()).setLong("submissionId", submissionId).setCacheable(true).list();
		
		RequirementOffers requirementOffers = null;
		
		if (requirementOffersList != null && requirementOffersList.size() > 0) {
			requirementOffers = (RequirementOffers) requirementOffersList.iterator().next();
		}
		return requirementOffers;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public RequirementOffers getRequirementOfferId(Long offerId) throws Exception {

		Session session = this.sessionFactory.getCurrentSession();
		String query = "from RequirementOffers rs left join fetch rs.submissionId where rs.id = :offerId ";
		List<RequirementOffers> requirementOffersList =  session.createQuery(query.toString()).setLong("offerId", offerId).setCacheable(true).list();
		
		RequirementOffers requirementOffers = null;
		
		if (requirementOffersList != null && requirementOffersList.size() > 0) {
			requirementOffers = (RequirementOffers) requirementOffersList.iterator().next();
		}
		return requirementOffers;
		
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<RequirementOffers> getRequirementOfferSubmissionByIdAndOrgLocationId(Long submissionId, Long orgLocationId)
			throws Exception {
		logger.debug("In RequirementInterviewDaoImpl class getRequirementInterviewsBySubmissionIdAndOrgLocationsId method");
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from RequirementOffers requirementOffer where requirementOffer.submissionId.id = :submissionId and requirementOffer.submissionId.orgLocations.id = :orgLocationId ";
		List<RequirementOffers> requirementOffersList =  session.createQuery(query).setLong("submissionId", submissionId).setLong("orgLocationId", orgLocationId).setCacheable(true).list();
		
		return requirementOffersList;
	}
}
