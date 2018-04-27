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
import com.bis.sourcelead.entity.Countries;
import com.bis.sourcelead.entity.LookUpData;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.CountriesService;
import com.bis.sourcelead.service.JobAlertService;
import com.bis.sourcelead.service.LookUpDataService;
import com.bis.sourcelead.service.StatesService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;

@Controller
@RequestMapping("/app")
public class JobAlertController {
	
	private static final Logger logger = LoggerFactory.getLogger(JobAlertController.class);
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	@Autowired
	CommonUtil commonUtil;
	
	@Autowired
	CountriesService countriesService;
	
	@Autowired
	StatesService statesService;
	
	@Autowired
	LookUpDataService lookUpDataService;
	
	@Autowired
	JobAlertService jobAlertService;
	
	protected static boolean isNullOrEmpty(String str) {
		return (str == null || "".equals(str.trim()));
	}
	
	
	
	@RequestMapping(value = "/jobAlertList")
 	public ModelAndView jobAlertList(HttpServletRequest request) throws Exception {
		    ModelAndView model = new ModelAndView();
		    OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		    User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			Countries countries = countriesService.getCountriesByCountryName(user.getCountry());
			if(null != countries){
            model.addObject("loginCountryName", countries.getName());
            model.addObject("currencyCode", countries.getCurrency());
			}
			
			List<LookUpData> skillsList = lookUpDataService.getLookUpDataByCategory(Constants.SKILLS);
			model.addObject("skillsList", skillsList);
			
			List<String> industryListWithoutId = lookUpDataService.getAllSkillsByIndustryCategory();
			model.addObject("industryList", industryListWithoutId);
			  
 			commonUtil.highLightMenu(Constants.OrgSettings, request);
 			model.setViewName("jobAlert/jobAlertList");
 			model.addObject("sourceleadUrl", sourceleadUrl);
 			return model;
 	}
	
	
	
	@RequestMapping(value = "/viewAllJobAlerts")
 	public ModelAndView viewAllJobAlerts(@RequestParam(value="jobAlertUUID", required=false) String jobAlertUUID,HttpServletRequest request) throws Exception {
		    ModelAndView model = new ModelAndView();
		    
 			model.setViewName("jobAlert/viewAllJobAlerts");
 			model.addObject("jobAlertUUID",jobAlertUUID);
 			model.addObject("sourceleadUrl", sourceleadUrl);
 			return model;
 	}
	

}
