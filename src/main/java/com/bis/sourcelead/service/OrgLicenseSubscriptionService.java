package com.bis.sourcelead.service;

import com.bis.sourcelead.dto.OrgLicenseSubscriptionDTO;
import com.bis.sourcelead.entity.OrgLicenseSubscription;
import com.bis.sourcelead.entity.User;

public interface OrgLicenseSubscriptionService {

	OrgLicenseSubscription saveOrgLicenseSubscription(OrgLicenseSubscription orgLicenseSubscription) throws Exception;
	
	OrgLicenseSubscription getOrgLicenseSubscriptionById(Long id) throws Exception;
	
	OrgLicenseSubscription getOrgLicenseSubscriptionByOrgIdAndCountry(Long orgId,String country) throws Exception;
	
	void registerOrgLicenseSubscription(OrgLicenseSubscriptionDTO orgLicenseSubscriptionDTO,User loginUser) throws Exception;
}
