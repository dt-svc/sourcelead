package com.bis.sourcelead.web;

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
	
	
	
	

}
