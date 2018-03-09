package com.bis.sourcelead.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.EmployeeVerificationTemplateDao;
import com.bis.sourcelead.dto.EmploymentVerificationTemplateDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.EmployeeVerificationTemplate;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.EmployeeVerificationTemplateService;
import com.bis.sourcelead.util.SourceLeadRandomCodeHelper;

@Service
public class EmployeeVerificationTemplateServiceImpl implements EmployeeVerificationTemplateService {
	
	@Autowired
	private EmployeeVerificationTemplateDao employeeVerificationTemplateDao;

	@Override
	public EmployeeVerificationTemplate saveDefaultEmployeeVerificationTemplate(EmployeeVerificationTemplate employeeVerificationTemplate) {
		return employeeVerificationTemplateDao.saveEmployeeVerificationTemplate(employeeVerificationTemplate);
	}

	@Override
	public EmployeeVerificationTemplate saveEmployeeVerificationTemplate(EmploymentVerificationTemplateDTO employmentVerificationTemplateDTO,
			User user, OrgLocations orgLocation) {
		
		EmployeeVerificationTemplate employeeVerificationTemplate =  new EmployeeVerificationTemplate();
		employeeVerificationTemplate.setTemplateName(employmentVerificationTemplateDTO.getTemplateName());
		employeeVerificationTemplate.setOrgLocationId(orgLocation);
		employeeVerificationTemplate.setDescription(employmentVerificationTemplateDTO.getDescription());
		employeeVerificationTemplate.setTemplateContent(employmentVerificationTemplateDTO.getTemplateContent());
		
		employeeVerificationTemplate.setCreatedDate(new Date());
		employeeVerificationTemplate.setCreatedBy(user.getUserCode());
		employeeVerificationTemplate.setUpdatedDate(new Date());
		employeeVerificationTemplate.setUpdatedBy(user.getUserCode());
		employeeVerificationTemplate.setTemplateUUID(SourceLeadRandomCodeHelper.generateRandomUUID());
		employeeVerificationTemplate.setTemplateStatus(Constants.MEMBER_ACTIVE);
		
		return employeeVerificationTemplateDao.saveEmployeeVerificationTemplate(employeeVerificationTemplate);
	}

	@Override
	public ResponseList<EmployeeVerificationTemplate> getAllEmploymentVerificationListByOrgLocationId(Long orgLocationId,EmploymentVerificationTemplateDTO employmentVerificationTemplateDTO) {
		return employeeVerificationTemplateDao.getAllEmploymentVerificationListByOrgLocationId(orgLocationId,employmentVerificationTemplateDTO);
	}
	
}
