package com.bis.sourcelead.service;

import java.util.List;

import org.json.JSONObject;

import com.bis.sourcelead.entity.Contracts;
import com.bis.sourcelead.entity.ContractsTemplate;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;

public interface ContractsTemplateService {

	ContractsTemplate saveContractTemplate(JSONObject jsonObj, User user, OrgLocations orgLocation) throws Exception;

	String generatePDFContractTemplate(JSONObject jsonObj, User user, OrgLocations orgLocation) throws Exception;

	ContractsTemplate getContractTemplateById(Long contractTemplateId);

	List<ContractsTemplate> getAllContractsTemplatesByOrgLocationid(OrgLocations orgLocation)throws Exception;

	List<ContractsTemplate> getAllContractsTemplatesByContractTypeLandLocationId(Long orgLocationId, String contractType)throws Exception;

	Contracts saveNewContract(JSONObject jsonObj, OrgLocations orgLocation, String saveType, User user)throws Exception;

	List<Contracts> getAllContractsByLocationIdAndStatus(OrgLocations orgLocation, String contractSubmissionType)throws Exception;
	
}
