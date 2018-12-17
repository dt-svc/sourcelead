package com.bis.sourcelead.rest;

import java.util.Date;

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
import com.bis.sourcelead.dto.PerformanceBadgesDTO;
import com.bis.sourcelead.dto.PerformanceFeedbackTemplateDTO;
import com.bis.sourcelead.dto.Response;
import com.bis.sourcelead.dto.performance.PerformanceAppraisalTemplateDTO;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.SubmissionTemplate;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.performance.PerformanceBadges;
import com.bis.sourcelead.entity.performance.PerformanceFeedbackTemplate;
import com.bis.sourcelead.enums.Result;
import com.bis.sourcelead.service.performance.PerformanceAppraisalTemplateService;
import com.bis.sourcelead.service.performance.PerformanceBadgesService;
import com.bis.sourcelead.service.performance.PerformanceFeedbackTemplateService;
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

}
