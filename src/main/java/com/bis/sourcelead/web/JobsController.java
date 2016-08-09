package com.bis.sourcelead.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.util.Constants;
import com.bis.sourcelead.util.SessionMaintainanceData;

@Controller
public class JobsController {
	
	
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	
	
	@RequestMapping(value = "/jobsList")
	public ModelAndView usersList(HttpServletRequest request) {
		
		ModelAndView model = new ModelAndView();
		model.addObject("sourceleadUrl", sourceleadUrl);
		model.setViewName("jobs/jobsList");
		
		SessionMaintainanceData sessionData = (SessionMaintainanceData)request.getSession().getAttribute("sessionData");
		if(sessionData == null)
		{
			sessionData = new SessionMaintainanceData();
		}
		sessionData.setNav(Constants.Jobs);
		
		request.getSession().setAttribute("sessionData",sessionData);
		
		
		return model;

	}

}
