package com.bis.sourcelead.dao;

import com.bis.sourcelead.entity.OrgBankDetails;

public interface OrgBankDetailsDao {

	OrgBankDetails saveOrgBankDetails(OrgBankDetails orgBankDetails) throws Exception;
	
	OrgBankDetails getOrgBankDetailsByOrgLocationId(Long orgLocationId) throws Exception;
	
	OrgBankDetails getOrgBankDetailsById(Long id) throws Exception;
}
