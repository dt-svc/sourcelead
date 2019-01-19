package com.bis.sourcelead.dao;

import com.bis.sourcelead.dto.EmployerRegistrationDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgRequest;

public interface OrgRequestDao {
 
	OrgRequest saveOrgRequest(OrgRequest orgRequest) throws Exception;
	
	Boolean validateOrgRequestCompanyNameByName(String companyName)throws Exception;
	
	ResponseList<EmployerRegistrationDTO> getDemoRequestsList(EmployerRegistrationDTO employerRegistrationDTO, OrgLocations orgLocations, String orgType) throws Exception;
	
	OrgRequest getOrgRequestById(Long id) throws Exception;
}
