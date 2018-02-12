package com.bis.sourcelead.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dto.CustomFieldsRequestTypeDTO;
import com.bis.sourcelead.dto.CustomFormFieldDTO;
import com.bis.sourcelead.dto.OrglocationFormFieldsDTO;
import com.bis.sourcelead.dto.SourceleadSectionDTO;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrglocationFormFields;
import com.bis.sourcelead.entity.OrglocationFormSubFields;
import com.bis.sourcelead.entity.SourceleadActions;
import com.bis.sourcelead.entity.SourceleadFormFields;
import com.bis.sourcelead.entity.SourceleadFormSections;
import com.bis.sourcelead.service.CandidateCustomFieldDataService;
import com.bis.sourcelead.service.JobCustomFieldDataService;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.OrglocationFormFieldsService;
import com.bis.sourcelead.service.SourceleadActionsService;
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
	
	@Autowired
	private OrgLocationService orgLocationService;
	
	@Autowired
	private JobCustomFieldDataService jobCustomFieldDataService;
	
	@Autowired
	private CandidateCustomFieldDataService candidateCustomFieldDataService;
	
	@Autowired
	private SourceleadActionsService sourceleadActionsService;
	
	
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
    public ModelAndView getAllCustomFormFieldsBySectionIdandOrgLocationIdWeb(CustomFieldsRequestTypeDTO CustomFieldsRequestTypeDTO,HttpServletRequest request) throws Exception {
    	OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
    	if(CustomFieldsRequestTypeDTO.getRequestFrom()!= null && StringUtils.isNotEmpty(CustomFieldsRequestTypeDTO.getRequestFrom()) && CustomFieldsRequestTypeDTO.getRequestFrom().equalsIgnoreCase("candidate")) {
    		SourceleadActions action = sourceleadActionsService.getSourceleadActionsByActionRole(Constants.ROLE_CREATE_CANDIDATE);
		     List<SourceleadSectionDTO> sections  = sourceleadFormSectionsService.getSourceleadFormSectionsByActionId(action.getId(),orgLocation.getId());
    		 CustomFieldsRequestTypeDTO.setSectionId(sections.get(0).getId());
    	}
    	
    	List<OrglocationFormFields> orglocationFormFieldList  = getAllCustomFormFieldsBySectionIdandOrgLocationId(CustomFieldsRequestTypeDTO.getSectionId(), request);
		ModelAndView model = new ModelAndView();
		List<OrglocationFormFieldsDTO> OrglocationFormFieldsDTOList =new ArrayList<>();
		Long actionId = null;
		Map<Long, String[]> jobData = null;
		long currentIndex = 0;
    	for(OrglocationFormFields customForm:orglocationFormFieldList) {
    		List<OrglocationFormSubFields>  CustomFormSubFields = orglocationFormFieldsService.getAllCustomFormSubFieldsBySectionIdandOrgLocationId(customForm.getId());
    		SourceleadFormSections section = customForm.getSourceleadFormSections();
    		actionId = section.getSourceleadActions().getId();
    		List<Long> orders = new  ArrayList<>();
    		if(section != null && section.getOrder() >1) {
    			for(long i = 1;i<section.getOrder();i++) {
    				orders.add(i);
    			}
    		}
    		if(orders != null && !orders.isEmpty()) {
    			currentIndex = orglocationFormFieldsService.getCurrentIndexBySourceleadActionIdAndOrders(actionId, orders);
    		}
    		OrglocationFormFieldsDTO customFormSectonsListDTO = sourceleadFormSectionsService.CustomFormFieldsDTOConverter(customForm, CustomFormSubFields);
    		OrglocationFormFieldsDTOList.add(customFormSectonsListDTO);
    	}
    		jobData  = getDataByEntityIdActionIdandType(CustomFieldsRequestTypeDTO,actionId,orgLocation);
	    	model.addObject("dataMapList", jobData);
	    	model.addObject("customFieldsList",OrglocationFormFieldsDTOList);
			model.addObject("currentIndex",currentIndex);
		
    	model.setViewName("genericCustomFormFields");
    	return model;
    }
    
    private  List<CustomFormFieldDTO> getSectionDataDetailsByActionId(Long actionId, OrgLocations orgLocation) throws Exception {
    	 List<CustomFormFieldDTO> formFieldId = null;
    	if(actionId != null){
			 formFieldId = new ArrayList<>();
			 List<SourceleadSectionDTO> sections  = sourceleadFormSectionsService.getSourceleadFormSectionsByActionId(actionId,orgLocation.getId());
			 if(sections != null && !sections.isEmpty()) {
				 for(SourceleadSectionDTO sec:sections) {
					 for(OrglocationFormFieldsDTO customFields:sec.getCustomFieldsList()) {
						 CustomFormFieldDTO custom = new CustomFormFieldDTO();
						 custom.setCustomFormFieldId(customFields.getId());
						 formFieldId.add(custom);
					 }
				 }
			 }
    	}
		return formFieldId;
    }

	private Map<Long, String[]> getDataByEntityIdActionIdandType(CustomFieldsRequestTypeDTO customFieldsRequestTypeDTO,Long actionId, OrgLocations orgLocation) throws Exception {
		Map<Long, String[]> jobData = null;
		 		 List<CustomFormFieldDTO> formFieldId = getSectionDataDetailsByActionId(actionId,orgLocation);
		 		 OrgLocations orgLoc = orgLocationService.getOrgLocations(orgLocation.getId());
		 		 if(customFieldsRequestTypeDTO != null && StringUtils.isNotEmpty(customFieldsRequestTypeDTO.getRequestFrom())) {
		 		 switch (customFieldsRequestTypeDTO.getRequestFrom()) {
		 		 	case "requirement":
			 			if(formFieldId != null && !formFieldId.isEmpty() && orgLocation != null && customFieldsRequestTypeDTO.getRequirementId()!= null) {
							 jobData = jobCustomFieldDataService.getJobCustomFieldDataMapByLocationId(orgLoc.getOrg().getId(),formFieldId,customFieldsRequestTypeDTO.getRequirementId());
						 }
			 			break;
		 		 	case "candidate":
		 		 		if(formFieldId != null && !formFieldId.isEmpty() && orgLocation != null && customFieldsRequestTypeDTO.getCandidateId() != null) {
							 jobData = candidateCustomFieldDataService.getCandidateCustomFieldDataMapByLocationId(orgLoc.getOrg().getId(),formFieldId,customFieldsRequestTypeDTO.getCandidateId());
						 }
			 			break;
		 		 	}
		 		 }
		 		 
		 		 
				 
		return jobData;
	}
    
}