package com.bis.sourcelead.rest;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dto.OnBoardingTemplateDTO;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.VendorOnboardingTemplate;
import com.bis.sourcelead.entity.VendorOnboardingTemplateDocuments;
import com.bis.sourcelead.service.VendorOnboardTemplateDocumentsService;
import com.bis.sourcelead.service.VendorOnboardTemplateService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;

@RestController
@PropertySource("${propertyLocation:app.properties}")
public class VendorOnboardRestController {
	
	protected static boolean isNullOrEmpty(String str) {
		return (str == null || "".equals(str.trim()));
	}

	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	@Autowired
	VendorOnboardTemplateService vendorOnboardTemplateService;
	

	@Autowired
	private CommonUtil commonUtil;
	
	@Autowired
	VendorOnboardTemplateDocumentsService vendorOnboardTemplateDocumentsService;
	
	
	
	@RequestMapping(value = "/getVendorOnboardingTemplatesList")
	public List<VendorOnboardingTemplate> getVendorOnboardingTemplatesList(HttpServletRequest request) throws Exception {
		OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
		List<VendorOnboardingTemplate> templatesList = vendorOnboardTemplateService.getVendorOnBoardingTemplatesList(loginOrgLocation.getId());
		return templatesList;
	 }
	
	
	@RequestMapping(value = "/createNewVendorOnBoardingTemplate" , method = RequestMethod.POST)
	 public @ResponseBody  VendorOnboardingTemplate createNewVendorOnBoardingTemplate(OnBoardingTemplateDTO onBoardingTemplateDTO,HttpServletRequest request) throws Exception {
	 	 User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
	 	 OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
	 	 VendorOnboardingTemplate onBoardingTemplate = vendorOnboardTemplateService.getVendorOnBoardTemplateDetails(onBoardingTemplateDTO, user, orgLocations);
		 return onBoardingTemplate;
	  
	}
	
	@RequestMapping(value = "/getVendorOnboardTemplateById" , method = RequestMethod.POST)
	 public @ResponseBody VendorOnboardingTemplate getVendorOnboardTemplateById(@RequestParam(value="templateId", required=false) Long templateId, HttpServletRequest request) throws Exception {
		
		VendorOnboardingTemplate onBoardingTemplate = vendorOnboardTemplateService.getVendorOnBoardingTemplateById(templateId);
		return onBoardingTemplate;
	   }
	
	
	
	@RequestMapping(value = "/validateVendorOnboardingTemplateName", method = RequestMethod.POST)
	@ResponseBody String validateVendorOnboardingTemplateName(@RequestParam(value = "templateName", required = false) String templateName,
			@RequestParam(value = "templateId", required = false) String templateId,HttpServletRequest request) throws Exception {
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
		
		VendorOnboardingTemplate onBoardingTemplate = null;
		String result = null;
		
		if(!isNullOrEmpty(templateId)){
			onBoardingTemplate  = vendorOnboardTemplateService.getVendorOnBoardingTemplateById(Long.parseLong(templateId));
			if(onBoardingTemplate.getTemplateName().equalsIgnoreCase(templateName)){
				result = "valid";
			}else{
				onBoardingTemplate  = vendorOnboardTemplateService.getVendorOnBoardingTemplateByTemplateNameAndOrgLocationId(templateName,orgLocations.getId());
				if(null != onBoardingTemplate){
					result = "invalid";
				}else{
					result = "valid";
				}
			}
		}
		
		else{
			onBoardingTemplate  = vendorOnboardTemplateService.getVendorOnBoardingTemplateByTemplateNameAndOrgLocationId(templateName,orgLocations.getId());
			if(null != onBoardingTemplate){
				result = "invalid";
			}else{
				result = "valid";
			}
		}
		
		return result;
		
	}
	
	
	@RequestMapping(value = "/getVendorOnboardTemplateDocumentByTemplateId" , method = RequestMethod.POST)
	 public @ResponseBody List<VendorOnboardingTemplateDocuments> getVendorOnboardTemplateDocumentByTemplateId(@RequestParam(value="templateId", required=false) Long templateId,
			 @RequestParam(value="documentType", required=false) String documentType, HttpServletRequest request) throws Exception {
		
		List<VendorOnboardingTemplateDocuments> documentsList = vendorOnboardTemplateDocumentsService.getVendorOnBoardingTemplateDocumentsByTemplateId(templateId,documentType);
		return documentsList;
	}
	
	@RequestMapping(value = "/viewVendorOnboardTemplateByTemplateUUID")
 	public ModelAndView viewVendorOnboardTemplateByTemplateUUID(@RequestParam(value="templateUUID", required=false) String templateUUID,
 			HttpServletRequest request) throws Exception {
		   
		    ModelAndView model = new ModelAndView();
 			commonUtil.highLightMenu(Constants.OrgSettings, request);
 			
 			VendorOnboardingTemplate template = vendorOnboardTemplateService.getVendorOnBoardingTemplateByTemplateUUID(templateUUID);
 			
 			model.setViewName("vendors/addNewVendorOnboardTemplate");
 			model.addObject("sourceleadUrl", sourceleadUrl);
 			model.addObject("templateId", template.getId());
 			return model;
 	}
	
}
