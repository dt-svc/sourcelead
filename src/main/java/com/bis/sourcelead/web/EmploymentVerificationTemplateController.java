package com.bis.sourcelead.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/app")
public class EmploymentVerificationTemplateController {
	
	private static final Logger logger = LoggerFactory.getLogger(EmploymentVerificationTemplateController.class);
	
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	/**
	 * 	Display add template page
	 * @return
	 */
	@RequestMapping(value = "/addEmploymentVerificationTemplate")
	public ModelAndView addEmploymentVerificationTemplates(HttpServletRequest request,HttpServletResponse response) {
		ModelAndView model = new ModelAndView();
		model.addObject("sourceleadUrl", sourceleadUrl);
		model.addObject("sourceleadUrl", sourceleadUrl);
		model.setViewName("employmentVerification/addEmploymentVerificationTemplate");
		return model;
	}
	
	/**
	 * display employment verification template list page
	 * @return model
	 */
	@RequestMapping(value = "/employmentVerificationTemplateList")
	public ModelAndView employmentVerificationTemplate() {
		ModelAndView model = new ModelAndView();
		model.addObject("sourceleadUrl", sourceleadUrl);
		model.setViewName("employmentVerification/employmentVerificationTemplateList");
		return model;
	}
}
