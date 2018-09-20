package com.bis.sourcelead.dao.impl;

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

import com.bis.sourcelead.dao.EmailDomainsDao;
import com.bis.sourcelead.entity.EmailDomains;

@Repository
public class EmailDomainsDaoImpl implements EmailDomainsDao{
	
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

	private static final Logger logger = LoggerFactory.getLogger(EmailDomainsDaoImpl.class);

	@Override
	@Transactional
	public EmailDomains getEmailDomainsByDomainName(String domainName) throws Exception
	{
		Session session = this.sessionFactory.getCurrentSession();
		List<EmailDomains> emailDomains = new ArrayList<EmailDomains>();
		logger.debug("getEmailDomainsByDomainName start");
		emailDomains = session.createQuery("from EmailDomains emailDomains where emailDomains.domainName =:domainName").setString("domainName", domainName).list();
		logger.debug("getEmailDomainsByDomainName end");
		if (emailDomains.size() > 0) {
			return emailDomains.get(0);
		} else {
			return null;
		}
	}
}
