package com.bis.sourcelead.dao;

import com.bis.sourcelead.entity.OrgLicenseSubscriptionPrice;

public interface OrgLicenseSubscriptionPriceDao {

	OrgLicenseSubscriptionPrice getOrgLicenseSubscriptionPriceBySubscriptionTypeAndUserType(Long subscriptionType,Long userSubscriptionType) throws Exception;
}
