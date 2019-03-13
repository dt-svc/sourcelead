package com.bis.sourcelead.service;

import com.bis.sourcelead.entity.OrgLocationInformationDetails;

public interface OrgLocationInformationDetailsService {

	OrgLocationInformationDetails saveOrgLocationInformationDetails(OrgLocationInformationDetails orgLocationInformationDetails) throws Exception;
	
	OrgLocationInformationDetails getOrgLocationInformationDetailsByOrgLocationId(Long orgLocationId) throws Exception;
}
