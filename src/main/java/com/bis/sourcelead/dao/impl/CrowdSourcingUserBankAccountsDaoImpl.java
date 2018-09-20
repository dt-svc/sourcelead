package com.bis.sourcelead.dao.impl;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.CrowdSourcingUserBankAccountsDao;
import com.bis.sourcelead.entity.CrowdSourcingUserBankAccounts;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;

@Repository
public class CrowdSourcingUserBankAccountsDaoImpl implements CrowdSourcingUserBankAccountsDao {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Value("${SourceLead.100}")
	private String notFound;
	@Value("${SourceLead.101}")
	private String noRecords;
	@Value("${SourceLead.102}")
	private String statusUpdate;
	@Value("${SourceLead.103}")
	private String statusDelete;
	@Value("${SourceLead.106}")
	private String duplicateValue;
	
	private static final Logger logger = LoggerFactory.getLogger(CrowdSourcingUserBankAccountsDaoImpl.class);
	
	protected boolean isNullOrEmpty(String str) {
		return (str == null || "".equals(str.trim()));
	}
	
	
	@Override
	@Transactional
	public CrowdSourcingUserBankAccounts addUserBankAccounts(
			CrowdSourcingUserBankAccounts crowdSourcingUserBankAccounts) throws Exception {
		logger.debug("Adding addUserBankAccounts");
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (crowdSourcingUserBankAccounts != null) {
				session.saveOrUpdate(crowdSourcingUserBankAccounts);
			}
			logger.debug("CrowdSourcingUserBankAccounts Added Successfully");
		} catch (Exception exception) {
			 StringBuilder message= new StringBuilder(MessageFormat.format(duplicateValue,"REQUIREMENT_REFER"));
				throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		return crowdSourcingUserBankAccounts;
	}
	
	

	@Override
	@Transactional
	public CrowdSourcingUserBankAccounts getUserBankAccountByUserId(Long userId) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		List<CrowdSourcingUserBankAccounts> bankAccount = new ArrayList<CrowdSourcingUserBankAccounts>();
		logger.debug("getUserBankAccountByUserId start");
		bankAccount = session.createQuery("from CrowdSourcingUserBankAccounts account left join fetch account.accountUserId user  "
				+ "where user.id = :userId" ).setLong("userId", userId).list();
		logger.debug("getUserBankAccountByUserId end");
		if (bankAccount.size() > 0) {
			return bankAccount.get(0);
		} else {
			return null;
		}
   	}

	
}
