package com.bis.sourcelead.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.OrgLicenseSubscriptionTypesDao;
import com.bis.sourcelead.entity.OrgLicenseSubscriptionTypes;
import com.bis.sourcelead.service.OrgLicenseSubscriptionTypesService;

@Service
public class OrgLicenseSubscriptionTypesServiceImpl implements OrgLicenseSubscriptionTypesService{

	@Autowired
	OrgLicenseSubscriptionTypesDao orgLicenseSubscriptionTypesDao;
	
	@Override
	public List<OrgLicenseSubscriptionTypes> getLicenseSubscriptionTypesByCategory(String category) throws Exception {
		return orgLicenseSubscriptionTypesDao.getLicenseSubscriptionTypesByCategory(category);
	}

	@Override
	public OrgLicenseSubscriptionTypes getOrgLicenseSubscriptionTypesById(Long id) throws Exception {
		return orgLicenseSubscriptionTypesDao.getOrgLicenseSubscriptionTypesById(id);
	}

	@Override
	public List<OrgLicenseSubscriptionTypes> getUpgradeLicenseSubscriptionTypesByCategoryAndSubscriptionTypeOrderId(
			String category,Integer id) throws Exception {
		return orgLicenseSubscriptionTypesDao.getUpgradeLicenseSubscriptionTypesByCategoryAndSubscriptionTypeOrderId(category, id);
	}

}
