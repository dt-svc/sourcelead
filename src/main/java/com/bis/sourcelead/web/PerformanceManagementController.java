package com.bis.sourcelead.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dto.performance.PerformanceAppraisalTemplateDTO;
import com.bis.sourcelead.dto.performance.PerformanceRatingLevelDTO;
import com.bis.sourcelead.dto.performance.PerformanceRatingScaleDTO;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.performance.PerformanceAppraisalTemplate;
import com.bis.sourcelead.entity.performance.PerformanceBadges;
import com.bis.sourcelead.entity.performance.PerformanceCompetencies;
import com.bis.sourcelead.entity.performance.PerformanceFeedbackTemplate;
import com.bis.sourcelead.entity.performance.PerformanceRatingLevel;
import com.bis.sourcelead.entity.performance.PerformanceRatingScale;
import com.bis.sourcelead.service.DepartmentService;
import com.bis.sourcelead.service.RolesService;
import com.bis.sourcelead.service.performance.PerformanceAppraisalTemplateService;
import com.bis.sourcelead.service.performance.PerformanceBadgesService;
import com.bis.sourcelead.service.performance.PerformanceCompetenciesService;
import com.bis.sourcelead.service.performance.PerformanceFeedbackTemplateService;
import com.bis.sourcelead.service.performance.PerformanceRatingLevelService;
import com.bis.sourcelead.service.performance.PerformanceRatingScaleService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;

@Controller
@RequestMapping("/app")
public class PerformanceManagementController {
	
	private static final Logger logger = LoggerFactory.getLogger(PerformanceManagementController.class);
	
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	@Autowired
	CommonUtil commonUtil;
	
	@Autowired
	PerformanceBadgesService performanceBadgesService;
	
	@Autowired
	PerformanceFeedbackTemplateService performanceFeedbackTemplateService;
	
	@Autowired
	DepartmentService departmentService;
	
	@Autowired
	RolesService rolesService;
	
	@Autowired
	PerformanceAppraisalTemplateService performanceAppraisalTemplateService;
	
	@Autowired
	PerformanceCompetenciesService performanceCompetenciesService;
	
	@Autowired
	PerformanceRatingLevelService performanceRatingLevelService;
	
	@Autowired
	PerformanceRatingScaleService performanceRatingScaleService;
	
	
	
	@RequestMapping(value = "/performanceManagementConfiguration")
	public ModelAndView performanceManagementConfiguration(HttpServletRequest request)
	{
		ModelAndView model = new ModelAndView();
		commonUtil.highLightMenu(Constants.OrgSettings, request);
		model.setViewName("performance/performanceManagementConfiguration");
		model.addObject("sourceleadUrl", sourceleadUrl);
		return model;
	}
	
	
	
	@RequestMapping(value = "/getPerformanceRewardsAndBadgesList")
	public ModelAndView getPerformanceRewardsAndBadgesList(HttpServletRequest request) throws Exception{
		ModelAndView model = new ModelAndView();
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		 model.addObject("sourceleadUrl", sourceleadUrl);
		 List<PerformanceBadges> badgesList = performanceBadgesService.getPerformanceBadgesList(orgLocation.getId());
		 model.addObject("badgesList", badgesList);
		 model.addObject("badgesListSize", badgesList.size());
		 model.setViewName("performance/performanceBadgesList");
		return model;
	}
	
	
	@RequestMapping(value = "/addNewFeedbackTemplate")
	public ModelAndView addNewFeedbackTemplate(HttpServletRequest request)
	{
		ModelAndView model = new ModelAndView();
		model.setViewName("performance/addNewFeedbackTemplate");
		model.addObject("sourceleadUrl", sourceleadUrl);
		return model;
	}
	
	
	@RequestMapping(value = "/getPerformanceFeedbackTemplateList")
	public ModelAndView getPerformanceFeedbackTemplateList(HttpServletRequest request) throws Exception{
		ModelAndView model = new ModelAndView();
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		 model.addObject("sourceleadUrl", sourceleadUrl);
		 List<PerformanceFeedbackTemplate> feedbackList = performanceFeedbackTemplateService.getPerformanceFeedbackTemplateList(orgLocation.getId());
		 model.addObject("feedbackList", feedbackList);
		 model.addObject("feedbackListSize", feedbackList.size());
		 model.setViewName("performance/performanceFeedbackTemplateList");
		return model;
	}
	
	@RequestMapping(value = "/viewPerformanceFeedbackTemplate")
	public ModelAndView viewPerformanceFeedbackTemplate(HttpServletRequest request)
	{
		ModelAndView model = new ModelAndView();
		model.setViewName("performance/viewPerformanceFeedbackTemplate");
		model.addObject("sourceleadUrl", sourceleadUrl);
		return model;
	}
	
	
	@RequestMapping(value = "/addNewAppraisalCycle")
	public ModelAndView addNewAppraisalCycle(HttpServletRequest request) throws Exception
	{
		ModelAndView model = new ModelAndView();
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		model.setViewName("performance/addNewAppraisalCycle");
		model.addObject("sourceleadUrl", sourceleadUrl);
		model.addObject("locationDateFormat", orgLocation.getDateFormat());
		return model;
	}
	
	
	@RequestMapping(value = "/getPerformanceAppraisalCyclesList")
	public ModelAndView getPerformanceAppraisalCyclesList(HttpServletRequest request) throws Exception{
		ModelAndView model = new ModelAndView();
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		List<PerformanceAppraisalTemplateDTO> appraisalTemplatesList = performanceAppraisalTemplateService.getPerformanceAppraisalTemplateDTOList(orgLocation.getId(),orgLocation.getDateFormat());
		model.addObject("appraisalTemplatesList", appraisalTemplatesList);
		model.addObject("appraisalTemplatesListSize", appraisalTemplatesList.size());
		model.addObject("sourceleadUrl", sourceleadUrl);
		model.setViewName("performance/performanceAppraisalCyclesList");
		return model;
	}
	
	@RequestMapping(value = "/getPerformanceAppraisalAssignToList")
	public ModelAndView getPerformanceAppraisalAssignToList(@RequestParam(value = "uuid", required = true) String uuid,
			HttpServletRequest request) throws Exception{
		ModelAndView model = new ModelAndView();
		model.addObject("sourceleadUrl", sourceleadUrl);
		model.addObject("uuid", uuid);
		model.setViewName("performance/performanceAppraisalAssignToList");
		return model;
	}
	
	
	@RequestMapping(value = "/performanceCompetenceList")
	public ModelAndView performanceCompetenceList(HttpServletRequest request) throws Exception{
		ModelAndView model = new ModelAndView();
		model.addObject("sourceleadUrl", sourceleadUrl);
		model.setViewName("performance/performanceCompetenceList");
		return model;
	}
	
	@RequestMapping(value = "/performanceRatingScaleList")
	public ModelAndView performanceRatingScaleList(HttpServletRequest request) throws Exception{
		ModelAndView model = new ModelAndView();
		model.addObject("sourceleadUrl", sourceleadUrl);
		model.setViewName("performance/performanceRatingScaleList");
		return model;
	}
	
	@RequestMapping(value = "/addNewRatingScale")
	public ModelAndView addNewRatingScale(HttpServletRequest request) throws Exception{
		ModelAndView model = new ModelAndView();
		model.addObject("sourceleadUrl", sourceleadUrl);
		model.setViewName("performance/addNewRatingScale");
		return model;
	}
	
	
	
	@RequestMapping(value = "/tagPerformanceCompetence")
	public ModelAndView tagPerformanceCompetence(HttpServletRequest request) throws Exception
	{
		ModelAndView model = new ModelAndView();
		model.addObject("sourceleadUrl", sourceleadUrl);
		model.setViewName("performance/tagPerformanceCompetence");
		return model;
	}
	
	
	@RequestMapping(value = "/validateCompetenceName", method = RequestMethod.GET)
	@ResponseBody String validateCompetenceName(@RequestParam(value = "competenceName", required = true) String competenceName,
			@RequestParam(value = "competenceId", required = false) Long competenceId,HttpServletRequest request) throws Exception {
		
		Long orgLocationId = (Long) SourceLeadSessionManager.getSessionAttribute(request,"orgLocationId");
		
		PerformanceCompetencies performanceCompetencies = null;
		String result = null;
		
		if(competenceId != null){
			performanceCompetencies  = performanceCompetenciesService.getPerformanceCompetenciesById(competenceId);
			if(performanceCompetencies.getCompetenciesName().equalsIgnoreCase(competenceName)){
				result = "valid";
			}else{
				boolean flag  = performanceCompetenciesService.isCompetenceNameExisted(orgLocationId, competenceName);
				if(flag == true){
					result = "invalid";
				}else{
					result = "valid";
				}
			}
		}
		
		else{
			boolean flag  = performanceCompetenciesService.isCompetenceNameExisted(orgLocationId, competenceName);
			if(flag == true){
				result = "invalid";
			}else{
				result = "valid";
			}
		}
		
		return result;
		
	}
	
	

	@RequestMapping(value = "/validateRatingLevelName", method = RequestMethod.GET)
	@ResponseBody String validateRatingLevelName(@RequestParam(value = "ratingLevelName", required = true) String ratingLevelName,
			@RequestParam(value = "ratingLevelId", required = false) Long ratingLevelId,HttpServletRequest request) throws Exception {
		
		Long orgLocationId = (Long) SourceLeadSessionManager.getSessionAttribute(request,"orgLocationId");
		
		String result = null;
		if(ratingLevelId != null){
			 PerformanceRatingLevelDTO ratingLevelDTO =  performanceRatingLevelService.getPerformanceRatingLevelDetailsById(ratingLevelId);
			 if(ratingLevelDTO.getName().toLowerCase().equalsIgnoreCase(ratingLevelName.toLowerCase())) {
				 result = "valid";
			 }else {
				 boolean flag  = performanceRatingLevelService.isRatingLevelNameExisted(orgLocationId, ratingLevelName);
					if(flag == true){
						result = "invalid";
					}else{
						result = "valid";
					} 
			 }
		}
		else{
			boolean flag  = performanceRatingLevelService.isRatingLevelNameExisted(orgLocationId, ratingLevelName);
			if(flag == true){
				result = "invalid";
			}else{
				result = "valid";
			}
		}
		
		return result;
		
	}
	
	
	
	@RequestMapping(value = "/validateFeedbackTemplateName", method = RequestMethod.GET)
	@ResponseBody String validateFeedbackTemplateName(@RequestParam(value = "templateName", required = true) String templateName,
			@RequestParam(value = "templateId", required = false) Long templateId,HttpServletRequest request) throws Exception {
		
		Long orgLocationId = (Long) SourceLeadSessionManager.getSessionAttribute(request,"orgLocationId");
		
		String result = null;
		if(templateId != null){
			 PerformanceFeedbackTemplate feedbackTemplate =  performanceFeedbackTemplateService.getPerformanceFeedbackTemplateById(templateId);
			 if(feedbackTemplate.getName().toLowerCase().equalsIgnoreCase(templateName.toLowerCase())) {
				 result = "valid";
			 }else {
				 boolean flag  = performanceFeedbackTemplateService.isTemplateNameExisted(orgLocationId, templateName);
					if(flag == true){
						result = "invalid";
					}else{
						result = "valid";
					} 
			 }
		}
		else{
			boolean flag  = performanceFeedbackTemplateService.isTemplateNameExisted(orgLocationId, templateName);
			if(flag == true){
				result = "invalid";
			}else{
				result = "valid";
			}
		}
		
		return result;
		
	}
	
	
	
	@RequestMapping(value = "/validateAppraisalTemplateName", method = RequestMethod.GET)
	@ResponseBody String validateAppraisalTemplateName(@RequestParam(value = "appraisalName", required = true) String appraisalName,
			@RequestParam(value = "appraisalTemplateId", required = false) Long appraisalTemplateId,HttpServletRequest request) throws Exception {
		
		Long orgLocationId = (Long) SourceLeadSessionManager.getSessionAttribute(request,"orgLocationId");
		
		String result = null;
		if(appraisalTemplateId != null){
			PerformanceAppraisalTemplate appraisalTemplate =  performanceAppraisalTemplateService.getPerformanceAppraisalTemplateById(appraisalTemplateId);
			 if(appraisalTemplate.getName().toLowerCase().equalsIgnoreCase(appraisalName.toLowerCase())) {
				 result = "valid";
			 }else {
				 boolean flag  = performanceAppraisalTemplateService.isAppraisalTemplateNameExisted(orgLocationId, appraisalName);
					if(flag == true){
						result = "invalid";
					}else{
						result = "valid";
					} 
			 }
		}
		else{
			boolean flag  = performanceAppraisalTemplateService.isAppraisalTemplateNameExisted(orgLocationId, appraisalName);
			if(flag == true){
				result = "invalid";
			}else{
				result = "valid";
			}
		}
		
		return result;
		
	}
	
	@RequestMapping(value = "/validatePerformanceBadgeName", method = RequestMethod.GET)
	@ResponseBody String validatePerformanceBadgeName(@RequestParam(value = "badgeName", required = true) String badgeName,
			@RequestParam(value = "badgeId", required = false) Long badgeId,HttpServletRequest request) throws Exception {
		
		Long orgLocationId = (Long) SourceLeadSessionManager.getSessionAttribute(request,"orgLocationId");
		
		String result = null;
		if(badgeId != null){
			PerformanceBadges performanceBadges =  performanceBadgesService.getPerformanceBadgesDetailsById(badgeId);
			 if(performanceBadges.getName().toLowerCase().equalsIgnoreCase(badgeName.toLowerCase())) {
				 result = "valid";
			 }else {
				 boolean flag  = performanceBadgesService.isPerformanceBadgeNameExisted(orgLocationId, badgeName);
					if(flag == true){
						result = "invalid";
					}else{
						result = "valid";
					} 
			 }
		}
		else{
			boolean flag  = performanceBadgesService.isPerformanceBadgeNameExisted(orgLocationId, badgeName);
			if(flag == true){
				result = "invalid";
			}else{
				result = "valid";
			}
		}
		
		return result;
		
	}
	
	

	@RequestMapping(value = "/validateRatingScaleName", method = RequestMethod.GET)
	@ResponseBody String validateRatingScaleName(@RequestParam(value = "ratingScaleName", required = true) String ratingScaleName,
			@RequestParam(value = "ratingScaleId", required = false) Long ratingScaleId,HttpServletRequest request) throws Exception {
		
		Long orgLocationId = (Long) SourceLeadSessionManager.getSessionAttribute(request,"orgLocationId");
		
		String result = null;
		
		if(ratingScaleId != null){
			PerformanceRatingScaleDTO ratingScaleDTO = performanceRatingScaleService.getPerformanceRatingScaleDTOByScaleId(ratingScaleId);
			 if(ratingScaleDTO.getRatingName().toLowerCase().equalsIgnoreCase(ratingScaleName)) {
				 result = "valid";
			 }	else{
					boolean flag  = performanceRatingScaleService.isRatingScaleNameExisted(orgLocationId, ratingScaleName);
					if(flag == true){
						result = "invalid";
					}else{
						result = "valid";
					}
				}
		}
		else{
			boolean flag  = performanceRatingScaleService.isRatingScaleNameExisted(orgLocationId, ratingScaleName);
			if(flag == true){
				result = "invalid";
			}else{
				result = "valid";
			}
		}
		
		return result;
		
	}
	
	
	@RequestMapping(value = "/validateRatingShortCode", method = RequestMethod.GET)
	@ResponseBody String validateRatingShortCode(@RequestParam(value = "ratingShortCode", required = true) String ratingShortCode,
			@RequestParam(value = "ratingScaleId", required = false) Long ratingScaleId,HttpServletRequest request) throws Exception {
		
		Long orgLocationId = (Long) SourceLeadSessionManager.getSessionAttribute(request,"orgLocationId");
		
		String result = null;
		
		if(ratingScaleId != null){
			PerformanceRatingScaleDTO ratingScaleDTO = performanceRatingScaleService.getPerformanceRatingScaleDTOByScaleId(ratingScaleId);
			 if(ratingScaleDTO.getShortCode().toLowerCase().equalsIgnoreCase(ratingShortCode)) {
				 result = "valid";
			 }	else{
					boolean flag  = performanceRatingScaleService.isRatingShortCodeExisted(orgLocationId, ratingShortCode);
					if(flag == true){
						result = "invalid";
					}else{
						result = "valid";
					}
				}
		}
		else{
			boolean flag  = performanceRatingScaleService.isRatingShortCodeExisted(orgLocationId, ratingShortCode);
			if(flag == true){
				result = "invalid";
			}else{
				result = "valid";
			}
		}
		
		return result;
		
	}
	
	@RequestMapping(value = "/getTemplateAssignedAssociatesList")
	public ModelAndView getTemplateAssignedAssociatesList(@RequestParam(value = "uuid", required = true) String uuid,
			HttpServletRequest request) throws Exception{
		
		PerformanceAppraisalTemplate template = performanceAppraisalTemplateService.getPerformanceAppraisalTemplateByUUID(uuid);
		ModelAndView model = new ModelAndView();
		model.addObject("sourceleadUrl", sourceleadUrl);
		model.addObject("uuid", uuid);
		model.addObject("templateName", template.getName());
		model.setViewName("performance/appraisalAssignedAssociates");
		return model;
	}
	
	
}
