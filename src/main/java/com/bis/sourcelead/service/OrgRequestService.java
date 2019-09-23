package com.bis.sourcelead.service;

import com.bis.sourcelead.dto.EmployerRegistrationDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgRequest;

public interface OrgRequestService {

	OrgRequest saveOrgRequest(OrgRequest orgRequest) throws Exception;

	Boolean  validateOrgRequestCompanyNameByName(String companyName)throws Exception;

	void senEmailOrgRequestToSourcelead(OrgRequest orgRequest)throws Exception;
	
	ResponseList<EmployerRegistrationDTO> getDemoRequestsList(EmployerRegistrationDTO employerRegistrationDTO,OrgLocations orgLocations,String orgType) throws Exception;
	
	OrgRequest getOrgRequestById(Long id) throws Exception;
}
