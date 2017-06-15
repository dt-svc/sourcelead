package com.bis.sourcelead.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.EmailDomainsDao;
import com.bis.sourcelead.entity.EmailDomains;
import com.bis.sourcelead.service.EmailDomainsService;

@Service
public class EmailDomainsServiceImpl implements EmailDomainsService {

	@Autowired
	EmailDomainsDao emailDomainsDao;
	
	@Override
	public EmailDomains getEmailDomainsByDomainName(String domainName) throws Exception
	{
		return emailDomainsDao.getEmailDomainsByDomainName(domainName);
	}
	
}
