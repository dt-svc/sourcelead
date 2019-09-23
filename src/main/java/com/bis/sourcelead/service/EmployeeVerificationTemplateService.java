package com.bis.sourcelead.service;

import java.util.List;

import com.bis.sourcelead.dto.EmploymentVerificationTemplateDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.ContractsTemplate;
import com.bis.sourcelead.entity.EmployeeVerificationTemplate;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;

public interface EmployeeVerificationTemplateService {


	EmployeeVerificationTemplate saveDefaultEmployeeVerificationTemplate(EmployeeVerificationTemplate employeeVerificationTemplate);

	EmployeeVerificationTemplate saveEmployeeVerificationTemplate(EmploymentVerificationTemplateDTO employmentVerificationTemplateDTO,
			User user, OrgLocations orgLocation) throws Exception;

	ResponseList<EmploymentVerificationTemplateDTO> getAllEmploymentVerificationListByOrgLocationId(Long orgLocationId, EmploymentVerificationTemplateDTO employmentVerificationTemplateDTO) throws Exception;

	EmployeeVerificationTemplate getEmploymentVerificationtemplateByTemplateUUID(String tempUUID);

	EmployeeVerificationTemplate getEmploymentVerificationtemplateByLocationId(Long orgLocationId);

	EmployeeVerificationTemplate updateEmployMentTemplate(EmployeeVerificationTemplate empTemp);

}
