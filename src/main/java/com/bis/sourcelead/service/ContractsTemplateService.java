package com.bis.sourcelead.service;

import java.util.List;

import org.json.JSONObject;
import org.springframework.web.multipart.MultipartFile;

import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.Contracts;
import com.bis.sourcelead.entity.ContractsTemplate;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;

public interface ContractsTemplateService {

	ContractsTemplate saveContractTemplate(JSONObject jsonObj, User user, OrgLocations orgLocation) throws Exception;

	String generatePDFContractTemplate(JSONObject jsonObj, User user, OrgLocations orgLocation) throws Exception;

	ContractsTemplate getContractTemplateById(Long contractTemplateId);

	List<ContractsTemplate> getAllContractsTemplatesByOrgLocationid(OrgLocations orgLocation)throws Exception;

	List<ContractsTemplate> getAllContractsTemplatesByContractTypeLandLocationId(Long orgLocationId, String contractType)throws Exception;

	Contracts saveNewContract(JSONObject jsonObj, OrgLocations orgLocation, String saveType, User user, Org org)throws Exception;

	Contracts getAllContractsByLocationIdAndStatus(OrgLocations orgLocation, String contractSubmissionType, String tabType)throws Exception;

	Contracts getContractById(Long contractId);

	Contracts saveNewContract(Contracts updateContracts) throws Exception;

	Contracts getChildContractById(Long contractId);

	Contracts approveContracts(long parseLong, String status, OrgLocations orgLocation, User user, Org org)throws Exception;
	

	ContractsTemplate saveContractTemplate(ContractsTemplate contractsTemplate) throws Exception;

	ContractsTemplate findByContractTemplateName(String templateName,Long orgLocationId) throws Exception;

	List<Contracts> getListOfContractsEnvId();

	Contracts saveExistigContract(JSONObject jsonObj, MultipartFile multiFile, OrgLocations orgLocation, User user, Org org) throws Exception;

	
}
