package com.bis.sourcelead.dao;

import com.bis.sourcelead.entity.OrgLocationInformationDetails;

public interface OrgLocationInformationDetailsDao {

	OrgLocationInformationDetails saveOrgLocationInformationDetails(OrgLocationInformationDetails orgLocationInformationDetails) throws Exception;
	
	OrgLocationInformationDetails getOrgLocationInformationDetailsByOrgLocationId(Long orgLocationId) throws Exception;
}
