package com.bis.sourcelead.dao.impl;

import java.text.MessageFormat;
import java.util.ArrayList;
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

import com.bis.sourcelead.dao.OrgLicenseSubscriptionDao;
import com.bis.sourcelead.entity.OrgLicenseSubscription;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserRole;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;

@Repository
public class OrgLicenseSubscriptionDaoImpl implements OrgLicenseSubscriptionDao {
	
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

	private static final Logger logger = LoggerFactory.getLogger(OrgLicenseSubscriptionDaoImpl.class);

	@Override
	@Transactional
	public OrgLicenseSubscription saveOrgLicenseSubscription(OrgLicenseSubscription orgLicenseSubscription)
			throws Exception {
		logger.debug("Adding orgLocations");
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (orgLicenseSubscription != null) {
				session.saveOrUpdate(orgLicenseSubscription);
			}
			logger.debug("orgLicenseSubscription Added Successfully");
		} catch (ConstraintViolationException exception) {
			StringBuffer message= new StringBuffer(MessageFormat.format(duplicateValue,"ORG_LICENSE_SUBSCRIPTION"));
			throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		return orgLicenseSubscription;
	}

	@Override
	@Transactional
	public OrgLicenseSubscription getOrgLicenseSubscriptionById(Long id) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		List<OrgLicenseSubscription> orgLicenseSubscription = new ArrayList<OrgLicenseSubscription>();
		logger.debug("getOrgLicenseSubscriptionById start");
		String query = "from OrgLicenseSubscription subscription left join fetch subscription.org org "
				+ "left join fetch subscription.subscriptionType left join fetch subscription.userSubscriptionType left join fetch subscription.storageSubscriptionType "
				+ "where subscription.id = :id ";
		orgLicenseSubscription = session.createQuery(query).setLong( "id", id ).list();
		logger.debug("getOrgLicenseSubscriptionById end");
		if (orgLicenseSubscription.size() > 0) {
			return orgLicenseSubscription.get(0);
		} else {
			return null;
		}
   	
	}

	@Override
	@Transactional
	public OrgLicenseSubscription getOrgLicenseSubscriptionByOrgIdAndCountry(Long orgId, String country)
			throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		List<OrgLicenseSubscription> orgLicenseSubscription = new ArrayList<OrgLicenseSubscription>();
		logger.debug("getOrgLicenseSubscriptionById start");
		String query = "from OrgLicenseSubscription subscription left join fetch subscription.org org "
				+ " left join fetch subscription.subscriptionType left join fetch subscription.userSubscriptionType left join fetch subscription.storageSubscriptionType "
				+ " where org.id = :orgId and subscription.country = :country ";
		orgLicenseSubscription = session.createQuery(query).setLong( "orgId", orgId ).setString("country", country).list();
		logger.debug("getOrgLicenseSubscriptionById end");
		if (orgLicenseSubscription.size() > 0) {
			return orgLicenseSubscription.get(0);
		} else {
			return null;
		}
   	
	
	}

	@Override
	@Transactional
	public List<OrgLicenseSubscription> getOrgLicenseSubscriptionByOrgId(Long orgId) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from OrgLicenseSubscription subscription left join fetch subscription.org org "
				+ " left join fetch subscription.subscriptionType left join fetch subscription.userSubscriptionType left join fetch subscription.storageSubscriptionType  "
				+ " where org.id = :orgId order by subscription.id ASC ";
		List<OrgLicenseSubscription> orgLicenseSubscriptionList = session.createQuery(query.toString()).setLong("orgId", orgId).list();
		return orgLicenseSubscriptionList;
	}
	

	@Override
	@Transactional
	public OrgLicenseSubscription getOrgLicenseSubscriptionByUUID(String uuid) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		List<OrgLicenseSubscription> orgLicenseSubscription = new ArrayList<OrgLicenseSubscription>();
		logger.debug("getOrgLicenseSubscriptionById start");
		String query = "from OrgLicenseSubscription subscription left join fetch subscription.org org left join fetch subscription.timezone "
				+ "left join fetch subscription.subscriptionType left join fetch subscription.userSubscriptionType left join fetch subscription.storageSubscriptionType "
				+ "where subscription.uuid = :uuid ";
		orgLicenseSubscription = session.createQuery(query).setString( "uuid", uuid ).list();
		logger.debug("getOrgLicenseSubscriptionById end");
		if (orgLicenseSubscription.size() > 0) {
			return orgLicenseSubscription.get(0);
		} else {
			return null;
		}
   	
	
	}

	@Override
	@Transactional
	public List<String> getOrgLicenseSubscribedCountriesByOrgId(Long orgId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "select subscription.country from OrgLicenseSubscription subscription where subscription.org.id = :orgId order by subscription.id ASC ";
		List<String> orgLicenseSubscribedCountriesList = session.createQuery(query.toString()).setLong("orgId", orgId).list();
		return orgLicenseSubscribedCountriesList;
	}

	@Override
	@Transactional
	public List<String> getOrgLicenseSubscribedAndRegisteredCountriesByOrgId(Long orgId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "select subscription.country from OrgLicenseSubscription subscription where subscription.org.id = :orgId "
				+ " and subscription.country IN (select ol.country from OrgLocations ol where ol.org.id = :orgId ) order by subscription.id ASC ";
		List<String> orgLicenseSubscribedCountriesList = session.createQuery(query.toString()).setLong("orgId", orgId).list();
		return orgLicenseSubscribedCountriesList;
	}

	
}
