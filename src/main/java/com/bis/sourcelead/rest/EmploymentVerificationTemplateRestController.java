package com.bis.sourcelead.rest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bis.sourcelead.dto.EmploymentVerificationTemplateDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.EmployeeVerificationTemplate;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.EmployeeVerificationTemplateService;
import com.bis.sourcelead.util.SourceLeadSessionManager;

/**
 * Handles employment verification related functionalities
 * @author root
 *
 */

@RestController
@PropertySource("${propertyLocation:app.properties}")
@RequestMapping("/app")
public class EmploymentVerificationTemplateRestController {
	
	private static final Logger logger = LoggerFactory.getLogger(EmploymentVerificationTemplateRestController.class);
	
	@Autowired
	EmployeeVerificationTemplateService employeeVerificationTemplateService;
	
	
	@RequestMapping(value = "/saveEmploymentverificationTemplate", method = RequestMethod.POST)
	public @ResponseBody EmployeeVerificationTemplate saveEmploymentverificationTemplate(EmploymentVerificationTemplateDTO employmentVerificationTemplateDTO,
			HttpServletRequest request) throws Exception {

		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		
		EmployeeVerificationTemplate employeeVerificationTemplate = employeeVerificationTemplateService.saveEmployeeVerificationTemplate(employmentVerificationTemplateDTO,user,orgLocation);
		return employeeVerificationTemplate;
	}
	
	
	@RequestMapping(value = "/getAllEmploymentVerificationListByOrgLocationId", method = RequestMethod.POST)
	public ResponseList<EmployeeVerificationTemplate> getAllEmploymentVerificationListByOrgLocationId(EmploymentVerificationTemplateDTO employmentVerificationTemplateDTO, HttpServletRequest request,HttpServletResponse response) throws Exception {
		
		
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		
		/*String[] tableColumns = {"templateName", "description", "signatory","createdBy","action"};
	     int sortColumnIndex = 0; //by default sorting using updated date
		String sortColumnOrder = "desc"; //by default descending order
		if(employmentVerificationTemplateDTO.getOrder() != null && !employmentVerificationTemplateDTO.getOrder().isEmpty()) {
			sortColumnIndex = Integer.parseInt(employmentVerificationTemplateDTO.getOrder().get(0).get("column"));
			sortColumnOrder = employmentVerificationTemplateDTO.getOrder().get(0).get("dir");
		}
		employmentVerificationTemplateDTO.setSortColumn(tableColumns[sortColumnIndex]);
		employmentVerificationTemplateDTO.setSortOrder(sortColumnOrder);*/
		
		
		return employeeVerificationTemplateService.getAllEmploymentVerificationListByOrgLocationId(orgLocation.getId(),employmentVerificationTemplateDTO);
	} 
	

}
