package com.bis.sourcelead.service;

import com.bis.sourcelead.entity.OrgLicenseSubscription;

public interface OrgLicenseSubscriptionService {

	OrgLicenseSubscription saveOrgLicenseSubscription(OrgLicenseSubscription orgLicenseSubscription) throws Exception;
	
	OrgLicenseSubscription getOrgLicenseSubscriptionById(Long id) throws Exception;
	
	OrgLicenseSubscription getOrgLicenseSubscriptionByOrgIdAndCountry(Long orgId,String country) throws Exception;
}
