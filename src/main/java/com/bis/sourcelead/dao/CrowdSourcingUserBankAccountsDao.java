package com.bis.sourcelead.dao;

import com.bis.sourcelead.entity.CrowdSourcingUserBankAccounts;

public interface CrowdSourcingUserBankAccountsDao {
	
	CrowdSourcingUserBankAccounts addUserBankAccounts(CrowdSourcingUserBankAccounts crowdSourcingUserBankAccounts) throws Exception;
	
	CrowdSourcingUserBankAccounts getUserBankAccountByUserId(Long userId) throws Exception;

}
