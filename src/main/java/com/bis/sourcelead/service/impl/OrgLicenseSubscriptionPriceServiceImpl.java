package com.bis.sourcelead.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.OrgLicenseSubscriptionPriceDao;
import com.bis.sourcelead.entity.OrgLicenseSubscriptionPrice;
import com.bis.sourcelead.service.OrgLicenseSubscriptionPriceService;

@Service
public class OrgLicenseSubscriptionPriceServiceImpl implements OrgLicenseSubscriptionPriceService{

	@Autowired
	OrgLicenseSubscriptionPriceDao orgLicenseSubscriptionPriceDao;
	
	@Override
	public OrgLicenseSubscriptionPrice getOrgLicenseSubscriptionPriceBySubscriptionTypeAndUserType(
			Long subscriptionType, Long userSubscriptionType) throws Exception {
		return orgLicenseSubscriptionPriceDao.getOrgLicenseSubscriptionPriceBySubscriptionTypeAndUserType(subscriptionType, userSubscriptionType);
	}

}
