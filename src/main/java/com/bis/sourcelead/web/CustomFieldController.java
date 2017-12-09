package com.bis.sourcelead.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CustomFieldController {

@RequestMapping (value="/customFields")
public ModelAndView CustomField() {
	ModelAndView model = new ModelAndView();
	model.setViewName("customFields");
	return model;
	
	
	
}
	
	
}
