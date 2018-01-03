package com.bis.sourcelead.web;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.service.CountriesService;
import com.bis.sourcelead.service.LookUpDataService;
import com.bis.sourcelead.service.OrgRelationsService;
import com.bis.sourcelead.service.RequirementService;
import com.bis.sourcelead.service.StatesService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SessionMaintainanceData;

@Controller
@RequestMapping("/app")
public class DataMigrationCommonController {
	
	private static final Logger logger = LoggerFactory.getLogger(DataMigrationCommonController.class);
	
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	@Autowired
	OrgRelationsService orgRelationsService;
	
	@Autowired
	 CommonUtil commonUtil;
	
	@Autowired
	CountriesService countriesService;
	
	 /*
	  * author Venkat Kesav
	  * redirect to jobsImport page
	  */
	 //@PreAuthorize("hasAnyRole('ROLE_JOBS_BULK_IMPORTS')")
	 @RequestMapping(value = "/uploadJobs")
		public ModelAndView uploadJobs(HttpServletRequest request) {
		 SessionMaintainanceData sessionData = (SessionMaintainanceData)request.getSession().getAttribute("sessionData");
			if(sessionData == null)
			{
				sessionData = new SessionMaintainanceData();
			}
			sessionData.setNav(Constants.Requirement);
			request.getSession().setAttribute("sessionData",sessionData);
			ModelAndView model = new ModelAndView();
			model.setViewName("upload/uploadJobForm");
			model.addObject("sourceleadUrl", sourceleadUrl);
			return model;

		}
	 
	 /*
	  * author Venkat Kesav
	  * redirect to timeSheetImport page
	  */
	 @RequestMapping(value = "/uploadTimesheet")
		public ModelAndView uploadTimesheet(HttpServletRequest request) {
		 SessionMaintainanceData sessionData = (SessionMaintainanceData)request.getSession().getAttribute("sessionData");
			if(sessionData == null)
			{
				sessionData = new SessionMaintainanceData();
			}
			sessionData.setNav(Constants.TimeSheet);
			request.getSession().setAttribute("sessionData",sessionData);
			ModelAndView model = new ModelAndView();
			commonUtil.highLightMenu(Constants.TimeSheet, request);
			model.setViewName("upload/uploadTimeSheetForm");
			model.addObject("sourceleadUrl", sourceleadUrl);
			return model;
		}
	 
}
