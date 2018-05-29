package com.bis.sourcelead.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.OrgLicensePaymentTransactionHistoryDao;
import com.bis.sourcelead.entity.OrgLicensePaymentTransactionHistory;
import com.bis.sourcelead.service.OrgLicensePaymentTransactionHistoryService;

@Service
public class OrgLicensePaymentTransactionHistoryServiceImpl implements OrgLicensePaymentTransactionHistoryService{

	@Autowired
	OrgLicensePaymentTransactionHistoryDao orgLicensePaymentTransactionHistoryDao;
	
	@Override
	public OrgLicensePaymentTransactionHistory saveOrgLicensePaymentTransactionHistory(
			OrgLicensePaymentTransactionHistory orgLicensePaymentTransactionHistory) throws Exception {
		return orgLicensePaymentTransactionHistoryDao.saveOrgLicensePaymentTransactionHistory(orgLicensePaymentTransactionHistory);
	}

}
