package com.bis.sourcelead.dao;

import java.util.List;

import org.json.JSONObject;

import com.bis.sourcelead.entity.ContractsTemplate;
import com.bis.sourcelead.entity.OrgLocations;

public interface ContractsTemplateDao {

	ContractsTemplate saveContractTemplate(ContractsTemplate contractsTemplate) throws Exception;

	ContractsTemplate saveContractTemplate(Long contractTemplateId);

	List<ContractsTemplate> getAllContractsTemplatesByOrgLocationid(OrgLocations orgLocation)throws Exception;

	List<ContractsTemplate> getAllContractsTemplatesByContractTypeLandLocationId(Long orgLocationId, String contractType)throws Exception;

	ContractsTemplate saveNewContract(JSONObject jsonObj, OrgLocations orgLocation, String saveType) throws Exception;

	ContractsTemplate getContractTemplateById(long parseLong)throws Exception;
	
}
