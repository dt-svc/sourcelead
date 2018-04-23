package com.bis.sourcelead.service;

import java.util.List;

import com.bis.sourcelead.entity.OrgLicenseSubscriptionTypes;

public interface OrgLicenseSubscriptionTypesService {

	public List<OrgLicenseSubscriptionTypes> getLicenseSubscriptionTypesByCategory(String category) throws Exception;
	
	OrgLicenseSubscriptionTypes getOrgLicenseSubscriptionTypesById(Long id) throws Exception;
	
	public List<OrgLicenseSubscriptionTypes> getUpgradeLicenseSubscriptionTypesByCategoryAndSubscriptionTypeOrderId(String category,Integer id) throws Exception;
}
