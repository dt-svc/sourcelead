package com.bis.sourcelead.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.EmployeeVerificationTemplateDao;
import com.bis.sourcelead.dto.EmploymentVerificationTemplateDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.dto.VendorContractsDTO;
import com.bis.sourcelead.entity.ContractsTemplate;
import com.bis.sourcelead.entity.EmployeeVerificationTemplate;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.EmployeeVerificationTemplateService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.SourceLeadRandomCodeHelper;

@Service
public class EmployeeVerificationTemplateServiceImpl implements EmployeeVerificationTemplateService {
	
	@Autowired
	private EmployeeVerificationTemplateDao employeeVerificationTemplateDao;
	
	@Autowired
	UserService userService;

	@Override
	public EmployeeVerificationTemplate saveDefaultEmployeeVerificationTemplate(EmployeeVerificationTemplate employeeVerificationTemplate) {
		return employeeVerificationTemplateDao.saveEmployeeVerificationTemplate(employeeVerificationTemplate);
	}

	@Override
	public EmployeeVerificationTemplate saveEmployeeVerificationTemplate(EmploymentVerificationTemplateDTO employmentVerificationTemplateDTO,
			User user, OrgLocations orgLocation) throws Exception {
		
		EmployeeVerificationTemplate employeeVerificationTemplate = null;
		if(StringUtils.isNotBlank(employmentVerificationTemplateDTO.getTemplateUUID())){
			employeeVerificationTemplate = employeeVerificationTemplateDao.getEmploymentVerificationtemplateByTemplateUUID(employmentVerificationTemplateDTO.getTemplateUUID());
		}else{
			 employeeVerificationTemplate =  new EmployeeVerificationTemplate();
		}
		
		employeeVerificationTemplate.setTemplateName(employmentVerificationTemplateDTO.getTemplateName());
		
		User signatoryUser = userService.getUserById(employmentVerificationTemplateDTO.getSignatory());
		employeeVerificationTemplate.setSignatory(signatoryUser);
		
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
	public ResponseList<EmploymentVerificationTemplateDTO> getAllEmploymentVerificationListByOrgLocationId(Long orgLocationId,EmploymentVerificationTemplateDTO employmentVerificationTemplateDTO) throws Exception {
		ResponseList<EmployeeVerificationTemplate> empList = employeeVerificationTemplateDao.getAllEmploymentVerificationListByOrgLocationId(orgLocationId,employmentVerificationTemplateDTO);
		
		ResponseList<EmploymentVerificationTemplateDTO> finalDto = new ResponseList<>();
		List<EmploymentVerificationTemplateDTO> list = new ArrayList<>();
		for (EmployeeVerificationTemplate emp : empList.getData()) {
			
			EmploymentVerificationTemplateDTO tempDto = new EmploymentVerificationTemplateDTO();
			tempDto.setTemplateName(emp.getTemplateName());
			tempDto.setDescription(emp.getDescription());
			tempDto.setTemplateUUID(emp.getTemplateUUID());
			
			if(emp.getSignatory().getLastName() != null) {
				StringBuilder sbName = new StringBuilder();
				tempDto.setSignatoryFullName(sbName.append(emp.getSignatory().getFirstName()).append(" ").append(emp.getSignatory().getLastName()).toString());
			}else {
				tempDto.setSignatoryFullName(emp.getSignatory().getFirstName());
			}
			
			User createdUser = userService.getUserByUserCode(emp.getCreatedBy());
			if(createdUser.getLastName() != null) {
				StringBuilder sbUser = new StringBuilder();
				tempDto.setCreatedBy(sbUser.append(createdUser.getFirstName()).append(" ").append(createdUser.getLastName()).toString());
			}else {
				tempDto.setCreatedBy(createdUser.getFirstName());
			}
			
			list.add(tempDto);
		}
		finalDto.setData(list);
		return finalDto;
	}

	@Override
	public EmployeeVerificationTemplate getEmploymentVerificationtemplateByTemplateUUID(String tempUUID) {
		return employeeVerificationTemplateDao.getEmploymentVerificationtemplateByTemplateUUID(tempUUID);
	}
	
}
