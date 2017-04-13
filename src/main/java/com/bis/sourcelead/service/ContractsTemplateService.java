package com.bis.sourcelead.service;

import org.json.JSONObject;

import com.bis.sourcelead.entity.ContractsTemplate;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;

public interface ContractsTemplateService {

	ContractsTemplate saveContractTemplate(JSONObject jsonObj, User user, OrgLocations orgLocation) throws Exception;

	String generatePDFContractTemplate(JSONObject jsonObj, User user, OrgLocations orgLocation) throws Exception;
	
}
