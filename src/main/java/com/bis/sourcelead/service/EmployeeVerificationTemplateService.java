package com.bis.sourcelead.service;

import com.bis.sourcelead.dto.EmploymentVerificationTemplateDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.ContractsTemplate;
import com.bis.sourcelead.entity.EmployeeVerificationTemplate;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;

public interface EmployeeVerificationTemplateService {


	EmployeeVerificationTemplate saveDefaultEmployeeVerificationTemplate(EmployeeVerificationTemplate employeeVerificationTemplate);

	EmployeeVerificationTemplate saveEmployeeVerificationTemplate(EmploymentVerificationTemplateDTO employmentVerificationTemplateDTO,
			User user, OrgLocations orgLocation);

	ResponseList<EmployeeVerificationTemplate> getAllEmploymentVerificationListByOrgLocationId(Long orgLocationId, EmploymentVerificationTemplateDTO employmentVerificationTemplateDTO);

}
