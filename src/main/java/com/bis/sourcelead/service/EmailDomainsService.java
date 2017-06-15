package com.bis.sourcelead.service;

import com.bis.sourcelead.entity.EmailDomains;

public interface EmailDomainsService {
	
	EmailDomains getEmailDomainsByDomainName(String domainName) throws Exception;
	
}
