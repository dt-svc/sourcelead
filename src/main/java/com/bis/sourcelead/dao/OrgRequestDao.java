package com.bis.sourcelead.dao;

import com.bis.sourcelead.entity.OrgRequest;

public interface OrgRequestDao {
 
	OrgRequest saveOrgRequest(OrgRequest orgRequest) throws Exception;
	
	Boolean validateOrgRequestCompanyNameByName(String companyName)throws Exception;
}
