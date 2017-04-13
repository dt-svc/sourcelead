package com.bis.sourcelead.service.impl;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.ContractsTemplateDao;
import com.bis.sourcelead.dao.RequirementSubmissionDao;
import com.bis.sourcelead.entity.ContractsTemplate;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.ContractsTemplateService;

@Service
public class ContractsTemplateServiceImpl implements ContractsTemplateService{

	@Autowired
	private ContractsTemplateDao contractsTemplateDao;
	
	
	@Override
	public ContractsTemplate saveContractTemplate(JSONObject jsonObj, User user, OrgLocations orgLocation) {
		
		
		ContractsTemplate contractsTemplate = new ContractsTemplate();
		return contractsTemplateDao.saveContractTemplate(contractsTemplate);
	}
	
}
