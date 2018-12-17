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

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.OrgLicenseSubscriptionTypesDao;
import com.bis.sourcelead.entity.OrgLicenseSubscriptionTypes;
import com.bis.sourcelead.entity.User;

@Repository
public class OrgLicenseSubscriptionTypesDaoImpl implements OrgLicenseSubscriptionTypesDao{
	
	
private static final Logger logger = LoggerFactory.getLogger(OrgLicenseSubscriptionTypesDaoImpl.class);
	
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
	@Transactional
	public List<OrgLicenseSubscriptionTypes> getLicenseSubscriptionTypesByCategory(String category) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = null;
		if(category.equalsIgnoreCase(Constants.SUBSCRIPTION_TYPE)) {
			query = "from OrgLicenseSubscriptionTypes subTypes where subTypes.category = :category and subTypes.uniqueKey <> 'Guest' and subTypes.uniqueKey <> 'Unlimited' ";
		}else {
			query = "from OrgLicenseSubscriptionTypes subTypes where subTypes.category = :category  ";
		}
	    
	    List<OrgLicenseSubscriptionTypes> subscriptionTypesList = session.createQuery(query.toString()).setString("category", category).list();
      
	    return subscriptionTypesList;
	}

	@Override
	@Transactional
	public OrgLicenseSubscriptionTypes getOrgLicenseSubscriptionTypesById(Long id) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		List<OrgLicenseSubscriptionTypes> orgLicenseSubscriptionTypes = new ArrayList<OrgLicenseSubscriptionTypes>();
		logger.debug("getOrgLicenseSubscriptionTypesById start");
		orgLicenseSubscriptionTypes = session.createQuery("from OrgLicenseSubscriptionTypes subTypes where subTypes.id = :id" ).setLong( "id", id ).list();
		logger.debug("getOrgLicenseSubscriptionTypesById end");
		if (orgLicenseSubscriptionTypes.size() > 0) {
			return orgLicenseSubscriptionTypes.get(0);
		} else {
			return null;
		}
   	}

	@Override
	@Transactional
	public List<OrgLicenseSubscriptionTypes> getUpgradeLicenseSubscriptionTypesByCategoryAndSubscriptionTypeOrderId(
			String category, Integer id) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = null;
		if(category.equalsIgnoreCase(Constants.SUBSCRIPTION_TYPE)) {
			query = "from OrgLicenseSubscriptionTypes subTypes where subTypes.category = :category "
					+ " and subTypes.uniqueKey <> 'Guest' and subTypes.uniqueKey <> 'Unlimited' and subTypes.subscriptionTypeOrder >= :id and subTypes.status = '1' ";
		}else {
			query = "from OrgLicenseSubscriptionTypes subTypes where subTypes.category = :category and subTypes.subscriptionTypeOrder >= :id and subTypes.status = '1' ";
		}
	    
	    List<OrgLicenseSubscriptionTypes> subscriptionTypesList = session.createQuery(query.toString()).setString("category", category).setInteger("id", id).list();
      
	    return subscriptionTypesList;
	}

}
