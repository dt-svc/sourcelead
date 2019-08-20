package com.bis.sourcelead.timesheet.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.util.SourceLeadSessionManager;

@Controller
@RequestMapping("/app")
public class TimesheetReportsController {
	
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	@RequestMapping(value = "/timeSheetReports")
	public ModelAndView timeSheetReports(HttpServletRequest request) throws Exception {
		ModelAndView model = new ModelAndView();
		 model.setViewName("timesheet/timesheetReports");
		 model.addObject("sourceleadUrl", sourceleadUrl);
		return model;

	}

	@RequestMapping(value = "/dailyTimeSheetReports")
	public ModelAndView dailyTimeSheetReports(HttpServletRequest request) throws Exception {
		ModelAndView model = new ModelAndView();
		 model.setViewName("timesheet/dailyTimesheetReports");
		 return model;
	}
	
	@RequestMapping(value = "/daywiseTimeSheetReports")
	public ModelAndView daywiseTimeSheetReports(HttpServletRequest request) throws Exception {
		ModelAndView model = new ModelAndView();
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
		 model.setViewName("timesheet/dayWiseTimesheetReports");
		 model.addObject("SourceLeadWeb_orgLocationId",orgLocations.getId());
		 model.addObject("sourceleadUrl", sourceleadUrl);
		return model;

	}
	
	@RequestMapping(value = "/projectTimeEntriesReport")
	public ModelAndView projectTimeEntriesReport(HttpServletRequest request) throws Exception {
		ModelAndView model = new ModelAndView();
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
		 model.setViewName("timesheet/projectTimeEntryReportList");
		 model.addObject("SourceLeadWeb_orgLocationId",orgLocations.getId());
		 model.addObject("sourceleadUrl", sourceleadUrl);
		return model;

	}

}
