package com.bis.sourcelead.web;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.util.SessionMaintainanceData;

@Controller
public class BackOfficeController {
	
private static final Logger logger = LoggerFactory.getLogger(BackOfficeController.class);
	
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	
	@RequestMapping(value = "/finance")
	public ModelAndView finance(HttpServletRequest request) {
		
		ModelAndView model = new ModelAndView();
		model.setViewName("backOffice/finance");
		model.addObject("sourceleadUrl", sourceleadUrl);
		
		SessionMaintainanceData sessionData = (SessionMaintainanceData)request.getSession().getAttribute("sessionData");
		if(sessionData == null)
		{
			sessionData = new SessionMaintainanceData();
		}
		sessionData.setNav(Constants.Finance);
		
		request.getSession().setAttribute("sessionData",sessionData);
		
		return model;
		
	}
	
	
	@RequestMapping(value = "/assetManagement")
	public ModelAndView assetManagement(HttpServletRequest request) {
		
		ModelAndView model = new ModelAndView();
		model.setViewName("backOffice/assetManagement");
		model.addObject("sourceleadUrl", sourceleadUrl);
		
		SessionMaintainanceData sessionData = (SessionMaintainanceData)request.getSession().getAttribute("sessionData");
		if(sessionData == null)
		{
			sessionData = new SessionMaintainanceData();
		}
		sessionData.setNav(Constants.AssetManagement);
		
		request.getSession().setAttribute("sessionData",sessionData);
		
		return model;
		
	}

}
