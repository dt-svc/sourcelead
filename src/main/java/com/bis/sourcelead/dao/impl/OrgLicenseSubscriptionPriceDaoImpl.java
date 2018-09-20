package com.bis.sourcelead.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.OrgLicenseSubscriptionPriceDao;
import com.bis.sourcelead.entity.OrgLicenseSubscription;
import com.bis.sourcelead.entity.OrgLicenseSubscriptionPrice;

@Repository
public class OrgLicenseSubscriptionPriceDaoImpl implements OrgLicenseSubscriptionPriceDao{

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

	private static final Logger logger = LoggerFactory.getLogger(OrgLicenseSubscriptionPriceDaoImpl.class);

	@Override
	@Transactional
	public OrgLicenseSubscriptionPrice getOrgLicenseSubscriptionPriceBySubscriptionTypeAndUserType(
			Long subscriptionType, Long userSubscriptionType) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		List<OrgLicenseSubscriptionPrice> orgLicenseSubscriptionPrice = new ArrayList<OrgLicenseSubscriptionPrice>();
		logger.debug("getOrgLicenseSubscriptionPriceBySubscriptionTypeAndUserType start");
		String query = "from OrgLicenseSubscriptionPrice price left join fetch price.subscriptionType subType left join fetch price.userSubscriptionType userType "
				+ " where subType.id = :subscriptionType and userType.id = :userSubscriptionType ";
		orgLicenseSubscriptionPrice = session.createQuery(query).setLong( "subscriptionType", subscriptionType ).setLong("userSubscriptionType", userSubscriptionType).list();
		logger.debug("getOrgLicenseSubscriptionPriceBySubscriptionTypeAndUserType end");
		if (orgLicenseSubscriptionPrice.size() > 0) {
			return orgLicenseSubscriptionPrice.get(0);
		} else {
			return null;
		}
   	
	
	}
}
