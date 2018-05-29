package com.bis.sourcelead.service;

import java.util.List;

import com.bis.sourcelead.dto.OrgLicenseSubscriptionDTO;
import com.bis.sourcelead.entity.OrgLicenseSubscription;
import com.bis.sourcelead.entity.User;

public interface OrgLicenseSubscriptionService {

	OrgLicenseSubscription saveOrgLicenseSubscription(OrgLicenseSubscription orgLicenseSubscription) throws Exception;
	
	OrgLicenseSubscription getOrgLicenseSubscriptionById(Long id) throws Exception;
	
	OrgLicenseSubscription getOrgLicenseSubscriptionByOrgIdAndCountry(Long orgId,String country) throws Exception;
	
	void registerOrgLicenseSubscription(OrgLicenseSubscriptionDTO orgLicenseSubscriptionDTO,User loginUser) throws Exception;
	
	List<OrgLicenseSubscription> getOrgLicenseSubscriptionByOrgId(Long orgId) throws Exception;
	
	void upgradeOrgLicenseSubscription(OrgLicenseSubscriptionDTO orgLicenseSubscriptionDTO,User loginUser,Long orgId) throws Exception;
	
	OrgLicenseSubscription getOrgLicenseSubscriptionByUUID(String uuid) throws Exception;
	
	List<String> getOrgLicenseSubscribedCountriesByOrgId(Long orgId) throws Exception;
	
	List<String> getOrgLicenseSubscribedAndRegisteredCountriesByOrgId(Long orgId) throws Exception;
	
	void upgradeLicenseSubscription(OrgLicenseSubscriptionDTO orgLicenseSubscriptionDTO,User loginUser,Long orgId,String subscriptionUUID) throws Exception;
	
	
}
