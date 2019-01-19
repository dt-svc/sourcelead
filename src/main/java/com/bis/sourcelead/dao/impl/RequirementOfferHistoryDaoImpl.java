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

import com.bis.sourcelead.dao.RequirementDao;
import com.bis.sourcelead.dao.RequirementOfferHistoryDao;
import com.bis.sourcelead.entity.RequirementOfferHistory;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;

@Repository
public class RequirementOfferHistoryDaoImpl implements RequirementOfferHistoryDao{
	
	private static final Logger logger = LoggerFactory.getLogger(RequirementOfferHistoryDaoImpl.class);
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	RequirementDao requirementDao;
	
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
	public RequirementOfferHistory storeRequirementOfferHistory(
			RequirementOfferHistory requirementOfferHistory) throws Exception {
		logger.debug("Adding RequirementOfferHistory");
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (requirementOfferHistory != null) {
				session.saveOrUpdate(requirementOfferHistory);
			}
			logger.debug("RequirementOfferHistory Added Successfully");
		} catch (Exception exception) {
			StringBuilder message= new StringBuilder(MessageFormat.format(duplicateValue,"REQUIREMENT_OFFER_HISTORY"));
				throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		return requirementOfferHistory;
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public RequirementOfferHistory getRequirementOfferHistoryById(Long Id) {
			logger.debug("In RequirementOfferHistory getRequirementOfferHistoryById method");
			Session session = this.sessionFactory.getCurrentSession();
			return (RequirementOfferHistory) session.get(RequirementOfferHistory.class, Id);
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<RequirementOfferHistory> getRequirementInternalAndIsClientOfferssBySubmissionIdAndOrgLocationsId(
			Long submissionId, Long orgLocationsId) throws Exception {
		logger.debug("In RequirementOfferHistoryDaoImpl class getRequirementInternalAndIsClientOfferssBySubmissionIdAndOrgLocationsId method");
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from RequirementOfferHistory roh where roh.submissionId.id = :submissionId and roh.orgLocationId.id = :orgLocationsId ";
		List<RequirementOfferHistory> offersList =  session.createQuery(query).setLong("submissionId", submissionId).setLong("orgLocationsId", orgLocationsId).list();
		
		return offersList;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<RequirementOfferHistory> getAllRequirementOfferHistoriesByOfferId(Long offerId) {
		logger.debug("In RequirementOfferHistoryDaoImpl class getAllRequirementOfferHistoriesByOfferId method");
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from RequirementOfferHistory roh where roh.offerId.id = :offerId  ";
		List<RequirementOfferHistory> offersList =  session.createQuery(query).setLong("offerId", offerId).list();
		
		return offersList;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<RequirementOfferHistory> getRequirementInternalAndIsClientOffersHistoryBySubmissionIdAndOrgLocationsId(
			Long submissionId, Long orgLocationsId) throws Exception {
		logger.debug("In RequirementOfferHistoryDaoImpl class getRequirementInternalAndIsClientOffersHistoryBySubmissionIdAndOrgLocationsId method");
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from RequirementOfferHistory roh where roh.submissionId.id = :submissionId and roh.orgLocationId.id = :orgLocationsId ";
		List<RequirementOfferHistory> offersHistoryList =  session.createQuery(query).setLong("submissionId", submissionId).setLong("orgLocationsId", orgLocationsId).list();
		
		return offersHistoryList;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<RequirementOfferHistory> getCrowdSourcingOfferDetailsBySubmissionId(Long submissionId,String locationCode)
			throws Exception {
		  
		    logger.debug("In RequirementOfferHistoryDaoImpl class getCrowdSourcingOfferDetailsBySubmissionId method");
			Session session = this.sessionFactory.getCurrentSession();
			String query = "from RequirementOfferHistory roh left join fetch roh.offerId left join fetch roh.submissionId where roh.submissionId.id = :submissionId ";
			List<RequirementOfferHistory> offersList =  session.createQuery(query).setLong("submissionId", submissionId).list();
			
			return offersList;
			}

	
}
