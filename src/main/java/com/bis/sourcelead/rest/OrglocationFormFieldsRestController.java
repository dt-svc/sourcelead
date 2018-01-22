package com.bis.sourcelead.rest;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.dto.OrglocationFormFieldsDTO;
import com.bis.sourcelead.dto.SourceleadSectionDTO;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrglocationFormFields;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.OrglocationFormFieldsService;
import com.bis.sourcelead.service.SourceleadFormSectionsService;
import com.bis.sourcelead.util.SourceLeadSessionManager;

@RestController
@RequestMapping(value="/app")
public class OrglocationFormFieldsRestController {

	@Autowired
	private OrglocationFormFieldsService orglocationFormFieldsService;
	
	@Autowired
	private SourceleadFormSectionsService sourceleadFormSectionsService;
	
	@RequestMapping(value="/storeNewCustomFormField",method = RequestMethod.POST)
    public ModelAndView storeNewCustomFormField(OrglocationFormFieldsDTO orgLocFormFieldsDTO,HttpServletRequest request) throws Exception {
    	User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		OrglocationFormFields orglocationFormFields = orglocationFormFieldsService.saveNewCustomFormFields(orgLocFormFieldsDTO,orgLocation,org,user);
		OrglocationFormFieldsDTO orglocationFormFieldsDTO = sourceleadFormSectionsService.CustomFormFieldsDTOConverter(orglocationFormFields, null);
		ModelAndView model = new ModelAndView();
		model.addObject("customFormDetails",orglocationFormFieldsDTO);
		model.setViewName("customForms/customFormFieldData");
		return model;
    }
    
	@RequestMapping(value="/editNewCustomFormField",method = RequestMethod.POST)
    public ModelAndView editNewCustomFormField(OrglocationFormFieldsDTO orgLocFormFieldsDTO,HttpServletRequest request,@RequestParam (value="actionId") Long actionId) throws Exception {
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		OrglocationFormFields orglocationFormFields = orglocationFormFieldsService.getOrglocationFormFieldsById(orgLocFormFieldsDTO.getId());
		OrglocationFormFieldsDTO orglocationFormFieldsDTO = sourceleadFormSectionsService.CustomFormFieldsDTOConverter(orglocationFormFields, null);
		ModelAndView model = new ModelAndView();
		List<SourceleadSectionDTO> sections  = sourceleadFormSectionsService.getSourceleadFormSectionsByActionId(actionId,orgLocation.getId());
		model.addObject("customFormPopDetails",orglocationFormFieldsDTO);
		model.addObject("SourceleadForm_Sections_pop",sections);
		model.setViewName("customForms/customFormSections");
		return model;
    }
	
	@RequestMapping(value="/deleteCustomField",method = RequestMethod.POST)
    public String deleteCustomField(@RequestParam(value="customID")Long customID,HttpServletRequest request) throws Exception {
		String result  = orglocationFormFieldsService.deleteCustomFieldById(customID);
		return result;
    }
	
}
