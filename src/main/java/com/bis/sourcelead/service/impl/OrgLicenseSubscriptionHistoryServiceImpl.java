package com.bis.sourcelead.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.OrgLicenseSubscriptionHistoryDao;
import com.bis.sourcelead.entity.OrgLicenseSubscriptionHistory;
import com.bis.sourcelead.service.OrgLicenseSubscriptionHistoryService;

@Service
public class OrgLicenseSubscriptionHistoryServiceImpl implements OrgLicenseSubscriptionHistoryService{

	@Autowired
	OrgLicenseSubscriptionHistoryDao orgLicenseSubscriptionHistoryDao;
	
	@Override
	public OrgLicenseSubscriptionHistory saveOrgLicenseSubscriptionHistory(
			OrgLicenseSubscriptionHistory orgLicenseSubscriptionHistory) throws Exception {
		return orgLicenseSubscriptionHistoryDao.saveOrgLicenseSubscriptionHistory(orgLicenseSubscriptionHistory);
	}

}
