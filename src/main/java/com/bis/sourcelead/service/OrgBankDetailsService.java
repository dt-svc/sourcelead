package com.bis.sourcelead.service;

import com.bis.sourcelead.entity.OrgBankDetails;

public interface OrgBankDetailsService {

	OrgBankDetails saveOrgBankDetails(OrgBankDetails orgBankDetails) throws Exception;
	
	OrgBankDetails getOrgBankDetailsByOrgLocationId(Long orgLocationId) throws Exception;
	
	OrgBankDetails getOrgBankDetailsById(Long id) throws Exception;
	
	
}
