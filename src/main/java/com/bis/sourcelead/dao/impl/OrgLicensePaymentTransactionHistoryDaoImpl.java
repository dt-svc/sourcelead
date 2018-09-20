package com.bis.sourcelead.dao.impl;

import java.text.MessageFormat;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.OrgLicensePaymentTransactionHistoryDao;
import com.bis.sourcelead.entity.OrgLicensePaymentTransactionHistory;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;

@Repository
public class OrgLicensePaymentTransactionHistoryDaoImpl implements OrgLicensePaymentTransactionHistoryDao{
	
	
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

	private static final Logger logger = LoggerFactory.getLogger(OrgLicensePaymentTransactionHistoryDaoImpl.class);

	@Override
	@Transactional
	public OrgLicensePaymentTransactionHistory saveOrgLicensePaymentTransactionHistory(
			OrgLicensePaymentTransactionHistory orgLicensePaymentTransactionHistory) throws Exception {
		logger.debug("Adding orgLicensePaymentTransactionHistory");
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (orgLicensePaymentTransactionHistory != null) {
				session.saveOrUpdate(orgLicensePaymentTransactionHistory);
			}
			logger.debug("orgLicensePaymentTransactionHistory Added Successfully");
		} catch (ConstraintViolationException exception) {
			StringBuffer message= new StringBuffer(MessageFormat.format(duplicateValue,"ORG_LICENSE_SUBSCRIPTION"));
			throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		return orgLicensePaymentTransactionHistory;
	}

}
