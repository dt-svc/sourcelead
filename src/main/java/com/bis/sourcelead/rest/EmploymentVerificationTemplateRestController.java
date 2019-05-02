package com.bis.sourcelead.rest;

import java.io.ByteArrayOutputStream;
import java.io.StringReader;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jsoup.Jsoup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dto.EmploymentVerificationTemplateDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.EmployeeVerificationTemplate;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.EmployeeVerificationTemplateService;
import com.bis.sourcelead.util.ReportsUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;
import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;

/**
 * Handles employment verification related functionalities
 * @author root
 *
 */

@RestController
@PropertySource("${propertyLocation:app.properties}")
@RequestMapping("/app")
public class EmploymentVerificationTemplateRestController {
	
	
	@Value("${static.path}")
	private String staticPath;
	
	@Autowired
	private EmployeeVerificationTemplateService employeeVerificationTemplateService;
	
	@Autowired
	private ReportsUtil reportsUtil;
	
	private static final Logger logger = LoggerFactory.getLogger(EmploymentVerificationTemplateRestController.class);
	
	
	@RequestMapping(value = "/saveEmploymentverificationTemplate", method = RequestMethod.POST)
	public @ResponseBody EmployeeVerificationTemplate saveEmploymentverificationTemplate(EmploymentVerificationTemplateDTO employmentVerificationTemplateDTO,
			HttpServletRequest request) throws Exception {

		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		
		EmployeeVerificationTemplate employeeVerificationTemplate = employeeVerificationTemplateService.saveEmployeeVerificationTemplate(employmentVerificationTemplateDTO,user,orgLocation);
		return employeeVerificationTemplate;
	}
	
	
	@RequestMapping(value = "/getAllEmploymentVerificationListByOrgLocationId", method = RequestMethod.GET)
	public ResponseList<EmploymentVerificationTemplateDTO> getAllEmploymentVerificationListByOrgLocationId(EmploymentVerificationTemplateDTO employmentVerificationTemplateDTO, HttpServletRequest request,HttpServletResponse response) throws Exception {
		
		
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		
		String[] tableColumns = {"templateName", "", "signatory","createdBy",""};
	     int sortColumnIndex = 0; //by default sorting using updated date
		String sortColumnOrder = "desc"; //by default descending order
		if(employmentVerificationTemplateDTO.getOrder() != null && !employmentVerificationTemplateDTO.getOrder().isEmpty()) {
			sortColumnIndex = Integer.parseInt(employmentVerificationTemplateDTO.getOrder().get(0).get("column"));
			sortColumnOrder = employmentVerificationTemplateDTO.getOrder().get(0).get("dir");
		}
		employmentVerificationTemplateDTO.setSortColumn(tableColumns[sortColumnIndex]);
		employmentVerificationTemplateDTO.setSortOrder(sortColumnOrder);
		
		
		return employeeVerificationTemplateService.getAllEmploymentVerificationListByOrgLocationId(orgLocation.getId(),employmentVerificationTemplateDTO);
	} 
	
	@RequestMapping(value = "/previewEmpTemplate/{templateUUID}/{templateName}", method = RequestMethod.GET)
	public @ResponseBody  void previewEmpTemplate(@PathVariable("templateUUID") String tempUUID,@PathVariable("templateName") String tempName,HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		EmployeeVerificationTemplate empTemplate = employeeVerificationTemplateService.getEmploymentVerificationtemplateByTemplateUUID(tempUUID);
		
			String header = "<div style='text-align: center;'>"+empTemplate.getTemplateName()+"<br></br></div><br></br><br></br><br></br>";
			String totalBody =header+ empTemplate.getTemplateContent();
			reportsUtil.viewEmployeeCompleteletterheadData(totalBody, empTemplate, response);
    	
	}
	
	@RequestMapping(value = "/deleteEmployementTemplate", method = RequestMethod.POST)
	public @ResponseBody  void deleteEmployementTemplate(@RequestParam("templateUUID") String tempUUID,HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		EmployeeVerificationTemplate empTemplate = employeeVerificationTemplateService.getEmploymentVerificationtemplateByTemplateUUID(tempUUID);
		empTemplate.setTemplateStatus(Constants.MEMBER_IN_ACTIVE);
		employeeVerificationTemplateService.updateEmployMentTemplate(empTemplate);
    	
	}
	
}
