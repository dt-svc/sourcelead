package com.bis.sourcelead.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.entity.AssociateLoanAndAdvances;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.PerformanceBagdes;
import com.bis.sourcelead.service.PerformanceBadgesService;
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
		 List<PerformanceBagdes> badgesList = performanceBadgesService.getPerformanceBadgesList(orgLocation.getId());
		 model.addObject("badgesList", badgesList);
		 model.addObject("badgesListSize", badgesList.size());
		 model.setViewName("performance/performanceBadgesList");
		return model;
	}
	
}
