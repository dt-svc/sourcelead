package com.bis.sourcelead.dao;

import java.util.List;

import com.bis.sourcelead.entity.OrgLicenseSubscription;

public interface OrgLicenseSubscriptionDao {

	OrgLicenseSubscription saveOrgLicenseSubscription(OrgLicenseSubscription orgLicenseSubscription) throws Exception;
	
	OrgLicenseSubscription getOrgLicenseSubscriptionById(Long id) throws Exception;
	
	OrgLicenseSubscription getOrgLicenseSubscriptionByOrgIdAndCountry(Long orgId,String country) throws Exception;
	
	List<OrgLicenseSubscription> getOrgLicenseSubscriptionByOrgId(Long orgId) throws Exception;
	
	OrgLicenseSubscription getOrgLicenseSubscriptionByUUID(String uuid) throws Exception;
	
	List<String> getOrgLicenseSubscribedCountriesByOrgId(Long orgId) throws Exception;
	
	List<String> getOrgLicenseSubscribedAndRegisteredCountriesByOrgId(Long orgId) throws Exception;
}
