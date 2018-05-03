package com.bis.sourcelead.rest;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.StringReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
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
import com.bis.sourcelead.dto.AddUserDTO;
import com.bis.sourcelead.dto.EmploymentVerificationTemplateDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.dto.RoleDTO;
import com.bis.sourcelead.dto.UserDTO;
import com.bis.sourcelead.entity.Contracts;
import com.bis.sourcelead.entity.ContractsTemplate;
import com.bis.sourcelead.entity.EmployeeVerificationTemplate;
import com.bis.sourcelead.entity.Employees;
import com.bis.sourcelead.entity.LetterHead;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.Requirement;
import com.bis.sourcelead.entity.RequirementOffers;
import com.bis.sourcelead.entity.RequirementSubmission;
import com.bis.sourcelead.entity.Timezone;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserRole;
import com.bis.sourcelead.service.EmployeeVerificationTemplateService;
import com.bis.sourcelead.service.EmployeesService;
import com.bis.sourcelead.service.LetterHeadService;
import com.bis.sourcelead.service.UserRoleService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.ReportsUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.html.WebColors;
import com.itextpdf.text.html.simpleparser.HTMLWorker;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
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
	UserService userService;
	
	@Autowired
	UserRoleService userRoleService;
	
	@Autowired
	LetterHeadService letterHeadService;
	
	@Autowired
	EmployeesService employeesService;
	
	@Autowired
	CommonUtil commonUtil;
	
	@Autowired
	ReportsUtil reportsUtil;
	
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
		
		try {
			String header = "<div style='text-align: center;'>"+empTemplate.getTemplateName()+"<br></br></div><br></br><br></br><br></br>";
			String totalBody =header+ empTemplate.getTemplateContent();
		     org.jsoup.nodes.Document doc = Jsoup.parse(totalBody);
		     doc.outputSettings().syntax(org.jsoup.nodes.Document.OutputSettings.Syntax.xml);    
	         String finalString=doc.html();
		    ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
		   
		    Document document = new Document();
		    PdfWriter writer = PdfWriter.getInstance(document, byteArray);
		    document.open();
		    XMLWorkerHelper.getInstance().parseXHtml(writer, document, new StringReader(finalString));
		    document.close();
		    byte[] pdfBytes = byteArray.toByteArray();
	  	    response.setHeader("Content-disposition","inline; filename=contractPdf.pdf");
	  	    response.setContentType("application/pdf");
	  	    response.setContentLength(pdfBytes.length);
	          ServletOutputStream sos = response.getOutputStream();
	          sos.write(pdfBytes);
	          sos.flush();
	          sos.close();
		} catch (Exception e) {
		    e.printStackTrace();
		}
		
    	
	}
	
	@RequestMapping(value = "/deleteEmployementTemplate", method = RequestMethod.POST)
	public @ResponseBody  void deleteEmployementTemplate(@RequestParam("templateUUID") String tempUUID,HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		EmployeeVerificationTemplate empTemplate = employeeVerificationTemplateService.getEmploymentVerificationtemplateByTemplateUUID(tempUUID);
		empTemplate.setTemplateStatus(Constants.MEMBER_IN_ACTIVE);
		employeeVerificationTemplateService.updateEmployMentTemplate(empTemplate);
    	
	}

	@RequestMapping(value = "/getAllEmployeesList", method = RequestMethod.GET)
	public @ResponseBody  ResponseList<UserDTO> getAllEmployeesList(UserDTO userDTO,HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		
		List<UserDTO> userDtoList =  new ArrayList<>();
		ResponseList<UserDTO> employeeResponseList =  new ResponseList<>();
		ResponseList<User> employeesList = userService.getUsersResponseListByOrglocationId(userDTO,orgLocation);
		employeesList.getData().forEach(emp->{
			
			Employees employee = employeesService.getEmployeeByUserIdAndOrgLocationsId(emp.getId(), orgLocation.getId());
			UserRole userRole = null;
			try {
				 userRole = userRoleService.getUserDetailsByUserIdAndLocationId(emp.getId(), orgLocation.getId());
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			UserDTO user = new UserDTO();
			user.setEmployeeId(emp.getId().toString());
			if(emp.getLastName() != null) {
				StringBuilder sb = new StringBuilder();
				user.setUserName(sb.append(emp.getFirstName()).append(" ").append(emp.getLastName()).toString());
			}else {
				user.setUserName(emp.getFirstName());
			}
			user.setEmail(emp.getEmail());
			user.setMobilePhone(emp.getMobilePhone());
			
			if(employee.getHireDate() != null)
			user.setDateofJoin(commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(),employee.getHireDate()));
			
			if(Constants.INACTIVE.equalsIgnoreCase(userRole.getUserState().toString())) {
				user.setDateOfTermination(commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(),userRole.getUpdatedDate()));
			}else {
				user.setDateOfTermination("Till Date");
			}
			
			if(employee.getUserEmploymentType() != null)
			user.setEmploymentType(employee.getUserEmploymentType());
			
			if(userRole.getDesignation() != null)
			user.setDesignation(userRole.getDesignation());
			userDtoList.add(user);
		});
		employeeResponseList.setData(userDtoList);
		employeeResponseList.setRecordsFiltered(userDtoList.size());
		employeeResponseList.setRecordsTotal(userDtoList.size());
		return employeeResponseList;
	}
	
	@RequestMapping(value = "/previewCompleteTemplateByTemplateUUID", method = RequestMethod.GET)
	public @ResponseBody  void previewCompleteTemplateByTemplateId(@RequestParam("templateUUID") String templateUUID,@RequestParam("toAddress") String toAddress,@RequestParam("employeeId") Long employeeId,HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		try {
			OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			EmployeeVerificationTemplate empTemplate = employeeVerificationTemplateService.getEmploymentVerificationtemplateByTemplateUUID(templateUUID);
			LetterHead letterHead = letterHeadService.getLetterheadById(empTemplate.getLetterHead().getId());
			User user = userService.getUserById(employeeId);
			String templateContent = reportsUtil.replaceAllTemplatePlaceHolders(empTemplate.getTemplateContent(),empTemplate,toAddress,user,orgLocation);
			reportsUtil.viewCompleteletterheadData(templateContent,letterHead,response);
		    
		} catch (Exception e) {
		    e.printStackTrace();
		}
	}

}
