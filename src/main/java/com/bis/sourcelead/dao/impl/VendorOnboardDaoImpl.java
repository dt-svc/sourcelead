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

import com.bis.sourcelead.dao.VendorOnboardDao;
import com.bis.sourcelead.entity.VendorOnboardTasks;
import com.bis.sourcelead.entity.VendorOnboarding;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;

@Repository
public class VendorOnboardDaoImpl implements VendorOnboardDao{
	
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
	

	private static final Logger logger = LoggerFactory.getLogger(VendorOnboardDaoImpl.class);

	@Override
	@Transactional
	public VendorOnboarding saveVendorOnboarding(VendorOnboarding vendorOnboarding) throws Exception {
	
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (vendorOnboarding != null) {
				session.saveOrUpdate(vendorOnboarding);
			}
			logger.debug("VendorOnboarding Saved Successfully");
		} catch (Exception exception) {
			 StringBuilder message= new StringBuilder(MessageFormat.format(duplicateValue,"VENDOR_ONBOARD"));
				throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		return vendorOnboarding;
	}

	@Override
	@Transactional
	public VendorOnboarding getVendorOnboardingByOrgRelationId(Long orgRelationId) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		List<VendorOnboarding> vendorOnboard = session.createQuery("from VendorOnboarding onboard left join fetch onboard.orgRelationId "
				+ " left join fetch onboard.vendorLocationId left join fetch onboard.orgLocationsId where onboard.orgRelationId.id = :orgRelationId" ).setLong( "orgRelationId", orgRelationId ).setCacheable(true).list();
		if (vendorOnboard.size() > 0) {
			return vendorOnboard.get(0);
		} else {
			return null;
		}
	
	}

	@Override
	@Transactional
	public VendorOnboarding getVendorOnboardingById(Long id) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		List<VendorOnboarding> vendorOnboard = session.createQuery("from VendorOnboarding onboard left join fetch onboard.orgRelationId left join fetch onboard.spocUserId "
				+ " left join fetch onboard.vendorLocationId left join fetch onboard.orgLocationsId where onboard.id = :id" ).setLong( "id", id ).setCacheable(true).list();
		if (vendorOnboard.size() > 0) {
			return vendorOnboard.get(0);
		} else {
			return null;
		}
	}

	@Override
	@Transactional
	public VendorOnboarding getVendorOnboardingByUUID(String onboardUUID) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		List<VendorOnboarding> vendorOnboard = session.createQuery("from VendorOnboarding onboard left join fetch onboard.orgRelationId left join fetch onboard.spocUserId "
				+ " left join fetch onboard.vendorLocationId left join fetch onboard.orgLocationsId where onboard.onboardUUID = :onboardUUID" ).setString( "onboardUUID", onboardUUID ).setCacheable(true).list();
		if (vendorOnboard.size() > 0) {
			return vendorOnboard.get(0);
		} else {
			return null;
		}
	}

}
