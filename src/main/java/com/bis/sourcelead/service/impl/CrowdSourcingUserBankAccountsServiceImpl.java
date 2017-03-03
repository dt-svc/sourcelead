package com.bis.sourcelead.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.CrowdSourcingUserBankAccountsDao;
import com.bis.sourcelead.entity.CrowdSourcingUserBankAccounts;
import com.bis.sourcelead.service.CrowdSourcingUserBankAccountsService;

@Service
public class CrowdSourcingUserBankAccountsServiceImpl implements CrowdSourcingUserBankAccountsService{

	@Autowired
	CrowdSourcingUserBankAccountsDao crowdSourcingUserBankAccountsDao;
	
	
	@Override
	public CrowdSourcingUserBankAccounts addUserBankAccounts(
			CrowdSourcingUserBankAccounts crowdSourcingUserBankAccounts) throws Exception {
		return crowdSourcingUserBankAccountsDao.addUserBankAccounts(crowdSourcingUserBankAccounts);
	}
	
	
	@Override
	public CrowdSourcingUserBankAccounts getUserBankAccountByUserId(Long userId) throws Exception {
		return crowdSourcingUserBankAccountsDao.getUserBankAccountByUserId(userId);
	}


	

}
