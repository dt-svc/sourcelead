package com.bis.sourcelead.rest;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.dto.OrglocationFormFieldsDTO;
import com.bis.sourcelead.dto.SourceleadSectionDTO;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrglocationFormFields;
import com.bis.sourcelead.entity.SourceleadFormFields;
import com.bis.sourcelead.service.OrglocationFormFieldsService;
import com.bis.sourcelead.service.SourceleadFormFieldsService;
import com.bis.sourcelead.service.SourceleadFormSectionsService;
import com.bis.sourcelead.util.SourceLeadSessionManager;


@RestController
@RequestMapping("/app")
@PropertySource("${propertyLocation:app.properties}")
public class CustomFormRestController {

	@Value("${sourceleadUrl}")
	private String sourceleadUrl;

	@Autowired
	private SourceleadFormFieldsService sourceleadFormFieldsService;
	
	@Autowired
	private SourceleadFormSectionsService sourceleadFormSectionsService;
	
	@Autowired
	private OrglocationFormFieldsService orglocationFormFieldsService;
	
    @RequestMapping(value = "/getFormSectionsByActionId",  method = RequestMethod.POST)
    public  ModelAndView getFormSectionsByActionId(@RequestParam(value="actionId", required=true) Long actionId,HttpServletRequest request) throws Exception {
    	ModelAndView model = new ModelAndView();
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
    	List<SourceleadSectionDTO> sections  = sourceleadFormSectionsService.getSourceleadFormSectionsByActionId(actionId,orgLocation.getId());
    	model.addObject("SourceleadForm_Sections",sections);
    	model.setViewName("customForms/customFormSections");
    	return model;
    }
	 
    @RequestMapping(value = "/getFormFeildsBySectionId",  method = RequestMethod.POST)
    public @ResponseBody List<SourceleadFormFields> getFormFieldsBySectionId(@RequestParam(value="sectionId", required=true) Long sectionId) throws Exception {
    	List<SourceleadFormFields> fromFeilds  = sourceleadFormFieldsService.getAllFormFieldsBySectionId(sectionId);
    	return fromFeilds;
    }
    
    @RequestMapping(value="/getAllCustomFormFieldsBySectionId",method = RequestMethod.POST)
    public List<OrglocationFormFields> getAllCustomFormFieldsBySectionIdandOrgLocationId(@RequestParam(value="sectionId")Long sectionId,HttpServletRequest request) throws Exception {
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		List<OrglocationFormFields> orglocationFormField  = orglocationFormFieldsService.getAllCustomFormFieldsBySectionIdandOrgLocationId(sectionId,orgLocation.getId());
		return orglocationFormField;
    }
    
    @RequestMapping(value="/getAllASectionCustomFields",method = RequestMethod.POST)
    public ModelAndView getAllCustomFormFieldsBySectionIdandOrgLocationIdWeb(@RequestParam(value="sectionId")Long sectionId,HttpServletRequest request) throws Exception {
		List<OrglocationFormFields> orglocationFormFieldList  = getAllCustomFormFieldsBySectionIdandOrgLocationId(sectionId, request);
		ModelAndView model = new ModelAndView();
		List<OrglocationFormFieldsDTO> OrglocationFormFieldsDTOList =new ArrayList<>();
    	for(OrglocationFormFields customForm:orglocationFormFieldList) {
    		OrglocationFormFieldsDTO customFormSectonsListDTO = sourceleadFormSectionsService.CustomFormFieldsDTOConverter(customForm, null);
    		OrglocationFormFieldsDTOList.add(customFormSectonsListDTO);
    	}
		model.addObject("customFieldsList",OrglocationFormFieldsDTOList);
    	model.setViewName("genericCustomFormFields");
    	return model;
    }
    
    
    @RequestMapping(value="/getAllSectionByActionId",method = RequestMethod.POST)
    public  ModelAndView getAllSectionByActionId(@RequestParam(value="actionId", required=true) Long actionId, HttpServletRequest request) throws Exception {
    	OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
    	ModelAndView model = new ModelAndView();
    	List<OrglocationFormFields> customFormSectonsList = orglocationFormFieldsService.getAllSectionByActionId(actionId,orgLocation);
    	OrglocationFormFieldsDTO customFormSectonsListDTO = null;
    	List<OrglocationFormFieldsDTO> OrglocationFormFieldsDTOList =new ArrayList<>();
    	for(OrglocationFormFields customForm:customFormSectonsList) {
    		customFormSectonsListDTO = sourceleadFormSectionsService.CustomFormFieldsDTOConverter(customForm, null);
    		OrglocationFormFieldsDTOList.add(customFormSectonsListDTO);
    	}
    	model.addObject("customFieldsList",OrglocationFormFieldsDTOList);
    	model.setViewName("genericCustomFormFields");
    	return model;
    	
    }
    
    
}