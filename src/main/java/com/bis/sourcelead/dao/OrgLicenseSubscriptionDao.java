package com.bis.sourcelead.dao;

import com.bis.sourcelead.entity.OrgLicenseSubscription;

public interface OrgLicenseSubscriptionDao {

	OrgLicenseSubscription saveOrgLicenseSubscription(OrgLicenseSubscription orgLicenseSubscription) throws Exception;
	
	OrgLicenseSubscription getOrgLicenseSubscriptionById(Long id) throws Exception;
	
	OrgLicenseSubscription getOrgLicenseSubscriptionByOrgIdAndCountry(Long orgId,String country) throws Exception;
}
