package com.bis.sourcelead.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/path")
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

}
