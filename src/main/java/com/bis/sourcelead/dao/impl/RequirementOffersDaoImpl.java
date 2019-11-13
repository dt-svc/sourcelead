package com.bis.sourcelead.dao.impl;

import java.util.Date;
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
		String query = "from RequirementOffers rs left join fetch rs.submissionId  sub left join fetch sub.sourceSubmission where rs.submissionId = :submissionId and rs.offerStatus = 'OFFERACCEPTED' ";
		List<RequirementOffers> requirementOffersList =  session.createQuery(query.toString()).setLong("submissionId", submissionId).list();
		
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
		String query = "from RequirementOffers rs left join fetch rs.offerLetterTemplate where rs.id = :offerId ";
		List<RequirementOffers> requirementOffersList =  session.createQuery(query.toString()).setLong("offerId", offerId).list();
		
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
		List<RequirementOffers> requirementOffersList =  session.createQuery(query).setLong("submissionId", submissionId).setLong("orgLocationId", orgLocationId).list();
		
		return requirementOffersList;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<RequirementOffers> getOfferAcceptedCandidatesByOrgLocationId(Long orgLocationId) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from RequirementOffers requirementOffer left join fetch requirementOffer.submissionId submission left join fetch submission.orgLocations left join fetch submission.candidateResumes left join fetch submission.requirement "
				+ " left join fetch submission.candidate candidate left join fetch candidate.consultantUser consultantUser where requirementOffer.submissionId.orgLocations.id = :orgLocationId "
				+ " and (requirementOffer.submissionId.submissionStatus = 'OFFERACCEPTED' or submission.submissionStatus ='joined' )and requirementOffer.offerStatus = 'OFFERACCEPTED' and consultantUser.id IS NULL ";
		List<RequirementOffers> requirementOffersList =  session.createQuery(query).setLong("orgLocationId", orgLocationId).list();
		
		return requirementOffersList;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public RequirementOffers getRequirementOffersByUUID(String offerUUID) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from RequirementOffers rs left join fetch rs.offerLetterTemplate left join fetch rs.submissionId submission left join fetch submission.requirement where rs.offerUUID = :offerUUID ";
		List<RequirementOffers> requirementOffersList =  session.createQuery(query.toString()).setString("offerUUID", offerUUID).list();
		
		RequirementOffers requirementOffers = null;
		
		if (requirementOffersList != null && requirementOffersList.size() > 0) {
			requirementOffers = (RequirementOffers) requirementOffersList.iterator().next();
		}
		return requirementOffers;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<RequirementOffers> getOfferAcceptedVendorCandidatesByOrgLocationId(Long locationId)
	{
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from RequirementOffers rs left join fetch rs.submissionId submission left join fetch submission.requirement left join fetch submission.candidate candidate where submission.orgLocations.id =:locationId and submission.sourceSubmission <> NULL and submission.submissionStatus = 'OFFERACCEPTED' order by submission.updatedDate DESC";
		List<RequirementOffers> requirementOffersList =  session.createQuery(query.toString()).setLong("locationId", locationId).setMaxResults(10).list();
		return requirementOffersList;
	}

	@Override
	@Transactional
	public RequirementOffers getOnboardRequirementOfferId(Long offerId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from RequirementOffers rs left join fetch rs.submissionId submission left join fetch submission.candidate candidate "
				+ "left join fetch candidate.orgRelationId left join fetch candidate.spocId where rs.id = :offerId ";
		List<RequirementOffers> requirementOffersList =  session.createQuery(query.toString()).setLong("offerId", offerId).list();
		
		RequirementOffers requirementOffers = null;
		
		if (requirementOffersList != null && requirementOffersList.size() > 0) {
			requirementOffers = (RequirementOffers) requirementOffersList.iterator().next();
		}
		return requirementOffers;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<RequirementOffers> getOfferAcceptedCandidatesByOrgLocationIdAndDateOfJoin(Long orgLocationId, Date joiningDate) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		java.sql.Date sqlJoiningDate = new java.sql.Date(joiningDate.getTime());
		String query = "from RequirementOffers requirementOffer left join fetch requirementOffer.submissionId submission left join fetch submission.orgLocations left join fetch submission.requirement "
				+ " left join fetch submission.candidate candidate where requirementOffer.submissionId.orgLocations.id = :orgLocationId "
				+ " and requirementOffer.submissionId.submissionStatus = 'OFFERACCEPTED' and date(requirementOffer.dateOfJoining) =:joiningDate and requirementOffer.status = '1' ";
		List<RequirementOffers> requirementOffersList =  session.createQuery(query).setLong("orgLocationId", orgLocationId).setDate("joiningDate", sqlJoiningDate).list();
		
		return requirementOffersList;
	}
}
