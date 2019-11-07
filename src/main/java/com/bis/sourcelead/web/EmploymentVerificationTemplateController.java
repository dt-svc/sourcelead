package com.bis.sourcelead.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.dto.OrglocationSignatoryDTO;
import com.bis.sourcelead.entity.EmployeeVerificationTemplate;
import com.bis.sourcelead.entity.LetterHead;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.RoleHierarchy;
import com.bis.sourcelead.entity.Roles;
import com.bis.sourcelead.service.EmployeeVerificationTemplateService;
import com.bis.sourcelead.service.LetterHeadService;
import com.bis.sourcelead.service.OrglocationSignatoryService;
import com.bis.sourcelead.service.RoleHierarchyService;
import com.bis.sourcelead.service.RolesService;
import com.bis.sourcelead.util.SourceLeadSessionManager;

@Controller
@RequestMapping("/app")
public class EmploymentVerificationTemplateController {
	
	private static final Logger logger = LoggerFactory.getLogger(EmploymentVerificationTemplateController.class);
	
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	@Autowired
	OrglocationSignatoryService orglocationSignatoryService;
	
	@Autowired
	EmployeeVerificationTemplateService employeeVerificationTemplateService;
	
	@Autowired
	LetterHeadService letterHeadService;
	
	@Autowired
	private RolesService rolesService;
	
	/**
	 * 	Display add template page
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/addEmploymentVerificationTemplate")
	public ModelAndView addEmploymentVerificationTemplates(HttpServletRequest request,HttpServletResponse response) throws Exception {
		
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		List<OrglocationSignatoryDTO> signatoryList = orglocationSignatoryService.getAllSignatoriesByLocationId(orgLocation.getId());
		List<LetterHead> letterHeadList = letterHeadService.getLetterheadBylocationId(orgLocation.getId());
		ModelAndView model = new ModelAndView();
		model.addObject("signatoryList",signatoryList);
		model.addObject("letterHeadList",letterHeadList);
		model.addObject("sourceleadUrl", sourceleadUrl);
		model.addObject("sourceleadUrl", sourceleadUrl);
		model.setViewName("employmentVerification/addEmploymentVerificationTemplate");
		return model;
	}
	
	/**
	 * display employment verification template list page
	 * @return model
	 */
	@RequestMapping(value = "/employmentVerificationTemplateList")
	public ModelAndView employmentVerificationTemplate() {
		ModelAndView model = new ModelAndView();
		model.addObject("sourceleadUrl", sourceleadUrl);
		model.setViewName("employmentVerification/employmentVerificationTemplateList");
		return model;	
	}
	
	@RequestMapping(value = "/editEmpTemplate")
    public ModelAndView editTemplate(@RequestParam(value="templateUUID", required=false) String templateUUID ,HttpServletRequest request,HttpServletResponse response) throws Exception {
		
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		
		List<OrglocationSignatoryDTO> signatoryList = orglocationSignatoryService.getAllSignatoriesByLocationId(orgLocation.getId());
		List<LetterHead> letterHeadList = letterHeadService.getLetterheadBylocationId(orgLocation.getId());
		EmployeeVerificationTemplate empTemplate = employeeVerificationTemplateService.getEmploymentVerificationtemplateByTemplateUUID(templateUUID);
		ModelAndView model = new ModelAndView();
		model.addObject("templateUUID", templateUUID);
		model.addObject("signatoryList", signatoryList);
		model.addObject("letterHeadList",letterHeadList);
		model.addObject("sourceleadUrl", sourceleadUrl);
		model.addObject("empTemplate", empTemplate);
		model.setViewName("employmentVerification/addEmploymentVerificationTemplate");
		return model;
	}
	
	@RequestMapping(value = "/employementVerificationReport")
	public ModelAndView employementVerificationReport(HttpServletRequest request) throws Exception
	{
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
		List<Roles> orgRolesList = rolesService.getOrgEnrolledRolesByOrgLocationId(org, orgLocation.getId());
		
		 ModelAndView model = new ModelAndView();
		 model.addObject("sourceleadUrl", sourceleadUrl);
		 model.addObject("orgRolesList", orgRolesList);
		 model.setViewName("reports/employementVerificationReport");
		 return model;
	}
}
