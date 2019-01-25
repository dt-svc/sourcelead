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
import com.bis.sourcelead.entity.NewContractCustomFieldData;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgRelations;
import com.bis.sourcelead.entity.OrglocationFormFields;
import com.bis.sourcelead.entity.OrglocationFormSubFields;
import com.bis.sourcelead.entity.SourceleadActions;
import com.bis.sourcelead.entity.SourceleadFormFields;
import com.bis.sourcelead.entity.SourceleadFormSections;
import com.bis.sourcelead.service.AssetCustomFieldService;
import com.bis.sourcelead.service.CandidateCustomFieldDataService;
import com.bis.sourcelead.service.ClientCustomFieldDataService;
//import com.bis.sourcelead.service.ContractCustomFieldDataService;
import com.bis.sourcelead.service.JobCustomFieldDataService;
import com.bis.sourcelead.service.NewContractCustomFieldDataService;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.OrgRelationsService;
import com.bis.sourcelead.service.OrglocationFormFieldsService;
import com.bis.sourcelead.service.ProjectCustomFieldDataService;
import com.bis.sourcelead.service.ProjectTaskCustomFieldDataService;
import com.bis.sourcelead.service.SourceleadActionsService;
import com.bis.sourcelead.service.SourceleadFormFieldsService;
import com.bis.sourcelead.service.SourceleadFormSectionsService;
import com.bis.sourcelead.service.UserCustomFieldDataService;
import com.bis.sourcelead.util.SourceLeadSessionManager;


@RestController
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
	
	@Autowired
	private UserCustomFieldDataService userCustomFieldDataService;
	
	@Autowired
	private ClientCustomFieldDataService clientCustomFieldDataService;
	
	@Autowired
	private ProjectCustomFieldDataService projectCustomFieldDataService;
	
	@Autowired
	private NewContractCustomFieldDataService newContractCustomFieldDataService;
	
	@Autowired
	private OrgRelationsService orgRelationsService;
	
	@Autowired
	private AssetCustomFieldService assetCustomFieldService;
	
	@Autowired
	private ProjectTaskCustomFieldDataService projectTaskCustomFieldDataService;
	
	
    @RequestMapping(value = "/getFormSectionsByActionId",  method = RequestMethod.POST)
    public  ModelAndView getFormSectionsByActionId(@RequestParam(value="actionId", required=true) Long actionId,HttpServletRequest request) throws Exception {
    	ModelAndView model = new ModelAndView();
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
    	List<SourceleadSectionDTO> sections  = sourceleadFormSectionsService.getSourceleadFormSectionsByActionId(actionId,orgLocation.getId());
    	model.addObject("SourceleadForm_Sections",sections);
    	model.setViewName("customForms/customFormSections");
    	return model;
    }
	 
    
    @RequestMapping(value = "/getSourceleadSectionDetailsById",  method = RequestMethod.POST)
    public  ModelAndView getSourceleadSectionDetailsById(@RequestParam(value="SectionId", required=true) Long section,@RequestParam(value="sourceleadFormId",required=true)Long sourceleadFormId,@RequestParam(value="type",required = true)String type,HttpServletRequest request) throws Exception {
    	ModelAndView model = new ModelAndView();
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		List<SourceleadFormFields> sectionsList  = sourceleadFormSectionsService.getSourceleadSectionDetailsById(section,sourceleadFormId,orgLocation.getId());
		SourceleadSectionDTO sections = sourceleadFormSectionsService.sourceleadFormSectionsDTOConverter(sectionsList.get(0).getSourceleadFormSections(), sectionsList, null);
		model.addObject("SourceleadFormViewData",sections);
		model.addObject("type",type);
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
    	OrgLocations loginOrgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
    	if(CustomFieldsRequestTypeDTO.getRequestFrom()!= null && StringUtils.isNotEmpty(CustomFieldsRequestTypeDTO.getRequestFrom()) && CustomFieldsRequestTypeDTO.getRequestFrom().equalsIgnoreCase("candidate")) {
    		SourceleadActions action = sourceleadActionsService.getSourceleadActionsByActionRole(Constants.ROLE_CREATE_CANDIDATE);
		     List<SourceleadSectionDTO> sections  = sourceleadFormSectionsService.getSourceleadFormSectionsByActionId(action.getId(),loginOrgLocations.getId());
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
    		jobData  = getDataByEntityIdActionIdandType(CustomFieldsRequestTypeDTO,actionId,loginOrgLocations);
	    	model.addObject("dataMapList", jobData);
	    	model.addObject("customFieldsList",OrglocationFormFieldsDTOList);
			model.addObject("currentIndex",currentIndex);
		if(CustomFieldsRequestTypeDTO.getRequestType()!= null && StringUtils.isNotBlank(CustomFieldsRequestTypeDTO.getRequestType()) && CustomFieldsRequestTypeDTO.getRequestType().equalsIgnoreCase("view")) {
			model.setViewName("genericCustomViewFields");
		}else {
			model.setViewName("genericCustomFormFields");
		}
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

	private Map<Long, String[]> getDataByEntityIdActionIdandType(CustomFieldsRequestTypeDTO customFieldsRequestTypeDTO,Long actionId, OrgLocations loginOrgLocations) throws Exception {
		Map<Long, String[]> getcustomFieldData = null;
		 		 List<CustomFormFieldDTO> formFieldId = getSectionDataDetailsByActionId(actionId,loginOrgLocations);
		 		 OrgLocations orgLoc = orgLocationService.getOrgLocations(loginOrgLocations.getId());
		 		 if(customFieldsRequestTypeDTO != null && StringUtils.isNotEmpty(customFieldsRequestTypeDTO.getRequestFrom())) {
		 		 switch (customFieldsRequestTypeDTO.getRequestFrom()) {
		 		 	case "requirement":
			 			if(formFieldId != null && !formFieldId.isEmpty() && loginOrgLocations != null && customFieldsRequestTypeDTO.getRequirementId()!= null) {
							 getcustomFieldData = jobCustomFieldDataService.getJobCustomFieldDataMapByLocationId(orgLoc.getOrg().getId(),formFieldId,customFieldsRequestTypeDTO.getRequirementId());
						 }
			 			break;
		 		 	case "candidate":
		 		 		if(formFieldId != null && !formFieldId.isEmpty() && loginOrgLocations != null && customFieldsRequestTypeDTO.getCandidateId() != null) {
							 getcustomFieldData = candidateCustomFieldDataService.getCandidateCustomFieldDataMapByLocationId(orgLoc.getOrg().getId(),formFieldId,customFieldsRequestTypeDTO.getCandidateId());
						 }
			 			break;
		 		 	case "user" :
		 		 		if(formFieldId != null && !formFieldId.isEmpty() && loginOrgLocations != null && customFieldsRequestTypeDTO.getUserId() != null){
		 		 			getcustomFieldData = userCustomFieldDataService.getUserCustomFieldDataByUserId(orgLoc.getOrg().getId(),formFieldId, customFieldsRequestTypeDTO.getUserId());
		 		 		}
		 		 	     break;
		 		 	case "clientAndVendor":
		 		 		if(formFieldId != null && !formFieldId.isEmpty() && loginOrgLocations != null && customFieldsRequestTypeDTO.getOrgRelationID()!= null && customFieldsRequestTypeDTO.getClientLocationId() != null){
		 		 			OrgRelations orgRelation = orgRelationsService.getClientAndVendorOrgDeatilsById( customFieldsRequestTypeDTO.getClientLocationId(), customFieldsRequestTypeDTO.getOrgRelationID());
		 		 			if(orgRelation != null) {
		 		 				if(orgRelation.getResponderLocationId()!= null && orgRelation.getResponderLocationId().getId().longValue() != loginOrgLocations.getId().longValue()) {
			 		 				getcustomFieldData = clientCustomFieldDataService.getClientAndVendorCustomFieldDataByClientOrgIdAndClientLocId(orgRelation.getResponderLocationId().getOrg().getId(),formFieldId,orgLoc.getOrg().getId());
			 		 			}else if(orgRelation.getLocationId()!= null && orgRelation.getLocationId().getId().longValue() != loginOrgLocations.getId().longValue()) {
			 		 				getcustomFieldData = clientCustomFieldDataService.getClientAndVendorCustomFieldDataByClientOrgIdAndClientLocId(orgRelation.getLocationId().getOrg().getId(),formFieldId,orgLoc.getOrg().getId());
			 		 			}
		 		 			}
		 		 		}
		 		 		break;
		 		 	case "project" :
		 		 		if(formFieldId != null && !formFieldId.isEmpty() && loginOrgLocations != null && customFieldsRequestTypeDTO.getProjectId() != null){
		 		 			getcustomFieldData =  projectCustomFieldDataService.getProjectCustomFieldDataByProjectId(orgLoc.getOrg().getId(),formFieldId, customFieldsRequestTypeDTO.getProjectId());
		 		 		}
		 		 		
		 		 		break;
		 		 	case "contract" :
		 		 		if(formFieldId != null && !formFieldId.isEmpty() && loginOrgLocations != null && customFieldsRequestTypeDTO.getContractId() != null){
		 		 			getcustomFieldData =  newContractCustomFieldDataService.getNewContractCustomFieldDataByContractId(orgLoc.getOrg().getId(),formFieldId, customFieldsRequestTypeDTO.getContractId());
		 		 	}
		 		 		
		 		 		break;
		 		 	case "asset" :
		 		 		if(formFieldId != null && !formFieldId.isEmpty() && loginOrgLocations != null && customFieldsRequestTypeDTO.getAssetId() != null){
		 		 			getcustomFieldData =  assetCustomFieldService.getAssetCustomFieldDataByAssetId(orgLoc.getOrg().getId(),formFieldId, customFieldsRequestTypeDTO.getAssetId());
		 		 	}
		 		 		
		 		 		break;
		 		 	case "projectTask" :
		 		 		if(formFieldId != null && !formFieldId.isEmpty() && loginOrgLocations != null && customFieldsRequestTypeDTO.getProjectTask() != null){
		 		 			getcustomFieldData =  projectTaskCustomFieldDataService.getProjectTaskCustomFieldDataByUserId(orgLoc.getOrg().getId(),formFieldId, customFieldsRequestTypeDTO.getProjectTask());
		 		 	}
		 		 	break;	
		 		 		
		 		 }
		 		 }
		  return getcustomFieldData;
		 }
	}
