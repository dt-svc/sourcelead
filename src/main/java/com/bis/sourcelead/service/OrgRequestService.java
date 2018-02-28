package com.bis.sourcelead.service;

import com.bis.sourcelead.entity.OrgRequest;

public interface OrgRequestService {

	OrgRequest saveOrgRequest(OrgRequest orgRequest) throws Exception;

	Boolean  validateOrgRequestCompanyNameByName(String companyName)throws Exception;

	void senEmailOrgRequestToSourcelead(OrgRequest orgRequest)throws Exception;
}
