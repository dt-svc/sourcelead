package com.bis.sourcelead.web.reportsController;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/app")
public class ClientsReportsController {
	
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;

	@RequestMapping(value = "/clientReports")
	public ModelAndView clientReports(HttpServletRequest request) throws Exception
	{
		 ModelAndView model = new ModelAndView();
		 model.setViewName("reports/clientReports");
		 model.addObject("sourceleadUrl", sourceleadUrl);
		 return model;
	}
}
