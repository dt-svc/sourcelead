package com.bis.sourcelead.service;

import com.bis.sourcelead.entity.CrowdSourcingUserBankAccounts;

public interface CrowdSourcingUserBankAccountsService {

	CrowdSourcingUserBankAccounts addUserBankAccounts(CrowdSourcingUserBankAccounts crowdSourcingUserBankAccounts) throws Exception;
	
	CrowdSourcingUserBankAccounts getUserBankAccountByUserId(Long userId) throws Exception;
}
