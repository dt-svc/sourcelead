package com.bis.sourcelead.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ReportsController {

private static final Logger logger = LoggerFactory.getLogger(ReportsController.class);
	
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	 @RequestMapping(value = "/applicantsListForReports")
		public ModelAndView applicantsListForReports() throws Exception {
		 ModelAndView model = new ModelAndView();
		 model.addObject("sourceleadUrl", sourceleadUrl);
		 model.setViewName("reports/applicantsReport");
		 return model;
			
	 }	
	 @RequestMapping(value = "/applicantsReceivedListForReports")
		public ModelAndView applicantsReceivedListForReports() throws Exception {
		 ModelAndView model = new ModelAndView();
		 model.addObject("sourceleadUrl", sourceleadUrl);
		 model.setViewName("reports/applicantsReceivedReport");
		 return model;
			
	 }	
}
