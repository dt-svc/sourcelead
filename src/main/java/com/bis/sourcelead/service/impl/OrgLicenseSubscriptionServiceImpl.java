package com.bis.sourcelead.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.OrgLicenseSubscriptionDao;
import com.bis.sourcelead.entity.OrgLicenseSubscription;
import com.bis.sourcelead.service.OrgLicenseSubscriptionService;

@Service
public class OrgLicenseSubscriptionServiceImpl implements OrgLicenseSubscriptionService{

	@Autowired
	OrgLicenseSubscriptionDao orgLicenseSubscriptionDao;
	
	@Override
	public OrgLicenseSubscription saveOrgLicenseSubscription(OrgLicenseSubscription orgLicenseSubscription)
			throws Exception {
		return orgLicenseSubscriptionDao.saveOrgLicenseSubscription(orgLicenseSubscription);
	}

	@Override
	public OrgLicenseSubscription getOrgLicenseSubscriptionById(Long id) throws Exception {
		return orgLicenseSubscriptionDao.getOrgLicenseSubscriptionById(id);
	}

	@Override
	public OrgLicenseSubscription getOrgLicenseSubscriptionByOrgIdAndCountry(Long orgId, String country)
			throws Exception {
		return orgLicenseSubscriptionDao.getOrgLicenseSubscriptionByOrgIdAndCountry(orgId, country);
	}

}
