package com.bis.sourcelead.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@PropertySource("${propertyLocation:app.properties}")
public class OrgSettingsController {
	
	private static final Logger logger = LoggerFactory.getLogger(OrgSettingsController.class);
	
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	
	@RequestMapping(value = "/orgSettings")
 	public ModelAndView orgSettings() {
 			ModelAndView model = new ModelAndView();
 			model.setViewName("orgSettings");
 			model.addObject("sourceleadUrl", sourceleadUrl);
 			return model;
 	}
}
