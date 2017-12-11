package com.bis.sourcelead.service;

import java.util.List;

import com.bis.sourcelead.dto.FederalAndStateTaxDTO;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrglocationStateTax;
import com.bis.sourcelead.entity.User;

public interface OrglocationStateTaxService {

	OrglocationStateTax saveOrglocationStateTax(OrglocationStateTax orglocationStateTax) throws Exception;
	
	public String addOrglocationStateTax(FederalAndStateTaxDTO federalAndStateTaxDTO,OrgLocations orgLocations, User user) throws Exception;
	
	List<OrglocationStateTax> getOrglocationStateTaxListByOrgLocationId(Long orgLocationId) throws Exception;
	
	OrglocationStateTax getOrglocationStateTaxByUUID(String UUID) throws Exception;
	
	void deleteOrglocationStateTax(OrglocationStateTax orglocationStateTax) throws Exception;
}
