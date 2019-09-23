package com.bis.sourcelead.dao;

import java.util.List;

import com.bis.sourcelead.entity.OrgLicenseSubscriptionTypes;

public interface OrgLicenseSubscriptionTypesDao {
	
	public List<OrgLicenseSubscriptionTypes> getLicenseSubscriptionTypesByCategory(String category) throws Exception;
	
	OrgLicenseSubscriptionTypes getOrgLicenseSubscriptionTypesById(Long id) throws Exception;

	public List<OrgLicenseSubscriptionTypes> getUpgradeLicenseSubscriptionTypesByCategoryAndSubscriptionTypeOrderId(String category,Integer id) throws Exception;
}
