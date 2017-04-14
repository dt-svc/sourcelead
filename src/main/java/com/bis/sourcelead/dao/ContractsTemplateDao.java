package com.bis.sourcelead.dao;

import java.util.List;

import com.bis.sourcelead.entity.ContractsTemplate;
import com.bis.sourcelead.entity.OrgLocations;

public interface ContractsTemplateDao {

	ContractsTemplate saveContractTemplate(ContractsTemplate contractsTemplate) throws Exception;

	ContractsTemplate saveContractTemplate(Long contractTemplateId);

	List<ContractsTemplate> getAllContractsTemplatesByOrgLocationid(OrgLocations orgLocation)throws Exception;
	
}
