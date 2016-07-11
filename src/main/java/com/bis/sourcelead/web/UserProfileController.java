package com.bis.sourcelead.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RestController
@PropertySource(value = { "classpath:config/application.properties" })
public class UserProfileController {
	
	private static final Logger logger = LoggerFactory.getLogger(UserProfileController.class);
	
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;

	   @RequestMapping(value = "/userProfile")
	 	public ModelAndView userProfile() {
	 			ModelAndView model = new ModelAndView();
	 			model.setViewName("userProfile");
	 			model.addObject("sourceleadUrl", sourceleadUrl);
	 			return model;
	 	}

}
