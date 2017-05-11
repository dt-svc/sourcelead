package com.bis.sourcelead.dao;

import java.util.List;

import org.json.JSONObject;

import com.bis.sourcelead.entity.Contracts;
import com.bis.sourcelead.entity.ContractsTemplate;
import com.bis.sourcelead.entity.OrgLocations;

public interface ContractsTemplateDao {

	ContractsTemplate saveContractTemplate(ContractsTemplate contractsTemplate) throws Exception;

	//ContractsTemplate saveContractTemplate(Long contractTemplateId);

	List<ContractsTemplate> getAllContractsTemplatesByOrgLocationid(OrgLocations orgLocation)throws Exception;

	List<ContractsTemplate> getAllContractsTemplatesByContractTypeLandLocationId(Long orgLocationId, String contractType)throws Exception;

	ContractsTemplate getContractTemplateById(long parseLong)throws Exception;

	Contracts saveNewContract(Contracts contracts)throws Exception;

	List<Contracts> getAllContractsByLocationIdAndStatus(OrgLocations orgLocation, String contractSubmissionType, String tabType, String contractsType, String contractStatus)throws Exception;

	Contracts getContractById(Long contractId);
	
	Contracts getContractByContractUUID(String contractUUID);

	Contracts getChildContractById(Long contractId);
	
	ContractsTemplate findByContractTemplateName(String templateName,String contractType, Long orgLocationId) throws Exception;

	List<Contracts> getListOfContractsEnvId();

	Contracts findContractTitleByOrgLocationAndContractType(String contractTitle,String contractType,Long orgLocationId);
	
}
