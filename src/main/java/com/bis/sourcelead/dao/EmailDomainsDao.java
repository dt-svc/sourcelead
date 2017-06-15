package com.bis.sourcelead.dao;

import com.bis.sourcelead.entity.EmailDomains;

public interface EmailDomainsDao {
	
	EmailDomains getEmailDomainsByDomainName(String domainName) throws Exception;
}
