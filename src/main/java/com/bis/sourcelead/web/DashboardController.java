package com.bis.sourcelead.web;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SessionMaintainanceData;

/**
 * This class is used for...
 * 
 * @author: Venkat Kesav
 * @date: 15-July-2016
 */

@Controller
public class DashboardController {

	private static final Logger logger = LoggerFactory.getLogger(DashboardController.class);
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	@Autowired
	CommonUtil commonUtil;
	

	@RequestMapping(value = "/dashboard")
	public ModelAndView projects() {
		logger.debug("display the dash board page");
		ModelAndView model = new ModelAndView();
		model.addObject("sourceleadUrl", sourceleadUrl);
		model.setViewName("/dashboard");
		return model;
	}
	
	@RequestMapping(value = "/professionalDashboard")
	public ModelAndView professionalDashboard(HttpServletRequest request) {
		logger.debug("display the dash board page");
		ModelAndView model = new ModelAndView();
		
		SessionMaintainanceData sessionData = (SessionMaintainanceData)request.getSession().getAttribute("sessionData");
		if(sessionData == null)
		{
			sessionData = new SessionMaintainanceData();
		}
		sessionData.setNav(Constants.Dashboard);
		
		request.getSession().setAttribute("sessionData",sessionData);
		model.addObject("sourceleadUrl", sourceleadUrl);
		model.setViewName("professional/professionalDashboard");
		return model;
	}
}
