package com.bis.sourcelead.web.reportsController;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/app")
public class ApplicantBySourceController {
	
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;

	@RequestMapping(value = "/applicantBySource")
	public ModelAndView applicantBySource(HttpServletRequest request) throws Exception
	{
		 ModelAndView model = new ModelAndView();
		 model.setViewName("reports/applicantBySource");
		 model.addObject("sourceleadUrl", sourceleadUrl);
		 return model;
	}
}
