package com.bis.sourcelead.service;

import com.bis.sourcelead.entity.OrgLicenseSubscriptionPrice;

public interface OrgLicenseSubscriptionPriceService {

	OrgLicenseSubscriptionPrice getOrgLicenseSubscriptionPriceBySubscriptionTypeAndUserType(Long subscriptionType,Long userSubscriptionType) throws Exception;
}
