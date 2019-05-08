package com.bis.sourcelead.rest;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bis.sourcelead.annotations.Validator;
import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dto.DepartmentDTO;
import com.bis.sourcelead.dto.PerformanceBadgesDTO;
import com.bis.sourcelead.dto.PerformanceFeedbackTemplateDTO;
import com.bis.sourcelead.dto.Response;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.dto.RoleDTO;
import com.bis.sourcelead.dto.performance.PerformanceAppraisalTemplateDTO;
import com.bis.sourcelead.dto.performance.PerformanceCompetenciesDTO;
import com.bis.sourcelead.dto.performance.PerformanceTagCompetenciesDTO;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.performance.PerformanceAppraisalTemplate;
import com.bis.sourcelead.entity.performance.PerformanceBadges;
import com.bis.sourcelead.entity.performance.PerformanceCompetencies;
import com.bis.sourcelead.entity.performance.PerformanceFeedbackTemplate;
import com.bis.sourcelead.entity.performance.PerformanceTagCompetencies;
import com.bis.sourcelead.enums.Result;
import com.bis.sourcelead.service.DepartmentService;
import com.bis.sourcelead.service.RolesService;
import com.bis.sourcelead.service.performance.PerformanceAppraisalTemplateService;
import com.bis.sourcelead.service.performance.PerformanceBadgesService;
import com.bis.sourcelead.service.performance.PerformanceCompetenciesService;
import com.bis.sourcelead.service.performance.PerformanceFeedbackTemplateService;
import com.bis.sourcelead.service.performance.PerformanceTagCompetenciesService;
import com.bis.sourcelead.util.SourceLeadRandomCodeHelper;
import com.bis.sourcelead.util.SourceLeadSessionManager;

@RestController
@PropertySource("${propertyLocation:app.properties}")
@RequestMapping("/app")
public class PerformanceManagementRestController {

	private static final Logger logger = LoggerFactory.getLogger(PerformanceManagementRestController.class);
	
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	@Value("${static.path}")
	private String staticPath;
	
	@Autowired
	PerformanceBadgesService performanceBadgesService;
	
	@Autowired
	PerformanceFeedbackTemplateService performanceFeedbackTemplateService;
	
	@Autowired
	PerformanceAppraisalTemplateService performanceAppraisalTemplateService;
	
	@Autowired
	DepartmentService departmentService;
	
	@Autowired
	RolesService rolesService;
	
	@Autowired
	PerformanceCompetenciesService performanceCompetenciesService;
	
	@Autowired
	PerformanceTagCompetenciesService performanceTagCompetenciesService;
	
    @RequestMapping(value = "/savePerformanceBudgetDetails", method = RequestMethod.POST)
	public Response<PerformanceBadgesDTO> savePerformanceBudgetDetails(HttpServletRequest request, 
			PerformanceBadgesDTO performanceBadgesDTO){
		 Response<PerformanceBadgesDTO> resObj = new Response<>();
		 OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		 User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		 try {
			 if(Validator.validate(performanceBadgesDTO)){
				 performanceBadgesDTO.setStaticPath(staticPath);
				 performanceBadgesService.storePerformanceBadges(performanceBadgesDTO, orgLocation.getId(), user.getId());
			 resObj.setResponse(Result.SUCCESS.name());
			 resObj.setResponseMessage(Result.SUCCESS.getValue());
		    }
			 else {
				 resObj.setResponse(Result.INVALID_DATA.name());
				 resObj.setResponseMessage(Result.INVALID_DATA.getValue());
				
	    	 }
		 }catch (Exception e) {
			 resObj.setResponse(Result.AWKWARD.name());
			 resObj.setResponseMessage(Result.AWKWARD.getValue());
		}
		 return resObj;
    }
    
    
    
    @RequestMapping(value = "/getPerformanceRewardsAndBadgeDetailsById", method = RequestMethod.GET)
	public Response<PerformanceBadgesDTO> getPerformanceRewardsAndBadgeDetailsById(HttpServletRequest request, 
			@RequestParam(value = "badgeId", required = true) Long badgeId){
		 Response<PerformanceBadgesDTO> resObj = new Response<>();
		 try {
			 PerformanceBadgesDTO badgesDTO =  performanceBadgesService.getPerformanceBadgesById(badgeId);
			 resObj.setObject(badgesDTO);
			 resObj.setResponse(Result.SUCCESS.name());
			 resObj.setResponseMessage(Result.SUCCESS.getValue());
		    
		 }catch (Exception e) {
			 resObj.setResponse(Result.AWKWARD.name());
			 resObj.setResponseMessage(Result.AWKWARD.getValue());
		}
		 return resObj;
    }
    
    
    @RequestMapping(value = "/deletePerformanceRewardsAndBadgeById", method = RequestMethod.POST)
  	public String deletePerformanceRewardsAndBadgeById(HttpServletRequest request, 
  			@RequestParam(value = "badgeId", required = true) Long badgeId){
  		 String resObj = null;
  		 try {
  			 PerformanceBadges badges =  performanceBadgesService.getPerformanceBadgesDetailsById(badgeId);
  			 badges.setStatus(Constants.RECORD_INACTIVE);
  			 performanceBadgesService.savePerformanceBadges(badges);
  			 resObj = Result.SUCCESS.name();
  		    
  		 }catch (Exception e) {
  			resObj = Result.AWKWARD.name();
  		}
  		 return resObj;
    }
    
    
    @RequestMapping(value = "/deletePerformanceCompetenceById", method = RequestMethod.POST)
  	public String deletePerformanceCompetenceById(HttpServletRequest request, 
  			@RequestParam(value = "competenceId", required = true) Long competenceId){
  		 String resObj = null;
  		 try {
  			 PerformanceCompetencies competence =  performanceCompetenciesService.getPerformanceCompetenciesById(competenceId);
  			competence.setStatus(Constants.RECORD_INACTIVE);
  			performanceCompetenciesService.savePerformanceCompetencies(competence);
  			resObj = Result.SUCCESS.name();
  		    
  		 }catch (Exception e) {
  			resObj = Result.AWKWARD.name();
  		}
  		 return resObj;
    }
    
    
    @RequestMapping(value = "/deletePerformanceTagCompetenceById", method = RequestMethod.POST)
  	public String deletePerformanceTagCompetenceById(HttpServletRequest request, 
  			@RequestParam(value = "tagCompetenceId", required = true) Long tagCompetenceId){
  		 String resObj = null;
  		 try {
  			PerformanceTagCompetencies tagCompetence =  performanceTagCompetenciesService.getPerformanceTagCompetenciesById(tagCompetenceId);
  			tagCompetence.setStatus(Constants.RECORD_INACTIVE);
  			performanceTagCompetenciesService.savePerformanceTagCompetencies(tagCompetence);
  			resObj = Result.SUCCESS.name();
  		    
  		 }catch (Exception e) {
  			resObj = Result.AWKWARD.name();
  		}
  		 return resObj;
    }
    
	
	
	@RequestMapping(value = "/savePerformanceFeedbackTemplate", method = RequestMethod.POST)
	public String savePerformanceFeedbackTemplate(@RequestParam(value = "jsonData", required = false) String json,
			@RequestParam(value = "templateId", required = false) String templateId,
			@RequestParam(value = "jsonStringData", required = false) String jsonStringData, HttpServletRequest request)
			throws Exception {

		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,
				"orgLocations");
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		String result = null;

		String json1 = "{" + json + "}";
		JSONObject jsonObj = new JSONObject(json1);

		try {
			PerformanceFeedbackTemplate template = null;
			if (StringUtils.isNotBlank(templateId)) {
				template = performanceFeedbackTemplateService.getPerformanceFeedbackTemplateById(Long.parseLong(templateId));
			} else {
				template = new PerformanceFeedbackTemplate();
				template.setUuid(SourceLeadRandomCodeHelper.generateRandomUUID());
				template.setCreatedBy(user);
				template.setCreatedDate(new Date());
			}

			template.setName(jsonObj.getString("templateName").trim());
			template.setDescription(jsonObj.getString("description"));
			template.setOrgLocationId(orgLocations);
			template.setJsonString(jsonStringData);
			template.setStatus(Constants.RECORD_ACTIVE);
			
			template.setUpdatedBy(user);
			template.setUpdatedDate(new Date());
			performanceFeedbackTemplateService.savePerformanceFeedbackTemplate(template);
			result = Result.SUCCESS.name();

		} catch (Exception e) {
			e.printStackTrace();
			result = Result.AWKWARD.name();
		}
		return result;

	}
	
	
	
   @RequestMapping(value = "/loadSavedPerformanceFeedbackTemplate" , method = RequestMethod.GET)
   public PerformanceFeedbackTemplateDTO loadSavedPerformanceFeedbackTemplate(@RequestParam(value="templateId", required=false) Long templateId) throws Exception {
		
	    PerformanceFeedbackTemplateDTO templateDTO = new PerformanceFeedbackTemplateDTO();
	    PerformanceFeedbackTemplate template = performanceFeedbackTemplateService.getPerformanceFeedbackTemplateById(templateId);
	    templateDTO.setTemplateName(template.getName());
	    templateDTO.setDescription(template.getDescription());
	    templateDTO.setJsonString(template.getJsonString());
		return templateDTO;
    
   }
   
   
   @RequestMapping(value = "/deletePerformanceFeedbackTemplate", method = RequestMethod.POST)
 	public String deletePerformanceFeedbackTemplate(HttpServletRequest request, 
 			@RequestParam(value = "templateId", required = true) Long templateId){
 		 String resObj = null;
 		 try {
 			 PerformanceFeedbackTemplate template = performanceFeedbackTemplateService.getPerformanceFeedbackTemplateById(templateId);
 			template.setStatus(Constants.RECORD_INACTIVE);
 			performanceFeedbackTemplateService.savePerformanceFeedbackTemplate(template);
 			 resObj = Result.SUCCESS.name();
 		    
 		 }catch (Exception e) {
 			resObj = Result.AWKWARD.name();
 		}
 		 return resObj;
   }
   
   

   @RequestMapping(value = "/saveAppraisalCycleTemplate", method = RequestMethod.POST)
	public Response<PerformanceAppraisalTemplateDTO> saveAppraisalCycleTemplate(HttpServletRequest request, 
			PerformanceAppraisalTemplateDTO templateDTO){
		 Response<PerformanceAppraisalTemplateDTO> resObj = new Response<>();
		 OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		 User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		 try {
			 if(Validator.validate(templateDTO)){
			 performanceAppraisalTemplateService.storePerformanceAppraisalTemplate(templateDTO, orgLocation.getId(), user.getId(), orgLocation.getDateFormat());
			 resObj.setResponse(Result.SUCCESS.name());
			 resObj.setResponseMessage(Result.SUCCESS.getValue());
		    }
			 else {
				 resObj.setResponse(Result.INVALID_DATA.name());
				 resObj.setResponseMessage(Result.INVALID_DATA.getValue());
				
	    	 }
		 }catch (Exception e) {
			 resObj.setResponse(Result.AWKWARD.name());
			 resObj.setResponseMessage(Result.AWKWARD.getValue());
		}
		 return resObj;
   }
   
   
   
   @RequestMapping(value = "/getAppraisalCycleTemplateDetailsById" , method = RequestMethod.GET)
   public Response<PerformanceAppraisalTemplateDTO> getAppraisalCycleTemplateDetailsById(@RequestParam(value="templateId", required=false) Long templateId) throws Exception {
	   Response<PerformanceAppraisalTemplateDTO> resObj = new Response<>();
	   try {
		   PerformanceAppraisalTemplateDTO templateDTO = performanceAppraisalTemplateService.getPerformanceAppraisalTemplateDTOById(templateId);
		   resObj.setObject(templateDTO);
		   resObj.setResponse(Result.SUCCESS.name());
		   resObj.setResponseMessage(Result.SUCCESS.getValue());
	   }catch (Exception e) {
		     e.printStackTrace();
			 resObj.setResponse(Result.AWKWARD.name());
			 resObj.setResponseMessage(Result.AWKWARD.getValue());
		}
	  
	   return resObj;
   }
   
   
   
   @RequestMapping(value = "/getDepartmentsListForPerformanceAppraisal" , method = RequestMethod.GET)
   public List<DepartmentDTO> getDepartmentsListForPerformanceAppraisal(HttpServletRequest request) throws Exception {
	   OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
	   List<DepartmentDTO> departmentList = null;
	   try {
		   departmentList = departmentService.getDepartmentsListByOrgLocationId(orgLocation.getId());
	   }catch (Exception e) {
		     e.printStackTrace();
		}
	   return departmentList;
   }
   
   
   @RequestMapping(value = "/getRolesListForPerformanceAppraisal" , method = RequestMethod.GET)
   public List<RoleDTO> getRolesListForPerformanceAppraisal(HttpServletRequest request) throws Exception {
	   OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
	   List<RoleDTO> rolesList = null;
	   try {
		   rolesList = rolesService.getRolesDTOListByOrgLocationId(orgLocation.getId());
	   }catch (Exception e) {
		     e.printStackTrace();
		}
	   return rolesList;
   }
   
   
   @RequestMapping(value = "/getEmployeeTypesForPerformanceAppraisal" , method = RequestMethod.GET)
   public List<String> getEmployeeTypesForPerformanceAppraisal() throws Exception {
	   List<String> employeeTypeList = Arrays.asList(Constants.EMPLOYMENT_TYPES);
	   return employeeTypeList;
   }
   
   
   @RequestMapping(value = "/getFeedBackCategoriesListForPerformanceAppraisal" , method = RequestMethod.GET)
   public List<PerformanceFeedbackTemplateDTO> getFeedBackCategoriesListForPerformanceAppraisal(HttpServletRequest request) throws Exception {
	   OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
	   List<PerformanceFeedbackTemplateDTO> templateDTOList = null;
	   try {
		   templateDTOList = performanceFeedbackTemplateService.getPerformanceFeedbackTemplateDTOList(orgLocation.getId());
	   }catch (Exception e) {
		     e.printStackTrace();
		}
	   return templateDTOList;
   }
   
   
   @RequestMapping(value = "/getPerformanceCompetenceListForTagging" , method = RequestMethod.GET)
   public List<PerformanceCompetenciesDTO> getPerformanceCompetenceListForTagging(HttpServletRequest request) throws Exception {
	   Long orgLocationId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "orgLocationId");
	   List<PerformanceCompetenciesDTO> competenciesDTOList = null;
	   try {
		   competenciesDTOList = performanceCompetenciesService.getPerformanceCompetenciesListByOrgLocationId(orgLocationId);
	   }catch (Exception e) {
		     e.printStackTrace();
		}
	   return competenciesDTOList;
   }
   
   @RequestMapping(value = "/deleteAppraisalCycleTemplateById", method = RequestMethod.POST)
 	public String deleteAppraisalCycleTemplateById(HttpServletRequest request, 
 			@RequestParam(value = "templateId", required = true) Long templateId){
 		 String resObj = null;
 		 try {
 			 PerformanceAppraisalTemplate template =  performanceAppraisalTemplateService.getPerformanceAppraisalTemplateById(templateId);
 			template.setStatus(Constants.RECORD_INACTIVE);
 			performanceAppraisalTemplateService.savePerformanceAppraisalTemplate(template);
 			 resObj = Result.SUCCESS.name();
 		    
 		 }catch (Exception e) {
 			resObj = Result.AWKWARD.name();
 		}
 		 return resObj;
   }
   
   
   @RequestMapping(value = "/savePerformanceCompetence", method = RequestMethod.POST)
  	public Response<PerformanceCompetenciesDTO> savePerformanceCompetence(HttpServletRequest request, 
  			PerformanceCompetenciesDTO performanceCompetenciesDTO){
  		 Response<PerformanceCompetenciesDTO> resObj = new Response<>();
  		 OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
  		 User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
  		 try {
  			 if(Validator.validate(performanceCompetenciesDTO)){
  				performanceCompetenciesService.storePerformanceCompetencies(performanceCompetenciesDTO, orgLocation.getId(), user.getId());
  			 resObj.setResponse(Result.SUCCESS.name());
  			 resObj.setResponseMessage(Result.SUCCESS.getValue());
  		    }
  			 else {
  				 resObj.setResponse(Result.INVALID_DATA.name());
  				 resObj.setResponseMessage(Result.INVALID_DATA.getValue());
  				
  	    	 }
  		 }catch (Exception e) {
  			 resObj.setResponse(Result.AWKWARD.name());
  			 resObj.setResponseMessage(Result.AWKWARD.getValue());
  		}
  		 return resObj;
      }
   
   
	 @RequestMapping(value = "/getPerformanceCompetenceList", method = RequestMethod.GET)
	public ResponseList<PerformanceCompetenciesDTO> getPerformanceCompetenceList(PerformanceCompetenciesDTO performanceCompetenciesDTO,HttpServletRequest request) throws Exception {
		
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		
		String[] tableColumns = { "competenciesName", "createdDate", "description", "competenciesStatus" };
		int sortColumnIndex = 1; // by default sorting using updated date
		String sortColumnOrder = "desc"; // by default descending order
		if (performanceCompetenciesDTO.getOrder() != null && !performanceCompetenciesDTO.getOrder().isEmpty()) {
			sortColumnIndex = Integer.parseInt(performanceCompetenciesDTO.getOrder().get(0).get("column"));
			sortColumnOrder = performanceCompetenciesDTO.getOrder().get(0).get("dir");
		}
		performanceCompetenciesDTO.setSortColumn(tableColumns[sortColumnIndex]);
		performanceCompetenciesDTO.setSortOrder(sortColumnOrder);
		ResponseList<PerformanceCompetenciesDTO> performanceCompetenciesList = performanceCompetenciesService.getPerformanceCompetenciesList(performanceCompetenciesDTO, orgLocation.getDateFormat(), orgLocation.getId());
		return performanceCompetenciesList;
	}
	 
	 
	 
	    @RequestMapping(value = "/getTagCompetenceList", method = RequestMethod.GET)
		public ResponseList<PerformanceTagCompetenciesDTO> getTagCompetenceList(HttpServletRequest request){
	    	 ResponseList<PerformanceTagCompetenciesDTO> resObj = new ResponseList<>();
			 try {
				 OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
				 List<PerformanceTagCompetenciesDTO> competenciesTagDTOList =  performanceTagCompetenciesService.getPerformanceTagCompetenciesList(orgLocation.getId(),orgLocation.getDateFormat());
				 resObj.setData(competenciesTagDTOList);
				 resObj.setResponse(Result.SUCCESS.name());
				 resObj.setResponseMessage(Result.SUCCESS.getValue());
			    
			 }catch (Exception e) {
				 resObj.setResponse(Result.AWKWARD.name());
				 resObj.setResponseMessage(Result.AWKWARD.getValue());
			}
			 return resObj;
	    }
	 
	 
	    @RequestMapping(value = "/getPerformanceCompetenceDetailsById", method = RequestMethod.GET)
		public Response<PerformanceCompetenciesDTO> getPerformanceCompetenceDetailsById(HttpServletRequest request, 
				@RequestParam(value = "competenceId", required = true) Long competenceId){
			 Response<PerformanceCompetenciesDTO> resObj = new Response<>();
			 try {
				 PerformanceCompetenciesDTO competenciesDTO =  performanceCompetenciesService.getPerformanceCompetenciesDTOById(competenceId);
				 resObj.setObject(competenciesDTO);
				 resObj.setResponse(Result.SUCCESS.name());
				 resObj.setResponseMessage(Result.SUCCESS.getValue());
			    
			 }catch (Exception e) {
				 resObj.setResponse(Result.AWKWARD.name());
				 resObj.setResponseMessage(Result.AWKWARD.getValue());
			}
			 return resObj;
	    }
	    
	    
	    @RequestMapping(value = "/savePerformanceTagCompetencies", method = RequestMethod.POST)
		public Response<PerformanceTagCompetenciesDTO> savePerformanceTagCompetencies(HttpServletRequest request, 
				PerformanceTagCompetenciesDTO tagCompetenciesDTO){
			 Response<PerformanceTagCompetenciesDTO> resObj = new Response<>();
			 OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			 User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			 try {
				 if(Validator.validate(tagCompetenciesDTO)){
					 performanceTagCompetenciesService.storePerformanceTagCompetencies(tagCompetenciesDTO, orgLocation.getId(), user.getId());
				 resObj.setResponse(Result.SUCCESS.name());
				 resObj.setResponseMessage(Result.SUCCESS.getValue());
			    }
				 else {
					 resObj.setResponse(Result.INVALID_DATA.name());
					 resObj.setResponseMessage(Result.INVALID_DATA.getValue());
					
		    	 }
			 }catch (Exception e) {
				 resObj.setResponse(Result.AWKWARD.name());
				 resObj.setResponseMessage(Result.AWKWARD.getValue());
			}
			 return resObj;
	   }
	    
	    
	    @RequestMapping(value = "/getTagPerformanceCompetenceDetailsById" , method = RequestMethod.GET)
	    public Response<PerformanceTagCompetenciesDTO> getTagPerformanceCompetenceDetailsById(@RequestParam(value="tagCompetenceId", required=true) Long tagCompetenceId) throws Exception {
	 	   Response<PerformanceTagCompetenciesDTO> resObj = new Response<>();
	 	   try {
	 		   PerformanceTagCompetencies tagCompetices = performanceTagCompetenciesService.getPerformanceTagCompetenciesById(tagCompetenceId);
	 		   PerformanceTagCompetenciesDTO competencesDTO = new PerformanceTagCompetenciesDTO();
	 		   competencesDTO.setTagCompetenceId(tagCompetices.getId());
	 		   competencesDTO.setCompetenceRole(tagCompetices.getRoles());
	 		   competencesDTO.setJsonCompetenceString(tagCompetices.getCompetenciesTaggingJsonString());
	 		   competencesDTO.setCompetenceIds(tagCompetices.getCompetencies());
	 		   resObj.setObject(competencesDTO);
	 		   resObj.setResponse(Result.SUCCESS.name());
	 		   resObj.setResponseMessage(Result.SUCCESS.getValue());
	 	   }catch (Exception e) {
	 		     e.printStackTrace();
	 			 resObj.setResponse(Result.AWKWARD.name());
	 			 resObj.setResponseMessage(Result.AWKWARD.getValue());
	 		}
	 	  
	 	   return resObj;
	    }

}
