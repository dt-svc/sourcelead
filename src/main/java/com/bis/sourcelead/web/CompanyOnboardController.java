package com.bis.sourcelead.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.entity.Modules;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.Timezone;
import com.bis.sourcelead.service.ModulesService;
import com.bis.sourcelead.service.TimeZoneService;
import com.bis.sourcelead.util.SourceLeadSessionManager;

@Controller
public class CompanyOnboardController {

	private static final Logger logger = LoggerFactory.getLogger(CompanyOnboardController.class);
	
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	@Autowired
	TimeZoneService timeZoneService;
	
	@Autowired
	ModulesService modulesService;
	
	
		@RequestMapping(value = "/companyOnboardLocations")
		public ModelAndView companyOnboardLocations(HttpServletRequest request) throws Exception  {
			
			ModelAndView model = new ModelAndView();  
			OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			List<Timezone> timezoneList = timeZoneService.getAllTimeZones();
			List<Modules> modulesList = null;
			List<Modules> requirementSubModulesList = null;
			try {
				modulesList = modulesService.getSourceLeadModules();
				model.addObject("modulesList", modulesList);
				requirementSubModulesList = modulesService.getRequirementSubModules();
				model.addObject("requirementSubModulesList", requirementSubModulesList);
			} catch (Exception e) {
				e.printStackTrace();
			}
			model.addObject("timezoneList", timezoneList);
			model.addObject("orgLocations", orgLocations);
			model.setViewName("orgSettings");
			
			model.addObject("sourceleadUrl", sourceleadUrl);
			model.setViewName("onboard/companyOnboardLocations");
			return model;
		}
		
		@RequestMapping(value = "/companyOnboardFederalAndStateTaxes")
		public ModelAndView companyOnboardFederalAndStateTaxes(HttpServletRequest request) throws Exception  {
			
			ModelAndView model = new ModelAndView();  
			model.addObject("sourceleadUrl", sourceleadUrl);
			model.setViewName("onboard/companyOnboardFederalAndStateTaxes");
			return model;
		}
		
}
