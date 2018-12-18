package com.bis.sourcelead.web;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.entity.Department;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.Roles;
import com.bis.sourcelead.entity.performance.PerformanceAppraisalTemplate;
import com.bis.sourcelead.entity.performance.PerformanceBadges;
import com.bis.sourcelead.entity.performance.PerformanceFeedbackTemplate;
import com.bis.sourcelead.service.DepartmentService;
import com.bis.sourcelead.service.RolesService;
import com.bis.sourcelead.service.performance.PerformanceAppraisalTemplateService;
import com.bis.sourcelead.service.performance.PerformanceBadgesService;
import com.bis.sourcelead.service.performance.PerformanceFeedbackTemplateService;
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
		
		List<PerformanceFeedbackTemplate> categoriesList = performanceFeedbackTemplateService.getPerformanceFeedbackTemplateList(orgLocation.getId());
		model.addObject("categoriesList", categoriesList);
		
		return model;
	}
	
	
	@RequestMapping(value = "/getPerformanceAppraisalCyclesList")
	public ModelAndView getPerformanceAppraisalCyclesList(HttpServletRequest request) throws Exception{
		ModelAndView model = new ModelAndView();
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		List<PerformanceAppraisalTemplate> appraisalTemplatesList = performanceAppraisalTemplateService.getPerformanceAppraisalTemplateList(orgLocation.getId());
		model.addObject("appraisalTemplatesList", appraisalTemplatesList);
		model.addObject("appraisalTemplatesListSize", appraisalTemplatesList.size());
		model.addObject("sourceleadUrl", sourceleadUrl);
		model.setViewName("performance/performanceAppraisalCyclesList");
		return model;
	}
	
	
}
