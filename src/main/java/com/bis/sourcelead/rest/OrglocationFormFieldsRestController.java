package com.bis.sourcelead.rest;

import java.util.ArrayList;
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
import com.bis.sourcelead.entity.OrglocationFormSubFields;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.OrglocationFormFieldsService;
import com.bis.sourcelead.service.OrglocationFormSubFieldsService;
import com.bis.sourcelead.service.SourceleadFormSectionsService;
import com.bis.sourcelead.util.SourceLeadSessionManager;

@RestController
@RequestMapping(value="/app")
public class OrglocationFormFieldsRestController {

	@Autowired
	private OrglocationFormFieldsService orglocationFormFieldsService;
	
	@Autowired
	private SourceleadFormSectionsService sourceleadFormSectionsService;
	
	@Autowired
	private OrglocationFormSubFieldsService orglocationFormSubFieldsService;
	
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
    public ModelAndView editNewCustomFormField(OrglocationFormFieldsDTO orgLocFormFieldsDTO,HttpServletRequest request,@RequestParam (value="actionId") Long actionId,@RequestParam (value="type") String type) throws Exception {
		ModelAndView model = new ModelAndView();
		OrglocationFormFields orglocationFormFields = null;
		OrglocationFormFieldsDTO orglocationFormFieldsDTO = null;
		List<SourceleadSectionDTO> sections  = null;
		List<OrglocationFormSubFields> orglocationFormSubFields = null;
		String disableFields =  null;
		if(type.equalsIgnoreCase("addNewCustomField")) {
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		orglocationFormFields = orglocationFormFieldsService.getOrglocationFormFieldsById(orgLocFormFieldsDTO.getId());
			if(orglocationFormFields != null) {
				orglocationFormSubFields = orglocationFormSubFieldsService.getOrgCustomSufieldsBySectionId(orgLocFormFieldsDTO.getId());	
				orglocationFormFieldsDTO = sourceleadFormSectionsService.CustomFormFieldsDTOConverter(orglocationFormFields,orglocationFormSubFields);
			}
		sections = sourceleadFormSectionsService.getSourceleadFormSectionsByActionId(actionId,orgLocation.getId());
		}else if(type.equalsIgnoreCase("editCustomRowField") || type.equalsIgnoreCase("summary")) {
		orglocationFormFields = orglocationFormFieldsService.getOrglocationFormFieldsById(orgLocFormFieldsDTO.getId());
		orglocationFormSubFields = orglocationFormSubFieldsService.getOrgCustomSufieldsBySectionId(orgLocFormFieldsDTO.getId());
		orglocationFormFieldsDTO = sourceleadFormSectionsService.CustomFormFieldsDTOConverter(orglocationFormFields, orglocationFormSubFields);
		sections  = new ArrayList<>();
		sections.add(orglocationFormFieldsDTO.getSectionData());
		orglocationFormFieldsDTO.setCustomEditType("Edit");
		disableFields = "disabled";
		}
		model.addObject("customFormPopDetails",orglocationFormFieldsDTO);
		model.addObject("SourceleadForm_Sections_pop",sections);
		model.addObject("disableFields",disableFields);
		model.addObject("type",type);
		model.setViewName("customForms/addCustomPopUp");
		return model;
    }
	
	@RequestMapping(value="/deleteCustomField",method = RequestMethod.POST)
    public String deleteCustomField(@RequestParam(value="customID")Long customID,@RequestParam(value="status")String status,HttpServletRequest request) throws Exception {
		String result  = orglocationFormFieldsService.deleteCustomFieldById(customID,status);
		return result;
    }
	
}
